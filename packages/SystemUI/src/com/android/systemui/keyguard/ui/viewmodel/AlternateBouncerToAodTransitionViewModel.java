package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerToAodTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public AlternateBouncerToAodTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromAlternateBouncerTransitionInteractor.Companion.getClass();
        long j = FromAlternateBouncerTransitionInteractor.TO_AOD_DURATION;
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.ALTERNATE_BOUNCER, KeyguardState.AOD));
        this.transitionAnimation = m2598setupVtjQ1oo;
        this.deviceEntryBackgroundViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, j, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new AlternateBouncerToAodTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
