package com.android.systemui.edgelighting.device;

import java.util.HashMap;

/* loaded from: classes2.dex */
public class DeviceWakeLockManager {
    public static DeviceWakeLockManager sInstance;
    public final HashMap mWakeLockMap = new HashMap();

    private DeviceWakeLockManager() {
    }

    public static synchronized DeviceWakeLockManager getInstance() {
        try {
            if (sInstance == null) {
                sInstance = new DeviceWakeLockManager();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }
}
