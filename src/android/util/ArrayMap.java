package android.util;

import com.android.internal.util.ArrayUtils;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/* loaded from: classes4.dex */
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

    private static int binarySearchHashes(int[] iArr, int i, int i2) {
        try {
            return ContainerHelpers.binarySearch(iArr, i, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException(e);
        }
    }

    int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i2, i);
        if (binarySearchHashes < 0 || obj.equals(this.mArray[binarySearchHashes << 1])) {
            return binarySearchHashes;
        }
        int i3 = binarySearchHashes + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearchHashes - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i, 0);
        if (binarySearchHashes < 0 || this.mArray[binarySearchHashes << 1] == null) {
            return binarySearchHashes;
        }
        int i2 = binarySearchHashes + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearchHashes - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void allocArrays(int i) {
        if (this.mHashes == EMPTY_IMMUTABLE_INTS) {
            throw new UnsupportedOperationException("ArrayMap is immutable");
        }
        if (i == 8) {
            synchronized (sTwiceBaseCacheLock) {
                Object[] objArr = mTwiceBaseCache;
                if (objArr != null) {
                    this.mArray = objArr;
                    try {
                        mTwiceBaseCache = (Object[]) objArr[0];
                        int[] iArr = (int[]) objArr[1];
                        this.mHashes = iArr;
                        if (iArr != null) {
                            objArr[1] = null;
                            objArr[0] = null;
                            mTwiceBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException unused) {
                    }
                    Slog.wtf(TAG, "Found corrupt ArrayMap cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                    mTwiceBaseCache = null;
                    mTwiceBaseCacheSize = 0;
                }
            }
        } else if (i == 4) {
            synchronized (sBaseCacheLock) {
                Object[] objArr2 = mBaseCache;
                if (objArr2 != null) {
                    this.mArray = objArr2;
                    try {
                        mBaseCache = (Object[]) objArr2[0];
                        int[] iArr2 = (int[]) objArr2[1];
                        this.mHashes = iArr2;
                        if (iArr2 != null) {
                            objArr2[1] = null;
                            objArr2[0] = null;
                            mBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException unused2) {
                    }
                    Slog.wtf(TAG, "Found corrupt ArrayMap cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                    mBaseCache = null;
                    mBaseCacheSize = 0;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[i << 1];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public ArrayMap() {
        this(0, false);
    }

    public ArrayMap(int i) {
        this(i, false);
    }

    public ArrayMap(int i, boolean z) {
        this.mIdentityHashCode = z;
        if (i < 0) {
            this.mHashes = EMPTY_IMMUTABLE_INTS;
            this.mArray = EmptyArray.OBJECT;
        } else if (i == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            allocArrays(i);
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
        int i = this.mSize;
        if (i > 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void erase() {
        int i = this.mSize;
        if (i > 0) {
            int i2 = i << 1;
            Object[] objArr = this.mArray;
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = null;
            }
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        int[] iArr = this.mHashes;
        if (iArr.length < i) {
            Object[] objArr = this.mArray;
            allocArrays(i);
            if (this.mSize > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                System.arraycopy(objArr, 0, this.mArray, 0, i2 << 1);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public int indexOfKey(Object obj) {
        if (obj == null) {
            return indexOfNull();
        }
        return indexOf(obj, this.mIdentityHashCode ? System.identityHashCode(obj) : obj.hashCode());
    }

    public int indexOfValue(Object obj) {
        int i = this.mSize * 2;
        Object[] objArr = this.mArray;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return indexOfValue(obj) >= 0;
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
        Object[] objArr = this.mArray;
        V v2 = (V) objArr[i2];
        objArr[i2] = v;
        return v2;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        int i;
        int indexOf;
        int i2 = this.mSize;
        if (k == null) {
            indexOf = indexOfNull();
            i = 0;
        } else {
            int identityHashCode = this.mIdentityHashCode ? System.identityHashCode(k) : k.hashCode();
            i = identityHashCode;
            indexOf = indexOf(k, identityHashCode);
        }
        if (indexOf >= 0) {
            int i3 = (indexOf << 1) + 1;
            Object[] objArr = this.mArray;
            V v2 = (V) objArr[i3];
            objArr[i3] = v;
            return v2;
        }
        int i4 = ~indexOf;
        int[] iArr = this.mHashes;
        if (i2 >= iArr.length) {
            int i5 = 8;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i5 = 4;
            }
            Object[] objArr2 = this.mArray;
            allocArrays(i5);
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr2, 0, this.mArray, 0, objArr2.length);
            }
            freeArrays(iArr, objArr2, i2);
        }
        if (i4 < i2) {
            int[] iArr3 = this.mHashes;
            int i6 = i4 + 1;
            System.arraycopy(iArr3, i4, iArr3, i6, i2 - i4);
            Object[] objArr3 = this.mArray;
            System.arraycopy(objArr3, i4 << 1, objArr3, i6 << 1, (this.mSize - i4) << 1);
        }
        int i7 = this.mSize;
        if (i2 == i7) {
            int[] iArr4 = this.mHashes;
            if (i4 < iArr4.length) {
                iArr4[i4] = i;
                Object[] objArr4 = this.mArray;
                int i8 = i4 << 1;
                objArr4[i8] = k;
                objArr4[i8 + 1] = v;
                this.mSize = i7 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void append(K k, V v) {
        int identityHashCode;
        int i = this.mSize;
        if (k == null) {
            identityHashCode = 0;
        } else {
            identityHashCode = this.mIdentityHashCode ? System.identityHashCode(k) : k.hashCode();
        }
        int[] iArr = this.mHashes;
        if (i >= iArr.length) {
            throw new IllegalStateException("Array is full");
        }
        if (i > 0) {
            int i2 = i - 1;
            if (iArr[i2] > identityHashCode) {
                Log.w(TAG, "New hash " + identityHashCode + " is before end of array hash " + this.mHashes[i2] + " at index " + i + "", new RuntimeException("here"));
                put(k, v);
                return;
            }
        }
        this.mSize = i + 1;
        iArr[i] = identityHashCode;
        int i3 = i << 1;
        Object[] objArr = this.mArray;
        objArr[i3] = k;
        objArr[i3 + 1] = v;
    }

    public void validate() {
        int i = this.mSize;
        if (i <= 1) {
            return;
        }
        int i2 = 0;
        int i3 = this.mHashes[0];
        for (int i4 = 1; i4 < i; i4++) {
            int i5 = this.mHashes[i4];
            if (i5 != i3) {
                i2 = i4;
                i3 = i5;
            } else {
                Object obj = this.mArray[i4 << 1];
                for (int i6 = i4 - 1; i6 >= i2; i6--) {
                    Object obj2 = this.mArray[i6 << 1];
                    if (obj == obj2) {
                        throw new IllegalArgumentException("Duplicate key in ArrayMap: " + obj);
                    }
                    if (obj != null && obj2 != null && obj.equals(obj2)) {
                        throw new IllegalArgumentException("Duplicate key in ArrayMap: " + obj);
                    }
                }
            }
        }
    }

    public void putAll(ArrayMap<? extends K, ? extends V> arrayMap) {
        int i = arrayMap.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                put(arrayMap.keyAt(i2), arrayMap.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(arrayMap.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(arrayMap.mArray, 0, this.mArray, 0, i << 1);
            this.mSize = i;
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public V removeAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        Object[] objArr = this.mArray;
        int i2 = i << 1;
        V v = (V) objArr[i2 + 1];
        int i3 = this.mSize;
        int i4 = 0;
        if (i3 <= 1) {
            int[] iArr = this.mHashes;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            freeArrays(iArr, objArr, i3);
        } else {
            int i5 = i3 - 1;
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 8 && i3 < iArr2.length / 3) {
                allocArrays(i3 > 8 ? i3 + (i3 >> 1) : 8);
                if (i3 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr2, 0, this.mHashes, 0, i);
                    System.arraycopy(objArr, 0, this.mArray, 0, i2);
                }
                if (i < i5) {
                    int i6 = i + 1;
                    int i7 = i5 - i;
                    System.arraycopy(iArr2, i6, this.mHashes, i, i7);
                    System.arraycopy(objArr, i6 << 1, this.mArray, i2, i7 << 1);
                }
            } else {
                if (i < i5) {
                    int i8 = i + 1;
                    int i9 = i5 - i;
                    System.arraycopy(iArr2, i8, iArr2, i, i9);
                    Object[] objArr2 = this.mArray;
                    System.arraycopy(objArr2, i8 << 1, objArr2, i2, i9 << 1);
                }
                Object[] objArr3 = this.mArray;
                int i10 = i5 << 1;
                objArr3[i10] = null;
                objArr3[i10 + 1] = null;
            }
            i4 = i5;
        }
        if (i3 != this.mSize) {
            throw new ConcurrentModificationException();
        }
        this.mSize = i4;
        return v;
    }

    @Override // java.util.Map
    public int size() {
        return this.mSize;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (size() != map.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    K keyAt = keyAt(i);
                    V valueAt = valueAt(i);
                    Object obj2 = map.get(keyAt);
                    if (valueAt == null) {
                        if (obj2 != null || !map.containsKey(keyAt)) {
                            return false;
                        }
                    } else if (!valueAt.equals(obj2)) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Map
    public int hashCode() {
        int[] iArr = this.mHashes;
        Object[] objArr = this.mArray;
        int i = this.mSize;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            i4 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K keyAt = keyAt(i);
            if (keyAt != this) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(ArrayUtils.deepToString(valueAt));
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>() { // from class: android.util.ArrayMap.1
                @Override // android.util.MapCollections
                protected int colGetSize() {
                    return ArrayMap.this.mSize;
                }

                @Override // android.util.MapCollections
                protected Object colGetEntry(int i, int i2) {
                    return ArrayMap.this.mArray[(i << 1) + i2];
                }

                @Override // android.util.MapCollections
                protected int colIndexOfKey(Object obj) {
                    return ArrayMap.this.indexOfKey(obj);
                }

                @Override // android.util.MapCollections
                protected int colIndexOfValue(Object obj) {
                    return ArrayMap.this.indexOfValue(obj);
                }

                @Override // android.util.MapCollections
                protected Map<K, V> colGetMap() {
                    return ArrayMap.this;
                }

                @Override // android.util.MapCollections
                protected void colPut(K k, V v) {
                    ArrayMap.this.put(k, v);
                }

                @Override // android.util.MapCollections
                protected V colSetValue(int i, V v) {
                    return (V) ArrayMap.this.setValueAt(i, v);
                }

                @Override // android.util.MapCollections
                protected void colRemoveAt(int i) {
                    ArrayMap.this.removeAt(i);
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
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer == null) {
            throw new NullPointerException("action must not be null");
        }
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            if (i != this.mSize) {
                throw new ConcurrentModificationException();
            }
            biConsumer.accept(keyAt(i2), valueAt(i2));
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
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new NullPointerException("function must not be null");
        }
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 << 1;
            int i4 = i3 + 1;
            try {
                Object[] objArr = this.mArray;
                objArr[i4] = biFunction.apply(objArr[i3], objArr[i4]);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
        if (i != this.mSize) {
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
