package com.android.settingslib.bluetooth;

import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((SemBluetoothCastDevice) obj).getAddress() == null) {
                    break;
                }
                break;
            case 1:
                if (((SemBluetoothCastDevice) obj).getLocalDeviceRole() != 1) {
                    break;
                }
                break;
            case 2:
                if (((SemBluetoothCastDevice) obj).getAddress() == null) {
                    break;
                }
                break;
            default:
                if (((SemBluetoothCastDevice) obj).getLocalDeviceRole() != 2) {
                    break;
                }
                break;
        }
        return false;
    }
}
