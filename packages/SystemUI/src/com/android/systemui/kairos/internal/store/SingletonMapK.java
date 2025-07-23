package com.android.systemui.kairos.internal.store;

import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SingletonMapK extends AbstractMutableMap implements MutableMapK {
    public final SingletonMapK$entries$1 entries;
    public volatile Object value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory implements MutableMapK.Factory {
        @Override // com.android.systemui.kairos.internal.store.MutableMapK.Factory
        public final MutableMapK create(Integer num) {
            if (num == null || num.intValue() == 0 || num.intValue() == 1) {
                return new SingletonMapK();
            }
            throw new IllegalStateException(("Can't use singleton store with capacity > 1. Got: " + num).toString());
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.kairos.internal.store.SingletonMapK$entries$1] */
    public SingletonMapK(Object obj) {
        this.value = obj;
        this.entries = new AbstractMutableSet() { // from class: com.android.systemui.kairos.internal.store.SingletonMapK$entries$1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean add(Object obj2) {
                Map.Entry entry = (Map.Entry) obj2;
                boolean z = SingletonMapK.this.value != NoValue.INSTANCE;
                SingletonMapK.this.value = entry;
                return z;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean contains(Object obj2) {
                if (TypeIntrinsics.isMutableMapEntry(obj2)) {
                    return super.contains((Map.Entry) obj2);
                }
                return false;
            }

            @Override // kotlin.collections.AbstractMutableSet
            public final int getSize() {
                return SingletonMapK.this.value == NoValue.INSTANCE ? 0 : 1;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator iterator() {
                return new SingletonMapK$entries$1$iterator$1(SingletonMapK.this);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean remove(Object obj2) {
                if (TypeIntrinsics.isMutableMapEntry(obj2)) {
                    return super.remove((Map.Entry) obj2);
                }
                return false;
            }
        };
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Unit) {
            return super.containsKey((Unit) obj);
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object get(Object obj) {
        if (obj instanceof Unit) {
            return super.get((Unit) obj);
        }
        return null;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Set getEntries() {
        return this.entries;
    }

    @Override // java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof Unit) ? obj2 : super.getOrDefault((Unit) obj, obj2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        Object obj3 = this.value;
        Map.Entry entry = TypeIntrinsics.isMutableMapEntry(obj3) ? (Map.Entry) obj3 : null;
        Object value = entry != null ? entry.getValue() : null;
        this.value = new StoreEntry(Unit.INSTANCE, obj2);
        return value;
    }

    @Override // com.android.systemui.kairos.internal.store.MutableMapK
    public final MapK readOnlyCopy() {
        Object obj = this.value;
        NoValue noValue = NoValue.INSTANCE;
        Object obj2 = this.value;
        if (obj != noValue) {
            obj2 = TypeIntrinsics.asMutableMapEntry(obj2).getValue();
        }
        return new Single(obj2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object remove(Object obj) {
        if (obj instanceof Unit) {
            return super.remove((Unit) obj);
        }
        return null;
    }

    @Override // java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if (obj instanceof Unit) {
            return super.remove((Unit) obj, obj2);
        }
        return false;
    }

    public SingletonMapK() {
        this(NoValue.INSTANCE);
    }
}
