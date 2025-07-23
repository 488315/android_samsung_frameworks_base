package androidx.collection;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OrderedSetWrapper$iterator$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ OrderedSetWrapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OrderedSetWrapper$iterator$1(OrderedSetWrapper orderedSetWrapper, Continuation continuation) {
        super(2, continuation);
        this.this$0 = orderedSetWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OrderedSetWrapper$iterator$1 orderedSetWrapper$iterator$1 = new OrderedSetWrapper$iterator$1(this.this$0, continuation);
        orderedSetWrapper$iterator$1.L$0 = obj;
        return orderedSetWrapper$iterator$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OrderedSetWrapper$iterator$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        Object[] objArr;
        long[] jArr;
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            OrderedScatterSet orderedScatterSet = this.this$0.parent;
            Object[] objArr2 = orderedScatterSet.elements;
            long[] jArr2 = orderedScatterSet.nodes;
            i = orderedScatterSet.tail;
            objArr = objArr2;
            jArr = jArr2;
            sequenceScope = sequenceScope2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            jArr = (long[]) this.L$2;
            objArr = (Object[]) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (i != Integer.MAX_VALUE) {
            int i3 = (int) ((jArr[i] >> 31) & 2147483647L);
            Object obj2 = objArr[i];
            this.L$0 = sequenceScope;
            this.L$1 = objArr;
            this.L$2 = jArr;
            this.I$0 = i3;
            this.label = 1;
            if (sequenceScope.yield(obj2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i = i3;
        }
        return Unit.INSTANCE;
    }
}
