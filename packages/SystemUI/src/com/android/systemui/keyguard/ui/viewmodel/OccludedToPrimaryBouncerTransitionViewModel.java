package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OccludedToPrimaryBouncerTransitionViewModel implements PrimaryBouncerTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final ChannelLimitedFlowMerge notificationBlurRadius;
    public final ChannelLimitedFlowMerge windowBlurRadius;

    public OccludedToPrimaryBouncerTransitionViewModel(ShadeDependentFlows shadeDependentFlows, BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromOccludedTransitionInteractor.Companion.getClass();
        long j = FromOccludedTransitionInteractor.TO_PRIMARY_BOUNCER_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.OCCLUDED, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, Edge.INVALID));
        this.lockscreenAlpha = m.immediatelyTransitionTo(0.0f);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo = m.immediatelyTransitionTo(blurConfig.minBlurRadiusPx);
        float f = blurConfig.maxBlurRadiusPx;
        this.windowBlurRadius = shadeDependentFlows.transitionFlow(immediatelyTransitionTo, m.immediatelyTransitionTo(f));
        this.notificationBlurRadius = shadeDependentFlows.transitionFlow(m.immediatelyTransitionTo(f), EmptyFlow.INSTANCE);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
