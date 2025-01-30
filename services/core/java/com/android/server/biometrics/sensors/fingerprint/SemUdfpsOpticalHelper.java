package com.android.server.biometrics.sensors.fingerprint;

import android.R;
import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.biometrics.SemBioFgThread;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemBioSysFsProvider;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.color.ColorDisplayService;
import com.samsung.android.displaysolution.ISemDisplaySolutionManager;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class SemUdfpsOpticalHelper {
  static final String DISPLAY_PANEL_TYPE_PATH = "sys/class/lcd/panel/window_type";
  static final String HW_LIGHT_CIRCLE_DRAW = "1";
  static final String HW_LIGHT_CIRCLE_HIDE = "0";
  static final String HW_LIGHT_SOURCE_PATH = "/sys/class/lcd/panel/fp_green_circle";
  static final String MAX_BRIGHTNESS_PATH = "/sys/class/lcd/panel/mask_brightness";
  static final int OPTICAL_CALIBRATION_LOW_BRIGHTNESS = 2;
  static final int OPTICAL_CALIBRATION_NORMAL = 1;
  public IBinder mBinderForDisplayStateLimit;
  public String mBrightnessColor;
  public String mBrightnessColorForLowBrightness;
  public SemFpOpticalClient mCalibrationClient;
  public DisplayAdjustmentManager mDisplayAdjManager;
  public DisplayManagerInternal mDisplayManagerInternal;
  public String mDisplayPanelType;
  public Runnable mRunnableDisableFunctionForLightSource;
  public Runnable mRunnableRestoreFunctionForLightSource;
  public final SemBioSysFsProvider mSysFsProvider;
  public float mMaxBrightness = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public int mNits = 0;
  public String mLatestHwLightMode = "";
  public final Map mMaskClientList = new HashMap(3);

  public class DisplayAdjustmentManager {
    public ColorDisplayService.ColorDisplayServiceInternal mColorDisplayServiceInternal;
    public boolean mIsDisabled;

    public void disable() {
      synchronized (DisplayAdjustmentManager.class) {
        if (Utils.DEBUG) {
          Slog.d(
              "FingerprintService",
              "DisplayAdjustmentManager.disable: start = " + this.mIsDisabled);
        }
        if (this.mIsDisabled) {
          return;
        }
        this.mIsDisabled = true;
        try {
          if (this.mColorDisplayServiceInternal == null) {
            this.mColorDisplayServiceInternal =
                (ColorDisplayService.ColorDisplayServiceInternal)
                    LocalServices.getService(ColorDisplayService.ColorDisplayServiceInternal.class);
          }
          ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal =
              this.mColorDisplayServiceInternal;
          if (colorDisplayServiceInternal != null) {
            colorDisplayServiceInternal.onOpticalUdfpsStarted();
          }
        } catch (Exception e) {
          Slog.w("FingerprintService", "DisplayAdjustmentManager.disable: " + e.getMessage());
        }
      }
    }

    public void restore() {
      synchronized (DisplayAdjustmentManager.class) {
        if (Utils.DEBUG) {
          Slog.d(
              "FingerprintService",
              "DisplayAdjustmentManager.restore: start = " + this.mIsDisabled);
        }
        if (this.mIsDisabled) {
          try {
            if (this.mColorDisplayServiceInternal == null) {
              this.mColorDisplayServiceInternal =
                  (ColorDisplayService.ColorDisplayServiceInternal)
                      LocalServices.getService(
                          ColorDisplayService.ColorDisplayServiceInternal.class);
            }
            ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal =
                this.mColorDisplayServiceInternal;
            if (colorDisplayServiceInternal != null) {
              colorDisplayServiceInternal.onOpticalUdfpsStopped();
            }
          } catch (Exception e) {
            Slog.w("FingerprintService", "DisplayAdjustmentManager.restore: " + e.getMessage());
          }
          this.mIsDisabled = false;
        }
      }
    }

    public boolean canChangeDeviceColorMode() {
      boolean z;
      synchronized (DisplayAdjustmentManager.class) {
        z = !this.mIsDisabled;
      }
      return z;
    }
  }

  public SemUdfpsOpticalHelper(SemBioSysFsProvider semBioSysFsProvider) {
    this.mSysFsProvider = semBioSysFsProvider;
    if (SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
      return;
    }
    this.mRunnableDisableFunctionForLightSource =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SemUdfpsOpticalHelper.this.disableDisplayAdjustFunc();
          }
        };
    this.mRunnableRestoreFunctionForLightSource =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SemUdfpsOpticalHelper.this.restoreDisplayAdjustFunc();
          }
        };
  }

  public void runOnBgThread(Runnable runnable) {
    SemBioFgThread.get().post(runnable);
  }

  public void onBootThirdPartyAppsCanStart(Context context, String[] strArr) {
    setBrightnessConfigs(context, strArr);
    writeMaxBrightnessInfo();
    runOnBgThread(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            SemUdfpsOpticalHelper.this.lambda$onBootThirdPartyAppsCanStart$0();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBootThirdPartyAppsCanStart$0() {
    try {
      byte[] readSysFs = this.mSysFsProvider.readSysFs(DISPLAY_PANEL_TYPE_PATH);
      this.mDisplayPanelType =
          (readSysFs == null || readSysFs.length == 0)
              ? "No file"
              : new String(readSysFs, StandardCharsets.UTF_8).trim();
    } catch (UnsupportedOperationException e) {
      Slog.e("FingerprintService", "getDisplayPanelType : ", e);
    }
  }

  public final void setBrightnessConfigs(Context context, String[] strArr) {
    this.mNits = 525;
    try {
      if (strArr != null) {
        if (strArr.length > 0) {
          this.mNits = Integer.parseInt(strArr[0]);
        }
        if (strArr.length > 3) {
          this.mBrightnessColor = strArr[3];
        }
        if (strArr.length > 4) {
          this.mBrightnessColorForLowBrightness = strArr[4];
        }
        for (String str : strArr) {
          Slog.d("FingerprintService", "setBrightnessConfigs: node = " + str);
        }
      } else {
        Slog.w("FingerprintService", "setBrightnessConfigs: failed to read from HAL");
        int parseInt =
            Integer.parseInt(
                context.getResources()
                    .getStringArray(
                        R.array.config_highAmbientBrightnessThresholdsOfFixedRefreshRate)[
                    r6.length - 1]);
        if (parseInt < 525) {
          this.mNits = parseInt;
        }
        this.mNits = Math.min(parseInt, 525);
      }
    } catch (Exception e) {
      Slog.w(
          "FingerprintService",
          "getBrightnessNitsValue: failure to read nits info: " + e.getMessage());
    }
    if (Utils.DEBUG) {
      Slog.i("FingerprintService", "setBrightnessConfigs: nits = [" + this.mNits + "]");
    }
  }

  public final void writeMaxBrightnessInfo() {
    runOnBgThread(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            SemUdfpsOpticalHelper.this.lambda$writeMaxBrightnessInfo$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$writeMaxBrightnessInfo$1() {
    ISemDisplaySolutionManager displaySolutionManager = getDisplaySolutionManager();
    if (displaySolutionManager != null) {
      try {
        this.mMaxBrightness = displaySolutionManager.getFingerPrintBacklightValue(this.mNits);
      } catch (RemoteException e) {
        Slog.w("FingerprintService", "writeMaxBrightnessInfo: " + e.getMessage());
      }
    }
    if (this.mMaxBrightness <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      Slog.w(
          "FingerprintService",
          "writeMaxBrightnessInfo: use default value, " + this.mMaxBrightness);
      this.mMaxBrightness = 319.0f;
    }
    this.mSysFsProvider.writeSysFs(
        MAX_BRIGHTNESS_PATH,
        Integer.toString((int) this.mMaxBrightness).getBytes(StandardCharsets.UTF_8));
  }

  public ISemDisplaySolutionManager getDisplaySolutionManager() {
    final AtomicReference atomicReference =
        new AtomicReference(
            ISemDisplaySolutionManager.Stub.asInterface(
                ServiceManager.getService("DisplaySolution")));
    if (atomicReference.get() == null) {
      ServiceThread serviceThread = new ServiceThread("FingerprintService", 10, true);
      final CountDownLatch countDownLatch = new CountDownLatch(1);
      serviceThread.start();
      serviceThread
          .getThreadHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  SemUdfpsOpticalHelper.lambda$getDisplaySolutionManager$2(
                      atomicReference, countDownLatch);
                }
              });
      try {
        Slog.d(
            "FingerprintService",
            "getDisplaySolutionManager: wait for service result = "
                + countDownLatch.await(3L, TimeUnit.SECONDS));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      serviceThread.quit();
    }
    return (ISemDisplaySolutionManager) atomicReference.get();
  }

  public static /* synthetic */ void lambda$getDisplaySolutionManager$2(
      AtomicReference atomicReference, CountDownLatch countDownLatch) {
    atomicReference.set(
        ISemDisplaySolutionManager.Stub.asInterface(
            ServiceManager.waitForService("DisplaySolution")));
    countDownLatch.countDown();
  }

  public void turnOnHwLightMode() {
    setHwLightMode("1");
  }

  public void turnOffHwLightMode() {
    setHwLightMode("0");
  }

  public final void setHwLightMode(String str) {
    if (this.mLatestHwLightMode.equals(str)) {
      return;
    }
    this.mSysFsProvider.writeSysFs(HW_LIGHT_SOURCE_PATH, str.getBytes(StandardCharsets.UTF_8));
    if (Utils.DEBUG) {
      Slog.i("FingerprintService", "setHwLightMode: [" + str + "] done");
    }
    this.mLatestHwLightMode = str;
  }

  public void disableFunctionForLightSource() {
    if (SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
      return;
    }
    SemBioFgThread.get().getHandler().removeCallbacks(this.mRunnableRestoreFunctionForLightSource);
    SemBioFgThread.get().getHandler().post(this.mRunnableDisableFunctionForLightSource);
  }

  public void restoreFunctionForLightSource(long j) {
    if (SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE) {
      return;
    }
    SemBioFgThread.get().getHandler().removeCallbacks(this.mRunnableDisableFunctionForLightSource);
    SemBioFgThread.get().getHandler().postDelayed(this.mRunnableRestoreFunctionForLightSource, j);
  }

  public final void disableDisplayAdjustFunc() {
    if (this.mDisplayAdjManager == null) {
      this.mDisplayAdjManager = new DisplayAdjustmentManager();
    }
    this.mDisplayAdjManager.disable();
  }

  public final void restoreDisplayAdjustFunc() {
    DisplayAdjustmentManager displayAdjustmentManager = this.mDisplayAdjManager;
    if (displayAdjustmentManager != null) {
      displayAdjustmentManager.restore();
      this.mDisplayAdjManager = null;
    }
  }

  public boolean canChangeDeviceColorMode() {
    DisplayAdjustmentManager displayAdjustmentManager = this.mDisplayAdjManager;
    if (displayAdjustmentManager != null) {
      return displayAdjustmentManager.canChangeDeviceColorMode();
    }
    return true;
  }

  public void setDisplayStateLimit(boolean z) {
    try {
      if (this.mDisplayManagerInternal == null) {
        this.mDisplayManagerInternal =
            (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        this.mBinderForDisplayStateLimit = new Binder();
      }
      if (z) {
        this.mDisplayManagerInternal.setDisplayStateLimit(this.mBinderForDisplayStateLimit, 2);
      } else {
        this.mDisplayManagerInternal.setDisplayStateLimit(this.mBinderForDisplayStateLimit, 0);
      }
    } catch (Exception e) {
      Slog.e("FingerprintService", "setDisplayStateLimit: ", e);
    }
  }

  public void addMaskView(IBinder iBinder, String str) {
    synchronized (this.mMaskClientList) {
      if (this.mMaskClientList.containsKey(iBinder)) {
        Slog.i(
            "FingerprintService",
            "addMaskView: already registered client: [" + iBinder + "], [" + str + "]");
      } else {
        SemFpOpticalClient semFpOpticalClient = new SemFpOpticalClient(iBinder, str);
        this.mMaskClientList.put(iBinder, semFpOpticalClient);
        semFpOpticalClient.start(
            new SemFpOpticalClient
                .Callback() { // from class:
                              // com.android.server.biometrics.sensors.fingerprint.SemUdfpsOpticalHelper$$ExternalSyntheticLambda4
              @Override // com.android.server.biometrics.sensors.fingerprint.SemFpOpticalClient.Callback
              public final void onError(SemFpOpticalClient semFpOpticalClient2) {
                SemUdfpsOpticalHelper.this.lambda$addMaskView$3(semFpOpticalClient2);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addMaskView$3(SemFpOpticalClient semFpOpticalClient) {
    removeMaskView(semFpOpticalClient.getToken());
  }

  public void removeMaskView(IBinder iBinder) {
    synchronized (this.mMaskClientList) {
      SemFpOpticalClient semFpOpticalClient =
          (SemFpOpticalClient) this.mMaskClientList.get(iBinder);
      if (semFpOpticalClient == null) {
        Slog.i("FingerprintService", "removeMaskView: No registered client:  " + iBinder);
      } else {
        this.mMaskClientList.remove(iBinder);
        semFpOpticalClient.stop();
      }
    }
  }

  public void getOpticalBrightnessData(Bundle bundle) {
    bundle.putFloat("brightness", this.mMaxBrightness);
    bundle.putFloat("lightColor", this.mNits);
    bundle.putString("nits", this.mBrightnessColor);
  }

  public void setOpticalCalibrationMode(IBinder iBinder, String str, int i) {
    if (i >= 1) {
      SemFpOpticalClient semFpOpticalClient = this.mCalibrationClient;
      if (semFpOpticalClient != null) {
        semFpOpticalClient.stop();
        this.mCalibrationClient = null;
      }
      SemFpOpticalClient semFpOpticalClient2 =
          new SemFpOpticalClient(
              iBinder,
              str,
              true,
              i == 2 ? this.mBrightnessColorForLowBrightness : this.mBrightnessColor);
      this.mCalibrationClient = semFpOpticalClient2;
      semFpOpticalClient2.start(null);
      return;
    }
    SemFpOpticalClient semFpOpticalClient3 = this.mCalibrationClient;
    if (semFpOpticalClient3 == null) {
      Slog.d("FingerprintService", "handleCalibrationMode: No Calibration Client");
    } else {
      semFpOpticalClient3.stop();
      this.mCalibrationClient = null;
    }
  }

  public void dump(PrintWriter printWriter, Pair pair) {
    printWriter.println(" Optical, HW_LS : " + SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE);
    printWriter.println(" Optical, B : " + this.mMaxBrightness);
    printWriter.println(" Optical, N : " + this.mNits);
    printWriter.println(" Optical, C : " + this.mBrightnessColor);
    printWriter.println(" Optical, DT : " + this.mDisplayPanelType);
    synchronized (this.mMaskClientList) {
      Iterator it = this.mMaskClientList.values().iterator();
      while (it.hasNext()) {
        printWriter.println(" Optical, M : " + ((SemFpOpticalClient) it.next()).getPackageName());
      }
    }
    if (pair != null) {
      printWriter.println(" Optical, Calibrated time : " + getOpticalCalibrationTime(pair));
    }
  }

  public final String getOpticalCalibrationTime(Pair pair) {
    String str;
    if (pair != null) {
      byte[] bArr = new byte[256];
      int semRequest =
          ((ServiceProvider) pair.second)
              .semRequest(((Integer) pair.first).intValue(), 40, 0, null, bArr);
      if (semRequest > 0) {
        str = new String(Arrays.copyOf(bArr, semRequest), StandardCharsets.UTF_8).trim();
        return TextUtils.emptyIfNull(str);
      }
    }
    str = null;
    return TextUtils.emptyIfNull(str);
  }
}
