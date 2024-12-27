package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BatteryController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BatteryControllerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryControllerImpl f$0;

    public /* synthetic */ BatteryControllerImpl$$ExternalSyntheticLambda1(BatteryControllerImpl batteryControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        BatteryControllerImpl batteryControllerImpl = this.f$0;
        switch (i) {
            case 0:
                ((BatteryController.BatteryStateChangeCallback) obj).onBatteryUnknownStateChanged(batteryControllerImpl.mStateUnknown);
                break;
            case 1:
                ((BatteryController.BatteryStateChangeCallback) obj).onPowerSaveChanged(batteryControllerImpl.mPowerSave);
                break;
            case 2:
                ((BatteryController.BatteryStateChangeCallback) obj).onWirelessChargingChanged(batteryControllerImpl.mWirelessCharging);
                break;
            case 3:
                ((BatteryController.BatteryStateChangeCallback) obj).onIsIncompatibleChargingChanged(batteryControllerImpl.mIsIncompatibleCharging);
                break;
            case 4:
                ((BatteryController.BatteryStateChangeCallback) obj).onBatteryLevelChanged(batteryControllerImpl.mLevel, batteryControllerImpl.mPluggedIn, batteryControllerImpl.mCharging);
                break;
            case 5:
                ((BatteryController.BatteryStateChangeCallback) obj).onBatteryLevelChanged(batteryControllerImpl.mLevel, batteryControllerImpl.mPluggedIn, batteryControllerImpl.mCharging, batteryControllerImpl.mBatteryStatus, batteryControllerImpl.mBatteryHealth, batteryControllerImpl.mBatteryOnline, batteryControllerImpl.mIsDirectPowerMode, batteryControllerImpl.mMiscEvent);
                break;
            default:
                ((BatteryController.BatteryStateChangeCallback) obj).onIsBatteryDefenderChanged(batteryControllerImpl.mIsBatteryDefender);
                break;
        }
    }
}
