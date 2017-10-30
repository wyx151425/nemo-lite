package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoAuthorBlogContract;
import com.rumofuture.nemolite.app.widget.OnListScrollListener;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.BookDataSource;
import com.rumofuture.nemolite.view.adapter.NemoAuthorBlogListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoAuthorBlogFragment extends Fragment implements NemoAuthorBlogContract.View {

    private static final String ARG_AUTHOR = "com.rumofuture.nemo.view.fragment.NemoAuthorBlogFragment.author";

    private NemoAuthorBlogContract.Presenter mPresenter;

    private User mAuthor;
    private List<Book> mBookList;
    private NemoAuthorBlogListAdapter mBookListAdapter;

    private OnListScrollListener mScrollListener;

    public NemoAuthorBlogFragment() {

    }

    public static NemoAuthorBlogFragment newInstance(User author) {
        Bundle args = new Bundle();
        NemoAuthorBlogFragment fragment = new NemoAuthorBlogFragment();
        args.putSerializable(ARG_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAuthor = (User) getArguments().getSerializable(ARG_AUTHOR);
        }

        mBookList = new ArrayList<>();
        mBookListAdapter = new NemoAuthorBlogListAdapter(mAuthor, mBookList);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAuthorBookList(mAuthor, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        mScrollListener.init();
        mPresenter.getAuthorBookList(mAuthor, 0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBookList.clear();
    }

    @Override
    public void setPresenter(NemoAuthorBlogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showAuthorBookListGetSuccess(List<Book> bookList) {
        mBookList.addAll(bookList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAuthorBookListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
