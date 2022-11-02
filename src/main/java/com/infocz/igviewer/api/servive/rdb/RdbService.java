package com.infocz.igviewer.api.servive.rdb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.mapper.rdb.RdbMapper;

@Service
public class RdbService {
    @Autowired RdbMapper rdbMapper;

    public List<Map<String, Object>> selectEmployee() throws Exception {
        return rdbMapper.selectEmployee();
    }

    public List<Map<String, Object>> selectTableInfo() throws Exception {
        return rdbMapper.selectTableInfo();
    }    
}