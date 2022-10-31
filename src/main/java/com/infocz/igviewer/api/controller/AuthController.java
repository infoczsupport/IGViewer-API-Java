package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.db.DbService;
import com.infocz.igviewer.api.session.SessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/api")
@RestController
public class AuthController {
	@Autowired DbService dbService;
	@Autowired SessionService sessionService;

	@PostMapping("/login")
	Map<String, Object> connect(@RequestBody Map<String, Object> requestBody, HttpSession session) {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> login >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
}