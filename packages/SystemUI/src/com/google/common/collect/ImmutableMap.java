package com.google.common.collect;

import com.android.settingslib.notification.modes.ZenIcon;
import com.google.common.collect.ImmutableCollection;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    private static final long serialVersionUID = 912559;
    public transient ImmutableSet entrySet;
    public transient ImmutableSet keySet;
    public transient ImmutableCollection values;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public Object[] alternatingKeysAndValues;
        public DuplicateKey duplicateKey;
        public int size;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class DuplicateKey {
            public final Object key;
            public final Object value1;
            public final Object value2;

            public DuplicateKey(Object obj, Object obj2, Object obj3) {
                this.key = obj;
                this.value1 = obj2;
                this.value2 = obj3;
            }

            public final IllegalArgumentException exception() {
                StringBuilder sb = new StringBuilder("Multiple entries with same key: ");
                Object obj = this.key;
                sb.append(obj);
                sb.append("=");
                sb.append(this.value1);
                sb.append(" and ");
                sb.append(obj);
                sb.append("=");
                sb.append(this.value2);
                return new IllegalArgumentException(sb.toString());
            }
        }

        public Builder() {
            this(4);
        }

        public final ImmutableMap buildOrThrow() {
            DuplicateKey duplicateKey = this.duplicateKey;
            if (duplicateKey != null) {
                throw duplicateKey.exception();
            }
            RegularImmutableMap create = RegularImmutableMap.create(this.size, this.alternatingKeysAndValues, this);
            DuplicateKey duplicateKey2 = this.duplicateKey;
            if (duplicateKey2 == null) {
                return create;
            }
            throw duplicateKey2.exception();
        }

        public final void put(Object obj, Object obj2) {
            int i = (this.size + 1) * 2;
            Object[] objArr = this.alternatingKeysAndValues;
            if (i > objArr.length) {
                this.alternatingKeysAndValues = Arrays.copyOf(objArr, ImmutableCollection.Builder.expandedCapacity(objArr.length, i));
            }
            if (obj == null) {
                throw new NullPointerException("null key in entry: null=" + obj2);
            }
            if (obj2 == null) {
                throw new NullPointerException("null value in entry: " + obj + "=null");
            }
            Object[] objArr2 = this.alternatingKeysAndValues;
            int i2 = this.size;
            int i3 = i2 * 2;
            objArr2[i3] = obj;
            objArr2[i3 + 1] = obj2;
            this.size = i2 + 1;
        }

        public Builder(int i) {
            this.alternatingKeysAndValues = new Object[i * 2];
            this.size = 0;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class SerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object keys;
        private final Object values;

        public SerializedForm(ImmutableMap<K, V> immutableMap) {
            Object[] objArr = new Object[immutableMap.size()];
            Object[] objArr2 = new Object[immutableMap.size()];
            ImmutableSet immutableSet = immutableMap.entrySet;
            if (immutableSet == null) {
                immutableSet = immutableMap.createEntrySet();
                immutableMap.entrySet = immutableSet;
            }
            UnmodifiableIterator it = immutableSet.iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                objArr[i] = entry.getKey();
                objArr2[i] = entry.getValue();
                i++;
            }
            this.keys = objArr;
            this.values = objArr2;
        }

        public final Object readResolve() {
            Object obj = this.keys;
            if (obj instanceof ImmutableSet) {
                ImmutableSet immutableSet = (ImmutableSet) obj;
                ImmutableCollection immutableCollection = (ImmutableCollection) this.values;
                Builder builder = new Builder(immutableSet.size());
                UnmodifiableIterator it = immutableSet.iterator();
                UnmodifiableIterator it2 = immutableCollection.iterator();
                while (it.hasNext()) {
                    builder.put(it.next(), it2.next());
                }
                return builder.buildOrThrow();
            }
            Object[] objArr = (Object[]) obj;
            Object[] objArr2 = (Object[]) this.values;
            Builder builder2 = new Builder(objArr.length);
            for (int i = 0; i < objArr.length; i++) {
                builder2.put(objArr[i], objArr2[i]);
            }
            return builder2.buildOrThrow();
        }
    }

    static {
        Map.Entry[] entryArr = new Map.Entry[0];
    }

    public static ImmutableMap of(ZenIcon.Key key, ZenIcon.Key key2, ZenIcon.Key key3, ZenIcon.Key key4, ZenIcon.Key key5, ZenIcon.Key key6, ZenIcon.Key key7, ZenIcon.Key key8, ZenIcon.Key key9) {
        return RegularImmutableMap.create(9, new Object[]{-1, key, 0, key2, 1, key3, 2, key4, 3, key5, 4, key6, 5, key7, 6, key8, 7, key9}, null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        ImmutableCollection immutableCollection = this.values;
        if (immutableCollection == null) {
            immutableCollection = createValues();
            this.values = immutableCollection;
        }
        return immutableCollection.contains(obj);
    }

    public abstract ImmutableSet createEntrySet();

    public abstract ImmutableSet createKeySet();

    public abstract ImmutableCollection createValues();

    @Override // java.util.Map
    public final Set entrySet() {
        ImmutableSet immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    public abstract Object get(Object obj);

    @Override // java.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public final int hashCode() {
        ImmutableSet immutableSet = this.entrySet;
        if (immutableSet == null) {
            immutableSet = createEntrySet();
            this.entrySet = immutableSet;
        }
        Iterator<E> it = immutableSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        ImmutableSet immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        CollectPreconditions.checkNonnegative(size, "size");
        StringBuilder sb = new StringBuilder((int) Math.min(size * 8, 1073741824L));
        sb.append('{');
        boolean z = true;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Map
    public final Collection values() {
        ImmutableCollection immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
