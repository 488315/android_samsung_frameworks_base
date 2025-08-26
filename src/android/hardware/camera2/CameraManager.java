package android.hardware.camera2;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.compat.CompatChanges;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.AttributionSourceState;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.CameraExtensionSessionStats;
import android.hardware.CameraStatus;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceListener;
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
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Display;
import android.window.DesktopModeFlags;
import com.android.internal.R;
import com.android.internal.camera.flags.Flags;
import com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.android.hardware.devicestate.feature.flags.FeatureFlagsImpl;
import com.android.internal.util.ArrayUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
    private static final int USE_CALLING_PID = -1;
    private static final int USE_CALLING_UID = -1;
    private static final int kInternalIdStart = 20;
    private static final int kVirtualIdStart = 100;
    private final boolean DEBUG = false;
    private final Map<String, Map<String, StreamConfiguration[]>> mCameraIdToMultiResolutionStreamConfigurationMap = new HashMap();
    private final Context mContext;
    private final boolean mHasOpenCloseListenerPermission;
    private Boolean mHiddenCameraPermittedState;
    private final Object mLock;
    private VirtualDeviceManager mVirtualDeviceManager;

    public static abstract class AvailabilityCallback {
        private int mDeviceId;
        private int mDevicePolicy;
        private boolean mIsHiddenIdPermittedPackage;
        private boolean mIsRegisteredWhileServiceDown;
        private String mPackageName;

        public void onCameraAccessPrioritiesChanged() {
        }

        public void onCameraAvailable(String str) {
        }

        @SystemApi
        public void onCameraClosed(String str) {
        }

        @SystemApi
        public void onCameraOpened(String str, String str2) {
        }

        public void onCameraUnavailable(String str) {
        }

        public void onPhysicalCameraAvailable(String str, String str2) {
        }

        public void onPhysicalCameraUnavailable(String str, String str2) {
        }

        public void onSemCameraDeviceActive(String str, int i, String str2) {
        }

        public void onSemCameraDeviceClose(String str, int i, String str2) {
        }

        public void onSemCameraDeviceIdle(String str, int i, String str2) {
        }

        public void onSemCameraDeviceOpen(String str, int i, String str2) {
        }

        public void onSemCameraDeviceRawStatus(String str, int i) {
        }
    }

    public interface DeviceStateListener {
        void onDeviceStateChanged(boolean z);
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

        public void onCameraDeviceStateChanged(String str, int i, int i2, String str2) {
        }

        public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3) {
        }
    }

    public static abstract class TorchCallback {
        private int mDeviceId;
        private int mDevicePolicy;

        public void onTorchModeChanged(String str, boolean z) {
        }

        public void onTorchModeUnavailable(String str) {
        }

        public void onTorchStrengthLevelChanged(String str, int i) {
        }
    }

    private static int getRotationOverrideForCompatFreeform(int i) {
        return (i == 5 || i == 2) ? 2 : 0;
    }

    private static boolean isInCameraCompatMode(int i) {
        return (i == 0 || i == 1) ? false : true;
    }

    private static class CameraDeviceState {
        public final String mClientName;
        public final int mDeviceState;
        public final int mFacing;
        public final int mUserId;

        public CameraDeviceState(int i, int i2, String str, int i3) {
            this.mFacing = i;
            this.mDeviceState = i2;
            this.mClientName = str;
            this.mUserId = i3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CameraDeviceState)) {
                return false;
            }
            CameraDeviceState cameraDeviceState = (CameraDeviceState) obj;
            return cameraDeviceState.mFacing == this.mFacing && cameraDeviceState.mDeviceState == this.mDeviceState && cameraDeviceState.mClientName.equals(this.mClientName) && cameraDeviceState.mUserId == this.mUserId;
        }

        public String toString() {
            return String.format("facing %s state now %s for client %s", cameraFacingToString(this.mFacing), cameraStateToString(this.mDeviceState), this.mClientName);
        }

        private static String cameraStateToString(int i) {
            if (i == 0) {
                return "CAMERA_STATE_OPEN";
            }
            if (i == 1) {
                return "CAMERA_STATE_ACTIVE";
            }
            if (i == 2) {
                return "CAMERA_STATE_IDLE";
            }
            if (i == 3) {
                return "CAMERA_STATE_CLOSED";
            }
            if (i == 100) {
                return "CAMERA_STATE_OPENING";
            }
            if (i == 101) {
                return "CAMERA_STATE_OPENING_FAILED";
            }
            return "CAMERA_STATE_UNKNOWN";
        }

        private static String cameraFacingToString(int i) {
            if (i == 0) {
                return "CAMERA_FACING_BACK";
            }
            if (i == 1) {
                return "CAMERA_FACING_FRONT";
            }
            if (i == 2) {
                return "CAMERA_FACING_EXTERNAL";
            }
            return "CAMERA_FACING_UNKNOWN";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPublicId(String str) {
        return ((Boolean) Optional.ofNullable(str).map(new Function() { // from class: android.hardware.camera2.CameraManager$$ExternalSyntheticLambda0
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

    static /* synthetic */ Boolean lambda$isPublicId$0(String str) {
        try {
            return Boolean.valueOf(Integer.parseInt(str) < 20 || 100 <= Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return true;
        }
    }

    static /* synthetic */ NullPointerException lambda$isPublicId$1() {
        return new NullPointerException("Camera ID must not be null");
    }

    public CameraManager(Context context) {
        Object obj = new Object();
        this.mLock = obj;
        this.mHiddenCameraPermittedState = null;
        synchronized (obj) {
            this.mContext = context;
            this.mHasOpenCloseListenerPermission = context.checkSelfPermission("android.permission.CAMERA_OPEN_CLOSE_LISTENER") == 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
        private ArrayList<WeakReference<DeviceStateListener>> mDeviceStateListeners = new ArrayList<>();
        private final FeatureFlags mDeviceStateManagerFlags = new FeatureFlagsImpl();
        private boolean mFoldedDeviceState;
        private final int[] mFoldedDeviceStates;

        public FoldStateListener(Context context) {
            this.mFoldedDeviceStates = context.getResources().getIntArray(R.array.config_foldedDeviceStates);
        }

        private synchronized void handleStateChange(DeviceState deviceState) {
            boolean zContains;
            if (this.mDeviceStateManagerFlags.deviceStatePropertyMigration()) {
                zContains = deviceState.hasProperty(11);
            } else {
                zContains = ArrayUtils.contains(this.mFoldedDeviceStates, deviceState.getIdentifier());
            }
            this.mFoldedDeviceState = zContains;
            Iterator<WeakReference<DeviceStateListener>> it = this.mDeviceStateListeners.iterator();
            while (it.hasNext()) {
                DeviceStateListener deviceStateListener = it.next().get();
                if (deviceStateListener != null) {
                    deviceStateListener.onDeviceStateChanged(zContains);
                } else {
                    it.remove();
                }
            }
        }

        public synchronized void addDeviceStateListener(DeviceStateListener deviceStateListener) {
            deviceStateListener.onDeviceStateChanged(this.mFoldedDeviceState);
            this.mDeviceStateListeners.removeIf(new Predicate() { // from class: android.hardware.camera2.CameraManager$FoldStateListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CameraManager.FoldStateListener.lambda$addDeviceStateListener$0((WeakReference) obj);
                }
            });
            this.mDeviceStateListeners.add(new WeakReference<>(deviceStateListener));
        }

        static /* synthetic */ boolean lambda$addDeviceStateListener$0(WeakReference weakReference) {
            return weakReference.get() == null;
        }

        @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
        public void onDeviceStateChanged(DeviceState deviceState) {
            handleStateChange(deviceState);
        }
    }

    public void registerDeviceStateListener(CameraCharacteristics cameraCharacteristics) {
        CameraManagerGlobal.get().registerDeviceStateListener(cameraCharacteristics, this.mContext);
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

    public boolean isConcurrentSessionConfigurationSupported(Map<String, SessionConfiguration> map) throws CameraAccessException {
        return CameraManagerGlobal.get().isConcurrentSessionConfigurationSupported(map, this.mContext.getApplicationInfo().targetSdkVersion, getClientAttribution(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerAvailabilityCallback(AvailabilityCallback availabilityCallback, Handler handler) {
        if (availabilityCallback != null) {
            availabilityCallback.mPackageName = this.mContext.getOpPackageName();
            boolean zIsHiddenIdPermittedPackage = false;
            availabilityCallback.mIsRegisteredWhileServiceDown = false;
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                Log.e(TAG, "Camera service is currently unavailable");
                availabilityCallback.mIsRegisteredWhileServiceDown = true;
            } else {
                try {
                    zIsHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(availabilityCallback.mPackageName);
                } catch (RemoteException e) {
                    availabilityCallback.mIsRegisteredWhileServiceDown = true;
                    Log.e(TAG, "Camera service is currently unavailable", e);
                }
            }
            availabilityCallback.mIsHiddenIdPermittedPackage = zIsHiddenIdPermittedPackage;
            Log.i(TAG, "registerAvailabilityCallback: Is device callback = " + this.mHasOpenCloseListenerPermission);
        }
        CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mHasOpenCloseListenerPermission, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerAvailabilityCallback(Executor executor, AvailabilityCallback availabilityCallback) {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (availabilityCallback != null) {
            availabilityCallback.mPackageName = this.mContext.getOpPackageName();
            boolean zIsHiddenIdPermittedPackage = false;
            availabilityCallback.mIsRegisteredWhileServiceDown = false;
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                Log.e(TAG, "Camera service is currently unavailable");
                availabilityCallback.mIsRegisteredWhileServiceDown = true;
            } else {
                try {
                    zIsHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(availabilityCallback.mPackageName);
                } catch (RemoteException e) {
                    availabilityCallback.mIsRegisteredWhileServiceDown = true;
                    Log.e(TAG, "Camera service is currently unavailable", e);
                }
            }
            availabilityCallback.mIsHiddenIdPermittedPackage = zIsHiddenIdPermittedPackage;
            Log.i(TAG, "registerAvailabilityCallback: Is device callback = " + this.mHasOpenCloseListenerPermission);
        }
        CameraManagerGlobal.get().registerAvailabilityCallback(availabilityCallback, executor, this.mHasOpenCloseListenerPermission, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback availabilityCallback) {
        CameraManagerGlobal.get().unregisterAvailabilityCallback(availabilityCallback);
    }

    public void registerTorchCallback(TorchCallback torchCallback, Handler handler) {
        CameraManagerGlobal.get().registerTorchCallback(torchCallback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerTorchCallback(Executor executor, TorchCallback torchCallback) {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        CameraManagerGlobal.get().registerTorchCallback(torchCallback, executor, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void unregisterTorchCallback(TorchCallback torchCallback) {
        CameraManagerGlobal.get().unregisterTorchCallback(torchCallback);
    }

    public int getDevicePolicyFromContext(Context context) {
        if (context.getDeviceId() == 0) {
            return 0;
        }
        if (this.mVirtualDeviceManager == null) {
            this.mVirtualDeviceManager = (VirtualDeviceManager) context.getSystemService(VirtualDeviceManager.class);
        }
        VirtualDeviceManager virtualDeviceManager = this.mVirtualDeviceManager;
        if (virtualDeviceManager == null) {
            return 0;
        }
        return virtualDeviceManager.getDevicePolicy(context.getDeviceId(), 5);
    }

    public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback semCameraDeviceStateCallback, Handler handler) {
        CameraManagerGlobal.get().registerSemCameraDeviceStateCallback(semCameraDeviceStateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext));
    }

    public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback semCameraDeviceStateCallback, Handler handler, boolean z) {
        semCameraDeviceStateCallback.isExtended = z;
        registerSemCameraDeviceStateCallback(semCameraDeviceStateCallback, handler);
    }

    public void unregisterSemCameraDeviceStateCallback(SemCameraDeviceStateCallback semCameraDeviceStateCallback) {
        CameraManagerGlobal.get().unregisterSemCameraDeviceStateCallback(semCameraDeviceStateCallback);
    }

    private Size getDisplaySize() {
        Size size = new Size(0, 0);
        try {
            Display display = ((DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE)).getDisplay(0);
            if (display == null) {
                Log.e(TAG, "Invalid default display!");
                return size;
            }
            Point point = new Point();
            display.getRealSize(point);
            int i = point.x;
            int i2 = point.y;
            if (i2 > i) {
                i2 = i;
                i = point.y;
            }
            return new Size(i, i2);
        } catch (Exception e) {
            Log.e(TAG, "getDisplaySize Failed. " + e);
            return size;
        }
    }

    private Map<String, StreamConfiguration[]> getPhysicalCameraMultiResolutionConfigs(String str, CameraMetadataNative cameraMetadataNative, ICameraService iCameraService) throws CameraAccessException {
        if (this.mCameraIdToMultiResolutionStreamConfigurationMap.containsKey(str)) {
            return this.mCameraIdToMultiResolutionStreamConfigurationMap.get(str);
        }
        HashMap map = new HashMap();
        this.mCameraIdToMultiResolutionStreamConfigurationMap.put(str, map);
        Boolean bool = (Boolean) cameraMetadataNative.get(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED);
        if (bool != null && bool.booleanValue()) {
            Set<String> physicalCameraIds = cameraMetadataNative.getPhysicalCameraIds();
            if (physicalCameraIds.size() == 0 && cameraMetadataNative.isUltraHighResolutionSensor()) {
                StreamConfiguration[] streamConfigurationArr = (StreamConfiguration[]) cameraMetadataNative.get(CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
                if (streamConfigurationArr != null) {
                    map.put(str, streamConfigurationArr);
                    return map;
                }
            } else {
                try {
                    for (String str2 : physicalCameraIds) {
                        ICameraService iCameraService2 = iCameraService;
                        StreamConfiguration[] streamConfigurationArr2 = (StreamConfiguration[]) iCameraService2.getCameraCharacteristics(str2, this.mContext.getApplicationInfo().targetSdkVersion, 0, getClientAttribution(0, false), 0).get(CameraCharacteristics.SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
                        if (streamConfigurationArr2 != null) {
                            map.put(str2, streamConfigurationArr2);
                        }
                        iCameraService = iCameraService2;
                    }
                } catch (RemoteException unused) {
                    throw ExceptionUtils.throwAsPublicException(new ServiceSpecificException(4, "Camera service is currently unavailable"));
                }
            }
        }
        return map;
    }

    public CameraCharacteristics getCameraCharacteristics(String str) throws CameraAccessException {
        return getCameraCharacteristics(str, getRotationOverride(this.mContext));
    }

    public CameraCharacteristics getCameraCharacteristics(String str, boolean z) throws CameraAccessException {
        return getCameraCharacteristics(str, z ? 1 : 0);
    }

    private CameraCharacteristics getCameraCharacteristics(String str, int i) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristicsPrepareCameraCharacteristics;
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        synchronized (this.mLock) {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                throw new CameraAccessException(2, "Camera service is currently unavailable");
            }
            try {
                if (!isPublicId(str)) {
                    String opPackageName = this.mContext.getOpPackageName();
                    if (this.mHiddenCameraPermittedState == null) {
                        this.mHiddenCameraPermittedState = Boolean.valueOf(cameraService.isHiddenIdPermittedPackage(opPackageName));
                    }
                    if (!this.mHiddenCameraPermittedState.booleanValue()) {
                        throw new IllegalArgumentException(String.format("Unknown camera ID %s", str));
                    }
                    try {
                        try {
                            cameraCharacteristicsPrepareCameraCharacteristics = prepareCameraCharacteristics(str, cameraService.getCameraCharacteristics(str, this.mContext.getApplicationInfo().targetSdkVersion, i, getClientAttribution(), getDevicePolicyFromContext(this.mContext)), cameraService);
                        } catch (ServiceSpecificException e) {
                            throw ExceptionUtils.throwAsPublicException(e);
                        }
                    } catch (RemoteException e2) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable", e2);
                    }
                } else {
                    cameraCharacteristicsPrepareCameraCharacteristics = prepareCameraCharacteristics(str, cameraService.getCameraCharacteristics(str, this.mContext.getApplicationInfo().targetSdkVersion, i, getClientAttribution(), getDevicePolicyFromContext(this.mContext)), cameraService);
                }
            } catch (RemoteException e3) {
                throw new CameraAccessException(2, "Camera service is currently unavailable", e3);
            }
        }
        registerDeviceStateListener(cameraCharacteristicsPrepareCameraCharacteristics);
        return cameraCharacteristicsPrepareCameraCharacteristics;
    }

    public CameraCharacteristics prepareCameraCharacteristics(String str, CameraMetadataNative cameraMetadataNative, ICameraService iCameraService) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics;
        synchronized (this.mLock) {
            try {
                cameraMetadataNative.setCameraId(Integer.parseInt(str));
            } catch (NumberFormatException unused) {
                Log.v(TAG, "Failed to parse camera Id " + str + " to integer");
            }
            cameraMetadataNative.setHasMandatoryConcurrentStreams(CameraManagerGlobal.get().cameraIdHasConcurrentStreams(str, this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext)));
            cameraMetadataNative.setDisplaySize(getDisplaySize());
            Map<String, StreamConfiguration[]> physicalCameraMultiResolutionConfigs = getPhysicalCameraMultiResolutionConfigs(str, cameraMetadataNative, iCameraService);
            if (!physicalCameraMultiResolutionConfigs.isEmpty()) {
                cameraMetadataNative.setMultiResolutionStreamConfigurationMap(physicalCameraMultiResolutionConfigs);
            }
            cameraCharacteristics = new CameraCharacteristics(cameraMetadataNative);
        }
        return cameraCharacteristics;
    }

    public CameraExtensionCharacteristics getCameraExtensionCharacteristics(String str) throws CameraAccessException {
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        Map<String, CameraCharacteristics> physicalIdToCharsMap = getPhysicalIdToCharsMap(cameraCharacteristics);
        physicalIdToCharsMap.put(str, cameraCharacteristics);
        return new CameraExtensionCharacteristics(this.mContext, str, physicalIdToCharsMap);
    }

    public Map<String, CameraCharacteristics> getPhysicalIdToCharsMap(CameraCharacteristics cameraCharacteristics) throws CameraAccessException {
        HashMap map = new HashMap();
        for (String str : cameraCharacteristics.getPhysicalCameraIds()) {
            map.put(str, getCameraCharacteristics(str));
        }
        return map;
    }

    public CameraDevice.CameraDeviceSetup getCameraDeviceSetup(String str) throws CameraAccessException {
        if (!isCameraDeviceSetupSupported(str)) {
            throw new UnsupportedOperationException("CameraDeviceSetup is not supported for Camera ID: " + str);
        }
        return getCameraDeviceSetupUnsafe(str);
    }

    private CameraDevice.CameraDeviceSetup getCameraDeviceSetupUnsafe(String str) {
        return new CameraDeviceSetupImpl(str, this, this.mContext);
    }

    public boolean isCameraDeviceSetupSupported(String str) throws CameraAccessException {
        if (str == null) {
            throw new IllegalArgumentException("Camera ID was null");
        }
        if (CameraManagerGlobal.sCameraServiceDisabled || !Arrays.asList(CameraManagerGlobal.get().getCameraIdList(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext))).contains(str)) {
            throw new IllegalArgumentException("Camera ID '" + str + "' not available on device.");
        }
        return CameraDeviceSetupImpl.isCameraDeviceSetupSupported(getCameraCharacteristics(str));
    }

    @SystemApi
    public boolean isCameraDeviceSharingSupported(String str) throws CameraAccessException {
        if (str == null) {
            throw new IllegalArgumentException("Camera ID was null");
        }
        if (!CameraManagerGlobal.sCameraServiceDisabled && Arrays.asList(CameraManagerGlobal.get().getCameraIdList(this.mContext.getDeviceId(), getDevicePolicyFromContext(this.mContext))).contains(str)) {
            return ((long[]) getCameraCharacteristics(str).get(CameraCharacteristics.SHARED_SESSION_OUTPUT_CONFIGURATIONS)) != null;
        }
        throw new IllegalArgumentException("Camera ID '" + str + "' not available on device.");
    }

    public AttributionSourceState getClientAttribution(int i, boolean z) {
        AttributionSource attributionSource = this.mContext.getAttributionSource();
        if (i != -1) {
            attributionSource = attributionSource.withDeviceId(i);
        }
        AttributionSourceState attributionSourceStateAsState = attributionSource.asState();
        if (Flags.dataDeliveryPermissionChecks() && z) {
            return attributionSourceStateAsState;
        }
        AttributionSourceState attributionSourceState = new AttributionSourceState();
        attributionSourceState.uid = -1;
        attributionSourceState.pid = -1;
        attributionSourceState.deviceId = attributionSourceStateAsState.deviceId;
        attributionSourceState.packageName = this.mContext.getOpPackageName();
        attributionSourceState.attributionTag = this.mContext.getAttributionTag();
        attributionSourceState.next = new AttributionSourceState[0];
        return attributionSourceState;
    }

    public AttributionSourceState getClientAttribution(boolean z) {
        return getClientAttribution(-1, z);
    }

    public AttributionSourceState getClientAttribution() {
        return getClientAttribution(-1, false);
    }

    private CameraDevice openCameraDeviceUserAsync(String str, CameraDevice.StateCallback stateCallback, Executor executor, int i, int i2, boolean z) throws CameraAccessException {
        CameraDeviceImpl cameraDeviceImpl;
        ICameraService cameraService;
        CameraCharacteristics cameraCharacteristics = getCameraCharacteristics(str);
        synchronized (this.mLock) {
            ICameraDeviceUser iCameraDeviceUserConnectDevice = null;
            cameraDeviceImpl = new CameraDeviceImpl(str, stateCallback, executor, cameraCharacteristics, this, this.mContext.getApplicationInfo().targetSdkVersion, this.mContext, (Flags.cameraDeviceSetup() && CameraDeviceSetupImpl.isCameraDeviceSetupSupported(cameraCharacteristics)) ? getCameraDeviceSetupUnsafe(str) : null, z);
            CameraDeviceImpl.CameraDeviceCallbacks callbacks = cameraDeviceImpl.getCallbacks();
            try {
                try {
                    cameraService = CameraManagerGlobal.get().getCameraService();
                } catch (ServiceSpecificException e) {
                    if (e.errorCode == 9) {
                        throw new AssertionError("Should've gone down the shim path");
                    }
                    if (e.errorCode != 7 && e.errorCode != 8 && e.errorCode != 6 && e.errorCode != 4 && e.errorCode != 10) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                    cameraDeviceImpl.setRemoteFailure(e);
                    if (e.errorCode == 6 || e.errorCode == 4 || e.errorCode == 7) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                }
                if (cameraService == null) {
                    throw new ServiceSpecificException(4, "Camera service is currently unavailable");
                }
                iCameraDeviceUserConnectDevice = cameraService.connectDevice(callbacks, str, i, this.mContext.getApplicationInfo().targetSdkVersion, i2, getClientAttribution(true), getDevicePolicyFromContext(this.mContext), z);
                cameraDeviceImpl.setRemoteDevice(iCameraDeviceUserConnectDevice);
            } catch (RemoteException unused) {
                ServiceSpecificException serviceSpecificException = new ServiceSpecificException(4, "Camera service is currently unavailable");
                cameraDeviceImpl.setRemoteFailure(serviceSpecificException);
                throw ExceptionUtils.throwAsPublicException(serviceSpecificException);
            }
        }
        return cameraDeviceImpl;
    }

    public void openCamera(String str, CameraDevice.StateCallback stateCallback, Handler handler) throws CameraAccessException {
        openCameraImpl(str, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, getRotationOverride(this.mContext), false);
    }

    public void openCamera(String str, boolean z, Handler handler, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        openCameraImpl(str, stateCallback, CameraDeviceImpl.checkAndWrapHandler(handler), 0, z ? 1 : 0, false);
    }

    public void openCamera(String str, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        openCameraImpl(str, stateCallback, executor, 0, getRotationOverride(this.mContext), false);
    }

    @SystemApi
    public void openSharedCamera(String str, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (!isCameraDeviceSharingSupported(str)) {
            throw new UnsupportedOperationException("CameraDevice sharing is not supported for Camera ID: " + str);
        }
        openCameraImpl(str, stateCallback, executor, 0, getRotationOverride(this.mContext), true);
    }

    @SystemApi
    public void openCamera(String str, int i, Executor executor, CameraDevice.StateCallback stateCallback) throws CameraAccessException {
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("oomScoreOffset < 0, cannot increase priority of camera client");
        }
        openCameraImpl(str, stateCallback, executor, i, getRotationOverride(this.mContext), false);
    }

    public void openCameraImpl(String str, CameraDevice.StateCallback stateCallback, Executor executor, int i, int i2, boolean z) throws CameraAccessException {
        if (str == null) {
            throw new IllegalArgumentException("cameraId was null");
        }
        if (stateCallback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        openCameraDeviceUserAsync(str, stateCallback, executor, i, i2, z);
    }

    public void setTorchMode(String str, boolean z) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        CameraManagerGlobal.get().setTorchMode(str, z, getClientAttribution(), getDevicePolicyFromContext(this.mContext));
    }

    public void turnOnTorchWithStrengthLevel(String str, int i) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No camera available on device");
        }
        CameraManagerGlobal.get().turnOnTorchWithStrengthLevel(str, i, getClientAttribution(), getDevicePolicyFromContext(this.mContext));
    }

    public int getTorchStrengthLevel(String str) throws CameraAccessException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No camera available on device.");
        }
        return CameraManagerGlobal.get().getTorchStrengthLevel(str, getClientAttribution(), getDevicePolicyFromContext(this.mContext));
    }

    public static int getRotationOverride(Context context) {
        PackageManager packageManager;
        String opPackageName;
        if (context != null) {
            packageManager = context.getPackageManager();
            opPackageName = context.getOpPackageName();
        } else {
            packageManager = null;
            opPackageName = null;
        }
        return getRotationOverride(context, packageManager, opPackageName);
    }

    public static int getRotationOverride(Context context, PackageManager packageManager, String str) {
        if (Process.isIsolated() || !DesktopModeFlags.ENABLE_CAMERA_COMPAT_SIMULATE_REQUESTED_ORIENTATION.isTrue()) {
            return shouldOverrideToPortrait(packageManager, str) ? 1 : 0;
        }
        return getRotationOverrideInternal(context, packageManager, str);
    }

    public static int getRotationOverrideInternal(Context context, PackageManager packageManager, String str) {
        ActivityManager activityManager;
        if (!CameraManagerGlobal.sLandscapeToPortrait) {
            return 0;
        }
        if (context != null && !Process.isIsolated() && (activityManager = (ActivityManager) context.getSystemService(ActivityManager.class)) != null) {
            Iterator<ActivityManager.AppTask> it = activityManager.getAppTasks().iterator();
            while (it.hasNext()) {
                ActivityManager.RecentTaskInfo taskInfo = it.next().getTaskInfo();
                int i = taskInfo.appCompatTaskInfo.cameraCompatTaskInfo.freeformCameraCompatMode;
                if (isInCameraCompatMode(i) && taskInfo.topActivity != null && taskInfo.topActivity.getPackageName().equals(str)) {
                    return getRotationOverrideForCompatFreeform(i);
                }
            }
        }
        if (packageManager != null && str != null) {
            try {
                return packageManager.getProperty(PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, str).getBoolean() ? 1 : 0;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT) ? 1 : 0;
    }

    public static boolean shouldOverrideToPortrait(PackageManager packageManager, String str) {
        if (!CameraManagerGlobal.sLandscapeToPortrait) {
            return false;
        }
        if (packageManager != null && str != null) {
            try {
                return packageManager.getProperty(PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, str).getBoolean();
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT);
    }

    public static boolean physicalCallbacksAreEnabledForUnavailableCamera() {
        return CompatChanges.isChangeEnabled(ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA);
    }

    public static boolean isHiddenPhysicalCamera(String str) {
        try {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                return false;
            }
            return cameraService.isHiddenPhysicalCamera(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void injectCamera(String str, String str2, String str3, Executor executor, CameraInjectionSession.InjectionStatusCallback injectionStatusCallback) throws CameraAccessException, SecurityException, IllegalArgumentException {
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
                    try {
                        CameraInjectionSessionImpl cameraInjectionSessionImpl = new CameraInjectionSessionImpl(injectionStatusCallback, executor);
                        cameraInjectionSessionImpl.setRemoteInjectionSession(cameraService.injectCamera(str, str2, str3, cameraInjectionSessionImpl.getCallback()));
                    } catch (ServiceSpecificException e) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                } catch (RemoteException unused) {
                    throw ExceptionUtils.throwAsPublicException(new ServiceSpecificException(4, "Camera service is currently unavailable"));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void injectSessionParams(String str, CaptureRequest captureRequest) throws CameraAccessException, SecurityException {
        CameraManagerGlobal.get().injectSessionParams(str, captureRequest);
    }

    public ICameraService getCameraService() {
        return CameraManagerGlobal.get().getCameraService();
    }

    public boolean isCameraServiceDisabled() {
        return CameraManagerGlobal.sCameraServiceDisabled;
    }

    public static String reportExtensionSessionStats(CameraExtensionSessionStats cameraExtensionSessionStats) {
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            Log.e(TAG, "CameraService not available. Not reporting extension stats.");
            return "";
        }
        try {
            return cameraService.reportExtensionSessionStats(cameraExtensionSessionStats);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to report extension session stats to cameraservice.", e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CameraManagerGlobal extends ICameraServiceListener.Stub implements IBinder.DeathRecipient {
        private static final String CAMERA_SERVICE_BINDER_NAME = "media.camera";
        private static final int DEVICE_STATUS_ARRAY_SIZE = 10;
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
        private final ArrayDeque<String> mDeviceStatusHistory = new ArrayDeque<>(10);
        private final ArrayMap<AvailabilityCallback, Executor> mCallbackMap = new ArrayMap<>();
        private final Binder mTorchClientBinder = new Binder();
        private final ArrayMap<DeviceCameraInfo, Integer> mTorchStatus = new ArrayMap<>();
        private final ArrayMap<TorchCallback, Executor> mTorchCallbackMap = new ArrayMap<>();
        private final ArrayMap<DeviceCameraInfo, CameraDeviceState> mCameraDeviceStates = new ArrayMap<>();
        private final ArrayMap<SemCameraDeviceStateCallback, Executor> mSemCameraDeviceStateCallbackMap = new ArrayMap<>();
        private final Object mLock = new Object();
        private boolean mHasOpenCloseListenerPermission = false;

        private boolean isAvailable(int i) {
            return i == 1;
        }

        private boolean validStatus(int i) {
            return i == -2 || i == 0 || i == 1 || i == 2;
        }

        private boolean validTorchStatus(int i) {
            return i == 0 || i == 1 || i == 2;
        }

        @Override // android.hardware.ICameraServiceListener.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpenedInSharedMode(String str, String str2, int i, boolean z) {
        }

        private CameraManagerGlobal() {
        }

        public static CameraManagerGlobal get() {
            return gCameraManager;
        }

        public void registerDeviceStateListener(CameraCharacteristics cameraCharacteristics, Context context) {
            synchronized (this.mLock) {
                if (this.mDeviceStateHandlerThread == null) {
                    HandlerThread handlerThread = new HandlerThread(TAG);
                    this.mDeviceStateHandlerThread = handlerThread;
                    handlerThread.start();
                    this.mDeviceStateHandler = new Handler(this.mDeviceStateHandlerThread.getLooper());
                }
                if (this.mFoldStateListener == null) {
                    this.mFoldStateListener = new FoldStateListener(context);
                    try {
                        ((DeviceStateManager) context.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mDeviceStateHandler), this.mFoldStateListener);
                    } catch (IllegalStateException unused) {
                        this.mFoldStateListener = null;
                        Log.v(TAG, "Failed to register device state listener!");
                        Log.v(TAG, "Device state dependent characteristics updates will not befunctional!");
                        return;
                    }
                }
                this.mFoldStateListener.addDeviceStateListener(cameraCharacteristics.getDeviceStateListener());
            }
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
            IBinder service = ServiceManager.getService(CAMERA_SERVICE_BINDER_NAME);
            if (service == null) {
                return;
            }
            try {
                service.linkToDeath(this, 0);
                ICameraService iCameraServiceAsInterface = ICameraService.Stub.asInterface(service);
                try {
                    CameraMetadataNative.setupGlobalVendorTagDescriptor();
                } catch (ServiceSpecificException e) {
                    handleRecoverableSetupErrors(e);
                }
                try {
                    addDeviceStatusHistoryLocked(TextUtils.formatSimple("connectCameraServiceLocked(E): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                    for (CameraStatus cameraStatus : iCameraServiceAsInterface.addListener(this)) {
                        DeviceCameraInfo deviceCameraInfo = new DeviceCameraInfo(cameraStatus.cameraId, cameraStatus.deviceId);
                        onStatusChangedLocked(cameraStatus.status, deviceCameraInfo);
                        if (cameraStatus.unavailablePhysicalCameras != null) {
                            for (String str : cameraStatus.unavailablePhysicalCameras) {
                                onPhysicalCameraStatusChangedLocked(0, deviceCameraInfo, str);
                            }
                        }
                        if (this.mHasOpenCloseListenerPermission && cameraStatus.status == -2 && !cameraStatus.clientPackage.isEmpty()) {
                            onCameraOpenedLocked(deviceCameraInfo, cameraStatus.clientPackage);
                        }
                    }
                    this.mCameraService = iCameraServiceAsInterface;
                    addDeviceStatusHistoryLocked(TextUtils.formatSimple("connectCameraServiceLocked(X): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                } catch (RemoteException unused) {
                } catch (ServiceSpecificException e2) {
                    throw new IllegalStateException("Failed to register a camera service listener", e2);
                }
                try {
                    for (ConcurrentCameraIdCombination concurrentCameraIdCombination : iCameraServiceAsInterface.getConcurrentCameraIds()) {
                        Set<Pair<String, Integer>> concurrentCameraIdCombination2 = concurrentCameraIdCombination.getConcurrentCameraIdCombination();
                        ArraySet arraySet = new ArraySet();
                        for (Pair<String, Integer> pair : concurrentCameraIdCombination2) {
                            arraySet.add(new DeviceCameraInfo(pair.first, pair.second.intValue()));
                        }
                        this.mConcurrentCameraIdCombinations.add(arraySet);
                    }
                } catch (ServiceSpecificException e3) {
                    throw new IllegalStateException("Failed to get concurrent camera id combinations", e3);
                }
            } catch (RemoteException unused2) {
            }
        }

        public void injectSessionParams(String str, CaptureRequest captureRequest) throws CameraAccessException, SecurityException {
            synchronized (this.mLock) {
                ICameraService cameraService = getCameraService();
                if (cameraService == null) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable.");
                }
                try {
                    cameraService.injectSessionParams(str, captureRequest.getNativeMetadata());
                } catch (RemoteException unused) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable.");
                } catch (ServiceSpecificException e) {
                    throw ExceptionUtils.throwAsPublicException(e);
                }
            }
        }

        private String[] extractCameraIdListLocked(int i, int i2) {
            addDeviceStatusHistoryLocked(TextUtils.formatSimple("extractCameraIdListLocked(E): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
            try {
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < this.mDeviceStatus.size(); i3++) {
                    int iIntValue = this.mDeviceStatus.valueAt(i3).intValue();
                    DeviceCameraInfo deviceCameraInfoKeyAt = this.mDeviceStatus.keyAt(i3);
                    if (iIntValue != 0 && iIntValue != 2 && !shouldHideCamera(i, i2, deviceCameraInfoKeyAt)) {
                        arrayList.add(deviceCameraInfoKeyAt.mCameraId);
                    }
                }
                return (String[]) arrayList.toArray(new String[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException(e.getMessage() + ": {" + String.join(" -> ", this.mDeviceStatusHistory) + "}");
            }
        }

        private Set<Set<String>> extractConcurrentCameraIdListLocked(int i, int i2) {
            ArraySet arraySet = new ArraySet();
            for (Set<DeviceCameraInfo> set : this.mConcurrentCameraIdCombinations) {
                ArraySet arraySet2 = new ArraySet();
                for (DeviceCameraInfo deviceCameraInfo : set) {
                    Integer num = this.mDeviceStatus.get(deviceCameraInfo);
                    if (num != null && num.intValue() != 2 && num.intValue() != 0 && !shouldHideCamera(i, i2, deviceCameraInfo)) {
                        arraySet2.add(deviceCameraInfo.mCameraId);
                    }
                }
                if (!arraySet2.isEmpty()) {
                    arraySet.add(arraySet2);
                }
            }
            return arraySet;
        }

        private static void sortCameraIds(String[] strArr) {
            Arrays.sort(strArr, new Comparator<String>() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.1
                @Override // java.util.Comparator
                public int compare(String str, String str2) throws NumberFormatException {
                    int i;
                    int i2;
                    try {
                        i = Integer.parseInt(str);
                    } catch (NumberFormatException unused) {
                        i = -1;
                    }
                    try {
                        i2 = Integer.parseInt(str2);
                    } catch (NumberFormatException unused2) {
                        i2 = -1;
                    }
                    if (i >= 0 && i2 >= 0) {
                        return i - i2;
                    }
                    if (i >= 0) {
                        return -1;
                    }
                    if (i2 >= 0) {
                        return 1;
                    }
                    return str.compareTo(str2);
                }
            });
        }

        private boolean shouldHideCamera(int i, int i2, DeviceCameraInfo deviceCameraInfo) {
            return ((i2 == 0 && deviceCameraInfo.mDeviceId == 0) || i == deviceCameraInfo.mDeviceId) ? false : true;
        }

        private static boolean cameraStatusesContains(CameraStatus[] cameraStatusArr, DeviceCameraInfo deviceCameraInfo) {
            for (CameraStatus cameraStatus : cameraStatusArr) {
                if (cameraStatus.cameraId.equals(deviceCameraInfo.mCameraId) && cameraStatus.deviceId == deviceCameraInfo.mDeviceId) {
                    return true;
                }
            }
            return false;
        }

        public String[] getCameraIdListNoLazy(int i, int i2) {
            String[] strArrExtractCameraIdListLocked;
            if (sCameraServiceDisabled) {
                return new String[0];
            }
            ICameraServiceListener.Stub stub = new ICameraServiceListener.Stub(this) { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.2
                @Override // android.hardware.ICameraServiceListener
                public void onCameraAccessPrioritiesChanged() {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraClosed(String str, int i3) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraDeviceStateChanged(String str, int i3, int i4, String str2, int i5, int i6, int i7) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraOpened(String str, String str2, int i3) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onCameraOpenedInSharedMode(String str, String str2, int i3, boolean z) {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onPhysicalCameraStatusChanged(int i3, String str, String str2, int i4) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onStatusChanged(int i3, String str, int i4) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStatusChanged(int i3, String str, int i4) throws RemoteException {
                }

                @Override // android.hardware.ICameraServiceListener
                public void onTorchStrengthLevelChanged(String str, int i3, int i4) throws RemoteException {
                }
            };
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                try {
                    addDeviceStatusHistoryLocked(TextUtils.formatSimple("getCameraIdListNoLazy(E): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                    CameraStatus[] cameraStatusArrAddListener = this.mCameraService.addListener(stub);
                    this.mCameraService.removeListener(stub);
                    for (CameraStatus cameraStatus : cameraStatusArrAddListener) {
                        onStatusChangedLocked(cameraStatus.status, new DeviceCameraInfo(cameraStatus.cameraId, cameraStatus.deviceId));
                    }
                    Set<DeviceCameraInfo> setKeySet = this.mDeviceStatus.keySet();
                    ArrayList<DeviceCameraInfo> arrayList = new ArrayList();
                    for (DeviceCameraInfo deviceCameraInfo : setKeySet) {
                        if (!cameraStatusesContains(cameraStatusArrAddListener, deviceCameraInfo)) {
                            arrayList.add(deviceCameraInfo);
                        }
                    }
                    for (DeviceCameraInfo deviceCameraInfo2 : arrayList) {
                        onStatusChangedLocked(0, deviceCameraInfo2);
                        this.mTorchStatus.remove(deviceCameraInfo2);
                    }
                    addDeviceStatusHistoryLocked(TextUtils.formatSimple("getCameraIdListNoLazy(X): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                } catch (RemoteException unused) {
                } catch (ServiceSpecificException e) {
                    throw new IllegalStateException("Failed to register a camera service listener", e);
                }
                strArrExtractCameraIdListLocked = extractCameraIdListLocked(i, i2);
            }
            sortCameraIds(strArrExtractCameraIdListLocked);
            return (String[]) Arrays.stream(strArrExtractCameraIdListLocked).filter(new CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda11
                @Override // java.util.function.IntFunction
                public final Object apply(int i3) {
                    return CameraManager.CameraManagerGlobal.lambda$getCameraIdListNoLazy$0(i3);
                }
            });
        }

        static /* synthetic */ String[] lambda$getCameraIdListNoLazy$0(int i) {
            return new String[i];
        }

        public String[] getCameraIdList(int i, int i2) {
            String[] strArrExtractCameraIdListLocked;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                strArrExtractCameraIdListLocked = extractCameraIdListLocked(i, i2);
            }
            sortCameraIds(strArrExtractCameraIdListLocked);
            return (String[]) Arrays.stream(strArrExtractCameraIdListLocked).filter(new CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0()).toArray(new IntFunction() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i3) {
                    return CameraManager.CameraManagerGlobal.lambda$getCameraIdList$1(i3);
                }
            });
        }

        static /* synthetic */ String[] lambda$getCameraIdList$1(int i) {
            return new String[i];
        }

        public Set<Set<String>> getConcurrentCameraIds(int i, int i2) {
            Set<Set<String>> setExtractConcurrentCameraIdListLocked;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                setExtractConcurrentCameraIdListLocked = extractConcurrentCameraIdListLocked(i, i2);
            }
            return setExtractConcurrentCameraIdListLocked;
        }

        public boolean isConcurrentSessionConfigurationSupported(Map<String, SessionConfiguration> map, int i, AttributionSourceState attributionSourceState, int i2) throws CameraAccessException {
            if (map == null) {
                throw new IllegalArgumentException("cameraIdsAndSessionConfigurations was null");
            }
            int size = map.size();
            if (size == 0) {
                throw new IllegalArgumentException("camera id and session combination is empty");
            }
            synchronized (this.mLock) {
                int i3 = 0;
                boolean z = false;
                for (Set<DeviceCameraInfo> set : this.mConcurrentCameraIdCombinations) {
                    ArraySet arraySet = new ArraySet();
                    Iterator<String> it = map.keySet().iterator();
                    while (it.hasNext()) {
                        arraySet.add(new DeviceCameraInfo(it.next(), i2 == 0 ? 0 : attributionSourceState.deviceId));
                    }
                    if (set.containsAll(arraySet)) {
                        z = true;
                    }
                }
                if (!z) {
                    Log.v(TAG, "isConcurrentSessionConfigurationSupported called with a subset of camera ids not returned by getConcurrentCameraIds");
                    return false;
                }
                CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr = new CameraIdAndSessionConfiguration[size];
                for (Map.Entry<String, SessionConfiguration> entry : map.entrySet()) {
                    cameraIdAndSessionConfigurationArr[i3] = new CameraIdAndSessionConfiguration(entry.getKey(), entry.getValue());
                    i3++;
                }
                try {
                    return this.mCameraService.isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfigurationArr, i, attributionSourceState, i2);
                } catch (RemoteException e) {
                    throw new CameraAccessException(2, "Camera service is currently unavailable", e);
                } catch (ServiceSpecificException e2) {
                    throw ExceptionUtils.throwAsPublicException(e2);
                }
            }
        }

        public boolean cameraIdHasConcurrentStreams(String str, int i, int i2) {
            synchronized (this.mLock) {
                if (i2 == 0) {
                    i = 0;
                }
                DeviceCameraInfo deviceCameraInfo = new DeviceCameraInfo(str, i);
                if (!this.mDeviceStatus.containsKey(deviceCameraInfo)) {
                    return false;
                }
                Iterator<Set<DeviceCameraInfo>> it = this.mConcurrentCameraIdCombinations.iterator();
                while (it.hasNext()) {
                    if (it.next().contains(deviceCameraInfo)) {
                        return true;
                    }
                }
                return false;
            }
        }

        public void setTorchMode(String str, boolean z, AttributionSourceState attributionSourceState, int i) throws CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable");
                    }
                    try {
                        cameraService.setTorchMode(str, z, this.mTorchClientBinder, attributionSourceState, i);
                    } catch (RemoteException unused) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable");
                    } catch (ServiceSpecificException e) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void turnOnTorchWithStrengthLevel(String str, int i, AttributionSourceState attributionSourceState, int i2) throws CameraAccessException {
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        cameraService.turnOnTorchWithStrengthLevel(str, i, this.mTorchClientBinder, attributionSourceState, i2);
                    } catch (RemoteException unused) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    } catch (ServiceSpecificException e) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public int getTorchStrengthLevel(String str, AttributionSourceState attributionSourceState, int i) throws CameraAccessException {
            int torchStrengthLevel;
            synchronized (this.mLock) {
                try {
                    if (str == null) {
                        throw new IllegalArgumentException("cameraId was null");
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService == null) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    }
                    try {
                        torchStrengthLevel = cameraService.getTorchStrengthLevel(str, attributionSourceState, i);
                    } catch (RemoteException unused) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable.");
                    } catch (ServiceSpecificException e) {
                        throw ExceptionUtils.throwAsPublicException(e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return torchStrengthLevel;
        }

        private void handleRecoverableSetupErrors(ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode == 4) {
                Log.w(TAG, serviceSpecificException.getMessage());
                return;
            }
            throw new IllegalStateException(serviceSpecificException);
        }

        private void postSingleAccessPriorityChangeUpdate(final AvailabilityCallback availabilityCallback, Executor executor) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Objects.requireNonNull(availabilityCallback);
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        availabilityCallback.onCameraAccessPrioritiesChanged();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void postSingleCameraOpenedUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String str, final String str2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        availabilityCallback.onCameraOpened(str, str2);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void postSingleCameraClosedUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String str) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        availabilityCallback.onCameraClosed(str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void postSingleUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String str, final String str2, int i) {
            long jClearCallingIdentity;
            if (CameraManager.isPublicId(str) || availabilityCallback == null || availabilityCallback.mIsHiddenIdPermittedPackage) {
                Log.i(TAG, String.format("postSingleUpdate device: camera id %s status %s", str, cameraStatusToString(i)));
                if (isAvailable(i)) {
                    jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraManager.CameraManagerGlobal.lambda$postSingleUpdate$4(str2, availabilityCallback, str);
                            }
                        });
                    } finally {
                    }
                } else {
                    jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraManager.CameraManagerGlobal.lambda$postSingleUpdate$5(str2, availabilityCallback, str);
                            }
                        });
                    } finally {
                    }
                }
            }
        }

        static /* synthetic */ void lambda$postSingleUpdate$4(String str, AvailabilityCallback availabilityCallback, String str2) {
            if (str == null) {
                availabilityCallback.onCameraAvailable(str2);
            } else {
                availabilityCallback.onPhysicalCameraAvailable(str2, str);
            }
        }

        static /* synthetic */ void lambda$postSingleUpdate$5(String str, AvailabilityCallback availabilityCallback, String str2) {
            if (str == null) {
                availabilityCallback.onCameraUnavailable(str2);
            } else {
                availabilityCallback.onPhysicalCameraUnavailable(str2, str);
            }
        }

        private void postSingleTorchUpdate(final TorchCallback torchCallback, Executor executor, final String str, final int i) {
            long jClearCallingIdentity;
            Log.i(TAG, String.format("postSingleTorchUpdate device: camera id %s status %d", str, Integer.valueOf(i)));
            if (i == 1 || i == 2) {
                jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$6(torchCallback, str, i);
                        }
                    });
                } finally {
                }
            } else {
                jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$7(torchCallback, str);
                        }
                    });
                } finally {
                }
            }
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$6(TorchCallback torchCallback, String str, int i) {
            Log.i(TAG, "onTorchModeChanged");
            torchCallback.onTorchModeChanged(str, i == 2);
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$7(TorchCallback torchCallback, String str) {
            Log.i(TAG, "onTorchModeUnavailable");
            torchCallback.onTorchModeUnavailable(str);
        }

        private void postSingleTorchStrengthLevelUpdate(final TorchCallback torchCallback, Executor executor, final String str, final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        torchCallback.onTorchStrengthLevelChanged(str, i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void updateCallbackLocked(AvailabilityCallback availabilityCallback, Executor executor) {
            for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                DeviceCameraInfo deviceCameraInfoKeyAt = this.mDeviceStatus.keyAt(i);
                if (!shouldHideCamera(availabilityCallback.mDeviceId, availabilityCallback.mDevicePolicy, deviceCameraInfoKeyAt)) {
                    Integer numValueAt = this.mDeviceStatus.valueAt(i);
                    postSingleUpdate(availabilityCallback, executor, deviceCameraInfoKeyAt.mCameraId, null, numValueAt.intValue());
                    if (this.mHasOpenCloseListenerPermission) {
                        postSemSingleUpdate(availabilityCallback, executor, deviceCameraInfoKeyAt.mCameraId, numValueAt.intValue());
                    }
                    if ((isAvailable(numValueAt.intValue()) || CameraManager.physicalCallbacksAreEnabledForUnavailableCamera()) && this.mUnavailablePhysicalDevices.containsKey(deviceCameraInfoKeyAt)) {
                        Iterator<String> it = this.mUnavailablePhysicalDevices.get(deviceCameraInfoKeyAt).iterator();
                        while (it.hasNext()) {
                            postSingleUpdate(availabilityCallback, executor, deviceCameraInfoKeyAt.mCameraId, it.next(), 0);
                        }
                    }
                }
            }
            if (this.mHasOpenCloseListenerPermission) {
                Log.i(TAG, "updateCallbackLocked: post device state update");
                for (int i2 = 0; i2 < this.mCameraDeviceStates.size(); i2++) {
                    DeviceCameraInfo deviceCameraInfoKeyAt2 = this.mCameraDeviceStates.keyAt(i2);
                    CameraDeviceState cameraDeviceStateValueAt = this.mCameraDeviceStates.valueAt(i2);
                    if (!shouldHideCamera(availabilityCallback.mDeviceId, availabilityCallback.mDevicePolicy, deviceCameraInfoKeyAt2)) {
                        postSemSingleCameraDeviceStateUpdate(availabilityCallback, executor, deviceCameraInfoKeyAt2.mCameraId, cameraDeviceStateValueAt);
                    }
                }
            }
            for (int i3 = 0; i3 < this.mOpenedDevices.size(); i3++) {
                DeviceCameraInfo deviceCameraInfoKeyAt3 = this.mOpenedDevices.keyAt(i3);
                if (!shouldHideCamera(availabilityCallback.mDeviceId, availabilityCallback.mDevicePolicy, deviceCameraInfoKeyAt3)) {
                    postSingleCameraOpenedUpdate(availabilityCallback, executor, deviceCameraInfoKeyAt3.mCameraId, this.mOpenedDevices.valueAt(i3));
                }
            }
        }

        private void onStatusChangedLocked(int i, DeviceCameraInfo deviceCameraInfo) {
            Integer numPut;
            CameraManagerGlobal cameraManagerGlobal;
            int i2;
            if (!validStatus(i)) {
                Log.e(TAG, String.format("Ignoring invalid camera %s status 0x%x for device %d", deviceCameraInfo.mCameraId, Integer.valueOf(i), Integer.valueOf(deviceCameraInfo.mDeviceId)));
                return;
            }
            if (i == 0) {
                numPut = this.mDeviceStatus.remove(deviceCameraInfo);
                this.mUnavailablePhysicalDevices.remove(deviceCameraInfo);
            } else {
                numPut = this.mDeviceStatus.put(deviceCameraInfo, Integer.valueOf(i));
                if (numPut == null) {
                    this.mUnavailablePhysicalDevices.put(deviceCameraInfo, new ArrayList<>());
                }
            }
            if (numPut == null || numPut.intValue() != i) {
                int size = this.mCallbackMap.size();
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    Executor executorValueAt = this.mCallbackMap.valueAt(i4);
                    AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i4);
                    if (!shouldHideCamera(availabilityCallbackKeyAt.mDeviceId, availabilityCallbackKeyAt.mDevicePolicy, deviceCameraInfo) && this.mHasOpenCloseListenerPermission) {
                        postSemSingleUpdate(availabilityCallbackKeyAt, executorValueAt, deviceCameraInfo.mCameraId, i);
                    }
                }
                if (numPut == null || isAvailable(i) != isAvailable(numPut.intValue())) {
                    int size2 = this.mCallbackMap.size();
                    while (i3 < size2) {
                        AvailabilityCallback availabilityCallbackKeyAt2 = this.mCallbackMap.keyAt(i3);
                        if (this.shouldHideCamera(availabilityCallbackKeyAt2.mDeviceId, availabilityCallbackKeyAt2.mDevicePolicy, deviceCameraInfo)) {
                            cameraManagerGlobal = this;
                            i2 = i;
                        } else {
                            Executor executorValueAt2 = this.mCallbackMap.valueAt(i3);
                            cameraManagerGlobal = this;
                            int i5 = i;
                            cameraManagerGlobal.postSingleUpdate(availabilityCallbackKeyAt2, executorValueAt2, deviceCameraInfo.mCameraId, null, i5);
                            i2 = i5;
                            if (cameraManagerGlobal.isAvailable(i2) && cameraManagerGlobal.mUnavailablePhysicalDevices.containsKey(deviceCameraInfo)) {
                                Iterator<String> it = cameraManagerGlobal.mUnavailablePhysicalDevices.get(deviceCameraInfo).iterator();
                                while (it.hasNext()) {
                                    cameraManagerGlobal.postSingleUpdate(availabilityCallbackKeyAt2, executorValueAt2, deviceCameraInfo.mCameraId, it.next(), 0);
                                }
                            }
                        }
                        i3++;
                        i = i2;
                        this = cameraManagerGlobal;
                    }
                }
            }
        }

        private void onPhysicalCameraStatusChangedLocked(int i, DeviceCameraInfo deviceCameraInfo, String str) {
            CameraManagerGlobal cameraManagerGlobal;
            int i2;
            String str2;
            if (!validStatus(i)) {
                Log.e(TAG, String.format("Ignoring invalid device %s physical device %s status 0x%x for device %d", deviceCameraInfo.mCameraId, str, Integer.valueOf(i), Integer.valueOf(deviceCameraInfo.mDeviceId)));
                return;
            }
            if (!this.mDeviceStatus.containsKey(deviceCameraInfo) || !this.mUnavailablePhysicalDevices.containsKey(deviceCameraInfo)) {
                Log.e(TAG, String.format("Camera %s is not present. Ignore physical camera status change", deviceCameraInfo.mCameraId));
                return;
            }
            ArrayList<String> arrayList = this.mUnavailablePhysicalDevices.get(deviceCameraInfo);
            if (!isAvailable(i) && !arrayList.contains(str)) {
                arrayList.add(str);
            } else if (!isAvailable(i) || !arrayList.contains(str)) {
                return;
            } else {
                arrayList.remove(str);
            }
            if (!CameraManager.physicalCallbacksAreEnabledForUnavailableCamera() && !isAvailable(this.mDeviceStatus.get(deviceCameraInfo).intValue())) {
                Log.i(TAG, String.format("Camera %s is not available. Ignore physical camera status change callback(s)", deviceCameraInfo.mCameraId));
                return;
            }
            int size = this.mCallbackMap.size();
            int i3 = 0;
            while (i3 < size) {
                AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i3);
                if (this.shouldHideCamera(availabilityCallbackKeyAt.mDeviceId, availabilityCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                    cameraManagerGlobal = this;
                    i2 = i;
                    str2 = str;
                } else {
                    cameraManagerGlobal = this;
                    i2 = i;
                    str2 = str;
                    cameraManagerGlobal.postSingleUpdate(availabilityCallbackKeyAt, this.mCallbackMap.valueAt(i3), deviceCameraInfo.mCameraId, str2, i2);
                }
                i3++;
                this = cameraManagerGlobal;
                str = str2;
                i = i2;
            }
        }

        private void updateTorchCallbackLocked(TorchCallback torchCallback, Executor executor) {
            for (int i = 0; i < this.mTorchStatus.size(); i++) {
                DeviceCameraInfo deviceCameraInfoKeyAt = this.mTorchStatus.keyAt(i);
                if (!shouldHideCamera(torchCallback.mDeviceId, torchCallback.mDevicePolicy, deviceCameraInfoKeyAt)) {
                    postSingleTorchUpdate(torchCallback, executor, deviceCameraInfoKeyAt.mCameraId, this.mTorchStatus.valueAt(i).intValue());
                }
            }
        }

        private void onTorchStatusChangedLocked(int i, DeviceCameraInfo deviceCameraInfo) {
            if (!validTorchStatus(i)) {
                Log.e(TAG, String.format("Ignoring invalid camera %s torch status 0x%x for device %d", deviceCameraInfo.mCameraId, Integer.valueOf(i), Integer.valueOf(deviceCameraInfo.mDeviceId)));
                return;
            }
            Integer numPut = this.mTorchStatus.put(deviceCameraInfo, Integer.valueOf(i));
            if (numPut == null || numPut.intValue() != i) {
                int size = this.mTorchCallbackMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    TorchCallback torchCallbackKeyAt = this.mTorchCallbackMap.keyAt(i2);
                    if (!shouldHideCamera(torchCallbackKeyAt.mDeviceId, torchCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                        postSingleTorchUpdate(torchCallbackKeyAt, this.mTorchCallbackMap.valueAt(i2), deviceCameraInfo.mCameraId, i);
                    }
                }
            }
        }

        private void onTorchStrengthLevelChangedLocked(DeviceCameraInfo deviceCameraInfo, int i) {
            int size = this.mTorchCallbackMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                TorchCallback torchCallbackKeyAt = this.mTorchCallbackMap.keyAt(i2);
                if (!shouldHideCamera(torchCallbackKeyAt.mDeviceId, torchCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                    postSingleTorchStrengthLevelUpdate(torchCallbackKeyAt, this.mTorchCallbackMap.valueAt(i2), deviceCameraInfo.mCameraId, i);
                }
            }
        }

        public void registerAvailabilityCallback(AvailabilityCallback availabilityCallback, Executor executor, boolean z, int i, int i2) {
            synchronized (this.mLock) {
                this.mHasOpenCloseListenerPermission = z;
                connectCameraServiceLocked();
                availabilityCallback.mDeviceId = i;
                availabilityCallback.mDevicePolicy = i2;
                if (this.mCallbackMap.put(availabilityCallback, executor) == null) {
                    updateCallbackLocked(availabilityCallback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterAvailabilityCallback(AvailabilityCallback availabilityCallback) {
            synchronized (this.mLock) {
                this.mCallbackMap.remove(availabilityCallback);
            }
        }

        public void registerTorchCallback(TorchCallback torchCallback, Executor executor, int i, int i2) {
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                torchCallback.mDeviceId = i;
                torchCallback.mDevicePolicy = i2;
                if (this.mTorchCallbackMap.put(torchCallback, executor) == null) {
                    updateTorchCallbackLocked(torchCallback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterTorchCallback(TorchCallback torchCallback) {
            synchronized (this.mLock) {
                this.mTorchCallbackMap.remove(torchCallback);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onStatusChanged(int i, String str, int i2) throws RemoteException {
            synchronized (this.mLock) {
                addDeviceStatusHistoryLocked(TextUtils.formatSimple("onStatusChanged(E): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                onStatusChangedLocked(i, new DeviceCameraInfo(str, i2));
                addDeviceStatusHistoryLocked(TextUtils.formatSimple("onStatusChanged(X): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2) throws RemoteException {
            synchronized (this.mLock) {
                onPhysicalCameraStatusChangedLocked(i, new DeviceCameraInfo(str, i2), str2);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStatusChanged(int i, String str, int i2) throws RemoteException {
            synchronized (this.mLock) {
                onTorchStatusChangedLocked(i, new DeviceCameraInfo(str, i2));
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException {
            synchronized (this.mLock) {
                onTorchStrengthLevelChangedLocked(new DeviceCameraInfo(str, i2), i);
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraAccessPrioritiesChanged() {
            synchronized (this.mLock) {
                int size = this.mCallbackMap.size();
                for (int i = 0; i < size; i++) {
                    postSingleAccessPriorityChangeUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i));
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpened(String str, String str2, int i) {
            synchronized (this.mLock) {
                onCameraOpenedLocked(new DeviceCameraInfo(str, i), str2);
            }
        }

        private void onCameraOpenedLocked(DeviceCameraInfo deviceCameraInfo, String str) {
            String strPut = this.mOpenedDevices.put(deviceCameraInfo, str);
            if (strPut != null) {
                if (strPut.equals(str)) {
                    Log.w(TAG, "onCameraOpened was previously called for " + strPut + " and is now again called for the same package name, so no new client visible update will be sent");
                    return;
                }
                Log.w(TAG, "onCameraOpened was previously called for " + strPut + " and is now called for " + str + " without onCameraClosed being called first");
            }
            int size = this.mCallbackMap.size();
            for (int i = 0; i < size; i++) {
                AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(availabilityCallbackKeyAt.mDeviceId, availabilityCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                    postSingleCameraOpenedUpdate(availabilityCallbackKeyAt, this.mCallbackMap.valueAt(i), deviceCameraInfo.mCameraId, str);
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraClosed(String str, int i) {
            synchronized (this.mLock) {
                onCameraClosedLocked(new DeviceCameraInfo(str, i));
            }
        }

        private void onCameraClosedLocked(DeviceCameraInfo deviceCameraInfo) {
            this.mOpenedDevices.remove(deviceCameraInfo);
            int size = this.mCallbackMap.size();
            for (int i = 0; i < size; i++) {
                AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i);
                if (!shouldHideCamera(availabilityCallbackKeyAt.mDeviceId, availabilityCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                    postSingleCameraClosedUpdate(availabilityCallbackKeyAt, this.mCallbackMap.valueAt(i), deviceCameraInfo.mCameraId);
                }
            }
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5) {
            synchronized (this.mLock) {
                CameraDeviceState cameraDeviceState = new CameraDeviceState(i, i2, str2, i4);
                Log.i(TAG, "Camera " + str + " " + cameraDeviceState + " API Level " + i3 + " User Id " + i4 + "Device Id " + i5);
                onCameraDeviceStateChangedLocked(cameraDeviceState, new DeviceCameraInfo(str, i5));
            }
        }

        private void onCameraDeviceStateChangedLocked(CameraDeviceState cameraDeviceState, DeviceCameraInfo deviceCameraInfo) {
            CameraDeviceState cameraDeviceStatePut = this.mCameraDeviceStates.put(deviceCameraInfo, cameraDeviceState);
            if (cameraDeviceStatePut != null && cameraDeviceStatePut.equals(cameraDeviceState)) {
                Log.i(TAG, String.format("CameraDevice (%s, %d) state changed to (%s), which is what it already was, skip callback", deviceCameraInfo.mCameraId, Integer.valueOf(deviceCameraInfo.mDeviceId), cameraDeviceState));
                return;
            }
            int size = this.mSemCameraDeviceStateCallbackMap.size();
            for (int i = 0; i < size; i++) {
                Executor executorValueAt = this.mSemCameraDeviceStateCallbackMap.valueAt(i);
                SemCameraDeviceStateCallback semCameraDeviceStateCallbackKeyAt = this.mSemCameraDeviceStateCallbackMap.keyAt(i);
                if (!shouldHideCamera(semCameraDeviceStateCallbackKeyAt.mDeviceId, semCameraDeviceStateCallbackKeyAt.mDevicePolicy, deviceCameraInfo)) {
                    postSingleCameraDeviceStateUpdate(semCameraDeviceStateCallbackKeyAt, executorValueAt, deviceCameraInfo.mCameraId, cameraDeviceState);
                }
            }
            int size2 = this.mCallbackMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Executor executorValueAt2 = this.mCallbackMap.valueAt(i2);
                AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i2);
                if (!shouldHideCamera(availabilityCallbackKeyAt.mDeviceId, availabilityCallbackKeyAt.mDevicePolicy, deviceCameraInfo) && this.mHasOpenCloseListenerPermission) {
                    Log.i(TAG, "onCameraDeviceStateChangedLocked: post device state update");
                    postSemSingleCameraDeviceStateUpdate(availabilityCallbackKeyAt, executorValueAt2, deviceCameraInfo.mCameraId, cameraDeviceState);
                }
            }
        }

        public void registerSemCameraDeviceStateCallback(SemCameraDeviceStateCallback semCameraDeviceStateCallback, Executor executor, int i, int i2) {
            synchronized (this.mLock) {
                Log.i(TAG, "registerSemCameraDeviceStateCallback");
                connectCameraServiceLocked();
                semCameraDeviceStateCallback.mDeviceId = i;
                semCameraDeviceStateCallback.mDevicePolicy = i2;
                if (this.mSemCameraDeviceStateCallbackMap.put(semCameraDeviceStateCallback, executor) == null) {
                    updateSemCameraDeviceStateCallbackLocked(semCameraDeviceStateCallback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterSemCameraDeviceStateCallback(SemCameraDeviceStateCallback semCameraDeviceStateCallback) {
            synchronized (this.mLock) {
                Log.i(TAG, "unregisterSemCameraDeviceStateCallback");
                this.mSemCameraDeviceStateCallbackMap.remove(semCameraDeviceStateCallback);
            }
        }

        private void updateSemCameraDeviceStateCallbackLocked(SemCameraDeviceStateCallback semCameraDeviceStateCallback, Executor executor) {
            for (int i = 0; i < this.mCameraDeviceStates.size(); i++) {
                DeviceCameraInfo deviceCameraInfoKeyAt = this.mCameraDeviceStates.keyAt(i);
                postSingleCameraDeviceStateUpdate(semCameraDeviceStateCallback, executor, deviceCameraInfoKeyAt.mCameraId, this.mCameraDeviceStates.valueAt(i));
            }
        }

        private void postSingleCameraDeviceStateUpdate(final SemCameraDeviceStateCallback semCameraDeviceStateCallback, Executor executor, final String str, final CameraDeviceState cameraDeviceState) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable(this) { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (semCameraDeviceStateCallback.isExtended || cameraDeviceState.mDeviceState < 100) {
                            semCameraDeviceStateCallback.onCameraDeviceStateChanged(str, cameraDeviceState.mFacing, cameraDeviceState.mDeviceState, cameraDeviceState.mClientName);
                            semCameraDeviceStateCallback.onCameraDeviceStateChanged(str, cameraDeviceState.mFacing, cameraDeviceState.mDeviceState, cameraDeviceState.mClientName, cameraDeviceState.mUserId);
                        }
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void postSemSingleUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String str, final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() { // from class: android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        availabilityCallback.onSemCameraDeviceRawStatus(str, i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        private void postSemSingleCameraDeviceStateUpdate(final AvailabilityCallback availabilityCallback, Executor executor, final String str, final CameraDeviceState cameraDeviceState) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable(this) { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.4
                    @Override // java.lang.Runnable
                    public void run() {
                        int i;
                        int i2 = cameraDeviceState.mFacing;
                        if (i2 == 0) {
                            i = 1;
                        } else if (i2 == 1) {
                            i = 0;
                        } else {
                            if (i2 != 2) {
                                Log.w(CameraManagerGlobal.TAG, "Unknown lens facing.");
                                return;
                            }
                            i = 2;
                        }
                        int i3 = cameraDeviceState.mDeviceState;
                        if (i3 == 0) {
                            availabilityCallback.onSemCameraDeviceOpen(str, i, cameraDeviceState.mClientName);
                            return;
                        }
                        if (i3 == 1) {
                            availabilityCallback.onSemCameraDeviceActive(str, i, cameraDeviceState.mClientName);
                            return;
                        }
                        if (i3 == 2) {
                            availabilityCallback.onSemCameraDeviceIdle(str, i, cameraDeviceState.mClientName);
                            return;
                        }
                        if (i3 == 3) {
                            availabilityCallback.onSemCameraDeviceClose(str, i, cameraDeviceState.mClientName);
                        } else {
                            if (i3 == 100 || i3 == 101) {
                                return;
                            }
                            Log.w(CameraManagerGlobal.TAG, "Unknown device state");
                        }
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
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
                        this.f$0.lambda$scheduleCameraServiceReconnectionLocked$10();
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
                    int size = this.mCallbackMap.size();
                    for (int i = 0; i < size; i++) {
                        AvailabilityCallback availabilityCallbackKeyAt = this.mCallbackMap.keyAt(i);
                        if (availabilityCallbackKeyAt.mIsRegisteredWhileServiceDown) {
                            availabilityCallbackKeyAt.mIsHiddenIdPermittedPackage = cameraService.isHiddenIdPermittedPackage(availabilityCallbackKeyAt.mPackageName);
                            availabilityCallbackKeyAt.mIsRegisteredWhileServiceDown = false;
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
                addDeviceStatusHistoryLocked(TextUtils.formatSimple("binderDied(E): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
                if (this.mCameraService == null) {
                    return;
                }
                this.mCameraService = null;
                for (int size = this.mDeviceStatus.size() - 1; size >= 0; size--) {
                    DeviceCameraInfo deviceCameraInfoKeyAt = this.mDeviceStatus.keyAt(size);
                    onStatusChangedLocked(0, deviceCameraInfoKeyAt);
                    if (this.mHasOpenCloseListenerPermission) {
                        onCameraClosedLocked(deviceCameraInfoKeyAt);
                    }
                }
                for (int i = 0; i < this.mTorchStatus.size(); i++) {
                    onTorchStatusChangedLocked(0, this.mTorchStatus.keyAt(i));
                }
                for (int i2 = 0; i2 < this.mCameraDeviceStates.size(); i2++) {
                    onCameraDeviceStateChangedLocked(new CameraDeviceState(this.mCameraDeviceStates.valueAt(i2).mFacing, 3, "android.system", 0), this.mCameraDeviceStates.keyAt(i2));
                }
                this.mConcurrentCameraIdCombinations.clear();
                scheduleCameraServiceReconnectionLocked();
                addDeviceStatusHistoryLocked(TextUtils.formatSimple("binderDied(X): tid(%d): mDeviceStatus size %d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mDeviceStatus.size())));
            }
        }

        private static final class DeviceCameraInfo {
            private final String mCameraId;
            private final int mDeviceId;

            DeviceCameraInfo(String str, int i) {
                this.mCameraId = str;
                this.mDeviceId = i;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj != null && getClass() == obj.getClass()) {
                    DeviceCameraInfo deviceCameraInfo = (DeviceCameraInfo) obj;
                    if (this.mDeviceId == deviceCameraInfo.mDeviceId && Objects.equals(this.mCameraId, deviceCameraInfo.mCameraId)) {
                        return true;
                    }
                }
                return false;
            }

            public int hashCode() {
                return Objects.hash(this.mCameraId, Integer.valueOf(this.mDeviceId));
            }
        }

        private void addDeviceStatusHistoryLocked(String str) {
            if (this.mDeviceStatusHistory.size() == 10) {
                this.mDeviceStatusHistory.removeFirst();
            }
            this.mDeviceStatusHistory.addLast(str);
        }

        private static String cameraStatusToString(int i) {
            if (i == -2) {
                return "STATUS_NOT_AVAILABLE";
            }
            if (i == 0) {
                return "STATUS_NOT_PRESENT";
            }
            if (i == 1) {
                return "STATUS_PRESENT";
            }
            if (i == 2) {
                return "STATUS_ENUMERATING";
            }
            return "STATUS_UNKNOWN";
        }
    }

    public void startDeviceInjector(String[] strArr, String[] strArr2, String str, Executor executor, DeviceInjectorSession.StatusCallback statusCallback) throws CameraAccessException, SecurityException, IllegalArgumentException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        if (strArr == null) {
            throw new IllegalArgumentException("targetPackages was null");
        }
        if (strArr2 == null) {
            throw new IllegalArgumentException("targetCameraIds was null");
        }
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("sourceCameraId was null or empty");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (statusCallback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        for (String str2 : strArr) {
            if (str2 == null || str2.isEmpty()) {
                throw new IllegalArgumentException("targetPackages contains empty of null package name");
            }
        }
        for (String str3 : strArr2) {
            if (str3 == null || str3.isEmpty()) {
                throw new IllegalArgumentException("targetCameraIds contains empty of null camera Id");
            }
            if (str.equals(str3)) {
                throw new IllegalArgumentException("targetCameraIds contains source camera Id");
            }
        }
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                cameraService.startDeviceInjector(strArr, strArr2, str, new DeviceInjectorSessionImpl(statusCallback, executor).getCallback());
            } catch (RemoteException unused) {
                ExceptionUtils.throwAsPublicException(new ServiceSpecificException(4, "Camera service is currently unavailable"));
            } catch (ServiceSpecificException e) {
                ExceptionUtils.throwAsPublicException(e);
            }
        }
    }

    public void startRemoteDeviceInjector(String[] strArr, String[] strArr2, DeviceInjectorSession.RemoteDevice remoteDevice, Executor executor, DeviceInjectorSession.StatusCallback statusCallback) throws CameraAccessException, SecurityException, IllegalArgumentException {
        if (CameraManagerGlobal.sCameraServiceDisabled) {
            throw new IllegalArgumentException("No cameras available on device");
        }
        if (strArr == null) {
            throw new IllegalArgumentException("targetPackages was null");
        }
        if (strArr2 == null) {
            throw new IllegalArgumentException("targetCameraIds was null");
        }
        if (remoteDevice == null) {
            throw new IllegalArgumentException("sourceDevice was null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("executor was null");
        }
        if (statusCallback == null) {
            throw new IllegalArgumentException("callback was null");
        }
        for (String str : strArr) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("targetPackages contains empty of null package name");
            }
        }
        for (String str2 : strArr2) {
            if (str2 == null || str2.isEmpty()) {
                throw new IllegalArgumentException("targetCameraIds contains empty of null camera Id");
            }
        }
        ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
        if (cameraService == null) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
        }
        synchronized (this.mLock) {
            try {
                DeviceInjectorSessionImpl deviceInjectorSessionImpl = new DeviceInjectorSessionImpl(statusCallback, executor);
                cameraService.startRemoteDeviceInjector(strArr, strArr2, deviceInjectorSessionImpl.getRemoteDevice(remoteDevice), deviceInjectorSessionImpl.getCallback());
            } catch (RemoteException unused) {
                ExceptionUtils.throwAsPublicException(new ServiceSpecificException(4, "Camera service is currently unavailable"));
            } catch (ServiceSpecificException e) {
                ExceptionUtils.throwAsPublicException(e);
            }
        }
    }
}
