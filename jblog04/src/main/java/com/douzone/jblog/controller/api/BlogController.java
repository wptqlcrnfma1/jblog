package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		// System.out.println(list);
		return JsonResult.success(list);
	}

	@ResponseBody
	@PostMapping("/add")
	public JsonResult add(@RequestBody CategoryVo vo) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!add called : " + vo);
		blogService.categoryInsert(vo);
	
		return JsonResult.success(vo);
	}

	@ResponseBody
	@DeleteMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") int no) {
		System.out.println(no);
		boolean result = blogService.categoryDelete(no);
		
		return JsonResult.success(result ? no : -1);
	}

}
