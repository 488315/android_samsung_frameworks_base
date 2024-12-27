package com.android.server.am;

import android.bluetooth.BluetoothActivityEnergyInfo;
import android.os.BatteryStats;
import android.os.PowerSaveState;
import android.os.SpeakerOutEnergyInfo;
import android.telephony.SignalStrength;

import com.android.server.power.stats.BatteryStatsImpl;

import java.util.ArrayList;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda28
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda28(
            BatteryStatsService batteryStatsService,
            BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo,
            long j,
            long j2) {
        this.$r8$classId = 2;
        this.f$0 = batteryStatsService;
        this.f$1 = bluetoothActivityEnergyInfo;
        this.f$2 = j;
        this.f$3 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda28(
            BatteryStatsService batteryStatsService,
            SpeakerOutEnergyInfo speakerOutEnergyInfo,
            long j,
            long j2) {
        this.$r8$classId = 0;
        this.f$0 = batteryStatsService;
        this.f$1 = speakerOutEnergyInfo;
        this.f$2 = j;
        this.f$3 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda28(
            BatteryStatsService batteryStatsService,
            SignalStrength signalStrength,
            long j,
            long j2) {
        this.$r8$classId = 3;
        this.f$0 = batteryStatsService;
        this.f$1 = signalStrength;
        this.f$2 = j;
        this.f$3 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda28(
            BatteryStatsService batteryStatsService, Object obj, long j, long j2, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = obj;
        this.f$2 = j;
        this.f$3 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda85() {
        BatteryStatsService batteryStatsService = this.f$0;
        SignalStrength signalStrength = (SignalStrength) this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.notePhoneSignalStrengthLocked(signalStrength, j, j2);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                SpeakerOutEnergyInfo speakerOutEnergyInfo = (SpeakerOutEnergyInfo) this.f$1;
                long j = this.f$2;
                long j2 = this.f$3;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.updateSpeakerOutEnergyInfoLocked(
                            speakerOutEnergyInfo, j, j2);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                PowerSaveState powerSaveState = (PowerSaveState) this.f$1;
                long j3 = this.f$2;
                long j4 = this.f$3;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.notePowerSaveModeLocked(
                            j3, j4, powerSaveState.batterySaverEnabled);
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo =
                        (BluetoothActivityEnergyInfo) this.f$1;
                long j5 = this.f$2;
                long j6 = this.f$3;
                synchronized (batteryStatsService3.mStats) {
                    batteryStatsService3.mStats.updateBluetoothStateLocked(
                            bluetoothActivityEnergyInfo, -1L, j5, j6);
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda85();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                String str = (String) this.f$1;
                long j7 = this.f$2;
                long j8 = this.f$3;
                synchronized (batteryStatsService4.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService4.mStats;
                    batteryStatsImpl.mHistory.recordEvent(j7, j8, 12, str, 0);
                    BatteryStats.PackageChange packageChange = new BatteryStats.PackageChange();
                    packageChange.mPackageName = str;
                    packageChange.mUpdate = true;
                    if (batteryStatsImpl.mDailyPackageChanges == null) {
                        batteryStatsImpl.mDailyPackageChanges = new ArrayList();
                    }
                    batteryStatsImpl.mDailyPackageChanges.add(packageChange);
                }
                return;
        }
    }
}
