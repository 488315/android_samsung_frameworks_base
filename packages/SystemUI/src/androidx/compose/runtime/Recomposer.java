package androidx.compose.runtime;

import android.util.Log;
import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ObjectList;
import androidx.collection.ObjectListKt;
import androidx.collection.ScatterMapKt;
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
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import androidx.compose.runtime.snapshots.MutableSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import androidx.compose.runtime.snapshots.SnapshotKt;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    PersistentHashMap persistentHashMap = persistentOrderedSet.hashMap;
                    TrieNode remove = persistentHashMap.node.remove(recomposerInfoImpl != null ? recomposerInfoImpl.hashCode() : 0, 0, recomposerInfoImpl);
                    if (persistentHashMap.node != remove) {
                        if (remove == null) {
                            PersistentHashMap.Companion.getClass();
                            persistentHashMap = PersistentHashMap.EMPTY;
                        } else {
                            persistentHashMap = new PersistentHashMap(remove, persistentHashMap.size - 1);
                        }
                    }
                    EndOfChain endOfChain = EndOfChain.INSTANCE;
                    Object obj = links.previous;
                    boolean z = obj != endOfChain;
                    Object obj2 = links.next;
                    if (z) {
                        Object obj3 = persistentHashMap.get(obj);
                        obj3.getClass();
                        persistentHashMap = persistentHashMap.put(obj, new Links(((Links) obj3).previous, obj2));
                    }
                    if (obj2 != endOfChain) {
                        Object obj4 = persistentHashMap.get(obj2);
                        obj4.getClass();
                        persistentHashMap = persistentHashMap.put(obj2, new Links(obj, ((Links) obj4).next));
                    }
                    Object obj5 = obj != endOfChain ? persistentOrderedSet.firstElement : obj2;
                    if (obj2 != endOfChain) {
                        obj = persistentOrderedSet.lastElement;
                    }
                    persistentOrderedSet = new PersistentOrderedSet(obj5, obj, persistentHashMap);
                }
                if (persistentSet == persistentOrderedSet) {
                    return;
                }
            } while (!stateFlowImpl.updateState(persistentSet, persistentOrderedSet));
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class RecomposerErrorState {
        public final Throwable cause;

        public RecomposerErrorState(boolean z, Throwable th) {
            this.cause = th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class RecomposerInfoImpl {
        public RecomposerInfoImpl(Recomposer recomposer) {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                CancellableContinuation deriveStateLocked;
                Recomposer recomposer = Recomposer.this;
                synchronized (recomposer.stateLock) {
                    deriveStateLocked = recomposer.deriveStateLocked();
                    if (((Recomposer.State) recomposer._state.getValue()).compareTo(Recomposer.State.ShuttingDown) <= 0) {
                        throw ExceptionsKt.CancellationException("Recomposer shutdown; frame clock awaiter will never resume", recomposer.closeCause);
                    }
                }
                if (deriveStateLocked != null) {
                    int i = Result.$r8$clinit;
                    ((CancellableContinuationImpl) deriveStateLocked).resumeWith(Unit.INSTANCE);
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
        this.movableContentRemoved = MultiValueMap.m343constructorimpl$default();
        this.movableContentNestedStatesAvailable = new NestedContentMap();
        this.movableContentStatesAvailable = ScatterMapKt.mutableScatterMapOf();
        this.movableContentNestedExtractionsPending = MultiValueMap.m343constructorimpl$default();
        this._state = StateFlowKt.MutableStateFlow(State.Inactive);
        new SnapshotThreadLocal();
        JobImpl jobImpl = new JobImpl((Job) coroutineContext.get(Job.Key));
        jobImpl.invokeOnCompletion(new Function1() { // from class: androidx.compose.runtime.Recomposer$effectJob$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final Throwable th = (Throwable) obj;
                CancellationException CancellationException = ExceptionsKt.CancellationException("Recomposer effect job completed", th);
                final Recomposer recomposer = Recomposer.this;
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
                                public final Object mo779invoke(Object obj2) {
                                    Throwable th2 = (Throwable) obj2;
                                    Recomposer recomposer2 = Recomposer.this;
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
        MutableSnapshot takeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(recomposer$readObserverOf$1, recomposer$writeObserverOf$1);
        try {
            Snapshot makeCurrent = takeMutableSnapshot.makeCurrent();
            if (mutableScatterSet != null) {
                try {
                    if (mutableScatterSet.isNotEmpty()) {
                        Function0 function0 = new Function0() { // from class: androidx.compose.runtime.Recomposer$performRecompose$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                MutableScatterSet mutableScatterSet2 = MutableScatterSet.this;
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
                                        }
                                        if (i == length) {
                                            break;
                                        }
                                        i++;
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
                    Snapshot.restoreCurrent(makeCurrent);
                    throw th2;
                }
            }
            boolean recompose = ((CompositionImpl) controlledComposition).recompose();
            Snapshot.restoreCurrent(makeCurrent);
            if (recompose) {
                return controlledComposition;
            }
            return null;
        } finally {
            applyAndCheck(takeMutableSnapshot);
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
                MultiValueMap.m341addimpl(nestedContentMap.contentMap, movableContent, nestedMovableContent);
                MultiValueMap.m341addimpl(nestedContentMap.containerMap, nestedMovableContent.container, movableContent);
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

    public static /* synthetic */ void processCompositionError$default(Recomposer recomposer, Throwable th, int i) {
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
    public final void composeInitial$runtime_release(CompositionImpl compositionImpl, ComposableLambdaImpl composableLambdaImpl) {
        boolean z = compositionImpl.composer.isComposing;
        try {
            Snapshot.Companion companion = Snapshot.Companion;
            Recomposer$readObserverOf$1 recomposer$readObserverOf$1 = new Recomposer$readObserverOf$1(compositionImpl);
            Recomposer$writeObserverOf$1 recomposer$writeObserverOf$1 = new Recomposer$writeObserverOf$1(compositionImpl, null);
            companion.getClass();
            MutableSnapshot takeMutableSnapshot = Snapshot.Companion.takeMutableSnapshot(recomposer$readObserverOf$1, recomposer$writeObserverOf$1);
            try {
                Snapshot makeCurrent = takeMutableSnapshot.makeCurrent();
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
                    Snapshot.restoreCurrent(makeCurrent);
                }
            } finally {
                applyAndCheck(takeMutableSnapshot);
            }
        } catch (Throwable th3) {
            processCompositionError(th3, compositionImpl, true);
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void deletedMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
        synchronized (this.stateLock) {
            try {
                MultiValueMap.m341addimpl(this.movableContentRemoved, movableContentStateReference.content, movableContentStateReference);
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
        int compareTo = ((State) stateFlowImpl.getValue()).compareTo(State.ShuttingDown);
        MutableVector mutableVector = this.compositionInvalidations;
        if (compareTo <= 0) {
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

    public final boolean getHasSchedulingWork() {
        boolean z;
        synchronized (this.stateLock) {
            if (!this.snapshotInvalidations.isNotEmpty() && this.compositionInvalidations.size == 0) {
                z = getHasBroadcastFrameClockAwaitersLocked();
            }
        }
        return z;
    }

    public final List getKnownCompositions() {
        List list = this._knownCompositionsCache;
        if (list == null) {
            ArrayList arrayList = (ArrayList) this._knownCompositions;
            list = arrayList.isEmpty() ? EmptyList.INSTANCE : new ArrayList(arrayList);
            this._knownCompositionsCache = list;
        }
        return list;
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void insertMovableContent$runtime_release(MovableContentStateReference movableContentStateReference) {
        CancellableContinuation deriveStateLocked;
        synchronized (this.stateLock) {
            ((ArrayList) this.movableContentAwaitingInsert).add(movableContentStateReference);
            deriveStateLocked = deriveStateLocked();
        }
        if (deriveStateLocked != null) {
            int i = Result.$r8$clinit;
            ((CancellableContinuationImpl) deriveStateLocked).resumeWith(Unit.INSTANCE);
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void invalidate$runtime_release(ControlledComposition controlledComposition) {
        CancellableContinuation cancellableContinuation;
        synchronized (this.stateLock) {
            if (this.compositionInvalidations.contains(controlledComposition)) {
                cancellableContinuation = null;
            } else {
                this.compositionInvalidations.add(controlledComposition);
                cancellableContinuation = deriveStateLocked();
            }
        }
        if (cancellableContinuation != null) {
            int i = Result.$r8$clinit;
            ((CancellableContinuationImpl) cancellableContinuation).resumeWith(Unit.INSTANCE);
        }
    }

    public final Object join(Continuation continuation) {
        Object first = FlowKt.first(this._state, new Recomposer$join$2(null), continuation);
        return first == CoroutineSingletons.COROUTINE_SUSPENDED ? first : Unit.INSTANCE;
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
                    MutableScatterMap extractNestedStates$runtime_release = movableContentState.extractNestedStates$runtime_release(applier, objectList);
                    Object[] objArr2 = extractNestedStates$runtime_release.keys;
                    Object[] objArr3 = extractNestedStates$runtime_release.values;
                    long[] jArr = extractNestedStates$runtime_release.metadata;
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01c8, code lost:
    
        r10 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0156, code lost:
    
        r3 = r10.size();
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x015b, code lost:
    
        if (r4 >= r3) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0167, code lost:
    
        if (((kotlin.Pair) r10.get(r4)).getSecond() == null) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0169, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x016c, code lost:
    
        r3 = new java.util.ArrayList(r10.size());
        r4 = r10.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x017a, code lost:
    
        if (r8 >= r4) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x017c, code lost:
    
        r11 = (kotlin.Pair) r10.get(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0186, code lost:
    
        if (r11.getSecond() != null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0188, code lost:
    
        r11 = (androidx.compose.runtime.MovableContentStateReference) r11.getFirst();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0192, code lost:
    
        if (r11 == null) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0194, code lost:
    
        r3.add(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0197, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0191, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x019a, code lost:
    
        r4 = r16.stateLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x019c, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x019d, code lost:
    
        kotlin.collections.CollectionsKt__MutableCollectionsKt.addAll(r3, r16.movableContentAwaitingInsert);
        r3 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01a4, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01a5, code lost:
    
        r3 = new java.util.ArrayList(r10.size());
        r4 = r10.size();
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01b3, code lost:
    
        if (r8 >= r4) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01b5, code lost:
    
        r11 = r10.get(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01c0, code lost:
    
        if (((kotlin.Pair) r11).getSecond() == null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01c2, code lost:
    
        r3.add(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01c5, code lost:
    
        r8 = r8 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List performInsertValues(java.util.List r17, androidx.collection.MutableScatterSet r18) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.Recomposer.performInsertValues(java.util.List, androidx.collection.MutableScatterSet):java.util.List");
    }

    public final void processCompositionError(Throwable th, CompositionImpl compositionImpl, boolean z) {
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
        List list = this.failedCompositions;
        if (list == null) {
            list = new ArrayList();
            this.failedCompositions = list;
        }
        if (!list.contains(compositionImpl)) {
            list.add(compositionImpl);
        }
        if (((ArrayList) this._knownCompositions).remove(compositionImpl)) {
            this._knownCompositionsCache = null;
        }
    }

    @Override // androidx.compose.runtime.CompositionContext
    public final void reportRemovedComposition$runtime_release(ControlledComposition controlledComposition) {
        synchronized (this.stateLock) {
            try {
                Set set = this.compositionsRemoved;
                if (set == null) {
                    set = new LinkedHashSet();
                    this.compositionsRemoved = set;
                }
                set.add(controlledComposition);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object runRecomposeAndApplyChanges(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.broadcastFrameClock, new Recomposer$recompositionRunner$2(this, new Recomposer$runRecomposeAndApplyChanges$2(this, null), MonotonicFrameClockKt.getMonotonicFrameClock(continuation.getContext()), null), continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (withContext != coroutineSingletons) {
            withContext = Unit.INSTANCE;
        }
        return withContext == coroutineSingletons ? withContext : Unit.INSTANCE;
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
