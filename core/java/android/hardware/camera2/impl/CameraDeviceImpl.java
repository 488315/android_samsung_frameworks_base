package android.hardware.camera2.impl;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraOfflineSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.ICameraOfflineSession;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.CameraOfflineSessionImpl;
import android.hardware.camera2.params.ExtensionSessionConfiguration;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.MultiResolutionStreamConfigurationMap;
import android.hardware.camera2.params.MultiResolutionStreamInfo;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.SubmitInfo;
import android.hardware.camera2.utils.SurfaceUtils;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class CameraDeviceImpl extends CameraDevice implements IBinder.DeathRecipient {
    private static final long NANO_PER_SECOND = 1000000000;
    private static final int REQUEST_ID_NONE = -1;
    private final String TAG;
    private final int mAppTargetSdkVersion;
    private final String mCameraId;
    private final CameraCharacteristics mCharacteristics;
    private final Context mContext;
    private CameraAdvancedExtensionSessionImpl mCurrentAdvancedExtensionSession;
    private CameraExtensionSessionImpl mCurrentExtensionSession;
    private CameraCaptureSessionCore mCurrentSession;
    private final CameraDevice.StateCallback mDeviceCallback;
    private final Executor mDeviceExecutor;
    private int[] mFailedRepeatingRequestTypes;
    private CameraOfflineSessionImpl mOfflineSessionImpl;
    private ExecutorService mOfflineSwitchService;
    private final Map<String, CameraCharacteristics> mPhysicalIdsToChars;
    private ICameraDeviceUserWrapper mRemoteDevice;
    private int[] mRepeatingRequestTypes;
    private volatile StateCallbackKK mSessionStateCallback;
    private final int mTotalPartialCount;
    private final boolean DEBUG = false;
    private boolean mRemoteDeviceInit = false;
    final Object mInterfaceLock = new Object();
    private final CameraDeviceCallbacks mCallbacks = new CameraDeviceCallbacks();
    private final AtomicBoolean mClosing = new AtomicBoolean();
    private boolean mInError = false;
    private boolean mIdle = true;
    private SparseArray<CaptureCallbackHolder> mCaptureCallbackMap = new SparseArray<>();
    private HashMap<Integer, Integer> mBatchOutputMap = new HashMap<>();
    private int mRepeatingRequestId = -1;
    private int mFailedRepeatingRequestId = -1;
    private AbstractMap.SimpleEntry<Integer, InputConfiguration> mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, null);
    private final SparseArray<OutputConfiguration> mConfiguredOutputs = new SparseArray<>();
    private final HashSet<Integer> mOfflineSupport = new HashSet<>();
    private final List<RequestLastFrameNumbersHolder> mRequestLastFrameNumbersList = new ArrayList();
    private FrameNumberTracker mFrameNumberTracker = new FrameNumberTracker();
    private int mNextSessionId = 0;
    private final Runnable mCallOnOpened = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onOpened(CameraDeviceImpl.this);
                }
                CameraDeviceImpl.this.mDeviceCallback.onOpened(CameraDeviceImpl.this);
            }
        }
    };
    private final Runnable mCallOnUnconfigured = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onUnconfigured(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnActive = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.3
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onActive(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnBusy = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.4
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onBusy(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnClosed = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.5
        private boolean mClosedOnce = false;

        @Override // java.lang.Runnable
        public void run() {
            StateCallbackKK sessionCallback;
            if (this.mClosedOnce) {
                throw new AssertionError("Don't post #onClosed more than once");
            }
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
            }
            if (sessionCallback != null) {
                sessionCallback.onClosed(CameraDeviceImpl.this);
            }
            CameraDeviceImpl.this.mDeviceCallback.onClosed(CameraDeviceImpl.this);
            this.mClosedOnce = true;
        }
    };
    private final Runnable mCallOnIdle = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.6
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onIdle(CameraDeviceImpl.this);
                }
            }
        }
    };
    private final Runnable mCallOnDisconnected = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.7
        @Override // java.lang.Runnable
        public void run() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice == null) {
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback != null) {
                    sessionCallback.onDisconnected(CameraDeviceImpl.this);
                }
                CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
            }
        }
    };

    public CameraDeviceImpl(String cameraId, CameraDevice.StateCallback callback, Executor executor, CameraCharacteristics characteristics, Map<String, CameraCharacteristics> physicalIdsToChars, int appTargetSdkVersion, Context ctx) {
        if (cameraId == null || callback == null || executor == null || characteristics == null) {
            throw new IllegalArgumentException("Null argument given");
        }
        this.mCameraId = cameraId;
        this.mDeviceCallback = callback;
        this.mDeviceExecutor = executor;
        this.mCharacteristics = characteristics;
        this.mPhysicalIdsToChars = physicalIdsToChars;
        this.mAppTargetSdkVersion = appTargetSdkVersion;
        this.mContext = ctx;
        String tag = String.format("CameraDevice-JV-%s", cameraId);
        this.TAG = tag.length() > 23 ? tag.substring(0, 23) : tag;
        Integer partialCount = (Integer) characteristics.get(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT);
        if (partialCount == null) {
            this.mTotalPartialCount = 1;
        } else {
            this.mTotalPartialCount = partialCount.intValue();
        }
    }

    public CameraDeviceCallbacks getCallbacks() {
        return this.mCallbacks;
    }

    public void setRemoteDevice(ICameraDeviceUser remoteDevice) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInError) {
                return;
            }
            this.mRemoteDevice = new ICameraDeviceUserWrapper(remoteDevice);
            IBinder remoteDeviceBinder = remoteDevice.asBinder();
            if (remoteDeviceBinder != null) {
                try {
                    remoteDeviceBinder.linkToDeath(this, 0);
                } catch (RemoteException e) {
                    this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                    throw new CameraAccessException(2, "The camera device has encountered a serious error");
                }
            }
            this.mDeviceExecutor.execute(this.mCallOnOpened);
            this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
            this.mRemoteDeviceInit = true;
        }
    }

    public void setRemoteFailure(ServiceSpecificException failure) {
        int failureCode = 4;
        boolean failureIsError = true;
        switch (failure.errorCode) {
            case 4:
                failureIsError = false;
                break;
            case 5:
            case 9:
            default:
                Log.m96e(this.TAG, "Unexpected failure in opening camera device: " + failure.errorCode + failure.getMessage());
                break;
            case 6:
                failureCode = 3;
                break;
            case 7:
                failureCode = 1;
                break;
            case 8:
                failureCode = 2;
                break;
            case 10:
                failureCode = 4;
                break;
        }
        final int code = failureCode;
        final boolean isError = failureIsError;
        synchronized (this.mInterfaceLock) {
            this.mInError = true;
            this.mDeviceExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.8
                @Override // java.lang.Runnable
                public void run() {
                    if (isError) {
                        CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, code);
                    } else {
                        CameraDeviceImpl.this.mDeviceCallback.onDisconnected(CameraDeviceImpl.this);
                    }
                }
            });
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public String getId() {
        return this.mCameraId;
    }

    public void configureOutputs(List<Surface> outputs) throws CameraAccessException {
        ArrayList<OutputConfiguration> outputConfigs = new ArrayList<>(outputs.size());
        for (Surface s : outputs) {
            outputConfigs.add(new OutputConfiguration(s));
        }
        configureStreamsChecked(null, outputConfigs, 0, null, SystemClock.uptimeMillis());
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x009c, code lost:
    
        r18.mRemoteDevice.deleteStream(r18.mConfiguredInput.getKey().intValue());
        r18.mConfiguredInput = new java.util.AbstractMap.SimpleEntry<>(-1, null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean configureStreamsChecked(InputConfiguration inputConfig, List<OutputConfiguration> outputs, int operatingMode, CaptureRequest sessionParams, long createSessionStartTime) throws CameraAccessException {
        List<OutputConfiguration> outputs2 = outputs == null ? new ArrayList<>() : outputs;
        if (outputs2.size() == 0 && inputConfig != null) {
            throw new IllegalArgumentException("cannot configure an input stream without any output streams");
        }
        checkInputConfiguration(inputConfig);
        List<OutputConfiguration> outputs3 = applyExtensionStreamOption(outputs2, sessionParams);
        boolean success = false;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            HashSet<OutputConfiguration> addSet = new HashSet<>(outputs3);
            List<Integer> deleteList = new ArrayList<>();
            for (int i = 0; i < this.mConfiguredOutputs.size(); i++) {
                int streamId = this.mConfiguredOutputs.keyAt(i);
                OutputConfiguration outConfig = this.mConfiguredOutputs.valueAt(i);
                if (outputs3.contains(outConfig) && !outConfig.isDeferredConfiguration()) {
                    addSet.remove(outConfig);
                }
                deleteList.add(Integer.valueOf(streamId));
            }
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            stopRepeating();
            try {
                try {
                    waitUntilIdle();
                    this.mRemoteDevice.beginConfigure();
                    InputConfiguration currentInputConfig = this.mConfiguredInput.getValue();
                    if (inputConfig != currentInputConfig) {
                        if (inputConfig != null && inputConfig.equals(currentInputConfig)) {
                        }
                        if (inputConfig != null) {
                            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(Integer.valueOf(this.mRemoteDevice.createInputStream(inputConfig.getWidth(), inputConfig.getHeight(), inputConfig.getFormat(), inputConfig.isMultiResolution())), inputConfig);
                        }
                    }
                    for (Integer streamId2 : deleteList) {
                        this.mRemoteDevice.deleteStream(streamId2.intValue());
                        this.mConfiguredOutputs.delete(streamId2.intValue());
                    }
                    for (OutputConfiguration outConfig2 : outputs3) {
                        if (addSet.contains(outConfig2)) {
                            this.mConfiguredOutputs.put(this.mRemoteDevice.createStream(outConfig2), outConfig2);
                        }
                    }
                    int[] offlineStreamIds = sessionParams != null ? this.mRemoteDevice.endConfigure(operatingMode, sessionParams.getNativeCopy(), createSessionStartTime) : this.mRemoteDevice.endConfigure(operatingMode, null, createSessionStartTime);
                    this.mOfflineSupport.clear();
                    if (offlineStreamIds != null && offlineStreamIds.length > 0) {
                        int length = offlineStreamIds.length;
                        int i2 = 0;
                        while (i2 < length) {
                            int offlineStreamId = offlineStreamIds[i2];
                            int[] offlineStreamIds2 = offlineStreamIds;
                            this.mOfflineSupport.add(Integer.valueOf(offlineStreamId));
                            i2++;
                            offlineStreamIds = offlineStreamIds2;
                        }
                    }
                    success = true;
                } finally {
                    if (!success || outputs3.size() <= 0) {
                        this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
                    } else {
                        this.mDeviceExecutor.execute(this.mCallOnIdle);
                    }
                }
            } catch (CameraAccessException e) {
                if (e.getReason() == 4) {
                    throw new IllegalStateException("The camera is currently busy. You must wait until the previous operation completes.", e);
                }
                throw e;
            } catch (IllegalArgumentException e2) {
                Log.m102w(this.TAG, "Stream configuration failed due to: " + e2.getMessage());
                if (!success || outputs3.size() <= 0) {
                    this.mDeviceExecutor.execute(this.mCallOnUnconfigured);
                } else {
                    this.mDeviceExecutor.execute(this.mCallOnIdle);
                }
                return false;
            }
        }
        return success;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        List<OutputConfiguration> outConfigurations = new ArrayList<>(outputs.size());
        for (Surface surface : outputs) {
            outConfigurations.add(new OutputConfiguration(surface));
        }
        createCaptureSessionInternal(null, outConfigurations, callback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSessionByOutputConfigurations(List<OutputConfiguration> outputConfigurations, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        List<OutputConfiguration> currentOutputs = new ArrayList<>(outputConfigurations);
        createCaptureSessionInternal(null, currentOutputs, callback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSession(InputConfiguration inputConfig, List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (inputConfig == null) {
            throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        List<OutputConfiguration> outConfigurations = new ArrayList<>(outputs.size());
        for (Surface surface : outputs) {
            outConfigurations.add(new OutputConfiguration(surface));
        }
        createCaptureSessionInternal(inputConfig, outConfigurations, callback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createReprocessableCaptureSessionByConfigurations(InputConfiguration inputConfig, List<OutputConfiguration> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (inputConfig == null) {
            throw new IllegalArgumentException("inputConfig cannot be null when creating a reprocessable capture session");
        }
        if (outputs == null) {
            throw new IllegalArgumentException("Output configurations cannot be null when creating a reprocessable capture session");
        }
        List<OutputConfiguration> currentOutputs = new ArrayList<>();
        for (OutputConfiguration output : outputs) {
            currentOutputs.add(new OutputConfiguration(output));
        }
        createCaptureSessionInternal(inputConfig, currentOutputs, callback, checkAndWrapHandler(handler), 0, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createConstrainedHighSpeedCaptureSession(List<Surface> outputs, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        if (outputs == null || outputs.size() == 0 || outputs.size() > 2) {
            throw new IllegalArgumentException("Output surface list must not be null and the size must be no more than 2");
        }
        List<OutputConfiguration> outConfigurations = new ArrayList<>(outputs.size());
        for (Surface surface : outputs) {
            outConfigurations.add(new OutputConfiguration(surface));
        }
        createCaptureSessionInternal(null, outConfigurations, callback, checkAndWrapHandler(handler), 1, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCustomCaptureSession(InputConfiguration inputConfig, List<OutputConfiguration> outputs, int operatingMode, CameraCaptureSession.StateCallback callback, Handler handler) throws CameraAccessException {
        List<OutputConfiguration> currentOutputs = new ArrayList<>();
        for (OutputConfiguration output : outputs) {
            currentOutputs.add(new OutputConfiguration(output));
        }
        createCaptureSessionInternal(inputConfig, currentOutputs, callback, checkAndWrapHandler(handler), operatingMode, null);
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createCaptureSession(SessionConfiguration config) throws CameraAccessException {
        if (config == null) {
            throw new IllegalArgumentException("Invalid session configuration");
        }
        List<OutputConfiguration> outputConfigs = config.getOutputConfigurations();
        if (outputConfigs == null) {
            throw new IllegalArgumentException("Invalid output configurations");
        }
        if (config.getExecutor() == null) {
            throw new IllegalArgumentException("Invalid executor");
        }
        createCaptureSessionInternal(config.getInputConfiguration(), outputConfigs, config.getStateCallback(), config.getExecutor(), config.getSessionType(), config.getSessionParameters());
    }

    /* JADX WARN: Incorrect condition in loop: B:48:0x0094 */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fa A[Catch: all -> 0x0109, TryCatch #2 {all -> 0x0109, blocks: (B:12:0x001b, B:13:0x0022, B:73:0x0107, B:15:0x0023, B:17:0x0027, B:18:0x002a, B:20:0x002e, B:21:0x0031, B:23:0x0035, B:24:0x0038, B:26:0x003d, B:27:0x0042, B:29:0x0046, B:32:0x005c, B:37:0x0065, B:46:0x0080, B:47:0x0090, B:49:0x0096, B:57:0x00b0, B:58:0x00bf, B:59:0x00f6, B:61:0x00fa, B:62:0x0100, B:64:0x0103, B:65:0x00de), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0103 A[Catch: all -> 0x0109, TryCatch #2 {all -> 0x0109, blocks: (B:12:0x001b, B:13:0x0022, B:73:0x0107, B:15:0x0023, B:17:0x0027, B:18:0x002a, B:20:0x002e, B:21:0x0031, B:23:0x0035, B:24:0x0038, B:26:0x003d, B:27:0x0042, B:29:0x0046, B:32:0x005c, B:37:0x0065, B:46:0x0080, B:47:0x0090, B:49:0x0096, B:57:0x00b0, B:58:0x00bf, B:59:0x00f6, B:61:0x00fa, B:62:0x0100, B:64:0x0103, B:65:0x00de), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00de A[Catch: all -> 0x0109, TryCatch #2 {all -> 0x0109, blocks: (B:12:0x001b, B:13:0x0022, B:73:0x0107, B:15:0x0023, B:17:0x0027, B:18:0x002a, B:20:0x002e, B:21:0x0031, B:23:0x0035, B:24:0x0038, B:26:0x003d, B:27:0x0042, B:29:0x0046, B:32:0x005c, B:37:0x0065, B:46:0x0080, B:47:0x0090, B:49:0x0096, B:57:0x00b0, B:58:0x00bf, B:59:0x00f6, B:61:0x00fa, B:62:0x0100, B:64:0x0103, B:65:0x00de), top: B:3:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void createCaptureSessionInternal(InputConfiguration inputConfig, List<OutputConfiguration> outputConfigurations, CameraCaptureSession.StateCallback callback, Executor executor, int operatingMode, CaptureRequest sessionParams) throws CameraAccessException {
        boolean configureSuccess;
        CameraCaptureSessionCore newSession;
        long createSessionStartTime = SystemClock.uptimeMillis();
        synchronized (this.mInterfaceLock) {
            try {
                try {
                    checkIfCameraClosedOrInError();
                    boolean isConstrainedHighSpeed = operatingMode == 1;
                    if (isConstrainedHighSpeed && inputConfig != null) {
                        throw new IllegalArgumentException("Constrained high speed session doesn't support input configuration yet.");
                    }
                    CameraExtensionSessionImpl cameraExtensionSessionImpl = this.mCurrentExtensionSession;
                    if (cameraExtensionSessionImpl != null) {
                        cameraExtensionSessionImpl.commitStats();
                    }
                    CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = this.mCurrentAdvancedExtensionSession;
                    if (cameraAdvancedExtensionSessionImpl != null) {
                        cameraAdvancedExtensionSessionImpl.commitStats();
                    }
                    CameraCaptureSessionCore cameraCaptureSessionCore = this.mCurrentSession;
                    if (cameraCaptureSessionCore != null) {
                        cameraCaptureSessionCore.replaceSessionClose();
                    }
                    CameraExtensionSessionImpl cameraExtensionSessionImpl2 = this.mCurrentExtensionSession;
                    if (cameraExtensionSessionImpl2 != null) {
                        cameraExtensionSessionImpl2.release(false);
                        this.mCurrentExtensionSession = null;
                    }
                    CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl2 = this.mCurrentAdvancedExtensionSession;
                    if (cameraAdvancedExtensionSessionImpl2 != null) {
                        cameraAdvancedExtensionSessionImpl2.release(false);
                        this.mCurrentAdvancedExtensionSession = null;
                    }
                    CameraAccessException pendingException = null;
                    Surface input = null;
                    try {
                        boolean configureSuccess2 = configureStreamsChecked(inputConfig, outputConfigurations, operatingMode, sessionParams, createSessionStartTime);
                        if (configureSuccess2 && inputConfig != null) {
                            try {
                                input = this.mRemoteDevice.getInputSurface();
                            } catch (CameraAccessException e) {
                                e = e;
                                pendingException = e;
                                input = null;
                                configureSuccess = false;
                                if (isConstrainedHighSpeed) {
                                }
                                this.mCurrentSession = newSession;
                                if (pendingException == null) {
                                }
                            }
                        }
                        configureSuccess = configureSuccess2;
                    } catch (CameraAccessException e2) {
                        e = e2;
                    }
                    if (isConstrainedHighSpeed) {
                        int i = this.mNextSessionId;
                        this.mNextSessionId = i + 1;
                        newSession = new CameraCaptureSessionImpl(i, input, callback, executor, this, this.mDeviceExecutor, configureSuccess);
                    } else {
                        ArrayList<Surface> surfaces = new ArrayList<>(outputConfigurations.size());
                        Iterator<OutputConfiguration> it = outputConfigurations.iterator();
                        boolean skipSufaceSizeCheck = false;
                        while (skipSufaceSizeCheck) {
                            OutputConfiguration outConfig = it.next();
                            surfaces.add(outConfig.getSurface());
                            if (outConfig.getOption() > 0) {
                                skipSufaceSizeCheck = true;
                            }
                        }
                        if (!skipSufaceSizeCheck) {
                            StreamConfigurationMap config = (StreamConfigurationMap) getCharacteristics().get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                            SurfaceUtils.checkConstrainedHighSpeedSurfaces(surfaces, null, config);
                        }
                        int i2 = this.mNextSessionId;
                        this.mNextSessionId = i2 + 1;
                        newSession = new CameraConstrainedHighSpeedCaptureSessionImpl(i2, callback, executor, this, this.mDeviceExecutor, configureSuccess, this.mCharacteristics);
                    }
                    this.mCurrentSession = newSession;
                    if (pendingException == null) {
                        throw pendingException;
                    }
                    this.mSessionStateCallback = newSession.getDeviceStateCallback();
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfig) throws CameraAccessException, UnsupportedOperationException, IllegalArgumentException {
        boolean isSessionConfigurationSupported;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            isSessionConfigurationSupported = this.mRemoteDevice.isSessionConfigurationSupported(sessionConfig);
        }
        return isSessionConfigurationSupported;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void setParameters(String params) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mRemoteDevice.setParameters(params);
        }
    }

    public void setSessionListener(StateCallbackKK sessionCallback) {
        synchronized (this.mInterfaceLock) {
            this.mSessionStateCallback = sessionCallback;
        }
    }

    private void overrideEnableZsl(CameraMetadataNative request, boolean newValue) {
        Boolean enableZsl = (Boolean) request.get(CaptureRequest.CONTROL_ENABLE_ZSL);
        if (enableZsl == null) {
            return;
        }
        request.set((CaptureRequest.Key<CaptureRequest.Key<Boolean>>) CaptureRequest.CONTROL_ENABLE_ZSL, (CaptureRequest.Key<Boolean>) Boolean.valueOf(newValue));
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createCaptureRequest(int templateType, Set<String> physicalCameraIdSet) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (String physicalId : physicalCameraIdSet) {
                if (physicalId == getId()) {
                    throw new IllegalStateException("Physical id matches the logical id!");
                }
            }
            CameraMetadataNative templatedRequest = this.mRemoteDevice.createDefaultRequest(templateType);
            if (this.mAppTargetSdkVersion < 26 || templateType != 2) {
                overrideEnableZsl(templatedRequest, false);
            }
            builder = new CaptureRequest.Builder(templatedRequest, false, -1, getId(), physicalCameraIdSet);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createCaptureRequest(int templateType) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            CameraMetadataNative templatedRequest = this.mRemoteDevice.createDefaultRequest(templateType);
            if (this.mAppTargetSdkVersion < 26 || templateType != 2) {
                overrideEnableZsl(templatedRequest, false);
            }
            builder = new CaptureRequest.Builder(templatedRequest, false, -1, getId(), null);
        }
        return builder;
    }

    @Override // android.hardware.camera2.CameraDevice
    public CaptureRequest.Builder createReprocessCaptureRequest(TotalCaptureResult inputResult) throws CameraAccessException {
        CaptureRequest.Builder builder;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            CameraMetadataNative resultMetadata = new CameraMetadataNative(inputResult.getNativeCopy());
            builder = new CaptureRequest.Builder(resultMetadata, true, inputResult.getSessionId(), getId(), null);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 2);
        }
        return builder;
    }

    public void prepare(Surface surface) throws CameraAccessException {
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                }
                List<Surface> surfaces = this.mConfiguredOutputs.valueAt(i).getSurfaces();
                if (!surfaces.contains(surface)) {
                    i++;
                } else {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
            }
            if (streamId == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare(streamId);
        }
    }

    public void prepare(int maxCount, Surface surface) throws CameraAccessException {
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        if (maxCount <= 0) {
            throw new IllegalArgumentException("Invalid maxCount given: " + maxCount);
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                }
                if (surface != this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    i++;
                } else {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
            }
            if (streamId == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.prepare2(maxCount, streamId);
        }
    }

    public void updateOutputConfiguration(OutputConfiguration config) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                }
                if (config.getSurface() != this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    i++;
                } else {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
            }
            if (streamId == -1) {
                throw new IllegalArgumentException("Invalid output configuration");
            }
            this.mRemoteDevice.updateOutputConfiguration(streamId, config);
            this.mConfiguredOutputs.put(streamId, config);
        }
    }

    public CameraOfflineSession switchToOffline(Collection<Surface> offlineOutputs, Executor executor, CameraOfflineSession.CameraOfflineSessionCallback listener) throws CameraAccessException {
        CameraOfflineSessionImpl cameraOfflineSessionImpl;
        if (offlineOutputs.isEmpty()) {
            throw new IllegalArgumentException("Invalid offline surfaces!");
        }
        final HashSet<Integer> offlineStreamIds = new HashSet<>();
        SparseArray<OutputConfiguration> offlineConfiguredOutputs = new SparseArray<>();
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mOfflineSessionImpl != null) {
                throw new IllegalStateException("Switch to offline mode already in progress");
            }
            for (Surface surface : offlineOutputs) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    }
                    if (surface != this.mConfiguredOutputs.valueAt(i).getSurface()) {
                        i++;
                    } else {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        offlineConfiguredOutputs.append(streamId, this.mConfiguredOutputs.valueAt(i));
                        break;
                    }
                }
                if (streamId == -1) {
                    throw new IllegalArgumentException("Offline surface is not part of this session");
                }
                if (!this.mOfflineSupport.contains(Integer.valueOf(streamId))) {
                    throw new IllegalArgumentException("Surface: " + surface + " does not  support offline mode");
                }
                offlineStreamIds.add(Integer.valueOf(streamId));
            }
            stopRepeating();
            cameraOfflineSessionImpl = new CameraOfflineSessionImpl(this.mCameraId, this.mCharacteristics, executor, listener, offlineConfiguredOutputs, this.mConfiguredInput, this.mConfiguredOutputs, this.mFrameNumberTracker, this.mCaptureCallbackMap, this.mRequestLastFrameNumbersList);
            this.mOfflineSessionImpl = cameraOfflineSessionImpl;
            this.mOfflineSwitchService = Executors.newSingleThreadExecutor();
            this.mConfiguredOutputs.clear();
            this.mConfiguredInput = new AbstractMap.SimpleEntry<>(-1, null);
            this.mIdle = true;
            this.mCaptureCallbackMap = new SparseArray<>();
            this.mBatchOutputMap = new HashMap<>();
            this.mFrameNumberTracker = new FrameNumberTracker();
            this.mCurrentSession.closeWithoutDraining();
            this.mCurrentSession = null;
        }
        this.mOfflineSwitchService.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        ICameraDeviceUserWrapper iCameraDeviceUserWrapper = CameraDeviceImpl.this.mRemoteDevice;
                        CameraOfflineSessionImpl.CameraDeviceCallbacks callbacks = CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks();
                        HashSet hashSet = offlineStreamIds;
                        ICameraOfflineSession remoteOfflineSession = iCameraDeviceUserWrapper.switchToOffline(callbacks, Arrays.stream((Integer[]) hashSet.toArray(new Integer[hashSet.size()])).mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
                        CameraDeviceImpl.this.mOfflineSessionImpl.setRemoteSession(remoteOfflineSession);
                    } catch (CameraAccessException e) {
                        CameraDeviceImpl.this.mOfflineSessionImpl.notifyFailedSwitch();
                    }
                } finally {
                    CameraDeviceImpl.this.mOfflineSessionImpl = null;
                }
            }
        });
        return cameraOfflineSessionImpl;
    }

    public boolean supportsOfflineProcessing(Surface surface) {
        boolean contains;
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                }
                if (surface != this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    i++;
                } else {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
            }
            if (streamId == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            contains = this.mOfflineSupport.contains(Integer.valueOf(streamId));
        }
        return contains;
    }

    public void tearDown(Surface surface) throws CameraAccessException {
        if (surface == null) {
            throw new IllegalArgumentException("Surface is null");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int streamId = -1;
            int i = 0;
            while (true) {
                if (i >= this.mConfiguredOutputs.size()) {
                    break;
                }
                if (surface != this.mConfiguredOutputs.valueAt(i).getSurface()) {
                    i++;
                } else {
                    streamId = this.mConfiguredOutputs.keyAt(i);
                    break;
                }
            }
            if (streamId == -1) {
                throw new IllegalArgumentException("Surface is not part of this session");
            }
            this.mRemoteDevice.tearDown(streamId);
        }
    }

    public void finalizeOutputConfigs(List<OutputConfiguration> outputConfigs) throws CameraAccessException {
        if (outputConfigs == null || outputConfigs.size() == 0) {
            throw new IllegalArgumentException("deferred config is null or empty");
        }
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            for (OutputConfiguration config : outputConfigs) {
                int streamId = -1;
                int i = 0;
                while (true) {
                    if (i >= this.mConfiguredOutputs.size()) {
                        break;
                    }
                    if (!config.equals(this.mConfiguredOutputs.valueAt(i))) {
                        i++;
                    } else {
                        streamId = this.mConfiguredOutputs.keyAt(i);
                        break;
                    }
                }
                if (streamId == -1) {
                    throw new IllegalArgumentException("Deferred config is not part of this session");
                }
                if (config.getSurfaces().size() == 0) {
                    throw new IllegalArgumentException("The final config for stream " + streamId + " must have at least 1 surface");
                }
                this.mRemoteDevice.finalizeOutputConfigurations(streamId, config);
                this.mConfiguredOutputs.put(streamId, config);
            }
        }
    }

    public int capture(CaptureRequest request, CaptureCallback callback, Executor executor) throws CameraAccessException {
        List<CaptureRequest> requestList = new ArrayList<>();
        requestList.add(request);
        return submitCaptureRequest(requestList, callback, executor, false);
    }

    public int captureBurst(List<CaptureRequest> requests, CaptureCallback callback, Executor executor) throws CameraAccessException {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(requests, callback, executor, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEarlyTriggerSequenceCompleteLocked(final int requestId, long lastFrameNumber, int[] repeatingRequestTypes) {
        if (lastFrameNumber == -1) {
            int index = this.mCaptureCallbackMap.indexOfKey(requestId);
            final CaptureCallbackHolder holder = index >= 0 ? this.mCaptureCallbackMap.valueAt(index) : null;
            if (holder != null) {
                this.mCaptureCallbackMap.removeAt(index);
            }
            if (holder != null) {
                Runnable resultDispatch = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.10
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!CameraDeviceImpl.this.isClosed()) {
                            holder.getCallback().onCaptureSequenceAborted(CameraDeviceImpl.this, requestId);
                        }
                    }
                };
                long ident = Binder.clearCallingIdentity();
                try {
                    holder.getExecutor().execute(resultDispatch);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.m102w(this.TAG, String.format("did not register callback to request %d", Integer.valueOf(requestId)));
            return;
        }
        this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(requestId, lastFrameNumber, repeatingRequestTypes));
        checkAndFireSequenceComplete();
    }

    private int[] getRequestTypes(CaptureRequest[] requestArray) {
        int[] requestTypes = new int[requestArray.length];
        for (int i = 0; i < requestArray.length; i++) {
            requestTypes[i] = requestArray[i].getRequestType();
        }
        return requestTypes;
    }

    private boolean hasBatchedOutputs(List<CaptureRequest> requestList) {
        for (int i = 0; i < requestList.size(); i++) {
            CaptureRequest request = requestList.get(i);
            if (!request.isPartOfCRequestList()) {
                return false;
            }
            if (i == 0) {
                Collection<Surface> targets = request.getTargets();
                if (targets.size() != 2) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTracker(int requestId, long frameNumber, int requestType, CaptureResult result, boolean isPartialResult) {
        if (this.mBatchOutputMap.containsKey(Integer.valueOf(requestId))) {
            int requestCount = this.mBatchOutputMap.get(Integer.valueOf(requestId)).intValue();
            for (int i = 0; i < requestCount; i++) {
                this.mFrameNumberTracker.updateTracker(frameNumber - ((requestCount - 1) - i), result, isPartialResult, requestType);
            }
            return;
        }
        this.mFrameNumberTracker.updateTracker(frameNumber, result, isPartialResult, requestType);
    }

    private int submitCaptureRequest(List<CaptureRequest> requestList, CaptureCallback callback, Executor executor, boolean repeating) throws CameraAccessException {
        Executor executor2 = checkExecutor(executor, callback);
        synchronized (this.mInterfaceLock) {
            try {
                try {
                    checkIfCameraClosedOrInError();
                    for (CaptureRequest request : requestList) {
                        try {
                            if (request.getTargets().isEmpty()) {
                                throw new IllegalArgumentException("Each request must have at least one Surface target");
                            }
                            for (Surface surface : request.getTargets()) {
                                if (surface == null) {
                                    throw new IllegalArgumentException("Null Surface targets are not allowed");
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                    if (repeating) {
                        stopRepeating();
                    }
                    CaptureRequest[] requestArray = (CaptureRequest[]) requestList.toArray(new CaptureRequest[requestList.size()]);
                    for (CaptureRequest request2 : requestArray) {
                        request2.convertSurfaceToStreamId(this.mConfiguredOutputs);
                    }
                    SubmitInfo requestInfo = this.mRemoteDevice.submitRequestList(requestArray, repeating);
                    for (CaptureRequest request3 : requestArray) {
                        request3.recoverStreamIdToSurface();
                    }
                    boolean hasBatchedOutputs = hasBatchedOutputs(requestList);
                    if (hasBatchedOutputs) {
                        int requestCount = requestList.size();
                        this.mBatchOutputMap.put(Integer.valueOf(requestInfo.getRequestId()), Integer.valueOf(requestCount));
                    }
                    if (callback != null) {
                        this.mCaptureCallbackMap.put(requestInfo.getRequestId(), new CaptureCallbackHolder(callback, requestList, executor2, repeating, this.mNextSessionId - 1));
                    }
                    if (repeating) {
                        int i = this.mRepeatingRequestId;
                        if (i != -1) {
                            checkEarlyTriggerSequenceCompleteLocked(i, requestInfo.getLastFrameNumber(), this.mRepeatingRequestTypes);
                        }
                        this.mRepeatingRequestId = requestInfo.getRequestId();
                        this.mRepeatingRequestTypes = getRequestTypes(requestArray);
                    } else {
                        this.mRequestLastFrameNumbersList.add(new RequestLastFrameNumbersHolder(requestList, requestInfo));
                    }
                    if (this.mIdle) {
                        this.mDeviceExecutor.execute(this.mCallOnActive);
                    }
                    this.mIdle = false;
                    return requestInfo.getRequestId();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    public int setRepeatingRequest(CaptureRequest request, CaptureCallback callback, Executor executor) throws CameraAccessException {
        List<CaptureRequest> requestList = new ArrayList<>();
        requestList.add(request);
        return submitCaptureRequest(requestList, callback, executor, true);
    }

    public int setRepeatingBurst(List<CaptureRequest> requests, CaptureCallback callback, Executor executor) throws CameraAccessException {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("At least one request must be given");
        }
        return submitCaptureRequest(requests, callback, executor, true);
    }

    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            int requestId = this.mRepeatingRequestId;
            if (requestId != -1) {
                this.mRepeatingRequestId = -1;
                this.mFailedRepeatingRequestId = -1;
                int[] requestTypes = this.mRepeatingRequestTypes;
                this.mRepeatingRequestTypes = null;
                this.mFailedRepeatingRequestTypes = null;
                try {
                    long lastFrameNumber = this.mRemoteDevice.cancelRequest(requestId);
                    checkEarlyTriggerSequenceCompleteLocked(requestId, lastFrameNumber, requestTypes);
                } catch (IllegalArgumentException e) {
                    this.mFailedRepeatingRequestId = requestId;
                    this.mFailedRepeatingRequestTypes = requestTypes;
                }
            }
        }
    }

    private void waitUntilIdle() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            if (this.mRepeatingRequestId != -1) {
                throw new IllegalStateException("Active repeating request ongoing");
            }
            this.mRemoteDevice.waitUntilIdle();
        }
    }

    public void flush() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mDeviceExecutor.execute(this.mCallOnBusy);
            if (this.mIdle) {
                this.mDeviceExecutor.execute(this.mCallOnIdle);
                Log.m102w(this.TAG, "flush - transit busy->idle immediately, don't actually flush");
                return;
            }
            long lastFrameNumber = this.mRemoteDevice.flush();
            int i = this.mRepeatingRequestId;
            if (i != -1) {
                checkEarlyTriggerSequenceCompleteLocked(i, lastFrameNumber, this.mRepeatingRequestTypes);
                this.mRepeatingRequestId = -1;
                this.mRepeatingRequestTypes = null;
            }
        }
    }

    @Override // android.hardware.camera2.CameraDevice, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            if (this.mClosing.getAndSet(true)) {
                return;
            }
            ExecutorService executorService = this.mOfflineSwitchService;
            if (executorService != null) {
                executorService.shutdownNow();
                this.mOfflineSwitchService = null;
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl != null) {
                cameraExtensionSessionImpl.commitStats();
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl != null) {
                cameraAdvancedExtensionSessionImpl.commitStats();
            }
            ICameraDeviceUserWrapper iCameraDeviceUserWrapper = this.mRemoteDevice;
            if (iCameraDeviceUserWrapper != null) {
                iCameraDeviceUserWrapper.disconnect();
                this.mRemoteDevice.unlinkToDeath(this, 0);
            }
            CameraExtensionSessionImpl cameraExtensionSessionImpl2 = this.mCurrentExtensionSession;
            if (cameraExtensionSessionImpl2 != null) {
                cameraExtensionSessionImpl2.release(true);
                this.mCurrentExtensionSession = null;
            }
            CameraAdvancedExtensionSessionImpl cameraAdvancedExtensionSessionImpl2 = this.mCurrentAdvancedExtensionSession;
            if (cameraAdvancedExtensionSessionImpl2 != null) {
                cameraAdvancedExtensionSessionImpl2.release(true);
                this.mCurrentAdvancedExtensionSession = null;
            }
            if (this.mRemoteDevice != null || this.mInError) {
                this.mDeviceExecutor.execute(this.mCallOnClosed);
            }
            this.mRemoteDevice = null;
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private boolean checkInputConfigurationWithStreamConfigurationsAs(InputConfiguration inputConfig, StreamConfigurationMap configMap) {
        int[] inputFormats = configMap.getInputFormats();
        boolean validFormat = false;
        int inputFormat = inputConfig.getFormat();
        for (int format : inputFormats) {
            if (format == inputFormat) {
                validFormat = true;
            }
        }
        if (!validFormat) {
            return false;
        }
        boolean validSize = false;
        Size[] inputSizes = configMap.getInputSizes(inputFormat);
        for (Size s : inputSizes) {
            if (inputConfig.getWidth() == s.getWidth() && inputConfig.getHeight() == s.getHeight()) {
                validSize = true;
            }
        }
        return validSize;
    }

    private boolean checkInputConfigurationWithStreamConfigurations(InputConfiguration inputConfig, boolean maxResolution) {
        CameraCharacteristics.Key<StreamConfigurationMap> ck = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP;
        if (maxResolution) {
            ck = CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP_MAXIMUM_RESOLUTION;
        }
        StreamConfigurationMap configMap = (StreamConfigurationMap) this.mCharacteristics.get(ck);
        if (configMap != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfig, configMap)) {
            return true;
        }
        for (Map.Entry<String, CameraCharacteristics> entry : this.mPhysicalIdsToChars.entrySet()) {
            StreamConfigurationMap configMap2 = (StreamConfigurationMap) entry.getValue().get(ck);
            if (configMap2 != null && checkInputConfigurationWithStreamConfigurationsAs(inputConfig, configMap2)) {
                return true;
            }
        }
        return false;
    }

    private void checkInputConfiguration(InputConfiguration inputConfig) {
        if (inputConfig == null) {
            return;
        }
        int inputFormat = inputConfig.getFormat();
        if (inputConfig.isMultiResolution()) {
            MultiResolutionStreamConfigurationMap configMap = (MultiResolutionStreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_CONFIGURATION_MAP);
            int[] inputFormats = configMap.getInputFormats();
            boolean validFormat = false;
            for (int format : inputFormats) {
                if (format == inputFormat) {
                    validFormat = true;
                }
            }
            if (!validFormat) {
                throw new IllegalArgumentException("multi-resolution input format " + inputFormat + " is not valid");
            }
            boolean validSize = false;
            Collection<MultiResolutionStreamInfo> inputStreamInfo = configMap.getInputInfo(inputFormat);
            for (MultiResolutionStreamInfo info : inputStreamInfo) {
                if (inputConfig.getWidth() == info.getWidth() && inputConfig.getHeight() == info.getHeight()) {
                    validSize = true;
                }
            }
            if (!validSize) {
                throw new IllegalArgumentException("Multi-resolution input size " + inputConfig.getWidth() + "x" + inputConfig.getHeight() + " is not valid");
            }
            return;
        }
        if (!checkInputConfigurationWithStreamConfigurations(inputConfig, false) && !checkInputConfigurationWithStreamConfigurations(inputConfig, true)) {
            throw new IllegalArgumentException("Input config with format " + inputFormat + " and size " + inputConfig.getWidth() + "x" + inputConfig.getHeight() + " not supported by camera id " + this.mCameraId);
        }
    }

    public static abstract class StateCallbackKK extends CameraDevice.StateCallback {
        public void onUnconfigured(CameraDevice camera) {
        }

        public void onActive(CameraDevice camera) {
        }

        public void onBusy(CameraDevice camera) {
        }

        public void onIdle(CameraDevice camera) {
        }

        public void onRequestQueueEmpty() {
        }

        public void onSurfacePrepared(Surface surface) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndFireSequenceComplete() {
        long completedFrameNumber;
        long completedFrameNumber2 = this.mFrameNumberTracker.getCompletedFrameNumber();
        long completedReprocessFrameNumber = this.mFrameNumberTracker.getCompletedReprocessFrameNumber();
        long completedZslStillFrameNumber = this.mFrameNumberTracker.getCompletedZslStillFrameNumber();
        Iterator<RequestLastFrameNumbersHolder> iter = this.mRequestLastFrameNumbersList.iterator();
        while (iter.hasNext()) {
            final RequestLastFrameNumbersHolder requestLastFrameNumbers = iter.next();
            final int requestId = requestLastFrameNumbers.getRequestId();
            if (this.mRemoteDevice == null) {
                Log.m102w(this.TAG, "Camera closed while checking sequences");
                return;
            }
            if (requestLastFrameNumbers.isSequenceCompleted()) {
                completedFrameNumber = completedFrameNumber2;
            } else {
                long lastRegularFrameNumber = requestLastFrameNumbers.getLastRegularFrameNumber();
                long lastReprocessFrameNumber = requestLastFrameNumbers.getLastReprocessFrameNumber();
                long lastZslStillFrameNumber = requestLastFrameNumbers.getLastZslStillFrameNumber();
                if (lastRegularFrameNumber <= completedFrameNumber2 && lastReprocessFrameNumber <= completedReprocessFrameNumber && lastZslStillFrameNumber <= completedZslStillFrameNumber) {
                    requestLastFrameNumbers.markSequenceCompleted();
                }
                completedFrameNumber = completedFrameNumber2;
                int index = this.mCaptureCallbackMap.indexOfKey(requestId);
                final CaptureCallbackHolder holder = index >= 0 ? this.mCaptureCallbackMap.valueAt(index) : null;
                if (holder != null && requestLastFrameNumbers.isSequenceCompleted()) {
                    Runnable resultDispatch = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.11
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!CameraDeviceImpl.this.isClosed()) {
                                holder.getCallback().onCaptureSequenceCompleted(CameraDeviceImpl.this, requestId, requestLastFrameNumbers.getLastFrameNumber());
                            }
                        }
                    };
                    long ident = Binder.clearCallingIdentity();
                    try {
                        try {
                            holder.getExecutor().execute(resultDispatch);
                            Binder.restoreCallingIdentity(ident);
                        } catch (Throwable th) {
                            th = th;
                            Binder.restoreCallingIdentity(ident);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            }
            if (requestLastFrameNumbers.isSequenceCompleted() && requestLastFrameNumbers.isInflightCompleted()) {
                int index2 = this.mCaptureCallbackMap.indexOfKey(requestId);
                if (index2 >= 0) {
                    this.mCaptureCallbackMap.removeAt(index2);
                }
                iter.remove();
            }
            completedFrameNumber2 = completedFrameNumber;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCompletedCallbackHolderLocked(long lastCompletedRegularFrameNumber, long lastCompletedReprocessFrameNumber, long lastCompletedZslStillFrameNumber) {
        Iterator<RequestLastFrameNumbersHolder> iter = this.mRequestLastFrameNumbersList.iterator();
        while (iter.hasNext()) {
            RequestLastFrameNumbersHolder requestLastFrameNumbers = iter.next();
            int requestId = requestLastFrameNumbers.getRequestId();
            if (this.mRemoteDevice == null) {
                Log.m102w(this.TAG, "Camera closed while removing completed callback holders");
                return;
            }
            long lastRegularFrameNumber = requestLastFrameNumbers.getLastRegularFrameNumber();
            long lastReprocessFrameNumber = requestLastFrameNumbers.getLastReprocessFrameNumber();
            long lastZslStillFrameNumber = requestLastFrameNumbers.getLastZslStillFrameNumber();
            if (lastRegularFrameNumber <= lastCompletedRegularFrameNumber && lastReprocessFrameNumber <= lastCompletedReprocessFrameNumber && lastZslStillFrameNumber <= lastCompletedZslStillFrameNumber) {
                if (requestLastFrameNumbers.isSequenceCompleted()) {
                    int index = this.mCaptureCallbackMap.indexOfKey(requestId);
                    if (index >= 0) {
                        this.mCaptureCallbackMap.removeAt(index);
                    }
                    iter.remove();
                } else {
                    requestLastFrameNumbers.markInflightCompleted();
                }
            }
        }
    }

    public void onDeviceError(int errorCode, CaptureResultExtras resultExtras) {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null && this.mRemoteDeviceInit) {
                return;
            }
            CameraOfflineSessionImpl cameraOfflineSessionImpl = this.mOfflineSessionImpl;
            if (cameraOfflineSessionImpl != null) {
                cameraOfflineSessionImpl.getCallbacks().onDeviceError(errorCode, resultExtras);
                return;
            }
            switch (errorCode) {
                case 0:
                    long ident = Binder.clearCallingIdentity();
                    try {
                        this.mDeviceExecutor.execute(this.mCallOnDisconnected);
                        Binder.restoreCallingIdentity(ident);
                        return;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(ident);
                        throw th;
                    }
                case 1:
                    scheduleNotifyError(4);
                    return;
                case 2:
                default:
                    Log.m96e(this.TAG, "Unknown error from camera device: " + errorCode);
                    scheduleNotifyError(5);
                    return;
                case 3:
                case 4:
                case 5:
                    onCaptureErrorLocked(errorCode, resultExtras);
                    return;
                case 6:
                    scheduleNotifyError(3);
                    return;
            }
        }
    }

    private void scheduleNotifyError(int code) {
        this.mInError = true;
        long ident = Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.CameraDeviceImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((CameraDeviceImpl) obj).notifyError(((Integer) obj2).intValue());
                }
            }, this, Integer.valueOf(code)).recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(ident);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int code) {
        if (!isClosed()) {
            this.mDeviceCallback.onError(this, code);
        }
    }

    private void onCaptureErrorLocked(int errorCode, CaptureResultExtras resultExtras) {
        long ident;
        int requestId = resultExtras.getRequestId();
        int subsequenceId = resultExtras.getSubsequenceId();
        final long frameNumber = resultExtras.getFrameNumber();
        String errorPhysicalCameraId = resultExtras.getErrorPhysicalCameraId();
        final CaptureCallbackHolder holder = this.mCaptureCallbackMap.get(requestId);
        if (holder == null) {
            Log.m96e(this.TAG, String.format("Receive capture error on unknown request ID %d", Integer.valueOf(requestId)));
            return;
        }
        final CaptureRequest request = holder.getRequest(subsequenceId);
        if (errorCode == 5) {
            OutputConfiguration config = this.mConfiguredOutputs.get(resultExtras.getErrorStreamId());
            if (config == null) {
                Log.m100v(this.TAG, String.format("Stream %d has been removed. Skipping buffer lost callback", Integer.valueOf(resultExtras.getErrorStreamId())));
                return;
            }
            for (final Surface surface : config.getSurfaces()) {
                if (request.containsTarget(surface)) {
                    Runnable failureDispatch = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.12
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!CameraDeviceImpl.this.isClosed()) {
                                holder.getCallback().onCaptureBufferLost(CameraDeviceImpl.this, request, surface, frameNumber);
                            }
                        }
                    };
                    ident = Binder.clearCallingIdentity();
                    try {
                        holder.getExecutor().execute(failureDispatch);
                    } finally {
                    }
                }
            }
            return;
        }
        int i = 0;
        boolean mayHaveBuffers = errorCode == 4;
        CameraCaptureSessionCore cameraCaptureSessionCore = this.mCurrentSession;
        if (cameraCaptureSessionCore != null && cameraCaptureSessionCore.isAborting()) {
            i = 1;
        }
        int reason = i;
        final CaptureFailure failure = new CaptureFailure(request, reason, mayHaveBuffers, requestId, frameNumber, errorPhysicalCameraId);
        Runnable failureDispatch2 = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.13
            @Override // java.lang.Runnable
            public void run() {
                if (!CameraDeviceImpl.this.isClosed()) {
                    holder.getCallback().onCaptureFailed(CameraDeviceImpl.this, request, failure);
                }
            }
        };
        if (errorPhysicalCameraId == null) {
            if (this.mBatchOutputMap.containsKey(Integer.valueOf(requestId))) {
                int i2 = 0;
                while (i2 < this.mBatchOutputMap.get(Integer.valueOf(requestId)).intValue()) {
                    this.mFrameNumberTracker.updateTracker(frameNumber - (subsequenceId - i2), true, request.getRequestType());
                    i2++;
                    failure = failure;
                }
            } else {
                this.mFrameNumberTracker.updateTracker(frameNumber, true, request.getRequestType());
            }
            checkAndFireSequenceComplete();
        }
        ident = Binder.clearCallingIdentity();
        try {
            holder.getExecutor().execute(failureDispatch2);
        } finally {
        }
    }

    public void onDeviceIdle() {
        synchronized (this.mInterfaceLock) {
            if (this.mRemoteDevice == null) {
                return;
            }
            CameraOfflineSessionImpl cameraOfflineSessionImpl = this.mOfflineSessionImpl;
            if (cameraOfflineSessionImpl != null) {
                cameraOfflineSessionImpl.getCallbacks().onDeviceIdle();
                return;
            }
            removeCompletedCallbackHolderLocked(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
            if (!this.mIdle) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mDeviceExecutor.execute(this.mCallOnIdle);
                    Binder.restoreCallingIdentity(ident);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(ident);
                    throw th;
                }
            }
            this.mIdle = true;
        }
    }

    public class CameraDeviceCallbacks extends ICameraDeviceCallbacks.Stub {
        public CameraDeviceCallbacks() {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceError(int errorCode, CaptureResultExtras resultExtras) {
            CameraDeviceImpl.this.onDeviceError(errorCode, resultExtras);
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long lastFrameNumber, int repeatingRequestId) {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mRemoteDevice != null && CameraDeviceImpl.this.mRepeatingRequestId != -1) {
                    if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                        CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRepeatingRequestError(lastFrameNumber, repeatingRequestId);
                        return;
                    }
                    CameraDeviceImpl cameraDeviceImpl = CameraDeviceImpl.this;
                    cameraDeviceImpl.checkEarlyTriggerSequenceCompleteLocked(cameraDeviceImpl.mRepeatingRequestId, lastFrameNumber, CameraDeviceImpl.this.mRepeatingRequestTypes);
                    if (CameraDeviceImpl.this.mRepeatingRequestId == repeatingRequestId) {
                        CameraDeviceImpl.this.mRepeatingRequestId = -1;
                        CameraDeviceImpl.this.mRepeatingRequestTypes = null;
                    }
                    return;
                }
                if (CameraDeviceImpl.this.mFailedRepeatingRequestId == repeatingRequestId && CameraDeviceImpl.this.mFailedRepeatingRequestTypes != null && CameraDeviceImpl.this.mRemoteDevice != null) {
                    Log.m100v(CameraDeviceImpl.this.TAG, "Resuming stop of failed repeating request with id: " + CameraDeviceImpl.this.mFailedRepeatingRequestId);
                    CameraDeviceImpl cameraDeviceImpl2 = CameraDeviceImpl.this;
                    cameraDeviceImpl2.checkEarlyTriggerSequenceCompleteLocked(cameraDeviceImpl2.mFailedRepeatingRequestId, lastFrameNumber, CameraDeviceImpl.this.mFailedRepeatingRequestTypes);
                    CameraDeviceImpl.this.mFailedRepeatingRequestId = -1;
                    CameraDeviceImpl.this.mFailedRepeatingRequestTypes = null;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() {
            CameraDeviceImpl.this.onDeviceIdle();
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(final CaptureResultExtras resultExtras, final long timestamp) {
            int requestId = resultExtras.getRequestId();
            final long frameNumber = resultExtras.getFrameNumber();
            long lastCompletedRegularFrameNumber = resultExtras.getLastCompletedRegularFrameNumber();
            long lastCompletedReprocessFrameNumber = resultExtras.getLastCompletedReprocessFrameNumber();
            long lastCompletedZslFrameNumber = resultExtras.getLastCompletedZslFrameNumber();
            final boolean hasReadoutTimestamp = resultExtras.hasReadoutTimestamp();
            final long readoutTimestamp = resultExtras.getReadoutTimestamp();
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                try {
                    try {
                        if (CameraDeviceImpl.this.mRemoteDevice == null) {
                            return;
                        }
                        if (CameraDeviceImpl.this.mOfflineSessionImpl == null) {
                            CameraDeviceImpl.this.removeCompletedCallbackHolderLocked(lastCompletedRegularFrameNumber, lastCompletedReprocessFrameNumber, lastCompletedZslFrameNumber);
                            final CaptureCallbackHolder holder = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                            if (holder == null) {
                                return;
                            }
                            if (CameraDeviceImpl.this.isClosed()) {
                                return;
                            }
                            long ident = Binder.clearCallingIdentity();
                            try {
                                try {
                                    holder.getExecutor().execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (!CameraDeviceImpl.this.isClosed()) {
                                                int subsequenceId = resultExtras.getSubsequenceId();
                                                CaptureRequest request = holder.getRequest(subsequenceId);
                                                if (!holder.hasBatchedOutputs()) {
                                                    holder.getCallback().onCaptureStarted(CameraDeviceImpl.this, request, timestamp, frameNumber);
                                                    if (hasReadoutTimestamp) {
                                                        holder.getCallback().onReadoutStarted(CameraDeviceImpl.this, request, readoutTimestamp, frameNumber);
                                                        return;
                                                    }
                                                    return;
                                                }
                                                Range<Integer> fpsRange = (Range) request.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                                for (int i = 0; i < holder.getRequestCount(); i++) {
                                                    holder.getCallback().onCaptureStarted(CameraDeviceImpl.this, holder.getRequest(i), timestamp - (((subsequenceId - i) * 1000000000) / fpsRange.getUpper().intValue()), frameNumber - (subsequenceId - i));
                                                    if (hasReadoutTimestamp) {
                                                        holder.getCallback().onReadoutStarted(CameraDeviceImpl.this, holder.getRequest(i), readoutTimestamp - (((subsequenceId - i) * 1000000000) / fpsRange.getUpper().intValue()), frameNumber - (subsequenceId - i));
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    Binder.restoreCallingIdentity(ident);
                                } catch (Throwable th) {
                                    th = th;
                                    Binder.restoreCallingIdentity(ident);
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } else {
                            try {
                                CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onCaptureStarted(resultExtras, timestamp);
                            } catch (Throwable th3) {
                                th = th3;
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r14v0, types: [long] */
        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v10 */
        /* JADX WARN: Type inference failed for: r14v11 */
        /* JADX WARN: Type inference failed for: r14v12 */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4 */
        /* JADX WARN: Type inference failed for: r14v6 */
        /* JADX WARN: Type inference failed for: r14v7 */
        /* JADX WARN: Type inference failed for: r14v9 */
        /* JADX WARN: Type inference failed for: r1v9, types: [android.hardware.camera2.impl.FrameNumberTracker] */
        /* JADX WARN: Type inference failed for: r7v3, types: [android.hardware.camera2.CaptureResult] */
        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(CameraMetadataNative cameraMetadataNative, final CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws RemoteException {
            final TotalCaptureResult totalCaptureResult;
            long j;
            Object obj;
            int i;
            Runnable runnable;
            TotalCaptureResult totalCaptureResult2;
            int requestId = captureResultExtras.getRequestId();
            ?? frameNumber = captureResultExtras.getFrameNumber();
            Object obj2 = CameraDeviceImpl.this.mInterfaceLock;
            synchronized (obj2) {
                try {
                    try {
                        try {
                            if (CameraDeviceImpl.this.mRemoteDevice == null) {
                                return;
                            }
                            if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                                try {
                                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onResultReceived(cameraMetadataNative, captureResultExtras, physicalCaptureResultInfoArr);
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    frameNumber = obj2;
                                    throw th;
                                }
                            }
                            cameraMetadataNative.set((CameraCharacteristics.Key<CameraCharacteristics.Key<Size>>) CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE, (CameraCharacteristics.Key<Size>) CameraDeviceImpl.this.getCharacteristics().get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE));
                            final CaptureCallbackHolder captureCallbackHolder = (CaptureCallbackHolder) CameraDeviceImpl.this.mCaptureCallbackMap.get(requestId);
                            final CaptureRequest request = captureCallbackHolder.getRequest(captureResultExtras.getSubsequenceId());
                            boolean z = captureResultExtras.getPartialResultCount() < CameraDeviceImpl.this.mTotalPartialCount;
                            int requestType = request.getRequestType();
                            if (captureCallbackHolder == null) {
                                CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, null, z);
                                return;
                            }
                            if (CameraDeviceImpl.this.isClosed()) {
                                CameraDeviceImpl.this.updateTracker(requestId, frameNumber, requestType, null, z);
                                return;
                            }
                            CameraMetadataNative cameraMetadataNative2 = captureCallbackHolder.hasBatchedOutputs() ? new CameraMetadataNative(cameraMetadataNative) : null;
                            if (z) {
                                final ?? captureResult = new CaptureResult(CameraDeviceImpl.this.getId(), cameraMetadataNative, request, captureResultExtras);
                                final CameraMetadataNative cameraMetadataNative3 = cameraMetadataNative2;
                                i = requestId;
                                j = frameNumber;
                                runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (!CameraDeviceImpl.this.isClosed()) {
                                            if (captureCallbackHolder.hasBatchedOutputs()) {
                                                for (int i2 = 0; i2 < captureCallbackHolder.getRequestCount(); i2++) {
                                                    CameraMetadataNative resultLocal = new CameraMetadataNative(cameraMetadataNative3);
                                                    CaptureResult resultInBatch = new CaptureResult(CameraDeviceImpl.this.getId(), resultLocal, captureCallbackHolder.getRequest(i2), captureResultExtras);
                                                    captureCallbackHolder.getCallback().onCaptureProgressed(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i2), resultInBatch);
                                                }
                                                return;
                                            }
                                            captureCallbackHolder.getCallback().onCaptureProgressed(CameraDeviceImpl.this, request, captureResult);
                                        }
                                    }
                                };
                                totalCaptureResult2 = captureResult;
                                frameNumber = obj2;
                            } else {
                                final List<CaptureResult> popPartialResults = CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber);
                                if (CameraDeviceImpl.this.mBatchOutputMap.containsKey(Integer.valueOf(requestId))) {
                                    int intValue = ((Integer) CameraDeviceImpl.this.mBatchOutputMap.get(Integer.valueOf(requestId))).intValue();
                                    for (int i2 = 1; i2 < intValue; i2++) {
                                        CameraDeviceImpl.this.mFrameNumberTracker.popPartialResults(frameNumber - (intValue - i2));
                                    }
                                }
                                final long longValue = ((Long) cameraMetadataNative.get(CaptureResult.SENSOR_TIMESTAMP)).longValue();
                                final Range range = (Range) request.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                                final int subsequenceId = captureResultExtras.getSubsequenceId();
                                j = frameNumber;
                                try {
                                    totalCaptureResult = new TotalCaptureResult(CameraDeviceImpl.this.getId(), cameraMetadataNative, request, captureResultExtras, popPartialResults, captureCallbackHolder.getSessionId(), physicalCaptureResultInfoArr);
                                    obj = obj2;
                                } catch (Throwable th2) {
                                    th = th2;
                                    frameNumber = obj2;
                                }
                                try {
                                    final CameraMetadataNative cameraMetadataNative4 = cameraMetadataNative2;
                                    i = requestId;
                                    runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.CameraDeviceCallbacks.3
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (!CameraDeviceImpl.this.isClosed()) {
                                                if (captureCallbackHolder.hasBatchedOutputs()) {
                                                    for (int i3 = 0; i3 < captureCallbackHolder.getRequestCount(); i3++) {
                                                        cameraMetadataNative4.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(longValue - (((subsequenceId - i3) * 1000000000) / ((Integer) range.getUpper()).intValue())));
                                                        CameraMetadataNative resultLocal = new CameraMetadataNative(cameraMetadataNative4);
                                                        TotalCaptureResult resultInBatch = new TotalCaptureResult(CameraDeviceImpl.this.getId(), resultLocal, captureCallbackHolder.getRequest(i3), captureResultExtras, popPartialResults, captureCallbackHolder.getSessionId(), new PhysicalCaptureResultInfo[0]);
                                                        captureCallbackHolder.getCallback().onCaptureCompleted(CameraDeviceImpl.this, captureCallbackHolder.getRequest(i3), resultInBatch);
                                                    }
                                                    return;
                                                }
                                                captureCallbackHolder.getCallback().onCaptureCompleted(CameraDeviceImpl.this, request, totalCaptureResult);
                                            }
                                        }
                                    };
                                    totalCaptureResult2 = totalCaptureResult;
                                    frameNumber = obj;
                                } catch (Throwable th3) {
                                    th = th3;
                                    frameNumber = obj;
                                    throw th;
                                }
                            }
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                captureCallbackHolder.getExecutor().execute(runnable);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                CameraDeviceImpl.this.updateTracker(i, j, requestType, totalCaptureResult2, z);
                                if (!z) {
                                    CameraDeviceImpl.this.checkAndFireSequenceComplete();
                                }
                            } catch (Throwable th4) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th4;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    frameNumber = obj2;
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int streamId) {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onPrepared(streamId);
                    return;
                }
                OutputConfiguration output = (OutputConfiguration) CameraDeviceImpl.this.mConfiguredOutputs.get(streamId);
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback == null) {
                    return;
                }
                if (output == null) {
                    Log.m102w(CameraDeviceImpl.this.TAG, "onPrepared invoked for unknown output Surface");
                    return;
                }
                List<Surface> surfaces = output.getSurfaces();
                for (Surface surface : surfaces) {
                    sessionCallback.onSurfacePrepared(surface);
                }
            }
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() {
            synchronized (CameraDeviceImpl.this.mInterfaceLock) {
                if (CameraDeviceImpl.this.mOfflineSessionImpl != null) {
                    CameraDeviceImpl.this.mOfflineSessionImpl.getCallbacks().onRequestQueueEmpty();
                    return;
                }
                StateCallbackKK sessionCallback = CameraDeviceImpl.this.mSessionStateCallback;
                if (sessionCallback == null) {
                    return;
                }
                sessionCallback.onRequestQueueEmpty();
            }
        }
    }

    private static class CameraHandlerExecutor implements Executor {
        private final Handler mHandler;

        public CameraHandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Objects.requireNonNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable command) {
            this.mHandler.post(command);
        }
    }

    static Executor checkExecutor(Executor executor) {
        return executor == null ? checkAndWrapHandler(null) : executor;
    }

    public static <T> Executor checkExecutor(Executor executor, T callback) {
        return callback != null ? checkExecutor(executor) : executor;
    }

    public static Executor checkAndWrapHandler(Handler handler) {
        return new CameraHandlerExecutor(checkHandler(handler));
    }

    static Handler checkHandler(Handler handler) {
        if (handler == null) {
            Looper looper = Looper.myLooper();
            if (looper == null) {
                throw new IllegalArgumentException("No handler given, and current thread has no looper!");
            }
            return new Handler(looper);
        }
        return handler;
    }

    static <T> Handler checkHandler(Handler handler, T callback) {
        if (callback != null) {
            return checkHandler(handler);
        }
        return handler;
    }

    private void checkIfCameraClosedOrInError() throws CameraAccessException {
        if (this.mRemoteDevice == null) {
            throw new IllegalStateException("CameraDevice was already closed");
        }
        if (this.mInError) {
            throw new CameraAccessException(3, "The camera device has encountered a serious error");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClosed() {
        return this.mClosing.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CameraCharacteristics getCharacteristics() {
        return this.mCharacteristics;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Log.m102w(this.TAG, "CameraDevice " + this.mCameraId + " died unexpectedly");
        if (this.mRemoteDevice == null) {
            return;
        }
        this.mInError = true;
        Runnable r = new Runnable() { // from class: android.hardware.camera2.impl.CameraDeviceImpl.14
            @Override // java.lang.Runnable
            public void run() {
                if (!CameraDeviceImpl.this.isClosed()) {
                    CameraDeviceImpl.this.mDeviceCallback.onError(CameraDeviceImpl.this, 5);
                }
            }
        };
        long ident = Binder.clearCallingIdentity();
        try {
            this.mDeviceExecutor.execute(r);
        } finally {
            Binder.restoreCallingIdentity(ident);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public void setCameraAudioRestriction(int mode) throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            this.mRemoteDevice.setCameraAudioRestriction(mode);
        }
    }

    @Override // android.hardware.camera2.CameraDevice
    public int getCameraAudioRestriction() throws CameraAccessException {
        int globalAudioRestriction;
        synchronized (this.mInterfaceLock) {
            checkIfCameraClosedOrInError();
            globalAudioRestriction = this.mRemoteDevice.getGlobalAudioRestriction();
        }
        return globalAudioRestriction;
    }

    @Override // android.hardware.camera2.CameraDevice
    public void createExtensionSession(ExtensionSessionConfiguration extensionConfiguration) throws CameraAccessException {
        HashMap<String, CameraCharacteristics> characteristicsMap = new HashMap<>(this.mPhysicalIdsToChars);
        characteristicsMap.put(this.mCameraId, this.mCharacteristics);
        StringBuilder append = new StringBuilder().append(this.TAG).append(" : ");
        int i = this.mNextSessionId;
        this.mNextSessionId = i + 1;
        IBinder token = new Binder(append.append(i).toString());
        try {
            try {
                boolean ret = CameraExtensionCharacteristics.registerClient(this.mContext, token);
                if (!ret) {
                    throw new UnsupportedOperationException("Unsupported extension!");
                }
                if (CameraExtensionCharacteristics.areAdvancedExtensionsSupported()) {
                    this.mCurrentAdvancedExtensionSession = CameraAdvancedExtensionSessionImpl.createCameraAdvancedExtensionSession(this, characteristicsMap, this.mContext, extensionConfiguration, this.mNextSessionId, token);
                } else {
                    this.mCurrentExtensionSession = CameraExtensionSessionImpl.createCameraExtensionSession(this, characteristicsMap, this.mContext, extensionConfiguration, this.mNextSessionId, token);
                }
                if (0 != 0) {
                    CameraExtensionCharacteristics.unregisterClient(this.mContext, token);
                }
            } catch (RemoteException e) {
                throw new CameraAccessException(3);
            }
        } catch (Throwable th) {
            if (1 != 0 && token != null) {
                CameraExtensionCharacteristics.unregisterClient(this.mContext, token);
            }
            throw th;
        }
    }

    private List<OutputConfiguration> applyExtensionStreamOption(List<OutputConfiguration> outputs, CaptureRequest sessionParams) {
        CaptureRequest.Key<Integer> CONTROL_CAMERA_CLIENT = new CaptureRequest.Key<>("samsung.android.control.cameraClient", (Class<Integer>) Integer.class);
        if (sessionParams == null) {
            return outputs;
        }
        Integer clientValue = null;
        try {
            clientValue = (Integer) sessionParams.get(CONTROL_CAMERA_CLIENT);
        } catch (IllegalArgumentException e) {
            Log.m98i(this.TAG, "no camera client key, skip");
        }
        if (clientValue == null || clientValue.intValue() != 3) {
            return outputs;
        }
        List<OutputConfiguration> modified = new ArrayList<>();
        for (OutputConfiguration outputConfiguration : outputs) {
            if (outputConfiguration.getSurfaceGroupId() != -1) {
                OutputConfiguration newConfiguration = new OutputConfiguration(-1, outputConfiguration.getSurface(), 0, outputConfiguration.getSurfaceGroupId());
                newConfiguration.setTimestampBase(outputConfiguration.getTimestampBase());
                newConfiguration.setReadoutTimestampEnabled(outputConfiguration.isReadoutTimestampEnabled());
                newConfiguration.setPhysicalCameraId(outputConfiguration.getPhysicalCameraId());
                modified.add(newConfiguration);
            } else {
                modified.add(outputConfiguration);
            }
        }
        return modified;
    }
}
