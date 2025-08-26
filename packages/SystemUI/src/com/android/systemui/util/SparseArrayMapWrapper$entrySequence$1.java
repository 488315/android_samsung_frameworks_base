package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* loaded from: classes3.dex */
final class SparseArrayMapWrapper$entrySequence$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SparseArrayMapWrapper<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x005a -> B:13:0x005d). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int size;
        int i;
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            size = ((SparseArrayMapWrapper) this.this$0).sparseArray.size();
            i = 0;
            sequenceScope = sequenceScope2;
            if (i < size) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$1;
            size = this.I$0;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            i++;
            if (i < size) {
                int iKeyAt = ((SparseArrayMapWrapper) this.this$0).sparseArray.keyAt(i);
                SparseArrayMapWrapper.Entry entry = new SparseArrayMapWrapper.Entry(iKeyAt, ((SparseArrayMapWrapper) this.this$0).sparseArray.get(iKeyAt));
                this.L$0 = sequenceScope;
                this.I$0 = size;
                this.I$1 = i;
                this.label = 1;
                if (sequenceScope.yield(entry, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i++;
                if (i < size) {
                    return Unit.INSTANCE;
                }
            }
        }
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((SparseArrayMapWrapper$entrySequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
