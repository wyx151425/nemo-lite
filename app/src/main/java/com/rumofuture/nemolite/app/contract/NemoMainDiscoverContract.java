package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public interface NemoMainDiscoverContract {

    interface View extends NemoView<NemoMainDiscoverContract.Presenter> {
        void showProgressBar(boolean show);

        void showAuthorListGetSuccess(List<User> authorList);
        void showAuthorListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAuthorList(int pageCode);
    }
}
