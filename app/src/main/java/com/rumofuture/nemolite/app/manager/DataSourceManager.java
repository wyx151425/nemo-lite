package com.rumofuture.nemolite.app.manager;

import android.content.Context;

import com.rumofuture.nemolite.model.source.BookRepository;
import com.rumofuture.nemolite.model.source.PageRepository;
import com.rumofuture.nemolite.model.source.UserRepository;
import com.rumofuture.nemolite.model.source.local.BookLocalDataSource;
import com.rumofuture.nemolite.model.source.local.PageLocalDataSource;
import com.rumofuture.nemolite.model.source.local.UserLocalDataSource;
import com.rumofuture.nemolite.model.source.remote.BookRemoteDataSource;
import com.rumofuture.nemolite.model.source.remote.PageRemoteDataSource;
import com.rumofuture.nemolite.model.source.remote.UserRemoteDataSource;

public class DataSourceManager {

    public static UserRepository provideUserRepository(Context context) {
        return UserRepository.getInstance(
                UserLocalDataSource.getInstance(context),
                UserRemoteDataSource.getInstance()
        );
    }

    public static BookRepository provideBookRepository(Context context) {
        return BookRepository.getInstance(
                BookLocalDataSource.getInstance(context),
                BookRemoteDataSource.getInstance()
        );
    }

    public static PageRepository providePageRepository(Context context) {
        return PageRepository.getInstance(
                PageLocalDataSource.getInstance(context),
                PageRemoteDataSource.getInstance()
        );
    }
}
