package com.android.settingslib.bluetooth;

import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                return ((CachedBluetoothDevice) obj).getConnectableProfiles().stream().anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(4));
            case 1:
                return ((CachedBluetoothDevice) obj).getConnectableProfiles().stream().anyMatch(new CsipDeviceManager$$ExternalSyntheticLambda1(3));
            case 2:
                return ((CachedBluetoothDevice) obj).isConnected();
            case 3:
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                return (localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HeadsetProfile);
            default:
                return ((LocalBluetoothProfile) obj) instanceof LeAudioProfile;
        }
    }
}
