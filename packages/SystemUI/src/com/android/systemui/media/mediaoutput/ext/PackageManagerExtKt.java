package com.android.systemui.media.mediaoutput.ext;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            Log.e("PackageManagerExt", "isPackageInstalled() failed: " + m3422exceptionOrNullimpl);
        }
        Boolean bool = Boolean.FALSE;
        if (failure instanceof Result.Failure) {
            failure = bool;
        }
        return ((Boolean) failure).booleanValue();
    }
}
