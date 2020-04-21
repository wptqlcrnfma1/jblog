package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.security.Auth;

@Controller
@RequestMapping("blog/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Auth
	@RequestMapping({ "", "/{pathNo1}", "/{pathNo1}/{pathNo2}" })
	public String main(@PathVariable String id, @ModelAttribute BlogVo blogVo, Model model, 
			@PathVariable Optional<Integer> pathNo1, @PathVariable Optional<Integer> pathNo2, ModelMap modelMap) {

		blogVo = blogService.getContents(id);
		model.addAttribute("blogVo", blogVo);
		
		int categoryNo = 0;
		int postNo = 0;
		
		if (pathNo2.isPresent()) {
			postNo = pathNo2.get();

			categoryNo = pathNo1.get();
			
		} else if (pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}
		
		
		modelMap.putAll(blogService.getAll(id, categoryNo, postNo));
		
		return "blog/blog-main";
	}

	@Auth
	@RequestMapping("/admin")
	public String admin(@PathVariable String id, @ModelAttribute BlogVo blogVo, Model model) {
		blogVo = blogService.getContents(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}

	@Auth
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public String adminUpdate(@PathVariable String id, @ModelAttribute BlogVo blogVo, Model model,
			@RequestParam(value = "file") MultipartFile multipartFile) {

		String logo = blogService.restore(multipartFile);
		blogVo.setLogo(logo);

		blogService.updateContents(blogVo);

		model.addAttribute("blogVo", blogVo);
		return "redirect:/blog/" + id;
	}

	@Auth
	@RequestMapping("/category")
	public String category(@PathVariable String id, Model model) {
		
		List<CategoryVo> list = blogService.categoryGetContents(id);
		
		model.addAttribute("id", id);
		
		model.addAttribute("list", list);

		return "blog/blog-admin-category";
	}

	@Auth
	@RequestMapping(value = "{id}/category", method = RequestMethod.POST)
	public String category(@PathVariable String id, @Param("name") String name,
			@Param("description") String description, CategoryVo categoryVo) {

		blogService.categoryInsert(categoryVo);

		return "redirect:/blog/" + id + "/category";
	}

	@Auth
	@RequestMapping("/write")
	public String write(@PathVariable String id, Model model) {
		List<CategoryVo> list = blogService.categoryGetContents(id);

		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@PathVariable String id, PostVo postVo, @ModelAttribute CategoryVo categoryVo) {

		blogService.postInsert(postVo);

		return "redirect:/blog/" + id + "/category";
	}

	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable String id, @PathVariable("no") int no) {
		blogService.categoryDelete(no);

		return "redirect:/blog" + id + "/category";
	}
}
