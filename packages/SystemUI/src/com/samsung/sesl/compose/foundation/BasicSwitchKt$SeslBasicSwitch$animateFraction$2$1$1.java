package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class BasicSwitchKt$SeslBasicSwitch$animateFraction$2$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Float> $fraction$delegate;
    final /* synthetic */ Animatable<Float, AnimationVector1D> $fractionAnimatable;
    final /* synthetic */ float $target;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicSwitchKt$SeslBasicSwitch$animateFraction$2$1$1(float f, Animatable<Float, AnimationVector1D> animatable, State<Float> state, Continuation continuation) {
        super(2, continuation);
        this.$target = f;
        this.$fractionAnimatable = animatable;
        this.$fraction$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BasicSwitchKt$SeslBasicSwitch$animateFraction$2$1$1(this.$target, this.$fractionAnimatable, this.$fraction$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicSwitchKt$SeslBasicSwitch$animateFraction$2$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            float fAbs = Math.abs(this.$target - ((Number) this.$fraction$delegate.getValue()).floatValue());
            Animatable<Float, AnimationVector1D> animatable = this.$fractionAnimatable;
            Float f = new Float(this.$target);
            SeslBasicSwitchDefaults.INSTANCE.getClass();
            TweenSpec tweenSpecTween$default = AnimationSpecKt.tween$default((int) (300 * fAbs), 0, SeslBasicSwitchDefaults.ThumbTransitionEasing, 2);
            this.label = 1;
            if (Animatable.animateTo$default(animatable, f, tweenSpecTween$default, null, null, this, 12) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
