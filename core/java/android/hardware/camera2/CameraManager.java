package android.hardware.camera2;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.TaskInfo;
import android.app.compat.CompatChanges;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.flags.Flags;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.CameraExtensionSessionStats;
import android.hardware.CameraStatus;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceListener;
import android.hardware.IDeviceInjectorCallback;
import android.hardware.IRemoteDevice;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraInjectionSession;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.DeviceInjectorSession;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl.CameraDeviceSetupImpl;
import android.hardware.camera2.impl.CameraInjectionSessionImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.DeviceInjectorSessionImpl;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.CameraIdAndSessionConfiguration;
import android.hardware.camera2.utils.ConcurrentCameraIdCombination;
import android.hardware.camera2.utils.ExceptionUtils;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Display;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public final class CameraManager {
    private static final int API_VERSION_1 = 1;
    private static final int API_VERSION_2 = 2;
    private static final String CAMERA_OPEN_CLOSE_LISTENER_PERMISSION = "android.permission.CAMERA_OPEN_CLOSE_LISTENER";
    private static final int CAMERA_TYPE_ALL = 1;
    private static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    private static final long ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA = 244358506;
    public static final String LANDSCAPE_TO_PORTRAIT_PROP = "camera.enable_landscape_to_portrait";
    public static final long OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT = 250678880;
    public static final int ROTATION_OVERRIDE_NONE = 0;
    public static final int ROTATION_OVERRIDE_OVERRIDE_TO_PORTRAIT = 1;
    public static final int ROTATION_OVERRIDE_ROTATION_ONLY = 2;
    private static final String TAG = "CameraManager";
    public static final int TORCH_STRENGTH_1 = 1;
    public static final int TORCH_STRENGTH_2 = 2;
    public static final int TORCH_STRENGTH_3 = 3;
    public static final int TORCH_STRENGTH_4 = 4;
    public static final int TORCH_STRENGTH_5 = 5;
    public static final int TORCH_STRENGTH_DEFAULT = 0;
    private static final int TORCH_STRENGTH_MAX = 5;
    private static final int USE_CALLING_UID = -1;
    private static final int kInternalIdStart = 20;
    private static final int kVirtualIdStart = 100;
    private final Context mContext;
    private final boolean mHasOpenCloseListenerPermission;
    private VirtualDeviceManager mVirtualDeviceManager;
    private final boolean DEBUG = false;
    private final Map<String, Map<String, StreamConfiguration[]>> mCameraIdToMultiResolutionStreamConfigurationMap = new HashMap();
    private final Object mLock = new Object();
    private Boolean mHiddenCameraPermittedState = null;

    public interface DeviceStateListener {
        void onDeviceStateChanged(boolean z);
    }

    private static class CameraDeviceState {
        public final String mClientName;
        public final int mDeviceState;
        public final int mFacing;
        public final int mUserId;

        public CameraDeviceState(int facing, int deviceState, String clientName, int userId) {
            this.mFacing = facing;
            this.mDeviceState = deviceState;
            this.mClientName = clientName;
            this.mUserId = userId;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CameraDeviceState)) {
                return false;
            }
            CameraDeviceState other = (CameraDeviceState) obj;
            return other.mFacing == this.mFacing && other.mDeviceState == this.mDeviceState && other.mClientName.equals(this.mClientName) && other.mUserId == this.mUserId;
        }

        public String toString() {
            return String.format("facing %s state now %s for client %s", cameraFacingToString(this.mFacing), cameraStateToString(this.mDeviceState), this.mClientName);
        }

        private static String cameraStateToString(int newCameraState) {
            switch (newCameraState) {
                case 0:
                    return "CAMERA_STATE_OPEN";
                case 1:
                    return "CAMERA_STATE_ACTIVE";
                case 2:
                    return "CAMERA_STATE_IDLE";
                case 3:
                    return "CAMERA_STATE_CLOSED";
                case 100:
                    return "CAMERA_STATE_OPENING";
                case 101:
                    return "CAMERA_STATE_OPENING_FAILED";
                default:
                    return "CAMERA_STATE_UNKNOWN";
            }
        }

        private static String cameraFacingToString(int cameraFacing) {
            switch (cameraFacing) {
                case 0:
                    return "CAMERA_FACING_BACK";
                case 1:
                    return "CAMERA_FACING_FRONT";
                case 2:
                    return "CAMERA_FACING_EXTERNAL";
                default:
                    return "CAMERA_FACING_UNKNOWN";
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPublicId(String cameraId) {
        return ((Boolean) Optional.ofNullable(cameraId).map(new Function() { // from class: android.hardware.camera2.CameraManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CameraManager.lambda$isPublicId$0((String) obj);
            }
        }).orElseThrow(new Supplier() { // from class: android.hardware.camera2.CameraManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CameraManager.lambda$isPublicId$1();
            }
        })).booleanValue();
    }

    static /* synthetic */ Boolean lambda$isPublicId$0(String id) {
        boolean z;
        try {
            if (Integer.parseInt(id) >= 20 && 100 > Integer.parseInt(id)) {
                z = false;
                return Boolean.valueOf(z);
            }
            z = true;
            return Boolean.valueOf(z);
        } catch (NumberFormatException e) {
            return true;
        }
    }

    static /* synthetic */ NullPointerException lambda$isPublicId$1() {
        return new NullPointerException("Camera ID must not be null");
    }

    public CameraManager(Context context) {
        synchronized (this.mLock) {
            this.mContext = context;
            this.mHasOpenCloseListenerPermission = this.mContext.checkSelfPermission("android.permission.CAMERA_OPEN_CLOSE_LISTENER") == 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
        private ArrayList<WeakReference<DeviceStateListener>> mDeviceStateListeners = new ArrayList<>();
        private boolean mFoldedDeviceState;
        private final int[] mFoldedDeviceStates;

        public FoldStateListener(Context context) {
            this.mFoldedDeviceStates = context.getResources().getIntArray(R.array.config_foldedDeviceStates);
        }

        private synchronized void handleStateChange(int state) {
            boolean folded = ArrayUtils.contains(this.mFoldedDeviceStates, state);
            this.mFoldedDeviceState = folded;
            Iterator<WeakReference<DeviceStateListener>> it = this.mDeviceStateListeners.iterator();
            while (it.hasNext()) {
                DeviceStateListener callback = it.next().get();
                if (callback != null) {
                    callback.onDeviceStateChanged(folded);
                } else {
                    it.remove();
                }
            }
        }

        public synchronized void addDeviceStateListener(DeviceStateListener listener) {
            listener.onDeviceStateChanged(this.mFoldedDeviceState);
            this.mDeviceStateListeners.removeIf(new Predicate() { // from class: android.hardware.camera2.CameraManager$FoldStateListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CameraManager.FoldStateListener.lambda$addDeviceStateListener$0((WeakReference) obj);
                }
            });
            this.mDeviceStateListeners.add(new WeakReference<>(listener));
        }

        static /* synthetic */ boolean lambda$addDeviceStateListener$0(WeakReference l) {
            return l.get() == null;
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public void onDeviceStateChanged(DeviceState state) {
            handleStateChange(state.getIdentifier());
        }
    }

    public void registerDeviceStateListener(CameraCharacteristics chars) {
        CameraManagerGlobal.get().registerDeviceStateListener(chars, this.mContext);
    }

    public String[] getCameraIdList() throws CameraAccessException {
        return CameraManagerGlobal.get().getCameraIdList(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public String[] getCameraIdListNoLazy() throws CameraAccessException {
        return CameraManagerGlobal.get().getCameraIdListNoLazy(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public Set<Set<String>> getConcurrentCameraIds() throws CameraAccessException {
        return CameraManagerGlobal.get().getConcurrentCameraIds(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public boolean isConcurrentSessionConfigurationSupported(Map<String, SessionConfiguration> cameraIdAndSessionConfig) throws CameraAccessException {
        return CameraManagerGlobal.get().isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfig, this.mContext.getApplicationInfo().targetSdkVersion, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerAvailabilityCallback(AvailabilityCallback callback, Handler handler) {
        if (callback != null) {
            boolean isHiddenIdPermittedPackage = false;
            callback.mPackageName = this.mContext.getOpPackageName();
            callback.mIsRegisteredWhileServiceDown = false;
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                Log.e(TAG, "Camera service is currently unavailable");
                callback.mIsRegisteredWhileServiceDown = true;
            } else {
                try {
                    isHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
                } catch (RemoteException e) {
                    callback.mIsRegisteredWhileServiceDown = true;
                    Log.e(TAG, "Camera service is currently unavailable", e);
                }
            }
            callback.mIsHiddenIdPermittedPackage = isHiddenIdPermittedPackage;
            Log.i(TAG, "registerAvailabilityCallback: Is device callback = " + this.mHasOpenCloseListenerPermission);
        }
        CameraManagerGlobal.get().registerAvailabilityCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mHasOpenCloseListenerPermission, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerAvailabilityCallback(Executor executor, AvailabilityCallback callback) {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (callback != null) {
            boolean isHiddenIdPermittedPackage = false;
            callback.mPackageName = this.mContext.getOpPackageName();
            callback.mIsRegisteredWhileServiceDown = false;
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                Log.e(TAG, "Camera service is currently unavailable");
                callback.mIsRegisteredWhileServiceDown = true;
            } else {
                try {
                    isHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
                } catch (RemoteException e) {
                    callback.mIsRegisteredWhileServiceDown = true;
                    Log.e(TAG, "Camera service is currently unavailable", e);
                }
            }
            callback.mIsHiddenIdPermittedPackage = isHiddenIdPermittedPackage;
            Log.i(TAG, "registerAvailabilityCallback: Is device callback = " + this.mHasOpenCloseListenerPermission);
        }
        CameraManagerGlobal.get().registerAvailabilityCallback(callback, executor, this.mHasOpenCloseListenerPermission, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback callback) {
        CameraManagerGlobal.get().unregisterAvailabilityCallback(callback);
    }

    public void registerTorchCallback(TorchCallback callback, Handler handler) {
        CameraManagerGlobal.get().registerTorchCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerTorchCallback(Executor executor, TorchCallback callback) {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        CameraManagerGlobal.get().registerTorchCallback(callback, executor, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void unregisterTorchCallback(TorchCallback callback) {
        CameraManagerGlobal.get().unregisterTorchCallback(callback);
    }

    public int getDevicePolicyFromContext(Context context) {
        if (context.getDeviceId() == 0 || !Flags.virtualCamera()) {
            return 0;
        }
        if (this.mVirtualDeviceManager == null) {
            this.mVirtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class);
        }
        return this.mVirtualDeviceManager.getDevicePolicy(context.getDeviceId(), 5);
    }

    public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback, Handler handler) {
        CameraManagerGlobal.get().registerSemCameraDeviceStateCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback, Handler handler, boolean isExtended) {
        callback.isExtended = isExtended;
        registerSemCameraDeviceStateCallback(callback, handler);
    }

    public void unregisterSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback) {
        CameraManagerGlobal.get().unregisterSemCameraDeviceStateCallback(callback);
    }

    private Size getDisplaySize() {
        Size ret = new Size(0, 0);
        try {
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
            Display display = displayManager.getDisplay(0);
            if (display == null) {
                Log.e(TAG, "Invalid default display!");
                return ret;
            }
            Point sz = new Point();
            display.getRealSize(sz);
            int width = sz.x;
            int height = sz.y;
            if (height > width) {
                height = width;
                width = sz.y;
            }
            return new Size(width, height);
        } catch (Exception e) {
            Log.e(TAG, "getDisplaySize Failed. " + e);
            return ret;
        }
    }

    private Map<String, StreamConfiguration[]> getPhysicalCameraMultiResolutionConfigs(String cameraId, CameraMetadataNative info, ICameraService cameraService) throws CameraAccessException {
        if (this.mCameraIdToMultiResolutionStreamConfigurationMap.containsKey(cameraId)) {
            return this.mCameraIdToMultiResolutionStreamConfigurationMap.get(cameraId);
        }
        HashMap<String, StreamConfiguration[]> multiResolutionStreamConfigurations = new HashMap<>();
        this.mCameraIdToMultiResolutionStreamConfigurationMap.put(cameraId, multiResolutionStreamConfigurations);
        Boolean multiResolutionStreamSupported = (Boolean) info.get(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED);
        if (multiResolutionStreamSupported == null || !multiResolutionStreamSupported.booleanValue()) {
            return multiResolutionStreamConfigurations;
        }
        Set<String> physicalCameraIds = info.getPhysicalCameraIds();
        if (physicalCameraIds.size() == 0 && info.isUltraHighResolutionSensor()) {
            StreamConfiguration[] configs = (StreamConfiguration[]) info.get(CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
            if (configs != null) {
                multiResolutionStreamConfigurations.put(cameraId, configs);
            }
            return multiResolutionStreamConfigurations;
        }
        try {
            for (String physicalCameraId : physicalCameraIds) {
                CameraMetadataNative physicalCameraInfo = cameraService.getCameraCharacteristics(physicalCameraId, this.mContext.getApplicationInfo().targetSdkVersion, 0, 0, 0);
                StreamConfiguration[] configs2 = (StreamConfiguration[]) physicalCameraInfo.get(CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
                if (configs2 != null) {
                    multiResolutionStreamConfigurations.put(physicalCameraId, configs2);
                }
            }
            return multiResolutionStreamConfigurations;
        } catch (RemoteException e) {
            ServiceSpecificException sse = new ServiceSpecificException(4, "Camera service is currently unavailable");
            throw ExceptionUtils.throwAsPublicException(sse);
        }
    }

    public CameraCharacteristics getCameraCharacteristics(String cameraId) throws CameraAccessException {
        return getCameraCharacteristics(cameraId, getRotationOverride(this.mContext));
    }

    public CameraCharacteristics getCameraCharacteristics(String cameraId, boolean overrideToPortrait) throws CameraAccessException {
        int i;
        if (overrideToPortrait) {
            i = 1;
        } else {
            i = 0;
        }
        return getCameraCharacteristics(cameraId, i);
    }

    private CameraCharacteristics getCameraCharacteristics(String cameraId, int rotationOverride) throws CameraAccessException {
        CameraCharacteristics characteristics;
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        synchronized (this.mLock) {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                if (!isPublicId(cameraId)) {
                    String currentPackage = this.mContext.getOpPackageName();
                    if (this.mHiddenCameraPermittedState == null) {
                        this.mHiddenCameraPermittedState = Boolean.valueOf(cameraService.isHiddenIdPermittedPackage(currentPackage));
                    }
                    if (!this.mHiddenCameraPermittedState.booleanValue()) {
                        throw new IllegalArgumentException(String.format("Unknown camera ID %s", cameraId));
                    }
                }
                try {
                    CameraMetadataNative info = cameraService.getCameraCharacteristics(cameraId, this.mContext.getApplicationInfo().targetSdkVersion, rotationOverride, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
                    characteristics = prepareCameraCharacteristics(cameraId, info, cameraService);
                } catch (RemoteException e) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable", e);
                } catch (ServiceSpecificException e2) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
            } catch (RemoteException e3) {
                throw new CameraAccessException(2, "Camera service is currently unavailable", e3);
            }
        }
        registerDeviceStateListener(characteristics);
        return characteristics;
    }

    public CameraCharacteristics prepareCameraCharacteristics(String cameraId, CameraMetadataNative metadata, ICameraService cameraService) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics;
        synchronized (this.mLock) {
            try {
                metadata.setCameraId(Integer.parseInt(cameraId));
            } catch (NumberFormatException e) {
                Log.v(TAG, "Failed to parse camera Id " + cameraId + " to integer");
            }
            boolean hasConcurrentStreams = CameraManagerGlobal.get().cameraIdHasConcurrentStreamsLocked(cameraId, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
            metadata.setHasMandatoryConcurrentStreams(hasConcurrentStreams);
            Size displaySize = getDisplaySize();
            metadata.setDisplaySize(displaySize);
            Map<String, StreamConfiguration[]> multiResolutionSizeMap = getPhysicalCameraMultiResolutionConfigs(cameraId, metadata, cameraService);
            if (!multiResolutionSizeMap.isEmpty()) {
                metadata.setMultiResolutionStreamConfigurationMap(multiResolutionSizeMap);
            }
            cameraCharacteristics = new CameraCharacteristics(metadata);
        }
        return cameraCharacteristics;
    }

    public CameraExtensionCharacteristics getCameraExtensionCharacteristics(String cameraId) throws CameraAccessException {
        CameraCharacteristics chars = getCameraCharacteristics(cameraId);
        Map<String, CameraCharacteristics> characteristicsMap = getPhysicalIdToCharsMap(chars);
        characteristicsMap.put(cameraId, chars);
        return new CameraExtensionCharacteristics(this.mContext, cameraId, characteristicsMap);
    }

    public Map<String, CameraCharacteristics> getPhysicalIdToCharsMap(CameraCharacteristics chars) throws CameraAccessException {
        HashMap<String, CameraCharacteristics> physicalIdsToChars = new HashMap<>();
        Set<String> physicalCameraIds = chars.getPhysicalCameraIds();
        for (String physicalCameraId : physicalCameraIds) {
            CameraCharacteristics physicalChars = getCameraCharacteristics(physicalCameraId);
            physicalIdsToChars.put(physicalCameraId, physicalChars);
        }
        return physicalIdsToChars;
    }

    public CameraDevice.CameraDeviceSetup getCameraDeviceSetup(String cameraId) throws CameraAccessException {
        if (!isCameraDeviceSetupSupported(cameraId)) {
            throw new UnsupportedOperationException("CameraDeviceSetup is not supported for Camera ID: " + cameraId);
        }
        return getCameraDeviceSetupUnsafe(cameraId);
    }

    private CameraDevice.CameraDeviceSetup getCameraDeviceSetupUnsafe(String cameraId) {
        return new CameraDeviceSetupImpl(cameraId, this, this.mContext);
    }

    public boolean isCameraDeviceSetupSupported(String cameraId) throws CameraAccessException {
        if (cameraId == null) {
            throw new IllegalArgumentException("Camera ID was null");
        }
        if (CameraManagerGlobal.sCameraServiceDisabled || !Arrays.asList(CameraManagerGlobal.get().getCameraIdList(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext))).contains(cameraId)) {
            throw new IllegalArgumentException("Camera ID '" + cameraId + "' not available on device.");
        }
        CameraCharacteristics chars = getCameraCharacteristics(cameraId);
        return CameraDeviceSetupImpl.isCameraDeviceSetupSupported(chars);
    }

    private CameraDevice openCameraDeviceUserAsync(String cameraId, CameraDevice.StateCallback callback, Executor executor, int uid, int oomScoreOffset, int rotationOverride) throws CameraAccessException {
        CameraDevice.CameraDeviceSetup cameraDeviceSetup;
        CameraDeviceImpl deviceImpl;
        ICameraService cameraService;
        CameraCharacteristics characteristics = getCameraCharacteristics(cameraId);
        synchronized (this.mLock) {
            ICameraDeviceUser cameraUser = null;
            if (com.android.internal.camera.flags.Flags.cameraDeviceSetup() && CameraDeviceSetupImpl.isCameraDeviceSetupSupported(characteristics)) {
                CameraDevice.CameraDeviceSetup cameraDeviceSetup2 = getCameraDeviceSetupUnsafe(cameraId);
                cameraDeviceSetup = cameraDeviceSetup2;
            } else {
                cameraDeviceSetup = null;
            }
            deviceImpl = new CameraDeviceImpl(cameraId, callback, executor, characteristics, this, this.mContext.getApplicationInfo().targetSdkVersion, this.mContext, cameraDeviceSetup);
            ICameraDeviceCallbacks callbacks = deviceImpl.getCallbacks();
            try {
                cameraService = CameraManagerGlobal.get().getCameraService();
            } catch (RemoteException e) {
                ServiceSpecificException sse = new ServiceSpecificException(4, "Camera service is currently unavailable");
                deviceImpl.setRemoteFailure(sse);
                throw ExceptionUtils.throwAsPublicException(sse);
            } catch (ServiceSpecificException e2) {
                if (e2.errorCode == 9) {
                    throw new AssertionError("Should've gone down the shim path");
                }
                if (e2.errorCode != 7 && e2.errorCode != 8 && e2.errorCode != 6 && e2.errorCode != 4 && e2.errorCode != 10) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
                deviceImpl.setRemoteFailure(e2);
                if (e2.errorCode == 6 || e2.errorCode == 4 || e2.errorCode == 7) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
            }
            if (cameraService == null) {
                throw new ServiceSpecificException(4, "Camera service is currently unavailable");
            }
            cameraUser = cameraService.connectDevice(callbacks, cameraId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), uid, oomScoreOffset, this.mContext.getApplicationInfo().targetSdkVersion, rotationOverride, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
            deviceImpl.setRemoteDevice(cameraUser);
        }
        return deviceImpl;
    }

    public void openCamera(String cameraId, CameraDevice.StateCallback callback, Handler handler) throws CameraAccessException {
        openCameraForUid(cameraId, callback, CameraDeviceImpl.checkAndWrapHandler(handler), -1);
    }

    public void openCamera(String cameraId, boolean overrideToPortrait, Handler handler, CameraDevice.StateCallback callback) throws CameraAccessException {
        int i;
        Executor checkAndWrapHandler = CameraDeviceImpl.checkAndWrapHandler(handler);
        if (overrideToPortrait) {
            i = 1;
        } else {
            i = 0;
        }
        openCameraForUid(cameraId, callback, checkAndWrapHandler, -1, 0, i);
    }

    public void openCamera(String cameraId, Executor executor, CameraDevice.StateCallback callback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        openCameraForUid(cameraId, callback, executor, -1);
    }

    @SystemApi
    public void openCamera(String cameraId, int oomScoreOffset, Executor executor, CameraDevice.StateCallback callback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (oomScoreOffset < 0) {
            throw new IllegalArgumentException("oomScoreOffset < 0, cannot increase priority of camera client");
        }
        openCameraForUid(cameraId, callback, executor, -1, oomScoreOffset, getRotationOverride(this.mContext));
    }

    public void openCameraForUid(String cameraId, CameraDevice.StateCallback callback, Executor executor, int clientUid, int oomScoreOffset, int rotationOverride) throws CameraAccessException {
        if (cameraId == null) {
            throw new IllegalArgumentException("cameraId was null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        openCameraDeviceUserAsync(cameraId, callback, executor, clientUid, oomScoreOffset, rotationOverride);
    }

    public void openCameraForUid(String cameraId, CameraDevice.StateCallback callback, Executor executor, int clientUid) throws CameraAccessException {
        openCameraForUid(cameraId, callback, executor, clientUid, 0, getRotationOverride(this.mContext));
    }

    public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        CameraManagerGlobal.get().setTorchMode(cameraId, enabled, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void turnOnTorchWithStrengthLevel(String cameraId, int torchStrength) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No camera available on device");
        }
        CameraManagerGlobal.get().turnOnTorchWithStrengthLevel(cameraId, torchStrength, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public int getTorchStrengthLevel(String cameraId) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No camera available on device.");
        }
        return CameraManagerGlobal.get().getTorchStrengthLevel(cameraId, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public static int getRotationOverride(Context context) {
        PackageManager packageManager = null;
        String packageName = null;
        if (context != null) {
            packageManager = context.getPackageManager();
            packageName = context.getOpPackageName();
        }
        return getRotationOverride(context, packageManager, packageName);
    }

    public static int getRotationOverride(Context context, PackageManager packageManager, String packageName) {
        if (com.android.window.flags.Flags.cameraCompatForFreeform()) {
            return getRotationOverrideInternal(context, packageManager, packageName);
        }
        if (shouldOverrideToPortrait(packageManager, packageName)) {
            return 1;
        }
        return 0;
    }

    public static int getRotationOverrideInternal(Context context, PackageManager packageManager, String packageName) {
        if (!CameraManagerGlobal.sLandscapeToPortrait) {
            return 0;
        }
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
            for (ActivityManager.AppTask appTask : activityManager.getAppTasks()) {
                TaskInfo taskInfo = appTask.getTaskInfo();
                if (taskInfo.appCompatTaskInfo.cameraCompatTaskInfo.freeformCameraCompatMode != 0 && taskInfo.topActivity != null && taskInfo.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
        }
        if (packageManager != null && packageName != null) {
            try {
                return packageManager.getProperty(PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, packageName).getBoolean() ? 1 : 0;
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT) ? 1 : 0;
    }

    public static boolean shouldOverrideToPortrait(PackageManager packageManager, String packageName) {
        if (!CameraManagerGlobal.sLandscapeToPortrait) {
            return false;
        }
        if (packageManager != null && packageName != null) {
            try {
                return packageManager.getProperty(PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, packageName).getBoolean();
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT);
    }

    public static boolean physicalCallbacksAreEnabledForUnavailableCamera() {
        return CompatChanges.isChangeEnabled(ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA);
    }

    public static abstract class AvailabilityCallback {
        private int mDeviceId;
        private int mDevicePolicy;
        private boolean mIsHiddenIdPermittedPackage;
        private boolean mIsRegisteredWhileServiceDown;
        private String mPackageName;

        public void onCameraAvailable(String cameraId) {
        }

        public void onCameraUnavailable(String cameraId) {
        }

        public void onCameraAccessPrioritiesChanged() {
        }

        public void onPhysicalCameraAvailable(String cameraId, String physicalCameraId) {
        }

        public void onPhysicalCameraUnavailable(String cameraId, String physicalCameraId) {
        }

        @SystemApi
        public void onCameraOpened(String cameraId, String packageId) {
        }

        @SystemApi
        public void onCameraClosed(String cameraId) {
        }

        public void onSemCameraDeviceOpen(String cameraId, int facing, String clientName) {
        }

        public void onSemCameraDeviceClose(String cameraId, int facing, String clientName) {
        }

        public void onSemCameraDeviceIdle(String cameraId, int facing, String clientName) {
        }

        public void onSemCameraDeviceActive(String cameraId, int facing, String clientName) {
        }

        public void onSemCameraDeviceRawStatus(String cameraId, int rawStatus) {
        }
    }

    public static abstract class TorchCallback {
        private int mDeviceId;
        private int mDevicePolicy;

        public void onTorchModeUnavailable(String cameraId) {
        }

        public void onTorchModeChanged(String cameraId, boolean enabled) {
        }

        public void onTorchStrengthLevelChanged(String cameraId, int newStrengthLevel) {
        }
    }

    public static abstract class SemCameraDeviceStateCallback {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_EXTERNAL = 2;
        public static final int CAMERA_FACING_FRONT = 1;
        public static final int CAMERA_STATE_ACTIVE = 1;
        public static final int CAMERA_STATE_CLOSED = 3;
        public static final int CAMERA_STATE_IDLE = 2;
        public static final int CAMERA_STATE_OPEN = 0;
        public static final int CAMERA_STATE_OPENING = 100;
        public static final int CAMERA_STATE_OPENING_FAILED = 101;
        private boolean isExtended = false;
        private int mDeviceId;
        private int mDevicePolicy;

        public void onCameraDeviceStateChanged(String cameraId, int facing, int newCameraState, String clientName) {
        }

        public void onCameraDeviceStateChanged(String cameraId, int facing, int newCameraState, String clientName, int userId) {
        }
    }

    public static boolean isHiddenPhysicalCamera(String cameraId) {
        try {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                return false;
            }
            return cameraService.isHiddenPhysicalCamera(cameraId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void injectCamera(String packageName, String internalCamId, String externalCamId, Executor executor, CameraInjectionSession.InjectionStatusCallback callback) throws CameraAccessException, SecurityException, IllegalArgumentException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                try {
                    CameraInjectionSessionImpl injectionSessionImpl = new CameraInjectionSessionImpl(callback, executor);
                    ICameraInjectionCallback cameraInjectionCallback = injectionSessionImpl.getCallback();
                    ICameraInjectionSession injectionSession = cameraService.injectCamera(packageName, internalCamId, externalCamId, cameraInjectionCallback);
                    injectionSessionImpl.setRemoteInjectionSession(injectionSession);
                } catch (RemoteException e) {
                    ServiceSpecificException sse = new ServiceSpecificException(4, "Camera service is currently unavailable");
                    throw ExceptionUtils.throwAsPublicException(sse);
                } catch (ServiceSpecificException e2) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void injectSessionParams(String cameraId, CaptureRequest sessionParams) throws CameraAccessException, SecurityException {
        CameraManagerGlobal.get().injectSessionParams(cameraId, sessionParams);
    }

    public ICameraService getCameraService() {
        return CameraManagerGlobal.get().getCameraService();
    }

    public boolean isCameraServiceDisabled() {
        return CameraManagerGlobal.sCameraServiceDisabled;
    }

    public static String reportExtensionSessionStats(CameraExtensionSessionStats extStats) {
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            Log.e(TAG, "CameraService not available. Not reporting extension stats.");
            return "";
        }
        try {
            return cameraService.reportExtensionSessionStats(extStats);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to report extension session stats to cameraservice.", e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CameraManagerGlobal extends ICameraServiceListener.Stub implements IBinder.DeathRecipient {
        private static final String CAMERA_SERVICE_BINDER_NAME = "media.camera";
        private static final String TAG = "CameraManagerGlobal";
        private static final CameraManagerGlobal gCameraManager = new CameraManagerGlobal();
        public static final boolean sCameraServiceDisabled = SystemProperties.getBoolean("config.disable_cameraservice", false);
        public static final boolean sLandscapeToPortrait = SystemProperties.getBoolean(CameraManager.LANDSCAPE_TO_PORTRAIT_PROP, false);
        private ICameraService mCameraService;
        private Handler mDeviceStateHandler;
        private HandlerThread mDeviceStateHandlerThread;
        private FoldStateListener mFoldStateListener;
        private final boolean DEBUG = false;
        private final int CAMERA_SERVICE_RECONNECT_DELAY_MS = 1000;
        private final ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
        private final ArrayMap<DeviceCameraInfo, Integer> mDeviceStatus = new ArrayMap<>();
        private final ArrayMap<DeviceCameraInfo, ArrayList<String>> mUnavailablePhysicalDevices = new ArrayMap<>();
        private final ArrayMap<DeviceCameraInfo, String> mOpenedDevices = new ArrayMap<>();
        private final Set<Set<DeviceCameraInfo>> mConcurrentCameraIdCombinations = new ArraySet();
        private final ArrayMap<AvailabilityCallback, Executor> mCallbackMap = new ArrayMap<>();
        private final Binder mTorchClientBinder = new Binder();
        private final ArrayMap<DeviceCameraInfo, Integer> mTorchStatus = new ArrayMap<>();
        private final ArrayMap<TorchCallback, Executor> mTorchCallbackMap = new ArrayMap<>();
        private final ArrayMap<DeviceCameraInfo, CameraDeviceState> mCameraDeviceStates = new ArrayMap<>();
        private final ArrayMap<SemCameraDeviceStateCallback, Executor> mSemCameraDeviceStateCallbackMap = new ArrayMap<>();
        private final Object mLock = new Object();
        private boolean mHasOpenCloseListenerPermission = false;

        private CameraManagerGlobal() {
        }

        public static CameraManagerGlobal get() {
            return gCameraManager;
        }

        public void registerDeviceStateListener(CameraCharacteristics chars, Context ctx) {
            synchronized (this.mLock) {
                if (this.mDeviceStateHandlerThread == null) {
                    this.mDeviceStateHandlerThread = new HandlerThread(TAG);
                    this.mDeviceStateHandlerThread.start();
                    this.mDeviceStateHandler = new Handler(this.mDeviceStateHandlerThread.getLooper());
                }
                if (this.mFoldStateListener == null) {
                    this.mFoldStateListener = new FoldStateListener(ctx);
                    try {
                        ((DeviceStateManager) ctx.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mDeviceStateHandler), this.mFoldStateListener);
                    } catch (IllegalStateException e) {
                        this.mFoldStateListener = null;
                        Log.v(TAG, "Failed to register device state listener!");
                        Log.v(TAG, "Device state dependent characteristics updates will not befunctional!");
                        return;
                    }
                }
                this.mFoldStateListener.addDeviceStateListener(chars.getDeviceStateListener());
            }
        }

        @Override // android.hardware.ICameraServiceListener.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public ICameraService getCameraService() {
            ICameraService iCameraService;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mCameraService == null && !sCameraServiceDisabled) {
                    Log.e(TAG, "Camera service is unavailable");
                }
                iCameraService = this.mCameraService;
            }
            return iCameraService;
        }

        private void connectCameraServiceLocked() {
            if (this.mCameraService != null || sCameraServiceDisabled) {
                return;
            }
            Log.i(TAG, "Connecting to camera service");
            IBinder cameraServiceBinder = ServiceManager.getService(CAMERA_SERVICE_BINDER_NAME);
            if (cameraServiceBinder == null) {
                return;
            }
            try {
                cameraServiceBinder.linkToDeath(this, 0);
                ICameraService cameraService = ICameraService.Stub.asInterface(cameraServiceBinder);
                try {
                    CameraMetadataNative.setupGlobalVendorTagDescriptor();
                } catch (ServiceSpecificException e) {
                    handleRecoverableSetupErrors(e);
                }
                try {
                    CameraStatus[] cameraStatuses = cameraService.addListener(this);
                    for (CameraStatus cameraStatus : cameraStatuses) {
                        DeviceCameraInfo info = new DeviceCameraInfo(cameraStatus.cameraId, cameraStatus.deviceId);
                        onStatusChangedLocked(cameraStatus.status, info);
                        if (cameraStatus.unavailablePhysicalCameras != null) {
                            for (String unavailablePhysicalCamera : cameraStatus.unavailablePhysicalCameras) {
                                onPhysicalCameraStatusChangedLocked(0, info, unavailablePhysicalCamera);
                            }
                        }
                        if (this.mHasOpenCloseListenerPermission && cameraStatus.status == -2 && !cameraStatus.clientPackage.isEmpty()) {
                            onCameraOpenedLocked(info, cameraStatus.clientPackage);
                        }
                    }
                    this.mCameraService = cameraService;
                } catch (RemoteException e2) {
                } catch (ServiceSpecificException e3) {
                    throw new IllegalStateException("Failed to register a camera service listener", e3);
                }
                try {
                    ConcurrentCameraIdCombination[] cameraIdCombinations = cameraService.getConcurrentCameraIds();
                    for (ConcurrentCameraIdCombination comb : cameraIdCombinations) {
                        Set<Pair<String, Integer>> combination = comb.getConcurrentCameraIdCombination();
                        Set<DeviceCameraInfo> deviceCameraInfoSet = new ArraySet<>();
                        for (Pair<String, Integer> entry : combination) {
                            deviceCameraInfoSet.add(new DeviceCameraInfo(entry.first, entry.second.intValue()));
                        }
                        this.mConcurrentCameraIdCombinations.add(deviceCameraInfoSet);
                    }
                } catch (RemoteException e4) {
                } catch (ServiceSpecificException e5) {
                    throw new IllegalStateException("Failed to get concurrent camera id combinations", e5);
                }
            } catch (RemoteException e6) {
            }
        }

        public void injectSessionParams(String cameraId, CaptureRequest sessionParams) throws CameraAccessException, SecurityException {
            synchronized (this.mLock) {
                ICameraService cameraService = getCameraService();
                if (cameraService == null) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable.");
                }
                try {
                    cameraService.injectSessionParams(cameraId, sessionParams.getNativeMetadata());
                } catch (RemoteException e) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable.");
                } catch (ServiceSpecificException e2) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }

        private String[] extractCameraIdListLocked(int deviceId, int devicePolicy) {
            List<String> cameraIds = new ArrayList<>();
            for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                int status = this.mDeviceStatus.valueAt(i).intValue();
                DeviceCameraInfo info = this.mDeviceStatus.keyAt(i);
                if (status != 0 && status != 2 && !shouldHideCamera(deviceId, devicePolicy, info)) {
                    cameraIds.add(info.mCameraId);
                }
            }
            return (String[]) cameraIds.toArray(new String[0]);
        }

        private Set<Set<String>> extractConcurrentCameraIdListLocked(int deviceId, int devicePolicy) {
            Set<Set<String>> concurrentCameraIds = new ArraySet<>();
            for (Set<DeviceCameraInfo> deviceCameraInfos : this.mConcurrentCameraIdCombinations) {
                Set<String> extractedCameraIds = new ArraySet<>();
                for (DeviceCameraInfo info : deviceCameraInfos) {
                    Integer status = this.mDeviceStatus.get(info);
                    if (status != null && status.intValue() != 2 && status.intValue() != 0 && !shouldHideCamera(deviceId, devicePolicy, info)) {
                        extractedCameraIds.add(info.mCameraId);
                    }
                }
                if (!extractedCameraIds.isEmpty()) {
                    concurrentCameraIds.add(extractedCameraIds);
                }
            }
            return concurrentCameraIds;
        }

        private static void sortCameraIds(String[] cameraIds) {
            Arrays.sort(cameraIds, new Comparator<String>() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.1
                @Override // java.util.Comparator
                public int compare(String s1, String s2) {
                    int s1Int;
                    int s2Int;
                    try {
                        s1Int = Integer.parseInt(s1);
                    } catch (NumberFormatException e) {
                        s1Int = -1;
                    }
                    try {
                        s2Int = Integer.parseInt(s2);
                    } catch (NumberFormatException e2) {
                        s2Int = -1;
                    }
                    if (s1Int >= 0 && s2Int >= 0) {
                        return s1Int - s2Int;
                    }
                    if (s1Int >= 0) {
                        return -1;
                    }
                    if (s2Int >= 0) {
                        return 1;
                    }
                    return s1.compareTo(s2);
                }
            });
        }

        private boolean shouldHideCamera(int currentDeviceId, int devicePolicy, DeviceCameraInfo info) {
            if (android.companion.virtualdevice.flags.Flags.cameraDeviceAwareness()) {
                return ((devicePolicy == 0 && info.mDeviceId == 0) || currentDeviceId == info.mDeviceId) ? false : true;
            }
            return false;
        }

        private static boolean cameraStatusesContains(CameraStatus[] cameraStatuses, DeviceCameraInfo info) {
            for (CameraStatus c : cameraStatuses) {
                if (c.cameraId.equals(info.mCameraId) && c.deviceId == info.mDeviceId) {
                    return true;
                }
            }
            return false;
        }

        public String[] getCameraIdListNoLazy(int deviceId, int devicePolicy) {
            String[] cameraIds;
            if (sCameraServiceDisabled) {
                return new String[0];
            }
            ICameraServiceListener.Stub testListener = new ICameraServiceListener.Stub() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.2
                @Override // android.hardware.ICameraServiceListener
                public void onStatusChanged(int status, String id, int deviceId2) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onPhysicalCameraStatusChanged(int status, String id, String physicalId, int deviceId2) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStatusChanged(int status, String id, int deviceId2) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStrengthLevelChanged(String id, int newStrengthLevel, int deviceId2) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraAccessPrioritiesChanged() {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraOpened(String id, String clientPackageId, int deviceId2) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraClosed(String id, int deviceId2) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraDeviceStateChanged(String cameraId, int facing, int newCameraState, String clientName, int apiLevel, int userId, int deviceId2) {
                }
            };
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                try {
                    CameraStatus[] cameraStatuses = this.mCameraService.addListener(testListener);
                    this.mCameraService.removeListener(testListener);
                    for (CameraStatus cameraStatus : cameraStatuses) {
                        onStatusChangedLocked(cameraStatus.status, new DeviceCameraInfo(cameraStatus.cameraId, cameraStatus.deviceId));
                    }
                    Set<DeviceCameraInfo> deviceCameraInfos = this.mDeviceStatus.keySet();
                    List<DeviceCameraInfo> deviceInfosToRemove = new ArrayList<>();
                    for (DeviceCameraInfo info : deviceCameraInfos) {
                        if (!cameraStatusesContains(cameraStatuses, info)) {
                            deviceInfosToRemove.add(info);
                        }
                    }
                    for (DeviceCameraInfo info2 : deviceInfosToRemove) {
                        onStatusChangedLocked(0, info2);
                        this.mTorchStatus.remove(info2);
                    }
                } catch (RemoteException e) {
                } catch (ServiceSpecificException e2) {
                    throw new IllegalStateException("Failed to register a camera service listener", e2);
                }
                cameraIds = extractCameraIdListLocked(deviceId, devicePolicy);
            }
            sortCameraIds(cameraIds);
            return (String[]) Arrays.stream(cameraIds).filter(new CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda11
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return CameraManager.CameraManagerGlobal.lambda$getCameraIdListNoLazy$0(i);
                }
            });
        }

        static /* synthetic */ String[] lambda$getCameraIdListNoLazy$0(int x$0) {
            return new String[x$0];
        }

        public String[] getCameraIdList(int deviceId, int devicePolicy) {
            String[] cameraIds;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                cameraIds = extractCameraIdListLocked(deviceId, devicePolicy);
            }
            sortCameraIds(cameraIds);
            return (String[]) Arrays.stream(cameraIds).filter(new CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return CameraManager.CameraManagerGlobal.lambda$getCameraIdList$1(i);
                }
            });
        }

        static /* synthetic */ String[] lambda$getCameraIdList$1(int x$0) {
            return new String[x$0];
        }

        public Set<Set<String>> getConcurrentCameraIds(int deviceId, int devicePolicy) {
            Set<Set<String>> concurrentStreamingCameraIds;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                concurrentStreamingCameraIds = extractConcurrentCameraIdListLocked(deviceId, devicePolicy);
            }
            return concurrentStreamingCameraIds;
        }

        public boolean isConcurrentSessionConfigurationSupported(Map<String, SessionConfiguration> cameraIdsAndSessionConfigurations, int targetSdkVersion, int deviceId, int devicePolicy) throws CameraAccessException {
            if (cameraIdsAndSessionConfigurations == null) {
                throw new IllegalArgumentException("cameraIdsAndSessionConfigurations was null");
            }
            int size = cameraIdsAndSessionConfigurations.size();
            if (size == 0) {
                throw new IllegalArgumentException("camera id and session combination is empty");
            }
            synchronized (this.mLock) {
                boolean subsetFound = false;
                for (Set<DeviceCameraInfo> combination : this.mConcurrentCameraIdCombinations) {
                    Set<DeviceCameraInfo> infos = new ArraySet<>();
                    for (String cameraId : cameraIdsAndSessionConfigurations.keySet()) {
                        infos.add(new DeviceCameraInfo(cameraId, devicePolicy == 0 ? 0 : deviceId));
                    }
                    if (combination.containsAll(infos)) {
                        subsetFound = true;
                    }
                }
                if (!subsetFound) {
                    Log.v(TAG, "isConcurrentSessionConfigurationSupported called with a subset of camera ids not returned by getConcurrentCameraIds");
                    return false;
                }
                CameraIdAndSessionConfiguration[] cameraIdsAndConfigs = new CameraIdAndSessionConfiguration[size];
                int i = 0;
                for (Map.Entry<String, SessionConfiguration> pair : cameraIdsAndSessionConfigurations.entrySet()) {
                    cameraIdsAndConfigs[i] = new CameraIdAndSessionConfiguration(pair.getKey(), pair.getValue());
                    i++;
                }
                try {
                    try {
                        return this.mCameraService.isConcurrentSessionConfigurationSupported(cameraIdsAndConfigs, targetSdkVersion, deviceId, devicePolicy);
                    } catch (ServiceSpecificException e) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                } catch (RemoteException e2) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable", e2);
                }
            }
        }

        public boolean cameraIdHasConcurrentStreamsLocked(String cameraId, int deviceId, int devicePolicy) {
            DeviceCameraInfo info = new DeviceCameraInfo(cameraId, devicePolicy == 0 ? 0 : deviceId);
            if (!this.mDeviceStatus.containsKey(info)) {
                return false;
            }
            for (Set<DeviceCameraInfo> comb : this.mConcurrentCameraIdCombinations) {
                if (comb.contains(info)) {
                    return true;
                }
            }
            return false;
        }

        public void setTorchMode(String cameraId, boolean enabled, int deviceId, int devicePolicy) throws CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (cameraId == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable");
                    }
                    try {
                        try {
                            cameraService.setTorchMode(cameraId, enabled, this.mTorchClientBinder, deviceId, devicePolicy);
                        } catch (RemoteException e) {
                            throw new CameraAccessException(2, "Camera service is currently unavailable");
                        }
                    } catch (ServiceSpecificException e2) {
                        throw ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void turnOnTorchWithStrengthLevel(String cameraId, int torchStrength, int deviceId, int devicePolicy) throws CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (cameraId == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        try {
                            cameraService.turnOnTorchWithStrengthLevel(cameraId, torchStrength, this.mTorchClientBinder, deviceId, devicePolicy);
                        } catch (RemoteException e) {
                            throw new CameraAccessException(2, "Camera service is currently unavailable.");
                        }
                    } catch (ServiceSpecificException e2) {
                        throw ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public int getTorchStrengthLevel(String cameraId, int deviceId, int devicePolicy) throws CameraAccessException {
            int torchStrength;
            synchronized (this.mLock) {
                try {
                    if (cameraId == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        torchStrength = cameraService.getTorchStrengthLevel(cameraId, deviceId, devicePolicy);
                    } catch (RemoteException e) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    } catch (ServiceSpecificException e2) {
                        throw ExceptionUtils.throwAsPublicException(e2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return torchStrength;
        }

        private void handleRecoverableSetupErrors(ServiceSpecificException e) {
            switch (e.errorCode) {
                case 4:
                    Log.w(TAG, e.getMessage());
                    return;
                default:
                    throw new IllegalStateException(e);
            }
        }

        private boolean isAvailable(int status) {
            switch (status) {
                case 1:
                    return true;
                default:
                    return false;
            }
        }

        private boolean validStatus(int status) {
            switch (status) {
                case -2:
                case 0:
                case 1:
                case 2:
                    return true;
                case -1:
                default:
                    return false;
            }
        }

        private boolean validTorchStatus(int status) {
            switch (status) {
                case 0:
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }

        private void postSingleAccessPriorityChangeUpdate(final AvailabilityCallback callback, Executor executor) {
            long ident = Binder.clearCallingIdentity();
            try {
                Objects.requireNonNull(callback);
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.AvailabilityCallback.this.onCameraAccessPrioritiesChanged();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSingleCameraOpenedUpdate(final AvailabilityCallback callback, Executor executor, final String id, final String packageId) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.AvailabilityCallback.this.onCameraOpened(id, packageId);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSingleCameraClosedUpdate(final AvailabilityCallback callback, Executor executor, final String id) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.AvailabilityCallback.this.onCameraClosed(id);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSingleUpdate(final AvailabilityCallback callback, Executor executor, final String id, final String physicalId, int status) {
            long ident;
            if (!CameraManager.isPublicId(id) && callback != null && !callback.mIsHiddenIdPermittedPackage) {
                return;
            }
            Log.i(TAG, String.format("postSingleUpdate device: camera id %s status %s", id, cameraStatusToString(status)));
            if (isAvailable(status)) {
                ident = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraManager.CameraManagerGlobal.lambda$postSingleUpdate$4(physicalId, callback, id);
                        }
                    });
                } finally {
                }
            } else {
                ident = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraManager.CameraManagerGlobal.lambda$postSingleUpdate$5(physicalId, callback, id);
                        }
                    });
                } finally {
                }
            }
        }

        static /* synthetic */ void lambda$postSingleUpdate$4(String physicalId, AvailabilityCallback callback, String id) {
            if (physicalId == null) {
                callback.onCameraAvailable(id);
            } else {
                callback.onPhysicalCameraAvailable(id, physicalId);
            }
        }

        static /* synthetic */ void lambda$postSingleUpdate$5(String physicalId, AvailabilityCallback callback, String id) {
            if (physicalId == null) {
                callback.onCameraUnavailable(id);
            } else {
                callback.onPhysicalCameraUnavailable(id, physicalId);
            }
        }

        private void postSingleTorchUpdate(final TorchCallback callback, Executor executor, final String id, final int status) {
            long ident;
            Log.i(TAG, String.format("postSingleTorchUpdate device: camera id %s status %d", id, Integer.valueOf(status)));
            switch (status) {
                case 1:
                case 2:
                    ident = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$6(CameraManager.TorchCallback.this, id, status);
                            }
                        });
                        return;
                    } finally {
                    }
                default:
                    ident = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$7(CameraManager.TorchCallback.this, id);
                            }
                        });
                        return;
                    } finally {
                    }
            }
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$6(TorchCallback callback, String id, int status) {
            Log.i(TAG, "onTorchModeChanged");
            callback.onTorchModeChanged(id, status == 2);
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$7(TorchCallback callback, String id) {
            Log.i(TAG, "onTorchModeUnavailable");
            callback.onTorchModeUnavailable(id);
        }

        private void postSingleTorchStrengthLevelUpdate(final TorchCallback callback, Executor executor, final String id, final int newStrengthLevel) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.TorchCallback.this.onTorchStrengthLevelChanged(id, newStrengthLevel);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void updateCallbackLocked(AvailabilityCallback callback, Executor executor) {
            for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                DeviceCameraInfo info = this.mDeviceStatus.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Integer status = this.mDeviceStatus.valueAt(i);
                    postSingleUpdate(callback, executor, info.mCameraId, null, status.intValue());
                    if (this.mHasOpenCloseListenerPermission) {
                        postSemSingleUpdate(callback, executor, info.mCameraId, status.intValue());
                    }
                    if ((isAvailable(status.intValue()) || CameraManager.physicalCallbacksAreEnabledForUnavailableCamera()) && this.mUnavailablePhysicalDevices.containsKey(info)) {
                        List<String> unavailableIds = this.mUnavailablePhysicalDevices.get(info);
                        for (String unavailableId : unavailableIds) {
                            postSingleUpdate(callback, executor, info.mCameraId, unavailableId, 0);
                        }
                    }
                }
            }
            if (this.mHasOpenCloseListenerPermission) {
                Log.i(TAG, "updateCallbackLocked: post device state update");
                for (int i2 = 0; i2 < this.mCameraDeviceStates.size(); i2++) {
                    DeviceCameraInfo info2 = this.mCameraDeviceStates.keyAt(i2);
                    CameraDeviceState state = this.mCameraDeviceStates.valueAt(i2);
                    if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info2)) {
                        postSemSingleCameraDeviceStateUpdate(callback, executor, info2.mCameraId, state);
                    }
                }
            }
            for (int i3 = 0; i3 < this.mOpenedDevices.size(); i3++) {
                DeviceCameraInfo info3 = this.mOpenedDevices.keyAt(i3);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info3)) {
                    String clientPackageId = this.mOpenedDevices.valueAt(i3);
                    postSingleCameraOpenedUpdate(callback, executor, info3.mCameraId, clientPackageId);
                }
            }
        }

        private void onStatusChangedLocked(int status, DeviceCameraInfo info) {
            Integer oldStatus;
            if (!validStatus(status)) {
                Log.e(TAG, String.format("Ignoring invalid camera %s status 0x%x for device %d", info.mCameraId, Integer.valueOf(status), Integer.valueOf(info.mDeviceId)));
                return;
            }
            if (status == 0) {
                Integer oldStatus2 = this.mDeviceStatus.remove(info);
                this.mUnavailablePhysicalDevices.remove(info);
                oldStatus = oldStatus2;
            } else {
                Integer oldStatus3 = this.mDeviceStatus.put(info, Integer.valueOf(status));
                if (oldStatus3 == null) {
                    this.mUnavailablePhysicalDevices.put(info, new ArrayList<>());
                }
                oldStatus = oldStatus3;
            }
            if (oldStatus != null && oldStatus.intValue() == status) {
                return;
            }
            int callbackCount = this.mCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                Executor executor = this.mCallbackMap.valueAt(i);
                AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info) && this.mHasOpenCloseListenerPermission) {
                    postSemSingleUpdate(callback, executor, info.mCameraId, status);
                }
            }
            if (oldStatus != null && isAvailable(status) == isAvailable(oldStatus.intValue())) {
                return;
            }
            int callbackCount2 = this.mCallbackMap.size();
            for (int i2 = 0; i2 < callbackCount2; i2++) {
                AvailabilityCallback callback2 = this.mCallbackMap.keyAt(i2);
                if (!shouldHideCamera(callback2.mDeviceId, callback2.mDevicePolicy, info)) {
                    Executor executor2 = this.mCallbackMap.valueAt(i2);
                    postSingleUpdate(callback2, executor2, info.mCameraId, null, status);
                    if (isAvailable(status) && this.mUnavailablePhysicalDevices.containsKey(info)) {
                        List<String> unavailableIds = this.mUnavailablePhysicalDevices.get(info);
                        for (String unavailableId : unavailableIds) {
                            postSingleUpdate(callback2, executor2, info.mCameraId, unavailableId, 0);
                        }
                    }
                }
            }
        }

        private void onPhysicalCameraStatusChangedLocked(int status, DeviceCameraInfo info, String physicalId) {
            if (!validStatus(status)) {
                Log.e(TAG, String.format("Ignoring invalid device %s physical device %s status 0x%x for device %d", info.mCameraId, physicalId, Integer.valueOf(status), Integer.valueOf(info.mDeviceId)));
                return;
            }
            if (!this.mDeviceStatus.containsKey(info) || !this.mUnavailablePhysicalDevices.containsKey(info)) {
                Log.e(TAG, String.format("Camera %s is not present. Ignore physical camera status change", info.mCameraId));
                return;
            }
            List<String> unavailablePhysicalDevices = this.mUnavailablePhysicalDevices.get(info);
            if (!isAvailable(status) && !unavailablePhysicalDevices.contains(physicalId)) {
                unavailablePhysicalDevices.add(physicalId);
            } else if (isAvailable(status) && unavailablePhysicalDevices.contains(physicalId)) {
                unavailablePhysicalDevices.remove(physicalId);
            } else {
                return;
            }
            if (!CameraManager.physicalCallbacksAreEnabledForUnavailableCamera() && !isAvailable(this.mDeviceStatus.get(info).intValue())) {
                Log.i(TAG, String.format("Camera %s is not available. Ignore physical camera status change callback(s)", info.mCameraId));
                return;
            }
            int callbackCount = this.mCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Executor executor = this.mCallbackMap.valueAt(i);
                    postSingleUpdate(callback, executor, info.mCameraId, physicalId, status);
                }
            }
        }

        private void updateTorchCallbackLocked(TorchCallback callback, Executor executor) {
            for (int i = 0; i < this.mTorchStatus.size(); i++) {
                DeviceCameraInfo info = this.mTorchStatus.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Integer status = this.mTorchStatus.valueAt(i);
                    postSingleTorchUpdate(callback, executor, info.mCameraId, status.intValue());
                }
            }
        }

        private void onTorchStatusChangedLocked(int status, DeviceCameraInfo info) {
            if (!validTorchStatus(status)) {
                Log.e(TAG, String.format("Ignoring invalid camera %s torch status 0x%x for device %d", info.mCameraId, Integer.valueOf(status), Integer.valueOf(info.mDeviceId)));
                return;
            }
            Integer oldStatus = this.mTorchStatus.put(info, Integer.valueOf(status));
            if (oldStatus != null && oldStatus.intValue() == status) {
                return;
            }
            int callbackCount = this.mTorchCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                TorchCallback callback = this.mTorchCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Executor executor = this.mTorchCallbackMap.valueAt(i);
                    postSingleTorchUpdate(callback, executor, info.mCameraId, status);
                }
            }
        }

        private void onTorchStrengthLevelChangedLocked(DeviceCameraInfo info, int newStrengthLevel) {
            int callbackCount = this.mTorchCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                TorchCallback callback = this.mTorchCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Executor executor = this.mTorchCallbackMap.valueAt(i);
                    postSingleTorchStrengthLevelUpdate(callback, executor, info.mCameraId, newStrengthLevel);
                }
            }
        }

        public void registerAvailabilityCallback(AvailabilityCallback callback, Executor executor, boolean hasOpenCloseListenerPermission, int deviceId, int devicePolicy) {
            synchronized (this.mLock) {
                this.mHasOpenCloseListenerPermission = hasOpenCloseListenerPermission;
                connectCameraServiceLocked();
                callback.mDeviceId = deviceId;
                callback.mDevicePolicy = devicePolicy;
                Executor oldExecutor = this.mCallbackMap.put(callback, executor);
                if (oldExecutor == null) {
                    updateCallbackLocked(callback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterAvailabilityCallback(AvailabilityCallback callback) {
            synchronized (this.mLock) {
                this.mCallbackMap.remove(callback);
            }
        }

        public void registerTorchCallback(TorchCallback callback, Executor executor, int deviceId, int devicePolicy) {
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                callback.mDeviceId = deviceId;
                callback.mDevicePolicy = devicePolicy;
                Executor oldExecutor = this.mTorchCallbackMap.put(callback, executor);
                if (oldExecutor == null) {
                    updateTorchCallbackLocked(callback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterTorchCallback(TorchCallback callback) {
            synchronized (this.mLock) {
                this.mTorchCallbackMap.remove(callback);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onStatusChanged(int status, String cameraId, int deviceId) throws RemoteException {
            synchronized (this.mLock) {
                onStatusChangedLocked(status, new DeviceCameraInfo(cameraId, deviceId));
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onPhysicalCameraStatusChanged(int status, String cameraId, String physicalCameraId, int deviceId) throws RemoteException {
            synchronized (this.mLock) {
                onPhysicalCameraStatusChangedLocked(status, new DeviceCameraInfo(cameraId, deviceId), physicalCameraId);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStatusChanged(int status, String cameraId, int deviceId) throws RemoteException {
            synchronized (this.mLock) {
                onTorchStatusChangedLocked(status, new DeviceCameraInfo(cameraId, deviceId));
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStrengthLevelChanged(String cameraId, int newStrengthLevel, int deviceId) throws RemoteException {
            synchronized (this.mLock) {
                onTorchStrengthLevelChangedLocked(new DeviceCameraInfo(cameraId, deviceId), newStrengthLevel);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraAccessPrioritiesChanged() {
            synchronized (this.mLock) {
                int callbackCount = this.mCallbackMap.size();
                for (int i = 0; i < callbackCount; i++) {
                    Executor executor = this.mCallbackMap.valueAt(i);
                    AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                    postSingleAccessPriorityChangeUpdate(callback, executor);
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpened(String cameraId, String clientPackageId, int deviceId) {
            synchronized (this.mLock) {
                onCameraOpenedLocked(new DeviceCameraInfo(cameraId, deviceId), clientPackageId);
            }
        }

        private void onCameraOpenedLocked(DeviceCameraInfo info, String clientPackageId) {
            String oldApk = this.mOpenedDevices.put(info, clientPackageId);
            if (oldApk != null) {
                if (oldApk.equals(clientPackageId)) {
                    Log.w(TAG, "onCameraOpened was previously called for " + oldApk + " and is now again called for the same package name, so no new client visible update will be sent");
                    return;
                }
                Log.w(TAG, "onCameraOpened was previously called for " + oldApk + " and is now called for " + clientPackageId + " without onCameraClosed being called first");
            }
            int callbackCount = this.mCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Executor executor = this.mCallbackMap.valueAt(i);
                    postSingleCameraOpenedUpdate(callback, executor, info.mCameraId, clientPackageId);
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraClosed(String cameraId, int deviceId) {
            synchronized (this.mLock) {
                onCameraClosedLocked(new DeviceCameraInfo(cameraId, deviceId));
            }
        }

        private void onCameraClosedLocked(DeviceCameraInfo info) {
            this.mOpenedDevices.remove(info);
            int callbackCount = this.mCallbackMap.size();
            for (int i = 0; i < callbackCount; i++) {
                AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    Executor executor = this.mCallbackMap.valueAt(i);
                    postSingleCameraClosedUpdate(callback, executor, info.mCameraId);
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraDeviceStateChanged(String cameraId, int facing, int newCameraState, String clientName, int apiLevel, int userId, int deviceId) {
            synchronized (this.mLock) {
                CameraDeviceState state = new CameraDeviceState(facing, newCameraState, clientName, userId);
                Log.i(TAG, "Camera " + cameraId + " " + state + " API Level " + apiLevel + " User Id " + userId + "Device Id " + deviceId);
                onCameraDeviceStateChangedLocked(state, new DeviceCameraInfo(cameraId, deviceId));
            }
        }

        private void onCameraDeviceStateChangedLocked(CameraDeviceState state, DeviceCameraInfo info) {
            CameraDeviceState oldState = this.mCameraDeviceStates.put(info, state);
            if (oldState != null && oldState.equals(state)) {
                Log.i(TAG, String.format("CameraDevice (%s, %d) state changed to (%s), which is what it already was, skip callback", info.mCameraId, Integer.valueOf(info.mDeviceId), state));
                return;
            }
            int semCallbackCount = this.mSemCameraDeviceStateCallbackMap.size();
            for (int i = 0; i < semCallbackCount; i++) {
                Executor executor = this.mSemCameraDeviceStateCallbackMap.valueAt(i);
                SemCameraDeviceStateCallback callback = this.mSemCameraDeviceStateCallbackMap.keyAt(i);
                if (!shouldHideCamera(callback.mDeviceId, callback.mDevicePolicy, info)) {
                    postSingleCameraDeviceStateUpdate(callback, executor, info.mCameraId, state);
                }
            }
            int callbackCount = this.mCallbackMap.size();
            for (int i2 = 0; i2 < callbackCount; i2++) {
                Executor executor2 = this.mCallbackMap.valueAt(i2);
                AvailabilityCallback callback2 = this.mCallbackMap.keyAt(i2);
                if (!shouldHideCamera(callback2.mDeviceId, callback2.mDevicePolicy, info) && this.mHasOpenCloseListenerPermission) {
                    Log.i(TAG, "onCameraDeviceStateChangedLocked: post device state update");
                    postSemSingleCameraDeviceStateUpdate(callback2, executor2, info.mCameraId, state);
                }
            }
        }

        public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback, Executor executor, int deviceId, int devicePolicy) {
            synchronized (this.mLock) {
                Log.i(TAG, "registerSemCameraDeviceStateCallback");
                connectCameraServiceLocked();
                callback.mDeviceId = deviceId;
                callback.mDevicePolicy = devicePolicy;
                Executor oldExecutor = this.mSemCameraDeviceStateCallbackMap.put(callback, executor);
                if (oldExecutor == null) {
                    updateSemCameraDeviceStateCallbackLocked(callback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback) {
            synchronized (this.mLock) {
                Log.i(TAG, "unregisterSemCameraDeviceStateCallback");
                this.mSemCameraDeviceStateCallbackMap.remove(callback);
            }
        }

        private void updateSemCameraDeviceStateCallbackLocked(SemCameraDeviceStateCallback callback, Executor executor) {
            for (int i = 0; i < this.mCameraDeviceStates.size(); i++) {
                DeviceCameraInfo info = this.mCameraDeviceStates.keyAt(i);
                CameraDeviceState state = this.mCameraDeviceStates.valueAt(i);
                postSingleCameraDeviceStateUpdate(callback, executor, info.mCameraId, state);
            }
        }

        private void postSingleCameraDeviceStateUpdate(final SemCameraDeviceStateCallback callback, Executor executor, final String cameraId, final CameraDeviceState state) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!callback.isExtended && state.mDeviceState >= 100) {
                            return;
                        }
                        callback.onCameraDeviceStateChanged(cameraId, state.mFacing, state.mDeviceState, state.mClientName);
                        callback.onCameraDeviceStateChanged(cameraId, state.mFacing, state.mDeviceState, state.mClientName, state.mUserId);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSemSingleUpdate(final AvailabilityCallback callback, Executor executor, final String id, final int status) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.AvailabilityCallback.this.onSemCameraDeviceRawStatus(id, status);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSemSingleCameraDeviceStateUpdate(final AvailabilityCallback callback, Executor executor, final String cameraId, final CameraDeviceState state) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.4
                    @Override // java.lang.Runnable
                    public void run() {
                        int lensFacing;
                        switch (state.mFacing) {
                            case 0:
                                lensFacing = 1;
                                break;
                            case 1:
                                lensFacing = 0;
                                break;
                            case 2:
                                lensFacing = 2;
                                break;
                            default:
                                Log.w(CameraManagerGlobal.TAG, "Unknown lens facing.");
                        }
                        switch (state.mDeviceState) {
                            case 0:
                                callback.onSemCameraDeviceOpen(cameraId, lensFacing, state.mClientName);
                                break;
                            case 1:
                                callback.onSemCameraDeviceActive(cameraId, lensFacing, state.mClientName);
                                break;
                            case 2:
                                callback.onSemCameraDeviceIdle(cameraId, lensFacing, state.mClientName);
                                break;
                            case 3:
                                callback.onSemCameraDeviceClose(cameraId, lensFacing, state.mClientName);
                                break;
                            case 100:
                            case 101:
                                break;
                            default:
                                Log.w(CameraManagerGlobal.TAG, "Unknown device state");
                                break;
                        }
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void scheduleCameraServiceReconnectionLocked() {
            if (this.mCallbackMap.isEmpty() && this.mTorchCallbackMap.isEmpty() && this.mSemCameraDeviceStateCallbackMap.isEmpty()) {
                return;
            }
            try {
                this.mScheduler.schedule(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraManager.CameraManagerGlobal.this.lambda$scheduleCameraServiceReconnectionLocked$10();
                    }
                }, 1000L, TimeUnit.MILLISECONDS);
            } catch (RejectedExecutionException e) {
                Log.e(TAG, "Failed to schedule camera service re-connect: " + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleCameraServiceReconnectionLocked$10() {
            ICameraService cameraService = getCameraService();
            if (cameraService == null) {
                synchronized (this.mLock) {
                    scheduleCameraServiceReconnectionLocked();
                }
                return;
            }
            synchronized (this.mLock) {
                try {
                    int callbackCount = this.mCallbackMap.size();
                    for (int i = 0; i < callbackCount; i++) {
                        AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
                        if (callback.mIsRegisteredWhileServiceDown) {
                            callback.mIsHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
                            callback.mIsRegisteredWhileServiceDown = false;
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Camera service is currently unavailable", e);
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (this.mLock) {
                if (this.mCameraService == null) {
                    return;
                }
                this.mCameraService = null;
                for (int i = this.mDeviceStatus.size() - 1; i >= 0; i--) {
                    DeviceCameraInfo info = this.mDeviceStatus.keyAt(i);
                    onStatusChangedLocked(0, info);
                    if (this.mHasOpenCloseListenerPermission) {
                        onCameraClosedLocked(info);
                    }
                }
                for (int i2 = 0; i2 < this.mTorchStatus.size(); i2++) {
                    DeviceCameraInfo info2 = this.mTorchStatus.keyAt(i2);
                    onTorchStatusChangedLocked(0, info2);
                }
                for (int i3 = 0; i3 < this.mCameraDeviceStates.size(); i3++) {
                    DeviceCameraInfo info3 = this.mCameraDeviceStates.keyAt(i3);
                    CameraDeviceState state = this.mCameraDeviceStates.valueAt(i3);
                    onCameraDeviceStateChangedLocked(new CameraDeviceState(state.mFacing, 3, "android.system", 0), info3);
                }
                this.mConcurrentCameraIdCombinations.clear();
                scheduleCameraServiceReconnectionLocked();
            }
        }

        private static final class DeviceCameraInfo {
            private final String mCameraId;
            private final int mDeviceId;

            DeviceCameraInfo(String cameraId, int deviceId) {
                this.mCameraId = cameraId;
                this.mDeviceId = deviceId;
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                DeviceCameraInfo that = (DeviceCameraInfo) o;
                if (this.mDeviceId == that.mDeviceId && Objects.equals(this.mCameraId, that.mCameraId)) {
                    return true;
                }
                return false;
            }

            public int hashCode() {
                return Objects.hash(this.mCameraId, Integer.valueOf(this.mDeviceId));
            }
        }

        private static String cameraStatusToString(int cameraStatus) {
            switch (cameraStatus) {
            }
            return "STATUS_UNKNOWN";
        }
    }

    public void startDeviceInjector(String[] targetPackages, String[] targetCameraIds, String sourceCameraId, Executor executor, DeviceInjectorSession.StatusCallback callback) throws CameraAccessException, SecurityException, IllegalArgumentException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        if (targetPackages == null) {
            throw new IllegalArgumentException("targetPackages was null");
        }
        if (targetCameraIds == null) {
            throw new IllegalArgumentException("targetCameraIds was null");
        }
        if (sourceCameraId == null || sourceCameraId.isEmpty()) {
            throw new IllegalArgumentException("sourceCameraId was null or empty");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        for (String targetPackage : targetPackages) {
            if (targetPackage == null || targetPackage.isEmpty()) {
                throw new IllegalArgumentException("targetPackages contains empty of null package name");
            }
        }
        for (String targetId : targetCameraIds) {
            if (targetId == null || targetId.isEmpty()) {
                throw new IllegalArgumentException("targetCameraIds contains empty of null camera Id");
            }
            if (sourceCameraId.equals(targetId)) {
                throw new IllegalArgumentException("targetCameraIds contains source camera Id");
            }
        }
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                DeviceInjectorSessionImpl injectorSessionImpl = new DeviceInjectorSessionImpl(callback, executor);
                IDeviceInjectorCallback deviceInjectorCallback = injectorSessionImpl.getCallback();
                cameraService.startDeviceInjector(targetPackages, targetCameraIds, sourceCameraId, deviceInjectorCallback);
            } catch (RemoteException e) {
                ServiceSpecificException sse = new ServiceSpecificException(4, "Camera service is currently unavailable");
                ExceptionUtils.throwAsPublicException(sse);
            } catch (ServiceSpecificException e2) {
                ExceptionUtils.throwAsPublicException(e2);
            }
        }
    }

    public void startRemoteDeviceInjector(String[] targetPackages, String[] targetCameraIds, DeviceInjectorSession.RemoteDevice sourceDevice, Executor executor, DeviceInjectorSession.StatusCallback callback) throws CameraAccessException, SecurityException, IllegalArgumentException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        if (targetPackages == null) {
            throw new IllegalArgumentException("targetPackages was null");
        }
        if (targetCameraIds == null) {
            throw new IllegalArgumentException("targetCameraIds was null");
        }
        if (sourceDevice == null) {
            throw new IllegalArgumentException("sourceDevice was null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        for (String targetPackage : targetPackages) {
            if (targetPackage == null || targetPackage.isEmpty()) {
                throw new IllegalArgumentException("targetPackages contains empty of null package name");
            }
        }
        for (String targetId : targetCameraIds) {
            if (targetId == null || targetId.isEmpty()) {
                throw new IllegalArgumentException("targetCameraIds contains empty of null camera Id");
            }
        }
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                DeviceInjectorSessionImpl injectorSessionImpl = new DeviceInjectorSessionImpl(callback, executor);
                IDeviceInjectorCallback deviceInjectorCallback = injectorSessionImpl.getCallback();
                IRemoteDevice deviceInjectorDevice = injectorSessionImpl.getRemoteDevice(sourceDevice);
                cameraService.startRemoteDeviceInjector(targetPackages, targetCameraIds, deviceInjectorDevice, deviceInjectorCallback);
            } catch (RemoteException e) {
                ServiceSpecificException sse = new ServiceSpecificException(4, "Camera service is currently unavailable");
                ExceptionUtils.throwAsPublicException(sse);
            } catch (ServiceSpecificException e2) {
                ExceptionUtils.throwAsPublicException(e2);
            }
        }
    }
}
