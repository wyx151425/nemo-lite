package com.rumofuture.nemolite.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.datatype.BmobFile;

public class BookRepository implements BookDataSource {

    private static BookRepository sInstance;

    private final BookDataSource mBookLocalDataSource;
    private final BookDataSource mBookRemoteDataSource;

    public static BookRepository getInstance(
            @NonNull BookDataSource nemoLocalDataSource,
            @NonNull BookDataSource nemoRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new BookRepository(nemoLocalDataSource, nemoRemoteDataSource);
        }
        return sInstance;
    }

    private BookRepository(
            @NonNull BookDataSource bookLocalDataSource,
            @NonNull BookDataSource bookRemoteDataSource
    ) {
        mBookLocalDataSource = bookLocalDataSource;
        mBookRemoteDataSource = bookRemoteDataSource;
    }

    @Override
    public void saveBook(Book book, BookSaveCallback callback) {
        mBookRemoteDataSource.saveBook(book, callback);
    }

    @Override
    public void deleteBook(Book book, BookDeleteCallback callback) {
        mBookRemoteDataSource.deleteBook(book, callback);
    }

    @Override
    public void updateBook(Book book, @Nullable BmobFile newCover, BookUpdateCallback callback) {
        mBookRemoteDataSource.updateBook(book, newCover, callback);
    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, BookListGetCallback callback) {
        mBookRemoteDataSource.getBookListByAuthor(author, pageCode, callback);
    }

    @Override
    public void getBookListWithAuthor(int pageCode, BookListGetCallback callback) {
        mBookRemoteDataSource.getBookListWithAuthor(pageCode, callback);
    }

    @Override
    public void getAuthorBookTotal(User author, TotalGetCallback callback) {
        mBookRemoteDataSource.getAuthorBookTotal(author, callback);
    }
}
