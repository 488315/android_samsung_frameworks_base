package android.hardware.camera2.impl;

import android.content.Context;
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
import android.hardware.camera2.params.ExtensionSessionConfiguration;
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
import android.util.IntArray;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;

import com.android.internal.camera.flags.Flags;

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
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);

    private interface OnImageAvailableListener {
        void onImageAvailable(ImageReader imageReader, Image image);

        void onImageDropped(long j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int nativeGetSurfaceFormat(Surface surface) {
        return SurfaceUtils.getSurfaceFormat(surface);
    }

    public static CameraExtensionSessionImpl createCameraExtensionSession(
            CameraDeviceImpl cameraDevice,
            Map<String, CameraCharacteristics> characteristicsMap,
            Context ctx,
            ExtensionSessionConfiguration config,
            int sessionId,
            IBinder token)
            throws CameraAccessException, RemoteException {
        int suitableSurfaceCount;
        int captureFormat;
        Surface postviewSurface;
        int captureFormat2;
        Size burstCaptureSurfaceSize;
        String cameraId = cameraDevice.getId();
        CameraExtensionCharacteristics extensionChars =
                new CameraExtensionCharacteristics(ctx, cameraId, characteristicsMap);
        if (!CameraExtensionCharacteristics.isExtensionSupported(
                cameraDevice.getId(),
                config.getExtension(),
                CameraExtensionUtils.getCharacteristicsMapNative(characteristicsMap))) {
            throw new UnsupportedOperationException(
                    "Unsupported extension type: " + config.getExtension());
        }
        if (!config.getOutputConfigurations().isEmpty()
                && config.getOutputConfigurations().size() <= 2) {
            for (OutputConfiguration c : config.getOutputConfigurations()) {
                if (c.getDynamicRangeProfile() != 1) {
                    throw new IllegalArgumentException(
                            "Unsupported dynamic range profile: " + c.getDynamicRangeProfile());
                }
                if (c.getStreamUseCase() != 0) {
                    throw new IllegalArgumentException(
                            "Unsupported stream use case: " + c.getStreamUseCase());
                }
            }
            Pair<IPreviewExtenderImpl, IImageCaptureExtenderImpl> extenders =
                    CameraExtensionCharacteristics.initializeExtension(config.getExtension());
            int suitableSurfaceCount2 = 0;
            List<Size> supportedPreviewSizes =
                    extensionChars.getExtensionSupportedSizes(
                            config.getExtension(), SurfaceTexture.class);
            Surface repeatingRequestSurface =
                    CameraExtensionUtils.getRepeatingRequestSurface(
                            config.getOutputConfigurations(), supportedPreviewSizes);
            if (repeatingRequestSurface != null) {
                suitableSurfaceCount2 = 0 + 1;
            }
            HashMap<Integer, List<Size>> supportedCaptureSizes = new HashMap<>();
            IntArray supportedCaptureOutputFormats =
                    new IntArray(CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS.length);
            supportedCaptureOutputFormats.addAll(
                    CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS);
            if (Flags.extension10Bit()) {
                supportedCaptureOutputFormats.add(54);
            }
            for (int format : supportedCaptureOutputFormats.toArray()) {
                List<Size> supportedSizes =
                        extensionChars.getExtensionSupportedSizes(config.getExtension(), format);
                if (supportedSizes != null) {
                    supportedCaptureSizes.put(Integer.valueOf(format), supportedSizes);
                }
            }
            Surface burstCaptureSurface =
                    CameraExtensionUtils.getBurstCaptureSurface(
                            config.getOutputConfigurations(), supportedCaptureSizes);
            if (burstCaptureSurface == null) {
                suitableSurfaceCount = suitableSurfaceCount2;
                captureFormat = 0;
            } else {
                int suitableSurfaceCount3 = suitableSurfaceCount2 + 1;
                if (!Flags.analytics24q3()) {
                    suitableSurfaceCount = suitableSurfaceCount3;
                    captureFormat = 0;
                } else {
                    int captureFormat3 =
                            CameraExtensionUtils.querySurface(burstCaptureSurface).mFormat;
                    suitableSurfaceCount = suitableSurfaceCount3;
                    captureFormat = captureFormat3;
                }
            }
            if (suitableSurfaceCount != config.getOutputConfigurations().size()) {
                throw new IllegalArgumentException(
                        "One or more unsupported output surfaces found!");
            }
            if (burstCaptureSurface == null || config.getPostviewOutputConfiguration() == null) {
                postviewSurface = null;
                captureFormat2 = captureFormat;
            } else {
                CameraExtensionUtils.SurfaceInfo burstCaptureSurfaceInfo =
                        CameraExtensionUtils.querySurface(burstCaptureSurface);
                Size burstCaptureSurfaceSize2 =
                        new Size(burstCaptureSurfaceInfo.mWidth, burstCaptureSurfaceInfo.mHeight);
                HashMap<Integer, List<Size>> supportedPostviewSizes = new HashMap<>();
                int[] array = supportedCaptureOutputFormats.toArray();
                int length = array.length;
                captureFormat2 = captureFormat;
                int captureFormat4 = 0;
                while (captureFormat4 < length) {
                    int i = length;
                    int format2 = array[captureFormat4];
                    int[] iArr = array;
                    List<Size> supportedSizesPostview =
                            extensionChars.getPostviewSupportedSizes(
                                    config.getExtension(), burstCaptureSurfaceSize2, format2);
                    if (supportedSizesPostview == null) {
                        burstCaptureSurfaceSize = burstCaptureSurfaceSize2;
                    } else {
                        burstCaptureSurfaceSize = burstCaptureSurfaceSize2;
                        supportedPostviewSizes.put(
                                Integer.valueOf(format2), supportedSizesPostview);
                    }
                    captureFormat4++;
                    length = i;
                    array = iArr;
                    burstCaptureSurfaceSize2 = burstCaptureSurfaceSize;
                }
                Surface postviewSurface2 =
                        CameraExtensionUtils.getPostviewSurface(
                                config.getPostviewOutputConfiguration(),
                                supportedPostviewSizes,
                                burstCaptureSurfaceInfo.mFormat);
                if (postviewSurface2 == null) {
                    throw new IllegalArgumentException("Unsupported output surface for postview!");
                }
                postviewSurface = postviewSurface2;
            }
            extenders.first.init(cameraId, characteristicsMap.get(cameraId).getNativeMetadata());
            extenders.first.onInit(
                    token, cameraId, characteristicsMap.get(cameraId).getNativeMetadata());
            extenders.second.init(cameraId, characteristicsMap.get(cameraId).getNativeMetadata());
            extenders.second.onInit(
                    token, cameraId, characteristicsMap.get(cameraId).getNativeMetadata());
            int captureFormat5 = captureFormat2;
            CameraExtensionSessionImpl session =
                    new CameraExtensionSessionImpl(
                            ctx,
                            extenders.second,
                            extenders.first,
                            supportedPreviewSizes,
                            cameraDevice,
                            repeatingRequestSurface,
                            burstCaptureSurface,
                            postviewSurface,
                            config.getStateCallback(),
                            config.getExecutor(),
                            sessionId,
                            token,
                            extensionChars.getAvailableCaptureRequestKeys(config.getExtension()),
                            extensionChars.getAvailableCaptureResultKeys(config.getExtension()),
                            config.getExtension());
            if (Flags.analytics24q3()) {
                session.mStatsAggregator.setCaptureFormat(captureFormat5);
            }
            session.mStatsAggregator.setClientName(ctx.getOpPackageName());
            session.mStatsAggregator.setExtensionType(config.getExtension());
            session.initialize();
            return session;
        }
        throw new IllegalArgumentException(
                "Unexpected amount of output surfaces, received: "
                        + config.getOutputConfigurations().size()
                        + " expected <= 2");
    }

    public CameraExtensionSessionImpl(
            Context ctx,
            IImageCaptureExtenderImpl imageExtender,
            IPreviewExtenderImpl previewExtender,
            List<Size> previewSizes,
            CameraDeviceImpl cameraDevice,
            Surface repeatingRequestSurface,
            Surface burstCaptureSurface,
            Surface postviewSurface,
            CameraExtensionSession.StateCallback callback,
            Executor executor,
            int sessionId,
            IBinder token,
            Set<CaptureRequest.Key> requestKeys,
            Set<CaptureResult.Key> resultKeys,
            int extension) {
        this.mToken = null;
        this.mContext = ctx;
        this.mImageExtender = imageExtender;
        this.mPreviewExtender = previewExtender;
        this.mCameraDevice = cameraDevice;
        this.mCallbacks = callback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestSurface = repeatingRequestSurface;
        this.mClientCaptureSurface = burstCaptureSurface;
        this.mClientPostviewSurface = postviewSurface;
        this.mSupportedPreviewSizes = previewSizes;
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new InitializeSessionHandler();
        this.mSessionId = sessionId;
        this.mToken = token;
        this.mSupportedRequestKeys = requestKeys;
        this.mSupportedResultKeys = resultKeys;
        this.mCaptureResultsSupported = !resultKeys.isEmpty();
        this.mInterfaceLock = cameraDevice.mInterfaceLock;
        this.mExtensionType = extension;
        this.mStatsAggregator =
                new ExtensionSessionStatsAggregator(this.mCameraDevice.getId(), false);
    }

    private void initializeRepeatingRequestPipeline() throws RemoteException {
        CameraExtensionUtils.SurfaceInfo repeatingSurfaceInfo =
                new CameraExtensionUtils.SurfaceInfo();
        this.mPreviewProcessorType = this.mPreviewExtender.getProcessorType();
        if (this.mClientRepeatingRequestSurface != null) {
            repeatingSurfaceInfo =
                    CameraExtensionUtils.querySurface(this.mClientRepeatingRequestSurface);
        } else {
            CameraExtensionUtils.SurfaceInfo captureSurfaceInfo =
                    CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
            Size captureSize = new Size(captureSurfaceInfo.mWidth, captureSurfaceInfo.mHeight);
            Size previewSize =
                    findSmallestAspectMatchedSize(this.mSupportedPreviewSizes, captureSize);
            repeatingSurfaceInfo.mWidth = previewSize.getWidth();
            repeatingSurfaceInfo.mHeight = previewSize.getHeight();
            repeatingSurfaceInfo.mUsage = 256L;
        }
        if (this.mPreviewProcessorType == 1) {
            try {
                this.mPreviewImageProcessor =
                        new CameraExtensionForwardProcessor(
                                this.mPreviewExtender.getPreviewImageProcessor(),
                                repeatingSurfaceInfo.mFormat,
                                repeatingSurfaceInfo.mUsage,
                                this.mHandler);
                this.mPreviewImageProcessor.onImageFormatUpdate(35);
                this.mPreviewImageProcessor.onResolutionUpdate(
                        new Size(repeatingSurfaceInfo.mWidth, repeatingSurfaceInfo.mHeight));
                this.mPreviewImageProcessor.onOutputSurface(null, -1);
                this.mRepeatingRequestImageReader =
                        ImageReader.newInstance(
                                repeatingSurfaceInfo.mWidth,
                                repeatingSurfaceInfo.mHeight,
                                35,
                                10,
                                repeatingSurfaceInfo.mUsage);
                this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
            } catch (ClassCastException e) {
                throw new UnsupportedOperationException("Failed casting preview processor!");
            }
        } else if (this.mPreviewProcessorType == 0) {
            try {
                this.mPreviewRequestUpdateProcessor =
                        this.mPreviewExtender.getRequestUpdateProcessor();
                this.mRepeatingRequestImageReader =
                        ImageReader.newInstance(
                                repeatingSurfaceInfo.mWidth,
                                repeatingSurfaceInfo.mHeight,
                                34,
                                10,
                                repeatingSurfaceInfo.mUsage);
                this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
                android.hardware.camera2.extension.Size sz =
                        new android.hardware.camera2.extension.Size();
                sz.width = repeatingSurfaceInfo.mWidth;
                sz.height = repeatingSurfaceInfo.mHeight;
                this.mPreviewRequestUpdateProcessor.onResolutionUpdate(sz);
                this.mPreviewRequestUpdateProcessor.onImageFormatUpdate(34);
            } catch (ClassCastException e2) {
                throw new UnsupportedOperationException("Failed casting preview processor!");
            }
        } else {
            this.mRepeatingRequestImageReader =
                    ImageReader.newInstance(
                            repeatingSurfaceInfo.mWidth,
                            repeatingSurfaceInfo.mHeight,
                            34,
                            10,
                            repeatingSurfaceInfo.mUsage);
            this.mCameraRepeatingSurface = this.mRepeatingRequestImageReader.getSurface();
        }
        this.mRepeatingRequestImageCallback =
                new CameraOutputImageCallback(this.mRepeatingRequestImageReader, true);
        this.mRepeatingRequestImageReader.setOnImageAvailableListener(
                this.mRepeatingRequestImageCallback, this.mHandler);
    }

    private void initializeBurstCapturePipeline() throws RemoteException {
        this.mImageProcessor = this.mImageExtender.getCaptureProcessor();
        if (this.mImageProcessor == null && this.mImageExtender.getMaxCaptureStage() != 1) {
            throw new UnsupportedOperationException(
                    "Multiple stages expected without a valid capture processor!");
        }
        if (this.mImageProcessor != null) {
            if (this.mClientCaptureSurface != null) {
                CameraExtensionUtils.SurfaceInfo surfaceInfo =
                        CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
                if (surfaceInfo.mFormat == 256) {
                    this.mImageJpegProcessor =
                            new CameraExtensionJpegProcessor(this.mImageProcessor);
                    this.mImageProcessor = this.mImageJpegProcessor;
                } else if (Flags.extension10Bit()
                        && this.mClientPostviewSurface != null
                        && CameraExtensionUtils.querySurface(this.mClientPostviewSurface).mFormat
                                == 256) {
                    this.mImageJpegProcessor =
                            new CameraExtensionJpegProcessor(this.mImageProcessor);
                    this.mImageProcessor = this.mImageJpegProcessor;
                }
                this.mBurstCaptureImageReader =
                        ImageReader.newInstance(
                                surfaceInfo.mWidth,
                                surfaceInfo.mHeight,
                                35,
                                this.mImageExtender.getMaxCaptureStage());
            } else {
                this.mBurstCaptureImageReader =
                        ImageReader.newInstance(
                                this.mRepeatingRequestImageReader.getWidth(),
                                this.mRepeatingRequestImageReader.getHeight(),
                                35,
                                1);
                this.mStubCaptureImageReader =
                        ImageReader.newInstance(
                                this.mRepeatingRequestImageReader.getWidth(),
                                this.mRepeatingRequestImageReader.getHeight(),
                                35,
                                1);
                this.mImageProcessor.onOutputSurface(this.mStubCaptureImageReader.getSurface(), 35);
            }
            this.mBurstCaptureImageCallback =
                    new CameraOutputImageCallback(this.mBurstCaptureImageReader, false);
            this.mBurstCaptureImageReader.setOnImageAvailableListener(
                    this.mBurstCaptureImageCallback, this.mHandler);
            this.mCameraBurstSurface = this.mBurstCaptureImageReader.getSurface();
            android.hardware.camera2.extension.Size sz =
                    new android.hardware.camera2.extension.Size();
            sz.width = this.mBurstCaptureImageReader.getWidth();
            sz.height = this.mBurstCaptureImageReader.getHeight();
            if (this.mClientPostviewSurface != null) {
                CameraExtensionUtils.SurfaceInfo postviewSurfaceInfo =
                        CameraExtensionUtils.querySurface(this.mClientPostviewSurface);
                android.hardware.camera2.extension.Size postviewSize =
                        new android.hardware.camera2.extension.Size();
                postviewSize.width = postviewSurfaceInfo.mWidth;
                postviewSize.height = postviewSurfaceInfo.mHeight;
                this.mImageProcessor.onResolutionUpdate(sz, postviewSize);
            } else {
                this.mImageProcessor.onResolutionUpdate(sz, null);
            }
            this.mImageProcessor.onImageFormatUpdate(
                    this.mBurstCaptureImageReader.getImageFormat());
            return;
        }
        if (this.mClientCaptureSurface != null) {
            this.mCameraBurstSurface = this.mClientCaptureSurface;
        } else {
            this.mBurstCaptureImageReader =
                    ImageReader.newInstance(
                            this.mRepeatingRequestImageReader.getWidth(),
                            this.mRepeatingRequestImageReader.getHeight(),
                            256,
                            1);
            this.mCameraBurstSurface = this.mBurstCaptureImageReader.getSurface();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishPipelineInitialization() throws RemoteException {
        if (this.mClientRepeatingRequestSurface != null) {
            if (this.mPreviewProcessorType == 0) {
                this.mPreviewRequestUpdateProcessor.onOutputSurface(
                        this.mClientRepeatingRequestSurface,
                        nativeGetSurfaceFormat(this.mClientRepeatingRequestSurface));
                this.mRepeatingRequestImageWriter =
                        ImageWriter.newInstance(this.mClientRepeatingRequestSurface, 10, 34);
            } else if (this.mPreviewProcessorType == 2) {
                this.mRepeatingRequestImageWriter =
                        ImageWriter.newInstance(this.mClientRepeatingRequestSurface, 10, 34);
            }
        }
        if (this.mImageProcessor != null && this.mClientCaptureSurface != null) {
            if (this.mClientPostviewSurface != null) {
                this.mImageProcessor.onPostviewOutputSurface(this.mClientPostviewSurface);
            }
            CameraExtensionUtils.SurfaceInfo surfaceInfo =
                    CameraExtensionUtils.querySurface(this.mClientCaptureSurface);
            this.mImageProcessor.onOutputSurface(this.mClientCaptureSurface, surfaceInfo.mFormat);
        }
    }

    public synchronized void initialize() throws CameraAccessException, RemoteException {
        if (this.mInitialized) {
            Log.d(TAG, "Session already initialized");
            return;
        }
        int previewSessionType = this.mPreviewExtender.getSessionType();
        int imageSessionType = this.mImageExtender.getSessionType();
        if (previewSessionType != imageSessionType) {
            throw new IllegalStateException(
                    "Preview extender session type: "
                            + previewSessionType
                            + "and image extender session type: "
                            + imageSessionType
                            + " mismatch!");
        }
        int sessionType = 0;
        if (previewSessionType != -1 && previewSessionType != 1) {
            sessionType = previewSessionType;
            Log.v(TAG, "Using session type: " + sessionType);
        }
        ArrayList<CaptureStageImpl> sessionParamsList = new ArrayList<>();
        ArrayList<OutputConfiguration> outputList = new ArrayList<>();
        initializeRepeatingRequestPipeline();
        OutputConfiguration previewOutput = new OutputConfiguration(this.mCameraRepeatingSurface);
        previewOutput.setTimestampBase(1);
        previewOutput.setReadoutTimestampEnabled(false);
        outputList.add(previewOutput);
        CaptureStageImpl previewSessionParams = this.mPreviewExtender.onPresetSession();
        if (previewSessionParams != null) {
            sessionParamsList.add(previewSessionParams);
        }
        initializeBurstCapturePipeline();
        OutputConfiguration captureOutput = new OutputConfiguration(this.mCameraBurstSurface);
        captureOutput.setTimestampBase(1);
        captureOutput.setReadoutTimestampEnabled(false);
        outputList.add(captureOutput);
        CaptureStageImpl stillCaptureSessionParams = this.mImageExtender.onPresetSession();
        if (stillCaptureSessionParams != null) {
            sessionParamsList.add(stillCaptureSessionParams);
        }
        SessionConfiguration sessionConfig =
                new SessionConfiguration(
                        sessionType,
                        outputList,
                        new CameraExtensionUtils.HandlerExecutor(this.mHandler),
                        new SessionStateHandler());
        if (!sessionParamsList.isEmpty()) {
            CaptureRequest sessionParamRequest =
                    createRequest(this.mCameraDevice, sessionParamsList, null, 1);
            sessionConfig.setSessionParameters(sessionParamRequest);
        }
        this.mCameraDevice.createCaptureSession(sessionConfig);
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
    public CameraExtensionSession.StillCaptureLatency getRealtimeStillCaptureLatency()
            throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            try {
                LatencyPair latency = this.mImageExtender.getRealtimeCaptureLatency();
                if (latency == null) {
                    return null;
                }
                return new CameraExtensionSession.StillCaptureLatency(
                        latency.first, latency.second);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to query realtime latency! Extension service does not respond");
                throw new CameraAccessException(3);
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int setRepeatingRequest(
            CaptureRequest request,
            Executor executor,
            CameraExtensionSession.ExtensionCaptureCallback listener)
            throws CameraAccessException {
        int repeatingRequest;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            if (this.mClientRepeatingRequestSurface == null) {
                throw new IllegalArgumentException("No registered preview surface");
            }
            if (!request.containsTarget(this.mClientRepeatingRequestSurface)
                    || request.getTargets().size() != 1) {
                throw new IllegalArgumentException("Invalid repeating request output target!");
            }
            this.mInternalRepeatingRequestEnabled = false;
            try {
                repeatingRequest =
                        setRepeatingRequest(
                                this.mPreviewExtender.getCaptureStage(),
                                new PreviewRequestHandler(
                                        this,
                                        request,
                                        executor,
                                        listener,
                                        this.mRepeatingRequestImageCallback),
                                request);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to set repeating request! Extension service does not respond");
                throw new CameraAccessException(3);
            }
        }
        return repeatingRequest;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<CaptureStageImpl> compileInitialRequestList() {
        ArrayList<CaptureStageImpl> captureStageList = new ArrayList<>();
        try {
            CaptureStageImpl initialPreviewParams = this.mPreviewExtender.onEnableSession();
            if (initialPreviewParams != null) {
                captureStageList.add(initialPreviewParams);
            }
            CaptureStageImpl initialStillCaptureParams = this.mImageExtender.onEnableSession();
            if (initialStillCaptureParams != null) {
                captureStageList.add(initialStillCaptureParams);
            }
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Failed to initialize session parameters! Extension service does not respond!");
        }
        return captureStageList;
    }

    private List<CaptureRequest> createBurstRequest(
            CameraDevice cameraDevice,
            List<CaptureStageImpl> captureStageList,
            CaptureRequest clientRequest,
            Surface target,
            int captureTemplate,
            Map<CaptureRequest, Integer> captureMap) {
        ArrayList<CaptureRequest> ret = new ArrayList<>();
        for (CaptureStageImpl captureStage : captureStageList) {
            try {
                CaptureRequest.Builder requestBuilder =
                        cameraDevice.createCaptureRequest(captureTemplate);
                for (CaptureRequest.Key requestKey : this.mSupportedRequestKeys) {
                    Object value = clientRequest.get(requestKey);
                    if (value != null) {
                        captureStage.parameters.set(
                                (CaptureRequest.Key<CaptureRequest.Key>) requestKey,
                                (CaptureRequest.Key) value);
                    }
                }
                requestBuilder.addTarget(target);
                CaptureRequest request = requestBuilder.build();
                CameraMetadataNative.update(request.getNativeMetadata(), captureStage.parameters);
                ret.add(request);
                if (captureMap != null) {
                    captureMap.put(request, Integer.valueOf(captureStage.id));
                }
            } catch (CameraAccessException e) {
                return null;
            }
        }
        return ret;
    }

    private CaptureRequest createRequest(
            CameraDevice cameraDevice,
            List<CaptureStageImpl> captureStageList,
            Surface target,
            int captureTemplate,
            CaptureRequest clientRequest)
            throws CameraAccessException {
        CaptureRequest.Builder requestBuilder = cameraDevice.createCaptureRequest(captureTemplate);
        if (target != null) {
            requestBuilder.addTarget(target);
        }
        CaptureRequest ret = requestBuilder.build();
        CameraMetadataNative nativeMeta = ret.getNativeMetadata();
        for (CaptureStageImpl captureStage : captureStageList) {
            if (captureStage != null) {
                CameraMetadataNative.update(nativeMeta, captureStage.parameters);
            }
        }
        if (clientRequest != null) {
            for (CaptureRequest.Key requestKey : this.mSupportedRequestKeys) {
                Object value = clientRequest.get(requestKey);
                if (value != null) {
                    nativeMeta.set(
                            (CaptureRequest.Key<CaptureRequest.Key>) requestKey,
                            (CaptureRequest.Key) value);
                }
            }
        }
        return ret;
    }

    private CaptureRequest createRequest(
            CameraDevice cameraDevice,
            List<CaptureStageImpl> captureStageList,
            Surface target,
            int captureTemplate)
            throws CameraAccessException {
        return createRequest(cameraDevice, captureStageList, target, captureTemplate, null);
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(
            CaptureRequest request,
            Executor executor,
            CameraExtensionSession.ExtensionCaptureCallback listener)
            throws CameraAccessException {
        if (!this.mInitialized) {
            throw new IllegalStateException("Uninitialized component");
        }
        validateCaptureRequestTargets(request);
        if (this.mClientCaptureSurface != null
                && request.containsTarget(this.mClientCaptureSurface)) {
            HashMap<CaptureRequest, Integer> requestMap = new HashMap<>();
            try {
                List<CaptureRequest> burstRequest =
                        createBurstRequest(
                                this.mCameraDevice,
                                this.mImageExtender.getCaptureStages(),
                                request,
                                this.mCameraBurstSurface,
                                2,
                                requestMap);
                if (burstRequest == null) {
                    throw new UnsupportedOperationException(
                            "Failed to create still capture burst request");
                }
                int seqId =
                        this.mCaptureSession.captureBurstRequests(
                                burstRequest,
                                new CameraExtensionUtils.HandlerExecutor(this.mHandler),
                                new BurstRequestHandler(
                                        request,
                                        executor,
                                        listener,
                                        requestMap,
                                        this.mBurstCaptureImageCallback));
                return seqId;
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Failed to initialize internal burst request! Extension service does not"
                                + " respond!");
                throw new CameraAccessException(3);
            }
        }
        if (this.mClientRepeatingRequestSurface != null
                && request.containsTarget(this.mClientRepeatingRequestSurface)) {
            try {
                ArrayList<CaptureStageImpl> captureStageList = new ArrayList<>();
                captureStageList.add(this.mPreviewExtender.getCaptureStage());
                CaptureRequest captureRequest =
                        createRequest(
                                this.mCameraDevice,
                                captureStageList,
                                this.mCameraRepeatingSurface,
                                1,
                                request);
                int seqId2 =
                        this.mCaptureSession.capture(
                                captureRequest,
                                new PreviewRequestHandler(
                                        request,
                                        executor,
                                        listener,
                                        this.mRepeatingRequestImageCallback,
                                        true),
                                this.mHandler);
                return seqId2;
            } catch (RemoteException e2) {
                Log.e(
                        TAG,
                        "Failed to initialize capture request! Extension service does not"
                                + " respond!");
                throw new CameraAccessException(3);
            }
        }
        throw new IllegalArgumentException("Capture request to unknown output surface!");
    }

    private void validateCaptureRequestTargets(CaptureRequest request) {
        if (request.getTargets().size() == 1) {
            boolean containsCaptureTarget =
                    this.mClientCaptureSurface != null
                            && request.containsTarget(this.mClientCaptureSurface);
            boolean containsRepeatingTarget =
                    this.mClientRepeatingRequestSurface != null
                            && request.containsTarget(this.mClientRepeatingRequestSurface);
            if (!containsCaptureTarget && !containsRepeatingTarget) {
                throw new IllegalArgumentException(
                        "Target output combination requested is not supported!");
            }
        }
        if (request.getTargets().size() == 2
                && !request.getTargets()
                        .containsAll(
                                Arrays.asList(
                                        this.mClientCaptureSurface, this.mClientPostviewSurface))) {
            throw new IllegalArgumentException(
                    "Target output combination requested is not supported!");
        }
        if (request.getTargets().size() > 2) {
            throw new IllegalArgumentException(
                    "Target output combination requested is not supported!");
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
                } catch (IllegalStateException e) {
                    this.mSessionClosed = true;
                }
                ArrayList<CaptureStageImpl> captureStageList = new ArrayList<>();
                try {
                    CaptureStageImpl disableParams = this.mPreviewExtender.onDisableSession();
                    if (disableParams != null) {
                        captureStageList.add(disableParams);
                    }
                    CaptureStageImpl disableStillCaptureParams =
                            this.mImageExtender.onDisableSession();
                    if (disableStillCaptureParams != null) {
                        captureStageList.add(disableStillCaptureParams);
                    }
                } catch (RemoteException e2) {
                    Log.e(TAG, "Failed to disable extension! Extension service does not respond!");
                }
                if (!captureStageList.isEmpty() && !this.mSessionClosed) {
                    CaptureRequest disableRequest =
                            createRequest(
                                    this.mCameraDevice,
                                    captureStageList,
                                    this.mCameraRepeatingSurface,
                                    1);
                    this.mCaptureSession.capture(
                            disableRequest,
                            new CloseRequestHandler(this.mRepeatingRequestImageCallback),
                            this.mHandler);
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
    public void setInitialCaptureRequest(
            List<CaptureStageImpl> captureStageList, InitialRequestHandler requestHandler)
            throws CameraAccessException {
        CaptureRequest initialRequest =
                createRequest(
                        this.mCameraDevice, captureStageList, this.mCameraRepeatingSurface, 1);
        this.mCaptureSession.capture(initialRequest, requestHandler, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(
            CaptureStageImpl captureStage, CameraCaptureSession.CaptureCallback requestHandler)
            throws CameraAccessException {
        return setRepeatingRequest(captureStage, requestHandler, (CaptureRequest) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setRepeatingRequest(
            CaptureStageImpl captureStage,
            CameraCaptureSession.CaptureCallback requestHandler,
            CaptureRequest clientRequest)
            throws CameraAccessException {
        ArrayList<CaptureStageImpl> captureStageList = new ArrayList<>();
        captureStageList.add(captureStage);
        CaptureRequest repeatingRequest =
                createRequest(
                        this.mCameraDevice,
                        captureStageList,
                        this.mCameraRepeatingSurface,
                        1,
                        clientRequest);
        return this.mCaptureSession.setSingleRepeatingRequest(
                repeatingRequest,
                new CameraExtensionUtils.HandlerExecutor(this.mHandler),
                requestHandler);
    }

    public void release(boolean skipCloseNotification) {
        boolean notifyClose = false;
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
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to release extensions! Extension service does not respond!");
            }
            if (this.mToken != null) {
                if (this.mInitialized || this.mCaptureSession != null) {
                    notifyClose = true;
                    CameraExtensionCharacteristics.releaseSession(this.mExtensionType);
                }
                CameraExtensionCharacteristics.unregisterClient(
                        this.mContext, this.mToken, this.mExtensionType);
            }
            this.mInitialized = false;
            this.mToken = null;
            if (this.mRepeatingRequestImageCallback != null) {
                this.mRepeatingRequestImageCallback.close();
                this.mRepeatingRequestImageCallback = null;
            }
            if (this.mRepeatingRequestImageReader != null) {
                this.mRepeatingRequestImageReader.close();
                this.mRepeatingRequestImageReader = null;
            }
            if (this.mBurstCaptureImageCallback != null) {
                this.mBurstCaptureImageCallback.close();
                this.mBurstCaptureImageCallback = null;
            }
            if (this.mBurstCaptureImageReader != null) {
                this.mBurstCaptureImageReader.close();
                this.mBurstCaptureImageReader = null;
            }
            if (this.mStubCaptureImageReader != null) {
                this.mStubCaptureImageReader.close();
                this.mStubCaptureImageReader = null;
            }
            if (this.mRepeatingRequestImageWriter != null) {
                this.mRepeatingRequestImageWriter.close();
                this.mRepeatingRequestImageWriter = null;
            }
            if (this.mPreviewImageProcessor != null) {
                this.mPreviewImageProcessor.close();
                this.mPreviewImageProcessor = null;
            }
            if (this.mImageJpegProcessor != null) {
                this.mImageJpegProcessor.close();
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
        if (notifyClose && !skipCloseNotification) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.this.lambda$release$0();
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
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
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.this
                                        .lambda$notifyConfigurationFailure$1();
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
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
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.this
                                        .lambda$notifyConfigurationSuccess$2();
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationSuccess$2() {
        this.mCallbacks.onConfigured(this);
    }

    private class SessionStateHandler extends CameraCaptureSession.StateCallback {
        private SessionStateHandler() {}

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession session) {
            CameraExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession session) {
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession session) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                CameraExtensionSessionImpl.this.mCaptureSession = session;
                CameraExtensionSessionImpl.this.mStatsAggregator.commit(false);
                try {
                    CameraExtensionSessionImpl.this.finishPipelineInitialization();
                    CameraExtensionCharacteristics.initializeSession(
                            CameraExtensionSessionImpl.this.mInitializeHandler,
                            CameraExtensionSessionImpl.this.mExtensionType);
                } catch (RemoteException e) {
                    Log.e(
                            CameraExtensionSessionImpl.TAG,
                            "Failed to initialize session! Extension service does not respond!");
                    CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            }
        }
    }

    private class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {}

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            CameraExtensionSessionImpl.this.mHandler.post(
                    new Runnable() { // from class:
                        // android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            boolean status = true;
                            ArrayList<CaptureStageImpl> initialRequestList =
                                    CameraExtensionSessionImpl.this.compileInitialRequestList();
                            if (!initialRequestList.isEmpty()) {
                                try {
                                    CameraExtensionSessionImpl.this.setInitialCaptureRequest(
                                            initialRequestList,
                                            CameraExtensionSessionImpl.this
                                            .new InitialRequestHandler(
                                                    CameraExtensionSessionImpl.this
                                                            .mRepeatingRequestImageCallback));
                                } catch (CameraAccessException e) {
                                    Log.e(
                                            CameraExtensionSessionImpl.TAG,
                                            "Failed to initialize the initial capture request!");
                                    status = false;
                                }
                            } else {
                                try {
                                    CameraExtensionSessionImpl.this.setRepeatingRequest(
                                            CameraExtensionSessionImpl.this.mPreviewExtender
                                                    .getCaptureStage(),
                                            new PreviewRequestHandler(
                                                    CameraExtensionSessionImpl.this,
                                                    null,
                                                    null,
                                                    null,
                                                    CameraExtensionSessionImpl.this
                                                            .mRepeatingRequestImageCallback));
                                } catch (CameraAccessException | RemoteException e2) {
                                    Log.e(
                                            CameraExtensionSessionImpl.TAG,
                                            "Failed to initialize internal repeating request!");
                                    status = false;
                                }
                            }
                            if (!status) {
                                CameraExtensionSessionImpl.this.notifyConfigurationFailure();
                            }
                        }
                    });
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            CameraExtensionSessionImpl.this.mHandler.post(
                    new Runnable() { // from class:
                        // android.hardware.camera2.impl.CameraExtensionSessionImpl.InitializeSessionHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraExtensionSessionImpl.this.mCaptureSession.close();
                            Log.e(
                                    CameraExtensionSessionImpl.TAG,
                                    "Failed to initialize proxy service session! This can happen"
                                        + " when trying to configure multiple concurrent extension"
                                        + " sessions!");
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
        private HashMap<Integer, Pair<Image, TotalCaptureResult>> mCaptureStageMap =
                new HashMap<>();
        private LongSparseArray<Pair<Image, Integer>> mCapturePendingMap = new LongSparseArray<>();
        private ImageCallback mImageCallback = null;
        private boolean mCaptureFailed = false;
        private CaptureResultHandler mCaptureResultHandler = null;

        public BurstRequestHandler(
                CaptureRequest request,
                Executor executor,
                CameraExtensionSession.ExtensionCaptureCallback callbacks,
                HashMap<CaptureRequest, Integer> requestMap,
                CameraOutputImageCallback imageCallback) {
            this.mClientRequest = request;
            this.mExecutor = executor;
            this.mCallbacks = callbacks;
            this.mCaptureRequestMap = requestMap;
            this.mBurstImageCallback = imageCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyCaptureFailed() {
            if (!this.mCaptureFailed) {
                this.mCaptureFailed = true;
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.BurstRequestHandler.this
                                            .lambda$notifyCaptureFailed$0();
                                }
                            });
                    Binder.restoreCallingIdentity(ident);
                    for (Pair<Image, TotalCaptureResult> captureStage :
                            this.mCaptureStageMap.values()) {
                        if (captureStage.first != null) {
                            captureStage.first.close();
                        }
                    }
                    this.mCaptureStageMap.clear();
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(ident);
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyCaptureFailed$0() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(
                CameraCaptureSession session,
                CaptureRequest request,
                final long timestamp,
                long frameNumber) {
            boolean initialCallback = false;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (CameraExtensionSessionImpl.this.mImageProcessor != null
                        && this.mImageCallback == null) {
                    this.mImageCallback = new ImageCallback();
                    initialCallback = true;
                } else if (CameraExtensionSessionImpl.this.mImageProcessor == null) {
                    initialCallback = true;
                }
            }
            if (initialCallback) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.BurstRequestHandler.this
                                            .lambda$onCaptureStarted$1(timestamp);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            if (this.mBurstImageCallback != null && this.mImageCallback != null) {
                this.mBurstImageCallback.registerListener(
                        Long.valueOf(timestamp), this.mImageCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$1(long timestamp) {
            this.mCallbacks.onCaptureStarted(
                    CameraExtensionSessionImpl.this, this.mClientRequest, timestamp);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(
                CameraCaptureSession session,
                CaptureRequest request,
                Surface target,
                long frameNumber) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(
                CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            notifyCaptureFailed();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession session, final int sequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.BurstRequestHandler.this
                                        .lambda$onCaptureSequenceAborted$2(sequenceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$2(int sequenceId) {
            this.mCallbacks.onCaptureSequenceAborted(CameraExtensionSessionImpl.this, sequenceId);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(
                CameraCaptureSession session, final int sequenceId, long frameNumber) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.BurstRequestHandler.this
                                        .lambda$onCaptureSequenceCompleted$3(sequenceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$3(int sequenceId) {
            this.mCallbacks.onCaptureSequenceCompleted(CameraExtensionSessionImpl.this, sequenceId);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(
                CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            if (!this.mCaptureRequestMap.containsKey(request)) {
                Log.e(CameraExtensionSessionImpl.TAG, "Unexpected still capture request received!");
                return;
            }
            Integer stageId = this.mCaptureRequestMap.get(request);
            Long timestamp = (Long) result.get(CaptureResult.SENSOR_TIMESTAMP);
            if (timestamp != null) {
                if (CameraExtensionSessionImpl.this.mCaptureResultsSupported
                        && this.mCaptureResultHandler == null) {
                    this.mCaptureResultHandler =
                            CameraExtensionSessionImpl.this
                            .new CaptureResultHandler(
                                    this.mClientRequest,
                                    this.mExecutor,
                                    this.mCallbacks,
                                    result.getSequenceId());
                }
                if (CameraExtensionSessionImpl.this.mImageProcessor != null) {
                    if (this.mCapturePendingMap.indexOfKey(timestamp.longValue()) >= 0) {
                        Image img = this.mCapturePendingMap.get(timestamp.longValue()).first;
                        this.mCapturePendingMap.remove(timestamp.longValue());
                        this.mCaptureStageMap.put(stageId, new Pair<>(img, result));
                        checkAndFireBurstProcessing();
                        return;
                    }
                    this.mCapturePendingMap.put(timestamp.longValue(), new Pair<>(null, stageId));
                    this.mCaptureStageMap.put(stageId, new Pair<>(null, result));
                    return;
                }
                this.mCaptureRequestMap.clear();
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.BurstRequestHandler.this
                                            .lambda$onCaptureCompleted$4();
                                }
                            });
                    if (this.mCaptureResultHandler != null) {
                        this.mCaptureResultHandler.onCaptureCompleted(
                                timestamp.longValue(),
                                CameraExtensionSessionImpl.this.initializeFilteredResults(result));
                    }
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(CameraExtensionSessionImpl.TAG, "Capture result without valid sensor timestamp!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(
                    CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkAndFireBurstProcessing() {
            if (this.mCaptureRequestMap.size() == this.mCaptureStageMap.size()) {
                for (Pair<Image, TotalCaptureResult> captureStage :
                        this.mCaptureStageMap.values()) {
                    if (captureStage.first == null || captureStage.second == null) {
                        return;
                    }
                }
                this.mCaptureRequestMap.clear();
                this.mCapturePendingMap.clear();
                boolean processStatus = true;
                Byte jpegQuality = (Byte) this.mClientRequest.get(CaptureRequest.JPEG_QUALITY);
                Integer jpegOrientation =
                        (Integer) this.mClientRequest.get(CaptureRequest.JPEG_ORIENTATION);
                List<CaptureBundle> captureList =
                        CameraExtensionSessionImpl.initializeParcelable(
                                this.mCaptureStageMap, jpegOrientation, jpegQuality);
                try {
                    boolean isPostviewRequested =
                            this.mClientRequest.containsTarget(
                                    CameraExtensionSessionImpl.this.mClientPostviewSurface);
                    CameraExtensionSessionImpl.this.mImageProcessor.process(
                            captureList, this.mCaptureResultHandler, isPostviewRequested);
                } catch (RemoteException e) {
                    Log.e(
                            CameraExtensionSessionImpl.TAG,
                            "Failed to process multi-frame request! Extension service does not"
                                    + " respond!");
                    processStatus = false;
                }
                for (CaptureBundle bundle : captureList) {
                    bundle.captureImage.buffer.close();
                }
                captureList.clear();
                Iterator<Pair<Image, TotalCaptureResult>> it =
                        this.mCaptureStageMap.values().iterator();
                while (it.hasNext()) {
                    it.next().first.close();
                }
                this.mCaptureStageMap.clear();
                long ident = Binder.clearCallingIdentity();
                try {
                    if (processStatus) {
                        this.mExecutor.execute(
                                new Runnable() { // from class:
                                    // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CameraExtensionSessionImpl.BurstRequestHandler.this
                                                .lambda$checkAndFireBurstProcessing$5();
                                    }
                                });
                    } else {
                        this.mExecutor.execute(
                                new Runnable() { // from class:
                                    // android.hardware.camera2.impl.CameraExtensionSessionImpl$BurstRequestHandler$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CameraExtensionSessionImpl.BurstRequestHandler.this
                                                .lambda$checkAndFireBurstProcessing$6();
                                    }
                                });
                    }
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$5() {
            this.mCallbacks.onCaptureProcessStarted(
                    CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkAndFireBurstProcessing$6() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private class ImageCallback implements OnImageAvailableListener {
            private ImageCallback() {}

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long timestamp) {
                BurstRequestHandler.this.notifyCaptureFailed();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader reader, Image img) {
                if (BurstRequestHandler.this.mCaptureFailed) {
                    img.close();
                }
                long timestamp = img.getTimestamp();
                reader.detachImage(img);
                if (BurstRequestHandler.this.mCapturePendingMap.indexOfKey(timestamp) >= 0) {
                    Integer stageId =
                            (Integer)
                                    ((Pair)
                                                    BurstRequestHandler.this.mCapturePendingMap.get(
                                                            timestamp))
                                            .second;
                    BurstRequestHandler.this.mCapturePendingMap.remove(timestamp);
                    Pair<Image, TotalCaptureResult> captureStage =
                            (Pair) BurstRequestHandler.this.mCaptureStageMap.get(stageId);
                    if (captureStage != null) {
                        BurstRequestHandler.this.mCaptureStageMap.put(
                                stageId, new Pair(img, captureStage.second));
                        BurstRequestHandler.this.checkAndFireBurstProcessing();
                        return;
                    } else {
                        Log.e(
                                CameraExtensionSessionImpl.TAG,
                                "Capture stage: "
                                        + ((Pair)
                                                        BurstRequestHandler.this.mCapturePendingMap
                                                                .get(timestamp))
                                                .second
                                        + " is absent!");
                        return;
                    }
                }
                BurstRequestHandler.this.mCapturePendingMap.put(timestamp, new Pair(img, -1));
            }
        }
    }

    private class ImageLoopbackCallback implements OnImageAvailableListener {
        private ImageLoopbackCallback() {}

        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageDropped(long timestamp) {}

        @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader, Image img) {
            img.close();
        }
    }

    private class InitialRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraOutputImageCallback mImageCallback;

        public InitialRequestHandler(CameraOutputImageCallback imageCallback) {
            this.mImageCallback = imageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(
                CameraCaptureSession session,
                CaptureRequest request,
                long timestamp,
                long frameNumber) {
            this.mImageCallback.registerListener(
                    Long.valueOf(timestamp), new ImageLoopbackCallback());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession session, int sequenceId) {
            Log.e(CameraExtensionSessionImpl.TAG, "Initial capture request aborted!");
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(
                CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            Log.e(CameraExtensionSessionImpl.TAG, "Initial capture request failed!");
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(
                CameraCaptureSession session, int sequenceId, long frameNumber) {
            boolean status = true;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    CameraExtensionSessionImpl.this.setRepeatingRequest(
                            CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(),
                            new PreviewRequestHandler(
                                    CameraExtensionSessionImpl.this,
                                    null,
                                    null,
                                    null,
                                    this.mImageCallback));
                } catch (CameraAccessException | RemoteException e) {
                    Log.e(
                            CameraExtensionSessionImpl.TAG,
                            "Failed to start the internal repeating request!");
                    status = false;
                }
            }
            if (!status) {
                CameraExtensionSessionImpl.this.notifyConfigurationFailure();
            }
        }
    }

    private class CameraOutputImageCallback
            implements ImageReader.OnImageAvailableListener, Closeable {
        private final ImageReader mImageReader;
        private final boolean mPruneOlderBuffers;
        private HashMap<Long, Pair<Image, OnImageAvailableListener>> mImageListenerMap =
                new HashMap<>();
        private boolean mOutOfBuffers = false;

        CameraOutputImageCallback(ImageReader imageReader, boolean pruneOlderBuffers) {
            this.mImageReader = imageReader;
            this.mPruneOlderBuffers = pruneOlderBuffers;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                try {
                    try {
                        Image img = reader.acquireNextImage();
                        if (img == null) {
                            Log.e(CameraExtensionSessionImpl.TAG, "Invalid image!");
                            return;
                        }
                        Long timestamp = Long.valueOf(img.getTimestamp());
                        if (this.mImageListenerMap.containsKey(timestamp)) {
                            Pair<Image, OnImageAvailableListener> entry =
                                    this.mImageListenerMap.remove(timestamp);
                            if (entry.second != null) {
                                entry.second.onImageAvailable(reader, img);
                            } else {
                                Log.w(
                                        CameraExtensionSessionImpl.TAG,
                                        "Invalid image listener, dropping frame!");
                                img.close();
                            }
                        } else {
                            this.mImageListenerMap.put(timestamp, new Pair<>(img, null));
                        }
                        notifyDroppedImages(timestamp.longValue());
                    } catch (IllegalStateException e) {
                        Log.e(
                                CameraExtensionSessionImpl.TAG,
                                "Failed to acquire image, too many images pending!");
                        this.mOutOfBuffers = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private void notifyDroppedImages(long timestamp) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                Set<Long> timestamps = this.mImageListenerMap.keySet();
                ArrayList<Long> removedTs = new ArrayList<>();
                Iterator<Long> it = timestamps.iterator();
                while (it.hasNext()) {
                    long ts = it.next().longValue();
                    if (ts < timestamp) {
                        if (!this.mPruneOlderBuffers) {
                            Log.w(
                                    CameraExtensionSessionImpl.TAG,
                                    "Unexpected older image with ts: " + ts);
                        } else {
                            Log.e(CameraExtensionSessionImpl.TAG, "Dropped image with ts: " + ts);
                            Pair<Image, OnImageAvailableListener> entry =
                                    this.mImageListenerMap.get(Long.valueOf(ts));
                            if (entry.second != null) {
                                entry.second.onImageDropped(ts);
                            }
                            if (entry.first != null) {
                                entry.first.close();
                            }
                            removedTs.add(Long.valueOf(ts));
                        }
                    }
                }
                Iterator<Long> it2 = removedTs.iterator();
                while (it2.hasNext()) {
                    this.mImageListenerMap.remove(Long.valueOf(it2.next().longValue()));
                }
            }
        }

        public void registerListener(Long timestamp, OnImageAvailableListener listener) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageListenerMap.containsKey(timestamp)) {
                    Pair<Image, OnImageAvailableListener> entry =
                            this.mImageListenerMap.remove(timestamp);
                    if (entry.first != null) {
                        listener.onImageAvailable(this.mImageReader, entry.first);
                        if (this.mOutOfBuffers) {
                            this.mOutOfBuffers = false;
                            Log.w(CameraExtensionSessionImpl.TAG, "Out of buffers, retry!");
                            onImageAvailable(this.mImageReader);
                        }
                    } else {
                        Log.w(
                                CameraExtensionSessionImpl.TAG,
                                "No valid image for listener with ts: " + timestamp.longValue());
                    }
                } else {
                    this.mImageListenerMap.put(timestamp, new Pair<>(null, listener));
                }
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                for (Pair<Image, OnImageAvailableListener> entry :
                        this.mImageListenerMap.values()) {
                    if (entry.first != null) {
                        entry.first.close();
                    }
                }
                Iterator<Long> it = this.mImageListenerMap.keySet().iterator();
                while (it.hasNext()) {
                    long timestamp = it.next().longValue();
                    Pair<Image, OnImageAvailableListener> entry2 =
                            this.mImageListenerMap.get(Long.valueOf(timestamp));
                    if (entry2.second != null) {
                        entry2.second.onImageDropped(timestamp);
                    }
                }
                this.mImageListenerMap.clear();
            }
        }
    }

    private class CloseRequestHandler extends CameraCaptureSession.CaptureCallback {
        private final CameraOutputImageCallback mImageCallback;

        public CloseRequestHandler(CameraOutputImageCallback imageCallback) {
            this.mImageCallback = imageCallback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(
                CameraCaptureSession session,
                CaptureRequest request,
                long timestamp,
                long frameNumber) {
            this.mImageCallback.registerListener(
                    Long.valueOf(timestamp), new ImageLoopbackCallback());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CaptureResultHandler extends IProcessResultImpl.Stub {
        private final CameraExtensionSession.ExtensionCaptureCallback mCallbacks;
        private final CaptureRequest mClientRequest;
        private final Executor mExecutor;
        private final int mRequestId;

        public CaptureResultHandler(
                CaptureRequest clientRequest,
                Executor executor,
                CameraExtensionSession.ExtensionCaptureCallback listener,
                int requestId) {
            this.mClientRequest = clientRequest;
            this.mExecutor = executor;
            this.mCallbacks = listener;
            this.mRequestId = requestId;
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureCompleted(long shutterTimestamp, CameraMetadataNative result) {
            if (result == null) {
                Log.e(CameraExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            result.set(
                    (CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP,
                    (CaptureResult.Key<Long>) Long.valueOf(shutterTimestamp));
            final TotalCaptureResult totalResult =
                    new TotalCaptureResult(
                            CameraExtensionSessionImpl.this.mCameraDevice.getId(),
                            result,
                            this.mClientRequest,
                            this.mRequestId,
                            shutterTimestamp,
                            new ArrayList(),
                            CameraExtensionSessionImpl.this.mSessionId,
                            new PhysicalCaptureResultInfo[0]);
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.CaptureResultHandler.this
                                        .lambda$onCaptureCompleted$0(totalResult);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$0(TotalCaptureResult totalResult) {
            this.mCallbacks.onCaptureResultAvailable(
                    CameraExtensionSessionImpl.this, this.mClientRequest, totalResult);
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureProcessProgressed(final int progress) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.hardware.camera2.impl.CameraExtensionSessionImpl$CaptureResultHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraExtensionSessionImpl.CaptureResultHandler.this
                                        .lambda$onCaptureProcessProgressed$1(progress);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$1(int progress) {
            this.mCallbacks.onCaptureProcessProgressed(
                    CameraExtensionSessionImpl.this, this.mClientRequest, progress);
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

        public PreviewRequestHandler(
                CameraExtensionSessionImpl cameraExtensionSessionImpl,
                CaptureRequest clientRequest,
                Executor executor,
                CameraExtensionSession.ExtensionCaptureCallback listener,
                CameraOutputImageCallback imageCallback) {
            this(clientRequest, executor, listener, imageCallback, false);
        }

        public PreviewRequestHandler(
                CaptureRequest clientRequest,
                Executor executor,
                CameraExtensionSession.ExtensionCaptureCallback listener,
                CameraOutputImageCallback imageCallback,
                boolean singleCapture) {
            this.mImageCallback = null;
            this.mPendingResultMap = new LongSparseArray<>();
            this.mCaptureResultHandler = null;
            boolean z = false;
            this.mRequestUpdatedNeeded = false;
            this.mClientRequest = clientRequest;
            this.mExecutor = executor;
            this.mCallbacks = listener;
            if (this.mClientRequest != null && this.mExecutor != null && this.mCallbacks != null) {
                z = true;
            }
            this.mClientNotificationsEnabled = z;
            this.mRepeatingImageCallback = imageCallback;
            this.mSingleCapture = singleCapture;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(
                CameraCaptureSession cameraCaptureSession,
                CaptureRequest captureRequest,
                final long j,
                long j2) {
            OnImageAvailableListener imageLoopbackCallback;
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mImageCallback == null) {
                    byte b = 0;
                    if (CameraExtensionSessionImpl.this.mPreviewProcessorType == 1) {
                        if (this.mClientNotificationsEnabled) {
                            CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(
                                    CameraExtensionSessionImpl.this.mClientRepeatingRequestSurface,
                                    CameraExtensionSessionImpl.nativeGetSurfaceFormat(
                                            CameraExtensionSessionImpl.this
                                                    .mClientRepeatingRequestSurface));
                        } else {
                            CameraExtensionSessionImpl.this.mPreviewImageProcessor.onOutputSurface(
                                    null, -1);
                        }
                        this.mImageCallback = new ImageProcessCallback();
                    } else {
                        if (this.mClientNotificationsEnabled) {
                            imageLoopbackCallback =
                                    new ImageForwardCallback(
                                            CameraExtensionSessionImpl.this
                                                    .mRepeatingRequestImageWriter);
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
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.PreviewRequestHandler.this
                                            .lambda$onCaptureStarted$0(j);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            this.mRepeatingImageCallback.registerListener(Long.valueOf(j), this.mImageCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long timestamp) {
            this.mCallbacks.onCaptureStarted(
                    CameraExtensionSessionImpl.this, this.mClientRequest, timestamp);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession session, final int sequenceId) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled
                        && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.PreviewRequestHandler.this
                                            .lambda$onCaptureSequenceAborted$1(sequenceId);
                                }
                            });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            CameraExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$1(int sequenceId) {
            this.mCallbacks.onCaptureSequenceAborted(CameraExtensionSessionImpl.this, sequenceId);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(
                CameraCaptureSession session, final int sequenceId, long frameNumber) {
            synchronized (CameraExtensionSessionImpl.this.mInterfaceLock) {
                if (this.mRequestUpdatedNeeded && !this.mSingleCapture) {
                    this.mRequestUpdatedNeeded = false;
                    resumeInternalRepeatingRequest(false);
                } else if (CameraExtensionSessionImpl.this.mInternalRepeatingRequestEnabled
                        && !this.mSingleCapture) {
                    resumeInternalRepeatingRequest(true);
                }
            }
            if (this.mClientNotificationsEnabled) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda6
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.PreviewRequestHandler.this
                                            .lambda$onCaptureSequenceCompleted$2(sequenceId);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$2(int sequenceId) {
            this.mCallbacks.onCaptureSequenceCompleted(CameraExtensionSessionImpl.this, sequenceId);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(
                CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            if (this.mClientNotificationsEnabled) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CameraExtensionSessionImpl.PreviewRequestHandler.this
                                            .lambda$onCaptureFailed$3();
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0187 A[Catch: all -> 0x01bc, TRY_ENTER, TryCatch #6 {all -> 0x01bc, blocks: (B:31:0x0187, B:33:0x0195, B:35:0x019d, B:38:0x01ad), top: B:29:0x0185, outer: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x01ad A[Catch: all -> 0x01bc, TRY_LEAVE, TryCatch #6 {all -> 0x01bc, blocks: (B:31:0x0187, B:33:0x0195, B:35:0x019d, B:38:0x01ad), top: B:29:0x0185, outer: #2 }] */
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCaptureCompleted(
                android.hardware.camera2.CameraCaptureSession r12,
                android.hardware.camera2.CaptureRequest r13,
                android.hardware.camera2.TotalCaptureResult r14) {
            /*
                Method dump skipped, instructions count: 469
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.hardware.camera2.impl.CameraExtensionSessionImpl.PreviewRequestHandler.onCaptureCompleted(android.hardware.camera2.CameraCaptureSession,"
                        + " android.hardware.camera2.CaptureRequest,"
                        + " android.hardware.camera2.TotalCaptureResult):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$4() {
            this.mCallbacks.onCaptureProcessStarted(
                    CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$5() {
            this.mCallbacks.onCaptureFailed(CameraExtensionSessionImpl.this, this.mClientRequest);
        }

        private void resumeInternalRepeatingRequest(boolean internal) {
            try {
                if (internal) {
                    CameraExtensionSessionImpl.this.setRepeatingRequest(
                            CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(),
                            new PreviewRequestHandler(
                                    CameraExtensionSessionImpl.this,
                                    null,
                                    null,
                                    null,
                                    this.mRepeatingImageCallback));
                } else {
                    CameraExtensionSessionImpl.this.setRepeatingRequest(
                            CameraExtensionSessionImpl.this.mPreviewExtender.getCaptureStage(),
                            this,
                            this.mClientRequest);
                }
            } catch (CameraAccessException e) {
                Log.e(
                        CameraExtensionSessionImpl.TAG,
                        "Failed to resume internal repeating request!");
            } catch (RemoteException e2) {
                Log.e(
                        CameraExtensionSessionImpl.TAG,
                        "Failed to resume internal repeating request, extension service fails to"
                                + " respond!");
            } catch (IllegalStateException e3) {
                Log.w(
                        CameraExtensionSessionImpl.TAG,
                        "Failed to resume internal repeating request!");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Long calculatePruneThreshold(
                LongSparseArray<Pair<Image, TotalCaptureResult>> previewMap) {
            long oldestTimestamp = Long.MAX_VALUE;
            for (int idx = 0; idx < previewMap.size(); idx++) {
                Pair<Image, TotalCaptureResult> entry = previewMap.valueAt(idx);
                long timestamp = previewMap.keyAt(idx);
                if (entry.first != null && timestamp < oldestTimestamp) {
                    oldestTimestamp = timestamp;
                }
            }
            return Long.valueOf(oldestTimestamp == Long.MAX_VALUE ? 0L : oldestTimestamp);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void discardPendingRepeatingResults(
                int idx,
                LongSparseArray<Pair<Image, TotalCaptureResult>> previewMap,
                boolean notifyCurrentIndex) {
            if (idx < 0) {
                return;
            }
            for (int i = idx; i >= 0; i--) {
                if (previewMap.valueAt(i).first != null) {
                    previewMap.valueAt(i).first.close();
                } else if (this.mClientNotificationsEnabled
                        && previewMap.valueAt(i).second != null
                        && (i != idx || notifyCurrentIndex)) {
                    TotalCaptureResult result = previewMap.valueAt(i).second;
                    Long timestamp = (Long) result.get(CaptureResult.SENSOR_TIMESTAMP);
                    if (this.mCaptureResultHandler != null) {
                        this.mCaptureResultHandler.onCaptureCompleted(
                                timestamp.longValue(),
                                CameraExtensionSessionImpl.this.initializeFilteredResults(result));
                    }
                    Log.w(
                            CameraExtensionSessionImpl.TAG,
                            "Preview frame drop with timestamp: " + previewMap.keyAt(i));
                    long ident = Binder.clearCallingIdentity();
                    try {
                        this.mExecutor.execute(
                                new Runnable() { // from class:
                                    // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CameraExtensionSessionImpl.PreviewRequestHandler.this
                                                .lambda$discardPendingRepeatingResults$6();
                                    }
                                });
                    } finally {
                        Binder.restoreCallingIdentity(ident);
                    }
                }
                previewMap.removeAt(i);
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
            public void onImageDropped(long timestamp) {
                PreviewRequestHandler.this.discardPendingRepeatingResults(
                        PreviewRequestHandler.this.mPendingResultMap.indexOfKey(timestamp),
                        PreviewRequestHandler.this.mPendingResultMap,
                        true);
            }

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader reader, Image img) {
                if (img == null) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    this.mOutputWriter.queueInputImage(img);
                } catch (IllegalStateException e) {
                    Log.w(
                            CameraExtensionSessionImpl.TAG,
                            "Output surface likely abandoned, dropping buffer!");
                    img.close();
                } catch (RuntimeException e2) {
                    if (!e2.getClass().equals(RuntimeException.class)) {
                        throw e2;
                    }
                    Log.w(
                            CameraExtensionSessionImpl.TAG,
                            "Output surface likely abandoned, dropping buffer!");
                    img.close();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ImageProcessCallback implements OnImageAvailableListener {
            private ImageProcessCallback() {}

            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageDropped(long timestamp) {
                PreviewRequestHandler.this.discardPendingRepeatingResults(
                        PreviewRequestHandler.this.mPendingResultMap.indexOfKey(timestamp),
                        PreviewRequestHandler.this.mPendingResultMap,
                        true);
                PreviewRequestHandler.this.mPendingResultMap.put(timestamp, new Pair(null, null));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.hardware.camera2.impl.CameraExtensionSessionImpl.OnImageAvailableListener
            public void onImageAvailable(ImageReader reader, Image img) {
                if (PreviewRequestHandler.this.mPendingResultMap.size() + 1 >= 10) {
                    PreviewRequestHandler.this.discardPendingRepeatingResults(
                            PreviewRequestHandler.this.mPendingResultMap.indexOfKey(
                                    PreviewRequestHandler.this
                                            .calculatePruneThreshold(
                                                    PreviewRequestHandler.this.mPendingResultMap)
                                            .longValue()),
                            PreviewRequestHandler.this.mPendingResultMap,
                            true);
                }
                if (img == null) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Invalid preview buffer!");
                    return;
                }
                try {
                    reader.detachImage(img);
                    long timestamp = img.getTimestamp();
                    int idx = PreviewRequestHandler.this.mPendingResultMap.indexOfKey(timestamp);
                    if (idx >= 0) {
                        boolean processStatus = true;
                        ParcelImage parcelImage =
                                CameraExtensionSessionImpl.initializeParcelImage(img);
                        try {
                            try {
                                CameraExtensionSessionImpl.this.mPreviewImageProcessor.process(
                                        parcelImage,
                                        (TotalCaptureResult)
                                                ((Pair)
                                                                PreviewRequestHandler.this
                                                                        .mPendingResultMap.get(
                                                                        timestamp))
                                                        .second,
                                        PreviewRequestHandler.this.mCaptureResultHandler);
                            } catch (Throwable th) {
                                parcelImage.buffer.close();
                                img.close();
                                throw th;
                            }
                        } catch (RemoteException e) {
                            processStatus = false;
                            Log.e(
                                    CameraExtensionSessionImpl.TAG,
                                    "Extension service does not respond during processing, dropping"
                                            + " frame!");
                        }
                        parcelImage.buffer.close();
                        img.close();
                        PreviewRequestHandler.this.discardPendingRepeatingResults(
                                idx, PreviewRequestHandler.this.mPendingResultMap, false);
                        if (PreviewRequestHandler.this.mClientNotificationsEnabled) {
                            long ident = Binder.clearCallingIdentity();
                            try {
                                if (processStatus) {
                                    PreviewRequestHandler.this.mExecutor.execute(
                                            new Runnable() { // from class:
                                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    CameraExtensionSessionImpl.PreviewRequestHandler
                                                            .ImageProcessCallback.this
                                                            .lambda$onImageAvailable$0();
                                                }
                                            });
                                } else {
                                    PreviewRequestHandler.this.mExecutor.execute(
                                            new Runnable() { // from class:
                                                // android.hardware.camera2.impl.CameraExtensionSessionImpl$PreviewRequestHandler$ImageProcessCallback$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    CameraExtensionSessionImpl.PreviewRequestHandler
                                                            .ImageProcessCallback.this
                                                            .lambda$onImageAvailable$1();
                                                }
                                            });
                                }
                                return;
                            } finally {
                                Binder.restoreCallingIdentity(ident);
                            }
                        }
                        return;
                    }
                    PreviewRequestHandler.this.mPendingResultMap.put(
                            timestamp, new Pair(img, null));
                } catch (IllegalStateException e2) {
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    img.close();
                } catch (RuntimeException e3) {
                    if (!e3.getClass().equals(RuntimeException.class)) {
                        throw e3;
                    }
                    Log.e(CameraExtensionSessionImpl.TAG, "Failed to detach image!");
                    img.close();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$0() {
                PreviewRequestHandler.this.mCallbacks.onCaptureProcessStarted(
                        CameraExtensionSessionImpl.this, PreviewRequestHandler.this.mClientRequest);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onImageAvailable$1() {
                PreviewRequestHandler.this.mCallbacks.onCaptureFailed(
                        CameraExtensionSessionImpl.this, PreviewRequestHandler.this.mClientRequest);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CameraMetadataNative initializeFilteredResults(TotalCaptureResult result) {
        CameraMetadataNative captureResults = new CameraMetadataNative();
        for (CaptureResult.Key key : this.mSupportedResultKeys) {
            Object value = result.get(key);
            if (value != null) {
                captureResults.set(
                        (CaptureResult.Key<CaptureResult.Key>) key, (CaptureResult.Key) value);
            }
        }
        return captureResults;
    }

    private static Size findSmallestAspectMatchedSize(List<Size> sizes, Size arSize) {
        if (arSize.getHeight() == 0) {
            throw new IllegalArgumentException("Invalid input aspect ratio");
        }
        float targetAR = arSize.getWidth() / arSize.getHeight();
        Size ret = null;
        Size fallbackSize = null;
        for (Size sz : sizes) {
            if (fallbackSize == null) {
                fallbackSize = sz;
            }
            if (sz.getHeight() > 0
                    && (ret == null
                            || ret.getWidth() * ret.getHeight() < sz.getWidth() * sz.getHeight())) {
                float currentAR = sz.getWidth() / sz.getHeight();
                if (Math.abs(currentAR - targetAR) <= 0.01f) {
                    ret = sz;
                }
            }
        }
        if (ret == null) {
            Log.e(TAG, "AR matched size not found returning first size in list");
            Size ret2 = fallbackSize;
            return ret2;
        }
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelImage initializeParcelImage(Image img) {
        ParcelImage parcelImage = new ParcelImage();
        parcelImage.buffer = img.getHardwareBuffer();
        try {
            SyncFence fd = img.getFence();
            if (fd.isValid()) {
                parcelImage.fence = fd.getFdDup();
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to parcel buffer fence!");
        }
        parcelImage.width = img.getWidth();
        parcelImage.height = img.getHeight();
        parcelImage.format = img.getFormat();
        parcelImage.timestamp = img.getTimestamp();
        parcelImage.transform = img.getTransform();
        parcelImage.scalingMode = img.getScalingMode();
        parcelImage.planeCount = img.getPlaneCount();
        parcelImage.crop = img.getCropRect();
        return parcelImage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<CaptureBundle> initializeParcelable(
            HashMap<Integer, Pair<Image, TotalCaptureResult>> captureMap,
            Integer jpegOrientation,
            Byte jpegQuality) {
        ArrayList<CaptureBundle> ret = new ArrayList<>();
        for (Integer stageId : captureMap.keySet()) {
            Pair<Image, TotalCaptureResult> entry = captureMap.get(stageId);
            CaptureBundle bundle = new CaptureBundle();
            bundle.stage = stageId.intValue();
            bundle.captureImage = initializeParcelImage(entry.first);
            bundle.sequenceId = entry.second.getSequenceId();
            bundle.captureResult = entry.second.getNativeMetadata();
            if (jpegOrientation != null) {
                bundle.captureResult.set(
                        (CaptureResult.Key<CaptureResult.Key<Integer>>)
                                CaptureResult.JPEG_ORIENTATION,
                        (CaptureResult.Key<Integer>) jpegOrientation);
            }
            if (jpegQuality != null) {
                bundle.captureResult.set(
                        (CaptureResult.Key<CaptureResult.Key<Byte>>) CaptureResult.JPEG_QUALITY,
                        (CaptureResult.Key<Byte>) jpegQuality);
            }
            ret.add(bundle);
        }
        return ret;
    }
}
