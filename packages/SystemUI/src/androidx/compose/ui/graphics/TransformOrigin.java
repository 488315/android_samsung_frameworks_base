package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransformOrigin {
    public final long packedValue;
    public static final Companion Companion = new Companion(null);
    public static final long Center = TransformOriginKt.TransformOrigin(0.5f, 0.5f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ TransformOrigin(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TransformOrigin m501boximpl(long j) {
        return new TransformOrigin(j);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m502equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: getPivotFractionX-impl, reason: not valid java name */
    public static final float m503getPivotFractionXimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* renamed from: getPivotFractionY-impl, reason: not valid java name */
    public static final float m504getPivotFractionYimpl(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m505toStringimpl(long j) {
        return "TransformOrigin(packedValue=" + j + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TransformOrigin) {
            return this.packedValue == ((TransformOrigin) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m505toStringimpl(this.packedValue);
    }
}
