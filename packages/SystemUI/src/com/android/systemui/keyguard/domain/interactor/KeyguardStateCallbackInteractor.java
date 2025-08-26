package com.android.systemui.keyguard.domain.interactor;

import android.app.trust.TrustManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class KeyguardStateCallbackInteractor implements CoreStartable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final List callbacks = new ArrayList();
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final SimBouncerInteractor simBouncerInteractor;
    public final TrustInteractor trustInteractor;
    public final TrustManager trustManager;
    public final WindowManagerLockscreenVisibilityInteractor wmLockscreenVisibilityInteractor;

    public KeyguardStateCallbackInteractor(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SelectedUserInteractor selectedUserInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, TrustInteractor trustInteractor, SimBouncerInteractor simBouncerInteractor, DismissCallbackRegistry dismissCallbackRegistry, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor, TrustManager trustManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.trustInteractor = trustInteractor;
        this.simBouncerInteractor = simBouncerInteractor;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.wmLockscreenVisibilityInteractor = windowManagerLockscreenVisibilityInteractor;
        this.trustManager = trustManager;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
