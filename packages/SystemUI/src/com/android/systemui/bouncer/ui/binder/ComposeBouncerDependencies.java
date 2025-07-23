package com.android.systemui.bouncer.ui.binder;

import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerContainerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeBouncerDependencies {
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final BouncerContainerViewModel.Factory bouncerContainerViewModelFactory;
    public final BouncerDialogFactory dialogFactory;
    public final KeyguardInteractor keyguardInteractor;
    public final PrimaryBouncerInteractor legacyInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ViewMediatorCallback viewMediatorCallback;
    public final BouncerOverlayContentViewModel.Factory viewModelFactory;

    public ComposeBouncerDependencies(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, SelectedUserInteractor selectedUserInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerOverlayContentViewModel.Factory factory, BouncerDialogFactory bouncerDialogFactory, BouncerContainerViewModel.Factory factory2, AuthenticationInteractor authenticationInteractor, ViewMediatorCallback viewMediatorCallback) {
        this.applicationScope = coroutineScope;
        this.keyguardInteractor = keyguardInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.legacyInteractor = primaryBouncerInteractor;
        this.viewModelFactory = factory;
        this.dialogFactory = bouncerDialogFactory;
        this.bouncerContainerViewModelFactory = factory2;
        this.authenticationInteractor = authenticationInteractor;
        this.viewMediatorCallback = viewMediatorCallback;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComposeBouncerDependencies)) {
            return false;
        }
        ComposeBouncerDependencies composeBouncerDependencies = (ComposeBouncerDependencies) obj;
        return Intrinsics.areEqual(this.applicationScope, composeBouncerDependencies.applicationScope) && Intrinsics.areEqual(this.keyguardInteractor, composeBouncerDependencies.keyguardInteractor) && Intrinsics.areEqual(this.selectedUserInteractor, composeBouncerDependencies.selectedUserInteractor) && Intrinsics.areEqual(this.legacyInteractor, composeBouncerDependencies.legacyInteractor) && Intrinsics.areEqual(this.viewModelFactory, composeBouncerDependencies.viewModelFactory) && Intrinsics.areEqual(this.dialogFactory, composeBouncerDependencies.dialogFactory) && Intrinsics.areEqual(this.bouncerContainerViewModelFactory, composeBouncerDependencies.bouncerContainerViewModelFactory) && Intrinsics.areEqual(this.authenticationInteractor, composeBouncerDependencies.authenticationInteractor) && Intrinsics.areEqual(this.viewMediatorCallback, composeBouncerDependencies.viewMediatorCallback);
    }

    public final int hashCode() {
        int hashCode = (this.authenticationInteractor.hashCode() + ((this.bouncerContainerViewModelFactory.hashCode() + ((this.dialogFactory.hashCode() + ((this.viewModelFactory.hashCode() + ((this.legacyInteractor.hashCode() + ((this.selectedUserInteractor.hashCode() + ((this.keyguardInteractor.hashCode() + (this.applicationScope.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        ViewMediatorCallback viewMediatorCallback = this.viewMediatorCallback;
        return hashCode + (viewMediatorCallback == null ? 0 : viewMediatorCallback.hashCode());
    }

    public final String toString() {
        return "ComposeBouncerDependencies(applicationScope=" + this.applicationScope + ", keyguardInteractor=" + this.keyguardInteractor + ", selectedUserInteractor=" + this.selectedUserInteractor + ", legacyInteractor=" + this.legacyInteractor + ", viewModelFactory=" + this.viewModelFactory + ", dialogFactory=" + this.dialogFactory + ", bouncerContainerViewModelFactory=" + this.bouncerContainerViewModelFactory + ", authenticationInteractor=" + this.authenticationInteractor + ", viewMediatorCallback=" + this.viewMediatorCallback + ")";
    }
}
