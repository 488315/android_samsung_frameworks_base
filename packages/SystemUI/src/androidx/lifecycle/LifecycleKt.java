package androidx.lifecycle;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LifecycleKt {
    public static final LifecycleCoroutineScopeImpl getCoroutineScope(Lifecycle lifecycle) {
        LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl;
        HandlerContext handlerContext;
        do {
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl2 = (LifecycleCoroutineScopeImpl) lifecycle.internalScopeRef.get();
            if (lifecycleCoroutineScopeImpl2 != null) {
                return lifecycleCoroutineScopeImpl2;
            }
            SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            handlerContext = MainDispatcherLoader.dispatcher;
            lifecycleCoroutineScopeImpl = new LifecycleCoroutineScopeImpl(lifecycle, CoroutineContext.DefaultImpls.plus(SupervisorJob$default, handlerContext.immediate));
        } while (!lifecycle.internalScopeRef.compareAndSet(null, lifecycleCoroutineScopeImpl));
        BuildersKt.launch$default(lifecycleCoroutineScopeImpl, handlerContext.immediate, null, new LifecycleCoroutineScopeImpl$register$1(lifecycleCoroutineScopeImpl, null), 2);
        return lifecycleCoroutineScopeImpl;
    }
}
