package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;

/* loaded from: classes4.dex */
public class SchemeNeutral extends DynamicScheme {
    public SchemeNeutral(Hct hct, boolean z, double d) {
        super(hct, Variant.NEUTRAL, z, d, TonalPalette.fromHueAndChroma(hct.hue, 12.0d), TonalPalette.fromHueAndChroma(hct.hue, 8.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d), TonalPalette.fromHueAndChroma(hct.hue, 2.0d), TonalPalette.fromHueAndChroma(hct.hue, 2.0d));
    }
}
