package com.android.systemui.kairos.internal.util;

import java.util.Map;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ConcurrentNullableHashMap$entries$1$iterator$1$next$1 implements Map.Entry, KMutableMap.Entry {
    public final /* synthetic */ Map.Entry $element;

    public ConcurrentNullableHashMap$entries$1$iterator$1$next$1(ConcurrentNullableHashMap concurrentNullableHashMap, Map.Entry<Object, Object> entry) {
        this.$element = entry;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        Object key = this.$element.getKey();
        if (key != NullValue.INSTANCE) {
            return key;
        }
        return null;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        Object value = this.$element.getValue();
        if (value != NullValue.INSTANCE) {
            return value;
        }
        return null;
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        Map.Entry entry = this.$element;
        if (obj == null) {
            obj = NullValue.INSTANCE;
        }
        Object value = entry.setValue(obj);
        if (value != NullValue.INSTANCE) {
            return value;
        }
        return null;
    }
}
