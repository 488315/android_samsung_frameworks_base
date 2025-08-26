package com.google.ux.material.libmonet.dynamiccolor;

import com.android.systemui.aibrief.ui.BriefViewController;
import com.google.ux.material.libmonet.dislike.DislikeAnalyzer;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final /* synthetic */ class MaterialDynamicColors$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MaterialDynamicColors f$0;

    public /* synthetic */ MaterialDynamicColors$$ExternalSyntheticLambda2(MaterialDynamicColors materialDynamicColors, int i) {
        this.$r8$classId = i;
        this.f$0 = materialDynamicColors;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                return ((DynamicScheme) obj).isDark ? new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null) : new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
            case 1:
                return MaterialDynamicColors.inverseSurface();
            case 2:
                return this.f$0.secondaryFixedDim();
            case 3:
                return this.f$0.secondaryFixed();
            case 4:
                return this.f$0.primaryFixedDim();
            case 5:
                MaterialDynamicColors materialDynamicColors = this.f$0;
                DynamicScheme dynamicScheme = (DynamicScheme) obj;
                materialDynamicColors.getClass();
                boolean zIsMonochrome = MaterialDynamicColors.isMonochrome(dynamicScheme);
                boolean z = dynamicScheme.isDark;
                if (zIsMonochrome) {
                    return Double.valueOf(z ? 0.0d : 100.0d);
                }
                if (materialDynamicColors.isFidelity(dynamicScheme)) {
                    return Double.valueOf(DynamicColor.foregroundTone(((Double) materialDynamicColors.tertiaryContainer().tone.apply(dynamicScheme)).doubleValue(), 4.5d));
                }
                return Double.valueOf(z ? 90.0d : 30.0d);
            case 6:
                return this.f$0.tertiaryContainer();
            case 7:
                MaterialDynamicColors materialDynamicColors2 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors2.errorContainer(), materialDynamicColors2.error(), 10.0d, TonePolarity.NEARER, false);
            case 8:
                MaterialDynamicColors materialDynamicColors3 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors3.tertiaryFixed(), materialDynamicColors3.tertiaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 9:
                return this.f$0.primaryFixed();
            case 10:
                return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
            case 11:
                MaterialDynamicColors materialDynamicColors4 = this.f$0;
                DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                if (materialDynamicColors4.isFidelity(dynamicScheme2)) {
                    return Double.valueOf(DynamicColor.foregroundTone(((Double) materialDynamicColors4.primaryContainer().tone.apply(dynamicScheme2)).doubleValue(), 4.5d));
                }
                Variant variant = Variant.MONOCHROME;
                Variant variant2 = dynamicScheme2.variant;
                boolean z2 = dynamicScheme2.isDark;
                if (variant2 == variant) {
                    return Double.valueOf(z2 ? 0.0d : 100.0d);
                }
                return Double.valueOf(z2 ? 90.0d : 30.0d);
            case 12:
                return this.f$0.primaryContainer();
            case 13:
                MaterialDynamicColors materialDynamicColors5 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors5.secondaryFixed(), materialDynamicColors5.secondaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 14:
                MaterialDynamicColors materialDynamicColors6 = this.f$0;
                DynamicScheme dynamicScheme3 = (DynamicScheme) obj;
                materialDynamicColors6.getClass();
                boolean z3 = dynamicScheme3.isDark;
                double d = z3 ? 30.0d : 90.0d;
                if (dynamicScheme3.variant == Variant.MONOCHROME) {
                    return Double.valueOf(z3 ? 30.0d : 85.0d);
                }
                if (!materialDynamicColors6.isFidelity(dynamicScheme3)) {
                    return Double.valueOf(d);
                }
                TonalPalette tonalPalette = dynamicScheme3.secondaryPalette;
                double d2 = tonalPalette.hue;
                double d3 = tonalPalette.chroma;
                Hct hctFrom = Hct.from(d2, d3, d);
                double d4 = hctFrom.chroma;
                if (d4 < d3) {
                    double dMax = d4;
                    while (hctFrom.chroma < d3) {
                        double d5 = (!dynamicScheme3.isDark ? -1.0d : 1.0d) + d;
                        Hct hctFrom2 = Hct.from(d2, d3, d5);
                        double d6 = hctFrom2.chroma;
                        if (dMax <= d6 && Math.abs(d6 - d3) >= 0.4d) {
                            if (Math.abs(hctFrom2.chroma - d3) < Math.abs(hctFrom.chroma - d3)) {
                                hctFrom = hctFrom2;
                            }
                            dMax = Math.max(dMax, hctFrom2.chroma);
                            d = d5;
                        } else {
                            d = d5;
                        }
                    }
                }
                return Double.valueOf(d);
            case 15:
                MaterialDynamicColors materialDynamicColors7 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors7.errorContainer(), materialDynamicColors7.error(), 10.0d, TonePolarity.NEARER, false);
            case 16:
                MaterialDynamicColors materialDynamicColors8 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors8.tertiaryContainer(), materialDynamicColors8.tertiary(), 10.0d, TonePolarity.NEARER, false);
            case 17:
                MaterialDynamicColors materialDynamicColors9 = this.f$0;
                DynamicScheme dynamicScheme4 = (DynamicScheme) obj;
                materialDynamicColors9.getClass();
                boolean zIsMonochrome2 = MaterialDynamicColors.isMonochrome(dynamicScheme4);
                boolean z4 = dynamicScheme4.isDark;
                if (zIsMonochrome2) {
                    return Double.valueOf(z4 ? 60.0d : 49.0d);
                }
                if (!materialDynamicColors9.isFidelity(dynamicScheme4)) {
                    return Double.valueOf(z4 ? 30.0d : 90.0d);
                }
                double d7 = dynamicScheme4.sourceColorHct.tone;
                TonalPalette tonalPalette2 = dynamicScheme4.tertiaryPalette;
                return Double.valueOf(DislikeAnalyzer.fixIfDisliked(Hct.from(tonalPalette2.hue, tonalPalette2.chroma, d7)).tone);
            case 18:
                MaterialDynamicColors materialDynamicColors10 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors10.tertiaryContainer(), materialDynamicColors10.tertiary(), 10.0d, TonePolarity.NEARER, false);
            case 19:
                return this.f$0.error();
            case 20:
                MaterialDynamicColors materialDynamicColors11 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors11.secondaryContainer(), materialDynamicColors11.secondary(), 10.0d, TonePolarity.NEARER, false);
            case 21:
                MaterialDynamicColors materialDynamicColors12 = this.f$0;
                DynamicScheme dynamicScheme5 = (DynamicScheme) obj;
                materialDynamicColors12.getClass();
                boolean zIsMonochrome3 = MaterialDynamicColors.isMonochrome(dynamicScheme5);
                boolean z5 = dynamicScheme5.isDark;
                if (zIsMonochrome3) {
                    return Double.valueOf(z5 ? 90.0d : 10.0d);
                }
                if (materialDynamicColors12.isFidelity(dynamicScheme5)) {
                    return Double.valueOf(DynamicColor.foregroundTone(((Double) materialDynamicColors12.secondaryContainer().tone.apply(dynamicScheme5)).doubleValue(), 4.5d));
                }
                return Double.valueOf(z5 ? 90.0d : 30.0d);
            case 22:
                return this.f$0.secondaryContainer();
            case 23:
                MaterialDynamicColors materialDynamicColors13 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors13.secondaryFixed(), materialDynamicColors13.secondaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 24:
                MaterialDynamicColors materialDynamicColors14 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors14.primaryFixed(), materialDynamicColors14.primaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 25:
                MaterialDynamicColors materialDynamicColors15 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors15.primaryContainer(), materialDynamicColors15.primary(), 10.0d, TonePolarity.NEARER, false);
            case 26:
                MaterialDynamicColors materialDynamicColors16 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors16.secondaryContainer(), materialDynamicColors16.secondary(), 10.0d, TonePolarity.NEARER, false);
            case 27:
                MaterialDynamicColors materialDynamicColors17 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors17.primaryFixed(), materialDynamicColors17.primaryFixedDim(), 10.0d, TonePolarity.LIGHTER, true);
            case 28:
                DynamicScheme dynamicScheme6 = (DynamicScheme) obj;
                if (this.f$0.isFidelity(dynamicScheme6)) {
                    return Double.valueOf(dynamicScheme6.sourceColorHct.tone);
                }
                Variant variant3 = Variant.MONOCHROME;
                Variant variant4 = dynamicScheme6.variant;
                boolean z6 = dynamicScheme6.isDark;
                if (variant4 == variant3) {
                    return Double.valueOf(z6 ? 85.0d : 25.0d);
                }
                return Double.valueOf(z6 ? 30.0d : 90.0d);
            default:
                MaterialDynamicColors materialDynamicColors18 = this.f$0;
                return new ToneDeltaPair(materialDynamicColors18.primaryContainer(), materialDynamicColors18.primary(), 10.0d, TonePolarity.NEARER, false);
        }
    }
}
