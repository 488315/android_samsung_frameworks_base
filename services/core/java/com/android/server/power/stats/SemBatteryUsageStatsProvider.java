package com.android.server.power.stats;

import android.os.AggregateBatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.SemDevicePowerInfo;
import android.os.SemKernelWakelockInfo;
import android.os.SemScreenWakeInfo;
import android.os.SemUidPowerInfo;
import android.os.SystemClock;
import android.os.UidBatteryConsumer;
import android.telephony.CellSignalStrength;
import android.util.ArrayMap;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class SemBatteryUsageStatsProvider {
    public final BatteryUsageStatsProvider mBatteryUsageStatsProvider;
    public final BatteryStats mStats;
    public final Map mLastWakeupMap = new HashMap();
    public final Map mLastKWakelockMap = new HashMap();
    public final Map mLastScreenWakeMap = new HashMap();

    public SemBatteryUsageStatsProvider(
            BatteryStats batteryStats, BatteryUsageStatsProvider batteryUsageStatsProvider) {
        this.mStats = batteryStats;
        this.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
    }

    public final void updateBatteryUsage(
            BatteryUsageStats batteryUsageStats,
            long j,
            long j2,
            SemDevicePowerInfo semDevicePowerInfo,
            ArrayList arrayList) {
        HashMap hashMap;
        SparseArray sparseArray;
        int i;
        long j3;
        long j4;
        int i2;
        long j5;
        int i3;
        int i4;
        long j6;
        SemDevicePowerInfo semDevicePowerInfo2 = semDevicePowerInfo;
        HashMap hashMap2 = new HashMap();
        for (UidBatteryConsumer uidBatteryConsumer : batteryUsageStats.getUidBatteryConsumers()) {
            if (uidBatteryConsumer != null) {
                hashMap2.put(Integer.valueOf(uidBatteryConsumer.getUid()), uidBatteryConsumer);
            }
        }
        SparseArray uidStats = this.mStats.getUidStats();
        int size = uidStats.size() - 1;
        while (size >= 0) {
            BatteryStats.Uid uid = (BatteryStats.Uid) uidStats.valueAt(size);
            if (uid == null || !hashMap2.containsKey(Integer.valueOf(uid.getUid()))) {
                hashMap = hashMap2;
                sparseArray = uidStats;
                i = size;
            } else {
                UidBatteryConsumer uidBatteryConsumer2 =
                        (UidBatteryConsumer) hashMap2.get(Integer.valueOf(uid.getUid()));
                SemUidPowerInfo semUidPowerInfo = new SemUidPowerInfo(uid.getUid());
                HashMap hashMap3 = hashMap2;
                sparseArray = uidStats;
                long processStateTime = uid.getProcessStateTime(0, j, 0) / 1000;
                BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
                if (foregroundActivityTimer != null) {
                    j3 = 1000;
                    j4 = foregroundActivityTimer.getTotalTimeLocked(j, 0) / 1000;
                } else {
                    j3 = 1000;
                    j4 = 0;
                }
                long min = Math.min(processStateTime, j4);
                hashMap = hashMap3;
                long processStateTime2 =
                        ((uid.getProcessStateTime(2, j, 0) + uid.getProcessStateTime(1, j, 0)) / j3)
                                + processStateTime;
                long processStateTime3 =
                        (uid.getProcessStateTime(5, j, 0)
                                        + (uid.getProcessStateTime(4, j, 0)
                                                + uid.getProcessStateTime(3, j, 0)))
                                / j3;
                long j7 = 0;
                long j8 = 0;
                for (int i5 = 0; i5 < 16; i5++) {
                    long speakerMediaTime = uid.getSpeakerMediaTime(i5, 0);
                    if (speakerMediaTime > 0) {
                        j7 = (speakerMediaTime * (i5 + 1)) + j7;
                        j8 += speakerMediaTime;
                    }
                }
                long j9 = j7;
                semUidPowerInfo.screenPower =
                        Math.max(0.0d, uidBatteryConsumer2.getConsumedPower(0));
                semUidPowerInfo.smearedPower =
                        semUidPowerInfo.shouldHide ? 0.0d : uidBatteryConsumer2.getConsumedPower();
                double consumedPower = uidBatteryConsumer2.getConsumedPower();
                i = size;
                long j10 = j8;
                semUidPowerInfo.power = Math.max(0.0d, consumedPower - semUidPowerInfo.screenPower);
                semUidPowerInfo.cpuTime =
                        (uid.getSystemCpuTimeUs(0) + uid.getUserCpuTimeUs(0)) / 1000;
                ArrayMap wakelockStats = uid.getWakelockStats();
                int size2 = wakelockStats.size();
                long j11 = 0;
                for (int i6 = 0; i6 < size2; i6++) {
                    BatteryStats.Timer wakeTime =
                            ((BatteryStats.Uid.Wakelock) wakelockStats.valueAt(i6)).getWakeTime(0);
                    if (wakeTime != null) {
                        j11 = wakeTime.getTotalTimeLocked(j, 0) + j11;
                    }
                }
                semUidPowerInfo.wakelockTime = j11 / 1000;
                semUidPowerInfo.mobileActive = uid.getMobileRadioActiveTime(0) / 1000;
                semUidPowerInfo.mobileData =
                        uid.getNetworkActivityBytes(1, 0) + uid.getNetworkActivityBytes(0, 0);
                semUidPowerInfo.mobilePackets =
                        uid.getNetworkActivityPackets(1, 0) + uid.getNetworkActivityPackets(0, 0);
                semUidPowerInfo.wifiPackets =
                        uid.getNetworkActivityPackets(3, 0) + uid.getNetworkActivityPackets(2, 0);
                semUidPowerInfo.wifiData =
                        uid.getNetworkActivityBytes(3, 0) + uid.getNetworkActivityBytes(2, 0);
                ArrayMap packageStats = uid.getPackageStats();
                int i7 = 1;
                int size3 = packageStats.size() - 1;
                int i8 = 0;
                while (size3 >= 0) {
                    ArrayMap wakeupAlarmStats =
                            ((BatteryStats.Uid.Pkg) packageStats.valueAt(size3))
                                    .getWakeupAlarmStats();
                    if (((String) packageStats.keyAt(size3)) != null) {
                        for (int size4 = wakeupAlarmStats.size() - i7; size4 >= 0; size4--) {
                            i8 +=
                                    ((BatteryStats.Counter) wakeupAlarmStats.valueAt(size4))
                                            .getCountLocked(0);
                        }
                    }
                    size3--;
                    i7 = 1;
                }
                semUidPowerInfo.wakeupAlarm = i8;
                BatteryStats.Timer bluetoothScanTimer = uid.getBluetoothScanTimer();
                semUidPowerInfo.btScan =
                        (bluetoothScanTimer == null
                                        || (bluetoothScanTimer.getTotalTimeLocked(j, 0) + 500)
                                                        / 1000
                                                == 0)
                                ? 0
                                : bluetoothScanTimer.getCountLocked(0);
                semUidPowerInfo.btData =
                        uid.getNetworkActivityBytes(5, 0) + uid.getNetworkActivityBytes(4, 0);
                SparseArray sensorStats = uid.getSensorStats();
                BatteryStats.Uid.Sensor sensor = (BatteryStats.Uid.Sensor) sensorStats.get(-10000);
                BatteryStats.Uid.Sensor sensor2 = (BatteryStats.Uid.Sensor) sensorStats.get(-10001);
                semUidPowerInfo.gpsTime = 0L;
                semUidPowerInfo.actualGpsTime = 0L;
                if (sensor == null || sensor.getSensorTime() == null) {
                    i2 = 0;
                    j5 = 1000;
                } else {
                    i2 = 0;
                    j5 = 1000;
                    semUidPowerInfo.gpsTime =
                            sensor.getSensorTime().getTotalTimeLocked(j, 0) / 1000;
                }
                if (sensor2 != null && sensor2.getSensorTime() != null) {
                    semUidPowerInfo.actualGpsTime =
                            sensor2.getSensorTime().getTotalTimeLocked(j, i2) / j5;
                }
                BatteryStats.Timer cameraTurnedOnTimer = uid.getCameraTurnedOnTimer();
                semUidPowerInfo.cameraRunTime =
                        cameraTurnedOnTimer != null
                                ? (cameraTurnedOnTimer.getTotalTimeLocked(j, i2) + 500) / j5
                                : 0L;
                ArrayMap processStats = uid.getProcessStats();
                if (processStats != null) {
                    int size5 = processStats.size();
                    int i9 = 0;
                    for (int i10 = 0; i10 < size5; i10++) {
                        i9 +=
                                ((BatteryStats.Uid.Proc) processStats.valueAt(i10))
                                        .countExcessivePowers();
                    }
                    i3 = i9;
                } else {
                    i3 = 0;
                }
                semUidPowerInfo.killCount = i3;
                semUidPowerInfo.screenTime = min;
                semUidPowerInfo.fgTime = processStateTime2;
                semUidPowerInfo.bgTime = processStateTime3;
                semUidPowerInfo.spkTime = j10;
                semUidPowerInfo.spkLevel = j9;
                BatteryStats.Timer audioTurnedOnTimer = uid.getAudioTurnedOnTimer();
                if (audioTurnedOnTimer != null) {
                    i4 = 0;
                    j6 = (audioTurnedOnTimer.getTotalTimeLocked(j, 0) + 500) / 1000;
                } else {
                    i4 = 0;
                    j6 = 0;
                }
                semUidPowerInfo.audioTime = j6;
                semUidPowerInfo.networkWakeup = uid.getMobileRadioApWakeupCount(i4);
                ArrayMap syncStats = uid.getSyncStats();
                int size6 = syncStats.size() - 1;
                long j12 = 0;
                while (size6 >= 0) {
                    BatteryStats.Timer timer = (BatteryStats.Timer) syncStats.valueAt(size6);
                    if (timer != null) {
                        j12 = ((timer.getTotalTimeLocked(j, i4) + 500) / 1000) + j12;
                    }
                    size6--;
                    i4 = 0;
                }
                semUidPowerInfo.syncTime = j12;
                for (int i11 = 0; i11 < this.mStats.getDisplayCount(); i11++) {
                    semUidPowerInfo.displayTopActivityTime[i11] =
                            (uid.getDisplayTopActivityTime(i11, j, 0) + 500) / 1000;
                }
                semDevicePowerInfo2.btScanCount += semUidPowerInfo.btScan;
                semDevicePowerInfo2.gpsTime += semUidPowerInfo.gpsTime;
                semDevicePowerInfo2.actualGpsTime += semUidPowerInfo.actualGpsTime;
                semDevicePowerInfo2.wifiScanTime =
                        (uid.getWifiScanTime(j, 0) / 1000) + semDevicePowerInfo2.wifiScanTime;
                semDevicePowerInfo2.wifiScanCount =
                        uid.getWifiScanCount(0) + semDevicePowerInfo2.wifiScanCount;
                semDevicePowerInfo2.pwlTime += semUidPowerInfo.wakelockTime;
                arrayList.add(semUidPowerInfo);
            }
            size = i - 1;
            uidStats = sparseArray;
            hashMap2 = hashMap;
        }
        AggregateBatteryConsumer aggregateBatteryConsumer =
                batteryUsageStats.getAggregateBatteryConsumer(0);
        for (int i12 = 0; i12 < 19; i12++) {
            if (i12 == 0) {
                semDevicePowerInfo2.screenPower = aggregateBatteryConsumer.getConsumedPower(i12);
                semDevicePowerInfo2.screenOnTime =
                        aggregateBatteryConsumer.getUsageDurationMillis(i12);
            } else if (i12 == 8) {
                semDevicePowerInfo2.radioPower = aggregateBatteryConsumer.getConsumedPower(i12);
            } else if (i12 == 11) {
                semDevicePowerInfo2.wifiPower = aggregateBatteryConsumer.getConsumedPower(i12);
            } else if (i12 != 18) {
                switch (i12) {
                    case 14:
                        semDevicePowerInfo2.phonePower =
                                aggregateBatteryConsumer.getConsumedPower(i12);
                        semDevicePowerInfo2.phoneOnTime =
                                aggregateBatteryConsumer.getUsageDurationMillis(i12);
                        break;
                    case 15:
                        semDevicePowerInfo2.aodPower =
                                aggregateBatteryConsumer.getConsumedPower(i12);
                        semDevicePowerInfo2.aodTime =
                                aggregateBatteryConsumer.getUsageDurationMillis(i12);
                        break;
                    case 16:
                        semDevicePowerInfo2.idlePower =
                                aggregateBatteryConsumer.getConsumedPower(i12);
                        semDevicePowerInfo2.idleTime =
                                aggregateBatteryConsumer.getUsageDurationMillis(i12);
                        break;
                }
            } else {
                semDevicePowerInfo2.powersharePower =
                        (long) aggregateBatteryConsumer.getConsumedPower(i12);
                semDevicePowerInfo2.powershareTime =
                        aggregateBatteryConsumer.getUsageDurationMillis(i12);
            }
        }
        for (int i13 = 0; i13 < 5; i13++) {
            semDevicePowerInfo2.screenBrightnessTime[i13] =
                    this.mStats.getScreenBrightnessTime(i13, j, 0) / 1000;
            semDevicePowerInfo2.screenAutoBrightnessTime[i13] =
                    this.mStats.getScreenAutoBrightnessTime(i13, j, 0) / 1000;
            semDevicePowerInfo2.subScreenBrightnessTime[i13] =
                    this.mStats.getSubScreenBrightnessTime(i13, j, 0) / 1000;
            semDevicePowerInfo2.subScreenAutoBrightnessTime[i13] =
                    this.mStats.getSubScreenAutoBrightnessTime(i13, j, 0) / 1000;
        }
        int i14 = 0;
        semDevicePowerInfo2.screenHighBrightnessTime =
                this.mStats.getScreenHighBrightnessTime(j, 0) / 1000;
        semDevicePowerInfo2.subScreenHighBrightnessTime =
                this.mStats.getSubScreenHighBrightnessTime(j, 0) / 1000;
        int i15 = 0;
        long j13 = 0;
        long j14 = 0;
        long j15 = 0;
        long j16 = 0;
        while (i15 < 15) {
            double speakerCallTime = this.mStats.getSpeakerCallTime(i15, i14);
            double speakerMediaTime2 = this.mStats.getSpeakerMediaTime(i15, i14);
            long j17 = (long) (j13 + speakerCallTime);
            i15++;
            double d = i15;
            j14 = (long) ((speakerCallTime * d) + j14);
            j15 = (long) (j15 + speakerMediaTime2);
            j16 = (long) ((speakerMediaTime2 * d) + j16);
            semDevicePowerInfo2 = semDevicePowerInfo;
            j13 = j17;
            i14 = 0;
        }
        SemDevicePowerInfo semDevicePowerInfo3 = semDevicePowerInfo2;
        semDevicePowerInfo3.spkCallTime = j13;
        semDevicePowerInfo3.spkCallLevel = j14;
        semDevicePowerInfo3.spkMediaTime = j15;
        semDevicePowerInfo3.spkMediaLevel = j16;
        int numSignalStrengthLevels = CellSignalStrength.getNumSignalStrengthLevels();
        for (int i16 = 0; i16 < numSignalStrengthLevels; i16++) {
            semDevicePowerInfo3.signalStrengthTime[i16] =
                    this.mStats.getPhoneSignalStrengthTime(i16, j, 0) / 1000;
        }
        int i17 = 0;
        semDevicePowerInfo3.mobileActiveTime = this.mStats.getMobileRadioActiveTime(j, 0) / 1000;
        semDevicePowerInfo3.mobileActiveCount = this.mStats.getMobileRadioActiveCount(0);
        semDevicePowerInfo3.mobileActiveTime5G = this.mStats.getMobileActive5GTime(j, 0) / 1000;
        semDevicePowerInfo3.wifiOnTime = this.mStats.getWifiOnTime(j, 0) / 1000;
        BatteryStats.ControllerActivityCounter bluetoothControllerActivity =
                this.mStats.getBluetoothControllerActivity();
        semDevicePowerInfo3.btOnTime =
                bluetoothControllerActivity.getIdleTimeCounter().getCountLocked(0)
                        + bluetoothControllerActivity.getRxTimeCounter().getCountLocked(0)
                        + bluetoothControllerActivity.getTxTimeCounters()[0].getCountLocked(0);
        semDevicePowerInfo3.btScanTime = this.mStats.getBluetoothScanTime(j, 0) / 1000;
        semDevicePowerInfo3.btTotalBytes =
                this.mStats.getNetworkActivityBytes(5, 0)
                        + this.mStats.getNetworkActivityBytes(4, 0);
        semDevicePowerInfo3.mobileTotalBytes =
                this.mStats.getNetworkActivityBytes(1, 0)
                        + this.mStats.getNetworkActivityBytes(0, 0);
        semDevicePowerInfo3.wifiTotalBytes =
                this.mStats.getNetworkActivityBytes(3, 0)
                        + this.mStats.getNetworkActivityBytes(2, 0);
        semDevicePowerInfo3.mobileTotalPackets =
                this.mStats.getNetworkActivityPackets(1, 0)
                        + this.mStats.getNetworkActivityPackets(0, 0);
        semDevicePowerInfo3.wifiTotalPackets =
                this.mStats.getNetworkActivityPackets(3, 0)
                        + this.mStats.getNetworkActivityPackets(2, 0);
        BatteryStats.ModemActivityCounter networkModemControllerActivity =
                this.mStats.getNetworkModemControllerActivity();
        semDevicePowerInfo3.cpSleepTime =
                networkModemControllerActivity.getSleepTimeCounter().getCountLocked(0);
        semDevicePowerInfo3.cpIdleTime =
                networkModemControllerActivity.getIdleTimeCounter().getCountLocked(0);
        long[] jArr = new long[5];
        BatteryStats.LongCounter[] txTimeCounters =
                networkModemControllerActivity.getNrModemActivityInfo().getTxTimeCounters();
        int length = txTimeCounters.length;
        int i18 = 0;
        int i19 = 0;
        while (i18 < length) {
            long countLocked = txTimeCounters[i18].getCountLocked(i17);
            jArr[i19] = countLocked;
            semDevicePowerInfo3.nrTxTime += countLocked;
            i19++;
            semDevicePowerInfo3.nrTxLevel += countLocked * i19;
            i18++;
            txTimeCounters = txTimeCounters;
            jArr = jArr;
            i17 = 0;
        }
        long j18 = semDevicePowerInfo3.nrTxTime;
        if (j18 != 0) {
            semDevicePowerInfo3.nrTxLevel /= j18;
        }
        int i20 = 0;
        semDevicePowerInfo3.nrRxTime =
                networkModemControllerActivity
                        .getNrModemActivityInfo()
                        .getRxTimeCounter()
                        .getCountLocked(0);
        semDevicePowerInfo3.nrTxByte =
                networkModemControllerActivity
                        .getNrModemActivityInfo()
                        .getTxByteCounter()
                        .getCountLocked(0);
        semDevicePowerInfo3.nrRxByte =
                networkModemControllerActivity
                        .getNrModemActivityInfo()
                        .getRxByteCounter()
                        .getCountLocked(0);
        long[] jArr2 = new long[5];
        BatteryStats.LongCounter[] txTimeCounters2 =
                networkModemControllerActivity.getLcModemActivityInfo().getTxTimeCounters();
        int length2 = txTimeCounters2.length;
        int i21 = 0;
        int i22 = 0;
        while (i21 < length2) {
            long countLocked2 = txTimeCounters2[i21].getCountLocked(i20);
            jArr2[i22] = countLocked2;
            semDevicePowerInfo3.lcTxTime += countLocked2;
            i22++;
            semDevicePowerInfo3.lcTxLevel += countLocked2 * i22;
            i21++;
            txTimeCounters2 = txTimeCounters2;
            jArr2 = jArr2;
            i20 = 0;
        }
        long j19 = semDevicePowerInfo3.lcTxTime;
        if (j19 != 0) {
            semDevicePowerInfo3.lcTxLevel /= j19;
        }
        semDevicePowerInfo3.lcRxTime =
                networkModemControllerActivity
                        .getLcModemActivityInfo()
                        .getRxTimeCounter()
                        .getCountLocked(0);
        semDevicePowerInfo3.lcTxByte =
                networkModemControllerActivity
                        .getLcModemActivityInfo()
                        .getTxByteCounter()
                        .getCountLocked(0);
        semDevicePowerInfo3.lcRxByte =
                networkModemControllerActivity
                        .getLcModemActivityInfo()
                        .getRxByteCounter()
                        .getCountLocked(0);
        semDevicePowerInfo3.totalPower = batteryUsageStats.getConsumedPower();
        semDevicePowerInfo3.batteryPerc = this.mStats.getHighDischargeAmountSinceCharge();
        semDevicePowerInfo3.screenOffTime =
                this.mStats.computeBatteryScreenOffRealtime(j, 0) / 1000;
        semDevicePowerInfo3.screenOnTime =
                (this.mStats.computeBatteryRealtime(j, 0) / 1000)
                        - semDevicePowerInfo3.screenOffTime;
        semDevicePowerInfo3.screenOnCount = this.mStats.getScreenOnCount(0);
        semDevicePowerInfo3.subScreenOnTime = this.mStats.getSubScreenOnTime(j, 0) / 1000;
        semDevicePowerInfo3.uptime = this.mStats.computeBatteryUptime(j2, 0) / 1000;
        semDevicePowerInfo3.screenOffUptime =
                this.mStats.computeBatteryScreenOffUptime(j2, 0) / 1000;
        semDevicePowerInfo3.psmTime = this.mStats.getPowerSaveModeEnabledTime(j, 0) / 1000;
        semDevicePowerInfo3.screenOffDischarge =
                this.mStats.getDischargeAmountScreenDozeSinceChargePermil()
                        + this.mStats.getDischargeAmountScreenOffSinceChargePermil();
        semDevicePowerInfo3.screenOnDischarge =
                this.mStats.getDischargeAmountScreenOnSinceChargePermil();
        semDevicePowerInfo3.subScreenOffDischarge = semDevicePowerInfo3.screenOffDischarge;
        semDevicePowerInfo3.subScreenOnDischarge =
                this.mStats.getDischargeAmountSubScreenOnSinceChargePermil();
        semDevicePowerInfo3.subAodTime = this.mStats.getSubScreenDozeTime(j, 0) / 1000;
        semDevicePowerInfo3.screenOffCoulombCounter =
                this.mStats.getDischargeAmountScreenOffSinceChargeCoulombCounter();
        semDevicePowerInfo3.screenOnCoulombCounter =
                this.mStats.getDischargeAmountScreenOnSinceChargeCoulombCounter();
        semDevicePowerInfo3.powershareTime = this.mStats.getTxPowerSharingTime(j, 0) / 1000;
        semDevicePowerInfo3.powersharePower = this.mStats.getTxSharingDischargeAmount();
        semDevicePowerInfo3.hrrAlwaysTime =
                (this.mStats.getDisplayHighRefreshRateTime(1, j, 0)
                                + this.mStats.getDisplayHighRefreshRateTime(2, j, 0))
                        / 1000;
        semDevicePowerInfo3.subHrrAlwaysTime =
                (this.mStats.getSubDisplayHighRefreshRateTime(1, j, 0)
                                + this.mStats.getSubDisplayHighRefreshRateTime(2, j, 0))
                        / 1000;
        semDevicePowerInfo3.screenOnGpsTime = this.mStats.getScreenOnGpsRunningTime(j, 0) / 1000;
    }

    public final void updateKernelWakelockInfoToList(
            BatteryStats batteryStats, ArrayList arrayList) {
        Map kernelWakelockStats = batteryStats.getKernelWakelockStats();
        ArrayList arrayList2 = new ArrayList();
        if (kernelWakelockStats != null && kernelWakelockStats.size() > 0) {
            long j = 1000;
            long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            for (String str : kernelWakelockStats.keySet()) {
                if (str != null) {
                    BatteryStats.Timer timer = (BatteryStats.Timer) kernelWakelockStats.get(str);
                    if (timer != null) {
                        long totalTimeLocked =
                                (timer.getTotalTimeLocked(elapsedRealtime, 0) + 500) / j;
                        int countLocked = timer.getCountLocked(0);
                        if (totalTimeLocked > 0 && countLocked > 0) {
                            if (!((HashMap) this.mLastKWakelockMap).containsKey(str)) {
                                ((HashMap) this.mLastKWakelockMap)
                                        .put(str, new SemKernelWakelockInfo(str, 0, 0L));
                            }
                            SemKernelWakelockInfo semKernelWakelockInfo =
                                    (SemKernelWakelockInfo)
                                            ((HashMap) this.mLastKWakelockMap).get(str);
                            long max =
                                    Math.max(0L, totalTimeLocked - semKernelWakelockInfo.getTime());
                            int max2 = Math.max(0, countLocked - semKernelWakelockInfo.getCount());
                            ((HashMap) this.mLastKWakelockMap)
                                    .put(
                                            str,
                                            new SemKernelWakelockInfo(
                                                    str, countLocked, totalTimeLocked));
                            if (max != 0 || max2 != 0) {
                                arrayList2.add(new SemKernelWakelockInfo(str, max2, max));
                            }
                        }
                    }
                    j = 1000;
                }
            }
            Collections.sort(arrayList2);
            arrayList.addAll(arrayList2.subList(0, Math.min(10, arrayList2.size())));
        }
    }

    public final void updateScreenWakeInfoToList(BatteryStats batteryStats, ArrayList arrayList) {
        int countLocked;
        Map screenWakeStats = batteryStats.getScreenWakeStats();
        if (screenWakeStats != null && screenWakeStats.size() > 0) {
            for (String str : screenWakeStats.keySet()) {
                BatteryStats.Counter counter = (BatteryStats.Counter) screenWakeStats.get(str);
                if (counter != null && (countLocked = counter.getCountLocked(0)) > 0) {
                    if (!((HashMap) this.mLastScreenWakeMap).containsKey(str)) {
                        ((HashMap) this.mLastScreenWakeMap).put(str, 0);
                    }
                    int max =
                            Math.max(
                                    0,
                                    countLocked
                                            - ((Integer)
                                                            ((HashMap) this.mLastScreenWakeMap)
                                                                    .get(str))
                                                    .intValue());
                    ((HashMap) this.mLastScreenWakeMap).put(str, Integer.valueOf(countLocked));
                    if (max != 0) {
                        arrayList.add(new SemScreenWakeInfo(str, max));
                    }
                }
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWakeupReasonInfoToList(
            android.os.BatteryStats r18, java.util.ArrayList r19) {
        /*
            r17 = this;
            r0 = r17
            java.util.Map r1 = r18.getWakeupReasonStats()
            int r2 = r1.size()
            if (r2 <= 0) goto Ld6
            long r2 = android.os.SystemClock.elapsedRealtime()
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            java.util.Set r6 = r1.keySet()
            java.util.Iterator r6 = r6.iterator()
        L1b:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto Ld6
            java.lang.Object r7 = r6.next()
            java.lang.String r7 = (java.lang.String) r7
            if (r7 != 0) goto L2a
            goto L1b
        L2a:
            java.lang.Object r8 = r1.get(r7)
            android.os.BatteryStats$Timer r8 = (android.os.BatteryStats.Timer) r8
            if (r8 == 0) goto Ld3
            r9 = 0
            long r10 = r8.getTotalTimeLocked(r2, r9)
            r12 = 500(0x1f4, double:2.47E-321)
            long r10 = r10 + r12
            long r10 = r10 / r4
            int r8 = r8.getCountLocked(r9)
            java.lang.String r12 = "Abort:"
            boolean r12 = r7.contains(r12)
            r13 = 0
            if (r12 != 0) goto L4a
        L48:
            r7 = r13
            goto L76
        L4a:
            java.lang.String r12 = "Some devices failed to suspend, or early wake event detected"
            boolean r12 = r7.contains(r12)
            if (r12 == 0) goto L53
            goto L48
        L53:
            java.lang.String r12 = "PowerManagerService.WakeLocks"
            java.lang.String r14 = "alarmtimer"
            java.lang.String[] r12 = new java.lang.String[]{r12, r14}
            r14 = r9
        L5c:
            r15 = 2
            if (r14 >= r15) goto L76
            r15 = r12[r14]
            boolean r15 = r7.contains(r15)
            if (r15 == 0) goto L73
            java.lang.String r12 = "******** Except for the blocked tag or tableName="
            java.lang.String r7 = r12.concat(r7)
            java.lang.String r12 = "SemBatteryUsageStatsProvider"
            android.util.Slog.w(r12, r7)
            goto L48
        L73:
            int r14 = r14 + 1
            goto L5c
        L76:
            if (r7 == 0) goto Ld3
            r12 = 0
            int r14 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r14 == 0) goto Ld3
            java.util.Map r14 = r0.mLastWakeupMap
            java.util.HashMap r14 = (java.util.HashMap) r14
            boolean r14 = r14.containsKey(r7)
            if (r14 != 0) goto L94
            java.util.Map r14 = r0.mLastWakeupMap
            android.os.SemWakeupReasonInfo r15 = new android.os.SemWakeupReasonInfo
            r15.<init>(r7, r9, r12)
            java.util.HashMap r14 = (java.util.HashMap) r14
            r14.put(r7, r15)
        L94:
            java.util.Map r14 = r0.mLastWakeupMap
            java.util.HashMap r14 = (java.util.HashMap) r14
            java.lang.Object r14 = r14.get(r7)
            android.os.SemWakeupReasonInfo r14 = (android.os.SemWakeupReasonInfo) r14
            long r15 = r14.getTime()
            long r4 = r10 - r15
            long r4 = java.lang.Math.max(r12, r4)
            int r14 = r14.getCount()
            int r14 = r8 - r14
            int r9 = java.lang.Math.max(r9, r14)
            java.util.Map r14 = r0.mLastWakeupMap
            android.os.SemWakeupReasonInfo r15 = new android.os.SemWakeupReasonInfo
            r15.<init>(r7, r8, r10)
            java.util.HashMap r14 = (java.util.HashMap) r14
            r14.put(r7, r15)
            int r8 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r8 != 0) goto Lc8
            if (r9 != 0) goto Lc8
        Lc4:
            r4 = 1000(0x3e8, double:4.94E-321)
            goto L1b
        Lc8:
            android.os.SemWakeupReasonInfo r8 = new android.os.SemWakeupReasonInfo
            r8.<init>(r7, r9, r4)
            r4 = r19
            r4.add(r8)
            goto Lc4
        Ld3:
            r4 = r19
            goto Lc4
        Ld6:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.SemBatteryUsageStatsProvider.updateWakeupReasonInfoToList(android.os.BatteryStats,"
                    + " java.util.ArrayList):void");
    }
}
