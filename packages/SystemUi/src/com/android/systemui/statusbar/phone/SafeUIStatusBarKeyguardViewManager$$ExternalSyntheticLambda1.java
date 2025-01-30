package com.android.systemui.statusbar.phone;

import android.view.ViewGroup;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SafeUIStatusBarKeyguardViewManager f$0;
    public final /* synthetic */ KeyguardBouncerViewModel f$1;
    public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$2;
    public final /* synthetic */ KeyguardBouncerComponent.Factory f$3;

    public /* synthetic */ SafeUIStatusBarKeyguardViewManager$$ExternalSyntheticLambda1(SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager, KeyguardBouncerViewModel keyguardBouncerViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardBouncerComponent.Factory factory) {
        this.f$0 = safeUIStatusBarKeyguardViewManager;
        this.f$1 = keyguardBouncerViewModel;
        this.f$2 = primaryBouncerToGoneTransitionViewModel;
        this.f$3 = factory;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SafeUIStatusBarKeyguardViewManager safeUIStatusBarKeyguardViewManager = this.f$0;
        KeyguardBouncerViewModel keyguardBouncerViewModel = this.f$1;
        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$2;
        KeyguardBouncerComponent.Factory factory = this.f$3;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) safeUIStatusBarKeyguardViewManager.mNotificationShadeWindowController).mHelper;
        ViewGroup viewGroup = safeUIStatusBarKeyguardViewManager.mSafeUIBouncerContainer;
        secNotificationShadeWindowControllerHelperImpl.addBouncer(viewGroup);
        ArrayList arrayList = safeUIStatusBarKeyguardViewManager.mPrimaryBouncerCallbackInteractor.expansionCallbacks;
        SafeUIStatusBarKeyguardViewManager.C30991 c30991 = safeUIStatusBarKeyguardViewManager.mExpansionCallback;
        if (!arrayList.contains(c30991)) {
            arrayList.add(c30991);
        }
        KeyguardBouncerViewBinder.bind(viewGroup, keyguardBouncerViewModel, primaryBouncerToGoneTransitionViewModel, factory);
    }
}
