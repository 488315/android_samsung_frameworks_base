package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ResumeOnCompletion extends JobNode {
    public final Continuation continuation;

    public ResumeOnCompletion(Continuation<? super Unit> continuation) {
        this.continuation = continuation;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.CompletionHandlerBase
    public final void invoke(Throwable th) {
        int i = Result.$r8$clinit;
        this.continuation.resumeWith(Unit.INSTANCE);
    }
}
