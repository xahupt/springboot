package com.percy.projectspring_boot.provider;

import com.alibaba.fastjson.JSON;
import com.percy.projectspring_boot.dto.AccessTokenDTO;
import com.percy.projectspring_boot.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body =
                RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getGitHubUser (String accessToken) {
        OkHttpClient client = new OkHttpClient();
        System.out.println("接收消息开始");
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            GitHubUser gitHubUser = JSON.parseObject(response.body().string(), GitHubUser.class);
//            System.out.println(gitHubUser.getLogin());
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
