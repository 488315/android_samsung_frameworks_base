package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.WindowConfiguration;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.Region;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.IInstalld;
import android.provider.Settings;
import android.util.Slog;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.WindowInsets;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.DockedDividerUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.uri.NeededUriGrants;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SmartPopupViewUtil;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.remoteappmode.RemoteAppModeManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class MultiTaskingController implements IController {
  public static final boolean ALLOW_OVERRIDE_DESKTOP_LAUNCHER =
      SystemProperties.getBoolean("debug.mt.allow_override_activity_config_desktop_launcher", true);
  public static final String TAG = "MultiTaskingController";
  public final ActivityEmbeddedPackageRepository mActivityEmbeddedPackageRepository;
  public final ActivityTaskManagerService mAtm;
  public boolean mDeferFocusChanging;

  /* renamed from: mH */
  public HandlerC2944H f1743mH;
  public boolean mIsFullToSplitEnabled;
  public boolean mIsGestureTypeSideAndBottom;
  public boolean mIsMinimalBatteryUse;
  public boolean mIsNavigationModeGesture;
  public int mLastRotation;
  public Runnable mMoveToBackTaskWithIme;
  public SettingsObserver mSettingsObserver;
  public int mSwipeGestureThreshold;
  public ActivityRecord mTmpPipCandidate;
  public WindowManagerService mWm;
  public int mSystemUIUid = -1;
  public final Rect mTmpRect = new Rect();
  public Task mAffordanceTargetTask = null;
  public int mPendingMoveToTaskId = -1;
  public Configuration mLastConfig = new Configuration();
  public int mSplitFeasibleMode = 2;
  public boolean mIsDisplaySizeOverride = false;
  public final Queue mFocusableTaskIds = new LinkedList();
  public final RemoteCallbackList mRemoteAppTransitionListeners = new RemoteCallbackList();
  public final AppTransitionConsumer mNotifyStartRecentsAnimation =
      new AppTransitionConsumer() { // from class:
                                    // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.MultiTaskingController.AppTransitionConsumer
        public final void accept(
            IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
          MultiTaskingController.lambda$new$0(iRemoteAppTransitionListener, message);
        }
      };
  public final AppTransitionConsumer mNotifyFinishRecentsAnimation =
      new AppTransitionConsumer() { // from class:
                                    // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda1
        @Override // com.android.server.wm.MultiTaskingController.AppTransitionConsumer
        public final void accept(
            IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
          MultiTaskingController.lambda$new$1(iRemoteAppTransitionListener, message);
        }
      };
  public final AppTransitionConsumer mNotifyStartHomeAnimation =
      new AppTransitionConsumer() { // from class:
                                    // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda2
        @Override // com.android.server.wm.MultiTaskingController.AppTransitionConsumer
        public final void accept(
            IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
          MultiTaskingController.lambda$new$2(iRemoteAppTransitionListener, message);
        }
      };
  public final AppTransitionConsumer mNotifyWallpaperVisibilityChanged =
      new AppTransitionConsumer() { // from class:
                                    // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda3
        @Override // com.android.server.wm.MultiTaskingController.AppTransitionConsumer
        public final void accept(
            IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
          MultiTaskingController.lambda$new$3(iRemoteAppTransitionListener, message);
        }
      };
  public boolean mDeferEnsureConfig = false;
  public final BroadcastReceiver mMinimizeAllReceiver =
      new BroadcastReceiver() { // from class: com.android.server.wm.MultiTaskingController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          Bundle extras = intent.getExtras();
          int i = extras != null ? extras.getInt("displayId", 0) : 0;
          if ("com.samsung.android.multiwindow.MINIMIZE_ALL".equals(action)
              || "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM".equals(action)) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                MultiTaskingController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                MultiTaskingController.this.minimizeAllTasksLocked(i, true);
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        }
      };
  public final BroadcastReceiver mDemoResetStartedReceiver =
      new BroadcastReceiver() { // from class: com.android.server.wm.MultiTaskingController.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action)) {
            if (SmartPopupViewUtil.isShopDemo(MultiTaskingController.this.mAtm.mContext)) {
              Slog.d(MultiTaskingController.TAG, "demo reset");
              if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                SmartPopupViewUtil.resetPackageListStr(MultiTaskingController.this.mAtm.mContext);
              }
              MultiWindowManager.getInstance().setCornerGestureEnabledWithSettings(false);
              return;
            }
            return;
          }
          if ("com.samsung.intent.action.SETTINGS_SOFT_RESET".equals(action)) {
            Slog.d(MultiTaskingController.TAG, "settings reset");
            SmartPopupViewUtil.resetPackageListStr(MultiTaskingController.this.mAtm.mContext);
          }
        }
      };

  public interface AppTransitionConsumer {
    void accept(IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message);
  }

  public final boolean isVerticalDivision(int i, int i2) {
    if ((i & 8) != 0 && (i2 & 32) != 0) {
      return true;
    }
    if ((i2 & 8) == 0 || (i & 32) == 0) {
      return i == 0 && i2 == 0;
    }
    return true;
  }

  public void removeMoveToBackTaskWithIme(int i) {}

  public static /* synthetic */ void lambda$new$0(
      IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
    iRemoteAppTransitionListener.onStartRecentsAnimation(message.arg1 != 0);
  }

  public static /* synthetic */ void lambda$new$1(
      IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
    iRemoteAppTransitionListener.onFinishRecentsAnimation(message.arg1 != 0);
  }

  public static /* synthetic */ void lambda$new$2(
      IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
    iRemoteAppTransitionListener.onStartHomeAnimation(message.arg1 != 0);
  }

  public static /* synthetic */ void lambda$new$3(
      IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
    iRemoteAppTransitionListener.onWallpaperVisibilityChanged(message.arg1 != 0, message.arg2 != 0);
  }

  public MultiTaskingController(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
    this.mActivityEmbeddedPackageRepository =
        new ActivityEmbeddedPackageRepository(activityTaskManagerService);
  }

  @Override // com.android.server.wm.IController
  public void initialize() {
    this.f1743mH = new HandlerC2944H(this.mAtm.f1734mH.getLooper());
    this.mSettingsObserver = new SettingsObserver(this.f1743mH);
  }

  @Override // com.android.server.wm.IController
  public void setWindowManager(WindowManagerService windowManagerService) {
    this.mWm = windowManagerService;
  }

  public void performDisplayOverrideConfigUpdate(
      int i, int i2, float f, float f2, Rect rect, Rect rect2) {
    Slog.i(
        TAG,
        "onConfigurationChangedLocked: display#"
            + i2
            + ", configChanges=0x"
            + Integer.toHexString(i)
            + ", scaleW="
            + f
            + ", scaleH="
            + f2
            + ", prevScreenBounds="
            + rect
            + ", nextScreenBounds="
            + rect2);
    this.mAtm.mDexController.performDisplayOverrideConfigUpdate(i, i2);
    this.mAtm.mFreeformController.performDisplayOverrideConfigUpdate(i, i2, f, f2, rect, rect2);
    this.mAtm.mDexController.updateDexModeIfNeededLocked("configurationChanged");
    if (i2 == 0) {
      updateSystemGestureThreshold();
    }
  }

  public void finishBooting() {
    if (CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
      this.mActivityEmbeddedPackageRepository.registerActivityEmbeddedPackageReceiver();
      this.mActivityEmbeddedPackageRepository.loadActivityEmbeddedPackages();
    }
    registerMinimizeAllReceiver();
    boolean z = CoreRune.MW_FREEFORM_SMART_POPUP_VIEW;
    registerDemoResetReceiver();
    updateSystemGestureThreshold();
  }

  public final void registerDemoResetReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
    intentFilter.addAction("com.samsung.intent.action.SETTINGS_SOFT_RESET");
    this.mAtm.mContext.registerReceiver(
        this.mDemoResetStartedReceiver,
        intentFilter,
        "android.permission.MANAGE_ACTIVITY_STACKS",
        this.f1743mH);
  }

  public void readAllSettings() {
    this.mSettingsObserver.readSettings(null, true);
  }

  public void enableScreenAfterBoot() {
    this.mAtm.mDexController.setGlobalFontScale(
        Settings.System.getFloatForUser(
            this.mAtm.mContext.getContentResolver(),
            "font_scale",
            1.0f,
            this.mAtm.getCurrentUserId()));
  }

  public boolean interceptStartActivityLocked(
      Task task,
      ActivityRecord activityRecord,
      ActivityRecord activityRecord2,
      int i,
      int i2,
      int i3,
      ActivityOptions activityOptions,
      NeededUriGrants neededUriGrants,
      Task task2,
      boolean z,
      ActivityStarter.Request request) {
    boolean intercept =
        this.mAtm.mDexController.intercept(
            task,
            activityRecord,
            activityRecord2,
            i,
            i2,
            i3,
            activityOptions,
            neededUriGrants,
            z ? task2 : null);
    return (intercept || task == null)
        ? intercept
        : this.mAtm.mRemoteAppController.interceptStartActivityLocked(
            activityRecord,
            task,
            activityRecord2,
            activityOptions,
            i3,
            neededUriGrants,
            "reusedTask",
            request);
  }

  public boolean interceptStartActivityFromRecentsLocked(
      Task task, ActivityOptions activityOptions, int i, int i2) {
    int launchDisplayId = activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
    boolean interceptStartActivityFromRecentsLocked =
        this.mAtm.mDexController.interceptStartActivityFromRecentsLocked(task, activityOptions)
            | false;
    return !interceptStartActivityFromRecentsLocked
        ? interceptStartActivityFromRecentsLocked
            | this.mAtm.mRemoteAppController.interceptStartActivityFromRecentsLocked(
                task, activityOptions, launchDisplayId, "fromRecents")
        : interceptStartActivityFromRecentsLocked;
  }

  public boolean interceptNewTaskIfNeededLocked(
      ActivityRecord activityRecord,
      ActivityRecord activityRecord2,
      ActivityOptions activityOptions,
      int i,
      NeededUriGrants neededUriGrants,
      ActivityStarter.Request request) {
    return this.mAtm.mRemoteAppController.interceptStartActivityLocked(
            activityRecord,
            null,
            activityRecord2,
            activityOptions,
            i,
            neededUriGrants,
            "newTask",
            request)
        | false;
  }

  public void retrieveSettings() {
    this.mSettingsObserver.initSettings();
  }

  public class SettingsObserver extends ContentObserver {
    public Uri mDexDeveloperModeUri;
    public Uri mDexFontScaleUri;
    public Uri mDexForceImmersiveModeEnabledUri;
    public Uri mDexForceImmersiveModeSettingUri;
    public Uri mDexModeSettingUri;
    public final Uri mDexOnPC;
    public Uri mDexRestartDialogDoNotShowAgainUri;
    public Uri mDexStandaloneRotationUri;
    public Uri mDexStarShowingDelayTimeUri;
    public Uri mDexTouchPadUsingUri;
    public Uri mEdgeUri;
    public Uri mFreeformCaptionTypeUri;
    public Uri mMinimalBatteryUseUri;
    public final Uri mNavigationBarGestureWhileHiddenUri;
    public final Uri mNavigationBarGesturesDetailTypeUri;
    public Uri mNewDexCaptionTypeUri;
    public Uri mNewDexUri;
    public Uri mNotificationBubbleUri;
    public Uri mSmartPopupViewPackageListUri;
    public Uri mSplitGestureUri;
    public Uri mSwipeForPopUpViewCornerAreaUri;
    public final ArrayList mUriList;

    public SettingsObserver(Handler handler) {
      super(handler);
      this.mDexOnPC = Settings.System.getUriFor("dexonpc_connection_state");
      this.mUriList = new ArrayList();
      this.mDexRestartDialogDoNotShowAgainUri =
          Settings.System.getUriFor("display_chooser_do_not_show_again");
      this.mDexDeveloperModeUri = DesktopModeSettings.getUriFor("launch_policy_developer_enabled");
      this.mNavigationBarGestureWhileHiddenUri =
          Settings.Global.getUriFor("navigation_bar_gesture_while_hidden");
      this.mNavigationBarGesturesDetailTypeUri =
          Settings.Global.getUriFor("navigation_bar_gesture_detail_type");
      this.mDexStandaloneRotationUri = DesktopModeSettings.getUriFor("standalone_mode_rotate_app");
      this.mDexFontScaleUri = DesktopModeSettings.getUriFor("font_scale");
      this.mDexTouchPadUsingUri = DesktopModeSettings.getUriFor("touchpad_enabled");
      this.mSplitGestureUri = Settings.Global.getUriFor("open_in_split_screen_view");
      this.mEdgeUri = Settings.Secure.getUriFor("edge_enable");
      this.mDexForceImmersiveModeSettingUri = DesktopModeSettings.getUriFor("taskbar_hide_bar");
      this.mDexForceImmersiveModeEnabledUri =
          DesktopModeSettings.getUriFor("taskbar_hide_bar_enabled");
      if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
        this.mSmartPopupViewPackageListUri =
            Settings.Secure.getUriFor("floating_noti_package_list");
        this.mNotificationBubbleUri = Settings.Secure.getUriFor("notification_bubbles");
      }
      this.mSwipeForPopUpViewCornerAreaUri =
          Settings.Global.getUriFor("freeform_corner_gesture_level");
      if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
        this.mFreeformCaptionTypeUri = Settings.Global.getUriFor("freeform_caption_type");
      }
      if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE) {
        this.mNewDexCaptionTypeUri = Settings.Global.getUriFor("freeform_caption_type_new_dex");
      }
      this.mDexStarShowingDelayTimeUri =
          DesktopModeSettings.getUriFor("mouse_immersive_time_control");
      if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
        this.mDexModeSettingUri = DesktopModeSettings.getUriFor("dex_mode");
        this.mNewDexUri = Settings.System.getUriFor("new_dex");
      }
      this.mMinimalBatteryUseUri = Settings.System.getUriFor("minimal_battery_use");
    }

    public final void initSettings() {
      ContentResolver contentResolver =
          MultiTaskingController.this.mAtm.mContext.getContentResolver();
      this.mUriList.add(this.mDexRestartDialogDoNotShowAgainUri);
      this.mUriList.add(this.mDexDeveloperModeUri);
      this.mUriList.add(this.mDexFontScaleUri);
      this.mUriList.add(this.mNavigationBarGestureWhileHiddenUri);
      this.mUriList.add(this.mNavigationBarGesturesDetailTypeUri);
      this.mUriList.add(this.mSplitGestureUri);
      this.mUriList.add(this.mDexStandaloneRotationUri);
      this.mUriList.add(this.mDexTouchPadUsingUri);
      this.mUriList.add(this.mEdgeUri);
      this.mUriList.add(this.mDexForceImmersiveModeSettingUri);
      this.mUriList.add(this.mDexForceImmersiveModeEnabledUri);
      if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
        this.mUriList.add(this.mSmartPopupViewPackageListUri);
        this.mUriList.add(this.mNotificationBubbleUri);
      }
      this.mUriList.add(this.mSwipeForPopUpViewCornerAreaUri);
      if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
        this.mUriList.add(this.mFreeformCaptionTypeUri);
      }
      if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE) {
        this.mUriList.add(this.mNewDexCaptionTypeUri);
      }
      this.mUriList.add(this.mDexStarShowingDelayTimeUri);
      if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
        this.mUriList.add(this.mDexModeSettingUri);
        this.mUriList.add(this.mNewDexUri);
      }
      this.mUriList.add(this.mMinimalBatteryUseUri);
      Iterator it = this.mUriList.iterator();
      while (it.hasNext()) {
        contentResolver.registerContentObserver((Uri) it.next(), false, this, -1);
      }
      readSettings(null, true);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (uri == null) {
        Slog.w(MultiTaskingController.TAG, "SettingsObserver_onChange: uri is null");
      } else {
        readSettings(uri, false);
      }
    }

    public void readSettings(Uri uri, boolean z) {
      String str;
      ContentResolver contentResolver =
          MultiTaskingController.this.mAtm.mContext.getContentResolver();
      boolean z2 = true;
      if (z || this.mDexRestartDialogDoNotShowAgainUri.equals(uri)) {
        boolean z3 =
            Settings.System.getInt(
                    MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                    "display_chooser_do_not_show_again",
                    0)
                != 0;
        MultiTaskingController.this.mAtm.mDexController.setDoNotShowAgainChecked(z3);
        String str2 = MultiTaskingController.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("SettingsObserver_readSettings: do_not_show=");
        sb.append(z3);
        if (CoreRune.MD_DEBUG_LOG) {
          str = ", Callers=" + Debug.getCallers(2);
        } else {
          str = "";
        }
        sb.append(str);
        Slog.d(str2, sb.toString());
      }
      if (z || this.mDexDeveloperModeUri.equals(uri)) {
        MultiTaskingController.this.mAtm.mDexController.updateDexDeveloperMode(
            DesktopModeSettings.getSettings(
                contentResolver, "launch_policy_developer_enabled", false));
      }
      if (z || this.mDexFontScaleUri.equals(uri)) {
        float settings = DesktopModeSettings.getSettings(contentResolver, "font_scale", 1.0f);
        MultiTaskingController.this.mAtm.mDexController.updateDexFontScaleIfNeeded(settings);
        Slog.d(
            MultiTaskingController.TAG,
            "SettingsObserver_readSettings: dex_font_scale=" + settings);
      }
      if (z || this.mNavigationBarGestureWhileHiddenUri.equals(uri)) {
        MultiTaskingController.this.mIsNavigationModeGesture =
            Settings.Global.getInt(contentResolver, "navigation_bar_gesture_while_hidden", 0) == 1;
      }
      if (z || this.mNavigationBarGesturesDetailTypeUri.equals(uri)) {
        MultiTaskingController.this.mIsGestureTypeSideAndBottom =
            Settings.Global.getInt(contentResolver, "navigation_bar_gesture_detail_type", 1) == 1;
      }
      if (z || this.mSplitGestureUri.equals(uri)) {
        MultiTaskingController multiTaskingController = MultiTaskingController.this;
        multiTaskingController.mIsFullToSplitEnabled =
            Settings.Global.getInt(
                    multiTaskingController.mAtm.mContext.getContentResolver(),
                    "open_in_split_screen_view",
                    0)
                == 1;
      }
      if (z || this.mDexStandaloneRotationUri.equals(uri)) {
        MultiTaskingController.this.mAtm.mDexController.updateDexStandaloneRotationEnabled(
            DesktopModeSettings.getSettings(contentResolver, "standalone_mode_rotate_app", false));
      }
      if (z || this.mDexTouchPadUsingUri.equals(uri)) {
        boolean settings2 =
            DesktopModeSettings.getSettings(contentResolver, "touchpad_enabled", false);
        WindowManagerGlobalLock windowManagerGlobalLock =
            MultiTaskingController.this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            MultiTaskingController.this.mAtm.mDexController.setDexTouchPadEnabledLocked(settings2);
          } finally {
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
      if (z || this.mEdgeUri.equals(uri)) {
        boolean z4 = Settings.Secure.getIntForUser(contentResolver, "edge_enable", 0, -2) == 1;
        WindowManagerGlobalLock windowManagerGlobalLock2 =
            MultiTaskingController.this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock2) {
          try {
            DisplayContent defaultDisplay =
                MultiTaskingController.this.mAtm.mRootWindowContainer.getDefaultDisplay();
            if (defaultDisplay != null) {
              defaultDisplay.getDisplayPolicy().updateEdgeSettings(z4);
            }
          } finally {
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
      if (z || this.mDexForceImmersiveModeSettingUri.equals(uri)) {
        MultiTaskingController.this.mAtm.mDexController.updateForceImmersiveModeSetting(
            DesktopModeSettings.getSettings(
                MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                "taskbar_hide_bar",
                false));
      }
      if (z || this.mDexForceImmersiveModeEnabledUri.equals(uri)) {
        MultiTaskingController.this.mAtm.mDexController.updateForceImmersiveModeState(
            DesktopModeSettings.getSettings(
                MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                "taskbar_hide_bar_enabled",
                false));
      }
      if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
          && MultiTaskingController.this.mAtm.isBooted()
          && (z
              || this.mSmartPopupViewPackageListUri.equals(uri)
              || this.mNotificationBubbleUri.equals(uri))) {
        MultiTaskingController.this.mAtm.mFreeformController.scheduleUnbindSmartPopupViewService(
            "setting_changed");
        MultiTaskingController.this.mAtm.mFreeformController.scheduleBindSmartPopupViewService(
            "setting_changed");
      }
      if (z || this.mSwipeForPopUpViewCornerAreaUri.equals(uri)) {
        MultiTaskingController.this.setCornerGestureCustomValue(
            MultiWindowEdgeDetector.getCornerGestureCustomValue(
                Settings.Global.getInt(contentResolver, "freeform_corner_gesture_level", 2)));
      }
      if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE
          && (z || this.mFreeformCaptionTypeUri.equals(uri))) {
        MultiTaskingController.this.mAtm.mFreeformController.updateFreeformHeaderType(
            Settings.Global.getInt(
                MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                "freeform_caption_type",
                0));
      }
      if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE
          && (z || this.mNewDexCaptionTypeUri.equals(uri))) {
        MultiTaskingController.this.mAtm.mFreeformController.updateNewDexHeaderType(1);
      }
      if (z || this.mDexStarShowingDelayTimeUri.equals(uri)) {
        MultiTaskingController.this.mAtm.mDexController.updateDexStarShowingDelayTime(
            DesktopModeSettings.getSettings(
                MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                "mouse_immersive_time_control",
                -1));
      }
      if (CoreRune.MT_NEW_DEX_DISPLAY_MANAGEMENT) {
        if (z || this.mDexModeSettingUri.equals(uri)) {
          MultiTaskingController.this.mAtm.mNewDexController.updateDesktopModeSettings(
              DesktopModeSettings.getSettingsAsUser(
                      MultiTaskingController.this.mAtm.mContext.getContentResolver(),
                      "dex_mode",
                      DesktopModeSettings.DEX_MODE_DEFAULT_VALUE,
                      0)
                  .equals("new"));
        }
        if (z || this.mNewDexUri.equals(uri)) {
          MultiTaskingController.this.mAtm.mNewDexController.updateNewDexMode(
              Settings.System.getIntForUser(
                  MultiTaskingController.this.mAtm.mContext.getContentResolver(), "new_dex", 0, 0));
        }
      }
      if (z || this.mMinimalBatteryUseUri.equals(uri)) {
        int i = Settings.System.getInt(contentResolver, "minimal_battery_use", 0);
        WindowManagerGlobalLock windowManagerGlobalLock3 =
            MultiTaskingController.this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock3) {
          try {
            MultiTaskingController multiTaskingController2 = MultiTaskingController.this;
            if (i != 1) {
              z2 = false;
            }
            multiTaskingController2.mIsMinimalBatteryUse = z2;
          } finally {
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      }
    }

    public String toString() {
      return "SettingsObserver{mUriList=" + this.mUriList + "}";
    }
  }

  public void notifySecureWindowAdded(WindowState windowState) {
    Task task = windowState.getTask();
    DisplayContent displayContent = windowState.getDisplayContent();
    if (task == null || displayContent == null || !displayContent.isRemoteAppDisplay()) {
      return;
    }
    this.f1743mH.sendMessage(
        this.f1743mH.obtainMessage(1, task.mTaskId, 1, windowState.getOwningPackage()));
  }

  public void notifySecureWindowRemoved(WindowState windowState) {
    Task task = windowState.getTask();
    DisplayContent displayContent = windowState.getDisplayContent();
    if (task == null || displayContent == null || !displayContent.isRemoteAppDisplay()) {
      return;
    }
    this.f1743mH.sendMessage(
        this.f1743mH.obtainMessage(1, task.mTaskId, 0, windowState.getOwningPackage()));
  }

  public boolean minimizeTaskLocked(Task task, boolean z) {
    return minimizeTaskLocked(task, z, -1, -1);
  }

  public boolean minimizeTaskLocked(final Task task, final boolean z, int i, int i2) {
    if (task == null) {
      Slog.w(TAG, "minimizeTaskLocked: fail, task is null");
      return false;
    }
    if (!task.canMinimize()) {
      String str = TAG;
      Slog.w(str, "minimizeTaskLocked: fail, task can't minimize. t=" + task);
      if (task.isAnimating() && task.getWindowingMode() == 5) {
        Slog.w(str, "minimizeTaskLocked: cancelAnimation, t=" + task);
        task.cancelAnimation();
      }
      return false;
    }
    DisplayContent displayContent = task.getDisplayContent();
    if (displayContent != null
        && !displayContent.isDesktopModeEnabled()
        && displayContent.isInputMethodTargetTaskAndShowing(task)
        && displayContent.getInsetsStateController().getImeSourceProvider().isImeShowing()) {
      InputMethodManagerInternal.get().hideCurrentInputMethod(43);
      this.mPendingMoveToTaskId = task.mTaskId;
      Runnable runnable =
          new Runnable() { // from class:
                           // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              MultiTaskingController.this.lambda$minimizeTaskLocked$4(task, z);
            }
          };
      this.mMoveToBackTaskWithIme = runnable;
      this.f1743mH.postDelayed(runnable, 400L);
      return true;
    }
    return task.moveTaskToBack(task, null, true, z, i, i2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$minimizeTaskLocked$4(Task task, boolean z) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        task.moveTaskToBack(task, null, true, z);
        this.mPendingMoveToTaskId = -1;
        if (CoreRune.SAFE_DEBUG) {
          Slog.i(TAG, "minimizeTaskLocked: minimizeDelayTask=" + task);
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void minimizeAllTasksLocked(int i, boolean z) {
    minimizeAllTasksLocked(i, z, -1);
  }

  public void minimizeAllTasksLocked(int i, boolean z, int i2) {
    ActivityRecord activityRecord;
    DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
    if (displayContent == null) {
      return;
    }
    if (CoreRune.SAFE_DEBUG) {
      Slog.i(TAG, "minimizeAllFreeformTasks: displayId=" + i);
    }
    if (displayContent.isDexMode()
        && this.mAtm.mDexController.handleDexMinimizeToggleLocked(displayContent)) {
      return;
    }
    if (MultiWindowCoreState.MW_MULTISTAR_BLOCKED_MINIMIZED_FREEFORM_ENABLED
        && !displayContent.isDexMode()) {
      Slog.i(TAG, "Blocked minimized freeform by multistar");
      return;
    }
    if (!CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
      i2 = -1;
    }
    boolean z2 = i == 0 && !displayContent.isDesktopModeEnabled();
    try {
      this.mAtm.deferWindowLayout();
      if (z2) {
        this.mAtm.mFreeformController.deferMinimizeStateChangedCallbacks();
      }
      final ArrayList arrayList = new ArrayList();
      displayContent
          .getDefaultTaskDisplayArea()
          .forAllRootTasks(
              new Consumer() { // from class:
                               // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  MultiTaskingController.this.lambda$minimizeAllTasksLocked$5(
                      arrayList, (Task) obj);
                }
              },
              true);
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        Task task = (Task) it.next();
        if (minimizeTaskLocked(task, z)) {
          if (displayContent.isDexMode()) {
            this.mAtm.mDexController.addToggleTaskLocked(task);
          }
          if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && i == 0 && i2 != -1) {
            task.setLastMinimizedDisplayType(i2);
          }
        }
      }
      if (CoreRune.MT_NEW_DEX_PIP && (activityRecord = this.mTmpPipCandidate) != null) {
        this.mTmpPipCandidate = null;
        if (this.mAtm.mActivityClientController.requestPictureInPictureMode(activityRecord)) {
          Slog.d(TAG, "minimizeAllTasksLocked: entered pip, r=" + activityRecord);
        } else {
          activityRecord.supportsEnterPipOnTaskSwitch = false;
          Slog.d(TAG, "minimizeAllTasksLocked: failed to enter pip, r=" + activityRecord);
        }
      }
    } finally {
      if (z2) {
        this.mAtm.mFreeformController.continueMinimizeStateChangedCallbacks();
      }
      this.mAtm.continueWindowLayout();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$minimizeAllTasksLocked$5(ArrayList arrayList, Task task) {
    ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
    if (CoreRune.MT_NEW_DEX_PIP
        && this.mTmpPipCandidate == null
        && topNonFinishingActivity != null) {
      boolean z = topNonFinishingActivity.supportsEnterPipOnTaskSwitch;
      topNonFinishingActivity.supportsEnterPipOnTaskSwitch = true;
      if (canEnterPipWhileMinimizingAllTasks(task, topNonFinishingActivity)) {
        this.mTmpPipCandidate = topNonFinishingActivity;
        Slog.d(TAG, "minimizeAllTasksLocked: found pipCandidate, r=" + topNonFinishingActivity);
        return;
      }
      topNonFinishingActivity.supportsEnterPipOnTaskSwitch = z;
    }
    if (task.canMinimize()) {
      arrayList.add(task);
    }
  }

  public static boolean canEnterPipWhileMinimizingAllTasks(
      Task task, ActivityRecord activityRecord) {
    DisplayContent displayContent = task.getDisplayContent();
    if (displayContent != null
        && displayContent.isNewDexMode()
        && displayContent.getDefaultTaskDisplayArea().getRootPinnedTask() == null) {
      return (task.inFullscreenWindowingMode()
              || (CoreRune.MT_NEW_DEX_PIP_ON_FREEFORM && task.inFreeformWindowingMode()))
          && task.isVisible()
          && activityRecord.checkEnterPictureInPictureState("new_dex", true)
          && activityRecord.pictureInPictureArgs.isAutoEnterEnabled();
    }
    return false;
  }

  public void notifyFocusedDisplayChangedLocked(final int i) {
    this.f1743mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda17
          @Override // java.lang.Runnable
          public final void run() {
            MultiTaskingController.lambda$notifyFocusedDisplayChangedLocked$6(i);
          }
        });
  }

  public static /* synthetic */ void lambda$notifyFocusedDisplayChangedLocked$6(int i) {
    StatusBarManagerInternal statusBarManagerInternal =
        (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
    if (statusBarManagerInternal != null) {
      statusBarManagerInternal.onFocusedDisplayChanged(i);
    }
  }

  public void startUser(final int i, boolean z, boolean z2) {
    this.f1743mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda13
          @Override // java.lang.Runnable
          public final void run() {
            MultiTaskingController.this.lambda$startUser$7(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startUser$7(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
          this.mAtm.mFreeformController.scheduleUnbindMinimizeContainerService("startUser");
          if (Settings.Secure.getIntForUser(
                  this.mAtm.mContext.getContentResolver(), "notification_bubbles", 0, i)
              == 2) {
            this.mAtm.mFreeformController.scheduleBindSmartPopupViewService("startUser");
          } else {
            this.mAtm.mFreeformController.scheduleUnbindSmartPopupViewService("startUser");
          }
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isFullToSplitEnabled() {
    return this.mIsFullToSplitEnabled;
  }

  /* renamed from: com.android.server.wm.MultiTaskingController$H */
  public final class HandlerC2944H extends Handler {
    public HandlerC2944H(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 0) {
        int i2 = message.arg1;
        int i3 = message.arg2;
        Rect rect = (Rect) message.obj;
        WindowManagerGlobalLock windowManagerGlobalLock =
            MultiTaskingController.this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            MultiTaskingController.this.mAtm.mFreeformController
                .setFreeformWindowingModeByCornerGestureLocked(i2, i3, rect);
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (CoreRune.MW_FREEFORM_CORNER_GESTURE_SA_LOGGING) {
          CoreSaLogger.logForAdvanced("2004", "From Gesture");
          return;
        }
        return;
      }
      if (i == 1) {
        RemoteAppModeManagerInternal remoteAppModeManagerInternal =
            (RemoteAppModeManagerInternal)
                LocalServices.getService(RemoteAppModeManagerInternal.class);
        if (remoteAppModeManagerInternal == null) {
          Slog.w(
              MultiTaskingController.TAG,
              "handleMessage: can't find localService, what=" + message.what);
          return;
        }
        int i4 = message.arg1;
        Object obj = message.obj;
        String str = obj != null ? (String) obj : "";
        if (message.arg2 == 1) {
          remoteAppModeManagerInternal.onSecuredAppLaunched(i4, str);
          return;
        } else {
          remoteAppModeManagerInternal.onSecuredAppRemoved(i4, str);
          return;
        }
      }
      if (i == 3) {
        MultiTaskingController multiTaskingController = MultiTaskingController.this;
        multiTaskingController.forAllRemoteAppTransitionListeners(
            multiTaskingController.mNotifyStartRecentsAnimation, message);
        return;
      }
      if (i == 4) {
        MultiTaskingController multiTaskingController2 = MultiTaskingController.this;
        multiTaskingController2.forAllRemoteAppTransitionListeners(
            multiTaskingController2.mNotifyFinishRecentsAnimation, message);
      } else if (i == 5) {
        MultiTaskingController multiTaskingController3 = MultiTaskingController.this;
        multiTaskingController3.forAllRemoteAppTransitionListeners(
            multiTaskingController3.mNotifyStartHomeAnimation, message);
      } else {
        if (i != 6) {
          return;
        }
        MultiTaskingController multiTaskingController4 = MultiTaskingController.this;
        multiTaskingController4.forAllRemoteAppTransitionListeners(
            multiTaskingController4.mNotifyWallpaperVisibilityChanged, message);
      }
    }
  }

  public int getDexMode() {
    int dexModeLocked;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return dexModeLocked;
  }

  public void showCanNotSwitchUserToast() {
    this.mAtm.mDexController.showCanNotSwitchUserToast();
  }

  public boolean isDeferredTaskFocusChange() {
    return this.mDeferFocusChanging;
  }

  public boolean shouldIgnoreTaskFocusChange(InputTarget inputTarget, int i, int i2, int i3) {
    boolean z;
    boolean z2;
    if (!this.mIsNavigationModeGesture && !isInImmersiveSplitScreenMode()) {
      return false;
    }
    WindowState windowState = inputTarget.getWindowState();
    DisplayContent displayContent = windowState != null ? windowState.getDisplayContent() : null;
    if (displayContent == null || !displayContent.isDefaultDisplay || displayContent.isDexMode()) {
      z = false;
      z2 = false;
    } else {
      z = isMultiWindowActivated(displayContent);
      z2 = isInImmersiveSplitScreenMode() && windowState.inSplitScreenWindowingMode();
    }
    if (!z && !z2) {
      this.mDeferFocusChanging = false;
      return i != 0;
    }
    if (i != 0) {
      if (i != 1) {
        if (i != 3) {
          return false;
        }
        this.mDeferFocusChanging = false;
      } else if (this.mDeferFocusChanging) {
        this.mDeferFocusChanging = false;
        if (!z2 || !isShowingTransientBars(displayContent)) {
          return false;
        }
      }
    } else {
      if (!isInSwipeGestureArea(displayContent, i2, i3)
          && !isInImmersiveSplitModeArea(windowState, i2, i3)
          && !isInThreeButtonsGestureArea(displayContent, i2, i3)) {
        return false;
      }
      this.mDeferFocusChanging = true;
    }
    return true;
  }

  public final boolean isInSystemBarArea(DisplayContent displayContent, int i, int i2, int i3) {
    InsetsState rawInsetsState = displayContent.getInsetsStateController().getRawInsetsState();
    int sourceSize = rawInsetsState.sourceSize();
    for (int i4 = 0; i4 < sourceSize; i4++) {
      InsetsSource sourceAt = rawInsetsState.sourceAt(i4);
      if (sourceAt.getType() == i && !sourceAt.getFrame().isEmpty()) {
        return sourceAt.getFrame().contains(i2, i3);
      }
    }
    return false;
  }

  public final boolean isInSwipeGestureArea(DisplayContent displayContent, int i, int i2) {
    boolean z = false;
    if (!this.mIsGestureTypeSideAndBottom) {
      return false;
    }
    Region obtain = Region.obtain();
    displayContent.calculateSystemGestureExclusion(obtain, null);
    if ((obtain.isEmpty() || !obtain.contains(i, i2))
        && (isInSystemBarArea(displayContent, WindowInsets.Type.navigationBars(), i, i2)
            || displayContent.isInSidesGestureArea(i, i2))) {
      z = true;
    }
    obtain.recycle();
    return z;
  }

  public final boolean isInThreeButtonsGestureArea(DisplayContent displayContent, int i, int i2) {
    WindowState window =
        displayContent.getWindow(
            new Predicate() { // from class:
                              // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda20
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$isInThreeButtonsGestureArea$8;
                lambda$isInThreeButtonsGestureArea$8 =
                    MultiTaskingController.lambda$isInThreeButtonsGestureArea$8((WindowState) obj);
                return lambda$isInThreeButtonsGestureArea$8;
              }
            });
    if (window == null || window.mRemoved) {
      return false;
    }
    return window.getWindowFrames().mFrame.contains(i, i2);
  }

  public static /* synthetic */ boolean lambda$isInThreeButtonsGestureArea$8(
      WindowState windowState) {
    return windowState.mAttrs.type == 2274;
  }

  public final boolean isInImmersiveSplitModeArea(WindowState windowState, int i, int i2) {
    DisplayContent displayContent = windowState.getDisplayContent();
    if (!isInImmersiveSplitScreenMode()
        || !displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()
        || !windowState.inSplitScreenWindowingMode()) {
      return false;
    }
    if (!isInSystemBarArea(displayContent, WindowInsets.Type.navigationBars(), i, i2)
        && !isInSystemBarArea(displayContent, WindowInsets.Type.statusBars(), i, i2)) {
      this.mTmpRect.set(displayContent.getBounds());
      int i3 = this.mSwipeGestureThreshold;
      if (i >= i3 && i <= this.mTmpRect.right - i3) {
        return false;
      }
    }
    return true;
  }

  public final boolean isMultiWindowActivated(DisplayContent displayContent) {
    if (displayContent == null) {
      return false;
    }
    TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
    if (defaultTaskDisplayArea.isSplitScreenModeActivated()) {
      return true;
    }
    Task topRootTaskInWindowingMode = defaultTaskDisplayArea.getTopRootTaskInWindowingMode(5);
    return topRootTaskInWindowingMode != null && topRootTaskInWindowingMode.isVisible();
  }

  public boolean isInImmersiveSplitScreenMode() {
    return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
        || MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
  }

  public final boolean isShowingTransientBars(DisplayContent displayContent) {
    InsetsPolicy insetsPolicy = displayContent.getInsetsPolicy();
    if (insetsPolicy.isTransient(WindowInsets.Type.statusBars())
        || insetsPolicy.isTransient(WindowInsets.Type.navigationBars())) {
      return true;
    }
    return this.mIsGestureTypeSideAndBottom
        && insetsPolicy.isTransient(WindowInsets.Type.systemGestures());
  }

  public final void updateSystemGestureThreshold() {
    this.mSwipeGestureThreshold =
        this.mWm
            .getDefaultDisplayContentLocked()
            .getDisplayPolicy()
            .getCurrentUserResources()
            .getDimensionPixelSize(17106189);
  }

  public void registerRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) {
    synchronized (this.mRemoteAppTransitionListeners) {
      this.mRemoteAppTransitionListeners.register(iRemoteAppTransitionListener);
    }
  }

  public void unregisterRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) {
    synchronized (this.mRemoteAppTransitionListeners) {
      this.mRemoteAppTransitionListeners.unregister(iRemoteAppTransitionListener);
    }
  }

  public void notifyStartRecentsAnimation(boolean z) {
    this.f1743mH.removeMessages(3);
    this.f1743mH.obtainMessage(3, z ? 1 : 0, 0).sendToTarget();
  }

  public void notifyFinishRecentsAnimation(boolean z) {
    this.f1743mH.removeMessages(4);
    this.f1743mH.obtainMessage(4, z ? 1 : 0, 0).sendToTarget();
  }

  public void notifyWallpaperVisibilityChanged(DisplayContent displayContent) {
    if (displayContent.getDefaultTaskDisplayArea() == null) {
      return;
    }
    this.f1743mH.removeMessages(6);
    boolean isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
    final boolean[] zArr = {false};
    displayContent
        .getDefaultTaskDisplayArea()
        .forAllRootTasks(
            new Predicate() { // from class:
                              // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda18
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$notifyWallpaperVisibilityChanged$9;
                lambda$notifyWallpaperVisibilityChanged$9 =
                    MultiTaskingController.lambda$notifyWallpaperVisibilityChanged$9(
                        zArr, (Task) obj);
                return lambda$notifyWallpaperVisibilityChanged$9;
              }
            });
    this.f1743mH.obtainMessage(6, isWallpaperVisible ? 1 : 0, zArr[0] ? 1 : 0).sendToTarget();
  }

  public static /* synthetic */ boolean lambda$notifyWallpaperVisibilityChanged$9(
      boolean[] zArr, Task task) {
    if (!task.isActivityTypeHomeOrRecents()) {
      return false;
    }
    zArr[0] = task.isVisibleRequested();
    return true;
  }

  public final void forAllRemoteAppTransitionListeners(
      AppTransitionConsumer appTransitionConsumer, Message message) {
    synchronized (this.mRemoteAppTransitionListeners) {
      for (int beginBroadcast = this.mRemoteAppTransitionListeners.beginBroadcast() - 1;
          beginBroadcast >= 0;
          beginBroadcast--) {
        try {
          appTransitionConsumer.accept(
              (IRemoteAppTransitionListener)
                  this.mRemoteAppTransitionListeners.getBroadcastItem(beginBroadcast),
              message);
        } catch (RemoteException unused) {
        }
      }
      this.mRemoteAppTransitionListeners.finishBroadcast();
    }
  }

  public int getSystemUiUid() {
    if (this.mSystemUIUid == -1) {
      this.mSystemUIUid =
          this.mAtm
              .getPackageManagerInternalLocked()
              .getPackageUid("com.android.systemui", 1048576L, 0);
    }
    return this.mSystemUIUid;
  }

  public boolean getEmbedActivityPackageEnabled(String str, int i) {
    return this.mAtm.mExt.mActivityEmbeddedController.getEnabled(str, i) != 2;
  }

  public void setEmbedActivityPackageEnabled(String str, boolean z, int i) {
    int i2 = z ? 1 : 2;
    if (this.mAtm.mExt.mActivityEmbeddedController.getEnabled(str, i) == i2) {
      return;
    }
    this.mAtm.mExt.mActivityEmbeddedController.setEnabled(str, i2, i);
    removeEmbedActivityTaskIfNeeded(str, i);
  }

  public void updateEmbedActivityPackageEnabled(String str, int i, int i2, boolean z) {
    if (this.mAtm.mExt.mActivityEmbeddedController.getEnabled(str, i2) == 0 || z) {
      this.mAtm.mExt.mActivityEmbeddedController.setEnabled(str, i, i2);
    }
  }

  public List getSupportEmbedActivityPackages() {
    if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
      return List.of();
    }
    return this.mActivityEmbeddedPackageRepository.getActivityEmbeddedPackages();
  }

  public void dumpActivityEmbeddedPackageRepository(PrintWriter printWriter) {
    this.mActivityEmbeddedPackageRepository.dump(printWriter);
  }

  public void removeEmbedActivityTaskIfNeeded(final String str, final int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        final ArrayList arrayList = new ArrayList();
        this.mAtm
            .mRootWindowContainer
            .getDefaultTaskDisplayArea()
            .forAllLeafTasks(
                new Consumer() { // from class:
                                 // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda7
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    MultiTaskingController.this.lambda$removeEmbedActivityTaskIfNeeded$11(
                        i, str, arrayList, (Task) obj);
                  }
                },
                true);
        if (arrayList.isEmpty()) {
          removeEmbedActivityProcessIfNeeded(str, i);
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
          Task task = (Task) it.next();
          if (task.isVisible()) {
            task.mIsWaitingRemoveEmbedActivityTask = true;
            task.getRootTask().moveTaskToBack(task);
          } else {
            removeEmbedActivityTaskAndProcessIfNeeded(task);
          }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeEmbedActivityTaskIfNeeded$11(
      int i, String str, ArrayList arrayList, Task task) {
    ComponentName componentName;
    if (this.mAtm.mExt.mActivityEmbeddedController.findTargetUserId(task.mUserId) == i
        && (componentName = task.realActivity) != null
        && componentName.getPackageName().equals(str)) {
      arrayList.add(task);
    }
  }

  public void removeWaitingEmbedActivityTaskIfNeeded(ActivityRecord activityRecord) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task rootTask = activityRecord.getRootTask();
        if (rootTask != null && rootTask.mIsWaitingRemoveEmbedActivityTask) {
          removeEmbedActivityTaskAndProcessIfNeeded(rootTask);
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void removeEmbedActivityTaskAndProcessIfNeeded(final Task task) {
    final WindowProcessController rootProcess = task.getRootProcess();
    if (rootProcess != null) {
      if (rootProcess.getPid() == ActivityManagerService.MY_PID) {
        if (CoreRune.SAFE_DEBUG) {
          Slog.w(
              TAG,
              "Do not kill system process, app=" + rootProcess + " callers=" + Debug.getCallers(3));
          return;
        }
        return;
      }
      this.f1743mH.post(
          new Runnable() { // from class:
                           // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              MultiTaskingController.this.lambda$removeEmbedActivityTaskAndProcessIfNeeded$12(
                  task, rootProcess);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeEmbedActivityTaskAndProcessIfNeeded$12(
      Task task, WindowProcessController windowProcessController) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAtm.mTaskSupervisor.removeTask(task, false, false, "embedded-package-changed");
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    this.mAtm.mAmInternal.killProcess(
        windowProcessController.mName, windowProcessController.mUid, "embedded-package-changed");
  }

  public void removeEmbedActivityProcessIfNeeded(String str, int i) {
    try {
      PackageInfo packageInfoAsUser =
          this.mAtm.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
      if (packageInfoAsUser != null) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
        final WindowProcessController processController =
            activityTaskManagerService.getProcessController(
                applicationInfo.processName, applicationInfo.uid);
        if (processController != null) {
          if (processController.getPid() == ActivityManagerService.MY_PID) {
            if (CoreRune.SAFE_DEBUG) {
              Slog.w(
                  TAG,
                  "Do not kill system process, app="
                      + processController
                      + " callers="
                      + Debug.getCallers(3));
              return;
            }
            return;
          }
          this.f1743mH.post(
              new Runnable() { // from class:
                               // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                  MultiTaskingController.this.lambda$removeEmbedActivityProcessIfNeeded$13(
                      processController);
                }
              });
        }
      }
    } catch (PackageManager.NameNotFoundException unused) {
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeEmbedActivityProcessIfNeeded$13(
      WindowProcessController windowProcessController) {
    this.mAtm.mAmInternal.killProcess(
        windowProcessController.mName, windowProcessController.mUid, "embedded-package-changed");
  }

  public boolean exitMultiWindow(IBinder iBinder) {
    return exitMultiWindow(iBinder, true, false);
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Removed duplicated region for block: B:18:0x0052 A[Catch: all -> 0x0114, TryCatch #0 {all -> 0x0114, blocks: (B:4:0x0008, B:6:0x000f, B:8:0x0015, B:13:0x0044, B:16:0x004c, B:18:0x0052, B:19:0x0054, B:21:0x005e, B:24:0x006a, B:26:0x0074, B:27:0x0081, B:29:0x008f, B:30:0x00ed, B:33:0x00f4, B:39:0x00b3, B:41:0x00bd, B:43:0x00c3, B:44:0x00cb, B:45:0x00f9, B:46:0x010f, B:49:0x001e, B:51:0x0022, B:53:0x002d), top: B:3:0x0008 }] */
  /* JADX WARN: Removed duplicated region for block: B:32:0x00f3  */
  /* JADX WARN: Removed duplicated region for block: B:39:0x00b3 A[Catch: all -> 0x0114, TryCatch #0 {all -> 0x0114, blocks: (B:4:0x0008, B:6:0x000f, B:8:0x0015, B:13:0x0044, B:16:0x004c, B:18:0x0052, B:19:0x0054, B:21:0x005e, B:24:0x006a, B:26:0x0074, B:27:0x0081, B:29:0x008f, B:30:0x00ed, B:33:0x00f4, B:39:0x00b3, B:41:0x00bd, B:43:0x00c3, B:44:0x00cb, B:45:0x00f9, B:46:0x010f, B:49:0x001e, B:51:0x0022, B:53:0x002d), top: B:3:0x0008 }] */
  /* JADX WARN: Removed duplicated region for block: B:40:0x00bb  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean exitMultiWindow(IBinder iBinder, boolean z, boolean z2) {
    boolean z3;
    Task task;
    Task asTask;
    ActivityRecord activityRecord;
    boolean z4;
    int windowingMode;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ActivityRecord activityRecord2 = null;
        if (iBinder instanceof ActivityRecord.Token) {
          activityRecord = ActivityRecord.isInRootTaskLocked(iBinder);
          asTask = activityRecord != null ? activityRecord.getTask() : null;
          z4 = false;
        } else if ((iBinder instanceof WindowContainer.RemoteToken)
            && (((WindowContainer.RemoteToken) iBinder).getContainer() instanceof Task)) {
          asTask = ((WindowContainer.RemoteToken) iBinder).getContainer().asTask();
          activityRecord = asTask.topRunningActivity();
          z4 = true;
        } else {
          z3 = false;
          task = null;
          if (activityRecord2 != null && task != null && task.getDisplayArea() != null) {
            windowingMode = task.getWindowingMode();
            IBinder iBinder2 = iBinder;
            if (z3) {
              iBinder2 = activityRecord2.token;
            }
            if (!WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration())
                && task.getDisplayArea().isSplitScreenModeActivated()) {
              if (!z) {
                if (task.getWindowConfiguration().getStageType() == 1) {
                  task = task.getDisplayArea().getRootSideStageTask().getTopMostTask();
                } else {
                  task = task.getDisplayArea().getRootMainStageTask().getTopMostTask();
                }
              }
              if (task != null) {
                TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                taskOrganizerInfo.setExitSplitScreenTopTaskId(task.mTaskId);
                taskOrganizerInfo.setExitSplitScreenStageType(
                    task.getWindowConfiguration().getStageType());
                this.mAtm.mTaskOrganizerController.onSplitLayoutChangeRequested(
                    taskOrganizerInfo.toBundle());
              }
            } else if (5 != windowingMode) {
              this.mAtm.mActivityClientController.toggleFreeformWindowingMode(iBinder2);
            } else if (1 == windowingMode) {
              if (task.isDexMode()) {
                this.mAtm.mActivityClientController.toggleFreeformWindowingMode(iBinder2);
              }
            } else {
              Slog.w(
                  TAG,
                  "exitMultiWindow: invalid windowing mode to request mode="
                      + activityRecord2.getWindowingMode()
                      + ", r="
                      + activityRecord2);
            }
            boolean z5 = activityRecord2.getWindowingMode() == 1;
            WindowManagerService.resetPriorityAfterLockedSection();
            return z5;
          }
          Slog.w(TAG, "exitMultiWindow: cannot find task, token=" + iBinder);
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        Task task2 = asTask;
        activityRecord2 = activityRecord;
        task = task2;
        z3 = z4;
        if (activityRecord2 != null) {
          windowingMode = task.getWindowingMode();
          IBinder iBinder22 = iBinder;
          if (z3) {}
          if (!WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration())) {}
          if (5 != windowingMode) {}
          if (activityRecord2.getWindowingMode() == 1) {}
          WindowManagerService.resetPriorityAfterLockedSection();
          return z5;
        }
        Slog.w(TAG, "exitMultiWindow: cannot find task, token=" + iBinder);
        WindowManagerService.resetPriorityAfterLockedSection();
        return false;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public int getMultiWindowModeStates(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return 0;
        }
        int i2 = displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated() ? 2 : 0;
        final int[] iArr = new int[1];
        displayContent.forAllRootTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda12
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                MultiTaskingController.lambda$getMultiWindowModeStates$14(iArr, (Task) obj);
              }
            });
        int i3 = iArr[0] | i2;
        WindowManagerService.resetPriorityAfterLockedSection();
        return i3;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public static /* synthetic */ void lambda$getMultiWindowModeStates$14(int[] iArr, Task task) {
    if (task.topRunningActivityLocked() != null) {
      int windowingMode = task.getWindowingMode();
      if (windowingMode != 2) {
        if (windowingMode == 5 && !task.isUnderHomeRootTask()) {
          iArr[0] = iArr[0] | 1;
          return;
        }
        return;
      }
      iArr[0] = iArr[0] | 4;
    }
  }

  public void deferEnsureConfig() {
    this.mDeferEnsureConfig = true;
  }

  public void continueEnsureConfig() {
    this.mDeferEnsureConfig = false;
  }

  public boolean isEnsureConfigDeferred() {
    return this.mDeferEnsureConfig;
  }

  public ParceledListSlice getTaskInfoFromPackageName(final String str) {
    ParceledListSlice parceledListSlice;
    if (str == null) {
      return null;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        for (int childCount = this.mAtm.mRootWindowContainer.getChildCount() - 1;
            childCount >= 0;
            childCount--) {
          ((DisplayContent) this.mAtm.mRootWindowContainer.getChildAt(childCount))
              .forAllActivities(
                  new Consumer() { // from class:
                                   // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      MultiTaskingController.lambda$getTaskInfoFromPackageName$15(
                          str, arrayList2, arrayList, (ActivityRecord) obj);
                    }
                  });
        }
        parceledListSlice = new ParceledListSlice(arrayList);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return parceledListSlice;
  }

  public static /* synthetic */ void lambda$getTaskInfoFromPackageName$15(
      String str, ArrayList arrayList, ArrayList arrayList2, ActivityRecord activityRecord) {
    String str2 = activityRecord.packageName;
    if (str2 == null
        || !str2.equals(str)
        || arrayList.contains(Integer.valueOf(activityRecord.getTask().mTaskId))) {
      return;
    }
    ActivityManager.RecentTaskInfo recentTaskInfo = new ActivityManager.RecentTaskInfo();
    activityRecord.getTask().fillTaskInfo(recentTaskInfo);
    arrayList2.add(recentTaskInfo);
    arrayList.add(Integer.valueOf(recentTaskInfo.taskId));
    Slog.d(TAG, "getTaskIdFromPackageName, recentTaskInfo=" + recentTaskInfo);
  }

  public boolean removeFocusedTask(int i) {
    if (i == -1) {
      return false;
    }
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
        if (displayContent == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return false;
        }
        final boolean[] zArr = {false};
        displayContent.forAllTaskDisplayAreas(
            new Consumer() { // from class:
                             // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda6
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                MultiTaskingController.this.lambda$removeFocusedTask$16(
                    zArr, (TaskDisplayArea) obj);
              }
            });
        boolean z = zArr[0];
        WindowManagerService.resetPriorityAfterLockedSection();
        return z;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeFocusedTask$16(
      boolean[] zArr, TaskDisplayArea taskDisplayArea) {
    ActivityRecord focusedActivity = taskDisplayArea.getFocusedActivity();
    if (focusedActivity == null || focusedActivity.getTask() == null) {
      return;
    }
    if (focusedActivity.isActivityTypeHomeOrRecents()) {
      Task rootTask = taskDisplayArea.getRootTask(5, 1);
      if (rootTask == null || rootTask.getTopMostTask() == null) {
        return;
      }
      Slog.d(TAG, "removeFocusedTask, topMostFreeformTask=" + rootTask.getTopMostTask());
      this.mAtm.removeTask(rootTask.getTopMostTask().mTaskId);
      zArr[0] = true;
      return;
    }
    Slog.d(TAG, "removeFocusedTask, focusedTask=" + focusedActivity.getTask());
    this.mAtm.removeTask(focusedActivity.getTask().mTaskId);
    zArr[0] = true;
  }

  public void registerMinimizeAllReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.android.multiwindow.MINIMIZE_ALL");
    intentFilter.addAction("com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM");
    this.mAtm.mContext.registerReceiver(
        this.mMinimizeAllReceiver,
        intentFilter,
        "android.permission.MANAGE_ACTIVITY_STACKS",
        this.f1743mH);
  }

  public int getMultiSplitFlags() {
    int i;
    TaskDisplayArea defaultTaskDisplayArea =
        this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
    Task task =
        defaultTaskDisplayArea.getTask(
            new Predicate() { // from class:
                              // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda10
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$getMultiSplitFlags$17;
                lambda$getMultiSplitFlags$17 =
                    MultiTaskingController.lambda$getMultiSplitFlags$17((Task) obj);
                return lambda$getMultiSplitFlags$17;
              }
            });
    int i2 = 0;
    if (task != null) {
      int i3 = 2;
      if (!this.mAtm.mKeyguardController.isKeyguardLocked(0)) {
        if (task.isActivityTypeHome() || task.isActivityTypeRecents()) {
          i3 = 4;
        } else if (task.supportsMultiWindowInDisplayArea(defaultTaskDisplayArea)) {
          i3 = 1;
        }
      }
      if (defaultTaskDisplayArea.isMultiSplitActive()) {
        i = i3 | 32;
      } else {
        i = defaultTaskDisplayArea.isSplitScreenModeActivated() ? i3 | 16 : i3 | 8;
      }
      i2 = i | getCurrentMultiSplitSide(defaultTaskDisplayArea);
    }
    return ((i2 & 1) == 0 && (i2 & 4) == 0)
        ? i2
        : i2 | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
  }

  public static /* synthetic */ boolean lambda$getMultiSplitFlags$17(Task task) {
    return (task.inFullscreenWindowingMode() || task.inSplitScreenWindowingMode())
        && task.topRunningActivity() != null;
  }

  public final int getCurrentMultiSplitSide(TaskDisplayArea taskDisplayArea) {
    boolean z = false;
    int stagePosition =
        taskDisplayArea.getRootMainStageTask() != null
            ? taskDisplayArea.getRootMainStageTask().getStagePosition()
            : 0;
    int stagePosition2 =
        taskDisplayArea.getRootSideStageTask() != null
            ? taskDisplayArea.getRootSideStageTask().getStagePosition()
            : 0;
    int stagePosition3 =
        (!CoreRune.MW_MULTI_SPLIT || taskDisplayArea.getRootCellStageTask() == null)
            ? 0
            : taskDisplayArea.getRootCellStageTask().getStagePosition();
    if (CoreRune.MW_MULTI_SPLIT && !this.mAtm.mWindowManager.isFolded()) {
      z = true;
    }
    if (z) {
      if (isVerticalDivision(stagePosition, stagePosition2)) {
        if ((stagePosition3 & 8) != 0) {
          return 128;
        }
        return ((stagePosition3 & 32) == 0 && stagePosition2 == 32) ? 128 : 512;
      }
      if ((stagePosition3 & 16) != 0) {
        return 256;
      }
      return ((stagePosition3 & 64) == 0 && stagePosition2 == 64) ? 256 : 1024;
    }
    DisplayContent defaultDisplay = this.mAtm.mRootWindowContainer.getDefaultDisplay();
    DisplayPolicy displayPolicy = defaultDisplay.getDisplayPolicy();
    int i = defaultDisplay.getDisplayInfo().logicalWidth;
    int i2 = defaultDisplay.getDisplayInfo().logicalHeight;
    int navBarPosition = displayPolicy.getNavBarPosition();
    if (navBarPosition == 1) {
      return 512;
    }
    if (navBarPosition == 2) {
      return 128;
    }
    if (navBarPosition != 4) {
      return 64;
    }
    return i > i2 ? 128 : 256;
  }

  public void updateFreeformStashState(
      WindowContainer windowContainer, WindowContainerTransaction.Change change) {
    Task asTask = windowContainer.asTask();
    if (asTask == null) {
      Slog.w(
          TAG,
          "updateFocusForFreeformStash: failed, we support Task only now!, wc=" + windowContainer);
      return;
    }
    if (!asTask.isLeafTask()) {
      asTask =
          asTask.getTask(
              new Predicate() { // from class:
                                // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda15
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$updateFreeformStashState$18;
                  lambda$updateFreeformStashState$18 =
                      MultiTaskingController.lambda$updateFreeformStashState$18((Task) obj);
                  return lambda$updateFreeformStashState$18;
                }
              });
    }
    if (asTask == null || !asTask.inFreeformWindowingMode()) {
      String str = TAG;
      StringBuilder sb = new StringBuilder();
      sb.append("updateFocusForFreeformStash: failed, tid #");
      sb.append(asTask != null ? Integer.valueOf(asTask.mTaskId) : "null");
      Slog.w(str, sb.toString());
      return;
    }
    if (change.hasChangeFreeformStashScale()) {
      asTask.setFreeformStashed(change.getChangeFreeformStashScale());
    }
    if (change.hasChangeFreeformStashMode() && !asTask.isMinimized()) {
      int changeFreeformStashMode = change.getChangeFreeformStashMode();
      asTask.setFreeformStashMode(changeFreeformStashMode);
      if (changeFreeformStashMode == 2) {
        asTask.adjustFocusToNextFocusableTask("setStashScaled");
      } else {
        asTask.setFreeformStashed(1.0f);
        this.mAtm.setFocusedTask(asTask.mTaskId);
        this.mAtm.mTaskSupervisor.updateTopResumedActivityIfNeeded("stash-update");
      }
    }
    if (change.isForceTaskInfoChangeRequested()) {
      Slog.d(TAG, "updateFreeformStashState: force taskInfoChanged , t #" + asTask.mTaskId);
      asTask.dispatchTaskInfoChangedIfNeeded(true);
    }
  }

  public static /* synthetic */ boolean lambda$updateFreeformStashState$18(Task task) {
    return task.isVisible() && task.isLeafTask();
  }

  public boolean needAffordanceAnimation(Task task, ActivityOptions activityOptions) {
    if (task == null
        || activityOptions == null
        || !activityOptions.isResumedAffordanceAnimationRequested()
        || task.isFreeformStashed()) {
      return false;
    }
    if (!task.inSplitScreenWindowingMode()) {
      return task.inFreeformWindowingMode();
    }
    Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
    return (createdByOrganizerTask == null
            || createdByOrganizerTask.getTopMostTask() == null
            || !createdByOrganizerTask.getTopMostTask().equals(task))
        ? false
        : true;
  }

  public boolean isAffordanceTargetTask(Task task) {
    Task task2 = this.mAffordanceTargetTask;
    return task2 != null && task2 == task;
  }

  public void setAffordanceTargetTask(Task task) {
    if (this.mAffordanceTargetTask != task) {
      this.mAffordanceTargetTask = task;
      Slog.d(TAG, "setAffordanceTargetTask: " + task);
    }
  }

  @Override // com.android.server.wm.IController
  public void dumpLocked(PrintWriter printWriter, String str) {
    printWriter.println("[MultiTaskingController]");
    if (this.mAffordanceTargetTask != null) {
      printWriter.println(str + "mAffordanceTargetTask=" + this.mAffordanceTargetTask);
    }
    if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      printWriter.println(str + "mSplitFeasibleMode=" + this.mSplitFeasibleMode);
      printWriter.println(str + "mIsDisplaySizeOverride=" + this.mIsDisplaySizeOverride);
    }
    dumpConfigurationContainersLocked(printWriter, str);
    dumpTaskOrderLocked(printWriter, str);
    dumpFocusStateLocked(printWriter, str);
    dumpTransitionLocked(printWriter, str);
    printWriter.println();
  }

  public final void dumpConfigurationContainersLocked(PrintWriter printWriter, String str) {
    printWriter.println(str + "(CONFIGURATION CONTAINERS)");
    this.mAtm.mRootWindowContainer.dumpConfigurationLocked(printWriter, str, 0);
    printWriter.println();
  }

  public final void dumpTaskOrderLocked(final PrintWriter printWriter, final String str) {
    printWriter.println(str + "(TASK ORDER INFO)");
    for (int childCount = this.mAtm.mRootWindowContainer.getChildCount() + (-1);
        childCount >= 0;
        childCount += -1) {
      DisplayContent displayContent =
          (DisplayContent) this.mAtm.mRootWindowContainer.getChildAt(childCount);
      printWriter.println(str + "  DisplayContent #" + displayContent.mDisplayId);
      displayContent.forAllTaskDisplayAreas(
          new Consumer() { // from class:
                           // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              MultiTaskingController.this.lambda$dumpTaskOrderLocked$19(
                  printWriter, str, (TaskDisplayArea) obj);
            }
          });
    }
    printWriter.println();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dumpTaskOrderLocked$19(
      PrintWriter printWriter, String str, TaskDisplayArea taskDisplayArea) {
    printTaskDisplayAreaLocked(printWriter, str + "  ", taskDisplayArea);
  }

  public final void printTaskDisplayAreaLocked(
      PrintWriter printWriter, String str, WindowContainer windowContainer) {
    for (int childCount = windowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
      printWriter.print(str + "TaskDisplayArea: " + windowContainer.getName());
      WindowContainer childAt = windowContainer.getChildAt(childCount);
      if (childAt.asTaskDisplayArea() != null) {
        printTaskDisplayAreaLocked(printWriter, str + "  ", windowContainer);
      } else {
        printAllTasksLocked(printWriter, str + "  ", childAt, childCount);
      }
    }
  }

  public final void printAllTasksLocked(
      PrintWriter printWriter, String str, WindowContainer windowContainer, int i) {
    Task asTask = windowContainer.asTask();
    if (asTask == null) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append(asTask.isRootTask() ? "Root #" : "Leaf #");
    sb.append(i);
    sb.append(" ");
    sb.append(asTask);
    printWriter.println(sb.toString());
    for (int size = asTask.mChildren.size() - 1; size >= 0; size += -1) {
      printAllTasksLocked(
          printWriter, str + "  ", (WindowContainer) asTask.mChildren.get(size), size);
    }
  }

  public final void dumpFocusStateLocked(PrintWriter printWriter, String str) {
    printWriter.println(str + "(FOCUS INFO)");
    RootWindowContainer rootWindowContainer = this.mAtm.mRootWindowContainer;
    printWriter.println(
        str + "  TopDisplayFocusedTask=" + rootWindowContainer.getTopDisplayFocusedRootTask());
    printWriter.println(
        str + "  TopResumedActivity=" + rootWindowContainer.getTopResumedActivity());
    printWriter.println();
    for (int childCount = rootWindowContainer.getChildCount() + (-1);
        childCount >= 0;
        childCount += -1) {
      DisplayContent displayContent = (DisplayContent) rootWindowContainer.getChildAt(childCount);
      printWriter.println(str + "  DisplayContent #" + displayContent.mDisplayId);
      printWriter.println(str + "    FocusedTask=" + displayContent.getFocusedRootTask());
      printWriter.println(
          str
              + "    Preferred TopFocusableTask="
              + displayContent.getDefaultTaskDisplayArea().mPreferredTopFocusableRootTask);
      printWriter.println(str + "    mFocusedApp=" + displayContent.mFocusedApp);
      printWriter.println(str + "    mCurrentFocus=" + displayContent.mCurrentFocus);
      printWriter.println(str + "    mInputMethodWindow=" + displayContent.mInputMethodWindow);
      printWriter.println(str + "    mImeLayeringTarget=" + displayContent.getImeTarget(0));
      printWriter.println(str + "    mImeInputTarget=" + displayContent.getImeInputTarget());
      printWriter.println(str + "    mImeControlTarget=" + displayContent.getImeTarget(2));
      printWriter.println(
          str
              + "    "
              + displayContent.getInsetsStateController().getImeSourceProvider().getSource());
    }
    if (!rootWindowContainer.mTopFocusedAppByProcess.isEmpty()) {
      printWriter.println();
      printWriter.println(str + "  mTopFocusedAppByProcess");
      for (Map.Entry entry : rootWindowContainer.mTopFocusedAppByProcess.entrySet()) {
        printWriter.println(str + "    [" + entry.getKey() + "] r=" + entry.getValue());
      }
    }
    printWriter.println();
  }

  public final void dumpTransitionLocked(PrintWriter printWriter, String str) {
    TransitionController transitionController = this.mAtm.getTransitionController();
    if (transitionController == null) {
      return;
    }
    printWriter.println(str + "(TRANSITION INFO)");
    if (transitionController.getCollectingTransition() != null) {
      printWriter.println(
          str + "  mCollectingTransition=" + transitionController.getCollectingTransition());
    }
    if (!transitionController.mWaitingTransitions.isEmpty()) {
      printWriter.println(
          str + "  mWaitingTransitions=" + transitionController.mWaitingTransitions);
    }
    if (transitionController.mFinishingTransition != null) {
      printWriter.println(
          str + "  mFinishingTransition=" + transitionController.mFinishingTransition);
    }
  }

  public void handleMultiSplitAppMinSize(DisplayContent displayContent) {
    int updateFrom = this.mLastConfig.updateFrom(this.mAtm.getConfiguration());
    int i = displayContent.getDisplayInfo().rotation;
    boolean z = true;
    boolean z2 = this.mLastRotation != i;
    if ((updateFrom & 5120) == 0 && !z2) {
      z = false;
    }
    if (z) {
      ensureMultiSplitAppMinSize(displayContent.getDefaultTaskDisplayArea());
    }
    if (z2) {
      this.mLastRotation = i;
    }
  }

  public void ensureMultiSplitAppMinSize(TaskDisplayArea taskDisplayArea) {
    ActivityRecord topMostActivity;
    updateMultiSplitAppMinimumSizeLocked();
    if ((CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY
            && this.mAtm.mWindowManager.isFolded()
            && taskDisplayArea.isSplitScreenModeActivated())
        || supportMultiSplitAppMinimumSizeLocked()
        || !taskDisplayArea.isMultiSplitActive()) {
      return;
    }
    Task focusedRootTask = taskDisplayArea.getFocusedRootTask();
    if (focusedRootTask != null) {
      ActivityRecord topMostActivity2 = focusedRootTask.getTopMostActivity();
      if (topMostActivity2 == null || !topMostActivity2.inSplitScreenWindowingMode()) {
        return;
      }
      exitMultiWindow(topMostActivity2.token);
      return;
    }
    Task topRootTaskInStageType = taskDisplayArea.getTopRootTaskInStageType(2);
    if (topRootTaskInStageType == null
        || (topMostActivity = topRootTaskInStageType.getTopMostActivity()) == null) {
      return;
    }
    exitMultiWindow(topMostActivity.token);
  }

  public void updateMultiSplitAppMinimumSizeLocked() {
    if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      int i = this.mSplitFeasibleMode;
      this.mSplitFeasibleMode = 2;
      DisplayContent defaultDisplay = this.mAtm.mRootWindowContainer.getDefaultDisplay();
      if (defaultDisplay == null) {
        return;
      }
      DisplayPolicy displayPolicy = defaultDisplay.getDisplayPolicy();
      Resources currentUserResources = displayPolicy.getCurrentUserResources();
      int dividerSize =
          DockedDividerUtils.getDividerSize(
              currentUserResources, DockedDividerUtils.getDividerInsets(currentUserResources));
      int minimalSize = getMinimalSize(currentUserResources);
      int i2 = 0;
      while (true) {
        if (i2 >= 4) {
          break;
        }
        if (i2 != 2 || defaultDisplay.getDisplayRotation().isAllowAllRotations()) {
          boolean z = i2 == 1 || i2 == 3;
          DisplayPolicy.DecorInsets.Info decorInsetsInfo =
              displayPolicy.getDecorInsetsInfo(
                  i2,
                  z ? defaultDisplay.mBaseDisplayHeight : defaultDisplay.mBaseDisplayWidth,
                  z ? defaultDisplay.mBaseDisplayWidth : defaultDisplay.mBaseDisplayHeight);
          int width = decorInsetsInfo.mConfigFrame.width();
          int height = decorInsetsInfo.mConfigFrame.height();
          int i3 = (minimalSize * 2) + dividerSize;
          boolean z2 = width < i3;
          boolean z3 = height < i3;
          if (z2 && z3) {
            this.mSplitFeasibleMode = 0;
            break;
          } else if (z2 != z3) {
            this.mSplitFeasibleMode = 1;
            break;
          }
        }
        i2++;
      }
      boolean z4 = defaultDisplay.mIsSizeForced || defaultDisplay.mIsDensityForced;
      if (z4 || this.mIsDisplaySizeOverride) {
        TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
        if (!z4) {
          taskOrganizerInfo.setSplitFeasibleMode(2);
        } else {
          taskOrganizerInfo.setSplitFeasibleMode(this.mSplitFeasibleMode);
        }
        this.mAtm.mTaskOrganizerController.onSplitLayoutChangeRequested(
            taskOrganizerInfo.toBundle());
        Slog.d(
            TAG,
            "split feasible change, prev="
                + i
                + ", cur="
                + this.mSplitFeasibleMode
                + ", override="
                + z4);
      }
      this.mIsDisplaySizeOverride = z4;
    }
  }

  public boolean supportMultiSplitAppMinimumSizeLocked() {
    return this.mSplitFeasibleMode == 2;
  }

  public final int getMinimalSize(Resources resources) {
    if (resources.getConfiguration().densityDpi
        >= ((MultiWindowUtils.isTablet()
                || (CoreRune.MW_MULTI_SPLIT_FOR_COVER_DISPLAY
                    && (resources.getConfiguration().semDisplayDeviceType == 5)))
            ? FrameworkStatsLog.VBMETA_DIGEST_REPORTED
            : FrameworkStatsLog.SYSTEM_SERVER_PRE_WATCHDOG_OCCURRED)) {
      return resources.getDimensionPixelSize(R.dimen.dialog_corner_radius);
    }
    return resources.getDimensionPixelSize(R.dimen.notification_badge_size);
  }

  public void setCornerGestureCustomValue(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Iterator it = this.mAtm.mExt.getStartedUserIdsLocked().iterator();
        while (it.hasNext()) {
          this.mAtm
              .mExt
              .getCoreStateController()
              .setVolatileState(
                  "corner_gesture_custom_value",
                  Integer.valueOf(i),
                  ((Integer) it.next()).intValue(),
                  true,
                  true,
                  null);
          MultiWindowEdgeDetector.updateCustomBoundsIfNeeded();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) {
    ActivityInfo activityInfo;
    if (pendingIntent != null && this.mAtm.mDexController.getDexModeLocked() == 2) {
      List queryIntentComponents = pendingIntent.queryIntentComponents(0);
      if (!queryIntentComponents.isEmpty()
          && (activityInfo = ((ResolveInfo) queryIntentComponents.get(0)).activityInfo) != null) {
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        if (!this.mAtm
            .mDexController
            .getTaskLocked(applicationInfo.processName, applicationInfo.uid, true, 0)
            .isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isVisibleTaskByTaskIdInDexDisplay(int i) {
    Task anyTaskForId;
    int i2;
    String str;
    if (i != -1
        && this.mAtm.mDexController.getDexModeLocked() == 2
        && (anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 1)) != null
        && anyTaskForId.getDisplayId() == 2) {
      ActivityRecord rootActivity = anyTaskForId.getRootActivity();
      if (rootActivity != null) {
        if (anyTaskForId.isVisible()) {
          return true;
        }
        str = rootActivity.processName;
        i2 = rootActivity.getUid();
      } else {
        WindowProcessController rootProcess = anyTaskForId.getRootProcess();
        if (rootProcess == null) {
          return false;
        }
        String str2 = rootProcess.mName;
        i2 = rootProcess.mUid;
        str = str2;
      }
      if (!this.mAtm.mDexController.getTaskLocked(str, i2, true, 0).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public boolean shouldDeferEnterSplit(List list, List list2) {
    String str;
    int uid;
    WindowProcessController rootProcess;
    ActivityInfo activityInfo;
    if (this.mAtm.mDexController.getDexModeLocked() != 2) {
      return false;
    }
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      List queryIntentComponents = ((PendingIntent) it.next()).queryIntentComponents(0);
      if (!queryIntentComponents.isEmpty()
          && (activityInfo = ((ResolveInfo) queryIntentComponents.get(0)).activityInfo) != null) {
        ApplicationInfo applicationInfo = activityInfo.applicationInfo;
        ArrayList taskLocked =
            this.mAtm.mDexController.getTaskLocked(
                applicationInfo.processName, applicationInfo.uid, true, 0);
        if (!taskLocked.isEmpty()) {
          Iterator it2 = taskLocked.iterator();
          while (it2.hasNext()) {
            arrayList.add(((DexController.FindTaskResult) it2.next()).mTask);
          }
        }
      }
    }
    Iterator it3 = list2.iterator();
    while (it3.hasNext()) {
      Task anyTaskForId =
          this.mAtm.mRootWindowContainer.anyTaskForId(((Integer) it3.next()).intValue(), 1);
      if (anyTaskForId != null && anyTaskForId.getDisplayId() == 2) {
        if (anyTaskForId.isVisible() && anyTaskForId.topRunningActivity() != null) {
          arrayList.add(anyTaskForId);
        } else {
          ActivityRecord rootActivity = anyTaskForId.getRootActivity();
          if (rootActivity != null) {
            str = rootActivity.processName;
            uid = rootActivity.getUid();
          } else {
            WindowProcessController rootProcess2 = anyTaskForId.getRootProcess();
            if (rootProcess2 != null) {
              str = rootProcess2.mName;
              uid = rootProcess2.mUid;
            }
          }
          ArrayList taskLocked2 = this.mAtm.mDexController.getTaskLocked(str, uid, true, 0);
          if (!taskLocked2.isEmpty()) {
            Iterator it4 = taskLocked2.iterator();
            while (it4.hasNext()) {
              arrayList.add(((DexController.FindTaskResult) it4.next()).mTask);
            }
          }
          if (this.mAtm.mDexController.shouldRestartProcess(str)
              && (rootProcess = anyTaskForId.getRootProcess()) != null) {
            this.mAtm.mDexController.killProcessIfNeeded(rootProcess, 0, true);
            arrayList.add(anyTaskForId);
          }
        }
      }
    }
    Iterator it5 = arrayList.iterator();
    while (it5.hasNext()) {
      minimizeTaskLocked((Task) it5.next(), true);
    }
    return !arrayList.isEmpty();
  }

  public boolean handleAltTabKeyIfNeededLocked() {
    int intValue;
    TaskDisplayArea defaultTaskDisplayArea =
        this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
    ActivityRecord focusedActivity = defaultTaskDisplayArea.getFocusedActivity();
    if (focusedActivity == null
        || !focusedActivity.inSplitScreenWindowingMode()
        || !defaultTaskDisplayArea.isSplitScreenModeActivated()) {
      this.mFocusableTaskIds.clear();
      return false;
    }
    if ((this.mFocusableTaskIds.isEmpty()
            && !collectFocusableSplitScreenTasksLocked(
                defaultTaskDisplayArea, focusedActivity.getStageType()))
        || (intValue = ((Integer) this.mFocusableTaskIds.poll()).intValue()) == -1) {
      return false;
    }
    this.mAtm.setFocusedTask(intValue);
    return true;
  }

  public final boolean collectFocusableSplitScreenTasksLocked(
      TaskDisplayArea taskDisplayArea, final int i) {
    taskDisplayArea.forAllRootStageTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda16
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            MultiTaskingController.this.lambda$collectFocusableSplitScreenTasksLocked$20(
                i, (Task) obj);
          }
        });
    if (this.mFocusableTaskIds.isEmpty()) {
      return false;
    }
    this.mFocusableTaskIds.add(-1);
    Slog.d(TAG, "collectFocusableSplitScreenTasksLocked:" + this.mFocusableTaskIds);
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$collectFocusableSplitScreenTasksLocked$20(int i, Task task) {
    Task topMostTask;
    if (task.getStageType() == i
        || !task.isVisible()
        || (topMostTask = task.getTopMostTask()) == null) {
      return;
    }
    this.mFocusableTaskIds.add(Integer.valueOf(topMostTask.mTaskId));
  }

  public void releaseAltTabKeyConsumerLocked() {
    if (this.mFocusableTaskIds.isEmpty()) {
      return;
    }
    Slog.d(TAG, "releaseAltTabKeyStateLocked:" + this.mFocusableTaskIds);
    this.mFocusableTaskIds.clear();
  }

  public boolean shouldNotSupportWallpaper() {
    return this.mIsMinimalBatteryUse;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:

     if (r2.supportsMultiWindow() == false) goto L8;
  */
  /* JADX WARN: Code restructure failed: missing block: B:6:0x0025, code lost:

     if (r6.isMultiSplitVisible() != false) goto L8;
  */
  /* JADX WARN: Code restructure failed: missing block: B:8:0x003d, code lost:

     return 3;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int getMultiWindowModeForAssistantHotKeyLocked() {
    TaskDisplayArea defaultTaskDisplayArea =
        this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
    boolean z = !MultiWindowCoreState.MW_ENABLED;
    Task task =
        defaultTaskDisplayArea.getTask(
            new Predicate() { // from class:
                              // com.android.server.wm.MultiTaskingController$$ExternalSyntheticLambda4
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$getMultiWindowModeForAssistantHotKeyLocked$21;
                lambda$getMultiWindowModeForAssistantHotKeyLocked$21 =
                    MultiTaskingController.lambda$getMultiWindowModeForAssistantHotKeyLocked$21(
                        (Task) obj);
                return lambda$getMultiWindowModeForAssistantHotKeyLocked$21;
              }
            });
    if (defaultTaskDisplayArea.isSplitScreenModeActivated()) {
      if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {}
      return 2;
    }
    if (task == null || task.isActivityTypeHomeOrRecents() || z) {
      return 1;
    }
  }

  public static /* synthetic */ boolean lambda$getMultiWindowModeForAssistantHotKeyLocked$21(
      Task task) {
    return task.inFullscreenWindowingMode() && task.isVisible();
  }

  public void startAssistantActivityToSplitLocked(Intent intent, float f) {
    TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
    taskOrganizerInfo.setAssistantActivityToSplit(intent, f);
    this.mAtm.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
  }
}
