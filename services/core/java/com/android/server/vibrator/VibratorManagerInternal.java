package com.android.server.vibrator;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class VibratorManagerInternal {
  public WeakReference mServiceWeakReference;

  public VibratorManagerInternal(VibratorManagerService vibratorManagerService) {
    this.mServiceWeakReference = new WeakReference(vibratorManagerService);
  }
}
