package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class SensitiveContentCoordinatorKt {
    public static final Sequence extractAllRepresentativeEntries(List<? extends ListEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1.INSTANCE);
    }

    public static final Sequence extractAllRepresentativeEntries(ListEntry listEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(listEntry, null));
    }
}
