package com.android.systemui.kairos;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class BuildScope$toStateFlow$2$collect$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BuildScope$toStateFlow$2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScope$toStateFlow$2$collect$1(BuildScope$toStateFlow$2 buildScope$toStateFlow$2, Continuation continuation) {
        super(continuation);
        this.this$0 = buildScope$toStateFlow$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.collect(null, this);
    }
}
