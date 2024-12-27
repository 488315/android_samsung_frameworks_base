package com.android.server.input;

import com.android.server.input.BatteryController;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda1 implements Predicate {
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
