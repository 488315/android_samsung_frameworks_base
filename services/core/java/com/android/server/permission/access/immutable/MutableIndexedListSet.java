package com.android.server.permission.access.immutable;

import java.util.ArrayList;

public final class MutableIndexedListSet extends IndexedListSet {
    public MutableIndexedListSet() {
        super(new ArrayList());
    }

    public final void add(Object obj) {
        if (this.list.contains(obj)) {
            return;
        }
        this.list.add(obj);
    }
}
