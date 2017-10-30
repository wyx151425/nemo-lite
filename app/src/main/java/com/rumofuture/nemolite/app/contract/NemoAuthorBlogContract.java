package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface NemoAuthorBlogContract {

    interface View extends NemoView<NemoAuthorBlogContract.Presenter> {
        void showProgressBar(boolean show);

        void showAuthorBookListGetSuccess(List<Book> bookList);
        void showAuthorBookListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAuthorBookList(User author, int pageCode);
    }
}
