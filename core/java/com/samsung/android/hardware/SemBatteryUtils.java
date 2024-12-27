package com.samsung.android.hardware;

import android.content.Context;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.sec.android.sdhms.ISamsungDeviceHealthManager;

public class SemBatteryUtils {

    @Deprecated public static final int FLAG_AOD_ON = 4096;

    @Deprecated public static final int FLAG_BRIGHTNESS_100 = 5;

    @Deprecated public static final int FLAG_BRIGHTNESS_80 = 1;

    @Deprecated public static final int FLAG_BRIGHTNESS_85 = 2;

    @Deprecated public static final int FLAG_BRIGHTNESS_90 = 3;

    @Deprecated public static final int FLAG_BRIGHTNESS_95 = 4;

    @Deprecated public static final int FLAG_RESOLUTION_FHD = 64;

    @Deprecated public static final int FLAG_RESOLUTION_HD = 32;

    @Deprecated public static final int FLAG_RESOLUTION_WQHD = 96;

    @Deprecated public static final int FLAG_RESTRICT_DATA = 2048;

    @Deprecated public static final int FLAG_RESTRICT_PERFORMANCE = 1024;

    @Deprecated public static final int MODE_BIG_DATA_LOGGING = 4;
    public static final int MODE_BIG_DATA_UPLOADING = 5;

    @Deprecated(forRemoval = true, since = "4.5")
    public static final int MODE_NORMAL = 0;

    @Deprecated public static final int MODE_NORMAL_100 = 19;

    @Deprecated public static final int MODE_PERFORMANCE = 18;
    public static final int MODE_POWER_SAVING = 1;

    @Deprecated public static final int MODE_POWER_SAVING_IN_DARK_THEME = 17;

    @Deprecated public static final int MODE_POWER_SAVING_IN_GRAYSCALE = 16;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_BLOCKING_DATA = 6;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_LIMITING_APPLICATIONS = 18;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_MAX_BRIGHTNESS_100 = 11;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_MAX_BRIGHTNESS_80 = 7;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_MAX_BRIGHTNESS_85 = 8;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_MAX_BRIGHTNESS_90 = 9;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_MAX_BRIGHTNESS_95 = 10;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_RESOLUTIION_FHD = 13;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_RESOLUTIION_HD = 12;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_RESOLUTIION_WQHD = 14;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_RESTRICTING_BG_DATA = 3;

    @Deprecated public static final int MODE_POWER_SAVING_WITH_RESTRICTING_PERFORMANCE = 15;
    public static final int MODE_ULTRA_POWER_SAVING = 2;
    private static ICustomFrequencyManager mService;

    private SemBatteryUtils() {}

    private static synchronized ICustomFrequencyManager getService(Context context) {
        ICustomFrequencyManager iCustomFrequencyManager;
        IBinder b;
        synchronized (SemBatteryUtils.class) {
            if (mService == null && (b = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                mService = ICustomFrequencyManager.Stub.asInterface(b);
            }
            iCustomFrequencyManager = mService;
        }
        return iCustomFrequencyManager;
    }

    public static int getBatteryRemainingUsageTime(Context context, int mode) {
        ISamsungDeviceHealthManager service;
        if (context == null || mode < 0) {
            return -2;
        }
        IBinder binder = ServiceManager.getService("sdhms");
        if (binder == null
                || (service = ISamsungDeviceHealthManager.Stub.asInterface(binder)) == null) {
            return 0;
        }
        try {
            int remainTime = service.getRemainingUsageTime(mode);
            return remainTime;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getBatteryRemainingUsageTime(Context context, int mode, int flags) {
        ISamsungDeviceHealthManager service;
        if (context == null || mode < 0) {
            return -2;
        }
        IBinder binder = ServiceManager.getService("sdhms");
        if (binder == null
                || (service = ISamsungDeviceHealthManager.Stub.asInterface(binder)) == null) {
            return 0;
        }
        try {
            int remainTime = service.getRemainingUsageTimeWithSettings(mode, flags);
            return remainTime;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
