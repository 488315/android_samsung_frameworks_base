package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Element extends BaseObj {
    int[] mArraySizes;
    String[] mElementNames;
    Element[] mElements;
    DataKind mKind;
    boolean mNormalized;
    int[] mOffsetInBytes;
    int mSize;
    DataType mType;
    int mVectorSize;
    int[] mVisibleElementMap;

    private void updateVisibleSubElements() {
        if (this.mElements == null) {
            return;
        }
        int length = this.mElementNames.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.mElementNames[i2].charAt(0) != '#') {
                i++;
            }
        }
        this.mVisibleElementMap = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (this.mElementNames[i4].charAt(0) != '#') {
                this.mVisibleElementMap[i3] = i4;
                i3++;
            }
        }
    }

    public int getBytesSize() {
        return this.mSize;
    }

    public int getVectorSize() {
        return this.mVectorSize;
    }

    public enum DataType {
        NONE(0, 0),
        FLOAT_16(1, 2),
        FLOAT_32(2, 4),
        FLOAT_64(3, 8),
        SIGNED_8(4, 1),
        SIGNED_16(5, 2),
        SIGNED_32(6, 4),
        SIGNED_64(7, 8),
        UNSIGNED_8(8, 1),
        UNSIGNED_16(9, 2),
        UNSIGNED_32(10, 4),
        UNSIGNED_64(11, 8),
        BOOLEAN(12, 1),
        UNSIGNED_5_6_5(13, 2),
        UNSIGNED_5_5_5_1(14, 2),
        UNSIGNED_4_4_4_4(15, 2),
        MATRIX_4X4(16, 64),
        MATRIX_3X3(17, 36),
        MATRIX_2X2(18, 16),
        RS_ELEMENT(1000),
        RS_TYPE(1001),
        RS_ALLOCATION(1002),
        RS_SAMPLER(1003),
        RS_SCRIPT(1004),
        RS_MESH(1005),
        RS_PROGRAM_FRAGMENT(1006),
        RS_PROGRAM_VERTEX(1007),
        RS_PROGRAM_RASTER(1008),
        RS_PROGRAM_STORE(1009),
        RS_FONT(1010);

        int mID;
        int mSize;

        DataType(int i, int i2) {
            this.mID = i;
            this.mSize = i2;
        }

        DataType(int i) {
            this.mID = i;
            this.mSize = 4;
            if (RenderScript.sPointerSize == 8) {
                this.mSize = 32;
            }
        }
    }

    public enum DataKind {
        USER(0),
        PIXEL_L(7),
        PIXEL_A(8),
        PIXEL_LA(9),
        PIXEL_RGB(10),
        PIXEL_RGBA(11),
        PIXEL_DEPTH(12),
        PIXEL_YUV(13);

        int mID;

        DataKind(int i) {
            this.mID = i;
        }
    }

    public boolean isComplex() {
        if (this.mElements == null) {
            return false;
        }
        int i = 0;
        while (true) {
            Element[] elementArr = this.mElements;
            if (i >= elementArr.length) {
                return false;
            }
            if (elementArr[i].mElements != null) {
                return true;
            }
            i++;
        }
    }

    public int getSubElementCount() {
        int[] iArr = this.mVisibleElementMap;
        if (iArr == null) {
            return 0;
        }
        return iArr.length;
    }

    public Element getSubElement(int i) {
        int[] iArr = this.mVisibleElementMap;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= iArr.length) {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mElements[iArr[i]];
    }

    public String getSubElementName(int i) {
        int[] iArr = this.mVisibleElementMap;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= iArr.length) {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mElementNames[iArr[i]];
    }

    public int getSubElementArraySize(int i) {
        int[] iArr = this.mVisibleElementMap;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= iArr.length) {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mArraySizes[iArr[i]];
    }

    public int getSubElementOffsetBytes(int i) {
        int[] iArr = this.mVisibleElementMap;
        if (iArr == null) {
            throw new RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= iArr.length) {
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mOffsetInBytes[iArr[i]];
    }

    public DataType getDataType() {
        return this.mType;
    }

    public DataKind getDataKind() {
        return this.mKind;
    }

    public static Element BOOLEAN(RenderScript renderScript) {
        if (renderScript.mElement_BOOLEAN == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_BOOLEAN == null) {
                    renderScript.mElement_BOOLEAN = createUser(renderScript, DataType.BOOLEAN);
                }
            }
        }
        return renderScript.mElement_BOOLEAN;
    }

    public static Element U8(RenderScript renderScript) {
        if (renderScript.mElement_U8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U8 == null) {
                    renderScript.mElement_U8 = createUser(renderScript, DataType.UNSIGNED_8);
                }
            }
        }
        return renderScript.mElement_U8;
    }

    public static Element I8(RenderScript renderScript) {
        if (renderScript.mElement_I8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I8 == null) {
                    renderScript.mElement_I8 = createUser(renderScript, DataType.SIGNED_8);
                }
            }
        }
        return renderScript.mElement_I8;
    }

    public static Element U16(RenderScript renderScript) {
        if (renderScript.mElement_U16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U16 == null) {
                    renderScript.mElement_U16 = createUser(renderScript, DataType.UNSIGNED_16);
                }
            }
        }
        return renderScript.mElement_U16;
    }

    public static Element I16(RenderScript renderScript) {
        if (renderScript.mElement_I16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I16 == null) {
                    renderScript.mElement_I16 = createUser(renderScript, DataType.SIGNED_16);
                }
            }
        }
        return renderScript.mElement_I16;
    }

    public static Element U32(RenderScript renderScript) {
        if (renderScript.mElement_U32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U32 == null) {
                    renderScript.mElement_U32 = createUser(renderScript, DataType.UNSIGNED_32);
                }
            }
        }
        return renderScript.mElement_U32;
    }

    public static Element I32(RenderScript renderScript) {
        if (renderScript.mElement_I32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I32 == null) {
                    renderScript.mElement_I32 = createUser(renderScript, DataType.SIGNED_32);
                }
            }
        }
        return renderScript.mElement_I32;
    }

    public static Element U64(RenderScript renderScript) {
        if (renderScript.mElement_U64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U64 == null) {
                    renderScript.mElement_U64 = createUser(renderScript, DataType.UNSIGNED_64);
                }
            }
        }
        return renderScript.mElement_U64;
    }

    public static Element I64(RenderScript renderScript) {
        if (renderScript.mElement_I64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I64 == null) {
                    renderScript.mElement_I64 = createUser(renderScript, DataType.SIGNED_64);
                }
            }
        }
        return renderScript.mElement_I64;
    }

    public static Element F16(RenderScript renderScript) {
        if (renderScript.mElement_F16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F16 == null) {
                    renderScript.mElement_F16 = createUser(renderScript, DataType.FLOAT_16);
                }
            }
        }
        return renderScript.mElement_F16;
    }

    public static Element F32(RenderScript renderScript) {
        if (renderScript.mElement_F32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F32 == null) {
                    renderScript.mElement_F32 = createUser(renderScript, DataType.FLOAT_32);
                }
            }
        }
        return renderScript.mElement_F32;
    }

    public static Element F64(RenderScript renderScript) {
        if (renderScript.mElement_F64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F64 == null) {
                    renderScript.mElement_F64 = createUser(renderScript, DataType.FLOAT_64);
                }
            }
        }
        return renderScript.mElement_F64;
    }

    public static Element ELEMENT(RenderScript renderScript) {
        if (renderScript.mElement_ELEMENT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ELEMENT == null) {
                    renderScript.mElement_ELEMENT = createUser(renderScript, DataType.RS_ELEMENT);
                }
            }
        }
        return renderScript.mElement_ELEMENT;
    }

    public static Element TYPE(RenderScript renderScript) {
        if (renderScript.mElement_TYPE == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_TYPE == null) {
                    renderScript.mElement_TYPE = createUser(renderScript, DataType.RS_TYPE);
                }
            }
        }
        return renderScript.mElement_TYPE;
    }

    public static Element ALLOCATION(RenderScript renderScript) {
        if (renderScript.mElement_ALLOCATION == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ALLOCATION == null) {
                    renderScript.mElement_ALLOCATION = createUser(renderScript, DataType.RS_ALLOCATION);
                }
            }
        }
        return renderScript.mElement_ALLOCATION;
    }

    public static Element SAMPLER(RenderScript renderScript) {
        if (renderScript.mElement_SAMPLER == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SAMPLER == null) {
                    renderScript.mElement_SAMPLER = createUser(renderScript, DataType.RS_SAMPLER);
                }
            }
        }
        return renderScript.mElement_SAMPLER;
    }

    public static Element SCRIPT(RenderScript renderScript) {
        if (renderScript.mElement_SCRIPT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SCRIPT == null) {
                    renderScript.mElement_SCRIPT = createUser(renderScript, DataType.RS_SCRIPT);
                }
            }
        }
        return renderScript.mElement_SCRIPT;
    }

    public static Element MESH(RenderScript renderScript) {
        if (renderScript.mElement_MESH == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MESH == null) {
                    renderScript.mElement_MESH = createUser(renderScript, DataType.RS_MESH);
                }
            }
        }
        return renderScript.mElement_MESH;
    }

    public static Element PROGRAM_FRAGMENT(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_FRAGMENT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_FRAGMENT == null) {
                    renderScript.mElement_PROGRAM_FRAGMENT = createUser(renderScript, DataType.RS_PROGRAM_FRAGMENT);
                }
            }
        }
        return renderScript.mElement_PROGRAM_FRAGMENT;
    }

    public static Element PROGRAM_VERTEX(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_VERTEX == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_VERTEX == null) {
                    renderScript.mElement_PROGRAM_VERTEX = createUser(renderScript, DataType.RS_PROGRAM_VERTEX);
                }
            }
        }
        return renderScript.mElement_PROGRAM_VERTEX;
    }

    public static Element PROGRAM_RASTER(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_RASTER == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_RASTER == null) {
                    renderScript.mElement_PROGRAM_RASTER = createUser(renderScript, DataType.RS_PROGRAM_RASTER);
                }
            }
        }
        return renderScript.mElement_PROGRAM_RASTER;
    }

    public static Element PROGRAM_STORE(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_STORE == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_STORE == null) {
                    renderScript.mElement_PROGRAM_STORE = createUser(renderScript, DataType.RS_PROGRAM_STORE);
                }
            }
        }
        return renderScript.mElement_PROGRAM_STORE;
    }

    public static Element FONT(RenderScript renderScript) {
        if (renderScript.mElement_FONT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FONT == null) {
                    renderScript.mElement_FONT = createUser(renderScript, DataType.RS_FONT);
                }
            }
        }
        return renderScript.mElement_FONT;
    }

    public static Element A_8(RenderScript renderScript) {
        if (renderScript.mElement_A_8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_A_8 == null) {
                    renderScript.mElement_A_8 = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_A);
                }
            }
        }
        return renderScript.mElement_A_8;
    }

    public static Element RGB_565(RenderScript renderScript) {
        if (renderScript.mElement_RGB_565 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGB_565 == null) {
                    renderScript.mElement_RGB_565 = createPixel(renderScript, DataType.UNSIGNED_5_6_5, DataKind.PIXEL_RGB);
                }
            }
        }
        return renderScript.mElement_RGB_565;
    }

    public static Element RGB_888(RenderScript renderScript) {
        if (renderScript.mElement_RGB_888 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGB_888 == null) {
                    renderScript.mElement_RGB_888 = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGB);
                }
            }
        }
        return renderScript.mElement_RGB_888;
    }

    public static Element RGBA_5551(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_5551 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_5551 == null) {
                    renderScript.mElement_RGBA_5551 = createPixel(renderScript, DataType.UNSIGNED_5_5_5_1, DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_5551;
    }

    public static Element RGBA_4444(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_4444 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_4444 == null) {
                    renderScript.mElement_RGBA_4444 = createPixel(renderScript, DataType.UNSIGNED_4_4_4_4, DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_4444;
    }

    public static Element RGBA_8888(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_8888 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_8888 == null) {
                    renderScript.mElement_RGBA_8888 = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_8888;
    }

    public static Element F16_2(RenderScript renderScript) {
        if (renderScript.mElement_HALF_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_2 == null) {
                    renderScript.mElement_HALF_2 = createVector(renderScript, DataType.FLOAT_16, 2);
                }
            }
        }
        return renderScript.mElement_HALF_2;
    }

    public static Element F16_3(RenderScript renderScript) {
        if (renderScript.mElement_HALF_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_3 == null) {
                    renderScript.mElement_HALF_3 = createVector(renderScript, DataType.FLOAT_16, 3);
                }
            }
        }
        return renderScript.mElement_HALF_3;
    }

    public static Element F16_4(RenderScript renderScript) {
        if (renderScript.mElement_HALF_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_4 == null) {
                    renderScript.mElement_HALF_4 = createVector(renderScript, DataType.FLOAT_16, 4);
                }
            }
        }
        return renderScript.mElement_HALF_4;
    }

    public static Element F32_2(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_2 == null) {
                    renderScript.mElement_FLOAT_2 = createVector(renderScript, DataType.FLOAT_32, 2);
                }
            }
        }
        return renderScript.mElement_FLOAT_2;
    }

    public static Element F32_3(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_3 == null) {
                    renderScript.mElement_FLOAT_3 = createVector(renderScript, DataType.FLOAT_32, 3);
                }
            }
        }
        return renderScript.mElement_FLOAT_3;
    }

    public static Element F32_4(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_4 == null) {
                    renderScript.mElement_FLOAT_4 = createVector(renderScript, DataType.FLOAT_32, 4);
                }
            }
        }
        return renderScript.mElement_FLOAT_4;
    }

    public static Element F64_2(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_2 == null) {
                    renderScript.mElement_DOUBLE_2 = createVector(renderScript, DataType.FLOAT_64, 2);
                }
            }
        }
        return renderScript.mElement_DOUBLE_2;
    }

    public static Element F64_3(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_3 == null) {
                    renderScript.mElement_DOUBLE_3 = createVector(renderScript, DataType.FLOAT_64, 3);
                }
            }
        }
        return renderScript.mElement_DOUBLE_3;
    }

    public static Element F64_4(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_4 == null) {
                    renderScript.mElement_DOUBLE_4 = createVector(renderScript, DataType.FLOAT_64, 4);
                }
            }
        }
        return renderScript.mElement_DOUBLE_4;
    }

    public static Element U8_2(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_2 == null) {
                    renderScript.mElement_UCHAR_2 = createVector(renderScript, DataType.UNSIGNED_8, 2);
                }
            }
        }
        return renderScript.mElement_UCHAR_2;
    }

    public static Element U8_3(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_3 == null) {
                    renderScript.mElement_UCHAR_3 = createVector(renderScript, DataType.UNSIGNED_8, 3);
                }
            }
        }
        return renderScript.mElement_UCHAR_3;
    }

    public static Element U8_4(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_4 == null) {
                    renderScript.mElement_UCHAR_4 = createVector(renderScript, DataType.UNSIGNED_8, 4);
                }
            }
        }
        return renderScript.mElement_UCHAR_4;
    }

    public static Element I8_2(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_2 == null) {
                    renderScript.mElement_CHAR_2 = createVector(renderScript, DataType.SIGNED_8, 2);
                }
            }
        }
        return renderScript.mElement_CHAR_2;
    }

    public static Element I8_3(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_3 == null) {
                    renderScript.mElement_CHAR_3 = createVector(renderScript, DataType.SIGNED_8, 3);
                }
            }
        }
        return renderScript.mElement_CHAR_3;
    }

    public static Element I8_4(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_4 == null) {
                    renderScript.mElement_CHAR_4 = createVector(renderScript, DataType.SIGNED_8, 4);
                }
            }
        }
        return renderScript.mElement_CHAR_4;
    }

    public static Element U16_2(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_2 == null) {
                    renderScript.mElement_USHORT_2 = createVector(renderScript, DataType.UNSIGNED_16, 2);
                }
            }
        }
        return renderScript.mElement_USHORT_2;
    }

    public static Element U16_3(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_3 == null) {
                    renderScript.mElement_USHORT_3 = createVector(renderScript, DataType.UNSIGNED_16, 3);
                }
            }
        }
        return renderScript.mElement_USHORT_3;
    }

    public static Element U16_4(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_4 == null) {
                    renderScript.mElement_USHORT_4 = createVector(renderScript, DataType.UNSIGNED_16, 4);
                }
            }
        }
        return renderScript.mElement_USHORT_4;
    }

    public static Element I16_2(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_2 == null) {
                    renderScript.mElement_SHORT_2 = createVector(renderScript, DataType.SIGNED_16, 2);
                }
            }
        }
        return renderScript.mElement_SHORT_2;
    }

    public static Element I16_3(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_3 == null) {
                    renderScript.mElement_SHORT_3 = createVector(renderScript, DataType.SIGNED_16, 3);
                }
            }
        }
        return renderScript.mElement_SHORT_3;
    }

    public static Element I16_4(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_4 == null) {
                    renderScript.mElement_SHORT_4 = createVector(renderScript, DataType.SIGNED_16, 4);
                }
            }
        }
        return renderScript.mElement_SHORT_4;
    }

    public static Element U32_2(RenderScript renderScript) {
        if (renderScript.mElement_UINT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_2 == null) {
                    renderScript.mElement_UINT_2 = createVector(renderScript, DataType.UNSIGNED_32, 2);
                }
            }
        }
        return renderScript.mElement_UINT_2;
    }

    public static Element U32_3(RenderScript renderScript) {
        if (renderScript.mElement_UINT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_3 == null) {
                    renderScript.mElement_UINT_3 = createVector(renderScript, DataType.UNSIGNED_32, 3);
                }
            }
        }
        return renderScript.mElement_UINT_3;
    }

    public static Element U32_4(RenderScript renderScript) {
        if (renderScript.mElement_UINT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_4 == null) {
                    renderScript.mElement_UINT_4 = createVector(renderScript, DataType.UNSIGNED_32, 4);
                }
            }
        }
        return renderScript.mElement_UINT_4;
    }

    public static Element I32_2(RenderScript renderScript) {
        if (renderScript.mElement_INT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_2 == null) {
                    renderScript.mElement_INT_2 = createVector(renderScript, DataType.SIGNED_32, 2);
                }
            }
        }
        return renderScript.mElement_INT_2;
    }

    public static Element I32_3(RenderScript renderScript) {
        if (renderScript.mElement_INT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_3 == null) {
                    renderScript.mElement_INT_3 = createVector(renderScript, DataType.SIGNED_32, 3);
                }
            }
        }
        return renderScript.mElement_INT_3;
    }

    public static Element I32_4(RenderScript renderScript) {
        if (renderScript.mElement_INT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_4 == null) {
                    renderScript.mElement_INT_4 = createVector(renderScript, DataType.SIGNED_32, 4);
                }
            }
        }
        return renderScript.mElement_INT_4;
    }

    public static Element U64_2(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_2 == null) {
                    renderScript.mElement_ULONG_2 = createVector(renderScript, DataType.UNSIGNED_64, 2);
                }
            }
        }
        return renderScript.mElement_ULONG_2;
    }

    public static Element U64_3(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_3 == null) {
                    renderScript.mElement_ULONG_3 = createVector(renderScript, DataType.UNSIGNED_64, 3);
                }
            }
        }
        return renderScript.mElement_ULONG_3;
    }

    public static Element U64_4(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_4 == null) {
                    renderScript.mElement_ULONG_4 = createVector(renderScript, DataType.UNSIGNED_64, 4);
                }
            }
        }
        return renderScript.mElement_ULONG_4;
    }

    public static Element I64_2(RenderScript renderScript) {
        if (renderScript.mElement_LONG_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_2 == null) {
                    renderScript.mElement_LONG_2 = createVector(renderScript, DataType.SIGNED_64, 2);
                }
            }
        }
        return renderScript.mElement_LONG_2;
    }

    public static Element I64_3(RenderScript renderScript) {
        if (renderScript.mElement_LONG_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_3 == null) {
                    renderScript.mElement_LONG_3 = createVector(renderScript, DataType.SIGNED_64, 3);
                }
            }
        }
        return renderScript.mElement_LONG_3;
    }

    public static Element I64_4(RenderScript renderScript) {
        if (renderScript.mElement_LONG_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_4 == null) {
                    renderScript.mElement_LONG_4 = createVector(renderScript, DataType.SIGNED_64, 4);
                }
            }
        }
        return renderScript.mElement_LONG_4;
    }

    public static Element YUV(RenderScript renderScript) {
        if (renderScript.mElement_YUV == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_YUV == null) {
                    renderScript.mElement_YUV = createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_YUV);
                }
            }
        }
        return renderScript.mElement_YUV;
    }

    public static Element MATRIX_4X4(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_4X4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_4X4 == null) {
                    renderScript.mElement_MATRIX_4X4 = createUser(renderScript, DataType.MATRIX_4X4);
                }
            }
        }
        return renderScript.mElement_MATRIX_4X4;
    }

    public static Element MATRIX4X4(RenderScript renderScript) {
        return MATRIX_4X4(renderScript);
    }

    public static Element MATRIX_3X3(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_3X3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_3X3 == null) {
                    renderScript.mElement_MATRIX_3X3 = createUser(renderScript, DataType.MATRIX_3X3);
                }
            }
        }
        return renderScript.mElement_MATRIX_3X3;
    }

    public static Element MATRIX_2X2(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_2X2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_2X2 == null) {
                    renderScript.mElement_MATRIX_2X2 = createUser(renderScript, DataType.MATRIX_2X2);
                }
            }
        }
        return renderScript.mElement_MATRIX_2X2;
    }

    Element(long j, RenderScript renderScript, Element[] elementArr, String[] strArr, int[] iArr) {
        super(j, renderScript);
        int i = 0;
        this.mSize = 0;
        this.mVectorSize = 1;
        this.mElements = elementArr;
        this.mElementNames = strArr;
        this.mArraySizes = iArr;
        this.mType = DataType.NONE;
        this.mKind = DataKind.USER;
        this.mOffsetInBytes = new int[this.mElements.length];
        while (true) {
            Element[] elementArr2 = this.mElements;
            if (i < elementArr2.length) {
                int[] iArr2 = this.mOffsetInBytes;
                int i2 = this.mSize;
                iArr2[i] = i2;
                this.mSize = i2 + (elementArr2[i].mSize * this.mArraySizes[i]);
                i++;
            } else {
                updateVisibleSubElements();
                return;
            }
        }
    }

    Element(long j, RenderScript renderScript, DataType dataType, DataKind dataKind, boolean z, int i) {
        super(j, renderScript);
        if (dataType == DataType.UNSIGNED_5_6_5 || dataType == DataType.UNSIGNED_4_4_4_4 || dataType == DataType.UNSIGNED_5_5_5_1) {
            this.mSize = dataType.mSize;
        } else if (i == 3) {
            this.mSize = dataType.mSize * 4;
        } else {
            this.mSize = dataType.mSize * i;
        }
        this.mType = dataType;
        this.mKind = dataKind;
        this.mNormalized = z;
        this.mVectorSize = i;
    }

    Element(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        int[] iArr = new int[5];
        this.mRS.nElementGetNativeData(getID(this.mRS), iArr);
        this.mNormalized = iArr[2] == 1;
        this.mVectorSize = iArr[3];
        this.mSize = 0;
        for (DataType dataType : DataType.values()) {
            if (dataType.mID == iArr[0]) {
                this.mType = dataType;
                this.mSize = dataType.mSize * this.mVectorSize;
            }
        }
        for (DataKind dataKind : DataKind.values()) {
            if (dataKind.mID == iArr[1]) {
                this.mKind = dataKind;
            }
        }
        int i = iArr[4];
        if (i > 0) {
            this.mElements = new Element[i];
            this.mElementNames = new String[i];
            this.mArraySizes = new int[i];
            this.mOffsetInBytes = new int[i];
            long[] jArr = new long[i];
            this.mRS.nElementGetSubElements(getID(this.mRS), jArr, this.mElementNames, this.mArraySizes);
            for (int i2 = 0; i2 < i; i2++) {
                this.mElements[i2] = new Element(jArr[i2], this.mRS);
                this.mElements[i2].updateFromNative();
                int[] iArr2 = this.mOffsetInBytes;
                int i3 = this.mSize;
                iArr2[i2] = i3;
                this.mSize = i3 + (this.mElements[i2].mSize * this.mArraySizes[i2]);
            }
        }
        updateVisibleSubElements();
    }

    static Element createUser(RenderScript renderScript, DataType dataType) {
        DataKind dataKind = DataKind.USER;
        return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, 1), renderScript, dataType, dataKind, false, 1);
    }

    public static Element createVector(RenderScript renderScript, DataType dataType, int i) {
        if (i < 2 || i > 4) {
            throw new RSIllegalArgumentException("Vector size out of range 2-4.");
        }
        switch (dataType.ordinal()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                DataKind dataKind = DataKind.USER;
                return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, i), renderScript, dataType, dataKind, false, i);
            default:
                throw new RSIllegalArgumentException("Cannot create vector of non-primitive type.");
        }
    }

    public static Element createPixel(RenderScript renderScript, DataType dataType, DataKind dataKind) {
        int i;
        if (dataKind != DataKind.PIXEL_L && dataKind != DataKind.PIXEL_A && dataKind != DataKind.PIXEL_LA && dataKind != DataKind.PIXEL_RGB && dataKind != DataKind.PIXEL_RGBA && dataKind != DataKind.PIXEL_DEPTH && dataKind != DataKind.PIXEL_YUV) {
            throw new RSIllegalArgumentException("Unsupported DataKind");
        }
        if (dataType != DataType.UNSIGNED_8 && dataType != DataType.UNSIGNED_16 && dataType != DataType.UNSIGNED_5_6_5 && dataType != DataType.UNSIGNED_4_4_4_4 && dataType != DataType.UNSIGNED_5_5_5_1) {
            throw new RSIllegalArgumentException("Unsupported DataType");
        }
        if (dataType == DataType.UNSIGNED_5_6_5 && dataKind != DataKind.PIXEL_RGB) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_5_5_5_1 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_4_4_4_4 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_16 && dataKind != DataKind.PIXEL_DEPTH) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        int ordinal = dataKind.ordinal();
        int i2 = 2;
        if (ordinal != 3) {
            if (ordinal == 4) {
                i = 3;
            } else if (ordinal == 5) {
                i = 4;
            } else if (ordinal != 6) {
                i2 = 1;
            }
            return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, true, i), renderScript, dataType, dataKind, true, i);
        }
        i = i2;
        return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, true, i), renderScript, dataType, dataKind, true, i);
    }

    public boolean isCompatible(Element element) {
        if (equals(element)) {
            return true;
        }
        return this.mSize == element.mSize && this.mType != DataType.NONE && this.mType == element.mType && this.mVectorSize == element.mVectorSize;
    }

    public static class Builder {
        RenderScript mRS;
        int mSkipPadding;
        int mCount = 0;
        Element[] mElements = new Element[8];
        String[] mElementNames = new String[8];
        int[] mArraySizes = new int[8];

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public Builder add(Element element, String str, int i) {
            if (i < 1) {
                throw new RSIllegalArgumentException("Array size cannot be less than 1.");
            }
            if (this.mSkipPadding != 0 && str.startsWith("#padding_")) {
                this.mSkipPadding = 0;
                return this;
            }
            if (element.mVectorSize == 3) {
                this.mSkipPadding = 1;
            } else {
                this.mSkipPadding = 0;
            }
            int i2 = this.mCount;
            Element[] elementArr = this.mElements;
            if (i2 == elementArr.length) {
                Element[] elementArr2 = new Element[i2 + 8];
                String[] strArr = new String[i2 + 8];
                int[] iArr = new int[i2 + 8];
                System.arraycopy(elementArr, 0, elementArr2, 0, i2);
                System.arraycopy(this.mElementNames, 0, strArr, 0, this.mCount);
                System.arraycopy(this.mArraySizes, 0, iArr, 0, this.mCount);
                this.mElements = elementArr2;
                this.mElementNames = strArr;
                this.mArraySizes = iArr;
            }
            Element[] elementArr3 = this.mElements;
            int i3 = this.mCount;
            elementArr3[i3] = element;
            this.mElementNames[i3] = str;
            this.mArraySizes[i3] = i;
            this.mCount = i3 + 1;
            return this;
        }

        public Builder add(Element element, String str) {
            return add(element, str, 1);
        }

        public Element create() {
            this.mRS.validate();
            int i = this.mCount;
            Element[] elementArr = new Element[i];
            String[] strArr = new String[i];
            int[] iArr = new int[i];
            System.arraycopy(this.mElements, 0, elementArr, 0, i);
            System.arraycopy(this.mElementNames, 0, strArr, 0, this.mCount);
            System.arraycopy(this.mArraySizes, 0, iArr, 0, this.mCount);
            long[] jArr = new long[i];
            for (int i2 = 0; i2 < i; i2++) {
                jArr[i2] = elementArr[i2].getID(this.mRS);
            }
            return new Element(this.mRS.nElementCreate2(jArr, strArr, iArr), this.mRS, elementArr, strArr, iArr);
        }
    }
}
