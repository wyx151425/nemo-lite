package com.rumofuture.nemolite.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public class Page extends BmobObject {

    private Book book;  // 所属漫画册
    private BmobFile image;  // 图像

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
