package com.infocz.igviewer.api.session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Log4j2
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

	public static void removeSession() {
		for ( String key: session.keySet() ) {
			LocalDateTime curTime = LocalDateTime.now();
			LocalDateTime sesTime = session.get(key).getDateTime();
			Duration duration = Duration.between(sesTime, curTime);

			if ( duration.getSeconds() > 3600 ) {
				log.debug("Time Out Session Remove. = {}", key);
				session.remove(key);
			}
		}
	}
}
