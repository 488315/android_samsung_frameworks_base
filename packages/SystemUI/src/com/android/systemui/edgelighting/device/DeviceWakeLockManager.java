package com.android.systemui.edgelighting.device;

import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
