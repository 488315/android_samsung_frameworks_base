package androidx.compose.runtime;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

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
        GroupSourceInformation groupSourceInformationSourceInformationOf = this.table.sourceInformationOf(this.group);
        if (groupSourceInformationSourceInformationOf != null) {
            SlotTable slotTable = this.table;
            int i = this.group;
            return new SourceInformationGroupIterator(slotTable, i, groupSourceInformationSourceInformationOf, new AnchoredGroupPath(i));
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
