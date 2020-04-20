package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.CategoryVo;

@Controller("blogApiController")
@RequestMapping("/api/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@ResponseBody
	@RequestMapping("list/{id}")
	public JsonResult check(@PathVariable("id") String id) {
		
		List<CategoryVo> list = blogService.getMessageList(id);
		
		return JsonResult.success(list);
	}

}
