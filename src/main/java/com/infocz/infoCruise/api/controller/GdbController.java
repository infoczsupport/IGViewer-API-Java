package com.infocz.infoCruise.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.infoCruise.api.common.Utils;
import com.infocz.infoCruise.api.servive.gdb.GdbService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/db")
@RestController
public class GdbController {
	@Value("${spring.datasource-gdb.database}") String database;

	// @Autowired ConnectService cnnectService;
	@Autowired GdbService gdbService;

	@PostMapping("/connect")
	Map<String, Object> connect(@RequestBody Map<String, Object> requestBody) throws Exception {
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> connect");
		log.debug("requestBody = {}", requestBody);

		// cnnectService.createDs(requestBody);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result","Ok");
		resultMap.put("msg","");
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
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		return resultMap;
	}

	@PostMapping("/getMeta")
	Map<String, Object> getMeta(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMeta");
		log.debug("requestBody={}", requestBody);

		Map<String, Object> param = new HashMap<String, Object>();
		param.putAll(requestBody);
		param.put("database", database);	
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = gdbService.selectMetaData(param);
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
	Map<String, Object> getMapInit(@RequestBody Map<String, Object> RequestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMapInit");
		log.debug("RequestBody={}", RequestBody);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", gdbService.selectAgMap(RequestBody));
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
		
		String graph = Utils.getString(requestBody.get("graph"));
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

	
	@PostMapping("/getConvertMeta")
	Map<String, Object> getConvertMeta(@RequestBody Map<String, Object> requestBody) throws Exception {		
		log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getConvertMeta");
		log.debug("requestBody={}", requestBody);
		
		String graph = Utils.getString(requestBody.get("graph"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("graph", graph);
		param.put("labKind", Utils.getString(requestBody.get("labKind")));
		log.debug("param={}", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			gdbService.setGraphPath(graph);
			// resultMap.put("rows", Utils.convertDoubleQuotation(gdbService.selectAgConvertMeta(param)));
			resultMap.put("rows", gdbService.selectAgConvertMeta(param));
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