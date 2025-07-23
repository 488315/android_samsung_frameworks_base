package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewGroupKt$iterator$1 implements Iterator, KMappedMarker {
    public final /* synthetic */ ViewGroup $this_iterator;
    public int index;

    public ViewGroupKt$iterator$1(ViewGroup viewGroup) {
        this.$this_iterator = viewGroup;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.$this_iterator.getChildCount();
    }

    @Override // java.util.Iterator
    public final Object next() {
        ViewGroup viewGroup = this.$this_iterator;
        int i = this.index;
        this.index = i + 1;
        View childAt = viewGroup.getChildAt(i);
        if (childAt != null) {
            return childAt;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // java.util.Iterator
    public final void remove() {
        ViewGroup viewGroup = this.$this_iterator;
        int i = this.index - 1;
        this.index = i;
        viewGroup.removeViewAt(i);
    }
}
