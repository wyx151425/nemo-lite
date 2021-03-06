package com.rumofuture.nemolite.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.NemoActivity;
import com.rumofuture.nemolite.app.manager.NemoActivityManager;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.view.fragment.NemoBookInfoFragment;

public class NemoBookInfoActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemolite.view.activity.NemoBookInfoActivity.BookObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        NemoBookInfoFragment fragment =
                (NemoBookInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = NemoBookInfoFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
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

    public static void actionStart(Context context, Book book) {
        Intent intent = new Intent();
        intent.setClass(context, NemoBookInfoActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
