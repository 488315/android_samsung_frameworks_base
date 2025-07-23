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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IBatteryStats extends IInterface {
    public static final String KEY_EXCEPTION_MESSAGE = "exception";
    public static final String KEY_UID_SNAPSHOTS = "uid_snapshots";
    public static final int RESULT_OK = 0;
    public static final int RESULT_RUNTIME_EXCEPTION = 1;
    public static final int RESULT_SECURITY_EXCEPTION = 2;

    public static class Default implements IBatteryStats {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long computeBatteryScreenOffRealtimeMs() throws RemoteException {
            return 0L;
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
        public long getAwakeTimeBattery() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getAwakeTimePlugged() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public BluetoothBatteryStats getBluetoothBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public long getScreenOffDischargeMah() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.IBatteryStats
        public SemBatterySipper getSemBatteryUsageStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public WakeLockStats getWakeLockStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean isCharging() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleDutyScanStarted(WorkSource workSource, boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleDutyScanStopped(WorkSource workSource, boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanReset() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanResults(WorkSource workSource, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStarted(WorkSource workSource, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBleScanStopped(WorkSource workSource, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteChangeWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, WorkSource workSource2, int i3, String str3, String str4, int i4, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteConnectivityChanged(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDeviceIdleMode(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDualScreenBrightness(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteDualScreenState(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteEvent(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOff(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFlashlightOn(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquired(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleased(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteGpsSignalQuality(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteInteractive(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobFinish(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteJobStart(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinish(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockFinishFromSource(String str, String str2, WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStart(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteLongPartialWakelockStartFromSource(String str, String str2, WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteMobileRadioPowerState(int i, long j, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkInterfaceForTransports(String str, int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteNetworkStatsEnabled() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOff() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneOn() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void notePhoneState(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetAudio() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetCamera() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetFlashlight() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetGps() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteResetVideo() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenBrightness(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteScreenState(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartAudio(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartCamera(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartGps(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartSensor(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartTxPowerSharing() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartVideo(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelock(int i, int i2, String str, String str2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStartWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopAudio(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopCamera(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopGps(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopSensor(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopTxPowerSharing() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopVideo(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelock(int i, int i2, String str, String str2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteStopWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncFinish(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteSyncStart(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteUpdateNetworkStats(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteUserActivity(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOff(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteVibratorOn(int i, long j) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeUp(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWakeupSensorEvent(long j, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastDisabled(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiMulticastEnabled(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOff() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiOn() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRadioPowerState(int i, long j, int i2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRssiChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunning(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStarted(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStopped(int i) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiState(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiStopped(WorkSource workSource) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void noteWifiSupplicantStateChanged(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void registerDeviceBatteryInfoChanged(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void resetBattery(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryLevel(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, int i9, int i10, int i11, int i12, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setChargerAcOnline(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public boolean setChargingStateUpdateDelayMillis(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setDeviceBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void setTemperatureNCurrent(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void suspendBatteryInput() throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public HealthStatsParceler takeUidSnapshot(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IBatteryStats
        public void takeUidSnapshotsAsync(int[] iArr, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unRegisterDeviceBatteryInfoChanged(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unplugBattery(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unregisterBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void unsetDeviceBatteryInfo(String str) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void updateSemModemActivityInfo(SemModemActivityInfo semModemActivityInfo) throws RemoteException {
        }

        @Override // com.android.internal.app.IBatteryStats
        public void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo speakerOutEnergyInfo) throws RemoteException {
        }
    }

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

    void noteDualScreenState(int i, int i2, int i3, int i4) throws RemoteException;

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

    void noteScreenBrightness(int i, int i2) throws RemoteException;

    void noteScreenState(int i, int i2, int i3) throws RemoteException;

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

    void registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException;

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

    void unregisterBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException;

    void unsetDeviceBatteryInfo(String str) throws RemoteException;

    void updateSemModemActivityInfo(SemModemActivityInfo semModemActivityInfo) throws RemoteException;

    void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo speakerOutEnergyInfo) throws RemoteException;

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 120;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IBatteryStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBatteryStats)) {
                return (IBatteryStats) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartSensor(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopSensor(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartVideo(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopVideo(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartAudio(readInt7);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopAudio(readInt8);
                    return true;
                case 7:
                    noteResetVideo();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    noteResetAudio();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFlashlightOn(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFlashlightOff(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartCamera(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopCamera(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    noteResetCamera();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    noteResetFlashlight();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong = parcel.readLong();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWakeupSensorEvent(readLong, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStartGps(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopGps(readInt16);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    noteResetGps();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(BatteryUsageStatsQuery.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BatteryUsageStats> batteryUsageStats = getBatteryUsageStats(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryUsageStats, 1);
                    return true;
                case 20:
                    SemBatterySipper semBatteryUsageStats = getSemBatteryUsageStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semBatteryUsageStats, 1);
                    return true;
                case 21:
                    boolean isCharging = isCharging();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCharging);
                    return true;
                case 22:
                    long computeBatteryTimeRemaining = computeBatteryTimeRemaining();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeBatteryTimeRemaining);
                    return true;
                case 23:
                    long computeChargeTimeRemaining = computeChargeTimeRemaining();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeChargeTimeRemaining);
                    return true;
                case 24:
                    long computeBatteryScreenOffRealtimeMs = computeBatteryScreenOffRealtimeMs();
                    parcel2.writeNoException();
                    parcel2.writeLong(computeBatteryScreenOffRealtimeMs);
                    return true;
                case 25:
                    long screenOffDischargeMah = getScreenOffDischargeMah();
                    parcel2.writeNoException();
                    parcel2.writeLong(screenOffDischargeMah);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteEvent(readInt17, readString, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString2 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteSyncStart(readString2, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString3 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteSyncFinish(readString3, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString4 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteJobStart(readString4, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString5 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteJobFinish(readString5, readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteStartWakelock(readInt24, readInt25, readString6, readString7, readInt26, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopWakelock(readInt27, readInt28, readString8, readString9, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt30 = parcel.readInt();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteStartWakelockFromSource(workSource, readInt30, readString10, readString11, readInt31, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    WorkSource workSource2 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt32 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    WorkSource workSource3 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt34 = parcel.readInt();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteChangeWakelockFromSource(workSource2, readInt32, readString12, readString13, readInt33, workSource3, readInt34, readString14, readString15, readInt35, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    WorkSource workSource4 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt36 = parcel.readInt();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteStopWakelockFromSource(workSource4, readInt36, readString16, readString17, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockStart(readString18, readString19, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    WorkSource workSource5 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockStartFromSource(readString20, readString21, workSource5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockFinish(readString22, readString23, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    WorkSource workSource6 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteLongPartialWakelockFinishFromSource(readString24, readString25, workSource6);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt40 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    noteVibratorOn(readInt40, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteVibratorOff(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    WorkSource workSource7 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    WorkSource workSource8 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteGpsChanged(workSource7, workSource8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteGpsSignalQuality(readInt42);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteScreenState(readInt43, readInt44, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteScreenBrightness(readInt46, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteUserActivity(readInt48, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString26 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWakeUp(readString26, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteInteractive(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt51 = parcel.readInt();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteConnectivityChanged(readInt51, readString27);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt52 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteMobileRadioPowerState(readInt52, readLong3, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    notePhoneOn();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    notePhoneOff();
                    parcel2.writeNoException();
                    return true;
                case 53:
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notePhoneSignalStrength(signalStrength);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int readInt54 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notePhoneDataConnectionState(readInt54, readBoolean5, readInt55, readInt56, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notePhoneState(readInt58);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    noteWifiOn();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    noteWifiOff();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    WorkSource workSource9 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiRunning(workSource9);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    WorkSource workSource10 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    WorkSource workSource11 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiRunningChanged(workSource10, workSource11);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    WorkSource workSource12 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiStopped(workSource12);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt59 = parcel.readInt();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteWifiState(readInt59, readString28);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt60 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteWifiSupplicantStateChanged(readInt60, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiRssiChanged(readInt61);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockAcquired(readInt62);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockReleased(readInt63);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiScanStarted(readInt64);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiScanStopped(readInt65);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiMulticastEnabled(readInt66);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiMulticastDisabled(readInt67);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    WorkSource workSource13 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockAcquiredFromSource(workSource13);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    WorkSource workSource14 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteFullWifiLockReleasedFromSource(workSource14);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    WorkSource workSource15 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiScanStartedFromSource(workSource15);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    WorkSource workSource16 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiScanStoppedFromSource(workSource16);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    WorkSource workSource17 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiBatchedScanStartedFromSource(workSource17, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    WorkSource workSource18 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiBatchedScanStoppedFromSource(workSource18);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt69 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteWifiRadioPowerState(readInt69, readLong4, readInt70);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    String readString29 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    noteNetworkInterfaceForTransports(readString29, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    noteNetworkStatsEnabled();
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt71 = parcel.readInt();
                    String readString30 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteDeviceIdleMode(readInt71, readString30, readInt72);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    int readInt81 = parcel.readInt();
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryState(readInt73, readInt74, readInt75, readInt76, readInt77, readInt78, readInt79, readInt80, readLong5, readInt81, readInt82, readInt83, readInt84, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    long awakeTimeBattery = getAwakeTimeBattery();
                    parcel2.writeNoException();
                    parcel2.writeLong(awakeTimeBattery);
                    return true;
                case 82:
                    long awakeTimePlugged = getAwakeTimePlugged();
                    parcel2.writeNoException();
                    parcel2.writeLong(awakeTimePlugged);
                    return true;
                case 83:
                    WorkSource workSource19 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteBleScanStarted(workSource19, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    WorkSource workSource20 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    noteBleScanStopped(workSource20, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    noteBleScanReset();
                    parcel2.writeNoException();
                    return true;
                case 86:
                    WorkSource workSource21 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteBleScanResults(workSource21, readInt85);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    CellularBatteryStats cellularBatteryStats = getCellularBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellularBatteryStats, 1);
                    return true;
                case 88:
                    WifiBatteryStats wifiBatteryStats = getWifiBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiBatteryStats, 1);
                    return true;
                case 89:
                    GpsBatteryStats gpsBatteryStats = getGpsBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gpsBatteryStats, 1);
                    return true;
                case 90:
                    WakeLockStats wakeLockStats = getWakeLockStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wakeLockStats, 1);
                    return true;
                case 91:
                    BluetoothBatteryStats bluetoothBatteryStats = getBluetoothBatteryStats();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bluetoothBatteryStats, 1);
                    return true;
                case 92:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    HealthStatsParceler takeUidSnapshot = takeUidSnapshot(readInt86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeUidSnapshot, 1);
                    return true;
                case 93:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    HealthStatsParceler[] takeUidSnapshots = takeUidSnapshots(createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(takeUidSnapshots, 1);
                    return true;
                case 94:
                    int[] createIntArray3 = parcel.createIntArray();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    takeUidSnapshotsAsync(createIntArray3, resultReceiver);
                    return true;
                case 95:
                    BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo = (BluetoothActivityEnergyInfo) parcel.readTypedObject(BluetoothActivityEnergyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteBluetoothControllerActivity(bluetoothActivityEnergyInfo);
                    return true;
                case 96:
                    ModemActivityInfo modemActivityInfo = (ModemActivityInfo) parcel.readTypedObject(ModemActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteModemControllerActivity(modemActivityInfo);
                    return true;
                case 97:
                    WifiActivityEnergyInfo wifiActivityEnergyInfo = (WifiActivityEnergyInfo) parcel.readTypedObject(WifiActivityEnergyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    noteWifiControllerActivity(wifiActivityEnergyInfo);
                    return true;
                case 98:
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean chargingStateUpdateDelayMillis = setChargingStateUpdateDelayMillis(readInt87);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(chargingStateUpdateDelayMillis);
                    return true;
                case 99:
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setChargerAcOnline(readBoolean10, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    int readInt88 = parcel.readInt();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryLevel(readInt88, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    unplugBattery(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    resetBattery(readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    suspendBatteryInput();
                    parcel2.writeNoException();
                    return true;
                case 104:
                    int readInt89 = parcel.readInt();
                    int readInt90 = parcel.readInt();
                    int readInt91 = parcel.readInt();
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemperatureNCurrent(readInt89, readInt90, readInt91, readInt92, readInt93);
                    return true;
                case 105:
                    SpeakerOutEnergyInfo speakerOutEnergyInfo = (SpeakerOutEnergyInfo) parcel.readTypedObject(SpeakerOutEnergyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSpeakerOutEnergyInfo(speakerOutEnergyInfo);
                    return true;
                case 106:
                    IBatteryStatsCallback asInterface = IBatteryStatsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBatteryStatsCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    IBatteryStatsCallback asInterface2 = IBatteryStatsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterBatteryStatsCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteUpdateNetworkStats(readString31);
                    parcel2.writeNoException();
                    return true;
                case 109:
                    noteStartTxPowerSharing();
                    parcel2.writeNoException();
                    return true;
                case 110:
                    noteStopTxPowerSharing();
                    parcel2.writeNoException();
                    return true;
                case 111:
                    WorkSource workSource22 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteBleDutyScanStarted(workSource22, readBoolean15, readInt94);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    WorkSource workSource23 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteBleDutyScanStopped(workSource23, readBoolean16, readInt95);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    int readInt96 = parcel.readInt();
                    int readInt97 = parcel.readInt();
                    int readInt98 = parcel.readInt();
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteDualScreenState(readInt96, readInt97, readInt98, readInt99);
                    parcel2.writeNoException();
                    return true;
                case 114:
                    int readInt100 = parcel.readInt();
                    int readInt101 = parcel.readInt();
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteDualScreenBrightness(readInt100, readInt101, readInt102);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    SemModemActivityInfo semModemActivityInfo = (SemModemActivityInfo) parcel.readTypedObject(SemModemActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSemModemActivityInfo(semModemActivityInfo);
                    return true;
                case 116:
                    SemCompanionDeviceBatteryInfo[] deviceBatteryInfos = getDeviceBatteryInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(deviceBatteryInfos, 1);
                    return true;
                case 117:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemCompanionDeviceBatteryInfo deviceBatteryInfo = getDeviceBatteryInfo(readString32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceBatteryInfo, 1);
                    return true;
                case 118:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerDeviceBatteryInfoChanged(readString33);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unRegisterDeviceBatteryInfoChanged(readString34);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    String readString35 = parcel.readString();
                    SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = (SemCompanionDeviceBatteryInfo) parcel.readTypedObject(SemCompanionDeviceBatteryInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceBatteryInfo(readString35, semCompanionDeviceBatteryInfo);
                    parcel2.writeNoException();
                    return true;
                case 121:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsetDeviceBatteryInfo(readString36);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBatteryStats {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartSensor(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopSensor(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartVideo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopVideo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartAudio(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopAudio(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetVideo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetAudio() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOn(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFlashlightOff(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartCamera(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopCamera(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetCamera() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetFlashlight() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeupSensorEvent(long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartGps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopGps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteResetGps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BatteryUsageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemBatterySipper getSemBatteryUsageStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemBatterySipper) obtain2.readTypedObject(SemBatterySipper.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean isCharging() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryTimeRemaining() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeChargeTimeRemaining() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long computeBatteryScreenOffRealtimeMs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getScreenOffDischargeMah() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteEvent(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncStart(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteSyncFinish(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobStart(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteJobFinish(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelock(int i, int i2, String str, String str2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelock(int i, int i2, String str, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteChangeWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, WorkSource workSource2, int i3, String str3, String str4, int i4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(workSource2, 0);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStart(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockStartFromSource(String str, String str2, WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinish(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteLongPartialWakelockFinishFromSource(String str, String str2, WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOn(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteVibratorOff(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(workSource2, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteGpsSignalQuality(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenState(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteScreenBrightness(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteUserActivity(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWakeUp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteInteractive(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteConnectivityChanged(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteMobileRadioPowerState(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneDataConnectionState(int i, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void notePhoneState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunning(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeTypedObject(workSource2, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiStopped(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiState(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiSupplicantStateChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRssiChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquired(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleased(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStarted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStopped(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiMulticastDisabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiRadioPowerState(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkInterfaceForTransports(String str, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteNetworkStatsEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDeviceIdleMode(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, int i9, int i10, int i11, int i12, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    obtain.writeLong(j);
                    obtain.writeInt(i9);
                    obtain.writeInt(i10);
                    obtain.writeInt(i11);
                    obtain.writeInt(i12);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimeBattery() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public long getAwakeTimePlugged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStarted(WorkSource workSource, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanStopped(WorkSource workSource, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanReset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleScanResults(WorkSource workSource, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CellularBatteryStats) obtain2.readTypedObject(CellularBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WifiBatteryStats) obtain2.readTypedObject(WifiBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return (GpsBatteryStats) obtain2.readTypedObject(GpsBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public WakeLockStats getWakeLockStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WakeLockStats) obtain2.readTypedObject(WakeLockStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public BluetoothBatteryStats getBluetoothBatteryStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BluetoothBatteryStats) obtain2.readTypedObject(BluetoothBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public HealthStatsParceler takeUidSnapshot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HealthStatsParceler) obtain2.readTypedObject(HealthStatsParceler.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HealthStatsParceler[]) obtain2.createTypedArray(HealthStatsParceler.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void takeUidSnapshotsAsync(int[] iArr, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(94, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothActivityEnergyInfo, 0);
                    this.mRemote.transact(95, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(modemActivityInfo, 0);
                    this.mRemote.transact(96, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(wifiActivityEnergyInfo, 0);
                    this.mRemote.transact(97, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public boolean setChargingStateUpdateDelayMillis(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setChargerAcOnline(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setBatteryLevel(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unplugBattery(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void resetBattery(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void suspendBatteryInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setTemperatureNCurrent(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(104, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo speakerOutEnergyInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(speakerOutEnergyInfo, 0);
                    this.mRemote.transact(105, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBatteryStatsCallback);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unregisterBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBatteryStatsCallback);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteUpdateNetworkStats(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStartTxPowerSharing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteStopTxPowerSharing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleDutyScanStarted(WorkSource workSource, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteBleDutyScanStopped(WorkSource workSource, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDualScreenState(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void noteDualScreenBrightness(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void updateSemModemActivityInfo(SemModemActivityInfo semModemActivityInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(semModemActivityInfo, 0);
                    this.mRemote.transact(115, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCompanionDeviceBatteryInfo[]) obtain2.createTypedArray(SemCompanionDeviceBatteryInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCompanionDeviceBatteryInfo) obtain2.readTypedObject(SemCompanionDeviceBatteryInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void registerDeviceBatteryInfoChanged(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unRegisterDeviceBatteryInfoChanged(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void setDeviceBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(semCompanionDeviceBatteryInfo, 0);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IBatteryStats
            public void unsetDeviceBatteryInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
    }
}
