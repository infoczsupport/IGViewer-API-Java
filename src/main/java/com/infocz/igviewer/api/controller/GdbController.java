package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.servive.gdb.GdbService;
import com.infocz.igviewer.api.servive.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/db")
@SuppressWarnings("unchecked")
@RestController
public class GdbController {
	@Autowired GdbService gdbService;
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
			resultMap.put("rows", gdbService.selectGraphPaths());
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

	@PostMapping("/setGraph")
	Map<String, Object> setGraph(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> setGraph");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph = Utils.getString(requestBody.get("graph"), "infocz_graph");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			sessionService.setGraph(sessionID, graph);
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
			resultMap = gdbService.selectMetaData(param, database, graph);
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
	Map<String, Object> getMapInit(@RequestParam Map<String, Object> requestParam) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMapInit");
		log.debug("requestParam={}", requestParam);

		String sessionID = Utils.getString(requestParam.get("sessionID"));
		Map<String, Object> param = new HashMap<String, Object>();
		log.debug("graph={}", sessionService.getGraph(sessionID));
		param.put("graph", sessionService.getGraph(sessionID));
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", gdbService.selectAgMap(param));
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
		
		if ( requestBody.get("keyword").getClass().getSimpleName().equals("ArrayList") ) {
			param.put("keyword",Utils.getStringArray(requestBody.get("keyword")));
			param.put("category", Utils.getStringArray(requestBody.get("category")));
		} else {
			param.put("keyword", Utils.getString(requestBody.get("keyword")));
			param.put("category", Utils.getString(requestBody.get("category")));
		}

		param.put("showCnt", Utils.getString(requestBody.get("showCnt")));
		param.put("nodesLogic", Utils.getString(requestBody.get("nodesLogic")));
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			gdbService.setGraphPath(graph);
			resultMap.put("rows", Utils.convertDoubleQuotation(gdbService.selectEdgeList(param)));
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		return resultMap;
	}

	@PostMapping("/callProcedure")
	Map<String, Object> callProcedure(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> callProcedure");
		log.debug("requestBody = {}", requestBody);

		String proc = Utils.getString(requestBody.get("proc"));
		log.debug("proc={}", proc);

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			gdbService.callProcedure(proc);
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

	@PostMapping("/createVertex")
	Map<String, Object> createVertex(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> createVertex");
		log.debug("requestBody = {}", requestBody);

		String sessionID = Utils.getString(requestBody.get("sessionID"));
		String graph 	 = sessionService.getGraph(sessionID);
		List<Map<String, Object>> tables = (List<Map<String, Object>>) requestBody.get("tables");

		Map<String, Object> resultMap = new HashMap<String, Object>();		
		try {
			gdbService.createVertex(graph, tables);
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