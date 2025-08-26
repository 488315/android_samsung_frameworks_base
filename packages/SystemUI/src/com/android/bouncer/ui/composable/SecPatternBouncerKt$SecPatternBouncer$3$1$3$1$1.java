package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.runtime.State;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003e, code lost:
    
        if (r9.snapTo(r1, r8) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, null, null, r8, 12) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (Intrinsics.areEqual(this.$dot, (PatternDotViewModel) this.$currentDot$delegate.getValue())) {
                Animatable<Float, AnimationVector1D> animatable = this.$line;
                Float f = new Float(1.0f);
                this.label = 1;
            } else {
                Animatable<Float, AnimationVector1D> animatable2 = this.$line;
                Float f2 = new Float(0.0f);
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(this.$lineFadeOutAnimationDurationMs, this.$lineFadeOutAnimationDelayMs, null, 4);
                this.label = 2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
            Unit unit = Unit.INSTANCE;
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
