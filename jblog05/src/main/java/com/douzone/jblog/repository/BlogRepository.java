package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BlogVo blogVo) {
		return sqlSession.insert("blog.insert", blogVo);
	}
	
	public BlogVo findById( String id ) {
		return sqlSession.selectOne( "blog.findById", id );
	}

	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}
	
	
}
