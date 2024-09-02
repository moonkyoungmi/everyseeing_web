package com.es.web.content;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper {

	// 장르 리스트
	public List<Map<String, Object>> getGenreList(Map<String, Object> param);

	// 콘텐츠 리스트
	public List<Map<String, Object>> getContentList(Map<String, Object> param);

	// 콘텐츠 개수
	public int getContentCnt(Map<String, Object> param);
	
	// 북마크 콘텐츠 리스트
	public List<Map<String, Object>> getBookmarkContentList(Map<String, Object> param);

	// 북마크 콘텐츠 개수
	public int getBookmarkContentCnt(Map<String, Object> param);
	
	// TV 콘텐츠 회차 리스트
	public List<Map<String, Object>> getContentRoundList(Map<String, Object> param);
	
	// 찜한 콘텐츠 삭제
	public int deleteBookmarkContent(Map<String, Object> param);
}
