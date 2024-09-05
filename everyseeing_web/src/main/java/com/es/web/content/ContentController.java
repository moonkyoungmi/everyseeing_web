package com.es.web.content;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

@RestController
@RequestMapping("/api/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 장르 리스트
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/genre/list")
	public Map<String, Object> getGenreList(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return contentService.getGenreList(param);
	}
	
	/**
	 * 콘텐츠 리스트
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	public Map<String, Object> getContentList(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return contentService.getContentList(param);
	}
	
	/**
	 * 북마크
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/bookmark")
	public Map<String, Object> bookmark(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return contentService.bookmark(param);
	}
	
	/**
	 * 콘텐츠 상세
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/detail")
	public Map<String, Object> getDetail(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return contentService.getDetail(param);
	}
}
