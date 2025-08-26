package android.util;

import android.os.Parcel;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public class LongSparseLongArray implements Cloneable {
    private long[] mKeys;
    private int mSize;
    private long[] mValues;

    public LongSparseLongArray() {
        this(10);
    }

    public LongSparseLongArray(int i) {
        if (i == 0) {
            this.mKeys = EmptyArray.LONG;
            this.mValues = EmptyArray.LONG;
        } else {
            long[] jArrNewUnpaddedLongArray = ArrayUtils.newUnpaddedLongArray(i);
            this.mKeys = jArrNewUnpaddedLongArray;
            this.mValues = new long[jArrNewUnpaddedLongArray.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseLongArray m5527clone() {
        try {
            LongSparseLongArray longSparseLongArray = (LongSparseLongArray) super.clone();
            try {
                longSparseLongArray.mKeys = (long[]) this.mKeys.clone();
                longSparseLongArray.mValues = (long[]) this.mValues.clone();
                return longSparseLongArray;
            } catch (CloneNotSupportedException unused) {
                return longSparseLongArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public long get(long j) {
        return get(j, 0L);
    }

    public long get(long j, long j2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        return iBinarySearch < 0 ? j2 : this.mValues[iBinarySearch];
    }

    public void delete(long j) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch >= 0) {
            removeAt(iBinarySearch);
        }
    }

    public void removeAt(int i) {
        long[] jArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(jArr, i2, jArr, i, this.mSize - i2);
        long[] jArr2 = this.mValues;
        System.arraycopy(jArr2, i2, jArr2, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(long j, long j2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = j2;
            return;
        }
        int i = ~iBinarySearch;
        this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i, j);
        this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, i, j2);
        this.mSize++;
    }

    public int size() {
        return this.mSize;
    }

    public long keyAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mKeys[i];
    }

    public long valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public int indexOfKey(long j) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
    }

    public int indexOfValue(long j) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == j) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(long j, long j2) {
        int i = this.mSize;
        if (i != 0 && j <= this.mKeys[i - 1]) {
            put(j, j2);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, i, j);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, j2);
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
            sb.append(valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }

    public static class Parcelling implements com.android.internal.util.Parcelling<LongSparseLongArray> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(LongSparseLongArray longSparseLongArray, Parcel parcel, int i) {
            if (longSparseLongArray == null) {
                parcel.writeInt(-1);
                return;
            }
            parcel.writeInt(longSparseLongArray.mSize);
            parcel.writeLongArray(longSparseLongArray.mKeys);
            parcel.writeLongArray(longSparseLongArray.mValues);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public LongSparseLongArray unparcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == -1) {
                return null;
            }
            LongSparseLongArray longSparseLongArray = new LongSparseLongArray(0);
            longSparseLongArray.mSize = i;
            longSparseLongArray.mKeys = parcel.createLongArray();
            longSparseLongArray.mValues = parcel.createLongArray();
            Preconditions.checkArgument(longSparseLongArray.mKeys.length >= i);
            Preconditions.checkArgument(longSparseLongArray.mValues.length >= i);
            if (i > 0) {
                long j = longSparseLongArray.mKeys[0];
                for (int i2 = 1; i2 < i; i2++) {
                    Preconditions.checkArgument(j < longSparseLongArray.mKeys[i2]);
                }
            }
            return longSparseLongArray;
        }
    }
}
