package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SensitiveContentCoordinatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(List<? extends PipelineEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(PipelineEntry pipelineEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(pipelineEntry, null));
    }
}
