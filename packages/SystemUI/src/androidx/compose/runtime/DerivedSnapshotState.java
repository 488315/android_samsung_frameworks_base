package androidx.compose.runtime;

import androidx.collection.MutableObjectIntMap;
import androidx.collection.ObjectIntMapKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.IntRef;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.snapshots.StateObject;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import androidx.compose.runtime.snapshots.StateRecord;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DerivedSnapshotState<T> extends StateObjectImpl implements DerivedState<T> {
    public final Function0 calculation;
    public ResultRecord first = new ResultRecord(SnapshotKt.currentSnapshot().getSnapshotId());
    public final SnapshotMutationPolicy policy;

    public DerivedSnapshotState(Function0 function0, SnapshotMutationPolicy<T> snapshotMutationPolicy) {
        this.calculation = function0;
        this.policy = snapshotMutationPolicy;
    }

    public final ResultRecord currentRecord(ResultRecord resultRecord, Snapshot snapshot, boolean z, Function0 function0) {
        SnapshotMutationPolicy snapshotMutationPolicy;
        boolean z2;
        int i;
        ResultRecord resultRecord2 = resultRecord;
        boolean z3 = true;
        int i2 = 0;
        if (resultRecord2.isValid(this, snapshot)) {
            if (z) {
                MutableVector derivedStateObservers = SnapshotStateKt.derivedStateObservers();
                Object[] objArr = derivedStateObservers.content;
                int i3 = derivedStateObservers.size;
                for (int i4 = 0; i4 < i3; i4++) {
                    ((DerivedStateObserver) objArr[i4]).start();
                }
                try {
                    MutableObjectIntMap mutableObjectIntMap = resultRecord2.dependencies;
                    SnapshotThreadLocal snapshotThreadLocal = SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel;
                    IntRef intRef = (IntRef) snapshotThreadLocal.get();
                    if (intRef == null) {
                        intRef = new IntRef(0);
                        snapshotThreadLocal.set(intRef);
                    }
                    int i5 = intRef.element;
                    Object[] objArr2 = mutableObjectIntMap.keys;
                    int[] iArr = mutableObjectIntMap.values;
                    long[] jArr = mutableObjectIntMap.metadata;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i6 = 0;
                        while (true) {
                            long j = jArr[i6];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i7 = 8;
                                int i8 = 8 - ((~(i6 - length)) >>> 31);
                                z2 = z3;
                                int i9 = i2;
                                while (i9 < i8) {
                                    if ((j & 255) < 128) {
                                        int i10 = (i6 << 3) + i9;
                                        try {
                                            StateObject stateObject = (StateObject) objArr2[i10];
                                            i = i7;
                                            intRef.element = i5 + iArr[i10];
                                            Function1 readObserver = snapshot.getReadObserver();
                                            if (readObserver != null) {
                                                readObserver.mo779invoke(stateObject);
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            Object[] objArr3 = derivedStateObservers.content;
                                            int i11 = derivedStateObservers.size;
                                            for (int i12 = 0; i12 < i11; i12++) {
                                                ((DerivedStateObserver) objArr3[i12]).done();
                                            }
                                            throw th;
                                        }
                                    } else {
                                        i = i7;
                                    }
                                    j >>= i;
                                    i9++;
                                    i7 = i;
                                }
                                if (i8 != i7) {
                                    break;
                                }
                            } else {
                                z2 = z3;
                            }
                            if (i6 == length) {
                                break;
                            }
                            i6++;
                            z3 = z2;
                            i2 = 0;
                        }
                    }
                    intRef.element = i5;
                    Unit unit = Unit.INSTANCE;
                    Object[] objArr4 = derivedStateObservers.content;
                    int i13 = derivedStateObservers.size;
                    for (int i14 = 0; i14 < i13; i14++) {
                        ((DerivedStateObserver) objArr4[i14]).done();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            return resultRecord2;
        }
        final MutableObjectIntMap mutableObjectIntMap2 = new MutableObjectIntMap(0, 1, null);
        SnapshotThreadLocal snapshotThreadLocal2 = SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel;
        final IntRef intRef2 = (IntRef) snapshotThreadLocal2.get();
        if (intRef2 == null) {
            intRef2 = new IntRef(0);
            snapshotThreadLocal2.set(intRef2);
        }
        final int i15 = intRef2.element;
        MutableVector derivedStateObservers2 = SnapshotStateKt.derivedStateObservers();
        Object[] objArr5 = derivedStateObservers2.content;
        int i16 = derivedStateObservers2.size;
        for (int i17 = 0; i17 < i16; i17++) {
            ((DerivedStateObserver) objArr5[i17]).start();
        }
        try {
            intRef2.element = i15 + 1;
            Snapshot.Companion companion = Snapshot.Companion;
            Function1 function1 = new Function1(this) { // from class: androidx.compose.runtime.DerivedSnapshotState$currentRecord$result$1$1$result$1
                final /* synthetic */ DerivedSnapshotState<Object> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    if (obj == this.this$0) {
                        throw new IllegalStateException("A derived state calculation cannot read itself");
                    }
                    if (obj instanceof StateObject) {
                        int i18 = intRef2.element;
                        MutableObjectIntMap mutableObjectIntMap3 = mutableObjectIntMap2;
                        int i19 = i18 - i15;
                        int findKeyIndex = mutableObjectIntMap3.findKeyIndex(obj);
                        mutableObjectIntMap3.set(Math.min(i19, findKeyIndex >= 0 ? mutableObjectIntMap3.values[findKeyIndex] : Integer.MAX_VALUE), obj);
                    }
                    return Unit.INSTANCE;
                }
            };
            companion.getClass();
            Object observe = Snapshot.Companion.observe(function1, function0);
            intRef2.element = i15;
            Object[] objArr6 = derivedStateObservers2.content;
            int i18 = derivedStateObservers2.size;
            for (int i19 = 0; i19 < i18; i19++) {
                ((DerivedStateObserver) objArr6[i19]).done();
            }
            Object obj = SnapshotKt.lock;
            synchronized (obj) {
                try {
                    Snapshot.Companion.getClass();
                    Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
                    Object obj2 = resultRecord2.result;
                    ResultRecord.Companion.getClass();
                    if (obj2 == ResultRecord.Unset || (snapshotMutationPolicy = this.policy) == null || !snapshotMutationPolicy.equivalent(observe, resultRecord2.result)) {
                        ResultRecord resultRecord3 = this.first;
                        synchronized (obj) {
                            StateRecord newOverwritableRecordLocked = SnapshotKt.newOverwritableRecordLocked(resultRecord3, this);
                            newOverwritableRecordLocked.assign(resultRecord3);
                            newOverwritableRecordLocked.snapshotId = currentSnapshot.getSnapshotId();
                            resultRecord2 = (ResultRecord) newOverwritableRecordLocked;
                            resultRecord2.dependencies = mutableObjectIntMap2;
                            resultRecord2.resultHash = resultRecord2.readableHash(this, currentSnapshot);
                            resultRecord2.result = observe;
                        }
                        return resultRecord2;
                    }
                    resultRecord2.dependencies = mutableObjectIntMap2;
                    resultRecord2.resultHash = resultRecord2.readableHash(this, currentSnapshot);
                } catch (Throwable th3) {
                    throw th3;
                }
            }
            IntRef intRef3 = (IntRef) SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel.get();
            if (intRef3 == null || intRef3.element != 0) {
                return resultRecord2;
            }
            SnapshotKt.currentSnapshot().notifyObjectsInitialized$runtime_release();
            synchronized (obj) {
                Snapshot currentSnapshot2 = SnapshotKt.currentSnapshot();
                resultRecord2.validSnapshotId = currentSnapshot2.getSnapshotId();
                resultRecord2.validSnapshotWriteCount = currentSnapshot2.getWriteCount$runtime_release();
                Unit unit2 = Unit.INSTANCE;
                return resultRecord2;
            }
        } catch (Throwable th4) {
            Object[] objArr7 = derivedStateObservers2.content;
            int i20 = derivedStateObservers2.size;
            for (int i21 = 0; i21 < i20; i21++) {
                ((DerivedStateObserver) objArr7[i21]).done();
            }
            throw th4;
        }
    }

    @Override // androidx.compose.runtime.DerivedState
    public final ResultRecord getCurrentRecord() {
        Snapshot.Companion.getClass();
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, currentSnapshot), currentSnapshot, false, this.calculation);
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.first;
    }

    @Override // androidx.compose.runtime.DerivedState
    public final SnapshotMutationPolicy getPolicy() {
        return this.policy;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        Snapshot.Companion.getClass();
        Function1 readObserver = SnapshotKt.currentSnapshot().getReadObserver();
        if (readObserver != null) {
            readObserver.mo779invoke(this);
        }
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, currentSnapshot), currentSnapshot, true, this.calculation).result;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.first = (ResultRecord) stateRecord;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DerivedState(value=");
        ResultRecord resultRecord = (ResultRecord) SnapshotKt.current(this.first);
        Snapshot.Companion.getClass();
        sb.append(resultRecord.isValid(this, SnapshotKt.currentSnapshot()) ? String.valueOf(resultRecord.result) : "<Not calculated>");
        sb.append(")@");
        sb.append(hashCode());
        return sb.toString();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ResultRecord<T> extends StateRecord {
        public static final Companion Companion = new Companion(null);
        public static final Object Unset = new Object();
        public MutableObjectIntMap dependencies;
        public Object result;
        public int resultHash;
        public long validSnapshotId;
        public int validSnapshotWriteCount;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public ResultRecord(long j) {
            super(j);
            this.dependencies = ObjectIntMapKt.EmptyObjectIntMap;
            this.result = Unset;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            ResultRecord resultRecord = (ResultRecord) stateRecord;
            this.dependencies = resultRecord.dependencies;
            this.result = resultRecord.result;
            this.resultHash = resultRecord.resultHash;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return new ResultRecord(SnapshotKt.currentSnapshot().getSnapshotId());
        }

        public final boolean isValid(DerivedState derivedState, Snapshot snapshot) {
            boolean z;
            boolean z2;
            Object obj = SnapshotKt.lock;
            synchronized (obj) {
                z = true;
                if (this.validSnapshotId == snapshot.getSnapshotId()) {
                    if (this.validSnapshotWriteCount == snapshot.getWriteCount$runtime_release()) {
                        z2 = false;
                    }
                }
                z2 = true;
            }
            if (this.result == Unset || (z2 && this.resultHash != readableHash(derivedState, snapshot))) {
                z = false;
            }
            if (!z || !z2) {
                return z;
            }
            synchronized (obj) {
                this.validSnapshotId = snapshot.getSnapshotId();
                this.validSnapshotWriteCount = snapshot.getWriteCount$runtime_release();
                Unit unit = Unit.INSTANCE;
            }
            return z;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int readableHash(DerivedState derivedState, Snapshot snapshot) {
            MutableObjectIntMap mutableObjectIntMap;
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            StateRecord current;
            int i7 = 1;
            synchronized (SnapshotKt.lock) {
                mutableObjectIntMap = this.dependencies;
            }
            boolean z = false;
            int i8 = 7;
            if ((mutableObjectIntMap._size != 0) != true) {
                return 7;
            }
            MutableVector derivedStateObservers = SnapshotStateKt.derivedStateObservers();
            Object[] objArr = derivedStateObservers.content;
            int i9 = derivedStateObservers.size;
            for (int i10 = 0; i10 < i9; i10++) {
                ((DerivedStateObserver) objArr[i10]).start();
            }
            try {
                Object[] objArr2 = mutableObjectIntMap.keys;
                int[] iArr = mutableObjectIntMap.values;
                long[] jArr = mutableObjectIntMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i11 = 0;
                    i = 7;
                    while (true) {
                        long j = jArr[i11];
                        if ((((~j) << i8) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i12 = 8;
                            int i13 = 8 - ((~(i11 - length)) >>> 31);
                            int i14 = z ? 1 : 0;
                            while (i14 < i13) {
                                if ((j & 255) < 128) {
                                    int i15 = (i11 << 3) + i14;
                                    i5 = i8;
                                    i6 = i12;
                                    StateObject stateObject = (StateObject) objArr2[i15];
                                    if (iArr[i15] != i7) {
                                        i4 = i7;
                                    } else {
                                        if (stateObject instanceof DerivedSnapshotState) {
                                            DerivedSnapshotState derivedSnapshotState = (DerivedSnapshotState) stateObject;
                                            i4 = i7;
                                            try {
                                                current = derivedSnapshotState.currentRecord((ResultRecord) SnapshotKt.current(derivedSnapshotState.first, snapshot), snapshot, z, derivedSnapshotState.calculation);
                                            } catch (Throwable th) {
                                                th = th;
                                                Object[] objArr3 = derivedStateObservers.content;
                                                int i16 = derivedStateObservers.size;
                                                for (int i17 = 0; i17 < i16; i17++) {
                                                    ((DerivedStateObserver) objArr3[i17]).done();
                                                }
                                                throw th;
                                            }
                                        } else {
                                            i4 = i7;
                                            current = SnapshotKt.current(stateObject.getFirstStateRecord(), snapshot);
                                        }
                                        i = (((i * 31) + System.identityHashCode(current)) * 31) + Long.hashCode(current.snapshotId);
                                    }
                                } else {
                                    i4 = i7;
                                    i5 = i8;
                                    i6 = i12;
                                }
                                j >>= i6;
                                i14++;
                                i8 = i5;
                                i12 = i6;
                                i7 = i4;
                                z = false;
                            }
                            i2 = i7;
                            i3 = i8;
                            if (i13 != i12) {
                                break;
                            }
                        } else {
                            i2 = i7;
                            i3 = i8;
                        }
                        if (i11 == length) {
                            i8 = i;
                            break;
                        }
                        i11++;
                        i8 = i3;
                        i7 = i2;
                        z = false;
                    }
                }
                i = i8;
                Unit unit = Unit.INSTANCE;
                Object[] objArr4 = derivedStateObservers.content;
                int i18 = derivedStateObservers.size;
                for (int i19 = 0; i19 < i18; i19++) {
                    ((DerivedStateObserver) objArr4[i19]).done();
                }
                return i;
            } catch (Throwable th2) {
                th = th2;
            }
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new ResultRecord(j);
        }
    }
}
