package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractSharedFlowSlot {
    public abstract boolean allocateLocked(AbstractSharedFlow abstractSharedFlow);

    public abstract Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow);
}
