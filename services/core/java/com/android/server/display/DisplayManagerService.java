package com.android.server.display;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.compat.CompatChanges;
import android.companion.virtual.IVirtualDevice;
import android.content.BroadcastReceiver;
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
import android.hardware.SensorManager;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.devicestate.DeviceStateManagerInternal;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.Curve;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.DisplayViewport;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.hardware.display.HdrConversionMode;
import android.hardware.display.IDisplayManager;
import android.hardware.display.IDisplayManagerCallback;
import android.hardware.display.IHbmBrightnessCallback;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.VirtualDisplayConfig;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.hardware.input.HostUsiVersion;
import android.hardware.usb.UsbManager;
import android.media.RemoteDisplay;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.IThermalService;
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
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Spline;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayEventReceiver;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import android.view.Surface;
import android.view.SurfaceControl;
import android.window.DisplayWindowPolicyController;
import android.window.ScreenCapture;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.foldables.FoldLockSettingAvailabilityProvider;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.SettingsWrapper;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnimationThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.LocalServices;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.DisplayPowerController;
import com.android.server.display.ExternalDisplayPolicy;
import com.android.server.display.ExternalDisplayPolicy.SkinThermalStatusObserver;
import com.android.server.display.ExternalDisplayStatsService;
import com.android.server.display.LocalDisplayAdapter;
import com.android.server.display.OverlayDisplayAdapter;
import com.android.server.display.PersistentDataStore;
import com.android.server.display.VirtualDisplayAdapter;
import com.android.server.display.VolumeController.AnonymousClass3;
import com.android.server.display.WifiDisplayAdapter;
import com.android.server.display.WifiDisplayController;
import com.android.server.display.brightness.DisplayBrightnessController;
import com.android.server.display.brightness.strategy.AutoBrightnessFallbackStrategy;
import com.android.server.display.config.SensorData;
import com.android.server.display.feature.DeviceConfigParameterProvider;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.mode.RefreshRateController;
import com.android.server.display.mode.RefreshRateController.RefreshRateMaxLimitToken;
import com.android.server.display.mode.RefreshRateModeManager;
import com.android.server.display.mode.RefreshRateToken;
import com.android.server.display.mode.RefreshRateTokenController;
import com.android.server.display.mode.SizeVote;
import com.android.server.display.mode.SystemRequestObserver;
import com.android.server.display.mode.Vote;
import com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector;
import com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector$$ExternalSyntheticLambda0;
import com.android.server.display.notifications.DisplayNotificationManager;
import com.android.server.display.utils.DebugUtils;
import com.android.server.display.utils.SensorUtils;
import com.android.server.input.InputManagerService;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.android.server.utils.FoldSettingProvider;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.os.SemDvfsManager;
import com.samsung.android.provider.Feature;
import com.samsung.android.provider.SemDynamicFeature;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

public final class DisplayManagerService extends SystemService {
    public static boolean DEBUG = DebugUtils.isDebuggable("DisplayManagerService");
    public static final int[] EMPTY_ARRAY = new int[0];
    public static final HdrConversionMode HDR_CONVERSION_MODE_UNSUPPORTED = new HdrConversionMode(0);
    public final SimpleDateFormat dateFormat;
    public ActivityManagerInternal mActivityManagerInternal;
    public boolean mAreUserDisabledHdrTypesAllowed;
    public boolean mBootCompleted;
    public RefreshRateToken mBrightnessAnimRefreshRateToken;
    public boolean mBrightnessAnimStarted;
    public final BrightnessSynchronizer mBrightnessSynchronizer;
    public BrightnessTracker mBrightnessTracker;
    public final SparseArray mCallbacks;
    public final DeviceConfigParameterProvider mConfigParameterProvider;
    public int mConnectedExternalDisplayId;
    public final Context mContext;
    public int mCurrentUserId;
    public final int mDefaultDisplayDefaultColorMode;
    public int mDefaultDisplayTopInset;
    public final int mDefaultHdrConversionMode;
    public DeviceStateManagerInternal mDeviceStateManager;
    public final SparseArray mDisplayAccessUIDs;
    public final ArrayList mDisplayAdapters;
    public final AnonymousClass1 mDisplayBlanker;
    public final ArrayList mDisplayBrightnessListeners;
    public final SparseArray mDisplayBrightnesses;
    public final DisplayDeviceRepository mDisplayDeviceRepo;
    public final CopyOnWriteArrayList mDisplayGroupListeners;
    public final DisplayModeDirector mDisplayModeDirector;
    public final DisplayNotificationManager mDisplayNotificationManager;
    public DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
    public final SparseArray mDisplayPowerControllers;
    public final ArrayList mDisplayStateListeners;
    public final ArrayList mDisplayStateOverrideLocks;
    public final SparseIntArray mDisplayStateOverrides;
    public final SparseIntArray mDisplayStates;
    public final CopyOnWriteArrayList mDisplayTransactionListeners;
    public final SparseArray mDisplayWindowPolicyControllers;
    public int mDualScreenPolicy;
    public LogicalDisplay mEnabledDexDisplay;
    public final ExternalDisplayPolicy mExternalDisplayPolicy;
    public final ExternalDisplayStatsService mExternalDisplayStatsService;
    public final boolean mExtraDisplayEventLogging;
    public final String mExtraDisplayLoggingPackageName;
    public final DisplayManagerFlags mFlags;
    public int mForceListenProcessId;
    public final DisplayManagerHandler mHandler;
    public final SparseArray mHbmBrightnessCallbacks;
    public HdrConversionMode mHdrConversionMode;
    public final HighBrightnessModeMetadataMapper mHighBrightnessModeMetadataMapper;
    public final AnonymousClass2 mIdleModeReceiver;
    public final Injector mInjector;
    public InputManagerService.LocalService mInputManagerInternal;
    public boolean mIsDocked;
    public boolean mIsDreaming;
    public boolean mIsHbmEnabled;
    public volatile boolean mIsHdrOutputControlEnabled;
    public final boolean mIsMtkPtHintExist;
    public final LogicalDisplayMapper mLogicalDisplayMapper;
    public boolean mMinimalPostProcessingAllowed;
    public final Curve mMinimumBrightnessCurve;
    public final Spline mMinimumBrightnessSpline;
    public final SemDvfsManager mMtKPowerThrottling;
    public final OverlayProperties mOverlayProperties;
    public HdrConversionMode mOverrideHdrConversionMode;
    public final SparseArray mPendingCallbackSelfLocked;
    public boolean mPendingTraversal;
    public final PersistentDataStore mPersistentDataStore;
    public Handler mPowerHandler;
    public final HashSet mPresentationDisplays;
    public IMediaProjectionManager mProjectionService;
    public final Object mRequestDisplayStateLock;
    public final AnonymousClass2 mResolutionRestoreReceiver;
    public final AnonymousClass2 mRotationChangeReceiver;
    public boolean mSafeMode;
    public final float mScreenExtendedBrightnessRangeMaximum;
    public SensorManager mSensorManager;
    public SmallAreaDetectionController mSmallAreaDetectionController;
    public Point mStableDisplaySize;
    public int[] mSupportedHdrOutputType;
    public final SyncRoot mSyncRoot;
    public int mSystemPreferredHdrOutputType;
    public boolean mSystemReady;
    public final ArrayList mTempCallbacks;
    public final ArrayList mTempViewports;
    public final Handler mUiHandler;
    public final UidImportanceListener mUidImportanceListener;
    public int[] mUserDisabledHdrTypes;
    public Display.Mode mUserPreferredMode;
    public final ArrayList mViewports;
    public VirtualDisplayAdapter mVirtualDisplayAdapter;
    public VolumeController mVolumeController;
    public final ColorSpace mWideColorSpace;
    public WifiDisplayAdapter mWifiDisplayAdapter;
    public int mWifiDisplayScanRequestCount;
    public WindowManagerInternal mWindowManagerInternal;

