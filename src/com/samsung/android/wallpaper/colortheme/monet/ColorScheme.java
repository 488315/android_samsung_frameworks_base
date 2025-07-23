package com.samsung.android.wallpaper.colortheme.monet;

import android.app.WallpaperColors;
import android.graphics.Color;
import android.hardware.gnss.GnssSignalType;
import android.hardware.scontext.SContextConstants;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.graphics.cam.Cam;
import com.android.internal.graphics.cam.CamUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public class ColorScheme {
    public static final float ACCENT1_CHROMA = 48.0f;
    public static final int GOOGLE_BLUE = -14979341;
    public static final int MIN_CHROMA = 5;
    private static final String TAG = "ColorScheme";
    private List<Integer> accent1;
    private List<Integer> accent2;
    private List<Integer> accent3;
    private final boolean darkTheme;
    private List<Integer> neutral1;
    private List<Integer> neutral2;
    private final int seed;
    private final Style style;

    public static double wrapDegreesDouble(double d) {
        return d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? (d % 360.0d) + 360.0d : d >= 360.0d ? d % 360.0d : d;
    }

    public ColorScheme(int i, boolean z) {
        this(i, z, Style.TONAL_SPOT);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z) {
        this(wallpaperColors, z, Style.TONAL_SPOT);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean z, Style style) {
        this(getSeedColor(wallpaperColors, style != Style.CONTENT), z, style);
    }

    public ColorScheme(int i, boolean z, Style style) {
        this.seed = i;
        this.darkTheme = z;
        this.style = style;
        init();
    }

    private void init() {
        Cam fromInt = Cam.fromInt(this.seed);
        int i = this.seed;
        int i2 = GOOGLE_BLUE;
        if (i != 0 && (this.style == Style.CONTENT || fromInt.getChroma() >= 5.0f)) {
            i2 = this.seed;
        }
        Cam fromInt2 = Cam.fromInt(i2);
        this.accent1 = this.style.getCoreSpec().getA1().shades(fromInt2);
        this.accent2 = this.style.getCoreSpec().getA2().shades(fromInt2);
        this.accent3 = this.style.getCoreSpec().getA3().shades(fromInt2);
        this.neutral1 = this.style.getCoreSpec().getN1().shades(fromInt2);
        this.neutral2 = this.style.getCoreSpec().getN2().shades(fromInt2);
    }

    public List<Integer> getAccent1() {
        return this.accent1;
    }

    public List<Integer> getAccent2() {
        return this.accent2;
    }

    public List<Integer> getAccent3() {
        return this.accent3;
    }

    public List<Integer> getNeutral1() {
        return this.neutral1;
    }

    public List<Integer> getNeutral2() {
        return this.neutral2;
    }

    public List<Integer> allAccentColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.accent1);
        arrayList.addAll(this.accent2);
        arrayList.addAll(this.accent3);
        return arrayList;
    }

    public List<Integer> allNeutralColors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.neutral1);
        arrayList.addAll(this.neutral2);
        return arrayList;
    }

    public int backgroundColor() {
        return ColorUtils.setAlphaComponent(this.neutral1.get(this.darkTheme ? 8 : 0).intValue(), 255);
    }

    public int accentColor() {
        return ColorUtils.setAlphaComponent(this.accent1.get(this.darkTheme ? 2 : 6).intValue(), 255);
    }

    public String toString() {
        return "ColorScheme {\n  seed color: " + stringForColor(this.seed) + "\n  style: " + this.style + "\n  palettes: \n  " + humanReadable("PRIMARY", this.accent1) + "\n  " + humanReadable("SECONDARY", this.accent2) + "\n  " + humanReadable("TERTIARY", this.accent3) + "\n  " + humanReadable("NEUTRAL", this.neutral1) + "\n  " + humanReadable("NEUTRAL VARIANT", this.neutral2) + "\n}";
    }

    public final int getSeed() {
        return this.seed;
    }

    public final boolean getDarkTheme() {
        return this.darkTheme;
    }

    public final Style getStyle() {
        return this.style;
    }

    public static int getSeedColor(WallpaperColors wallpaperColors) {
        return getSeedColors(wallpaperColors, true).get(0).intValue();
    }

    public static int getSeedColor(WallpaperColors wallpaperColors, boolean z) {
        return getSeedColors(wallpaperColors, z).get(0).intValue();
    }

    public static List<Integer> getSeedColors(WallpaperColors wallpaperColors) {
        return getSeedColors(wallpaperColors, true);
    }

    public static List<Integer> getSeedColors(WallpaperColors wallpaperColors, boolean z) {
        Map<Integer, Integer> allColors = wallpaperColors.getAllColors();
        double d = 0.0d;
        while (new ArrayList(allColors.values()).iterator().hasNext()) {
            d += ((Integer) r2.next()).intValue();
        }
        boolean z2 = d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        if (z2) {
            List<Color> mainColors = wallpaperColors.getMainColors();
            ArrayList arrayList = new ArrayList();
            for (Color color : mainColors) {
                if (!z) {
                    arrayList.add(Integer.valueOf(color.toArgb()));
                } else if (Cam.fromInt(color.toArgb()).getChroma() >= 5.0f) {
                    arrayList.add(Integer.valueOf(color.toArgb()));
                }
            }
            if (arrayList.isEmpty()) {
                arrayList.add(Integer.valueOf(GOOGLE_BLUE));
            }
            return arrayList;
        }
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        for (Map.Entry<Integer, Integer> entry : allColors.entrySet()) {
            hashMap.put(entry.getKey(), Double.valueOf(entry.getValue().intValue() / d));
            hashMap2.put(entry.getKey(), Cam.fromInt(entry.getKey().intValue()));
        }
        List<Double> huePopulations = huePopulations(hashMap2, hashMap, z);
        HashMap hashMap3 = new HashMap();
        for (Map.Entry<Integer, Integer> entry2 : allColors.entrySet()) {
            int round = Math.round(((Cam) hashMap2.get(entry2.getKey())).getHue());
            double d2 = 0.0d;
            for (int i = round - 15; i <= round + 15; i++) {
                d2 += huePopulations.get(wrapDegrees(i)).doubleValue();
            }
            hashMap3.put(entry2.getKey(), Double.valueOf(d2));
        }
        HashMap hashMap4 = new HashMap();
        for (Map.Entry entry3 : hashMap2.entrySet()) {
            if (!z) {
                hashMap4.put((Integer) entry3.getKey(), (Cam) entry3.getValue());
            } else {
                Cam cam = (Cam) entry3.getValue();
                double doubleValue = ((Double) hashMap3.get(entry3.getKey())).doubleValue();
                if (cam.getChroma() >= 5.0f && (z2 || doubleValue > 0.01d)) {
                    hashMap4.put((Integer) entry3.getKey(), (Cam) entry3.getValue());
                }
            }
        }
        HashMap hashMap5 = new HashMap();
        for (Map.Entry entry4 : hashMap4.entrySet()) {
            hashMap5.put((Integer) entry4.getKey(), Double.valueOf(score((Cam) entry4.getValue(), ((Double) hashMap3.get(entry4.getKey())).doubleValue())));
        }
        List entriesSortedByValues = entriesSortedByValues(hashMap5);
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 90; i2 >= 15; i2--) {
            arrayList2.clear();
            Iterator it = entriesSortedByValues.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) ((Map.Entry) it.next()).getKey();
                num.intValue();
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        arrayList2.add(num);
                        break;
                    }
                    Integer num2 = (Integer) it2.next();
                    num2.intValue();
                    if (hueDiff(((Cam) hashMap2.get(num)).getHue(), ((Cam) hashMap2.get(num2)).getHue()) < i2) {
                        break;
                    }
                }
            }
            if (arrayList2.size() >= 4) {
                break;
            }
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(Integer.valueOf(GOOGLE_BLUE));
        }
        return arrayList2;
    }

    private static int wrapDegrees(int i) {
        if (i < 0) {
            return (i % 360) + 360;
        }
        return i >= 360 ? i % 360 : i;
    }

    private static float hueDiff(float f, float f2) {
        return 180.0f - Math.abs(Math.abs(f - f2) - 180.0f);
    }

    private static final String stringForColor(int i) {
        Cam fromInt = Cam.fromInt(i);
        return ("H" + String.format("%04d", Integer.valueOf(Math.round(fromInt.getHue())))) + (GnssSignalType.CODE_TYPE_C + String.format("%04d", Integer.valueOf(Math.round(fromInt.getChroma())))) + ("T" + String.format("%04d", Integer.valueOf(Math.round(CamUtils.lstarFromInt(i))))) + " = #" + String.format("%-06s", Integer.toHexString(i & 16777215).toUpperCase(Locale.ROOT));
    }

    public static String humanReadable(String str, List<Integer> list) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str).append('\n');
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            stringBuffer.append(stringForColor(it.next().intValue()));
        }
        return stringBuffer.toString();
    }

    private static double score(Cam cam, double d) {
        double chroma;
        double d2;
        double d3 = d * 70.0d;
        if (cam.getChroma() < 48.0f) {
            chroma = cam.getChroma() - 48.0f;
            d2 = 0.1d;
        } else {
            chroma = cam.getChroma() - 48.0f;
            d2 = 0.3d;
        }
        return (chroma * d2) + d3;
    }

    private static List<Double> huePopulations(Map<Integer, Cam> map, Map<Integer, Double> map2, boolean z) {
        ArrayList arrayList = new ArrayList(360);
        for (int i = 0; i < 360; i++) {
            arrayList.add(Double.valueOf(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN));
        }
        for (Map.Entry<Integer, Double> entry : map2.entrySet()) {
            double doubleValue = entry.getValue().doubleValue();
            Cam cam = map.get(entry.getKey());
            int round = Math.round(cam.getHue()) % 360;
            if (!z || cam.getChroma() > 5.0f) {
                arrayList.set(round, Double.valueOf(((Double) arrayList.get(round)).doubleValue() + doubleValue));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        ArrayList<Map.Entry> arrayList = new ArrayList(map.entrySet());
        arrayList.sort(Map.Entry.comparingByValue());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : arrayList) {
            linkedHashMap.put(entry.getKey(), (Comparable) entry.getValue());
        }
        return linkedHashMap;
    }

    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        ArrayList arrayList = new ArrayList(map.entrySet());
        Collections.sort(arrayList, new Comparator<Map.Entry<K, V>>() { // from class: com.samsung.android.wallpaper.colortheme.monet.ColorScheme.1
            @Override // java.util.Comparator
            public int compare(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
                return ((Comparable) entry2.getValue()).compareTo(entry.getValue());
            }
        });
        return arrayList;
    }
}
