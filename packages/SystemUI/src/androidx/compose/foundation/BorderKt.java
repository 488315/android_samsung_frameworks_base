package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;

/* loaded from: classes.dex */
public abstract class BorderKt {
    /* renamed from: border-xT4_qwU, reason: not valid java name */
    public static final Modifier m28borderxT4_qwU(Modifier modifier, float f, long j, Shape shape) {
        return m29borderziNgDLE(modifier, f, new SolidColor(j, null), shape);
    }

    /* renamed from: border-ziNgDLE, reason: not valid java name */
    public static final Modifier m29borderziNgDLE(Modifier modifier, float f, Brush brush, Shape shape) {
        return modifier.then(new BorderModifierNodeElement(f, brush, shape, null));
    }

    /* renamed from: shrink-Kibmq7A, reason: not valid java name */
    public static final long m30shrinkKibmq7A(float f, long j) {
        float fMax = Math.max(0.0f, Float.intBitsToFloat((int) (j >> 32)) - f);
        float fMax2 = Math.max(0.0f, Float.intBitsToFloat((int) (j & 4294967295L)) - f);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMax) << 32) | (Float.floatToRawIntBits(fMax2) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return jFloatToRawIntBits;
    }
}
