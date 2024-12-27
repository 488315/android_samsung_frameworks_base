package com.android.systemui.statusbar.phone.fragment;

import android.os.Handler;
import com.android.systemui.statusbar.policy.KeyguardStateController;

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
