package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(((DynamicScheme) obj).neutralVariantPalette.keyColor.tone);
            case 1:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 2:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 30.0d);
            case 3:
                return ((DynamicScheme) obj).secondaryPalette;
            case 4:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 80.0d : 90.0d);
            case 5:
                return ((DynamicScheme) obj).errorPalette;
            case 6:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 40.0d);
            case 7:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 8:
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                z = dynamicScheme.variant == Variant.MONOCHROME;
                boolean z2 = dynamicScheme.isDark;
                if (z) {
                    return Double.valueOf(z2 ? 90.0d : 25.0d);
                }
                return Double.valueOf(z2 ? 80.0d : 40.0d);
            case 9:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 10:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 90.0d : 30.0d);
            case 11:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 12:
                return ((DynamicScheme) obj).neutralPalette;
            case 13:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 12.0d : 94.0d);
            case 14:
                return ((DynamicScheme) obj).neutralPalette;
            case 15:
                return Double.valueOf(((DynamicScheme) obj).neutralPalette.keyColor.tone);
            case 16:
                return ((DynamicScheme) obj).neutralPalette;
            case 17:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 98.0d);
            case 18:
                return ((DynamicScheme) obj).neutralPalette;
            case 19:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            case 20:
                return ((DynamicScheme) obj).primaryPalette;
            case 21:
                return ((DynamicScheme) obj).variant == Variant.MONOCHROME ? Double.valueOf(30.0d) : Double.valueOf(80.0d);
            case 22:
                return ((DynamicScheme) obj).primaryPalette;
            case 23:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            case 24:
                return ((DynamicScheme) obj).primaryPalette;
            case 25:
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                z = dynamicScheme2.variant == Variant.MONOCHROME;
                boolean z3 = dynamicScheme2.isDark;
                if (z) {
                    return Double.valueOf(z3 ? 10.0d : 90.0d);
                }
                return Double.valueOf(z3 ? 20.0d : 100.0d);
            case 26:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 27:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 40.0d : 90.0d);
            case 28:
                return ((DynamicScheme) obj).neutralPalette;
            default:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 4.0d : 100.0d);
        }
    }
}
