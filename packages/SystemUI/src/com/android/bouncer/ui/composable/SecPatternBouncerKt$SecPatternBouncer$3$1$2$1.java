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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0045, code lost:
    
        if (r12 == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x006e, code lost:
    
        if (r12 == r0) goto L18;
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
            if (this.$isSelected) {
                Animatable<Float, AnimationVector1D> animatable = this.$animatable;
                Float f = new Float(1.5f);
                Easings.INSTANCE.getClass();
                TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default(83, 0, Easings.StandardAccelerate, 2);
                this.label = 1;
                obj = Animatable.animateTo$default(animatable, f, tweenSpecTween$default, null, null, this, 12);
            } else {
                Animatable<Float, AnimationVector1D> animatable2 = this.$animatable;
                Float f2 = new Float(1.0f);
                Easings.INSTANCE.getClass();
                TweenSpec tweenSpecTween$default2 = AnimationSpecKt.tween$default(750, 0, Easings.StandardDecelerate, 2);
                this.label = 2;
                obj = Animatable.animateTo$default(animatable2, f2, tweenSpecTween$default2, null, null, this, 12);
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
