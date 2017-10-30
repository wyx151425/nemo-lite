package com.rumofuture.nemolite.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class PageRepository implements PageDataSource {

    private static PageRepository sInstance;
    private final PageDataSource mPageLocalDataSource;
    private final PageDataSource mPageRemoteDataSource;

    public static PageRepository getInstance(
            @NonNull PageDataSource pageLocalDataSource,
            @NonNull PageDataSource pageRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new PageRepository(pageLocalDataSource, pageRemoteDataSource);
        }
        return sInstance;
    }

    private PageRepository(
            @NonNull PageDataSource pageLocalDataSource,
            @NonNull PageDataSource pageRemoteDataSource
    ) {
        mPageLocalDataSource = pageLocalDataSource;
        mPageRemoteDataSource = pageRemoteDataSource;
    }

    @Override
    public void savePage(Page page, PageSaveCallback callback) {
        mPageRemoteDataSource.savePage(page, callback);
    }

    @Override
    public void deletePage(Page page, PageDeleteCallback callback) {
        mPageRemoteDataSource.deletePage(page, callback);
    }

    @Override
    public void updatePage(Page page, BmobFile newImage, PageUpdateCallback callback) {
        mPageRemoteDataSource.updatePage(page, newImage, callback);
    }

    @Override
    public void getPageListByBook(Book book, int pageCode, PageListGetCallback callBack) {
        mPageRemoteDataSource.getPageListByBook(book, pageCode, callBack);
    }

    @Override
    public void getPageTotal(Book book, TotalGetCallback callback) {
        mPageRemoteDataSource.getPageTotal(book, callback);
    }
}
