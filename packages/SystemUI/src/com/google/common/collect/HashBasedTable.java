package com.google.common.collect;

import com.google.common.base.Supplier;
import com.google.common.collect.StandardTable;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class HashBasedTable<R, C, V> extends StandardTable<R, C, V> {
    private static final long serialVersionUID = 0;

    class Factory<C, V> implements Supplier, Serializable {
        private static final long serialVersionUID = 0;
        final int expectedSize;

        public Factory(int i) {
            this.expectedSize = i;
        }

        @Override // com.google.common.base.Supplier
        public final Object get() {
            return new LinkedHashMap(Maps.capacity(this.expectedSize));
        }
    }

    public HashBasedTable(Map<R, Map<C, V>> map, Factory<C, V> factory) {
        super(map, factory);
    }

    public static HashBasedTable create() {
        return new HashBasedTable(new LinkedHashMap(), new Factory(0));
    }

    @Override // com.google.common.collect.StandardTable
    public final StandardTable.Row row(Object obj) {
        return new StandardTable.Row(obj);
    }
}
