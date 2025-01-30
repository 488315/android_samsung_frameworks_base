package com.android.p038wm.shell.freeform;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.secutil.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PackageManagerUtil {
    public static ApplicationInfo getApplicationInfoAsUser(Context context, int i, String str) {
        PackageInfo packageInfoAsUser = getPackageInfoAsUser(context, i, str);
        if (packageInfoAsUser != null) {
            ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
            if (applicationInfo != null) {
                return applicationInfo;
            }
            throw new PackageManager.NameNotFoundException(KeyAttributes$$ExternalSyntheticOutline0.m21m("getApplicationInfoAsUser() Cannot find ", str));
        }
        throw new PackageManager.NameNotFoundException("Cannot get package info for " + str + ", userId: " + i);
    }

    public static PackageInfo getPackageInfoAsUser(Context context, int i, String str) {
        PackageManager packageManager = context.getPackageManager();
        Class<?> cls = packageManager.getClass();
        try {
            Class<?> cls2 = Integer.TYPE;
            return (PackageInfo) cls.getMethod("getPackageInfoAsUser", String.class, cls2, cls2).invoke(packageManager, str, 0, Integer.valueOf(i));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            if (e.getCause() instanceof PackageManager.NameNotFoundException) {
                Log.e("FreeformContainer.PackageManagerUtil", "Package: " + str + " might have been uninstalled", e);
                return null;
            }
            Log.i("FreeformContainer.PackageManagerUtil", "Unexpected behaviour", new Exception("\npackageName: " + str + "\nflags: 0\nuserId: " + i, e));
            return null;
        }
    }
}
