package android.p009os;

import android.content.Context;
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

  private CustomFrequencyManager() {}

  private static void printExceptionTrace(Exception e) {
    if (DEBUG) {
      e.printStackTrace();
    }
  }

  public CustomFrequencyManager(ICustomFrequencyManager service, Handler handler) {
    this.mService = service;
    this.mHandler = handler;
  }

  public void onTopAppChanged(boolean isTopFullscreen) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      if (isTopFullscreen) {
        this.mService.requestMpParameterUpdate(
            "--Nw 2.4 --Tw 150 --Ns 1.4 --Ts 100 --util_h 100 --util_l 0");
      } else {
        this.mService.requestMpParameterUpdate(
            "--Nw 1.99 --Tw 140 --Ns 1.1 --Ts 190 --util_h 70 --util_l 60");
      }
    } catch (Exception e) {
      printExceptionTrace(e);
    }
  }

  public void requestCPUUpdate(int cpu, int enable) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      logOnEng(TAG, "in manager requestCPUUpdate" + cpu + " " + enable);
      this.mService.requestCPUUpdate(cpu, enable);
    } catch (RemoteException e) {
      printExceptionTrace(e);
    }
  }

  public void mpdUpdate(int mpEnable) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      logOnEng(TAG, "in manager mpUpdate" + mpEnable);
      this.mService.mpdUpdate(mpEnable);
    } catch (RemoteException e) {
      printExceptionTrace(e);
    }
  }

  private static void logOnEng(String tag, String msg) {
    if (DEBUG) {
      Log.m98i(tag, msg);
    }
  }

  public void sendCommandToSSRM(String type, String value) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      this.mService.sendCommandToSSRM(type, value);
    } catch (Exception e) {
      printExceptionTrace(e);
    }
  }

  public void setGamePowerSaving(boolean enabled) {
    ICustomFrequencyManager iCustomFrequencyManager;
    try {
      if (getCfmsService() != null && (iCustomFrequencyManager = this.mService) != null) {
        iCustomFrequencyManager.setGamePowerSaving(enabled);
      }
    } catch (RemoteException e) {
    }
  }

  public void setGameFps(int level) {
    ICustomFrequencyManager iCustomFrequencyManager;
    try {
      if (getCfmsService() != null && (iCustomFrequencyManager = this.mService) != null) {
        iCustomFrequencyManager.setGameFps(level);
      }
    } catch (RemoteException e) {
    }
  }

  public int getGameThrottlingLevel() {
    if (getCfmsService() == null) {
      return 0;
    }
    try {
      int level = this.mService.getGameThrottlingLevel();
      return level;
    } catch (RemoteException e) {
      return 0;
    }
  }

  public void setGameTurboMode(boolean enabled) {
    ICustomFrequencyManager iCustomFrequencyManager;
    try {
      if (getCfmsService() != null && (iCustomFrequencyManager = this.mService) != null) {
        iCustomFrequencyManager.setGameTurboMode(enabled);
      }
    } catch (RemoteException e) {
    }
  }

  public int requestFreezeSlowdown(int pid, boolean isEnabled, String type) {
    ICustomFrequencyManager iCustomFrequencyManager;
    try {
      if (getCfmsService() == null || (iCustomFrequencyManager = this.mService) == null) {
        return -1;
      }
      int ret = iCustomFrequencyManager.requestFreezeSlowdown(pid, isEnabled, type);
      return ret;
    } catch (RemoteException e) {
      e.printStackTrace();
      return -1;
    }
  }

  public void setFrozenTime(int timeMs) {
    ICustomFrequencyManager iCustomFrequencyManager;
    try {
      if (getCfmsService() != null && (iCustomFrequencyManager = this.mService) != null) {
        iCustomFrequencyManager.setFrozenTime(timeMs);
      }
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
      IBinder b = ServiceManager.getService(Context.CFMS_SERVICE);
      if (b != null) {
        this.mService = ICustomFrequencyManager.Stub.asInterface(b);
      }
      return this.mService;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void acquire(int pid, int token, String procName, int hint, int[] list) {
    IBinder b;
    try {
      if (this.mService == null && (b = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
        this.mService = ICustomFrequencyManager.Stub.asInterface(b);
      }
      ICustomFrequencyManager iCustomFrequencyManager = this.mService;
      if (iCustomFrequencyManager != null) {
        iCustomFrequencyManager.acquire(pid, token, procName, hint, list);
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public void release(int pid, int token) {
    IBinder b;
    try {
      if (this.mService == null && (b = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
        this.mService = ICustomFrequencyManager.Stub.asInterface(b);
      }
      ICustomFrequencyManager iCustomFrequencyManager = this.mService;
      if (iCustomFrequencyManager != null) {
        iCustomFrequencyManager.release(pid, token);
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public int[] getSupportedFrequency(int type, int level) {
    IBinder b;
    try {
      if (this.mService == null && (b = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
        this.mService = ICustomFrequencyManager.Stub.asInterface(b);
      }
      ICustomFrequencyManager iCustomFrequencyManager = this.mService;
      if (iCustomFrequencyManager != null) {
        return iCustomFrequencyManager.getSupportedFrequency(type, level);
      }
      return null;
    } catch (RemoteException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void writeSysfs(int sysfsId, String value) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      logOnEng(TAG, "in manager writeSysfs - " + sysfsId + " " + value);
      this.mService.writeSysfs(sysfsId, value);
    } catch (RemoteException e) {
      printExceptionTrace(e);
    }
  }

  public String readSysfs(int sysfsId) {
    if (getCfmsService() == null) {
      return "";
    }
    try {
      logOnEng(TAG, "in manager readSysfs - " + sysfsId);
      return this.mService.readSysfs(sysfsId);
    } catch (RemoteException e) {
      printExceptionTrace(e);
      return "";
    }
  }

  public boolean checkSysfsIdExist(int sysfsId) {
    if (getCfmsService() == null) {
      return false;
    }
    try {
      logOnEng(TAG, "in manager checkSysfsIdExist - " + sysfsId);
      return this.mService.checkSysfsIdExist(sysfsId);
    } catch (RemoteException e) {
      printExceptionTrace(e);
      return false;
    }
  }

  public boolean checkHintExist(int hint) {
    try {
      if (getCfmsService() != null) {
        return this.mService.checkHintExist(hint);
      }
      return false;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean checkResourceExist(int resourceId) {
    try {
      if (getCfmsService() != null) {
        return this.mService.checkResourceExist(resourceId);
      }
      return false;
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void restrictApp(String packageName, int userId, int restrictReason) {
    try {
      if (getCfmsService() != null) {
        this.mService.restrictApp(packageName, userId, restrictReason);
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

  public void setGpisHint(boolean flag) {
    try {
      if (getCfmsService() != null) {
        this.mService.setGpisHint(flag);
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public void sendTid(int pid, int tid, int type) {
    if (getCfmsService() == null) {
      return;
    }
    try {
      this.mService.sendTid(pid, tid, type);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
