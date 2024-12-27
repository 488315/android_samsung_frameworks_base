package android.window;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;

import com.android.window.flags.Flags;

import libcore.util.NativeAllocationRegistry;

import com.samsung.android.rune.CoreRune;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.ObjIntConsumer;

public class ScreenCapture {
    private static final int SCREENSHOT_WAIT_TIME_S = Build.HW_TIMEOUT_MULTIPLIER * 4;
    private static final String TAG = "ScreenCapture";

    public static native long getNativeListenerFinalizer();

    private static native int nativeCaptureDisplay(DisplayCaptureArgs displayCaptureArgs, long j);

    private static native int nativeCaptureLayers(
            LayerCaptureArgs layerCaptureArgs, long j, boolean z);

    public static native long nativeCreateScreenCaptureListener(
            ObjIntConsumer<ScreenshotHardwareBuffer> objIntConsumer);

    public static native long nativeReadListenerFromParcel(Parcel parcel);

    public static native void nativeWriteListenerToParcel(long j, Parcel parcel);

    public static int captureDisplay(
            DisplayCaptureArgs captureArgs, ScreenCaptureListener captureListener) {
        return nativeCaptureDisplay(captureArgs, captureListener.mNativeObject);
    }

    public static ScreenshotHardwareBuffer captureDisplay(DisplayCaptureArgs captureArgs) {
        SynchronousScreenCaptureListener syncScreenCapture = createSyncCaptureListener();
        int status = captureDisplay(captureArgs, syncScreenCapture);
        if (status != 0) {
            return null;
        }
        try {
            return syncScreenCapture.getBuffer();
        } catch (Exception e) {
            return null;
        }
    }

    public static ScreenshotHardwareBuffer captureLayers(
            SurfaceControl layer, Rect sourceCrop, float frameScale) {
        return captureLayers(layer, sourceCrop, frameScale, 1);
    }

    public static ScreenshotHardwareBuffer captureLayers(
            SurfaceControl layer, Rect sourceCrop, float frameScale, int format) {
        LayerCaptureArgs captureArgs =
                new LayerCaptureArgs.Builder(layer)
                        .setSourceCrop(sourceCrop)
                        .setFrameScale(frameScale)
                        .setPixelFormat(format)
                        .build();
        return captureLayers(captureArgs);
    }

    public static ScreenshotHardwareBuffer captureLayers(LayerCaptureArgs captureArgs) {
        SynchronousScreenCaptureListener syncScreenCapture = createSyncCaptureListener();
        int status =
                nativeCaptureLayers(
                        captureArgs, syncScreenCapture.mNativeObject, Flags.syncScreenCapture());
        if (status != 0) {
            return null;
        }
        try {
            return syncScreenCapture.getBuffer();
        } catch (Exception e) {
            return null;
        }
    }

    public static ScreenshotHardwareBuffer captureLayersExcluding(
            SurfaceControl layer,
            Rect sourceCrop,
            float frameScale,
            int format,
            SurfaceControl[] exclude) {
        return captureLayersExcluding(layer, sourceCrop, frameScale, format, exclude, false);
    }

    public static ScreenshotHardwareBuffer captureLayersExcluding(
            SurfaceControl layer,
            Rect sourceCrop,
            float frameScale,
            int format,
            SurfaceControl[] exclude,
            boolean captureSecureLayers) {
        LayerCaptureArgs captureArgs =
                new LayerCaptureArgs.Builder(layer)
                        .setSourceCrop(sourceCrop)
                        .setFrameScale(frameScale)
                        .setPixelFormat(format)
                        .setExcludeLayers(exclude)
                        .setIsScreenShotBySystem(CoreRune.FW_SCREENSHOT_FOR_HDR)
                        .setCaptureSecureLayers(captureSecureLayers)
                        .build();
        return captureLayers(captureArgs);
    }

    public static int captureLayers(
            LayerCaptureArgs captureArgs, ScreenCaptureListener captureListener) {
        return nativeCaptureLayers(captureArgs, captureListener.mNativeObject, false);
    }

