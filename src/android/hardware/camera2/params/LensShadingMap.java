package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LensShadingMap {
    public static final float MINIMUM_GAIN_FACTOR = 1.0f;
    private final int mColumns;
    private final float[] mElements;
    private final int mRows;

    public LensShadingMap(float[] fArr, int i, int i2) {
        this.mRows = Preconditions.checkArgumentPositive(i, "rows must be positive");
        this.mColumns = Preconditions.checkArgumentPositive(i2, "columns must be positive");
        this.mElements = (float[]) Objects.requireNonNull(fArr, "elements must not be null");
        if (fArr.length != getGainFactorCount()) {
            throw new IllegalArgumentException("elements must be " + getGainFactorCount() + " length, received " + fArr.length);
        }
        Preconditions.checkArrayElementsInRange(fArr, 1.0f, Float.MAX_VALUE, "elements");
    }

    public int getRowCount() {
        return this.mRows;
    }

    public int getColumnCount() {
        return this.mColumns;
    }

    public int getGainFactorCount() {
        return this.mRows * this.mColumns * 4;
    }

    public float getGainFactor(int i, int i2, int i3) {
        int i4;
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("colorChannel out of range");
        }
        if (i2 < 0 || i2 >= (i4 = this.mColumns)) {
            throw new IllegalArgumentException("column out of range");
        }
        if (i3 < 0 || i3 >= this.mRows) {
            throw new IllegalArgumentException("row out of range");
        }
        return this.mElements[i + (((i3 * i4) + i2) * 4)];
    }

    public RggbChannelVector getGainFactorVector(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this.mColumns)) {
            throw new IllegalArgumentException("column out of range");
        }
        if (i2 < 0 || i2 >= this.mRows) {
            throw new IllegalArgumentException("row out of range");
        }
        int i4 = ((i2 * i3) + i) * 4;
        float[] fArr = this.mElements;
        return new RggbChannelVector(fArr[i4], fArr[i4 + 1], fArr[i4 + 2], fArr[i4 + 3]);
    }

    public void copyGainFactors(float[] fArr, int i) {
        Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        Objects.requireNonNull(fArr, "destination must not be null");
        if (fArr.length + i < getGainFactorCount()) {
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        System.arraycopy(this.mElements, 0, fArr, i, getGainFactorCount());
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof LensShadingMap) {
            LensShadingMap lensShadingMap = (LensShadingMap) obj;
            if (this.mRows == lensShadingMap.mRows && this.mColumns == lensShadingMap.mColumns && Arrays.equals(this.mElements, lensShadingMap.mElements)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mRows, this.mColumns, HashCodeHelpers.hashCode(this.mElements));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LensShadingMap{");
        String[] strArr = {"R:(", "G_even:(", "G_odd:(", "B:("};
        for (int i = 0; i < 4; i++) {
            sb.append(strArr[i]);
            for (int i2 = 0; i2 < this.mRows; i2++) {
                sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                for (int i3 = 0; i3 < this.mColumns; i3++) {
                    sb.append(getGainFactor(i, i3, i2));
                    if (i3 < this.mColumns - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                if (i2 < this.mRows - 1) {
                    sb.append(", ");
                }
            }
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            if (i < 3) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
