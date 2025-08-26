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

    public List<SemBatteryStats> getBatteryStats(int i, long j, long j2, boolean z) {
        if (j > j2) {
            return Collections.EMPTY_LIST;
        }
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getBatteryStats(i, j, j2, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i) {
        if (j > j2) {
            return Collections.EMPTY_LIST;
        }
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getBatteryEventHistory(j, j2, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getSupportedHistoryTypes() {
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.getSupportedHistoryTypes();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<SemThermalStats> getThermalStats(long j, long j2) {
        if (j > j2) {
            return Collections.EMPTY_LIST;
        }
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getThermalStats(j, j2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<SemProcessUsageStats> getProcessUsageStats(long j, long j2) {
        if (j > j2) {
            return Collections.EMPTY_LIST;
        }
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getProcessUsageStats(j, j2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<SemNetworkUsageStats> getNetworkUsageStats(long j, long j2) {
        if (j > j2) {
            return Collections.EMPTY_LIST;
        }
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getNetworkUsageStats(j, j2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean setThermalThrottlingDelta(Context context, int i) {
        ISamsungDeviceHealthManager service;
        if (context != null && (service = getService()) != null) {
            try {
                return service.setThermalThrottlingDeltaWithPackageName(context.getPackageName(), i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int getThermalThrottlingDelta() {
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return -999;
        }
        try {
            return service.getThermalThrottlingDelta();
        } catch (RemoteException e) {
            e.printStackTrace();
            return SehTempStatus.NOT_READABLE;
        }
    }

    public int getSupportedThermalThrottlingDelta() {
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return -999;
        }
        try {
            return service.getSupportedThermalThrottlingDelta();
        } catch (RemoteException e) {
            e.printStackTrace();
            return SehTempStatus.NOT_READABLE;
        }
    }

    public boolean setAnomalyConfig(PendingIntent pendingIntent) {
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.setAnomalyConfig(pendingIntent);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0024 A[Catch: all -> 0x0028, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x0005, B:7:0x000e, B:9:0x0016, B:12:0x0021, B:13:0x0024), top: B:21:0x0001, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized ISamsungDeviceHealthManager getService() {
        IBinder service;
        if (this.mService == null && (service = ServiceManager.getService("sdhms")) != null) {
            ISamsungDeviceHealthManager iSamsungDeviceHealthManagerAsInterface = ISamsungDeviceHealthManager.Stub.asInterface(service);
            this.mService = iSamsungDeviceHealthManagerAsInterface;
            if (iSamsungDeviceHealthManagerAsInterface != null) {
                try {
                    service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.sdhms.SemDeviceHealthManager.1
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
