package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.State;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<PatternDotViewModel> $currentDot$delegate;
    final /* synthetic */ PatternDotViewModel $dot;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $line;
    final /* synthetic */ int $lineFadeOutAnimationDelayMs;
    final /* synthetic */ int $lineFadeOutAnimationDurationMs;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1(PatternDotViewModel patternDotViewModel, Animatable<Float, AnimationVector1D> animatable, int i, int i2, State<PatternDotViewModel> state, Continuation continuation) {
        super(2, continuation);
        this.$dot = patternDotViewModel;
        this.$line = animatable;
        this.$lineFadeOutAnimationDurationMs = i;
        this.$lineFadeOutAnimationDelayMs = i2;
        this.$currentDot$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1(this.$dot, this.$line, this.$lineFadeOutAnimationDurationMs, this.$lineFadeOutAnimationDelayMs, this.$currentDot$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x003e, code lost:
    
        if (r9.snapTo(r1, r8) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0062, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, null, null, r8, 12) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r9)
            goto L65
        L10:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L18:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L41
        L1c:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel r9 = r8.$dot
            androidx.compose.runtime.State<com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel> r1 = r8.$currentDot$delegate
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel) r1
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r1)
            if (r9 == 0) goto L44
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r9 = r8.$line
            java.lang.Float r1 = new java.lang.Float
            r2 = 1065353216(0x3f800000, float:1.0)
            r1.<init>(r2)
            r8.label = r3
            java.lang.Object r8 = r9.snapTo(r1, r8)
            if (r8 != r0) goto L41
            goto L64
        L41:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            goto L65
        L44:
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r1 = r8.$line
            r9 = r2
            java.lang.Float r2 = new java.lang.Float
            r3 = 0
            r2.<init>(r3)
            int r3 = r8.$lineFadeOutAnimationDurationMs
            int r4 = r8.$lineFadeOutAnimationDelayMs
            r5 = 4
            r6 = 0
            androidx.compose.animation.core.TweenSpec r3 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r3, r4, r6, r5)
            r8.label = r9
            r5 = 0
            r7 = 12
            r4 = 0
            r6 = r8
            java.lang.Object r8 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto L65
        L64:
            return r0
        L65:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPatternBouncerKt$SecPatternBouncer$3$1$3$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
