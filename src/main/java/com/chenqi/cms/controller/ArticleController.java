package com.chenqi.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.chenqi.cms.dao.ArticleRes;
import com.chenqi.cms.domain.Article;
import com.chenqi.cms.util.HLUtils;

@RequestMapping("article")
@Controller
public class ArticleController {

	@Autowired
	ArticleRes articleRes;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	@RequestMapping("search")
	public String search(String key,Model model,@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "2") Integer pageSize) {
		// 从es索引库查询    (只完成普通的搜索无高亮)
		/*List<Article> list = articleRes.findByTitle(key);
		PageInfo<Article> info = new PageInfo<>(list);
		model.addAttribute("info",info);*/
		// 高亮搜索HighLight
		long start = System.currentTimeMillis();
		PageInfo<Article> info = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, pageSize, new String[] {"title"}, "id", key);
		long end = System.currentTimeMillis();
		System.err.println("es查询一共花费了"+(end-start)+"毫秒");
		model.addAttribute("key", key);
		model.addAttribute("info",info);
		return "index/index";
	}
}
