package com.chenqi.cms.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.chenqi.cms.domain.Article;
//此时自动具备了crud的功能
import com.sun.tools.javac.util.List;
public interface ArticleRes extends ElasticsearchRepository<Article, Integer>{

	List<Article> findByTitle(String key);
	
}
