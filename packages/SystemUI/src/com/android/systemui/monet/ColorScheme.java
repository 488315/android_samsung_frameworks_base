package com.android.systemui.monet;

import android.app.WallpaperColors;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.scheme.SchemeContent;
import com.google.ux.material.libmonet.scheme.SchemeExpressive;
import com.google.ux.material.libmonet.scheme.SchemeFruitSalad;
import com.google.ux.material.libmonet.scheme.SchemeMonochrome;
import com.google.ux.material.libmonet.scheme.SchemeNeutral;
import com.google.ux.material.libmonet.scheme.SchemeRainbow;
import com.google.ux.material.libmonet.scheme.SchemeTonalSpot;
import com.google.ux.material.libmonet.scheme.SchemeVibrant;
import com.sec.ims.settings.ImsProfile;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ColorScheme {
    public final TonalPalette mAccent1;
    public final TonalPalette mAccent2;
    public final TonalPalette mAccent3;
    public final boolean mIsDark;
    public final DynamicScheme mMaterialScheme;
    public final TonalPalette mNeutral1;
    public final TonalPalette mNeutral2;
    public final int mSeed;
    public final int mStyle;

    public ColorScheme(int i, boolean z, int i2, double d) {
        DynamicScheme schemeNeutral;
        this.mSeed = i;
        this.mIsDark = z;
        this.mStyle = i2;
        Hct fromInt = Hct.fromInt(i);
        if (i == 0 || (i2 != 6 && fromInt.chroma < 5.0d)) {
            i = -14979341;
        }
        Hct fromInt2 = Hct.fromInt(i);
        switch (i2) {
            case 0:
                schemeNeutral = new SchemeNeutral(fromInt2, z, d);
                break;
            case 1:
                schemeNeutral = new SchemeTonalSpot(fromInt2, z, d);
                break;
            case 2:
                schemeNeutral = new SchemeVibrant(fromInt2, z, d);
                break;
            case 3:
                schemeNeutral = new SchemeExpressive(fromInt2, z, d);
                break;
            case 4:
                schemeNeutral = new SchemeRainbow(fromInt2, z, d);
                break;
            case 5:
                schemeNeutral = new SchemeFruitSalad(fromInt2, z, d);
                break;
            case 6:
                schemeNeutral = new SchemeContent(fromInt2, z, d);
                break;
            case 7:
                schemeNeutral = new SchemeMonochrome(fromInt2, z, d);
                break;
            case 8:
                schemeNeutral = new SchemeClock(fromInt2, z, d);
                break;
            case 9:
                schemeNeutral = new SchemeClockVibrant(fromInt2, z, d);
                break;
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Unknown style: "));
        }
        this.mMaterialScheme = schemeNeutral;
        this.mAccent1 = new TonalPalette(schemeNeutral.primaryPalette);
        this.mAccent2 = new TonalPalette(schemeNeutral.secondaryPalette);
        this.mAccent3 = new TonalPalette(schemeNeutral.tertiaryPalette);
        this.mNeutral1 = new TonalPalette(schemeNeutral.neutralPalette);
        this.mNeutral2 = new TonalPalette(schemeNeutral.neutralVariantPalette);
        new TonalPalette(schemeNeutral.errorPalette);
    }

    public static List getSeedColors(WallpaperColors wallpaperColors, final boolean z) {
        final double sum = wallpaperColors.getAllColors().values().stream().mapToInt(new ColorScheme$$ExternalSyntheticLambda0()).sum();
        if (sum == 0.0d) {
            List list = (List) wallpaperColors.getMainColors().stream().map(new ColorScheme$$ExternalSyntheticLambda1(3)).distinct().filter(new Predicate() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return !z || Hct.fromInt(((Integer) obj).intValue()).chroma >= 5.0d;
                }
            }).collect(Collectors.toList());
            return list.isEmpty() ? List.of(-14979341) : list;
        }
        Map map = (Map) wallpaperColors.getAllColors().entrySet().stream().collect(Collectors.toMap(new ColorScheme$$ExternalSyntheticLambda1(0), new Function() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((Integer) ((Map.Entry) obj).getValue()).doubleValue() / sum);
            }
        }));
        final Map map2 = (Map) wallpaperColors.getAllColors().entrySet().stream().collect(Collectors.toMap(new ColorScheme$$ExternalSyntheticLambda1(0), new ColorScheme$$ExternalSyntheticLambda1(4)));
        final ArrayList arrayList = new ArrayList(Collections.nCopies(360, Double.valueOf(0.0d)));
        for (Map.Entry entry : map.entrySet()) {
            double doubleValue = ((Double) entry.getValue()).doubleValue();
            Hct hct = (Hct) map2.get(entry.getKey());
            int round = ((int) Math.round(hct.hue)) % 360;
            if (!z || hct.chroma > 5.0d) {
                arrayList.set(round, Double.valueOf(((Double) arrayList.get(round)).doubleValue() + doubleValue));
            }
        }
        final Map map3 = (Map) wallpaperColors.getAllColors().entrySet().stream().collect(Collectors.toMap(new ColorScheme$$ExternalSyntheticLambda1(0), new Function() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Map map4 = map2;
                List list2 = arrayList;
                int round2 = (int) Math.round(((Hct) map4.get(((Map.Entry) obj).getKey())).hue);
                int i = round2 - 15;
                double d = 0.0d;
                while (i <= round2 + 15) {
                    d += ((Double) list2.get(i < 0 ? (i % 360) + 360 : i >= 360 ? i % 360 : i)).doubleValue();
                    i++;
                }
                return Double.valueOf(d);
            }
        }));
        List list2 = (List) (z ? (Map) map2.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Map.Entry entry2 = (Map.Entry) obj;
                return ((Hct) entry2.getValue()).chroma >= 5.0d && ((Double) map3.get(entry2.getKey())).doubleValue() > 0.01d;
            }
        }).collect(Collectors.toMap(new ColorScheme$$ExternalSyntheticLambda1(0), new ColorScheme$$ExternalSyntheticLambda1(2))) : map2).entrySet().stream().map(new Function() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                double d;
                double d2;
                Map map4 = map3;
                Map.Entry entry2 = (Map.Entry) obj;
                Integer num = (Integer) entry2.getKey();
                Hct hct2 = (Hct) entry2.getValue();
                double doubleValue2 = ((Double) map4.get(entry2.getKey())).doubleValue() * 70.0d;
                double d3 = hct2.chroma;
                if (d3 < 48.0d) {
                    d = d3 - 48.0d;
                    d2 = 0.1d;
                } else {
                    d = d3 - 48.0d;
                    d2 = 0.3d;
                }
                return new AbstractMap.SimpleEntry(num, Double.valueOf((d * d2) + doubleValue2));
            }
        }).sorted(Map.Entry.comparingByValue().reversed()).collect(Collectors.toList());
        ArrayList arrayList2 = new ArrayList();
        for (final int i = 90; i >= 15; i--) {
            arrayList2.clear();
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) ((Map.Entry) it.next()).getKey();
                final int intValue = num.intValue();
                if (!arrayList2.stream().anyMatch(new Predicate() { // from class: com.android.systemui.monet.ColorScheme$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        Map map4 = map2;
                        int i2 = intValue;
                        int i3 = i;
                        double abs = Math.abs(((Hct) map4.get(Integer.valueOf(i2))).hue - ((Hct) map4.get((Integer) obj)).hue);
                        if (abs > 180.0d) {
                            abs = 360.0d - abs;
                        }
                        return abs < ((double) i3);
                    }
                })) {
                    arrayList2.add(num);
                    if (arrayList2.size() >= 4) {
                        break;
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                break;
            }
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(-14979341);
        }
        return arrayList2;
    }

    public static String humanReadable(String str, List list) {
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "\n");
        m.append((String) list.stream().map(new ColorScheme$$ExternalSyntheticLambda1(1)).collect(Collectors.joining("\n")));
        return m.toString();
    }

    public static String stringForColor(int i) {
        Hct fromInt = Hct.fromInt(i);
        return ImsProfile.TIMER_NAME_H.concat(String.format("%4s", Long.valueOf(Math.round(fromInt.hue)))) + ImsProfile.TIMER_NAME_C.concat(String.format("%4s", Long.valueOf(Math.round(fromInt.chroma)))) + "T".concat(String.format("%4s", Long.valueOf(Math.round(fromInt.tone)))) + " = #" + Integer.toHexString(i & 16777215).toUpperCase();
    }

    public final String toString() {
        return "ColorScheme {\n  seed color: " + stringForColor(this.mSeed) + "\n  style: " + this.mStyle + "\n  palettes: \n  " + humanReadable("PRIMARY", this.mAccent1.allShades) + "\n  " + humanReadable("SECONDARY", this.mAccent2.allShades) + "\n  " + humanReadable("TERTIARY", this.mAccent3.allShades) + "\n  " + humanReadable("NEUTRAL", this.mNeutral1.allShades) + "\n  " + humanReadable("NEUTRAL VARIANT", this.mNeutral2.allShades) + "\n}";
    }

    public ColorScheme(int i, boolean z) {
        this(i, z, 1);
    }

    public ColorScheme(int i, boolean z, int i2) {
        this(i, z, i2, 0.0d);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z) {
        this(wallpaperColors, z, 1);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z, int i) {
        this(((Integer) getSeedColors(wallpaperColors, i != 6).get(0)).intValue(), z, i);
    }
}
