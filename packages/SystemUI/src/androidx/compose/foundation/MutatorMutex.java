package androidx.compose.foundation;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
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

    public static final void access$tryMutateOrCancel(MutatorMutex mutatorMutex, Mutator mutator) {
        Mutator mutator2;
        do {
            mutator2 = (Mutator) mutatorMutex.currentMutator.get();
            if (mutator2 != null && mutator.priority.compareTo(mutator2.priority) < 0) {
                throw new CancellationException("Current mutation had a higher priority");
            }
        } while (!mutatorMutex.currentMutator.compareAndSet(mutator2, mutator));
        if (mutator2 != null) {
            mutator2.job.cancel(new MutationInterruptedException());
        }
    }

    public final Object mutate(MutatePriority mutatePriority, Function1 function1, ContinuationImpl continuationImpl) {
        return CoroutineScopeKt.coroutineScope(new MutatorMutex$mutate$2(mutatePriority, this, function1, null), continuationImpl);
    }

    public final Object mutateWith(Object obj, MutatePriority mutatePriority, Function2 function2, SuspendLambda suspendLambda) {
        return CoroutineScopeKt.coroutineScope(new MutatorMutex$mutateWith$2(mutatePriority, this, function2, obj, null), suspendLambda);
    }
}
