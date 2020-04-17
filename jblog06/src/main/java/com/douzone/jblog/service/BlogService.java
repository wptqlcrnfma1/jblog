package com.douzone.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {
	private static final String SAVE_PATH = "/jblog-uploads"; // 리눅스 식으로 내꺼는 c드라이브에 올라간다.
	private static final String URL = "/images";

	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepository postRepository;

	public BlogVo getContents(String id) {
		BlogVo blogVo = blogRepository.findById(id);
		return blogVo;
	}

	public int updateContents(BlogVo blogVo) {
		int count = blogRepository.update(blogVo);
		return count;
	}

	public String restore(MultipartFile multipartFile) {
		String url = "";

		try {
			// 핵심! 컨트롤러에서 넘어온 파일을 처리하는 부분

			if (multipartFile.isEmpty()) {
				return url;
			}

			String originFilename = multipartFile.getOriginalFilename();

			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1); // 확장자만 가져오기

			String saveFilename = gernerateSaveFilename(extName);

			long fileSize = multipartFile.getSize();

			System.out.println("############# " + originFilename);
			System.out.println("############# " + fileSize);
			System.out.println("############# " + saveFilename);

			byte[] fileData = multipartFile.getBytes();

			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename); // 파일 오픈
			os.write(fileData);
			os.close();

			url = URL + "/" + saveFilename;

		} catch (IOException e) {
			throw new RuntimeException("file upload error : " + e); // 나중에 mysite적용할때는 fileuploadException 만들어서 던져야 한다.
		}

		return url;

	}

	private String gernerateSaveFilename(String extName) {
		String filename = "";

		// 시간을 넣어서 saveFilename을 생성한다.
		// 시간+확장자명 = saveFilename

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}

	public int categoryInsert(CategoryVo categoryVo) {
		int count = categoryRepository.categoryInsert(categoryVo);
		return count;
	}

	public List<CategoryVo> categoryGetContents(String id) {
		return categoryRepository.selectList(id);
	}

	public int postInsert(PostVo postVo) {
		int count = postRepository.postInsert(postVo);
		return count;
	}

	public boolean categoryDelete(int no) {
		int count = categoryRepository.delete(no);
		return count == 1;
	}

	public List<PostVo> postGetTitle(int no) {
		return postRepository.selectTitleList(no);
	}

	public Map<String, Object> getAll(String id, int categoryNo, int postNo) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<CategoryVo> categoryList = categoryRepository.selectTitleList(id); // 카테고리 제목 리스트

		List<PostVo> postList = new ArrayList<PostVo>();
		PostVo postVo;

		if (categoryNo == 0 && postNo ==0) { // 첫 메인
			postList = postRepository.selectLatelyTitleList(id, categoryNo);
			postVo = postRepository.selectLatelyTitleContents(id);
			
		}else if(categoryNo != 0 && postNo == 0) { 
			postList = postRepository.selectLatelyTitleList(id, categoryNo);
			postVo = postRepository.selectLatelyTitleContents(id);
		}
		else {
			postList = postRepository.selectTitleList(categoryNo);
			postVo = postRepository.selectTitleContents(postNo);
		}

		map.put("postVo", postVo); //title, contents
		map.put("postList", postList);
		map.put("categoryList", categoryList); // id로 categoryList 찾는거

		return map;

	}
}