    public static class ScreenshotHardwareBuffer {
        private final ColorSpace mColorSpace;
        private final boolean mContainsHdrLayers;
        private final boolean mContainsSecureLayers;
        private final HardwareBuffer mHardwareBuffer;

        public ScreenshotHardwareBuffer(
                HardwareBuffer hardwareBuffer,
                ColorSpace colorSpace,
                boolean containsSecureLayers,
                boolean containsHdrLayers) {
            this.mHardwareBuffer = hardwareBuffer;
            this.mColorSpace = colorSpace;
            this.mContainsSecureLayers = containsSecureLayers;
            this.mContainsHdrLayers = containsHdrLayers;
        }

        private static ScreenshotHardwareBuffer createFromNative(
                HardwareBuffer hardwareBuffer,
                int dataspace,
                boolean containsSecureLayers,
                boolean containsHdrLayers) {
            ColorSpace colorSpace = ColorSpace.getFromDataSpace(dataspace);
            return new ScreenshotHardwareBuffer(
                    hardwareBuffer,
                    colorSpace != null ? colorSpace : ColorSpace.get(ColorSpace.Named.SRGB),
                    containsSecureLayers,
                    containsHdrLayers);
        }

        public ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        public HardwareBuffer getHardwareBuffer() {
            return this.mHardwareBuffer;
        }

        public boolean containsSecureLayers() {
            return this.mContainsSecureLayers;
        }

        public boolean containsHdrLayers() {
            return this.mContainsHdrLayers;
        }

        public Bitmap asBitmap() {
            if (this.mHardwareBuffer == null) {
                Log.w(ScreenCapture.TAG, "Failed to take screenshot. Null screenshot object");
                return null;
            }
            return Bitmap.wrapHardwareBuffer(this.mHardwareBuffer, this.mColorSpace);
        }
    }

