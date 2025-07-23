package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardBouncerViewBinder {
    static {
        new KeyguardBouncerViewBinder();
    }

    private KeyguardBouncerViewBinder() {
    }

    public static final void bind(ViewGroup viewGroup, KeyguardBouncerViewModel keyguardBouncerViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, GlanceableHubToPrimaryBouncerTransitionViewModel glanceableHubToPrimaryBouncerTransitionViewModel, KeyguardBouncerComponent.Factory factory, KeyguardMessageAreaController.Factory factory2, BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, SelectedUserInteractor selectedUserInteractor) {
        KeyguardSecSecurityContainerController securityContainerController = ((DaggerReferenceGlobalRootComponent.KeyguardBouncerComponentImpl) factory.create(viewGroup)).getSecurityContainerController();
        securityContainerController.init();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new KeyguardBouncerViewBinder$bind$1(keyguardBouncerViewModel, new KeyguardBouncerViewBinder$bind$delegate$1(securityContainerController, selectedUserInteractor), null, viewGroup, securityContainerController, glanceableHubToPrimaryBouncerTransitionViewModel, bouncerLogger, bouncerMessageInteractor, factory2, primaryBouncerToGoneTransitionViewModel, selectedUserInteractor, null));
    }
}
