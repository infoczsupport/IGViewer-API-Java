package com.infocz.igviewer.api.servive.gdb;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.mapper.gdb.MonitorMapper;

@Service
public class MonitorService {
    @Autowired MonitorMapper monitorMapper;
    
    public Map<String, Object> selectMonitor() throws Exception {
        return getMapFromJsonString(
            Utils.getString(monitorMapper.selectMonitor().get("monitor"))
        );
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonString( String strJson ){
        Map<String, Object> map = null;
        
        try {            
            map = new ObjectMapper().readValue(strJson, Map.class) ;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return map;
    }
}