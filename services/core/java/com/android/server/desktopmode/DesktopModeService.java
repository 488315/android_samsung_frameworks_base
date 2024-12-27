package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.app.role.RoleManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.view.Display;
import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.desktopmode.BlockerManager;
import com.android.server.desktopmode.BlockerManager.DesktopModeBlockerInfo;
import com.android.server.desktopmode.BootInitBlocker.AnonymousClass1;
import com.android.server.desktopmode.DockManager;
import com.android.server.desktopmode.ModeChanger;
import com.android.server.desktopmode.SemDesktopModeStateNotifier;
import com.android.server.desktopmode.SemDesktopModeStateNotifier.ListenerInfo;
import com.android.server.desktopmode.SettingsHelper;
import com.android.server.desktopmode.StateManager;
import com.android.server.desktopmode.UiManager;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import com.samsung.android.desktopmode.IDesktopMode;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import com.samsung.android.desktopmode.IDesktopModeLauncher;
import com.samsung.android.desktopmode.IDesktopModeListener;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.mcf.McfBleAdapter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DesktopModeService extends IDesktopMode.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ActivityManagerService mActivityManagerService;
    public ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public boolean mAllowPogoInitialDialog;
    public BleAdvertiserServiceManager mBleAdvertiserServiceManager;
    public BlockerManager mBlockerManager;
    public final Context mContext;
    public CoverStateManager mCoverStateManager;
    public int mCurrentUserId;
    public DexManager mDexManager;
    public DisplayPortStateManager mDisplayPortStateManager;
    public DockManager mDockManager;
    public DualModeChanger mDualModeChanger;
    public EmergencyModeBlocker mEmergencyModeBlocker;
    public boolean mEnteredViaWirelessDex;
    public final AnonymousClass3 mExternalDisplayModeListener;
    public final Handler mHandler;
    public HardwareManager mHwManager;
    public final Injector mInjector;
    public boolean mIsBootComplete;
    public boolean mIsBootInitBlockerRegistered;
    public boolean mIsDesktopModeEnablingOrEnabled;
    public boolean mIsDexSourceEntry;
    public boolean mIsNewDexEnabled;
    public IDesktopModeLauncher mLauncherInterface;
    public final Object mLock = new Object();
    public McfManager mMcfManager;
    public ModeChanger mModeChanger;
    public ModeChanger.ModeToModeChangeInfo mModeToModeChangeInfo;
    public MultiResolutionManager mMultiResolutionManager;
    public final AnonymousClass2 mNewDeXSettingObserver;
    public PackageStateManager mPackageStateManager;
    public final PhonePreconditionChecker mPreconditionChecker;
    public final ContentResolver mResolver;
    public final SemDesktopModeStateNotifier mSemDesktopModeStateNotifier;
    public SettingsHelper mSettingsHelper;
    public boolean mShowModeChangeScreen;
    public StandaloneModeChanger mStandaloneModeChanger;
    public final AnonymousClass1 mStateListener;
    public final IStateManager mStateManager;
    public StatusBarManager mStatusBarManager;
    public TouchpadManager mTouchpadManager;
    public UiManager mUiManager;
    public int mUserRequest;
    public ContentObserver mUserSetupCompleteObserver;
    public WelcomeActivityListener mWelcomeActivityListener;
    public final AnonymousClass3 mWelcomeCompletedSettingChangedListener;
    public WirelessDexManager mWirelessDexManager;
    public int mWirelessDexSetting;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.DesktopModeService$1, reason: invalid class name */
    public final class AnonymousClass1 extends StateManager.StateListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onBootCompleted() {
            switch (this.$r8$classId) {
                case 0:
                    HardwareManager hardwareManager = ((DesktopModeService) this.this$0).mHwManager;
                    hardwareManager.getClass();
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]HardwareManager", "initialize()");
                    }
                    Display[] displays = hardwareManager.mDisplayManager.getDisplays();
                    synchronized (hardwareManager.mLock) {
                        hardwareManager.updateDockStatusLocked();
                        hardwareManager.initializeExternalDisplayStatusLocked(displays);
                        hardwareManager.updateInputDeviceStatusLocked();
                        DesktopModeSettings.setSettings(hardwareManager.mResolver, "dock_usbpd_ids", hardwareManager.mDockState.mType);
                    }
                    CoverStateManager coverStateManager = ((DesktopModeService) this.this$0).mCoverStateManager;
                    synchronized (coverStateManager.mLock) {
                        coverStateManager.mScreenMirroringDisabled = DesktopModeSettings.getSettingsAsUser(coverStateManager.mContext.getContentResolver(), "mirroring_switch_disabled", false, DesktopModeSettings.sCurrentUserId);
                        coverStateManager.updateCoverSupportStateLocked();
                    }
                    StateManager.InternalState state = ((StateManager) ((DesktopModeService) this.this$0).mStateManager).getState();
                    if (DesktopModeFeature.SUPPORT_STANDALONE) {
                        DesktopModeService.m396$$Nest$mhandleHdmiAutoEnter((DesktopModeService) this.this$0, state.mIsExternalDisplayConnected);
                        DesktopModeService.m397$$Nest$mhandlePogoAutoEnter((DesktopModeService) this.this$0, state.mIsPogoKeyboardConnected);
                        ((DesktopModeService) this.this$0).mHandler.postDelayed(new DesktopModeService$$ExternalSyntheticLambda2(1, this), 5000L);
                        DesktopModeService.m401$$Nest$msetEnterRequestIfStandaloneLastBoot((DesktopModeService) this.this$0, state);
                    }
                    ((DesktopModeService) this.this$0).scheduleUpdateDesktopMode(true);
                    DesktopModeService.m400$$Nest$mrequestTileUpdate((DesktopModeService) this.this$0, state.mDesktopModeState);
                    return;
                default:
                    return;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onBootInitBlockerRegistered(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    ((DesktopModeService) this.this$0).mIsBootInitBlockerRegistered = z;
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDesktopModeStateChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    SemDesktopModeState semDesktopModeState = internalState.mDesktopModeState;
                    boolean z = DesktopModeFeature.SUPPORT_WIRELESS_DEX;
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    if (z && desktopModeService.mEnteredViaWirelessDex) {
                        int i = desktopModeService.mWirelessDexSetting;
                        int i2 = semDesktopModeState.enabled;
                        if (i != i2) {
                            desktopModeService.mWirelessDexSetting = i2;
                            DesktopModeSettings.setSettings(desktopModeService.mResolver, "wireless_dex", i2);
                        }
                        if (semDesktopModeState.compareTo(2, 0, 0)) {
                            desktopModeService.mEnteredViaWirelessDex = false;
                        }
                    }
                    DesktopModeService.m400$$Nest$mrequestTileUpdate(desktopModeService, semDesktopModeState);
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDockLowChargerPowerChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    if (internalState.mDockState.isDexPad()) {
                        int i = internalState.mDockLowChargerState;
                        DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                        if (i != 1) {
                            desktopModeService.dismissDialog(3);
                            break;
                        } else {
                            desktopModeService.mUiManager.showDialog(0, 3, null);
                            break;
                        }
                    }
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDockStateChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    if (!internalState.mDockState.isDexPad()) {
                        ((DesktopModeService) this.this$0).dismissDialog(3);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDualModeStopLoadingScreen(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    if (desktopModeService.mModeToModeChangeInfo == null) {
                        desktopModeService.setUserRequest(0);
                        break;
                    } else {
                        desktopModeService.setUserRequest(1);
                        break;
                    }
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onExternalDisplayConnectionChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    boolean settingsAsUser = DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "welcome_completed", false, DesktopModeSettings.sCurrentUserId);
                    if (!DesktopModeFeature.SUPPORT_STANDALONE) {
                        if (internalState.isHdmiConnected() && (DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "hdmi_auto_enter", false, DesktopModeSettings.sCurrentUserId) || !settingsAsUser)) {
                            desktopModeService.setUserRequest(1);
                        }
                        desktopModeService.scheduleUpdateDesktopMode(internalState.mIsExternalDisplayConnected);
                        break;
                    } else {
                        if (!internalState.isWirelessDexConnected()) {
                            DesktopModeService.m396$$Nest$mhandleHdmiAutoEnter(desktopModeService, internalState.isHdmiConnected());
                            desktopModeService.scheduleUpdateDesktopMode(internalState.isHdmiConnected());
                        } else if (desktopModeService.mCurrentUserId == 0 && desktopModeService.isSystemReady()) {
                            if (settingsAsUser) {
                                desktopModeService.scheduleUpdateDesktopMode(true);
                            } else {
                                desktopModeService.startWelcomeActivity();
                            }
                        }
                        if (!internalState.isHdmiConnected()) {
                            desktopModeService.mShowModeChangeScreen = false;
                            break;
                        }
                    }
                    break;
                default:
                    boolean z = internalState.mIsExternalDisplayConnected;
                    boolean z2 = internalState.mCoverSupportState == 2;
                    WelcomeActivityListener welcomeActivityListener = (WelcomeActivityListener) this.this$0;
                    welcomeActivityListener.showOrDismissOverlay(z, z2);
                    welcomeActivityListener.setDesktopModeState(10, internalState.mIsExternalDisplayConnected);
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onExternalDisplayUpdated(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    DisplayInfo displayInfo = internalState.mConnectedDisplay;
                    DisplayInfo displayInfo2 = internalState.mPreviousConnectedDisplay;
                    if (displayInfo != null && displayInfo2 != null && displayInfo.mType != displayInfo2.mType) {
                        final DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                        desktopModeService.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda4
                            public final /* synthetic */ boolean f$1 = false;
                            public final /* synthetic */ boolean f$2 = true;

                            @Override // java.lang.Runnable
                            public final void run() {
                                DesktopModeService desktopModeService2 = DesktopModeService.this;
                                desktopModeService2.updateDesktopMode(((StateManager) desktopModeService2.mStateManager).getState(), this.f$1, this.f$2);
                            }
                        });
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onForcedInternalScreenStateChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    int i = internalState.mForcedInternalScreenModeEnabled ? 1 : 2;
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    desktopModeService.setUserRequest(i);
                    desktopModeService.scheduleUpdateDesktopMode(internalState.mForcedInternalScreenModeEnabled);
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onLauncherPackageReplaced(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    StateManager.InternalState state = ((StateManager) desktopModeService.mStateManager).getState();
                    if (z) {
                        desktopModeService.mSettingsHelper.clearSettingsByLauncherDataCleared(state, desktopModeService.mCurrentUserId);
                    }
                    desktopModeService.startHome(state);
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onPackageStateChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    ((DesktopModeService) this.this$0).scheduleUpdateDesktopMode(internalState.isPackagesAvailable());
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onPogoKeyboardConnectionChanged(StateManager.InternalState internalState) {
            switch (this.$r8$classId) {
                case 0:
                    if (DesktopModeFeature.SUPPORT_STANDALONE) {
                        DesktopModeService.m397$$Nest$mhandlePogoAutoEnter((DesktopModeService) this.this$0, internalState.mIsPogoKeyboardConnected);
                        break;
                    }
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onScheduleUpdateDesktopMode(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    ((DesktopModeService) this.this$0).scheduleUpdateDesktopMode(z);
                    break;
            }
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onStopLoadingScreen(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = (DesktopModeService) this.this$0;
                    if (desktopModeService.mModeToModeChangeInfo == null) {
                        desktopModeService.setUserRequest(0);
                        break;
                    } else {
                        desktopModeService.setUserRequest(1);
                        break;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.DesktopModeService$2, reason: invalid class name */
    public final class AnonymousClass2 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DesktopModeService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass2(DesktopModeService desktopModeService, int i) {
            super(null);
            this.$r8$classId = i;
            this.this$0 = desktopModeService;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService.m400$$Nest$mrequestTileUpdate(this.this$0, ((StateManager) this.this$0.mStateManager).getState().mDesktopModeState);
                    break;
                default:
                    super.onChange(z);
                    this.this$0.initializeStates();
                    this.this$0.mResolver.unregisterContentObserver(this);
                    this.this$0.mUserSetupCompleteObserver = null;
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.DesktopModeService$6, reason: invalid class name */
    public final class AnonymousClass6 extends UiManager.InternalUiCallback {
        public final /* synthetic */ String val$firstConnectionKey;
        public final /* synthetic */ boolean val$startWelcomeActivity;

        public AnonymousClass6(boolean z, String str) {
            this.val$startWelcomeActivity = z;
            this.val$firstConnectionKey = str;
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onClickButtonPositive() {
            DesktopModeService desktopModeService = DesktopModeService.this;
            desktopModeService.setUserRequest(1);
            if (!this.val$startWelcomeActivity) {
                DesktopModeSettings.setSettings(desktopModeService.mResolver, "welcome_completed", true);
            }
            desktopModeService.scheduleUpdateDesktopMode(true);
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onDismiss() {
            DesktopModeSettings.setSettings(DesktopModeService.this.mResolver, this.val$firstConnectionKey, true);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final Injector mInjector;
        public final DesktopModeService mService;

        public Lifecycle(Context context) {
            super(context);
            Injector injector = new Injector(context);
            this.mInjector = injector;
            this.mService = (DesktopModeService) injector.get(DesktopModeService.class);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 500) {
                Injector injector = this.mInjector;
                ActivityManagerService activityManagerService = (ActivityManagerService) injector.get(ActivityManagerService.class);
                ActivityTaskManagerService activityTaskManagerService = (ActivityTaskManagerService) injector.get(ActivityTaskManagerService.class);
                ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) injector.get(ActivityTaskManagerInternal.class);
                BlockerManager blockerManager = (BlockerManager) injector.get(BlockerManager.class);
                EmergencyModeBlocker emergencyModeBlocker = (EmergencyModeBlocker) injector.get(EmergencyModeBlocker.class);
                UiManager uiManager = (UiManager) injector.get(UiManager.class);
                SettingsHelper settingsHelper = (SettingsHelper) injector.get(SettingsHelper.class);
                HardwareManager hardwareManager = (HardwareManager) injector.get(HardwareManager.class);
                TouchpadManager touchpadManager = (TouchpadManager) injector.get(TouchpadManager.class);
                CoverStateManager coverStateManager = (CoverStateManager) injector.get(CoverStateManager.class);
                MultiResolutionManager multiResolutionManager = (MultiResolutionManager) injector.get(MultiResolutionManager.class);
                DockManager dockManager = (DockManager) injector.get(DockManager.class);
                PackageStateManager packageStateManager = (PackageStateManager) injector.get(PackageStateManager.class);
                StatusBarManager statusBarManager = (StatusBarManager) injector.get(StatusBarManager.class);
                BootInitBlocker bootInitBlocker = DesktopModeFeature.SUPPORT_STANDALONE ? (BootInitBlocker) injector.get(BootInitBlocker.class) : null;
                DisplayPortStateManager displayPortStateManager = (DisplayPortStateManager) injector.get(DisplayPortStateManager.class);
                boolean z = DesktopModeFeature.SUPPORT_WIRELESS_DEX;
                this.mService.injectServices(activityManagerService, activityTaskManagerService, activityTaskManagerInternal, blockerManager, emergencyModeBlocker, uiManager, settingsHelper, hardwareManager, touchpadManager, coverStateManager, multiResolutionManager, dockManager, packageStateManager, statusBarManager, bootInitBlocker, displayPortStateManager, (z || DesktopModeFeature.SUPPORT_DEX_ON_PC) ? (WirelessDexManager) injector.get(WirelessDexManager.class) : null, z ? (BleAdvertiserServiceManager) injector.get(BleAdvertiserServiceManager.class) : null, (DualModeChanger) injector.get(DualModeChanger.class), (StandaloneModeChanger) injector.get(StandaloneModeChanger.class), (McfManager) injector.get(McfManager.class), (DexManager) injector.get(DexManager.class));
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("desktopmode", this.mService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            int i = DesktopModeService.$r8$clinit;
            this.mService.getClass();
            if (DesktopModeFeature.DEBUG) {
                DesktopModeService$$ExternalSyntheticOutline0.m(userIdentifier, "onStartUser(), userId=", "[DMS]DesktopModeService");
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            int i = DesktopModeService.$r8$clinit;
            this.mService.getClass();
            if (DesktopModeFeature.DEBUG) {
                DesktopModeService$$ExternalSyntheticOutline0.m(userIdentifier, "onCleanupUser(), userId=", "[DMS]DesktopModeService");
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            int i = DesktopModeService.$r8$clinit;
            this.mService.getClass();
            if (DesktopModeFeature.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userIdentifier, "onStopUser(), userId=", ", CurrentUser=");
                m.append(ActivityManager.getCurrentUser());
                Log.d("[DMS]DesktopModeService", m.toString());
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            int userIdentifier = targetUser2.getUserIdentifier();
            int i = DesktopModeService.$r8$clinit;
            DesktopModeService desktopModeService = this.mService;
            desktopModeService.getClass();
            if (DesktopModeFeature.DEBUG) {
                DesktopModeService$$ExternalSyntheticOutline0.m(userIdentifier, "onSwitchUser(), userId=", "[DMS]DesktopModeService");
            }
            desktopModeService.onUserChanged(userIdentifier);
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            int i = DesktopModeService.$r8$clinit;
            DesktopModeService desktopModeService = this.mService;
            desktopModeService.getClass();
            if (DesktopModeFeature.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(userIdentifier, "onUnlockUser(), userId=", ", CurrentUser=");
                m.append(ActivityManager.getCurrentUser());
                Log.d("[DMS]DesktopModeService", m.toString());
            }
            desktopModeService.mIsBootComplete = true;
            if (userIdentifier == ActivityManager.getCurrentUser()) {
                desktopModeService.onUserChanged(userIdentifier);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends DesktopModeManagerInternal {
        public LocalService() {
        }

        public final int getCurrentUiMode() {
            return DesktopModeService.this.mStandaloneModeChanger.mCurrentUiMode;
        }

        public final Bundle getDesktopModeKillPolicy() {
            return DesktopModeService.this.getDesktopModeKillPolicy();
        }

        public final SemDesktopModeState getDesktopModeState() {
            return DesktopModeService.this.getDesktopModeState();
        }

        public final int getDexHDMIAutoEnterState() {
            return DesktopModeSettings.getSettingsAsUser(DesktopModeService.this.mContext.getContentResolver(), "knox_hdmi_auto_enter_state", 9, DesktopModeSettings.sCurrentUserId);
        }

        public final int getModeToModeChangeType() {
            ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo;
            DesktopModeService desktopModeService = DesktopModeService.this;
            StateManager.InternalState state = ((StateManager) desktopModeService.mStateManager).getState();
            if (state.mDesktopModeState.getDisplayType() == 101) {
                ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo2 = desktopModeService.mStandaloneModeChanger.mModeToModeChangeInfo;
                if (modeToModeChangeInfo2 != null) {
                    return modeToModeChangeInfo2.mModeToMode;
                }
                return -1;
            }
            if (state.mDesktopModeState.getDisplayType() != 102 || (modeToModeChangeInfo = desktopModeService.mDualModeChanger.mModeToModeChangeInfo) == null) {
                return -1;
            }
            return modeToModeChangeInfo.mModeToMode;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getTouchpadSupportedFeatures() {
            /*
                r3 = this;
                com.android.server.desktopmode.DesktopModeService r3 = com.android.server.desktopmode.DesktopModeService.this
                com.android.server.desktopmode.IStateManager r0 = r3.mStateManager
                com.android.server.desktopmode.StateManager r0 = (com.android.server.desktopmode.StateManager) r0
                com.android.server.desktopmode.StateManager$InternalState r0 = r0.getState()
                com.android.server.desktopmode.ModeChanger r1 = r3.mModeChanger
                boolean r1 = r1 instanceof com.android.server.desktopmode.DualModeChanger
                if (r1 == 0) goto L30
                com.android.server.desktopmode.TouchpadManager r1 = r3.mTouchpadManager
                r1.getClass()
                boolean r1 = r0.isDexOnPcConnected()
                r2 = 1
                r1 = r1 ^ r2
                if (r1 == 0) goto L30
                com.android.server.desktopmode.TouchpadManager r3 = r3.mTouchpadManager
                r3.getClass()
                boolean r3 = com.samsung.android.desktopmode.DesktopModeFeature.SUPPORT_SPEN
                if (r3 == 0) goto L31
                com.android.server.desktopmode.HardwareManager$DockState r3 = r0.mDockState
                boolean r3 = r3.isDexStation()
                if (r3 != 0) goto L31
                r2 = 3
                goto L31
            L30:
                r2 = 0
            L31:
                boolean r3 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
                if (r3 == 0) goto L3d
                java.lang.String r3 = "getTouchpadSupportedFeatures(), supportedFeatures="
                java.lang.String r0 = "[DMS]DesktopModeService"
                com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0.m(r2, r3, r0)
            L3d:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.DesktopModeService.LocalService.getTouchpadSupportedFeatures():int");
        }

        public final boolean isConfigurationChangedFromDeX(Configuration configuration) {
            DesktopModeService desktopModeService = DesktopModeService.this;
            boolean z = false;
            if (configuration == null) {
                desktopModeService.getClass();
            } else if (desktopModeService.mStandaloneModeChanger != null) {
                SemDesktopModeState semDesktopModeState = ((StateManager) desktopModeService.mStateManager).getState().mDesktopModeState;
                int configurationState = desktopModeService.mStandaloneModeChanger.getConfigurationState(configuration);
                if (semDesktopModeState.state == 30 && ((configurationState == 1 && semDesktopModeState.enabled == 3) || (configurationState == 0 && semDesktopModeState.enabled == 1))) {
                    z = true;
                }
                Log.i("[DMS]DesktopModeService", "isConfigurationChangedFromDeX()=" + z + ", config=" + configuration + ", desktopModeState=" + semDesktopModeState);
            }
            return z;
        }

        public final boolean isDesktopModeAvailableEx(boolean z, boolean z2) {
            boolean z3 = false;
            PhonePreconditionChecker phonePreconditionChecker = DesktopModeService.this.mPreconditionChecker;
            switch (phonePreconditionChecker.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = phonePreconditionChecker.this$0;
                    StateManager.InternalState state = ((StateManager) desktopModeService.mStateManager).getState();
                    if ((!z || DesktopModeService.isExternalDisplayConnectedOrForced(state)) && ((!z2 || state.mDockState.mDesktopModeSupported || state.isDexOnPcOrWirelessDexConnected()) && DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "hdmi_auto_enter", false, DesktopModeSettings.sCurrentUserId))) {
                        z3 = true;
                    }
                    if (DesktopModeFeature.DEBUG) {
                        int i = DesktopModeService.$r8$clinit;
                        StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("isDesktopModeAvailableEx(checkExternalDisplay=", z, ", checkDesktopDock=", z2, ")=");
                        m.append(z3);
                        Log.d("[DMS]DesktopModeService", m.toString());
                    }
                    return z3;
                default:
                    boolean isAllowed = phonePreconditionChecker.isAllowed(((StateManager) phonePreconditionChecker.this$0.mStateManager).getState(), false);
                    if (!DesktopModeFeature.DEBUG) {
                        return isAllowed;
                    }
                    int i2 = DesktopModeService.$r8$clinit;
                    StringBuilder m2 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("isDesktopModeAvailable(checkExternalDisplay=", z, ", checkDesktopDock=", z2, ")=");
                    m2.append(isAllowed);
                    Log.d("[DMS]DesktopModeService", m2.toString());
                    return isAllowed;
            }
        }

        public final boolean isDesktopModeEnablingOrEnabled() {
            int i = ((StateManager) DesktopModeService.this.mStateManager).getState().mDesktopModeState.enabled;
            return i == 3 || i == 4;
        }

        public final boolean isDesktopModeForPreparing() {
            return DesktopModeService.isDesktopModeForPreparing(((StateManager) DesktopModeService.this.mStateManager).getState(), -1);
        }

        public final boolean isDesktopModeForPreparing(int i) {
            return DesktopModeService.isDesktopModeForPreparing(((StateManager) DesktopModeService.this.mStateManager).getState(), i);
        }

        public final boolean isExternalDisplayConnected() {
            return ((StateManager) DesktopModeService.this.mStateManager).getState().mIsExternalDisplayConnected;
        }

        public final boolean isForcedInternalScreenModeEnabled() {
            return ((StateManager) DesktopModeService.this.mStateManager).getState().mForcedInternalScreenModeEnabled;
        }

        public final boolean isLockTaskModeEnabledAndSecured() {
            ModeChanger modeChanger = DesktopModeService.this.mModeChanger;
            return modeChanger != null && modeChanger.mIsLockTaskModeEnabledAndSecured;
        }

        public final boolean isModeChangePending() {
            return ((StateManager) DesktopModeService.this.mStateManager).getState().mDesktopModeState.state == 30;
        }

        public final boolean isModeChangePending(int i) {
            SemDesktopModeState semDesktopModeState = ((StateManager) DesktopModeService.this.mStateManager).getState().mDesktopModeState;
            return (i == -1 || semDesktopModeState.getDisplayType() == i) && semDesktopModeState.state == 30;
        }

        public final void onConfigurationChanged(Configuration configuration) {
            DesktopModeService desktopModeService = DesktopModeService.this;
            if (configuration == null) {
                desktopModeService.getClass();
                return;
            }
            final StandaloneModeChanger standaloneModeChanger = desktopModeService.mStandaloneModeChanger;
            if (standaloneModeChanger != null) {
                final Configuration configuration2 = new Configuration(configuration);
                if (DesktopModeFeature.DEBUG) {
                    Log.v("[DMS]StandaloneModeChanger", "onConfigurationChanged(), config=" + configuration2);
                }
                SemDesktopModeState semDesktopModeState = ((StateManager) standaloneModeChanger.mStateManager).getState().mDesktopModeState;
                if (semDesktopModeState.state == 30 && semDesktopModeState.getDisplayType() == 101) {
                    standaloneModeChanger.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.StandaloneModeChanger$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            StandaloneModeChanger.this.handleOnConfigurationChanged(configuration2);
                        }
                    });
                }
            }
        }

        public final void onDesktopDisplayConfigured(final boolean z) {
            final DualModeChanger dualModeChanger = DesktopModeService.this.mDualModeChanger;
            dualModeChanger.getClass();
            if (DesktopModeFeature.DEBUG) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("onDesktopDisplayConfigured(), added=", ", mDesktopDisplayId=", z);
                m.append(dualModeChanger.mDesktopDisplayId);
                Log.v("[DMS]DualModeChanger", m.toString());
            }
            dualModeChanger.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.DualModeChanger$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DualModeChanger dualModeChanger2 = DualModeChanger.this;
                    boolean z2 = z;
                    StateManager stateManager = (StateManager) dualModeChanger2.mStateManager;
                    StateManager.InternalState state = stateManager.getState();
                    SemDesktopModeState semDesktopModeState = state.mDesktopModeState;
                    if (semDesktopModeState.state == 30 && semDesktopModeState.getDisplayType() == 102) {
                        if (!(z2 && semDesktopModeState.enabled == 3) && (z2 || semDesktopModeState.enabled != 1)) {
                            return;
                        }
                        boolean z3 = DesktopModeFeature.DEBUG;
                        if (z3) {
                            Log.d("[DMS]DualModeChanger", "handleDesktopDisplayConfigured(), added=" + z2 + ", desktopModeState=" + semDesktopModeState);
                        }
                        dualModeChanger2.mDualModeEnabled = z2;
                        dualModeChanger2.setDesktopModeState(z2 ? 4 : 2, 40);
                        if (z2) {
                            dualModeChanger2.startHome(state, dualModeChanger2.mDesktopDisplayId);
                        } else {
                            dualModeChanger2.mLoadingScreenAnimationComplete = true;
                            dualModeChanger2.mWallpaperShown = true;
                        }
                        SettingsHelper settingsHelper = dualModeChanger2.mSettingsHelper;
                        settingsHelper.getClass();
                        settingsHelper.backupOrRestoreSettings(z2, state, state.mCurrentUserId);
                        dualModeChanger2.setDesktopModeState(z2 ? 4 : 2, 50);
                        if (z3) {
                            DesktopModeService$$ExternalSyntheticOutline0.m("notifyDualOnConfigurationChanged(enter=", ")", "[DMS]StateManager", z2);
                        }
                        Iterator it = stateManager.mStateListeners.iterator();
                        while (it.hasNext()) {
                            ((StateManager.StateListener) it.next()).getClass();
                        }
                        dualModeChanger2.scheduleStopLoadingScreenIfPossible(z2);
                    }
                }
            });
        }

        public final void onSecuredAppLaunched(int i, String str) {
            DesktopModeService.this.onSecuredAppLaunched(i, str);
        }

        public final void scheduleUpdateDesktopMode(boolean z) {
            DesktopModeService.this.scheduleUpdateDesktopMode(z);
        }

        public final Bundle sendMessage(Bundle bundle) {
            return DesktopModeService.this.sendMessage(bundle);
        }

        public final int setDexHDMIAutoEnterState(int i) {
            ContentResolver contentResolver = DesktopModeService.this.mContext.getContentResolver();
            try {
                if (i == 0) {
                    String settingsAsUserOrThrowException = DesktopModeSettings.getSettingsAsUserOrThrowException(contentResolver, "hdmi_auto_enter_backup", null, DesktopModeSettings.sCurrentUserId);
                    if (settingsAsUserOrThrowException != null) {
                        DesktopModeSettings.setSettingsOrThrowException(contentResolver, "hdmi_auto_enter", settingsAsUserOrThrowException);
                        DesktopModeSettings.deleteSettingsAsUser(contentResolver, "hdmi_auto_enter_backup", DesktopModeSettings.sCurrentUserId);
                    }
                    DesktopModeSettings.setSettingsAsUserOrThrowException(contentResolver, "knox_hdmi_auto_enter_state", Integer.toString(i), DesktopModeSettings.sCurrentUserId);
                    return 0;
                }
                if (i != 1) {
                    if (i != 2) {
                        return 0;
                    }
                    DesktopModeSettings.deleteSettingsAsUser(contentResolver, "hdmi_initial_connection_dialog_shown", DesktopModeSettings.sCurrentUserId);
                    DesktopModeSettings.deleteSettingsAsUser(contentResolver, "hdmi_auto_enter", DesktopModeSettings.sCurrentUserId);
                    DesktopModeSettings.deleteSettingsAsUser(contentResolver, "hdmi_auto_enter_backup", DesktopModeSettings.sCurrentUserId);
                    DesktopModeSettings.setSettingsAsUserOrThrowException(contentResolver, "knox_hdmi_auto_enter_state", Integer.toString(i), DesktopModeSettings.sCurrentUserId);
                    return 0;
                }
                if (DesktopModeSettings.getSettingsAsUserOrThrowException(contentResolver, "hdmi_auto_enter_backup", null, DesktopModeSettings.sCurrentUserId) != null) {
                    return 0;
                }
                String settingsAsUserOrThrowException2 = DesktopModeSettings.getSettingsAsUserOrThrowException(contentResolver, "hdmi_auto_enter", null, DesktopModeSettings.sCurrentUserId);
                if (settingsAsUserOrThrowException2 == null) {
                    DesktopModeSettings.setSettingsOrThrowException(contentResolver, "hdmi_auto_enter_backup", "false");
                } else {
                    DesktopModeSettings.setSettingsOrThrowException(contentResolver, "hdmi_auto_enter_backup", settingsAsUserOrThrowException2);
                }
                DesktopModeSettings.setSettingsOrThrowException(contentResolver, "hdmi_auto_enter", "true");
                DesktopModeSettings.setSettingsAsUserOrThrowException(contentResolver, "knox_hdmi_auto_enter_state", Integer.toString(i), DesktopModeSettings.sCurrentUserId);
                return 0;
            } catch (IllegalArgumentException unused) {
                int i2 = DesktopModeService.$r8$clinit;
                Log.e("[DMS]DesktopModeService", "setDexHDMIAutoEnterState(), Failed to setting(value=" + i + ")");
                return -1;
            }
        }

        public final void startHome() {
            DesktopModeService desktopModeService = DesktopModeService.this;
            desktopModeService.startHome(((StateManager) desktopModeService.mStateManager).getState());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhonePreconditionChecker {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DesktopModeService this$0;

        public /* synthetic */ PhonePreconditionChecker(DesktopModeService desktopModeService, int i) {
            this.$r8$classId = i;
            this.this$0 = desktopModeService;
        }

        public final boolean isAllowed(StateManager.InternalState internalState, boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    DesktopModeService desktopModeService = this.this$0;
                    if (!desktopModeService.isSystemReady() || !DesktopModeService.m399$$Nest$misBlockerRegistered(desktopModeService, internalState, z) || !internalState.isPackagesAvailable()) {
                    }
                    break;
                default:
                    DesktopModeService desktopModeService2 = this.this$0;
                    if (desktopModeService2.isSystemReady()) {
                        if (internalState.mIsExternalDisplayConnected && internalState.mEmergencyModeEnabled) {
                            DesktopModeService.m399$$Nest$misBlockerRegistered(desktopModeService2, internalState, z);
                            break;
                        } else if (!DesktopModeService.m399$$Nest$misBlockerRegistered(desktopModeService2, internalState, z) || !internalState.isPackagesAvailable()) {
                        }
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:126:0x0247, code lost:
        
            if (r6.equals("PAP") == false) goto L100;
         */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0148  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0155  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r17, android.content.Intent r18) {
            /*
                Method dump skipped, instructions count: 750
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.DesktopModeService.Receiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WelcomeActivityListener extends UiManager.InternalUiCallback {
        public boolean mShown = false;
        public boolean mOverlayShown = false;
        public boolean mStartPressed = false;
        public final AnonymousClass1 mStateListener = new AnonymousClass1(1, this);

        public WelcomeActivityListener() {
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onClickButtonPositive() {
            this.mStartPressed = true;
            DesktopModeService desktopModeService = DesktopModeService.this;
            desktopModeService.finishWelcomeActivity();
            StateManager.InternalState state = ((StateManager) desktopModeService.mStateManager).getState();
            if (state.isHdmiConnected()) {
                DesktopModeSettings.setSettings(desktopModeService.mResolver, "hdmi_initial_connection_dialog_shown", true);
            }
            if (state.mIsPogoKeyboardConnected) {
                DesktopModeSettings.setSettings(desktopModeService.mResolver, "pogo_initial_connection_dialog_shown", true);
            }
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onDismiss() {
            DesktopModeService.this.mHandler.post(new DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
        public final void onShow() {
            DesktopModeService.this.mHandler.post(new DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda0(this, 0));
        }

        public final void setDesktopModeState(int i, boolean z) {
            boolean z2 = DesktopModeFeature.SUPPORT_STANDALONE;
            DesktopModeService desktopModeService = DesktopModeService.this;
            ((!z2 || z) ? desktopModeService.mDualModeChanger : desktopModeService.mStandaloneModeChanger).setDesktopModeState(2, i);
        }

        public final void showOrDismissOverlay(boolean z, boolean z2) {
            boolean z3 = false;
            if (!z || this.mOverlayShown) {
                if (z || !this.mOverlayShown) {
                    return;
                }
                if (DesktopModeFeature.DEBUG) {
                    int i = DesktopModeService.$r8$clinit;
                    DesktopModeService$$ExternalSyntheticOutline0.m("showOrDismissOverlay(), show=false, isCoverSupportStatePartial=", ", dismissing external overlay", "[DMS]DesktopModeService", z2);
                }
                this.mOverlayShown = false;
                DesktopModeService.this.mUiManager.dismissOverlay(103, 112);
                if (DesktopModeFeature.IS_TABLET) {
                    return;
                }
                DesktopModeService.this.mInjector.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                DesktopModeService.this.mCoverStateManager.disableCoverManager(false);
                DesktopModeService.this.mInjector.getClass();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            CoverStateManager coverStateManager = DesktopModeService.this.mCoverStateManager;
            synchronized (coverStateManager.mLock) {
                try {
                    CoverState coverState = coverStateManager.mCoverManager.getCoverState();
                    if (coverState != null) {
                        if (coverState.getAttachState() && CoverStateManager.isFlipTypeCover(coverState) && !coverState.getSwitchState()) {
                            z3 = true;
                        }
                    }
                } finally {
                }
            }
            if (z3) {
                if (DesktopModeFeature.DEBUG) {
                    int i2 = DesktopModeService.$r8$clinit;
                    DesktopModeService$$ExternalSyntheticOutline0.m("showOrDismissOverlay(), show=true, isCoverSupportStatePartial=", ", skip showing external overlay since cover is closed", "[DMS]DesktopModeService", z2);
                    return;
                }
                return;
            }
            if (DesktopModeFeature.DEBUG) {
                int i3 = DesktopModeService.$r8$clinit;
                DesktopModeService$$ExternalSyntheticOutline0.m("showOrDismissOverlay(), show=true, isCoverSupportStatePartial=", ", showing external overlay", "[DMS]DesktopModeService", z2);
            }
            this.mOverlayShown = true;
            DesktopModeService.this.mUiManager.showOverlay(103, 112, null);
            if (DesktopModeFeature.IS_TABLET || !z2) {
                return;
            }
            DesktopModeService.this.mInjector.getClass();
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            DesktopModeService.this.mCoverStateManager.disableCoverManager(true);
            DesktopModeService.this.mInjector.getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity2);
        }
    }

    /* renamed from: -$$Nest$mhandleHdmiAutoEnter, reason: not valid java name */
    public static void m396$$Nest$mhandleHdmiAutoEnter(DesktopModeService desktopModeService, boolean z) {
        if (desktopModeService.mCurrentUserId == 0 && desktopModeService.isSystemReady()) {
            if (!z) {
                desktopModeService.dismissDialog(5);
                desktopModeService.finishWelcomeActivity();
                return;
            }
            String settingsAsUser = DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "hdmi_auto_enter", (String) null, DesktopModeSettings.sCurrentUserId);
            boolean parseBoolean = Boolean.parseBoolean(settingsAsUser);
            if (!DesktopModeFeature.SUPPORT_NEW_DEX && settingsAsUser == null && !DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "hdmi_initial_connection_dialog_shown", false, DesktopModeSettings.sCurrentUserId)) {
                desktopModeService.mUiManager.showDialog(0, 5, desktopModeService.new AnonymousClass6(false, "hdmi_initial_connection_dialog_shown"));
            } else if (parseBoolean) {
                desktopModeService.setUserRequest(1);
                desktopModeService.scheduleUpdateDesktopMode(true);
            }
        }
    }

    /* renamed from: -$$Nest$mhandlePogoAutoEnter, reason: not valid java name */
    public static void m397$$Nest$mhandlePogoAutoEnter(DesktopModeService desktopModeService, boolean z) {
        if (desktopModeService.mCurrentUserId == 0 && desktopModeService.isSystemReady()) {
            if (!z) {
                desktopModeService.dismissDialog(4);
                desktopModeService.finishWelcomeActivity();
                return;
            }
            String settingsAsUser = DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "pogo_auto_enter", (String) null, DesktopModeSettings.sCurrentUserId);
            boolean parseBoolean = Boolean.parseBoolean(settingsAsUser);
            boolean equals = "new".equals(DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "dex_mode", DesktopModeSettings.DEX_MODE_DEFAULT_VALUE, DesktopModeSettings.sCurrentUserId));
            boolean settingsAsUser2 = DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "welcome_completed", false, DesktopModeSettings.sCurrentUserId);
            if (!parseBoolean) {
                if (DesktopModeFeature.SUPPORT_NEW_DEX || settingsAsUser != null || DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "pogo_initial_connection_dialog_shown", false, DesktopModeSettings.sCurrentUserId) || !desktopModeService.mAllowPogoInitialDialog || settingsAsUser2) {
                    return;
                }
                desktopModeService.mUiManager.showDialog(0, 4, desktopModeService.new AnonymousClass6(true, "pogo_initial_connection_dialog_shown"));
                return;
            }
            if (DesktopModeFeature.SUPPORT_NEW_DEX && equals) {
                desktopModeService.updateNewDexMode(1);
            } else if (!settingsAsUser2) {
                desktopModeService.startWelcomeActivity();
            } else {
                desktopModeService.setUserRequest(1);
                desktopModeService.scheduleUpdateDesktopMode(true);
            }
        }
    }

    /* renamed from: -$$Nest$misAllowedInternal, reason: not valid java name */
    public static boolean m398$$Nest$misAllowedInternal(DesktopModeService desktopModeService, StateManager.InternalState internalState, boolean z) {
        if (desktopModeService.mPreconditionChecker.isAllowed(internalState, z)) {
            return true;
        }
        if (DesktopModeFeature.SUPPORT_WIRELESS_DEX && internalState.isWirelessDexConnected()) {
            desktopModeService.mWirelessDexManager.disconnect();
        }
        return false;
    }

    /* renamed from: -$$Nest$misBlockerRegistered, reason: not valid java name */
    public static boolean m399$$Nest$misBlockerRegistered(DesktopModeService desktopModeService, StateManager.InternalState internalState, boolean z) {
        BlockerManager blockerManager = desktopModeService.mBlockerManager;
        BlockerManager.DesktopModeBlockerInfo defaultBlockerInfo = blockerManager.getDefaultBlockerInfo(internalState);
        if (defaultBlockerInfo == null) {
            synchronized (blockerManager.mBlockers) {
                try {
                    Iterator it = ((ArrayMap) blockerManager.mBlockers).values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            defaultBlockerInfo = null;
                            break;
                        }
                        BlockerManager.DesktopModeBlockerInfo desktopModeBlockerInfo = (BlockerManager.DesktopModeBlockerInfo) it.next();
                        if (desktopModeBlockerInfo != null) {
                            defaultBlockerInfo = desktopModeBlockerInfo;
                            break;
                        }
                    }
                } finally {
                }
            }
        }
        boolean z2 = true;
        if (defaultBlockerInfo != null) {
            Log.i("[DMS]DesktopModeService", "isAllowed(showToast=" + z + "), blocked by " + defaultBlockerInfo);
            if (z) {
                try {
                    ToastManager.showToast(desktopModeService.mContext, defaultBlockerInfo.blocker.onBlocked(), 1);
                    Log.SAVED_STATES.add(Log.buildLogString('W', "[DMS]State", "(enter) State=" + internalState + ", blocked by " + defaultBlockerInfo));
                } catch (RemoteException e) {
                    Log.e("[DMS]DesktopModeService", "Failed to get blocking message from blocker " + defaultBlockerInfo, e);
                }
            }
            z2 = false;
            if (!desktopModeService.mIsBootInitBlockerRegistered) {
                desktopModeService.setUserRequest(0);
            }
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0023  */
    /* renamed from: -$$Nest$mrequestTileUpdate, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m400$$Nest$mrequestTileUpdate(com.android.server.desktopmode.DesktopModeService r4, com.samsung.android.desktopmode.SemDesktopModeState r5) {
        /*
            r0 = 0
            r1 = 1
            if (r5 == 0) goto L17
            r4.getClass()
            int r2 = r5.getEnabled()
            r3 = 4
            if (r2 == r3) goto L15
            int r5 = r5.getEnabled()
            r2 = 3
            if (r5 != r2) goto L17
        L15:
            r5 = r1
            goto L18
        L17:
            r5 = r0
        L18:
            android.content.ContentResolver r2 = r4.mResolver
            java.lang.String r3 = "new_dex"
            int r2 = android.provider.Settings.System.getInt(r2, r3, r0)
            if (r2 != r1) goto L24
            r0 = r1
        L24:
            boolean r1 = r4.mIsDesktopModeEnablingOrEnabled
            if (r1 != r5) goto L2c
            boolean r1 = r4.mIsNewDexEnabled
            if (r1 == r0) goto L4d
        L2c:
            r4.mIsDesktopModeEnablingOrEnabled = r5
            r4.mIsNewDexEnabled = r0
            android.content.Context r5 = r4.mContext
            android.content.ComponentName r0 = new android.content.ComponentName
            java.lang.String r1 = "com.sec.android.desktopmode.uiservice"
            java.lang.String r2 = "com.sec.android.desktopmode.uiservice.DesktopModeTile"
            r0.<init>(r1, r2)
            android.service.quicksettings.TileService.requestListeningState(r5, r0)
            android.content.Context r4 = r4.mContext
            android.content.ComponentName r5 = new android.content.ComponentName
            java.lang.String r0 = "com.sec.android.desktopmode.uiservice.DesktopDisplayDesktopModeTile"
            r5.<init>(r1, r0)
            android.service.quicksettings.TileService.requestListeningState(r4, r5)
        L4d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.DesktopModeService.m400$$Nest$mrequestTileUpdate(com.android.server.desktopmode.DesktopModeService, com.samsung.android.desktopmode.SemDesktopModeState):void");
    }

    /* renamed from: -$$Nest$msetEnterRequestIfStandaloneLastBoot, reason: not valid java name */
    public static void m401$$Nest$msetEnterRequestIfStandaloneLastBoot(DesktopModeService desktopModeService, StateManager.InternalState internalState) {
        if (DesktopModeSettings.getSettingsAsUser(desktopModeService.mResolver, "display_type", 0, desktopModeService.mCurrentUserId) == 101) {
            Log.i("[DMS]DesktopModeService", "Request to enter standalone mode, since the standalone mode was enabled on last boot");
            desktopModeService.setUserRequest(1);
        }
        DesktopModeSettings.setSettingsAsUser(desktopModeService.mResolver, "display_type", internalState.mDesktopModeState.getDisplayType(), desktopModeService.mCurrentUserId);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.desktopmode.DesktopModeService$3] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.desktopmode.DesktopModeService$3] */
    public DesktopModeService(Injector injector, Context context, ServiceThread serviceThread, SemDesktopModeStateNotifier semDesktopModeStateNotifier, IStateManager iStateManager) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(0, this);
        int i = 0;
        this.mNewDeXSettingObserver = new AnonymousClass2(this, i);
        this.mWelcomeCompletedSettingChangedListener = new SettingsHelper.OnSettingChangedListener(this, i) { // from class: com.android.server.desktopmode.DesktopModeService.3
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ DesktopModeService this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super("welcome_completed");
                this.$r8$classId = i;
                switch (i) {
                    case 1:
                        this.this$0 = this;
                        super("external_display_mode");
                        break;
                    default:
                        this.this$0 = this;
                        break;
                }
            }

            @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
            public final void onSettingChanged(String str) {
                switch (this.$r8$classId) {
                    case 0:
                        if (Boolean.parseBoolean(str)) {
                            DesktopModeService desktopModeService = this.this$0;
                            desktopModeService.setUserRequest(1);
                            desktopModeService.scheduleUpdateDesktopMode(true);
                            break;
                        }
                        break;
                    default:
                        boolean equals = "dual".equals(str);
                        DesktopModeService desktopModeService2 = this.this$0;
                        desktopModeService2.mShowModeChangeScreen = ((StateManager) desktopModeService2.mStateManager).getState().isHdmiConnected();
                        desktopModeService2.scheduleUpdateDesktopMode(equals);
                        break;
                }
            }
        };
        this.mExternalDisplayModeListener = new SettingsHelper.OnSettingChangedListener(this, 1) { // from class: com.android.server.desktopmode.DesktopModeService.3
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ DesktopModeService this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super("welcome_completed");
                this.$r8$classId = i;
                switch (i) {
                    case 1:
                        this.this$0 = this;
                        super("external_display_mode");
                        break;
                    default:
                        this.this$0 = this;
                        break;
                }
            }

            @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
            public final void onSettingChanged(String str) {
                switch (this.$r8$classId) {
                    case 0:
                        if (Boolean.parseBoolean(str)) {
                            DesktopModeService desktopModeService = this.this$0;
                            desktopModeService.setUserRequest(1);
                            desktopModeService.scheduleUpdateDesktopMode(true);
                            break;
                        }
                        break;
                    default:
                        boolean equals = "dual".equals(str);
                        DesktopModeService desktopModeService2 = this.this$0;
                        desktopModeService2.mShowModeChangeScreen = ((StateManager) desktopModeService2.mStateManager).getState().isHdmiConnected();
                        desktopModeService2.scheduleUpdateDesktopMode(equals);
                        break;
                }
            }
        };
        this.mAllowPogoInitialDialog = false;
        this.mIsDexSourceEntry = false;
        this.mEnteredViaWirelessDex = false;
        this.mIsBootComplete = false;
        this.mIsBootInitBlockerRegistered = false;
        this.mShowModeChangeScreen = false;
        this.mIsDesktopModeEnablingOrEnabled = false;
        this.mIsNewDexEnabled = false;
        this.mUserRequest = 0;
        this.mCurrentUserId = -10000;
        this.mInjector = injector;
        this.mContext = context;
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mResolver = context.getContentResolver();
        this.mSemDesktopModeStateNotifier = semDesktopModeStateNotifier;
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(anonymousClass1);
        this.mPreconditionChecker = DesktopModeFeature.SUPPORT_STANDALONE ? new PhonePreconditionChecker(this, 1) : new PhonePreconditionChecker(this, 0);
        LocalService localService = new LocalService();
        injector.getClass();
        LocalServices.addService(DesktopModeManagerInternal.class, localService);
        Receiver receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
        intentFilter.addAction("com.samsung.android.desktopmode.action.DEX_RUNNING_NOTIFICATION_PRESSED");
        intentFilter.addAction("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(receiver, userHandle, intentFilter, null, null, 2);
        context.registerReceiverAsUser(receiver, userHandle, new IntentFilter("com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST"), "android.permission.WRITE_SECURE_SETTINGS", null, 2);
    }

    public static boolean isDesktopModeForPreparing(StateManager.InternalState internalState, int i) {
        SemDesktopModeState semDesktopModeState = internalState.mDesktopModeState;
        return (i == -1 || semDesktopModeState.getDisplayType() == i) && (semDesktopModeState.enabled == 4 || semDesktopModeState.compareTo(3, 30) || semDesktopModeState.compareTo(1, 20));
    }

    public static boolean isExternalDisplayConnectedOrForced(StateManager.InternalState internalState) {
        return internalState.mIsExternalDisplayConnected || internalState.mForcedInternalScreenModeEnabled;
    }

    public static String userRequestToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=") : "EXIT_REQUEST" : "ENTER_REQUEST" : "NO_REQUEST";
    }

    public final void dismissDialog(int... iArr) {
        for (int i : iArr) {
            this.mUiManager.dismissDialog(0, i);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        boolean z = true;
        if (!DumpUtils.checkDumpPermission(this.mContext, "[DMS]DesktopModeService", printWriter)) {
            return;
        }
        if (strArr != null && strArr.length != 0 && !"-a".equals(strArr[0])) {
            if (DesktopModeFeature.DEBUG) {
                String str2 = strArr[0];
                if ("off".equals(str2) || "toggle".equals(str2)) {
                    this.mHwManager.command(printWriter, str2);
                    printWriter.println("Please enter below next time:");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  cmd desktopmode "), str2, printWriter);
                    return;
                } else {
                    if (!"settings".equals(str2) && !"resolution".equals(str2) && !"ui".equals(str2)) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Unknown argument: ", str2, "; USAGE: [on|off|toggle|dblist]");
                        return;
                    }
                    printWriter.println("Please enter below instead:");
                    printWriter.println("  cmd desktopmode " + str2);
                    return;
                }
            }
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("DesktopModeService (dumpsys desktopmode):");
        indentingPrintWriter.println(Log.buildLogString('V', "[DMS]StateStart", "=========================================================================="));
        Log.SAVED_STATES.dump(indentingPrintWriter);
        indentingPrintWriter.println(Log.buildLogString('V', "[DMS]StateEnd", "=========================================================================="));
        indentingPrintWriter.println(Log.buildLogString('V', "[DMS]SavedLogsStart", "=========================================================================="));
        Log.SAVED_LOGS.dump(indentingPrintWriter);
        indentingPrintWriter.println(Log.buildLogString('V', "[DMS]SavedLogsEnd", "=========================================================================="));
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle call = this.mResolver.call(DesktopModeSettings.getUriAsUser(this.mCurrentUserId), "dumpApp", (String) null, (Bundle) null);
            if (call != null) {
                indentingPrintWriter.print(call.getString("dumpApp"));
            }
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to dump", e);
        }
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentUserId=" + this.mCurrentUserId);
        indentingPrintWriter.println("Configuration=" + this.mContext.getResources().getConfiguration());
        indentingPrintWriter.println("mUserRequest=" + userRequestToString(this.mUserRequest));
        indentingPrintWriter.println("welcomeCompleted=" + DesktopModeSettings.getSettingsAsUser(this.mResolver, "welcome_completed", false, DesktopModeSettings.sCurrentUserId));
        indentingPrintWriter.println("DISPLAY_SIZE_FORCED=" + Settings.Global.getString(this.mResolver, "display_size_forced"));
        indentingPrintWriter.println("DISPLAY_DENSITY_FORCED=" + Settings.Secure.getStringForUser(this.mResolver, "display_density_forced", 0));
        indentingPrintWriter.println("SCREEN_OFF_TIMEOUT=" + Settings.System.getStringForUser(this.mResolver, "screen_off_timeout", this.mCurrentUserId));
        indentingPrintWriter.println("SHOW_IME_WITH_HARD_KEYBOARD=" + Settings.Secure.getStringForUser(this.mResolver, "show_ime_with_hard_keyboard", this.mCurrentUserId));
        indentingPrintWriter.println();
        StandaloneModeChanger standaloneModeChanger = this.mStandaloneModeChanger;
        standaloneModeChanger.getClass();
        indentingPrintWriter.println("Current StandaloneModeChanger state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentUiMode=" + standaloneModeChanger.mCurrentUiMode);
        StringBuilder m = DesktopModeService$$ExternalSyntheticOutline0.m(DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mIsLockTaskModeEnabledAndSecured="), standaloneModeChanger.mIsLockTaskModeEnabledAndSecured, indentingPrintWriter, "mStandaloneModeEnabled="), standaloneModeChanger.mStandaloneModeEnabled, indentingPrintWriter, "mTopTaskId=");
        m.append(standaloneModeChanger.mTopTaskId);
        indentingPrintWriter.println(m.toString());
        if (DesktopModeFeature.FEATURE_STANDALONE_MODE_WALLPAPER) {
            indentingPrintWriter.println("mWallpaperShown=" + standaloneModeChanger.mWallpaperShown);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        DualModeChanger dualModeChanger = this.mDualModeChanger;
        dualModeChanger.getClass();
        indentingPrintWriter.println("Current DualModeChanger state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mDesktopDisplayId=" + dualModeChanger.mDesktopDisplayId);
        StringBuilder m2 = DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mDualModeEnabled="), dualModeChanger.mDualModeEnabled, indentingPrintWriter, "mModeToModeChangeInfo=");
        m2.append(dualModeChanger.mModeToModeChangeInfo);
        indentingPrintWriter.println(m2.toString());
        indentingPrintWriter.println("mPrevDesktopDisplayId=" + dualModeChanger.mPrevDesktopDisplayId);
        indentingPrintWriter.println("mTopTaskId=" + dualModeChanger.mTopTaskId);
        StringBuilder m3 = DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mTopTaskIdValid="), dualModeChanger.mTopTaskIdValid, indentingPrintWriter, "mTouchpadRequestedByNotification=");
        m3.append(dualModeChanger.mTouchpadRequestedByNotification);
        indentingPrintWriter.println(m3.toString());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        StateManager stateManager = (StateManager) this.mStateManager;
        synchronized (stateManager.mLock) {
            indentingPrintWriter.println("Current StateManager state:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mState=" + stateManager.mState);
            indentingPrintWriter.println("mStateListeners=" + stateManager.mStateListeners);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        PackageStateManager packageStateManager = this.mPackageStateManager;
        packageStateManager.getClass();
        indentingPrintWriter.println("Current PackageStateManager state:");
        indentingPrintWriter.increaseIndent();
        synchronized (packageStateManager.mLock) {
            indentingPrintWriter.println("mPackageState=" + packageStateManager.mPackageState);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        SettingsHelper settingsHelper = this.mSettingsHelper;
        ContentResolver contentResolver = this.mResolver;
        int i = this.mCurrentUserId;
        settingsHelper.getClass();
        indentingPrintWriter.println("Current DesktopModeSettings state:");
        indentingPrintWriter.increaseIndent();
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        StringBuilder m4 = BatteryService$$ExternalSyntheticOutline0.m(i, "Current user (", ") settings=");
        m4.append(Utils.bundleToString(DesktopModeSettings.getAllSettingsAsUser(i, contentResolver)));
        indentingPrintWriter.println(m4.toString());
        if (i != 0) {
            indentingPrintWriter.println("System user (0) settings" + Utils.bundleToString(DesktopModeSettings.getAllSettingsAsUser(0, contentResolver)));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity2);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        UiManager uiManager = this.mUiManager;
        uiManager.getClass();
        indentingPrintWriter.println("Current UiManager state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("external overlay=" + DesktopModeUiConstants.typeToString(uiManager.getCurrentOverlayType(103)));
        indentingPrintWriter.println("internal overlay=" + DesktopModeUiConstants.typeToString(uiManager.getCurrentOverlayType(102)));
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        SemDesktopModeStateNotifier semDesktopModeStateNotifier = this.mSemDesktopModeStateNotifier;
        semDesktopModeStateNotifier.getClass();
        indentingPrintWriter.println("Current SemDesktopModeStateNotifier state:");
        indentingPrintWriter.increaseIndent();
        synchronized (semDesktopModeStateNotifier.mInnerLock) {
            try {
                indentingPrintWriter.println("mListeners (" + ((ArrayMap) semDesktopModeStateNotifier.mListeners).size() + "):");
                indentingPrintWriter.increaseIndent();
                Iterator it = ((ArrayMap) semDesktopModeStateNotifier.mListeners).values().iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println((SemDesktopModeStateNotifier.ListenerInfo) it.next());
                }
                indentingPrintWriter.decreaseIndent();
            } finally {
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        BlockerManager blockerManager = this.mBlockerManager;
        blockerManager.getClass();
        indentingPrintWriter.println("Current BlockerManager state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("DefaultBlocker=" + blockerManager.getDefaultBlockerInfo(((StateManager) blockerManager.mStateManager).getState()));
        synchronized (blockerManager.mBlockers) {
            try {
                indentingPrintWriter.println("mBlockers (" + ((ArrayMap) blockerManager.mBlockers).size() + "):");
                indentingPrintWriter.increaseIndent();
                Iterator it2 = ((ArrayMap) blockerManager.mBlockers).values().iterator();
                while (it2.hasNext()) {
                    indentingPrintWriter.println((BlockerManager.DesktopModeBlockerInfo) it2.next());
                }
                indentingPrintWriter.decreaseIndent();
            } finally {
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mHwManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        TouchpadManager touchpadManager = this.mTouchpadManager;
        touchpadManager.getClass();
        indentingPrintWriter.println("Current TouchpadManager state:");
        indentingPrintWriter.increaseIndent();
        StringBuilder m5 = DesktopModeService$$ExternalSyntheticOutline0.m(DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mIsSPenDetached="), touchpadManager.mIsSPenDetached, indentingPrintWriter, "mIsSPenEnabled="), touchpadManager.mIsSPenEnabled, indentingPrintWriter, "mIsTouchpadEnabled=");
        m5.append(touchpadManager.mIsTouchpadEnabled);
        indentingPrintWriter.println(m5.toString());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mCoverStateManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        ToastManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        Set set = DesktopModeKillPolicyManager.KEEP_POLICY_PACKAGES;
        indentingPrintWriter.println("Current DesktopModeKillPolicyManager state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("KEEP_POLICY_PACKAGES (" + DesktopModeKillPolicyManager.KEEP_POLICY_PACKAGES.size() + ")");
        indentingPrintWriter.println("KILL_POLICY_PACKAGES (" + DesktopModeKillPolicyManager.KILL_POLICY_PACKAGES.size() + ")");
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        MultiResolutionManager multiResolutionManager = this.mMultiResolutionManager;
        multiResolutionManager.getClass();
        indentingPrintWriter.println("Current MultiResolutionManager state:");
        indentingPrintWriter.increaseIndent();
        StringBuilder m6 = DesktopModeService$$ExternalSyntheticOutline0.m(DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mIsForcedSupportAllResolution="), multiResolutionManager.mIsForcedSupportAllResolution, indentingPrintWriter, "mDisplayRemovedOnEnablingDesktopMode="), multiResolutionManager.mDisplayRemovedOnEnablingDesktopMode, indentingPrintWriter, "mStandaloneModeDisplayMetrics=");
        m6.append(multiResolutionManager.mStandaloneModeDisplayMetrics);
        indentingPrintWriter.println(m6.toString());
        indentingPrintWriter.println("mCustomDisplayMetrics=" + multiResolutionManager.mCustomDisplayMetrics);
        indentingPrintWriter.println("mUserSettingResolution=" + multiResolutionManager.mUserSettingResolution);
        indentingPrintWriter.println("mMaxSupportedResolution=" + multiResolutionManager.mMaxSupportedResolution);
        indentingPrintWriter.println("mSelectedDisplayMetrics=" + multiResolutionManager.mSelectedDisplayMetrics);
        indentingPrintWriter.println("mLastDualModeMaxSupportedResolution=" + multiResolutionManager.mLastDualModeMaxSupportedResolution);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        DockManager dockManager = this.mDockManager;
        dockManager.getClass();
        indentingPrintWriter.println("Current DockManager state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mDockVersion=" + dockManager.mDockVersion);
        indentingPrintWriter.println("mDockVersionExtra=" + dockManager.mDockVersionExtra);
        indentingPrintWriter.println("mDockChargerPower=" + dockManager.mDockChargerPower);
        indentingPrintWriter.println("mDockChargerSupport=" + dockManager.mDockChargerSupport);
        indentingPrintWriter.println("Last DockManager state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mLastDockVersion=" + dockManager.mLastDockVersion);
        int i2 = 0;
        while (true) {
            DockManager.AnonymousClass3 anonymousClass3 = dockManager.mLastDockControlLibMsgState;
            if (i2 >= ((ArrayMap) anonymousClass3.this$0).size()) {
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                this.mDisplayPortStateManager.dump(indentingPrintWriter);
                if (DesktopModeFeature.SUPPORT_WIRELESS_DEX) {
                    indentingPrintWriter.println();
                    WirelessDexManager wirelessDexManager = this.mWirelessDexManager;
                    wirelessDexManager.getClass();
                    indentingPrintWriter.println("Current WirelessDexManager state:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("mFrequencyValue=" + wirelessDexManager.mFrequencyValue);
                    StringBuilder m7 = DesktopModeService$$ExternalSyntheticOutline0.m(DesktopModeService$$ExternalSyntheticOutline0.m(new StringBuilder("mIsLowLatencyMode="), wirelessDexManager.mIsLowLatencyMode, indentingPrintWriter, "mIsWirelessDexEntered="), wirelessDexManager.mIsWirelessDexEntered, indentingPrintWriter, "mPointerIconSync=");
                    m7.append(wirelessDexManager.mPointerIconSync);
                    indentingPrintWriter.println(m7.toString());
                    indentingPrintWriter.println("mReducedLatency=" + wirelessDexManager.mReducedLatency);
                    indentingPrintWriter.println("mTvTizenVersion=" + wirelessDexManager.mTvTizenVersion);
                    indentingPrintWriter.decreaseIndent();
                    if (this.mBleAdvertiserServiceManager != null) {
                        indentingPrintWriter.println();
                        BleAdvertiserServiceManager bleAdvertiserServiceManager = this.mBleAdvertiserServiceManager;
                        bleAdvertiserServiceManager.getClass();
                        indentingPrintWriter.println("Current BleAdvertiserServiceManager state:");
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("mBound=" + bleAdvertiserServiceManager.mBound);
                        indentingPrintWriter.decreaseIndent();
                    }
                    if (this.mMcfManager != null) {
                        indentingPrintWriter.println();
                        McfManager mcfManager = this.mMcfManager;
                        mcfManager.getClass();
                        indentingPrintWriter.println("Current McfManager state:");
                        indentingPrintWriter.increaseIndent();
                        StringBuilder sb = new StringBuilder("isBleAvailable=");
                        McfBleAdapter mcfBleAdapter = mcfManager.mMcfBleAdapter;
                        if (mcfBleAdapter != null) {
                            z = mcfBleAdapter.isNetworkEnabled(1);
                        } else {
                            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                            if (defaultAdapter == null || !defaultAdapter.semIsBleEnabled()) {
                                z = false;
                            }
                        }
                        StringBuilder m8 = DesktopModeService$$ExternalSyntheticOutline0.m(sb, z, indentingPrintWriter, "mBleScannerState=");
                        m8.append(mcfManager.mBleScannerState);
                        indentingPrintWriter.println(m8.toString());
                        indentingPrintWriter.println("mBleStartScanReason=" + McfManager.bleStartScanReasonToString(mcfManager.mBleStartScanReason));
                        indentingPrintWriter.println("mBleAdvertiserServiceTimeout=" + mcfManager.mBleAdvertiserServiceTimeout);
                        indentingPrintWriter.println("mWakeLockTimeout=" + mcfManager.mWakeLockTimeout);
                        indentingPrintWriter.println("mLastBleScanFailedErrorCode=" + mcfManager.mLastBleScanFailedErrorCode);
                        if (DesktopModeFeature.DEBUG) {
                            indentingPrintWriter.println("mLastBleScanResult=" + mcfManager.mLastBleScanResult);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
                return;
            }
            StringBuilder sb2 = new StringBuilder("mLastDockControlLibMsgState=");
            StringBuilder sb3 = new StringBuilder();
            int intValue = ((Integer) ((ArrayMap) anonymousClass3.this$0).keyAt(i2)).intValue();
            switch (intValue) {
                case 100:
                    str = "MSG_TYPE_REQUEST";
                    break;
                case 101:
                    str = "MSG_TYPE_RESPONSE";
                    break;
                case 102:
                    str = "MSG_TYPE_ERROR";
                    break;
                case 103:
                    str = "MSG_TYPE_VERSION";
                    break;
                case 104:
                    str = "MSG_TYPE_EXTRA";
                    break;
                default:
                    str = VibrationParam$1$$ExternalSyntheticOutline0.m(intValue, "Unknown=");
                    break;
            }
            sb3.append(str);
            sb3.append(", ");
            sb3.append((String) ((ArrayMap) anonymousClass3.this$0).valueAt(i2));
            sb2.append(sb3.toString());
            indentingPrintWriter.println(sb2.toString());
            i2++;
        }
    }

    public final void finishWelcomeActivity() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DesktopModeService", "finishWelcomeActivity()");
        }
        this.mUiManager.finishActivity(301);
    }

    public final Bundle getDesktopModeKillPolicy() {
        Set set = DesktopModeKillPolicyManager.KEEP_POLICY_PACKAGES;
        Bundle m142m = AccountManagerService$$ExternalSyntheticOutline0.m142m("name", "DeX");
        m142m.putSerializable("kill_packages", (Serializable) DesktopModeKillPolicyManager.KILL_POLICY_PACKAGES);
        m142m.putSerializable("keep_packages", (Serializable) DesktopModeKillPolicyManager.KEEP_POLICY_PACKAGES);
        m142m.putBoolean("skip_sdk_version_check", true);
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS_POLICY]DesktopModeKillPolicyManager", "getDesktopModeKillPolicy(), args=" + m142m);
        }
        return m142m;
    }

    public final SemDesktopModeState getDesktopModeState() {
        return ((StateManager) this.mStateManager).getState().mDesktopModeState;
    }

    public final void initializeStates() {
        if (!isUserSetupComplete()) {
            if (this.mUserSetupCompleteObserver == null) {
                this.mUserSetupCompleteObserver = new AnonymousClass2(this, 1);
            }
            this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupCompleteObserver, this.mCurrentUserId);
        } else if (this.mIsBootComplete) {
            StateManager stateManager = (StateManager) this.mStateManager;
            stateManager.getClass();
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]StateManager", "notifyBootCompleted()");
            }
            synchronized (stateManager.mLock) {
                stateManager.mHandler.post(new StateManager$$ExternalSyntheticLambda2(stateManager));
            }
        }
    }

    public void injectServices(ActivityManagerService activityManagerService, ActivityTaskManagerService activityTaskManagerService, ActivityTaskManagerInternal activityTaskManagerInternal, BlockerManager blockerManager, EmergencyModeBlocker emergencyModeBlocker, UiManager uiManager, SettingsHelper settingsHelper, HardwareManager hardwareManager, TouchpadManager touchpadManager, CoverStateManager coverStateManager, MultiResolutionManager multiResolutionManager, DockManager dockManager, PackageStateManager packageStateManager, StatusBarManager statusBarManager, BootInitBlocker bootInitBlocker, DisplayPortStateManager displayPortStateManager, WirelessDexManager wirelessDexManager, BleAdvertiserServiceManager bleAdvertiserServiceManager, DualModeChanger dualModeChanger, StandaloneModeChanger standaloneModeChanger, McfManager mcfManager, DexManager dexManager) {
        this.mActivityManagerService = activityManagerService;
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mBlockerManager = blockerManager;
        this.mEmergencyModeBlocker = emergencyModeBlocker;
        this.mUiManager = uiManager;
        this.mSettingsHelper = settingsHelper;
        settingsHelper.registerListener(this.mWelcomeCompletedSettingChangedListener);
        settingsHelper.registerListener(this.mExternalDisplayModeListener);
        if (DesktopModeFeature.SUPPORT_NEW_DEX) {
            this.mResolver.registerContentObserver(Settings.System.getUriFor("new_dex"), true, this.mNewDeXSettingObserver);
        }
        this.mHwManager = hardwareManager;
        this.mTouchpadManager = touchpadManager;
        this.mCoverStateManager = coverStateManager;
        this.mMultiResolutionManager = multiResolutionManager;
        this.mDockManager = dockManager;
        this.mPackageStateManager = packageStateManager;
        this.mStatusBarManager = statusBarManager;
        DualModeChanger dualModeChanger2 = dualModeChanger;
        this.mDualModeChanger = dualModeChanger2;
        this.mStandaloneModeChanger = standaloneModeChanger;
        if (DesktopModeFeature.SUPPORT_STANDALONE) {
            dualModeChanger2 = null;
        }
        this.mModeChanger = dualModeChanger2;
        if (bootInitBlocker != null) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]BootInitBlocker", "registerBlocker");
            }
            bootInitBlocker.mDesktopModeManager.registerBlocker(bootInitBlocker.mBlocker);
            StateManager stateManager = (StateManager) bootInitBlocker.mStateManager;
            stateManager.notifyBootInitBlockerRegistered(true);
            stateManager.registerListener(bootInitBlocker.new AnonymousClass1());
        }
        this.mDisplayPortStateManager = displayPortStateManager;
        this.mWirelessDexManager = wirelessDexManager;
        this.mBleAdvertiserServiceManager = bleAdvertiserServiceManager;
        this.mMcfManager = mcfManager;
        this.mDexManager = dexManager;
    }

    public final boolean isAllowed() {
        return this.mPreconditionChecker.isAllowed(((StateManager) this.mStateManager).getState(), false);
    }

    public final boolean isDesktopDockConnected() {
        return ((StateManager) this.mStateManager).getState().mDockState.isDexStation();
    }

    public final boolean isDesktopMode() {
        return this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
    }

    public final boolean isDeviceConnected() {
        StateManager.InternalState state = ((StateManager) this.mStateManager).getState();
        return state.mDockState.mDesktopModeSupported || state.isDexOnPcOrWirelessDexConnected();
    }

    public final boolean isSystemReady() {
        if (this.mIsBootComplete && isUserSetupComplete() && !FactoryTest.isFactoryBinary() && this.mCurrentUserId != -10000) {
            PackageManager packageManager = this.mContext.getPackageManager();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.addCategory("android.intent.category.DEFAULT");
            Iterator<ResolveInfo> it = packageManager.queryIntentActivities(intent, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT).iterator();
            while (it.hasNext()) {
                if ("com.sec.android.app.SecSetupWizard".equals(it.next().activityInfo.packageName)) {
                    Log.i("[DMS]DesktopModeService", "isFotaSuwCompleted()=false");
                }
            }
            return true;
        }
        Log.i("[DMS]DesktopModeService", "isSystemReady(), mIsBootComplete=" + this.mIsBootComplete + ", isFactoryBinary=" + FactoryTest.isFactoryBinary() + ", mCurrentUserId=" + this.mCurrentUserId);
        return false;
    }

    public final boolean isUserSetupComplete() {
        boolean z = Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId) != 0;
        if (!z && DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DesktopModeService", "isUserSetupComplete()=false");
        }
        return z;
    }

    public final void onSecuredAppLaunched(final int i, final String str) {
        if (DesktopModeFeature.DEBUG) {
            Log.i("[DMS]DesktopModeService", "onSecuredAppLaunched, taskId=" + i + ", packageName= " + str);
        }
        if (((StateManager) this.mStateManager).getState().isDexOnPcConnected()) {
            final DualModeChanger dualModeChanger = this.mDualModeChanger;
            dualModeChanger.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.DualModeChanger$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DualModeChanger dualModeChanger2 = DualModeChanger.this;
                    int i2 = i;
                    String str2 = str;
                    int i3 = ((StateManager) dualModeChanger2.mStateManager).getState().mDesktopDisplayId;
                    if (i3 != 0) {
                        dualModeChanger2.mUiManager.showDialog(i3, 8, null);
                    }
                    dualModeChanger2.mContext.sendBroadcastAsUser(new Intent("com.samsung.android.desktopmode.action.SECURED_APP_LAUNCHED").setPackage("com.sec.android.app.dexonpc").putExtra("com.samsung.android.desktopmode.extra.TASK_ID", i2).putExtra("com.samsung.android.desktopmode.extra.PACKAGE_NAME", str2), UserHandle.CURRENT);
                }
            });
        }
    }

    public final void onSecuredAppLaunched(IBinder iBinder, String str) {
        onSecuredAppLaunched(this.mActivityManagerService.getTaskForActivity(iBinder, false), str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        if (!DesktopModeFeature.DEBUG) {
            super.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            return;
        }
        this.mInjector.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller must be shell");
        }
        ContentResolver contentResolver = this.mResolver;
        MultiResolutionManager multiResolutionManager = this.mMultiResolutionManager;
        UiManager uiManager = this.mUiManager;
        HardwareManager hardwareManager = this.mHwManager;
        Shell shell = new Shell();
        shell.mResolver = contentResolver;
        shell.mMultiResolutionManager = multiResolutionManager;
        shell.mUiManager = uiManager;
        shell.mHwManager = hardwareManager;
        shell.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onUserChanged(int i) {
        if (i == this.mCurrentUserId) {
            Log.d("[DMS]DesktopModeService", "onUserChanged(), userId and mCurrentUserId are same(" + i + ")");
            return;
        }
        boolean z = DesktopModeFeature.DEBUG;
        if (z) {
            DesktopModeService$$ExternalSyntheticOutline0.m(i, "onUserChanged(), userId=", "[DMS]DesktopModeService");
        }
        if (z) {
            DesktopModeService$$ExternalSyntheticOutline0.m(i, "setCurrentUserId(), userId=", "[DMS]DesktopModeService");
        }
        this.mCurrentUserId = i;
        StateManager stateManager = (StateManager) this.mStateManager;
        stateManager.getClass();
        if (z) {
            Log.d("[DMS]StateManager", "setCurrentUserId(userId=" + i + ")");
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mCurrentUserId != i) {
                    stateManager.mInternalState.mCurrentUserId = i;
                    stateManager.mHandler.post(new StateManager$$ExternalSyntheticLambda0(stateManager, stateManager.copyInternalStateLocked(stateManager.mInternalState), 8));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        initializeStates();
    }

    public final boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) {
        this.mInjector.getClass();
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only the system may call registerBlocker()");
        }
        BlockerManager blockerManager = this.mBlockerManager;
        synchronized (blockerManager.mBlockers) {
            try {
                IBinder asBinder = iDesktopModeBlocker.asBinder();
                try {
                    blockerManager.mInjector.getClass();
                    int callingPid = Binder.getCallingPid();
                    blockerManager.mInjector.getClass();
                    BlockerManager.DesktopModeBlockerInfo desktopModeBlockerInfo = blockerManager.new DesktopModeBlockerInfo(iDesktopModeBlocker, str, callingPid, Binder.getCallingUid());
                    asBinder.linkToDeath(desktopModeBlockerInfo, 0);
                    ((ArrayMap) blockerManager.mBlockers).put(asBinder, desktopModeBlockerInfo);
                    ((StateManager) blockerManager.mStateManager).notifyScheduleUpdateDesktopMode(false);
                } catch (RemoteException unused) {
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) {
        this.mInjector.getClass();
        int callingUid = Binder.getCallingUid();
        if (this.mCurrentUserId != 0) {
            callingUid = UserHandle.getAppId(callingUid);
        }
        if (callingUid != 0 && callingUid != 1000) {
            Log.d("[DMS]DesktopModeService", "registerDesktopLauncher() is blocked!!");
            return;
        }
        synchronized (this.mLock) {
            this.mLauncherInterface = iDesktopModeLauncher;
        }
    }

    public final boolean registerDesktopModeListener(IDesktopModeListener iDesktopModeListener, String str) {
        SemDesktopModeStateNotifier semDesktopModeStateNotifier = this.mSemDesktopModeStateNotifier;
        synchronized (semDesktopModeStateNotifier.mInnerLock) {
            try {
                IBinder asBinder = iDesktopModeListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    semDesktopModeStateNotifier.mInjector.getClass();
                    int callingPid = Binder.getCallingPid();
                    semDesktopModeStateNotifier.mInjector.getClass();
                    SemDesktopModeStateNotifier.ListenerInfo listenerInfo = semDesktopModeStateNotifier.new ListenerInfo(iDesktopModeListener, str, callingPid, Binder.getCallingUid());
                    SemDesktopModeStateNotifier.enforceRegisterLimitLocked(listenerInfo, semDesktopModeStateNotifier.mListeners);
                    asBinder.linkToDeath(listenerInfo, 0);
                    ((ArrayMap) semDesktopModeStateNotifier.mListeners).put(asBinder, listenerInfo);
                } catch (RemoteException e) {
                    Log.e("[DMS]SemDesktopModeStateNotifier", "Failed to register DesktopModeListener", e);
                    return false;
                }
            } finally {
            }
        }
        return true;
    }

    public final void scheduleUpdateDesktopMode(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DesktopModeService desktopModeService = DesktopModeService.this;
                desktopModeService.updateDesktopMode(((StateManager) desktopModeService.mStateManager).getState(), z, false);
            }
        });
    }

    public final Bundle sendMessage(Bundle bundle) {
        if (this.mContext.checkCallingOrSelfPermission("com.samsung.android.desktopmode.permission.BIND_DESKTOP_MODE_UI_SERVICE") == 0) {
            if (bundle.getInt("command") == 1000) {
                final StateManager.InternalState state = ((StateManager) this.mStateManager).getState();
                this.mHandler.post(new Runnable() { // from class: com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopModeService desktopModeService = DesktopModeService.this;
                        StateManager.InternalState internalState = state;
                        desktopModeService.getClass();
                        int i = internalState.mDesktopDisplayId;
                        if (i != 0) {
                            DualModeChanger dualModeChanger = desktopModeService.mDualModeChanger;
                            dualModeChanger.mTopTaskId = dualModeChanger.mActivityTaskManagerInternal.getForegroundTaskId(i);
                            dualModeChanger.mTopTaskIdValid = true;
                        }
                        desktopModeService.mActivityTaskManagerInternal.minimizeAllTasks(i, false);
                    }
                });
                return null;
            }
        } else if (this.mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE") == 0) {
            synchronized (this.mLock) {
                try {
                    IDesktopModeLauncher iDesktopModeLauncher = this.mLauncherInterface;
                    if (iDesktopModeLauncher != null) {
                        return iDesktopModeLauncher.sendMessage(bundle);
                    }
                    Log.w("[DMS]DesktopModeService", "sendMessage(), mLauncherInterface == null");
                } catch (RemoteException e) {
                    Log.e("[DMS]DesktopModeService", "sendMessage(), Failed to communicate with launcher. Starting again...", e);
                    this.mHandler.post(new DesktopModeService$$ExternalSyntheticLambda2(0, this));
                } finally {
                }
            }
        }
        return null;
    }

    public final void setUserRequest(int i) {
        this.mUserRequest = i;
        Log.i("[DMS]DesktopModeService", "userRequest=" + userRequestToString(this.mUserRequest));
    }

    public final void startConnectivityActivity(int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DesktopModeService", "startConnectivityActivity()");
        }
        Intent flags = new Intent().setComponent(new ComponentName("com.sec.android.desktopmode.uiservice", "com.sec.android.desktopmode.uiservice.activity.connectivity.ConnectivityActivity")).setFlags(337641472);
        Context context = this.mContext;
        int i2 = this.mCurrentUserId;
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(i);
        context.startActivityAsUser(flags, makeBasic.toBundle(), UserHandle.of(i2));
    }

    public final void startHome(StateManager.InternalState internalState) {
        DualModeChanger dualModeChanger;
        int i;
        SemDesktopModeState semDesktopModeState = internalState.mDesktopModeState;
        if (semDesktopModeState.enabled == 4) {
            if (semDesktopModeState.getDisplayType() == 101) {
                this.mStandaloneModeChanger.startHome(internalState);
            } else {
                if (semDesktopModeState.getDisplayType() != 102 || (i = (dualModeChanger = this.mDualModeChanger).mDesktopDisplayId) == -1) {
                    return;
                }
                dualModeChanger.startHome(internalState, i);
            }
        }
    }

    public final void startWelcomeActivity() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DesktopModeService", "startWelcomeActivity()");
        }
        if (DesktopModeSettings.getSettingsAsUser(this.mResolver, "skip_welcome_screen", false, DesktopModeSettings.sCurrentUserId) || DesktopModeSettings.getSettingsAsUser(this.mResolver, "welcome_shown", false, DesktopModeSettings.sCurrentUserId)) {
            DesktopModeSettings.setSettings(this.mResolver, "welcome_completed", true);
            return;
        }
        if (this.mWelcomeActivityListener == null) {
            this.mWelcomeActivityListener = new WelcomeActivityListener();
        }
        this.mUiManager.startActivity(0, 301, this.mWelcomeActivityListener);
    }

    public final boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) {
        boolean z;
        this.mInjector.getClass();
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Only the system may call unregisterBlocker()");
        }
        BlockerManager blockerManager = this.mBlockerManager;
        synchronized (blockerManager.mBlockers) {
            try {
                BlockerManager.DesktopModeBlockerInfo desktopModeBlockerInfo = (BlockerManager.DesktopModeBlockerInfo) ((ArrayMap) blockerManager.mBlockers).remove(iDesktopModeBlocker.asBinder());
                z = false;
                if (desktopModeBlockerInfo != null) {
                    desktopModeBlockerInfo.blocker.asBinder().unlinkToDeath(desktopModeBlockerInfo, 0);
                    z = true;
                    if (((ArrayMap) blockerManager.mBlockers).isEmpty()) {
                        ((StateManager) blockerManager.mStateManager).notifyScheduleUpdateDesktopMode(true);
                    }
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) {
        SemDesktopModeStateNotifier semDesktopModeStateNotifier = this.mSemDesktopModeStateNotifier;
        synchronized (semDesktopModeStateNotifier.mInnerLock) {
            try {
                SemDesktopModeStateNotifier.ListenerInfo listenerInfo = (SemDesktopModeStateNotifier.ListenerInfo) ((ArrayMap) semDesktopModeStateNotifier.mListeners).remove(iDesktopModeListener.asBinder());
                if (listenerInfo == null) {
                    return false;
                }
                listenerInfo.listener.asBinder().unlinkToDeath(listenerInfo, 0);
                return true;
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x007b, code lost:
    
        if (m398$$Nest$misAllowedInternal(r11, r17, r14 == 3 || r14 == 4) == false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDesktopMode(com.android.server.desktopmode.StateManager.InternalState r17, boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 680
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.DesktopModeService.updateDesktopMode(com.android.server.desktopmode.StateManager$InternalState, boolean, boolean):void");
    }

    public final void updateNewDexMode(int i) {
        if (!DesktopModeFeature.SUPPORT_NEW_DEX) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]DesktopModeService", "updateNewDexMode Failed : New Dex Feature Not Supported");
                return;
            }
            return;
        }
        if (((StateManager) this.mStateManager).getState().mCurrentUserId != 0) {
            Context context = this.mContext;
            ToastManager.showToast(context, context.getString(R.string.httpErrorLookup), 1);
            return;
        }
        EmergencyModeBlocker emergencyModeBlocker = this.mEmergencyModeBlocker;
        if (emergencyModeBlocker.mEnabledInBroadcast || emergencyModeBlocker.mEmergencyModeEnabledInSettings || emergencyModeBlocker.mMpsmEnabledInSettings || emergencyModeBlocker.mLimitAppsAndHomeScreenEnabledInSettings) {
            Log.d("[DMS]DesktopModeService", "New Dex Blocked, name = " + this.mEmergencyModeBlocker.toString());
            ToastManager.showToast(this.mContext, this.mEmergencyModeBlocker.onBlocked(), 1);
            return;
        }
        RoleManager roleManager = (RoleManager) this.mContext.getSystemService("role");
        this.mStandaloneModeChanger.getClass();
        String str = null;
        if (roleManager != null) {
            List roleHoldersAsUser = roleManager.getRoleHoldersAsUser("android.app.role.HOME", Process.myUserHandle());
            if (!roleHoldersAsUser.isEmpty()) {
                str = (String) roleHoldersAsUser.get(0);
            }
        }
        if (!KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str)) {
            Context context2 = this.mContext;
            ToastManager.showToast(context2, context2.getString(R.string.scNullCipherIssueNonEncryptedSummary), 1);
        } else if (i == 1) {
            Settings.System.putInt(this.mResolver, "new_dex", 1);
        } else {
            if (i != 2) {
                return;
            }
            Settings.System.putInt(this.mResolver, "new_dex", 0);
        }
    }
}
