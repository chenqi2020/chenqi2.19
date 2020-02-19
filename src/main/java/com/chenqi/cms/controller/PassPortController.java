package com.chenqi.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chenqi.cms.domain.User;
import com.chenqi.cms.service.UserService;
import com.chenqi.cms.util.CMSException;
import com.chenqi.cms.util.CookieUtil;
import com.chenqi.utils.StringUtil;

@RequestMapping("passport")
@Controller
public class PassPortController {

	@Resource
	private UserService userService;

	// 去登录页面
	@GetMapping("login")
	public String login() {
		return "passport/login";
	}

	// 去注册页面
	@GetMapping("reg")
	public String reg() {
		return "passport/reg";
	}

	// 执行注册
	@PostMapping("reg")
	public String reg(Model model, User user, RedirectAttributes redirectAttributes) {
		try {
			int i = userService.insertSelective(user);
			if (i > 0) {
				// 使用redirectAttributes对象用来重定向携带参数
				redirectAttributes.addFlashAttribute("username", user.getUsername());
				return "redirect:/passport/login"; // 重定向登录页面
			}
		} catch (CMSException e) {// catch自定义异常
			e.printStackTrace();// 给程序员找错
			// 获取并封装消息
			model.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();// 给程序员找错
			model.addAttribute("error", "系统错误,请联系管理员");
		}
		model.addAttribute("user", user);
		return "passport/reg"; // 注册失败
	}

	// 执行登录
	@PostMapping("login")
	public String login(Model model,User user, HttpSession session,HttpServletResponse response) {
		try {
			User u = userService.login(user);
			
			// 如果勾选了[十天免登陆]
			if (StringUtil.hasText(user.getIsRemember())) {
				// 姓名在cookie里存十天
				CookieUtil.addCookie(response, "username", u.getUsername(), 60 * 60 * 24 * 10);// 存10天
				// 密码在cookie里存十天
				CookieUtil.addCookie(response, "password", u.getPassword(), 60 * 60 * 24 * 10);// 存10天
			}
			
			// 根据角色进入不同的页面
			if ("0".equals(u.getRole())) {// 普通用户
				// 登陆成功
				session.setAttribute("user",u);
				return "redirect:/my";
			} else {
				// 登陆成功
				session.setAttribute("admin",u);
				return "redirect:/admin"; // 管理员
			}
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error",e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error","系统异常,请与管理员联系");
		}

		return "passport/login";
	}
	
	// 注销  注销时清空cookie值
	@GetMapping("logout")
	public String logout(HttpSession session,HttpServletRequest request, HttpServletResponse resp) {
		 //让cookie删除
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				// System.out.println("cookie.getName():"+cookie.getName());
				// 如果cookie有存值那么就删除cookie
				if (cookie.getName().equals("username")) {
					cookie.setMaxAge(0);//cookie的存活时间。 0：删除cookie
					cookie.setPath("/");
					resp.addCookie(cookie);
				}
			}
		}
		// 删除session
		session.invalidate();
		return "redirect:/passport/login";
	}
}
