package com.rumofuture.nemolite.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemolite.app.contract.NemoAuthorBlogContract;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.BookDataSource;
import com.rumofuture.nemolite.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoAuthorBlogPresenter implements NemoAuthorBlogContract.Presenter, BookDataSource.BookListGetCallback {

    private NemoAuthorBlogContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;

    public NemoAuthorBlogPresenter(
            @NonNull NemoAuthorBlogContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorBookList(User author, int pageCode) {
        mView.showProgressBar(true);
        mBookRepository.getBookListByAuthor(author, pageCode, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetSuccess(bookList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
