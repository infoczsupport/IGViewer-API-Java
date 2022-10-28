package com.infocz.igviewer.api.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    @Autowired DbMapper dbMapper;

    public List<Map<String, Object>> selectGraphPaths() throws Exception {
        return dbMapper.selectGraphPaths();
    }

    public int setGraphPath(String graph) throws Exception {
        return dbMapper.setGraphPath(graph);
    }

    public Map<String, Object> selectMetaData(Map<String, Object> param, String database, String graph) throws Exception {
        Map<String, Object> metaData = new HashMap<String, Object>();

        try {
            metaData.put("labels", dbMapper.selectLabels(param));
            metaData.put("properties", dbMapper.selectProperties(param));
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
        return dbMapper.selectAgMap(param);
    }
    
    public List<Map<String, Object>>  selectEdgeList(Map<String, Object> param) throws Exception {
        return dbMapper.selectEdgeList(param);
    }
}