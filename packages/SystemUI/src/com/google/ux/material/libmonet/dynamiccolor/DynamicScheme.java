package com.google.ux.material.libmonet.dynamiccolor;

import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.utils.MathUtils;
import java.util.Optional;

/* loaded from: classes4.dex */
public class DynamicScheme {
    public final double contrastLevel;
    public final TonalPalette errorPalette;
    public final boolean isDark;
    public final TonalPalette neutralPalette;
    public final TonalPalette neutralVariantPalette;
    public final TonalPalette primaryPalette;
    public final TonalPalette secondaryPalette;
    public final Hct sourceColorHct;
    public final TonalPalette tertiaryPalette;
    public final Variant variant;

    public DynamicScheme(Hct hct, Variant variant, boolean z, double d, TonalPalette tonalPalette, TonalPalette tonalPalette2, TonalPalette tonalPalette3, TonalPalette tonalPalette4, TonalPalette tonalPalette5) {
        this(hct, variant, z, d, tonalPalette, tonalPalette2, tonalPalette3, tonalPalette4, tonalPalette5, Optional.empty());
    }

    public static double getRotatedHue(Hct hct, double[] dArr, double[] dArr2) {
        double d = hct.hue;
        int i = 0;
        if (dArr2.length == 1) {
            return MathUtils.sanitizeDegreesDouble(d + dArr2[0]);
        }
        int length = dArr.length;
        while (i <= length - 2) {
            double d2 = dArr[i];
            int i2 = i + 1;
            double d3 = dArr[i2];
            if (d2 < d && d < d3) {
                return MathUtils.sanitizeDegreesDouble(d + dArr2[i]);
            }
            i = i2;
        }
        return d;
    }

    public final int getOnSurface() {
        return new MaterialDynamicColors().onSurface().getArgb(this);
    }

    public DynamicScheme(Hct hct, Variant variant, boolean z, double d, TonalPalette tonalPalette, TonalPalette tonalPalette2, TonalPalette tonalPalette3, TonalPalette tonalPalette4, TonalPalette tonalPalette5, Optional<TonalPalette> optional) {
        hct.getClass();
        this.sourceColorHct = hct;
        this.variant = variant;
        this.isDark = z;
        this.contrastLevel = d;
        this.primaryPalette = tonalPalette;
        this.secondaryPalette = tonalPalette2;
        this.tertiaryPalette = tonalPalette3;
        this.neutralPalette = tonalPalette4;
        this.neutralVariantPalette = tonalPalette5;
        this.errorPalette = optional.orElse(TonalPalette.fromHueAndChroma(25.0d, 84.0d));
    }
}
