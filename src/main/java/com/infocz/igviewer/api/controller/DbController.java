package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.db.DbService;
import com.infocz.igviewer.api.session.SessionInfo;
import com.infocz.igviewer.api.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/db")
@RestController
public class DbController {
	@Autowired DataSource dataSource;
	@Autowired DbService dbService;
	@Autowired SessionService sessionService;

	@PostMapping("/connect")
	Map<String, Object> connect(@RequestBody Map<String, Object> requestBody, HttpSession session) {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> connect >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.debug("requestBody = {}", requestBody);

		String sessionID = session.getId();
		String database = Utils.getString(requestBody.get("database"), "infocz");
		sessionService.setSession(sessionID, database);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result","Ok");
		resultMap.put("msg","");
		resultMap.put("sessionID", sessionID);
		resultMap.put("config", requestBody);

		return resultMap;
	}

	@PostMapping("/getGraphPaths")
	Map<String, Object> getGraphPaths(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getGraphPaths");

		Map<String, Object> resultMap = new HashMap<String, Object>();	
		try {
			resultMap.put("rows", dbService.selectGraphPaths());
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		
		return resultMap;
	}

	@PostMapping("/setGraph")
	Map<String, Object> setGraph(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> setGraph");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph = Utils.getString(requestBody.get("graph"), "infocz_graph");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sessionService.setGraph(sessionID, graph);
			log.debug("SessionInfo = {}, {}", sessionID, SessionInfo.getSession(sessionID));
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		resultMap.put("setCnt", 1);

		return resultMap;
	}	

	@PostMapping("/meta")
	Map<String, Object> getMetaData(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMetaData");
		log.debug("requestBody={}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String database = sessionService.getDatabase(sessionID);
		String graph = sessionService.getGraph(sessionID);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("graph", graph);		
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = dbService.selectMetaData(param, database, graph);
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");

		return resultMap;
	}

	@PostMapping("/getMapInit")
	Map<String, Object> getMapInit(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMapInit");
		log.debug("requestBody={}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		Map<String, Object> param = new HashMap<String, Object>();
		log.debug("graph={}", sessionService.getGraph(sessionID));
		param.put("graph", sessionService.getGraph(sessionID));
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", dbService.selectAgMap(param));
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");

		return resultMap;
	}

	@PostMapping("/getEdgeList")
	Map<String, Object> getEdgeList(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getEdgeList");
		log.debug("requestBody={}", requestBody);
		
		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph = sessionService.getGraph(sessionID);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("graph", graph);
		param.put("category", Utils.getString(requestBody.get("category")));
		param.put("keyword", Utils.getString(requestBody.get("keyword")));
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			dbService.setGraphPath(graph);
			resultMap.put("rows", Utils.convertDoubleQuotation(dbService.selectEdgeList(param)));
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		log.debug("resultMap={}", resultMap);
		return resultMap;
	}
}