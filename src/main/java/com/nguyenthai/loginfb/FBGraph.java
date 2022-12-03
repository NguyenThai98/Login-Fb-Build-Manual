package com.nguyenthai.loginfb;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FBGraph {
	private String accessToken;

	public FBGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() {

		HttpURLConnection uc;
		try {

			String g = "https://graph.facebook.com/me?access_token=" + accessToken + "&&fields=id,name,email,gender,first_name,birthday,hometown,middle_name,albums";
			URL u = new URL(g);
			uc = (HttpURLConnection) u.openConnection();

			uc.setRequestMethod("GET");
			uc.setUseCaches(false);
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type", "text/javascript; charset=utf-8");

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int length; (length = uc.getInputStream().read(buffer)) != -1; ) {
				result.write(buffer, 0, length);
			}
			// StandardCharsets.UTF_8.name() > JDK 7
			return result.toString("UTF-8");

//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					c.getInputStream(), "UTF-8"));
//			String inputLine;
//			StringBuffer b = new StringBuffer();
//			while ((inputLine = in.readLine()) != null)
//				b.append(inputLine + "\n");
//			in.close();
//			graph = b.toString();
//			System.out.println(graph);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}

	}

	public Map<String, String> getGraphData(String fbGraph) {
		//System.out.printf("fbGraph: " + fbGraph);
		Map<String, String> fbProfile = new HashMap<String, String>();
		try {
			JSONObject json = new JSONObject(fbGraph);
			fbProfile.put("id", json.getString("id"));
			fbProfile.put("first_name", json.getString("first_name"));
			fbProfile.put("name", json.getString("name"));
			fbProfile.put("hometown", json.getString("hometown"));
			//fbProfile.put("middle_name", json.getString("middle_name"));
			//fbProfile.put("albums", json.getString("albums"));
			if (json.has("email"))
				fbProfile.put("email", json.getString("email"));
			if (json.has("gender"))
				fbProfile.put("gender", json.getString("gender"));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		return fbProfile;
	}
}
