package com.android.server;

import android.content.Intent;

import com.android.server.battery.BattUtils;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryService$6$1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object this$1;
    public final /* synthetic */ boolean val$otgEnable;

    public BatteryService$6$1(BatteryService.AnonymousClass2 anonymousClass2, boolean z) {
        this.this$1 = anonymousClass2;
        this.val$otgEnable = z;
    }

    public BatteryService$6$1(BatteryService.AnonymousClass2 anonymousClass2, boolean z, byte b) {
        this.this$1 = anonymousClass2;
        this.val$otgEnable = z;
    }

    public BatteryService$6$1(BatteryService.AnonymousClass2 anonymousClass2, boolean z, char c) {
        this.this$1 = anonymousClass2;
        this.val$otgEnable = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryService batteryService =
                        ((BatteryService.AnonymousClass2) this.this$1).this$0;
                boolean z = this.val$otgEnable;
                batteryService.getClass();
                if (!BattUtils.writeNode("/sys/class/power_supply/otg/online", z)) {
                    String str = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "fail to set otgEnable");
                    break;
                } else {
                    String str2 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "success to set otgEnable as " + this.val$otgEnable);
                    BatteryService batteryService2 =
                            ((BatteryService.AnonymousClass2) this.this$1).this$0;
                    batteryService2.getClass();
                    batteryService2.mHandler.post(
                            new BatteryService.AnonymousClass31(
                                    0,
                                    new Intent("android.intent.action.RESPONSE_OTG_CHARGE_BLOCK")));
                    break;
                }
            case 1:
                String str3 = BatteryService.TAG_SS;
                Slog.d("BatteryService", "fastWirelessAutoModeEnable : " + this.val$otgEnable);
                ((BatteryService.AnonymousClass2) this.this$1)
                        .this$0.setWirelessFastCharging(this.val$otgEnable ^ true);
                break;
            default:
                BatteryService batteryService3 =
                        ((BatteryService.AnonymousClass2) this.this$1).this$0;
                boolean z2 = this.val$otgEnable;
                batteryService3.getClass();
                if (!BattUtils.writeNode("/sys/class/power_supply/battery/wc_tx_en", z2)) {
                    String str4 = BatteryService.TAG_SS;
                    Slog.d("BatteryService", "fail to disable wirelssPowerSharingEnable");
                    break;
                } else {
                    String str5 = BatteryService.TAG_SS;
                    Slog.d(
                            "BatteryService",
                            "success to set wirelssPowerSharingEnable as " + this.val$otgEnable);
                    break;
                }
        }
    }
}
