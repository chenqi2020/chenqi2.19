package com.chenqi.cms.service;

import java.util.List;

import com.chenqi.cms.domain.Article;
import com.chenqi.cms.domain.ArticleWithBLOBs;
import com.github.pagehelper.PageInfo;

public interface ArticleService {

	/**
	 * @Title: selects 
	 * @Description: 查询文章
	 * @param title
	 * @param channelId
	 * @return
	 * @return: List<Article>
	 */
	PageInfo<Article> selects(Article article, Integer page, Integer pageSize);
	
	/**
	 * @Title: insertSelective 
	 * @Description: 增加文章
	 * @param record
	 * @return
	 * @return: int
	 */
	 int insertSelective(ArticleWithBLOBs record);
	 
	 /**
	  * @Title: selectByPrimaryKey 
	  * @Description: 单个文章
	  * @param id
	  * @return
	  * @return: ArticleWithBLOBs
	  */
	 ArticleWithBLOBs selectByPrimaryKey(Integer id);
	 
	 /**
	 * 
	 * @Title: updateByPrimaryKeySelective 
	 * @Description: 修改文章
	 * @param record
	 * @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(ArticleWithBLOBs record);	
}
