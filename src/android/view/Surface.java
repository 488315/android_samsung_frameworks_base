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

/* loaded from: classes4.dex */
public class Surface implements Parcelable {
    public static final int CHANGE_FRAME_RATE_ALWAYS = 1;
    public static final int CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS = 0;
    public static final Parcelable.Creator<Surface> CREATOR = new Parcelable.Creator<Surface>() { // from class: android.view.Surface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Surface createFromParcel(Parcel parcel) {
            try {
                Surface surface = new Surface();
                surface.readFromParcel(parcel);
                return surface;
            } catch (Exception e) {
                Log.e(Surface.TAG, "Exception creating surface from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Surface[] newArray(int i) {
            return new Surface[i];
        }
    };
    public static final int FRAME_RATE_CATEGORY_DEFAULT = 0;
    public static final int FRAME_RATE_CATEGORY_DIRTY_HINT = 4;
    public static final int FRAME_RATE_CATEGORY_HIGH = 6;
    public static final int FRAME_RATE_CATEGORY_HIGH_HINT = 5;
    public static final int FRAME_RATE_CATEGORY_LOW = 2;
    public static final int FRAME_RATE_CATEGORY_NORMAL = 3;
    public static final int FRAME_RATE_CATEGORY_NO_PREFERENCE = 1;
    public static final int FRAME_RATE_COMPATIBILITY_AT_LEAST = 2;
    public static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    public static final int FRAME_RATE_COMPATIBILITY_EXACT = 100;
    public static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
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
    private final Canvas mCanvas;
    private final CloseGuard mCloseGuard;
    private Matrix mCompatibleMatrix;
    private int mGenerationId;
    private HwuiContext mHwuiContext;
    private boolean mIsAutoRefreshEnabled;
    private boolean mIsSharedBufferModeEnabled;
    private boolean mIsSingleBuffered;
    final Object mLock;
    private long mLockedObject;
    private String mName;
    long mNativeObject;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFrameRateStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateCompatibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScalingMode {
    }

    private static native void nativeAllocateBuffers(long j);

    private static native int nativeAttachAndQueueBufferWithColorSpace(long j, HardwareBuffer hardwareBuffer, int i);

    private static native long nativeCreateFromSurfaceControl(long j);

    private static native long nativeCreateFromSurfaceTexture(SurfaceTexture surfaceTexture) throws OutOfResourcesException;

    private static native void nativeDestroy(long j);

    private static native int nativeForceScopedDisconnect(long j);

    private static native long nativeGetFromBlastBufferQueue(long j, long j2);

    private static native long nativeGetFromSurfaceControl(long j, long j2);

    private static native int nativeGetHeight(long j);

    private static native long nativeGetNextFrameNumber(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeIsConsumerRunningBehind(long j);

    private static native boolean nativeIsValid(long j);

    private static native long nativeLockCanvas(long j, Canvas canvas, Rect rect) throws OutOfResourcesException;

    private static native long nativeReadFromParcel(long j, Parcel parcel);

    private static native void nativeRelease(long j);

    private static native int nativeSetAutoRefreshEnabled(long j, boolean z);

    private static native int nativeSetFrameRate(long j, float f, int i, int i2);

    private static native int nativeSetScalingMode(long j, int i);

    private static native int nativeSetSharedBufferModeEnabled(long j, boolean z);

    private static native void nativeUnlockCanvasAndPost(long j, Canvas canvas);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Surface() {
        this.mCloseGuard = CloseGuard.get();
        this.mLock = new Object();
        this.mCanvas = new CompatibleCanvas();
        registerNativeMemoryUsage();
    }

    public Surface(SurfaceControl surfaceControl) {
        this.mCloseGuard = CloseGuard.get();
        this.mLock = new Object();
        this.mCanvas = new CompatibleCanvas();
        copyFrom(surfaceControl);
        registerNativeMemoryUsage();
    }

    public Surface(SurfaceTexture surfaceTexture) {
        this.mCloseGuard = CloseGuard.get();
        Object obj = new Object();
        this.mLock = obj;
        this.mCanvas = new CompatibleCanvas();
        if (surfaceTexture == null) {
            throw new IllegalArgumentException("surfaceTexture must not be null");
        }
        this.mIsSingleBuffered = surfaceTexture.isSingleBuffered();
        synchronized (obj) {
            this.mName = surfaceTexture.toString();
            setNativeObjectLocked(nativeCreateFromSurfaceTexture(surfaceTexture));
        }
        registerNativeMemoryUsage();
    }

    private Surface(long j) {
        this.mCloseGuard = CloseGuard.get();
        Object obj = new Object();
        this.mLock = obj;
        this.mCanvas = new CompatibleCanvas();
        synchronized (obj) {
            setNativeObjectLocked(j);
        }
        registerNativeMemoryUsage();
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            release();
        } finally {
            super.finalize();
            freeNativeMemoryUsage();
        }
    }

    public void release() {
        synchronized (this.mLock) {
            HwuiContext hwuiContext = this.mHwuiContext;
            if (hwuiContext != null) {
                hwuiContext.destroy();
                this.mHwuiContext = null;
            }
            long j = this.mNativeObject;
            if (j != 0) {
                nativeRelease(j);
                setNativeObjectLocked(0L);
            }
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            long j = this.mNativeObject;
            if (j != 0) {
                nativeDestroy(j);
            }
            release();
        }
    }

    public void hwuiDestroy() {
        HwuiContext hwuiContext = this.mHwuiContext;
        if (hwuiContext != null) {
            hwuiContext.destroy();
            this.mHwuiContext = null;
        }
    }

    public boolean isValid() {
        synchronized (this.mLock) {
            long j = this.mNativeObject;
            if (j == 0) {
                return false;
            }
            return nativeIsValid(j);
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
            point = new Point(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return point;
    }

    public Canvas lockCanvas(Rect rect) throws OutOfResourcesException, IllegalArgumentException {
        Canvas canvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (this.mLockedObject != 0) {
                throw new IllegalArgumentException("Surface was already locked");
            }
            this.mLockedObject = nativeLockCanvas(this.mNativeObject, this.mCanvas, rect);
            canvas = this.mCanvas;
        }
        return canvas;
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            HwuiContext hwuiContext = this.mHwuiContext;
            if (hwuiContext != null) {
                hwuiContext.unlockAndPost(canvas);
            } else {
                unlockSwCanvasAndPost(canvas);
            }
        }
    }

    private void unlockSwCanvasAndPost(Canvas canvas) {
        if (canvas != this.mCanvas) {
            throw new IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
        }
        if (this.mNativeObject != this.mLockedObject) {
            Log.w(TAG, "WARNING: Surface's mNativeObject (0x" + Long.toHexString(this.mNativeObject) + ") != mLockedObject (0x" + Long.toHexString(this.mLockedObject) + NavigationBarInflaterView.KEY_CODE_END);
        }
        long j = this.mLockedObject;
        if (j == 0) {
            throw new IllegalStateException("Surface was not locked");
        }
        try {
            nativeUnlockCanvasAndPost(j, canvas);
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
            lockCanvas = this.mHwuiContext.lockCanvas(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    public Canvas lockHardwareWideColorGamutCanvas() {
        Canvas lockCanvas;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            HwuiContext hwuiContext = this.mHwuiContext;
            if (hwuiContext != null && !hwuiContext.isWideColorGamut()) {
                this.mHwuiContext.destroy();
                this.mHwuiContext = null;
            }
            if (this.mHwuiContext == null) {
                this.mHwuiContext = new HwuiContext(true);
            }
            lockCanvas = this.mHwuiContext.lockCanvas(nativeGetWidth(this.mNativeObject), nativeGetHeight(this.mNativeObject));
        }
        return lockCanvas;
    }

    @Deprecated
    public void unlockCanvas(Canvas canvas) {
        throw new UnsupportedOperationException();
    }

    void setCompatibilityTranslator(CompatibilityInfo.Translator translator) {
        if (translator != null) {
            float f = translator.applicationScale;
            Matrix matrix = new Matrix();
            this.mCompatibleMatrix = matrix;
            matrix.setScale(f, f);
        }
    }

    private void updateNativeObject(long j) {
        synchronized (this.mLock) {
            long j2 = this.mNativeObject;
            if (j == j2) {
                return;
            }
            if (j2 != 0) {
                nativeRelease(j2);
            }
            setNativeObjectLocked(j);
        }
    }

    public void copyFrom(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long j = surfaceControl.mNativeObject;
        if (j == 0) {
            throw new NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        updateNativeObject(nativeGetFromSurfaceControl(this.mNativeObject, j));
    }

    public void copyFrom(BLASTBufferQueue bLASTBufferQueue) {
        if (bLASTBufferQueue == null) {
            throw new IllegalArgumentException("queue must not be null");
        }
        long j = bLASTBufferQueue.mNativeObject;
        if (j == 0) {
            throw new NullPointerException("Null BLASTBufferQueue native object");
        }
        updateNativeObject(nativeGetFromBlastBufferQueue(this.mNativeObject, j));
    }

    public void createFrom(SurfaceControl surfaceControl) {
        if (surfaceControl == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        long j = surfaceControl.mNativeObject;
        if (j == 0) {
            throw new NullPointerException("null SurfaceControl native object. Are you using a released SurfaceControl?");
        }
        long nativeCreateFromSurfaceControl = nativeCreateFromSurfaceControl(j);
        synchronized (this.mLock) {
            long j2 = this.mNativeObject;
            if (j2 != 0) {
                nativeRelease(j2);
            }
            setNativeObjectLocked(nativeCreateFromSurfaceControl);
        }
    }

    @Deprecated
    public void transferFrom(Surface surface) {
        long j;
        if (surface == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (surface != this) {
            synchronized (surface.mLock) {
                j = surface.mNativeObject;
                surface.setNativeObjectLocked(0L);
            }
            synchronized (this.mLock) {
                long j2 = this.mNativeObject;
                if (j2 != 0) {
                    nativeRelease(j2);
                }
                setNativeObjectLocked(j);
            }
        }
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        synchronized (this.mLock) {
            this.mName = parcel.readString();
            this.mIsSingleBuffered = parcel.readInt() != 0;
            setNativeObjectLocked(nativeReadFromParcel(this.mNativeObject, parcel));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            throw new IllegalArgumentException("dest must not be null");
        }
        synchronized (this.mLock) {
            parcel.writeString(this.mName);
            parcel.writeInt(this.mIsSingleBuffered ? 1 : 0);
            nativeWriteToParcel(this.mNativeObject, parcel);
        }
        if ((i & 1) != 0) {
            release();
        }
    }

    public String toString() {
        String str;
        synchronized (this.mLock) {
            str = "Surface(name=" + this.mName + " mNativeObject=" + this.mNativeObject + ")/@0x" + Integer.toHexString(System.identityHashCode(this));
        }
        return str;
    }

    private void setNativeObjectLocked(long j) {
        long j2 = this.mNativeObject;
        if (j2 != j) {
            if (j2 == 0 && j != 0) {
                this.mCloseGuard.open("Surface.release");
            } else if (j2 != 0 && j == 0) {
                this.mCloseGuard.close();
            }
            this.mNativeObject = j;
            this.mGenerationId++;
            HwuiContext hwuiContext = this.mHwuiContext;
            if (hwuiContext != null) {
                hwuiContext.updateSurface();
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

    public void setScalingMode(int i) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (nativeSetScalingMode(this.mNativeObject, i) != 0) {
                throw new IllegalArgumentException("Invalid scaling mode: " + i);
            }
        }
    }

    void forceScopedDisconnect() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (nativeForceScopedDisconnect(this.mNativeObject) != 0) {
                throw new RuntimeException("Failed to disconnect Surface instance (bad object?)");
            }
        }
    }

    public void attachAndQueueBufferWithColorSpace(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            if (colorSpace == null) {
                colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
            int nativeAttachAndQueueBufferWithColorSpace = nativeAttachAndQueueBufferWithColorSpace(this.mNativeObject, hardwareBuffer, colorSpace.getId());
            if (nativeAttachAndQueueBufferWithColorSpace != 0) {
                throw new RuntimeException("Failed to attach and queue buffer to Surface (bad object?), native error: " + nativeAttachAndQueueBufferWithColorSpace);
            }
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }

    public void setSharedBufferModeEnabled(boolean z) {
        if (this.mIsSharedBufferModeEnabled != z) {
            if (nativeSetSharedBufferModeEnabled(this.mNativeObject, z) != 0) {
                throw new RuntimeException("Failed to set shared buffer mode on Surface (bad object?)");
            }
            this.mIsSharedBufferModeEnabled = z;
        }
    }

    public boolean isSharedBufferModeEnabled() {
        return this.mIsSharedBufferModeEnabled;
    }

    public void setAutoRefreshEnabled(boolean z) {
        if (this.mIsAutoRefreshEnabled != z) {
            if (nativeSetAutoRefreshEnabled(this.mNativeObject, z) != 0) {
                throw new RuntimeException("Failed to set auto refresh on Surface (bad object?)");
            }
            this.mIsAutoRefreshEnabled = z;
        }
    }

    public boolean isAutoRefreshEnabled() {
        return this.mIsAutoRefreshEnabled;
    }

    public static class FrameRateParams {
        public static final FrameRateParams IGNORE = new Builder().setDesiredRateRange(0.0f, Float.MAX_VALUE).build();
        int mChangeFrameRateStrategy;
        float mDesiredMaxRate;
        float mDesiredMinRate;
        float mFixedSourceRate;

        private FrameRateParams() {
        }

        public static final class Builder {
            private int mChangeFrameRateStrategy;
            private float mDesiredMaxRate;
            private float mDesiredMinRate;
            private float mFixedSourceRate;

            public Builder setDesiredRateRange(float f, float f2) {
                if (f2 < f) {
                    Log.e(Surface.TAG, "Failed to set desired frame rate range. desiredMaxRate should be greater than or equal to desiredMinRate");
                    return this;
                }
                this.mDesiredMinRate = f;
                this.mDesiredMaxRate = f2;
                return this;
            }

            public Builder setFixedSourceRate(float f) {
                this.mFixedSourceRate = f;
                return this;
            }

            public Builder setChangeFrameRateStrategy(int i) {
                this.mChangeFrameRateStrategy = i;
                return this;
            }

            public FrameRateParams build() {
                FrameRateParams frameRateParams = new FrameRateParams();
                frameRateParams.mDesiredMinRate = this.mDesiredMinRate;
                frameRateParams.mDesiredMaxRate = this.mDesiredMaxRate;
                frameRateParams.mFixedSourceRate = this.mFixedSourceRate;
                frameRateParams.mChangeFrameRateStrategy = this.mChangeFrameRateStrategy;
                return frameRateParams;
            }
        }

        public float getDesiredMinRate() {
            return this.mDesiredMinRate;
        }

        public float getDesiredMaxRate() {
            return this.mDesiredMaxRate;
        }

        public float getFixedSourceRate() {
            return this.mFixedSourceRate;
        }

        public int getChangeFrameRateStrategy() {
            return this.mChangeFrameRateStrategy;
        }
    }

    public void setFrameRate(FrameRateParams frameRateParams) {
        float fixedSourceRate;
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int i = frameRateParams.getFixedSourceRate() == 0.0f ? 0 : 1;
            if (i == 0) {
                fixedSourceRate = frameRateParams.getDesiredMinRate();
            } else {
                fixedSourceRate = frameRateParams.getFixedSourceRate();
            }
            int nativeSetFrameRate = nativeSetFrameRate(this.mNativeObject, fixedSourceRate, i, frameRateParams.getChangeFrameRateStrategy());
            if (nativeSetFrameRate == (-OsConstants.EINVAL)) {
                throw new IllegalArgumentException("Invalid argument to Surface.setFrameRate()");
            }
            if (nativeSetFrameRate != 0) {
                Log.e(TAG, "Failed to set frame rate on Surface. Native error: " + nativeSetFrameRate);
            }
        }
    }

    public void setFrameRate(float f, int i, int i2) {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int nativeSetFrameRate = nativeSetFrameRate(this.mNativeObject, f, i, i2);
            if (nativeSetFrameRate == (-OsConstants.EINVAL)) {
                throw new IllegalArgumentException("Invalid argument to Surface.setFrameRate()");
            }
            if (nativeSetFrameRate != 0) {
                Log.e(TAG, "Failed to set frame rate on Surface. Native error: " + nativeSetFrameRate);
            }
        }
    }

    public void clearFrameRate() {
        synchronized (this.mLock) {
            checkNotReleasedLocked();
            int nativeSetFrameRate = nativeSetFrameRate(this.mNativeObject, 0.0f, 0, 0);
            if (nativeSetFrameRate != 0) {
                throw new RuntimeException("Failed to clear the frame rate on Surface. Native error: " + nativeSetFrameRate);
            }
        }
    }

    public void setFrameRate(float f, int i) {
        setFrameRate(f, i, 0);
    }

    public static class OutOfResourcesException extends RuntimeException {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(String str) {
            super(str);
        }
    }

    public static String rotationToString(int i) {
        if (i == 0) {
            return "ROTATION_0";
        }
        if (i == 1) {
            return "ROTATION_90";
        }
        if (i == 2) {
            return "ROTATION_180";
        }
        if (i == 3) {
            return "ROTATION_270";
        }
        return Integer.toString(i);
    }

    private final class CompatibleCanvas extends Canvas {
        private Matrix mOrigMatrix;

        private CompatibleCanvas() {
            this.mOrigMatrix = null;
        }

        @Override // android.graphics.Canvas
        public void setMatrix(Matrix matrix) {
            Matrix matrix2;
            if (Surface.this.mCompatibleMatrix == null || (matrix2 = this.mOrigMatrix) == null || matrix2.equals(matrix)) {
                super.setMatrix(matrix);
                return;
            }
            Matrix matrix3 = new Matrix(Surface.this.mCompatibleMatrix);
            matrix3.preConcat(matrix);
            super.setMatrix(matrix3);
        }

        @Override // android.graphics.Canvas
        public void getMatrix(Matrix matrix) {
            super.getMatrix(matrix);
            if (this.mOrigMatrix == null) {
                this.mOrigMatrix = new Matrix();
            }
            this.mOrigMatrix.set(matrix);
        }
    }

    private final class HwuiContext {
        private RecordingCanvas mCanvas;
        private HardwareRenderer mHardwareRenderer;
        private final boolean mIsWideColorGamut;
        private final RenderNode mRenderNode;

        HwuiContext(boolean z) {
            RenderNode create = RenderNode.create("HwuiCanvas", null);
            this.mRenderNode = create;
            create.setClipToBounds(false);
            create.setForceDarkAllowed(false);
            this.mIsWideColorGamut = z;
            HardwareRenderer hardwareRenderer = new HardwareRenderer();
            this.mHardwareRenderer = hardwareRenderer;
            hardwareRenderer.setContentRoot(create);
            this.mHardwareRenderer.setSurface(Surface.this, true);
            this.mHardwareRenderer.setColorMode(z ? 1 : 0);
            this.mHardwareRenderer.setLightSourceAlpha(0.0f, 0.0f);
            this.mHardwareRenderer.setLightSourceGeometry(0.0f, 0.0f, 0.0f, 0.0f);
        }

        Canvas lockCanvas(int i, int i2) {
            if (this.mCanvas != null) {
                throw new IllegalStateException("Surface was already locked!");
            }
            RecordingCanvas beginRecording = this.mRenderNode.beginRecording(i, i2);
            this.mCanvas = beginRecording;
            return beginRecording;
        }

        void unlockAndPost(Canvas canvas) {
            if (canvas != this.mCanvas) {
                throw new IllegalArgumentException("canvas object must be the same instance that was previously returned by lockCanvas");
            }
            this.mRenderNode.endRecording();
            this.mCanvas = null;
            this.mHardwareRenderer.createRenderRequest().setVsyncTime(System.nanoTime()).syncAndDraw();
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
