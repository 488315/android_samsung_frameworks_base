package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class GeneratorSequence implements Sequence {
    public final Function0 getInitialValue;
    public final Function1 getNextValue;

    /* renamed from: kotlin.sequences.GeneratorSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public Object nextItem;
        public int nextState = -2;

        public AnonymousClass1() {
        }

        public final void calcNext$1() {
            Object objMo781invoke;
            if (this.nextState == -2) {
                objMo781invoke = GeneratorSequence.this.getInitialValue.invoke();
            } else {
                Function1 function1 = GeneratorSequence.this.getNextValue;
                Object obj = this.nextItem;
                obj.getClass();
                objMo781invoke = function1.mo781invoke(obj);
            }
            this.nextItem = objMo781invoke;
            this.nextState = objMo781invoke == null ? 0 : 1;
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

    public GeneratorSequence(Function0 function0, Function1 function1) {
        this.getInitialValue = function0;
        this.getNextValue = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
