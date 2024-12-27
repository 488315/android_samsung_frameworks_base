package android.se.omapi;

import android.annotation.SystemApi;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class SeFrameworkInitializer {
    private static volatile SeServiceManager sSeServiceManager;

    private SeFrameworkInitializer() {}

    public static void setSeServiceManager(SeServiceManager seServiceManager) {
        if (sSeServiceManager != null) {
            throw new IllegalStateException("setSeServiceManager called twice!");
        }
        if (seServiceManager == null) {
            throw new IllegalArgumentException("seServiceManager must not be null");
        }
        sSeServiceManager = seServiceManager;
    }

    public static SeServiceManager getSeServiceManager() {
        return sSeServiceManager;
    }
}
