package com.rumofuture.nemolite.presenter;

import com.rumofuture.nemolite.app.contract.NemoBookPageListContract;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.model.source.PageDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoBookPageListPresenter implements NemoBookPageListContract.Presenter, PageDataSource.PageListGetCallback {

    private NemoBookPageListContract.View mView;
    private PageDataSource mPageRepository;

    public NemoBookPageListPresenter(NemoBookPageListContract.View view, PageDataSource pageRepository) {
        mView = view;
        mPageRepository = pageRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getBookPageList(Book book, int pageCode) {
        mPageRepository.getPageListByBook(book, pageCode, this);
    }

    @Override
    public void onPageListGetSuccess(List<Page> pageList) {
        if (mView.isActive()) {
            mView.showPageListGetSuccess(pageList);
        }
    }

    @Override
    public void onPageListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showPageListGetFailed(e);
        }
    }
}
