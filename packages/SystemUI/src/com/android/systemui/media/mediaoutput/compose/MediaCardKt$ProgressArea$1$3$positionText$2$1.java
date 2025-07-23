package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaCardKt$ProgressArea$1$3$positionText$2$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ MutableFloatState $livePosition$delegate;
    final /* synthetic */ MutableState<Boolean> $seekTo$delegate;
    /* synthetic */ long J$0;
    /* synthetic */ long J$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ProgressArea$1$3$positionText$2$1(MutableState<Boolean> mutableState, MutableFloatState mutableFloatState, Continuation continuation) {
        super(3, continuation);
        this.$seekTo$delegate = mutableState;
        this.$livePosition$delegate = mutableFloatState;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        long longValue = ((Number) obj).longValue();
        long longValue2 = ((Number) obj2).longValue();
        MediaCardKt$ProgressArea$1$3$positionText$2$1 mediaCardKt$ProgressArea$1$3$positionText$2$1 = new MediaCardKt$ProgressArea$1$3$positionText$2$1(this.$seekTo$delegate, this.$livePosition$delegate, (Continuation) obj3);
        mediaCardKt$ProgressArea$1$3$positionText$2$1.J$0 = longValue;
        mediaCardKt$ProgressArea$1$3$positionText$2$1.J$1 = longValue2;
        return mediaCardKt$ProgressArea$1$3$positionText$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        long j = this.J$0;
        long j2 = this.J$1;
        if (((Boolean) this.$seekTo$delegate.getValue()).booleanValue()) {
            j2 = MathKt__MathJVMKt.roundToLong(((SnapshotMutableFloatStateImpl) this.$livePosition$delegate).getFloatValue() * j);
        }
        return new Long(j2);
    }
}
