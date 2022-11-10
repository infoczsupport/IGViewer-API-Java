package com.infocz.igviewer.api.servive.gdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.mapper.gdb.GdbMapper;
import com.infocz.igviewer.api.mapper.rdb.RdbMapper;

import lombok.extern.log4j.Log4j2;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;

@Log4j2
@Service
public class GdbService {
    @Autowired GdbMapper gdbMapper;
    @Autowired RdbMapper rdbMapper;
    
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

    
    public int callProcedure(String proc) throws Exception {
        if("Map".equals(proc)) return gdbMapper.callSpAgMap();
        else return gdbMapper.callSpAgProperties();
    }

    public int createVertex(String graph, List<Map<String, Object>> tables) throws Exception {
        int cntVerTex = 0;
        
        gdbMapper.setGraphPath(graph);
        log.debug("tables = {}", tables);
        for (Map<String,Object> map : tables) {
            log.debug("map = {}", map);
            String table = Utils.getString(map.get("key"));
            if("DEL".equals(map.get("act"))) {
                gdbMapper.dropVertex(table); 
            } else {
                gdbMapper.createVertex(table);
                List<Map<String, Object>> vertex = rdbMapper.selectTableList(table);
                for (Map<String,Object> v : vertex) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("tableName", table);
                    param.put("propertes", JSONObject.toJSONString(v).replace("\"", "'"));
                    log.debug("param = {}", param);
                    cntVerTex += gdbMapper.insertVertex(param);
                }
            }             
        }
        gdbMapper.callSpAgMap();
        gdbMapper.callSpAgProperties();

        return cntVerTex;
    }
    
}