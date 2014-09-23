package org.grizzly.api.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.grizzly.api.entity.Like;

public interface LikeDao {

	@Insert("INSERT INTO t_like (to_id, from_id, date)" +
			" VALUES (#{toId}, #{fromId}, #{date})")
	int insert(Like like);

	@Delete("DELETE FROM t_like WHERE id = #{id}")
	boolean delete(String id);

	@Select("SELECT count(id) FROM t_like WHERE to_id = #{toId}")
	long count(String toId);

	@Select("SELECT id FROM t_like WHERE to_id = #{toId} AND from_id = #{fromId}")
	String select(@Param("toId") String toId, @Param("fromId") String fromId);
}
