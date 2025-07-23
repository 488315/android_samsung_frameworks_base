package androidx.compose.foundation;

import androidx.compose.foundation.MutatorMutex;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MutatorMutex$mutateWith$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ MutatePriority $priority;
    final /* synthetic */ Object $receiver;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    final /* synthetic */ MutatorMutex this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutatorMutex$mutateWith$2(MutatePriority mutatePriority, MutatorMutex mutatorMutex, Function2 function2, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$priority = mutatePriority;
        this.this$0 = mutatorMutex;
        this.$block = function2;
        this.$receiver = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MutatorMutex$mutateWith$2 mutatorMutex$mutateWith$2 = new MutatorMutex$mutateWith$2(this.$priority, this.this$0, this.$block, this.$receiver, continuation);
        mutatorMutex$mutateWith$2.L$0 = obj;
        return mutatorMutex$mutateWith$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MutatorMutex$mutateWith$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r6v4, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MutatorMutex.Mutator mutator;
        MutatorMutex mutatorMutex;
        MutexImpl mutexImpl;
        Function2 function2;
        Object obj2;
        MutatorMutex mutatorMutex2;
        Throwable th;
        MutatorMutex.Mutator mutator2;
        Mutex mutex;
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
                    MutatorMutex.access$tryMutateOrCancel(this.this$0, mutator);
                    mutatorMutex = this.this$0;
                    mutexImpl = mutatorMutex.mutex;
                    function2 = this.$block;
                    Object obj3 = this.$receiver;
                    this.L$0 = mutator;
                    this.L$1 = mutexImpl;
                    this.L$2 = function2;
                    this.L$3 = obj3;
                    this.L$4 = mutatorMutex;
                    this.label = 1;
                    if (mutexImpl.lock(this) != coroutineSingletons) {
                        obj2 = obj3;
                    }
                    return coroutineSingletons;
                }
                if (r1 != 1) {
                    if (r1 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mutatorMutex2 = (MutatorMutex) this.L$2;
                    mutex = (Mutex) this.L$1;
                    mutator2 = (MutatorMutex.Mutator) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                        mutex.unlock(null);
                        return obj;
                    } catch (Throwable th2) {
                        th = th2;
                        mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                        throw th;
                    }
                }
                MutatorMutex mutatorMutex3 = (MutatorMutex) this.L$4;
                obj2 = this.L$3;
                Function2 function22 = (Function2) this.L$2;
                ?? r6 = (Mutex) this.L$1;
                MutatorMutex.Mutator mutator3 = (MutatorMutex.Mutator) this.L$0;
                ResultKt.throwOnFailure(obj);
                function2 = function22;
                mutexImpl = r6;
                mutatorMutex = mutatorMutex3;
                mutator = mutator3;
                this.L$0 = mutator;
                this.L$1 = mutexImpl;
                this.L$2 = mutatorMutex;
                this.L$3 = null;
                this.L$4 = null;
                this.label = 2;
                Object invoke = function2.invoke(obj2, this);
                if (invoke != coroutineSingletons) {
                    mutatorMutex2 = mutatorMutex;
                    obj = invoke;
                    mutator2 = mutator;
                    mutex = mutexImpl;
                    mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                    mutex.unlock(null);
                    return obj;
                }
                return coroutineSingletons;
            } catch (Throwable th3) {
                mutatorMutex2 = mutatorMutex;
                th = th3;
                mutator2 = mutator;
                mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                throw th;
            }
        } catch (Throwable th4) {
            r1.unlock(null);
            throw th4;
        }
    }
}
