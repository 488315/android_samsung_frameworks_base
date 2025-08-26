package android.renderscript;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaMetrics;
import android.os.Trace;
import android.renderscript.Element;
import android.renderscript.Type;
import android.util.Log;
import android.view.Surface;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.HashMap;

@Deprecated
/* loaded from: classes3.dex */
public class Allocation extends BaseObj {
    private static final int MAX_NUMBER_IO_INPUT_ALLOC = 16;
    public static final int USAGE_GRAPHICS_CONSTANTS = 8;
    public static final int USAGE_GRAPHICS_RENDER_TARGET = 16;
    public static final int USAGE_GRAPHICS_TEXTURE = 2;
    public static final int USAGE_GRAPHICS_VERTEX = 4;
    public static final int USAGE_IO_INPUT = 32;
    public static final int USAGE_IO_OUTPUT = 64;
    public static final int USAGE_SCRIPT = 1;
    public static final int USAGE_SHARED = 128;
    static HashMap<Long, Allocation> mAllocationMap = new HashMap<>();
    static BitmapFactory.Options mBitmapOptions;
    Allocation mAdaptedAllocation;
    boolean mAutoPadding;
    Bitmap mBitmap;
    OnBufferAvailableListener mBufferNotifier;
    private ByteBuffer mByteBuffer;
    private long mByteBufferStride;
    int mCurrentCount;
    int mCurrentDimX;
    int mCurrentDimY;
    int mCurrentDimZ;
    private Surface mGetSurfaceSurface;
    MipmapControl mMipmapControl;
    boolean mOwningType;
    boolean mReadAllowed;
    int[] mSelectedArray;
    Type.CubemapFace mSelectedFace;
    int mSelectedLOD;
    int mSelectedX;
    int mSelectedY;
    int mSelectedZ;
    int mSize;
    long mTimeStamp;
    Type mType;
    int mUsage;
    boolean mWriteAllowed;

    public interface OnBufferAvailableListener {
        void onBufferAvailable(Allocation allocation);
    }

    static {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBitmapOptions = options;
        options.inScaled = false;
    }

