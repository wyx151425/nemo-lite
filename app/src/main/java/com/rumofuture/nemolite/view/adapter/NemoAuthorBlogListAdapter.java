package com.rumofuture.nemolite.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.view.activity.NemoAuthorBookInfoActivity;

import java.util.List;

public class NemoAuthorBlogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_AUTHOR = 0;
    private static final int TYPE_BOOK = 1;

    private Context mContext;
    private User mAuthor;
    private List<Book> mBookList;

    public NemoAuthorBlogListAdapter(User author, List<Book> bookList) {
        mAuthor = author;
        mBookList = bookList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        if (TYPE_AUTHOR == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_author_info, parent, false);
            return new AuthorViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_author_book_list, parent, false);
            final BookViewHolder holder = new BookViewHolder(view);

            holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = mBookList.get(holder.getAdapterPosition() - 1);
                    book.setAuthor(mAuthor);
                    NemoAuthorBookInfoActivity.actionStart(mContext, book);
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (0 == position) {
            AuthorViewHolder authorViewHolder = (AuthorViewHolder) holder;
            authorViewHolder.mAuthorProfileView.setText(mAuthor.getProfile());
        } else {
            Book book = mBookList.get(position - 1);
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
            Glide.with(mContext).load(book.getCover().getUrl()).into(bookViewHolder.mBookCoverView);
            bookViewHolder.mBookNameView.setText(book.getName());
            bookViewHolder.mBookStyleView.setText(book.getStyle());
            bookViewHolder.mBookIntroductionView.setText(book.getIntroduction());
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_AUTHOR;
        } else {
            return TYPE_BOOK;
        }
    }

    private static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorProfileView;
        AuthorViewHolder(View itemView) {
            super(itemView);
            mAuthorProfileView = (TextView) itemView.findViewById(R.id.author_profile_view);
        }
    }

    private static class BookViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookStyleView;
        TextView mBookIntroductionView;

        BookViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = itemView.findViewById(R.id.book_cover_view);
            mBookNameView = itemView.findViewById(R.id.book_name_view);
            mBookStyleView = itemView.findViewById(R.id.book_style_view);
            mBookIntroductionView = itemView.findViewById(R.id.book_introduction_view);
        }
    }
}
