package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}

	public int categoryInsert(CategoryVo categoryVo) {
		return sqlSession.insert("category.categoryInsert", categoryVo);
	}

	public List<CategoryVo> selectList(String id) { //카테고리 글쓰기
		List<CategoryVo> list = sqlSession.selectList("category.selectAll", id);
		return list;
	}
	
	
	public List<CategoryVo> selectTitleList(String id) { //블로그 메인 화면
		List<CategoryVo> list = sqlSession.selectList("category.selectTitle", id);
		return list;
	}
	
	public int delete(int no) {
		return sqlSession.delete("category.delete", no);
	}

	
	

}
