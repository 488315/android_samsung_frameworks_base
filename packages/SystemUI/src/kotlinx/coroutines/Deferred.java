package kotlinx.coroutines;

import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes4.dex */
public interface Deferred extends Job {
    Object await(ContinuationImpl continuationImpl);

    Object getCompleted();
}
