package com.rumofuture.nemolite.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.rumofuture.nemolite.app.manager.DataSourceManager;
import com.rumofuture.nemolite.app.manager.NemoActivityManager;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.presenter.NemoAuthorBlogPresenter;
import com.rumofuture.nemolite.view.fragment.NemoAuthorBlogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class NemoAuthorBlogActivity extends NemoActivity {

    private static final String EXTRA_AUTHOR = "com.rumofuture.nemo.view.activity.NemoAuthorBlogActivity.author";

    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private User mAuthor;

    private AppBarLayout mAppBar;
    private FrameLayout mAuthorInformationForm;
    private LinearLayout mAuthorInformationContainer;
    private Toolbar mToolbar;
    private TextView mToolbarTitleView;

    private TextView mAuthorNameView;
    private TextView mAuthorMottoView;
    private CircleImageView mAuthorAvatarView;
    private ImageView mAuthorPortraitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_author_blog);

        initView();

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

        NemoAuthorBlogFragment fragment =
                (NemoAuthorBlogFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = NemoAuthorBlogFragment.newInstance(mAuthor);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoAuthorBlogPresenter presenter = new NemoAuthorBlogPresenter(
                fragment,
                DataSourceManager.provideUserRepository(NemoAuthorBlogActivity.this),
                DataSourceManager.provideBookRepository(NemoAuthorBlogActivity.this)
        );
        fragment.setPresenter(presenter);
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mAuthorPortraitView.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mAuthorInformationForm.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mAuthorPortraitView.setLayoutParams(petDetailsLp);
        mAuthorInformationForm.setLayoutParams(petBackgroundLp);
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
                startAlphaAnimation(mAuthorInformationContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mAuthorAvatarView, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mAuthorInformationContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mAuthorAvatarView, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void initView() {
        mAppBar = findViewById(R.id.app_bar);
        mAuthorInformationForm = findViewById(R.id.author_info_container);
        mAuthorInformationContainer = findViewById(R.id.author_info_layout);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mToolbarTitleView = findViewById(R.id.toolbar_title);
        mAuthorNameView = findViewById(R.id.author_name_view);
        mAuthorMottoView = findViewById(R.id.author_motto_view);
        mAuthorAvatarView = findViewById(R.id.author_avatar_view);
        mAuthorPortraitView = findViewById(R.id.author_portrait_view);

        mAuthor = (User) getIntent().getSerializableExtra(EXTRA_AUTHOR);

        mToolbarTitleView.setText(mAuthor.getName());
        mAuthorNameView.setText(mAuthor.getName());
        mAuthorMottoView.setText(mAuthor.getMotto());
        Glide.with(NemoAuthorBlogActivity.this).load(mAuthor.getAvatar().getUrl()).into(mAuthorAvatarView);

        if (null != mAuthor.getPortrait()) {
            Glide.with(NemoAuthorBlogActivity.this).load(mAuthor.getPortrait().getUrl()).into(mAuthorPortraitView);
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

    public static void actionStart(Context context, User author) {
        Intent intent = new Intent();
        intent.setClass(context, NemoAuthorBlogActivity.class);
        intent.putExtra(EXTRA_AUTHOR, author);
        context.startActivity(intent);
    }
}
