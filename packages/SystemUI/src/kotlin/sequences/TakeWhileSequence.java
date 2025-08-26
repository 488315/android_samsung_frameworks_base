package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class TakeWhileSequence implements Sequence {
    public final Function1 predicate;
    public final Sequence sequence;

    /* renamed from: kotlin.sequences.TakeWhileSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public final Iterator iterator;
        public Object nextItem;
        public int nextState = -1;

        public AnonymousClass1() {
            this.iterator = TakeWhileSequence.this.sequence.iterator();
        }

        public final void calcNext$2() {
            if (this.iterator.hasNext()) {
                Object next = this.iterator.next();
                if (((Boolean) TakeWhileSequence.this.predicate.mo781invoke(next)).booleanValue()) {
                    this.nextState = 1;
                    this.nextItem = next;
                    return;
                }
            }
            this.nextState = 0;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            if (this.nextState == -1) {
                calcNext$2();
            }
            return this.nextState == 1;
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.nextState == -1) {
                calcNext$2();
            }
            if (this.nextState == 0) {
                throw new NoSuchElementException();
            }
            Object obj = this.nextItem;
            this.nextItem = null;
            this.nextState = -1;
            return obj;
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public TakeWhileSequence(Sequence sequence, Function1 function1) {
        this.sequence = sequence;
        this.predicate = function1;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
