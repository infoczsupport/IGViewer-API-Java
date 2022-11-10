package com.infocz.igviewer.api.servive.rdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.mapper.rdb.RdbMapper;

@Service
public class RdbService {
    @Autowired RdbMapper rdbMapper;

    public List<Map<String, Object>> selectTableInfo() throws Exception {
        return rdbMapper.selectTableInfo();
    }  
    
    public Map<String, Object> getTableData(String tableNm) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("tableNm", tableNm);
        result.put("columns", rdbMapper.selectColumns(tableNm));
        result.put("rows", rdbMapper.selectTableLimit(tableNm));
        result.put("totalCnt", rdbMapper.selectTotalCnt(tableNm));
        return result;
    }  
    
}