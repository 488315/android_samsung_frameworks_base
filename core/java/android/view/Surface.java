package android.view;

import android.content.res.CompatibilityInfo;
import android.graphics.BLASTBufferQueue;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.SurfaceTexture;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.OsConstants;
import android.util.Log;
import android.view.flags.Flags;

import dalvik.system.CloseGuard;
import dalvik.system.VMRuntime;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Surface implements Parcelable {
    public static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    public static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;
    public static final Parcelable.Creator<Surface> CREATOR =
            new Parcelable.Creator<Surface>() { // from class: android.view.Surface.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Surface createFromParcel(Parcel source) {
                    try {
                        Surface s = new Surface();
                        s.readFromParcel(source);
                        return s;
                    } catch (Exception e) {
                        Log.e(Surface.TAG, "Exception creating surface from parcel", e);
                        return null;
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Surface[] newArray(int size) {
                    return new Surface[size];
                }
            };
    public static final int FRAME_RATE_CATEGORY_DEFAULT = 0;
    public static final int FRAME_RATE_CATEGORY_HIGH = 5;
    public static final int FRAME_RATE_CATEGORY_HIGH_HINT = 4;
    public static final int FRAME_RATE_CATEGORY_LOW = 2;
    public static final int FRAME_RATE_CATEGORY_NORMAL = 3;
    public static final int FRAME_RATE_CATEGORY_NO_PREFERENCE = 1;
    public static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    public static final int FRAME_RATE_COMPATIBILITY_EXACT = 100;
    public static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
    public static final int FRAME_RATE_COMPATIBILITY_GTE = 103;
    public static final int FRAME_RATE_COMPATIBILITY_MIN = 102;
    public static final int FRAME_RATE_COMPATIBILITY_NO_VOTE = 101;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    public static final int SCALING_MODE_FREEZE = 0;
    public static final int SCALING_MODE_NO_SCALE_CROP = 3;
    public static final int SCALING_MODE_SCALE_CROP = 2;
    public static final int SCALING_MODE_SCALE_TO_WINDOW = 1;
    private static final long SURFACE_NATIVE_ALLOCATION_SIZE_BYTES = 5000;
    private static final String TAG = "Surface";
    private Matrix mCompatibleMatrix;
    private int mGenerationId;
    private HwuiContext mHwuiContext;
    private boolean mIsAutoRefreshEnabled;
    private boolean mIsSharedBufferModeEnabled;
    private boolean mIsSingleBuffered;
    private long mLockedObject;
    private String mName;
    long mNativeObject;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    final Object mLock = new Object();
    private final Canvas mCanvas = new CompatibleCanvas();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFrameRateStrategy {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateCategory {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateCompatibility {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScalingMode {}

    private static native void nativeAllocateBuffers(long j);

    private static native int nativeAttachAndQueueBufferWithColorSpace(
            long j, HardwareBuffer hardwareBuffer, int i);

    private static native long nativeCreateFromSurfaceControl(long j);

    private static native long nativeCreateFromSurfaceTexture(SurfaceTexture surfaceTexture)
            throws OutOfResourcesException;

    private static native void nativeDestroy(long j);

    private static native int nativeForceScopedDisconnect(long j);

    private static native long nativeGetFromBlastBufferQueue(long j, long j2);

    private static native long nativeGetFromSurfaceControl(long j, long j2);

    private static native int nativeGetHeight(long j);

    private static native long nativeGetNextFrameNumber(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeIsConsumerRunningBehind(long j);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLockCanvas(long j, Canvas canvas, Rect rect)
            throws OutOfResourcesException;

    private static native long nativeReadFromParcel(long j, Parcel parcel);

    private static native void nativeRelease(long j);

    private static native int nativeSetAutoRefreshEnabled(long j, boolean z);

    private static native int nativeSetFrameRate(long j, float f, int i, int i2);

    private static native int nativeSetScalingMode(long j, int i);

    private static native int nativeSetSharedBufferModeEnabled(long j, boolean z);

    private static native void nativeUnlockCanvasAndPost(long j, Canvas canvas);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    public Surface() {
        registerNativeMemoryUsage();
    }

    public Surface(SurfaceControl from) {
        copyFrom(from);
        registerNativeMemoryUsage();
    }

    public Surface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new IllegalArgumentException("surfaceTexture must not be null");
        }
        this.mIsSingleBuffered = surfaceTexture.isSingleBuffered();
        synchronized (this.mLock) {
            this.mName = surfaceTexture.toString();
            setNativeObjectLocked(nativeCreateFromSurfaceTexture(surfaceTexture));
        }
        registerNativeMemoryUsage();
    }

    private Surface(long nativeObject) {
        synchronized (this.mLock) {
            setNativeObjectLocked(nativeObject);
        }
        registerNativeMemoryUsage();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            release();
        } finally {
            super.finalize();
            freeNativeMemoryUsage();
        }
    }

    public void release() {
        synchronized (this.mLock) {
            if (this.mHwuiContext != null) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
                setNativeObjectLocked(0L);
            }
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            if (this.mNativeObject != 0) {
                nativeDestroy(this.mNativeObject);
            }
            release();
        }
    }

    public void hwuiDestroy() {
        if (this.mHwuiContext != null) {
            this.mHwuiContext.destroy();
            this.mHwuiContext = null;
        }
    }

    public boolean isValid() {
        synchronized (this.mLock) {
            if (this.mNativeObject == 0) {
                return false;
            }
            return nativeIsValid(this.mNativeObject);
        }
    }

    public int getGenerationId() {
        int i;
        synchronized (this.mLock) {
            i = this.mGenerationId;
        }
        return i;
    }

    public long getNextFrameNumber() {
        long nativeGetNextFrameNumber;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeGetNextFrameNumber = nativeGetNextFrameNumber(this.mNativeObject);
        }
        return nativeGetNextFrameNumber;
    }

    public boolean isConsumerRunningBehind() {
        boolean nativeIsConsumerRunningBehind;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeIsConsumerRunningBehind = nativeIsConsumerRunningBehind(this.mNativeObject);
        }
        return nativeIsConsumerRunningBehind;
    }

    public Point getDefaultSize() {
        Point point;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            point =
                    new Point(
                            nativeGetWidth(this.mNativeObject),
                            nativeGetHeight(this.mNativeObject));
        }
        return point;
    }

    public Canvas lockCanvas(Rect inOutDirty)
            throws OutOfResourcesException, IllegalArgumentException {
        Canvas canvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mLockedObject != 0) {
                throw new IllegalArgumentException("Surface was already locked");
            }
            this.mLockedObject = nativeLockCanvas(this.mNativeObject, this.mCanvas, inOutDirty);
            canvas = this.mCanvas;
        }
        return canvas;
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext != null) {
                this.mHwuiContext.unlockAndPost(canvas);
            } else {
                unlockSwCanvasAndPost(canvas);
            }
        }
    }

    private void unlockSwCanvasAndPost(Canvas canvas) {
        if (canvas != this.mCanvas) {
            throw new IllegalArgumentException(
                    "canvas object must be the same instance that was previously returned by"
                        + " lockCanvas");
        }
        if (this.mNativeObject != this.mLockedObject) {
            Log.w(
                    TAG,
                    "WARNING: Surface's mNativeObject (0x"
                            + Long.toHexString(this.mNativeObject)
                            + ") != mLockedObject (0x"
                            + Long.toHexString(this.mLockedObject)
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mLockedObject == 0) {
            throw new IllegalStateException("Surface was not locked");
        }
        try {
            nativeUnlockCanvasAndPost(this.mLockedObject, canvas);
        } finally {
            nativeRelease(this.mLockedObject);
            this.mLockedObject = 0L;
        }
    }

    public Canvas lockHardwareCanvas() {
        Canvas lockCanvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext == null) {
                this.mHwuiContext = new HwuiContext(false);
            }
            lockCanvas =
                    this.mHwuiContext.lockCanvas(
                            nativeGetWidth(this.mNativeObject),
                            nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    public Canvas lockHardwareWideColorGamutCanvas() {
        Canvas lockCanvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mHwuiContext != null && !this.mHwuiContext.isWideColorGamut()) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mHwuiContext == null) {
                this.mHwuiContext = new HwuiContext(true);
            }
            lockCanvas =
                    this.mHwuiContext.lockCanvas(
                            nativeGetWidth(this.mNativeObject),
                            nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    @Deprecated
    public void unlockCanvas(Canvas canvas) {
        throw new UnsupportedOperationException();
    }

    void setCompatibilityTranslator(CompatibilityInfo.Translator translator) {
        if (translator != null) {
            float appScale = translator.applicationScale;
            this.mCompatibleMatrix = new Matrix();
            this.mCompatibleMatrix.setScale(appScale, appScale);
        }
    }

    private void updateNativeObject(long newNativeObject) {
        synchronized (this.mLock) {
            if (newNativeObject == this.mNativeObject) {
                return;
            }
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
            }
            setNativeObjectLocked(newNativeObject);
        }
    }

    public void copyFrom(SurfaceControl other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long surfaceControlPtr = other.mNativeObject;
        if (surfaceControlPtr == 0) {
            throw new NullPointerException(
                    "null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        long newNativeObject = nativeGetFromSurfaceControl(this.mNativeObject, surfaceControlPtr);
        updateNativeObject(newNativeObject);
    }

    public void copyFrom(BLASTBufferQueue queue) {
        if (queue == null) {
            throw new IllegalArgumentException("queue must not be null");
        }
        long blastBufferQueuePtr = queue.mNativeObject;
        if (blastBufferQueuePtr == 0) {
            throw new NullPointerException("Null BLASTBufferQueue native object");
        }
        long newNativeObject =
                nativeGetFromBlastBufferQueue(this.mNativeObject, blastBufferQueuePtr);
        updateNativeObject(newNativeObject);
    }

    public void createFrom(SurfaceControl other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long surfaceControlPtr = other.mNativeObject;
        if (surfaceControlPtr == 0) {
            throw new NullPointerException(
                    "null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        long newNativeObject = nativeCreateFromSurfaceControl(surfaceControlPtr);
        synchronized (this.mLock) {
            if (this.mNativeObject != 0) {
                nativeRelease(this.mNativeObject);
            }
            setNativeObjectLocked(newNativeObject);
        }
    }

    @Deprecated
    public void transferFrom(Surface other) {
        long newPtr;
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (other != this) {
            synchronized (other.mLock) {
                newPtr = other.mNativeObject;
                other.setNativeObjectLocked(0L);
            }
            synchronized (this.mLock) {
                if (this.mNativeObject != 0) {
                    nativeRelease(this.mNativeObject);
                }
                setNativeObjectLocked(newPtr);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel source) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        synchronized (this.mLock) {
            this.mName = source.readString();
            this.mIsSingleBuffered = source.readInt() != 0;
            setNativeObjectLocked(nativeReadFromParcel(this.mNativeObject, source));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (dest == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        synchronized (this.mLock) {
            dest.writeString(this.mName);
            dest.writeInt(this.mIsSingleBuffered ? 1 : 0);
            nativeWriteToParcel(this.mNativeObject, dest);
        }
        if ((flags & 1) != 0) {
            release();
        }
    }

    public String toString() {
        String str;
        synchronized (this.mLock) {
            str =
                    "Surface(name="
                            + this.mName
                            + " mNativeObject="
                            + this.mNativeObject
                            + ")/@0x"
                            + Integer.toHexString(System.identityHashCode(this));
        }
        return str;
    }

    private void setNativeObjectLocked(long ptr) {
        if (this.mNativeObject != ptr) {
            if (this.mNativeObject == 0 && ptr != 0) {
                this.mCloseGuard.open("Surface.release");
            } else if (this.mNativeObject != 0 && ptr == 0) {
                this.mCloseGuard.close();
            }
            this.mNativeObject = ptr;
            this.mGenerationId++;
            if (this.mHwuiContext != null) {
                this.mHwuiContext.updateSurface();
            }
        }
    }

    private void checkNotReleasedLocked() {
        if (this.mNativeObject == 0) {
            throw new IllegalStateException("Surface has already been released.");
        }
    }

    public void allocateBuffers() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            nativeAllocateBuffers(this.mNativeObject);
        }
    }

    public void setScalingMode(int scalingMode) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int err = nativeSetScalingMode(this.mNativeObject, scalingMode);
            if (err != 0) {
                throw new IllegalArgumentException("Invalid scaling mode: " + scalingMode);
            }
        }
    }

    void forceScopedDisconnect() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int err = nativeForceScopedDisconnect(this.mNativeObject);
            if (err != 0) {
                throw new RuntimeException("Failed to disconnect Surface instance (bad object?)");
            }
        }
    }

    public void attachAndQueueBufferWithColorSpace(HardwareBuffer buffer, ColorSpace colorSpace) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (colorSpace == null) {
                colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
            int err =
                    nativeAttachAndQueueBufferWithColorSpace(
                            this.mNativeObject, buffer, colorSpace.getId());
            if (err != 0) {
                throw new RuntimeException(
                        "Failed to attach and queue buffer to Surface (bad object?), native error: "
                                + err);
            }
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }

    public void setSharedBufferModeEnabled(boolean enabled) {
        if (this.mIsSharedBufferModeEnabled != enabled) {
            int error = nativeSetSharedBufferModeEnabled(this.mNativeObject, enabled);
            if (error != 0) {
                throw new RuntimeException(
                        "Failed to set shared buffer mode on Surface (bad object?)");
            }
            this.mIsSharedBufferModeEnabled = enabled;
        }
    }

    public boolean isSharedBufferModeEnabled() {
        return this.mIsSharedBufferModeEnabled;
    }

    public void setAutoRefreshEnabled(boolean enabled) {
        if (this.mIsAutoRefreshEnabled != enabled) {
            int error = nativeSetAutoRefreshEnabled(this.mNativeObject, enabled);
            if (error != 0) {
                throw new RuntimeException("Failed to set auto refresh on Surface (bad object?)");
            }
            this.mIsAutoRefreshEnabled = enabled;
        }
    }

    public boolean isAutoRefreshEnabled() {
        return this.mIsAutoRefreshEnabled;
    }

    public void setFrameRate(float frameRate, int compatibility, int changeFrameRateStrategy) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int error =
                    nativeSetFrameRate(
                            this.mNativeObject, frameRate, compatibility, changeFrameRateStrategy);
            if (error == (-OsConstants.EINVAL)) {
                throw new IllegalArgumentException("Invalid argument to Surface.setFrameRate()");
            }
            if (error != 0) {
                Log.e(TAG, "Failed to set frame rate on Surface. Native error: " + error);
            }
        }
    }

    public void clearFrameRate() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int error = nativeSetFrameRate(this.mNativeObject, 0.0f, 0, 0);
            if (error != 0) {
                throw new RuntimeException(
                        "Failed to clear the frame rate on Surface. Native error: " + error);
            }
        }
    }

    public void setFrameRate(float frameRate, int compatibility) {
        setFrameRate(frameRate, compatibility, 0);
    }

    public static class OutOfResourcesException extends RuntimeException {
        public OutOfResourcesException() {}

        public OutOfResourcesException(String name) {
            super(name);
        }
    }

    public static String rotationToString(int rotation) {
        switch (rotation) {
            case 0:
                return "ROTATION_0";
            case 1:
                return "ROTATION_90";
            case 2:
                return "ROTATION_180";
            case 3:
                return "ROTATION_270";
            default:
                return Integer.toString(rotation);
        }
    }

    private final class CompatibleCanvas extends Canvas {
        private Matrix mOrigMatrix;

        private CompatibleCanvas() {
            this.mOrigMatrix = null;
        }

        @Override // android.graphics.Canvas
        public void setMatrix(Matrix matrix) {
            if (Surface.this.mCompatibleMatrix == null
                    || this.mOrigMatrix == null
                    || this.mOrigMatrix.equals(matrix)) {
                super.setMatrix(matrix);
                return;
            }
            Matrix m = new Matrix(Surface.this.mCompatibleMatrix);
            m.preConcat(matrix);
            super.setMatrix(m);
        }

        @Override // android.graphics.Canvas
        public void getMatrix(Matrix m) {
            super.getMatrix(m);
            if (this.mOrigMatrix == null) {
                this.mOrigMatrix = new Matrix();
            }
            this.mOrigMatrix.set(m);
        }
    }

    private final class HwuiContext {
        private RecordingCanvas mCanvas;
        private HardwareRenderer mHardwareRenderer;
        private final boolean mIsWideColorGamut;
        private final RenderNode mRenderNode = RenderNode.create("HwuiCanvas", null);

        HwuiContext(boolean isWideColorGamut) {
            this.mRenderNode.setClipToBounds(false);
            this.mRenderNode.setForceDarkAllowed(false);
            this.mIsWideColorGamut = isWideColorGamut;
            this.mHardwareRenderer = new HardwareRenderer();
            this.mHardwareRenderer.setContentRoot(this.mRenderNode);
            this.mHardwareRenderer.setSurface(Surface.this, true);
            this.mHardwareRenderer.setColorMode(isWideColorGamut ? 1 : 0);
            this.mHardwareRenderer.setLightSourceAlpha(0.0f, 0.0f);
            this.mHardwareRenderer.setLightSourceGeometry(0.0f, 0.0f, 0.0f, 0.0f);
        }

        Canvas lockCanvas(int width, int height) {
            if (this.mCanvas != null) {
                throw new IllegalStateException("Surface was already locked!");
            }
            this.mCanvas = this.mRenderNode.beginRecording(width, height);
            return this.mCanvas;
        }

        void unlockAndPost(Canvas canvas) {
            if (canvas != this.mCanvas) {
                throw new IllegalArgumentException(
                        "canvas object must be the same instance that was previously returned by"
                            + " lockCanvas");
            }
            this.mRenderNode.endRecording();
            this.mCanvas = null;
            this.mHardwareRenderer
                    .createRenderRequest()
                    .setVsyncTime(System.nanoTime())
                    .syncAndDraw();
        }

        void updateSurface() {
            this.mHardwareRenderer.setSurface(Surface.this, true);
        }

        void destroy() {
            this.mHardwareRenderer.destroy();
        }

        boolean isWideColorGamut() {
            return this.mIsWideColorGamut;
        }
    }

    private static void registerNativeMemoryUsage() {
        if (Flags.enableSurfaceNativeAllocRegistrationRo()) {
            VMRuntime.getRuntime().registerNativeAllocation(5000L);
        }
    }

    private static void freeNativeMemoryUsage() {
        if (Flags.enableSurfaceNativeAllocRegistrationRo()) {
            VMRuntime.getRuntime().registerNativeFree(5000L);
        }
    }
}
