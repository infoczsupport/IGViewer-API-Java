package com.infocz.igviewer.api.servive.rdb2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.mapper.rdb2.Rdb2Mapper;

@Service
public class Rdb2Service {
    @Autowired Rdb2Mapper rdb2Mapper;

    public List<Map<String, Object>> selectEmployee() throws Exception {
        return rdb2Mapper.selectEmployee();
    }  
   
}