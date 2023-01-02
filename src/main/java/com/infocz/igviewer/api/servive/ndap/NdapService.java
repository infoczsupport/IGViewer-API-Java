package com.infocz.igviewer.api.servive.ndap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class NdapService {
    public void batchQueryHistory(){
        log.debug("\n\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> batchQueryHistory");
        HttpURLConnection conn = null;
        
        try {
            //URL 설정
            URL url = new URL("http://localhost:3001/api/getDbMonitor");
 
            conn = (HttpURLConnection) url.openConnection();
            
            // type의 경우 POST, GET, PUT, DELETE 가능
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            // conn.setRequestProperty("auth", "myAuth"); // header의 auth 정보
            conn.setRequestProperty("Transfer-Encoding", "chunked");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000); // 연결 타임아웃 설정 3초 
            conn.setReadTimeout(20000); // 읽기 타임아웃 설정 20초 
                        
            // BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            // // JSON 형식의 데이터 셋팅
            // JSONObject commands = new JSONObject();
            
            // // params.put("key", 1);
            // // params.put("age", 20);
            // // params.put("userNm", "홍길동");
            // commands.put("userInfo", "params");
            //  // JSON 형식의 데이터 셋팅 끝
            
            // bw.write(commands.toString());
            // bw.flush();
            // bw.close();

            //map = new ObjectMapper().readValue(strJson, Map.class) ;
            //JSONObject.toJSONString(Map)
            
            // 보내고 결과값 받기
            int responseCode = conn.getResponseCode();
            log.debug("responseCode :: {}", responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 응답 데이터
                log.debug("response :: {}", sb.toString());
            } 
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            System.out.println("not JSON Format response");
            e.printStackTrace();
        }
    }
    
}
