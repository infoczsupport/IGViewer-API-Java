package com.infocz.infoCruise.api.servive.gdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.infoCruise.api.common.Utils;
import com.infocz.infoCruise.api.mapper.gdb.GdbMapper;
import com.infocz.infoCruise.api.mapper.gdb.VertexMapper;
import com.infocz.infoCruise.api.mapper.rdb.RdbMapper;

@Service
public class GdbService {
    @Autowired GdbMapper gdbMapper;
    @Autowired VertexMapper vertexMapper;
    @Autowired RdbMapper rdbMapper;
    
    public List<Map<String, Object>> selectGraphPaths() throws Exception {
        return gdbMapper.selectGraphPaths();
    }    

    public int setGraphPath(String graph) throws Exception {
        return gdbMapper.setGraphPath(graph);
    }

    public Map<String, Object> selectMetaData(Map<String, Object> param) throws Exception {
        String graph = Utils.getString(param.get("graph"));		
        String database = Utils.getString(param.get("database"));		

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

    
    public int callProcedure(String proc) throws Exception {
        if("Map".equals(proc)) return gdbMapper.callSpAgMap();
        else return gdbMapper.callSpAgProperties();
    }
    
    public List<Map<String, Object>>  selectAgConvertMeta(Map<String, Object> param) throws Exception {
        return gdbMapper.selectAgConvertMeta(param);
    }
}