package com.android.systemui.util.kotlin;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class BatteryControllerExtKt {
    public static final Flow getBatteryLevel(BatteryController batteryController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        BatteryControllerExtKt$getBatteryLevel$1 batteryControllerExtKt$getBatteryLevel$1 = new BatteryControllerExtKt$getBatteryLevel$1(batteryController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$getBatteryLevel$2(null), FlowConflatedKt.conflatedCallbackFlow(batteryControllerExtKt$getBatteryLevel$1));
    }

    public static final Flow isBatteryPowerSaveEnabled(BatteryController batteryController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        BatteryControllerExtKt$isBatteryPowerSaveEnabled$1 batteryControllerExtKt$isBatteryPowerSaveEnabled$1 = new BatteryControllerExtKt$isBatteryPowerSaveEnabled$1(batteryController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isBatteryPowerSaveEnabled$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(batteryControllerExtKt$isBatteryPowerSaveEnabled$1));
    }

    public static final Flow isDevicePluggedIn(BatteryController batteryController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        BatteryControllerExtKt$isDevicePluggedIn$1 batteryControllerExtKt$isDevicePluggedIn$1 = new BatteryControllerExtKt$isDevicePluggedIn$1(batteryController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isDevicePluggedIn$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(batteryControllerExtKt$isDevicePluggedIn$1));
    }

    public static final Flow isExtremePowerSaverEnabled(BatteryController batteryController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        BatteryControllerExtKt$isExtremePowerSaverEnabled$1 batteryControllerExtKt$isExtremePowerSaverEnabled$1 = new BatteryControllerExtKt$isExtremePowerSaverEnabled$1(batteryController, null);
        conflatedCallbackFlow.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new BatteryControllerExtKt$isExtremePowerSaverEnabled$2(batteryController, null), FlowConflatedKt.conflatedCallbackFlow(batteryControllerExtKt$isExtremePowerSaverEnabled$1));
    }
}
