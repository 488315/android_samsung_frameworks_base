package com.samsung.android.sepunion;

import android.content.ComponentName;
import android.p009os.Bundle;
import android.p009os.IBinder;
import com.samsung.android.cover.CoverState;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public abstract class SemPluginManagerLocal {
  public abstract void addLedNotification(Bundle bundle);

  public abstract void disableCoverManager(boolean z, IBinder iBinder, String str);

  public abstract boolean disableLcdOffByCover(IBinder iBinder, ComponentName componentName);

  public abstract void dump(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, String str);

  public abstract boolean enableLcdOffByCover(IBinder iBinder, ComponentName componentName);

  public abstract CoverState getCoverState();

  public abstract CoverState getCoverStateForExternal();

  public abstract boolean getCoverSwitchState();

  public abstract int getVersion();

  public abstract boolean isCoverManagerDisabled();

  public abstract int onCoverAppCovered(boolean z);

  public abstract void registerCallback(IBinder iBinder, ComponentName componentName);

  public abstract void registerListenerCallback(
      IBinder iBinder, ComponentName componentName, int i);

  public abstract void registerListenerCallbackForExternal(
      IBinder iBinder, ComponentName componentName, int i);

  public abstract void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName);

  public abstract void removeLedNotification(Bundle bundle);

  public abstract boolean requestCoverAuthentication(IBinder iBinder, ComponentName componentName);

  public abstract void sendDataToCover(int i, byte[] bArr);

  public abstract void sendDataToNfcLedCover(int i, byte[] bArr);

  public abstract void sendPowerKeyToCover();

  public abstract void sendSystemEvent(Bundle bundle);

  public abstract boolean setFotaInProgress(
      boolean z, IBinder iBinder, ComponentName componentName);

  public abstract boolean unregisterCallback(IBinder iBinder);

  public abstract boolean unregisterCallbackForExternal(IBinder iBinder);

  public abstract boolean unregisterNfcTouchListenerCallback(IBinder iBinder);
}
