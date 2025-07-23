package androidx.datastore.core.handlers;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.CorruptionHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NoOpCorruptionHandler implements CorruptionHandler {
    @Override // androidx.datastore.core.CorruptionHandler
    public final Object handleCorruption(CorruptionException corruptionException) {
        throw corruptionException;
    }
}
