package com.samsung.android.multiwindow;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.input.KeyboardLayout;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Singleton;
import android.view.MotionEvent;
import android.window.WindowContainerToken;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class MultiWindowManager {
    public static final String ACTION_AUTORUN_FLEX_PANEL = "android.intent.action.AUTORUN_FLEX_PANEL";
    public static final String ACTION_COLLAPSE_FLEX_PANEL = "android.intent.action.COLLAPSE_FLEX_PANEL";
    public static final String ACTION_ENTER_CONTENTS_TO_WINDOW = "com.samsung.android.action.ENTER_CONTENTS_TO_WINDOW";
    public static final String ACTION_EXPAND_FLEX_PANEL = "android.intent.action.EXPAND_FLEX_PANEL";
    public static final String ACTION_MINIMIZE_ALL = "com.samsung.android.multiwindow.MINIMIZE_ALL";
    public static final String ACTION_MINIMIZE_ALL_BY_SYSTEM = "com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM";
    public static final String ACTION_MULTI_WINDOW_ENABLE_CHANGED = "com.samsung.android.action.MULTI_WINDOW_ENABLE_CHANGED";
    public static final int ASSISTANT_HOT_KEY_MODE_FREEFORM = 3;
    public static final int ASSISTANT_HOT_KEY_MODE_FULL = 1;
    public static final int ASSISTANT_HOT_KEY_MODE_SPLIT = 2;
    public static final int CHANGE_CAPTION_SHOWING_INVISIBLE = 2;
    public static final int CHANGE_CAPTION_SHOWING_UNDEFINED = 0;
    public static final int CHANGE_CAPTION_SHOWING_VISIBLE = 1;
    public static final int CHANGE_FREEFORM_STASH_FOCUSABLE = 1;
    public static final int CHANGE_FREEFORM_STASH_NONE_FOCUSABLE = 2;
    public static final int CHANGE_FREEFORM_STASH_UNDEFINED = 0;
    public static final int CHANGE_TRANSIT_FLAG_FORCE_COLLECT = 2;
    public static final int CHANGE_TRANSIT_FLAG_ROTATE_FREEFORM_TASK = 4;
    public static final int CHANGE_TRANSIT_FLAG_USE_FLOATING_LAYER = 1;
    public static final int CHANGE_TRANSIT_MODE_DISMISS = 2;
    public static final int CHANGE_TRANSIT_MODE_DISPLAY_CHANGE = 8;
    public static final int CHANGE_TRANSIT_MODE_MOVE_BACK_IN_SPLIT_SCREEN = 6;
    public static final int CHANGE_TRANSIT_MODE_NATURAL_SWITCHING = 4;
    public static final int CHANGE_TRANSIT_MODE_POP_OVER = 5;
    public static final int CHANGE_TRANSIT_MODE_SPLIT_ACTIVITY = 7;
    public static final int CHANGE_TRANSIT_MODE_STANDARD = 1;
    public static final int CHANGE_TRANSIT_MODE_UNDEFINED = 0;
    public static final int DESKTOP_CORNER_RADIUS_IN_DP = 8;
    public static final int DESK_ROOT_TASK_TYPE_ACTIVATABLE = 1;
    public static final int DESK_ROOT_TASK_TYPE_MINIMIZED = 2;
    public static final int DESK_ROOT_TASK_TYPE_UNDEFINED = 0;
    public static final int EMBED_ACTIVITY_PACKAGE_DISABLED = 2;
    public static final int EMBED_ACTIVITY_PACKAGE_ENABLED = 1;
    public static final int EMBED_ACTIVITY_PACKAGE_UNDEFINED = 0;
    public static final String EXTRA_AI_HOT_KEY_LAUNCH_BOUNDS = "ai_hot_key_launch_bounds";
    public static final String EXTRA_AI_HOT_KEY_LAUNCH_FREEFORM = "ai_hot_key_launch_freeform";
    public static final String EXTRA_AI_LAUNCH_MODE = "ai_launch_mode";
    public static final String EXTRA_AI_LAUNCH_SPLIT_RATIO = "ai_launch_split_ratio";
    public static final String EXTRA_IN_MULTI_WINDOW_MODE = "com.samsung.android.extra.IN_MULTI_WINDOW_MODE";
    public static final String EXTRA_LOAD_ALL_ITEMS = "load_all_items";
    public static final String EXTRA_MULTI_WINDOW_ENABLED = "com.samsung.android.extra.MULTI_WINDOW_ENABLED";
    public static final String EXTRA_MULTI_WINDOW_ENABLED_USER_ID = "com.samsung.android.extra.MULTI_WINDOW_ENABLED_USER_ID";
    public static final String EXTRA_MULTI_WINDOW_ENABLE_REQUESTER = "com.samsung.android.extra.MULTI_WINDOW_ENABLE_REQUESTER";
    public static final String FLEX_MODE_PANEL_ENABLED = "flex_mode_panel_enabled";
    public static final int FORCE_HIDING_TRANSIT_ENTER = 1;
    public static final int FORCE_HIDING_TRANSIT_ENTER_WITHOUT_ANIMATION = 3;
    public static final int FORCE_HIDING_TRANSIT_EXIT = 2;
    public static final int FORCE_HIDING_TRANSIT_EXIT_WITHOUT_ANIMATION = 4;
    public static final int FORCE_HIDING_TRANSIT_UNDEFINED = 0;
    public static final int FREEFORM_CAPTION_TYPE_BAR = 1;
    public static final int FREEFORM_CAPTION_TYPE_HANDLE = 0;
    public static final int FREEFORM_CAPTION_TYPE_UNDEFINED = -1;
    public static final int FREEFORM_CORNER_RADIUS_IN_DP = 14;
    public static final int FREEFORM_STASH_VISIBLE_WIDTH_IN_DP = 32;
    public static final int FREEFORM_TRANSIT_MINIMIZE = 1;
    public static final int FREEFORM_TRANSIT_NONE = 0;
    public static final int FREEFORM_TRANSIT_RESTORE = 2;
    private static final Singleton<IMultiTaskingBinder> IMultiTaskingBinderSingleton = new Singleton<IMultiTaskingBinder>() { // from class: com.samsung.android.multiwindow.MultiWindowManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IMultiTaskingBinder create() {
            try {
                return ActivityTaskManager.getService().getMultiTaskingBinder();
            } catch (RemoteException unused) {
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
    public static final int NATURAL_SWITCHING_SUPPORT = 2048;
    public static final String PERMISSION_MULTI_WINDOW_MONITOR = "com.samsung.android.permission.MULTI_WINDOW_MONITOR";
    public static final int RESIZE_HANDLE_INSET_IN_DP = 4;
    public static final int RESIZE_HANDLE_OUTSET_IN_PX = 48;
    public static final int RESIZE_HANDLE_POINTER_OUTSET_IN_DP = 10;
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

    public @interface AssistantHotKeyMode {
    }

    public @interface ChangeCaptionVisibility {
    }

    public @interface ChangeFreeformStashMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeTransitionFlags {
    }

    public @interface ChangeTransitionMode {
    }

    public @interface ForceHidingTransit {
    }

    public @interface FreeformCaptionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MultiSplitFlags {
    }

    public @interface SplitActivityPackageEnabledState {
    }

    public @interface SplitFeasibleMode {
    }

    public @interface embedActivityPackageEnabledState {
    }

    public static int createModeToDockSide(int i) {
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i != 4) {
            return i != 5 ? -1 : 4;
        }
        return 3;
    }

    public static int multiSplitFlagsToDockSide(int i) {
        int i2 = i & 1984;
        if (i2 == 128) {
            return 1;
        }
        if (i2 == 256) {
            return 2;
        }
        if (i2 != 512) {
            return i2 != 1024 ? -1 : 4;
        }
        return 3;
    }

    public boolean hasMinimizedToggleTasks() {
        return false;
    }

    public boolean isVisibleTaskByTaskIdInDexDisplay(int i) {
        return false;
    }

    public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) {
        return false;
    }

    public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) {
    }

    public boolean shouldDeferEnterSplit(List<PendingIntent> list, List<Integer> list2) {
        return false;
    }

    public static String changeTransitModeToString(int i) {
        switch (i) {
            case 0:
                return "CHANGE_TRANSIT_MODE_UNDEFINED";
            case 1:
                return "CHANGE_TRANSIT_MODE_STANDARD";
            case 2:
                return "CHANGE_TRANSIT_MODE_DISMISS";
            case 3:
            default:
                return Integer.toString(i);
            case 4:
                return "CHANGE_TRANSIT_MODE_NATURAL_SWITCHING";
            case 5:
                return "CHANGE_TRANSIT_MODE_POP_OVER";
            case 6:
                return "CHANGE_TRANSIT_MODE_MOVE_BACK_IN_SPLIT_SCREEN";
            case 7:
                return "CHANGE_TRANSIT_MODE_SPLIT_ACTIVITY";
            case 8:
                return "CHANGE_TRANSIT_MODE_DISPLAY_CHANGE";
        }
    }

    public static String forceHidingTransitToString(int i) {
        if (i == 0) {
            return "FORCE_HIDING_TRANSIT_UNDEFINED";
        }
        if (i == 1) {
            return "FORCE_HIDING_TRANSIT_ENTER";
        }
        if (i == 2) {
            return "FORCE_HIDING_TRANSIT_EXIT";
        }
        if (i == 3) {
            return "FORCE_HIDING_TRANSIT_ENTER_WITHOUT_ANIMATION";
        }
        if (i == 4) {
            return "FORCE_HIDING_TRANSIT_EXIT_WITHOUT_ANIMATION";
        }
        return Integer.toString(i);
    }

    public static String embedActivityPackageEnabledStateToString(int i) {
        if (i == 0) {
            return "EMBED_ACTIVITY_PACKAGE_UNDEFINED";
        }
        if (i == 1) {
            return "EMBED_ACTIVITY_PACKAGE_ENABLED";
        }
        if (i == 2) {
            return "EMBED_ACTIVITY_PACKAGE_DISABLED";
        }
        return Integer.toString(i);
    }

    public static String splitActivityPackageEnabledStateToString(int i) {
        if (i == 0) {
            return "SPLIT_ACTIVITY_PACKAGE_DISABLED";
        }
        if (i == 1) {
            return "SPLIT_ACTIVITY_PACKAGE_ENABLED";
        }
        if (i == 2) {
            return "SPLIT_ACTIVITY_PACKAGE_BLOCKED";
        }
        return Integer.toString(i);
    }

    public static String changeFreeformStashModeToString(int i) {
        if (i == 0) {
            return "CHANGE_FREEFORM_STASH_UNDEFINED";
        }
        if (i == 1) {
            return "CHANGE_FREEFORM_STASH_FOCUSABLE";
        }
        if (i == 2) {
            return "CHANGE_FREEFORM_STASH_NONE_FOCUSABLE";
        }
        return Integer.toString(i);
    }

    public void setBoostFreeformTaskLayer(int i, boolean z) {
        try {
            getDefault().setBoostFreeformTaskLayer(i, z);
        } catch (RemoteException e) {
            warningException(e);
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

    private static void warningException(Exception exc) {
        Log.w(TAG, "warningException() : caller=" + Debug.getCaller() + exc.getMessage());
    }

    public boolean exitMultiWindow(IBinder iBinder, boolean z) {
        try {
            return getDefault().exitMultiWindow(iBinder, z);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public int getMultiWindowModeStates(int i) {
        try {
            return getDefault().getMultiWindowModeStates(i);
        } catch (RemoteException e) {
            warningException(e);
            return 0;
        }
    }

    public void setCornerGestureEnabledWithSettings(boolean z) {
        try {
            getDefault().setCornerGestureEnabledWithSettings(z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public boolean isValidCornerGesture(MotionEvent motionEvent) {
        try {
            return getDefault().isValidCornerGesture(motionEvent);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public boolean isCornerGestureRunning() {
        try {
            return getDefault().isCornerGestureRunning();
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public void setSplitImmersiveMode(boolean z) {
        try {
            getDefault().setSplitImmersiveMode(z);
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

    public void setNaviBarImmersiveModeLocked(boolean z) {
        try {
            getDefault().setNaviStarSplitImmersiveMode(z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public int getSupportedMultiWindowModes(ResolveInfo resolveInfo) {
        if (resolveInfo == null) {
            return 0;
        }
        return getSupportedMultiWindowModes(resolveInfo.activityInfo);
    }

    public int getSupportedMultiWindowModes(ActivityInfo activityInfo) {
        if (activityInfo == null) {
            return 0;
        }
        int i = activityInfo.resizeMode;
        int i2 = ActivityInfo.isResizeableMode(getResizeMode(activityInfo)) ? 3 : 0;
        return activityInfo.supportsPictureInPicture() ? i2 | 4 : i2;
    }

    public boolean supportsMultiWindow(IBinder iBinder) {
        try {
            return getDefault().supportsMultiWindow(iBinder);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public int getResizeMode(ActivityInfo activityInfo) {
        try {
            return getDefault().getResizeMode(activityInfo);
        } catch (RemoteException e) {
            warningException(e);
            return 0;
        }
    }

    public boolean isAllowedMultiWindowPackage(String str) {
        try {
            return getDefault().isAllowedMultiWindowPackage(str);
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
            return Collections.EMPTY_LIST;
        }
    }

    public boolean isMultiWindowBlockListApp(String str) {
        try {
            return getDefault().isMultiWindowBlockListApp(str);
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
            return Collections.EMPTY_LIST;
        }
    }

    public void notifyDragSplitAppIconHasDrawable(boolean z) {
        try {
            getDefault().notifyDragSplitAppIconHasDrawable(z);
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

    public void setMultiWindowEnabled(String str, String str2, boolean z) {
        setMultiWindowEnabledForUser(str, str2, z, UserHandle.myUserId());
    }

    public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) {
        try {
            getDefault().setMultiWindowEnabledForUser(str, str2, z, i);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) {
        try {
            getDefault().registerRemoteAppTransitionListener(iRemoteAppTransitionListener);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) {
        try {
            getDefault().unregisterRemoteAppTransitionListener(iRemoteAppTransitionListener);
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

    public void dismissSplitTask(IBinder iBinder, boolean z) {
        try {
            getDefault().dismissSplitTask(iBinder, z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public boolean isAllTasksResizable(int i, int i2, int i3) {
        try {
            return getDefault().isAllTasksResizable(i, i2, i3);
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

    public void registerFreeformCallback(IFreeformCallback iFreeformCallback) {
        try {
            getDefault().registerFreeformCallback(iFreeformCallback);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) {
        try {
            getDefault().unregisterFreeformCallback(iFreeformCallback);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) {
        try {
            getDefault().notifyFreeformMinimizeAnimationEnd(i, pointF);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void reportFreeformContainerPoint(PointF pointF) {
        try {
            getDefault().reportFreeformContainerPoint(pointF);
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

    public List<ActivityManager.RunningTaskInfo> getVisibleTasks() {
        return getVisibleTasks(100);
    }

    public List<ActivityManager.RunningTaskInfo> getVisibleTasks(int i) {
        try {
            return getDefault().getVisibleTasks(i).getList();
        } catch (Exception e) {
            warningException(e);
            return Collections.EMPTY_LIST;
        }
    }

    public static StringBuilder multiSplitFlagsToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append(" MULTI_SPLIT_SUPPORT");
        } else if ((i & 2) != 0) {
            sb.append(" MULTI_SPLIT_NOT_SUPPORT");
        } else if ((i & 4) != 0) {
            sb.append(" MULTI_SPLIT_NOT_SUPPORT_BY_HOME");
        }
        if ((i & 8) != 0) {
            sb.append(" MULTI_SPLIT_NONE_SPLIT");
        } else if ((i & 16) != 0) {
            sb.append(" MULTI_SPLIT_TWO_SPLIT");
        } else if ((i & 32) != 0) {
            sb.append(" MULTI_SPLIT_THREE_SPLIT");
        }
        if ((i & 64) != 0) {
            sb.append(" MULTI_SPLIT_INVALID_SIDE");
            return sb;
        }
        if ((i & 128) != 0) {
            sb.append(" MULTI_SPLIT_LEFT_SIDE");
            return sb;
        }
        if ((i & 256) != 0) {
            sb.append(" MULTI_SPLIT_TOP_SIDE");
            return sb;
        }
        if ((i & 512) != 0) {
            sb.append(" MULTI_SPLIT_RIGHT_SIDE");
            return sb;
        }
        if ((i & 1024) != 0) {
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

    public List<ActivityManager.RecentTaskInfo> getTaskInfoFromPackageName(String str) {
        try {
            return getDefault().getTaskInfoFromPackageName(str).getList();
        } catch (RemoteException e) {
            warningException(e);
            return Collections.EMPTY_LIST;
        }
    }

    public boolean removeFocusedTask(int i) {
        try {
            return getDefault().removeFocusedTask(i);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public boolean minimizeTaskById(int i) {
        try {
            return getDefault().minimizeTaskById(i);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public boolean minimizeAllTasks(int i) {
        try {
            return getDefault().minimizeAllTasks(i);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public boolean minimizeAllTasksByRecents(int i) {
        try {
            return getDefault().minimizeAllTasksByRecents(i);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) {
        try {
            return getDefault().minimizeTaskToSpecificPosition(i, z, i2, i3);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public void saveFreeformBounds(int i) {
        try {
            getDefault().saveFreeformBounds(i);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setStayFocusActivityEnabled(boolean z) {
        try {
            getDefault().setStayFocusActivityEnabled(z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) {
        try {
            getDefault().setStayFocusAndTopResumedActivityEnabled(z, z2);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public boolean supportMultiSplitAppMinimumSize() {
        if (!CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            return false;
        }
        try {
            return getDefault().supportMultiSplitAppMinimumSize();
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
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

    public int getDexTaskInfoFlags(IBinder iBinder) {
        try {
            return getDefault().getDexTaskInfoFlags(iBinder);
        } catch (RemoteException e) {
            warningException(e);
            return 0;
        }
    }

    public void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) {
        try {
            getDefault().toggleFreeformWindowingModeForDex(windowContainerToken);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setBlockedMinimizeFreeformEnable(boolean z) {
        try {
            getDefault().setBlockedMinimizeFreeformEnable(z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setCustomDensityEnabled(int i) {
        try {
            getDefault().setCustomDensityEnabled(i, true);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setCustomDensityEnabled(int i, boolean z) {
        try {
            getDefault().setCustomDensityEnabled(i, z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setEnsureLaunchSplitEnabled(boolean z) {
        try {
            getDefault().setEnsureLaunchSplitEnabled(z);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void changeToHorizontalSplitLayout(IBinder iBinder) {
        try {
            getDefault().changeToHorizontalSplitLayout(iBinder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) {
        try {
            return getDefault().getSurfaceFreezerSnapshot(i);
        } catch (RemoteException | IllegalArgumentException e) {
            warningException(e);
            return null;
        }
    }

    public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) {
        try {
            return getDefault().startNaturalSwitching(iBinder, iBinder2);
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

    public boolean preventNaturalSwitching(int i) {
        try {
            return getDefault().preventNaturalSwitching(i);
        } catch (RemoteException e) {
            warningException(e);
            return false;
        }
    }

    public void initDockingBounds(Rect rect, Rect rect2, int i) {
        try {
            getDefault().initDockingBounds(rect, rect2, i);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void setCandidateTask(int i) {
        try {
            getDefault().setCandidateTask(i);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public int calculateMaxWidth(int i, int i2, int i3) {
        try {
            return getDefault().calculateMaxWidth(i, i2, i3);
        } catch (RemoteException e) {
            warningException(e);
            return i3;
        }
    }

    public void resizeOtherTaskIfNeeded(int i, Rect rect) {
        try {
            getDefault().resizeOtherTaskIfNeeded(i, rect);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public void clearAllDockingTasks(String str) {
        try {
            getDefault().clearAllDockingTasks(str);
        } catch (RemoteException e) {
            warningException(e);
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

    public List<String> getSplitActivityAllowPackages() {
        try {
            return getDefault().getSplitActivityAllowPackages();
        } catch (RemoteException e) {
            warningException(e);
            return Collections.EMPTY_LIST;
        }
    }

    public int getSplitActivityPackageEnabled(String str, int i) {
        try {
            return getDefault().getSplitActivityPackageEnabled(str, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getSplitActivityPackageEnabled", e);
            return 0;
        }
    }

    public void setSplitActivityPackageEnabled(String str, int i, int i2) {
        try {
            getDefault().setSplitActivityPackageEnabled(str, i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setSplitActivityPackageEnabled", e);
        }
    }

    public boolean getEmbedActivityPackageEnabled(String str, int i) {
        try {
            return getDefault().getEmbedActivityPackageEnabled(str, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to getEmbedActivityPackageEnabled", e);
            return true;
        }
    }

    public void setEmbedActivityPackageEnabled(String str, boolean z, int i) {
        try {
            getDefault().setEmbedActivityPackageEnabled(str, z, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setEmbedActivityPackageEnabled", e);
        }
    }

    public List<String> getSupportEmbedActivityPackages() {
        try {
            return getDefault().getSupportEmbedActivityPackages();
        } catch (RemoteException e) {
            warningException(e);
            return Collections.EMPTY_LIST;
        }
    }

    public void startAssistantActivityToSplit(Intent intent, float f) {
        try {
            getDefault().startAssistantActivityToSplit(intent, f);
        } catch (RemoteException e) {
            warningException(e);
        }
    }

    public int getModeForSameAssistantActivity(Intent intent) {
        try {
            return getDefault().getModeForSameAssistantActivity(intent);
        } catch (RemoteException e) {
            warningException(e);
            return 1;
        }
    }

    public void setInDesktopWindowing(boolean z) {
        try {
            getDefault().setInDesktopWindowing(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to setIsInDesktopWindowing", e);
        }
    }

    public boolean inDesktopWindowing() {
        try {
            return getDefault().inDesktopWindowing();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to isInDesktopWindowing", e);
            return false;
        }
    }

    public static String deskRootTaskTypeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "activatable";
        }
        if (i == 2) {
            return "minimized";
        }
        return String.valueOf(i);
    }

    public void notifyDragTaskStarted() {
        try {
            getDefault().notifyDragTaskToMoveStarted();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notifyDragTaskStarted", e);
        }
    }

    public void enableHighResolutionsForExternalDesktop(boolean z) {
        try {
            getDefault().enableHighResolutionsForExternalDesktop(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to enableHighResolutionsForExternalDesktop", e);
        }
    }
}
