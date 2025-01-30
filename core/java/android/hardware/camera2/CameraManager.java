package android.hardware.camera2;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.CameraExtensionSessionStats;
import android.hardware.CameraStatus;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceListener;
import android.hardware.IDeviceInjectorCallback;
import android.hardware.IRemoteDevice;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl.CameraInjectionSessionImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.DeviceInjectorSessionImpl;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.CameraIdAndSessionConfiguration;
import android.hardware.camera2.utils.ConcurrentCameraIdCombination;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.DeadObjectException;
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
import android.util.Size;
import android.view.Display;
import com.android.internal.C4337R;
import com.android.internal.util.ArrayUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class CameraManager {
  private static final int API_VERSION_1 = 1;
  private static final int API_VERSION_2 = 2;
  private static final String CAMERA_OPEN_CLOSE_LISTENER_PERMISSION =
      "android.permission.CAMERA_OPEN_CLOSE_LISTENER";
  private static final int CAMERA_TYPE_ALL = 1;
  private static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
  private static final long ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA =
      244358506;
  public static final String LANDSCAPE_TO_PORTRAIT_PROP = "camera.enable_landscape_to_portrait";
  public static final long OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT = 250678880;
  private static final String TAG = "CameraManager";
  public static final int TORCH_STRENGTH_1 = 1;
  public static final int TORCH_STRENGTH_2 = 2;
  public static final int TORCH_STRENGTH_3 = 3;
  public static final int TORCH_STRENGTH_4 = 4;
  public static final int TORCH_STRENGTH_5 = 5;
  public static final int TORCH_STRENGTH_DEFAULT = 0;
  private static final int TORCH_STRENGTH_MAX = 5;
  private static final int USE_CALLING_UID = -1;
  private final boolean DEBUG = false;
  private final Context mContext;
  private ArrayList<String> mDeviceIdList;
  private final boolean mHasOpenCloseListenerPermission;
  private Boolean mHiddenCameraPermittedState;
  private final Object mLock;

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
      return other.mFacing == this.mFacing
          && other.mDeviceState == this.mDeviceState
          && other.mClientName.equals(this.mClientName)
          && other.mUserId == this.mUserId;
    }

    public String toString() {
      return String.format(
          "facing %s state now %s for client %s",
          cameraFacingToString(this.mFacing),
          cameraStateToString(this.mDeviceState),
          this.mClientName);
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

  public CameraManager(Context context) {
    Object obj = new Object();
    this.mLock = obj;
    this.mHiddenCameraPermittedState = null;
    synchronized (obj) {
      this.mContext = context;
      this.mHasOpenCloseListenerPermission =
          context.checkSelfPermission("android.permission.CAMERA_OPEN_CLOSE_LISTENER") == 0;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
    private ArrayList<WeakReference<DeviceStateListener>> mDeviceStateListeners = new ArrayList<>();
    private boolean mFoldedDeviceState;
    private final int[] mFoldedDeviceStates;

    public FoldStateListener(Context context) {
      this.mFoldedDeviceStates =
          context.getResources().getIntArray(C4337R.array.config_foldedDeviceStates);
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
      this.mDeviceStateListeners.removeIf(
          new Predicate() { // from class:
                            // android.hardware.camera2.CameraManager$FoldStateListener$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              return CameraManager.FoldStateListener.lambda$addDeviceStateListener$0(
                  (WeakReference) obj);
            }
          });
      this.mDeviceStateListeners.add(new WeakReference<>(listener));
    }

    static /* synthetic */ boolean lambda$addDeviceStateListener$0(WeakReference l) {
      return l.get() == null;
    }

    @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
    public final void onBaseStateChanged(int state) {
      handleStateChange(state);
    }

    @Override // android.hardware.devicestate.DeviceStateManager.DeviceStateCallback
    public final void onStateChanged(int state) {
      handleStateChange(state);
    }
  }

  public void registerDeviceStateListener(CameraCharacteristics chars) {
    CameraManagerGlobal.get().registerDeviceStateListener(chars, this.mContext);
  }

  public String[] getCameraIdList() throws CameraAccessException {
    return CameraManagerGlobal.get().getCameraIdList();
  }

  public String[] getCameraIdListNoLazy() throws CameraAccessException {
    return CameraManagerGlobal.get().getCameraIdListNoLazy();
  }

  public Set<Set<String>> getConcurrentCameraIds() throws CameraAccessException {
    return CameraManagerGlobal.get().getConcurrentCameraIds();
  }

  public boolean isConcurrentSessionConfigurationSupported(
      Map<String, SessionConfiguration> cameraIdAndSessionConfig) throws CameraAccessException {
    return CameraManagerGlobal.get()
        .isConcurrentSessionConfigurationSupported(
            cameraIdAndSessionConfig, this.mContext.getApplicationInfo().targetSdkVersion);
  }

  public void registerAvailabilityCallback(AvailabilityCallback callback, Handler handler) {
    if (callback != null) {
      boolean isHiddenIdPermittedPackage = false;
      callback.mPackageName = this.mContext.getOpPackageName();
      callback.mIsRegisteredWhileServiceDown = false;
      ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
      if (cameraService == null) {
        Log.m96e(TAG, "Camera service is currently unavailable");
        callback.mIsRegisteredWhileServiceDown = true;
      } else {
        try {
          isHiddenIdPermittedPackage =
              cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
        } catch (RemoteException e) {
          callback.mIsRegisteredWhileServiceDown = true;
          Log.m97e(TAG, "Camera service is currently unavailable", e);
        }
      }
      callback.mIsHiddenIdPermittedPackage = isHiddenIdPermittedPackage;
      Log.m98i(
          TAG,
          "registerAvailabilityCallback: Is device callback = "
              + this.mHasOpenCloseListenerPermission);
    }
    CameraManagerGlobal.get()
        .registerAvailabilityCallback(
            callback,
            CameraDeviceImpl.checkAndWrapHandler(handler),
            this.mHasOpenCloseListenerPermission);
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
        Log.m96e(TAG, "Camera service is currently unavailable");
        callback.mIsRegisteredWhileServiceDown = true;
      } else {
        try {
          isHiddenIdPermittedPackage =
              cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
        } catch (RemoteException e) {
          callback.mIsRegisteredWhileServiceDown = true;
          Log.m97e(TAG, "Camera service is currently unavailable", e);
        }
      }
      callback.mIsHiddenIdPermittedPackage = isHiddenIdPermittedPackage;
      Log.m98i(
          TAG,
          "registerAvailabilityCallback: Is device callback = "
              + this.mHasOpenCloseListenerPermission);
    }
    CameraManagerGlobal.get()
        .registerAvailabilityCallback(callback, executor, this.mHasOpenCloseListenerPermission);
  }

  public void unregisterAvailabilityCallback(AvailabilityCallback callback) {
    CameraManagerGlobal.get().unregisterAvailabilityCallback(callback);
  }

  public void registerTorchCallback(TorchCallback callback, Handler handler) {
    CameraManagerGlobal.get()
        .registerTorchCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler));
  }

  public void registerTorchCallback(Executor executor, TorchCallback callback) {
    if (executor == null) {
      throw new IllegalArgumentException("executor was null");
    }
    CameraManagerGlobal.get().registerTorchCallback(callback, executor);
  }

  public void unregisterTorchCallback(TorchCallback callback) {
    CameraManagerGlobal.get().unregisterTorchCallback(callback);
  }

  public void registerSemCameraDeviceStateCallback(
      SemCameraDeviceStateCallback callback, Handler handler) {
    CameraManagerGlobal.get()
        .registerSemCameraDeviceStateCallback(
            callback, CameraDeviceImpl.checkAndWrapHandler(handler));
  }

  public void registerSemCameraDeviceStateCallback(
      SemCameraDeviceStateCallback callback, Handler handler, boolean isExtended) {
    callback.isExtended = isExtended;
    registerSemCameraDeviceStateCallback(callback, handler);
  }

  public void unregisterSemCameraDeviceStateCallback(SemCameraDeviceStateCallback callback) {
    CameraManagerGlobal.get().unregisterSemCameraDeviceStateCallback(callback);
  }

  private Size getDisplaySize() {
    Size ret = new Size(0, 0);
    try {
      DisplayManager displayManager =
          (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
      Display display = displayManager.getDisplay(0);
      if (display == null) {
        Log.m96e(TAG, "Invalid default display!");
        return ret;
      }
      Point sz = new Point();
      display.getRealSize(sz);
      int width = sz.f85x;
      int height = sz.f86y;
      if (height > width) {
        height = width;
        width = sz.f86y;
      }
      return new Size(width, height);
    } catch (Exception e) {
      Log.m96e(TAG, "getDisplaySize Failed. " + e);
      return ret;
    }
  }

  private Map<String, StreamConfiguration[]> getPhysicalCameraMultiResolutionConfigs(
      String cameraId, CameraMetadataNative info, ICameraService cameraService)
      throws CameraAccessException {
    HashMap<String, StreamConfiguration[]> multiResolutionStreamConfigurations = new HashMap<>();
    Boolean multiResolutionStreamSupported =
        (Boolean) info.get(CameraCharacteristics.SCALER_MULTI_RESOLUTION_STREAM_SUPPORTED);
    if (multiResolutionStreamSupported == null || !multiResolutionStreamSupported.booleanValue()) {
      return multiResolutionStreamConfigurations;
    }
    Set<String> physicalCameraIds = info.getPhysicalCameraIds();
    if (physicalCameraIds.size() == 0 && info.isUltraHighResolutionSensor()) {
      StreamConfiguration[] configs =
          (StreamConfiguration[])
              info.get(
                  CameraCharacteristics
                      .SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
      if (configs != null) {
        multiResolutionStreamConfigurations.put(cameraId, configs);
      }
      return multiResolutionStreamConfigurations;
    }
    try {
      for (String physicalCameraId : physicalCameraIds) {
        CameraMetadataNative physicalCameraInfo =
            cameraService.getCameraCharacteristics(
                physicalCameraId, this.mContext.getApplicationInfo().targetSdkVersion, false);
        StreamConfiguration[] configs2 =
            (StreamConfiguration[])
                physicalCameraInfo.get(
                    CameraCharacteristics
                        .SCALER_PHYSICAL_CAMERA_MULTI_RESOLUTION_STREAM_CONFIGURATIONS);
        if (configs2 != null) {
          multiResolutionStreamConfigurations.put(physicalCameraId, configs2);
        }
      }
    } catch (RemoteException e) {
      ServiceSpecificException sse =
          new ServiceSpecificException(4, "Camera service is currently unavailable");
      throwAsPublicException(sse);
    }
    return multiResolutionStreamConfigurations;
  }

  public CameraCharacteristics getCameraCharacteristics(String cameraId)
      throws CameraAccessException {
    return getCameraCharacteristics(cameraId, shouldOverrideToPortrait(this.mContext));
  }

  public CameraCharacteristics getCameraCharacteristics(String cameraId, boolean overrideToPortrait)
      throws CameraAccessException {
    CameraCharacteristics characteristics = null;
    if (CameraManagerGlobal.sCameraServiceDisabled) {
      throw new IllegalArgumentException("No cameras available on device");
    }
    synchronized (this.mLock) {
      ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
      if (cameraService == null) {
        throw new CameraAccessException(2, "Camera service is currently unavailable");
      }
      try {
        try {
          if (Integer.parseInt(cameraId) >= 20) {
            String currentPackage = this.mContext.getOpPackageName();
            if (this.mHiddenCameraPermittedState == null) {
              this.mHiddenCameraPermittedState =
                  Boolean.valueOf(cameraService.isHiddenIdPermittedPackage(currentPackage));
            }
            if (!this.mHiddenCameraPermittedState.booleanValue()) {
              throw new IllegalArgumentException(String.format("Unknown camera ID %s", cameraId));
            }
          }
          try {
            try {
              Size displaySize = getDisplaySize();
              CameraMetadataNative info =
                  cameraService.getCameraCharacteristics(
                      cameraId,
                      this.mContext.getApplicationInfo().targetSdkVersion,
                      overrideToPortrait);
              try {
                info.setCameraId(Integer.parseInt(cameraId));
              } catch (NumberFormatException e) {
                Log.m100v(TAG, "Failed to parse camera Id " + cameraId + " to integer");
              }
              boolean hasConcurrentStreams =
                  CameraManagerGlobal.get().cameraIdHasConcurrentStreamsLocked(cameraId);
              info.setHasMandatoryConcurrentStreams(hasConcurrentStreams);
              info.setDisplaySize(displaySize);
              Map<String, StreamConfiguration[]> multiResolutionSizeMap =
                  getPhysicalCameraMultiResolutionConfigs(cameraId, info, cameraService);
              if (multiResolutionSizeMap.size() > 0) {
                info.setMultiResolutionStreamConfigurationMap(multiResolutionSizeMap);
              }
              characteristics = new CameraCharacteristics(info);
            } catch (RemoteException e2) {
              throw new CameraAccessException(2, "Camera service is currently unavailable", e2);
            }
          } catch (ServiceSpecificException e3) {
            throwAsPublicException(e3);
          }
        } catch (NumberFormatException e4) {
          throw new IllegalArgumentException(
              "Expected cameraId to be numeric, but it was: " + cameraId);
        }
      } catch (RemoteException e5) {
        throw new CameraAccessException(2, "Camera service is currently unavailable", e5);
      }
    }
    registerDeviceStateListener(characteristics);
    return characteristics;
  }

  public CameraExtensionCharacteristics getCameraExtensionCharacteristics(String cameraId)
      throws CameraAccessException {
    CameraCharacteristics chars = getCameraCharacteristics(cameraId);
    Map<String, CameraCharacteristics> characteristicsMap = getPhysicalIdToCharsMap(chars);
    characteristicsMap.put(cameraId, chars);
    return new CameraExtensionCharacteristics(this.mContext, cameraId, characteristicsMap);
  }

  private Map<String, CameraCharacteristics> getPhysicalIdToCharsMap(CameraCharacteristics chars)
      throws CameraAccessException {
    HashMap<String, CameraCharacteristics> physicalIdsToChars = new HashMap<>();
    Set<String> physicalCameraIds = chars.getPhysicalCameraIds();
    for (String physicalCameraId : physicalCameraIds) {
      CameraCharacteristics physicalChars = getCameraCharacteristics(physicalCameraId);
      physicalIdsToChars.put(physicalCameraId, physicalChars);
    }
    return physicalIdsToChars;
  }

  private CameraDevice openCameraDeviceUserAsync(
      String cameraId,
      CameraDevice.StateCallback callback,
      Executor executor,
      int uid,
      int oomScoreOffset,
      boolean overrideToPortrait)
      throws CameraAccessException {
    CameraDeviceImpl deviceImpl;
    ICameraService cameraService;
    CameraCharacteristics characteristics = getCameraCharacteristics(cameraId);
    Map<String, CameraCharacteristics> physicalIdsToChars =
        getPhysicalIdToCharsMap(characteristics);
    synchronized (this.mLock) {
      ICameraDeviceUser cameraUser = null;
      deviceImpl =
          new CameraDeviceImpl(
              cameraId,
              callback,
              executor,
              characteristics,
              physicalIdsToChars,
              this.mContext.getApplicationInfo().targetSdkVersion,
              this.mContext);
      ICameraDeviceCallbacks callbacks = deviceImpl.getCallbacks();
      try {
        cameraService = CameraManagerGlobal.get().getCameraService();
      } catch (RemoteException e) {
        ServiceSpecificException sse =
            new ServiceSpecificException(4, "Camera service is currently unavailable");
        deviceImpl.setRemoteFailure(sse);
        throwAsPublicException(sse);
      } catch (ServiceSpecificException e2) {
        if (e2.errorCode == 9) {
          throw new AssertionError("Should've gone down the shim path");
        }
        if (e2.errorCode != 7
            && e2.errorCode != 8
            && e2.errorCode != 6
            && e2.errorCode != 4
            && e2.errorCode != 10) {
          throwAsPublicException(e2);
        }
        deviceImpl.setRemoteFailure(e2);
        if (e2.errorCode == 6 || e2.errorCode == 4 || e2.errorCode == 7) {
          throwAsPublicException(e2);
        }
      }
      if (cameraService == null) {
        throw new ServiceSpecificException(4, "Camera service is currently unavailable");
      }
      cameraUser =
          cameraService.connectDevice(
              callbacks,
              cameraId,
              this.mContext.getOpPackageName(),
              this.mContext.getAttributionTag(),
              uid,
              oomScoreOffset,
              this.mContext.getApplicationInfo().targetSdkVersion,
              overrideToPortrait);
      deviceImpl.setRemoteDevice(cameraUser);
    }
    return deviceImpl;
  }

  public void openCamera(String cameraId, CameraDevice.StateCallback callback, Handler handler)
      throws CameraAccessException {
    openCameraForUid(cameraId, callback, CameraDeviceImpl.checkAndWrapHandler(handler), -1);
  }

  public void openCamera(
      String cameraId,
      boolean overrideToPortrait,
      Handler handler,
      CameraDevice.StateCallback callback)
      throws CameraAccessException {
    openCameraForUid(
        cameraId,
        callback,
        CameraDeviceImpl.checkAndWrapHandler(handler),
        -1,
        0,
        overrideToPortrait);
  }

  public void openCamera(String cameraId, Executor executor, CameraDevice.StateCallback callback)
      throws CameraAccessException {
    if (executor == null) {
      throw new IllegalArgumentException("executor was null");
    }
    openCameraForUid(cameraId, callback, executor, -1);
  }

  @SystemApi
  public void openCamera(
      String cameraId, int oomScoreOffset, Executor executor, CameraDevice.StateCallback callback)
      throws CameraAccessException {
    if (executor == null) {
      throw new IllegalArgumentException("executor was null");
    }
    if (oomScoreOffset < 0) {
      throw new IllegalArgumentException(
          "oomScoreOffset < 0, cannot increase priority of camera client");
    }
    openCameraForUid(
        cameraId, callback, executor, -1, oomScoreOffset, shouldOverrideToPortrait(this.mContext));
  }

  public void openCameraForUid(
      String cameraId,
      CameraDevice.StateCallback callback,
      Executor executor,
      int clientUid,
      int oomScoreOffset,
      boolean overrideToPortrait)
      throws CameraAccessException {
    if (cameraId == null) {
      throw new IllegalArgumentException("cameraId was null");
    }
    if (callback == null) {
      throw new IllegalArgumentException("callback was null");
    }
    if (CameraManagerGlobal.sCameraServiceDisabled) {
      throw new IllegalArgumentException("No cameras available on device");
    }
    openCameraDeviceUserAsync(
        cameraId, callback, executor, clientUid, oomScoreOffset, overrideToPortrait);
  }

  public void openCameraForUid(
      String cameraId, CameraDevice.StateCallback callback, Executor executor, int clientUid)
      throws CameraAccessException {
    openCameraForUid(
        cameraId, callback, executor, clientUid, 0, shouldOverrideToPortrait(this.mContext));
  }

  public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
    if (CameraManagerGlobal.sCameraServiceDisabled) {
      throw new IllegalArgumentException("No cameras available on device");
    }
    CameraManagerGlobal.get().setTorchMode(cameraId, enabled);
  }

  @Deprecated(forRemoval = true, since = "15.5")
  public void semSetTorchMode(String cameraId, boolean enabled, int strength)
      throws CameraAccessException {
    Log.m98i(
        TAG,
        "semSetTorchMode: cameraId = "
            + cameraId
            + ", enabled = "
            + enabled
            + ", strength = "
            + strength);
    if (enabled) {
      if (strength < 0 || strength > 5) {
        throw new IllegalArgumentException("Strength is out of supported range");
      }
      if (strength == 0) {
        setTorchMode(cameraId, true);
        return;
      } else {
        turnOnTorchWithStrengthLevel(cameraId, strength);
        return;
      }
    }
    setTorchMode(cameraId, false);
  }

  public void turnOnTorchWithStrengthLevel(String cameraId, int torchStrength)
      throws CameraAccessException {
    if (CameraManagerGlobal.sCameraServiceDisabled) {
      throw new IllegalArgumentException("No camera available on device");
    }
    CameraManagerGlobal.get().turnOnTorchWithStrengthLevel(cameraId, torchStrength);
  }

  public int getTorchStrengthLevel(String cameraId) throws CameraAccessException {
    if (CameraManagerGlobal.sCameraServiceDisabled) {
      throw new IllegalArgumentException("No camera available on device.");
    }
    return CameraManagerGlobal.get().getTorchStrengthLevel(cameraId);
  }

  public static boolean shouldOverrideToPortrait(Context context) {
    PackageManager packageManager = null;
    String packageName = null;
    if (context != null) {
      packageManager = context.getPackageManager();
      packageName = context.getOpPackageName();
    }
    return shouldOverrideToPortrait(packageManager, packageName);
  }

  public static boolean shouldOverrideToPortrait(
      PackageManager packageManager, String packageName) {
    if (!CameraManagerGlobal.sLandscapeToPortrait) {
      return false;
    }
    if (packageManager != null && packageName != null) {
      try {
        return packageManager
            .getProperty(PackageManager.PROPERTY_COMPAT_OVERRIDE_LANDSCAPE_TO_PORTRAIT, packageName)
            .getBoolean();
      } catch (PackageManager.NameNotFoundException e) {
      }
    }
    return CompatChanges.isChangeEnabled(OVERRIDE_CAMERA_LANDSCAPE_TO_PORTRAIT);
  }

  public static boolean physicalCallbacksAreEnabledForUnavailableCamera() {
    return CompatChanges.isChangeEnabled(
        ENABLE_PHYSICAL_CAMERA_CALLBACK_FOR_UNAVAILABLE_LOGICAL_CAMERA);
  }

  public abstract static class AvailabilityCallback {
    private boolean mIsHiddenIdPermittedPackage;
    private boolean mIsRegisteredWhileServiceDown;
    private String mPackageName;

    public void onCameraAvailable(String cameraId) {}

    public void onCameraUnavailable(String cameraId) {}

    public void onCameraAccessPrioritiesChanged() {}

    public void onPhysicalCameraAvailable(String cameraId, String physicalCameraId) {}

    public void onPhysicalCameraUnavailable(String cameraId, String physicalCameraId) {}

    @SystemApi
    public void onCameraOpened(String cameraId, String packageId) {}

    @SystemApi
    public void onCameraClosed(String cameraId) {}

    public void onSemCameraDeviceOpen(String cameraId, int facing, String clientName) {}

    public void onSemCameraDeviceClose(String cameraId, int facing, String clientName) {}

    public void onSemCameraDeviceIdle(String cameraId, int facing, String clientName) {}

    public void onSemCameraDeviceActive(String cameraId, int facing, String clientName) {}

    public void onSemCameraDeviceRawStatus(String cameraId, int rawStatus) {}
  }

  public abstract static class TorchCallback {
    public void onTorchModeUnavailable(String cameraId) {}

    public void onTorchModeChanged(String cameraId, boolean enabled) {}

    public void onTorchStrengthLevelChanged(String cameraId, int newStrengthLevel) {}
  }

  public abstract static class SemCameraDeviceStateCallback {
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

    public void onCameraDeviceStateChanged(
        String cameraId, int facing, int newCameraState, String clientName) {}

    public void onCameraDeviceStateChanged(
        String cameraId, int facing, int newCameraState, String clientName, int userId) {}
  }

  public static void throwAsPublicException(Throwable t) throws CameraAccessException {
    int reason;
    if (t instanceof ServiceSpecificException) {
      ServiceSpecificException e = (ServiceSpecificException) t;
      switch (e.errorCode) {
        case 1:
          throw new SecurityException(e.getMessage(), e);
        case 2:
        case 3:
          throw new IllegalArgumentException(e.getMessage(), e);
        case 4:
          reason = 2;
          break;
        case 5:
        default:
          reason = 3;
          break;
        case 6:
          reason = 1;
          break;
        case 7:
          reason = 4;
          break;
        case 8:
          reason = 5;
          break;
        case 9:
          reason = 1000;
          break;
      }
      throw new CameraAccessException(reason, e.getMessage(), e);
    }
    if (t instanceof DeadObjectException) {
      throw new CameraAccessException(2, "Camera service has died unexpectedly", t);
    }
    if (t instanceof RemoteException) {
      throw new UnsupportedOperationException(
          "An unknown RemoteException was thrown which should never happen.", t);
    }
    if (t instanceof RuntimeException) {
      throw ((RuntimeException) t);
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

  public void injectCamera(
      String packageName,
      String internalCamId,
      String externalCamId,
      Executor executor,
      CameraInjectionSession.InjectionStatusCallback callback)
      throws CameraAccessException, SecurityException, IllegalArgumentException {
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
          CameraInjectionSessionImpl injectionSessionImpl =
              new CameraInjectionSessionImpl(callback, executor);
          ICameraInjectionCallback cameraInjectionCallback = injectionSessionImpl.getCallback();
          ICameraInjectionSession injectionSession =
              cameraService.injectCamera(
                  packageName, internalCamId, externalCamId, cameraInjectionCallback);
          injectionSessionImpl.setRemoteInjectionSession(injectionSession);
        } catch (ServiceSpecificException e) {
          throwAsPublicException(e);
        }
      } catch (RemoteException e2) {
        ServiceSpecificException sse =
            new ServiceSpecificException(4, "Camera service is currently unavailable");
        throwAsPublicException(sse);
      }
    }
  }

  public static String reportExtensionSessionStats(CameraExtensionSessionStats extStats) {
    ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
    if (cameraService == null) {
      Log.m96e(TAG, "CameraService not available. Not reporting extension stats.");
      return "";
    }
    try {
      return cameraService.reportExtensionSessionStats(extStats);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to report extension session stats to cameraservice.", e);
      return "";
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class CameraManagerGlobal extends ICameraServiceListener.Stub
      implements IBinder.DeathRecipient {
    private static final String CAMERA_SERVICE_BINDER_NAME = "media.camera";
    private static final String TAG = "CameraManagerGlobal";
    private static final CameraManagerGlobal gCameraManager = new CameraManagerGlobal();
    public static final boolean sCameraServiceDisabled =
        SystemProperties.getBoolean("config.disable_cameraservice", false);
    public static final boolean sLandscapeToPortrait =
        SystemProperties.getBoolean(CameraManager.LANDSCAPE_TO_PORTRAIT_PROP, false);
    private ICameraService mCameraService;
    private Handler mDeviceStateHandler;
    private HandlerThread mDeviceStateHandlerThread;
    private FoldStateListener mFoldStateListener;
    private final boolean DEBUG = false;
    private final int CAMERA_SERVICE_RECONNECT_DELAY_MS = 1000;
    private final ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
    private final ArrayMap<String, Integer> mDeviceStatus = new ArrayMap<>();
    private final ArrayMap<String, ArrayList<String>> mUnavailablePhysicalDevices =
        new ArrayMap<>();
    private final ArrayMap<String, String> mOpenedDevices = new ArrayMap<>();
    private final Set<Set<String>> mConcurrentCameraIdCombinations = new ArraySet();
    private final ArrayMap<AvailabilityCallback, Executor> mCallbackMap = new ArrayMap<>();
    private Binder mTorchClientBinder = new Binder();
    private final ArrayMap<String, Integer> mTorchStatus = new ArrayMap<>();
    private final ArrayMap<TorchCallback, Executor> mTorchCallbackMap = new ArrayMap<>();
    private final ArrayMap<String, CameraDeviceState> mCameraDeviceStates = new ArrayMap<>();
    private final ArrayMap<SemCameraDeviceStateCallback, Executor>
        mSemCameraDeviceStateCallbackMap = new ArrayMap<>();
    private final Object mLock = new Object();
    private boolean mHasOpenCloseListenerPermission = false;

    private CameraManagerGlobal() {}

    public static CameraManagerGlobal get() {
      return gCameraManager;
    }

    public void registerDeviceStateListener(CameraCharacteristics chars, Context ctx) {
      synchronized (this.mLock) {
        if (this.mDeviceStateHandlerThread == null) {
          HandlerThread handlerThread = new HandlerThread(TAG);
          this.mDeviceStateHandlerThread = handlerThread;
          handlerThread.start();
          this.mDeviceStateHandler = new Handler(this.mDeviceStateHandlerThread.getLooper());
        }
        if (this.mFoldStateListener == null) {
          this.mFoldStateListener = new FoldStateListener(ctx);
          try {
            ((DeviceStateManager) ctx.getSystemService(DeviceStateManager.class))
                .registerCallback(
                    new HandlerExecutor(this.mDeviceStateHandler), this.mFoldStateListener);
          } catch (IllegalStateException e) {
            this.mFoldStateListener = null;
            Log.m100v(TAG, "Failed to register device state listener!");
            Log.m100v(TAG, "Device state dependent characteristics updates will not befunctional!");
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
          Log.m96e(TAG, "Camera service is unavailable");
        }
        iCameraService = this.mCameraService;
      }
      return iCameraService;
    }

    private void connectCameraServiceLocked() {
      if (this.mCameraService != null || sCameraServiceDisabled) {
        return;
      }
      Log.m98i(TAG, "Connecting to camera service");
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
          for (CameraStatus c : cameraStatuses) {
            onStatusChangedLocked(c.status, c.cameraId);
            if (c.unavailablePhysicalCameras != null) {
              for (String unavailPhysicalCamera : c.unavailablePhysicalCameras) {
                onPhysicalCameraStatusChangedLocked(0, c.cameraId, unavailPhysicalCamera);
              }
            }
            if (this.mHasOpenCloseListenerPermission
                && c.status == -2
                && !c.clientPackage.isEmpty()) {
              onCameraOpenedLocked(c.cameraId, c.clientPackage);
            }
          }
          this.mCameraService = cameraService;
        } catch (RemoteException e2) {
        } catch (ServiceSpecificException e3) {
          throw new IllegalStateException("Failed to register a camera service listener", e3);
        }
        try {
          ConcurrentCameraIdCombination[] cameraIdCombinations =
              cameraService.getConcurrentCameraIds();
          for (ConcurrentCameraIdCombination comb : cameraIdCombinations) {
            this.mConcurrentCameraIdCombinations.add(comb.getConcurrentCameraIdCombination());
          }
        } catch (RemoteException e4) {
        } catch (ServiceSpecificException e5) {
          throw new IllegalStateException("Failed to get concurrent camera id combinations", e5);
        }
      } catch (RemoteException e6) {
      }
    }

    private String[] extractCameraIdListLocked() {
      int idCount = 0;
      for (int i = 0; i < this.mDeviceStatus.size(); i++) {
        int status = this.mDeviceStatus.valueAt(i).intValue();
        if (status != 0 && status != 2) {
          idCount++;
        }
      }
      String[] cameraIds = new String[idCount];
      int idCount2 = 0;
      for (int i2 = 0; i2 < this.mDeviceStatus.size(); i2++) {
        int status2 = this.mDeviceStatus.valueAt(i2).intValue();
        if (status2 != 0 && status2 != 2) {
          cameraIds[idCount2] = this.mDeviceStatus.keyAt(i2);
          idCount2++;
        }
      }
      return cameraIds;
    }

    private Set<Set<String>> extractConcurrentCameraIdListLocked() {
      Set<Set<String>> concurrentCameraIds = new ArraySet<>();
      for (Set<String> cameraIds : this.mConcurrentCameraIdCombinations) {
        Set<String> extractedCameraIds = new ArraySet<>();
        for (String cameraId : cameraIds) {
          Integer status = this.mDeviceStatus.get(cameraId);
          if (status != null && status.intValue() != 2 && status.intValue() != 0) {
            extractedCameraIds.add(cameraId);
          }
        }
        concurrentCameraIds.add(extractedCameraIds);
      }
      return concurrentCameraIds;
    }

    private static void sortCameraIds(String[] cameraIds) {
      Arrays.sort(
          cameraIds,
          new Comparator<
              String>() { // from class:
                          // android.hardware.camera2.CameraManager.CameraManagerGlobal.1
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

    public static boolean cameraStatusesContains(CameraStatus[] cameraStatuses, String id) {
      for (CameraStatus c : cameraStatuses) {
        if (c.cameraId.equals(id)) {
          return true;
        }
      }
      return false;
    }

    public String[] getCameraIdListNoLazy() {
      String[] cameraIds;
      if (sCameraServiceDisabled) {
        return new String[0];
      }
      ICameraServiceListener.Stub testListener =
          new ICameraServiceListener
              .Stub() { // from class: android.hardware.camera2.CameraManager.CameraManagerGlobal.2
            @Override // android.hardware.ICameraServiceListener
            public void onStatusChanged(int status, String id) throws RemoteException {}

            @Override // android.hardware.ICameraServiceListener
            public void onPhysicalCameraStatusChanged(int status, String id, String physicalId)
                throws RemoteException {}

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStatusChanged(int status, String id) throws RemoteException {}

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStrengthLevelChanged(String id, int newStrengthLevel)
                throws RemoteException {}

            @Override // android.hardware.ICameraServiceListener
            public void onCameraAccessPrioritiesChanged() {}

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpened(String id, String clientPackageId) {}

            @Override // android.hardware.ICameraServiceListener
            public void onCameraClosed(String id) {}

            @Override // android.hardware.ICameraServiceListener
            public void onCameraDeviceStateChanged(
                String cameraId,
                int facing,
                int newCameraState,
                String clientName,
                int apiLevel,
                int userId) {}
          };
      synchronized (this.mLock) {
        connectCameraServiceLocked();
        try {
          CameraStatus[] cameraStatuses = this.mCameraService.addListener(testListener);
          this.mCameraService.removeListener(testListener);
          for (CameraStatus c : cameraStatuses) {
            onStatusChangedLocked(c.status, c.cameraId);
          }
          Set<String> deviceCameraIds = this.mDeviceStatus.keySet();
          ArrayList<String> deviceIdsToRemove = new ArrayList<>();
          for (String deviceCameraId : deviceCameraIds) {
            if (!cameraStatusesContains(cameraStatuses, deviceCameraId)) {
              deviceIdsToRemove.add(deviceCameraId);
            }
          }
          Iterator<String> it = deviceIdsToRemove.iterator();
          while (it.hasNext()) {
            String id = it.next();
            onStatusChangedLocked(0, id);
            this.mTorchStatus.remove(id);
          }
        } catch (RemoteException e) {
        } catch (ServiceSpecificException e2) {
          throw new IllegalStateException("Failed to register a camera service listener", e2);
        }
        cameraIds = extractCameraIdListLocked();
      }
      sortCameraIds(cameraIds);
      int publicIdCount = 0;
      for (String str : cameraIds) {
        if (Integer.parseInt(str) < 20) {
          publicIdCount++;
        }
      }
      String[] publicCameraIds = new String[publicIdCount];
      int publicIdCount2 = 0;
      for (String cameraId : cameraIds) {
        if (Integer.parseInt(cameraId) < 20) {
          publicCameraIds[publicIdCount2] = cameraId;
          publicIdCount2++;
        }
      }
      return publicCameraIds;
    }

    public String[] getCameraIdList() {
      String[] cameraIds;
      synchronized (this.mLock) {
        connectCameraServiceLocked();
        cameraIds = extractCameraIdListLocked();
      }
      sortCameraIds(cameraIds);
      int publicIdCount = 0;
      for (String str : cameraIds) {
        if (Integer.parseInt(str) < 20) {
          publicIdCount++;
        }
      }
      String[] publicCameraIds = new String[publicIdCount];
      int publicIdCount2 = 0;
      for (String cameraId : cameraIds) {
        if (Integer.parseInt(cameraId) < 20) {
          publicCameraIds[publicIdCount2] = cameraId;
          publicIdCount2++;
        }
      }
      return publicCameraIds;
    }

    public Set<Set<String>> getConcurrentCameraIds() {
      Set<Set<String>> concurrentStreamingCameraIds;
      synchronized (this.mLock) {
        connectCameraServiceLocked();
        concurrentStreamingCameraIds = extractConcurrentCameraIdListLocked();
      }
      return concurrentStreamingCameraIds;
    }

    public boolean isConcurrentSessionConfigurationSupported(
        Map<String, SessionConfiguration> cameraIdsAndSessionConfigurations, int targetSdkVersion)
        throws CameraAccessException {
      if (cameraIdsAndSessionConfigurations == null) {
        throw new IllegalArgumentException("cameraIdsAndSessionConfigurations was null");
      }
      int size = cameraIdsAndSessionConfigurations.size();
      if (size == 0) {
        throw new IllegalArgumentException("camera id and session combination is empty");
      }
      synchronized (this.mLock) {
        boolean subsetFound = false;
        for (Set<String> combination : this.mConcurrentCameraIdCombinations) {
          if (combination.containsAll(cameraIdsAndSessionConfigurations.keySet())) {
            subsetFound = true;
          }
        }
        if (!subsetFound) {
          Log.m100v(
              TAG,
              "isConcurrentSessionConfigurationSupported called with a subset ofcamera ids not"
                  + " returned by getConcurrentCameraIds");
          return false;
        }
        CameraIdAndSessionConfiguration[] cameraIdsAndConfigs =
            new CameraIdAndSessionConfiguration[size];
        int i = 0;
        for (Map.Entry<String, SessionConfiguration> pair :
            cameraIdsAndSessionConfigurations.entrySet()) {
          cameraIdsAndConfigs[i] =
              new CameraIdAndSessionConfiguration(pair.getKey(), pair.getValue());
          i++;
        }
        try {
          return this.mCameraService.isConcurrentSessionConfigurationSupported(
              cameraIdsAndConfigs, targetSdkVersion);
        } catch (RemoteException e) {
          throw new CameraAccessException(2, "Camera service is currently unavailable", e);
        } catch (ServiceSpecificException e2) {
          CameraManager.throwAsPublicException(e2);
          return false;
        }
      }
    }

    public boolean cameraIdHasConcurrentStreamsLocked(String cameraId) {
      if (!this.mDeviceStatus.containsKey(cameraId)) {
        return false;
      }
      for (Set<String> comb : this.mConcurrentCameraIdCombinations) {
        if (comb.contains(cameraId)) {
          return true;
        }
      }
      return false;
    }

    public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
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
            cameraService.setTorchMode(cameraId, enabled, this.mTorchClientBinder);
          } catch (RemoteException e) {
            throw new CameraAccessException(2, "Camera service is currently unavailable");
          } catch (ServiceSpecificException e2) {
            CameraManager.throwAsPublicException(e2);
          }
        } catch (Throwable th) {
          throw th;
        }
      }
    }

    public void turnOnTorchWithStrengthLevel(String cameraId, int torchStrength)
        throws CameraAccessException {
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
            cameraService.turnOnTorchWithStrengthLevel(
                cameraId, torchStrength, this.mTorchClientBinder);
          } catch (RemoteException e) {
            throw new CameraAccessException(2, "Camera service is currently unavailable.");
          } catch (ServiceSpecificException e2) {
            CameraManager.throwAsPublicException(e2);
          }
        } catch (Throwable th) {
          throw th;
        }
      }
    }

    public int getTorchStrengthLevel(String cameraId) throws CameraAccessException {
      int torchStrength = 0;
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
            torchStrength = cameraService.getTorchStrengthLevel(cameraId);
          } catch (RemoteException e) {
            throw new CameraAccessException(2, "Camera service is currently unavailable.");
          } catch (ServiceSpecificException e2) {
            CameraManager.throwAsPublicException(e2);
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
          Log.m102w(TAG, e.getMessage());
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

    private void postSingleAccessPriorityChangeUpdate(
        final AvailabilityCallback callback, Executor executor) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager.CameraManagerGlobal.3
              @Override // java.lang.Runnable
              public void run() {
                callback.onCameraAccessPrioritiesChanged();
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void postSingleCameraOpenedUpdate(
        final AvailabilityCallback callback,
        Executor executor,
        final String id,
        final String packageId) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager.CameraManagerGlobal.4
              @Override // java.lang.Runnable
              public void run() {
                callback.onCameraOpened(id, packageId);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void postSingleCameraClosedUpdate(
        final AvailabilityCallback callback, Executor executor, final String id) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager.CameraManagerGlobal.5
              @Override // java.lang.Runnable
              public void run() {
                callback.onCameraClosed(id);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void postSingleUpdate(
        final AvailabilityCallback callback,
        Executor executor,
        final String id,
        final String physicalId,
        int status) {
      long ident;
      if (Integer.parseInt(id) >= 20 && callback != null && !callback.mIsHiddenIdPermittedPackage) {
        return;
      }
      Log.m98i(
          TAG,
          String.format(
              "postSingleUpdate device: camera id %s status %s", id, cameraStatusToString(status)));
      if (isAvailable(status)) {
        ident = Binder.clearCallingIdentity();
        try {
          executor.execute(
              new Runnable() { // from class:
                               // android.hardware.camera2.CameraManager.CameraManagerGlobal.6
                @Override // java.lang.Runnable
                public void run() {
                  String str = physicalId;
                  if (str == null) {
                    callback.onCameraAvailable(id);
                  } else {
                    callback.onPhysicalCameraAvailable(id, str);
                  }
                }
              });
        } finally {
        }
      } else {
        ident = Binder.clearCallingIdentity();
        try {
          executor.execute(
              new Runnable() { // from class:
                               // android.hardware.camera2.CameraManager.CameraManagerGlobal.7
                @Override // java.lang.Runnable
                public void run() {
                  String str = physicalId;
                  if (str == null) {
                    callback.onCameraUnavailable(id);
                  } else {
                    callback.onPhysicalCameraUnavailable(id, str);
                  }
                }
              });
        } finally {
        }
      }
    }

    private void postSingleTorchUpdate(
        final TorchCallback callback, Executor executor, final String id, final int status) {
      long ident;
      Log.m98i(
          TAG,
          String.format(
              "postSingleTorchUpdate device: camera id %s status %d", id, Integer.valueOf(status)));
      switch (status) {
        case 1:
        case 2:
          ident = Binder.clearCallingIdentity();
          try {
            executor.execute(
                new Runnable() { // from class:
                                 // android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda3
                  @Override // java.lang.Runnable
                  public final void run() {
                    CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$0(
                        CameraManager.TorchCallback.this, id, status);
                  }
                });
            return;
          } finally {
          }
        default:
          ident = Binder.clearCallingIdentity();
          try {
            executor.execute(
                new Runnable() { // from class:
                                 // android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda4
                  @Override // java.lang.Runnable
                  public final void run() {
                    CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$1(
                        CameraManager.TorchCallback.this, id);
                  }
                });
            return;
          } finally {
          }
      }
    }

    static /* synthetic */ void lambda$postSingleTorchUpdate$0(
        TorchCallback callback, String id, int status) {
      Log.m98i(TAG, "onTorchModeChanged");
      callback.onTorchModeChanged(id, status == 2);
    }

    static /* synthetic */ void lambda$postSingleTorchUpdate$1(TorchCallback callback, String id) {
      Log.m98i(TAG, "onTorchModeUnavailable");
      callback.onTorchModeUnavailable(id);
    }

    private void postSingleTorchStrengthLevelUpdate(
        final TorchCallback callback,
        Executor executor,
        final String id,
        final int newStrengthLevel) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda2
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
        String id = this.mDeviceStatus.keyAt(i);
        Integer status = this.mDeviceStatus.valueAt(i);
        postSingleUpdate(callback, executor, id, null, status.intValue());
        if (this.mHasOpenCloseListenerPermission) {
          postSemSingleUpdate(callback, executor, id, status.intValue());
        }
        if ((isAvailable(status.intValue())
                || CameraManager.physicalCallbacksAreEnabledForUnavailableCamera())
            && this.mUnavailablePhysicalDevices.containsKey(id)) {
          ArrayList<String> unavailableIds = this.mUnavailablePhysicalDevices.get(id);
          Iterator<String> it = unavailableIds.iterator();
          while (it.hasNext()) {
            String unavailableId = it.next();
            postSingleUpdate(callback, executor, id, unavailableId, 0);
          }
        }
      }
      if (this.mHasOpenCloseListenerPermission) {
        Log.m98i(TAG, "updateCallbackLocked: post device state update");
        for (int i2 = 0; i2 < this.mCameraDeviceStates.size(); i2++) {
          String id2 = this.mCameraDeviceStates.keyAt(i2);
          CameraDeviceState state = this.mCameraDeviceStates.valueAt(i2);
          postSemSingleCameraDeviceStateUpdate(callback, executor, id2, state);
        }
      }
      for (int i3 = 0; i3 < this.mOpenedDevices.size(); i3++) {
        String id3 = this.mOpenedDevices.keyAt(i3);
        String clientPackageId = this.mOpenedDevices.valueAt(i3);
        postSingleCameraOpenedUpdate(callback, executor, id3, clientPackageId);
      }
    }

    private void onStatusChangedLocked(int status, String id) {
      Integer oldStatus;
      if (!validStatus(status)) {
        Log.m96e(
            TAG,
            String.format("Ignoring invalid device %s status 0x%x", id, Integer.valueOf(status)));
        return;
      }
      if (status == 0) {
        Integer oldStatus2 = this.mDeviceStatus.remove(id);
        this.mUnavailablePhysicalDevices.remove(id);
        oldStatus = oldStatus2;
      } else {
        Integer oldStatus3 = this.mDeviceStatus.put(id, Integer.valueOf(status));
        if (oldStatus3 == null) {
          this.mUnavailablePhysicalDevices.put(id, new ArrayList<>());
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
        if (this.mHasOpenCloseListenerPermission) {
          postSemSingleUpdate(callback, executor, id, status);
        }
      }
      if (oldStatus != null && isAvailable(status) == isAvailable(oldStatus.intValue())) {
        return;
      }
      int callbackCount2 = this.mCallbackMap.size();
      for (int i2 = 0; i2 < callbackCount2; i2++) {
        Executor executor2 = this.mCallbackMap.valueAt(i2);
        AvailabilityCallback callback2 = this.mCallbackMap.keyAt(i2);
        postSingleUpdate(callback2, executor2, id, null, status);
        if (isAvailable(status) && this.mUnavailablePhysicalDevices.containsKey(id)) {
          ArrayList<String> unavailableIds = this.mUnavailablePhysicalDevices.get(id);
          Iterator<String> it = unavailableIds.iterator();
          while (it.hasNext()) {
            String unavailableId = it.next();
            postSingleUpdate(callback2, executor2, id, unavailableId, 0);
          }
        }
      }
    }

    private void onPhysicalCameraStatusChangedLocked(int status, String id, String physicalId) {
      if (!validStatus(status)) {
        Log.m96e(
            TAG,
            String.format(
                "Ignoring invalid device %s physical device %s status 0x%x",
                id, physicalId, Integer.valueOf(status)));
        return;
      }
      if (!this.mDeviceStatus.containsKey(id)
          || !this.mUnavailablePhysicalDevices.containsKey(id)) {
        Log.m96e(
            TAG,
            String.format("Camera %s is not present. Ignore physical camera status change", id));
        return;
      }
      ArrayList<String> unavailablePhysicalDevices = this.mUnavailablePhysicalDevices.get(id);
      if (!isAvailable(status) && !unavailablePhysicalDevices.contains(physicalId)) {
        unavailablePhysicalDevices.add(physicalId);
      } else if (isAvailable(status) && unavailablePhysicalDevices.contains(physicalId)) {
        unavailablePhysicalDevices.remove(physicalId);
      } else {
        return;
      }
      if (!CameraManager.physicalCallbacksAreEnabledForUnavailableCamera()
          && !isAvailable(this.mDeviceStatus.get(id).intValue())) {
        Log.m98i(
            TAG,
            String.format(
                "Camera %s is not available. Ignore physical camera status change callback(s)",
                id));
        return;
      }
      int callbackCount = this.mCallbackMap.size();
      for (int i = 0; i < callbackCount; i++) {
        Executor executor = this.mCallbackMap.valueAt(i);
        AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
        postSingleUpdate(callback, executor, id, physicalId, status);
      }
    }

    private void updateTorchCallbackLocked(TorchCallback callback, Executor executor) {
      for (int i = 0; i < this.mTorchStatus.size(); i++) {
        String id = this.mTorchStatus.keyAt(i);
        Integer status = this.mTorchStatus.valueAt(i);
        postSingleTorchUpdate(callback, executor, id, status.intValue());
      }
    }

    private void onTorchStatusChangedLocked(int status, String id) {
      if (!validTorchStatus(status)) {
        Log.m96e(
            TAG,
            String.format(
                "Ignoring invalid device %s torch status 0x%x", id, Integer.valueOf(status)));
        return;
      }
      Integer oldStatus = this.mTorchStatus.put(id, Integer.valueOf(status));
      if (oldStatus != null && oldStatus.intValue() == status) {
        return;
      }
      int callbackCount = this.mTorchCallbackMap.size();
      for (int i = 0; i < callbackCount; i++) {
        Executor executor = this.mTorchCallbackMap.valueAt(i);
        TorchCallback callback = this.mTorchCallbackMap.keyAt(i);
        postSingleTorchUpdate(callback, executor, id, status);
      }
    }

    private void onTorchStrengthLevelChangedLocked(String cameraId, int newStrengthLevel) {
      int callbackCount = this.mTorchCallbackMap.size();
      for (int i = 0; i < callbackCount; i++) {
        Executor executor = this.mTorchCallbackMap.valueAt(i);
        TorchCallback callback = this.mTorchCallbackMap.keyAt(i);
        postSingleTorchStrengthLevelUpdate(callback, executor, cameraId, newStrengthLevel);
      }
    }

    public void registerAvailabilityCallback(
        AvailabilityCallback callback, Executor executor, boolean hasOpenCloseListenerPermission) {
      synchronized (this.mLock) {
        this.mHasOpenCloseListenerPermission = hasOpenCloseListenerPermission;
        connectCameraServiceLocked();
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

    public void registerTorchCallback(TorchCallback callback, Executor executor) {
      synchronized (this.mLock) {
        connectCameraServiceLocked();
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
    public void onStatusChanged(int status, String cameraId) throws RemoteException {
      synchronized (this.mLock) {
        onStatusChangedLocked(status, cameraId);
      }
    }

    @Override // android.hardware.ICameraServiceListener
    public void onPhysicalCameraStatusChanged(int status, String cameraId, String physicalCameraId)
        throws RemoteException {
      synchronized (this.mLock) {
        onPhysicalCameraStatusChangedLocked(status, cameraId, physicalCameraId);
      }
    }

    @Override // android.hardware.ICameraServiceListener
    public void onTorchStatusChanged(int status, String cameraId) throws RemoteException {
      synchronized (this.mLock) {
        onTorchStatusChangedLocked(status, cameraId);
      }
    }

    @Override // android.hardware.ICameraServiceListener
    public void onTorchStrengthLevelChanged(String cameraId, int newStrengthLevel)
        throws RemoteException {
      synchronized (this.mLock) {
        onTorchStrengthLevelChangedLocked(cameraId, newStrengthLevel);
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
    public void onCameraOpened(String cameraId, String clientPackageId) {
      synchronized (this.mLock) {
        onCameraOpenedLocked(cameraId, clientPackageId);
      }
    }

    private void onCameraOpenedLocked(String cameraId, String clientPackageId) {
      String oldApk = this.mOpenedDevices.put(cameraId, clientPackageId);
      if (oldApk != null) {
        if (oldApk.equals(clientPackageId)) {
          Log.m102w(
              TAG,
              "onCameraOpened was previously called for "
                  + oldApk
                  + " and is now again called for the same package name, so no new client visible"
                  + " update will be sent");
          return;
        }
        Log.m102w(
            TAG,
            "onCameraOpened was previously called for "
                + oldApk
                + " and is now called for "
                + clientPackageId
                + " without onCameraClosed being called first");
      }
      int callbackCount = this.mCallbackMap.size();
      for (int i = 0; i < callbackCount; i++) {
        Executor executor = this.mCallbackMap.valueAt(i);
        AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
        postSingleCameraOpenedUpdate(callback, executor, cameraId, clientPackageId);
      }
    }

    @Override // android.hardware.ICameraServiceListener
    public void onCameraClosed(String cameraId) {
      synchronized (this.mLock) {
        onCameraClosedLocked(cameraId);
      }
    }

    private void onCameraClosedLocked(String cameraId) {
      this.mOpenedDevices.remove(cameraId);
      int callbackCount = this.mCallbackMap.size();
      for (int i = 0; i < callbackCount; i++) {
        Executor executor = this.mCallbackMap.valueAt(i);
        AvailabilityCallback callback = this.mCallbackMap.keyAt(i);
        postSingleCameraClosedUpdate(callback, executor, cameraId);
      }
    }

    @Override // android.hardware.ICameraServiceListener
    public void onCameraDeviceStateChanged(
        String cameraId,
        int facing,
        int newCameraState,
        String clientName,
        int apiLevel,
        int userId) {
      synchronized (this.mLock) {
        CameraDeviceState state = new CameraDeviceState(facing, newCameraState, clientName, userId);
        Log.m98i(
            TAG,
            "Camera "
                + cameraId
                + " "
                + state.toString()
                + " API Level "
                + apiLevel
                + " User Id "
                + userId);
        onCameraDeviceStateChangedLocked(state, cameraId);
      }
    }

    private void onCameraDeviceStateChangedLocked(CameraDeviceState state, String id) {
      CameraDeviceState oldState = this.mCameraDeviceStates.put(id, state);
      if (oldState != null && oldState.equals(state)) {
        Log.m98i(
            TAG,
            String.format(
                "CameraDevice %s state changed to (%s), which is what it already was, skip"
                    + " callback",
                id, state.toString()));
        return;
      }
      int semCallbackCount = this.mSemCameraDeviceStateCallbackMap.size();
      for (int i = 0; i < semCallbackCount; i++) {
        Executor executor = this.mSemCameraDeviceStateCallbackMap.valueAt(i);
        SemCameraDeviceStateCallback callback = this.mSemCameraDeviceStateCallbackMap.keyAt(i);
        postSingleCameraDeviceStateUpdate(callback, executor, id, state);
      }
      int callbackCount = this.mCallbackMap.size();
      for (int i2 = 0; i2 < callbackCount; i2++) {
        Executor executor2 = this.mCallbackMap.valueAt(i2);
        AvailabilityCallback callback2 = this.mCallbackMap.keyAt(i2);
        if (this.mHasOpenCloseListenerPermission) {
          Log.m98i(TAG, "onCameraDeviceStateChangedLocked: post device state update");
          postSemSingleCameraDeviceStateUpdate(callback2, executor2, id, state);
        }
      }
    }

    public void registerSemCameraDeviceStateCallback(
        SemCameraDeviceStateCallback callback, Executor executor) {
      synchronized (this.mLock) {
        Log.m98i(TAG, "registerSemCameraDeviceStateCallback");
        connectCameraServiceLocked();
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
        Log.m98i(TAG, "unregisterSemCameraDeviceStateCallback");
        this.mSemCameraDeviceStateCallbackMap.remove(callback);
      }
    }

    private void updateSemCameraDeviceStateCallbackLocked(
        SemCameraDeviceStateCallback callback, Executor executor) {
      for (int i = 0; i < this.mCameraDeviceStates.size(); i++) {
        String id = this.mCameraDeviceStates.keyAt(i);
        CameraDeviceState state = this.mCameraDeviceStates.valueAt(i);
        postSingleCameraDeviceStateUpdate(callback, executor, id, state);
      }
    }

    private void postSingleCameraDeviceStateUpdate(
        final SemCameraDeviceStateCallback callback,
        Executor executor,
        final String cameraId,
        final CameraDeviceState state) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager.CameraManagerGlobal.8
              @Override // java.lang.Runnable
              public void run() {
                if (!callback.isExtended && state.mDeviceState >= 100) {
                  return;
                }
                callback.onCameraDeviceStateChanged(
                    cameraId, state.mFacing, state.mDeviceState, state.mClientName);
                callback.onCameraDeviceStateChanged(
                    cameraId, state.mFacing, state.mDeviceState, state.mClientName, state.mUserId);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void postSemSingleUpdate(
        final AvailabilityCallback callback, Executor executor, final String id, final int status) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                CameraManager.AvailabilityCallback.this.onSemCameraDeviceRawStatus(id, status);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void postSemSingleCameraDeviceStateUpdate(
        final AvailabilityCallback callback,
        Executor executor,
        final String cameraId,
        final CameraDeviceState state) {
      long ident = Binder.clearCallingIdentity();
      try {
        executor.execute(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager.CameraManagerGlobal.9
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
                    Log.m102w(CameraManagerGlobal.TAG, "Unknown lens facing.");
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
                    Log.m102w(CameraManagerGlobal.TAG, "Unknown device state");
                    break;
                }
              }
            });
      } finally {
        Binder.restoreCallingIdentity(ident);
      }
    }

    private void scheduleCameraServiceReconnectionLocked() {
      if (this.mCallbackMap.isEmpty()
          && this.mTorchCallbackMap.isEmpty()
          && this.mSemCameraDeviceStateCallbackMap.isEmpty()) {
        return;
      }
      try {
        this.mScheduler.schedule(
            new Runnable() { // from class:
                             // android.hardware.camera2.CameraManager$CameraManagerGlobal$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                CameraManager.CameraManagerGlobal.this
                    .lambda$scheduleCameraServiceReconnectionLocked$4();
              }
            },
            1000L,
            TimeUnit.MILLISECONDS);
      } catch (RejectedExecutionException e) {
        Log.m96e(TAG, "Failed to schedule camera service re-connect: " + e);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleCameraServiceReconnectionLocked$4() {
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
              callback.mIsHiddenIdPermittedPackage =
                  cameraService.isHiddenIdPermittedPackage(callback.mPackageName);
              callback.mIsRegisteredWhileServiceDown = false;
            }
          }
        } catch (RemoteException e) {
          Log.m97e(TAG, "Camera service is currently unavailable", e);
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
          String cameraId = this.mDeviceStatus.keyAt(i);
          onStatusChangedLocked(0, cameraId);
          if (this.mHasOpenCloseListenerPermission) {
            onCameraClosedLocked(cameraId);
          }
        }
        for (int i2 = 0; i2 < this.mTorchStatus.size(); i2++) {
          String cameraId2 = this.mTorchStatus.keyAt(i2);
          onTorchStatusChangedLocked(0, cameraId2);
        }
        for (int i3 = 0; i3 < this.mCameraDeviceStates.size(); i3++) {
          String cameraId3 = this.mCameraDeviceStates.keyAt(i3);
          CameraDeviceState state = this.mCameraDeviceStates.valueAt(i3);
          onCameraDeviceStateChangedLocked(
              new CameraDeviceState(state.mFacing, 3, "android.system", 0), cameraId3);
        }
        this.mConcurrentCameraIdCombinations.clear();
        scheduleCameraServiceReconnectionLocked();
      }
    }

    private static String cameraStatusToString(int cameraStatus) {
      switch (cameraStatus) {
      }
      return "STATUS_UNKNOWN";
    }
  }

  public void startDeviceInjector(
      String[] targetPackages,
      String[] targetCameraIds,
      String sourceCameraId,
      Executor executor,
      DeviceInjectorSession.StatusCallback callback)
      throws CameraAccessException, SecurityException, IllegalArgumentException {
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
        try {
          DeviceInjectorSessionImpl injectorSessionImpl =
              new DeviceInjectorSessionImpl(callback, executor);
          IDeviceInjectorCallback deviceInjectorCallback = injectorSessionImpl.getCallback();
          cameraService.startDeviceInjector(
              targetPackages, targetCameraIds, sourceCameraId, deviceInjectorCallback);
        } catch (RemoteException e) {
          ServiceSpecificException sse =
              new ServiceSpecificException(4, "Camera service is currently unavailable");
          throwAsPublicException(sse);
        }
      } catch (ServiceSpecificException e2) {
        throwAsPublicException(e2);
      }
    }
  }

  public void startRemoteDeviceInjector(
      String[] targetPackages,
      String[] targetCameraIds,
      DeviceInjectorSession.RemoteDevice sourceDevice,
      Executor executor,
      DeviceInjectorSession.StatusCallback callback)
      throws CameraAccessException, SecurityException, IllegalArgumentException {
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
        try {
          DeviceInjectorSessionImpl injectorSessionImpl =
              new DeviceInjectorSessionImpl(callback, executor);
          IDeviceInjectorCallback deviceInjectorCallback = injectorSessionImpl.getCallback();
          IRemoteDevice deviceInjectorDevice = injectorSessionImpl.getRemoteDevice(sourceDevice);
          cameraService.startRemoteDeviceInjector(
              targetPackages, targetCameraIds, deviceInjectorDevice, deviceInjectorCallback);
        } catch (RemoteException e) {
          ServiceSpecificException sse =
              new ServiceSpecificException(4, "Camera service is currently unavailable");
          throwAsPublicException(sse);
        }
      } catch (ServiceSpecificException e2) {
        throwAsPublicException(e2);
      }
    }
  }
}
