package com.samsung.android.motionphoto.core;

import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import com.samsung.android.motionphoto.core.MPSurfaceReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes6.dex */
public class MPSurfaceReader implements AutoCloseable {
    private static final int ACQUIRE_MAX_IMAGES = 2;
    private static final int ACQUIRE_NO_BUFS = 1;
    private static final int ACQUIRE_SUCCESS = 0;
    private static final String TAG = "MPSurfaceReader";
    private final int format;
    private final int height;
    private final AtomicBoolean isReaderValid;
    private OnImageAvailableListener listener;
    private Executor listenerExecutor;
    private Handler listenerHandler;
    private long mNativeContext;
    private final int maxImages;
    public Surface surface;
    private final long usage;
    private final int width;
    private final int dataSpace = 0;
    private final Object listenerLock = new Object();

    public interface OnImageAvailableListener {
        void onImageAvailable(MPSurfaceImage mPSurfaceImage);
    }

    private static native void nativeClassInit();

    private native void nativeClose();

    private native Surface nativeGetSurface();

    private native int nativeImageSetup(MPSurfaceImage mPSurfaceImage);

    private native void nativeInit(Object obj, int i, int i2, int i3, int i4, int i5, long j);

    private native void nativeReleaseBuffer(MPSurfaceImage mPSurfaceImage);

    private MPSurfaceReader() {
        throw new UnsupportedOperationException();
    }

    private MPSurfaceReader(int i, int i2, int i3, int i4, long j) {
        Log.d(TAG, String.format("MPSurfaceReader: w=%d, h=%d, fmt=0x%x, maxImages=%d, usg=0x%x", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Long.valueOf(j)));
        this.width = i;
        this.height = i2;
        this.format = i3;
        this.maxImages = i4;
        this.usage = j;
        nativeInit(new WeakReference(this), i, i2, i3, 0, i4, j);
        this.isReaderValid = new AtomicBoolean(true);
        this.surface = nativeGetSurface();
    }

    public static MPSurfaceReader of(int i, int i2, int i3, int i4, long j) {
        return new MPSurfaceReader(i, i2, i3, i4, j);
    }

    public void setOnImageAvailableListener(OnImageAvailableListener onImageAvailableListener, Handler handler) {
        synchronized (this.listenerLock) {
            if (onImageAvailableListener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                Handler handler2 = this.listenerHandler;
                if (handler2 == null || handler2.getLooper() != looper) {
                    this.listenerHandler = Handler.createAsync(looper);
                    this.listenerExecutor = new HandlerExecutor(this.listenerHandler);
                }
            } else {
                this.listenerHandler = null;
                this.listenerExecutor = null;
            }
            this.listener = onImageAvailableListener;
        }
    }

    public Surface getSurface() {
        return this.surface;
    }

    public void releaseBuffer(MPSurfaceImage mPSurfaceImage) {
        nativeReleaseBuffer(mPSurfaceImage);
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        String str = TAG;
        Log.i(str, "close MPSurfaceReader...E");
        setOnImageAvailableListener(null, null);
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
            this.surface = null;
        }
        this.isReaderValid.set(false);
        nativeClose();
        Log.i(str, "close MPSurfaceReader...X");
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private static final class HandlerExecutor implements Executor {
        private final Handler mHandler;

        public HandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    private static void postEventFromNative(Object obj) {
        Executor executor;
        final OnImageAvailableListener onImageAvailableListener;
        MPSurfaceReader mPSurfaceReader = (MPSurfaceReader) ((WeakReference) obj).get();
        if (mPSurfaceReader == null) {
            return;
        }
        synchronized (mPSurfaceReader.listenerLock) {
            executor = mPSurfaceReader.listenerExecutor;
            onImageAvailableListener = mPSurfaceReader.listener;
        }
        boolean z = mPSurfaceReader.isReaderValid.get();
        if (executor == null || onImageAvailableListener == null || !z) {
            return;
        }
        Objects.requireNonNull(mPSurfaceReader);
        final MPSurfaceImage mPSurfaceImage = mPSurfaceReader.new MPSurfaceImage();
        int nativeImageSetup = mPSurfaceReader.nativeImageSetup(mPSurfaceImage);
        if (nativeImageSetup == 0 || nativeImageSetup == 1) {
            executor.execute(new Runnable() { // from class: com.samsung.android.motionphoto.core.MPSurfaceReader$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MPSurfaceReader.OnImageAvailableListener.this.onImageAvailable(mPSurfaceImage);
                }
            });
        } else if (nativeImageSetup == 2) {
            Log.w(TAG, String.format("maxImages (%d) has already been acquired, call #close before acquiring more.", Integer.valueOf(mPSurfaceReader.maxImages)));
        } else {
            throw new AssertionError("Unknown MPSurfaceReader_nativeImageSetup return code " + nativeImageSetup);
        }
    }

