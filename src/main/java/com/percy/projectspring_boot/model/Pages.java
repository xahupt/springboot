package com.percy.projectspring_boot.model;

import lombok.Data;

import java.util.List;

@Data
public class Pages {
    private boolean hasPre;
    private boolean hasBack;
    private int nowPage;
    private List<Integer> pageList;
}
