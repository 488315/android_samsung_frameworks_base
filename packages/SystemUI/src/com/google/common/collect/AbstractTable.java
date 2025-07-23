package com.google.common.collect;

import com.google.common.collect.StandardTable;
import com.google.common.collect.Tables;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractTable {
    public transient CellSet cellSet;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CellSet extends AbstractSet {
        public CellSet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            AbstractTable.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            boolean z;
            if (obj instanceof Tables.AbstractCell) {
                Tables.AbstractCell abstractCell = (Tables.AbstractCell) obj;
                Map map = (Map) Maps.safeGet(abstractCell.getRowKey(), AbstractTable.this.rowMap());
                if (map != null) {
                    Set entrySet = map.entrySet();
                    ImmutableEntry immutableEntry = new ImmutableEntry(abstractCell.getColumnKey(), abstractCell.getValue());
                    entrySet.getClass();
                    try {
                        z = entrySet.contains(immutableEntry);
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
            return AbstractTable.this.cellIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            boolean z;
            if (!(obj instanceof Tables.AbstractCell)) {
                return false;
            }
            Tables.AbstractCell abstractCell = (Tables.AbstractCell) obj;
            Map map = (Map) Maps.safeGet(abstractCell.getRowKey(), AbstractTable.this.rowMap());
            if (map == null) {
                return false;
            }
            Set entrySet = map.entrySet();
            ImmutableEntry immutableEntry = new ImmutableEntry(abstractCell.getColumnKey(), abstractCell.getValue());
            entrySet.getClass();
            try {
                z = entrySet.remove(immutableEntry);
            } catch (ClassCastException | NullPointerException unused) {
                z = false;
            }
            return z;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return AbstractTable.this.size();
        }
    }

    public abstract StandardTable.CellIterator cellIterator();

    public Set cellSet() {
        CellSet cellSet = this.cellSet;
        if (cellSet != null) {
            return cellSet;
        }
        CellSet cellSet2 = new CellSet();
        this.cellSet = cellSet2;
        return cellSet2;
    }

    public void clear() {
        Iterator it = cellSet().iterator();
        it.getClass();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public final boolean equals(Object obj) {
        int i = Tables.$r8$clinit;
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractTable) {
            return cellSet().equals(((AbstractTable) obj).cellSet());
        }
        return false;
    }

    public final int hashCode() {
        return cellSet().hashCode();
    }

    public abstract Map rowMap();

    public abstract int size();

    public final String toString() {
        return rowMap().toString();
    }
}
