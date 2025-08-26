package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;

/* loaded from: classes4.dex */
public final class MapBuilder<K, V> implements Map<K, V>, Serializable, KMutableMap {
    public static final Companion Companion = new Companion(null);
    public static final MapBuilder Empty;
    private MapBuilderEntries entriesView;
    private int[] hashArray;
    private int hashShift;
    private boolean isReadOnly;
    private K[] keysArray;
    private MapBuilderKeys keysView;
    private int length;
    private int maxProbeDistance;
    private int modCount;
    private int[] presenceArray;
    private int size;
    private V[] valuesArray;
    private MapBuilderValues valuesView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class EntriesItr extends Itr implements Iterator, KMappedMarker {
        public EntriesItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            checkForComodification$kotlin_stdlib();
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

    public final class EntryRef implements Map.Entry, KMutableMap.Entry {
        public final int expectedModCount;
        public final int index;
        public final MapBuilder map;

        public EntryRef(MapBuilder<Object, Object> mapBuilder, int i) {
            this.map = mapBuilder;
            this.index = i;
            this.expectedModCount = ((MapBuilder) mapBuilder).modCount;
        }

        public final void checkForComodification$5() {
            if (this.map.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException("The backing map has been modified after this entry was obtained.");
            }
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return Intrinsics.areEqual(entry.getKey(), getKey()) && Intrinsics.areEqual(entry.getValue(), getValue());
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            checkForComodification$5();
            return this.map.keysArray[this.index];
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            checkForComodification$5();
            Object[] objArr = this.map.valuesArray;
            objArr.getClass();
            return objArr[this.index];
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            Object key = getKey();
            int iHashCode = key != null ? key.hashCode() : 0;
            Object value = getValue();
            return iHashCode ^ (value != null ? value.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            checkForComodification$5();
            this.map.checkIsMutable$kotlin_stdlib();
            Object[] objArrAllocateValuesArray = this.map.allocateValuesArray();
            int i = this.index;
            Object obj2 = objArrAllocateValuesArray[i];
            objArrAllocateValuesArray[i] = obj;
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

    public class Itr {
        public int expectedModCount;
        public int index;
        public int lastIndex = -1;
        public final MapBuilder map;

        public Itr(MapBuilder<Object, Object> mapBuilder) {
            this.map = mapBuilder;
            this.expectedModCount = ((MapBuilder) mapBuilder).modCount;
            initNext$kotlin_stdlib();
        }

        public final void checkForComodification$kotlin_stdlib() {
            if (this.map.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
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
            checkForComodification$kotlin_stdlib();
            if (this.lastIndex == -1) {
                throw new IllegalStateException("Call next() before removing element from the iterator.");
            }
            MapBuilder mapBuilder = this.map;
            mapBuilder.checkIsMutable$kotlin_stdlib();
            mapBuilder.removeEntryAt(this.lastIndex);
            this.lastIndex = -1;
            this.expectedModCount = mapBuilder.modCount;
        }
    }

    public final class KeysItr extends Itr implements Iterator, KMappedMarker {
        public KeysItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            checkForComodification$kotlin_stdlib();
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

    public final class ValuesItr extends Itr implements Iterator, KMappedMarker {
        public ValuesItr(MapBuilder<Object, Object> mapBuilder) {
            super(mapBuilder);
        }

        @Override // java.util.Iterator
        public final Object next() {
            checkForComodification$kotlin_stdlib();
            int i = this.index;
            MapBuilder mapBuilder = this.map;
            if (i >= mapBuilder.length) {
                throw new NoSuchElementException();
            }
            int i2 = this.index;
            this.index = i2 + 1;
            this.lastIndex = i2;
            Object[] objArr = mapBuilder.valuesArray;
            objArr.getClass();
            Object obj = objArr[this.lastIndex];
            initNext$kotlin_stdlib();
            return obj;
        }
    }

    static {
        MapBuilder mapBuilder = new MapBuilder(0);
        mapBuilder.isReadOnly = true;
        Empty = mapBuilder;
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

    private final Object writeReplace() throws NotSerializableException {
        if (this.isReadOnly) {
            return new SerializedMap(this);
        }
        throw new NotSerializableException("The map cannot be serialized while it is being built.");
    }

    public final int addKey$kotlin_stdlib(Object obj) {
        checkIsMutable$kotlin_stdlib();
        while (true) {
            int iHash = hash(obj);
            int i = this.maxProbeDistance * 2;
            int length = this.hashArray.length / 2;
            if (i > length) {
                i = length;
            }
            int i2 = 0;
            while (true) {
                int[] iArr = this.hashArray;
                int i3 = iArr[iHash];
                if (i3 <= 0) {
                    int i4 = this.length;
                    Object[] objArr = (K[]) this.keysArray;
                    if (i4 < objArr.length) {
                        int i5 = i4 + 1;
                        this.length = i5;
                        objArr[i4] = obj;
                        this.presenceArray[i4] = iHash;
                        iArr[iHash] = i5;
                        this.size++;
                        this.modCount++;
                        if (i2 > this.maxProbeDistance) {
                            this.maxProbeDistance = i2;
                        }
                        return i4;
                    }
                    ensureExtraCapacity$1(1);
                } else {
                    if (Intrinsics.areEqual(this.keysArray[i3 - 1], obj)) {
                        return -i3;
                    }
                    i2++;
                    if (i2 > i) {
                        rehash(this.hashArray.length * 2);
                        break;
                    }
                    iHash = iHash == 0 ? this.hashArray.length - 1 : iHash - 1;
                }
            }
        }
    }

    public final Object[] allocateValuesArray() {
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            return vArr;
        }
        int length = this.keysArray.length;
        if (length < 0) {
            throw new IllegalArgumentException("capacity must be non-negative.");
        }
        V[] vArr2 = (V[]) new Object[length];
        this.valuesArray = vArr2;
        return vArr2;
    }

    public final MapBuilder build() {
        checkIsMutable$kotlin_stdlib();
        this.isReadOnly = true;
        return this.size > 0 ? this : Empty;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Map
    public final void clear() {
        checkIsMutable$kotlin_stdlib();
        int i = this.length - 1;
        if (i >= 0) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.presenceArray;
                int i3 = iArr[i2];
                if (i3 >= 0) {
                    this.hashArray[i3] = 0;
                    iArr[i2] = -1;
                }
                if (i2 == i) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        ListBuilderKt.resetRange(0, this.length, this.keysArray);
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            ListBuilderKt.resetRange(0, this.length, vArr);
        }
        this.size = 0;
        this.length = 0;
        this.modCount++;
    }

    public final void compact(boolean z) {
        int i;
        V[] vArr = this.valuesArray;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.length;
            if (i2 >= i) {
                break;
            }
            int[] iArr = this.presenceArray;
            int i4 = iArr[i2];
            if (i4 >= 0) {
                K[] kArr = this.keysArray;
                kArr[i3] = kArr[i2];
                if (vArr != null) {
                    vArr[i3] = vArr[i2];
                }
                if (z) {
                    iArr[i3] = i4;
                    this.hashArray[i4] = i3 + 1;
                }
                i3++;
            }
            i2++;
        }
        ListBuilderKt.resetRange(i3, i, this.keysArray);
        if (vArr != null) {
            ListBuilderKt.resetRange(i3, this.length, vArr);
        }
        this.length = i3;
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
        int iFindKey = findKey(entry.getKey());
        if (iFindKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        return Intrinsics.areEqual(vArr[iFindKey], entry.getValue());
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return findKey(obj) >= 0;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return findValue(obj) >= 0;
    }

    public final void ensureExtraCapacity$1(int i) {
        K[] kArr = this.keysArray;
        int length = kArr.length;
        int i2 = this.length;
        int i3 = length - i2;
        int i4 = i2 - this.size;
        if (i3 < i && i3 + i4 >= i && i4 >= kArr.length / 4) {
            compact(true);
            return;
        }
        int i5 = i2 + i;
        if (i5 < 0) {
            throw new OutOfMemoryError();
        }
        if (i5 > kArr.length) {
            AbstractList.Companion companion = AbstractList.Companion;
            int length2 = kArr.length;
            companion.getClass();
            int iNewCapacity$kotlin_stdlib = AbstractList.Companion.newCapacity$kotlin_stdlib(length2, i5);
            this.keysArray = (K[]) Arrays.copyOf(this.keysArray, iNewCapacity$kotlin_stdlib);
            V[] vArr = this.valuesArray;
            this.valuesArray = vArr != null ? (V[]) Arrays.copyOf(vArr, iNewCapacity$kotlin_stdlib) : null;
            this.presenceArray = Arrays.copyOf(this.presenceArray, iNewCapacity$kotlin_stdlib);
            Companion.getClass();
            int iHighestOneBit = Integer.highestOneBit((iNewCapacity$kotlin_stdlib >= 1 ? iNewCapacity$kotlin_stdlib : 1) * 3);
            if (iHighestOneBit > this.hashArray.length) {
                rehash(iHighestOneBit);
            }
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
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        return this.size == map.size() && containsAllEntries$kotlin_stdlib(map.entrySet());
    }

    public final int findKey(Object obj) {
        int iHash = hash(obj);
        int i = this.maxProbeDistance;
        while (true) {
            int i2 = this.hashArray[iHash];
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
            iHash = iHash == 0 ? this.hashArray.length - 1 : iHash - 1;
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
                vArr.getClass();
                if (Intrinsics.areEqual(vArr[i], obj)) {
                    return i;
                }
            }
        }
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        int iFindKey = findKey(obj);
        if (iFindKey < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        return vArr[iFindKey];
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
            int iHashCode = obj != null ? obj.hashCode() : 0;
            Object[] objArr = mapBuilder.valuesArray;
            objArr.getClass();
            Object obj2 = objArr[entriesItr.lastIndex];
            int iHashCode2 = obj2 != null ? obj2.hashCode() : 0;
            entriesItr.initNext$kotlin_stdlib();
            i += iHashCode ^ iHashCode2;
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
        int iAddKey$kotlin_stdlib = addKey$kotlin_stdlib(obj);
        Object[] objArrAllocateValuesArray = allocateValuesArray();
        if (iAddKey$kotlin_stdlib >= 0) {
            objArrAllocateValuesArray[iAddKey$kotlin_stdlib] = obj2;
            return null;
        }
        int i = (-iAddKey$kotlin_stdlib) - 1;
        Object obj3 = objArrAllocateValuesArray[i];
        objArrAllocateValuesArray[i] = obj2;
        return obj3;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        checkIsMutable$kotlin_stdlib();
        Set<Map.Entry<K, V>> setEntrySet = map.entrySet();
        if (setEntrySet.isEmpty()) {
            return;
        }
        ensureExtraCapacity$1(setEntrySet.size());
        for (Map.Entry<K, V> entry : setEntrySet) {
            int iAddKey$kotlin_stdlib = addKey$kotlin_stdlib(entry.getKey());
            Object[] objArrAllocateValuesArray = allocateValuesArray();
            if (iAddKey$kotlin_stdlib >= 0) {
                objArrAllocateValuesArray[iAddKey$kotlin_stdlib] = entry.getValue();
            } else {
                int i = (-iAddKey$kotlin_stdlib) - 1;
                if (!Intrinsics.areEqual(entry.getValue(), objArrAllocateValuesArray[i])) {
                    objArrAllocateValuesArray[i] = entry.getValue();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0037, code lost:
    
        r3[r0] = r6;
        r5.presenceArray[r2] = r0;
        r2 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void rehash(int i) {
        this.modCount++;
        int i2 = 0;
        if (this.length > this.size) {
            compact(false);
        }
        this.hashArray = new int[i];
        Companion.getClass();
        this.hashShift = Integer.numberOfLeadingZeros(i) + 1;
        while (i2 < this.length) {
            int i3 = i2 + 1;
            int iHash = hash(this.keysArray[i2]);
            int i4 = this.maxProbeDistance;
            while (true) {
                int[] iArr = this.hashArray;
                if (iArr[iHash] == 0) {
                    break;
                }
                i4--;
                if (i4 < 0) {
                    throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
                }
                iHash = iHash == 0 ? iArr.length - 1 : iHash - 1;
            }
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        checkIsMutable$kotlin_stdlib();
        int iFindKey = findKey(obj);
        if (iFindKey < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        V v = vArr[iFindKey];
        removeEntryAt(iFindKey);
        return v;
    }

    public final boolean removeEntry$kotlin_stdlib(Map.Entry entry) {
        checkIsMutable$kotlin_stdlib();
        int iFindKey = findKey(entry.getKey());
        if (iFindKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        if (!Intrinsics.areEqual(vArr[iFindKey], entry.getValue())) {
            return false;
        }
        removeEntryAt(iFindKey);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0063 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[LOOP:0: B:9:0x001f->B:33:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeEntryAt(int i) {
        this.keysArray[i] = null;
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            vArr[i] = null;
        }
        int length = this.presenceArray[i];
        int i2 = this.maxProbeDistance * 2;
        int length2 = this.hashArray.length / 2;
        if (i2 > length2) {
            i2 = length2;
        }
        int i3 = i2;
        int i4 = 0;
        int i5 = length;
        while (true) {
            length = length == 0 ? this.hashArray.length - 1 : length - 1;
            i4++;
            if (i4 > this.maxProbeDistance) {
                this.hashArray[i5] = 0;
                break;
            }
            int[] iArr = this.hashArray;
            int i6 = iArr[length];
            if (i6 == 0) {
                iArr[i5] = 0;
                break;
            }
            if (i6 < 0) {
                iArr[i5] = -1;
            } else {
                int i7 = i6 - 1;
                int iHash = hash(this.keysArray[i7]) - length;
                int[] iArr2 = this.hashArray;
                if ((iHash & (iArr2.length - 1)) >= i4) {
                    iArr2[i5] = i6;
                    this.presenceArray[i7] = i5;
                }
                i3--;
                if (i3 >= 0) {
                    this.hashArray[i5] = -1;
                    break;
                }
            }
            i5 = length;
            i4 = 0;
            i3--;
            if (i3 >= 0) {
            }
        }
        this.presenceArray[i] = -1;
        this.size--;
        this.modCount++;
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
            if (obj == mapBuilder) {
                sb.append("(this Map)");
            } else {
                sb.append(obj);
            }
            sb.append('=');
            Object[] objArr = mapBuilder.valuesArray;
            objArr.getClass();
            Object obj2 = objArr[entriesItr.lastIndex];
            if (obj2 == mapBuilder) {
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
    public MapBuilder(int i) {
        if (i >= 0) {
            Object[] objArr = new Object[i];
            int[] iArr = new int[i];
            Companion.getClass();
            this(objArr, null, iArr, new int[Integer.highestOneBit((i < 1 ? 1 : i) * 3)], 2, 0);
            return;
        }
        throw new IllegalArgumentException("capacity must be non-negative.");
    }
}
