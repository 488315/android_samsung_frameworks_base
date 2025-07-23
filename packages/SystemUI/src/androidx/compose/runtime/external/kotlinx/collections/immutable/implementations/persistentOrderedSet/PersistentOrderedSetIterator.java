package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PersistentOrderedSetIterator<E> implements Iterator<E>, KMappedMarker {
    public int index;
    public final Map map;
    public Object nextElement;

    public PersistentOrderedSetIterator(Object obj, Map<E, Links> map) {
        this.nextElement = obj;
        this.map = map;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.index < this.map.size();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object obj = this.nextElement;
        this.index++;
        Object obj2 = this.map.get(obj);
        if (obj2 != null) {
            this.nextElement = ((Links) obj2).next;
            return obj;
        }
        throw new ConcurrentModificationException("Hash code of an element (" + obj + ") has changed after it was added to the persistent set.");
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
