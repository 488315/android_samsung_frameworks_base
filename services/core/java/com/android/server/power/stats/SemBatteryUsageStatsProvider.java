package com.android.server.power.stats;

import android.content.Context;
import android.os.AggregateBatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.SemBatterySipper;
import android.os.SemDevicePowerInfo;
import android.os.SemKernelWakelockInfo;
import android.os.SemScreenWakeInfo;
import android.os.SemUidPowerInfo;
import android.os.SemWakeupReasonInfo;
import android.os.SystemClock;
import android.os.UidBatteryConsumer;
import android.telephony.CellSignalStrength;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.PowerProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class SemBatteryUsageStatsProvider {
    public final Context mContext;
    public final PowerProfile mPowerProfile;
    public final BatteryStats mStats;
    public final Object mLock = new Object();
    public Map mLastWakeupMap = null;
    public Map mLastKWakelockMap = null;
    public Map mLastScreenWakeMap = null;

    public SemBatteryUsageStatsProvider(Context context, BatteryStats batteryStats) {
        PowerProfile powerProfile;
        this.mContext = context;
        this.mStats = batteryStats;
        if (batteryStats instanceof BatteryStatsImpl) {
            powerProfile = ((BatteryStatsImpl) batteryStats).getPowerProfile();
        } else {
            powerProfile = new PowerProfile(context);
        }
        this.mPowerProfile = powerProfile;
    }

    public SemBatterySipper getBatteryUsageStats() {
        long elapsedRealtime = elapsedRealtime() * 1000;
        long uptimeMillis = uptimeMillis() * 1000;
        SemDevicePowerInfo semDevicePowerInfo = new SemDevicePowerInfo(0.0d);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        synchronized (this.mStats) {
            BatteryUsageStats batteryUsageStats = new BatteryUsageStatsProvider(this.mContext, this.mStats).getBatteryUsageStats(new BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includePowerModels().build());
            if (batteryUsageStats != null) {
                updateBatteryUsage(batteryUsageStats, elapsedRealtime, uptimeMillis, semDevicePowerInfo, arrayList);
                updateWakeupReasonInfoToList(this.mStats, arrayList2);
                updateKernelWakelockInfoToList(this.mStats, arrayList3);
                updateScreenWakeInfoToList(this.mStats, arrayList4);
            }
        }
        return new SemBatterySipper(semDevicePowerInfo, arrayList, arrayList2, arrayList3, arrayList4);
    }

    public final void updateBatteryUsage(BatteryUsageStats batteryUsageStats, long j, long j2, SemDevicePowerInfo semDevicePowerInfo, ArrayList arrayList) {
        HashMap hashMap = new HashMap();
        for (UidBatteryConsumer uidBatteryConsumer : batteryUsageStats.getUidBatteryConsumers()) {
            if (uidBatteryConsumer != null) {
                hashMap.put(Integer.valueOf(uidBatteryConsumer.getUid()), uidBatteryConsumer);
            }
        }
        SparseArray uidStats = this.mStats.getUidStats();
        for (int size = uidStats.size() - 1; size >= 0; size--) {
            BatteryStats.Uid uid = (BatteryStats.Uid) uidStats.valueAt(size);
            if (uid != null && hashMap.containsKey(Integer.valueOf(uid.getUid()))) {
                arrayList.add(processAppUsage(uid, (UidBatteryConsumer) hashMap.get(Integer.valueOf(uid.getUid())), semDevicePowerInfo, j));
            }
        }
        AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        for (int i = 0; i < 19; i++) {
            if (i == 0) {
                semDevicePowerInfo.screenPower = aggregateBatteryConsumer.getConsumedPower(i);
                semDevicePowerInfo.screenOnTime = aggregateBatteryConsumer.getUsageDurationMillis(i);
            } else if (i == 8) {
                semDevicePowerInfo.radioPower = aggregateBatteryConsumer.getConsumedPower(i);
            } else if (i == 11) {
                semDevicePowerInfo.wifiPower = aggregateBatteryConsumer.getConsumedPower(i);
            } else if (i != 18) {
                switch (i) {
                    case 14:
                        semDevicePowerInfo.phonePower = aggregateBatteryConsumer.getConsumedPower(i);
                        semDevicePowerInfo.phoneOnTime = aggregateBatteryConsumer.getUsageDurationMillis(i);
                        break;
                    case 15:
                        semDevicePowerInfo.aodPower = aggregateBatteryConsumer.getConsumedPower(i);
                        semDevicePowerInfo.aodTime = aggregateBatteryConsumer.getUsageDurationMillis(i);
                        break;
                    case 16:
                        semDevicePowerInfo.idlePower = aggregateBatteryConsumer.getConsumedPower(i);
                        semDevicePowerInfo.idleTime = aggregateBatteryConsumer.getUsageDurationMillis(i);
                        break;
                }
            } else {
                semDevicePowerInfo.powersharePower = (long) aggregateBatteryConsumer.getConsumedPower(i);
                semDevicePowerInfo.powershareTime = aggregateBatteryConsumer.getUsageDurationMillis(i);
            }
        }
        processDeviceUsage(batteryUsageStats, semDevicePowerInfo, j, j2);
    }

    public final SemUidPowerInfo processAppUsage(BatteryStats.Uid uid, UidBatteryConsumer uidBatteryConsumer, SemDevicePowerInfo semDevicePowerInfo, long j) {
        long j2;
        double consumedPower;
        long j3;
        SemUidPowerInfo semUidPowerInfo = new SemUidPowerInfo(uid.getUid());
        int i = 0;
        long processStateTime = uid.getProcessStateTime(0, j, 0) / 1000;
        long min = Math.min(processStateTime, getForegroundActivityTotalTime(uid, j));
        long processStateTime2 = processStateTime + ((uid.getProcessStateTime(1, j, 0) + uid.getProcessStateTime(2, j, 0)) / 1000);
        long processStateTime3 = ((uid.getProcessStateTime(3, j, 0) + uid.getProcessStateTime(4, j, 0)) + uid.getProcessStateTime(5, j, 0)) / 1000;
        int i2 = 0;
        long j4 = 0;
        long j5 = 0;
        while (i2 < 16) {
            long speakerMediaTime = uid.getSpeakerMediaTime(i2, i);
            if (speakerMediaTime <= 0) {
                j3 = processStateTime2;
            } else {
                j4 += speakerMediaTime;
                j3 = processStateTime2;
                j5 += speakerMediaTime * (i2 + 1);
            }
            i2++;
            processStateTime2 = j3;
            i = 0;
        }
        long j6 = processStateTime2;
        long j7 = j5;
        if (this.mStats.hasDisplayPowerReporting()) {
            j2 = j7;
            consumedPower = uid.getDisplayPowerDrain(0) / 1000.0d;
        } else {
            j2 = j7;
            consumedPower = uidBatteryConsumer.getConsumedPower(0);
        }
        semUidPowerInfo.screenPower = consumedPower;
        long j8 = j4;
        semUidPowerInfo.screenPower = Math.max(0.0d, consumedPower);
        semUidPowerInfo.smearedPower = semUidPowerInfo.shouldHide ? 0.0d : uidBatteryConsumer.getConsumedPower();
        semUidPowerInfo.power = Math.max(0.0d, uidBatteryConsumer.getConsumedPower() - semUidPowerInfo.screenPower);
        semUidPowerInfo.cpuTime = (uid.getUserCpuTimeUs(0) + uid.getSystemCpuTimeUs(0)) / 1000;
        semUidPowerInfo.wakelockTime = calculateWakelockTime(uid, j, 0);
        semUidPowerInfo.mobileActive = uid.getMobileRadioActiveTime(0) / 1000;
        semUidPowerInfo.mobileData = uid.getNetworkActivityBytes(0, 0) + uid.getNetworkActivityBytes(1, 0);
        semUidPowerInfo.mobilePackets = uid.getNetworkActivityPackets(0, 0) + uid.getNetworkActivityPackets(1, 0);
        semUidPowerInfo.wifiPackets = uid.getNetworkActivityPackets(2, 0) + uid.getNetworkActivityPackets(3, 0);
        semUidPowerInfo.wifiData = uid.getNetworkActivityBytes(2, 0) + uid.getNetworkActivityBytes(3, 0);
        semUidPowerInfo.wakeupAlarm = getWakeupAlarmCount(uid, 0);
        semUidPowerInfo.btScan = getBluetoothScanCount(uid, j, 0);
        semUidPowerInfo.btData = uid.getNetworkActivityBytes(4, 0) + uid.getNetworkActivityBytes(5, 0);
        updateUidGpsDuration(uid, semUidPowerInfo, j);
        semUidPowerInfo.cameraRunTime = getMsTimeFromTimer(uid.getCameraTurnedOnTimer(), j);
        semUidPowerInfo.killCount = getExcessivePowerCount(uid);
        semUidPowerInfo.screenTime = min;
        semUidPowerInfo.fgTime = j6;
        semUidPowerInfo.bgTime = processStateTime3;
        semUidPowerInfo.spkTime = j8;
        semUidPowerInfo.spkLevel = j2;
        semUidPowerInfo.audioTime = getMsTimeFromTimer(uid.getAudioTurnedOnTimer(), j);
        int i3 = 0;
        semUidPowerInfo.networkWakeup = uid.getMobileRadioApWakeupCount(0);
        semUidPowerInfo.syncTime = getSyncTotalTime(uid, j, 0);
        int i4 = 0;
        while (i4 < this.mStats.getDisplayCount()) {
            semUidPowerInfo.displayTopActivityTime[i4] = (uid.getDisplayTopActivityTime(i4, j, i3) + 500) / 1000;
            i4++;
            i3 = 0;
        }
        semDevicePowerInfo.btScanCount += semUidPowerInfo.btScan;
        semDevicePowerInfo.gpsTime += semUidPowerInfo.gpsTime;
        semDevicePowerInfo.actualGpsTime += semUidPowerInfo.actualGpsTime;
        semDevicePowerInfo.wifiScanTime += uid.getWifiScanTime(j, 0) / 1000;
        semDevicePowerInfo.wifiScanCount += uid.getWifiScanCount(0);
        semDevicePowerInfo.pwlTime += semUidPowerInfo.wakelockTime;
        return semUidPowerInfo;
    }

    public final void updateUidGpsDuration(BatteryStats.Uid uid, SemUidPowerInfo semUidPowerInfo, long j) {
        SparseArray sensorStats = uid.getSensorStats();
        BatteryStats.Uid.Sensor sensor = (BatteryStats.Uid.Sensor) sensorStats.get(-10000);
        BatteryStats.Uid.Sensor sensor2 = (BatteryStats.Uid.Sensor) sensorStats.get(-10001);
        semUidPowerInfo.gpsTime = 0L;
        semUidPowerInfo.actualGpsTime = 0L;
        if (sensor != null && sensor.getSensorTime() != null) {
            semUidPowerInfo.gpsTime = sensor.getSensorTime().getTotalTimeLocked(j, 0) / 1000;
        }
        if (sensor2 == null || sensor2.getSensorTime() == null) {
            return;
        }
        semUidPowerInfo.actualGpsTime = sensor2.getSensorTime().getTotalTimeLocked(j, 0) / 1000;
    }

    public final long getMsTimeFromTimer(BatteryStats.Timer timer, long j) {
        if (timer != null) {
            return (timer.getTotalTimeLocked(j, 0) + 500) / 1000;
        }
        return 0L;
    }

    public final long calculateWakelockTime(BatteryStats.Uid uid, long j, int i) {
        ArrayMap wakelockStats = uid.getWakelockStats();
        int size = wakelockStats.size();
        long j2 = 0;
        for (int i2 = 0; i2 < size; i2++) {
            BatteryStats.Timer wakeTime = ((BatteryStats.Uid.Wakelock) wakelockStats.valueAt(i2)).getWakeTime(0);
            if (wakeTime != null) {
                j2 += wakeTime.getTotalTimeLocked(j, i);
            }
        }
        return j2 / 1000;
    }

    public final void processDeviceUsage(BatteryUsageStats batteryUsageStats, SemDevicePowerInfo semDevicePowerInfo, long j, long j2) {
        processScreenUsage(semDevicePowerInfo, j);
        processSpeakerUsage(semDevicePowerInfo);
        processRadioUsage(semDevicePowerInfo, j);
        processWifiUsage(semDevicePowerInfo, j);
        processBluetoothUsage(semDevicePowerInfo, j);
        processNetworkUsage(semDevicePowerInfo);
        processModemUsage(semDevicePowerInfo);
        semDevicePowerInfo.totalPower = batteryUsageStats.getConsumedPower();
        semDevicePowerInfo.batteryPerc = this.mStats.getHighDischargeAmountSinceCharge();
        semDevicePowerInfo.screenOffTime = this.mStats.computeBatteryScreenOffRealtime(j, 0) / 1000;
        semDevicePowerInfo.screenOnTime = (this.mStats.computeBatteryRealtime(j, 0) / 1000) - semDevicePowerInfo.screenOffTime;
        semDevicePowerInfo.screenOnCount = this.mStats.getScreenOnCount(0);
        semDevicePowerInfo.subScreenOnTime = this.mStats.getSubScreenOnTime(j, 0) / 1000;
        semDevicePowerInfo.uptime = this.mStats.computeBatteryUptime(j2, 0) / 1000;
        semDevicePowerInfo.screenOffUptime = this.mStats.computeBatteryScreenOffUptime(j2, 0) / 1000;
        semDevicePowerInfo.psmTime = this.mStats.getPowerSaveModeEnabledTime(j, 0) / 1000;
        semDevicePowerInfo.screenOffDischarge = this.mStats.getDischargeAmountScreenOffSinceChargePermil();
        semDevicePowerInfo.screenOnDischarge = this.mStats.getDischargeAmountScreenOnSinceChargePermil();
        semDevicePowerInfo.subScreenOffDischarge = this.mStats.getDischargeAmountScreenOffSinceChargePermil();
        semDevicePowerInfo.subScreenOnDischarge = this.mStats.getDischargeAmountSubScreenOnSinceChargePermil();
        semDevicePowerInfo.subAodTime = this.mStats.getSubScreenDozeTime(j, 0) / 1000;
        semDevicePowerInfo.screenOffCoulombCounter = this.mStats.getDischargeAmountScreenOffSinceChargeCoulombCounter();
        semDevicePowerInfo.screenOnCoulombCounter = this.mStats.getDischargeAmountScreenOnSinceChargeCoulombCounter();
        semDevicePowerInfo.powershareTime = this.mStats.getTxPowerSharingTime(j, 0) / 1000;
        semDevicePowerInfo.powersharePower = this.mStats.getTxSharingDischargeAmount(0);
        semDevicePowerInfo.hrrAlwaysTime = (this.mStats.getDisplayHighRefreshRateTime(2, j, 0) + this.mStats.getDisplayHighRefreshRateTime(1, j, 0)) / 1000;
        semDevicePowerInfo.subHrrAlwaysTime = (this.mStats.getSubDisplayHighRefreshRateTime(2, j, 0) + this.mStats.getSubDisplayHighRefreshRateTime(1, j, 0)) / 1000;
        semDevicePowerInfo.screenOnGpsTime = this.mStats.getScreenOnGpsRunningTime(j, 0) / 1000;
    }

    public final void processScreenUsage(SemDevicePowerInfo semDevicePowerInfo, long j) {
        for (int i = 0; i < 5; i++) {
            semDevicePowerInfo.screenBrightnessTime[i] = this.mStats.getScreenBrightnessTime(i, j, 0) / 1000;
            semDevicePowerInfo.screenAutoBrightnessTime[i] = this.mStats.getScreenAutoBrightnessTime(i, j, 0) / 1000;
            semDevicePowerInfo.subScreenBrightnessTime[i] = this.mStats.getSubScreenBrightnessTime(i, j, 0) / 1000;
            semDevicePowerInfo.subScreenAutoBrightnessTime[i] = this.mStats.getSubScreenAutoBrightnessTime(i, j, 0) / 1000;
        }
        semDevicePowerInfo.screenHighBrightnessTime = this.mStats.getScreenHighBrightnessTime(j, 0) / 1000;
        semDevicePowerInfo.subScreenHighBrightnessTime = this.mStats.getSubScreenHighBrightnessTime(j, 0) / 1000;
    }

    public final void processSpeakerUsage(SemDevicePowerInfo semDevicePowerInfo) {
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        int i = 0;
        while (i < 15) {
            double speakerCallTime = this.mStats.getSpeakerCallTime(i, 0);
            double speakerMediaTime = this.mStats.getSpeakerMediaTime(i, 0);
            long j5 = (long) (j + speakerCallTime);
            i++;
            double d = i;
            j2 = (long) (j2 + (speakerCallTime * d));
            j3 = (long) (j3 + speakerMediaTime);
            j4 = (long) (j4 + (speakerMediaTime * d));
            j = j5;
        }
        semDevicePowerInfo.spkCallTime = j;
        semDevicePowerInfo.spkCallLevel = j2;
        semDevicePowerInfo.spkMediaTime = j3;
        semDevicePowerInfo.spkMediaLevel = j4;
    }

    public final void processRadioUsage(SemDevicePowerInfo semDevicePowerInfo, long j) {
        int numSignalStrengthLevels = CellSignalStrength.getNumSignalStrengthLevels();
        for (int i = 0; i < numSignalStrengthLevels; i++) {
            semDevicePowerInfo.signalStrengthTime[i] = this.mStats.getPhoneSignalStrengthTime(i, j, 0) / 1000;
        }
        semDevicePowerInfo.mobileActiveTime = this.mStats.getMobileRadioActiveTime(j, 0) / 1000;
        semDevicePowerInfo.mobileActiveCount = this.mStats.getMobileRadioActiveCount(0);
        semDevicePowerInfo.mobileActiveTime5G = this.mStats.getMobileActive5GTime(j, 0) / 1000;
    }

    public final void processWifiUsage(SemDevicePowerInfo semDevicePowerInfo, long j) {
        semDevicePowerInfo.wifiOnTime = this.mStats.getWifiOnTime(j, 0) / 1000;
    }

    public final void processBluetoothUsage(SemDevicePowerInfo semDevicePowerInfo, long j) {
        BatteryStats.ControllerActivityCounter bluetoothControllerActivity = this.mStats.getBluetoothControllerActivity();
        semDevicePowerInfo.btOnTime = bluetoothControllerActivity.getIdleTimeCounter().getCountLocked(0) + bluetoothControllerActivity.getRxTimeCounter().getCountLocked(0) + bluetoothControllerActivity.getTxTimeCounters()[0].getCountLocked(0);
        semDevicePowerInfo.btScanTime = this.mStats.getBluetoothScanTime(j, 0) / 1000;
        semDevicePowerInfo.btTotalBytes = this.mStats.getNetworkActivityBytes(4, 0) + this.mStats.getNetworkActivityBytes(5, 0);
    }

    public final void processNetworkUsage(SemDevicePowerInfo semDevicePowerInfo) {
        semDevicePowerInfo.mobileTotalBytes = this.mStats.getNetworkActivityBytes(0, 0) + this.mStats.getNetworkActivityBytes(1, 0);
        semDevicePowerInfo.wifiTotalBytes = this.mStats.getNetworkActivityBytes(2, 0) + this.mStats.getNetworkActivityBytes(3, 0);
        semDevicePowerInfo.mobileTotalPackets = this.mStats.getNetworkActivityPackets(0, 0) + this.mStats.getNetworkActivityPackets(1, 0);
        semDevicePowerInfo.wifiTotalPackets = this.mStats.getNetworkActivityPackets(2, 0) + this.mStats.getNetworkActivityPackets(3, 0);
    }

    public final void processModemUsage(SemDevicePowerInfo semDevicePowerInfo) {
        BatteryStats.ModemActivityCounter networkModemControllerActivity = this.mStats.getNetworkModemControllerActivity();
        semDevicePowerInfo.cpSleepTime = networkModemControllerActivity.getSleepTimeCounter().getCountLocked(0);
        semDevicePowerInfo.cpIdleTime = networkModemControllerActivity.getIdleTimeCounter().getCountLocked(0);
        long[] jArr = new long[5];
        int i = 0;
        for (BatteryStats.LongCounter longCounter : networkModemControllerActivity.getNrModemActivityInfo().getTxTimeCounters()) {
            long countLocked = longCounter.getCountLocked(0);
            jArr[i] = countLocked;
            semDevicePowerInfo.nrTxTime += countLocked;
            i++;
            semDevicePowerInfo.nrTxLevel += countLocked * i;
        }
        long j = semDevicePowerInfo.nrTxTime;
        if (j != 0) {
            semDevicePowerInfo.nrTxLevel /= j;
        }
        semDevicePowerInfo.nrRxTime = networkModemControllerActivity.getNrModemActivityInfo().getRxTimeCounter().getCountLocked(0);
        semDevicePowerInfo.nrTxByte = networkModemControllerActivity.getNrModemActivityInfo().getTxByteCounter().getCountLocked(0);
        semDevicePowerInfo.nrRxByte = networkModemControllerActivity.getNrModemActivityInfo().getRxByteCounter().getCountLocked(0);
        long[] jArr2 = new long[5];
        int i2 = 0;
        for (BatteryStats.LongCounter longCounter2 : networkModemControllerActivity.getLcModemActivityInfo().getTxTimeCounters()) {
            long countLocked2 = longCounter2.getCountLocked(0);
            jArr2[i2] = countLocked2;
            semDevicePowerInfo.lcTxTime += countLocked2;
            i2++;
            semDevicePowerInfo.lcTxLevel += countLocked2 * i2;
        }
        long j2 = semDevicePowerInfo.lcTxTime;
        if (j2 != 0) {
            semDevicePowerInfo.lcTxLevel /= j2;
        }
        semDevicePowerInfo.lcRxTime = networkModemControllerActivity.getLcModemActivityInfo().getRxTimeCounter().getCountLocked(0);
        semDevicePowerInfo.lcTxByte = networkModemControllerActivity.getLcModemActivityInfo().getTxByteCounter().getCountLocked(0);
        semDevicePowerInfo.lcRxByte = networkModemControllerActivity.getLcModemActivityInfo().getRxByteCounter().getCountLocked(0);
    }

    public final int getWakeupAlarmCount(BatteryStats.Uid uid, int i) {
        ArrayMap packageStats = uid.getPackageStats();
        int i2 = 0;
        for (int size = packageStats.size() - 1; size >= 0; size--) {
            ArrayMap wakeupAlarmStats = ((BatteryStats.Uid.Pkg) packageStats.valueAt(size)).getWakeupAlarmStats();
            if (((String) packageStats.keyAt(size)) != null) {
                for (int size2 = wakeupAlarmStats.size() - 1; size2 >= 0; size2--) {
                    i2 += ((BatteryStats.Counter) wakeupAlarmStats.valueAt(size2)).getCountLocked(i);
                }
            }
        }
        return i2;
    }

    public final long getForegroundActivityTotalTime(BatteryStats.Uid uid, long j) {
        BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer != null) {
            return foregroundActivityTimer.getTotalTimeLocked(j, 0) / 1000;
        }
        return 0L;
    }

    public final void updateWakeupReasonInfoToList(BatteryStats batteryStats, ArrayList arrayList) {
        BatteryStats.Timer timer;
        Map wakeupReasonStats = batteryStats.getWakeupReasonStats();
        if (wakeupReasonStats.size() > 0) {
            for (Map.Entry entry : wakeupReasonStats.entrySet()) {
                if (entry != null && (timer = (BatteryStats.Timer) entry.getValue()) != null) {
                    int countLocked = timer.getCountLocked(0);
                    String str = (String) entry.getKey();
                    long computeWakeupReasonTime = computeWakeupReasonTime(timer, SystemClock.elapsedRealtime() * 1000, 0);
                    String makeWakeupReasonListName = makeWakeupReasonListName(str);
                    if (makeWakeupReasonListName != null && countLocked > 0 && computeWakeupReasonTime != 0) {
                        arrayList.add(new SemWakeupReasonInfo(makeWakeupReasonListName, countLocked, computeWakeupReasonTime));
                    }
                }
            }
            if (this.mLastWakeupMap == null) {
                this.mLastWakeupMap = new HashMap();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SemWakeupReasonInfo semWakeupReasonInfo = (SemWakeupReasonInfo) it.next();
                    this.mLastWakeupMap.put(semWakeupReasonInfo.getTag(), semWakeupReasonInfo);
                }
                arrayList.clear();
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                SemWakeupReasonInfo semWakeupReasonInfo2 = (SemWakeupReasonInfo) it2.next();
                String tag = semWakeupReasonInfo2.getTag();
                long count = semWakeupReasonInfo2.getCount();
                long time = semWakeupReasonInfo2.getTime();
                if (this.mLastWakeupMap.containsKey(tag)) {
                    semWakeupReasonInfo2.calculateDelta((SemWakeupReasonInfo) this.mLastWakeupMap.get(tag));
                    if (semWakeupReasonInfo2.getCount() == 0) {
                    }
                }
                this.mLastWakeupMap.put(tag, new SemWakeupReasonInfo(tag, count, time));
                arrayList2.add(semWakeupReasonInfo2);
            }
            arrayList.clear();
            arrayList.addAll(arrayList2);
        }
    }

    public final long computeWakeupReasonTime(BatteryStats.Timer timer, long j, int i) {
        if (timer != null) {
            return (timer.getTotalTimeLocked(j, i) + 500) / 1000;
        }
        return 0L;
    }

    public final String makeWakeupReasonListName(String str) {
        if (!str.contains("Abort:") || str.contains("Some devices failed to suspend, or early wake event detected")) {
            return null;
        }
        String[] strArr = {"PowerManagerService.WakeLocks", "alarmtimer"};
        for (int i = 0; i < 2; i++) {
            if (str.contains(strArr[i])) {
                Slog.i("SemBatteryUsageStatsProvider", "makeWakeupReasonListName WhiteList Tag/tableName=" + str);
                return null;
            }
        }
        return str;
    }

    public final void updateScreenWakeInfoToList(BatteryStats batteryStats, ArrayList arrayList) {
        BatteryStats.LongCounter longCounter;
        Map screenWakeStats = batteryStats.getScreenWakeStats();
        if (screenWakeStats != null && screenWakeStats.size() > 0) {
            for (Map.Entry entry : screenWakeStats.entrySet()) {
                if (entry != null && (longCounter = (BatteryStats.LongCounter) entry.getValue()) != null) {
                    String str = (String) entry.getKey();
                    long countLocked = longCounter.getCountLocked(0);
                    if (str != null && countLocked > 0) {
                        arrayList.add(new SemScreenWakeInfo(str, countLocked));
                    }
                }
            }
            if (this.mLastScreenWakeMap == null) {
                this.mLastScreenWakeMap = new HashMap();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SemScreenWakeInfo semScreenWakeInfo = (SemScreenWakeInfo) it.next();
                    this.mLastScreenWakeMap.put(semScreenWakeInfo.getTag(), semScreenWakeInfo);
                }
                arrayList.clear();
                Slog.i("SemBatteryUsageStatsProvider", "First update of ScreenWake");
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                SemScreenWakeInfo semScreenWakeInfo2 = (SemScreenWakeInfo) it2.next();
                String tag = semScreenWakeInfo2.getTag();
                long count = semScreenWakeInfo2.getCount();
                if (this.mLastScreenWakeMap.containsKey(tag)) {
                    semScreenWakeInfo2.calculateDelta((SemScreenWakeInfo) this.mLastScreenWakeMap.get(tag));
                    if (semScreenWakeInfo2.getCount() == 0) {
                    }
                }
                this.mLastScreenWakeMap.put(tag, new SemScreenWakeInfo(tag, count));
                arrayList2.add(semScreenWakeInfo2);
            }
            arrayList.clear();
            arrayList.addAll(arrayList2);
        }
    }

    public final void updateKernelWakelockInfoToList(BatteryStats batteryStats, ArrayList arrayList) {
        BatteryStats.Timer timer;
        Map kernelWakelockStats = batteryStats.getKernelWakelockStats();
        if (kernelWakelockStats != null && kernelWakelockStats.size() > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            for (Map.Entry entry : kernelWakelockStats.entrySet()) {
                if (entry != null && (timer = (BatteryStats.Timer) entry.getValue()) != null) {
                    String str = (String) entry.getKey();
                    long computeWakeLock = computeWakeLock(timer, elapsedRealtime, 0);
                    int countLocked = timer.getCountLocked(0);
                    if (str != null && computeWakeLock > 0 && countLocked > 0) {
                        arrayList.add(new SemKernelWakelockInfo(str, countLocked, computeWakeLock));
                    }
                }
            }
            if (this.mLastKWakelockMap == null) {
                this.mLastKWakelockMap = new HashMap();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SemKernelWakelockInfo semKernelWakelockInfo = (SemKernelWakelockInfo) it.next();
                    this.mLastKWakelockMap.put(semKernelWakelockInfo.getTag(), semKernelWakelockInfo);
                }
                arrayList.clear();
                Slog.i("SemBatteryUsageStatsProvider", "First update of KernelWakelock");
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                SemKernelWakelockInfo semKernelWakelockInfo2 = (SemKernelWakelockInfo) it2.next();
                String tag = semKernelWakelockInfo2.getTag();
                long count = semKernelWakelockInfo2.getCount();
                long time = semKernelWakelockInfo2.getTime();
                if (this.mLastKWakelockMap.containsKey(tag)) {
                    semKernelWakelockInfo2.calculateDelta((SemKernelWakelockInfo) this.mLastKWakelockMap.get(tag));
                    if (semKernelWakelockInfo2.getCount() > 0 && semKernelWakelockInfo2.getTime() > 0) {
                    }
                }
                this.mLastKWakelockMap.put(tag, new SemKernelWakelockInfo(tag, count, time));
                arrayList2.add(semKernelWakelockInfo2);
            }
            Collections.sort(arrayList2);
            arrayList.clear();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                arrayList.add((SemKernelWakelockInfo) it3.next());
                if (arrayList.size() >= 10) {
                    return;
                }
            }
        }
    }

    public final int getBluetoothScanCount(BatteryStats.Uid uid, long j, int i) {
        BatteryStats.Timer bluetoothScanTimer = uid.getBluetoothScanTimer();
        if (bluetoothScanTimer == null || (bluetoothScanTimer.getTotalTimeLocked(j, i) + 500) / 1000 == 0) {
            return 0;
        }
        return bluetoothScanTimer.getCountLocked(i);
    }

    public final long getSyncTotalTime(BatteryStats.Uid uid, long j, int i) {
        ArrayMap syncStats = uid.getSyncStats();
        long j2 = 0;
        for (int size = syncStats.size() - 1; size >= 0; size--) {
            BatteryStats.Timer timer = (BatteryStats.Timer) syncStats.valueAt(size);
            if (timer != null) {
                j2 += (timer.getTotalTimeLocked(j, i) + 500) / 1000;
            }
        }
        return j2;
    }

    public final int getExcessivePowerCount(BatteryStats.Uid uid) {
        ArrayMap processStats = uid.getProcessStats();
        if (processStats == null) {
            return 0;
        }
        int size = processStats.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((BatteryStats.Uid.Proc) processStats.valueAt(i2)).countExcessivePowers();
        }
        return i;
    }

    public static long computeWakeLock(BatteryStats.Timer timer, long j, int i) {
        if (timer != null) {
            return (timer.getTotalTimeLocked(j, i) + 500) / 1000;
        }
        return 0L;
    }

    public final long elapsedRealtime() {
        BatteryStats batteryStats = this.mStats;
        if (batteryStats instanceof BatteryStatsImpl) {
            return ((BatteryStatsImpl) batteryStats).mClock.elapsedRealtime();
        }
        return SystemClock.elapsedRealtime();
    }

    public final long uptimeMillis() {
        BatteryStats batteryStats = this.mStats;
        if (batteryStats instanceof BatteryStatsImpl) {
            return ((BatteryStatsImpl) batteryStats).mClock.uptimeMillis();
        }
        return SystemClock.uptimeMillis();
    }
}
