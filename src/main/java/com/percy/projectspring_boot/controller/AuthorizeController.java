package com.percy.projectspring_boot.controller;

import com.percy.projectspring_boot.dto.AccessTokenDTO;
import com.percy.projectspring_boot.dto.GitHubUser;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.User;
import com.percy.projectspring_boot.model.UserExample;
import com.percy.projectspring_boot.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        System.out.println("进入这里");
        String accessToken = gitHubProvider.getAccessToken(new AccessTokenDTO(clientId, clientSecret, code, redirectUri, state));
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser!=null) {

            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(String.valueOf(gitHubUser.getId()));
            List<User> user = userMapper.selectByExample(userExample) ;
            String token = UUID.randomUUID().toString();
            if (user.size() != 0) {
                //老用户更新数据
                User dbUser = user.get(0);
                User updataUser = new User();
                updataUser.setToken(token);
                updataUser.setName(gitHubUser.getName());
                updataUser.setGmtModified(System.currentTimeMillis());
                updataUser.setSrcUrl(gitHubUser.getAvatar_url());
                UserExample userExample1 = new UserExample();
                userExample1.createCriteria().andIdEqualTo(dbUser.getId());
                userMapper.updateByExampleSelective(updataUser, userExample1);
            } else {
                //新登陆用户直接添加
                User DBuser = new User();
                DBuser.setToken(token);
                DBuser.setAccountId(String.valueOf(gitHubUser.getId()));
                DBuser.setName(gitHubUser.getName());
                DBuser.setGmtCreate(System.currentTimeMillis());
                DBuser.setGmtModified(DBuser.getGmtCreate());
                DBuser.setSrcUrl(gitHubUser.getAvatar_url());
                userMapper.insert(DBuser);
            }

            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("gitHubUser",gitHubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }


}
