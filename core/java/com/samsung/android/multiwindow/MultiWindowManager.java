package com.samsung.android.multiwindow;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ResolveInfo;
import android.graphics.PointF;
import android.graphics.Rect;
import android.p009os.Debug;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.util.Log;
import android.util.Singleton;
import android.view.MotionEvent;
import android.window.WindowContainerToken;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public class MultiWindowManager {
  public static final String ACTION_AUTORUN_FLEX_PANEL = "android.intent.action.AUTORUN_FLEX_PANEL";
  public static final String ACTION_COLLAPSE_FLEX_PANEL =
      "android.intent.action.COLLAPSE_FLEX_PANEL";
  public static final String ACTION_ENTER_CONTENTS_TO_WINDOW =
      "com.samsung.android.action.ENTER_CONTENTS_TO_WINDOW";
  public static final String ACTION_EXPAND_FLEX_PANEL = "android.intent.action.EXPAND_FLEX_PANEL";
  public static final String ACTION_MINIMIZE_ALL = "com.samsung.android.multiwindow.MINIMIZE_ALL";
  public static final String ACTION_MINIMIZE_ALL_BY_SYSTEM =
      "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM";
  public static final String ACTION_MULTI_WINDOW_ENABLE_CHANGED =
      "com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED";
  public static final int ASSISTANT_HOT_KEY_MODE_FREEFORM = 3;
  public static final int ASSISTANT_HOT_KEY_MODE_FULL = 1;
  public static final int ASSISTANT_HOT_KEY_MODE_SPLIT = 2;
  public static final String BUNDLE_TASK_REMOVED = "bundle_task_removed";
  public static final int CHANGE_FREEFORM_STASH_FOCUSABLE = 1;
  public static final int CHANGE_FREEFORM_STASH_NONE_FOCUSABLE = 2;
  public static final int CHANGE_FREEFORM_STASH_UNDEFINED = 0;
  public static final int CHANGE_TRANSIT_FLAG_USE_FLOATING_LAYER = 1;
  public static final int CHANGE_TRANSIT_MODE_DISMISS = 2;
  public static final int CHANGE_TRANSIT_MODE_FREEFORM_CAPTION_TYPE_CHANGE = 3;
  public static final int CHANGE_TRANSIT_MODE_MOVE_BACK_IN_SPLIT_SCREEN = 6;
  public static final int CHANGE_TRANSIT_MODE_NATURAL_SWITCHING = 4;
  public static final int CHANGE_TRANSIT_MODE_NEW_DEX_CAPTION_TYPE_CHANGE = 7;
  public static final int CHANGE_TRANSIT_MODE_POP_OVER = 5;
  public static final int CHANGE_TRANSIT_MODE_STANDARD = 1;
  public static final int CHANGE_TRANSIT_MODE_UNDEFINED = 0;
  public static final String DEX_COMPAT_LOG_PREFIX = "[DexCompat] ";
  public static final int EMBED_ACTIVITY_PACKAGE_DISABLED = 2;
  public static final int EMBED_ACTIVITY_PACKAGE_ENABLED = 1;
  public static final int EMBED_ACTIVITY_PACKAGE_UNDEFINED = 0;
  public static final String EXTRA_IN_MULTI_WINDOW_MODE =
      "com.samsung.android.extra.IN_MULTI_WINDOW_MODE";
  public static final String EXTRA_MULTI_WINDOW_ENABLED =
      "com.samsung.android.extra.MULTI_WINDOW_ENABLED";
  public static final String EXTRA_MULTI_WINDOW_ENABLED_USER_ID =
      "com.samsung.android.extra.MULTI_WINDOW_ENABLED_USER_ID";
  public static final String EXTRA_MULTI_WINDOW_ENABLE_REQUESTER =
      "com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER";
  public static final String FLEX_MODE_PANEL_ENABLED = "flex_mode_panel_enabled";
  public static final int FORCE_HIDING_TRANSIT_ENTER = 1;
  public static final int FORCE_HIDING_TRANSIT_EXIT = 2;
  public static final int FORCE_HIDING_TRANSIT_UNDEFINED = 0;
  public static final int FREEFORM_CAPTION_TYPE_BAR = 1;
  public static final int FREEFORM_CAPTION_TYPE_HANDLER = 0;
  public static final int FREEFORM_CAPTION_TYPE_UNDEFINED = -1;
  public static final int FREEFORM_CORNER_RADIUS_IN_DP = 14;
  public static final int FREEFORM_TRANSIT_MINIMIZE = 1;
  public static final int FREEFORM_TRANSIT_NONE = 0;
  public static final int FREEFORM_TRANSIT_RESTORE = 2;
  private static final Singleton<IMultiTaskingBinder> IMultiTaskingBinderSingleton =
      new Singleton<
          IMultiTaskingBinder>() { // from class:
                                   // com.samsung.android.multiwindow.MultiWindowManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IMultiTaskingBinder create() {
          try {
            return ActivityTaskManager.getService().getMultiTaskingBinder();
          } catch (RemoteException e) {
            return null;
          }
        }
      };
  public static final int INVALID_POSITION = -1;
  public static final int LAUNCH_OVER_FOCUSED_TASK_ID = -10000;
  private static final long META_MASK = 281474976710656L;
  public static final int MULTIWINDOW_MODE_FREEFORM = 1;
  public static final int MULTIWINDOW_MODE_NONE = 0;
  public static final int MULTIWINDOW_MODE_PINNED = 4;
  public static final int MULTIWINDOW_MODE_SPLIT_SCREEN = 2;
  public static final int MULTI_SPLIT_BOTTOM_SIDE = 1024;
  public static final int MULTI_SPLIT_DOCK_SIDE_MASK = 1984;
  public static final int MULTI_SPLIT_FEASIBLE = 2;
  public static final int MULTI_SPLIT_INVALID_SIDE = 64;
  public static final int MULTI_SPLIT_LEFT_SIDE = 128;
  public static final int MULTI_SPLIT_MODE_MASK = 56;
  public static final int MULTI_SPLIT_NONE_SPLIT = 8;
  public static final int MULTI_SPLIT_NOT_SUPPORT = 2;
  public static final int MULTI_SPLIT_NOT_SUPPORT_BY_HOME = 4;
  public static final int MULTI_SPLIT_RIGHT_SIDE = 512;
  public static final int MULTI_SPLIT_SUPPORT = 1;
  public static final int MULTI_SPLIT_THREE_SPLIT = 32;
  public static final int MULTI_SPLIT_TOP_SIDE = 256;
  public static final int MULTI_SPLIT_TWO_SPLIT = 16;
  public static final int MW_MINIMIZE_ANIMATION_DURATION = 250;
  public static final int MW_NEW_DEX_MINIMIZE_ANIMATION_DURATION = 450;
  public static final int NATURAL_SWITCHING_SUPPORT = 2048;
  public static final int OUT_OF_SCREEN_POSITION = -2;
  public static final String PERMISSION_MULTI_WINDOW_MONITOR =
      "com.samsung.android.permission.MULTI_WINDOW_MONITOR";
  public static final int RESIZE_HANDLE_FOR_POINTER_WIDTH_IN_DP = 10;
  public static final int RESIZE_HANDLE_INNER_WIDTH_IN_DP = 4;
  public static final int RESIZE_HANDLE_WIDTH_IN_PX = 48;
  public static final long SC_DOCK_LEFT = 281474976710727L;
  public static final int SPLIT_ACTIVITY_PACKAGE_BLOCKED = 2;
  public static final int SPLIT_ACTIVITY_PACKAGE_DISABLED = 0;
  public static final int SPLIT_ACTIVITY_PACKAGE_ENABLED = 1;
  public static final int SPLIT_FEASIBLE = 1;
  public static final int SPLIT_FEASIBLE_UNDEFINED = -1;
  public static final int SPLIT_NOT_FEASIBLE = 0;
  public static final String TAG = "MultiWindowManager";
  public static final int TYPE_LONG_PRESS = 1;
  private static MultiWindowManager sInstance;

  public @interface AssistantHotKeyMode {}

  public @interface ChangeFreeformStashMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface ChangeTransitionFlags {}

  public @interface ChangeTransitionMode {}

  public @interface ForceHidingTransit {}

  public @interface FreeformCaptionType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface MultiSplitFlags {}

  public @interface SplitActivityPackageEnabledState {}

  public @interface SplitFeasibleMode {}

  public @interface embedActivityPackageEnabledState {}

  public static String changeTransitModeToString(int changeTransitMode) {
    switch (changeTransitMode) {
      case 0:
        return "CHANGE_TRANSIT_MODE_UNDEFINED";
      case 1:
        return "CHANGE_TRANSIT_MODE_STANDARD";
      case 2:
        return "CHANGE_TRANSIT_MODE_DISMISS";
      case 3:
        return "CHANGE_TRANSIT_MODE_FREEFORM_CAPTION_TYPE_CHANGE";
      case 4:
        return "CHANGE_TRANSIT_MODE_NATURAL_SWITCHING";
      case 5:
        return "CHANGE_TRANSIT_MODE_POP_OVER";
      case 6:
        return "CHANGE_TRANSIT_MODE_MOVE_BACK_IN_SPLIT_SCREEN";
      case 7:
        return "CHANGE_TRANSIT_MODE_NEW_DEX_CAPTION_TYPE_CHANGE";
      default:
        return Integer.toString(changeTransitMode);
    }
  }

  public static String freeformCaptionTypeToString(int freeformCaptionType) {
    switch (freeformCaptionType) {
      case -1:
        return "CAPTION_TYPE_UNDEFINED";
      case 0:
        return "CAPTION_TYPE_HANDLER";
      case 1:
        return "CAPTION_TYPE_BAR";
      default:
        return Integer.toString(freeformCaptionType);
    }
  }

  public static int stringToFreeformCaptionType(String string) {
    if ("CAPTION_TYPE_HANDLER".equals(string)) {
      return 0;
    }
    if ("CAPTION_TYPE_BAR".equals(string)) {
      return 1;
    }
    return -1;
  }

  public static String forceHidingTransitToString(int forceHidingTransit) {
    switch (forceHidingTransit) {
      case 0:
        return "FORCE_HIDING_TRANSIT_UNDEFINED";
      case 1:
        return "FORCE_HIDING_TRANSIT_ENTER";
      case 2:
        return "FORCE_HIDING_TRANSIT_EXIT";
      default:
        return Integer.toString(forceHidingTransit);
    }
  }

  public static String splitActivityPackageEnabledStateToString(int state) {
    switch (state) {
      case 0:
        return "SPLIT_ACTIVITY_PACKAGE_DISABLED";
      case 1:
        return "SPLIT_ACTIVITY_PACKAGE_ENABLED";
      case 2:
        return "SPLIT_ACTIVITY_PACKAGE_BLOCKED";
      default:
        return Integer.toString(state);
    }
  }

  public static String embedActivityPackageEnabledStateToString(int state) {
    switch (state) {
      case 0:
        return "EMBED_ACTIVITY_PACKAGE_UNDEFINED";
      case 1:
        return "EMBED_ACTIVITY_PACKAGE_ENABLED";
      case 2:
        return "EMBED_ACTIVITY_PACKAGE_DISABLED";
      default:
        return Integer.toString(state);
    }
  }

  public static MultiWindowManager getInstance() {
    if (sInstance == null) {
      sInstance = new MultiWindowManager();
    }
    return sInstance;
  }

  private static IMultiTaskingBinder getDefault() {
    return IMultiTaskingBinderSingleton.get();
  }

  private static void warningException(Exception e) {
    if (CoreRune.SAFE_DEBUG) {
      e.printStackTrace();
    } else {
      Log.m102w(TAG, "warningException() : caller=" + Debug.getCaller() + e.getMessage());
    }
  }

  public boolean minimizeTaskById(int taskId) {
    try {
      return getDefault().minimizeTaskById(taskId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean minimizeAllTasks(int displayId) {
    try {
      return getDefault().minimizeAllTasks(displayId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean minimizeAllTasksByRecents(int displayId) {
    try {
      return getDefault().minimizeAllTasksByRecents(displayId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean minimizeTaskToSpecificPosition(int taskId, boolean animate, int x, int y) {
    try {
      return getDefault().minimizeTaskToSpecificPosition(taskId, animate, x, y);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void registerFreeformCallback(IFreeformCallback observer) {
    try {
      getDefault().registerFreeformCallback(observer);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void unregisterFreeformCallback(IFreeformCallback observer) {
    try {
      getDefault().unregisterFreeformCallback(observer);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void notifyFreeformMinimizeAnimationEnd(int taskId, PointF point) {
    try {
      getDefault().notifyFreeformMinimizeAnimationEnd(taskId, point);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void reportFreeformContainerPoint(PointF point) {
    try {
      getDefault().reportFreeformContainerPoint(point);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public PointF getFreeformContainerPoint() {
    try {
      getDefault().getFreeformContainerPoint();
      return null;
    } catch (RemoteException e) {
      warningException(e);
      return null;
    }
  }

  public List<ActivityManager.RunningTaskInfo> getMinimizedFreeformTasksForCurrentUser() {
    try {
      return getDefault().getMinimizedFreeformTasksForCurrentUser().getList();
    } catch (RemoteException e) {
      warningException(e);
      return null;
    }
  }

  public void setBlockedMinimizeFreeformEnable(boolean enabled) {
    try {
      getDefault().setBlockedMinimizeFreeformEnable(enabled);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void setCustomDensityEnabled(int enabledFlags) {
    try {
      getDefault().setCustomDensityEnabled(enabledFlags);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void setEnsureLaunchSplitEnabled(boolean enabled) {
    try {
      getDefault().setEnsureLaunchSplitEnabled(enabled);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public List<ActivityManager.RunningTaskInfo> getVisibleTasks() {
    try {
      return getDefault().getVisibleTasks().getList();
    } catch (Exception e) {
      warningException(e);
      return Collections.emptyList();
    }
  }

  public void setMaxVisibleFreeformCountForDex(int maxCount, int maxDexCount) {
    try {
      getDefault().setMaxVisibleFreeformCountForDex(maxCount, maxDexCount);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void notifyDragSplitAppIconHasDrawable(boolean hasDrawable) {
    try {
      getDefault().notifyDragSplitAppIconHasDrawable(hasDrawable);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public List<String> getMWDisableRequesters() {
    try {
      return getDefault().getMWDisableRequesters().getList();
    } catch (RemoteException e) {
      warningException(e);
      return null;
    }
  }

  public void setMultiWindowEnabled(String key, String reason, boolean enabled) {
    setMultiWindowEnabledForUser(key, reason, enabled, UserHandle.myUserId());
  }

  public void setMultiWindowEnabledForUser(String key, String reason, boolean enabled, int userId) {
    try {
      getDefault().setMultiWindowEnabledForUser(key, reason, enabled, userId);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public int getSupportedMultiWindowModes(ResolveInfo rInfo) {
    if (rInfo == null) {
      return 0;
    }
    return getSupportedMultiWindowModes(rInfo.activityInfo);
  }

  public int getSupportedMultiWindowModes(ActivityInfo aInfo) {
    if (aInfo == null) {
      return 0;
    }
    int i = aInfo.resizeMode;
    int supportedModes = 0;
    int resizeMode = getResizeMode(aInfo);
    if (ActivityInfo.isResizeableMode(resizeMode)) {
      supportedModes = 3;
    }
    if (aInfo.supportsPictureInPicture()) {
      return supportedModes | 4;
    }
    return supportedModes;
  }

  public boolean supportsMultiWindow(IBinder token) {
    try {
      return getDefault().supportsMultiWindow(token);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public int getResizeMode(ActivityInfo aInfo) {
    try {
      return getDefault().getResizeMode(aInfo);
    } catch (RemoteException e) {
      warningException(e);
      return 0;
    }
  }

  public boolean isAllowedMultiWindowPackage(String packageName) {
    try {
      return getDefault().isAllowedMultiWindowPackage(packageName);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public List<String> getAllowedMultiWindowPackageList() {
    try {
      return getDefault().getAllowedMultiWindowPackageList();
    } catch (RemoteException e) {
      warningException(e);
      return Collections.emptyList();
    }
  }

  public boolean isMultiWindowBlockListApp(String packageName) {
    try {
      return getDefault().isMultiWindowBlockListApp(packageName);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public List<String> getMultiWindowBlockListApp() {
    try {
      return getDefault().getMultiWindowBlockListApp().getList();
    } catch (RemoteException e) {
      warningException(e);
      return Collections.emptyList();
    }
  }

  public int getDexTaskInfoFlags(IBinder token) {
    try {
      return getDefault().getDexTaskInfoFlags(token);
    } catch (RemoteException e) {
      warningException(e);
      return 0;
    }
  }

  public boolean isAllTasksResizable(int taskId1, int taskId2, int taskId3) {
    try {
      return getDefault().isAllTasksResizable(taskId1, taskId2, taskId3);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean isDismissedFlexPanelMode() {
    try {
      return getDefault().isDismissedFlexPanelMode();
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener listener) {
    try {
      getDefault().registerRemoteAppTransitionListener(listener);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener listener) {
    try {
      getDefault().unregisterRemoteAppTransitionListener(listener);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean isFlexPanelRunning() {
    try {
      return getDefault().isFlexPanelRunning();
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void dismissSplitTask(IBinder token, boolean homeBehindTopTask) {
    try {
      getDefault().dismissSplitTask(token, homeBehindTopTask);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int taskId) {
    try {
      return getDefault().getSurfaceFreezerSnapshot(taskId);
    } catch (RemoteException e) {
      warningException(e);
      return null;
    }
  }

  public boolean startNaturalSwitching(IBinder client, IBinder dragTargetToken) {
    try {
      return getDefault().startNaturalSwitching(client, dragTargetToken);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void finishNaturalSwitching() {
    try {
      getDefault().finishNaturalSwitching();
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean preventNaturalSwitching(int taskId) {
    try {
      return getDefault().preventNaturalSwitching(taskId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void rotateDexCompatTask(IBinder token) {
    try {
      getDefault().rotateDexCompatTask(token);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public List<String> getSplitActivityAllowPackages() {
    try {
      return getDefault().getSplitActivityAllowPackages();
    } catch (RemoteException e) {
      warningException(e);
      return List.of();
    }
  }

  public int getSplitActivityPackageEnabled(String packageName, int userId) {
    try {
      return getDefault().getSplitActivityPackageEnabled(packageName, userId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getSplitActivityPackageEnabled", e);
      return 0;
    }
  }

  public void setSplitActivityPackageEnabled(String packageName, int newState, int userId) {
    try {
      getDefault().setSplitActivityPackageEnabled(packageName, newState, userId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setSplitActivityPackageEnabled", e);
    }
  }

  public boolean getEmbedActivityPackageEnabled(String packageName, int userId) {
    try {
      return getDefault().getEmbedActivityPackageEnabled(packageName, userId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to getEmbedActivityPackageEnabled", e);
      return true;
    }
  }

  public void setEmbedActivityPackageEnabled(String packageName, boolean enabled, int userId) {
    try {
      getDefault().setEmbedActivityPackageEnabled(packageName, enabled, userId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Failed to setEmbedActivityPackageEnabled", e);
    }
  }

  public List<String> getSupportEmbedActivityPackages() {
    try {
      return getDefault().getSupportEmbedActivityPackages();
    } catch (RemoteException e) {
      warningException(e);
      return List.of();
    }
  }

  public void toggleFreeformWindowingModeForDex(WindowContainerToken token) {
    try {
      getDefault().toggleFreeformWindowingModeForDex(token);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean exitMultiWindow(IBinder token, boolean checkPermission) {
    try {
      return getDefault().exitMultiWindow(token, checkPermission);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public int getMultiWindowModeStates(int displayId) {
    try {
      return getDefault().getMultiWindowModeStates(displayId);
    } catch (RemoteException e) {
      warningException(e);
      return 0;
    }
  }

  public void setCornerGestureEnabledWithSettings(boolean enabled) {
    try {
      getDefault().setCornerGestureEnabledWithSettings(enabled);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean isValidCornerGesture(MotionEvent ev) {
    try {
      return getDefault().isValidCornerGesture(ev);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void setSplitImmersiveMode(boolean enable) {
    try {
      getDefault().setSplitImmersiveMode(enable);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean isSplitImmersiveModeEnabled() {
    try {
      return getDefault().isSplitImmersiveModeEnabled();
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void setNaviBarImmersiveModeLocked(boolean enable) {
    try {
      getDefault().setNaviStarSplitImmersiveMode(enable);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public List<ActivityManager.RecentTaskInfo> getTaskInfoFromPackageName(String packageName) {
    try {
      return getDefault().getTaskInfoFromPackageName(packageName).getList();
    } catch (RemoteException e) {
      warningException(e);
      return Collections.emptyList();
    }
  }

  public boolean removeFocusedTask(int displayId) {
    try {
      return getDefault().removeFocusedTask(displayId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void setStayFocusActivityEnabled(boolean enabled) {
    try {
      getDefault().setStayFocusActivityEnabled(enabled);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void setStayFocusAndTopResumedActivityEnabled(
      boolean stayFocusEnabled, boolean stayTopResumedEnabled) {
    try {
      getDefault()
          .setStayFocusAndTopResumedActivityEnabled(stayFocusEnabled, stayTopResumedEnabled);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean hasMinimizedToggleTasks() {
    try {
      return getDefault().hasMinimizedToggleTasks();
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean toggleFreeformWindowingMode() {
    try {
      return getDefault().toggleFreeformWindowingMode();
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void saveFreeformBounds(int taskId) {
    try {
      getDefault().saveFreeformBounds(taskId);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public static int createModeToDockSide(int createMode) {
    switch (createMode) {
      case 2:
        return 1;
      case 3:
        return 2;
      case 4:
        return 3;
      case 5:
        return 4;
      default:
        return -1;
    }
  }

  public static int multiSplitFlagsToDockSide(int flags) {
    switch (flags & 1984) {
      case 128:
        return 1;
      case 256:
        return 2;
      case 512:
        return 3;
      case 1024:
        return 4;
      default:
        return -1;
    }
  }

  public static StringBuilder multiSplitFlagsToString(int flags) {
    StringBuilder sb = new StringBuilder();
    if ((flags & 1) != 0) {
      sb.append(" MULTI_SPLIT_SUPPORT");
    } else if ((flags & 2) != 0) {
      sb.append(" MULTI_SPLIT_NOT_SUPPORT");
    } else if ((flags & 4) != 0) {
      sb.append(" MULTI_SPLIT_NOT_SUPPORT_BY_HOME");
    }
    if ((flags & 8) != 0) {
      sb.append(" MULTI_SPLIT_NONE_SPLIT");
    } else if ((flags & 16) != 0) {
      sb.append(" MULTI_SPLIT_TWO_SPLIT");
    } else if ((flags & 32) != 0) {
      sb.append(" MULTI_SPLIT_THREE_SPLIT");
    }
    if ((flags & 64) != 0) {
      sb.append(" MULTI_SPLIT_INVALID_SIDE");
    } else if ((flags & 128) != 0) {
      sb.append(" MULTI_SPLIT_LEFT_SIDE");
    } else if ((flags & 256) != 0) {
      sb.append(" MULTI_SPLIT_TOP_SIDE");
    } else if ((flags & 512) != 0) {
      sb.append(" MULTI_SPLIT_RIGHT_SIDE");
    } else if ((flags & 1024) != 0) {
      sb.append(" MULTI_SPLIT_BOTTOM_SIDE");
    }
    return sb;
  }

  public int getMultiSplitFlags() {
    try {
      return getDefault().getMultiSplitFlags();
    } catch (RemoteException e) {
      warningException(e);
      return 0;
    }
  }

  public void registerDexSnappingCallback(IDexSnappingCallback observer) {
    try {
      getDefault().registerDexSnappingCallback(observer);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void unregisterDexSnappingCallback(IDexSnappingCallback observer) {
    try {
      getDefault().unregisterDexSnappingCallback(observer);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void scheduleNotifyDexSnappingCallback(int taskId, Rect otherBounds) {
    try {
      getDefault().scheduleNotifyDexSnappingCallback(taskId, otherBounds);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void initDockingBounds(Rect leftBounds, Rect rightBounds, int displayWidth) {
    try {
      getDefault().initDockingBounds(leftBounds, rightBounds, displayWidth);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void setCandidateTask(int TaskId) {
    try {
      getDefault().setCandidateTask(TaskId);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public int calculateMaxWidth(int taskDockingState, int displayWidth, int defaultMinWidth) {
    try {
      return getDefault().calculateMaxWidth(taskDockingState, displayWidth, defaultMinWidth);
    } catch (RemoteException e) {
      warningException(e);
      return defaultMinWidth;
    }
  }

  public void resizeOtherTaskIfNeeded(int taskId, Rect bounds) {
    try {
      getDefault().resizeOtherTaskIfNeeded(taskId, bounds);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void clearAllDockingTasks(String reason) {
    try {
      getDefault().clearAllDockingTasks(reason);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public static String changeFreeformStashModeToString(int changeStashMode) {
    switch (changeStashMode) {
      case 0:
        return "CHANGE_FREEFORM_STASH_UNDEFINED";
      case 1:
        return "CHANGE_FREEFORM_STASH_FOCUSABLE";
      case 2:
        return "CHANGE_FREEFORM_STASH_NONE_FOCUSABLE";
      default:
        return Integer.toString(changeStashMode);
    }
  }

  public void setBoostFreeformTaskLayer(int taskId, boolean boost) {
    try {
      getDefault().setBoostFreeformTaskLayer(taskId, boost);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public void toggleFreeformForDexCompatApp(int taskId) {
    try {
      getDefault().toggleFreeformForDexCompatApp(taskId);
    } catch (RemoteException e) {
      warningException(e);
    }
  }

  public boolean supportMultiSplitAppMinimumSize() {
    if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      try {
        return getDefault().supportMultiSplitAppMinimumSize();
      } catch (RemoteException e) {
        warningException(e);
        return false;
      }
    }
    return false;
  }

  public void updateMultiSplitAppMinimumSize() {
    if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
      try {
        getDefault().updateMultiSplitAppMinimumSize();
      } catch (RemoteException e) {
        warningException(e);
      }
    }
  }

  public boolean isVisibleTaskInDexDisplay(PendingIntent intent) {
    try {
      return getDefault().isVisibleTaskInDexDisplay(intent);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean isVisibleTaskByTaskIdInDexDisplay(int taskId) {
    try {
      return getDefault().isVisibleTaskByTaskIdInDexDisplay(taskId);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public boolean shouldDeferEnterSplit(List<PendingIntent> intents, List<Integer> taskIds) {
    try {
      return getDefault().shouldDeferEnterSplit(intents, taskIds);
    } catch (RemoteException e) {
      warningException(e);
      return false;
    }
  }

  public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener listener) {
    try {
      getDefault().registerDexTransientDelayListener(listener);
    } catch (RemoteException e) {
      warningException(e);
    }
  }
}
