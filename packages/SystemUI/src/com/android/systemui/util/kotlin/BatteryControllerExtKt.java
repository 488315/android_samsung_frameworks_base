package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryControllerExtKt {
    public static final Flow getBatteryLevel(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$getBatteryLevel$2(null), FlowConflatedKt.conflatedCallbackFlow(new BatteryControllerExtKt$getBatteryLevel$1(batteryController, null)));
    }

    public static final Flow isBatteryPowerSaveEnabled(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isBatteryPowerSaveEnabled$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new BatteryControllerExtKt$isBatteryPowerSaveEnabled$1(batteryController, null)));
    }

    public static final Flow isDevicePluggedIn(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isDevicePluggedIn$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new BatteryControllerExtKt$isDevicePluggedIn$1(batteryController, null)));
    }

    public static final Flow isExtremePowerSaverEnabled(BatteryController batteryController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isExtremePowerSaverEnabled$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(new BatteryControllerExtKt$isExtremePowerSaverEnabled$1(batteryController, null)));
    }
}
