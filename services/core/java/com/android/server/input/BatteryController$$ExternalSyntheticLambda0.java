package com.android.server.input;

import android.view.InputDevice;

import java.util.function.Function;

public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        InputDevice inputDevice = (InputDevice) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(inputDevice.hasBattery());
            case 1:
                return inputDevice.getName();
            case 2:
                boolean z = BatteryController.DEBUG;
                return Boolean.valueOf(inputDevice.getHostUsiVersion() != null);
            default:
                return inputDevice.getBluetoothAddress();
        }
    }
}
