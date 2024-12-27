package android.content.res;

import android.util.MathUtils;
import android.util.SparseArray;

public class FontScaleConverterFactory {
    private static final float SCALE_KEY_MULTIPLIER = 100.0f;
    private static float sMinScaleBeforeCurvesApplied;
    public static volatile SparseArray<FontScaleConverter> sLookupTables = new SparseArray<>();
    private static final Object LOOKUP_TABLES_WRITE_LOCK = new Object();

    static {
        sMinScaleBeforeCurvesApplied = 1.05f;
        synchronized (LOOKUP_TABLES_WRITE_LOCK) {
            putInto(
                    sLookupTables,
                    1.05f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                8.4f, 10.5f, 12.6f, 14.8f, 18.6f, 20.6f, 24.4f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.1f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                8.8f, 11.0f, 13.2f, 15.6f, 19.2f, 21.2f, 24.8f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.15f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.2f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                9.6f, 12.0f, 14.4f, 17.2f, 20.4f, 22.4f, 25.6f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.3f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.5f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    1.8f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f
                            }));
            putInto(
                    sLookupTables,
                    2.0f,
                    new FontScaleConverterImpl(
                            new float[] {
                                8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f
                            },
                            new float[] {
                                16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f
                            }));
        }
        sMinScaleBeforeCurvesApplied = getScaleFromKey(sLookupTables.keyAt(0)) - 0.01f;
        if (sMinScaleBeforeCurvesApplied <= 1.0f) {
            throw new IllegalStateException(
                    "You should only apply non-linear scaling to font scales > 1");
        }
    }

    private FontScaleConverterFactory() {}

    public static boolean isNonLinearFontScalingActive(float fontScale) {
        return fontScale >= sMinScaleBeforeCurvesApplied;
    }

    public static FontScaleConverter forScale(float fontScale) {
        if (!isNonLinearFontScalingActive(fontScale)) {
            return null;
        }
        FontScaleConverter lookupTable = get(fontScale);
        if (lookupTable != null) {
            return lookupTable;
        }
        int index = sLookupTables.indexOfKey(getKey(fontScale));
        if (index >= 0) {
            return sLookupTables.valueAt(index);
        }
        int lowerIndex = (-(index + 1)) - 1;
        int higherIndex = lowerIndex + 1;
        if (lowerIndex < 0 || higherIndex >= sLookupTables.size()) {
            FontScaleConverterImpl converter =
                    new FontScaleConverterImpl(new float[] {1.0f}, new float[] {fontScale});
            if (Flags.fontScaleConverterPublic()) {
                put(fontScale, converter);
            }
            return converter;
        }
        float startScale = getScaleFromKey(sLookupTables.keyAt(lowerIndex));
        float endScale = getScaleFromKey(sLookupTables.keyAt(higherIndex));
        float interpolationPoint =
                MathUtils.constrainedMap(0.0f, 1.0f, startScale, endScale, fontScale);
        FontScaleConverter converter2 =
                createInterpolatedTableBetween(
                        sLookupTables.valueAt(lowerIndex),
                        sLookupTables.valueAt(higherIndex),
                        interpolationPoint);
        if (Flags.fontScaleConverterPublic()) {
            put(fontScale, converter2);
        }
        return converter2;
    }

    private static FontScaleConverter createInterpolatedTableBetween(
            FontScaleConverter start, FontScaleConverter end, float interpolationPoint) {
        float[] commonSpSizes = {8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
        float[] dpInterpolated = new float[commonSpSizes.length];
        for (int i = 0; i < commonSpSizes.length; i++) {
            float sp = commonSpSizes[i];
            float startDp = start.convertSpToDp(sp);
            float endDp = end.convertSpToDp(sp);
            dpInterpolated[i] = MathUtils.lerp(startDp, endDp, interpolationPoint);
        }
        return new FontScaleConverterImpl(commonSpSizes, dpInterpolated);
    }

    private static int getKey(float fontScale) {
        return (int) (100.0f * fontScale);
    }

    private static float getScaleFromKey(int key) {
        return key / 100.0f;
    }

    private static void put(float scaleKey, FontScaleConverter fontScaleConverter) {
        synchronized (LOOKUP_TABLES_WRITE_LOCK) {
            SparseArray<FontScaleConverter> newTable = sLookupTables.m5234clone();
            putInto(newTable, scaleKey, fontScaleConverter);
            sLookupTables = newTable;
        }
    }

    private static void putInto(
            SparseArray<FontScaleConverter> table,
            float scaleKey,
            FontScaleConverter fontScaleConverter) {
        table.put(getKey(scaleKey), fontScaleConverter);
    }

    private static FontScaleConverter get(float scaleKey) {
        return sLookupTables.get(getKey(scaleKey));
    }
}
