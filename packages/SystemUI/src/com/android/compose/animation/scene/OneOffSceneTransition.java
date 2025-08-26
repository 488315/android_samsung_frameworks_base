package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class OneOffSceneTransition extends TransitionState.Transition.ChangeScene {
    public final SceneKey currentScene;
    public final boolean isInitiatedByUserInput;
    public final TransitionKey key;
    public final OneOffAnimation oneOffAnimation;

    public OneOffSceneTransition(TransitionKey transitionKey, SceneKey sceneKey, SceneKey sceneKey2, SceneKey sceneKey3, boolean z, TransitionState.Transition transition, OneOffAnimation oneOffAnimation) {
        super(sceneKey, sceneKey2, transition);
        this.key = transitionKey;
        this.currentScene = sceneKey3;
        this.isInitiatedByUserInput = z;
        this.oneOffAnimation = oneOffAnimation;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final void freezeAndAnimateToCurrentState() {
        this.oneOffAnimation.getClass();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState
    public final SceneKey getCurrentScene() {
        return this.currentScene;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final TransitionKey getKey() {
        return this.key;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final float getProgress() {
        Animatable animatable = this.oneOffAnimation.animatable;
        if (animatable == null) {
            animatable = null;
        }
        return ((Number) animatable.internalState.getValue()).floatValue();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final float getProgressVelocity() {
        Animatable animatable = this.oneOffAnimation.animatable;
        if (animatable == null) {
            animatable = null;
        }
        return ((Number) animatable.getVelocity()).floatValue();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isInitiatedByUserInput() {
        return this.isInitiatedByUserInput;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isUserInputOngoing() {
        return false;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final Object run(Continuation continuation) {
        Function1 function1 = this.oneOffAnimation.onRun;
        if (function1 == null) {
            function1 = null;
        }
        Object objMo781invoke = ((AnimateContentKt$animateContent$1) function1).mo781invoke(continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objMo781invoke != coroutineSingletons) {
            objMo781invoke = Unit.INSTANCE;
        }
        return objMo781invoke == coroutineSingletons ? objMo781invoke : Unit.INSTANCE;
    }
}
