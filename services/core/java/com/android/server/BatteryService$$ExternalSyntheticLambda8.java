package com.android.server;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryService$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryService f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ BatteryService$$ExternalSyntheticLambda8(
            BatteryService batteryService, Intent intent, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryService;
        this.f$1 = intent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryService batteryService = this.f$0;
                Intent intent = this.f$1;
                batteryService.getClass();
                if (Build.IS_DEBUGGABLE) {
                    BatteryService$$ExternalSyntheticOutline0.m(
                            new StringBuilder(
                                    "Sending ACTION_SEC_BATTERY_CURRENT_CHANGED: current_now:"),
                            batteryService.mSehHealthInfo.batteryCurrentNow,
                            "BatteryService");
                }
                ActivityManager.broadcastStickyIntent(intent, -1);
                break;
            default:
                ActivityManager.broadcastStickyIntent(
                        this.f$1, -1, this.f$0.mBatteryChangedOptions, -1);
                break;
        }
    }
}
