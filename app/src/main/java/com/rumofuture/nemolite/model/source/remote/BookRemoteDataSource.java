package com.rumofuture.nemolite.model.source.remote;

import android.support.annotation.Nullable;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.schema.BookSchema;
import com.rumofuture.nemolite.model.source.BookDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class BookRemoteDataSource implements BookDataSource {

    private static final BookRemoteDataSource sInstance = new BookRemoteDataSource();

    public static BookRemoteDataSource getInstance() {
        return sInstance;
    }

    private BookRemoteDataSource() {

    }

    @Override
    public void saveBook(final Book book, final BookSaveCallback callback) {
        final BmobFile cover = book.getCover();
        // 用于上传封面图片的方法
        cover.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    // 图片上传成功后，封面对象自动获取Bmob云中地址
                    // 将图片赋值给漫画册对象并进行上传即可
                    book.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                book.setObjectId(objectId);
                                callback.onBookSaveSuccess(book);
                            } else {
                                callback.onBookSaveFailed(e);
                                cover.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onBookSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deleteBook(final Book book, final BookDeleteCallback callback) {
        final BmobFile cover = book.getCover();
        book.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookDeleteSuccess(book);
                    cover.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onBookDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void updateBook(final Book book, @Nullable final BmobFile newCover, final BookUpdateCallback callback) {
        if (null != newCover) {
            newCover.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (null == e) {
                        BmobFile oldCover = book.getCover();
                        oldCover.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                        book.setCover(newCover);
                    } else {
                        callback.onBookUpdateFailed(e);
                    }
                }
            });
        }

        book.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookUpdateSuccess(book);
                } else {
                    callback.onBookUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListWithAuthor(int pageCode, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAuthorBookTotal(User author, final TotalGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        query.count(Book.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }
}
