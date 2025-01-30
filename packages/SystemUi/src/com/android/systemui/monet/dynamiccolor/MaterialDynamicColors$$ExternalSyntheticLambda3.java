package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                return ((DynamicScheme) obj).neutralPalette;
            case 1:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 98.0d);
            case 2:
                return ((DynamicScheme) obj).primaryPalette;
            case 3:
                return Double.valueOf(((DynamicScheme) obj).primaryPalette.keyColor.tone);
            case 4:
                return ((DynamicScheme) obj).primaryPalette;
            case 5:
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                if (MaterialDynamicColors.isFidelity(dynamicScheme)) {
                    return Double.valueOf(MaterialDynamicColors.performAlbers(dynamicScheme.sourceColorHct, dynamicScheme));
                }
                z = dynamicScheme.variant == Variant.MONOCHROME;
                boolean z2 = dynamicScheme.isDark;
                if (z) {
                    return Double.valueOf(z2 ? 85.0d : 25.0d);
                }
                return Double.valueOf(z2 ? 30.0d : 90.0d);
            case 6:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 7:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 30.0d : 80.0d);
            case 8:
                return ((DynamicScheme) obj).secondaryPalette;
            case 9:
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                z = dynamicScheme2.variant == Variant.MONOCHROME;
                boolean z3 = dynamicScheme2.isDark;
                if (z) {
                    return Double.valueOf(z3 ? 10.0d : 100.0d);
                }
                return Double.valueOf(z3 ? 20.0d : 100.0d);
            case 10:
                return ((DynamicScheme) obj).secondaryPalette;
            case 11:
                return ((DynamicScheme) obj).neutralPalette;
            case 12:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 17.0d : 92.0d);
            case 13:
                return ((DynamicScheme) obj).neutralPalette;
            case 14:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 96.0d);
            case 15:
                return ((DynamicScheme) obj).neutralPalette;
            case 16:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            case 17:
                return ((DynamicScheme) obj).errorPalette;
            case 18:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            case 19:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 20:
                return Double.valueOf(((DynamicScheme) obj).variant == Variant.MONOCHROME ? 100.0d : 10.0d);
            case 21:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 22:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 80.0d);
            case 23:
                return ((DynamicScheme) obj).primaryPalette;
            case 24:
                return ((DynamicScheme) obj).variant == Variant.MONOCHROME ? Double.valueOf(100.0d) : Double.valueOf(10.0d);
            case 25:
                return ((DynamicScheme) obj).neutralPalette;
            case 26:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 22.0d : 90.0d);
            case 27:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 28:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 30.0d);
            default:
                return Double.valueOf(0.0d);
        }
    }
}
