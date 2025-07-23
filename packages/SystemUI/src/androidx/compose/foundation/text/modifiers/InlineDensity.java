package androidx.compose.foundation.text.modifiers;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InlineDensity {
    public static final Companion Companion = new Companion(null);
    public static final long Unspecified = m219constructorimpl(Float.NaN, Float.NaN);
    public final long packedValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m219constructorimpl(float f, float f2) {
        return (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m220toStringimpl(long j) {
        return "InlineDensity(density=" + Float.intBitsToFloat((int) (j >> 32)) + ", fontScale=" + Float.intBitsToFloat((int) (j & 4294967295L)) + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof InlineDensity) {
            return this.packedValue == ((InlineDensity) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m220toStringimpl(this.packedValue);
    }
}
