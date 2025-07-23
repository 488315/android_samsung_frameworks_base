package android.hardware.camera2.params;

import android.graphics.PointF;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class TonemapCurve {
    public static final int CHANNEL_BLUE = 2;
    public static final int CHANNEL_GREEN = 1;
    public static final int CHANNEL_RED = 0;
    public static final float LEVEL_BLACK = 0.0f;
    public static final float LEVEL_WHITE = 1.0f;
    private static final int MIN_CURVE_LENGTH = 4;
    private static final int OFFSET_POINT_IN = 0;
    private static final int OFFSET_POINT_OUT = 1;
    public static final int POINT_SIZE = 2;
    private static final int TONEMAP_MIN_CURVE_POINTS = 2;
    private final float[] mBlue;
    private final float[] mGreen;
    private boolean mHashCalculated = false;
    private int mHashCode;
    private final float[] mRed;

    public TonemapCurve(float[] fArr, float[] fArr2, float[] fArr3) {
        Preconditions.checkNotNull(fArr, "red must not be null");
        Preconditions.checkNotNull(fArr2, "green must not be null");
        Preconditions.checkNotNull(fArr3, "blue must not be null");
        checkArgumentArrayLengthDivisibleBy(fArr, 2, "red");
        checkArgumentArrayLengthDivisibleBy(fArr2, 2, "green");
        checkArgumentArrayLengthDivisibleBy(fArr3, 2, "blue");
        checkArgumentArrayLengthNoLessThan(fArr, 4, "red");
        checkArgumentArrayLengthNoLessThan(fArr2, 4, "green");
        checkArgumentArrayLengthNoLessThan(fArr3, 4, "blue");
        Preconditions.checkArrayElementsInRange(fArr, 0.0f, 1.0f, "red");
        Preconditions.checkArrayElementsInRange(fArr2, 0.0f, 1.0f, "green");
        Preconditions.checkArrayElementsInRange(fArr3, 0.0f, 1.0f, "blue");
        this.mRed = Arrays.copyOf(fArr, fArr.length);
        this.mGreen = Arrays.copyOf(fArr2, fArr2.length);
        this.mBlue = Arrays.copyOf(fArr3, fArr3.length);
    }

    private static void checkArgumentArrayLengthDivisibleBy(float[] fArr, int i, String str) {
        if (fArr.length % i == 0) {
            return;
        }
        throw new IllegalArgumentException(str + " size must be divisible by " + i);
    }

    private static int checkArgumentColorChannel(int i) {
        if (i == 0 || i == 1 || i == 2) {
            return i;
        }
        throw new IllegalArgumentException("colorChannel out of range");
    }

    private static void checkArgumentArrayLengthNoLessThan(float[] fArr, int i, String str) {
        if (fArr.length >= i) {
            return;
        }
        throw new IllegalArgumentException(str + " size must be at least " + i);
    }

    public int getPointCount(int i) {
        checkArgumentColorChannel(i);
        return getCurve(i).length / 2;
    }

    public PointF getPoint(int i, int i2) {
        checkArgumentColorChannel(i);
        if (i2 < 0 || i2 >= getPointCount(i)) {
            throw new IllegalArgumentException("index out of range");
        }
        float[] curve = getCurve(i);
        int i3 = i2 * 2;
        return new PointF(curve[i3], curve[i3 + 1]);
    }

    public void copyColorCurve(int i, float[] fArr, int i2) {
        Preconditions.checkArgumentNonnegative(i2, "offset must not be negative");
        Preconditions.checkNotNull(fArr, "destination must not be null");
        if (fArr.length + i2 < getPointCount(i) * 2) {
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        float[] curve = getCurve(i);
        System.arraycopy(curve, 0, fArr, i2, curve.length);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof TonemapCurve) {
            TonemapCurve tonemapCurve = (TonemapCurve) obj;
            if (Arrays.equals(this.mRed, tonemapCurve.mRed) && Arrays.equals(this.mGreen, tonemapCurve.mGreen) && Arrays.equals(this.mBlue, tonemapCurve.mBlue)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.mHashCalculated) {
            return this.mHashCode;
        }
        int hashCodeGeneric = HashCodeHelpers.hashCodeGeneric(this.mRed, this.mGreen, this.mBlue);
        this.mHashCode = hashCodeGeneric;
        this.mHashCalculated = true;
        return hashCodeGeneric;
    }

    public String toString() {
        return "TonemapCurve{R:" + curveToString(0) + ", G:" + curveToString(1) + ", B:" + curveToString(2) + "}";
    }

    private String curveToString(int i) {
        checkArgumentColorChannel(i);
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        float[] curve = getCurve(i);
        int length = curve.length / 2;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(curve[i3]);
            sb.append(", ");
            sb.append(curve[i3 + 1]);
            sb.append("), ");
            i2++;
            i3 += 2;
        }
        sb.setLength(sb.length() - 2);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private float[] getCurve(int i) {
        if (i == 0) {
            return this.mRed;
        }
        if (i == 1) {
            return this.mGreen;
        }
        if (i == 2) {
            return this.mBlue;
        }
        throw new AssertionError("colorChannel out of range");
    }
}
