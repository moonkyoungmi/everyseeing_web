package com.es.web.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.web.vo.ResponseMap;

@Service
@Transactional
public class ContentService {

	@Autowired
	private ContentMapper contentMapper;
	
	/**
	 * 장르 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getGenreList(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		List<Map<String, Object>> list = contentMapper.getGenreList(param);
		respMap.setBody("list", list);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 콘텐츠 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getContentList(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();

		List<Map<String, Object>> list = contentMapper.getMovieContentList(param);
		
		List<List<Map<String, Object>>> data = new ArrayList<>();
		
		// 5개씩 묶기
		int limit = 5;
		for(int i = 0; i < list.size(); i += limit) {
			int index = i + limit;
			if(index > list.size()) {
				index = list.size();
			}
			List<Map<String, Object>> subList = list.subList(i, index);
			data.add(subList);
		}
		
		respMap.setBody("list", data);
		respMap.setBody("total", contentMapper.getMovieContentCnt(param));
		
		return respMap.getResponseMap();
	}
}
