package android.os;

import android.content.Context;
import android.os.ICustomFrequencyManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class CustomFrequencyManager {
    public static final int CPUCTL = 17;
    public static final int CPUSET = 16;
    public static final int CPU_CORE_NUM_MAX_LIMIT = 5;
    public static final int CPU_CORE_NUM_MIN_LIMIT = 4;
    public static final int CPU_DISABLE_CSTATE = 12;
    public static final int CPU_HOTPLUG_DISABLE = 14;
    public static final int CPU_LEGACY_SCHEDULER = 13;
    public static final int DVFS_MAX_LIMIT = 2;
    public static final int DVFS_MIN_LIMIT = 1;
    public static final int FREQUENCY_BUS_TYPE_BOOST_MAX_LIMIT = 11;
    public static final int FREQUENCY_BUS_TYPE_BOOST_MIN_LIMIT = 10;
    public static final int FREQUENCY_CPU_TYPE_BOOST_MAX_LIMIT = 7;
    public static final int FREQUENCY_CPU_TYPE_BOOST_MIN_LIMIT = 6;
    public static final int FREQUENCY_LCD_FRAME_RATE = 3;
    public static final int FREQUENCY_MMC_TYPE_BURST_MODE = 8;
    public static final int FREQUENCY_REQ_TYPE_GPU = 1;
    public static final int FREQUENCY_REQ_TYPE_GPU_MAX = 9;
    public static final int LITTLE_CPU_MIN = 21;
    public static final int LPM_BIAS = 20;
    public static final int PCIE_PSM_DISABLE = 15;
    public static final int SCHEDTUNE_BOOST = 19;
    public static final int SCHEDTUNE_POLICY = 18;
    private static final String TAG = "CustomFrequencyManager";
    Handler mHandler;
    ICustomFrequencyManager mService;
    private static final boolean DEBUG = "eng".equals(Build.TYPE);
    private static Context mContext = null;

    private CustomFrequencyManager() {
    }

    private static void printExceptionTrace(Exception exc) {
        if (DEBUG) {
            exc.printStackTrace();
        }
    }

    public CustomFrequencyManager(ICustomFrequencyManager iCustomFrequencyManager, Handler handler) {
        this.mService = iCustomFrequencyManager;
        this.mHandler = handler;
    }

    public void onTopAppChanged(boolean z) {
        if (getCfmsService() == null) {
            return;
        }
        try {
            if (z) {
                this.mService.requestMpParameterUpdate("--Nw 2.4 --Tw 150 --Ns 1.4 --Ts 100 --util_h 100 --util_l 0");
            } else {
                this.mService.requestMpParameterUpdate("--Nw 1.99 --Tw 140 --Ns 1.1 --Ts 190 --util_h 70 --util_l 60");
            }
        } catch (Exception e) {
            printExceptionTrace(e);
        }
    }

    public void requestCPUUpdate(int i, int i2) {
        if (getCfmsService() == null) {
            return;
        }
        try {
            logOnEng(TAG, "in manager requestCPUUpdate" + i + " " + i2);
            this.mService.requestCPUUpdate(i, i2);
        } catch (RemoteException e) {
            printExceptionTrace(e);
        }
    }

    public void mpdUpdate(int i) {
        if (getCfmsService() == null) {
            return;
        }
        try {
            logOnEng(TAG, "in manager mpUpdate" + i);
            this.mService.mpdUpdate(i);
        } catch (RemoteException e) {
            printExceptionTrace(e);
        }
    }

    private static void logOnEng(String str, String str2) {
        if (DEBUG) {
            Log.i(str, str2);
        }
    }

    public void sendCommandToSSRM(String str, String str2) {
        if (getCfmsService() == null) {
            return;
        }
        try {
            this.mService.sendCommandToSSRM(str, str2);
        } catch (Exception e) {
            printExceptionTrace(e);
        }
    }

    public void setGamePowerSaving(boolean z) {
        ICustomFrequencyManager iCustomFrequencyManager;
        try {
            if (getCfmsService() == null || (iCustomFrequencyManager = this.mService) == null) {
                return;
            }
            iCustomFrequencyManager.setGamePowerSaving(z);
        } catch (RemoteException unused) {
        }
    }

    public void setGameFps(int i) {
        ICustomFrequencyManager iCustomFrequencyManager;
        try {
            if (getCfmsService() == null || (iCustomFrequencyManager = this.mService) == null) {
                return;
            }
            iCustomFrequencyManager.setGameFps(i);
        } catch (RemoteException unused) {
        }
    }

    public int getGameThrottlingLevel() {
        if (getCfmsService() == null) {
            return 0;
        }
        try {
            return this.mService.getGameThrottlingLevel();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public void setGameTurboMode(boolean z) {
        ICustomFrequencyManager iCustomFrequencyManager;
        try {
            if (getCfmsService() == null || (iCustomFrequencyManager = this.mService) == null) {
                return;
            }
            iCustomFrequencyManager.setGameTurboMode(z);
        } catch (RemoteException unused) {
        }
    }

    public int requestFreezeSlowdown(int i, int i2, boolean z, String str) {
        ICustomFrequencyManager iCustomFrequencyManager;
        try {
            if (getCfmsService() != null && (iCustomFrequencyManager = this.mService) != null) {
                return iCustomFrequencyManager.requestFreezeSlowdown(i, i2, z, str);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setFrozenTime(int i) {
        ICustomFrequencyManager iCustomFrequencyManager;
        try {
            if (getCfmsService() == null || (iCustomFrequencyManager = this.mService) == null) {
                return;
            }
            iCustomFrequencyManager.setFrozenTime(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private synchronized ICustomFrequencyManager getCfmsService() {
        try {
            ICustomFrequencyManager iCustomFrequencyManager = this.mService;
            if (iCustomFrequencyManager != null) {
                return iCustomFrequencyManager;
            }
            IBinder service = ServiceManager.getService(Context.CFMS_SERVICE);
            if (service != null) {
                this.mService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            return this.mService;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void acquire(int i, int i2, String str, int i3, int[] iArr) {
        IBinder service;
        try {
            if (this.mService == null && (service = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                this.mService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            ICustomFrequencyManager iCustomFrequencyManager = this.mService;
            if (iCustomFrequencyManager != null) {
                iCustomFrequencyManager.acquire(i, i2, str, i3, iArr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void release(int i, int i2) {
        IBinder service;
        try {
            if (this.mService == null && (service = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                this.mService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            ICustomFrequencyManager iCustomFrequencyManager = this.mService;
            if (iCustomFrequencyManager != null) {
                iCustomFrequencyManager.release(i, i2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateUsingCgroupVersion(int i) {
        IBinder service;
        try {
            if (this.mService == null && (service = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                this.mService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            ICustomFrequencyManager iCustomFrequencyManager = this.mService;
            if (iCustomFrequencyManager != null) {
                iCustomFrequencyManager.updateUsingCgroupVersion(i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int[] getSupportedFrequency(int i, int i2) {
        IBinder service;
        try {
            if (this.mService == null && (service = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                this.mService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            ICustomFrequencyManager iCustomFrequencyManager = this.mService;
            if (iCustomFrequencyManager != null) {
                return iCustomFrequencyManager.getSupportedFrequency(i, i2);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeSysfs(int i, String str) {
        if (getCfmsService() == null) {
            return;
        }
        try {
            logOnEng(TAG, "in manager writeSysfs - " + i + " " + str);
            this.mService.writeSysfs(i, str);
        } catch (RemoteException e) {
            printExceptionTrace(e);
        }
    }

    public String readSysfs(int i) {
        if (getCfmsService() == null) {
            return "";
        }
        try {
            logOnEng(TAG, "in manager readSysfs - " + i);
            return this.mService.readSysfs(i);
        } catch (RemoteException e) {
            printExceptionTrace(e);
            return "";
        }
    }

    public boolean checkSysfsIdExist(int i) {
        if (getCfmsService() == null) {
            return false;
        }
        try {
            logOnEng(TAG, "in manager checkSysfsIdExist - " + i);
            return this.mService.checkSysfsIdExist(i);
        } catch (RemoteException e) {
            printExceptionTrace(e);
            return false;
        }
    }

    public boolean checkHintExist(int i) {
        try {
            if (getCfmsService() != null) {
                return this.mService.checkHintExist(i);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkResourceExist(int i) {
        try {
            if (getCfmsService() != null) {
                return this.mService.checkResourceExist(i);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void restrictApp(String str, int i, int i2) {
        try {
            if (getCfmsService() != null) {
                this.mService.restrictApp(str, i, i2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disableGpisHint() {
        try {
            if (getCfmsService() != null) {
                this.mService.disableGpisHint();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setGpisHint(boolean z) {
        try {
            if (getCfmsService() != null) {
                this.mService.setGpisHint(z);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendTid(int i, int i2, int i3) {
        try {
            if (getCfmsService() != null) {
                this.mService.sendTid(i, i2, i3);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
