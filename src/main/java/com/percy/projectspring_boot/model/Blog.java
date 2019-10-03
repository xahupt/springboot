package com.percy.projectspring_boot.model;

import lombok.Data;

@Data
public class Blog {
    private String accountId ;
    private String title ;
    private String content;
    private String tags;
    private long gmtCreate;
    private long gmtModified;
    private int commentCount;
    private int viewCount;
    private int likeCount;
}
