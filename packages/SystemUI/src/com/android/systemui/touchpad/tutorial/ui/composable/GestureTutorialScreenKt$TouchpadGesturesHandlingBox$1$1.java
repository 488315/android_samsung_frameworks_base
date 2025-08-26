package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $easterEggTriggered;
    final /* synthetic */ Function0 $onEasterEggFinished;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $rotationAnimation;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1(boolean z, Animatable<Float, AnimationVector1D> animatable, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$easterEggTriggered = z;
        this.$rotationAnimation = animatable;
        this.$onEasterEggFinished = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1(this.$easterEggTriggered, this.$rotationAnimation, this.$onEasterEggFinished, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005a, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r3, r4, r5, null, null, r8, 12) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1 gestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$easterEggTriggered || this.$rotationAnimation.isRunning()) {
                Animatable<Float, AnimationVector1D> animatable = this.$rotationAnimation;
                Float f = new Float(0.0f);
                this.label = 1;
                if (animatable.snapTo(f, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            gestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1 = this;
            gestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1.$onEasterEggFinished.invoke();
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        Animatable<Float, AnimationVector1D> animatable2 = this.$rotationAnimation;
        Float f2 = new Float(360.0f);
        TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(2000, 0, null, 6);
        this.label = 2;
        gestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1 = this;
    }
}
