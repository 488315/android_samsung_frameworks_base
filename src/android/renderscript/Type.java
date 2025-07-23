package android.renderscript;

@Deprecated
/* loaded from: classes3.dex */
public class Type extends BaseObj {
    static final int mMaxArrays = 4;
    int[] mArrays;
    boolean mDimFaces;
    boolean mDimMipmaps;
    int mDimX;
    int mDimY;
    int mDimYuv;
    int mDimZ;
    Element mElement;
    int mElementCount;

    public enum CubemapFace {
        POSITIVE_X(0),
        NEGATIVE_X(1),
        POSITIVE_Y(2),
        NEGATIVE_Y(3),
        POSITIVE_Z(4),
        NEGATIVE_Z(5),
        POSITVE_X(0),
        POSITVE_Y(2),
        POSITVE_Z(4);

        int mID;

        CubemapFace(int i) {
            this.mID = i;
        }
    }

    public Element getElement() {
        return this.mElement;
    }

    public int getX() {
        return this.mDimX;
    }

    public int getY() {
        return this.mDimY;
    }

    public int getZ() {
        return this.mDimZ;
    }

    public int getYuv() {
        return this.mDimYuv;
    }

    public boolean hasMipmaps() {
        return this.mDimMipmaps;
    }

    public boolean hasFaces() {
        return this.mDimFaces;
    }

    public int getCount() {
        return this.mElementCount;
    }

    public int getArray(int i) {
        if (i < 0 || i >= 4) {
            throw new RSIllegalArgumentException("Array dimension out of range.");
        }
        int[] iArr = this.mArrays;
        if (iArr == null || i >= iArr.length) {
            return 0;
        }
        return iArr[i];
    }

    public int getArrayCount() {
        int[] iArr = this.mArrays;
        if (iArr != null) {
            return iArr.length;
        }
        return 0;
    }

    void calcElementCount() {
        boolean hasMipmaps = hasMipmaps();
        int x = getX();
        int y = getY();
        int z = getZ();
        int i = hasFaces() ? 6 : 1;
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        if (z == 0) {
            z = 1;
        }
        int i2 = x * y * z * i;
        while (hasMipmaps && (x > 1 || y > 1 || z > 1)) {
            if (x > 1) {
                x >>= 1;
            }
            if (y > 1) {
                y >>= 1;
            }
            if (z > 1) {
                z >>= 1;
            }
            i2 += x * y * z * i;
        }
        if (this.mArrays != null) {
            int i3 = 0;
            while (true) {
                int[] iArr = this.mArrays;
                if (i3 >= iArr.length) {
                    break;
                }
                i2 *= iArr[i3];
                i3++;
            }
        }
        this.mElementCount = i2;
    }

    Type(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        long[] jArr = new long[6];
        this.mRS.nTypeGetNativeData(getID(this.mRS), jArr);
        this.mDimX = (int) jArr[0];
        this.mDimY = (int) jArr[1];
        this.mDimZ = (int) jArr[2];
        this.mDimMipmaps = jArr[3] == 1;
        this.mDimFaces = jArr[4] == 1;
        long j = jArr[5];
        if (j != 0) {
            Element element = new Element(j, this.mRS);
            this.mElement = element;
            element.updateFromNative();
        }
        calcElementCount();
    }

    public static Type createX(RenderScript renderScript, Element element, int i) {
        if (i < 1) {
            throw new RSInvalidStateException("Dimension must be >= 1.");
        }
        Type type = new Type(renderScript.nTypeCreate(element.getID(renderScript), i, 0, 0, false, false, 0), renderScript);
        type.mElement = element;
        type.mDimX = i;
        type.calcElementCount();
        return type;
    }

