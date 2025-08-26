package androidx.datastore.core;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public interface StorageConnection extends Closeable {
    InterProcessCoordinator getCoordinator();

    Object readScope(Function3 function3, ContinuationImpl continuationImpl);

    Object writeScope(Function2 function2, ContinuationImpl continuationImpl);
}
