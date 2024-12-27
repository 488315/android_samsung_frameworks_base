package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface BluetoothController extends CallbackController, Dumpable {

    public interface Callback {
        void onBluetoothDevicesChanged();

        void onBluetoothStateChange(boolean z);
    }

    int getBluetoothState();

    boolean isBluetoothEnabled();

    void setBluetoothEnabled(boolean z);
}
