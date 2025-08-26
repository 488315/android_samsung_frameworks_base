package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.MotionScheme$Companion$standard$1;
import androidx.compose.runtime.MonotonicFrameClockKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class AnimateContentKt$animateContent$1 extends SuspendLambda implements Function1 {
    final /* synthetic */ MutableSceneTransitionLayoutStateImpl $layoutState;
    final /* synthetic */ OneOffAnimation $oneOffAnimation;
    final /* synthetic */ float $targetProgress;
    final /* synthetic */ TransitionState.Transition $transition;
    float F$0;
    Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimateContentKt$animateContent$1(TransitionState.Transition transition, MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, float f, OneOffAnimation oneOffAnimation, Continuation continuation) {
        super(1, continuation);
        this.$transition = transition;
        this.$layoutState = mutableSceneTransitionLayoutStateImpl;
        this.$targetProgress = f;
        this.$oneOffAnimation = oneOffAnimation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new AnimateContentKt$animateContent$1(this.$transition, this.$layoutState, this.$targetProgress, this.$oneOffAnimation, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((AnimateContentKt$animateContent$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b5, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r6, r7, r8, r9, null, r13, 8) != r0) goto L41;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        AnimationSpec animationSpec;
        float progressVelocity;
        Animatable animatable;
        Animatable animatable2;
        Float f;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnimationSpec animationSpec2 = this.$transition.transformationSpec.progressSpec;
            if (animationSpec2 == null) {
                animationSpec2 = ((MotionScheme$Companion$standard$1) this.$layoutState.motionScheme).defaultSpatialSpec;
            }
            animationSpec = animationSpec2;
            SpringSpec springSpec = animationSpec instanceof SpringSpec ? (SpringSpec) animationSpec : null;
            float fFloatValue = (springSpec == null || (f = (Float) springSpec.visibilityThreshold) == null) ? 0.001f : f.floatValue();
            TransitionState.Transition transition = this.$transition.replacedTransition;
            float progress = transition != null ? transition.getProgress() : 0.0f;
            progressVelocity = transition != null ? transition.getProgressVelocity() : 0.0f;
            Animatable Animatable = AnimatableKt.Animatable(progress, fFloatValue);
            this.$oneOffAnimation.animatable = Animatable;
            if (this.$layoutState.deferTransitionProgress) {
                AnimateContentKt$animateContent$1$$ExternalSyntheticLambda0 animateContentKt$animateContent$1$$ExternalSyntheticLambda0 = new AnimateContentKt$animateContent$1$$ExternalSyntheticLambda0();
                this.L$0 = animationSpec;
                this.L$1 = Animatable;
                this.F$0 = progressVelocity;
                this.label = 1;
                if (MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(animateContentKt$animateContent$1$$ExternalSyntheticLambda0, this) != coroutineSingletons) {
                    animatable2 = Animatable;
                }
                return coroutineSingletons;
            }
            animatable = Animatable;
            AnimationSpec animationSpec3 = animationSpec;
            Float f2 = new Float(this.$targetProgress);
            Float f3 = new Float(progressVelocity);
            this.L$0 = null;
            this.L$1 = null;
            this.label = 2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            progressVelocity = this.F$0;
            animatable2 = (Animatable) this.L$1;
            animationSpec = (AnimationSpec) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        animatable = animatable2;
        AnimationSpec animationSpec32 = animationSpec;
        Float f22 = new Float(this.$targetProgress);
        Float f32 = new Float(progressVelocity);
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
