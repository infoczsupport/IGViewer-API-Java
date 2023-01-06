package com.infocz.infoCruise.api.mapper.gdb;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GdbMapper {
     ArrayList<Map<String, Object>> selectGraphPaths();

     Map<String, Object> setGraphPath();

     int setGraphPath(String graph);

     ArrayList<Map<String, Object>> selectLabels(Map<String, Object> param);
     ArrayList<Map<String, Object>> selectProperties(Map<String, Object> param);
     
     ArrayList<Map<String, Object>> selectAgMap(Map<String, Object> param);   
     
     ArrayList<Map<String, Object>> selectEdgeList(Map<String, Object> param);   
 
     int callSpAgMap();
     int callSpAgProperties();

     int insertAgConvertMeta(Map<String, Object> param);
     int deleteAgConvertMeta(Map<String, Object> param);     
     ArrayList<Map<String, Object>> selectAgConvertMeta(Map<String, Object> param);
}

