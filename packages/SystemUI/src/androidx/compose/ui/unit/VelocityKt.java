package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Velocity;

/* loaded from: classes.dex */
public abstract class VelocityKt {
    public static final long Velocity(float f, float f2) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Velocity.Companion companion = Velocity.Companion;
        return jFloatToRawIntBits;
    }
}
