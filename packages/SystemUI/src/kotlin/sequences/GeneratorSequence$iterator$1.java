package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GeneratorSequence$iterator$1 implements Iterator, KMappedMarker {
    public Object nextItem;
    public int nextState = -2;
    public final /* synthetic */ GeneratorSequence this$0;

    public GeneratorSequence$iterator$1(GeneratorSequence generatorSequence) {
        this.this$0 = generatorSequence;
    }

    public final void calcNext$1() {
        Object mo779invoke;
        if (this.nextState == -2) {
            mo779invoke = this.this$0.getInitialValue.invoke();
        } else {
            Function1 function1 = this.this$0.getNextValue;
            Object obj = this.nextItem;
            obj.getClass();
            mo779invoke = function1.mo779invoke(obj);
        }
        this.nextItem = mo779invoke;
        this.nextState = mo779invoke == null ? 0 : 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.nextState < 0) {
            calcNext$1();
        }
        return this.nextState == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.nextState < 0) {
            calcNext$1();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        Object obj = this.nextItem;
        this.nextState = -1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
