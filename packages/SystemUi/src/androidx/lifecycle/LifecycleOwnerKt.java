package androidx.lifecycle;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LifecycleOwnerKt {
    public static final LifecycleCoroutineScopeImpl getLifecycleScope(LifecycleOwner lifecycleOwner) {
        LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl;
        LifecycleRegistry lifecycle = lifecycleOwner.getLifecycle();
        while (true) {
            AtomicReference atomicReference = lifecycle.mInternalScopeRef;
            lifecycleCoroutineScopeImpl = (LifecycleCoroutineScopeImpl) atomicReference.get();
            if (lifecycleCoroutineScopeImpl != null) {
                break;
            }
            SupervisorJobImpl supervisorJobImpl = new SupervisorJobImpl(null);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
            lifecycleCoroutineScopeImpl = new LifecycleCoroutineScopeImpl(lifecycle, CoroutineContext.DefaultImpls.plus(supervisorJobImpl, mainCoroutineDispatcher.getImmediate()));
            if (atomicReference.compareAndSet(null, lifecycleCoroutineScopeImpl)) {
                BuildersKt.launch$default(lifecycleCoroutineScopeImpl, mainCoroutineDispatcher.getImmediate(), null, new LifecycleCoroutineScopeImpl$register$1(lifecycleCoroutineScopeImpl, null), 2);
                break;
            }
        }
        return lifecycleCoroutineScopeImpl;
    }
}
