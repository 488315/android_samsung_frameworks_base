package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.ActivityManager;
import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.RemoteException;
import android.os.ServiceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DevicePolicyManagerWrapper {
    private static final DevicePolicyManagerWrapper sInstance = new DevicePolicyManagerWrapper();

    private DevicePolicyManagerWrapper() {
    }

    public static DevicePolicyManagerWrapper getInstance() {
        return sInstance;
    }

    public boolean isWifiBlockedByEASPolicy() {
        IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        if (asInterface == null) {
            return false;
        }
        try {
            return !asInterface.semGetAllowWifi((ComponentName) null, ActivityManager.getCurrentUser());
        } catch (RemoteException unused) {
            return false;
        }
    }
}
