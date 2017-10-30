package com.rumofuture.nemolite.app.manager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class NemoActivityManager {

    private static List<Activity> sActivityList = new ArrayList<>();

    public static void addActivity(@NonNull Activity activity) {
        sActivityList.add(activity);
    }

    public static void removeActivity(@NonNull Activity activity) {
        sActivityList.remove(activity);
    }

    public static void addFragmentToActivity(
            @NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment,
            int fragmentContainerId
    ) {
        fragmentManager.beginTransaction().add(fragmentContainerId, fragment).commit();
    }

    public static void hindFragmentAtActivity(
            @NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment
    ) {
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    public static void replaceFragmentToActivity(
            @NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment,
            int fragmentContainerId
    ) {
        fragmentManager.beginTransaction().replace(fragmentContainerId, fragment).commit();
    }

    public static void finishAll() {
        for (Activity activity : sActivityList) {
            // 判断activity是否已被销毁，避免重复销毁
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
