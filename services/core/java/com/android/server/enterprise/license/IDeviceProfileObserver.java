package com.android.server.enterprise.license;

/* loaded from: classes2.dex */
public interface IDeviceProfileObserver {
    void onDeviceOwnerAdded(String str);

    void onDeviceOwnerRemoved(String str);

    void onProfileOwnerAdded(int i);

    void onProfileOwnerRemoved(int i);
}
