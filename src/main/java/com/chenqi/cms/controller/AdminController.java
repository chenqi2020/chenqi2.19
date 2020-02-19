package com.chenqi.cms.controller;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chenqi.cms.dao.ArticleRes;
import com.chenqi.cms.domain.Article;
import com.chenqi.cms.domain.ArticleWithBLOBs;
import com.chenqi.cms.domain.Complain;
import com.chenqi.cms.domain.Domain;
import com.chenqi.cms.domain.User;
import com.chenqi.cms.service.ArticleService;
import com.chenqi.cms.service.ComplainService;
import com.chenqi.cms.service.DomainService;
import com.chenqi.cms.service.UserService;
import com.chenqi.cms.vo.ComplainVO;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.util.List;

@RequestMapping("admin")
@Controller
public class AdminController {

	@Resource
	private UserService userService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ComplainService complainService;
	@Resource
	private DomainService domainService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private KafkaTemplate kafkaTemplate ;
	
	/**
	 * @Title: index 
	 * @Description: 进入后台首页
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"","/","index"})
	public String index() {
		return "admin/index";
	}
	
	/**
	 * @Title: articles 
	 * @Description: 文章列表
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	
	
	@GetMapping("article/selects")
	public String articles(Model model,Article article,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize) {
		
			// 默认文章审核未待审
			if(article.getStatus() == null) {
				article.setStatus(0);
			}
			
			PageInfo<Article> info = articleService.selects(article, page, pageSize);
			model.addAttribute("info", info);
			model.addAttribute("article", article);
			return "admin/article/articles";
	}
	
	/**
	 * @Title: selects 
	 * @Description: 用户列表(User)
	 * @param model
	 * @param username
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@GetMapping("user/selects")
	public String selects(Model model,String username,
			@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<User> info = userService.selects(username, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("username", username);
		
		return "admin/user/users";
	}
	
	/**
	 * @Title: update 
	 * @Description: 修改用户
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@RequestMapping("user/update")
	public boolean update(User user) {
		return userService.updateByPrimaryKeySelective(user)>0;
	}
	
	/**
	 * @Title: select 
	 * @Description: 查看文章详情
	 * @return
	 * @return: String
	 */
	@GetMapping("article/select")
	public String select(Integer id,Model model) {
		ArticleWithBLOBs a = articleService.selectByPrimaryKey(id);
		model.addAttribute("a",a);
		return "admin/article/article";
	}
	
	// 审核文章
	/**
	 * @Title: update 
	 * @Description: 审核文章
	 * @return
	 * @return: boolean
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		
		if(article.getStatus() != null) {
			if(article.getStatus()==1) {
				ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(article.getId());
				String jsonString = JSON.toJSONString(selectByPrimaryKey);
				kafkaTemplate.send("1708D", "add"+jsonString);
				
			}else if(article.getStatus()== -1) {
				
				String jsonString = JSON.toJSONString(article);
				kafkaTemplate.send("1708D", "del"+jsonString);
			}
			
			
		}
		
		return articleService.updateByPrimaryKeySelective(article)>0;
	}
	
	//查询投诉
	@GetMapping("article/complains")
	public String complain(Model model ,ComplainVO complainVO , @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		
		PageInfo<Complain> info = complainService.selects(complainVO, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("ComplainVO",complainVO);
		return "admin/article/complains";
	}
	//收藏
	@GetMapping("article/collect")
	public String collect(Model model,@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize,ComplainVO complainVO) {
		PageInfo<Domain> info = domainService.list(page, pageSize,complainVO);
		model.addAttribute("info", info);
		
		return "admin/article/collect";
	}
	
	//删除
	@GetMapping("article/dele")
	public String dele(Integer did) {
		domainService.dele(did);
		
		return "admin/article/collect";
	}
	
}
