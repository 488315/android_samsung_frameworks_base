package com.android.server.input;

import java.util.function.Predicate;

public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        BatteryController.DeviceMonitor deviceMonitor = (BatteryController.DeviceMonitor) obj;
        switch (this.$r8$classId) {
            case 0:
                deviceMonitor.getClass();
                return !(deviceMonitor instanceof BatteryController.UsiDeviceMonitor);
            default:
                boolean z = BatteryController.DEBUG;
                return deviceMonitor.mBluetoothDevice != null;
        }
    }
}
