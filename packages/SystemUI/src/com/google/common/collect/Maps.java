package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.StandardTable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class Maps {

    public abstract class EntrySet extends Sets$ImprovedAbstractSet {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            map().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object objSafeGet = Maps.safeGet(key, map());
            if (Objects.equal(objSafeGet, entry.getValue())) {
                return objSafeGet != null || map().containsKey(key);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean isEmpty() {
            return map().isEmpty();
        }

        public abstract Map map();

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (contains(obj) && (obj instanceof Map.Entry)) {
                return map().keySet().remove(((Map.Entry) obj).getKey());
            }
            return false;
        }

        @Override // com.google.common.collect.Sets$ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean removeAll(Collection collection) {
            try {
                collection.getClass();
                return super.removeAll(collection);
            } catch (UnsupportedOperationException unused) {
                Iterator it = collection.iterator();
                boolean zRemove = false;
                while (it.hasNext()) {
                    zRemove |= this.remove(it.next());
                }
                return zRemove;
            }
        }

        @Override // com.google.common.collect.Sets$ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean retainAll(Collection collection) {
            try {
                collection.getClass();
                return super.retainAll(collection);
            } catch (UnsupportedOperationException unused) {
                HashSet hashSet = new HashSet(Maps.capacity(collection.size()));
                for (Object obj : collection) {
                    if (this.contains(obj) && (obj instanceof Map.Entry)) {
                        hashSet.add(((Map.Entry) obj).getKey());
                    }
                }
                return this.map().keySet().retainAll(hashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return map().size();
        }
    }

    public abstract class IteratorBasedAbstractMap extends AbstractMap {
        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            Iterator itEntryIterator = entryIterator();
            itEntryIterator.getClass();
            while (itEntryIterator.hasNext()) {
                itEntryIterator.next();
                itEntryIterator.remove();
            }
        }

        public abstract Iterator entryIterator();

        @Override // java.util.AbstractMap, java.util.Map
        public final Set entrySet() {
            return new EntrySet() { // from class: com.google.common.collect.Maps.IteratorBasedAbstractMap.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public final Iterator iterator() {
                    return IteratorBasedAbstractMap.this.entryIterator();
                }

                @Override // com.google.common.collect.Maps.EntrySet
                public final Map map() {
                    return IteratorBasedAbstractMap.this;
                }
            };
        }
    }

    public class KeySet extends Sets$ImprovedAbstractSet {
        public final Map map;

        public KeySet(Map<Object, Object> map) {
            map.getClass();
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            this.map.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            return new TransformedIterator(this.map.entrySet().iterator()) { // from class: com.google.common.collect.Maps.1
                @Override // com.google.common.collect.TransformedIterator
                public final Object transform(Object obj) {
                    return ((Map.Entry) obj).getKey();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (!this.map.containsKey(obj)) {
                return false;
            }
            this.map.remove(obj);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.map.size();
        }
    }

    public class Values extends AbstractCollection {
        public final Map map;

        public Values(Map<Object, Object> map) {
            map.getClass();
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final void clear() {
            this.map.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            return new TransformedIterator(this.map.entrySet().iterator()) { // from class: com.google.common.collect.Maps.2
                @Override // com.google.common.collect.TransformedIterator
                public final Object transform(Object obj) {
                    return ((Map.Entry) obj).getValue();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean remove(Object obj) {
            try {
                return super.remove(obj);
            } catch (UnsupportedOperationException unused) {
                for (Map.Entry entry : this.map.entrySet()) {
                    if (Objects.equal(obj, entry.getValue())) {
                        this.map.remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean removeAll(Collection collection) {
            try {
                collection.getClass();
                return super.removeAll(collection);
            } catch (UnsupportedOperationException unused) {
                HashSet hashSet = new HashSet();
                for (Map.Entry entry : this.map.entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSet.add(entry.getKey());
                    }
                }
                return this.map.keySet().removeAll(hashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean retainAll(Collection collection) {
            try {
                collection.getClass();
                return super.retainAll(collection);
            } catch (UnsupportedOperationException unused) {
                HashSet hashSet = new HashSet();
                for (Map.Entry entry : this.map.entrySet()) {
                    if (collection.contains(entry.getValue())) {
                        hashSet.add(entry.getKey());
                    }
                }
                return this.map.keySet().retainAll(hashSet);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final int size() {
            return this.map.size();
        }
    }

    public abstract class ViewCachingAbstractMap extends AbstractMap {
        public transient Set entrySet;
        public transient KeySet keySet;
        public transient Values values;

        public abstract StandardTable.RowMap.EntrySet createEntrySet();

        @Override // java.util.AbstractMap, java.util.Map
        public final Set entrySet() {
            Set set = this.entrySet;
            if (set != null) {
                return set;
            }
            StandardTable.RowMap.EntrySet entrySetCreateEntrySet = createEntrySet();
            this.entrySet = entrySetCreateEntrySet;
            return entrySetCreateEntrySet;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set keySet() {
            KeySet keySet = this.keySet;
            if (keySet != null) {
                return keySet;
            }
            KeySet keySet2 = new KeySet(this);
            this.keySet = keySet2;
            return keySet2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Collection values() {
            Values values = this.values;
            if (values != null) {
                return values;
            }
            Values values2 = new Values(this);
            this.values = values2;
            return values2;
        }
    }

    private Maps() {
    }

    public static int capacity(int i) {
        if (i < 3) {
            CollectPreconditions.checkNonnegative(i, "expectedSize");
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) Math.ceil(i / 0.75d);
        }
        return Integer.MAX_VALUE;
    }

    public static Object safeGet(Object obj, Map map) {
        map.getClass();
        try {
            return map.get(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }
}
