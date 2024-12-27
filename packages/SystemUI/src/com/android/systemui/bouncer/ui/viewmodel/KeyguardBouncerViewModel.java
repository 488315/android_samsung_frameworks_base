package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$3;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$3;
import com.android.systemui.bouncer.ui.BouncerView;
import com.android.systemui.bouncer.ui.BouncerViewImpl;
import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import java.lang.ref.WeakReference;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBouncerViewModel {
    public final ReadonlyStateFlow bouncerExpansionAmount;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 bouncerShowMessage;
    public final PrimaryBouncerInteractor interactor;
    public final ReadonlyStateFlow isInflated;
    public final PrimaryBouncerInteractor$special$$inlined$map$3 isInteractable;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardAuthenticated;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardPosition;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 startDisappearAnimation;
    public final PrimaryBouncerInteractor$special$$inlined$map$2 startingToHide;
    public final PrimaryBouncerInteractor$special$$inlined$filter$3 updateResources;
    public final BouncerView view;

    public KeyguardBouncerViewModel(BouncerView bouncerView, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.view = bouncerView;
        this.interactor = primaryBouncerInteractor;
        this.bouncerExpansionAmount = primaryBouncerInteractor.panelExpansionAmount;
        this.isInteractable = primaryBouncerInteractor.isInteractable;
        this.isShowing = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(primaryBouncerInteractor.isShowing, primaryBouncerInteractor.primaryBouncerUpdating, new KeyguardBouncerViewModel$isShowing$1(null));
        this.startingToHide = primaryBouncerInteractor.startingToHide;
        this.startDisappearAnimation = primaryBouncerInteractor.startingDisappearAnimation;
        this.keyguardPosition = primaryBouncerInteractor.keyguardPosition;
        this.updateResources = primaryBouncerInteractor.resourceUpdateRequests;
        this.bouncerShowMessage = primaryBouncerInteractor.showMessage;
        this.keyguardAuthenticated = primaryBouncerInteractor.keyguardAuthenticatedBiometrics;
        this.isInflated = primaryBouncerInteractor.isInflated;
    }

    public final void setBouncerViewDelegate(KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1) {
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.view;
        bouncerViewImpl.getClass();
        bouncerViewImpl._delegate = new WeakReference(keyguardBouncerViewBinder$bind$delegate$1);
        PrimaryBouncerInteractor primaryBouncerInteractor = this.interactor;
        if (primaryBouncerInteractor.pendingBouncerViewDelegate) {
            primaryBouncerInteractor.show(true);
            primaryBouncerInteractor.pendingBouncerViewDelegate = false;
        }
    }
}
