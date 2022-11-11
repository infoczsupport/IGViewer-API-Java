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

    public List<Map<String, Object>> selectTableList() throws Exception {
        return rdbMapper.selectTableList();
    }  
    
    public Map<String, Object> getTableData(String tableNm) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableNm", tableNm);
        param.put("limit", 10);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("tableNm", tableNm);
        result.put("columns", rdbMapper.selectColumns(tableNm));
        result.put("rows", rdbMapper.selectTableData(param));
        result.put("totalCnt", rdbMapper.selectTotalCnt(tableNm));
        return result;
    }  
    
}