package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