    private Element.DataType validateObjectIsPrimitiveArray(Object obj, boolean z) {
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new RSIllegalArgumentException("Object passed is not an array of primitives.");
        }
        Class<?> componentType = cls.getComponentType();
        if (!componentType.isPrimitive()) {
            throw new RSIllegalArgumentException("Object passed is not an Array of primitives.");
        }
        if (componentType == Long.TYPE) {
            if (z) {
                validateIsInt64();
                return this.mType.mElement.mType;
            }
            return Element.DataType.SIGNED_64;
        }
        if (componentType == Integer.TYPE) {
            if (z) {
                validateIsInt32();
                return this.mType.mElement.mType;
            }
            return Element.DataType.SIGNED_32;
        }
        if (componentType == Short.TYPE) {
            if (z) {
                validateIsInt16OrFloat16();
                return this.mType.mElement.mType;
            }
            return Element.DataType.SIGNED_16;
        }
        if (componentType == Byte.TYPE) {
            if (z) {
                validateIsInt8();
                return this.mType.mElement.mType;
            }
            return Element.DataType.SIGNED_8;
        }
        if (componentType == Float.TYPE) {
            if (z) {
                validateIsFloat32();
            }
            return Element.DataType.FLOAT_32;
        }
        if (componentType == Double.TYPE) {
            if (z) {
                validateIsFloat64();
            }
            return Element.DataType.FLOAT_64;
        }
        throw new RSIllegalArgumentException("Parameter of type " + componentType.getSimpleName() + "[] is not compatible with data type " + this.mType.mElement.mType.name() + " of allocation");
    }

    public enum MipmapControl {
        MIPMAP_NONE(0),
        MIPMAP_FULL(1),
        MIPMAP_ON_SYNC_TO_TEXTURE(2);

        int mID;

        MipmapControl(int i) {
            this.mID = i;
        }
    }

    private long getIDSafe() {
        Allocation allocation = this.mAdaptedAllocation;
        if (allocation != null) {
            return allocation.getID(this.mRS);
        }
        return getID(this.mRS);
    }

    public Element getElement() {
        return this.mType.getElement();
    }

    public int getUsage() {
        return this.mUsage;
    }

    public MipmapControl getMipmap() {
        return this.mMipmapControl;
    }

    public void setAutoPadding(boolean z) {
        this.mAutoPadding = z;
    }

    public int getBytesSize() {
        if (this.mType.mDimYuv != 0) {
            return (int) Math.ceil(this.mType.getCount() * this.mType.getElement().getBytesSize() * 1.5d);
        }
        return this.mType.getCount() * this.mType.getElement().getBytesSize();
    }

    private void updateCacheInfo(Type type) {
        this.mCurrentDimX = type.getX();
        this.mCurrentDimY = type.getY();
        int z = type.getZ();
        this.mCurrentDimZ = z;
        int i = this.mCurrentDimX;
        this.mCurrentCount = i;
        int i2 = this.mCurrentDimY;
        if (i2 > 1) {
            this.mCurrentCount = i * i2;
        }
        if (z > 1) {
            this.mCurrentCount *= z;
        }
    }

    private void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    Allocation(long j, RenderScript renderScript, Type type, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super(j, renderScript);
        this.mOwningType = false;
        this.mTimeStamp = -1L;
        this.mReadAllowed = true;
        this.mWriteAllowed = true;
        this.mAutoPadding = false;
        this.mSelectedFace = Type.CubemapFace.POSITIVE_X;
        this.mGetSurfaceSurface = null;
        this.mByteBuffer = null;
        this.mByteBufferStride = -1L;
        if ((i & (-256)) != 0) {
            throw new RSIllegalArgumentException("Unknown usage specified.");
        }
        if ((i & 32) != 0) {
            this.mWriteAllowed = false;
            if ((i & (-36)) != 0) {
                throw new RSIllegalArgumentException("Invalid usage combination.");
            }
        }
        this.mType = type;
        this.mUsage = i;
        if (type != null) {
            this.mSize = type.getCount() * this.mType.getElement().getBytesSize();
            updateCacheInfo(type);
        }
        try {
            RenderScript.registerNativeAllocation.invoke(RenderScript.sRuntime, Integer.valueOf(this.mSize));
            this.guard.open("destroy");
        } catch (Exception e) {
            Log.e("RenderScript_jni", "Couldn't invoke registerNativeAllocation:" + e);
            throw new RSRuntimeException("Couldn't invoke registerNativeAllocation:" + e);
        }
    }

    Allocation(long j, RenderScript renderScript, Type type, boolean z, int i, MipmapControl mipmapControl) {
        this(j, renderScript, type, i);
        this.mOwningType = z;
        this.mMipmapControl = mipmapControl;
    }

    @Override // android.renderscript.BaseObj
    protected void finalize() throws Throwable {
        RenderScript.registerNativeFree.invoke(RenderScript.sRuntime, Integer.valueOf(this.mSize));
        super.finalize();
    }

    private void validateIsInt64() {
        if (this.mType.mElement.mType == Element.DataType.SIGNED_64 || this.mType.mElement.mType == Element.DataType.UNSIGNED_64) {
            return;
        }
        throw new RSIllegalArgumentException("64 bit integer source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsInt32() {
        if (this.mType.mElement.mType == Element.DataType.SIGNED_32 || this.mType.mElement.mType == Element.DataType.UNSIGNED_32) {
            return;
        }
        throw new RSIllegalArgumentException("32 bit integer source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsInt16OrFloat16() {
        if (this.mType.mElement.mType == Element.DataType.SIGNED_16 || this.mType.mElement.mType == Element.DataType.UNSIGNED_16 || this.mType.mElement.mType == Element.DataType.FLOAT_16) {
            return;
        }
        throw new RSIllegalArgumentException("16 bit integer source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsInt8() {
        if (this.mType.mElement.mType == Element.DataType.SIGNED_8 || this.mType.mElement.mType == Element.DataType.UNSIGNED_8) {
            return;
        }
        throw new RSIllegalArgumentException("8 bit integer source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsFloat32() {
        if (this.mType.mElement.mType == Element.DataType.FLOAT_32) {
            return;
        }
        throw new RSIllegalArgumentException("32 bit float source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsFloat64() {
        if (this.mType.mElement.mType == Element.DataType.FLOAT_64) {
            return;
        }
        throw new RSIllegalArgumentException("64 bit float source does not match allocation type " + this.mType.mElement.mType);
    }

    private void validateIsObject() {
        if (this.mType.mElement.mType == Element.DataType.RS_ELEMENT || this.mType.mElement.mType == Element.DataType.RS_TYPE || this.mType.mElement.mType == Element.DataType.RS_ALLOCATION || this.mType.mElement.mType == Element.DataType.RS_SAMPLER || this.mType.mElement.mType == Element.DataType.RS_SCRIPT || this.mType.mElement.mType == Element.DataType.RS_MESH || this.mType.mElement.mType == Element.DataType.RS_PROGRAM_FRAGMENT || this.mType.mElement.mType == Element.DataType.RS_PROGRAM_VERTEX || this.mType.mElement.mType == Element.DataType.RS_PROGRAM_RASTER || this.mType.mElement.mType == Element.DataType.RS_PROGRAM_STORE) {
            return;
        }
        throw new RSIllegalArgumentException("Object source does not match allocation type " + this.mType.mElement.mType);
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        long jNAllocationGetType = this.mRS.nAllocationGetType(getID(this.mRS));
        if (jNAllocationGetType != 0) {
            Type type = new Type(jNAllocationGetType, this.mRS);
            this.mType = type;
            type.updateFromNative();
            updateCacheInfo(this.mType);
        }
    }

    public Type getType() {
        return this.mType;
    }

    public void syncAll(int i) {
        try {
            Trace.traceBegin(32768L, "syncAll");
            if (i == 1 || i == 2) {
                if ((this.mUsage & 128) != 0) {
                    copyFrom(this.mBitmap);
                }
            } else if (i != 4 && i != 8) {
                if (i == 128) {
                    if ((this.mUsage & 128) != 0) {
                        copyTo(this.mBitmap);
                    }
                } else {
                    throw new RSIllegalArgumentException("Source must be exactly one usage type.");
                }
            }
            this.mRS.validate();
            this.mRS.nAllocationSyncAll(getIDSafe(), i);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void ioSend() {
        try {
            Trace.traceBegin(32768L, "ioSend");
            if ((this.mUsage & 64) == 0) {
                throw new RSIllegalArgumentException("Can only send buffer if IO_OUTPUT usage specified.");
            }
            this.mRS.validate();
            this.mRS.nAllocationIoSend(getID(this.mRS));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void ioReceive() {
        try {
            Trace.traceBegin(32768L, "ioReceive");
            if ((this.mUsage & 32) == 0) {
                throw new RSIllegalArgumentException("Can only receive if IO_INPUT usage specified.");
            }
            this.mRS.validate();
            this.mTimeStamp = this.mRS.nAllocationIoReceive(getID(this.mRS));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(BaseObj[] baseObjArr) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            validateIsObject();
            if (baseObjArr.length != this.mCurrentCount) {
                throw new RSIllegalArgumentException("Array size mismatch, allocation sizeX = " + this.mCurrentCount + ", array length = " + baseObjArr.length);
            }
            if (RenderScript.sPointerSize == 8) {
                long[] jArr = new long[baseObjArr.length * 4];
                for (int i = 0; i < baseObjArr.length; i++) {
                    jArr[i * 4] = baseObjArr[i].getID(this.mRS);
                }
                copy1DRangeFromUnchecked(0, this.mCurrentCount, (Object) jArr);
            } else {
                int[] iArr = new int[baseObjArr.length];
                for (int i2 = 0; i2 < baseObjArr.length; i2++) {
                    iArr[i2] = (int) baseObjArr[i2].getID(this.mRS);
                }
                copy1DRangeFromUnchecked(0, this.mCurrentCount, iArr);
            }
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    private void validateBitmapFormat(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            throw new RSIllegalArgumentException("Bitmap has an unsupported format for this operation");
        }
        int i = AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()];
        if (i == 1) {
            if (this.mType.getElement().mKind == Element.DataKind.PIXEL_A) {
                return;
            }
            throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
        }
        if (i == 2) {
            if (this.mType.getElement().mKind == Element.DataKind.PIXEL_RGBA && this.mType.getElement().getBytesSize() == 4) {
                return;
            }
            throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
        }
        if (i == 3) {
            if (this.mType.getElement().mKind == Element.DataKind.PIXEL_RGB && this.mType.getElement().getBytesSize() == 2) {
                return;
            }
            throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
        }
        if (i != 4) {
            return;
        }
        if (this.mType.getElement().mKind == Element.DataKind.PIXEL_RGBA && this.mType.getElement().getBytesSize() == 2) {
            return;
        }
        throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + config);
    }

    /* renamed from: android.renderscript.Allocation$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            $SwitchMap$android$graphics$Bitmap$Config = iArr;
            try {
                iArr[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGB_565.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void validateBitmapSize(Bitmap bitmap) {
        if (this.mCurrentDimX != bitmap.getWidth() || this.mCurrentDimY != bitmap.getHeight()) {
            throw new RSIllegalArgumentException("Cannot update allocation from bitmap, sizes mismatch");
        }
    }

    private void copyFromUnchecked(Object obj, Element.DataType dataType, int i) {
        try {
            Trace.traceBegin(32768L, "copyFromUnchecked");
            this.mRS.validate();
            int i2 = this.mCurrentDimZ;
            if (i2 > 0) {
                copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, i2, obj, dataType, i);
            } else {
                int i3 = this.mCurrentDimY;
                if (i3 > 0) {
                    copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, i3, obj, dataType, i);
                } else {
                    copy1DRangeFromUnchecked(0, this.mCurrentCount, obj, dataType, i);
                }
            }
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFromUnchecked(Object obj) {
        try {
            Trace.traceBegin(32768L, "copyFromUnchecked");
            copyFromUnchecked(obj, validateObjectIsPrimitiveArray(obj, false), Array.getLength(obj));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFromUnchecked(int[] iArr) {
        copyFromUnchecked(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFromUnchecked(short[] sArr) {
        copyFromUnchecked(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFromUnchecked(byte[] bArr) {
        copyFromUnchecked(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFromUnchecked(float[] fArr) {
        copyFromUnchecked(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(Object obj) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            copyFromUnchecked(obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(int[] iArr) {
        validateIsInt32();
        copyFromUnchecked(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyFrom(short[] sArr) {
        validateIsInt16OrFloat16();
        copyFromUnchecked(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyFrom(byte[] bArr) {
        validateIsInt8();
        copyFromUnchecked(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyFrom(float[] fArr) {
        validateIsFloat32();
        copyFromUnchecked(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyFrom(Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                copyFrom(bitmapCreateBitmap);
            } else {
                validateBitmapSize(bitmap);
                validateBitmapFormat(bitmap);
                this.mRS.nAllocationCopyFromBitmap(getID(this.mRS), bitmap);
            }
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyFrom(Allocation allocation) {
        try {
            Trace.traceBegin(32768L, "copyFrom");
            this.mRS.validate();
            if (!this.mType.equals(allocation.getType())) {
                throw new RSIllegalArgumentException("Types of allocations must match.");
            }
            copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, allocation, 0, 0);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void setFromFieldPacker(int i, FieldPacker fieldPacker) {
        this.mRS.validate();
        int bytesSize = this.mType.mElement.getBytesSize();
        byte[] data = fieldPacker.getData();
        int pos = fieldPacker.getPos();
        int i2 = pos / bytesSize;
        if (bytesSize * i2 != pos) {
            throw new RSIllegalArgumentException("Field packer length " + pos + " not divisible by element size " + bytesSize + MediaMetrics.SEPARATOR);
        }
        copy1DRangeFromUnchecked(i, i2, data);
    }

    public void setFromFieldPacker(int i, int i2, FieldPacker fieldPacker) {
        setFromFieldPacker(i, 0, 0, i2, fieldPacker);
    }

    public void setFromFieldPacker(int i, int i2, int i3, int i4, FieldPacker fieldPacker) {
        this.mRS.validate();
        if (i4 >= this.mType.mElement.mElements.length) {
            throw new RSIllegalArgumentException("Component_number " + i4 + " out of range.");
        }
        if (i < 0) {
            throw new RSIllegalArgumentException("Offset x must be >= 0.");
        }
        if (i2 < 0) {
            throw new RSIllegalArgumentException("Offset y must be >= 0.");
        }
        if (i3 < 0) {
            throw new RSIllegalArgumentException("Offset z must be >= 0.");
        }
        byte[] data = fieldPacker.getData();
        int pos = fieldPacker.getPos();
        int bytesSize = this.mType.mElement.mElements[i4].getBytesSize() * this.mType.mElement.mArraySizes[i4];
        if (pos != bytesSize) {
            throw new RSIllegalArgumentException("Field packer sizelength " + pos + " does not match component size " + bytesSize + MediaMetrics.SEPARATOR);
        }
        this.mRS.nAllocationElementData(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, data, pos);
    }

    private void data1DChecks(int i, int i2, int i3, int i4, boolean z) {
        this.mRS.validate();
        if (i < 0) {
            throw new RSIllegalArgumentException("Offset must be >= 0.");
        }
        if (i2 < 1) {
            throw new RSIllegalArgumentException("Count must be >= 1.");
        }
        if (i + i2 <= this.mCurrentCount) {
            if (z) {
                if (i3 < (i4 / 4) * 3) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                return;
            } else {
                if (i3 < i4) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                return;
            }
        }
        throw new RSIllegalArgumentException("Overflow, Available count " + this.mCurrentCount + ", got " + i2 + " at offset " + i + MediaMetrics.SEPARATOR);
    }

    public void generateMipmaps() {
        this.mRS.nAllocationGenerateMipmaps(getID(this.mRS));
    }

    private void copy1DRangeFromUnchecked(int i, int i2, Object obj, Element.DataType dataType, int i3) {
        try {
            Trace.traceBegin(32768L, "copy1DRangeFromUnchecked");
            int bytesSize = this.mType.mElement.getBytesSize() * i2;
            boolean z = this.mAutoPadding && this.mType.getElement().getVectorSize() == 3;
            data1DChecks(i, i2, i3 * dataType.mSize, bytesSize, z);
            this.mRS.nAllocationData1D(getIDSafe(), i, this.mSelectedLOD, i2, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy1DRangeFromUnchecked(int i, int i2, Object obj) {
        copy1DRangeFromUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, false), Array.getLength(obj));
    }

    public void copy1DRangeFromUnchecked(int i, int i2, int[] iArr) {
        copy1DRangeFromUnchecked(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, short[] sArr) {
        copy1DRangeFromUnchecked(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, byte[] bArr) {
        copy1DRangeFromUnchecked(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, float[] fArr) {
        copy1DRangeFromUnchecked(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, Object obj) {
        copy1DRangeFromUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
    }

    public void copy1DRangeFrom(int i, int i2, int[] iArr) {
        validateIsInt32();
        copy1DRangeFromUnchecked(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, short[] sArr) {
        validateIsInt16OrFloat16();
        copy1DRangeFromUnchecked(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, byte[] bArr) {
        validateIsInt8();
        copy1DRangeFromUnchecked(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, float[] fArr) {
        validateIsFloat32();
        copy1DRangeFromUnchecked(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeFrom(int i, int i2, Allocation allocation, int i3) {
        Trace.traceBegin(32768L, "copy1DRangeFrom");
        this.mRS.nAllocationData2D(getIDSafe(), i, 0, this.mSelectedLOD, this.mSelectedFace.mID, i2, 1, allocation.getID(this.mRS), i3, 0, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
        Trace.traceEnd(32768L);
    }

    private void validate2DRange(int i, int i2, int i3, int i4) {
        if (this.mAdaptedAllocation != null) {
            return;
        }
        if (i < 0 || i2 < 0) {
            throw new RSIllegalArgumentException("Offset cannot be negative.");
        }
        if (i4 < 0 || i3 < 0) {
            throw new RSIllegalArgumentException("Height or width cannot be negative.");
        }
        if (i + i3 > this.mCurrentDimX || i2 + i4 > this.mCurrentDimY) {
            throw new RSIllegalArgumentException("Updated region larger than allocation.");
        }
    }

    void copy2DRangeFromUnchecked(int i, int i2, int i3, int i4, Object obj, Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        try {
            Trace.traceBegin(32768L, "copy2DRangeFromUnchecked");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            int bytesSize = this.mType.mElement.getBytesSize() * i3 * i4;
            int i7 = dataType.mSize * i5;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i7) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                i6 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i7) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i6 = i7;
            }
            this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, obj, i6, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, Object obj) {
        try {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            copy2DRangeFromUnchecked(i, i2, i3, i4, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, byte[] bArr) {
        validateIsInt8();
        copy2DRangeFromUnchecked(i, i2, i3, i4, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, short[] sArr) {
        validateIsInt16OrFloat16();
        copy2DRangeFromUnchecked(i, i2, i3, i4, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, int[] iArr) {
        validateIsInt32();
        copy2DRangeFromUnchecked(i, i2, i3, i4, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, float[] fArr) {
        validateIsFloat32();
        copy2DRangeFromUnchecked(i, i2, i3, i4, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, Allocation allocation, int i5, int i6) {
        try {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, allocation.getID(this.mRS), i5, i6, allocation.mSelectedLOD, allocation.mSelectedFace.mID);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeFrom(int i, int i2, Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copy2DRangeFrom");
            this.mRS.validate();
            if (bitmap.getConfig() == null) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                copy2DRangeFrom(i, i2, bitmapCreateBitmap);
            } else {
                validateBitmapFormat(bitmap);
                validate2DRange(i, i2, bitmap.getWidth(), bitmap.getHeight());
                this.mRS.nAllocationData2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, bitmap);
            }
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    private void validate3DRange(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mAdaptedAllocation != null) {
            return;
        }
        if (i < 0 || i2 < 0 || i3 < 0) {
            throw new RSIllegalArgumentException("Offset cannot be negative.");
        }
        if (i5 < 0 || i4 < 0 || i6 < 0) {
            throw new RSIllegalArgumentException("Height or width cannot be negative.");
        }
        if (i + i4 > this.mCurrentDimX || i2 + i5 > this.mCurrentDimY || i3 + i6 > this.mCurrentDimZ) {
            throw new RSIllegalArgumentException("Updated region larger than allocation.");
        }
    }

    private void copy3DRangeFromUnchecked(int i, int i2, int i3, int i4, int i5, int i6, Object obj, Element.DataType dataType, int i7) {
        boolean z;
        int i8;
        try {
            Trace.traceBegin(32768L, "copy3DRangeFromUnchecked");
            this.mRS.validate();
            validate3DRange(i, i2, i3, i4, i5, i6);
            int bytesSize = this.mType.mElement.getBytesSize() * i4 * i5 * i6;
            int i9 = dataType.mSize * i7;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i9) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                i8 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i9) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i8 = i9;
            }
            this.mRS.nAllocationData3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, obj, i8, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        try {
            Trace.traceBegin(32768L, "copy3DRangeFrom");
            copy3DRangeFromUnchecked(i, i2, i3, i4, i5, i6, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeFrom(int i, int i2, int i3, int i4, int i5, int i6, Allocation allocation, int i7, int i8, int i9) {
        this.mRS.validate();
        validate3DRange(i, i2, i3, i4, i5, i6);
        this.mRS.nAllocationData3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, allocation.getID(this.mRS), i7, i8, i9, allocation.mSelectedLOD);
    }

    public void copyTo(Bitmap bitmap) {
        try {
            Trace.traceBegin(32768L, "copyTo");
            this.mRS.validate();
            validateBitmapFormat(bitmap);
            validateBitmapSize(bitmap);
            this.mRS.nAllocationCopyToBitmap(getID(this.mRS), bitmap);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    private void copyTo(Object obj, Element.DataType dataType, int i) {
        try {
            Trace.traceBegin(32768L, "copyTo");
            this.mRS.validate();
            boolean z = this.mAutoPadding && this.mType.getElement().getVectorSize() == 3;
            if (z) {
                if (dataType.mSize * i < (this.mSize / 4) * 3) {
                    throw new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
                }
            } else if (dataType.mSize * i < this.mSize) {
                throw new RSIllegalArgumentException("Size of output array cannot be smaller than size of allocation.");
            }
            this.mRS.nAllocationRead(getID(this.mRS), obj, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copyTo(Object obj) {
        copyTo(obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
    }

    public void copyTo(byte[] bArr) {
        validateIsInt8();
        copyTo(bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copyTo(short[] sArr) {
        validateIsInt16OrFloat16();
        copyTo(sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copyTo(int[] iArr) {
        validateIsInt32();
        copyTo(iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copyTo(float[] fArr) {
        validateIsFloat32();
        copyTo(fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copyToFieldPacker(int i, int i2, int i3, int i4, FieldPacker fieldPacker) {
        this.mRS.validate();
        if (i4 >= this.mType.mElement.mElements.length) {
            throw new RSIllegalArgumentException("Component_number " + i4 + " out of range.");
        }
        if (i < 0) {
            throw new RSIllegalArgumentException("Offset x must be >= 0.");
        }
        if (i2 < 0) {
            throw new RSIllegalArgumentException("Offset y must be >= 0.");
        }
        if (i3 < 0) {
            throw new RSIllegalArgumentException("Offset z must be >= 0.");
        }
        byte[] data = fieldPacker.getData();
        int length = data.length;
        int bytesSize = this.mType.mElement.mElements[i4].getBytesSize() * this.mType.mElement.mArraySizes[i4];
        if (length != bytesSize) {
            throw new RSIllegalArgumentException("Field packer sizelength " + length + " does not match component size " + bytesSize + MediaMetrics.SEPARATOR);
        }
        this.mRS.nAllocationElementRead(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, data, length);
    }

    public synchronized void resize(int i) {
        if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion >= 21) {
            throw new RSRuntimeException("Resize is not allowed in API 21+.");
        }
        if (this.mType.getY() > 0 || this.mType.getZ() > 0 || this.mType.hasFaces() || this.mType.hasMipmaps()) {
            throw new RSInvalidStateException("Resize only support for 1D allocations at this time.");
        }
        this.mRS.nAllocationResize1D(getID(this.mRS), i);
        this.mRS.finish();
        long jNAllocationGetType = this.mRS.nAllocationGetType(getID(this.mRS));
        this.mType.setID(0L);
        Type type = new Type(jNAllocationGetType, this.mRS);
        this.mType = type;
        type.updateFromNative();
        updateCacheInfo(this.mType);
    }

    private void copy1DRangeToUnchecked(int i, int i2, Object obj, Element.DataType dataType, int i3) {
        try {
            Trace.traceBegin(32768L, "copy1DRangeToUnchecked");
            int bytesSize = this.mType.mElement.getBytesSize() * i2;
            boolean z = this.mAutoPadding && this.mType.getElement().getVectorSize() == 3;
            data1DChecks(i, i2, i3 * dataType.mSize, bytesSize, z);
            this.mRS.nAllocationRead1D(getIDSafe(), i, this.mSelectedLOD, i2, obj, bytesSize, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy1DRangeToUnchecked(int i, int i2, Object obj) {
        copy1DRangeToUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, false), Array.getLength(obj));
    }

    public void copy1DRangeToUnchecked(int i, int i2, int[] iArr) {
        copy1DRangeToUnchecked(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, short[] sArr) {
        copy1DRangeToUnchecked(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, byte[] bArr) {
        copy1DRangeToUnchecked(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeToUnchecked(int i, int i2, float[] fArr) {
        copy1DRangeToUnchecked(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    public void copy1DRangeTo(int i, int i2, Object obj) {
        copy1DRangeToUnchecked(i, i2, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
    }

    public void copy1DRangeTo(int i, int i2, int[] iArr) {
        validateIsInt32();
        copy1DRangeToUnchecked(i, i2, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy1DRangeTo(int i, int i2, short[] sArr) {
        validateIsInt16OrFloat16();
        copy1DRangeToUnchecked(i, i2, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy1DRangeTo(int i, int i2, byte[] bArr) {
        validateIsInt8();
        copy1DRangeToUnchecked(i, i2, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy1DRangeTo(int i, int i2, float[] fArr) {
        validateIsFloat32();
        copy1DRangeToUnchecked(i, i2, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    void copy2DRangeToUnchecked(int i, int i2, int i3, int i4, Object obj, Element.DataType dataType, int i5) {
        boolean z;
        int i6;
        try {
            Trace.traceBegin(32768L, "copy2DRangeToUnchecked");
            this.mRS.validate();
            validate2DRange(i, i2, i3, i4);
            int bytesSize = this.mType.mElement.getBytesSize() * i3 * i4;
            int i7 = dataType.mSize * i5;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i7) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                i6 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i7) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i6 = i7;
            }
            this.mRS.nAllocationRead2D(getIDSafe(), i, i2, this.mSelectedLOD, this.mSelectedFace.mID, i3, i4, obj, i6, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, Object obj) {
        copy2DRangeToUnchecked(i, i2, i3, i4, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, byte[] bArr) {
        validateIsInt8();
        copy2DRangeToUnchecked(i, i2, i3, i4, bArr, Element.DataType.SIGNED_8, bArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, short[] sArr) {
        validateIsInt16OrFloat16();
        copy2DRangeToUnchecked(i, i2, i3, i4, sArr, Element.DataType.SIGNED_16, sArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, int[] iArr) {
        validateIsInt32();
        copy2DRangeToUnchecked(i, i2, i3, i4, iArr, Element.DataType.SIGNED_32, iArr.length);
    }

    public void copy2DRangeTo(int i, int i2, int i3, int i4, float[] fArr) {
        validateIsFloat32();
        copy2DRangeToUnchecked(i, i2, i3, i4, fArr, Element.DataType.FLOAT_32, fArr.length);
    }

    private void copy3DRangeToUnchecked(int i, int i2, int i3, int i4, int i5, int i6, Object obj, Element.DataType dataType, int i7) {
        boolean z;
        int i8;
        try {
            Trace.traceBegin(32768L, "copy3DRangeToUnchecked");
            this.mRS.validate();
            validate3DRange(i, i2, i3, i4, i5, i6);
            int bytesSize = this.mType.mElement.getBytesSize() * i4 * i5 * i6;
            int i9 = dataType.mSize * i7;
            if (this.mAutoPadding && this.mType.getElement().getVectorSize() == 3) {
                if ((bytesSize / 4) * 3 > i9) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                i8 = bytesSize;
                z = true;
            } else {
                if (bytesSize > i9) {
                    throw new RSIllegalArgumentException("Array too small for allocation type.");
                }
                z = false;
                i8 = i9;
            }
            this.mRS.nAllocationRead3D(getIDSafe(), i, i2, i3, this.mSelectedLOD, i4, i5, i6, obj, i8, dataType, this.mType.mElement.mType.mSize, z);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public void copy3DRangeTo(int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        copy3DRangeToUnchecked(i, i2, i3, i4, i5, i6, obj, validateObjectIsPrimitiveArray(obj, true), Array.getLength(obj));
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, MipmapControl mipmapControl, int i) {
        try {
            Trace.traceBegin(32768L, "createTyped");
            renderScript.validate();
            if (type.getID(renderScript) == 0) {
                throw new RSInvalidStateException("Bad Type");
            }
            long jNAllocationCreateTyped = renderScript.nAllocationCreateTyped(type.getID(renderScript), mipmapControl.mID, i, 0L);
            if (jNAllocationCreateTyped == 0) {
                throw new RSRuntimeException("Allocation creation failed.");
            }
            return new Allocation(jNAllocationCreateTyped, renderScript, type, false, i, mipmapControl);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, int i) {
        return createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, i);
    }

    public static Allocation createTyped(RenderScript renderScript, Type type) {
        return createTyped(renderScript, type, MipmapControl.MIPMAP_NONE, 1);
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int i, int i2) {
        try {
            Trace.traceBegin(32768L, "createSized");
            renderScript.validate();
            Type.Builder builder = new Type.Builder(renderScript, element);
            builder.setX(i);
            Type typeCreate = builder.create();
            long jNAllocationCreateTyped = renderScript.nAllocationCreateTyped(typeCreate.getID(renderScript), MipmapControl.MIPMAP_NONE.mID, i2, 0L);
            if (jNAllocationCreateTyped == 0) {
                throw new RSRuntimeException("Allocation creation failed.");
            }
            return new Allocation(jNAllocationCreateTyped, renderScript, typeCreate, true, i2, MipmapControl.MIPMAP_NONE);
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int i) {
        return createSized(renderScript, element, i, 1);
    }

    static Element elementFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.ALPHA_8) {
            return Element.A_8(renderScript);
        }
        if (config == Bitmap.Config.ARGB_4444) {
            return Element.RGBA_4444(renderScript);
        }
        if (config == Bitmap.Config.ARGB_8888) {
            return Element.RGBA_8888(renderScript);
        }
        if (config == Bitmap.Config.RGB_565) {
            return Element.RGB_565(renderScript);
        }
        throw new RSInvalidStateException("Bad bitmap type: " + config);
    }

    static Type typeFromBitmap(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl) {
        Type.Builder builder = new Type.Builder(renderScript, elementFromBitmap(renderScript, bitmap));
        builder.setX(bitmap.getWidth());
        builder.setY(bitmap.getHeight());
        builder.setMipmaps(mipmapControl == MipmapControl.MIPMAP_FULL);
        return builder.create();
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl, int i) {
        try {
            Trace.traceBegin(32768L, "createFromBitmap");
            renderScript.validate();
            if (bitmap.getConfig() == null) {
                if ((i & 128) != 0) {
                    throw new RSIllegalArgumentException("USAGE_SHARED cannot be used with a Bitmap that has a null config.");
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                return createFromBitmap(renderScript, bitmapCreateBitmap, mipmapControl, i);
            }
            Type typeTypeFromBitmap = typeFromBitmap(renderScript, bitmap, mipmapControl);
            if (mipmapControl != MipmapControl.MIPMAP_NONE || !typeTypeFromBitmap.getElement().isCompatible(Element.RGBA_8888(renderScript)) || i != 131) {
                long jNAllocationCreateFromBitmap = renderScript.nAllocationCreateFromBitmap(typeTypeFromBitmap.getID(renderScript), mipmapControl.mID, bitmap, i);
                if (jNAllocationCreateFromBitmap != 0) {
                    return new Allocation(jNAllocationCreateFromBitmap, renderScript, typeTypeFromBitmap, true, i, mipmapControl);
                }
                throw new RSRuntimeException("Load failed.");
            }
            long jNAllocationCreateBitmapBackedAllocation = renderScript.nAllocationCreateBitmapBackedAllocation(typeTypeFromBitmap.getID(renderScript), mipmapControl.mID, bitmap, i);
            if (jNAllocationCreateBitmapBackedAllocation == 0) {
                throw new RSRuntimeException("Load failed.");
            }
            Allocation allocation = new Allocation(jNAllocationCreateBitmapBackedAllocation, renderScript, typeTypeFromBitmap, true, i, mipmapControl);
            allocation.setBitmap(bitmap);
            return allocation;
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    public ByteBuffer getByteBuffer() {
        if (this.mType.hasFaces()) {
            throw new RSInvalidStateException("Cubemap is not supported for getByteBuffer().");
        }
        if (this.mType.getYuv() == 17 || this.mType.getYuv() == 842094169 || this.mType.getYuv() == 35) {
            throw new RSInvalidStateException("YUV format is not supported for getByteBuffer().");
        }
        if (this.mByteBuffer == null || (this.mUsage & 32) != 0) {
            long[] jArr = new long[1];
            this.mByteBuffer = this.mRS.nAllocationGetByteBuffer(getID(this.mRS), jArr, this.mType.getX() * this.mType.getElement().getBytesSize(), this.mType.getY(), this.mType.getZ());
            this.mByteBufferStride = jArr[0];
        }
        if ((this.mUsage & 32) != 0) {
            return this.mByteBuffer.asReadOnlyBuffer();
        }
        return this.mByteBuffer;
    }

    public static Allocation[] createAllocations(RenderScript renderScript, Type type, int i, int i2) {
        try {
            Trace.traceBegin(32768L, "createAllocations");
            renderScript.validate();
            if (type.getID(renderScript) == 0) {
                throw new RSInvalidStateException("Bad Type");
            }
            Allocation[] allocationArr = new Allocation[i2];
            Allocation allocationCreateTyped = createTyped(renderScript, type, i);
            allocationArr[0] = allocationCreateTyped;
            if ((i & 32) != 0) {
                if (i2 > 16) {
                    allocationCreateTyped.destroy();
                    throw new RSIllegalArgumentException("Exceeds the max number of Allocations allowed: 16");
                }
                allocationCreateTyped.setupBufferQueue(i2);
            }
            for (int i3 = 1; i3 < i2; i3++) {
                allocationArr[i3] = createFromAllocation(renderScript, allocationArr[0]);
            }
            return allocationArr;
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    static Allocation createFromAllocation(RenderScript renderScript, Allocation allocation) {
        try {
            Trace.traceBegin(32768L, "createFromAllcation");
            renderScript.validate();
            if (allocation.getID(renderScript) == 0) {
                throw new RSInvalidStateException("Bad input Allocation");
            }
            Type type = allocation.getType();
            int usage = allocation.getUsage();
            MipmapControl mipmap = allocation.getMipmap();
            long jNAllocationCreateTyped = renderScript.nAllocationCreateTyped(type.getID(renderScript), mipmap.mID, usage, 0L);
            if (jNAllocationCreateTyped == 0) {
                throw new RSRuntimeException("Allocation creation failed.");
            }
            Allocation allocation2 = new Allocation(jNAllocationCreateTyped, renderScript, type, false, usage, mipmap);
            if ((usage & 32) != 0) {
                allocation2.shareBufferQueue(allocation);
            }
            return allocation2;
        } finally {
            Trace.traceEnd(32768L);
        }
    }

    void setupBufferQueue(int i) {
        this.mRS.validate();
        if ((this.mUsage & 32) == 0) {
            throw new RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
        }
        this.mRS.nAllocationSetupBufferQueue(getID(this.mRS), i);
    }

    void shareBufferQueue(Allocation allocation) {
        this.mRS.validate();
        if ((this.mUsage & 32) == 0) {
            throw new RSInvalidStateException("Allocation is not USAGE_IO_INPUT.");
        }
        this.mGetSurfaceSurface = allocation.getSurface();
        this.mRS.nAllocationShareBufferQueue(getID(this.mRS), allocation.getID(this.mRS));
    }

    public long getStride() {
        if (this.mByteBufferStride == -1) {
            getByteBuffer();
        }
        return this.mByteBufferStride;
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public Surface getSurface() {
        if ((this.mUsage & 32) == 0) {
            throw new RSInvalidStateException("Allocation is not a surface texture.");
        }
        if (this.mGetSurfaceSurface == null) {
            this.mGetSurfaceSurface = this.mRS.nAllocationGetSurface(getID(this.mRS));
        }
        return this.mGetSurfaceSurface;
    }

    public void setSurface(Surface surface) {
        this.mRS.validate();
        if ((this.mUsage & 64) == 0) {
            throw new RSInvalidStateException("Allocation is not USAGE_IO_OUTPUT.");
        }
        this.mRS.nAllocationSetSurface(getID(this.mRS), surface);
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return createFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 131);
        }
        return createFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap, MipmapControl mipmapControl, int i) {
        renderScript.validate();
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (width % 6 != 0) {
            throw new RSIllegalArgumentException("Cubemap height must be multiple of 6");
        }
        if (width / 6 != height) {
            throw new RSIllegalArgumentException("Only square cube map faces supported");
        }
        if (((height - 1) & height) != 0) {
            throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
        }
        Element elementElementFromBitmap = elementFromBitmap(renderScript, bitmap);
        Type.Builder builder = new Type.Builder(renderScript, elementElementFromBitmap);
        builder.setX(height);
        builder.setY(height);
        builder.setFaces(true);
        builder.setMipmaps(mipmapControl == MipmapControl.MIPMAP_FULL);
        Type typeCreate = builder.create();
        long jNAllocationCubeCreateFromBitmap = renderScript.nAllocationCubeCreateFromBitmap(typeCreate.getID(renderScript), mipmapControl.mID, bitmap, i);
        if (jNAllocationCubeCreateFromBitmap == 0) {
            throw new RSRuntimeException("Load failed for bitmap " + bitmap + " element " + elementElementFromBitmap);
        }
        return new Allocation(jNAllocationCubeCreateFromBitmap, renderScript, typeCreate, true, i, mipmapControl);
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap) {
        return createCubemapFromBitmap(renderScript, bitmap, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, MipmapControl mipmapControl, int i) {
        int height = bitmap.getHeight();
        if (bitmap.getWidth() != height || bitmap2.getWidth() != height || bitmap2.getHeight() != height || bitmap3.getWidth() != height || bitmap3.getHeight() != height || bitmap4.getWidth() != height || bitmap4.getHeight() != height || bitmap5.getWidth() != height || bitmap5.getHeight() != height || bitmap6.getWidth() != height || bitmap6.getHeight() != height) {
            throw new RSIllegalArgumentException("Only square cube map faces supported");
        }
        if (((height - 1) & height) != 0) {
            throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
        }
        Type.Builder builder = new Type.Builder(renderScript, elementFromBitmap(renderScript, bitmap));
        builder.setX(height);
        builder.setY(height);
        builder.setFaces(true);
        builder.setMipmaps(mipmapControl == MipmapControl.MIPMAP_FULL);
        Allocation allocationCreateTyped = createTyped(renderScript, builder.create(), mipmapControl, i);
        AllocationAdapter allocationAdapterCreate2D = AllocationAdapter.create2D(renderScript, allocationCreateTyped);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.POSITIVE_X);
        allocationAdapterCreate2D.copyFrom(bitmap);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.NEGATIVE_X);
        allocationAdapterCreate2D.copyFrom(bitmap2);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.POSITIVE_Y);
        allocationAdapterCreate2D.copyFrom(bitmap3);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.NEGATIVE_Y);
        allocationAdapterCreate2D.copyFrom(bitmap4);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.POSITIVE_Z);
        allocationAdapterCreate2D.copyFrom(bitmap5);
        allocationAdapterCreate2D.setFace(Type.CubemapFace.NEGATIVE_Z);
        allocationAdapterCreate2D.copyFrom(bitmap6);
        return allocationCreateTyped;
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6) {
        return createCubemapFromCubeFaces(renderScript, bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int i, MipmapControl mipmapControl, int i2) {
        renderScript.validate();
        if ((i2 & 224) != 0) {
            throw new RSIllegalArgumentException("Unsupported usage specified.");
        }
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(resources, i);
        Allocation allocationCreateFromBitmap = createFromBitmap(renderScript, bitmapDecodeResource, mipmapControl, i2);
        bitmapDecodeResource.recycle();
        return allocationCreateFromBitmap;
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int i) {
        if (renderScript.getApplicationContext().getApplicationInfo().targetSdkVersion >= 18) {
            return createFromBitmapResource(renderScript, resources, i, MipmapControl.MIPMAP_NONE, 3);
        }
        return createFromBitmapResource(renderScript, resources, i, MipmapControl.MIPMAP_NONE, 2);
    }

    public static Allocation createFromString(RenderScript renderScript, String str, int i) throws UnsupportedEncodingException {
        renderScript.validate();
        try {
            byte[] bytes = str.getBytes("UTF-8");
            Allocation allocationCreateSized = createSized(renderScript, Element.U8(renderScript), bytes.length, i);
            allocationCreateSized.copyFrom(bytes);
            return allocationCreateSized;
        } catch (Exception unused) {
            throw new RSRuntimeException("Could not convert string to utf-8.");
        }
    }

    public void setOnBufferAvailableListener(OnBufferAvailableListener onBufferAvailableListener) {
        synchronized (mAllocationMap) {
            mAllocationMap.put(new Long(getID(this.mRS)), this);
            this.mBufferNotifier = onBufferAvailableListener;
        }
    }

    static void sendBufferNotification(long j) {
        OnBufferAvailableListener onBufferAvailableListener;
        synchronized (mAllocationMap) {
            Allocation allocation = mAllocationMap.get(new Long(j));
            if (allocation != null && (onBufferAvailableListener = allocation.mBufferNotifier) != null) {
                onBufferAvailableListener.onBufferAvailable(allocation);
            }
        }
    }

    @Override // android.renderscript.BaseObj
    public void destroy() {
        if ((this.mUsage & 64) != 0) {
            setSurface(null);
        }
        Type type = this.mType;
        if (type != null && this.mOwningType) {
            type.destroy();
        }
        super.destroy();
    }
}
