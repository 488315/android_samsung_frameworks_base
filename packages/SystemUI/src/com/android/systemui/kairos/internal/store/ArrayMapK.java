package com.android.systemui.kairos.internal.store;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.AbstractMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ArrayMapK extends AbstractMap implements MapK {
    public final ArrayMapK$entries$1 entries = new ArrayMapK$entries$1(this);
    public final List unwrapped;

    public ArrayMapK(List<? extends Map.Entry<Integer, Object>> list, int i) {
        this.unwrapped = list;
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Integer) {
            return super.containsKey(Integer.valueOf(((Number) obj).intValue()));
        }
        return false;
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public final /* bridge */ Object get(Object obj) {
        if (obj instanceof Integer) {
            return super.get(Integer.valueOf(((Number) obj).intValue()));
        }
        return null;
    }

    @Override // kotlin.collections.AbstractMap
    public final Set getEntries() {
        return this.entries;
    }

    @Override // java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof Integer) ? obj2 : super.getOrDefault(Integer.valueOf(((Number) obj).intValue()), obj2);
    }
}
