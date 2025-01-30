package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NoOpContinuation implements Continuation {
    public static final NoOpContinuation INSTANCE = new NoOpContinuation();
    public static final EmptyCoroutineContext context = EmptyCoroutineContext.INSTANCE;

    private NoOpContinuation() {
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return context;
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
    }
}
