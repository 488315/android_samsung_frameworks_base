package com.android.systemui.monet.dynamiccolor;

import com.android.systemui.monet.contrast.Contrast;
import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.utils.ColorUtils;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DynamicColor {
    public final Function background;
    public final Function chroma;
    public final HashMap hctCache = new HashMap();
    public final Function hue;
    public final Function opacity;
    public final Function tone;
    public final Function toneDeltaConstraint;
    public final Function toneMaxContrast;
    public final Function toneMinContrast;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.monet.dynamiccolor.DynamicColor$1 */
    public abstract /* synthetic */ class AbstractC18361 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$monet$dynamiccolor$TonePolarity;

        static {
            int[] iArr = new int[TonePolarity.values().length];
            $SwitchMap$com$android$systemui$monet$dynamiccolor$TonePolarity = iArr;
            try {
                iArr[TonePolarity.DARKER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$dynamiccolor$TonePolarity[TonePolarity.LIGHTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$dynamiccolor$TonePolarity[TonePolarity.NO_PREFERENCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public DynamicColor(Function<DynamicScheme, Double> function, Function<DynamicScheme, Double> function2, Function<DynamicScheme, Double> function3, Function<DynamicScheme, Double> function4, Function<DynamicScheme, DynamicColor> function5, Function<DynamicScheme, Double> function6, Function<DynamicScheme, Double> function7, Function<DynamicScheme, ToneDeltaConstraint> function8) {
        this.hue = function;
        this.chroma = function2;
        this.tone = function3;
        this.opacity = function4;
        this.background = function5;
        this.toneMinContrast = function6;
        this.toneMaxContrast = function7;
        this.toneDeltaConstraint = function8;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0135, code lost:
    
        if (r3 > 100.0d) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:?, code lost:
    
        return 100.0d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0146, code lost:
    
        if (r3 > 100.0d) goto L73;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double calculateDynamicTone(DynamicScheme dynamicScheme, Function function, DynamicColor$$ExternalSyntheticLambda0 dynamicColor$$ExternalSyntheticLambda0, BiFunction biFunction, Function function2, Function function3, DynamicColor$$ExternalSyntheticLambda2 dynamicColor$$ExternalSyntheticLambda2, Function function4) {
        double d;
        double doubleValue;
        double contrastingTone;
        Function function5;
        ToneDeltaConstraint toneDeltaConstraint;
        double d2;
        double doubleValue2 = ((Double) function.apply(dynamicScheme)).doubleValue();
        DynamicColor dynamicColor = function2 == null ? null : (DynamicColor) function2.apply(dynamicScheme);
        if (dynamicColor == null) {
            return doubleValue2;
        }
        double ratioOfTones = Contrast.ratioOfTones(doubleValue2, ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue());
        double doubleValue3 = ((Double) dynamicColor$$ExternalSyntheticLambda0.apply(dynamicColor)).doubleValue();
        double doubleValue4 = ((Double) biFunction.apply(Double.valueOf(ratioOfTones), Double.valueOf(doubleValue3))).doubleValue();
        double ratioOfTones2 = Contrast.ratioOfTones(doubleValue3, doubleValue4);
        double doubleValue5 = (dynamicColor$$ExternalSyntheticLambda2 == null || dynamicColor$$ExternalSyntheticLambda2.apply(Double.valueOf(ratioOfTones)) == null) ? 1.0d : ((Double) dynamicColor$$ExternalSyntheticLambda2.apply(Double.valueOf(ratioOfTones))).doubleValue();
        if (function4 == null) {
            d = doubleValue4;
        } else {
            d = doubleValue4;
            if (function4.apply(Double.valueOf(ratioOfTones)) != null) {
                doubleValue = ((Double) function4.apply(Double.valueOf(ratioOfTones))).doubleValue();
                if (ratioOfTones2 >= doubleValue5) {
                    doubleValue5 = ratioOfTones2 > doubleValue ? doubleValue : ratioOfTones2;
                }
                contrastingTone = doubleValue5 != ratioOfTones2 ? d : contrastingTone(doubleValue3, doubleValue5);
                function5 = dynamicColor.background;
                if (function5 != null || function5.apply(dynamicScheme) == null) {
                    contrastingTone = enableLightForeground(contrastingTone);
                }
                toneDeltaConstraint = function3 != null ? null : (ToneDeltaConstraint) function3.apply(dynamicScheme);
                if (toneDeltaConstraint != null) {
                    return contrastingTone;
                }
                DynamicColor dynamicColor2 = toneDeltaConstraint.keepAway;
                double doubleValue6 = ((Double) dynamicColor$$ExternalSyntheticLambda0.apply(dynamicColor2)).doubleValue();
                double abs = Math.abs(contrastingTone - doubleValue6);
                double d3 = toneDeltaConstraint.delta;
                if (abs >= d3) {
                    return contrastingTone;
                }
                int i = AbstractC18361.$SwitchMap$com$android$systemui$monet$dynamiccolor$TonePolarity[toneDeltaConstraint.keepAwayPolarity.ordinal()];
                boolean z = true;
                if (i == 1) {
                    d2 = doubleValue6 + d3;
                    if (d2 < 0.0d) {
                        return 0.0d;
                    }
                } else {
                    if (i != 2) {
                        if (i != 3) {
                            return contrastingTone;
                        }
                        boolean z2 = doubleValue2 > ((Double) dynamicColor2.tone.apply(dynamicScheme)).doubleValue();
                        double abs2 = Math.abs(abs - d3);
                        if (!z2 ? contrastingTone >= abs2 : contrastingTone + abs2 > 100.0d) {
                            z = false;
                        }
                        return z ? contrastingTone + abs2 : contrastingTone - abs2;
                    }
                    d2 = doubleValue6 - d3;
                    if (d2 < 0.0d) {
                        return 0.0d;
                    }
                }
            }
        }
        doubleValue = 21.0d;
        if (ratioOfTones2 >= doubleValue5) {
        }
        if (doubleValue5 != ratioOfTones2) {
        }
        function5 = dynamicColor.background;
        if (function5 != null) {
        }
        contrastingTone = enableLightForeground(contrastingTone);
        if (function3 != null) {
        }
        if (toneDeltaConstraint != null) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005e, code lost:
    
        if (r5 <= 100.0d) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double contrastingTone(double d, double d2) {
        double d3;
        double d4;
        if (d >= 0.0d && d <= 100.0d) {
            double yFromLstar = ColorUtils.yFromLstar(d);
            double d5 = ((yFromLstar + 5.0d) * d2) - 5.0d;
            if (d5 >= 0.0d && d5 <= 100.0d) {
                double max = Math.max(d5, yFromLstar);
                if (max == yFromLstar) {
                    yFromLstar = d5;
                }
                double d6 = (max + 5.0d) / (yFromLstar + 5.0d);
                double abs = Math.abs(d6 - d2);
                if (d6 >= d2 || abs <= 0.04d) {
                    d3 = ((ColorUtils.labF(d5 / 100.0d) * 116.0d) - 16.0d) + 0.4d;
                    if (d3 >= 0.0d) {
                    }
                }
            }
        }
        d3 = -1.0d;
        if (d3 < 0.0d) {
            d3 = 100.0d;
        }
        if (d >= 0.0d && d <= 100.0d) {
            double yFromLstar2 = ColorUtils.yFromLstar(d);
            double d7 = ((yFromLstar2 + 5.0d) / d2) - 5.0d;
            if (d7 >= 0.0d && d7 <= 100.0d) {
                double max2 = Math.max(yFromLstar2, d7);
                if (max2 != d7) {
                    yFromLstar2 = d7;
                }
                double d8 = (max2 + 5.0d) / (yFromLstar2 + 5.0d);
                double abs2 = Math.abs(d8 - d2);
                if (d8 >= d2 || abs2 <= 0.04d) {
                    double labF = ((ColorUtils.labF(d7 / 100.0d) * 116.0d) - 16.0d) - 0.4d;
                    if (labF >= 0.0d && labF <= 100.0d) {
                        d4 = labF;
                        double max3 = Math.max(0.0d, d4);
                        double ratioOfTones = Contrast.ratioOfTones(d3, d);
                        double ratioOfTones2 = Contrast.ratioOfTones(max3, d);
                        if (Math.round(d) >= 60) {
                            return (ratioOfTones2 >= d2 || ratioOfTones2 >= ratioOfTones) ? max3 : d3;
                        }
                        return (ratioOfTones >= d2 || ratioOfTones >= ratioOfTones2 || (Math.abs(ratioOfTones - ratioOfTones2) < 0.1d && ratioOfTones < d2 && ratioOfTones2 < d2)) ? d3 : max3;
                    }
                }
            }
        }
        d4 = -1.0d;
        double max32 = Math.max(0.0d, d4);
        double ratioOfTones3 = Contrast.ratioOfTones(d3, d);
        double ratioOfTones22 = Contrast.ratioOfTones(max32, d);
        if (Math.round(d) >= 60) {
        }
    }

    public static double enableLightForeground(double d) {
        if (!(Math.round(d) < 60)) {
            return d;
        }
        if (Math.round(d) <= 49) {
            return d;
        }
        return 49.0d;
    }

    public static DynamicColor fromPalette(Function function, final Function function2, final Function function3, final Function function4) {
        final int i = 0;
        final int i2 = 1;
        return new DynamicColor(new DynamicColor$$ExternalSyntheticLambda3(function, 0), new DynamicColor$$ExternalSyntheticLambda3(function, 1), function2, null, function3, new Function() { // from class: com.android.systemui.monet.dynamiccolor.DynamicColor$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i) {
                    case 0:
                        Function function5 = function2;
                        Function function6 = function3;
                        DynamicScheme dynamicScheme = (DynamicScheme) obj;
                        return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme, function5, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme, 1), new DynamicColor$$ExternalSyntheticLambda5(function5, dynamicScheme, function6), function6, function4, null, new DynamicColor$$ExternalSyntheticLambda6()));
                    default:
                        Function function7 = function2;
                        Function function8 = function3;
                        DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                        return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme2, function7, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme2, 2), new DynamicColor$$ExternalSyntheticLambda7(function8, dynamicScheme2), function8, function4, null, null));
                }
            }
        }, new Function() { // from class: com.android.systemui.monet.dynamiccolor.DynamicColor$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        Function function5 = function2;
                        Function function6 = function3;
                        DynamicScheme dynamicScheme = (DynamicScheme) obj;
                        return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme, function5, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme, 1), new DynamicColor$$ExternalSyntheticLambda5(function5, dynamicScheme, function6), function6, function4, null, new DynamicColor$$ExternalSyntheticLambda6()));
                    default:
                        Function function7 = function2;
                        Function function8 = function3;
                        DynamicScheme dynamicScheme2 = (DynamicScheme) obj;
                        return Double.valueOf(DynamicColor.calculateDynamicTone(dynamicScheme2, function7, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme2, 2), new DynamicColor$$ExternalSyntheticLambda7(function8, dynamicScheme2), function8, function4, null, null));
                }
            }
        }, function4);
    }

    public final int getArgb(DynamicScheme dynamicScheme) {
        HashMap hashMap = this.hctCache;
        Hct hct = (Hct) hashMap.get(dynamicScheme);
        if (hct == null) {
            hct = Hct.from(((Double) this.hue.apply(dynamicScheme)).doubleValue(), ((Double) this.chroma.apply(dynamicScheme)).doubleValue(), getTone(dynamicScheme));
            if (hashMap.size() > 4) {
                hashMap.clear();
            }
            hashMap.put(dynamicScheme, hct);
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
        double d2;
        Function function = this.tone;
        final double doubleValue = ((Double) function.apply(dynamicScheme)).doubleValue();
        double d3 = dynamicScheme.contrastLevel;
        boolean z = d3 < 0.0d;
        Function function2 = this.toneMinContrast;
        Function function3 = this.toneMaxContrast;
        if (d3 != 0.0d) {
            double doubleValue2 = ((Double) function.apply(dynamicScheme)).doubleValue();
            doubleValue = doubleValue2 + (Math.abs(dynamicScheme.contrastLevel) * (((Double) (z ? function2.apply(dynamicScheme) : function3.apply(dynamicScheme))).doubleValue() - doubleValue2));
        }
        Function function4 = this.background;
        DynamicColor dynamicColor = function4 == null ? null : (DynamicColor) function4.apply(dynamicScheme);
        if (dynamicColor != null) {
            Function function5 = dynamicColor.background;
            boolean z2 = (function5 == null || function5.apply(dynamicScheme) == null) ? false : true;
            double ratioOfTones = Contrast.ratioOfTones(((Double) function.apply(dynamicScheme)).doubleValue(), ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue());
            if (z) {
                double ratioOfTones2 = Contrast.ratioOfTones(((Double) function2.apply(dynamicScheme)).doubleValue(), ((Double) dynamicColor.toneMinContrast.apply(dynamicScheme)).doubleValue());
                d = ratioOfTones;
                if (z2) {
                    d2 = ratioOfTones2;
                }
            } else {
                double ratioOfTones3 = Contrast.ratioOfTones(((Double) function3.apply(dynamicScheme)).doubleValue(), ((Double) dynamicColor.toneMaxContrast.apply(dynamicScheme)).doubleValue());
                double min = z2 ? Math.min(ratioOfTones3, ratioOfTones) : 1.0d;
                if (z2) {
                    double max = Math.max(ratioOfTones3, ratioOfTones);
                    d2 = min;
                    d = max;
                } else {
                    d2 = min;
                    d = 21.0d;
                }
            }
            return calculateDynamicTone(dynamicScheme, this.tone, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme, 0), new BiFunction() { // from class: com.android.systemui.monet.dynamiccolor.DynamicColor$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Double.valueOf(doubleValue);
                }
            }, new DynamicColor$$ExternalSyntheticLambda3(dynamicColor, 2), this.toneDeltaConstraint, new DynamicColor$$ExternalSyntheticLambda2(d2, 0), new DynamicColor$$ExternalSyntheticLambda2(d, 1));
        }
        d = 21.0d;
        d2 = 1.0d;
        return calculateDynamicTone(dynamicScheme, this.tone, new DynamicColor$$ExternalSyntheticLambda0(dynamicScheme, 0), new BiFunction() { // from class: com.android.systemui.monet.dynamiccolor.DynamicColor$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Double.valueOf(doubleValue);
            }
        }, new DynamicColor$$ExternalSyntheticLambda3(dynamicColor, 2), this.toneDeltaConstraint, new DynamicColor$$ExternalSyntheticLambda2(d2, 0), new DynamicColor$$ExternalSyntheticLambda2(d, 1));
    }
}
