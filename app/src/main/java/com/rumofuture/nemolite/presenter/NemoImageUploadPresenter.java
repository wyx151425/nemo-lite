package com.rumofuture.nemolite.presenter;

import android.content.Intent;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.smile.filechoose.api.ChosenImage;

public interface NemoImageUploadPresenter extends NemoPresenter {
    void chooseImage();
    void submitChoice(int requestCode, Intent data);
    void setChosenImage(ChosenImage chosenImage);
    void releaseImageChooseManager();
}
