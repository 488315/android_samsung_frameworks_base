package com.android.server.power.stats;

import android.view.Display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsImpl$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ BatteryStatsImpl$$ExternalSyntheticLambda6(
            BatteryStatsImpl batteryStatsImpl, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = batteryStatsImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsImpl batteryStatsImpl = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int batteryPermil = batteryStatsImpl.getBatteryPermil();
                if (batteryStatsImpl.mIsSubScreen) {
                    if (Display.isOnState(i4)) {
                        int i6 =
                                batteryStatsImpl.mDischargeSubScreenOnUnplugLevelPermil
                                        - batteryPermil;
                        if (i6 > 0) {
                            batteryStatsImpl.mDischargeAmountSubScreenOnSinceChargePermil += i6;
                        }
                    } else if (Display.isDozeState(i4)
                            && (i =
                                            batteryStatsImpl
                                                            .mDischargeSubScreenDozeUnplugLevelPermil
                                                    - batteryPermil)
                                    > 0) {
                        batteryStatsImpl.mDischargeAmountSubScreenDozeSinceChargePermil += i;
                    }
                }
                if (batteryStatsImpl.mIsSubScreen) {
                    if (!Display.isOnState(i5)) {
                        if (!Display.isDozeState(i5)) {
                            batteryStatsImpl.mDischargeSubScreenOnUnplugLevelPermil = 0;
                            batteryStatsImpl.mDischargeSubScreenDozeUnplugLevelPermil = 0;
                            break;
                        } else {
                            batteryStatsImpl.mDischargeSubScreenOnUnplugLevelPermil = 0;
                            batteryStatsImpl.mDischargeSubScreenDozeUnplugLevelPermil =
                                    batteryPermil;
                            break;
                        }
                    } else {
                        batteryStatsImpl.mDischargeSubScreenOnUnplugLevelPermil = batteryPermil;
                        batteryStatsImpl.mDischargeSubScreenDozeUnplugLevelPermil = 0;
                        break;
                    }
                }
                break;
            default:
                BatteryStatsImpl batteryStatsImpl2 = this.f$0;
                int i7 = this.f$1;
                int i8 = this.f$2;
                int batteryPermil2 = batteryStatsImpl2.getBatteryPermil();
                int batteryCCInfo = batteryStatsImpl2.getBatteryCCInfo();
                if (Display.isOnState(i7)) {
                    int i9 = batteryStatsImpl2.mDischargeScreenOnUnplugLevelPermil - batteryPermil2;
                    if (i9 > 0) {
                        batteryStatsImpl2.mDischargeAmountScreenOnSinceChargePermil += i9;
                    }
                } else if (Display.isDozeState(i7)) {
                    int i10 =
                            batteryStatsImpl2.mDischargeScreenDozeUnplugLevelPermil
                                    - batteryPermil2;
                    if (i10 > 0) {
                        batteryStatsImpl2.mDischargeAmountScreenDozeSinceChargePermil += i10;
                    }
                } else if (Display.isOffState(i7)
                        && (i2 =
                                        batteryStatsImpl2.mDischargeScreenOffUnplugLevelPermil
                                                - batteryPermil2)
                                > 0) {
                    batteryStatsImpl2.mDischargeAmountScreenOffSinceChargePermil += i2;
                }
                if (Display.isOnState(i8)) {
                    batteryStatsImpl2.mDischargeScreenOnUnplugLevelPermil = batteryPermil2;
                    batteryStatsImpl2.mDischargeScreenDozeUnplugLevelPermil = 0;
                    batteryStatsImpl2.mDischargeScreenOffUnplugLevelPermil = 0;
                } else if (Display.isDozeState(i8)) {
                    batteryStatsImpl2.mDischargeScreenOnUnplugLevelPermil = 0;
                    batteryStatsImpl2.mDischargeScreenDozeUnplugLevelPermil = batteryPermil2;
                    batteryStatsImpl2.mDischargeScreenOffUnplugLevelPermil = 0;
                } else if (Display.isOffState(i8)) {
                    batteryStatsImpl2.mDischargeScreenOnUnplugLevelPermil = 0;
                    batteryStatsImpl2.mDischargeScreenDozeUnplugLevelPermil = 0;
                    batteryStatsImpl2.mDischargeScreenOffUnplugLevelPermil = batteryPermil2;
                }
                if (Display.isOnState(i7)) {
                    int i11 =
                            batteryStatsImpl2.mDischargeScreenOnUnplugLevelCoulombCounter
                                    - batteryCCInfo;
                    if (i11 > 0) {
                        batteryStatsImpl2.mDischargeAmountScreenOnSinceChargeCoulombCounter += i11;
                    }
                } else if (Display.isDozeState(i7)) {
                    int i12 =
                            batteryStatsImpl2.mDischargeScreenOffUnplugLevelCoulombCounter
                                    - batteryCCInfo;
                    if (i12 > 0) {
                        batteryStatsImpl2.mDischargeAmountScreenOffSinceChargeCoulombCounter += i12;
                    }
                } else if (Display.isOffState(i7)
                        && (i3 =
                                        batteryStatsImpl2
                                                        .mDischargeScreenOffUnplugLevelCoulombCounter
                                                - batteryCCInfo)
                                > 0) {
                    batteryStatsImpl2.mDischargeAmountScreenOffSinceChargeCoulombCounter += i3;
                }
                if (!Display.isOnState(i8)) {
                    if (!Display.isDozeState(i8)) {
                        if (Display.isOffState(i8)) {
                            batteryStatsImpl2.mDischargeScreenOnUnplugLevelCoulombCounter = 0;
                            batteryStatsImpl2.mDischargeScreenOffUnplugLevelCoulombCounter =
                                    batteryCCInfo;
                            break;
                        }
                    } else {
                        batteryStatsImpl2.mDischargeScreenOnUnplugLevelCoulombCounter = 0;
                        batteryStatsImpl2.mDischargeScreenOffUnplugLevelCoulombCounter =
                                batteryCCInfo;
                        break;
                    }
                } else {
                    batteryStatsImpl2.mDischargeScreenOnUnplugLevelCoulombCounter = batteryCCInfo;
                    batteryStatsImpl2.mDischargeScreenOffUnplugLevelCoulombCounter = 0;
                    break;
                }
                break;
        }
    }
}
