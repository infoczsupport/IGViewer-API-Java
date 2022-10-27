package com.infocz.igviewer.api.service;

import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.ConnectionInfo;
import com.infocz.igviewer.api.common.SessionInfo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SessionService {
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

}