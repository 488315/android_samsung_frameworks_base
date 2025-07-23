package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrimaryBouncerToOccludedTransitionViewModel implements PrimaryBouncerTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final ChannelLimitedFlowMerge windowBlurRadius;

    public PrimaryBouncerToOccludedTransitionViewModel(ShadeDependentFlows shadeDependentFlows, BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_OCCLUDED_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.PRIMARY_BOUNCER, KeyguardState.OCCLUDED, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, Edge.INVALID));
        this.windowBlurRadius = shadeDependentFlows.transitionFlow(m.immediatelyTransitionTo(blurConfig.minBlurRadiusPx), m.immediatelyTransitionTo(blurConfig.minBlurRadiusPx));
        this.notificationBlurRadius = m.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
