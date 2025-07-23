package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface BluetoothController extends CallbackController, Dumpable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onBluetoothDevicesChanged();

        void onBluetoothStateChange(boolean z);
    }

    int getBluetoothState();

    boolean isBluetoothEnabled();

    void setBluetoothEnabled(boolean z);
}
