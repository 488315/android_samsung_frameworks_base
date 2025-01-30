package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes2.dex */
public class ApkExtractorRunnable implements Runnable {
    public static final String TAG = ApkExtractorRunnable.class.getSimpleName();
    public CompressedAssetCopier mAssetCopier;
    public final ApkExtractionTask mExtractionTask;

    public ApkExtractorRunnable(ApkExtractionTask apkExtractionTask, CompressedAssetCopier compressedAssetCopier) {
        this.mExtractionTask = apkExtractionTask;
        this.mAssetCopier = compressedAssetCopier;
    }

    public ApkExtractionTask getApkExtractionTask() {
        return this.mExtractionTask;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str = TAG;
        Log.i(str, "run() called.  mExtractionTask: " + this.mExtractionTask);
        Context context = this.mExtractionTask.getContextRef() != null ? (Context) this.mExtractionTask.getContextRef().get() : null;
        if (context != null) {
            try {
                String targetPackage = this.mExtractionTask.getTargetPackage();
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(targetPackage, PackageManager.ApplicationInfoFlags.of(0L));
                if (applicationInfo == null) {
                    Log.d(str, "aInfo is null : " + targetPackage);
                    this.mExtractionTask.onTaskFailed();
                    return;
                }
                String str2 = applicationInfo.sourceDir;
                Log.i(str, "run() called.  Path : " + str2);
                ApkAssets loadFromPath = ApkAssets.loadFromPath(str2);
                AssetManager.Builder builder = new AssetManager.Builder();
                builder.addApkAssets(loadFromPath);
                doCopy(builder.build(), this.mExtractionTask.getLocaleLanguages(), this.mExtractionTask.shouldReplace());
            } catch (PackageManager.NameNotFoundException | IOException e) {
                Log.e(TAG, "Package not found " + e.getMessage());
                this.mExtractionTask.onTaskFailed();
                return;
            }
        }
        this.mExtractionTask.onTaskComplete();
    }

    public final void createLocaleOverlayDir() {
        File file = new File("/data/overlays/current_locale_apks/files/");
        if (file.exists()) {
            return;
        }
        if (!file.mkdirs()) {
            Log.e(TAG, "createLocaleOverlayDir: Unable to create Dir - " + file);
            return;
        }
        FileUtils.setPermissions(file, 509, -1, 1000);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doCopy(AssetManager assetManager, Set set, boolean z) {
        int i;
        Iterator it;
        String str;
        String str2;
        String[] list;
        int i2;
        createLocaleOverlayDir();
        try {
            list = assetManager.list("localeapks_zipped");
        } catch (IOException e) {
            Log.e(TAG, "Couldn't copy for localeapks_zipped " + e.getMessage());
            i = 1;
        }
        if (list != null && list.length != 0) {
            if (list.length == 1) {
                if (list[0].startsWith("localeapks_zipped_v2")) {
                    i2 = 2;
                    i = i2;
                    Log.i(TAG, "doCopy() called with: assetManager = [" + assetManager + "], localeLangs = " + set + " version = " + i + " package = " + this.mExtractionTask.getTargetPackage());
                    it = set.iterator();
                    while (it.hasNext()) {
                        String str3 = (String) it.next();
                        if (str3 != null) {
                            if (str3.length() == 3) {
                                str3 = (String) OverlayConstants.ISO_639_2_TO_639_1_MAPPING.get(str3);
                            }
                            if (str3 != null) {
                                String str4 = this.mExtractionTask.getTargetPackage() + "." + str3;
                                File file = new File("/data/overlays/current_locale_apks/files/" + str4 + ".apk");
                                if (file.exists()) {
                                    if (z) {
                                        Utils.deleteFile(file);
                                    } else {
                                        this.mExtractionTask.onApkExtracted(str4);
                                        LogWriter.logDebugInfoAndLogcat(TAG, "Package already exists, skipping extraction TID = " + Thread.currentThread().getId() + " Package Name = " + str4);
                                    }
                                }
                                if (i == 1) {
                                    try {
                                        str2 = "localeapks_zipped/" + str4;
                                    } catch (IOException e2) {
                                        e = e2;
                                        str = "localeapks_zipped";
                                        String str5 = TAG;
                                        LogWriter.logErrorToFile(str5, "Extraction unsuccessful TID = " + Thread.currentThread().getId() + " Package Name = " + str4 + ", error: " + e.getMessage());
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Couldn't copy ");
                                        sb.append(str);
                                        sb.append(" ");
                                        sb.append(e.getMessage());
                                        Log.e(str5, sb.toString());
                                    }
                                } else {
                                    str2 = "localeapks_zipped/localeapks_zipped_v2";
                                }
                                str = str2;
                                try {
                                    this.mAssetCopier.copyFile(assetManager, str, str4, file);
                                    this.mExtractionTask.onApkExtracted(str4, true);
                                } catch (IOException e3) {
                                    e = e3;
                                    String str52 = TAG;
                                    LogWriter.logErrorToFile(str52, "Extraction unsuccessful TID = " + Thread.currentThread().getId() + " Package Name = " + str4 + ", error: " + e.getMessage());
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("Couldn't copy ");
                                    sb2.append(str);
                                    sb2.append(" ");
                                    sb2.append(e.getMessage());
                                    Log.e(str52, sb2.toString());
                                }
                            }
                        }
                    }
                    Log.i(TAG, "doCopy: Finished");
                }
            }
            i2 = 1;
            i = i2;
            Log.i(TAG, "doCopy() called with: assetManager = [" + assetManager + "], localeLangs = " + set + " version = " + i + " package = " + this.mExtractionTask.getTargetPackage());
            it = set.iterator();
            while (it.hasNext()) {
            }
            Log.i(TAG, "doCopy: Finished");
        }
    }
}
