package com.android.systemui.statusbar.phone;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda15(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        switch (i) {
            case 0:
                TintedIconManager tintedIconManager = keyguardStatusBarViewController.mTintedIconManager;
                if (tintedIconManager != null) {
                    tintedIconManager.setBlockList(keyguardStatusBarViewController.getBlockedIcons());
                    break;
                }
                break;
            case 1:
                ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).updateIconsAndTextColors(keyguardStatusBarViewController.mTintedIconManager);
                break;
            default:
                final boolean isUserSwitcherEnabled = keyguardStatusBarViewController.mUserManager.isUserSwitcherEnabled(keyguardStatusBarViewController.getResources().getBoolean(R.bool.qs_show_user_switcher_for_single_user));
                keyguardStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((KeyguardStatusBarView) KeyguardStatusBarViewController.this.mView).mIsUserSwitcherEnabled = isUserSwitcherEnabled;
                    }
                });
                break;
        }
    }
}
