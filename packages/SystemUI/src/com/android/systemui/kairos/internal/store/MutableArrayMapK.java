package com.android.systemui.kairos.internal.store;

import com.android.systemui.kairos.internal.store.MutableMapK;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: classes2.dex */
public final class MutableArrayMapK extends AbstractMutableMap implements MutableMapK {
    public final MutableArrayMapK$entries$1 entries;
    public final AtomicReferenceArray storage;

    public final class Factory implements MutableMapK.Factory {
        @Override // com.android.systemui.kairos.internal.store.MutableMapK.Factory
        public final MutableMapK create(Integer num) {
            if (num != null) {
                return new MutableArrayMapK(num.intValue());
            }
            throw new IllegalStateException("Cannot use ArrayMapK with null capacity.");
        }
    }

    public /* synthetic */ MutableArrayMapK(AtomicReferenceArray atomicReferenceArray, DefaultConstructorMarker defaultConstructorMarker) {
        this((AtomicReferenceArray<Map.Entry<Integer, Object>>) atomicReferenceArray);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof Integer) {
            return super.containsKey(Integer.valueOf(((Number) obj).intValue()));
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object get(Object obj) {
        if (obj instanceof Integer) {
            return super.get(Integer.valueOf(((Number) obj).intValue()));
        }
        return null;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public final Set getEntries() {
        return this.entries;
    }

    @Override // java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof Integer) ? obj2 : super.getOrDefault(Integer.valueOf(((Number) obj).intValue()), obj2);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int iIntValue = ((Number) obj).intValue();
        Map.Entry entry = (Map.Entry) this.storage.get(iIntValue);
        Object value = entry != null ? entry.getValue() : null;
        this.storage.set(iIntValue, new StoreEntry(Integer.valueOf(iIntValue), obj2));
        return value;
    }

    @Override // com.android.systemui.kairos.internal.store.MutableMapK
    public final MapK readOnlyCopy() {
        int length = this.storage.length();
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        for (int i = 0; i < length; i++) {
            Map.Entry entry = (Map.Entry) this.storage.get(i);
            if (entry != null) {
                listBuilderCreateListBuilder.add(new StoreEntry(entry.getKey(), entry.getValue()));
            }
        }
        return new ArrayMapK(listBuilderCreateListBuilder.build(), length);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object remove(Object obj) {
        if (obj instanceof Integer) {
            return super.remove(Integer.valueOf(((Number) obj).intValue()));
        }
        return null;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.kairos.internal.store.MutableArrayMapK$entries$1] */
    private MutableArrayMapK(AtomicReferenceArray<Map.Entry<Integer, Object>> atomicReferenceArray) {
        this.storage = atomicReferenceArray;
        this.entries = new AbstractMutableSet() { // from class: com.android.systemui.kairos.internal.store.MutableArrayMapK$entries$1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean add(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                boolean z = this.this$0.storage.get(((Number) entry.getKey()).intValue()) != null;
                this.this$0.storage.set(((Number) entry.getKey()).intValue(), entry);
                return z;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean contains(Object obj) {
                if (TypeIntrinsics.isMutableMapEntry(obj)) {
                    return super.contains((Map.Entry) obj);
                }
                return false;
            }

            @Override // kotlin.collections.AbstractMutableSet
            public final int getSize() {
                MutableArrayMapK mutableArrayMapK = this.this$0;
                int length = mutableArrayMapK.storage.length();
                int i = 0;
                for (int i2 = 0; i2 < length; i2++) {
                    if (((Map.Entry) mutableArrayMapK.storage.get(i2)) != null) {
                        i++;
                    }
                }
                return i;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator iterator() {
                return new MutableArrayMapK$entries$1$iterator$1(this.this$0);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final /* bridge */ boolean remove(Object obj) {
                if (TypeIntrinsics.isMutableMapEntry(obj)) {
                    return super.remove((Map.Entry) obj);
                }
                return false;
            }
        };
    }

    @Override // java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if (obj instanceof Integer) {
            return super.remove(Integer.valueOf(((Number) obj).intValue()), obj2);
        }
        return false;
    }

    public MutableArrayMapK(int i) {
        this((AtomicReferenceArray<Map.Entry<Integer, Object>>) new AtomicReferenceArray(i));
    }
}
