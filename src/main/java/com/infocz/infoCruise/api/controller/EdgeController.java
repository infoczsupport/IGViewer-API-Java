package com.infocz.infoCruise.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.infoCruise.api.common.Utils;
import com.infocz.infoCruise.api.servive.gdb.EdgeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/convert")
@RestController
public class EdgeController {
	@Autowired EdgeService edgeService;

	@PostMapping("/createEdge")
	Map<String, Object> createEdge(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> createEdge");
		log.debug("requestBody = {}", requestBody);

		String graph = Utils.getString(requestBody.get("graph"));

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

		String graph  = Utils.getString(requestBody.get("graph"));
		String edgeNm = Utils.getString(requestBody.get("edgeNm"));

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