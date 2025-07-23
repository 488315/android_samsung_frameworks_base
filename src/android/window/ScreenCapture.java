package android.window;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Gainmap;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.ObjIntConsumer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes5.dex */
public class ScreenCapture {
    private static final int SCREENSHOT_WAIT_TIME_S = Build.HW_TIMEOUT_MULTIPLIER * 4;
    private static final String TAG = "ScreenCapture";

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeListenerFinalizer();

    private static native int nativeCaptureDisplay(DisplayCaptureArgs displayCaptureArgs, long j);

    private static native int nativeCaptureLayers(LayerCaptureArgs layerCaptureArgs, long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> objIntConsumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadListenerFromParcel(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteListenerToParcel(long j, Parcel parcel);

    public static int captureDisplay(DisplayCaptureArgs displayCaptureArgs, ScreenCaptureListener screenCaptureListener) {
        return nativeCaptureDisplay(displayCaptureArgs, screenCaptureListener.mNativeObject);
    }

    public static ScreenshotHardwareBuffer captureDisplay(DisplayCaptureArgs displayCaptureArgs) {
        SynchronousScreenCaptureListener createSyncCaptureListener = createSyncCaptureListener();
        if (captureDisplay(displayCaptureArgs, createSyncCaptureListener) != 0) {
            return null;
        }
        try {
            return createSyncCaptureListener.getBuffer();
        } catch (Exception unused) {
            return null;
        }
    }

    public static ScreenshotHardwareBuffer captureLayers(SurfaceControl surfaceControl, Rect rect, float f) {
        return captureLayers(surfaceControl, rect, f, 1);
    }

    public static ScreenshotHardwareBuffer captureLayers(SurfaceControl surfaceControl, Rect rect, float f, int i) {
        return captureLayers(new LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(f).setPixelFormat(i).build());
    }

    public static ScreenshotHardwareBuffer captureLayers(LayerCaptureArgs layerCaptureArgs) {
        SynchronousScreenCaptureListener createSyncCaptureListener = createSyncCaptureListener();
        if (nativeCaptureLayers(layerCaptureArgs, createSyncCaptureListener.mNativeObject, Flags.syncScreenCapture()) != 0) {
            return null;
        }
        try {
            return createSyncCaptureListener.getBuffer();
        } catch (Exception unused) {
            return null;
        }
    }

    public static ScreenshotHardwareBuffer captureLayersExcluding(SurfaceControl surfaceControl, Rect rect, float f, int i, SurfaceControl[] surfaceControlArr) {
        return captureLayers(new LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(f).setPixelFormat(i).setExcludeLayers(surfaceControlArr).setIsScreenShotBySystem(CoreRune.FW_SCREENSHOT_FOR_HDR).build());
    }

    public static int captureLayers(LayerCaptureArgs layerCaptureArgs, ScreenCaptureListener screenCaptureListener) {
        return nativeCaptureLayers(layerCaptureArgs, screenCaptureListener.mNativeObject, false);
    }

    public static class ScreenshotHardwareBuffer {
        private static final float EPSILON = 0.015625f;
        private final ColorSpace mColorSpace;
        private final boolean mContainsHdrLayers;
        private final boolean mContainsSecureLayers;
        private final HardwareBuffer mGainmap;
        private final HardwareBuffer mHardwareBuffer;
        private final float mHdrSdrRatio;

        public ScreenshotHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace, boolean z, boolean z2) {
            this(hardwareBuffer, colorSpace, z, z2, null, 1.0f);
        }

        public ScreenshotHardwareBuffer(HardwareBuffer hardwareBuffer, ColorSpace colorSpace, boolean z, boolean z2, HardwareBuffer hardwareBuffer2, float f) {
            this.mHardwareBuffer = hardwareBuffer;
            this.mColorSpace = colorSpace;
            this.mContainsSecureLayers = z;
            this.mContainsHdrLayers = z2;
            this.mGainmap = hardwareBuffer2;
            this.mHdrSdrRatio = f;
        }

        private static ScreenshotHardwareBuffer createFromNative(HardwareBuffer hardwareBuffer, int i, boolean z, boolean z2, HardwareBuffer hardwareBuffer2, float f) {
            ColorSpace fromDataSpace = ColorSpace.getFromDataSpace(i);
            if (fromDataSpace == null) {
                fromDataSpace = ColorSpace.get(ColorSpace.Named.SRGB);
            }
            return new ScreenshotHardwareBuffer(hardwareBuffer, fromDataSpace, z, z2, hardwareBuffer2, f);
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
            HardwareBuffer hardwareBuffer = this.mHardwareBuffer;
            if (hardwareBuffer == null) {
                Log.w(ScreenCapture.TAG, "Failed to take screenshot. Null screenshot object");
                return null;
            }
            Bitmap wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, this.mColorSpace);
            HardwareBuffer hardwareBuffer2 = this.mGainmap;
            if (hardwareBuffer2 != null) {
                Gainmap gainmap = new Gainmap(Bitmap.wrapHardwareBuffer(hardwareBuffer2, null));
                gainmap.setRatioMin(1.0f, 1.0f, 1.0f);
                float f = this.mHdrSdrRatio;
                gainmap.setRatioMax(f, f, f);
                gainmap.setGamma(1.0f, 1.0f, 1.0f);
                gainmap.setEpsilonSdr(EPSILON, EPSILON, EPSILON);
                gainmap.setEpsilonHdr(EPSILON, EPSILON, EPSILON);
                gainmap.setMinDisplayRatioForHdrTransition(1.0f);
                gainmap.setDisplayRatioForFullHdr(this.mHdrSdrRatio);
                wrapHardwareBuffer.setGainmap(gainmap);
            }
            return wrapHardwareBuffer;
        }
    }

