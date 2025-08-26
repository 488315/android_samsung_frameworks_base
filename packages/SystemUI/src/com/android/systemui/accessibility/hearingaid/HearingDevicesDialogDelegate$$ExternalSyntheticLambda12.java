package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothHapPresetInfo;
import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import java.util.function.Function;

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
