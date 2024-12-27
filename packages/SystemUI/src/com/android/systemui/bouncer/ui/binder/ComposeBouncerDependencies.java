package com.android.systemui.bouncer.ui.binder;

import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.jvm.internal.Intrinsics;

public final class ComposeBouncerDependencies {
    public final AuthenticationInteractor authenticationInteractor;
    public final BouncerDialogFactory dialogFactory;
    public final PrimaryBouncerInteractor legacyInteractor;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ViewMediatorCallback viewMediatorCallback;
    public final BouncerViewModel viewModel;

    public ComposeBouncerDependencies(PrimaryBouncerInteractor primaryBouncerInteractor, BouncerViewModel bouncerViewModel, BouncerDialogFactory bouncerDialogFactory, AuthenticationInteractor authenticationInteractor, ViewMediatorCallback viewMediatorCallback, SelectedUserInteractor selectedUserInteractor) {
        this.legacyInteractor = primaryBouncerInteractor;
        this.viewModel = bouncerViewModel;
        this.dialogFactory = bouncerDialogFactory;
        this.authenticationInteractor = authenticationInteractor;
        this.viewMediatorCallback = viewMediatorCallback;
        this.selectedUserInteractor = selectedUserInteractor;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComposeBouncerDependencies)) {
            return false;
        }
        ComposeBouncerDependencies composeBouncerDependencies = (ComposeBouncerDependencies) obj;
        return Intrinsics.areEqual(this.legacyInteractor, composeBouncerDependencies.legacyInteractor) && Intrinsics.areEqual(this.viewModel, composeBouncerDependencies.viewModel) && Intrinsics.areEqual(this.dialogFactory, composeBouncerDependencies.dialogFactory) && Intrinsics.areEqual(this.authenticationInteractor, composeBouncerDependencies.authenticationInteractor) && Intrinsics.areEqual(this.viewMediatorCallback, composeBouncerDependencies.viewMediatorCallback) && Intrinsics.areEqual(this.selectedUserInteractor, composeBouncerDependencies.selectedUserInteractor);
    }

    public final int hashCode() {
        int hashCode = (this.authenticationInteractor.hashCode() + ((this.dialogFactory.hashCode() + ((this.viewModel.hashCode() + (this.legacyInteractor.hashCode() * 31)) * 31)) * 31)) * 31;
        ViewMediatorCallback viewMediatorCallback = this.viewMediatorCallback;
        return this.selectedUserInteractor.hashCode() + ((hashCode + (viewMediatorCallback == null ? 0 : viewMediatorCallback.hashCode())) * 31);
    }

    public final String toString() {
        return "ComposeBouncerDependencies(legacyInteractor=" + this.legacyInteractor + ", viewModel=" + this.viewModel + ", dialogFactory=" + this.dialogFactory + ", authenticationInteractor=" + this.authenticationInteractor + ", viewMediatorCallback=" + this.viewMediatorCallback + ", selectedUserInteractor=" + this.selectedUserInteractor + ")";
    }
}
