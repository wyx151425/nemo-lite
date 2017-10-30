package com.rumofuture.nemolite.model.source;

import android.support.annotation.Nullable;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public interface BookDataSource {

    int PAGE_LIMIT = 32;

    // 保存漫画册的方法
    void saveBook(Book book, BookSaveCallback callback);
    // 删除漫画册的方法
    void deleteBook(Book book, BookDeleteCallback callback);
    // 更新漫画册的方法
    void updateBook(Book book, @Nullable BmobFile newCover, BookUpdateCallback callback);

    // 直接获取漫画册信息和漫画册所属漫画家信息的方法
    void getBookListWithAuthor(int pageCode, BookListGetCallback callback);
    // 根据漫画家获得属于此漫画家的漫画册的方法
    void getBookListByAuthor(User author, int pageCode, BookListGetCallback callback);

    void getAuthorBookTotal(User author, TotalGetCallback callback);

    // 漫画册保存回调接口
    interface BookSaveCallback {
        void onBookSaveSuccess(Book book);
        void onBookSaveFailed(BmobException e);
    }

    // 漫画册删除回调接口
    interface BookDeleteCallback {
        void onBookDeleteSuccess(Book book);
        void onBookDeleteFailed(BmobException e);
    }

    // 漫画册删除回调接口
    interface BookUpdateCallback {
        void onBookUpdateSuccess(Book book);
        void onBookUpdateFailed(BmobException e);
    }

    // 漫画册获取回调接口
    interface BookListGetCallback {
        void onBookListGetSuccess(List<Book> bookList);
        void onBookListGetFailed(BmobException e);
    }

    interface TotalGetCallback {
        void onTotalGetSuccess(Integer total);
        void onTotalGetFailed(BmobException e);
    }
}
