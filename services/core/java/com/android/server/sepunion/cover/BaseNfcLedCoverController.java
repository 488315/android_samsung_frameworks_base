package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class BaseNfcLedCoverController {
  public static final String TAG =
      "CoverManager_" + BaseNfcLedCoverController.class.getSimpleName();
  public Context mContext;
  public NfcAdapter mNfcAdapter;
  public final PowerManager mPowerManager;
  public boolean mIsLedCoverAttached = false;
  public final int EVENT_TYPE_POWER_KEY = 10;

  public void addLedNotification(Bundle bundle) {}

  public void notifyAuthenticationResponse() {}

  public void registerNfcTouchListenerCallback(
      int i, IBinder iBinder, ComponentName componentName) {}

  public void removeLedNotification(Bundle bundle) {}

  public boolean requestCoverAuthentication(long j, IBinder iBinder, ComponentName componentName) {
    return false;
  }

  public void sendDataToNfcLedCover(int i, byte[] bArr) {}

  public void sendPowerKeyToCover() {}

  public void sendSystemEvent(Bundle bundle) {}

  public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
    return false;
  }

  public boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
    return false;
  }

  public void updateNfcLedCoverAttachStateLocked(boolean z, int i) {}

  public BaseNfcLedCoverController(Looper looper, Context context) {
    this.mContext = context;
    this.mPowerManager = (PowerManager) context.getSystemService("power");
    this.mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
  }

  public void setLcdOffDisabledByCover(boolean z) {
    Bundle bundle = new Bundle();
    bundle.putInt("event_type", 1);
    bundle.putBoolean("lcd_off_disabled_by_cover", z);
    sendSystemEvent(bundle);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current NfcLedCoverController state:");
    printWriter.print("  mIsLEDCoverAttached=");
    printWriter.println(this.mIsLedCoverAttached);
    printWriter.println("  ");
  }

  public String getByteDataString(byte[] bArr) {
    if (bArr == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    for (byte b : bArr) {
      sb.append(String.format("%02X", Byte.valueOf(b)));
      sb.append(" ");
    }
    return sb.toString();
  }

  public NfcAdapter getNfcAdapter() {
    if (this.mNfcAdapter == null) {
      this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
    }
    if (this.mNfcAdapter == null) {
      Log.e(TAG, "Could not get NfcAdapter");
    }
    return this.mNfcAdapter;
  }

  public final void acquireWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (!wakeLock.isHeld()) {
        wakeLock.acquire();
      }
    } catch (IllegalStateException e) {
      Log.e(TAG, "Shouldn't happen", e);
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }

  public final void releaseWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (wakeLock.isHeld()) {
        wakeLock.release();
      }
    } catch (IllegalStateException e) {
      Log.e(TAG, "Shouldn't happen", e);
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
  }
}
