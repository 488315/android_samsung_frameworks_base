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
/* loaded from: classes.dex */
public final class DerivedSnapshotState<T> extends StateObjectImpl implements DerivedState<T> {
    public final Function0 calculation;
    public ResultRecord first = new ResultRecord(SnapshotKt.currentSnapshot().getSnapshotId());
    public final SnapshotMutationPolicy policy;

    public DerivedSnapshotState(Function0 function0, SnapshotMutationPolicy<T> snapshotMutationPolicy) {
        this.calculation = function0;
        this.policy = snapshotMutationPolicy;
    }

    public final ResultRecord currentRecord(ResultRecord resultRecord, Snapshot snapshot, boolean z, Function0 function0) throws Throwable {
        SnapshotMutationPolicy snapshotMutationPolicy;
        boolean z2;
        int i;
        ResultRecord resultRecord2 = resultRecord;
        boolean z3 = true;
        int i2 = 0;
        if (resultRecord2.isValid(this, snapshot)) {
            if (z) {
                MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
                Object[] objArr = mutableVectorDerivedStateObservers.content;
                int i3 = mutableVectorDerivedStateObservers.size;
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
                                                readObserver.mo781invoke(stateObject);
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            Object[] objArr3 = mutableVectorDerivedStateObservers.content;
                                            int i11 = mutableVectorDerivedStateObservers.size;
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
                    Object[] objArr4 = mutableVectorDerivedStateObservers.content;
                    int i13 = mutableVectorDerivedStateObservers.size;
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
        MutableVector mutableVectorDerivedStateObservers2 = SnapshotStateKt.derivedStateObservers();
        Object[] objArr5 = mutableVectorDerivedStateObservers2.content;
        int i16 = mutableVectorDerivedStateObservers2.size;
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
                public final Object mo781invoke(Object obj) {
                    if (obj == this.this$0) {
                        throw new IllegalStateException("A derived state calculation cannot read itself");
                    }
                    if (obj instanceof StateObject) {
                        int i18 = intRef2.element;
                        MutableObjectIntMap mutableObjectIntMap3 = mutableObjectIntMap2;
                        int i19 = i18 - i15;
                        int iFindKeyIndex = mutableObjectIntMap3.findKeyIndex(obj);
                        mutableObjectIntMap3.set(Math.min(i19, iFindKeyIndex >= 0 ? mutableObjectIntMap3.values[iFindKeyIndex] : Integer.MAX_VALUE), obj);
                    }
                    return Unit.INSTANCE;
                }
            };
            companion.getClass();
            Object objObserve = Snapshot.Companion.observe(function1, function0);
            intRef2.element = i15;
            Object[] objArr6 = mutableVectorDerivedStateObservers2.content;
            int i18 = mutableVectorDerivedStateObservers2.size;
            for (int i19 = 0; i19 < i18; i19++) {
                ((DerivedStateObserver) objArr6[i19]).done();
            }
            Object obj = SnapshotKt.lock;
            synchronized (obj) {
                try {
                    Snapshot.Companion.getClass();
                    Snapshot snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                    Object obj2 = resultRecord2.result;
                    ResultRecord.Companion.getClass();
                    if (obj2 == ResultRecord.Unset || (snapshotMutationPolicy = this.policy) == null || !snapshotMutationPolicy.equivalent(objObserve, resultRecord2.result)) {
                        ResultRecord resultRecord3 = this.first;
                        synchronized (obj) {
                            StateRecord stateRecordNewOverwritableRecordLocked = SnapshotKt.newOverwritableRecordLocked(resultRecord3, this);
                            stateRecordNewOverwritableRecordLocked.assign(resultRecord3);
                            stateRecordNewOverwritableRecordLocked.snapshotId = snapshotCurrentSnapshot.getSnapshotId();
                            resultRecord2 = (ResultRecord) stateRecordNewOverwritableRecordLocked;
                            resultRecord2.dependencies = mutableObjectIntMap2;
                            resultRecord2.resultHash = resultRecord2.readableHash(this, snapshotCurrentSnapshot);
                            resultRecord2.result = objObserve;
                        }
                        return resultRecord2;
                    }
                    resultRecord2.dependencies = mutableObjectIntMap2;
                    resultRecord2.resultHash = resultRecord2.readableHash(this, snapshotCurrentSnapshot);
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
                Snapshot snapshotCurrentSnapshot2 = SnapshotKt.currentSnapshot();
                resultRecord2.validSnapshotId = snapshotCurrentSnapshot2.getSnapshotId();
                resultRecord2.validSnapshotWriteCount = snapshotCurrentSnapshot2.getWriteCount$runtime_release();
                Unit unit2 = Unit.INSTANCE;
                return resultRecord2;
            }
        } catch (Throwable th4) {
            Object[] objArr7 = mutableVectorDerivedStateObservers2.content;
            int i20 = mutableVectorDerivedStateObservers2.size;
            for (int i21 = 0; i21 < i20; i21++) {
                ((DerivedStateObserver) objArr7[i21]).done();
            }
            throw th4;
        }
    }

    @Override // androidx.compose.runtime.DerivedState
    public final ResultRecord getCurrentRecord() {
        Snapshot.Companion.getClass();
        Snapshot snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, snapshotCurrentSnapshot), snapshotCurrentSnapshot, false, this.calculation);
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
            readObserver.mo781invoke(this);
        }
        Snapshot snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
        return currentRecord((ResultRecord) SnapshotKt.current(this.first, snapshotCurrentSnapshot), snapshotCurrentSnapshot, true, this.calculation).result;
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

    public final class ResultRecord<T> extends StateRecord {
        public static final Companion Companion = new Companion(null);
        public static final Object Unset = new Object();
        public MutableObjectIntMap dependencies;
        public Object result;
        public int resultHash;
        public long validSnapshotId;
        public int validSnapshotWriteCount;

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

        /* JADX WARN: Removed duplicated region for block: B:12:0x001c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean isValid(DerivedState derivedState, Snapshot snapshot) {
            boolean z;
            boolean z2;
            Object obj = SnapshotKt.lock;
            synchronized (obj) {
                z = true;
                if (this.validSnapshotId == snapshot.getSnapshotId()) {
                    z2 = this.validSnapshotWriteCount != snapshot.getWriteCount$runtime_release();
                }
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
        public final int readableHash(DerivedState derivedState, Snapshot snapshot) throws Throwable {
            MutableObjectIntMap mutableObjectIntMap;
            int iIdentityHashCode;
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            StateRecord stateRecordCurrent;
            int i6 = 1;
            synchronized (SnapshotKt.lock) {
                mutableObjectIntMap = this.dependencies;
            }
            boolean z = false;
            int i7 = 7;
            if ((mutableObjectIntMap._size != 0) != true) {
                return 7;
            }
            MutableVector mutableVectorDerivedStateObservers = SnapshotStateKt.derivedStateObservers();
            Object[] objArr = mutableVectorDerivedStateObservers.content;
            int i8 = mutableVectorDerivedStateObservers.size;
            for (int i9 = 0; i9 < i8; i9++) {
                ((DerivedStateObserver) objArr[i9]).start();
            }
            try {
                Object[] objArr2 = mutableObjectIntMap.keys;
                int[] iArr = mutableObjectIntMap.values;
                long[] jArr = mutableObjectIntMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i10 = 0;
                    iIdentityHashCode = 7;
                    while (true) {
                        long j = jArr[i10];
                        if ((((~j) << i7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i11 = 8;
                            int i12 = 8 - ((~(i10 - length)) >>> 31);
                            int i13 = z ? 1 : 0;
                            while (i13 < i12) {
                                if ((j & 255) < 128) {
                                    int i14 = (i10 << 3) + i13;
                                    i4 = i7;
                                    i5 = i11;
                                    StateObject stateObject = (StateObject) objArr2[i14];
                                    if (iArr[i14] != i6) {
                                        i3 = i6;
                                    } else {
                                        if (stateObject instanceof DerivedSnapshotState) {
                                            DerivedSnapshotState derivedSnapshotState = (DerivedSnapshotState) stateObject;
                                            i3 = i6;
                                            try {
                                                stateRecordCurrent = derivedSnapshotState.currentRecord((ResultRecord) SnapshotKt.current(derivedSnapshotState.first, snapshot), snapshot, z, derivedSnapshotState.calculation);
                                            } catch (Throwable th) {
                                                th = th;
                                                Object[] objArr3 = mutableVectorDerivedStateObservers.content;
                                                int i15 = mutableVectorDerivedStateObservers.size;
                                                for (int i16 = 0; i16 < i15; i16++) {
                                                    ((DerivedStateObserver) objArr3[i16]).done();
                                                }
                                                throw th;
                                            }
                                        } else {
                                            i3 = i6;
                                            stateRecordCurrent = SnapshotKt.current(stateObject.getFirstStateRecord(), snapshot);
                                        }
                                        iIdentityHashCode = (((iIdentityHashCode * 31) + System.identityHashCode(stateRecordCurrent)) * 31) + Long.hashCode(stateRecordCurrent.snapshotId);
                                    }
                                } else {
                                    i3 = i6;
                                    i4 = i7;
                                    i5 = i11;
                                }
                                j >>= i5;
                                i13++;
                                i7 = i4;
                                i11 = i5;
                                i6 = i3;
                                z = false;
                            }
                            i = i6;
                            i2 = i7;
                            if (i12 != i11) {
                                break;
                            }
                        } else {
                            i = i6;
                            i2 = i7;
                        }
                        if (i10 == length) {
                            i7 = iIdentityHashCode;
                            break;
                        }
                        i10++;
                        i7 = i2;
                        i6 = i;
                        z = false;
                    }
                }
                iIdentityHashCode = i7;
                Unit unit = Unit.INSTANCE;
                Object[] objArr4 = mutableVectorDerivedStateObservers.content;
                int i17 = mutableVectorDerivedStateObservers.size;
                for (int i18 = 0; i18 < i17; i18++) {
                    ((DerivedStateObserver) objArr4[i18]).done();
                }
                return iIdentityHashCode;
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
