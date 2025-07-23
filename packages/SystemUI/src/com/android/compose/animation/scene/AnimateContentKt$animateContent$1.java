package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        return ((AnimateContentKt$animateContent$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x00b5, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r6, r7, r8, r9, null, r13, 8) != r0) goto L41;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r13.label
            r2 = 2
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L29
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lb8
        L12:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L1a:
            float r1 = r13.F$0
            java.lang.Object r3 = r13.L$1
            androidx.compose.animation.core.Animatable r3 = (androidx.compose.animation.core.Animatable) r3
            java.lang.Object r5 = r13.L$0
            androidx.compose.animation.core.AnimationSpec r5 = (androidx.compose.animation.core.AnimationSpec) r5
            kotlin.ResultKt.throwOnFailure(r14)
            goto L96
        L29:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.compose.animation.scene.content.state.TransitionState$Transition r14 = r13.$transition
            com.android.compose.animation.scene.TransformationSpecImpl r14 = r14.transformationSpec
            androidx.compose.animation.core.AnimationSpec r14 = r14.progressSpec
            if (r14 != 0) goto L3c
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl r14 = r13.$layoutState
            androidx.compose.material3.MotionScheme r14 = r14.motionScheme
            androidx.compose.material3.MotionScheme$Companion$standard$1 r14 = (androidx.compose.material3.MotionScheme$Companion$standard$1) r14
            androidx.compose.animation.core.SpringSpec r14 = r14.defaultSpatialSpec
        L3c:
            r5 = r14
            boolean r14 = r5 instanceof androidx.compose.animation.core.SpringSpec
            if (r14 == 0) goto L45
            r14 = r5
            androidx.compose.animation.core.SpringSpec r14 = (androidx.compose.animation.core.SpringSpec) r14
            goto L46
        L45:
            r14 = r4
        L46:
            if (r14 == 0) goto L53
            java.lang.Object r14 = r14.visibilityThreshold
            java.lang.Float r14 = (java.lang.Float) r14
            if (r14 == 0) goto L53
            float r14 = r14.floatValue()
            goto L56
        L53:
            r14 = 981668463(0x3a83126f, float:0.001)
        L56:
            com.android.compose.animation.scene.content.state.TransitionState$Transition r1 = r13.$transition
            com.android.compose.animation.scene.content.state.TransitionState$Transition r1 = r1.replacedTransition
            r6 = 0
            if (r1 == 0) goto L62
            float r7 = r1.getProgress()
            goto L63
        L62:
            r7 = r6
        L63:
            if (r1 == 0) goto L6a
            float r1 = r1.getProgressVelocity()
            goto L6b
        L6a:
            r1 = r6
        L6b:
            androidx.compose.animation.core.Animatable r14 = androidx.compose.animation.core.AnimatableKt.Animatable(r7, r14)
            com.android.compose.animation.scene.OneOffAnimation r6 = r13.$oneOffAnimation
            r6.animatable = r14
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl r6 = r13.$layoutState
            boolean r6 = r6.deferTransitionProgress
            if (r6 == 0) goto L99
            com.android.compose.animation.scene.AnimateContentKt$animateContent$1$$ExternalSyntheticLambda0 r6 = new com.android.compose.animation.scene.AnimateContentKt$animateContent$1$$ExternalSyntheticLambda0
            r6.<init>()
            r13.L$0 = r5
            r13.L$1 = r14
            r13.F$0 = r1
            r13.label = r3
            kotlin.coroutines.CoroutineContext r3 = r13.getContext()
            androidx.compose.runtime.MonotonicFrameClock r3 = androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r3)
            java.lang.Object r3 = r3.withFrameNanos(r6, r13)
            if (r3 != r0) goto L95
            goto Lb7
        L95:
            r3 = r14
        L96:
            r6 = r3
        L97:
            r8 = r5
            goto L9b
        L99:
            r6 = r14
            goto L97
        L9b:
            float r14 = r13.$targetProgress
            java.lang.Float r7 = new java.lang.Float
            r7.<init>(r14)
            java.lang.Float r9 = new java.lang.Float
            r9.<init>(r1)
            r13.L$0 = r4
            r13.L$1 = r4
            r13.label = r2
            r10 = 0
            r12 = 8
            r11 = r13
            java.lang.Object r13 = androidx.compose.animation.core.Animatable.animateTo$default(r6, r7, r8, r9, r10, r11, r12)
            if (r13 != r0) goto Lb8
        Lb7:
            return r0
        Lb8:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.AnimateContentKt$animateContent$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
