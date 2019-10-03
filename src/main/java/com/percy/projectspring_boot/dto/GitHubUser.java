package com.percy.projectspring_boot.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String login;
    private long id;
    private String bio;
    private String name;
    private String avatar_url;


}
