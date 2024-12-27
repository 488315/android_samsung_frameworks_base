package com.samsung.android.server.battery;

import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceBatteryInfoService$$ExternalSyntheticLambda1 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        DeviceBatteryInfoService.printBatteryInfo((SemCompanionDeviceBatteryInfo) obj2);
    }
}
