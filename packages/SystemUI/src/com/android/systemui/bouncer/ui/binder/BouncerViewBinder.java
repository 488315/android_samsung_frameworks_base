package com.android.systemui.bouncer.ui.binder;

import android.view.ViewGroup;
import com.android.systemui.Flags;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlagsImpl;
import dagger.Lazy;

public final class BouncerViewBinder {
    public final ComposeBouncerFlags composeBouncerFlags;
    public final Lazy legacyBouncerDependencies;

    public BouncerViewBinder(ComposeBouncerFlags composeBouncerFlags, Lazy lazy, Lazy lazy2) {
        this.composeBouncerFlags = composeBouncerFlags;
        this.legacyBouncerDependencies = lazy;
    }

    public final void bind(ViewGroup viewGroup) {
        ((ComposeBouncerFlagsImpl) this.composeBouncerFlags).getClass();
        Flags.sceneContainer();
        Flags.FEATURE_FLAGS.getClass();
        LegacyBouncerDependencies legacyBouncerDependencies = (LegacyBouncerDependencies) this.legacyBouncerDependencies.get();
        KeyguardBouncerViewBinder.bind(viewGroup, legacyBouncerDependencies.viewModel, legacyBouncerDependencies.primaryBouncerToGoneTransitionViewModel, legacyBouncerDependencies.componentFactory, legacyBouncerDependencies.messageAreaControllerFactory, legacyBouncerDependencies.bouncerMessageInteractor, legacyBouncerDependencies.bouncerLogger, legacyBouncerDependencies.selectedUserInteractor);
    }
}
