package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.servive.gdb.VertexService;
import com.infocz.igviewer.api.servive.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/convert")
@RestController
public class VertexController {
	@Autowired VertexService vertexService;
	@Autowired SessionService sessionService;

	@PostMapping("/createVertex")
	Map<String, Object> createVertex(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> createVertex");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			vertexService.createVertex(graph, requestBody);
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		resultMap.put("cnt", 1);
		return resultMap;
	}
	
	@PostMapping("/deleteVertex")
	Map<String, Object> deleteVertex(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> deleteVertex");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);
		String vertexNm = Utils.getString(requestBody.get("vertexNm"));

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			vertexService.dropVertex(graph, vertexNm);
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		resultMap.put("cnt", 1);
		return resultMap;
	}

	@PostMapping("/getMetaVertex")
	Map<String, Object> getMetaVertex(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMetaVertex");
		log.debug("requestBody = {}", requestBody);
		
		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);
		log.debug("graph={}", graph);

		Map<String, Object> resultMap = new HashMap<String, Object>();	
		try {
			resultMap.put("rows", vertexService.selectMetaVertex(graph));
		} catch(Exception e) {
			log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", e.getMessage());
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		return resultMap;
	}
}