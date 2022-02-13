package com.example.demo.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoService {


        public String getAccessToken (String authorize_code) {
            String access_Token = "";
            String refresh_Token = "";
            String reqURL = "https://kauth.kakao.com/oauth/token";

            try {
                URL url = new URL(reqURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");// 카카오는 필수 파라미터 값들을 담아 POST로만 요청이 가능하다.
                conn.setDoOutput(true);       // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

                //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                StringBuilder sb = new StringBuilder();
                sb.append("grant_type=authorization_code");
                sb.append("&client_id=53c5f9b54ad9450a6cc811fdccf65417");  //본인이 발급받은 key
                sb.append("&redirect_uri=http://localhost:8080/login");     // 본인이 설정해 놓은 경로
                sb.append("&code=" + authorize_code);
                bw.write(sb.toString());
                bw.flush();//토큰 요청을 위해 토큰 요청 URI를 작성하고(버퍼로), 이를 flush해서 토큰을 받아온다. 

                //    결과 코드가 200이라면 성공
                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                
                System.out.println("response body : " + result);//json타입의 객체

                //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                access_Token = element.getAsJsonObject().get("access_token").getAsString();
                refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

                System.out.println("access_token : " + access_Token);
                System.out.println("refresh_token : " + refresh_Token);

                br.close();
                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return access_Token;
        }
        
        
        public HashMap<String, Object> getUserInfo (String access_Token) {

            //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
            HashMap<String, Object> userInfo = new HashMap<>();
            String reqURL = "https://kapi.kakao.com/v2/user/me";
            
            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                //    요청에 필요한 Header에 포함될 내용
                conn.setRequestProperty("Authorization", "Bearer " + access_Token);

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                
                System.out.println("response body : " + result);

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                System.out.println("########properties : " + element.getAsJsonObject().get("properties").getAsJsonObject());
                JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                String nickname = properties.getAsJsonObject().get("nickname").getAsString();//필수
                
                //String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();//선택 또는 없을 수 있음
				
                userInfo.put("nickname", nickname);
                String profile_image = null;
                String email = null;
               
                if(properties.getAsJsonObject().has("profile_image"))//properties에 프사 자체를 가지고 있지 않을 수 있다.
                {
                	System.out.println("###profileImage###" + properties.getAsJsonObject().get("profile_image").getAsString());
                	profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
                	userInfo.put("profile_image", profile_image);
                	
                }
                else {
                	System.out.println("##profile_image is null!##");
                	userInfo.put("profile_image", null);
				}
                
                
                //if (!kakao_account.getAsJsonObject().get("email").isJsonNull()) {//isJsonNull은 아예 해당 값을 키로 가진 객체가 비어있는건지 보는건데 여기서는 email이라는 값 자체가 존재하지 않는다. 그래서 이거 사용 불가.
                if (kakao_account.getAsJsonObject().get("email_needs_agreement").getAsBoolean()==true) {
                	System.out.println("##EMail is null!##");
                	userInfo.put("email", null);
				}
                else {
                	System.out.println("###EMail###"+kakao_account.getAsJsonObject().get("email").getAsString());
                	email = kakao_account.getAsJsonObject().get("email").getAsString();
                	userInfo.put("email", email);
                
				}
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return userInfo;
        }
}