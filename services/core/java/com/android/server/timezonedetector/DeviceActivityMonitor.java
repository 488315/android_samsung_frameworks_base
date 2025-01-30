package com.android.server.timezonedetector;

/* loaded from: classes3.dex */
public interface DeviceActivityMonitor extends Dumpable {

    public interface Listener {
        void onFlightComplete();
    }

    void addListener(Listener listener);
}
