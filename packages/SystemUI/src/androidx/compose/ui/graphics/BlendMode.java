package androidx.compose.ui.graphics;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BlendMode {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Src = 1;
    public static final int Dst = 2;
    public static final int SrcOver = 3;
    public static final int DstOver = 4;
    public static final int SrcIn = 5;
    public static final int DstIn = 6;
    public static final int SrcOut = 7;
    public static final int DstOut = 8;
    public static final int SrcAtop = 9;
    public static final int DstAtop = 10;
    public static final int Xor = 11;
    public static final int Plus = 12;
    public static final int Modulate = 13;
    public static final int Screen = 14;
    public static final int Overlay = 15;
    public static final int Darken = 16;
    public static final int Lighten = 17;
    public static final int ColorDodge = 18;
    public static final int ColorBurn = 19;
    public static final int Hardlight = 20;
    public static final int Softlight = 21;
    public static final int Difference = 22;
    public static final int Exclusion = 23;
    public static final int Multiply = 24;
    public static final int Hue = 25;
    public static final int Saturation = 26;
    public static final int Color = 27;
    public static final int Luminosity = 28;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m450toStringimpl(int i) {
        return i == 0 ? "Clear" : i == Src ? "Src" : i == Dst ? "Dst" : i == SrcOver ? "SrcOver" : i == DstOver ? "DstOver" : i == SrcIn ? "SrcIn" : i == DstIn ? "DstIn" : i == SrcOut ? "SrcOut" : i == DstOut ? "DstOut" : i == SrcAtop ? "SrcAtop" : i == DstAtop ? "DstAtop" : i == Xor ? "Xor" : i == Plus ? "Plus" : i == Modulate ? "Modulate" : i == Screen ? "Screen" : i == Overlay ? "Overlay" : i == Darken ? "Darken" : i == Lighten ? "Lighten" : i == ColorDodge ? "ColorDodge" : i == ColorBurn ? "ColorBurn" : i == Hardlight ? "HardLight" : i == Softlight ? "Softlight" : i == Difference ? "Difference" : i == Exclusion ? "Exclusion" : i == Multiply ? "Multiply" : i == Hue ? "Hue" : i == Saturation ? "Saturation" : i == Color ? "Color" : i == Luminosity ? "Luminosity" : C2paManifestList.UNKNOWN_VALUE;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof BlendMode) {
            return this.value == ((BlendMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m450toStringimpl(this.value);
    }
}
