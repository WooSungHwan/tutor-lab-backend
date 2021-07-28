package com.tutor.tutorlab.config.security.oauth.provider.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.tutorlab.config.security.oauth.provider.OAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
// https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
public class KakaoOAuth implements OAuth {

    private final String KAKAO_BASE_URL = "https://kauth.kakao.com/oauth/authorize";
    private final String KAKAO_CALLBACK_URL = "http://localhost:8080/oauth/kakao/callback";
    private final String KAKAO_USERINFO_ACCESS_URL = "https://kapi.kakao.com/v2/user/me";
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_CLIENT_ID = "8dc9eea7e202a581e0449058e753beaf";
    // private final String KAKAO_CLIENT_SECRET;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String requestAccessToken(String code) {

        if (!StringUtils.hasLength(code)) {
            return null;
        }

        /*
        * Required Parameter
        - grant_type
        - client_id
        - redirect_uri
        - code
        */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_CALLBACK_URL);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity
                = restTemplate.exchange(KAKAO_TOKEN_URL, HttpMethod.POST, request, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // System.out.println(responseEntity.getBody());
            /*
                {
                    "access_token":"DfhhliiDrnYgUIte1xHREi_jMKE-QJxih32Zswo9dVsAAAF67yBRdQ",
                    "token_type":"bearer",
                    "refresh_token":"bxYiyw945QkVMMHMFvYiH1I3BFp3JRMYanO8Mgo9dVsAAAF67yBRdA",
                    "expires_in":21599,
                    "scope":"account_email profile_nickname",
                    "refresh_token_expires_in":5183999
                }
            */

            return responseEntity.getBody();
        }

        return null;
    }

    @Override
    public String requestUserInfo(String accessToken) {

        /*
            GET/POST /v2/user/me HTTP/1.1
            Host: kapi.kakao.com
            Authorization: Bearer {ACCESS_TOKEN}
            Content-type: application/x-www-form-urlencoded;charset=utf-8
        */

        if (!StringUtils.hasLength(accessToken)) {
            return null;
        }

        Map<String, String> map = convertStringToMap(accessToken);
        String token = map.get("access_token");
        if (!StringUtils.hasLength(token)) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, TOKEN_PREFIX + token);
        ResponseEntity<String> responseEntity
                = restTemplate.exchange(KAKAO_USERINFO_ACCESS_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }

    // TODO - 리팩토링
    @Override
    public Map<String, String> requestLogin(String code) {
        
        String accessToken = requestAccessToken(code);
        String userInfo = requestUserInfo(accessToken);

        return convertStringToMap(userInfo);
    }

    // TODO - 리팩토링
    public Map<String, String> convertStringToMap(String string) {

        try {
            if (StringUtils.hasLength(string)) {
                return objectMapper.readValue(string, Map.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}
