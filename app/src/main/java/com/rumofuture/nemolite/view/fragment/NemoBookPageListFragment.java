package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoBookPageListContract;
import com.rumofuture.nemolite.app.widget.OnListScrollListener;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.model.source.PageDataSource;
import com.rumofuture.nemolite.view.adapter.NemoBookPageListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoBookPageListFragment extends Fragment implements NemoBookPageListContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemolite.view.fragment.NemoBookPageListFragment.book";

    private NemoBookPageListContract.Presenter mPresenter;

    private Book mBook;
    private List<Page> mPageList;
    private NemoBookPageListAdapter mPageListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NemoBookPageListFragment() {

    }

    public static NemoBookPageListFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoBookPageListFragment fragment = new NemoBookPageListFragment();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
        mPageList = new ArrayList<>();
        mPageListAdapter = new NemoBookPageListAdapter(mPageList);
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
                mPresenter.getBookPageList(mBook, 0);
            }
        });

        RecyclerView pageListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pageListView.setLayoutManager(layoutManager);
        pageListView.setAdapter(mPageListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        pageListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mPresenter.getBookPageList(mBook, 0);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(NemoBookPageListContract.Presenter presenter) {
        mPresenter = presenter;
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
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
