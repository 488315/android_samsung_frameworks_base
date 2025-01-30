package com.samsung.android.lib.dexcontrol;

/* loaded from: classes2.dex */
public interface ISDCDeviceController {

    public interface ControlResponseListener {
        void onChargingModeUpdated(boolean z);

        void onConnectedPowerChargerInfoUpdated(int i, int i2, int i3);

        void onDSVersionUpdated(String str);

        void onError(int i);
    }

    void destroy();

    void requestConnectedPowerChargerInfoUpdate();
}
