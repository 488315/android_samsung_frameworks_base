package androidx.navigation;

import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavGraph$iterator$1 implements Iterator, KMappedMarker {
    public int index = -1;
    public final /* synthetic */ NavGraph this$0;
    public boolean wentToNext;

    public NavGraph$iterator$1(NavGraph navGraph) {
        this.this$0 = navGraph;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index + 1 < this.this$0.nodes.size();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.wentToNext = true;
        SparseArrayCompat sparseArrayCompat = this.this$0.nodes;
        int i = this.index + 1;
        this.index = i;
        return (NavDestination) sparseArrayCompat.valueAt(i);
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (!this.wentToNext) {
            throw new IllegalStateException("You must call next() before you can remove an element");
        }
        SparseArrayCompat sparseArrayCompat = this.this$0.nodes;
        ((NavDestination) sparseArrayCompat.valueAt(this.index)).parent = null;
        int i = this.index;
        Object[] objArr = sparseArrayCompat.values;
        Object obj = objArr[i];
        Object obj2 = SparseArrayCompatKt.DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            sparseArrayCompat.garbage = true;
        }
        this.index = i - 1;
        this.wentToNext = false;
    }
}
