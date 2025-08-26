package androidx.core.view;

import android.view.View;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt__SequencesKt;

/* loaded from: classes.dex */
public abstract class ViewKt {
    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 getAllViews(View view) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ViewKt$allViews$1(view, null));
    }

    public static final Sequence getAncestors(View view) {
        return SequencesKt__SequencesKt.generateSequence(view.getParent(), ViewKt$ancestors$1.INSTANCE);
    }
}
