package android.content.res;

import android.util.MathUtils;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class FontScaleConverterFactory {
    private static final Object LOOKUP_TABLES_WRITE_LOCK;
    private static final float SCALE_KEY_MULTIPLIER = 100.0f;
    public static volatile SparseArray<FontScaleConverter> sLookupTables = new SparseArray<>();
    private static float sMinScaleBeforeCurvesApplied;

    private static int getKey(float f) {
        return (int) (f * 100.0f);
    }

    private static float getScaleFromKey(int i) {
        return i / 100.0f;
    }

    static {
        Object obj = new Object();
        LOOKUP_TABLES_WRITE_LOCK = obj;
        sMinScaleBeforeCurvesApplied = 1.05f;
        synchronized (obj) {
            putInto(sLookupTables, 1.05f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{8.4f, 10.5f, 12.6f, 14.8f, 18.6f, 20.6f, 24.4f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.1f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{8.8f, 11.0f, 13.2f, 15.6f, 19.2f, 21.2f, 24.8f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.15f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.2f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.6f, 12.0f, 14.4f, 17.2f, 20.4f, 22.4f, 25.6f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.3f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.5f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f}));
            putInto(sLookupTables, 1.8f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f}));
            putInto(sLookupTables, 2.0f, new FontScaleConverterImpl(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f}));
        }
        float scaleFromKey = getScaleFromKey(sLookupTables.keyAt(0)) - 0.01f;
        sMinScaleBeforeCurvesApplied = scaleFromKey;
        if (scaleFromKey <= 1.0f) {
            throw new IllegalStateException("You should only apply non-linear scaling to font scales > 1");
        }
    }

    private FontScaleConverterFactory() {
    }

    public static boolean isNonLinearFontScalingActive(float f) {
        return f >= sMinScaleBeforeCurvesApplied;
    }

    public static FontScaleConverter forScale(float f) {
        if (!isNonLinearFontScalingActive(f)) {
            return null;
        }
        FontScaleConverter fontScaleConverter = get(f);
        if (fontScaleConverter != null) {
            return fontScaleConverter;
        }
        int indexOfKey = sLookupTables.indexOfKey(getKey(f));
        if (indexOfKey >= 0) {
            return sLookupTables.valueAt(indexOfKey);
        }
        int i = -(indexOfKey + 1);
        int i2 = i - 1;
        if (i2 < 0 || i >= sLookupTables.size()) {
            FontScaleConverterImpl fontScaleConverterImpl = new FontScaleConverterImpl(new float[]{1.0f}, new float[]{f});
            if (Flags.fontScaleConverterPublic()) {
                put(f, fontScaleConverterImpl);
            }
            return fontScaleConverterImpl;
        }
        FontScaleConverter createInterpolatedTableBetween = createInterpolatedTableBetween(sLookupTables.valueAt(i2), sLookupTables.valueAt(i), MathUtils.constrainedMap(0.0f, 1.0f, getScaleFromKey(sLookupTables.keyAt(i2)), getScaleFromKey(sLookupTables.keyAt(i)), f));
        if (Flags.fontScaleConverterPublic()) {
            put(f, createInterpolatedTableBetween);
        }
        return createInterpolatedTableBetween;
    }

    private static FontScaleConverter createInterpolatedTableBetween(FontScaleConverter fontScaleConverter, FontScaleConverter fontScaleConverter2, float f) {
        float[] fArr = {8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
        float[] fArr2 = new float[9];
        for (int i = 0; i < 9; i++) {
            float f2 = fArr[i];
            fArr2[i] = MathUtils.lerp(fontScaleConverter.convertSpToDp(f2), fontScaleConverter2.convertSpToDp(f2), f);
        }
        return new FontScaleConverterImpl(fArr, fArr2);
    }

    private static void put(float f, FontScaleConverter fontScaleConverter) {
        synchronized (LOOKUP_TABLES_WRITE_LOCK) {
            SparseArray<FontScaleConverter> m5529clone = sLookupTables.m5529clone();
            putInto(m5529clone, f, fontScaleConverter);
            sLookupTables = m5529clone;
        }
    }

    private static void putInto(SparseArray<FontScaleConverter> sparseArray, float f, FontScaleConverter fontScaleConverter) {
        sparseArray.put(getKey(f), fontScaleConverter);
    }

    private static FontScaleConverter get(float f) {
        return sLookupTables.get(getKey(f));
    }
}
