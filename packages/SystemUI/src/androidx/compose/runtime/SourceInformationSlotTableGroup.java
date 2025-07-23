package androidx.compose.runtime;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SourceInformationSlotTableGroup implements Iterable<Object>, KMappedMarker {
    public final Iterable compositionGroups;
    public final SourceInformationGroupPath identityPath;
    public final int parent;
    public final GroupSourceInformation sourceInformation;
    public final SlotTable table;

    public SourceInformationSlotTableGroup(SlotTable slotTable, int i, GroupSourceInformation groupSourceInformation, SourceInformationGroupPath sourceInformationGroupPath) {
        this.table = slotTable;
        this.parent = i;
        this.sourceInformation = groupSourceInformation;
        this.identityPath = sourceInformationGroupPath;
        int i2 = groupSourceInformation.key;
        this.compositionGroups = this;
    }

    @Override // java.lang.Iterable
    public final Iterator<Object> iterator() {
        return new SourceInformationGroupIterator(this.table, this.parent, this.sourceInformation, this.identityPath);
    }
}
