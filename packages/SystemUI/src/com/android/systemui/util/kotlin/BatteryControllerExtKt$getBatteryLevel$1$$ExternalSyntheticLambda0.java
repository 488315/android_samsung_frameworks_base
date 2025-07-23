package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Unit invokeSuspend$lambda$0;
        Unit invokeSuspend$lambda$02;
        Unit invokeSuspend$lambda$03;
        Unit invokeSuspend$lambda$04;
        switch (this.$r8$classId) {
            case 0:
                invokeSuspend$lambda$0 = BatteryControllerExtKt$getBatteryLevel$1.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$getBatteryLevel$1$batteryCallback$1) this.f$1);
                return invokeSuspend$lambda$0;
            case 1:
                invokeSuspend$lambda$02 = BatteryControllerExtKt$isBatteryPowerSaveEnabled$1.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isBatteryPowerSaveEnabled$1$batteryCallback$1) this.f$1);
                return invokeSuspend$lambda$02;
            case 2:
                invokeSuspend$lambda$03 = BatteryControllerExtKt$isDevicePluggedIn$1.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isDevicePluggedIn$1$batteryCallback$1) this.f$1);
                return invokeSuspend$lambda$03;
            default:
                invokeSuspend$lambda$04 = BatteryControllerExtKt$isExtremePowerSaverEnabled$1.invokeSuspend$lambda$0(this.f$0, (BatteryControllerExtKt$isExtremePowerSaverEnabled$1$batteryCallback$1) this.f$1);
                return invokeSuspend$lambda$04;
        }
    }
}
