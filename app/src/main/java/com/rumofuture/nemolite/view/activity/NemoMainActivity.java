package com.rumofuture.nemolite.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.NemoActivity;
import com.rumofuture.nemolite.app.manager.DataSourceManager;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.presenter.NemoMainDiscoverPresenter;
import com.rumofuture.nemolite.presenter.NemoMainPresenter;
import com.rumofuture.nemolite.view.fragment.NemoMainDiscoverFragment;
import com.rumofuture.nemolite.view.fragment.NemoMainFragment;
import com.rumofuture.nemolite.view.fragment.NemoMainMeFragment;
import com.rumofuture.nemolite.view.fragment.NemoMainWelcomeFragment;

import java.lang.reflect.Method;

import cn.bmob.v3.BmobUser;

public class NemoMainActivity extends NemoActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_LOG = "com.rumofuture.nemolite.view.activity.NemoMainActivity.log";

    private NemoMainFragment mMainFragment;
    private NemoMainDiscoverFragment mDiscoverFragment;
    private NemoMainMeFragment mMeFragment;
    private NemoMainWelcomeFragment mWelcomeFragment;

    private int navigationIndex = 0;

    private static final int NAVIGATION_MAIN_INDEX = 0;
    private static final int NAVIGATION_DISCOVER_INDEX = 1;
    private static final int NAVIGATION_ME_INDEX = 2;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isLog = getIntent().getBooleanExtra(EXTRA_LOG, false);
        if (isLog) {
            navigationIndex = 2;
            mBottomNavigationView.setSelectedItemId(R.id.navigation_me);
            showFragment(navigationIndex);
        } else {
            showFragment(navigationIndex);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_main:
                showFragment(NAVIGATION_MAIN_INDEX);
                return true;
            case R.id.navigation_discover:
                showFragment(NAVIGATION_DISCOVER_INDEX);
                return true;
            case R.id.navigation_me:
                showFragment(NAVIGATION_ME_INDEX);
                return true;
        }
        return false;
    }

    public static void actionStart(Context context, boolean isLogOut) {
        Intent intent = new Intent();
        intent.setClass(context, NemoMainActivity.class);
        intent.putExtra(EXTRA_LOG, isLogOut);
        context.startActivity(intent);
    }

    public void showFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        navigationIndex = index;
        switch (index) {
            case NAVIGATION_MAIN_INDEX:
                if (null == mMainFragment) {
                    mMainFragment = NemoMainFragment.newInstance();
                    NemoMainPresenter presenter = new NemoMainPresenter(
                            mMainFragment,
                            DataSourceManager.provideBookRepository(NemoMainActivity.this)
                    );
                    mMainFragment.setPresenter(presenter);
                    transaction.add(R.id.fragment_container, mMainFragment);
                } else {
                    transaction.show(mMainFragment);
                }
                break;
            case NAVIGATION_DISCOVER_INDEX:
                if (null == mDiscoverFragment) {
                    mDiscoverFragment = NemoMainDiscoverFragment.newInstance();
                    NemoMainDiscoverPresenter presenter = new NemoMainDiscoverPresenter(
                            mDiscoverFragment,
                            DataSourceManager.provideUserRepository(NemoMainActivity.this)
                    );
                    mDiscoverFragment.setPresenter(presenter);
                    transaction.add(R.id.fragment_container, mDiscoverFragment);
                } else {
                    transaction.show(mDiscoverFragment);
                }
                break;
            case NAVIGATION_ME_INDEX:
                if (null == BmobUser.getCurrentUser(User.class)) {
                    if (null != mMeFragment) {
                        transaction.remove(mMeFragment);
                    }
                    if (null == mWelcomeFragment) {
                        mWelcomeFragment = NemoMainWelcomeFragment.newInstance();
                        transaction.add(R.id.fragment_container, mWelcomeFragment);
                    } else {
                        transaction.show(mWelcomeFragment);
                    }
                } else {
                    if (null != mWelcomeFragment) {
                        transaction.remove(mWelcomeFragment);
                    }
                    if (null == mMeFragment) {
                        mMeFragment = NemoMainMeFragment.newInstance();
                        transaction.add(R.id.fragment_container, mMeFragment);
                    } else {
                        transaction.show(mMeFragment);
                    }
                }
                break;
        }
        transaction.commit();
    }

    public void hideFragment(FragmentTransaction transaction) {
        if (null != mMainFragment) {
            transaction.hide(mMainFragment);
        }
        if (null != mDiscoverFragment) {
            transaction.hide(mDiscoverFragment);
        }
        if (null != mMeFragment) {
            transaction.hide(mMeFragment);
        }
        if (null != mWelcomeFragment) {
            transaction.hide(mWelcomeFragment);
        }
    }
}
