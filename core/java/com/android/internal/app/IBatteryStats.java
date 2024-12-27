package com.android.internal.app;

import android.Manifest;
import android.app.ActivityThread;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.net.NetworkStack;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Binder;
import android.os.BluetoothBatteryStats;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SemBatterySipper;
import android.os.SemModemActivityInfo;
import android.os.SpeakerOutEnergyInfo;
import android.os.WakeLockStats;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.os.connectivity.WifiBatteryStats;
import android.os.health.HealthStatsParceler;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import com.android.internal.app.IBatteryStatsCallback;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import java.util.List;

/* loaded from: classes5.dex */
public interface IBatteryStats extends IInterface {
    public static final String KEY_UID_SNAPSHOTS = "uid_snapshots";

    long computeBatteryScreenOffRealtimeMs() throws RemoteException;

    long computeBatteryTimeRemaining() throws RemoteException;

    long computeChargeTimeRemaining() throws RemoteException;

    long getAwakeTimeBattery() throws RemoteException;

    long getAwakeTimePlugged() throws RemoteException;

    List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> list) throws RemoteException;

    BluetoothBatteryStats getBluetoothBatteryStats() throws RemoteException;

    CellularBatteryStats getCellularBatteryStats() throws RemoteException;

    SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) throws RemoteException;

    SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() throws RemoteException;

    GpsBatteryStats getGpsBatteryStats() throws RemoteException;

    long getScreenOffDischargeMah() throws RemoteException;

    SemBatterySipper getSemBatteryUsageStats() throws RemoteException;

    WakeLockStats getWakeLockStats() throws RemoteException;

    WifiBatteryStats getWifiBatteryStats() throws RemoteException;

    boolean isCharging() throws RemoteException;

    void noteBleDutyScanStarted(WorkSource workSource, boolean z, int i) throws RemoteException;

    void noteBleDutyScanStopped(WorkSource workSource, boolean z, int i) throws RemoteException;

    void noteBleScanReset() throws RemoteException;

    void noteBleScanResults(WorkSource workSource, int i) throws RemoteException;

    void noteBleScanStarted(WorkSource workSource, boolean z) throws RemoteException;

    void noteBleScanStopped(WorkSource workSource, boolean z) throws RemoteException;

    void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException;

    void noteChangeWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, WorkSource workSource2, int i3, String str3, String str4, int i4, boolean z) throws RemoteException;

    void noteConnectivityChanged(int i, String str) throws RemoteException;

    void noteDeviceIdleMode(int i, String str, int i2) throws RemoteException;

    void noteDualScreenBrightness(int i, int i2, int i3) throws RemoteException;

    void noteDualScreenState(int i, int i2, int i3) throws RemoteException;

    void noteEvent(int i, String str, int i2) throws RemoteException;

    void noteFlashlightOff(int i) throws RemoteException;

    void noteFlashlightOn(int i) throws RemoteException;

    void noteFullWifiLockAcquired(int i) throws RemoteException;

    void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException;

    void noteFullWifiLockReleased(int i) throws RemoteException;

    void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException;

    void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException;

    void noteGpsSignalQuality(int i) throws RemoteException;

    void noteInteractive(boolean z) throws RemoteException;

    void noteJobFinish(String str, int i, int i2) throws RemoteException;

    void noteJobStart(String str, int i) throws RemoteException;

    void noteLongPartialWakelockFinish(String str, String str2, int i) throws RemoteException;

    void noteLongPartialWakelockFinishFromSource(String str, String str2, WorkSource workSource) throws RemoteException;

    void noteLongPartialWakelockStart(String str, String str2, int i) throws RemoteException;

    void noteLongPartialWakelockStartFromSource(String str, String str2, WorkSource workSource) throws RemoteException;

    void noteMobileRadioPowerState(int i, long j, int i2) throws RemoteException;

    void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException;

    void noteNetworkInterfaceForTransports(String str, int[] iArr) throws RemoteException;

    void noteNetworkStatsEnabled() throws RemoteException;

    void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws RemoteException;

    void notePhoneOff() throws RemoteException;

    void notePhoneOn() throws RemoteException;

    void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException;

    void notePhoneState(int i) throws RemoteException;

    void noteResetAudio() throws RemoteException;

    void noteResetCamera() throws RemoteException;

    void noteResetFlashlight() throws RemoteException;

    void noteResetGps() throws RemoteException;

    void noteResetVideo() throws RemoteException;

    void noteScreenBrightness(int i) throws RemoteException;

    void noteScreenState(int i) throws RemoteException;

    void noteStartAudio(int i) throws RemoteException;

    void noteStartCamera(int i) throws RemoteException;

    void noteStartGps(int i) throws RemoteException;

    void noteStartSensor(int i, int i2) throws RemoteException;

    void noteStartTxPowerSharing() throws RemoteException;

    void noteStartVideo(int i) throws RemoteException;

    void noteStartWakelock(int i, int i2, String str, String str2, int i3, boolean z) throws RemoteException;

    void noteStartWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, boolean z) throws RemoteException;

    void noteStopAudio(int i) throws RemoteException;

    void noteStopCamera(int i) throws RemoteException;

    void noteStopGps(int i) throws RemoteException;

    void noteStopSensor(int i, int i2) throws RemoteException;

    void noteStopTxPowerSharing() throws RemoteException;

    void noteStopVideo(int i) throws RemoteException;

    void noteStopWakelock(int i, int i2, String str, String str2, int i3) throws RemoteException;

    void noteStopWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2) throws RemoteException;

    void noteSyncFinish(String str, int i) throws RemoteException;

    void noteSyncStart(String str, int i) throws RemoteException;

    void noteUpdateNetworkStats(String str) throws RemoteException;

    void noteUserActivity(int i, int i2) throws RemoteException;

    void noteVibratorOff(int i) throws RemoteException;

    void noteVibratorOn(int i, long j) throws RemoteException;

    void noteWakeUp(String str, int i) throws RemoteException;

    void noteWakeupSensorEvent(long j, int i, int i2) throws RemoteException;

    void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int i) throws RemoteException;

    void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException;

    void noteWifiMulticastDisabled(int i) throws RemoteException;

    void noteWifiMulticastEnabled(int i) throws RemoteException;

    void noteWifiOff() throws RemoteException;

    void noteWifiOn() throws RemoteException;

    void noteWifiRadioPowerState(int i, long j, int i2) throws RemoteException;

    void noteWifiRssiChanged(int i) throws RemoteException;

    void noteWifiRunning(WorkSource workSource) throws RemoteException;

    void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException;

    void noteWifiScanStarted(int i) throws RemoteException;

    void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiScanStopped(int i) throws RemoteException;

    void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiState(int i, String str) throws RemoteException;

    void noteWifiStopped(WorkSource workSource) throws RemoteException;

    void noteWifiSupplicantStateChanged(int i, boolean z) throws RemoteException;

    boolean registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException;

    void registerDeviceBatteryInfoChanged(String str) throws RemoteException;

    void resetBattery(boolean z) throws RemoteException;

    void setBatteryLevel(int i, boolean z) throws RemoteException;

    void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, int i9, int i10, int i11, int i12, boolean z) throws RemoteException;

    void setChargerAcOnline(boolean z, boolean z2) throws RemoteException;

    boolean setChargingStateUpdateDelayMillis(int i) throws RemoteException;

    void setDeviceBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) throws RemoteException;

    void setTemperatureNCurrent(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void suspendBatteryInput() throws RemoteException;

    HealthStatsParceler takeUidSnapshot(int i) throws RemoteException;

    HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws RemoteException;

    void takeUidSnapshotsAsync(int[] iArr, ResultReceiver resultReceiver) throws RemoteException;

    void unRegisterDeviceBatteryInfoChanged(String str) throws RemoteException;

    void unplugBattery(boolean z) throws RemoteException;

    boolean unregisterBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException;

    void unsetDeviceBatteryInfo(String str) throws RemoteException;

    void updateSemModemActivityInfo(SemModemActivityInfo semModemActivityInfo) throws RemoteException;

    void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo speakerOutEnergyInfo) throws RemoteException;

    public static class Default implements IBatteryStats {
        @Override // com.android.internal.app.IBatteryStats
        public void noteStartSensor(int uid, int sensor) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopSensor(int uid, int sensor) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartVideo(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopVideo(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartAudio(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopAudio(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetVideo() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetAudio() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOn(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOff(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartCamera(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopCamera(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetCamera() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetFlashlight() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeupSensorEvent(long elapsedNanos, int uid, int handle) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartGps(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopGps(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetGps() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> queries) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemBatterySipper getSemBatteryUsageStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean isCharging() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeBatteryTimeRemaining() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeChargeTimeRemaining() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeBatteryScreenOffRealtimeMs() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getScreenOffDischargeMah() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteEvent(int code, String name, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncStart(String name, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncFinish(String name, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobStart(String name, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobFinish(String name, int uid, int stopReason) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelock(int uid, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelock(int uid, int pid, String name, String historyName, int type) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteChangeWakelockFromSource(WorkSource ws, int pid, String name, String histyoryName, int type, WorkSource newWs, int newPid, String newName, String newHistoryName, int newType, boolean newUnimportantForLogging) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStart(String name, String historyName, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStartFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinish(String name, String historyName, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinishFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOn(int uid, long durationMillis) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOff(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsChanged(WorkSource oldSource, WorkSource newSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsSignalQuality(int signalLevel) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenState(int state) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenBrightness(int brightness) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteUserActivity(int uid, int event) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeUp(String reason, int reasonUid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteInteractive(boolean interactive) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteConnectivityChanged(int type, String extra) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteMobileRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOn() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOff() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneDataConnectionState(int dataType, boolean hasData, int serviceType, int nrState, int nrFrequency) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneState(int phoneState) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOn() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOff() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunning(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunningChanged(WorkSource oldWs, WorkSource newWs) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiStopped(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiState(int wifiState, String accessPoint) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiSupplicantStateChanged(int supplState, boolean failedAuth) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRssiChanged(int newRssi) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquired(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleased(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStarted(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStopped(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastEnabled(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastDisabled(int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquiredFromSource(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleasedFromSource(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStartedFromSource(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStoppedFromSource(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStartedFromSource(WorkSource ws, int csph) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStoppedFromSource(WorkSource ws) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkInterfaceForTransports(String iface, int[] transportTypes) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkStatsEnabled() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDeviceIdleMode(int mode, String activeReason, int activeUid) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryState(int status, int health, int plugType, int level, int temp, int volt, int chargeUAh, int chargeFullUAh, long chargeTimeToFullSeconds, int secEvent, int secOnline, int secCurrentEvent, int secTxShareEvent, boolean otgOnline) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getAwakeTimeBattery() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getAwakeTimePlugged() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStarted(WorkSource ws, boolean isUnoptimized) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStopped(WorkSource ws, boolean isUnoptimized) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanReset() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanResults(WorkSource ws, int numNewResults) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public WakeLockStats getWakeLockStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public BluetoothBatteryStats getBluetoothBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public HealthStatsParceler takeUidSnapshot(int uid) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public HealthStatsParceler[] takeUidSnapshots(int[] uid) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void takeUidSnapshotsAsync(int[] uid, ResultReceiver result) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteModemControllerActivity(ModemActivityInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiControllerActivity(WifiActivityEnergyInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean setChargingStateUpdateDelayMillis(int delay) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setChargerAcOnline(boolean online, boolean forceUpdate) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryLevel(int level, boolean forceUpdate) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unplugBattery(boolean forceUpdate) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void resetBattery(boolean forceUpdate) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void suspendBatteryInput() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setTemperatureNCurrent(int ap_temp, int pa_temp, int skin_temp, int sub_batt_temp, int current) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean registerBatteryStatsCallback(IBatteryStatsCallback callback) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean unregisterBatteryStatsCallback(IBatteryStatsCallback callback) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteUpdateNetworkStats(String caller) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartTxPowerSharing() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopTxPowerSharing() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleDutyScanStarted(WorkSource ws, boolean isUnoptimized, int dutyCycle) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleDutyScanStopped(WorkSource ws, boolean isUnoptimized, int dutyCycle) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDualScreenState(int state, int displayId, int screenPolicy) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDualScreenBrightness(int brightness, int displayId, int screenPolicy) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void updateSemModemActivityInfo(SemModemActivityInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String address) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void registerDeviceBatteryInfoChanged(String packageName) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unRegisterDeviceBatteryInfoChanged(String packageName) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setDeviceBatteryInfo(String address, SemCompanionDeviceBatteryInfo info) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unsetDeviceBatteryInfo(String address) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBatteryStats {
        public static final String DESCRIPTOR = "com.android.internal.app.IBatteryStats";
        static final int TRANSACTION_computeBatteryScreenOffRealtimeMs = 24;
        static final int TRANSACTION_computeBatteryTimeRemaining = 22;
        static final int TRANSACTION_computeChargeTimeRemaining = 23;
        static final int TRANSACTION_getAwakeTimeBattery = 81;
        static final int TRANSACTION_getAwakeTimePlugged = 82;
        static final int TRANSACTION_getBatteryUsageStats = 19;
        static final int TRANSACTION_getBluetoothBatteryStats = 91;
        static final int TRANSACTION_getCellularBatteryStats = 87;
        static final int TRANSACTION_getDeviceBatteryInfo = 117;
        static final int TRANSACTION_getDeviceBatteryInfos = 116;
        static final int TRANSACTION_getGpsBatteryStats = 89;
        static final int TRANSACTION_getScreenOffDischargeMah = 25;
        static final int TRANSACTION_getSemBatteryUsageStats = 20;
        static final int TRANSACTION_getWakeLockStats = 90;
        static final int TRANSACTION_getWifiBatteryStats = 88;
        static final int TRANSACTION_isCharging = 21;
        static final int TRANSACTION_noteBleDutyScanStarted = 111;
        static final int TRANSACTION_noteBleDutyScanStopped = 112;
        static final int TRANSACTION_noteBleScanReset = 85;
        static final int TRANSACTION_noteBleScanResults = 86;
        static final int TRANSACTION_noteBleScanStarted = 83;
        static final int TRANSACTION_noteBleScanStopped = 84;
        static final int TRANSACTION_noteBluetoothControllerActivity = 95;
        static final int TRANSACTION_noteChangeWakelockFromSource = 34;
        static final int TRANSACTION_noteConnectivityChanged = 49;
        static final int TRANSACTION_noteDeviceIdleMode = 79;
        static final int TRANSACTION_noteDualScreenBrightness = 114;
        static final int TRANSACTION_noteDualScreenState = 113;
        static final int TRANSACTION_noteEvent = 26;
        static final int TRANSACTION_noteFlashlightOff = 10;
        static final int TRANSACTION_noteFlashlightOn = 9;
        static final int TRANSACTION_noteFullWifiLockAcquired = 64;
        static final int TRANSACTION_noteFullWifiLockAcquiredFromSource = 70;
        static final int TRANSACTION_noteFullWifiLockReleased = 65;
        static final int TRANSACTION_noteFullWifiLockReleasedFromSource = 71;
        static final int TRANSACTION_noteGpsChanged = 42;
        static final int TRANSACTION_noteGpsSignalQuality = 43;
        static final int TRANSACTION_noteInteractive = 48;
        static final int TRANSACTION_noteJobFinish = 30;
        static final int TRANSACTION_noteJobStart = 29;
        static final int TRANSACTION_noteLongPartialWakelockFinish = 38;
        static final int TRANSACTION_noteLongPartialWakelockFinishFromSource = 39;
        static final int TRANSACTION_noteLongPartialWakelockStart = 36;
        static final int TRANSACTION_noteLongPartialWakelockStartFromSource = 37;
        static final int TRANSACTION_noteMobileRadioPowerState = 50;
        static final int TRANSACTION_noteModemControllerActivity = 96;
        static final int TRANSACTION_noteNetworkInterfaceForTransports = 77;
        static final int TRANSACTION_noteNetworkStatsEnabled = 78;
        static final int TRANSACTION_notePhoneDataConnectionState = 54;
        static final int TRANSACTION_notePhoneOff = 52;
        static final int TRANSACTION_notePhoneOn = 51;
        static final int TRANSACTION_notePhoneSignalStrength = 53;
        static final int TRANSACTION_notePhoneState = 55;
        static final int TRANSACTION_noteResetAudio = 8;
        static final int TRANSACTION_noteResetCamera = 13;
        static final int TRANSACTION_noteResetFlashlight = 14;
        static final int TRANSACTION_noteResetGps = 18;
        static final int TRANSACTION_noteResetVideo = 7;
        static final int TRANSACTION_noteScreenBrightness = 45;
        static final int TRANSACTION_noteScreenState = 44;
        static final int TRANSACTION_noteStartAudio = 5;
        static final int TRANSACTION_noteStartCamera = 11;
        static final int TRANSACTION_noteStartGps = 16;
        static final int TRANSACTION_noteStartSensor = 1;
        static final int TRANSACTION_noteStartTxPowerSharing = 109;
        static final int TRANSACTION_noteStartVideo = 3;
        static final int TRANSACTION_noteStartWakelock = 31;
        static final int TRANSACTION_noteStartWakelockFromSource = 33;
        static final int TRANSACTION_noteStopAudio = 6;
        static final int TRANSACTION_noteStopCamera = 12;
        static final int TRANSACTION_noteStopGps = 17;
        static final int TRANSACTION_noteStopSensor = 2;
        static final int TRANSACTION_noteStopTxPowerSharing = 110;
        static final int TRANSACTION_noteStopVideo = 4;
        static final int TRANSACTION_noteStopWakelock = 32;
        static final int TRANSACTION_noteStopWakelockFromSource = 35;
        static final int TRANSACTION_noteSyncFinish = 28;
        static final int TRANSACTION_noteSyncStart = 27;
        static final int TRANSACTION_noteUpdateNetworkStats = 108;
        static final int TRANSACTION_noteUserActivity = 46;
        static final int TRANSACTION_noteVibratorOff = 41;
        static final int TRANSACTION_noteVibratorOn = 40;
        static final int TRANSACTION_noteWakeUp = 47;
        static final int TRANSACTION_noteWakeupSensorEvent = 15;
        static final int TRANSACTION_noteWifiBatchedScanStartedFromSource = 74;
        static final int TRANSACTION_noteWifiBatchedScanStoppedFromSource = 75;
        static final int TRANSACTION_noteWifiControllerActivity = 97;
        static final int TRANSACTION_noteWifiMulticastDisabled = 69;
        static final int TRANSACTION_noteWifiMulticastEnabled = 68;
        static final int TRANSACTION_noteWifiOff = 57;
        static final int TRANSACTION_noteWifiOn = 56;
        static final int TRANSACTION_noteWifiRadioPowerState = 76;
        static final int TRANSACTION_noteWifiRssiChanged = 63;
        static final int TRANSACTION_noteWifiRunning = 58;
        static final int TRANSACTION_noteWifiRunningChanged = 59;
        static final int TRANSACTION_noteWifiScanStarted = 66;
        static final int TRANSACTION_noteWifiScanStartedFromSource = 72;
        static final int TRANSACTION_noteWifiScanStopped = 67;
        static final int TRANSACTION_noteWifiScanStoppedFromSource = 73;
        static final int TRANSACTION_noteWifiState = 61;
        static final int TRANSACTION_noteWifiStopped = 60;
        static final int TRANSACTION_noteWifiSupplicantStateChanged = 62;
        static final int TRANSACTION_registerBatteryStatsCallback = 106;
        static final int TRANSACTION_registerDeviceBatteryInfoChanged = 118;
        static final int TRANSACTION_resetBattery = 102;
        static final int TRANSACTION_setBatteryLevel = 100;
        static final int TRANSACTION_setBatteryState = 80;
        static final int TRANSACTION_setChargerAcOnline = 99;
        static final int TRANSACTION_setChargingStateUpdateDelayMillis = 98;
        static final int TRANSACTION_setDeviceBatteryInfo = 120;
        static final int TRANSACTION_setTemperatureNCurrent = 104;
        static final int TRANSACTION_suspendBatteryInput = 103;
        static final int TRANSACTION_takeUidSnapshot = 92;
        static final int TRANSACTION_takeUidSnapshots = 93;
        static final int TRANSACTION_takeUidSnapshotsAsync = 94;
        static final int TRANSACTION_unRegisterDeviceBatteryInfoChanged = 119;
        static final int TRANSACTION_unplugBattery = 101;
        static final int TRANSACTION_unregisterBatteryStatsCallback = 107;
        static final int TRANSACTION_unsetDeviceBatteryInfo = 121;
        static final int TRANSACTION_updateSemModemActivityInfo = 115;
        static final int TRANSACTION_updateSpeakerOutEnergyInfo = 105;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_noteNetworkInterfaceForTransports = {Manifest.permission.NETWORK_STACK, NetworkStack.PERMISSION_MAINLINE_NETWORK_STACK};
        static final String[] PERMISSIONS_getCellularBatteryStats = {Manifest.permission.UPDATE_DEVICE_STATS, Manifest.permission.BATTERY_STATS};
        static final String[] PERMISSIONS_getWifiBatteryStats = {Manifest.permission.UPDATE_DEVICE_STATS, Manifest.permission.BATTERY_STATS};

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IBatteryStats asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBatteryStats)) {
                return (IBatteryStats) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "noteStartSensor";
                case 2:
                    return "noteStopSensor";
                case 3:
                    return "noteStartVideo";
                case 4:
                    return "noteStopVideo";
                case 5:
                    return "noteStartAudio";
                case 6:
                    return "noteStopAudio";
                case 7:
                    return "noteResetVideo";
                case 8:
                    return "noteResetAudio";
                case 9:
                    return "noteFlashlightOn";
                case 10:
                    return "noteFlashlightOff";
                case 11:
                    return "noteStartCamera";
                case 12:
                    return "noteStopCamera";
                case 13:
                    return "noteResetCamera";
                case 14:
                    return "noteResetFlashlight";
                case 15:
                    return "noteWakeupSensorEvent";
                case 16:
                    return "noteStartGps";
                case 17:
                    return "noteStopGps";
                case 18:
                    return "noteResetGps";
                case 19:
                    return "getBatteryUsageStats";
                case 20:
                    return "getSemBatteryUsageStats";
                case 21:
                    return "isCharging";
                case 22:
                    return "computeBatteryTimeRemaining";
                case 23:
                    return "computeChargeTimeRemaining";
                case 24:
                    return "computeBatteryScreenOffRealtimeMs";
                case 25:
                    return "getScreenOffDischargeMah";
                case 26:
                    return "noteEvent";
                case 27:
                    return "noteSyncStart";
                case 28:
                    return "noteSyncFinish";
                case 29:
                    return "noteJobStart";
                case 30:
                    return "noteJobFinish";
                case 31:
                    return "noteStartWakelock";
                case 32:
                    return "noteStopWakelock";
                case 33:
                    return "noteStartWakelockFromSource";
                case 34:
                    return "noteChangeWakelockFromSource";
                case 35:
                    return "noteStopWakelockFromSource";
                case 36:
                    return "noteLongPartialWakelockStart";
                case 37:
                    return "noteLongPartialWakelockStartFromSource";
                case 38:
                    return "noteLongPartialWakelockFinish";
                case 39:
                    return "noteLongPartialWakelockFinishFromSource";
                case 40:
                    return "noteVibratorOn";
                case 41:
                    return "noteVibratorOff";
                case 42:
                    return "noteGpsChanged";
                case 43:
                    return "noteGpsSignalQuality";
                case 44:
                    return "noteScreenState";
                case 45:
                    return "noteScreenBrightness";
                case 46:
                    return "noteUserActivity";
                case 47:
                    return "noteWakeUp";
                case 48:
                    return "noteInteractive";
                case 49:
                    return "noteConnectivityChanged";
                case 50:
                    return "noteMobileRadioPowerState";
                case 51:
                    return "notePhoneOn";
                case 52:
                    return "notePhoneOff";
                case 53:
                    return "notePhoneSignalStrength";
                case 54:
                    return "notePhoneDataConnectionState";
                case 55:
                    return "notePhoneState";
                case 56:
                    return "noteWifiOn";
                case 57:
                    return "noteWifiOff";
                case 58:
                    return "noteWifiRunning";
                case 59:
                    return "noteWifiRunningChanged";
                case 60:
                    return "noteWifiStopped";
                case 61:
                    return "noteWifiState";
                case 62:
                    return "noteWifiSupplicantStateChanged";
                case 63:
                    return "noteWifiRssiChanged";
                case 64:
                    return "noteFullWifiLockAcquired";
                case 65:
                    return "noteFullWifiLockReleased";
                case 66:
                    return "noteWifiScanStarted";
                case 67:
                    return "noteWifiScanStopped";
                case 68:
                    return "noteWifiMulticastEnabled";
                case 69:
                    return "noteWifiMulticastDisabled";
                case 70:
                    return "noteFullWifiLockAcquiredFromSource";
                case 71:
                    return "noteFullWifiLockReleasedFromSource";
                case 72:
                    return "noteWifiScanStartedFromSource";
                case 73:
                    return "noteWifiScanStoppedFromSource";
                case 74:
                    return "noteWifiBatchedScanStartedFromSource";
                case 75:
                    return "noteWifiBatchedScanStoppedFromSource";
                case 76:
                    return "noteWifiRadioPowerState";
                case 77:
                    return "noteNetworkInterfaceForTransports";
                case 78:
                    return "noteNetworkStatsEnabled";
                case 79:
                    return "noteDeviceIdleMode";
                case 80:
                    return "setBatteryState";
                case 81:
                    return "getAwakeTimeBattery";
                case 82:
                    return "getAwakeTimePlugged";
                case 83:
                    return "noteBleScanStarted";
                case 84:
                    return "noteBleScanStopped";
                case 85:
                    return "noteBleScanReset";
                case 86:
                    return "noteBleScanResults";
                case 87:
                    return "getCellularBatteryStats";
                case 88:
                    return "getWifiBatteryStats";
                case 89:
                    return "getGpsBatteryStats";
                case 90:
                    return "getWakeLockStats";
                case 91:
                    return "getBluetoothBatteryStats";
                case 92:
                    return "takeUidSnapshot";
                case 93:
                    return "takeUidSnapshots";
                case 94:
                    return "takeUidSnapshotsAsync";
                case 95:
                    return "noteBluetoothControllerActivity";
                case 96:
                    return "noteModemControllerActivity";
                case 97:
                    return "noteWifiControllerActivity";
                case 98:
                    return "setChargingStateUpdateDelayMillis";
                case 99:
                    return "setChargerAcOnline";
                case 100:
                    return "setBatteryLevel";
                case 101:
                    return "unplugBattery";
                case 102:
                    return "resetBattery";
                case 103:
                    return "suspendBatteryInput";
                case 104:
                    return "setTemperatureNCurrent";
                case 105:
                    return "updateSpeakerOutEnergyInfo";
                case 106:
                    return "registerBatteryStatsCallback";
                case 107:
                    return "unregisterBatteryStatsCallback";
                case 108:
                    return "noteUpdateNetworkStats";
                case 109:
                    return "noteStartTxPowerSharing";
                case 110:
                    return "noteStopTxPowerSharing";
                case 111:
                    return "noteBleDutyScanStarted";
                case 112:
                    return "noteBleDutyScanStopped";
                case 113:
                    return "noteDualScreenState";
                case 114:
                    return "noteDualScreenBrightness";
                case 115:
                    return "updateSemModemActivityInfo";
                case 116:
                    return "getDeviceBatteryInfos";
                case 117:
                    return "getDeviceBatteryInfo";
                case 118:
                    return "registerDeviceBatteryInfoChanged";
                case 119:
                    return "unRegisterDeviceBatteryInfoChanged";
                case 120:
                    return "setDeviceBatteryInfo";
                case 121:
                    return "unsetDeviceBatteryInfo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStartSensor(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopSensor(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStartVideo(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopVideo(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStartAudio(_arg05);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopAudio(_arg06);
                    return true;
                case 7:
                    noteResetVideo();
                    reply.writeNoException();
                    return true;
                case 8:
                    noteResetAudio();
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    noteFlashlightOn(_arg07);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    noteFlashlightOff(_arg08);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStartCamera(_arg09);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopCamera(_arg010);
                    reply.writeNoException();
                    return true;
                case 13:
                    noteResetCamera();
                    reply.writeNoException();
                    return true;
                case 14:
                    noteResetFlashlight();
                    reply.writeNoException();
                    return true;
                case 15:
                    long _arg011 = data.readLong();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWakeupSensorEvent(_arg011, _arg13, _arg2);
                    reply.writeNoException();
                    return true;
                case 16:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStartGps(_arg012);
                    reply.writeNoException();
                    return true;
                case 17:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopGps(_arg013);
                    reply.writeNoException();
                    return true;
                case 18:
                    noteResetGps();
                    reply.writeNoException();
                    return true;
                case 19:
                    List<BatteryUsageStatsQuery> _arg014 = data.createTypedArrayList(BatteryUsageStatsQuery.CREATOR);
                    data.enforceNoDataAvail();
                    List<BatteryUsageStats> _result = getBatteryUsageStats(_arg014);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 20:
                    SemBatterySipper _result2 = getSemBatteryUsageStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 21:
                    boolean _result3 = isCharging();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 22:
                    long _result4 = computeBatteryTimeRemaining();
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    return true;
                case 23:
                    long _result5 = computeChargeTimeRemaining();
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 24:
                    long _result6 = computeBatteryScreenOffRealtimeMs();
                    reply.writeNoException();
                    reply.writeLong(_result6);
                    return true;
                case 25:
                    long _result7 = getScreenOffDischargeMah();
                    reply.writeNoException();
                    reply.writeLong(_result7);
                    return true;
                case 26:
                    int _arg015 = data.readInt();
                    String _arg14 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    noteEvent(_arg015, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg016 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    noteSyncStart(_arg016, _arg15);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg017 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    noteSyncFinish(_arg017, _arg16);
                    reply.writeNoException();
                    return true;
                case 29:
                    String _arg018 = data.readString();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    noteJobStart(_arg018, _arg17);
                    reply.writeNoException();
                    return true;
                case 30:
                    String _arg019 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    noteJobFinish(_arg019, _arg18, _arg23);
                    reply.writeNoException();
                    return true;
                case 31:
                    int _arg020 = data.readInt();
                    int _arg19 = data.readInt();
                    String _arg24 = data.readString();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteStartWakelock(_arg020, _arg19, _arg24, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 32:
                    int _arg021 = data.readInt();
                    int _arg110 = data.readInt();
                    String _arg25 = data.readString();
                    String _arg32 = data.readString();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopWakelock(_arg021, _arg110, _arg25, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 33:
                    WorkSource _arg022 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg111 = data.readInt();
                    String _arg26 = data.readString();
                    String _arg33 = data.readString();
                    int _arg43 = data.readInt();
                    boolean _arg52 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteStartWakelockFromSource(_arg022, _arg111, _arg26, _arg33, _arg43, _arg52);
                    reply.writeNoException();
                    return true;
                case 34:
                    WorkSource _arg023 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg112 = data.readInt();
                    String _arg27 = data.readString();
                    String _arg34 = data.readString();
                    int _arg44 = data.readInt();
                    WorkSource _arg53 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg6 = data.readInt();
                    String _arg7 = data.readString();
                    String _arg8 = data.readString();
                    int _arg9 = data.readInt();
                    boolean _arg10 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteChangeWakelockFromSource(_arg023, _arg112, _arg27, _arg34, _arg44, _arg53, _arg6, _arg7, _arg8, _arg9, _arg10);
                    reply.writeNoException();
                    return true;
                case 35:
                    WorkSource _arg024 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg113 = data.readInt();
                    String _arg28 = data.readString();
                    String _arg35 = data.readString();
                    int _arg45 = data.readInt();
                    data.enforceNoDataAvail();
                    noteStopWakelockFromSource(_arg024, _arg113, _arg28, _arg35, _arg45);
                    reply.writeNoException();
                    return true;
                case 36:
                    String _arg025 = data.readString();
                    String _arg114 = data.readString();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    noteLongPartialWakelockStart(_arg025, _arg114, _arg29);
                    reply.writeNoException();
                    return true;
                case 37:
                    String _arg026 = data.readString();
                    String _arg115 = data.readString();
                    WorkSource _arg210 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteLongPartialWakelockStartFromSource(_arg026, _arg115, _arg210);
                    reply.writeNoException();
                    return true;
                case 38:
                    String _arg027 = data.readString();
                    String _arg116 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    noteLongPartialWakelockFinish(_arg027, _arg116, _arg211);
                    reply.writeNoException();
                    return true;
                case 39:
                    String _arg028 = data.readString();
                    String _arg117 = data.readString();
                    WorkSource _arg212 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteLongPartialWakelockFinishFromSource(_arg028, _arg117, _arg212);
                    reply.writeNoException();
                    return true;
                case 40:
                    int _arg029 = data.readInt();
                    long _arg118 = data.readLong();
                    data.enforceNoDataAvail();
                    noteVibratorOn(_arg029, _arg118);
                    reply.writeNoException();
                    return true;
                case 41:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    noteVibratorOff(_arg030);
                    reply.writeNoException();
                    return true;
                case 42:
                    WorkSource _arg031 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    WorkSource _arg119 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteGpsChanged(_arg031, _arg119);
                    reply.writeNoException();
                    return true;
                case 43:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    noteGpsSignalQuality(_arg032);
                    reply.writeNoException();
                    return true;
                case 44:
                    int _arg033 = data.readInt();
                    data.enforceNoDataAvail();
                    noteScreenState(_arg033);
                    reply.writeNoException();
                    return true;
                case 45:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    noteScreenBrightness(_arg034);
                    reply.writeNoException();
                    return true;
                case 46:
                    int _arg035 = data.readInt();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    noteUserActivity(_arg035, _arg120);
                    reply.writeNoException();
                    return true;
                case 47:
                    String _arg036 = data.readString();
                    int _arg121 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWakeUp(_arg036, _arg121);
                    reply.writeNoException();
                    return true;
                case 48:
                    boolean _arg037 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteInteractive(_arg037);
                    reply.writeNoException();
                    return true;
                case 49:
                    int _arg038 = data.readInt();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    noteConnectivityChanged(_arg038, _arg122);
                    reply.writeNoException();
                    return true;
                case 50:
                    int _arg039 = data.readInt();
                    long _arg123 = data.readLong();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    noteMobileRadioPowerState(_arg039, _arg123, _arg213);
                    reply.writeNoException();
                    return true;
                case 51:
                    notePhoneOn();
                    reply.writeNoException();
                    return true;
                case 52:
                    notePhoneOff();
                    reply.writeNoException();
                    return true;
                case 53:
                    SignalStrength _arg040 = (SignalStrength) data.readTypedObject(SignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    notePhoneSignalStrength(_arg040);
                    reply.writeNoException();
                    return true;
                case 54:
                    int _arg041 = data.readInt();
                    boolean _arg124 = data.readBoolean();
                    int _arg214 = data.readInt();
                    int _arg36 = data.readInt();
                    int _arg46 = data.readInt();
                    data.enforceNoDataAvail();
                    notePhoneDataConnectionState(_arg041, _arg124, _arg214, _arg36, _arg46);
                    reply.writeNoException();
                    return true;
                case 55:
                    int _arg042 = data.readInt();
                    data.enforceNoDataAvail();
                    notePhoneState(_arg042);
                    reply.writeNoException();
                    return true;
                case 56:
                    noteWifiOn();
                    reply.writeNoException();
                    return true;
                case 57:
                    noteWifiOff();
                    reply.writeNoException();
                    return true;
                case 58:
                    WorkSource _arg043 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiRunning(_arg043);
                    reply.writeNoException();
                    return true;
                case 59:
                    WorkSource _arg044 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    WorkSource _arg125 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiRunningChanged(_arg044, _arg125);
                    reply.writeNoException();
                    return true;
                case 60:
                    WorkSource _arg045 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiStopped(_arg045);
                    reply.writeNoException();
                    return true;
                case 61:
                    int _arg046 = data.readInt();
                    String _arg126 = data.readString();
                    data.enforceNoDataAvail();
                    noteWifiState(_arg046, _arg126);
                    reply.writeNoException();
                    return true;
                case 62:
                    int _arg047 = data.readInt();
                    boolean _arg127 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteWifiSupplicantStateChanged(_arg047, _arg127);
                    reply.writeNoException();
                    return true;
                case 63:
                    int _arg048 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiRssiChanged(_arg048);
                    reply.writeNoException();
                    return true;
                case 64:
                    int _arg049 = data.readInt();
                    data.enforceNoDataAvail();
                    noteFullWifiLockAcquired(_arg049);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _arg050 = data.readInt();
                    data.enforceNoDataAvail();
                    noteFullWifiLockReleased(_arg050);
                    reply.writeNoException();
                    return true;
                case 66:
                    int _arg051 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiScanStarted(_arg051);
                    reply.writeNoException();
                    return true;
                case 67:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiScanStopped(_arg052);
                    reply.writeNoException();
                    return true;
                case 68:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiMulticastEnabled(_arg053);
                    reply.writeNoException();
                    return true;
                case 69:
                    int _arg054 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiMulticastDisabled(_arg054);
                    reply.writeNoException();
                    return true;
                case 70:
                    WorkSource _arg055 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteFullWifiLockAcquiredFromSource(_arg055);
                    reply.writeNoException();
                    return true;
                case 71:
                    WorkSource _arg056 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteFullWifiLockReleasedFromSource(_arg056);
                    reply.writeNoException();
                    return true;
                case 72:
                    WorkSource _arg057 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiScanStartedFromSource(_arg057);
                    reply.writeNoException();
                    return true;
                case 73:
                    WorkSource _arg058 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiScanStoppedFromSource(_arg058);
                    reply.writeNoException();
                    return true;
                case 74:
                    WorkSource _arg059 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiBatchedScanStartedFromSource(_arg059, _arg128);
                    reply.writeNoException();
                    return true;
                case 75:
                    WorkSource _arg060 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiBatchedScanStoppedFromSource(_arg060);
                    reply.writeNoException();
                    return true;
                case 76:
                    int _arg061 = data.readInt();
                    long _arg129 = data.readLong();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    noteWifiRadioPowerState(_arg061, _arg129, _arg215);
                    reply.writeNoException();
                    return true;
                case 77:
                    String _arg062 = data.readString();
                    int[] _arg130 = data.createIntArray();
                    data.enforceNoDataAvail();
                    noteNetworkInterfaceForTransports(_arg062, _arg130);
                    reply.writeNoException();
                    return true;
                case 78:
                    noteNetworkStatsEnabled();
                    reply.writeNoException();
                    return true;
                case 79:
                    int _arg063 = data.readInt();
                    String _arg131 = data.readString();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    noteDeviceIdleMode(_arg063, _arg131, _arg216);
                    reply.writeNoException();
                    return true;
                case 80:
                    int _arg064 = data.readInt();
                    int _arg132 = data.readInt();
                    int _arg217 = data.readInt();
                    int _arg37 = data.readInt();
                    int _arg47 = data.readInt();
                    int _arg54 = data.readInt();
                    int _arg62 = data.readInt();
                    int _arg72 = data.readInt();
                    long _arg82 = data.readLong();
                    int _arg92 = data.readInt();
                    int _arg102 = data.readInt();
                    int _arg11 = data.readInt();
                    int _arg1210 = data.readInt();
                    boolean _arg133 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBatteryState(_arg064, _arg132, _arg217, _arg37, _arg47, _arg54, _arg62, _arg72, _arg82, _arg92, _arg102, _arg11, _arg1210, _arg133);
                    reply.writeNoException();
                    return true;
                case 81:
                    long _result8 = getAwakeTimeBattery();
                    reply.writeNoException();
                    reply.writeLong(_result8);
                    return true;
                case 82:
                    long _result9 = getAwakeTimePlugged();
                    reply.writeNoException();
                    reply.writeLong(_result9);
                    return true;
                case 83:
                    WorkSource _arg065 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    boolean _arg134 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteBleScanStarted(_arg065, _arg134);
                    reply.writeNoException();
                    return true;
                case 84:
                    WorkSource _arg066 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    boolean _arg135 = data.readBoolean();
                    data.enforceNoDataAvail();
                    noteBleScanStopped(_arg066, _arg135);
                    reply.writeNoException();
                    return true;
                case 85:
                    noteBleScanReset();
                    reply.writeNoException();
                    return true;
                case 86:
                    WorkSource _arg067 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg136 = data.readInt();
                    data.enforceNoDataAvail();
                    noteBleScanResults(_arg067, _arg136);
                    reply.writeNoException();
                    return true;
                case 87:
                    CellularBatteryStats _result10 = getCellularBatteryStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 88:
                    WifiBatteryStats _result11 = getWifiBatteryStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 89:
                    GpsBatteryStats _result12 = getGpsBatteryStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 90:
                    WakeLockStats _result13 = getWakeLockStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 91:
                    BluetoothBatteryStats _result14 = getBluetoothBatteryStats();
                    reply.writeNoException();
                    reply.writeTypedObject(_result14, 1);
                    return true;
                case 92:
                    int _arg068 = data.readInt();
                    data.enforceNoDataAvail();
                    HealthStatsParceler _result15 = takeUidSnapshot(_arg068);
                    reply.writeNoException();
                    reply.writeTypedObject(_result15, 1);
                    return true;
                case 93:
                    int[] _arg069 = data.createIntArray();
                    data.enforceNoDataAvail();
                    HealthStatsParceler[] _result16 = takeUidSnapshots(_arg069);
                    reply.writeNoException();
                    reply.writeTypedArray(_result16, 1);
                    return true;
                case 94:
                    int[] _arg070 = data.createIntArray();
                    ResultReceiver _arg137 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    takeUidSnapshotsAsync(_arg070, _arg137);
                    return true;
                case 95:
                    BluetoothActivityEnergyInfo _arg071 = (BluetoothActivityEnergyInfo) data.readTypedObject(BluetoothActivityEnergyInfo.CREATOR);
                    data.enforceNoDataAvail();
                    noteBluetoothControllerActivity(_arg071);
                    return true;
                case 96:
                    ModemActivityInfo _arg072 = (ModemActivityInfo) data.readTypedObject(ModemActivityInfo.CREATOR);
                    data.enforceNoDataAvail();
                    noteModemControllerActivity(_arg072);
                    return true;
                case 97:
                    WifiActivityEnergyInfo _arg073 = (WifiActivityEnergyInfo) data.readTypedObject(WifiActivityEnergyInfo.CREATOR);
                    data.enforceNoDataAvail();
                    noteWifiControllerActivity(_arg073);
                    return true;
                case 98:
                    int _arg074 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result17 = setChargingStateUpdateDelayMillis(_arg074);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 99:
                    boolean _arg075 = data.readBoolean();
                    boolean _arg138 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setChargerAcOnline(_arg075, _arg138);
                    reply.writeNoException();
                    return true;
                case 100:
                    int _arg076 = data.readInt();
                    boolean _arg139 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBatteryLevel(_arg076, _arg139);
                    reply.writeNoException();
                    return true;
                case 101:
                    boolean _arg077 = data.readBoolean();
                    data.enforceNoDataAvail();
                    unplugBattery(_arg077);
                    reply.writeNoException();
                    return true;
                case 102:
                    boolean _arg078 = data.readBoolean();
                    data.enforceNoDataAvail();
                    resetBattery(_arg078);
                    reply.writeNoException();
                    return true;
                case 103:
                    suspendBatteryInput();
                    reply.writeNoException();
                    return true;
                case 104:
                    int _arg079 = data.readInt();
                    int _arg140 = data.readInt();
                    int _arg218 = data.readInt();
                    int _arg38 = data.readInt();
                    int _arg48 = data.readInt();
                    data.enforceNoDataAvail();
                    setTemperatureNCurrent(_arg079, _arg140, _arg218, _arg38, _arg48);
                    return true;
                case 105:
                    SpeakerOutEnergyInfo _arg080 = (SpeakerOutEnergyInfo) data.readTypedObject(SpeakerOutEnergyInfo.CREATOR);
                    data.enforceNoDataAvail();
                    updateSpeakerOutEnergyInfo(_arg080);
                    return true;
                case 106:
                    IBatteryStatsCallback _arg081 = IBatteryStatsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result18 = registerBatteryStatsCallback(_arg081);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 107:
                    IBatteryStatsCallback _arg082 = IBatteryStatsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result19 = unregisterBatteryStatsCallback(_arg082);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 108:
                    String _arg083 = data.readString();
                    data.enforceNoDataAvail();
                    noteUpdateNetworkStats(_arg083);
                    reply.writeNoException();
                    return true;
                case 109:
                    noteStartTxPowerSharing();
                    reply.writeNoException();
                    return true;
                case 110:
                    noteStopTxPowerSharing();
                    reply.writeNoException();
                    return true;
                case 111:
                    WorkSource _arg084 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    boolean _arg141 = data.readBoolean();
                    int _arg219 = data.readInt();
                    data.enforceNoDataAvail();
                    noteBleDutyScanStarted(_arg084, _arg141, _arg219);
                    reply.writeNoException();
                    return true;
                case 112:
                    WorkSource _arg085 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    boolean _arg142 = data.readBoolean();
                    int _arg220 = data.readInt();
                    data.enforceNoDataAvail();
                    noteBleDutyScanStopped(_arg085, _arg142, _arg220);
                    reply.writeNoException();
                    return true;
                case 113:
                    int _arg086 = data.readInt();
                    int _arg143 = data.readInt();
                    int _arg221 = data.readInt();
                    data.enforceNoDataAvail();
                    noteDualScreenState(_arg086, _arg143, _arg221);
                    reply.writeNoException();
                    return true;
                case 114:
                    int _arg087 = data.readInt();
                    int _arg144 = data.readInt();
                    int _arg222 = data.readInt();
                    data.enforceNoDataAvail();
                    noteDualScreenBrightness(_arg087, _arg144, _arg222);
                    reply.writeNoException();
                    return true;
                case 115:
                    SemModemActivityInfo _arg088 = (SemModemActivityInfo) data.readTypedObject(SemModemActivityInfo.CREATOR);
                    data.enforceNoDataAvail();
                    updateSemModemActivityInfo(_arg088);
                    return true;
                case 116:
                    SemCompanionDeviceBatteryInfo[] _result20 = getDeviceBatteryInfos();
                    reply.writeNoException();
                    reply.writeTypedArray(_result20, 1);
                    return true;
                case 117:
                    String _arg089 = data.readString();
                    data.enforceNoDataAvail();
                    SemCompanionDeviceBatteryInfo _result21 = getDeviceBatteryInfo(_arg089);
                    reply.writeNoException();
                    reply.writeTypedObject(_result21, 1);
                    return true;
                case 118:
                    String _arg090 = data.readString();
                    data.enforceNoDataAvail();
                    registerDeviceBatteryInfoChanged(_arg090);
                    reply.writeNoException();
                    return true;
                case 119:
                    String _arg091 = data.readString();
                    data.enforceNoDataAvail();
                    unRegisterDeviceBatteryInfoChanged(_arg091);
                    reply.writeNoException();
                    return true;
                case 120:
                    String _arg092 = data.readString();
                    SemCompanionDeviceBatteryInfo _arg145 = (SemCompanionDeviceBatteryInfo) data.readTypedObject(SemCompanionDeviceBatteryInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setDeviceBatteryInfo(_arg092, _arg145);
                    reply.writeNoException();
                    return true;
                case 121:
                    String _arg093 = data.readString();
                    data.enforceNoDataAvail();
                    unsetDeviceBatteryInfo(_arg093);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBatteryStats {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartSensor(int uid, int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(sensor);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopSensor(int uid, int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(sensor);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartVideo(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopVideo(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartAudio(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopAudio(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetVideo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetAudio() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOn(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOff(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartCamera(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopCamera(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetCamera() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetFlashlight() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeupSensorEvent(long elapsedNanos, int uid, int handle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(elapsedNanos);
                    _data.writeInt(uid);
                    _data.writeInt(handle);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartGps(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopGps(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetGps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> queries) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(queries, 0);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    List<BatteryUsageStats> _result = _reply.createTypedArrayList(BatteryUsageStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemBatterySipper getSemBatteryUsageStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    SemBatterySipper _result = (SemBatterySipper) _reply.readTypedObject(SemBatterySipper.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean isCharging() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryTimeRemaining() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeChargeTimeRemaining() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryScreenOffRealtimeMs() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getScreenOffDischargeMah() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteEvent(int code, String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncStart(String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncFinish(String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobStart(String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobFinish(String name, int uid, int stopReason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    _data.writeInt(stopReason);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelock(int uid, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    _data.writeBoolean(unimportantForLogging);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelock(int uid, int pid, String name, String historyName, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    _data.writeBoolean(unimportantForLogging);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteChangeWakelockFromSource(WorkSource ws, int pid, String name, String histyoryName, int type, WorkSource newWs, int newPid, String newName, String newHistoryName, int newType, boolean newUnimportantForLogging) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(pid);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(name);
                    try {
                        _data.writeString(histyoryName);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(type);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(newWs, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(newPid);
                    try {
                        _data.writeString(newName);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(newHistoryName);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(newType);
                        try {
                            _data.writeBoolean(newUnimportantForLogging);
                            try {
                                this.mRemote.transact(34, _data, _reply, 0);
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th9) {
                                th = th9;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStart(String name, String historyName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(uid);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStartFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeTypedObject(workSource, 0);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinish(String name, String historyName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(uid);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinishFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeTypedObject(workSource, 0);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOn(int uid, long durationMillis) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeLong(durationMillis);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOff(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsChanged(WorkSource oldSource, WorkSource newSource) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(oldSource, 0);
                    _data.writeTypedObject(newSource, 0);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsSignalQuality(int signalLevel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(signalLevel);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenBrightness(int brightness) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightness);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteUserActivity(int uid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(event);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeUp(String reason, int reasonUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    _data.writeInt(reasonUid);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteInteractive(boolean interactive) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(interactive);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteConnectivityChanged(int type, String extra) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(extra);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteMobileRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerState);
                    _data.writeLong(timestampNs);
                    _data.writeInt(uid);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneDataConnectionState(int dataType, boolean hasData, int serviceType, int nrState, int nrFrequency) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeBoolean(hasData);
                    _data.writeInt(serviceType);
                    _data.writeInt(nrState);
                    _data.writeInt(nrFrequency);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneState(int phoneState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneState);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunning(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunningChanged(WorkSource oldWs, WorkSource newWs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(oldWs, 0);
                    _data.writeTypedObject(newWs, 0);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiStopped(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiState(int wifiState, String accessPoint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(wifiState);
                    _data.writeString(accessPoint);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiSupplicantStateChanged(int supplState, boolean failedAuth) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(supplState);
                    _data.writeBoolean(failedAuth);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRssiChanged(int newRssi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newRssi);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquired(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleased(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStarted(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStopped(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastEnabled(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastDisabled(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquiredFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleasedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStartedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStoppedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStartedFromSource(WorkSource ws, int csph) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeInt(csph);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStoppedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerState);
                    _data.writeLong(timestampNs);
                    _data.writeInt(uid);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkInterfaceForTransports(String iface, int[] transportTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeIntArray(transportTypes);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkStatsEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDeviceIdleMode(int mode, String activeReason, int activeUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(activeReason);
                    _data.writeInt(activeUid);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryState(int status, int health, int plugType, int level, int temp, int volt, int chargeUAh, int chargeFullUAh, long chargeTimeToFullSeconds, int secEvent, int secOnline, int secCurrentEvent, int secTxShareEvent, boolean otgOnline) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(health);
                    try {
                        _data.writeInt(plugType);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(level);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(temp);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(volt);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(chargeUAh);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(chargeFullUAh);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(chargeTimeToFullSeconds);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                }
                try {
                    _data.writeInt(secEvent);
                    try {
                        _data.writeInt(secOnline);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(secCurrentEvent);
                        _data.writeInt(secTxShareEvent);
                        _data.writeBoolean(otgOnline);
                        this.mRemote.transact(80, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimeBattery() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimePlugged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStarted(WorkSource ws, boolean isUnoptimized) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeBoolean(isUnoptimized);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStopped(WorkSource ws, boolean isUnoptimized) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeBoolean(isUnoptimized);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanReset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanResults(WorkSource ws, int numNewResults) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeInt(numNewResults);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    CellularBatteryStats _result = (CellularBatteryStats) _reply.readTypedObject(CellularBatteryStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    WifiBatteryStats _result = (WifiBatteryStats) _reply.readTypedObject(WifiBatteryStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    GpsBatteryStats _result = (GpsBatteryStats) _reply.readTypedObject(GpsBatteryStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public WakeLockStats getWakeLockStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    WakeLockStats _result = (WakeLockStats) _reply.readTypedObject(WakeLockStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public BluetoothBatteryStats getBluetoothBatteryStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    BluetoothBatteryStats _result = (BluetoothBatteryStats) _reply.readTypedObject(BluetoothBatteryStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public HealthStatsParceler takeUidSnapshot(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    HealthStatsParceler _result = (HealthStatsParceler) _reply.readTypedObject(HealthStatsParceler.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public HealthStatsParceler[] takeUidSnapshots(int[] uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uid);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                    HealthStatsParceler[] _result = (HealthStatsParceler[]) _reply.createTypedArray(HealthStatsParceler.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void takeUidSnapshotsAsync(int[] uid, ResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uid);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(94, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(95, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteModemControllerActivity(ModemActivityInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(96, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiControllerActivity(WifiActivityEnergyInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(97, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean setChargingStateUpdateDelayMillis(int delay) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(delay);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setChargerAcOnline(boolean online, boolean forceUpdate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(online);
                    _data.writeBoolean(forceUpdate);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryLevel(int level, boolean forceUpdate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    _data.writeBoolean(forceUpdate);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unplugBattery(boolean forceUpdate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(forceUpdate);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void resetBattery(boolean forceUpdate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(forceUpdate);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void suspendBatteryInput() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setTemperatureNCurrent(int ap_temp, int pa_temp, int skin_temp, int sub_batt_temp, int current) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ap_temp);
                    _data.writeInt(pa_temp);
                    _data.writeInt(skin_temp);
                    _data.writeInt(sub_batt_temp);
                    _data.writeInt(current);
                    this.mRemote.transact(104, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(105, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean registerBatteryStatsCallback(IBatteryStatsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean unregisterBatteryStatsCallback(IBatteryStatsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteUpdateNetworkStats(String caller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(caller);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartTxPowerSharing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopTxPowerSharing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleDutyScanStarted(WorkSource ws, boolean isUnoptimized, int dutyCycle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeBoolean(isUnoptimized);
                    _data.writeInt(dutyCycle);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleDutyScanStopped(WorkSource ws, boolean isUnoptimized, int dutyCycle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ws, 0);
                    _data.writeBoolean(isUnoptimized);
                    _data.writeInt(dutyCycle);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDualScreenState(int state, int displayId, int screenPolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(displayId);
                    _data.writeInt(screenPolicy);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDualScreenBrightness(int brightness, int displayId, int screenPolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightness);
                    _data.writeInt(displayId);
                    _data.writeInt(screenPolicy);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void updateSemModemActivityInfo(SemModemActivityInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(115, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    SemCompanionDeviceBatteryInfo[] _result = (SemCompanionDeviceBatteryInfo[]) _reply.createTypedArray(SemCompanionDeviceBatteryInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                    SemCompanionDeviceBatteryInfo _result = (SemCompanionDeviceBatteryInfo) _reply.readTypedObject(SemCompanionDeviceBatteryInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void registerDeviceBatteryInfoChanged(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unRegisterDeviceBatteryInfoChanged(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setDeviceBatteryInfo(String address, SemCompanionDeviceBatteryInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unsetDeviceBatteryInfo(String address) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void noteStartSensor_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopSensor_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartVideo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopVideo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartAudio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopAudio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetVideo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetAudio_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFlashlightOn_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFlashlightOff_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartCamera_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopCamera_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetCamera_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetFlashlight_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartGps_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopGps_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteResetGps_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBatteryUsageStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getSemBatteryUsageStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void computeBatteryScreenOffRealtimeMs_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getScreenOffDischargeMah_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteSyncStart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteSyncFinish_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteJobStart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteJobFinish_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartWakelock_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopWakelock_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartWakelockFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteChangeWakelockFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopWakelockFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockStart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockStartFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockFinish_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteLongPartialWakelockFinishFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteVibratorOn_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteVibratorOff_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteGpsChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteGpsSignalQuality_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteScreenState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteScreenBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteUserActivity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWakeUp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteInteractive_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteConnectivityChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteMobileRadioPowerState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneOn_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneOff_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneSignalStrength_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneDataConnectionState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void notePhoneState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiOn_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiOff_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRunning_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRunningChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiStopped_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiSupplicantStateChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRssiChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockAcquired_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockReleased_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStarted_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStopped_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiMulticastEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiMulticastDisabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockAcquiredFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteFullWifiLockReleasedFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStartedFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiScanStoppedFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiBatchedScanStartedFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiBatchedScanStoppedFromSource_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiRadioPowerState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteNetworkInterfaceForTransports_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_noteNetworkInterfaceForTransports, getCallingPid(), getCallingUid());
        }

        protected void noteNetworkStatsEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteDeviceIdleMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void setBatteryState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getAwakeTimeBattery_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getAwakeTimePlugged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanStarted_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanStopped_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanReset_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleScanResults_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getCellularBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getCellularBatteryStats, getCallingPid(), getCallingUid());
        }

        protected void getWifiBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getWifiBatteryStats, getCallingPid(), getCallingUid());
        }

        protected void getGpsBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getWakeLockStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBluetoothControllerActivity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteModemControllerActivity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteWifiControllerActivity_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void setChargingStateUpdateDelayMillis_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.POWER_SAVER, getCallingPid(), getCallingUid());
        }

        protected void setChargerAcOnline_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void setBatteryLevel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void unplugBattery_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void resetBattery_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void suspendBatteryInput_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }

        protected void setTemperatureNCurrent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateSpeakerOutEnergyInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void registerBatteryStatsCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void unregisterBatteryStatsCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteUpdateNetworkStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStartTxPowerSharing_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteStopTxPowerSharing_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleDutyScanStarted_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteBleDutyScanStopped_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteDualScreenState_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void noteDualScreenBrightness_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateSemModemActivityInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_DEVICE_STATS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 120;
        }
    }
}
