package com.infocz.igviewer.api.common;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.infocz.igviewer.api.servive.session.SessionInfo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class LoginIntercepter implements HandlerInterceptor {
    public List<String> loginEssential
            = Arrays.asList("/api/cypher/**", "/api/db/**");

    public List<String> loginInessential
            = Arrays.asList("/api/login/**", "/api/db/connect", "/api/db/getGraphPaths", "/api/db/setGraph");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String destUri = request.getRequestURI();
        String destQuery = request.getQueryString();
        String dest = (destQuery == null) ? destUri : destUri+"?"+destQuery;
        log.debug("dest = {}", dest);
                
        String sessionID = request.getParameter("sessionID");
        log.debug("sessionID = {}", sessionID);
        // log.debug(SessionInfo.toStr());
        if(SessionInfo.getSession(sessionID) != null){
            log.debug("session Ok ==========>");
            return true;
        }else{
            log.debug("session is Null ==========>");
            response.sendError(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
            return false;
        }
    }
}