package com.android.server.input;

import android.hardware.input.IInputDeviceBatteryState;

import java.util.function.Supplier;

public final /* synthetic */ class BatteryController$DeviceMonitor$$ExternalSyntheticLambda1
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryController.DeviceMonitor f$0;

    public /* synthetic */ BatteryController$DeviceMonitor$$ExternalSyntheticLambda1(
            BatteryController.DeviceMonitor deviceMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceMonitor;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        BatteryController.DeviceMonitor deviceMonitor = this.f$0;
        switch (i) {
            case 0:
                deviceMonitor.getClass();
                return new BatteryController.State(deviceMonitor.mState);
            default:
                BatteryController.UsiDeviceMonitor usiDeviceMonitor =
                        (BatteryController.UsiDeviceMonitor) deviceMonitor;
                BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2
                        batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 =
                                usiDeviceMonitor.mValidityTimeoutCallback;
                BatteryController.State state = usiDeviceMonitor.mState;
                return batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 != null
                        ? new BatteryController.State(state)
                        : new BatteryController.State(((IInputDeviceBatteryState) state).deviceId);
        }
    }
}
