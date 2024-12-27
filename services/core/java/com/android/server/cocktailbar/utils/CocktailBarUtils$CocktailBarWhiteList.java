package com.android.server.cocktailbar.utils;

import android.app.AppGlobals;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class CocktailBarUtils$CocktailBarWhiteList {
    public static boolean isSystemApplication(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        if (packageManager == null) {
            Slog.d("CocktailBarUtils", "Can not get PM");
            return false;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 64L, i);
            if (packageInfo != null && (packageInfo.applicationInfo.flags & 129) != 0) {
                return true;
            }
            StringBuffer stringBuffer = new StringBuffer("isSystemApplication: ");
            stringBuffer.append(str);
            if (packageInfo == null) {
                stringBuffer.append(" is no signature app");
            } else {
                stringBuffer.append(" f=");
                stringBuffer.append(packageInfo.applicationInfo.flags);
            }
            Slog.d("CocktailBarUtils", stringBuffer.toString());
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
