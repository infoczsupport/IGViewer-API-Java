package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.servive.rdb2.Rdb2Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api/rdb2")
@RestController
public class Rdb2Controller {
	@Autowired Rdb2Service rdb2Service;

	@RequestMapping(value = "/getEmployee")
	Map<String, Object> getEmployee() {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getEmployee >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows", rdb2Service.selectEmployee());
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
