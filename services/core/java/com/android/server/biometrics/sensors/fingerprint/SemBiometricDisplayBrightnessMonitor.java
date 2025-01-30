package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.os.Handler;
import android.util.Slog;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.biometrics.Utils;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemBiometricDisplayBrightnessMonitor extends SemBiometricDisplayMonitor {
  public int mBrightness;

  public SemBiometricDisplayBrightnessMonitor(Context context, Handler handler) {
    super(context, handler);
    this.mBrightness = 127;
  }

  public void start() {
    this.mDisplayManagerInternal.registerDisplayBrightnessListener(this);
  }

  public int getBrightness() {
    return this.mBrightness;
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor
  public void onChanged(final float f) {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayBrightnessMonitor$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemBiometricDisplayBrightnessMonitor.this.lambda$onChanged$0(f);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onChanged$0(float f) {
    int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f);
    if (Utils.DEBUG) {
      Slog.d(
          "SemBiometricDisplayBrightnessMonitor",
          "DisplayBrightnessListener#onChanged : " + brightnessFloatToInt);
    }
    this.mBrightness = brightnessFloatToInt;
    Iterator it = this.mCallbacks.iterator();
    while (it.hasNext()) {
      ((SemBiometricDisplayMonitor.Callback) it.next()).onBrightnessChanged(this.mBrightness);
    }
  }
}
