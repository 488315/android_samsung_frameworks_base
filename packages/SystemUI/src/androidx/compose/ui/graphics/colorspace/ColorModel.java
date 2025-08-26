package androidx.compose.ui.graphics.colorspace;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ColorModel {
    public static final long Cmyk;
    public static final Companion Companion = new Companion(null);
    public static final long Lab;
    public static final long Rgb;
    public static final long Xyz;
    public final long packedValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        long j = 3;
        long j2 = j << 32;
        Rgb = (0 & 4294967295L) | j2;
        Xyz = (1 & 4294967295L) | j2;
        Lab = j2 | (2 & 4294967295L);
        Cmyk = (j & 4294967295L) | (4 << 32);
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m510equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m511toStringimpl(long j) {
        return m510equalsimpl0(j, Rgb) ? "Rgb" : m510equalsimpl0(j, Xyz) ? "Xyz" : m510equalsimpl0(j, Lab) ? "Lab" : m510equalsimpl0(j, Cmyk) ? "Cmyk" : C2paManifestList.UNKNOWN_VALUE;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ColorModel) {
            return this.packedValue == ((ColorModel) obj).packedValue;
        }
        return false;
    }

    public final int hashCode() {
        return Long.hashCode(this.packedValue);
    }

    public final String toString() {
        return m511toStringimpl(this.packedValue);
    }
}
