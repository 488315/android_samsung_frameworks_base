package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;
import com.android.systemui.demomode.DemoMode;
import java.io.PrintWriter;

public interface BatteryController extends DemoMode, CallbackController {

    public interface BatteryStateChangeCallback extends Dumpable {
        @Override // com.android.systemui.Dumpable
        default void dump(PrintWriter printWriter, String[] strArr) {
            printWriter.println(this);
        }

        default void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        }

        default void onBatteryLevelChanged(int i, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, int i5) {
        }

        default void onBatteryUnknownStateChanged(boolean z) {
        }

        default void onIsBatteryDefenderChanged(boolean z) {
        }

        default void onIsIncompatibleChargingChanged(boolean z) {
        }

        default void onPowerSaveChanged(boolean z) {
        }

        default void onWirelessChargingChanged(boolean z) {
        }
    }
}
