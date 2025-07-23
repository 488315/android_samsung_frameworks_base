package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShowOrHideOverlaySwipeTransition extends TransitionState.Transition.ShowOrHideOverlay {
    public final SwipeAnimation gestureContext;
    public final boolean isInitiatedByUserInput;
    public final TransitionKey key;
    public final SwipeAnimation swipeAnimation;

    public ShowOrHideOverlaySwipeTransition(SwipeAnimation swipeAnimation, OverlayKey overlayKey, SceneKey sceneKey, TransitionKey transitionKey, ShowOrHideOverlaySwipeTransition showOrHideOverlaySwipeTransition) {
        super(overlayKey, sceneKey, swipeAnimation.fromContent, swipeAnimation.toContent, showOrHideOverlaySwipeTransition);
        this.swipeAnimation = swipeAnimation;
        this.key = transitionKey;
        swipeAnimation.contentTransition = this;
        this.isInitiatedByUserInput = true;
        this.gestureContext = swipeAnimation;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final void freezeAndAnimateToCurrentState() {
        this.swipeAnimation.freezeAndAnimateToCurrentState();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final TransitionKey getKey() {
        return this.key;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final float getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        return this.swipeAnimation.getPreviewProgress();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final float getProgress() {
        return this.swipeAnimation.getProgress();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final float getProgressVelocity() {
        return this.swipeAnimation.getProgressVelocity();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition.ShowOrHideOverlay
    public final boolean isEffectivelyShown() {
        return Intrinsics.areEqual(this.swipeAnimation.getCurrentContent(), this.overlay);
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        return this.swipeAnimation.isInPreviewStage();
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isInitiatedByUserInput() {
        return this.isInitiatedByUserInput;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final boolean isUserInputOngoing() {
        return ((Animatable) ((SnapshotMutableStateImpl) this.swipeAnimation.offsetAnimation$delegate).getValue()) == null;
    }

    @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition
    public final Object run(Continuation continuation) {
        Object run = this.swipeAnimation.run((ContinuationImpl) continuation);
        return run == CoroutineSingletons.COROUTINE_SUSPENDED ? run : Unit.INSTANCE;
    }
}
