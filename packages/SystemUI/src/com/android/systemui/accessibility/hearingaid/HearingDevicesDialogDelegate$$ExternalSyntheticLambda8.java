package com.android.systemui.accessibility.hearingaid;

import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import com.android.systemui.bluetooth.qsdialog.DeviceItemType;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class HearingDevicesDialogDelegate$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        DeviceItem deviceItem = (DeviceItem) obj;
        switch (this.$r8$classId) {
            case 0:
                return Objects.nonNull(deviceItem);
            default:
                String str = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                return deviceItem.type == DeviceItemType.ACTIVE_MEDIA_BLUETOOTH_DEVICE;
        }
    }
}
