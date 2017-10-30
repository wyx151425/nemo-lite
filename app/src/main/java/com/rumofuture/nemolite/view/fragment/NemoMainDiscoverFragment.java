package com.rumofuture.nemolite.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoMainDiscoverContract;
import com.rumofuture.nemolite.app.widget.OnMainScrollListener;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.UserDataSource;
import com.rumofuture.nemolite.view.adapter.NemoMainAuthorListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoMainDiscoverFragment extends Fragment implements NemoMainDiscoverContract.View {

    private NemoMainDiscoverContract.Presenter mPresenter;

    private ProgressBar mProgressBar;

    private List<User> mAuthorList;
    private NemoMainAuthorListAdapter mAuthorListAdapter;
    private RecyclerView mAuthorListView;

    private int mPageCode = 0;
    private boolean mQueryable = true;

    public NemoMainDiscoverFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorList = new ArrayList<>();
        mAuthorListAdapter = new NemoMainAuthorListAdapter(mAuthorList);
    }

    public static NemoMainDiscoverFragment newInstance() {
        return new NemoMainDiscoverFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_discover, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar_view);

        mAuthorListView = view.findViewById(R.id.author_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAuthorListView.setLayoutManager(layoutManager);
        mAuthorListView.setAdapter(mAuthorListAdapter);
        mAuthorListView.addOnScrollListener(new OnMainScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (mQueryable) {
                    mQueryable = false;
                    mPresenter.getAuthorList(mPageCode);
                }
            }
        });

        mPresenter.getAuthorList(mPageCode);

        return view;
    }

    @Override
    public void setPresenter(NemoMainDiscoverContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAuthorListGetSuccess(List<User> authorList) {
        // 当pageCode为0时，此时界面可能已刷新，需要将之前的数据全部删除掉，否则会出现重复数据项
        // 当pageCode不为0时，此时界面正在等待获取下一个数据分页，所以将数据项直接追加
        mQueryable = authorList.size() >= UserDataSource.PAGE_LIMIT;
        if (0 == mPageCode) {
            mAuthorList.clear();
        }
        mAuthorList.addAll(authorList);
        mAuthorListAdapter.notifyDataSetChanged();
        mPageCode++;
    }

    @Override
    public void showAuthorListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressBar(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mAuthorListView.setVisibility(show ? View.GONE : View.VISIBLE);
        mAuthorListView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAuthorListView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
