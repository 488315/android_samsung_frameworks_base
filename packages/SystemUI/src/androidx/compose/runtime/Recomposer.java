package androidx.compose.runtime;

import android.util.Log;
import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ObjectList;
import androidx.collection.ObjectListKt;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.Recomposer;
import androidx.compose.runtime.collection.MultiValueMap;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.TrieNode;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.Links;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.EndOfChain;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.RememberEventDispatcher;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import androidx.compose.runtime.internal.Trace;
import androidx.compose.runtime.snapshots.MutableSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import androidx.compose.runtime.snapshots.SnapshotKt;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceBuilderIterator;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class Recomposer extends CompositionContext {
    public static final Companion Companion = new Companion(null);
    public static final AtomicReference _hotReloadEnabled;
    public static final StateFlowImpl _runningRecomposers;
    public final List _knownCompositions;
    public List _knownCompositionsCache;
    public final StateFlowImpl _state;
    public final BroadcastFrameClock broadcastFrameClock;
    public Throwable closeCause;
    public final MutableVector compositionInvalidations;
    public final List compositionsAwaitingApply;
    public Set compositionsRemoved;
    public final CoroutineContext effectCoroutineContext;
    public final JobImpl effectJob;
    public RecomposerErrorState errorState;
    public List failedCompositions;
    public boolean frameClockPaused;
    public final List movableContentAwaitingInsert;
    public final MutableScatterMap movableContentNestedExtractionsPending;
    public final NestedContentMap movableContentNestedStatesAvailable;
    public final MutableScatterMap movableContentRemoved;
    public final MutableScatterMap movableContentStatesAvailable;
    public final RecomposerInfoImpl recomposerInfo;
    public Job runnerJob;
    public MutableScatterSet snapshotInvalidations;
    public final Object stateLock;
    public CancellableContinuationImpl workContinuation;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$removeRunning(Companion companion, RecomposerInfoImpl recomposerInfoImpl) {
            StateFlowImpl stateFlowImpl;
            PersistentSet persistentSet;
            PersistentOrderedSet persistentOrderedSet;
            companion.getClass();
            do {
                stateFlowImpl = Recomposer._runningRecomposers;
                persistentSet = (PersistentSet) stateFlowImpl.getValue();
                persistentOrderedSet = (PersistentOrderedSet) persistentSet;
                Links links = (Links) persistentOrderedSet.hashMap.get(recomposerInfoImpl);
                if (links != null) {
                    PersistentHashMap persistentHashMapPut = persistentOrderedSet.hashMap;
                    TrieNode trieNodeRemove = persistentHashMapPut.node.remove(recomposerInfoImpl != null ? recomposerInfoImpl.hashCode() : 0, 0, recomposerInfoImpl);
                    if (persistentHashMapPut.node != trieNodeRemove) {
                        if (trieNodeRemove == null) {
                            PersistentHashMap.Companion.getClass();
                            persistentHashMapPut = PersistentHashMap.EMPTY;
                        } else {
                            persistentHashMapPut = new PersistentHashMap(trieNodeRemove, persistentHashMapPut.size - 1);
                        }
                    }
                    EndOfChain endOfChain = EndOfChain.INSTANCE;
                    Object obj = links.previous;
                    boolean z = obj != endOfChain;
                    Object obj2 = links.next;
                    if (z) {
                        Object obj3 = persistentHashMapPut.get(obj);
                        obj3.getClass();
                        persistentHashMapPut = persistentHashMapPut.put(obj, new Links(((Links) obj3).previous, obj2));
                    }
                    if (obj2 != endOfChain) {
                        Object obj4 = persistentHashMapPut.get(obj2);
                        obj4.getClass();
                        persistentHashMapPut = persistentHashMapPut.put(obj2, new Links(obj, ((Links) obj4).next));
                    }
                    Object obj5 = obj != endOfChain ? persistentOrderedSet.firstElement : obj2;
                    if (obj2 != endOfChain) {
                        obj = persistentOrderedSet.lastElement;
                    }
                    persistentOrderedSet = new PersistentOrderedSet(obj5, obj, persistentHashMapPut);
                }
                if (persistentSet == persistentOrderedSet) {
                    return;
                }
            } while (!stateFlowImpl.updateState(persistentSet, persistentOrderedSet));
        }

        private Companion() {
        }
    }

    final class RecomposerErrorState {
        public final Throwable cause;

        public RecomposerErrorState(boolean z, Throwable th) {
            this.cause = th;
        }
    }

    final class RecomposerInfoImpl {
        public RecomposerInfoImpl(Recomposer recomposer) {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State Idle;
        public static final State Inactive;
        public static final State InactivePendingWork;
        public static final State PendingWork;
        public static final State ShutDown;
        public static final State ShuttingDown;

        static {
            State state = new State("ShutDown", 0);
            ShutDown = state;
            State state2 = new State("ShuttingDown", 1);
            ShuttingDown = state2;
            State state3 = new State("Inactive", 2);
            Inactive = state3;
            State state4 = new State("InactivePendingWork", 3);
            InactivePendingWork = state4;
            State state5 = new State("Idle", 4);
            Idle = state5;
            State state6 = new State("PendingWork", 5);
            PendingWork = state6;
            State[] stateArr = {state, state2, state3, state4, state5, state6};
            $VALUES = stateArr;
            EnumEntriesKt.enumEntries(stateArr);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    /* renamed from: androidx.compose.runtime.Recomposer$join$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((State) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(((State) this.L$0) == State.ShutDown);
        }
    }

    /* renamed from: androidx.compose.runtime.Recomposer$runRecomposeAndApplyChanges$2, reason: invalid class name and case insensitive filesystem */
    final class C07422 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        int label;

        public C07422(Continuation continuation) {
            super(3, continuation);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00c1  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0106  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final void access$invokeSuspend$clearRecompositionState(Recomposer recomposer, List list, List list2, List list3, MutableScatterSet mutableScatterSet, MutableScatterSet mutableScatterSet2, MutableScatterSet mutableScatterSet3, MutableScatterSet mutableScatterSet4) {
            char c;
            long j;
            long j2;
            synchronized (recomposer.stateLock) {
                try {
                    list.clear();
                    list2.clear();
                    int size = list3.size();
                    for (int i = 0; i < size; i++) {
                        CompositionImpl compositionImpl = (CompositionImpl) ((ControlledComposition) list3.get(i));
                        compositionImpl.abandonChanges();
                        recomposer.recordFailedCompositionLocked(compositionImpl);
                    }
                    list3.clear();
                    Object[] objArr = mutableScatterSet.elements;
                    long[] jArr = mutableScatterSet.metadata;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i2 = 0;
                        j = 255;
                        while (true) {
                            long j3 = jArr[i2];
                            c = 7;
                            j2 = -9187201950435737472L;
                            if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i3 = 8 - ((~(i2 - length)) >>> 31);
                                for (int i4 = 0; i4 < i3; i4++) {
                                    if ((j3 & 255) < 128) {
                                        CompositionImpl compositionImpl2 = (CompositionImpl) ((ControlledComposition) objArr[(i2 << 3) + i4]);
                                        compositionImpl2.abandonChanges();
                                        recomposer.recordFailedCompositionLocked(compositionImpl2);
                                    }
                                    j3 >>= 8;
                                }
                                if (i3 != 8) {
                                    break;
                                } else if (i2 == length) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                    } else {
                        c = 7;
                        j = 255;
                        j2 = -9187201950435737472L;
                    }
                    mutableScatterSet.clear();
                    Object[] objArr2 = mutableScatterSet2.elements;
                    long[] jArr2 = mutableScatterSet2.metadata;
                    int length2 = jArr2.length - 2;
                    if (length2 >= 0) {
                        int i5 = 0;
                        while (true) {
                            long j4 = jArr2[i5];
                            if ((((~j4) << c) & j4 & j2) != j2) {
                                int i6 = 8 - ((~(i5 - length2)) >>> 31);
                                for (int i7 = 0; i7 < i6; i7++) {
                                    if ((j4 & j) < 128) {
                                        ((CompositionImpl) ((ControlledComposition) objArr2[(i5 << 3) + i7])).changesApplied();
                                    }
                                    j4 >>= 8;
                                }
                                if (i6 != 8) {
                                    break;
                                } else if (i5 == length2) {
                                    break;
                                } else {
                                    i5++;
                                }
                            }
                        }
                    }
                    mutableScatterSet2.clear();
                    mutableScatterSet3.clear();
                    Object[] objArr3 = mutableScatterSet4.elements;
                    long[] jArr3 = mutableScatterSet4.metadata;
                    int length3 = jArr3.length - 2;
                    if (length3 >= 0) {
                        int i8 = 0;
                        while (true) {
                            long j5 = jArr3[i8];
                            if ((((~j5) << c) & j5 & j2) != j2) {
                                int i9 = 8 - ((~(i8 - length3)) >>> 31);
                                for (int i10 = 0; i10 < i9; i10++) {
                                    if ((j5 & j) < 128) {
                                        CompositionImpl compositionImpl3 = (CompositionImpl) ((ControlledComposition) objArr3[(i8 << 3) + i10]);
                                        compositionImpl3.abandonChanges();
                                        recomposer.recordFailedCompositionLocked(compositionImpl3);
                                    }
                                    j5 >>= 8;
                                }
                                if (i9 != 8) {
                                    break;
                                } else if (i8 == length3) {
                                    break;
                                } else {
                                    i8++;
                                }
                            }
                        }
                    }
                    mutableScatterSet4.clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static final void access$invokeSuspend$fillToInsert(List list, Recomposer recomposer) {
            list.clear();
            synchronized (recomposer.stateLock) {
                try {
                    ArrayList arrayList = (ArrayList) recomposer.movableContentAwaitingInsert;
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        list.add((MovableContentStateReference) arrayList.get(i));
                    }
                    ((ArrayList) recomposer.movableContentAwaitingInsert).clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C07422 c07422 = Recomposer.this.new C07422((Continuation) obj3);
            c07422.L$0 = (MonotonicFrameClock) obj2;
            return c07422.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x0154, code lost:
        
            if (r3.withFrameNanos(r17, r0) == r2) goto L42;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:14:0x00c0 A[DONT_GENERATE] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0121  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0221  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0154 -> B:43:0x0157). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:73:0x0221 -> B:12:0x00bb). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            MonotonicFrameClock monotonicFrameClock;
            MutableScatterSet mutableScatterSetMutableScatterSetOf;
            MutableScatterSet mutableScatterSetMutableScatterSetOf2;
            List list;
            Set set;
            List list2;
            MutableScatterSet mutableScatterSet;
            List list3;
            MutableScatterSet mutableScatterSet2;
            final MutableScatterSet mutableScatterSet3;
            Object result;
            CancellableContinuationImpl cancellableContinuationImpl;
            int i;
            CoroutineSingletons coroutineSingletons;
            MonotonicFrameClock monotonicFrameClock2;
            MutableObjectList mutableObjectList;
            C07422 c07422 = this;
            int i2 = 1;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = c07422.label;
            int i4 = 2;
            if (i3 == 0) {
                ResultKt.throwOnFailure(obj);
                monotonicFrameClock = (MonotonicFrameClock) c07422.L$0;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                mutableScatterSetMutableScatterSetOf = ScatterSetKt.mutableScatterSetOf();
                MutableScatterSet mutableScatterSetMutableScatterSetOf3 = ScatterSetKt.mutableScatterSetOf();
                MutableScatterSet mutableScatterSet4 = new MutableScatterSet(0, 1, null);
                ScatterSetWrapper scatterSetWrapper = new ScatterSetWrapper(mutableScatterSet4);
                mutableScatterSetMutableScatterSetOf2 = ScatterSetKt.mutableScatterSetOf();
                list = arrayList;
                set = scatterSetWrapper;
                list2 = arrayList2;
                mutableScatterSet = mutableScatterSet4;
                list3 = arrayList3;
                mutableScatterSet2 = mutableScatterSetMutableScatterSetOf3;
                synchronized (Recomposer.this.stateLock) {
                }
            } else {
                if (i3 != 1) {
                    if (i3 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    MutableScatterSet mutableScatterSet5 = (MutableScatterSet) c07422.L$8;
                    set = (Set) c07422.L$7;
                    mutableScatterSet = (MutableScatterSet) c07422.L$6;
                    mutableScatterSet2 = (MutableScatterSet) c07422.L$5;
                    mutableScatterSetMutableScatterSetOf = (MutableScatterSet) c07422.L$4;
                    list3 = (List) c07422.L$3;
                    list2 = (List) c07422.L$2;
                    list = (List) c07422.L$1;
                    MonotonicFrameClock monotonicFrameClock3 = (MonotonicFrameClock) c07422.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutableScatterSetMutableScatterSetOf2 = mutableScatterSet5;
                    monotonicFrameClock = monotonicFrameClock3;
                    Recomposer recomposer = Recomposer.this;
                    synchronized (recomposer.stateLock) {
                        try {
                            MutableScatterMap mutableScatterMap = recomposer.movableContentRemoved;
                            if ((mutableScatterMap._size != 0 ? i2 : 0) != 0) {
                                MutableObjectList mutableObjectListM347valuesimpl = MultiValueMap.m347valuesimpl(mutableScatterMap);
                                recomposer.movableContentRemoved.clear();
                                NestedContentMap nestedContentMap = recomposer.movableContentNestedStatesAvailable;
                                i = i2;
                                nestedContentMap.contentMap.clear();
                                nestedContentMap.containerMap.clear();
                                recomposer.movableContentNestedExtractionsPending.clear();
                                mutableObjectList = new MutableObjectList(mutableObjectListM347valuesimpl._size);
                                Object[] objArr = mutableObjectListM347valuesimpl.content;
                                int i5 = mutableObjectListM347valuesimpl._size;
                                int i6 = 0;
                                while (i6 < i5) {
                                    int i7 = i6;
                                    MovableContentStateReference movableContentStateReference = (MovableContentStateReference) objArr[i6];
                                    mutableObjectList.add(new Pair(movableContentStateReference, recomposer.movableContentStatesAvailable.get(movableContentStateReference)));
                                    i6 = i7 + 1;
                                    monotonicFrameClock = monotonicFrameClock;
                                    coroutineSingletons2 = coroutineSingletons2;
                                }
                                coroutineSingletons = coroutineSingletons2;
                                monotonicFrameClock2 = monotonicFrameClock;
                                recomposer.movableContentStatesAvailable.clear();
                            } else {
                                i = i2;
                                coroutineSingletons = coroutineSingletons2;
                                monotonicFrameClock2 = monotonicFrameClock;
                                mutableObjectList = ObjectListKt.EmptyObjectList;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    Object[] objArr2 = mutableObjectList.content;
                    int i8 = mutableObjectList._size;
                    for (int i9 = 0; i9 < i8; i9++) {
                        Pair pair = (Pair) objArr2[i9];
                        MovableContentStateReference movableContentStateReference2 = (MovableContentStateReference) pair.component1();
                        MovableContentState movableContentState = (MovableContentState) pair.component2();
                        if (movableContentState != null) {
                            CompositionImpl compositionImpl = (CompositionImpl) movableContentStateReference2.composition;
                            RememberEventDispatcher rememberEventDispatcher = new RememberEventDispatcher(compositionImpl.abandonSet, compositionImpl.composer.getErrorContext$runtime_release());
                            SlotWriter slotWriterOpenWriter = movableContentState.slotTable.openWriter();
                            try {
                                ComposerKt.removeCurrentGroup(slotWriterOpenWriter, rememberEventDispatcher);
                                Unit unit = Unit.INSTANCE;
                                slotWriterOpenWriter.close(i);
                                rememberEventDispatcher.dispatchRememberObservers();
                            } catch (Throwable th2) {
                                slotWriterOpenWriter.close(false);
                                throw th2;
                            }
                        }
                        i = 1;
                    }
                    c07422 = this;
                    monotonicFrameClock = monotonicFrameClock2;
                    i2 = i;
                    coroutineSingletons2 = coroutineSingletons;
                    i4 = 2;
                    synchronized (Recomposer.this.stateLock) {
                    }
                    Recomposer recomposer2 = Recomposer.this;
                    c07422.L$0 = monotonicFrameClock;
                    c07422.L$1 = list;
                    c07422.L$2 = list2;
                    c07422.L$3 = list3;
                    c07422.L$4 = mutableScatterSetMutableScatterSetOf;
                    c07422.L$5 = mutableScatterSet2;
                    c07422.L$6 = mutableScatterSet;
                    c07422.L$7 = set;
                    c07422.L$8 = mutableScatterSetMutableScatterSetOf2;
                    c07422.label = i2;
                    if (recomposer2.getHasSchedulingWork()) {
                        result = Unit.INSTANCE;
                    } else {
                        CancellableContinuationImpl cancellableContinuationImpl2 = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(c07422), i2);
                        cancellableContinuationImpl2.initCancellability();
                        synchronized (recomposer2.stateLock) {
                            if (recomposer2.getHasSchedulingWork()) {
                                cancellableContinuationImpl = cancellableContinuationImpl2;
                            } else {
                                recomposer2.workContinuation = cancellableContinuationImpl2;
                                cancellableContinuationImpl = null;
                            }
                        }
                        if (cancellableContinuationImpl != null) {
                            int i10 = Result.$r8$clinit;
                            cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                        }
                        result = cancellableContinuationImpl2.getResult();
                        if (result != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            result = Unit.INSTANCE;
                        }
                    }
                    if (result != coroutineSingletons2) {
                        mutableScatterSet3 = mutableScatterSetMutableScatterSetOf2;
                        final Set set2 = set;
                        final MutableScatterSet mutableScatterSet6 = mutableScatterSet;
                        final MutableScatterSet mutableScatterSet7 = mutableScatterSet2;
                        final MutableScatterSet mutableScatterSet8 = mutableScatterSetMutableScatterSetOf;
                        final List list4 = list3;
                        final List list5 = list2;
                        final List list6 = list;
                        if (Recomposer.access$recordComposerModifications(Recomposer.this)) {
                            mutableScatterSet = mutableScatterSet6;
                            mutableScatterSetMutableScatterSetOf2 = mutableScatterSet3;
                            list = list6;
                            list2 = list5;
                            mutableScatterSetMutableScatterSetOf = mutableScatterSet8;
                            list3 = list4;
                            mutableScatterSet2 = mutableScatterSet7;
                            set = set2;
                            c07422 = this;
                            synchronized (Recomposer.this.stateLock) {
                            }
                        } else {
                            final Recomposer recomposer3 = Recomposer.this;
                            Function1 function1 = new Function1() { // from class: androidx.compose.runtime.Recomposer.runRecomposeAndApplyChanges.2.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:103:0x01bf  */
                                /* JADX WARN: Removed duplicated region for block: B:113:0x01dc A[Catch: all -> 0x0308, TryCatch #5 {all -> 0x0308, blocks: (B:14:0x005b, B:15:0x0060, B:22:0x0081, B:23:0x0082, B:24:0x0088, B:26:0x0092, B:29:0x009f, B:41:0x00d9, B:49:0x00ef, B:79:0x0166, B:82:0x016e, B:83:0x0171, B:76:0x015a, B:85:0x0178, B:105:0x01c7, B:113:0x01dc, B:114:0x01de, B:116:0x01e2, B:117:0x01e3, B:121:0x0205, B:122:0x0206, B:108:0x01d1, B:111:0x01d8, B:112:0x01db, B:44:0x00e3, B:47:0x00eb, B:48:0x00ee, B:133:0x0231, B:135:0x023a, B:161:0x02af, B:174:0x02e3, B:137:0x0240, B:138:0x0242, B:160:0x02ae, B:176:0x02ed, B:177:0x02ee, B:181:0x02f7, B:186:0x0302, B:187:0x0305, B:188:0x0306, B:189:0x0307, B:163:0x02b5, B:164:0x02b8, B:166:0x02c1, B:167:0x02ce, B:169:0x02d4, B:170:0x02dc, B:180:0x02f1, B:115:0x01df, B:43:0x00dd, B:139:0x0243, B:141:0x0251, B:143:0x025d, B:145:0x0265, B:148:0x026f, B:149:0x0272, B:151:0x027a, B:153:0x0286, B:155:0x028c, B:158:0x029f, B:157:0x0297, B:159:0x02a2, B:78:0x0160, B:107:0x01cb, B:87:0x017e, B:90:0x0188, B:92:0x0197, B:94:0x01a1, B:96:0x01a7, B:16:0x0061, B:18:0x006a, B:21:0x007a, B:32:0x00a9, B:34:0x00b3, B:37:0x00c1, B:39:0x00cb), top: B:206:0x005b, inners: #0, #2, #3, #6, #7, #8, #13, #14, #15, #16 }] */
                                /* JADX WARN: Removed duplicated region for block: B:202:0x01df A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                /* JADX WARN: Removed duplicated region for block: B:215:0x00fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                /* JADX WARN: Removed duplicated region for block: B:223:0x017e A[EXC_TOP_SPLITTER, SYNTHETIC] */
                                /* JADX WARN: Removed duplicated region for block: B:71:0x0148  */
                                /* JADX WARN: Removed duplicated region for block: B:84:0x0172  */
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object mo781invoke(Object obj2) {
                                    boolean hasBroadcastFrameClockAwaitersLocked;
                                    int i11;
                                    Trace trace;
                                    long j;
                                    Recomposer recomposer4;
                                    long j2;
                                    long jLongValue = ((Number) obj2).longValue();
                                    Recomposer recomposer5 = recomposer3;
                                    synchronized (recomposer5.stateLock) {
                                        hasBroadcastFrameClockAwaitersLocked = recomposer5.getHasBroadcastFrameClockAwaitersLocked();
                                    }
                                    if (hasBroadcastFrameClockAwaitersLocked) {
                                        Recomposer recomposer6 = recomposer3;
                                        Trace.INSTANCE.getClass();
                                        android.os.Trace.beginSection("Recomposer:animation");
                                        try {
                                            recomposer6.broadcastFrameClock.sendFrame(jLongValue);
                                            Snapshot.Companion.getClass();
                                            Snapshot.Companion.sendApplyNotifications();
                                            Unit unit2 = Unit.INSTANCE;
                                            android.os.Trace.endSection();
                                        } finally {
                                        }
                                    }
                                    Recomposer recomposer7 = recomposer3;
                                    MutableScatterSet mutableScatterSet9 = mutableScatterSet6;
                                    MutableScatterSet mutableScatterSet10 = mutableScatterSet3;
                                    List<ControlledComposition> list7 = list6;
                                    List<MovableContentStateReference> list8 = list5;
                                    MutableScatterSet mutableScatterSet11 = mutableScatterSet8;
                                    List<ControlledComposition> list9 = list4;
                                    MutableScatterSet mutableScatterSet12 = mutableScatterSet7;
                                    Set<Object> set3 = set2;
                                    Trace.INSTANCE.getClass();
                                    android.os.Trace.beginSection("Recomposer:recompose");
                                    try {
                                        Recomposer.access$recordComposerModifications(recomposer7);
                                        synchronized (recomposer7.stateLock) {
                                            try {
                                                MutableVector mutableVector = recomposer7.compositionInvalidations;
                                                Object[] objArr3 = mutableVector.content;
                                                int i12 = mutableVector.size;
                                                for (int i13 = 0; i13 < i12; i13++) {
                                                    list7.add((ControlledComposition) objArr3[i13]);
                                                }
                                                recomposer7.compositionInvalidations.clear();
                                                Unit unit3 = Unit.INSTANCE;
                                            } finally {
                                            }
                                        }
                                        mutableScatterSet9.clear();
                                        mutableScatterSet10.clear();
                                        while (true) {
                                            if (list7.isEmpty() && list8.isEmpty()) {
                                                break;
                                            }
                                            try {
                                                int size = list7.size();
                                                for (int i14 = 0; i14 < size; i14++) {
                                                    try {
                                                        ControlledComposition controlledComposition = list7.get(i14);
                                                        ControlledComposition controlledCompositionAccess$performRecompose = Recomposer.access$performRecompose(recomposer7, controlledComposition, mutableScatterSet9);
                                                        if (controlledCompositionAccess$performRecompose != null) {
                                                            list9.add(controlledCompositionAccess$performRecompose);
                                                            Unit unit4 = Unit.INSTANCE;
                                                        }
                                                        mutableScatterSet10.add(controlledComposition);
                                                    } catch (Throwable th3) {
                                                        th = th3;
                                                        i11 = 2;
                                                        try {
                                                            Recomposer.processCompositionError$default(recomposer7, th, i11);
                                                            C07422.access$invokeSuspend$clearRecompositionState(recomposer7, list7, list8, list9, mutableScatterSet11, mutableScatterSet12, mutableScatterSet9, mutableScatterSet10);
                                                            trace = Trace.INSTANCE;
                                                            trace.getClass();
                                                            android.os.Trace.endSection();
                                                            return Unit.INSTANCE;
                                                        } finally {
                                                            list7.clear();
                                                        }
                                                    }
                                                }
                                                list7.clear();
                                                if (mutableScatterSet9.isNotEmpty() || recomposer7.compositionInvalidations.size != 0) {
                                                    synchronized (recomposer7.stateLock) {
                                                        try {
                                                            List knownCompositions = recomposer7.getKnownCompositions();
                                                            int size2 = knownCompositions.size();
                                                            for (int i15 = 0; i15 < size2; i15++) {
                                                                ControlledComposition controlledComposition2 = (ControlledComposition) knownCompositions.get(i15);
                                                                if (!mutableScatterSet10.contains(controlledComposition2)) {
                                                                    CompositionImpl compositionImpl2 = (CompositionImpl) controlledComposition2;
                                                                    if (compositionImpl2.observesAnyOf(set3)) {
                                                                        list7.add(compositionImpl2);
                                                                    }
                                                                }
                                                            }
                                                            MutableVector mutableVector2 = recomposer7.compositionInvalidations;
                                                            int i16 = mutableVector2.size;
                                                            int i17 = 0;
                                                            for (int i18 = 0; i18 < i16; i18++) {
                                                                ControlledComposition controlledComposition3 = (ControlledComposition) mutableVector2.content[i18];
                                                                if (!mutableScatterSet10.contains(controlledComposition3) && !list7.contains(controlledComposition3)) {
                                                                    list7.add(controlledComposition3);
                                                                    i17++;
                                                                } else if (i17 > 0) {
                                                                    Object[] objArr4 = mutableVector2.content;
                                                                    objArr4[i18 - i17] = objArr4[i18];
                                                                }
                                                            }
                                                            int i19 = i16 - i17;
                                                            Arrays.fill(mutableVector2.content, i19, i16, (Object) null);
                                                            mutableVector2.size = i19;
                                                            Unit unit5 = Unit.INSTANCE;
                                                        } finally {
                                                        }
                                                    }
                                                }
                                                if (list7.isEmpty()) {
                                                    try {
                                                        C07422.access$invokeSuspend$fillToInsert(list8, recomposer7);
                                                        while (!list8.isEmpty()) {
                                                            List listPerformInsertValues = recomposer7.performInsertValues(list8, mutableScatterSet9);
                                                            mutableScatterSet11.getClass();
                                                            Iterator it = listPerformInsertValues.iterator();
                                                            while (it.hasNext()) {
                                                                mutableScatterSet11.plusAssign(it.next());
                                                            }
                                                            C07422.access$invokeSuspend$fillToInsert(list8, recomposer7);
                                                        }
                                                    } catch (Throwable th4) {
                                                        Recomposer.processCompositionError$default(recomposer7, th4, 2);
                                                        C07422.access$invokeSuspend$clearRecompositionState(recomposer7, list7, list8, list9, mutableScatterSet11, mutableScatterSet12, mutableScatterSet9, mutableScatterSet10);
                                                        trace = Trace.INSTANCE;
                                                    }
                                                }
                                            } catch (Throwable th5) {
                                                th = th5;
                                                i11 = 2;
                                            }
                                            trace.getClass();
                                            android.os.Trace.endSection();
                                            return Unit.INSTANCE;
                                        }
                                        if (list9.isEmpty()) {
                                            if (mutableScatterSet11.isNotEmpty()) {
                                            }
                                            if (mutableScatterSet12.isNotEmpty()) {
                                            }
                                        } else {
                                            try {
                                                int size3 = list9.size();
                                                for (int i20 = 0; i20 < size3; i20++) {
                                                    mutableScatterSet12.add(list9.get(i20));
                                                }
                                                int size4 = list9.size();
                                                for (int i21 = 0; i21 < size4; i21++) {
                                                    ((CompositionImpl) list9.get(i21)).applyChanges();
                                                }
                                                if (mutableScatterSet11.isNotEmpty()) {
                                                    j = 128;
                                                    j2 = 255;
                                                } else {
                                                    try {
                                                        mutableScatterSet12.plusAssign((ScatterSet) mutableScatterSet11);
                                                        Object[] objArr5 = mutableScatterSet11.elements;
                                                        j = 128;
                                                        long[] jArr = mutableScatterSet11.metadata;
                                                        int length = jArr.length - 2;
                                                        if (length >= 0) {
                                                            int i22 = 0;
                                                            j2 = 255;
                                                            while (true) {
                                                                long j3 = jArr[i22];
                                                                recomposer4 = recomposer7;
                                                                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                    int i23 = 8 - ((~(i22 - length)) >>> 31);
                                                                    for (int i24 = 0; i24 < i23; i24++) {
                                                                        if ((j3 & 255) < 128) {
                                                                            try {
                                                                                ((CompositionImpl) ((ControlledComposition) objArr5[(i22 << 3) + i24])).applyLateChanges();
                                                                            } catch (Throwable th6) {
                                                                                th = th6;
                                                                                recomposer7 = recomposer4;
                                                                                try {
                                                                                    Recomposer.processCompositionError$default(recomposer7, th, 6);
                                                                                    C07422.access$invokeSuspend$clearRecompositionState(recomposer7, list7, list8, list9, mutableScatterSet11, mutableScatterSet12, mutableScatterSet9, mutableScatterSet10);
                                                                                    trace = Trace.INSTANCE;
                                                                                    trace.getClass();
                                                                                    android.os.Trace.endSection();
                                                                                    return Unit.INSTANCE;
                                                                                } finally {
                                                                                    mutableScatterSet11.clear();
                                                                                }
                                                                            }
                                                                        }
                                                                        j3 >>= 8;
                                                                    }
                                                                    if (i23 != 8) {
                                                                        break;
                                                                    }
                                                                    if (i22 == length) {
                                                                        break;
                                                                    }
                                                                    i22++;
                                                                    recomposer7 = recomposer4;
                                                                }
                                                            }
                                                        } else {
                                                            recomposer4 = recomposer7;
                                                            j2 = 255;
                                                        }
                                                        recomposer7 = recomposer4;
                                                    } catch (Throwable th7) {
                                                        th = th7;
                                                    }
                                                }
                                                if (mutableScatterSet12.isNotEmpty()) {
                                                    synchronized (recomposer7.stateLock) {
                                                    }
                                                } else {
                                                    try {
                                                        Object[] objArr6 = mutableScatterSet12.elements;
                                                        long[] jArr2 = mutableScatterSet12.metadata;
                                                        int length2 = jArr2.length - 2;
                                                        if (length2 >= 0) {
                                                            int i25 = 0;
                                                            while (true) {
                                                                long j4 = jArr2[i25];
                                                                Object[] objArr7 = objArr6;
                                                                long[] jArr3 = jArr2;
                                                                if ((((~j4) << 7) & j4 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                                    int i26 = 8 - ((~(i25 - length2)) >>> 31);
                                                                    for (int i27 = 0; i27 < i26; i27++) {
                                                                        if ((j4 & j2) < j) {
                                                                            ((CompositionImpl) ((ControlledComposition) objArr7[(i25 << 3) + i27])).changesApplied();
                                                                        }
                                                                        j4 >>= 8;
                                                                    }
                                                                    if (i26 != 8) {
                                                                        break;
                                                                    }
                                                                    if (i25 == length2) {
                                                                        break;
                                                                    }
                                                                    i25++;
                                                                    objArr6 = objArr7;
                                                                    jArr2 = jArr3;
                                                                }
                                                            }
                                                        }
                                                        synchronized (recomposer7.stateLock) {
                                                            recomposer7.deriveStateLocked();
                                                        }
                                                        Snapshot.Companion.getClass();
                                                        SnapshotKt.currentSnapshot().notifyObjectsInitialized$runtime_release();
                                                        mutableScatterSet10.clear();
                                                        mutableScatterSet9.clear();
                                                        recomposer7.compositionsRemoved = null;
                                                        Unit unit6 = Unit.INSTANCE;
                                                        trace = Trace.INSTANCE;
                                                    } catch (Throwable th8) {
                                                        try {
                                                            Recomposer.processCompositionError$default(recomposer7, th8, 6);
                                                            C07422.access$invokeSuspend$clearRecompositionState(recomposer7, list7, list8, list9, mutableScatterSet11, mutableScatterSet12, mutableScatterSet9, mutableScatterSet10);
                                                            trace = Trace.INSTANCE;
                                                        } finally {
                                                            mutableScatterSet12.clear();
                                                        }
                                                    }
                                                }
                                            } catch (Throwable th9) {
                                                try {
                                                    Recomposer.processCompositionError$default(recomposer7, th9, 6);
                                                    C07422.access$invokeSuspend$clearRecompositionState(recomposer7, list7, list8, list9, mutableScatterSet11, mutableScatterSet12, mutableScatterSet9, mutableScatterSet10);
                                                    trace = Trace.INSTANCE;
                                                } finally {
                                                    list9.clear();
                                                }
                                            }
                                        }
                                        trace.getClass();
                                        android.os.Trace.endSection();
                                        return Unit.INSTANCE;
                                    } finally {
                                    }
                                }
                            };
                            mutableScatterSet = mutableScatterSet6;
                            mutableScatterSetMutableScatterSetOf2 = mutableScatterSet3;
                            list = list6;
                            list2 = list5;
                            mutableScatterSetMutableScatterSetOf = mutableScatterSet8;
                            list3 = list4;
                            mutableScatterSet2 = mutableScatterSet7;
                            set = set2;
                            c07422.L$0 = monotonicFrameClock;
                            c07422.L$1 = list;
                            c07422.L$2 = list2;
                            c07422.L$3 = list3;
                            c07422.L$4 = mutableScatterSetMutableScatterSetOf;
                            c07422.L$5 = mutableScatterSet2;
                            c07422.L$6 = mutableScatterSet;
                            c07422.L$7 = set;
                            c07422.L$8 = mutableScatterSetMutableScatterSetOf2;
                            c07422.label = i4;
                        }
                    }
                    return coroutineSingletons2;
                }
                MutableScatterSet mutableScatterSet9 = (MutableScatterSet) c07422.L$8;
                set = (Set) c07422.L$7;
                mutableScatterSet = (MutableScatterSet) c07422.L$6;
                mutableScatterSet2 = (MutableScatterSet) c07422.L$5;
                mutableScatterSetMutableScatterSetOf = (MutableScatterSet) c07422.L$4;
                list3 = (List) c07422.L$3;
                list2 = (List) c07422.L$2;
                list = (List) c07422.L$1;
                MonotonicFrameClock monotonicFrameClock4 = (MonotonicFrameClock) c07422.L$0;
                ResultKt.throwOnFailure(obj);
                mutableScatterSet3 = mutableScatterSet9;
                monotonicFrameClock = monotonicFrameClock4;
                final Set<? extends Object> set22 = set;
                final MutableScatterSet mutableScatterSet62 = mutableScatterSet;
                final MutableScatterSet mutableScatterSet72 = mutableScatterSet2;
                final MutableScatterSet mutableScatterSet82 = mutableScatterSetMutableScatterSetOf;
                final List<ControlledComposition> list42 = list3;
                final List<MovableContentStateReference> list52 = list2;
                final List<ControlledComposition> list62 = list;
                if (Recomposer.access$recordComposerModifications(Recomposer.this)) {
                }
            }
        }
    }

    static {
        PersistentOrderedSet.Companion.getClass();
        _runningRecomposers = StateFlowKt.MutableStateFlow(PersistentOrderedSet.EMPTY);
        _hotReloadEnabled = new AtomicReference(Boolean.FALSE);
    }

    public Recomposer(CoroutineContext coroutineContext) {
        BroadcastFrameClock broadcastFrameClock = new BroadcastFrameClock(new Function0() { // from class: androidx.compose.runtime.Recomposer$broadcastFrameClock$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CancellableContinuation cancellableContinuationDeriveStateLocked;
                Recomposer recomposer = this.this$0;
                synchronized (recomposer.stateLock) {
                    cancellableContinuationDeriveStateLocked = recomposer.deriveStateLocked();
                    if (((Recomposer.State) recomposer._state.getValue()).compareTo(Recomposer.State.ShuttingDown) <= 0) {
                        throw ExceptionsKt.CancellationException("Recomposer shutdown; frame clock awaiter will never resume", recomposer.closeCause);
                    }
                }
                if (cancellableContinuationDeriveStateLocked != null) {
                    int i = Result.$r8$clinit;
                    ((CancellableContinuationImpl) cancellableContinuationDeriveStateLocked).resumeWith(Unit.INSTANCE);
                }
                return Unit.INSTANCE;
            }
        });
        this.broadcastFrameClock = broadcastFrameClock;
        this.stateLock = new Object();
        this._knownCompositions = new ArrayList();
        this.snapshotInvalidations = new MutableScatterSet(0, 1, null);
        this.compositionInvalidations = new MutableVector(new ControlledComposition[16], 0);
        this.compositionsAwaitingApply = new ArrayList();
        this.movableContentAwaitingInsert = new ArrayList();
        this.movableContentRemoved = MultiValueMap.m344constructorimpl$default();
        this.movableContentNestedStatesAvailable = new NestedContentMap();
        this.movableContentStatesAvailable = ScatterMapKt.mutableScatterMapOf();
        this.movableContentNestedExtractionsPending = MultiValueMap.m344constructorimpl$default();
        this._state = StateFlowKt.MutableStateFlow(State.Inactive);
        new SnapshotThreadLocal();
        JobImpl jobImpl = new JobImpl((Job) coroutineContext.get(Job.Key));
        jobImpl.invokeOnCompletion(new Function1() { // from class: androidx.compose.runtime.Recomposer$effectJob$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final Throwable th = (Throwable) obj;
                CancellationException CancellationException = ExceptionsKt.CancellationException("Recomposer effect job completed", th);
                final Recomposer recomposer = this.this$0;
                synchronized (recomposer.stateLock) {
                    try {
                        Job job = recomposer.runnerJob;
                        if (job != null) {
                            recomposer._state.setValue(Recomposer.State.ShuttingDown);
                            Recomposer.Companion companion = Recomposer.Companion;
                            job.cancel(CancellationException);
                            recomposer.workContinuation = null;
                            job.invokeOnCompletion(new Function1() { // from class: androidx.compose.runtime.Recomposer$effectJob$1$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    Throwable th2 = (Throwable) obj2;
                                    Recomposer recomposer2 = recomposer;
                                    Object obj3 = recomposer2.stateLock;
                                    Throwable th3 = th;
                                    synchronized (obj3) {
                                        if (th3 == null) {
                                            th3 = null;
                                        } else if (th2 != null) {
                                            try {
                                                if (th2 instanceof CancellationException) {
                                                    th2 = null;
                                                }
                                                if (th2 != null) {
                                                    ExceptionsKt__ExceptionsKt.addSuppressed(th3, th2);
                                                }
                                            } catch (Throwable th4) {
                                                throw th4;
                                            }
                                        }
                                        recomposer2.closeCause = th3;
                                        recomposer2._state.setValue(Recomposer.State.ShutDown);
                                    }
                                    return Unit.INSTANCE;
                                }
                            });
                        } else {
                            recomposer.closeCause = CancellationException;
                            recomposer._state.setValue(Recomposer.State.ShutDown);
                            Unit unit = Unit.INSTANCE;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                return Unit.INSTANCE;
            }
        });
        this.effectJob = jobImpl;
        this.effectCoroutineContext = coroutineContext.plus(broadcastFrameClock).plus(jobImpl);
        this.recomposerInfo = new RecomposerInfoImpl(this);
    }

    public static final ControlledComposition access$performRecompose(Recomposer recomposer, final ControlledComposition controlledComposition, final MutableScatterSet mutableScatterSet) {
        recomposer.getClass();
        CompositionImpl compositionImpl = (CompositionImpl) controlledComposition;
        if (compositionImpl.composer.isComposing || compositionImpl.disposed) {
            return null;
        }
        Set set = recomposer.compositionsRemoved;
        if (set != null && set.contains(controlledComposition)) {
            return null;
        }
        Snapshot.Companion companion = Snapshot.Companion;
        Recomposer$readObserverOf$1 recomposer$readObserverOf$1 = new Recomposer$readObserverOf$1(controlledComposition);
        Recomposer$writeObserverOf$1 recomposer$writeObserverOf$1 = new Recomposer$writeObserverOf$1(controlledComposition, mutableScatterSet);
        companion.getClass();
        MutableSnapshot mutableSnapshotTakeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(recomposer$readObserverOf$1, recomposer$writeObserverOf$1);
        try {
            Snapshot snapshotMakeCurrent = mutableSnapshotTakeMutableSnapshot.makeCurrent();
            if (mutableScatterSet != null) {
                try {
                    if (mutableScatterSet.isNotEmpty()) {
                        Function0 function0 = new Function0() { // from class: androidx.compose.runtime.Recomposer$performRecompose$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
                            @Override // kotlin.jvm.functions.Function0
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke() {
                                MutableScatterSet mutableScatterSet2 = mutableScatterSet;
                                ControlledComposition controlledComposition2 = controlledComposition;
                                Object[] objArr = mutableScatterSet2.elements;
                                long[] jArr = mutableScatterSet2.metadata;
                                int length = jArr.length - 2;
                                if (length >= 0) {
                                    int i = 0;
                                    while (true) {
                                        long j = jArr[i];
                                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i2 = 8 - ((~(i - length)) >>> 31);
                                            for (int i3 = 0; i3 < i2; i3++) {
                                                if ((255 & j) < 128) {
                                                    ((CompositionImpl) controlledComposition2).recordWriteOf(objArr[(i << 3) + i3]);
                                                }
                                                j >>= 8;
                                            }
                                            if (i2 != 8) {
                                                break;
                                            }
                                            if (i == length) {
                                                break;
                                            }
                                            i++;
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        ComposerImpl composerImpl = ((CompositionImpl) controlledComposition).composer;
                        if (composerImpl.isComposing) {
                            ComposerKt.composeImmediateRuntimeError("Preparing a composition while composing is not supported");
                        }
                        composerImpl.isComposing = true;
                        try {
                            function0.invoke();
                            composerImpl.isComposing = false;
                        } catch (Throwable th) {
                            composerImpl.isComposing = false;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    Snapshot.restoreCurrent(snapshotMakeCurrent);
                    throw th2;
                }
            }
            boolean zRecompose = ((CompositionImpl) controlledComposition).recompose();
            Snapshot.restoreCurrent(snapshotMakeCurrent);
            if (zRecompose) {
                return controlledComposition;
            }
            return null;
        } finally {
            applyAndCheck(mutableSnapshotTakeMutableSnapshot);
        }
    }

    public static final boolean access$recordComposerModifications(Recomposer recomposer) {
        List knownCompositions;
        boolean z = true;
        synchronized (recomposer.stateLock) {
            if (recomposer.snapshotInvalidations.isEmpty()) {
                if (recomposer.compositionInvalidations.size == 0 && !recomposer.getHasBroadcastFrameClockAwaitersLocked()) {
                    z = false;
                }
                return z;
            }
            ScatterSetWrapper scatterSetWrapper = new ScatterSetWrapper(recomposer.snapshotInvalidations);
            recomposer.snapshotInvalidations = new MutableScatterSet(0, 1, null);
            synchronized (recomposer.stateLock) {
                knownCompositions = recomposer.getKnownCompositions();
            }
            try {
                int size = knownCompositions.size();
                for (int i = 0; i < size; i++) {
                    ((CompositionImpl) ((ControlledComposition) knownCompositions.get(i))).recordModificationsOf(scatterSetWrapper);
                    if (((State) recomposer._state.getValue()).compareTo(State.ShuttingDown) <= 0) {
                        break;
                    }
                }
                synchronized (recomposer.stateLock) {
                    recomposer.snapshotInvalidations = new MutableScatterSet(0, 1, null);
                    Unit unit = Unit.INSTANCE;
                }
                synchronized (recomposer.stateLock) {
                    if (recomposer.deriveStateLocked() != null) {
                        throw new IllegalStateException("called outside of runRecomposeAndApplyChanges");
                    }
                    if (recomposer.compositionInvalidations.size == 0 && !recomposer.getHasBroadcastFrameClockAwaitersLocked()) {
                        z = false;
                    }
                }
                return z;
            } catch (Throwable th) {
                synchronized (recomposer.stateLock) {
                    MutableScatterSet mutableScatterSet = recomposer.snapshotInvalidations;
                    int i2 = mutableScatterSet._size;
                    Iterator it = scatterSetWrapper.iterator();
                    while (true) {
                        SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
                        if (!sequenceBuilderIterator.hasNext()) {
                            throw th;
                        }
                        mutableScatterSet.plusAssign(sequenceBuilderIterator.next());
                    }
                }
            }
        }
    }

    public static void applyAndCheck(MutableSnapshot mutableSnapshot) {
        try {
            if (mutableSnapshot.apply() instanceof SnapshotApplyResult.Failure) {
                throw new IllegalStateException("Unsupported concurrent change during composition. A state object was modified by composition as well as being modified outside composition.");
            }
        } finally {
            mutableSnapshot.dispose();
        }
    }

    public static final void deletedMovableContent$lambda$73$recordNestedStatesOf(Recomposer recomposer, MovableContentStateReference movableContentStateReference, MovableContentStateReference movableContentStateReference2) {
        List list = movableContentStateReference2.nestedReferences;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                MovableContentStateReference movableContentStateReference3 = (MovableContentStateReference) list.get(i);
                NestedContentMap nestedContentMap = recomposer.movableContentNestedStatesAvailable;
                MovableContent movableContent = movableContentStateReference3.content;
                NestedMovableContent nestedMovableContent = new NestedMovableContent(movableContentStateReference3, movableContentStateReference);
                MultiValueMap.m342addimpl(nestedContentMap.contentMap, movableContent, nestedMovableContent);
                MultiValueMap.m342addimpl(nestedContentMap.containerMap, nestedMovableContent.container, movableContent);
                deletedMovableContent$lambda$73$recordNestedStatesOf(recomposer, movableContentStateReference, movableContentStateReference3);
            }
        }
    }

    public static final void performInitialMovableContentInserts$fillToInsert(List list, Recomposer recomposer, CompositionImpl compositionImpl) {
        ArrayList arrayList = (ArrayList) list;
        arrayList.clear();
        synchronized (recomposer.stateLock) {
            try {
                Iterator it = ((ArrayList) recomposer.movableContentAwaitingInsert).iterator();
                while (it.hasNext()) {
                    MovableContentStateReference movableContentStateReference = (MovableContentStateReference) it.next();
                    if (Intrinsics.areEqual(movableContentStateReference.composition, compositionImpl)) {
                        arrayList.add(movableContentStateReference);
                        it.remove();
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static /* synthetic */ void processCompositionError$default(Recomposer recomposer, Throwable th, int i) throws Throwable {
        recomposer.processCompositionError(th, null, (i & 4) == 0);
    }

    public final void cancel() {
        synchronized (this.stateLock) {
            try {
                if (((State) this._state.getValue()).compareTo(State.Idle) >= 0) {
                    this._state.setValue(State.ShuttingDown);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.effectJob.cancel(null);
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void composeInitial$runtime_release(CompositionImpl compositionImpl, ComposableLambdaImpl composableLambdaImpl) throws Throwable {
        boolean z = compositionImpl.composer.isComposing;
        try {
            Snapshot.Companion companion = Snapshot.Companion;
            Recomposer$readObserverOf$1 recomposer$readObserverOf$1 = new Recomposer$readObserverOf$1(compositionImpl);
            Recomposer$writeObserverOf$1 recomposer$writeObserverOf$1 = new Recomposer$writeObserverOf$1(compositionImpl, null);
            companion.getClass();
            MutableSnapshot mutableSnapshotTakeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(recomposer$readObserverOf$1, recomposer$writeObserverOf$1);
            try {
                Snapshot snapshotMakeCurrent = mutableSnapshotTakeMutableSnapshot.makeCurrent();
                try {
                    compositionImpl.composeContent(composableLambdaImpl);
                    Unit unit = Unit.INSTANCE;
                    if (!z) {
                        SnapshotKt.currentSnapshot().notifyObjectsInitialized$runtime_release();
                    }
                    synchronized (this.stateLock) {
                        if (((State) this._state.getValue()).compareTo(State.ShuttingDown) > 0 && !getKnownCompositions().contains(compositionImpl)) {
                            ((ArrayList) this._knownCompositions).add(compositionImpl);
                            this._knownCompositionsCache = null;
                        }
                    }
                    try {
                        performInitialMovableContentInserts(compositionImpl);
                        try {
                            compositionImpl.applyChanges();
                            compositionImpl.applyLateChanges();
                            if (z) {
                                return;
                            }
                            SnapshotKt.currentSnapshot().notifyObjectsInitialized$runtime_release();
                        } catch (Throwable th) {
                            processCompositionError$default(this, th, 6);
                        }
                    } catch (Throwable th2) {
                        processCompositionError(th2, compositionImpl, true);
                    }
                } finally {
                    Snapshot.restoreCurrent(snapshotMakeCurrent);
                }
            } finally {
                applyAndCheck(mutableSnapshotTakeMutableSnapshot);
            }
        } catch (Throwable th3) {
            processCompositionError(th3, compositionImpl, true);
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void deletedMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
        synchronized (this.stateLock) {
            try {
                MultiValueMap.m342addimpl(this.movableContentRemoved, movableContentStateReference.content, movableContentStateReference);
                if (movableContentStateReference.nestedReferences != null) {
                    deletedMovableContent$lambda$73$recordNestedStatesOf(this, movableContentStateReference, movableContentStateReference);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final CancellableContinuation deriveStateLocked() {
        State state;
        StateFlowImpl stateFlowImpl = this._state;
        int iCompareTo = ((State) stateFlowImpl.getValue()).compareTo(State.ShuttingDown);
        MutableVector mutableVector = this.compositionInvalidations;
        if (iCompareTo <= 0) {
            ((ArrayList) this._knownCompositions).clear();
            this._knownCompositionsCache = EmptyList.INSTANCE;
            this.snapshotInvalidations = new MutableScatterSet(0, 1, null);
            mutableVector.clear();
            ((ArrayList) this.compositionsAwaitingApply).clear();
            ((ArrayList) this.movableContentAwaitingInsert).clear();
            this.failedCompositions = null;
            CancellableContinuationImpl cancellableContinuationImpl = this.workContinuation;
            if (cancellableContinuationImpl != null) {
                cancellableContinuationImpl.cancel(null);
            }
            this.workContinuation = null;
            this.errorState = null;
            return null;
        }
        if (this.errorState != null) {
            state = State.Inactive;
        } else if (this.runnerJob == null) {
            this.snapshotInvalidations = new MutableScatterSet(0, 1, null);
            mutableVector.clear();
            state = getHasBroadcastFrameClockAwaitersLocked() ? State.InactivePendingWork : State.Inactive;
        } else {
            state = (mutableVector.size == 0 && !this.snapshotInvalidations.isNotEmpty() && ((ArrayList) this.compositionsAwaitingApply).isEmpty() && ((ArrayList) this.movableContentAwaitingInsert).isEmpty() && !getHasBroadcastFrameClockAwaitersLocked()) ? State.Idle : State.PendingWork;
        }
        stateFlowImpl.setValue(state);
        if (state != State.PendingWork) {
            return null;
        }
        CancellableContinuationImpl cancellableContinuationImpl2 = this.workContinuation;
        this.workContinuation = null;
        return cancellableContinuationImpl2;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final boolean getCollectingCallByInformation$runtime_release() {
        return ((Boolean) _hotReloadEnabled.get()).booleanValue();
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final boolean getCollectingParameterInformation$runtime_release() {
        return false;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final boolean getCollectingSourceInformation$runtime_release() {
        EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
        return false;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final Composition getComposition$runtime_release() {
        return null;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final int getCompoundHashKey$runtime_release() {
        return 1000;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final CoroutineContext getEffectCoroutineContext() {
        return this.effectCoroutineContext;
    }

    public final boolean getHasBroadcastFrameClockAwaitersLocked() {
        return (this.frameClockPaused || this.broadcastFrameClock.hasAwaitersUnlocked.get() == 0) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean getHasSchedulingWork() {
        boolean z;
        synchronized (this.stateLock) {
            if (this.snapshotInvalidations.isNotEmpty() || this.compositionInvalidations.size != 0) {
                z = true;
            } else if (!getHasBroadcastFrameClockAwaitersLocked()) {
                z = false;
            }
        }
        return z;
    }

    public final List getKnownCompositions() {
        List arrayList = this._knownCompositionsCache;
        if (arrayList == null) {
            ArrayList arrayList2 = (ArrayList) this._knownCompositions;
            arrayList = arrayList2.isEmpty() ? EmptyList.INSTANCE : new ArrayList(arrayList2);
            this._knownCompositionsCache = arrayList;
        }
        return arrayList;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void insertMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
        CancellableContinuation cancellableContinuationDeriveStateLocked;
        synchronized (this.stateLock) {
            ((ArrayList) this.movableContentAwaitingInsert).add(movableContentStateReference);
            cancellableContinuationDeriveStateLocked = deriveStateLocked();
        }
        if (cancellableContinuationDeriveStateLocked != null) {
            int i = Result.$r8$clinit;
            ((CancellableContinuationImpl) cancellableContinuationDeriveStateLocked).resumeWith(Unit.INSTANCE);
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void invalidate$runtime_release(ControlledComposition controlledComposition) {
        CancellableContinuation cancellableContinuationDeriveStateLocked;
        synchronized (this.stateLock) {
            if (this.compositionInvalidations.contains(controlledComposition)) {
                cancellableContinuationDeriveStateLocked = null;
            } else {
                this.compositionInvalidations.add(controlledComposition);
                cancellableContinuationDeriveStateLocked = deriveStateLocked();
            }
        }
        if (cancellableContinuationDeriveStateLocked != null) {
            int i = Result.$r8$clinit;
            ((CancellableContinuationImpl) cancellableContinuationDeriveStateLocked).resumeWith(Unit.INSTANCE);
        }
    }

    public final Object join(Continuation continuation) {
        Object objFirst = FlowKt.first(this._state, new AnonymousClass2(null), continuation);
        return objFirst == CoroutineSingletons.COROUTINE_SUSPENDED ? objFirst : Unit.INSTANCE;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void movableContentStateReleased$runtime_release(MovableContentStateReference movableContentStateReference, MovableContentState movableContentState, Applier applier) {
        ObjectList objectList;
        boolean z;
        boolean z2;
        boolean z3 = true;
        synchronized (this.stateLock) {
            try {
                this.movableContentStatesAvailable.set(movableContentStateReference, movableContentState);
                Object obj = this.movableContentNestedExtractionsPending.get(movableContentStateReference);
                if (obj == null) {
                    objectList = ObjectListKt.EmptyObjectList;
                } else if (obj instanceof MutableObjectList) {
                    objectList = (ObjectList) obj;
                } else {
                    Object[] objArr = ObjectListKt.EmptyArray;
                    MutableObjectList mutableObjectList = new MutableObjectList(1);
                    mutableObjectList.add(obj);
                    objectList = mutableObjectList;
                }
                if (objectList.isNotEmpty()) {
                    MutableScatterMap mutableScatterMapExtractNestedStates$runtime_release = movableContentState.extractNestedStates$runtime_release(applier, objectList);
                    Object[] objArr2 = mutableScatterMapExtractNestedStates$runtime_release.keys;
                    Object[] objArr3 = mutableScatterMapExtractNestedStates$runtime_release.values;
                    long[] jArr = mutableScatterMapExtractNestedStates$runtime_release.metadata;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i = 0;
                        while (true) {
                            long j = jArr[i];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i2 = 8 - ((~(i - length)) >>> 31);
                                int i3 = 0;
                                while (i3 < i2) {
                                    if ((255 & j) < 128) {
                                        int i4 = (i << 3) + i3;
                                        Object obj2 = objArr2[i4];
                                        z2 = z3;
                                        this.movableContentStatesAvailable.set((MovableContentStateReference) obj2, (MovableContentState) objArr3[i4]);
                                    } else {
                                        z2 = z3;
                                    }
                                    j >>= 8;
                                    i3++;
                                    z3 = z2;
                                }
                                z = z3;
                                if (i2 != 8) {
                                    break;
                                }
                            } else {
                                z = z3;
                            }
                            if (i == length) {
                                break;
                            }
                            i++;
                            z3 = z;
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final MovableContentState movableContentStateResolve$runtime_release(MovableContentStateReference movableContentStateReference) {
        MovableContentState movableContentState;
        synchronized (this.stateLock) {
            movableContentState = (MovableContentState) this.movableContentStatesAvailable.remove(movableContentStateReference);
        }
        return movableContentState;
    }

    public final void performInitialMovableContentInserts(CompositionImpl compositionImpl) {
        synchronized (this.stateLock) {
            ArrayList arrayList = (ArrayList) this.movableContentAwaitingInsert;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (Intrinsics.areEqual(((MovableContentStateReference) arrayList.get(i)).composition, compositionImpl)) {
                    Unit unit = Unit.INSTANCE;
                    ArrayList arrayList2 = new ArrayList();
                    performInitialMovableContentInserts$fillToInsert(arrayList2, this, compositionImpl);
                    while (!arrayList2.isEmpty()) {
                        performInsertValues(arrayList2, null);
                        performInitialMovableContentInserts$fillToInsert(arrayList2, this, compositionImpl);
                    }
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0156, code lost:
    
        r3 = r10.size();
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x015b, code lost:
    
        if (r4 >= r3) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0167, code lost:
    
        if (((kotlin.Pair) r10.get(r4)).getSecond() == null) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0169, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x016c, code lost:
    
        r3 = new java.util.ArrayList(r10.size());
        r4 = r10.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x017a, code lost:
    
        if (r8 >= r4) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x017c, code lost:
    
        r11 = (kotlin.Pair) r10.get(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0186, code lost:
    
        if (r11.getSecond() != null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0188, code lost:
    
        r11 = (androidx.compose.runtime.MovableContentStateReference) r11.getFirst();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0191, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0192, code lost:
    
        if (r11 == null) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0194, code lost:
    
        r3.add(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0197, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x019a, code lost:
    
        r4 = r16.stateLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x019c, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x019d, code lost:
    
        kotlin.collections.CollectionsKt__MutableCollectionsKt.addAll(r3, r16.movableContentAwaitingInsert);
        r3 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01a4, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01a5, code lost:
    
        r3 = new java.util.ArrayList(r10.size());
        r4 = r10.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01b3, code lost:
    
        if (r8 >= r4) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b5, code lost:
    
        r11 = r10.get(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01c0, code lost:
    
        if (((kotlin.Pair) r11).getSecond() == null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01c2, code lost:
    
        r3.add(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01c5, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01c8, code lost:
    
        r10 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List performInsertValues(List list, MutableScatterSet mutableScatterSet) {
        ArrayList arrayList;
        HashMap map = new HashMap(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            ControlledComposition controlledComposition = ((MovableContentStateReference) obj).composition;
            Object arrayList2 = map.get(controlledComposition);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                map.put(controlledComposition, arrayList2);
            }
            ((ArrayList) arrayList2).add(obj);
        }
        for (Map.Entry entry : map.entrySet()) {
            ControlledComposition controlledComposition2 = (ControlledComposition) entry.getKey();
            List list2 = (List) entry.getValue();
            if (((CompositionImpl) controlledComposition2).composer.isComposing) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            Snapshot.Companion companion = Snapshot.Companion;
            Recomposer$readObserverOf$1 recomposer$readObserverOf$1 = new Recomposer$readObserverOf$1(controlledComposition2);
            Recomposer$writeObserverOf$1 recomposer$writeObserverOf$1 = new Recomposer$writeObserverOf$1(controlledComposition2, mutableScatterSet);
            companion.getClass();
            MutableSnapshot mutableSnapshotTakeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(recomposer$readObserverOf$1, recomposer$writeObserverOf$1);
            try {
                Snapshot snapshotMakeCurrent = mutableSnapshotTakeMutableSnapshot.makeCurrent();
                try {
                    synchronized (this.stateLock) {
                        try {
                            arrayList = new ArrayList(list2.size());
                            int size2 = list2.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                MovableContentStateReference movableContentStateReference = (MovableContentStateReference) list2.get(i2);
                                Object objM345removeLastimpl = MultiValueMap.m345removeLastimpl(this.movableContentRemoved, movableContentStateReference.content);
                                MovableContentStateReference movableContentStateReference2 = (MovableContentStateReference) objM345removeLastimpl;
                                if (movableContentStateReference2 != null) {
                                    this.movableContentNestedStatesAvailable.usedContainer(movableContentStateReference2);
                                }
                                arrayList.add(new Pair(movableContentStateReference, objM345removeLastimpl));
                            }
                            if (ComposeRuntimeFlags.isMovingNestedMovableContentEnabled) {
                                int size3 = arrayList.size();
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= size3) {
                                        break;
                                    }
                                    Pair pair = (Pair) arrayList.get(i3);
                                    if (pair.getSecond() == null) {
                                        NestedContentMap nestedContentMap = this.movableContentNestedStatesAvailable;
                                        if (nestedContentMap.contentMap.contains(((MovableContentStateReference) pair.getFirst()).content)) {
                                            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                                            int size4 = arrayList.size();
                                            int i4 = 0;
                                            while (i4 < size4) {
                                                Object obj2 = arrayList.get(i4);
                                                i4++;
                                                Pair pair2 = (Pair) obj2;
                                                if (pair2.getSecond() == null) {
                                                    NestedContentMap nestedContentMap2 = this.movableContentNestedStatesAvailable;
                                                    MovableContent movableContent = ((MovableContentStateReference) pair2.getFirst()).content;
                                                    MutableScatterMap mutableScatterMap = nestedContentMap2.contentMap;
                                                    NestedMovableContent nestedMovableContent = (NestedMovableContent) MultiValueMap.m345removeLastimpl(mutableScatterMap, movableContent);
                                                    if (mutableScatterMap.isEmpty()) {
                                                        nestedContentMap2.containerMap.clear();
                                                    }
                                                    if (nestedMovableContent != null) {
                                                        MovableContentStateReference movableContentStateReference3 = nestedMovableContent.content;
                                                        MultiValueMap.m342addimpl(this.movableContentNestedExtractionsPending, nestedMovableContent.container, movableContentStateReference3);
                                                        pair2 = new Pair(pair2.getFirst(), movableContentStateReference3);
                                                    }
                                                }
                                                arrayList3.add(pair2);
                                            }
                                            arrayList = arrayList3;
                                        }
                                    }
                                    i3++;
                                }
                            }
                        } finally {
                        }
                    }
                    int size5 = arrayList.size();
                    int i5 = 0;
                    while (true) {
                        if (i5 >= size5) {
                            break;
                        }
                        if (((Pair) arrayList.get(i5)).getSecond() != null) {
                            break;
                        }
                        i5++;
                    }
                    ((CompositionImpl) controlledComposition2).insertMovableContent(arrayList);
                    Unit unit = Unit.INSTANCE;
                    Snapshot.restoreCurrent(snapshotMakeCurrent);
                } catch (Throwable th) {
                    Snapshot.restoreCurrent(snapshotMakeCurrent);
                    throw th;
                }
            } finally {
                applyAndCheck(mutableSnapshotTakeMutableSnapshot);
            }
        }
        return CollectionsKt___CollectionsKt.toList(map.keySet());
    }

    public final void processCompositionError(Throwable th, CompositionImpl compositionImpl, boolean z) throws Throwable {
        if (!((Boolean) _hotReloadEnabled.get()).booleanValue() || (th instanceof ComposeRuntimeError)) {
            synchronized (this.stateLock) {
                RecomposerErrorState recomposerErrorState = this.errorState;
                if (recomposerErrorState != null) {
                    throw recomposerErrorState.cause;
                }
                this.errorState = new RecomposerErrorState(false, th);
                Unit unit = Unit.INSTANCE;
            }
            throw th;
        }
        synchronized (this.stateLock) {
            Log.e("ComposeInternal", "Error was captured in composition while live edit was enabled.", th);
            ((ArrayList) this.compositionsAwaitingApply).clear();
            this.compositionInvalidations.clear();
            this.snapshotInvalidations = new MutableScatterSet(0, 1, null);
            ((ArrayList) this.movableContentAwaitingInsert).clear();
            this.movableContentRemoved.clear();
            this.movableContentStatesAvailable.clear();
            this.errorState = new RecomposerErrorState(z, th);
            if (compositionImpl != null) {
                recordFailedCompositionLocked(compositionImpl);
            }
            deriveStateLocked();
        }
    }

    public final void recordFailedCompositionLocked(CompositionImpl compositionImpl) {
        List arrayList = this.failedCompositions;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.failedCompositions = arrayList;
        }
        if (!arrayList.contains(compositionImpl)) {
            arrayList.add(compositionImpl);
        }
        if (((ArrayList) this._knownCompositions).remove(compositionImpl)) {
            this._knownCompositionsCache = null;
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void reportRemovedComposition$runtime_release(ControlledComposition controlledComposition) {
        synchronized (this.stateLock) {
            try {
                Set linkedHashSet = this.compositionsRemoved;
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                    this.compositionsRemoved = linkedHashSet;
                }
                linkedHashSet.add(controlledComposition);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object runRecomposeAndApplyChanges(Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.broadcastFrameClock, new Recomposer$recompositionRunner$2(this, new C07422(null), MonotonicFrameClockKt.getMonotonicFrameClock(continuation.getContext()), null), continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objWithContext != coroutineSingletons) {
            objWithContext = Unit.INSTANCE;
        }
        return objWithContext == coroutineSingletons ? objWithContext : Unit.INSTANCE;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void unregisterComposition$runtime_release(CompositionImpl compositionImpl) {
        synchronized (this.stateLock) {
            if (((ArrayList) this._knownCompositions).remove(compositionImpl)) {
                this._knownCompositionsCache = null;
            }
            this.compositionInvalidations.remove(compositionImpl);
            ((ArrayList) this.compositionsAwaitingApply).remove(compositionImpl);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void recordInspectionTable$runtime_release(Set set) {
    }
}
