package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    public static final ImmutableMap EMPTY = new RegularImmutableMap(null, new Object[0], 0);
    private static final long serialVersionUID = 0;
    public final transient Object[] alternatingKeysAndValues;
    public final transient Object hashTable;
    public final transient int size;

    class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        public final transient Object[] alternatingKeysAndValues;
        public final transient int keyOffset;
        public final transient ImmutableMap map;
        public final transient int size;

        public EntrySet(ImmutableMap<K, V> immutableMap, Object[] objArr, int i, int i2) {
            this.map = immutableMap;
            this.alternatingKeysAndValues = objArr;
            this.keyOffset = i;
            this.size = i2;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public final boolean contains(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && value.equals(this.map.get(key))) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int copyIntoArray(int i, Object[] objArr) {
            return asList().copyIntoArray(i, objArr);
        }

        @Override // com.google.common.collect.ImmutableSet
        public final ImmutableList createAsList() {
            return new ImmutableList<Map.Entry<Object, Object>>() { // from class: com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // java.util.List
                public final Object get(int i) {
                    Preconditions.checkElementIndex(i, EntrySet.this.size);
                    EntrySet entrySet = EntrySet.this;
                    int i2 = i * 2;
                    Object obj = entrySet.alternatingKeysAndValues[entrySet.keyOffset + i2];
                    Objects.requireNonNull(obj);
                    EntrySet entrySet2 = EntrySet.this;
                    Object obj2 = entrySet2.alternatingKeysAndValues[i2 + (entrySet2.keyOffset ^ 1)];
                    Objects.requireNonNull(obj2);
                    return new AbstractMap.SimpleImmutableEntry(obj, obj2);
                }

                @Override // com.google.common.collect.ImmutableCollection
                public final boolean isPartialView() {
                    return true;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public final int size() {
                    return EntrySet.this.size;
                }

                @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
                public Object writeReplace() {
                    return super.writeReplace();
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final UnmodifiableIterator iterator() {
            return asList().listIterator(0);
        }
    }

    final class KeySet<K> extends ImmutableSet<K> {
        public final transient ImmutableList list;
        public final transient ImmutableMap map;

        public KeySet(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.map = immutableMap;
            this.list = immutableList;
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public final ImmutableList asList() {
            return this.list;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        public final boolean contains(Object obj) {
            return this.map.get(obj) != null;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int copyIntoArray(int i, Object[] objArr) {
            return this.list.copyIntoArray(i, objArr);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }

        @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final UnmodifiableIterator iterator() {
            return this.list.listIterator(0);
        }
    }

    final class KeysOrValuesAsList extends ImmutableList<Object> {
        public final transient Object[] alternatingKeysAndValues;
        public final transient int offset;
        public final transient int size;

        public KeysOrValuesAsList(Object[] objArr, int i, int i2) {
            this.alternatingKeysAndValues = objArr;
            this.offset = i;
            this.size = i2;
        }

        @Override // java.util.List
        public final Object get(int i) {
            Preconditions.checkElementIndex(i, this.size);
            Object obj = this.alternatingKeysAndValues[(i * 2) + this.offset];
            Objects.requireNonNull(obj);
            return obj;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }
    }

    private RegularImmutableMap(Object obj, Object[] objArr, int i) {
        this.hashTable = obj;
        this.alternatingKeysAndValues = objArr;
        this.size = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019b  */
    /* JADX WARN: Type inference failed for: r17v11 */
    /* JADX WARN: Type inference failed for: r17v12 */
    /* JADX WARN: Type inference failed for: r17v13 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.Object[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static RegularImmutableMap create(int i, Object[] objArr, ImmutableMap.Builder builder) {
        int i2;
        boolean z;
        char c;
        Object obj;
        char c2;
        short[] sArr;
        int i3;
        boolean z2;
        ?? r17;
        boolean z3;
        boolean z4;
        int i4 = i;
        Object[] objArrCopyOf = objArr;
        if (i4 == 0) {
            return (RegularImmutableMap) EMPTY;
        }
        int i5 = 1;
        Object duplicateKey = null;
        boolean z5 = false;
        if (i4 == 1) {
            Objects.requireNonNull(objArrCopyOf[0]);
            Objects.requireNonNull(objArrCopyOf[1]);
            return new RegularImmutableMap(null, objArrCopyOf, 1);
        }
        Preconditions.checkPositionIndex(i4, objArrCopyOf.length >> 1);
        int iChooseTableSize = ImmutableSet.chooseTableSize(i4);
        char c3 = 2;
        if (i4 != 1) {
            int i6 = iChooseTableSize - 1;
            if (iChooseTableSize <= 128) {
                byte[] bArr = new byte[iChooseTableSize];
                Arrays.fill(bArr, (byte) -1);
                int i7 = 0;
                int i8 = 0;
                while (i7 < i4) {
                    int i9 = i7 * 2;
                    int i10 = i8 * 2;
                    Object obj2 = objArrCopyOf[i9];
                    Objects.requireNonNull(obj2);
                    Object obj3 = objArrCopyOf[i9 ^ i5];
                    Objects.requireNonNull(obj3);
                    int iSmear = Hashing.smear(obj2.hashCode());
                    while (true) {
                        int i11 = iSmear & i6;
                        i3 = i5;
                        z2 = z5;
                        int i12 = bArr[i11] & 255;
                        if (i12 == 255) {
                            bArr[i11] = (byte) i10;
                            if (i8 < i7) {
                                objArrCopyOf[i10] = obj2;
                                objArrCopyOf[i10 ^ 1] = obj3;
                            }
                            i8++;
                        } else {
                            if (obj2.equals(objArrCopyOf[i12])) {
                                int i13 = i12 ^ 1;
                                Object obj4 = objArrCopyOf[i13];
                                Objects.requireNonNull(obj4);
                                duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj2, obj3, obj4);
                                objArrCopyOf[i13] = obj3;
                                break;
                            }
                            iSmear = i11 + 1;
                            i5 = i3;
                            z5 = z2;
                        }
                    }
                    i7++;
                    i5 = i3;
                    z5 = z2;
                }
                i2 = i5;
                z = z5;
                if (i8 == i4) {
                    duplicateKey = bArr;
                    z4 = z;
                } else {
                    sArr = new Object[3];
                    sArr[z ? 1 : 0] = bArr;
                    sArr[i2] = Integer.valueOf(i8);
                    sArr[2] = duplicateKey;
                    duplicateKey = sArr;
                    z4 = z;
                }
            } else {
                i2 = 1;
                z = false;
                if (iChooseTableSize <= 32768) {
                    sArr = new short[iChooseTableSize];
                    Arrays.fill(sArr, (short) -1);
                    int i14 = 0;
                    for (int i15 = 0; i15 < i4; i15++) {
                        int i16 = i15 * 2;
                        int i17 = i14 * 2;
                        Object obj5 = objArrCopyOf[i16];
                        Objects.requireNonNull(obj5);
                        Object obj6 = objArrCopyOf[i16 ^ 1];
                        Objects.requireNonNull(obj6);
                        int iSmear2 = Hashing.smear(obj5.hashCode());
                        while (true) {
                            int i18 = iSmear2 & i6;
                            int i19 = sArr[i18] & 65535;
                            if (i19 == 65535) {
                                sArr[i18] = (short) i17;
                                if (i14 < i15) {
                                    objArrCopyOf[i17] = obj5;
                                    objArrCopyOf[i17 ^ 1] = obj6;
                                }
                                i14++;
                            } else {
                                if (obj5.equals(objArrCopyOf[i19])) {
                                    int i20 = i19 ^ 1;
                                    Object obj7 = objArrCopyOf[i20];
                                    Objects.requireNonNull(obj7);
                                    duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj5, obj6, obj7);
                                    objArrCopyOf[i20] = obj6;
                                    break;
                                }
                                iSmear2 = i18 + 1;
                            }
                        }
                    }
                    if (i14 == i4) {
                        duplicateKey = sArr;
                        z4 = z;
                    } else {
                        duplicateKey = new Object[]{sArr, Integer.valueOf(i14), duplicateKey};
                        z4 = z;
                    }
                } else {
                    int[] iArr = new int[iChooseTableSize];
                    Arrays.fill(iArr, -1);
                    int i21 = 0;
                    int i22 = 0;
                    while (i21 < i4) {
                        int i23 = i21 * 2;
                        int i24 = i22 * 2;
                        Object obj8 = objArrCopyOf[i23];
                        Objects.requireNonNull(obj8);
                        Object obj9 = objArrCopyOf[i23 ^ 1];
                        Objects.requireNonNull(obj9);
                        int iSmear3 = Hashing.smear(obj8.hashCode());
                        while (true) {
                            int i25 = iSmear3 & i6;
                            int i26 = iArr[i25];
                            if (i26 == -1) {
                                iArr[i25] = i24;
                                if (i22 < i21) {
                                    objArrCopyOf[i24] = obj8;
                                    objArrCopyOf[i24 ^ 1] = obj9;
                                }
                                i22++;
                                c2 = c3;
                            } else {
                                c2 = c3;
                                if (obj8.equals(objArrCopyOf[i26])) {
                                    int i27 = i26 ^ 1;
                                    Object obj10 = objArrCopyOf[i27];
                                    Objects.requireNonNull(obj10);
                                    duplicateKey = new ImmutableMap.Builder.DuplicateKey(obj8, obj9, obj10);
                                    objArrCopyOf[i27] = obj9;
                                    break;
                                }
                                iSmear3 = i25 + 1;
                                c3 = c2;
                            }
                        }
                        i21++;
                        c3 = c2;
                    }
                    c = c3;
                    if (i22 == i4) {
                        obj = iArr;
                        r17 = z;
                    } else {
                        Object[] objArr2 = new Object[3];
                        objArr2[0] = iArr;
                        objArr2[1] = Integer.valueOf(i22);
                        objArr2[c] = duplicateKey;
                        obj = objArr2;
                        r17 = z;
                    }
                }
            }
            z3 = obj instanceof Object[];
            Object obj11 = obj;
            if (z3) {
                Object[] objArr3 = (Object[]) obj;
                ImmutableMap.Builder.DuplicateKey duplicateKey2 = (ImmutableMap.Builder.DuplicateKey) objArr3[c];
                if (builder == null) {
                    throw duplicateKey2.exception();
                }
                builder.duplicateKey = duplicateKey2;
                Object obj12 = objArr3[r17];
                int iIntValue = ((Integer) objArr3[i2]).intValue();
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, iIntValue * 2);
                obj11 = obj12;
                i4 = iIntValue;
            }
            return new RegularImmutableMap(obj11, objArrCopyOf, i4);
        }
        Objects.requireNonNull(objArrCopyOf[0]);
        Objects.requireNonNull(objArrCopyOf[1]);
        i2 = 1;
        z4 = false;
        c = 2;
        obj = duplicateKey;
        r17 = z4;
        z3 = obj instanceof Object[];
        Object obj112 = obj;
        if (z3) {
        }
        return new RegularImmutableMap(obj112, objArrCopyOf, i4);
    }

    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableSet createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableSet createKeySet() {
        return new KeySet(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableCollection createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0009 A[EDGE_INSN: B:43:0x0009->B:4:0x0009 BREAK  A[LOOP:0: B:15:0x0037->B:21:0x004d], EDGE_INSN: B:45:0x0009->B:4:0x0009 BREAK  A[LOOP:1: B:25:0x0062->B:31:0x0079], EDGE_INSN: B:47:0x0009->B:4:0x0009 BREAK  A[LOOP:2: B:33:0x0088->B:42:0x00a0]] */
    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object get(Object obj) {
        Object obj2;
        Object obj3 = this.hashTable;
        Object[] objArr = this.alternatingKeysAndValues;
        int i = this.size;
        if (obj != null) {
            if (i == 1) {
                Object obj4 = objArr[0];
                Objects.requireNonNull(obj4);
                if (obj4.equals(obj)) {
                    obj2 = objArr[1];
                    Objects.requireNonNull(obj2);
                } else {
                    obj2 = null;
                }
            } else if (obj3 != null) {
                if (obj3 instanceof byte[]) {
                    byte[] bArr = (byte[]) obj3;
                    int length = bArr.length - 1;
                    int iSmear = Hashing.smear(obj.hashCode());
                    while (true) {
                        int i2 = iSmear & length;
                        int i3 = bArr[i2] & 255;
                        if (i3 == 255) {
                            break;
                        }
                        if (obj.equals(objArr[i3])) {
                            obj2 = objArr[i3 ^ 1];
                            break;
                        }
                        iSmear = i2 + 1;
                    }
                } else if (obj3 instanceof short[]) {
                    short[] sArr = (short[]) obj3;
                    int length2 = sArr.length - 1;
                    int iSmear2 = Hashing.smear(obj.hashCode());
                    while (true) {
                        int i4 = iSmear2 & length2;
                        int i5 = sArr[i4] & 65535;
                        if (i5 == 65535) {
                            break;
                        }
                        if (obj.equals(objArr[i5])) {
                            obj2 = objArr[i5 ^ 1];
                            break;
                        }
                        iSmear2 = i4 + 1;
                    }
                } else {
                    int[] iArr = (int[]) obj3;
                    int length3 = iArr.length - 1;
                    int iSmear3 = Hashing.smear(obj.hashCode());
                    while (true) {
                        int i6 = iSmear3 & length3;
                        int i7 = iArr[i6];
                        if (i7 == -1) {
                            break;
                        }
                        if (obj.equals(objArr[i7])) {
                            obj2 = objArr[i7 ^ 1];
                            break;
                        }
                        iSmear3 = i6 + 1;
                    }
                }
            }
        }
        if (obj2 == null) {
            return null;
        }
        return obj2;
    }

    @Override // java.util.Map
    public final int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMap
    public Object writeReplace() {
        return super.writeReplace();
    }
}
