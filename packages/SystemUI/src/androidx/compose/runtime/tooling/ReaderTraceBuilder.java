package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import androidx.compose.runtime.SlotReader;
import androidx.compose.runtime.SlotTable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ReaderTraceBuilder extends ComposeStackTraceBuilder {
    public final SlotReader reader;

    public ReaderTraceBuilder(SlotReader slotReader) {
        this.reader = slotReader;
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public final int groupKeyOf(Anchor anchor) {
        SlotReader slotReader = this.reader;
        return slotReader.groupKey(slotReader.table.anchorIndex(anchor));
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public final GroupSourceInformation sourceInformationOf(Anchor anchor) {
        SlotTable slotTable = this.reader.table;
        return slotTable.sourceInformationOf(slotTable.anchorIndex(anchor));
    }
}
