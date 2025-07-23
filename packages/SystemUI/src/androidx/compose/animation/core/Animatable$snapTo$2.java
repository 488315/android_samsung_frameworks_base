package androidx.compose.animation.core;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class Animatable$snapTo$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ Object $targetValue;
    int label;
    final /* synthetic */ Animatable<Object, AnimationVector> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Animatable$snapTo$2(Animatable<Object, AnimationVector> animatable, Object obj, Continuation continuation) {
        super(1, continuation);
        this.this$0 = animatable;
        this.$targetValue = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new Animatable$snapTo$2(this.this$0, this.$targetValue, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((Animatable$snapTo$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Animatable.access$endAnimation(this.this$0);
        Object clampToBounds = this.this$0.clampToBounds(this.$targetValue);
        ((SnapshotMutableStateImpl) this.this$0.internalState.value$delegate).setValue(clampToBounds);
        ((SnapshotMutableStateImpl) this.this$0.targetValue$delegate).setValue(clampToBounds);
        return Unit.INSTANCE;
    }
}
