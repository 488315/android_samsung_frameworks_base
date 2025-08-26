package androidx.datastore.core;

import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public interface ReadScope extends Closeable {
    Object readData(Continuation continuation);
}
