package com.android.server.sepunion;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.server.sepunion.cover.CoverManagerServiceImpl;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.SemPluginManagerLocal;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class SemPluginLocalService extends SemPluginManagerLocal {
  public static final String TAG = SemPluginLocalService.class.getSimpleName();
  public Context mContext;
  public CoverManagerServiceImpl mCoverManagerServiceImpl;
  public boolean mPluginServiceReady = false;
  public SemPluginManagerService mSemPluginManagerService;

  public SemPluginLocalService(Context context) {
    this.mContext = context;
    initialize();
  }

  public void initialize() {
    this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
  }

  public final CoverManagerServiceImpl getCoverManagerServiceImpl() {
    SemPluginManagerService semPluginManagerService = getSemPluginManagerService();
    this.mSemPluginManagerService = semPluginManagerService;
    if (semPluginManagerService != null) {
      return semPluginManagerService.getCoverManagerServiceImpl();
    }
    return null;
  }

  public final SemPluginManagerService getSemPluginManagerService() {
    SemPluginManagerService semPluginManagerService =
        (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService("plugin");
    this.mSemPluginManagerService = semPluginManagerService;
    return semPluginManagerService;
  }

  public void registerCallback(IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.registerCallback(iBinder, componentName);
  }

  public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.registerListenerCallback(iBinder, componentName, i);
  }

  public boolean unregisterCallback(IBinder iBinder) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.unregisterCallback(iBinder);
  }

  public CoverState getCoverState() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.getCoverState();
  }

  public boolean getCoverSwitchState() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.getCoverSwitchState();
  }

  public boolean isCoverManagerDisabled() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.isCoverManagerDisabled();
  }

  public void disableCoverManager(boolean z, IBinder iBinder, String str) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.disableCoverManager(z, iBinder, str);
  }

  public int getVersion() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.getVersion();
  }

  public void sendDataToCover(int i, byte[] bArr) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.sendDataToCover(i, bArr);
  }

  public void sendPowerKeyToCover() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.sendPowerKeyToCover();
  }

  public void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    try {
      this.mCoverManagerServiceImpl.registerNfcTouchListenerCallback(i, iBinder, componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.unregisterNfcTouchListenerCallback(iBinder);
  }

  public void sendDataToNfcLedCover(int i, byte[] bArr) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.sendDataToNfcLedCover(i, bArr);
  }

  public void addLedNotification(Bundle bundle) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.addLedNotification(bundle);
  }

  public void removeLedNotification(Bundle bundle) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.removeLedNotification(bundle);
  }

  public void sendSystemEvent(Bundle bundle) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.sendSystemEvent(bundle);
  }

  public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    try {
      return this.mCoverManagerServiceImpl.disableLcdOffByCover(iBinder, componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    try {
      return this.mCoverManagerServiceImpl.enableLcdOffByCover(iBinder, componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    try {
      return this.mCoverManagerServiceImpl.requestCoverAuthentication(iBinder, componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    try {
      return this.mCoverManagerServiceImpl.setFotaInProgress(z, iBinder, componentName);
    } catch (RemoteException e) {
      e.printStackTrace();
      return false;
    }
  }

  public int onCoverAppCovered(boolean z) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.onCoverAppCovered(z);
  }

  public void registerListenerCallbackForExternal(
      IBinder iBinder, ComponentName componentName, int i) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    this.mCoverManagerServiceImpl.registerListenerCallbackForExternal(iBinder, componentName, i);
  }

  public boolean unregisterCallbackForExternal(IBinder iBinder) {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.unregisterCallbackForExternal(iBinder);
  }

  public CoverState getCoverStateForExternal() {
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    return this.mCoverManagerServiceImpl.getCoverStateForExternal();
  }

  public void dump(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, String str) {
    if (str == null || !str.equals("cover")) {
      return;
    }
    if (this.mCoverManagerServiceImpl == null) {
      this.mCoverManagerServiceImpl = getCoverManagerServiceImpl();
    }
    CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
    if (coverManagerServiceImpl != null) {
      coverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
    }
  }
}
