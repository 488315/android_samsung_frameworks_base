package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
