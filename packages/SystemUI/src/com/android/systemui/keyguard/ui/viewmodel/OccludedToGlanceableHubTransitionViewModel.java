package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.dagger.GlanceableHubBlurComponent;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
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
public final class OccludedToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition, GlanceableHubTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public OccludedToGlanceableHubTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, GlanceableHubBlurComponent.Factory factory) {
        FromOccludedTransitionInteractor.Companion.getClass();
        long j = FromOccludedTransitionInteractor.TO_GLANCEABLE_HUB_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.OCCLUDED;
        SceneKey sceneKey = Scenes.Communal;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)));
        this.deviceEntryParentViewAlpha = m.immediatelyTransitionTo(1.0f);
        this.windowBlurRadius = ((DaggerReferenceGlobalRootComponent.GlanceableHubBlurComponentImpl) factory.create(m)).getBlurProvider().enterBlurRadius;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
