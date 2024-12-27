package com.android.systemui.media.mediaoutput.ext;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import kotlin.Result;

public abstract class PackageManagerExtKt {
    public static final String getAppLabel(PackageManager packageManager, String str) {
        Object failure;
        CharSequence loadLabel;
        String obj;
        try {
            int i = Result.$r8$clinit;
            failure = packageManager.getApplicationInfo(str, 0);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        ApplicationInfo applicationInfo = (ApplicationInfo) failure;
        return (applicationInfo == null || (loadLabel = applicationInfo.loadLabel(packageManager)) == null || (obj = loadLabel.toString()) == null) ? str : obj;
    }
}