    public class MPSurfaceImage implements AutoCloseable {
        private HardwareBuffer buffer;
        private int dataSpace;
        private int fd;
        private final int format;
        private long nativeBufferItemContext;
        private int stride;
        private long timestamp;
        private final String TAG = MPSurfaceImage.class.getSimpleName();
        private final AtomicBoolean isClosed = new AtomicBoolean(false);

        MPSurfaceImage getOwner() {
            return this;
        }

        public MPSurfaceImage() {
            this.format = MPSurfaceReader.this.format;
        }

        public MPSurfaceImage(HardwareBuffer hardwareBuffer, int i, long j, int i2, int i3) {
            this.buffer = hardwareBuffer;
            this.stride = i;
            this.timestamp = j;
            this.dataSpace = i2;
            this.fd = i3;
            this.format = MPSurfaceReader.this.format;
        }

        public HardwareBuffer getHardwareBuffer() {
            return this.buffer;
        }

        public void setTimestamp(long j) {
            this.timestamp = j;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public SyncFence getFence() throws IOException {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        public void setFence(SyncFence syncFence) throws IOException {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        public int getDataSpace() {
            return this.dataSpace;
        }

        public void setDataSpace(int i) {
            this.dataSpace = i;
        }

        public int getFd() {
            return this.fd;
        }

        public void setFd(int i) {
            this.fd = i;
        }

        public String toString() {
            return "MPSurfaceImage=: context=" + this.nativeBufferItemContext + ": buffer=" + bufferToString() + ": timestamp=" + this.timestamp + ": dataSpace=" + this.dataSpace + ": format=" + this.format + ": fd=" + this.fd;
        }

        public int getFormat() {
            return this.format;
        }

        public int getWidth() {
            int i = this.format;
            if (i == 34 || i == 36 || i == 4101 || i == 1212500294 || i == 1768253795 || i == 256 || i == 257) {
                return MPSurfaceReader.this.width;
            }
            return this.buffer.getWidth();
        }

        public int getHeight() {
            int i = this.format;
            if (i == 34 || i == 36 || i == 4101 || i == 1212500294 || i == 1768253795 || i == 256 || i == 257) {
                return MPSurfaceReader.this.height;
            }
            return this.buffer.getHeight();
        }

        public int getStride() {
            return this.stride;
        }

        public int getScanline() {
            return this.buffer.getHeight();
        }

        public Image.Plane[] getPlanes() {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (!this.isClosed.compareAndSet(false, true) || this.buffer == null) {
                return;
            }
            MPSurfaceReader.this.releaseBuffer(this);
            this.buffer.close();
        }

        protected void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        private String bufferToString() {
            HardwareBuffer hardwareBuffer = this.buffer;
            if (hardwareBuffer == null) {
                return "n/a";
            }
            return String.format("HardwareBuffer@%d[#0x%s: [w=%d, h=%d], stride=%d, scanline=%d fmt=%d]", Integer.valueOf(this.buffer.hashCode()), Long.toHexString(hardwareBuffer.getId()), Integer.valueOf(this.buffer.getWidth()), Integer.valueOf(this.buffer.getHeight()), Integer.valueOf(getStride()), Integer.valueOf(getScanline()), Integer.valueOf(this.buffer.getFormat()));
        }
    }

    static {
        System.loadLibrary(Def.MP_NATIVE_LIB);
        nativeClassInit();
    }
}
