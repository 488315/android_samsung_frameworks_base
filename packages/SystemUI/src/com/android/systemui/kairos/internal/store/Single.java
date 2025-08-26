package com.android.systemui.kairos.internal.store;

import java.util.Collections;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.AbstractMap;
import kotlin.collections.EmptySet;

/* loaded from: classes2.dex */
public final class Single extends AbstractMap implements MapK {
    public final Set entries;
    public final Object unwrapped;

    public Single(Object obj) {
        this.unwrapped = obj;
        this.entries = obj == NoValue.INSTANCE ? EmptySet.INSTANCE : Collections.singleton(new StoreEntry(Unit.INSTANCE, obj));
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Unit) {
            return super.containsKey((Unit) obj);
        }
        return false;
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public final /* bridge */ Object get(Object obj) {
        if (obj instanceof Unit) {
            return super.get((Unit) obj);
        }
        return null;
    }

    @Override // kotlin.collections.AbstractMap
    public final Set getEntries() {
        return this.entries;
    }

    @Override // java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof Unit) ? obj2 : super.getOrDefault((Unit) obj, obj2);
    }

    public Single() {
        this(NoValue.INSTANCE);
    }
}
