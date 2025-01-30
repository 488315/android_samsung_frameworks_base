package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StandaloneCoroutine extends AbstractCoroutine {
    public StandaloneCoroutine(CoroutineContext coroutineContext, boolean z) {
        super(coroutineContext, true, z);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final boolean handleJobException(Throwable th) {
        CoroutineExceptionHandlerKt.handleCoroutineException(th, this.context);
        return true;
    }
}
