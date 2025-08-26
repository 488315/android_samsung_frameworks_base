package com.android.settingslib.bluetooth;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean z = BluetoothUtils.DEBUG;
                if (semBluetoothCastDevice.getAddress() != null) {
                    break;
                }
                break;
            case 1:
                boolean z2 = BluetoothUtils.DEBUG;
                if (semBluetoothCastDevice.getLocalDeviceRole() == 1) {
                    break;
                }
                break;
            case 2:
                boolean z3 = BluetoothUtils.DEBUG;
                if (semBluetoothCastDevice.getAddress() != null) {
                    break;
                }
                break;
            default:
                boolean z4 = BluetoothUtils.DEBUG;
                if (semBluetoothCastDevice.getLocalDeviceRole() == 2) {
                    break;
                }
                break;
        }
        return true;
    }
}
