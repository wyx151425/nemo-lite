package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface NemoMainContract {

    interface View extends NemoView<NemoMainContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookListGetSuccess(List<Book> bookList);
        void showBookListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getBookList(int pageCode);
    }
}
