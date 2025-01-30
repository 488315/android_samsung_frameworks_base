package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;
import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((DynamicScheme) obj).neutralPalette;
            case 1:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            case 2:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 3:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 80.0d);
            case 4:
                return ((DynamicScheme) obj).secondaryPalette;
            case 5:
                return Double.valueOf(((DynamicScheme) obj).secondaryPalette.keyColor.tone);
            case 6:
                return ((DynamicScheme) obj).secondaryPalette;
            case 7:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 70.0d : 80.0d);
            case 8:
                return ((DynamicScheme) obj).primaryPalette;
            case 9:
                return ((DynamicScheme) obj).variant == Variant.MONOCHROME ? Double.valueOf(90.0d) : Double.valueOf(30.0d);
            case 10:
                return ((DynamicScheme) obj).primaryPalette;
            case 11:
                return ((DynamicScheme) obj).secondaryPalette;
            case 12:
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                boolean z = dynamicScheme.variant == Variant.MONOCHROME;
                boolean z2 = dynamicScheme.isDark;
                if (z) {
                    return Double.valueOf(z2 ? 30.0d : 85.0d);
                }
                double d = z2 ? 30.0d : 90.0d;
                if (!MaterialDynamicColors.isFidelity(dynamicScheme)) {
                    return Double.valueOf(d);
                }
                TonalPalette tonalPalette = dynamicScheme.secondaryPalette;
                double d2 = tonalPalette.hue;
                double d3 = tonalPalette.chroma;
                boolean z3 = true ^ z2;
                Hct from = Hct.from(d2, d3, d);
                double d4 = from.chroma;
                if (d4 < d3) {
                    while (from.chroma < d3) {
                        d += z3 ? -1.0d : 1.0d;
                        double d5 = d3;
                        double d6 = d2;
                        Hct from2 = Hct.from(d2, d5, d);
                        double d7 = from2.chroma;
                        if (d4 <= d7 && Math.abs(d7 - d5) >= 0.4d) {
                            if (Math.abs(from2.chroma - d5) < Math.abs(from.chroma - d5)) {
                                from = from2;
                            }
                            d4 = Math.max(d4, from2.chroma);
                            d3 = d5;
                            d2 = d6;
                        }
                    }
                }
                return Double.valueOf(MaterialDynamicColors.performAlbers(Hct.from(tonalPalette.hue, tonalPalette.chroma, d), dynamicScheme));
            case 13:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 14:
                return Double.valueOf(((DynamicScheme) obj).tertiaryPalette.keyColor.tone);
            case 15:
                return ((DynamicScheme) obj).neutralPalette;
            case 16:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            case 17:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 18:
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                boolean z4 = dynamicScheme2.variant == Variant.MONOCHROME;
                boolean z5 = dynamicScheme2.isDark;
                if (z4) {
                    return Double.valueOf(z5 ? 10.0d : 90.0d);
                }
                return Double.valueOf(z5 ? 20.0d : 100.0d);
            case 19:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 20:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 60.0d : 50.0d);
            case 21:
                return ((DynamicScheme) obj).secondaryPalette;
            case 22:
                return Double.valueOf(10.0d);
            case 23:
                return ((DynamicScheme) obj).secondaryPalette;
            case 24:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 25.0d : 30.0d);
            case 25:
                return ((DynamicScheme) obj).errorPalette;
            case 26:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 20.0d : 100.0d);
            case 27:
                return ((DynamicScheme) obj).neutralPalette;
            case 28:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            default:
                return ((DynamicScheme) obj).neutralVariantPalette;
        }
    }
}
