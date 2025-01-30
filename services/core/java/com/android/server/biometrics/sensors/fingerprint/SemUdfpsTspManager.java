package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.server.ServiceThread;
import com.android.server.biometrics.SemBioFgThread;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.samsung.android.hardware.secinputdev.ISemInputDeviceManager;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class SemUdfpsTspManager {
  static final int CMD_DISABLE = 0;
  static final int CMD_ENABLE = 1;
  static final int CMD_FOD_DISABLE = 6;
  static final int CMD_FOD_ENABLE = 2;
  static final int CMD_FOD_ENABLE_50 = 3;
  static final int CMD_FOD_RECT = 7;
  static final int CMD_FOD_STRICT_ENABLE = 4;
  static final int CMD_FOD_STRICT_ENABLE_50 = 5;
  static final int CMD_LP_MODE = 8;
  static final int CMD_TEMPERATURE = 9;
  public Rect mFodRect;
  public boolean mIsEnable;
  public boolean mIsHalfMode;
  public boolean mIsHalfModeBlocked;
  public boolean mIsLpMode;
  public boolean mIsStrictMode;
  public int mLastCmd;
  public final SparseBooleanArray mEnableClient = new SparseBooleanArray();
  public final SparseArray mCommands = new SparseArray();

  public abstract class InstanceHolder {
    public static final SemUdfpsTspManager INSTANCE = new SemUdfpsTspManager();
  }

  public static synchronized SemUdfpsTspManager get() {
    SemUdfpsTspManager semUdfpsTspManager;
    synchronized (SemUdfpsTspManager.class) {
      semUdfpsTspManager = InstanceHolder.INSTANCE;
    }
    return semUdfpsTspManager;
  }

  public void onBootActivityManagerReady(final Context context) {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
      ContentObserver contentObserver =
          new ContentObserver(
              SemFpMainThread.get()
                  .getHandler()) { // from class:
                                   // com.android.server.biometrics.sensors.fingerprint.SemUdfpsTspManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
              SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.this;
              semUdfpsTspManager.setLpMode(semUdfpsTspManager.isEnableWOF(context));
            }
          };
      try {
        if (Utils.isFirstApiLevel31orGreater()) {
          context
              .getContentResolver()
              .registerContentObserver(
                  Settings.Secure.getUriFor("fingerprint_always_on"), false, contentObserver, -1);
        }
        context
            .getContentResolver()
            .registerContentObserver(
                Settings.Secure.getUriFor("fingerprint_screen_lock"), false, contentObserver, -1);
      } catch (Exception e) {
        Slog.w("FingerprintService", "TspCommand: " + e.getMessage());
      }
      setLpMode(isEnableWOF(context));
    }
  }

  public final boolean isEnableWOF(Context context) {
    if (Utils.getIntDb(context, "fingerprint_screen_lock", true, 0) != 1) {
      return false;
    }
    return !Utils.isFirstApiLevel31orGreater()
        || Utils.getIntDb(context, "fingerprint_always_on", true, 0) == 1;
  }

  public synchronized void enable(int i) {
    this.mEnableClient.put(i, true);
    if (this.mIsEnable) {
      return;
    }
    this.mIsEnable = true;
    setFodEnable();
  }

  public synchronized void disable(int i) {
    this.mEnableClient.delete(i);
    if (this.mIsEnable && this.mEnableClient.size() == 0) {
      sendCommand(6);
      this.mIsEnable = false;
    }
  }

  public synchronized void setHalfMode(boolean z) {
    if (this.mIsHalfMode == z) {
      return;
    }
    this.mIsHalfMode = z;
    setFodEnable();
  }

  public synchronized void doNotEnterHalfMode() {
    this.mIsHalfModeBlocked = true;
  }

  public synchronized void screenOn() {
    setHalfMode(false);
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
      enable(0);
      this.mIsHalfModeBlocked = false;
    }
  }

  public synchronized void screenOff() {
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
        && (SemBiometricFeature.FEATURE_SUPPORT_AOD || SemBiometricFeature.FP_FEATURE_FAKE_AOD)) {
      setHalfMode(true);
    }
    if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_ULTRASONIC) {
      if (!this.mIsHalfModeBlocked) {
        setHalfMode(true);
      }
      this.mIsHalfModeBlocked = false;
    }
  }

  public synchronized void onCaptureStarted() {
    sendCommand(9);
  }

  public synchronized void setStrictMode(boolean z) {
    if (this.mIsStrictMode == z) {
      return;
    }
    this.mIsStrictMode = z;
    setFodEnable();
  }

  public synchronized void setFodRect(Rect rect) {
    this.mFodRect = rect;
    this.mCommands.put(
        7,
        String.format(
            Locale.ENGLISH,
            "set_fod_rect,%d,%d,%d,%d",
            Integer.valueOf(rect.left),
            Integer.valueOf(rect.top),
            Integer.valueOf(rect.right),
            Integer.valueOf(rect.bottom)));
    sendCommand(7);
  }

  public String toDumpString() {
    return " TSP : "
        + ((String) this.mCommands.get(7))
        + "\n TSP : "
        + this.mLastCmd
        + ", "
        + this.mIsLpMode
        + ", "
        + this.mIsStrictMode
        + ", "
        + this.mIsEnable
        + ", "
        + this.mIsHalfMode;
  }

  public final void setFodEnable() {
    int i;
    if (this.mIsEnable) {
      if (this.mIsStrictMode) {
        i = this.mIsHalfMode ? 5 : 4;
      } else {
        i = this.mIsHalfMode ? 3 : 2;
      }
      sendCommand(i);
    }
  }

  public final void setLpMode(boolean z) {
    if (this.mIsLpMode == z) {
      return;
    }
    this.mIsLpMode = z;
    sendCommand(8);
  }

  public final void sendCommand(final int i) {
    SemBioFgThread.get()
        .getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemUdfpsTspManager$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SemUdfpsTspManager.this.lambda$sendCommand$0(i);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendCommand$0(int i) {
    ISemInputDeviceManager inputDeviceManager = getInputDeviceManager();
    if (inputDeviceManager == null) {
      Slog.w("FingerprintService", "sendCommand fail : InputDeviceManager is null");
      return;
    }
    this.mLastCmd = i;
    try {
      switch (i) {
        case 2:
          inputDeviceManager.setFodEnable(1, 1, 0);
          break;
        case 3:
          inputDeviceManager.setFodEnable(1, 0, 0);
          break;
        case 4:
          inputDeviceManager.setFodEnable(1, 1, 1);
          break;
        case 5:
          inputDeviceManager.setFodEnable(1, 0, 1);
          break;
        case 6:
          inputDeviceManager.setFodEnable(0, 0, 0);
          break;
        case 7:
          Rect rect = this.mFodRect;
          if (rect != null && !rect.isEmpty()) {
            Rect rect2 = this.mFodRect;
            inputDeviceManager.setFodRect(rect2.left, rect2.top, rect2.right, rect2.bottom);
            break;
          }
          break;
        case 8:
          inputDeviceManager.setFodLpMode(this.mIsLpMode ? 1 : 0);
          break;
        case 9:
          inputDeviceManager.setTemperature(1);
          break;
      }
    } catch (RemoteException e) {
      Slog.w("FingerprintService", "sendCommand fail with e : " + e.getMessage());
    }
    Slog.i("FingerprintService", "setTspMode: [" + i + "] done");
  }

  public ISemInputDeviceManager getInputDeviceManager() {
    final AtomicReference atomicReference =
        new AtomicReference(
            ISemInputDeviceManager.Stub.asInterface(
                ServiceManager.getService("SemInputDeviceManagerService")));
    if (atomicReference.get() == null) {
      ServiceThread serviceThread = new ServiceThread("FingerprintService", 10, true);
      final CountDownLatch countDownLatch = new CountDownLatch(1);
      serviceThread.start();
      serviceThread
          .getThreadHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.SemUdfpsTspManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  SemUdfpsTspManager.lambda$getInputDeviceManager$1(
                      atomicReference, countDownLatch);
                }
              });
      try {
        Slog.d(
            "FingerprintService",
            "getInputDeviceManager: wait for service result = "
                + countDownLatch.await(2L, TimeUnit.SECONDS));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      serviceThread.quit();
    }
    return (ISemInputDeviceManager) atomicReference.get();
  }

  public static /* synthetic */ void lambda$getInputDeviceManager$1(
      AtomicReference atomicReference, CountDownLatch countDownLatch) {
    atomicReference.set(
        ISemInputDeviceManager.Stub.asInterface(
            ServiceManager.waitForService("SemInputDeviceManagerService")));
    countDownLatch.countDown();
  }
}
