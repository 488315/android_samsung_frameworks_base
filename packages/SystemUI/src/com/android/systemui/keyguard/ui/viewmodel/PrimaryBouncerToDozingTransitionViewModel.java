package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PrimaryBouncerToDozingTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public PrimaryBouncerToDozingTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_DOZING_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Bouncer;
        KeyguardState keyguardState = KeyguardState.DOZING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.PRIMARY_BOUNCER, keyguardState, keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.SceneToState(sceneKey, keyguardState)));
        this.transitionAnimation = m;
        this.deviceEntryBackgroundViewAlpha = m.immediatelyTransitionTo(0.0f);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new PrimaryBouncerToDozingTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
