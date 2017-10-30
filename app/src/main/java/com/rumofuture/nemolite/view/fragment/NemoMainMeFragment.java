package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoMainMeContract;
import com.rumofuture.nemolite.app.manager.DataSourceManager;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.presenter.NemoMainMePresenter;
import com.rumofuture.nemolite.view.activity.MyBlogActivity;
import com.rumofuture.nemolite.view.activity.MyBookListActivity;
import com.rumofuture.nemolite.view.activity.MySettingListActivity;

import cn.bmob.v3.BmobUser;

public class NemoMainMeFragment extends Fragment {

    private ImageView mMyAvatarView;
    private TextView mMyNameView;
    private TextView mMyMottoView;

    private NemoMainMeContract.Presenter mPresenter;

    public NemoMainMeFragment() {

    }

    public static NemoMainMeFragment newInstance() {
        return new NemoMainMeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new NemoMainMePresenter(DataSourceManager.provideUserRepository(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_me, container, false);

        mMyAvatarView = view.findViewById(R.id.my_avatar_view);
        mMyNameView = view.findViewById(R.id.my_name_view);
        mMyMottoView = view.findViewById(R.id.my_motto_view);

        TextView myInfoView = view.findViewById(R.id.my_info_view);
        myInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBlogActivity.actionStart(getActivity());
            }
        });

        TextView myBookListView = view.findViewById(R.id.my_book_list_view);
        myBookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBookListActivity.actionStart(getActivity());
            }
        });

        TextView mySettingListView = view.findViewById(R.id.my_setting_list_view);
        mySettingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySettingListActivity.actionStart(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        User myself = BmobUser.getCurrentUser(User.class);
        if (!myself.getAuthorize() && null != myself.getProfile() && !myself.getProfile().equals("")
                && null != myself.getAvatar() && null != myself.getPortrait()) {
            mPresenter.getAuthorization();
        }

        if (null != myself.getAvatar()) {
            Glide.with(getActivity()).load(myself.getAvatar().getUrl()).into(mMyAvatarView);
        }
        mMyNameView.setText(myself.getName());
        mMyMottoView.setText(myself.getMotto());
    }
}
