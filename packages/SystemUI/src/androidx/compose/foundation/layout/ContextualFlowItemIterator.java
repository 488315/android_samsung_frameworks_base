package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.Measurable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public final class ContextualFlowItemIterator implements Iterator<Measurable>, KMappedMarker {
    public final List _list = new ArrayList();
    public final Function2 getMeasurables;
    public final int itemCount;
    public int itemIndex;
    public int listIndex;

    public ContextualFlowItemIterator(int i, Function2 function2) {
        this.itemCount = i;
        this.getMeasurables = function2;
    }

    public final Measurable getNext$foundation_layout(FlowLineInfo flowLineInfo) {
        if (this.listIndex < ((ArrayList) this._list).size()) {
            Measurable measurable = (Measurable) ((ArrayList) this._list).get(this.listIndex);
            this.listIndex++;
            return measurable;
        }
        int i = this.itemIndex;
        if (i >= this.itemCount) {
            throw new IndexOutOfBoundsException("No item returned at index call. Index: " + this.itemIndex);
        }
        List list = (List) this.getMeasurables.invoke(Integer.valueOf(i), flowLineInfo);
        this.itemIndex++;
        if (list.isEmpty()) {
            return getNext$foundation_layout(new FlowLineInfo(0, 0, 0.0f, 0.0f, 15, null));
        }
        Measurable measurable2 = (Measurable) CollectionsKt___CollectionsKt.first(list);
        ((ArrayList) this._list).addAll(list);
        this.listIndex++;
        return measurable2;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.listIndex < ((ArrayList) this._list).size() || this.itemIndex < this.itemCount;
    }

    @Override // java.util.Iterator
    public final Measurable next() {
        return getNext$foundation_layout(new FlowLineInfo(0, 0, 0.0f, 0.0f, 15, null));
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
