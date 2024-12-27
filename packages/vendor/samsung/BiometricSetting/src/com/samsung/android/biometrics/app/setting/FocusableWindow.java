package com.samsung.android.biometrics.app.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class FocusableWindow extends SysUiWindow
        implements ViewTreeObserver.OnWindowFocusChangeListener {
    BroadcastReceiver mCloseSystemDialogBR;
    public final FocusableWindow$$ExternalSyntheticLambda1 mRunnableHandleWindowFocus;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticLambda1] */
    public FocusableWindow(Context context) {
        super(context);
        this.mRunnableHandleWindowFocus =
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z;
                        View view;
                        FocusableWindow focusableWindow = FocusableWindow.this;
                        focusableWindow.getClass();
                        try {
                            view = focusableWindow.mBaseView;
                        } catch (Exception e) {
                            FocusableWindow$$ExternalSyntheticOutline0.m(
                                    e,
                                    new StringBuilder("hasFocus: "),
                                    focusableWindow.getLogTag());
                        }
                        if (view != null) {
                            z = view.getWindowId().isFocused();
                            if (!z || Utils.isDesktopMode(focusableWindow.mContext)) {}
                            focusableWindow.onFocusLost();
                            return;
                        }
                        z = false;
                        if (z) {}
                    }
                };
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void addView() {
        View view = this.mBaseView;
        if (view != null) {
            view.getViewTreeObserver().addOnWindowFocusChangeListener(this);
        }
        if (this.mCloseSystemDialogBR == null) {
            this.mCloseSystemDialogBR =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.biometrics.app.setting.FocusableWindow.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS"
                                    .equals(intent.getAction())) {
                                Log.i(
                                        FocusableWindow.this.getLogTag(),
                                        "ACTION: CLOSE_SYSTEM_DIALOG: reason = "
                                                + intent.getStringExtra("reason"));
                                FocusableWindow.this.onSystemDialogClosed();
                            }
                        }
                    };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
                this.mContext.registerReceiverAsUser(
                        this.mCloseSystemDialogBR, UserHandle.ALL, intentFilter, null, this.mH);
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("registerBroadcastReceiverForSystemDialog: "),
                        getLogTag());
            }
        }
        super.addView();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void onAttachedToWindow(View view) {
        FocusableWindow$$ExternalSyntheticLambda1 focusableWindow$$ExternalSyntheticLambda1 =
                this.mRunnableHandleWindowFocus;
        if (focusableWindow$$ExternalSyntheticLambda1 != null) {
            this.mH.postDelayed(focusableWindow$$ExternalSyntheticLambda1, 500L);
        }
    }

    public abstract void onFocusLost();

    public abstract void onSystemDialogClosed();

    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
    public final void onWindowFocusChanged(boolean z) {
        Log.i(getLogTag(), "onWindowFocusChanged: " + z);
        Handler handler = this.mH;
        if (z) {
            handler.removeCallbacks(this.mRunnableHandleWindowFocus);
        } else if (this.mHasPendingRemove) {
            Log.d(getLogTag(), "onWindowFocusChanged: ignore, Pending Remove");
        } else {
            handler.postDelayed(this.mRunnableHandleWindowFocus, 1000L);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void removeView() {
        this.mH.removeCallbacks(this.mRunnableHandleWindowFocus);
        BroadcastReceiver broadcastReceiver = this.mCloseSystemDialogBR;
        if (broadcastReceiver != null) {
            try {
                this.mContext.unregisterReceiver(broadcastReceiver);
                this.mCloseSystemDialogBR = null;
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("unregisterBroadcastReceiverForSystemDialog: "),
                        getLogTag());
            }
        }
        super.removeView();
    }
}
