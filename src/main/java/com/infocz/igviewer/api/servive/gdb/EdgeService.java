package com.infocz.igviewer.api.servive.gdb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.mapper.gdb.EdgeMapper;
import com.infocz.igviewer.api.mapper.gdb.GdbMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EdgeService {
    @Autowired GdbMapper gdbMapper;
    @Autowired EdgeMapper edgeMapper;
    
    public int createEdge(String graph, Map<String, Object> param) throws Exception {
        int cnt = 0;        
        gdbMapper.setGraphPath(graph);
        String edgeNm = Utils.getString(param.get("edgeNm"));

        edgeMapper.createEdge(edgeNm);
        edgeMapper.insertEdge(param);

        Map<String, Object> arg = new HashMap<String, Object>();
        arg.put("graph", graph);
        arg.put("labKind", "e");
        arg.put("labNm", edgeNm);
        log.debug("arg = {}", arg);
        cnt = gdbMapper.insertAgConvertMeta(arg);

        gdbMapper.callSpAgMap();
        gdbMapper.callSpAgProperties();

        return cnt;
    } 

    public int dropEdge(String graph, String edgeNm) throws Exception {
        gdbMapper.setGraphPath(graph);
        int cnt = edgeMapper.dropEdge(edgeNm);

        Map<String, Object> param = new HashMap<String, Object>();	
        param.put("graph", graph);
        param.put("labNm", edgeNm);
        gdbMapper.deleteAgConvertMeta(param);

        gdbMapper.callSpAgMap();
        // gdbMapper.callSpAgProperties();

        return cnt;
    } 
}