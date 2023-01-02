package com.infocz.igviewer.api.security;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private String userId;
	private String loginId;
	private String userNm;
	private String userPwd;
	private Boolean isUse;
	private List<String> auth;	

	// public UserDTO(String userId, String loginId, String userNm, String userPwd, boolean isUse, List<String> auth) {
	// 	this.userId = userId;
	// 	this.loginId = loginId;
	// 	this.userNm = userNm;
	// 	this.userPwd = userPwd;
	// 	this.isUse = isUse;
	// 	this.auth = auth;
	// }
}