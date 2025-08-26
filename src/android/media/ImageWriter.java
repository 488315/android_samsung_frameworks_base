package android.media;

import android.graphics.GraphicBuffer;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.SyncFence;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Size;
import android.view.Surface;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class ImageWriter implements AutoCloseable {
    private final Object mCloseLock;
    private int mDataSpace;
    private List<Image> mDequeuedImages;
    private int mEstimatedNativeAllocBytes;
    private int mHardwareBufferFormat;
    private int mHeight;
    private boolean mIsWriterValid;
    private OnImageReleasedListener mListener;
    private ListenerHandler mListenerHandler;
    private final Object mListenerLock;
    private final int mMaxImages;
    private long mNativeContext;
    private int mSemTransform;
    private long mUsage;
    private int mWidth;
    private int mWriterFormat;

    public interface OnImageReleasedListener {
        void onImageReleased(ImageWriter imageWriter);
    }

    private native synchronized void cancelImage(long j, Image image);

    private native synchronized int nativeAttachAndQueueGraphicBuffer(long j, GraphicBuffer graphicBuffer, int i, long j2, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private native synchronized int nativeAttachAndQueueImage(long j, long j2, int i, long j3, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native void nativeClassInit();

    private native synchronized void nativeClose(long j);

    private native synchronized void nativeDequeueInputImage(long j, Image image);

    private native synchronized long nativeInit(Object obj, Surface surface, int i, int i2, int i3, boolean z, int i4, int i5, long j);

    private native synchronized void nativeQueueInputImage(long j, Image image, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public static ImageWriter newInstance(Surface surface, int i) {
        return new ImageWriter(surface, i, true, 0, -1, -1);
    }

    public static ImageWriter newInstance(Surface surface, int i, int i2, int i3, int i4) {
        if (!ImageFormat.isPublicFormat(i2) && !PixelFormat.isPublicFormat(i2)) {
            throw new IllegalArgumentException("Invalid format is specified: " + i2);
        }
        return new ImageWriter(surface, i, false, i2, i3, i4);
    }

    public static ImageWriter newInstance(Surface surface, int i, int i2) {
        if (!ImageFormat.isPublicFormat(i2) && !PixelFormat.isPublicFormat(i2)) {
            throw new IllegalArgumentException("Invalid format is specified: " + i2);
        }
        return new ImageWriter(surface, i, false, i2, -1, -1);
    }

    private void initializeImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4, int i5, int i6, long j) {
        int publicFormat;
        if (surface == null || i < 1) {
            throw new IllegalArgumentException("Illegal input argument: surface " + surface + ", maxImages: " + i);
        }
        this.mNativeContext = nativeInit(new WeakReference(this), surface, i, i5, i6, z, i3, i4, j);
        if (z) {
            int surfaceFormat = SurfaceUtils.getSurfaceFormat(surface);
            this.mHardwareBufferFormat = surfaceFormat;
            int surfaceDataspace = SurfaceUtils.getSurfaceDataspace(surface);
            this.mDataSpace = surfaceDataspace;
            publicFormat = PublicFormatUtils.getPublicFormat(surfaceFormat, surfaceDataspace);
        } else {
            publicFormat = i2;
        }
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        int width = i5;
        if (width == -1) {
            width = surfaceSize.getWidth();
        }
        this.mWidth = width;
        int height = i6;
        if (height == -1) {
            height = surfaceSize.getHeight();
        }
        this.mHeight = height;
        this.mEstimatedNativeAllocBytes = ImageUtils.getEstimatedNativeAllocBytes(this.mWidth, height, publicFormat, 1);
        VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
        this.mIsWriterValid = true;
    }

    private ImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4) {
        this.mListenerLock = new Object();
        this.mCloseLock = new Object();
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mSemTransform = 0;
        this.mDequeuedImages = new CopyOnWriteArrayList();
        this.mMaxImages = i;
        if (!z) {
            this.mHardwareBufferFormat = PublicFormatUtils.getHalFormat(i2);
            this.mDataSpace = PublicFormatUtils.getHalDataspace(i2);
        }
        initializeImageWriter(surface, i, z, i2, this.mHardwareBufferFormat, this.mDataSpace, i3, i4, this.mUsage);
    }

    private ImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4, long j) {
        this.mListenerLock = new Object();
        this.mCloseLock = new Object();
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mSemTransform = 0;
        this.mDequeuedImages = new CopyOnWriteArrayList();
        this.mMaxImages = i;
        this.mUsage = j;
        if (!z) {
            this.mHardwareBufferFormat = PublicFormatUtils.getHalFormat(i2);
            this.mDataSpace = PublicFormatUtils.getHalDataspace(i2);
        }
        initializeImageWriter(surface, i, z, i2, this.mHardwareBufferFormat, this.mDataSpace, i3, i4, j);
    }

    private ImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4, int i5, long j) {
        int i6;
        this.mListenerLock = new Object();
        this.mCloseLock = new Object();
        int publicFormat = 0;
        this.mIsWriterValid = false;
        this.mUsage = 48L;
        this.mSemTransform = 0;
        this.mDequeuedImages = new CopyOnWriteArrayList();
        this.mMaxImages = i;
        this.mUsage = j;
        if (z) {
            i6 = i3;
        } else {
            publicFormat = PublicFormatUtils.getPublicFormat(i2, i3);
            this.mHardwareBufferFormat = i2;
            i6 = i3;
            this.mDataSpace = i6;
        }
        initializeImageWriter(surface, i, z, publicFormat, i2, i6, i4, i5, j);
    }

    private ImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4, long j, int i5) {
        this(surface, i, z, i2, i3, i4, j);
        this.mSemTransform = i5;
    }

    private ImageWriter(Surface surface, int i, boolean z, int i2, int i3, int i4, int i5, long j, int i6) {
        this(surface, i, z, i2, i3, i4, i5, j);
        this.mSemTransform = i6;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Image dequeueInputImage() {
        if (this.mDequeuedImages.size() >= this.mMaxImages) {
            throw new IllegalStateException("Already dequeued max number of Images " + this.mMaxImages);
        }
        WriterSurfaceImage writerSurfaceImage = new WriterSurfaceImage(this);
        nativeDequeueInputImage(this.mNativeContext, writerSurfaceImage);
        this.mDequeuedImages.add(writerSurfaceImage);
        writerSurfaceImage.mIsImageValid = true;
        return writerSurfaceImage;
    }

    public void queueInputImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("image shouldn't be null");
        }
        boolean zIsImageOwnedByMe = isImageOwnedByMe(image);
        if (zIsImageOwnedByMe && !((WriterSurfaceImage) image).mIsImageValid) {
            throw new IllegalStateException("Image from ImageWriter is invalid");
        }
        if (!zIsImageOwnedByMe) {
            if (image.getOwner() instanceof ImageReader) {
                ((ImageReader) image.getOwner()).detachImage(image);
            } else if (image.getOwner() != null) {
                throw new IllegalArgumentException("Only images from ImageReader can be queued to ImageWriter, other image source is not supported yet!");
            }
            attachAndQueueInputImage(image);
            image.close();
            return;
        }
        Rect cropRect = image.getCropRect();
        nativeQueueInputImage(this.mNativeContext, image, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
        if (zIsImageOwnedByMe) {
            this.mDequeuedImages.remove(image);
            WriterSurfaceImage writerSurfaceImage = (WriterSurfaceImage) image;
            writerSurfaceImage.clearSurfacePlanes();
            writerSurfaceImage.mIsImageValid = false;
        }
    }

    public int getFormat() {
        return this.mWriterFormat;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public int getHardwareBufferFormat() {
        return this.mHardwareBufferFormat;
    }

    public int getDataSpace() {
        return this.mDataSpace;
    }

    public void setOnImageReleasedListener(OnImageReleasedListener onImageReleasedListener, Handler handler) {
        synchronized (this.mListenerLock) {
            if (onImageReleasedListener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                ListenerHandler listenerHandler = this.mListenerHandler;
                if (listenerHandler == null || listenerHandler.getLooper() != looper) {
                    this.mListenerHandler = new ListenerHandler(looper);
                }
                this.mListener = onImageReleasedListener;
            } else {
                this.mListener = null;
                this.mListenerHandler = null;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        setOnImageReleasedListener(null, null);
        synchronized (this.mCloseLock) {
            if (this.mIsWriterValid) {
                Iterator<Image> it = this.mDequeuedImages.iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.mDequeuedImages.clear();
                nativeClose(this.mNativeContext);
                this.mNativeContext = 0L;
                if (this.mEstimatedNativeAllocBytes > 0) {
                    VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                    this.mEstimatedNativeAllocBytes = 0;
                }
                this.mIsWriterValid = false;
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void attachAndQueueInputImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("image shouldn't be null");
        }
        if (isImageOwnedByMe(image)) {
            throw new IllegalArgumentException("Can not attach an image that is owned ImageWriter already");
        }
        if (!image.isAttachable()) {
            throw new IllegalStateException("Image was not detached from last owner, or image  is not detachable");
        }
        Rect cropRect = image.getCropRect();
        int halFormat = PublicFormatUtils.getHalFormat(image.getFormat());
        if (image.getNativeContext() != 0) {
            nativeAttachAndQueueImage(this.mNativeContext, image.getNativeContext(), halFormat, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
            return;
        }
        GraphicBuffer graphicBufferCreateFromHardwareBuffer = GraphicBuffer.createFromHardwareBuffer(image.getHardwareBuffer());
        nativeAttachAndQueueGraphicBuffer(this.mNativeContext, graphicBufferCreateFromHardwareBuffer, halFormat, image.getTimestamp(), image.getDataSpace(), cropRect.left, cropRect.top, cropRect.right, cropRect.bottom, image.getTransform(), image.getScalingMode());
        graphicBufferCreateFromHardwareBuffer.destroy();
        image.close();
    }

    private final class ListenerHandler extends Handler {
        public ListenerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            OnImageReleasedListener onImageReleasedListener;
            boolean z;
            synchronized (ImageWriter.this.mListenerLock) {
                onImageReleasedListener = ImageWriter.this.mListener;
            }
            synchronized (ImageWriter.this.mCloseLock) {
                z = ImageWriter.this.mIsWriterValid;
            }
            if (onImageReleasedListener == null || !z) {
                return;
            }
            onImageReleasedListener.onImageReleased(ImageWriter.this);
        }
    }

    private static void postEventFromNative(Object obj) {
        ListenerHandler listenerHandler;
        ImageWriter imageWriter = (ImageWriter) ((WeakReference) obj).get();
        if (imageWriter == null) {
            return;
        }
        synchronized (imageWriter.mListenerLock) {
            listenerHandler = imageWriter.mListenerHandler;
        }
        if (listenerHandler != null) {
            listenerHandler.sendEmptyMessage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("image shouldn't be null");
        }
        if (!this.mDequeuedImages.contains(image)) {
            throw new IllegalStateException("It is illegal to abort some image that is not dequeued yet");
        }
        WriterSurfaceImage writerSurfaceImage = (WriterSurfaceImage) image;
        if (writerSurfaceImage.mIsImageValid) {
            cancelImage(this.mNativeContext, image);
            this.mDequeuedImages.remove(image);
            writerSurfaceImage.clearSurfacePlanes();
            writerSurfaceImage.mIsImageValid = false;
        }
    }

    private boolean isImageOwnedByMe(Image image) {
        return (image instanceof WriterSurfaceImage) && ((WriterSurfaceImage) image).getOwner() == this;
    }

    public static final class Builder {
        private Surface mSurface;
        private int mWidth = -1;
        private int mHeight = -1;
        private int mMaxImages = 1;
        private int mImageFormat = 0;
        private long mUsage = -1;
        private int mHardwareBufferFormat = 1;
        private int mDataSpace = 0;
        private boolean mUseSurfaceImageFormatInfo = true;
        private boolean mUseLegacyImageFormat = false;
        private int mSemTransform = 0;

        public Builder(Surface surface) {
            this.mSurface = surface;
        }

        public Builder setWidthAndHeight(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
            return this;
        }

        public Builder setMaxImages(int i) {
            this.mMaxImages = i;
            return this;
        }

        public Builder setImageFormat(int i) {
            if (!ImageFormat.isPublicFormat(i) && !PixelFormat.isPublicFormat(i)) {
                throw new IllegalArgumentException("Invalid imageFormat is specified: " + i);
            }
            this.mImageFormat = i;
            this.mUseLegacyImageFormat = true;
            this.mHardwareBufferFormat = 1;
            this.mDataSpace = 0;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public Builder setHardwareBufferFormat(int i) {
            this.mHardwareBufferFormat = i;
            this.mImageFormat = 0;
            this.mUseLegacyImageFormat = false;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public Builder setDataSpace(int i) {
            this.mDataSpace = i;
            this.mImageFormat = 0;
            this.mUseLegacyImageFormat = false;
            this.mUseSurfaceImageFormatInfo = false;
            return this;
        }

        public Builder semSetTransform(int i) {
            if (i == 0) {
                this.mSemTransform = 0;
                return this;
            }
            if (i == 1) {
                this.mSemTransform = 4;
                return this;
            }
            if (i == 2) {
                this.mSemTransform = 3;
                return this;
            }
            if (i == 3) {
                this.mSemTransform = 7;
                return this;
            }
            this.mSemTransform = 0;
            return this;
        }

        public Builder setUsage(long j) {
            this.mUsage = j;
            return this;
        }

        public ImageWriter build() {
            if (this.mUseLegacyImageFormat) {
                return new ImageWriter(this.mSurface, this.mMaxImages, this.mUseSurfaceImageFormatInfo, this.mImageFormat, this.mWidth, this.mHeight, this.mUsage);
            }
            return new ImageWriter(this.mSurface, this.mMaxImages, this.mUseSurfaceImageFormatInfo, this.mHardwareBufferFormat, this.mDataSpace, this.mWidth, this.mHeight, this.mUsage, this.mSemTransform);
        }
    }

    private static class WriterSurfaceImage extends Image {
        private int mDataSpace;
        private int mHeight;
        private long mNativeBuffer;
        private ImageWriter mOwner;
        private SurfacePlane[] mPlanes;
        private int mTransform;
        private int mWidth;
        private int mNativeFenceFd = -1;
        private int mFormat = -1;
        private final long DEFAULT_TIMESTAMP = Long.MIN_VALUE;
        private long mTimestamp = Long.MIN_VALUE;
        private int mScalingMode = 0;
        private final Object mCloseLock = new Object();

        private native synchronized SurfacePlane[] nativeCreatePlanes(int i, int i2);

        private native synchronized int nativeGetFormat(int i);

        private native synchronized HardwareBuffer nativeGetHardwareBuffer();

        private native synchronized int nativeGetHeight();

        private native synchronized int nativeGetWidth();

        private native synchronized void nativeSetFenceFd(int i);

        public WriterSurfaceImage(ImageWriter imageWriter) {
            this.mHeight = -1;
            this.mWidth = -1;
            this.mDataSpace = 0;
            this.mTransform = 0;
            this.mOwner = imageWriter;
            this.mWidth = imageWriter.mWidth;
            this.mHeight = imageWriter.mHeight;
            this.mDataSpace = imageWriter.mDataSpace;
            this.mTransform = imageWriter.mSemTransform;
        }

        @Override // android.media.Image
        public int getDataSpace() {
            throwISEIfImageIsInvalid();
            return this.mDataSpace;
        }

        @Override // android.media.Image
        public void setDataSpace(int i) {
            throwISEIfImageIsInvalid();
            this.mDataSpace = i;
        }

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            if (this.mFormat == -1) {
                this.mFormat = nativeGetFormat(this.mDataSpace);
            }
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            if (this.mWidth == -1) {
                this.mWidth = nativeGetWidth();
            }
            return this.mWidth;
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            if (this.mHeight == -1) {
                this.mHeight = nativeGetHeight();
            }
            return this.mHeight;
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
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public void setTimestamp(long j) {
            throwISEIfImageIsInvalid();
            this.mTimestamp = j;
        }

        @Override // android.media.Image
        public HardwareBuffer getHardwareBuffer() {
            throwISEIfImageIsInvalid();
            return nativeGetHardwareBuffer();
        }

        @Override // android.media.Image
        public SyncFence getFence() throws IOException {
            throwISEIfImageIsInvalid();
            int i = this.mNativeFenceFd;
            if (i != -1) {
                return SyncFence.create(ParcelFileDescriptor.fromFd(i));
            }
            return SyncFence.createEmpty();
        }

        @Override // android.media.Image
        public void setFence(SyncFence syncFence) throws IOException {
            throwISEIfImageIsInvalid();
            if (syncFence.isValid()) {
                nativeSetFenceFd(syncFence.getFdDup().detachFd());
            } else {
                nativeSetFenceFd(-1);
            }
        }

        @Override // android.media.Image
        public Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = nativeCreatePlanes(ImageUtils.getNumPlanesForFormat(getFormat()), getOwner().getFormat());
            }
            return (Image.Plane[]) this.mPlanes.clone();
        }

        @Override // android.media.Image
        public boolean isAttachable() {
            throwISEIfImageIsInvalid();
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.Image
        public ImageWriter getOwner() {
            throwISEIfImageIsInvalid();
            return this.mOwner;
        }

        @Override // android.media.Image
        long getNativeContext() {
            throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mCloseLock) {
                if (this.mIsImageValid) {
                    getOwner().abortImage(this);
                }
            }
        }

        protected final void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
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
            public int getRowStride() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mRowStride;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mPixelStride;
            }

            @Override // android.media.Image.Plane
            public ByteBuffer getBuffer() {
                WriterSurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
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

    static {
        System.loadLibrary("media_jni");
        nativeClassInit();
    }
}
