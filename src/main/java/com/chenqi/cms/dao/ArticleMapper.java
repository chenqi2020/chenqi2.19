package com.chenqi.cms.dao;

import java.util.List;

import com.chenqi.cms.domain.Article;
import com.chenqi.cms.domain.ArticleWithBLOBs;

public interface ArticleMapper {
	/**
	 * @Title: selects 
	 * @Description: 查询文章
	 * @param title
	 * @param channelId
	 * @return
	 * @return: List<Article>
	 */
	List<Article> selects(Article article);
	
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);

    int updateComplainnum(Integer articleId);//更新举报数量
    
}