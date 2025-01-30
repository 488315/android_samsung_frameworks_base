package androidx.core.view;

import android.view.View;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ViewKt {
    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 getAllViews(View view) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ViewKt$allViews$1(view, null));
    }

    public static final Sequence getAncestors(View view) {
        return SequencesKt__SequencesKt.generateSequence(view.getParent(), ViewKt$ancestors$1.INSTANCE);
    }
}
