package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes.dex */
final class SwipeAnimation$animateOffset$3 extends SuspendLambda implements Function1 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animatable;
    final /* synthetic */ Function1 $awaitFling;
    final /* synthetic */ float $initialVelocity;
    final /* synthetic */ AnimationSpec<Float> $spec;
    final /* synthetic */ float $targetOffset;
    final /* synthetic */ CompletableDeferred $velocityConsumed;
    int label;
    final /* synthetic */ SwipeAnimation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SwipeAnimation$animateOffset$3(SwipeAnimation swipeAnimation, Animatable<Float, AnimationVector1D> animatable, float f, float f2, AnimationSpec<Float> animationSpec, CompletableDeferred completableDeferred, Function1 function1, Continuation continuation) {
        super(1, continuation);
        this.this$0 = swipeAnimation;
        this.$animatable = animatable;
        this.$targetOffset = f;
        this.$initialVelocity = f2;
        this.$spec = animationSpec;
        this.$velocityConsumed = completableDeferred;
        this.$awaitFling = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new SwipeAnimation$animateOffset$3(this.this$0, this.$animatable, this.$targetOffset, this.$initialVelocity, this.$spec, this.$velocityConsumed, this.$awaitFling, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return ((SwipeAnimation$animateOffset$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0051, code lost:
    
        if (r9.mo781invoke(r8) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SwipeAnimation$animateOffset$3 swipeAnimation$animateOffset$3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SwipeAnimation swipeAnimation = this.this$0;
            Animatable<Float, AnimationVector1D> animatable = this.$animatable;
            float f = this.$targetOffset;
            float f2 = this.$initialVelocity;
            AnimationSpec<Float> animationSpec = this.$spec;
            this.label = 1;
            swipeAnimation$animateOffset$3 = this;
            obj = SwipeAnimation.access$animateOffset(swipeAnimation, animatable, f, f2, animationSpec, swipeAnimation$animateOffset$3);
            if (obj != coroutineSingletons) {
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
        swipeAnimation$animateOffset$3 = this;
        float fFloatValue = ((Number) obj).floatValue();
        ((CompletableDeferredImpl) swipeAnimation$animateOffset$3.$velocityConsumed).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new Float(fFloatValue));
        Function1 function1 = swipeAnimation$animateOffset$3.$awaitFling;
        if (function1 != null) {
            swipeAnimation$animateOffset$3.label = 2;
        }
        return Unit.INSTANCE;
    }
}
