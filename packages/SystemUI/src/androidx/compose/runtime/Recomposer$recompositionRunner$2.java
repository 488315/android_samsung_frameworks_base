package androidx.compose.runtime;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSet;
import androidx.compose.runtime.Recomposer;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.Links;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSet;
import androidx.compose.runtime.snapshots.ObserverHandle;
import androidx.compose.runtime.snapshots.ReaderKind;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.Snapshot$Companion$$ExternalSyntheticLambda0;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import java.util.List;
import java.util.Set;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
final class Recomposer$recompositionRunner$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function3 $block;
    final /* synthetic */ MonotonicFrameClock $parentFrameClock;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ Recomposer this$0;

    /* renamed from: androidx.compose.runtime.Recomposer$recompositionRunner$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function3 $block;
        final /* synthetic */ MonotonicFrameClock $parentFrameClock;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function3 function3, MonotonicFrameClock monotonicFrameClock, Continuation continuation) {
            super(2, continuation);
            this.$block = function3;
            this.$parentFrameClock = monotonicFrameClock;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$block, this.$parentFrameClock, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Function3 function3 = this.$block;
                MonotonicFrameClock monotonicFrameClock = this.$parentFrameClock;
                this.label = 1;
                if (function3.invoke(coroutineScope, monotonicFrameClock, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Recomposer$recompositionRunner$2(Recomposer recomposer, Function3 function3, MonotonicFrameClock monotonicFrameClock, Continuation continuation) {
        super(2, continuation);
        this.this$0 = recomposer;
        this.$block = function3;
        this.$parentFrameClock = monotonicFrameClock;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        Recomposer$recompositionRunner$2 recomposer$recompositionRunner$2 = new Recomposer$recompositionRunner$2(this.this$0, this.$block, this.$parentFrameClock, continuation);
        recomposer$recompositionRunner$2.L$0 = obj;
        return recomposer$recompositionRunner$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Recomposer$recompositionRunner$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x0139 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0113 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        Job job;
        StateFlowImpl stateFlowImpl;
        PersistentSet persistentSet;
        PersistentOrderedSet persistentOrderedSet;
        ObserverHandle observerHandle;
        Throwable th;
        List knownCompositions;
        Recomposer recomposer;
        Recomposer recomposer2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            job = JobKt.getJob(((CoroutineScope) this.L$0).getCoroutineContext());
            Recomposer recomposer3 = this.this$0;
            synchronized (recomposer3.stateLock) {
                Throwable th2 = recomposer3.closeCause;
                if (th2 != null) {
                    throw th2;
                }
                if (((Recomposer.State) recomposer3._state.getValue()).compareTo(Recomposer.State.ShuttingDown) <= 0) {
                    throw new IllegalStateException("Recomposer shut down");
                }
                if (recomposer3.runnerJob != null) {
                    throw new IllegalStateException("Recomposer already running");
                }
                recomposer3.runnerJob = job;
                recomposer3.deriveStateLocked();
            }
            Snapshot.Companion companion = Snapshot.Companion;
            final Recomposer recomposer4 = this.this$0;
            Function2 function2 = new Function2() { // from class: androidx.compose.runtime.Recomposer$recompositionRunner$2$unregisterApplyObserver$1
                {
                    super(2);
                }

                /* JADX WARN: Removed duplicated region for block: B:24:0x0071 A[Catch: all -> 0x006f, TryCatch #0 {all -> 0x006f, blocks: (B:4:0x0010, B:6:0x0020, B:8:0x0026, B:11:0x0035, B:13:0x0045, B:15:0x0051, B:17:0x005a, B:19:0x0063, B:24:0x0071, B:25:0x0074, B:28:0x007b, B:38:0x00a1, B:29:0x007d, B:30:0x0083, B:32:0x0089, B:34:0x0091, B:37:0x009d), top: B:48:0x0010 }] */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0079  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj2, Object obj3) {
                    CancellableContinuation cancellableContinuationDeriveStateLocked;
                    Set set = (Set) obj2;
                    Recomposer recomposer5 = recomposer4;
                    synchronized (recomposer5.stateLock) {
                        try {
                            if (((Recomposer.State) recomposer5._state.getValue()).compareTo(Recomposer.State.Idle) >= 0) {
                                MutableScatterSet mutableScatterSet = recomposer5.snapshotInvalidations;
                                if (set instanceof ScatterSetWrapper) {
                                    ScatterSet scatterSet = ((ScatterSetWrapper) set).set;
                                    Object[] objArr = scatterSet.elements;
                                    long[] jArr = scatterSet.metadata;
                                    int length = jArr.length - 2;
                                    if (length >= 0) {
                                        int i2 = 0;
                                        while (true) {
                                            long j = jArr[i2];
                                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                                int i3 = 8 - ((~(i2 - length)) >>> 31);
                                                for (int i4 = 0; i4 < i3; i4++) {
                                                    if ((255 & j) < 128) {
                                                        Object obj4 = objArr[(i2 << 3) + i4];
                                                        if (obj4 instanceof StateObjectImpl) {
                                                            int i5 = ReaderKind.$r8$clinit;
                                                            if (((StateObjectImpl) obj4).m351isReadInh_f27i8$runtime_release(1)) {
                                                                mutableScatterSet.add(obj4);
                                                            }
                                                        }
                                                    }
                                                    j >>= 8;
                                                }
                                                if (i3 != 8) {
                                                    break;
                                                }
                                                if (i2 == length) {
                                                    break;
                                                }
                                                i2++;
                                            }
                                        }
                                    }
                                } else {
                                    for (Object obj5 : set) {
                                        if (obj5 instanceof StateObjectImpl) {
                                            int i6 = ReaderKind.$r8$clinit;
                                            if (!((StateObjectImpl) obj5).m351isReadInh_f27i8$runtime_release(1)) {
                                            }
                                        }
                                        mutableScatterSet.add(obj5);
                                    }
                                }
                                cancellableContinuationDeriveStateLocked = recomposer5.deriveStateLocked();
                            } else {
                                cancellableContinuationDeriveStateLocked = null;
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                    if (cancellableContinuationDeriveStateLocked != null) {
                        int i7 = Result.$r8$clinit;
                        ((CancellableContinuationImpl) cancellableContinuationDeriveStateLocked).resumeWith(Unit.INSTANCE);
                    }
                    return Unit.INSTANCE;
                }
            };
            companion.getClass();
            Snapshot$Companion$$ExternalSyntheticLambda0 snapshot$Companion$$ExternalSyntheticLambda0RegisterApplyObserver = Snapshot.Companion.registerApplyObserver(function2);
            Recomposer.Companion companion2 = Recomposer.Companion;
            Recomposer.RecomposerInfoImpl recomposerInfoImpl = this.this$0.recomposerInfo;
            companion2.getClass();
            try {
                do {
                    stateFlowImpl = Recomposer._runningRecomposers;
                    persistentSet = (PersistentSet) stateFlowImpl.getValue();
                    persistentOrderedSet = (PersistentOrderedSet) persistentSet;
                    if (!persistentOrderedSet.hashMap.containsKey(recomposerInfoImpl)) {
                        if (persistentOrderedSet.isEmpty()) {
                            persistentOrderedSet = new PersistentOrderedSet(recomposerInfoImpl, recomposerInfoImpl, persistentOrderedSet.hashMap.put((Object) recomposerInfoImpl, new Links()));
                        } else {
                            Object obj2 = persistentOrderedSet.lastElement;
                            Object obj3 = persistentOrderedSet.hashMap.get(obj2);
                            obj3.getClass();
                            persistentOrderedSet = new PersistentOrderedSet(persistentOrderedSet.firstElement, recomposerInfoImpl, persistentOrderedSet.hashMap.put(obj2, new Links(((Links) obj3).previous, recomposerInfoImpl)).put((Object) recomposerInfoImpl, new Links(obj2)));
                        }
                    }
                    if (persistentSet != persistentOrderedSet) {
                    }
                    break;
                } while (!stateFlowImpl.updateState(persistentSet, persistentOrderedSet));
                break;
                Recomposer recomposer5 = this.this$0;
                synchronized (recomposer5.stateLock) {
                    knownCompositions = recomposer5.getKnownCompositions();
                }
                int size = knownCompositions.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((CompositionImpl) ((ControlledComposition) knownCompositions.get(i2))).invalidateAll();
                }
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$block, this.$parentFrameClock, null);
                this.L$0 = job;
                this.L$1 = snapshot$Companion$$ExternalSyntheticLambda0RegisterApplyObserver;
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(anonymousClass3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                observerHandle = snapshot$Companion$$ExternalSyntheticLambda0RegisterApplyObserver;
                observerHandle.dispose();
                recomposer2 = this.this$0;
                synchronized (recomposer2.stateLock) {
                }
            } catch (Throwable th3) {
                observerHandle = snapshot$Companion$$ExternalSyntheticLambda0RegisterApplyObserver;
                th = th3;
                observerHandle.dispose();
                recomposer = this.this$0;
                synchronized (recomposer.stateLock) {
                    try {
                        if (recomposer.runnerJob == job) {
                            recomposer.runnerJob = null;
                        }
                        recomposer.deriveStateLocked();
                    } catch (Throwable th4) {
                        throw th4;
                    }
                }
                Recomposer.Companion.access$removeRunning(Recomposer.Companion, this.this$0.recomposerInfo);
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            observerHandle = (ObserverHandle) this.L$1;
            job = (Job) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                observerHandle.dispose();
                recomposer2 = this.this$0;
                synchronized (recomposer2.stateLock) {
                    try {
                        if (recomposer2.runnerJob == job) {
                            recomposer2.runnerJob = null;
                        }
                        recomposer2.deriveStateLocked();
                    } catch (Throwable th5) {
                        throw th5;
                    }
                }
                Recomposer.Companion.access$removeRunning(Recomposer.Companion, this.this$0.recomposerInfo);
                return Unit.INSTANCE;
            } catch (Throwable th6) {
                th = th6;
                observerHandle.dispose();
                recomposer = this.this$0;
                synchronized (recomposer.stateLock) {
                }
            }
        }
    }
}
