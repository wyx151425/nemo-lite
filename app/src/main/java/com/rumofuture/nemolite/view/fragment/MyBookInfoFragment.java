package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.model.entity.Book;

public class MyBookInfoFragment extends Fragment {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.MyBookInfoFragment.book";

    private Book mBook;

    public MyBookInfoFragment() {

    }

    public static MyBookInfoFragment newInstance(Book book) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        MyBookInfoFragment fragment = new MyBookInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_book_info, container, false);

        ImageView bookCoverView = (ImageView) view.findViewById(R.id.book_cover_view);
        TextView bookNameView = view.findViewById(R.id.book_name_view);
        TextView bookIntroductionView = view.findViewById(R.id.book_introduction_view);
        TextView bookStyleView = view.findViewById(R.id.book_style_view);

        Glide.with(getActivity()).load(mBook.getCover().getUrl()).into(bookCoverView);
        bookNameView.setText(mBook.getName());
        bookIntroductionView.setText(mBook.getIntroduction());
        bookStyleView.setText(mBook.getStyle());

        return view;
    }
}
