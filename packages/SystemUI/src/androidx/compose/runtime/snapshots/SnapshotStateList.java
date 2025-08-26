package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.AbstractPersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.SmallPersistentVector;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableList;

/* loaded from: classes.dex */
public final class SnapshotStateList<T> implements StateObject, List<T>, RandomAccess, KMutableList {
    public StateListStateRecord firstStateRecord;

    public final class StateListStateRecord<T> extends StateRecord {
        public PersistentList list;
        public int modification;
        public int structuralChange;

        public StateListStateRecord(long j, PersistentList<? extends T> persistentList) {
            super(j);
            this.list = persistentList;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            synchronized (SnapshotStateListKt.sync) {
                this.list = ((StateListStateRecord) stateRecord).list;
                this.modification = ((StateListStateRecord) stateRecord).modification;
                this.structuralChange = ((StateListStateRecord) stateRecord).structuralChange;
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return create(SnapshotKt.currentSnapshot().getSnapshotId());
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new StateListStateRecord(j, this.list);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SnapshotStateList() {
        this(SmallPersistentVector.EMPTY);
        SmallPersistentVector.Companion.getClass();
    }

    public static boolean attemptUpdate(StateListStateRecord stateListStateRecord, int i, PersistentList persistentList, boolean z) {
        boolean z2;
        synchronized (SnapshotStateListKt.sync) {
            try {
                int i2 = stateListStateRecord.modification;
                if (i2 == i) {
                    stateListStateRecord.list = persistentList;
                    z2 = true;
                    if (z) {
                        stateListStateRecord.structuralChange++;
                    }
                    stateListStateRecord.modification = i2 + 1;
                } else {
                    z2 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentListAdd = persistentList.add(obj);
            if (persistentListAdd.equals(persistentList)) {
                return false;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i, persistentListAdd, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return true;
    }

    @Override // java.util.List
    public final boolean addAll(final int i, final Collection collection) {
        return mutateBoolean(new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotStateList.addAll.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(((List) obj).addAll(i, collection));
            }
        });
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        Snapshot snapshotCurrentSnapshot;
        StateListStateRecord stateListStateRecord = this.firstStateRecord;
        synchronized (SnapshotKt.lock) {
            Snapshot.Companion.getClass();
            snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
            StateListStateRecord stateListStateRecord2 = (StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord, this, snapshotCurrentSnapshot);
            synchronized (SnapshotStateListKt.sync) {
                SmallPersistentVector.Companion.getClass();
                stateListStateRecord2.list = SmallPersistentVector.EMPTY;
                stateListStateRecord2.modification++;
                stateListStateRecord2.structuralChange++;
            }
        }
        SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return getReadable$runtime_release().list.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return getReadable$runtime_release().list.containsAll(collection);
    }

    @Override // java.util.List
    public final Object get(int i) {
        return getReadable$runtime_release().list.get(i);
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.firstStateRecord;
    }

    public final StateListStateRecord getReadable$runtime_release() {
        return (StateListStateRecord) SnapshotKt.readable(this.firstStateRecord, this);
    }

    public final int getStructure$runtime_release() {
        return ((StateListStateRecord) SnapshotKt.current(this.firstStateRecord)).structuralChange;
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return getReadable$runtime_release().list.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return getReadable$runtime_release().list.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return getReadable$runtime_release().list.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new StateListIterator(this, 0);
    }

    public final boolean mutateBoolean(Function1 function1) {
        int i;
        PersistentList persistentList;
        Object objMo781invoke;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentVectorBuilder persistentVectorBuilderBuilder = persistentList.builder();
            objMo781invoke = function1.mo781invoke(persistentVectorBuilderBuilder);
            PersistentList persistentListBuild = persistentVectorBuilderBuilder.build();
            if (Intrinsics.areEqual(persistentListBuild, persistentList)) {
                break;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i, persistentListBuild, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return ((Boolean) objMo781invoke).booleanValue();
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        stateRecord.next = this.firstStateRecord;
        this.firstStateRecord = (StateListStateRecord) stateRecord;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        int i2;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        Object obj = get(i);
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i2 = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentListRemoveAt = persistentList.removeAt(i);
            if (Intrinsics.areEqual(persistentListRemoveAt, persistentList)) {
                break;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i2, persistentListRemoveAt, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return obj;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentListRemoveAll = ((AbstractPersistentList) persistentList).removeAll(collection);
            if (Intrinsics.areEqual(persistentListRemoveAll, persistentList)) {
                return false;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i, persistentListRemoveAll, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(final Collection collection) {
        return mutateBoolean(new Function1() { // from class: androidx.compose.runtime.snapshots.SnapshotStateList.retainAll.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(((List) obj).retainAll(collection));
            }
        });
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        int i2;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        Object obj2 = get(i);
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i2 = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentList2 = persistentList.set(i, obj);
            if (persistentList2.equals(persistentList)) {
                break;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i2, persistentList2, false);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return obj2;
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return getReadable$runtime_release().list.size();
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        if (!(i >= 0 && i <= i2 && i2 <= size())) {
            PreconditionsKt.throwIllegalArgumentException("fromIndex or toIndex are out of bounds");
        }
        return new SubList(this, i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public final String toString() {
        return "SnapshotStateList(value=" + ((StateListStateRecord) SnapshotKt.current(this.firstStateRecord)).list + ")@" + hashCode();
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentListAddAll = persistentList.addAll(collection);
            if (Intrinsics.areEqual(persistentListAddAll, persistentList)) {
                return false;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i, persistentListAddAll, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return true;
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new StateListIterator(this, i);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }

    public SnapshotStateList(PersistentList<? extends T> persistentList) {
        Snapshot snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
        StateListStateRecord stateListStateRecord = new StateListStateRecord(snapshotCurrentSnapshot.getSnapshotId(), persistentList);
        if (!(snapshotCurrentSnapshot instanceof GlobalSnapshot)) {
            stateListStateRecord.next = new StateListStateRecord(1, persistentList);
        }
        this.firstStateRecord = stateListStateRecord;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        int i2;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i2 = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentList persistentListAdd = persistentList.add(i, obj);
            if (persistentListAdd.equals(persistentList)) {
                return;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i2, persistentListAdd, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        do {
            synchronized (SnapshotStateListKt.sync) {
                StateListStateRecord stateListStateRecord = (StateListStateRecord) SnapshotKt.current(this.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            AbstractPersistentList abstractPersistentList = (AbstractPersistentList) persistentList;
            int iIndexOf = abstractPersistentList.indexOf(obj);
            PersistentList persistentListRemoveAt = abstractPersistentList;
            if (iIndexOf != -1) {
                persistentListRemoveAt = abstractPersistentList.removeAt(iIndexOf);
            }
            if (Intrinsics.areEqual(persistentListRemoveAt, persistentList)) {
                return false;
            }
            StateListStateRecord stateListStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = attemptUpdate((StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, this, snapshotCurrentSnapshot), i, persistentListRemoveAt, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, this);
        } while (!zAttemptUpdate);
        return true;
    }
}
