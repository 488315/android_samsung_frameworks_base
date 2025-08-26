package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes4.dex */
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
