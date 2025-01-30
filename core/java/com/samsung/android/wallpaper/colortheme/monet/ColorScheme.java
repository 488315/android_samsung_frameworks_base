package com.samsung.android.wallpaper.colortheme.monet;

import android.app.WallpaperColors;
import android.graphics.Color;
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

/* loaded from: classes5.dex */
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

    public ColorScheme(int seed, boolean darkTheme) {
        this(seed, darkTheme, Style.TONAL_SPOT);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean darkTheme) {
        this(wallpaperColors, darkTheme, Style.TONAL_SPOT);
    }

    public ColorScheme(WallpaperColors wallpaperColors, boolean darkTheme, Style style) {
        this(getSeedColor(wallpaperColors, style != Style.CONTENT), darkTheme, style);
    }

    public ColorScheme(int seed, boolean darkTheme, Style style) {
        this.seed = seed;
        this.darkTheme = darkTheme;
        this.style = style;
        init();
    }

    private void init() {
        int seedArgb;
        Cam proposedSeedCam = Cam.fromInt(this.seed);
        if (this.seed == 0) {
            seedArgb = GOOGLE_BLUE;
        } else if (this.style != Style.CONTENT && proposedSeedCam.getChroma() < 5.0f) {
            seedArgb = GOOGLE_BLUE;
        } else {
            seedArgb = this.seed;
        }
        Cam camSeed = Cam.fromInt(seedArgb);
        this.accent1 = this.style.getCoreSpec().getA1().shades(camSeed);
        this.accent2 = this.style.getCoreSpec().getA2().shades(camSeed);
        this.accent3 = this.style.getCoreSpec().getA3().shades(camSeed);
        this.neutral1 = this.style.getCoreSpec().getN1().shades(camSeed);
        this.neutral2 = this.style.getCoreSpec().getN2().shades(camSeed);
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
        List<Integer> allColors = new ArrayList<>();
        allColors.addAll(this.accent1);
        allColors.addAll(this.accent2);
        allColors.addAll(this.accent3);
        return allColors;
    }

    public List<Integer> allNeutralColors() {
        List<Integer> allColors = new ArrayList<>();
        allColors.addAll(this.neutral1);
        allColors.addAll(this.neutral2);
        return allColors;
    }

    public int backgroundColor() {
        List<Integer> list;
        int i;
        if (this.darkTheme) {
            list = this.neutral1;
            i = 8;
        } else {
            list = this.neutral1;
            i = 0;
        }
        return ColorUtils.setAlphaComponent(list.get(i).intValue(), 255);
    }

    public int accentColor() {
        List<Integer> list;
        int i;
        if (this.darkTheme) {
            list = this.accent1;
            i = 2;
        } else {
            list = this.accent1;
            i = 6;
        }
        return ColorUtils.setAlphaComponent(list.get(i).intValue(), 255);
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

    public static int getSeedColor(WallpaperColors wallpaperColors, boolean filter) {
        return getSeedColors(wallpaperColors, filter).get(0).intValue();
    }

    public static List<Integer> getSeedColors(WallpaperColors wallpaperColors) {
        return getSeedColors(wallpaperColors, true);
    }

    public static List<Integer> getSeedColors(WallpaperColors wallpaperColors, boolean filter) {
        double totalPopulation;
        boolean z = filter;
        double totalPopulation2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        Map<Integer, Integer> allColors = wallpaperColors.getAllColors();
        List<Integer> importances = new ArrayList<>(allColors.values());
        Iterator<Integer> it = importances.iterator();
        while (it.hasNext()) {
            int importance = it.next().intValue();
            totalPopulation2 += importance;
        }
        boolean totalPopulationMeaningless = totalPopulation2 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        if (totalPopulationMeaningless) {
            List<Color> colors = wallpaperColors.getMainColors();
            List<Integer> distinctColors = new ArrayList<>();
            for (Color color : colors) {
                if (!z) {
                    distinctColors.add(Integer.valueOf(color.toArgb()));
                } else if (Cam.fromInt(color.toArgb()).getChroma() >= 5.0f) {
                    distinctColors.add(Integer.valueOf(color.toArgb()));
                }
            }
            if (distinctColors.isEmpty()) {
                distinctColors.add(Integer.valueOf(GOOGLE_BLUE));
            }
            return distinctColors;
        }
        Map<Integer, Double> intToProportion = new HashMap<>();
        Map<Integer, Cam> intToCam = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : allColors.entrySet()) {
            intToProportion.put(entry.getKey(), Double.valueOf(entry.getValue().intValue() / totalPopulation2));
            intToCam.put(entry.getKey(), Cam.fromInt(entry.getKey().intValue()));
        }
        List<Double> hueProportions = huePopulations(intToCam, intToProportion, z);
        Map<Integer, Double> intToHueProportion = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry2 : allColors.entrySet()) {
            Cam cam = intToCam.get(entry2.getKey());
            int hue = Math.round(cam.getHue());
            double proportion = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int i = hue - 15; i <= hue + 15; i++) {
                proportion += hueProportions.get(wrapDegrees(i)).doubleValue();
            }
            intToHueProportion.put(entry2.getKey(), Double.valueOf(proportion));
        }
        Map<Integer, Cam> filteredIntToCam = new HashMap<>();
        for (Map.Entry<Integer, Cam> entry3 : intToCam.entrySet()) {
            if (!z) {
                filteredIntToCam.put(entry3.getKey(), entry3.getValue());
                totalPopulation = totalPopulation2;
            } else {
                Cam cam2 = entry3.getValue();
                double proportion2 = intToHueProportion.get(entry3.getKey()).doubleValue();
                if (cam2.getChroma() < 5.0f) {
                    totalPopulation = totalPopulation2;
                } else if (totalPopulationMeaningless || proportion2 > 0.01d) {
                    totalPopulation = totalPopulation2;
                    filteredIntToCam.put(entry3.getKey(), entry3.getValue());
                } else {
                    totalPopulation = totalPopulation2;
                }
            }
            z = filter;
            totalPopulation2 = totalPopulation;
        }
        Map<Integer, Double> intToScoreIntermediate = new HashMap<>();
        for (Map.Entry<Integer, Cam> entry4 : filteredIntToCam.entrySet()) {
            intToScoreIntermediate.put(entry4.getKey(), Double.valueOf(score(entry4.getValue(), intToHueProportion.get(entry4.getKey()).doubleValue())));
        }
        List<Map.Entry<Integer, Double>> intToScore = entriesSortedByValues(intToScoreIntermediate);
        List<Integer> seeds = new ArrayList<>();
        int i2 = 90;
        while (i2 >= 15) {
            seeds.clear();
            Iterator<Map.Entry<Integer, Double>> it2 = intToScore.iterator();
            while (it2.hasNext()) {
                int key = it2.next().getKey().intValue();
                Iterator<Integer> it3 = seeds.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        int otherKey = it3.next().intValue();
                        Map<Integer, Double> intToScoreIntermediate2 = intToScoreIntermediate;
                        float hueA = intToCam.get(Integer.valueOf(key)).getHue();
                        List<Map.Entry<Integer, Double>> intToScore2 = intToScore;
                        float hueB = intToCam.get(Integer.valueOf(otherKey)).getHue();
                        float hueDiff = hueDiff(hueA, hueB);
                        float hueA2 = i2;
                        if (hueDiff < hueA2) {
                            intToScoreIntermediate = intToScoreIntermediate2;
                            intToScore = intToScore2;
                            break;
                        }
                        intToScoreIntermediate = intToScoreIntermediate2;
                        intToScore = intToScore2;
                    } else {
                        seeds.add(Integer.valueOf(key));
                        intToScoreIntermediate = intToScoreIntermediate;
                        break;
                    }
                }
            }
            Map<Integer, Double> intToScoreIntermediate3 = intToScoreIntermediate;
            List<Map.Entry<Integer, Double>> intToScore3 = intToScore;
            if (seeds.size() >= 4) {
                break;
            }
            i2--;
            intToScoreIntermediate = intToScoreIntermediate3;
            intToScore = intToScore3;
        }
        if (seeds.isEmpty()) {
            seeds.add(Integer.valueOf(GOOGLE_BLUE));
        }
        return seeds;
    }

    private static int wrapDegrees(int degrees) {
        if (degrees < 0) {
            return (degrees % 360) + 360;
        }
        if (degrees >= 360) {
            return degrees % 360;
        }
        return degrees;
    }

    public static double wrapDegreesDouble(double degrees) {
        if (degrees < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return (degrees % 360.0d) + 360.0d;
        }
        if (degrees >= 360.0d) {
            return degrees % 360.0d;
        }
        return degrees;
    }

    private static float hueDiff(float a, float b) {
        return 180.0f - Math.abs(Math.abs(a - b) - 180.0f);
    }

    private static final String stringForColor(int color) {
        Cam hct = Cam.fromInt(color);
        String h = 'H' + String.format("%04d", Integer.valueOf(Math.round(hct.getHue())));
        String c = 'C' + String.format("%04d", Integer.valueOf(Math.round(hct.getChroma())));
        String t = 'T' + String.format("%04d", Integer.valueOf(Math.round(CamUtils.lstarFromInt(color))));
        String hex = String.format("%-06s", Integer.toHexString(16777215 & color).toUpperCase(Locale.ROOT));
        return h + c + t + " = #" + hex;
    }

    public static String humanReadable(String paletteName, List<Integer> colors) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(paletteName).append('\n');
        Iterator<Integer> it = colors.iterator();
        while (it.hasNext()) {
            int color = it.next().intValue();
            buffer.append(stringForColor(color));
        }
        return buffer.toString();
    }

    private static double score(Cam cam, double proportion) {
        double chromaScore;
        double proportionScore = 70.0d * proportion;
        if (cam.getChroma() < 48.0f) {
            chromaScore = (cam.getChroma() - 48.0f) * 0.1d;
        } else {
            chromaScore = (cam.getChroma() - 48.0f) * 0.3d;
        }
        return chromaScore + proportionScore;
    }

    private static List<Double> huePopulations(Map<Integer, Cam> camByColor, Map<Integer, Double> populationByColor, boolean filter) {
        List<Double> huePopulation = new ArrayList<>(360);
        for (int i = 0; i < 360; i++) {
            huePopulation.add(Double.valueOf(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN));
        }
        for (Map.Entry<Integer, Double> entry : populationByColor.entrySet()) {
            double population = entry.getValue().doubleValue();
            Cam cam = camByColor.get(entry.getKey());
            int hue = Math.round(cam.getHue()) % 360;
            if (!filter || cam.getChroma() > 5.0f) {
                huePopulation.set(hue, Double.valueOf(huePopulation.get(hue).doubleValue() + population));
            }
        }
        return huePopulation;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<K, V>>() { // from class: com.samsung.android.wallpaper.colortheme.monet.ColorScheme.1
            @Override // java.util.Comparator
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return ((Comparable) e2.getValue()).compareTo(e1.getValue());
            }
        });
        return sortedEntries;
    }
}
