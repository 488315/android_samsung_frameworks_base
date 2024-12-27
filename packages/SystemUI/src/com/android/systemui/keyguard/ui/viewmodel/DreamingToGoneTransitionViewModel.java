package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.lockscreenAlpha = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.StateToScene(keyguardState, sceneKey)).setupWithoutSceneContainer(new Edge.StateToState(keyguardState, KeyguardState.GONE)).immediatelyTransitionTo(0.0f);
    }
}
