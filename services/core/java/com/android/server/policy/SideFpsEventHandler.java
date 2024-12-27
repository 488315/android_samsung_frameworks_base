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
import android.view.accessibility.AccessibilityManager;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SideFpsEventHandler implements View.OnClickListener {
    public final AccessibilityManager mAccessibilityManager;
    public final Context mContext;
    public FingerprintManager mFingerprintManager;
    public final Handler mHandler;
    public final PowerManager mPowerManager;
    public final AtomicBoolean mSideFpsEventHandlerReady = new AtomicBoolean(false);

    /* renamed from: com.android.server.policy.SideFpsEventHandler$2, reason: invalid class name */
    public final class AnonymousClass2 extends IFingerprintAuthenticatorsRegisteredCallback.Stub {
        public final /* synthetic */ FingerprintManager val$fingerprintManager;

        /* renamed from: com.android.server.policy.SideFpsEventHandler$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends BiometricStateListener {
            public SideFpsEventHandler$2$1$$ExternalSyntheticLambda0 mStateRunnable = null;

            public AnonymousClass1() {
            }

            public final void onBiometricAction(int i) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "onBiometricAction ", "SideFpsEventHandler");
                AccessibilityManager accessibilityManager = SideFpsEventHandler.this.mAccessibilityManager;
                if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                    return;
                }
                SideFpsEventHandler.this.getClass();
                SideFpsEventHandler.dismissDialog("mTurnOffDialog");
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.policy.SideFpsEventHandler$2$1$$ExternalSyntheticLambda0, java.lang.Runnable] */
            public final void onStateChanged(final int i) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "onStateChanged : ", "SideFpsEventHandler");
                SideFpsEventHandler$2$1$$ExternalSyntheticLambda0 sideFpsEventHandler$2$1$$ExternalSyntheticLambda0 = this.mStateRunnable;
                if (sideFpsEventHandler$2$1$$ExternalSyntheticLambda0 != null) {
                    SideFpsEventHandler.this.mHandler.removeCallbacks(sideFpsEventHandler$2$1$$ExternalSyntheticLambda0);
                    this.mStateRunnable = null;
                }
                if (i != 0) {
                    SideFpsEventHandler.this.getClass();
                    return;
                }
                ?? r0 = new Runnable(i) { // from class: com.android.server.policy.SideFpsEventHandler$2$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SideFpsEventHandler.this.getClass();
                    }
                };
                this.mStateRunnable = r0;
                SideFpsEventHandler.this.mHandler.postDelayed(r0, 500L);
                SideFpsEventHandler.this.getClass();
                SideFpsEventHandler.dismissDialog("STATE_IDLE");
            }
        }

        public AnonymousClass2(FingerprintManager fingerprintManager) {
            this.val$fingerprintManager = fingerprintManager;
        }

        public final void onAllAuthenticatorsRegistered(List list) {
            if (this.val$fingerprintManager.isPowerbuttonFps()) {
                this.val$fingerprintManager.registerBiometricStateListener(new AnonymousClass1());
                SideFpsEventHandler.this.mSideFpsEventHandlerReady.set(true);
            }
        }
    }

    public interface DialogProvider {
    }

    public SideFpsEventHandler(Context context, Handler handler, PowerManager powerManager, DialogProvider dialogProvider) {
        this.mContext = context;
        this.mHandler = handler;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mPowerManager = powerManager;
        context.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.policy.SideFpsEventHandler.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                SideFpsEventHandler.this.getClass();
            }
        }, new IntentFilter("android.intent.action.SCREEN_OFF"));
        context.getResources().getInteger(R.integer.device_idle_idle_to_ms);
    }

    public static void dismissDialog(String str) {
        Log.d("SideFpsEventHandler", "Dismissing dialog with reason: ".concat(str));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.mPowerManager.goToSleep(0L, 4, 0);
    }
}
