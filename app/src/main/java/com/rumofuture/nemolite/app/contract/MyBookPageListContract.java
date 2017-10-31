package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.presenter.NemoImageUploadPresenter;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface MyBookPageListContract {

    interface View extends NemoView<MyBookPageListContract.Presenter> {
        void showProgressBar(boolean show, int stringId);

        void showPageSaveSuccess(Page page);
        void showPageSaveFailed(BmobException e);

        void showPageDeleteSuccess(Page page);
        void showPageDeleteFailed(BmobException e);

        void showPageUpdateSuccess(Page page);
        void showPageUpdateFailed(BmobException e);

        void showPageListGetSuccess(List<Page> pageList);
        void showPageListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoImageUploadPresenter {
        void uploadPage(Page page);
        void deletePage(Page page);
        void updatePage(Page page);
        void getBookPageList(Book book, int pageCode);
    }
}
