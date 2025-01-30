package com.google.protobuf;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SmallSortedMap extends AbstractMap {
    public static final /* synthetic */ int $r8$clinit = 0;
    public List entryList;
    public boolean isImmutable;
    public volatile DescendingEntrySet lazyDescendingEntrySet;
    public volatile EntrySet lazyEntrySet;
    public final int maxArraySize;
    public Map overflowEntries;
    public Map overflowEntriesDescending;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DescendingEntryIterator implements Iterator {
        public Iterator lazyOverflowIterator;
        public int pos;

        private DescendingEntryIterator() {
            this.pos = SmallSortedMap.this.entryList.size();
        }

        public final Iterator getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntriesDescending.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            int i = this.pos;
            return (i > 0 && i <= SmallSortedMap.this.entryList.size()) || getOverflowIterator().hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (getOverflowIterator().hasNext()) {
                return (Map.Entry) getOverflowIterator().next();
            }
            List list = SmallSortedMap.this.entryList;
            int i = this.pos - 1;
            this.pos = i;
            return (Map.Entry) list.get(i);
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DescendingEntrySet extends EntrySet {
        private DescendingEntrySet() {
            super();
        }

        @Override // com.google.protobuf.SmallSortedMap.EntrySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            return new DescendingEntryIterator();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmptySet {
        public static final C45371 ITERATOR = new Iterator() { // from class: com.google.protobuf.SmallSortedMap.EmptySet.1
            @Override // java.util.Iterator
            public final boolean hasNext() {
                return false;
            }

            @Override // java.util.Iterator
            public final Object next() {
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public final void remove() {
                throw new UnsupportedOperationException();
            }
        };
        public static final C45382 ITERABLE = new Iterable() { // from class: com.google.protobuf.SmallSortedMap.EmptySet.2
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return EmptySet.ITERATOR;
            }
        };

        private EmptySet() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Entry implements Map.Entry, Comparable {
        public final Comparable key;
        public Object value;

        public Entry(SmallSortedMap smallSortedMap, Map.Entry<Comparable<Object>, Object> entry) {
            this(entry.getKey(), entry.getValue());
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            return this.key.compareTo(((Entry) obj).key);
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Comparable comparable = this.key;
            Object key = entry.getKey();
            if (comparable == null ? key == null : comparable.equals(key)) {
                Object obj2 = this.value;
                Object value = entry.getValue();
                if (obj2 == null ? value == null : obj2.equals(value)) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            Comparable comparable = this.key;
            int hashCode = comparable == null ? 0 : comparable.hashCode();
            Object obj = this.value;
            return hashCode ^ (obj != null ? obj.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            SmallSortedMap smallSortedMap = SmallSortedMap.this;
            int i = SmallSortedMap.$r8$clinit;
            smallSortedMap.checkMutable();
            Object obj2 = this.value;
            this.value = obj;
            return obj2;
        }

        public final String toString() {
            return this.key + "=" + this.value;
        }

        public Entry(Comparable<Object> comparable, Object obj) {
            this.key = comparable;
            this.value = obj;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EntryIterator implements Iterator {
        public Iterator lazyOverflowIterator;
        public boolean nextCalledBeforeRemove;
        public int pos;

        private EntryIterator() {
            this.pos = -1;
        }

        public final Iterator getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            if (this.pos + 1 >= SmallSortedMap.this.entryList.size()) {
                return !SmallSortedMap.this.overflowEntries.isEmpty() && getOverflowIterator().hasNext();
            }
            return true;
        }

        @Override // java.util.Iterator
        public final Object next() {
            this.nextCalledBeforeRemove = true;
            int i = this.pos + 1;
            this.pos = i;
            return i < SmallSortedMap.this.entryList.size() ? (Map.Entry) SmallSortedMap.this.entryList.get(this.pos) : (Map.Entry) getOverflowIterator().next();
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (!this.nextCalledBeforeRemove) {
                throw new IllegalStateException("remove() was called before next()");
            }
            this.nextCalledBeforeRemove = false;
            SmallSortedMap smallSortedMap = SmallSortedMap.this;
            int i = SmallSortedMap.$r8$clinit;
            smallSortedMap.checkMutable();
            if (this.pos >= SmallSortedMap.this.entryList.size()) {
                getOverflowIterator().remove();
                return;
            }
            SmallSortedMap smallSortedMap2 = SmallSortedMap.this;
            int i2 = this.pos;
            this.pos = i2 - 1;
            smallSortedMap2.removeArrayEntryAt(i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class EntrySet extends AbstractSet {
        private EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean add(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            if (contains(entry)) {
                return false;
            }
            SmallSortedMap.this.put((Comparable) entry.getKey(), entry.getValue());
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            SmallSortedMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = SmallSortedMap.this.get(entry.getKey());
            Object value = entry.getValue();
            return obj2 == value || (obj2 != null && obj2.equals(value));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator iterator() {
            return new EntryIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            if (!contains(entry)) {
                return false;
            }
            SmallSortedMap.this.remove(entry.getKey());
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return SmallSortedMap.this.size();
        }
    }

    public final int binarySearchInArray(Comparable comparable) {
        int size = this.entryList.size() - 1;
        if (size >= 0) {
            int compareTo = comparable.compareTo(((Entry) this.entryList.get(size)).key);
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = comparable.compareTo(((Entry) this.entryList.get(i2)).key);
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else {
                if (compareTo2 <= 0) {
                    return i2;
                }
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    public final void checkMutable() {
        if (this.isImmutable) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        checkMutable();
        if (!this.entryList.isEmpty()) {
            this.entryList.clear();
        }
        if (this.overflowEntries.isEmpty()) {
            return;
        }
        this.overflowEntries.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return binarySearchInArray(comparable) >= 0 || this.overflowEntries.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        if (this.lazyEntrySet == null) {
            this.lazyEntrySet = new EntrySet();
        }
        return this.lazyEntrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmallSortedMap)) {
            return super.equals(obj);
        }
        SmallSortedMap smallSortedMap = (SmallSortedMap) obj;
        int size = size();
        if (size != smallSortedMap.size()) {
            return false;
        }
        int numArrayEntries = getNumArrayEntries();
        if (numArrayEntries != smallSortedMap.getNumArrayEntries()) {
            return entrySet().equals(smallSortedMap.entrySet());
        }
        for (int i = 0; i < numArrayEntries; i++) {
            if (!getArrayEntryAt(i).equals(smallSortedMap.getArrayEntryAt(i))) {
                return false;
            }
        }
        if (numArrayEntries != size) {
            return this.overflowEntries.equals(smallSortedMap.overflowEntries);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int binarySearchInArray = binarySearchInArray(comparable);
        return binarySearchInArray >= 0 ? ((Entry) this.entryList.get(binarySearchInArray)).value : this.overflowEntries.get(comparable);
    }

    public final Map.Entry getArrayEntryAt(int i) {
        return (Map.Entry) this.entryList.get(i);
    }

    public final int getNumArrayEntries() {
        return this.entryList.size();
    }

    public final Iterable getOverflowEntries() {
        return this.overflowEntries.isEmpty() ? EmptySet.ITERABLE : this.overflowEntries.entrySet();
    }

    public final SortedMap getOverflowEntriesMutable() {
        checkMutable();
        if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.overflowEntries = treeMap;
            this.overflowEntriesDescending = treeMap.descendingMap();
        }
        return (SortedMap) this.overflowEntries;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        int numArrayEntries = getNumArrayEntries();
        int i = 0;
        for (int i2 = 0; i2 < numArrayEntries; i2++) {
            i += ((Entry) this.entryList.get(i2)).hashCode();
        }
        return this.overflowEntries.size() > 0 ? i + this.overflowEntries.hashCode() : i;
    }

    public void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        this.overflowEntries = this.overflowEntries.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.overflowEntries);
        this.overflowEntriesDescending = this.overflowEntriesDescending.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.overflowEntriesDescending);
        this.isImmutable = true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        checkMutable();
        Comparable comparable = (Comparable) obj;
        int binarySearchInArray = binarySearchInArray(comparable);
        if (binarySearchInArray >= 0) {
            return removeArrayEntryAt(binarySearchInArray);
        }
        if (this.overflowEntries.isEmpty()) {
            return null;
        }
        return this.overflowEntries.remove(comparable);
    }

    public final Object removeArrayEntryAt(int i) {
        checkMutable();
        Object obj = ((Entry) this.entryList.remove(i)).value;
        if (!this.overflowEntries.isEmpty()) {
            Iterator it = getOverflowEntriesMutable().entrySet().iterator();
            this.entryList.add(new Entry(this, (Map.Entry) it.next()));
            it.remove();
        }
        return obj;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.overflowEntries.size() + this.entryList.size();
    }

    private SmallSortedMap(int i) {
        this.maxArraySize = i;
        this.entryList = Collections.emptyList();
        this.overflowEntries = Collections.emptyMap();
        this.overflowEntriesDescending = Collections.emptyMap();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Comparable comparable, Object obj) {
        checkMutable();
        int binarySearchInArray = binarySearchInArray(comparable);
        if (binarySearchInArray >= 0) {
            return ((Entry) this.entryList.get(binarySearchInArray)).setValue(obj);
        }
        checkMutable();
        if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
            this.entryList = new ArrayList(this.maxArraySize);
        }
        int i = -(binarySearchInArray + 1);
        if (i >= this.maxArraySize) {
            return getOverflowEntriesMutable().put(comparable, obj);
        }
        int size = this.entryList.size();
        int i2 = this.maxArraySize;
        if (size == i2) {
            Entry entry = (Entry) this.entryList.remove(i2 - 1);
            getOverflowEntriesMutable().put(entry.key, entry.value);
        }
        this.entryList.add(i, new Entry(comparable, obj));
        return null;
    }
}
