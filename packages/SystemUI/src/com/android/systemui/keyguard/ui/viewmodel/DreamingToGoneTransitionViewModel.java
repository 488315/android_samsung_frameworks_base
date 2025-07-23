package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DreamingToGoneTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;

    public DreamingToGoneTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromDreamingTransitionInteractor.Companion.getClass();
        long j = FromDreamingTransitionInteractor.TO_GONE_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        this.lockscreenAlpha = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)).setupWithoutSceneContainer(new Edge.StateToState(keyguardState, KeyguardState.GONE)).immediatelyTransitionTo(0.0f);
    }
}
