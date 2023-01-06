package com.infocz.infoCruise.api.mapper.gdb;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MonitorMapper {
     Map<String, Object> selectMonitor();     
}

