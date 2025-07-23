package androidx.compose.foundation.text;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ContextMenu_androidKt$getContextMenuItemsAvailability$2 extends ContinuationImpl {
    int I$0;
    int I$1;
    int I$2;
    Object L$0;
    int label;
    /* synthetic */ Object result;

    public ContextMenu_androidKt$getContextMenuItemsAvailability$2(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ContextMenu_androidKt.getContextMenuItemsAvailability(null, this);
    }
}
