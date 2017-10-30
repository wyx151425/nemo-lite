package com.rumofuture.nemolite.model.source.local;

import android.content.Context;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.model.source.PageDataSource;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class PageLocalDataSource implements PageDataSource {

    private static PageLocalDataSource sInstance;
    private Context mContext;

    public static PageLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new PageLocalDataSource(context);
        return sInstance;
    }

    private PageLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void savePage(Page page, PageSaveCallback callback) {

    }

    @Override
    public void deletePage(Page page, PageDeleteCallback callback) {

    }

    @Override
    public void updatePage(Page page, BmobFile newImage, PageUpdateCallback callback) {

    }

    @Override
    public void getPageListByBook(Book book, int pageCode, PageListGetCallback callBack) {

    }

    @Override
    public void getPageTotal(Book book, TotalGetCallback callback) {

    }
}
