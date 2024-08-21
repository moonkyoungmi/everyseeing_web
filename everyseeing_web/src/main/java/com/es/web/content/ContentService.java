package com.es.web.content;

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
}
