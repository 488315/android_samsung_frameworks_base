package android.os;

import android.annotation.SystemApi;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.WifiBatteryStats;
import com.android.internal.app.IBatteryStats;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class BatteryStatsManager {
    public static final int NUM_WIFI_STATES = 8;
    public static final int NUM_WIFI_SUPPL_STATES = 13;
    public static final int WIFI_STATE_OFF = 0;
    public static final int WIFI_STATE_OFF_SCANNING = 1;
    public static final int WIFI_STATE_ON_CONNECTED_P2P = 5;
    public static final int WIFI_STATE_ON_CONNECTED_STA = 4;
    public static final int WIFI_STATE_ON_CONNECTED_STA_P2P = 6;
    public static final int WIFI_STATE_ON_DISCONNECTED = 3;
    public static final int WIFI_STATE_ON_NO_NETWORKS = 2;
    public static final int WIFI_STATE_SOFT_AP = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATED = 7;
    public static final int WIFI_SUPPL_STATE_ASSOCIATING = 6;
    public static final int WIFI_SUPPL_STATE_AUTHENTICATING = 5;
    public static final int WIFI_SUPPL_STATE_COMPLETED = 10;
    public static final int WIFI_SUPPL_STATE_DISCONNECTED = 1;
    public static final int WIFI_SUPPL_STATE_DORMANT = 11;
    public static final int WIFI_SUPPL_STATE_FOUR_WAY_HANDSHAKE = 8;
    public static final int WIFI_SUPPL_STATE_GROUP_HANDSHAKE = 9;
    public static final int WIFI_SUPPL_STATE_INACTIVE = 3;
    public static final int WIFI_SUPPL_STATE_INTERFACE_DISABLED = 2;
    public static final int WIFI_SUPPL_STATE_INVALID = 0;
    public static final int WIFI_SUPPL_STATE_SCANNING = 4;
    public static final int WIFI_SUPPL_STATE_UNINITIALIZED = 12;
    private final IBatteryStats mBatteryStats;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiSupplState {
    }

    private static int getDataConnectionPowerState(boolean z) {
        return z ? 3 : 1;
    }

    @Deprecated
    public void reportBluetoothOff(int i, int i2, String str) {
    }

    @Deprecated
    public void reportBluetoothOn(int i, int i2, String str) {
    }

    public BatteryStatsManager(IBatteryStats iBatteryStats) {
        this.mBatteryStats = iBatteryStats;
    }

    public BatteryUsageStats getBatteryUsageStats() {
        return getBatteryUsageStats(BatteryUsageStatsQuery.DEFAULT);
    }

    public BatteryUsageStats getBatteryUsageStats(BatteryUsageStatsQuery batteryUsageStatsQuery) {
        return getBatteryUsageStats(List.of(batteryUsageStatsQuery)).get(0);
    }

    public List<BatteryUsageStats> getBatteryUsageStats(List<BatteryUsageStatsQuery> list) {
        try {
            return this.mBatteryStats.getBatteryUsageStats(list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportWifiRssiChanged(int i) {
        try {
            this.mBatteryStats.noteWifiRssiChanged(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiOn() {
        try {
            this.mBatteryStats.noteWifiOn();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiOff() {
        try {
            this.mBatteryStats.noteWifiOff();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiState(int i, String str) {
        try {
            this.mBatteryStats.noteWifiState(i, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiScanStartedFromSource(WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiScanStartedFromSource(workSource);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiScanStoppedFromSource(WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiScanStoppedFromSource(workSource);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiBatchedScanStartedFromSource(WorkSource workSource, int i) {
        try {
            this.mBatteryStats.noteWifiBatchedScanStartedFromSource(workSource, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiBatchedScanStoppedFromSource(WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiBatchedScanStoppedFromSource(workSource);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public CellularBatteryStats getCellularBatteryStats() {
        try {
            return this.mBatteryStats.getCellularBatteryStats();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public WifiBatteryStats getWifiBatteryStats() {
        try {
            return this.mBatteryStats.getWifiBatteryStats();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public WakeLockStats getWakeLockStats() {
        try {
            return this.mBatteryStats.getWakeLockStats();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BluetoothBatteryStats getBluetoothBatteryStats() {
        try {
            return this.mBatteryStats.getBluetoothBatteryStats();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportFullWifiLockAcquiredFromSource(WorkSource workSource) {
        try {
            this.mBatteryStats.noteFullWifiLockAcquiredFromSource(workSource);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportFullWifiLockReleasedFromSource(WorkSource workSource) {
        try {
            this.mBatteryStats.noteFullWifiLockReleasedFromSource(workSource);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiSupplicantStateChanged(int i, boolean z) {
        try {
            this.mBatteryStats.noteWifiSupplicantStateChanged(i, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiMulticastEnabled(WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiMulticastEnabled(workSource.getAttributionUid());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiMulticastDisabled(WorkSource workSource) {
        try {
            this.mBatteryStats.noteWifiMulticastDisabled(workSource.getAttributionUid());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportMobileRadioPowerState(boolean z, int i) {
        try {
            this.mBatteryStats.noteMobileRadioPowerState(getDataConnectionPowerState(z), SystemClock.elapsedRealtimeNanos(), i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportWifiRadioPowerState(boolean z, int i) {
        try {
            this.mBatteryStats.noteWifiRadioPowerState(getDataConnectionPowerState(z), SystemClock.elapsedRealtimeNanos(), i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void reportNetworkInterfaceForTransports(String str, int[] iArr) throws RuntimeException {
        try {
            this.mBatteryStats.noteNetworkInterfaceForTransports(str, iArr);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanStarted(WorkSource workSource, boolean z) {
        try {
            this.mBatteryStats.noteBleScanStarted(workSource, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanStopped(WorkSource workSource, boolean z) {
        try {
            this.mBatteryStats.noteBleScanStopped(workSource, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanReset() {
        try {
            this.mBatteryStats.noteBleScanReset();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleScanResults(WorkSource workSource, int i) {
        try {
            this.mBatteryStats.noteBleScanResults(workSource, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setChargerAcOnline(boolean z, boolean z2) {
        try {
            this.mBatteryStats.setChargerAcOnline(z, z2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setBatteryLevel(int i, boolean z) {
        try {
            this.mBatteryStats.setBatteryLevel(i, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unplugBattery(boolean z) {
        try {
            this.mBatteryStats.unplugBattery(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void resetBattery(boolean z) {
        try {
            this.mBatteryStats.resetBattery(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void suspendBatteryInput() {
        try {
            this.mBatteryStats.suspendBatteryInput();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleDutyScanStarted(WorkSource workSource, boolean z, int i) {
        try {
            this.mBatteryStats.noteBleDutyScanStarted(workSource, z, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void reportBleDutyScanStopped(WorkSource workSource, boolean z, int i) {
        try {
            this.mBatteryStats.noteBleDutyScanStopped(workSource, z, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
