package com.android.server.power;

import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;

/* loaded from: classes3.dex */
public class InattentiveSleepWarningController {
  public final Handler mHandler = new Handler();
  public boolean mIsShown;
  public IStatusBarService mStatusBarService;

  public boolean isShown() {
    return this.mIsShown;
  }

  public void show() {
    if (isShown()) {
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.power.InattentiveSleepWarningController$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            InattentiveSleepWarningController.this.showInternal();
          }
        });
    this.mIsShown = true;
  }

  public final void showInternal() {
    try {
      getStatusBar().showInattentiveSleepWarning();
    } catch (RemoteException e) {
      Log.e("InattentiveSleepWarning", "Failed to show inattentive sleep warning", e);
      this.mIsShown = false;
    }
  }

  public void dismiss(final boolean z) {
    if (isShown()) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.power.InattentiveSleepWarningController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              InattentiveSleepWarningController.this.lambda$dismiss$0(z);
            }
          });
      this.mIsShown = false;
    }
  }

  /* renamed from: dismissInternal, reason: merged with bridge method [inline-methods] */
  public final void lambda$dismiss$0(boolean z) {
    try {
      getStatusBar().dismissInattentiveSleepWarning(z);
    } catch (RemoteException e) {
      Log.e("InattentiveSleepWarning", "Failed to dismiss inattentive sleep warning", e);
    }
  }

  public final IStatusBarService getStatusBar() {
    if (this.mStatusBarService == null) {
      this.mStatusBarService =
          IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
    }
    return this.mStatusBarService;
  }
}
