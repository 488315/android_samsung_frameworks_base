package com.android.systemui.util;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

final class SparseArrayMapWrapper$entrySequence$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SparseArrayMapWrapper<Object> this$0;

    public SparseArrayMapWrapper$entrySequence$1(SparseArrayMapWrapper<Object> sparseArrayMapWrapper, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sparseArrayMapWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SparseArrayMapWrapper$entrySequence$1 sparseArrayMapWrapper$entrySequence$1 = new SparseArrayMapWrapper$entrySequence$1(this.this$0, continuation);
        sparseArrayMapWrapper$entrySequence$1.L$0 = obj;
        return sparseArrayMapWrapper$entrySequence$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            if (r1 == 0) goto L1d
            if (r1 != r2) goto L15
            int r1 = r8.I$1
            int r3 = r8.I$0
            java.lang.Object r4 = r8.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.ResultKt.throwOnFailure(r9)
            goto L5d
        L15:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1d:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            com.android.systemui.util.SparseArrayMapWrapper<java.lang.Object> r1 = r8.this$0
            android.util.SparseArray r1 = com.android.systemui.util.SparseArrayMapWrapper.access$getSparseArray$p(r1)
            int r1 = r1.size()
            r3 = 0
            r4 = r9
            r7 = r3
            r3 = r1
            r1 = r7
        L33:
            if (r1 >= r3) goto L5f
            com.android.systemui.util.SparseArrayMapWrapper<java.lang.Object> r9 = r8.this$0
            android.util.SparseArray r9 = com.android.systemui.util.SparseArrayMapWrapper.access$getSparseArray$p(r9)
            int r9 = r9.keyAt(r1)
            com.android.systemui.util.SparseArrayMapWrapper<java.lang.Object> r5 = r8.this$0
            android.util.SparseArray r5 = com.android.systemui.util.SparseArrayMapWrapper.access$getSparseArray$p(r5)
            java.lang.Object r5 = r5.get(r9)
            com.android.systemui.util.SparseArrayMapWrapper$Entry r6 = new com.android.systemui.util.SparseArrayMapWrapper$Entry
            r6.<init>(r9, r5)
            r8.L$0 = r4
            r8.I$0 = r3
            r8.I$1 = r1
            r8.label = r2
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = r4.yield(r6, r8)
            if (r9 != r0) goto L5d
            return r0
        L5d:
            int r1 = r1 + r2
            goto L33
        L5f:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.SparseArrayMapWrapper$entrySequence$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((SparseArrayMapWrapper$entrySequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
