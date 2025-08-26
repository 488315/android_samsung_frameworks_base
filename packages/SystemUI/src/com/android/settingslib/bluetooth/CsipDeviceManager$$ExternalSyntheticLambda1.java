package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class CsipDeviceManager$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ CsipDeviceManager$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((CachedBluetoothDevice) obj).getUiAccessibleProfiles().stream().anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(2));
            case 1:
                return ((CachedBluetoothDevice) obj).getUiAccessibleProfiles().stream().anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(3));
            case 2:
                return ((LocalBluetoothProfile) obj) instanceof LeAudioProfile;
            default:
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                return (localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HeadsetProfile);
        }
    }
}
