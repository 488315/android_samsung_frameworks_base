package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardJankViewModel {
    public final Flow aodToLockscreenTransition;
    public final Flow goneToAodTransition;
    public final Flow lockscreenToAodTransition;

    public KeyguardJankViewModel(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        new Edge.ContentToState(sceneKey, keyguardState);
        Edge.StateToState stateToState = new Edge.StateToState(KeyguardState.GONE, keyguardState);
        keyguardTransitionInteractor.getClass();
        this.goneToAodTransition = keyguardTransitionInteractor.transition(stateToState);
        SceneKey sceneKey2 = Scenes.Lockscreen;
        new Edge.ContentToState(sceneKey2, keyguardState);
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        this.lockscreenToAodTransition = keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState2, keyguardState));
        new Edge.StateToContent(keyguardState, sceneKey2);
        this.aodToLockscreenTransition = keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState, keyguardState2));
    }
}
