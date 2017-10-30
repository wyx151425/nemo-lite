package com.rumofuture.nemolite.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.MyBookPageListContract;
import com.rumofuture.nemolite.app.widget.OnListScrollListener;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.model.source.PageDataSource;
import com.rumofuture.nemolite.view.adapter.MyBookPageListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class MyBookPageListFragment extends Fragment implements MyBookPageListContract.View {

    private static final String ARG_PARAM = "com.rumofuture.wzq.nemo.view.fragment.MyBookPageListFragment.book";

    private NemoProgressBarFragment mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MyBookPageListContract.Presenter mPresenter;

    private Book mBook;
    private Page mPage;
    private int mIndexOfPageToUpdate = -1;
    private List<Page> mPageList;
    private MyBookPageListAdapter mPageListAdapter;

    private OnListScrollListener mScrollListener;

    public MyBookPageListFragment() {

    }

    public static MyBookPageListFragment newInstance(@NonNull Book book) {
        MyBookPageListFragment fragment = new MyBookPageListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBook = (Book) getArguments().getSerializable(ARG_PARAM);
        mPage = new Page();
        mPage.setBook(mBook);

        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_loading));

        mPageList = new ArrayList<>();
        mPageListAdapter = new MyBookPageListAdapter(mPageList, this);
        mScrollListener = new OnListScrollListener(PageDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getBookPageList(mBook, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_swipe_refresh, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.init();
                mPresenter.getBookPageList(mBook, 0);
            }
        });

        RecyclerView pageListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pageListView.setLayoutManager(layoutManager);
        pageListView.setAdapter(mPageListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        pageListView.addOnScrollListener(mScrollListener);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.uploadPage(mPage);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSwipeRefreshLayout.setRefreshing(true);
        mScrollListener.init();
        mPresenter.getBookPageList(mBook, 0);
    }

    @Override
    public void setPresenter(MyBookPageListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPageSaveSuccess(Page page) {
        Toast.makeText(getActivity(), R.string.prompt_save_success, Toast.LENGTH_LONG).show();
        mPageList.add(page);
        mPageListAdapter.notifyDataSetChanged();
        mPage = new Page();
        mPage.setBook(mBook);
    }

    @Override
    public void showPageSaveFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPageDeleteSuccess(Page page) {
        Toast.makeText(getActivity(), R.string.prompt_delete_success, Toast.LENGTH_LONG).show();
        mPageList.remove(page);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageDeleteFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPageUpdateSuccess(Page page) {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
        mPageList.set(mIndexOfPageToUpdate, page);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPageListGetSuccess(List<Page> pageList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mPageList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mPageList.addAll(pageList);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 291 == requestCode) {
            mPresenter.submitChoice(requestCode, data);
        }
    }

    public void actionDeletePage(Page page) {
        mPresenter.deletePage(page);
    }

    public void actionUpdatePage(Page page, int index) {
        mPresenter.updatePage(page);
        mIndexOfPageToUpdate = index;
    }
}
