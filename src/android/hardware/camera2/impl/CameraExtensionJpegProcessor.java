package android.hardware.camera2.impl;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.extension.CaptureBundle;
import android.hardware.camera2.extension.ICaptureProcessorImpl;
import android.hardware.camera2.extension.IProcessResultImpl;
import android.hardware.camera2.extension.Size;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes2.dex */
public class CameraExtensionJpegProcessor implements ICaptureProcessorImpl {
    private static final int JPEG_APP_SEGMENT_SIZE = 65536;
    private static final int JPEG_QUEUE_SIZE = 1;
    public static final String TAG = "CameraExtensionJpeg";
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final ICaptureProcessorImpl mProcessor;
    private ImageReader mYuvReader = null;
    private ImageReader mPostviewYuvReader = null;
    private Size mResolution = null;
    private Size mPostviewResolution = null;
    private int mFormat = -1;
    private int mPostviewFormat = -1;
    private int mCaptureFormat = -1;
    private Surface mOutputSurface = null;
    private ImageWriter mOutputWriter = null;
    private Surface mPostviewOutputSurface = null;
    private ImageWriter mPostviewOutputWriter = null;
    private ConcurrentLinkedQueue<JpegParameters> mJpegParameters = new ConcurrentLinkedQueue<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int compressJpegFromYUV420pNative(int i, int i2, ByteBuffer byteBuffer, int i3, int i4, ByteBuffer byteBuffer2, int i5, int i6, ByteBuffer byteBuffer3, int i7, int i8, ByteBuffer byteBuffer4, int i9, int i10, int i11, int i12, int i13, int i14, int i15);

    private static final class JpegParameters {
        public int mQuality;
        public int mRotation;
        public HashSet<Long> mTimeStamps;

        private JpegParameters() {
            this.mTimeStamps = new HashSet<>();
            this.mRotation = 0;
            this.mQuality = 100;
        }
    }

