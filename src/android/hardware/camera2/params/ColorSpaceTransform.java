package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Rational;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class ColorSpaceTransform {
    private static final int COLUMNS = 3;
    private static final int COUNT = 9;
    private static final int COUNT_INT = 18;
    private static final int OFFSET_DENOMINATOR = 1;
    private static final int OFFSET_NUMERATOR = 0;
    private static final int RATIONAL_SIZE = 2;
    private static final int ROWS = 3;
    private final int[] mElements;

    public ColorSpaceTransform(Rational[] rationalArr) {
        Preconditions.checkNotNull(rationalArr, "elements must not be null");
        if (rationalArr.length != 9) {
            throw new IllegalArgumentException("elements must be 9 length");
        }
        this.mElements = new int[18];
        for (int i = 0; i < rationalArr.length; i++) {
            Preconditions.checkNotNull(rationalArr, "element[%d] must not be null", Integer.valueOf(i));
            int i2 = i * 2;
            this.mElements[i2] = rationalArr[i].getNumerator();
            this.mElements[i2 + 1] = rationalArr[i].getDenominator();
        }
    }

    public ColorSpaceTransform(int[] iArr) {
        Preconditions.checkNotNull(iArr, "elements must not be null");
        if (iArr.length != 18) {
            throw new IllegalArgumentException("elements must be 18 length");
        }
        for (int i = 0; i < iArr.length; i++) {
            Preconditions.checkNotNull(iArr, "element %d must not be null", Integer.valueOf(i));
        }
        this.mElements = Arrays.copyOf(iArr, iArr.length);
    }

    public Rational getElement(int i, int i2) {
        if (i < 0 || i >= 3) {
            throw new IllegalArgumentException("column out of range");
        }
        if (i2 < 0 || i2 >= 3) {
            throw new IllegalArgumentException("row out of range");
        }
        int[] iArr = this.mElements;
        int i3 = ((i2 * 3) + i) * 2;
        return new Rational(iArr[i3], iArr[i3 + 1]);
    }

    public void copyElements(Rational[] rationalArr, int i) {
        Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        Preconditions.checkNotNull(rationalArr, "destination must not be null");
        if (rationalArr.length - i < 9) {
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < 9) {
            int[] iArr = this.mElements;
            rationalArr[i2 + i] = new Rational(iArr[i3], iArr[i3 + 1]);
            i2++;
            i3 += 2;
        }
    }

    public void copyElements(int[] iArr, int i) {
        Preconditions.checkArgumentNonnegative(i, "offset must not be negative");
        Preconditions.checkNotNull(iArr, "destination must not be null");
        if (iArr.length - i < 18) {
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        for (int i2 = 0; i2 < 18; i2++) {
            iArr[i2 + i] = this.mElements[i2];
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorSpaceTransform)) {
            return false;
        }
        ColorSpaceTransform colorSpaceTransform = (ColorSpaceTransform) obj;
        int i = 0;
        int i2 = 0;
        while (i < 9) {
            int[] iArr = this.mElements;
            int i3 = iArr[i2];
            int i4 = i2 + 1;
            int i5 = iArr[i4];
            int[] iArr2 = colorSpaceTransform.mElements;
            if (!new Rational(i3, i5).equals((Object) new Rational(iArr2[i2], iArr2[i4]))) {
                return false;
            }
            i++;
            i2 += 2;
        }
        return true;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mElements);
    }

    public String toString() {
        return String.format("ColorSpaceTransform%s", toShortString());
    }

    private String toShortString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.KEY_CODE_START);
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            int i3 = 0;
            while (i3 < 3) {
                int[] iArr = this.mElements;
                int i4 = iArr[i];
                int i5 = iArr[i + 1];
                sb.append(i4);
                sb.append("/");
                sb.append(i5);
                if (i3 < 2) {
                    sb.append(", ");
                }
                i3++;
                i += 2;
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            if (i2 < 2) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
