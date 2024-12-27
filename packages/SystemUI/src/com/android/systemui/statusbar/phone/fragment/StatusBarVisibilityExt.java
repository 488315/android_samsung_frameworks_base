package com.android.systemui.statusbar.phone.fragment;

import android.os.Handler;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarVisibilityExt implements KeyguardStateController.Callback {
    public final Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public Runnable mUpdateRunnable;

    public StatusBarVisibilityExt(Handler handler, KeyguardStateController keyguardStateController) {
        this.mHandler = handler;
        this.mKeyguardStateController = keyguardStateController;
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardShowingChanged() {
        Runnable runnable = this.mUpdateRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onLaunchTransitionFadingAwayChanged() {
        Runnable runnable = this.mUpdateRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void postUpdateStatusBarVisibility() {
        Runnable runnable = this.mUpdateRunnable;
        if (runnable == null) {
            return;
        }
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(runnable)) {
            handler.removeCallbacks(this.mUpdateRunnable);
        }
        handler.postDelayed(this.mUpdateRunnable, 200L);
    }
}
