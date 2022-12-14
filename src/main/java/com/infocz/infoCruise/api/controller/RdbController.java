package com.infocz.infoCruise.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.infoCruise.api.common.Utils;
import com.infocz.infoCruise.api.servive.rdb.RdbService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/rdb")
@RestController
public class RdbController {
	@Autowired RdbService rdbService;
	
	@PostMapping(value = "/getTableList")
	Map<String, Object> getTableList() {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getTableList");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", rdbService.selectTableList());
		} catch(Exception e) {
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		return resultMap;
	}

	@PostMapping(value = "/getTableData")
	Map<String, Object> getTableData(@RequestBody Map<String, Object> requestBody, Authentication authentication) {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getTableData");
		log.debug("Credentials = {}", authentication.getCredentials());
		log.debug("Authorities = {}", authentication.getAuthorities());
		log.debug("userId = {}", authentication.getName());

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
