package org.grizzly.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.grizzly.api.entity.Article;

public interface ArticleDao {

	@Insert("INSERT INTO article (user_id, title, body, create_time, update_time)" +
			" VALUES (#{userId}, #{title}, #{body}, #{createTime}, #{updateTime})")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "articleId", before = false, resultType = String.class)
	int insert(Article article);

	@Update("UPDATE article SET title = #{title}, body = #{body}, update_time = #{updateTime}" +
			" WHERE article_id = #{articleId}")
	boolean update(Article article);

	@Delete("DELETE FROM article WHERE article_id = #{articleId}")
	boolean delete(String articleId);

	@Select("SELECT article_id as articleId, user_id as userId, title, body, create_time as createTime, update_time as updateTime" +
			" FROM article WHERE article_id = #{articleId}")
	Article selectById(String articleId);

	@Select("SELECT article_id as articleId, user_id as userId, title, body, create_time as createTime, update_time as updateTime" +
			" FROM article WHERE user_id = #{userId}")
	List<Article> select(String userId);
}
