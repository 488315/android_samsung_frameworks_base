package com.google.ux.material.libmonet.dynamiccolor;

import com.google.ux.material.libmonet.contrast.Contrast;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

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

    /* JADX WARN: Removed duplicated region for block: B:26:0x0054 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0055 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double foregroundTone(double d, double d2) {
        double dLighter = Contrast.lighter(d, d2);
        if (dLighter < 0.0d) {
            dLighter = 100.0d;
        }
        double dMax = Math.max(0.0d, Contrast.darker(d, d2));
        double dRatioOfTones = Contrast.ratioOfTones(dLighter, d);
        double dRatioOfTones2 = Contrast.ratioOfTones(dMax, d);
        if (Math.round(d) < 60) {
            return (dRatioOfTones >= d2 || dRatioOfTones >= dRatioOfTones2 || ((Math.abs(dRatioOfTones - dRatioOfTones2) > 0.1d ? 1 : (Math.abs(dRatioOfTones - dRatioOfTones2) == 0.1d ? 0 : -1)) < 0 && (dRatioOfTones > d2 ? 1 : (dRatioOfTones == d2 ? 0 : -1)) < 0 && (dRatioOfTones2 > d2 ? 1 : (dRatioOfTones2 == d2 ? 0 : -1)) < 0)) ? dLighter : dMax;
        }
        if (dRatioOfTones2 >= d2 || dRatioOfTones2 >= dRatioOfTones) {
        }
    }

    public static DynamicColor fromPalette(String str, Function function, Function function2) {
        return new DynamicColor(str, function, function2, false, null, null, null, null);
    }

    public final int getArgb(DynamicScheme dynamicScheme) {
        Hct hctFrom = (Hct) this.hctCache.get(dynamicScheme);
        if (hctFrom == null) {
            double tone = getTone(dynamicScheme);
            TonalPalette tonalPalette = (TonalPalette) this.palette.apply(dynamicScheme);
            hctFrom = Hct.from(tonalPalette.hue, tonalPalette.chroma, tone);
            if (this.hctCache.size() > 4) {
                this.hctCache.clear();
            }
            this.hctCache.put(dynamicScheme, hctFrom);
        }
        int i = hctFrom.argb;
        Function function = this.opacity;
        if (function == null) {
            return i;
        }
        int iRound = (int) Math.round(((Double) function.apply(dynamicScheme)).doubleValue() * 255.0d);
        if (iRound < 0) {
            iRound = 0;
        } else if (iRound > 255) {
            iRound = 255;
        }
        return (iRound << 24) | (16777215 & i);
    }

    public final double getTone(DynamicScheme dynamicScheme) {
        double dMax;
        double dMin;
        double d = dynamicScheme.contrastLevel;
        boolean z = false;
        boolean z2 = d < 0.0d;
        Function function = this.toneDeltaPair;
        double d2 = 60.0d;
        if (function == null) {
            double dDoubleValue = ((Double) this.tone.apply(dynamicScheme)).doubleValue();
            Function function2 = this.background;
            if (function2 == null) {
                return dDoubleValue;
            }
            double tone = ((DynamicColor) function2.apply(dynamicScheme)).getTone(dynamicScheme);
            double d3 = this.contrastCurve.get(d);
            if (Contrast.ratioOfTones(tone, dDoubleValue) < d3) {
                dDoubleValue = foregroundTone(tone, d3);
            }
            if (z2) {
                dDoubleValue = foregroundTone(tone, d3);
            }
            if (this.isBackground && 50.0d <= dDoubleValue && dDoubleValue < 60.0d) {
                dDoubleValue = Contrast.ratioOfTones(49.0d, tone) >= d3 ? 49.0d : 60.0d;
            }
            if (this.secondBackground != null) {
                double tone2 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
                double tone3 = ((DynamicColor) this.secondBackground.apply(dynamicScheme)).getTone(dynamicScheme);
                double dMax2 = Math.max(tone2, tone3);
                double dMin2 = Math.min(tone2, tone3);
                if (Contrast.ratioOfTones(dMax2, dDoubleValue) < d3 || Contrast.ratioOfTones(dMin2, dDoubleValue) < d3) {
                    double dLighter = Contrast.lighter(dMax2, d3);
                    double dDarker = Contrast.darker(dMin2, d3);
                    ArrayList arrayList = new ArrayList();
                    if (dLighter != -1.0d) {
                        arrayList.add(Double.valueOf(dLighter));
                    }
                    if (dDarker != -1.0d) {
                        arrayList.add(Double.valueOf(dDarker));
                    }
                    if (Math.round(tone2) < 60 || Math.round(tone3) < 60) {
                        if (dLighter == -1.0d) {
                            return 100.0d;
                        }
                        return dLighter;
                    }
                    if (arrayList.size() == 1) {
                        return ((Double) arrayList.get(0)).doubleValue();
                    }
                    if (dDarker == -1.0d) {
                        return 0.0d;
                    }
                    return dDarker;
                }
            }
            return dDoubleValue;
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
        boolean zEquals = this.name.equals(dynamicColor3.name);
        double d4 = z3 ? 1.0d : -1.0d;
        double d5 = dynamicColor3.contrastCurve.get(d);
        double d6 = dynamicColor.contrastCurve.get(d);
        double dDoubleValue2 = ((Double) dynamicColor3.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue2) < d5) {
            dDoubleValue2 = foregroundTone(tone4, d5);
        }
        double dDoubleValue3 = ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue();
        if (Contrast.ratioOfTones(tone4, dDoubleValue3) < d6) {
            dDoubleValue3 = foregroundTone(tone4, d6);
        }
        if (z2) {
            dDoubleValue2 = foregroundTone(tone4, d5);
            dDoubleValue3 = foregroundTone(tone4, d6);
        }
        double d7 = (dDoubleValue3 - dDoubleValue2) * d4;
        double d8 = toneDeltaPair.delta;
        if (d7 < d8) {
            double d9 = d8 * d4;
            dDoubleValue3 = dDoubleValue2 + d9;
            if (dDoubleValue3 < 0.0d) {
                dDoubleValue3 = 0.0d;
            } else if (dDoubleValue3 > 100.0d) {
                dDoubleValue3 = 100.0d;
            }
            if ((dDoubleValue3 - dDoubleValue2) * d4 < d8) {
                double d10 = dDoubleValue3 - d9;
                if (d10 < 0.0d) {
                    d10 = 0.0d;
                } else if (d10 > 100.0d) {
                    d10 = 100.0d;
                }
                dDoubleValue2 = d10;
            }
        }
        if (50.0d > dDoubleValue2 || dDoubleValue2 >= 60.0d) {
            if (50.0d > dDoubleValue3 || dDoubleValue3 >= 60.0d) {
                d2 = dDoubleValue2;
                dMax = dDoubleValue3;
            } else if (toneDeltaPair.stayTogether) {
                if (d4 > 0.0d) {
                    dMax = Math.max(dDoubleValue3, (d8 * d4) + 60.0d);
                } else {
                    dMin = Math.min(dDoubleValue3, (d8 * d4) + 49.0d);
                    dMax = dMin;
                    d2 = 49.0d;
                }
            } else if (d4 > 0.0d) {
                d2 = dDoubleValue2;
                dMax = 60.0d;
            } else {
                d2 = dDoubleValue2;
                dMax = 49.0d;
            }
        } else if (d4 > 0.0d) {
            dMax = Math.max(dDoubleValue3, (d8 * d4) + 60.0d);
        } else {
            dMin = Math.min(dDoubleValue3, (d8 * d4) + 49.0d);
            dMax = dMin;
            d2 = 49.0d;
        }
        return zEquals ? d2 : dMax;
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
