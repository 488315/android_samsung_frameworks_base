package androidx.collection;

import androidx.collection.MutableOrderedSetWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* loaded from: classes.dex */
final class MutableOrderedSetWrapper$iterator$1$iterator$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ MutableOrderedSetWrapper this$0;
    final /* synthetic */ MutableOrderedSetWrapper.AnonymousClass1 this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableOrderedSetWrapper$iterator$1$iterator$1(MutableOrderedSetWrapper mutableOrderedSetWrapper, MutableOrderedSetWrapper.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mutableOrderedSetWrapper;
        this.this$1 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MutableOrderedSetWrapper$iterator$1$iterator$1 mutableOrderedSetWrapper$iterator$1$iterator$1 = new MutableOrderedSetWrapper$iterator$1$iterator$1(this.this$0, this.this$1, continuation);
        mutableOrderedSetWrapper$iterator$1$iterator$1.L$0 = obj;
        return mutableOrderedSetWrapper$iterator$1$iterator$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutableOrderedSetWrapper$iterator$1$iterator$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0068  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0065 -> B:6:0x0020). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long[] jArr;
        int i;
        SequenceScope sequenceScope;
        MutableOrderedSetWrapper.AnonymousClass1 anonymousClass1;
        MutableOrderedSetWrapper mutableOrderedSetWrapper;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            MutableOrderedSetWrapper mutableOrderedSetWrapper2 = this.this$0;
            MutableOrderedScatterSet mutableOrderedScatterSet = mutableOrderedSetWrapper2.parent;
            MutableOrderedSetWrapper.AnonymousClass1 anonymousClass12 = this.this$1;
            jArr = mutableOrderedScatterSet.nodes;
            i = mutableOrderedScatterSet.tail;
            sequenceScope = sequenceScope2;
            anonymousClass1 = anonymousClass12;
            mutableOrderedSetWrapper = mutableOrderedSetWrapper2;
            if (i != Integer.MAX_VALUE) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i3 = this.I$0;
            long[] jArr2 = (long[]) this.L$3;
            mutableOrderedSetWrapper = (MutableOrderedSetWrapper) this.L$2;
            MutableOrderedSetWrapper.AnonymousClass1 anonymousClass13 = (MutableOrderedSetWrapper.AnonymousClass1) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            anonymousClass1 = anonymousClass13;
            jArr = jArr2;
            i = i3;
            if (i != Integer.MAX_VALUE) {
                i3 = (int) ((jArr[i] >> 31) & 2147483647L);
                anonymousClass1.current = i;
                Object obj2 = mutableOrderedSetWrapper.parent.elements[i];
                this.L$0 = sequenceScope;
                this.L$1 = anonymousClass1;
                this.L$2 = mutableOrderedSetWrapper;
                this.L$3 = jArr;
                this.I$0 = i3;
                this.label = 1;
                if (sequenceScope.yield(obj2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i = i3;
                if (i != Integer.MAX_VALUE) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
