package com.rumofuture.nemolite.model.source.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.BookDataSource;

import cn.bmob.v3.datatype.BmobFile;

public class BookLocalDataSource implements BookDataSource {

    private static BookLocalDataSource sInstance;

    private Context mContext;

    public static BookLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new BookLocalDataSource(context);
        return sInstance;
    }

    private BookLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void saveBook(Book book, BookSaveCallback callback) {

    }

    @Override
    public void deleteBook(Book book, BookDeleteCallback callback) {

    }

    @Override
    public void updateBook(Book book, @Nullable BmobFile newCover, BookUpdateCallback callback) {

    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getBookListWithAuthor(int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getAuthorBookTotal(User author, TotalGetCallback callback) {

    }
}
