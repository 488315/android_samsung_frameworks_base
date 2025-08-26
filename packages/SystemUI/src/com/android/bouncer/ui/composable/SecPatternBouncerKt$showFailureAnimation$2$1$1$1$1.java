package com.android.bouncer.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import com.android.compose.animation.Easings;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, r6, null, null, r9, 12) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SecPatternBouncerKt$showFailureAnimation$2$1$1$1$1 secPatternBouncerKt$showFailureAnimation$2$1$1$1$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Animatable<Float, AnimationVector1D> animatable = this.$dotScaleAnimatable;
            Float f = new Float(0.75f);
            int i2 = this.$rowIndex * 33;
            Easings.INSTANCE.getClass();
            TweenSpec tweenSpec = new TweenSpec(50, i2, Easings.Linear);
            this.label = 1;
            secPatternBouncerKt$showFailureAnimation$2$1$1$1$1 = this;
            if (Animatable.animateTo$default(animatable, f, tweenSpec, null, null, secPatternBouncerKt$showFailureAnimation$2$1$1$1$1, 12) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        secPatternBouncerKt$showFailureAnimation$2$1$1$1$1 = this;
        Animatable<Float, AnimationVector1D> animatable2 = secPatternBouncerKt$showFailureAnimation$2$1$1$1$1.$dotScaleAnimatable;
        Float f2 = new Float(1.0f);
        Easings.INSTANCE.getClass();
        TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(617, 0, Easings.Standard, 2);
        secPatternBouncerKt$showFailureAnimation$2$1$1$1$1.label = 2;
    }
}
