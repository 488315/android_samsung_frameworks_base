package com.android.server.am;

import android.app.usage.NetworkStatsManager;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.telephony.ModemActivityInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda61 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ NetworkStatsManager f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda61(BatteryStatsService batteryStatsService, WifiActivityEnergyInfo wifiActivityEnergyInfo, long j, long j2, NetworkStatsManager networkStatsManager) {
        this.f$0 = batteryStatsService;
        this.f$1 = wifiActivityEnergyInfo;
        this.f$2 = j;
        this.f$3 = j2;
        this.f$4 = networkStatsManager;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda61(BatteryStatsService batteryStatsService, ModemActivityInfo modemActivityInfo, long j, long j2, NetworkStatsManager networkStatsManager) {
        this.f$0 = batteryStatsService;
        this.f$1 = modemActivityInfo;
        this.f$2 = j;
        this.f$3 = j2;
        this.f$4 = networkStatsManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                batteryStatsService.mStats.updateWifiState((WifiActivityEnergyInfo) this.f$1, -1L, this.f$2, this.f$3, this.f$4);
                break;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                batteryStatsService2.mStats.noteModemControllerActivity((ModemActivityInfo) this.f$1, -1L, this.f$2, this.f$3, this.f$4);
                break;
        }
    }
}
