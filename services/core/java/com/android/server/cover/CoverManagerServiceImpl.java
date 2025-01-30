package com.android.server.cover;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import com.android.server.LocalServices;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.sepunion.SemPluginManagerLocal;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class CoverManagerServiceImpl extends ICoverManager.Stub {
  public Context mContext;
  public final CoverState mCoverState = new CoverState();
  public final Object mCoverStateLock = new Object();
  public boolean mSystemReady = false;
  public SemPluginManagerLocal mUnionLocal =
      (SemPluginManagerLocal) LocalServices.getService(SemPluginManagerLocal.class);

  public CoverManagerServiceImpl(Context context) {
    this.mContext = context;
  }

  public void systemRunning() {
    if (this.mSystemReady) {
      return;
    }
    this.mSystemReady = true;
  }

  public void registerCallback(IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.registerCallback(iBinder, componentName);
  }

  public void registerListenerCallback(IBinder iBinder, ComponentName componentName, int i) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.registerListenerCallback(iBinder, componentName, i);
  }

  public void registerListenerCallbackForExternal(
      IBinder iBinder, ComponentName componentName, int i) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.registerListenerCallbackForExternal(iBinder, componentName, i);
  }

  public boolean unregisterCallback(IBinder iBinder) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.unregisterCallback(iBinder);
  }

  public boolean unregisterCallbackForExternal(IBinder iBinder) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.unregisterCallbackForExternal(iBinder);
  }

  public CoverState getCoverState() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return new CoverState();
    }
    return semPluginManagerLocal.getCoverState();
  }

  public CoverState getCoverStateForExternal() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return null;
    }
    return semPluginManagerLocal.getCoverStateForExternal();
  }

  public boolean getCoverSwitchState() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return true;
    }
    return semPluginManagerLocal.getCoverSwitchState();
  }

  public boolean isCoverManagerDisabled() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.isCoverManagerDisabled();
  }

  public void disableCoverManager(boolean z, IBinder iBinder, String str) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.disableCoverManager(z, iBinder, str);
  }

  public int getVersion() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    return semPluginManagerLocal == null ? R.animator.fade_in : semPluginManagerLocal.getVersion();
  }

  public void sendDataToCover(int i, byte[] bArr) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.sendDataToCover(i, bArr);
  }

  public void sendPowerKeyToCover() {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.sendPowerKeyToCover();
  }

  public void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.registerNfcTouchListenerCallback(i, iBinder, componentName);
  }

  public boolean unregisterNfcTouchListenerCallback(IBinder iBinder) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.unregisterNfcTouchListenerCallback(iBinder);
  }

  public void sendDataToNfcLedCover(int i, byte[] bArr) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.sendDataToNfcLedCover(i, bArr);
  }

  public void addLedNotification(Bundle bundle) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.addLedNotification(bundle);
  }

  public void removeLedNotification(Bundle bundle) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.removeLedNotification(bundle);
  }

  public void sendSystemEvent(Bundle bundle) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.sendSystemEvent(bundle);
  }

  public boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.disableLcdOffByCover(iBinder, componentName);
  }

  public boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.enableLcdOffByCover(iBinder, componentName);
  }

  public boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.requestCoverAuthentication(iBinder, componentName);
  }

  public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return false;
    }
    return semPluginManagerLocal.setFotaInProgress(z, iBinder, componentName);
  }

  public int onCoverAppCovered(boolean z) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return -1;
    }
    return semPluginManagerLocal.onCoverAppCovered(z);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    SemPluginManagerLocal semPluginManagerLocal = this.mUnionLocal;
    if (semPluginManagerLocal == null) {
      return;
    }
    semPluginManagerLocal.dump(fileDescriptor, printWriter, strArr, "cover");
  }
}
