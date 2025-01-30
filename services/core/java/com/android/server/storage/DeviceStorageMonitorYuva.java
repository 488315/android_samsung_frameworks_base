package com.android.server.storage;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.samsung.android.feature.SemCscFeature;
import java.io.File;

/* loaded from: classes3.dex */
public class DeviceStorageMonitorYuva {
    public static final File DATA_PATH = Environment.getDataDirectory();
    public static final String valueCscYuva = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigYuva");
    public boolean isSupported;
    public final Context mContext;
    public long mMemLowUserThreshold_15;
    public long mMemLowUserThreshold_20;
    public boolean mLowUserMemFlag_20 = false;
    public boolean mLowUserMemFlag_15 = false;
    public boolean isBootCompleted = false;
    public boolean DEBUG = true;

    public DeviceStorageMonitorYuva(Context context) {
        this.isSupported = false;
        Log.d("DeviceStorageMonitorYuva", "constructor is called");
        this.mContext = context;
        this.mMemLowUserThreshold_20 = getStorageUserMemLowBytes(20);
        this.mMemLowUserThreshold_15 = getStorageUserMemLowBytes(15);
        this.isSupported = isYuvaSupported();
    }

    public final long getStorageUserMemLowBytes(int i) {
        long totalSpace = (DATA_PATH.getTotalSpace() * i) / 100;
        if (this.DEBUG) {
            Log.d("DeviceStorageMonitorYuva", "user spcific threshold is calculated ->" + totalSpace);
        }
        return totalSpace;
    }

    public boolean isYuvaSupported() {
        if (valueCscYuva.contains("MemorySaver")) {
            if (!this.DEBUG) {
                return true;
            }
            Log.d("DeviceStorageMonitorYuva", "device supports YUVA");
            return true;
        }
        if (!this.DEBUG) {
            return false;
        }
        Log.d("DeviceStorageMonitorYuva", "device does not support YUVA");
        return false;
    }

    public final void sendUserMemLowNotification(int i) {
        if (this.DEBUG) {
            Log.d("DeviceStorageMonitorYuva", "Intent ACTION_USER_MEM_LOW broadcasting with (" + i + "%)");
        }
        Intent intent = new Intent("com.samsung.android.sm.ACTION_USER_MEM_LOW");
        intent.setClassName("com.samsung.memorysaver", "com.samsung.memorysaver.receiver.StorageStatusReceiver");
        intent.putExtra("PERCENT", i);
        intent.addFlags(268435456);
        this.mContext.sendBroadcast(intent);
    }

    public final void sendCancelUserMemLowNotification(int i) {
        if (this.DEBUG) {
            Log.d("DeviceStorageMonitorYuva", "Intent ACTION_CANCEL_USER_MEM_LOW broadcasting with (" + i + "%)");
        }
        Intent intent = new Intent("com.samsung.android.sm.ACTION_CANCEL_USER_MEM_LOW");
        intent.setClassName("com.samsung.memorysaver", "com.samsung.memorysaver.receiver.StorageStatusReceiver");
        intent.putExtra("PERCENT", i);
        intent.addFlags(268435456);
        this.mContext.sendBroadcast(intent);
    }

    public void onUpdate(long j) {
        if (this.isSupported) {
            intentBroadcastForUserLowMem(j);
        }
    }

    public final void intentBroadcastForUserLowMem(long j) {
        if (this.isSupported) {
            if (j < this.mMemLowUserThreshold_15) {
                if (!ActivityManagerNative.isSystemReady() || this.mLowUserMemFlag_15) {
                    return;
                }
                this.mLowUserMemFlag_15 = true;
                sendUserMemLowNotification(15);
                return;
            }
            if (j < this.mMemLowUserThreshold_20) {
                if (ActivityManagerNative.isSystemReady() && this.mLowUserMemFlag_15) {
                    this.mLowUserMemFlag_15 = false;
                    sendCancelUserMemLowNotification(15);
                }
                if (!ActivityManagerNative.isSystemReady() || this.mLowUserMemFlag_20) {
                    return;
                }
                this.mLowUserMemFlag_20 = true;
                sendUserMemLowNotification(20);
                return;
            }
            if (ActivityManagerNative.isSystemReady() && this.mLowUserMemFlag_15) {
                this.mLowUserMemFlag_15 = false;
                sendCancelUserMemLowNotification(15);
            }
            if (ActivityManagerNative.isSystemReady() && this.mLowUserMemFlag_20) {
                this.mLowUserMemFlag_20 = false;
                sendCancelUserMemLowNotification(20);
            }
        }
    }
}
