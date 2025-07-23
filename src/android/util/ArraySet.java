package android.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
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

    private int binarySearch(int[] iArr, int i) {
        try {
            return ContainerHelpers.binarySearch(iArr, this.mSize, i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    private int indexOf(Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = binarySearch(this.mHashes, i);
        if (binarySearch < 0 || obj.equals(this.mArray[binarySearch])) {
            return binarySearch;
        }
        int i3 = binarySearch + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearch - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4])) {
                return i4;
            }
        }
        return ~i3;
    }

    private int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearch = binarySearch(this.mHashes, 0);
        if (binarySearch < 0 || this.mArray[binarySearch] == null) {
            return binarySearch;
        }
        int i2 = binarySearch + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearch - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (sTwiceBaseCacheLock) {
                Object[] objArr = sTwiceBaseCache;
                if (objArr != null) {
                    try {
                        this.mArray = objArr;
                        sTwiceBaseCache = (Object[]) objArr[0];
                        int[] iArr = (int[]) objArr[1];
                        this.mHashes = iArr;
                        if (iArr != null) {
                            objArr[1] = null;
                            objArr[0] = null;
                            sTwiceBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException unused) {
                    }
                    Slog.wtf(TAG, "Found corrupt ArraySet cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                    sTwiceBaseCache = null;
                    sTwiceBaseCacheSize = 0;
                }
            }
        } else if (i == 4) {
            synchronized (sBaseCacheLock) {
                Object[] objArr2 = sBaseCache;
                if (objArr2 != null) {
                    try {
                        this.mArray = objArr2;
                        sBaseCache = (Object[]) objArr2[0];
                        int[] iArr2 = (int[]) objArr2[1];
                        this.mHashes = iArr2;
                        if (iArr2 != null) {
                            objArr2[1] = null;
                            objArr2[0] = null;
                            sBaseCacheSize--;
                            return;
                        }
                    } catch (ClassCastException unused2) {
                    }
                    Slog.wtf(TAG, "Found corrupt ArraySet cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                    sBaseCache = null;
                    sBaseCacheSize = 0;
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new Object[i];
    }

    private static void freeArrays(int[] iArr, Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCacheSize < 10) {
                    objArr[0] = sTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    sTwiceBaseCache = objArr;
                    sTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCacheSize < 10) {
                    objArr[0] = sBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    sBaseCache = objArr;
                    sBaseCacheSize++;
                }
            }
        }
    }

    public ArraySet() {
        this(0, false);
    }

    public ArraySet(int i) {
        this(i, false);
    }

    public ArraySet(int i, boolean z) {
        this.mIdentityHashCode = z;
        if (i == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((ArraySet) arraySet);
        }
    }

    public ArraySet(Collection<? extends E> collection) {
        this();
        if (collection != null) {
            addAll(collection);
        }
    }

    public ArraySet(E[] eArr) {
        this();
        if (eArr != null) {
            for (E e : eArr) {
                add(e);
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        int i = this.mSize;
        if (i != 0) {
            int[] iArr = this.mHashes;
            Object[] objArr = this.mArray;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize != 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        int[] iArr = this.mHashes;
        if (iArr.length < i) {
            Object[] objArr = this.mArray;
            allocArrays(i);
            int i3 = this.mSize;
            if (i3 > 0) {
                System.arraycopy(iArr, 0, this.mHashes, 0, i3);
                System.arraycopy(objArr, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
        if (this.mSize != i2) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(Object obj) {
        if (obj == null) {
            return indexOfNull();
        }
        return indexOf(obj, this.mIdentityHashCode ? System.identityHashCode(obj) : obj.hashCode());
    }

    public E valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return valueAtUnchecked(i);
    }

    public E valueAtUnchecked(int i) {
        return (E) this.mArray[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e) {
        int i;
        int indexOf;
        int i2 = this.mSize;
        if (e == null) {
            indexOf = indexOfNull();
            i = 0;
        } else {
            int identityHashCode = this.mIdentityHashCode ? System.identityHashCode(e) : e.hashCode();
            i = identityHashCode;
            indexOf = indexOf(e, identityHashCode);
        }
        if (indexOf >= 0) {
            return false;
        }
        int i3 = ~indexOf;
        int[] iArr = this.mHashes;
        if (i2 >= iArr.length) {
            int i4 = 8;
            if (i2 >= 8) {
                i4 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i4 = 4;
            }
            Object[] objArr = this.mArray;
            allocArrays(i4);
            if (i2 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            int[] iArr2 = this.mHashes;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (i3 < i2) {
            int[] iArr3 = this.mHashes;
            int i5 = i3 + 1;
            int i6 = i2 - i3;
            System.arraycopy(iArr3, i3, iArr3, i5, i6);
            Object[] objArr2 = this.mArray;
            System.arraycopy(objArr2, i3, objArr2, i5, i6);
        }
        int i7 = this.mSize;
        if (i2 == i7) {
            int[] iArr4 = this.mHashes;
            if (i3 < iArr4.length) {
                iArr4[i3] = i;
                this.mArray[i3] = e;
                this.mSize = i7 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void append(E e) {
        int identityHashCode;
        int i = this.mSize;
        if (e == null) {
            identityHashCode = 0;
        } else {
            identityHashCode = this.mIdentityHashCode ? System.identityHashCode(e) : e.hashCode();
        }
        int[] iArr = this.mHashes;
        if (i >= iArr.length) {
            throw new IllegalStateException("Array is full");
        }
        if (i > 0 && iArr[i - 1] > identityHashCode) {
            add(e);
        } else {
            if (i != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = i + 1;
            iArr[i] = identityHashCode;
            this.mArray[i] = e;
        }
    }

    public void addAll(ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                add(arraySet.valueAt(i2));
            }
        } else if (i > 0) {
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i);
            System.arraycopy(arraySet.mArray, 0, this.mArray, 0, i);
            if (this.mSize != 0) {
                throw new ConcurrentModificationException();
            }
            this.mSize = i;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    private boolean shouldShrink() {
        int[] iArr = this.mHashes;
        return iArr.length > 8 && this.mSize < iArr.length / 3;
    }

    private int getNewShrunkenSize() {
        int i = this.mSize;
        if (i > 8) {
            return i + (i >> 1);
        }
        return 8;
    }

    public E removeAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        int i2 = this.mSize;
        E e = (E) this.mArray[i];
        if (i2 <= 1) {
            clear();
            return e;
        }
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
                int i4 = i + 1;
                int i5 = i3 - i;
                System.arraycopy(iArr, i4, this.mHashes, i, i5);
                System.arraycopy(objArr, i4, this.mArray, i, i5);
            }
        } else {
            if (i < i3) {
                int[] iArr2 = this.mHashes;
                int i6 = i + 1;
                int i7 = i3 - i;
                System.arraycopy(iArr2, i6, iArr2, i, i7);
                Object[] objArr2 = this.mArray;
                System.arraycopy(objArr2, i6, objArr2, i, i7);
            }
            this.mArray[i3] = null;
        }
        if (i2 != this.mSize) {
            throw new ConcurrentModificationException();
        }
        this.mSize = i3;
        return e;
    }

    public boolean removeAll(ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3));
        }
        return i2 != this.mSize;
    }

    @Override // java.util.Collection
    public boolean removeIf(Predicate<? super E> predicate) {
        int i;
        if (this.mSize == 0) {
            return false;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i = this.mSize;
            if (i2 >= i) {
                break;
            }
            if (predicate.test(this.mArray[i2])) {
                i3++;
            } else {
                if (i4 != i2) {
                    Object[] objArr = this.mArray;
                    objArr[i4] = objArr[i2];
                    int[] iArr = this.mHashes;
                    iArr[i4] = iArr[i2];
                }
                i4++;
            }
            i2++;
        }
        if (i3 == 0) {
            return false;
        }
        if (i3 == i) {
            clear();
            return true;
        }
        this.mSize = i - i3;
        if (shouldShrink()) {
            int newShrunkenSize = getNewShrunkenSize();
            int[] iArr2 = this.mHashes;
            Object[] objArr2 = this.mArray;
            allocArrays(newShrunkenSize);
            System.arraycopy(iArr2, 0, this.mHashes, 0, this.mSize);
            System.arraycopy(objArr2, 0, this.mArray, 0, this.mSize);
        } else {
            int i5 = this.mSize;
            while (true) {
                Object[] objArr3 = this.mArray;
                if (i5 >= objArr3.length) {
                    break;
                }
                objArr3[i5] = null;
                i5++;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.mSize;
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        if (consumer == null) {
            throw new NullPointerException("action must not be null");
        }
        for (int i = 0; i < this.mSize; i++) {
            consumer.accept(valueAt(i));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public Object[] toArray() {
        int i = this.mSize;
        Object[] objArr = new Object[i];
        System.arraycopy(this.mArray, 0, objArr, 0, i);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        if (tArr.length < this.mSize) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.mSize));
        }
        System.arraycopy(this.mArray, 0, tArr, 0, this.mSize);
        int length = tArr.length;
        int i = this.mSize;
        if (length > i) {
            tArr[i] = null;
        }
        return tArr;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            for (int i = 0; i < this.mSize; i++) {
                try {
                    if (!set.contains(valueAt(i))) {
                        return false;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.mHashes;
        int i = this.mSize;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 14);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>() { // from class: android.util.ArraySet.1
                @Override // android.util.MapCollections
                protected int colGetSize() {
                    return ArraySet.this.mSize;
                }

                @Override // android.util.MapCollections
                protected Object colGetEntry(int i, int i2) {
                    return ArraySet.this.mArray[i];
                }

                @Override // android.util.MapCollections
                protected int colIndexOfKey(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                @Override // android.util.MapCollections
                protected int colIndexOfValue(Object obj) {
                    return ArraySet.this.indexOf(obj);
                }

                @Override // android.util.MapCollections
                protected Map<E, E> colGetMap() {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override // android.util.MapCollections
                protected void colPut(E e, E e2) {
                    ArraySet.this.add(e);
                }

                @Override // android.util.MapCollections
                protected E colSetValue(int i, E e) {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override // android.util.MapCollections
                protected void colRemoveAt(int i) {
                    ArraySet.this.removeAt(i);
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
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }
}
