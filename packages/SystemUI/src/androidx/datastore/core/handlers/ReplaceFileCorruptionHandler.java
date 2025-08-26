package androidx.datastore.core.handlers;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.CorruptionHandler;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class ReplaceFileCorruptionHandler implements CorruptionHandler {
    public final Function1 produceNewData;

    public ReplaceFileCorruptionHandler(Function1 function1) {
        this.produceNewData = function1;
    }

    @Override // androidx.datastore.core.CorruptionHandler
    public final Object handleCorruption(CorruptionException corruptionException) {
        return this.produceNewData.mo781invoke(corruptionException);
    }
}
