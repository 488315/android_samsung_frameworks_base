package com.android.server.desktopmode;

import android.app.ActivityManagerInternal;
import android.app.StatusBarManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.custom.PrivateCustomDeviceManager;
import com.samsung.android.multiwindow.MultiWindowManager;

/* loaded from: classes2.dex */
public abstract class ModeChanger {
  public static final String TAG = "[DMS]" + ModeChanger.class.getSimpleName();
  public ActivityManagerInternal mActivityManagerInternal;
  public ActivityTaskManagerService mActivityTaskManager;
  public ActivityTaskManagerInternal mActivityTaskManagerInternal;
  public final Context mContext;
  public Handler mHandler;
  public boolean mIsLockTaskModeEnabledAndSecured = false;
  public MultiResolutionManager mMultiResolutionManager;
  public MultiWindowManager mMultiWindowManager;
  public final ContentResolver mResolver;
  public SemDesktopModeManager mSemDesktopModeManager;
  public final SemDesktopModeStateNotifier mSemDesktopModeStateNotifier;
  public SettingsHelper mSettingsHelper;
  public final IStateManager mStateManager;
  public StatusBarManager mStatusBarManager;
  public UiManager mUiManager;
  public WindowManagerInternal mWindowManagerInternal;

  public abstract void setDesktopModeState(int i, int i2);

  public ModeChanger(
      Context context,
      IStateManager iStateManager,
      SemDesktopModeStateNotifier semDesktopModeStateNotifier,
      UiManager uiManager,
      SettingsHelper settingsHelper,
      MultiResolutionManager multiResolutionManager,
      ActivityTaskManagerService activityTaskManagerService,
      ActivityTaskManagerInternal activityTaskManagerInternal,
      ActivityManagerInternal activityManagerInternal,
      WindowManagerInternal windowManagerInternal,
      StatusBarManager statusBarManager,
      MultiWindowManager multiWindowManager,
      SemDesktopModeManager semDesktopModeManager) {
    this.mContext = context;
    this.mResolver = context.getContentResolver();
    this.mStateManager = iStateManager;
    this.mSemDesktopModeStateNotifier = semDesktopModeStateNotifier;
    this.mUiManager = uiManager;
    this.mSettingsHelper = settingsHelper;
    this.mMultiResolutionManager = multiResolutionManager;
    this.mActivityTaskManager = activityTaskManagerService;
    this.mActivityTaskManagerInternal = activityTaskManagerInternal;
    this.mActivityManagerInternal = activityManagerInternal;
    this.mWindowManagerInternal = windowManagerInternal;
    this.mStatusBarManager = statusBarManager;
    this.mMultiWindowManager = multiWindowManager;
    this.mSemDesktopModeManager = semDesktopModeManager;
  }

  public void dismissDialog(int... iArr) {
    for (int i : iArr) {
      this.mUiManager.dismissDialog(i);
    }
  }