    /* renamed from: com.android.server.display.DisplayManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements DisplayBlanker {
        public /* synthetic */ AnonymousClass1() {
        }
    }

    /* renamed from: com.android.server.display.DisplayManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DisplayManagerService this$0;

        public /* synthetic */ AnonymousClass2(DisplayManagerService displayManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = displayManagerService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        this.this$0.mIsDocked = intExtra == 1 || intExtra == 3 || intExtra == 4;
                    }
                    if ("android.intent.action.DREAMING_STARTED".equals(intent.getAction())) {
                        this.this$0.mIsDreaming = true;
                    } else if ("android.intent.action.DREAMING_STOPPED".equals(intent.getAction())) {
                        this.this$0.mIsDreaming = false;
                    }
                    DisplayManagerService displayManagerService = this.this$0;
                    displayManagerService.setDockedAndIdleEnabled(displayManagerService.mIsDocked && displayManagerService.mIsDreaming);
                    return;
                case 1:
                    if ("android.os.action.SETTING_RESTORED".equals(intent.getAction()) && "screen_resolution_mode".equals(intent.getExtra("setting_name"))) {
                        DisplayManagerService displayManagerService2 = this.this$0;
                        int intForUser = Settings.Secure.getIntForUser(displayManagerService2.mContext.getContentResolver(), "screen_resolution_mode", 0, -2);
                        if (intForUser == 0) {
                            return;
                        }
                        synchronized (displayManagerService2.mSyncRoot) {
                            try {
                                LogicalDisplay displayLocked = displayManagerService2.mLogicalDisplayMapper.getDisplayLocked(0, true);
                                DisplayDevice displayDevice = displayLocked == null ? null : displayLocked.mPrimaryDisplayDevice;
                                if (displayDevice == null) {
                                    Slog.w("DisplayManagerService", "No default display device present to restore resolution mode");
                                    return;
                                }
                                Point[] supportedResolutionsLocked = displayDevice.getSupportedResolutionsLocked();
                                if (supportedResolutionsLocked.length != 2) {
                                    if (DisplayManagerService.DEBUG) {
                                        Slog.d("DisplayManagerService", "Skipping resolution restore - " + supportedResolutionsLocked.length);
                                    }
                                    return;
                                }
                                Point point = supportedResolutionsLocked[intForUser == 1 ? (char) 0 : (char) 1];
                                Display.Mode mode = new Display.Mode(point.x, point.y, FullScreenMagnificationGestureHandler.MAX_SCALE);
                                Slog.i("DisplayManagerService", "Restoring resolution from backup: (" + intForUser + ") " + point.x + "x" + point.y);
                                displayManagerService2.setUserPreferredDisplayModeInternal(0, mode);
                                return;
                            } finally {
                            }
                        }
                    }
                    return;
                default:
                    if ("com.samsung.intent.action.ROTATION_CHANGED".equals(intent.getAction())) {
                        final int intExtra2 = intent.getIntExtra("rotation", 0);
                        if (intent.getBooleanExtra("updateDisplayDevice", false)) {
                            synchronized (this.this$0.mSyncRoot) {
                                try {
                                    WifiDisplayAdapter wifiDisplayAdapter = this.this$0.mWifiDisplayAdapter;
                                    if (wifiDisplayAdapter == null) {
                                        return;
                                    }
                                    WifiDisplayAdapter.WifiDisplayDevice wifiDisplayDevice = wifiDisplayAdapter.mDisplayDevice;
                                    if (wifiDisplayDevice != null) {
                                        wifiDisplayDevice.mRotationForHiddenDisplay = intExtra2;
                                    }
                                } finally {
                                }
                            }
                        }
                        this.this$0.mHandler.postDelayed(new Runnable() { // from class: com.android.server.display.DisplayManagerService$4$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DisplayManagerService.AnonymousClass2 anonymousClass2 = DisplayManagerService.AnonymousClass2.this;
                                int i = intExtra2;
                                DisplayManagerService displayManagerService3 = anonymousClass2.this$0;
                                synchronized (displayManagerService3.mSyncRoot) {
                                    try {
                                        WifiDisplayAdapter wifiDisplayAdapter2 = displayManagerService3.mWifiDisplayAdapter;
                                        if (wifiDisplayAdapter2 == null) {
                                            return;
                                        }
                                        WifiDisplayAdapter.WifiDisplayDevice wifiDisplayDevice2 = wifiDisplayAdapter2.mDisplayDevice;
                                        if (wifiDisplayDevice2 == null) {
                                            return;
                                        }
                                        LogicalDisplay displayLocked2 = displayManagerService3.mLogicalDisplayMapper.getDisplayLocked(wifiDisplayDevice2);
                                        if (displayLocked2 == null) {
                                            return;
                                        }
                                        int i2 = displayLocked2.mDisplayId;
                                        WifiDisplayAdapter.WifiDisplayDevice wifiDisplayDevice3 = displayManagerService3.mWifiDisplayAdapter.mDisplayDevice;
                                        if (wifiDisplayDevice3 != null) {
                                            wifiDisplayDevice3.mCurrentDisplayRotation = i;
                                        }
                                        WindowManagerService asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
                                        asInterface.setFixedToUserRotation(i2, 2);
                                        asInterface.freezeDisplayRotation(i2, i, "SmartView");
                                    } finally {
                                    }
                                }
                            }
                        }, intent.getBooleanExtra("waitForDeviceAdded", false) ? 300L : 0L);
                        return;
                    }
                    return;
            }
        }
    }

    /* renamed from: com.android.server.display.DisplayManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            ICustomFrequencyManager asInterface;
            IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
            if (service == null || (asInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                return;
            }
            try {
                asInterface.sendTid(Process.myPid(), AnimationThread.get().getThreadId(), 4);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.android.server.display.DisplayManagerService$6, reason: invalid class name */
    public final class AnonymousClass6 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            if (action.equals("com.sec.df.changed.DISPLAY_MANAGER.ENABLE_DEBUG_LOG")) {
                DisplayManagerService.DEBUG = ((SemDynamicFeature.Properties) intent.getParcelableExtra("PROPERTY_CARGO", SemDynamicFeature.Properties.class)).getBoolean("ENABLE_DEBUG_LOG", DisplayManagerService.DEBUG);
                Slog.d("DynamicFeature_Display", "Debug value changed to " + DisplayManagerService.DEBUG + " by DynamicFeature ");
            }
        }
    }

    final class BinderService extends IDisplayManager.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public BinderService() {
        }

        public final IRefreshRateToken acquireLowRefreshRateToken(IBinder iBinder, String str) {
            RefreshRateController.LowRefreshRateToken lowRefreshRateToken;
            if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager.getController().getClass();
                RefreshRateTokenController refreshRateTokenController = RefreshRateController.mRefreshRateTokenController;
                lowRefreshRateToken = new RefreshRateController.LowRefreshRateToken();
                String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("LowRefreshRateToken:", str);
                long uptimeMillis = SystemClock.uptimeMillis();
                RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo = new RefreshRateToken.RefreshRateTokenInfo();
                refreshRateTokenInfo.mToken = iBinder;
                refreshRateTokenInfo.mTag = m;
                refreshRateTokenInfo.mAcquireTime = uptimeMillis;
                refreshRateTokenInfo.mRefreshRate = 0;
                refreshRateTokenController.createRefreshRateToken(lowRefreshRateToken, refreshRateTokenInfo);
            }
            return lowRefreshRateToken;
        }

        public final IRefreshRateToken acquirePassiveModeToken(IBinder iBinder, String str) {
            RefreshRateController.PassiveModeToken createPassiveModeToken;
            if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                createPassiveModeToken = DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager.getController().createPassiveModeToken(iBinder, str);
            }
            return createPassiveModeToken;
        }

        public final IRefreshRateToken acquireRefreshRateMaxLimitToken(IBinder iBinder, int i, String str) {
            RefreshRateController.RefreshRateMaxLimitToken refreshRateMaxLimitToken;
            if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                RefreshRateController controller = DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager.getController();
                controller.getClass();
                RefreshRateTokenController refreshRateTokenController = RefreshRateController.mRefreshRateTokenController;
                refreshRateMaxLimitToken = controller.new RefreshRateMaxLimitToken();
                String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("RefreshRateMaxLimitToken:", str);
                long uptimeMillis = SystemClock.uptimeMillis();
                RefreshRateToken.RefreshRateTokenInfo refreshRateTokenInfo = new RefreshRateToken.RefreshRateTokenInfo();
                refreshRateTokenInfo.mToken = iBinder;
                refreshRateTokenInfo.mTag = m;
                refreshRateTokenInfo.mAcquireTime = uptimeMillis;
                refreshRateTokenInfo.mRefreshRate = i;
                refreshRateTokenController.createRefreshRateToken(refreshRateMaxLimitToken, refreshRateTokenInfo);
            }
            return refreshRateMaxLimitToken;
        }

        public final IRefreshRateToken acquireRefreshRateMinLimitToken(IBinder iBinder, int i, String str) {
            RefreshRateController.RefreshRateMinLimitToken createRefreshRateMinLimitToken;
            if (!CoreRune.FW_VRR_REFRESH_RATE_TOKEN || !isAllowedRefreshRateToken()) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager.getController().getClass();
                createRefreshRateMinLimitToken = RefreshRateController.createRefreshRateMinLimitToken(i, iBinder, str);
            }
            return createRefreshRateMinLimitToken;
        }

        public final boolean areUserDisabledHdrTypesAllowed() {
            boolean z;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                z = DisplayManagerService.this.mAreUserDisabledHdrTypesAllowed;
            }
            return z;
        }

        public final void connectWifiDisplay(String str) {
            if (str == null) {
                throw new IllegalArgumentException("address must not be null");
            }
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to connect to a wifi display");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m437$$Nest$mconnectWifiDisplayInternal(DisplayManagerService.this, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void connectWifiDisplayWithConfig(final SemWifiDisplayConfig semWifiDisplayConfig, final IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) {
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to connect to a wifi display");
            Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    final WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter != null) {
                        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.9
                            public final /* synthetic */ IWifiDisplayConnectionCallback val$callback;
                            public final /* synthetic */ SemWifiDisplayConfig val$wifidisplayConfig;

                            public AnonymousClass9(final SemWifiDisplayConfig semWifiDisplayConfig2, final IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback2) {
                                r2 = semWifiDisplayConfig2;
                                r3 = iWifiDisplayConnectionCallback2;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                WifiDisplayController wifiDisplayController = WifiDisplayAdapter.this.mDisplayController;
                                if (wifiDisplayController != null) {
                                    SemWifiDisplayConfig semWifiDisplayConfig2 = r2;
                                    wifiDisplayController.mCallback = r3;
                                    wifiDisplayController.mWifiDisplayConfig = semWifiDisplayConfig2;
                                    Log.d("WifiDisplayController", "requestConnect:: " + semWifiDisplayConfig2.toString());
                                    if (wifiDisplayController.mWifiDisplayConfig.getConnectionType() == 1) {
                                        wifiDisplayController.requestConnect(wifiDisplayController.mWifiDisplayConfig.getP2pMacAddress());
                                        return;
                                    }
                                    if (wifiDisplayController.mWifiDisplayConfig.getConnectionType() == 2 || wifiDisplayController.mWifiDisplayConfig.getConnectionType() == 3) {
                                        WifiP2pDevice wifiP2pDevice = new WifiP2pDevice();
                                        wifiP2pDevice.deviceAddress = wifiDisplayController.mWifiDisplayConfig.getMode() == 3 ? wifiDisplayController.mWifiDisplayConfig.getIpAddress() : wifiDisplayController.mWifiDisplayConfig.getP2pMacAddress();
                                        wifiP2pDevice.deviceName = wifiDisplayController.mWifiDisplayConfig.getDeviceName();
                                        wifiDisplayController.mConnectedDevice = wifiP2pDevice;
                                        wifiDisplayController.mDesiredDevice = wifiP2pDevice;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(wifiDisplayController.mWifiDisplayConfig.getIpAddress());
                                        sb.append(":");
                                        sb.append((wifiP2pDevice.deviceName.startsWith("DIRECT-") && wifiP2pDevice.deviceName.endsWith("Broadcom")) ? 8554 : 7236);
                                        wifiDisplayController.mRemoteDisplayInterface = sb.toString();
                                        if (wifiDisplayController.mScanRequested) {
                                            wifiDisplayController.mScanRequested = false;
                                            wifiDisplayController.updateScanState();
                                        }
                                        WifiDisplay createWifiDisplay = wifiDisplayController.createWifiDisplay(wifiDisplayController.mConnectedDevice);
                                        createWifiDisplay.setState(6);
                                        createWifiDisplay.setFlags(wifiDisplayController.mWifiDisplayConfig.getFlags());
                                        createWifiDisplay.setMode(wifiDisplayController.mWifiDisplayConfig.getMode());
                                        wifiDisplayController.advertiseDisplay(createWifiDisplay, null, 0, 0, 0);
                                        wifiDisplayController.sendEventToSemDeviceStatusListener(1);
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Listening for RTSP connection from Wifi display via AP : "), wifiDisplayController.mConnectedDevice.deviceName, "WifiDisplayController");
                                        wifiDisplayController.mRemoteDisplay = RemoteDisplay.listen(wifiDisplayController.mRemoteDisplayInterface, wifiDisplayController.remoteDisplayListener, wifiDisplayController.mHandler, wifiDisplayController.mContext.getOpPackageName(), wifiDisplayController.getEngineParameters(), wifiDisplayController.mNativeListener);
                                        wifiDisplayController.mHandler.postDelayed(wifiDisplayController.mRtspTimeout, 30000L);
                                    }
                                }
                            }
                        });
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int convertToBrightness(float f) {
            int convertToBrightness;
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", "Permission required to covert the nits to brightness");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    BrightnessMappingStrategy brightnessMappingStrategy = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).defaultModeBrightnessMapper;
                    convertToBrightness = brightnessMappingStrategy != null ? brightnessMappingStrategy.convertToBrightness(f) : -1;
                    Slog.d("DisplayManagerService", "[api] convertToBrightness: " + f + " -> " + convertToBrightness);
                }
                return convertToBrightness;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str) {
            return DisplayManagerService.m438$$Nest$mcreateVirtualDisplayInternal(DisplayManagerService.this, virtualDisplayConfig, iVirtualDisplayCallback, iMediaProjection, null, null, str);
        }

        public final void disableConnectedDisplay(int i) {
            disableConnectedDisplay_enforcePermission();
            DisplayManagerService.this.enableConnectedDisplay(i, false);
        }

        public final void disconnectWifiDisplay() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                        if (wifiDisplayAdapter != null) {
                            wifiDisplayAdapter.requestDisconnectLocked();
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(DisplayManagerService.this.mContext, "DisplayManagerService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DisplayManagerService.m444$$Nest$mdumpInternal(DisplayManagerService.this, printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void enableConnectedDisplay(int i) {
            enableConnectedDisplay_enforcePermission();
            DisplayManagerService.this.enableConnectedDisplay(i, true);
        }

        public final void fitToActiveDisplay(boolean z) {
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "no permission to call fitToActiveDisplay(boolean)");
            synchronized (DisplayManagerService.this.mSyncRoot) {
                WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                if (wifiDisplayAdapter != null) {
                    wifiDisplayAdapter.getClass();
                    DeviceIdleController$$ExternalSyntheticOutline0.m("fitToActiveDisplayLocked, status : ", "WifiDisplayAdapter", z);
                    PersistentDataStore persistentDataStore = wifiDisplayAdapter.mPersistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    if (z != persistentDataStore.mIsFitToActiveDisplay) {
                        persistentDataStore.mIsFitToActiveDisplay = z;
                        persistentDataStore.mDirty = true;
                        persistentDataStore.saveIfNeeded();
                    }
                    ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass11(wifiDisplayAdapter, !z, 0));
                }
            }
        }

        public final void forgetWifiDisplay(String str) {
            if (str == null) {
                throw new IllegalArgumentException("address must not be null");
            }
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to forget to a wifi display");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                        if (wifiDisplayAdapter != null) {
                            wifiDisplayAdapter.requestForgetLocked(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final float getAdaptiveBrightness(int i, float f) {
            float f2;
            BrightnessMappingStrategy brightnessMappingStrategy;
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", "Permission required to get the display's adaptive brightness");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i);
                    if (displayPowerControllerInterface != null) {
                        DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
                        f2 = displayPowerController.mAppliedForceDimming ? displayPowerController.mAppliedForceDimmingBrightness : (Float.compare(f, FullScreenMagnificationGestureHandler.MAX_SCALE) == -1 || (brightnessMappingStrategy = displayPowerController.defaultModeBrightnessMapper) == null) ? -1.0f : brightnessMappingStrategy.getBrightness(null, f, -1);
                    } else {
                        f2 = Float.NaN;
                    }
                }
                return f2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice getAmbientBrightnessStats() {
            ParceledListSlice ambientBrightnessStats;
            getAmbientBrightnessStats_enforcePermission();
            int userId = UserHandle.getUserId(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    Slog.d("DisplayManagerService", "[api] getAmbientBrightnessStats: u:" + userId + ": " + ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).getAmbientBrightnessStats(userId) + PowerManagerUtil.callerInfoToString(false));
                    ambientBrightnessStats = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).getAmbientBrightnessStats(userId);
                }
                return ambientBrightnessStats;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final BrightnessConfiguration getBackupBrightnessConfiguration(int i) {
            BrightnessConfiguration brightnessConfiguration;
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", new StringBuilder("[api] getBackupBrightnessConfiguration: "), false);
            BrightnessConfiguration brightnessConfiguration2 = null;
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int userSerialNumber = DisplayManagerService.this.getUserManager().getUserSerialNumber(i);
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    try {
                        PersistentDataStore persistentDataStore = DisplayManagerService.this.mPersistentDataStore;
                        persistentDataStore.loadIfNeeded();
                        brightnessConfiguration = (BrightnessConfiguration) persistentDataStore.mGlobalBrightnessConfigurations.mConfigurations.get(userSerialNumber);
                        if (brightnessConfiguration == null) {
                            Slog.d("DisplayManagerService", "getBackupBrightnessConfiguration: learned config did not exist");
                            AutomaticBrightnessController automaticBrightnessController = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mAutomaticBrightnessController;
                            if (automaticBrightnessController != null) {
                                brightnessConfiguration2 = ((BrightnessMappingStrategy) automaticBrightnessController.mBrightnessMappingStrategyMap.get(0)).getDefaultConfig();
                            }
                            brightnessConfiguration = brightnessConfiguration2;
                        }
                        Slog.d("DisplayManagerService", "[api] getBackupBrightnessConfiguration: " + brightnessConfiguration);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return brightnessConfiguration;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final float getBrightness(int i) {
            float screenBrightnessSetting;
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", "Permission required to set the display's brightness");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i);
                    screenBrightnessSetting = displayPowerControllerInterface != null ? ((DisplayPowerController) displayPowerControllerInterface).mDisplayBrightnessController.getScreenBrightnessSetting() : Float.NaN;
                }
                return screenBrightnessSetting;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final BrightnessConfiguration getBrightnessConfigurationForDisplay(String str, int i) {
            BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked;
            getBrightnessConfigurationForDisplay_enforcePermission();
            if (i != UserHandle.getCallingUserId()) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to read the display brightness configuration of another user");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            boolean z = DisplayManagerService.DEBUG;
            int userSerialNumber = displayManagerService.getUserManager().getUserSerialNumber(i);
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    try {
                        brightnessConfigForDisplayWithPdsFallbackLocked = DisplayManagerService.this.getBrightnessConfigForDisplayWithPdsFallbackLocked(userSerialNumber, str);
                        if (brightnessConfigForDisplayWithPdsFallbackLocked == null) {
                            Slog.d("DisplayManagerService", "[api] getBrightnessConfigurationForDisplay: config is null");
                            DisplayManagerService displayManagerService2 = DisplayManagerService.this;
                            LogicalDisplay displayLocked = displayManagerService2.mLogicalDisplayMapper.getDisplayLocked(displayManagerService2.mDisplayDeviceRepo.getByUniqueIdLocked(str));
                            DisplayPowerControllerInterface displayPowerControllerInterface = displayLocked != null ? (DisplayPowerControllerInterface) displayManagerService2.mDisplayPowerControllers.get(displayLocked.mDisplayId) : null;
                            if (displayPowerControllerInterface != null) {
                                AutomaticBrightnessController automaticBrightnessController = ((DisplayPowerController) displayPowerControllerInterface).mAutomaticBrightnessController;
                                brightnessConfigForDisplayWithPdsFallbackLocked = automaticBrightnessController == null ? null : ((BrightnessMappingStrategy) automaticBrightnessController.mBrightnessMappingStrategyMap.get(0)).getDefaultConfig();
                            }
                        }
                        Slog.d("DisplayManagerService", "[api] getBrightnessConfigurationForDisplay: " + brightnessConfigForDisplayWithPdsFallbackLocked + PowerManagerUtil.callerInfoToString(false));
                    } finally {
                    }
                }
                return brightnessConfigForDisplayWithPdsFallbackLocked;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
            String str;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                str = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(0, true).mPrimaryDisplayDevice.mUniqueId;
            }
            return getBrightnessConfigurationForDisplay(str, i);
        }

        public final ParceledListSlice getBrightnessEvents(String str) {
            ParceledListSlice brightnessEvents;
            getBrightnessEvents_enforcePermission();
            int callingUid = Binder.getCallingUid();
            int noteOp = ((AppOpsManager) DisplayManagerService.this.mContext.getSystemService(AppOpsManager.class)).noteOp(43, callingUid, str);
            boolean z = true;
            if (noteOp != 3 ? noteOp != 0 : DisplayManagerService.this.mContext.checkCallingPermission("android.permission.PACKAGE_USAGE_STATS") != 0) {
                z = false;
            }
            int userId = UserHandle.getUserId(callingUid);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    Slog.d("DisplayManagerService", "[api] getBrightnessEvents: u:" + userId + ", us:" + z + " " + ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).getBrightnessEvents(userId, z) + PowerManagerUtil.callerInfoToString(false));
                    brightnessEvents = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).getBrightnessEvents(userId, z);
                }
                return brightnessEvents;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final BrightnessInfo getBrightnessInfo(int i) {
            getBrightnessInfo_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, false);
                    if (displayLocked != null && displayLocked.mIsEnabled) {
                        DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i);
                        if (displayPowerControllerInterface == null) {
                            return null;
                        }
                        return ((DisplayPowerController) displayPowerControllerInterface).getBrightnessInfo();
                    }
                    return null;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final BrightnessConfiguration getDefaultBrightnessConfiguration() {
            getDefaultBrightnessConfiguration_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    PersistentDataStore persistentDataStore = DisplayManagerService.this.mPersistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    BrightnessConfiguration brightnessConfiguration = (BrightnessConfiguration) persistentDataStore.mGlobalBrightnessConfigurations.mConfigurations.get(0);
                    if (brightnessConfiguration != null && "sec-backup".equals(brightnessConfiguration.getDescription())) {
                        Pair curve = brightnessConfiguration.getCurve();
                        BrightnessConfiguration.Builder builder = new BrightnessConfiguration.Builder((float[]) curve.first, (float[]) curve.second);
                        builder.setDescription("");
                        Slog.d("DisplayManagerService", "[api] getDefaultBrightnessConfiguration(backup config is applied) :" + builder.build() + PowerManagerUtil.callerInfoToString(false));
                        return builder.build();
                    }
                    StringBuilder sb = new StringBuilder("[api] getDefaultBrightnessConfiguration: ");
                    AutomaticBrightnessController automaticBrightnessController = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mAutomaticBrightnessController;
                    BrightnessConfiguration brightnessConfiguration2 = null;
                    sb.append(automaticBrightnessController == null ? null : ((BrightnessMappingStrategy) automaticBrightnessController.mBrightnessMappingStrategyMap.get(0)).getDefaultConfig());
                    sb.append(PowerManagerUtil.callerInfoToString(false));
                    Slog.d("DisplayManagerService", sb.toString());
                    AutomaticBrightnessController automaticBrightnessController2 = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mAutomaticBrightnessController;
                    if (automaticBrightnessController2 != null) {
                        brightnessConfiguration2 = ((BrightnessMappingStrategy) automaticBrightnessController2.mBrightnessMappingStrategyMap.get(0)).getDefaultConfig();
                    }
                    return brightnessConfiguration2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getDeviceMaxVolume() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                    if (volumeController != null) {
                        return volumeController.mDlnaController.mDevice.isConnected() ? 100 : volumeController.mMaxVolume;
                    }
                    return -1;
                } finally {
                }
            }
        }

        public final int getDeviceMinVolume() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                    if (volumeController != null) {
                        return volumeController.mDlnaController.mDevice.isConnected() ? 0 : volumeController.mMinVolume;
                    }
                    return -1;
                } finally {
                }
            }
        }

        public final DisplayDecorationSupport getDisplayDecorationSupport(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                boolean z = DisplayManagerService.DEBUG;
                IBinder displayToken = displayManagerService.getDisplayToken(i);
                return displayToken == null ? null : SurfaceControl.getDisplayDecorationSupport(displayToken);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int[] getDisplayIds(boolean z) {
            int[] iArr;
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    LogicalDisplayMapper logicalDisplayMapper = DisplayManagerService.this.mLogicalDisplayMapper;
                    int size = logicalDisplayMapper.mLogicalDisplays.size();
                    iArr = new int[size];
                    int i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        LogicalDisplay logicalDisplay = (LogicalDisplay) logicalDisplayMapper.mLogicalDisplays.valueAt(i2);
                        if ((logicalDisplay.mIsEnabled || z || logicalDisplay.mDisplayId == 0) && logicalDisplay.getDisplayInfoLocked().hasAccess(callingUid)) {
                            iArr[i] = logicalDisplayMapper.mLogicalDisplays.keyAt(i2);
                            i++;
                        }
                    }
                    if (i != size) {
                        iArr = Arrays.copyOfRange(iArr, 0, i);
                    }
                }
                return iArr;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final DisplayInfo getDisplayInfo(int i) {
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                boolean z = DisplayManagerService.DEBUG;
                return displayManagerService.getDisplayInfoInternal(i, callingUid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final SemDlnaDevice getDlnaDevice() {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call getDlnaDevice");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter != null) {
                        return wifiDisplayAdapter.mDlnaController.mDevice;
                    }
                    return new SemDlnaDevice();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final HdrConversionMode getHdrConversionMode() {
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

        public final HdrConversionMode getHdrConversionModeSetting() {
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

        public final Curve getMinimumBrightnessCurve() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DisplayManagerService.this.getMinimumBrightnessCurveInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final OverlayProperties getOverlaySupport() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DisplayManagerService.this.mOverlayProperties;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getPreferredWideGamutColorSpaceId() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DisplayManagerService.this.mWideColorSpace.getId();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getPresentationOwner(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked != null && displayLocked.mHasContent && !DisplayManagerService.this.mPresentationDisplays.isEmpty()) {
                        String str = "";
                        Iterator it = DisplayManagerService.this.mPresentationDisplays.iterator();
                        while (it.hasNext()) {
                            PresentationDisplay presentationDisplay = (PresentationDisplay) it.next();
                            if (presentationDisplay.displayId == i) {
                                if (!str.isEmpty()) {
                                    str = str + ",";
                                }
                                str = str + presentationDisplay.packageName;
                            }
                        }
                        return str;
                    }
                    return "";
                } finally {
                }
            }
        }

        public final long getPrimaryPhysicalDisplayId() {
            DisplayDevice displayDevice;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    Iterator it = ((ArrayList) DisplayManagerService.this.mDisplayDeviceRepo.mDisplayDevices).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            displayDevice = null;
                            break;
                        }
                        displayDevice = (DisplayDevice) it.next();
                        if (displayDevice.isFirstDisplay()) {
                            break;
                        }
                    }
                    if (displayDevice != null) {
                        DisplayAddress.Physical physical = displayDevice.getDisplayDeviceInfoLocked().address;
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

        public final int getRefreshRateSwitchingType() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DisplayManagerService.this.mDisplayModeDirector.getModeSwitchingType();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getScreenSharingStatus() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter == null) {
                        return -1;
                    }
                    WifiDisplay wifiDisplay = wifiDisplayAdapter.mDisplayController.mAdvertisedDisplay;
                    return wifiDisplay != null ? wifiDisplay.getState() : 7;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final Point getStableDisplaySize() {
            int i;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                boolean z = DisplayManagerService.DEBUG;
                displayManagerService.getClass();
                Point point = new Point();
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        Point point2 = displayManagerService.mStableDisplaySize;
                        int i2 = point2.x;
                        if (i2 > 0 && (i = point2.y) > 0) {
                            point.set(i2, i);
                        }
                    } finally {
                    }
                }
                return point;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int[] getSupportedHdrOutputTypes() {
            if (!DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                return DisplayManagerService.EMPTY_ARRAY;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                if (displayManagerService.mSupportedHdrOutputType == null) {
                    displayManagerService.mInjector.getClass();
                    displayManagerService.mSupportedHdrOutputType = DisplayControl.getSupportedHdrOutputTypes();
                }
                return displayManagerService.mSupportedHdrOutputType;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Display.Mode getSystemPreferredDisplayMode(int i) {
            Display.Mode systemPreferredDisplayModeLocked;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        DisplayDevice deviceForDisplayLocked = displayManagerService.getDeviceForDisplayLocked(i);
                        systemPreferredDisplayModeLocked = deviceForDisplayLocked == null ? null : deviceForDisplayLocked.getSystemPreferredDisplayModeLocked();
                    } finally {
                    }
                }
                return systemPreferredDisplayModeLocked;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int[] getUserDisabledHdrTypes() {
            int[] iArr;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                iArr = DisplayManagerService.this.mUserDisabledHdrTypes;
            }
            return iArr;
        }

        public final Display.Mode getUserPreferredDisplayMode(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return DisplayManagerService.this.getUserPreferredDisplayModeInternal(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final WifiDisplayStatus getWifiDisplayStatus() {
            WifiDisplayStatus wifiDisplayStatusLocked;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                        wifiDisplayStatusLocked = wifiDisplayAdapter != null ? wifiDisplayAdapter.getWifiDisplayStatusLocked() : new WifiDisplayStatus();
                    } finally {
                    }
                }
                return wifiDisplayStatusLocked;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isAllowedRefreshRateToken() {
            int callingUid = Binder.getCallingUid();
            if (DisplayManagerService.this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
                return true;
            }
            Slog.d("DisplayManagerService", "RefreshRateToken - abnormal caller, uid: " + callingUid + ", pid: " + Binder.getCallingPid());
            return false;
        }

        public final boolean isDeviceVolumeMuted() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                    if (volumeController == null) {
                        return false;
                    }
                    return volumeController.mMuted;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isFitToActiveDisplay() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter == null) {
                        return false;
                    }
                    PersistentDataStore persistentDataStore = wifiDisplayAdapter.mPersistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    boolean z = persistentDataStore.mIsFitToActiveDisplay;
                    android.util.Slog.d("WifiDisplayAdapter", "isFitToActiveDisplayLocked : " + z);
                    return z;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isMinimalPostProcessingRequested(int i) {
            boolean z;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                z = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true).mRequestedMinimalPostProcessing;
            }
            return z;
        }

        public final boolean isUidPresentOnDisplay(int i, int i2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                boolean z = DisplayManagerService.DEBUG;
                return displayManagerService.isUidPresentOnDisplayInternal(i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isWifiDisplayWithPinSupported(String str) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    boolean z = false;
                    if (wifiDisplayAdapter == null) {
                        return false;
                    }
                    wifiDisplayAdapter.getClass();
                    android.util.Slog.d("WifiDisplayAdapter", "isWifiDisplayWithPinSupported");
                    WifiDisplayController wifiDisplayController = wifiDisplayAdapter.mDisplayController;
                    if (wifiDisplayController != null) {
                        Iterator it = wifiDisplayController.mAvailableWifiDisplayPeers.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) it.next();
                            if (wifiP2pDevice.deviceAddress.equals(str)) {
                                if (wifiP2pDevice.wpsDisplaySupported() || wifiP2pDevice.wpsKeypadSupported()) {
                                    z = true;
                                }
                            }
                        }
                    }
                    return z;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            new DisplayManagerShellCommand(displayManagerService, displayManagerService.mFlags).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final void overrideHdrTypes(int i, int[] iArr) {
            IBinder displayToken;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                displayToken = DisplayManagerService.this.getDisplayToken(i);
                if (displayToken == null) {
                    throw new IllegalArgumentException("Invalid display: " + i);
                }
            }
            DisplayControl.overrideHdrTypes(displayToken, iArr);
        }

        public final void pauseWifiDisplay() {
            pauseWifiDisplay_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter != null) {
                        android.util.Slog.d("WifiDisplayAdapter", "requestPauseLocked");
                        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass1(wifiDisplayAdapter, 3));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) {
            registerCallbackWithEventMask(iDisplayManagerCallback, 7L);
        }

        public final void registerCallbackWithEventMask(IDisplayManagerCallback iDisplayManagerCallback, long j) {
            if (iDisplayManagerCallback == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            if (DisplayManagerService.this.mFlags.mConnectedDisplayManagementFlagState.isEnabled() && (32 & j) != 0) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DISPLAYS", "Permission required to get signals about connection events.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m447$$Nest$mregisterCallbackInternal(callingPid, callingUid, j, iDisplayManagerCallback, DisplayManagerService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerHbmBrightnessCallback(IHbmBrightnessCallback iHbmBrightnessCallback) {
            if (iHbmBrightnessCallback == null) {
                throw new IllegalArgumentException("listener mut not be null");
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m449$$Nest$mregisterHbmBrightnessCallbackInternal(callingPid, callingUid, iHbmBrightnessCallback, DisplayManagerService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                IBinder asBinder = iVirtualDisplayCallback.asBinder();
                boolean z = DisplayManagerService.DEBUG;
                displayManagerService.releaseVirtualDisplayInternal(asBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void renameWifiDisplay(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("address must not be null");
            }
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to rename to a wifi display");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                        if (wifiDisplayAdapter != null) {
                            wifiDisplayAdapter.requestRenameLocked(str, str2);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestColorMode(int i, int i2) {
            requestColorMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                        if (displayLocked != null && displayLocked.mRequestedColorMode != i2) {
                            displayLocked.mRequestedColorMode = i2;
                            displayManagerService.scheduleTraversalLocked(false);
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestDisplayModes(IBinder iBinder, int i, int[] iArr) {
            boolean isVrrSupportedLocked;
            requestDisplayModes_enforcePermission();
            DisplayModeDirector displayModeDirector = DisplayManagerService.this.mDisplayModeDirector;
            if (displayModeDirector.mSystemRequestObserver != null) {
                synchronized (displayModeDirector.mLock) {
                    isVrrSupportedLocked = displayModeDirector.isVrrSupportedLocked(i);
                }
                if (isVrrSupportedLocked) {
                    SystemRequestObserver systemRequestObserver = displayModeDirector.mSystemRequestObserver;
                    systemRequestObserver.getClass();
                    boolean z = true;
                    if (iArr == null) {
                        synchronized (systemRequestObserver.mLock) {
                            try {
                                SparseArray sparseArray = (SparseArray) ((HashMap) systemRequestObserver.mDisplaysRestrictions).get(iBinder);
                                if (sparseArray != null) {
                                    sparseArray.remove(i);
                                    if (sparseArray.size() != 0) {
                                        z = false;
                                    }
                                    systemRequestObserver.updateStorageLocked(i);
                                } else {
                                    z = false;
                                }
                            } finally {
                            }
                        }
                        if (z) {
                            iBinder.unlinkToDeath(systemRequestObserver.mDeathRecipient, 0);
                            return;
                        }
                        return;
                    }
                    try {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 : iArr) {
                            arrayList.add(Integer.valueOf(i2));
                        }
                        synchronized (systemRequestObserver.mLock) {
                            try {
                                SparseArray sparseArray2 = (SparseArray) ((HashMap) systemRequestObserver.mDisplaysRestrictions).get(iBinder);
                                if (sparseArray2 == null) {
                                    sparseArray2 = new SparseArray();
                                    ((HashMap) systemRequestObserver.mDisplaysRestrictions).put(iBinder, sparseArray2);
                                } else {
                                    z = false;
                                }
                                sparseArray2.put(i, arrayList);
                                systemRequestObserver.updateStorageLocked(i);
                            } finally {
                            }
                        }
                        if (z) {
                            iBinder.linkToDeath(systemRequestObserver.mDeathRecipient, 0);
                        }
                    } catch (RemoteException unused) {
                        systemRequestObserver.removeSystemRequestedVotes(iBinder);
                    }
                }
            }
        }

        public final boolean requestDisplayPower(int i, boolean z) {
            requestDisplayPower_enforcePermission();
            return DisplayManagerService.this.requestDisplayPower(i, z);
        }

        public final boolean requestSetWifiDisplayParameters(List list) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call requestSetWifiDisplayParameters");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter == null) {
                        return false;
                    }
                    WifiDisplayController wifiDisplayController = wifiDisplayAdapter.mDisplayController;
                    return wifiDisplayController != null ? wifiDisplayController.requestSetWifiDisplayParameters(list) : false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean requestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call requestWifiDisplayParameter");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter == null) {
                        return false;
                    }
                    return wifiDisplayAdapter.requestWifiDisplayParameter(str, semWifiDisplayParameter);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void resetBrightnessConfigurationForUser(final int i, final String str) {
            StringBuilder sb = new StringBuilder("[api] resetBrightnessConfigurationForUser:  ");
            sb.append(i);
            sb.append(": ");
            sb.append(str);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", sb, false);
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS", "Permission required to change the display's brightness configuration");
            if (i != UserHandle.getCallingUserId()) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new Consumer() { // from class: com.android.server.display.DisplayManagerService$BinderService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DisplayManagerService.BinderService binderService = DisplayManagerService.BinderService.this;
                            String str2 = str;
                            int i2 = i;
                            LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                            int i3 = DisplayManagerService.BinderService.$r8$clinit;
                            binderService.getClass();
                            if (logicalDisplay.getDisplayInfoLocked().type == 1) {
                                String str3 = logicalDisplay.mPrimaryDisplayDevice.mUniqueId;
                                if (str2 != null) {
                                    DisplayManagerService displayManagerService = DisplayManagerService.this;
                                    int callingUid = IDisplayManager.Stub.getCallingUid();
                                    boolean z = DisplayManagerService.DEBUG;
                                    if (!displayManagerService.validatePackageName(callingUid, str2)) {
                                        str2 = null;
                                    }
                                }
                                DisplayManagerService displayManagerService2 = DisplayManagerService.this;
                                boolean z2 = DisplayManagerService.DEBUG;
                                displayManagerService2.setBrightnessConfigurationForDisplayInternal(null, str3, i2, str2);
                            }
                        }
                    }, true);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) {
            if (i <= 0 || i2 <= 0 || i3 <= 0) {
                throw new IllegalArgumentException("width, height, and densityDpi must be greater than 0");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                IBinder asBinder = iVirtualDisplayCallback.asBinder();
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        VirtualDisplayAdapter virtualDisplayAdapter = displayManagerService.mVirtualDisplayAdapter;
                        if (virtualDisplayAdapter != null) {
                            virtualDisplayAdapter.resizeVirtualDisplayLocked(i, i2, i3, asBinder);
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resumeWifiDisplay() {
            resumeWifiDisplay_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter != null) {
                        android.util.Slog.d("WifiDisplayAdapter", "requestResumeLocked");
                        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass1(wifiDisplayAdapter, 4));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void rotateVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m451$$Nest$mrotateVirtualDisplayInternal(DisplayManagerService.this, iVirtualDisplayCallback.asBinder(), i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setAreUserDisabledHdrTypesAllowed(boolean z) {
            setAreUserDisabledHdrTypesAllowed_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m452$$Nest$msetAreUserDisabledHdrTypesAllowedInternal(DisplayManagerService.this, z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str) {
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", new StringBuilder("[api] setBackupBrightnessConfiguration: "), false);
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    try {
                        Slog.d("DisplayManagerService", "[api] setBackupBrightnessConfiguration: " + brightnessConfiguration);
                        DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0);
                        AutomaticBrightnessController automaticBrightnessController = ((DisplayPowerController) displayPowerControllerInterface).mAutomaticBrightnessController;
                        BrightnessConfiguration appliedBackupConfig = automaticBrightnessController != null ? automaticBrightnessController.mCurrentBrightnessMapper.getAppliedBackupConfig(brightnessConfiguration) : null;
                        if (appliedBackupConfig != null) {
                            Slog.d("DisplayManagerService", "[api] setBackupBrightnessConfiguration(new): " + appliedBackupConfig);
                            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                                ((DisplayPowerController) displayPowerControllerInterface).clearAdaptiveBrightnessLongtermModelBuilder();
                            } else {
                                DisplayManagerService.this.resetBrightnessConfigurations();
                                DisplayManagerService.this.mContext.getPackageManager().clearApplicationUserData("com.google.android.apps.turbo", null);
                            }
                            DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$BinderService$$ExternalSyntheticLambda2(this, appliedBackupConfig, i, str, 1), true);
                            if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                                ((DisplayPowerController) displayPowerControllerInterface).mHandler.obtainMessage(19).sendToTarget();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setBrightness(int i, float f) {
            StringBuilder sb = new StringBuilder("[api] setBrightness: displayId=");
            sb.append(i);
            sb.append(" brightness=");
            sb.append(f);
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", sb, false);
            setBrightness_enforcePermission();
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            boolean z = DisplayManagerService.DEBUG;
            displayManagerService.getClass();
            if (Float.isNaN(f) || f < FullScreenMagnificationGestureHandler.MAX_SCALE || f > displayManagerService.mScreenExtendedBrightnessRangeMaximum) {
                Slog.w("DisplayManagerService", "Attempted to set invalid brightness" + f);
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i);
                    if (displayPowerControllerInterface != null) {
                        DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
                        displayPowerController.mDisplayBrightnessController.setBrightness(displayPowerController.clampScreenBrightness(f), displayPowerController.mBrightnessRangeController.getCurrentBrightnessMax());
                    }
                    DisplayManagerService.this.mPersistentDataStore.saveIfNeeded();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) {
            setBrightnessConfigurationForDisplay_enforcePermission();
            if (i != UserHandle.getCallingUserId()) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                boolean z = DisplayManagerService.DEBUG;
                displayManagerService.setBrightnessConfigurationForDisplayInternal(brightnessConfiguration, str, i, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) {
            Slog.d("DisplayManagerService", "[api] setBrightnessConfigurationForUser: " + brightnessConfiguration + " " + i + ": " + str + PowerManagerUtil.callerInfoToString(false));
            setBrightnessConfigurationForUser_enforcePermission();
            if (i != UserHandle.getCallingUserId()) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$BinderService$$ExternalSyntheticLambda2(this, brightnessConfiguration, i, str, 0), true);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setBrightnessConfigurationForUserWithStats(BrightnessConfiguration brightnessConfiguration, int i, String str, List list, List list2, List list3) {
            Slog.d("DisplayManagerService", "[api] setBrightnessConfigurationForUser: " + brightnessConfiguration + " " + i + ": " + str + PowerManagerUtil.callerInfoToString(false));
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_DISPLAY_BRIGHTNESS", "Permission required to change the display's brightness configuration");
            if (i != UserHandle.getCallingUserId()) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Permission required to change the display brightness configuration of another user");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m453$$Nest$msetBrightnessConfigurationForUserWithStatsInternal(DisplayManagerService.this, brightnessConfiguration, i, str, list, list2, list3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDeviceVolume(int i) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setDeviceVolume");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                if (volumeController != null) {
                    Log.d("VolumeController", "setVolume : volume = " + volumeController.mVolume);
                    volumeController.mVolume = i;
                    volumeController.mMuted = false;
                    volumeController.mHandler.post(volumeController.new AnonymousClass3());
                }
            }
        }

        public final void setDeviceVolumeMuted(boolean z) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setDeviceVolumeMuted");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                if (volumeController != null) {
                    Log.d("VolumeController", "setVolumeMuted :  muted = " + volumeController.mMuted + ", volume = " + volumeController.mVolume);
                    volumeController.mMuted = z;
                    volumeController.mHandler.post(volumeController.new AnonymousClass3());
                }
            }
        }

        public final void setDisplayIdToMirror(IBinder iBinder, int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                VirtualDisplayAdapter virtualDisplayAdapter = DisplayManagerService.this.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter != null) {
                    if (displayLocked == null) {
                        i = -1;
                    }
                    VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = (VirtualDisplayAdapter.VirtualDisplayDevice) virtualDisplayAdapter.mVirtualDisplayDevices.get(iBinder);
                    if (virtualDisplayDevice != null && virtualDisplayDevice.mDisplayIdToMirror != i) {
                        virtualDisplayDevice.mDisplayIdToMirror = i;
                        virtualDisplayDevice.mInfo = null;
                        VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(virtualDisplayDevice, 2);
                        VirtualDisplayAdapter.this.sendTraversalRequestLocked();
                    }
                }
            }
        }

        public final void setDisplayStateOverride(IBinder iBinder, int i) {
            StringBuilder sb = new StringBuilder("[api] setDisplayStateOverride(b): stateOverride=");
            sb.append(Display.stateToString(i));
            sb.append(" (");
            sb.append(Objects.hashCode(iBinder));
            sb.append(")");
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", sb, false);
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m454$$Nest$msetDisplayStateOverrideInternal(DisplayManagerService.this, iBinder, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDisplayStateOverrideWithDisplayId(IBinder iBinder, int i, int i2) {
            Slog.d("DisplayManagerService", "[api] setDisplayStateOverrideWithDisplayId: state=" + Display.stateToString(i) + ", displayId=" + i2 + " (" + Objects.hashCode(iBinder) + ")" + PowerManagerUtil.callerInfoToString(false));
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m455$$Nest$msetDisplayStateOverrideWithDisplayIdInternal(DisplayManagerService.this, iBinder, i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setDlnaDevice");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                    if (wifiDisplayAdapter != null) {
                        wifiDisplayAdapter.setDlnaDevice(semDlnaDevice, iBinder);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setHdrConversionMode(HdrConversionMode hdrConversionMode) {
            if (DisplayManagerService.this.mIsHdrOutputControlEnabled) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_HDR_CONVERSION_MODE", "Permission required to set the HDR conversion mode.");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DisplayManagerService.this.setHdrConversionModeInternal(hdrConversionMode);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void setRefreshRateSwitchingType(int i) {
            setRefreshRateSwitchingType_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayModeDirector displayModeDirector = DisplayManagerService.this.mDisplayModeDirector;
                synchronized (displayModeDirector.mLock) {
                    try {
                        if (i != displayModeDirector.mModeSwitchingType) {
                            displayModeDirector.mModeSwitchingType = i;
                            displayModeDirector.notifyDesiredDisplayModeSpecsChangedLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setScreenSharingStatus(int i) {
            WifiDisplayController wifiDisplayController;
            WifiDisplay wifiDisplay;
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setScreenSharingStatus");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                if (wifiDisplayAdapter != null && (wifiDisplay = (wifiDisplayController = wifiDisplayAdapter.mDisplayController).mAdvertisedDisplay) != null && wifiDisplay.getState() != i) {
                    wifiDisplayController.mAdvertisedDisplay.setState(i);
                    wifiDisplayController.mHandler.post(new WifiDisplayController.AnonymousClass21(wifiDisplayController, i, 9, 2));
                    wifiDisplayController.sendWifiDisplayVolumeSupportChangedBroadcast();
                }
            }
        }

        public final void setShouldAlwaysRespectAppRequestedMode(boolean z) {
            setShouldAlwaysRespectAppRequestedMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayModeDirector displayModeDirector = DisplayManagerService.this.mDisplayModeDirector;
                synchronized (displayModeDirector.mLock) {
                    try {
                        if (displayModeDirector.mAlwaysRespectAppRequest != z) {
                            displayModeDirector.mAlwaysRespectAppRequest = z;
                            displayModeDirector.notifyDesiredDisplayModeSpecsChangedLocked();
                            if (CoreRune.FW_VRR_SEAMLESS) {
                                displayModeDirector.mBrightnessObserver.onBrightnessChangedLocked();
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setTemporaryAutoBrightnessAdjustment(float f) {
            setTemporaryAutoBrightnessAdjustment_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayPowerController displayPowerController = (DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0));
                    displayPowerController.getClass();
                    displayPowerController.mHandler.obtainMessage(6, Float.floatToIntBits(f), 0).sendToTarget();
                    displayPowerController.mClock.getClass();
                    DisplayPowerController.sLastScreenBrightnessSettingChangedTime = SystemClock.uptimeMillis();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setTemporaryBrightness(int i, float f) {
            StringBuilder sb = new StringBuilder("[api] setTemporaryBrightness: displayId=");
            sb.append(i);
            sb.append(" brightness=");
            sb.append(f);
            int i2 = 0;
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", sb, false);
            setTemporaryBrightness_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    try {
                        DisplayPowerController displayPowerController = (DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i));
                        displayPowerController.getClass();
                        displayPowerController.mHandler.obtainMessage(5, Float.floatToIntBits(f), 0).sendToTarget();
                        if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                            displayPowerController.mClock.getClass();
                            DisplayPowerController.sLastScreenBrightnessSettingChangedTime = SystemClock.uptimeMillis();
                        }
                        if (PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY) {
                            DisplayManagerService displayManagerService = DisplayManagerService.this;
                            displayManagerService.getClass();
                            if (i == 0) {
                                i2 = 1;
                            } else if (i != 1) {
                                i2 = -1;
                            }
                            if (i2 != -1) {
                                ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService.mDisplayPowerControllers.get(i2))).sendUpdatePowerState();
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setTemporaryBrightnessForSlowChange(int i, float f, boolean z) {
            Slog.d("DisplayManagerService", "[api] setTemporaryBrightnessForSlowChange: displayId=" + i + ", brightness=" + f + ", slowChange=" + z + PowerManagerUtil.callerInfoToString(false));
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_BRIGHTNESS", "Permission required to set the display's brightness");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (DisplayManagerService.this.mSyncRoot) {
                    DisplayPowerController displayPowerController = (DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i));
                    displayPowerController.getClass();
                    displayPowerController.mHandler.obtainMessage(5, Float.floatToIntBits(f), z ? 1 : 0, 0).sendToTarget();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setUserDisabledHdrTypes(int[] iArr) {
            setUserDisabledHdrTypes_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m456$$Nest$msetUserDisabledHdrTypesInternal(DisplayManagerService.this, iArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setUserPreferredDisplayMode(int i, Display.Mode mode) {
            setUserPreferredDisplayMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.this.setUserPreferredDisplayModeInternal(i, mode);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m457$$Nest$msetVirtualDisplayStateInternal(DisplayManagerService.this, iVirtualDisplayCallback.asBinder(), z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) {
            if (surface != null && surface.isSingleBuffered()) {
                throw new IllegalArgumentException("Surface can't be single-buffered");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m458$$Nest$msetVirtualDisplaySurfaceInternal(DisplayManagerService.this, iVirtualDisplayCallback.asBinder(), surface);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setVolumeKeyEvent(int i) {
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setVolumeKeyEvent");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    VolumeController volumeController = DisplayManagerService.this.mVolumeController;
                    if (volumeController != null) {
                        volumeController.setVolumeKeyEvent(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setWifiDisplayParam(String str, String str2) {
            WifiDisplayController wifiDisplayController;
            if (Binder.getCallingUid() != 1000) {
                DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to call setWifiDisplayParam");
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                if (wifiDisplayAdapter != null && (wifiDisplayController = wifiDisplayAdapter.mDisplayController) != null && wifiDisplayController.mAdvertisedDisplay != null) {
                    wifiDisplayController.setParam(str, str2);
                }
            }
        }

        public final boolean shouldAlwaysRespectAppRequestedMode() {
            boolean z;
            shouldAlwaysRespectAppRequestedMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayModeDirector displayModeDirector = DisplayManagerService.this.mDisplayModeDirector;
                synchronized (displayModeDirector.mLock) {
                    z = displayModeDirector.mAlwaysRespectAppRequest;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startWifiDisplayChannelScan(int i) {
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to start wifi display scans");
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m459$$Nest$mstartWifiDisplayScanInternal(DisplayManagerService.this, callingPid, i, -1);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startWifiDisplayChannelScanAndInterval(int i, int i2) {
            DisplayManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY", "Permission required to start wifi display scans");
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService.m459$$Nest$mstartWifiDisplayScanInternal(DisplayManagerService.this, callingPid, i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startWifiDisplayScan() {
            startWifiDisplayScan_enforcePermission();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mCallbacks.get(callingPid);
                    if (callbackRecord == null) {
                        throw new IllegalStateException("The calling process has not registered an IDisplayManagerCallback.");
                    }
                    displayManagerService.startWifiDisplayScanLocked(callbackRecord);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopWifiDisplayScan() {
            stopWifiDisplayScan_enforcePermission();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                synchronized (displayManagerService.mSyncRoot) {
                    CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mCallbacks.get(callingPid);
                    if (callbackRecord == null) {
                        throw new IllegalStateException("The calling process has not registered an IDisplayManagerCallback.");
                    }
                    displayManagerService.stopWifiDisplayScanLocked(callbackRecord);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final class BrightnessPair {
        public float brightness;
        public float sdrBrightness;
    }

    public final class CallbackRecord implements IBinder.DeathRecipient {
        public final IDisplayManagerCallback mCallback;
        public final AtomicLong mEventsMask;
        public final String mPackageName;
        public final int mPid;
        public final int mUid;
        public boolean mWifiDisplayScanRequested;
        public String mWifiDisplayScanRequestedTime;
        public final /* synthetic */ DisplayManagerService this$0;

        public CallbackRecord(int i, int i2, long j, IDisplayManagerCallback iDisplayManagerCallback, DisplayManagerService displayManagerService) {
            this.this$0 = displayManagerService;
            this.mPid = i;
            this.mUid = i2;
            this.mCallback = iDisplayManagerCallback;
            this.mEventsMask = new AtomicLong(j);
            String[] packagesForUid = displayManagerService.mContext.getPackageManager().getPackagesForUid(i2);
            this.mPackageName = packagesForUid == null ? null : packagesForUid[0];
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (DisplayManagerService.DEBUG || this.this$0.extraLogging(this.mPackageName)) {
                Slog.d("DisplayManagerService", "Display listener for pid " + this.mPid + " died.");
            }
            if (Trace.isTagEnabled(131072L)) {
                Trace.instant(131072L, "displayManagerBinderDied#mPid=" + this.mPid);
            }
            DisplayManagerService displayManagerService = this.this$0;
            synchronized (displayManagerService.mSyncRoot) {
                displayManagerService.mCallbacks.remove(this.mPid);
                displayManagerService.stopWifiDisplayScanLocked(this);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0025, code lost:
        
            if ((r0 & 32) != 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x007b, code lost:
        
            if (r8.this$0.extraLogging(r8.mPackageName) == false) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x007d, code lost:
        
            r9 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r10, "Not sending displayEvent: ", " due to mask:");
            r9.append(r8.mEventsMask);
            com.android.server.power.Slog.i("DisplayManagerService", r9.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0098, code lost:
        
            if (android.os.Trace.isTagEnabled(131072) == false) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x009a, code lost:
        
            r9 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r10, "notifyDisplayEventAsync#notSendingEvent=", ",mEventsMask=");
            r9.append(r8.mEventsMask);
            android.os.Trace.instant(131072, r9.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x00af, code lost:
        
            return true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x002d, code lost:
        
            if ((r0 & 16) != 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0035, code lost:
        
            if ((r0 & 8) != 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x003d, code lost:
        
            if ((r0 & 2) != 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0045, code lost:
        
            if ((r0 & 4) != 0) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x004d, code lost:
        
            if ((r0 & 1) != 0) goto L34;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean notifyDisplayEventAsync(int r9, int r10) {
            /*
                r8 = this;
                java.util.concurrent.atomic.AtomicLong r0 = r8.mEventsMask
                long r0 = r0.get()
                r2 = 1
                java.lang.String r3 = "DisplayManagerService"
                r4 = 0
                switch(r10) {
                    case 1: goto L48;
                    case 2: goto L40;
                    case 3: goto L38;
                    case 4: goto L30;
                    case 5: goto L28;
                    case 6: goto L20;
                    case 7: goto L20;
                    default: goto Le;
                }
            Le:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Unknown display event "
                r0.<init>(r1)
                r0.append(r10)
                java.lang.String r0 = r0.toString()
                com.android.server.power.Slog.e(r3, r0)
                goto L4f
            L20:
                r6 = 32
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
                goto L4f
            L28:
                r6 = 16
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
                goto L4f
            L30:
                r6 = 8
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
                goto L4f
            L38:
                r6 = 2
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
                goto L4f
            L40:
                r6 = 4
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
                goto L4f
            L48:
                r6 = 1
                long r0 = r0 & r6
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 == 0) goto L73
            L4f:
                android.hardware.display.IDisplayManagerCallback r0 = r8.mCallback     // Catch: android.os.RemoteException -> L55
                r0.onDisplayEvent(r9, r10)     // Catch: android.os.RemoteException -> L55
                return r2
            L55:
                r9 = move-exception
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                java.lang.String r0 = "Failed to notify process "
                r10.<init>(r0)
                int r0 = r8.mPid
                r10.append(r0)
                java.lang.String r0 = " that displays changed, assuming it died."
                r10.append(r0)
                java.lang.String r10 = r10.toString()
                com.android.server.power.Slog.w(r10, r9)
                r8.binderDied()
                r8 = 0
                return r8
            L73:
                com.android.server.display.DisplayManagerService r9 = r8.this$0
                java.lang.String r0 = r8.mPackageName
                boolean r9 = r9.extraLogging(r0)
                if (r9 == 0) goto L91
                java.lang.String r9 = "Not sending displayEvent: "
                java.lang.String r0 = " due to mask:"
                java.lang.StringBuilder r9 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r10, r9, r0)
                java.util.concurrent.atomic.AtomicLong r0 = r8.mEventsMask
                r9.append(r0)
                java.lang.String r9 = r9.toString()
                com.android.server.power.Slog.i(r3, r9)
            L91:
                r0 = 131072(0x20000, double:6.47582E-319)
                boolean r9 = android.os.Trace.isTagEnabled(r0)
                if (r9 == 0) goto Laf
                java.lang.String r9 = "notifyDisplayEventAsync#notSendingEvent="
                java.lang.String r3 = ",mEventsMask="
                java.lang.StringBuilder r9 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r10, r9, r3)
                java.util.concurrent.atomic.AtomicLong r8 = r8.mEventsMask
                r9.append(r8)
                java.lang.String r8 = r9.toString()
                android.os.Trace.instant(r0, r8)
            Laf:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.CallbackRecord.notifyDisplayEventAsync(int, int):boolean");
        }
    }

    public interface Clock {
    }

    public final class DesiredDisplayModeSpecsObserver {
        public final DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0 mSpecsChangedConsumer = new DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0(this, 0);
        public boolean mChanged = false;

        public DesiredDisplayModeSpecsObserver() {
        }

        public final void onDesiredDisplayModeSpecsChanged() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    this.mChanged = false;
                    DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(this.mSpecsChangedConsumer, false);
                    if (this.mChanged) {
                        DisplayManagerService.this.scheduleTraversalLocked(false);
                        this.mChanged = false;
                    }
                    if (CoreRune.FW_VRR_REFRESH_RATE_MODE && DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager.getController().mUpdateRefreshRateMode.getAndSet(false)) {
                        DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0(this, 1), true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
        public DeviceStateListener() {
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    Message obtainMessage = DisplayManagerService.this.mHandler.obtainMessage(9);
                    obtainMessage.arg1 = deviceState.getIdentifier();
                    DisplayManagerService.this.mHandler.sendMessage(obtainMessage);
                    if (PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY) {
                        DisplayManagerService displayManagerService = DisplayManagerService.this;
                        int identifier = deviceState.getIdentifier();
                        int i = 1;
                        if (identifier != 0 && identifier != 1) {
                            if (identifier == 2 || identifier == 3) {
                                i = 0;
                            } else {
                                if (identifier != 4) {
                                }
                                i = -1;
                            }
                        }
                        displayManagerService.mDualScreenPolicy = i;
                    }
                    DisplayManagerService.this.mLogicalDisplayMapper.setDeviceStateLocked(deviceState.getIdentifier());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class DisplayManagerHandler extends Handler {
        public DisplayManagerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int size;
            WifiDisplayController wifiDisplayController;
            boolean z;
            int i = 0;
            int i2 = message.what;
            if (i2 == 25) {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                int i3 = message.arg1;
                List list = (List) message.obj;
                if (DisplayManagerService.DEBUG) {
                    displayManagerService.getClass();
                    Slog.d("DisplayManagerService", "Delivering wifidisplay parameter event=" + i3);
                }
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        size = displayManagerService.mCallbacks.size();
                        displayManagerService.mTempCallbacks.clear();
                        for (int i4 = 0; i4 < size; i4++) {
                            displayManagerService.mTempCallbacks.add((CallbackRecord) displayManagerService.mCallbacks.valueAt(i4));
                        }
                    } finally {
                    }
                }
                while (i < size) {
                    CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mTempCallbacks.get(i);
                    callbackRecord.getClass();
                    try {
                        callbackRecord.mCallback.onWifiDisplayParameterEvent(i3, list);
                    } catch (RemoteException e) {
                        Slog.w("Failed to notify process " + callbackRecord.mPid + " that displays changed, assuming it died.", e);
                        callbackRecord.binderDied();
                    }
                    i++;
                }
                displayManagerService.mTempCallbacks.clear();
                return;
            }
            if (i2 == 28) {
                WifiDisplayAdapter wifiDisplayAdapter = DisplayManagerService.this.mWifiDisplayAdapter;
                if (wifiDisplayAdapter == null || (wifiDisplayController = wifiDisplayAdapter.mDisplayController) == null || Build.VERSION.SEM_PLATFORM_INT < 120100) {
                    return;
                }
                PersistentDataStore persistentDataStore = wifiDisplayController.mPersistentDataStore;
                persistentDataStore.loadIfNeeded();
                if (persistentDataStore.mRememberedInitiatedDevices.isEmpty()) {
                    return;
                }
                wifiDisplayController.mMcfManager.initialize();
                return;
            }
            if (i2 == 30) {
                DisplayManagerService.this.mWindowManagerInternal.updateMirroredSurface(message.arg1);
                return;
            }
            if (i2 == 31) {
                DisplayManagerService displayManagerService2 = DisplayManagerService.this;
                int i5 = message.arg1;
                synchronized (displayManagerService2.mSyncRoot) {
                    displayManagerService2.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda14(displayManagerService2, i5, i), true);
                }
                return;
            }
            switch (i2) {
                case 1:
                    DisplayManagerService.m448$$Nest$mregisterDefaultDisplayAdapters(DisplayManagerService.this);
                    return;
                case 2:
                    DisplayManagerService.m446$$Nest$mregisterAdditionalDisplayAdapters(DisplayManagerService.this);
                    return;
                case 3:
                    DisplayManagerService.m440$$Nest$mdeliverDisplayEvent(DisplayManagerService.this, message.arg1, null, message.arg2);
                    return;
                case 4:
                    DisplayManagerService.this.mWindowManagerInternal.requestTraversalFromDisplayManager();
                    return;
                case 5:
                    synchronized (DisplayManagerService.this.mSyncRoot) {
                        try {
                            DisplayManagerService displayManagerService3 = DisplayManagerService.this;
                            z = !displayManagerService3.mTempViewports.equals(displayManagerService3.mViewports);
                            if (z) {
                                DisplayManagerService.this.mTempViewports.clear();
                                Iterator it = DisplayManagerService.this.mViewports.iterator();
                                while (it.hasNext()) {
                                    DisplayManagerService.this.mTempViewports.add(((DisplayViewport) it.next()).makeCopy());
                                }
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        DisplayManagerService displayManagerService4 = DisplayManagerService.this;
                        InputManagerService.LocalService localService = displayManagerService4.mInputManagerInternal;
                        ArrayList arrayList = displayManagerService4.mTempViewports;
                        localService.getClass();
                        boolean z2 = InputManagerService.DEBUG;
                        InputManagerService.this.setDisplayViewportsInternal(arrayList);
                        return;
                    }
                    return;
                case 6:
                    DisplayManagerService.m445$$Nest$mloadBrightnessConfigurations(DisplayManagerService.this);
                    return;
                case 7:
                    synchronized (DisplayManagerService.this.mSyncRoot) {
                        try {
                            LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(message.arg1, true);
                            if (displayLocked != null) {
                                ArraySet arraySet = displayLocked.mPendingFrameRateOverrideUids;
                                displayLocked.mPendingFrameRateOverrideUids = new ArraySet();
                                DisplayManagerService.m440$$Nest$mdeliverDisplayEvent(DisplayManagerService.this, message.arg1, arraySet, message.arg2);
                            }
                        } finally {
                        }
                    }
                    return;
                case 8:
                    DisplayManagerService displayManagerService5 = DisplayManagerService.this;
                    int i6 = message.arg1;
                    int i7 = message.arg2;
                    displayManagerService5.getClass();
                    Slog.d("DisplayManagerService", "Delivering display group event: groupId=" + i6 + ", event=" + i7);
                    if (i7 == 1) {
                        Iterator it2 = displayManagerService5.mDisplayGroupListeners.iterator();
                        while (it2.hasNext()) {
                            ((DisplayManagerInternal.DisplayGroupListener) it2.next()).onDisplayGroupAdded(i6);
                        }
                        return;
                    } else if (i7 == 2) {
                        Iterator it3 = displayManagerService5.mDisplayGroupListeners.iterator();
                        while (it3.hasNext()) {
                            ((DisplayManagerInternal.DisplayGroupListener) it3.next()).onDisplayGroupChanged(i6);
                        }
                        return;
                    } else {
                        if (i7 != 3) {
                            return;
                        }
                        Iterator it4 = displayManagerService5.mDisplayGroupListeners.iterator();
                        while (it4.hasNext()) {
                            ((DisplayManagerInternal.DisplayGroupListener) it4.next()).onDisplayGroupRemoved(i6);
                        }
                        return;
                    }
                case 9:
                    DisplayManagerService.this.mWindowManagerInternal.onDisplayManagerReceivedDeviceState(message.arg1);
                    return;
                default:
                    switch (i2) {
                        case 20:
                            DisplayManagerService.m439$$Nest$mdeliverDeviceEvent(message.arg1, message.getData(), DisplayManagerService.this);
                            return;
                        case 21:
                            DisplayManagerService.m443$$Nest$mdeliverPresentationDisplayInfoEvent(DisplayManagerService.this, message.arg1, message.arg2, (String) message.obj);
                            return;
                        case 22:
                            DisplayManagerService.m441$$Nest$mdeliverDisplayVolumeEvent(message.arg1, message.getData(), DisplayManagerService.this);
                            return;
                        case 23:
                            DisplayManagerService.m442$$Nest$mdeliverDisplayVolumeKeyEvent(DisplayManagerService.this, message.arg1);
                            return;
                        default:
                            return;
                    }
            }
        }
    }

    public final class DisplayStateOverrideLock implements IBinder.DeathRecipient {
        public final int mDisplayId;
        public int mLastRequestedState;
        public final long mLastRequestedTime;
        public final IBinder mLock;
        public final String mTag = PowerManagerUtil.callerInfoToString(false);

        public DisplayStateOverrideLock(IBinder iBinder, int i, long j, int i2) {
            this.mLock = iBinder;
            this.mLastRequestedState = i;
            this.mLastRequestedTime = j;
            this.mDisplayId = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (DisplayManagerService.this.mRequestDisplayStateLock) {
                try {
                    Slog.d("DisplayManagerService", "DisplayStateOverrideLock: binderDied: " + this);
                    int indexOf = DisplayManagerService.this.mDisplayStateOverrideLocks.indexOf(this);
                    if (indexOf < 0) {
                        return;
                    }
                    DisplayManagerService.m455$$Nest$msetDisplayStateOverrideWithDisplayIdInternal(DisplayManagerService.this, this.mLock, 0, ((DisplayStateOverrideLock) DisplayManagerService.this.mDisplayStateOverrideLocks.get(indexOf)).mDisplayId);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" " + this.mDisplayId);
            sb.append(" " + Display.stateToString(this.mLastRequestedState));
            sb.append(" (lock:" + Objects.hashCode(this.mLock));
            sb.append(", t:" + this.mLastRequestedTime + ")");
            StringBuilder sb2 = new StringBuilder(" ");
            sb2.append(this.mTag);
            sb.append(sb2.toString());
            return sb.toString();
        }
    }

    enum DisplayStatePriority {
        EF6("ON"),
        EF15("ON_SUSPEND"),
        EF23("VR"),
        EF31("DOZE"),
        EF39("DOZE_SUSPEND"),
        EF46("OFF");

        private final int displayState;

        DisplayStatePriority(String str) {
            this.displayState = r2;
        }

        public final int getDisplayState() {
            return this.displayState;
        }
    }

    public final class HbmBrightnessCallbackRecord implements IBinder.DeathRecipient {
        public final IHbmBrightnessCallback mCallback;
        public final int mPid;
        public final int mUid;
        public final /* synthetic */ DisplayManagerService this$0;

        public HbmBrightnessCallbackRecord(int i, int i2, IHbmBrightnessCallback iHbmBrightnessCallback, DisplayManagerService displayManagerService) {
            this.this$0 = displayManagerService;
            this.mPid = i;
            this.mUid = i2;
            this.mCallback = iHbmBrightnessCallback;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.d("DisplayManagerService", "Hbm listener for pid " + this.mPid + " died.");
            DisplayManagerService displayManagerService = this.this$0;
            synchronized (displayManagerService.mSyncRoot) {
                displayManagerService.mHbmBrightnessCallbacks.remove(this.mPid);
            }
        }
    }

    class Injector {
    }

    final class LocalService extends DisplayManagerInternal {
        public LocalService() {
        }

        public final int createSpegVirtualDisplay(String str, int i, IVirtualDisplayCallback iVirtualDisplayCallback) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            boolean z = DisplayManagerService.DEBUG;
            displayManagerService.getClass();
            int i2 = -1;
            if (CoreRune.SYSFW_APP_SPEG) {
                int callingUid = Binder.getCallingUid();
                if (callingUid == 1000 || callingUid == 0) {
                    DisplayInfo displayInfoInternal = displayManagerService.getDisplayInfoInternal(0, callingUid);
                    VirtualDisplayConfig.Builder builder = new VirtualDisplayConfig.Builder("SpegVirtualDisplay", displayInfoInternal.getNaturalWidth(), displayInfoInternal.getNaturalHeight(), displayInfoInternal.logicalDensityDpi);
                    builder.setFlags(16777672);
                    builder.setUniqueId(String.valueOf(i));
                    VirtualDisplayConfig build = builder.build();
                    String generateDisplayUniqueId = VirtualDisplayAdapter.generateDisplayUniqueId(str, callingUid, build);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (displayManagerService.mSyncRoot) {
                            i2 = displayManagerService.createVirtualDisplayLocked(iVirtualDisplayCallback, null, i, str, generateDisplayUniqueId, null, null, 16777672, build);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } else {
                    Slog.e("SPEG", "Try to create display from unprivileged uid");
                }
            }
            return i2;
        }

        public final int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IVirtualDevice iVirtualDevice, DisplayWindowPolicyController displayWindowPolicyController, String str) {
            return DisplayManagerService.m438$$Nest$mcreateVirtualDisplayInternal(DisplayManagerService.this, virtualDisplayConfig, iVirtualDisplayCallback, null, iVirtualDevice, displayWindowPolicyController, str);
        }

        public final DisplayManagerInternal.AmbientLightSensorData getAmbientLightSensorData(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return null;
                    }
                    DisplayDevice displayDevice = displayLocked.mPrimaryDisplayDevice;
                    if (displayDevice == null) {
                        return null;
                    }
                    SensorData sensorData = displayDevice.getDisplayDeviceConfig().mAmbientLightSensor;
                    return new DisplayManagerInternal.AmbientLightSensorData(sensorData.name, sensorData.type);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int[] getBrightnessLearningMaxLimitCount() {
            int[] iArr;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mAdaptiveBrightnessLongtermModelBuilder;
                if (adaptiveBrightnessLongtermModelBuilder != null) {
                    int[] iArr2 = adaptiveBrightnessLongtermModelBuilder.mMaximumBrightnessLimitCount;
                    iArr = (int[]) iArr2.clone();
                    Arrays.fill(iArr2, 0);
                } else {
                    iArr = null;
                }
            }
            return iArr;
        }

        public final float getCurrentScreenBrightness() {
            float currentBrightness;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                currentBrightness = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mDisplayBrightnessController.getCurrentBrightness();
            }
            return currentBrightness;
        }

        public final IntArray getDisplayGroupIds() {
            ArraySet arraySet = new ArraySet();
            IntArray intArray = new IntArray();
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayManagerService.this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda2(this, arraySet, intArray, 1), true);
            }
            return intArray;
        }

        public final int getDisplayIdToMirror(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return -1;
                    }
                    DisplayDevice displayDevice = displayLocked.mPrimaryDisplayDevice;
                    int i2 = 0;
                    boolean z = displayLocked.mDevicePosition == 1;
                    if ((displayDevice.getDisplayDeviceInfoLocked().flags & 128) == 0 && !z && !displayDevice.isWindowManagerMirroringLocked()) {
                        int displayIdToMirrorLocked = displayDevice.getDisplayIdToMirrorLocked();
                        if (DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(displayIdToMirrorLocked, true) != null) {
                            i2 = displayIdToMirrorLocked;
                        }
                        return i2;
                    }
                    return -1;
                } finally {
                }
            }
        }

        public final DisplayInfo getDisplayInfo(int i) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            int myUid = Process.myUid();
            boolean z = DisplayManagerService.DEBUG;
            return displayManagerService.getDisplayInfoInternal(i, myUid);
        }

        public final SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i) {
            IBinder displayToken;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                displayToken = DisplayManagerService.this.getDisplayToken(i);
                if (displayToken == null) {
                    throw new IllegalArgumentException("Invalid displayId=" + i);
                }
            }
            return SurfaceControl.getDisplayNativePrimaries(displayToken);
        }

        public final Point getDisplayPosition(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return null;
                    }
                    return new Point(displayLocked.mDisplayPosition);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final Point getDisplaySurfaceDefaultSize(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    DisplayDevice deviceForDisplayLocked = DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked == null) {
                        return null;
                    }
                    return deviceForDisplayLocked.getDisplaySurfaceDefaultSizeLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final DisplayWindowPolicyController getDisplayWindowPolicyController(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    if (!DisplayManagerService.this.mDisplayWindowPolicyControllers.contains(i)) {
                        return null;
                    }
                    return (DisplayWindowPolicyController) ((Pair) DisplayManagerService.this.mDisplayWindowPolicyControllers.get(i)).second;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final DisplayedContentSample getDisplayedContentSample(int i, long j, long j2) {
            return DisplayManagerService.this.getDisplayedContentSampleInternal(i, j, j2);
        }

        public final DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i) {
            return DisplayManagerService.this.getDisplayedContentSamplingAttributesInternal(i);
        }

        public final HostUsiVersion getHostUsiVersion(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return null;
                    }
                    return displayLocked.mPrimaryDisplayDevice.getDisplayDeviceConfig().mHostUsiVersion;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final float getLastAutomaticScreenBrightness() {
            float f;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                f = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mLastAutomaticScreenBrightness;
            }
            return f;
        }

        public final long getLastUserSetScreenBrightnessTime() {
            long j;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).getClass();
                j = DisplayPowerController.sLastScreenBrightnessSettingChangedTime;
            }
            return j;
        }

        public final void getNonOverrideDisplayInfo(int i, DisplayInfo displayInfo) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked != null) {
                    displayInfo.copyFrom(displayLocked.mBaseDisplayInfo);
                }
            }
        }

        public final Set getPossibleDisplayInfo(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    ArraySet arraySet = new ArraySet();
                    DeviceStateManagerInternal deviceStateManagerInternal = DisplayManagerService.this.mDeviceStateManager;
                    if (deviceStateManagerInternal == null) {
                        Slog.w("DisplayManagerService", "Can't get supported states since DeviceStateManager not ready");
                        return arraySet;
                    }
                    int[] supportedStateIdentifiers = deviceStateManagerInternal.getSupportedStateIdentifiers();
                    Slog.d("DisplayManagerService", "supportedStates=" + Arrays.toString(supportedStateIdentifiers));
                    for (int i2 : supportedStateIdentifiers) {
                        DisplayInfo displayInfoForStateLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayInfoForStateLocked(i2, i);
                        if (displayInfoForStateLocked != null) {
                            arraySet.add(displayInfoForStateLocked);
                        }
                    }
                    Slog.d("DisplayManagerService", "possibleInfos=" + arraySet);
                    return arraySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final IBinder getRealDisplayToken(int i) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            boolean z = DisplayManagerService.DEBUG;
            return displayManagerService.getDisplayToken(i);
        }

        public final SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(int i, String str, String str2) {
            SensorManager sensorManager;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                sensorManager = DisplayManagerService.this.mSensorManager;
            }
            if (sensorManager == null || SensorUtils.findSensor(sensorManager, str2, str, 0) == null) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return null;
                    }
                    DisplayDevice displayDevice = displayLocked.mPrimaryDisplayDevice;
                    if (displayDevice == null) {
                        return null;
                    }
                    SensorData sensorData = displayDevice.getDisplayDeviceConfig().mProximitySensor;
                    if (sensorData != null) {
                        boolean z = !TextUtils.isEmpty(str);
                        boolean isEmpty = true ^ TextUtils.isEmpty(str2);
                        if ((z || isEmpty) && ((!z || str.equals(sensorData.name)) && (!isEmpty || str2.equals(sensorData.type)))) {
                            return new SurfaceControl.RefreshRateRange(sensorData.minRefreshRate, sensorData.maxRefreshRate);
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getRefreshRateLimitations(int i) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    DisplayDevice deviceForDisplayLocked = DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked == null) {
                        return null;
                    }
                    return deviceForDisplayLocked.getDisplayDeviceConfig().mRefreshRateLimitations;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int getRefreshRateSwitchingType() {
            return DisplayManagerService.this.mDisplayModeDirector.getModeSwitchingType();
        }

        public final void hideCutoutForFoldable(boolean z) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(0, true);
                    LogicalDisplay displayLocked2 = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(1, true);
                    if (displayLocked != null && displayLocked2 != null) {
                        Slog.d("DisplayManagerService", "hideCutoutForFoldableInternal, hideCutout=" + z);
                        displayLocked.updateLocked(displayManagerService.mDisplayDeviceRepo, displayManagerService.mLogicalDisplayMapper.mSyntheticModeManager);
                        displayLocked2.updateLocked(displayManagerService.mDisplayDeviceRepo, displayManagerService.mLogicalDisplayMapper.mSyntheticModeManager);
                    }
                } finally {
                }
            }
        }

        public final void ignoreProximitySensorUntilChanged() {
            if (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY) {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                if (displayManagerService.mDualScreenPolicy == 1) {
                    ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService.mDisplayPowerControllers.get(1))).mDisplayPowerProximityStateController.mHandler.sendEmptyMessage(2);
                    return;
                }
            }
            ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mDisplayPowerProximityStateController.mHandler.sendEmptyMessage(2);
        }

        public final void initPowerManagement(DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayManagerService displayManagerService = DisplayManagerService.this;
                displayManagerService.mDisplayPowerCallbacks = displayPowerCallbacks;
                displayManagerService.mSensorManager = sensorManager;
                displayManagerService.mPowerHandler = handler;
                displayManagerService.getClass();
                displayManagerService.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda8(displayManagerService, 0), true);
            }
            DisplayManagerService.this.mHandler.sendEmptyMessage(6);
        }

        public final boolean isProximitySensorAvailable() {
            boolean z;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                z = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).mDisplayPowerProximityStateController.mProximitySensor != null;
            }
            return z;
        }

        public final void onEarlyInteractivityChange(boolean z) {
            LogicalDisplayMapper logicalDisplayMapper = DisplayManagerService.this.mLogicalDisplayMapper;
            synchronized (logicalDisplayMapper.mSyncRoot) {
                try {
                    if (logicalDisplayMapper.mInteractive != z) {
                        logicalDisplayMapper.mInteractive = z;
                        logicalDisplayMapper.finishStateTransitionLocked(false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onOverlayChanged() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayManagerService.this.mDisplayDeviceRepo.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda5(1));
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPresentation(int r5, boolean r6) {
            /*
                r4 = this;
                com.android.server.display.DisplayManagerService r4 = com.android.server.display.DisplayManagerService.this
                com.android.server.display.ExternalDisplayPolicy r4 = r4.mExternalDisplayPolicy
                com.android.server.display.DisplayManagerService$SyncRoot r0 = r4.mSyncRoot
                monitor-enter(r0)
                com.android.server.display.LogicalDisplayMapper r1 = r4.mLogicalDisplayMapper     // Catch: java.lang.Throwable -> Lbb
                r2 = 1
                com.android.server.display.LogicalDisplay r1 = r1.getDisplayLocked(r5, r2)     // Catch: java.lang.Throwable -> Lbb
                if (r1 == 0) goto Lbd
                boolean r1 = com.android.server.display.ExternalDisplayPolicy.isExternalDisplayLocked(r1)     // Catch: java.lang.Throwable -> Lbb
                if (r1 != 0) goto L18
                goto Lbd
            L18:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lbb
                com.android.server.display.feature.DisplayManagerFlags r0 = r4.mFlags
                com.android.server.display.feature.DisplayManagerFlags$FlagState r0 = r0.mConnectedDisplayManagementFlagState
                boolean r0 = r0.isEnabled()
                if (r0 != 0) goto L25
                goto Lbe
            L25:
                r0 = 806(0x326, float:1.13E-42)
                if (r6 == 0) goto L7e
                com.android.server.display.ExternalDisplayStatsService r4 = r4.mExternalDisplayStatsService
                android.util.SparseIntArray r6 = r4.mExternalDisplayStates
                monitor-enter(r6)
                android.util.SparseIntArray r1 = r4.mExternalDisplayStates     // Catch: java.lang.Throwable -> L39
                int r5 = r1.get(r5, r2)     // Catch: java.lang.Throwable -> L39
                if (r5 != r2) goto L3b
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L39
                goto Lbe
            L39:
                r4 = move-exception
                goto L7c
            L3b:
                android.util.SparseIntArray r1 = r4.mExternalDisplayStates     // Catch: java.lang.Throwable -> L39
                int r1 = r1.size()     // Catch: java.lang.Throwable -> L39
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L39
                com.android.server.display.ExternalDisplayStatsService$Injector r6 = r4.mInjector
                r6.getClass()
                android.content.Context r6 = r6.mContext     // Catch: java.lang.Throwable -> L5a
                android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L5a
                java.lang.String r2 = "force_desktop_mode_on_external_displays"
                r3 = 0
                int r6 = android.provider.Settings.Global.getInt(r6, r2, r3)     // Catch: java.lang.Throwable -> L5a
                if (r6 == 0) goto L5a
                r6 = 8
                goto L5b
            L5a:
                r6 = 7
            L5b:
                com.android.server.display.ExternalDisplayStatsService$Injector r2 = r4.mInjector
                boolean r3 = r4.mIsExternalDisplayUsedForAudio
                r2.getClass()
                com.android.internal.util.FrameworkStatsLog.write(r0, r6, r1, r3)
                boolean r0 = com.android.server.display.ExternalDisplayStatsService.DEBUG
                if (r0 == 0) goto Lbe
                java.lang.String r0 = "ExternalDisplayStatsService"
                java.lang.String r1 = "logExternalDisplayPresentationStarted state="
                java.lang.String r2 = " newState="
                java.lang.String r3 = " mIsExternalDisplayUsedForAudio="
                java.lang.StringBuilder r5 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r5, r6, r1, r2, r3)
                boolean r4 = r4.mIsExternalDisplayUsedForAudio
                com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r0, r5, r4)
                goto Lbe
            L7c:
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L39
                throw r4
            L7e:
                com.android.server.display.ExternalDisplayStatsService r4 = r4.mExternalDisplayStatsService
                android.util.SparseIntArray r6 = r4.mExternalDisplayStates
                monitor-enter(r6)
                android.util.SparseIntArray r1 = r4.mExternalDisplayStates     // Catch: java.lang.Throwable -> L8d
                int r5 = r1.get(r5, r2)     // Catch: java.lang.Throwable -> L8d
                if (r5 != r2) goto L8f
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L8d
                goto Lbe
            L8d:
                r4 = move-exception
                goto Lb9
            L8f:
                android.util.SparseIntArray r1 = r4.mExternalDisplayStates     // Catch: java.lang.Throwable -> L8d
                int r1 = r1.size()     // Catch: java.lang.Throwable -> L8d
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L8d
                com.android.server.display.ExternalDisplayStatsService$Injector r6 = r4.mInjector
                boolean r2 = r4.mIsExternalDisplayUsedForAudio
                r6.getClass()
                r6 = 9
                com.android.internal.util.FrameworkStatsLog.write(r0, r6, r1, r2)
                boolean r6 = com.android.server.display.ExternalDisplayStatsService.DEBUG
                if (r6 == 0) goto Lbe
                java.lang.String r6 = "ExternalDisplayStatsService"
                java.lang.String r0 = "logExternalDisplayPresentationEnded state="
                java.lang.String r2 = " countOfExternalDisplays="
                java.lang.String r3 = " mIsExternalDisplayUsedForAudio="
                java.lang.StringBuilder r5 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r5, r1, r0, r2, r3)
                boolean r4 = r4.mIsExternalDisplayUsedForAudio
                com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r6, r5, r4)
                goto Lbe
            Lb9:
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L8d
                throw r4
            Lbb:
                r4 = move-exception
                goto Lbf
            Lbd:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lbb
            Lbe:
                return
            Lbf:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> Lbb
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.LocalService.onPresentation(int, boolean):void");
        }

        public final void performTraversal(SurfaceControl.Transaction transaction, SparseArray sparseArray) {
            DisplayManagerService.this.performTraversalInternal(transaction, sparseArray);
        }

        public final void persistBrightnessTrackerState() {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                DisplayPowerController displayPowerController = (DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0));
                displayPowerController.getClass();
                if (PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                    AdaptiveBrightnessLongtermModelBuilder adaptiveBrightnessLongtermModelBuilder = displayPowerController.mAdaptiveBrightnessLongtermModelBuilder;
                    if (adaptiveBrightnessLongtermModelBuilder != null && !adaptiveBrightnessLongtermModelBuilder.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled) {
                        adaptiveBrightnessLongtermModelBuilder.mBgHandler.post(new AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda1(adaptiveBrightnessLongtermModelBuilder));
                        adaptiveBrightnessLongtermModelBuilder.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled = true;
                    }
                } else {
                    BrightnessTracker brightnessTracker = displayPowerController.mBrightnessTracker;
                    if (brightnessTracker != null && !brightnessTracker.mWriteBrightnessTrackerStateScheduled) {
                        brightnessTracker.mBgHandler.post(new BrightnessTracker$$ExternalSyntheticLambda1(brightnessTracker));
                        brightnessTracker.mWriteBrightnessTrackerStateScheduled = true;
                    }
                }
            }
        }

        public final float registerDisplayBrightnessListener(DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
            float f;
            Slog.d("DisplayManagerService", "registerDisplayBrightnessListener: " + displayBrightnessListener);
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                if (displayBrightnessListener != null) {
                    try {
                        displayManagerService.mDisplayBrightnessListeners.add(displayBrightnessListener);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                f = ((BrightnessPair) displayManagerService.mDisplayBrightnesses.get(0)).brightness;
            }
            return f;
        }

        public final void registerDisplayGroupListener(DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
            DisplayManagerService.this.mDisplayGroupListeners.add(displayGroupListener);
        }

        public final DisplayManagerInternal.DisplayOffloadSession registerDisplayOffloader(int i, DisplayManagerInternal.DisplayOffloader displayOffloader) {
            if (!DisplayManagerService.this.mFlags.mDisplayOffloadFlagState.isEnabled()) {
                return null;
            }
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        Slog.w("DisplayManagerService", "registering DisplayOffloader: LogicalDisplay for displayId=" + i + " is not found. No Op.");
                        return null;
                    }
                    DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(displayLocked.mDisplayId);
                    if (displayPowerControllerInterface == null) {
                        Slog.w("DisplayManagerService", "setting doze state override: DisplayPowerController for displayId=" + i + " is unavailable. No Op.");
                        return null;
                    }
                    DisplayOffloadSessionImpl displayOffloadSessionImpl = new DisplayOffloadSessionImpl(displayOffloader, displayPowerControllerInterface);
                    displayLocked.mDisplayOffloadSession = displayOffloadSessionImpl;
                    DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
                    if (displayOffloadSessionImpl != displayPowerController.mDisplayOffloadSession) {
                        displayPowerController.unblockScreenOnByDisplayOffload();
                        displayPowerController.mDisplayOffloadSession = displayOffloadSessionImpl;
                    }
                    return displayOffloadSessionImpl;
                } finally {
                }
            }
        }

        public final void registerDisplayStateListener(DisplayManagerInternal.DisplayStateListener displayStateListener) {
            Slog.d("DisplayManagerService", "registerDisplayStateListener: " + displayStateListener);
            if (displayStateListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                displayManagerService.mDisplayStateListeners.add(displayStateListener);
            }
        }

        public final void registerDisplayTransactionListener(DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
            if (displayTransactionListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            DisplayManagerService.this.mDisplayTransactionListeners.add(displayTransactionListener);
        }

        public final boolean requestPowerState(int i, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z) {
            DisplayPowerControllerInterface displayPowerControllerInterface;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    DisplayGroup displayGroupLocked = DisplayManagerService.this.mLogicalDisplayMapper.getDisplayGroupLocked(i);
                    if (displayGroupLocked == null) {
                        return true;
                    }
                    int size = ((ArrayList) displayGroupLocked.mDisplays).size();
                    boolean z2 = true;
                    for (int i2 = 0; i2 < size; i2++) {
                        int i3 = ((LogicalDisplay) ((ArrayList) displayGroupLocked.mDisplays).get(i2)).mDisplayId;
                        if ((DisplayManagerService.this.mLogicalDisplayMapper.getDisplayLocked(i3, true).mPrimaryDisplayDevice.getDisplayDeviceInfoLocked().flags & 32) == 0 && (displayPowerControllerInterface = (DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(i3)) != null) {
                            z2 &= ((DisplayPowerController) displayPowerControllerInterface).requestPowerState(displayPowerRequest, z);
                        }
                    }
                    return z2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDisplayAccessUIDs(SparseArray sparseArray) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    displayManagerService.mDisplayAccessUIDs.clear();
                    for (int size = sparseArray.size() - 1; size >= 0; size--) {
                        displayManagerService.mDisplayAccessUIDs.append(sparseArray.keyAt(size), (IntArray) sparseArray.valueAt(size));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDisplayInfoOverrideFromWindowManager(int i, DisplayInfo displayInfo) {
            DisplayManagerService.this.setDisplayInfoOverrideFromWindowManagerInternal(i, displayInfo);
        }

        public final void setDisplayOffsets(int i, int i2, int i3) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return;
                    }
                    if (displayLocked.mDisplayOffsetX != i2 || displayLocked.mDisplayOffsetY != i3) {
                        if (DisplayManagerService.DEBUG) {
                            Slog.d("DisplayManagerService", "Display " + i + " burn-in offset set to (" + i2 + ", " + i3 + ")");
                        }
                        displayLocked.mDisplayOffsetX = i2;
                        displayLocked.mDisplayOffsetY = i3;
                        displayManagerService.scheduleTraversalLocked(false);
                    }
                } finally {
                }
            }
        }

        public final void setDisplayProperties(int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4) {
            boolean z5;
            boolean z6;
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return;
                    }
                    boolean z7 = false;
                    if (displayLocked.mHasContent != z) {
                        if (DisplayManagerService.DEBUG) {
                            Slog.d("DisplayManagerService", "Display " + i + " hasContent flag changed: hasContent=" + z + ", inTraversal=" + z4);
                        }
                        displayLocked.mHasContent = z;
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    displayManagerService.mDisplayModeDirector.mAppRequestObserver.setAppRequest(i, i2, f, f2, f3);
                    boolean z8 = displayManagerService.isMinimalPostProcessingAllowed() && z2;
                    if (z8) {
                        HdrConversionMode hdrConversionModeSettingInternal = displayManagerService.getHdrConversionModeSettingInternal();
                        int preferredHdrOutputType = hdrConversionModeSettingInternal.getConversionMode() == 2 ? displayManagerService.mSystemPreferredHdrOutputType : hdrConversionModeSettingInternal.getPreferredHdrOutputType();
                        if (preferredHdrOutputType != -1) {
                            displayManagerService.mInjector.getClass();
                            z6 = ArrayUtils.contains(DisplayControl.getHdrOutputTypesWithLatency(), preferredHdrOutputType);
                        } else {
                            z6 = false;
                        }
                        if (z6) {
                            z7 = true;
                        }
                    }
                    if (displayLocked.mRequestedMinimalPostProcessing != z8) {
                        displayLocked.mRequestedMinimalPostProcessing = z8;
                        z5 = true;
                    }
                    if (z5) {
                        displayManagerService.scheduleTraversalLocked(z4);
                    }
                    HdrConversionMode hdrConversionMode = displayManagerService.mHdrConversionMode;
                    if (hdrConversionMode == null) {
                        return;
                    }
                    HdrConversionMode hdrConversionMode2 = displayManagerService.mOverrideHdrConversionMode;
                    if (hdrConversionMode2 == null && (z3 || z7)) {
                        displayManagerService.mOverrideHdrConversionMode = new HdrConversionMode(1);
                        displayManagerService.setHdrConversionModeInternal(displayManagerService.mHdrConversionMode);
                        displayManagerService.handleLogicalDisplayChangedLocked(displayLocked);
                    } else if (hdrConversionMode2 != null && !z3 && !z7) {
                        displayManagerService.mOverrideHdrConversionMode = null;
                        displayManagerService.setHdrConversionModeInternal(hdrConversionMode);
                        displayManagerService.handleLogicalDisplayChangedLocked(displayLocked);
                    }
                } finally {
                }
            }
        }

        public final void setDisplayScalingDisabled(int i, boolean z) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return;
                    }
                    if (displayLocked.mDisplayScalingDisabled != z) {
                        if (DisplayManagerService.DEBUG) {
                            Slog.d("DisplayManagerService", "Display " + i + " content scaling disabled = " + z);
                        }
                        displayLocked.mDisplayScalingDisabled = z;
                        displayManagerService.scheduleTraversalLocked(false);
                    }
                } finally {
                }
            }
        }

        public final void setDisplayStateOverride(IBinder iBinder, int i) {
            StringBuilder sb = new StringBuilder("[api] setDisplayStateOverride(l): stateOverride=");
            sb.append(Display.stateToString(i));
            sb.append(" (");
            sb.append(Objects.hashCode(iBinder));
            sb.append(")");
            DisplayManagerService$BinderService$$ExternalSyntheticOutline0.m("DisplayManagerService", sb, false);
            DisplayManagerService.m454$$Nest$msetDisplayStateOverrideInternal(DisplayManagerService.this, iBinder, i);
        }

        public final boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3) {
            return DisplayManagerService.this.setDisplayedContentSamplingEnabledInternal(i, z, i2, i3);
        }

        public final void setForceListenProcess(int i) {
            DisplayManagerService.this.mForceListenProcessId = i;
        }

        public final int setFreezeBrightnessMode(boolean z) {
            int freezeBrightnessMode;
            synchronized (DisplayManagerService.this.mSyncRoot) {
                freezeBrightnessMode = ((DisplayPowerController) ((DisplayPowerControllerInterface) DisplayManagerService.this.mDisplayPowerControllers.get(0))).setFreezeBrightnessMode(z);
            }
            return freezeBrightnessMode;
        }

        public final void setWindowManagerMirroring(int i, boolean z) {
            synchronized (DisplayManagerService.this.mSyncRoot) {
                try {
                    DisplayDevice deviceForDisplayLocked = DisplayManagerService.this.getDeviceForDisplayLocked(i);
                    if (deviceForDisplayLocked != null) {
                        deviceForDisplayLocked.setWindowManagerMirroringLocked(z);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    IBinder displayToken = displayManagerService.getDisplayToken(i);
                    if (displayToken == null) {
                        return null;
                    }
                    LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i, true);
                    if (displayLocked == null) {
                        return null;
                    }
                    DisplayInfo displayInfoLocked = displayLocked.getDisplayInfoLocked();
                    int naturalWidth = displayInfoLocked.getNaturalWidth();
                    int naturalHeight = displayInfoLocked.getNaturalHeight();
                    int i2 = displayInfoLocked.rotation;
                    if (i2 == 1 || i2 == 3) {
                        naturalWidth = displayInfoLocked.getNaturalHeight();
                        naturalHeight = displayInfoLocked.getNaturalWidth();
                    }
                    return ScreenCapture.captureDisplay(new ScreenCapture.DisplayCaptureArgs.Builder(displayToken).setSize(naturalWidth, naturalHeight).setCaptureSecureLayers(true).setAllowProtected(true).build());
                } finally {
                }
            }
        }

        public final void unregisterDisplayBrightnessListener(DisplayManagerInternal.DisplayBrightnessListener displayBrightnessListener) {
            Slog.d("DisplayManagerService", "unregisterDisplayBrightnessListener: " + displayBrightnessListener);
            if (displayBrightnessListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                displayManagerService.mDisplayBrightnessListeners.remove(displayBrightnessListener);
            }
        }

        public final void unregisterDisplayGroupListener(DisplayManagerInternal.DisplayGroupListener displayGroupListener) {
            DisplayManagerService.this.mDisplayGroupListeners.remove(displayGroupListener);
        }

        public final void unregisterDisplayStateListener(final DisplayManagerInternal.DisplayStateListener displayStateListener) {
            Slog.d("DisplayManagerService", "unregisterDisplayStateListener: " + displayStateListener);
            if (displayStateListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                displayManagerService.mDisplayStateListeners.removeIf(new Predicate() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return displayStateListener.equals((DisplayManagerInternal.DisplayStateListener) obj);
                    }
                });
            }
        }

        public final void unregisterDisplayTransactionListener(DisplayManagerInternal.DisplayTransactionListener displayTransactionListener) {
            if (displayTransactionListener == null) {
                throw new IllegalArgumentException("listener must not be null");
            }
            DisplayManagerService.this.mDisplayTransactionListeners.remove(displayTransactionListener);
        }

        public final int updateDexDisplayState(boolean z) {
            boolean z2;
            int i;
            float f;
            float f2;
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                Iterator it = ((ArrayList) displayManagerService.mDisplayDeviceRepo.mDisplayDevices).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (((DisplayDevice) it.next()).getDisplayDeviceInfoLocked().type == 2) {
                        z2 = true;
                        break;
                    }
                }
            }
            if (z2) {
                if (z) {
                    DisplayDeviceRepository displayDeviceRepository = displayManagerService.mDisplayDeviceRepo;
                    PowerManager.WakeLock wakeLock = displayDeviceRepository.mHDMIWakeLock;
                    if (wakeLock != null && wakeLock.isHeld()) {
                        displayDeviceRepository.mHDMIWakeLock.release();
                    }
                } else {
                    DisplayDeviceRepository displayDeviceRepository2 = displayManagerService.mDisplayDeviceRepo;
                    PowerManager.WakeLock wakeLock2 = displayDeviceRepository2.mHDMIWakeLock;
                    if (wakeLock2 != null && !wakeLock2.isHeld()) {
                        displayDeviceRepository2.mHDMIWakeLock.acquire();
                    }
                }
            }
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    DisplayDevice dexDisplayDeviceLocked = displayManagerService.mDisplayDeviceRepo.getDexDisplayDeviceLocked();
                    DisplayDeviceInfo displayDeviceInfoLocked = dexDisplayDeviceLocked != null ? dexDisplayDeviceLocked.getDisplayDeviceInfoLocked() : null;
                    i = -1;
                    if (displayDeviceInfoLocked != null && (displayDeviceInfoLocked.flags & 1048576) != 0) {
                        LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(dexDisplayDeviceLocked);
                        if (displayLocked == null) {
                            dexDisplayDeviceLocked.updateDexEnabledStateLocked(false);
                            Slog.w("DisplayManagerService", "updateDexDisplayStateInternalLocked: cannot found display: " + displayDeviceInfoLocked);
                        } else {
                            i = displayLocked.mDisplayId;
                            BrightnessPair brightnessPair = (BrightnessPair) displayManagerService.mDisplayBrightnesses.get(i);
                            if (!z || brightnessPair == null) {
                                f = displayDeviceInfoLocked.brightnessDefault;
                                f2 = f;
                            } else {
                                f = brightnessPair.brightness;
                                f2 = brightnessPair.sdrBrightness;
                            }
                            if (z) {
                                dexDisplayDeviceLocked.updateDexEnabledStateLocked(true);
                                dexDisplayDeviceLocked.requestDisplayStateLocked(2, f, f2, null);
                            } else {
                                dexDisplayDeviceLocked.requestDisplayStateLocked(1, f, f2, null);
                                dexDisplayDeviceLocked.updateDexEnabledStateLocked(false);
                            }
                            displayLocked.updateLocked(displayManagerService.mDisplayDeviceRepo, displayManagerService.mLogicalDisplayMapper.mSyntheticModeManager);
                            displayManagerService.sendDisplayEventLocked(displayLocked, 2);
                            displayManagerService.scheduleTraversalLocked(false);
                        }
                    }
                    Slog.w("DisplayManagerService", "updateDexDisplayStateInternal: abnormal device=" + displayDeviceInfoLocked);
                } finally {
                }
            }
            return i;
        }

        public final ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    IBinder displayToken = displayManagerService.getDisplayToken(i);
                    if (displayToken == null) {
                        return null;
                    }
                    return ScreenCapture.captureDisplay(new ScreenCapture.DisplayCaptureArgs.Builder(displayToken).build());
                } finally {
                }
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

        public final void addDisplayEvent(int i, int i2) {
            Pair pair = (Pair) this.mDisplayEvents.get(r0.size() - 1);
            if (((Integer) pair.first).intValue() == i && ((Integer) pair.second).intValue() == i2) {
                return;
            }
            this.mDisplayEvents.add(new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
        }

        public final void sendPendingDisplayEvent() {
            int i = 0;
            while (true) {
                if (i >= this.mDisplayEvents.size()) {
                    break;
                }
                Pair pair = (Pair) this.mDisplayEvents.get(i);
                boolean z = DisplayManagerService.DEBUG;
                CallbackRecord callbackRecord = this.mCallbackRecord;
                if (z) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Send pending display event #", " ");
                    m.append(pair.first);
                    m.append("/");
                    m.append(pair.second);
                    m.append(" to ");
                    m.append(callbackRecord.mUid);
                    m.append("/");
                    BatteryService$$ExternalSyntheticOutline0.m(m, callbackRecord.mPid, "DisplayManagerService");
                }
                if (!callbackRecord.notifyDisplayEventAsync(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue())) {
                    BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("Drop pending events for dead process "), callbackRecord.mPid, "DisplayManagerService");
                    break;
                }
                i++;
            }
            this.mDisplayEvents.clear();
        }
    }

    public final class PresentationDisplay {
        public int displayId;
        public String packageName;

        public final boolean equals(Object obj) {
            if (obj != null) {
                PresentationDisplay presentationDisplay = (PresentationDisplay) obj;
                if (presentationDisplay.displayId == this.displayId && presentationDisplay.packageName.equals(this.packageName)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            String str = this.packageName;
            return this.displayId * (str == null ? -1 : str.hashCode());
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(DisplayManagerService.this.mHandler);
            DisplayManagerService.this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("minimal_post_processing_allowed"), false, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            DisplayManagerService displayManagerService = DisplayManagerService.this;
            synchronized (displayManagerService.mSyncRoot) {
                boolean z2 = true;
                if (Settings.Secure.getIntForUser(displayManagerService.mContext.getContentResolver(), "minimal_post_processing_allowed", 1, -2) == 0) {
                    z2 = false;
                }
                displayManagerService.setMinimalPostProcessingAllowed(z2);
                displayManagerService.scheduleTraversalLocked(false);
            }
        }
    }

    public final class SyncRoot {
    }

    public final class UidImportanceListener implements ActivityManager.OnUidImportanceListener {
        public UidImportanceListener() {
        }

        public final void onUidImportance(int i, int i2) {
            synchronized (DisplayManagerService.this.mPendingCallbackSelfLocked) {
                try {
                    if (i2 >= 1000) {
                        Slog.d("DisplayManagerService", "Drop pending events for gone uid " + i);
                        DisplayManagerService.this.mPendingCallbackSelfLocked.delete(i);
                        return;
                    }
                    if (i2 >= 400) {
                        return;
                    }
                    SparseArray sparseArray = (SparseArray) DisplayManagerService.this.mPendingCallbackSelfLocked.get(i);
                    if (sparseArray == null) {
                        return;
                    }
                    if (DisplayManagerService.DEBUG) {
                        Slog.d("DisplayManagerService", "Uid " + i + " becomes " + i2);
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

    /* renamed from: -$$Nest$mconnectWifiDisplayInternal, reason: not valid java name */
    public static void m437$$Nest$mconnectWifiDisplayInternal(DisplayManagerService displayManagerService, final String str) {
        synchronized (displayManagerService.mSyncRoot) {
            final WifiDisplayAdapter wifiDisplayAdapter = displayManagerService.mWifiDisplayAdapter;
            if (wifiDisplayAdapter != null) {
                android.util.Slog.d("WifiDisplayAdapter", "requestConnectLocked: address=" + str);
                ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.4
                    public final /* synthetic */ String val$address;

                    public AnonymousClass4(final String str2) {
                        r2 = str2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        WifiDisplayController wifiDisplayController = WifiDisplayAdapter.this.mDisplayController;
                        if (wifiDisplayController != null) {
                            wifiDisplayController.requestConnect(r2);
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0116, code lost:
    
        if (r26.canProjectSecureVideo() != false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00f7, code lost:
    
        if (r26.canProjectVideo() != false) goto L68;
     */
    /* renamed from: -$$Nest$mcreateVirtualDisplayInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m438$$Nest$mcreateVirtualDisplayInternal(com.android.server.display.DisplayManagerService r23, android.hardware.display.VirtualDisplayConfig r24, android.hardware.display.IVirtualDisplayCallback r25, android.media.projection.IMediaProjection r26, android.companion.virtual.IVirtualDevice r27, android.window.DisplayWindowPolicyController r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 864
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.m438$$Nest$mcreateVirtualDisplayInternal(com.android.server.display.DisplayManagerService, android.hardware.display.VirtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback, android.media.projection.IMediaProjection, android.companion.virtual.IVirtualDevice, android.window.DisplayWindowPolicyController, java.lang.String):int");
    }

    /* renamed from: -$$Nest$mdeliverDeviceEvent, reason: not valid java name */
    public static void m439$$Nest$mdeliverDeviceEvent(int i, Bundle bundle, DisplayManagerService displayManagerService) {
        int size;
        int i2;
        displayManagerService.getClass();
        Slog.d("DisplayManagerService", "Delivering device event=" + i);
        synchronized (displayManagerService.mSyncRoot) {
            try {
                size = displayManagerService.mCallbacks.size();
                displayManagerService.mTempCallbacks.clear();
                for (int i3 = 0; i3 < size; i3++) {
                    displayManagerService.mTempCallbacks.add((CallbackRecord) displayManagerService.mCallbacks.valueAt(i3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i2 = 0; i2 < size; i2++) {
            CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mTempCallbacks.get(i2);
            callbackRecord.getClass();
            try {
                callbackRecord.mCallback.onDeviceEvent(bundle, i);
            } catch (RemoteException e) {
                Slog.w("Failed to notify process " + callbackRecord.mPid + " that displays changed, assuming it died.", e);
                callbackRecord.binderDied();
            }
        }
        displayManagerService.mTempCallbacks.clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0006, code lost:
    
        if (r8.mExtraDisplayEventLogging != false) goto L8;
     */
    /* renamed from: -$$Nest$mdeliverDisplayEvent, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m440$$Nest$mdeliverDisplayEvent(com.android.server.display.DisplayManagerService r8, int r9, android.util.ArraySet r10, int r11) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.m440$$Nest$mdeliverDisplayEvent(com.android.server.display.DisplayManagerService, int, android.util.ArraySet, int):void");
    }

    /* renamed from: -$$Nest$mdeliverDisplayVolumeEvent, reason: not valid java name */
    public static void m441$$Nest$mdeliverDisplayVolumeEvent(int i, Bundle bundle, DisplayManagerService displayManagerService) {
        int size;
        if (DEBUG) {
            displayManagerService.getClass();
            Slog.d("DisplayManagerService", "Delivering display volume event=" + i);
        }
        synchronized (displayManagerService.mSyncRoot) {
            try {
                size = displayManagerService.mCallbacks.size();
                displayManagerService.mTempCallbacks.clear();
                for (int i2 = 0; i2 < size; i2++) {
                    displayManagerService.mTempCallbacks.add((CallbackRecord) displayManagerService.mCallbacks.valueAt(i2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mTempCallbacks.get(i3);
            callbackRecord.getClass();
            try {
                callbackRecord.mCallback.onDisplayVolumeEvent(i, bundle);
            } catch (RemoteException e) {
                Slog.w("Failed to notify process " + callbackRecord.mPid + " that displays changed, assuming it died.", e);
                callbackRecord.binderDied();
            }
        }
        displayManagerService.mTempCallbacks.clear();
        VolumeController volumeController = displayManagerService.mWifiDisplayAdapter.mVolumeController;
        displayManagerService.mVolumeController = volumeController;
        if (volumeController != null) {
            volumeController.mMinVolume = bundle.getInt("minVol");
            volumeController.mMaxVolume = bundle.getInt("maxVol");
            volumeController.mVolume = bundle.getInt("curVol");
            volumeController.mMuted = bundle.getBoolean("isMute", false);
            StringBuilder sb = new StringBuilder("notifyDisplayVolumeEvnet: max=");
            sb.append(volumeController.mMaxVolume);
            sb.append(", min=");
            sb.append(volumeController.mMinVolume);
            sb.append(", vol=");
            sb.append(volumeController.mVolume);
            sb.append(", muted=");
            RCPManagerService$$ExternalSyntheticOutline0.m("VolumeController", sb, volumeController.mMuted);
        }
    }

    /* renamed from: -$$Nest$mdeliverDisplayVolumeKeyEvent, reason: not valid java name */
    public static void m442$$Nest$mdeliverDisplayVolumeKeyEvent(DisplayManagerService displayManagerService, int i) {
        int size;
        int i2;
        if (DEBUG) {
            displayManagerService.getClass();
            Slog.d("DisplayManagerService", "Delivering volume Key event=" + i);
        }
        synchronized (displayManagerService.mSyncRoot) {
            try {
                size = displayManagerService.mCallbacks.size();
                displayManagerService.mTempCallbacks.clear();
                for (int i3 = 0; i3 < size; i3++) {
                    displayManagerService.mTempCallbacks.add((CallbackRecord) displayManagerService.mCallbacks.valueAt(i3));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i2 = 0; i2 < size; i2++) {
            CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mTempCallbacks.get(i2);
            callbackRecord.getClass();
            try {
                callbackRecord.mCallback.onDisplayVolumeKeyEvent(i);
            } catch (RemoteException e) {
                Slog.w("Failed to notify process " + callbackRecord.mPid + " that displays changed, assuming it died.", e);
                callbackRecord.binderDied();
            }
        }
        displayManagerService.mTempCallbacks.clear();
    }

    /* renamed from: -$$Nest$mdeliverPresentationDisplayInfoEvent, reason: not valid java name */
    public static void m443$$Nest$mdeliverPresentationDisplayInfoEvent(DisplayManagerService displayManagerService, int i, int i2, String str) {
        displayManagerService.getClass();
        PresentationDisplay presentationDisplay = new PresentationDisplay();
        presentationDisplay.displayId = i;
        presentationDisplay.packageName = str;
        synchronized (displayManagerService.mSyncRoot) {
            try {
                if (i2 == 1) {
                    displayManagerService.addPresentationDisplay(presentationDisplay);
                } else if (i2 == 0) {
                    displayManagerService.removePresentationDisplay(presentationDisplay);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m444$$Nest$mdumpInternal(DisplayManagerService displayManagerService, final PrintWriter printWriter) {
        BrightnessTracker brightnessTracker;
        displayManagerService.getClass();
        printWriter.println("DISPLAY MANAGER (dumpsys display)");
        synchronized (displayManagerService.mSyncRoot) {
            try {
                brightnessTracker = displayManagerService.mBrightnessTracker;
                printWriter.println("  mSafeMode=" + displayManagerService.mSafeMode);
                printWriter.println("  mPendingTraversal=" + displayManagerService.mPendingTraversal);
                printWriter.println("  mViewports=" + displayManagerService.mViewports);
                printWriter.println("  mDefaultDisplayDefaultColorMode=" + displayManagerService.mDefaultDisplayDefaultColorMode);
                printWriter.println("  mWifiDisplayScanRequestCount=" + displayManagerService.mWifiDisplayScanRequestCount);
                printWriter.println("  mStableDisplaySize=" + displayManagerService.mStableDisplaySize);
                printWriter.println("  mMinimumBrightnessCurve=" + displayManagerService.mMinimumBrightnessCurve);
                if (displayManagerService.mUserPreferredMode != null) {
                    printWriter.println(" mUserPreferredMode=" + displayManagerService.mUserPreferredMode);
                }
                printWriter.println();
                if (!displayManagerService.mAreUserDisabledHdrTypesAllowed) {
                    printWriter.println("  mUserDisabledHdrTypes: size=" + displayManagerService.mUserDisabledHdrTypes.length);
                    for (int i : displayManagerService.mUserDisabledHdrTypes) {
                        printWriter.println("  " + i);
                    }
                }
                if (displayManagerService.mHdrConversionMode != null) {
                    printWriter.println("  mHdrConversionMode=" + displayManagerService.mHdrConversionMode);
                }
                printWriter.println();
                int size = displayManagerService.mDisplayStates.size();
                printWriter.println("Display States: size=" + size);
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = displayManagerService.mDisplayStates.keyAt(i2);
                    int valueAt = displayManagerService.mDisplayStates.valueAt(i2);
                    BrightnessPair brightnessPair = (BrightnessPair) displayManagerService.mDisplayBrightnesses.valueAt(i2);
                    printWriter.println("  Display Id=" + keyAt);
                    printWriter.println("  Display State=" + Display.stateToString(valueAt));
                    printWriter.println("  Display Brightness=" + brightnessPair.brightness);
                    printWriter.println("  Display SdrBrightness=" + brightnessPair.sdrBrightness);
                }
                printWriter.println();
                int size2 = displayManagerService.mDisplayStateOverrideLocks.size();
                printWriter.println("Display State Override Locks: size=" + size2);
                for (int i3 = 0; i3 < size2; i3++) {
                    printWriter.println("  " + i3 + ": " + displayManagerService.mDisplayStateOverrideLocks.get(i3));
                }
                printWriter.println();
                int size3 = displayManagerService.mDisplayStateOverrides.size();
                printWriter.println("Display State Overrides: size=" + size3);
                for (int i4 = 0; i4 < size3; i4++) {
                    int keyAt2 = displayManagerService.mDisplayStateOverrides.keyAt(i4);
                    int valueAt2 = displayManagerService.mDisplayStateOverrides.valueAt(i4);
                    printWriter.println("  Display Id=" + keyAt2);
                    printWriter.println("  Display State Override=" + Display.stateToString(valueAt2));
                }
                PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
                indentingPrintWriter.increaseIndent();
                printWriter.println();
                printWriter.println("Display Adapters: size=" + displayManagerService.mDisplayAdapters.size());
                Iterator it = displayManagerService.mDisplayAdapters.iterator();
                while (it.hasNext()) {
                    DisplayAdapter displayAdapter = (DisplayAdapter) it.next();
                    printWriter.println("  " + displayAdapter.mName);
                    displayAdapter.dumpLocked(indentingPrintWriter);
                }
                printWriter.println();
                printWriter.println("Display Devices: size=" + ((ArrayList) displayManagerService.mDisplayDeviceRepo.mDisplayDevices).size());
                displayManagerService.mDisplayDeviceRepo.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda4(1, printWriter, indentingPrintWriter));
                printWriter.println();
                displayManagerService.mLogicalDisplayMapper.dumpLocked(printWriter);
                int size4 = displayManagerService.mCallbacks.size();
                printWriter.println();
                printWriter.println("Callbacks: size=" + size4);
                for (int i5 = 0; i5 < size4; i5++) {
                    CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mCallbacks.valueAt(i5);
                    printWriter.println("  " + i5 + ": mPid=" + callbackRecord.mPid + ", mWifiDisplayScanRequested=" + callbackRecord.mWifiDisplayScanRequested + ", mWifiDisplayScanRequestedTime=" + callbackRecord.mWifiDisplayScanRequestedTime);
                }
                int size5 = displayManagerService.mDisplayPowerControllers.size();
                printWriter.println();
                printWriter.println("Display Power Controllers: size=" + size5);
                for (int i6 = 0; i6 < size5; i6++) {
                    ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService.mDisplayPowerControllers.valueAt(i6))).dump(printWriter);
                }
                printWriter.println();
                displayManagerService.mPersistentDataStore.dump(printWriter);
                int size6 = displayManagerService.mDisplayBrightnessListeners.size();
                if (size6 > 0) {
                    printWriter.println();
                    printWriter.println("mDisplayBrightnessListeners: size=" + size6);
                    final int i7 = 0;
                    displayManagerService.mDisplayBrightnessListeners.forEach(new Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i8 = i7;
                            PrintWriter printWriter2 = printWriter;
                            switch (i8) {
                                case 0:
                                    printWriter2.println("  " + ((DisplayManagerInternal.DisplayBrightnessListener) obj));
                                    break;
                                default:
                                    printWriter2.println("  " + ((DisplayManagerInternal.DisplayStateListener) obj));
                                    break;
                            }
                        }
                    });
                }
                int size7 = displayManagerService.mDisplayStateListeners.size();
                if (size7 > 0) {
                    printWriter.println();
                    printWriter.println("mDisplayStateListenerInfo: size=" + size7);
                    final int i8 = 1;
                    displayManagerService.mDisplayStateListeners.forEach(new Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i82 = i8;
                            PrintWriter printWriter2 = printWriter;
                            switch (i82) {
                                case 0:
                                    printWriter2.println("  " + ((DisplayManagerInternal.DisplayBrightnessListener) obj));
                                    break;
                                default:
                                    printWriter2.println("  " + ((DisplayManagerInternal.DisplayStateListener) obj));
                                    break;
                            }
                        }
                    });
                }
                printWriter.println("VRR request about brightness animation:");
                printWriter.println("  mBrightnessAnimStarted=" + displayManagerService.mBrightnessAnimStarted);
                printWriter.println("  mBrightnessAnimRefreshRateToken=" + displayManagerService.mBrightnessAnimRefreshRateToken);
                int size8 = displayManagerService.mHbmBrightnessCallbacks.size();
                printWriter.println();
                printWriter.println("HBM brightness Listener: size=" + size8);
                if (size8 > 0) {
                    for (int i9 = 0; i9 < size8; i9++) {
                        HbmBrightnessCallbackRecord hbmBrightnessCallbackRecord = (HbmBrightnessCallbackRecord) displayManagerService.mHbmBrightnessCallbacks.valueAt(i9);
                        printWriter.println("  " + i9 + ": mUid=" + hbmBrightnessCallbackRecord.mUid + " mPid=" + hbmBrightnessCallbackRecord.mPid);
                    }
                }
                printWriter.println();
                printWriter.println("MTK power throttling as per HBM");
                printWriter.println("  SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING=" + PowerManagerUtil.SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING);
                printWriter.println("  mIsMtkPtHintExist=" + displayManagerService.mIsMtkPtHintExist);
                printWriter.println("  mMtKPowerThrottling=" + displayManagerService.mMtKPowerThrottling);
                printWriter.println();
                int size9 = displayManagerService.mDisplayWindowPolicyControllers.size();
                printWriter.println();
                printWriter.println("Display Window Policy Controllers: size=" + size9);
                for (int i10 = 0; i10 < size9; i10++) {
                    printWriter.print("Display " + displayManagerService.mDisplayWindowPolicyControllers.keyAt(i10) + ":");
                    ((DisplayWindowPolicyController) ((Pair) displayManagerService.mDisplayWindowPolicyControllers.valueAt(i10)).second).dump("  ", printWriter);
                }
            } finally {
            }
        }
        if (brightnessTracker != null) {
            printWriter.println();
            brightnessTracker.dump(printWriter);
        }
        printWriter.println();
        displayManagerService.mDisplayModeDirector.dump(printWriter);
        displayManagerService.mBrightnessSynchronizer.dump(printWriter);
        SmallAreaDetectionController smallAreaDetectionController = displayManagerService.mSmallAreaDetectionController;
        if (smallAreaDetectionController != null) {
            printWriter.println("Small area detection allowlist");
            printWriter.println("  Packages:");
            synchronized (smallAreaDetectionController.mLock) {
                try {
                    for (String str : ((ArrayMap) smallAreaDetectionController.mAllowPkgMap).keySet()) {
                        printWriter.println("    " + str + " threshold = " + ((ArrayMap) smallAreaDetectionController.mAllowPkgMap).get(str));
                    }
                } finally {
                }
            }
        }
        printWriter.println();
        displayManagerService.mFlags.dump(printWriter);
    }

    /* renamed from: -$$Nest$mloadBrightnessConfigurations, reason: not valid java name */
    public static void m445$$Nest$mloadBrightnessConfigurations(DisplayManagerService displayManagerService) {
        int userSerialNumber = displayManagerService.getUserManager().getUserSerialNumber(displayManagerService.mContext.getUserId());
        synchronized (displayManagerService.mSyncRoot) {
            displayManagerService.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda14(displayManagerService, userSerialNumber, 1), true);
        }
    }

    /* renamed from: -$$Nest$mregisterAdditionalDisplayAdapters, reason: not valid java name */
    public static void m446$$Nest$mregisterAdditionalDisplayAdapters(DisplayManagerService displayManagerService) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                if (!displayManagerService.mSafeMode) {
                    OverlayDisplayAdapter overlayDisplayAdapter = new OverlayDisplayAdapter(displayManagerService.mSyncRoot, displayManagerService.mContext, displayManagerService.mHandler, displayManagerService.mDisplayDeviceRepo, displayManagerService.mUiHandler, displayManagerService.mFlags);
                    displayManagerService.mDisplayAdapters.add(overlayDisplayAdapter);
                    overlayDisplayAdapter.mHandler.post(new OverlayDisplayAdapter.AnonymousClass1(0, overlayDisplayAdapter));
                    displayManagerService.registerWifiDisplayAdapterLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mregisterCallbackInternal, reason: not valid java name */
    public static void m447$$Nest$mregisterCallbackInternal(int i, int i2, long j, IDisplayManagerCallback iDisplayManagerCallback, DisplayManagerService displayManagerService) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mCallbacks.get(i);
                if (callbackRecord != null) {
                    callbackRecord.mEventsMask.set(j);
                    return;
                }
                CallbackRecord callbackRecord2 = new CallbackRecord(i, i2, j, iDisplayManagerCallback, displayManagerService);
                try {
                    iDisplayManagerCallback.asBinder().linkToDeath(callbackRecord2, 0);
                    displayManagerService.mCallbacks.put(i, callbackRecord2);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mregisterDefaultDisplayAdapters, reason: not valid java name */
    public static void m448$$Nest$mregisterDefaultDisplayAdapters(DisplayManagerService displayManagerService) {
        synchronized (displayManagerService.mSyncRoot) {
            Injector injector = displayManagerService.mInjector;
            SyncRoot syncRoot = displayManagerService.mSyncRoot;
            Context context = displayManagerService.mContext;
            DisplayManagerHandler displayManagerHandler = displayManagerService.mHandler;
            DisplayDeviceRepository displayDeviceRepository = displayManagerService.mDisplayDeviceRepo;
            DisplayManagerFlags displayManagerFlags = displayManagerService.mFlags;
            DisplayNotificationManager displayNotificationManager = displayManagerService.mDisplayNotificationManager;
            injector.getClass();
            LocalDisplayAdapter localDisplayAdapter = new LocalDisplayAdapter(syncRoot, context, displayManagerHandler, displayDeviceRepository, displayManagerFlags, displayNotificationManager, new LocalDisplayAdapter.Injector());
            displayManagerService.mDisplayAdapters.add(localDisplayAdapter);
            localDisplayAdapter.registerLocked();
            Injector injector2 = displayManagerService.mInjector;
            SyncRoot syncRoot2 = displayManagerService.mSyncRoot;
            Context context2 = displayManagerService.mContext;
            DisplayManagerHandler displayManagerHandler2 = displayManagerService.mHandler;
            DisplayDeviceRepository displayDeviceRepository2 = displayManagerService.mDisplayDeviceRepo;
            DisplayManagerFlags displayManagerFlags2 = displayManagerService.mFlags;
            injector2.getClass();
            VirtualDisplayAdapter virtualDisplayAdapter = new VirtualDisplayAdapter(syncRoot2, context2, displayManagerHandler2, displayDeviceRepository2, new VirtualDisplayAdapter.AnonymousClass1(), displayManagerFlags2);
            displayManagerService.mVirtualDisplayAdapter = virtualDisplayAdapter;
            displayManagerService.mDisplayAdapters.add(virtualDisplayAdapter);
            displayManagerService.mVirtualDisplayAdapter.mLogicalDisplayMapper = displayManagerService.mLogicalDisplayMapper;
        }
    }

    /* renamed from: -$$Nest$mregisterHbmBrightnessCallbackInternal, reason: not valid java name */
    public static void m449$$Nest$mregisterHbmBrightnessCallbackInternal(int i, int i2, IHbmBrightnessCallback iHbmBrightnessCallback, DisplayManagerService displayManagerService) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                if (((HbmBrightnessCallbackRecord) displayManagerService.mHbmBrightnessCallbacks.get(i)) != null) {
                    Slog.d("DisplayManagerService", "registerHbmBrightnessCallbackInternal: already registered. pid=" + i + ", uid=" + i2);
                    return;
                }
                Slog.d("DisplayManagerService", "[api] registerHbmBrightnessCallbackInternal: callingPid=" + i + " callingUid=" + i2);
                HbmBrightnessCallbackRecord hbmBrightnessCallbackRecord = new HbmBrightnessCallbackRecord(i, i2, iHbmBrightnessCallback, displayManagerService);
                try {
                    iHbmBrightnessCallback.asBinder().linkToDeath(hbmBrightnessCallbackRecord, 0);
                    displayManagerService.mHbmBrightnessCallbacks.put(i, hbmBrightnessCallbackRecord);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0095, code lost:
    
        r4 = r9.mIsHbmEnabled;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009b, code lost:
    
        if (r12 <= 1.0f) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009d, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009e, code lost:
    
        r9.mIsHbmEnabled = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a0, code lost:
    
        if (r4 == r0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a4, code lost:
    
        if (com.android.server.power.PowerManagerUtil.SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a8, code lost:
    
        if (r9.mIsMtkPtHintExist == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00aa, code lost:
    
        if (r0 == false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ac, code lost:
    
        com.android.server.power.Slog.d("DisplayManagerService", "Mtk power throttling enabled");
        r9.mMtKPowerThrottling.acquire();
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b9, code lost:
    
        com.android.server.power.Slog.d("DisplayManagerService", "Mtk power throttling disabled");
        r9.mMtKPowerThrottling.release();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c5, code lost:
    
        r9.sendDisplayHbmBrightnessEventLocked(r10, r9.mIsHbmEnabled);
     */
    /* renamed from: -$$Nest$mrequestDisplayStateInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m450$$Nest$mrequestDisplayStateInternal(com.android.server.display.DisplayManagerService r9, int r10, int r11, float r12, float r13) {
        /*
            Method dump skipped, instructions count: 391
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.m450$$Nest$mrequestDisplayStateInternal(com.android.server.display.DisplayManagerService, int, int, float, float):void");
    }

    /* renamed from: -$$Nest$mrotateVirtualDisplayInternal, reason: not valid java name */
    public static void m451$$Nest$mrotateVirtualDisplayInternal(DisplayManagerService displayManagerService, IBinder iBinder, int i) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                VirtualDisplayAdapter virtualDisplayAdapter = displayManagerService.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter == null) {
                    return;
                }
                VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = (VirtualDisplayAdapter.VirtualDisplayDevice) virtualDisplayAdapter.mVirtualDisplayDevices.get(iBinder);
                if (virtualDisplayDevice == null) {
                    return;
                }
                LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(virtualDisplayDevice);
                if (displayLocked == null) {
                    return;
                }
                displayManagerService.mWindowManagerInternal.freezeDisplayRotation(displayLocked.mDisplayId, i, "HiddenDisplay");
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetAreUserDisabledHdrTypesAllowedInternal, reason: not valid java name */
    public static void m452$$Nest$msetAreUserDisabledHdrTypesAllowedInternal(DisplayManagerService displayManagerService, boolean z) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                if (displayManagerService.mAreUserDisabledHdrTypesAllowed == z) {
                    return;
                }
                displayManagerService.mAreUserDisabledHdrTypesAllowed = z;
                if (displayManagerService.mUserDisabledHdrTypes.length == 0) {
                    return;
                }
                Settings.Global.putInt(displayManagerService.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", z ? 1 : 0);
                int[] iArr = new int[0];
                if (!displayManagerService.mAreUserDisabledHdrTypesAllowed) {
                    iArr = displayManagerService.mUserDisabledHdrTypes;
                }
                displayManagerService.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda7(displayManagerService, iArr, 1), true);
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetBrightnessConfigurationForUserWithStatsInternal, reason: not valid java name */
    public static void m453$$Nest$msetBrightnessConfigurationForUserWithStatsInternal(DisplayManagerService displayManagerService, BrightnessConfiguration brightnessConfiguration, int i, String str, List list, List list2, List list3) {
        displayManagerService.validateBrightnessConfiguration(brightnessConfiguration);
        int userSerialNumber = displayManagerService.getUserManager().getUserSerialNumber(i);
        synchronized (displayManagerService.mSyncRoot) {
            try {
                try {
                    PersistentDataStore persistentDataStore = displayManagerService.mPersistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    PersistentDataStore.BrightnessConfigurations brightnessConfigurations = persistentDataStore.mGlobalBrightnessConfigurations;
                    if (PersistentDataStore.BrightnessConfigurations.m463$$Nest$msetBrightnessConfigurationForUser(brightnessConfigurations, brightnessConfiguration, userSerialNumber, str)) {
                        persistentDataStore.mDirty = true;
                    }
                    brightnessConfigurations.saveHistory(list, list2, list3);
                    displayManagerService.mPersistentDataStore.saveIfNeeded();
                    ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService.mDisplayPowerControllers.get(0))).setBrightnessConfiguration(brightnessConfiguration, true);
                } catch (Throwable th) {
                    displayManagerService.mPersistentDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* renamed from: -$$Nest$msetDisplayStateOverrideInternal, reason: not valid java name */
    public static void m454$$Nest$msetDisplayStateOverrideInternal(DisplayManagerService displayManagerService, IBinder iBinder, int i) {
        displayManagerService.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        ArrayList arrayList = new ArrayList();
        synchronized (displayManagerService.mRequestDisplayStateLock) {
            synchronized (displayManagerService.mSyncRoot) {
                try {
                    int i2 = (PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && displayManagerService.mDualScreenPolicy == 1) ? 1 : 0;
                    int size = displayManagerService.mDisplayStateOverrideLocks.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            i3 = -1;
                            break;
                        } else if (((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLock == iBinder) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 < 0) {
                        if (i == 0) {
                            Slog.d("DisplayManagerService", "setDisplayStateOverrideInternal: sameRequest: unknown");
                            return;
                        }
                        DisplayStateOverrideLock displayStateOverrideLock = displayManagerService.new DisplayStateOverrideLock(iBinder, i, uptimeMillis, i2);
                        try {
                            iBinder.linkToDeath(displayStateOverrideLock, 0);
                            displayManagerService.mDisplayStateOverrideLocks.add(displayStateOverrideLock);
                        } catch (RemoteException unused) {
                            throw new IllegalArgumentException("DisplayStateOverrideLock is already dead.");
                        }
                    } else if (i == 0) {
                        displayManagerService.mDisplayStateOverrideLocks.remove(i3);
                    } else {
                        if (i == ((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLastRequestedState) {
                            Slog.d("DisplayManagerService", "setDisplayStateOverrideInternal: sameRequest: " + Display.stateToString(i));
                            return;
                        }
                        ((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLastRequestedState = i;
                    }
                    displayManagerService.mDisplayDeviceRepo.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda4(0, displayManagerService, arrayList));
                    arrayList.forEach(new DisplayManagerService$$ExternalSyntheticLambda5(0));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$msetDisplayStateOverrideWithDisplayIdInternal, reason: not valid java name */
    public static void m455$$Nest$msetDisplayStateOverrideWithDisplayIdInternal(DisplayManagerService displayManagerService, IBinder iBinder, int i, int i2) {
        DisplayDevice displayDevice;
        displayManagerService.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (displayManagerService.mRequestDisplayStateLock) {
            synchronized (displayManagerService.mSyncRoot) {
                int size = displayManagerService.mDisplayStateOverrideLocks.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        i3 = -1;
                        break;
                    } else if (((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLock == iBinder && ((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mDisplayId == i2) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i3 < 0) {
                    if (i == 0) {
                        Slog.d("DisplayManagerService", "setDisplayStateOverrideWithDisplayIdInternal: sameRequest: unknown");
                        return;
                    }
                    DisplayStateOverrideLock displayStateOverrideLock = displayManagerService.new DisplayStateOverrideLock(iBinder, i, uptimeMillis, i2);
                    try {
                        iBinder.linkToDeath(displayStateOverrideLock, 0);
                        displayManagerService.mDisplayStateOverrideLocks.add(displayStateOverrideLock);
                    } catch (RemoteException unused) {
                        throw new IllegalArgumentException("DisplayStateOverrideLock is already dead.");
                    }
                } else if (i == 0) {
                    displayManagerService.mDisplayStateOverrideLocks.remove(i3);
                } else {
                    if (i == ((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLastRequestedState) {
                        Slog.d("DisplayManagerService", "setDisplayStateOverrideWithDisplayIdInternal: sameRequest: " + Display.stateToString(i));
                        return;
                    }
                    ((DisplayStateOverrideLock) displayManagerService.mDisplayStateOverrideLocks.get(i3)).mLastRequestedState = i;
                }
                LogicalDisplay displayLocked = displayManagerService.mLogicalDisplayMapper.getDisplayLocked(i2, true);
                Runnable updateDisplayStateLocked = (displayLocked == null || (displayDevice = displayLocked.mPrimaryDisplayDevice) == null) ? null : displayManagerService.updateDisplayStateLocked(displayDevice);
                if (updateDisplayStateLocked != null) {
                    updateDisplayStateLocked.run();
                }
            }
        }
    }

    /* renamed from: -$$Nest$msetUserDisabledHdrTypesInternal, reason: not valid java name */
    public static void m456$$Nest$msetUserDisabledHdrTypesInternal(DisplayManagerService displayManagerService, int[] iArr) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                if (iArr == null) {
                    Slog.e("DisplayManagerService", "Null is not an expected argument to setUserDisabledHdrTypesInternal");
                    return;
                }
                int[] iArr2 = Display.HdrCapabilities.HDR_TYPES;
                for (int i : iArr) {
                    if (Arrays.binarySearch(iArr2, i) < 0) {
                        Slog.e("DisplayManagerService", "userDisabledHdrTypes contains unexpected types");
                        return;
                    }
                }
                Arrays.sort(iArr);
                if (Arrays.equals(displayManagerService.mUserDisabledHdrTypes, iArr)) {
                    return;
                }
                Settings.Global.putString(displayManagerService.mContext.getContentResolver(), "user_disabled_hdr_formats", iArr.length != 0 ? TextUtils.join(",", Arrays.stream(iArr).boxed().toArray()) : "");
                displayManagerService.mUserDisabledHdrTypes = iArr;
                if (!displayManagerService.mAreUserDisabledHdrTypesAllowed) {
                    displayManagerService.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda7(displayManagerService, iArr, 0), true);
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetVirtualDisplayStateInternal, reason: not valid java name */
    public static void m457$$Nest$msetVirtualDisplayStateInternal(DisplayManagerService displayManagerService, IBinder iBinder, boolean z) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                VirtualDisplayAdapter virtualDisplayAdapter = displayManagerService.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter == null) {
                    return;
                }
                VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = (VirtualDisplayAdapter.VirtualDisplayDevice) virtualDisplayAdapter.mVirtualDisplayDevices.get(iBinder);
                if (virtualDisplayDevice != null && virtualDisplayDevice.mIsDisplayOn != z) {
                    virtualDisplayDevice.mIsDisplayOn = z;
                    virtualDisplayDevice.mInfo = null;
                    VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(virtualDisplayDevice, 2);
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$msetVirtualDisplaySurfaceInternal, reason: not valid java name */
    public static void m458$$Nest$msetVirtualDisplaySurfaceInternal(DisplayManagerService displayManagerService, IBinder iBinder, Surface surface) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                VirtualDisplayAdapter virtualDisplayAdapter = displayManagerService.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter == null) {
                    return;
                }
                VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = (VirtualDisplayAdapter.VirtualDisplayDevice) virtualDisplayAdapter.mVirtualDisplayDevices.get(iBinder);
                if (virtualDisplayDevice != null) {
                    android.util.Slog.v("VirtualDisplayAdapter", "Update surface for VirtualDisplay " + virtualDisplayDevice.mName);
                    virtualDisplayDevice.setSurfaceLocked(surface);
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mstartWifiDisplayScanInternal, reason: not valid java name */
    public static void m459$$Nest$mstartWifiDisplayScanInternal(DisplayManagerService displayManagerService, int i, int i2, int i3) {
        synchronized (displayManagerService.mSyncRoot) {
            try {
                CallbackRecord callbackRecord = (CallbackRecord) displayManagerService.mCallbacks.get(i);
                if (callbackRecord == null) {
                    throw new IllegalStateException("The calling process has not registered an IDisplayManagerCallback.");
                }
                displayManagerService.startWifiDisplayScanLocked(callbackRecord, i2, i3);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DisplayManagerService(Context context) {
        this(context, new Injector());
    }

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
        this.mRequestDisplayStateLock = new Object();
        this.mDisplayBlanker = new AnonymousClass1();
        this.mDisplayStates = new SparseIntArray();
        this.mDisplayStateOverrides = new SparseIntArray();
        this.mDisplayBrightnesses = new SparseArray();
        this.mStableDisplaySize = new Point();
        this.mViewports = new ArrayList();
        PersistentDataStore persistentDataStore = new PersistentDataStore(new PersistentDataStore.Injector());
        this.mPersistentDataStore = persistentDataStore;
        this.mTempCallbacks = new ArrayList();
        this.mPendingCallbackSelfLocked = new SparseArray();
        this.mTempViewports = new ArrayList();
        this.mDisplayAccessUIDs = new SparseArray();
        this.mPresentationDisplays = new HashSet();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss.SSS");
        this.mBootCompleted = false;
        this.mIdleModeReceiver = new AnonymousClass2(this, 0);
        this.mResolutionRestoreReceiver = new AnonymousClass2(this, 1);
        this.mRotationChangeReceiver = new AnonymousClass2(this, 2);
        DisplayManagerService$$ExternalSyntheticLambda1 displayManagerService$$ExternalSyntheticLambda1 = new DisplayManagerService$$ExternalSyntheticLambda1(this);
        this.mDisplayStateOverrideLocks = new ArrayList();
        this.mDualScreenPolicy = -1;
        this.mDisplayBrightnessListeners = new ArrayList();
        this.mDisplayStateListeners = new ArrayList();
        this.mHbmBrightnessCallbacks = new SparseArray();
        this.mConnectedExternalDisplayId = -1;
        this.mBrightnessAnimRefreshRateToken = null;
        this.mIsMtkPtHintExist = false;
        this.mForceListenProcessId = -1;
        long uptimeMillis = SystemClock.uptimeMillis();
        FoldSettingProvider foldSettingProvider = new FoldSettingProvider(context, new SettingsWrapper(), new FoldLockSettingAvailabilityProvider(context.getResources()));
        this.mInjector = injector;
        this.mContext = context;
        injector.getClass();
        DisplayManagerFlags displayManagerFlags = new DisplayManagerFlags();
        this.mFlags = displayManagerFlags;
        DisplayManagerHandler displayManagerHandler = new DisplayManagerHandler(DisplayThread.get().getLooper());
        this.mHandler = displayManagerHandler;
        this.mUiHandler = UiThread.getHandler();
        DisplayDeviceRepository displayDeviceRepository = new DisplayDeviceRepository(syncRoot, persistentDataStore);
        this.mDisplayDeviceRepo = displayDeviceRepository;
        this.mLogicalDisplayMapper = new LogicalDisplayMapper(context, foldSettingProvider, new FoldGracePeriodProvider(), displayDeviceRepository, new AnonymousClass1(), syncRoot, displayManagerHandler, displayManagerFlags);
        this.mDisplayModeDirector = new DisplayModeDirector(context, displayManagerHandler, displayManagerFlags, displayManagerService$$ExternalSyntheticLambda1);
        this.mBrightnessSynchronizer = new BrightnessSynchronizer(context, displayManagerFlags.mBrightnessIntRangeUserPerceptionFlagState.isEnabled());
        Resources resources = context.getResources();
        this.mDefaultDisplayDefaultColorMode = context.getResources().getInteger(R.integer.config_displayWhiteBalanceColorTemperatureMax);
        this.mDefaultDisplayTopInset = SystemProperties.getInt("persist.sys.displayinset.top", -1);
        this.mDefaultHdrConversionMode = context.getResources().getBoolean(R.bool.config_enableHapticTextHandle) ? 1 : 2;
        TypedArray obtainTypedArray = resources.obtainTypedArray(17236254);
        int length = obtainTypedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = obtainTypedArray.getFloat(i, Float.NaN);
        }
        obtainTypedArray.recycle();
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(17236255);
        int length2 = obtainTypedArray2.length();
        float[] fArr2 = new float[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            fArr2[i2] = obtainTypedArray2.getFloat(i2, Float.NaN);
        }
        obtainTypedArray2.recycle();
        this.mMinimumBrightnessCurve = new Curve(fArr, fArr2);
        this.mMinimumBrightnessSpline = Spline.createSpline(fArr, fArr2);
        this.mCurrentUserId = 0;
        this.mWideColorSpace = SurfaceControl.getCompositionColorSpaces()[1];
        this.mOverlayProperties = SurfaceControl.getOverlaySupport();
        this.mScreenExtendedBrightnessRangeMaximum = Math.max(context.getResources().getInteger(R.integer.config_wait_for_satellite_enabling_response_timeout_millis), context.getResources().getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit)) / 255.0f;
        displayDeviceRepository.mHDMIWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(805306374, "DisplayDeviceRepository.mHDMIWakeLock");
        this.mSystemReady = false;
        this.mConfigParameterProvider = new DeviceConfigParameterProvider(DeviceConfigInterface.REAL);
        this.mExtraDisplayLoggingPackageName = (String) DisplayProperties.debug_vri_package().orElse(null);
        this.mExtraDisplayEventLogging = !TextUtils.isEmpty(r1);
        ExternalDisplayStatsService externalDisplayStatsService = new ExternalDisplayStatsService(new ExternalDisplayStatsService.Injector(context, displayManagerHandler));
        this.mExternalDisplayStatsService = externalDisplayStatsService;
        this.mDisplayNotificationManager = new DisplayNotificationManager(displayManagerFlags, context, new DisplayNotificationManager.AnonymousClass1(context, displayManagerFlags, externalDisplayStatsService));
        this.mExternalDisplayPolicy = new ExternalDisplayPolicy(new AnonymousClass1());
        Slog.d("DisplayManagerService", "Create DisplayManagerService took to complete: " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
        if (PowerManagerUtil.SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING) {
            SemDvfsManager createInstance = SemDvfsManager.createInstance(context, "PT_CONTROL");
            this.mMtKPowerThrottling = createInstance;
            if (createInstance != null) {
                this.mIsMtkPtHintExist = createInstance.checkHintSupported(FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED);
            }
            if (this.mIsMtkPtHintExist) {
                createInstance.setHint(FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED);
            }
        }
    }

    public static Optional getViewportType(DisplayDeviceInfo displayDeviceInfo, boolean z) {
        if (z) {
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
        if (DEBUG) {
            Slog.w("DisplayManagerService", "Display " + displayDeviceInfo + " does not support input device matching.");
        }
        return Optional.empty();
    }

    public final DisplayPowerController addDisplayPowerControllerLocked(final LogicalDisplay logicalDisplay) {
        if (this.mPowerHandler == null) {
            return null;
        }
        if (this.mBrightnessTracker == null && logicalDisplay.mDisplayId == 0) {
            this.mBrightnessTracker = new BrightnessTracker(this.mContext);
        }
        BrightnessSetting brightnessSetting = new BrightnessSetting(getUserManager().getUserSerialNumber(this.mContext.getUserId()), this.mPersistentDataStore, logicalDisplay, this.mSyncRoot);
        HighBrightnessModeMetadata highBrightnessModeMetadataLocked = this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay);
        Context context = this.mContext;
        DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks = this.mDisplayPowerCallbacks;
        Handler handler = this.mPowerHandler;
        SensorManager sensorManager = this.mSensorManager;
        BrightnessTracker brightnessTracker = this.mBrightnessTracker;
        final int i = 0;
        ?? r10 = new Runnable(this) { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda23
            public final /* synthetic */ DisplayManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        DisplayManagerService displayManagerService = this.f$0;
                        LogicalDisplay logicalDisplay2 = logicalDisplay;
                        synchronized (displayManagerService.mSyncRoot) {
                            displayManagerService.sendDisplayEventIfEnabledLocked(logicalDisplay2, 4);
                        }
                        return;
                    default:
                        DisplayManagerService displayManagerService2 = this.f$0;
                        LogicalDisplay logicalDisplay3 = logicalDisplay;
                        synchronized (displayManagerService2.mSyncRoot) {
                            int i2 = logicalDisplay3.mDisplayId;
                            int i3 = i2 != 0 ? i2 != 1 ? -1 : 0 : 1;
                            if (i3 != -1) {
                                ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService2.mDisplayPowerControllers.get(i3))).sendUpdatePowerState();
                            }
                        }
                        return;
                }
            }
        };
        boolean z = this.mBootCompleted;
        final int i2 = 1;
        ?? r13 = new Runnable(this) { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda23
            public final /* synthetic */ DisplayManagerService f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        DisplayManagerService displayManagerService = this.f$0;
                        LogicalDisplay logicalDisplay2 = logicalDisplay;
                        synchronized (displayManagerService.mSyncRoot) {
                            displayManagerService.sendDisplayEventIfEnabledLocked(logicalDisplay2, 4);
                        }
                        return;
                    default:
                        DisplayManagerService displayManagerService2 = this.f$0;
                        LogicalDisplay logicalDisplay3 = logicalDisplay;
                        synchronized (displayManagerService2.mSyncRoot) {
                            int i22 = logicalDisplay3.mDisplayId;
                            int i3 = i22 != 0 ? i22 != 1 ? -1 : 0 : 1;
                            if (i3 != -1) {
                                ((DisplayPowerController) ((DisplayPowerControllerInterface) displayManagerService2.mDisplayPowerControllers.get(i3))).sendUpdatePowerState();
                            }
                        }
                        return;
                }
            }
        };
        DisplayManagerService$$ExternalSyntheticLambda8 displayManagerService$$ExternalSyntheticLambda8 = new DisplayManagerService$$ExternalSyntheticLambda8(this, i2);
        DisplayPowerController displayPowerController = new DisplayPowerController(context, displayPowerCallbacks, handler, sensorManager, this.mDisplayBlanker, logicalDisplay, brightnessTracker, brightnessSetting, r10, highBrightnessModeMetadataLocked, z, this.mFlags, r13, displayManagerService$$ExternalSyntheticLambda8);
        this.mDisplayPowerControllers.append(logicalDisplay.mDisplayId, displayPowerController);
        return displayPowerController;
    }

    public final void addPresentationDisplay(PresentationDisplay presentationDisplay) {
        if (this.mPresentationDisplays.contains(presentationDisplay)) {
            Slog.w("DisplayManagerService", "Presentation has been added already");
            return;
        }
        if (this.mPresentationDisplays.add(presentationDisplay)) {
            if (this.mPresentationDisplays.size() == 1) {
                this.mHandler.post(new DisplayManagerService$$ExternalSyntheticLambda15(this, 3));
                return;
            }
            return;
        }
        Slog.w("DisplayManagerService", "Failed to add presentation(" + presentationDisplay.displayId + ", " + presentationDisplay.packageName + ")");
    }

    public final void applyDisplayChangedLocked(LogicalDisplay logicalDisplay) {
        int i = logicalDisplay.mDisplayId;
        scheduleTraversalLocked(false);
        this.mPersistentDataStore.saveIfNeeded();
        DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i);
        if (displayPowerControllerInterface != null) {
            int i2 = logicalDisplay.mLeadDisplayId;
            boolean z = PowerManagerUtil.SEC_FEATURE_DUAL_DISPLAY;
            int i3 = (z && !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && i == 1 && this.mDualScreenPolicy == -1) ? 0 : i2;
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, i3);
            HighBrightnessModeMetadata highBrightnessModeMetadataLocked = this.mHighBrightnessModeMetadataMapper.getHighBrightnessModeMetadataLocked(logicalDisplay);
            DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
            if (highBrightnessModeMetadataLocked != null || (z && displayInfoLocked.type == 1)) {
                ((DisplayPowerController) displayPowerControllerInterface).onDisplayChanged(highBrightnessModeMetadataLocked, i3);
            }
        }
    }

    public final boolean checkCallingPermission(String str, String str2) {
        if (this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Permission Denial: ", str2, " from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        m.append(" requires ");
        m.append(str);
        Slog.w("DisplayManagerService", m.toString());
        return false;
    }

    public final float clampBrightness(float f, int i) {
        if (i == 1) {
            return -1.0f;
        }
        if (f != -1.0f && f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return Float.NaN;
        }
        float f2 = this.mScreenExtendedBrightnessRangeMaximum;
        return f > f2 ? f2 : f;
    }

    public final void clearUserDisabledHdrTypesLocked() {
        synchronized (this.mSyncRoot) {
            this.mUserDisabledHdrTypes = new int[0];
            Settings.Global.putString(this.mContext.getContentResolver(), "user_disabled_hdr_formats", "");
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void configureDisplayLocked(android.view.SurfaceControl.Transaction r27, com.android.server.display.DisplayDevice r28) {
        /*
            Method dump skipped, instructions count: 1101
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.configureDisplayLocked(android.view.SurfaceControl$Transaction, com.android.server.display.DisplayDevice):void");
    }

    public final void configurePreferredDisplayModeLocked(LogicalDisplay logicalDisplay) {
        PersistentDataStore.DisplayState displayState;
        PersistentDataStore.DisplayState displayState2;
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.getClass();
        Point point = null;
        if (displayDevice != null && displayDevice.hasStableUniqueId() && (displayState2 = persistentDataStore.getDisplayState(displayDevice.mUniqueId, false)) != null) {
            point = new Point(displayState2.mWidth, displayState2.mHeight);
        }
        float f = Float.NaN;
        if (displayDevice != null && displayDevice.hasStableUniqueId() && (displayState = persistentDataStore.getDisplayState(displayDevice.mUniqueId, false)) != null) {
            f = displayState.mRefreshRate;
        }
        if (point == null && Float.isNaN(f)) {
            return;
        }
        Display.Mode.Builder builder = new Display.Mode.Builder();
        if (point != null) {
            builder.setResolution(point.x, point.y);
        }
        if (!Float.isNaN(f)) {
            builder.setRefreshRate(f);
        }
        displayDevice.setUserPreferredDisplayModeLocked(builder.build());
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int createVirtualDisplayLocked(android.hardware.display.IVirtualDisplayCallback r20, android.media.projection.IMediaProjection r21, int r22, java.lang.String r23, java.lang.String r24, android.companion.virtual.IVirtualDevice r25, android.view.Surface r26, int r27, android.hardware.display.VirtualDisplayConfig r28) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.DisplayManagerService.createVirtualDisplayLocked(android.hardware.display.IVirtualDisplayCallback, android.media.projection.IMediaProjection, int, java.lang.String, java.lang.String, android.companion.virtual.IVirtualDevice, android.view.Surface, int, android.hardware.display.VirtualDisplayConfig):int");
    }

    public final void enableConnectedDisplay(int i, boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked == null) {
                    Slog.w("DisplayManagerService", "enableConnectedDisplay: Can not find displayId=" + i);
                } else if (ExternalDisplayPolicy.isExternalDisplayLocked(displayLocked)) {
                    this.mExternalDisplayPolicy.setExternalDisplayEnabledLocked(displayLocked, z);
                } else {
                    this.mLogicalDisplayMapper.setDisplayEnabledLocked(displayLocked, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean extraLogging(String str) {
        return this.mExtraDisplayEventLogging && this.mExtraDisplayLoggingPackageName.equals(str);
    }

    public final String getAmbientBrightnessInfo(float f) {
        String ambientBrightnessInfo;
        synchronized (this.mSyncRoot) {
            ambientBrightnessInfo = ((DisplayPowerController) ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0))).getAmbientBrightnessInfo(f);
        }
        return ambientBrightnessInfo;
    }

    public final BrightnessConfiguration getBrightnessConfigForDisplayWithPdsFallbackLocked(int i, String str) {
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.loadIfNeeded();
        PersistentDataStore.DisplayState displayState = (PersistentDataStore.DisplayState) persistentDataStore.mDisplayStates.get(str);
        BrightnessConfiguration brightnessConfiguration = displayState != null ? (BrightnessConfiguration) displayState.mDisplayBrightnessConfigurations.mConfigurations.get(i) : null;
        if (brightnessConfiguration != null) {
            return brightnessConfiguration;
        }
        persistentDataStore.loadIfNeeded();
        return (BrightnessConfiguration) persistentDataStore.mGlobalBrightnessConfigurations.mConfigurations.get(i);
    }

    public final DisplayDevice getDeviceForDisplayLocked(int i) {
        LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
        if (displayLocked == null) {
            return null;
        }
        return displayLocked.mPrimaryDisplayDevice;
    }

    public DisplayDeviceInfo getDisplayDeviceInfoInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked == null) {
                    return null;
                }
                return displayLocked.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DisplayDeviceRepository getDisplayDeviceRepository() {
        return this.mDisplayDeviceRepo;
    }

    public Handler getDisplayHandler() {
        return this.mHandler;
    }

    public final DisplayInfo getDisplayInfoForFrameRateOverride(DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr, DisplayInfo displayInfo, int i) {
        int i2;
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            RefreshRateModeManager refreshRateModeManager = this.mDisplayModeDirector.mRefreshRateModeManager;
            synchronized (refreshRateModeManager.mLock) {
                i2 = refreshRateModeManager.getController().mRefreshRateMode.get();
            }
            displayInfo.refreshRateMode = i2;
        }
        float f = displayInfo.renderFrameRate;
        int length = frameRateOverrideArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            DisplayEventReceiver.FrameRateOverride frameRateOverride = frameRateOverrideArr[i3];
            if (frameRateOverride.uid == i) {
                f = frameRateOverride.frameRateHz;
                break;
            }
            i3++;
        }
        if (f == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return displayInfo;
        }
        boolean z = i < 10000 || CompatChanges.isChangeEnabled(170503758L, i);
        Display.Mode mode = displayInfo.getMode();
        float vsyncRate = mode.getVsyncRate();
        float f2 = vsyncRate / f;
        float round = Math.round(f2);
        if (Math.abs(f2 - round) > 9.0E-4f) {
            return displayInfo;
        }
        float f3 = vsyncRate / round;
        DisplayInfo displayInfo2 = new DisplayInfo();
        displayInfo2.copyFrom(displayInfo);
        for (Display.Mode mode2 : displayInfo.supportedModes) {
            if (mode2.equalsExceptRefreshRate(mode) && mode2.getRefreshRate() >= f3 - 9.0E-4f && mode2.getRefreshRate() <= f3 + 9.0E-4f) {
                if (DEBUG) {
                    Slog.d("DisplayManagerService", "found matching modeId " + mode2.getModeId());
                }
                displayInfo2.refreshRateOverride = mode2.getRefreshRate();
                if (!z) {
                    displayInfo2.modeId = mode2.getModeId();
                }
                return displayInfo2;
            }
        }
        displayInfo2.refreshRateOverride = f3;
        if (!z) {
            Display.Mode[] modeArr = displayInfo.supportedModes;
            Display.Mode[] modeArr2 = (Display.Mode[]) Arrays.copyOf(modeArr, modeArr.length + 1);
            displayInfo2.supportedModes = modeArr2;
            modeArr2[modeArr2.length - 1] = new Display.Mode(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, mode.getPhysicalWidth(), mode.getPhysicalHeight(), displayInfo2.refreshRateOverride, mode.getVsyncRate(), new float[0], mode.getSupportedHdrTypes());
            Display.Mode[] modeArr3 = displayInfo2.supportedModes;
            displayInfo2.modeId = modeArr3[modeArr3.length - 1].getModeId();
        }
        return displayInfo2;
    }

    public final DisplayInfo getDisplayInfoInternal(int i, int i2) {
        synchronized (this.mSyncRoot) {
            try {
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked != null) {
                    DisplayInfo displayInfoForFrameRateOverride = getDisplayInfoForFrameRateOverride(displayLocked.mFrameRateOverrides, displayLocked.getDisplayInfoLocked(), i2);
                    if (!displayInfoForFrameRateOverride.hasAccess(i2)) {
                        if (isUidPresentOnDisplayInternal(i2, i)) {
                        }
                    }
                    return displayInfoForFrameRateOverride;
                }
                return null;
            } finally {
            }
        }
    }

    public final IBinder getDisplayToken(int i) {
        DisplayDevice displayDevice;
        synchronized (this.mSyncRoot) {
            try {
                if (i == 2) {
                    return getDisplayTokenForCurrentLayerStackLocked(i);
                }
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked == null || (displayDevice = displayLocked.mPrimaryDisplayDevice) == null) {
                    return null;
                }
                return displayDevice.mDisplayToken;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IBinder getDisplayTokenForCurrentLayerStackLocked(int i) {
        DisplayDevice displayDevice;
        LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
        if (displayLocked == null || (displayDevice = displayLocked.mPrimaryDisplayDevice) == null) {
            return null;
        }
        int i2 = displayLocked.getDisplayInfoLocked().layerStack;
        DisplayDeviceRepository displayDeviceRepository = this.mDisplayDeviceRepo;
        for (int size = ((ArrayList) displayDeviceRepository.mDisplayDevices).size() - 1; size >= 0; size--) {
            DisplayDevice displayDevice2 = (DisplayDevice) ((ArrayList) displayDeviceRepository.mDisplayDevices).get(size);
            if (displayDevice2 != displayDevice && displayDevice2.mCurrentLayerStack == i2) {
                return displayDevice2.mDisplayToken;
            }
        }
        return displayDevice.mDisplayToken;
    }

    public DisplayedContentSample getDisplayedContentSampleInternal(int i, long j, long j2) {
        IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return null;
        }
        return SurfaceControl.getDisplayedContentSample(displayToken, j, j2);
    }

    public DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributesInternal(int i) {
        IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return null;
        }
        return SurfaceControl.getDisplayedContentSamplingAttributes(displayToken);
    }

    public final HdrConversionMode getHdrConversionModeInternal() {
        this.mInjector.getClass();
        if (!DisplayControl.getHdrOutputConversionSupport()) {
            return HDR_CONVERSION_MODE_UNSUPPORTED;
        }
        synchronized (this.mSyncRoot) {
            try {
                HdrConversionMode hdrConversionMode = this.mOverrideHdrConversionMode;
                if (hdrConversionMode == null) {
                    hdrConversionMode = this.mHdrConversionMode;
                }
                if (hdrConversionMode == null && this.mDefaultHdrConversionMode == 1) {
                    return new HdrConversionMode(1);
                }
                if (hdrConversionMode != null && hdrConversionMode.getConversionMode() != 2) {
                    return hdrConversionMode;
                }
                return new HdrConversionMode(2, this.mSystemPreferredHdrOutputType);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final HdrConversionMode getHdrConversionModeSettingInternal() {
        this.mInjector.getClass();
        if (!DisplayControl.getHdrOutputConversionSupport()) {
            return HDR_CONVERSION_MODE_UNSUPPORTED;
        }
        synchronized (this.mSyncRoot) {
            try {
                HdrConversionMode hdrConversionMode = this.mHdrConversionMode;
                return hdrConversionMode != null ? hdrConversionMode : new HdrConversionMode(this.mDefaultHdrConversionMode);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public LogicalDisplayMapper getLogicalDisplayMapper() {
        return this.mLogicalDisplayMapper;
    }

    public Curve getMinimumBrightnessCurveInternal() {
        return this.mMinimumBrightnessCurve;
    }

    public final IMediaProjectionManager getProjectionService() {
        if (this.mProjectionService == null) {
            this.mInjector.getClass();
            this.mProjectionService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
        }
        return this.mProjectionService;
    }

    public final UserManager getUserManager() {
        return (UserManager) this.mContext.getSystemService(UserManager.class);
    }

    public final Display.Mode getUserPreferredDisplayModeInternal(int i) {
        synchronized (this.mSyncRoot) {
            try {
                if (i == -1) {
                    return this.mUserPreferredMode;
                }
                DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
                if (deviceForDisplayLocked == null) {
                    return null;
                }
                return deviceForDisplayLocked.getUserPreferredDisplayModeLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Surface getVirtualDisplaySurfaceInternal(IBinder iBinder) {
        synchronized (this.mSyncRoot) {
            try {
                VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter == null) {
                    return null;
                }
                return virtualDisplayAdapter.getVirtualDisplaySurfaceLocked(iBinder);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleLogicalDisplayChangedLocked(LogicalDisplay logicalDisplay) {
        updateViewportPowerStateLocked(logicalDisplay);
        int i = logicalDisplay.mDisplayId;
        if (i == 0) {
            recordTopInsetLocked(logicalDisplay);
            if (CoreRune.FW_VRR_RESOLUTION_POLICY) {
                RefreshRateController controller = this.mDisplayModeDirector.mRefreshRateModeManager.getController();
                DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
                controller.getClass();
                final int min = Math.min(displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight);
                Display.Mode mode = (Display.Mode) Arrays.stream(displayInfoLocked.supportedModes).filter(new Predicate() { // from class: com.android.server.display.mode.RefreshRateController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Display.Mode) obj).getPhysicalWidth() == min;
                    }
                }).findFirst().orElse(null);
                if (mode != null) {
                    int physicalWidth = mode.getPhysicalWidth();
                    int physicalHeight = mode.getPhysicalHeight();
                    Vote vote = RefreshRateController.mDm.getVote(i, 12);
                    if (vote == null || !(vote instanceof SizeVote) || ((SizeVote) vote).mWidth != physicalWidth) {
                        RefreshRateController.mVotesStorage.updateVote(i, 12, new SizeVote(physicalWidth, physicalHeight, physicalWidth, physicalHeight));
                    }
                }
            }
        }
        sendDisplayEventIfEnabledLocked(logicalDisplay, 2);
        applyDisplayChangedLocked(logicalDisplay);
    }

    public boolean isMinimalPostProcessingAllowed() {
        boolean z;
        synchronized (this.mSyncRoot) {
            z = this.mMinimalPostProcessingAllowed;
        }
        return z;
    }

    public final boolean isUidPresentOnDisplayInternal(int i, int i2) {
        boolean z;
        synchronized (this.mSyncRoot) {
            try {
                IntArray intArray = (IntArray) this.mDisplayAccessUIDs.get(i2);
                z = (intArray == null || intArray.indexOf(i) == -1) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public final void loadStableDisplayValuesLocked() {
        int i;
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.loadIfNeeded();
        PersistentDataStore.StableDeviceValues stableDeviceValues = persistentDataStore.mStableDeviceValues;
        stableDeviceValues.getClass();
        Point point = new Point(stableDeviceValues.mWidth, stableDeviceValues.mHeight);
        int i2 = point.x;
        if (i2 > 0 && (i = point.y) > 0) {
            this.mStableDisplaySize.set(i2, i);
            return;
        }
        Resources resources = this.mContext.getResources();
        int integer = resources.getInteger(R.integer.device_idle_light_idle_to_ms);
        int integer2 = resources.getInteger(R.integer.device_idle_light_idle_to_max_flex_ms);
        if (integer <= 0 || integer2 <= 0) {
            return;
        }
        setStableDisplaySizeLocked(integer, integer2);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 100) {
            synchronized (this.mSyncRoot) {
                try {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    this.mInjector.getClass();
                    long j = uptimeMillis + 10000;
                    while (true) {
                        if (this.mLogicalDisplayMapper.getDisplayLocked(0, true) != null && this.mVirtualDisplayAdapter != null) {
                        }
                        long uptimeMillis2 = j - SystemClock.uptimeMillis();
                        if (uptimeMillis2 <= 0) {
                            throw new RuntimeException("Timeout waiting for default display to be initialized. DefaultDisplay=" + this.mLogicalDisplayMapper.getDisplayLocked(0, true) + ", mVirtualDisplayAdapter=" + this.mVirtualDisplayAdapter);
                        }
                        if (DEBUG) {
                            Slog.d("DisplayManagerService", "waitForDefaultDisplay: waiting, timeout=" + uptimeMillis2);
                        }
                        try {
                            this.mSyncRoot.wait(uptimeMillis2);
                        } catch (InterruptedException unused) {
                        }
                    }
                } finally {
                }
            }
            return;
        }
        if (i == 1000) {
            synchronized (this.mSyncRoot) {
                try {
                    this.mBootCompleted = true;
                    for (int i2 = 0; i2 < this.mDisplayPowerControllers.size(); i2++) {
                        DisplayPowerController displayPowerController = (DisplayPowerController) ((DisplayPowerControllerInterface) this.mDisplayPowerControllers.valueAt(i2));
                        DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                        Message obtainMessage = displayControllerHandler.obtainMessage(13);
                        displayPowerController.mClock.getClass();
                        displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
                    }
                } finally {
                }
            }
            this.mDisplayModeDirector.onBootCompleted();
            LogicalDisplayMapper logicalDisplayMapper = this.mLogicalDisplayMapper;
            synchronized (logicalDisplayMapper.mSyncRoot) {
                try {
                    logicalDisplayMapper.mBootCompleted = true;
                    int i3 = logicalDisplayMapper.mDeviceStateToBeAppliedAfterBoot;
                    if (i3 != -1) {
                        logicalDisplayMapper.setDeviceStateLocked(i3);
                    }
                } finally {
                }
            }
            DisplayNotificationManager displayNotificationManager = this.mDisplayNotificationManager;
            DisplayNotificationManager.AnonymousClass1 anonymousClass1 = (DisplayNotificationManager.AnonymousClass1) displayNotificationManager.mInjector;
            NotificationManager notificationManager = (NotificationManager) anonymousClass1.val$context.getSystemService(NotificationManager.class);
            displayNotificationManager.mNotificationManager = notificationManager;
            if (notificationManager == null) {
                android.util.Slog.e("DisplayNotificationManager", "onBootCompleted: NotificationManager is null");
            } else {
                Context context = anonymousClass1.val$context;
                ConnectedDisplayUsbErrorsDetector connectedDisplayUsbErrorsDetector = new ConnectedDisplayUsbErrorsDetector(anonymousClass1.val$flags, context, new ConnectedDisplayUsbErrorsDetector$$ExternalSyntheticLambda0(context));
                if (connectedDisplayUsbErrorsDetector.mIsConnectedDisplayErrorHandlingEnabled) {
                    UsbManager usbManager = (UsbManager) ((ConnectedDisplayUsbErrorsDetector$$ExternalSyntheticLambda0) connectedDisplayUsbErrorsDetector.mInjector).f$0.getSystemService(UsbManager.class);
                    if (usbManager == null) {
                        android.util.Slog.e("ConnectedDisplayUsbErrorsDetector", "UsbManager is null");
                    } else {
                        connectedDisplayUsbErrorsDetector.mListener = displayNotificationManager;
                        try {
                            usbManager.registerDisplayPortAltModeInfoListener(connectedDisplayUsbErrorsDetector.mContext.getMainExecutor(), connectedDisplayUsbErrorsDetector);
                        } catch (IllegalStateException e) {
                            android.util.Slog.e("ConnectedDisplayUsbErrorsDetector", "Failed to register listener", e);
                        }
                    }
                }
            }
            ExternalDisplayPolicy externalDisplayPolicy = this.mExternalDisplayPolicy;
            synchronized (externalDisplayPolicy.mSyncRoot) {
                try {
                    externalDisplayPolicy.mIsBootCompleted = true;
                    Iterator it = ((HashSet) externalDisplayPolicy.mDisplayIdsWaitingForBootCompletion).iterator();
                    while (it.hasNext()) {
                        LogicalDisplay displayLocked = externalDisplayPolicy.mLogicalDisplayMapper.getDisplayLocked(((Integer) it.next()).intValue(), true);
                        if (displayLocked != null) {
                            externalDisplayPolicy.handleExternalDisplayConnectedLocked(displayLocked);
                        }
                    }
                    if (!((HashSet) externalDisplayPolicy.mDisplayIdsWaitingForBootCompletion).isEmpty()) {
                        externalDisplayPolicy.mLogicalDisplayMapper.updateLogicalDisplaysLocked$1();
                    }
                    ((HashSet) externalDisplayPolicy.mDisplayIdsWaitingForBootCompletion).clear();
                } finally {
                }
            }
            if (!externalDisplayPolicy.mFlags.mConnectedDisplayManagementFlagState.isEnabled()) {
                if (ExternalDisplayPolicy.DEBUG) {
                    android.util.Slog.d("ExternalDisplayPolicy", "External display management is not enabled on your device: cannot register thermal listener.");
                    return;
                }
                return;
            }
            if (!externalDisplayPolicy.mFlags.mConnectedDisplayErrorHandlingFlagState.isEnabled()) {
                if (ExternalDisplayPolicy.DEBUG) {
                    android.util.Slog.d("ExternalDisplayPolicy", "ConnectedDisplayErrorHandlingEnabled is not enabled on your device: cannot register thermal listener.");
                    return;
                }
                return;
            }
            ExternalDisplayPolicy.SkinThermalStatusObserver skinThermalStatusObserver = externalDisplayPolicy.new SkinThermalStatusObserver();
            externalDisplayPolicy.mInjector.getClass();
            IThermalService asInterface = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
            if (asInterface == null) {
                android.util.Slog.w("ExternalDisplayPolicy", "Could not observe thermal status. Service not available");
            } else {
                try {
                    asInterface.registerThermalEventListenerWithType(skinThermalStatusObserver, 3);
                    if (ExternalDisplayPolicy.DEBUG) {
                        android.util.Slog.d("ExternalDisplayPolicy", "registerThermalServiceListener complete.");
                        return;
                    }
                    return;
                } catch (RemoteException e2) {
                    android.util.Slog.e("ExternalDisplayPolicy", "Failed to register thermal status listener", e2);
                }
            }
            android.util.Slog.e("ExternalDisplayPolicy", "Failed to register thermal listener");
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.d("DisplayManagerService", "DisplayManagerService onStart");
        synchronized (this.mSyncRoot) {
            this.mPersistentDataStore.loadIfNeeded();
            loadStableDisplayValuesLocked();
        }
        this.mHandler.sendEmptyMessage(1);
        DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
        publishBinderService("display", new BinderService(), true, 1);
        publishLocalService(DisplayManagerInternal.class, new LocalService());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        final int userIdentifier = targetUser2.getUserIdentifier();
        final int userSerialNumber = getUserManager().getUserSerialNumber(userIdentifier);
        synchronized (this.mSyncRoot) {
            try {
                boolean z = true;
                final boolean z2 = this.mCurrentUserId != userIdentifier;
                if (z2) {
                    this.mCurrentUserId = userIdentifier;
                }
                this.mLogicalDisplayMapper.forEachLocked(new Consumer() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DisplayPowerControllerInterface displayPowerControllerInterface;
                        PersistentDataStore.DisplayState displayState;
                        DisplayManagerService displayManagerService = DisplayManagerService.this;
                        boolean z3 = z2;
                        int i = userSerialNumber;
                        int i2 = userIdentifier;
                        LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                        displayManagerService.getClass();
                        if (logicalDisplay.getDisplayInfoLocked().type == 1 && (displayPowerControllerInterface = (DisplayPowerControllerInterface) displayManagerService.mDisplayPowerControllers.get(logicalDisplay.mDisplayId)) != null) {
                            if (z3 && !PowerManagerUtil.USE_SEC_LONG_TERM_MODEL) {
                                ((DisplayPowerController) displayPowerControllerInterface).setBrightnessConfiguration(displayManagerService.getBrightnessConfigForDisplayWithPdsFallbackLocked(i, logicalDisplay.mPrimaryDisplayDevice.mUniqueId), true);
                            }
                            DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
                            float f = Float.NaN;
                            if (displayDevice != null) {
                                PersistentDataStore persistentDataStore = displayManagerService.mPersistentDataStore;
                                persistentDataStore.getClass();
                                if (displayDevice.hasStableUniqueId() && (displayState = persistentDataStore.getDisplayState(displayDevice.mUniqueId, false)) != null) {
                                    f = displayState.getBrightness(i);
                                }
                            }
                            if (Float.isNaN(f)) {
                                f = logicalDisplay.getDisplayInfoLocked().brightnessDefault;
                            }
                            DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
                            Float valueOf = Float.valueOf(f);
                            DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                            Message obtainMessage = displayControllerHandler.obtainMessage(12, i2, i, valueOf);
                            displayPowerController.mClock.getClass();
                            displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
                        }
                    }
                }, true);
                synchronized (this.mSyncRoot) {
                    if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "minimal_post_processing_allowed", 1, -2) == 0) {
                        z = false;
                    }
                    setMinimalPostProcessingAllowed(z);
                    scheduleTraversalLocked(false);
                }
                if (CoreRune.FW_VRR_POLICY) {
                    this.mDisplayModeDirector.onUserSwitching();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        this.mHandler.sendEmptyMessage(28);
    }

    public void overrideSensorManager(SensorManager sensorManager) {
        synchronized (this.mSyncRoot) {
            this.mSensorManager = sensorManager;
        }
    }

    public void performTraversalInternal(SurfaceControl.Transaction transaction, SparseArray sparseArray) {
        DisplayDeviceRepository displayDeviceRepository;
        PowerManager.WakeLock wakeLock;
        synchronized (this.mSyncRoot) {
            try {
                if (this.mPendingTraversal) {
                    this.mPendingTraversal = false;
                    performTraversalLocked(transaction, sparseArray);
                    if (this.mEnabledDexDisplay != null && (wakeLock = (displayDeviceRepository = this.mDisplayDeviceRepo).mHDMIWakeLock) != null && wakeLock.isHeld()) {
                        displayDeviceRepository.mHDMIWakeLock.release();
                    }
                    Iterator it = this.mDisplayTransactionListeners.iterator();
                    while (it.hasNext()) {
                        ((DisplayManagerInternal.DisplayTransactionListener) it.next()).onDisplayTransaction(transaction);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void performTraversalLocked(SurfaceControl.Transaction transaction, SparseArray sparseArray) {
        this.mViewports.clear();
        boolean isDexDisplayDeviceEnabledLocked = this.mDisplayDeviceRepo.isDexDisplayDeviceEnabledLocked();
        LogicalDisplayMapper logicalDisplayMapper = this.mLogicalDisplayMapper;
        LogicalDisplay displayLocked = isDexDisplayDeviceEnabledLocked ? logicalDisplayMapper.getDisplayLocked(2, true) : null;
        if (this.mEnabledDexDisplay != displayLocked) {
            Slog.d("DisplayManagerService", "updateEnabledDexDisplayLocked: prev=" + this.mEnabledDexDisplay + ", next=" + displayLocked);
            this.mEnabledDexDisplay = displayLocked;
        }
        logicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda2(this, sparseArray, transaction, 0), true);
        if (this.mInputManagerInternal != null) {
            this.mHandler.sendEmptyMessage(5);
        }
    }

    public final void recordTopInsetLocked(LogicalDisplay logicalDisplay) {
        int i;
        if (!this.mSystemReady || logicalDisplay == null || (i = LogicalDisplay.getMaskingInsets(logicalDisplay.mPrimaryDisplayDeviceInfo).top) == this.mDefaultDisplayTopInset) {
            return;
        }
        this.mDefaultDisplayTopInset = i;
        SystemProperties.set("persist.sys.displayinset.top", Integer.toString(i));
    }

    public final void registerWifiDisplayAdapterLocked() {
        WifiDisplayAdapter wifiDisplayAdapter = new WifiDisplayAdapter(this.mSyncRoot, this.mContext, this.mHandler, this.mDisplayDeviceRepo, this.mPersistentDataStore, this.mFlags);
        this.mWifiDisplayAdapter = wifiDisplayAdapter;
        this.mDisplayAdapters.add(wifiDisplayAdapter);
        wifiDisplayAdapter.updateRememberedDisplaysLocked();
        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass1(wifiDisplayAdapter, 0));
        this.mWifiDisplayAdapter.mDefaultDisplayDeviceInfo = getDisplayDeviceInfoInternal(0);
        this.mHandler.post(new DisplayManagerService$$ExternalSyntheticLambda15(this, 2));
    }

    public final void releaseDisplayAndEmitEvent(LogicalDisplay logicalDisplay, int i) {
        final IVirtualDevice iVirtualDevice;
        AutoBrightnessFallbackStrategy autoBrightnessFallbackStrategy;
        ScreenOffBrightnessSensorController screenOffBrightnessSensorController;
        final int i2 = logicalDisplay.mDisplayId;
        DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) this.mDisplayPowerControllers.removeReturnOld(i2);
        if (displayPowerControllerInterface != null) {
            updateDisplayPowerControllerLeaderLocked(displayPowerControllerInterface, -1);
            DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
            synchronized (displayPowerController.mLock) {
                for (int i3 = 0; i3 < displayPowerController.mDisplayBrightnessFollowers.size(); i3++) {
                    DisplayPowerController$$ExternalSyntheticLambda0 displayPowerController$$ExternalSyntheticLambda0 = new DisplayPowerController$$ExternalSyntheticLambda0(0, (DisplayPowerControllerInterface) displayPowerController.mDisplayBrightnessFollowers.valueAt(i3));
                    displayPowerController.mClock.getClass();
                    displayPowerController.mHandler.postAtTime(displayPowerController$$ExternalSyntheticLambda0, SystemClock.uptimeMillis());
                }
                displayPowerController.mDisplayBrightnessFollowers.clear();
                displayPowerController.mStopped = true;
                Message obtainMessage = displayPowerController.mHandler.obtainMessage(7);
                DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                displayPowerController.mClock.getClass();
                displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
                AutomaticBrightnessController automaticBrightnessController = displayPowerController.mAutomaticBrightnessController;
                if (automaticBrightnessController != null) {
                    automaticBrightnessController.setLightSensorEnabled(false);
                }
                DisplayBrightnessController displayBrightnessController = displayPowerController.mDisplayBrightnessController;
                BrightnessSetting brightnessSetting = displayBrightnessController.mBrightnessSetting;
                if (brightnessSetting != null) {
                    brightnessSetting.mListeners.remove(displayBrightnessController.mBrightnessSettingListener);
                }
                synchronized (displayBrightnessController.mLock) {
                    autoBrightnessFallbackStrategy = displayBrightnessController.mDisplayBrightnessStrategySelector.mAutoBrightnessFallbackStrategy;
                }
                if (autoBrightnessFallbackStrategy != null && (screenOffBrightnessSensorController = autoBrightnessFallbackStrategy.mScreenOffBrightnessSensorController) != null) {
                    screenOffBrightnessSensorController.setLightSensorEnabled(false);
                }
                displayPowerController.mContext.getContentResolver().unregisterContentObserver(displayPowerController.mSettingsObserver);
            }
        }
        this.mDisplayStates.delete(i2);
        if (logicalDisplay.getDisplayInfoLocked().type == 1) {
            this.mDisplayStateOverrides.delete(i2);
        }
        this.mDisplayBrightnesses.delete(i2);
        DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
        if (this.mSystemReady && this.mConnectedExternalDisplayId == i2) {
            this.mConnectedExternalDisplayId = -1;
            this.mUiHandler.post(new DisplayManagerService$$ExternalSyntheticLambda15(this, 5));
            this.mDisplayDeviceRepo.mNeedWakeLock = false;
        }
        if (this.mDisplayWindowPolicyControllers.contains(i2) && (iVirtualDevice = (IVirtualDevice) ((Pair) this.mDisplayWindowPolicyControllers.removeReturnOld(i2)).first) != null) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayManagerService displayManagerService = DisplayManagerService.this;
                    ((VirtualDeviceManagerInternal) displayManagerService.getLocalService(VirtualDeviceManagerInternal.class)).onVirtualDisplayRemoved(iVirtualDevice, i2);
                }
            });
        }
        sendDisplayEventLocked(logicalDisplay, i);
        scheduleTraversalLocked(false);
    }

    public final void releaseVirtualDisplayInternal(IBinder iBinder) {
        synchronized (this.mSyncRoot) {
            try {
                VirtualDisplayAdapter virtualDisplayAdapter = this.mVirtualDisplayAdapter;
                if (virtualDisplayAdapter == null) {
                    return;
                }
                VirtualDisplayAdapter.VirtualDisplayDevice releaseVirtualDisplayLocked = virtualDisplayAdapter.releaseVirtualDisplayLocked(iBinder);
                Slog.d("DisplayManagerService", "Virtual Display: Display Device released");
                if (releaseVirtualDisplayLocked != null) {
                    this.mDisplayDeviceRepo.onDisplayDeviceEvent(releaseVirtualDisplayLocked, 3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePresentationDisplay(PresentationDisplay presentationDisplay) {
        if (!this.mPresentationDisplays.contains(presentationDisplay)) {
            Slog.w("DisplayManagerService", "Presentation is not included in Set");
            return;
        }
        if (this.mPresentationDisplays.remove(presentationDisplay)) {
            if (this.mPresentationDisplays.isEmpty()) {
                this.mHandler.post(new DisplayManagerService$$ExternalSyntheticLambda15(this, 1));
                return;
            }
            return;
        }
        Slog.w("DisplayManagerService", "Failed to remove presentation(" + presentationDisplay.displayId + ", " + presentationDisplay.packageName + ")");
    }

    public final boolean requestDisplayPower(int i, boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
                if (displayLocked == null) {
                    Slog.w("DisplayManagerService", "requestDisplayPower: Cannot find a display with displayId=" + i);
                    return false;
                }
                BrightnessPair brightnessPair = (BrightnessPair) this.mDisplayBrightnesses.get(i);
                Runnable requestDisplayStateLocked = displayLocked.mPrimaryDisplayDevice.requestDisplayStateLocked(z ? 2 : 1, z ? brightnessPair.brightness : -1.0f, brightnessPair.sdrBrightness, displayLocked.mDisplayOffloadSession);
                if (requestDisplayStateLocked == null) {
                    Slog.w("DisplayManagerService", "requestDisplayPower: Cannot update the power state to ON=" + z + " for a display with displayId=" + i + ", runnable is null");
                    return false;
                }
                ((LocalDisplayAdapter.LocalDisplayDevice.AnonymousClass1) requestDisplayStateLocked).run();
                Slog.i("DisplayManagerService", "requestDisplayPower(displayId=" + i + ", on=" + z + ")");
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetBrightnessConfigurations() {
        int userId = this.mContext.getUserId();
        String packageName = this.mContext.getPackageName();
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.loadIfNeeded();
        PersistentDataStore.BrightnessConfigurations brightnessConfigurations = persistentDataStore.mGlobalBrightnessConfigurations;
        if (PersistentDataStore.BrightnessConfigurations.m463$$Nest$msetBrightnessConfigurationForUser(brightnessConfigurations, null, userId, packageName)) {
            persistentDataStore.mDirty = true;
        }
        brightnessConfigurations.saveHistory(null, null, null);
        this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda8(this, 4), true);
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

    public final void sendDisplayEventIfEnabledLocked(LogicalDisplay logicalDisplay, int i) {
        boolean z = logicalDisplay.mIsEnabled;
        if (Trace.isTagEnabled(131072L)) {
            Trace.instant(131072L, AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i, "sendDisplayEventLocked#event=", ",displayEnabled=", z));
        }
        if (z || logicalDisplay.getDisplayInfoLocked().type == 1) {
            sendDisplayEventLocked(logicalDisplay, i);
        } else if (this.mExtraDisplayEventLogging) {
            Slog.i("DisplayManagerService", "Not Sending Display Event; display is not enabled: " + logicalDisplay);
        }
    }

    public final void sendDisplayEventLocked(LogicalDisplay logicalDisplay, int i) {
        int i2 = logicalDisplay.mDisplayId;
        DisplayManagerHandler displayManagerHandler = this.mHandler;
        Message obtainMessage = displayManagerHandler.obtainMessage(3, i2, i);
        if (this.mExtraDisplayEventLogging) {
            Slog.i("DisplayManagerService", "Deliver Display Event on Handler: " + i);
        }
        displayManagerHandler.sendMessage(obtainMessage);
    }

    public final void sendDisplayHbmBrightnessEventLocked(int i, boolean z) {
        if (this.mHbmBrightnessCallbacks.size() == 0) {
            return;
        }
        Slog.d("DisplayManagerService", "sendDisplayHbmBrightnessEventLocked: displayId=" + i + " isHbm=" + z);
        for (int i2 = 0; i2 < this.mHbmBrightnessCallbacks.size(); i2++) {
            HbmBrightnessCallbackRecord hbmBrightnessCallbackRecord = (HbmBrightnessCallbackRecord) this.mHbmBrightnessCallbacks.valueAt(i2);
            hbmBrightnessCallbackRecord.getClass();
            try {
                hbmBrightnessCallbackRecord.mCallback.onChanged(i, z);
            } catch (RemoteException unused) {
                Slog.w("DisplayManagerService", "notifyHbmBrightnessEvent: Error calling hbm brightness callback pid=" + hbmBrightnessCallbackRecord.mPid + " uid=" + hbmBrightnessCallbackRecord.mUid);
                hbmBrightnessCallbackRecord.binderDied();
            }
        }
    }

    public final void setBrightnessConfigurationForDisplayInternal(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2) {
        validateBrightnessConfiguration(brightnessConfiguration);
        int userSerialNumber = getUserManager().getUserSerialNumber(i);
        synchronized (this.mSyncRoot) {
            try {
                DisplayDevice byUniqueIdLocked = this.mDisplayDeviceRepo.getByUniqueIdLocked(str);
                if (byUniqueIdLocked == null) {
                    this.mPersistentDataStore.saveIfNeeded();
                    return;
                }
                if (this.mLogicalDisplayMapper.getDisplayLocked(byUniqueIdLocked) != null && this.mLogicalDisplayMapper.getDisplayLocked(byUniqueIdLocked).getDisplayInfoLocked().type == 1 && brightnessConfiguration != null) {
                    FrameworkStatsLog.write(FrameworkStatsLog.BRIGHTNESS_CONFIGURATION_UPDATED, (float[]) brightnessConfiguration.getCurve().first, (float[]) brightnessConfiguration.getCurve().second, str);
                }
                PersistentDataStore persistentDataStore = this.mPersistentDataStore;
                persistentDataStore.getClass();
                if (byUniqueIdLocked.hasStableUniqueId()) {
                    PersistentDataStore.BrightnessConfigurations.m463$$Nest$msetBrightnessConfigurationForUser(persistentDataStore.getDisplayState(byUniqueIdLocked.mUniqueId, true).mDisplayBrightnessConfigurations, brightnessConfiguration, userSerialNumber, str2);
                    persistentDataStore.mDirty = true;
                }
                this.mPersistentDataStore.saveIfNeeded();
                if (i != this.mCurrentUserId) {
                    return;
                }
                LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(this.mDisplayDeviceRepo.getByUniqueIdLocked(str));
                DisplayPowerControllerInterface displayPowerControllerInterface = displayLocked != null ? (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(displayLocked.mDisplayId) : null;
                if (displayPowerControllerInterface != null) {
                    ((DisplayPowerController) displayPowerControllerInterface).setBrightnessConfiguration(brightnessConfiguration, true);
                }
            } catch (Throwable th) {
                this.mPersistentDataStore.saveIfNeeded();
                throw th;
            }
        }
    }

    public void setDisplayInfoOverrideFromWindowManagerInternal(int i, DisplayInfo displayInfo) {
        LogicalDisplay displayLocked;
        synchronized (this.mSyncRoot) {
            try {
                displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i, true);
            } catch (Throwable th) {
                throw th;
            }
            if (displayLocked != null) {
                DisplayInfoProxy displayInfoProxy = displayLocked.mInfo;
                if (displayInfo != null) {
                    DisplayInfo displayInfo2 = displayLocked.mOverrideDisplayInfo;
                    if (displayInfo2 == null) {
                        displayLocked.mOverrideDisplayInfo = new DisplayInfo(displayInfo);
                        displayInfoProxy.set(null);
                    } else if (!displayInfo2.equals(displayInfo)) {
                        displayLocked.mOverrideDisplayInfo.copyFrom(displayInfo);
                        displayInfoProxy.set(null);
                    }
                    handleLogicalDisplayChangedLocked(displayLocked);
                } else if (displayLocked.mOverrideDisplayInfo != null) {
                    displayLocked.mOverrideDisplayInfo = null;
                    displayInfoProxy.set(null);
                    handleLogicalDisplayChangedLocked(displayLocked);
                }
                throw th;
            }
        }
    }

    public boolean setDisplayedContentSamplingEnabledInternal(int i, boolean z, int i2, int i3) {
        IBinder displayToken = getDisplayToken(i);
        if (displayToken == null) {
            return false;
        }
        return SurfaceControl.setDisplayedContentSamplingEnabled(displayToken, z, i2, i3);
    }

    public final void setDockedAndIdleEnabled(boolean z) {
        synchronized (this.mSyncRoot) {
            DisplayPowerControllerInterface displayPowerControllerInterface = (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(0);
            if (displayPowerControllerInterface != null) {
                DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface;
                DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                Message obtainMessage = displayControllerHandler.obtainMessage();
                obtainMessage.what = 14;
                obtainMessage.arg1 = z ? 1 : 0;
                displayPowerController.mClock.getClass();
                displayControllerHandler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
            }
        }
    }

    public final void setHdrConversionModeInternal(HdrConversionMode hdrConversionMode) {
        int[] iArr;
        this.mInjector.getClass();
        if (DisplayControl.getHdrOutputConversionSupport()) {
            synchronized (this.mSyncRoot) {
                try {
                    if (hdrConversionMode.getConversionMode() == 2 && hdrConversionMode.getPreferredHdrOutputType() != -1) {
                        throw new IllegalArgumentException("preferredHdrOutputType must not be set if the conversion mode is HDR_CONVERSION_SYSTEM");
                    }
                    this.mHdrConversionMode = hdrConversionMode;
                    Settings.Global.putInt(this.mContext.getContentResolver(), "hdr_conversion_mode", hdrConversionMode.getConversionMode());
                    Settings.Global.putInt(this.mContext.getContentResolver(), "hdr_force_conversion_type", hdrConversionMode.getConversionMode() == 3 ? hdrConversionMode.getPreferredHdrOutputType() : -1);
                    int[] iArr2 = null;
                    if (hdrConversionMode.getConversionMode() == 2) {
                        IntArray intArray = new IntArray();
                        if (this.mSupportedHdrOutputType == null) {
                            this.mInjector.getClass();
                            this.mSupportedHdrOutputType = DisplayControl.getSupportedHdrOutputTypes();
                        }
                        for (int i : this.mSupportedHdrOutputType) {
                            int[] iArr3 = this.mUserDisabledHdrTypes;
                            int length = iArr3.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    intArray.add(i);
                                    break;
                                } else if (i == iArr3[i2]) {
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        iArr = intArray.toArray();
                    } else {
                        iArr = null;
                    }
                    int conversionMode = hdrConversionMode.getConversionMode();
                    int preferredHdrOutputType = hdrConversionMode.getPreferredHdrOutputType();
                    HdrConversionMode hdrConversionMode2 = this.mOverrideHdrConversionMode;
                    if (hdrConversionMode2 == null) {
                        if (conversionMode == 3 && preferredHdrOutputType == -1) {
                            conversionMode = 1;
                        }
                        iArr2 = iArr;
                    } else {
                        conversionMode = hdrConversionMode2.getConversionMode();
                        preferredHdrOutputType = this.mOverrideHdrConversionMode.getPreferredHdrOutputType();
                    }
                    this.mInjector.getClass();
                    this.mSystemPreferredHdrOutputType = DisplayControl.setHdrConversionMode(iArr2, conversionMode, preferredHdrOutputType);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void setMinimalPostProcessingAllowed(boolean z) {
        synchronized (this.mSyncRoot) {
            this.mMinimalPostProcessingAllowed = z;
        }
    }

    public final void setStableDisplaySizeLocked(int i, int i2) {
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        Point point = new Point(i, i2);
        this.mStableDisplaySize = point;
        try {
            persistentDataStore.loadIfNeeded();
            PersistentDataStore.StableDeviceValues stableDeviceValues = persistentDataStore.mStableDeviceValues;
            int i3 = stableDeviceValues.mWidth;
            int i4 = point.x;
            if (i3 != i4 || stableDeviceValues.mHeight != point.y) {
                stableDeviceValues.mWidth = i4;
                stableDeviceValues.mHeight = point.y;
                persistentDataStore.mDirty = true;
            }
        } finally {
            persistentDataStore.saveIfNeeded();
        }
    }

    public final void setUserPreferredDisplayModeInternal(int i, Display.Mode mode) {
        synchronized (this.mSyncRoot) {
            float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
            if (mode != null) {
                try {
                    if ((mode.getPhysicalWidth() <= 0 || mode.getPhysicalHeight() <= 0 || mode.getRefreshRate() <= FullScreenMagnificationGestureHandler.MAX_SCALE) && i == -1) {
                        throw new IllegalArgumentException("width, height and refresh rate of mode should be greater than 0 when setting the global user preferred display mode.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            int physicalHeight = mode == null ? -1 : mode.getPhysicalHeight();
            int physicalWidth = mode == null ? -1 : mode.getPhysicalWidth();
            if (mode != null) {
                f = mode.getRefreshRate();
            }
            storeModeInPersistentDataStoreLocked(i, physicalWidth, physicalHeight, f);
            if (i != -1) {
                setUserPreferredModeForDisplayLocked(i, mode);
            } else {
                this.mUserPreferredMode = mode;
                storeModeInGlobalSettingsLocked(physicalWidth, physicalHeight, f, mode);
            }
        }
    }

    public final void setUserPreferredModeForDisplayLocked(int i, Display.Mode mode) {
        DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
            return;
        }
        if (this.mFlags.mResolutionBackupRestore.isEnabled() && i == 0) {
            Point[] supportedResolutionsLocked = deviceForDisplayLocked.getSupportedResolutionsLocked();
            int i2 = 2;
            if (supportedResolutionsLocked.length == 2) {
                Point point = new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight());
                if (point.equals(supportedResolutionsLocked[0])) {
                    i2 = 1;
                } else if (!point.equals(supportedResolutionsLocked[1])) {
                    i2 = 0;
                }
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "screen_resolution_mode", i2, -2);
            }
        }
        deviceForDisplayLocked.setUserPreferredDisplayModeLocked(mode);
    }

    public final void setupLogicalDisplay(LogicalDisplay logicalDisplay) {
        PersistentDataStore.DisplayState displayState;
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        int i = logicalDisplay.mDisplayId;
        boolean z = i == 0;
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        persistentDataStore.getClass();
        int i2 = (displayDevice.hasStableUniqueId() && (displayState = persistentDataStore.getDisplayState(displayDevice.mUniqueId, false)) != null) ? displayState.mColorMode : -1;
        if (i2 == -1) {
            i2 = i == 0 ? this.mDefaultDisplayDefaultColorMode : 0;
        }
        logicalDisplay.mRequestedColorMode = i2;
        if (!this.mAreUserDisabledHdrTypesAllowed) {
            logicalDisplay.setUserDisabledHdrTypes(this.mUserDisabledHdrTypes);
        }
        if (z) {
            this.mDisplayModeDirector.defaultDisplayDeviceUpdated(logicalDisplay.mPrimaryDisplayDevice.mDisplayDeviceConfig);
            Point point = this.mStableDisplaySize;
            if (point.x <= 0 && point.y <= 0) {
                DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
                setStableDisplaySizeLocked(displayInfoLocked.getNaturalWidth(), displayInfoLocked.getNaturalHeight());
            }
            recordTopInsetLocked(logicalDisplay);
        }
        Display.Mode mode = this.mUserPreferredMode;
        if (mode != null) {
            displayDevice.setUserPreferredDisplayModeLocked(mode);
        } else {
            configurePreferredDisplayModeLocked(logicalDisplay);
        }
        DisplayPowerController addDisplayPowerControllerLocked = addDisplayPowerControllerLocked(logicalDisplay);
        if (addDisplayPowerControllerLocked != null) {
            updateDisplayPowerControllerLeaderLocked(addDisplayPowerControllerLocked, logicalDisplay.mLeadDisplayId);
            this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda14(this, i, 2), true);
        }
        this.mDisplayStates.append(i, 0);
        if (logicalDisplay.getDisplayInfoLocked().type == 1) {
            this.mDisplayStateOverrides.append(i, 0);
        }
        float f = logicalDisplay.getDisplayInfoLocked().brightnessDefault;
        SparseArray sparseArray = this.mDisplayBrightnesses;
        BrightnessPair brightnessPair = new BrightnessPair();
        brightnessPair.brightness = f;
        brightnessPair.sdrBrightness = f;
        sparseArray.append(i, brightnessPair);
        DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    }

    public final void startWifiDisplayScanLocked(CallbackRecord callbackRecord) {
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
        android.util.Slog.d("WifiDisplayAdapter", "requestStartScanLocked");
        wifiDisplayAdapter.mAvailableDisplays = WifiDisplay.EMPTY_ARRAY;
        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass1(wifiDisplayAdapter, 1));
        this.mVolumeController = this.mWifiDisplayAdapter.mVolumeController;
    }

    public final void startWifiDisplayScanLocked(CallbackRecord callbackRecord, final int i, final int i2) {
        final WifiDisplayAdapter wifiDisplayAdapter;
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
        ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "requestStartScanLocked : ", ", interval : ", "WifiDisplayAdapter");
        wifiDisplayAdapter.mAvailableDisplays = WifiDisplay.EMPTY_ARRAY;
        ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.8
            public final /* synthetic */ int val$interval;
            public final /* synthetic */ int val$scanChannel;

            public AnonymousClass8(final int i4, final int i22) {
                r2 = i4;
                r3 = i22;
            }

            @Override // java.lang.Runnable
            public final void run() {
                WifiDisplayController wifiDisplayController = WifiDisplayAdapter.this.mDisplayController;
                if (wifiDisplayController != null) {
                    int i4 = r2;
                    int i5 = r3;
                    if (wifiDisplayController.mScanRequested) {
                        return;
                    }
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(i4, i5, "requestStartScan setChannel = ", ", interval = ", "WifiDisplayController");
                    wifiDisplayController.mRequestedScanChannel = i4;
                    if (i5 <= 0) {
                        wifiDisplayController.mRequestedScanInterval = 1000;
                    } else {
                        wifiDisplayController.mRequestedScanInterval = i5 * 1000;
                    }
                    wifiDisplayController.mScanRequested = true;
                    wifiDisplayController.enableP2p();
                    wifiDisplayController.updateScanState();
                }
            }
        });
        this.mVolumeController = this.mWifiDisplayAdapter.mVolumeController;
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
                    wifiDisplayAdapter.getClass();
                    android.util.Slog.d("WifiDisplayAdapter", "requestStopScanLocked");
                    ((DisplayAdapter) wifiDisplayAdapter).mHandler.post(new WifiDisplayAdapter.AnonymousClass1(wifiDisplayAdapter, 2));
                    return;
                }
                return;
            }
            if (i < 0) {
                String str = "mWifiDisplayScanRequestCount became negative: " + this.mWifiDisplayScanRequestCount;
                int i2 = Slog.$r8$clinit;
                android.util.Slog.wtf("DisplayManagerService", str);
                this.mWifiDisplayScanRequestCount = 0;
            }
        }
    }

    public final void storeModeInGlobalSettingsLocked(int i, int i2, float f, Display.Mode mode) {
        Settings.Global.putFloat(this.mContext.getContentResolver(), "user_preferred_refresh_rate", f);
        Settings.Global.putInt(this.mContext.getContentResolver(), "user_preferred_resolution_height", i2);
        Settings.Global.putInt(this.mContext.getContentResolver(), "user_preferred_resolution_width", i);
        this.mDisplayDeviceRepo.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda0(0, mode));
    }

    public final void storeModeInPersistentDataStoreLocked(int i, int i2, int i3, float f) {
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        DisplayDevice deviceForDisplayLocked = getDeviceForDisplayLocked(i);
        if (deviceForDisplayLocked == null) {
            return;
        }
        try {
            String str = deviceForDisplayLocked.mUniqueId;
            persistentDataStore.getClass();
            if (deviceForDisplayLocked.hasStableUniqueId() && str != null) {
                PersistentDataStore.DisplayState displayState = persistentDataStore.getDisplayState(str, true);
                if (i2 != displayState.mWidth || i3 != displayState.mHeight) {
                    displayState.mWidth = i2;
                    displayState.mHeight = i3;
                    persistentDataStore.mDirty = true;
                }
            }
            if (deviceForDisplayLocked.hasStableUniqueId() && str != null) {
                PersistentDataStore.DisplayState displayState2 = persistentDataStore.getDisplayState(str, true);
                if (f != displayState2.mRefreshRate) {
                    displayState2.mRefreshRate = f;
                    persistentDataStore.mDirty = true;
                }
            }
        } finally {
            persistentDataStore.saveIfNeeded();
        }
    }

    public final void systemReady(boolean z) {
        SmallAreaDetectionController smallAreaDetectionController;
        synchronized (this.mSyncRoot) {
            this.mSafeMode = z;
            this.mSystemReady = true;
            this.mConfigParameterProvider.getClass();
            this.mIsHdrOutputControlEnabled = DeviceConfig.getBoolean("display_manager", "enable_hdr_output_control", true);
            this.mConfigParameterProvider.mDeviceConfig.addOnPropertiesChangedListener("display_manager", BackgroundThread.getExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda26
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    DisplayManagerService displayManagerService = DisplayManagerService.this;
                    displayManagerService.mConfigParameterProvider.getClass();
                    displayManagerService.mIsHdrOutputControlEnabled = DeviceConfig.getBoolean("display_manager", "enable_hdr_output_control", true);
                }
            });
            recordTopInsetLocked(this.mLogicalDisplayMapper.getDisplayLocked(0, true));
            setMinimalPostProcessingAllowed(Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "minimal_post_processing_allowed", 1, -2) != 0);
            updateUserDisabledHdrTypesFromSettingsLocked();
            updateUserPreferredDisplayModeSettingsLocked();
            if (this.mIsHdrOutputControlEnabled) {
                int i = Settings.Global.getInt(this.mContext.getContentResolver(), "hdr_conversion_mode", this.mDefaultHdrConversionMode);
                HdrConversionMode hdrConversionMode = new HdrConversionMode(i, i == 3 ? Settings.Global.getInt(this.mContext.getContentResolver(), "hdr_force_conversion_type", 1) : -1);
                this.mHdrConversionMode = hdrConversionMode;
                setHdrConversionModeInternal(hdrConversionMode);
            }
        }
        DisplayModeDirector displayModeDirector = this.mDisplayModeDirector;
        DesiredDisplayModeSpecsObserver desiredDisplayModeSpecsObserver = new DesiredDisplayModeSpecsObserver();
        synchronized (displayModeDirector.mLock) {
            displayModeDirector.mDesiredDisplayModeSpecsListener = desiredDisplayModeSpecsObserver;
        }
        this.mDisplayModeDirector.start(this.mSensorManager);
        this.mHandler.sendEmptyMessage(2);
        new SettingsObserver();
        this.mBrightnessSynchronizer.startSynchronizing();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        this.mContext.registerReceiver(this.mIdleModeReceiver, intentFilter);
        if (SemDynamicFeature.isSuitable()) {
            Feature property = SemDynamicFeature.getProperty(this.mContext, "DISPLAY_MANAGER", "ENABLE_DEBUG_LOG");
            if (property != null) {
                Slog.d("DynamicFeature_Display", "Debug value changed from " + DEBUG);
                DEBUG = property.getBoolean() | DEBUG;
                Slog.d("DynamicFeature_Display", "Debug value changed to " + DEBUG);
            }
            this.mContext.registerReceiver(new AnonymousClass6(), BatteryService$$ExternalSyntheticOutline0.m("com.sec.df.changed.DISPLAY_MANAGER.ENABLE_DEBUG_LOG"));
        }
        if (this.mFlags.mResolutionBackupRestore.isEnabled()) {
            this.mContext.registerReceiver(this.mResolutionRestoreReceiver, new IntentFilter("android.os.action.SETTING_RESTORED"));
        }
        if (this.mFlags.mSmallAreaDetectionFlagState.isEnabled()) {
            Context context = this.mContext;
            DeviceConfigInterface deviceConfigInterface = DeviceConfigInterface.REAL;
            smallAreaDetectionController = new SmallAreaDetectionController(context, deviceConfigInterface);
            smallAreaDetectionController.updateAllowlist(deviceConfigInterface.getProperty("display_manager", "small_area_detection_allowlist"));
        } else {
            smallAreaDetectionController = null;
        }
        this.mSmallAreaDetectionController = smallAreaDetectionController;
    }

    public final void updateDisplayPowerControllerLeaderLocked(DisplayPowerControllerInterface displayPowerControllerInterface, int i) {
        DisplayPowerControllerInterface displayPowerControllerInterface2;
        DisplayPowerControllerInterface displayPowerControllerInterface3;
        int i2 = ((DisplayPowerController) displayPowerControllerInterface).mLeadDisplayId;
        if (i2 == i) {
            return;
        }
        if (i2 != -1 && (displayPowerControllerInterface3 = (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i2)) != null) {
            DisplayPowerController displayPowerController = (DisplayPowerController) displayPowerControllerInterface3;
            synchronized (displayPowerController.mLock) {
                displayPowerController.mDisplayBrightnessFollowers.remove(((DisplayPowerController) displayPowerControllerInterface).mDisplayId);
                DisplayPowerController.DisplayControllerHandler displayControllerHandler = displayPowerController.mHandler;
                DisplayPowerController$$ExternalSyntheticLambda0 displayPowerController$$ExternalSyntheticLambda0 = new DisplayPowerController$$ExternalSyntheticLambda0(1, displayPowerControllerInterface);
                displayPowerController.mClock.getClass();
                displayControllerHandler.postAtTime(displayPowerController$$ExternalSyntheticLambda0, SystemClock.uptimeMillis());
            }
        }
        if (i == -1 || (displayPowerControllerInterface2 = (DisplayPowerControllerInterface) this.mDisplayPowerControllers.get(i)) == null) {
            return;
        }
        DisplayPowerController displayPowerController2 = (DisplayPowerController) displayPowerControllerInterface2;
        synchronized (displayPowerController2.mLock) {
            displayPowerController2.mDisplayBrightnessFollowers.append(((DisplayPowerController) displayPowerControllerInterface).mDisplayId, displayPowerControllerInterface);
            displayPowerController2.sendUpdatePowerStateLocked();
        }
    }

    public final Runnable updateDisplayStateLocked(DisplayDevice displayDevice) {
        LogicalDisplay displayLocked;
        ArrayList arrayList;
        int i;
        int i2;
        ArrayList arrayList2;
        DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        if ((displayDeviceInfoLocked.flags & 32) != 0 || (displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(displayDevice)) == null) {
            return null;
        }
        SparseIntArray sparseIntArray = this.mDisplayStates;
        int i3 = displayLocked.mDisplayId;
        int i4 = sparseIntArray.get(i3);
        if (displayDeviceInfoLocked.type == 1) {
            ArrayList arrayList3 = this.mDisplayStateOverrideLocks;
            if (this.mDisplayStateOverrides.get(i3) == 2) {
                i = 2;
            } else {
                if (arrayList3.size() != 0) {
                    loop0: for (DisplayStatePriority displayStatePriority : DisplayStatePriority.values()) {
                        i = displayStatePriority.getDisplayState();
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            DisplayStateOverrideLock displayStateOverrideLock = (DisplayStateOverrideLock) it.next();
                            if (i == displayStateOverrideLock.mLastRequestedState && displayStateOverrideLock.mDisplayId == i3) {
                                break loop0;
                            }
                        }
                    }
                }
                i = 0;
            }
            int i5 = this.mDualScreenPolicy;
            if ((i5 == 0 && (displayDeviceInfoLocked.flags & 8388608) != 0) || (i5 == 1 && (displayDeviceInfoLocked.flags & 8388608) == 0)) {
                i = 0;
            }
            ArrayList arrayList4 = this.mDisplayStateListeners;
            if (arrayList4 == null) {
                Slog.e("DisplayManagerService", "getCopyOfArrayList: error : null");
                arrayList2 = null;
            } else {
                arrayList2 = new ArrayList(arrayList4);
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
            i = 0;
        }
        if (PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY && (displayDeviceInfoLocked.flags & 8388608) != 0 && Display.isDozeState(i4)) {
            i4 = 2;
            i2 = 0;
        } else {
            i2 = i;
        }
        if (displayDeviceInfoLocked.type == 2 && Display.isDozeState(i4)) {
            i4 = 1;
        }
        if (i4 == 0) {
            return null;
        }
        BrightnessPair brightnessPair = (BrightnessPair) this.mDisplayBrightnesses.get(i3);
        return displayDevice.requestDisplayStateLocked(i4, brightnessPair.brightness, brightnessPair.sdrBrightness, displayLocked.mDisplayOffloadSession, i2, arrayList);
    }

    public final void updateUserDisabledHdrTypesFromSettingsLocked() {
        this.mAreUserDisabledHdrTypesAllowed = Settings.Global.getInt(this.mContext.getContentResolver(), "are_user_disabled_hdr_formats_allowed", 1) != 0;
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "user_disabled_hdr_formats");
        if (string == null) {
            clearUserDisabledHdrTypesLocked();
            return;
        }
        try {
            String[] split = TextUtils.split(string, ",");
            this.mUserDisabledHdrTypes = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                this.mUserDisabledHdrTypes[i] = Integer.parseInt(split[i]);
            }
            if (this.mAreUserDisabledHdrTypesAllowed) {
                return;
            }
            this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda8(this, 3), true);
        } catch (NumberFormatException e) {
            Slog.e("DisplayManagerService", "Failed to parse USER_DISABLED_HDR_FORMATS. Clearing the setting.", e);
            clearUserDisabledHdrTypesLocked();
        }
    }

    public final void updateUserPreferredDisplayModeSettingsLocked() {
        Display.Mode mode = new Display.Mode(Settings.Global.getInt(this.mContext.getContentResolver(), "user_preferred_resolution_width", -1), Settings.Global.getInt(this.mContext.getContentResolver(), "user_preferred_resolution_height", -1), Settings.Global.getFloat(this.mContext.getContentResolver(), "user_preferred_refresh_rate", FullScreenMagnificationGestureHandler.MAX_SCALE));
        Display.Mode mode2 = (mode.getPhysicalWidth() <= 0 || mode.getPhysicalHeight() <= 0 || mode.getRefreshRate() <= FullScreenMagnificationGestureHandler.MAX_SCALE) ? null : mode;
        this.mUserPreferredMode = mode2;
        if (mode2 != null) {
            this.mDisplayDeviceRepo.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda0(1, mode));
        } else {
            this.mLogicalDisplayMapper.forEachLocked(new DisplayManagerService$$ExternalSyntheticLambda8(this, 2), true);
        }
    }

    public final void updateViewportPowerStateLocked(LogicalDisplay logicalDisplay) {
        DisplayDeviceInfo displayDeviceInfoLocked = logicalDisplay.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked();
        Optional viewportType = getViewportType(displayDeviceInfoLocked, false);
        if (viewportType.isPresent()) {
            Iterator it = this.mViewports.iterator();
            while (it.hasNext()) {
                DisplayViewport displayViewport = (DisplayViewport) it.next();
                if (displayViewport.type == ((Integer) viewportType.get()).intValue() && displayDeviceInfoLocked.uniqueId.equals(displayViewport.uniqueId)) {
                    displayViewport.isActive = Display.isActiveState(displayDeviceInfoLocked.state);
                }
            }
            if (this.mInputManagerInternal != null) {
                this.mHandler.sendEmptyMessage(5);
            }
        }
    }

    public void validateBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
        if (brightnessConfiguration == null) {
            return;
        }
        Pair curve = brightnessConfiguration.getCurve();
        float[] fArr = (float[]) curve.first;
        float[] fArr2 = (float[]) curve.second;
        for (int i = 0; i < fArr.length; i++) {
            if (fArr2[i] < this.mMinimumBrightnessSpline.interpolate(fArr[i])) {
                Slog.e("DisplayManagerService", "brightness curve is too dark");
                return;
            }
        }
    }

    public final boolean validatePackageName(int i, String str) {
        String[] packagesForUid;
        if (i == 0) {
            return true;
        }
        if (str != null && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null) {
            for (String str2 : packagesForUid) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void windowManagerAndInputReady() {
        synchronized (this.mSyncRoot) {
            this.mWindowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
            this.mInputManagerInternal = (InputManagerService.LocalService) LocalServices.getService(InputManagerService.LocalService.class);
            this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            ((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).addOnUidImportanceListener(this.mUidImportanceListener, 400);
            this.mDeviceStateManager = (DeviceStateManagerInternal) LocalServices.getService(DeviceStateManagerInternal.class);
            ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mHandler), new DeviceStateListener());
            LogicalDisplayMapper logicalDisplayMapper = this.mLogicalDisplayMapper;
            logicalDisplayMapper.getClass();
            logicalDisplayMapper.mWindowManagerPolicy = (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class);
            scheduleTraversalLocked(false);
        }
    }
}
