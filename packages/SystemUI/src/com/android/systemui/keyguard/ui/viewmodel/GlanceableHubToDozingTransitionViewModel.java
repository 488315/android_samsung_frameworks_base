package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.dagger.GlanceableHubBlurComponent;
import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.scene.shared.model.Scenes;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubToDozingTransitionViewModel implements GlanceableHubTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public GlanceableHubToDozingTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, GlanceableHubBlurComponent.Factory factory) {
        FromDozingTransitionInteractor.Companion.getClass();
        long j = FromDozingTransitionInteractor.TO_GLANCEABLE_HUB_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.DOZING;
        SceneKey sceneKey = Scenes.Communal;
        companion.getClass();
        this.windowBlurRadius = ((DaggerReferenceGlobalRootComponent.GlanceableHubBlurComponentImpl) factory.create(keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)).setupWithoutSceneContainer(new Edge.StateToState(KeyguardState.GLANCEABLE_HUB, keyguardState)))).getBlurProvider().exitBlurRadius;
    }
}
