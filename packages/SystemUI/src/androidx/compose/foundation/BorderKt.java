package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.SolidColor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        float max = Math.max(0.0f, Float.intBitsToFloat((int) (j >> 32)) - f);
        float max2 = Math.max(0.0f, Float.intBitsToFloat((int) (j & 4294967295L)) - f);
        long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        return floatToRawIntBits;
    }
}
