package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x005a, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r3, r4, r5, null, null, r8, 12) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003a, code lost:
    
        if (r11.snapTo(r1, r10) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r11)
            r8 = r10
            goto L5d
        L11:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L19:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L3d
        L1d:
            kotlin.ResultKt.throwOnFailure(r11)
            boolean r11 = r10.$easterEggTriggered
            if (r11 != 0) goto L2c
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r11 = r10.$rotationAnimation
            boolean r11 = r11.isRunning()
            if (r11 == 0) goto L62
        L2c:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r11 = r10.$rotationAnimation
            java.lang.Float r1 = new java.lang.Float
            r4 = 0
            r1.<init>(r4)
            r10.label = r3
            java.lang.Object r11 = r11.snapTo(r1, r10)
            if (r11 != r0) goto L3d
            goto L5c
        L3d:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r3 = r10.$rotationAnimation
            java.lang.Float r4 = new java.lang.Float
            r11 = 1135869952(0x43b40000, float:360.0)
            r4.<init>(r11)
            r11 = 2000(0x7d0, float:2.803E-42)
            r1 = 0
            r5 = 0
            r6 = 6
            androidx.compose.animation.core.TweenSpec r5 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r11, r5, r1, r6)
            r10.label = r2
            r7 = 0
            r9 = 12
            r6 = 0
            r8 = r10
            java.lang.Object r10 = androidx.compose.animation.core.Animatable.animateTo$default(r3, r4, r5, r6, r7, r8, r9)
            if (r10 != r0) goto L5d
        L5c:
            return r0
        L5d:
            kotlin.jvm.functions.Function0 r10 = r8.$onEasterEggFinished
            r10.invoke()
        L62:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.composable.GestureTutorialScreenKt$TouchpadGesturesHandlingBox$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
