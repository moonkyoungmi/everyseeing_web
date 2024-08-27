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

		String menu = (String) param.get("menu");
		List<Map<String, Object>> list = new ArrayList<>();
		int total = 0;
	
		if(menu == null || menu.equals("")) {
			menu = "A";
		}
		
		if(menu.equals("A") || menu.equals("M") || menu.equals("T") || menu.equals("N")) {
			// 일반 콘텐츠
			list = contentMapper.getContentList(param);
			total = contentMapper.getContentCnt(param);
		} else if(menu.equals("B")) {
			// 찜한 콘텐츠
			list = contentMapper.getBookmarkContentList(param);
			total = contentMapper.getBookmarkContentCnt(param);
		}

		// TV 콘텐츠 회차 매핑
		for(Map<String, Object> content : list) {
			String category = (String) content.get("category");
			
			if(category.equals("TV")) {
				List<Map<String, Object>> roundList = contentMapper.getContentRoundList(content);
				content.put("round_list", roundList);
			}
		}
		
		List<List<Map<String, Object>>> resultList = new ArrayList<>();
		
		// 5개씩 묶기
		int limit = 5;
		for(int i = 0; i < list.size(); i += limit) {
			int index = i + limit;
			if(index > list.size()) {
				index = list.size();
			}
			List<Map<String, Object>> subList = list.subList(i, index);
			resultList.add(subList);
		}
		
		respMap.setBody("list", resultList);
		respMap.setBody("total", total);
		
		return respMap.getResponseMap();
	}
}
