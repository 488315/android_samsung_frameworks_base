package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SparseArrayMapWrapper$values$1 implements Collection, KMappedMarker {
    public final /* synthetic */ SparseArrayMapWrapper this$0;
    public final TransformingSequence valueSequence;

    public SparseArrayMapWrapper$values$1(SparseArrayMapWrapper sparseArrayMapWrapper) {
        this.this$0 = sparseArrayMapWrapper;
        this.valueSequence = new TransformingSequence(sparseArrayMapWrapper.entrySequence, new Function1() { // from class: com.android.systemui.util.SparseArrayMapWrapper$values$1$valueSequence$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((SparseArrayMapWrapper.Entry) obj).value;
            }
        });
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        return this.this$0.containsValue(obj);
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        if (collection.isEmpty()) {
            return true;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.this$0.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        TransformingSequence transformingSequence = this.valueSequence;
        transformingSequence.getClass();
        return new TransformingSequence$iterator$1(transformingSequence);
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final int size() {
        return this.this$0.size();
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return CollectionToArray.toArray(this, objArr);
    }
}
