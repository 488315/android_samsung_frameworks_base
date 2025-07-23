package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b4 A[LOOP:1: B:32:0x00b2->B:33:0x00b4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x011b A[Catch: all -> 0x0108, TryCatch #1 {all -> 0x0108, blocks: (B:38:0x00c2, B:40:0x00d1, B:43:0x00df, B:45:0x00ec, B:47:0x00f6, B:49:0x00fc, B:51:0x010a, B:57:0x011b, B:60:0x0125, B:62:0x0130, B:64:0x013a, B:66:0x0140, B:68:0x014a, B:74:0x0151, B:76:0x0153, B:78:0x0157, B:80:0x015e, B:82:0x0169, B:88:0x0111), top: B:37:0x00c2 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0157 A[Catch: all -> 0x0108, TryCatch #1 {all -> 0x0108, blocks: (B:38:0x00c2, B:40:0x00d1, B:43:0x00df, B:45:0x00ec, B:47:0x00f6, B:49:0x00fc, B:51:0x010a, B:57:0x011b, B:60:0x0125, B:62:0x0130, B:64:0x013a, B:66:0x0140, B:68:0x014a, B:74:0x0151, B:76:0x0153, B:78:0x0157, B:80:0x015e, B:82:0x0169, B:88:0x0111), top: B:37:0x00c2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.compose.runtime.snapshots.SnapshotApplyResult apply() {
        /*
            Method dump skipped, instructions count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.MutableSnapshot.apply():androidx.compose.runtime.snapshots.SnapshotApplyResult");
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

    public final SnapshotApplyResult innerApplyLocked$runtime_release(long j, MutableScatterSet mutableScatterSet, Map map, SnapshotIdSet snapshotIdSet) {
        ArrayList arrayList;
        List list;
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
        List list2;
        StateRecord mergeRecords;
        SnapshotIdSet or = getInvalid$runtime_release().set(getSnapshotId()).or(this.previousIds);
        Object[] objArr3 = mutableScatterSet.elements;
        long[] jArr3 = mutableScatterSet.metadata;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i3 = 0;
            arrayList2 = null;
            list = null;
            Throwable th2 = null;
            while (true) {
                long j3 = jArr3[i3];
                SnapshotIdSet snapshotIdSet4 = or;
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
                            StateRecord readable = SnapshotKt.readable(firstStateRecord, j, snapshotIdSet);
                            if (readable == null) {
                                arrayList3 = arrayList2;
                                list2 = list;
                                snapshotIdSet3 = snapshotIdSet4;
                            } else {
                                arrayList3 = arrayList2;
                                list2 = list;
                                SnapshotIdSet snapshotIdSet5 = snapshotIdSet4;
                                StateRecord readable2 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), snapshotIdSet5);
                                if (readable2 == null) {
                                    snapshotIdSet3 = snapshotIdSet5;
                                } else {
                                    i2 = i6;
                                    snapshotIdSet3 = snapshotIdSet5;
                                    if (readable2.snapshotId != 1 && !readable.equals(readable2)) {
                                        StateRecord readable3 = SnapshotKt.readable(firstStateRecord, getSnapshotId(), getInvalid$runtime_release());
                                        if (readable3 == null) {
                                            SnapshotKt.readError();
                                            throw th;
                                        }
                                        if (map == null || (mergeRecords = (StateRecord) map.get(readable)) == null) {
                                            mergeRecords = stateObject.mergeRecords(readable2, readable, readable3);
                                        }
                                        if (mergeRecords == null) {
                                            return new SnapshotApplyResult.Failure(this);
                                        }
                                        if (!mergeRecords.equals(readable3)) {
                                            if (mergeRecords.equals(readable)) {
                                                arrayList2 = arrayList3 == null ? new ArrayList() : arrayList3;
                                                arrayList2.add(new Pair(stateObject, readable.create(getSnapshotId())));
                                                list = list2 == null ? new ArrayList() : list2;
                                                list.add(stateObject);
                                            } else {
                                                if (arrayList3 == null) {
                                                    arrayList3 = new ArrayList();
                                                }
                                                arrayList3.add(!mergeRecords.equals(readable2) ? new Pair(stateObject, mergeRecords) : new Pair(stateObject, readable2.create(getSnapshotId())));
                                            }
                                        }
                                    }
                                    arrayList2 = arrayList3;
                                    list = list2;
                                }
                            }
                            i2 = i6;
                            arrayList2 = arrayList3;
                            list = list2;
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
                or = snapshotIdSet2;
            }
        } else {
            arrayList = null;
            list = null;
        }
        arrayList2 = arrayList;
        if (arrayList2 != null) {
            advance$runtime_release();
            int size = arrayList2.size();
            for (int i7 = 0; i7 < size; i7++) {
                Pair pair = (Pair) arrayList2.get(i7);
                StateObject stateObject2 = (StateObject) pair.component1();
                StateRecord stateRecord = (StateRecord) pair.component2();
                stateRecord.snapshotId = j;
                synchronized (SnapshotKt.lock) {
                    stateRecord.next = stateObject2.getFirstStateRecord();
                    stateObject2.prependStateRecord(stateRecord);
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        if (list != null) {
            int size2 = list.size();
            for (int i8 = 0; i8 < size2; i8++) {
                mutableScatterSet.remove((StateObject) list.get(i8));
            }
            List list3 = this.merged;
            if (list3 != null) {
                list = CollectionsKt___CollectionsKt.plus((Iterable) list, (Collection) list3);
            }
            this.merged = list;
        }
        return SnapshotApplyResult.Success.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public void nestedActivated$runtime_release() {
        this.snapshots++;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
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
                        }
                    }
                    if (i2 == length) {
                        break;
                    } else {
                        i2++;
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
