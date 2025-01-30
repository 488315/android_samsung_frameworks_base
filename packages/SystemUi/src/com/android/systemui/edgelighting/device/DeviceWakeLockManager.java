package com.android.systemui.edgelighting.device;

import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DeviceWakeLockManager {
    public static DeviceWakeLockManager sInstance;
    public final HashMap mWakeLockMap = new HashMap();

    private DeviceWakeLockManager() {
    }

    public static synchronized DeviceWakeLockManager getInstance() {
        DeviceWakeLockManager deviceWakeLockManager;
        synchronized (DeviceWakeLockManager.class) {
            if (sInstance == null) {
                sInstance = new DeviceWakeLockManager();
            }
            deviceWakeLockManager = sInstance;
        }
        return deviceWakeLockManager;
    }
}
