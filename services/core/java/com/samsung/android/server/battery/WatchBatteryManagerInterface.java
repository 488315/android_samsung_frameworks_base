package com.samsung.android.server.battery;

/* loaded from: classes2.dex */
public interface WatchBatteryManagerInterface {
    void aodShowStateChanged(int i);

    void displayStateChanged(boolean z);

    void notifyPackageRegistered(boolean z);

    void systemServicesReady();
}
