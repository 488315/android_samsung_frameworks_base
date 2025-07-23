package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.dagger.GlanceableHubBlurComponent;
import com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.scene.shared.model.Scenes;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubToOccludedTransitionViewModel implements DeviceEntryIconTransition, GlanceableHubTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public GlanceableHubToOccludedTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, GlanceableHubBlurComponent.Factory factory) {
        FromGlanceableHubTransitionInteractor.Companion.getClass();
        long j = FromGlanceableHubTransitionInteractor.TO_OCCLUDED_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Communal;
        KeyguardState keyguardState = KeyguardState.OCCLUDED;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GLANCEABLE_HUB, keyguardState, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.ContentToState(sceneKey, keyguardState)));
        this.deviceEntryParentViewAlpha = m.immediatelyTransitionTo(0.0f);
        this.windowBlurRadius = ((DaggerReferenceGlobalRootComponent.GlanceableHubBlurComponentImpl) factory.create(m)).getBlurProvider().exitBlurRadius;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
