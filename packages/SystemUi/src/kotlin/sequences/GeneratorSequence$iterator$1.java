package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class GeneratorSequence$iterator$1 implements Iterator, KMappedMarker {
    public Object nextItem;
    public int nextState = -2;
    public final /* synthetic */ GeneratorSequence this$0;

    public GeneratorSequence$iterator$1(GeneratorSequence generatorSequence) {
        this.this$0 = generatorSequence;
    }

    public final void calcNext() {
        Object invoke;
        if (this.nextState == -2) {
            invoke = this.this$0.getInitialValue.invoke();
        } else {
            Function1 function1 = this.this$0.getNextValue;
            Object obj = this.nextItem;
            Intrinsics.checkNotNull(obj);
            invoke = function1.invoke(obj);
        }
        this.nextItem = invoke;
        this.nextState = invoke == null ? 0 : 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.nextState < 0) {
            calcNext();
        }
        return this.nextState == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.nextState < 0) {
            calcNext();
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
