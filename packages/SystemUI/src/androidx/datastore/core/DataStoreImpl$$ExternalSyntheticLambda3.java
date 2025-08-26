package androidx.datastore.core;

import androidx.datastore.core.Message;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* loaded from: classes.dex */
public final /* synthetic */ class DataStoreImpl$$ExternalSyntheticLambda3 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Throwable cancellationException = (Throwable) obj2;
        int i = DataStoreImpl.$r8$clinit;
        CompletableDeferred completableDeferred = ((Message.Update) obj).ack;
        if (cancellationException == null) {
            cancellationException = new CancellationException("DataStore scope was cancelled before updateData could complete");
        }
        ((CompletableDeferredImpl) completableDeferred).completeExceptionally(cancellationException);
        return Unit.INSTANCE;
    }
}
