package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Velocity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VelocityKt {
    public static final long Velocity(float f, float f2) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Velocity.Companion companion = Velocity.Companion;
        return floatToRawIntBits;
    }
}
