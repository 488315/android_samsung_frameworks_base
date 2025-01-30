package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.SparseArrayMapWrapper$entrySequence$1", m277f = "SparseArrayUtils.kt", m278l = {91}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class SparseArrayMapWrapper$entrySequence$1 extends RestrictedSuspendLambda implements Function2 {
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SparseArrayMapWrapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SparseArrayMapWrapper$entrySequence$1(SparseArrayMapWrapper sparseArrayMapWrapper, Continuation<? super SparseArrayMapWrapper$entrySequence$1> continuation) {
        super(2, continuation);
        this.this$0 = sparseArrayMapWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SparseArrayMapWrapper$entrySequence$1 sparseArrayMapWrapper$entrySequence$1 = new SparseArrayMapWrapper$entrySequence$1(this.this$0, continuation);
        sparseArrayMapWrapper$entrySequence$1.L$0 = obj;
        return sparseArrayMapWrapper$entrySequence$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SparseArrayMapWrapper$entrySequence$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x0054 -> B:5:0x0057). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        int size;
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            size = this.this$0.sparseArray.size();
            i = 0;
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
                int keyAt = this.this$0.sparseArray.keyAt(i);
                SparseArrayMapWrapper.Entry entry = new SparseArrayMapWrapper.Entry(keyAt, this.this$0.sparseArray.get(keyAt));
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
}
