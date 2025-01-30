package com.android.server.biometrics.sensors.fingerprint;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemFpAodController {
  ISemFingerprintAodController mAodController;
  public boolean mIsDozeMode;
  public boolean mIsHlpmMode;

  /* renamed from: mH */
  final Handler f1656mH = new Handler(SemFpMainThread.get().getLooper());
  final ArrayList mPendingRequestBeforeListener = new ArrayList();

  public void registerController(final ISemFingerprintAodController iSemFingerprintAodController) {
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$registerController$0(iSemFingerprintAodController);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$registerController$0(
      ISemFingerprintAodController iSemFingerprintAodController) {
    this.mAodController = iSemFingerprintAodController;
    Iterator it = this.mPendingRequestBeforeListener.iterator();
    while (it.hasNext()) {
      ((Runnable) it.next()).run();
    }
    this.mPendingRequestBeforeListener.clear();
  }

  public void unregisterController() {
    this.f1656mH.removeCallbacksAndMessages(null);
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda10
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$unregisterController$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$unregisterController$1() {
    this.mAodController = null;
  }

  public void turnOnDozeMode() {
    final Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOnDozeMode$2();
          }
        };
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOnDozeMode$3(runnable);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOnDozeMode$2() {
    ISemFingerprintAodController iSemFingerprintAodController;
    if (!this.mIsDozeMode && (iSemFingerprintAodController = this.mAodController) != null) {
      try {
        iSemFingerprintAodController.turnOnDozeMode();
      } catch (RemoteException e) {
        Slog.w("SemFpAodController", "turnOnDozeMode: " + e.getMessage());
      }
    }
    this.mIsDozeMode = true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOnDozeMode$3(Runnable runnable) {
    if (this.mAodController == null) {
      this.mPendingRequestBeforeListener.add(runnable);
    } else {
      runnable.run();
    }
  }

  public void turnOffDozeMode() {
    final Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOffDozeMode$4();
          }
        };
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOffDozeMode$5(runnable);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOffDozeMode$4() {
    ISemFingerprintAodController iSemFingerprintAodController;
    if (this.mIsDozeMode && (iSemFingerprintAodController = this.mAodController) != null) {
      try {
        iSemFingerprintAodController.turnOffDozeMode();
      } catch (RemoteException e) {
        Slog.w("SemFpAodController", "turnOffDozeMode: " + e.getMessage());
      }
    }
    this.mIsDozeMode = false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOffDozeMode$5(Runnable runnable) {
    if (this.mAodController == null) {
      this.mPendingRequestBeforeListener.add(runnable);
    } else {
      runnable.run();
    }
  }

  public void turnOnDozeHlpmMode() {
    final Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda6
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOnDozeHlpmMode$6();
          }
        };
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda7
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOnDozeHlpmMode$7(runnable);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOnDozeHlpmMode$6() {
    ISemFingerprintAodController iSemFingerprintAodController;
    if (!this.mIsHlpmMode && (iSemFingerprintAodController = this.mAodController) != null) {
      try {
        iSemFingerprintAodController.turnOnDozeHlpmMode();
      } catch (RemoteException e) {
        Slog.w("SemFpAodController", "turnOnDozeHlpmMode: " + e.getMessage());
      }
    }
    this.mIsHlpmMode = true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOnDozeHlpmMode$7(Runnable runnable) {
    if (this.mAodController == null) {
      this.mPendingRequestBeforeListener.add(runnable);
    } else {
      runnable.run();
    }
  }

  public void turnOffDozeHlpmMode() {
    final Runnable runnable =
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOffDozeHlpmMode$8();
          }
        };
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda9
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$turnOffDozeHlpmMode$9(runnable);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOffDozeHlpmMode$8() {
    ISemFingerprintAodController iSemFingerprintAodController;
    if (this.mIsHlpmMode && (iSemFingerprintAodController = this.mAodController) != null) {
      try {
        iSemFingerprintAodController.turnOffDozeHlpmMode();
      } catch (RemoteException e) {
        Slog.w("SemFpAodController", "turnOffDozeHlpmMode: " + e.getMessage());
      }
    }
    this.mIsHlpmMode = false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$turnOffDozeHlpmMode$9(Runnable runnable) {
    if (this.mAodController == null) {
      this.mPendingRequestBeforeListener.add(runnable);
    } else {
      runnable.run();
    }
  }

  public void hideAodScreen() {
    this.f1656mH.post(
        new Runnable() { // from class:
                         // com.android.server.biometrics.sensors.fingerprint.SemFpAodController$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            SemFpAodController.this.lambda$hideAodScreen$10();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$hideAodScreen$10() {
    ISemFingerprintAodController iSemFingerprintAodController = this.mAodController;
    if (iSemFingerprintAodController != null) {
      try {
        iSemFingerprintAodController.hideAodScreen();
      } catch (RemoteException e) {
        Slog.w("SemFpAodController", "hideAodScreen: " + e.getMessage());
      }
    }
  }
}
