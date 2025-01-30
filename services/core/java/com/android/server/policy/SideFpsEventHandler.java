package com.android.server.policy;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class SideFpsEventHandler implements View.OnClickListener {
  public int mBiometricState;
  public final Context mContext;
  public DialogProvider mDialogProvider;
  public final int mDismissDialogTimeout;
  public FingerprintManager mFingerprintManager;
  public final Handler mHandler;
  public long mLastPowerPressTime;
  public final PowerManager mPowerManager;
  public final AtomicBoolean mSideFpsEventHandlerReady;
  public final Runnable mTurnOffDialog;

  public interface DialogProvider {}

  /* renamed from: -$$Nest$fgetmDialog, reason: not valid java name */
  public static /* bridge */ /* synthetic */ SideFpsToast m10210$$Nest$fgetmDialog(
      SideFpsEventHandler sideFpsEventHandler) {
    sideFpsEventHandler.getClass();
    return null;
  }

  public boolean shouldConsumeSinglePress(long j) {
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    dismissDialog("mTurnOffDialog");
  }

  public SideFpsEventHandler(Context context, Handler handler, PowerManager powerManager) {
    this(
        context,
        handler,
        powerManager,
        new DialogProvider() { // from class:
                               // com.android.server.policy.SideFpsEventHandler$$ExternalSyntheticLambda1
        });
  }

  public SideFpsEventHandler(
      Context context, Handler handler, PowerManager powerManager, DialogProvider dialogProvider) {
    this.mTurnOffDialog =
        new Runnable() { // from class:
                         // com.android.server.policy.SideFpsEventHandler$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SideFpsEventHandler.this.lambda$new$0();
          }
        };
    this.mContext = context;
    this.mHandler = handler;
    this.mPowerManager = powerManager;
    this.mBiometricState = 0;
    this.mSideFpsEventHandlerReady = new AtomicBoolean(false);
    this.mDialogProvider = dialogProvider;
    context.registerReceiver(
        new BroadcastReceiver() { // from class: com.android.server.policy.SideFpsEventHandler.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            SideFpsEventHandler.m10210$$Nest$fgetmDialog(SideFpsEventHandler.this);
          }
        },
        new IntentFilter("android.intent.action.SCREEN_OFF"));
    this.mDismissDialogTimeout =
        context.getResources().getInteger(R.integer.config_smartSelectionInitializedTimeoutMillis);
  }

  @Override // android.view.View.OnClickListener
  public void onClick(View view) {
    goToSleep(this.mLastPowerPressTime);
  }

  public void notifyPowerPressed() {
    Log.i("SideFpsEventHandler", "notifyPowerPressed");
    if (this.mFingerprintManager == null && this.mSideFpsEventHandlerReady.get()) {
      this.mFingerprintManager =
          (FingerprintManager) this.mContext.getSystemService(FingerprintManager.class);
    }
    FingerprintManager fingerprintManager = this.mFingerprintManager;
    if (fingerprintManager == null) {
      return;
    }
    fingerprintManager.onPowerPressed();
  }

  public final void goToSleep(long j) {
    this.mPowerManager.goToSleep(j, 4, 0);
  }

  public void onFingerprintSensorReady() {
    if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
      final FingerprintManager fingerprintManager =
          (FingerprintManager) this.mContext.getSystemService(FingerprintManager.class);
      fingerprintManager.addAuthenticatorsRegisteredCallback(
          new IFingerprintAuthenticatorsRegisteredCallback
              .Stub() { // from class: com.android.server.policy.SideFpsEventHandler.2
            public void onAllAuthenticatorsRegistered(List list) {
              if (fingerprintManager.isPowerbuttonFps()) {
                fingerprintManager.registerBiometricStateListener(new AnonymousClass1());
                SideFpsEventHandler.this.mSideFpsEventHandlerReady.set(true);
              }
            }

            /* renamed from: com.android.server.policy.SideFpsEventHandler$2$1, reason: invalid class name */
            public class AnonymousClass1 extends BiometricStateListener {
              public Runnable mStateRunnable = null;

              public AnonymousClass1() {}

              public void onStateChanged(final int i) {
                Log.d("SideFpsEventHandler", "onStateChanged : " + i);
                if (this.mStateRunnable != null) {
                  SideFpsEventHandler.this.mHandler.removeCallbacks(this.mStateRunnable);
                  this.mStateRunnable = null;
                }
                if (i == 0) {
                  this.mStateRunnable =
                      new Runnable() { // from class:
                                       // com.android.server.policy.SideFpsEventHandler$2$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                          SideFpsEventHandler.C23112.AnonymousClass1.this.lambda$onStateChanged$0(
                              i);
                        }
                      };
                  SideFpsEventHandler.this.mHandler.postDelayed(this.mStateRunnable, 500L);
                  SideFpsEventHandler.this.dismissDialog("STATE_IDLE");
                  return;
                }
                SideFpsEventHandler.this.mBiometricState = i;
              }

              /* JADX INFO: Access modifiers changed from: private */
              public /* synthetic */ void lambda$onStateChanged$0(int i) {
                SideFpsEventHandler.this.mBiometricState = i;
              }

              public void onBiometricAction(int i) {
                Log.d("SideFpsEventHandler", "onBiometricAction " + i);
              }
            }
          });
    }
  }

  public final void dismissDialog(String str) {
    Log.d("SideFpsEventHandler", "Dismissing dialog with reason: " + str);
  }
}
