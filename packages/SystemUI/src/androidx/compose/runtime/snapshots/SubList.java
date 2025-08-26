package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
final class SubList<T> implements List<T>, KMutableList {
    public final int offset;
    public final SnapshotStateList parentList;
    public int size;
    public int structure;

    /* renamed from: androidx.compose.runtime.snapshots.SubList$listIterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements ListIterator<Object>, KMappedMarker {
        public final /* synthetic */ Ref$IntRef $current;
        public final /* synthetic */ SubList this$0;

        public AnonymousClass1(Ref$IntRef ref$IntRef, SubList<Object> subList) {
            this.$current = ref$IntRef;
            this.this$0 = subList;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            throw new IllegalStateException("Cannot modify a state list through an iterator");
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this.$current.element < this.this$0.size - 1;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.$current.element >= 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final Object next() {
            int i = this.$current.element + 1;
            SnapshotStateListKt.access$validateRange(i, this.this$0.size);
            this.$current.element = i;
            return this.this$0.get(i);
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.$current.element + 1;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            int i = this.$current.element;
            SnapshotStateListKt.access$validateRange(i, this.this$0.size);
            this.$current.element = i - 1;
            return this.this$0.get(i);
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.$current.element;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            throw new IllegalStateException("Cannot modify a state list through an iterator");
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            throw new IllegalStateException("Cannot modify a state list through an iterator");
        }
    }

    public SubList(SnapshotStateList<T> snapshotStateList, int i, int i2) {
        this.parentList = snapshotStateList;
        this.offset = i;
        this.structure = snapshotStateList.getStructure$runtime_release();
        this.size = i2 - i;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean add(Object obj) {
        validateModification$1();
        this.parentList.add(this.offset + this.size, obj);
        this.size++;
        this.structure = this.parentList.getStructure$runtime_release();
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        return addAll(this.size, collection);
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        if (this.size > 0) {
            validateModification$1();
            SnapshotStateList snapshotStateList = this.parentList;
            int i2 = this.offset;
            int i3 = this.size + i2;
            snapshotStateList.getClass();
            do {
                synchronized (SnapshotStateListKt.sync) {
                    SnapshotStateList.StateListStateRecord stateListStateRecord = (SnapshotStateList.StateListStateRecord) SnapshotKt.current(snapshotStateList.firstStateRecord);
                    i = stateListStateRecord.modification;
                    persistentList = stateListStateRecord.list;
                    Unit unit = Unit.INSTANCE;
                }
                persistentList.getClass();
                PersistentVectorBuilder persistentVectorBuilderBuilder = persistentList.builder();
                persistentVectorBuilderBuilder.subList(i2, i3).clear();
                PersistentList persistentListBuild = persistentVectorBuilderBuilder.build();
                if (Intrinsics.areEqual(persistentListBuild, persistentList)) {
                    break;
                }
                SnapshotStateList.StateListStateRecord stateListStateRecord2 = snapshotStateList.firstStateRecord;
                synchronized (SnapshotKt.lock) {
                    Snapshot.Companion.getClass();
                    snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                    zAttemptUpdate = SnapshotStateList.attemptUpdate((SnapshotStateList.StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, snapshotStateList, snapshotCurrentSnapshot), i, persistentListBuild, true);
                }
                SnapshotKt.notifyWrite(snapshotCurrentSnapshot, snapshotStateList);
            } while (!zAttemptUpdate);
            this.size = 0;
            this.structure = this.parentList.getStructure$runtime_release();
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        Collection collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        validateModification$1();
        SnapshotStateListKt.access$validateRange(i, this.size);
        return this.parentList.get(this.offset + i);
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        validateModification$1();
        int i = this.offset;
        Iterator<T> it = RangesKt___RangesKt.until(i, this.size + i).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            if (Intrinsics.areEqual(obj, this.parentList.get(iNextInt))) {
                return iNextInt - this.offset;
            }
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        validateModification$1();
        int i = this.offset + this.size;
        do {
            i--;
            if (i < this.offset) {
                return -1;
            }
        } while (!Intrinsics.areEqual(obj, this.parentList.get(i)));
        return i - this.offset;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        while (true) {
            boolean z = false;
            while (it.hasNext()) {
                if (remove(it.next()) || z) {
                    z = true;
                }
            }
            return z;
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        int i;
        PersistentList persistentList;
        Snapshot snapshotCurrentSnapshot;
        boolean zAttemptUpdate;
        validateModification$1();
        SnapshotStateList snapshotStateList = this.parentList;
        int i2 = this.offset;
        int i3 = this.size + i2;
        int size = snapshotStateList.size();
        do {
            synchronized (SnapshotStateListKt.sync) {
                SnapshotStateList.StateListStateRecord stateListStateRecord = (SnapshotStateList.StateListStateRecord) SnapshotKt.current(snapshotStateList.firstStateRecord);
                i = stateListStateRecord.modification;
                persistentList = stateListStateRecord.list;
                Unit unit = Unit.INSTANCE;
            }
            persistentList.getClass();
            PersistentVectorBuilder persistentVectorBuilderBuilder = persistentList.builder();
            persistentVectorBuilderBuilder.subList(i2, i3).retainAll(collection);
            PersistentList persistentListBuild = persistentVectorBuilderBuilder.build();
            if (Intrinsics.areEqual(persistentListBuild, persistentList)) {
                break;
            }
            SnapshotStateList.StateListStateRecord stateListStateRecord2 = snapshotStateList.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                snapshotCurrentSnapshot = SnapshotKt.currentSnapshot();
                zAttemptUpdate = SnapshotStateList.attemptUpdate((SnapshotStateList.StateListStateRecord) SnapshotKt.writableRecord(stateListStateRecord2, snapshotStateList, snapshotCurrentSnapshot), i, persistentListBuild, true);
            }
            SnapshotKt.notifyWrite(snapshotCurrentSnapshot, snapshotStateList);
        } while (!zAttemptUpdate);
        int size2 = size - snapshotStateList.size();
        if (size2 > 0) {
            this.structure = this.parentList.getStructure$runtime_release();
            this.size -= size2;
        }
        return size2 > 0;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        SnapshotStateListKt.access$validateRange(i, this.size);
        validateModification$1();
        Object obj2 = this.parentList.set(i + this.offset, obj);
        this.structure = this.parentList.getStructure$runtime_release();
        return obj2;
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.size;
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        if (!(i >= 0 && i <= i2 && i2 <= this.size)) {
            PreconditionsKt.throwIllegalArgumentException("fromIndex or toIndex are out of bounds");
        }
        validateModification$1();
        SnapshotStateList snapshotStateList = this.parentList;
        int i3 = this.offset;
        return new SubList(snapshotStateList, i + i3, i2 + i3);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public final void validateModification$1() {
        if (this.parentList.getStructure$runtime_release() != this.structure) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        validateModification$1();
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = i - 1;
        return new AnonymousClass1(ref$IntRef, this);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        validateModification$1();
        boolean zAddAll = this.parentList.addAll(i + this.offset, collection);
        if (zAddAll) {
            this.size = collection.size() + this.size;
            this.structure = this.parentList.getStructure$runtime_release();
        }
        return zAddAll;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        validateModification$1();
        Object objRemove = this.parentList.remove(this.offset + i);
        this.size--;
        this.structure = this.parentList.getStructure$runtime_release();
        return objRemove;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        validateModification$1();
        this.parentList.add(this.offset + i, obj);
        this.size++;
        this.structure = this.parentList.getStructure$runtime_release();
    }
}
