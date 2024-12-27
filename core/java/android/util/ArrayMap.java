package android.util;

import com.android.internal.util.ArrayUtils;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class ArrayMap<K, V> implements Map<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    private MapCollections<K, V> mCollections;
    int[] mHashes;
    private final boolean mIdentityHashCode;
    int mSize;
    static final int[] EMPTY_IMMUTABLE_INTS = new int[0];
    public static final ArrayMap EMPTY = new ArrayMap(-1);
    private static final Object sBaseCacheLock = new Object();
    private static final Object sTwiceBaseCacheLock = new Object();

    private static int binarySearchHashes(int[] hashes, int N, int hash) {
        try {
            return ContainerHelpers.binarySearch(hashes, N, hash);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    int indexOf(Object key, int hash) {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearchHashes(this.mHashes, N, hash);
        if (index < 0) {
            return index;
        }
        if (key.equals(this.mArray[index << 1])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end << 1])) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == hash; i--) {
            if (key.equals(this.mArray[i << 1])) {
                return i;
            }
        }
        int i2 = ~end;
        return i2;
    }

    int indexOfNull() {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearchHashes(this.mHashes, N, 0);
        if (index < 0) {
            return index;
        }
        if (this.mArray[index << 1] == null) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (this.mArray[end << 1] == null) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == 0; i--) {
            if (this.mArray[i << 1] == null) {
                return i;
            }
        }
        int i2 = ~end;
        return i2;
    }

    private void allocArrays(int size) {
        if (this.mHashes == EMPTY_IMMUTABLE_INTS) {
            throw new UnsupportedOperationException("ArrayMap is immutable");
        }
        if (size == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (mTwiceBaseCache != null) {
                    Object[] array = mTwiceBaseCache;
                    this.mArray = array;
                    try {
                        mTwiceBaseCache = (Object[]) array[0];
                        this.mHashes = (int[]) array[1];
                    } catch (ClassCastException e) {
                    }
                    if (this.mHashes != null) {
                        array[1] = null;
                        array[0] = null;
                        mTwiceBaseCacheSize--;
                        return;
                    } else {
                        Slog.wtf(
                                TAG,
                                "Found corrupt ArrayMap cache: [0]="
                                        + array[0]
                                        + " [1]="
                                        + array[1]);
                        mTwiceBaseCache = null;
                        mTwiceBaseCacheSize = 0;
                    }
                }
            }
        } else if (size == 4) {
            synchronized (sBaseCacheLock) {
                if (mBaseCache != null) {
                    Object[] array2 = mBaseCache;
                    this.mArray = array2;
                    try {
                        mBaseCache = (Object[]) array2[0];
                        this.mHashes = (int[]) array2[1];
                    } catch (ClassCastException e2) {
                    }
                    if (this.mHashes != null) {
                        array2[1] = null;
                        array2[0] = null;
                        mBaseCacheSize--;
                        return;
                    } else {
                        Slog.wtf(
                                TAG,
                                "Found corrupt ArrayMap cache: [0]="
                                        + array2[0]
                                        + " [1]="
                                        + array2[1]);
                        mBaseCache = null;
                        mBaseCacheSize = 0;
                    }
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[size << 1];
    }

    private static void freeArrays(int[] hashes, Object[] array, int size) {
        if (hashes.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (mTwiceBaseCacheSize < 10) {
                    array[0] = mTwiceBaseCache;
                    array[1] = hashes;
                    for (int i = (size << 1) - 1; i >= 2; i--) {
                        array[i] = null;
                    }
                    mTwiceBaseCache = array;
                    mTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (hashes.length == 4) {
            synchronized (sBaseCacheLock) {
                if (mBaseCacheSize < 10) {
                    array[0] = mBaseCache;
                    array[1] = hashes;
                    for (int i2 = (size << 1) - 1; i2 >= 2; i2--) {
                        array[i2] = null;
                    }
                    mBaseCache = array;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public ArrayMap() {
        this(0, false);
    }

    public ArrayMap(int capacity) {
        this(capacity, false);
    }

    public ArrayMap(int capacity, boolean identityHashCode) {
        this.mIdentityHashCode = identityHashCode;
        if (capacity < 0) {
            this.mHashes = EMPTY_IMMUTABLE_INTS;
            this.mArray = EmptyArray.OBJECT;
        } else if (capacity == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            allocArrays(capacity);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArrayMap(ArrayMap<K, V> arrayMap) {
        this();
        if (arrayMap != 0) {
            putAll((ArrayMap) arrayMap);
        }
    }

    @Override // java.util.Map
    public void clear() {
        if (this.mSize > 0) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            int osize = this.mSize;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void erase() {
        if (this.mSize > 0) {
            int N = this.mSize << 1;
            Object[] array = this.mArray;
            for (int i = 0; i < N; i++) {
                array[i] = null;
            }
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int minimumCapacity) {
        int osize = this.mSize;
        if (this.mHashes.length < minimumCapacity) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(minimumCapacity);
            if (this.mSize > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, osize);
                System.arraycopy(oarray, 0, this.mArray, 0, osize << 1);
            }
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize != osize) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return indexOfKey(key) >= 0;
    }

    public int indexOfKey(Object key) {
        if (key == null) {
            return indexOfNull();
        }
        return indexOf(key, this.mIdentityHashCode ? System.identityHashCode(key) : key.hashCode());
    }

    public int indexOfValue(Object value) {
        int N = this.mSize * 2;
        Object[] array = this.mArray;
        if (value == null) {
            for (int i = 1; i < N; i += 2) {
                if (array[i] == null) {
                    return i >> 1;
                }
            }
            return -1;
        }
        for (int i2 = 1; i2 < N; i2 += 2) {
            if (value.equals(array[i2])) {
                return i2 >> 1;
            }
        }
        return -1;
    }

    @Override // java.util.Map
    public boolean containsValue(Object value) {
        return indexOfValue(value) >= 0;
    }

    @Override // java.util.Map
    public V get(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return (V) this.mArray[(indexOfKey << 1) + 1];
        }
        return null;
    }

    public K keyAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return (K) this.mArray[i << 1];
    }

    public V valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return (V) this.mArray[(i << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        int i2 = (i << 1) + 1;
        V v2 = (V) this.mArray[i2];
        this.mArray[i2] = v;
        return v2;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        int identityHashCode;
        int indexOf;
        int i = this.mSize;
        if (k == null) {
            identityHashCode = 0;
            indexOf = indexOfNull();
        } else {
            identityHashCode = this.mIdentityHashCode ? System.identityHashCode(k) : k.hashCode();
            indexOf = indexOf(k, identityHashCode);
        }
        if (indexOf >= 0) {
            int i2 = (indexOf << 1) + 1;
            V v2 = (V) this.mArray[i2];
            this.mArray[i2] = v;
            return v2;
        }
        int i3 = ~indexOf;
        if (i >= this.mHashes.length) {
            int i4 = 8;
            if (i >= 8) {
                i4 = (i >> 1) + i;
            } else if (i < 4) {
                i4 = 4;
            }
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            allocArrays(i4);
            if (i != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i);
        }
        if (i3 < i) {
            System.arraycopy(this.mHashes, i3, this.mHashes, i3 + 1, i - i3);
            System.arraycopy(
                    this.mArray, i3 << 1, this.mArray, (i3 + 1) << 1, (this.mSize - i3) << 1);
        }
        if (i != this.mSize || i3 >= this.mHashes.length) {
            throw new ConcurrentModificationException();
        }
        this.mHashes[i3] = identityHashCode;
        this.mArray[i3 << 1] = k;
        this.mArray[(i3 << 1) + 1] = v;
        this.mSize++;
        return null;
    }

    public void append(K key, V value) {
        int hash;
        int index = this.mSize;
        if (key == null) {
            hash = 0;
        } else {
            hash = this.mIdentityHashCode ? System.identityHashCode(key) : key.hashCode();
        }
        if (index >= this.mHashes.length) {
            throw new IllegalStateException("Array is full");
        }
        if (index > 0 && this.mHashes[index - 1] > hash) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.w(
                    TAG,
                    "New hash "
                            + hash
                            + " is before end of array hash "
                            + this.mHashes[index - 1]
                            + " at index "
                            + index
                            + "",
                    e);
            put(key, value);
            return;
        }
        this.mSize = index + 1;
        this.mHashes[index] = hash;
        int index2 = index << 1;
        this.mArray[index2] = key;
        this.mArray[index2 + 1] = value;
    }

    public void validate() {
        int N = this.mSize;
        if (N <= 1) {
            return;
        }
        int basehash = this.mHashes[0];
        int basei = 0;
        for (int i = 1; i < N; i++) {
            int hash = this.mHashes[i];
            if (hash != basehash) {
                basehash = hash;
                basei = i;
            } else {
                Object cur = this.mArray[i << 1];
                for (int j = i - 1; j >= basei; j--) {
                    Object prev = this.mArray[j << 1];
                    if (cur == prev) {
                        throw new IllegalArgumentException("Duplicate key in ArrayMap: " + cur);
                    }
                    if (cur != null && prev != null && cur.equals(prev)) {
                        throw new IllegalArgumentException("Duplicate key in ArrayMap: " + cur);
                    }
                }
            }
        }
    }

    public void putAll(ArrayMap<? extends K, ? extends V> array) {
        int N = array.mSize;
        ensureCapacity(this.mSize + N);
        if (this.mSize == 0) {
            if (N > 0) {
                System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
                System.arraycopy(array.mArray, 0, this.mArray, 0, N << 1);
                this.mSize = N;
                return;
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            put(array.keyAt(i), array.valueAt(i));
        }
    }

    @Override // java.util.Map
    public V remove(Object key) {
        int index = indexOfKey(key);
        if (index >= 0) {
            return removeAt(index);
        }
        return null;
    }

    public V removeAt(int i) {
        int i2;
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        V v = (V) this.mArray[(i << 1) + 1];
        int i3 = this.mSize;
        if (i3 <= 1) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            freeArrays(iArr, objArr, i3);
            i2 = 0;
        } else {
            int i4 = i3 - 1;
            if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
                int i5 = i3 > 8 ? i3 + (i3 >> 1) : 8;
                int[] iArr2 = this.mHashes;
                Object[] objArr2 = this.mArray;
                allocArrays(i5);
                if (i3 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr2, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr2, 0, this.mArray, 0, i << 1);
                }
                if (i < i4) {
                    System.arraycopy(iArr2, i + 1, this.mHashes, i, i4 - i);
                    System.arraycopy(objArr2, (i + 1) << 1, this.mArray, i << 1, (i4 - i) << 1);
                }
            } else {
                if (i < i4) {
                    System.arraycopy(this.mHashes, i + 1, this.mHashes, i, i4 - i);
                    System.arraycopy(this.mArray, (i + 1) << 1, this.mArray, i << 1, (i4 - i) << 1);
                }
                this.mArray[i4 << 1] = null;
                this.mArray[(i4 << 1) + 1] = null;
            }
            i2 = i4;
        }
        if (i3 != this.mSize) {
            throw new ConcurrentModificationException();
        }
        this.mSize = i2;
        return v;
    }

    @Override // java.util.Map
    public int size() {
        return this.mSize;
    }

    @Override // java.util.Map
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Map)) {
            return false;
        }
        Map<?, ?> map = (Map) object;
        if (size() != map.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                K key = keyAt(i);
                V mine = valueAt(i);
                Object theirs = map.get(key);
                if (mine == null) {
                    if (theirs != null || !map.containsKey(key)) {
                        return false;
                    }
                } else if (!mine.equals(theirs)) {
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

    @Override // java.util.Map
    public int hashCode() {
        int[] hashes = this.mHashes;
        Object[] array = this.mArray;
        int result = 0;
        int i = 0;
        int v = 1;
        int s = this.mSize;
        while (i < s) {
            Object value = array[v];
            result += hashes[i] ^ (value == null ? 0 : value.hashCode());
            i++;
            v += 2;
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 28);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            Object key = keyAt(i);
            if (key != this) {
                buffer.append(key);
            } else {
                buffer.append("(this Map)");
            }
            buffer.append('=');
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(ArrayUtils.deepToString(value));
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections =
                    new MapCollections<K, V>() { // from class: android.util.ArrayMap.1
                        @Override // android.util.MapCollections
                        protected int colGetSize() {
                            return ArrayMap.this.mSize;
                        }

                        @Override // android.util.MapCollections
                        protected Object colGetEntry(int index, int offset) {
                            return ArrayMap.this.mArray[(index << 1) + offset];
                        }

                        @Override // android.util.MapCollections
                        protected int colIndexOfKey(Object key) {
                            return ArrayMap.this.indexOfKey(key);
                        }

                        @Override // android.util.MapCollections
                        protected int colIndexOfValue(Object value) {
                            return ArrayMap.this.indexOfValue(value);
                        }

                        @Override // android.util.MapCollections
                        protected Map<K, V> colGetMap() {
                            return ArrayMap.this;
                        }

                        @Override // android.util.MapCollections
                        protected void colPut(K key, V value) {
                            ArrayMap.this.put(key, value);
                        }

                        @Override // android.util.MapCollections
                        protected V colSetValue(int i, V v) {
                            return (V) ArrayMap.this.setValueAt(i, v);
                        }

                        @Override // android.util.MapCollections
                        protected void colRemoveAt(int index) {
                            ArrayMap.this.removeAt(index);
                        }

                        @Override // android.util.MapCollections
                        protected void colClear() {
                            ArrayMap.this.clear();
                        }
                    };
        }
        return this.mCollections;
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.containsAllHelper(this, collection);
    }

    @Override // java.util.Map
    public void forEach(BiConsumer<? super K, ? super V> action) {
        if (action == null) {
            throw new NullPointerException("action must not be null");
        }
        int size = this.mSize;
        for (int i = 0; i < size; i++) {
            if (size != this.mSize) {
                throw new ConcurrentModificationException();
            }
            action.accept(keyAt(i), valueAt(i));
        }
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.removeAllHelper(this, collection);
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException("function must not be null");
        }
        int size = this.mSize;
        for (int i = 0; i < size; i++) {
            int valIndex = (i << 1) + 1;
            try {
                this.mArray[valIndex] = function.apply(this.mArray[i << 1], this.mArray[valIndex]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
        if (size != this.mSize) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.retainAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return getCollection().getKeySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return getCollection().getValues();
    }
}
