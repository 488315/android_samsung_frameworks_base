package androidx.compose.ui.graphics;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ColorMatrixColorFilter extends ColorFilter {
    public float[] colorMatrix;

    public /* synthetic */ ColorMatrixColorFilter(float[] fArr, android.graphics.ColorFilter colorFilter, DefaultConstructorMarker defaultConstructorMarker) {
        this(fArr, colorFilter);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ColorMatrixColorFilter) && Arrays.equals(m472obtainColorMatrixp10uLo(), ((ColorMatrixColorFilter) obj).m472obtainColorMatrixp10uLo());
    }

    public final int hashCode() {
        float[] fArr = this.colorMatrix;
        if (fArr != null) {
            return Arrays.hashCode(fArr);
        }
        return 0;
    }

    /* renamed from: obtainColorMatrix-p10-uLo, reason: not valid java name */
    public final float[] m472obtainColorMatrixp10uLo() {
        float[] fArr = this.colorMatrix;
        if (fArr != null) {
            return fArr;
        }
        android.graphics.ColorFilter colorFilter = this.nativeColorFilter;
        if (!(colorFilter instanceof android.graphics.ColorMatrixColorFilter)) {
            throw new IllegalArgumentException("Unable to obtain ColorMatrix from Android ColorMatrixColorFilter. This method was invoked on an unsupported Android version");
        }
        ColorMatrixFilterHelper.INSTANCE.getClass();
        android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();
        ((android.graphics.ColorMatrixColorFilter) colorFilter).getColorMatrix(colorMatrix);
        float[] array = colorMatrix.getArray();
        this.colorMatrix = array;
        return array;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("ColorMatrixColorFilter(colorMatrix=");
        float[] fArr = this.colorMatrix;
        if (fArr == null) {
            str = "null";
        } else {
            str = "ColorMatrix(values=" + Arrays.toString(fArr) + ')';
        }
        sb.append((Object) str);
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ ColorMatrixColorFilter(float[] fArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(fArr);
    }

    private ColorMatrixColorFilter(float[] fArr) {
        this(fArr, new android.graphics.ColorMatrixColorFilter(fArr), null);
    }

    private ColorMatrixColorFilter(float[] fArr, android.graphics.ColorFilter colorFilter) {
        super(colorFilter);
        this.colorMatrix = fArr;
    }
}
