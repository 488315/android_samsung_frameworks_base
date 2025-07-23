package com.android.systemui.edgelighting.device;

import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DeviceWakeLockManager {
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
