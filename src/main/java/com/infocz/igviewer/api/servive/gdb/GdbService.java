package com.infocz.igviewer.api.servive.gdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.mapper.gdb.GdbMapper;

@Service
public class GdbService {
    @Autowired GdbMapper gdbMapper;
    
    public List<Map<String, Object>> selectGraphPaths() throws Exception {
        return gdbMapper.selectGraphPaths();
    }    

    public int setGraphPath(String graph) throws Exception {
        return gdbMapper.setGraphPath(graph);
    }

    public Map<String, Object> selectMetaData(Map<String, Object> param, String database, String graph) throws Exception {
        Map<String, Object> metaData = new HashMap<String, Object>();

        try {
            metaData.put("labels", gdbMapper.selectLabels(param));
            metaData.put("properties", gdbMapper.selectProperties(param));
            metaData.put("database", database);
            metaData.put("graph", graph);
            // metaData.put("role", dbMapper.selectRole());
            metaData.put("role", null);
        } catch (Exception e) {
            throw e;
        }
        return metaData;
    }

    public List<Map<String, Object>>  selectAgMap(Map<String, Object> param) throws Exception {
        return gdbMapper.selectAgMap(param);
    }
    
    public List<Map<String, Object>>  selectEdgeList(Map<String, Object> param) throws Exception {
        return gdbMapper.selectEdgeList(param);
    }
}