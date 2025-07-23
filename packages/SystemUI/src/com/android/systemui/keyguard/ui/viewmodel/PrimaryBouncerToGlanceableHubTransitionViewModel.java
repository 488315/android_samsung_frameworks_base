package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrimaryBouncerToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public PrimaryBouncerToGlanceableHubTransitionViewModel(BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, CommunalSettingsInteractor communalSettingsInteractor) {
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_GLANCEABLE_HUB_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.PRIMARY_BOUNCER, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, Edge.INVALID));
        this.transitionAnimation = m;
        this.deviceEntryParentViewAlpha = m.immediatelyTransitionTo(1.0f);
        this.windowBlurRadius = m.immediatelyTransitionTo(blurConfig.minBlurRadiusPx);
        this.notificationBlurRadius = m.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
