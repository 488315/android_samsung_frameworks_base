package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.systemui.biometrics.plugins.AuthContextPlugins;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
