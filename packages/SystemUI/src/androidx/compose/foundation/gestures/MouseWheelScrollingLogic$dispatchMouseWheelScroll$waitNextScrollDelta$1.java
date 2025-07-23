package androidx.compose.foundation.gestures;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    /* synthetic */ Object result;

    public MouseWheelScrollingLogic$dispatchMouseWheelScroll$waitNextScrollDelta$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return MouseWheelScrollingLogic.access$dispatchMouseWheelScroll$waitNextScrollDelta(null, null, null, null, null, 0L, this);
    }
}
