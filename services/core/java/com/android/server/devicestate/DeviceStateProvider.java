package com.android.server.devicestate;

/* loaded from: classes2.dex */
public interface DeviceStateProvider {

    public interface Listener {
        void onStateChanged(int i);

        void onSupportedDeviceStatesChanged(DeviceState[] deviceStateArr, int i);
    }

    void setListener(Listener listener);
}
