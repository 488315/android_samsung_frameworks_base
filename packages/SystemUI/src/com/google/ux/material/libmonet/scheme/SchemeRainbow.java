package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.utils.MathUtils;

/* loaded from: classes4.dex */
public class SchemeRainbow extends DynamicScheme {
    public SchemeRainbow(Hct hct, boolean z, double d) {
        super(hct, Variant.RAINBOW, z, d, TonalPalette.fromHueAndChroma(hct.hue, 48.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue + 60.0d), 24.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d));
    }
}
