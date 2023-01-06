package com.infocz.infoCruise.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.infoCruise.api.servive.gdb.MonitorService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api")
@RestController
public class MonitorController {
	@Autowired MonitorService monitorService;

	@GetMapping("/getDbMonitor")
	Map<String, Object> getMonitor() {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getMonitor");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("data", monitorService.selectMonitor());
		} catch(Exception e) {
			log.debug("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", e.getMessage());
			resultMap.put("result", "Fail");
			resultMap.put("msg", e.getMessage());
			return resultMap;  
		}

		resultMap.put("result", "Ok");
		resultMap.put("msg", "");
		log.debug("resultMap = {}", resultMap);
		return resultMap;
	}
}