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
	// private Set<AuthDTO> auth;
	private List<String> auth;
}