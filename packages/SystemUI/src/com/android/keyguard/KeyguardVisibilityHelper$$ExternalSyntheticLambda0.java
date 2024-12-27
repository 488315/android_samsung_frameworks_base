package com.android.keyguard;

public final /* synthetic */ class KeyguardVisibilityHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardVisibilityHelper f$0;

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardVisibilityHelper keyguardVisibilityHelper = this.f$0;
        keyguardVisibilityHelper.mKeyguardViewVisibilityAnimating = false;
        keyguardVisibilityHelper.mView.setVisibility(0);
        keyguardVisibilityHelper.log("Callback Set Visibility to VISIBLE");
    }
}
