package com.rumofuture.nemolite.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.rumofuture.nemolite.app.contract.MyBookListContract;
import com.rumofuture.nemolite.app.widget.OnListScrollListener;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.source.BookDataSource;
import com.rumofuture.nemolite.view.activity.MyBookCreateActivity;
import com.rumofuture.nemolite.view.adapter.MyBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyBookListFragment extends Fragment implements MyBookListContract.View {

    private static final int REQUEST_BOOK = 710;

    private MyBookListContract.Presenter mPresenter;

    private List<Book> mBookList;
    private MyBookListAdapter mBookListAdapter;

    private NemoProgressBarFragment mProgressBar;
    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MyBookListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookList = new ArrayList<>();
        mBookListAdapter = new MyBookListAdapter(mBookList, this);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getMyBookList(pageCode);
            }
        };
    }

    public static MyBookListFragment newInstance() {
        return new MyBookListFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBookCreateActivity.actionStart(getActivity(), REQUEST_BOOK);
            }
        });

        mProgressBar = NemoProgressBarFragment.newInstance(getResources().getString(R.string.prompt_deleting));

        View view = inflater.inflate(R.layout.fragment_nemo_swipe_refresh, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.init();
                mPresenter.getMyBookList(0);
            }
        });

        final RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getMyBookList(0);
    }

    @Override
    public void setPresenter(MyBookListContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void showBookListGetSuccess(List<Book> bookList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mBookList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mBookList.addAll(bookList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBookListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBookDeleteSuccess(Book book) {
        mBookList.remove(book);
        mBookListAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), R.string.prompt_delete_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBookDeleteFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_delete_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode) {
            return;
        }
        if (REQUEST_BOOK == requestCode) {
            Book book = (Book) data.getSerializableExtra(MyBookCreateFragment.EXTRA_BOOK);
            mBookList.add(book);
            mBookListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), R.string.prompt_create_success, Toast.LENGTH_LONG).show();
            mPresenter.updateMyBookTotalOnCreate();
        }
    }

    public void actionDeleteBook(Book book) {
        mPresenter.deleteBook(book);
    }
}
