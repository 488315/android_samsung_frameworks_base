package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ApkExtractorRunnable implements Runnable {
    public final SevenZFileCopier mAssetCopier;
    public final ApkExtractionTask mExtractionTask;

    public ApkExtractorRunnable(
            ApkExtractionTask apkExtractionTask, SevenZFileCopier sevenZFileCopier) {
        this.mExtractionTask = apkExtractionTask;
        this.mAssetCopier = sevenZFileCopier;
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x01a7, code lost:

       com.samsung.android.localeoverlaymanager.LogWriter.logDebugInfoAndLogcat("ApkExtractorRunnable", "Overlay file is invalid. Deleting file " + r10);
       com.samsung.android.localeoverlaymanager.Utils.deleteFile(r11);
       r6 = 1;
    */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doCopy(android.content.res.AssetManager r20, java.util.Set r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 554
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.localeoverlaymanager.ApkExtractorRunnable.doCopy(android.content.res.AssetManager,"
                    + " java.util.Set, boolean):void");
    }

    @Override // java.lang.Runnable
    public final void run() {
        Log.i("ApkExtractorRunnable", "run() called.  mExtractionTask: " + this.mExtractionTask);
        WeakReference weakReference = this.mExtractionTask.mContextRef;
        Context context = weakReference != null ? (Context) weakReference.get() : null;
        if (context != null) {
            try {
                String str = this.mExtractionTask.mTargetPackage;
                ApplicationInfo applicationInfo =
                        context.getPackageManager()
                                .getApplicationInfo(
                                        str, PackageManager.ApplicationInfoFlags.of(0L));
                if (applicationInfo == null) {
                    Log.d("ApkExtractorRunnable", "aInfo is null : " + str);
                    ApkExtractionTask apkExtractionTask = this.mExtractionTask;
                    apkExtractionTask.getClass();
                    ApkExtractionManager.sInstance.handleState(apkExtractionTask, 0);
                    return;
                }
                String str2 = applicationInfo.sourceDir;
                Log.i("ApkExtractorRunnable", "run() called.  Path : " + str2);
                ApkAssets loadFromPath = ApkAssets.loadFromPath(str2);
                AssetManager.Builder builder = new AssetManager.Builder();
                builder.addApkAssets(loadFromPath);
                AssetManager build = builder.build();
                ApkExtractionTask apkExtractionTask2 = this.mExtractionTask;
                doCopy(
                        build,
                        apkExtractionTask2.mLocaleLanguages,
                        apkExtractionTask2.mShouldReplace);
            } catch (PackageManager.NameNotFoundException | IOException e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Package not found "), "ApkExtractorRunnable");
                ApkExtractionTask apkExtractionTask3 = this.mExtractionTask;
                apkExtractionTask3.getClass();
                ApkExtractionManager.sInstance.handleState(apkExtractionTask3, 0);
                return;
            }
        }
        ApkExtractionTask apkExtractionTask4 = this.mExtractionTask;
        apkExtractionTask4.getClass();
        ApkExtractionManager.sInstance.handleState(apkExtractionTask4, 1);
    }
}
