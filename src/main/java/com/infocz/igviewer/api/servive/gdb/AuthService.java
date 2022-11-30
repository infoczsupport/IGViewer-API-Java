package com.infocz.igviewer.api.servive.gdb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infocz.igviewer.api.common.Utils;
import com.infocz.igviewer.api.mapper.gdb.GdbMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthService {
    @Autowired GdbMapper gdbMapper;

    List<Map<String, Object>> users = Arrays.asList(
          new HashMap<String, Object>() {{put("id", "infocz"); put("pwd", "infocz4ever"); put("auth", "viewer");}}
        , new HashMap<String, Object>() {{put("id", "infocz-admin"); put("pwd", "infocz4ever"); put("auth", "admin");}}
        , new HashMap<String, Object>() {{put("id", "igviewer"); put("pwd", "infocz4ever"); put("auth", "viewer");}}
        , new HashMap<String, Object>() {{put("id", "igconverter"); put("pwd", "infocz4ever"); put("auth", "converter");}}
        , new HashMap<String, Object>() {{put("id", "igadmin"); put("pwd", "infocz4ever"); put("auth", "igadmin");}}
    );
    
    public Map<String, Object> selectUserInfo(Map<String, Object> param) throws Exception {
        String id = Utils.getString(param.get("id"));
        String pwd = Utils.getString(param.get("pwd"));
        
        Map<String, Object> userInfo = users.stream()
            .filter(m -> Objects.equals(m.get("id"), id) && Objects.equals(m.get("pwd"), pwd))
            .findFirst()
            .orElse(null);
        log.debug("userInfo = {}", userInfo);

        return userInfo;
    }
}