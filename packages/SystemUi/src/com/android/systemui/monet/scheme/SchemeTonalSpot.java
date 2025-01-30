package com.android.systemui.monet.scheme;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;
import com.android.systemui.monet.utils.MathUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SchemeTonalSpot extends DynamicScheme {
    public SchemeTonalSpot(Hct hct, boolean z, double d) {
        super(hct, Variant.TONAL_SPOT, z, d, TonalPalette.fromHueAndChroma(hct.hue, 36.0d), TonalPalette.fromHueAndChroma(hct.hue, 16.0d), TonalPalette.fromHueAndChroma(MathUtils.sanitizeDegreesDouble(hct.hue + 60.0d), 24.0d), TonalPalette.fromHueAndChroma(hct.hue, 6.0d), TonalPalette.fromHueAndChroma(hct.hue, 8.0d));
    }
}
