package kotlin.sequences;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            if (this.observed.add(this.keySelector.mo779invoke(next))) {
                this.nextValue = next;
                this.state = 1;
                return;
            }
        }
        this.state = 2;
    }
}
