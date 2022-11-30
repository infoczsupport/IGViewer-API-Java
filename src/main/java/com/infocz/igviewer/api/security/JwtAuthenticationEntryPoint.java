package com.infocz.igviewer.api.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;

@Log4j2
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("result","Fail");
        resMap.put("msg","ID not found or password is different ! (403)");

        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //Todo : 한글오류 추후 수정 필요
            writer.write(JSONObject.toJSONString(resMap));
        }catch(NullPointerException e){
            log.error("응답 메시지 작성 에러", e);
        }finally{
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
        // response.getWriter().write(JSONObject.toJSONString(resMap));
    }
}