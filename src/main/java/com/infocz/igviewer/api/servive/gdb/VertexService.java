package com.infocz.igviewer.api.servive.gdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.mapper.gdb.GdbMapper;
import com.infocz.igviewer.api.mapper.gdb.VertexMapper;
import com.infocz.igviewer.api.mapper.rdb.RdbMapper;

import lombok.extern.log4j.Log4j2;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;

@Log4j2
@Service
public class VertexService {
    @Autowired GdbMapper gdbMapper;
    @Autowired VertexMapper vertexMapper;
    @Autowired RdbMapper rdbMapper;
    
    public int createVertex(String graph, List<Map<String, Object>> tables) throws Exception {
        int cntVerTex = 0;        
        gdbMapper.setGraphPath(graph);
        log.debug("tables = {}", tables);
        for (Map<String,Object> map : tables) {
            log.debug("map = {}", map);
            String tableNm = Utils.getString(map.get("key"));
            if("DEL".equals(map.get("act"))) {
                cntVerTex += vertexMapper.dropVertex(tableNm); 
                map.put("tableNm", tableNm);
                map.put("graph", graph);
                vertexMapper.deleteMetaVertex(map);
            } else {
                vertexMapper.createVertex(tableNm);
                this.insertVertexMeta(graph, tableNm);
                cntVerTex += this.insertVertex(tableNm);
            }             
        }
        gdbMapper.callSpAgMap();
        // gdbMapper.callSpAgProperties();

        return cntVerTex;
    }
    
    public List<Map<String, Object>> selectMetaVertex(String graph) throws Exception {
        return vertexMapper.selectMetaVertex(graph);
    }

    private int insertVertexMeta(String graph, String tableNm){
        int cntVerTex = 0;
        List<Map<String, Object>> metaList = rdbMapper.selectColumns(tableNm);
        for (Map<String,Object> map : metaList) {
            map.put("graph", graph);
            log.debug("metaMap = {}", map);
            cntVerTex += vertexMapper.insertMetaVertex(map);
        }                
        
        return cntVerTex;
    }

    public int insertVertex(String tableNm){
        int cntVerTex = 0;
        Map<String, Object> par = new HashMap<String, Object>() {{
            put("tableNm", tableNm);
        }};
        List<Map<String, Object>> vertex = rdbMapper.selectTableData(par);
        for (Map<String,Object> v : vertex) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("tableNm", tableNm);
            // param.put("propertes", JSONObject.toJSONString(v).replace("\"", "'"));
            String propertes = "";
            for (String str : JSONObject.toJSONString(v).split(",")) {
                if(!Utils.isEmpty(propertes)) propertes += ", ";
                propertes += str.replaceFirst("\"", "")
                  .replaceFirst("\"", "").replace("\"", "'");
            }
            param.put("propertes", propertes);
            log.debug("param = {}", param);
            cntVerTex += vertexMapper.insertVertex(param);
        }
        return cntVerTex;
    }
}