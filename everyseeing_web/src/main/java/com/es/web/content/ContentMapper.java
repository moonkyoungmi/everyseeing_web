package com.es.web.content;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper {

	// 장르 리스트
	public List<Map<String, Object>> getGenreList(Map<String, Object> param);

	// 영화 콘텐츠 리스트
	public List<Map<String, Object>> getMovieContentList(Map<String, Object> param);

	// 영화 콘텐츠 개수
	public int getMovieContentCnt(Map<String, Object> param);
	
}
