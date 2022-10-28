package com.infocz.igviewer.api.cyhper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.Utils;

import lombok.extern.log4j.Log4j2;
import net.bitnine.agensgraph.graph.Edge;
import net.bitnine.agensgraph.graph.Path;
import net.bitnine.agensgraph.graph.Vertex;

@Log4j2
@Service
public class CypherService {
    @Autowired CyhperDao cyhperDao;

    public Map<String, Object> execute(String sql) throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>(); 
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(); 
        SqlRowSet rs = cyhperDao.executeForRowSet(sql);
        while ( rs.next() ) {                        
            SqlRowSetMetaData metaData = rs.getMetaData();
            Map<String, Object> map = new HashMap<String, Object>(); 
            for(int i=1; i<metaData.getColumnCount()+1; i++){
                // log.debug("class ={}", rs.getObject(metaData.getColumnLabel(i)).getClass());
                String columnLabel = metaData.getColumnLabel(i);
                PGobject pgObject = (PGobject) rs.getObject(columnLabel);
                if (pgObject != null) {
                    // log.debug("pgObject.getType() ={}", pgObject.getType());
                    //          if (typeName === 'Array') {
                    //             let idx = 0;
                    //             row[k].forEach(el => {
                    //                 convetedObject[k+idx++] = this.convertEdge(el);
                    //              }); } 

                    switch(pgObject.getType()){
                        case "vertex" : 
                            map.put(columnLabel, convertVertex((Vertex) rs.getObject(columnLabel)));
                            break;
                        case "edge" : 
                            map.put(columnLabel, convertEdge((Edge) rs.getObject(columnLabel)));
                            break;
                        case "graphpath" : 
                            map.put(columnLabel, convertPath((Path) rs.getObject(columnLabel)));
                            break;
                        default : 
                            map.put(columnLabel,  Utils.getString(pgObject).replace("\"", ""));
                            break;
                    }
                } else {
                    map.put(columnLabel,  Utils.getString(pgObject).replace("\"", ""));
                }
            }
            log.debug("map ={}", map);
            rows.add(map);
        }
        
        retMap.put("rows", rows);
        retMap.put("columns", this.getColumns(rows));
        retMap.put("rowCount", rows.size());
        retMap.put("command", "SELECT");
        return retMap;
    }

    private Set<String> getColumns(List<Map<String, Object>>  list) {
		if (list.size() < 1) return null;
		return list.get(0).keySet();
    }


    private List<Map<String, Object>> convertPath(Path path) {
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        for (Vertex vertex : path.vertices()) {
            retList.add(this.convertVertex(vertex));
        }
        for (Edge edg : path.edges()) {
            retList.add(this.convertEdge(edg));
        }
        return retList;
    }

    private Map<String, Object> convertEdge(Edge edge) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("label", edge.getLabel());
        retMap.put("id", edge.getEdgeId().getValue());
        retMap.put("start", edge.getStartVertexId().getValue());
        retMap.put("end", edge.getEndVertexId().getValue());
        retMap.put("properties", edge.getProperties().getJsonValue());
        return retMap;
    }

    private Map<String, Object> convertVertex(Vertex vertex) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("label", vertex.getLabel());
        retMap.put("id", vertex.getVertexId().getValue());
        retMap.put("properties", vertex.getProperties().getJsonValue());
        return retMap;
    }

}

