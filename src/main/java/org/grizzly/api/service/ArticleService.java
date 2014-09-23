package org.grizzly.api.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.grizzly.api.dao.ArticleDao;
import org.grizzly.api.entity.Article;

public class ArticleService {

	@Inject
	private SqlSessionFactory sqlSessionFactory;

	public Article create(String userId, Article article) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ArticleDao articleDao = session.getMapper(ArticleDao.class);
			article.setUserId(userId);
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			articleDao.insert(article);
			session.commit();
			return article;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public boolean update(String id, Article update) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ArticleDao articleDao = session.getMapper(ArticleDao.class);
			Article article = articleDao.selectById(id);
			article.setTitle(update.getTitle());
			article.setBody(update.getBody());
			session.commit();
			return articleDao.update(article);
		} finally {
			session.close();
		}
	}

	public List<Article> list(String userId, int offset, int limit) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ArticleDao articleDao = session.getMapper(ArticleDao.class);
			return articleDao.select(userId);
		} finally {
			session.close();
		}
	}

	public Article read(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ArticleDao articleDao = session.getMapper(ArticleDao.class);
			return articleDao.selectById(id);
		} finally {
			session.close();
		}
	}

	public boolean delete(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ArticleDao articleDao = session.getMapper(ArticleDao.class);
			if (articleDao.delete(id)) {
				session.commit();
			}
			return true;
		} finally {
			session.close();
		}
	}
}
