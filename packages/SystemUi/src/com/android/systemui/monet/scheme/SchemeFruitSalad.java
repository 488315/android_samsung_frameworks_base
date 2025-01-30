package com.android.systemui.monet.scheme;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;
import com.android.systemui.monet.utils.MathUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SchemeFruitSalad extends DynamicScheme {
    public SchemeFruitSalad(Hct hct, boolean z, double d) {
        super(hct, Variant.FRUIT_SALAD, z, d, TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue - 50.0d), 48.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue - 50.0d), 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 10.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d));
    }
}
