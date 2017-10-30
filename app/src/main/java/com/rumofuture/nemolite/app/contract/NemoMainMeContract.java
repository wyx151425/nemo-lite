package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;

public interface NemoMainMeContract {

    interface View extends NemoView<NemoMainMeContract.Presenter> {
        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAuthorization();
    }
}
