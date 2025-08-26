package com.android.systemui.qs;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.SecurityController;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QSSecurityFooterUtils_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider bgLooperProvider;
    public final Provider contextProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider dialogTransitionAnimatorProvider;
    public final Provider mainHandlerProvider;
    public final Provider securityControllerProvider;
    public final Provider userTrackerProvider;

    public QSSecurityFooterUtils_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.devicePolicyManagerProvider = provider2;
        this.userTrackerProvider = provider3;
        this.mainHandlerProvider = provider4;
        this.activityStarterProvider = provider5;
        this.securityControllerProvider = provider6;
        this.bgLooperProvider = provider7;
        this.dialogTransitionAnimatorProvider = provider8;
    }

    public static QSSecurityFooterUtils newInstance(Context context, DevicePolicyManager devicePolicyManager, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, DialogTransitionAnimator dialogTransitionAnimator) {
        return new QSSecurityFooterUtils(context, devicePolicyManager, userTracker, handler, activityStarter, securityController, looper, dialogTransitionAnimator);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new QSSecurityFooterUtils((Context) this.contextProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SecurityController) this.securityControllerProvider.get(), (Looper) this.bgLooperProvider.get(), (DialogTransitionAnimator) this.dialogTransitionAnimatorProvider.get());
    }
}
