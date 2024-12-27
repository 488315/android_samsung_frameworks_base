package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
