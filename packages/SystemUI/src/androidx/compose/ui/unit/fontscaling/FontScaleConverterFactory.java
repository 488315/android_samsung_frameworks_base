package androidx.compose.ui.unit.fontscaling;

import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.collection.internal.ContainerHelpersKt;
import androidx.compose.ui.unit.InlineClassHelperKt;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class FontScaleConverterFactory {
    public static final Object[] LookupTablesWriteLock;
    public static final FontScaleConverterFactory INSTANCE = new FontScaleConverterFactory();
    public static final float[] CommonFontSizes = {8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
    public static volatile SparseArrayCompat sLookupTables = new SparseArrayCompat(0, 1, null);

    static {
        Object[] objArr = new Object[0];
        LookupTablesWriteLock = objArr;
        synchronized (objArr) {
            sLookupTables.put((int) 115.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f}));
            sLookupTables.put((int) 130.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f}));
            sLookupTables.put((int) 150.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f}));
            sLookupTables.put((int) 180.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f}));
            sLookupTables.put((int) 200.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f}));
            Unit unit = Unit.INSTANCE;
        }
        if ((sLookupTables.keyAt(0) / 100.0f) - 0.01f > 1.03f) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("You should only apply non-linear scaling to font scales > 1");
    }

    private FontScaleConverterFactory() {
    }

    public static FontScaleConverter forScale(float f) {
        float fKeyAt;
        FontScaleConverter fontScaleConverterTable;
        if (f < 1.03f) {
            return null;
        }
        INSTANCE.getClass();
        int i = (int) (f * 100.0f);
        FontScaleConverter fontScaleConverter = (FontScaleConverter) sLookupTables.get(i);
        if (fontScaleConverter != null) {
            return fontScaleConverter;
        }
        SparseArrayCompat sparseArrayCompat = sLookupTables;
        if (sparseArrayCompat.garbage) {
            SparseArrayCompatKt.access$gc(sparseArrayCompat);
        }
        int iBinarySearch = ContainerHelpersKt.binarySearch(sparseArrayCompat.size, i, sparseArrayCompat.keys);
        if (iBinarySearch >= 0) {
            return (FontScaleConverter) sLookupTables.valueAt(iBinarySearch);
        }
        int i2 = -(iBinarySearch + 1);
        int i3 = i2 - 1;
        if (i2 >= sLookupTables.size()) {
            FontScaleConverterTable fontScaleConverterTable2 = new FontScaleConverterTable(new float[]{1.0f}, new float[]{f});
            put(f, fontScaleConverterTable2);
            return fontScaleConverterTable2;
        }
        if (i3 < 0) {
            float[] fArr = CommonFontSizes;
            fontScaleConverterTable = new FontScaleConverterTable(fArr, fArr);
            fKeyAt = 1.0f;
        } else {
            fKeyAt = sLookupTables.keyAt(i3) / 100.0f;
            fontScaleConverterTable = (FontScaleConverter) sLookupTables.valueAt(i3);
        }
        float fKeyAt2 = sLookupTables.keyAt(i2) / 100.0f;
        MathUtils.INSTANCE.getClass();
        float fMax = (Math.max(0.0f, Math.min(1.0f, fKeyAt == fKeyAt2 ? 0.0f : (f - fKeyAt) / (fKeyAt2 - fKeyAt))) * 1.0f) + 0.0f;
        FontScaleConverter fontScaleConverter2 = (FontScaleConverter) sLookupTables.valueAt(i2);
        float[] fArr2 = CommonFontSizes;
        float[] fArr3 = new float[fArr2.length];
        int length = fArr2.length;
        for (int i4 = 0; i4 < length; i4++) {
            float f2 = fArr2[i4];
            float fConvertSpToDp = fontScaleConverterTable.convertSpToDp(f2);
            float fConvertSpToDp2 = fontScaleConverter2.convertSpToDp(f2);
            MathUtils.INSTANCE.getClass();
            fArr3[i4] = ((fConvertSpToDp2 - fConvertSpToDp) * fMax) + fConvertSpToDp;
        }
        FontScaleConverterTable fontScaleConverterTable3 = new FontScaleConverterTable(fArr2, fArr3);
        put(f, fontScaleConverterTable3);
        return fontScaleConverterTable3;
    }

    public static void put(float f, FontScaleConverterTable fontScaleConverterTable) {
        synchronized (LookupTablesWriteLock) {
            SparseArrayCompat sparseArrayCompatM2clone = sLookupTables.m2clone();
            INSTANCE.getClass();
            sparseArrayCompatM2clone.put((int) (f * 100.0f), fontScaleConverterTable);
            sLookupTables = sparseArrayCompatM2clone;
            Unit unit = Unit.INSTANCE;
        }
    }
}
