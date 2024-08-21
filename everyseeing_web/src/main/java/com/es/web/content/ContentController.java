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
	
}
