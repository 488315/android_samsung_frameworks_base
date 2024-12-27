package com.samsung.android.motionphoto.core;

import android.graphics.ImageFormat;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

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
    private static final String TAG = MPSurfaceReader.class.getSimpleName();
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

    static {
        System.loadLibrary(Def.MP_NATIVE_LIB);
        nativeClassInit();
    }

    private MPSurfaceReader() {
        throw new UnsupportedOperationException();
    }

    private MPSurfaceReader(int width, int height, int format, int maxImages, long usage) {
        Log.d(
                TAG,
                String.format(
                        "MPSurfaceReader: w=%d, h=%d, fmt=0x%x, maxImages=%d, usg=0x%x",
                        Integer.valueOf(width),
                        Integer.valueOf(height),
                        Integer.valueOf(format),
                        Integer.valueOf(maxImages),
                        Long.valueOf(usage)));
        this.width = width;
        this.height = height;
        this.format = format;
        this.maxImages = maxImages;
        this.usage = usage;
        nativeInit(new WeakReference(this), width, height, format, 0, maxImages, usage);
        this.isReaderValid = new AtomicBoolean(true);
        this.surface = nativeGetSurface();
    }

    public static MPSurfaceReader of(int width, int height, int format, int maxImages, long usage) {
        return new MPSurfaceReader(width, height, format, maxImages, usage);
    }

    public void setOnImageAvailableListener(OnImageAvailableListener listener, Handler handler) {
        synchronized (this.listenerLock) {
            if (listener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException(
                            "handler is null but the current thread is not a looper");
                }
                if (this.listenerHandler == null || this.listenerHandler.getLooper() != looper) {
                    this.listenerHandler = Handler.createAsync(looper);
                    this.listenerExecutor = new HandlerExecutor(this.listenerHandler);
                }
            } else {
                this.listenerHandler = null;
                this.listenerExecutor = null;
            }
            this.listener = listener;
        }
    }

    public Surface getSurface() {
        return this.surface;
    }

    public void releaseBuffer(MPSurfaceImage image) {
        nativeReleaseBuffer(image);
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
        Log.i(TAG, "close MPSurfaceReader...E");
        setOnImageAvailableListener(null, null);
        if (this.surface != null) {
            this.surface.release();
            this.surface = null;
        }
        this.isReaderValid.set(false);
        nativeClose();
        Log.i(TAG, "close MPSurfaceReader...X");
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
        public void execute(Runnable command) {
            this.mHandler.post(command);
        }
    }

    private static void postEventFromNative(Object selfRef) {
        Executor executor;
        final OnImageAvailableListener listener;
        WeakReference<MPSurfaceReader> weakSelf = (WeakReference) selfRef;
        MPSurfaceReader sr = weakSelf.get();
        if (sr == null) {
            return;
        }
        synchronized (sr.listenerLock) {
            executor = sr.listenerExecutor;
            listener = sr.listener;
        }
        boolean isReaderValid = sr.isReaderValid.get();
        if (executor != null && listener != null && isReaderValid) {
            Objects.requireNonNull(sr);
            final MPSurfaceImage image = sr.new MPSurfaceImage();
            int status = sr.nativeImageSetup(image);
            switch (status) {
                case 0:
                case 1:
                    executor.execute(
                            new Runnable() { // from class:
                                             // com.samsung.android.motionphoto.core.MPSurfaceReader$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MPSurfaceReader.OnImageAvailableListener.this.onImageAvailable(
                                            image);
                                }
                            });
                    return;
                case 2:
                    Log.w(
                            TAG,
                            String.format(
                                    "maxImages (%d) has already been acquired, call #close before"
                                        + " acquiring more.",
                                    Integer.valueOf(sr.maxImages)));
                    return;
                default:
                    throw new AssertionError(
                            "Unknown MPSurfaceReader_nativeImageSetup return code " + status);
            }
        }
    }

    public class MPSurfaceImage implements AutoCloseable {
        private HardwareBuffer buffer;
        private int dataSpace;
        private int fd;
        private final int format;
        private long nativeBufferItemContext;
        private long timestamp;
        private final String TAG = MPSurfaceImage.class.getSimpleName();
        private final AtomicBoolean isClosed = new AtomicBoolean(false);

        public MPSurfaceImage() {
            this.format = MPSurfaceReader.this.format;
        }

        public MPSurfaceImage(HardwareBuffer buffer, long timestamp, int dataSpace, int fd) {
            this.buffer = buffer;
            this.timestamp = timestamp;
            this.dataSpace = dataSpace;
            this.fd = fd;
            this.format = MPSurfaceReader.this.format;
        }

        public HardwareBuffer getHardwareBuffer() {
            return this.buffer;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public SyncFence getFence() throws IOException {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        public void setFence(SyncFence fence) throws IOException {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        public int getDataSpace() {
            return this.dataSpace;
        }

        public void setDataSpace(int dataSpace) {
            this.dataSpace = dataSpace;
        }

        public int getFd() {
            return this.fd;
        }

        public void setFd(int fd) {
            this.fd = fd;
        }

        public String toString() {
            return "MPSurfaceImage=: buffer="
                    + bufferToString()
                    + ": timestamp="
                    + this.timestamp
                    + ": dataSpace="
                    + this.dataSpace
                    + ": format="
                    + this.format
                    + ": fd="
                    + this.fd;
        }

        public int getFormat() {
            return this.format;
        }

        public int getWidth() {
            switch (this.format) {
                case 36:
                case 256:
                case 257:
                case 4101:
                case ImageFormat.HEIC /* 1212500294 */:
                case ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    int width = MPSurfaceReader.this.width;
                    return width;
                default:
                    int width2 = this.buffer.getWidth();
                    return width2;
            }
        }

        public int getHeight() {
            switch (this.format) {
                case 36:
                case 256:
                case 257:
                case 4101:
                case ImageFormat.HEIC /* 1212500294 */:
                case ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    int height = MPSurfaceReader.this.height;
                    return height;
                default:
                    int height2 = this.buffer.getHeight();
                    return height2;
            }
        }

        MPSurfaceImage getOwner() {
            return this;
        }

        public Image.Plane[] getPlanes() {
            throw new UnsupportedOperationException("Not supported yet!");
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.isClosed.compareAndSet(false, true) && this.buffer != null) {
                MPSurfaceReader.this.releaseBuffer(this);
                this.buffer.close();
            }
        }

        protected void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        private String bufferToString() {
            if (this.buffer == null) {
                return "n/a";
            }
            String id = Long.toHexString(this.buffer.getId());
            return String.format(
                    HardwareBuffer.class.getSimpleName() + "@%d[#0x%s: w=%d, h=%d, fmt=%d]",
                    Integer.valueOf(this.buffer.hashCode()),
                    id,
                    Integer.valueOf(this.buffer.getWidth()),
                    Integer.valueOf(this.buffer.getHeight()),
                    Integer.valueOf(this.buffer.getFormat()));
        }
    }
}
