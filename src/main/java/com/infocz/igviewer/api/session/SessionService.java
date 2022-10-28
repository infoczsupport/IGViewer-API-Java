package com.infocz.igviewer.api.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SessionService {
    @Value("${com.infocz.igviewer.session-time-seconds}")
	private Integer sessionTimeSeconds;

    public boolean isSession(String sessionID){
        return SessionInfo.getSession(sessionID) != null;
    }

    public ConnectionInfo getSession(String sessionID){
        return SessionInfo.getSession(sessionID);
    }

    public String getDatabase(String sessionID){
        return SessionInfo.getSession(sessionID).getDatabase();
    }
    public String getGraph(String sessionID){
        return SessionInfo.getSession(sessionID).getGraph();
    }
    public void setGraph(String sessionID, String graph){
        SessionInfo.getSession(sessionID).setGraph(graph);
    }

    public void setSession(String sessionID, String database) {
        SessionInfo.setSession(sessionID, database);
        log.debug("SessionInfo = {}", SessionInfo.getSession(sessionID));
    }

    public void timeoutSession() {
        SessionInfo.removeSession(sessionTimeSeconds);
    }
}