package com.android.systemui.bouncer.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeBouncerViewBinder {
    public static final ComposeBouncerViewBinder INSTANCE = new ComposeBouncerViewBinder();
    public static StandaloneCoroutine persistentBouncerJob;

    private ComposeBouncerViewBinder() {
    }

    public static void bind(final ViewGroup viewGroup, CoroutineScope coroutineScope, PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardInteractor keyguardInteractor, SelectedUserInteractor selectedUserInteractor, BouncerOverlayContentViewModel.Factory factory, BouncerDialogFactory bouncerDialogFactory, BouncerContainerViewModel.Factory factory2, AuthenticationInteractor authenticationInteractor, ViewMediatorCallback viewMediatorCallback) {
        viewGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.bouncer.ui.binder.ComposeBouncerViewBinder$bind$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ComposeInitializer composeInitializer = ComposeInitializer.INSTANCE;
                ViewGroup viewGroup2 = viewGroup;
                composeInitializer.getClass();
                ComposeInitializer.onAttachedToWindow(viewGroup2);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ComposeInitializer composeInitializer = ComposeInitializer.INSTANCE;
                ViewGroup viewGroup2 = viewGroup;
                composeInitializer.getClass();
                ComposeInitializer.onDetachedFromWindow(viewGroup2);
            }
        });
        StandaloneCoroutine standaloneCoroutine = persistentBouncerJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        persistentBouncerJob = BuildersKt.launch$default(coroutineScope, null, null, new ComposeBouncerViewBinder$bind$2(authenticationInteractor, viewGroup, keyguardInteractor, viewMediatorCallback, primaryBouncerInteractor, selectedUserInteractor, null), 3);
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new ComposeBouncerViewBinder$bind$3(viewGroup, factory2, factory, bouncerDialogFactory, null));
    }
}
