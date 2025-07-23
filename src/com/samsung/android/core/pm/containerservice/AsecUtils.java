package com.samsung.android.core.pm.containerservice;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.os.Binder;
import android.os.RemoteException;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes6.dex */
public class AsecUtils {
    private static final String TAG = "AsecUtils";

    public static boolean isExternalAsec(ApplicationInfo applicationInfo) {
        return applicationInfo != null && TextUtils.isEmpty(applicationInfo.volumeUuid) && applicationInfo.isExternal();
    }

    public static boolean fitsOnExternal(PackageInstaller.SessionParams sessionParams, StorageManager storageManager) {
        boolean hasActiveContainer = hasActiveContainer(storageManager);
        StorageVolume[] volumeList = storageManager.getVolumeList();
        if (volumeList == null) {
            Log.e(TAG, "storageVolumes is null");
            return false;
        }
        for (StorageVolume storageVolume : volumeList) {
            if ("sd".equals(storageVolume.getSubSystem()) && storageVolume.isRemovable()) {
                Log.d(TAG, "getExternalStorageSdPath: " + storageVolume.getPath());
                return sessionParams.sizeBytes > 0 && hasActiveContainer && sessionParams.sizeBytes <= storageManager.getStorageBytesUntilLow(storageVolume.getPathFile());
            }
        }
        Log.e(TAG, "Cannot find fitsOnExternal volume");
        return false;
    }

    private static boolean hasActiveContainer(StorageManager storageManager) {
        if (storageManager == null) {
            Log.e(TAG, "hasActiveContainer, StorageManager is null");
            return false;
        }
        for (StorageVolume storageVolume : storageManager.getVolumeList()) {
            if (storageVolume.getActivitySecureContainer()) {
                return true;
            }
        }
        return false;
    }

    public static final PackageInfo getPackageInfoAsUser(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return AppGlobals.getPackageManager().getPackageInfo(str, 0L, i);
            } catch (RemoteException e) {
                Log.i(TAG, "RemoteException", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
