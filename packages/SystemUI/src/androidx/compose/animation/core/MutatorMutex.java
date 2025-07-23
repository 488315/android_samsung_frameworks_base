package androidx.compose.animation.core;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutatorMutex {
    public final AtomicReference currentMutator = new AtomicReference(null);
    public final MutexImpl mutex = MutexKt.Mutex$default();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Mutator {
        public final Job job;
        public final MutatePriority priority;

        public Mutator(MutatePriority mutatePriority, Job job) {
            this.priority = mutatePriority;
            this.job = job;
        }
    }

    public static Object mutate$default(MutatorMutex mutatorMutex, Function1 function1, Continuation continuation) {
        MutatePriority mutatePriority = MutatePriority.Default;
        mutatorMutex.getClass();
        return CoroutineScopeKt.coroutineScope(new MutatorMutex$mutate$2(mutatePriority, mutatorMutex, function1, null), continuation);
    }
}
