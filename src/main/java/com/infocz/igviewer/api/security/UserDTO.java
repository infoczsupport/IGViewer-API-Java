package com.infocz.igviewer.api.security;

import lombok.Data;

@Data
public class UserDTO {
	private String userId;
	private String loginId;
	private String userNm;
	private String userPwd;
	private Boolean isUse;
	// private Set<AuthDTO> auth;
	private String auth;
}