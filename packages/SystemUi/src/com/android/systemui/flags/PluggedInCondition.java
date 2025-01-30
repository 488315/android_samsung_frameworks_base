package com.android.systemui.flags;

import com.android.systemui.flags.ConditionalRestarter;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PluggedInCondition implements ConditionalRestarter.Condition {
    public final PluggedInCondition$batteryCallback$1 batteryCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.flags.PluggedInCondition$batteryCallback$1
        @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
        public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            Function0 function0 = PluggedInCondition.this.retryFn;
            if (function0 != null) {
                function0.invoke();
            }
        }
    };
    public final BatteryController batteryController;
    public boolean listenersAdded;
    public Function0 retryFn;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.flags.PluggedInCondition$batteryCallback$1] */
    public PluggedInCondition(BatteryController batteryController) {
        this.batteryController = batteryController;
    }

    @Override // com.android.systemui.flags.ConditionalRestarter.Condition
    public final boolean canRestartNow(Function0 function0) {
        boolean z = this.listenersAdded;
        BatteryController batteryController = this.batteryController;
        if (!z) {
            this.listenersAdded = true;
            ((BatteryControllerImpl) batteryController).addCallback(this.batteryCallback);
        }
        this.retryFn = function0;
        return ((BatteryControllerImpl) batteryController).mPluggedIn;
    }
}
