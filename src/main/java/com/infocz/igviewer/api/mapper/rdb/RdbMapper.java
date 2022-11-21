package com.infocz.igviewer.api.mapper.rdb;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RdbMapper {
     ArrayList<Map<String, Object>> selectTableList();

     Integer selectTotalCnt(String tableNm);
     ArrayList<Map<String, Object>> selectColumns(Map<String, Object> param);
     
     ArrayList<Map<String, Object>> selectTableData(Map<String, Object> param);
}