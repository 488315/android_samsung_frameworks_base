package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;

/* loaded from: classes2.dex */
public class SchemeClock extends DynamicScheme {
    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0023 A[PHI: r9
      0x0023: PHI (r9v4 double) = (r9v1 double), (r9v2 double) binds: [B:3:0x0021, B:6:0x0029] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SchemeClock(Hct hct, boolean z, double d) {
        Variant variant = Variant.MONOCHROME;
        com.google.ux.material.libmonet.palettes.TonalPalette tonalPaletteFromHueAndChroma = com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, Double.max(hct.chroma, 20.0d));
        double d2 = hct.hue + 10.0d;
        double d3 = hct.chroma * 0.85d;
        double d4 = 17.0d;
        if (d3 < 17.0d) {
            d3 = d4;
        } else {
            d4 = 40.0d;
            if (d3 > 40.0d) {
            }
        }
        super(hct, variant, z, d, tonalPaletteFromHueAndChroma, com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(d2, d3), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue + 20.0d, Double.max(hct.chroma + 20.0d, 50.0d)), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, 0.0d), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, 0.0d));
    }
}
