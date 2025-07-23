package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.LimitedDispatcherKt;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class MainCoroutineDispatcher extends CoroutineDispatcher {
    public abstract MainCoroutineDispatcher getImmediate();

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public CoroutineDispatcher limitedParallelism(int i) {
        LimitedDispatcherKt.checkParallelism(i);
        return this;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        HandlerContext handlerContext;
        String str;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext2 = MainDispatcherLoader.dispatcher;
        if (this == handlerContext2) {
            str = "Dispatchers.Main";
        } else {
            try {
                handlerContext = handlerContext2.immediate;
            } catch (UnsupportedOperationException unused) {
                handlerContext = null;
            }
            str = this == handlerContext ? "Dispatchers.Main.immediate" : null;
        }
        return str == null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getClass().getSimpleName(), "@", DebugStringsKt.getHexAddress(this)) : str;
    }
}
