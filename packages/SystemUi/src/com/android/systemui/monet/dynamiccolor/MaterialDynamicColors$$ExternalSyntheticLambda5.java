package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;
import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.Variant;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Double.valueOf(0.0d);
            case 1:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 100.0d : 0.0d);
            case 2:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 0.2d : 0.12d);
            case 3:
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                MaterialDynamicColors$$ExternalSyntheticLambda5 materialDynamicColors$$ExternalSyntheticLambda5 = new MaterialDynamicColors$$ExternalSyntheticLambda5(24);
                return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme, materialDynamicColors$$ExternalSyntheticLambda5, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme, 1), new DynamicColor$$ExternalSyntheticLambda5(materialDynamicColors$$ExternalSyntheticLambda5, dynamicScheme, null), null, null, null, new DynamicColor$$ExternalSyntheticLambda6()));
            case 4:
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme2, new MaterialDynamicColors$$ExternalSyntheticLambda5(23), new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme2, 2), new DynamicColor$$ExternalSyntheticLambda7(null, dynamicScheme2), null, null, null, null));
            case 5:
                return ((DynamicScheme) obj).neutralPalette;
            case 6:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            case 7:
                return ((DynamicScheme) obj).neutralPalette;
            case 8:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 87.0d);
            case 9:
                return ((DynamicScheme) obj).primaryPalette;
            case 10:
                DynamicScheme dynamicScheme3 = (DynamicScheme) obj;
                boolean z = dynamicScheme3.variant == Variant.MONOCHROME;
                boolean z2 = dynamicScheme3.isDark;
                if (z) {
                    return Double.valueOf(z2 ? 100.0d : 0.0d);
                }
                return Double.valueOf(z2 ? 80.0d : 40.0d);
            case 11:
                return ((DynamicScheme) obj).tertiaryPalette;
            case 12:
                DynamicScheme dynamicScheme4 = (DynamicScheme) obj;
                boolean z3 = dynamicScheme4.variant == Variant.MONOCHROME;
                boolean z4 = dynamicScheme4.isDark;
                if (z3) {
                    return Double.valueOf(z4 ? 60.0d : 49.0d);
                }
                if (!MaterialDynamicColors.isFidelity(dynamicScheme4)) {
                    return Double.valueOf(z4 ? 30.0d : 90.0d);
                }
                double d = dynamicScheme4.sourceColorHct.tone;
                TonalPalette tonalPalette = dynamicScheme4.tertiaryPalette;
                Hct from = Hct.from(tonalPalette.hue, tonalPalette.chroma, MaterialDynamicColors.performAlbers(Hct.from(tonalPalette.hue, tonalPalette.chroma, d), dynamicScheme4));
                if (((((double) Math.round(from.hue)) > 90.0d ? 1 : (((double) Math.round(from.hue)) == 90.0d ? 0 : -1)) >= 0 && (((double) Math.round(from.hue)) > 111.0d ? 1 : (((double) Math.round(from.hue)) == 111.0d ? 0 : -1)) <= 0) && ((((double) Math.round(from.chroma)) > 16.0d ? 1 : (((double) Math.round(from.chroma)) == 16.0d ? 0 : -1)) > 0) && ((((double) Math.round(from.tone)) > 70.0d ? 1 : (((double) Math.round(from.tone)) == 70.0d ? 0 : -1)) < 0)) {
                    from = Hct.from(from.hue, from.chroma, 70.0d);
                }
                return Double.valueOf(from.tone);
            case 13:
                return ((DynamicScheme) obj).neutralPalette;
            case 14:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 24.0d : 98.0d);
            case 15:
                return ((DynamicScheme) obj).primaryPalette;
            case 16:
                return ((DynamicScheme) obj).variant == Variant.MONOCHROME ? Double.valueOf(40.0d) : Double.valueOf(90.0d);
            case 17:
                return ((DynamicScheme) obj).secondaryPalette;
            case 18:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 40.0d);
            case 19:
                return ((DynamicScheme) obj).errorPalette;
            case 20:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            case 21:
                return ((DynamicScheme) obj).neutralVariantPalette;
            case 22:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            case 23:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 100.0d : 0.0d);
            default:
                return Double.valueOf(((DynamicScheme) obj).isDark ? 100.0d : 0.0d);
        }
    }
}
