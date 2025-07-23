package androidx.compose.runtime;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SlotTableGroup implements Iterable<Object>, KMappedMarker {
    public final int group;
    public final SlotTable table;
    public final int version;

    public SlotTableGroup(SlotTable slotTable, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(slotTable, i, (i3 & 4) != 0 ? slotTable.version : i2);
    }

    @Override // java.lang.Iterable
    public final Iterator<Object> iterator() {
        if (this.table.version != this.version) {
            SlotTableKt.throwConcurrentModificationException();
        }
        GroupSourceInformation sourceInformationOf = this.table.sourceInformationOf(this.group);
        if (sourceInformationOf != null) {
            SlotTable slotTable = this.table;
            int i = this.group;
            return new SourceInformationGroupIterator(slotTable, i, sourceInformationOf, new AnchoredGroupPath(i));
        }
        SlotTable slotTable2 = this.table;
        int i2 = this.group;
        return new GroupIterator(slotTable2, i2 + 1, SlotTableKt.access$groupSize(i2, slotTable2.groups) + i2);
    }

    public SlotTableGroup(SlotTable slotTable, int i, int i2) {
        this.table = slotTable;
        this.group = i;
        this.version = i2;
    }
}
