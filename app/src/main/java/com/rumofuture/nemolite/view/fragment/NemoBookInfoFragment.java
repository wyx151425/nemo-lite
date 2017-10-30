package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.view.activity.NemoAuthorBlogActivity;
import com.rumofuture.nemolite.view.activity.NemoBookPageListActivity;

public class NemoBookInfoFragment extends Fragment {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.NemoBookInfoFragment.book";

    private Book mBook;

    public NemoBookInfoFragment() {

    }

    public static NemoBookInfoFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoBookInfoFragment fragment = new NemoBookInfoFragment();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取Activity启动此Fragment时传递的Book对象
        if (null != getArguments()) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_book_info_with_author, container, false);

        CardView bookInfoContainer = view.findViewById(R.id.book_info_container);
        ImageView bookCover = view.findViewById(R.id.book_cover_view);
        TextView bookName = view.findViewById(R.id.book_name_view);
        TextView bookIntroduction = view.findViewById(R.id.book_introduction_view);
        TextView bookStyle = view.findViewById(R.id.book_style_view);

        LinearLayout authorInfoContainer = view.findViewById(R.id.author_info_container);
        ImageView authorAvatar = view.findViewById(R.id.author_avatar_view);
        TextView authorName = view.findViewById(R.id.author_name_view);

        bookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoBookPageListActivity.actionStart(getActivity(), mBook);
            }
        });

        authorInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoAuthorBlogActivity.actionStart(getActivity(), mBook.getAuthor());
            }
        });

        Glide.with(getActivity()).load(mBook.getCover().getUrl()).into(bookCover);
        bookName.setText(mBook.getName());
        bookIntroduction.setText(mBook.getIntroduction());
        bookStyle.setText(mBook.getStyle());
        Glide.with(getActivity()).load(mBook.getAuthor().getAvatar().getUrl()).into(authorAvatar);
        authorName.setText(mBook.getAuthor().getName());

        return view;
    }
}
