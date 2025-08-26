package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.utils.MathUtils;

/* loaded from: classes4.dex */
public class SchemeTonalSpot extends DynamicScheme {
    public SchemeTonalSpot(Hct hct, boolean z, double d) {
        super(hct, Variant.TONAL_SPOT, z, d, TonalPalette.fromHueAndChroma(hct.hue, 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue + 60.0d), 24.0d), TonalPalette.fromHueAndChroma(hct.hue, 6.0d), TonalPalette.fromHueAndChroma(hct.hue, 8.0d));
    }
}
