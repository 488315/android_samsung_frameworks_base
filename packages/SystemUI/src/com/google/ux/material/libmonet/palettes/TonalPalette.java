package com.google.ux.material.libmonet.palettes;

import com.google.ux.material.libmonet.hct.Hct;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TonalPalette {
    public final Map cache = new HashMap();
    public final double chroma;
    public final double hue;
    public final Hct keyColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyColor {
        public final Map chromaCache = new HashMap();
        public final double hue;
        public final double requestedChroma;

        public KeyColor(double d, double d2) {
            this.hue = d;
            this.requestedChroma = d2;
        }

        public final double maxChroma(int i) {
            if (((HashMap) this.chromaCache).get(Integer.valueOf(i)) == null) {
                ((HashMap) this.chromaCache).put(Integer.valueOf(i), Double.valueOf(Hct.from(this.hue, 200.0d, i).chroma));
            }
            return ((Double) ((HashMap) this.chromaCache).get(Integer.valueOf(i))).doubleValue();
        }
    }

    private TonalPalette(double d, double d2, Hct hct) {
        this.hue = d;
        this.chroma = d2;
        this.keyColor = hct;
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.hue, hct.chroma, hct);
    }

    public static TonalPalette fromHueAndChroma(double d, double d2) {
        Hct from;
        KeyColor keyColor = new KeyColor(d, d2);
        int i = 100;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                from = Hct.from(keyColor.hue, keyColor.requestedChroma, i2);
                break;
            }
            int i3 = (i2 + i) / 2;
            int i4 = i3 + 1;
            boolean z = keyColor.maxChroma(i3) < keyColor.maxChroma(i4);
            if (keyColor.maxChroma(i3) >= keyColor.requestedChroma - 0.01d) {
                if (Math.abs(i2 - 50) < Math.abs(i - 50)) {
                    i = i3;
                } else {
                    if (i2 == i3) {
                        from = Hct.from(keyColor.hue, keyColor.requestedChroma, i2);
                        break;
                    }
                    i2 = i3;
                }
            } else if (z) {
                i2 = i4;
            } else {
                i = i3;
            }
        }
        return new TonalPalette(d, d2, from);
    }
}
