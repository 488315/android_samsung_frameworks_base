package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.utils.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SchemeRainbow extends DynamicScheme {
    public SchemeRainbow(Hct hct, boolean z, double d) {
        super(hct, Variant.RAINBOW, z, d, TonalPalette.fromHueAndChroma(hct.hue, 48.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue + 60.0d), 24.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d));
    }
}
