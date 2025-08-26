package android.hardware.camera2.impl;

import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.SurfaceTexture;
import android.hardware.SyncFence;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraExtensionSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.CameraOutputConfig;
import android.hardware.camera2.extension.CameraSessionConfig;
import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ICaptureCallback;
import android.hardware.camera2.extension.IImageProcessorImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IRequestCallback;
import android.hardware.camera2.extension.IRequestProcessorImpl;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.extension.LatencyPair;
import android.hardware.camera2.extension.OutputConfigId;
import android.hardware.camera2.extension.OutputSurface;
import android.hardware.camera2.extension.ParcelCaptureResult;
import android.hardware.camera2.extension.ParcelImage;
import android.hardware.camera2.extension.ParcelTotalCaptureResult;
import android.hardware.camera2.extension.Request;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.hardware.camera2.params.ExtensionSessionConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.ExtensionSessionStatsAggregator;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.media.ImageReader;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import com.android.internal.camera.flags.Flags;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class CameraAdvancedExtensionSessionImpl extends CameraExtensionSession {
    private static final String TAG = "CameraAdvancedExtensionSessionImpl";
    private IAdvancedExtenderImpl mAdvancedExtender;
    private final CameraExtensionSession.StateCallback mCallbacks;
    private CameraDevice mCameraDevice;
    private final Map<String, CameraMetadataNative> mCharacteristicsMap;
    private OutputConfiguration mClientCaptureOutputConfig;
    private Surface mClientCaptureSurface;
    private OutputConfiguration mClientPostviewOutputConfig;
    private Surface mClientPostviewSurface;
    private OutputConfiguration mClientRepeatingRequestOutputConfig;
    private Surface mClientRepeatingRequestSurface;
    private final Context mContext;
    private final Executor mExecutor;
    private int mExtensionType;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final InitializeSessionHandler mInitializeHandler;
    private boolean mInitialized;
    final Object mInterfaceLock;
    private RequestProcessor mRequestProcessor;
    private boolean mSessionClosed;
    private final int mSessionId;
    private final ExtensionSessionStatsAggregator mStatsAggregator;
    private IBinder mToken;
    private final HashMap<Surface, CameraOutputConfig> mCameraConfigMap = new HashMap<>();
    private final HashMap<Integer, ImageReader> mReaderMap = new HashMap<>();
    private CameraCaptureSession mCaptureSession = null;
    private ISessionProcessorImpl mSessionProcessor = null;

    /* JADX WARN: Removed duplicated region for block: B:56:0x0167 A[PHI: r2 r4
      0x0167: PHI (r2v20 int) = (r2v19 int), (r2v29 int) binds: [B:46:0x0139, B:54:0x015d] A[DONT_GENERATE, DONT_INLINE]
      0x0167: PHI (r4v6 android.hardware.camera2.params.OutputConfiguration) = (r4v5 android.hardware.camera2.params.OutputConfiguration), (r4v9 android.hardware.camera2.params.OutputConfiguration) binds: [B:46:0x0139, B:54:0x015d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CameraAdvancedExtensionSessionImpl createCameraAdvancedExtensionSession(CameraDeviceImpl cameraDeviceImpl, Map<String, CameraCharacteristics> map, Context context, ExtensionSessionConfiguration extensionSessionConfiguration, int i, IBinder iBinder) throws CameraAccessException, RemoteException {
        OutputConfiguration outputConfiguration;
        int i2;
        int i3;
        OutputConfiguration outputConfiguration2;
        String id = cameraDeviceImpl.getId();
        CameraExtensionCharacteristics cameraExtensionCharacteristics = new CameraExtensionCharacteristics(context, id, map);
        Map<String, CameraMetadataNative> characteristicsMapNative = CameraExtensionUtils.getCharacteristicsMapNative(map);
        if (!CameraExtensionCharacteristics.isExtensionSupported(cameraDeviceImpl.getId(), extensionSessionConfiguration.getExtension(), characteristicsMapNative)) {
            throw new UnsupportedOperationException("Unsupported extension type: " + extensionSessionConfiguration.getExtension());
        }
        if (extensionSessionConfiguration.getOutputConfigurations().isEmpty() || extensionSessionConfiguration.getOutputConfigurations().size() > 2) {
            throw new IllegalArgumentException("Unexpected amount of output surfaces, received: " + extensionSessionConfiguration.getOutputConfigurations().size() + " expected <= 2");
        }
        for (OutputConfiguration outputConfiguration3 : extensionSessionConfiguration.getOutputConfigurations()) {
            if (outputConfiguration3.getDynamicRangeProfile() != 1) {
                if (Flags.cameraExtensionsCharacteristicsGet()) {
                    DynamicRangeProfiles dynamicRangeProfiles = (DynamicRangeProfiles) cameraExtensionCharacteristics.get(extensionSessionConfiguration.getExtension(), CameraCharacteristics.REQUEST_AVAILABLE_DYNAMIC_RANGE_PROFILES);
                    if (dynamicRangeProfiles == null || !dynamicRangeProfiles.getSupportedProfiles().contains(Long.valueOf(outputConfiguration3.getDynamicRangeProfile()))) {
                        throw new IllegalArgumentException("Unsupported dynamic range profile: " + outputConfiguration3.getDynamicRangeProfile());
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported dynamic range profile: " + outputConfiguration3.getDynamicRangeProfile());
                }
            }
            if (outputConfiguration3.getStreamUseCase() != 0) {
                throw new IllegalArgumentException("Unsupported stream use case: " + outputConfiguration3.getStreamUseCase());
            }
        }
        Surface repeatingRequestSurface = CameraExtensionUtils.getRepeatingRequestSurface(extensionSessionConfiguration.getOutputConfigurations(), cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), SurfaceTexture.class));
        OutputConfiguration outputConfiguration4 = null;
        int i4 = 0;
        if (repeatingRequestSurface != null) {
            OutputConfiguration outputConfiguration5 = null;
            for (OutputConfiguration outputConfiguration6 : extensionSessionConfiguration.getOutputConfigurations()) {
                if (outputConfiguration6.getSurface() == repeatingRequestSurface) {
                    outputConfiguration5 = outputConfiguration6;
                }
            }
            i2 = 1;
            outputConfiguration = outputConfiguration5;
        } else {
            outputConfiguration = null;
            i2 = 0;
        }
        HashMap map2 = new HashMap();
        Integer[] numArr = (Integer[]) CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS.toArray(new Integer[CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS.size()]);
        for (Integer num : numArr) {
            List<Size> extensionSupportedSizes = cameraExtensionCharacteristics.getExtensionSupportedSizes(extensionSessionConfiguration.getExtension(), num.intValue());
            if (extensionSupportedSizes != null) {
                map2.put(num, extensionSupportedSizes);
            }
        }
        Surface burstCaptureSurface = CameraExtensionUtils.getBurstCaptureSurface(extensionSessionConfiguration.getOutputConfigurations(), map2);
        if (burstCaptureSurface != null) {
            for (OutputConfiguration outputConfiguration7 : extensionSessionConfiguration.getOutputConfigurations()) {
                if (outputConfiguration7.getSurface() == burstCaptureSurface) {
                    outputConfiguration4 = outputConfiguration7;
                }
            }
            i2++;
            i3 = Flags.analytics24q3() ? CameraExtensionUtils.querySurface(burstCaptureSurface).mFormat : 0;
        }
        if (i2 != extensionSessionConfiguration.getOutputConfigurations().size()) {
            throw new IllegalArgumentException("One or more unsupported output surfaces found!");
        }
        OutputConfiguration postviewOutputConfiguration = extensionSessionConfiguration.getPostviewOutputConfiguration();
        if (burstCaptureSurface == null || extensionSessionConfiguration.getPostviewOutputConfiguration() == null) {
            outputConfiguration2 = postviewOutputConfiguration;
        } else {
            CameraExtensionUtils.SurfaceInfo surfaceInfoQuerySurface = CameraExtensionUtils.querySurface(burstCaptureSurface);
            Size size = new Size(surfaceInfoQuerySurface.mWidth, surfaceInfoQuerySurface.mHeight);
            HashMap map3 = new HashMap();
            int length = numArr.length;
            while (i4 < length) {
                Integer num2 = numArr[i4];
                OutputConfiguration outputConfiguration8 = postviewOutputConfiguration;
                List<Size> postviewSupportedSizes = cameraExtensionCharacteristics.getPostviewSupportedSizes(extensionSessionConfiguration.getExtension(), size, num2.intValue());
                if (postviewSupportedSizes != null) {
                    map3.put(num2, postviewSupportedSizes);
                }
                i4++;
                postviewOutputConfiguration = outputConfiguration8;
            }
            outputConfiguration2 = postviewOutputConfiguration;
            if (CameraExtensionUtils.getPostviewSurface(extensionSessionConfiguration.getPostviewOutputConfiguration(), map3, surfaceInfoQuerySurface.mFormat) == null) {
                throw new IllegalArgumentException("Unsupported output surface for postview!");
            }
        }
        IAdvancedExtenderImpl iAdvancedExtenderImplInitializeAdvancedExtension = CameraExtensionCharacteristics.initializeAdvancedExtension(extensionSessionConfiguration.getExtension());
        iAdvancedExtenderImplInitializeAdvancedExtension.init(id, characteristicsMapNative);
        CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = new CameraAdvancedExtensionSessionImpl(context, iAdvancedExtenderImplInitializeAdvancedExtension, cameraDeviceImpl, characteristicsMapNative, outputConfiguration, outputConfiguration4, outputConfiguration2, extensionSessionConfiguration.getStateCallback(), extensionSessionConfiguration.getExecutor(), i, iBinder, extensionSessionConfiguration.getExtension());
        if (Flags.analytics24q3()) {
            cameraAdvancedExtensionSessionImpl.mStatsAggregator.setCaptureFormat(i3);
        }
        cameraAdvancedExtensionSessionImpl.mStatsAggregator.setClientName(context.getOpPackageName());
        cameraAdvancedExtensionSessionImpl.mStatsAggregator.setExtensionType(extensionSessionConfiguration.getExtension());
        cameraAdvancedExtensionSessionImpl.initialize();
        return cameraAdvancedExtensionSessionImpl;
    }

    private CameraAdvancedExtensionSessionImpl(Context context, IAdvancedExtenderImpl iAdvancedExtenderImpl, CameraDeviceImpl cameraDeviceImpl, Map<String, CameraMetadataNative> map, OutputConfiguration outputConfiguration, OutputConfiguration outputConfiguration2, OutputConfiguration outputConfiguration3, CameraExtensionSession.StateCallback stateCallback, Executor executor, int i, IBinder iBinder, int i2) {
        this.mRequestProcessor = new RequestProcessor();
        this.mToken = null;
        this.mContext = context;
        this.mAdvancedExtender = iAdvancedExtenderImpl;
        this.mCameraDevice = cameraDeviceImpl;
        this.mCharacteristicsMap = map;
        this.mCallbacks = stateCallback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestOutputConfig = outputConfiguration;
        this.mClientCaptureOutputConfig = outputConfiguration2;
        this.mClientPostviewOutputConfig = outputConfiguration3;
        if (outputConfiguration != null) {
            this.mClientRepeatingRequestSurface = outputConfiguration.getSurface();
        }
        if (outputConfiguration2 != null) {
            this.mClientCaptureSurface = outputConfiguration2.getSurface();
        }
        if (outputConfiguration3 != null) {
            this.mClientPostviewSurface = outputConfiguration3.getSurface();
        }
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new InitializeSessionHandler();
        this.mSessionId = i;
        this.mToken = iBinder;
        this.mInterfaceLock = cameraDeviceImpl.mInterfaceLock;
        this.mExtensionType = i2;
        this.mStatsAggregator = new ExtensionSessionStatsAggregator(this.mCameraDevice.getId(), true);
    }

    public synchronized void initialize() throws CameraAccessException, RemoteException {
        int i;
        if (this.mInitialized) {
            Log.d(TAG, "Session already initialized");
            return;
        }
        OutputSurface outputSurfaceInitializeParcelable = initializeParcelable(this.mClientRepeatingRequestOutputConfig);
        OutputSurface outputSurfaceInitializeParcelable2 = initializeParcelable(this.mClientCaptureOutputConfig);
        OutputSurface outputSurfaceInitializeParcelable3 = initializeParcelable(this.mClientPostviewOutputConfig);
        ISessionProcessorImpl sessionProcessor = this.mAdvancedExtender.getSessionProcessor();
        this.mSessionProcessor = sessionProcessor;
        CameraSessionConfig cameraSessionConfigInitSession = sessionProcessor.initSession(this.mToken, this.mCameraDevice.getId(), this.mCharacteristicsMap, outputSurfaceInitializeParcelable, outputSurfaceInitializeParcelable2, outputSurfaceInitializeParcelable3);
        List<CameraOutputConfig> list = cameraSessionConfigInitSession.outputConfigs;
        ArrayList arrayList = new ArrayList();
        Iterator<CameraOutputConfig> it = list.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            CameraOutputConfig next = it.next();
            Surface surfaceInitializeSurface = initializeSurface(next);
            if (surfaceInitializeSurface != null) {
                OutputConfiguration outputConfiguration = new OutputConfiguration(next.surfaceGroupId, surfaceInitializeSurface);
                if (next.isMultiResolutionOutput) {
                    outputConfiguration.setMultiResolutionOutput();
                }
                if (next.sharedSurfaceConfigs != null && !next.sharedSurfaceConfigs.isEmpty()) {
                    outputConfiguration.enableSurfaceSharing();
                    for (CameraOutputConfig cameraOutputConfig : next.sharedSurfaceConfigs) {
                        Surface surfaceInitializeSurface2 = initializeSurface(cameraOutputConfig);
                        if (surfaceInitializeSurface2 != null) {
                            outputConfiguration.addSurface(surfaceInitializeSurface2);
                            this.mCameraConfigMap.put(surfaceInitializeSurface2, cameraOutputConfig);
                        }
                    }
                }
                outputConfiguration.setTimestampBase(1);
                outputConfiguration.setReadoutTimestampEnabled(false);
                outputConfiguration.setPhysicalCameraId(next.physicalCameraId);
                long j = 1;
                while (true) {
                    if (j >= 4096) {
                        Log.e(TAG, "Extension configured dynamic range profile " + next.dynamicRangeProfile + " is not valid, using default DynamicRangeProfile.STANDARD");
                        break;
                    }
                    if (next.dynamicRangeProfile == j) {
                        outputConfiguration.setDynamicRangeProfile(next.dynamicRangeProfile);
                        break;
                    }
                    j <<= 1;
                }
                arrayList.add(outputConfiguration);
                this.mCameraConfigMap.put(outputConfiguration.getSurface(), next);
            }
        }
        if (cameraSessionConfigInitSession.sessionType != -1 && cameraSessionConfigInitSession.sessionType != 1) {
            i = cameraSessionConfigInitSession.sessionType;
            Log.v(TAG, "Using session type: " + i);
        }
        SessionConfiguration sessionConfiguration = new SessionConfiguration(i, arrayList, new CameraExtensionUtils.HandlerExecutor(this.mHandler), new SessionStateHandler());
        if (cameraSessionConfigInitSession.colorSpace >= 0 && cameraSessionConfigInitSession.colorSpace < ColorSpace.Named.values().length) {
            sessionConfiguration.setColorSpace(ColorSpace.Named.values()[cameraSessionConfigInitSession.colorSpace]);
        } else {
            Log.e(TAG, "Extension configured color space " + cameraSessionConfigInitSession.colorSpace + " is not valid, using default unspecified color space");
        }
        if (cameraSessionConfigInitSession.sessionParameter != null && !cameraSessionConfigInitSession.sessionParameter.isEmpty()) {
            CaptureRequest captureRequestBuild = this.mCameraDevice.createCaptureRequest(cameraSessionConfigInitSession.sessionTemplateId).build();
            CameraMetadataNative.update(captureRequestBuild.getNativeMetadata(), cameraSessionConfigInitSession.sessionParameter);
            sessionConfiguration.setSessionParameters(captureRequestBuild);
        }
        this.mCameraDevice.createCaptureSession(sessionConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelCaptureResult initializeParcelable(CaptureResult captureResult) {
        ParcelCaptureResult parcelCaptureResult = new ParcelCaptureResult();
        parcelCaptureResult.cameraId = captureResult.getCameraId();
        parcelCaptureResult.results = captureResult.getNativeMetadata();
        parcelCaptureResult.parent = captureResult.getRequest();
        parcelCaptureResult.sequenceId = captureResult.getSequenceId();
        parcelCaptureResult.frameNumber = captureResult.getFrameNumber();
        return parcelCaptureResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelTotalCaptureResult initializeParcelable(TotalCaptureResult totalCaptureResult) {
        ParcelTotalCaptureResult parcelTotalCaptureResult = new ParcelTotalCaptureResult();
        parcelTotalCaptureResult.logicalCameraId = totalCaptureResult.getCameraId();
        parcelTotalCaptureResult.results = totalCaptureResult.getNativeMetadata();
        parcelTotalCaptureResult.parent = totalCaptureResult.getRequest();
        parcelTotalCaptureResult.sequenceId = totalCaptureResult.getSequenceId();
        parcelTotalCaptureResult.frameNumber = totalCaptureResult.getFrameNumber();
        parcelTotalCaptureResult.sessionId = totalCaptureResult.getSessionId();
        parcelTotalCaptureResult.partials = new ArrayList(totalCaptureResult.getPartialResults().size());
        Iterator<CaptureResult> it = totalCaptureResult.getPartialResults().iterator();
        while (it.hasNext()) {
            parcelTotalCaptureResult.partials.add(initializeParcelable(it.next()));
        }
        Map<String, TotalCaptureResult> physicalCameraTotalResults = totalCaptureResult.getPhysicalCameraTotalResults();
        parcelTotalCaptureResult.physicalResult = new ArrayList(physicalCameraTotalResults.size());
        for (TotalCaptureResult totalCaptureResult2 : physicalCameraTotalResults.values()) {
            parcelTotalCaptureResult.physicalResult.add(new PhysicalCaptureResultInfo(totalCaptureResult2.getCameraId(), totalCaptureResult2.getNativeMetadata()));
        }
        return parcelTotalCaptureResult;
    }

    private static OutputSurface initializeParcelable(OutputConfiguration outputConfiguration) {
        OutputSurface outputSurface = new OutputSurface();
        if (outputConfiguration != null && outputConfiguration.getSurface() != null) {
            Surface surface = outputConfiguration.getSurface();
            outputSurface.surface = surface;
            outputSurface.size = new android.hardware.camera2.extension.Size();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
            outputSurface.size.width = surfaceSize.getWidth();
            outputSurface.size.height = surfaceSize.getHeight();
            outputSurface.imageFormat = SurfaceUtils.getSurfaceFormat(surface);
            outputSurface.dynamicRangeProfile = outputConfiguration.getDynamicRangeProfile();
            ColorSpace colorSpace = outputConfiguration.getColorSpace();
            if (colorSpace != null) {
                outputSurface.colorSpace = colorSpace.getId();
                return outputSurface;
            }
            outputSurface.colorSpace = -1;
            return outputSurface;
        }
        outputSurface.surface = null;
        outputSurface.size = new android.hardware.camera2.extension.Size();
        outputSurface.size.width = -1;
        outputSurface.size.height = -1;
        outputSurface.imageFormat = 0;
        outputSurface.dynamicRangeProfile = 1L;
        outputSurface.colorSpace = -1;
        return outputSurface;
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
                LatencyPair realtimeCaptureLatency = this.mSessionProcessor.getRealtimeCaptureLatency();
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
        int iStartRepeating;
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
            try {
                this.mSessionProcessor.setParameters(captureRequest);
                iStartRepeating = this.mSessionProcessor.startRepeating(new RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()));
            } catch (RemoteException unused) {
                throw new CameraAccessException(3, "Failed to enable repeating request, extension service failed to respond!");
            }
        }
        return iStartRepeating;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback) throws CameraAccessException {
        int iStartTrigger;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            validateCaptureRequestTargets(captureRequest);
            Surface surface = this.mClientCaptureSurface;
            if (surface != null && captureRequest.containsTarget(surface)) {
                try {
                    boolean zContainsTarget = captureRequest.containsTarget(this.mClientPostviewSurface);
                    this.mSessionProcessor.setParameters(captureRequest);
                    iStartTrigger = this.mSessionProcessor.startCapture(new RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()), zContainsTarget);
                } catch (RemoteException unused) {
                    throw new CameraAccessException(3, "Failed  to submit capture request, extension service failed to respond!");
                }
            } else {
                Surface surface2 = this.mClientRepeatingRequestSurface;
                if (surface2 != null && captureRequest.containsTarget(surface2)) {
                    try {
                        iStartTrigger = this.mSessionProcessor.startTrigger(captureRequest, new RequestCallbackHandler(captureRequest, executor, extensionCaptureCallback, this.mCameraDevice.getId()));
                    } catch (RemoteException unused2) {
                        throw new CameraAccessException(3, "Failed  to submit trigger request, extension service failed to respond!");
                    }
                } else {
                    throw new IllegalArgumentException("Invalid single capture output target!");
                }
            }
        }
        return iStartTrigger;
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
            this.mCaptureSession.stopRepeating();
            try {
                this.mSessionProcessor.stopRepeating();
            } catch (RemoteException unused) {
                throw new CameraAccessException(3, "Failed to notify about the end of repeating request, extension service failed to respond!");
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession, java.lang.AutoCloseable
    public void close() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                try {
                    try {
                        this.mCaptureSession.stopRepeating();
                    } catch (RemoteException unused) {
                        Log.e(TAG, "Failed to stop the repeating request or end the session, , extension service does not respond!");
                    }
                } catch (IllegalStateException unused2) {
                }
                this.mSessionProcessor.stopRepeating();
                this.mSessionProcessor.onCaptureSessionEnd();
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x002a A[Catch: all -> 0x0092, TryCatch #0 {, blocks: (B:4:0x0003, B:7:0x000d, B:9:0x0011, B:10:0x0014, B:13:0x0023, B:14:0x0025, B:16:0x002a, B:18:0x002e, B:23:0x003b, B:25:0x0046, B:26:0x0054, B:28:0x005a, B:29:0x0064, B:30:0x0075, B:22:0x0035, B:12:0x001c), top: B:43:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005a A[Catch: all -> 0x0092, LOOP:0: B:26:0x0054->B:28:0x005a, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0003, B:7:0x000d, B:9:0x0011, B:10:0x0014, B:13:0x0023, B:14:0x0025, B:16:0x002a, B:18:0x002e, B:23:0x003b, B:25:0x0046, B:26:0x0054, B:28:0x005a, B:29:0x0064, B:30:0x0075, B:22:0x0035, B:12:0x001c), top: B:43:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void release(boolean z) {
        boolean z2;
        Iterator<ImageReader> it;
        synchronized (this.mInterfaceLock) {
            this.mHandlerThread.quitSafely();
            ISessionProcessorImpl iSessionProcessorImpl = this.mSessionProcessor;
            if (iSessionProcessorImpl != null) {
                try {
                    if (!this.mSessionClosed) {
                        iSessionProcessorImpl.onCaptureSessionEnd();
                    }
                    this.mSessionProcessor.deInitSession(this.mToken);
                } catch (RemoteException unused) {
                    Log.e(TAG, "Failed to de-initialize session processor, extension service does not respond!");
                }
                this.mSessionProcessor = null;
                if (this.mToken == null) {
                    if (this.mInitialized || this.mCaptureSession != null) {
                        CameraExtensionCharacteristics.releaseSession(this.mExtensionType);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken, this.mExtensionType);
                } else {
                    z2 = false;
                }
                this.mInitialized = false;
                this.mToken = null;
                it = this.mReaderMap.values().iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.mReaderMap.clear();
                this.mClientRepeatingRequestSurface = null;
                this.mClientCaptureSurface = null;
                this.mCaptureSession = null;
                this.mRequestProcessor = null;
                this.mCameraDevice = null;
                this.mAdvancedExtender = null;
            } else {
                if (this.mToken == null) {
                }
                this.mInitialized = false;
                this.mToken = null;
                it = this.mReaderMap.values().iterator();
                while (it.hasNext()) {
                }
                this.mReaderMap.clear();
                this.mClientRepeatingRequestSurface = null;
                this.mClientCaptureSurface = null;
                this.mCaptureSession = null;
                this.mRequestProcessor = null;
                this.mCameraDevice = null;
                this.mAdvancedExtender = null;
            }
        }
        if (!z2 || z) {
            return;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$release$0();
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
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
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$notifyConfigurationFailure$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationFailure$1() {
        this.mCallbacks.onConfigureFailed(this);
    }

    private class SessionStateHandler extends CameraCaptureSession.StateCallback {
        private SessionStateHandler() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            CameraAdvancedExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                CameraAdvancedExtensionSessionImpl.this.mCaptureSession = cameraCaptureSession;
                CameraAdvancedExtensionSessionImpl.this.mStatsAggregator.commit(false);
            }
            try {
                CameraExtensionCharacteristics.initializeSession(CameraAdvancedExtensionSessionImpl.this.mInitializeHandler, CameraAdvancedExtensionSessionImpl.this.mExtensionType);
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize session! Extension service does not respond!");
                CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {
        }

        /* renamed from: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    z = false;
                    try {
                        if (CameraAdvancedExtensionSessionImpl.this.mSessionProcessor != null) {
                            CameraAdvancedExtensionSessionImpl.this.mInitialized = true;
                            CameraAdvancedExtensionSessionImpl.this.mSessionProcessor.onCaptureSessionStart(CameraAdvancedExtensionSessionImpl.this.mRequestProcessor, CameraAdvancedExtensionSessionImpl.this.mStatsAggregator.getStatsKey());
                            z = true;
                        } else {
                            Log.v(CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, session  released before extension start!");
                        }
                    } catch (RemoteException unused) {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, extension service does not respond!");
                        CameraAdvancedExtensionSessionImpl.this.mInitialized = z;
                    }
                }
                if (z) {
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$run$0();
                            }
                        });
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
                InitializeSessionHandler.this.onFailure();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0() {
                CameraAdvancedExtensionSessionImpl.this.mCallbacks.onConfigured(CameraAdvancedExtensionSessionImpl.this);
            }
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            CameraAdvancedExtensionSessionImpl.this.mHandler.post(new AnonymousClass1());
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            CameraAdvancedExtensionSessionImpl.this.mHandler.post(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    CameraAdvancedExtensionSessionImpl.this.mCaptureSession.close();
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize proxy service session! This can happen when trying to configure multiple concurrent extension sessions!");
                    CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class RequestCallbackHandler extends ICaptureCallback.Stub {
        private final String mCameraId;
        private final CameraExtensionSession.ExtensionCaptureCallback mClientCallbacks;
        private final Executor mClientExecutor;
        private final CaptureRequest mClientRequest;

        private RequestCallbackHandler(CaptureRequest captureRequest, Executor executor, CameraExtensionSession.ExtensionCaptureCallback extensionCaptureCallback, String str) {
            this.mClientRequest = captureRequest;
            this.mClientExecutor = executor;
            this.mClientCallbacks = extensionCaptureCallback;
            this.mCameraId = str;
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureStarted(int i, final long j) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureStarted$0(j);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long j) {
            this.mClientCallbacks.onCaptureStarted(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, j);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessStarted(int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureProcessStarted$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessStarted$1() {
            this.mClientCallbacks.onCaptureProcessStarted(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureFailed(int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureFailed$2();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$2() {
            this.mClientCallbacks.onCaptureFailed(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessFailed(int i, final int i2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureProcessFailed$3(i2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessFailed$3(int i) {
            this.mClientCallbacks.onCaptureFailed(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceCompleted(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureSequenceCompleted$4(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$4(int i) {
            this.mClientCallbacks.onCaptureSequenceCompleted(CameraAdvancedExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceAborted(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureSequenceAborted$5(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$5(int i) {
            this.mClientCallbacks.onCaptureSequenceAborted(CameraAdvancedExtensionSessionImpl.this, i);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureCompleted(long j, int i, CameraMetadataNative cameraMetadataNative) {
            if (cameraMetadataNative == null) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            cameraMetadataNative.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(j));
            final TotalCaptureResult totalCaptureResult = new TotalCaptureResult(this.mCameraId, cameraMetadataNative, this.mClientRequest, i, j, new ArrayList(), CameraAdvancedExtensionSessionImpl.this.mSessionId, new PhysicalCaptureResultInfo[0]);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureCompleted$6(totalCaptureResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$6(TotalCaptureResult totalCaptureResult) {
            this.mClientCallbacks.onCaptureResultAvailable(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessProgressed(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureProcessProgressed$7(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$7(int i) {
            this.mClientCallbacks.onCaptureProcessProgressed(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, i);
        }
    }

    private final class CaptureCallbackHandler extends CameraCaptureSession.CaptureCallback {
        private final IRequestCallback mCallback;

        public CaptureCallbackHandler(IRequestCallback iRequestCallback) {
            this.mCallback = iRequestCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long j) {
            try {
                if (!(captureRequest.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureBufferLost(((Integer) captureRequest.getTag()).intValue(), j, ((CameraOutputConfig) CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(surface)).outputId.id);
                }
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify lost capture buffer, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            try {
                if (!(captureRequest.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureCompleted(((Integer) captureRequest.getTag()).intValue(), CameraAdvancedExtensionSessionImpl.initializeParcelable(totalCaptureResult));
                }
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            try {
                if (!(captureRequest.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                    return;
                }
                Integer num = (Integer) captureRequest.getTag();
                android.hardware.camera2.extension.CaptureFailure captureFailure2 = new android.hardware.camera2.extension.CaptureFailure();
                captureFailure2.request = captureRequest;
                captureFailure2.reason = captureFailure.getReason();
                captureFailure2.errorPhysicalCameraId = captureFailure.getPhysicalCameraId();
                captureFailure2.frameNumber = captureFailure.getFrameNumber();
                captureFailure2.sequenceId = captureFailure.getSequenceId();
                captureFailure2.dropped = !captureFailure.wasImageCaptured();
                this.mCallback.onCaptureFailed(num.intValue(), captureFailure2);
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture failure, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
            try {
                if (!(captureRequest.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureProgressed(((Integer) captureRequest.getTag()).intValue(), CameraAdvancedExtensionSessionImpl.initializeParcelable(captureResult));
                }
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture partial result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
            try {
                this.mCallback.onCaptureSequenceAborted(i);
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify aborted sequence, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
            try {
                this.mCallback.onCaptureSequenceCompleted(i, j);
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify sequence complete, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            try {
                if (!(captureRequest.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    this.mCallback.onCaptureStarted(((Integer) captureRequest.getTag()).intValue(), j2, j);
                }
            } catch (RemoteException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture started, extension service doesn't respond!");
            }
        }
    }

    private static final class ImageReaderHandler implements ImageReader.OnImageAvailableListener {
        private final IImageProcessorImpl mIImageProcessor;
        private final OutputConfigId mOutputConfigId;
        private final String mPhysicalCameraId;

        private ImageReaderHandler(int i, IImageProcessorImpl iImageProcessorImpl, String str) {
            OutputConfigId outputConfigId = new OutputConfigId();
            this.mOutputConfigId = outputConfigId;
            outputConfigId.id = i;
            this.mIImageProcessor = iImageProcessorImpl;
            this.mPhysicalCameraId = str;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader imageReader) {
            if (this.mIImageProcessor == null) {
                return;
            }
            try {
                Image imageAcquireNextImage = imageReader.acquireNextImage();
                if (imageAcquireNextImage == null) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    imageReader.detachImage(imageAcquireNextImage);
                    ParcelImage parcelImage = new ParcelImage();
                    parcelImage.buffer = imageAcquireNextImage.getHardwareBuffer();
                    try {
                        SyncFence fence = imageAcquireNextImage.getFence();
                        if (fence.isValid()) {
                            parcelImage.fence = fence.getFdDup();
                        }
                    } catch (IOException unused) {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to parcel buffer fence!");
                    }
                    parcelImage.width = imageAcquireNextImage.getWidth();
                    parcelImage.height = imageAcquireNextImage.getHeight();
                    parcelImage.format = imageAcquireNextImage.getFormat();
                    parcelImage.timestamp = imageAcquireNextImage.getTimestamp();
                    parcelImage.transform = imageAcquireNextImage.getTransform();
                    parcelImage.scalingMode = imageAcquireNextImage.getScalingMode();
                    parcelImage.planeCount = imageAcquireNextImage.getPlaneCount();
                    parcelImage.crop = imageAcquireNextImage.getCropRect();
                    try {
                        try {
                            this.mIImageProcessor.onNextImageAvailable(this.mOutputConfigId, parcelImage, this.mPhysicalCameraId);
                        } catch (RemoteException unused2) {
                            Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to propagate image buffer on output surface id: " + this.mOutputConfigId + " extension service does not respond!");
                        }
                    } finally {
                        parcelImage.buffer.close();
                        imageAcquireNextImage.close();
                    }
                } catch (Exception unused3) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to detach image");
                    imageAcquireNextImage.close();
                }
            } catch (IllegalStateException unused4) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to acquire image, too many images pending!");
            }
        }
    }

    private final class RequestProcessor extends IRequestProcessorImpl.Stub {
        private RequestProcessor() {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl iImageProcessorImpl) {
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (CameraAdvancedExtensionSessionImpl.this.mReaderMap.containsKey(Integer.valueOf(outputConfigId.id))) {
                    ImageReader imageReader = (ImageReader) CameraAdvancedExtensionSessionImpl.this.mReaderMap.get(Integer.valueOf(outputConfigId.id));
                    if (CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.containsKey(imageReader.getSurface())) {
                        imageReader.setOnImageAvailableListener(new ImageReaderHandler(outputConfigId.id, iImageProcessorImpl, ((CameraOutputConfig) CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(imageReader.getSurface())).physicalCameraId), CameraAdvancedExtensionSessionImpl.this.mHandler);
                    } else {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Camera output configuration for ImageReader with  config Id " + outputConfigId.id + " not found!");
                    }
                } else {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "ImageReader with output config id: " + outputConfigId.id + " not found!");
                }
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(Request request, IRequestCallback iRequestCallback) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(request);
            return submitBurst(arrayList, iRequestCallback);
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(List<Request> list, IRequestCallback iRequestCallback) {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (!CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        return -1;
                    }
                    CaptureCallbackHandler captureCallbackHandler = CameraAdvancedExtensionSessionImpl.this.new CaptureCallbackHandler(iRequestCallback);
                    ArrayList arrayList = new ArrayList();
                    Iterator<Request> it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(CameraAdvancedExtensionSessionImpl.this.mCameraDevice, it.next(), CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap));
                    }
                    return CameraAdvancedExtensionSessionImpl.this.mCaptureSession.captureBurstRequests(arrayList, new CameraExtensionUtils.HandlerExecutor(CameraAdvancedExtensionSessionImpl.this.mHandler), captureCallbackHandler);
                }
            } catch (CameraAccessException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to submit capture requests!");
                return -1;
            } catch (IllegalStateException unused2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
                return -1;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(Request request, IRequestCallback iRequestCallback) {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (!CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        return -1;
                    }
                    return CameraAdvancedExtensionSessionImpl.this.mCaptureSession.setSingleRepeatingRequest(CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(CameraAdvancedExtensionSessionImpl.this.mCameraDevice, request, CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap), new CameraExtensionUtils.HandlerExecutor(CameraAdvancedExtensionSessionImpl.this.mHandler), CameraAdvancedExtensionSessionImpl.this.new CaptureCallbackHandler(iRequestCallback));
                }
            } catch (CameraAccessException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to enable repeating request!");
                return -1;
            } catch (IllegalStateException unused2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
                return -1;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        CameraAdvancedExtensionSessionImpl.this.mCaptureSession.abortCaptures();
                    }
                }
            } catch (CameraAccessException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed during capture abort!");
            } catch (IllegalStateException unused2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        CameraAdvancedExtensionSessionImpl.this.mCaptureSession.stopRepeating();
                    }
                }
            } catch (CameraAccessException unused) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed during repeating capture stop!");
            } catch (IllegalStateException unused2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CaptureRequest initializeCaptureRequest(CameraDevice cameraDevice, Request request, HashMap<Surface, CameraOutputConfig> map) throws CameraAccessException {
        CaptureRequest.Builder builderCreateCaptureRequest = cameraDevice.createCaptureRequest(request.templateId);
        for (OutputConfigId outputConfigId : request.targetOutputConfigIds) {
            Iterator<Map.Entry<Surface, CameraOutputConfig>> it = map.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    Log.e(TAG, "Surface with output id: " + outputConfigId.id + " not found among registered camera outputs!");
                    break;
                }
                Map.Entry<Surface, CameraOutputConfig> next = it.next();
                if (next.getValue().outputId.id == outputConfigId.id) {
                    builderCreateCaptureRequest.addTarget(next.getKey());
                    break;
                }
            }
        }
        builderCreateCaptureRequest.setTag(Integer.valueOf(request.requestId));
        CaptureRequest captureRequestBuild = builderCreateCaptureRequest.build();
        CameraMetadataNative.update(captureRequestBuild.getNativeMetadata(), request.parameters);
        return captureRequestBuild;
    }

    private Surface initializeSurface(CameraOutputConfig cameraOutputConfig) {
        int i = cameraOutputConfig.type;
        if (i == 0) {
            if (cameraOutputConfig.surface == null) {
                Log.w(TAG, "Unsupported client output id: " + cameraOutputConfig.outputId.id + ", skipping!");
                return null;
            }
            return cameraOutputConfig.surface;
        }
        if (i == 1) {
            if (cameraOutputConfig.imageFormat == 0 || cameraOutputConfig.size.width <= 0 || cameraOutputConfig.size.height <= 0) {
                Log.w(TAG, "Unsupported client output id: " + cameraOutputConfig.outputId.id + ", skipping!");
                return null;
            }
            ImageReader imageReaderNewInstance = ImageReader.newInstance(cameraOutputConfig.size.width, cameraOutputConfig.size.height, cameraOutputConfig.imageFormat, cameraOutputConfig.capacity, cameraOutputConfig.usage);
            this.mReaderMap.put(Integer.valueOf(cameraOutputConfig.outputId.id), imageReaderNewInstance);
            return imageReaderNewInstance.getSurface();
        }
        throw new IllegalArgumentException("Unsupported output config type: " + cameraOutputConfig.type);
    }
}
