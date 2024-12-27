package com.android.server.pm;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.secutil.Slog;

import com.samsung.android.server.pm.appcategory.AppCategoryFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCategoryHintHelper {
    public static final String FILE_PATH =
            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                    Environment.getDataDirectory().getPath() + "/system/",
                    "package-appcategory.dat");
    public AppCategoryFilter mAppCategoryFilter;
    public HashMap mCategoryMap;
    public ArraySet mChangedByUserApp;
    public Handler mHandler;
    public AtomicBoolean mInit;
    public PackageManagerService mService;

    public static boolean isSystemServerOrShell() {
        return (Binder.getCallingUid() == 1000 && Binder.getCallingPid() == Process.myPid())
                || Binder.getCallingUid() == 2000;
    }

    public final void clearAppCategoryHintUser(String str) {
        if (!isSystemServerOrShell()) {
            throw new RemoteException("calling uid is not system server!");
        }
        if (!this.mInit.get()) {
            Slog.d(
                    "AppCategoryHintHelper",
                    "AppCategoryHintHelper is not initialized, can't set category");
            throw new RemoteException(
                    "AppCategoryHintHelper is not initialized, can't set category");
        }
        if (str == null || str.isEmpty()) {
            Slog.d("AppCategoryHintHelper", "packageName is null or empty!");
            throw new RemoteException("packageName is null or empty!");
        }
        synchronized (this.mCategoryMap) {
            this.mCategoryMap.remove(str);
        }
        this.mHandler.removeCallbacks(new AppCategoryHintHelper$$ExternalSyntheticLambda0(this));
        this.mHandler.postDelayed(new AppCategoryHintHelper$$ExternalSyntheticLambda0(this), 3000L);
    }

    public final int getAppCategoryHintUser(String str) {
        int intValue;
        if (!this.mInit.get()) {
            Slog.d(
                    "AppCategoryHintHelper",
                    "AppCategoryHintHelper is not initialized, can't get category");
            return -1;
        }
        synchronized (this.mCategoryMap) {
            try {
                intValue =
                        this.mCategoryMap.containsKey(str)
                                ? ((Integer) this.mCategoryMap.get(str)).intValue()
                                : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        return intValue;
    }

    public final void onInit(
            PackageManagerService packageManagerService,
            Handler handler,
            AppCategoryFilter appCategoryFilter) {
        FileInputStream fileInputStream;
        if (this.mInit.get()) {
            return;
        }
        this.mService = packageManagerService;
        this.mHandler = handler;
        this.mAppCategoryFilter = appCategoryFilter;
        synchronized (this.mCategoryMap) {
            this.mCategoryMap.clear();
            try {
                fileInputStream = new FileInputStream(new File(FILE_PATH));
            } catch (Exception unused) {
                Slog.d("AppCategoryHintHelper", "categoryMap read error!");
            }
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    this.mCategoryMap.putAll((HashMap) objectInputStream.readObject());
                    objectInputStream.close();
                    fileInputStream.close();
                    if (Build.isDebuggable()) {
                        Slog.d("AppCategoryHintHelper", "read AppCategoryHintUser");
                        this.mCategoryMap.forEach(
                                new AppCategoryHintHelper$$ExternalSyntheticLambda4(1));
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        this.mInit.set(true);
        Slog.d("AppCategoryHintHelper", "AppCategoryHintHelper init!");
    }

    public final void sendAppCategoryBroadcast(int i, String str) {
        if (!this.mInit.get()) {
            Slog.d(
                    "AppCategoryHintHelper",
                    "AppCategoryHintHelper is not initialized, sendAppCategoryBroadcast is not"
                        + " required.");
            return;
        }
        Bundle bundle = new Bundle(2);
        bundle.putString("android.intent.extra.PACKAGE_NAME", str);
        bundle.putInt("app_category", i);
        PackageManagerService packageManagerService = this.mService;
        packageManagerService.getClass();
        packageManagerService.mHandler.post(
                new PackageManagerService$$ExternalSyntheticLambda8(
                        packageManagerService,
                        "com.samsung.android.intent.action.SET_APPCATEGORY",
                        str,
                        bundle,
                        null));
    }
}
