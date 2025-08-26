package androidx.compose.foundation;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class MutatorMutex {
    public final AtomicReference currentMutator = new AtomicReference(null);
    public final MutexImpl mutex = MutexKt.Mutex$default();

    final class Mutator {
        public final Job job;
        public final MutatePriority priority;

        public Mutator(MutatePriority mutatePriority, Job job) {
            this.priority = mutatePriority;
            this.job = job;
        }
    }

    /* renamed from: androidx.compose.foundation.MutatorMutex$mutate$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $block;
        final /* synthetic */ MutatePriority $priority;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        final /* synthetic */ MutatorMutex this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MutatePriority mutatePriority, MutatorMutex mutatorMutex, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$priority = mutatePriority;
            this.this$0 = mutatorMutex;
            this.$block = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$priority, this.this$0, this.$block, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.Mutex] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutator mutator;
            MutatorMutex mutatorMutex;
            Mutex mutex;
            Function1 function1;
            MutatorMutex mutatorMutex2;
            Throwable th;
            Mutator mutator2;
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
                        mutator = new Mutator(mutatePriority, (Job) element);
                        MutatorMutex.access$tryMutateOrCancel(this.this$0, mutator);
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
                        mutator2 = (Mutator) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                            mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                            mutex2.unlock(null);
                            return obj;
                        } catch (Throwable th2) {
                            th = th2;
                            mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                            throw th;
                        }
                    }
                    MutatorMutex mutatorMutex3 = (MutatorMutex) this.L$3;
                    function1 = (Function1) this.L$2;
                    mutex = (Mutex) this.L$1;
                    Mutator mutator3 = (Mutator) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutatorMutex = mutatorMutex3;
                    mutator = mutator3;
                    this.L$0 = mutator;
                    this.L$1 = mutex;
                    this.L$2 = mutatorMutex;
                    this.L$3 = null;
                    this.label = 2;
                    Object objMo781invoke = function1.mo781invoke(this);
                    if (objMo781invoke != coroutineSingletons) {
                        mutatorMutex2 = mutatorMutex;
                        obj = objMo781invoke;
                        mutator2 = mutator;
                        mutex2 = mutex;
                        mutatorMutex2.currentMutator.compareAndSet(mutator2, null);
                        mutex2.unlock(null);
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

    /* renamed from: androidx.compose.foundation.MutatorMutex$mutateWith$2, reason: invalid class name and case insensitive filesystem */
    final class C06842 extends SuspendLambda implements Function2 {
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
        public C06842(MutatePriority mutatePriority, MutatorMutex mutatorMutex, Function2 function2, Object obj, Continuation continuation) {
            super(2, continuation);
            this.$priority = mutatePriority;
            this.this$0 = mutatorMutex;
            this.$block = function2;
            this.$receiver = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C06842 c06842 = new C06842(this.$priority, this.this$0, this.$block, this.$receiver, continuation);
            c06842.L$0 = obj;
            return c06842;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06842) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int, kotlinx.coroutines.sync.Mutex] */
        /* JADX WARN: Type inference failed for: r6v4, types: [kotlinx.coroutines.sync.Mutex] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Mutator mutator;
            MutatorMutex mutatorMutex;
            MutexImpl mutexImpl;
            Function2 function2;
            Object obj2;
            MutatorMutex mutatorMutex2;
            Throwable th;
            Mutator mutator2;
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
                        mutator = new Mutator(mutatePriority, (Job) element);
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
                        mutator2 = (Mutator) this.L$0;
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
                    Mutator mutator3 = (Mutator) this.L$0;
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
                    Object objInvoke = function2.invoke(obj2, this);
                    if (objInvoke != coroutineSingletons) {
                        mutatorMutex2 = mutatorMutex;
                        obj = objInvoke;
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
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(mutatePriority, this, function1, null), continuationImpl);
    }

    public final Object mutateWith(Object obj, MutatePriority mutatePriority, Function2 function2, SuspendLambda suspendLambda) {
        return CoroutineScopeKt.coroutineScope(new C06842(mutatePriority, this, function2, obj, null), suspendLambda);
    }
}
