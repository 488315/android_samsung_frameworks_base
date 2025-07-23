package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPatternBouncerKt$SecPatternBouncer$3$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animatable;
    final /* synthetic */ boolean $isSelected;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPatternBouncerKt$SecPatternBouncer$3$1$2$1(boolean z, Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
        super(2, continuation);
        this.$isSelected = z;
        this.$animatable = animatable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPatternBouncerKt$SecPatternBouncer$3$1$2$1(this.$isSelected, this.$animatable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$SecPatternBouncer$3$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0045, code lost:
    
        if (r12 == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006e, code lost:
    
        if (r12 == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L1c
            if (r1 == r2) goto L18
            if (r1 != r3) goto L10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L71
        L10:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L18:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L48
        L1c:
            kotlin.ResultKt.throwOnFailure(r12)
            boolean r12 = r11.$isSelected
            r1 = 0
            if (r12 == 0) goto L4b
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r11.$animatable
            java.lang.Float r5 = new java.lang.Float
            r12 = 1069547520(0x3fc00000, float:1.5)
            r5.<init>(r12)
            com.android.compose.animation.Easings r12 = com.android.compose.animation.Easings.INSTANCE
            r12.getClass()
            com.android.compose.animation.Easings$fromInterpolator$1 r12 = com.android.compose.animation.Easings.StandardAccelerate
            r6 = 83
            androidx.compose.animation.core.TweenSpec r6 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r6, r1, r12, r3)
            r11.label = r2
            r8 = 0
            r10 = 12
            r7 = 0
            r9 = r11
            java.lang.Object r12 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)
            if (r12 != r0) goto L48
            goto L70
        L48:
            androidx.compose.animation.core.AnimationResult r12 = (androidx.compose.animation.core.AnimationResult) r12
            goto L73
        L4b:
            r6 = r11
            r11 = r1
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r1 = r6.$animatable
            java.lang.Float r2 = new java.lang.Float
            r12 = 1065353216(0x3f800000, float:1.0)
            r2.<init>(r12)
            com.android.compose.animation.Easings r12 = com.android.compose.animation.Easings.INSTANCE
            r12.getClass()
            com.android.compose.animation.Easings$fromInterpolator$1 r12 = com.android.compose.animation.Easings.StandardDecelerate
            r4 = 750(0x2ee, float:1.051E-42)
            androidx.compose.animation.core.TweenSpec r11 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r4, r11, r12, r3)
            r6.label = r3
            r5 = 0
            r7 = 12
            r4 = 0
            r3 = r11
            java.lang.Object r12 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r12 != r0) goto L71
        L70:
            return r0
        L71:
            androidx.compose.animation.core.AnimationResult r12 = (androidx.compose.animation.core.AnimationResult) r12
        L73:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$3$1$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
