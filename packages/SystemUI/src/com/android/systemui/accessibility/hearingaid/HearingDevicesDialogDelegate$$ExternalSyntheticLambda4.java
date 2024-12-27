package com.android.systemui.accessibility.hearingaid;

import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import com.android.systemui.bluetooth.qsdialog.DeviceItemType;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        DeviceItem deviceItem = (DeviceItem) obj;
        switch (this.$r8$classId) {
            case 0:
                return deviceItem.type == DeviceItemType.ACTIVE_MEDIA_BLUETOOTH_DEVICE;
            default:
                return Objects.nonNull(deviceItem);
        }
    }
}
