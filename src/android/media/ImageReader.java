package android.media;

import android.app.compat.CompatChanges;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import android.hardware.camera2.MultiResolutionImageReader;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Trace;
import android.view.Surface;
import com.android.internal.camera.flags.Flags;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class ImageReader implements AutoCloseable {
    private static final int ACQUIRE_MAX_IMAGES = 2;
    private static final int ACQUIRE_NO_BUFS = 1;
    private static final int ACQUIRE_SUCCESS = 0;
    private static final long DETACH_THROWS_ISE_ONLY = 236825255;
    private List<Image> mAcquiredImages;
    private final Object mCloseLock;
    private final int mDataSpace;
    private final boolean mDetachThrowsIseOnly;
    private int mEstimatedNativeAllocBytes;
    private final int mFormat;
    private final int mHardwareBufferFormat;
    private final int mHeight;
    private boolean mIsReaderValid;
    private OnImageAvailableListener mListener;
    private Executor mListenerExecutor;
    private ListenerHandler mListenerHandler;
    private final Object mListenerLock;
    private final int mMaxImages;
    private long mNativeContext;
    private final int mNumPlanes;
    private final MultiResolutionImageReader mParent;
    private Surface mSurface;
    private final long mUsage;
    private final int mWidth;

    public interface OnImageAvailableListener {
        void onImageAvailable(ImageReader imageReader);
    }

    private static native void nativeClassInit();

    private native synchronized void nativeClose();

    private static native synchronized ImagePlane[] nativeCreateImagePlanes(int i, GraphicBuffer graphicBuffer, int i2, int i3, int i4, int i5, int i6, int i7);

    private native synchronized int nativeDetachImage(Image image, boolean z);

    private native synchronized void nativeDiscardFreeBuffers();

    private native synchronized Surface nativeGetSurface();

    private native synchronized int nativeImageSetup(Image image);

    private native synchronized void nativeInit(Object obj, int i, int i2, int i3, long j, int i4, int i5);

    private native synchronized void nativeReleaseImage(Image image);

    private static native synchronized void nativeUnlockGraphicBuffer(GraphicBuffer graphicBuffer);

    public static ImageReader newInstance(int i, int i2, int i3, int i4) {
        return new ImageReader(i, i2, i3, i4, i3 == 34 ? 0L : 3L, null);
    }

    public static ImageReader newInstance(int i, int i2, int i3, int i4, long j) {
        return new ImageReader(i, i2, i3, i4, j, null);
    }

    public static ImageReader newInstance(int i, int i2, int i3, int i4, MultiResolutionImageReader multiResolutionImageReader) {
        return new ImageReader(i, i2, i3, i4, i3 == 34 ? 0L : 3L, multiResolutionImageReader);
    }

    private void initializeImageReader(int i, int i2, int i3, int i4, long j, int i5, int i6) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("The image dimensions must be positive");
        }
        if (i4 < 1) {
            throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
        }
        if (i3 == 17) {
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        nativeInit(new WeakReference(this), i, i2, i4, j, i5, i6);
        this.mIsReaderValid = true;
        this.mSurface = nativeGetSurface();
        this.mEstimatedNativeAllocBytes = ImageUtils.getEstimatedNativeAllocBytes(i, i2, i3, 1);
        VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
    }

    private ImageReader(int i, int i2, int i3, int i4, long j, MultiResolutionImageReader multiResolutionImageReader) {
        this.mDetachThrowsIseOnly = CompatChanges.isChangeEnabled(DETACH_THROWS_ISE_ONLY);
        this.mListenerLock = new Object();
        this.mCloseLock = new Object();
        this.mIsReaderValid = false;
        this.mAcquiredImages = new CopyOnWriteArrayList();
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mUsage = j;
        this.mMaxImages = i4;
        this.mParent = multiResolutionImageReader;
        int halFormat = PublicFormatUtils.getHalFormat(i3);
        this.mHardwareBufferFormat = halFormat;
        int halDataspace = PublicFormatUtils.getHalDataspace(i3);
        this.mDataSpace = halDataspace;
        this.mNumPlanes = ImageUtils.getNumPlanesForFormat(i3);
        initializeImageReader(i, i2, i3, i4, j, halFormat, halDataspace);
    }

    private ImageReader(int i, int i2, int i3, long j, MultiResolutionImageReader multiResolutionImageReader, int i4, int i5) {
        this.mDetachThrowsIseOnly = CompatChanges.isChangeEnabled(DETACH_THROWS_ISE_ONLY);
        this.mListenerLock = new Object();
        this.mCloseLock = new Object();
        this.mIsReaderValid = false;
        this.mAcquiredImages = new CopyOnWriteArrayList();
        this.mWidth = i;
        this.mHeight = i2;
        this.mUsage = j;
        this.mMaxImages = i3;
        this.mParent = multiResolutionImageReader;
        this.mHardwareBufferFormat = i4;
        this.mDataSpace = i5;
        this.mNumPlanes = ImageUtils.getNumPlanesForHardwareBufferFormat(i4);
        int publicFormat = PublicFormatUtils.getPublicFormat(i4, i5);
        this.mFormat = publicFormat;
        initializeImageReader(i, i2, publicFormat, i3, j, i4, i5);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getImageFormat() {
        return this.mFormat;
    }

    public int getHardwareBufferFormat() {
        return this.mHardwareBufferFormat;
    }

    public int getDataSpace() {
        return this.mDataSpace;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public Image acquireLatestImage() {
        Image imageAcquireNextImage = acquireNextImage();
        if (imageAcquireNextImage == null) {
            return null;
        }
        while (true) {
            try {
                Image imageAcquireNextImageNoThrowISE = acquireNextImageNoThrowISE();
                if (imageAcquireNextImageNoThrowISE == null) {
                    break;
                }
                imageAcquireNextImage.close();
                imageAcquireNextImage = imageAcquireNextImageNoThrowISE;
            } catch (Throwable th) {
                if (imageAcquireNextImage != null) {
                    imageAcquireNextImage.close();
                }
                MultiResolutionImageReader multiResolutionImageReader = this.mParent;
                if (multiResolutionImageReader != null) {
                    multiResolutionImageReader.flushOther(this);
                }
                throw th;
            }
        }
        MultiResolutionImageReader multiResolutionImageReader2 = this.mParent;
        if (multiResolutionImageReader2 != null) {
            multiResolutionImageReader2.flushOther(this);
        }
        return imageAcquireNextImage;
    }

    public Image acquireNextImageNoThrowISE() {
        SurfaceImage surfaceImage = new SurfaceImage(this.mFormat);
        if (acquireNextSurfaceImage(surfaceImage) == 0) {
            return surfaceImage;
        }
        return null;
    }

    private int acquireNextSurfaceImage(SurfaceImage surfaceImage) {
        int iNativeImageSetup;
        synchronized (this.mCloseLock) {
            iNativeImageSetup = this.mIsReaderValid ? nativeImageSetup(surfaceImage) : 1;
            if (iNativeImageSetup == 0) {
                surfaceImage.mIsImageValid = true;
            } else if (iNativeImageSetup != 1 && iNativeImageSetup != 2) {
                throw new AssertionError("Unknown nativeImageSetup return code " + iNativeImageSetup);
            }
            if (iNativeImageSetup == 0) {
                this.mAcquiredImages.add(surfaceImage);
            }
        }
        return iNativeImageSetup;
    }

    public Image acquireNextImage() {
        SurfaceImage surfaceImage = new SurfaceImage(this.mFormat);
        int iAcquireNextSurfaceImage = acquireNextSurfaceImage(surfaceImage);
        if (iAcquireNextSurfaceImage == 0) {
            return surfaceImage;
        }
        if (iAcquireNextSurfaceImage == 1) {
            return null;
        }
        if (iAcquireNextSurfaceImage == 2) {
            throw new IllegalStateException(String.format("maxImages (%d) has already been acquired, call #close before acquiring more.", Integer.valueOf(this.mMaxImages)));
        }
        throw new AssertionError("Unknown nativeImageSetup return code " + iAcquireNextSurfaceImage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseImage(Image image) {
        if (!(image instanceof SurfaceImage)) {
            throw new IllegalArgumentException("This image was not produced by an ImageReader");
        }
        SurfaceImage surfaceImage = (SurfaceImage) image;
        if (surfaceImage.mIsImageValid) {
            if (surfaceImage.getReader() != this || !this.mAcquiredImages.contains(image)) {
                throw new IllegalArgumentException("This image was not produced by this ImageReader");
            }
            surfaceImage.clearSurfacePlanes();
            nativeReleaseImage(image);
            surfaceImage.mIsImageValid = false;
            this.mAcquiredImages.remove(image);
        }
    }

    public void setOnImageAvailableListener(OnImageAvailableListener onImageAvailableListener, Handler handler) {
        synchronized (this.mListenerLock) {
            if (onImageAvailableListener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                ListenerHandler listenerHandler = this.mListenerHandler;
                if (listenerHandler == null || listenerHandler.getLooper() != looper) {
                    this.mListenerHandler = new ListenerHandler(this, looper);
                    this.mListenerExecutor = new HandlerExecutor(this, this.mListenerHandler);
                }
            } else {
                this.mListenerHandler = null;
                this.mListenerExecutor = null;
            }
            this.mListener = onImageAvailableListener;
        }
    }

    public void setOnImageAvailableListenerWithExecutor(OnImageAvailableListener onImageAvailableListener, Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        synchronized (this.mListenerLock) {
            this.mListenerExecutor = executor;
            this.mListener = onImageAvailableListener;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        setOnImageAvailableListener(null, null);
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
        }
        synchronized (this.mCloseLock) {
            this.mIsReaderValid = false;
            Iterator<Image> it = this.mAcquiredImages.iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.mAcquiredImages.clear();
            nativeClose();
            if (this.mEstimatedNativeAllocBytes > 0) {
                VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                this.mEstimatedNativeAllocBytes = 0;
            }
        }
    }

    public void discardFreeBuffers() {
        synchronized (this.mCloseLock) {
            nativeDiscardFreeBuffers();
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public void detachImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("input image must not be null");
        }
        if (!isImageOwnedbyMe(image)) {
            throw new IllegalArgumentException("Trying to detach an image that is not owned by this ImageReader");
        }
        SurfaceImage surfaceImage = (SurfaceImage) image;
        surfaceImage.throwISEIfImageIsInvalid();
        if (surfaceImage.isAttachable()) {
            throw new IllegalStateException("Image was already detached from this ImageReader");
        }
        nativeDetachImage(image, this.mDetachThrowsIseOnly);
        surfaceImage.clearSurfacePlanes();
        surfaceImage.mPlanes = null;
        surfaceImage.setDetached(true);
    }

    private boolean isImageOwnedbyMe(Image image) {
        return (image instanceof SurfaceImage) && ((SurfaceImage) image).getReader() == this;
    }

    private static void postEventFromNative(Object obj) {
        Executor executor;
        final OnImageAvailableListener onImageAvailableListener;
        boolean z;
        final ImageReader imageReader = (ImageReader) ((WeakReference) obj).get();
        if (imageReader == null) {
            return;
        }
        Trace.beginSection("android.media.ImageReader#postEventFromNative");
        synchronized (imageReader.mListenerLock) {
            executor = imageReader.mListenerExecutor;
            onImageAvailableListener = imageReader.mListener;
        }
        synchronized (imageReader.mCloseLock) {
            z = imageReader.mIsReaderValid;
        }
        if (executor != null && onImageAvailableListener != null && z) {
            executor.execute(new Runnable() { // from class: android.media.ImageReader.1
                @Override // java.lang.Runnable
                public void run() {
                    onImageAvailableListener.onImageAvailable(imageReader);
                }
            });
        }
        Trace.endSection();
    }

    public static final class Builder {
        private int mHeight;
        private int mWidth;
        private int mMaxImages = 1;
        private int mImageFormat = 0;
        private int mHardwareBufferFormat = 1;
        private int mDataSpace = 0;
        private long mUsage = 3;
        private boolean mUseLegacyImageFormat = false;

        public Builder(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }

        public Builder setMaxImages(int i) {
            this.mMaxImages = i;
            return this;
        }

        public Builder setUsage(long j) {
            this.mUsage = j;
            return this;
        }

        public Builder setImageFormat(int i) {
            this.mImageFormat = i;
            this.mUseLegacyImageFormat = true;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            return this;
        }

        public Builder setDefaultHardwareBufferFormat(int i) {
            this.mHardwareBufferFormat = i;
            this.mUseLegacyImageFormat = false;
            this.mImageFormat = 0;
            return this;
        }

        public Builder setDefaultDataSpace(int i) {
            this.mDataSpace = i;
            this.mUseLegacyImageFormat = false;
            this.mImageFormat = 0;
            return this;
        }

        public ImageReader build() {
            if (this.mUseLegacyImageFormat) {
                return new ImageReader(this.mWidth, this.mHeight, this.mImageFormat, this.mMaxImages, this.mUsage, (MultiResolutionImageReader) null);
            }
            return new ImageReader(this.mWidth, this.mHeight, this.mMaxImages, this.mUsage, null, this.mHardwareBufferFormat, this.mDataSpace);
        }
    }

    private final class ListenerHandler extends Handler {
        public ListenerHandler(ImageReader imageReader, Looper looper) {
            super(looper, null, true);
        }
    }

    private final class HandlerExecutor implements Executor {
        private final Handler mHandler;

        public HandlerExecutor(ImageReader imageReader, Handler handler) {
            this.mHandler = (Handler) Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    private class SurfaceImage extends Image {
        private int mDataSpace;
        private int mFormat;
        private int mHardwareBufferFormat;
        private long mNativeBuffer;
        private SurfacePlane[] mPlanes;
        private int mScalingMode;
        private long mTimestamp;
        private int mTransform;
        private final Object mCloseLock = new Object();
        private AtomicBoolean mIsDetached = new AtomicBoolean(false);

        private native synchronized SurfacePlane[] nativeCreatePlanes(int i, int i2, long j);

        private native synchronized int nativeGetFenceFd();

        private native synchronized int nativeGetFormat(int i);

        private native synchronized HardwareBuffer nativeGetHardwareBuffer();

        private native synchronized int nativeGetHeight();

        private native synchronized int nativeGetWidth();

        public SurfaceImage(int i) {
            this.mFormat = 0;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            this.mFormat = i;
            this.mHardwareBufferFormat = ImageReader.this.mHardwareBufferFormat;
            this.mDataSpace = ImageReader.this.mDataSpace;
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mCloseLock) {
                ImageReader.this.releaseImage(this);
            }
        }

        public ImageReader getReader() {
            return ImageReader.this;
        }

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            int imageFormat = ImageReader.this.getImageFormat();
            if (imageFormat != 34) {
                imageFormat = nativeGetFormat(imageFormat);
            }
            this.mFormat = imageFormat;
            return imageFormat;
        }

        @Override // android.media.Image
        public int getWidth() {
            int width;
            throwISEIfImageIsInvalid();
            int format = getFormat();
            if (format == 36 || format == 4101 || format == 1212500294 || format == 1768253795 || format == 256 || format == 257) {
                width = ImageReader.this.getWidth();
            } else {
                width = nativeGetWidth();
            }
            return (Flags.cameraHeifGainmap() && getFormat() == 4102) ? ImageReader.this.getWidth() : width;
        }

        @Override // android.media.Image
        public int getHeight() {
            int height;
            throwISEIfImageIsInvalid();
            int format = getFormat();
            if (format == 36 || format == 4101 || format == 1212500294 || format == 1768253795 || format == 256 || format == 257) {
                height = ImageReader.this.getHeight();
            } else {
                height = nativeGetHeight();
            }
            return (Flags.cameraHeifGainmap() && getFormat() == 4102) ? ImageReader.this.getHeight() : height;
        }

        @Override // android.media.Image
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public int getTransform() {
            throwISEIfImageIsInvalid();
            return this.mTransform;
        }

        @Override // android.media.Image
        public int getScalingMode() {
            throwISEIfImageIsInvalid();
            return this.mScalingMode;
        }

        @Override // android.media.Image
        public int getPlaneCount() {
            throwISEIfImageIsInvalid();
            return ImageReader.this.mNumPlanes;
        }

        @Override // android.media.Image
        public SyncFence getFence() throws IOException {
            throwISEIfImageIsInvalid();
            if (nativeGetFenceFd() != -1) {
                return SyncFence.create(ParcelFileDescriptor.fromFd(nativeGetFenceFd()));
            }
            return SyncFence.createEmpty();
        }

        @Override // android.media.Image
        public HardwareBuffer getHardwareBuffer() {
            throwISEIfImageIsInvalid();
            return nativeGetHardwareBuffer();
        }

        @Override // android.media.Image
        public int getDataSpace() {
            throwISEIfImageIsInvalid();
            return this.mDataSpace;
        }

        @Override // android.media.Image
        public void setTimestamp(long j) {
            throwISEIfImageIsInvalid();
            this.mTimestamp = j;
        }

        @Override // android.media.Image
        public Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = nativeCreatePlanes(ImageReader.this.mNumPlanes, ImageReader.this.mHardwareBufferFormat, ImageReader.this.mUsage);
            }
            return (Image.Plane[]) this.mPlanes.clone();
        }

        protected final void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        @Override // android.media.Image
        public boolean isAttachable() {
            throwISEIfImageIsInvalid();
            return this.mIsDetached.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.Image
        public ImageReader getOwner() {
            throwISEIfImageIsInvalid();
            return ImageReader.this;
        }

        @Override // android.media.Image
        long getNativeContext() {
            throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDetached(boolean z) {
            throwISEIfImageIsInvalid();
            this.mIsDetached.getAndSet(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSurfacePlanes() {
            if (!this.mIsImageValid || this.mPlanes == null) {
                return;
            }
            int i = 0;
            while (true) {
                SurfacePlane[] surfacePlaneArr = this.mPlanes;
                if (i >= surfacePlaneArr.length) {
                    return;
                }
                SurfacePlane surfacePlane = surfacePlaneArr[i];
                if (surfacePlane != null) {
                    surfacePlane.clearBuffer();
                    this.mPlanes[i] = null;
                }
                i++;
            }
        }

        private class SurfacePlane extends Image.Plane {
            private ByteBuffer mBuffer;
            private final int mPixelStride;
            private final int mRowStride;

            private SurfacePlane(int i, int i2, ByteBuffer byteBuffer) {
                this.mRowStride = i;
                this.mPixelStride = i2;
                this.mBuffer = byteBuffer;
                byteBuffer.order(ByteOrder.nativeOrder());
            }

            @Override // android.media.Image.Plane
            public ByteBuffer getBuffer() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat == 36) {
                    throw new UnsupportedOperationException("getPixelStride is not supported for RAW_PRIVATE plane");
                }
                return this.mPixelStride;
            }

            @Override // android.media.Image.Plane
            public int getRowStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat == 36) {
                    throw new UnsupportedOperationException("getRowStride is not supported for RAW_PRIVATE plane");
                }
                return this.mRowStride;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void clearBuffer() {
                ByteBuffer byteBuffer = this.mBuffer;
                if (byteBuffer == null) {
                    return;
                }
                if (byteBuffer.isDirect()) {
                    NioUtils.freeDirectBuffer(this.mBuffer);
                }
                this.mBuffer = null;
            }
        }
    }

    public static class ImagePlane extends Image.Plane {
        private ByteBuffer mBuffer;
        private final int mPixelStride;
        private final int mRowStride;

        private ImagePlane(int i, int i2, ByteBuffer byteBuffer) {
            this.mRowStride = i;
            this.mPixelStride = i2;
            this.mBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.nativeOrder());
        }

        @Override // android.media.Image.Plane
        public ByteBuffer getBuffer() {
            return this.mBuffer;
        }

        @Override // android.media.Image.Plane
        public int getPixelStride() {
            return this.mPixelStride;
        }

        @Override // android.media.Image.Plane
        public int getRowStride() {
            return this.mRowStride;
        }
    }

    public static ImagePlane[] initializeImagePlanes(int i, GraphicBuffer graphicBuffer, int i2, int i3, long j, int i4, int i5, Rect rect) {
        return nativeCreateImagePlanes(i, graphicBuffer, i2, i3, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static void unlockGraphicBuffer(GraphicBuffer graphicBuffer) {
        nativeUnlockGraphicBuffer(graphicBuffer);
    }

    static {
        System.loadLibrary("media_jni");
        nativeClassInit();
    }
}
