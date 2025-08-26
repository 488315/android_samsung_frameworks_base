package com.android.internal.colorextraction.types;

import android.app.WallpaperColors;
import android.app.blob.XmlTags;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.Log;
import android.util.MathUtils;
import android.util.Range;
import com.android.internal.R;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class Tonal implements ExtractionType {
    private static final boolean DEBUG = true;
    private static final float FIT_WEIGHT_H = 1.0f;
    private static final float FIT_WEIGHT_L = 10.0f;
    private static final float FIT_WEIGHT_S = 1.0f;
    public static final int MAIN_COLOR_DARK = -14671580;
    public static final int MAIN_COLOR_LIGHT = -2433824;
    public static final int MAIN_COLOR_REGULAR = -16777216;
    private static final String TAG = "Tonal";
    private final Context mContext;
    private final TonalPalette mGreyPalette;
    private float[] mTmpHSL = new float[3];
    private final ArrayList<TonalPalette> mTonalPalettes;

    public Tonal(Context context) {
        ArrayList<TonalPalette> tonalPalettes = new ConfigParser(context).getTonalPalettes();
        this.mTonalPalettes = tonalPalettes;
        this.mContext = context;
        this.mGreyPalette = tonalPalettes.get(0);
        tonalPalettes.remove(0);
    }

    @Override // com.android.internal.colorextraction.types.ExtractionType
    public void extractInto(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        if (runTonalExtraction(wallpaperColors, gradientColors, gradientColors2, gradientColors3)) {
            return;
        }
        applyFallback(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
    }

    private boolean runTonalExtraction(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        int iMin;
        if (wallpaperColors == null) {
            return false;
        }
        List<Color> mainColors = wallpaperColors.getMainColors();
        int size = mainColors.size();
        boolean z = (wallpaperColors.getColorHints() & 1) != 0;
        if (size == 0) {
            return false;
        }
        int argb = mainColors.get(0).toArgb();
        float[] fArr = {f, 0.0f, 0.0f};
        ColorUtils.RGBToHSL(Color.red(argb), Color.green(argb), Color.blue(argb), fArr);
        float f = fArr[0] / 360.0f;
        TonalPalette tonalPaletteFindTonalPalette = findTonalPalette(f, fArr[1]);
        if (tonalPaletteFindTonalPalette == null) {
            Log.w(TAG, "Could not find a tonal palette!");
            return false;
        }
        int length = 2;
        int iBestFit = bestFit(tonalPaletteFindTonalPalette, fArr[0], fArr[1], fArr[2]);
        if (iBestFit == -1) {
            Log.w(TAG, "Could not find best fit!");
            return false;
        }
        float[] fArrFit = fit(tonalPaletteFindTonalPalette.h, fArr[0], iBestFit, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        float[] fArrFit2 = fit(tonalPaletteFindTonalPalette.s, fArr[1], iBestFit, 0.0f, 1.0f);
        float[] fArrFit3 = fit(tonalPaletteFindTonalPalette.l, fArr[2], iBestFit, 0.0f, 1.0f);
        int[] colorPalette = getColorPalette(fArrFit, fArrFit2, fArrFit3);
        StringBuilder sb = new StringBuilder("Tonal Palette - index: " + iBestFit + ". Main color: " + Integer.toHexString(getColorInt(iBestFit, fArrFit, fArrFit2, fArrFit3)) + "\nColors: ");
        for (int i = 0; i < fArrFit.length; i++) {
            sb.append(Integer.toHexString(getColorInt(i, fArrFit, fArrFit2, fArrFit3)));
            if (i < fArrFit.length - 1) {
                sb.append(", ");
            }
        }
        Log.d(TAG, sb.toString());
        int colorInt = getColorInt(iBestFit, fArrFit, fArrFit2, fArrFit3);
        ColorUtils.colorToHSL(colorInt, this.mTmpHSL);
        float[] fArr2 = this.mTmpHSL;
        float f2 = fArr2[2];
        ColorUtils.colorToHSL(MAIN_COLOR_LIGHT, fArr2);
        float[] fArr3 = this.mTmpHSL;
        if (f2 > fArr3[2]) {
            return false;
        }
        ColorUtils.colorToHSL(MAIN_COLOR_DARK, fArr3);
        if (f2 < this.mTmpHSL[2]) {
            return false;
        }
        gradientColors.setMainColor(colorInt);
        gradientColors.setSecondaryColor(colorInt);
        gradientColors.setColorPalette(colorPalette);
        if (z) {
            iMin = fArrFit.length - 1;
        } else {
            iMin = iBestFit < 2 ? 0 : Math.min(iBestFit, 3);
        }
        int colorInt2 = getColorInt(iMin, fArrFit, fArrFit2, fArrFit3);
        gradientColors2.setMainColor(colorInt2);
        gradientColors2.setSecondaryColor(colorInt2);
        gradientColors2.setColorPalette(colorPalette);
        if (z) {
            length = fArrFit.length - 1;
        } else if (iBestFit < 2) {
            length = 0;
        }
        int colorInt3 = getColorInt(length, fArrFit, fArrFit2, fArrFit3);
        gradientColors3.setMainColor(colorInt3);
        gradientColors3.setSecondaryColor(colorInt3);
        gradientColors3.setColorPalette(colorPalette);
        gradientColors.setSupportsDarkText(z);
        gradientColors2.setSupportsDarkText(z);
        gradientColors3.setSupportsDarkText(z);
        Log.d(TAG, "Gradients: \n\tNormal " + gradientColors + "\n\tDark " + gradientColors2 + "\n\tExtra dark: " + gradientColors3);
        return true;
    }

    private void applyFallback(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors, ColorExtractor.GradientColors gradientColors2, ColorExtractor.GradientColors gradientColors3) {
        applyFallback(wallpaperColors, gradientColors);
        applyFallback(wallpaperColors, gradientColors2);
        applyFallback(wallpaperColors, gradientColors3);
    }

    public void applyFallback(WallpaperColors wallpaperColors, ColorExtractor.GradientColors gradientColors) {
        boolean z = (wallpaperColors == null || (wallpaperColors.getColorHints() & 1) == 0) ? false : true;
        int i = z ? MAIN_COLOR_LIGHT : ((wallpaperColors != null && (wallpaperColors.getColorHints() & 2) != 0) || ((this.mContext.getResources().getConfiguration().uiMode & 48) == 32)) ? MAIN_COLOR_DARK : -16777216;
        float[] fArr = new float[3];
        ColorUtils.colorToHSL(i, fArr);
        gradientColors.setMainColor(i);
        gradientColors.setSecondaryColor(i);
        gradientColors.setSupportsDarkText(z);
        gradientColors.setColorPalette(getColorPalette(findTonalPalette(fArr[0], fArr[1])));
    }

    private int getColorInt(int i, float[] fArr, float[] fArr2, float[] fArr3) {
        this.mTmpHSL[0] = fract(fArr[i]) * 360.0f;
        float[] fArr4 = this.mTmpHSL;
        fArr4[1] = fArr2[i];
        fArr4[2] = fArr3[i];
        return ColorUtils.HSLToColor(fArr4);
    }

    private int[] getColorPalette(float[] fArr, float[] fArr2, float[] fArr3) {
        int length = fArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = getColorInt(i, fArr, fArr2, fArr3);
        }
        return iArr;
    }

    private int[] getColorPalette(TonalPalette tonalPalette) {
        return getColorPalette(tonalPalette.h, tonalPalette.s, tonalPalette.l);
    }

    private static float[] fit(float[] fArr, float f, int i, float f2, float f3) {
        float[] fArr2 = new float[fArr.length];
        float f4 = f - fArr[i];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr2[i2] = MathUtils.constrain(fArr[i2] + f4, f2, f3);
        }
        return fArr2;
    }

    private static int bestFit(TonalPalette tonalPalette, float f, float f2, float f3) {
        int i = -1;
        float f4 = Float.POSITIVE_INFINITY;
        for (int i2 = 0; i2 < tonalPalette.h.length; i2++) {
            float fAbs = (Math.abs(f - tonalPalette.h[i2]) * 1.0f) + (Math.abs(f2 - tonalPalette.s[i2]) * 1.0f) + (Math.abs(f3 - tonalPalette.l[i2]) * FIT_WEIGHT_L);
            if (fAbs < f4) {
                i = i2;
                f4 = fAbs;
            }
        }
        return i;
    }

    private TonalPalette findTonalPalette(float f, float f2) {
        float fFract;
        float fFract2;
        float f3;
        if (f2 < 0.05f) {
            return this.mGreyPalette;
        }
        int size = this.mTonalPalettes.size();
        TonalPalette tonalPalette = null;
        float f4 = Float.POSITIVE_INFINITY;
        for (int i = 0; i < size; i++) {
            TonalPalette tonalPalette2 = this.mTonalPalettes.get(i);
            if ((f >= tonalPalette2.minHue && f <= tonalPalette2.maxHue) || ((tonalPalette2.maxHue > 1.0f && f >= 0.0f && f <= fract(tonalPalette2.maxHue)) || (tonalPalette2.minHue < 0.0f && f >= fract(tonalPalette2.minHue) && f <= 1.0f))) {
                return tonalPalette2;
            }
            if (f > tonalPalette2.minHue || tonalPalette2.minHue - f >= f4) {
                if (f >= tonalPalette2.maxHue && f - tonalPalette2.maxHue < f4) {
                    fFract2 = tonalPalette2.maxHue;
                } else if (tonalPalette2.maxHue > 1.0f && f >= fract(tonalPalette2.maxHue) && f - fract(tonalPalette2.maxHue) < f4) {
                    fFract2 = fract(tonalPalette2.maxHue);
                } else if (tonalPalette2.minHue < 0.0f && f <= fract(tonalPalette2.minHue) && fract(tonalPalette2.minHue) - f < f4) {
                    fFract = fract(tonalPalette2.minHue);
                }
                f3 = f - fFract2;
                f4 = f3;
                tonalPalette = tonalPalette2;
            } else {
                fFract = tonalPalette2.minHue;
            }
            f3 = fFract - f;
            f4 = f3;
            tonalPalette = tonalPalette2;
        }
        return tonalPalette;
    }

    private static float fract(float f) {
        return f - ((float) Math.floor(f));
    }

    public static class TonalPalette {
        public final float[] h;
        public final float[] l;
        public final float maxHue;
        public final float minHue;
        public final float[] s;

        TonalPalette(float[] fArr, float[] fArr2, float[] fArr3) {
            if (fArr.length != fArr2.length || fArr2.length != fArr3.length) {
                throw new IllegalArgumentException("All arrays should have the same size. h: " + Arrays.toString(fArr) + " s: " + Arrays.toString(fArr2) + " l: " + Arrays.toString(fArr3));
            }
            this.h = fArr;
            this.s = fArr2;
            this.l = fArr3;
            float fMin = Float.POSITIVE_INFINITY;
            float fMax = Float.NEGATIVE_INFINITY;
            for (float f : fArr) {
                fMin = Math.min(f, fMin);
                fMax = Math.max(f, fMax);
            }
            this.minHue = fMin;
            this.maxHue = fMax;
        }
    }

    public static class ColorRange {
        private Range<Float> mHue;
        private Range<Float> mLightness;
        private Range<Float> mSaturation;

        public ColorRange(Range<Float> range, Range<Float> range2, Range<Float> range3) {
            this.mHue = range;
            this.mSaturation = range2;
            this.mLightness = range3;
        }

        public boolean containsColor(float f, float f2, float f3) {
            return this.mHue.contains((Range<Float>) Float.valueOf(f)) && this.mSaturation.contains((Range<Float>) Float.valueOf(f2)) && this.mLightness.contains((Range<Float>) Float.valueOf(f3));
        }

        public float[] getCenter() {
            return new float[]{((Float) this.mHue.getLower()).floatValue() + ((((Float) this.mHue.getUpper()).floatValue() - ((Float) this.mHue.getLower()).floatValue()) / 2.0f), ((Float) this.mSaturation.getLower()).floatValue() + ((((Float) this.mSaturation.getUpper()).floatValue() - ((Float) this.mSaturation.getLower()).floatValue()) / 2.0f), ((Float) this.mLightness.getLower()).floatValue() + ((((Float) this.mLightness.getUpper()).floatValue() - ((Float) this.mLightness.getLower()).floatValue()) / 2.0f)};
        }

        public String toString() {
            return String.format("H: %s, S: %s, L %s", this.mHue, this.mSaturation, this.mLightness);
        }
    }

    public static class ConfigParser {
        private final ArrayList<TonalPalette> mTonalPalettes = new ArrayList<>();

        public ConfigParser(Context context) throws XmlPullParserException, IOException {
            try {
                XmlResourceParser xml = context.getResources().getXml(R.xml.color_extraction);
                for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                    if (eventType != 0 && eventType != 3) {
                        if (eventType == 2) {
                            if (xml.getName().equals("palettes")) {
                                parsePalettes(xml);
                            }
                        } else {
                            throw new XmlPullParserException("Invalid XML event " + eventType + " - " + xml.getName(), xml, null);
                        }
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                throw new RuntimeException(e);
            }
        }

        public ArrayList<TonalPalette> getTonalPalettes() {
            return this.mTonalPalettes;
        }

        private ColorRange readRange(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            xmlPullParser.require(2, null, "range");
            float[] floatArray = readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] floatArray2 = readFloatArray(xmlPullParser.getAttributeValue(null, XmlTags.TAG_SESSION));
            float[] floatArray3 = readFloatArray(xmlPullParser.getAttributeValue(null, XmlTags.TAG_LEASEE));
            if (floatArray == null || floatArray2 == null || floatArray3 == null) {
                throw new XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
            }
            return new ColorRange(new Range(Float.valueOf(floatArray[0]), Float.valueOf(floatArray[1])), new Range(Float.valueOf(floatArray2[0]), Float.valueOf(floatArray2[1])), new Range(Float.valueOf(floatArray3[0]), Float.valueOf(floatArray3[1])));
        }

        private void parsePalettes(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            xmlPullParser.require(2, null, "palettes");
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("palette")) {
                        this.mTonalPalettes.add(readPalette(xmlPullParser));
                        xmlPullParser.next();
                    } else {
                        throw new XmlPullParserException("Invalid tag: " + name);
                    }
                }
            }
        }

        private TonalPalette readPalette(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            xmlPullParser.require(2, null, "palette");
            float[] floatArray = readFloatArray(xmlPullParser.getAttributeValue(null, "h"));
            float[] floatArray2 = readFloatArray(xmlPullParser.getAttributeValue(null, XmlTags.TAG_SESSION));
            float[] floatArray3 = readFloatArray(xmlPullParser.getAttributeValue(null, XmlTags.TAG_LEASEE));
            if (floatArray == null || floatArray2 == null || floatArray3 == null) {
                throw new XmlPullParserException("Incomplete range tag.", xmlPullParser, null);
            }
            return new TonalPalette(floatArray, floatArray2, floatArray3);
        }

        private float[] readFloatArray(String str) throws XmlPullParserException, IOException {
            String[] strArrSplit = str.replaceAll(" ", "").replaceAll(ShaderAssembler.NEWLINE, "").split(",");
            float[] fArr = new float[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                fArr[i] = Float.parseFloat(strArrSplit[i]);
            }
            return fArr;
        }
    }
}
