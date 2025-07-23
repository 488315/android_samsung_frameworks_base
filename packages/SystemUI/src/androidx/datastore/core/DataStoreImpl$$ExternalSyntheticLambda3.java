package androidx.datastore.core;

import androidx.datastore.core.Message;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DataStoreImpl$$ExternalSyntheticLambda3 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Throwable th = (Throwable) obj2;
        int i = DataStoreImpl.$r8$clinit;
        CompletableDeferred completableDeferred = ((Message.Update) obj).ack;
        if (th == null) {
            th = new CancellationException("DataStore scope was cancelled before updateData could complete");
        }
        ((CompletableDeferredImpl) completableDeferred).completeExceptionally(th);
        return Unit.INSTANCE;
    }
}
