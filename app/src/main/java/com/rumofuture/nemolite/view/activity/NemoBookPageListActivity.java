package com.rumofuture.nemolite.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.NemoActivity;
import com.rumofuture.nemolite.app.manager.DataSourceManager;
import com.rumofuture.nemolite.app.manager.NemoActivityManager;
import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.presenter.NemoBookPageListPresenter;
import com.rumofuture.nemolite.view.fragment.NemoBookPageListFragment;

public class NemoBookPageListActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.NemoBookPageListActivity.BookObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        NemoBookPageListFragment fragment = (NemoBookPageListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = NemoBookPageListFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.fragment_container);
        }

        // Create the presenter
        NemoBookPageListPresenter presenter = new NemoBookPageListPresenter(
                fragment,
                DataSourceManager.providePageRepository(NemoBookPageListActivity.this)
        );

        fragment.setPresenter(presenter);
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
        intent.setClass(context, NemoBookPageListActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
