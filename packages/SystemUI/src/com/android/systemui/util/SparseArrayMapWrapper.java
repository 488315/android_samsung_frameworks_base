package com.android.systemui.util;

import android.util.SparseArray;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes3.dex */
final class SparseArrayMapWrapper<T> implements Map<Integer, T>, KMappedMarker {
    private final Sequence entrySequence = new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SparseArrayMapWrapper$entrySequence$1(this, null));
    private final Set<Integer> keys = new SparseArrayMapWrapper$keys$1(this);
    private final SparseArray<T> sparseArray;

    final class Entry<T> implements Map.Entry<Integer, T>, KMappedMarker {
        private final int key;
        private final T value;

        public Entry(int i, T t) {
            this.key = i;
            this.value = t;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Entry copy$default(Entry entry, int i, Object obj, int i2, Object obj2) {
            if ((i2 & 1) != 0) {
                i = entry.key;
            }
            if ((i2 & 2) != 0) {
                obj = entry.value;
            }
            return entry.copy(i, obj);
        }

        public final int component1() {
            return this.key;
        }

        public final T component2() {
            return this.value;
        }

        public final Entry<T> copy(int i, T t) {
            return new Entry<>(i, t);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.key == entry.key && Intrinsics.areEqual(this.value, entry.value);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        public Integer getKey() {
            return Integer.valueOf(this.key);
        }

        @Override // java.util.Map.Entry
        public T getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iHashCode = Integer.hashCode(this.key) * 31;
            T t = this.value;
            return iHashCode + (t == null ? 0 : t.hashCode());
        }

        @Override // java.util.Map.Entry
        public T setValue(T t) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public String toString() {
            return "Entry(key=" + this.key + ", value=" + this.value + ")";
        }
    }

    /* renamed from: com.android.systemui.util.SparseArrayMapWrapper$values$1, reason: invalid class name */
    public final class AnonymousClass1 implements Collection<Object>, KMappedMarker {
        final /* synthetic */ SparseArrayMapWrapper<Object> this$0;
        private final Sequence valueSequence;

        public AnonymousClass1(SparseArrayMapWrapper<Object> sparseArrayMapWrapper) {
            this.this$0 = sparseArrayMapWrapper;
            this.valueSequence = new TransformingSequence(((SparseArrayMapWrapper) sparseArrayMapWrapper).entrySequence, new SparseArrayMapWrapper$keys$1$$ExternalSyntheticLambda0(1));
        }

        @Override // java.util.Collection
        public boolean add(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Object> collection) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public void clear() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return this.this$0.containsValue(obj);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<? extends Object> collection) {
            Collection<? extends Object> collection2 = collection;
            if (collection2.isEmpty()) {
                return true;
            }
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        public int getSize() {
            return this.this$0.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.this$0.isEmpty();
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return this.valueSequence.iterator();
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<? extends Object> collection) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super Object> predicate) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<? extends Object> collection) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) CollectionToArray.toArray(this, tArr);
        }
    }

    public SparseArrayMapWrapper(SparseArray<T> sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: compute, reason: avoid collision after fix types in other method */
    public T compute2(Integer num, BiFunction<? super Integer, ? super T, ? extends T> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: computeIfAbsent, reason: avoid collision after fix types in other method */
    public T computeIfAbsent2(Integer num, Function<? super Integer, ? extends T> function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: computeIfPresent, reason: avoid collision after fix types in other method */
    public T computeIfPresent2(Integer num, BiFunction<? super Integer, ? super T, ? extends T> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Integer) {
            return containsKey(((Number) obj).intValue());
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.sparseArray.indexOfValue(obj) >= 0;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Map.Entry<Integer, T>> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final /* bridge */ T get(Object obj) {
        if (obj instanceof Integer) {
            return get(((Number) obj).intValue());
        }
        return null;
    }

    public Set<Map.Entry<Integer, T>> getEntries() {
        return new SparseArrayMapWrapper$entries$1(this);
    }

    public Set<Integer> getKeys() {
        return this.keys;
    }

    public int getSize() {
        return this.sparseArray.size();
    }

    public Collection<T> getValues() {
        return new AnonymousClass1(this);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.sparseArray.size() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Integer> keySet() {
        return getKeys();
    }

    /* renamed from: merge, reason: avoid collision after fix types in other method */
    public T merge2(Integer num, T t, BiFunction<? super T, ? super T, ? extends T> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public T put(int i, T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Integer, ? extends T> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: putIfAbsent, reason: avoid collision after fix types in other method */
    public T putIfAbsent2(Integer num, T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public T remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: replace, reason: avoid collision after fix types in other method */
    public T replace2(Integer num, T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super Integer, ? super T, ? extends T> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final /* bridge */ Collection<T> values() {
        return getValues();
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object compute(Integer num, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfAbsent(Integer num, Function function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object computeIfPresent(Integer num, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean containsKey(int i) {
        return this.sparseArray.contains(i);
    }

    public T get(int i) {
        return this.sparseArray.get(i);
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object merge(Integer num, Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(Integer num, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object putIfAbsent(Integer num, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object replace(Integer num, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: replace, reason: avoid collision after fix types in other method */
    public boolean replace2(Integer num, T t, T t2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ boolean replace(Integer num, Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
