package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.view.activity.NemoBookPageListActivity;

public class NemoAuthorBookInfoFragment extends Fragment {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.NemoBookInfoFragment.book";

    private Book mBook;

    public NemoAuthorBookInfoFragment() {

    }

    public static NemoAuthorBookInfoFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoAuthorBookInfoFragment fragment = new NemoAuthorBookInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_nemo_book_info, container, false);

        CardView bookInfoContainer = view.findViewById(R.id.book_info_container);
        ImageView bookCover = view.findViewById(R.id.book_cover_view);
        TextView bookName = view.findViewById(R.id.book_name_view);
        TextView bookIntroduction = view.findViewById(R.id.book_introduction_view);
        TextView bookStyle = view.findViewById(R.id.book_style_view);

        bookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoBookPageListActivity.actionStart(getActivity(), mBook);
            }
        });

        Glide.with(getActivity()).load(mBook.getCover().getUrl()).into(bookCover);
        bookName.setText(mBook.getName());
        bookIntroduction.setText(mBook.getIntroduction());
        bookStyle.setText(mBook.getStyle());

        return view;
    }
}