package com.rumofuture.nemolite.model.source.remote;

import com.rumofuture.nemolite.model.entity.Book;
import com.rumofuture.nemolite.model.entity.Page;
import com.rumofuture.nemolite.model.schema.PageSchema;
import com.rumofuture.nemolite.model.source.PageDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PageRemoteDataSource implements PageDataSource {

    private static final int PAGE_LIMIT = 64;

    private static final PageRemoteDataSource sInstance = new PageRemoteDataSource();

    public static PageRemoteDataSource getInstance() {
        return sInstance;
    }

    private PageRemoteDataSource() {

    }

    @Override
    public void savePage(final Page page, final PageSaveCallback callback) {
        final BmobFile image = page.getImage();
        image.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    page.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                page.setObjectId(objectId);
                                callback.onPageSaveSuccess(page);
                            } else {
                                callback.onPageSaveFailed(e);
                                image.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onPageSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deletePage(final Page page, final PageDeleteCallback callback) {
        final BmobFile image = page.getImage();
        page.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onPageDeleteSuccess(page);
                    image.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onPageDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void updatePage(final Page page, final BmobFile newImage, final PageUpdateCallback callback) {
        final BmobFile oldImage = page.getImage();
        newImage.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    page.setImage(newImage);
                    page.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                callback.onPageUpdateSuccess(page);
                                oldImage.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            } else {
                                callback.onPageUpdateFailed(e);
                            }
                        }
                    });
                } else {
                    callback.onPageUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getPageListByBook(Book book, int pageCode, final PageListGetCallback callBack) {
        BmobQuery<Page> query = new BmobQuery<>();
        query.addWhereEqualTo(PageSchema.Table.Cols.BOOK, book);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.order(PageSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Page>() {
            @Override
            public void done(List<Page> pageList, BmobException e) {
                if (e == null) {
                    callBack.onPageListGetSuccess(pageList);
                } else {
                    callBack.onPageListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookPageTotal(Book book, final TotalGetCallback callback) {
        BmobQuery<Page> query = new BmobQuery<>();
        query.addWhereEqualTo(PageSchema.Table.Cols.BOOK, book);
        query.count(Page.class, new CountListener() {
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
