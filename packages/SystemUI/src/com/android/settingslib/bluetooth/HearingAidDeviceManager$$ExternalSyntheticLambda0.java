package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingAidDeviceManager$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HearingAidDeviceManager$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                boolean z = HearingAidDeviceManager.DEBUG;
                return ((LocalBluetoothProfile) obj) instanceof CsipSetCoordinatorProfile;
            case 1:
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                boolean z2 = HearingAidDeviceManager.DEBUG;
                return cachedBluetoothDevice.mBondState == 12 && cachedBluetoothDevice.isHearingDevice();
            case 2:
                boolean z3 = HearingAidDeviceManager.DEBUG;
                return ((LocalBluetoothProfile) obj) instanceof CsipSetCoordinatorProfile;
            case 3:
                boolean z4 = HearingAidDeviceManager.DEBUG;
                return ((LocalBluetoothProfile) obj) instanceof HapClientProfile;
            case 4:
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj;
                boolean z5 = HearingAidDeviceManager.DEBUG;
                return !cachedBluetoothDevice2.isConnected() && cachedBluetoothDevice2.mBondState == 12;
            default:
                return ((CachedBluetoothDevice) obj).isConnected();
        }
    }
}
