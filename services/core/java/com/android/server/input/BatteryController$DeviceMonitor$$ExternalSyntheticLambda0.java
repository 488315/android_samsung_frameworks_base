package com.android.server.input;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryController$DeviceMonitor$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryController.DeviceMonitor f$0;

    public /* synthetic */ BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(
            BatteryController.DeviceMonitor deviceMonitor, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceMonitor;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BatteryController.DeviceMonitor deviceMonitor = this.f$0;
        Long l = (Long) obj;
        switch (i) {
            case 0:
                deviceMonitor.updateBatteryStateFromNative(l.longValue());
                break;
            default:
                deviceMonitor.configureDeviceMonitor(l.longValue());
                break;
        }
    }
}
