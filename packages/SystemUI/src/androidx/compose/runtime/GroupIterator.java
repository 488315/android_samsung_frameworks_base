package androidx.compose.runtime;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GroupIterator implements Iterator<Object>, KMappedMarker {
    public final int end;
    public int index;
    public final SlotTable table;
    public final int version;

    public GroupIterator(SlotTable slotTable, int i, int i2) {
        this.table = slotTable;
        this.end = i2;
        this.index = i;
        this.version = slotTable.version;
        if (slotTable.writer) {
            SlotTableKt.throwConcurrentModificationException();
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.end;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.table.version != this.version) {
            SlotTableKt.throwConcurrentModificationException();
        }
        int i = this.index;
        this.index = SlotTableKt.access$groupSize(i, this.table.groups) + i;
        return new SlotTableGroup(this.table, i, this.version);
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
