package com.android.systemui.media.mediaoutput.ext;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import kotlin.Result;

/* loaded from: classes2.dex */
public abstract class PackageManagerExtKt {
    public static final String getAppLabel(PackageManager packageManager, String str) {
        Object failure;
        CharSequence charSequenceLoadLabel;
        String string;
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
        return (applicationInfo == null || (charSequenceLoadLabel = applicationInfo.loadLabel(packageManager)) == null || (string = charSequenceLoadLabel.toString()) == null) ? str : string;
    }

    public static final boolean isPackageInstalled(PackageManager packageManager, String str) {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            packageManager.getApplicationInfo(str, 128);
            failure = Boolean.TRUE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
        if (thM3442exceptionOrNullimpl != null) {
            Log.e("PackageManagerExt", "isPackageInstalled() failed: " + thM3442exceptionOrNullimpl);
        }
        Boolean bool = Boolean.FALSE;
        if (failure instanceof Result.Failure) {
            failure = bool;
        }
        return ((Boolean) failure).booleanValue();
    }
}
