package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class MainCoroutineDispatcher extends CoroutineDispatcher {
    public abstract MainCoroutineDispatcher getImmediate();

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        MainCoroutineDispatcher mainCoroutineDispatcher;
        String str;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher2 = MainDispatcherLoader.dispatcher;
        if (this == mainCoroutineDispatcher2) {
            str = "Dispatchers.Main";
        } else {
            try {
                mainCoroutineDispatcher = mainCoroutineDispatcher2.getImmediate();
            } catch (UnsupportedOperationException unused) {
                mainCoroutineDispatcher = null;
            }
            str = this == mainCoroutineDispatcher ? "Dispatchers.Main.immediate" : null;
        }
        return str == null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(DebugStringsKt.getClassSimpleName(this), "@", DebugStringsKt.getHexAddress(this)) : str;
    }
}
