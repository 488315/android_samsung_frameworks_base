package com.android.server.input;

import android.bluetooth.BluetoothDevice;
import com.android.server.input.BatteryController;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryController$$ExternalSyntheticLambda9(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                String str = (String) obj2;
                boolean z = BatteryController.DEBUG;
                BluetoothDevice bluetoothDevice = ((BatteryController.DeviceMonitor) obj).mBluetoothDevice;
                return bluetoothDevice != null && str.equals(bluetoothDevice.getAddress());
            default:
                boolean z2 = BatteryController.DEBUG;
                return ((BluetoothDevice) obj2).equals(((BatteryController.DeviceMonitor) obj).mBluetoothDevice);
        }
    }
}
