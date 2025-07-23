package com.android.internal.os;

import android.app.AppGlobals;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.SparseArray;
import java.util.List;

/* loaded from: classes5.dex */
public final class AppIdToPackageMap {
    private final SparseArray<String> mAppIdToPackageMap;

    public AppIdToPackageMap(SparseArray<String> sparseArray) {
        this.mAppIdToPackageMap = sparseArray;
    }

    public static AppIdToPackageMap getSnapshot() {
        try {
            List<PackageInfo> list = AppGlobals.getPackageManager().getInstalledPackages(794624L, 0).getList();
            SparseArray sparseArray = new SparseArray();
            for (PackageInfo packageInfo : list) {
                int i = packageInfo.applicationInfo.uid;
                if (packageInfo.sharedUserId != null && sparseArray.indexOfKey(i) >= 0) {
                    sparseArray.put(i, "shared:" + packageInfo.sharedUserId);
                } else {
                    sparseArray.put(i, packageInfo.packageName);
                }
            }
            return new AppIdToPackageMap(sparseArray);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String mapAppId(int i) {
        String str = this.mAppIdToPackageMap.get(i);
        return str == null ? String.valueOf(i) : str;
    }

    public String mapUid(int i) {
        String str = this.mAppIdToPackageMap.get(UserHandle.getAppId(i));
        String formatUid = UserHandle.formatUid(i);
        if (str == null) {
            return formatUid;
        }
        return str + '/' + formatUid;
    }
}
