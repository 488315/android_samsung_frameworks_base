package com.samsung.android.game;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemGameManager {
  private static final int FPS_PARAM_MAX = 60;
  private static final int FPS_PARAM_MIN = 1;
  private static final String TAG = "SemGameManager";
  private static final int TRANSACT_CODE_GET_DFS = 1124;
  public static final int TUNE_PERFORMANCE_MODE_HIGH_PERFORMANCE = 1;
  public static final int TUNE_PERFORMANCE_MODE_NORMAL_PERFORMANCE = 0;
  public static final int TUNE_PERFORMANCE_MODE_SAVE_POWER = -1;
  private IGameManagerService mService;

  public SemGameManager(Context context) {
    this();
  }

  public SemGameManager() {
    IBinder b = getGMSBinder();
    if (b != null && this.mService == null) {
      this.mService = IGameManagerService.Stub.asInterface(b);
    }
    if (this.mService == null) {
      GmsLog.m261w(TAG, "SemGameManager(), init mService failed");
    }
  }

  private IGameManagerService getService() {
    return this.mService;
  }

  public static boolean isAvailable() {
    boolean available = getGMSBinder() != null;
    if (!available) {
      GmsLog.m261w(TAG, "isAvailable(), not available");
    }
    return available;
  }

  public static boolean isGamePackage(String pkgName) throws IllegalStateException {
    IBinder b = getGMSBinder();
    if (b == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    IGameManagerService svc = IGameManagerService.Stub.asInterface(b);
    if (svc == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      int tempRet = svc.identifyGamePackage(pkgName);
      boolean ret = tempRet == 1;
      GmsLog.m256d(TAG, "isGamePackage(), pkgName=" + pkgName + ", ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public boolean isForegroundGame() throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      int tempRet = iGameManagerService.identifyForegroundApp();
      boolean ret = tempRet == 1;
      GmsLog.m256d(TAG, "isForegroundGame(), ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public String getForegroundApp() throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      String ret = iGameManagerService.getForegroundApp();
      GmsLog.m256d(TAG, "getForegroundApp(), ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public List<String> getGameList() throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      List<String> ret = iGameManagerService.getGameList();
      GmsLog.m256d(TAG, "getGameList(), ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public boolean init(int mode, Map pkgMap) throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      return iGameManagerService.initGameManager(mode, pkgMap);
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public void syncGameList(Map pkgMap) throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      iGameManagerService.syncGameList(pkgMap);
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public String getVersion() throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      String ret = iGameManagerService.getVersion();
      GmsLog.m256d(TAG, "getVersion(), ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public String requestWithJson(String command, String jsonParam) throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      String ret = iGameManagerService.requestWithJson(command, jsonParam);
      GmsLog.m256d(
          TAG,
          "requestWithJson(), command=" + command + ", jsonParam=" + jsonParam + ", ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public String getTopActivityName() throws IllegalStateException {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      String ret = iGameManagerService.getTopActivityName();
      GmsLog.m256d(TAG, "getTopActivityName(), ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public int getTargetFrameRate() {
    IBinder surfaceFlinger = ServiceManager.getService("SurfaceFlinger");
    if (surfaceFlinger == null) {
      throw new IllegalStateException("failed to get SurfaceFlinger");
    }
    int curDfsValue = -1;
    boolean transactRet = false;
    try {
      String appName = getForegroundApp();
      Parcel data = Parcel.obtain();
      if (data != null) {
        data.writeInterfaceToken("android.ui.ISurfaceComposer");
        data.writeString16(appName);
        Parcel reply = Parcel.obtain();
        if (reply != null) {
          transactRet = surfaceFlinger.transact(1124, data, reply, 0);
          if (!transactRet) {
            GmsLog.m257e(TAG, "getTargetFrameRate(), transactRet: false");
          } else {
            curDfsValue = reply.readInt();
            GmsLog.m256d(TAG, "getTargetFrameRate(), transactGetDFS: " + curDfsValue);
          }
          reply.recycle();
        }
        data.recycle();
      }
    } catch (RemoteException e) {
      GmsLog.m257e(TAG, "getTargetFrameRate(), RemoteException!");
    } catch (SecurityException e2) {
      GmsLog.m257e(TAG, "getTargetFrameRate(), SecurityException: Need system privilege");
    }
    if (transactRet) {
      GmsLog.m256d(TAG, "getTargetFrameRate(), ret=" + curDfsValue);
      return curDfsValue;
    }
    throw new IllegalStateException("failed to transact SurfaceFlinger");
  }

  public boolean setTargetFrameRate(int fps) {
    if (this.mService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    IBinder surfaceFlinger = ServiceManager.getService("SurfaceFlinger");
    if (surfaceFlinger == null) {
      throw new IllegalStateException("failed to get SurfaceFlinger");
    }
    if (fps < 1) {
      GmsLog.m257e(TAG, "setTargetFrameRate(), given fps is not allowed value. do nothing.");
      return false;
    }
    if (fps > 60) {
      GmsLog.m261w(TAG, "setTargetFrameRate(), use max value 60");
      fps = 60;
    }
    try {
      boolean ret = this.mService.setTargetFrameRate(surfaceFlinger, fps);
      GmsLog.m256d(TAG, "setTargetFrameRate(), fps=" + fps + ", ret=" + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public boolean setPackageConfigurations(List<SemPackageConfiguration> packageConfigurations) {
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      boolean ret = iGameManagerService.setPackageConfigurations(packageConfigurations);
      GmsLog.m256d(
          TAG,
          "setPackageConfigurations(), packageConfigurations="
              + packageConfigurations
              + ", ret="
              + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public boolean setPerformanceMode(int tunePerformanceMode, String callerPackageName) {
    if (callerPackageName == null || tunePerformanceMode < -1 || 1 < tunePerformanceMode) {
      GmsLog.m257e(
          TAG,
          "setPerformanceMode(), unexpected param. tunePerformanceMode: "
              + tunePerformanceMode
              + ", callerPackageName: "
              + callerPackageName);
      return false;
    }
    IGameManagerService iGameManagerService = this.mService;
    if (iGameManagerService == null) {
      throw new IllegalStateException("gamemanager system service is not available");
    }
    try {
      boolean ret = iGameManagerService.setPerformanceMode(tunePerformanceMode, callerPackageName);
      GmsLog.m256d(
          TAG,
          "setPerformanceMode(), tunePerformanceMode="
              + tunePerformanceMode
              + ", callerPackageName="
              + callerPackageName
              + ", ret="
              + ret);
      return ret;
    } catch (RemoteException e) {
      throw new IllegalStateException("failed to call gamemanager system service");
    }
  }

  public boolean isDynamicSurfaceScalingSupported() {
    GmsLog.m256d(TAG, "isDynamicSurfaceScalingSupported(), ret=true");
    return true;
  }

  public static IBinder getGMSBinder() {
    IBinder b = ServiceManager.getService("gamemanager");
    if (b == null) {
      GmsLog.m261w(TAG, "getGMSBinder(), failed");
    }
    return b;
  }
}
