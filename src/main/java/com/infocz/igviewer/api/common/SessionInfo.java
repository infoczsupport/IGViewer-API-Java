package com.infocz.igviewer.api.common;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class SessionInfo {
	private static Map<String, ConnectionInfo> session = new HashMap<String, ConnectionInfo>();

	public static ConnectionInfo getSession(String key){
		ConnectionInfo connectionInfo = (ConnectionInfo)session.get(key);
		connectionInfo.setDateTime(LocalDateTime.now());
		return connectionInfo;
	}

	public static void setSession(String key, String database){
		ConnectionInfo connectionInfo = new ConnectionInfo();
		connectionInfo.setDatabase(database);
		connectionInfo.setDateTime(LocalDateTime.now());
		session.put(key, connectionInfo);
	}
}	
