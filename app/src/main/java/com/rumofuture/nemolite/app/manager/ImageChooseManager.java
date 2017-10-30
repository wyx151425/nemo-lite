package com.rumofuture.nemolite.app.manager;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rumofuture.nemolite.presenter.NemoImageUploadPresenter;
import com.smile.filechoose.api.ChosenImage;
import com.smile.filechoose.api.ImageChooserListener;
import com.smile.filechoose.api.ImageChooserManager;

/**
 * 图片选择管理器
 * 此类的对象创建时需要传入目标Fragment与目标Presenter；
 * 因为图片选择回调作用于Fragment而不是Activity，所以需要重写Fragment的onActivityResult方法，
 * 而不要重写Activity的onActivityResult方法，否则返回结果的请求码会出错；
 * 图片选择包含三个过程：
 * 1. Fragment的某个事件请求图片选择操作，则调用Presenter的chooseImage方法，
 * 此方法将调用此图片选择管理器的chooseImage方法，执行真正的图片选择过程，请求码为291；
 * 2. 用户选择图片后，回调结果将传到Fragment的onActivityResult方法中，此时Fragment需要调用Presenter的submitChoice方法，
 * 将结果传给Presenter，Presenter获取结果后，调用图片选择管理器的submitChoice方法，由图片选择器进行处理；
 * 3. 图片选择器处理完毕后，调用onImageChosen方法，将处理完毕的图片交给Presenter；
 */
public class ImageChooseManager implements ImageChooserListener {

    private Fragment mFragment;
    private NemoImageUploadPresenter mPresenter;

    private ChosenImage mChosenImage;
    private ImageChooserManager mChooserManager;

    public ImageChooseManager(Fragment fragment, NemoImageUploadPresenter presenter) {
        mFragment = fragment;
        mPresenter = presenter;
    }

    // 第一步 选择图片
    public void chooseImage() {
        if (mChosenImage == null) {
            mChooserManager = new ImageChooserManager(mFragment, 291);
            mChooserManager.setImageChooserListener(this);
            try {
                mChooserManager.choose();  // 执行图片选择操作
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 第二步 对选择图片进行处理
    public void submitChoice(int requestCode, Intent data) {
        if (null == mChooserManager) {
            mChooserManager = new ImageChooserManager(mFragment, 291);
            mChooserManager.setImageChooserListener(this);
        }
        mChooserManager.submit(requestCode, data);  // 进行图片处理操作
    }

    // 第三步 将处理完成的图片传给Presenter
    @Override
    public void onImageChosen(ChosenImage chosenImage) {
        mChosenImage = chosenImage;
        mPresenter.setChosenImage(mChosenImage);
    }

    @Override
    public void onError(String s) {
        mChosenImage = null;
        mPresenter.setChosenImage(null);
    }
}
