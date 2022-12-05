package com.infocz.igviewer.api.servive.gdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpUtils;

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
import com.infocz.igviewer.api.security.UserDTO;

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
        // getNdopRequest(loginId);
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

    private String getNdopRequest(String id, String pwd) {
		String url = "http://192.168.0.2:9201/twitter/_doc/1";
        String params = "user";
		String method = "GET";
		String result = "";
		HttpURLConnection conn = null;
		
        //HttpURLConnection 객체 생성
		conn = getHttpURLConnection(url, method);
		//URL 연결에서 데이터를 읽을지에 대한 설정 ( defualt true )
        // conn.setDoInput(true); 
		//API에서 받은 데이터를 StringBuilder 형태로 리턴하여 줍니다. 
        result = getHttpRespons(conn);
        //해당 정보를 확인합니다.
		System.out.println("GET = " + result);
        return result;
	}

    private HttpURLConnection getHttpURLConnection(String strUrl, String method) {
		URL url;
		HttpURLConnection conn = null;
		try {
			url = new URL(strUrl);

			conn = (HttpURLConnection) url.openConnection(); //HttpURLConnection 객체 생성
			conn.setRequestMethod(method); //Method 방식 설정. GET/POST/DELETE/PUT/HEAD/OPTIONS/TRACE
			conn.setConnectTimeout(5000); //연결제한 시간 설정. 5초 간 연결시도
			conn.setRequestProperty("Content-Type", "application/json");
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return conn;		
	}
	
	private String getHttpRespons(HttpURLConnection conn) {
		StringBuilder sb = null;

		try {
			if(conn.getResponseCode() == 200) {
            // 정상적으로 데이터를 받았을 경우
            	//데이터 가져오기
				sb = readResopnseData(conn.getInputStream());
			}else{
            // 정상적으로 데이터를 받지 못했을 경우            
            	//오류코드, 오류 메시지 표출
				System.out.println(conn.getResponseCode());
				System.out.println(conn.getResponseMessage());
				//오류정보 가져오기
				sb = readResopnseData(conn.getErrorStream());
				System.out.println("error : " + sb.toString());
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.disconnect(); //연결 해제
		};
		if(sb == null) return null;
        
		return sb.toString();
	}
	
	private StringBuilder readResopnseData(InputStream in) {
		if(in == null ) return null;

		StringBuilder sb = new StringBuilder();
		String line = "";
		
		try (InputStreamReader ir = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(ir)){
			while( (line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}    
}