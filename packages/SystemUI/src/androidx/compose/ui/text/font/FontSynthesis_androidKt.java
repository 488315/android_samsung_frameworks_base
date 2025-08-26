package androidx.compose.ui.text.font;

import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class FontSynthesis_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0061  */
    /* renamed from: synthesizeTypeface-FxwP2eA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m770synthesizeTypefaceFxwP2eA(int i, Object obj, Font font, FontWeight fontWeight, int i2) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (!(obj instanceof android.graphics.Typeface)) {
            return obj;
        }
        FontSynthesis.Companion companion = FontSynthesis.Companion;
        if ((i & 1) == 0 || Intrinsics.areEqual(font.getWeight(), fontWeight)) {
            z = false;
        } else {
            FontWeight.Companion.getClass();
            FontWeight fontWeight2 = FontWeight.W600;
            if (fontWeight.compareTo(fontWeight2) >= 0 && Intrinsics.compare(font.getWeight().weight, fontWeight2.weight) < 0) {
                z = true;
            }
        }
        if ((i & 2) != 0) {
            int iMo762getStyle_LCdwA = font.mo762getStyle_LCdwA();
            FontStyle.Companion companion2 = FontStyle.Companion;
            z2 = i2 != iMo762getStyle_LCdwA;
        }
        if (!z2 && !z) {
            return obj;
        }
        int i3 = z ? fontWeight.weight : font.getWeight().weight;
        if (z2) {
            FontStyle.Companion.getClass();
            if (i2 != FontStyle.Italic) {
                z3 = false;
            }
        } else {
            int iMo762getStyle_LCdwA2 = font.mo762getStyle_LCdwA();
            FontStyle.Companion.getClass();
            if (iMo762getStyle_LCdwA2 != FontStyle.Italic) {
            }
        }
        TypefaceHelperMethodsApi28.INSTANCE.getClass();
        return android.graphics.Typeface.create((android.graphics.Typeface) obj, i3, z3);
    }
}
