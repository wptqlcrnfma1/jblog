package com.douzone.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model, BlogVo blogVo,
			CategoryVo categoryVo) {

		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel()); // map으로 return된다 jsp로 간다 > 스프링 태그를 사용해서 jsp에서 처리하도록
			return "user/join";
		}

		userService.userJoin(userVo);
		userService.blogJoin(blogVo);
		userService.categoryJoin(categoryVo);

		return "user/joinsuccess";
	}

	// 절대 오진 않지만 핸들러에 url이 있어야 인터셉터가 된다.
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public void auth() {

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {

	}

}
