package android.graphics;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;
import android.view.Surface;
import android.view.flags.Flags;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class SurfaceTexture {
    private final Looper mCreatorLooper;
    private long mFrameAvailableListener;
    private boolean mIsSingleBuffered;
    private Handler mOnFrameAvailableHandler;
    private Handler mOnSetFrameRateHandler;
    private long mProducer;
    private long mSurfaceTexture;

    public interface OnFrameAvailableListener {
        void onFrameAvailable(SurfaceTexture surfaceTexture);
    }

    public interface OnSetFrameRateListener {
        void onSetFrameRate(SurfaceTexture surfaceTexture, float f, int i, int i2);
    }

    private native int nativeAttachToGLContext(int i);

    private native int nativeDetachFromGLContext();

    private native void nativeFinalize();

    private native int nativeGetDataSpace();

    private native long nativeGetTimestamp();

    private native void nativeGetTransformMatrix(float[] fArr);

    private native void nativeInit(boolean z, int i, boolean z2, WeakReference<SurfaceTexture> weakReference) throws Surface.OutOfResourcesException;

    private native boolean nativeIsReleased();

    private native void nativeRelease();

    private native void nativeReleaseTexImage();

    private native void nativeSetDefaultBufferSize(int i, int i2);

    private native void nativeUpdateTexImage();

    @Deprecated
    public static class OutOfResourcesException extends Exception {
        public OutOfResourcesException() {
        }

        public OutOfResourcesException(String name) {
            super(name);
        }
    }

    public SurfaceTexture(int texName) {
        this(texName, false);
    }

    public SurfaceTexture(int texName, boolean singleBufferMode) {
        this.mCreatorLooper = Looper.myLooper();
        this.mIsSingleBuffered = singleBufferMode;
        nativeInit(false, texName, singleBufferMode, new WeakReference<>(this));
    }

    public SurfaceTexture(boolean singleBufferMode) {
        this.mCreatorLooper = Looper.myLooper();
        this.mIsSingleBuffered = singleBufferMode;
        nativeInit(true, 0, singleBufferMode, new WeakReference<>(this));
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener listener) {
        setOnFrameAvailableListener(listener, null);
    }

    public void setOnFrameAvailableListener(final OnFrameAvailableListener listener, Handler handler) {
        Looper looper;
        if (listener != null) {
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = this.mCreatorLooper != null ? this.mCreatorLooper : Looper.getMainLooper();
            }
            this.mOnFrameAvailableHandler = new Handler(looper, null, true) { // from class: android.graphics.SurfaceTexture.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    listener.onFrameAvailable(SurfaceTexture.this);
                }
            };
            return;
        }
        this.mOnFrameAvailableHandler = null;
    }

    private static class SetFrameRateArgs {
        final int mChangeFrameRateStrategy;
        final int mCompatibility;
        final float mFrameRate;

        SetFrameRateArgs(float frameRate, int compatibility, int changeFrameRateStrategy) {
            this.mFrameRate = frameRate;
            this.mCompatibility = compatibility;
            this.mChangeFrameRateStrategy = changeFrameRateStrategy;
        }
    }

    public void setOnSetFrameRateListener(final OnSetFrameRateListener listener, Handler handler) {
        Looper looper;
        if (listener != null) {
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = this.mCreatorLooper != null ? this.mCreatorLooper : Looper.getMainLooper();
            }
            this.mOnSetFrameRateHandler = new Handler(looper, null, true) { // from class: android.graphics.SurfaceTexture.2
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    Trace.traceBegin(8L, "onSetFrameRateHandler");
                    try {
                        SetFrameRateArgs args = (SetFrameRateArgs) msg.obj;
                        listener.onSetFrameRate(SurfaceTexture.this, args.mFrameRate, args.mCompatibility, args.mChangeFrameRateStrategy);
                    } finally {
                        Trace.traceEnd(8L);
                    }
                }
            };
            return;
        }
        this.mOnSetFrameRateHandler = null;
    }

    public void setDefaultBufferSize(int width, int height) {
        nativeSetDefaultBufferSize(width, height);
    }

    public void updateTexImage() {
        nativeUpdateTexImage();
    }

    public void releaseTexImage() {
        nativeReleaseTexImage();
    }

    public void detachFromGLContext() {
        int err = nativeDetachFromGLContext();
        if (err != 0) {
            throw new RuntimeException("Error during detachFromGLContext (see logcat for details)");
        }
    }

    public void attachToGLContext(int texName) {
        int err = nativeAttachToGLContext(texName);
        if (err != 0) {
            throw new RuntimeException("Error during attachToGLContext (see logcat for details)");
        }
    }

    public void getTransformMatrix(float[] mtx) {
        if (mtx.length != 16) {
            throw new IllegalArgumentException();
        }
        nativeGetTransformMatrix(mtx);
    }

    public long getTimestamp() {
        return nativeGetTimestamp();
    }

    public int getDataSpace() {
        return nativeGetDataSpace();
    }

    public void release() {
        nativeRelease();
    }

    public boolean isReleased() {
        return nativeIsReleased();
    }

    protected void finalize() throws Throwable {
        try {
            nativeFinalize();
        } finally {
            super.finalize();
        }
    }

    private static void postEventFromNative(WeakReference<SurfaceTexture> weakSelf) {
        Handler handler;
        SurfaceTexture st = weakSelf.get();
        if (st != null && (handler = st.mOnFrameAvailableHandler) != null) {
            handler.sendEmptyMessage(0);
        }
    }

    private static void postOnSetFrameRateEventFromNative(WeakReference<SurfaceTexture> weakSelf, float frameRate, int compatibility, int changeFrameRateStrategy) {
        SurfaceTexture st;
        Handler handler;
        Trace.traceBegin(8L, "postOnSetFrameRateEventFromNative");
        try {
            if (Flags.toolkitSetFrameRateReadOnly() && (st = weakSelf.get()) != null && (handler = st.mOnSetFrameRateHandler) != null) {
                Message msg = new Message();
                msg.obj = new SetFrameRateArgs(frameRate, compatibility, changeFrameRateStrategy);
                handler.sendMessage(msg);
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }
}
