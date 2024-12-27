package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.WifiPowerStatsCollector;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda0(
            BatteryStatsService batteryStatsService, long j, long j2, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = j;
        this.f$2 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda26() {
        BatteryStatsService batteryStatsService = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            if (batteryStatsImpl.mAudioOnNesting > 0) {
                batteryStatsImpl.mAudioOnNesting = 0;
                batteryStatsImpl.mHistory.recordStateStopEvent(j, j2, 4194304);
                batteryStatsImpl.mAudioOnTimer.stopAllRunningLocked(j);
                for (int i = 0; i < batteryStatsImpl.mUidStats.size(); i++) {
                    BatteryStatsImpl.StopwatchTimer stopwatchTimer =
                            ((BatteryStatsImpl.Uid) batteryStatsImpl.mUidStats.valueAt(i))
                                    .mAudioTurnedOnTimer;
                    if (stopwatchTimer != null) {
                        stopwatchTimer.stopAllRunningLocked(j);
                    }
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda31() {
        BatteryStatsService batteryStatsService = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            if (batteryStatsImpl.mWifiOn) {
                batteryStatsImpl.mHistory.recordState2StopEvent(j, j2, 268435456);
                batteryStatsImpl.mWifiOn = false;
                batteryStatsImpl.mWifiOnTimer.stopRunningLocked(j);
                WifiPowerStatsCollector wifiPowerStatsCollector =
                        batteryStatsImpl.mWifiPowerStatsCollector;
                if (wifiPowerStatsCollector.mEnabled) {
                    wifiPowerStatsCollector.schedule();
                } else {
                    batteryStatsImpl.scheduleSyncExternalStatsLocked(2, "wifi-on");
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda40() {
        BatteryStatsService batteryStatsService = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            if (!batteryStatsImpl.mWifiOn) {
                batteryStatsImpl.mHistory.recordState2StartEvent(j, j2, 268435456);
                batteryStatsImpl.mWifiOn = true;
                batteryStatsImpl.mWifiOnTimer.startRunningLocked(j);
                WifiPowerStatsCollector wifiPowerStatsCollector =
                        batteryStatsImpl.mWifiPowerStatsCollector;
                if (wifiPowerStatsCollector.mEnabled) {
                    wifiPowerStatsCollector.schedule();
                } else {
                    batteryStatsImpl.scheduleSyncExternalStatsLocked(2, "wifi-off");
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda44() {
        BatteryStatsService batteryStatsService = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteResetBluetoothScanLocked(j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda64() {
        BatteryStatsService batteryStatsService = this.f$0;
        long j = this.f$1;
        long j2 = this.f$2;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.notePhoneOnLocked(j, j2);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                long j = this.f$1;
                long j2 = this.f$2;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteResetCameraLocked(j, j2);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                long j3 = this.f$1;
                long j4 = this.f$2;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService2.mStats;
                    if (batteryStatsImpl.mFlashlightOnNesting > 0) {
                        batteryStatsImpl.mFlashlightOnNesting = 0;
                        batteryStatsImpl.mHistory.recordState2StopEvent(j3, j4, 134217728);
                        batteryStatsImpl.mFlashlightOnTimer.stopAllRunningLocked(j3);
                        for (int i = 0; i < batteryStatsImpl.mUidStats.size(); i++) {
                            BatteryStatsImpl.StopwatchTimer stopwatchTimer =
                                    ((BatteryStatsImpl.Uid) batteryStatsImpl.mUidStats.valueAt(i))
                                            .mFlashlightTurnedOnTimer;
                            if (stopwatchTimer != null) {
                                stopwatchTimer.stopAllRunningLocked(j3);
                            }
                        }
                    }
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                long j5 = this.f$1;
                long j6 = this.f$2;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService3.mStats;
                    if (batteryStatsImpl2.mVideoOnNesting > 0) {
                        batteryStatsImpl2.mVideoOnNesting = 0;
                        batteryStatsImpl2.mHistory.recordState2StopEvent(j5, j6, 1073741824);
                        batteryStatsImpl2.mVideoOnTimer.stopAllRunningLocked(j5);
                        for (int i2 = 0; i2 < batteryStatsImpl2.mUidStats.size(); i2++) {
                            BatteryStatsImpl.StopwatchTimer stopwatchTimer2 =
                                    ((BatteryStatsImpl.Uid) batteryStatsImpl2.mUidStats.valueAt(i2))
                                            .mVideoTurnedOnTimer;
                            if (stopwatchTimer2 != null) {
                                stopwatchTimer2.stopAllRunningLocked(j5);
                            }
                        }
                    }
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda26();
                return;
            case 4:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda31();
                return;
            case 5:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda40();
                return;
            case 6:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda44();
                return;
            case 7:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda64();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                long j7 = this.f$1;
                long j8 = this.f$2;
                synchronized (batteryStatsService4.mStats) {
                    BatteryStatsImpl batteryStatsImpl3 = batteryStatsService4.mStats;
                    if (batteryStatsImpl3.mPhoneOn) {
                        batteryStatsImpl3.mHistory.recordState2StopEvent(j7, j8, 8388608);
                        batteryStatsImpl3.mPhoneOn = false;
                        batteryStatsImpl3.mPhoneOnTimer.stopRunningLocked(j7);
                        batteryStatsImpl3.scheduleSyncExternalStatsLocked(4, "phone-off");
                        batteryStatsImpl3.mMobileRadioPowerStatsCollector.schedule();
                    }
                }
                return;
        }
    }
}
