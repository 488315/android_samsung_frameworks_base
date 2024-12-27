package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothHapPresetInfo;
import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HearingDevicesDialogDelegate$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BluetoothHapPresetInfo) obj).getName();
            default:
                return ((DeviceItem) obj).cachedBluetoothDevice;
        }
    }
}
