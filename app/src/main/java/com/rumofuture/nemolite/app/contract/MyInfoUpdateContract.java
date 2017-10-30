package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.presenter.NemoImageUploadPresenter;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public interface MyInfoUpdateContract {

    interface View extends NemoView<Presenter> {
        void showProgressBar(boolean show);

        void showUserAvatarUpdateSuccess(BmobFile avatar);
        void showUserAvatarUpdateFailed(BmobException e);

        void showUserPortraitUpdateSuccess(BmobFile portrait);
        void showUserPortraitUpdateFailed(BmobException e);

        void showUserInfoUpdateSuccess();
        void showUserInfoUpdateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoImageUploadPresenter {
        void updateUserAvatar();
        void updateUserPortrait();

        void updateUserInfo(User user);
    }
}
