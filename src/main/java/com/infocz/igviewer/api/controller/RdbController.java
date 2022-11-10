package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.servive.rdb.RdbService;
import com.infocz.igviewer.api.servive.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/rdb")
@RestController
public class RdbController {
	@Autowired RdbService rdbService;
	@Autowired SessionService sessionService;

	@RequestMapping(value = "/getTableInfo")
	Map<String, Object> getTableInfo() {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getTableInfo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", rdbService.selectTableInfo());
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		return resultMap;
	}

	@RequestMapping(value = "/getTableData")
	Map<String, Object> selectTableList(@RequestBody Map<String, Object> requestBody) {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getTableData >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String tableNm = Utils.getString(requestBody.get("tableNm"));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("tableData", rdbService.getTableData(tableNm));
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
