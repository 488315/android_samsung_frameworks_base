package kotlinx.coroutines;

import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.internal.MissingMainCoroutineDispatcher;
import kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DefaultExecutorKt {
    public static final Delay DefaultDelay;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        String str;
        Delay delay;
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        try {
            str = System.getProperty("kotlinx.coroutines.main.delay");
        } catch (SecurityException unused) {
            str = null;
        }
        if (str != null ? Boolean.parseBoolean(str) : false) {
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
            delay = ((mainCoroutineDispatcher.getImmediate() instanceof MissingMainCoroutineDispatcher) || !(mainCoroutineDispatcher instanceof Delay)) ? DefaultExecutor.INSTANCE : (Delay) mainCoroutineDispatcher;
        } else {
            delay = DefaultExecutor.INSTANCE;
        }
        DefaultDelay = delay;
    }
}
