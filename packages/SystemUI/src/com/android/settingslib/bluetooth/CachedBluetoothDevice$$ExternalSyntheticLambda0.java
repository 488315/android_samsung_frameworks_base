package com.android.settingslib.bluetooth;

import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class CachedBluetoothDevice$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CachedBluetoothDevice$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                int i = CachedBluetoothDevice.$r8$clinit;
                return (localBluetoothProfile instanceof HearingAidProfile) || (localBluetoothProfile instanceof HapClientProfile);
            case 1:
                return Objects.nonNull((CachedBluetoothDevice) obj);
            case 2:
                return ((CachedBluetoothDevice) obj).mDevice.isConnected();
            case 3:
                int i2 = CachedBluetoothDevice.$r8$clinit;
                return ((LocalBluetoothProfile) obj) instanceof LeAudioProfile;
            case 4:
                int i3 = CachedBluetoothDevice.$r8$clinit;
                return ((LocalBluetoothProfile) obj) instanceof HidProfile;
            default:
                int i4 = CachedBluetoothDevice.$r8$clinit;
                return ((Integer) obj).intValue() > -1;
        }
    }
}
