package com.android.server;

public abstract /* synthetic */ class Watchdog$$ExternalSyntheticOutline0 {
    public static ServiceThread m(int i, String str, boolean z) {
        ServiceThread serviceThread = new ServiceThread(i, str, z);
        serviceThread.start();
        return serviceThread;
    }
}
