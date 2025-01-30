package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda11(KeyguardStatusBarViewController keyguardStatusBarViewController, boolean z) {
        this.f$0 = keyguardStatusBarViewController;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        boolean z = this.f$1;
        KeyguardStatusBarView keyguardStatusBarView = (KeyguardStatusBarView) keyguardStatusBarViewController.mView;
        keyguardStatusBarView.mHiddenByDeX = z;
        if (z || !((KeyguardStateControllerImpl) keyguardStatusBarViewController.mKeyguardStateController).mShowing) {
            keyguardStatusBarView.setVisibility(keyguardStatusBarView.getVisibility());
        } else {
            keyguardStatusBarView.setVisibility(0);
        }
    }
}