  public void clearSettingsBadgeCount() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "clearSettingsBadgeCount()");
    }
    Settings.System.putIntForUser(this.mContext.getContentResolver(), "badge_for_dex", 0, -2);
  }

  public void setDesktopModeToSurfaceFlinger(boolean z) {
    IBinder service = ServiceManager.getService("SurfaceFlinger");
    if (service != null) {
      try {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeInt(z ? 1 : 0);
        service.transact(1103, obtain, null, 0);
        obtain.recycle();
      } catch (RemoteException e) {
        Log.m9e(TAG, "Failed to set desktop mode for SF", e);
      }
    }
  }

  public boolean isLockTaskModeEnabledAndSecured() {
    return this.mIsLockTaskModeEnabledAndSecured;
  }

  public void backupLockTaskModeEnabledAndSecured(State state) {
    boolean z = false;
    if (this.mActivityTaskManager.getLockTaskModeState() != 0
        && Settings.Secure.getIntForUser(
                this.mContext.getContentResolver(),
                "lock_to_app_exit_locked",
                0,
                state.getCurrentUserId())
            != 0) {
      z = true;
    }
    this.mIsLockTaskModeEnabledAndSecured = z;
  }

  public void switchKnoxToFolderMode(State state) {
    if (isKnoxLauncherMode(state)) {
      Bundle bundle = new Bundle(1);
      bundle.putInt("android.intent.extra.user_handle", state.getCurrentUserId());
      ContainerProxy.sendCommand("knox.container.proxy.COMMAND_SWITCH_PROFILE", bundle);
    }
  }

  public final boolean isKnoxLauncherMode(State state) {
    ResolveInfo resolveActivityAsUser =
        this.mContext
            .getPackageManager()
            .resolveActivityAsUser(
                new Intent("android.intent.action.MAIN")
                    .addCategory("android.intent.category.HOME"),
                65536,
                state.getCurrentUserId());
    if (resolveActivityAsUser == null
        || !resolveActivityAsUser.activityInfo.name.startsWith("com.android.internal.app")) {
      return false;
    }
    if (!DesktopModeFeature.DEBUG) {
      return true;
    }
    Log.m11v(TAG, "isKnoxLauncherMode()=true");
    return true;
  }

  public boolean hasPackageTask(int i, String str, int i2) {
    return this.mActivityTaskManagerInternal.hasPackageTask(i, str, i2);
  }

  public void forceKillProcessWhenExit() {
    this.mActivityManagerInternal.killProcessWhenDexExit();
  }

  public void cancelKillProcessWhenExit() {
    this.mActivityManagerInternal.cancelKillProcessWhenDexExit();
  }

  public void bringTaskToForeground(int i, int i2, int i3) {
    if (canTaskBeBroughtToForeground(i)) {
      if (DesktopModeFeature.DEBUG) {
        Log.m10i(
            TAG,
            "bringTaskToForeground(), taskId="
                + i
                + ", targetDisplayId="
                + i2
                + ", targetWindowingMode="
                + WindowConfiguration.windowingModeToString(i3));
      }
      this.mActivityTaskManagerInternal.bringTaskToForeground(i, i2, i3);
    }
  }

  public final boolean canTaskBeBroughtToForeground(int i) {
    if (i < 0) {
      return false;
    }
    PrivateCustomDeviceManager privateCustomDeviceManager =
        PrivateCustomDeviceManager.getInstance();
    if (privateCustomDeviceManager != null
        && privateCustomDeviceManager.isAutoOpenLastAppAllowed() == 8) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(
            TAG,
            "canTaskBeBroughtToForeground(), auto_open_last_app is forcibly disabled by Knox Custom"
                + " SDK");
      }
      DesktopModeSettings.setSettings(
          this.mContext.getContentResolver(), "auto_open_last_app", false);
      ComponentName realActivityForTaskId =
          this.mActivityTaskManagerInternal.getRealActivityForTaskId(i);
      if (privateCustomDeviceManager == null
          || privateCustomDeviceManager.stayInForeground(realActivityForTaskId)) {
        return true;
      }
      if (DesktopModeFeature.DEBUG) {
        android.util.Log.d(
            TAG,
            "canTaskBeBroughtToForeground(), componentName="
                + realActivityForTaskId
                + " is not allowed.");
      }
      return false;
    }
    if (DesktopModeSettings.getSettings(
        this.mContext.getContentResolver(), "auto_open_last_app", false)) {
      return true;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "canTaskBeBroughtToForeground(), auto_open_last_app is not enabled");
    }
    return false;
  }

  public void showDexMirroringTipsNotification(String str, String str2) {
    if (DesktopModeFeature.SUPPORT_STANDALONE
        && DesktopModeFeature.SUPPORT_DUAL
        && !DesktopModeSettings.getSettings(
            this.mResolver, "dex_mirroring_tips_notification_shown", false)) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "Show DeX mirroring Tips Notification.");
      }
      DesktopModeSettings.setSettings(
          this.mResolver, "dex_mirroring_tips_notification_shown", true);
      Utils.showTipsNotification(this.mContext, "0008", "DEXX_0004", str, str2);
    }
  }

  public void saveDisplayType(int i) {
    DesktopModeSettings.setSettings(this.mResolver, "display_type", i);
  }

  public class ModeToModeChangeInfo {
    public int mModeToMode;
    public boolean mShowModeChangeScreen;
    public int mTopTaskId = -1;

    public ModeToModeChangeInfo(boolean z, int i) {
      this.mShowModeChangeScreen = z;
      this.mModeToMode = i;
    }

    public boolean showModeChangeScreen() {
      return this.mShowModeChangeScreen;
    }

    public int getModeToMode() {
      return this.mModeToMode;
    }

    public int getTopTaskId() {
      return this.mTopTaskId;
    }

    public void setTopTaskId(int i) {
      this.mTopTaskId = i;
    }

    public static String modeToModeToString(int i) {
      if (i == -1) {
        return "NONE";
      }
      if (i == 1) {
        return "DUAL_TO_STANDALONE";
      }
      if (i == 2) {
        return "STANDALONE_TO_DUAL";
      }
      return "Unknown=" + i;
    }

    public String toString() {
      return getClass().getSimpleName()
          + "(mShowModeChangeScreen="
          + this.mShowModeChangeScreen
          + ", mModeToMode="
          + modeToModeToString(this.mModeToMode)
          + ", mTopTaskId="
          + this.mTopTaskId
          + ")";
    }
  }
}
