package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerToOccludedTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;

    public AlternateBouncerToOccludedTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromAlternateBouncerTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromAlternateBouncerTransitionInteractor.TO_OCCLUDED_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.ALTERNATE_BOUNCER, KeyguardState.OCCLUDED));
        this.lockscreenAlpha = m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f);
        this.deviceEntryParentViewAlpha = m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
