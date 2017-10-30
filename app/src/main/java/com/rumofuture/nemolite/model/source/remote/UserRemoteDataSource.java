package com.rumofuture.nemolite.model.source.remote;

import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.schema.UserSchema;
import com.rumofuture.nemolite.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static final UserRemoteDataSource sInstance = new UserRemoteDataSource();

    public static UserRemoteDataSource getInstance() {
        return sInstance;
    }

    private UserRemoteDataSource() {

    }

    @Override
    public void logIn(User user, final UserLogInCallback callback) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    callback.onUserLogInSuccess(user);
                } else {
                    callback.onUserLogInFailed(e);
                }
            }
        });
    }

    @Override
    public void signUp(User user, final UserSignUpCallback callback) {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    callback.onUserSignUpSuccess(user);
                } else {
                    callback.onUserSignUpFailed(e);
                }
            }
        });
    }

    @Override
    public void updateUserAvatar(final BmobFile newAvatar, final UserImageUpdateCallback callback) {
        final User currentUser = BmobUser.getCurrentUser(User.class);

        final User targetUser = new User();
        targetUser.setObjectId(currentUser.getObjectId());

        final BmobFile oldAvatar = currentUser.getAvatar();

        newAvatar.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    targetUser.setAvatar(newAvatar);
                    targetUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                callback.onUserImageUpdateSuccess(newAvatar);
                                currentUser.setAvatar(newAvatar);
                                if (null != oldAvatar) {
                                    oldAvatar.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {

                                        }
                                    });
                                }
                            } else {
                                callback.onUserImageUpdateFailed(e);
                            }
                        }
                    });
                } else {
                    callback.onUserImageUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void updateUserPortrait(final BmobFile newPortrait, final UserImageUpdateCallback callback) {
        final User currentUser = BmobUser.getCurrentUser(User.class);

        final User targetUser = new User();
        targetUser.setObjectId(currentUser.getObjectId());

        final BmobFile oldPortrait = currentUser.getPortrait();

        newPortrait.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    targetUser.setPortrait(newPortrait);
                    targetUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                callback.onUserImageUpdateSuccess(newPortrait);
                                currentUser.setPortrait(newPortrait);
                                if (null != oldPortrait) {
                                    oldPortrait.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {

                                        }
                                    });
                                }
                            } else {
                                callback.onUserImageUpdateFailed(e);
                            }
                        }
                    });
                } else {
                    callback.onUserImageUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void updateUserInfo(User user, final UserInfoUpdateCallback callback) {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onUserInfoUpdateSuccess();
                } else {
                    callback.onUserInfoUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getAuthorList(int pageCode, final UserListGetCallback callback) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(UserSchema.Table.Cols.AUTHORIZE, true);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> authorList, BmobException e) {
                if (null == e) {
                    callback.onUserListGetSuccess(authorList);
                } else {
                    callback.onUserListGetFailed(e);
                }
            }
        });
    }
}
