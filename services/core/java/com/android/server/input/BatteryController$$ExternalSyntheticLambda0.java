package com.android.server.input;

import android.view.InputDevice;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
