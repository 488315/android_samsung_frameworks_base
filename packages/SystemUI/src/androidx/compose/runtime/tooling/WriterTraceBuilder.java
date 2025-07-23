package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import androidx.compose.runtime.SlotWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WriterTraceBuilder extends ComposeStackTraceBuilder {
    public final SlotWriter writer;

    public WriterTraceBuilder(SlotWriter slotWriter) {
        this.writer = slotWriter;
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public final int groupKeyOf(Anchor anchor) {
        SlotWriter slotWriter = this.writer;
        return slotWriter.groups[slotWriter.groupIndexToAddress(slotWriter.anchorIndex(anchor)) * 5];
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public final GroupSourceInformation sourceInformationOf(Anchor anchor) {
        SlotWriter slotWriter = this.writer;
        return slotWriter.sourceInformationOf$runtime_release(slotWriter.anchorIndex(anchor));
    }
}
