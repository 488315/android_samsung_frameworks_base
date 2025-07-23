package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Tables;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class StandardTable<R, C, V> extends AbstractTable implements Serializable {
    private static final long serialVersionUID = 0;
    final Map<R, Map<C, V>> backingMap;
    final Supplier factory;
    public transient RowMap rowMap;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CellIterator implements Iterator {
        public Iterator columnIterator;
        public Map.Entry rowEntry;
        public final Iterator rowIterator;

        private CellIterator(StandardTable standardTable) {
            this.rowIterator = standardTable.backingMap.entrySet().iterator();
            this.columnIterator = Iterators$EmptyModifiableIterator.INSTANCE;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (!this.columnIterator.hasNext()) {
                Map.Entry entry = (Map.Entry) this.rowIterator.next();
                this.rowEntry = entry;
                this.columnIterator = ((Map) entry.getValue()).entrySet().iterator();
            }
            Objects.requireNonNull(this.rowEntry);
            Map.Entry entry2 = (Map.Entry) this.columnIterator.next();
            Object key = this.rowEntry.getKey();
            Object key2 = entry2.getKey();
            Object value = entry2.getValue();
            int i = Tables.$r8$clinit;
            return new Tables.ImmutableCell(key, key2, value);
        }

        @Override // java.util.Iterator
        public final void remove() {
            this.columnIterator.remove();
            Map.Entry entry = this.rowEntry;
            Objects.requireNonNull(entry);
            if (((Map) entry.getValue()).isEmpty()) {
                this.rowIterator.remove();
                this.rowEntry = null;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Row extends Maps.IteratorBasedAbstractMap {
        public Map backingRowMap;
        public final Object rowKey;

        public Row(Object obj) {
            obj.getClass();
            this.rowKey = obj;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap, java.util.AbstractMap, java.util.Map
        public final void clear() {
            updateBackingRowMapField();
            Map map = this.backingRowMap;
            if (map != null) {
                map.clear();
            }
            maintainEmptyInvariant();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsKey(Object obj) {
            Map map;
            boolean z;
            updateBackingRowMapField();
            if (obj != null && (map = this.backingRowMap) != null) {
                try {
                    z = map.containsKey(obj);
                } catch (ClassCastException | NullPointerException unused) {
                    z = false;
                }
                if (z) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.common.collect.Maps.IteratorBasedAbstractMap
        public final Iterator entryIterator() {
            updateBackingRowMapField();
            Map map = this.backingRowMap;
            if (map == null) {
                return Iterators$EmptyModifiableIterator.INSTANCE;
            }
            final Iterator it = map.entrySet().iterator();
            return new Iterator() { // from class: com.google.common.collect.StandardTable.Row.1
                @Override // java.util.Iterator
                public final boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public final Object next() {
                    Row row = Row.this;
                    Map.Entry entry = (Map.Entry) it.next();
                    row.getClass();
                    return new ForwardingMapEntry(row, entry) { // from class: com.google.common.collect.StandardTable.Row.2
                        public final /* synthetic */ Map.Entry val$entry;

                        {
                            this.val$entry = entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Object delegate() {
                            return this.val$entry;
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public final boolean equals(Object obj) {
                            if (!(obj instanceof Map.Entry)) {
                                return false;
                            }
                            Map.Entry entry2 = (Map.Entry) obj;
                            return com.google.common.base.Objects.equal(getKey(), entry2.getKey()) && com.google.common.base.Objects.equal(getValue(), entry2.getValue());
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, java.util.Map.Entry
                        public final Object setValue(Object obj) {
                            obj.getClass();
                            return super.setValue(obj);
                        }

                        @Override // com.google.common.collect.ForwardingMapEntry, com.google.common.collect.ForwardingObject
                        public final Map.Entry delegate() {
                            return this.val$entry;
                        }
                    };
                }

                @Override // java.util.Iterator
                public final void remove() {
                    it.remove();
                    Row.this.maintainEmptyInvariant();
                }
            };
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object get(Object obj) {
            Map map;
            updateBackingRowMapField();
            if (obj == null || (map = this.backingRowMap) == null) {
                return null;
            }
            return Maps.safeGet(obj, map);
        }

        public final void maintainEmptyInvariant() {
            updateBackingRowMapField();
            Map map = this.backingRowMap;
            if (map == null || !map.isEmpty()) {
                return;
            }
            StandardTable.this.backingMap.remove(this.rowKey);
            this.backingRowMap = null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object put(Object obj, Object obj2) {
            obj.getClass();
            obj2.getClass();
            Map map = this.backingRowMap;
            return (map == null || map.isEmpty()) ? StandardTable.this.put(this.rowKey, obj, obj2) : this.backingRowMap.put(obj, obj2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object remove(Object obj) {
            updateBackingRowMapField();
            Map map = this.backingRowMap;
            Object obj2 = null;
            if (map == null) {
                return null;
            }
            map.getClass();
            try {
                obj2 = map.remove(obj);
            } catch (ClassCastException | NullPointerException unused) {
            }
            maintainEmptyInvariant();
            return obj2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final int size() {
            updateBackingRowMapField();
            Map map = this.backingRowMap;
            if (map == null) {
                return 0;
            }
            return map.size();
        }

        public final void updateBackingRowMapField() {
            Map map = this.backingRowMap;
            if (map == null || (map.isEmpty() && StandardTable.this.backingMap.containsKey(this.rowKey))) {
                this.backingRowMap = StandardTable.this.backingMap.get(this.rowKey);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RowMap extends Maps.ViewCachingAbstractMap {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class EntrySet extends TableSet {
            private EntrySet() {
                super();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean contains(Object obj) {
                boolean z;
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    if (entry.getKey() != null && (entry.getValue() instanceof Map)) {
                        Set<Map.Entry<R, Map<C, V>>> entrySet = StandardTable.this.backingMap.entrySet();
                        entrySet.getClass();
                        try {
                            z = entrySet.contains(entry);
                        } catch (ClassCastException | NullPointerException unused) {
                            z = false;
                        }
                        if (z) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator iterator() {
                Set<R> keySet = StandardTable.this.backingMap.keySet();
                final Function function = new Function() { // from class: com.google.common.collect.StandardTable.RowMap.EntrySet.1
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return StandardTable.this.row(obj);
                    }
                };
                final Iterator<R> it = keySet.iterator();
                return new TransformedIterator(it) { // from class: com.google.common.collect.Maps.3
                    public final /* synthetic */ Function val$function;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass3(final Iterator it2, final Function function2) {
                        super(it2);
                        r2 = function2;
                    }

                    @Override // com.google.common.collect.TransformedIterator
                    public final Object transform(Object obj) {
                        return new ImmutableEntry(obj, r2.apply(obj));
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean remove(Object obj) {
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    if (entry.getKey() != null && (entry.getValue() instanceof Map) && StandardTable.this.backingMap.entrySet().remove(entry)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final int size() {
                return StandardTable.this.backingMap.size();
            }
        }

        public RowMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsKey(Object obj) {
            return StandardTable.this.containsRow(obj);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        public final EntrySet createEntrySet() {
            return new EntrySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object get(Object obj) {
            if (!StandardTable.this.containsRow(obj)) {
                return null;
            }
            StandardTable standardTable = StandardTable.this;
            Objects.requireNonNull(obj);
            return standardTable.row(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return StandardTable.this.backingMap.remove(obj);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class TableSet extends Sets$ImprovedAbstractSet {
        private TableSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            StandardTable.this.backingMap.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
    }

    public StandardTable(Map<R, Map<C, V>> map, Supplier supplier) {
        this.backingMap = map;
        this.factory = supplier;
    }

    @Override // com.google.common.collect.AbstractTable
    public final CellIterator cellIterator() {
        return new CellIterator();
    }

    @Override // com.google.common.collect.AbstractTable
    public void clear() {
        this.backingMap.clear();
    }

    public boolean containsRow(Object obj) {
        boolean z;
        if (obj != null) {
            Map<R, Map<C, V>> map = this.backingMap;
            map.getClass();
            try {
                z = map.containsKey(obj);
            } catch (ClassCastException | NullPointerException unused) {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2, Object obj3) {
        obj.getClass();
        Map<C, V> map = this.backingMap.get(obj);
        if (map == null) {
            map = (Map) this.factory.get();
            this.backingMap.put(obj, map);
        }
        return map.put(obj2, obj3);
    }

    public Row row(Object obj) {
        return new Row(obj);
    }

    @Override // com.google.common.collect.AbstractTable
    public Map rowMap() {
        RowMap rowMap = this.rowMap;
        if (rowMap != null) {
            return rowMap;
        }
        RowMap rowMap2 = new RowMap();
        this.rowMap = rowMap2;
        return rowMap2;
    }

    @Override // com.google.common.collect.AbstractTable
    public int size() {
        Iterator<Map<C, V>> it = this.backingMap.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().size();
        }
        return i;
    }
}
