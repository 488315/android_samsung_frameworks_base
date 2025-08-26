package com.android.systemui.kairos.internal.util;

import java.util.Map;
import kotlin.jvm.internal.markers.KMutableMap;

/* loaded from: classes2.dex */
public final class ConcurrentNullableHashMap$entries$1$add$e$1 implements Map.Entry, KMutableMap.Entry {
    public final /* synthetic */ Map.Entry $element;

    public ConcurrentNullableHashMap$entries$1$add$e$1(Map.Entry<Object, Object> entry, ConcurrentNullableHashMap concurrentNullableHashMap) {
        this.$element = entry;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        Object key = this.$element.getKey();
        return key == null ? NullValue.INSTANCE : key;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        Object value = this.$element.getValue();
        return value == null ? NullValue.INSTANCE : value;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Map.Entry entry = this.$element;
        NullValue nullValue = NullValue.INSTANCE;
        if (obj == nullValue) {
            obj = null;
        }
        Object value = entry.setValue(obj);
        return value == null ? nullValue : value;
    }
}
