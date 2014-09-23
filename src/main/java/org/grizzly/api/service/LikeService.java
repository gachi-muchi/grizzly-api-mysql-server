package org.grizzly.api.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.grizzly.api.dao.LikeDao;
import org.grizzly.api.entity.Like;

public class LikeService {

	@Inject
	private SqlSessionFactory sqlSessionFactory;

	public long like(String id, String userId) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			LikeDao likeDao = session.getMapper(LikeDao.class);
			if (likeDao.select(id, userId) != null) {
				return -1L;
			}
			Like like = new Like();
			like.setToId(id);
			like.setFromId(userId);
			like.setDate(new Date());
			likeDao.insert(like);
			session.commit();
			return likeDao.count(id);
		} finally {
			session.close();
		}
	}

	public long unLike(String id, String userId) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			LikeDao likeDao = session.getMapper(LikeDao.class);
			String likeId = likeDao.select(id, userId);
			if (likeId != null) {
				return -1L;
			}
			likeDao.delete(likeId);
			session.commit();
			return likeDao.count(id);
		} finally {
			session.close();
		}
	}

	public long getCount(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			LikeDao likeDao = session.getMapper(LikeDao.class);
			return likeDao.count(id);
		} finally {
			session.close();
		}
	}

}