package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.internal.AtomicInt;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import androidx.compose.runtime.internal.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class SnapshotKt {
    public static List applyObservers;
    public static final SnapshotWeakSet extraStateObjects;
    public static final GlobalSnapshot globalSnapshot;
    public static List globalWriteObservers;
    public static long nextSnapshotId;
    public static SnapshotIdSet openSnapshots;
    public static final AtomicInt pendingApplyObserverCount;
    public static final SnapshotDoubleIndexHeap pinningTable;
    public static final Function1 emptyLambda = new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotKt$emptyLambda$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
            return Unit.INSTANCE;
        }
    };
    public static final SnapshotThreadLocal threadSnapshot = new SnapshotThreadLocal();
    public static final Object lock = new Object();

    static {
        SnapshotIdSet.Companion.getClass();
        SnapshotIdSet snapshotIdSet = SnapshotIdSet.EMPTY;
        openSnapshots = snapshotIdSet;
        nextSnapshotId = 1 + 1;
        pinningTable = new SnapshotDoubleIndexHeap();
        extraStateObjects = new SnapshotWeakSet();
        EmptyList emptyList = EmptyList.INSTANCE;
        applyObservers = emptyList;
        globalWriteObservers = emptyList;
        long j = nextSnapshotId;
        nextSnapshotId = 1 + j;
        GlobalSnapshot globalSnapshot2 = new GlobalSnapshot(j, snapshotIdSet);
        openSnapshots = openSnapshots.set(globalSnapshot2.snapshotId);
        globalSnapshot = globalSnapshot2;
        pendingApplyObserverCount = new AtomicInt(0);
    }

    public static final Function1 access$mergedWriteObserver(final Function1 function1, final Function1 function12) {
        return (function1 == null || function12 == null || function1 == function12) ? function1 == null ? function12 : function1 : new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotKt$mergedWriteObserver$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                function1.mo781invoke(obj);
                function12.mo781invoke(obj);
                return Unit.INSTANCE;
            }
        };
    }

    public static final Map access$optimisticMerges(long j, MutableSnapshot mutableSnapshot, SnapshotIdSet snapshotIdSet) {
        long[] jArr;
        SnapshotIdSet snapshotIdSet2;
        long[] jArr2;
        SnapshotIdSet snapshotIdSet3;
        int i;
        StateRecord stateRecord;
        long j2 = j;
        MutableScatterSet modified$runtime_release = mutableSnapshot.getModified$runtime_release();
        if (modified$runtime_release != null) {
            SnapshotIdSet snapshotIdSetOr = mutableSnapshot.getInvalid$runtime_release().set(mutableSnapshot.getSnapshotId()).or(mutableSnapshot.previousIds);
            Object[] objArr = modified$runtime_release.elements;
            long[] jArr3 = modified$runtime_release.metadata;
            int length = jArr3.length - 2;
            if (length >= 0) {
                int i2 = 0;
                HashMap map = null;
                while (true) {
                    long j3 = jArr3[i2];
                    if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i3 = 8;
                        int i4 = 8 - ((~(i2 - length)) >>> 31);
                        int i5 = 0;
                        while (i5 < i4) {
                            if ((j3 & 255) < 128) {
                                StateObject stateObject = (StateObject) objArr[(i2 << 3) + i5];
                                StateRecord firstStateRecord = stateObject.getFirstStateRecord();
                                jArr2 = jArr3;
                                i = i3;
                                StateRecord stateRecord2 = readable(firstStateRecord, j2, snapshotIdSet);
                                if (stateRecord2 == null || (stateRecord = readable(firstStateRecord, j2, snapshotIdSetOr)) == null || stateRecord2.equals(stateRecord)) {
                                    snapshotIdSet3 = snapshotIdSetOr;
                                } else {
                                    snapshotIdSet3 = snapshotIdSetOr;
                                    StateRecord stateRecord3 = readable(firstStateRecord, mutableSnapshot.getSnapshotId(), mutableSnapshot.getInvalid$runtime_release());
                                    if (stateRecord3 == null) {
                                        readError();
                                        throw null;
                                    }
                                    StateRecord stateRecordMergeRecords = stateObject.mergeRecords(stateRecord, stateRecord2, stateRecord3);
                                    if (stateRecordMergeRecords == null) {
                                        return null;
                                    }
                                    if (map == null) {
                                        map = new HashMap();
                                    }
                                    map.put(stateRecord2, stateRecordMergeRecords);
                                    map = map;
                                }
                            } else {
                                jArr2 = jArr3;
                                snapshotIdSet3 = snapshotIdSetOr;
                                i = i3;
                            }
                            j3 >>= i;
                            i5++;
                            j2 = j;
                            i3 = i;
                            jArr3 = jArr2;
                            snapshotIdSetOr = snapshotIdSet3;
                        }
                        jArr = jArr3;
                        snapshotIdSet2 = snapshotIdSetOr;
                        if (i4 != i3) {
                            return map;
                        }
                    } else {
                        jArr = jArr3;
                        snapshotIdSet2 = snapshotIdSetOr;
                    }
                    if (i2 == length) {
                        return map;
                    }
                    i2++;
                    j2 = j;
                    jArr3 = jArr;
                    snapshotIdSetOr = snapshotIdSet2;
                }
            }
        }
        return null;
    }

    public static final void access$validateOpen(Snapshot snapshot) {
        long j;
        if (openSnapshots.get(snapshot.getSnapshotId())) {
            return;
        }
        StringBuilder sb = new StringBuilder("Snapshot is not open: snapshotId=");
        sb.append(snapshot.getSnapshotId());
        sb.append(", disposed=");
        sb.append(snapshot.disposed);
        sb.append(", applied=");
        MutableSnapshot mutableSnapshot = snapshot instanceof MutableSnapshot ? (MutableSnapshot) snapshot : null;
        sb.append(mutableSnapshot != null ? Boolean.valueOf(mutableSnapshot.applied) : "read-only");
        sb.append(", lowestPin=");
        synchronized (lock) {
            SnapshotDoubleIndexHeap snapshotDoubleIndexHeap = pinningTable;
            j = snapshotDoubleIndexHeap.size > 0 ? snapshotDoubleIndexHeap.values[0] : -1L;
        }
        sb.append(j);
        throw new IllegalStateException(sb.toString().toString());
    }

    public static final SnapshotIdSet addRange(SnapshotIdSet snapshotIdSet, long j, long j2) {
        while (j < j2) {
            snapshotIdSet = snapshotIdSet.set(j);
            j++;
        }
        return snapshotIdSet;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object advanceGlobalSnapshot(Function1 function1) {
        MutableScatterSet mutableScatterSet;
        Object objResetGlobalSnapshotLocked;
        GlobalSnapshot globalSnapshot2 = globalSnapshot;
        synchronized (lock) {
            try {
                mutableScatterSet = globalSnapshot2.modified;
                if (mutableScatterSet != null) {
                    pendingApplyObserverCount.addAndGet(1);
                }
                objResetGlobalSnapshotLocked = resetGlobalSnapshotLocked(globalSnapshot2, function1);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (mutableScatterSet != null) {
            try {
                List list = applyObservers;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ((Function2) list.get(i)).invoke(new ScatterSetWrapper(mutableScatterSet), globalSnapshot2);
                }
            } finally {
                pendingApplyObserverCount.addAndGet(-1);
            }
        }
        synchronized (lock) {
            try {
                checkAndOverwriteUnusedRecordsLocked();
                if (mutableScatterSet != null) {
                    Object[] objArr = mutableScatterSet.elements;
                    long[] jArr = mutableScatterSet.metadata;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i2 = 0;
                        while (true) {
                            long j = jArr[i2];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i3 = 8 - ((~(i2 - length)) >>> 31);
                                for (int i4 = 0; i4 < i3; i4++) {
                                    if ((255 & j) < 128) {
                                        processForUnusedRecordsLocked((StateObject) objArr[(i2 << 3) + i4]);
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
                    Unit unit = Unit.INSTANCE;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return objResetGlobalSnapshotLocked;
    }

    public static final void checkAndOverwriteUnusedRecordsLocked() {
        SnapshotWeakSet snapshotWeakSet = extraStateObjects;
        int i = snapshotWeakSet.size;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= i) {
                break;
            }
            WeakReference weakReference = snapshotWeakSet.values[i2];
            Object obj = weakReference != null ? weakReference.get() : null;
            if (obj != null && overwriteUnusedRecordsLocked((StateObject) obj)) {
                if (i3 != i2) {
                    snapshotWeakSet.values[i3] = weakReference;
                    int[] iArr = snapshotWeakSet.hashes;
                    iArr[i3] = iArr[i2];
                }
                i3++;
            }
            i2++;
        }
        for (int i4 = i3; i4 < i; i4++) {
            snapshotWeakSet.values[i4] = null;
            snapshotWeakSet.hashes[i4] = 0;
        }
        if (i3 != i) {
            snapshotWeakSet.size = i3;
        }
    }

    public static final Snapshot createTransparentSnapshotWithNoParentReadObserver(Snapshot snapshot, Function1 function1, boolean z) {
        boolean z2 = snapshot instanceof MutableSnapshot;
        if (z2 || snapshot == null) {
            return new TransparentObserverMutableSnapshot(z2 ? (MutableSnapshot) snapshot : null, function1, null, false, z);
        }
        return new TransparentObserverSnapshot(snapshot, function1, false, z);
    }

    public static final StateRecord current(StateRecord stateRecord, Snapshot snapshot) {
        StateRecord stateRecord2;
        StateRecord stateRecord3 = readable(stateRecord, snapshot.getSnapshotId(), snapshot.getInvalid$runtime_release());
        if (stateRecord3 != null) {
            return stateRecord3;
        }
        synchronized (lock) {
            stateRecord2 = readable(stateRecord, snapshot.getSnapshotId(), snapshot.getInvalid$runtime_release());
        }
        if (stateRecord2 != null) {
            return stateRecord2;
        }
        readError();
        throw null;
    }

    public static final Snapshot currentSnapshot() {
        Snapshot snapshot = (Snapshot) threadSnapshot.get();
        return snapshot == null ? globalSnapshot : snapshot;
    }

    public static final Function1 mergedReadObserver(final Function1 function1, boolean z, final Function1 function12) {
        if (!z) {
            function12 = null;
        }
        return (function1 == null || function12 == null || function1 == function12) ? function1 == null ? function12 : function1 : new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotKt.mergedReadObserver.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                function1.mo781invoke(obj);
                function12.mo781invoke(obj);
                return Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        r4 = r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final StateRecord newOverwritableRecordLocked(StateRecord stateRecord, StateObject stateObject) {
        StateRecord firstStateRecord = stateObject.getFirstStateRecord();
        long j = nextSnapshotId;
        SnapshotDoubleIndexHeap snapshotDoubleIndexHeap = pinningTable;
        if (snapshotDoubleIndexHeap.size > 0) {
            j = snapshotDoubleIndexHeap.values[0];
        }
        long j2 = j - 1;
        SnapshotIdSet.Companion.getClass();
        SnapshotIdSet snapshotIdSet = SnapshotIdSet.EMPTY;
        StateRecord stateRecord2 = null;
        StateRecord stateRecord3 = null;
        while (true) {
            if (firstStateRecord == null) {
                break;
            }
            long j3 = firstStateRecord.snapshotId;
            if (j3 == 0) {
                break;
            }
            if (j3 != 0 && j3 <= j2 && !snapshotIdSet.get(j3)) {
                if (stateRecord3 == null) {
                    stateRecord3 = firstStateRecord;
                } else {
                    if (firstStateRecord.snapshotId < stateRecord3.snapshotId) {
                        break;
                    }
                    stateRecord2 = stateRecord3;
                }
            }
            firstStateRecord = firstStateRecord.next;
        }
        if (stateRecord2 != null) {
            stateRecord2.snapshotId = Long.MAX_VALUE;
            return stateRecord2;
        }
        StateRecord stateRecordCreate = stateRecord.create(Long.MAX_VALUE);
        stateRecordCreate.next = stateObject.getFirstStateRecord();
        stateObject.prependStateRecord(stateRecordCreate);
        return stateRecordCreate;
    }

    public static final void notifyWrite(Snapshot snapshot, StateObject stateObject) {
        snapshot.setWriteCount$runtime_release(snapshot.getWriteCount$runtime_release() + 1);
        Function1 writeObserver$runtime_release = snapshot.getWriteObserver$runtime_release();
        if (writeObserver$runtime_release != null) {
            writeObserver$runtime_release.mo781invoke(stateObject);
        }
    }

    public static final StateRecord overwritableRecord(StateRecord stateRecord, StateObjectImpl stateObjectImpl, Snapshot snapshot, StateRecord stateRecord2) {
        StateRecord stateRecordNewOverwritableRecordLocked;
        if (snapshot.getReadOnly()) {
            snapshot.recordModified$runtime_release(stateObjectImpl);
        }
        long snapshotId = snapshot.getSnapshotId();
        if (stateRecord2.snapshotId == snapshotId) {
            return stateRecord2;
        }
        synchronized (lock) {
            stateRecordNewOverwritableRecordLocked = newOverwritableRecordLocked(stateRecord, stateObjectImpl);
        }
        stateRecordNewOverwritableRecordLocked.snapshotId = snapshotId;
        if (stateRecord2.snapshotId != 1) {
            snapshot.recordModified$runtime_release(stateObjectImpl);
        }
        return stateRecordNewOverwritableRecordLocked;
    }

    public static final boolean overwriteUnusedRecordsLocked(StateObject stateObject) {
        StateRecord stateRecord;
        long j = nextSnapshotId;
        SnapshotDoubleIndexHeap snapshotDoubleIndexHeap = pinningTable;
        if (snapshotDoubleIndexHeap.size > 0) {
            j = snapshotDoubleIndexHeap.values[0];
        }
        StateRecord stateRecord2 = null;
        StateRecord firstStateRecord = null;
        int i = 0;
        for (StateRecord firstStateRecord2 = stateObject.getFirstStateRecord(); firstStateRecord2 != null; firstStateRecord2 = firstStateRecord2.next) {
            long j2 = firstStateRecord2.snapshotId;
            if (j2 != 0) {
                if (j2 >= j) {
                    i++;
                } else if (stateRecord2 == null) {
                    i++;
                    stateRecord2 = firstStateRecord2;
                } else {
                    if (j2 < stateRecord2.snapshotId) {
                        stateRecord = stateRecord2;
                        stateRecord2 = firstStateRecord2;
                    } else {
                        stateRecord = firstStateRecord2;
                    }
                    if (firstStateRecord == null) {
                        firstStateRecord = stateObject.getFirstStateRecord();
                        StateRecord stateRecord3 = firstStateRecord;
                        while (true) {
                            if (firstStateRecord == null) {
                                firstStateRecord = stateRecord3;
                                break;
                            }
                            long j3 = firstStateRecord.snapshotId;
                            if (j3 >= j) {
                                break;
                            }
                            if (stateRecord3.snapshotId < j3) {
                                stateRecord3 = firstStateRecord;
                            }
                            firstStateRecord = firstStateRecord.next;
                        }
                    }
                    stateRecord2.snapshotId = 0L;
                    stateRecord2.assign(firstStateRecord);
                    stateRecord2 = stateRecord;
                }
            }
        }
        return i > 1;
    }

    public static final void processForUnusedRecordsLocked(StateObject stateObject) {
        if (overwriteUnusedRecordsLocked(stateObject)) {
            SnapshotWeakSet snapshotWeakSet = extraStateObjects;
            int i = snapshotWeakSet.size;
            int iIdentityHashCode = System.identityHashCode(stateObject);
            int i2 = -1;
            if (i > 0) {
                int i3 = snapshotWeakSet.size - 1;
                int i4 = 0;
                while (true) {
                    if (i4 > i3) {
                        i2 = -(i4 + 1);
                        break;
                    }
                    int i5 = (i4 + i3) >>> 1;
                    int i6 = snapshotWeakSet.hashes[i5];
                    if (i6 < iIdentityHashCode) {
                        i4 = i5 + 1;
                    } else if (i6 > iIdentityHashCode) {
                        i3 = i5 - 1;
                    } else {
                        WeakReference weakReference = snapshotWeakSet.values[i5];
                        if (stateObject == (weakReference != null ? weakReference.get() : null)) {
                            i2 = i5;
                        } else {
                            for (int i7 = i5 - 1; -1 < i7 && snapshotWeakSet.hashes[i7] == iIdentityHashCode; i7--) {
                                WeakReference weakReference2 = snapshotWeakSet.values[i7];
                                if ((weakReference2 != null ? weakReference2.get() : null) == stateObject) {
                                    i2 = i7;
                                    break;
                                }
                            }
                            i5++;
                            int i8 = snapshotWeakSet.size;
                            while (true) {
                                if (i5 >= i8) {
                                    i2 = -(snapshotWeakSet.size + 1);
                                    break;
                                } else {
                                    if (snapshotWeakSet.hashes[i5] != iIdentityHashCode) {
                                        i2 = -(i5 + 1);
                                        break;
                                    }
                                    WeakReference weakReference3 = snapshotWeakSet.values[i5];
                                    if ((weakReference3 != null ? weakReference3.get() : null) == stateObject) {
                                        break;
                                    } else {
                                        i5++;
                                    }
                                }
                            }
                            i2 = i5;
                        }
                    }
                }
                if (i2 >= 0) {
                    return;
                }
            }
            int i9 = -(i2 + 1);
            WeakReference[] weakReferenceArr = snapshotWeakSet.values;
            int length = weakReferenceArr.length;
            if (i == length) {
                int i10 = length * 2;
                WeakReference[] weakReferenceArr2 = new WeakReference[i10];
                int[] iArr = new int[i10];
                int i11 = i9 + 1;
                System.arraycopy(weakReferenceArr, i9, weakReferenceArr2, i11, i - i9);
                System.arraycopy(snapshotWeakSet.values, 0, weakReferenceArr2, 0, i9);
                ArraysKt___ArraysJvmKt.copyInto(i11, i9, i, snapshotWeakSet.hashes, iArr);
                ArraysKt___ArraysJvmKt.copyInto$default(0, i9, 6, snapshotWeakSet.hashes, iArr);
                snapshotWeakSet.values = weakReferenceArr2;
                snapshotWeakSet.hashes = iArr;
            } else {
                int i12 = i9 + 1;
                System.arraycopy(weakReferenceArr, i9, weakReferenceArr, i12, i - i9);
                int[] iArr2 = snapshotWeakSet.hashes;
                ArraysKt___ArraysJvmKt.copyInto(i12, i9, i, iArr2, iArr2);
            }
            snapshotWeakSet.values[i9] = new WeakReference(stateObject);
            snapshotWeakSet.hashes[i9] = iIdentityHashCode;
            snapshotWeakSet.size++;
        }
    }

    public static final void readError() {
        throw new IllegalStateException("Reading a state that was created after the snapshot was taken or in a snapshot that has not yet been applied");
    }

    public static final StateRecord readable(StateRecord stateRecord, long j, SnapshotIdSet snapshotIdSet) {
        StateRecord stateRecord2 = null;
        while (stateRecord != null) {
            long j2 = stateRecord.snapshotId;
            if (j2 != 0 && j2 <= j && !snapshotIdSet.get(j2) && (stateRecord2 == null || stateRecord2.snapshotId < stateRecord.snapshotId)) {
                stateRecord2 = stateRecord;
            }
            stateRecord = stateRecord.next;
        }
        if (stateRecord2 != null) {
            return stateRecord2;
        }
        return null;
    }

    public static final void releasePinningLocked(int i) {
        SnapshotDoubleIndexHeap snapshotDoubleIndexHeap = pinningTable;
        int i2 = snapshotDoubleIndexHeap.handles[i];
        snapshotDoubleIndexHeap.swap(i2, snapshotDoubleIndexHeap.size - 1);
        snapshotDoubleIndexHeap.size--;
        long[] jArr = snapshotDoubleIndexHeap.values;
        long j = jArr[i2];
        int i3 = i2;
        while (i3 > 0) {
            int i4 = ((i3 + 1) >> 1) - 1;
            if (jArr[i4] <= j) {
                break;
            }
            snapshotDoubleIndexHeap.swap(i4, i3);
            i3 = i4;
        }
        long[] jArr2 = snapshotDoubleIndexHeap.values;
        int i5 = snapshotDoubleIndexHeap.size >> 1;
        while (i2 < i5) {
            int i6 = (i2 + 1) << 1;
            int i7 = i6 - 1;
            if (i6 < snapshotDoubleIndexHeap.size) {
                long j2 = jArr2[i6];
                if (j2 < jArr2[i7]) {
                    if (j2 >= jArr2[i2]) {
                        break;
                    }
                    snapshotDoubleIndexHeap.swap(i6, i2);
                    i2 = i6;
                }
            }
            if (jArr2[i7] >= jArr2[i2]) {
                break;
            }
            snapshotDoubleIndexHeap.swap(i7, i2);
            i2 = i7;
        }
        snapshotDoubleIndexHeap.handles[i] = snapshotDoubleIndexHeap.firstFreeHandle;
        snapshotDoubleIndexHeap.firstFreeHandle = i;
    }

    public static final Object resetGlobalSnapshotLocked(GlobalSnapshot globalSnapshot2, Function1 function1) {
        long j = globalSnapshot2.snapshotId;
        Object objMo781invoke = function1.mo781invoke(openSnapshots.clear(j));
        long j2 = nextSnapshotId;
        nextSnapshotId = 1 + j2;
        SnapshotIdSet snapshotIdSetClear = openSnapshots.clear(j);
        openSnapshots = snapshotIdSetClear;
        globalSnapshot2.snapshotId = j2;
        globalSnapshot2.invalid = snapshotIdSetClear;
        globalSnapshot2.writeCount = 0;
        globalSnapshot2.modified = null;
        globalSnapshot2.releasePinnedSnapshotLocked$runtime_release();
        openSnapshots = openSnapshots.set(j2);
        return objMo781invoke;
    }

    public static final StateRecord writableRecord(StateRecord stateRecord, StateObject stateObject, Snapshot snapshot) {
        StateRecord stateRecord2;
        if (snapshot.getReadOnly()) {
            snapshot.recordModified$runtime_release(stateObject);
        }
        long snapshotId = snapshot.getSnapshotId();
        StateRecord stateRecord3 = readable(stateRecord, snapshotId, snapshot.getInvalid$runtime_release());
        if (stateRecord3 == null) {
            readError();
            throw null;
        }
        if (stateRecord3.snapshotId == snapshot.getSnapshotId()) {
            return stateRecord3;
        }
        synchronized (lock) {
            stateRecord2 = readable(stateObject.getFirstStateRecord(), snapshotId, snapshot.getInvalid$runtime_release());
            if (stateRecord2 == null) {
                readError();
                throw null;
            }
            if (stateRecord2.snapshotId != snapshotId) {
                StateRecord stateRecordNewOverwritableRecordLocked = newOverwritableRecordLocked(stateRecord2, stateObject);
                stateRecordNewOverwritableRecordLocked.assign(stateRecord2);
                stateRecordNewOverwritableRecordLocked.snapshotId = snapshot.getSnapshotId();
                stateRecord2 = stateRecordNewOverwritableRecordLocked;
            }
        }
        if (stateRecord3.snapshotId != 1) {
            snapshot.recordModified$runtime_release(stateObject);
        }
        return stateRecord2;
    }

    public static final StateRecord readable(StateRecord stateRecord, StateObject stateObject) {
        StateRecord stateRecord2;
        Snapshot.Companion.getClass();
        Snapshot snapshotCurrentSnapshot = currentSnapshot();
        Function1 readObserver = snapshotCurrentSnapshot.getReadObserver();
        if (readObserver != null) {
            readObserver.mo781invoke(stateObject);
        }
        StateRecord stateRecord3 = readable(stateRecord, snapshotCurrentSnapshot.getSnapshotId(), snapshotCurrentSnapshot.getInvalid$runtime_release());
        if (stateRecord3 != null) {
            return stateRecord3;
        }
        synchronized (lock) {
            Snapshot snapshotCurrentSnapshot2 = currentSnapshot();
            stateRecord2 = readable(stateObject.getFirstStateRecord(), snapshotCurrentSnapshot2.getSnapshotId(), snapshotCurrentSnapshot2.getInvalid$runtime_release());
            if (stateRecord2 == null) {
                readError();
                throw null;
            }
        }
        return stateRecord2;
    }

    public static final StateRecord current(StateRecord stateRecord) {
        StateRecord stateRecord2;
        Snapshot.Companion.getClass();
        Snapshot snapshotCurrentSnapshot = currentSnapshot();
        StateRecord stateRecord3 = readable(stateRecord, snapshotCurrentSnapshot.getSnapshotId(), snapshotCurrentSnapshot.getInvalid$runtime_release());
        if (stateRecord3 != null) {
            return stateRecord3;
        }
        synchronized (lock) {
            Snapshot snapshotCurrentSnapshot2 = currentSnapshot();
            stateRecord2 = readable(stateRecord, snapshotCurrentSnapshot2.getSnapshotId(), snapshotCurrentSnapshot2.getInvalid$runtime_release());
        }
        if (stateRecord2 != null) {
            return stateRecord2;
        }
        readError();
        throw null;
    }
}
