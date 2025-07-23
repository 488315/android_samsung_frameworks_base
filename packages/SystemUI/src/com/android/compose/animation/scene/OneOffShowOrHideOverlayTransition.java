package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OneOffShowOrHideOverlayTransition extends TransitionState.Transition.ShowOrHideOverlay {
    public final boolean isEffectivelyShown;
    public final TransitionKey key;
    public final OneOffAnimation oneOffAnimation;

    public OneOffShowOrHideOverlayTransition(OverlayKey overlayKey, SceneKey sceneKey, ContentKey contentKey, ContentKey contentKey2, boolean z, TransitionKey transitionKey, TransitionState.Transition transition, OneOffAnimation oneOffAnimation) {
        super(overlayKey, sceneKey, contentKey, contentKey2, transition);
        this.isEffectivelyShown = z;
        this.key = transitionKey;
        this.oneOffAnimation = oneOffAnimation;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final void freezeAndAnimateToCurrentState() {
        this.oneOffAnimation.getClass();
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

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition.ShowOrHideOverlay
    public final boolean isEffectivelyShown() {
        return this.isEffectivelyShown;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isInitiatedByUserInput() {
        return false;
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
        Object mo779invoke = ((AnimateContentKt$animateContent$1) function1).mo779invoke(continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (mo779invoke != coroutineSingletons) {
            mo779invoke = Unit.INSTANCE;
        }
        return mo779invoke == coroutineSingletons ? mo779invoke : Unit.INSTANCE;
    }
}
