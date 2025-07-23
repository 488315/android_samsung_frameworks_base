package androidx.collection;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MutableOrderedSetWrapper$iterator$1$iterator$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ MutableOrderedSetWrapper this$0;
    final /* synthetic */ MutableOrderedSetWrapper$iterator$1 this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableOrderedSetWrapper$iterator$1$iterator$1(MutableOrderedSetWrapper mutableOrderedSetWrapper, MutableOrderedSetWrapper$iterator$1 mutableOrderedSetWrapper$iterator$1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mutableOrderedSetWrapper;
        this.this$1 = mutableOrderedSetWrapper$iterator$1;
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0065 -> B:5:0x0020). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            if (r1 == 0) goto L2a
            if (r1 != r2) goto L22
            int r1 = r11.I$0
            java.lang.Object r3 = r11.L$3
            long[] r3 = (long[]) r3
            java.lang.Object r4 = r11.L$2
            androidx.collection.MutableOrderedSetWrapper r4 = (androidx.collection.MutableOrderedSetWrapper) r4
            java.lang.Object r5 = r11.L$1
            androidx.collection.MutableOrderedSetWrapper$iterator$1 r5 = (androidx.collection.MutableOrderedSetWrapper$iterator$1) r5
            java.lang.Object r6 = r11.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r12)
            r12 = r5
            r5 = r3
        L20:
            r3 = r1
            goto L3e
        L22:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L2a:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlin.sequences.SequenceScope r12 = (kotlin.sequences.SequenceScope) r12
            androidx.collection.MutableOrderedSetWrapper r1 = r11.this$0
            androidx.collection.MutableOrderedScatterSet r3 = r1.parent
            androidx.collection.MutableOrderedSetWrapper$iterator$1 r4 = r11.this$1
            long[] r5 = r3.nodes
            int r3 = r3.tail
            r6 = r12
            r12 = r4
            r4 = r1
        L3e:
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r3 == r1) goto L68
            r7 = r5[r3]
            r1 = 31
            long r7 = r7 >> r1
            r9 = 2147483647(0x7fffffff, double:1.060997895E-314)
            long r7 = r7 & r9
            int r1 = (int) r7
            r12.current = r3
            androidx.collection.MutableOrderedScatterSet r7 = r4.parent
            java.lang.Object[] r7 = r7.elements
            r3 = r7[r3]
            r11.L$0 = r6
            r11.L$1 = r12
            r11.L$2 = r4
            r11.L$3 = r5
            r11.I$0 = r1
            r11.label = r2
            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = r6.yield(r3, r11)
            if (r3 != r0) goto L20
            return r0
        L68:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableOrderedSetWrapper$iterator$1$iterator$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
