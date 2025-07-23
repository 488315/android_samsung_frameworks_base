package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothHapPresetInfo;
import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda12 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda12(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((DeviceItem) obj).cachedBluetoothDevice;
            default:
                return ((BluetoothHapPresetInfo) obj).getName();
        }
    }
}
