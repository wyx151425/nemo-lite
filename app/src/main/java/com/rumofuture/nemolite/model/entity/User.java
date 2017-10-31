package com.rumofuture.nemolite.model.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {

    private String name;  // 姓名
    private String motto;  // 座右铭
    private String profile;  // 简介

    private String gender;  // 性别
    private String birthday;  // 生日

    private Integer bookTotal;  // 漫画册数量

    private BmobFile avatar;  // 头像
    private BmobFile portrait;  // 个人肖像

    private Boolean authorize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getBookTotal() {
        return bookTotal;
    }

    public void setBookTotal(Integer bookTotal) {
        this.bookTotal = bookTotal;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobFile getPortrait() {
        return portrait;
    }

    public void setPortrait(BmobFile portrait) {
        this.portrait = portrait;
    }

    public Boolean getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Boolean authorize) {
        this.authorize = authorize;
    }
}
