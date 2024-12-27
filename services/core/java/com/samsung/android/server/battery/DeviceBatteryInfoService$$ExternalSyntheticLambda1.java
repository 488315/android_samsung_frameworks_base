package com.samsung.android.server.battery;

import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

import java.util.function.BiConsumer;

public final /* synthetic */ class DeviceBatteryInfoService$$ExternalSyntheticLambda1
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        DeviceBatteryInfoService.printBatteryInfo((SemCompanionDeviceBatteryInfo) obj2);
    }
}
