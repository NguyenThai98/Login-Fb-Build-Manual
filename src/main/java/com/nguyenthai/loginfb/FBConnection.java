package com.nguyenthai.loginfb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FBConnection {
	public static final String FB_APP_ID = "1041055959643557";
	public static final String FB_APP_SECRET = "314f18ba0e60fc7093975ca7f990f4ed";
	public static final String REDIRECT_URI = "http://localhost:8080/api/code";



	public static String getFBAuthUrl() {
		String fbLoginUrl = "";
		fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
				+ FBConnection.FB_APP_ID + "&redirect_uri="
				+ FBConnection.REDIRECT_URI
				+ "&scope=email,public_profile";
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
				+ "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
				+ FBConnection.REDIRECT_URI
				+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		return fbGraphUrl;
	}

	public AccessTokenFB getAccessToken(String code) {
		String accessToken = "";
		AccessTokenFB token = new AccessTokenFB();
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			ObjectMapper mapper = new ObjectMapper();

			try {
				token = mapper.readValue(accessToken, AccessTokenFB.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println("token: " + token);
			return token;
		}
		return token;
	}
}
