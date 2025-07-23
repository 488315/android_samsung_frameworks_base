package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class SparseRectFArray implements Parcelable {
    public static final Parcelable.Creator<SparseRectFArray> CREATOR = new Parcelable.Creator<SparseRectFArray>() { // from class: android.view.inputmethod.SparseRectFArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SparseRectFArray createFromParcel(Parcel parcel) {
            return new SparseRectFArray(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SparseRectFArray[] newArray(int i) {
            return new SparseRectFArray[i];
        }
    };
    private final float[] mCoordinates;
    private final int[] mFlagsArray;
    private final int[] mKeys;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SparseRectFArray(Parcel parcel) {
        this.mKeys = parcel.createIntArray();
        this.mCoordinates = parcel.createFloatArray();
        this.mFlagsArray = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mKeys);
        parcel.writeFloatArray(this.mCoordinates);
        parcel.writeIntArray(this.mFlagsArray);
    }

    public int hashCode() {
        int[] iArr = this.mKeys;
        if (iArr == null || iArr.length == 0) {
            return 0;
        }
        int length = iArr.length;
        for (int i = 0; i < 4; i++) {
            length = (int) ((length * 31) + this.mCoordinates[i]);
        }
        return (length * 31) + this.mFlagsArray[0];
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SparseRectFArray)) {
            return false;
        }
        SparseRectFArray sparseRectFArray = (SparseRectFArray) obj;
        return Arrays.equals(this.mKeys, sparseRectFArray.mKeys) && Arrays.equals(this.mCoordinates, sparseRectFArray.mCoordinates) && Arrays.equals(this.mFlagsArray, sparseRectFArray.mFlagsArray);
    }

    public String toString() {
        if (this.mKeys == null || this.mCoordinates == null || this.mFlagsArray == null) {
            return "SparseRectFArray{}";
        }
        StringBuilder sb = new StringBuilder("SparseRectFArray{");
        for (int i = 0; i < this.mKeys.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            int i2 = i * 4;
            sb.append(this.mKeys[i]);
            sb.append(":[");
            sb.append(this.mCoordinates[i2]);
            sb.append(",");
            sb.append(this.mCoordinates[i2 + 1]);
            sb.append("],[");
            sb.append(this.mCoordinates[i2 + 2]);
            sb.append(",");
            sb.append(this.mCoordinates[i2 + 3]);
            sb.append("]:flagsArray=");
            sb.append(this.mFlagsArray[i]);
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class SparseRectFArrayBuilder {
        private static int INITIAL_SIZE = 16;
        private int mCount = 0;
        private int[] mKeys = null;
        private float[] mCoordinates = null;
        private int[] mFlagsArray = null;

        private void checkIndex(int i) {
            int i2 = this.mCount;
            if (i2 != 0 && this.mKeys[i2 - 1] >= i) {
                throw new IllegalArgumentException("key must be greater than all existing keys.");
            }
        }

        private void ensureBufferSize() {
            if (this.mKeys == null) {
                this.mKeys = new int[INITIAL_SIZE];
            }
            if (this.mCoordinates == null) {
                this.mCoordinates = new float[INITIAL_SIZE * 4];
            }
            if (this.mFlagsArray == null) {
                this.mFlagsArray = new int[INITIAL_SIZE];
            }
            int i = this.mCount;
            int i2 = i + 1;
            int[] iArr = this.mKeys;
            if (iArr.length <= i2) {
                int[] iArr2 = new int[i2 * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                this.mKeys = iArr2;
            }
            int i3 = this.mCount;
            int i4 = i3 + 1;
            int i5 = i4 * 4;
            float[] fArr = this.mCoordinates;
            if (fArr.length <= i5) {
                float[] fArr2 = new float[i4 * 8];
                System.arraycopy(fArr, 0, fArr2, 0, i3 * 4);
                this.mCoordinates = fArr2;
            }
            int[] iArr3 = this.mFlagsArray;
            if (iArr3.length <= i2) {
                int[] iArr4 = new int[i2 * 2];
                System.arraycopy(iArr3, 0, iArr4, 0, this.mCount);
                this.mFlagsArray = iArr4;
            }
        }

        public SparseRectFArrayBuilder append(int i, float f, float f2, float f3, float f4, int i2) {
            checkIndex(i);
            ensureBufferSize();
            int i3 = this.mCount;
            int i4 = i3 * 4;
            float[] fArr = this.mCoordinates;
            fArr[i4] = f;
            fArr[i4 + 1] = f2;
            fArr[i4 + 2] = f3;
            fArr[i4 + 3] = f4;
            this.mFlagsArray[i3] = i2;
            this.mKeys[i3] = i;
            this.mCount = i3 + 1;
            return this;
        }

        public boolean isEmpty() {
            return this.mCount <= 0;
        }

        public SparseRectFArray build() {
            return new SparseRectFArray(this);
        }

        public void reset() {
            if (this.mCount == 0) {
                this.mKeys = null;
                this.mCoordinates = null;
                this.mFlagsArray = null;
            }
            this.mCount = 0;
        }
    }

    private SparseRectFArray(SparseRectFArrayBuilder sparseRectFArrayBuilder) {
        if (sparseRectFArrayBuilder.mCount == 0) {
            this.mKeys = null;
            this.mCoordinates = null;
            this.mFlagsArray = null;
            return;
        }
        int[] iArr = new int[sparseRectFArrayBuilder.mCount];
        this.mKeys = iArr;
        float[] fArr = new float[sparseRectFArrayBuilder.mCount * 4];
        this.mCoordinates = fArr;
        int[] iArr2 = new int[sparseRectFArrayBuilder.mCount];
        this.mFlagsArray = iArr2;
        System.arraycopy(sparseRectFArrayBuilder.mKeys, 0, iArr, 0, sparseRectFArrayBuilder.mCount);
        System.arraycopy(sparseRectFArrayBuilder.mCoordinates, 0, fArr, 0, sparseRectFArrayBuilder.mCount * 4);
        System.arraycopy(sparseRectFArrayBuilder.mFlagsArray, 0, iArr2, 0, sparseRectFArrayBuilder.mCount);
    }

    public RectF get(int i) {
        int binarySearch;
        int[] iArr = this.mKeys;
        if (iArr == null || i < 0 || (binarySearch = Arrays.binarySearch(iArr, i)) < 0) {
            return null;
        }
        int i2 = binarySearch * 4;
        float[] fArr = this.mCoordinates;
        return new RectF(fArr[i2], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3]);
    }

    public int getFlags(int i, int i2) {
        int binarySearch;
        int[] iArr = this.mKeys;
        return (iArr != null && i >= 0 && (binarySearch = Arrays.binarySearch(iArr, i)) >= 0) ? this.mFlagsArray[binarySearch] : i2;
    }
}
