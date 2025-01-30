package com.android.systemui.statusbar.policy;

import com.android.systemui.demomode.DemoMode;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface BatteryController extends DemoMode, CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BatteryStateChangeCallback {
        default void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        }

        default void onBatteryLevelChanged(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3) {
        }

        default void onBatteryUnknownStateChanged(boolean z) {
        }

        default void onIsBatteryDefenderChanged(boolean z) {
        }

        default void onPowerSaveChanged(boolean z) {
        }

        default void onWirelessChargingChanged(boolean z) {
        }
    }
}
