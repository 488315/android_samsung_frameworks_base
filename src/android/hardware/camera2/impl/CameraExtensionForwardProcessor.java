package android.hardware.camera2.impl;

import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.IPreviewImageProcessorImpl;
import android.hardware.camera2.extension.IProcessResultImpl;
import android.hardware.camera2.extension.ParcelImage;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

/* loaded from: classes2.dex */
public class CameraExtensionForwardProcessor {
    private static final int FORWARD_QUEUE_SIZE = 3;
    public static final String TAG = "CameraExtensionForward";
    private final Handler mHandler;
    private final int mOutputSurfaceFormat;
    private final long mOutputSurfaceUsage;
    private final IPreviewImageProcessorImpl mProcessor;
    private ImageReader mIntermediateReader = null;
    private Surface mIntermediateSurface = null;
    private Size mResolution = null;
    private Surface mOutputSurface = null;
    private ImageWriter mOutputWriter = null;
    private boolean mOutputAbandoned = false;

    public CameraExtensionForwardProcessor(IPreviewImageProcessorImpl iPreviewImageProcessorImpl, int i, long j, Handler handler) {
        this.mProcessor = iPreviewImageProcessorImpl;
        this.mOutputSurfaceUsage = j;
        this.mOutputSurfaceFormat = i;
        this.mHandler = handler;
    }

    public void close() {
        ImageWriter imageWriter = this.mOutputWriter;
        if (imageWriter != null) {
            imageWriter.close();
            this.mOutputWriter = null;
        }
        ImageReader imageReader = this.mIntermediateReader;
        if (imageReader != null) {
            imageReader.close();
            this.mIntermediateReader = null;
        }
    }

    public void onOutputSurface(Surface surface, int i) {
        this.mOutputSurface = surface;
        try {
            initializePipeline();
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to initialize forward processor, extension service does not respond!");
        }
    }

    public void onResolutionUpdate(Size size) {
        this.mResolution = size;
    }

    public void onImageFormatUpdate(int i) {
        if (i != 35) {
            Log.e(TAG, "Unsupported input format: " + i);
        }
    }

    private void initializePipeline() throws RemoteException {
        ImageWriter imageWriter = this.mOutputWriter;
        if (imageWriter != null) {
            imageWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mIntermediateReader == null) {
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.mResolution.getWidth(), this.mResolution.getHeight(), 35, 3, this.mOutputSurfaceUsage);
            this.mIntermediateReader = imageReaderNewInstance;
            this.mIntermediateSurface = imageReaderNewInstance.getSurface();
            this.mIntermediateReader.setOnImageAvailableListener(new ForwardCallback(), this.mHandler);
            this.mProcessor.onOutputSurface(this.mIntermediateSurface, this.mOutputSurfaceFormat);
            this.mProcessor.onImageFormatUpdate(35);
            android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
            size.width = this.mResolution.getWidth();
            size.height = this.mResolution.getHeight();
            this.mProcessor.onResolutionUpdate(size);
        }
    }

    public void process(ParcelImage parcelImage, TotalCaptureResult totalCaptureResult, IProcessResultImpl iProcessResultImpl) throws RemoteException {
        Surface surface = this.mIntermediateSurface;
        if (surface == null || !surface.isValid() || this.mOutputAbandoned) {
            return;
        }
        this.mProcessor.process(parcelImage, totalCaptureResult.getNativeMetadata(), totalCaptureResult.getSequenceId(), iProcessResultImpl);
    }

    private class ForwardCallback implements ImageReader.OnImageAvailableListener {
        private ForwardCallback() {
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            try {
                Image imageAcquireNextImage = imageReader.acquireNextImage();
                if (imageAcquireNextImage == null) {
                    Log.e(CameraExtensionForwardProcessor.TAG, "Invalid image");
                    return;
                }
                if (CameraExtensionForwardProcessor.this.mOutputSurface != null && CameraExtensionForwardProcessor.this.mOutputSurface.isValid() && !CameraExtensionForwardProcessor.this.mOutputAbandoned) {
                    if (CameraExtensionForwardProcessor.this.mOutputWriter == null) {
                        CameraExtensionForwardProcessor cameraExtensionForwardProcessor = CameraExtensionForwardProcessor.this;
                        cameraExtensionForwardProcessor.mOutputWriter = ImageWriter.newInstance(cameraExtensionForwardProcessor.mOutputSurface, 3, imageAcquireNextImage.getFormat());
                    }
                    try {
                        CameraExtensionForwardProcessor.this.mOutputWriter.queueInputImage(imageAcquireNextImage);
                        return;
                    } catch (IllegalStateException unused) {
                        Log.e(CameraExtensionForwardProcessor.TAG, "Failed to queue processed buffer!");
                        imageAcquireNextImage.close();
                        CameraExtensionForwardProcessor.this.mOutputAbandoned = true;
                        return;
                    }
                }
                imageAcquireNextImage.close();
            } catch (IllegalStateException unused2) {
                Log.e(CameraExtensionForwardProcessor.TAG, "Failed to acquire processed image!");
            }
        }
    }
}
