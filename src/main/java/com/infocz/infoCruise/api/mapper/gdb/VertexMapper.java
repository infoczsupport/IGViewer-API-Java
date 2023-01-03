package com.infocz.infoCruise.api.mapper.gdb;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VertexMapper {
     int createVertex(String vertexNm);
     int dropVertex(String vertexNm);     
     int insertVertex(Map<String, Object> param);
     
     ArrayList<Map<String, Object>> selectMetaVertex(String graph);   
     int insertMetaVertex(Map<String, Object> param);
     int deleteMetaVertex(Map<String, Object> param);
}