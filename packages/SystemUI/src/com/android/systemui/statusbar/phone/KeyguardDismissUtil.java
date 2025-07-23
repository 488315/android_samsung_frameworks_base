package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardDismissUtil {
    public final ActivityStarter mActivityStarter;
    public final KeyguardStateController mKeyguardStateController;
    public final SysuiStatusBarStateController mStatusBarStateController;

    public KeyguardDismissUtil(KeyguardStateController keyguardStateController, SysuiStatusBarStateController sysuiStatusBarStateController, ActivityStarter activityStarter) {
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mActivityStarter = activityStarter;
    }

    public final void executeWhenUnlocked(ActivityStarter.OnDismissAction onDismissAction, boolean z, boolean z2) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && z) {
            ((StatusBarStateControllerImpl) this.mStatusBarStateController).setLeaveOpenOnKeyguardHide(true);
        }
        this.mActivityStarter.dismissKeyguardThenExecute(onDismissAction, null, z2);
    }
}
