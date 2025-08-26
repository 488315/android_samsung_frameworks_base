package androidx.compose.animation.core;

import androidx.compose.animation.core.MutatorMutex;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;

/* loaded from: classes.dex */
final class MutatorMutex$mutate$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $block;
    final /* synthetic */ MutatePriority $priority;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ MutatorMutex this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutatorMutex$mutate$2(MutatePriority mutatePriority, MutatorMutex mutatorMutex, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$priority = mutatePriority;
        this.this$0 = mutatorMutex;
        this.$block = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MutatorMutex$mutate$2 mutatorMutex$mutate$2 = new MutatorMutex$mutate$2(this.$priority, this.this$0, this.$block, continuation);
        mutatorMutex$mutate$2.L$0 = obj;
        return mutatorMutex$mutate$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutatorMutex$mutate$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.Mutex] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MutatorMutex.Mutator mutator;
        MutatorMutex.Mutator mutator2;
        MutatorMutex mutatorMutex;
        Mutex mutex;
        Function1 function1;
        MutatorMutex mutatorMutex2;
        Throwable th;
        MutatorMutex.Mutator mutator3;
        Mutex mutex2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        ?? r1 = this.label;
        try {
            try {
                if (r1 == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    MutatePriority mutatePriority = this.$priority;
                    CoroutineContext.Element element = coroutineScope.getCoroutineContext().get(Job.Key);
                    element.getClass();
                    mutator = new MutatorMutex.Mutator(mutatePriority, (Job) element);
                    MutatorMutex mutatorMutex3 = this.this$0;
                    do {
                        mutator2 = (MutatorMutex.Mutator) mutatorMutex3.currentMutator.get();
                        if (mutator2 != null && mutator.priority.compareTo(mutator2.priority) < 0) {
                            throw new CancellationException("Current mutation had a higher priority");
                        }
                    } while (!mutatorMutex3.currentMutator.compareAndSet(mutator2, mutator));
                    if (mutator2 != null) {
                        mutator2.job.cancel(new MutationInterruptedException());
                    }
                    mutatorMutex = this.this$0;
                    mutex = mutatorMutex.mutex;
                    Function1 function12 = this.$block;
                    this.L$0 = mutator;
                    this.L$1 = mutex;
                    this.L$2 = function12;
                    this.L$3 = mutatorMutex;
                    this.label = 1;
                    if (mutex.lock(this) != coroutineSingletons) {
                        function1 = function12;
                    }
                    return coroutineSingletons;
                }
                if (r1 != 1) {
                    if (r1 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mutatorMutex2 = (MutatorMutex) this.L$2;
                    mutex2 = (Mutex) this.L$1;
                    mutator3 = (MutatorMutex.Mutator) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        mutatorMutex2.currentMutator.compareAndSet(mutator3, null);
                        mutex2.unlock(null);
                        return obj;
                    } catch (Throwable th2) {
                        th = th2;
                        mutatorMutex2.currentMutator.compareAndSet(mutator3, null);
                        throw th;
                    }
                }
                MutatorMutex mutatorMutex4 = (MutatorMutex) this.L$3;
                function1 = (Function1) this.L$2;
                mutex = (Mutex) this.L$1;
                MutatorMutex.Mutator mutator4 = (MutatorMutex.Mutator) this.L$0;
                ResultKt.throwOnFailure(obj);
                mutatorMutex = mutatorMutex4;
                mutator = mutator4;
                this.L$0 = mutator;
                this.L$1 = mutex;
                this.L$2 = mutatorMutex;
                this.L$3 = null;
                this.label = 2;
                Object objMo781invoke = function1.mo781invoke(this);
                if (objMo781invoke != coroutineSingletons) {
                    mutatorMutex2 = mutatorMutex;
                    obj = objMo781invoke;
                    mutator3 = mutator;
                    mutex2 = mutex;
                    mutatorMutex2.currentMutator.compareAndSet(mutator3, null);
                    mutex2.unlock(null);
                    return obj;
                }
                return coroutineSingletons;
            } catch (Throwable th3) {
                mutatorMutex2 = mutatorMutex;
                th = th3;
                mutator3 = mutator;
                mutatorMutex2.currentMutator.compareAndSet(mutator3, null);
                throw th;
            }
        } catch (Throwable th4) {
            r1.unlock(null);
            throw th4;
        }
    }
}
