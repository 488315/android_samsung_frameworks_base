package androidx.compose.ui.unit;

import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class DpOffset {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long packedValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    private /* synthetic */ DpOffset(long j) {
        this.packedValue = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ DpOffset m841boximpl(long j) {
        return new DpOffset(j);
    }

    /* renamed from: getX-D9Ej5fM, reason: not valid java name */
    public static final float m842getXD9Ej5fM(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        Dp.Companion companion = Dp.Companion;
        return fIntBitsToFloat;
    }

    /* renamed from: getY-D9Ej5fM, reason: not valid java name */
    public static final float m843getYD9Ej5fM(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j & 4294967295L));
        Dp.Companion companion = Dp.Companion;
        return fIntBitsToFloat;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DpOffset) {
            return this.packedValue == ((DpOffset) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        long j = this.packedValue;
        if (j == 9205357640488583168L) {
            return "DpOffset.Unspecified";
        }
        return "(" + ((Object) Dp.m839toStringimpl(m842getXD9Ej5fM(j))) + ", " + ((Object) Dp.m839toStringimpl(m843getYD9Ej5fM(j))) + ')';
    }
}
