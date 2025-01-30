package android.graphics;

import android.content.p002pm.IPackageManager;
import android.graphics.ColorSpace;
import android.graphics.NinePatch;
import android.hardware.HardwareBuffer;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Parcelable;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SharedMemory;
import android.p009os.StrictMode;
import android.p009os.Trace;
import android.util.DisplayMetrics;
import android.util.Half;
import android.util.Log;
import android.view.ThreadedRenderer;
import dalvik.annotation.optimization.CriticalNative;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class Bitmap implements Parcelable {
    public static final int DENSITY_NONE = 0;
    private static final long NATIVE_ALLOCATION_SIZE = 32;
    private static final String TAG = "Bitmap";
    private static final int WORKING_COMPRESS_STORAGE = 4096;
    private ColorSpace mColorSpace;
    int mDensity;
    private Gainmap mGainmap;
    private WeakReference<HardwareBuffer> mHardwareBuffer;
    private int mHeight;
    private final long mNativePtr;
    private byte[] mNinePatchChunk;
    private NinePatch.InsetStruct mNinePatchInsets;
    private boolean mRecycled;
    private boolean mRequestPremultiplied;
    private Object mTag;
    private int mWidth;
    private static volatile int sDefaultDensity = -1;
    public static final Parcelable.Creator<Bitmap> CREATOR = new Parcelable.Creator<Bitmap>() { // from class: android.graphics.Bitmap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Bitmap createFromParcel(Parcel p) {
            Bitmap bm = Bitmap.nativeCreateFromParcel(p);
            if (bm == null) {
                throw new RuntimeException("Failed to unparcel Bitmap");
            }
            if (p.readBoolean()) {
                bm.setGainmap((Gainmap) p.readTypedObject(Gainmap.CREATOR));
            }
            return bm;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Bitmap[] newArray(int size) {
            return new Bitmap[size];
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

    public static void setDefaultDensity(int density) {
        sDefaultDensity = density;
    }

    static int getDefaultDensity() {
        if (sDefaultDensity >= 0) {
            return sDefaultDensity;
        }
        sDefaultDensity = DisplayMetrics.DENSITY_DEVICE;
        return sDefaultDensity;
    }

    Bitmap(long nativeBitmap, int width, int height, int density, boolean requestPremultiplied, byte[] ninePatchChunk, NinePatch.InsetStruct ninePatchInsets) {
        this(nativeBitmap, width, height, density, requestPremultiplied, ninePatchChunk, ninePatchInsets, true);
    }

    Bitmap(long nativeBitmap, int width, int height, int density, boolean requestPremultiplied, byte[] ninePatchChunk, NinePatch.InsetStruct ninePatchInsets, boolean fromMalloc) {
        NativeAllocationRegistry registry;
        this.mDensity = getDefaultDensity();
        this.mTag = null;
        if (nativeBitmap == 0) {
            throw new RuntimeException("internal error: native bitmap is 0");
        }
        this.mWidth = width;
        this.mHeight = height;
        this.mRequestPremultiplied = requestPremultiplied;
        this.mNinePatchChunk = ninePatchChunk;
        this.mNinePatchInsets = ninePatchInsets;
        if (density >= 0) {
            this.mDensity = density;
        }
        this.mNativePtr = nativeBitmap;
        int allocationByteCount = getAllocationByteCount();
        if (fromMalloc) {
            registry = NativeAllocationRegistry.createMalloced(Bitmap.class.getClassLoader(), nativeGetNativeFinalizer(), allocationByteCount);
        } else {
            registry = NativeAllocationRegistry.createNonmalloced(Bitmap.class.getClassLoader(), nativeGetNativeFinalizer(), allocationByteCount);
        }
        registry.registerNativeAllocation(this, nativeBitmap);
    }

    public long getNativeInstance() {
        return this.mNativePtr;
    }

    void reinit(int width, int height, boolean requestPremultiplied) {
        this.mWidth = width;
        this.mHeight = height;
        this.mRequestPremultiplied = requestPremultiplied;
        this.mColorSpace = null;
    }

    public int getDensity() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getDensity() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mDensity;
    }

    public void setDensity(int density) {
        this.mDensity = density;
    }

    public void reconfigure(int width, int height, Config config) {
        checkRecycled("Can't call reconfigure() on a recycled bitmap");
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        if (!isMutable()) {
            throw new IllegalStateException("only mutable bitmaps may be reconfigured");
        }
        nativeReconfigure(this.mNativePtr, width, height, config.nativeInt, this.mRequestPremultiplied);
        this.mWidth = width;
        this.mHeight = height;
        this.mColorSpace = null;
    }

    public void setWidth(int width) {
        reconfigure(width, getHeight(), getConfig());
    }

    public void setHeight(int height) {
        reconfigure(getWidth(), height, getConfig());
    }

    public void setConfig(Config config) {
        reconfigure(getWidth(), getHeight(), config);
    }

    private void setNinePatchChunk(byte[] chunk) {
        this.mNinePatchChunk = chunk;
    }

    public void recycle() {
        if (!this.mRecycled) {
            nativeRecycle(this.mNativePtr);
            this.mNinePatchChunk = null;
            this.mRecycled = true;
            this.mHardwareBuffer = null;
        }
    }

    public final boolean isRecycled() {
        return this.mRecycled;
    }

    public int getGenerationId() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getGenerationId() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeGenerationId(this.mNativePtr);
    }

    void checkRecycled(String errorMessage) {
        if (this.mRecycled) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private void checkHardware(String errorMessage) {
        if (getConfig() == Config.HARDWARE) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private static void checkXYSign(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("x must be >= 0");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y must be >= 0");
        }
    }

    private static void checkWidthHeight(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("width must be > 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("height must be > 0");
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v0 android.graphics.Bitmap$Config, still in use, count: 1, list:
      (r1v0 android.graphics.Bitmap$Config) from 0x0057: FILLED_NEW_ARRAY 
      (null android.graphics.Bitmap$Config)
      (r1v0 android.graphics.Bitmap$Config)
      (null android.graphics.Bitmap$Config)
      (r4v0 android.graphics.Bitmap$Config)
      (r5v0 android.graphics.Bitmap$Config)
      (r7v0 android.graphics.Bitmap$Config)
      (r8v0 android.graphics.Bitmap$Config)
      (r9v0 android.graphics.Bitmap$Config)
      (r10v0 android.graphics.Bitmap$Config)
     A[WRAPPED] (LINE:579) elemType: android.graphics.Bitmap$Config
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
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

        public static Config valueOf(String name) {
            return (Config) Enum.valueOf(Config.class, name);
        }

        public static Config[] values() {
            return (Config[]) $VALUES.clone();
        }

        static {
        }

        private Config(int ni) {
            this.nativeInt = ni;
        }

        static Config nativeToConfig(int ni) {
            return sConfigs[ni];
        }
    }

    public void copyPixelsToBuffer(Buffer dst) {
        int shift;
        checkHardware("unable to copyPixelsToBuffer, pixel access is not supported on Config#HARDWARE bitmaps");
        int elements = dst.remaining();
        if (dst instanceof ByteBuffer) {
            shift = 0;
        } else if (dst instanceof ShortBuffer) {
            shift = 1;
        } else if (dst instanceof IntBuffer) {
            shift = 2;
        } else {
            throw new RuntimeException("unsupported Buffer subclass");
        }
        long bufferSize = elements << shift;
        long pixelSize = getByteCount();
        if (bufferSize < pixelSize) {
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsToBuffer(this.mNativePtr, dst);
        int position = dst.position();
        dst.position((int) (position + (pixelSize >> shift)));
    }

    public void copyPixelsFromBuffer(Buffer src) {
        int shift;
        checkRecycled("copyPixelsFromBuffer called on recycled bitmap");
        checkHardware("unable to copyPixelsFromBuffer, Config#HARDWARE bitmaps are immutable");
        int elements = src.remaining();
        if (src instanceof ByteBuffer) {
            shift = 0;
        } else if (src instanceof ShortBuffer) {
            shift = 1;
        } else if (src instanceof IntBuffer) {
            shift = 2;
        } else {
            throw new RuntimeException("unsupported Buffer subclass");
        }
        long bufferBytes = elements << shift;
        long bitmapBytes = getByteCount();
        if (bufferBytes < bitmapBytes) {
            throw new RuntimeException("Buffer not large enough for pixels");
        }
        nativeCopyPixelsFromBuffer(this.mNativePtr, src);
        int position = src.position();
        src.position((int) (position + (bitmapBytes >> shift)));
    }

    private void noteHardwareBitmapSlowCall() {
        if (getConfig() == Config.HARDWARE) {
            StrictMode.noteSlowCall("Warning: attempt to read pixels from hardware bitmap, which is very slow operation");
        }
    }

    public Bitmap copy(Config config, boolean isMutable) {
        checkRecycled("Can't copy a recycled bitmap");
        if (config == Config.HARDWARE && isMutable) {
            throw new IllegalArgumentException("Hardware bitmaps are always immutable");
        }
        noteHardwareBitmapSlowCall();
        Bitmap b = nativeCopy(this.mNativePtr, config.nativeInt, isMutable);
        if (b != null) {
            b.setPremultiplied(this.mRequestPremultiplied);
            b.mDensity = this.mDensity;
        }
        return b;
    }

    public Bitmap createAshmemBitmap() {
        checkRecycled("Can't copy a recycled bitmap");
        noteHardwareBitmapSlowCall();
        Bitmap b = nativeCopyAshmem(this.mNativePtr);
        if (b != null) {
            b.setPremultiplied(this.mRequestPremultiplied);
            b.mDensity = this.mDensity;
        }
        return b;
    }

    public Bitmap asShared() {
        if (nativeIsBackedByAshmem(this.mNativePtr) && nativeIsImmutable(this.mNativePtr)) {
            return this;
        }
        Bitmap shared = createAshmemBitmap();
        if (shared == null) {
            throw new RuntimeException("Failed to create shared Bitmap!");
        }
        return shared;
    }

    public SharedMemory getSharedMemory() {
        checkRecycled("Cannot access shared memory of a recycled bitmap");
        if (nativeIsBackedByAshmem(this.mNativePtr)) {
            try {
                int fd = nativeGetAshmemFD(this.mNativePtr);
                return SharedMemory.fromFileDescriptor(ParcelFileDescriptor.fromFd(fd));
            } catch (IOException e) {
                Log.m96e(TAG, "Unable to create dup'd file descriptor for shared bitmap memory");
                return null;
            }
        }
        return null;
    }

    public static Bitmap wrapHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        if ((hardwareBuffer.getUsage() & 256) == 0) {
            throw new IllegalArgumentException("usage flags must contain USAGE_GPU_SAMPLED_IMAGE.");
        }
        hardwareBuffer.getFormat();
        if (colorSpace == null) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        Bitmap bitmap = nativeWrapHardwareBufferBitmap(hardwareBuffer, colorSpace.getNativeInstance());
        if (bitmap != null) {
            bitmap.mHardwareBuffer = new WeakReference<>(hardwareBuffer);
        }
        return bitmap;
    }

    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
        Matrix m = new Matrix();
        int width = src.getWidth();
        int height = src.getHeight();
        if (width != dstWidth || height != dstHeight) {
            float sx = dstWidth / width;
            float sy = dstHeight / height;
            m.setScale(sx, sy);
        }
        return createBitmap(src, 0, 0, width, height, m, filter);
    }

    public static Bitmap createBitmap(Bitmap src) {
        return createBitmap(src, 0, 0, src.getWidth(), src.getHeight());
    }

    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height) {
        return createBitmap(source, x, y, width, height, (Matrix) null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x018e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x017f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
        Config newConfig;
        Bitmap bitmap;
        Paint paint;
        boolean z;
        Bitmap source2 = source;
        checkXYSign(x, y);
        checkWidthHeight(width, height);
        if (x + width > source.getWidth()) {
            throw new IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        if (y + height > source.getHeight()) {
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (source.isRecycled()) {
            throw new IllegalArgumentException("cannot use a recycled source in createBitmap");
        }
        if (!source.isMutable() && x == 0 && y == 0 && width == source.getWidth() && height == source.getHeight() && (m == null || m.isIdentity())) {
            return source2;
        }
        boolean isHardware = source.getConfig() == Config.HARDWARE;
        if (isHardware) {
            source.noteHardwareBitmapSlowCall();
            source2 = nativeCopyPreserveInternalConfig(source2.mNativePtr);
        }
        int neww = width;
        int newh = height;
        Rect srcR = new Rect(x, y, x + width, y + height);
        RectF dstR = new RectF(0.0f, 0.0f, width, height);
        RectF deviceR = new RectF();
        Config newConfig2 = Config.ARGB_8888;
        Config config = source2.getConfig();
        if (config == null) {
            newConfig = newConfig2;
        } else {
            switch (C08192.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()]) {
                case 1:
                    Config newConfig3 = Config.RGB_565;
                    newConfig = newConfig3;
                    break;
                case 2:
                    Config newConfig4 = Config.ALPHA_8;
                    newConfig = newConfig4;
                    break;
                case 3:
                    Config newConfig5 = Config.RGBA_F16;
                    newConfig = newConfig5;
                    break;
                default:
                    Config newConfig6 = Config.ARGB_8888;
                    newConfig = newConfig6;
                    break;
            }
        }
        ColorSpace cs = source2.getColorSpace();
        if (m != null && !m.isIdentity()) {
            boolean transformed = !m.rectStaysRect();
            m.mapRect(deviceR, dstR);
            neww = Math.round(deviceR.width());
            newh = Math.round(deviceR.height());
            Config transformedConfig = newConfig;
            if (transformed && transformedConfig != Config.ARGB_8888 && transformedConfig != Config.RGBA_F16) {
                transformedConfig = Config.ARGB_8888;
                if (cs == null) {
                    cs = ColorSpace.get(ColorSpace.Named.SRGB);
                }
            }
            bitmap = createBitmap((DisplayMetrics) null, neww, newh, transformedConfig, transformed || source2.hasAlpha(), cs);
            Paint paint2 = new Paint();
            paint2.setFilterBitmap(filter);
            if (transformed) {
                paint2.setAntiAlias(true);
            }
            paint = paint2;
            bitmap.mDensity = source2.mDensity;
            bitmap.setHasAlpha(source2.hasAlpha());
            bitmap.setPremultiplied(source2.mRequestPremultiplied);
            Canvas canvas = new Canvas(bitmap);
            canvas.translate(-deviceR.left, -deviceR.top);
            canvas.concat(m);
            canvas.drawBitmap(source2, srcR, dstR, paint);
            canvas.setBitmap(null);
            if (source2.hasGainmap()) {
                z = false;
            } else {
                z = false;
                Bitmap newMapContents = transformGainmap(source2, m, neww, newh, paint, srcR, dstR, deviceR);
                if (newMapContents != null) {
                    bitmap.setGainmap(new Gainmap(source2.getGainmap(), newMapContents));
                }
            }
            if (!isHardware) {
                return bitmap.copy(Config.HARDWARE, z);
            }
            return bitmap;
        }
        bitmap = createBitmap((DisplayMetrics) null, neww, newh, newConfig, source2.hasAlpha(), cs);
        paint = null;
        bitmap.mDensity = source2.mDensity;
        bitmap.setHasAlpha(source2.hasAlpha());
        bitmap.setPremultiplied(source2.mRequestPremultiplied);
        Canvas canvas2 = new Canvas(bitmap);
        canvas2.translate(-deviceR.left, -deviceR.top);
        canvas2.concat(m);
        canvas2.drawBitmap(source2, srcR, dstR, paint);
        canvas2.setBitmap(null);
        if (source2.hasGainmap()) {
        }
        if (!isHardware) {
        }
    }

    /* renamed from: android.graphics.Bitmap$2 */
    static /* synthetic */ class C08192 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config;

        static {
            int[] iArr = new int[Config.values().length];
            $SwitchMap$android$graphics$Bitmap$Config = iArr;
            try {
                iArr[Config.RGB_565.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.RGBA_F16.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_4444.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Config.ARGB_8888.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private static Bitmap transformGainmap(Bitmap source, Matrix m, int neww, int newh, Paint paint, Rect srcR, RectF dstR, RectF deviceR) {
        Bitmap sourceGainmap = source.getGainmap().getGainmapContents();
        float scaleX = sourceGainmap.getWidth() / source.getWidth();
        float scaleY = sourceGainmap.getHeight() / source.getHeight();
        int mapw = Math.round(neww * scaleX);
        int maph = Math.round(newh * scaleY);
        if (mapw != 0 && maph != 0) {
            Rect gSrcR = new Rect((int) (srcR.left * scaleX), (int) (srcR.top * scaleY), (int) (srcR.right * scaleX), (int) (srcR.bottom * scaleY));
            long nativeColorSpace = sourceGainmap.getColorSpace() != null ? sourceGainmap.getColorSpace().getNativeInstance() : 0L;
            Bitmap newMapContents = nativeCreate(null, 0, mapw, mapw, maph, sourceGainmap.getConfig().nativeInt, true, nativeColorSpace);
            newMapContents.eraseColor(0);
            Canvas canvas = new Canvas(newMapContents);
            canvas.scale(scaleX, scaleY);
            canvas.translate(-deviceR.left, -deviceR.top);
            canvas.concat(m);
            canvas.drawBitmap(sourceGainmap, gSrcR, dstR, paint);
            canvas.setBitmap(null);
            return newMapContents;
        }
        return null;
    }

    public static Bitmap createBitmap(int width, int height, Config config) {
        return createBitmap(width, height, config, true);
    }

    public static Bitmap createBitmap(DisplayMetrics display, int width, int height, Config config) {
        return createBitmap(display, width, height, config, true);
    }

    public static Bitmap createBitmap(int width, int height, Config config, boolean hasAlpha) {
        return createBitmap((DisplayMetrics) null, width, height, config, hasAlpha);
    }

    public static Bitmap createBitmap(int width, int height, Config config, boolean hasAlpha, ColorSpace colorSpace) {
        return createBitmap((DisplayMetrics) null, width, height, config, hasAlpha, colorSpace);
    }

    public static Bitmap createBitmap(DisplayMetrics display, int width, int height, Config config, boolean hasAlpha) {
        return createBitmap(display, width, height, config, hasAlpha, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Bitmap createBitmap(DisplayMetrics display, int width, int height, Config config, boolean hasAlpha, ColorSpace colorSpace) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        if (config == Config.HARDWARE) {
            throw new IllegalArgumentException("can't create mutable bitmap with Config.HARDWARE");
        }
        if (colorSpace == null && config != Config.ALPHA_8) {
            throw new IllegalArgumentException("can't create bitmap without a color space");
        }
        Bitmap bm = nativeCreate(null, 0, width, width, height, config.nativeInt, true, colorSpace == null ? 0L : colorSpace.getNativeInstance());
        if (display != null) {
            bm.mDensity = display.densityDpi;
        }
        bm.setHasAlpha(hasAlpha);
        if ((config == Config.ARGB_8888 || config == Config.RGBA_F16) && !hasAlpha) {
            nativeErase(bm.mNativePtr, -16777216);
        }
        return bm;
    }

    public static Bitmap createBitmap(int[] colors, int offset, int stride, int width, int height, Config config) {
        return createBitmap((DisplayMetrics) null, colors, offset, stride, width, height, config);
    }

    public static Bitmap createBitmap(DisplayMetrics display, int[] colors, int offset, int stride, int width, int height, Config config) {
        checkWidthHeight(width, height);
        if (Math.abs(stride) < width) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int lastScanline = offset + ((height - 1) * stride);
        int length = colors.length;
        if (offset < 0 || offset + width > length || lastScanline < 0 || lastScanline + width > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        ColorSpace sRGB = ColorSpace.get(ColorSpace.Named.SRGB);
        Bitmap bm = nativeCreate(colors, offset, stride, width, height, config.nativeInt, false, sRGB.getNativeInstance());
        if (display != null) {
            bm.mDensity = display.densityDpi;
        }
        return bm;
    }

    public static Bitmap createBitmap(int[] colors, int width, int height, Config config) {
        return createBitmap((DisplayMetrics) null, colors, 0, width, width, height, config);
    }

    public static Bitmap createBitmap(DisplayMetrics display, int[] colors, int width, int height, Config config) {
        return createBitmap(display, colors, 0, width, width, height, config);
    }

    public static Bitmap createBitmap(Picture source) {
        return createBitmap(source, source.getWidth(), source.getHeight(), Config.HARDWARE);
    }

    public static Bitmap createBitmap(Picture source, int width, int height, Config config) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width & height must be > 0");
        }
        if (config == null) {
            throw new IllegalArgumentException("Config must not be null");
        }
        source.endRecording();
        if (source.requiresHardwareAcceleration() && config != Config.HARDWARE) {
            StrictMode.noteSlowCall("GPU readback");
        }
        if (config == Config.HARDWARE || source.requiresHardwareAcceleration()) {
            RenderNode node = RenderNode.create("BitmapTemporary", null);
            node.setLeftTopRightBottom(0, 0, width, height);
            node.setClipToBounds(false);
            node.setForceDarkAllowed(false);
            RecordingCanvas canvas = node.beginRecording(width, height);
            if (source.getWidth() != width || source.getHeight() != height) {
                canvas.scale(width / source.getWidth(), height / source.getHeight());
            }
            canvas.drawPicture(source);
            node.endRecording();
            Bitmap bitmap = ThreadedRenderer.createHardwareBitmap(node, width, height);
            if (config != Config.HARDWARE) {
                return bitmap.copy(config, false);
            }
            return bitmap;
        }
        Bitmap bitmap2 = createBitmap(width, height, config);
        Canvas canvas2 = new Canvas(bitmap2);
        if (source.getWidth() != width || source.getHeight() != height) {
            canvas2.scale(width / source.getWidth(), height / source.getHeight());
        }
        canvas2.drawPicture(source);
        canvas2.setBitmap(null);
        bitmap2.setImmutable();
        return bitmap2;
    }

    public byte[] getNinePatchChunk() {
        return this.mNinePatchChunk;
    }

    public void getOpticalInsets(Rect outInsets) {
        NinePatch.InsetStruct insetStruct = this.mNinePatchInsets;
        if (insetStruct == null) {
            outInsets.setEmpty();
        } else {
            outInsets.set(insetStruct.opticalRect);
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

        CompressFormat(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    public boolean compress(CompressFormat format, int quality, OutputStream stream) {
        checkRecycled("Can't compress a recycled bitmap");
        if (stream == null) {
            throw new NullPointerException();
        }
        if (quality < 0 || quality > 100) {
            throw new IllegalArgumentException("quality must be 0..100");
        }
        int uid = Process.myUid();
        String pkgName = "";
        IPackageManager pm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        try {
            pkgName = pm.getNameForUid(uid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (pkgName != null && pkgName.contains("com.tencent.mm") && quality <= 70) {
            quality = 85;
        }
        StrictMode.noteSlowCall("Compression of a bitmap is slow");
        Trace.traceBegin(8192L, "Bitmap.compress");
        boolean result = nativeCompress(this.mNativePtr, format.nativeInt, quality, stream, new byte[4096]);
        Trace.traceEnd(8192L);
        return result;
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
            Log.m102w(TAG, "Called isPremultiplied() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeIsPremultiplied(this.mNativePtr);
    }

    public final void setPremultiplied(boolean premultiplied) {
        checkRecycled("setPremultiplied called on a recycled bitmap");
        this.mRequestPremultiplied = premultiplied;
        nativeSetPremultiplied(this.mNativePtr, premultiplied);
    }

    public final int getWidth() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getWidth() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mWidth;
    }

    public final int getHeight() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getHeight() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return this.mHeight;
    }

    public int getScaledWidth(Canvas canvas) {
        return scaleFromDensity(getWidth(), this.mDensity, canvas.mDensity);
    }

    public int getScaledHeight(Canvas canvas) {
        return scaleFromDensity(getHeight(), this.mDensity, canvas.mDensity);
    }

    public int getScaledWidth(DisplayMetrics metrics) {
        return scaleFromDensity(getWidth(), this.mDensity, metrics.densityDpi);
    }

    public int getScaledHeight(DisplayMetrics metrics) {
        return scaleFromDensity(getHeight(), this.mDensity, metrics.densityDpi);
    }

    public int getScaledWidth(int targetDensity) {
        return scaleFromDensity(getWidth(), this.mDensity, targetDensity);
    }

    public int getScaledHeight(int targetDensity) {
        return scaleFromDensity(getHeight(), this.mDensity, targetDensity);
    }

    public static int scaleFromDensity(int size, int sdensity, int tdensity) {
        if (sdensity == 0 || tdensity == 0 || sdensity == tdensity) {
            return size;
        }
        return ((size * tdensity) + (sdensity >> 1)) / sdensity;
    }

    public final int getRowBytes() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getRowBytes() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeRowBytes(this.mNativePtr);
    }

    public final int getByteCount() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return getRowBytes() * getHeight();
    }

    public final int getAllocationByteCount() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getAllocationByteCount() on a recycle()'d bitmap! This is undefined behavior!");
            return 0;
        }
        return nativeGetAllocationByteCount(this.mNativePtr);
    }

    public final Config getConfig() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called getConfig() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return Config.nativeToConfig(nativeConfig(this.mNativePtr));
    }

    public final boolean hasAlpha() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called hasAlpha() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasAlpha(this.mNativePtr);
    }

    public void setHasAlpha(boolean hasAlpha) {
        checkRecycled("setHasAlpha called on a recycled bitmap");
        nativeSetHasAlpha(this.mNativePtr, hasAlpha, this.mRequestPremultiplied);
    }

    public final boolean hasMipMap() {
        if (this.mRecycled) {
            Log.m102w(TAG, "Called hasMipMap() on a recycle()'d bitmap! This is undefined behavior!");
        }
        return nativeHasMipMap(this.mNativePtr);
    }

    public final void setHasMipMap(boolean hasMipMap) {
        checkRecycled("setHasMipMap called on a recycled bitmap");
        nativeSetHasMipMap(this.mNativePtr, hasMipMap);
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
        ColorSpace oldColorSpace = getColorSpace();
        nativeSetColorSpace(this.mNativePtr, colorSpace.getNativeInstance());
        this.mColorSpace = null;
        ColorSpace newColorSpace = getColorSpace();
        try {
            if (oldColorSpace.getComponentCount() != newColorSpace.getComponentCount()) {
                throw new IllegalArgumentException("The new ColorSpace must have the same component count as the current ColorSpace");
            }
            for (int i = 0; i < oldColorSpace.getComponentCount(); i++) {
                if (oldColorSpace.getMinValue(i) < newColorSpace.getMinValue(i)) {
                    throw new IllegalArgumentException("The new ColorSpace cannot increase the minimum value for any of the components compared to the current ColorSpace. To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
                if (oldColorSpace.getMaxValue(i) > newColorSpace.getMaxValue(i)) {
                    throw new IllegalArgumentException("The new ColorSpace cannot decrease the maximum value for any of the components compared to the current ColorSpace/ To perform this type of conversion create a new Bitmap in the desired ColorSpace and draw this Bitmap into it.");
                }
            }
        } catch (IllegalArgumentException e) {
            this.mColorSpace = oldColorSpace;
            nativeSetColorSpace(this.mNativePtr, oldColorSpace.getNativeInstance());
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
        this.mGainmap = null;
        nativeSetGainmap(this.mNativePtr, gainmap == null ? 0L : gainmap.mNativePtr);
    }

    public void eraseColor(int c) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException("cannot erase immutable bitmaps");
        }
        nativeErase(this.mNativePtr, c);
    }

    public void eraseColor(long color) {
        checkRecycled("Can't erase a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException("cannot erase immutable bitmaps");
        }
        ColorSpace cs = Color.colorSpace(color);
        nativeErase(this.mNativePtr, cs.getNativeInstance(), color);
    }

    public int getPixel(int x, int y) {
        checkRecycled("Can't call getPixel() on a recycled bitmap");
        checkHardware("unable to getPixel(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(x, y);
        return nativeGetPixel(this.mNativePtr, x, y);
    }

    private static float clamp(float value, ColorSpace cs, int index) {
        return Math.max(Math.min(value, cs.getMaxValue(index)), cs.getMinValue(index));
    }

    public Color getColor(int x, int y) {
        checkRecycled("Can't call getColor() on a recycled bitmap");
        checkHardware("unable to getColor(), pixel access is not supported on Config#HARDWARE bitmaps");
        checkPixelAccess(x, y);
        ColorSpace cs = getColorSpace();
        if (cs == null || cs.equals(ColorSpace.get(ColorSpace.Named.SRGB))) {
            return Color.valueOf(nativeGetPixel(this.mNativePtr, x, y));
        }
        long rgba = nativeGetColor(this.mNativePtr, x, y);
        float r = Half.toFloat((short) ((rgba >> 0) & 65535));
        float g = Half.toFloat((short) ((rgba >> 16) & 65535));
        float b = Half.toFloat((short) ((rgba >> 32) & 65535));
        float a = Half.toFloat((short) (65535 & (rgba >> 48)));
        return Color.valueOf(clamp(r, cs, 0), clamp(g, cs, 1), clamp(b, cs, 2), a, cs);
    }

    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkRecycled("Can't call getPixels() on a recycled bitmap");
        checkHardware("unable to getPixels(), pixel access is not supported on Config#HARDWARE bitmaps");
        if (width == 0 || height == 0) {
            return;
        }
        checkPixelsAccess(x, y, width, height, offset, stride, pixels);
        nativeGetPixels(this.mNativePtr, pixels, offset, stride, x, y, width, height);
    }

    private void checkPixelAccess(int x, int y) {
        checkXYSign(x, y);
        if (x >= getWidth()) {
            throw new IllegalArgumentException("x must be < bitmap.width()");
        }
        if (y >= getHeight()) {
            throw new IllegalArgumentException("y must be < bitmap.height()");
        }
    }

    private void checkPixelsAccess(int x, int y, int width, int height, int offset, int stride, int[] pixels) {
        checkXYSign(x, y);
        if (width < 0) {
            throw new IllegalArgumentException("width must be >= 0");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must be >= 0");
        }
        if (x + width > getWidth()) {
            throw new IllegalArgumentException("x + width must be <= bitmap.width()");
        }
        if (y + height > getHeight()) {
            throw new IllegalArgumentException("y + height must be <= bitmap.height()");
        }
        if (Math.abs(stride) < width) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int lastScanline = ((height - 1) * stride) + offset;
        int length = pixels.length;
        if (offset < 0 || offset + width > length || lastScanline < 0 || lastScanline + width > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setPixel(int x, int y, int color) {
        checkRecycled("Can't call setPixel() on a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException();
        }
        checkPixelAccess(x, y);
        nativeSetPixel(this.mNativePtr, x, y, color);
    }

    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkRecycled("Can't call setPixels() on a recycled bitmap");
        if (!isMutable()) {
            throw new IllegalStateException();
        }
        if (width == 0 || height == 0) {
            return;
        }
        checkPixelsAccess(x, y, width, height, offset, stride, pixels);
        nativeSetPixels(this.mNativePtr, pixels, offset, stride, x, y, width, height);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel p, int flags) {
        checkRecycled("Can't parcel a recycled bitmap");
        noteHardwareBitmapSlowCall();
        if (!nativeWriteToParcel(this.mNativePtr, this.mDensity, p)) {
            throw new RuntimeException("native writeToParcel failed");
        }
        if (hasGainmap()) {
            p.writeBoolean(true);
            p.writeTypedObject(this.mGainmap, flags);
        } else {
            p.writeBoolean(false);
        }
    }

    public Bitmap extractAlpha() {
        return extractAlpha(null, null);
    }

    public Bitmap extractAlpha(Paint paint, int[] offsetXY) {
        checkRecycled("Can't extractAlpha on a recycled bitmap");
        long nativePaint = paint != null ? paint.getNativeInstance() : 0L;
        noteHardwareBitmapSlowCall();
        Bitmap bm = nativeExtractAlpha(this.mNativePtr, nativePaint, offsetXY);
        if (bm == null) {
            throw new RuntimeException("Failed to extractAlpha on Bitmap");
        }
        bm.mDensity = this.mDensity;
        return bm;
    }

    public boolean sameAs(Bitmap other) {
        StrictMode.noteSlowCall("sameAs compares pixel data, not expected to be fast");
        checkRecycled("Can't call sameAs on a recycled bitmap!");
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.isRecycled()) {
            throw new IllegalArgumentException("Can't compare to a recycled bitmap!");
        }
        return nativeSameAs(this.mNativePtr, other.mNativePtr);
    }

    public void prepareToDraw() {
        checkRecycled("Can't prepareToDraw on a recycled bitmap!");
        nativePrepareToDraw(this.mNativePtr);
    }

    public HardwareBuffer getHardwareBuffer() {
        checkRecycled("Can't getHardwareBuffer from a recycled bitmap");
        WeakReference<HardwareBuffer> weakReference = this.mHardwareBuffer;
        HardwareBuffer hardwareBuffer = weakReference == null ? null : weakReference.get();
        if (hardwareBuffer == null || hardwareBuffer.isClosed()) {
            HardwareBuffer hardwareBuffer2 = nativeGetHardwareBuffer(this.mNativePtr);
            this.mHardwareBuffer = new WeakReference<>(hardwareBuffer2);
            return hardwareBuffer2;
        }
        return hardwareBuffer;
    }

    public void semSetTag(Object tag) {
        this.mTag = tag;
    }

    public Object semGetTag() {
        return this.mTag;
    }
}
