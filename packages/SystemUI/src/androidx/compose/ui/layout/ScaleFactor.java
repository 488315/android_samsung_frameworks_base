package androidx.compose.ui.layout;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScaleFactor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long packedValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Float.floatToRawIntBits(Float.NaN);
        Float.floatToRawIntBits(Float.NaN);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ScaleFactor) {
            return this.packedValue == ((ScaleFactor) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ScaleFactor(");
        long j = this.packedValue;
        sb.append(Float.intBitsToFloat((int) (j >> 32)));
        sb.append(", ");
        sb.append(Float.intBitsToFloat((int) (j & 4294967295L)));
        sb.append(')');
        return sb.toString();
    }
}
