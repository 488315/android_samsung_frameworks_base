package com.android.server.biometrics.sensors.fingerprint;

import android.os.Handler;
import android.os.PowerManagerInternal;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import java.io.File;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public class SemFpLocalHbmOpticalController implements SemBiometricDisplayMonitor.Callback {
  public SemBiometricDisplayStateMonitor mDisplayStateMonitor;
  public final Handler mHandler;
  public Runnable mLocalHbmModeChangeAfterScreenOn;
  public LocalHbmStatus mCurrentLocalHbmStatus = LocalHbmStatus.LOCAL_HBM_MODE_OFF;
  public PowerManagerInternal mPowerManagerInternal =
      (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);

  public SemFpLocalHbmOpticalController(
      Handler handler, SemBiometricDisplayStateMonitor semBiometricDisplayStateMonitor) {
    this.mHandler = handler;
    this.mDisplayStateMonitor = semBiometricDisplayStateMonitor;
    semBiometricDisplayStateMonitor.registerCallback(this);
    this.mDisplayStateMonitor.mRunnableLocalHbmOff =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SemFpLocalHbmOpticalController.this.lambda$new$0();
          }
        };
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    handleLocalHbm(0, true);
  }

  public void handleLocalHbm(final int i, boolean z) {
    Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemFpLocalHbmOpticalController.this.lambda$handleLocalHbm$2(i);
          }
        };
    if (z) {
      runnable.run();
    } else {
      this.mHandler.post(runnable);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLocalHbm$2(final int i) {
    synchronized (this) {
      if (i == 0) {
        changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_OFF);
      } else if (!this.mDisplayStateMonitor.isChangingPhysicalState()
          && this.mDisplayStateMonitor.getPhysicalDisplayState() == 2) {
        if (i == 1) {
          changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF);
        } else if (i == 2) {
          changeToNextState(LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON);
        }
        this.mLocalHbmModeChangeAfterScreenOn = null;
      } else {
        this.mLocalHbmModeChangeAfterScreenOn =
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                SemFpLocalHbmOpticalController.this.lambda$handleLocalHbm$1(i);
              }
            };
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLocalHbm$1(int i) {
    handleLocalHbm(i, false);
  }

  @Override // com.android.server.biometrics.sensors.fingerprint.SemBiometricDisplayMonitor.Callback
  public void onFinishDisplayState(int i, int i2, int i3) {
    Runnable runnable;
    if (SemBiometricFeature.FP_FEATURE_SUPPORT_LOCAL_HBM
        && i2 == 2
        && (runnable = this.mLocalHbmModeChangeAfterScreenOn) != null) {
      runnable.run();
      this.mLocalHbmModeChangeAfterScreenOn = null;
    }
  }

  public final void freezingBrightness(boolean z) {
    PowerManagerInternal powerManagerInternal;
    if (!SemBiometricFeature.FP_FEATURE_SUPPORT_LOCAL_HBM
        || (powerManagerInternal = this.mPowerManagerInternal) == null) {
      return;
    }
    powerManagerInternal.setFreezeBrightnessMode(z);
  }

  public final boolean writeLocalHbmStatus(LocalHbmStatus localHbmStatus) {
    if (SemBiometricFeature.FP_FEATURE_SUPPORT_LOCAL_HBM) {
      if (localHbmStatus == LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON) {
        freezingBrightness(true);
      } else if (localHbmStatus == LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF) {
        freezingBrightness(false);
      }
      return Utils.writeFile(
          new File("/sys/class/lcd/panel/local_hbm"),
          localHbmStatus.getString().getBytes(StandardCharsets.UTF_8));
    }
    if (SemBiometricFeature.FP_FEATURE_SUPPORT_JDM_LOCAL_HBM) {
      return Utils.writeFile(
          new File("/sys/class/display/display_ctrl/lhbm_mode_set"),
          localHbmStatus.getString().getBytes(StandardCharsets.UTF_8));
    }
    return false;
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x0039, code lost:

     if (writeLocalHbmStatus(r3) != false) goto L42;
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x0065, code lost:

     if (writeLocalHbmStatus(r3) != false) goto L42;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void changeToNextState(LocalHbmStatus localHbmStatus) {
    if (SemBiometricFeature.FP_FEATURE_SUPPORT_JDM_LOCAL_HBM) {
      if (localHbmStatus == LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON) {
        localHbmStatus = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF;
      } else {
        localHbmStatus = LocalHbmStatus.LOCAL_HBM_MODE_OFF;
      }
    }
    int i = AbstractC09061.f1658x599318c[this.mCurrentLocalHbmStatus.ordinal()];
    boolean z = true;
    boolean z2 = false;
    if (i == 1) {
      LocalHbmStatus localHbmStatus2 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF;
      if (localHbmStatus == localHbmStatus2) {
        z2 = writeLocalHbmStatus(localHbmStatus2);
      } else {
        LocalHbmStatus localHbmStatus3 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON;
        if (localHbmStatus == localHbmStatus3) {
          if (writeLocalHbmStatus(localHbmStatus2)) {}
          z = false;
          z2 = z;
        }
      }
    } else if (i == 2) {
      LocalHbmStatus localHbmStatus4 = LocalHbmStatus.LOCAL_HBM_MODE_OFF;
      if (localHbmStatus == localHbmStatus4) {
        z2 = writeLocalHbmStatus(localHbmStatus4);
      } else {
        LocalHbmStatus localHbmStatus5 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON;
        if (localHbmStatus == localHbmStatus5) {
          z2 = writeLocalHbmStatus(localHbmStatus5);
        }
      }
    } else if (i == 3) {
      LocalHbmStatus localHbmStatus6 = LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF;
      if (localHbmStatus == localHbmStatus6) {
        z2 = writeLocalHbmStatus(localHbmStatus6);
      } else {
        LocalHbmStatus localHbmStatus7 = LocalHbmStatus.LOCAL_HBM_MODE_OFF;
        if (localHbmStatus == localHbmStatus7) {
          if (writeLocalHbmStatus(localHbmStatus6)) {}
          z = false;
          z2 = z;
        }
      }
    }
    if (z2) {
      Slog.i(
          "FingerprintService.SemFpLhbmOpticalController",
          "LocalHbmStatus change from : "
              + this.mCurrentLocalHbmStatus
              + ", to : "
              + localHbmStatus);
      this.mCurrentLocalHbmStatus = localHbmStatus;
    }
  }

  /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemFpLocalHbmOpticalController$1 */
  public abstract /* synthetic */ class AbstractC09061 {

    /* renamed from: $SwitchMap$com$android$server$biometrics$sensors$fingerprint$SemFpLocalHbmOpticalController$LocalHbmStatus */
    public static final /* synthetic */ int[] f1658x599318c;

    static {
      int[] iArr = new int[LocalHbmStatus.values().length];
      f1658x599318c = iArr;
      try {
        iArr[LocalHbmStatus.LOCAL_HBM_MODE_OFF.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1658x599318c[LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_OFF.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        f1658x599318c[LocalHbmStatus.LOCAL_HBM_MODE_ON_SOURCE_ON.ordinal()] = 3;
      } catch (NoSuchFieldError unused3) {
      }
    }
  }

  enum LocalHbmStatus {
    LOCAL_HBM_MODE_OFF("0"),
    LOCAL_HBM_MODE_ON_SOURCE_OFF("1"),
    LOCAL_HBM_MODE_ON_SOURCE_ON("2");

    private String value;

    LocalHbmStatus(String str) {
      this.value = str;
    }

    public String getString() {
      return this.value;
    }
  }
}