    public CameraExtensionJpegProcessor(ICaptureProcessorImpl iCaptureProcessorImpl) {
        this.mProcessor = iCaptureProcessorImpl;
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public void close() {
        this.mHandlerThread.quitSafely();
        ImageWriter imageWriter = this.mOutputWriter;
        if (imageWriter != null) {
            imageWriter.close();
            this.mOutputWriter = null;
        }
        ImageReader imageReader = this.mYuvReader;
        if (imageReader != null) {
            imageReader.close();
            this.mYuvReader = null;
        }
    }

    private static JpegParameters getJpegParameters(List<CaptureBundle> list) {
        JpegParameters jpegParameters = new JpegParameters();
        if (!list.isEmpty()) {
            Byte b = (Byte) list.get(0).captureResult.get(CaptureResult.JPEG_QUALITY);
            if (b == null) {
                Log.w(TAG, "No jpeg quality set, using default: 100");
            } else {
                jpegParameters.mQuality = b.byteValue();
            }
            Integer num = (Integer) list.get(0).captureResult.get(CaptureResult.JPEG_ORIENTATION);
            if (num == null) {
                Log.w(TAG, "No jpeg rotation set, using default: 0");
            } else {
                jpegParameters.mRotation = (360 - (num.intValue() % 360)) / 90;
            }
            Iterator<CaptureBundle> it = list.iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next().captureResult.get(CaptureResult.SENSOR_TIMESTAMP);
                if (l == null) {
                    Log.e(TAG, "Capture bundle without valid sensor timestamp!");
                } else {
                    jpegParameters.mTimeStamps.add(l);
                }
            }
        }
        return jpegParameters;
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void process(List<CaptureBundle> list, IProcessResultImpl iProcessResultImpl, boolean z) throws Exception {
        JpegParameters jpegParameters = getJpegParameters(list);
        try {
            this.mJpegParameters.add(jpegParameters);
            this.mProcessor.process(list, iProcessResultImpl, z);
        } catch (Exception e) {
            this.mJpegParameters.remove(jpegParameters);
            throw e;
        }
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onOutputSurface(Surface surface, int i) throws RemoteException {
        this.mCaptureFormat = CameraExtensionUtils.querySurface(surface).mFormat;
        this.mOutputSurface = surface;
        initializePipeline();
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onPostviewOutputSurface(Surface surface) throws RemoteException {
        this.mPostviewFormat = CameraExtensionUtils.querySurface(surface).mFormat;
        this.mPostviewOutputSurface = surface;
        initializePostviewPipeline();
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onResolutionUpdate(Size size, Size size2) throws RemoteException {
        this.mResolution = size;
        this.mPostviewResolution = size2;
        initializePipeline();
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onImageFormatUpdate(int i) throws RemoteException {
        this.mFormat = i;
        initializePipeline();
    }

    private void initializePipeline() throws RemoteException {
        Surface surface;
        Size size;
        if (this.mFormat == -1 || (surface = this.mOutputSurface) == null || (size = this.mResolution) == null || this.mYuvReader != null) {
            return;
        }
        int i = this.mCaptureFormat;
        if (i == 35) {
            this.mProcessor.onOutputSurface(surface, i);
        } else {
            this.mOutputWriter = ImageWriter.newInstance(surface, 1, 256, (((size.width * this.mResolution.height) * 3) / 2) + 65536, 1);
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.mResolution.width, this.mResolution.height, this.mFormat, 1);
            this.mYuvReader = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(new YuvCallback(this.mYuvReader, this.mOutputWriter), this.mHandler);
            this.mProcessor.onOutputSurface(this.mYuvReader.getSurface(), this.mFormat);
        }
        this.mProcessor.onResolutionUpdate(this.mResolution, this.mPostviewResolution);
        this.mProcessor.onImageFormatUpdate(35);
    }

    private void initializePostviewPipeline() throws RemoteException {
        Surface surface;
        Size size;
        if (this.mFormat == -1 || (surface = this.mPostviewOutputSurface) == null || (size = this.mPostviewResolution) == null || this.mPostviewYuvReader != null) {
            return;
        }
        if (this.mPostviewFormat == 35) {
            this.mProcessor.onPostviewOutputSurface(surface);
        } else {
            this.mPostviewOutputWriter = ImageWriter.newInstance(surface, 1, 256, size.width * this.mPostviewResolution.height, 1);
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.mPostviewResolution.width, this.mPostviewResolution.height, this.mFormat, 1);
            this.mPostviewYuvReader = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(new YuvCallback(this.mPostviewYuvReader, this.mPostviewOutputWriter), this.mHandler);
            this.mProcessor.onPostviewOutputSurface(this.mPostviewYuvReader.getSurface());
        }
        this.mProcessor.onResolutionUpdate(this.mResolution, this.mPostviewResolution);
        this.mProcessor.onImageFormatUpdate(35);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        throw new UnsupportedOperationException("Binder IPC not supported!");
    }

    private class YuvCallback implements ImageReader.OnImageAvailableListener {
        private ImageReader mImageReader;
        private ImageWriter mImageWriter;

        public YuvCallback(ImageReader imageReader, ImageWriter imageWriter) {
            this.mImageReader = imageReader;
            this.mImageWriter = imageWriter;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            JpegParameters jpegParameters;
            Image image = null;
            Object[] objArr = 0;
            try {
                Image imageAcquireNextImage = this.mImageReader.acquireNextImage();
                try {
                    Image imageDequeueInputImage = this.mImageWriter.dequeueInputImage();
                    ByteBuffer buffer = imageDequeueInputImage.getPlanes()[0].getBuffer();
                    buffer.clear();
                    int width = imageDequeueInputImage.getWidth();
                    Image.Plane plane = imageAcquireNextImage.getPlanes()[0];
                    Image.Plane plane2 = imageAcquireNextImage.getPlanes()[1];
                    Image.Plane plane3 = imageAcquireNextImage.getPlanes()[2];
                    ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue(CameraExtensionJpegProcessor.this.mJpegParameters);
                    Iterator it = concurrentLinkedQueue.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            jpegParameters = null;
                            break;
                        }
                        jpegParameters = (JpegParameters) it.next();
                        if (jpegParameters.mTimeStamps.contains(Long.valueOf(imageAcquireNextImage.getTimestamp()))) {
                            it.remove();
                            break;
                        }
                    }
                    if (jpegParameters == null) {
                        if (concurrentLinkedQueue.isEmpty()) {
                            Log.w(CameraExtensionJpegProcessor.TAG, "Empty jpeg settings queue! Using default jpeg orientation and quality!");
                            jpegParameters = new JpegParameters();
                            jpegParameters.mRotation = 0;
                            jpegParameters.mQuality = 100;
                        } else {
                            Log.w(CameraExtensionJpegProcessor.TAG, "No jpeg settings found with matching timestamp for current processed input!");
                            Log.w(CameraExtensionJpegProcessor.TAG, "Using values from the top of the queue!");
                            jpegParameters = (JpegParameters) concurrentLinkedQueue.poll();
                        }
                    }
                    CameraExtensionJpegProcessor.compressJpegFromYUV420pNative(imageAcquireNextImage.getWidth(), imageAcquireNextImage.getHeight(), plane.getBuffer(), plane.getPixelStride(), plane.getRowStride(), plane2.getBuffer(), plane2.getPixelStride(), plane2.getRowStride(), plane3.getBuffer(), plane3.getPixelStride(), plane3.getRowStride(), buffer, width, jpegParameters.mQuality, 0, 0, imageAcquireNextImage.getWidth(), imageAcquireNextImage.getHeight(), jpegParameters.mRotation);
                    imageDequeueInputImage.setTimestamp(imageAcquireNextImage.getTimestamp());
                    imageAcquireNextImage.close();
                    try {
                        this.mImageWriter.queueInputImage(imageDequeueInputImage);
                    } catch (IllegalStateException unused) {
                        Log.e(CameraExtensionJpegProcessor.TAG, "Failed to queue encoded result!");
                    } finally {
                        imageDequeueInputImage.close();
                    }
                } catch (IllegalStateException unused2) {
                    image = imageAcquireNextImage;
                    if (image != null) {
                        image.close();
                    }
                    Log.e(CameraExtensionJpegProcessor.TAG, "Failed to acquire processed yuv image or jpeg image!");
                }
            } catch (IllegalStateException unused3) {
            }
        }
    }
}
