package com.rumofuture.nemolite.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Book extends BmobObject {

    private User author;  // 所属漫画作者

    private String name;  // 名称
    private String style;  // 风格
    private String introduction;  // 简介

    private Integer pageTotal;  // 漫画册漫画分页数

    private BmobFile cover;  // 漫画册封面

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public BmobFile getCover() {
        return cover;
    }

    public void setCover(BmobFile cover) {
        this.cover = cover;
    }
}
