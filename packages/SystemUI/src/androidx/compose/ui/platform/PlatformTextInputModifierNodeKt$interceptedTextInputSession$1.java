package androidx.compose.ui.platform;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PlatformTextInputModifierNodeKt$interceptedTextInputSession$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;

    public PlatformTextInputModifierNodeKt$interceptedTextInputSession$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return PlatformTextInputModifierNodeKt.interceptedTextInputSession(null, null, null, this);
    }
}
