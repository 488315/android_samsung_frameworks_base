package androidx.compose.runtime.snapshots;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class StateListIterator<T> implements ListIterator<T>, KMappedMarker {
    public int index;
    public int lastRequested = -1;
    public final SnapshotStateList list;
    public int structure;

    public StateListIterator(SnapshotStateList<T> snapshotStateList, int i) {
        this.list = snapshotStateList;
        this.index = i - 1;
        this.structure = snapshotStateList.getStructure$runtime_release();
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        validateModification();
        this.list.add(this.index + 1, obj);
        this.lastRequested = -1;
        this.index++;
        this.structure = this.list.getStructure$runtime_release();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.list.size() - 1;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.index >= 0;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        validateModification();
        int i = this.index + 1;
        this.lastRequested = i;
        SnapshotStateListKt.access$validateRange(i, this.list.size());
        Object obj = this.list.get(i);
        this.index = i;
        return obj;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.index + 1;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        validateModification();
        SnapshotStateListKt.access$validateRange(this.index, this.list.size());
        int i = this.index;
        this.lastRequested = i;
        this.index--;
        return this.list.get(i);
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.index;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        validateModification();
        this.list.remove(this.index);
        this.index--;
        this.lastRequested = -1;
        this.structure = this.list.getStructure$runtime_release();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        validateModification();
        int i = this.lastRequested;
        if (i < 0) {
            throw new IllegalStateException("Cannot call set before the first call to next() or previous() or immediately after a call to add() or remove()");
        }
        this.list.set(i, obj);
        this.structure = this.list.getStructure$runtime_release();
    }

    public final void validateModification() {
        if (this.list.getStructure$runtime_release() != this.structure) {
            throw new ConcurrentModificationException();
        }
    }
}
