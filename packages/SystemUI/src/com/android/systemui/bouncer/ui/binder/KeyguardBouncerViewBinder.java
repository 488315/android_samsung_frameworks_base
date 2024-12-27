package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardBouncerViewBinder {
    static {
        new KeyguardBouncerViewBinder();
    }

    private KeyguardBouncerViewBinder() {
    }

    public static final void bind(ViewGroup viewGroup, KeyguardBouncerViewModel keyguardBouncerViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardBouncerComponent.Factory factory, KeyguardMessageAreaController.Factory factory2, BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, SelectedUserInteractor selectedUserInteractor) {
        KeyguardSecSecurityContainerController securityContainerController = factory.create(viewGroup).getSecurityContainerController();
        securityContainerController.init();
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new KeyguardBouncerViewBinder$bind$1(keyguardBouncerViewModel, new KeyguardBouncerViewBinder$bind$delegate$1(securityContainerController, selectedUserInteractor), viewGroup, securityContainerController, bouncerLogger, bouncerMessageInteractor, factory2, primaryBouncerToGoneTransitionViewModel, selectedUserInteractor, null));
    }
}
