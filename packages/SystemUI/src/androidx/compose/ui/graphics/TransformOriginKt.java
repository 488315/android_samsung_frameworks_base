package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.TransformOrigin;

/* loaded from: classes.dex */
public abstract class TransformOriginKt {
    public static final long TransformOrigin(float f, float f2) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        TransformOrigin.Companion companion = TransformOrigin.Companion;
        return jFloatToRawIntBits;
    }
}
