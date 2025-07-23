package com.google.ux.material.libmonet.hct;

import com.google.ux.material.libmonet.utils.ColorUtils;
import com.google.ux.material.libmonet.utils.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Hct {
    public final int argb;
    public final double chroma;
    public final double hue;
    public final double tone;

    private Hct(int i) {
        this.argb = i;
        Cam16 fromInt = Cam16.fromInt(i);
        this.hue = fromInt.hue;
        this.chroma = fromInt.chroma;
        this.tone = (ColorUtils.labF(MathUtils.matrixMultiply(new double[]{ColorUtils.linearized((i >> 16) & 255), ColorUtils.linearized((i >> 8) & 255), ColorUtils.linearized(i & 255)}, ColorUtils.SRGB_TO_XYZ)[1] / 100.0d) * 116.0d) - 16.0d;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x01dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.ux.material.libmonet.hct.Hct from(double r48, double r50, double r52) {
        /*
            Method dump skipped, instructions count: 1026
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ux.material.libmonet.hct.Hct.from(double, double, double):com.google.ux.material.libmonet.hct.Hct");
    }

    public static Hct fromInt(int i) {
        return new Hct(i);
    }
}
