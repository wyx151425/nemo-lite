package com.rumofuture.nemolite.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.NemoActivity;
import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyBlogActivity extends NemoActivity {

    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private AppBarLayout mAppBar;
    private FrameLayout mMyInfoContainer;
    private LinearLayout mMyInfoLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitleView;

    private TextView mMyNameView;
    private TextView mMyMottoView;
    private TextView mMyProfileView;
    private CircleImageView mMyAvatarView;
    private ImageView mMyPortraitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog);

        initView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfoEditActivity.actionStart(MyBlogActivity.this);
            }
        });

        // AppBar的监听
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });

        initParallaxValues(); // 自动滑动效果
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mMyPortraitView.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mMyInfoContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mMyPortraitView.setLayoutParams(petDetailsLp);
        mMyInfoContainer.setLayoutParams(petBackgroundLp);
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mToolbarTitleView, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mToolbarTitleView, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mMyInfoLayout, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mMyAvatarView, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mMyInfoLayout, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mMyAvatarView, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View view, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private void initView() {
        mAppBar = findViewById(R.id.app_bar);
        mMyInfoContainer = findViewById(R.id.my_info_container);
        mMyInfoLayout = findViewById(R.id.my_info_layout);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbarTitleView = findViewById(R.id.toolbar_title_view);

        mMyNameView = findViewById(R.id.my_name_view);
        mMyMottoView = findViewById(R.id.my_motto_view);
        mMyProfileView = findViewById(R.id.my_profile_view);
        mMyAvatarView = findViewById(R.id.my_avatar_view);
        mMyPortraitView = findViewById(R.id.my_portrait_view);
    }

    private void initData() {
        User myself = BmobUser.getCurrentUser(User.class);

        mMyNameView.setText(myself.getName());
        mMyMottoView.setText(myself.getMotto());
        mMyProfileView.setText(myself.getProfile());

        if (null != myself.getAvatar()) {
            Glide.with(MyBlogActivity.this).load(myself.getAvatar().getUrl()).into(mMyAvatarView);
        }

        if (null != myself.getPortrait()) {
            Glide.with(MyBlogActivity.this).load(myself.getPortrait().getUrl()).into(mMyPortraitView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyBlogActivity.class);
        context.startActivity(intent);
    }
}
