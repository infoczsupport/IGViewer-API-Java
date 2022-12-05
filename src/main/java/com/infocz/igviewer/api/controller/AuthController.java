package com.infocz.igviewer.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.security.JwtFilter;
import com.infocz.igviewer.api.security.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/com")
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@PostMapping("/login")
	ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> requestBody, HttpSession session) throws Exception {
		log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> login");
		log.debug("requestBody = {}", requestBody);

        String id = Utils.getString(requestBody.get("id"));
        String pwd = Utils.getString(requestBody.get("pwd"));

		// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// log.debug("{}, {}", pwd, passwordEncoder.encode(pwd));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pwd);
		log.debug("authenticationToken={}", authenticationToken);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
		log.debug("jwt = {}", jwt);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
				
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("result","Ok");
		resMap.put("msg","");
		resMap.put("jwt", jwt);
		resMap.put("userInfo", authentication.getPrincipal());

		return new ResponseEntity<>(resMap, httpHeaders, HttpStatus.OK);
	}	
}