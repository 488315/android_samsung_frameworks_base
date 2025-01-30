package com.android.server.am;

import android.app.ForegroundServiceDelegationOptions;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

/* loaded from: classes.dex */
public class ForegroundServiceDelegation {
  public final IBinder mBinder = new Binder();
  public final ServiceConnection mConnection;
  public final ForegroundServiceDelegationOptions mOptions;

  public ForegroundServiceDelegation(
      ForegroundServiceDelegationOptions foregroundServiceDelegationOptions,
      ServiceConnection serviceConnection) {
    this.mOptions = foregroundServiceDelegationOptions;
    this.mConnection = serviceConnection;
  }
}
