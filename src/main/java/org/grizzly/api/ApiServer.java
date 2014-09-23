package org.grizzly.api;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.grizzly.api.config.Config;
import org.grizzly.api.dao.ArticleDao;
import org.grizzly.api.dao.LikeDao;
import org.grizzly.api.resource.ArticleResource;
import org.grizzly.api.resource.LiveResource;
import org.grizzly.api.service.ArticleService;
import org.grizzly.api.service.LikeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class ApiServer {

	private Config config;

	public static void main(String[] args) throws Exception {
		ApiServer apiServer = new ApiServer();
		apiServer.loadConfig(args.length > 0 ? args[0] : "default.json");
		apiServer.start();
	}

	public void start() throws Exception {
		URI uri = UriBuilder.fromUri(config.getBaseUrl()).port(config.getPort()).build();
		ResourceConfig resourceConfig = new ResourceConfig();

		bindService(resourceConfig);
		bindResource(resourceConfig);

		HttpServer server =
				GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false);
		settingPoolSize(server);
		server.start();
		System.in.read();
	}

	private void loadConfig(String path) throws Exception {
		config =
				new ObjectMapper()
						.readValue(ApiServer.class.getClassLoader().getResourceAsStream(path),
								Config.class);
	}

	private void settingPoolSize(HttpServer server) {
		NetworkListener listener = server.getListener("grizzly");
		TCPNIOTransport transport = listener.getTransport();
		ThreadPoolConfig threadPoolConfig = ThreadPoolConfig.defaultConfig();
		threadPoolConfig.setCorePoolSize(config.getWorkerCorePool());
		threadPoolConfig.setMaxPoolSize(config.getWorkerMaxPool());
		transport.setWorkerThreadPoolConfig(threadPoolConfig);
		listener.setTransport(transport);
		server.addListener(listener);
	}

	private void bindResource(ResourceConfig resourceConfig) {
		resourceConfig.register(JacksonFeature.class);
		resourceConfig.register(LiveResource.class);
		resourceConfig.register(ArticleResource.class);
	}

	private void bindService(ResourceConfig resourceConfig) {
		resourceConfig.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(config);
				bindFactory(MyBatisSqlSessionFactory.class).to(SqlSessionFactory.class).in(
						Singleton.class);
				bindAsContract(ArticleService.class).in(Singleton.class);
				bindAsContract(LikeService.class).in(Singleton.class);
			}
		});
	}

	public static class MyBatisSqlSessionFactory implements Factory<SqlSessionFactory> {

		private SqlSessionFactory sqlSessionFactory;

		@Inject
		public MyBatisSqlSessionFactory(Config config) {
			BoneCPConfig boneCPConfig = new BoneCPConfig();
			boneCPConfig.setJdbcUrl(config.getUrl());
			boneCPConfig.setUsername(config.getUsername());
			boneCPConfig.setPassword(config.getPassword());
			boneCPConfig.setMinConnectionsPerPartition(config.getMinConnectionsPerPartition());
			boneCPConfig.setMaxConnectionsPerPartition(config.getMaxConnectionsPerPartition());
			boneCPConfig.setPartitionCount(config.getPartitionCount());
			boneCPConfig.setDefaultAutoCommit(config.isAutocommit());
			boneCPConfig.setDefaultReadOnly(config.isReadOnly());
			boneCPConfig.setDefaultTransactionIsolation(config.getIsolation());
			Environment environment =
					new Environment("development", new JdbcTransactionFactory(),
							new BoneCPDataSource(boneCPConfig));
			Configuration configuration = new Configuration(environment);
			configuration.addMapper(ArticleDao.class);
			configuration.addMapper(LikeDao.class);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		}

		@Override
		public SqlSessionFactory provide() {
			return sqlSessionFactory;
		}

		@Override
		public void dispose(SqlSessionFactory instance) {/* no use */
		}

	}
}
