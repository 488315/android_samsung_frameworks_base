package android.hardware.camera2.impl;

import android.content.Context;
import android.hardware.SyncFence;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraExtensionSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.CaptureBundle;
import android.hardware.camera2.extension.CaptureStageImpl;
import android.hardware.camera2.extension.ICaptureProcessorImpl;
import android.hardware.camera2.extension.IImageCaptureExtenderImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IPreviewExtenderImpl;
import android.hardware.camera2.extension.IProcessResultImpl;
import android.hardware.camera2.extension.IRequestUpdateProcessorImpl;
import android.hardware.camera2.extension.LatencyPair;
import android.hardware.camera2.extension.ParcelImage;
import android.hardware.camera2.impl.CameraExtensionSessionImpl;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.ExtensionSessionStatsAggregator;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class CameraExtensionSessionImpl extends CameraExtensionSession {
    private static final int PREVIEW_QUEUE_SIZE = 10;
    private static final String TAG = "CameraExtensionSessionImpl";
    private final CameraExtensionSession.StateCallback mCallbacks;
    private Surface mCameraBurstSurface;
    private final CameraDevice mCameraDevice;
    private Surface mCameraRepeatingSurface;
    private boolean mCaptureResultsSupported;
    private Surface mClientCaptureSurface;
    private Surface mClientPostviewSurface;
    private Surface mClientRepeatingRequestSurface;
    private final Context mContext;
    private final Executor mExecutor;
    private int mExtensionType;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final IImageCaptureExtenderImpl mImageExtender;
    private final InitializeSessionHandler mInitializeHandler;
    private boolean mInitialized;
    final Object mInterfaceLock;
    private final IPreviewExtenderImpl mPreviewExtender;
    private boolean mSessionClosed;
    private final int mSessionId;
    private final ExtensionSessionStatsAggregator mStatsAggregator;
    private final List<Size> mSupportedPreviewSizes;
    private final Set<CaptureRequest.Key> mSupportedRequestKeys;
    private final Set<CaptureResult.Key> mSupportedResultKeys;
    private IBinder mToken;
    private CameraCaptureSession mCaptureSession = null;
    private ImageReader mRepeatingRequestImageReader = null;
    private ImageReader mBurstCaptureImageReader = null;
    private ImageReader mStubCaptureImageReader = null;
    private ImageWriter mRepeatingRequestImageWriter = null;
    private CameraOutputImageCallback mRepeatingRequestImageCallback = null;
    private CameraOutputImageCallback mBurstCaptureImageCallback = null;
    private CameraExtensionJpegProcessor mImageJpegProcessor = null;
    private ICaptureProcessorImpl mImageProcessor = null;
    private CameraExtensionForwardProcessor mPreviewImageProcessor = null;
    private IRequestUpdateProcessorImpl mPreviewRequestUpdateProcessor = null;
    private int mPreviewProcessorType = 2;
    private boolean mInternalRepeatingRequestEnabled = true;

    private interface OnImageAvailableListener {
        void onImageAvailable(ImageReader imageReader, Image image);

        void onImageDropped(long j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int nativeGetSurfaceFormat(Surface surface) {
        return SurfaceUtils.getSurfaceFormat(surface);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.hardware.camera2.impl.CameraExtensionSessionImpl createCameraExtensionSession(android.hardware.camera2.impl.CameraDeviceImpl r19, java.util.Map<java.lang.String, android.hardware.camera2.CameraCharacteristics> r20, android.content.Context r21, android.hardware.camera2.params.ExtensionSessionConfiguration r22, int r23, android.os.IBinder r24) throws android.hardware.camera2.CameraAccessException, android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 573
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraExtensionSessionImpl.createCameraExtensionSession(android.hardware.camera2.impl.CameraDeviceImpl, java.util.Map, android.content.Context, android.hardware.camera2.params.ExtensionSessionConfiguration, int, android.os.IBinder):android.hardware.camera2.impl.CameraExtensionSessionImpl");
    }

    public CameraExtensionSessionImpl(Context context, IImageCaptureExtenderImpl iImageCaptureExtenderImpl, IPreviewExtenderImpl iPreviewExtenderImpl, List<Size> list, CameraDeviceImpl cameraDeviceImpl, Surface surface, Surface surface2, Surface surface3, CameraExtensionSession.StateCallback stateCallback, Executor executor, int i, IBinder iBinder, Set<CaptureRequest.Key> set, Set<CaptureResult.Key> set2, int i2) {
        this.mToken = null;
        this.mContext = context;
        this.mImageExtender = iImageCaptureExtenderImpl;
        this.mPreviewExtender = iPreviewExtenderImpl;
        this.mCameraDevice = cameraDeviceImpl;
        this.mCallbacks = stateCallback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestSurface = surface;
        this.mClientCaptureSurface = surface2;
        this.mClientPostviewSurface = surface3;
        this.mSupportedPreviewSizes = list;
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new InitializeSessionHandler();
        this.mSessionId = i;
        this.mToken = iBinder;
        this.mSupportedRequestKeys = set;
        this.mSupportedResultKeys = set2;
        this.mCaptureResultsSupported = !set2.isEmpty();
        this.mInterfaceLock = cameraDeviceImpl.mInterfaceLock;
        this.mExtensionType = i2;
        this.mStatsAggregator = new ExtensionSessionStatsAggregator(cameraDeviceImpl.getId(), false);
    }

    private void initializeRepeatingRequestPipeline() throws RemoteException {
        CameraExtensionUtils.SurfaceInfo surfaceInfo = new CameraExtensionUtils.SurfaceInfo();
        this.mPreviewProcessorType = this.mPreviewExtender.getProcessorType();
        Surface surface = this.mClientRepeatingRequestSurface;
        if (surface != null) {
            surfaceInfo = CameraExtensionUtils.querySurface(surface);
        } else {
            CameraExtensionUtils.SurfaceInfo querySurface = CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
            Size findSmallestAspectMatchedSize = findSmallestAspectMatchedSize(this.mSupportedPreviewSizes, new Size(querySurface.mWidth, querySurface.mHeight));
            surfaceInfo.mWidth = findSmallestAspectMatchedSize.getWidth();
            surfaceInfo.mHeight = findSmallestAspectMatchedSize.getHeight();
            surfaceInfo.mUsage = 256L;
        }
        int i = this.mPreviewProcessorType;
        if (i == 1) {
            try {
                CameraExtensionForwardProcessor cameraExtensionForwardProcessor = new CameraExtensionForwardProcessor(this.mPreviewExtender.getPreviewImageProcessor(), surfaceInfo.mFormat, surfaceInfo.mUsage, this.mHandler);
                this.mPreviewImageProcessor = cameraExtensionForwardProcessor;
                cameraExtensionForwardProcessor.onImageFormatUpdate(35);
                this.mPreviewImageProcessor.onResolutionUpdate(new Size(surfaceInfo.mWidth, surfaceInfo.mHeight));
                this.mPreviewImageProcessor.onOutputSurface(null, -1);
                ImageReader newInstance = ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 35, 10, surfaceInfo.mUsage);
                this.mRepeatingRequestImageReader = newInstance;
                this.mCameraRepeatingSurface = newInstance.getSurface();
            } catch (ClassCastException unused) {
                throw new UnsupportedOperationException("Failed casting preview processor!");
            }
        } else if (i == 0) {
            try {
                this.mPreviewRequestUpdateProcessor = this.mPreviewExtender.getRequestUpdateProcessor();
                ImageReader newInstance2 = ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 34, 10, surfaceInfo.mUsage);
                this.mRepeatingRequestImageReader = newInstance2;
                this.mCameraRepeatingSurface = newInstance2.getSurface();
                android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
                size.width = surfaceInfo.mWidth;
                size.height = surfaceInfo.mHeight;
                this.mPreviewRequestUpdateProcessor.onResolutionUpdate(size);
                this.mPreviewRequestUpdateProcessor.onImageFormatUpdate(34);
            } catch (ClassCastException unused2) {
                throw new UnsupportedOperationException("Failed casting preview processor!");
            }
        } else {
            ImageReader newInstance3 = ImageReader.newInstance(surfaceInfo.mWidth, surfaceInfo.mHeight, 34, 10, surfaceInfo.mUsage);
            this.mRepeatingRequestImageReader = newInstance3;
            this.mCameraRepeatingSurface = newInstance3.getSurface();
        }
        CameraOutputImageCallback cameraOutputImageCallback = new CameraOutputImageCallback(this.mRepeatingRequestImageReader, true);
        this.mRepeatingRequestImageCallback = cameraOutputImageCallback;
        this.mRepeatingRequestImageReader.setOnImageAvailableListener(cameraOutputImageCallback, this.mHandler);
    }

    private void initializeBurstCapturePipeline() throws RemoteException {
        ICaptureProcessorImpl captureProcessor = this.mImageExtender.getCaptureProcessor();
        this.mImageProcessor = captureProcessor;
        if (captureProcessor == null && this.mImageExtender.getMaxCaptureStage() != 1) {
            throw new UnsupportedOperationException("Multiple stages expected without a valid capture processor!");
        }
        if (this.mImageProcessor != null) {
            Surface surface = this.mClientCaptureSurface;
            if (surface != null) {
                CameraExtensionUtils.SurfaceInfo querySurface = CameraExtensionUtils.querySurface(surface);
                if (querySurface.mFormat == 256) {
                    CameraExtensionJpegProcessor cameraExtensionJpegProcessor = new CameraExtensionJpegProcessor(this.mImageProcessor);
                    this.mImageJpegProcessor = cameraExtensionJpegProcessor;
                    this.mImageProcessor = cameraExtensionJpegProcessor;
                } else {
                    Surface surface2 = this.mClientPostviewSurface;
                    if (surface2 != null && CameraExtensionUtils.querySurface(surface2).mFormat == 256) {
                        CameraExtensionJpegProcessor cameraExtensionJpegProcessor2 = new CameraExtensionJpegProcessor(this.mImageProcessor);
                        this.mImageJpegProcessor = cameraExtensionJpegProcessor2;
                        this.mImageProcessor = cameraExtensionJpegProcessor2;
                    }
                }
                this.mBurstCaptureImageReader = ImageReader.newInstance(querySurface.mWidth, querySurface.mHeight, 35, this.mImageExtender.getMaxCaptureStage());
            } else {
                this.mBurstCaptureImageReader = ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 35, 1);
                ImageReader newInstance = ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 35, 1);
                this.mStubCaptureImageReader = newInstance;
                this.mImageProcessor.onOutputSurface(newInstance.getSurface(), 35);
            }
            CameraOutputImageCallback cameraOutputImageCallback = new CameraOutputImageCallback(this.mBurstCaptureImageReader, false);
            this.mBurstCaptureImageCallback = cameraOutputImageCallback;
            this.mBurstCaptureImageReader.setOnImageAvailableListener(cameraOutputImageCallback, this.mHandler);
            this.mCameraBurstSurface = this.mBurstCaptureImageReader.getSurface();
            android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
            size.width = this.mBurstCaptureImageReader.getWidth();
            size.height = this.mBurstCaptureImageReader.getHeight();
            Surface surface3 = this.mClientPostviewSurface;
            if (surface3 != null) {
                CameraExtensionUtils.SurfaceInfo querySurface2 = CameraExtensionUtils.querySurface(surface3);
                android.hardware.camera2.extension.Size size2 = new android.hardware.camera2.extension.Size();
                size2.width = querySurface2.mWidth;
                size2.height = querySurface2.mHeight;
                this.mImageProcessor.onResolutionUpdate(size, size2);
            } else {
                this.mImageProcessor.onResolutionUpdate(size, null);
            }
            this.mImageProcessor.onImageFormatUpdate(this.mBurstCaptureImageReader.getImageFormat());
            return;
        }
        Surface surface4 = this.mClientCaptureSurface;
        if (surface4 != null) {
            this.mCameraBurstSurface = surface4;
            return;
        }
        ImageReader newInstance2 = ImageReader.newInstance(this.mRepeatingRequestImageReader.getWidth(), this.mRepeatingRequestImageReader.getHeight(), 256, 1);
        this.mBurstCaptureImageReader = newInstance2;
        this.mCameraBurstSurface = newInstance2.getSurface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPipelineInitialization() throws RemoteException {
        Surface surface = this.mClientRepeatingRequestSurface;
        if (surface != null) {
            int i = this.mPreviewProcessorType;
            if (i == 0) {
                this.mPreviewRequestUpdateProcessor.onOutputSurface(surface, nativeGetSurfaceFormat(surface));
                this.mRepeatingRequestImageWriter = ImageWriter.newInstance(this.mClientRepeatingRequestSurface, 10, 34);
            } else if (i == 2) {
                this.mRepeatingRequestImageWriter = ImageWriter.newInstance(surface, 10, 34);
            }
        }
        ICaptureProcessorImpl iCaptureProcessorImpl = this.mImageProcessor;
        if (iCaptureProcessorImpl == null || this.mClientCaptureSurface == null) {
            return;
        }
        Surface surface2 = this.mClientPostviewSurface;
        if (surface2 != null) {
            iCaptureProcessorImpl.onPostviewOutputSurface(surface2);
        }
        this.mImageProcessor.onOutputSurface(this.mClientCaptureSurface, CameraExtensionUtils.querySurface(this.mClientCaptureSurface).mFormat);
    }

    public synchronized void initialize() throws CameraAccessException, RemoteException {
        if (this.mInitialized) {
            Log.d(TAG, "Session already initialized");
            return;
        }
        int sessionType = this.mPreviewExtender.getSessionType();
        int sessionType2 = this.mImageExtender.getSessionType();
        if (sessionType != sessionType2) {
            throw new IllegalStateException("Preview extender session type: " + sessionType + "and image extender session type: " + sessionType2 + " mismatch!");
        }
        if (sessionType == -1 || sessionType == 1) {
            sessionType = 0;
        } else {
            Log.v(TAG, "Using session type: " + sessionType);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        initializeRepeatingRequestPipeline();
        OutputConfiguration outputConfiguration = new OutputConfiguration(this.mCameraRepeatingSurface);
        outputConfiguration.setTimestampBase(1);
        outputConfiguration.setReadoutTimestampEnabled(false);
        arrayList2.add(outputConfiguration);
        CaptureStageImpl onPresetSession = this.mPreviewExtender.onPresetSession();
        if (onPresetSession != null) {
            arrayList.add(onPresetSession);
        }
        initializeBurstCapturePipeline();
        OutputConfiguration outputConfiguration2 = new OutputConfiguration(this.mCameraBurstSurface);
        outputConfiguration2.setTimestampBase(1);
        outputConfiguration2.setReadoutTimestampEnabled(false);
        arrayList2.add(outputConfiguration2);
        CaptureStageImpl onPresetSession2 = this.mImageExtender.onPresetSession();
        if (onPresetSession2 != null) {
            arrayList.add(onPresetSession2);
        }
        SessionConfiguration sessionConfiguration = new SessionConfiguration(sessionType, arrayList2, new CameraExtensionUtils.HandlerExecutor(this.mHandler), new SessionStateHandler());
        if (!arrayList.isEmpty()) {
            sessionConfiguration.setSessionParameters(createRequest(this.mCameraDevice, arrayList, null, 1));
        }
        this.mCameraDevice.createCaptureSession(sessionConfiguration);
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public CameraDevice getDevice() {
        CameraDevice cameraDevice;
        synchronized (this.mInterfaceLock) {
            cameraDevice = this.mCameraDevice;
        }
        return cameraDevice;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public CameraExtensionSession.StillCaptureLatency getRealtimeStillCaptureLatency() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            try {
                LatencyPair realtimeCaptureLatency = this.mImageExtender.getRealtimeCaptureLatency();
                if (realtimeCaptureLatency == null) {
                    return null;
                }
                return new CameraExtensionSession.StillCaptureLatency(realtimeCaptureLatency.first, realtimeCaptureLatency.second);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to query realtime latency! Extension service does not respond");
                throw new CameraAccessException(3);
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int setRepeatingRequest(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws CameraAccessException {
        int repeatingRequest;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            Surface surface = this.mClientRepeatingRequestSurface;
            if (surface == null) {
                throw new IllegalArgumentException("No registered preview surface");
            }
            if (!captureRequest.containsTarget(surface) || captureRequest.getTargets().size() != 1) {
                throw new IllegalArgumentException("Invalid repeating request output target!");
            }
            this.mInternalRepeatingRequestEnabled = false;
            try {
                repeatingRequest = setRepeatingRequest(this.mPreviewExtender.getCaptureStage(), new PreviewRequestHandler(this, captureRequest, executor, extensionCaptureCallback, this.mRepeatingRequestImageCallback), captureRequest);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to set repeating request! Extension service does not respond");
                throw new CameraAccessException(3);
            }
        }
        return repeatingRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<CaptureStageImpl> compileInitialRequestList() {
        ArrayList<CaptureStageImpl> arrayList = new ArrayList<>();
        try {
            CaptureStageImpl onEnableSession = this.mPreviewExtender.onEnableSession();
            if (onEnableSession != null) {
                arrayList.add(onEnableSession);
            }
            CaptureStageImpl onEnableSession2 = this.mImageExtender.onEnableSession();
            if (onEnableSession2 != null) {
                arrayList.add(onEnableSession2);
                return arrayList;
            }
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to initialize session parameters! Extension service does not respond!");
        }
        return arrayList;
    }

    private List<CaptureRequest> createBurstRequest(CameraDevice cameraDevice, List<CaptureStageImpl> list, CaptureRequest captureRequest, Surface surface, int i, Map<CaptureRequest, Integer> map) {
        ArrayList arrayList = new ArrayList();
        for (CaptureStageImpl captureStageImpl : list) {
            try {
                CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(i);
                for (CaptureRequest.Key key : this.mSupportedRequestKeys) {
                    Object obj = captureRequest.get(key);
                    if (obj != null) {
                        captureStageImpl.parameters.set((CaptureRequest.Key<CaptureRequest.Key>) key, (CaptureRequest.Key) obj);
                    }
                }
                createCaptureRequest.addTarget(surface);
                CaptureRequest build = createCaptureRequest.build();
                CameraMetadataNative.update(build.getNativeMetadata(), captureStageImpl.parameters);
                arrayList.add(build);
                if (map != null) {
                    map.put(build, Integer.valueOf(captureStageImpl.id));
                }
            } catch (CameraAccessException unused) {
                return null;
            }
        }
        return arrayList;
    }

    private CaptureRequest createRequest(CameraDevice cameraDevice, List<CaptureStageImpl> list, Surface surface, int i, CaptureRequest captureRequest) throws CameraAccessException {
        CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(i);
        if (surface != null) {
            createCaptureRequest.addTarget(surface);
        }
        CaptureRequest build = createCaptureRequest.build();
        CameraMetadataNative nativeMetadata = build.getNativeMetadata();
        for (CaptureStageImpl captureStageImpl : list) {
            if (captureStageImpl != null) {
                CameraMetadataNative.update(nativeMetadata, captureStageImpl.parameters);
            }
        }
        if (captureRequest != null) {
            for (CaptureRequest.Key key : this.mSupportedRequestKeys) {
                Object obj = captureRequest.get(key);
                if (obj != null) {
                    nativeMetadata.set((CaptureRequest.Key<CaptureRequest.Key>) key, (CaptureRequest.Key) obj);
                }
            }
        }
        return build;
    }

    private CaptureRequest createRequest(CameraDevice cameraDevice, List<CaptureStageImpl> list, Surface surface, int i) throws CameraAccessException {
        return createRequest(cameraDevice, list, surface, i, null);
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws CameraAccessException {
        if (!this.mInitialized) {
            throw new IllegalStateException("Uninitialized component");
        }
        validateCaptureRequestTargets(captureRequest);
        Surface surface = this.mClientCaptureSurface;
        if (surface != null && captureRequest.containsTarget(surface)) {
            HashMap hashMap = new HashMap();
            try {
                List<CaptureRequest> createBurstRequest = createBurstRequest(this.mCameraDevice, this.mImageExtender.getCaptureStages(), captureRequest, this.mCameraBurstSurface, 2, hashMap);
                if (createBurstRequest == null) {
                    throw new UnsupportedOperationException("Failed to create still capture burst request");
                }
                return this.mCaptureSession.captureBurstRequests(createBurstRequest, new CameraExtensionUtils.HandlerExecutor(this.mHandler), new BurstRequestHandler(captureRequest, executor, extensionCaptureCallback, hashMap, this.mBurstCaptureImageCallback));
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to initialize internal burst request! Extension service does not respond!");
                throw new CameraAccessException(3);
            }
        }
        Surface surface2 = this.mClientRepeatingRequestSurface;
        if (surface2 != null && captureRequest.containsTarget(surface2)) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.mPreviewExtender.getCaptureStage());
                return this.mCaptureSession.capture(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1, captureRequest), new PreviewRequestHandler(captureRequest, executor, extensionCaptureCallback, this.mRepeatingRequestImageCallback, true), this.mHandler);
            } catch (RemoteException unused2) {
                Log.e(TAG, "Failed to initialize capture request! Extension service does not respond!");
                throw new CameraAccessException(3);
            }
        }
        throw new IllegalArgumentException("Capture request to unknown output surface!");
    }

    private void validateCaptureRequestTargets(CaptureRequest captureRequest) {
        if (captureRequest.getTargets().size() == 1) {
            Surface surface = this.mClientCaptureSurface;
            boolean z = surface != null && captureRequest.containsTarget(surface);
            Surface surface2 = this.mClientRepeatingRequestSurface;
            boolean z2 = surface2 != null && captureRequest.containsTarget(surface2);
            if (!z && !z2) {
                throw new IllegalArgumentException("Target output combination requested is not supported!");
            }
        }
        if (captureRequest.getTargets().size() == 2 && !captureRequest.getTargets().containsAll(Arrays.asList(this.mClientCaptureSurface, this.mClientPostviewSurface))) {
            throw new IllegalArgumentException("Target output combination requested is not supported!");
        }
        if (captureRequest.getTargets().size() > 2) {
            throw new IllegalArgumentException("Target output combination requested is not supported!");
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            this.mInternalRepeatingRequestEnabled = true;
            this.mCaptureSession.stopRepeating();
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession, java.lang.AutoCloseable
    public void close() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                this.mInternalRepeatingRequestEnabled = false;
                try {
                    this.mCaptureSession.stopRepeating();
                } catch (IllegalStateException unused) {
                    this.mSessionClosed = true;
                }
                ArrayList arrayList = new ArrayList();
                try {
                    CaptureStageImpl onDisableSession = this.mPreviewExtender.onDisableSession();
                    if (onDisableSession != null) {
                        arrayList.add(onDisableSession);
                    }
                    CaptureStageImpl onDisableSession2 = this.mImageExtender.onDisableSession();
                    if (onDisableSession2 != null) {
                        arrayList.add(onDisableSession2);
                    }
                } catch (RemoteException unused2) {
                    Log.e(TAG, "Failed to disable extension! Extension service does not respond!");
                }
                if (!arrayList.isEmpty() && !this.mSessionClosed) {
                    this.mCaptureSession.capture(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1), new CloseRequestHandler(this.mRepeatingRequestImageCallback), this.mHandler);
                }
                this.mSessionClosed = true;
                this.mStatsAggregator.commit(true);
                this.mCaptureSession.close();
            }
        }
    }

    public void commitStats() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                this.mStatsAggregator.commit(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInitialCaptureRequest(List<CaptureStageImpl> list, InitialRequestHandler initialRequestHandler) throws CameraAccessException {
        this.mCaptureSession.capture(createRequest(this.mCameraDevice, list, this.mCameraRepeatingSurface, 1), initialRequestHandler, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(CaptureStageImpl captureStageImpl, CameraCaptureSession.CaptureCallback captureCallback) throws CameraAccessException {
        return setRepeatingRequest(captureStageImpl, captureCallback, (CaptureRequest) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(CaptureStageImpl captureStageImpl, CameraCaptureSession.CaptureCallback captureCallback, CaptureRequest captureRequest) throws CameraAccessException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(captureStageImpl);
        return this.mCaptureSession.setSingleRepeatingRequest(createRequest(this.mCameraDevice, arrayList, this.mCameraRepeatingSurface, 1, captureRequest), new CameraExtensionUtils.HandlerExecutor(this.mHandler), captureCallback);
    }

    public void release(boolean z) {
        boolean z2;
        synchronized (this.mInterfaceLock) {
            this.mInternalRepeatingRequestEnabled = false;
            this.mHandlerThread.quit();
            try {
                if (!this.mSessionClosed) {
                    this.mPreviewExtender.onDisableSession();
                    this.mImageExtender.onDisableSession();
                }
                this.mPreviewExtender.onDeInit(this.mToken);
                this.mImageExtender.onDeInit(this.mToken);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to release extensions! Extension service does not respond!");
            }
            if (this.mToken != null) {
                if (!this.mInitialized && this.mCaptureSession == null) {
                    z2 = false;
                    CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken, this.mExtensionType);
                }
                CameraExtensionCharacteristics.releaseSession(this.mExtensionType);
                z2 = true;
                CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken, this.mExtensionType);
            } else {
                z2 = false;
            }
            this.mInitialized = false;
            this.mToken = null;
            CameraOutputImageCallback cameraOutputImageCallback = this.mRepeatingRequestImageCallback;
            if (cameraOutputImageCallback != null) {
                cameraOutputImageCallback.close();
                this.mRepeatingRequestImageCallback = null;
            }
            ImageReader imageReader = this.mRepeatingRequestImageReader;
            if (imageReader != null) {
                imageReader.close();
                this.mRepeatingRequestImageReader = null;
            }
            CameraOutputImageCallback cameraOutputImageCallback2 = this.mBurstCaptureImageCallback;
            if (cameraOutputImageCallback2 != null) {
                cameraOutputImageCallback2.close();
                this.mBurstCaptureImageCallback = null;
            }
            ImageReader imageReader2 = this.mBurstCaptureImageReader;
            if (imageReader2 != null) {
                imageReader2.close();
                this.mBurstCaptureImageReader = null;
            }
            ImageReader imageReader3 = this.mStubCaptureImageReader;
            if (imageReader3 != null) {
                imageReader3.close();
                this.mStubCaptureImageReader = null;
            }
            ImageWriter imageWriter = this.mRepeatingRequestImageWriter;
            if (imageWriter != null) {
                imageWriter.close();
                this.mRepeatingRequestImageWriter = null;
            }
            CameraExtensionForwardProcessor cameraExtensionForwardProcessor = this.mPreviewImageProcessor;
            if (cameraExtensionForwardProcessor != null) {
                cameraExtensionForwardProcessor.close();
                this.mPreviewImageProcessor = null;
            }
            CameraExtensionJpegProcessor cameraExtensionJpegProcessor = this.mImageJpegProcessor;
            if (cameraExtensionJpegProcessor != null) {
                cameraExtensionJpegProcessor.close();
                this.mImageJpegProcessor = null;
            }
            this.mCaptureSession = null;
            this.mImageProcessor = null;
            this.mClientRepeatingRequestSurface = null;
            this.mCameraRepeatingSurface = null;
            this.mClientCaptureSurface = null;
            this.mCameraBurstSurface = null;
            this.mClientPostviewSurface = null;
        }
        if (!z2 || z) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CameraExtensionSessionImpl.this.lambda$release$0();
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$0() {
        this.mCallbacks.onClosed(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigurationFailure() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                return;
            }
            release(true);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.this.lambda$notifyConfigurationFailure$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationFailure$1() {
        this.mCallbacks.onConfigureFailed(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigurationSuccess() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                return;
            }
            this.mInitialized = true;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.this.lambda$notifyConfigurationSuccess$2();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationSuccess$2() {
        this.mCallbacks.onConfigured(this);
    }

    private class SessionStateHandler extends CameraCaptureSession.StateCallback {
        private SessionStateHandler() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            CameraExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                CameraExtensionSessionImpl.this.mCaptureSession = cameraCaptureSession;
                CameraExtensionSessionImpl.this.mStatsAggregator.commit(false);
                try {
                    CameraExtensionSessionImpl.this.finishPipelineInitialization();
                    CameraExtensionCharacteristics.initializeSession(CameraExtensionSessionImpl.this.mInitializeHandler, CameraExtensionSessionImpl.this.mExtensionType);
                } catch (RemoteException unused) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to initialize session! Extension service does not respond!");
                    CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            }
        }
    }

    private class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            CameraExtensionSessionImpl.this.mHandler.post(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    ArrayList compileInitialRequestList = CameraExtensionSessionImpl.this.compileInitialRequestList();
                    if (!compileInitialRequestList.isEmpty()) {
                        try {
                            CameraExtensionSessionImpl.this.setInitialCaptureRequest(compileInitialRequestList, CameraExtensionSessionImpl.this.new InitialRequestHandler(CameraExtensionSessionImpl.this.mRepeatingRequestImageCallback));
                            return;
                        } catch (CameraAccessException unused) {
                            Log.e(CameraExtensionSessionImpl.TAG, "Failed to initialize the initial capture request!");
                        }
                    } else {
                        try {
                            CameraExtensionSessionImpl.this.setRepeatingRequest(CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(), new PreviewRequestHandler(CameraExtensionSessionImpl.this, null, null, null, CameraExtensionSessionImpl.this.mRepeatingRequestImageCallback));
                            return;
                        } catch (CameraAccessException | RemoteException unused2) {
                            Log.e(CameraExtensionSessionImpl.TAG, "Failed to initialize internal repeating request!");
                        }
                    }
                    CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            CameraExtensionSessionImpl.this.mHandler.post(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    CameraExtensionSessionImpl.this.mCaptureSession.close();
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to initialize proxy service session! This can happen when trying to configure multiple concurrent extension sessions!");
                    CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BurstRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraOutputImageCallback mBurstImageCallback;
        private final CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private final HashMap<CaptureRequest, Integer> mCaptureRequestMap;
        private final CaptureRequest mClientRequest;
        private final Executor mExecutor;
        private HashMap<Integer, Pair<Image, TotalCaptureResult>> mCaptureStageMap = new HashMap<>();
        private LongSparseArray<Pair<Image, Integer>> mCapturePendingMap = new LongSparseArray<>();
        private ImageCallback mImageCallback = null;
        private boolean mCaptureFailed = false;
        private CaptureResultHandler mCaptureResultHandler = null;

        public BurstRequestHandler(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, HashMap<CaptureRequest, Integer> hashMap, CameraOutputImageCallback cameraOutputImageCallback) {
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            this.mCaptureRequestMap = hashMap;
            this.mBurstImageCallback = cameraOutputImageCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyCaptureFailed() {
            if (this.mCaptureFailed) {
                return;
            }
            this.mCaptureFailed = true;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$notifyCaptureFailed$0();
                    }
                });
                Binder.restoreCallingIdentity(clearCallingIdentity);
                for (Pair<Image, TotalCaptureResult> pair : this.mCaptureStageMap.values()) {
                    if (pair.first != null) {
                        pair.first.close();
                    }
                }
                this.mCaptureStageMap.clear();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyCaptureFailed$0() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, final long j, long j2) {
            boolean z;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                z = true;
                if (CameraExtensionSessionImpl.this.mImageProcessor != null && this.mImageCallback == null) {
                    this.mImageCallback = new ImageCallback();
                } else if (CameraExtensionSessionImpl.this.mImageProcessor != null) {
                    z = false;
                }
            }
            if (z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureStarted$1(j);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            CameraOutputImageCallback cameraOutputImageCallback = this.mBurstImageCallback;
            if (cameraOutputImageCallback == null || this.mImageCallback == null) {
                return;
            }
            cameraOutputImageCallback.registerListener(Long.valueOf(j), this.mImageCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$1(long j) {
            this.mCallbacks.onCaptureStarted(CameraExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long j) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureSequenceAborted$2(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$2(int i) {
            this.mCallbacks.onCaptureSequenceAborted(CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, final int i, long j) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureSequenceCompleted$3(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$3(int i) {
            this.mCallbacks.onCaptureSequenceCompleted(CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            if (!this.mCaptureRequestMap.containsKey(captureRequest)) {
                Log.e(CameraExtensionSessionImpl.TAG, "Unexpected still capture request received!");
                return;
            }
            Integer num = this.mCaptureRequestMap.get(captureRequest);
            Long l = (Long) totalCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (l != null) {
                if (CameraExtensionSessionImpl.this.mCaptureResultsSupported && this.mCaptureResultHandler == null) {
                    this.mCaptureResultHandler = CameraExtensionSessionImpl.this.new CaptureResultHandler(this.mClientRequest, this.mExecutor, this.mCallbacks, totalCaptureResult.getSequenceId());
                }
                if (CameraExtensionSessionImpl.this.mImageProcessor != null) {
                    if (this.mCapturePendingMap.indexOfKey(l.longValue()) >= 0) {
                        Image image = this.mCapturePendingMap.get(l.longValue()).first;
                        this.mCapturePendingMap.remove(l.longValue());
                        this.mCaptureStageMap.put(num, new Pair<>(image, totalCaptureResult));
                        checkAndFireBurstProcessing();
                        return;
                    }
                    this.mCapturePendingMap.put(l.longValue(), new Pair<>(null, num));
                    this.mCaptureStageMap.put(num, new Pair<>(null, totalCaptureResult));
                    return;
                }
                this.mCaptureRequestMap.clear();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$onCaptureCompleted$4();
                        }
                    });
                    CaptureResultHandler captureResultHandler = this.mCaptureResultHandler;
                    if (captureResultHandler != null) {
                        captureResultHandler.onCaptureCompleted(l.longValue(), CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                    }
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            Log.e(CameraExtensionSessionImpl.TAG, "Capture result without valid sensor timestamp!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkAndFireBurstProcessing() {
            boolean z;
            if (this.mCaptureRequestMap.size() == this.mCaptureStageMap.size()) {
                for (Pair<Image, TotalCaptureResult> pair : this.mCaptureStageMap.values()) {
                    if (pair.first == null || pair.second == null) {
                        return;
                    }
                }
                this.mCaptureRequestMap.clear();
                this.mCapturePendingMap.clear();
                Byte b = (Byte) this.mClientRequest.get(CaptureRequest.JPEG_QUALITY);
                List<CaptureBundle> initializeParcelable = CameraExtensionSessionImpl.initializeParcelable(this.mCaptureStageMap, (Integer) this.mClientRequest.get(CaptureRequest.JPEG_ORIENTATION), b);
                try {
                    CameraExtensionSessionImpl.this.mImageProcessor.process(initializeParcelable, this.mCaptureResultHandler, this.mClientRequest.containsTarget(CameraExtensionSessionImpl.this.mClientPostviewSurface));
                    z = true;
                } catch (RemoteException unused) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to process multi-frame request! Extension service does not respond!");
                    z = false;
                }
                Iterator<CaptureBundle> it = initializeParcelable.iterator();
                while (it.hasNext()) {
                    it.next().captureImage.buffer.close();
                }
                initializeParcelable.clear();
                Iterator<Pair<Image, TotalCaptureResult>> it2 = this.mCaptureStageMap.values().iterator();
                while (it2.hasNext()) {
                    it2.next().first.close();
                }
                this.mCaptureStageMap.clear();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (z) {
                        this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$checkAndFireBurstProcessing$5();
                            }
                        });
                    } else {
                        this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.BurstRequestHandler.this.lambda$checkAndFireBurstProcessing$6();
                            }
                        });
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$5() {
            this.mCallbacks.onCaptureProcessStarted(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$6() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private class ImageCallback implements OnImageAvailableListener {
            private ImageCallback() {
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                BurstRequestHandler.this.notifyCaptureFailed();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader imageReader, Image image) {
                if (BurstRequestHandler.this.mCaptureFailed) {
                    image.close();
                }
                long timestamp = image.getTimestamp();
                imageReader.detachImage(image);
                if (BurstRequestHandler.this.mCapturePendingMap.indexOfKey(timestamp) >= 0) {
                    Integer num = (Integer) ((Pair) BurstRequestHandler.this.mCapturePendingMap.get(timestamp)).second;
                    BurstRequestHandler.this.mCapturePendingMap.remove(timestamp);
                    Pair pair = (Pair) BurstRequestHandler.this.mCaptureStageMap.get(num);
                    if (pair != null) {
                        BurstRequestHandler.this.mCaptureStageMap.put(num, new Pair(image, (TotalCaptureResult) pair.second));
                        BurstRequestHandler.this.checkAndFireBurstProcessing();
                        return;
                    } else {
                        Log.e(CameraExtensionSessionImpl.TAG, "Capture stage: " + ((Pair) BurstRequestHandler.this.mCapturePendingMap.get(timestamp)).second + " is absent!");
                        return;
                    }
                }
                BurstRequestHandler.this.mCapturePendingMap.put(timestamp, new Pair(image, -1));
            }
        }
    }

    private class ImageLoopbackCallback implements OnImageAvailableListener {
        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageDropped(long j) {
        }

        private ImageLoopbackCallback(CameraExtensionSessionImpl cameraExtensionSessionImpl) {
        }

        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader, Image image) {
            image.close();
        }
    }

    private class InitialRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraOutputImageCallback mImageCallback;

        public InitialRequestHandler(CameraOutputImageCallback cameraOutputImageCallback) {
            this.mImageCallback = cameraOutputImageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            this.mImageCallback.registerListener(Long.valueOf(j), new ImageLoopbackCallback());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
            Log.e(CameraExtensionSessionImpl.TAG, "Initial capture request aborted!");
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            Log.e(CameraExtensionSessionImpl.TAG, "Initial capture request failed!");
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
            boolean z;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    CameraExtensionSessionImpl cameraExtensionSessionImpl = CameraExtensionSessionImpl.this;
                    cameraExtensionSessionImpl.setRepeatingRequest(cameraExtensionSessionImpl.mPreviewExtender.getCaptureStage(), new PreviewRequestHandler(CameraExtensionSessionImpl.this, null, null, null, this.mImageCallback));
                    z = true;
                } catch (CameraAccessException | RemoteException unused) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to start the internal repeating request!");
                    z = false;
                }
            }
            if (z) {
                return;
            }
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }
    }

    private class CameraOutputImageCallback implements ImageReader.OnImageAvailableListener, Closeable {
        private final ImageReader mImageReader;
        private final boolean mPruneOlderBuffers;
        private HashMap<Long, Pair<Image, OnImageAvailableListener>> mImageListenerMap = new HashMap<>();
        private boolean mOutOfBuffers = false;

        CameraOutputImageCallback(ImageReader imageReader, boolean z) {
            this.mImageReader = imageReader;
            this.mPruneOlderBuffers = z;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    try {
                        Image acquireNextImage = imageReader.acquireNextImage();
                        if (acquireNextImage == null) {
                            Log.e(CameraExtensionSessionImpl.TAG, "Invalid image!");
                            return;
                        }
                        long timestamp = acquireNextImage.getTimestamp();
                        Long valueOf = Long.valueOf(timestamp);
                        if (this.mImageListenerMap.containsKey(valueOf)) {
                            Pair<Image, OnImageAvailableListener> remove = this.mImageListenerMap.remove(valueOf);
                            if (remove.second != null) {
                                remove.second.onImageAvailable(imageReader, acquireNextImage);
                            } else {
                                Log.w(CameraExtensionSessionImpl.TAG, "Invalid image listener, dropping frame!");
                                acquireNextImage.close();
                            }
                        } else {
                            this.mImageListenerMap.put(valueOf, new Pair<>(acquireNextImage, null));
                        }
                        valueOf.getClass();
                        notifyDroppedImages(timestamp);
                    } catch (IllegalStateException unused) {
                        Log.e(CameraExtensionSessionImpl.TAG, "Failed to acquire image, too many images pending!");
                        this.mOutOfBuffers = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private void notifyDroppedImages(long j) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                Set<Long> keySet = this.mImageListenerMap.keySet();
                ArrayList arrayList = new ArrayList();
                for (Long l : keySet) {
                    long longValue = l.longValue();
                    if (longValue < j) {
                        if (!this.mPruneOlderBuffers) {
                            Log.w(CameraExtensionSessionImpl.TAG, "Unexpected older image with ts: " + longValue);
                        } else {
                            Log.e(CameraExtensionSessionImpl.TAG, "Dropped image with ts: " + longValue);
                            Pair<Image, OnImageAvailableListener> pair = this.mImageListenerMap.get(l);
                            if (pair.second != null) {
                                pair.second.onImageDropped(longValue);
                            }
                            if (pair.first != null) {
                                pair.first.close();
                            }
                            arrayList.add(l);
                        }
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Long l2 = (Long) it.next();
                    l2.longValue();
                    this.mImageListenerMap.remove(l2);
                }
            }
        }

        public void registerListener(Long l, OnImageAvailableListener onImageAvailableListener) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageListenerMap.containsKey(l)) {
                    Pair<Image, OnImageAvailableListener> remove = this.mImageListenerMap.remove(l);
                    if (remove.first != null) {
                        onImageAvailableListener.onImageAvailable(this.mImageReader, remove.first);
                        if (this.mOutOfBuffers) {
                            this.mOutOfBuffers = false;
                            Log.w(CameraExtensionSessionImpl.TAG, "Out of buffers, retry!");
                            onImageAvailable(this.mImageReader);
                        }
                    } else {
                        Log.w(CameraExtensionSessionImpl.TAG, "No valid image for listener with ts: " + l.longValue());
                    }
                } else {
                    this.mImageListenerMap.put(l, new Pair<>(null, onImageAvailableListener));
                }
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                for (Pair<Image, OnImageAvailableListener> pair : this.mImageListenerMap.values()) {
                    if (pair.first != null) {
                        pair.first.close();
                    }
                }
                for (Long l : this.mImageListenerMap.keySet()) {
                    long longValue = l.longValue();
                    Pair<Image, OnImageAvailableListener> pair2 = this.mImageListenerMap.get(l);
                    if (pair2.second != null) {
                        pair2.second.onImageDropped(longValue);
                    }
                }
                this.mImageListenerMap.clear();
            }
        }
    }

    private class CloseRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraOutputImageCallback mImageCallback;

        public CloseRequestHandler(CameraOutputImageCallback cameraOutputImageCallback) {
            this.mImageCallback = cameraOutputImageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            this.mImageCallback.registerListener(Long.valueOf(j), new ImageLoopbackCallback());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CaptureResultHandler extends IProcessResultImpl.Stub {
        private final CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private final CaptureRequest mClientRequest;
        private final Executor mExecutor;
        private final int mRequestId;

        public CaptureResultHandler(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, int i) {
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            this.mRequestId = i;
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureCompleted(long j, CameraMetadataNative cameraMetadataNative) {
            if (cameraMetadataNative == null) {
                Log.e(CameraExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(j));
            final TotalCaptureResult totalCaptureResult = new TotalCaptureResult(CameraExtensionSessionImpl.this.mCameraDevice.getId(), cameraMetadataNative, this.mClientRequest, this.mRequestId, j, new ArrayList(), CameraExtensionSessionImpl.this.mSessionId, new PhysicalCaptureResultInfo[0]);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.CaptureResultHandler.this.lambda$onCaptureCompleted$0(totalCaptureResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$0(TotalCaptureResult totalCaptureResult) {
            this.mCallbacks.onCaptureResultAvailable(CameraExtensionSessionImpl.this, this.mClientRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureProcessProgressed(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraExtensionSessionImpl.CaptureResultHandler.this.lambda$onCaptureProcessProgressed$1(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$1(int i) {
            this.mCallbacks.onCaptureProcessProgressed(CameraExtensionSessionImpl.this, this.mClientRequest, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PreviewRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private CaptureResultHandler mCaptureResultHandler;
        private final boolean mClientNotificationsEnabled;
        private final CaptureRequest mClientRequest;
        private final Executor mExecutor;
        private OnImageAvailableListener mImageCallback;
        private LongSparseArray<Pair<Image, TotalCaptureResult>> mPendingResultMap;
        private final CameraOutputImageCallback mRepeatingImageCallback;
        private boolean mRequestUpdatedNeeded;
        private final boolean mSingleCapture;

        public PreviewRequestHandler(CameraExtensionSessionImpl cameraExtensionSessionImpl, CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, CameraOutputImageCallback cameraOutputImageCallback) {
            this(captureRequest, executor, extensionCaptureCallback, cameraOutputImageCallback, false);
        }

        public PreviewRequestHandler(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, CameraOutputImageCallback cameraOutputImageCallback, boolean z) {
            this.mImageCallback = null;
            this.mPendingResultMap = new LongSparseArray<>();
            this.mCaptureResultHandler = null;
            boolean z2 = false;
            this.mRequestUpdatedNeeded = false;
            this.mClientRequest = captureRequest;
            this.mExecutor = executor;
            this.mCallbacks = extensionCaptureCallback;
            if (captureRequest != null && executor != null && extensionCaptureCallback != null) {
                z2 = true;
            }
            this.mClientNotificationsEnabled = z2;
            this.mRepeatingImageCallback = cameraOutputImageCallback;
            this.mSingleCapture = z;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, final long j, long j2) {
            OnImageAvailableListener imageLoopbackCallback;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageCallback == null) {
                    if (CameraExtensionSessionImpl.this.mPreviewProcessorType == 1) {
                        if (this.mClientNotificationsEnabled) {
                            CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(CameraExtensionSessionImpl.this.mClientRepeatingRequestSurface, CameraExtensionSessionImpl.nativeGetSurfaceFormat(CameraExtensionSessionImpl.this.mClientRepeatingRequestSurface));
                        } else {
                            CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(null, -1);
                        }
                        this.mImageCallback = new ImageProcessCallback();
                    } else {
                        if (this.mClientNotificationsEnabled) {
                            imageLoopbackCallback = new ImageForwardCallback(CameraExtensionSessionImpl.this.mRepeatingRequestImageWriter);
                        } else {
                            imageLoopbackCallback = new ImageLoopbackCallback();
                        }
                        this.mImageCallback = imageLoopbackCallback;
                    }
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureStarted$0(j);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            this.mRepeatingImageCallback.registerListener(Long.valueOf(j), this.mImageCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long j) {
            this.mCallbacks.onCaptureStarted(CameraExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, final int i) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureSequenceAborted$1(i);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$1(int i) {
            this.mCallbacks.onCaptureSequenceAborted(CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, final int i, long j) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mRequestUpdatedNeeded && !this.mSingleCapture) {
                    this.mRequestUpdatedNeeded = false;
                    resumeInternalRepeatingRequest(false);
                } else if (CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureSequenceCompleted$2(i);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$2(int i) {
            this.mCallbacks.onCaptureSequenceCompleted(CameraExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            if (this.mClientNotificationsEnabled) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$onCaptureFailed$3();
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x016e A[Catch: all -> 0x01c6, TryCatch #4 {, blocks: (B:4:0x0007, B:6:0x0012, B:8:0x001a, B:10:0x001e, B:12:0x0022, B:13:0x0035, B:15:0x003c, B:17:0x0044, B:20:0x004c, B:48:0x0068, B:22:0x007a, B:50:0x0071, B:25:0x016e, B:27:0x0176, B:35:0x01ac, B:39:0x01b1, B:40:0x01b5, B:42:0x01bd, B:53:0x005f, B:54:0x007e, B:56:0x0086, B:58:0x008e, B:60:0x009a, B:62:0x00aa, B:64:0x00ae, B:65:0x00bb, B:67:0x00c4, B:70:0x00e3, B:72:0x013d, B:76:0x0143, B:77:0x015b, B:82:0x0107, B:80:0x0139, B:79:0x0124, B:83:0x015c, B:84:0x01b6, B:30:0x017c, B:32:0x018a, B:34:0x0192, B:37:0x01a2, B:69:0x00d8, B:81:0x0100, B:78:0x011d), top: B:3:0x0007, inners: #0, #3, #6, #8, #7 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x017c A[Catch: all -> 0x01b0, TRY_ENTER, TryCatch #0 {all -> 0x01b0, blocks: (B:30:0x017c, B:32:0x018a, B:34:0x0192, B:37:0x01a2), top: B:28:0x017a, outer: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x01a2 A[Catch: all -> 0x01b0, TRY_LEAVE, TryCatch #0 {all -> 0x01b0, blocks: (B:30:0x017c, B:32:0x018a, B:34:0x0192, B:37:0x01a2), top: B:28:0x017a, outer: #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x01c0  */
        /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession r10, android.hardware.camera2.CaptureRequest r11, android.hardware.camera2.TotalCaptureResult r12) {
            /*
                Method dump skipped, instructions count: 458
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.onCaptureCompleted(android.hardware.camera2.CameraCaptureSession, android.hardware.camera2.CaptureRequest, android.hardware.camera2.TotalCaptureResult):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$5() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private void resumeInternalRepeatingRequest(boolean z) {
            try {
                if (z) {
                    CameraExtensionSessionImpl cameraExtensionSessionImpl = CameraExtensionSessionImpl.this;
                    cameraExtensionSessionImpl.setRepeatingRequest(cameraExtensionSessionImpl.mPreviewExtender.getCaptureStage(), new PreviewRequestHandler(CameraExtensionSessionImpl.this, null, null, null, this.mRepeatingImageCallback));
                } else {
                    CameraExtensionSessionImpl cameraExtensionSessionImpl2 = CameraExtensionSessionImpl.this;
                    cameraExtensionSessionImpl2.setRepeatingRequest(cameraExtensionSessionImpl2.mPreviewExtender.getCaptureStage(), this, this.mClientRequest);
                }
            } catch (CameraAccessException unused) {
                Log.e(CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request!");
            } catch (RemoteException unused2) {
                Log.e(CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request, extension service fails to respond!");
            } catch (IllegalStateException unused3) {
                Log.w(CameraExtensionSessionImpl.TAG, "Failed to resume internal repeating request!");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Long calculatePruneThreshold(LongSparseArray<Pair<Image, TotalCaptureResult>> longSparseArray) {
            long j = Long.MAX_VALUE;
            for (int i = 0; i < longSparseArray.size(); i++) {
                Pair<Image, TotalCaptureResult> valueAt = longSparseArray.valueAt(i);
                long keyAt = longSparseArray.keyAt(i);
                if (valueAt.first != null && keyAt < j) {
                    j = keyAt;
                }
            }
            if (j == Long.MAX_VALUE) {
                j = 0;
            }
            return Long.valueOf(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void discardPendingRepeatingResults(int i, LongSparseArray<Pair<Image, TotalCaptureResult>> longSparseArray, boolean z) {
            if (i < 0) {
                return;
            }
            for (int i2 = i; i2 >= 0; i2--) {
                if (longSparseArray.valueAt(i2).first != null) {
                    longSparseArray.valueAt(i2).first.close();
                } else if (this.mClientNotificationsEnabled && longSparseArray.valueAt(i2).second != null && (i2 != i || z)) {
                    TotalCaptureResult totalCaptureResult = longSparseArray.valueAt(i2).second;
                    Long l = (Long) totalCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
                    CaptureResultHandler captureResultHandler = this.mCaptureResultHandler;
                    if (captureResultHandler != null) {
                        captureResultHandler.onCaptureCompleted(l.longValue(), CameraExtensionSessionImpl.this.initializeFilteredResults(totalCaptureResult));
                    }
                    Log.w(CameraExtensionSessionImpl.TAG, "Preview frame drop with timestamp: " + longSparseArray.keyAt(i2));
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.PreviewRequestHandler.this.lambda$discardPendingRepeatingResults$6();
                            }
                        });
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                longSparseArray.removeAt(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$discardPendingRepeatingResults$6() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private class ImageForwardCallback implements OnImageAvailableListener {
            private final ImageWriter mOutputWriter;

            public ImageForwardCallback(ImageWriter imageWriter) {
                this.mOutputWriter = imageWriter;
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                PreviewRequestHandler previewRequestHandler = PreviewRequestHandler.this;
                previewRequestHandler.discardPendingRepeatingResults(previewRequestHandler.mPendingResultMap.indexOfKey(j), PreviewRequestHandler.this.mPendingResultMap, true);
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader imageReader, Image image) {
                if (image == null) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    this.mOutputWriter.queueInputImage(image);
                } catch (IllegalStateException unused) {
                    Log.w(CameraExtensionSessionImpl.TAG, "Output surface likely abandoned, dropping buffer!");
                    image.close();
                } catch (RuntimeException e) {
                    if (!e.getClass().equals(RuntimeException.class)) {
                        throw e;
                    }
                    Log.w(CameraExtensionSessionImpl.TAG, "Output surface likely abandoned, dropping buffer!");
                    image.close();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ImageProcessCallback implements OnImageAvailableListener {
            private ImageProcessCallback() {
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long j) {
                PreviewRequestHandler previewRequestHandler = PreviewRequestHandler.this;
                previewRequestHandler.discardPendingRepeatingResults(previewRequestHandler.mPendingResultMap.indexOfKey(j), PreviewRequestHandler.this.mPendingResultMap, true);
                PreviewRequestHandler.this.mPendingResultMap.put(j, new Pair(null, null));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader imageReader, Image image) {
                boolean z = true;
                if (PreviewRequestHandler.this.mPendingResultMap.size() + 1 >= 10) {
                    PreviewRequestHandler previewRequestHandler = PreviewRequestHandler.this;
                    LongSparseArray longSparseArray = previewRequestHandler.mPendingResultMap;
                    PreviewRequestHandler previewRequestHandler2 = PreviewRequestHandler.this;
                    previewRequestHandler.discardPendingRepeatingResults(longSparseArray.indexOfKey(previewRequestHandler2.calculatePruneThreshold(previewRequestHandler2.mPendingResultMap).longValue()), PreviewRequestHandler.this.mPendingResultMap, true);
                }
                if (image == null) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Invalid preview buffer!");
                    return;
                }
                try {
                    imageReader.detachImage(image);
                    long timestamp = image.getTimestamp();
                    int indexOfKey = PreviewRequestHandler.this.mPendingResultMap.indexOfKey(timestamp);
                    if (indexOfKey >= 0) {
                        ParcelImage initializeParcelImage = CameraExtensionSessionImpl.initializeParcelImage(image);
                        try {
                            try {
                                CameraExtensionSessionImpl.this.mPreviewImageProcessor.process(initializeParcelImage, (TotalCaptureResult) ((Pair) PreviewRequestHandler.this.mPendingResultMap.get(timestamp)).second, PreviewRequestHandler.this.mCaptureResultHandler);
                            } finally {
                                initializeParcelImage.buffer.close();
                                image.close();
                            }
                        } catch (RemoteException unused) {
                            Log.e(CameraExtensionSessionImpl.TAG, "Extension service does not respond during processing, dropping frame!");
                            initializeParcelImage.buffer.close();
                            image.close();
                            z = false;
                        }
                        PreviewRequestHandler previewRequestHandler3 = PreviewRequestHandler.this;
                        previewRequestHandler3.discardPendingRepeatingResults(indexOfKey, previewRequestHandler3.mPendingResultMap, false);
                        if (PreviewRequestHandler.this.mClientNotificationsEnabled) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                if (z) {
                                    PreviewRequestHandler.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            CameraExtensionSessionImpl.PreviewRequestHandler.ImageProcessCallback.this.lambda$onImageAvailable$0();
                                        }
                                    });
                                } else {
                                    PreviewRequestHandler.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            CameraExtensionSessionImpl.PreviewRequestHandler.ImageProcessCallback.this.lambda$onImageAvailable$1();
                                        }
                                    });
                                }
                                return;
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        return;
                    }
                    PreviewRequestHandler.this.mPendingResultMap.put(timestamp, new Pair(image, null));
                } catch (IllegalStateException unused2) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    image.close();
                } catch (RuntimeException e) {
                    if (!e.getClass().equals(RuntimeException.class)) {
                        throw e;
                    }
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    image.close();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$0() {
                PreviewRequestHandler.this.mCallbacks.onCaptureProcessStarted(CameraExtensionSessionImpl.this, PreviewRequestHandler.this.mClientRequest);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$1() {
                PreviewRequestHandler.this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, PreviewRequestHandler.this.mClientRequest);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CameraMetadataNative initializeFilteredResults(TotalCaptureResult totalCaptureResult) {
        CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
        for (CaptureResult.Key key : this.mSupportedResultKeys) {
            Object obj = totalCaptureResult.get(key);
            if (obj != null) {
                cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key>) key, (CaptureResult.Key) obj);
            }
        }
        return cameraMetadataNative;
    }

    private static Size findSmallestAspectMatchedSize(List<Size> list, Size size) {
        if (size.getHeight() == 0) {
            throw new IllegalArgumentException("Invalid input aspect ratio");
        }
        float width = size.getWidth() / size.getHeight();
        Size size2 = null;
        Size size3 = null;
        for (Size size4 : list) {
            if (size3 == null) {
                size3 = size4;
            }
            if (size4.getHeight() > 0 && (size2 == null || size2.getWidth() * size2.getHeight() < size4.getWidth() * size4.getHeight())) {
                if (Math.abs((size4.getWidth() / size4.getHeight()) - width) <= 0.01f) {
                    size2 = size4;
                }
            }
        }
        if (size2 != null) {
            return size2;
        }
        Log.e(TAG, "AR matched size not found returning first size in list");
        return size3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelImage initializeParcelImage(Image image) {
        ParcelImage parcelImage = new ParcelImage();
        parcelImage.buffer = image.getHardwareBuffer();
        try {
            SyncFence fence = image.getFence();
            if (fence.isValid()) {
                parcelImage.fence = fence.getFdDup();
            }
        } catch (IOException unused) {
            Log.e(TAG, "Failed to parcel buffer fence!");
        }
        parcelImage.width = image.getWidth();
        parcelImage.height = image.getHeight();
        parcelImage.format = image.getFormat();
        parcelImage.timestamp = image.getTimestamp();
        parcelImage.transform = image.getTransform();
        parcelImage.scalingMode = image.getScalingMode();
        parcelImage.planeCount = image.getPlaneCount();
        parcelImage.crop = image.getCropRect();
        return parcelImage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<CaptureBundle> initializeParcelable(HashMap<Integer, Pair<Image, TotalCaptureResult>> hashMap, Integer num, Byte b) {
        ArrayList arrayList = new ArrayList();
        for (Integer num2 : hashMap.keySet()) {
            Pair<Image, TotalCaptureResult> pair = hashMap.get(num2);
            CaptureBundle captureBundle = new CaptureBundle();
            captureBundle.stage = num2.intValue();
            captureBundle.captureImage = initializeParcelImage(pair.first);
            captureBundle.sequenceId = pair.second.getSequenceId();
            captureBundle.captureResult = pair.second.getNativeMetadata();
            if (num != null) {
                captureBundle.captureResult.set((CaptureResult.Key<CaptureResult.Key<Integer>>) CaptureResult.JPEG_ORIENTATION, (CaptureResult.Key<Integer>) num);
            }
            if (b != null) {
                captureBundle.captureResult.set((CaptureResult.Key<CaptureResult.Key<Byte>>) CaptureResult.JPEG_QUALITY, (CaptureResult.Key<Byte>) b);
            }
            arrayList.add(captureBundle);
        }
        return arrayList;
    }
}
