package com.android.server.display;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.compat.CompatChanges;
import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.hardware.OverlayProperties;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateManagerInternal;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.Curve;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.DisplayViewport;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.hardware.display.HdrConversionMode;
import android.hardware.display.IDisplayManager;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.VirtualDisplayConfig;
import android.hardware.display.WifiDisplayStatus;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.hardware.input.HostUsiVersion;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.PerfLog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Spline;
import android.view.ContentRecordingSession;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayEventReceiver;
import android.view.DisplayInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import android.widget.Toast;
import android.window.DisplayWindowPolicyController;
import android.window.ScreenCapture;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AnimationThread;
import com.android.server.DisplayThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.utils.SensorUtils;
import com.android.server.input.InputManagerInternal;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.android.server.wm.SurfaceAnimationThread;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class DisplayManagerService extends SystemService {
  public static final int[] EMPTY_ARRAY = new int[0];
  public static final HdrConversionMode HDR_CONVERSION_MODE_UNSUPPORTED = new HdrConversionMode(0);
  public SimpleDateFormat dateFormat;
  public ActivityManager mActivityManager;
  public ActivityManagerInternal mActivityManagerInternal;
  public boolean mAreUserDisabledHdrTypesAllowed;
  public boolean mBootCompleted;
  public IRefreshRateToken mBrightnessAnimRefreshRateToken;
  public boolean mBrightnessAnimStarted;
  public final BrightnessSynchronizer mBrightnessSynchronizer;
  public BrightnessTracker mBrightnessTracker;
  public final SparseArray mCallbacks;
  public int mConnectedExternalDisplayId;
  public final Context mContext;
  public int mCurrentUserId;
  public final int mDefaultDisplayDefaultColorMode;
  public int mDefaultDisplayTopInset;
  public LogicalDisplay mDesktopPrimaryDisplay;
  public LogicalDisplay mDesktopSecondaryDisplay;
  public DeviceStateManagerInternal mDeviceStateManager;
  public final DexEmulator mDexEmulator;
  public final SparseArray mDisplayAccessUIDs;
  public final ArrayList mDisplayAdapters;
  public final DisplayBlanker mDisplayBlanker;
  public final ArrayList mDisplayBrightnessListeners;
  public final SparseArray mDisplayBrightnesses;
  public final DisplayDeviceRepository mDisplayDeviceRepo;
  public final CopyOnWriteArrayList mDisplayGroupListeners;
  public final DisplayModeDirector mDisplayModeDirector;
  public DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
  public final SparseArray mDisplayPowerControllers;
  public final SparseArray mDisplayPowerControllers1;
  public final ArrayList mDisplayStateLimitLocks;
  public final SparseIntArray mDisplayStateLimits;
  public final ArrayList mDisplayStateListeners;
  public final SparseIntArray mDisplayStates;
  public final CopyOnWriteArrayList mDisplayTransactionListeners;
  public final SparseArray mDisplayWindowPolicyControllers;
  public int mDualScreenPolicy;
  public LogicalDisplay mEnabledDexDisplay;
  public int mForceListenProcessId;
  public final DisplayManagerHandler mHandler;
  public HdrConversionMode mHdrConversionMode;
  public final HighBrightnessModeMetadataMapper mHighBrightnessModeMetadataMapper;
  public final BroadcastReceiver mIdleModeReceiver;
  public final Injector mInjector;
  public InputManagerInternal mInputManagerInternal;
  public boolean mIsDocked;
  public boolean mIsDreaming;
  public volatile boolean mIsHdrOutputControlEnabled;
  public final LogicalDisplayMapper mLogicalDisplayMapper;
  public boolean mMinimalPostProcessingAllowed;
  public final Curve mMinimumBrightnessCurve;
  public final Spline mMinimumBrightnessSpline;
  public boolean mNeedSkipDozeState;
  public final NewDexEmulator mNewDexEmulator;
  public final OverlayProperties mOverlayProperties;
  public HdrConversionMode mOverrideHdrConversionMode;
  public final SparseArray mPendingCallbackSelfLocked;
  public boolean mPendingTraversal;
  public final PersistentDataStore mPersistentDataStore;
  public Handler mPowerHandler;
  public HashSet mPresentationDisplays;
  public IMediaProjectionManager mProjectionService;
  public final Object mRequestDisplayStateLock;
  public int mRequestedStateLimitForEarlyWakeUp;
  public boolean mSafeMode;
  public final float mScreenExtendedBrightnessRangeMaximum;
  public SensorManager mSensorManager;
  public SettingsObserver mSettingsObserver;
  public Point mStableDisplaySize;
  public int[] mSupportedHdrOutputType;
  public final SyncRoot mSyncRoot;
  public int mSystemPreferredHdrOutputType;
  public boolean mSystemReady;
  public final ArrayList mTempCallbacks;
  public final ArrayList mTempViewports;
  public final Handler mUiHandler;
  public UidImportanceListener mUidImportanceListener;
  public int[] mUserDisabledHdrTypes;
  public Display.Mode mUserPreferredMode;
  public final ArrayList mViewports;
  public VirtualDisplayAdapter mVirtualDisplayAdapter;
  public VolumeController mVolumeController;
  public final ColorSpace mWideColorSpace;
  public WifiDisplayAdapter mWifiDisplayAdapter;
  public int mWifiDisplayScanRequestCount;
  public final BroadcastReceiver mWifiReceiver;
  public WindowManagerInternal mWindowManagerInternal;

  public interface Clock {
    long uptimeMillis();
  }

  public final class SyncRoot {}

  enum DisplayStatePriority {
    ON(2),
    ON_SUSPEND(6),
    VR(5),
    DOZE(3),
    DOZE_SUSPEND(4),
    OFF(1);

    private final int displayState;

    DisplayStatePriority(int i) {
      this.displayState = i;
    }

    public int getDisplayState() {
      return this.displayState;
    }
  }

  public DisplayManagerService(Context context) {
    this(context, new Injector());
  }

  /* JADX WARN: Multi-variable type inference failed */
  public DisplayManagerService(Context context, Injector injector) {
    super(context);
    this.mUidImportanceListener = new UidImportanceListener();
    this.mUserDisabledHdrTypes = new int[0];
    this.mAreUserDisabledHdrTypesAllowed = true;
    this.mHdrConversionMode = null;
    this.mOverrideHdrConversionMode = null;
    this.mSystemPreferredHdrOutputType = -1;
    SyncRoot syncRoot = new SyncRoot();
    this.mSyncRoot = syncRoot;
    this.mCallbacks = new SparseArray();
    this.mDisplayWindowPolicyControllers = new SparseArray();
    this.mHighBrightnessModeMetadataMapper = new HighBrightnessModeMetadataMapper();
    this.mDisplayAdapters = new ArrayList();
    this.mDisplayTransactionListeners = new CopyOnWriteArrayList();
    this.mDisplayGroupListeners = new CopyOnWriteArrayList();
    this.mDisplayPowerControllers = new SparseArray();
    this.mDisplayPowerControllers1 = new SparseArray();
    this.mRequestDisplayStateLock = new Object();
    this.mDisplayBlanker =
        new DisplayBlanker() { // from class: com.android.server.display.DisplayManagerService.1
          @Override // com.android.server.display.DisplayBlanker
          public synchronized void requestDisplayState(int i, int i2, float f, float f2) {
            boolean z;
            boolean z2;
            boolean z3;
            synchronized (DisplayManagerService.this.mRequestDisplayStateLock) {
              synchronized (DisplayManagerService.this.mSyncRoot) {
                int indexOfKey = DisplayManagerService.this.mDisplayStates.indexOfKey(i);
                z = false;
                if (indexOfKey > -1) {
                  boolean z4 = i2 != DisplayManagerService.this.mDisplayStates.valueAt(indexOfKey);
                  if (z4) {
                    int size = DisplayManagerService.this.mDisplayStates.size();
                    int i3 = 0;
                    z2 = true;
                    z3 = true;
                    while (i3 < size) {
                      int valueAt =
                          i3 == indexOfKey
                              ? i2
                              : DisplayManagerService.this.mDisplayStates.valueAt(i3);
                      if (valueAt != 1) {
                        z2 = false;
                      }
                      if (Display.isActiveState(valueAt)) {
                        z3 = false;
                      }
                      if (!z2 && !z3) {
                        break;
                      } else {
                        i3++;
                      }
                    }
                  } else {
                    z2 = true;
                    z3 = true;
                  }
                  z = z4;
                } else {
                  z2 = true;
                  z3 = true;
                }
              }
              if (i2 == 1) {
                DisplayManagerService.this.requestDisplayStateInternal(i, i2, f, f2);
              }
              if (z) {
                DisplayManagerService.this.mDisplayPowerCallbacks.onDisplayStateChange(z3, z2);
              }
              if (i == 0 && z) {
                DisplayManagerService.this.mDisplayPowerCallbacks.onDefaultDisplayStateChange(i2);
              }
              if (i2 != 1) {
                DisplayManagerService.this.requestDisplayStateInternal(i, i2, f, f2);
              }
              if (i == 0 || (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY && i == 1)) {
                ((DisplayPowerControllerInterface)
                        DisplayManagerService.this.mDisplayPowerControllers.get(i))
                    .setActualDisplayState(i2);
              }
            }
          }

          @Override // com.android.server.display.DisplayBlanker
          public synchronized void setDisplayStateLimitForEarlyWakeUp(int i, int i2) {
            synchronized (DisplayManagerService.this.mRequestDisplayStateLock) {
              synchronized (DisplayManagerService.this.mSyncRoot) {
                int indexOfKey = DisplayManagerService.this.mDisplayStateLimits.indexOfKey(i);
                if (DisplayManagerService.this.mDisplayStateLimits.valueAt(indexOfKey) != i2) {
                  Slog.m72d(
                      "DisplayManagerService",
                      "setDisplayStateLimitForEarlyWakeUp : stateLimit="
                          + Display.stateToString(i2)
                          + " displayId="
                          + i);
                  DisplayManagerService.this.mDisplayStateLimits.setValueAt(indexOfKey, i2);
                  DisplayManagerService displayManagerService = DisplayManagerService.this;
                  Runnable updateDisplayStateLocked =
                      displayManagerService.updateDisplayStateLocked(
                          displayManagerService
                              .mLogicalDisplayMapper
                              .getDisplayLocked(i)
                              .getPrimaryDisplayDeviceLocked());
                  if (updateDisplayStateLocked != null) {
                    updateDisplayStateLocked.run();
                  }
                  return;
                }
                Slog.m72d(
                    "DisplayManagerService",
                    "setDisplayStateLimitForEarlyWakeUp: sameRequest " + Display.stateToString(i2));
              }
            }
          }
        };
    this.mDisplayStates = new SparseIntArray();
    this.mDisplayStateLimits = new SparseIntArray();
    this.mDisplayBrightnesses = new SparseArray();
    this.mStableDisplaySize = new Point();
    this.mViewports = new ArrayList();
    PersistentDataStore persistentDataStore = new PersistentDataStore();
    this.mPersistentDataStore = persistentDataStore;
    this.mTempCallbacks = new ArrayList();
    this.mPendingCallbackSelfLocked = new SparseArray();
    this.mTempViewports = new ArrayList();
    this.mDisplayAccessUIDs = new SparseArray();
    this.mPresentationDisplays = new HashSet();
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss.SSS");
    this.mBootCompleted = false;
    this.mIdleModeReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.DisplayManagerService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
              int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
              DisplayManagerService.this.mIsDocked =
                  intExtra == 1 || intExtra == 3 || intExtra == 4;
            }
            if ("android.intent.action.DREAMING_STARTED".equals(intent.getAction())) {
              DisplayManagerService.this.mIsDreaming = true;
            } else if ("android.intent.action.DREAMING_STOPPED".equals(intent.getAction())) {
              DisplayManagerService.this.mIsDreaming = false;
            }
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            displayManagerService.setDockedAndIdleEnabled(
                displayManagerService.mIsDocked && DisplayManagerService.this.mIsDreaming, 0);
          }
        };
    this.mDisplayStateLimitLocks = new ArrayList();
    this.mRequestedStateLimitForEarlyWakeUp = 0;
    this.mDualScreenPolicy = -1;
    this.mDisplayBrightnessListeners = new ArrayList();
    this.mDisplayStateListeners = new ArrayList();
    this.mBrightnessAnimRefreshRateToken = null;
    this.mConnectedExternalDisplayId = -1;
    this.mDexEmulator = new DexEmulator();
    this.mNewDexEmulator = new NewDexEmulator();
    this.mWifiReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.DisplayManagerService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String str;
            if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
              NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
              NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
              if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                Slog.m72d("DisplayManagerService", "network connected");
                str = "com.samsung.intent.action.NETWORK_CONNECTED_STATE";
              } else if (detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
                Slog.m72d("DisplayManagerService", "network disconnected");
                str = "com.samsung.intent.action.NETWORK_DISCONNECTED_STATE";
              } else {
                str = null;
              }
              if (str == null) {
                return;
              }
              Intent intent2 = new Intent(str);
              intent2.setComponent(
                  new ComponentName(
                      "com.sec.android.CcInfo", "com.sec.android.CcInfo.WifiConnectionReceiver"));
              intent2.putExtra("networkInfo", networkInfo);
              DisplayManagerService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
            }
          }
        };
    this.mForceListenProcessId = -1;
    long uptimeMillis = SystemClock.uptimeMillis();
    this.mInjector = injector;
    this.mContext = context;
    DisplayManagerHandler displayManagerHandler =
        new DisplayManagerHandler(DisplayThread.get().getLooper());
    this.mHandler = displayManagerHandler;
    this.mUiHandler = UiThread.getHandler();
    DisplayDeviceRepository displayDeviceRepository =
        new DisplayDeviceRepository(syncRoot, persistentDataStore);
    this.mDisplayDeviceRepo = displayDeviceRepository;
    this.mLogicalDisplayMapper =
        new LogicalDisplayMapper(
            context,
            displayDeviceRepository,
            new LogicalDisplayListener(),
            syncRoot,
            displayManagerHandler);
    this.mDisplayModeDirector = new DisplayModeDirector(context, displayManagerHandler);
    this.mBrightnessSynchronizer = new BrightnessSynchronizer(context);
    Resources resources = context.getResources();
    this.mDefaultDisplayDefaultColorMode =
        context.getResources().getInteger(R.integer.config_delay_for_ims_dereg_millis);
    this.mDefaultDisplayTopInset = SystemProperties.getInt("persist.sys.displayinset.top", -1);
    float[] floatArray = getFloatArray(resources.obtainTypedArray(R.array.wfcSpnFormats));
    float[] floatArray2 = getFloatArray(resources.obtainTypedArray(R.array.wifi_known_signers));
    this.mMinimumBrightnessCurve = new Curve(floatArray, floatArray2);
    this.mMinimumBrightnessSpline = Spline.createSpline(floatArray, floatArray2);
    this.mCurrentUserId = 0;
    this.mWideColorSpace = SurfaceControl.getCompositionColorSpaces()[1];
    this.mOverlayProperties = SurfaceControl.getOverlaySupport();
    this.mScreenExtendedBrightnessRangeMaximum =
        Math.max(
                context.getResources().getInteger(R.integer.config_screen_rotation_fade_in_delay),
                context.getResources().getInteger(R.integer.config_screenTimeoutOverride))
            / 255.0f;
    displayDeviceRepository.mHDMIWakeLock =
        ((PowerManager) context.getSystemService(PowerManager.class))
            .newWakeLock(805306374, "DisplayDeviceRepository.mHDMIWakeLock");
    this.mSystemReady = false;
    Slog.m72d(
        "DisplayManagerService",
        "Create DisplayManagerService took to complete: "
            + (SystemClock.uptimeMillis() - uptimeMillis)
            + "ms");
  }

  public void setupSchedulerPolicies() {
    Process.setThreadGroupAndCpuset(
        DisplayThread.get().getThreadId(), CoreRune.SYSPERF_BOOST_OPT ? 10 : 5);
    Process.setThreadGroupAndCpuset(
        AnimationThread.get().getThreadId(), CoreRune.SYSPERF_BOOST_OPT ? 10 : 5);
    Process.setThreadGroupAndCpuset(
        SurfaceAnimationThread.get().getThreadId(), CoreRune.SYSPERF_BOOST_OPT ? 10 : 5);
    if (CoreRune.SYSPERF_VI_BOOST) {
      new Handler()
          .postDelayed(
              new Runnable() { // from class: com.android.server.display.DisplayManagerService.4
                @Override // java.lang.Runnable
                public void run() {
                  ICustomFrequencyManager asInterface;
                  IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                  if (service == null
                      || (asInterface = ICustomFrequencyManager.Stub.asInterface(service))
                          == null) {
                    return;
                  }
                  try {
                    asInterface.sendTid(Process.myPid(), AnimationThread.get().getThreadId(), 4);
                  } catch (RemoteException e) {
                    e.printStackTrace();
                  }
                }
              },
              10000L);
    }
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    Slog.m72d("DisplayManagerService", "DisplayManagerService onStart");
    synchronized (this.mSyncRoot) {
      this.mPersistentDataStore.loadIfNeeded();
      loadStableDisplayValuesLocked();
    }
    this.mHandler.sendEmptyMessage(1);
    DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    publishBinderService("display", new BinderService(), true);
    publishLocalService(DisplayManagerInternal.class, new LocalService());
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i != 100) {
      if (i == 1000) {
        synchronized (this.mSyncRoot) {
          this.mBootCompleted = true;
          for (int i2 = 0; i2 < this.mDisplayPowerControllers.size(); i2++) {
            ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.valueAt(i2))
                .onBootCompleted();
          }
        }
        this.mDisplayModeDirector.onBootCompleted();
        this.mLogicalDisplayMapper.onBootCompleted();
        return;
      }
      return;
    }
    synchronized (this.mSyncRoot) {
      long uptimeMillis =
          SystemClock.uptimeMillis() + this.mInjector.getDefaultDisplayDelayTimeout();
      while (true) {
        if (this.mLogicalDisplayMapper.getDisplayLocked(0) != null
            && this.mVirtualDisplayAdapter != null) {}
        long uptimeMillis2 = uptimeMillis - SystemClock.uptimeMillis();
        if (uptimeMillis2 <= 0) {
          throw new RuntimeException(
              "Timeout waiting for default display to be initialized. DefaultDisplay="
                  + this.mLogicalDisplayMapper.getDisplayLocked(0)
                  + ", mVirtualDisplayAdapter="
                  + this.mVirtualDisplayAdapter);
        }
        try {
          this.mSyncRoot.wait(uptimeMillis2);
        } catch (InterruptedException unused) {
        }
      }
    }
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    final int userIdentifier = targetUser2.getUserIdentifier();
    final int userSerialNumber = getUserManager().getUserSerialNumber(userIdentifier);
    synchronized (this.mSyncRoot) {
      final boolean z = this.mCurrentUserId != userIdentifier;
      if (z) {
        this.mCurrentUserId = userIdentifier;
      }
      this.mLogicalDisplayMapper.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.this.lambda$onUserSwitching$0(
                  z, userSerialNumber, userIdentifier, (LogicalDisplay) obj);
            }
          });
      handleSettingsChange();
      if (CoreRune.FW_VRR_POLICY) {
        this.mDisplayModeDirector.onUserSwitching();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onUserSwitching$0(
      boolean z, int i, int i2, LogicalDisplay logicalDisplay) {
    DisplayPowerControllerInterface displayPowerControllerInterface;
    if (logicalDisplay.getDisplayInfoLocked().type == 1
        && (displayPowerControllerInterface =
                (DisplayPowerControllerInterface)
                    this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked()))
            != null) {
      if (z && !PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
        displayPowerControllerInterface.setBrightnessConfiguration(
            getBrightnessConfigForDisplayWithPdsFallbackLocked(
                logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), i),
            true);
      }
      displayPowerControllerInterface.onSwitchUser(i2);
    }
  }

  @Override // com.android.server.SystemService
  public void onUserUnlocking(SystemService.TargetUser targetUser) {
    this.mHandler.sendEmptyMessage(28);
  }

  public void windowManagerAndInputReady() {
    synchronized (this.mSyncRoot) {
      this.mWindowManagerInternal =
          (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
      this.mInputManagerInternal =
          (InputManagerInternal) LocalServices.getService(InputManagerInternal.class);
      this.mActivityManagerInternal =
          (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
      ActivityManager activityManager =
          (ActivityManager) this.mContext.getSystemService(ActivityManager.class);
      this.mActivityManager = activityManager;
      activityManager.addOnUidImportanceListener(this.mUidImportanceListener, 400);
      this.mDeviceStateManager =
          (DeviceStateManagerInternal) LocalServices.getService(DeviceStateManagerInternal.class);
      ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class))
          .registerCallback(new HandlerExecutor(this.mHandler), new DeviceStateListener());
      scheduleTraversalLocked(false);
    }
  }

  public void systemReady(boolean z) {
    synchronized (this.mSyncRoot) {
      this.mSafeMode = z;
      this.mSystemReady = true;
      this.mIsHdrOutputControlEnabled = isDeviceConfigHdrOutputControlEnabled();
      DeviceConfig.addOnPropertiesChangedListener(
          "display_manager",
          BackgroundThread.getExecutor(),
          new DeviceConfig
              .OnPropertiesChangedListener() { // from class:
                                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
              DisplayManagerService.this.lambda$systemReady$1(properties);
            }
          });
      recordTopInsetLocked(this.mLogicalDisplayMapper.getDisplayLocked(0));
      updateSettingsLocked();
      updateUserDisabledHdrTypesFromSettingsLocked();
      updateUserPreferredDisplayModeSettingsLocked();
      if (this.mIsHdrOutputControlEnabled) {
        updateHdrConversionModeSettingsLocked();
      }
      if (CoreRune.MD_DEX_EMULATOR) {
        this.mDexEmulator.onSystemReadyLocked();
      }
      if (CoreRune.MT_NEW_DEX_EMULATOR) {
        this.mNewDexEmulator.onSystemReadyLocked();
      }
    }
    this.mDisplayModeDirector.setDesiredDisplayModeSpecsListener(
        new DesiredDisplayModeSpecsObserver());
    this.mDisplayModeDirector.start(this.mSensorManager);
    this.mHandler.sendEmptyMessage(2);
    this.mSettingsObserver = new SettingsObserver();
    this.mBrightnessSynchronizer.startSynchronizing();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.DREAMING_STARTED");
    intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
    intentFilter.addAction("android.intent.action.DOCK_EVENT");
    this.mContext.registerReceiver(this.mIdleModeReceiver, intentFilter);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
    this.mContext.registerReceiver(this.mWifiReceiver, intentFilter2, null, this.mHandler);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$1(DeviceConfig.Properties properties) {
    this.mIsHdrOutputControlEnabled = isDeviceConfigHdrOutputControlEnabled();
  }

  public final boolean isDeviceConfigHdrOutputControlEnabled() {
    return DeviceConfig.getBoolean("display_manager", "enable_hdr_output_control", true);
  }

  public Handler getDisplayHandler() {
    return this.mHandler;
  }

  public DisplayDeviceRepository getDisplayDeviceRepository() {
    return this.mDisplayDeviceRepo;
  }

  public boolean isMinimalPostProcessingAllowed() {
    boolean z;
    synchronized (this.mSyncRoot) {
      z = this.mMinimalPostProcessingAllowed;
    }
    return z;
  }

  public void setMinimalPostProcessingAllowed(boolean z) {
    synchronized (this.mSyncRoot) {
      this.mMinimalPostProcessingAllowed = z;
    }
  }

  public final void loadStableDisplayValuesLocked() {
    int i;
    Point stableDisplaySize = this.mPersistentDataStore.getStableDisplaySize();
    int i2 = stableDisplaySize.x;
    if (i2 > 0 && (i = stableDisplaySize.y) > 0) {
      this.mStableDisplaySize.set(i2, i);
      return;
    }
    Resources resources = this.mContext.getResources();
    int integer = resources.getInteger(R.integer.config_tooltipAnimTime);
    int integer2 = resources.getInteger(R.integer.config_toastDefaultGravity);
    if (integer <= 0 || integer2 <= 0) {
      return;
    }
    setStableDisplaySizeLocked(integer, integer2);
  }

  public final Point getStableDisplaySizeInternal() {
    int i;
    Point point = new Point();
    synchronized (this.mSyncRoot) {
      Point point2 = this.mStableDisplaySize;
      int i2 = point2.x;
      if (i2 > 0 && (i = point2.y) > 0) {
        point.set(i2, i);
      }
    }
    return point;
  }

  public final void registerDisplayTransactionListenerInternal(
      DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
    this.mDisplayTransactionListeners.add(displayTransactionListener);
  }

  public final void unregisterDisplayTransactionListenerInternal(
      DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
    this.mDisplayTransactionListeners.remove(displayTransactionListener);
  }

  public void setDisplayInfoOverrideFromWindowManagerInternal(int i, DisplayInfo displayInfo) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked != null
          && displayLocked.setDisplayInfoOverrideFromWindowManagerLocked(displayInfo)) {
        handleLogicalDisplayChangedLocked(displayLocked);
      }
    }
  }

  public final void getNonOverrideDisplayInfoInternal(int i, DisplayInfo displayInfo) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked != null) {
        displayLocked.getNonOverrideDisplayInfoLocked(displayInfo);
      }
    }
  }

  public void performTraversalInternal(
      SurfaceControl.Transaction transaction, SparseArray sparseArray) {
    synchronized (this.mSyncRoot) {
      if (this.mPendingTraversal) {
        this.mPendingTraversal = false;
        performTraversalLocked(transaction, sparseArray);
        if (this.mEnabledDexDisplay != null) {
          this.mDisplayDeviceRepo.releaseHDMIWake();
        }
        Iterator it = this.mDisplayTransactionListeners.iterator();
        while (it.hasNext()) {
          ((DisplayManagerInternal.DisplayTransactionListener) it.next())
              .onDisplayTransaction(transaction);
        }
      }
    }
  }

  public final float clampBrightness(int i, float f) {
    if (i == 1) {
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    if (f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        && f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return Float.NaN;
    }
    float f2 = this.mScreenExtendedBrightnessRangeMaximum;
    return f > f2 ? f2 : f;
  }

  public final void requestDisplayStateInternal(int i, int i2, float f, float f2) {
    if (i2 == 0) {
      i2 = 2;
    }
    float clampBrightness = clampBrightness(i2, f);
    float clampBrightness2 = clampBrightness(i2, f2);
    synchronized (this.mSyncRoot) {
      int indexOfKey = this.mDisplayStates.indexOfKey(i);
      BrightnessPair brightnessPair =
          indexOfKey < 0 ? null : (BrightnessPair) this.mDisplayBrightnesses.valueAt(indexOfKey);
      if (indexOfKey >= 0
          && (this.mDisplayStates.valueAt(indexOfKey) != i2
              || brightnessPair.brightness != clampBrightness
              || brightnessPair.sdrBrightness != clampBrightness2)) {
        if (this.mDisplayStates.valueAt(indexOfKey) != i2) {
          PerfLog.d(2, Display.stateToString(i2));
        }
        if (i == 0 && brightnessPair.brightness != clampBrightness) {
          sendDisplayBrightnessEventLocked(clampBrightness);
        }
        if (this.mDisplayStates.valueAt(indexOfKey) != i2) {
          Slog.m72d(
              "DisplayManagerService",
              "!@display_state requestDisplayStateInternal: "
                  + Display.stateToString(this.mDisplayStates.valueAt(indexOfKey))
                  + " -> "
                  + Display.stateToString(i2)
                  + " displayId="
                  + i);
        }
        if (Trace.isTagEnabled(131072L)) {
          Trace.asyncTraceForTrackBegin(
              131072L,
              "requestDisplayStateInternal:" + i,
              Display.stateToString(i2)
                  + ", brightness="
                  + clampBrightness
                  + ", sdrBrightness="
                  + clampBrightness2,
              i);
        }
        this.mDisplayStates.setValueAt(indexOfKey, i2);
        brightnessPair.brightness = clampBrightness;
        brightnessPair.sdrBrightness = clampBrightness2;
        Runnable updateDisplayStateLocked =
            updateDisplayStateLocked(
                this.mLogicalDisplayMapper.getDisplayLocked(i).getPrimaryDisplayDeviceLocked());
        if (Trace.isTagEnabled(131072L)) {
          Trace.asyncTraceForTrackEnd(131072L, "requestDisplayStateInternal:" + i, i);
        }
        if (updateDisplayStateLocked != null) {
          updateDisplayStateLocked.run();
        }
      }
    }
  }

  public class UidImportanceListener implements ActivityManager.OnUidImportanceListener {
    public UidImportanceListener() {}

    public void onUidImportance(int i, int i2) {
      synchronized (DisplayManagerService.this.mPendingCallbackSelfLocked) {
        try {
          if (i2 >= 1000) {
            Slog.m72d("DisplayManagerService", "Drop pending events for gone uid " + i);
            DisplayManagerService.this.mPendingCallbackSelfLocked.delete(i);
            return;
          }
          if (i2 >= 400) {
            return;
          }
          SparseArray sparseArray =
              (SparseArray) DisplayManagerService.this.mPendingCallbackSelfLocked.get(i);
          if (sparseArray == null) {
            return;
          }
          for (int i3 = 0; i3 < sparseArray.size(); i3++) {
            PendingCallback pendingCallback = (PendingCallback) sparseArray.valueAt(i3);
            if (pendingCallback != null) {
              pendingCallback.sendPendingDisplayEvent();
            }
          }
          DisplayManagerService.this.mPendingCallbackSelfLocked.delete(i);
        } catch (Throwable th) {
          throw th;
        }
      }
    }
  }

  public class SettingsObserver extends ContentObserver {
    public SettingsObserver() {
      super(DisplayManagerService.this.mHandler);
      DisplayManagerService.this
          .mContext
          .getContentResolver()
          .registerContentObserver(
              Settings.Secure.getUriFor("minimal_post_processing_allowed"), false, this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      DisplayManagerService.this.handleSettingsChange();
    }
  }

  public final void handleSettingsChange() {
    synchronized (this.mSyncRoot) {
      updateSettingsLocked();
      scheduleTraversalLocked(false);
    }
  }

  public final void updateSettingsLocked() {
    setMinimalPostProcessingAllowed(
        Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(), "minimal_post_processing_allowed", 1, -2)
            != 0);
  }

  public final void updateUserDisabledHdrTypesFromSettingsLocked() {
    this.mAreUserDisabledHdrTypesAllowed =
        Settings.Global.getInt(
                this.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", 1)
            != 0;
    String string =
        Settings.Global.getString(this.mContext.getContentResolver(), "user_disabled_hdr_formats");
    if (string != null) {
      try {
        String[] split = TextUtils.split(string, ",");
        this.mUserDisabledHdrTypes = new int[split.length];
        for (int i = 0; i < split.length; i++) {
          this.mUserDisabledHdrTypes[i] = Integer.parseInt(split[i]);
        }
        if (this.mAreUserDisabledHdrTypesAllowed) {
          return;
        }
        this.mLogicalDisplayMapper.forEachLocked(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda14
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.this.lambda$updateUserDisabledHdrTypesFromSettingsLocked$2(
                    (LogicalDisplay) obj);
              }
            });
        return;
      } catch (NumberFormatException e) {
        Slog.m75e(
            "DisplayManagerService",
            "Failed to parse USER_DISABLED_HDR_FORMATS. Clearing the setting.",
            e);
        clearUserDisabledHdrTypesLocked();
        return;
      }
    }
    clearUserDisabledHdrTypesLocked();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateUserDisabledHdrTypesFromSettingsLocked$2(
      LogicalDisplay logicalDisplay) {
    logicalDisplay.setUserDisabledHdrTypes(this.mUserDisabledHdrTypes);
    handleLogicalDisplayChangedLocked(logicalDisplay);
  }

  public final void clearUserDisabledHdrTypesLocked() {
    synchronized (this.mSyncRoot) {
      this.mUserDisabledHdrTypes = new int[0];
      Settings.Global.putString(
          this.mContext.getContentResolver(), "user_disabled_hdr_formats", "");
    }
  }

  public final void updateUserPreferredDisplayModeSettingsLocked() {
    final Display.Mode mode =
        new Display.Mode(
            Settings.Global.getInt(
                this.mContext.getContentResolver(), "user_preferred_resolution_width", -1),
            Settings.Global.getInt(
                this.mContext.getContentResolver(), "user_preferred_resolution_height", -1),
            Settings.Global.getFloat(
                this.mContext.getContentResolver(),
                "user_preferred_refresh_rate",
                DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
    Display.Mode mode2 = isResolutionAndRefreshRateValid(mode) ? mode : null;
    this.mUserPreferredMode = mode2;
    if (mode2 != null) {
      this.mDisplayDeviceRepo.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((DisplayDevice) obj).setUserPreferredDisplayModeLocked(mode);
            }
          });
    } else {
      this.mLogicalDisplayMapper.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.this.lambda$updateUserPreferredDisplayModeSettingsLocked$4(
                  (LogicalDisplay) obj);
            }
          });
    }
  }

  public final DisplayInfo getDisplayInfoForFrameRateOverride(
      DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr,
      DisplayInfo displayInfo,
      int i) {
    if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
      displayInfo.refreshRateMode =
          this.mDisplayModeDirector.getRefreshRateModeManager().getRefreshRateMode();
    }
    float f = displayInfo.renderFrameRate;
    int length = frameRateOverrideArr.length;
    int i2 = 0;
    while (true) {
      if (i2 >= length) {
        break;
      }
      DisplayEventReceiver.FrameRateOverride frameRateOverride = frameRateOverrideArr[i2];
      if (frameRateOverride.uid == i) {
        f = frameRateOverride.frameRateHz;
        break;
      }
      i2++;
    }
    if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return displayInfo;
    }
    boolean z = i < 10000 || CompatChanges.isChangeEnabled(170503758L, i);
    Display.Mode mode = displayInfo.getMode();
    float refreshRate = mode.getRefreshRate() / f;
    float round = Math.round(refreshRate);
    if (Math.abs(refreshRate - round) > 9.0E-4f) {
      return displayInfo;
    }
    float refreshRate2 = mode.getRefreshRate() / round;
    DisplayInfo displayInfo2 = new DisplayInfo();
    displayInfo2.copyFrom(displayInfo);
    for (Display.Mode mode2 : displayInfo.supportedModes) {
      if (mode2.equalsExceptRefreshRate(mode)
          && mode2.getRefreshRate() >= refreshRate2 - 9.0E-4f
          && mode2.getRefreshRate() <= refreshRate2 + 9.0E-4f) {
        displayInfo2.refreshRateOverride = mode2.getRefreshRate();
        if (!z) {
          displayInfo2.modeId = mode2.getModeId();
        }
        return displayInfo2;
      }
    }
    displayInfo2.refreshRateOverride = refreshRate2;
    if (!z) {
      Display.Mode[] modeArr = displayInfo.supportedModes;
      Display.Mode[] modeArr2 = (Display.Mode[]) Arrays.copyOf(modeArr, modeArr.length + 1);
      displayInfo2.supportedModes = modeArr2;
      modeArr2[modeArr2.length - 1] =
          new Display.Mode(
              IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT,
              mode.getPhysicalWidth(),
              mode.getPhysicalHeight(),
              displayInfo2.refreshRateOverride,
              new float[0],
              mode.getSupportedHdrTypes());
      Display.Mode[] modeArr3 = displayInfo2.supportedModes;
      displayInfo2.modeId = modeArr3[modeArr3.length - 1].getModeId();
    }
    return displayInfo2;
  }

  public final DisplayInfo getDisplayInfoInternal(int i, int i2) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked != null) {
        DisplayInfo displayInfoForFrameRateOverride =
            getDisplayInfoForFrameRateOverride(
                displayLocked.getFrameRateOverrides(), displayLocked.getDisplayInfoLocked(), i2);
        if (displayInfoForFrameRateOverride.hasAccess(i2) || isUidPresentOnDisplayInternal(i2, i)) {
          return displayInfoForFrameRateOverride;
        }
      }
      return null;
    }
  }

  public final void registerCallbackInternal(
      IDisplayManagerCallback iDisplayManagerCallback, int i, int i2, long j) {
    synchronized (this.mSyncRoot) {
      CallbackRecord callbackRecord = (CallbackRecord) this.mCallbacks.get(i);
      if (callbackRecord != null) {
        callbackRecord.updateEventsMask(j);
        return;
      }
      CallbackRecord callbackRecord2 = new CallbackRecord(i, i2, iDisplayManagerCallback, j);
      try {
        iDisplayManagerCallback.asBinder().linkToDeath(callbackRecord2, 0);
        this.mCallbacks.put(i, callbackRecord2);
      } catch (RemoteException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public final void onCallbackDied(CallbackRecord callbackRecord) {
    synchronized (this.mSyncRoot) {
      this.mCallbacks.remove(callbackRecord.mPid);
      stopWifiDisplayScanLocked(callbackRecord);
    }
  }

  public final void startWifiDisplayScanInternal(int i, boolean z) {
    synchronized (this.mSyncRoot) {
      CallbackRecord callbackRecord = (CallbackRecord) this.mCallbacks.get(i);
      if (callbackRecord == null) {
        throw new IllegalStateException(
            "The calling process has not registered an IDisplayManagerCallback.");
      }
      startWifiDisplayScanLocked(callbackRecord, z);
    }
  }

  public final void startWifiDisplayScanLocked(CallbackRecord callbackRecord, boolean z) {
    WifiDisplayAdapter wifiDisplayAdapter;
    if (callbackRecord.mWifiDisplayScanRequested) {
      return;
    }
    callbackRecord.mWifiDisplayScanRequested = true;
    callbackRecord.mWifiDisplayScanRequestedTime = this.dateFormat.format(new Date());
    int i = this.mWifiDisplayScanRequestCount;
    this.mWifiDisplayScanRequestCount = i + 1;
    if (i != 0 || (wifiDisplayAdapter = this.mWifiDisplayAdapter) == null) {
      return;
    }
    wifiDisplayAdapter.requestStartScanLocked(z);
    this.mVolumeController = this.mWifiDisplayAdapter.getVolumeControllerInstance();
  }

  public final void stopWifiDisplayScanInternal(int i) {
    synchronized (this.mSyncRoot) {
      CallbackRecord callbackRecord = (CallbackRecord) this.mCallbacks.get(i);
      if (callbackRecord == null) {
        throw new IllegalStateException(
            "The calling process has not registered an IDisplayManagerCallback.");
      }
      stopWifiDisplayScanLocked(callbackRecord);
    }
  }

  public final void stopWifiDisplayScanLocked(CallbackRecord callbackRecord) {
    if (callbackRecord.mWifiDisplayScanRequested) {
      callbackRecord.mWifiDisplayScanRequested = false;
      callbackRecord.mWifiDisplayScanRequestedTime = this.dateFormat.format(new Date());
      int i = this.mWifiDisplayScanRequestCount - 1;
      this.mWifiDisplayScanRequestCount = i;
      if (i == 0) {
        WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
        if (wifiDisplayAdapter != null) {
          wifiDisplayAdapter.requestStopScanLocked();
          return;
        }
        return;
      }
      if (i < 0) {
        Slog.wtf(
            "DisplayManagerService",
            "mWifiDisplayScanRequestCount became negative: " + this.mWifiDisplayScanRequestCount);
        this.mWifiDisplayScanRequestCount = 0;
      }
    }
  }

  public final void connectWifiDisplayInternal(String str) {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestConnectLocked(str);
      }
    }
  }

  public final void pauseWifiDisplayInternal() {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestPauseLocked();
      }
    }
  }

  public final void resumeWifiDisplayInternal() {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestResumeLocked();
      }
    }
  }

  public final void disconnectWifiDisplayInternal() {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestDisconnectLocked();
      }
    }
  }

  public final void renameWifiDisplayInternal(String str, String str2) {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestRenameLocked(str, str2);
      }
    }
  }

  public final void forgetWifiDisplayInternal(String str) {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestForgetLocked(str);
      }
    }
  }

  public final WifiDisplayStatus getWifiDisplayStatusInternal() {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        return wifiDisplayAdapter.getWifiDisplayStatusLocked();
      }
      return new WifiDisplayStatus();
    }
  }

  public final void setUserDisabledHdrTypesInternal(final int[] iArr) {
    synchronized (this.mSyncRoot) {
      if (iArr == null) {
        Slog.m74e(
            "DisplayManagerService",
            "Null is not an expected argument to setUserDisabledHdrTypesInternal");
        return;
      }
      if (!isSubsetOf(Display.HdrCapabilities.HDR_TYPES, iArr)) {
        Slog.m74e("DisplayManagerService", "userDisabledHdrTypes contains unexpected types");
        return;
      }
      Arrays.sort(iArr);
      if (Arrays.equals(this.mUserDisabledHdrTypes, iArr)) {
        return;
      }
      Settings.Global.putString(
          this.mContext.getContentResolver(),
          "user_disabled_hdr_formats",
          iArr.length != 0 ? TextUtils.join(",", Arrays.stream(iArr).boxed().toArray()) : "");
      this.mUserDisabledHdrTypes = iArr;
      if (!this.mAreUserDisabledHdrTypesAllowed) {
        this.mLogicalDisplayMapper.forEachLocked(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda16
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.this.lambda$setUserDisabledHdrTypesInternal$5(
                    iArr, (LogicalDisplay) obj);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setUserDisabledHdrTypesInternal$5(
      int[] iArr, LogicalDisplay logicalDisplay) {
    logicalDisplay.setUserDisabledHdrTypes(iArr);
    handleLogicalDisplayChangedLocked(logicalDisplay);
  }

  public final boolean isSubsetOf(int[] iArr, int[] iArr2) {
    for (int i : iArr2) {
      if (Arrays.binarySearch(iArr, i) < 0) {
        return false;
      }
    }
    return true;
  }

  public final void setAreUserDisabledHdrTypesAllowedInternal(boolean z) {
    synchronized (this.mSyncRoot) {
      if (this.mAreUserDisabledHdrTypesAllowed == z) {
        return;
      }
      this.mAreUserDisabledHdrTypesAllowed = z;
      if (this.mUserDisabledHdrTypes.length == 0) {
        return;
      }
      Settings.Global.putInt(
          this.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", z ? 1 : 0);
      final int[] iArr = new int[0];
      if (!this.mAreUserDisabledHdrTypesAllowed) {
        iArr = this.mUserDisabledHdrTypes;
      }
      this.mLogicalDisplayMapper.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.this.lambda$setAreUserDisabledHdrTypesAllowedInternal$6(
                  iArr, (LogicalDisplay) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setAreUserDisabledHdrTypesAllowedInternal$6(
      int[] iArr, LogicalDisplay logicalDisplay) {
    logicalDisplay.setUserDisabledHdrTypes(iArr);
    handleLogicalDisplayChangedLocked(logicalDisplay);
  }

  public final void requestColorModeInternal(int i, int i2) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked != null && displayLocked.getRequestedColorModeLocked() != i2) {
        displayLocked.setRequestedColorModeLocked(i2);
        scheduleTraversalLocked(false);
      }
    }
  }

  public final void rotateVirtualDisplayInternal(IBinder iBinder, int i) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return;
      }
      VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDeviceLocked =
          virtualDisplayAdapter.getVirtualDisplayDeviceLocked(iBinder);
      if (virtualDisplayDeviceLocked == null) {
        return;
      }
      LogicalDisplay displayLocked =
          this.mLogicalDisplayMapper.getDisplayLocked(virtualDisplayDeviceLocked);
      if (displayLocked == null) {
        return;
      }
      this.mWindowManagerInternal.freezeDisplayRotation(displayLocked.getDisplayIdLocked(), i);
    }
  }

  public final void connectWifiDisplayInternal(
      SemWifiDisplayConfig semWifiDisplayConfig,
      IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) {
    synchronized (this.mSyncRoot) {
      WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
      if (wifiDisplayAdapter != null) {
        wifiDisplayAdapter.requestConnectLocked(
            semWifiDisplayConfig, iWifiDisplayConnectionCallback);
      }
    }
  }

  public final void startWifiDisplayScanInternal(int i, int i2, int i3) {
    synchronized (this.mSyncRoot) {
      CallbackRecord callbackRecord = (CallbackRecord) this.mCallbacks.get(i);
      if (callbackRecord == null) {
        throw new IllegalStateException(
            "The calling process has not registered an IDisplayManagerCallback.");
      }
      startWifiDisplayScanLocked(callbackRecord, i2, i3);
    }
  }

  public final void startWifiDisplayScanLocked(CallbackRecord callbackRecord, int i, int i2) {
    WifiDisplayAdapter wifiDisplayAdapter;
    if (callbackRecord.mWifiDisplayScanRequested) {
      return;
    }
    callbackRecord.mWifiDisplayScanRequested = true;
    callbackRecord.mWifiDisplayScanRequestedTime = this.dateFormat.format(new Date());
    int i3 = this.mWifiDisplayScanRequestCount;
    this.mWifiDisplayScanRequestCount = i3 + 1;
    if (i3 != 0 || (wifiDisplayAdapter = this.mWifiDisplayAdapter) == null) {
      return;
    }
    wifiDisplayAdapter.requestStartScanLocked(i, i2);
    this.mVolumeController = this.mWifiDisplayAdapter.getVolumeControllerInstance();
  }

  public final boolean validatePackageName(int i, String str) {
    String[] packagesForUid;
    if (i == 0) {
      return true;
    }
    if (str != null
        && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null) {
      for (String str2 : packagesForUid) {
        if (str2.equals(str)) {
          return true;
        }
      }
    }
    return false;
  }

  public final boolean canProjectVideo(IMediaProjection iMediaProjection) {
    if (iMediaProjection != null) {
      try {
        if (iMediaProjection.canProjectVideo()) {
          return true;
        }
      } catch (RemoteException e) {
        Slog.m75e("DisplayManagerService", "Unable to query projection service for permissions", e);
      }
    }
    if (checkCallingPermission("android.permission.CAPTURE_VIDEO_OUTPUT", "canProjectVideo()")) {
      return true;
    }
    return canProjectSecureVideo(iMediaProjection);
  }

  public final boolean canProjectSecureVideo(IMediaProjection iMediaProjection) {
    if (iMediaProjection != null) {
      try {
        if (iMediaProjection.canProjectSecureVideo()) {
          return true;
        }
      } catch (RemoteException e) {
        Slog.m75e("DisplayManagerService", "Unable to query projection service for permissions", e);
      }
    }
    return checkCallingPermission(
        "android.permission.CAPTURE_SECURE_VIDEO_OUTPUT", "canProjectSecureVideo()");
  }

  public final boolean checkCallingPermission(String str, String str2) {
    if (this.mContext.checkCallingPermission(str) == 0) {
      return true;
    }
    Slog.m79w(
        "DisplayManagerService",
        "Permission Denial: "
            + str2
            + " from pid="
            + Binder.getCallingPid()
            + ", uid="
            + Binder.getCallingUid()
            + " requires "
            + str);
    return false;
  }

  public final int createVirtualDisplayInternal(
      VirtualDisplayConfig virtualDisplayConfig,
      IVirtualDisplayCallback iVirtualDisplayCallback,
      IMediaProjection iMediaProjection,
      IVirtualDevice iVirtualDevice,
      DisplayWindowPolicyController displayWindowPolicyController,
      String str) {
    boolean z;
    boolean z2;
    int createVirtualDisplayLocked;
    int callingUid = Binder.getCallingUid();
    if (!validatePackageName(callingUid, str)) {
      throw new SecurityException("packageName must match the calling uid");
    }
    if (iVirtualDisplayCallback == null) {
      throw new IllegalArgumentException("appToken must not be null");
    }
    if (virtualDisplayConfig == null) {
      throw new IllegalArgumentException("virtualDisplayConfig must not be null");
    }
    Surface surface = virtualDisplayConfig.getSurface();
    int flags = virtualDisplayConfig.getFlags();
    if (iVirtualDevice != null) {
      try {
        if (!((VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class))
            .isValidVirtualDeviceId(iVirtualDevice.getDeviceId())) {
          throw new SecurityException("Invalid virtual device");
        }
        flags |=
            ((VirtualDeviceManagerInternal) getLocalService(VirtualDeviceManagerInternal.class))
                .getBaseVirtualDisplayFlags(iVirtualDevice);
      } catch (RemoteException unused) {
        throw new SecurityException("Unable to validate virtual device");
      }
    }
    if (surface != null && surface.isSingleBuffered()) {
      throw new IllegalArgumentException("Surface can't be single-buffered");
    }
    if ((flags & 1) != 0) {
      flags |= 16;
      if ((flags & 32) != 0) {
        throw new IllegalArgumentException(
            "Public display must not be marked as SHOW_WHEN_LOCKED_INSECURE");
      }
    }
    if ((flags & 8) != 0) {
      flags &= -17;
    }
    if ((flags & 16) != 0) {
      flags &= -2049;
    }
    if ((flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0 && iVirtualDevice != null) {
      flags |= 32768;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (iMediaProjection != null) {
        try {
          if (!getProjectionService().isCurrentProjection(iMediaProjection)) {
            throw new SecurityException(
                "Cannot create VirtualDisplay with non-current MediaProjection");
          }
          if (iMediaProjection.isValid()) {
            z = false;
          } else {
            Slog.m79w(
                "DisplayManagerService",
                "Reusing token: create virtual display for app reusing token");
            getProjectionService().requestConsentForInvalidProjection(iMediaProjection);
            z = true;
          }
          flags = iMediaProjection.applyVirtualDisplayFlags(flags);
          z2 = z;
        } catch (RemoteException e) {
          throw new SecurityException("Unable to validate media projection or flags", e);
        }
      } else {
        z2 = false;
      }
      Binder.restoreCallingIdentity(clearCallingIdentity);
      if (callingUid != 1000 && (flags & 16) != 0 && !canProjectVideo(iMediaProjection)) {
        throw new SecurityException(
            "Requires CAPTURE_VIDEO_OUTPUT or CAPTURE_SECURE_VIDEO_OUTPUT permission, or an"
                + " appropriate MediaProjection token in order to create a screen sharing virtual"
                + " display.");
      }
      if (callingUid != 1000 && (flags & 4) != 0 && !canProjectSecureVideo(iMediaProjection)) {
        throw new SecurityException(
            "Requires CAPTURE_SECURE_VIDEO_OUTPUT or an appropriate MediaProjection token to create"
                + " a secure virtual display.");
      }
      if (callingUid != 1000
          && (flags & 1024) != 0
          && !checkCallingPermission(
              "android.permission.ADD_TRUSTED_DISPLAY", "createVirtualDisplay()")) {
        EventLog.writeEvent(
            1397638484,
            "162627132",
            Integer.valueOf(callingUid),
            "Attempt to create a trusted display without holding permission!");
        throw new SecurityException(
            "Requires ADD_TRUSTED_DISPLAY permission to create a trusted virtual display.");
      }
      if (callingUid != 1000
          && (flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0
          && iVirtualDevice == null
          && !checkCallingPermission(
              "android.permission.ADD_TRUSTED_DISPLAY", "createVirtualDisplay()")) {
        throw new SecurityException(
            "Requires ADD_TRUSTED_DISPLAY permission to create a virtual display which is not in"
                + " the default DisplayGroup.");
      }
      if ((flags & IInstalld.FLAG_USE_QUOTA) != 0
          && callingUid != 1000
          && !checkCallingPermission(
              "android.permission.ADD_ALWAYS_UNLOCKED_DISPLAY", "createVirtualDisplay()")) {
        throw new SecurityException(
            "Requires ADD_ALWAYS_UNLOCKED_DISPLAY permission to create an always unlocked virtual"
                + " display.");
      }
      if ((1048576 & flags) != 0
          && this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
        throw new SecurityException("Carlife display only create by System app");
      }
      if ((flags & 1024) == 0) {
        flags &= -513;
      }
      int i = flags;
      if ((i & FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM)
              == 512
          && !checkCallingPermission(
              "android.permission.INTERNAL_SYSTEM_WINDOW", "createVirtualDisplay()")) {
        throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
      }
      clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (this.mSyncRoot) {
          createVirtualDisplayLocked =
              createVirtualDisplayLocked(
                  iVirtualDisplayCallback,
                  iMediaProjection,
                  callingUid,
                  str,
                  iVirtualDevice,
                  surface,
                  i,
                  virtualDisplayConfig);
          if (createVirtualDisplayLocked != -1
              && iVirtualDevice != null
              && displayWindowPolicyController != null) {
            this.mDisplayWindowPolicyControllers.put(
                createVirtualDisplayLocked,
                Pair.create(iVirtualDevice, displayWindowPolicyController));
          }
        }
        ContentRecordingSession contentRecordingSession = null;
        if (iMediaProjection != null) {
          try {
            IBinder launchCookie = iMediaProjection.getLaunchCookie();
            if (launchCookie == null) {
              contentRecordingSession =
                  ContentRecordingSession.createDisplaySession(
                      virtualDisplayConfig.getDisplayIdToMirror());
            } else {
              contentRecordingSession = ContentRecordingSession.createTaskSession(launchCookie);
            }
          } catch (RemoteException e2) {
            Slog.m75e(
                "DisplayManagerService", "Unable to retrieve the projection's launch cookie", e2);
          }
        }
        if (((iMediaProjection == null && (i & 16) == 0) ? false : true)
            && createVirtualDisplayLocked != -1
            && contentRecordingSession != null) {
          contentRecordingSession.setVirtualDisplayId(createVirtualDisplayLocked);
          contentRecordingSession.setWaitingForConsent(z2);
          try {
            if (!getProjectionService()
                .setContentRecordingSession(contentRecordingSession, iMediaProjection)) {
              releaseVirtualDisplayInternal(iVirtualDisplayCallback.asBinder());
              return -1;
            }
            if (iMediaProjection != null) {
              iMediaProjection.notifyVirtualDisplayCreated(createVirtualDisplayLocked);
            }
          } catch (RemoteException e3) {
            Slog.m75e(
                "DisplayManagerService",
                "Unable to tell MediaProjectionManagerService to set the content recording session",
                e3);
          }
        }
        return createVirtualDisplayLocked;
      } finally {
      }
    } finally {
    }
  }

  public final int createVirtualDisplayLocked(
      IVirtualDisplayCallback iVirtualDisplayCallback,
      IMediaProjection iMediaProjection,
      int i,
      String str,
      IVirtualDevice iVirtualDevice,
      Surface surface,
      int i2,
      VirtualDisplayConfig virtualDisplayConfig) {
    VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
    if (virtualDisplayAdapter == null) {
      Slog.m79w(
          "DisplayManagerService",
          "Rejecting request to create private virtual display because the virtual display adapter"
              + " is not available.");
      return -1;
    }
    virtualDisplayAdapter.setDefaultDisplayDeviceInfoLocked(getDisplayDeviceInfoInternal(0));
    DisplayDevice createVirtualDisplayLocked =
        this.mVirtualDisplayAdapter.createVirtualDisplayLocked(
            iVirtualDisplayCallback, iMediaProjection, i, str, surface, i2, virtualDisplayConfig);
    if (createVirtualDisplayLocked == null) {
      return -1;
    }
    if ((i2 & 32768) != 0) {
      if (iVirtualDevice != null) {
        try {
          this.mLogicalDisplayMapper.associateDisplayDeviceWithVirtualDevice(
              createVirtualDisplayLocked, iVirtualDevice.getDeviceId());
        } catch (RemoteException e) {
          e.rethrowFromSystemServer();
        }
      } else {
        Slog.m76i(
            "DisplayManagerService",
            "Display created with VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP set, but no virtual"
                + " device. The display will not be added to a device display group.");
      }
    }
    this.mDisplayDeviceRepo.onDisplayDeviceEvent(createVirtualDisplayLocked, 1);
    LogicalDisplay displayLocked =
        this.mLogicalDisplayMapper.getDisplayLocked(createVirtualDisplayLocked);
    if (displayLocked != null) {
      return displayLocked.getDisplayIdLocked();
    }
    Slog.m79w(
        "DisplayManagerService",
        "Rejecting request to create virtual display because the logical display was not created.");
    this.mVirtualDisplayAdapter.releaseVirtualDisplayLocked(iVirtualDisplayCallback.asBinder());
    this.mDisplayDeviceRepo.onDisplayDeviceEvent(createVirtualDisplayLocked, 3);
    return -1;
  }

  public final void resizeVirtualDisplayInternal(IBinder iBinder, int i, int i2, int i3) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return;
      }
      virtualDisplayAdapter.resizeVirtualDisplayLocked(iBinder, i, i2, i3);
    }
  }

  public final void setVirtualDisplaySurfaceInternal(IBinder iBinder, Surface surface) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return;
      }
      virtualDisplayAdapter.setVirtualDisplaySurfaceLocked(iBinder, surface);
    }
  }

  public final void releaseVirtualDisplayInternal(IBinder iBinder) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return;
      }
      DisplayDevice releaseVirtualDisplayLocked =
          virtualDisplayAdapter.releaseVirtualDisplayLocked(iBinder);
      if (releaseVirtualDisplayLocked != null) {
        this.mDisplayDeviceRepo.onDisplayDeviceEvent(releaseVirtualDisplayLocked, 3);
      }
    }
  }

  public final void setVirtualDisplayStateInternal(IBinder iBinder, boolean z) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return;
      }
      virtualDisplayAdapter.setVirtualDisplayStateLocked(iBinder, z);
    }
  }

  public final void registerDefaultDisplayAdapters() {
    synchronized (this.mSyncRoot) {
      registerDisplayAdapterLocked(
          this.mInjector.getLocalDisplayAdapter(
              this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo));
      VirtualDisplayAdapter virtualDisplayAdapter =
          this.mInjector.getVirtualDisplayAdapter(
              this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo);
      this.mVirtualDisplayAdapter = virtualDisplayAdapter;
      if (virtualDisplayAdapter != null) {
        registerDisplayAdapterLocked(virtualDisplayAdapter);
        this.mVirtualDisplayAdapter.setLogicalDisplayMapperLocked(this.mLogicalDisplayMapper);
      }
    }
  }

  public final void registerAdditionalDisplayAdapters() {
    synchronized (this.mSyncRoot) {
      if (shouldRegisterNonEssentialDisplayAdaptersLocked()) {
        registerOverlayDisplayAdapterLocked();
        registerWifiDisplayAdapterLocked();
      }
    }
  }

  public final void registerOverlayDisplayAdapterLocked() {
    registerDisplayAdapterLocked(
        new OverlayDisplayAdapter(
            this.mSyncRoot,
            this.mContext,
            this.mHandler,
            this.mDisplayDeviceRepo,
            this.mUiHandler));
  }

  public final void registerWifiDisplayAdapterLocked() {
    WifiDisplayAdapter wifiDisplayAdapter =
        new WifiDisplayAdapter(
            this.mSyncRoot,
            this.mContext,
            this.mHandler,
            this.mDisplayDeviceRepo,
            this.mPersistentDataStore);
    this.mWifiDisplayAdapter = wifiDisplayAdapter;
    registerDisplayAdapterLocked(wifiDisplayAdapter);
    this.mWifiDisplayAdapter.setDefaultDisplayDeviceInfoLocked(getDisplayDeviceInfoInternal(0));
  }

  public final boolean shouldRegisterNonEssentialDisplayAdaptersLocked() {
    return !this.mSafeMode;
  }

  public final void registerDisplayAdapterLocked(DisplayAdapter displayAdapter) {
    this.mDisplayAdapters.add(displayAdapter);
    displayAdapter.registerLocked();
  }

  public final void handleLogicalDisplayAddedLocked(LogicalDisplay logicalDisplay) {
    DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
    final int displayIdLocked = logicalDisplay.getDisplayIdLocked();
    boolean z = displayIdLocked == 0;
    configureColorModeLocked(logicalDisplay, primaryDisplayDeviceLocked);
    if (!this.mAreUserDisabledHdrTypesAllowed) {
      logicalDisplay.setUserDisabledHdrTypes(this.mUserDisabledHdrTypes);
    }
    if (z) {
      notifyDefaultDisplayDeviceUpdated(logicalDisplay);
      recordStableDisplayStatsIfNeededLocked(logicalDisplay);
      recordTopInsetLocked(logicalDisplay);
    }
    Display.Mode mode = this.mUserPreferredMode;
    if (mode != null) {
      primaryDisplayDeviceLocked.setUserPreferredDisplayModeLocked(mode);
    } else {
      lambda$updateUserPreferredDisplayModeSettingsLocked$4(logicalDisplay);
    }
    DisplayPowerControllerInterface addDisplayPowerControllerLocked =
        addDisplayPowerControllerLocked(logicalDisplay);
    if (addDisplayPowerControllerLocked != null) {
      updateDisplayPowerControllerLeaderLocked(
          addDisplayPowerControllerLocked, logicalDisplay.getLeadDisplayIdLocked());
      this.mLogicalDisplayMapper.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.this.lambda$handleLogicalDisplayAddedLocked$7(
                  displayIdLocked, (LogicalDisplay) obj);
            }
          });
    }
    this.mDisplayStates.append(displayIdLocked, 0);
    if (logicalDisplay.getDisplayInfoLocked().type == 1) {
      this.mDisplayStateLimits.append(displayIdLocked, 0);
    }
    float f = logicalDisplay.getDisplayInfoLocked().brightnessDefault;
    this.mDisplayBrightnesses.append(displayIdLocked, new BrightnessPair(f, f));
    DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    if (z) {
      this.mSyncRoot.notifyAll();
    }
    sendDisplayEventLocked(logicalDisplay, 1);
    Runnable updateDisplayStateLocked = updateDisplayStateLocked(primaryDisplayDeviceLocked);
    if (updateDisplayStateLocked != null) {
      updateDisplayStateLocked.run();
    }
    scheduleTraversalLocked(false);
    if (this.mSystemReady && logicalDisplay.getDisplayInfoLocked().type == 2) {
      this.mConnectedExternalDisplayId = displayIdLocked;
      this.mUiHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.this.lambda$handleLogicalDisplayAddedLocked$8();
            }
          });
      if (this.mDisplayDeviceRepo.isDexDisplayDeviceEnabledLocked()) {
        return;
      }
      this.mDisplayDeviceRepo.mNeedWakeLock = true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLogicalDisplayAddedLocked$7(
      int i, LogicalDisplay logicalDisplay) {
    DisplayPowerControllerInterface displayPowerControllerInterface;
    if (logicalDisplay.getLeadDisplayIdLocked() != i
        || (displayPowerControllerInterface =
                (DisplayPowerControllerInterface)
                    this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked()))
            == null) {
      return;
    }
    updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLogicalDisplayAddedLocked$8() {
    Toast makeText =
        Toast.makeText(
            this.mContext.createDisplayContext(
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                    .getDisplay(0)),
            this.mContext.getResources().getString(R.string.mediasize_na_arch_b),
            0);
    makeText.semSetPreferredDisplayType(0);
    makeText.show();
  }

  public final void handleLogicalDisplayChangedLocked(LogicalDisplay logicalDisplay) {
    updateViewportPowerStateLocked(logicalDisplay);
    int displayIdLocked = logicalDisplay.getDisplayIdLocked();
    if (displayIdLocked == 0) {
      recordTopInsetLocked(logicalDisplay);
      if (CoreRune.FW_VRR_RESOLUTION_POLICY) {
        this.mDisplayModeDirector
            .getRefreshRateModeManager()
            .getController()
            .updateResolutionLocked(displayIdLocked, logicalDisplay.getDisplayInfoLocked());
      }
    }
    sendDisplayEventLocked(logicalDisplay, 2);
    scheduleTraversalLocked(false);
    this.mPersistentDataStore.saveIfNeeded();
    DisplayPowerControllerInterface displayPowerControllerInterface =
        (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(displayIdLocked);
    if (displayPowerControllerInterface != null) {
      int leadDisplayIdLocked =
          (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY
                  && !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
                  && displayIdLocked == 1
                  && this.mDualScreenPolicy == -1)
              ? 0
              : logicalDisplay.getLeadDisplayIdLocked();
      updateDisplayPowerControllerLeaderLocked(
          displayPowerControllerInterface, leadDisplayIdLocked);
      HighBrightnessModeMetadata highBrightnessModeMetadataLocked =
          this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(
              logicalDisplay);
      if (highBrightnessModeMetadataLocked != null) {
        displayPowerControllerInterface.onDisplayChanged(
            highBrightnessModeMetadataLocked, leadDisplayIdLocked);
      }
    }
    if (CoreRune.MD_DEX_EMULATOR) {
      this.mDexEmulator.onLogicalDisplayAddedLocked(logicalDisplay);
    }
    if (CoreRune.MT_NEW_DEX_EMULATOR) {
      this.mNewDexEmulator.onLogicalDisplayAddedLocked(logicalDisplay);
    }
  }

  public final void updateDisplayPowerControllerLeaderLocked(
      DisplayPowerControllerInterface displayPowerControllerInterface, int i) {
    DisplayPowerControllerInterface displayPowerControllerInterface2;
    DisplayPowerControllerInterface displayPowerControllerInterface3;
    if (displayPowerControllerInterface.getLeadDisplayId() == i) {
      return;
    }
    int leadDisplayId = displayPowerControllerInterface.getLeadDisplayId();
    if (leadDisplayId != -1
        && (displayPowerControllerInterface3 =
                (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(leadDisplayId))
            != null) {
      displayPowerControllerInterface3.removeDisplayBrightnessFollower(
          displayPowerControllerInterface);
    }
    if (i == -1
        || (displayPowerControllerInterface2 =
                (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i))
            == null) {
      return;
    }
    displayPowerControllerInterface2.addDisplayBrightnessFollower(displayPowerControllerInterface);
  }

  public final void handleLogicalDisplayFrameRateOverridesChangedLocked(
      LogicalDisplay logicalDisplay) {
    sendDisplayEventFrameRateOverrideLocked(logicalDisplay.getDisplayIdLocked());
    scheduleTraversalLocked(false);
  }

  public final void handleLogicalDisplayRemovedLocked(LogicalDisplay logicalDisplay) {
    final IVirtualDevice iVirtualDevice;
    final int displayIdLocked = logicalDisplay.getDisplayIdLocked();
    DisplayPowerControllerInterface displayPowerControllerInterface =
        (DisplayPowerControllerInterface)
            this.mDisplayPowerControllers.removeReturnOld(displayIdLocked);
    if (displayPowerControllerInterface != null) {
      updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, -1);
      displayPowerControllerInterface.stop();
    }
    this.mDisplayStates.delete(displayIdLocked);
    if (logicalDisplay.getDisplayInfoLocked().type == 1) {
      this.mDisplayStateLimits.delete(displayIdLocked);
    }
    this.mDisplayBrightnesses.delete(displayIdLocked);
    DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    sendDisplayEventLocked(logicalDisplay, 3);
    scheduleTraversalLocked(false);
    if (this.mSystemReady && this.mConnectedExternalDisplayId == displayIdLocked) {
      this.mConnectedExternalDisplayId = -1;
      this.mUiHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.this.lambda$handleLogicalDisplayRemovedLocked$9();
            }
          });
      this.mDisplayDeviceRepo.mNeedWakeLock = false;
    }
    if (!this.mDisplayWindowPolicyControllers.contains(displayIdLocked)
        || (iVirtualDevice =
                (IVirtualDevice)
                    ((Pair) this.mDisplayWindowPolicyControllers.removeReturnOld(displayIdLocked))
                        .first)
            == null) {
      return;
    }
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda27
          @Override // java.lang.Runnable
          public final void run() {
            DisplayManagerService.this.lambda$handleLogicalDisplayRemovedLocked$10(
                iVirtualDevice, displayIdLocked);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLogicalDisplayRemovedLocked$9() {
    Toast makeText =
        Toast.makeText(
            this.mContext.createDisplayContext(
                ((DisplayManager) this.mContext.getSystemService(DisplayManager.class))
                    .getDisplay(0)),
            this.mContext.getResources().getString(R.string.mediasize_na_arch_c),
            0);
    makeText.semSetPreferredDisplayType(0);
    makeText.show();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleLogicalDisplayRemovedLocked$10(
      IVirtualDevice iVirtualDevice, int i) {
    ((VirtualDeviceManagerInternal) getLocalService(VirtualDeviceManagerInternal.class))
        .onVirtualDisplayRemoved(iVirtualDevice, i);
  }

  public final void handleLogicalDisplaySwappedLocked(LogicalDisplay logicalDisplay) {
    handleLogicalDisplayChangedLocked(logicalDisplay);
    if (logicalDisplay.getDisplayIdLocked() == 0) {
      notifyDefaultDisplayDeviceUpdated(logicalDisplay);
    }
    this.mHandler.sendEmptyMessage(6);
  }

  public final void handleLogicalDisplayHdrSdrRatioChangedLocked(LogicalDisplay logicalDisplay) {
    sendDisplayEventLocked(logicalDisplay, 5);
  }

  public final void notifyDefaultDisplayDeviceUpdated(LogicalDisplay logicalDisplay) {
    this.mDisplayModeDirector.defaultDisplayDeviceUpdated(
        logicalDisplay.getPrimaryDisplayDeviceLocked().mDisplayDeviceConfig);
  }

  public final void handleLogicalDisplayDeviceStateTransitionLocked(LogicalDisplay logicalDisplay) {
    int displayIdLocked = logicalDisplay.getDisplayIdLocked();
    DisplayPowerControllerInterface displayPowerControllerInterface =
        (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(displayIdLocked);
    if (displayPowerControllerInterface != null) {
      int leadDisplayIdLocked = logicalDisplay.getLeadDisplayIdLocked();
      if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY
          && !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
          && displayIdLocked == 1
          && this.mDualScreenPolicy == -1) {
        leadDisplayIdLocked = 0;
      }
      updateDisplayPowerControllerLeaderLocked(
          displayPowerControllerInterface, leadDisplayIdLocked);
      HighBrightnessModeMetadata highBrightnessModeMetadataLocked =
          this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(
              logicalDisplay);
      if (highBrightnessModeMetadataLocked != null) {
        displayPowerControllerInterface.onDisplayChanged(
            highBrightnessModeMetadataLocked, leadDisplayIdLocked);
      }
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:28:0x0079  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final Runnable updateDisplayStateLocked(DisplayDevice displayDevice) {
    LogicalDisplay displayLocked;
    ArrayList arrayList;
    int i;
    int i2;
    int i3;
    DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
    if ((displayDeviceInfoLocked.flags & 32) != 0
        || (displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(displayDevice)) == null) {
      return null;
    }
    int displayIdLocked = displayLocked.getDisplayIdLocked();
    int i4 = this.mDisplayStates.get(displayIdLocked);
    if (displayDeviceInfoLocked.type == 1) {
      i =
          getDesiredStateLimitLocked(
              this.mDisplayStateLimitLocks,
              this.mDisplayStateLimits.get(displayIdLocked),
              displayDeviceInfoLocked);
      arrayList = getCopyOfArrayList(this.mDisplayStateListeners);
    } else {
      arrayList = null;
      i = 0;
    }
    if (PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY
        && (displayDeviceInfoLocked.flags & 16777216) != 0
        && Display.isDozeState(i4)) {
      i2 = 0;
      i4 = 2;
    } else {
      i2 = i;
    }
    if (displayDeviceInfoLocked.type == 2 && Display.isDozeState(i4)) {
      i4 = 1;
    }
    if (this.mNeedSkipDozeState) {
      if (Display.isDozeState(i4)) {
        Slog.m72d("DisplayManagerService", "Ignore setting display state doze while unfolding");
        i3 = 1;
        if (i3 != 0) {
          BrightnessPair brightnessPair =
              (BrightnessPair) this.mDisplayBrightnesses.get(displayIdLocked);
          return displayDevice.requestDisplayStateLocked(
              i3, brightnessPair.brightness, brightnessPair.sdrBrightness, i2, arrayList);
        }
        return null;
      }
      if (Display.isActiveState(i4)) {
        this.mNeedSkipDozeState = false;
      }
    }
    i3 = i4;
    if (i3 != 0) {}
    return null;
  }

  public final void configureColorModeLocked(
      LogicalDisplay logicalDisplay, DisplayDevice displayDevice) {
    if (logicalDisplay.getPrimaryDisplayDeviceLocked() == displayDevice) {
      int colorMode = this.mPersistentDataStore.getColorMode(displayDevice);
      if (colorMode == -1) {
        colorMode =
            logicalDisplay.getDisplayIdLocked() == 0 ? this.mDefaultDisplayDefaultColorMode : 0;
      }
      logicalDisplay.setRequestedColorModeLocked(colorMode);
    }
  }

  /* renamed from: configurePreferredDisplayModeLocked, reason: merged with bridge method [inline-methods] */
  public final void lambda$updateUserPreferredDisplayModeSettingsLocked$4(
      LogicalDisplay logicalDisplay) {
    DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
    Point userPreferredResolution =
        this.mPersistentDataStore.getUserPreferredResolution(primaryDisplayDeviceLocked);
    float userPreferredRefreshRate =
        this.mPersistentDataStore.getUserPreferredRefreshRate(primaryDisplayDeviceLocked);
    if ((userPreferredResolution == null && Float.isNaN(userPreferredRefreshRate))
        || (userPreferredResolution.equals(0, 0)
            && userPreferredRefreshRate == DisplayPowerController2.RATE_FROM_DOZE_TO_ON)) {
      Display.Mode systemPreferredDisplayModeLocked =
          primaryDisplayDeviceLocked.getSystemPreferredDisplayModeLocked();
      if (systemPreferredDisplayModeLocked == null) {
        return;
      }
      storeModeInPersistentDataStoreLocked(
          logicalDisplay.getDisplayIdLocked(),
          systemPreferredDisplayModeLocked.getPhysicalWidth(),
          systemPreferredDisplayModeLocked.getPhysicalHeight(),
          systemPreferredDisplayModeLocked.getRefreshRate());
      primaryDisplayDeviceLocked.setUserPreferredDisplayModeLocked(
          systemPreferredDisplayModeLocked);
      return;
    }
    Display.Mode.Builder builder = new Display.Mode.Builder();
    builder.setResolution(userPreferredResolution.x, userPreferredResolution.y);
    if (!Float.isNaN(userPreferredRefreshRate)) {
      builder.setRefreshRate(userPreferredRefreshRate);
    }
    primaryDisplayDeviceLocked.setUserPreferredDisplayModeLocked(builder.build());
  }

  public final void storeHdrConversionModeLocked(HdrConversionMode hdrConversionMode) {
    Settings.Global.putInt(
        this.mContext.getContentResolver(),
        "hdr_conversion_mode",
        hdrConversionMode.getConversionMode());
    Settings.Global.putInt(
        this.mContext.getContentResolver(),
        "hdr_force_conversion_type",
        hdrConversionMode.getConversionMode() == 3
            ? hdrConversionMode.getPreferredHdrOutputType()
            : -1);
  }

  public void updateHdrConversionModeSettingsLocked() {
    int i = Settings.Global.getInt(this.mContext.getContentResolver(), "hdr_conversion_mode", 2);
    HdrConversionMode hdrConversionMode =
        new HdrConversionMode(
            i,
            i == 3
                ? Settings.Global.getInt(
                    this.mContext.getContentResolver(), "hdr_force_conversion_type", 1)
                : -1);
    this.mHdrConversionMode = hdrConversionMode;
    setHdrConversionModeInternal(hdrConversionMode);
  }

  public final void recordStableDisplayStatsIfNeededLocked(LogicalDisplay logicalDisplay) {
    Point point = this.mStableDisplaySize;
    if (point.x > 0 || point.y > 0) {
      return;
    }
    DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
    setStableDisplaySizeLocked(
        displayInfoLocked.getNaturalWidth(), displayInfoLocked.getNaturalHeight());
  }

  public final void recordTopInsetLocked(LogicalDisplay logicalDisplay) {
    int i;
    if (!this.mSystemReady
        || logicalDisplay == null
        || (i = logicalDisplay.getInsets().top) == this.mDefaultDisplayTopInset) {
      return;
    }
    this.mDefaultDisplayTopInset = i;
    SystemProperties.set("persist.sys.displayinset.top", Integer.toString(i));
  }

  public final void setStableDisplaySizeLocked(int i, int i2) {
    Point point = new Point(i, i2);
    this.mStableDisplaySize = point;
    try {
      this.mPersistentDataStore.setStableDisplaySize(point);
    } finally {
      this.mPersistentDataStore.saveIfNeeded();
    }
  }

  public Curve getMinimumBrightnessCurveInternal() {
    return this.mMinimumBrightnessCurve;
  }

  public int getPreferredWideGamutColorSpaceIdInternal() {
    return this.mWideColorSpace.getId();
  }

  public OverlayProperties getOverlaySupportInternal() {
    return this.mOverlayProperties;
  }

  /* JADX WARN: Code restructure failed: missing block: B:6:0x001c, code lost:

     r2 = -1;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void setUserPreferredDisplayModeInternal(int i, Display.Mode mode) {
    synchronized (this.mSyncRoot) {
      if (mode != null) {
        try {
          if (!isResolutionAndRefreshRateValid(mode) && i == -1) {
            throw new IllegalArgumentException(
                "width, height and refresh rate of mode should be greater than 0 when setting the"
                    + " global user preferred display mode.");
          }
        } catch (Throwable th) {
          throw th;
        }
      }
      int physicalHeight = mode.getPhysicalHeight();
      int physicalWidth = mode == null ? -1 : mode.getPhysicalWidth();
      float refreshRate =
          mode == null ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : mode.getRefreshRate();
      storeModeInPersistentDataStoreLocked(i, physicalWidth, physicalHeight, refreshRate);
      if (i != -1) {
        setUserPreferredModeForDisplayLocked(i, mode);
      } else {
        this.mUserPreferredMode = mode;
        storeModeInGlobalSettingsLocked(physicalWidth, physicalHeight, refreshRate, mode);
      }
    }
  }

  public final void storeModeInPersistentDataStoreLocked(int i, int i2, int i3, float f) {
    DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
    if (deviceForDisplayLocked == null) {
      return;
    }
    try {
      this.mPersistentDataStore.setUserPreferredResolution(deviceForDisplayLocked, i2, i3);
      this.mPersistentDataStore.setUserPreferredRefreshRate(deviceForDisplayLocked, f);
    } finally {
      this.mPersistentDataStore.saveIfNeeded();
    }
  }

  public final void setUserPreferredModeForDisplayLocked(int i, Display.Mode mode) {
    DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
    if (deviceForDisplayLocked == null) {
      return;
    }
    deviceForDisplayLocked.setUserPreferredDisplayModeLocked(mode);
  }

  public final void storeModeInGlobalSettingsLocked(
      int i, int i2, float f, final Display.Mode mode) {
    Settings.Global.putFloat(this.mContext.getContentResolver(), "user_preferred_refresh_rate", f);
    Settings.Global.putInt(
        this.mContext.getContentResolver(), "user_preferred_resolution_height", i2);
    Settings.Global.putInt(
        this.mContext.getContentResolver(), "user_preferred_resolution_width", i);
    this.mDisplayDeviceRepo.forEachLocked(
        new Consumer() { // from class:
                         // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((DisplayDevice) obj).setUserPreferredDisplayModeLocked(mode);
          }
        });
  }

  public final int[] getEnabledAutoHdrTypesLocked() {
    boolean z;
    IntArray intArray = new IntArray();
    for (int i : getSupportedHdrOutputTypesInternal()) {
      int[] iArr = this.mUserDisabledHdrTypes;
      int length = iArr.length;
      int i2 = 0;
      while (true) {
        if (i2 >= length) {
          z = false;
          break;
        }
        if (i == iArr[i2]) {
          z = true;
          break;
        }
        i2++;
      }
      if (!z) {
        intArray.add(i);
      }
    }
    return intArray.toArray();
  }

  public final boolean hdrConversionIntroducesLatencyLocked() {
    int preferredHdrOutputType = getHdrConversionModeSettingInternal().getPreferredHdrOutputType();
    if (preferredHdrOutputType != -1) {
      return ArrayUtils.contains(
          this.mInjector.getHdrOutputTypesWithLatency(), preferredHdrOutputType);
    }
    return false;
  }

  public Display.Mode getUserPreferredDisplayModeInternal(int i) {
    synchronized (this.mSyncRoot) {
      if (i == -1) {
        return this.mUserPreferredMode;
      }
      DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
      if (deviceForDisplayLocked == null) {
        return null;
      }
      return deviceForDisplayLocked.getUserPreferredDisplayModeLocked();
    }
  }

  public Display.Mode getSystemPreferredDisplayModeInternal(int i) {
    synchronized (this.mSyncRoot) {
      DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
      if (deviceForDisplayLocked == null) {
        return null;
      }
      return deviceForDisplayLocked.getSystemPreferredDisplayModeLocked();
    }
  }

  public void setHdrConversionModeInternal(HdrConversionMode hdrConversionMode) {
    if (this.mInjector.getHdrOutputConversionSupport()) {
      synchronized (this.mSyncRoot) {
        if (hdrConversionMode.getConversionMode() == 2
            && hdrConversionMode.getPreferredHdrOutputType() != -1) {
          throw new IllegalArgumentException(
              "preferredHdrOutputType must not be set if the conversion mode is"
                  + " HDR_CONVERSION_SYSTEM");
        }
        this.mHdrConversionMode = hdrConversionMode;
        storeHdrConversionModeLocked(hdrConversionMode);
        int[] iArr = null;
        int[] enabledAutoHdrTypesLocked =
            hdrConversionMode.getConversionMode() == 2 ? getEnabledAutoHdrTypesLocked() : null;
        int conversionMode = hdrConversionMode.getConversionMode();
        int preferredHdrOutputType = hdrConversionMode.getPreferredHdrOutputType();
        HdrConversionMode hdrConversionMode2 = this.mOverrideHdrConversionMode;
        if (hdrConversionMode2 == null) {
          if (conversionMode == 3 && preferredHdrOutputType == -1) {
            conversionMode = 1;
          }
          iArr = enabledAutoHdrTypesLocked;
        } else {
          conversionMode = hdrConversionMode2.getConversionMode();
          preferredHdrOutputType = this.mOverrideHdrConversionMode.getPreferredHdrOutputType();
        }
        this.mSystemPreferredHdrOutputType =
            this.mInjector.setHdrConversionMode(conversionMode, preferredHdrOutputType, iArr);
      }
    }
  }

  public HdrConversionMode getHdrConversionModeSettingInternal() {
    if (!this.mInjector.getHdrOutputConversionSupport()) {
      return HDR_CONVERSION_MODE_UNSUPPORTED;
    }
    synchronized (this.mSyncRoot) {
      HdrConversionMode hdrConversionMode = this.mHdrConversionMode;
      return hdrConversionMode != null ? hdrConversionMode : new HdrConversionMode(2);
    }
  }

  public HdrConversionMode getHdrConversionModeInternal() {
    if (!this.mInjector.getHdrOutputConversionSupport()) {
      return HDR_CONVERSION_MODE_UNSUPPORTED;
    }
    synchronized (this.mSyncRoot) {
      HdrConversionMode hdrConversionMode = this.mOverrideHdrConversionMode;
      if (hdrConversionMode == null) {
        hdrConversionMode = this.mHdrConversionMode;
      }
      if (hdrConversionMode != null && hdrConversionMode.getConversionMode() != 2) {
        return hdrConversionMode;
      }
      return new HdrConversionMode(2, this.mSystemPreferredHdrOutputType);
    }
  }

  public final int[] getSupportedHdrOutputTypesInternal() {
    if (this.mSupportedHdrOutputType == null) {
      this.mSupportedHdrOutputType = this.mInjector.getSupportedHdrOutputTypes();
    }
    return this.mSupportedHdrOutputType;
  }

  public void setShouldAlwaysRespectAppRequestedModeInternal(boolean z) {
    this.mDisplayModeDirector.setShouldAlwaysRespectAppRequestedMode(z);
  }

  public boolean shouldAlwaysRespectAppRequestedModeInternal() {
    return this.mDisplayModeDirector.shouldAlwaysRespectAppRequestedMode();
  }

  public void setRefreshRateSwitchingTypeInternal(int i) {
    this.mDisplayModeDirector.setModeSwitchingType(i);
  }

  public int getRefreshRateSwitchingTypeInternal() {
    return this.mDisplayModeDirector.getModeSwitchingType();
  }

  public final DisplayDecorationSupport getDisplayDecorationSupportInternal(int i) {
    IBinder displayToken = getDisplayToken(i);
    if (displayToken == null) {
      return null;
    }
    return SurfaceControl.getDisplayDecorationSupport(displayToken);
  }

  public final void setBrightnessConfigurationForDisplayInternal(
      BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) {
    validateBrightnessConfiguration(brightnessConfiguration);
    int userSerialNumber = getUserManager().getUserSerialNumber(i);
    synchronized (this.mSyncRoot) {
      try {
        DisplayDevice byUniqueIdLocked = this.mDisplayDeviceRepo.getByUniqueIdLocked(str);
        if (byUniqueIdLocked == null) {
          return;
        }
        if (this.mLogicalDisplayMapper.getDisplayLocked(byUniqueIdLocked) != null
            && this.mLogicalDisplayMapper
                    .getDisplayLocked(byUniqueIdLocked)
                    .getDisplayInfoLocked()
                    .type
                == 1
            && brightnessConfiguration != null) {
          FrameworkStatsLog.write(
              FrameworkStatsLog.BRIGHTNESS_CONFIGURATION_UPDATED,
              (float[]) brightnessConfiguration.getCurve().first,
              (float[]) brightnessConfiguration.getCurve().second,
              str);
        }
        this.mPersistentDataStore.setBrightnessConfigurationForDisplayLocked(
            brightnessConfiguration, byUniqueIdLocked, userSerialNumber, str2);
        this.mPersistentDataStore.saveIfNeeded();
        if (i != this.mCurrentUserId) {
          return;
        }
        DisplayPowerControllerInterface dpcFromUniqueIdLocked = getDpcFromUniqueIdLocked(str);
        if (dpcFromUniqueIdLocked != null) {
          dpcFromUniqueIdLocked.setBrightnessConfiguration(brightnessConfiguration, true);
        }
      } finally {
        this.mPersistentDataStore.saveIfNeeded();
      }
    }
  }

  public final DisplayPowerControllerInterface getDpcFromUniqueIdLocked(String str) {
    LogicalDisplay displayLocked =
        this.mLogicalDisplayMapper.getDisplayLocked(
            this.mDisplayDeviceRepo.getByUniqueIdLocked(str));
    if (displayLocked == null) {
      return null;
    }
    return (DisplayPowerControllerInterface)
        this.mDisplayPowerControllers.get(displayLocked.getDisplayIdLocked());
  }

  public void validateBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
    if (brightnessConfiguration != null
        && isBrightnessConfigurationTooDark(brightnessConfiguration)) {
      Slog.m74e("DisplayManagerService", "brightness curve is too dark");
    }
  }

  public final boolean isBrightnessConfigurationTooDark(
      BrightnessConfiguration brightnessConfiguration) {
    Pair curve = brightnessConfiguration.getCurve();
    float[] fArr = (float[]) curve.first;
    float[] fArr2 = (float[]) curve.second;
    for (int i = 0; i < fArr.length; i++) {
      if (fArr2[i] < this.mMinimumBrightnessSpline.interpolate(fArr[i])) {
        return true;
      }
    }
    return false;
  }

  public final void loadBrightnessConfigurations() {
    final int userSerialNumber = getUserManager().getUserSerialNumber(this.mContext.getUserId());
    synchronized (this.mSyncRoot) {
      this.mLogicalDisplayMapper.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.this.lambda$loadBrightnessConfigurations$12(
                  userSerialNumber, (LogicalDisplay) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$loadBrightnessConfigurations$12(
      int i, LogicalDisplay logicalDisplay) {
    DisplayPowerControllerInterface displayPowerControllerInterface;
    BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked =
        getBrightnessConfigForDisplayWithPdsFallbackLocked(
            logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(), i);
    if (brightnessConfigForDisplayWithPdsFallbackLocked == null
        || (displayPowerControllerInterface =
                (DisplayPowerControllerInterface)
                    this.mDisplayPowerControllers.get(logicalDisplay.getDisplayIdLocked()))
            == null) {
      return;
    }
    displayPowerControllerInterface.setBrightnessConfiguration(
        brightnessConfigForDisplayWithPdsFallbackLocked, false);
  }

  public final void performTraversalLocked(
      final SurfaceControl.Transaction transaction, final SparseArray sparseArray) {
    clearViewportsLocked();
    updateEnabledDexDisplayLocked();
    this.mLogicalDisplayMapper.forEachLocked(
        new Consumer() { // from class:
                         // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayManagerService.this.lambda$performTraversalLocked$13(
                sparseArray, transaction, (LogicalDisplay) obj);
          }
        });
    if (this.mInputManagerInternal != null) {
      this.mHandler.sendEmptyMessage(5);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$performTraversalLocked$13(
      SparseArray sparseArray,
      SurfaceControl.Transaction transaction,
      LogicalDisplay logicalDisplay) {
    DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
    SurfaceControl.Transaction transaction2 =
        (SurfaceControl.Transaction)
            sparseArray.get(logicalDisplay.getDisplayIdLocked(), transaction);
    if (primaryDisplayDeviceLocked != null) {
      configureDisplayLocked(transaction2, primaryDisplayDeviceLocked);
      primaryDisplayDeviceLocked.performTraversalLocked(transaction2);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:24:0x0072 A[Catch: all -> 0x00b2, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:9:0x0011, B:11:0x0019, B:15:0x0026, B:17:0x0030, B:18:0x0054, B:22:0x006c, B:24:0x0072, B:26:0x0077, B:29:0x007e, B:30:0x0083, B:32:0x0087, B:34:0x0089, B:38:0x0091, B:39:0x00b0, B:44:0x00a7, B:46:0x0035), top: B:3:0x0007 }] */
  /* JADX WARN: Removed duplicated region for block: B:29:0x007e A[Catch: all -> 0x00b2, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:9:0x0011, B:11:0x0019, B:15:0x0026, B:17:0x0030, B:18:0x0054, B:22:0x006c, B:24:0x0072, B:26:0x0077, B:29:0x007e, B:30:0x0083, B:32:0x0087, B:34:0x0089, B:38:0x0091, B:39:0x00b0, B:44:0x00a7, B:46:0x0035), top: B:3:0x0007 }] */
  /* JADX WARN: Removed duplicated region for block: B:32:0x0087 A[Catch: all -> 0x00b2, DONT_GENERATE, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:9:0x0011, B:11:0x0019, B:15:0x0026, B:17:0x0030, B:18:0x0054, B:22:0x006c, B:24:0x0072, B:26:0x0077, B:29:0x007e, B:30:0x0083, B:32:0x0087, B:34:0x0089, B:38:0x0091, B:39:0x00b0, B:44:0x00a7, B:46:0x0035), top: B:3:0x0007 }] */
  /* JADX WARN: Removed duplicated region for block: B:34:0x0089 A[Catch: all -> 0x00b2, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000f, B:9:0x0011, B:11:0x0019, B:15:0x0026, B:17:0x0030, B:18:0x0054, B:22:0x006c, B:24:0x0072, B:26:0x0077, B:29:0x007e, B:30:0x0083, B:32:0x0087, B:34:0x0089, B:38:0x0091, B:39:0x00b0, B:44:0x00a7, B:46:0x0035), top: B:3:0x0007 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void setDisplayPropertiesInternal(
      int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4) {
    boolean z5;
    int i3;
    boolean z6;
    HdrConversionMode hdrConversionMode;
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null) {
        return;
      }
      if (displayLocked.hasContentLocked() != z) {
        displayLocked.setHasContentLocked(z);
        z5 = true;
      } else {
        z5 = false;
      }
      if (i2 == 0 && f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        Display.Mode findDefaultModeByRefreshRate =
            displayLocked.getDisplayInfoLocked().findDefaultModeByRefreshRate(f);
        if (findDefaultModeByRefreshRate != null) {
          i3 = findDefaultModeByRefreshRate.getModeId();
          this.mDisplayModeDirector.getAppRequestObserver().setAppRequest(i, i3, f2, f3);
          z6 = !isMinimalPostProcessingAllowed() && z2;
          if (displayLocked.getRequestedMinimalPostProcessingLocked() != z6) {
            displayLocked.setRequestedMinimalPostProcessingLocked(z6);
            r7 = z6 ? hdrConversionIntroducesLatencyLocked() : false;
            z5 = true;
          }
          if (z5) {
            scheduleTraversalLocked(z4);
          }
          hdrConversionMode = this.mHdrConversionMode;
          if (hdrConversionMode != null) {
            return;
          }
          HdrConversionMode hdrConversionMode2 = this.mOverrideHdrConversionMode;
          if (hdrConversionMode2 == null && (z3 || r7)) {
            this.mOverrideHdrConversionMode = new HdrConversionMode(1);
            setHdrConversionModeInternal(this.mHdrConversionMode);
            handleLogicalDisplayChangedLocked(displayLocked);
          } else if (hdrConversionMode2 != null && !z3 && !r7) {
            this.mOverrideHdrConversionMode = null;
            setHdrConversionModeInternal(hdrConversionMode);
            handleLogicalDisplayChangedLocked(displayLocked);
          }
          return;
        }
        Slog.m74e(
            "DisplayManagerService",
            "Couldn't find a mode for the requestedRefreshRate: " + f + " on Display: " + i);
      }
      i3 = i2;
      this.mDisplayModeDirector.getAppRequestObserver().setAppRequest(i, i3, f2, f3);
      if (isMinimalPostProcessingAllowed()) {}
      if (displayLocked.getRequestedMinimalPostProcessingLocked() != z6) {}
      if (z5) {}
      hdrConversionMode = this.mHdrConversionMode;
      if (hdrConversionMode != null) {}
    }
  }

  public final void setDisplayOffsetsInternal(int i, int i2, int i3) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null) {
        return;
      }
      if (displayLocked.getDisplayOffsetXLocked() != i2
          || displayLocked.getDisplayOffsetYLocked() != i3) {
        displayLocked.setDisplayOffsetsLocked(i2, i3);
        scheduleTraversalLocked(false);
      }
    }
  }

  public final void setDisplayScalingDisabledInternal(int i, boolean z) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null) {
        return;
      }
      if (displayLocked.isDisplayScalingDisabled() != z) {
        displayLocked.setDisplayScalingDisabledLocked(z);
        scheduleTraversalLocked(false);
      }
    }
  }

  public final void setDisplayAccessUIDsInternal(SparseArray sparseArray) {
    synchronized (this.mSyncRoot) {
      this.mDisplayAccessUIDs.clear();
      for (int size = sparseArray.size() - 1; size >= 0; size--) {
        this.mDisplayAccessUIDs.append(
            sparseArray.keyAt(size), (IntArray) sparseArray.valueAt(size));
      }
    }
  }

  public final boolean isUidPresentOnDisplayInternal(int i, int i2) {
    boolean z;
    synchronized (this.mSyncRoot) {
      IntArray intArray = (IntArray) this.mDisplayAccessUIDs.get(i2);
      z = (intArray == null || intArray.indexOf(i) == -1) ? false : true;
    }
    return z;
  }

  public final IBinder getDisplayToken(int i) {
    DisplayDevice primaryDisplayDeviceLocked;
    synchronized (this.mSyncRoot) {
      if (i == 2) {
        return getDisplayTokenForCurrentLayerStackLocked(i);
      }
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null
          || (primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked()) == null) {
        return null;
      }
      return primaryDisplayDeviceLocked.getDisplayTokenLocked();
    }
  }

  public final ScreenCapture.ScreenshotHardwareBuffer systemScreenshotInternal(int i) {
    synchronized (this.mSyncRoot) {
      IBinder displayToken = getDisplayToken(i);
      if (displayToken == null) {
        return null;
      }
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null) {
        return null;
      }
      DisplayInfo displayInfoLocked = displayLocked.getDisplayInfoLocked();
      return ScreenCapture.captureDisplay(
          new ScreenCapture.DisplayCaptureArgs.Builder(displayToken)
              .setSize(displayInfoLocked.getNaturalWidth(), displayInfoLocked.getNaturalHeight())
              .setUseIdentityTransform(true)
              .setCaptureSecureLayers(true)
              .setAllowProtected(true)
              .build());
    }
  }

  public final ScreenCapture.ScreenshotHardwareBuffer userScreenshotInternal(int i) {
    synchronized (this.mSyncRoot) {
      IBinder displayToken = getDisplayToken(i);
      if (displayToken == null) {
        return null;
      }
      return ScreenCapture.captureDisplay(
          new ScreenCapture.DisplayCaptureArgs.Builder(displayToken).build());
    }
  }

  public DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributesInternal(int i) {
    IBinder displayToken = getDisplayToken(i);
    if (displayToken == null) {
      return null;
    }
    return SurfaceControl.getDisplayedContentSamplingAttributes(displayToken);
  }

  public boolean setDisplayedContentSamplingEnabledInternal(int i, boolean z, int i2, int i3) {
    IBinder displayToken = getDisplayToken(i);
    if (displayToken == null) {
      return false;
    }
    return SurfaceControl.setDisplayedContentSamplingEnabled(displayToken, z, i2, i3);
  }

  public DisplayedContentSample getDisplayedContentSampleInternal(int i, long j, long j2) {
    IBinder displayToken = getDisplayToken(i);
    if (displayToken == null) {
      return null;
    }
    return SurfaceControl.getDisplayedContentSample(displayToken, j, j2);
  }

  public void resetBrightnessConfigurations() {
    this.mPersistentDataStore.setBrightnessConfigurationForUser(
        null, this.mContext.getUserId(), this.mContext.getPackageName());
    this.mLogicalDisplayMapper.forEachLocked(
        new Consumer() { // from class:
                         // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayManagerService.this.lambda$resetBrightnessConfigurations$14(
                (LogicalDisplay) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$resetBrightnessConfigurations$14(
      LogicalDisplay logicalDisplay) {
    if (logicalDisplay.getDisplayInfoLocked().type != 1) {
      return;
    }
    setBrightnessConfigurationForDisplayInternal(
        null,
        logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(),
        this.mContext.getUserId(),
        this.mContext.getPackageName());
  }

  public void setAutoBrightnessLoggingEnabled(boolean z) {
    synchronized (this.mSyncRoot) {
      DisplayPowerControllerInterface displayPowerControllerInterface =
          (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0);
      if (displayPowerControllerInterface != null) {
        displayPowerControllerInterface.setAutoBrightnessLoggingEnabled(z);
      }
    }
  }

  public void setDisplayWhiteBalanceLoggingEnabled(boolean z) {
    synchronized (this.mSyncRoot) {
      DisplayPowerControllerInterface displayPowerControllerInterface =
          (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0);
      if (displayPowerControllerInterface != null) {
        displayPowerControllerInterface.setDisplayWhiteBalanceLoggingEnabled(z);
      }
    }
  }

  public void setDisplayModeDirectorLoggingEnabled(boolean z) {
    synchronized (this.mSyncRoot) {
      this.mDisplayModeDirector.setLoggingEnabled(z);
    }
  }

  public Display.Mode getActiveDisplayModeAtStart(int i) {
    synchronized (this.mSyncRoot) {
      DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
      if (deviceForDisplayLocked == null) {
        return null;
      }
      return deviceForDisplayLocked.getActiveDisplayModeAtStartLocked();
    }
  }

  public void setAmbientColorTemperatureOverride(float f) {
    synchronized (this.mSyncRoot) {
      DisplayPowerControllerInterface displayPowerControllerInterface =
          (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0);
      if (displayPowerControllerInterface != null) {
        displayPowerControllerInterface.setAmbientColorTemperatureOverride(f);
      }
    }
  }

  public void setDockedAndIdleEnabled(boolean z, int i) {
    synchronized (this.mSyncRoot) {
      DisplayPowerControllerInterface displayPowerControllerInterface =
          (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i);
      if (displayPowerControllerInterface != null) {
        displayPowerControllerInterface.setAutomaticScreenBrightnessMode(z);
      }
    }
  }

  public final void clearViewportsLocked() {
    this.mViewports.clear();
  }

  public final Optional getViewportType(DisplayDeviceInfo displayDeviceInfo) {
    return getViewportType(displayDeviceInfo, false, false);
  }

  public final Optional getViewportType(
      DisplayDeviceInfo displayDeviceInfo, boolean z, boolean z2) {
    if (z) {
      if (CoreRune.MD_DEX_EMULATOR && z2) {
        return Optional.of(1);
      }
      return Optional.of(100);
    }
    int i = displayDeviceInfo.touch;
    if (i == 1) {
      return Optional.of(1);
    }
    if (i == 2) {
      return Optional.of(2);
    }
    if (i == 3 && !TextUtils.isEmpty(displayDeviceInfo.uniqueId)) {
      return Optional.of(3);
    }
    return Optional.empty();
  }

  /* JADX WARN: Removed duplicated region for block: B:26:0x00cd  */
  /* JADX WARN: Removed duplicated region for block: B:30:0x00dd  */
  /* JADX WARN: Removed duplicated region for block: B:32:0x00e1  */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
  /* JADX WARN: Removed duplicated region for block: B:47:0x0122  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void configureDisplayLocked(
      SurfaceControl.Transaction transaction, DisplayDevice displayDevice) {
    boolean z;
    boolean z2;
    DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
    LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(displayDevice);
    boolean z3 = (displayDeviceInfoLocked.flags & 128) != 0;
    if (this.mDisplayDeviceRepo.isDisplayDeviceForHiddenSpaceLocked(displayDeviceInfoLocked)
        && !z3
        && displayLocked != null
        && !displayLocked.hasContentLocked()) {
      displayLocked =
          this.mLogicalDisplayMapper.getHiddenDisplayLocked() != null
              ? this.mLogicalDisplayMapper.getHiddenDisplayLocked()
              : this.mLogicalDisplayMapper.getDisplayLocked(
                  displayDevice.getDisplayIdToMirrorLocked());
    }
    if (this.mEnabledDexDisplay != null) {
      if (CoreRune.MD_DEX_EMULATOR
          && this.mDexEmulator.needToConfigureDexDisplayLocked(displayDevice)) {
        displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(2);
        if ((displayDeviceInfoLocked.flags & 1) != 0) {
          z = true;
          z2 = true;
          if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT && this.mDesktopPrimaryDisplay != null) {
            DisplayDeviceInfo displayDeviceInfoLocked2 = displayDevice.getDisplayDeviceInfoLocked();
            if (!this.mDisplayDeviceRepo.isExternalDisplayDeviceForDexLocked(
                displayDeviceInfoLocked)) {
              displayLocked = this.mDesktopPrimaryDisplay;
            } else if (this.mDesktopSecondaryDisplay == null) {
              if (this.mNewDexEmulator.needToConfigureDexDisplayLocked(displayDevice)) {
                displayLocked = this.mDesktopPrimaryDisplay;
                z = (displayDeviceInfoLocked.flags & 1) != 0;
              } else if (this.mNewDexEmulator.needToConfigureDefaultDisplayLocked(
                  displayDeviceInfoLocked2)) {
                displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(0);
              }
            }
            z2 = true;
          }
          if (displayLocked == null) {
            Slog.m79w(
                "DisplayManagerService",
                "Missing logical display to use for physical display device: "
                    + displayDevice.getDisplayDeviceInfoLocked());
            return;
          }
          displayLocked.configureDisplayLocked(
              transaction, displayDevice, displayDeviceInfoLocked.state == 1);
          Optional viewportType = getViewportType(displayDeviceInfoLocked, z2, z);
          if (viewportType.isPresent()) {
            populateViewportLocked(
                ((Integer) viewportType.get()).intValue(),
                displayLocked.getDisplayIdLocked(),
                displayDevice,
                displayDeviceInfoLocked);
            return;
          }
          return;
        }
      } else if (CoreRune.MD_DEX_EMULATOR
          && this.mDexEmulator.needToConfigureDefaultDisplayLocked(displayDeviceInfoLocked)) {
        displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(0);
      } else if (this.mDisplayDeviceRepo.isExternalDisplayDeviceForDexLocked(
          displayDeviceInfoLocked)) {
        displayLocked = this.mEnabledDexDisplay;
        Slog.m76i(
            "DisplayManagerService",
            "configureDisplayLocked: dex displayId #"
                + displayLocked.getDisplayIdLocked()
                + " (state="
                + Display.stateToString(displayLocked.getDisplayInfoLocked().state)
                + "), target device="
                + displayDeviceInfoLocked);
      } else if (!this.mDisplayDeviceRepo.hasExternalDisplayDeviceForDexLocked()
          && (displayDeviceInfoLocked.flags & 1048576) != 0) {
        Slog.m76i(
            "DisplayManagerService",
            "configureDisplayLocked: use dex viewport without external device");
      }
      z = false;
      z2 = true;
      if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
        DisplayDeviceInfo displayDeviceInfoLocked22 = displayDevice.getDisplayDeviceInfoLocked();
        if (!this.mDisplayDeviceRepo.isExternalDisplayDeviceForDexLocked(
            displayDeviceInfoLocked)) {}
        z2 = true;
      }
      if (displayLocked == null) {}
    }
    z = false;
    z2 = false;
    if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {}
    if (displayLocked == null) {}
  }

  public final DisplayViewport getViewportLocked(int i, String str) {
    if (i != 1 && i != 2 && i != 3 && i != 100) {
      Slog.wtf(
          "DisplayManagerService",
          "Cannot call getViewportByTypeLocked for type " + DisplayViewport.typeToString(i));
      return null;
    }
    int size = this.mViewports.size();
    for (int i2 = 0; i2 < size; i2++) {
      DisplayViewport displayViewport = (DisplayViewport) this.mViewports.get(i2);
      if (displayViewport.type == i && str.equals(displayViewport.uniqueId)) {
        return displayViewport;
      }
    }
    DisplayViewport displayViewport2 = new DisplayViewport();
    displayViewport2.type = i;
    displayViewport2.uniqueId = str;
    this.mViewports.add(displayViewport2);
    return displayViewport2;
  }

  public final void populateViewportLocked(
      int i, int i2, DisplayDevice displayDevice, DisplayDeviceInfo displayDeviceInfo) {
    DisplayViewport viewportLocked = getViewportLocked(i, displayDeviceInfo.uniqueId);
    displayDevice.populateViewportLocked(viewportLocked);
    viewportLocked.valid = true;
    viewportLocked.displayId = i2;
    viewportLocked.isActive = Display.isActiveState(displayDeviceInfo.state);
  }

  public final void updateViewportPowerStateLocked(LogicalDisplay logicalDisplay) {
    DisplayDeviceInfo displayDeviceInfoLocked =
        logicalDisplay.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked();
    Optional viewportType = getViewportType(displayDeviceInfoLocked);
    if (viewportType.isPresent()) {
      Iterator it = this.mViewports.iterator();
      while (it.hasNext()) {
        DisplayViewport displayViewport = (DisplayViewport) it.next();
        if (displayViewport.type == ((Integer) viewportType.get()).intValue()
            && displayDeviceInfoLocked.uniqueId.equals(displayViewport.uniqueId)) {
          displayViewport.isActive = Display.isActiveState(displayDeviceInfoLocked.state);
        }
      }
      if (this.mInputManagerInternal != null) {
        this.mHandler.sendEmptyMessage(5);
      }
    }
  }

  public final void sendDisplayEventLocked(LogicalDisplay logicalDisplay, int i) {
    if (logicalDisplay.isEnabledLocked()) {
      this.mHandler.sendMessage(
          this.mHandler.obtainMessage(3, logicalDisplay.getDisplayIdLocked(), i));
    }
  }

  public final void sendDisplayGroupEvent(int i, int i2) {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(8, i, i2));
  }

  public final void sendDisplayEventFrameRateOverrideLocked(int i) {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i, 2));
  }

  public final void scheduleTraversalLocked(boolean z) {
    if (this.mPendingTraversal || this.mWindowManagerInternal == null) {
      return;
    }
    this.mPendingTraversal = true;
    if (z) {
      return;
    }
    this.mHandler.sendEmptyMessage(4);
  }

  public final boolean isUidCached(int i) {
    ActivityManagerInternal activityManagerInternal = this.mActivityManagerInternal;
    return activityManagerInternal != null
        && ActivityManager.RunningAppProcessInfo.procStateToImportance(
                activityManagerInternal.getUidProcessState(i))
            >= 400;
  }

  public final void deliverDisplayEvent(int i, ArraySet arraySet, int i2) {
    int i3;
    synchronized (this.mSyncRoot) {
      int size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i4 = 0; i4 < size; i4++) {
        if (arraySet == null
            || arraySet.contains(
                Integer.valueOf(((CallbackRecord) this.mCallbacks.valueAt(i4)).mUid))) {
          this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i4));
        }
      }
    }
    for (i3 = 0; i3 < this.mTempCallbacks.size(); i3++) {
      CallbackRecord callbackRecord = (CallbackRecord) this.mTempCallbacks.get(i3);
      int i5 = callbackRecord.mUid;
      int i6 = callbackRecord.mPid;
      if (isUidCached(i5)) {
        synchronized (this.mPendingCallbackSelfLocked) {
          SparseArray sparseArray = (SparseArray) this.mPendingCallbackSelfLocked.get(i5);
          if (sparseArray == null) {
            sparseArray = new SparseArray();
            this.mPendingCallbackSelfLocked.put(i5, sparseArray);
          }
          PendingCallback pendingCallback = (PendingCallback) sparseArray.get(i6);
          if (pendingCallback == null) {
            sparseArray.put(i6, new PendingCallback(callbackRecord, i, i2));
          } else {
            pendingCallback.addDisplayEvent(i, i2);
          }
        }
      } else {
        callbackRecord.notifyDisplayEventAsync(i, i2);
      }
    }
    this.mTempCallbacks.clear();
  }

  public final void deliverDisplayGroupEvent(int i, int i2) {
    Slog.m72d(
        "DisplayManagerService", "Delivering display group event: groupId=" + i + ", event=" + i2);
    if (i2 == 1) {
      Iterator it = this.mDisplayGroupListeners.iterator();
      while (it.hasNext()) {
        ((DisplayManagerInternal.DisplayGroupListener) it.next()).onDisplayGroupAdded(i);
      }
    } else if (i2 == 2) {
      Iterator it2 = this.mDisplayGroupListeners.iterator();
      while (it2.hasNext()) {
        ((DisplayManagerInternal.DisplayGroupListener) it2.next()).onDisplayGroupChanged(i);
      }
    } else {
      if (i2 != 3) {
        return;
      }
      Iterator it3 = this.mDisplayGroupListeners.iterator();
      while (it3.hasNext()) {
        ((DisplayManagerInternal.DisplayGroupListener) it3.next()).onDisplayGroupRemoved(i);
      }
    }
  }

  public final void addPresentationDisplay(PresentationDisplay presentationDisplay) {
    if (this.mPresentationDisplays.contains(presentationDisplay)) {
      Slog.m79w("DisplayManagerService", "Presentation has been added already");
      return;
    }
    if (!this.mPresentationDisplays.add(presentationDisplay)) {
      Slog.m79w(
          "DisplayManagerService",
          "Failed to add presentation("
              + presentationDisplay.displayId
              + ", "
              + presentationDisplay.packageName
              + ")");
      return;
    }
    if (this.mPresentationDisplays.size() == 1) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.this.lambda$addPresentationDisplay$15();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addPresentationDisplay$15() {
    this.mContext.sendBroadcastAsUser(
        new Intent("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW"), UserHandle.ALL);
  }

  public final void removePresentationDisplay(PresentationDisplay presentationDisplay) {
    if (!this.mPresentationDisplays.contains(presentationDisplay)) {
      Slog.m79w("DisplayManagerService", "Presentation is not included in Set");
      return;
    }
    if (!this.mPresentationDisplays.remove(presentationDisplay)) {
      Slog.m79w(
          "DisplayManagerService",
          "Failed to remove presentation("
              + presentationDisplay.displayId
              + ", "
              + presentationDisplay.packageName
              + ")");
      return;
    }
    if (this.mPresentationDisplays.isEmpty()) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.this.lambda$removePresentationDisplay$16();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removePresentationDisplay$16() {
    this.mContext.sendBroadcastAsUser(
        new Intent("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW"), UserHandle.ALL);
  }

  public final void deliverPresentationDisplayInfoEvent(int i, int i2, String str) {
    PresentationDisplay presentationDisplay = new PresentationDisplay(i, str);
    synchronized (this.mSyncRoot) {
      try {
        if (i2 == 1) {
          addPresentationDisplay(presentationDisplay);
        } else if (i2 == 0) {
          removePresentationDisplay(presentationDisplay);
        }
      } catch (Throwable th) {
        throw th;
      }
    }
  }

  public final void deliverDisplayVolumeEvent(int i, Bundle bundle) {
    int size;
    int i2;
    synchronized (this.mSyncRoot) {
      size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((CallbackRecord) this.mTempCallbacks.get(i2)).notifyDisplayVolumeEventAsync(i, bundle);
    }
    this.mTempCallbacks.clear();
    VolumeController volumeControllerInstance =
        this.mWifiDisplayAdapter.getVolumeControllerInstance();
    this.mVolumeController = volumeControllerInstance;
    if (volumeControllerInstance != null) {
      volumeControllerInstance.notifyDisplayVolumeEvnet(bundle);
    }
  }

  public final void deliverAsyncBinderBufferFillEvent(int i, Bundle bundle) {
    int size;
    int i2;
    synchronized (this.mSyncRoot) {
      size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((CallbackRecord) this.mTempCallbacks.get(i2)).notifyAsyncBinderBufferFillEvent(i, bundle);
    }
    this.mTempCallbacks.clear();
  }

  public final void deliverDisplayVolumeKeyEvent(int i) {
    int size;
    int i2;
    synchronized (this.mSyncRoot) {
      size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((CallbackRecord) this.mTempCallbacks.get(i2)).notifyDisplayVolumeKeyEventAsync(i);
    }
    this.mTempCallbacks.clear();
  }

  public final void deliverWifiDisplayParameterEvent(int i, List list) {
    int size;
    int i2;
    synchronized (this.mSyncRoot) {
      size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((CallbackRecord) this.mTempCallbacks.get(i2)).notifyWifiDisplayParameterEventAsync(i, list);
    }
    this.mTempCallbacks.clear();
  }

  public final void initializeWifiDisplayMcfManager() {
    WifiDisplayAdapter wifiDisplayAdapter = this.mWifiDisplayAdapter;
    if (wifiDisplayAdapter != null) {
      wifiDisplayAdapter.initializeMcfManager();
    }
  }

  public final void deliverDeviceEvent(Bundle bundle, int i) {
    int size;
    int i2;
    Slog.m72d("DisplayManagerService", "Delivering device event=" + i);
    synchronized (this.mSyncRoot) {
      size = this.mCallbacks.size();
      this.mTempCallbacks.clear();
      for (int i3 = 0; i3 < size; i3++) {
        this.mTempCallbacks.add((CallbackRecord) this.mCallbacks.valueAt(i3));
      }
    }
    for (i2 = 0; i2 < size; i2++) {
      ((CallbackRecord) this.mTempCallbacks.get(i2)).notifyDeviceEventAsync(bundle, i);
    }
    this.mTempCallbacks.clear();
  }

  public final IMediaProjectionManager getProjectionService() {
    if (this.mProjectionService == null) {
      this.mProjectionService = this.mInjector.getProjectionService();
    }
    return this.mProjectionService;
  }

  public final UserManager getUserManager() {
    return (UserManager) this.mContext.getSystemService(UserManager.class);
  }

  public final void dumpInternal(final PrintWriter printWriter) {
    BrightnessTracker brightnessTracker;
    printWriter.println("DISPLAY MANAGER (dumpsys display)");
    synchronized (this.mSyncRoot) {
      brightnessTracker = this.mBrightnessTracker;
      printWriter.println("  mSafeMode=" + this.mSafeMode);
      printWriter.println("  mPendingTraversal=" + this.mPendingTraversal);
      printWriter.println("  mViewports=" + this.mViewports);
      printWriter.println(
          "  mDefaultDisplayDefaultColorMode=" + this.mDefaultDisplayDefaultColorMode);
      printWriter.println("  mWifiDisplayScanRequestCount=" + this.mWifiDisplayScanRequestCount);
      printWriter.println("  mStableDisplaySize=" + this.mStableDisplaySize);
      printWriter.println("  mMinimumBrightnessCurve=" + this.mMinimumBrightnessCurve);
      if (this.mUserPreferredMode != null) {
        printWriter.println(" mUserPreferredMode=" + this.mUserPreferredMode);
      }
      printWriter.println();
      if (!this.mAreUserDisabledHdrTypesAllowed) {
        printWriter.println("  mUserDisabledHdrTypes: size=" + this.mUserDisabledHdrTypes.length);
        int[] iArr = this.mUserDisabledHdrTypes;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
          printWriter.println("  " + iArr[i]);
        }
      }
      if (this.mHdrConversionMode != null) {
        printWriter.println("  mHdrConversionMode=" + this.mHdrConversionMode);
      }
      printWriter.println();
      int size = this.mDisplayStates.size();
      printWriter.println("Display States: size=" + size);
      for (int i2 = 0; i2 < size; i2++) {
        int keyAt = this.mDisplayStates.keyAt(i2);
        int valueAt = this.mDisplayStates.valueAt(i2);
        BrightnessPair brightnessPair = (BrightnessPair) this.mDisplayBrightnesses.valueAt(i2);
        printWriter.println("  Display Id=" + keyAt);
        printWriter.println("  Display State=" + Display.stateToString(valueAt));
        printWriter.println("  Display Brightness=" + brightnessPair.brightness);
        printWriter.println("  Display SdrBrightness=" + brightnessPair.sdrBrightness);
      }
      printWriter.println();
      int size2 = this.mDisplayStateLimitLocks.size();
      printWriter.println("Display State Limit Locks: size=" + size2);
      for (int i3 = 0; i3 < size2; i3++) {
        printWriter.println("  " + i3 + ": " + this.mDisplayStateLimitLocks.get(i3));
      }
      printWriter.println();
      int size3 = this.mDisplayStateLimits.size();
      printWriter.println("Display State Limits: size=" + size3);
      for (int i4 = 0; i4 < size3; i4++) {
        int keyAt2 = this.mDisplayStateLimits.keyAt(i4);
        int valueAt2 = this.mDisplayStateLimits.valueAt(i4);
        printWriter.println("  Display Id=" + keyAt2);
        printWriter.println("  Display State Limit=" + Display.stateToString(valueAt2));
      }
      final PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
      indentingPrintWriter.increaseIndent();
      printWriter.println();
      printWriter.println("Display Adapters: size=" + this.mDisplayAdapters.size());
      Iterator it = this.mDisplayAdapters.iterator();
      while (it.hasNext()) {
        DisplayAdapter displayAdapter = (DisplayAdapter) it.next();
        printWriter.println("  " + displayAdapter.getName());
        displayAdapter.dumpLocked(indentingPrintWriter);
      }
      printWriter.println();
      printWriter.println("Display Devices: size=" + this.mDisplayDeviceRepo.sizeLocked());
      this.mDisplayDeviceRepo.forEachLocked(
          new Consumer() { // from class:
                           // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayManagerService.lambda$dumpInternal$17(
                  printWriter, indentingPrintWriter, (DisplayDevice) obj);
            }
          });
      printWriter.println();
      this.mLogicalDisplayMapper.dumpLocked(printWriter);
      int size4 = this.mCallbacks.size();
      printWriter.println();
      printWriter.println("Callbacks: size=" + size4);
      for (int i5 = 0; i5 < size4; i5++) {
        CallbackRecord callbackRecord = (CallbackRecord) this.mCallbacks.valueAt(i5);
        printWriter.println(
            "  "
                + i5
                + ": mPid="
                + callbackRecord.mPid
                + ", mWifiDisplayScanRequested="
                + callbackRecord.mWifiDisplayScanRequested
                + ", mWifiDisplayScanRequestedTime="
                + callbackRecord.mWifiDisplayScanRequestedTime);
      }
      int size5 = this.mDisplayPowerControllers.size();
      printWriter.println();
      printWriter.println("Display Power Controllers: size=" + size5);
      for (int i6 = 0; i6 < size5; i6++) {
        ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.valueAt(i6))
            .dump(printWriter);
      }
      printWriter.println();
      this.mPersistentDataStore.dump(printWriter);
      int size6 = this.mDisplayBrightnessListeners.size();
      if (size6 > 0) {
        printWriter.println();
        printWriter.println("mDisplayBrightnessListeners: size=" + size6);
        this.mDisplayBrightnessListeners.forEach(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda6
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.lambda$dumpInternal$18(
                    printWriter, (DisplayManagerInternal.DisplayBrightnessListener) obj);
              }
            });
      }
      int size7 = this.mDisplayStateListeners.size();
      if (size7 > 0) {
        printWriter.println();
        printWriter.println("mDisplayStateListenerInfo: size=" + size7);
        this.mDisplayStateListeners.forEach(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda7
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.lambda$dumpInternal$19(
                    printWriter, (DisplayManagerInternal.DisplayStateListener) obj);
              }
            });
      }
      printWriter.println("VRR request about brightness animation:");
      printWriter.println("  mBrightnessAnimStarted=" + this.mBrightnessAnimStarted);
      printWriter.println(
          "  mBrightnessAnimRefreshRateToken=" + this.mBrightnessAnimRefreshRateToken);
      int size8 = this.mDisplayWindowPolicyControllers.size();
      printWriter.println();
      printWriter.println("Display Window Policy Controllers: size=" + size8);
      for (int i7 = 0; i7 < size8; i7++) {
        printWriter.print(
            "Display "
                + this.mDisplayWindowPolicyControllers.keyAt(i7)
                + XmlUtils.STRING_ARRAY_SEPARATOR);
        ((DisplayWindowPolicyController)
                ((Pair) this.mDisplayWindowPolicyControllers.valueAt(i7)).second)
            .dump("  ", printWriter);
      }
    }
    if (brightnessTracker != null) {
      printWriter.println();
      brightnessTracker.dump(printWriter);
    }
    printWriter.println();
    this.mDisplayModeDirector.dump(printWriter);
    this.mBrightnessSynchronizer.dump(printWriter);
  }

  public static /* synthetic */ void lambda$dumpInternal$17(
      PrintWriter printWriter,
      IndentingPrintWriter indentingPrintWriter,
      DisplayDevice displayDevice) {
    printWriter.println("  " + displayDevice.getDisplayDeviceInfoLocked());
    displayDevice.dumpLocked(indentingPrintWriter);
  }

  public static /* synthetic */ void lambda$dumpInternal$18(
      PrintWriter printWriter,
      DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
    printWriter.println("  " + displayBrightnessListener);
  }

  public static /* synthetic */ void lambda$dumpInternal$19(
      PrintWriter printWriter, DisplayManagerInternal.DisplayStateListener displayStateListener) {
    printWriter.println("  " + displayStateListener);
  }

  public static float[] getFloatArray(TypedArray typedArray) {
    int length = typedArray.length();
    float[] fArr = new float[length];
    for (int i = 0; i < length; i++) {
      fArr[i] = typedArray.getFloat(i, Float.NaN);
    }
    typedArray.recycle();
    return fArr;
  }

  public final float registerDisplayBrightnessListenerInternal(
      DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
    float f;
    synchronized (this.mSyncRoot) {
      if (displayBrightnessListener != null) {
        this.mDisplayBrightnessListeners.add(displayBrightnessListener);
      }
      f = ((BrightnessPair) this.mDisplayBrightnesses.get(0)).brightness;
    }
    return f;
  }

  public final void unregisterDisplayBrightnessListenerInternal(
      DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
    synchronized (this.mSyncRoot) {
      this.mDisplayBrightnessListeners.remove(displayBrightnessListener);
    }
  }

  public final void registerDisplayStateListenerInternal(
      DisplayManagerInternal.DisplayStateListener displayStateListener) {
    synchronized (this.mSyncRoot) {
      this.mDisplayStateListeners.add(displayStateListener);
    }
  }

  public final void unregisterDisplayStateListenerInternal(
      final DisplayManagerInternal.DisplayStateListener displayStateListener) {
    synchronized (this.mSyncRoot) {
      ArrayList arrayList = this.mDisplayStateListeners;
      Objects.requireNonNull(displayStateListener);
      arrayList.removeIf(
          new Predicate() { // from class:
                            // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean equals;
              equals =
                  displayStateListener.equals((DisplayManagerInternal.DisplayStateListener) obj);
              return equals;
            }
          });
    }
  }

  public final ArrayList getCopyOfArrayList(ArrayList arrayList) {
    if (arrayList == null) {
      Slog.m74e("DisplayManagerService", "getCopyOfArrayList: error : null");
      return null;
    }
    return new ArrayList(arrayList);
  }

  public final int getDesiredStateLimitLocked(
      ArrayList arrayList, int i, DisplayDeviceInfo displayDeviceInfo) {
    int i2 = 2;
    if (i != 2) {
      if (arrayList.size() != 0) {
        for (DisplayStatePriority displayStatePriority : DisplayStatePriority.values()) {
          int displayState = displayStatePriority.getDisplayState();
          if (hasDisplayStateInDisplayStateLimitLocks(displayState, arrayList)) {
            i2 = displayState;
            break;
          }
        }
      }
      i2 = 0;
    }
    int i3 = this.mDualScreenPolicy;
    if (i3 == 0 && (displayDeviceInfo.flags & 16777216) != 0) {
      return 0;
    }
    if (i3 == 1 && (displayDeviceInfo.flags & 16777216) == 0) {
      return 0;
    }
    return i2;
  }

  public final boolean hasDisplayStateInDisplayStateLimitLocks(int i, ArrayList arrayList) {
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      if (i == ((DisplayStateLimitLock) it.next()).mLastRequestedState) {
        return true;
      }
    }
    return false;
  }

  public final int findDisplayStateLimitLockIndex(IBinder iBinder) {
    int size = this.mDisplayStateLimitLocks.size();
    for (int i = 0; i < size; i++) {
      if (((DisplayStateLimitLock) this.mDisplayStateLimitLocks.get(i)).mLock == iBinder) {
        return i;
      }
    }
    return -1;
  }

  public final void setDisplayStateLimitInternal(IBinder iBinder, int i) {
    long uptimeMillis = SystemClock.uptimeMillis();
    final ArrayList arrayList = new ArrayList();
    synchronized (this.mRequestDisplayStateLock) {
      synchronized (this.mSyncRoot) {
        int findDisplayStateLimitLockIndex = findDisplayStateLimitLockIndex(iBinder);
        if (findDisplayStateLimitLockIndex < 0) {
          if (i == 0) {
            Slog.m72d(
                "DisplayManagerService", "setDisplayStateLimitInternal: sameRequest: unknown");
            return;
          }
          DisplayStateLimitLock displayStateLimitLock =
              new DisplayStateLimitLock(iBinder, i, uptimeMillis);
          try {
            iBinder.linkToDeath(displayStateLimitLock, 0);
            this.mDisplayStateLimitLocks.add(displayStateLimitLock);
          } catch (RemoteException unused) {
            throw new IllegalArgumentException("DisplayStateLimitLock is already dead.");
          }
        } else if (i == 0) {
          this.mDisplayStateLimitLocks.remove(findDisplayStateLimitLockIndex);
        } else if (i
            != ((DisplayStateLimitLock)
                    this.mDisplayStateLimitLocks.get(findDisplayStateLimitLockIndex))
                .mLastRequestedState) {
          ((DisplayStateLimitLock) this.mDisplayStateLimitLocks.get(findDisplayStateLimitLockIndex))
                  .mLastRequestedState =
              i;
        } else {
          Slog.m72d(
              "DisplayManagerService",
              "setDisplayStateLimitInternal: sameRequest: " + Display.stateToString(i));
          return;
        }
        this.mDisplayDeviceRepo.forEachLocked(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda12
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.this.lambda$setDisplayStateLimitInternal$20(
                    arrayList, (DisplayDevice) obj);
              }
            });
        arrayList.forEach(new DisplayManagerService$$ExternalSyntheticLambda13());
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setDisplayStateLimitInternal$20(
      List list, DisplayDevice displayDevice) {
    Runnable updateDisplayStateLocked;
    if (displayDevice.getDisplayDeviceInfoLocked().type != 1
        || (updateDisplayStateLocked = updateDisplayStateLocked(displayDevice)) == null) {
      return;
    }
    list.add(updateDisplayStateLocked);
  }

  public String getAmbientBrightnessInfo(float f) {
    String ambientBrightnessInfo;
    synchronized (this.mSyncRoot) {
      ambientBrightnessInfo =
          ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))
              .getAmbientBrightnessInfo(f);
    }
    return ambientBrightnessInfo;
  }

  public void doShortTermReset() {
    synchronized (this.mSyncRoot) {
      ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0)).doShortTermReset();
    }
  }

  public void setTestModeEnabled(boolean z) {
    synchronized (this.mSyncRoot) {
      ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))
          .setTestModeEnabled(z);
    }
  }

  public void addBrightnessWeights(float f, float f2, float f3, float f4) {
    synchronized (this.mSyncRoot) {
      ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))
          .addBrightnessWeights(f, f2, f3, f4);
    }
  }

  public void injectLux(SensorEvent sensorEvent) {
    synchronized (this.mSyncRoot) {
      ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))
          .injectLux(sensorEvent);
    }
  }

  public final void setBrightnessConfigurationForUserWithStatsInternal(
      BrightnessConfiguration brightnessConfiguration,
      int i,
      String str,
      List list,
      List list2,
      List list3) {
    validateBrightnessConfiguration(brightnessConfiguration);
    int userSerialNumber = getUserManager().getUserSerialNumber(i);
    synchronized (this.mSyncRoot) {
      try {
        try {
          this.mPersistentDataStore.setBrightnessConfigurationForUser(
              brightnessConfiguration, userSerialNumber, str, list, list2, list3);
          this.mPersistentDataStore.saveIfNeeded();
          ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))
              .setBrightnessConfiguration(brightnessConfiguration, true);
        } catch (Throwable th) {
          this.mPersistentDataStore.saveIfNeeded();
          throw th;
        }
      } catch (Throwable th2) {
        throw th2;
      }
    }
  }

  public final void sendDisplayBrightnessEventLocked(float f) {
    if (this.mDisplayBrightnessListeners.size() == 0) {
      return;
    }
    boolean z = false;
    if (PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V3) {
      if (f % 100.0f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        z = true;
      } else {
        f /= 100.0f;
      }
    }
    if (z) {
      return;
    }
    Iterator it = this.mDisplayBrightnessListeners.iterator();
    while (it.hasNext()) {
      ((DisplayManagerInternal.DisplayBrightnessListener) it.next()).onChanged(f);
    }
  }

  public static boolean isResolutionAndRefreshRateValid(Display.Mode mode) {
    return mode.getPhysicalWidth() > 0
        && mode.getPhysicalHeight() > 0
        && mode.getRefreshRate() > DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  class Injector {
    public long getDefaultDisplayDelayTimeout() {
      return 10000L;
    }

    public VirtualDisplayAdapter getVirtualDisplayAdapter(
        SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener) {
      return new VirtualDisplayAdapter(syncRoot, context, handler, listener);
    }

    public LocalDisplayAdapter getLocalDisplayAdapter(
        SyncRoot syncRoot, Context context, Handler handler, DisplayAdapter.Listener listener) {
      return new LocalDisplayAdapter(syncRoot, context, handler, listener);
    }

    public int setHdrConversionMode(int i, int i2, int[] iArr) {
      return DisplayControl.setHdrConversionMode(i, i2, iArr);
    }

    public int[] getSupportedHdrOutputTypes() {
      return DisplayControl.getSupportedHdrOutputTypes();
    }

    public int[] getHdrOutputTypesWithLatency() {
      return DisplayControl.getHdrOutputTypesWithLatency();
    }

    public boolean getHdrOutputConversionSupport() {
      return DisplayControl.getHdrOutputConversionSupport();
    }

    public IMediaProjectionManager getProjectionService() {
      return IMediaProjectionManager.Stub.asInterface(
          ServiceManager.getService("media_projection"));
    }
  }

  public DisplayDeviceInfo getDisplayDeviceInfoInternal(int i) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
      if (displayLocked == null) {
        return null;
      }
      return displayLocked.getPrimaryDisplayDeviceLocked().getDisplayDeviceInfoLocked();
    }
  }

  public Surface getVirtualDisplaySurfaceInternal(IBinder iBinder) {
    synchronized (this.mSyncRoot) {
      VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
      if (virtualDisplayAdapter == null) {
        return null;
      }
      return virtualDisplayAdapter.getVirtualDisplaySurfaceLocked(iBinder);
    }
  }

  public final void initializeDisplayPowerControllersLocked() {
    this.mLogicalDisplayMapper.forEachLocked(
        new Consumer() { // from class:
                         // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda15
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayManagerService.this.addDisplayPowerControllerLocked((LogicalDisplay) obj);
          }
        });
  }

  public final DisplayPowerControllerInterface addDisplayPowerControllerLocked(
      final LogicalDisplay logicalDisplay) {
    DisplayPowerControllerInterface displayPowerController;
    if (this.mPowerHandler == null) {
      return null;
    }
    if (this.mBrightnessTracker == null && logicalDisplay.getDisplayIdLocked() == 0) {
      this.mBrightnessTracker = new BrightnessTracker(this.mContext, null);
    }
    BrightnessSetting brightnessSetting =
        new BrightnessSetting(this.mPersistentDataStore, logicalDisplay, this.mSyncRoot);
    HighBrightnessModeMetadata highBrightnessModeMetadataLocked =
        this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay);
    if (highBrightnessModeMetadataLocked == null) {
      Slog.wtf(
          "DisplayManagerService",
          "High Brightness Mode Metadata is null in DisplayManagerService for display: "
              + logicalDisplay.getDisplayIdLocked());
      return null;
    }
    if (DeviceConfig.getBoolean(
        "display_manager", "use_newly_structured_display_power_controller", true)) {
      displayPowerController =
          new DisplayPowerController2(
              this.mContext,
              null,
              this.mDisplayPowerCallbacks,
              this.mPowerHandler,
              this.mSensorManager,
              this.mDisplayBlanker,
              logicalDisplay,
              this.mBrightnessTracker,
              brightnessSetting,
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.this.lambda$addDisplayPowerControllerLocked$21(
                      logicalDisplay);
                }
              },
              highBrightnessModeMetadataLocked,
              this.mBootCompleted,
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.this.lambda$addDisplayPowerControllerLocked$22(
                      logicalDisplay);
                }
              },
              new Consumer() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayManagerService.this.handleBrightnessAnimation(
                      ((Boolean) obj).booleanValue());
                }
              });
    } else {
      displayPowerController =
          new DisplayPowerController(
              this.mContext,
              null,
              this.mDisplayPowerCallbacks,
              this.mPowerHandler,
              this.mSensorManager,
              this.mDisplayBlanker,
              logicalDisplay,
              this.mBrightnessTracker,
              brightnessSetting,
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.this.lambda$addDisplayPowerControllerLocked$23(
                      logicalDisplay);
                }
              },
              highBrightnessModeMetadataLocked,
              this.mBootCompleted,
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.this.lambda$addDisplayPowerControllerLocked$24(
                      logicalDisplay);
                }
              },
              new Consumer() { // from class:
                               // com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayManagerService.this.handleBrightnessAnimation(
                      ((Boolean) obj).booleanValue());
                }
              });
    }
    this.mDisplayPowerControllers.append(
        logicalDisplay.getDisplayIdLocked(), displayPowerController);
    return displayPowerController;
  }

  /* renamed from: handleBrightnessChange, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
  public final void lambda$addDisplayPowerControllerLocked$23(LogicalDisplay logicalDisplay) {
    synchronized (this.mSyncRoot) {
      sendDisplayEventLocked(logicalDisplay, 4);
    }
  }

  /* renamed from: handleBrightnessModeChange, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
  public final void lambda$addDisplayPowerControllerLocked$24(LogicalDisplay logicalDisplay) {
    synchronized (this.mSyncRoot) {
      updateOtherInternalDisplayBrightnessLocked(logicalDisplay.getDisplayIdLocked());
    }
  }

  public final void handleBrightnessAnimation(boolean z) {
    synchronized (this.mSyncRoot) {
      if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN && this.mBrightnessAnimStarted != z) {
        Slog.m72d("DisplayManagerService", "handleBrightnessAnimation: started=" + z);
        this.mBrightnessAnimStarted = z;
        if (z) {
          if (PowerManagerUtil.SEC_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY) {
            this.mBrightnessAnimRefreshRateToken =
                this.mDisplayModeDirector
                    .getRefreshRateModeManager()
                    .getController()
                    .createPassiveModeToken(new Binder(), "BrightnessAnim");
          } else {
            this.mBrightnessAnimRefreshRateToken =
                this.mDisplayModeDirector
                    .getRefreshRateModeManager()
                    .getController()
                    .createRefreshRateMinLimitToken(new Binder(), 60, "BrightnessAnim");
          }
        } else {
          try {
            IRefreshRateToken iRefreshRateToken = this.mBrightnessAnimRefreshRateToken;
            if (iRefreshRateToken != null) {
              iRefreshRateToken.release();
            }
          } catch (RemoteException e) {
            Slog.m72d("DisplayManagerService", "Exception occur : " + e);
          }
        }
      }
    }
  }

  public final DisplayDevice getDeviceForDisplayLocked(int i) {
    LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
    if (displayLocked == null) {
      return null;
    }
    return displayLocked.getPrimaryDisplayDeviceLocked();
  }

  public final BrightnessConfiguration getBrightnessConfigForDisplayWithPdsFallbackLocked(
      String str, int i) {
    BrightnessConfiguration brightnessConfigurationForDisplayLocked =
        this.mPersistentDataStore.getBrightnessConfigurationForDisplayLocked(str, i);
    return brightnessConfigurationForDisplayLocked == null
        ? this.mPersistentDataStore.getBrightnessConfiguration(i)
        : brightnessConfigurationForDisplayLocked;
  }

  public final int updateDexDisplayStateInternal(boolean z) {
    boolean hasExternalDisplayDevice;
    float f;
    float f2;
    synchronized (this.mSyncRoot) {
      hasExternalDisplayDevice = this.mDisplayDeviceRepo.hasExternalDisplayDevice();
    }
    if (hasExternalDisplayDevice) {
      if (z) {
        this.mDisplayDeviceRepo.releaseHDMIWake();
      } else {
        this.mDisplayDeviceRepo.acquireHDMIWake();
      }
    }
    synchronized (this.mSyncRoot) {
      DisplayDevice dexDisplayDeviceLocked = this.mDisplayDeviceRepo.getDexDisplayDeviceLocked();
      DisplayDeviceInfo displayDeviceInfoLocked =
          dexDisplayDeviceLocked != null
              ? dexDisplayDeviceLocked.getDisplayDeviceInfoLocked()
              : null;
      if (displayDeviceInfoLocked != null && (displayDeviceInfoLocked.flags & 1048576) != 0) {
        LogicalDisplay displayLocked =
            this.mLogicalDisplayMapper.getDisplayLocked(dexDisplayDeviceLocked);
        if (displayLocked == null) {
          dexDisplayDeviceLocked.updateDexEnabledStateLocked(false);
          Slog.m79w(
              "DisplayManagerService",
              "updateDexDisplayStateInternalLocked: cannot found display: "
                  + displayDeviceInfoLocked);
          return -1;
        }
        int displayIdLocked = displayLocked.getDisplayIdLocked();
        BrightnessPair brightnessPair =
            (BrightnessPair) this.mDisplayBrightnesses.get(displayIdLocked);
        if (z && brightnessPair != null) {
          f = brightnessPair.brightness;
          f2 = brightnessPair.sdrBrightness;
        } else {
          f = displayDeviceInfoLocked.brightnessDefault;
          f2 = f;
        }
        if (z) {
          dexDisplayDeviceLocked.updateDexEnabledStateLocked(true);
          dexDisplayDeviceLocked.requestDisplayStateLocked(2, f, f2);
        } else {
          dexDisplayDeviceLocked.requestDisplayStateLocked(1, f, f2);
          dexDisplayDeviceLocked.updateDexEnabledStateLocked(false);
        }
        displayLocked.updateLocked(this.mDisplayDeviceRepo);
        sendDisplayEventLocked(displayLocked, 2);
        scheduleTraversalLocked(false);
        return displayIdLocked;
      }
      Slog.m79w(
          "DisplayManagerService",
          "updateDexDisplayStateInternal: abnormal device=" + displayDeviceInfoLocked);
      return -1;
    }
  }

  public final void updateEnabledDexDisplayLocked() {
    LogicalDisplay displayLocked =
        this.mDisplayDeviceRepo.isDexDisplayDeviceEnabledLocked()
            ? this.mLogicalDisplayMapper.getDisplayLocked(2)
            : null;
    if (this.mEnabledDexDisplay != displayLocked) {
      Slog.m72d(
          "DisplayManagerService",
          "updateEnabledDexDisplayLocked: prev="
              + this.mEnabledDexDisplay
              + ", next="
              + displayLocked);
      this.mEnabledDexDisplay = displayLocked;
    }
  }

  public final class DexEmulator {
    public boolean mDualOverlayEnabled;
    public boolean mDualSwitchEnabled;
    public boolean mSystemReady;

    public DexEmulator() {
      this.mDualSwitchEnabled = false;
      this.mDualOverlayEnabled = false;
      this.mSystemReady = false;
    }

    public final void onSystemReadyLocked() {
      this.mSystemReady = true;
      DisplayManagerService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$DexEmulator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.DexEmulator.this.lambda$onSystemReadyLocked$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReadyLocked$0() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        initDualOverlayStateLocked();
      }
    }

    public final boolean needToConfigureDexDisplayLocked(DisplayDevice displayDevice) {
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      if ((displayDeviceInfoLocked.flags & 1) != 0) {
        return this.mDualSwitchEnabled;
      }
      if (displayDeviceInfoLocked.type == 4 && !this.mDualSwitchEnabled) {
        return this.mDualOverlayEnabled
            && !DisplayManagerService.this.mDisplayDeviceRepo
                .hasExternalDisplayDeviceForDexLocked();
      }
      String str = displayDeviceInfoLocked.name;
      return str != null
          && str.contains("scrcpy")
          && displayDeviceInfoLocked.type == 5
          && (displayDeviceInfoLocked.flags & 16) != 0
          && displayDevice.getDisplayIdToMirrorLocked() == 2;
    }

    public final boolean needToConfigureDefaultDisplayLocked(DisplayDeviceInfo displayDeviceInfo) {
      return displayDeviceInfo.type == 4
          && this.mDualSwitchEnabled
          && this.mDualOverlayEnabled
          && !DisplayManagerService.this.mDisplayDeviceRepo.hasExternalDisplayDeviceForDexLocked();
    }

    public final void onLogicalDisplayAddedLocked(LogicalDisplay logicalDisplay) {
      if (isExternalLogicalDisplayForDexLocked(logicalDisplay.getDisplayInfoLocked())) {
        this.mDualOverlayEnabled = false;
        this.mDualSwitchEnabled = false;
        updateDualSwitchStateLocked();
        updateDualOverlayStateLocked();
      }
    }

    public final boolean isExternalLogicalDisplayForDexLocked(DisplayInfo displayInfo) {
      if (displayInfo.type != 2) {
        int i = displayInfo.flags;
        if ((134217728 & i) == 0 && (i & 67108864) == 0) {
          return false;
        }
      }
      return true;
    }

    public final boolean hasDualOverlaySettingsLocked() {
      if (!this.mSystemReady) {
        return false;
      }
      String string =
          Settings.Global.getString(
              DisplayManagerService.this.mContext.getContentResolver(), "overlay_display_devices");
      return "dex#1080x2220/320#2".equals(string) || "dex#1920x1080/320#0".equals(string);
    }

    public final void initDualOverlayStateLocked() {
      if (this.mDualOverlayEnabled != hasDualOverlaySettingsLocked()) {
        updateDualOverlayStateLocked();
      }
    }

    public final void updateDualOverlayStateLocked() {
      final String str;
      if (this.mSystemReady) {
        final ContentResolver contentResolver =
            DisplayManagerService.this.mContext.getContentResolver();
        if (this.mDualOverlayEnabled) {
          str = this.mDualSwitchEnabled ? "dex#1080x2220/320#2" : "dex#1920x1080/320#0";
        } else {
          str = "";
        }
        DisplayManagerService.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.display.DisplayManagerService$DexEmulator$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                Settings.Global.putString(contentResolver, "overlay_display_devices", str);
              }
            });
      }
    }

    public final void updateDualSwitchStateLocked() {
      LogicalDisplay dexLogicalDisplayLocked =
          DisplayManagerService.this.mLogicalDisplayMapper.getDexLogicalDisplayLocked();
      if (dexLogicalDisplayLocked != null) {
        dexLogicalDisplayLocked.mDualSwitchApplied = this.mDualSwitchEnabled;
      }
    }

    public final boolean dumpInternal(PrintWriter printWriter, String[] strArr) {
      if (strArr == null || strArr.length < 1) {
        return false;
      }
      final String str = strArr[0];
      boolean equals = "dual-switch".equals(str);
      boolean equals2 = "dual-overlay".equals(str);
      if (!equals && !equals2) {
        return false;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mDisplayDeviceRepo.getDexDisplayDeviceLocked() == null) {
          printWriter.println("[cmd desktopmode on] before enable " + str);
          return true;
        }
        if (equals) {
          this.mDualSwitchEnabled = !this.mDualSwitchEnabled;
          updateDualSwitchStateLocked();
          if (this.mDualOverlayEnabled) {
            updateDualOverlayStateLocked();
          }
        } else if (equals2) {
          this.mDualOverlayEnabled = !this.mDualOverlayEnabled;
          updateDualOverlayStateLocked();
        }
        DisplayManagerService.this.scheduleTraversalLocked(false);
        DisplayManagerService.this.mUiHandler.post(
            new Runnable() { // from class:
                             // com.android.server.display.DisplayManagerService$DexEmulator$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                DisplayManagerService.DexEmulator.this.lambda$dumpInternal$2(str);
              }
            });
        return true;
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpInternal$2(String str) {
      Toast.makeText(DisplayManagerService.this.getUiContext(), str, 0).show();
    }
  }

  public final class NewDexEmulator {
    public boolean mDualSwitchEnabled;
    public boolean mOverlay2Enabled;
    public boolean mOverlayEnabled;
    public boolean mSystemReady;

    public NewDexEmulator() {
      this.mDualSwitchEnabled = false;
      this.mOverlayEnabled = false;
      this.mOverlay2Enabled = false;
      this.mSystemReady = false;
    }

    public final void onSystemReadyLocked() {
      this.mSystemReady = true;
      DisplayManagerService.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.display.DisplayManagerService$NewDexEmulator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              DisplayManagerService.NewDexEmulator.this.lambda$onSystemReadyLocked$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReadyLocked$0() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        initDualOverlayStateLocked();
      }
    }

    public final boolean needToConfigureDexDisplayLocked(DisplayDevice displayDevice) {
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      if ((displayDeviceInfoLocked.flags & 1) != 0) {
        return this.mDualSwitchEnabled;
      }
      return (displayDeviceInfoLocked.type != 4 || this.mDualSwitchEnabled)
          ? CoreRune.MT_NEW_DEX_EMULATOR && canUseScrcpyForDexDisplayLocked(displayDevice)
          : this.mOverlayEnabled
              && !DisplayManagerService.this.mDisplayDeviceRepo
                  .hasExternalDisplayDeviceForDexLocked();
    }

    public final boolean needToConfigureDefaultDisplayLocked(DisplayDeviceInfo displayDeviceInfo) {
      return displayDeviceInfo.type == 4
          && this.mDualSwitchEnabled
          && !DisplayManagerService.this.mDisplayDeviceRepo.hasExternalDisplayDeviceForDexLocked();
    }

    public final boolean canUseScrcpyForDexDisplayLocked(DisplayDevice displayDevice) {
      LogicalDisplay logicalDisplay = DisplayManagerService.this.mDesktopPrimaryDisplay;
      if (logicalDisplay == null) {
        return false;
      }
      int displayIdLocked = logicalDisplay.getDisplayIdLocked();
      DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
      String str = displayDeviceInfoLocked.name;
      return str != null
          && str.contains("scrcpy")
          && displayDeviceInfoLocked.type == 5
          && (displayDeviceInfoLocked.flags & 16) != 0
          && displayDevice.getDisplayIdToMirrorLocked() == displayIdLocked;
    }

    public final void onLogicalDisplayAddedLocked(LogicalDisplay logicalDisplay) {
      if (logicalDisplay.getDisplayInfoLocked().type == 2) {
        if (this.mOverlayEnabled) {
          updateOverlayState();
        }
        this.mDualSwitchEnabled = false;
      }
    }

    public final boolean hasDualOverlaySettingsLocked() {
      String string;
      return (!this.mSystemReady
              || (string =
                      Settings.Global.getString(
                          DisplayManagerService.this.mContext.getContentResolver(),
                          "overlay_display_devices"))
                  == null
              || string.isEmpty())
          ? false
          : true;
    }

    public final void initDualOverlayStateLocked() {
      if (this.mOverlayEnabled != hasDualOverlaySettingsLocked()) {
        updateOverlayState();
      }
    }

    public final void updateOverlayState() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (this.mSystemReady) {
          boolean z = !this.mOverlayEnabled;
          this.mOverlayEnabled = z;
          final String str = z ? "1920x1080/320" : "";
          DisplayManagerService.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$NewDexEmulator$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.NewDexEmulator.this.lambda$updateOverlayState$1(str);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateOverlayState$1(String str) {
      Settings.Global.putString(
          DisplayManagerService.this.mContext.getContentResolver(), "overlay_display_devices", str);
    }

    public final void updateDualOverlayState() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        boolean z = !this.mOverlay2Enabled;
        this.mOverlay2Enabled = z;
        final String str = z ? "1920x1080/320;1920x1080/320" : "";
        DisplayManagerService.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.display.DisplayManagerService$NewDexEmulator$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                DisplayManagerService.NewDexEmulator.this.lambda$updateDualOverlayState$2(str);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDualOverlayState$2(String str) {
      Settings.Global.putString(
          DisplayManagerService.this.mContext.getContentResolver(), "overlay_display_devices", str);
    }

    public final void switchOverlay() {
      final String str;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (this.mOverlayEnabled) {
          boolean z = !this.mDualSwitchEnabled;
          this.mDualSwitchEnabled = z;
          if (z) {
            str =
                "dex#1080x2220/320#"
                    + DisplayManagerService.this.mDesktopPrimaryDisplay.getDisplayIdLocked();
          } else {
            str = "1920x1080/320";
          }
          DisplayManagerService.this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.display.DisplayManagerService$NewDexEmulator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayManagerService.NewDexEmulator.this.lambda$switchOverlay$3(str);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchOverlay$3(String str) {
      Settings.Global.putString(
          DisplayManagerService.this.mContext.getContentResolver(), "overlay_display_devices", str);
    }
  }

  public final void updateDesktopDisplayStateInternal(boolean z, int i) {
    LogicalDisplay logicalDisplay;
    synchronized (this.mSyncRoot) {
      if (z) {
        logicalDisplay = this.mLogicalDisplayMapper.getDisplayLocked(i);
        this.mDesktopPrimaryDisplay = logicalDisplay;
      } else {
        logicalDisplay = null;
        this.mDesktopPrimaryDisplay = null;
      }
      if (logicalDisplay == null) {
        return;
      }
      logicalDisplay.updateLocked(this.mDisplayDeviceRepo);
      sendDisplayEventLocked(logicalDisplay, 2);
      scheduleTraversalLocked(false);
    }
  }

  public final int createSpegVirtualDisplayInternal(
      String str, int i, IVirtualDisplayCallback iVirtualDisplayCallback) {
    int createVirtualDisplayLocked;
    if (!CoreRune.SYSFW_APP_SPEG) {
      return -1;
    }
    int callingUid = Binder.getCallingUid();
    if (callingUid != 1000 && callingUid != 0) {
      Slog.m74e("SPEG", "Try to create display from unprivileged uid");
      return -1;
    }
    DisplayInfo displayInfoInternal = getDisplayInfoInternal(0, callingUid);
    VirtualDisplayConfig.Builder builder =
        new VirtualDisplayConfig.Builder(
            "SpegVirtualDisplay",
            displayInfoInternal.getNaturalWidth(),
            displayInfoInternal.getNaturalHeight(),
            displayInfoInternal.logicalDensityDpi);
    builder.setFlags(16777672);
    builder.setUniqueId(String.valueOf(i));
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mSyncRoot) {
        createVirtualDisplayLocked =
            createVirtualDisplayLocked(
                iVirtualDisplayCallback, null, i, str, null, null, 16777672, builder.build());
      }
      return createVirtualDisplayLocked;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final class DisplayManagerHandler extends Handler {
    public DisplayManagerHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      boolean z;
      int i = message.what;
      if (i != 25) {
        switch (i) {
          case 1:
            DisplayManagerService.this.registerDefaultDisplayAdapters();
            return;
          case 2:
            DisplayManagerService.this.registerAdditionalDisplayAdapters();
            return;
          case 3:
            DisplayManagerService.this.deliverDisplayEvent(message.arg1, null, message.arg2);
            return;
          case 4:
            DisplayManagerService.this.mWindowManagerInternal.requestTraversalFromDisplayManager();
            return;
          case 5:
            synchronized (DisplayManagerService.this.mSyncRoot) {
              z =
                  !DisplayManagerService.this.mTempViewports.equals(
                      DisplayManagerService.this.mViewports);
              if (z) {
                DisplayManagerService.this.mTempViewports.clear();
                Iterator it = DisplayManagerService.this.mViewports.iterator();
                while (it.hasNext()) {
                  DisplayManagerService.this.mTempViewports.add(
                      ((DisplayViewport) it.next()).makeCopy());
                }
              }
            }
            if (z) {
              DisplayManagerService.this.mInputManagerInternal.setDisplayViewports(
                  DisplayManagerService.this.mTempViewports);
              return;
            }
            return;
          case 6:
            DisplayManagerService.this.loadBrightnessConfigurations();
            return;
          case 7:
            synchronized (DisplayManagerService.this.mSyncRoot) {
              LogicalDisplay displayLocked =
                  DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(message.arg1);
              if (displayLocked != null) {
                ArraySet pendingFrameRateOverrideUids =
                    displayLocked.getPendingFrameRateOverrideUids();
                displayLocked.clearPendingFrameRateOverrideUids();
                DisplayManagerService.this.deliverDisplayEvent(
                    message.arg1, pendingFrameRateOverrideUids, message.arg2);
              }
            }
            return;
          case 8:
            DisplayManagerService.this.deliverDisplayGroupEvent(message.arg1, message.arg2);
            return;
          case 9:
            DisplayManagerService.this.mWindowManagerInternal.onDisplayManagerReceivedDeviceState(
                message.arg1);
            return;
          default:
            switch (i) {
              case 20:
                DisplayManagerService.this.deliverDeviceEvent(message.getData(), message.arg1);
                return;
              case 21:
                DisplayManagerService.this.deliverPresentationDisplayInfoEvent(
                    message.arg1, message.arg2, (String) message.obj);
                return;
              case 22:
                DisplayManagerService.this.deliverDisplayVolumeEvent(
                    message.arg1, message.getData());
                return;
              case 23:
                DisplayManagerService.this.deliverDisplayVolumeKeyEvent(message.arg1);
                return;
              default:
                switch (i) {
                  case 28:
                    DisplayManagerService.this.initializeWifiDisplayMcfManager();
                    return;
                  case 29:
                    DisplayManagerService.this.deliverAsyncBinderBufferFillEvent(
                        message.arg1, message.getData());
                    return;
                  case 30:
                    DisplayManagerService.this.mWindowManagerInternal.updateMirroredSurface(
                        message.arg1);
                    return;
                  default:
                    return;
                }
            }
        }
      }
      DisplayManagerService.this.deliverWifiDisplayParameterEvent(message.arg1, (List) message.obj);
    }
  }

  public final class LogicalDisplayListener implements LogicalDisplayMapper.Listener {
    public LogicalDisplayListener() {}

    @Override // com.android.server.display.LogicalDisplayMapper.Listener
    public void onLogicalDisplayEventLocked(LogicalDisplay logicalDisplay, int i) {
      switch (i) {
        case 1:
          DisplayManagerService.this.handleLogicalDisplayAddedLocked(logicalDisplay);
          break;
        case 2:
          DisplayManagerService.this.handleLogicalDisplayChangedLocked(logicalDisplay);
          break;
        case 3:
          DisplayManagerService.this.handleLogicalDisplayRemovedLocked(logicalDisplay);
          break;
        case 4:
          DisplayManagerService.this.handleLogicalDisplaySwappedLocked(logicalDisplay);
          break;
        case 5:
          DisplayManagerService.this.handleLogicalDisplayFrameRateOverridesChangedLocked(
              logicalDisplay);
          break;
        case 6:
          DisplayManagerService.this.handleLogicalDisplayDeviceStateTransitionLocked(
              logicalDisplay);
          break;
        case 7:
          DisplayManagerService.this.handleLogicalDisplayHdrSdrRatioChangedLocked(logicalDisplay);
          break;
      }
    }

    @Override // com.android.server.display.LogicalDisplayMapper.Listener
    public void onDisplayGroupEventLocked(int i, int i2) {
      DisplayManagerService.this.sendDisplayGroupEvent(i, i2);
    }

    @Override // com.android.server.display.LogicalDisplayMapper.Listener
    public void onTraversalRequested() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayManagerService.this.scheduleTraversalLocked(false);
      }
    }
  }

  public final class CallbackRecord implements IBinder.DeathRecipient {
    public final IDisplayManagerCallback mCallback;
    public AtomicLong mEventsMask;
    public final int mPid;
    public final int mUid;
    public boolean mWifiDisplayScanRequested;
    public String mWifiDisplayScanRequestedTime;

    public CallbackRecord(int i, int i2, IDisplayManagerCallback iDisplayManagerCallback, long j) {
      this.mPid = i;
      this.mUid = i2;
      this.mCallback = iDisplayManagerCallback;
      this.mEventsMask = new AtomicLong(j);
    }

    public void updateEventsMask(long j) {
      this.mEventsMask.set(j);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      DisplayManagerService.this.onCallbackDied(this);
    }

    public boolean notifyDisplayEventAsync(int i, int i2) {
      if (!shouldSendEvent(i2)) {
        return true;
      }
      try {
        this.mCallback.onDisplayEvent(i, i2);
        return true;
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
        return false;
      }
    }

    public final boolean shouldSendEvent(int i) {
      long j = this.mEventsMask.get();
      if (i == 1) {
        return (j & 1) != 0;
      }
      if (i == 2) {
        return (j & 4) != 0;
      }
      if (i == 3) {
        return (j & 2) != 0;
      }
      if (i == 4) {
        return (j & 8) != 0;
      }
      if (i == 5) {
        return (j & 16) != 0;
      }
      Slog.m74e("DisplayManagerService", "Unknown display event " + i);
      return true;
    }

    public void notifyDisplayVolumeEventAsync(int i, Bundle bundle) {
      try {
        this.mCallback.onDisplayVolumeEvent(i, bundle);
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
      }
    }

    public void notifyAsyncBinderBufferFillEvent(int i, Bundle bundle) {
      try {
        this.mCallback.onAsyncBinderBufferFillEvent(i, bundle);
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
      }
    }

    public void notifyDisplayVolumeKeyEventAsync(int i) {
      try {
        this.mCallback.onDisplayVolumeKeyEvent(i);
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
      }
    }

    public void notifyWifiDisplayParameterEventAsync(int i, List list) {
      try {
        this.mCallback.onWifiDisplayParameterEvent(i, list);
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
      }
    }

    public void notifyDeviceEventAsync(Bundle bundle, int i) {
      try {
        this.mCallback.onDeviceEvent(bundle, i);
      } catch (RemoteException e) {
        Slog.m80w(
            "DisplayManagerService",
            "Failed to notify process " + this.mPid + " that displays changed, assuming it died.",
            e);
        binderDied();
      }
    }
  }

  public final class PendingCallback {
    public final CallbackRecord mCallbackRecord;
    public final ArrayList mDisplayEvents;

    public PendingCallback(CallbackRecord callbackRecord, int i, int i2) {
      this.mCallbackRecord = callbackRecord;
      ArrayList arrayList = new ArrayList();
      this.mDisplayEvents = arrayList;
      arrayList.add(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void addDisplayEvent(int i, int i2) {
      Pair pair = (Pair) this.mDisplayEvents.get(r0.size() - 1);
      if (((Integer) pair.first).intValue() == i && ((Integer) pair.second).intValue() == i2) {
        Slog.m72d(
            "DisplayManagerService",
            "Ignore redundant display event "
                + i
                + "/"
                + i2
                + " to "
                + this.mCallbackRecord.mUid
                + "/"
                + this.mCallbackRecord.mPid);
        return;
      }
      Iterator it = this.mDisplayEvents.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        Pair pair2 = (Pair) it.next();
        if (((Integer) pair2.first).intValue() == i && ((Integer) pair2.second).intValue() == i2) {
          this.mDisplayEvents.remove(pair2);
          break;
        }
      }
      this.mDisplayEvents.add(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void sendPendingDisplayEvent() {
      int i = 0;
      while (true) {
        if (i >= this.mDisplayEvents.size()) {
          break;
        }
        Pair pair = (Pair) this.mDisplayEvents.get(i);
        if (!this.mCallbackRecord.notifyDisplayEventAsync(
            ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue())) {
          Slog.m72d(
              "DisplayManagerService",
              "Drop pending events for dead process " + this.mCallbackRecord.mPid);
          break;
        }
        i++;
      }
      this.mDisplayEvents.clear();
    }
  }

  final class BinderService extends IDisplayManager.Stub {
    public BinderService() {}

    public DisplayInfo getDisplayInfo(int i) {
      int callingUid = Binder.getCallingUid();
      Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getDisplayInfoInternal(i, callingUid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int[] getDisplayIds(boolean z) {
      int[] displayIdsLocked;
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          displayIdsLocked =
              DisplayManagerService.this.mLogicalDisplayMapper.getDisplayIdsLocked(callingUid, z);
        }
        return displayIdsLocked;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isUidPresentOnDisplay(int i, int i2) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.isUidPresentOnDisplayInternal(i, i2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public Point getStableDisplaySize() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getStableDisplaySizeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) {
      registerCallbackWithEventMask(iDisplayManagerCallback, 7L);
    }

    public void registerCallbackWithEventMask(
        IDisplayManagerCallback iDisplayManagerCallback, long j) {
      if (iDisplayManagerCallback == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.registerCallbackInternal(
            iDisplayManagerCallback, callingPid, callingUid, j);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void startWifiDisplayScan() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to start wifi display scans");
      int callingPid = Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.startWifiDisplayScanInternal(callingPid, false);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void stopWifiDisplayScan() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to stop wifi display scans");
      int callingPid = Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.stopWifiDisplayScanInternal(callingPid);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void connectWifiDisplay(String str) {
      if (str == null) {
        throw new IllegalArgumentException("address must not be null");
      }
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to connect to a wifi display");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.connectWifiDisplayInternal(str);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void disconnectWifiDisplay() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.disconnectWifiDisplayInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void renameWifiDisplay(String str, String str2) {
      if (str == null) {
        throw new IllegalArgumentException("address must not be null");
      }
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to rename to a wifi display");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.renameWifiDisplayInternal(str, str2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void forgetWifiDisplay(String str) {
      if (str == null) {
        throw new IllegalArgumentException("address must not be null");
      }
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to forget to a wifi display");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.forgetWifiDisplayInternal(str);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void pauseWifiDisplay() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to pause a wifi display session");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.pauseWifiDisplayInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void resumeWifiDisplay() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to resume a wifi display session");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.resumeWifiDisplayInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public WifiDisplayStatus getWifiDisplayStatus() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getWifiDisplayStatusInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WRITE_SECURE_SETTINGS",
          "Permission required to write the user settings.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setUserDisabledHdrTypesInternal(iArr);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void overrideHdrTypes(int i, int[] iArr) {
      IBinder displayToken;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        displayToken = DisplayManagerService.this.getDisplayToken(i);
        if (displayToken == null) {
          throw new IllegalArgumentException("Invalid display: " + i);
        }
      }
      DisplayControl.overrideHdrTypes(displayToken, iArr);
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean z) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.WRITE_SECURE_SETTINGS",
          "Permission required to write the user settings.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setAreUserDisabledHdrTypesAllowedInternal(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean areUserDisabledHdrTypesAllowed() {
      boolean z;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        z = DisplayManagerService.this.mAreUserDisabledHdrTypesAllowed;
      }
      return z;
    }

    public int[] getUserDisabledHdrTypes() {
      int[] iArr;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        iArr = DisplayManagerService.this.mUserDisabledHdrTypes;
      }
      return iArr;
    }

    public void requestColorMode(int i, int i2) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_COLOR_MODE",
          "Permission required to change the display color mode");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.requestColorModeInternal(i, i2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int createVirtualDisplay(
        VirtualDisplayConfig virtualDisplayConfig,
        IVirtualDisplayCallback iVirtualDisplayCallback,
        IMediaProjection iMediaProjection,
        String str) {
      return DisplayManagerService.this.createVirtualDisplayInternal(
          virtualDisplayConfig, iVirtualDisplayCallback, iMediaProjection, null, null, str);
    }

    public void resizeVirtualDisplay(
        IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) {
      if (i <= 0 || i2 <= 0 || i3 <= 0) {
        throw new IllegalArgumentException("width, height, and densityDpi must be greater than 0");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.resizeVirtualDisplayInternal(
            iVirtualDisplayCallback.asBinder(), i, i2, i3);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setVirtualDisplaySurface(
        IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
      if (surface != null && surface.isSingleBuffered()) {
        throw new IllegalArgumentException("Surface can't be single-buffered");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setVirtualDisplaySurfaceInternal(
            iVirtualDisplayCallback.asBinder(), surface);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.releaseVirtualDisplayInternal(
            iVirtualDisplayCallback.asBinder());
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.rotateVirtualDisplayInternal(
            iVirtualDisplayCallback.asBinder(), i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void connectWifiDisplayWithConfig(
        SemWifiDisplayConfig semWifiDisplayConfig,
        IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to connect to a wifi display");
      Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.connectWifiDisplayInternal(
            semWifiDisplayConfig, iWifiDisplayConnectionCallback);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void startWifiDisplayChannelScan(int i) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to start wifi display scans");
      int callingPid = Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.startWifiDisplayScanInternal(callingPid, i, -1);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void startWifiDisplayChannelScanAndInterval(int i, int i2) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to start wifi display scans");
      int callingPid = Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.startWifiDisplayScanInternal(callingPid, i, i2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getScreenSharingStatus() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter == null) {
          return -1;
        }
        return DisplayManagerService.this.mWifiDisplayAdapter.getScreenSharingStatus();
      }
    }

    public void setScreenSharingStatus(int i) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setScreenSharingStatus");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter != null) {
          DisplayManagerService.this.mWifiDisplayAdapter.setScreenSharingStatus(i);
        }
      }
    }

    public void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setDlnaDevice");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter != null) {
          DisplayManagerService.this.mWifiDisplayAdapter.setDlnaDevice(semDlnaDevice, iBinder);
        }
      }
    }

    public SemDlnaDevice getDlnaDevice() {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call getDlnaDevice");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter != null) {
          return DisplayManagerService.this.mWifiDisplayAdapter.getDlnaDevice();
        }
        return new SemDlnaDevice();
      }
    }

    public void setDeviceVolume(int i) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setDeviceVolume");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController != null) {
          DisplayManagerService.this.mVolumeController.setVolume(i);
        }
      }
    }

    public void setDeviceVolumeMuted(boolean z) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setDeviceVolumeMuted");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController != null) {
          DisplayManagerService.this.mVolumeController.setVolumeMuted(z);
        }
      }
    }

    public void setVolumeKeyEvent(int i) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setVolumeKeyEvent");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController != null) {
          DisplayManagerService.this.mVolumeController.setVolumeKeyEvent(i);
        }
      }
    }

    public int getDeviceMaxVolume() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController == null) {
          return -1;
        }
        return DisplayManagerService.this.mVolumeController.getMaxVolume();
      }
    }

    public int getDeviceMinVolume() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController == null) {
          return -1;
        }
        return DisplayManagerService.this.mVolumeController.getMinVolume();
      }
    }

    public boolean isDeviceVolumeMuted() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mVolumeController == null) {
          return false;
        }
        return DisplayManagerService.this.mVolumeController.isVolumeMuted();
      }
    }

    public boolean isWifiDisplayWithPinSupported(String str) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter == null) {
          return false;
        }
        return DisplayManagerService.this.mWifiDisplayAdapter.isWifiDisplayWithPinSupported(str);
      }
    }

    public void fitToActiveDisplay(boolean z) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "no permission to call fitToActiveDisplay(boolean)");
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter != null) {
          DisplayManagerService.this.mWifiDisplayAdapter.fitToActiveDisplayLocked(z);
        }
      }
    }

    public boolean isFitToActiveDisplay() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter == null) {
          return false;
        }
        return DisplayManagerService.this.mWifiDisplayAdapter.isFitToActiveDisplayLocked();
      }
    }

    public String getPresentationOwner(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked != null
            && displayLocked.hasContentLocked()
            && !DisplayManagerService.this.mPresentationDisplays.isEmpty()) {
          String str = "";
          Iterator it = DisplayManagerService.this.mPresentationDisplays.iterator();
          while (it.hasNext()) {
            PresentationDisplay presentationDisplay = (PresentationDisplay) it.next();
            if (presentationDisplay.getDisplayId() == i) {
              if (!str.isEmpty()) {
                str = str + ",";
              }
              str = str + presentationDisplay.getPackageName();
            }
          }
          return str;
        }
        return "";
      }
    }

    public void setWifiDisplayParam(String str, String str2) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call setWifiDisplayParam");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter != null) {
          DisplayManagerService.this.mWifiDisplayAdapter.setWifiDisplayParam(str, str2);
        }
      }
    }

    public boolean requestSetWifiDisplayParameters(List list) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call requestSetWifiDisplayParameters");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter == null) {
          return false;
        }
        return DisplayManagerService.this.mWifiDisplayAdapter.requestSetWifiDisplayParameters(list);
      }
    }

    public boolean requestWifiDisplayParameter(
        String str, SemWifiDisplayParameter semWifiDisplayParameter) {
      if (Binder.getCallingUid() != 1000) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.CONFIGURE_WIFI_DISPLAY",
            "Permission required to call requestWifiDisplayParameter");
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (DisplayManagerService.this.mWifiDisplayAdapter == null) {
          return false;
        }
        return DisplayManagerService.this.mWifiDisplayAdapter.requestWifiDisplayParameter(
            str, semWifiDisplayParameter);
      }
    }

    public void startWifiDisplayScanAutoP2P() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_WIFI_DISPLAY",
          "Permission required to start wifi display scans");
      int callingPid = Binder.getCallingPid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.startWifiDisplayScanInternal(callingPid, true);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setVirtualDisplayStateInternal(
            iVirtualDisplayCallback.asBinder(), z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (DumpUtils.checkDumpPermission(
          DisplayManagerService.this.mContext, "DisplayManagerService", printWriter)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          if (CoreRune.MD_DEX_EMULATOR
              && DisplayManagerService.this.mDexEmulator.dumpInternal(printWriter, strArr)) {
            return;
          }
          DisplayManagerService.this.dumpInternal(printWriter);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public ParceledListSlice getBrightnessEvents(String str) {
      ParceledListSlice brightnessEvents;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.BRIGHTNESS_SLIDER_USAGE", "Permission to read brightness events.");
      int callingUid = Binder.getCallingUid();
      int noteOp =
          ((AppOpsManager)
                  DisplayManagerService.this.mContext.getSystemService(AppOpsManager.class))
              .noteOp(43, callingUid, str);
      boolean z = true;
      if (noteOp != 3
          ? noteOp != 0
          : DisplayManagerService.this.mContext.checkCallingPermission(
                  "android.permission.PACKAGE_USAGE_STATS")
              != 0) {
        z = false;
      }
      int userId = UserHandle.getUserId(callingUid);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          Slog.m72d(
              "DisplayManagerService",
              "[api] getBrightnessEvents: u:"
                  + userId
                  + ", us:"
                  + z
                  + " "
                  + ((DisplayPowerControllerInterface)
                          DisplayManagerService.this.mDisplayPowerControllers.get(0))
                      .getBrightnessEvents(userId, z)
                  + PowerManagerUtil.callerInfoToString());
          brightnessEvents =
              ((DisplayPowerControllerInterface)
                      DisplayManagerService.this.mDisplayPowerControllers.get(0))
                  .getBrightnessEvents(userId, z);
        }
        return brightnessEvents;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public ParceledListSlice getAmbientBrightnessStats() {
      ParceledListSlice ambientBrightnessStats;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.ACCESS_AMBIENT_LIGHT_STATS",
          "Permission required to to access ambient light stats.");
      int userId = UserHandle.getUserId(Binder.getCallingUid());
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          Slog.m72d(
              "DisplayManagerService",
              "[api] getAmbientBrightnessStats: u:"
                  + userId
                  + ": "
                  + ((DisplayPowerControllerInterface)
                          DisplayManagerService.this.mDisplayPowerControllers.get(0))
                      .getAmbientBrightnessStats(userId)
                  + PowerManagerUtil.callerInfoToString());
          ambientBrightnessStats =
              ((DisplayPowerControllerInterface)
                      DisplayManagerService.this.mDisplayPowerControllers.get(0))
                  .getAmbientBrightnessStats(userId);
        }
        return ambientBrightnessStats;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setBrightnessConfigurationForUser(
        final BrightnessConfiguration brightnessConfiguration, final int i, final String str) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setBrightnessConfigurationForUser: "
              + brightnessConfiguration
              + " "
              + i
              + ": "
              + str
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to change the display's brightness configuration");
      if (i != UserHandle.getCallingUserId()) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.INTERACT_ACROSS_USERS",
            "Permission required to change the display brightness configuration of another user");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
              new Consumer() { // from class:
                               // com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayManagerService.BinderService.this
                      .lambda$setBrightnessConfigurationForUser$0(
                          brightnessConfiguration, i, str, (LogicalDisplay) obj);
                }
              });
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBrightnessConfigurationForUser$0(
        BrightnessConfiguration brightnessConfiguration,
        int i,
        String str,
        LogicalDisplay logicalDisplay) {
      if (logicalDisplay.getDisplayInfoLocked().type != 1) {
        return;
      }
      DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(
          brightnessConfiguration,
          logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(),
          i,
          str);
    }

    public void setBrightnessConfigurationForDisplay(
        BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setBrightnessConfigurationForDisplay: "
              + brightnessConfiguration
              + " uniqueId: "
              + str
              + " userId: "
              + i
              + " packageName: "
              + str2);
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to change the display's brightness configuration");
      if (i != UserHandle.getCallingUserId()) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.INTERACT_ACROSS_USERS",
            "Permission required to change the display brightness configuration of another user");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(
            brightnessConfiguration, str, i, str2);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) {
      BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to read the display's brightness configuration");
      if (i != UserHandle.getCallingUserId()) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.INTERACT_ACROSS_USERS",
            "Permission required to read the display brightness configuration of another user");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      int userSerialNumber = DisplayManagerService.this.getUserManager().getUserSerialNumber(i);
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          brightnessConfigForDisplayWithPdsFallbackLocked =
              DisplayManagerService.this.getBrightnessConfigForDisplayWithPdsFallbackLocked(
                  str, userSerialNumber);
          if (brightnessConfigForDisplayWithPdsFallbackLocked == null) {
            Slog.m72d(
                "DisplayManagerService",
                "[api] getBrightnessConfigurationForDisplay: config is null");
            DisplayPowerControllerInterface dpcFromUniqueIdLocked =
                DisplayManagerService.this.getDpcFromUniqueIdLocked(str);
            if (dpcFromUniqueIdLocked != null) {
              brightnessConfigForDisplayWithPdsFallbackLocked =
                  dpcFromUniqueIdLocked.getDefaultBrightnessConfiguration();
            }
          }
          Slog.m72d(
              "DisplayManagerService",
              "[api] getBrightnessConfigurationForDisplay: "
                  + brightnessConfigForDisplayWithPdsFallbackLocked
                  + PowerManagerUtil.callerInfoToString());
        }
        return brightnessConfigForDisplayWithPdsFallbackLocked;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
      String uniqueId;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        uniqueId =
            DisplayManagerService.this
                .mLogicalDisplayMapper
                .getDisplayLocked(0)
                .getPrimaryDisplayDeviceLocked()
                .getUniqueId();
      }
      return getBrightnessConfigurationForDisplay(uniqueId, i);
    }

    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to read the display's default brightness configuration");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          BrightnessConfiguration brightnessConfiguration =
              DisplayManagerService.this.mPersistentDataStore.getBrightnessConfiguration(0);
          if (brightnessConfiguration != null
              && "sec-backup".equals(brightnessConfiguration.getDescription())) {
            Pair curve = brightnessConfiguration.getCurve();
            BrightnessConfiguration.Builder builder =
                new BrightnessConfiguration.Builder((float[]) curve.first, (float[]) curve.second);
            builder.setDescription("");
            Slog.m72d(
                "DisplayManagerService",
                "[api] getDefaultBrightnessConfiguration(backup config is applied) :"
                    + builder.build()
                    + PowerManagerUtil.callerInfoToString());
            return builder.build();
          }
          Slog.m72d(
              "DisplayManagerService",
              "[api] getDefaultBrightnessConfiguration: "
                  + ((DisplayPowerControllerInterface)
                          DisplayManagerService.this.mDisplayPowerControllers.get(0))
                      .getDefaultBrightnessConfiguration()
                  + PowerManagerUtil.callerInfoToString());
          return ((DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(0))
              .getDefaultBrightnessConfiguration();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public BrightnessInfo getBrightnessInfo(int i) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to read the display's brightness info.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          LogicalDisplay displayLocked =
              DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, false);
          if (displayLocked != null && displayLocked.isEnabledLocked()) {
            DisplayPowerControllerInterface displayPowerControllerInterface =
                (DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(i);
            if (displayPowerControllerInterface == null) {
              return null;
            }
            return displayPowerControllerInterface.getBrightnessInfo();
          }
          return null;
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean isMinimalPostProcessingRequested(int i) {
      boolean requestedMinimalPostProcessingLocked;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        requestedMinimalPostProcessingLocked =
            DisplayManagerService.this
                .mLogicalDisplayMapper
                .getDisplayLocked(i)
                .getRequestedMinimalPostProcessingLocked();
      }
      return requestedMinimalPostProcessingLocked;
    }

    public void setTemporaryBrightness(int i, float f) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setTemporaryBrightness: displayId="
              + i
              + " brightness="
              + f
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to set the display's brightness");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          ((DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(i))
              .setTemporaryBrightness(f);
          if (PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY) {
            DisplayManagerService.this.updateOtherInternalDisplayBrightnessLocked(i);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setBrightness(int i, float f) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setBrightness: displayId="
              + i
              + " brightness="
              + f
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to set the display's brightness");
      if (!DisplayManagerService.this.isValidBrightness(f)) {
        Slog.m79w("DisplayManagerService", "Attempted to set invalid brightness" + f);
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayPowerControllerInterface displayPowerControllerInterface =
              (DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(i);
          if (displayPowerControllerInterface != null) {
            displayPowerControllerInterface.setBrightness(f);
          }
          DisplayManagerService.this.mPersistentDataStore.saveIfNeeded();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public float getBrightness(int i) {
      float screenBrightnessSetting;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to set the display's brightness");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayPowerControllerInterface displayPowerControllerInterface =
              (DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(i);
          screenBrightnessSetting =
              displayPowerControllerInterface != null
                  ? displayPowerControllerInterface.getScreenBrightnessSetting()
                  : Float.NaN;
        }
        return screenBrightnessSetting;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to set the display's auto brightness adjustment");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          ((DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(0))
              .setTemporaryAutoBrightnessAdjustment(f);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
        FileDescriptor fileDescriptor,
        FileDescriptor fileDescriptor2,
        FileDescriptor fileDescriptor3,
        String[] strArr,
        ShellCallback shellCallback,
        ResultReceiver resultReceiver) {
      new DisplayManagerShellCommand(DisplayManagerService.this)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
    }

    public Curve getMinimumBrightnessCurve() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getMinimumBrightnessCurveInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getPreferredWideGamutColorSpaceId() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getPreferredWideGamutColorSpaceIdInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setUserPreferredDisplayMode(int i, Display.Mode mode) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.MODIFY_USER_PREFERRED_DISPLAY_MODE",
          "Permission required to set the user preferred display mode.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setUserPreferredDisplayModeInternal(i, mode);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public Display.Mode getUserPreferredDisplayMode(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getUserPreferredDisplayModeInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public Display.Mode getSystemPreferredDisplayMode(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getSystemPreferredDisplayModeInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setHdrConversionMode(HdrConversionMode hdrConversionMode) {
      if (DisplayManagerService.this.mIsHdrOutputControlEnabled) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.MODIFY_HDR_CONVERSION_MODE",
            "Permission required to set the HDR conversion mode.");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          DisplayManagerService.this.setHdrConversionModeInternal(hdrConversionMode);
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }

    public HdrConversionMode getHdrConversionModeSetting() {
      if (!DisplayManagerService.this.mIsHdrOutputControlEnabled) {
        return DisplayManagerService.HDR_CONVERSION_MODE_UNSUPPORTED;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getHdrConversionModeSettingInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public HdrConversionMode getHdrConversionMode() {
      if (!DisplayManagerService.this.mIsHdrOutputControlEnabled) {
        return DisplayManagerService.HDR_CONVERSION_MODE_UNSUPPORTED;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getHdrConversionModeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int[] getSupportedHdrOutputTypes() {
      if (!DisplayManagerService.this.mIsHdrOutputControlEnabled) {
        return DisplayManagerService.EMPTY_ARRAY;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getSupportedHdrOutputTypesInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.OVERRIDE_DISPLAY_MODE_REQUESTS",
          "Permission required to override display mode requests.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setShouldAlwaysRespectAppRequestedModeInternal(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.OVERRIDE_DISPLAY_MODE_REQUESTS",
          "Permission required to override display mode requests.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.shouldAlwaysRespectAppRequestedModeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setRefreshRateSwitchingType(int i) {
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.MODIFY_REFRESH_RATE_SWITCHING_TYPE",
          "Permission required to modify refresh rate switching type.");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setRefreshRateSwitchingTypeInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int getRefreshRateSwitchingType() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getRefreshRateSwitchingTypeInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public DisplayDecorationSupport getDisplayDecorationSupport(int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getDisplayDecorationSupportInternal(i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setDisplayIdToMirror(IBinder iBinder, int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (DisplayManagerService.this.mVirtualDisplayAdapter != null) {
          VirtualDisplayAdapter virtualDisplayAdapter =
              DisplayManagerService.this.mVirtualDisplayAdapter;
          if (displayLocked == null) {
            i = -1;
          }
          virtualDisplayAdapter.setDisplayIdToMirror(iBinder, i);
        }
      }
    }

    public OverlayProperties getOverlaySupport() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        return DisplayManagerService.this.getOverlaySupportInternal();
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setDisplayStateLimit(IBinder iBinder, int i) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setDisplayStateLimit(b): stateLimit="
              + Display.stateToString(i)
              + " ("
              + Objects.hashCode(iBinder)
              + ")"
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setDisplayStateLimitInternal(iBinder, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setTemporaryBrightnessForSlowChange: displayId="
              + i
              + ", brightness="
              + f
              + ", slowChange="
              + z
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to set the display's brightness");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          ((DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(i))
              .setTemporaryBrightnessForSlowChange(f, z);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setBrightnessConfigurationForUserWithStats(
        BrightnessConfiguration brightnessConfiguration,
        int i,
        String str,
        List list,
        List list2,
        List list3) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setBrightnessConfigurationForUser: "
              + brightnessConfiguration
              + " "
              + i
              + ": "
              + str
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to change the display's brightness configuration");
      if (i != UserHandle.getCallingUserId()) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.INTERACT_ACROSS_USERS",
            "Permission required to change the display brightness configuration of another user");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        DisplayManagerService.this.setBrightnessConfigurationForUserWithStatsInternal(
            brightnessConfiguration, i, str, list, list2, list3);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void resetBrightnessConfigurationForUser(final int i, final String str) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] resetBrightnessConfigurationForUser:  "
              + i
              + ": "
              + str
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONFIGURE_DISPLAY_BRIGHTNESS",
          "Permission required to change the display's brightness configuration");
      if (i != UserHandle.getCallingUserId()) {
        DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
            "android.permission.INTERACT_ACROSS_USERS",
            "Permission required to change the display brightness configuration of another user");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
              new Consumer() { // from class:
                               // com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayManagerService.BinderService.this
                      .lambda$resetBrightnessConfigurationForUser$1(str, i, (LogicalDisplay) obj);
                }
              });
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetBrightnessConfigurationForUser$1(
        String str, int i, LogicalDisplay logicalDisplay) {
      if (logicalDisplay.getDisplayInfoLocked().type == 1) {
        String uniqueId = logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId();
        if (str != null
            && !DisplayManagerService.this.validatePackageName(
                IDisplayManager.Stub.getCallingUid(), str)) {
          str = null;
        }
        DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(
            null, uniqueId, i, str);
      }
    }

    public void setBackupBrightnessConfiguration(
        BrightnessConfiguration brightnessConfiguration, final int i, final String str) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setBackupBrightnessConfiguration: " + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          Slog.m72d(
              "DisplayManagerService",
              "[api] setBackupBrightnessConfiguration: " + brightnessConfiguration);
          DisplayPowerControllerInterface displayPowerControllerInterface =
              (DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(0);
          final BrightnessConfiguration appliedBackupConfiguration =
              displayPowerControllerInterface.getAppliedBackupConfiguration(
                  brightnessConfiguration);
          if (appliedBackupConfiguration != null) {
            Slog.m72d(
                "DisplayManagerService",
                "[api] setBackupBrightnessConfiguration(new): " + appliedBackupConfiguration);
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
              displayPowerControllerInterface.clearAdaptiveBrightnessLongtermModelBuilder();
            } else {
              DisplayManagerService.this.resetBrightnessConfigurations();
              DisplayManagerService.this
                  .mContext
                  .getPackageManager()
                  .clearApplicationUserData("com.google.android.apps.turbo", null);
            }
            DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
                new Consumer() { // from class:
                                 // com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    DisplayManagerService.BinderService.this
                        .lambda$setBackupBrightnessConfiguration$2(
                            appliedBackupConfiguration, i, str, (LogicalDisplay) obj);
                  }
                });
            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
              displayPowerControllerInterface
                  .restartAdaptiveBrightnessLongtermModelBuilderFromBnr();
            }
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBackupBrightnessConfiguration$2(
        BrightnessConfiguration brightnessConfiguration,
        int i,
        String str,
        LogicalDisplay logicalDisplay) {
      if (logicalDisplay.getDisplayInfoLocked().type == 1) {
        DisplayManagerService.this.setBrightnessConfigurationForDisplayInternal(
            brightnessConfiguration,
            logicalDisplay.getPrimaryDisplayDeviceLocked().getUniqueId(),
            i,
            str);
      }
    }

    public BrightnessConfiguration getBackupBrightnessConfiguration(int i) {
      BrightnessConfiguration brightnessConfiguration;
      Slog.m72d(
          "DisplayManagerService",
          "[api] getBackupBrightnessConfiguration: " + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.DEVICE_POWER", null);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        int userSerialNumber = DisplayManagerService.this.getUserManager().getUserSerialNumber(i);
        synchronized (DisplayManagerService.this.mSyncRoot) {
          brightnessConfiguration =
              DisplayManagerService.this.mPersistentDataStore.getBrightnessConfiguration(
                  userSerialNumber);
          if (brightnessConfiguration == null) {
            Slog.m72d(
                "DisplayManagerService",
                "getBackupBrightnessConfiguration: learned config did not exist");
            brightnessConfiguration =
                ((DisplayPowerControllerInterface)
                        DisplayManagerService.this.mDisplayPowerControllers.get(0))
                    .getDefaultBrightnessConfiguration();
          }
          Slog.m72d(
              "DisplayManagerService",
              "[api] getBackupBrightnessConfiguration: " + brightnessConfiguration);
        }
        return brightnessConfiguration;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public int convertToBrightness(float f) {
      int convertToBrightness;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to covert the nits to brightness");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          convertToBrightness =
              ((DisplayPowerControllerInterface)
                      DisplayManagerService.this.mDisplayPowerControllers.get(0))
                  .convertToBrightness(f);
          Slog.m72d(
              "DisplayManagerService",
              "[api] convertToBrightness: " + f + " -> " + convertToBrightness);
        }
        return convertToBrightness;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public float getAdaptiveBrightness(int i, float f) {
      float adaptiveBrightness;
      DisplayManagerService.this.mContext.enforceCallingOrSelfPermission(
          "android.permission.CONTROL_DISPLAY_BRIGHTNESS",
          "Permission required to get the display's adaptive brightness");
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayPowerControllerInterface displayPowerControllerInterface =
              (DisplayPowerControllerInterface)
                  DisplayManagerService.this.mDisplayPowerControllers.get(i);
          adaptiveBrightness =
              displayPowerControllerInterface != null
                  ? displayPowerControllerInterface.getAdaptiveBrightness(f)
                  : Float.NaN;
        }
        return adaptiveBrightness;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final boolean isAllowedRefreshRateToken() {
      int callingUid = Binder.getCallingUid();
      if (DisplayManagerService.this.mContext.getPackageManager().checkSignatures(1000, callingUid)
          == 0) {
        return true;
      }
      Slog.m72d(
          "DisplayManagerService",
          "RefreshRateToken - abnormal caller, uid: "
              + callingUid
              + ", pid: "
              + Binder.getCallingPid());
      return false;
    }

    public IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) {
      IRefreshRateToken createPassiveModeToken;
      if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
        return null;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        createPassiveModeToken =
            DisplayManagerService.this
                .mDisplayModeDirector
                .getRefreshRateModeManager()
                .getController()
                .createPassiveModeToken(iBinder, str);
      }
      return createPassiveModeToken;
    }

    public IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) {
      IRefreshRateToken createLowRefreshRateToken;
      if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
        return null;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        createLowRefreshRateToken =
            DisplayManagerService.this
                .mDisplayModeDirector
                .getRefreshRateModeManager()
                .getController()
                .createLowRefreshRateToken(iBinder, str);
      }
      return createLowRefreshRateToken;
    }

    public IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) {
      IRefreshRateToken createRefreshRateMaxLimitToken;
      if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
        return null;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        createRefreshRateMaxLimitToken =
            DisplayManagerService.this
                .mDisplayModeDirector
                .getRefreshRateModeManager()
                .getController()
                .createRefreshRateMaxLimitToken(iBinder, i, str);
      }
      return createRefreshRateMaxLimitToken;
    }

    public IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) {
      IRefreshRateToken createRefreshRateMinLimitToken;
      if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
        return null;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        createRefreshRateMinLimitToken =
            DisplayManagerService.this
                .mDisplayModeDirector
                .getRefreshRateModeManager()
                .getController()
                .createRefreshRateMinLimitToken(iBinder, i, str);
      }
      return createRefreshRateMinLimitToken;
    }

    public long getPrimaryPhysicalDisplayId() {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (DisplayManagerService.this.mSyncRoot) {
          DisplayDevice primaryDisplayDeviceLocked =
              DisplayManagerService.this.mDisplayDeviceRepo.getPrimaryDisplayDeviceLocked();
          if (primaryDisplayDeviceLocked != null) {
            DisplayAddress.Physical physical =
                primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().address;
            if (physical instanceof DisplayAddress.Physical) {
              return physical.getPhysicalDisplayId();
            }
          }
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return -1L;
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public final boolean isValidBrightness(float f) {
    return !Float.isNaN(f)
        && f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        && f <= this.mScreenExtendedBrightnessRangeMaximum;
  }

  public final void updateOtherInternalDisplayBrightnessLocked(int i) {
    int i2 = i != 0 ? i != 1 ? -1 : 0 : 1;
    if (i2 != -1) {
      ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i2))
          .onScreenBrightnessSettingTimeChanged();
    }
  }

  final class LocalService extends DisplayManagerInternal {
    public void clearOldDisplayDevice() {}

    public LocalService() {}

    public void initPowerManagement(
        DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks,
        Handler handler,
        SensorManager sensorManager) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayManagerService.this.mDisplayPowerCallbacks = displayPowerCallbacks;
        DisplayManagerService.this.mSensorManager = sensorManager;
        DisplayManagerService.this.mPowerHandler = handler;
        DisplayManagerService.this.initializeDisplayPowerControllersLocked();
      }
      DisplayManagerService.this.mHandler.sendEmptyMessage(6);
    }

    public int createVirtualDisplay(
        VirtualDisplayConfig virtualDisplayConfig,
        IVirtualDisplayCallback iVirtualDisplayCallback,
        IVirtualDevice iVirtualDevice,
        DisplayWindowPolicyController displayWindowPolicyController,
        String str) {
      return DisplayManagerService.this.createVirtualDisplayInternal(
          virtualDisplayConfig,
          iVirtualDisplayCallback,
          null,
          iVirtualDevice,
          displayWindowPolicyController,
          str);
    }

    public boolean requestPowerState(
        int i, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayGroup displayGroupLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayGroupLocked(i);
        boolean z2 = true;
        if (displayGroupLocked == null) {
          return true;
        }
        int sizeLocked = displayGroupLocked.getSizeLocked();
        for (int i2 = 0; i2 < sizeLocked; i2++) {
          int idLocked = displayGroupLocked.getIdLocked(i2);
          if ((DisplayManagerService.this
                      .mLogicalDisplayMapper
                      .getDisplayLocked(idLocked)
                      .getPrimaryDisplayDeviceLocked()
                      .getDisplayDeviceInfoLocked()
                      .flags
                  & 32)
              == 0) {
            z2 &=
                ((DisplayPowerControllerInterface)
                        DisplayManagerService.this.mDisplayPowerControllers.get(idLocked))
                    .requestPowerState(displayPowerRequest, z);
          }
        }
        return z2;
      }
    }

    public boolean isProximitySensorAvailable() {
      boolean isProximitySensorAvailable;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        isProximitySensorAvailable =
            ((DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(0))
                .isProximitySensorAvailable();
      }
      return isProximitySensorAvailable;
    }

    public void registerDisplayGroupListener(
        DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
      DisplayManagerService.this.mDisplayGroupListeners.add(displayGroupListener);
    }

    public void unregisterDisplayGroupListener(
        DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
      DisplayManagerService.this.mDisplayGroupListeners.remove(displayGroupListener);
    }

    public ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i) {
      return DisplayManagerService.this.systemScreenshotInternal(i);
    }

    public ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i) {
      return DisplayManagerService.this.userScreenshotInternal(i);
    }

    public DisplayInfo getDisplayInfo(int i) {
      return DisplayManagerService.this.getDisplayInfoInternal(i, Process.myUid());
    }

    public Set getPossibleDisplayInfo(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        ArraySet arraySet = new ArraySet();
        if (DisplayManagerService.this.mDeviceStateManager == null) {
          Slog.m79w(
              "DisplayManagerService",
              "Can't get supported states since DeviceStateManager not ready");
          return arraySet;
        }
        for (int i2 :
            DisplayManagerService.this.mDeviceStateManager.getSupportedStateIdentifiers()) {
          DisplayInfo displayInfoForStateLocked =
              DisplayManagerService.this.mLogicalDisplayMapper.getDisplayInfoForStateLocked(i2, i);
          if (displayInfoForStateLocked != null) {
            arraySet.add(displayInfoForStateLocked);
          }
        }
        return arraySet;
      }
    }

    public Point getDisplayPosition(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked == null) {
          return null;
        }
        return displayLocked.getDisplayPosition();
      }
    }

    public void registerDisplayTransactionListener(
        DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
      if (displayTransactionListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      DisplayManagerService.this.registerDisplayTransactionListenerInternal(
          displayTransactionListener);
    }

    public void unregisterDisplayTransactionListener(
        DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
      if (displayTransactionListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      DisplayManagerService.this.unregisterDisplayTransactionListenerInternal(
          displayTransactionListener);
    }

    public void setDisplayInfoOverrideFromWindowManager(int i, DisplayInfo displayInfo) {
      DisplayManagerService.this.setDisplayInfoOverrideFromWindowManagerInternal(i, displayInfo);
    }

    public void getNonOverrideDisplayInfo(int i, DisplayInfo displayInfo) {
      DisplayManagerService.this.getNonOverrideDisplayInfoInternal(i, displayInfo);
    }

    public void performTraversal(SurfaceControl.Transaction transaction, SparseArray sparseArray) {
      DisplayManagerService.this.performTraversalInternal(transaction, sparseArray);
    }

    public void setDisplayProperties(
        int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4) {
      DisplayManagerService.this.setDisplayPropertiesInternal(i, z, f, i2, f2, f3, z2, z3, z4);
    }

    public void setDisplayOffsets(int i, int i2, int i3) {
      DisplayManagerService.this.setDisplayOffsetsInternal(i, i2, i3);
    }

    public void setDisplayScalingDisabled(int i, boolean z) {
      DisplayManagerService.this.setDisplayScalingDisabledInternal(i, z);
    }

    public void setDisplayAccessUIDs(SparseArray sparseArray) {
      DisplayManagerService.this.setDisplayAccessUIDsInternal(sparseArray);
    }

    public void persistBrightnessTrackerState() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        ((DisplayPowerControllerInterface)
                DisplayManagerService.this.mDisplayPowerControllers.get(0))
            .persistBrightnessTrackerState();
      }
    }

    public void onOverlayChanged() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayManagerService.this.mDisplayDeviceRepo.forEachLocked(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$LocalService$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((DisplayDevice) obj).onOverlayChangedLocked();
              }
            });
      }
    }

    public DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i) {
      return DisplayManagerService.this.getDisplayedContentSamplingAttributesInternal(i);
    }

    public boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3) {
      return DisplayManagerService.this.setDisplayedContentSamplingEnabledInternal(i, z, i2, i3);
    }

    public DisplayedContentSample getDisplayedContentSample(int i, long j, long j2) {
      return DisplayManagerService.this.getDisplayedContentSampleInternal(i, j, j2);
    }

    public void ignoreProximitySensorUntilChanged() {
      if (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
          && DisplayManagerService.this.mDualScreenPolicy == 1) {
        ((DisplayPowerControllerInterface)
                DisplayManagerService.this.mDisplayPowerControllers.get(1))
            .ignoreProximitySensorUntilChanged();
      } else {
        ((DisplayPowerControllerInterface)
                DisplayManagerService.this.mDisplayPowerControllers.get(0))
            .ignoreProximitySensorUntilChanged();
      }
    }

    public int getRefreshRateSwitchingType() {
      return DisplayManagerService.this.getRefreshRateSwitchingTypeInternal();
    }

    public SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(
        int i, String str, String str2) {
      SensorManager sensorManager;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        sensorManager = DisplayManagerService.this.mSensorManager;
      }
      if (sensorManager == null || SensorUtils.findSensor(sensorManager, str2, str, 0) == null) {
        return null;
      }
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked == null) {
          return null;
        }
        DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
        if (primaryDisplayDeviceLocked == null) {
          return null;
        }
        DisplayDeviceConfig.SensorData proximitySensor =
            primaryDisplayDeviceLocked.getDisplayDeviceConfig().getProximitySensor();
        if (!proximitySensor.matches(str, str2)) {
          return null;
        }
        return new SurfaceControl.RefreshRateRange(
            proximitySensor.minRefreshRate, proximitySensor.maxRefreshRate);
      }
    }

    public List getRefreshRateLimitations(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayDevice deviceForDisplayLocked =
            DisplayManagerService.this.getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
          return null;
        }
        return deviceForDisplayLocked.getDisplayDeviceConfig().getRefreshRateLimitations();
      }
    }

    public int createSpegVirtualDisplay(
        String str, int i, IVirtualDisplayCallback iVirtualDisplayCallback) {
      return DisplayManagerService.this.createSpegVirtualDisplayInternal(
          str, i, iVirtualDisplayCallback);
    }

    public void setWindowManagerMirroring(int i, boolean z) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayDevice deviceForDisplayLocked =
            DisplayManagerService.this.getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked != null) {
          deviceForDisplayLocked.setWindowManagerMirroringLocked(z);
        }
      }
    }

    public Point getDisplaySurfaceDefaultSize(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayDevice deviceForDisplayLocked =
            DisplayManagerService.this.getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
          return null;
        }
        return deviceForDisplayLocked.getDisplaySurfaceDefaultSizeLocked();
      }
    }

    public void onEarlyInteractivityChange(boolean z) {
      DisplayManagerService.this.mLogicalDisplayMapper.onEarlyInteractivityChange(z);
    }

    public DisplayWindowPolicyController getDisplayWindowPolicyController(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        if (!DisplayManagerService.this.mDisplayWindowPolicyControllers.contains(i)) {
          return null;
        }
        return (DisplayWindowPolicyController)
            ((Pair) DisplayManagerService.this.mDisplayWindowPolicyControllers.get(i)).second;
      }
    }

    public int getDisplayIdToMirror(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked == null) {
          return -1;
        }
        DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
        int i2 = 0;
        if (!((primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().flags & 128) != 0)
            && !primaryDisplayDeviceLocked.isWindowManagerMirroringLocked()) {
          int displayIdToMirrorLocked = primaryDisplayDeviceLocked.getDisplayIdToMirrorLocked();
          if (DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(
                  displayIdToMirrorLocked)
              != null) {
            i2 = displayIdToMirrorLocked;
          }
          return i2;
        }
        return -1;
      }
    }

    public SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i) {
      IBinder displayToken;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        displayToken = DisplayManagerService.this.getDisplayToken(i);
        if (displayToken == null) {
          throw new IllegalArgumentException("Invalid displayId=" + i);
        }
      }
      return SurfaceControl.getDisplayNativePrimaries(displayToken);
    }

    public HostUsiVersion getHostUsiVersion(int i) {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        LogicalDisplay displayLocked =
            DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i);
        if (displayLocked == null) {
          return null;
        }
        return displayLocked
            .getPrimaryDisplayDeviceLocked()
            .getDisplayDeviceConfig()
            .getHostUsiVersion();
      }
    }

    public IntArray getDisplayGroupIds() {
      final ArraySet arraySet = new ArraySet();
      final IntArray intArray = new IntArray();
      synchronized (DisplayManagerService.this.mSyncRoot) {
        DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
            new Consumer() { // from class:
                             // com.android.server.display.DisplayManagerService$LocalService$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                DisplayManagerService.LocalService.this.lambda$getDisplayGroupIds$0(
                    arraySet, intArray, (LogicalDisplay) obj);
              }
            });
      }
      return intArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDisplayGroupIds$0(
        Set set, IntArray intArray, LogicalDisplay logicalDisplay) {
      int displayGroupIdFromDisplayIdLocked =
          DisplayManagerService.this.mLogicalDisplayMapper.getDisplayGroupIdFromDisplayIdLocked(
              logicalDisplay.getDisplayIdLocked());
      if (set.contains(Integer.valueOf(displayGroupIdFromDisplayIdLocked))) {
        return;
      }
      set.add(Integer.valueOf(displayGroupIdFromDisplayIdLocked));
      intArray.add(displayGroupIdFromDisplayIdLocked);
    }

    public float getLastAutomaticScreenBrightness() {
      float lastAutomaticScreenBrightness;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        lastAutomaticScreenBrightness =
            ((DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(0))
                .getLastAutomaticScreenBrightness();
      }
      return lastAutomaticScreenBrightness;
    }

    public float getCurrentScreenBrightness() {
      float currentScreenBrightness;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        currentScreenBrightness =
            ((DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(0))
                .getCurrentScreenBrightness();
      }
      return currentScreenBrightness;
    }

    public long getLastUserSetScreenBrightnessTime() {
      long lastUserSetScreenBrightnessTime;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        lastUserSetScreenBrightnessTime =
            ((DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(0))
                .getLastUserSetScreenBrightnessTime();
      }
      return lastUserSetScreenBrightnessTime;
    }

    public int[] getBrightnessLearningMaxLimitCount() {
      int[] brightnessLearningMaxLimitCount;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        brightnessLearningMaxLimitCount =
            ((DisplayPowerControllerInterface)
                    DisplayManagerService.this.mDisplayPowerControllers.get(0))
                .getBrightnessLearningMaxLimitCount();
      }
      return brightnessLearningMaxLimitCount;
    }

    public float registerDisplayBrightnessListener(
        DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
      Slog.m72d(
          "DisplayManagerService",
          "registerDisplayBrightnessListener: " + displayBrightnessListener);
      return DisplayManagerService.this.registerDisplayBrightnessListenerInternal(
          displayBrightnessListener);
    }

    public void unregisterDisplayBrightnessListener(
        DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
      Slog.m72d(
          "DisplayManagerService",
          "unregisterDisplayBrightnessListener: " + displayBrightnessListener);
      if (displayBrightnessListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      DisplayManagerService.this.unregisterDisplayBrightnessListenerInternal(
          displayBrightnessListener);
    }

    public void registerDisplayStateListener(
        DisplayManagerInternal.DisplayStateListener displayStateListener) {
      Slog.m72d("DisplayManagerService", "registerDisplayStateListener: " + displayStateListener);
      if (displayStateListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      DisplayManagerService.this.registerDisplayStateListenerInternal(displayStateListener);
    }

    public void unregisterDisplayStateListener(
        DisplayManagerInternal.DisplayStateListener displayStateListener) {
      Slog.m72d("DisplayManagerService", "unregisterDisplayStateListener: " + displayStateListener);
      if (displayStateListener == null) {
        throw new IllegalArgumentException("listener must not be null");
      }
      DisplayManagerService.this.unregisterDisplayStateListenerInternal(displayStateListener);
    }

    public void setDisplayStateLimit(IBinder iBinder, int i) {
      Slog.m72d(
          "DisplayManagerService",
          "[api] setDisplayStateLimit(l): stateLimit="
              + Display.stateToString(i)
              + " ("
              + Objects.hashCode(iBinder)
              + ")"
              + PowerManagerUtil.callerInfoToString());
      DisplayManagerService.this.setDisplayStateLimitInternal(iBinder, i);
    }

    public IBinder getRealDisplayToken(int i) {
      return DisplayManagerService.this.getDisplayToken(i);
    }

    public int updateDexDisplayState(boolean z) {
      return DisplayManagerService.this.updateDexDisplayStateInternal(z);
    }

    public boolean isDualSwitchEnabled() {
      boolean z;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        z = CoreRune.MD_DEX_EMULATOR && DisplayManagerService.this.mDexEmulator.mDualSwitchEnabled;
      }
      return z;
    }

    public void hideCutoutForFoldable(boolean z) {
      DisplayManagerService.this.hideCutoutForFoldableInternal(z);
    }

    public void updateDesktopDisplayState(boolean z, int i) {
      DisplayManagerService.this.updateDesktopDisplayStateInternal(z, i);
    }

    public void updateOverlayState() {
      DisplayManagerService.this.mNewDexEmulator.updateOverlayState();
    }

    public void updateDualOverlayState() {
      DisplayManagerService.this.mNewDexEmulator.updateDualOverlayState();
    }

    public void switchOverlay() {
      DisplayManagerService.this.mNewDexEmulator.switchOverlay();
    }

    public void setForceListenProcess(int i) {
      DisplayManagerService.this.mForceListenProcessId = i;
    }
  }

  public class DesiredDisplayModeSpecsObserver
      implements DisplayModeDirector.DesiredDisplayModeSpecsListener {
    public final Consumer mSpecsChangedConsumer =
        new Consumer() { // from class:
                         // com.android.server.display.DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayManagerService.DesiredDisplayModeSpecsObserver.this.lambda$new$0(
                (LogicalDisplay) obj);
          }
        };
    public boolean mChanged = false;

    public DesiredDisplayModeSpecsObserver() {}

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(LogicalDisplay logicalDisplay) {
      int displayIdLocked = logicalDisplay.getDisplayIdLocked();
      DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs =
          DisplayManagerService.this.mDisplayModeDirector.getDesiredDisplayModeSpecs(
              displayIdLocked);
      if (desiredDisplayModeSpecs.equals(logicalDisplay.getDesiredDisplayModeSpecsLocked())) {
        return;
      }
      logicalDisplay.setDesiredDisplayModeSpecsLocked(desiredDisplayModeSpecs);
      this.mChanged = true;
      if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
        DisplayManagerService.this
            .mDisplayModeDirector
            .getRefreshRateModeManager()
            .logCurrentState(displayIdLocked, desiredDisplayModeSpecs);
      }
    }

    @Override // com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecsListener
    public void onDesiredDisplayModeSpecsChanged() {
      synchronized (DisplayManagerService.this.mSyncRoot) {
        this.mChanged = false;
        DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
            this.mSpecsChangedConsumer, false);
        if (this.mChanged) {
          DisplayManagerService.this.scheduleTraversalLocked(false);
          this.mChanged = false;
        }
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE
            && DisplayManagerService.this
                .mDisplayModeDirector
                .getRefreshRateModeManager()
                .getController()
                .consumeUpdateRefreshRateMode()) {
          DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(
              new Consumer() { // from class:
                               // com.android.server.display.DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayManagerService.DesiredDisplayModeSpecsObserver.this
                      .lambda$onDesiredDisplayModeSpecsChanged$1((LogicalDisplay) obj);
                }
              });
        }
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDesiredDisplayModeSpecsChanged$1(
        LogicalDisplay logicalDisplay) {
      if (logicalDisplay.getDisplayInfoLocked().type == 1) {
        DisplayManagerService.this.handleLogicalDisplayChangedLocked(logicalDisplay);
      }
    }
  }

  public class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
    public int mBaseState = -1;

    public DeviceStateListener() {}

    public void onStateChanged(int i) {
      boolean z = i != this.mBaseState;
      synchronized (DisplayManagerService.this.mSyncRoot) {
        Message obtainMessage = DisplayManagerService.this.mHandler.obtainMessage(9);
        obtainMessage.arg1 = i;
        DisplayManagerService.this.mHandler.sendMessage(obtainMessage);
        if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
          int i2 = DisplayManagerService.this.mDualScreenPolicy;
          DisplayManagerService.this.mDualScreenPolicy = PowerManagerUtil.getDualScreenPolicy(i);
          if (DisplayManagerService.this.mDualScreenPolicy != i2
              && DisplayManagerService.this.mDualScreenPolicy == 0
              && i2 != -1) {
            DisplayManagerService.this.mNeedSkipDozeState = true;
          } else if (DisplayManagerService.this.mDualScreenPolicy == 1) {
            DisplayManagerService.this.mNeedSkipDozeState = false;
          }
        }
        DisplayManagerService.this.mLogicalDisplayMapper.setDeviceStateLocked(i, z);
      }
    }

    public void onBaseStateChanged(int i) {
      this.mBaseState = i;
    }
  }

  public class BrightnessPair {
    public float brightness;
    public float sdrBrightness;

    public BrightnessPair(float f, float f2) {
      this.brightness = f;
      this.sdrBrightness = f2;
    }
  }

  public class DisplayStateLimitLock implements IBinder.DeathRecipient {
    public int mLastRequestedState;
    public long mLastRequestedTime;
    public final IBinder mLock;
    public final String mTag = PowerManagerUtil.callerInfoToString();

    public DisplayStateLimitLock(IBinder iBinder, int i, long j) {
      this.mLock = iBinder;
      this.mLastRequestedState = i;
      this.mLastRequestedTime = j;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      synchronized (DisplayManagerService.this.mRequestDisplayStateLock) {
        Slog.m72d("DisplayManagerService", "DisplayStateLimitLock: binderDied: " + this);
        if (DisplayManagerService.this.mDisplayStateLimitLocks.indexOf(this) < 0) {
          return;
        }
        DisplayManagerService.this.setDisplayStateLimitInternal(this.mLock, 0);
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(" " + Display.stateToString(this.mLastRequestedState));
      sb.append(" (lock:" + Objects.hashCode(this.mLock));
      sb.append(", t:" + this.mLastRequestedTime + ")");
      StringBuilder sb2 = new StringBuilder();
      sb2.append(" ");
      sb2.append(this.mTag);
      sb.append(sb2.toString());
      return sb.toString();
    }
  }

  public class PresentationDisplay {
    public int displayId;
    public String packageName;

    public PresentationDisplay(int i, String str) {
      this.displayId = i;
      this.packageName = str;
    }

    public int hashCode() {
      int i = this.displayId;
      String str = this.packageName;
      return i * (str == null ? -1 : str.hashCode());
    }

    public boolean equals(Object obj) {
      if (obj != null) {
        PresentationDisplay presentationDisplay = (PresentationDisplay) obj;
        if (presentationDisplay.displayId == this.displayId
            && presentationDisplay.packageName.equals(this.packageName)) {
          return true;
        }
      }
      return false;
    }

    public int getDisplayId() {
      return this.displayId;
    }

    public String getPackageName() {
      return this.packageName;
    }
  }

  public void hideCutoutForFoldableInternal(boolean z) {
    synchronized (this.mSyncRoot) {
      LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(0);
      LogicalDisplay displayLocked2 = this.mLogicalDisplayMapper.getDisplayLocked(1);
      if (displayLocked != null && displayLocked2 != null) {
        Slog.m72d("DisplayManagerService", "hideCutoutForFoldableInternal, hideCutout=" + z);
        displayLocked.setMaskingCutout(z);
        displayLocked2.setMaskingCutout(z);
        displayLocked.updateLocked(this.mDisplayDeviceRepo);
        displayLocked2.updateLocked(this.mDisplayDeviceRepo);
      }
    }
  }

  public final IBinder getDisplayTokenForCurrentLayerStackLocked(int i) {
    DisplayDevice primaryDisplayDeviceLocked;
    LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
    if (displayLocked == null
        || (primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked()) == null) {
      return null;
    }
    return this.mDisplayDeviceRepo.getDisplayTokenForCurrentLayerStackLocked(
        primaryDisplayDeviceLocked, displayLocked.getDisplayInfoLocked().layerStack);
  }

  public void setHdrRampRate(float f, float f2) {
    ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0)).setHdrRampRate(f, f2);
  }
}
