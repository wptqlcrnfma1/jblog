package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int postInsert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}

	public List<PostVo> selectTitleList(int no) {
		List<PostVo> list = sqlSession.selectList("post.selectTitle", no);
		return list;
	}

	public PostVo selectTitleContents(int no) {
		return sqlSession.selectOne("post.selectTitleContents", no);
	}
	
	public List<PostVo> selectLatelyTitleList(String id, int categoryNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "id", id );
		map.put( "categoryNo", categoryNo );
		
		List<PostVo> list = sqlSession.selectList("post.selectLatelyTitle",map);
		return list;
	}

	public PostVo selectLatelyTitleContents(String id) {
		return sqlSession.selectOne("post.selectLatelyTitleContents",id);
	}
}
