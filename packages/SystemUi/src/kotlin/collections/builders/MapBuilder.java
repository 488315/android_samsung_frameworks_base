package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MapBuilder<K, V> implements Map<K, V>, Serializable, KMutableMap {
    public static final Companion Companion = new Companion(null);
    private MapBuilderEntries entriesView;
    private int[] hashArray;
    private int hashShift;
    private boolean isReadOnly;
    private K[] keysArray;
    private MapBuilderKeys keysView;
    private int length;
    private int maxProbeDistance;
    private int[] presenceArray;
    private int size;
    private V[] valuesArray;
    private MapBuilderValues valuesView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EntriesItr extends Itr implements Iterator, KMappedMarker {
        public EntriesItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            int i = this.index;
            MapBuilder mapBuilder = this.map;
            if (i >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i2 = this.index;
            this.index = i2 + 1;
            this.lastIndex = i2;
            EntryRef entryRef = new EntryRef(mapBuilder, i2);
            initNext$kotlin_stdlib();
            return entryRef;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EntryRef implements Map.Entry, KMappedMarker {
        public final int index;
        public final MapBuilder map;

        public EntryRef(MapBuilder<Object, Object> mapBuilder, int i) {
            this.map = mapBuilder;
            this.index = i;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                if (Intrinsics.areEqual(entry.getKey(), getKey()) && Intrinsics.areEqual(entry.getValue(), getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.map.keysArray[this.index];
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            Object[] objArr = this.map.valuesArray;
            Intrinsics.checkNotNull(objArr);
            return objArr[this.index];
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            Object key = getKey();
            int hashCode = key != null ? key.hashCode() : 0;
            Object value = getValue();
            return hashCode ^ (value != null ? value.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            this.map.checkIsMutable$kotlin_stdlib();
            Object[] allocateValuesArray = this.map.allocateValuesArray();
            int i = this.index;
            Object obj2 = allocateValuesArray[i];
            allocateValuesArray[i] = obj;
            return obj2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append('=');
            sb.append(getValue());
            return sb.toString();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Itr {
        public int index;
        public int lastIndex = -1;
        public final MapBuilder map;

        public Itr(MapBuilder<Object, Object> mapBuilder) {
            this.map = mapBuilder;
            initNext$kotlin_stdlib();
        }

        public final boolean hasNext() {
            return this.index < this.map.length;
        }

        public final void initNext$kotlin_stdlib() {
            while (true) {
                int i = this.index;
                MapBuilder mapBuilder = this.map;
                if (i >= mapBuilder.length) {
                    return;
                }
                int[] iArr = mapBuilder.presenceArray;
                int i2 = this.index;
                if (iArr[i2] >= 0) {
                    return;
                } else {
                    this.index = i2 + 1;
                }
            }
        }

        public final void remove() {
            if (!(this.lastIndex != -1)) {
                throw new IllegalStateException("Call next() before removing element from the iterator.".toString());
            }
            MapBuilder mapBuilder = this.map;
            mapBuilder.checkIsMutable$kotlin_stdlib();
            mapBuilder.removeKeyAt(this.lastIndex);
            this.lastIndex = -1;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeysItr extends Itr implements Iterator, KMappedMarker {
        public KeysItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            int i = this.index;
            MapBuilder mapBuilder = this.map;
            if (i >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i2 = this.index;
            this.index = i2 + 1;
            this.lastIndex = i2;
            Object obj = mapBuilder.keysArray[this.lastIndex];
            initNext$kotlin_stdlib();
            return obj;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ValuesItr extends Itr implements Iterator, KMappedMarker {
        public ValuesItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            int i = this.index;
            MapBuilder mapBuilder = this.map;
            if (i >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i2 = this.index;
            this.index = i2 + 1;
            this.lastIndex = i2;
            Object[] objArr = mapBuilder.valuesArray;
            Intrinsics.checkNotNull(objArr);
            Object obj = objArr[this.lastIndex];
            initNext$kotlin_stdlib();
            return obj;
        }
    }

    private MapBuilder(K[] kArr, V[] vArr, int[] iArr, int[] iArr2, int i, int i2) {
        this.keysArray = kArr;
        this.valuesArray = vArr;
        this.presenceArray = iArr;
        this.hashArray = iArr2;
        this.maxProbeDistance = i;
        this.length = i2;
        Companion companion = Companion;
        int length = iArr2.length;
        companion.getClass();
        this.hashShift = Integer.numberOfLeadingZeros(length) + 1;
    }

    private final Object writeReplace() {
        if (this.isReadOnly) {
            return new SerializedMap(this);
        }
        throw new NotSerializableException("The map cannot be serialized while it is being built.");
    }

    public final int addKey$kotlin_stdlib(Object obj) {
        checkIsMutable$kotlin_stdlib();
        while (true) {
            int hash = hash(obj);
            int i = this.maxProbeDistance * 2;
            int length = this.hashArray.length / 2;
            if (i > length) {
                i = length;
            }
            int i2 = 0;
            while (true) {
                int[] iArr = this.hashArray;
                int i3 = iArr[hash];
                if (i3 <= 0) {
                    int i4 = this.length;
                    Object[] objArr = (K[]) this.keysArray;
                    if (i4 < objArr.length) {
                        int i5 = i4 + 1;
                        this.length = i5;
                        objArr[i4] = obj;
                        this.presenceArray[i4] = hash;
                        iArr[hash] = i5;
                        this.size++;
                        if (i2 > this.maxProbeDistance) {
                            this.maxProbeDistance = i2;
                        }
                        return i4;
                    }
                    ensureExtraCapacity(1);
                } else {
                    if (Intrinsics.areEqual(this.keysArray[i3 - 1], obj)) {
                        return -i3;
                    }
                    i2++;
                    if (i2 > i) {
                        rehash(this.hashArray.length * 2);
                        break;
                    }
                    hash = hash == 0 ? this.hashArray.length - 1 : hash - 1;
                }
            }
        }
    }

    public final Object[] allocateValuesArray() {
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            return vArr;
        }
        V[] vArr2 = (V[]) ListBuilderKt.arrayOfUninitializedElements(this.keysArray.length);
        this.valuesArray = vArr2;
        return vArr2;
    }

    public final void build$1() {
        checkIsMutable$kotlin_stdlib();
        this.isReadOnly = true;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Map
    public final void clear() {
        checkIsMutable$kotlin_stdlib();
        IntProgressionIterator it = new IntRange(0, this.length - 1).iterator();
        while (it.hasNext) {
            int nextInt = it.nextInt();
            int[] iArr = this.presenceArray;
            int i = iArr[nextInt];
            if (i >= 0) {
                this.hashArray[i] = 0;
                iArr[nextInt] = -1;
            }
        }
        ListBuilderKt.resetRange(0, this.length, this.keysArray);
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            ListBuilderKt.resetRange(0, this.length, vArr);
        }
        this.size = 0;
        this.length = 0;
    }

    public final boolean containsAllEntries$kotlin_stdlib(Collection collection) {
        for (Object obj : collection) {
            if (obj != null) {
                try {
                    if (!containsEntry$kotlin_stdlib((Map.Entry) obj)) {
                    }
                } catch (ClassCastException unused) {
                }
            }
            return false;
        }
        return true;
    }

    public final boolean containsEntry$kotlin_stdlib(Map.Entry entry) {
        int findKey = findKey(entry.getKey());
        if (findKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        return Intrinsics.areEqual(vArr[findKey], entry.getValue());
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return findKey(obj) >= 0;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return findValue(obj) >= 0;
    }

    public final void ensureExtraCapacity(int i) {
        int i2 = this.length;
        int i3 = i + i2;
        if (i3 < 0) {
            throw new OutOfMemoryError();
        }
        K[] kArr = this.keysArray;
        if (i3 <= kArr.length) {
            if ((i2 + i3) - this.size > kArr.length) {
                rehash(this.hashArray.length);
                return;
            }
            return;
        }
        int length = (kArr.length * 3) / 2;
        if (i3 <= length) {
            i3 = length;
        }
        this.keysArray = (K[]) Arrays.copyOf(kArr, i3);
        V[] vArr = this.valuesArray;
        this.valuesArray = vArr != null ? (V[]) Arrays.copyOf(vArr, i3) : null;
        this.presenceArray = Arrays.copyOf(this.presenceArray, i3);
        Companion.getClass();
        if (i3 < 1) {
            i3 = 1;
        }
        int highestOneBit = Integer.highestOneBit(i3 * 3);
        if (highestOneBit > this.hashArray.length) {
            rehash(highestOneBit);
        }
    }

    @Override // java.util.Map
    public final Set entrySet() {
        MapBuilderEntries mapBuilderEntries = this.entriesView;
        if (mapBuilderEntries != null) {
            return mapBuilderEntries;
        }
        MapBuilderEntries mapBuilderEntries2 = new MapBuilderEntries(this);
        this.entriesView = mapBuilderEntries2;
        return mapBuilderEntries2;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (this.size == map.size() && containsAllEntries$kotlin_stdlib(map.entrySet())) {
                return true;
            }
        }
        return false;
    }

    public final int findKey(Object obj) {
        int hash = hash(obj);
        int i = this.maxProbeDistance;
        while (true) {
            int i2 = this.hashArray[hash];
            if (i2 == 0) {
                return -1;
            }
            if (i2 > 0) {
                int i3 = i2 - 1;
                if (Intrinsics.areEqual(this.keysArray[i3], obj)) {
                    return i3;
                }
            }
            i--;
            if (i < 0) {
                return -1;
            }
            hash = hash == 0 ? this.hashArray.length - 1 : hash - 1;
        }
    }

    public final int findValue(Object obj) {
        int i = this.length;
        while (true) {
            i--;
            if (i < 0) {
                return -1;
            }
            if (this.presenceArray[i] >= 0) {
                V[] vArr = this.valuesArray;
                Intrinsics.checkNotNull(vArr);
                if (Intrinsics.areEqual(vArr[i], obj)) {
                    return i;
                }
            }
        }
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        int findKey = findKey(obj);
        if (findKey < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        return vArr[findKey];
    }

    public final int hash(Object obj) {
        return ((obj != null ? obj.hashCode() : 0) * (-1640531527)) >>> this.hashShift;
    }

    @Override // java.util.Map
    public final int hashCode() {
        EntriesItr entriesItr = new EntriesItr(this);
        int i = 0;
        while (entriesItr.hasNext()) {
            int i2 = entriesItr.index;
            MapBuilder mapBuilder = entriesItr.map;
            if (i2 >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i3 = entriesItr.index;
            entriesItr.index = i3 + 1;
            entriesItr.lastIndex = i3;
            Object obj = mapBuilder.keysArray[entriesItr.lastIndex];
            int hashCode = obj != null ? obj.hashCode() : 0;
            Object[] objArr = mapBuilder.valuesArray;
            Intrinsics.checkNotNull(objArr);
            Object obj2 = objArr[entriesItr.lastIndex];
            int hashCode2 = obj2 != null ? obj2.hashCode() : 0;
            entriesItr.initNext$kotlin_stdlib();
            i += hashCode ^ hashCode2;
        }
        return i;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.size == 0;
    }

    public final boolean isReadOnly$kotlin_stdlib() {
        return this.isReadOnly;
    }

    @Override // java.util.Map
    public final Set keySet() {
        MapBuilderKeys mapBuilderKeys = this.keysView;
        if (mapBuilderKeys != null) {
            return mapBuilderKeys;
        }
        MapBuilderKeys mapBuilderKeys2 = new MapBuilderKeys(this);
        this.keysView = mapBuilderKeys2;
        return mapBuilderKeys2;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        checkIsMutable$kotlin_stdlib();
        int addKey$kotlin_stdlib = addKey$kotlin_stdlib(obj);
        Object[] allocateValuesArray = allocateValuesArray();
        if (addKey$kotlin_stdlib >= 0) {
            allocateValuesArray[addKey$kotlin_stdlib] = obj2;
            return null;
        }
        int i = (-addKey$kotlin_stdlib) - 1;
        Object obj3 = allocateValuesArray[i];
        allocateValuesArray[i] = obj2;
        return obj3;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        checkIsMutable$kotlin_stdlib();
        Set<Map.Entry<K, V>> entrySet = map.entrySet();
        if (entrySet.isEmpty()) {
            return;
        }
        ensureExtraCapacity(entrySet.size());
        for (Map.Entry<K, V> entry : entrySet) {
            int addKey$kotlin_stdlib = addKey$kotlin_stdlib(entry.getKey());
            Object[] allocateValuesArray = allocateValuesArray();
            if (addKey$kotlin_stdlib >= 0) {
                allocateValuesArray[addKey$kotlin_stdlib] = entry.getValue();
            } else {
                int i = (-addKey$kotlin_stdlib) - 1;
                if (!Intrinsics.areEqual(entry.getValue(), allocateValuesArray[i])) {
                    allocateValuesArray[i] = entry.getValue();
                }
            }
        }
    }

    public final void rehash(int i) {
        boolean z;
        int i2;
        if (this.length > this.size) {
            V[] vArr = this.valuesArray;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                i2 = this.length;
                if (i3 >= i2) {
                    break;
                }
                if (this.presenceArray[i3] >= 0) {
                    K[] kArr = this.keysArray;
                    kArr[i4] = kArr[i3];
                    if (vArr != null) {
                        vArr[i4] = vArr[i3];
                    }
                    i4++;
                }
                i3++;
            }
            ListBuilderKt.resetRange(i4, i2, this.keysArray);
            if (vArr != null) {
                ListBuilderKt.resetRange(i4, this.length, vArr);
            }
            this.length = i4;
        }
        int[] iArr = this.hashArray;
        if (i != iArr.length) {
            this.hashArray = new int[i];
            Companion.getClass();
            this.hashShift = Integer.numberOfLeadingZeros(i) + 1;
        } else {
            Arrays.fill(iArr, 0, iArr.length, 0);
        }
        int i5 = 0;
        while (i5 < this.length) {
            int i6 = i5 + 1;
            int hash = hash(this.keysArray[i5]);
            int i7 = this.maxProbeDistance;
            while (true) {
                int[] iArr2 = this.hashArray;
                if (iArr2[hash] == 0) {
                    iArr2[hash] = i6;
                    this.presenceArray[i5] = hash;
                    z = true;
                    break;
                } else {
                    i7--;
                    if (i7 < 0) {
                        z = false;
                        break;
                    }
                    hash = hash == 0 ? iArr2.length - 1 : hash - 1;
                }
            }
            if (!z) {
                throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
            }
            i5 = i6;
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        checkIsMutable$kotlin_stdlib();
        int findKey = findKey(obj);
        if (findKey < 0) {
            findKey = -1;
        } else {
            removeKeyAt(findKey);
        }
        if (findKey < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        V v = vArr[findKey];
        vArr[findKey] = null;
        return v;
    }

    public final boolean removeEntry$kotlin_stdlib(Map.Entry entry) {
        checkIsMutable$kotlin_stdlib();
        int findKey = findKey(entry.getKey());
        if (findKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        if (!Intrinsics.areEqual(vArr[findKey], entry.getValue())) {
            return false;
        }
        removeKeyAt(findKey);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[LOOP:0: B:5:0x0019->B:22:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeKeyAt(int i) {
        this.keysArray[i] = null;
        int i2 = this.presenceArray[i];
        int i3 = this.maxProbeDistance * 2;
        int length = this.hashArray.length / 2;
        if (i3 > length) {
            i3 = length;
        }
        int i4 = i3;
        int i5 = 0;
        int i6 = i2;
        while (true) {
            i2 = i2 == 0 ? this.hashArray.length - 1 : i2 - 1;
            i5++;
            if (i5 > this.maxProbeDistance) {
                this.hashArray[i6] = 0;
                break;
            }
            int[] iArr = this.hashArray;
            int i7 = iArr[i2];
            if (i7 == 0) {
                iArr[i6] = 0;
                break;
            }
            if (i7 < 0) {
                iArr[i6] = -1;
            } else {
                int i8 = i7 - 1;
                int hash = hash(this.keysArray[i8]) - i2;
                int[] iArr2 = this.hashArray;
                if ((hash & (iArr2.length - 1)) >= i5) {
                    iArr2[i6] = i7;
                    this.presenceArray[i8] = i6;
                }
                i4--;
                if (i4 >= 0) {
                    this.hashArray[i6] = -1;
                    break;
                }
            }
            i6 = i2;
            i5 = 0;
            i4--;
            if (i4 >= 0) {
            }
        }
        this.presenceArray[i] = -1;
        this.size--;
    }

    @Override // java.util.Map
    public final int size() {
        return this.size;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((this.size * 3) + 2);
        sb.append("{");
        EntriesItr entriesItr = new EntriesItr(this);
        int i = 0;
        while (entriesItr.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            int i2 = entriesItr.index;
            MapBuilder mapBuilder = entriesItr.map;
            if (i2 >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i3 = entriesItr.index;
            entriesItr.index = i3 + 1;
            entriesItr.lastIndex = i3;
            Object obj = mapBuilder.keysArray[entriesItr.lastIndex];
            if (Intrinsics.areEqual(obj, mapBuilder)) {
                sb.append("(this Map)");
            } else {
                sb.append(obj);
            }
            sb.append('=');
            Object[] objArr = mapBuilder.valuesArray;
            Intrinsics.checkNotNull(objArr);
            Object obj2 = objArr[entriesItr.lastIndex];
            if (Intrinsics.areEqual(obj2, mapBuilder)) {
                sb.append("(this Map)");
            } else {
                sb.append(obj2);
            }
            entriesItr.initNext$kotlin_stdlib();
            i++;
        }
        sb.append("}");
        return sb.toString();
    }

    @Override // java.util.Map
    public final Collection values() {
        MapBuilderValues mapBuilderValues = this.valuesView;
        if (mapBuilderValues != null) {
            return mapBuilderValues;
        }
        MapBuilderValues mapBuilderValues2 = new MapBuilderValues(this);
        this.valuesView = mapBuilderValues2;
        return mapBuilderValues2;
    }

    public MapBuilder() {
        this(8);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MapBuilder(int i) {
        this(r1, null, r3, new int[Integer.highestOneBit((i < 1 ? 1 : i) * 3)], 2, 0);
        Object[] arrayOfUninitializedElements = ListBuilderKt.arrayOfUninitializedElements(i);
        int[] iArr = new int[i];
        Companion.getClass();
    }
}
