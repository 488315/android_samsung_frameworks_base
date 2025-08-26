package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.collection.ScatterSetWrapper;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public class MutableSnapshot extends Snapshot {
    public static final int[] EmptyIntArray;
    public boolean applied;
    public List merged;
    public MutableScatterSet modified;
    public SnapshotIdSet previousIds;
    public int[] previousPinnedSnapshots;
    public final Function1 readObserver;
    public int snapshots;
    public int writeCount;
    public final Function1 writeObserver;

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        EmptyIntArray = new int[0];
    }

    public MutableSnapshot(long j, SnapshotIdSet snapshotIdSet, Function1 function1, Function1 function12) {
        super(j, snapshotIdSet, (DefaultConstructorMarker) null);
        this.readObserver = function1;
        this.writeObserver = function12;
        SnapshotIdSet.Companion.getClass();
        this.previousIds = SnapshotIdSet.EMPTY;
        this.previousPinnedSnapshots = EmptyIntArray;
        this.snapshots = 1;
    }

    public final void advance$runtime_release() {
        recordPrevious$runtime_release(getSnapshotId());
        Unit unit = Unit.INSTANCE;
        if (this.applied || this.disposed) {
            return;
        }
        long snapshotId = getSnapshotId();
        synchronized (SnapshotKt.lock) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId = j + 1;
            setSnapshotId$runtime_release(j);
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
        }
        setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, getSnapshotId()));
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SnapshotApplyResult apply() {
        Map mapAccess$optimisticMerges;
        List list;
        MutableScatterSet mutableScatterSet;
        char c;
        long j;
        long j2;
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        if (modified$runtime_release != null) {
            long j3 = SnapshotKt.globalSnapshot.snapshotId;
            mapAccess$optimisticMerges = SnapshotKt.access$optimisticMerges(j3, this, SnapshotKt.openSnapshots.clear(j3));
        } else {
            mapAccess$optimisticMerges = null;
        }
        EmptyList emptyList = EmptyList.INSTANCE;
        synchronized (SnapshotKt.lock) {
            try {
                SnapshotKt.access$validateOpen(this);
                if (modified$runtime_release == null || modified$runtime_release._size == 0) {
                    closeLocked$runtime_release();
                    GlobalSnapshot globalSnapshot = SnapshotKt.globalSnapshot;
                    MutableScatterSet mutableScatterSet2 = globalSnapshot.modified;
                    SnapshotKt.resetGlobalSnapshotLocked(globalSnapshot, SnapshotKt.emptyLambda);
                    if (mutableScatterSet2 == null || !mutableScatterSet2.isNotEmpty()) {
                        list = emptyList;
                        mutableScatterSet = null;
                    } else {
                        list = SnapshotKt.applyObservers;
                        mutableScatterSet = mutableScatterSet2;
                    }
                } else {
                    GlobalSnapshot globalSnapshot2 = SnapshotKt.globalSnapshot;
                    SnapshotApplyResult snapshotApplyResultInnerApplyLocked$runtime_release = innerApplyLocked$runtime_release(SnapshotKt.nextSnapshotId, modified$runtime_release, mapAccess$optimisticMerges, SnapshotKt.openSnapshots.clear(globalSnapshot2.snapshotId));
                    if (!Intrinsics.areEqual(snapshotApplyResultInnerApplyLocked$runtime_release, SnapshotApplyResult.Success.INSTANCE)) {
                        return snapshotApplyResultInnerApplyLocked$runtime_release;
                    }
                    closeLocked$runtime_release();
                    mutableScatterSet = globalSnapshot2.modified;
                    SnapshotKt.resetGlobalSnapshotLocked(globalSnapshot2, SnapshotKt.emptyLambda);
                    setModified(null);
                    globalSnapshot2.modified = null;
                    list = SnapshotKt.applyObservers;
                }
                Unit unit = Unit.INSTANCE;
                this.applied = true;
                if (mutableScatterSet != null) {
                    ScatterSetWrapper scatterSetWrapper = new ScatterSetWrapper(mutableScatterSet);
                    if (!scatterSetWrapper.set.isEmpty()) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            ((Function2) list.get(i)).invoke(scatterSetWrapper, this);
                        }
                    }
                }
                if (modified$runtime_release != null && modified$runtime_release.isNotEmpty()) {
                    ScatterSetWrapper scatterSetWrapper2 = new ScatterSetWrapper(modified$runtime_release);
                    int size2 = list.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((Function2) list.get(i2)).invoke(scatterSetWrapper2, this);
                    }
                }
                synchronized (SnapshotKt.lock) {
                    try {
                        releasePinnedSnapshotsForCloseLocked$runtime_release();
                        SnapshotKt.checkAndOverwriteUnusedRecordsLocked();
                        if (mutableScatterSet != null) {
                            Object[] objArr = mutableScatterSet.elements;
                            long[] jArr = mutableScatterSet.metadata;
                            int length = jArr.length - 2;
                            if (length >= 0) {
                                int i3 = 0;
                                c = 7;
                                j = 128;
                                while (true) {
                                    long j4 = jArr[i3];
                                    j2 = 255;
                                    if ((((~j4) << 7) & j4 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i4 = 8 - ((~(i3 - length)) >>> 31);
                                        for (int i5 = 0; i5 < i4; i5++) {
                                            if ((j4 & 255) < 128) {
                                                SnapshotKt.processForUnusedRecordsLocked((StateObject) objArr[(i3 << 3) + i5]);
                                            }
                                            j4 >>= 8;
                                        }
                                        if (i4 != 8) {
                                            break;
                                        }
                                        if (i3 == length) {
                                            break;
                                        }
                                        i3++;
                                    }
                                }
                            } else {
                                c = 7;
                                j = 128;
                                j2 = 255;
                            }
                        }
                        if (modified$runtime_release != null) {
                            Object[] objArr2 = modified$runtime_release.elements;
                            long[] jArr2 = modified$runtime_release.metadata;
                            int length2 = jArr2.length - 2;
                            if (length2 >= 0) {
                                int i6 = 0;
                                while (true) {
                                    long j5 = jArr2[i6];
                                    if ((((~j5) << c) & j5 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i7 = 8 - ((~(i6 - length2)) >>> 31);
                                        for (int i8 = 0; i8 < i7; i8++) {
                                            if ((j5 & j2) < j) {
                                                SnapshotKt.processForUnusedRecordsLocked((StateObject) objArr2[(i6 << 3) + i8]);
                                            }
                                            j5 >>= 8;
                                        }
                                        if (i7 != 8) {
                                            break;
                                        }
                                        if (i6 == length2) {
                                            break;
                                        }
                                        i6++;
                                    }
                                }
                            }
                        }
                        List list2 = this.merged;
                        if (list2 != null) {
                            int size3 = list2.size();
                            for (int i9 = 0; i9 < size3; i9++) {
                                SnapshotKt.processForUnusedRecordsLocked((StateObject) list2.get(i9));
                            }
                        }
                        this.merged = null;
                        Unit unit2 = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return SnapshotApplyResult.Success.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void closeLocked$runtime_release() {
        SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.clear(getSnapshotId()).andNot(this.previousIds);
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void dispose() {
        if (this.disposed) {
            return;
        }
        super.dispose();
        nestedDeactivated$runtime_release();
    }

    public MutableScatterSet getModified$runtime_release() {
        return this.modified;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    /* renamed from: getReadObserver$runtime_release, reason: merged with bridge method [inline-methods] */
    public Function1 getReadObserver() {
        return this.readObserver;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public boolean getReadOnly() {
        return false;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public int getWriteCount$runtime_release() {
        return this.writeCount;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public Function1 getWriteObserver$runtime_release() {
        return this.writeObserver;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x011e A[PHI: r11 r17 r24 r25
      0x011e: PHI (r11v8 java.util.ArrayList) = 
      (r11v5 java.util.ArrayList)
      (r11v5 java.util.ArrayList)
      (r11v5 java.util.ArrayList)
      (r11v6 java.util.ArrayList)
      (r11v9 java.util.ArrayList)
     binds: [B:19:0x0098, B:22:0x00a0, B:33:0x00ca, B:50:0x011b, B:14:0x0077] A[DONT_GENERATE, DONT_INLINE]
      0x011e: PHI (r17v6 int) = (r17v5 int), (r17v5 int), (r17v5 int), (r17v5 int), (r17v7 int) binds: [B:19:0x0098, B:22:0x00a0, B:33:0x00ca, B:50:0x011b, B:14:0x0077] A[DONT_GENERATE, DONT_INLINE]
      0x011e: PHI (r24v3 java.util.List) = (r24v2 java.util.List), (r24v2 java.util.List), (r24v2 java.util.List), (r24v2 java.util.List), (r24v4 java.util.List) binds: [B:19:0x0098, B:22:0x00a0, B:33:0x00ca, B:50:0x011b, B:14:0x0077] A[DONT_GENERATE, DONT_INLINE]
      0x011e: PHI (r25v7 androidx.compose.runtime.snapshots.SnapshotIdSet) = 
      (r25v5 androidx.compose.runtime.snapshots.SnapshotIdSet)
      (r25v5 androidx.compose.runtime.snapshots.SnapshotIdSet)
      (r25v5 androidx.compose.runtime.snapshots.SnapshotIdSet)
      (r25v5 androidx.compose.runtime.snapshots.SnapshotIdSet)
      (r25v8 androidx.compose.runtime.snapshots.SnapshotIdSet)
     binds: [B:19:0x0098, B:22:0x00a0, B:33:0x00ca, B:50:0x011b, B:14:0x0077] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SnapshotApplyResult innerApplyLocked$runtime_release(long j, MutableScatterSet mutableScatterSet, Map map, SnapshotIdSet snapshotIdSet) throws Throwable {
        ArrayList arrayList;
        List listPlus;
        ArrayList arrayList2;
        Object[] objArr;
        long[] jArr;
        Throwable th;
        SnapshotIdSet snapshotIdSet2;
        int i;
        Object[] objArr2;
        long[] jArr2;
        long j2;
        SnapshotIdSet snapshotIdSet3;
        int i2;
        ArrayList arrayList3;
        List list;
        StateRecord stateRecordMergeRecords;
        SnapshotIdSet snapshotIdSetOr = getInvalid$runtime_release().set(getSnapshotId()).or(this.previousIds);
        Object[] objArr3 = mutableScatterSet.elements;
        long[] jArr3 = mutableScatterSet.metadata;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i3 = 0;
            arrayList2 = null;
            listPlus = null;
            Throwable th2 = null;
            while (true) {
                long j3 = jArr3[i3];
                SnapshotIdSet snapshotIdSet4 = snapshotIdSetOr;
                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8;
                    int i5 = 8 - ((~(i3 - length)) >>> 31);
                    th = th2;
                    int i6 = 0;
                    while (i6 < i5) {
                        if ((j3 & 255) < 128) {
                            i = i4;
                            StateObject stateObject = (StateObject) objArr3[(i3 << 3) + i6];
                            objArr2 = objArr3;
                            StateRecord firstStateRecord = stateObject.getFirstStateRecord();
                            jArr2 = jArr3;
                            j2 = j3;
                            StateRecord stateRecord = SnapshotKt.readable(firstStateRecord, j, snapshotIdSet);
                            if (stateRecord == null) {
                                arrayList3 = arrayList2;
                                list = listPlus;
                                snapshotIdSet3 = snapshotIdSet4;
                            } else {
                                arrayList3 = arrayList2;
                                list = listPlus;
                                SnapshotIdSet snapshotIdSet5 = snapshotIdSet4;
                                StateRecord stateRecord2 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), snapshotIdSet5);
                                if (stateRecord2 == null) {
                                    snapshotIdSet3 = snapshotIdSet5;
                                } else {
                                    i2 = i6;
                                    snapshotIdSet3 = snapshotIdSet5;
                                    if (stateRecord2.snapshotId != 1 && !stateRecord.equals(stateRecord2)) {
                                        StateRecord stateRecord3 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), getInvalid$runtime_release());
                                        if (stateRecord3 == null) {
                                            SnapshotKt.readError();
                                            throw th;
                                        }
                                        if (map == null || (stateRecordMergeRecords = (StateRecord) map.get(stateRecord)) == null) {
                                            stateRecordMergeRecords = stateObject.mergeRecords(stateRecord2, stateRecord, stateRecord3);
                                        }
                                        if (stateRecordMergeRecords == null) {
                                            return new SnapshotApplyResult.Failure(this);
                                        }
                                        if (stateRecordMergeRecords.equals(stateRecord3)) {
                                            arrayList2 = arrayList3;
                                            listPlus = list;
                                        } else if (stateRecordMergeRecords.equals(stateRecord)) {
                                            arrayList2 = arrayList3 == null ? new ArrayList() : arrayList3;
                                            arrayList2.add(new Pair(stateObject, stateRecord.create(getSnapshotId())));
                                            listPlus = list == null ? new ArrayList() : list;
                                            listPlus.add(stateObject);
                                        } else {
                                            if (arrayList3 == null) {
                                                arrayList3 = new ArrayList();
                                            }
                                            arrayList3.add(!stateRecordMergeRecords.equals(stateRecord2) ? new Pair(stateObject, stateRecordMergeRecords) : new Pair(stateObject, stateRecord2.create(getSnapshotId())));
                                            arrayList2 = arrayList3;
                                            listPlus = list;
                                        }
                                    }
                                }
                            }
                            i2 = i6;
                            arrayList2 = arrayList3;
                            listPlus = list;
                        } else {
                            i = i4;
                            objArr2 = objArr3;
                            jArr2 = jArr3;
                            j2 = j3;
                            snapshotIdSet3 = snapshotIdSet4;
                            i2 = i6;
                        }
                        j3 = j2 >> i;
                        i6 = i2 + 1;
                        objArr3 = objArr2;
                        i4 = i;
                        jArr3 = jArr2;
                        snapshotIdSet4 = snapshotIdSet3;
                    }
                    objArr = objArr3;
                    jArr = jArr3;
                    snapshotIdSet2 = snapshotIdSet4;
                    if (i5 != i4) {
                        break;
                    }
                } else {
                    objArr = objArr3;
                    jArr = jArr3;
                    th = th2;
                    snapshotIdSet2 = snapshotIdSet4;
                }
                if (i3 == length) {
                    arrayList = arrayList2;
                    break;
                }
                i3++;
                th2 = th;
                objArr3 = objArr;
                jArr3 = jArr;
                snapshotIdSetOr = snapshotIdSet2;
            }
        } else {
            arrayList = null;
            listPlus = null;
        }
        arrayList2 = arrayList;
        if (arrayList2 != null) {
            advance$runtime_release();
            int size = arrayList2.size();
            for (int i7 = 0; i7 < size; i7++) {
                Pair pair = (Pair) arrayList2.get(i7);
                StateObject stateObject2 = (StateObject) pair.component1();
                StateRecord stateRecord4 = (StateRecord) pair.component2();
                stateRecord4.snapshotId = j;
                synchronized (SnapshotKt.lock) {
                    stateRecord4.next = stateObject2.getFirstStateRecord();
                    stateObject2.prependStateRecord(stateRecord4);
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        if (listPlus != null) {
            int size2 = listPlus.size();
            for (int i8 = 0; i8 < size2; i8++) {
                mutableScatterSet.remove((StateObject) listPlus.get(i8));
            }
            List list2 = this.merged;
            if (list2 != null) {
                listPlus = CollectionsKt___CollectionsKt.plus((Iterable) listPlus, (Collection) list2);
            }
            this.merged = listPlus;
        }
        return SnapshotApplyResult.Success.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void nestedActivated$runtime_release() {
        this.snapshots++;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x008c  */
    @Override // androidx.compose.runtime.snapshots.Snapshot
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void nestedDeactivated$runtime_release() {
        if (this.snapshots <= 0) {
            PreconditionsKt.throwIllegalArgumentException("no pending nested snapshots");
        }
        int i = this.snapshots - 1;
        this.snapshots = i;
        if (i != 0 || this.applied) {
            return;
        }
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        if (modified$runtime_release != null) {
            if (this.applied) {
                PreconditionsKt.throwIllegalStateException("Unsupported operation on a snapshot that has been applied");
            }
            setModified(null);
            long snapshotId = getSnapshotId();
            Object[] objArr = modified$runtime_release.elements;
            long[] jArr = modified$runtime_release.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i2 = 0;
                while (true) {
                    long j = jArr[i2];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i3 = 8 - ((~(i2 - length)) >>> 31);
                        for (int i4 = 0; i4 < i3; i4++) {
                            if ((255 & j) < 128) {
                                for (StateRecord firstStateRecord = ((StateObject) objArr[(i2 << 3) + i4]).getFirstStateRecord(); firstStateRecord != null; firstStateRecord = firstStateRecord.next) {
                                    long j2 = firstStateRecord.snapshotId;
                                    if (j2 == snapshotId || CollectionsKt___CollectionsKt.contains(this.previousIds, Long.valueOf(j2))) {
                                        Function1 function1 = SnapshotKt.emptyLambda;
                                        firstStateRecord.snapshotId = 0L;
                                    }
                                }
                            }
                            j >>= 8;
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
            }
        }
        closeAndReleasePinning$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void notifyObjectsInitialized$runtime_release() {
        if (this.applied || this.disposed) {
            return;
        }
        advance$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void recordModified$runtime_release(StateObject stateObject) {
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        if (modified$runtime_release == null) {
            modified$runtime_release = ScatterSetKt.mutableScatterSetOf();
            setModified(modified$runtime_release);
        }
        modified$runtime_release.add(stateObject);
    }

    public final void recordPrevious$runtime_release(long j) {
        synchronized (SnapshotKt.lock) {
            this.previousIds = this.previousIds.set(j);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void releasePinnedSnapshotsForCloseLocked$runtime_release() {
        int length = this.previousPinnedSnapshots.length;
        for (int i = 0; i < length; i++) {
            SnapshotKt.releasePinningLocked(this.previousPinnedSnapshots[i]);
        }
        releasePinnedSnapshotLocked$runtime_release();
    }

    public void setModified(MutableScatterSet mutableScatterSet) {
        this.modified = mutableScatterSet;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void setWriteCount$runtime_release(int i) {
        this.writeCount = i;
    }

    public MutableSnapshot takeNestedMutableSnapshot(Function1 function1, Function1 function12) {
        NestedMutableSnapshot nestedMutableSnapshot;
        if (this.disposed) {
            PreconditionsKt.throwIllegalArgumentException("Cannot use a disposed snapshot");
        }
        if (this.applied && this.pinningTrackingHandle < 0) {
            PreconditionsKt.throwIllegalStateException("Unsupported operation on a disposed or applied snapshot");
        }
        recordPrevious$runtime_release(getSnapshotId());
        Object obj = SnapshotKt.lock;
        synchronized (obj) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId = j + 1;
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(j);
            SnapshotIdSet invalid$runtime_release = getInvalid$runtime_release();
            setInvalid$runtime_release(invalid$runtime_release.set(j));
            nestedMutableSnapshot = new NestedMutableSnapshot(j, SnapshotKt.addRange(invalid$runtime_release, getSnapshotId() + 1, j), SnapshotKt.mergedReadObserver(function1, true, getReadObserver()), SnapshotKt.access$mergedWriteObserver(function12, getWriteObserver$runtime_release()), this);
        }
        if (this.applied || this.disposed) {
            return nestedMutableSnapshot;
        }
        long snapshotId = getSnapshotId();
        synchronized (obj) {
            long j2 = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId = j2 + 1;
            setSnapshotId$runtime_release(j2);
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
            Unit unit = Unit.INSTANCE;
        }
        setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, getSnapshotId()));
        return nestedMutableSnapshot;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public Snapshot takeNestedSnapshot(Function1 function1) {
        NestedReadonlySnapshot nestedReadonlySnapshot;
        if (this.disposed) {
            PreconditionsKt.throwIllegalArgumentException("Cannot use a disposed snapshot");
        }
        if (this.applied && this.pinningTrackingHandle < 0) {
            PreconditionsKt.throwIllegalStateException("Unsupported operation on a disposed or applied snapshot");
        }
        long snapshotId = getSnapshotId();
        boolean z = this instanceof GlobalSnapshot;
        recordPrevious$runtime_release(getSnapshotId());
        Object obj = SnapshotKt.lock;
        synchronized (obj) {
            long j = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId = j + 1;
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(j);
            nestedReadonlySnapshot = new NestedReadonlySnapshot(j, SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId + 1, j), SnapshotKt.mergedReadObserver(function1, true, getReadObserver()), this);
        }
        if (this.applied || this.disposed) {
            return nestedReadonlySnapshot;
        }
        long snapshotId2 = getSnapshotId();
        synchronized (obj) {
            long j2 = SnapshotKt.nextSnapshotId;
            SnapshotKt.nextSnapshotId = j2 + 1;
            setSnapshotId$runtime_release(j2);
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(getSnapshotId());
            Unit unit = Unit.INSTANCE;
        }
        setInvalid$runtime_release(SnapshotKt.addRange(getInvalid$runtime_release(), snapshotId2 + 1, getSnapshotId()));
        return nestedReadonlySnapshot;
    }
}
