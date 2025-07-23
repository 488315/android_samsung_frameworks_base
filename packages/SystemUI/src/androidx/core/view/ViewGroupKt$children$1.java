package androidx.core.view;

import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewGroupKt$children$1 implements Sequence {
    public final /* synthetic */ ViewGroup $this_children;

    public ViewGroupKt$children$1(ViewGroup viewGroup) {
        this.$this_children = viewGroup;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new ViewGroupKt$iterator$1(this.$this_children);
    }
}
