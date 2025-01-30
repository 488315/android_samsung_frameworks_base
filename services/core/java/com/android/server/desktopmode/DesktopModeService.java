package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.app.role.RoleManager;
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
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.util.IndentingPrintWriter;
import com.android.internal.util.DumpUtils;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.am.ActivityManagerService;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
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
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class DesktopModeService extends IDesktopMode.Stub {
  public static final String TAG = "[DMS]" + DesktopModeService.class.getSimpleName();
  public ActivityManagerService mActivityManagerService;
  public ActivityTaskManagerInternal mActivityTaskManagerInternal;
  public ActivityTaskManagerService mActivityTaskManagerService;
  public boolean mAllowPogoInitialDialog;
  public BleAdvertiserServiceManager mBleAdvertiserServiceManager;
  public BlockerManager mBlockerManager;
  public BootInitBlocker mBootInitBlocker;
  public final Context mContext;
  public CoverStateManager mCoverStateManager;
  public int mCurrentUserId;
  public DexManager mDexManager;
  public DisplayPortStateManager mDisplayPortStateManager;
  public DockManager mDockManager;
  public DualModeChanger mDualModeChanger;
  public EmergencyModeBlocker mEmergencyModeBlocker;
  public boolean mEnteredViaWirelessDex;
  public final SettingsHelper.OnSettingChangedListener mExternalDisplayModeListener;
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
  public final ContentObserver mNewDeXSettingObserver;
  public PackageStateManager mPackageStateManager;
  public final PreconditionChecker mPreconditionChecker;
  public final ContentResolver mResolver;
  public final SemDesktopModeStateNotifier mSemDesktopModeStateNotifier;
  public SettingsHelper mSettingsHelper;
  public boolean mShowModeChangeScreen;
  public StandaloneModeChanger mStandaloneModeChanger;
  public final StateManager.StateListener mStateListener;
  public final IStateManager mStateManager;
  public StatusBarManager mStatusBarManager;
  public TouchpadManager mTouchpadManager;
  public UiManager mUiManager;
  public int mUserRequest;
  public ContentObserver mUserSetupCompleteObserver;
  public WelcomeActivityListener mWelcomeActivityListener;
  public final SettingsHelper.OnSettingChangedListener mWelcomeCompletedSettingChangedListener;
  public WirelessDexManager mWirelessDexManager;
  public int mWirelessDexSetting;

  public interface PreconditionChecker {
    boolean isAllowed(State state, boolean z, boolean z2);

    boolean isDesktopModeAvailableEx(boolean z, boolean z2);

    boolean updateDesktopMode(State state, boolean z, boolean z2);
  }

  /* renamed from: com.android.server.desktopmode.DesktopModeService$1 */
  public class C11071 extends StateManager.StateListener {
    public C11071() {}

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onBootCompleted() {
      DesktopModeService.this.mHwManager.initialize();
      DesktopModeService.this.mCoverStateManager.initialize();
      State state = DesktopModeService.this.mStateManager.getState();
      if (DesktopModeFeature.SUPPORT_STANDALONE) {
        DesktopModeService.this.handleHdmiAutoEnter(state.isExternalDisplayConnected());
        DesktopModeService.this.handlePogoAutoEnter(state.isPogoKeyboardConnected());
        DesktopModeService.this.mHandler.postDelayed(
            new Runnable() { // from class:
              // com.android.server.desktopmode.DesktopModeService$1$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                DesktopModeService.C11071.this.lambda$onBootCompleted$0();
              }
            },
            5000L);
        DesktopModeService.this.setEnterRequestIfStandaloneLastBoot(state);
      }
      DesktopModeService.this.scheduleUpdateDesktopMode(true);
      DesktopModeService.this.requestTileUpdate(state.getDesktopModeState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$0() {
      DesktopModeService.this.mAllowPogoInitialDialog = true;
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onBootInitBlockerRegistered(boolean z) {
      DesktopModeService.this.mIsBootInitBlockerRegistered = z;
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onExternalDisplayConnectionChanged(State state) {
      boolean settings =
          DesktopModeSettings.getSettings(
              DesktopModeService.this.mResolver, "welcome_completed", false);
      if (DesktopModeFeature.SUPPORT_STANDALONE) {
        if (state.isWirelessDexConnected()) {
          DesktopModeService.this.handleWirelessDexEnter(settings);
        } else {
          DesktopModeService.this.handleHdmiAutoEnter(state.isHdmiConnected());
          DesktopModeService.this.scheduleUpdateDesktopMode(state.isHdmiConnected());
        }
        if (state.isHdmiConnected()) {
          return;
        }
        DesktopModeService.this.mShowModeChangeScreen = false;
        return;
      }
      if (state.isHdmiConnected()
          && (DesktopModeSettings.getSettings(
                  DesktopModeService.this.mResolver, "hdmi_auto_enter", false)
              || !settings)) {
        DesktopModeService.this.setUserRequest(1);
      }
      DesktopModeService.this.scheduleUpdateDesktopMode(state.isExternalDisplayConnected());
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onExternalDisplayUpdated(State state) {
      if (DesktopModeService.isConnectionTypeChanged(state)) {
        DesktopModeService.this.scheduleUpdateDesktopMode(false, true);
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDockStateChanged(State state) {
      if (state.getDockState().isDexPad()) {
        return;
      }
      DesktopModeService.this.dismissDialog(3);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDockLowChargerPowerChanged(State state) {
      if (DesktopModeService.isDexPadConnected(state)) {
        if (state.isDockLowChargerConnected()) {
          DesktopModeService.this.showDialog(3, null);
        } else {
          DesktopModeService.this.dismissDialog(3);
        }
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onPackageStateChanged(State state) {
      DesktopModeService.this.scheduleUpdateDesktopMode(state.isPackagesAvailable());
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onLauncherPackageReplaced(boolean z) {
      State state = DesktopModeService.this.mStateManager.getState();
      if (z) {
        DesktopModeService.this.mSettingsHelper.clearSettingsByLauncherDataCleared(
            state, DesktopModeService.this.mCurrentUserId);
      }
      DesktopModeService.this.startHome(state);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onPogoKeyboardConnectionChanged(State state) {
      if (DesktopModeFeature.SUPPORT_STANDALONE) {
        DesktopModeService.this.handlePogoAutoEnter(state.isPogoKeyboardConnected());
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDesktopModeStateChanged(State state) {
      SemDesktopModeState desktopModeState = state.getDesktopModeState();
      if (DesktopModeFeature.SUPPORT_WIRELESS_DEX
          && DesktopModeService.this.mEnteredViaWirelessDex) {
        int i = DesktopModeService.this.mWirelessDexSetting;
        int i2 = desktopModeState.enabled;
        if (i != i2) {
          DesktopModeService.this.mWirelessDexSetting = i2;
          DesktopModeSettings.setSettings(
              DesktopModeService.this.mResolver, "wireless_dex", desktopModeState.enabled);
        }
        if (desktopModeState.compareTo(2, 0, 0)) {
          DesktopModeService.this.mEnteredViaWirelessDex = false;
        }
      }
      DesktopModeService.this.requestTileUpdate(desktopModeState);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onForcedInternalScreenStateChanged(State state) {
      DesktopModeService.this.setUserRequest(state.isForcedInternalScreenModeEnabled() ? 1 : 2);
      DesktopModeService.this.scheduleUpdateDesktopMode(state.isForcedInternalScreenModeEnabled());
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onStopLoadingScreen(boolean z) {
      if (DesktopModeService.this.mModeToModeChangeInfo != null) {
        DesktopModeService.this.setUserRequest(1);
      } else {
        DesktopModeService.this.setUserRequest(0);
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDualModeStopLoadingScreen(boolean z) {
      if (DesktopModeService.this.mModeToModeChangeInfo != null) {
        DesktopModeService.this.setUserRequest(1);
      } else {
        DesktopModeService.this.setUserRequest(0);
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onScheduleUpdateDesktopMode(boolean z) {
      DesktopModeService.this.scheduleUpdateDesktopMode(z);
    }
  }

  public final void setEnterRequestIfStandaloneLastBoot(State state) {
    if (DesktopModeSettings.getSettingsAsUser(
            this.mResolver, "display_type", 0, this.mCurrentUserId)
        == 101) {
      Log.m10i(
          TAG,
          "Request to enter standalone mode, since the standalone mode was enabled on last boot");
      setUserRequest(1);
    }
    DesktopModeSettings.setSettingsAsUser(
        this.mResolver,
        "display_type",
        state.getDesktopModeState().getDisplayType(),
        this.mCurrentUserId);
  }

  public final void requestTileUpdate(SemDesktopModeState semDesktopModeState) {
    boolean z =
        semDesktopModeState != null
            && (semDesktopModeState.getEnabled() == 4 || semDesktopModeState.getEnabled() == 3);
    boolean z2 = Settings.System.getInt(this.mResolver, "new_dex", 0) == 1;
    if (this.mIsDesktopModeEnablingOrEnabled == z && this.mIsNewDexEnabled == z2) {
      return;
    }
    this.mIsDesktopModeEnablingOrEnabled = z;
    this.mIsNewDexEnabled = z2;
    TileService.requestListeningState(
        this.mContext,
        new ComponentName(
            "com.sec.android.desktopmode.uiservice",
            "com.sec.android.desktopmode.uiservice.DesktopModeTile"));
    TileService.requestListeningState(
        this.mContext,
        new ComponentName(
            "com.sec.android.desktopmode.uiservice",
            "com.sec.android.desktopmode.uiservice.DesktopDisplayDesktopModeTile"));
  }

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
    public void onStart() {
      publishBinderService("desktopmode", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
      DisplayPortStateManager displayPortStateManager;
      WirelessDexManager wirelessDexManager;
      WirelessDexManager wirelessDexManager2;
      BleAdvertiserServiceManager bleAdvertiserServiceManager;
      if (i == 500) {
        DesktopModeService desktopModeService = this.mService;
        ActivityManagerService activityManagerService =
            (ActivityManagerService) this.mInjector.get(ActivityManagerService.class);
        ActivityTaskManagerService activityTaskManagerService =
            (ActivityTaskManagerService) this.mInjector.get(ActivityTaskManagerService.class);
        ActivityTaskManagerInternal activityTaskManagerInternal =
            (ActivityTaskManagerInternal) this.mInjector.get(ActivityTaskManagerInternal.class);
        BlockerManager blockerManager = (BlockerManager) this.mInjector.get(BlockerManager.class);
        EmergencyModeBlocker emergencyModeBlocker =
            (EmergencyModeBlocker) this.mInjector.get(EmergencyModeBlocker.class);
        UiManager uiManager = (UiManager) this.mInjector.get(UiManager.class);
        SettingsHelper settingsHelper = (SettingsHelper) this.mInjector.get(SettingsHelper.class);
        HardwareManager hardwareManager =
            (HardwareManager) this.mInjector.get(HardwareManager.class);
        TouchpadManager touchpadManager =
            (TouchpadManager) this.mInjector.get(TouchpadManager.class);
        CoverStateManager coverStateManager =
            (CoverStateManager) this.mInjector.get(CoverStateManager.class);
        MultiResolutionManager multiResolutionManager =
            (MultiResolutionManager) this.mInjector.get(MultiResolutionManager.class);
        DockManager dockManager = (DockManager) this.mInjector.get(DockManager.class);
        PackageStateManager packageStateManager =
            (PackageStateManager) this.mInjector.get(PackageStateManager.class);
        StatusBarManager statusBarManager =
            (StatusBarManager) this.mInjector.get(StatusBarManager.class);
        BootInitBlocker bootInitBlocker =
            DesktopModeFeature.SUPPORT_STANDALONE
                ? (BootInitBlocker) this.mInjector.get(BootInitBlocker.class)
                : null;
        DisplayPortStateManager displayPortStateManager2 =
            (DisplayPortStateManager) this.mInjector.get(DisplayPortStateManager.class);
        if (DesktopModeFeature.SUPPORT_WIRELESS_DEX || DesktopModeFeature.SUPPORT_DEX_ON_PC) {
          displayPortStateManager = displayPortStateManager2;
          wirelessDexManager = (WirelessDexManager) this.mInjector.get(WirelessDexManager.class);
        } else {
          displayPortStateManager = displayPortStateManager2;
          wirelessDexManager = null;
        }
        if (DesktopModeFeature.SUPPORT_WIRELESS_DEX) {
          wirelessDexManager2 = wirelessDexManager;
          bleAdvertiserServiceManager =
              (BleAdvertiserServiceManager) this.mInjector.get(BleAdvertiserServiceManager.class);
        } else {
          wirelessDexManager2 = wirelessDexManager;
          bleAdvertiserServiceManager = null;
        }
        desktopModeService.injectServices(
            activityManagerService,
            activityTaskManagerService,
            activityTaskManagerInternal,
            blockerManager,
            emergencyModeBlocker,
            uiManager,
            settingsHelper,
            hardwareManager,
            touchpadManager,
            coverStateManager,
            multiResolutionManager,
            dockManager,
            packageStateManager,
            statusBarManager,
            bootInitBlocker,
            displayPortStateManager,
            wirelessDexManager2,
            bleAdvertiserServiceManager,
            (DualModeChanger) this.mInjector.get(DualModeChanger.class),
            (StandaloneModeChanger) this.mInjector.get(StandaloneModeChanger.class),
            (McfManager) this.mInjector.get(McfManager.class),
            (DexManager) this.mInjector.get(DexManager.class));
      }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
      this.mService.onUserStarting(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
      this.mService.onUserUnlocking(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(
        SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
      this.mService.onUserSwitching(targetUser2.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
      this.mService.onUserStopping(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
      this.mService.onUserStopped(targetUser.getUserIdentifier());
    }
  }

  public class Receiver extends BroadcastReceiver {
    public Receiver() {}

    public void register() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
      intentFilter.addAction(
          "com.samsung.android.desktopmode.action.DEX_RUNNING_NOTIFICATION_PRESSED");
      intentFilter.addAction("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE");
      DesktopModeService.this.mContext.registerReceiverAsUser(
          this, UserHandle.ALL, intentFilter, null, null);
      DesktopModeService.this.mContext.registerReceiverAsUser(
          this,
          UserHandle.ALL,
          new IntentFilter("com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST"),
          "android.permission.WRITE_SECURE_SETTINGS",
          null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(DesktopModeService.TAG, "onReceive(), action=" + action);
      }
      State state = DesktopModeService.this.mStateManager.getState();
      if ("com.samsung.android.desktopmode.action.DESKTOP_MODE_UPDATE_REQUEST".equals(action)) {
        DesktopModeService.this.logDesktopModeChanged(intent);
        int intExtra =
            intent.getIntExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_SOURCE", -1);
        int intExtra2 =
            intent.getIntExtra("com.samsung.android.desktopmode.extra.NEW_DEX_MODE_STATE", -1);
        if (intExtra2 > -1) {
          DesktopModeService.this.updateNewDexMode(intExtra2);
          return;
        }
        int intExtra3 =
            intent.getIntExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_STATE", -1);
        if (intExtra3 == 0) {
          DesktopModeService.this.mStatusBarManager.collapsePanels();
          DesktopModeService.this.scheduleUpdateDesktopMode(true);
          return;
        }
        if (intExtra3 != 1) {
          if (intExtra3 != 2) {
            return;
          }
          DesktopModeService.this.setUserRequest(2);
          if (DesktopModeService.this.shouldStayInDesktopMode(state)) {
            return;
          }
          DesktopModeService.this.mStatusBarManager.collapsePanels();
          if (DesktopModeFeature.SUPPORT_WIRELESS_DEX && state.isWirelessDexConnected()) {
            DesktopModeService.this.minimizeAllTasks(state.getDesktopDisplayId());
            DesktopModeService.this.mWirelessDexManager.disconnect();
            return;
          } else if (state.isDexOnPcConnected()) {
            DesktopModeService.this.mStateManager.notifyDisplayDisconnectionRequest(1000);
            return;
          } else {
            DesktopModeService.this.scheduleUpdateDesktopMode(false);
            return;
          }
        }
        DesktopModeService.this.mStatusBarManager.collapsePanels();
        boolean settings =
            DesktopModeSettings.getSettings(
                DesktopModeService.this.mResolver, "welcome_completed", false);
        boolean isNeededStartConnectivityActivity =
            DesktopModeService.this.isNeededStartConnectivityActivity(state, intExtra);
        if (!DesktopModeFeature.SUPPORT_WIRELESS_DEX
            || (!state.isWirelessDexConnected() && !isNeededStartConnectivityActivity)) {
          DesktopModeService.this.setUserRequest(1);
          DesktopModeService.this.mIsDexSourceEntry = true;
          DesktopModeService.this.scheduleUpdateDesktopMode(true);
          return;
        } else if (isNeededStartConnectivityActivity) {
          DesktopModeService.this.startConnectivityActivity();
          return;
        } else {
          if (settings) {
            return;
          }
          DesktopModeSettings.setSettings(
              DesktopModeService.this.mResolver, "welcome_completed", true);
          return;
        }
      }
      if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(DesktopModeService.TAG, "Shutdown received with UserId: " + getSendingUserId());
        }
        if (getSendingUserId() == -1) {
          DesktopModeService.this.mIsBootComplete = false;
          return;
        }
        return;
      }
      if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
        if (Utils.isRetailMode(
            DesktopModeService.this.mContext, DesktopModeService.this.mCurrentUserId)) {
          DesktopModeService.this.mSettingsHelper.clearSettingsByLauncherDataCleared(
              state, DesktopModeService.this.mCurrentUserId);
          DesktopModeService.this.mSettingsHelper.setDefaultSettingsInRetailMode(
              state, DesktopModeService.this.mCurrentUserId);
          return;
        }
        return;
      }
      if ("com.samsung.android.desktopmode.action.DEX_RUNNING_NOTIFICATION_PRESSED"
          .equals(action)) {
        DesktopModeService.this.mStatusBarManager.collapsePanels();
        DesktopModeService.this.startConnectivityActivity(intent.getIntExtra("CLICK_DISPLAYID", 0));
      } else if ("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE".equals(action)) {
        DesktopModeService.this.setEnterRequestIfStandaloneLastBoot(state);
        DesktopModeService.this.scheduleUpdateDesktopMode(true);
      }
    }
  }

  public class PhonePreconditionChecker implements PreconditionChecker {
    public PhonePreconditionChecker() {}

    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    public boolean isAllowed(State state, boolean z, boolean z2) {
      return DesktopModeService.this.isSystemReady()
          && DesktopModeService.this.isBlockerRegistered(state, z2)
          && state.isPackagesAvailable();
    }

    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    public boolean isDesktopModeAvailableEx(boolean z, boolean z2) {
      State state = DesktopModeService.this.mStateManager.getState();
      boolean z3 = false;
      if ((!z || DesktopModeService.isExternalDisplayConnectedOrForced(state))
          && ((!z2 || DesktopModeService.isDesktopDockConnectedOrForced(state))
              && DesktopModeSettings.getSettings(
                  DesktopModeService.this.mResolver, "hdmi_auto_enter", false))) {
        z3 = true;
      }
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            DesktopModeService.TAG,
            "isDesktopModeAvailableEx(checkExternalDisplay="
                + z
                + ", checkDesktopDock="
                + z2
                + ")="
                + z3);
      }
      return z3;
    }

    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    public boolean updateDesktopMode(State state, boolean z, boolean z2) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            DesktopModeService.TAG,
            "updateDesktopMode(), enter="
                + z
                + ", isModeChangeLocked="
                + state.isModeChangeLocked()
                + ", mUserRequest="
                + DesktopModeService.userRequestToString(DesktopModeService.this.mUserRequest));
      }
      if (DesktopModeService.this.isSystemReady() && !state.isModeChangeLocked()) {
        SemDesktopModeState desktopModeState = state.getDesktopModeState();
        if (z) {
          if ((desktopModeState.compareTo(2, 0, 0) || desktopModeState.compareTo(2, 10, 102))
              && DesktopModeService.isExternalDisplayConnectedOrForced(state)
              && DesktopModeService.this.isAllowedInternal(state, true, true)) {
            boolean settings =
                DesktopModeSettings.getSettings(
                    DesktopModeService.this.mResolver, "hdmi_auto_enter", false);
            boolean settings2 =
                DesktopModeSettings.getSettings(
                    DesktopModeService.this.mResolver, "welcome_completed", false);
            boolean settings3 =
                DesktopModeSettings.getSettings(
                    DesktopModeService.this.mResolver, "welcome_shown", false);
            if ((DesktopModeService.this.mUserRequest == 1 && settings2)
                || state.isDexOnPcConnected()
                || (DesktopModeFeature.SUPPORT_WIRELESS_DEX
                    && state.isWirelessDexConnected()
                    && settings2)) {
              if (DesktopModeFeature.SUPPORT_WIRELESS_DEX && state.isWirelessDexConnected()) {
                DesktopModeService.this.mEnteredViaWirelessDex = true;
              }
              DesktopModeService.this.mDualModeChanger.setDesktopMode(state, true);
              return true;
            }
            if (!settings2
                && (state.isDexStationConnectedWithFlipCover() || state.isDexOnPcConnected())) {
              DesktopModeSettings.setSettings(
                  DesktopModeService.this.mResolver, "welcome_completed", true);
              return true;
            }
            if (!settings2
                && (!settings3
                    || DesktopModeService.this.mIsDexSourceEntry
                    || settings
                    || (DesktopModeFeature.SUPPORT_WIRELESS_DEX
                        && state.isWirelessDexConnected()))) {
              DesktopModeService.this.startWelcomeActivity();
              return true;
            }
            Log.m7d(
                DesktopModeService.TAG,
                "updateDesktopMode(welcomeCompleted=true, userRequest="
                    + DesktopModeService.userRequestToString(DesktopModeService.this.mUserRequest)
                    + ")");
            return false;
          }
        } else {
          DesktopModeService.this.mIsDexSourceEntry = false;
          if (z2
              || DesktopModeService.this.mUserRequest == 2
              || !DesktopModeService.isExternalDisplayConnectedOrForced(state)
              || !DesktopModeService.this.isAllowedInternal(state, true, false)) {
            DesktopModeService.this.finishWelcomeActivity();
            if (desktopModeState.compareTo(4, 0, 102)) {
              DesktopModeService.this.mDualModeChanger.setDesktopMode(state, false);
              return true;
            }
          }
        }
      }
      return false;
    }
  }

  public class TabletPreconditionChecker implements PreconditionChecker {
    public TabletPreconditionChecker() {}

    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    public boolean isAllowed(State state, boolean z, boolean z2) {
      if (!DesktopModeService.this.isSystemReady()) {
        return false;
      }
      if (!state.isExternalDisplayConnected() || !state.isEmergencyModeEnabled()) {
        return DesktopModeService.this.isBlockerRegistered(state, z2)
            && state.isPackagesAvailable();
      }
      DesktopModeService.this.isBlockerRegistered(state, z2);
      return false;
    }

    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    public boolean isDesktopModeAvailableEx(boolean z, boolean z2) {
      boolean isAllowed = isAllowed(DesktopModeService.this.mStateManager.getState(), false, false);
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            DesktopModeService.TAG,
            "isDesktopModeAvailable(checkExternalDisplay="
                + z
                + ", checkDesktopDock="
                + z2
                + ")="
                + isAllowed);
      }
      return isAllowed;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0070, code lost:

       if (r12.isAllowedInternal(r11, true, r12.isDesktopModeEnablingOrEnabled()) == false) goto L16;
    */
    @Override // com.android.server.desktopmode.DesktopModeService.PreconditionChecker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateDesktopMode(State state, boolean z, boolean z2) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            DesktopModeService.TAG,
            "updateDesktopMode(), enter="
                + z
                + ", isModeChangeLocked="
                + state.isModeChangeLocked());
      }
      boolean z3 = false;
      if (DesktopModeService.this.isSystemReady() && !state.isModeChangeLocked()) {
        SemDesktopModeState desktopModeState = state.getDesktopModeState();
        boolean equals =
            "dual"
                .equals(
                    DesktopModeSettings.getSettings(
                        DesktopModeService.this.mContext.getContentResolver(),
                        "external_display_mode",
                        "dual"));
        ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = null;
        if (!z) {
          if (DesktopModeService.this.mUserRequest != 2) {
            DesktopModeService desktopModeService = DesktopModeService.this;
          }
          if (desktopModeState.compareTo(4, 0, 101)) {
            DesktopModeService.this.mStandaloneModeChanger.setDesktopMode(state, false, null);
          } else {
            if (desktopModeState.compareTo(4, 0, 102)) {
              DesktopModeService.this.mDualModeChanger.setDesktopMode(state, false);
            }
            DesktopModeService.this.mModeToModeChangeInfo = modeToModeChangeInfo;
          }
          z3 = true;
          DesktopModeService.this.mModeToModeChangeInfo = modeToModeChangeInfo;
        }
        boolean settings =
            DesktopModeSettings.getSettings(
                DesktopModeService.this.mResolver, "welcome_completed", false);
        if (DesktopModeFeature.SUPPORT_WIRELESS_DEX && state.isWirelessDexConnected()) {
          DesktopModeService.this.mEnteredViaWirelessDex = true;
        }
        DesktopModeService desktopModeService2 = DesktopModeService.this;
        if (desktopModeService2.isAllowedInternal(
            state,
            true,
            desktopModeService2.mUserRequest == 1
                || DesktopModeService.this.mEnteredViaWirelessDex)) {
          if (!settings && DesktopModeService.this.mUserRequest == 1) {
            DesktopModeService.this.startWelcomeActivity();
          } else if (DesktopModeService.isExternalDisplayConnectedOrForced(state)) {
            if (desktopModeState.compareTo(4, 0, 101)
                && (equals || DesktopModeService.this.mEnteredViaWirelessDex)) {
              DesktopModeService.this.mUiManager.showOverlay(103, 115);
              modeToModeChangeInfo =
                  DesktopModeService.this.mStandaloneModeChanger.setDesktopMode(
                      state,
                      false,
                      new ModeChanger.ModeToModeChangeInfo(
                          DesktopModeService.this.mShowModeChangeScreen, 2));
            } else if (desktopModeState.compareTo(2, 0, 0)
                || desktopModeState.compareTo(2, 10, 102)) {
              if (DesktopModeService.this.mEnteredViaWirelessDex
                  || ((DesktopModeService.this.mUserRequest == 1 && equals)
                      || (DesktopModeService.this.mModeToModeChangeInfo != null
                          && DesktopModeService.this.mModeToModeChangeInfo.getModeToMode() == 2))) {
                DesktopModeService desktopModeService3 = DesktopModeService.this;
                desktopModeService3.mModeChanger = desktopModeService3.mDualModeChanger;
                DesktopModeService.this.mDualModeChanger.setDesktopMode(
                    state, true, DesktopModeService.this.mModeToModeChangeInfo);
              } else if (DesktopModeService.this.mUserRequest == 1) {
                DesktopModeService desktopModeService4 = DesktopModeService.this;
                desktopModeService4.mModeChanger = desktopModeService4.mStandaloneModeChanger;
                DesktopModeService.this.mStandaloneModeChanger.setDesktopMode(
                    state, true, DesktopModeService.this.mModeToModeChangeInfo);
              }
            } else if (desktopModeState.compareTo(4, 0, 102)
                && !equals
                && !DesktopModeService.this.mEnteredViaWirelessDex) {
              DesktopModeService.this.mUiManager.showOverlay(103, 115);
              modeToModeChangeInfo =
                  DesktopModeService.this.mDualModeChanger.setDesktopMode(
                      state,
                      false,
                      new ModeChanger.ModeToModeChangeInfo(
                          DesktopModeService.this.mShowModeChangeScreen, 1));
            }
            z3 = true;
          } else {
            if (desktopModeState.enabled == 2 && DesktopModeService.this.mUserRequest == 1) {
              DesktopModeService desktopModeService5 = DesktopModeService.this;
              desktopModeService5.mModeChanger = desktopModeService5.mStandaloneModeChanger;
              DesktopModeService.this.mStandaloneModeChanger.setDesktopMode(
                  state, true, DesktopModeService.this.mModeToModeChangeInfo);
            } else if (desktopModeState.compareTo(4, 0, 102)) {
              DesktopModeService.this.scheduleUpdateDesktopMode(false);
              DesktopModeService.this.mDualModeChanger.setDesktopMode(state, false);
            }
            z3 = true;
          }
        }
        DesktopModeService.this.mModeToModeChangeInfo = modeToModeChangeInfo;
      }
      return z3;
    }
  }

  public class WelcomeActivityListener extends UiManager.InternalUiCallback {
    public boolean mOverlayShown;
    public boolean mShown;
    public boolean mStartPressed;
    public StateManager.StateListener mStateListener;

    public WelcomeActivityListener() {
      this.mShown = false;
      this.mOverlayShown = false;
      this.mStartPressed = false;
      this.mStateListener =
          new StateManager.StateListener() { // from class:
            // com.android.server.desktopmode.DesktopModeService.WelcomeActivityListener.1
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onExternalDisplayConnectionChanged(State state) {
              WelcomeActivityListener.this.showOrDismissOverlay(
                  state.isExternalDisplayConnected(), state.isCoverSupportStatePartial());
              WelcomeActivityListener.this.setDesktopModeState(
                  state.isExternalDisplayConnected(), 2, 10);
            }
          };
    }

    public final void showOrDismissOverlay(boolean z, boolean z2) {
      if (z && !this.mOverlayShown) {
        if (DesktopModeService.this.mCoverStateManager.isFlipTypeCoverClosed()) {
          if (DesktopModeFeature.DEBUG) {
            Log.m7d(
                DesktopModeService.TAG,
                "showOrDismissOverlay(), show=true, isCoverSupportStatePartial="
                    + z2
                    + ", skip showing external overlay since cover is closed");
            return;
          }
          return;
        }
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(
              DesktopModeService.TAG,
              "showOrDismissOverlay(), show=true, isCoverSupportStatePartial="
                  + z2
                  + ", showing external overlay");
        }
        this.mOverlayShown = true;
        DesktopModeService.this.mUiManager.showOverlay(103, 112);
        if (DesktopModeFeature.IS_TABLET || !z2) {
          return;
        }
        long binderClearCallingIdentity =
            DesktopModeService.this.mInjector.binderClearCallingIdentity();
        DesktopModeService.this.mCoverStateManager.disableCoverManager(true);
        DesktopModeService.this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        return;
      }
      if (z || !this.mOverlayShown) {
        return;
      }
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            DesktopModeService.TAG,
            "showOrDismissOverlay(), show=false, isCoverSupportStatePartial="
                + z2
                + ", dismissing external overlay");
      }
      this.mOverlayShown = false;
      DesktopModeService.this.mUiManager.dismissOverlay(103, 112);
      if (DesktopModeFeature.IS_TABLET) {
        return;
      }
      long binderClearCallingIdentity2 =
          DesktopModeService.this.mInjector.binderClearCallingIdentity();
      DesktopModeService.this.mCoverStateManager.disableCoverManager(false);
      DesktopModeService.this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity2);
    }

    @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
    public void onShow() {
      DesktopModeService.this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.desktopmode.DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              DesktopModeService.WelcomeActivityListener.this.handleOnShow();
            }
          });
    }

    @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
    public void onDismiss() {
      DesktopModeService.this.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.desktopmode.DesktopModeService$WelcomeActivityListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DesktopModeService.WelcomeActivityListener.this.handleOnDismiss();
            }
          });
    }

    @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
    public void onClickButtonPositive() {
      this.mStartPressed = true;
      DesktopModeService.this.finishWelcomeActivity();
      State state = DesktopModeService.this.mStateManager.getState();
      if (state.isHdmiConnected()) {
        DesktopModeSettings.setSettings(
            DesktopModeService.this.mResolver, "hdmi_initial_connection_dialog_shown", true);
      }
      if (state.isPogoKeyboardConnected()) {
        DesktopModeSettings.setSettings(
            DesktopModeService.this.mResolver, "pogo_initial_connection_dialog_shown", true);
      }
    }

    public final void handleOnShow() {
      this.mShown = true;
      State state = DesktopModeService.this.mStateManager.getState();
      showOrDismissOverlay(state.isExternalDisplayConnected(), state.isCoverSupportStatePartial());
      SemDesktopModeState desktopModeState = state.getDesktopModeState();
      if (this.mShown && desktopModeState.compareTo(2, 0)) {
        setDesktopModeState(state.isExternalDisplayConnected(), 2, 10);
      }
      DesktopModeService.this.mStateManager.registerListener(this.mStateListener);
    }

    public final void handleOnDismiss() {
      this.mShown = false;
      DesktopModeService.this.mStateManager.unregisterListener(this.mStateListener);
      showOrDismissOverlay(false, false);
      State state = DesktopModeService.this.mStateManager.getState();
      if (!this.mShown && state.getDesktopModeState().compareTo(2, 10)) {
        setDesktopModeState(state.isExternalDisplayConnected(), 2, 0);
      }
      if (DesktopModeFeature.SUPPORT_WIRELESS_DEX
          && state.isWirelessDexConnected()
          && !this.mStartPressed) {
        DesktopModeService.this.mWirelessDexManager.disconnect();
      }
      this.mStartPressed = false;
    }

    public final void setDesktopModeState(boolean z, int i, int i2) {
      ((!DesktopModeFeature.SUPPORT_STANDALONE || z)
              ? DesktopModeService.this.mDualModeChanger
              : DesktopModeService.this.mStandaloneModeChanger)
          .setDesktopModeState(i, i2);
    }
  }

  public final class LocalService extends DesktopModeManagerInternal {
    public LocalService() {}

    public void scheduleUpdateDesktopMode(boolean z) {
      DesktopModeService.this.scheduleUpdateDesktopMode(z);
    }

    public SemDesktopModeState getDesktopModeState() {
      return DesktopModeService.this.getDesktopModeState();
    }

    public boolean isDesktopModeForPreparing() {
      return DesktopModeService.this.isDesktopModeForPreparing();
    }

    public boolean isDesktopModeForPreparing(int i) {
      return DesktopModeService.this.isDesktopModeForPreparing(i);
    }

    public boolean isExternalDisplayConnected() {
      return DesktopModeService.this.isExternalDisplayConnected();
    }

    public Bundle getDesktopModeKillPolicy() {
      return DesktopModeService.this.getDesktopModeKillPolicy();
    }

    public Bundle sendMessage(Bundle bundle) {
      return DesktopModeService.this.sendMessage(bundle);
    }

    public boolean isDesktopModeEnablingOrEnabled() {
      return DesktopModeService.this.isDesktopModeEnablingOrEnabled();
    }

    public boolean isDesktopModeAvailableEx(boolean z, boolean z2) {
      return DesktopModeService.this.isDesktopModeAvailableEx(z, z2);
    }

    public boolean isForcedInternalScreenModeEnabled() {
      return DesktopModeService.this.isForcedInternalScreenModeEnabled();
    }

    public boolean isModeChangePending() {
      return DesktopModeService.this.isModeChangePending();
    }

    public boolean isModeChangePending(int i) {
      return DesktopModeService.this.isModeChangePending(i);
    }

    public void onConfigurationChanged(Configuration configuration) {
      DesktopModeService.this.onConfigurationChanged(configuration);
    }

    public void onDesktopDisplayConfigured(boolean z) {
      DesktopModeService.this.onDesktopDisplayConfigured(z);
    }

    public int getCurrentUiMode() {
      return DesktopModeService.this.getCurrentUiMode();
    }

    public boolean isLockTaskModeEnabledAndSecured() {
      return DesktopModeService.this.isLockTaskModeEnabledAndSecured();
    }

    public boolean isConfigurationChangedFromDeX(Configuration configuration) {
      return DesktopModeService.this.isConfigurationChangedFromDeX(configuration);
    }

    public void startHome() {
      DesktopModeService.this.startHome();
    }

    public int setDexHDMIAutoEnterState(int i) {
      ContentResolver contentResolver = DesktopModeService.this.mContext.getContentResolver();
      try {
        if (i == 0) {
          String settingsOrThrowException =
              DesktopModeSettings.getSettingsOrThrowException(
                  contentResolver, "hdmi_auto_enter_backup", null);
          if (settingsOrThrowException != null) {
            DesktopModeSettings.setSettingsOrThrowException(
                contentResolver, "hdmi_auto_enter", settingsOrThrowException);
            DesktopModeSettings.deleteSettings(contentResolver, "hdmi_auto_enter_backup");
          }
          DesktopModeSettings.setSettingsOrThrowException(
              contentResolver, "knox_hdmi_auto_enter_state", i);
          return 0;
        }
        if (i != 1) {
          if (i != 2) {
            return 0;
          }
          DesktopModeSettings.deleteSettings(
              contentResolver, "hdmi_initial_connection_dialog_shown");
          DesktopModeSettings.deleteSettings(contentResolver, "hdmi_auto_enter");
          DesktopModeSettings.deleteSettings(contentResolver, "hdmi_auto_enter_backup");
          DesktopModeSettings.setSettingsOrThrowException(
              contentResolver, "knox_hdmi_auto_enter_state", i);
          return 0;
        }
        if (DesktopModeSettings.getSettingsOrThrowException(
                contentResolver, "hdmi_auto_enter_backup", null)
            != null) {
          return 0;
        }
        String settingsOrThrowException2 =
            DesktopModeSettings.getSettingsOrThrowException(
                contentResolver, "hdmi_auto_enter", null);
        if (settingsOrThrowException2 == null) {
          DesktopModeSettings.setSettingsOrThrowException(
              contentResolver, "hdmi_auto_enter_backup", "false");
        } else {
          DesktopModeSettings.setSettingsOrThrowException(
              contentResolver, "hdmi_auto_enter_backup", settingsOrThrowException2);
        }
        DesktopModeSettings.setSettingsOrThrowException(contentResolver, "hdmi_auto_enter", "true");
        DesktopModeSettings.setSettingsOrThrowException(
            contentResolver, "knox_hdmi_auto_enter_state", i);
        return 0;
      } catch (IllegalArgumentException unused) {
        Log.m8e(
            DesktopModeService.TAG,
            "setDexHDMIAutoEnterState(), Failed to setting(value=" + i + ")");
        return -1;
      }
    }

    public int getDexHDMIAutoEnterState() {
      return DesktopModeSettings.getSettings(
          DesktopModeService.this.mContext.getContentResolver(), "knox_hdmi_auto_enter_state", 9);
    }

    public int getTouchpadSupportedFeatures() {
      return DesktopModeService.this.getTouchpadSupportedFeatures();
    }

    public int getModeToModeChangeType() {
      return DesktopModeService.this.getModeToModeChangeType();
    }

    public void onSecuredAppLaunched(int i, String str) {
      DesktopModeService.this.onSecuredAppLaunched(i, str);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public DesktopModeService(
      Injector injector,
      Context context,
      ServiceThread serviceThread,
      SemDesktopModeStateNotifier semDesktopModeStateNotifier,
      IStateManager iStateManager) {
    PreconditionChecker phonePreconditionChecker;
    C11071 c11071 = new C11071();
    this.mStateListener = c11071;
    Object[] objArr = 0;
    Object[] objArr2 = 0;
    Object[] objArr3 = 0;
    Object[] objArr4 = 0;
    this.mNewDeXSettingObserver =
        new ContentObserver(
            null) { // from class: com.android.server.desktopmode.DesktopModeService.2
          @Override // android.database.ContentObserver
          public void onChange(boolean z) {
            DesktopModeService.this.requestTileUpdate(
                DesktopModeService.this.mStateManager.getState().getDesktopModeState());
          }
        };
    this.mWelcomeCompletedSettingChangedListener =
        new SettingsHelper.OnSettingChangedListener("welcome_completed") { // from class:
          // com.android.server.desktopmode.DesktopModeService.3
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            if (Boolean.parseBoolean(str)) {
              DesktopModeService.this.setUserRequest(1);
              DesktopModeService.this.scheduleUpdateDesktopMode(true);
            }
          }
        };
    this.mExternalDisplayModeListener =
        new SettingsHelper.OnSettingChangedListener("external_display_mode") { // from class:
          // com.android.server.desktopmode.DesktopModeService.4
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            boolean equals = "dual".equals(str);
            DesktopModeService desktopModeService = DesktopModeService.this;
            desktopModeService.mShowModeChangeScreen =
                desktopModeService.mStateManager.getState().isHdmiConnected();
            DesktopModeService.this.scheduleUpdateDesktopMode(equals);
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
    iStateManager.registerListener(c11071);
    if (DesktopModeFeature.SUPPORT_STANDALONE) {
      phonePreconditionChecker = new TabletPreconditionChecker();
    } else {
      phonePreconditionChecker = new PhonePreconditionChecker();
    }
    this.mPreconditionChecker = phonePreconditionChecker;
    injector.addLocalService(DesktopModeManagerInternal.class, new LocalService());
    new Receiver().register();
  }

  public void injectServices(
      ActivityManagerService activityManagerService,
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskManagerInternal activityTaskManagerInternal,
      BlockerManager blockerManager,
      EmergencyModeBlocker emergencyModeBlocker,
      UiManager uiManager,
      SettingsHelper settingsHelper,
      HardwareManager hardwareManager,
      TouchpadManager touchpadManager,
      CoverStateManager coverStateManager,
      MultiResolutionManager multiResolutionManager,
      DockManager dockManager,
      PackageStateManager packageStateManager,
      StatusBarManager statusBarManager,
      BootInitBlocker bootInitBlocker,
      DisplayPortStateManager displayPortStateManager,
      WirelessDexManager wirelessDexManager,
      BleAdvertiserServiceManager bleAdvertiserServiceManager,
      DualModeChanger dualModeChanger,
      StandaloneModeChanger standaloneModeChanger,
      McfManager mcfManager,
      DexManager dexManager) {
    this.mActivityManagerService = activityManagerService;
    this.mActivityTaskManagerService = activityTaskManagerService;
    this.mActivityTaskManagerInternal = activityTaskManagerInternal;
    this.mBlockerManager = blockerManager;
    this.mEmergencyModeBlocker = emergencyModeBlocker;
    this.mUiManager = uiManager;
    this.mSettingsHelper = settingsHelper;
    settingsHelper.registerListener(this.mWelcomeCompletedSettingChangedListener);
    settingsHelper.registerListener(this.mExternalDisplayModeListener);
    if (DesktopModeFeature.SUPPORT_NEW_DEX) {
      this.mResolver.registerContentObserver(
          Settings.System.getUriFor("new_dex"), true, this.mNewDeXSettingObserver);
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
      this.mBootInitBlocker = bootInitBlocker;
      bootInitBlocker.register();
    }
    this.mDisplayPortStateManager = displayPortStateManager;
    this.mWirelessDexManager = wirelessDexManager;
    this.mBleAdvertiserServiceManager = bleAdvertiserServiceManager;
    this.mMcfManager = mcfManager;
    this.mDexManager = dexManager;
  }

  public final void initializeStates() {
    if (!isUserSetupComplete()) {
      if (this.mUserSetupCompleteObserver == null) {
        this.mUserSetupCompleteObserver =
            new ContentObserver(
                null) { // from class: com.android.server.desktopmode.DesktopModeService.5
              @Override // android.database.ContentObserver
              public void onChange(boolean z) {
                super.onChange(z);
                DesktopModeService.this.initializeStates();
                DesktopModeService.this.mResolver.unregisterContentObserver(this);
                DesktopModeService.this.mUserSetupCompleteObserver = null;
              }
            };
      }
      this.mResolver.registerContentObserver(
          Settings.Secure.getUriFor("user_setup_complete"),
          false,
          this.mUserSetupCompleteObserver,
          this.mCurrentUserId);
    } else if (this.mIsBootComplete) {
      this.mStateManager.notifyBootCompleted();
    }
  }

  public final void onUserStarting(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "onStartUser(), userId=" + i);
    }
  }

  public final void onUserUnlocking(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(
          TAG, "onUnlockUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
    }
    this.mIsBootComplete = true;
    if (i == ActivityManager.getCurrentUser()) {
      onUserChanged(i);
    }
  }

  public final void onUserSwitching(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "onSwitchUser(), userId=" + i);
    }
    onUserChanged(i);
  }

  public final void onUserStopping(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(
          TAG, "onStopUser(), userId=" + i + ", CurrentUser=" + ActivityManager.getCurrentUser());
    }
  }

  public final void onUserStopped(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "onCleanupUser(), userId=" + i);
    }
  }

  public void setCurrentUserId(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setCurrentUserId(), userId=" + i);
    }
    this.mCurrentUserId = i;
    this.mStateManager.setCurrentUserId(i);
  }

  public final void onUserChanged(int i) {
    if (i == this.mCurrentUserId) {
      Log.m7d(TAG, "onUserChanged(), userId and mCurrentUserId are same(" + i + ")");
      return;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "onUserChanged(), userId=" + i);
    }
    setCurrentUserId(i);
    initializeStates();
  }

  public boolean isDesktopDockConnected() {
    return this.mStateManager.getState().getDockState().isDexStation();
  }

  public static boolean isDexPadConnected(State state) {
    return state.getDockState().isDexPad();
  }

  public boolean isDeviceConnected() {
    return isDesktopDockConnectedOrForced(this.mStateManager.getState());
  }

  public static boolean isDesktopDockConnectedOrForced(State state) {
    return state.getDockState().isDesktopModeSupported() || state.isDexOnPcOrWirelessDexConnected();
  }

  public boolean isDesktopMode() {
    return this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
  }

  public SemDesktopModeState getDesktopModeState() {
    return this.mStateManager.getState().getDesktopModeState();
  }

  public final boolean isDesktopModeAvailableEx(boolean z, boolean z2) {
    return this.mPreconditionChecker.isDesktopModeAvailableEx(z, z2);
  }

  public boolean isAllowed() {
    return this.mPreconditionChecker.isAllowed(this.mStateManager.getState(), false, false);
  }

  public final boolean isAllowedInternal(State state, boolean z, boolean z2) {
    if (this.mPreconditionChecker.isAllowed(state, z, z2)) {
      return true;
    }
    if (!DesktopModeFeature.SUPPORT_WIRELESS_DEX || !state.isWirelessDexConnected()) {
      return false;
    }
    this.mWirelessDexManager.disconnect();
    return false;
  }

  public final boolean isDesktopModeEnablingOrEnabled() {
    return isDesktopModeEnablingOrEnabled(this.mStateManager.getState(), -1);
  }

  public static boolean isDesktopModeEnablingOrEnabled(State state, int i) {
    int i2;
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    return (i == -1 || desktopModeState.getDisplayType() == i)
        && ((i2 = desktopModeState.enabled) == 3 || i2 == 4);
  }

  public final boolean isDesktopModeForPreparing() {
    return isDesktopModeForPreparing(this.mStateManager.getState(), -1);
  }

  public final boolean isDesktopModeForPreparing(int i) {
    return isDesktopModeForPreparing(this.mStateManager.getState(), i);
  }

  public static boolean isDesktopModeForPreparing(State state, int i) {
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    return (i == -1 || desktopModeState.getDisplayType() == i)
        && (desktopModeState.enabled == 4
            || desktopModeState.compareTo(3, 30)
            || desktopModeState.compareTo(1, 20));
  }

  public final boolean isForcedInternalScreenModeEnabled() {
    return this.mStateManager.getState().isForcedInternalScreenModeEnabled();
  }

  public final boolean isModeChangePending() {
    return isModeChangePending(this.mStateManager.getState(), -1);
  }

  public final boolean isModeChangePending(int i) {
    return isModeChangePending(this.mStateManager.getState(), i);
  }

  public static boolean isModeChangePending(State state, int i) {
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    return (i == -1 || desktopModeState.getDisplayType() == i) && desktopModeState.state == 30;
  }

  public boolean isExternalDisplayConnected() {
    return this.mStateManager.getState().isExternalDisplayConnected();
  }

  public static boolean isExternalDisplayConnectedOrForced(State state) {
    return state.isExternalDisplayConnected() || state.isForcedInternalScreenModeEnabled();
  }

  public static boolean isConnectionTypeChanged(State state) {
    DisplayInfo connectedDisplay = state.getConnectedDisplay();
    DisplayInfo previousConnectedDisplay = state.getPreviousConnectedDisplay();
    return (connectedDisplay == null
            || previousConnectedDisplay == null
            || connectedDisplay.getType() == previousConnectedDisplay.getType())
        ? false
        : true;
  }

  public boolean registerDesktopModeListener(
      IDesktopModeListener iDesktopModeListener, String str) {
    return this.mSemDesktopModeStateNotifier.registerDesktopModeListener(iDesktopModeListener, str);
  }

  public boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) {
    if (this.mInjector.binderGetCallingUid() != 1000) {
      throw new SecurityException("Only the system may call registerBlocker()");
    }
    return this.mBlockerManager.registerBlocker(iDesktopModeBlocker, str);
  }

  public boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) {
    if (this.mInjector.binderGetCallingUid() != 1000) {
      throw new SecurityException("Only the system may call unregisterBlocker()");
    }
    return this.mBlockerManager.unregisterBlocker(iDesktopModeBlocker);
  }

  public boolean unregisterDesktopModeListener(IDesktopModeListener iDesktopModeListener) {
    return this.mSemDesktopModeStateNotifier.unregisterDesktopModeListener(iDesktopModeListener);
  }

  public void onConfigurationChanged(Configuration configuration) {
    StandaloneModeChanger standaloneModeChanger;
    if (configuration == null || (standaloneModeChanger = this.mStandaloneModeChanger) == null) {
      return;
    }
    standaloneModeChanger.onConfigurationChanged(new Configuration(configuration));
  }

  public final void onDesktopDisplayConfigured(boolean z) {
    this.mDualModeChanger.onDesktopDisplayConfigured(z);
  }

  public final int getCurrentUiMode() {
    return this.mStandaloneModeChanger.getCurrentUiMode();
  }

  public final boolean isLockTaskModeEnabledAndSecured() {
    ModeChanger modeChanger = this.mModeChanger;
    return modeChanger != null && modeChanger.isLockTaskModeEnabledAndSecured();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdateDesktopMode$0(boolean z) {
    updateDesktopMode(this.mStateManager.getState(), z, false);
  }

  public void scheduleUpdateDesktopMode(final boolean z) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DesktopModeService.this.lambda$scheduleUpdateDesktopMode$0(z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleUpdateDesktopMode$1(boolean z, boolean z2) {
    updateDesktopMode(this.mStateManager.getState(), z, z2);
  }

  public void scheduleUpdateDesktopMode(final boolean z, final boolean z2) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            DesktopModeService.this.lambda$scheduleUpdateDesktopMode$1(z, z2);
          }
        });
  }

  public final void updateDesktopMode(State state, boolean z, boolean z2) {
    Log.saveState(state, z, this.mPreconditionChecker.updateDesktopMode(state, z, z2));
  }

  public final void showInitialConnectionDialog(int i, final String str, final boolean z) {
    showDialog(
        i,
        new UiManager.InternalUiCallback() { // from class:
          // com.android.server.desktopmode.DesktopModeService.6
          @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
          public void onClickButtonPositive() {
            DesktopModeService.this.setUserRequest(1);
            if (!z) {
              DesktopModeSettings.setSettings(
                  DesktopModeService.this.mResolver, "welcome_completed", true);
            }
            DesktopModeService.this.scheduleUpdateDesktopMode(true);
          }

          @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
          public void onDismiss() {
            DesktopModeSettings.setSettings(DesktopModeService.this.mResolver, str, true);
          }
        });
  }

  public final void handleHdmiAutoEnter(boolean z) {
    if (getCurrentUserId() == 0 && isSystemReady()) {
      if (z) {
        String settings =
            DesktopModeSettings.getSettings(this.mResolver, "hdmi_auto_enter", (String) null);
        boolean parseBoolean = Boolean.parseBoolean(settings);
        if (!DesktopModeFeature.SUPPORT_NEW_DEX
            && settings == null
            && !DesktopModeSettings.getSettings(
                this.mResolver, "hdmi_initial_connection_dialog_shown", false)) {
          showInitialConnectionDialog(5, "hdmi_initial_connection_dialog_shown", false);
          return;
        } else {
          if (parseBoolean) {
            setUserRequest(1);
            scheduleUpdateDesktopMode(true);
            return;
          }
          return;
        }
      }
      dismissDialog(5);
      finishWelcomeActivity();
    }
  }

  public final void handlePogoAutoEnter(boolean z) {
    if (getCurrentUserId() == 0 && isSystemReady()) {
      if (z) {
        String settings =
            DesktopModeSettings.getSettings(this.mResolver, "pogo_auto_enter", (String) null);
        boolean parseBoolean = Boolean.parseBoolean(settings);
        boolean equals =
            "new"
                .equals(
                    DesktopModeSettings.getSettings(
                        this.mResolver, "dex_mode", DesktopModeSettings.DEX_MODE_DEFAULT_VALUE));
        boolean settings2 =
            DesktopModeSettings.getSettings(this.mResolver, "welcome_completed", false);
        if (parseBoolean) {
          if (DesktopModeFeature.SUPPORT_NEW_DEX && equals) {
            updateNewDexMode(1);
            return;
          } else if (settings2) {
            setUserRequest(1);
            scheduleUpdateDesktopMode(true);
            return;
          } else {
            startWelcomeActivity();
            return;
          }
        }
        if (DesktopModeFeature.SUPPORT_NEW_DEX
            || settings != null
            || DesktopModeSettings.getSettings(
                this.mResolver, "pogo_initial_connection_dialog_shown", false)
            || !this.mAllowPogoInitialDialog
            || settings2) {
          return;
        }
        showInitialConnectionDialog(4, "pogo_initial_connection_dialog_shown", true);
        return;
      }
      dismissDialog(4);
      finishWelcomeActivity();
    }
  }

  public final void handleWirelessDexEnter(boolean z) {
    if (getCurrentUserId() == 0 && isSystemReady()) {
      if (!z) {
        startWelcomeActivity();
      } else {
        scheduleUpdateDesktopMode(true);
      }
    }
  }

  public final boolean isUserSetupComplete() {
    boolean z =
        Settings.Secure.getIntForUser(this.mResolver, "user_setup_complete", 0, this.mCurrentUserId)
            != 0;
    if (!z && DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "isUserSetupComplete()=false");
    }
    return z;
  }

  public final boolean isFotaSuwCompleted() {
    PackageManager packageManager = this.mContext.getPackageManager();
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.HOME");
    intent.addCategory("android.intent.category.DEFAULT");
    Iterator<ResolveInfo> it = packageManager.queryIntentActivities(intent, 65536).iterator();
    while (it.hasNext()) {
      if ("com.sec.android.app.SecSetupWizard".equals(it.next().activityInfo.packageName)) {
        Log.m10i(TAG, "isFotaSuwCompleted()=false");
        return false;
      }
    }
    return true;
  }

  public final boolean isSystemReady() {
    if (this.mIsBootComplete
        && isUserSetupComplete()
        && !FactoryTest.isFactoryBinary()
        && this.mCurrentUserId != -10000
        && isFotaSuwCompleted()) {
      return true;
    }
    Log.m10i(
        TAG,
        "isSystemReady(), mIsBootComplete="
            + this.mIsBootComplete
            + ", isFactoryBinary="
            + FactoryTest.isFactoryBinary()
            + ", mCurrentUserId="
            + this.mCurrentUserId);
    return false;
  }

  public final void showDialog(int i, UiManager.InternalUiCallback internalUiCallback) {
    this.mUiManager.showDialog(i, internalUiCallback);
  }

  public final void dismissDialog(int... iArr) {
    for (int i : iArr) {
      this.mUiManager.dismissDialog(i);
    }
  }

  public final void startWelcomeActivity() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "startWelcomeActivity()");
    }
    if (DesktopModeSettings.getSettings(this.mResolver, "skip_welcome_screen", false)
        || DesktopModeSettings.getSettings(this.mResolver, "welcome_shown", false)) {
      DesktopModeSettings.setSettings(this.mResolver, "welcome_completed", true);
      return;
    }
    if (this.mWelcomeActivityListener == null) {
      this.mWelcomeActivityListener = new WelcomeActivityListener();
    }
    this.mUiManager.startActivity(0, 301, this.mWelcomeActivityListener);
  }

  public final void finishWelcomeActivity() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "finishWelcomeActivity()");
    }
    this.mUiManager.finishActivity(301);
  }

  public final void logDesktopModeChanged(Intent intent) {
    int intExtra =
        intent.getIntExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_STATE", -1);
    int intExtra2 =
        intent.getIntExtra("com.samsung.android.desktopmode.extra.DESKTOP_MODE_SOURCE", -1);
    String str = TAG;
    Log.m10i(str, "EXTRA_DESKTOP_MODE_SOURCE=" + DesktopModeUiConstants.sourceToString(intExtra2));
    if (!this.mStateManager.getState().isExternalDisplayConnected() || intExtra2 <= -1) {
      return;
    }
    boolean z = intExtra == 1;
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(str, "logDesktopModeChanged(" + z + ")");
    }
    DesktopModeLogger.log(this.mContext, z ? "LMTD" : "LDTM", String.valueOf(intExtra2));
  }

  public final void setUserRequest(int i) {
    this.mUserRequest = i;
    Log.m10i(TAG, "userRequest=" + userRequestToString(this.mUserRequest));
  }

  public int getCurrentUserId() {
    return this.mCurrentUserId;
  }

  public final boolean isConfigurationChangedFromDeX(Configuration configuration) {
    boolean z = false;
    if (configuration != null && this.mStandaloneModeChanger != null) {
      SemDesktopModeState desktopModeState = this.mStateManager.getState().getDesktopModeState();
      int configurationState = this.mStandaloneModeChanger.getConfigurationState(configuration);
      if (desktopModeState.state == 30
          && ((configurationState == 1 && desktopModeState.enabled == 3)
              || (configurationState == 0 && desktopModeState.enabled == 1))) {
        z = true;
      }
      Log.m10i(
          TAG,
          "isConfigurationChangedFromDeX()="
              + z
              + ", config="
              + configuration
              + ", desktopModeState="
              + desktopModeState);
    }
    return z;
  }

  public final void startHome() {
    startHome(this.mStateManager.getState());
  }

  public final void startHome(State state) {
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    if (desktopModeState.enabled == 4) {
      if (desktopModeState.getDisplayType() == 101) {
        this.mStandaloneModeChanger.startHome(state);
      } else if (desktopModeState.getDisplayType() == 102) {
        this.mDualModeChanger.startHome(state);
      }
    }
  }

  public Bundle getDesktopModeKillPolicy() {
    return DesktopModeKillPolicyManager.getDesktopModeKillPolicy();
  }

  public void registerDesktopLauncher(IDesktopModeLauncher iDesktopModeLauncher) {
    int binderGetCallingUid = this.mInjector.binderGetCallingUid();
    if (this.mCurrentUserId != 0) {
      binderGetCallingUid = UserHandle.getAppId(binderGetCallingUid);
    }
    if (binderGetCallingUid == 0 || binderGetCallingUid == 1000) {
      synchronized (this.mLock) {
        this.mLauncherInterface = iDesktopModeLauncher;
      }
      return;
    }
    Log.m7d(TAG, "registerDesktopLauncher() is blocked!!");
  }

  public Bundle sendMessage(Bundle bundle) {
    if (this.mContext.checkCallingOrSelfPermission(
            "com.samsung.android.desktopmode.permission.BIND_DESKTOP_MODE_UI_SERVICE")
        == 0) {
      if (bundle.getInt(KnoxVpnFirewallHelper.CMD) == 1000) {
        final State state = this.mStateManager.getState();
        this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                DesktopModeService.this.lambda$sendMessage$2(state);
              }
            });
        return null;
      }
    } else if (this.mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE")
        == 0) {
      synchronized (this.mLock) {
        IDesktopModeLauncher iDesktopModeLauncher = this.mLauncherInterface;
        if (iDesktopModeLauncher != null) {
          try {
            return iDesktopModeLauncher.sendMessage(bundle);
          } catch (RemoteException e) {
            Log.m9e(
                TAG, "sendMessage(), Failed to communicate with launcher. Starting again...", e);
            this.mHandler.post(
                new Runnable() { // from class:
                  // com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticLambda2
                  @Override // java.lang.Runnable
                  public final void run() {
                    DesktopModeService.this.lambda$sendMessage$3();
                  }
                });
          }
        } else {
          Log.m12w(TAG, "sendMessage(), mLauncherInterface == null");
        }
      }
    }
    return null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendMessage$2(State state) {
    minimizeAllTasks(state.getDesktopDisplayId());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendMessage$3() {
    startHome(this.mStateManager.getState());
  }

  public final void minimizeAllTasks(int i) {
    if (i != 0) {
      this.mDualModeChanger.saveTopTaskId(i);
    }
    this.mActivityTaskManagerInternal.minimizeAllTasks(i, false);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public void onShellCommand(
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      FileDescriptor fileDescriptor3,
      String[] strArr,
      ShellCallback shellCallback,
      ResultReceiver resultReceiver) {
    if (DesktopModeFeature.DEBUG) {
      int binderGetCallingUid = this.mInjector.binderGetCallingUid();
      if (binderGetCallingUid != 2000 && binderGetCallingUid != 0) {
        throw new SecurityException("Caller must be shell");
      }
      new Shell(this.mResolver, this.mMultiResolutionManager, this.mUiManager, this.mHwManager)
          .exec(
              this,
              fileDescriptor,
              fileDescriptor2,
              fileDescriptor3,
              strArr,
              shellCallback,
              resultReceiver);
      return;
    }
    super.onShellCommand(
        fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
  }

  public static void startActivityInDisplayAsUser(Context context, Intent intent, int i, int i2) {
    ActivityOptions makeBasic = ActivityOptions.makeBasic();
    makeBasic.setLaunchDisplayId(i);
    context.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.of(i2));
  }

  public final int getTouchpadSupportedFeatures() {
    int i;
    State state = this.mStateManager.getState();
    if ((this.mModeChanger instanceof DualModeChanger)
        && this.mTouchpadManager.isTouchpadFeatureAvailable(state)) {
      i = this.mTouchpadManager.isSPenFeatureAvailable(state) ? 3 : 1;
    } else {
      i = 0;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "getTouchpadSupportedFeatures(), supportedFeatures=" + i);
    }
    return i;
  }

  public final int getModeToModeChangeType() {
    State state = this.mStateManager.getState();
    if (state.getDesktopModeState().getDisplayType() == 101) {
      return this.mStandaloneModeChanger.getModeToModeChangeType();
    }
    if (state.getDesktopModeState().getDisplayType() == 102) {
      return this.mDualModeChanger.getModeToModeChangeType();
    }
    return -1;
  }

  public final boolean isBlockerRegistered(State state, boolean z) {
    BlockerManager.DesktopModeBlockerInfo blocker = this.mBlockerManager.getBlocker(state);
    if (blocker == null) {
      return true;
    }
    Log.m10i(TAG, "isAllowed(showToast=" + z + "), blocked by " + blocker);
    if (z) {
      try {
        ToastManager.showToast(this.mContext, blocker.blocker.onBlocked());
        Log.saveState(state, blocker);
      } catch (RemoteException e) {
        Log.m9e(TAG, "Failed to get blocking message from blocker " + blocker, e);
      }
    }
    if (!this.mIsBootInitBlockerRegistered) {
      setUserRequest(0);
    }
    return false;
  }

  public void onSecuredAppLaunched(IBinder iBinder, String str) {
    onSecuredAppLaunched(this.mActivityManagerService.getTaskForActivity(iBinder, false), str);
  }

  public final void onSecuredAppLaunched(int i, String str) {
    if (DesktopModeFeature.DEBUG) {
      Log.m10i(TAG, "onSecuredAppLaunched, taskId=" + i + ", packageName= " + str);
    }
    if (this.mStateManager.getState().isDexOnPcConnected()) {
      this.mDualModeChanger.onSecuredAppLaunched(i, str);
    }
  }

  public final void updateNewDexMode(int i) {
    if (!DesktopModeFeature.SUPPORT_NEW_DEX) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "updateNewDexMode Failed : New Dex Feature Not Supported");
        return;
      }
      return;
    }
    if (this.mStateManager.getState().getCurrentUserId() != 0) {
      Context context = this.mContext;
      ToastManager.showToast(context, context.getString(R.string.global_action_settings));
      return;
    }
    if (this.mEmergencyModeBlocker.shouldBlockDesktopMode()) {
      Log.m7d(TAG, "New Dex Blocked, name = " + this.mEmergencyModeBlocker.toString());
      ToastManager.showToast(this.mContext, this.mEmergencyModeBlocker.onBlocked());
      return;
    }
    if (!KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(
        this.mStandaloneModeChanger.getDefaultHomePackageName(
            (RoleManager) this.mContext.getSystemService("role")))) {
      Context context2 = this.mContext;
      ToastManager.showToast(context2, context2.getString(R.string.preposition_for_date));
    } else if (i == 1) {
      Settings.System.putInt(this.mResolver, "new_dex", 1);
    } else {
      if (i != 2) {
        return;
      }
      Settings.System.putInt(this.mResolver, "new_dex", 0);
    }
  }

  public final boolean isNeededStartConnectivityActivity(State state, int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "isNeededStartConnectivityActivity()");
    }
    return (DesktopModeFeature.SUPPORT_STANDALONE
            || i == 3
            || i == 5
            || isExternalDisplayConnectedOrForced(state)
            || !state.getDesktopModeState().compareTo(2, 0))
        ? false
        : true;
  }

  public final void startConnectivityActivity(int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "startConnectivityActivity()");
    }
    startActivityInDisplayAsUser(
        this.mContext,
        new Intent()
            .setComponent(
                new ComponentName(
                    "com.sec.android.desktopmode.uiservice",
                    "com.sec.android.desktopmode.uiservice.activity.connectivity.ConnectivityActivity"))
            .setFlags(337641472),
        i,
        this.mCurrentUserId);
  }

  public final void startConnectivityActivity() {
    startConnectivityActivity(0);
  }

  public final boolean shouldStayInDesktopMode(State state) {
    boolean z = state.isHdmiConnected() && isDesktopModeEnablingOrEnabled(state, 102);
    if (z && this.mDexManager.getHDMIAutoEnterState() == 1) {
      Context context = this.mContext;
      ToastManager.showToast(context, context.getString(R.string.global_action_assist));
      return true;
    }
    if (!z || !state.isDexStationConnectedWithFlipCover()) {
      return false;
    }
    Context context2 = this.mContext;
    ToastManager.showToast(
        context2,
        String.format(
            context2.getString(R.string.global_action_silent_mode_off_status),
            this.mContext.getString(R.string.global_actions_airplane_mode_off_status)));
    return true;
  }

  public static String userRequestToString(int i) {
    if (i == 0) {
      return "NO_REQUEST";
    }
    if (i == 1) {
      return "ENTER_REQUEST";
    }
    if (i == 2) {
      return "EXIT_REQUEST";
    }
    return "Unknown=" + i;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
      if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("DesktopModeService (dumpsys desktopmode):");
        Log.dump(indentingPrintWriter);
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        DesktopModeSettings.dumpApp(indentingPrintWriter, this.mResolver, this.mCurrentUserId);
        this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        dumpImpl(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mStandaloneModeChanger.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mDualModeChanger.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mStateManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mPackageStateManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mSettingsHelper.dump(indentingPrintWriter, this.mResolver, this.mCurrentUserId);
        indentingPrintWriter.println();
        this.mUiManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mSemDesktopModeStateNotifier.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mBlockerManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mHwManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mTouchpadManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mCoverStateManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        ToastManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        DesktopModeKillPolicyManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mMultiResolutionManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mDockManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mDisplayPortStateManager.dump(indentingPrintWriter);
        if (DesktopModeFeature.SUPPORT_WIRELESS_DEX) {
          indentingPrintWriter.println();
          this.mWirelessDexManager.dump(indentingPrintWriter);
          if (this.mBleAdvertiserServiceManager != null) {
            indentingPrintWriter.println();
            this.mBleAdvertiserServiceManager.dump(indentingPrintWriter);
          }
          if (this.mMcfManager != null) {
            indentingPrintWriter.println();
            this.mMcfManager.dump(indentingPrintWriter);
          }
        }
        indentingPrintWriter.decreaseIndent();
        return;
      }
      if (DesktopModeFeature.DEBUG) {
        String str = strArr[0];
        if ((CoreRune.MD_DEX_EMULATOR && "on".equals(str))
            || "off".equals(str)
            || "toggle".equals(str)) {
          this.mHwManager.command(printWriter, str);
          printWriter.println("Please enter below next time:");
          printWriter.println("  cmd desktopmode " + str);
          return;
        }
        if ("settings".equals(str) || "resolution".equals(str) || "ui".equals(str)) {
          printWriter.println("Please enter below instead:");
          printWriter.println("  cmd desktopmode " + str);
          return;
        }
        printWriter.println("Unknown argument: " + str + "; USAGE: [on|off|toggle|dblist]");
      }
    }
  }

  public final void dumpImpl(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("mCurrentUserId=" + this.mCurrentUserId);
    indentingPrintWriter.println(
        "Configuration=" + this.mContext.getResources().getConfiguration());
    indentingPrintWriter.println("mUserRequest=" + userRequestToString(this.mUserRequest));
    indentingPrintWriter.println(
        "welcomeCompleted="
            + DesktopModeSettings.getSettings(this.mResolver, "welcome_completed", false));
    indentingPrintWriter.println(
        "DISPLAY_SIZE_FORCED=" + Settings.Global.getString(this.mResolver, "display_size_forced"));
    indentingPrintWriter.println(
        "DISPLAY_DENSITY_FORCED="
            + Settings.Secure.getStringForUser(this.mResolver, "display_density_forced", 0));
    indentingPrintWriter.println(
        "SCREEN_OFF_TIMEOUT="
            + Settings.System.getStringForUser(
                this.mResolver, "screen_off_timeout", this.mCurrentUserId));
    indentingPrintWriter.println(
        "SHOW_IME_WITH_HARD_KEYBOARD="
            + Settings.Secure.getStringForUser(
                this.mResolver, "show_ime_with_hard_keyboard", this.mCurrentUserId));
  }
}
