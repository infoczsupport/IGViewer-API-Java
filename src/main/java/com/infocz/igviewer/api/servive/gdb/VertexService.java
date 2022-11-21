package com.infocz.igviewer.api.servive.gdb;

import java.util.ArrayList;
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
@SuppressWarnings("unchecked")
public class VertexService {
    @Autowired GdbMapper gdbMapper;
    @Autowired VertexMapper vertexMapper;
    @Autowired RdbMapper rdbMapper;
    
    public int createVertex(String graph, Map<String, Object> param) throws Exception {
        int cnt = 0;        
        String tableNm = Utils.getString(param.get("tableNm"));
        String vertexNm = Utils.getString(param.get("vertexNm"));
        List<String> columns = new ArrayList<String>();
        for (String str : (List<String>) param.get("columns")) {
            int idx = str.indexOf(".");
            if(idx > -1) columns.add(str.substring(idx+1));        
        }
        
        gdbMapper.setGraphPath(graph);        
        vertexMapper.createVertex(vertexNm);

        Map<String, Object> arg = new HashMap<String, Object>();
        arg.put("graph", graph);
        arg.put("labKind", "v");
        arg.put("labNm", vertexNm);
        log.debug("arg = {}", arg);
        gdbMapper.insertAgConvertMeta(arg);

        this.insertVertexMeta(graph, tableNm, vertexNm, columns);
        cnt += this.insertVertex(tableNm, vertexNm, columns);

        gdbMapper.callSpAgMap();
        // gdbMapper.callSpAgProperties();

        return cnt;
    }
    
    public int dropVertex(String graph, String vertexNm) throws Exception {
        gdbMapper.setGraphPath(graph);
        int cnt = vertexMapper.dropVertex(vertexNm);

        Map<String, Object> param = new HashMap<String, Object>();	
        param.put("graph", graph);
        param.put("labNm", vertexNm);
        gdbMapper.deleteAgConvertMeta(param);

        param.put("vertexNm", vertexNm);
        vertexMapper.deleteMetaVertex(param);

        gdbMapper.callSpAgMap();
        // gdbMapper.callSpAgProperties();

        return cnt;
    } 

    public List<Map<String, Object>> selectMetaVertex(String graph) throws Exception {
        return vertexMapper.selectMetaVertex(graph);
    }

    private int insertVertexMeta(String graph, String tableNm, String vertexNm, List<String> columns){
        int cnt = 0;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableNm", tableNm);
        param.put("columns", columns);
        List<Map<String, Object>> metaList = rdbMapper.selectColumns(param);
        for (Map<String, Object> map : metaList) {
            Map<String, Object> arg = new HashMap<String, Object>();
            arg.putAll(map);
            arg.put("graph", graph);
            arg.put("vertexNm", vertexNm);
            log.debug("metaMap = {}", arg);
            cnt += vertexMapper.insertMetaVertex(arg);
        }
        return cnt;
    }

    public int insertVertex(String tableNm, String vertexNm, List<String> columns){
        int cnt = 0;
        Map<String, Object> par = new HashMap<String, Object>() {{
            put("tableNm", tableNm);
            put("columns", columns);
        }};
        List<Map<String, Object>> vertex = rdbMapper.selectTableData(par);
        for (Map<String,Object> v : vertex) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("vertexNm", vertexNm);
            // param.put("propertes", JSONObject.toJSONString(v).replace("\"", "'"));
            String propertes = "";
            for (String str : JSONObject.toJSONString(v).split(",")) {
                if(!Utils.isEmpty(propertes)) propertes += ", ";
                propertes += str.replaceFirst("\"", "")
                  .replaceFirst("\"", "").replace("\"", "'");
            }
            param.put("propertes", propertes);
            log.debug("param = {}", param);
            cnt += vertexMapper.insertVertex(param);
        }
        return cnt;
    }
}