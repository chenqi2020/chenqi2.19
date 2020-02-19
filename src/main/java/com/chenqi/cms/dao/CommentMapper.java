package com.chenqi.cms.dao;

import java.util.List;

import com.chenqi.cms.domain.Comment;

public interface CommentMapper {

	/**
	 * @Title: insert 
	 * @Description: 添加评论
	 * @param comment
	 * @return
	 * @return: int
	 */
	int insert(Comment comment);
	
	/**
	 * @Title: selects 
	 * @Description: 查询评论
	 * @param comment
	 * @return
	 * @return: List<Comment>
	 */
	List<Comment> selects(Comment comment);
}
