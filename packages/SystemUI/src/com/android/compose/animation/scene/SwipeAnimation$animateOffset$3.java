package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CompletableDeferred;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        return ((SwipeAnimation$animateOffset$3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0051, code lost:
    
        if (r9.mo779invoke(r8) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0053, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
    
        if (r10 == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1d
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r10)
            goto L54
        L10:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L18:
            kotlin.ResultKt.throwOnFailure(r10)
            r8 = r9
            goto L35
        L1d:
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r3
            com.android.compose.animation.scene.SwipeAnimation r3 = r9.this$0
            androidx.compose.animation.core.Animatable<java.lang.Float, androidx.compose.animation.core.AnimationVector1D> r4 = r9.$animatable
            float r5 = r9.$targetOffset
            float r6 = r9.$initialVelocity
            androidx.compose.animation.core.AnimationSpec<java.lang.Float> r7 = r9.$spec
            r9.label = r10
            r8 = r9
            java.lang.Object r10 = com.android.compose.animation.scene.SwipeAnimation.access$animateOffset(r3, r4, r5, r6, r7, r8)
            if (r10 != r0) goto L35
            goto L53
        L35:
            java.lang.Number r10 = (java.lang.Number) r10
            float r9 = r10.floatValue()
            kotlinx.coroutines.CompletableDeferred r10 = r8.$velocityConsumed
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r9)
            kotlinx.coroutines.CompletableDeferredImpl r10 = (kotlinx.coroutines.CompletableDeferredImpl) r10
            r10.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r1)
            kotlin.jvm.functions.Function1 r9 = r8.$awaitFling
            if (r9 == 0) goto L54
            r8.label = r2
            java.lang.Object r9 = r9.mo779invoke(r8)
            if (r9 != r0) goto L54
        L53:
            return r0
        L54:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.SwipeAnimation$animateOffset$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
