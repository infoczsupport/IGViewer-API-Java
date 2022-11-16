package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.servive.gdb.EdgeService;
import com.infocz.igviewer.api.servive.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/convert")
@RestController
public class EdgeController {
	@Autowired EdgeService edgeService;
	@Autowired SessionService sessionService;

	@PostMapping("/createEdge")
	Map<String, Object> createEdge(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> createEdge");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			edgeService.createEdge(graph, requestBody);
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

	@PostMapping("/deleteEdge")
	Map<String, Object> deleteEdge(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> deleteEdge");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);
		String edgeNm 	 = Utils.getString(requestBody.get("edgeNm"));

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			edgeService.dropEdge(graph, edgeNm);
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

}