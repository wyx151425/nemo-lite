package com.rumofuture.nemolite.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.rumofuture.nemolite.view.activity.MyBookInfoActivity;
import com.rumofuture.nemolite.view.fragment.MyBookListFragment;

import java.util.List;

public class MyBookListAdapter extends RecyclerView.Adapter<MyBookListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Book> mBookList;
    private MyBookListFragment mFragment;

    public MyBookListAdapter(List<Book> bookList, MyBookListFragment fragment) {
        mBookList = bookList;
        mFragment = fragment;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_book_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBookInfoActivity.actionStart(mContext, mBookList.get(holder.getAdapterPosition()));
            }
        });

        holder.mBookInfoContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("删除漫画册")
                        .setMessage("您确定要删除吗？")
                        .setCancelable(true)
                        .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mFragment.actionDeleteBook(mBookList.get(holder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton(R.string.prompt_cancel, null)
                        .show();
                return false;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Book book = mBookList.get(position);
        Glide.with(mContext).load(book.getCover().getUrl()).into(holder.mBookCoverView);
        holder.mBookNameView.setText(book.getName());
        holder.mBookIntroductionView.setText(book.getIntroduction());
        holder.mBookStyleView.setText(book.getStyle());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookIntroductionView;
        TextView mBookStyleView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = itemView.findViewById(R.id.book_cover_view);
            mBookNameView = itemView.findViewById(R.id.book_name_view);
            mBookIntroductionView = itemView.findViewById(R.id.book_introduction_view);
            mBookStyleView = itemView.findViewById(R.id.book_style_view);
        }
    }
}
