package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.cyhper.CypherService;
import com.infocz.igviewer.api.db.DbService;
import com.infocz.igviewer.api.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api")
@RestController
public class CypherController {
	@Autowired CypherService cypherService;
	@Autowired DbService dbService;
	@Autowired SessionService sessionService;

	@PostMapping("/cypher")	
	Map<String, Object> getCypherData(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getCypherData");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph = sessionService.getGraph(sessionID);
		String sql = Utils.getString(requestBody.get("cmd"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			dbService.setGraphPath(graph);
			resultMap = cypherService.execute(sql);
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}
		resultMap.put("result", "Ok");
		resultMap.put("msg", "");

		return resultMap;
	}
}