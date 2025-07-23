package androidx.compose.foundation.lazy.layout;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazyLayoutScrollScopeKt$animateScrollToItem$1 extends ContinuationImpl {
    float F$0;
    float F$1;
    float F$2;
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;

    public LazyLayoutScrollScopeKt$animateScrollToItem$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return LazyLayoutScrollScopeKt.animateScrollToItem(null, 0, 0, 0, null, this);
    }
}
