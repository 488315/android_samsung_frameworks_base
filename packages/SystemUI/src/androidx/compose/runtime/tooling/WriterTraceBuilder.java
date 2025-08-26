package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import androidx.compose.runtime.SlotWriter;

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
