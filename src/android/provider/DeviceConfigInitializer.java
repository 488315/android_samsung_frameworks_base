package android.provider;

import android.annotation.SystemApi;
import java.util.Objects;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class DeviceConfigInitializer {
    private static DeviceConfigServiceManager sDeviceConfigServiceManager;
    private static final Object sLock = new Object();

    private DeviceConfigInitializer() {
    }

    public static void setDeviceConfigServiceManager(DeviceConfigServiceManager deviceConfigServiceManager) {
        synchronized (sLock) {
            if (sDeviceConfigServiceManager != null) {
                throw new IllegalStateException("setDeviceConfigServiceManager called twice!");
            }
            Objects.requireNonNull(deviceConfigServiceManager, "serviceManager must not be null");
            sDeviceConfigServiceManager = deviceConfigServiceManager;
        }
    }

    public static DeviceConfigServiceManager getDeviceConfigServiceManager() {
        DeviceConfigServiceManager deviceConfigServiceManager;
        synchronized (sLock) {
            deviceConfigServiceManager = sDeviceConfigServiceManager;
        }
        return deviceConfigServiceManager;
    }
}
