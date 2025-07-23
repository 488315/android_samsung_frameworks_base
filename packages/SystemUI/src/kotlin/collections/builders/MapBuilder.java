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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = key != null ? key.hashCode() : 0;
            Object value = getValue();
            return hashCode ^ (value != null ? value.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            checkForComodification$5();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int findKey = findKey(entry.getKey());
        if (findKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
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
            int newCapacity$kotlin_stdlib = AbstractList.Companion.newCapacity$kotlin_stdlib(length2, i5);
            this.keysArray = (K[]) Arrays.copyOf(this.keysArray, newCapacity$kotlin_stdlib);
            V[] vArr = this.valuesArray;
            this.valuesArray = vArr != null ? (V[]) Arrays.copyOf(vArr, newCapacity$kotlin_stdlib) : null;
            this.presenceArray = Arrays.copyOf(this.presenceArray, newCapacity$kotlin_stdlib);
            Companion.getClass();
            int highestOneBit = Integer.highestOneBit((newCapacity$kotlin_stdlib >= 1 ? newCapacity$kotlin_stdlib : 1) * 3);
            if (highestOneBit > this.hashArray.length) {
                rehash(highestOneBit);
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
                vArr.getClass();
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
        vArr.getClass();
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
            objArr.getClass();
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
        ensureExtraCapacity$1(entrySet.size());
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0037, code lost:
    
        r3[r0] = r6;
        r5.presenceArray[r2] = r0;
        r2 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void rehash(int r6) {
        /*
            r5 = this;
            int r0 = r5.modCount
            int r0 = r0 + 1
            r5.modCount = r0
            int r0 = r5.length
            int r1 = r5.size
            r2 = 0
            if (r0 <= r1) goto L10
            r5.compact(r2)
        L10:
            int[] r0 = new int[r6]
            r5.hashArray = r0
            kotlin.collections.builders.MapBuilder$Companion r0 = kotlin.collections.builders.MapBuilder.Companion
            r0.getClass()
            int r6 = java.lang.Integer.numberOfLeadingZeros(r6)
            int r6 = r6 + 1
            r5.hashShift = r6
        L21:
            int r6 = r5.length
            if (r2 >= r6) goto L55
            int r6 = r2 + 1
            K[] r0 = r5.keysArray
            r0 = r0[r2]
            int r0 = r5.hash(r0)
            int r1 = r5.maxProbeDistance
        L31:
            int[] r3 = r5.hashArray
            r4 = r3[r0]
            if (r4 != 0) goto L3f
            r3[r0] = r6
            int[] r1 = r5.presenceArray
            r1[r2] = r0
            r2 = r6
            goto L21
        L3f:
            int r1 = r1 + (-1)
            if (r1 < 0) goto L4d
            int r4 = r0 + (-1)
            if (r0 != 0) goto L4b
            int r0 = r3.length
            int r0 = r0 + (-1)
            goto L31
        L4b:
            r0 = r4
            goto L31
        L4d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?"
            r5.<init>(r6)
            throw r5
        L55:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.builders.MapBuilder.rehash(int):void");
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        checkIsMutable$kotlin_stdlib();
        int findKey = findKey(obj);
        if (findKey < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        V v = vArr[findKey];
        removeEntryAt(findKey);
        return v;
    }

    public final boolean removeEntry$kotlin_stdlib(Map.Entry entry) {
        checkIsMutable$kotlin_stdlib();
        int findKey = findKey(entry.getKey());
        if (findKey < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        vArr.getClass();
        if (!Intrinsics.areEqual(vArr[findKey], entry.getValue())) {
            return false;
        }
        removeEntryAt(findKey);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0063 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[LOOP:0: B:8:0x001f->B:25:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeEntryAt(int r12) {
        /*
            r11 = this;
            K[] r0 = r11.keysArray
            r1 = 0
            r0[r12] = r1
            V[] r0 = r11.valuesArray
            if (r0 == 0) goto Lb
            r0[r12] = r1
        Lb:
            int[] r0 = r11.presenceArray
            r0 = r0[r12]
            int r1 = r11.maxProbeDistance
            int r1 = r1 * 2
            int[] r2 = r11.hashArray
            int r2 = r2.length
            int r2 = r2 / 2
            if (r1 <= r2) goto L1b
            r1 = r2
        L1b:
            r2 = 0
            r3 = r1
            r4 = r2
            r1 = r0
        L1f:
            int r5 = r0 + (-1)
            if (r0 != 0) goto L29
            int[] r0 = r11.hashArray
            int r0 = r0.length
            int r0 = r0 + (-1)
            goto L2a
        L29:
            r0 = r5
        L2a:
            int r4 = r4 + 1
            int r5 = r11.maxProbeDistance
            r6 = -1
            if (r4 <= r5) goto L36
            int[] r0 = r11.hashArray
            r0[r1] = r2
            goto L67
        L36:
            int[] r5 = r11.hashArray
            r7 = r5[r0]
            if (r7 != 0) goto L3f
            r5[r1] = r2
            goto L67
        L3f:
            if (r7 >= 0) goto L46
            r5[r1] = r6
        L43:
            r1 = r0
            r4 = r2
            goto L60
        L46:
            K[] r5 = r11.keysArray
            int r8 = r7 + (-1)
            r5 = r5[r8]
            int r5 = r11.hash(r5)
            int r5 = r5 - r0
            int[] r9 = r11.hashArray
            int r10 = r9.length
            int r10 = r10 + (-1)
            r5 = r5 & r10
            if (r5 < r4) goto L60
            r9[r1] = r7
            int[] r4 = r11.presenceArray
            r4[r8] = r1
            goto L43
        L60:
            int r3 = r3 + r6
            if (r3 >= 0) goto L1f
            int[] r0 = r11.hashArray
            r0[r1] = r6
        L67:
            int[] r0 = r11.presenceArray
            r0[r12] = r6
            int r12 = r11.size
            int r12 = r12 + r6
            r11.size = r12
            int r12 = r11.modCount
            int r12 = r12 + 1
            r11.modCount = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.builders.MapBuilder.removeEntryAt(int):void");
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MapBuilder(int r8) {
        /*
            r7 = this;
            if (r8 < 0) goto L1f
            java.lang.Object[] r1 = new java.lang.Object[r8]
            int[] r3 = new int[r8]
            kotlin.collections.builders.MapBuilder$Companion r0 = kotlin.collections.builders.MapBuilder.Companion
            r0.getClass()
            r0 = 1
            if (r8 >= r0) goto Lf
            r8 = r0
        Lf:
            int r8 = r8 * 3
            int r8 = java.lang.Integer.highestOneBit(r8)
            int[] r4 = new int[r8]
            r6 = 0
            r2 = 0
            r5 = 2
            r0 = r7
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        L1f:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "capacity must be non-negative."
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.builders.MapBuilder.<init>(int):void");
    }
}
