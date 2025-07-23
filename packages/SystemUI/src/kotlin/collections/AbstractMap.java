package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractMap implements Map, KMappedMarker {
    public static final /* synthetic */ int $r8$clinit = 0;
    public volatile AbstractMap$keys$1 _keys;
    public volatile AbstractMap$values$1 _values;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return implFindEntry(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        Set entries = getEntries();
        if ((entries instanceof Collection) && entries.isEmpty()) {
            return false;
        }
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Map.Entry) it.next()).getValue(), obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ Set entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (getSize() != map.size()) {
            return false;
        }
        Set<Map.Entry> entrySet = map.entrySet();
        if ((entrySet instanceof Collection) && entrySet.isEmpty()) {
            return true;
        }
        for (Map.Entry entry : entrySet) {
            if (entry != null) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                Object obj2 = get(key);
                if (Intrinsics.areEqual(value, obj2) && (obj2 != null || containsKey(key))) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        Map.Entry implFindEntry = implFindEntry(obj);
        if (implFindEntry != null) {
            return implFindEntry.getValue();
        }
        return null;
    }

    public abstract Set getEntries();

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.collections.AbstractMap$keys$1] */
    public Set getKeys() {
        if (this._keys == null) {
            this._keys = new AbstractSet() { // from class: kotlin.collections.AbstractMap$keys$1
                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
                public final boolean contains(Object obj) {
                    return AbstractMap.this.containsKey(obj);
                }

                @Override // kotlin.collections.AbstractCollection
                public final int getSize() {
                    return AbstractMap.this.getSize();
                }

                @Override // kotlin.collections.AbstractSet, java.util.Collection, java.lang.Iterable, java.util.Set
                public final Iterator iterator() {
                    return new AbstractMap$keys$1$iterator$1(AbstractMap.this.getEntries().iterator());
                }
            };
        }
        AbstractMap$keys$1 abstractMap$keys$1 = this._keys;
        abstractMap$keys$1.getClass();
        return abstractMap$keys$1;
    }

    public int getSize() {
        return getEntries().size();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.collections.AbstractMap$values$1] */
    public Collection getValues() {
        if (this._values == null) {
            this._values = new AbstractCollection() { // from class: kotlin.collections.AbstractMap$values$1
                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
                public final boolean contains(Object obj) {
                    return AbstractMap.this.containsValue(obj);
                }

                @Override // kotlin.collections.AbstractCollection
                public final int getSize() {
                    return AbstractMap.this.getSize();
                }

                @Override // java.util.Collection, java.lang.Iterable
                public final Iterator iterator() {
                    return new AbstractMap$values$1$iterator$1(AbstractMap.this.getEntries().iterator());
                }
            };
        }
        AbstractMap$values$1 abstractMap$values$1 = this._values;
        abstractMap$values$1.getClass();
        return abstractMap$values$1;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return getEntries().hashCode();
    }

    public final Map.Entry implFindEntry(Object obj) {
        Object obj2;
        Iterator it = getEntries().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            if (Intrinsics.areEqual(((Map.Entry) obj2).getKey(), obj)) {
                break;
            }
        }
        return (Map.Entry) obj2;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return getSize() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ Set keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(getEntries(), ", ", "{", "}", new Function1() { // from class: kotlin.collections.AbstractMap$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                int i = AbstractMap.$r8$clinit;
                AbstractMap abstractMap = AbstractMap.this;
                StringBuilder sb = new StringBuilder();
                Object key = entry.getKey();
                sb.append(key == abstractMap ? "(this Map)" : String.valueOf(key));
                sb.append('=');
                Object value = entry.getValue();
                sb.append(value != abstractMap ? String.valueOf(value) : "(this Map)");
                return sb.toString();
            }
        }, 24);
    }

    @Override // java.util.Map
    public final /* bridge */ Collection values() {
        return getValues();
    }
}
