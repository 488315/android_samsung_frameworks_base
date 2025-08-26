package android.graphics;

import android.content.pm.IPackageManager;
import android.graphics.ColorSpace;
import android.graphics.NinePatch;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.StrictMode;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Half;
import android.util.Log;
import android.view.ThreadedRenderer;
import com.android.libcore.readonly.Flags;
import dalvik.annotation.optimization.CriticalNative;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class Bitmap implements Parcelable {
    public static final int DENSITY_NONE = 0;
    private static final long NATIVE_ALLOCATION_SIZE = 32;
    private static final String TAG = "Bitmap";
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private static volatile int sDefaultDensity = -1;
    private ColorSpace mColorSpace;
    int mDensity;
    private Gainmap mGainmap;
    private WeakReference<HardwareBuffer> mHardwareBuffer;
    private int mHeight;
    private long mId;
    private final long mNativePtr;
    private byte[] mNinePatchChunk;
    private NinePatch.InsetStruct mNinePatchInsets;
    private boolean mRecycled;
    private boolean mRequestPremultiplied;
    private Object mTag;
    private int mWidth;
    private static final WeakHashMap<Bitmap, Void> sAllBitmaps = new WeakHashMap<>();
    private static DumpData dumpData = null;
    public static final Parcelable.Creator<Bitmap> CREATOR = new Parcelable.Creator<Bitmap>() { // from class: android.graphics.Bitmap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Bitmap createFromParcel(Parcel parcel) {
            Bitmap bitmapNativeCreateFromParcel = Bitmap.nativeCreateFromParcel(parcel);
            if (bitmapNativeCreateFromParcel == null) {
                throw new RuntimeException("Failed to unparcel Bitmap");
            }
            if (parcel.readBoolean()) {
                bitmapNativeCreateFromParcel.setGainmap((Gainmap) parcel.readTypedObject(Gainmap.CREATOR));
            }
            return bitmapNativeCreateFromParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Bitmap[] newArray(int i) {
            return new Bitmap[i];
        }
    };

    private static native boolean nativeCompress(long j, int i, int i2, OutputStream outputStream, byte[] bArr);

    private static native ColorSpace nativeComputeColorSpace(long j);

    private static native int nativeConfig(long j);

    private static native Bitmap nativeCopy(long j, int i, boolean z);

    private static native Bitmap nativeCopyAshmem(long j);

    private static native Bitmap nativeCopyAshmemConfig(long j, int i);

    private static native void nativeCopyPixelsFromBuffer(long j, Buffer buffer);

    private static native void nativeCopyPixelsToBuffer(long j, Buffer buffer);

    private static native Bitmap nativeCopyPreserveInternalConfig(long j);

    private static native Bitmap nativeCreate(int[] iArr, int i, int i2, int i3, int i4, int i5, boolean z, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native Bitmap nativeCreateFromParcel(Parcel parcel);

    private static native void nativeErase(long j, int i);

    private static native void nativeErase(long j, long j2, long j3);

    private static native Bitmap nativeExtractAlpha(long j, long j2, int[] iArr);

    private static native Gainmap nativeExtractGainmap(long j);

    private static native int nativeGenerationId(long j);

    private static native int nativeGetAllocationByteCount(long j);

    private static native int nativeGetAshmemFD(long j);

    private static native long nativeGetColor(long j, int i, int i2);

    private static native HardwareBuffer nativeGetHardwareBuffer(long j);

    private static native long nativeGetNativeFinalizer();

    private static native int nativeGetPixel(long j, int i, int i2);

    private static native void nativeGetPixels(long j, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native boolean nativeHasAlpha(long j);

    @CriticalNative
    private static native boolean nativeHasGainmap(long j);

    private static native boolean nativeHasMipMap(long j);

    @CriticalNative
    private static native boolean nativeIsBackedByAshmem(long j);

    @CriticalNative
    private static native boolean nativeIsImmutable(long j);

    private static native boolean nativeIsPremultiplied(long j);

    private static native boolean nativeIsSRGB(long j);

    private static native boolean nativeIsSRGBLinear(long j);

    private static native void nativePrepareToDraw(long j);

    private static native void nativeReconfigure(long j, int i, int i2, int i3, boolean z);

    private static native void nativeRecycle(long j);

    private static native int nativeRowBytes(long j);

    private static native boolean nativeSameAs(long j, long j2);

    private static native void nativeSetColorSpace(long j, long j2);

    private static native void nativeSetGainmap(long j, long j2);

    private static native void nativeSetHasAlpha(long j, boolean z, boolean z2);

    private static native void nativeSetHasMipMap(long j, boolean z);

    private static native void nativeSetImmutable(long j);

    private static native void nativeSetPixel(long j, int i, int i2, int i3);

    private static native void nativeSetPixels(long j, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native void nativeSetPremultiplied(long j, boolean z);

    private static native Bitmap nativeWrapHardwareBufferBitmap(HardwareBuffer hardwareBuffer, long j);

    private static native boolean nativeWriteToParcel(long j, int i, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static void setDefaultDensity(int i) {
        sDefaultDensity = i;
    }

    static int getDefaultDensity() {
        if (sDefaultDensity >= 0) {
            return sDefaultDensity;
        }
        sDefaultDensity = DisplayMetrics.DENSITY_DEVICE;
        return sDefaultDensity;
    }

    private static NativeAllocationRegistry getRegistry(boolean z, long j) {
        long jNativeGetNativeFinalizer = nativeGetNativeFinalizer();
        if (Flags.nativeMetrics()) {
            if (z) {
                return NativeAllocationRegistry.createMalloced(Bitmap.class, jNativeGetNativeFinalizer, j);
            }
            return NativeAllocationRegistry.createNonmalloced(Bitmap.class, jNativeGetNativeFinalizer, j);
        }
        ClassLoader classLoader = Bitmap.class.getClassLoader();
        if (z) {
            return NativeAllocationRegistry.createMalloced(classLoader, jNativeGetNativeFinalizer, j);
        }
        return NativeAllocationRegistry.createNonmalloced(classLoader, jNativeGetNativeFinalizer, j);
    }

    Bitmap(long j, int i, int i2, int i3, boolean z, byte[] bArr, NinePatch.InsetStruct insetStruct) {
        this(0L, j, i, i2, i3, z, bArr, insetStruct, true);
    }

    Bitmap(long j, long j2, int i, int i2, int i3, boolean z, byte[] bArr, NinePatch.InsetStruct insetStruct, boolean z2) {
        this.mDensity = getDefaultDensity();
        this.mTag = null;
        if (j2 == 0) {
            throw new RuntimeException("internal error: native bitmap is 0");
        }
        this.mId = j;
        this.mWidth = i;
        this.mHeight = i2;
        this.mRequestPremultiplied = z;
        this.mNinePatchChunk = bArr;
        this.mNinePatchInsets = insetStruct;
        if (i3 >= 0) {
            this.mDensity = i3;
        }
        this.mNativePtr = j2;
        getRegistry(z2, getAllocationByteCount()).registerNativeAllocation(this, j2);
        synchronized (Bitmap.class) {
            sAllBitmaps.put(this, null);
        }
    }

    public long getNativeInstance() {
        return this.mNativePtr;
    }

    void reinit(int i, int i2, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mRequestPremultiplied = z;
        this.mColorSpace = null;
    }

    public int getDensity() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getDensity() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mDensity;
    }

    public void setDensity(int i) {
        this.mDensity = i;
    }

    public void reconfigure(int i, int i2, Config config) {
        checkRecycled("Can't call reconfigure() on a recycled bitmap");
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        if (!isMutable()) {
            throw new IllegalStateException("only mutable bitmaps may be reconfigured");
        }
        nativeReconfigure(this.mNativePtr, i, i2, config.nativeInt, this.mRequestPremultiplied);
        this.mWidth = i;
        this.mHeight = i2;
        this.mColorSpace = null;
    }

    public void setWidth(int i) {
        reconfigure(i, getHeight(), getConfig());
    }

    public void setHeight(int i) {
        reconfigure(getWidth(), i, getConfig());
    }

    public void setConfig(Config config) {
        reconfigure(getWidth(), getHeight(), config);
    }

    private void setNinePatchChunk(byte[] bArr) {
        this.mNinePatchChunk = bArr;
    }

    public void recycle() {
        if (this.mRecycled) {
            return;
        }
        nativeRecycle(this.mNativePtr);
        this.mNinePatchChunk = null;
        this.mRecycled = true;
        this.mHardwareBuffer = null;
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    public int getGenerationId() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getGenerationId() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeGenerationId(this.mNativePtr);
    }

    void checkRecycled(String str) {
        if (this.mRecycled) {
            throw new IllegalStateException(str);
        }
    }

    private void checkHardware(String str) {
        if (getConfig() == Config.HARDWARE) {
            throw new IllegalStateException(str);
        }
    }

    private static void checkXYSign(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("x must be >= 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("y must be >= 0");
        }
    }

    private static void checkWidthHeight(int i, int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("width must be > 0");
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("height must be > 0");
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v0 android.graphics.Bitmap$Config, still in use, count: 1, list:
      (r1v0 android.graphics.Bitmap$Config) from 0x0056: FILLED_NEW_ARRAY 
      (null android.graphics.Bitmap$Config)
      (r1v0 android.graphics.Bitmap$Config)
      (null android.graphics.Bitmap$Config)
      (r3v1 android.graphics.Bitmap$Config)
      (r4v1 android.graphics.Bitmap$Config)
      (r5v1 android.graphics.Bitmap$Config)
      (r6v1 android.graphics.Bitmap$Config)
      (r7v1 android.graphics.Bitmap$Config)
      (r8v1 android.graphics.Bitmap$Config)
     A[WRAPPED] (LINE:614) elemType: android.graphics.Bitmap$Config
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class Config {
        ALPHA_8(1),
        RGB_565(3),
        ARGB_4444(4),
        ARGB_8888(5),
        RGBA_F16(6),
        HARDWARE(7),
        RGBA_1010102(8);

        private static Config[] sConfigs = {null, new Config(1), null, new Config(3), new Config(4), new Config(5), new Config(6), new Config(7), new Config(8)};
        final int nativeInt;

        public static Config valueOf(String str) {
            return (Config) Enum.valueOf(Config.class, str);
        }

        public static Config[] values() {
            return (Config[]) $VALUES.clone();
        }

        static {
        }

        private Config(int i) {
            this.nativeInt = i;
        }

        static Config nativeToConfig(int i) {
            return sConfigs[i];
        }
    }

    public void copyPixelsToBuffer(Buffer buffer) {
        char c;
        checkHardware("unable to copyPixelsToBuffer, pixel access is not supported on Config#HARDWARE bitmaps");
        int iRemaining = buffer.remaining();
        if (buffer instanceof ByteBuffer) {
            c = 0;
        } else if (buffer instanceof ShortBuffer) {
            c = 1;
        } else {
            if (!(buffer instanceof IntBuffer)) {
                throw new RuntimeException("unsupported Buffer subclass");
            }
            c = 2;
        }
        long j = iRemaining << c;
        long byteCount = getByteCount();
        if (j < byteCount) {
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsToBuffer(this.mNativePtr, buffer);
        buffer.position((int) (buffer.position() + (byteCount >> c)));
    }

    public void copyPixelsFromBuffer(Buffer buffer) {
        char c;
        checkRecycled("copyPixelsFromBuffer called on recycled bitmap");
        checkHardware("unable to copyPixelsFromBuffer, Config#HARDWARE bitmaps are immutable");
        int iRemaining = buffer.remaining();
        if (buffer instanceof ByteBuffer) {
            c = 0;
        } else if (buffer instanceof ShortBuffer) {
            c = 1;
        } else {
            if (!(buffer instanceof IntBuffer)) {
                throw new RuntimeException("unsupported Buffer subclass");
            }
            c = 2;
        }
        long j = iRemaining << c;
        long byteCount = getByteCount();
        if (j < byteCount) {
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsFromBuffer(this.mNativePtr, buffer);
        buffer.position((int) (buffer.position() + (byteCount >> c)));
    }

    private void noteHardwareBitmapSlowCall() {
        if (getConfig() == Config.HARDWARE) {
            StrictMode.noteSlowCall("Warning: attempt to read pixels from hardware bitmap, which is very slow operation");
        }
    }

    public Bitmap copy(Config config, boolean z) {
        checkRecycled("Can't copy a recycled bitmap");
        if (config == Config.HARDWARE && z) {
            throw new IllegalArgumentException("Hardware bitmaps are always immutable");
        }
        noteHardwareBitmapSlowCall();
        Bitmap bitmapNativeCopy = nativeCopy(this.mNativePtr, config.nativeInt, z);
        if (bitmapNativeCopy != null) {
            bitmapNativeCopy.setPremultiplied(this.mRequestPremultiplied);
            bitmapNativeCopy.mDensity = this.mDensity;
        }
        return bitmapNativeCopy;
    }

    public Bitmap createAshmemBitmap() {
        checkRecycled("Can't copy a recycled bitmap");
        noteHardwareBitmapSlowCall();
        Bitmap bitmapNativeCopyAshmem = nativeCopyAshmem(this.mNativePtr);
        if (bitmapNativeCopyAshmem != null) {
            bitmapNativeCopyAshmem.setPremultiplied(this.mRequestPremultiplied);
            bitmapNativeCopyAshmem.mDensity = this.mDensity;
            if (hasGainmap()) {
                bitmapNativeCopyAshmem.setGainmap(getGainmap().asShared());
            }
        }
        return bitmapNativeCopyAshmem;
    }

    public Bitmap asShared() {
        if (nativeIsBackedByAshmem(this.mNativePtr) && nativeIsImmutable(this.mNativePtr) && (!hasGainmap() || getGainmap().asShared() == getGainmap())) {
            return this;
        }
        Bitmap bitmapCreateAshmemBitmap = createAshmemBitmap();
        if (bitmapCreateAshmemBitmap != null) {
            return bitmapCreateAshmemBitmap;
        }
        throw new RuntimeException("Failed to create shared Bitmap!");
    }

    public SharedMemory getSharedMemory() {
        checkRecycled("Cannot access shared memory of a recycled bitmap");
        if (!nativeIsBackedByAshmem(this.mNativePtr)) {
            return null;
        }
        try {
            return SharedMemory.fromFileDescriptor(ParcelFileDescriptor.fromFd(nativeGetAshmemFD(this.mNativePtr)));
        } catch (IOException unused) {
            Log.e(TAG, "Unable to create dup'd file descriptor for shared bitmap memory");
            return null;
        }
    }

    public static Bitmap wrapHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        long usage = hardwareBuffer.getUsage();
        if ((256 & usage) == 0) {
            throw new IllegalArgumentException("usage flags must contain USAGE_GPU_SAMPLED_IMAGE.");
        }
        if ((usage & 16384) != 0) {
            throw new IllegalArgumentException("Bitmap is not compatible with protected buffers");
        }
        if (colorSpace == null) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        Bitmap bitmapNativeWrapHardwareBufferBitmap = nativeWrapHardwareBufferBitmap(hardwareBuffer, colorSpace.getNativeInstance());
        if (bitmapNativeWrapHardwareBufferBitmap != null) {
            bitmapNativeWrapHardwareBufferBitmap.mHardwareBuffer = new WeakReference<>(hardwareBuffer);
        }
        return bitmapNativeWrapHardwareBufferBitmap;
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int i2, boolean z) {
        Matrix matrix = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != i || height != i2) {
            matrix.setScale(i / width, i2 / height);
        }
        return createBitmap(bitmap, 0, 0, width, height, matrix, z);
    }

    public static Bitmap createBitmap(Bitmap bitmap) {
        return createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) {
        return createBitmap(bitmap, i, i2, i3, i4, (Matrix) null, false);
    }

    public static Bitmap createBitmap(Bitmap bitmap, int i, int i2, int i3, int i4, Matrix matrix, boolean z) {
        Bitmap bitmapCreateBitmap;
        int i5;
        int i6;
        Paint paint;
        Bitmap bitmapTransformGainmap;
        Bitmap bitmapNativeCopyPreserveInternalConfig = bitmap;
        checkXYSign(i, i2);
        checkWidthHeight(i3, i4);
        int i7 = i + i3;
        if (i7 > bitmapNativeCopyPreserveInternalConfig.getWidth()) {
            throw new IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        int i8 = i2 + i4;
        if (i8 > bitmapNativeCopyPreserveInternalConfig.getHeight()) {
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (bitmapNativeCopyPreserveInternalConfig.isRecycled()) {
            throw new IllegalArgumentException("cannot use a recycled source in createBitmap");
        }
        if (!bitmapNativeCopyPreserveInternalConfig.isMutable() && i == 0 && i2 == 0 && i3 == bitmapNativeCopyPreserveInternalConfig.getWidth() && i4 == bitmapNativeCopyPreserveInternalConfig.getHeight() && (matrix == null || matrix.isIdentity())) {
            return bitmapNativeCopyPreserveInternalConfig;
        }
        boolean z2 = bitmapNativeCopyPreserveInternalConfig.getConfig() == Config.HARDWARE;
        if (z2) {
            bitmapNativeCopyPreserveInternalConfig.noteHardwareBitmapSlowCall();
            bitmapNativeCopyPreserveInternalConfig = nativeCopyPreserveInternalConfig(bitmapNativeCopyPreserveInternalConfig.mNativePtr);
        }
        Bitmap bitmap2 = bitmapNativeCopyPreserveInternalConfig;
        Rect rect = new Rect(i, i2, i7, i8);
        RectF rectF = new RectF(0.0f, 0.0f, i3, i4);
        RectF rectF2 = new RectF();
        Config config = Config.ARGB_8888;
        Config config2 = bitmap2.getConfig();
        if (config2 != null) {
            int iOrdinal = config2.ordinal();
            if (iOrdinal == 0) {
                config = Config.ALPHA_8;
            } else if (iOrdinal == 1) {
                config = Config.RGB_565;
            } else if (iOrdinal == 4) {
                config = Config.RGBA_F16;
            } else {
                config = Config.ARGB_8888;
            }
        }
        ColorSpace colorSpace = bitmap2.getColorSpace();
        if (matrix == null || matrix.isIdentity()) {
            bitmapCreateBitmap = createBitmap((DisplayMetrics) null, i3, i4, config, bitmap2.hasAlpha(), colorSpace);
            i5 = i3;
            i6 = i4;
            paint = null;
        } else {
            boolean zRectStaysRect = matrix.rectStaysRect();
            matrix.mapRect(rectF2, rectF);
            int iRound = Math.round(rectF2.width());
            int iRound2 = Math.round(rectF2.height());
            if (!zRectStaysRect && config != Config.ARGB_8888 && config != Config.RGBA_F16) {
                config = Config.ARGB_8888;
                if (colorSpace == null) {
                    colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
                }
            }
            bitmapCreateBitmap = createBitmap((DisplayMetrics) null, iRound, iRound2, config, !zRectStaysRect || bitmap2.hasAlpha(), colorSpace);
            Paint paint2 = new Paint();
            paint2.setFilterBitmap(z);
            if (!zRectStaysRect) {
                paint2.setAntiAlias(true);
            }
            paint = paint2;
            i5 = iRound;
            i6 = iRound2;
        }
        Bitmap bitmap3 = bitmapCreateBitmap;
        bitmap3.mDensity = bitmap2.mDensity;
        bitmap3.setHasAlpha(bitmap2.hasAlpha());
        bitmap3.setPremultiplied(bitmap2.mRequestPremultiplied);
        Canvas canvas = new Canvas(bitmap3);
        canvas.translate(-rectF2.left, -rectF2.top);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap2, rect, rectF, paint);
        canvas.setBitmap(null);
        if (bitmap2.hasGainmap() && (bitmapTransformGainmap = transformGainmap(bitmap2, matrix, i5, i6, paint, rect, rectF, rectF2)) != null) {
            bitmap3.setGainmap(new Gainmap(bitmap2.getGainmap(), bitmapTransformGainmap));
        }
        return z2 ? bitmap3.copy(Config.HARDWARE, false) : bitmap3;
    }

    private static Bitmap transformGainmap(Bitmap bitmap, Matrix matrix, int i, int i2, Paint paint, Rect rect, RectF rectF, RectF rectF2) {
        Bitmap gainmapContents = bitmap.getGainmap().getGainmapContents();
        float width = gainmapContents.getWidth() / bitmap.getWidth();
        float height = gainmapContents.getHeight() / bitmap.getHeight();
        int iRound = Math.round(i * width);
        int iRound2 = Math.round(i2 * height);
        if (iRound == 0 || iRound2 == 0) {
            return null;
        }
        Rect rect2 = new Rect((int) (rect.left * width), (int) (rect.top * height), (int) (rect.right * width), (int) (rect.bottom * height));
        Bitmap bitmapNativeCreate = nativeCreate(null, 0, iRound, iRound, iRound2, gainmapContents.getConfig().nativeInt, true, 0L);
        bitmapNativeCreate.eraseColor(0);
        Canvas canvas = new Canvas(bitmapNativeCreate);
        canvas.scale(width, height);
        canvas.translate(-rectF2.left, -rectF2.top);
        canvas.concat(matrix);
        canvas.drawBitmap(gainmapContents, rect2, rectF, paint);
        canvas.setBitmap(null);
        return bitmapNativeCreate;
    }

    public static Bitmap createBitmap(int i, int i2, Config config) {
        return createBitmap(i, i2, config, true);
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int i, int i2, Config config) {
        return createBitmap(displayMetrics, i, i2, config, true);
    }

    public static Bitmap createBitmap(int i, int i2, Config config, boolean z) {
        return createBitmap((DisplayMetrics) null, i, i2, config, z);
    }

    public static Bitmap createBitmap(int i, int i2, Config config, boolean z, ColorSpace colorSpace) {
        return createBitmap((DisplayMetrics) null, i, i2, config, z, colorSpace);
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int i, int i2, Config config, boolean z) {
        return createBitmap(displayMetrics, i, i2, config, z, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int i, int i2, Config config, boolean z, ColorSpace colorSpace) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        if (config == Config.HARDWARE) {
            throw new IllegalArgumentException("can't create mutable bitmap with Config.HARDWARE");
        }
        if (colorSpace == null && config != Config.ALPHA_8) {
            throw new IllegalArgumentException("can't create bitmap without a color space");
        }
        Bitmap bitmapNativeCreate = nativeCreate(null, 0, i, i, i2, config.nativeInt, true, colorSpace == null ? 0L : colorSpace.getNativeInstance());
        if (displayMetrics != null) {
            bitmapNativeCreate.mDensity = displayMetrics.densityDpi;
        }
        bitmapNativeCreate.setHasAlpha(z);
        if ((config == Config.ARGB_8888 || config == Config.RGBA_F16) && !z) {
            nativeErase(bitmapNativeCreate.mNativePtr, -16777216);
        }
        return bitmapNativeCreate;
    }

    public static Bitmap createBitmap(int[] iArr, int i, int i2, int i3, int i4, Config config) {
        return createBitmap((DisplayMetrics) null, iArr, i, i2, i3, i4, config);
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int[] iArr, int i, int i2, int i3, int i4, Config config) {
        checkWidthHeight(i3, i4);
        if (Math.abs(i2) < i3) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int i5 = ((i4 - 1) * i2) + i;
        int length = iArr.length;
        if (i < 0 || i + i3 > length || i5 < 0 || i5 + i3 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (i3 <= 0 || i4 <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        Bitmap bitmapNativeCreate = nativeCreate(iArr, i, i2, i3, i4, config.nativeInt, false, ColorSpace.get(ColorSpace.Named.SRGB).getNativeInstance());
        if (displayMetrics != null) {
            bitmapNativeCreate.mDensity = displayMetrics.densityDpi;
        }
        return bitmapNativeCreate;
    }

    public static Bitmap createBitmap(int[] iArr, int i, int i2, Config config) {
        return createBitmap((DisplayMetrics) null, iArr, 0, i, i, i2, config);
    }

    public static Bitmap createBitmap(DisplayMetrics displayMetrics, int[] iArr, int i, int i2, Config config) {
        return createBitmap(displayMetrics, iArr, 0, i, i, i2, config);
    }

    public static Bitmap createBitmap(Picture picture) {
        return createBitmap(picture, picture.getWidth(), picture.getHeight(), Config.HARDWARE);
    }

    public static Bitmap createBitmap(Picture picture, int i, int i2, Config config) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("width & height must be > 0");
        }
        if (config == null) {
            throw new IllegalArgumentException("Config must not be null");
        }
        picture.endRecording();
        if (picture.requiresHardwareAcceleration() && config != Config.HARDWARE) {
            StrictMode.noteSlowCall("GPU readback");
        }
        if (config == Config.HARDWARE || picture.requiresHardwareAcceleration()) {
            RenderNode renderNodeCreate = RenderNode.create("BitmapTemporary", null);
            renderNodeCreate.setLeftTopRightBottom(0, 0, i, i2);
            renderNodeCreate.setClipToBounds(false);
            renderNodeCreate.setForceDarkAllowed(false);
            RecordingCanvas recordingCanvasBeginRecording = renderNodeCreate.beginRecording(i, i2);
            if (picture.getWidth() != i || picture.getHeight() != i2) {
                recordingCanvasBeginRecording.scale(i / picture.getWidth(), i2 / picture.getHeight());
            }
            recordingCanvasBeginRecording.drawPicture(picture);
            renderNodeCreate.endRecording();
            Bitmap bitmapCreateHardwareBitmap = ThreadedRenderer.createHardwareBitmap(renderNodeCreate, i, i2);
            return config != Config.HARDWARE ? bitmapCreateHardwareBitmap.copy(config, false) : bitmapCreateHardwareBitmap;
        }
        Bitmap bitmapCreateBitmap = createBitmap(i, i2, config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        if (picture.getWidth() != i || picture.getHeight() != i2) {
            canvas.scale(i / picture.getWidth(), i2 / picture.getHeight());
        }
        canvas.drawPicture(picture);
        canvas.setBitmap(null);
        bitmapCreateBitmap.setImmutable();
        return bitmapCreateBitmap;
    }

    public byte[] getNinePatchChunk() {
        return this.mNinePatchChunk;
    }

    public void getOpticalInsets(Rect rect) {
        NinePatch.InsetStruct insetStruct = this.mNinePatchInsets;
        if (insetStruct == null) {
            rect.setEmpty();
        } else {
            rect.set(insetStruct.opticalRect);
        }
    }

    public NinePatch.InsetStruct getNinePatchInsets() {
        return this.mNinePatchInsets;
    }

    public enum CompressFormat {
        JPEG(0),
        PNG(1),
        WEBP(2),
        WEBP_LOSSY(3),
        WEBP_LOSSLESS(4);

        final int nativeInt;

        CompressFormat(int i) {
            this.nativeInt = i;
        }
    }

    private static final class DumpData {
        private byte[][] buffers;
        private int count = 0;
        private int format;
        private int max;
        private long[] natives;

        public DumpData(CompressFormat compressFormat, int i) {
            this.max = i;
            this.format = compressFormat.nativeInt;
            this.natives = new long[i];
            this.buffers = new byte[i][];
        }

        public void add(long j, byte[] bArr) {
            long[] jArr = this.natives;
            int i = this.count;
            jArr[i] = j;
            this.buffers[i] = bArr;
            int i2 = this.max;
            if (i < i2) {
                i2 = i + 1;
            }
            this.count = i2;
        }

        public int size() {
            return this.count;
        }
    }

    public static void dumpAll(String str) {
        CompressFormat compressFormat;
        ArrayList arrayList;
        if (str == null) {
            dumpData = null;
            return;
        }
        if (str.equals("jpg") || str.equals("jpeg")) {
            compressFormat = CompressFormat.JPEG;
        } else if (str.equals("png")) {
            compressFormat = CompressFormat.PNG;
        } else if (str.equals("webp")) {
            compressFormat = CompressFormat.WEBP_LOSSLESS;
        } else {
            Log.w(TAG, "No bitmaps dumped: unrecognized format " + str);
            return;
        }
        synchronized (Bitmap.class) {
            WeakHashMap<Bitmap, Void> weakHashMap = sAllBitmaps;
            arrayList = new ArrayList(weakHashMap.size());
            for (Bitmap bitmap : weakHashMap.keySet()) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    arrayList.add(bitmap);
                }
            }
        }
        dumpData = new DumpData(compressFormat, arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Bitmap bitmap2 = (Bitmap) it.next();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (bitmap2.compress(compressFormat, 90, byteArrayOutputStream)) {
                dumpData.add(bitmap2.getNativeInstance(), byteArrayOutputStream.toByteArray());
            }
        }
        Log.i(TAG, dumpData.size() + "/" + arrayList.size() + " bitmaps dumped");
    }

    public boolean compress(CompressFormat compressFormat, int i, OutputStream outputStream) {
        String nameForUid;
        checkRecycled("Can't compress a recycled bitmap");
        outputStream.getClass();
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("quality must be 0..100");
        }
        try {
            nameForUid = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).getNameForUid(Process.myUid());
        } catch (RemoteException e) {
            e.printStackTrace();
            nameForUid = "";
        }
        if (nameForUid != null && nameForUid.contains("com.tencent.mm") && i <= 70) {
            i = 85;
        }
        StrictMode.noteSlowCall("Compression of a bitmap is slow");
        Trace.traceBegin(8192L, "Bitmap.compress");
        boolean zNativeCompress = nativeCompress(this.mNativePtr, compressFormat.nativeInt, i, outputStream, new byte[4096]);
        Trace.traceEnd(8192L);
        return zNativeCompress;
    }

    public final boolean isMutable() {
        return !nativeIsImmutable(this.mNativePtr);
    }

    private void setImmutable() {
        if (isMutable()) {
            nativeSetImmutable(this.mNativePtr);
        }
    }

    public final boolean isPremultiplied() {
        if (this.mRecycled) {
            Log.w(TAG, "Called isPremultiplied() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeIsPremultiplied(this.mNativePtr);
    }

    public final void setPremultiplied(boolean z) {
        checkRecycled("setPremultiplied called on a recycled bitmap");
        this.mRequestPremultiplied = z;
        nativeSetPremultiplied(this.mNativePtr, z);
    }

    public final int getWidth() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getWidth() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mWidth;
    }

    public final int getHeight() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getHeight() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mHeight;
    }

    public int getScaledWidth(Canvas canvas) {
        return scaleFromDensity(getWidth(), this.mDensity, canvas.mDensity);
    }

    public int getScaledHeight(Canvas canvas) {
        return scaleFromDensity(getHeight(), this.mDensity, canvas.mDensity);
    }

    public int getScaledWidth(DisplayMetrics displayMetrics) {
        return scaleFromDensity(getWidth(), this.mDensity, displayMetrics.densityDpi);
    }

    public int getScaledHeight(DisplayMetrics displayMetrics) {
        return scaleFromDensity(getHeight(), this.mDensity, displayMetrics.densityDpi);
    }

    public int getScaledWidth(int i) {
        return scaleFromDensity(getWidth(), this.mDensity, i);
    }

    public int getScaledHeight(int i) {
        return scaleFromDensity(getHeight(), this.mDensity, i);
    }

    public static int scaleFromDensity(int i, int i2, int i3) {
        return (i2 == 0 || i3 == 0 || i2 == i3) ? i : ((i * i3) + (i2 >> 1)) / i2;
    }

    public final int getRowBytes() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getRowBytes() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeRowBytes(this.mNativePtr);
    }

    public final int getByteCount() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return getRowBytes() * getHeight();
    }

    public final int getAllocationByteCount() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getAllocationByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return nativeGetAllocationByteCount(this.mNativePtr);
    }

    public final Config getConfig() {
        if (this.mRecycled) {
            Log.w(TAG, "Called getConfig() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Config.nativeToConfig(nativeConfig(this.mNativePtr));
    }

    public final boolean hasAlpha() {
        if (this.mRecycled) {
            Log.w(TAG, "Called hasAlpha() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasAlpha(this.mNativePtr);
    }

    public void setHasAlpha(boolean z) {
        checkRecycled("setHasAlpha called on a recycled bitmap");
        nativeSetHasAlpha(this.mNativePtr, z, this.mRequestPremultiplied);
    }

    public final boolean hasMipMap() {
        if (this.mRecycled) {
            Log.w(TAG, "Called hasMipMap() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasMipMap(this.mNativePtr);
    }

    public final void setHasMipMap(boolean z) {
        checkRecycled("setHasMipMap called on a recycled bitmap");
        nativeSetHasMipMap(this.mNativePtr, z);
    }

    public final ColorSpace getColorSpace() {
        checkRecycled("getColorSpace called on a recycled bitmap");
        if (this.mColorSpace == null) {
            this.mColorSpace = nativeComputeColorSpace(this.mNativePtr);
        }
        return this.mColorSpace;
    }

    public void setColorSpace(ColorSpace colorSpace) {
        checkRecycled("setColorSpace called on a recycled bitmap");
        if (colorSpace == null) {
            throw new IllegalArgumentException("The colorSpace cannot be set to null");
        }
        if (getConfig() == Config.ALPHA_8) {
            throw new IllegalArgumentException("Cannot set a ColorSpace on ALPHA_8");
        }
        ColorSpace colorSpace2 = getColorSpace();
        nativeSetColorSpace(this.mNativePtr, colorSpace.getNativeInstance());
        this.mColorSpace = null;
        ColorSpace colorSpace3 = getColorSpace();
        try {
            if (colorSpace2.getComponentCount() != colorSpace3.getComponentCount()) {
                throw new IllegalArgumentException("The new ColorSpace must have the same component count as the current ColorSpace");
            }
            for (int i = 0; i < colorSpace2.getComponentCount(); i++) {
                if (colorSpace2.getMinValue(i) < colorSpace3.getMinValue(i)) {
                    throw new IllegalArgumentException("The new ColorSpace cannot increase the minimum value for any of the components compared to the current ColorSpace. To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
                if (colorSpace2.getMaxValue(i) > colorSpace3.getMaxValue(i)) {
                    throw new IllegalArgumentException("The new ColorSpace cannot decrease the maximum value for any of the components compared to the current ColorSpace/ To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
            }
        } catch (IllegalArgumentException e) {
            this.mColorSpace = colorSpace2;
            nativeSetColorSpace(this.mNativePtr, colorSpace2.getNativeInstance());
            throw e;
        }
    }

    public boolean hasGainmap() {
        checkRecycled("Bitmap is recycled");
        return nativeHasGainmap(this.mNativePtr);
    }

    public Gainmap getGainmap() {
        checkRecycled("Bitmap is recycled");
        if (this.mGainmap == null) {
            this.mGainmap = nativeExtractGainmap(this.mNativePtr);
        }
        return this.mGainmap;
    }

    public void setGainmap(Gainmap gainmap) {
        checkRecycled("Bitmap is recycled");
        this.mGainmap = gainmap;
        nativeSetGainmap(this.mNativePtr, gainmap == null ? 0L : gainmap.mNativePtr);
    }

    public void eraseColor(int i) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException("cannot erase immutable bitmaps");
        }
        nativeErase(this.mNativePtr, i);
    }

    public void eraseColor(long j) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException("cannot erase immutable bitmaps");
        }
        nativeErase(this.mNativePtr, Color.colorSpace(j).getNativeInstance(), j);
    }

    public int getPixel(int i, int i2) {
        checkRecycled("Can't call getPixel() on a recycled bitmap");
        checkHardware("unable to getPixel(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(i, i2);
        return nativeGetPixel(this.mNativePtr, i, i2);
    }

    private static float clamp(float f, ColorSpace colorSpace, int i) {
        return Math.max(Math.min(f, colorSpace.getMaxValue(i)), colorSpace.getMinValue(i));
    }

    public Color getColor(int i, int i2) {
        checkRecycled("Can't call getColor() on a recycled bitmap");
        checkHardware("unable to getColor(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(i, i2);
        ColorSpace colorSpace = getColorSpace();
        if (colorSpace == null || colorSpace.equals(ColorSpace.get(ColorSpace.Named.SRGB))) {
            return Color.valueOf(nativeGetPixel(this.mNativePtr, i, i2));
        }
        long jNativeGetColor = nativeGetColor(this.mNativePtr, i, i2);
        return Color.valueOf(clamp(Half.toFloat((short) (jNativeGetColor & 65535)), colorSpace, 0), clamp(Half.toFloat((short) ((jNativeGetColor >> 16) & 65535)), colorSpace, 1), clamp(Half.toFloat((short) ((jNativeGetColor >> 32) & 65535)), colorSpace, 2), Half.toFloat((short) ((jNativeGetColor >> 48) & 65535)), colorSpace);
    }

    public void getPixels(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        checkRecycled("Can't call getPixels() on a recycled bitmap");
        checkHardware("unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps");
        if (i5 == 0 || i6 == 0) {
            return;
        }
        checkPixelsAccess(i3, i4, i5, i6, i, i2, iArr);
        nativeGetPixels(this.mNativePtr, iArr, i, i2, i3, i4, i5, i6);
    }

    private void checkPixelAccess(int i, int i2) {
        checkXYSign(i, i2);
        if (i >= getWidth()) {
            throw new IllegalArgumentException("x must be < bitmap.width()");
        }
        if (i2 >= getHeight()) {
            throw new IllegalArgumentException("y must be < bitmap.height()");
        }
    }

    private void checkPixelsAccess(int i, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        checkXYSign(i, i2);
        if (i3 < 0) {
            throw new IllegalArgumentException("width must be >= 0");
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("height must be >= 0");
        }
        if (i + i3 > getWidth()) {
            throw new IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        if (i2 + i4 > getHeight()) {
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (Math.abs(i6) < i3) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int i7 = ((i4 - 1) * i6) + i5;
        int length = iArr.length;
        if (i5 < 0 || i5 + i3 > length || i7 < 0 || i7 + i3 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setPixel(int i, int i2, int i3) {
        checkRecycled("Can't call setPixel() on a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException();
        }
        checkPixelAccess(i, i2);
        nativeSetPixel(this.mNativePtr, i, i2, i3);
    }

    public void setPixels(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        checkRecycled("Can't call setPixels() on a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException();
        }
        if (i5 == 0 || i6 == 0) {
            return;
        }
        checkPixelsAccess(i3, i4, i5, i6, i, i2, iArr);
        nativeSetPixels(this.mNativePtr, iArr, i, i2, i3, i4, i5, i6);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        checkRecycled("Can't parcel a recycled bitmap");
        noteHardwareBitmapSlowCall();
        if (!nativeWriteToParcel(this.mNativePtr, this.mDensity, parcel)) {
            throw new RuntimeException("native writeToParcel failed");
        }
        if (hasGainmap()) {
            parcel.writeBoolean(true);
            parcel.writeTypedObject(this.mGainmap, i);
        } else {
            parcel.writeBoolean(false);
        }
    }

    public Bitmap extractAlpha() {
        return extractAlpha(null, null);
    }

    public Bitmap extractAlpha(Paint paint, int[] iArr) {
        checkRecycled("Can't extractAlpha on a recycled bitmap");
        long nativeInstance = paint != null ? paint.getNativeInstance() : 0L;
        noteHardwareBitmapSlowCall();
        Bitmap bitmapNativeExtractAlpha = nativeExtractAlpha(this.mNativePtr, nativeInstance, iArr);
        if (bitmapNativeExtractAlpha == null) {
            throw new RuntimeException("Failed to extractAlpha on Bitmap");
        }
        bitmapNativeExtractAlpha.mDensity = this.mDensity;
        return bitmapNativeExtractAlpha;
    }

    public boolean sameAs(Bitmap bitmap) {
        StrictMode.noteSlowCall("sameAs compares pixel data, not expected to be fast");
        checkRecycled("Can't call sameAs on a recycled bitmap!");
        if (this == bitmap) {
            return true;
        }
        if (bitmap == null) {
            return false;
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("Can't compare to a recycled bitmap!");
        }
        return nativeSameAs(this.mNativePtr, bitmap.mNativePtr);
    }

    public void prepareToDraw() {
        checkRecycled("Can't prepareToDraw on a recycled bitmap!");
        nativePrepareToDraw(this.mNativePtr);
    }

    public HardwareBuffer getHardwareBuffer() {
        checkRecycled("Can't getHardwareBuffer from a recycled bitmap");
        WeakReference<HardwareBuffer> weakReference = this.mHardwareBuffer;
        HardwareBuffer hardwareBuffer = weakReference == null ? null : weakReference.get();
        if (hardwareBuffer != null && !hardwareBuffer.isClosed()) {
            return hardwareBuffer;
        }
        HardwareBuffer hardwareBufferNativeGetHardwareBuffer = nativeGetHardwareBuffer(this.mNativePtr);
        this.mHardwareBuffer = new WeakReference<>(hardwareBufferNativeGetHardwareBuffer);
        return hardwareBufferNativeGetHardwareBuffer;
    }

    public void semSetTag(Object obj) {
        this.mTag = obj;
    }

    public Object semGetTag() {
        return this.mTag;
    }
}
