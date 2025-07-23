package com.google.ux.material.libmonet.dynamiccolor;

import com.google.ux.material.libmonet.contrast.Contrast;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DynamicColor {
    public final Function background;
    public final ContrastCurve contrastCurve;
    public final HashMap hctCache;
    public final boolean isBackground;
    public final String name;
    public final Function opacity;
    public final Function palette;
    public final Function secondBackground;
    public final Function tone;
    public final Function toneDeltaPair;

    public DynamicColor(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2, boolean z, Function<DynamicScheme, DynamicColor> function3, Function<DynamicScheme, DynamicColor> function4, ContrastCurve contrastCurve, Function<DynamicScheme, ToneDeltaPair> function5) {
        this.hctCache = new HashMap();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0055 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0054 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static double foregroundTone(double r10, double r12) {
        /*
            double r0 = com.google.ux.material.libmonet.contrast.Contrast.lighter(r10, r12)
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto Lc
            r0 = 4636737291354636288(0x4059000000000000, double:100.0)
        Lc:
            double r4 = com.google.ux.material.libmonet.contrast.Contrast.darker(r10, r12)
            double r2 = java.lang.Math.max(r2, r4)
            double r4 = com.google.ux.material.libmonet.contrast.Contrast.ratioOfTones(r0, r10)
            double r6 = com.google.ux.material.libmonet.contrast.Contrast.ratioOfTones(r2, r10)
            long r10 = java.lang.Math.round(r10)
            r8 = 60
            int r10 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r10 >= 0) goto L4b
            double r10 = r4 - r6
            double r10 = java.lang.Math.abs(r10)
            r8 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            int r10 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r10 >= 0) goto L3f
            int r10 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r10 >= 0) goto L3f
            int r10 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r10 >= 0) goto L3f
            r10 = 1
            goto L40
        L3f:
            r10 = 0
        L40:
            int r11 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r11 >= 0) goto L54
            int r11 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r11 >= 0) goto L54
            if (r10 == 0) goto L55
            goto L54
        L4b:
            int r10 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r10 >= 0) goto L55
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 < 0) goto L54
            goto L55
        L54:
            return r0
        L55:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ux.material.libmonet.dynamiccolor.DynamicColor.foregroundTone(double, double):double");
    }

    public static DynamicColor fromPalette(String str, Function function, Function function2) {
        return new DynamicColor(str, function, function2, false, null, null, null, null);
    }

    public final int getArgb(DynamicScheme dynamicScheme) {
        Hct hct = (Hct) this.hctCache.get(dynamicScheme);
        if (hct == null) {
            double tone = getTone(dynamicScheme);
            TonalPalette tonalPalette = (TonalPalette) this.palette.apply(dynamicScheme);
            hct = Hct.from(tonalPalette.hue, tonalPalette.chroma, tone);
            if (this.hctCache.size() > 4) {
                this.hctCache.clear();
            }
            this.hctCache.put(dynamicScheme, hct);
        }
        int i = hct.argb;
        Function function = this.opacity;
        if (function == null) {
            return i;
        }
        int round = (int) Math.round(((Double) function.apply(dynamicScheme)).doubleValue() * 255.0d);
        if (round < 0) {
            round = 0;
        } else if (round > 255) {
            round = 255;
        }
        return (round << 24) | (16777215 & i);
    }

    public final double getTone(DynamicScheme dynamicScheme) {
        double d;
        double min;
        double d2 = dynamicScheme.contrastLevel;
        boolean z = false;
        boolean z2 = d2 < 0.0d;
        Function function = this.toneDeltaPair;
        double d3 = 60.0d;
        if (function == null) {
            double doubleValue = ((Double) this.tone.apply(dynamicScheme)).doubleValue();
            Function function2 = this.background;
            if (function2 == null) {
                return doubleValue;
            }
            double tone = ((DynamicColor) function2.apply(dynamicScheme)).getTone(dynamicScheme);
            double d4 = this.contrastCurve.get(d2);
            if (Contrast.ratioOfTones(tone, doubleValue) < d4) {
                doubleValue = foregroundTone(tone, d4);
            }
            if (z2) {
                doubleValue = foregroundTone(tone, d4);
            }
            if (this.isBackground && 50.0d <= doubleValue && doubleValue < 60.0d) {
                doubleValue = Contrast.ratioOfTones(49.0d, tone) >= d4 ? 49.0d : 60.0d;
            }
            if (this.secondBackground != null) {
                double tone2 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
                double tone3 = ((DynamicColor) this.secondBackground.apply(dynamicScheme)).getTone(dynamicScheme);
                double max = Math.max(tone2, tone3);
                double min2 = Math.min(tone2, tone3);
                if (Contrast.ratioOfTones(max, doubleValue) < d4 || Contrast.ratioOfTones(min2, doubleValue) < d4) {
                    double lighter = Contrast.lighter(max, d4);
                    double darker = Contrast.darker(min2, d4);
                    ArrayList arrayList = new ArrayList();
                    if (lighter != -1.0d) {
                        arrayList.add(Double.valueOf(lighter));
                    }
                    if (darker != -1.0d) {
                        arrayList.add(Double.valueOf(darker));
                    }
                    if (Math.round(tone2) < 60 || Math.round(tone3) < 60) {
                        if (lighter == -1.0d) {
                            return 100.0d;
                        }
                        return lighter;
                    }
                    if (arrayList.size() == 1) {
                        return ((Double) arrayList.get(0)).doubleValue();
                    }
                    if (darker == -1.0d) {
                        return 0.0d;
                    }
                    return darker;
                }
            }
            return doubleValue;
        }
        ToneDeltaPair toneDeltaPair = (ToneDeltaPair) function.apply(dynamicScheme);
        DynamicColor dynamicColor = toneDeltaPair.roleA;
        double tone4 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
        TonePolarity tonePolarity = TonePolarity.NEARER;
        boolean z3 = dynamicScheme.isDark;
        TonePolarity tonePolarity2 = toneDeltaPair.polarity;
        if (tonePolarity2 == tonePolarity || ((tonePolarity2 == TonePolarity.LIGHTER && !z3) || (tonePolarity2 == TonePolarity.DARKER && z3))) {
            z = true;
        }
        DynamicColor dynamicColor2 = toneDeltaPair.roleB;
        DynamicColor dynamicColor3 = z ? dynamicColor : dynamicColor2;
        if (z) {
            dynamicColor = dynamicColor2;
        }
        boolean equals = this.name.equals(dynamicColor3.name);
        double d5 = z3 ? 1.0d : -1.0d;
        double d6 = dynamicColor3.contrastCurve.get(d2);
        double d7 = dynamicColor.contrastCurve.get(d2);
        double doubleValue2 = ((Double) dynamicColor3.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, doubleValue2) < d6) {
            doubleValue2 = foregroundTone(tone4, d6);
        }
        double doubleValue3 = ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, doubleValue3) < d7) {
            doubleValue3 = foregroundTone(tone4, d7);
        }
        if (z2) {
            doubleValue2 = foregroundTone(tone4, d6);
            doubleValue3 = foregroundTone(tone4, d7);
        }
        double d8 = (doubleValue3 - doubleValue2) * d5;
        double d9 = toneDeltaPair.delta;
        if (d8 < d9) {
            double d10 = d9 * d5;
            doubleValue3 = doubleValue2 + d10;
            if (doubleValue3 < 0.0d) {
                doubleValue3 = 0.0d;
            } else if (doubleValue3 > 100.0d) {
                doubleValue3 = 100.0d;
            }
            if ((doubleValue3 - doubleValue2) * d5 < d9) {
                double d11 = doubleValue3 - d10;
                if (d11 < 0.0d) {
                    d11 = 0.0d;
                } else if (d11 > 100.0d) {
                    d11 = 100.0d;
                }
                doubleValue2 = d11;
            }
        }
        if (50.0d > doubleValue2 || doubleValue2 >= 60.0d) {
            if (50.0d > doubleValue3 || doubleValue3 >= 60.0d) {
                d3 = doubleValue2;
                d = doubleValue3;
            } else if (toneDeltaPair.stayTogether) {
                if (d5 > 0.0d) {
                    d = Math.max(doubleValue3, (d9 * d5) + 60.0d);
                } else {
                    min = Math.min(doubleValue3, (d9 * d5) + 49.0d);
                    d = min;
                    d3 = 49.0d;
                }
            } else if (d5 > 0.0d) {
                d3 = doubleValue2;
                d = 60.0d;
            } else {
                d3 = doubleValue2;
                d = 49.0d;
            }
        } else if (d5 > 0.0d) {
            d = Math.max(doubleValue3, (d9 * d5) + 60.0d);
        } else {
            min = Math.min(doubleValue3, (d9 * d5) + 49.0d);
            d = min;
            d3 = 49.0d;
        }
        return equals ? d3 : d;
    }

    public DynamicColor(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2, boolean z, Function<DynamicScheme, DynamicColor> function3, Function<DynamicScheme, DynamicColor> function4, ContrastCurve contrastCurve, Function<DynamicScheme, ToneDeltaPair> function5, Function<DynamicScheme, Double> function6) {
        this.hctCache = new HashMap();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = function6;
    }
}
