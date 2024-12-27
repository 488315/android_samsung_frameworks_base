package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SensitiveContentCoordinatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(List<? extends ListEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(ListEntry listEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(listEntry, null));
    }
}
