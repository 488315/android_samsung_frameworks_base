package android.graphics.fonts;

import android.util.ArraySet;
import dalvik.annotation.optimization.FastNative;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
public class FontFileUtil {
    private static final int ANALYZE_ERROR = -1;
    private static final int FVAR_TABLE_TAG = 1719034226;
    private static final int OS2_TABLE_TAG = 1330851634;
    private static final int SFNT_VERSION_1 = 65536;
    private static final int SFNT_VERSION_OTTO = 1330926671;
    private static final int TTC_TAG = 1953784678;

    public static boolean isSuccess(int i) {
        return i != -1;
    }

    @FastNative
    private static native String nGetFontPostScriptName(ByteBuffer byteBuffer, int i);

    @FastNative
    private static native long nGetFontRevision(ByteBuffer byteBuffer, int i);

    @FastNative
    private static native int nIsPostScriptType1Font(ByteBuffer byteBuffer, int i);

    private static int pack(int i, boolean z) {
        return i | (z ? 65536 : 0);
    }

    public static boolean unpackItalic(int i) {
        return (i & 65536) != 0;
    }

    public static int unpackWeight(int i) {
        return i & 65535;
    }

    private FontFileUtil() {
    }

    public static final int analyzeStyle(ByteBuffer byteBuffer, int i, FontVariationAxis[] fontVariationAxisArr) {
        int styleValue;
        char c;
        int i2;
        int i3;
        if (fontVariationAxisArr != null) {
            styleValue = -1;
            c = 65535;
            for (FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                if ("wght".equals(fontVariationAxis.getTag())) {
                    styleValue = (int) fontVariationAxis.getStyleValue();
                } else if ("ital".equals(fontVariationAxis.getTag())) {
                    c = fontVariationAxis.getStyleValue() == 1.0f ? (char) 1 : (char) 0;
                }
            }
        } else {
            styleValue = -1;
            c = 65535;
        }
        if (styleValue != -1 && c != 65535) {
            return pack(styleValue, c == 1);
        }
        ByteOrder byteOrderOrder = byteBuffer.order();
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        try {
            if (byteBuffer.getInt(0) != TTC_TAG) {
                i2 = 0;
            } else {
                if (i >= byteBuffer.getInt(8)) {
                    return -1;
                }
                i2 = byteBuffer.getInt((i * 4) + 12);
            }
            int i4 = byteBuffer.getInt(i2);
            if (i4 != 65536 && i4 != SFNT_VERSION_OTTO) {
                return -1;
            }
            short s = byteBuffer.getShort(i2 + 4);
            int i5 = 0;
            while (true) {
                if (i5 >= s) {
                    i3 = -1;
                    break;
                }
                int i6 = i2 + 12 + (i5 * 16);
                if (byteBuffer.getInt(i6) == OS2_TABLE_TAG) {
                    i3 = byteBuffer.getInt(i6 + 8);
                    break;
                }
                i5++;
            }
            if (i3 == -1) {
                return pack(400, false);
            }
            short s2 = byteBuffer.getShort(i3 + 4);
            boolean z = (byteBuffer.getShort(i3 + 62) & 1) != 0;
            if (styleValue == -1) {
                styleValue = s2;
            }
            if (c == 65535) {
                z = z;
            } else if (c != 1) {
                z = false;
            }
            return pack(styleValue, z);
        } finally {
            byteBuffer.order(byteOrderOrder);
        }
    }

    public static long getRevision(ByteBuffer byteBuffer, int i) {
        return nGetFontRevision(byteBuffer, i);
    }

    public static String getPostScriptName(ByteBuffer byteBuffer, int i) {
        return nGetFontPostScriptName(byteBuffer, i);
    }

    public static int isPostScriptType1Font(ByteBuffer byteBuffer, int i) {
        return nIsPostScriptType1Font(byteBuffer, i);
    }

    public static int isCollectionFont(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferSlice = byteBuffer.slice();
        byteBufferSlice.order(ByteOrder.BIG_ENDIAN);
        int i = byteBufferSlice.getInt(0);
        if (i == TTC_TAG) {
            return 1;
        }
        return (i == 65536 || i == SFNT_VERSION_OTTO) ? 0 : -1;
    }

    private static int getUInt16(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getShort(i) & 65535;
    }

    public static Set<Integer> getSupportedAxes(ByteBuffer byteBuffer, int i) {
        int i2;
        int i3;
        ByteOrder byteOrderOrder = byteBuffer.order();
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        try {
            if (byteBuffer.getInt(0) != TTC_TAG) {
                i2 = 0;
            } else {
                if (i >= byteBuffer.getInt(8)) {
                    return Collections.EMPTY_SET;
                }
                i2 = byteBuffer.getInt((i * 4) + 12);
            }
            int i4 = byteBuffer.getInt(i2);
            if (i4 != 65536 && i4 != SFNT_VERSION_OTTO) {
                return Collections.EMPTY_SET;
            }
            short s = byteBuffer.getShort(i2 + 4);
            int i5 = 0;
            while (true) {
                if (i5 >= s) {
                    i3 = -1;
                    break;
                }
                int i6 = i2 + 12 + (i5 * 16);
                if (byteBuffer.getInt(i6) == FVAR_TABLE_TAG) {
                    i3 = byteBuffer.getInt(i6 + 8);
                    break;
                }
                i5++;
            }
            if (i3 == -1) {
                return Collections.EMPTY_SET;
            }
            if (byteBuffer.getShort(i3) == 1 && byteBuffer.getShort(i3 + 2) == 0) {
                int uInt16 = getUInt16(byteBuffer, i3 + 4);
                int uInt162 = getUInt16(byteBuffer, i3 + 8);
                int uInt163 = getUInt16(byteBuffer, i3 + 10);
                ArraySet arraySet = new ArraySet();
                for (int i7 = 0; i7 < uInt162; i7++) {
                    arraySet.add(Integer.valueOf(byteBuffer.getInt(i3 + uInt16 + (uInt163 * i7))));
                }
                return arraySet;
            }
            return Collections.EMPTY_SET;
        } finally {
            byteBuffer.order(byteOrderOrder);
        }
    }
}
