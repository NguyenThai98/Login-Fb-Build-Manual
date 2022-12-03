package com.nguyenthai.loginfb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CallBackLogin {

    @GetMapping("/code")
    public Map<String, String> getCode(String code){
        FBConnection fbConnection = new FBConnection();
        AccessTokenFB accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken.getAccess_token());
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        return fbProfileData;
    }

    @GetMapping("/callFbApi")
    public void callFbApi(){

    }

    @GetMapping("/viewPage")
    public String viewPage(){
        return "index";
    }
}
