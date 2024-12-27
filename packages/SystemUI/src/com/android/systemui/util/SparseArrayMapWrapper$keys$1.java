package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SparseArrayMapWrapper$keys$1 implements Set<Integer>, KMappedMarker {
    private final Sequence keySequence;
    final /* synthetic */ SparseArrayMapWrapper<Object> this$0;

    public SparseArrayMapWrapper$keys$1(SparseArrayMapWrapper<Object> sparseArrayMapWrapper) {
        Sequence sequence;
        this.this$0 = sparseArrayMapWrapper;
        sequence = ((SparseArrayMapWrapper) sparseArrayMapWrapper).entrySequence;
        this.keySequence = new TransformingSequence(sequence, new Function1() { // from class: com.android.systemui.util.SparseArrayMapWrapper$keys$1$keySequence$1
            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(SparseArrayMapWrapper.Entry<Object> entry) {
                return entry.getKey();
            }
        });
    }

    public boolean add(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends Integer> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Integer) {
            return contains(((Number) obj).intValue());
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<? extends Object> collection) {
        Collection<? extends Object> collection2 = collection;
        if (collection2.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (!contains(Integer.valueOf(((Number) it.next()).intValue()))) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return this.this$0.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<Integer> iterator() {
        return this.keySequence.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Set, java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains(int i) {
        return this.this$0.containsKey(Integer.valueOf(i));
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) CollectionToArray.toArray(this, tArr);
    }
}
