package com.android.server.power;

import android.os.SystemClock;
import android.provider.Settings;

import java.text.SimpleDateFormat;

public final /* synthetic */ class PowerManagerService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerManagerService f$0;

    public /* synthetic */ PowerManagerService$$ExternalSyntheticLambda1(
            PowerManagerService powerManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = powerManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PowerManagerService powerManagerService = this.f$0;
                synchronized (powerManagerService.mLock) {
                    try {
                        PowerGroup powerGroup =
                                (PowerGroup) powerManagerService.mPowerGroups.get(0);
                        powerManagerService.mClock.getClass();
                        if (powerManagerService.userActivityNoUpdateLocked(
                                powerGroup, SystemClock.uptimeMillis(), 4, 0, 1000)) {
                            powerManagerService.updatePowerStateLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 1:
                PowerManagerService powerManagerService2 = this.f$0;
                PowerGroup powerGroup2 = (PowerGroup) powerManagerService2.mPowerGroups.get(0);
                powerManagerService2.mClock.getClass();
                powerManagerService2.userActivityNoUpdateLocked(
                        powerGroup2, SystemClock.uptimeMillis(), 0, 0, 1000);
                return;
            case 2:
                this.f$0.mIsUserActivityNativeInvoked.set(false);
                return;
            case 3:
                PowerManagerService powerManagerService3 = this.f$0;
                SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
                powerManagerService3.handleDexScreenOffTimeoutChange();
                return;
            default:
                PowerManagerService powerManagerService4 = this.f$0;
                SimpleDateFormat simpleDateFormat2 = PowerManagerService.DATE_FORMAT;
                powerManagerService4.getClass();
                Slog.d(
                        "PowerManagerService",
                        "updateDisplayPowerStateLocked: OutdoorMode timed out");
                Settings.System.putIntForUser(
                        powerManagerService4.mContext.getContentResolver(),
                        "display_outdoor_mode",
                        0,
                        -2);
                return;
        }
    }
}
