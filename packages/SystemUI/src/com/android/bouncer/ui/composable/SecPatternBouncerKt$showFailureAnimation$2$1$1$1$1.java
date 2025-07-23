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
final class SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $dotScaleAnimatable;
    final /* synthetic */ int $rowIndex;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1(Animatable<Float, AnimationVector1D> animatable, int i, Continuation continuation) {
        super(2, continuation);
        this.$dotScaleAnimatable = animatable;
        this.$rowIndex = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1(this.$dotScaleAnimatable, this.$rowIndex, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x006a, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, null, null, r9, 12) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x006c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0046, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, null, null, r9, 12) == r0) goto L15;
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
            if (r1 == 0) goto L1d
            if (r1 == r2) goto L18
            if (r1 != r3) goto L10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L6d
        L10:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L18:
            kotlin.ResultKt.throwOnFailure(r12)
            r9 = r11
            goto L49
        L1d:
            kotlin.ResultKt.throwOnFailure(r12)
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r11.$dotScaleAnimatable
            java.lang.Float r5 = new java.lang.Float
            r12 = 1061158912(0x3f400000, float:0.75)
            r5.<init>(r12)
            int r12 = r11.$rowIndex
            int r12 = r12 * 33
            com.android.compose.animation.Easings r1 = com.android.compose.animation.Easings.INSTANCE
            r1.getClass()
            com.android.compose.animation.Easings$fromInterpolator$1 r1 = com.android.compose.animation.Easings.Linear
            androidx.compose.animation.core.TweenSpec r6 = new androidx.compose.animation.core.TweenSpec
            r7 = 50
            r6.<init>(r7, r12, r1)
            r11.label = r2
            r8 = 0
            r10 = 12
            r7 = 0
            r9 = r11
            java.lang.Object r11 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)
            if (r11 != r0) goto L49
            goto L6c
        L49:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r9.$dotScaleAnimatable
            java.lang.Float r5 = new java.lang.Float
            r11 = 1065353216(0x3f800000, float:1.0)
            r5.<init>(r11)
            com.android.compose.animation.Easings r11 = com.android.compose.animation.Easings.INSTANCE
            r11.getClass()
            com.android.compose.animation.Easings$fromInterpolator$1 r11 = com.android.compose.animation.Easings.Standard
            r12 = 617(0x269, float:8.65E-43)
            r1 = 0
            androidx.compose.animation.core.TweenSpec r6 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r12, r1, r11, r3)
            r9.label = r3
            r8 = 0
            r10 = 12
            r7 = 0
            java.lang.Object r11 = androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, r7, r8, r9, r10)
            if (r11 != r0) goto L6d
        L6c:
            return r0
        L6d:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
