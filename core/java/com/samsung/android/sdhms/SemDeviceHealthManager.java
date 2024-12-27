package com.samsung.android.sdhms;

import android.app.PendingIntent;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.util.Collections;
import java.util.List;
import vendor.samsung.hardware.thermal.V1_0.SehTempStatus;

/* loaded from: classes6.dex */
public class SemDeviceHealthManager {
    public static final String ACTION_THERMAL_THROTTLING_DELTA_CHANGED = "com.sec.android.sdhms.action.THERMAL_THROTTLING_DELTA_CHANGED";
    public static final int DRAIN_TYPE_AMBIENT_DISPLAY = 3;
    public static final int DRAIN_TYPE_BLUETOOTH = 6;
    public static final int DRAIN_TYPE_CELL_STANDBY = 4;
    public static final int DRAIN_TYPE_IDLE = 7;
    public static final int DRAIN_TYPE_PHONE = 1;
    public static final int DRAIN_TYPE_POWERSHARING = 8;
    public static final int DRAIN_TYPE_SCREEN = 2;
    public static final int DRAIN_TYPE_WIFI = 5;
    public static final String EXTRA_ANOMALY_TYPE_APP_ERROR = "AERR";
    public static final String EXTRA_ANOMALY_TYPE_BG_CAMERA = "CAM_28";
    public static final String EXTRA_ANOMALY_TYPE_BG_CPU = "CPU_27";
    public static final String EXTRA_ANOMALY_TYPE_BG_MOBILE = "MOB_16";
    public static final String EXTRA_ANOMALY_TYPE_BG_MOBILE_WAKEUP = "MWUP_16";
    public static final String EXTRA_ANOMALY_TYPE_CPU_KILL = "KILL_27";
    public static final String EXTRA_ANOMALY_TYPE_WAKELOCK = "WLOCK_3009";
    public static final String EXTRA_THROTTLING_DELTA = "delta";
    public static final String EXTRA_THROTTLING_TIME = "time";
    public static final int INTERVAL_TYPE_DAILY = 1;
    public static final int INTERVAL_TYPE_PERIODICALLY = 0;
    private ISamsungDeviceHealthManager mService;

    public List<SemBatteryStats> getBatteryStats(int intervalType, long startTimestamp, long endTimestamp, boolean includeDetails) {
        if (startTimestamp > endTimestamp) {
            return Collections.emptyList();
        }
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            List<SemBatteryStats> result = service.getBatteryStats(intervalType, startTimestamp, endTimestamp, includeDetails);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SemBatteryEventHistory> getBatteryEventHistory(long startTimestamp, long endTimestamp, int historyTypes) {
        if (startTimestamp > endTimestamp) {
            return Collections.emptyList();
        }
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            List<SemBatteryEventHistory> result = service.getBatteryEventHistory(startTimestamp, endTimestamp, historyTypes);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSupportedHistoryTypes() {
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getSupportedHistoryTypes();
            } catch (RemoteException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    public List<SemThermalStats> getThermalStats(long startTimestamp, long endTimestamp) {
        if (startTimestamp > endTimestamp) {
            return Collections.emptyList();
        }
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            List<SemThermalStats> result = service.getThermalStats(startTimestamp, endTimestamp);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SemProcessUsageStats> getProcessUsageStats(long startTimestamp, long endTimestamp) {
        if (startTimestamp > endTimestamp) {
            return Collections.emptyList();
        }
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            List<SemProcessUsageStats> result = service.getProcessUsageStats(startTimestamp, endTimestamp);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SemNetworkUsageStats> getNetworkUsageStats(long startTimestamp, long endTimestamp) {
        if (startTimestamp > endTimestamp) {
            return Collections.emptyList();
        }
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            List<SemNetworkUsageStats> result = service.getNetworkUsageStats(startTimestamp, endTimestamp);
            return result;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setThermalThrottlingDelta(Context context, int value) {
        ISamsungDeviceHealthManager service;
        if (context == null || (service = getService()) == null) {
            return false;
        }
        try {
            String pkgName = context.getPackageName();
            return service.setThermalThrottlingDeltaWithPackageName(pkgName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getThermalThrottlingDelta() {
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getThermalThrottlingDelta();
            } catch (RemoteException e) {
                e.printStackTrace();
                return SehTempStatus.NOT_READABLE;
            }
        }
        return -999;
    }

    public int getSupportedThermalThrottlingDelta() {
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getSupportedThermalThrottlingDelta();
            } catch (RemoteException e) {
                e.printStackTrace();
                return SehTempStatus.NOT_READABLE;
            }
        }
        return -999;
    }

    public boolean setAnomalyConfig(PendingIntent pendingIntent) {
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.setAnomalyConfig(pendingIntent);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private synchronized ISamsungDeviceHealthManager getService() {
        IBinder binder;
        if (this.mService == null && (binder = ServiceManager.getService("sdhms")) != null) {
            this.mService = ISamsungDeviceHealthManager.Stub.asInterface(binder);
            if (this.mService != null) {
                try {
                    binder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.sdhms.SemDeviceHealthManager.1
                        @Override // android.os.IBinder.DeathRecipient
                        public void binderDied() {
                            SemDeviceHealthManager.this.mService = null;
                        }
                    }, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.mService;
    }
}
