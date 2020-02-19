package com.chenqi.cms.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chenqi.cms.domain.Article;
import com.chenqi.cms.domain.ArticleWithBLOBs;
import com.chenqi.cms.domain.Category;
import com.chenqi.cms.domain.Channel;
import com.chenqi.cms.domain.Comment;
import com.chenqi.cms.domain.User;
import com.chenqi.cms.service.ArticleService;
import com.chenqi.cms.service.ChannelService;
import com.chenqi.cms.service.CommentService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: MyController 
 * @Description: 个人中心
 * @author:wts 
 * @date: 2019年12月12日 下午3:04:08
 */
@RequestMapping("my")
@Controller
public class MyController {

	@Resource
	private ChannelService  channelService;
	@Resource
	private ArticleService  articleService;
	@Resource
	private CommentService  commentService;
	
	// 个人中心首页
	@RequestMapping(value = {"","/","index"})
	public String index() {
		return "my/index";
	}
	
	/**
	 * @Title: publish 
	 * @Description: 发布文章
	 * @return
	 * @return: String
	 */
	@GetMapping("article/publish")
	public String publish() {
		return "my/article/publish";
	}
	
	/**
	 * @Title: selectChannels 
	 * @Description: 所有栏目
	 * @return
	 * @return: List<Channel>
	 */
	@ResponseBody
	@GetMapping("channel/selects")
	public List<Channel> selectChannels(){
		return channelService.selects();
	}
	
	/**
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Category>
	 */
	@ResponseBody
	@GetMapping("category/selectsByChannelId")
	public List<Category> selectsByChannelId(Integer channelId){
		return channelService.selectsByChannelId(channelId);
	}
	
	/**
	 * @Title: publish 
	 * @Description: 发布文章
	 * @param file
	 * @param article
	 * @return
	 * @throws Exception
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("article/publish")
	public boolean publish(HttpServletRequest request,MultipartFile file,ArticleWithBLOBs article) throws Exception {
		String path = "D:/pic";
		if(!file.isEmpty()) {
			// 获取原始文件名称
			String filename = file.getOriginalFilename();
			// 防止文件重名
			String newFilename = UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
			// 把文件写入硬盘
			file.transferTo(new File(path,newFilename));
			// 数据库存储文件地址
			article.setPicture(newFilename);
		}
		//  存值
		article.setCreated(new Date());// 发布时间
		article.setStatus(0);// 文章状态  0待审核
		article.setHits(0);  // 点击量
		article.setDeleted(0); // 是否删除  0 未删除
		article.setUpdated(new Date()); // 更新时间
		// 从session获取当前登录人信息
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId()); //用户id
		article.setHot(0);  // 非热门
		
		return articleService.insertSelective(article)>0;
	}
	
	/**
	 * @Title: articles 
	 * @Description: 我的文章列表
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@GetMapping("article/articles")
	public String articles(HttpServletRequest request,Model model,Article article,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize) {
		
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId()); //只查询当前用户的文章
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		model.addAttribute("info",info);
		return "my/article/articles";
	}
	
	/**
	 * @Title: article 
	 * @Description: 文章详情
	 * @return
	 * @return: String
	 */
	@GetMapping("article/article")
	public String article(Integer id,Model model) {
		ArticleWithBLOBs a = articleService.selectByPrimaryKey(id);
		model.addAttribute("a",a);
		return "my/article/article";
	}
	
	/**
	 * @Title: update 
	 * @Description: 修改文章
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@RequestMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		return articleService.updateByPrimaryKeySelective(article)>0;
	}
	
	/**
	 * 	查询评论
	 * @Title: comments 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("article/comments")
	public String comments(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment();
		comment.setUserId(user.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "my/article/comments";
	}
}
