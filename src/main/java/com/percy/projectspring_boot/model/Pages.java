package com.percy.projectspring_boot.model;

import com.percy.projectspring_boot.dto.BlogUserDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pages {
    private List<BlogUserDTO> blogUserDTOList;
    private boolean hasPre;
    private boolean hasBack;
    private boolean hasFirstPage;
    private boolean hasEndPage;
    private Integer nowPage;
    private List<Integer> pageList;
    private Integer totalPages;

    public void setPages(long totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalPages = Math.toIntExact(totalCount / size);
        } else {
            totalPages = Math.toIntExact(totalCount / size + 1);

        }
        if (page<1) {
            page = 1;
        }
        if (page>totalPages) {
            page = totalPages;
        }
        nowPage = page;
        pageList = new ArrayList<>();
        pageList.add(page);
        for (int i = 0; i < 3 ; i++) {
            if(page-i-1>0){
                pageList.add(0,page-i-1);
            }
            if (page+i+1<=totalPages) {
                pageList.add(page+i+1);
            }
        }
        //
        if (page == 1) {
            hasPre = false;
        } else {
            hasPre = true;
        }
        if (page == totalPages) {
            hasBack = false;
        } else {
            hasBack = true;
        }

        if (pageList.contains(1)) {
            hasFirstPage = false;
        } else {
            hasFirstPage = true;
        }
        if (pageList.contains(totalPages)) {
            hasEndPage = false;
        } else {
            hasEndPage = true;
        }

    }
}