    public static class CaptureArgs implements Parcelable {
        public static final Parcelable.Creator<CaptureArgs> CREATOR = new Parcelable.Creator<CaptureArgs>() { // from class: android.window.ScreenCapture.CaptureArgs.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CaptureArgs createFromParcel(Parcel parcel) {
                return new CaptureArgs(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CaptureArgs[] newArray(int i) {
                return new CaptureArgs[i];
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private CaptureArgs(Builder<? extends Builder<?>> builder) {
            Rect rect = new Rect();
            this.mSourceCrop = rect;
            this.mPixelFormat = ((Builder) builder).mPixelFormat;
            rect.set(((Builder) builder).mSourceCrop);
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

        private CaptureArgs(Parcel parcel) {
            Rect rect = new Rect();
            this.mSourceCrop = rect;
            this.mPixelFormat = parcel.readInt();
            rect.readFromParcel(parcel);
            this.mFrameScaleX = parcel.readFloat();
            this.mFrameScaleY = parcel.readFloat();
            this.mCaptureSecureLayers = parcel.readBoolean();
            this.mAllowProtected = parcel.readBoolean();
            this.mUid = parcel.readLong();
            this.mGrayscale = parcel.readBoolean();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.mExcludeLayers = new SurfaceControl[readInt];
                for (int i = 0; i < readInt; i++) {
                    this.mExcludeLayers[i] = SurfaceControl.CREATOR.createFromParcel(parcel);
                }
            } else {
                this.mExcludeLayers = null;
            }
            this.mHintForSeamlessTransition = parcel.readBoolean();
            this.mIsScreenShotBySystem = parcel.readBoolean();
        }

        public void release() {
            SurfaceControl[] surfaceControlArr = this.mExcludeLayers;
            if (surfaceControlArr == null || surfaceControlArr.length == 0) {
                return;
            }
            for (SurfaceControl surfaceControl : surfaceControlArr) {
                if (surfaceControl != null) {
                    surfaceControl.release();
                }
            }
        }

        private long[] getNativeExcludeLayers() {
            SurfaceControl[] surfaceControlArr = this.mExcludeLayers;
            int i = 0;
            if (surfaceControlArr == null || surfaceControlArr.length == 0) {
                return new long[0];
            }
            long[] jArr = new long[surfaceControlArr.length];
            while (true) {
                SurfaceControl[] surfaceControlArr2 = this.mExcludeLayers;
                if (i >= surfaceControlArr2.length) {
                    return jArr;
                }
                jArr[i] = surfaceControlArr2[i].mNativeObject;
                i++;
            }
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

            T getThis() {
                return this;
            }

            public CaptureArgs build() {
                return new CaptureArgs(this);
            }

            public T setPixelFormat(int i) {
                this.mPixelFormat = i;
                return getThis();
            }

            public T setSourceCrop(Rect rect) {
                if (rect == null) {
                    this.mSourceCrop.setEmpty();
                } else {
                    this.mSourceCrop.set(rect);
                }
                return getThis();
            }

            public T setFrameScale(float f) {
                this.mFrameScaleX = f;
                this.mFrameScaleY = f;
                return getThis();
            }

            public T setFrameScale(float f, float f2) {
                this.mFrameScaleX = f;
                this.mFrameScaleY = f2;
                return getThis();
            }

            public T setCaptureSecureLayers(boolean z) {
                this.mCaptureSecureLayers = z;
                return getThis();
            }

            public T setAllowProtected(boolean z) {
                this.mAllowProtected = z;
                return getThis();
            }

            public T setUid(long j) {
                this.mUid = j;
                return getThis();
            }

            public T setGrayscale(boolean z) {
                this.mGrayscale = z;
                return getThis();
            }

            public T setExcludeLayers(SurfaceControl[] surfaceControlArr) {
                this.mExcludeLayers = surfaceControlArr;
                return getThis();
            }

            public T setHintForSeamlessTransition(boolean z) {
                this.mHintForSeamlessTransition = z;
                return getThis();
            }

            public T setIsScreenShotBySystem(boolean z) {
                this.mIsScreenShotBySystem = z;
                Log.d(ScreenCapture.TAG, "[Capture_TEST] : setIsScreenShotBySystem " + z);
                return getThis();
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mPixelFormat);
            this.mSourceCrop.writeToParcel(parcel, i);
            parcel.writeFloat(this.mFrameScaleX);
            parcel.writeFloat(this.mFrameScaleY);
            parcel.writeBoolean(this.mCaptureSecureLayers);
            parcel.writeBoolean(this.mAllowProtected);
            parcel.writeLong(this.mUid);
            parcel.writeBoolean(this.mGrayscale);
            SurfaceControl[] surfaceControlArr = this.mExcludeLayers;
            if (surfaceControlArr != null) {
                parcel.writeInt(surfaceControlArr.length);
                for (SurfaceControl surfaceControl : this.mExcludeLayers) {
                    surfaceControl.writeToParcel(parcel, i);
                }
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBoolean(this.mHintForSeamlessTransition);
            parcel.writeBoolean(this.mIsScreenShotBySystem);
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

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public Builder getThis() {
                return this;
            }

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public DisplayCaptureArgs build() {
                if (this.mDisplayToken == null) {
                    throw new IllegalStateException("Can't take screenshot with null display token");
                }
                return new DisplayCaptureArgs(this);
            }

            public Builder(IBinder iBinder) {
                setDisplayToken(iBinder);
            }

            public Builder setDisplayToken(IBinder iBinder) {
                this.mDisplayToken = iBinder;
                return this;
            }

            public Builder setSize(int i, int i2) {
                this.mWidth = i;
                this.mHeight = i2;
                return this;
            }

            public Builder setUseIdentityTransform(boolean z) {
                this.mUseIdentityTransform = z;
                return this;
            }

            public Builder setLayer(SurfaceControl surfaceControl) {
                this.mLayer = surfaceControl;
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

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public Builder getThis() {
                return this;
            }

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public LayerCaptureArgs build() {
                if (this.mLayer == null) {
                    throw new IllegalStateException("Can't take screenshot with null layer");
                }
                return new LayerCaptureArgs(this);
            }

            public Builder(SurfaceControl surfaceControl, CaptureArgs captureArgs) {
                setLayer(surfaceControl);
                setPixelFormat(captureArgs.mPixelFormat);
                setSourceCrop(captureArgs.mSourceCrop);
                setFrameScale(captureArgs.mFrameScaleX, captureArgs.mFrameScaleY);
                setCaptureSecureLayers(captureArgs.mCaptureSecureLayers);
                setAllowProtected(captureArgs.mAllowProtected);
                setUid(captureArgs.mUid);
                setGrayscale(captureArgs.mGrayscale);
                setExcludeLayers(captureArgs.mExcludeLayers);
                setHintForSeamlessTransition(captureArgs.mHintForSeamlessTransition);
            }

            public Builder(SurfaceControl surfaceControl) {
                setLayer(surfaceControl);
            }

            public Builder setLayer(SurfaceControl surfaceControl) {
                this.mLayer = surfaceControl;
                return this;
            }

            public Builder setChildrenOnly(boolean z) {
                this.mChildrenOnly = z;
                return this;
            }
        }
    }

    public static class ScreenCaptureListener implements Parcelable {
        final long mNativeObject;
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ScreenCaptureListener.class.getClassLoader(), ScreenCapture.getNativeListenerFinalizer());
        public static final Parcelable.Creator<ScreenCaptureListener> CREATOR = new Parcelable.Creator<ScreenCaptureListener>() { // from class: android.window.ScreenCapture.ScreenCaptureListener.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ScreenCaptureListener createFromParcel(Parcel parcel) {
                return new ScreenCaptureListener(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ScreenCaptureListener[] newArray(int i) {
                return new ScreenCaptureListener[0];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> objIntConsumer) {
            long nativeCreateScreenCaptureListener = ScreenCapture.nativeCreateScreenCaptureListener(objIntConsumer);
            this.mNativeObject = nativeCreateScreenCaptureListener;
            sRegistry.registerNativeAllocation(this, nativeCreateScreenCaptureListener);
        }

        private ScreenCaptureListener(Parcel parcel) {
            if (parcel.readBoolean()) {
                long nativeReadListenerFromParcel = ScreenCapture.nativeReadListenerFromParcel(parcel);
                this.mNativeObject = nativeReadListenerFromParcel;
                sRegistry.registerNativeAllocation(this, nativeReadListenerFromParcel);
                return;
            }
            this.mNativeObject = 0L;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            if (this.mNativeObject == 0) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                ScreenCapture.nativeWriteListenerToParcel(this.mNativeObject, parcel);
            }
        }
    }

    public static SynchronousScreenCaptureListener createSyncCaptureListener() {
        final ScreenshotHardwareBuffer[] screenshotHardwareBufferArr = new ScreenshotHardwareBuffer[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ObjIntConsumer objIntConsumer = new ObjIntConsumer() { // from class: android.window.ScreenCapture$$ExternalSyntheticLambda0
            @Override // java.util.function.ObjIntConsumer
            public final void accept(Object obj, int i) {
                ScreenCapture.lambda$createSyncCaptureListener$0(screenshotHardwareBufferArr, countDownLatch, (ScreenCapture.ScreenshotHardwareBuffer) obj, i);
            }
        };
        return new SynchronousScreenCaptureListener(objIntConsumer, objIntConsumer, countDownLatch, screenshotHardwareBufferArr) { // from class: android.window.ScreenCapture.1
            private ObjIntConsumer<ScreenshotHardwareBuffer> mConsumer;
            final /* synthetic */ ScreenshotHardwareBuffer[] val$bufferRef;
            final /* synthetic */ ObjIntConsumer val$consumer;
            final /* synthetic */ CountDownLatch val$latch;

            {
                this.val$consumer = objIntConsumer;
                this.val$latch = countDownLatch;
                this.val$bufferRef = screenshotHardwareBufferArr;
                this.mConsumer = objIntConsumer;
            }

            @Override // android.window.ScreenCapture.SynchronousScreenCaptureListener
            public ScreenshotHardwareBuffer getBuffer() {
                try {
                    if (!this.val$latch.await(ScreenCapture.SCREENSHOT_WAIT_TIME_S, TimeUnit.SECONDS)) {
                        Log.e(ScreenCapture.TAG, "Timed out waiting for screenshot results");
                        return null;
                    }
                    return this.val$bufferRef[0];
                } catch (Exception e) {
                    Log.e(ScreenCapture.TAG, "Failed to wait for screen capture result", e);
                    return null;
                }
            }
        };
    }

    static /* synthetic */ void lambda$createSyncCaptureListener$0(ScreenshotHardwareBuffer[] screenshotHardwareBufferArr, CountDownLatch countDownLatch, ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (i != 0) {
            screenshotHardwareBufferArr[0] = null;
            Log.e(TAG, "Failed to generate screen capture. Error code: " + i);
        } else {
            screenshotHardwareBufferArr[0] = screenshotHardwareBuffer;
        }
        countDownLatch.countDown();
    }

    public static abstract class SynchronousScreenCaptureListener extends ScreenCaptureListener {
        public abstract ScreenshotHardwareBuffer getBuffer();

        SynchronousScreenCaptureListener(ObjIntConsumer<ScreenshotHardwareBuffer> objIntConsumer) {
            super(objIntConsumer);
        }
    }
}
