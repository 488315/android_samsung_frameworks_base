package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryController f$0;
    public final /* synthetic */ BatteryController.BatteryStateChangeCallback f$1;

    public /* synthetic */ BatteryControllerExtKt$getBatteryLevel$1$$ExternalSyntheticLambda0(BatteryController batteryController, BatteryController.BatteryStateChangeCallback batteryStateChangeCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryController;
        this.f$1 = batteryStateChangeCallback;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return BatteryControllerExtKt.AnonymousClass1.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1) this.f$1);
            case 1:
                return BatteryControllerExtKt.C11461.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1) this.f$1);
            case 2:
                return BatteryControllerExtKt.C11481.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1) this.f$1);
            default:
                return BatteryControllerExtKt.C11501.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1) this.f$1);
        }
    }
}
