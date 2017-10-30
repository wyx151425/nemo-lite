package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface NemoBookPageListContract {

    interface View extends NemoView<NemoBookPageListContract.Presenter> {
        void showPageListGetSuccess(List<Page> pageList);
        void showPageListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getBookPageList(Book book, int pageCode);
    }
}
