package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder;
import com.android.systemui.bouncer.ui.binder.LegacyBouncerDependencies;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SafeUIStatusBarKeyguardViewManager f$0;

    @Override // java.lang.Runnable
    public final void run() {
        SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = this.f$0;
        ((NotificationShadeWindowControllerImpl) safeUIStatusBarKeyguardViewManager.mNotificationShadeWindowController).mHelper.addBouncer(safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer);
        safeUIStatusBarKeyguardViewManager.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(safeUIStatusBarKeyguardViewManager.mExpansionCallback);
        ViewGroup viewGroup = safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer;
        Lazy lazy = safeUIStatusBarKeyguardViewManager.mLegacyBouncerDependencies;
        KeyguardBouncerViewBinder.bind(viewGroup, ((LegacyBouncerDependencies) lazy.get()).viewModel, ((LegacyBouncerDependencies) lazy.get()).primaryBouncerToGoneTransitionViewModel, ((LegacyBouncerDependencies) lazy.get()).glanceableHubToPrimaryBouncerTransitionViewModel, ((LegacyBouncerDependencies) lazy.get()).componentFactory, ((LegacyBouncerDependencies) lazy.get()).messageAreaControllerFactory, ((LegacyBouncerDependencies) lazy.get()).bouncerMessageInteractor, ((LegacyBouncerDependencies) lazy.get()).bouncerLogger, ((LegacyBouncerDependencies) lazy.get()).selectedUserInteractor);
    }
}
