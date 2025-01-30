package com.android.server.desktopmode;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.internal.statusbar.IStatusBarService;
import com.android.server.ServiceThread;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes2.dex */
public class DualModeChanger extends ModeChanger {
  public static final String TAG = "[DMS]" + DualModeChanger.class.getSimpleName();
  public final CoverStateManager mCoverStateManager;
  public Context mDesktopDisplayContext;
  public int mDesktopDisplayId;
  public boolean mDisplayUnsupportDialogShown;
  public boolean mDualModeEnabled;
  public final KeyguardManager mKeyguardManager;
  public boolean mLoadingScreenAnimationComplete;
  public ModeChanger.ModeToModeChangeInfo mModeToModeChangeInfo;
  public final PowerManager mPowerManager;
  public final PowerManagerInternal mPowerManagerInternal;
  public int mPrevDesktopDisplayId;
  public ModeChangeReceiver mReceiver;
  public final SettingsHelper.OnSettingChangedListener mStabilizerModeChangedListener;
  public ModeChangeStateListener mStateListener;
  public final IStatusBarService mStatusBarService;
  public int mTopTaskId;
  public boolean mTopTaskIdValid;
  public final TouchpadManager mTouchpadManager;
  public boolean mTouchpadRequestedByNotification;
  public final SettingsHelper.OnSettingChangedListener mTouchpadSettingChangedListener;
  public boolean mWallpaperShown;