    public static Type createXY(RenderScript renderScript, Element element, int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new RSInvalidStateException("Dimension must be >= 1.");
        }
        Type type = new Type(renderScript.nTypeCreate(element.getID(renderScript), i, i2, 0, false, false, 0), renderScript);
        type.mElement = element;
        type.mDimX = i;
        type.mDimY = i2;
        type.calcElementCount();
        return type;
    }

    public static Type createXYZ(RenderScript renderScript, Element element, int i, int i2, int i3) {
        if (i < 1 || i2 < 1 || i3 < 1) {
            throw new RSInvalidStateException("Dimension must be >= 1.");
        }
        Type type = new Type(renderScript.nTypeCreate(element.getID(renderScript), i, i2, i3, false, false, 0), renderScript);
        type.mElement = element;
        type.mDimX = i;
        type.mDimY = i2;
        type.mDimZ = i3;
        type.calcElementCount();
        return type;
    }

    public static class Builder {
        boolean mDimFaces;
        boolean mDimMipmaps;
        int mDimY;
        int mDimZ;
        Element mElement;
        RenderScript mRS;
        int mYuv;
        int mDimX = 1;
        int[] mArray = new int[4];

        public Builder(RenderScript renderScript, Element element) {
            element.checkValid();
            this.mRS = renderScript;
            this.mElement = element;
        }

        public Builder setX(int i) {
            if (i < 1) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension X are not valid.");
            }
            this.mDimX = i;
            return this;
        }

        public Builder setY(int i) {
            if (i < 1) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension Y are not valid.");
            }
            this.mDimY = i;
            return this;
        }

        public Builder setZ(int i) {
            if (i < 1) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension Z are not valid.");
            }
            this.mDimZ = i;
            return this;
        }

        public Builder setArray(int i, int i2) {
            if (i < 0 || i >= 4) {
                throw new RSIllegalArgumentException("Array dimension out of range.");
            }
            this.mArray[i] = i2;
            return this;
        }

        public Builder setMipmaps(boolean z) {
            this.mDimMipmaps = z;
            return this;
        }

        public Builder setFaces(boolean z) {
            this.mDimFaces = z;
            return this;
        }

        public Builder setYuvFormat(int i) {
            if (i != 17 && i != 35 && i != 842094169) {
                throw new RSIllegalArgumentException("Only ImageFormat.NV21, .YV12, and .YUV_420_888 are supported..");
            }
            this.mYuv = i;
            return this;
        }

        public Type create() {
            int i = this.mDimZ;
            if (i > 0) {
                if (this.mDimX < 1 || this.mDimY < 1) {
                    throw new RSInvalidStateException("Both X and Y dimension required when Z is present.");
                }
                if (this.mDimFaces) {
                    throw new RSInvalidStateException("Cube maps not supported with 3D types.");
                }
            }
            int i2 = this.mDimY;
            if (i2 > 0 && this.mDimX < 1) {
                throw new RSInvalidStateException("X dimension required when Y is present.");
            }
            boolean z = this.mDimFaces;
            if (z && i2 < 1) {
                throw new RSInvalidStateException("Cube maps require 2D Types.");
            }
            if (this.mYuv != 0 && (i != 0 || z || this.mDimMipmaps)) {
                throw new RSInvalidStateException("YUV only supports basic 2D.");
            }
            int[] iArr = null;
            for (int i3 = 3; i3 >= 0; i3--) {
                int i4 = this.mArray[i3];
                if (i4 != 0 && iArr == null) {
                    iArr = new int[i3];
                }
                if (i4 == 0 && iArr != null) {
                    throw new RSInvalidStateException("Array dimensions must be contigous from 0.");
                }
            }
            RenderScript renderScript = this.mRS;
            Type type = new Type(renderScript.nTypeCreate(this.mElement.getID(renderScript), this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv), this.mRS);
            type.mElement = this.mElement;
            type.mDimX = this.mDimX;
            type.mDimY = this.mDimY;
            type.mDimZ = this.mDimZ;
            type.mDimMipmaps = this.mDimMipmaps;
            type.mDimFaces = this.mDimFaces;
            type.mDimYuv = this.mYuv;
            type.mArrays = iArr;
            type.calcElementCount();
            return type;
        }
    }
}
