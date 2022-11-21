package com.infocz.igviewer.api.mapper.rdb2;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Rdb2Mapper {
     ArrayList<Map<String, Object>> selectEmployee();
}