package com.infocz.infoCruise.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.infoCruise.api.common.Utils;
import com.infocz.infoCruise.api.servive.gdb.CypherService;
import com.infocz.infoCruise.api.servive.gdb.GdbService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api")
@RestController
public class CypherController {
	@Autowired CypherService cypherService;
	@Autowired GdbService dbService;

	@PostMapping("/cypher")
	Map<String, Object> getCypherData(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getCypherData");
		log.debug("requestBody = {}", requestBody);

		String graph = Utils.getString(requestBody.get("graph"));
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