package com.android.systemui.monet.scheme;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SchemeMonochrome extends DynamicScheme {
    public SchemeMonochrome(Hct hct, boolean z, double d) {
        super(hct, Variant.MONOCHROME, z, d, TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d), TonalPalette.fromHueAndChroma(hct.hue, 0.0d));
    }
}