  public DualModeChanger(
      Context context,
      IStateManager iStateManager,
      SemDesktopModeStateNotifier semDesktopModeStateNotifier,
      ServiceThread serviceThread,
      IStatusBarService iStatusBarService,
      UiManager uiManager,
      SettingsHelper settingsHelper,
      MultiResolutionManager multiResolutionManager,
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskManagerInternal activityTaskManagerInternal,
      ActivityManagerInternal activityManagerInternal,
      WindowManagerInternal windowManagerInternal,
      StatusBarManager statusBarManager,
      MultiWindowManager multiWindowManager,
      SemDesktopModeManager semDesktopModeManager,
      CoverStateManager coverStateManager,
      KeyguardManager keyguardManager,
      TouchpadManager touchpadManager,
      PowerManager powerManager,
      PowerManagerInternal powerManagerInternal) {
    super(
        context,
        iStateManager,
        semDesktopModeStateNotifier,
        uiManager,
        settingsHelper,
        multiResolutionManager,
        activityTaskManagerService,
        activityTaskManagerInternal,
        activityManagerInternal,
        windowManagerInternal,
        statusBarManager,
        multiWindowManager,
        semDesktopModeManager);
    this.mDualModeEnabled = false;
    this.mLoadingScreenAnimationComplete = false;
    this.mWallpaperShown = false;
    this.mDisplayUnsupportDialogShown = false;
    this.mTouchpadRequestedByNotification = false;
    this.mTopTaskIdValid = false;
    this.mDesktopDisplayId = -1;
    this.mPrevDesktopDisplayId = -1;
    this.mTopTaskId = -1;
    this.mTouchpadSettingChangedListener =
        new SettingsHelper.OnSettingChangedListener(
            "touchpad_enabled") { // from class: com.android.server.desktopmode.DualModeChanger.1
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            if (DualModeChanger.this.mTouchpadRequestedByNotification
                || !Boolean.parseBoolean(str)) {
              return;
            }
            DualModeChanger dualModeChanger = DualModeChanger.this;
            dualModeChanger.mUiManager.startActivity(
                0, 300, dualModeChanger.mTouchpadManager.getInternalUiCallback());
            DualModeChanger.this.mTouchpadRequestedByNotification = true;
          }
        };
    this.mStabilizerModeChangedListener =
        new SettingsHelper.OnSettingChangedListener(
            "stabilizer_mode") { // from class: com.android.server.desktopmode.DualModeChanger.2
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            DualModeChanger.this.showStabilizerModeDialogIfNeeded();
          }
        };
    this.mHandler = new ModeChangeHandler(serviceThread.getLooper());
    this.mStatusBarService = iStatusBarService;
    this.mCoverStateManager = coverStateManager;
    this.mKeyguardManager = keyguardManager;
    this.mTouchpadManager = touchpadManager;
    this.mPowerManager = powerManager;
    this.mPowerManagerInternal = powerManagerInternal;
    this.mSettingsHelper = settingsHelper;
  }

  @Override // com.android.server.desktopmode.ModeChanger
  public void setDesktopModeState(int i, int i2) {
    SemDesktopModeState semDesktopModeState =
        new SemDesktopModeState(i, i2, (i == 2 && i2 == 0) ? 0 : 102);
    Log.m7d(TAG, "setDesktopModeState(), newState=" + semDesktopModeState);
    if (this.mStateManager.getState().getDesktopModeState().equals(semDesktopModeState)) {
      return;
    }
    this.mStateManager.setDesktopModeState(semDesktopModeState);
    this.mSemDesktopModeStateNotifier.notifyDesktopModeStateChanged(semDesktopModeState);
  }

  public void setDesktopMode(State state, boolean z) {
    setDesktopMode(state, z, null);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public ModeChanger.ModeToModeChangeInfo setDesktopMode(
      State state, boolean z, ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(
          TAG,
          "setDesktopMode(), enter=" + z + ", isModeChangeLocked=" + state.isModeChangeLocked());
    }
    this.mStateManager.notifyDualModeSetDesktopMode(state, z);
    setModeChangeLock(true);
    saveDisplayType(z ? 102 : 0);
    this.mModeToModeChangeInfo = modeToModeChangeInfo;
    int settings =
        (state.getDockState().isAdapter() || state.isDexOnPcOrWirelessDexConnected())
            ? DesktopModeSettings.getSettings(this.mResolver, "max_visible_freeform_count", 5)
            : -1;
    this.mMultiWindowManager.setMaxVisibleFreeformCountForDex(settings, settings);
    if (!this.mTopTaskIdValid) {
      if (modeToModeChangeInfo != null && modeToModeChangeInfo.getModeToMode() == 2) {
        this.mTopTaskId = modeToModeChangeInfo.getTopTaskId();
      } else {
        this.mTopTaskId =
            this.mActivityTaskManagerInternal.getForegroundTaskId(z ? 0 : this.mDesktopDisplayId);
      }
      this.mTopTaskIdValid = true;
    }
    if (modeToModeChangeInfo != null && modeToModeChangeInfo.getModeToMode() == 1) {
      modeToModeChangeInfo.setTopTaskId(this.mTopTaskId);
    }
    this.mUiManager.finishActivity(301);
    setDesktopModeState(z ? 3 : 1, 20);
    setDesktopModeToSurfaceFlinger(z);
    if (z) {
      cancelKillProcessWhenExit();
      Object[] objArr = 0;
      ModeChangeReceiver modeChangeReceiver = new ModeChangeReceiver();
      this.mReceiver = modeChangeReceiver;
      modeChangeReceiver.register();
      if (this.mStateListener == null) {
        this.mStateListener = new ModeChangeStateListener();
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(TAG, "mStateListener registered");
        }
        this.mStateManager.registerListener(this.mStateListener);
      } else {
        this.mHandler.removeMessages(3);
      }
      switchKnoxToFolderMode(state);
      setDesktopDisplayId(this.mMultiResolutionManager.setDualDisplayResolutionDensity(true));
    } else {
      if (!this.mCoverStateManager.goToSleepIfFlipTypeCoverClosed()
          && this.mPowerManagerInternal.isInternalDisplayOff()) {
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 114, "android.server:DeX");
      }
      if (this.mTopTaskIdValid
          && (modeToModeChangeInfo == null || modeToModeChangeInfo.getModeToMode() == 2)) {
        bringTaskToForeground(this.mTopTaskId, 0, 1);
        this.mTopTaskIdValid = false;
      }
      this.mMultiResolutionManager.setDualDisplayResolutionDensity(false);
      setDesktopDisplayId(-1);
    }
    this.mLoadingScreenAnimationComplete = false;
    this.mWallpaperShown = false;
    scheduleStartLoadingScreen(state, z);
    return modeToModeChangeInfo;
  }

  public void saveTopTaskId(int i) {
    this.mTopTaskId = this.mActivityTaskManagerInternal.getForegroundTaskId(i);
    this.mTopTaskIdValid = true;
  }

  public void onSecuredAppLaunched(final int i, final String str) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.desktopmode.DualModeChanger$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DualModeChanger.this.lambda$onSecuredAppLaunched$0(i, str);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onSecuredAppLaunched$0(int i, String str) {
    showSecuredAppLaunchedDialog();
    this.mContext.sendBroadcastAsUser(
        new Intent("com.samsung.android.desktopmode.action.SECURED_APP_LAUNCHED")
            .setPackage("com.sec.android.app.dexonpc")
            .putExtra("com.samsung.android.desktopmode.extra.TASK_ID", i)
            .putExtra("com.samsung.android.desktopmode.extra.PACKAGE_NAME", str),
        UserHandle.CURRENT);
  }

  public final void showSecuredAppLaunchedDialog() {
    int desktopDisplayId = this.mStateManager.getState().getDesktopDisplayId();
    if (desktopDisplayId != 0) {
      this.mUiManager.showDialog(desktopDisplayId, 8, null);
    }
  }

  public final void setDesktopDisplayId(int i) {
    this.mPrevDesktopDisplayId = this.mDesktopDisplayId;
    this.mDesktopDisplayId = i;
    this.mStateManager.setDesktopDisplayId(i);
    this.mDesktopDisplayContext =
        i != -1 ? Utils.getDisplayContext(this.mContext, this.mDesktopDisplayId) : null;
  }

  public final void setModeChangeLock(boolean z) {
    this.mStateManager.setModeChangeLocked(z);
  }

  public final void scheduleStartLoadingScreen(State state, boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "scheduleStartLoadingScreen(), enter=" + z);
    }
    this.mHandler.removeMessages(2);
    Handler handler = this.mHandler;
    handler.sendMessageDelayed(handler.obtainMessage(2, -1, 0, Boolean.valueOf(z)), 10000L);
    handleStartLoadingScreen(state, z);
  }

  public final void handleStartLoadingScreen(State state, boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m10i(TAG, "Starting loading screen... enter=" + z);
    }
    if (z) {
      if (state.isDexOnPcConnected()) {
        this.mLoadingScreenAnimationComplete = true;
      } else {
        ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = this.mModeToModeChangeInfo;
        this.mUiManager.showOverlay(
            103,
            (modeToModeChangeInfo == null || !modeToModeChangeInfo.showModeChangeScreen())
                ? 113
                : 117,
            new UiManager.InternalUiCallback() { // from class:
              // com.android.server.desktopmode.DualModeChanger.3
              @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
              public void onAnimationComplete() {
                if (DualModeChanger.this.mLoadingScreenAnimationComplete) {
                  return;
                }
                if (DesktopModeFeature.DEBUG) {
                  Log.m7d(DualModeChanger.TAG, "onAnimationComplete()");
                }
                DualModeChanger.this.mLoadingScreenAnimationComplete = true;
                DualModeChanger.this.scheduleStopLoadingScreenIfPossible(true);
              }
            });
      }
      this.mSettingsHelper.registerListener(this.mTouchpadSettingChangedListener);
      this.mSettingsHelper.registerListener(this.mStabilizerModeChangedListener);
    } else {
      this.mSettingsHelper.unregisterListener(this.mTouchpadSettingChangedListener);
      this.mSettingsHelper.unregisterListener(this.mStabilizerModeChangedListener);
      this.mTouchpadRequestedByNotification = false;
    }
    this.mStateManager.notifyDualModeStartLoadingScreen(z);
    setDesktopModeInternal(z);
  }

  public final void setDesktopModeInternal(boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setDesktopModeInternal(), enter=" + z);
    }
    dismissDialog(0);
    setDesktopModeState(z ? 3 : 1, 30);
    this.mStateManager.notifyDualModeSetDesktopModeInternal(z);
    backupLockTaskModeEnabledAndSecured(this.mStateManager.getState());
    if (z) {
      try {
        this.mActivityTaskManager.stopSystemLockTaskMode();
      } catch (RemoteException e) {
        e.rethrowFromSystemServer();
      }
    }
  }

  public void onDesktopDisplayConfigured(final boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m11v(
          TAG,
          "onDesktopDisplayConfigured(), added="
              + z
              + ", mDesktopDisplayId="
              + this.mDesktopDisplayId);
    }
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.desktopmode.DualModeChanger$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            DualModeChanger.this.lambda$onDesktopDisplayConfigured$1(z);
          }
        });
  }

  /* renamed from: handleDesktopDisplayConfigured, reason: merged with bridge method [inline-methods] */
  public final void lambda$onDesktopDisplayConfigured$1(boolean z) {
    State state = this.mStateManager.getState();
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    if (desktopModeState.state == 30 && desktopModeState.getDisplayType() == 102) {
      if (!(z && desktopModeState.enabled == 3) && (z || desktopModeState.enabled != 1)) {
        return;
      }
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            TAG,
            "handleDesktopDisplayConfigured(), added="
                + z
                + ", desktopModeState="
                + desktopModeState);
      }
      this.mDualModeEnabled = z;
      setDesktopModeState(z ? 4 : 2, 40);
      boolean z2 = false;
      if (CoreRune.SECONDARY_LAUNCHER_ACTIVITY_SUPPORT_FOR_DEX
          && DesktopModeSettings.getSettings(this.mResolver, "enable_new_dex_home", false)) {
        z2 = true;
      }
      if (z && !z2) {
        startHome(state, this.mDesktopDisplayId);
      } else {
        this.mLoadingScreenAnimationComplete = true;
        this.mWallpaperShown = true;
      }
      this.mSettingsHelper.backupOrRestoreSettings(z, state);
      setDesktopModeState(z ? 4 : 2, 50);
      this.mStateManager.notifyDualModeOnConfigurationChanged(z);
      scheduleStopLoadingScreenIfPossible(z);
    }
  }

  public void startHome(State state) {
    boolean z = false;
    if (CoreRune.SECONDARY_LAUNCHER_ACTIVITY_SUPPORT_FOR_DEX
        && DesktopModeSettings.getSettings(this.mResolver, "enable_new_dex_home", false)) {
      z = true;
    }
    int i = this.mDesktopDisplayId;
    if (i == -1 || z) {
      return;
    }
    startHome(state, i);
  }

  public int getModeToModeChangeType() {
    ModeChanger.ModeToModeChangeInfo modeToModeChangeInfo = this.mModeToModeChangeInfo;
    if (modeToModeChangeInfo != null) {
      return modeToModeChangeInfo.getModeToMode();
    }
    return -1;
  }

  public final void startHome(State state, int i) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "startHome(userId=" + state.getCurrentUserId() + ", displayId=" + i + ")");
    }
    Intent intent =
        new Intent("android.intent.action.MAIN")
            .addCategory("android.intent.category.HOME")
            .setFlags(270598144)
            .putExtra(DesktopModeService.class.getSimpleName(), true)
            .setPackage("com.sec.android.app.desktoplauncher");
    ActivityOptions makeBasic = ActivityOptions.makeBasic();
    makeBasic.setLaunchDisplayId(i);
    this.mContext.startActivityAsUser(
        intent, makeBasic.toBundle(), UserHandle.of(state.getCurrentUserId()));
  }

  public final void scheduleStopLoadingScreenIfPossible(boolean z) {
    SemDesktopModeState desktopModeState = this.mStateManager.getState().getDesktopModeState();
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(
          TAG,
          "scheduleStopLoadingScreenIfPossible(), enter="
              + z
              + ", mWallpaperShown="
              + this.mWallpaperShown
              + ", mLoadingScreenAnimationComplete="
              + this.mLoadingScreenAnimationComplete
              + ", desktopModeState="
              + desktopModeState);
    }
    if (this.mWallpaperShown && this.mLoadingScreenAnimationComplete) {
      if (desktopModeState.compareTo(z ? 4 : 2, 50, 102)) {
        this.mHandler.removeMessages(2);
        this.mHandler.obtainMessage(2, 0, 0, Boolean.valueOf(z)).sendToTarget();
      }
    }
  }

  public final void handleStopLoadingScreen(boolean z, int i) {
    State state = this.mStateManager.getState();
    if (i == -1) {
      if (DesktopModeFeature.DEBUG) {
        Log.m12w(TAG, "Loading screen timeout! enter=" + z);
      }
      this.mWallpaperShown = true;
      this.mLoadingScreenAnimationComplete = true;
      if (!hasPackageTask(state.getDesktopDisplayId(), "com.sec.android.app.desktoplauncher", 2)) {
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(TAG, "DeX Home task does not exist in DeX display");
        }
        startHome(state);
      }
    }
    if (state.getDesktopModeState().state != 50) {
      if (DesktopModeFeature.DEBUG) {
        Log.m12w(
            TAG,
            "Failed to stop loading screen. onDesktopDisplayConfigured() is not called! enter="
                + z
                + ", desktopModeState="
                + state.getDesktopModeState());
        return;
      }
      return;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m10i(TAG, "Stopping loading screen... enter=" + z + " state=" + state);
    }
    if (this.mTopTaskIdValid && z) {
      bringTaskToForeground(this.mTopTaskId, this.mDesktopDisplayId, 5);
      this.mTopTaskIdValid = false;
    }
    this.mUiManager.dismissOverlay(103, 111);
    setModeChangeLock(false);
    setDesktopModeState(z ? 4 : 2, 0);
    this.mStateManager.notifyDualModeStopLoadingScreen(z);
    if (!z) {
      this.mReceiver.unregister();
      this.mReceiver = null;
      this.mHandler.removeMessages(3);
      this.mHandler.sendEmptyMessageDelayed(3, 2000L);
      forceKillProcessWhenExit();
    } else {
      this.mDisplayUnsupportDialogShown = false;
      showDisplayUnsupportDialogIfNeeded(state);
      if (this.mDesktopDisplayContext != null
          && DesktopModeSettings.getSettings(this.mResolver, "auto_open_last_app", false)) {
        ToastManager.showToast(
            this.mDesktopDisplayContext, R.string.foreground_service_tap_for_details);
      }
      showDexMirroringTipsNotification(
          this.mContext.getString(R.string.fp_power_button_bp_title),
          this.mContext.getString(R.string.fp_power_button_bp_positive_button));
      if (state.isWirelessDexConnected()) {
        this.mUiManager.showNotification(204);
      }
      showStabilizerModeDialogIfNeeded();
    }
    this.mStateManager.notifyScheduleUpdateDesktopMode(!z);
  }

  public final void showDisplayUnsupportDialogIfNeeded(State state) {
    if (state.isDisplayResolutionUnsupported()) {
      this.mUiManager.showDialog(state.getDesktopDisplayId(), 2, null);
      this.mDisplayUnsupportDialogShown = true;
    }
  }

  public final void startSPenModeSettingActivity(State state) {
    startActivityInDesktopDisplay(
        new Intent()
            .setComponent(
                new ComponentName(
                    "com.sec.android.app.desktoplauncher",
                    "com.android.launcher3.settings.SPenInputModePreferenceActivity"))
            .setFlags(337641472),
        state);
  }

  public final void startTouchpadActivity(State state) {
    this.mUiManager.startActivity(0, 300, this.mTouchpadManager.getInternalUiCallback());
    this.mActivityTaskManagerInternal.minimizeAllTasks(0, true);
    if (this.mKeyguardManager.isKeyguardLocked()
        && this.mKeyguardManager.isKeyguardSecure()
        && !state.isNavBarGestureEnabled()
        && hasNavigationBar()) {
      this.mKeyguardManager.semDismissKeyguard();
    } else {
      this.mTouchpadManager.notifyStartedByNotification();
      this.mTouchpadRequestedByNotification = true;
    }
  }

  public final void startActivityInDesktopDisplay(Intent intent, State state) {
    if (state.getDesktopDisplayId() != -1) {
      ActivityOptions makeBasic = ActivityOptions.makeBasic();
      makeBasic.setLaunchDisplayId(state.getDesktopDisplayId());
      this.mContext.startActivityAsUser(
          intent, makeBasic.toBundle(), UserHandle.of(state.getCurrentUserId()));
    }
  }

  public final void collapsePanelsToDisplay(int i) {
    try {
      IStatusBarService iStatusBarService = this.mStatusBarService;
      if (iStatusBarService != null) {
        iStatusBarService.collapsePanelsToType(i == 0 ? 0 : 1);
      }
    } catch (RemoteException e) {
      Log.m9e(TAG, "collapsePanelsToDisplay failed.", e);
    }
  }

  public final void updateTouchpadNotification(State state) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateTouchpadNotification(), state=" + state);
    }
    if (canShowTouchpadNotification(state)) {
      this.mUiManager.showNotification(
          (!DesktopModeFeature.SUPPORT_SPEN || state.getDockState().isDexStation()) ? 201 : 202);
    } else {
      this.mUiManager.removeNotification(200);
    }
  }

  public boolean canShowTouchpadNotification(State state) {
    boolean isNavBarGestureEnabled = state.isNavBarGestureEnabled();
    boolean hasNavigationBar = hasNavigationBar();
    boolean isTouchpadAvailable = state.isTouchpadAvailable();
    boolean isTouchpadEnabled = state.isTouchpadEnabled();
    Log.m7d(
        TAG,
        "canShowTouchpadNotification navBarGestureEnabled="
            + isNavBarGestureEnabled
            + ", hasNavigationBar="
            + hasNavigationBar
            + ", isTouchpadAvailable="
            + isTouchpadAvailable
            + ", isTouchpadEnabled="
            + isTouchpadEnabled);
    return (isNavBarGestureEnabled || !hasNavigationBar)
        && isTouchpadAvailable
        && !isTouchpadEnabled;
  }

  public final void updateSpenNotification(State state) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateSpenNotification(), state=" + state);
    }
    if (state.isTouchpadEnabled() && state.isSpenEnabled()) {
      this.mUiManager.showNotification(203);
    } else {
      this.mUiManager.removeNotification(203);
    }
  }

  public final void updateNavBarIcon(State state) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "updateNavBarIcon(), state=" + state);
    }
    if (!hasNavigationBar()) {
      if (DesktopModeFeature.DEBUG) {
        Log.m10i(TAG, "Skip updateNavBarIcon");
      }
    } else if (state.isTouchpadAvailable()) {
      this.mUiManager.showNavBarIcon(400);
    } else {
      this.mUiManager.removeNavBarIcon(400);
    }
  }

  public final boolean hasNavigationBar() {
    return this.mContext.getResources().getBoolean(R.bool.config_predictShowStartingSurface);
  }

  public final void showStabilizerModeDialogIfNeeded() {
    boolean settings =
        DesktopModeSettings.getSettings(this.mResolver, "stabilizer_mode_consent", false);
    if (!DesktopModeSettings.getSettings(this.mResolver, "stabilizer_mode", false) || settings) {
      return;
    }
    UiManager.InternalUiCallback internalUiCallback =
        new UiManager
            .InternalUiCallback() { // from class: com.android.server.desktopmode.DualModeChanger.4
          @Override // com.android.server.desktopmode.UiManager.InternalUiCallback
          public void onClickButtonPositive() {
            if (DesktopModeFeature.DEBUG) {
              Log.m7d(DualModeChanger.TAG, "onClickButtonPositive()");
            }
            DesktopModeSettings.setSettings(
                DualModeChanger.this.mResolver, "stabilizer_mode_consent", true);
          }
        };
    if (this.mStateManager.getState().getDesktopDisplayId() != 0) {
      this.mUiManager.showDialog(
          this.mStateManager.getState().getDesktopDisplayId(), 10, internalUiCallback);
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Current " + DualModeChanger.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println("mDesktopDisplayId=" + this.mDesktopDisplayId);
    indentingPrintWriter.println("mDualModeEnabled=" + this.mDualModeEnabled);
    indentingPrintWriter.println("mModeToModeChangeInfo=" + this.mModeToModeChangeInfo);
    indentingPrintWriter.println("mPrevDesktopDisplayId=" + this.mPrevDesktopDisplayId);
    indentingPrintWriter.println("mTopTaskId=" + this.mTopTaskId);
    indentingPrintWriter.println("mTopTaskIdValid=" + this.mTopTaskIdValid);
    indentingPrintWriter.println(
        "mTouchpadRequestedByNotification=" + this.mTouchpadRequestedByNotification);
    indentingPrintWriter.decreaseIndent();
  }

  public class ModeChangeHandler extends Handler {
    public ModeChangeHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 2) {
        DualModeChanger.this.handleStopLoadingScreen(
            ((Boolean) message.obj).booleanValue(), message.arg1);
        return;
      }
      if (i == 3 && DualModeChanger.this.mStateManager != null) {
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(DualModeChanger.TAG, "mStateListener unregistered");
        }
        DualModeChanger dualModeChanger = DualModeChanger.this;
        dualModeChanger.mStateManager.unregisterListener(dualModeChanger.mStateListener);
        DualModeChanger.this.mStateListener = null;
      }
    }
  }

  public class ModeChangeReceiver extends BroadcastReceiver {
    public ModeChangeReceiver() {}

    public final void register() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.TIME_TICK");
      intentFilter.addAction("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN");
      intentFilter.addAction("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED");
      intentFilter.addAction(
          "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED");
      intentFilter.addAction(
          "com.samsung.android.desktopmode.action.TOUCHPAD_NOTIFICATION_PRESSED");
      intentFilter.addAction(
          "com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED");
      intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
      DualModeChanger dualModeChanger = DualModeChanger.this;
      dualModeChanger.mContext.registerReceiverAsUser(
          this, UserHandle.ALL, intentFilter, null, dualModeChanger.mHandler);
    }

    public final void unregister() {
      DualModeChanger.this.mContext.unregisterReceiver(this);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(DualModeChanger.TAG, "onReceive(), action=" + action);
      }
      State state = DualModeChanger.this.mStateManager.getState();
      if ("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN".equals(action)) {
        if (DualModeChanger.this.mWallpaperShown) {
          return;
        }
        DualModeChanger.this.mWallpaperShown = true;
        DualModeChanger.this.scheduleStopLoadingScreenIfPossible(true);
        return;
      }
      if ("android.intent.action.TIME_TICK".equals(action)) {
        long freeMemory = Process.getFreeMemory();
        if (freeMemory < 419430400) {
          if (DesktopModeFeature.DEBUG) {
            Log.m12w(DualModeChanger.TAG, "Low memory warning: " + (freeMemory / 1048576) + "MB");
          }
          if (DualModeChanger.this.mDesktopDisplayContext != null) {
            ToastManager.showToast(
                DualModeChanger.this.mDesktopDisplayContext, R.string.global_action_restart);
            return;
          }
          return;
        }
        return;
      }
      if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED".equals(action)) {
        int intExtra = intent.getIntExtra("ACTION_CLICK_DISPLAYID", 0);
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(DualModeChanger.TAG, "SPen notification clicked, displayID=" + intExtra);
        }
        DualModeChanger.this.collapsePanelsToDisplay(intExtra);
        DualModeChanger.this.mUiManager.showDialog(intExtra, 6, null);
        return;
      }
      if ("com.samsung.android.desktopmode.action.TOUCHPAD_NOTIFICATION_PRESSED".equals(action)) {
        int intExtra2 = intent.getIntExtra("CLICK_DISPLAYID", 0);
        DualModeChanger.this.collapsePanelsToDisplay(intExtra2);
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(DualModeChanger.TAG, "Touchpad notification clicked, displayID=" + intExtra2);
        }
        DualModeChanger.this.mUiManager.showDialog(intExtra2, 1, null);
        return;
      }
      if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED"
          .equals(action)) {
        int intExtra3 = intent.getIntExtra("ACTION_CLICK_DISPLAYID", 0);
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(
              DualModeChanger.TAG, "SPen notification change mode clicked, displayID=" + intExtra3);
        }
        DualModeChanger.this.collapsePanelsToDisplay(intExtra3);
        DualModeChanger.this.startSPenModeSettingActivity(state);
        return;
      }
      if ("com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED"
          .equals(action)) {
        DualModeChanger.this.collapsePanelsToDisplay(intent.getIntExtra("CLICK_DISPLAYID", 0));
        DualModeChanger.this.startTouchpadActivity(state);
      } else if ("android.intent.action.ACTION_SHUTDOWN".equals(action)
          && getSendingUserId() == -1
          && state.getDesktopDisplayId() != -1) {
        DualModeChanger.this.mMultiResolutionManager.setDualDisplayResolutionDensity(false);
      }
    }
  }

  public class ModeChangeStateListener extends StateManager.StateListener {
    public ModeChangeStateListener() {}

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onTouchpadEnabled(State state) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(DualModeChanger.TAG, "onTouchpadEnabled(), state=" + state);
      }
      DualModeChanger.this.updateTouchpadNotification(state);
      DualModeChanger.this.updateSpenNotification(state);
      DualModeChanger.this.updateNavBarIcon(state);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onTouchpadAvailabilityChanged(State state) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(DualModeChanger.TAG, "onTouchpadAvailabilityChanged(), state=" + state);
      }
      DualModeChanger.this.updateTouchpadNotification(state);
      DualModeChanger.this.updateNavBarIcon(state);
      if (state.isTouchpadAvailable()) {
        if (DesktopModeSettings.getSettings(
            DualModeChanger.this.mResolver, "touchpad_auto_run", false)) {
          DualModeChanger dualModeChanger = DualModeChanger.this;
          dualModeChanger.mUiManager.startActivity(
              0, 300, dualModeChanger.mTouchpadManager.getInternalUiCallback());
        }
        if (DesktopModeSettings.getSettings(
            DualModeChanger.this.mResolver, "how_to_open_touchpad_shown", false)) {
          return;
        }
        DualModeChanger.this.mUiManager.showDialog(
            0, !DualModeChanger.this.canShowTouchpadNotification(state) ? 9 : 7, null);
        DesktopModeSettings.setSettings(
            DualModeChanger.this.mResolver, "how_to_open_touchpad_shown", true);
        return;
      }
      DualModeChanger.this.mUiManager.finishActivity(300);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onSpenEnabled(State state) {
      DualModeChanger.this.updateSpenNotification(state);
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onExternalDisplayConnectionChanged(State state) {
      if (state.isExternalDisplayConnected()
          && !DualModeChanger.this.mDisplayUnsupportDialogShown
          && state.getDesktopModeState().enabled == 4) {
        DualModeChanger.this.showDisplayUnsupportDialogIfNeeded(state);
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onNavBarGestureEnabled(State state) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(DualModeChanger.TAG, "onNavBarGestureEnabled(), state=" + state);
      }
      DualModeChanger.this.updateTouchpadNotification(state);
    }
  }
}
