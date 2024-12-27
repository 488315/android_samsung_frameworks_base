package com.android.server.am;

import android.telephony.TelephonyManager;

import com.android.server.power.stats.BatteryStatsImpl;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda2(
            int i, long j, long j2, BatteryStatsService batteryStatsService, boolean z) {
        this.$r8$classId = 8;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda2(
            BatteryStatsService batteryStatsService, int i, long j, long j2, int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda116() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteGpsSignalQualityLocked(i, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda23() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            BatteryStatsImpl.BatchTimer batchTimer =
                    batteryStatsImpl.getUidStatsLocked(batteryStatsImpl.mapUid(i), j, j2)
                            .mVibratorOnTimer;
            if (batchTimer != null) {
                batchTimer.recomputeLastDuration(j * 1000, true);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda24() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            int i2 = batteryStatsImpl.mWifiMulticastNesting - 1;
            batteryStatsImpl.mWifiMulticastNesting = i2;
            if (i2 == 0) {
                batteryStatsImpl.mHistory.recordStateStopEvent(
                        j, j2, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                if (batteryStatsImpl.mWifiMulticastWakelockTimer.isRunningLocked()) {
                    batteryStatsImpl.mWifiMulticastWakelockTimer.stopRunningLocked(j);
                }
            }
            batteryStatsImpl.getUidStatsLocked(mapUid, j, j2).noteWifiMulticastDisabledLocked(j);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda27() {
        BatteryStatsImpl.StopwatchTimer stopwatchTimer;
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            if (batteryStatsImpl.mVideoOnNesting != 0) {
                int mapUid = batteryStatsImpl.mapUid(i);
                int i2 = batteryStatsImpl.mVideoOnNesting - 1;
                batteryStatsImpl.mVideoOnNesting = i2;
                if (i2 == 0) {
                    batteryStatsImpl.mHistory.recordState2StopEvent(
                            j, j2, 1073741824, mapUid, "video");
                    batteryStatsImpl.mVideoOnTimer.stopRunningLocked(j);
                }
                if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(5)
                        && (stopwatchTimer =
                                        batteryStatsImpl.getUidStatsLocked(mapUid, j, j2)
                                                .mVideoTurnedOnTimer)
                                != null) {
                    stopwatchTimer.stopRunningLocked(j);
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda35() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteScreenBrightnessLocked(i, -1, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda4() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            if (batteryStatsImpl.mWifiMulticastNesting == 0) {
                batteryStatsImpl.mHistory.recordStateStartEvent(
                        j, j2, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
                if (!batteryStatsImpl.mWifiMulticastWakelockTimer.isRunningLocked()) {
                    batteryStatsImpl.mWifiMulticastWakelockTimer.startRunningLocked(j);
                }
            }
            batteryStatsImpl.mWifiMulticastNesting++;
            batteryStatsImpl.getUidStatsLocked(mapUid, j, j2).noteWifiMulticastEnabledLocked(j);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda42() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int i2 = batteryStatsImpl.mWifiSupplState;
            if (i2 != i) {
                if (i2 >= 0) {
                    batteryStatsImpl.mWifiSupplStateTimer[i2].stopRunningLocked(j);
                }
                batteryStatsImpl.mWifiSupplState = i;
                batteryStatsImpl.mWifiSupplStateTimer[i].startRunningLocked(j);
                batteryStatsImpl.mHistory.recordWifiSupplicantStateChangeEvent(j, j2, i);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda45() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteWifiRssiChangedLocked(i, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda62() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteCameraOffLocked(i, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda63() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            int i2 = batteryStatsImpl.mFlashlightOnNesting;
            batteryStatsImpl.mFlashlightOnNesting = i2 + 1;
            if (i2 == 0) {
                batteryStatsImpl.mHistory.recordState2StartEvent(
                        j, j2, 134217728, mapUid, "flashlight");
                batteryStatsImpl.mFlashlightOnTimer.startRunningLocked(j);
            }
            if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(6)) {
                BatteryStatsImpl.Uid uidStatsLocked =
                        batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                if (uidStatsLocked.mFlashlightTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                    uidStatsLocked.mFlashlightTurnedOnTimer =
                            new BatteryStatsImpl.StopwatchTimer(
                                    batteryStatsImpl2.mClock,
                                    uidStatsLocked,
                                    16,
                                    batteryStatsImpl2.mFlashlightTurnedOnTimers,
                                    batteryStatsImpl2.mOnBatteryTimeBase);
                }
                uidStatsLocked.mFlashlightTurnedOnTimer.startRunningLocked(j);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda66() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        int simState =
                ((TelephonyManager)
                                batteryStatsService.mContext.getSystemService(
                                        TelephonyManager.class))
                        .getSimState();
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            batteryStatsImpl.updateAllPhoneStateLocked(
                    i, simState, batteryStatsImpl.mPhoneSignalStrengthBinRaw, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda67() {
        BatteryStatsImpl.StopwatchTimer stopwatchTimer;
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            if (batteryStatsImpl.mFlashlightOnNesting != 0) {
                int mapUid = batteryStatsImpl.mapUid(i);
                int i2 = batteryStatsImpl.mFlashlightOnNesting - 1;
                batteryStatsImpl.mFlashlightOnNesting = i2;
                if (i2 == 0) {
                    batteryStatsImpl.mHistory.recordState2StopEvent(
                            j, j2, 134217728, mapUid, "flashlight");
                    batteryStatsImpl.mFlashlightOnTimer.stopRunningLocked(j);
                }
                if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(6)
                        && (stopwatchTimer =
                                        batteryStatsImpl.getUidStatsLocked(mapUid, j, j2)
                                                .mFlashlightTurnedOnTimer)
                                != null) {
                    stopwatchTimer.stopRunningLocked(j);
                }
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda70() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteWifiScanStoppedLocked(i, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda72() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteFullWifiLockReleasedLocked(i, j, j2);
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda86() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            if (batteryStatsImpl.mVideoOnNesting == 0) {
                batteryStatsImpl.mHistory.recordState2StartEvent(
                        j, j2, 1073741824, mapUid, "video");
                batteryStatsImpl.mVideoOnTimer.startRunningLocked(j);
            }
            batteryStatsImpl.mVideoOnNesting++;
            if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(5)) {
                BatteryStatsImpl.Uid uidStatsLocked =
                        batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                if (uidStatsLocked.mVideoTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                    uidStatsLocked.mVideoTurnedOnTimer =
                            new BatteryStatsImpl.StopwatchTimer(
                                    batteryStatsImpl2.mClock,
                                    uidStatsLocked,
                                    8,
                                    batteryStatsImpl2.mVideoTurnedOnTimers,
                                    batteryStatsImpl2.mOnBatteryTimeBase);
                }
                uidStatsLocked.mVideoTurnedOnTimer.startRunningLocked(j);
            }
        }
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda89() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        long j = this.f$2;
        long j2 = this.f$3;
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            if (batteryStatsImpl.mAudioOnNesting == 0) {
                batteryStatsImpl.mHistory.recordStateStartEvent(j, j2, 4194304, mapUid, "audio");
                batteryStatsImpl.mAudioOnTimer.startRunningLocked(j);
            }
            batteryStatsImpl.mAudioOnNesting++;
            if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(4)) {
                BatteryStatsImpl.Uid uidStatsLocked =
                        batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                if (uidStatsLocked.mAudioTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                    uidStatsLocked.mAudioTurnedOnTimer =
                            new BatteryStatsImpl.StopwatchTimer(
                                    batteryStatsImpl2.mClock,
                                    uidStatsLocked,
                                    15,
                                    batteryStatsImpl2.mAudioTurnedOnTimers,
                                    batteryStatsImpl2.mOnBatteryTimeBase);
                }
                uidStatsLocked.mAudioTurnedOnTimer.startRunningLocked(j);
            }
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        BatteryStatsImpl.StopwatchTimer stopwatchTimer;
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                long j = this.f$2;
                long j2 = this.f$3;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteFullWifiLockAcquiredLocked(i, j, j2);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i2 = this.f$1;
                long j3 = this.f$2;
                long j4 = this.f$3;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.noteCameraOnLocked(i2, j3, j4);
                }
                return;
            case 2:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i3 = this.f$1;
                long j5 = this.f$2;
                long j6 = this.f$3;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService3.mStats;
                    if (batteryStatsImpl.mAudioOnNesting != 0) {
                        int mapUid = batteryStatsImpl.mapUid(i3);
                        int i4 = batteryStatsImpl.mAudioOnNesting - 1;
                        batteryStatsImpl.mAudioOnNesting = i4;
                        if (i4 == 0) {
                            batteryStatsImpl.mHistory.recordStateStopEvent(
                                    j5, j6, 4194304, mapUid, "audio");
                            batteryStatsImpl.mAudioOnTimer.stopRunningLocked(j5);
                        }
                        if (!batteryStatsImpl.mPowerStatsCollectorEnabled.get(4)
                                && (stopwatchTimer =
                                                batteryStatsImpl.getUidStatsLocked(mapUid, j5, j6)
                                                        .mAudioTurnedOnTimer)
                                        != null) {
                            stopwatchTimer.stopRunningLocked(j5);
                        }
                    }
                }
                return;
            case 3:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda116();
                return;
            case 4:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda23();
                return;
            case 5:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda24();
                return;
            case 6:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda27();
                return;
            case 7:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda35();
                return;
            case 8:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda42();
                return;
            case 9:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda45();
                return;
            case 10:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda4();
                return;
            case 11:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda62();
                return;
            case 12:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda63();
                return;
            case 13:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda66();
                return;
            case 14:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda67();
                return;
            case 15:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda70();
                return;
            case 16:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda72();
                return;
            case 17:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda86();
                return;
            case 18:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda89();
                return;
            default:
                BatteryStatsService batteryStatsService4 = this.f$0;
                int i5 = this.f$1;
                long j7 = this.f$2;
                long j8 = this.f$3;
                synchronized (batteryStatsService4.mStats) {
                    batteryStatsService4.mStats.noteWifiScanStartedLocked(i5, j7, j8);
                }
                return;
        }
    }
}
