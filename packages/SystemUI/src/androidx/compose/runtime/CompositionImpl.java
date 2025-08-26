package androidx.compose.runtime;

import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.MutableSetWrapper;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSet;
import androidx.collection.SetWrapper;
import androidx.compose.runtime.DerivedSnapshotState;
import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.collection.ScopeMap;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.RememberEventDispatcher;
import androidx.compose.runtime.internal.Trace;
import androidx.compose.runtime.snapshots.ReaderKind;
import androidx.compose.runtime.snapshots.StateObject;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class CompositionImpl implements ControlledComposition, ReusableComposition, RecomposeScopeOwner {
    public final MutableSetWrapper abandonSet;
    public final Applier applier;
    public final ChangeList changes;
    public ComposableLambdaImpl composable;
    public final ComposerImpl composer;
    public final MutableScatterSet conditionallyInvalidatedScopes;
    public final MutableScatterMap derivedStates;
    public boolean disposed;
    public final MutableScatterSet invalidatedScopes;
    public CompositionImpl invalidationDelegate;
    public int invalidationDelegateGroup;
    public MutableScatterMap invalidations;
    public final ChangeList lateChanges;
    public final Object lock;
    public final MutableScatterMap observations;
    public final MutableScatterMap observationsProcessed;
    public final CompositionObserverHolder observerHolder;
    public final CompositionContext parent;
    public boolean pendingInvalidScopes;
    public final AtomicReference pendingModifications;
    public final SlotTable slotTable;

    public CompositionImpl(CompositionContext compositionContext, Applier<?> applier, CoroutineContext coroutineContext) {
        this.parent = compositionContext;
        this.applier = applier;
        this.pendingModifications = new AtomicReference(null);
        this.lock = new Object();
        MutableSetWrapper mutableSetWrapper = new MutableSetWrapper(new MutableScatterSet(0, 1, null));
        this.abandonSet = mutableSetWrapper;
        SlotTable slotTable = new SlotTable();
        if (compositionContext.getCollectingCallByInformation$runtime_release()) {
            slotTable.calledByMap = new MutableIntObjectMap(0, 1, null);
        }
        if (compositionContext.getCollectingSourceInformation$runtime_release()) {
            slotTable.collectSourceInformation();
        }
        this.slotTable = slotTable;
        this.observations = ScatterMapKt.mutableScatterMapOf();
        this.invalidatedScopes = new MutableScatterSet(0, 1, null);
        this.conditionallyInvalidatedScopes = new MutableScatterSet(0, 1, null);
        this.derivedStates = ScatterMapKt.mutableScatterMapOf();
        ChangeList changeList = new ChangeList();
        this.changes = changeList;
        ChangeList changeList2 = new ChangeList();
        this.lateChanges = changeList2;
        this.observationsProcessed = ScatterMapKt.mutableScatterMapOf();
        this.invalidations = ScatterMapKt.mutableScatterMapOf();
        this.observerHolder = new CompositionObserverHolder(null, false, 3, null);
        ComposerImpl composerImpl = new ComposerImpl(applier, compositionContext, slotTable, mutableSetWrapper, changeList, changeList2, this);
        compositionContext.registerComposer$runtime_release(composerImpl);
        this.composer = composerImpl;
        boolean z = compositionContext instanceof Recomposer;
        ComposableSingletons$CompositionKt.INSTANCE.getClass();
        this.composable = ComposableSingletons$CompositionKt.f17lambda1;
    }

    public final void abandonChanges() {
        this.pendingModifications.set(null);
        this.changes.operations.clear();
        this.lateChanges.operations.clear();
        MutableSetWrapper mutableSetWrapper = this.abandonSet;
        if (((SetWrapper) mutableSetWrapper).parent.isEmpty()) {
            return;
        }
        new RememberEventDispatcher(mutableSetWrapper, this.composer.getErrorContext$runtime_release()).dispatchAbandons();
    }

    public final void addPendingInvalidationsLocked(Object obj, boolean z) {
        Object obj2 = this.observations.get(obj);
        if (obj2 == null) {
            return;
        }
        boolean z2 = obj2 instanceof MutableScatterSet;
        MutableScatterSet mutableScatterSet = this.invalidatedScopes;
        MutableScatterSet mutableScatterSet2 = this.conditionallyInvalidatedScopes;
        MutableScatterMap mutableScatterMap = this.observationsProcessed;
        if (!z2) {
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) obj2;
            if (ScopeMap.m349removeimpl(mutableScatterMap, obj, recomposeScopeImpl) || recomposeScopeImpl.invalidateForResult(obj) == InvalidationResult.IGNORED) {
                return;
            }
            if (recomposeScopeImpl.trackedDependencies == null || z) {
                mutableScatterSet.add(recomposeScopeImpl);
                return;
            } else {
                mutableScatterSet2.add(recomposeScopeImpl);
                return;
            }
        }
        MutableScatterSet mutableScatterSet3 = (MutableScatterSet) obj2;
        Object[] objArr = mutableScatterSet3.elements;
        long[] jArr = mutableScatterSet3.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        RecomposeScopeImpl recomposeScopeImpl2 = (RecomposeScopeImpl) objArr[(i << 3) + i3];
                        if (!ScopeMap.m349removeimpl(mutableScatterMap, obj, recomposeScopeImpl2) && recomposeScopeImpl2.invalidateForResult(obj) != InvalidationResult.IGNORED) {
                            if (recomposeScopeImpl2.trackedDependencies == null || z) {
                                mutableScatterSet.add(recomposeScopeImpl2);
                            } else {
                                mutableScatterSet2.add(recomposeScopeImpl2);
                            }
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void applyChanges() {
        synchronized (this.lock) {
            try {
                applyChangesInLocked(this.changes);
                drainPendingModificationsLocked();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                try {
                    if (!((SetWrapper) this.abandonSet).parent.isEmpty()) {
                        new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release()).dispatchAbandons();
                    }
                    throw th;
                } catch (Throwable th2) {
                    this.abandonChanges();
                    throw th2;
                }
            }
        }
    }

    public final void applyChangesInLocked(ChangeList changeList) throws Throwable {
        RememberEventDispatcher rememberEventDispatcher;
        boolean z;
        long[] jArr;
        int i;
        RememberEventDispatcher rememberEventDispatcher2;
        long[] jArr2;
        long j;
        char c;
        long j2;
        int i2;
        boolean zIsEmpty;
        int i3;
        boolean z2 = true;
        ChangeList changeList2 = this.lateChanges;
        ComposerImpl composerImpl = this.composer;
        RememberEventDispatcher rememberEventDispatcher3 = new RememberEventDispatcher(this.abandonSet, composerImpl.getErrorContext$runtime_release());
        try {
            if (changeList.operations.isEmpty()) {
                if (changeList2.operations.isEmpty()) {
                    rememberEventDispatcher3.dispatchAbandons();
                    return;
                }
                return;
            }
            try {
                Trace.INSTANCE.getClass();
                android.os.Trace.beginSection("Compose:applyChanges");
                try {
                    Applier applier = this.applier;
                    applier.getClass();
                    SlotWriter slotWriterOpenWriter = this.slotTable.openWriter();
                    int i4 = 0;
                    try {
                        changeList.executeAndFlushAllPendingChanges(applier, slotWriterOpenWriter, rememberEventDispatcher3, composerImpl.getErrorContext$runtime_release());
                        Unit unit = Unit.INSTANCE;
                        slotWriterOpenWriter.close(true);
                        applier.onEndChanges();
                        android.os.Trace.endSection();
                        rememberEventDispatcher3.dispatchRememberObservers();
                        rememberEventDispatcher3.dispatchSideEffects();
                        if (this.pendingInvalidScopes) {
                            android.os.Trace.beginSection("Compose:unobserve");
                            try {
                                this.pendingInvalidScopes = false;
                                MutableScatterMap mutableScatterMap = this.observations;
                                long[] jArr3 = mutableScatterMap.metadata;
                                int length = jArr3.length - 2;
                                if (length >= 0) {
                                    int i5 = 0;
                                    while (true) {
                                        long j3 = jArr3[i5];
                                        char c2 = 7;
                                        long j4 = -9187201950435737472L;
                                        if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                                            int i6 = 8;
                                            int i7 = 8 - ((~(i5 - length)) >>> 31);
                                            z = z2;
                                            int i8 = i4;
                                            while (i8 < i7) {
                                                if ((j3 & 255) < 128) {
                                                    c = c2;
                                                    int i9 = (i5 << 3) + i8;
                                                    j2 = j4;
                                                    Object obj = mutableScatterMap.keys[i9];
                                                    Object obj2 = mutableScatterMap.values[i9];
                                                    if (obj2 instanceof MutableScatterSet) {
                                                        MutableScatterSet mutableScatterSet = (MutableScatterSet) obj2;
                                                        Object[] objArr = mutableScatterSet.elements;
                                                        long[] jArr4 = mutableScatterSet.metadata;
                                                        int i10 = i6;
                                                        int length2 = jArr4.length - 2;
                                                        if (length2 >= 0) {
                                                            rememberEventDispatcher2 = rememberEventDispatcher3;
                                                            jArr2 = jArr3;
                                                            int i11 = 0;
                                                            while (true) {
                                                                try {
                                                                    long j5 = jArr4[i11];
                                                                    j = j3;
                                                                    long[] jArr5 = jArr4;
                                                                    if ((((~j5) << c) & j5 & j2) != j2) {
                                                                        int i12 = 8 - ((~(i11 - length2)) >>> 31);
                                                                        int i13 = 0;
                                                                        while (i13 < i12) {
                                                                            if ((j5 & 255) < 128) {
                                                                                i3 = i8;
                                                                                int i14 = (i11 << 3) + i13;
                                                                                if (!((RecomposeScopeImpl) objArr[i14]).getValid()) {
                                                                                    mutableScatterSet.removeElementAt(i14);
                                                                                }
                                                                            } else {
                                                                                i3 = i8;
                                                                            }
                                                                            j5 >>= i10;
                                                                            i13++;
                                                                            i8 = i3;
                                                                        }
                                                                        i = i8;
                                                                        if (i12 != i10) {
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        i = i8;
                                                                    }
                                                                    if (i11 == length2) {
                                                                        break;
                                                                    }
                                                                    i11++;
                                                                    jArr4 = jArr5;
                                                                    j3 = j;
                                                                    i8 = i;
                                                                    i10 = 8;
                                                                } catch (Throwable th) {
                                                                    th = th;
                                                                    Trace.INSTANCE.getClass();
                                                                    android.os.Trace.endSection();
                                                                    throw th;
                                                                }
                                                            }
                                                        } else {
                                                            i = i8;
                                                            rememberEventDispatcher2 = rememberEventDispatcher3;
                                                            jArr2 = jArr3;
                                                            j = j3;
                                                        }
                                                        zIsEmpty = mutableScatterSet.isEmpty();
                                                    } else {
                                                        i = i8;
                                                        rememberEventDispatcher2 = rememberEventDispatcher3;
                                                        jArr2 = jArr3;
                                                        j = j3;
                                                        zIsEmpty = !((RecomposeScopeImpl) obj2).getValid() ? z : false;
                                                    }
                                                    if (zIsEmpty) {
                                                        mutableScatterMap.removeValueAt(i9);
                                                    }
                                                    i2 = 8;
                                                } else {
                                                    i = i8;
                                                    rememberEventDispatcher2 = rememberEventDispatcher3;
                                                    jArr2 = jArr3;
                                                    j = j3;
                                                    c = c2;
                                                    j2 = j4;
                                                    i2 = i6;
                                                }
                                                j3 = j >> i2;
                                                c2 = c;
                                                i6 = i2;
                                                j4 = j2;
                                                rememberEventDispatcher3 = rememberEventDispatcher2;
                                                jArr3 = jArr2;
                                                i8 = i + 1;
                                            }
                                            rememberEventDispatcher = rememberEventDispatcher3;
                                            jArr = jArr3;
                                            if (i7 != i6) {
                                                break;
                                            }
                                        } else {
                                            z = z2;
                                            rememberEventDispatcher = rememberEventDispatcher3;
                                            jArr = jArr3;
                                        }
                                        if (i5 == length) {
                                            break;
                                        }
                                        i5++;
                                        z2 = z;
                                        rememberEventDispatcher3 = rememberEventDispatcher;
                                        jArr3 = jArr;
                                        i4 = 0;
                                    }
                                } else {
                                    rememberEventDispatcher = rememberEventDispatcher3;
                                }
                                cleanUpDerivedStateObservations();
                                Unit unit2 = Unit.INSTANCE;
                                Trace.INSTANCE.getClass();
                                android.os.Trace.endSection();
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } else {
                            rememberEventDispatcher = rememberEventDispatcher3;
                        }
                        if (changeList2.operations.isEmpty()) {
                            rememberEventDispatcher.dispatchAbandons();
                        }
                    } catch (Throwable th3) {
                        try {
                            slotWriterOpenWriter.close(false);
                            throw th3;
                        } catch (Throwable th4) {
                            th = th4;
                            Trace.INSTANCE.getClass();
                            android.os.Trace.endSection();
                            throw th;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
                if (changeList2.operations.isEmpty()) {
                    rememberEventDispatcher3.dispatchAbandons();
                }
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
        }
    }

    public final void applyLateChanges() {
        synchronized (this.lock) {
            try {
                if (this.lateChanges.operations.isNotEmpty()) {
                    applyChangesInLocked(this.lateChanges);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                try {
                    if (!((SetWrapper) this.abandonSet).parent.isEmpty()) {
                        new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release()).dispatchAbandons();
                    }
                    throw th;
                } catch (Throwable th2) {
                    this.abandonChanges();
                    throw th2;
                }
            }
        }
    }

    public final void changesApplied() {
        synchronized (this.lock) {
            try {
                this.composer.providerUpdates = null;
                if (!((SetWrapper) this.abandonSet).parent.isEmpty()) {
                    new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release()).dispatchAbandons();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                try {
                    if (!((SetWrapper) this.abandonSet).parent.isEmpty()) {
                        new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release()).dispatchAbandons();
                    }
                    throw th;
                } catch (Throwable th2) {
                    this.abandonChanges();
                    throw th2;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void cleanUpDerivedStateObservations() {
        char c;
        long j;
        long j2;
        long j3;
        long[] jArr;
        long[] jArr2;
        int i;
        long j4;
        char c2;
        long j5;
        long j6;
        int i2;
        boolean zIsEmpty;
        int i3;
        long j7;
        MutableScatterMap mutableScatterMap = this.derivedStates;
        long[] jArr3 = mutableScatterMap.metadata;
        int length = jArr3.length - 2;
        char c3 = 7;
        long j8 = -9187201950435737472L;
        int i4 = 8;
        if (length >= 0) {
            int i5 = 0;
            long j9 = 128;
            while (true) {
                long j10 = jArr3[i5];
                j2 = 255;
                if ((((~j10) << c3) & j10 & j8) != j8) {
                    int i6 = 8 - ((~(i5 - length)) >>> 31);
                    int i7 = 0;
                    while (i7 < i6) {
                        if ((j10 & 255) < j9) {
                            c2 = c3;
                            int i8 = (i5 << 3) + i7;
                            j5 = j8;
                            Object obj = mutableScatterMap.keys[i8];
                            Object obj2 = mutableScatterMap.values[i8];
                            boolean z = obj2 instanceof MutableScatterSet;
                            MutableScatterMap mutableScatterMap2 = this.observations;
                            if (z) {
                                MutableScatterSet mutableScatterSet = (MutableScatterSet) obj2;
                                Object[] objArr = mutableScatterSet.elements;
                                long[] jArr4 = mutableScatterSet.metadata;
                                j6 = j9;
                                int length2 = jArr4.length - 2;
                                if (length2 >= 0) {
                                    j4 = j10;
                                    int i9 = i4;
                                    int i10 = 0;
                                    while (true) {
                                        long j11 = jArr4[i10];
                                        jArr2 = jArr3;
                                        i = length;
                                        if ((((~j11) << c2) & j11 & j5) != j5) {
                                            int i11 = 8 - ((~(i10 - length2)) >>> 31);
                                            int i12 = 0;
                                            while (i12 < i11) {
                                                if ((j11 & 255) < j6) {
                                                    i3 = i12;
                                                    int i13 = (i10 << 3) + i3;
                                                    j7 = j11;
                                                    if (!mutableScatterMap2.containsKey((DerivedState) objArr[i13])) {
                                                        mutableScatterSet.removeElementAt(i13);
                                                    }
                                                } else {
                                                    i3 = i12;
                                                    j7 = j11;
                                                }
                                                j11 = j7 >> i9;
                                                i12 = i3 + 1;
                                            }
                                            if (i11 != i9) {
                                                break;
                                            }
                                            if (i10 == length2) {
                                                break;
                                            }
                                            i10++;
                                            jArr3 = jArr2;
                                            length = i;
                                            i9 = 8;
                                        }
                                    }
                                } else {
                                    jArr2 = jArr3;
                                    i = length;
                                    j4 = j10;
                                }
                                zIsEmpty = mutableScatterSet.isEmpty();
                            } else {
                                jArr2 = jArr3;
                                i = length;
                                j4 = j10;
                                j6 = j9;
                                zIsEmpty = !mutableScatterMap2.containsKey((DerivedState) obj2);
                            }
                            if (zIsEmpty) {
                                mutableScatterMap.removeValueAt(i8);
                            }
                            i2 = 8;
                        } else {
                            jArr2 = jArr3;
                            i = length;
                            j4 = j10;
                            c2 = c3;
                            j5 = j8;
                            j6 = j9;
                            i2 = i4;
                        }
                        j10 = j4 >> i2;
                        i7++;
                        i4 = i2;
                        c3 = c2;
                        j8 = j5;
                        j9 = j6;
                        jArr3 = jArr2;
                        length = i;
                    }
                    jArr = jArr3;
                    int i14 = length;
                    c = c3;
                    j = j8;
                    j3 = j9;
                    if (i6 != i4) {
                        break;
                    } else {
                        length = i14;
                    }
                } else {
                    jArr = jArr3;
                    c = c3;
                    j = j8;
                    j3 = j9;
                }
                if (i5 == length) {
                    break;
                }
                i5++;
                c3 = c;
                j8 = j;
                j9 = j3;
                jArr3 = jArr;
                i4 = 8;
            }
        } else {
            c = 7;
            j = -9187201950435737472L;
            j2 = 255;
            j3 = 128;
        }
        MutableScatterSet mutableScatterSet2 = this.conditionallyInvalidatedScopes;
        if (!mutableScatterSet2.isNotEmpty()) {
            return;
        }
        Object[] objArr2 = mutableScatterSet2.elements;
        long[] jArr5 = mutableScatterSet2.metadata;
        int length3 = jArr5.length - 2;
        if (length3 < 0) {
            return;
        }
        int i15 = 0;
        while (true) {
            long j12 = jArr5[i15];
            if ((((~j12) << c) & j12 & j) != j) {
                int i16 = 8 - ((~(i15 - length3)) >>> 31);
                for (int i17 = 0; i17 < i16; i17++) {
                    if ((j12 & j2) < j3) {
                        int i18 = (i15 << 3) + i17;
                        if (!(((RecomposeScopeImpl) objArr2[i18]).trackedDependencies != null)) {
                            mutableScatterSet2.removeElementAt(i18);
                        }
                    }
                    j12 >>= 8;
                }
                if (i16 != 8) {
                    return;
                }
            }
            if (i15 == length3) {
                return;
            } else {
                i15++;
            }
        }
    }

    public final void composeContent(ComposableLambdaImpl composableLambdaImpl) {
        try {
            synchronized (this.lock) {
                drainPendingModificationsForCompositionLocked();
                MutableScatterMap mutableScatterMap = this.invalidations;
                this.invalidations = ScatterMapKt.mutableScatterMapOf();
                try {
                    if (!this.observerHolder.root) {
                        this.parent.getClass();
                    }
                    ComposerImpl composerImpl = this.composer;
                    if (!composerImpl.changes.operations.isEmpty()) {
                        ComposerKt.composeImmediateRuntimeError("Expected applyChanges() to have been called");
                    }
                    composerImpl.m331doComposeaFTiNEg(mutableScatterMap, composableLambdaImpl);
                } finally {
                }
            }
        } finally {
        }
    }

    public final void composeInitial(ComposableLambdaImpl composableLambdaImpl) {
        if (this.disposed) {
            PreconditionsKt.throwIllegalStateException("The composition is disposed");
        }
        this.composable = composableLambdaImpl;
        this.parent.composeInitial$runtime_release(this, composableLambdaImpl);
    }

    public final void deactivate() {
        synchronized (this.lock) {
            try {
                boolean z = this.slotTable.groupsSize > 0;
                if (z || !((SetWrapper) this.abandonSet).parent.isEmpty()) {
                    Trace.INSTANCE.getClass();
                    android.os.Trace.beginSection("Compose:deactivate");
                    try {
                        RememberEventDispatcher rememberEventDispatcher = new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release());
                        if (z) {
                            this.applier.getClass();
                            SlotWriter slotWriterOpenWriter = this.slotTable.openWriter();
                            try {
                                ComposerKt.deactivateCurrentGroup(slotWriterOpenWriter, rememberEventDispatcher);
                                Unit unit = Unit.INSTANCE;
                                slotWriterOpenWriter.close(true);
                                this.applier.onEndChanges();
                                rememberEventDispatcher.dispatchRememberObservers();
                            } catch (Throwable th) {
                                slotWriterOpenWriter.close(false);
                                throw th;
                            }
                        }
                        rememberEventDispatcher.dispatchAbandons();
                        Unit unit2 = Unit.INSTANCE;
                        android.os.Trace.endSection();
                    } catch (Throwable th2) {
                        Trace.INSTANCE.getClass();
                        android.os.Trace.endSection();
                        throw th2;
                    }
                }
                this.observations.clear();
                this.derivedStates.clear();
                this.invalidations.clear();
                this.changes.operations.clear();
                this.lateChanges.operations.clear();
                ComposerImpl composerImpl = this.composer;
                composerImpl.invalidateStack.clear();
                ((ArrayList) composerImpl.invalidations).clear();
                composerImpl.changes.operations.clear();
                composerImpl.providerUpdates = null;
                Unit unit3 = Unit.INSTANCE;
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    @Override // androidx.compose.runtime.Composition
    public final void dispose() {
        synchronized (this.lock) {
            try {
                if (this.composer.isComposing) {
                    PreconditionsKt.throwIllegalStateException("Composition is disposed while composing. If dispose is triggered by a call in @Composable function, consider wrapping it with SideEffect block.");
                }
                if (!this.disposed) {
                    this.disposed = true;
                    ComposableSingletons$CompositionKt.INSTANCE.getClass();
                    this.composable = ComposableSingletons$CompositionKt.f18lambda2;
                    ChangeList changeList = this.composer.deferredChanges;
                    if (changeList != null) {
                        applyChangesInLocked(changeList);
                    }
                    boolean z = this.slotTable.groupsSize > 0;
                    if (z || !((SetWrapper) this.abandonSet).parent.isEmpty()) {
                        RememberEventDispatcher rememberEventDispatcher = new RememberEventDispatcher(this.abandonSet, this.composer.getErrorContext$runtime_release());
                        if (z) {
                            this.applier.getClass();
                            SlotWriter slotWriterOpenWriter = this.slotTable.openWriter();
                            try {
                                ComposerKt.removeCurrentGroup(slotWriterOpenWriter, rememberEventDispatcher);
                                Unit unit = Unit.INSTANCE;
                                slotWriterOpenWriter.close(true);
                                this.applier.clear();
                                this.applier.onEndChanges();
                                rememberEventDispatcher.dispatchRememberObservers();
                            } catch (Throwable th) {
                                slotWriterOpenWriter.close(false);
                                throw th;
                            }
                        }
                        rememberEventDispatcher.dispatchAbandons();
                    }
                    this.composer.dispose$runtime_release();
                }
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        this.parent.unregisterComposition$runtime_release(this);
    }

    public final void drainPendingModificationsForCompositionLocked() {
        AtomicReference atomicReference = this.pendingModifications;
        Object obj = CompositionKt.PendingApplyNoModifications;
        Object andSet = atomicReference.getAndSet(obj);
        if (andSet != null) {
            if (andSet.equals(obj)) {
                ComposerKt.composeRuntimeError("pending composition has not been applied");
                throw new KotlinNothingValueException();
            }
            if (andSet instanceof Set) {
                addPendingInvalidationsLocked((Set) andSet, true);
                return;
            }
            if (!(andSet instanceof Object[])) {
                ComposerKt.composeRuntimeError("corrupt pendingModifications drain: " + this.pendingModifications);
                throw new KotlinNothingValueException();
            }
            for (Set set : (Set[]) andSet) {
                addPendingInvalidationsLocked(set, true);
            }
        }
    }

    public final void drainPendingModificationsLocked() {
        Object andSet = this.pendingModifications.getAndSet(null);
        if (Intrinsics.areEqual(andSet, CompositionKt.PendingApplyNoModifications)) {
            return;
        }
        if (andSet instanceof Set) {
            addPendingInvalidationsLocked((Set) andSet, false);
            return;
        }
        if (andSet instanceof Object[]) {
            for (Set set : (Set[]) andSet) {
                addPendingInvalidationsLocked(set, false);
            }
            return;
        }
        if (andSet == null) {
            ComposerKt.composeRuntimeError("calling recordModificationsOf and applyChanges concurrently is not supported");
            throw new KotlinNothingValueException();
        }
        ComposerKt.composeRuntimeError("corrupt pendingModifications drain: " + this.pendingModifications);
        throw new KotlinNothingValueException();
    }

    public final void drainPendingModificationsOutOfBandLocked() {
        Object andSet = this.pendingModifications.getAndSet(EmptySet.INSTANCE);
        if (Intrinsics.areEqual(andSet, CompositionKt.PendingApplyNoModifications) || andSet == null) {
            return;
        }
        if (andSet instanceof Set) {
            addPendingInvalidationsLocked((Set) andSet, false);
            return;
        }
        if (!(andSet instanceof Object[])) {
            ComposerKt.composeRuntimeError("corrupt pendingModifications drain: " + this.pendingModifications);
            throw new KotlinNothingValueException();
        }
        for (Set set : (Set[]) andSet) {
            addPendingInvalidationsLocked(set, false);
        }
    }

    public final void insertMovableContent(List list) {
        MutableSetWrapper mutableSetWrapper = this.abandonSet;
        ComposerImpl composerImpl = this.composer;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i < size) {
                if (!Intrinsics.areEqual(((MovableContentStateReference) ((Pair) list.get(i)).getFirst()).composition, this)) {
                    ComposerKt.composeImmediateRuntimeError("Check failed");
                    break;
                }
                i++;
            }
        }
        try {
            composerImpl.getClass();
            try {
                composerImpl.insertMovableContentGuarded(list);
                composerImpl.cleanUpCompose();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                composerImpl.abortRoot();
                throw th;
            }
        } finally {
        }
    }

    @Override // androidx.compose.runtime.RecomposeScopeOwner
    public final InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj) {
        CompositionImpl compositionImpl;
        int i = recomposeScopeImpl.flags;
        if ((i & 2) != 0) {
            recomposeScopeImpl.flags = i | 4;
        }
        Anchor anchor = recomposeScopeImpl.anchor;
        if (anchor == null || !anchor.getValid()) {
            return InvalidationResult.IGNORED;
        }
        if (this.slotTable.ownsAnchor(anchor)) {
            return recomposeScopeImpl.block != null ? invalidateChecked(recomposeScopeImpl, anchor, obj) : InvalidationResult.IGNORED;
        }
        synchronized (this.lock) {
            compositionImpl = this.invalidationDelegate;
        }
        if (compositionImpl != null) {
            ComposerImpl composerImpl = compositionImpl.composer;
            if (composerImpl.isComposing && composerImpl.tryImminentInvalidation$runtime_release(recomposeScopeImpl, obj)) {
                return InvalidationResult.IMMINENT;
            }
        }
        return InvalidationResult.IGNORED;
    }

    public final void invalidateAll() {
        RecomposeScopeOwner recomposeScopeOwner;
        synchronized (this.lock) {
            try {
                for (Object obj : this.slotTable.slots) {
                    RecomposeScopeImpl recomposeScopeImpl = obj instanceof RecomposeScopeImpl ? (RecomposeScopeImpl) obj : null;
                    if (recomposeScopeImpl != null && (recomposeScopeOwner = recomposeScopeImpl.owner) != null) {
                        recomposeScopeOwner.invalidate(recomposeScopeImpl, null);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00e1 A[Catch: all -> 0x0042, EDGE_INSN: B:83:0x00e1->B:69:0x00e1 BREAK  A[LOOP:0: B:51:0x0098->B:65:0x00d9], EDGE_INSN: B:85:0x00e1->B:69:0x00e1 BREAK  A[LOOP:0: B:51:0x0098->B:65:0x00d9], TRY_LEAVE, TryCatch #0 {all -> 0x0042, blocks: (B:4:0x000b, B:6:0x0010, B:8:0x0018, B:10:0x001f, B:14:0x0029, B:16:0x002f, B:13:0x0024, B:25:0x0047, B:27:0x004d, B:32:0x0058, B:35:0x005c, B:40:0x006a, B:41:0x0073, B:43:0x0077, B:44:0x0080, B:46:0x0088, B:48:0x008c, B:51:0x0098, B:53:0x00a8, B:55:0x00b4, B:57:0x00be, B:61:0x00cd, B:65:0x00d9, B:66:0x00dc, B:69:0x00e1, B:38:0x0063), top: B:82:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final InvalidationResult invalidateChecked(RecomposeScopeImpl recomposeScopeImpl, Anchor anchor, Object obj) {
        int i;
        synchronized (this.lock) {
            try {
                CompositionImpl compositionImpl = this.invalidationDelegate;
                CompositionImpl compositionImpl2 = null;
                if (compositionImpl != null) {
                    SlotTable slotTable = this.slotTable;
                    int i2 = this.invalidationDelegateGroup;
                    if (slotTable.writer) {
                        ComposerKt.composeImmediateRuntimeError("Writer is active");
                    }
                    if (i2 < 0 || i2 >= slotTable.groupsSize) {
                        ComposerKt.composeImmediateRuntimeError("Invalid group index");
                    }
                    if (slotTable.ownsAnchor(anchor)) {
                        int i3 = slotTable.groups[(i2 * 5) + 3] + i2;
                        int i4 = anchor.location;
                        if (i2 > i4 || i4 >= i3) {
                            compositionImpl = null;
                        }
                        compositionImpl2 = compositionImpl;
                    }
                }
                if (compositionImpl2 == null) {
                    ComposerImpl composerImpl = this.composer;
                    if (composerImpl.isComposing && composerImpl.tryImminentInvalidation$runtime_release(recomposeScopeImpl, obj)) {
                        return InvalidationResult.IMMINENT;
                    }
                    if (!this.observerHolder.root) {
                        this.parent.getClass();
                    }
                    if (obj != null && (obj instanceof DerivedState)) {
                        Object obj2 = this.invalidations.get(recomposeScopeImpl);
                        if (obj2 == null) {
                            ScopeMap.m348addimpl(this.invalidations, recomposeScopeImpl, obj);
                        } else if (obj2 instanceof MutableScatterSet) {
                            MutableScatterSet mutableScatterSet = (MutableScatterSet) obj2;
                            Object[] objArr = mutableScatterSet.elements;
                            long[] jArr = mutableScatterSet.metadata;
                            int length = jArr.length - 2;
                            if (length >= 0) {
                                int i5 = 0;
                                loop0: while (true) {
                                    long j = jArr[i5];
                                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i6 = 8;
                                        int i7 = 8 - ((~(i5 - length)) >>> 31);
                                        int i8 = 0;
                                        while (i8 < i7) {
                                            if ((j & 255) < 128) {
                                                i = i6;
                                                if (objArr[(i5 << 3) + i8] == ScopeInvalidated.INSTANCE) {
                                                    break loop0;
                                                }
                                            } else {
                                                i = i6;
                                            }
                                            j >>= i;
                                            i8++;
                                            i6 = i;
                                        }
                                        if (i7 != i6) {
                                            break;
                                        }
                                        if (i5 == length) {
                                            break;
                                        }
                                        i5++;
                                    }
                                }
                                ScopeMap.m348addimpl(this.invalidations, recomposeScopeImpl, obj);
                            }
                        } else if (obj2 == ScopeInvalidated.INSTANCE) {
                        }
                    } else {
                        this.invalidations.set(recomposeScopeImpl, ScopeInvalidated.INSTANCE);
                    }
                }
                if (compositionImpl2 != null) {
                    return compositionImpl2.invalidateChecked(recomposeScopeImpl, anchor, obj);
                }
                this.parent.invalidate$runtime_release(this);
                return this.composer.isComposing ? InvalidationResult.DEFERRED : InvalidationResult.SCHEDULED;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void invalidateScopeOfLocked(Object obj) {
        Object obj2 = this.observations.get(obj);
        if (obj2 == null) {
            return;
        }
        boolean z = obj2 instanceof MutableScatterSet;
        MutableScatterMap mutableScatterMap = this.observationsProcessed;
        if (!z) {
            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) obj2;
            if (recomposeScopeImpl.invalidateForResult(obj) == InvalidationResult.IMMINENT) {
                ScopeMap.m348addimpl(mutableScatterMap, obj, recomposeScopeImpl);
                return;
            }
            return;
        }
        MutableScatterSet mutableScatterSet = (MutableScatterSet) obj2;
        Object[] objArr = mutableScatterSet.elements;
        long[] jArr = mutableScatterSet.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            long j = jArr[i];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i2 = 8 - ((~(i - length)) >>> 31);
                for (int i3 = 0; i3 < i2; i3++) {
                    if ((255 & j) < 128) {
                        RecomposeScopeImpl recomposeScopeImpl2 = (RecomposeScopeImpl) objArr[(i << 3) + i3];
                        if (recomposeScopeImpl2.invalidateForResult(obj) == InvalidationResult.IMMINENT) {
                            ScopeMap.m348addimpl(mutableScatterMap, obj, recomposeScopeImpl2);
                        }
                    }
                    j >>= 8;
                }
                if (i2 != 8) {
                    return;
                }
            }
            if (i == length) {
                return;
            } else {
                i++;
            }
        }
    }

    @Override // androidx.compose.runtime.Composition
    public final boolean isDisposed() {
        return this.disposed;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0052, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean observesAnyOf(Set set) {
        boolean z = set instanceof ScatterSetWrapper;
        MutableScatterMap mutableScatterMap = this.derivedStates;
        MutableScatterMap mutableScatterMap2 = this.observations;
        if (z) {
            ScatterSet scatterSet = ((ScatterSetWrapper) set).set;
            Object[] objArr = scatterSet.elements;
            long[] jArr = scatterSet.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                loop0: while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                Object obj = objArr[(i << 3) + i3];
                                if (mutableScatterMap2.containsKey(obj) || mutableScatterMap.containsKey(obj)) {
                                    break loop0;
                                }
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
        } else {
            for (Object obj2 : set) {
                if (mutableScatterMap2.containsKey(obj2) || mutableScatterMap.containsKey(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean recompose() {
        boolean zIsNotEmpty;
        synchronized (this.lock) {
            drainPendingModificationsForCompositionLocked();
            try {
                MutableScatterMap mutableScatterMap = this.invalidations;
                this.invalidations = ScatterMapKt.mutableScatterMapOf();
                try {
                    if (!this.observerHolder.root) {
                        this.parent.getClass();
                    }
                    ComposerImpl composerImpl = this.composer;
                    ChangeList changeList = composerImpl.changes;
                    if (!changeList.operations.isEmpty()) {
                        ComposerKt.composeImmediateRuntimeError("Expected applyChanges() to have been called");
                    }
                    if (mutableScatterMap._size > 0 || !((ArrayList) composerImpl.invalidations).isEmpty()) {
                        composerImpl.m331doComposeaFTiNEg(mutableScatterMap, null);
                        zIsNotEmpty = changeList.operations.isNotEmpty();
                    } else {
                        zIsNotEmpty = false;
                    }
                    if (!zIsNotEmpty) {
                        drainPendingModificationsLocked();
                    }
                } catch (Throwable th) {
                    this.invalidations = mutableScatterMap;
                    throw th;
                }
            } finally {
            }
        }
        return zIsNotEmpty;
    }

    @Override // androidx.compose.runtime.RecomposeScopeOwner
    public final void recomposeScopeReleased() {
        this.pendingInvalidScopes = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.util.Set[]] */
    public final void recordModificationsOf(ScatterSetWrapper scatterSetWrapper) {
        Object obj;
        ScatterSetWrapper scatterSetWrapper2;
        do {
            obj = this.pendingModifications.get();
            if (obj == null ? true : obj.equals(CompositionKt.PendingApplyNoModifications)) {
                scatterSetWrapper2 = scatterSetWrapper;
            } else if (obj instanceof Set) {
                scatterSetWrapper2 = new Set[]{obj, scatterSetWrapper};
            } else {
                if (!(obj instanceof Object[])) {
                    throw new IllegalStateException(("corrupt pendingModifications: " + this.pendingModifications).toString());
                }
                Set[] setArr = (Set[]) obj;
                int length = setArr.length;
                ?? CopyOf = Arrays.copyOf(setArr, length + 1);
                CopyOf[length] = scatterSetWrapper;
                scatterSetWrapper2 = CopyOf;
            }
        } while (!this.pendingModifications.compareAndSet(obj, scatterSetWrapper2));
        if (obj == null) {
            synchronized (this.lock) {
                drainPendingModificationsLocked();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00c8  */
    @Override // androidx.compose.runtime.RecomposeScopeOwner
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void recordReadOf(Object obj) {
        RecomposeScopeImpl currentRecomposeScope$runtime_release;
        int i;
        int i2;
        ComposerImpl composerImpl = this.composer;
        if (composerImpl.childrenComposing <= 0 && (currentRecomposeScope$runtime_release = composerImpl.getCurrentRecomposeScope$runtime_release()) != null) {
            int i3 = currentRecomposeScope$runtime_release.flags | 1;
            currentRecomposeScope$runtime_release.flags = i3;
            int i4 = 0;
            if ((i3 & 32) == 0) {
                MutableObjectIntMap mutableObjectIntMap = currentRecomposeScope$runtime_release.trackedInstances;
                if (mutableObjectIntMap == null) {
                    mutableObjectIntMap = new MutableObjectIntMap(0, 1, null);
                    currentRecomposeScope$runtime_release.trackedInstances = mutableObjectIntMap;
                }
                int i5 = currentRecomposeScope$runtime_release.currentToken;
                int iFindIndex = mutableObjectIntMap.findIndex(obj);
                if (iFindIndex < 0) {
                    iFindIndex = ~iFindIndex;
                    i2 = -1;
                } else {
                    i2 = mutableObjectIntMap.values[iFindIndex];
                }
                mutableObjectIntMap.keys[iFindIndex] = obj;
                mutableObjectIntMap.values[iFindIndex] = i5;
                if (i2 == currentRecomposeScope$runtime_release.currentToken) {
                    return;
                }
            }
            if (obj instanceof StateObjectImpl) {
                int i6 = ReaderKind.$r8$clinit;
                ((StateObjectImpl) obj).m352recordReadInh_f27i8$runtime_release(1);
            }
            ScopeMap.m348addimpl(this.observations, obj, currentRecomposeScope$runtime_release);
            if (obj instanceof DerivedState) {
                DerivedState derivedState = (DerivedState) obj;
                DerivedSnapshotState.ResultRecord currentRecord = ((DerivedSnapshotState) derivedState).getCurrentRecord();
                MutableScatterMap mutableScatterMap = this.derivedStates;
                ScopeMap.m350removeScopeimpl(mutableScatterMap, obj);
                MutableObjectIntMap mutableObjectIntMap2 = currentRecord.dependencies;
                Object[] objArr = mutableObjectIntMap2.keys;
                long[] jArr = mutableObjectIntMap2.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i7 = 0;
                    while (true) {
                        long j = jArr[i7];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i8 = 8;
                            int i9 = 8 - ((~(i7 - length)) >>> 31);
                            int i10 = i4;
                            while (i10 < i9) {
                                if ((j & 255) < 128) {
                                    StateObject stateObject = (StateObject) objArr[(i7 << 3) + i10];
                                    i = i8;
                                    if (stateObject instanceof StateObjectImpl) {
                                        int i11 = ReaderKind.$r8$clinit;
                                        ((StateObjectImpl) stateObject).m352recordReadInh_f27i8$runtime_release(1);
                                    }
                                    ScopeMap.m348addimpl(mutableScatterMap, stateObject, obj);
                                } else {
                                    i = i8;
                                }
                                j >>= i;
                                i10++;
                                i8 = i;
                            }
                            if (i9 != i8) {
                                break;
                            }
                            if (i7 == length) {
                                break;
                            }
                            i7++;
                            i4 = 0;
                        }
                    }
                }
                Object obj2 = currentRecord.result;
                MutableScatterMap mutableScatterMap2 = currentRecomposeScope$runtime_release.trackedDependencies;
                if (mutableScatterMap2 == null) {
                    mutableScatterMap2 = new MutableScatterMap(0, 1, null);
                    currentRecomposeScope$runtime_release.trackedDependencies = mutableScatterMap2;
                }
                mutableScatterMap2.set(derivedState, obj2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void recordWriteOf(Object obj) {
        synchronized (this.lock) {
            try {
                invalidateScopeOfLocked(obj);
                Object obj2 = this.derivedStates.get(obj);
                if (obj2 != null) {
                    if (obj2 instanceof MutableScatterSet) {
                        MutableScatterSet mutableScatterSet = (MutableScatterSet) obj2;
                        Object[] objArr = mutableScatterSet.elements;
                        long[] jArr = mutableScatterSet.metadata;
                        int length = jArr.length - 2;
                        if (length >= 0) {
                            int i = 0;
                            while (true) {
                                long j = jArr[i];
                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                    int i2 = 8 - ((~(i - length)) >>> 31);
                                    for (int i3 = 0; i3 < i2; i3++) {
                                        if ((255 & j) < 128) {
                                            invalidateScopeOfLocked((DerivedState) objArr[(i << 3) + i3]);
                                        }
                                        j >>= 8;
                                    }
                                    if (i2 != 8) {
                                        break;
                                    } else if (i == length) {
                                        break;
                                    } else {
                                        i++;
                                    }
                                }
                            }
                        }
                    } else {
                        invalidateScopeOfLocked((DerivedState) obj2);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.Composition
    public final void setContent(Function2 function2) {
        composeInitial((ComposableLambdaImpl) function2);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0183 A[EDGE_INSN: B:73:0x0183->B:224:0x0122 BREAK  A[LOOP:13: B:63:0x0151->B:74:0x0185]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addPendingInvalidationsLocked(Set set, boolean z) {
        long j;
        long j2;
        long j3;
        char c;
        int i;
        long[] jArr;
        long[] jArr2;
        long j4;
        boolean zContains;
        long[] jArr3;
        long j5;
        long[] jArr4;
        long[] jArr5;
        int i2;
        long j6;
        boolean zIsEmpty;
        int i3;
        long j7;
        long[] jArr6;
        long[] jArr7;
        char c2;
        long j8;
        int i4;
        int i5;
        long[] jArr8;
        boolean z2 = set instanceof ScatterSetWrapper;
        MutableScatterMap mutableScatterMap = this.derivedStates;
        Object obj = null;
        int i6 = 8;
        if (z2) {
            ScatterSet scatterSet = ((ScatterSetWrapper) set).set;
            Object[] objArr = scatterSet.elements;
            long[] jArr9 = scatterSet.metadata;
            int length = jArr9.length - 2;
            if (length >= 0) {
                int i7 = 0;
                j = 128;
                j2 = 255;
                while (true) {
                    long j9 = jArr9[i7];
                    char c3 = 7;
                    j3 = -9187201950435737472L;
                    if ((((~j9) << 7) & j9 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i8 = 8 - ((~(i7 - length)) >>> 31);
                        int i9 = 0;
                        while (i9 < i8) {
                            if ((j9 & 255) < 128) {
                                Object obj2 = objArr[(i7 << 3) + i9];
                                c2 = c3;
                                if (obj2 instanceof RecomposeScopeImpl) {
                                    ((RecomposeScopeImpl) obj2).invalidateForResult(obj);
                                } else {
                                    addPendingInvalidationsLocked(obj2, z);
                                    Object obj3 = mutableScatterMap.get(obj2);
                                    if (obj3 != null) {
                                        if (obj3 instanceof MutableScatterSet) {
                                            MutableScatterSet mutableScatterSet = (MutableScatterSet) obj3;
                                            Object[] objArr2 = mutableScatterSet.elements;
                                            long[] jArr10 = mutableScatterSet.metadata;
                                            int length2 = jArr10.length - 2;
                                            if (length2 >= 0) {
                                                int i10 = i6;
                                                i4 = length;
                                                int i11 = 0;
                                                while (true) {
                                                    long j10 = jArr10[i11];
                                                    j8 = j9;
                                                    long[] jArr11 = jArr10;
                                                    if ((((~j10) << c2) & j10 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                        int i12 = 8 - ((~(i11 - length2)) >>> 31);
                                                        int i13 = 0;
                                                        while (i13 < i12) {
                                                            if ((j10 & 255) < 128) {
                                                                jArr8 = jArr9;
                                                                addPendingInvalidationsLocked((DerivedState) objArr2[(i11 << 3) + i13], z);
                                                            } else {
                                                                jArr8 = jArr9;
                                                            }
                                                            j10 >>= i10;
                                                            i13++;
                                                            jArr9 = jArr8;
                                                        }
                                                        jArr7 = jArr9;
                                                        if (i12 != i10) {
                                                            break;
                                                        }
                                                    } else {
                                                        jArr7 = jArr9;
                                                    }
                                                    if (i11 == length2) {
                                                        break;
                                                    }
                                                    i11++;
                                                    jArr10 = jArr11;
                                                    j9 = j8;
                                                    jArr9 = jArr7;
                                                    i10 = 8;
                                                }
                                            }
                                        } else {
                                            jArr7 = jArr9;
                                            j8 = j9;
                                            i4 = length;
                                            addPendingInvalidationsLocked((DerivedState) obj3, z);
                                        }
                                        i5 = 8;
                                    }
                                }
                                jArr7 = jArr9;
                                j8 = j9;
                                i4 = length;
                                i5 = 8;
                            } else {
                                jArr7 = jArr9;
                                c2 = c3;
                                j8 = j9;
                                i4 = length;
                                i5 = i6;
                            }
                            j9 = j8 >> i5;
                            i9++;
                            length = i4;
                            i6 = i5;
                            c3 = c2;
                            jArr9 = jArr7;
                            obj = null;
                        }
                        jArr6 = jArr9;
                        c = c3;
                        int i14 = length;
                        if (i8 != i6) {
                            break;
                        } else {
                            length = i14;
                        }
                    } else {
                        jArr6 = jArr9;
                        c = 7;
                    }
                    if (i7 == length) {
                        break;
                    }
                    i7++;
                    jArr9 = jArr6;
                    obj = null;
                    i6 = 8;
                }
            } else {
                j = 128;
                j2 = 255;
                j3 = -9187201950435737472L;
                c = 7;
            }
        } else {
            j = 128;
            j2 = 255;
            j3 = -9187201950435737472L;
            c = 7;
            for (Object obj4 : set) {
                if (obj4 instanceof RecomposeScopeImpl) {
                    ((RecomposeScopeImpl) obj4).invalidateForResult(null);
                } else {
                    addPendingInvalidationsLocked(obj4, z);
                    Object obj5 = mutableScatterMap.get(obj4);
                    if (obj5 != null) {
                        if (obj5 instanceof MutableScatterSet) {
                            MutableScatterSet mutableScatterSet2 = (MutableScatterSet) obj5;
                            Object[] objArr3 = mutableScatterSet2.elements;
                            long[] jArr12 = mutableScatterSet2.metadata;
                            int length3 = jArr12.length - 2;
                            if (length3 >= 0) {
                                while (true) {
                                    long j11 = jArr12[i];
                                    if ((((~j11) << 7) & j11 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i15 = 8 - ((~(i - length3)) >>> 31);
                                        for (int i16 = 0; i16 < i15; i16++) {
                                            if ((j11 & 255) < 128) {
                                                addPendingInvalidationsLocked((DerivedState) objArr3[(i << 3) + i16], z);
                                            }
                                            j11 >>= 8;
                                        }
                                        if (i15 == 8) {
                                            i = i != length3 ? i + 1 : 0;
                                        }
                                    }
                                }
                            }
                        } else {
                            addPendingInvalidationsLocked((DerivedState) obj5, z);
                        }
                    }
                }
            }
        }
        MutableScatterMap mutableScatterMap2 = this.observations;
        MutableScatterSet mutableScatterSet3 = this.invalidatedScopes;
        if (z) {
            MutableScatterSet mutableScatterSet4 = this.conditionallyInvalidatedScopes;
            if (mutableScatterSet4.isNotEmpty()) {
                long[] jArr13 = mutableScatterMap2.metadata;
                int length4 = jArr13.length - 2;
                if (length4 >= 0) {
                    int i17 = 0;
                    while (true) {
                        long j12 = jArr13[i17];
                        if ((((~j12) << c) & j12 & j3) != j3) {
                            int i18 = 8 - ((~(i17 - length4)) >>> 31);
                            int i19 = 0;
                            while (i19 < i18) {
                                if ((j12 & j2) < j) {
                                    int i20 = (i17 << 3) + i19;
                                    Object obj6 = mutableScatterMap2.keys[i20];
                                    Object obj7 = mutableScatterMap2.values[i20];
                                    if (obj7 instanceof MutableScatterSet) {
                                        MutableScatterSet mutableScatterSet5 = (MutableScatterSet) obj7;
                                        Object[] objArr4 = mutableScatterSet5.elements;
                                        long[] jArr14 = mutableScatterSet5.metadata;
                                        int length5 = jArr14.length - 2;
                                        if (length5 >= 0) {
                                            j6 = j12;
                                            int i21 = 0;
                                            while (true) {
                                                long j13 = jArr14[i21];
                                                jArr5 = jArr13;
                                                i2 = length4;
                                                if ((((~j13) << c) & j13 & j3) != j3) {
                                                    int i22 = 8 - ((~(i21 - length5)) >>> 31);
                                                    for (int i23 = 0; i23 < i22; i23 = i3 + 1) {
                                                        if ((j13 & j2) < j) {
                                                            i3 = i23;
                                                            int i24 = (i21 << 3) + i3;
                                                            j7 = j13;
                                                            RecomposeScopeImpl recomposeScopeImpl = (RecomposeScopeImpl) objArr4[i24];
                                                            if (mutableScatterSet4.contains(recomposeScopeImpl) || mutableScatterSet3.contains(recomposeScopeImpl)) {
                                                                mutableScatterSet5.removeElementAt(i24);
                                                            }
                                                        } else {
                                                            i3 = i23;
                                                            j7 = j13;
                                                        }
                                                        j13 = j7 >> 8;
                                                    }
                                                    if (i22 != 8) {
                                                        break;
                                                    }
                                                    if (i21 == length5) {
                                                        break;
                                                    }
                                                    i21++;
                                                    length4 = i2;
                                                    jArr13 = jArr5;
                                                }
                                            }
                                        } else {
                                            jArr5 = jArr13;
                                            i2 = length4;
                                            j6 = j12;
                                        }
                                        zIsEmpty = mutableScatterSet5.isEmpty();
                                    } else {
                                        jArr5 = jArr13;
                                        i2 = length4;
                                        j6 = j12;
                                        RecomposeScopeImpl recomposeScopeImpl2 = (RecomposeScopeImpl) obj7;
                                        zIsEmpty = mutableScatterSet4.contains(recomposeScopeImpl2) || mutableScatterSet3.contains(recomposeScopeImpl2);
                                    }
                                    if (zIsEmpty) {
                                        mutableScatterMap2.removeValueAt(i20);
                                    }
                                } else {
                                    jArr5 = jArr13;
                                    i2 = length4;
                                    j6 = j12;
                                }
                                j12 = j6 >> 8;
                                i19++;
                                length4 = i2;
                                jArr13 = jArr5;
                            }
                            jArr4 = jArr13;
                            int i25 = length4;
                            if (i18 != 8) {
                                break;
                            } else {
                                length4 = i25;
                            }
                        } else {
                            jArr4 = jArr13;
                        }
                        if (i17 == length4) {
                            break;
                        }
                        i17++;
                        jArr13 = jArr4;
                    }
                }
                mutableScatterSet4.clear();
                cleanUpDerivedStateObservations();
                return;
            }
        }
        if (mutableScatterSet3.isNotEmpty()) {
            long[] jArr15 = mutableScatterMap2.metadata;
            int length6 = jArr15.length - 2;
            if (length6 >= 0) {
                int i26 = 0;
                while (true) {
                    long j14 = jArr15[i26];
                    if ((((~j14) << c) & j14 & j3) != j3) {
                        int i27 = 8 - ((~(i26 - length6)) >>> 31);
                        int i28 = 0;
                        while (i28 < i27) {
                            if ((j14 & j2) < j) {
                                int i29 = (i26 << 3) + i28;
                                Object obj8 = mutableScatterMap2.keys[i29];
                                Object obj9 = mutableScatterMap2.values[i29];
                                if (obj9 instanceof MutableScatterSet) {
                                    MutableScatterSet mutableScatterSet6 = (MutableScatterSet) obj9;
                                    Object[] objArr5 = mutableScatterSet6.elements;
                                    long[] jArr16 = mutableScatterSet6.metadata;
                                    int length7 = jArr16.length - 2;
                                    if (length7 >= 0) {
                                        j4 = j14;
                                        int i30 = 0;
                                        while (true) {
                                            long j15 = jArr16[i30];
                                            Object[] objArr6 = objArr5;
                                            long[] jArr17 = jArr16;
                                            if ((((~j15) << c) & j15 & j3) != j3) {
                                                int i31 = 8 - ((~(i30 - length7)) >>> 31);
                                                int i32 = 0;
                                                while (i32 < i31) {
                                                    if ((j15 & j2) < j) {
                                                        jArr3 = jArr15;
                                                        int i33 = (i30 << 3) + i32;
                                                        j5 = j15;
                                                        if (mutableScatterSet3.contains((RecomposeScopeImpl) objArr6[i33])) {
                                                            mutableScatterSet6.removeElementAt(i33);
                                                        }
                                                    } else {
                                                        jArr3 = jArr15;
                                                        j5 = j15;
                                                    }
                                                    i32++;
                                                    jArr15 = jArr3;
                                                    j15 = j5 >> 8;
                                                }
                                                jArr2 = jArr15;
                                                if (i31 != 8) {
                                                    break;
                                                }
                                            } else {
                                                jArr2 = jArr15;
                                            }
                                            if (i30 == length7) {
                                                break;
                                            }
                                            i30++;
                                            objArr5 = objArr6;
                                            jArr16 = jArr17;
                                            jArr15 = jArr2;
                                        }
                                    } else {
                                        jArr2 = jArr15;
                                        j4 = j14;
                                    }
                                    zContains = mutableScatterSet6.isEmpty();
                                } else {
                                    jArr2 = jArr15;
                                    j4 = j14;
                                    zContains = mutableScatterSet3.contains((RecomposeScopeImpl) obj9);
                                }
                                if (zContains) {
                                    mutableScatterMap2.removeValueAt(i29);
                                }
                            } else {
                                jArr2 = jArr15;
                                j4 = j14;
                            }
                            i28++;
                            j14 = j4 >> 8;
                            jArr15 = jArr2;
                        }
                        jArr = jArr15;
                        if (i27 != 8) {
                            break;
                        }
                    } else {
                        jArr = jArr15;
                    }
                    if (i26 == length6) {
                        break;
                    }
                    i26++;
                    jArr15 = jArr;
                }
            }
            cleanUpDerivedStateObservations();
            mutableScatterSet3.clear();
        }
    }

    public /* synthetic */ CompositionImpl(CompositionContext compositionContext, Applier applier, CoroutineContext coroutineContext, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compositionContext, applier, (i & 4) != 0 ? null : coroutineContext);
    }
}
