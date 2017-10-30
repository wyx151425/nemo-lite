package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.presenter.NemoImageUploadPresenter;

import cn.bmob.v3.exception.BmobException;

public interface MyBookCreateContract {

    interface View extends NemoView<MyBookCreateContract.Presenter> {
        void showBookInfoError(int stringId);
        void showBookCoverHasChosen(String imagePath);

        void showProgressBar(boolean show);

        void showBookCreateSuccess(Book book);
        void showBookCreateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoImageUploadPresenter {
        void getAuthorization();
        void createBook(Book book);
    }
}
