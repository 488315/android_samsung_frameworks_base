package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothHapPresetInfo;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesPresetsController$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BluetoothHapPresetInfo) obj).isAvailable();
            default:
                return ((LocalBluetoothProfile) obj) instanceof HapClientProfile;
        }
    }
}
