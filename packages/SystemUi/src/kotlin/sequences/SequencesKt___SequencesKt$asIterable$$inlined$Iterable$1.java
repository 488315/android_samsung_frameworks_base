package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 implements Iterable, KMappedMarker {
    public final /* synthetic */ Sequence $this_asIterable$inlined;

    public SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(Sequence sequence) {
        this.$this_asIterable$inlined = sequence;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.$this_asIterable$inlined.iterator();
    }
}
