package androidx.datastore.core.handlers;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.CorruptionHandler;

/* loaded from: classes.dex */
public final class NoOpCorruptionHandler implements CorruptionHandler {
    @Override // androidx.datastore.core.CorruptionHandler
    public final Object handleCorruption(CorruptionException corruptionException) throws CorruptionException {
        throw corruptionException;
    }
}
