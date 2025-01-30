package com.android.systemui.p016qs;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.SecurityController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSSecurityFooterUtils_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider bgLooperProvider;
    public final Provider contextProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider dialogLaunchAnimatorProvider;
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
        this.dialogLaunchAnimatorProvider = provider8;
    }

    public static QSSecurityFooterUtils newInstance(Context context, DevicePolicyManager devicePolicyManager, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, DialogLaunchAnimator dialogLaunchAnimator) {
        return new QSSecurityFooterUtils(context, devicePolicyManager, userTracker, handler, activityStarter, securityController, looper, dialogLaunchAnimator);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Context) this.contextProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SecurityController) this.securityControllerProvider.get(), (Looper) this.bgLooperProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get());
    }
}
