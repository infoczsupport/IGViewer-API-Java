package com.infocz.infoCruise.api.servive.gdb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infocz.infoCruise.api.security.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    // infocz4ever
    List<Map<String, Object>> users = Arrays.asList(
          new HashMap<String, Object>() {{put("loginId", "user"); put("userPwd", "$2a$10$.ZNhvn1CI3ibVUQss2iQCuaMHdEIC3yy.I9SHp7ozJUNhY4lRN7O2"); put("auth", Arrays.asList("viewer")); put("isUse", true);}}
        , new HashMap<String, Object>() {{put("loginId", "infocz"); put("userPwd", "$2a$10$XR.W61vu9oJNP/BXhwk2m.HvBehy6T5fV./aK/xsVHb2HkP8kY9Ga"); put("auth", Arrays.asList("viewer")); put("isUse", true);}}
        , new HashMap<String, Object>() {{put("loginId", "infocz-admin"); put("userPwd", "$2a$10$XR.W61vu9oJNP/BXhwk2m.HvBehy6T5fV./aK/xsVHb2HkP8kY9Ga"); put("auth", Arrays.asList("admin")); put("isUse", true);}}
        , new HashMap<String, Object>() {{put("loginId", "igviewer"); put("userPwd", "$2a$10$XR.W61vu9oJNP/BXhwk2m.HvBehy6T5fV./aK/xsVHb2HkP8kY9Ga"); put("auth", Arrays.asList("viewer")); put("isUse", true);}}
        , new HashMap<String, Object>() {{put("loginId", "igconverter"); put("userPwd", "$2a$10$XR.W61vu9oJNP/BXhwk2m.HvBehy6T5fV./aK/xsVHb2HkP8kY9Ga"); put("auth", Arrays.asList("converter", "igadmin")); put("isUse", true);}}
        , new HashMap<String, Object>() {{put("loginId", "igadmin"); put("userPwd", "$2a$10$XR.W61vu9oJNP/BXhwk2m.HvBehy6T5fV./aK/xsVHb2HkP8kY9Ga"); put("auth", Arrays.asList("igadmin")); put("isUse", true);}}
    );

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId){
        Map<String, Object> userInfo = users.stream()
            .filter(m -> Objects.equals(m.get("loginId"), loginId))
            .findFirst()
            .orElseThrow(() -> new UsernameNotFoundException(loginId + " -> 존재 하지 않음."));
            // .orElse(null);
        log.debug("userInfo = {}", userInfo);
        UserDTO userDTO = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userDTO = objectMapper.convertValue(userInfo, UserDTO.class);
        } catch (Exception e) {
            log.debug("error = {}", e);    
            // TODO: handle exception
        }
        
        return createUser(loginId, userDTO);
    }

    /**Security User 정보를 생성한다. */
    private UserDetails createUser(String loginId, UserDTO userDTO) {
        if(!userDTO.getIsUse()){
            throw new BadCredentialsException(loginId + " -> 활성화 되어있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = userDTO.getAuth().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
        return User.builder().username(userDTO.getLoginId())
                      .password(userDTO.getUserPwd())
                      .authorities(grantedAuthorities).build();
    }
}