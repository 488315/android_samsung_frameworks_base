package androidx.room.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidSQLiteDriverPooledConnection$transaction$1<R> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AndroidSQLiteDriverPooledConnection this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidSQLiteDriverPooledConnection$transaction$1(AndroidSQLiteDriverPooledConnection androidSQLiteDriverPooledConnection, Continuation continuation) {
        super(continuation);
        this.this$0 = androidSQLiteDriverPooledConnection;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.transaction(null, null, this);
    }
}
