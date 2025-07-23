package androidx.datastore.core.okio;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OkioReadScope$readData$1<T> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ OkioReadScope this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OkioReadScope$readData$1(OkioReadScope okioReadScope, Continuation continuation) {
        super(continuation);
        this.this$0 = okioReadScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return OkioReadScope.readData$suspendImpl(this.this$0, this);
    }
}
