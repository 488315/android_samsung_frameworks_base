package com.android.server.pm;

import android.os.Build;
import android.util.secutil.Slog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public final /* synthetic */ class AppCategoryHintHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ AppCategoryHintHelper f$0;

    public /* synthetic */ AppCategoryHintHelper$$ExternalSyntheticLambda0(
            AppCategoryHintHelper appCategoryHintHelper) {
        this.f$0 = appCategoryHintHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FileOutputStream fileOutputStream;
        AppCategoryHintHelper appCategoryHintHelper = this.f$0;
        if (!appCategoryHintHelper.mInit.get()) {
            Slog.d("AppCategoryHintHelper", "AppCategoryHintHelper is not initialized.");
            return;
        }
        synchronized (appCategoryHintHelper.mCategoryMap) {
            try {
                fileOutputStream = new FileOutputStream(new File(AppCategoryHintHelper.FILE_PATH));
            } catch (Exception unused) {
                Slog.d("AppCategoryHintHelper", "categoryMap write error!");
            }
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream.writeObject(appCategoryHintHelper.mCategoryMap);
                    objectOutputStream.close();
                    fileOutputStream.close();
                    if (Build.isDebuggable()) {
                        Slog.d("AppCategoryHintHelper", "write AppCategoryHintUser");
                        appCategoryHintHelper.mCategoryMap.forEach(
                                new AppCategoryHintHelper$$ExternalSyntheticLambda4(0));
                    }
                    appCategoryHintHelper.mChangedByUserApp.forEach(
                            new AppCategoryHintHelper$$ExternalSyntheticLambda5(
                                    appCategoryHintHelper, 0));
                    appCategoryHintHelper.mChangedByUserApp.clear();
                    PackageManagerService.invalidatePackageInfoCache();
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }
}
