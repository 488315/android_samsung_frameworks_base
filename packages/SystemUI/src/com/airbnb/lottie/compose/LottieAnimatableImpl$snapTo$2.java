package com.airbnb.lottie.compose;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.airbnb.lottie.LottieComposition;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LottieAnimatableImpl$snapTo$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ LottieComposition $composition;
    final /* synthetic */ int $iteration;
    final /* synthetic */ float $progress;
    final /* synthetic */ boolean $resetLastFrameNanos;
    int label;
    final /* synthetic */ LottieAnimatableImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LottieAnimatableImpl$snapTo$2(LottieAnimatableImpl lottieAnimatableImpl, LottieComposition lottieComposition, float f, int i, boolean z, Continuation continuation) {
        super(1, continuation);
        this.this$0 = lottieAnimatableImpl;
        this.$composition = lottieComposition;
        this.$progress = f;
        this.$iteration = i;
        this.$resetLastFrameNanos = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new LottieAnimatableImpl$snapTo$2(this.this$0, this.$composition, this.$progress, this.$iteration, this.$resetLastFrameNanos, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((LottieAnimatableImpl$snapTo$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LottieAnimatableImpl lottieAnimatableImpl = this.this$0;
        ((SnapshotMutableStateImpl) lottieAnimatableImpl.composition$delegate).setValue(this.$composition);
        this.this$0.setProgress$1(this.$progress);
        this.this$0.setIteration(this.$iteration);
        ((SnapshotMutableStateImpl) this.this$0.isPlaying$delegate).setValue(Boolean.valueOf(false));
        if (this.$resetLastFrameNanos) {
            ((SnapshotMutableStateImpl) this.this$0.lastFrameNanos$delegate).setValue(Long.MIN_VALUE);
        }
        return Unit.INSTANCE;
    }
}
