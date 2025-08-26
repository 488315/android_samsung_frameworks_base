package kotlin.sequences;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class DistinctIterator extends AbstractIterator {
    public final Function1 keySelector;
    public final HashSet observed = new HashSet();
    public final Iterator source;

    public DistinctIterator(Iterator<Object> it, Function1 function1) {
        this.source = it;
        this.keySelector = function1;
    }

    @Override // kotlin.collections.AbstractIterator
    public final void computeNext() {
        while (this.source.hasNext()) {
            Object next = this.source.next();
            if (this.observed.add(this.keySelector.mo781invoke(next))) {
                this.nextValue = next;
                this.state = 1;
                return;
            }
        }
        this.state = 2;
    }
}
