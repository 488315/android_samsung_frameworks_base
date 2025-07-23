package android.util;

import android.os.Parcel;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Parcelling;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.LongObjPredicate;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = EmptyArray.LONG;
            this.mValues = EmptyArray.OBJECT;
        } else {
            this.mKeys = ArrayUtils.newUnpaddedLongArray(i);
            this.mValues = ArrayUtils.newUnpaddedObjectArray(i);
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseArray<E> m5513clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            try {
                longSparseArray.mKeys = (long[]) this.mKeys.clone();
                longSparseArray.mValues = (Object[]) this.mValues.clone();
                return longSparseArray;
            } catch (CloneNotSupportedException unused) {
                return longSparseArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        E e2;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        return (binarySearch < 0 || (e2 = (E) this.mValues[binarySearch]) == DELETED) ? e : e2;
    }

    public void delete(long j) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[binarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[binarySearch] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeIf(LongObjPredicate<? super E> longObjPredicate) {
        Objects.requireNonNull(longObjPredicate);
        for (int i = 0; i < this.mSize; i++) {
            Object obj = this.mValues[i];
            Object obj2 = DELETED;
            if (obj != obj2 && longObjPredicate.test(this.mKeys[i], obj)) {
                this.mValues[i] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public void removeAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        Object[] objArr = this.mValues;
        Object obj = objArr[i];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.mGarbage = true;
        }
    }

    private void gc() {
        int i = this.mSize;
        long[] jArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public int firstIndexOnOrAfter(long j) {
        if (this.mGarbage) {
            gc();
        }
        int binarySearch = Arrays.binarySearch(this.mKeys, 0, size(), j);
        return binarySearch >= 0 ? binarySearch : (-binarySearch) - 1;
    }

    public int lastIndexOnOrBefore(long j) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(j);
        return (firstIndexOnOrAfter >= size() || keyAt(firstIndexOnOrAfter) != j) ? firstIndexOnOrAfter - 1 : firstIndexOnOrAfter;
    }

    public void put(long j, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
            return;
        }
        int i = ~binarySearch;
        int i2 = this.mSize;
        if (i < i2) {
            Object[] objArr = this.mValues;
            if (objArr[i] == DELETED) {
                this.mKeys[i] = j;
                objArr[i] = e;
                return;
            }
        }
        if (this.mGarbage && i2 >= this.mKeys.length) {
            gc();
            i = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        }
        this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i, j);
        this.mValues = GrowingArrayUtils.insert((E[]) this.mValues, this.mSize, i, e);
        this.mSize++;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public long keyAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public E valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        return (E) this.mValues[i];
    }

    public void setValueAt(int i, E e) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i] = e;
    }

    public int indexOfKey(long j) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfValueByValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (e == null) {
                if (this.mValues[i] == null) {
                    return i;
                }
            } else {
                if (e.equals(this.mValues[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(long j, E e) {
        int i = this.mSize;
        if (i != 0 && j <= this.mKeys[i - 1]) {
            put(j, e);
            return;
        }
        if (this.mGarbage && i >= this.mKeys.length) {
            gc();
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, j);
        this.mValues = GrowingArrayUtils.append((E[]) this.mValues, this.mSize, e);
        this.mSize++;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public static class StringParcelling implements Parcelling<LongSparseArray<String>> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(LongSparseArray<String> longSparseArray, Parcel parcel, int i) {
            if (longSparseArray == null) {
                parcel.writeInt(-1);
                return;
            }
            int i2 = ((LongSparseArray) longSparseArray).mSize;
            parcel.writeInt(i2);
            parcel.writeLongArray(((LongSparseArray) longSparseArray).mKeys);
            parcel.writeStringArray((String[]) Arrays.copyOfRange(((LongSparseArray) longSparseArray).mValues, 0, i2, String[].class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public LongSparseArray<String> unparcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            LongSparseArray<String> longSparseArray = new LongSparseArray<>(0);
            ((LongSparseArray) longSparseArray).mSize = readInt;
            ((LongSparseArray) longSparseArray).mKeys = parcel.createLongArray();
            ((LongSparseArray) longSparseArray).mValues = parcel.createStringArray();
            Preconditions.checkArgument(((LongSparseArray) longSparseArray).mKeys.length >= readInt);
            Preconditions.checkArgument(((LongSparseArray) longSparseArray).mValues.length >= readInt);
            if (readInt > 0) {
                long j = ((LongSparseArray) longSparseArray).mKeys[0];
                for (int i = 1; i < readInt; i++) {
                    Preconditions.checkArgument(j < ((LongSparseArray) longSparseArray).mKeys[i]);
                }
            }
            return longSparseArray;
        }
    }
}
