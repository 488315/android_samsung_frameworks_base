package com.samsung.android.knox.container;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DependencyWrapper {
    public static final String TAG = "DependencyWrapper";

    public static void addCrossProfileIntentFilterMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, IntentFilter intentFilter, int i, int i2) {
        try {
            iDevicePolicyManager.addCrossProfileIntentFilterMDM(componentName, intentFilter, i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at ContainerConfigurationPolicy API addCrossProfileIntentFilter()", e);
        }
    }

    public static void clearCrossProfileIntentFiltersMDM(IDevicePolicyManager iDevicePolicyManager, ComponentName componentName, int i) {
        try {
            iDevicePolicyManager.clearCrossProfileIntentFiltersMDM(componentName, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed ContainerConfigurationPolicy API clearCrossProfileIntentFilters()", e);
        }
    }
}
