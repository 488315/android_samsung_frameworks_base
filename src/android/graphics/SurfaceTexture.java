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

        public OutOfResourcesException(String str) {
            super(str);
        }
    }

    public SurfaceTexture(int i) {
        this(i, false);
    }

    public SurfaceTexture(int i, boolean z) {
        this.mCreatorLooper = Looper.myLooper();
        this.mIsSingleBuffered = z;
        nativeInit(false, i, z, new WeakReference<>(this));
    }

    public SurfaceTexture(boolean z) {
        this.mCreatorLooper = Looper.myLooper();
        this.mIsSingleBuffered = z;
        nativeInit(true, 0, z, new WeakReference<>(this));
    }

    public void setOnFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
        setOnFrameAvailableListener(onFrameAvailableListener, null);
    }

    public void setOnFrameAvailableListener(final OnFrameAvailableListener onFrameAvailableListener, Handler handler) {
        Looper looper;
        if (onFrameAvailableListener != null) {
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = this.mCreatorLooper;
                if (looper == null) {
                    looper = Looper.getMainLooper();
                }
            }
            this.mOnFrameAvailableHandler = new Handler(looper, null, true) { // from class: android.graphics.SurfaceTexture.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    onFrameAvailableListener.onFrameAvailable(SurfaceTexture.this);
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

        SetFrameRateArgs(float f, int i, int i2) {
            this.mFrameRate = f;
            this.mCompatibility = i;
            this.mChangeFrameRateStrategy = i2;
        }
    }

    public void setOnSetFrameRateListener(final OnSetFrameRateListener onSetFrameRateListener, Handler handler) {
        Looper looper;
        if (onSetFrameRateListener != null) {
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = this.mCreatorLooper;
                if (looper == null) {
                    looper = Looper.getMainLooper();
                }
            }
            this.mOnSetFrameRateHandler = new Handler(looper, null, true) { // from class: android.graphics.SurfaceTexture.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Trace.traceBegin(8L, "onSetFrameRateHandler");
                    try {
                        SetFrameRateArgs setFrameRateArgs = (SetFrameRateArgs) message.obj;
                        onSetFrameRateListener.onSetFrameRate(SurfaceTexture.this, setFrameRateArgs.mFrameRate, setFrameRateArgs.mCompatibility, setFrameRateArgs.mChangeFrameRateStrategy);
                    } finally {
                        Trace.traceEnd(8L);
                    }
                }
            };
            return;
        }
        this.mOnSetFrameRateHandler = null;
    }

    public void setDefaultBufferSize(int i, int i2) {
        nativeSetDefaultBufferSize(i, i2);
    }

    public void updateTexImage() {
        nativeUpdateTexImage();
    }

    public void releaseTexImage() {
        nativeReleaseTexImage();
    }

    public void detachFromGLContext() {
        if (nativeDetachFromGLContext() != 0) {
            throw new RuntimeException("Error during detachFromGLContext (see logcat for details)");
        }
    }

    public void attachToGLContext(int i) {
        if (nativeAttachToGLContext(i) != 0) {
            throw new RuntimeException("Error during attachToGLContext (see logcat for details)");
        }
    }

    public void getTransformMatrix(float[] fArr) {
        if (fArr.length != 16) {
            throw new IllegalArgumentException();
        }
        nativeGetTransformMatrix(fArr);
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

    private static void postEventFromNative(WeakReference<SurfaceTexture> weakReference) {
        Handler handler;
        SurfaceTexture surfaceTexture = weakReference.get();
        if (surfaceTexture == null || (handler = surfaceTexture.mOnFrameAvailableHandler) == null) {
            return;
        }
        handler.sendEmptyMessage(0);
    }

    private static void postOnSetFrameRateEventFromNative(WeakReference<SurfaceTexture> weakReference, float f, int i, int i2) {
        SurfaceTexture surfaceTexture;
        Handler handler;
        Trace.traceBegin(8L, "postOnSetFrameRateEventFromNative");
        try {
            if (Flags.toolkitSetFrameRateReadOnly() && (surfaceTexture = weakReference.get()) != null && (handler = surfaceTexture.mOnSetFrameRateHandler) != null) {
                Message message = new Message();
                message.obj = new SetFrameRateArgs(f, i, i2);
                handler.sendMessage(message);
            }
        } finally {
            Trace.traceEnd(8L);
        }
    }

    public boolean isSingleBuffered() {
        return this.mIsSingleBuffered;
    }
}
