package com.android.systemui.monet.scheme;

import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.palettes.TonalPalette;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SchemeVibrant extends DynamicScheme {
    public static final double[] HUES = {0.0d, 41.0d, 61.0d, 101.0d, 131.0d, 181.0d, 251.0d, 301.0d, 360.0d};
    public static final double[] SECONDARY_ROTATIONS = {18.0d, 15.0d, 10.0d, 12.0d, 15.0d, 18.0d, 15.0d, 12.0d, 12.0d};
    public static final double[] TERTIARY_ROTATIONS = {35.0d, 30.0d, 20.0d, 25.0d, 30.0d, 35.0d, 30.0d, 25.0d, 25.0d};

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SchemeVibrant(Hct hct, boolean z, double d) {
        super(hct, r2, z, d, r6, TonalPalette.fromHueAndChroma(DynamicScheme.getRotatedHue(hct, r0, SECONDARY_ROTATIONS), 24.0d), TonalPalette.fromHueAndChroma(DynamicScheme.getRotatedHue(hct, r0, TERTIARY_ROTATIONS), 32.0d), TonalPalette.fromHueAndChroma(hct.hue, 10.0d), TonalPalette.fromHueAndChroma(hct.hue, 12.0d));
        Variant variant = Variant.VIBRANT;
        TonalPalette fromHueAndChroma = TonalPalette.fromHueAndChroma(hct.hue, 200.0d);
        double[] dArr = HUES;
    }
}
