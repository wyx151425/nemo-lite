package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface MyBookListContract {

    interface View extends NemoView<MyBookListContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookListGetSuccess(List<Book> bookList);
        void showBookListGetFailed(BmobException e);

        void showBookDeleteSuccess(Book book);
        void showBookDeleteFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void deleteBook(Book book);
        void updateMyBookTotalOnCreate();
        void updateMyBookTotalOnDelete();
        void getMyBookList(int pageCode);
    }
}
