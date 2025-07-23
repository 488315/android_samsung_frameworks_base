package android.content.res;

import android.util.MathUtils;
import java.util.Arrays;

/* loaded from: classes.dex */
public class FontScaleConverterImpl implements FontScaleConverter {
    public final float[] mFromSpValues;
    public final float[] mToDpValues;

    public FontScaleConverterImpl(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length || fArr.length == 0) {
            throw new IllegalArgumentException("Array lengths must match and be nonzero");
        }
        this.mFromSpValues = fArr;
        this.mToDpValues = fArr2;
    }

    @Override // android.content.res.FontScaleConverter
    public float convertDpToSp(float f) {
        return lookupAndInterpolate(f, this.mToDpValues, this.mFromSpValues);
    }

    @Override // android.content.res.FontScaleConverter
    public float convertSpToDp(float f) {
        return lookupAndInterpolate(f, this.mFromSpValues, this.mToDpValues);
    }

    private static float lookupAndInterpolate(float f, float[] fArr, float[] fArr2) {
        float f2;
        float f3;
        float f4;
        float constrainedMap;
        float abs = Math.abs(f);
        float signum = Math.signum(f);
        int binarySearch = Arrays.binarySearch(fArr, abs);
        if (binarySearch >= 0) {
            constrainedMap = fArr2[binarySearch];
        } else {
            int i = -(binarySearch + 1);
            int i2 = i - 1;
            float f5 = 0.0f;
            if (i2 >= fArr.length - 1) {
                float f6 = fArr[fArr.length - 1];
                float f7 = fArr2[fArr.length - 1];
                if (f6 == 0.0f) {
                    return 0.0f;
                }
                return f * (f7 / f6);
            }
            if (i2 == -1) {
                f2 = fArr[0];
                f3 = fArr2[0];
                f4 = 0.0f;
            } else {
                float f8 = fArr[i2];
                f2 = fArr[i];
                f5 = fArr2[i2];
                f3 = fArr2[i];
                f4 = f8;
            }
            constrainedMap = MathUtils.constrainedMap(f5, f3, f4, f2, abs);
        }
        return signum * constrainedMap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof FontScaleConverterImpl)) {
            return false;
        }
        FontScaleConverterImpl fontScaleConverterImpl = (FontScaleConverterImpl) obj;
        return Arrays.equals(this.mFromSpValues, fontScaleConverterImpl.mFromSpValues) && Arrays.equals(this.mToDpValues, fontScaleConverterImpl.mToDpValues);
    }

    public int hashCode() {
        return (Arrays.hashCode(this.mFromSpValues) * 31) + Arrays.hashCode(this.mToDpValues);
    }

    public String toString() {
        return "FontScaleConverter{fromSpValues=" + Arrays.toString(this.mFromSpValues) + ", toDpValues=" + Arrays.toString(this.mToDpValues) + '}';
    }
}
