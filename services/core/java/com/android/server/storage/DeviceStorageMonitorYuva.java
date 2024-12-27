package com.android.server.storage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceStorageMonitorYuva {
    public static final File DATA_PATH = Environment.getDataDirectory();
    public final boolean isSupported;
    public final Context mContext;
    public final long mMemLowUserThreshold_15;
    public final long mMemLowUserThreshold_20;
    public boolean mLowUserMemFlag_20 = false;
    public boolean mLowUserMemFlag_15 = false;

    public DeviceStorageMonitorYuva(Context context) {
        ApplicationInfo applicationInfo;
        boolean z = false;
        this.isSupported = false;
        Log.d("DeviceStorageMonitorYuva", "constructor is called");
        this.mContext = context;
        this.mMemLowUserThreshold_20 = getStorageUserMemLowBytes(20);
        this.mMemLowUserThreshold_15 = getStorageUserMemLowBytes(15);
        try {
            applicationInfo =
                    context.getPackageManager().getApplicationInfo("com.samsung.memorysaver", 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            Log.d("DeviceStorageMonitorYuva", "device supports YUVA");
            z = true;
        } else {
            Log.d("DeviceStorageMonitorYuva", "device does not support YUVA");
        }
        this.isSupported = z;
    }

    public final long getStorageUserMemLowBytes(int i) {
        long totalSpace = (DATA_PATH.getTotalSpace() * i) / 100;
        Log.d("DeviceStorageMonitorYuva", "user spcific threshold is calculated ->" + totalSpace);
        return totalSpace;
    }

    public final void sendCancelUserMemLowNotification(int i) {
        Log.d(
                "DeviceStorageMonitorYuva",
                "Intent ACTION_CANCEL_USER_MEM_LOW broadcasting with (" + i + "%)");
        Intent intent = new Intent("com.samsung.android.sm.ACTION_CANCEL_USER_MEM_LOW");
        intent.setClassName(
                "com.samsung.memorysaver",
                "com.samsung.memorysaver.receiver.StorageStatusReceiver");
        intent.putExtra("PERCENT", i);
        intent.addFlags(268435456);
        this.mContext.sendBroadcast(intent);
    }

    public final void sendUserMemLowNotification(int i) {
        Log.d(
                "DeviceStorageMonitorYuva",
                "Intent ACTION_USER_MEM_LOW broadcasting with (" + i + "%)");
        Intent intent = new Intent("com.samsung.android.sm.ACTION_USER_MEM_LOW");
        intent.setClassName(
                "com.samsung.memorysaver",
                "com.samsung.memorysaver.receiver.StorageStatusReceiver");
        intent.putExtra("PERCENT", i);
        intent.addFlags(268435456);
        this.mContext.sendBroadcast(intent);
    }
}
