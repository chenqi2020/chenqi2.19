package com.chenqi.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
import com.chenqi.cms.domain.Complain;
import com.chenqi.cms.domain.Domain;
import com.chenqi.cms.domain.Slide;
import com.chenqi.cms.domain.User;
import com.chenqi.cms.service.ArticleService;
import com.chenqi.cms.service.ChannelService;
import com.chenqi.cms.service.CommentService;
import com.chenqi.cms.service.ComplainService;
import com.chenqi.cms.service.DomainService;
import com.chenqi.cms.service.SlideService;
import com.chenqi.cms.util.CMSException;
import com.github.pagehelper.PageInfo;

@Controller
public class IndexController {

	@Resource
	private ChannelService channelService;

	@Resource
	private ArticleService articleService;

	@Resource
	private SlideService slideService;

	@Resource
	private CommentService commentService;
	
	@Resource
	private ComplainService complainService;
	
	@Resource
	private DomainService domainService;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "", "/", "index" })
	public String index(Model model, Article article, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		// 0封装查询条件
		article.setStatus(1);
		model.addAttribute("article", article);
		// 1.查询出所有的栏目
		List<Channel> channels = channelService.selects();
		model.addAttribute("channels", channels);

		// 如果没有选择栏目,则默认选择推荐的文章
		Article last = new Article();
		if (article.getChannelId() == null) {
			// 1.查询广告
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);

			Article a2 = last;
			a2.setHot(1); // 推荐文章的标志
			a2.setStatus(1); // 审核过的文章
			List range1 = redisTemplate.opsForList().range("hot_articles", 0, -1);
			if(range1==null || range1.size()==0) {
				
				PageInfo<Article> info = articleService.selects(a2, 1, 5);
				
				System.err.println("从mysql中查询的热搜文章----");
				//放入Redis
				redisTemplate.opsForList().leftPushAll("hot_articles", info.getList().toArray());
				model.addAttribute("info", info);
			}else {
				
				System.err.println("从Redis中查询最热文章-----");
				PageInfo<Article> info = articleService.selects(a2, 1, 5);
				model.addAttribute("info", info);
				
			}
			
			// 2.查询栏目下所有的文章
			/*
			 * PageInfo<Article> info = articleService.selects(a2, page, pageSize);
			 * model.addAttribute("info", info);
			 */

		}

		// 如果栏目不为空,则查询栏目下的所有分类
		if (article.getChannelId() != null) {
			List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());

			// 查询栏目下所有的文章
			PageInfo<Article> info = articleService.selects(article, page, pageSize);
			model.addAttribute("info", info);
			model.addAttribute("categorys", categorys);

			// 如果分类不为空,则查询分类下 文章
			if (article.getCategoryId() != null) {
				PageInfo<Article> info2 = articleService.selects(article, page, pageSize);
				model.addAttribute("info", info2);
			}
		}
		// 页面右侧显示最近发布的10篇文章
		Article last1 = new Article();
		last.setStatus(1);
		//这里需要来Redis优化最新文章
		//1.从mysql中查询最新文章 2.判断Redis的最新文章又没有 3.如果为空 4.从mysql中查询并存入Redis然后返回前台
		//5.如果费控 6.直接把Redis中的数据返回给前台
		List<Article> range = redisTemplate.opsForList().range("new_articles", 0, -1);
		
		if(range==null || range.size()==0) {
			PageInfo<Article> lastInfo = articleService.selects(last1, 1, 5);
			System.err.println("从mysql中查询的最新文章----");
			//放入Redis
			redisTemplate.opsForList().leftPushAll("new_articles", lastInfo.getList().toArray());
			model.addAttribute("lastInfo", lastInfo);
		}else {
			
			System.err.println("从Redis中查询最新文章-----");
			PageInfo<Article> lastInfo= new PageInfo<>(range);
			model.addAttribute("lastInfo", lastInfo);
			
		}
		
		
		return "index/index";
	}

		
	@Autowired
	KafkaTemplate<String, String>  kafkaTemplate;
	
	@Autowired
	ThreadPoolTaskExecutor executor;
	
	// 查询单个文章
	@GetMapping("article")
	public String article(Integer id, Model model,HttpServletRequest req) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
			
		//每次增加次数
		//kafkaTemplate.send("1708D","user_view=="+id+"");
		// 查询出评论
		
		//获取用户ip的方法
				String user_ip = req.getRemoteAddr();
//				准备redis的key
				String key = "Hits"+id+user_ip;
				//查询redis中的该key
				String redisKey = (String) redisTemplate.opsForValue().get(key);
				if(redisKey==null) {
					executor.execute(new Runnable() {
						
						@Override
						public void run() {
							//在这里就可以写具体的逻辑了
							//数据库+1操作(根据id从mysql中查询文章对象)
							//设置浏览量+1
							article.setHits(article.getHits()+1);
							//更新到数据库
							articleService.updateByPrimaryKeySelective(article);
							//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟。
							redisTemplate.opsForValue().set(key, "",5, TimeUnit.MINUTES);
						}
					});
				}
				
		Comment comment = new Comment();
		comment.setArticleId(article.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "/index/article";
	}

	/**
	 * 评论
	 * 
	 * @Title: addComment
	 * @Description: TODO
	 * @param comment
	 * @param request
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("addComment")
	public boolean addComment(Comment comment, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 获取session中的用户对象
		User user = (User) session.getAttribute("user");
		if (null == user)
			return false;// 没有登录，不能评论
		comment.setUserId(user.getId());
		comment.setCreated(new Date());
		return commentService.insert(comment) > 0;
	}

	// 去举报
	@GetMapping("complain")
	public String complain(Model model, Article article, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {// 如果有户登录
			article.setUser(user);// 封装举报人和举报的文章
			model.addAttribute("article", article);
			return "index/complain";// 转发到举报页面
		}

		return "redirect:/passport/login";// 没有登录，先去登录
	}

	// 执行举报
	@ResponseBody
	@PostMapping("complain")
	public boolean complain(Model model,MultipartFile file, Complain complain) {
		if (null != file && !file.isEmpty()) {
			String path = "d:/pic/";
			String filename = file.getOriginalFilename();
			String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
			File f = new File(path, newFileName);
			try {
				file.transferTo(f);
				complain.setPicurl(newFileName);

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			complainService.insert(complain);
			return true;
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error",e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error","系统错误,联系管理员");
		}
		return false;
	}
	
	
	/*
	 * @RequestMapping(value = "/1", method = [color=red]RequestMethod.POST[/color])
	 */	
	@ResponseBody
	@PostMapping(value = "colled")
	public boolean colled(Model model, Domain domain) {
		
		try {
			domainService.add(domain);
			return true;
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error",e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error","系统错误,联系管理员");
		}
		return false;
	}
}
