package com.android.systemui.monet;

import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SchemeClockVibrant extends DynamicScheme {
    public SchemeClockVibrant(Hct hct, boolean z, double d) {
        super(hct, Variant.MONOCHROME, z, d, com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, Double.max(hct.chroma, 70.0d)), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue + 20.0d, Double.max(hct.chroma, 70.0d)), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue + 60.0d, Double.max(hct.chroma, 70.0d)), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, 0.0d), com.google.ux.material.libmonet.palettes.TonalPalette.fromHueAndChroma(hct.hue, 0.0d));
    }
}
