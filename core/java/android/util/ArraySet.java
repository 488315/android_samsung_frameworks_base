package android.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ArraySet<E> implements Collection<E>, Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";
    static Object[] sBaseCache;
    static int sBaseCacheSize;
    static Object[] sTwiceBaseCache;
    static int sTwiceBaseCacheSize;
    Object[] mArray;
    private MapCollections<E, E> mCollections;
    int[] mHashes;
    private final boolean mIdentityHashCode;
    int mSize;
    private static final Object sBaseCacheLock = new Object();
    private static final Object sTwiceBaseCacheLock = new Object();

    private int binarySearch(int[] hashes, int hash) {
        try {
            return ContainerHelpers.binarySearch(hashes, this.mSize, hash);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    private int indexOf(Object key, int hash) {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearch(this.mHashes, hash);
        if (index < 0) {
            return index;
        }
        if (key.equals(this.mArray[index])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end])) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == hash; i--) {
            if (key.equals(this.mArray[i])) {
                return i;
            }
        }
        int i2 = ~end;
        return i2;
    }

    private int indexOfNull() {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearch(this.mHashes, 0);
        if (index < 0) {
            return index;
        }
        if (this.mArray[index] == null) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (this.mArray[end] == null) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == 0; i--) {
            if (this.mArray[i] == null) {
                return i;
            }
        }
        int i2 = ~end;
        return i2;
    }

    private void allocArrays(int size) {
        if (size == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCache != null) {
                    Object[] array = sTwiceBaseCache;
                    try {
                        this.mArray = array;
                        sTwiceBaseCache = (Object[]) array[0];
                        this.mHashes = (int[]) array[1];
                    } catch (ClassCastException e) {
                    }
                    if (this.mHashes != null) {
                        array[1] = null;
                        array[0] = null;
                        sTwiceBaseCacheSize--;
                        return;
                    } else {
                        Slog.wtf(
                                TAG,
                                "Found corrupt ArraySet cache: [0]="
                                        + array[0]
                                        + " [1]="
                                        + array[1]);
                        sTwiceBaseCache = null;
                        sTwiceBaseCacheSize = 0;
                    }
                }
            }
        } else if (size == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCache != null) {
                    Object[] array2 = sBaseCache;
                    try {
                        this.mArray = array2;
                        sBaseCache = (Object[]) array2[0];
                        this.mHashes = (int[]) array2[1];
                    } catch (ClassCastException e2) {
                    }
                    if (this.mHashes != null) {
                        array2[1] = null;
                        array2[0] = null;
                        sBaseCacheSize--;
                        return;
                    } else {
                        Slog.wtf(
                                TAG,
                                "Found corrupt ArraySet cache: [0]="
                                        + array2[0]
                                        + " [1]="
                                        + array2[1]);
                        sBaseCache = null;
                        sBaseCacheSize = 0;
                    }
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[size];
    }

    private static void freeArrays(int[] hashes, Object[] array, int size) {
        if (hashes.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCacheSize < 10) {
                    array[0] = sTwiceBaseCache;
                    array[1] = hashes;
                    for (int i = size - 1; i >= 2; i--) {
                        array[i] = null;
                    }
                    sTwiceBaseCache = array;
                    sTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (hashes.length == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCacheSize < 10) {
                    array[0] = sBaseCache;
                    array[1] = hashes;
                    for (int i2 = size - 1; i2 >= 2; i2--) {
                        array[i2] = null;
                    }
                    sBaseCache = array;
                    sBaseCacheSize++;
                }
            }
        }
    }

    public ArraySet() {
        this(0, false);
    }

    public ArraySet(int capacity) {
        this(capacity, false);
    }

    public ArraySet(int capacity, boolean identityHashCode) {
        this.mIdentityHashCode = identityHashCode;
        if (capacity == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            allocArrays(capacity);
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    public ArraySet(Collection<? extends E> set) {
        this();
        if (set != null) {
            addAll(set);
        }
    }

    public ArraySet(E[] array) {
        this();
        if (array != null) {
            for (E value : array) {
                add(value);
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        if (this.mSize != 0) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            int osize = this.mSize;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize != 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int minimumCapacity) {
        int oSize = this.mSize;
        if (this.mHashes.length < minimumCapacity) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(minimumCapacity);
            if (this.mSize > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, this.mSize);
                System.arraycopy(oarray, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(ohashes, oarray, this.mSize);
        }
        if (this.mSize != oSize) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(Object key) {
        return indexOf(key) >= 0;
    }

    public int indexOf(Object key) {
        if (key == null) {
            return indexOfNull();
        }
        return indexOf(key, this.mIdentityHashCode ? System.identityHashCode(key) : key.hashCode());
    }

    public E valueAt(int index) {
        if (index >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return valueAtUnchecked(index);
    }

    public E valueAtUnchecked(int i) {
        return (E) this.mArray[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E value) {
        int hash;
        int index;
        int oSize = this.mSize;
        if (value == null) {
            hash = 0;
            index = indexOfNull();
        } else {
            hash = this.mIdentityHashCode ? System.identityHashCode(value) : value.hashCode();
            index = indexOf(value, hash);
        }
        if (index >= 0) {
            return false;
        }
        int index2 = ~index;
        if (oSize >= this.mHashes.length) {
            int n = 8;
            if (oSize >= 8) {
                n = (oSize >> 1) + oSize;
            } else if (oSize < 4) {
                n = 4;
            }
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(n);
            if (oSize != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, ohashes.length);
                System.arraycopy(oarray, 0, this.mArray, 0, oarray.length);
            }
            freeArrays(ohashes, oarray, oSize);
        }
        if (index2 < oSize) {
            System.arraycopy(this.mHashes, index2, this.mHashes, index2 + 1, oSize - index2);
            System.arraycopy(this.mArray, index2, this.mArray, index2 + 1, oSize - index2);
        }
        if (oSize != this.mSize || index2 >= this.mHashes.length) {
            throw new ConcurrentModificationException();
        }
        this.mHashes[index2] = hash;
        this.mArray[index2] = value;
        this.mSize++;
        return true;
    }

    public void append(E value) {
        int hash;
        int oSize = this.mSize;
        int index = this.mSize;
        if (value == null) {
            hash = 0;
        } else {
            hash = this.mIdentityHashCode ? System.identityHashCode(value) : value.hashCode();
        }
        if (index >= this.mHashes.length) {
            throw new IllegalStateException("Array is full");
        }
        if (index > 0 && this.mHashes[index - 1] > hash) {
            add(value);
        } else {
            if (oSize != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = index + 1;
            this.mHashes[index] = hash;
            this.mArray[index] = value;
        }
    }

    public void addAll(ArraySet<? extends E> array) {
        int N = array.mSize;
        ensureCapacity(this.mSize + N);
        if (this.mSize == 0) {
            if (N > 0) {
                System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
                System.arraycopy(array.mArray, 0, this.mArray, 0, N);
                if (this.mSize != 0) {
                    throw new ConcurrentModificationException();
                }
                this.mSize = N;
                return;
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            add(array.valueAt(i));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index >= 0) {
            removeAt(index);
            return true;
        }
        return false;
    }

    private boolean shouldShrink() {
        return this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3;
    }

    private int getNewShrunkenSize() {
        if (this.mSize <= 8) {
            return 8;
        }
        return (this.mSize >> 1) + this.mSize;
    }

    public E removeAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        int i2 = this.mSize;
        E e = (E) this.mArray[i];
        if (i2 <= 1) {
            clear();
        } else {
            int i3 = i2 - 1;
            if (shouldShrink()) {
                int newShrunkenSize = getNewShrunkenSize();
                int[] iArr = this.mHashes;
                Object[] objArr = this.mArray;
                allocArrays(newShrunkenSize);
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.mArray, 0, i);
                }
                if (i < i3) {
                    System.arraycopy(iArr, i + 1, this.mHashes, i, i3 - i);
                    System.arraycopy(objArr, i + 1, this.mArray, i, i3 - i);
                }
            } else {
                if (i < i3) {
                    System.arraycopy(this.mHashes, i + 1, this.mHashes, i, i3 - i);
                    System.arraycopy(this.mArray, i + 1, this.mArray, i, i3 - i);
                }
                this.mArray[i3] = null;
            }
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = i3;
        }
        return e;
    }

    public boolean removeAll(ArraySet<? extends E> array) {
        int N = array.mSize;
        int originalSize = this.mSize;
        for (int i = 0; i < N; i++) {
            remove(array.valueAt(i));
        }
        int i2 = this.mSize;
        return originalSize != i2;
    }

    @Override // java.util.Collection
    public boolean removeIf(Predicate<? super E> filter) {
        if (this.mSize == 0) {
            return false;
        }
        int replaceIndex = 0;
        int numRemoved = 0;
        for (int i = 0; i < this.mSize; i++) {
            if (filter.test(this.mArray[i])) {
                numRemoved++;
            } else {
                if (replaceIndex != i) {
                    this.mArray[replaceIndex] = this.mArray[i];
                    this.mHashes[replaceIndex] = this.mHashes[i];
                }
                replaceIndex++;
            }
        }
        if (numRemoved == 0) {
            return false;
        }
        if (numRemoved == this.mSize) {
            clear();
            return true;
        }
        this.mSize -= numRemoved;
        if (shouldShrink()) {
            int n = getNewShrunkenSize();
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(n);
            System.arraycopy(ohashes, 0, this.mHashes, 0, this.mSize);
            System.arraycopy(oarray, 0, this.mArray, 0, this.mSize);
        } else {
            for (int i2 = this.mSize; i2 < this.mArray.length; i2++) {
                this.mArray[i2] = null;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.mSize;
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> action) {
        if (action == null) {
            throw new NullPointerException("action must not be null");
        }
        for (int i = 0; i < this.mSize; i++) {
            action.accept(valueAt(i));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public Object[] toArray() {
        Object[] result = new Object[this.mSize];
        System.arraycopy(this.mArray, 0, result, 0, this.mSize);
        return result;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] array) {
        if (array.length < this.mSize) {
            array = (Object[]) Array.newInstance(array.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, array, 0, this.mSize);
        if (array.length > this.mSize) {
            array[this.mSize] = null;
        }
        return array;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> set = (Set) object;
        if (size() != set.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                E mine = valueAt(i);
                if (!set.contains(mine)) {
                    return false;
                }
            } catch (ClassCastException e) {
                return false;
            } catch (NullPointerException e2) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] hashes = this.mHashes;
        int result = 0;
        int s = this.mSize;
        for (int i = 0; i < s; i++) {
            result += hashes[i];
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 14);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Set)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections =
                    new MapCollections<E, E>() { // from class: android.util.ArraySet.1
                        @Override // android.util.MapCollections
                        protected int colGetSize() {
                            return ArraySet.this.mSize;
                        }

                        @Override // android.util.MapCollections
                        protected Object colGetEntry(int index, int offset) {
                            return ArraySet.this.mArray[index];
                        }

                        @Override // android.util.MapCollections
                        protected int colIndexOfKey(Object key) {
                            return ArraySet.this.indexOf(key);
                        }

                        @Override // android.util.MapCollections
                        protected int colIndexOfValue(Object value) {
                            return ArraySet.this.indexOf(value);
                        }

                        @Override // android.util.MapCollections
                        protected Map<E, E> colGetMap() {
                            throw new UnsupportedOperationException("not a map");
                        }

                        @Override // android.util.MapCollections
                        protected void colPut(E key, E value) {
                            ArraySet.this.add(key);
                        }

                        @Override // android.util.MapCollections
                        protected E colSetValue(int index, E value) {
                            throw new UnsupportedOperationException("not a map");
                        }

                        @Override // android.util.MapCollections
                        protected void colRemoveAt(int index) {
                            ArraySet.this.removeAt(index);
                        }

                        @Override // android.util.MapCollections
                        protected void colClear() {
                            ArraySet.this.clear();
                        }
                    };
        }
        return this.mCollections;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return getCollection().getKeySet().iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends E> collection) {
        ensureCapacity(this.mSize + collection.size());
        boolean added = false;
        for (E value : collection) {
            added |= add(value);
        }
        return added;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        boolean removed = false;
        for (Object value : collection) {
            removed |= remove(value);
        }
        return removed;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean removed = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                removed = true;
            }
        }
        return removed;
    }
}
