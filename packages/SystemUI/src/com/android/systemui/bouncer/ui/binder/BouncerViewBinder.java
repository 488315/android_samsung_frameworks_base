package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.systemui.biometrics.plugins.AuthContextPlugins;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import java.util.Optional;

/* loaded from: classes.dex */
public final class BouncerViewBinder {
    public final Lazy legacyBouncerDependencies;

    public BouncerViewBinder(Lazy lazy, Lazy lazy2, Optional<AuthContextPlugins> optional) {
        this.legacyBouncerDependencies = lazy;
    }

    public final void bind(ViewGroup viewGroup) {
        ComposeBouncerFlags.INSTANCE.getClass();
        LegacyBouncerDependencies legacyBouncerDependencies = (LegacyBouncerDependencies) this.legacyBouncerDependencies.get();
        KeyguardBouncerViewBinder.bind(viewGroup, legacyBouncerDependencies.viewModel, legacyBouncerDependencies.primaryBouncerToGoneTransitionViewModel, legacyBouncerDependencies.glanceableHubToPrimaryBouncerTransitionViewModel, legacyBouncerDependencies.componentFactory, legacyBouncerDependencies.messageAreaControllerFactory, legacyBouncerDependencies.bouncerMessageInteractor, legacyBouncerDependencies.bouncerLogger, legacyBouncerDependencies.selectedUserInteractor);
    }
}
