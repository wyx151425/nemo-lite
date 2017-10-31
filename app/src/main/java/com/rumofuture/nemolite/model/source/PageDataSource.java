package com.rumofuture.nemolite.model.source;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public interface PageDataSource {

    int PAGE_LIMIT = 64;

    void savePage(Page page, PageSaveCallback callback);
    void deletePage(Page page, PageDeleteCallback callback);
    void updatePage(Page page, BmobFile newImage, final PageUpdateCallback callback);
    void getPageListByBook(Book book, int pageCode, PageListGetCallback callBack);
    void getBookPageTotal(Book book, TotalGetCallback callback);

    interface PageSaveCallback {
        void onPageSaveSuccess(Page page);
        void onPageSaveFailed(BmobException e);
    }

    interface PageDeleteCallback {
        void onPageDeleteSuccess(Page page);
        void onPageDeleteFailed(BmobException e);
    }

    interface PageUpdateCallback {
        void onPageUpdateSuccess(Page page);
        void onPageUpdateFailed(BmobException e);
    }

    interface PageListGetCallback {
        void onPageListGetSuccess(List<Page> pageList);
        void onPageListGetFailed(BmobException e);
    }

    interface TotalGetCallback {
        void onTotalGetSuccess(Integer total);
        void onTotalGetFailed(BmobException e);
    }
}
