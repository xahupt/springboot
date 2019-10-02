package com.percy.projectspring_boot.controller;

import com.percy.projectspring_boot.dto.AccessTokenDTO;
import com.percy.projectspring_boot.dto.GitHubUser;
import com.percy.projectspring_boot.mapper.UserMapper;
import com.percy.projectspring_boot.model.User;
import com.percy.projectspring_boot.provider.GitHubProvider;
import com.percy.projectspring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
                           HttpServletRequest request) {
        System.out.println("进入这里");
        String accessToken = gitHubProvider.getAccessToken(new AccessTokenDTO(clientId, clientSecret, code, redirectUri, state));
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser!=null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            request.getSession().setAttribute("gitHubUser",gitHubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("gitHubUser");
        return "redirect:/";
    }
}
