package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.utils.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SchemeFruitSalad extends DynamicScheme {
    public SchemeFruitSalad(Hct hct, boolean z, double d) {
        super(hct, Variant.FRUIT_SALAD, z, d, TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue - 50.0d), 48.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue - 50.0d), 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 10.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d));
    }
}
