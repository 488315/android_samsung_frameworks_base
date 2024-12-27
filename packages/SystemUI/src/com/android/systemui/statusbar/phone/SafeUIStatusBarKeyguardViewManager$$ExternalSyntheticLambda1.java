package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder;
import com.android.systemui.bouncer.ui.binder.LegacyBouncerDependencies;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SafeUIStatusBarKeyguardViewManager f$0;

    @Override // java.lang.Runnable
    public final void run() {
        SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = this.f$0;
        ((NotificationShadeWindowControllerImpl) safeUIStatusBarKeyguardViewManager.mNotificationShadeWindowController).mHelper.addBouncer(safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer);
        safeUIStatusBarKeyguardViewManager.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(safeUIStatusBarKeyguardViewManager.mExpansionCallback);
        ViewGroup viewGroup = safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer;
        Lazy lazy = safeUIStatusBarKeyguardViewManager.mLegacyBouncerDependencies;
        KeyguardBouncerViewBinder.bind(viewGroup, ((LegacyBouncerDependencies) lazy.get()).viewModel, ((LegacyBouncerDependencies) lazy.get()).primaryBouncerToGoneTransitionViewModel, ((LegacyBouncerDependencies) lazy.get()).componentFactory, ((LegacyBouncerDependencies) lazy.get()).messageAreaControllerFactory, ((LegacyBouncerDependencies) lazy.get()).bouncerMessageInteractor, ((LegacyBouncerDependencies) lazy.get()).bouncerLogger, ((LegacyBouncerDependencies) lazy.get()).selectedUserInteractor);
    }
}
