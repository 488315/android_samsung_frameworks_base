package com.android.systemui.power;

import android.os.Handler;
import android.util.Log;
import com.android.systemui.power.SecPowerUI;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SecPowerUI$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecPowerUI$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SecPowerUI secPowerUI = (SecPowerUI) obj;
                if (secPowerUI.mIsWirelessMisalignTask) {
                    Log.d("PowerUI", "mWirelessMisalignTask");
                    secPowerUI.removeChargerView();
                    secPowerUI.removeMisalignView();
                    break;
                }
                break;
            case 1:
                ((DozeChargingHelper) obj).restoreDisplayStateWhenDozeCharging();
                break;
            default:
                SecPowerUI secPowerUI2 = ((SecPowerUI.Receiver) obj).this$0;
                BatteryStateData batteryStateData = secPowerUI2.mCurrentBatteryStateData;
                BatteryStateData batteryStateData2 = secPowerUI2.mLastBatteryStateData;
                int i2 = batteryStateData.bucket;
                int i3 = batteryStateData2.bucket;
                boolean z = batteryStateData2.plugged;
                boolean z2 = i2 != i3 || z;
                Handler handler = secPowerUI2.mHandler;
                SecWarningsUI secWarningsUI = secPowerUI2.mWarnings;
                boolean z3 = batteryStateData.plugged;
                if (!z3 && ((i2 < i3 || z) && i2 < 0 && batteryStateData.batteryStatus != 1)) {
                    if (i2 != i3) {
                        SecPowerNotificationWarnings secPowerNotificationWarnings = (SecPowerNotificationWarnings) secWarningsUI;
                        if (secPowerNotificationWarnings.mWarning) {
                            secPowerNotificationWarnings.mWarning = false;
                            secPowerNotificationWarnings.updateNotification();
                            secPowerNotificationWarnings.restoreScreenTimeOutIfNeeded();
                            secPowerUI2.mIsRunningLowBatteryTask = true;
                            handler.postDelayed(secPowerUI2.mLowBatteryWarningTask, 500L);
                            break;
                        }
                    }
                    ((SecPowerNotificationWarnings) secWarningsUI).showLowBatteryWarning(z2);
                    break;
                } else if (!z3 && (i2 <= i3 || i2 <= 0)) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings2 = (SecPowerNotificationWarnings) secWarningsUI;
                    if (secPowerNotificationWarnings2.mWarning && batteryStateData.batteryLevel != batteryStateData2.batteryLevel) {
                        secPowerNotificationWarnings2.updateNotification();
                        break;
                    }
                } else {
                    if (secPowerUI2.mIsRunningLowBatteryTask) {
                        handler.removeCallbacks(secPowerUI2.mLowBatteryWarningTask);
                        secPowerUI2.mIsRunningLowBatteryTask = false;
                    }
                    SecPowerNotificationWarnings secPowerNotificationWarnings3 = (SecPowerNotificationWarnings) secWarningsUI;
                    secPowerNotificationWarnings3.mWarning = false;
                    secPowerNotificationWarnings3.updateNotification();
                    secPowerNotificationWarnings3.restoreScreenTimeOutIfNeeded();
                    break;
                }
                break;
        }
    }
}
