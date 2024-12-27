package com.android.systemui.edgelighting.device;

import java.util.HashMap;

public final class DeviceWakeLockManager {
    public static DeviceWakeLockManager sInstance;
    public final HashMap mWakeLockMap = new HashMap();

    private DeviceWakeLockManager() {
    }

    public static synchronized DeviceWakeLockManager getInstance() {
        DeviceWakeLockManager deviceWakeLockManager;
        synchronized (DeviceWakeLockManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new DeviceWakeLockManager();
                }
                deviceWakeLockManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceWakeLockManager;
    }
}
