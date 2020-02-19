package com.chenqi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chenqi.cms.dao.ArticleMapper;
import com.chenqi.cms.domain.Article;
import com.chenqi.cms.domain.ArticleWithBLOBs;
import com.chenqi.cms.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Resource
	private ArticleMapper articleMapper;

	@Override
	public PageInfo<Article> selects(Article article,Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Article> list = articleMapper.selects(article);
		return new PageInfo<Article>(list);
	}

	@Override
	public int insertSelective(ArticleWithBLOBs record) {
		return articleMapper.insertSelective(record);
	}

	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ArticleWithBLOBs record) {
		return articleMapper.updateByPrimaryKeySelective(record);
	}
}
