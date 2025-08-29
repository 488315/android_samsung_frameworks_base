package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.scene.shared.model.Overlays;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class AodToPrimaryBouncerTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final EmptyFlow lockscreenAlpha;
    public final EmptyFlow notificationAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public AodToPrimaryBouncerTransitionViewModel(BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromAodTransitionInteractor.Companion.getClass();
        long j = FromAodTransitionInteractor.TO_PRIMARY_BOUNCER_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.AOD;
        OverlayKey overlayKey = Overlays.Bouncer;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, overlayKey)));
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
        this.windowBlurRadius = flowBuilderM.immediatelyTransitionTo(blurConfig.maxBlurRadiusPx);
        EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
        this.lockscreenAlpha = emptyFlow;
        this.notificationAlpha = emptyFlow;
        this.notificationBlurRadius = flowBuilderM.immediatelyTransitionTo(0.0f);
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
