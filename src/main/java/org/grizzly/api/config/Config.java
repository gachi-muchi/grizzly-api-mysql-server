package org.grizzly.api.config;

public class Config {

	private String baseUrl = "http://localhost";

	private int port = 9000;

	private int workerCorePool = 64;

	private int workerMaxPool = 64;

	private String driverClassName = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://10.34.48.41:3306/article?characterEncoding=utf-8";

	private String username = "db_user";

	private String password = "db_user";

	private int partitionCount = 1;

	private int maxConnectionsPerPartition = 10;

	private int minConnectionsPerPartition = 5;

	private boolean autocommit = false;

	private boolean readOnly = false;

	private String isolation = "READ_COMMITTED";

	private int acquireIncrement = 1;

	private int acquireRetryAttempts = 1;

	private String acquireRetryDelay = "5 seconds";

	private int connectionTimeout = 1000;

	private int idleMaxAge = 100;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getWorkerCorePool() {
		return workerCorePool;
	}

	public void setWorkerCorePool(int workerCorePool) {
		this.workerCorePool = workerCorePool;
	}

	public int getWorkerMaxPool() {
		return workerMaxPool;
	}

	public void setWorkerMaxPool(int workerMaxPool) {
		this.workerMaxPool = workerMaxPool;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}

	public int getMaxConnectionsPerPartition() {
		return maxConnectionsPerPartition;
	}

	public void setMaxConnectionsPerPartition(int maxConnectionsPerPartition) {
		this.maxConnectionsPerPartition = maxConnectionsPerPartition;
	}

	public int getMinConnectionsPerPartition() {
		return minConnectionsPerPartition;
	}

	public void setMinConnectionsPerPartition(int minConnectionsPerPartition) {
		this.minConnectionsPerPartition = minConnectionsPerPartition;
	}

	public boolean isAutocommit() {
		return autocommit;
	}

	public void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getIsolation() {
		return isolation;
	}

	public void setIsolation(String isolation) {
		this.isolation = isolation;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public String getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	public void setAcquireRetryDelay(String acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getIdleMaxAge() {
		return idleMaxAge;
	}

	public void setIdleMaxAge(int idleMaxAge) {
		this.idleMaxAge = idleMaxAge;
	}

}
