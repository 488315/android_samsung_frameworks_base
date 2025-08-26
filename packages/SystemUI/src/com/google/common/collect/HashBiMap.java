package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableCollection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes4.dex */
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap, Serializable {
    public transient EntrySet entrySet;
    public transient int firstInInsertionOrder;
    public transient int[] hashTableKToV;
    public transient int[] hashTableVToK;
    public transient BiMap inverse;
    public transient KeySet keySet;
    public transient Object[] keys;
    public transient int lastInInsertionOrder;
    public transient int modCount;
    public transient int[] nextInBucketKToV;
    public transient int[] nextInBucketVToK;
    public transient int[] nextInInsertionOrder;
    public transient int[] prevInInsertionOrder;
    public transient int size;
    public transient ValueSet valueSet;
    public transient Object[] values;

    public final class EntryForKey extends AbstractMapEntry {
        public int index;
        public final Object key;

        public EntryForKey(int i) {
            this.key = HashBiMap.this.keys[i];
            this.index = i;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                return null;
            }
            return HashBiMap.this.values[i];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public final Object setValue(Object obj) {
            updateIndex();
            int i = this.index;
            if (i == -1) {
                HashBiMap.this.put(this.key, obj);
                return null;
            }
            Object obj2 = HashBiMap.this.values[i];
            if (Objects.equal(obj2, obj)) {
                return obj;
            }
            HashBiMap.this.replaceValueInEntry(this.index, obj);
            return obj2;
        }

        public final void updateIndex() {
            int i = this.index;
            if (i != -1) {
                HashBiMap hashBiMap = HashBiMap.this;
                if (i <= hashBiMap.size && Objects.equal(hashBiMap.keys[i], this.key)) {
                    return;
                }
            }
            HashBiMap hashBiMap2 = HashBiMap.this;
            Object obj = this.key;
            hashBiMap2.getClass();
            this.index = hashBiMap2.findEntryByKey(Hashing.smearedHash(obj), obj);
        }
    }

    public final class EntryForValue extends AbstractMapEntry {
        public final HashBiMap biMap;
        public int index;
        public final Object value;

        public EntryForValue(HashBiMap<Object, Object> hashBiMap, int i) {
            this.biMap = hashBiMap;
            this.value = hashBiMap.values[i];
            this.index = i;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            updateIndex$1();
            int i = this.index;
            if (i == -1) {
                return null;
            }
            return this.biMap.keys[i];
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        public final Object setValue(Object obj) {
            updateIndex$1();
            int i = this.index;
            if (i == -1) {
                this.biMap.putInverse(this.value, obj);
                return null;
            }
            Object obj2 = this.biMap.keys[i];
            if (Objects.equal(obj2, obj)) {
                return obj;
            }
            this.biMap.replaceKeyInEntry(this.index, obj);
            return obj2;
        }

        public final void updateIndex$1() {
            int i = this.index;
            if (i != -1) {
                HashBiMap hashBiMap = this.biMap;
                if (i <= hashBiMap.size && Objects.equal(this.value, hashBiMap.values[i])) {
                    return;
                }
            }
            HashBiMap hashBiMap2 = this.biMap;
            Object obj = this.value;
            hashBiMap2.getClass();
            this.index = hashBiMap2.findEntryByValue(Hashing.smearedHash(obj), obj);
        }
    }

    public final class EntrySet extends View {
        public EntrySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            HashBiMap hashBiMap = HashBiMap.this;
            hashBiMap.getClass();
            int iFindEntryByKey = hashBiMap.findEntryByKey(Hashing.smearedHash(key), key);
            return iFindEntryByKey != -1 && Objects.equal(value, HashBiMap.this.values[iFindEntryByKey]);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return new EntryForKey(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(iSmearedHash, key);
            if (iFindEntryByKey == -1 || !Objects.equal(value, HashBiMap.this.values[iFindEntryByKey])) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }
    }

    class Inverse<K, V> extends AbstractMap<V, K> implements BiMap, Serializable {
        private final HashBiMap<K, V> forward;
        public transient InverseEntrySet inverseEntrySet;

        public Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.forward.inverse = this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsKey(Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsValue(Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set entrySet() {
            InverseEntrySet inverseEntrySet = this.inverseEntrySet;
            if (inverseEntrySet != null) {
                return inverseEntrySet;
            }
            InverseEntrySet inverseEntrySet2 = new InverseEntrySet(this.forward);
            this.inverseEntrySet = inverseEntrySet2;
            return inverseEntrySet2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object get(Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            hashBiMap.getClass();
            int iFindEntryByValue = hashBiMap.findEntryByValue(Hashing.smearedHash(obj), obj);
            if (iFindEntryByValue == -1) {
                return null;
            }
            return hashBiMap.keys[iFindEntryByValue];
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object put(Object obj, Object obj2) {
            return this.forward.putInverse(obj, obj2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object remove(Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            hashBiMap.getClass();
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByValue = hashBiMap.findEntryByValue(iSmearedHash, obj);
            if (iFindEntryByValue == -1) {
                return null;
            }
            Object obj2 = hashBiMap.keys[iFindEntryByValue];
            hashBiMap.removeEntry(iFindEntryByValue, Hashing.smearedHash(obj2), iSmearedHash);
            return obj2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Collection values() {
            return this.forward.keySet();
        }
    }

    public class InverseEntrySet extends View {
        public InverseEntrySet(HashBiMap<Object, Object> hashBiMap) {
            super(hashBiMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            HashBiMap hashBiMap = this.biMap;
            hashBiMap.getClass();
            int iFindEntryByValue = hashBiMap.findEntryByValue(Hashing.smearedHash(key), key);
            return iFindEntryByValue != -1 && Objects.equal(this.biMap.keys[iFindEntryByValue], value);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return new EntryForValue(this.biMap, i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int iSmearedHash = Hashing.smearedHash(key);
            int iFindEntryByValue = this.biMap.findEntryByValue(iSmearedHash, key);
            if (iFindEntryByValue == -1 || !Objects.equal(this.biMap.keys[iFindEntryByValue], value)) {
                return false;
            }
            HashBiMap hashBiMap = this.biMap;
            hashBiMap.removeEntry(iFindEntryByValue, Hashing.smearedHash(hashBiMap.keys[iFindEntryByValue]), iSmearedHash);
            return true;
        }
    }

    public final class KeySet extends View {
        public KeySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return HashBiMap.this.keys[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByKey = HashBiMap.this.findEntryByKey(iSmearedHash, obj);
            if (iFindEntryByKey == -1) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
            return true;
        }
    }

    public final class ValueSet extends View {
        public ValueSet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return HashBiMap.this.values[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            int iSmearedHash = Hashing.smearedHash(obj);
            int iFindEntryByValue = HashBiMap.this.findEntryByValue(iSmearedHash, obj);
            if (iFindEntryByValue == -1) {
                return false;
            }
            HashBiMap hashBiMap = HashBiMap.this;
            hashBiMap.removeEntry(iFindEntryByValue, Hashing.smearedHash(hashBiMap.keys[iFindEntryByValue]), iSmearedHash);
            return true;
        }
    }

    public abstract class View extends AbstractSet {
        public final HashBiMap biMap;

        /* renamed from: com.google.common.collect.HashBiMap$View$1, reason: invalid class name */
        public class AnonymousClass1 implements Iterator {
            public int expectedModCount;
            public int index;
            public int indexToRemove;
            public int remaining;

            public AnonymousClass1() {
                HashBiMap hashBiMap = View.this.biMap;
                this.index = hashBiMap.firstInInsertionOrder;
                this.indexToRemove = -1;
                this.expectedModCount = hashBiMap.modCount;
                this.remaining = hashBiMap.size;
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                if (View.this.biMap.modCount == this.expectedModCount) {
                    return this.index != -2 && this.remaining > 0;
                }
                throw new ConcurrentModificationException();
            }

            @Override // java.util.Iterator
            public final Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object objForEntry = View.this.forEntry(this.index);
                int i = this.index;
                this.indexToRemove = i;
                this.index = View.this.biMap.nextInInsertionOrder[i];
                this.remaining--;
                return objForEntry;
            }

            @Override // java.util.Iterator
            public final void remove() {
                HashBiMap hashBiMap = View.this.biMap;
                if (hashBiMap.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                int i = this.indexToRemove;
                if (i == -1) {
                    throw new IllegalStateException("no calls to next() since the last call to remove()");
                }
                hashBiMap.removeEntryKeyHashKnown(i, Hashing.smearedHash(hashBiMap.keys[i]));
                int i2 = this.index;
                HashBiMap hashBiMap2 = View.this.biMap;
                if (i2 == hashBiMap2.size) {
                    this.index = this.indexToRemove;
                }
                this.indexToRemove = -1;
                this.expectedModCount = hashBiMap2.modCount;
            }
        }

        public View(HashBiMap<Object, Object> hashBiMap) {
            this.biMap = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            this.biMap.clear();
        }

        public abstract Object forEntry(int i);

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            return new AnonymousClass1();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.biMap.size;
        }
    }

    private HashBiMap(int i) {
        init(i);
    }

    public static HashBiMap create() {
        return new HashBiMap(16);
    }

    public static int[] createFilledWithAbsent(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        init(16);
        for (int i2 = 0; i2 < i; i2++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        View.AnonymousClass1 anonymousClass1 = ((View) entrySet()).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            Map.Entry entry = (Map.Entry) anonymousClass1.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    public final int bucket(int i) {
        return (this.hashTableKToV.length - 1) & i;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return findEntryByKey(Hashing.smearedHash(obj), obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        return findEntryByValue(Hashing.smearedHash(obj), obj) != -1;
    }

    public final void deleteFromTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.hashTableKToV;
        int i3 = iArr[iBucket];
        if (i3 == i) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[iBucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i4 = this.nextInBucketKToV[i3];
        while (true) {
            int i5 = i3;
            i3 = i4;
            if (i3 == -1) {
                throw new AssertionError("Expected to find entry with key " + this.keys[i]);
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketKToV[i3];
        }
    }

    public final void deleteFromTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.hashTableVToK;
        int i3 = iArr[iBucket];
        if (i3 == i) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[iBucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i4 = this.nextInBucketVToK[i3];
        while (true) {
            int i5 = i3;
            i3 = i4;
            if (i3 == -1) {
                throw new AssertionError("Expected to find entry with value " + this.values[i]);
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketVToK[i3];
        }
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i) {
            int iExpandedCapacity = ImmutableCollection.Builder.expandedCapacity(iArr.length, i);
            this.keys = Arrays.copyOf(this.keys, iExpandedCapacity);
            this.values = Arrays.copyOf(this.values, iExpandedCapacity);
            int[] iArr2 = this.nextInBucketKToV;
            int length = iArr2.length;
            int[] iArrCopyOf = Arrays.copyOf(iArr2, iExpandedCapacity);
            Arrays.fill(iArrCopyOf, length, iExpandedCapacity, -1);
            this.nextInBucketKToV = iArrCopyOf;
            int[] iArr3 = this.nextInBucketVToK;
            int length2 = iArr3.length;
            int[] iArrCopyOf2 = Arrays.copyOf(iArr3, iExpandedCapacity);
            Arrays.fill(iArrCopyOf2, length2, iExpandedCapacity, -1);
            this.nextInBucketVToK = iArrCopyOf2;
            int[] iArr4 = this.prevInInsertionOrder;
            int length3 = iArr4.length;
            int[] iArrCopyOf3 = Arrays.copyOf(iArr4, iExpandedCapacity);
            Arrays.fill(iArrCopyOf3, length3, iExpandedCapacity, -1);
            this.prevInInsertionOrder = iArrCopyOf3;
            int[] iArr5 = this.nextInInsertionOrder;
            int length4 = iArr5.length;
            int[] iArrCopyOf4 = Arrays.copyOf(iArr5, iExpandedCapacity);
            Arrays.fill(iArrCopyOf4, length4, iExpandedCapacity, -1);
            this.nextInInsertionOrder = iArrCopyOf4;
        }
        if (this.hashTableKToV.length < i) {
            int iMax = Math.max(i, 2);
            int iHighestOneBit = Integer.highestOneBit(iMax);
            if (iMax > ((int) (1.0d * iHighestOneBit)) && (iHighestOneBit = iHighestOneBit << 1) <= 0) {
                iHighestOneBit = 1073741824;
            }
            this.hashTableKToV = createFilledWithAbsent(iHighestOneBit);
            this.hashTableVToK = createFilledWithAbsent(iHighestOneBit);
            for (int i2 = 0; i2 < this.size; i2++) {
                int iBucket = bucket(Hashing.smearedHash(this.keys[i2]));
                int[] iArr6 = this.nextInBucketKToV;
                int[] iArr7 = this.hashTableKToV;
                iArr6[i2] = iArr7[iBucket];
                iArr7[iBucket] = i2;
                int iBucket2 = bucket(Hashing.smearedHash(this.values[i2]));
                int[] iArr8 = this.nextInBucketVToK;
                int[] iArr9 = this.hashTableVToK;
                iArr8[i2] = iArr9[iBucket2];
                iArr9[iBucket2] = i2;
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    public final int findEntryByKey(int i, Object obj) {
        int[] iArr = this.hashTableKToV;
        int[] iArr2 = this.nextInBucketKToV;
        Object[] objArr = this.keys;
        for (int i2 = iArr[bucket(i)]; i2 != -1; i2 = iArr2[i2]) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
        }
        return -1;
    }

    public final int findEntryByValue(int i, Object obj) {
        int[] iArr = this.hashTableVToK;
        int[] iArr2 = this.nextInBucketVToK;
        Object[] objArr = this.values;
        for (int i2 = iArr[bucket(i)]; i2 != -1; i2 = iArr2[i2]) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        int iFindEntryByKey = findEntryByKey(Hashing.smearedHash(obj), obj);
        if (iFindEntryByKey == -1) {
            return null;
        }
        return this.values[iFindEntryByKey];
    }

    public final void init(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        int iMax = Math.max(i, 2);
        int iHighestOneBit = Integer.highestOneBit(iMax);
        if (iMax > ((int) (1.0d * iHighestOneBit)) && (iHighestOneBit = iHighestOneBit << 1) <= 0) {
            iHighestOneBit = 1073741824;
        }
        this.size = 0;
        this.keys = new Object[i];
        this.values = new Object[i];
        this.hashTableKToV = createFilledWithAbsent(iHighestOneBit);
        this.hashTableVToK = createFilledWithAbsent(iHighestOneBit);
        this.nextInBucketKToV = createFilledWithAbsent(i);
        this.nextInBucketVToK = createFilledWithAbsent(i);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(i);
        this.nextInInsertionOrder = createFilledWithAbsent(i);
    }

    public final void insertIntoTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i] = iArr2[iBucket];
        iArr2[iBucket] = i;
    }

    public final void insertIntoTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int iBucket = bucket(i2);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i] = iArr2[iBucket];
        iArr2[iBucket] = i;
    }

    public final BiMap inverse() {
        BiMap biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.inverse = inverse;
        return inverse;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        KeySet keySet = this.keySet;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByKey = findEntryByKey(iSmearedHash, obj);
        if (iFindEntryByKey != -1) {
            Object obj3 = this.values[iFindEntryByKey];
            if (Objects.equal(obj3, obj2)) {
                return obj2;
            }
            replaceValueInEntry(iFindEntryByKey, obj2);
            return obj3;
        }
        int iSmearedHash2 = Hashing.smearedHash(obj2);
        if (!(findEntryByValue(iSmearedHash2, obj2) == -1)) {
            throw new IllegalArgumentException(Strings.lenientFormat("Value already present: %s", obj2));
        }
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i = this.size;
        objArr[i] = obj;
        this.values[i] = obj2;
        insertIntoTableKToV(i, iSmearedHash);
        insertIntoTableVToK(this.size, iSmearedHash2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    public final Object putInverse(Object obj, Object obj2) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByValue = findEntryByValue(iSmearedHash, obj);
        if (iFindEntryByValue != -1) {
            Object obj3 = this.keys[iFindEntryByValue];
            if (Objects.equal(obj3, obj2)) {
                return obj2;
            }
            replaceKeyInEntry(iFindEntryByValue, obj2);
            return obj3;
        }
        int i = this.lastInInsertionOrder;
        int iSmearedHash2 = Hashing.smearedHash(obj2);
        if (!(findEntryByKey(iSmearedHash2, obj2) == -1)) {
            throw new IllegalArgumentException(Strings.lenientFormat("Key already present: %s", obj2));
        }
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i2 = this.size;
        objArr[i2] = obj2;
        this.values[i2] = obj;
        insertIntoTableKToV(i2, iSmearedHash2);
        insertIntoTableVToK(this.size, iSmearedHash);
        int i3 = i == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i];
        setSucceeds(i, this.size);
        setSucceeds(this.size, i3);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        int iSmearedHash = Hashing.smearedHash(obj);
        int iFindEntryByKey = findEntryByKey(iSmearedHash, obj);
        if (iFindEntryByKey == -1) {
            return null;
        }
        Object obj2 = this.values[iFindEntryByKey];
        removeEntryKeyHashKnown(iFindEntryByKey, iSmearedHash);
        return obj2;
    }

    public final void removeEntry(int i, int i2, int i3) {
        int i4;
        int i5;
        Preconditions.checkArgument(i != -1);
        deleteFromTableKToV(i, i2);
        deleteFromTableVToK(i, i3);
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        int i6 = this.size - 1;
        if (i6 != i) {
            int i7 = this.prevInInsertionOrder[i6];
            int i8 = this.nextInInsertionOrder[i6];
            setSucceeds(i7, i);
            setSucceeds(i, i8);
            Object[] objArr = this.keys;
            Object obj = objArr[i6];
            Object[] objArr2 = this.values;
            Object obj2 = objArr2[i6];
            objArr[i] = obj;
            objArr2[i] = obj2;
            int iBucket = bucket(Hashing.smearedHash(obj));
            int[] iArr = this.hashTableKToV;
            int i9 = iArr[iBucket];
            if (i9 == i6) {
                iArr[iBucket] = i;
            } else {
                int i10 = this.nextInBucketKToV[i9];
                while (true) {
                    i4 = i9;
                    i9 = i10;
                    if (i9 == i6) {
                        break;
                    } else {
                        i10 = this.nextInBucketKToV[i9];
                    }
                }
                this.nextInBucketKToV[i4] = i;
            }
            int[] iArr2 = this.nextInBucketKToV;
            iArr2[i] = iArr2[i6];
            iArr2[i6] = -1;
            int iBucket2 = bucket(Hashing.smearedHash(obj2));
            int[] iArr3 = this.hashTableVToK;
            int i11 = iArr3[iBucket2];
            if (i11 == i6) {
                iArr3[iBucket2] = i;
            } else {
                int i12 = this.nextInBucketVToK[i11];
                while (true) {
                    i5 = i11;
                    i11 = i12;
                    if (i11 == i6) {
                        break;
                    } else {
                        i12 = this.nextInBucketVToK[i11];
                    }
                }
                this.nextInBucketVToK[i5] = i;
            }
            int[] iArr4 = this.nextInBucketVToK;
            iArr4[i] = iArr4[i6];
            iArr4[i6] = -1;
        }
        Object[] objArr3 = this.keys;
        int i13 = this.size;
        objArr3[i13 - 1] = null;
        this.values[i13 - 1] = null;
        this.size = i13 - 1;
        this.modCount++;
    }

    public final void removeEntryKeyHashKnown(int i, int i2) {
        removeEntry(i, i2, Hashing.smearedHash(this.values[i]));
    }

    public final void replaceKeyInEntry(int i, Object obj) {
        Preconditions.checkArgument(i != -1);
        int iFindEntryByKey = findEntryByKey(Hashing.smearedHash(obj), obj);
        int i2 = this.lastInInsertionOrder;
        if (iFindEntryByKey != -1) {
            throw new IllegalArgumentException("Key already present in map: " + obj);
        }
        if (i2 == i) {
            i2 = this.prevInInsertionOrder[i];
        } else if (i2 == this.size) {
            i2 = iFindEntryByKey;
        }
        if (-2 == i) {
            iFindEntryByKey = this.nextInInsertionOrder[i];
        } else if (-2 != this.size) {
            iFindEntryByKey = -2;
        }
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        deleteFromTableKToV(i, Hashing.smearedHash(this.keys[i]));
        this.keys[i] = obj;
        insertIntoTableKToV(i, Hashing.smearedHash(obj));
        setSucceeds(i2, i);
        setSucceeds(i, iFindEntryByKey);
    }

    public final void replaceValueInEntry(int i, Object obj) {
        Preconditions.checkArgument(i != -1);
        int iSmearedHash = Hashing.smearedHash(obj);
        if (findEntryByValue(iSmearedHash, obj) != -1) {
            throw new IllegalArgumentException("Value already present in map: " + obj);
        }
        deleteFromTableVToK(i, Hashing.smearedHash(this.values[i]));
        this.values[i] = obj;
        insertIntoTableVToK(i, iSmearedHash);
    }

    public final void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstInInsertionOrder = i2;
        } else {
            this.nextInInsertionOrder[i] = i2;
        }
        if (i2 == -2) {
            this.lastInInsertionOrder = i;
        } else {
            this.prevInInsertionOrder[i2] = i;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set values() {
        ValueSet valueSet = this.valueSet;
        if (valueSet != null) {
            return valueSet;
        }
        ValueSet valueSet2 = new ValueSet();
        this.valueSet = valueSet2;
        return valueSet2;
    }
}
