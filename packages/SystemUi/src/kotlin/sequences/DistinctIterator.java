package kotlin.sequences;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
            if (this.observed.add(this.keySelector.invoke(next))) {
                setNext(next);
                return;
            }
        }
        done();
    }
}
