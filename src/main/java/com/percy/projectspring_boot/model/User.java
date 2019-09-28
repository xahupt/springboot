package com.percy.projectspring_boot.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;

}
