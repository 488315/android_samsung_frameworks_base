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

    @FastNative
    private static native String nGetFontPostScriptName(ByteBuffer byteBuffer, int i);

    @FastNative
    private static native long nGetFontRevision(ByteBuffer byteBuffer, int i);

    @FastNative
    private static native int nIsPostScriptType1Font(ByteBuffer byteBuffer, int i);

    private FontFileUtil() {
    }

    public static int unpackWeight(int packed) {
        return 65535 & packed;
    }

    public static boolean unpackItalic(int packed) {
        return (65536 & packed) != 0;
    }

    public static boolean isSuccess(int packed) {
        return packed != -1;
    }

    private static int pack(int weight, boolean italic) {
        return (italic ? 65536 : 0) | weight;
    }

    public static final int analyzeStyle(ByteBuffer buffer, int ttcIndex, FontVariationAxis[] varSettings) {
        int italic;
        int italic2;
        int weight = -1;
        int italic3 = -1;
        if (varSettings != null) {
            for (FontVariationAxis axis : varSettings) {
                if ("wght".equals(axis.getTag())) {
                    weight = (int) axis.getStyleValue();
                } else if ("ital".equals(axis.getTag())) {
                    italic3 = axis.getStyleValue() == 1.0f ? 1 : 0;
                }
            }
            italic = italic3;
            italic2 = weight;
        } else {
            italic = -1;
            italic2 = -1;
        }
        if (italic2 != -1 && italic != -1) {
            return pack(italic2, italic == 1);
        }
        ByteOrder originalOrder = buffer.order();
        buffer.order(ByteOrder.BIG_ENDIAN);
        int fontFileOffset = 0;
        try {
            int magicNumber = buffer.getInt(0);
            if (magicNumber == TTC_TAG) {
                if (ttcIndex >= buffer.getInt(8)) {
                    return -1;
                }
                fontFileOffset = buffer.getInt((ttcIndex * 4) + 12);
            }
            int sfntVersion = buffer.getInt(fontFileOffset);
            if (sfntVersion != 65536 && sfntVersion != SFNT_VERSION_OTTO) {
                return -1;
            }
            int numTables = buffer.getShort(fontFileOffset + 4);
            int os2TableOffset = -1;
            int i = 0;
            while (true) {
                if (i >= numTables) {
                    break;
                }
                int tableOffset = fontFileOffset + 12 + (i * 16);
                if (buffer.getInt(tableOffset) == OS2_TABLE_TAG) {
                    os2TableOffset = buffer.getInt(tableOffset + 8);
                    break;
                }
                i++;
            }
            if (os2TableOffset == -1) {
                return pack(400, false);
            }
            boolean z = false;
            int weightFromOS2 = buffer.getShort(os2TableOffset + 4);
            boolean italicFromOS2 = (buffer.getShort(os2TableOffset + 62) & 1) != 0;
            int i2 = italic2 == -1 ? weightFromOS2 : italic2;
            if (italic == -1) {
                z = italicFromOS2;
            } else if (italic == 1) {
                z = true;
            }
            return pack(i2, z);
        } finally {
            buffer.order(originalOrder);
        }
    }

    public static long getRevision(ByteBuffer buffer, int index) {
        return nGetFontRevision(buffer, index);
    }

    public static String getPostScriptName(ByteBuffer buffer, int index) {
        return nGetFontPostScriptName(buffer, index);
    }

    public static int isPostScriptType1Font(ByteBuffer buffer, int index) {
        return nIsPostScriptType1Font(buffer, index);
    }

    public static int isCollectionFont(ByteBuffer buffer) {
        ByteBuffer copied = buffer.slice();
        copied.order(ByteOrder.BIG_ENDIAN);
        int magicNumber = copied.getInt(0);
        if (magicNumber == TTC_TAG) {
            return 1;
        }
        if (magicNumber == 65536 || magicNumber == SFNT_VERSION_OTTO) {
            return 0;
        }
        return -1;
    }

    private static int getUInt16(ByteBuffer buffer, int offset) {
        return buffer.getShort(offset) & 65535;
    }

    public static Set<Integer> getSupportedAxes(ByteBuffer buffer, int index) {
        ByteOrder originalOrder = buffer.order();
        buffer.order(ByteOrder.BIG_ENDIAN);
        int fontFileOffset = 0;
        try {
            int magicNumber = buffer.getInt(0);
            if (magicNumber == TTC_TAG) {
                if (index >= buffer.getInt(8)) {
                    return Collections.EMPTY_SET;
                }
                fontFileOffset = buffer.getInt((index * 4) + 12);
            }
            int sfntVersion = buffer.getInt(fontFileOffset);
            if (sfntVersion != 65536 && sfntVersion != SFNT_VERSION_OTTO) {
                return Collections.EMPTY_SET;
            }
            int numTables = buffer.getShort(fontFileOffset + 4);
            int fvarTableOffset = -1;
            int i = 0;
            while (true) {
                if (i >= numTables) {
                    break;
                }
                int tableOffset = fontFileOffset + 12 + (i * 16);
                if (buffer.getInt(tableOffset) == FVAR_TABLE_TAG) {
                    fvarTableOffset = buffer.getInt(tableOffset + 8);
                    break;
                }
                i++;
            }
            if (fvarTableOffset == -1) {
                return Collections.EMPTY_SET;
            }
            if (buffer.getShort(fvarTableOffset) == 1 && buffer.getShort(fvarTableOffset + 2) == 0) {
                int axesArrayOffset = getUInt16(buffer, fvarTableOffset + 4);
                int axisCount = getUInt16(buffer, fvarTableOffset + 8);
                int axisSize = getUInt16(buffer, fvarTableOffset + 10);
                ArraySet<Integer> axes = new ArraySet<>();
                for (int i2 = 0; i2 < axisCount; i2++) {
                    axes.add(Integer.valueOf(buffer.getInt(fvarTableOffset + axesArrayOffset + (axisSize * i2))));
                }
                return axes;
            }
            return Collections.EMPTY_SET;
        } finally {
            buffer.order(originalOrder);
        }
    }
}
