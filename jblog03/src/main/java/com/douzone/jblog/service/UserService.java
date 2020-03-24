package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	public int userJoin(UserVo userVo) {
		int count = userRepository.insert(userVo);
		return count;
	}
	
	public int blogJoin(BlogVo blogVo) {
		int count = blogRepository.insert(blogVo);
		return count;
	}
	
	public int categoryJoin(CategoryVo categoryVo) {
		int count = categoryRepository.insert(categoryVo);
		return count;
	}
	
	public UserVo getUser(UserVo vo) {
		return userRepository.findByPassword(vo);
	}

}