    public static class CaptureArgs implements Parcelable {
        public static final Parcelable.Creator<CaptureArgs> CREATOR =
                new Parcelable.Creator<
                        CaptureArgs>() { // from class: android.window.ScreenCapture.CaptureArgs.1
                    @Override // android.os.Parcelable.Creator
                    public CaptureArgs createFromParcel(Parcel in) {
                        return new CaptureArgs(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public CaptureArgs[] newArray(int size) {
                        return new CaptureArgs[size];
                    }
                };
        public final boolean mAllowProtected;
        public final boolean mCaptureSecureLayers;
        final SurfaceControl[] mExcludeLayers;
        public final float mFrameScaleX;
        public final float mFrameScaleY;
        public final boolean mGrayscale;
        public final boolean mHintForSeamlessTransition;
        public final boolean mIsScreenShotBySystem;
        public final int mPixelFormat;
        public final Rect mSourceCrop;
        public final long mUid;

        private CaptureArgs(Builder<? extends Builder<?>> builder) {
            this.mSourceCrop = new Rect();
            this.mPixelFormat = ((Builder) builder).mPixelFormat;
            this.mSourceCrop.set(((Builder) builder).mSourceCrop);
            this.mFrameScaleX = ((Builder) builder).mFrameScaleX;
            this.mFrameScaleY = ((Builder) builder).mFrameScaleY;
            this.mCaptureSecureLayers = ((Builder) builder).mCaptureSecureLayers;
            this.mAllowProtected = ((Builder) builder).mAllowProtected;
            this.mUid = ((Builder) builder).mUid;
            this.mGrayscale = ((Builder) builder).mGrayscale;
            this.mExcludeLayers = ((Builder) builder).mExcludeLayers;
            this.mHintForSeamlessTransition = ((Builder) builder).mHintForSeamlessTransition;
            this.mIsScreenShotBySystem = ((Builder) builder).mIsScreenShotBySystem;
        }

        private CaptureArgs(Parcel in) {
            this.mSourceCrop = new Rect();
            this.mPixelFormat = in.readInt();
            this.mSourceCrop.readFromParcel(in);
            this.mFrameScaleX = in.readFloat();
            this.mFrameScaleY = in.readFloat();
            this.mCaptureSecureLayers = in.readBoolean();
            this.mAllowProtected = in.readBoolean();
            this.mUid = in.readLong();
            this.mGrayscale = in.readBoolean();
            int excludeLayersLength = in.readInt();
            if (excludeLayersLength > 0) {
                this.mExcludeLayers = new SurfaceControl[excludeLayersLength];
                for (int index = 0; index < excludeLayersLength; index++) {
                    this.mExcludeLayers[index] = SurfaceControl.CREATOR.createFromParcel(in);
                }
            } else {
                this.mExcludeLayers = null;
            }
            this.mHintForSeamlessTransition = in.readBoolean();
            this.mIsScreenShotBySystem = in.readBoolean();
        }

        public void release() {
            if (this.mExcludeLayers == null || this.mExcludeLayers.length == 0) {
                return;
            }
            for (SurfaceControl surfaceControl : this.mExcludeLayers) {
                if (surfaceControl != null) {
                    surfaceControl.release();
                }
            }
        }

        private long[] getNativeExcludeLayers() {
            if (this.mExcludeLayers == null || this.mExcludeLayers.length == 0) {
                return new long[0];
            }
            long[] nativeExcludeLayers = new long[this.mExcludeLayers.length];
            for (int index = 0; index < this.mExcludeLayers.length; index++) {
                nativeExcludeLayers[index] = this.mExcludeLayers[index].mNativeObject;
            }
            return nativeExcludeLayers;
        }

        public static class Builder<T extends Builder<T>> {
            private boolean mAllowProtected;
            private boolean mCaptureSecureLayers;
            private SurfaceControl[] mExcludeLayers;
            private boolean mGrayscale;
            private boolean mHintForSeamlessTransition;
            private int mPixelFormat = 1;
            private final Rect mSourceCrop = new Rect();
            private float mFrameScaleX = 1.0f;
            private float mFrameScaleY = 1.0f;
            private long mUid = -1;
            private boolean mIsScreenShotBySystem = false;

            public CaptureArgs build() {
                return new CaptureArgs(this);
            }

            public T setPixelFormat(int pixelFormat) {
                this.mPixelFormat = pixelFormat;
                return getThis();
            }

            public T setSourceCrop(Rect sourceCrop) {
                if (sourceCrop == null) {
                    this.mSourceCrop.setEmpty();
                } else {
                    this.mSourceCrop.set(sourceCrop);
                }
                return getThis();
            }

            public T setFrameScale(float frameScale) {
                this.mFrameScaleX = frameScale;
                this.mFrameScaleY = frameScale;
                return getThis();
            }

            public T setFrameScale(float frameScaleX, float frameScaleY) {
                this.mFrameScaleX = frameScaleX;
                this.mFrameScaleY = frameScaleY;
                return getThis();
            }

            public T setCaptureSecureLayers(boolean captureSecureLayers) {
                this.mCaptureSecureLayers = captureSecureLayers;
                return getThis();
            }

            public T setAllowProtected(boolean allowProtected) {
                this.mAllowProtected = allowProtected;
                return getThis();
            }

            public T setUid(long uid) {
                this.mUid = uid;
                return getThis();
            }

            public T setGrayscale(boolean grayscale) {
                this.mGrayscale = grayscale;
                return getThis();
            }

            public T setExcludeLayers(SurfaceControl[] excludeLayers) {
                this.mExcludeLayers = excludeLayers;
                return getThis();
            }

            public T setHintForSeamlessTransition(boolean hintForSeamlessTransition) {
                this.mHintForSeamlessTransition = hintForSeamlessTransition;
                return getThis();
            }

            public T setIsScreenShotBySystem(boolean isScreenShotBySystem) {
                this.mIsScreenShotBySystem = isScreenShotBySystem;
                Log.d(
                        ScreenCapture.TAG,
                        "[Capture_TEST] : setIsScreenShotBySystem " + isScreenShotBySystem);
                return getThis();
            }

            T getThis() {
                return this;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mPixelFormat);
            this.mSourceCrop.writeToParcel(dest, flags);
            dest.writeFloat(this.mFrameScaleX);
            dest.writeFloat(this.mFrameScaleY);
            dest.writeBoolean(this.mCaptureSecureLayers);
            dest.writeBoolean(this.mAllowProtected);
            dest.writeLong(this.mUid);
            dest.writeBoolean(this.mGrayscale);
            if (this.mExcludeLayers != null) {
                dest.writeInt(this.mExcludeLayers.length);
                for (SurfaceControl excludeLayer : this.mExcludeLayers) {
                    excludeLayer.writeToParcel(dest, flags);
                }
            } else {
                dest.writeInt(0);
            }
            dest.writeBoolean(this.mHintForSeamlessTransition);
            dest.writeBoolean(this.mIsScreenShotBySystem);
        }
    }

    public static class DisplayCaptureArgs extends CaptureArgs {
        private final IBinder mDisplayToken;
        private final int mHeight;
        private final long mNativeLayer;
        private final boolean mUseIdentityTransform;
        private final int mWidth;

        private DisplayCaptureArgs(Builder builder) {
            super(builder);
            this.mDisplayToken = builder.mDisplayToken;
            this.mWidth = builder.mWidth;
            this.mHeight = builder.mHeight;
            this.mUseIdentityTransform = builder.mUseIdentityTransform;
            if (builder.mLayer != null) {
                this.mNativeLayer = builder.mLayer.mNativeObject;
            } else {
                this.mNativeLayer = 0L;
            }
        }

        public static class Builder extends CaptureArgs.Builder<Builder> {
            private IBinder mDisplayToken;
            private int mHeight;
            private SurfaceControl mLayer;
            private boolean mUseIdentityTransform;
            private int mWidth;

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public DisplayCaptureArgs build() {
                if (this.mDisplayToken == null) {
                    throw new IllegalStateException(
                            "Can't take screenshot with null display token");
                }
                return new DisplayCaptureArgs(this);
            }

            public Builder(IBinder displayToken) {
                setDisplayToken(displayToken);
            }

            public Builder setDisplayToken(IBinder displayToken) {
                this.mDisplayToken = displayToken;
                return this;
            }

            public Builder setSize(int width, int height) {
                this.mWidth = width;
                this.mHeight = height;
                return this;
            }

            public Builder setUseIdentityTransform(boolean useIdentityTransform) {
                this.mUseIdentityTransform = useIdentityTransform;
                return this;
            }

            public Builder setLayer(SurfaceControl layer) {
                this.mLayer = layer;
                return this;
            }

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public Builder getThis() {
                return this;
            }
        }
    }

    public static class LayerCaptureArgs extends CaptureArgs {
        private final boolean mChildrenOnly;
        private final long mNativeLayer;

        private LayerCaptureArgs(Builder builder) {
            super(builder);
            this.mChildrenOnly = builder.mChildrenOnly;
            this.mNativeLayer = builder.mLayer.mNativeObject;
        }

        public static class Builder extends CaptureArgs.Builder<Builder> {
            private boolean mChildrenOnly = true;
            private SurfaceControl mLayer;

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public LayerCaptureArgs build() {
                if (this.mLayer == null) {
                    throw new IllegalStateException("Can't take screenshot with null layer");
                }
                return new LayerCaptureArgs(this);
            }

            public Builder(SurfaceControl layer, CaptureArgs args) {
                setLayer(layer);
                setPixelFormat(args.mPixelFormat);
                setSourceCrop(args.mSourceCrop);
                setFrameScale(args.mFrameScaleX, args.mFrameScaleY);
                setCaptureSecureLayers(args.mCaptureSecureLayers);
                setAllowProtected(args.mAllowProtected);
                setUid(args.mUid);
                setGrayscale(args.mGrayscale);
                setExcludeLayers(args.mExcludeLayers);
                setHintForSeamlessTransition(args.mHintForSeamlessTransition);
            }

            public Builder(SurfaceControl layer) {
                setLayer(layer);
            }

            public Builder setLayer(SurfaceControl layer) {
                this.mLayer = layer;
                return this;
            }

            public Builder setChildrenOnly(boolean childrenOnly) {
                this.mChildrenOnly = childrenOnly;
                return this;
            }

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public Builder getThis() {
                return this;
            }
        }
    }

    public static class ScreenCaptureListener implements Parcelable {
        final long mNativeObject;
        private static final NativeAllocationRegistry sRegistry =
                NativeAllocationRegistry.createMalloced(
                        ScreenCaptureListener.class.getClassLoader(),
                        ScreenCapture.getNativeListenerFinalizer());
        public static final Parcelable.Creator<ScreenCaptureListener> CREATOR =
                new Parcelable.Creator<
                        ScreenCaptureListener>() { // from class:
                                                   // android.window.ScreenCapture.ScreenCaptureListener.1
                    @Override // android.os.Parcelable.Creator
                    public ScreenCaptureListener createFromParcel(Parcel in) {
                        return new ScreenCaptureListener(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public ScreenCaptureListener[] newArray(int size) {
                        return new ScreenCaptureListener[0];
                    }
                };

        public ScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
            this.mNativeObject = ScreenCapture.nativeCreateScreenCaptureListener(consumer);
            sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }

        private ScreenCaptureListener(Parcel in) {
            if (in.readBoolean()) {
                this.mNativeObject = ScreenCapture.nativeReadListenerFromParcel(in);
                sRegistry.registerNativeAllocation(this, this.mNativeObject);
            } else {
                this.mNativeObject = 0L;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            if (this.mNativeObject == 0) {
                dest.writeBoolean(false);
            } else {
                dest.writeBoolean(true);
                ScreenCapture.nativeWriteListenerToParcel(this.mNativeObject, dest);
            }
        }
    }

    public static SynchronousScreenCaptureListener createSyncCaptureListener() {
        final ScreenshotHardwareBuffer[] bufferRef = new ScreenshotHardwareBuffer[1];
        final CountDownLatch latch = new CountDownLatch(1);
        final ObjIntConsumer<ScreenshotHardwareBuffer> consumer =
                new ObjIntConsumer() { // from class:
                                       // android.window.ScreenCapture$$ExternalSyntheticLambda0
                    @Override // java.util.function.ObjIntConsumer
                    public final void accept(Object obj, int i) {
                        ScreenCapture.lambda$createSyncCaptureListener$0(
                                bufferRef, latch, (ScreenCapture.ScreenshotHardwareBuffer) obj, i);
                    }
                };
        return new SynchronousScreenCaptureListener(
                consumer) { // from class: android.window.ScreenCapture.1
            private ObjIntConsumer<ScreenshotHardwareBuffer> mConsumer;

            {
                this.mConsumer = consumer;
            }

            @Override // android.window.ScreenCapture.SynchronousScreenCaptureListener
            public ScreenshotHardwareBuffer getBuffer() {
                try {
                    if (!latch.await(ScreenCapture.SCREENSHOT_WAIT_TIME_S, TimeUnit.SECONDS)) {
                        Log.e(ScreenCapture.TAG, "Timed out waiting for screenshot results");
                        return null;
                    }
                    return bufferRef[0];
                } catch (Exception e) {
                    Log.e(ScreenCapture.TAG, "Failed to wait for screen capture result", e);
                    return null;
                }
            }
        };
    }

    static /* synthetic */ void lambda$createSyncCaptureListener$0(
            ScreenshotHardwareBuffer[] bufferRef,
            CountDownLatch latch,
            ScreenshotHardwareBuffer buffer,
            int status) {
        if (status != 0) {
            bufferRef[0] = null;
            Log.e(TAG, "Failed to generate screen capture. Error code: " + status);
        } else {
            bufferRef[0] = buffer;
        }
        latch.countDown();
    }

    public abstract static class SynchronousScreenCaptureListener extends ScreenCaptureListener {
        public abstract ScreenshotHardwareBuffer getBuffer();

        SynchronousScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> consumer) {
            super(consumer);
        }
    }
}
