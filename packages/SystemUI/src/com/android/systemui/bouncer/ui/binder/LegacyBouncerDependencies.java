package com.android.systemui.bouncer.ui.binder;

import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LegacyBouncerDependencies {
    public final BouncerLogger bouncerLogger;
    public final BouncerMessageInteractor bouncerMessageInteractor;
    public final KeyguardBouncerComponent.Factory componentFactory;
    public final KeyguardMessageAreaController.Factory messageAreaControllerFactory;
    public final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel;
    public final SelectedUserInteractor selectedUserInteractor;
    public final KeyguardBouncerViewModel viewModel;

    public LegacyBouncerDependencies(KeyguardBouncerViewModel keyguardBouncerViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardBouncerComponent.Factory factory, KeyguardMessageAreaController.Factory factory2, BouncerMessageInteractor bouncerMessageInteractor, BouncerLogger bouncerLogger, SelectedUserInteractor selectedUserInteractor) {
        this.viewModel = keyguardBouncerViewModel;
        this.primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.componentFactory = factory;
        this.messageAreaControllerFactory = factory2;
        this.bouncerMessageInteractor = bouncerMessageInteractor;
        this.bouncerLogger = bouncerLogger;
        this.selectedUserInteractor = selectedUserInteractor;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LegacyBouncerDependencies)) {
            return false;
        }
        LegacyBouncerDependencies legacyBouncerDependencies = (LegacyBouncerDependencies) obj;
        return Intrinsics.areEqual(this.viewModel, legacyBouncerDependencies.viewModel) && Intrinsics.areEqual(this.primaryBouncerToGoneTransitionViewModel, legacyBouncerDependencies.primaryBouncerToGoneTransitionViewModel) && Intrinsics.areEqual(this.componentFactory, legacyBouncerDependencies.componentFactory) && Intrinsics.areEqual(this.messageAreaControllerFactory, legacyBouncerDependencies.messageAreaControllerFactory) && Intrinsics.areEqual(this.bouncerMessageInteractor, legacyBouncerDependencies.bouncerMessageInteractor) && Intrinsics.areEqual(this.bouncerLogger, legacyBouncerDependencies.bouncerLogger) && Intrinsics.areEqual(this.selectedUserInteractor, legacyBouncerDependencies.selectedUserInteractor);
    }

    public final int hashCode() {
        return this.selectedUserInteractor.hashCode() + ((this.bouncerLogger.hashCode() + ((this.bouncerMessageInteractor.hashCode() + ((this.messageAreaControllerFactory.hashCode() + ((this.componentFactory.hashCode() + ((this.primaryBouncerToGoneTransitionViewModel.hashCode() + (this.viewModel.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "LegacyBouncerDependencies(viewModel=" + this.viewModel + ", primaryBouncerToGoneTransitionViewModel=" + this.primaryBouncerToGoneTransitionViewModel + ", componentFactory=" + this.componentFactory + ", messageAreaControllerFactory=" + this.messageAreaControllerFactory + ", bouncerMessageInteractor=" + this.bouncerMessageInteractor + ", bouncerLogger=" + this.bouncerLogger + ", selectedUserInteractor=" + this.selectedUserInteractor + ")";
    }
}
