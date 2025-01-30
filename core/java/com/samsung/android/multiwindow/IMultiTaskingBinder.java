package com.samsung.android.multiwindow;

import android.app.PendingIntent;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.window.WindowContainerToken;
import java.util.List;

/* loaded from: classes5.dex */
public interface IMultiTaskingBinder extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IMultiTaskingBinder";

  int calculateMaxWidth(int i, int i2, int i3) throws RemoteException;

  void clearAllDockingTasks(String str) throws RemoteException;

  void dismissSplitTask(IBinder iBinder, boolean z) throws RemoteException;

  boolean exitMultiWindow(IBinder iBinder, boolean z) throws RemoteException;

  void finishNaturalSwitching() throws RemoteException;

  List<String> getAllowedMultiWindowPackageList() throws RemoteException;

  int getDexTaskInfoFlags(IBinder iBinder) throws RemoteException;

  boolean getEmbedActivityPackageEnabled(String str, int i) throws RemoteException;

  PointF getFreeformContainerPoint() throws RemoteException;

  StringParceledListSlice getMWDisableRequesters() throws RemoteException;

  ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException;

  int getMultiSplitFlags() throws RemoteException;

  StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException;

  int getMultiWindowModeStates(int i) throws RemoteException;

  int getResizeMode(ActivityInfo activityInfo) throws RemoteException;

  List<String> getSplitActivityAllowPackages() throws RemoteException;

  int getSplitActivityPackageEnabled(String str, int i) throws RemoteException;

  List<String> getSupportEmbedActivityPackages() throws RemoteException;

  SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) throws RemoteException;

  ParceledListSlice getTaskInfoFromPackageName(String str) throws RemoteException;

  ParceledListSlice getVisibleTasks() throws RemoteException;

  boolean hasMinimizedToggleTasks() throws RemoteException;

  void initDockingBounds(Rect rect, Rect rect2, int i) throws RemoteException;

  boolean isAllTasksResizable(int i, int i2, int i3) throws RemoteException;

  boolean isAllowedMultiWindowPackage(String str) throws RemoteException;

  boolean isDismissedFlexPanelMode() throws RemoteException;

  boolean isFlexPanelRunning() throws RemoteException;

  boolean isMultiWindowBlockListApp(String str) throws RemoteException;

  boolean isSplitImmersiveModeEnabled() throws RemoteException;

  boolean isValidCornerGesture(MotionEvent motionEvent) throws RemoteException;

  boolean isVisibleTaskByTaskIdInDexDisplay(int i) throws RemoteException;

  boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) throws RemoteException;

  boolean minimizeAllTasks(int i) throws RemoteException;

  boolean minimizeAllTasksByRecents(int i) throws RemoteException;

  boolean minimizeTaskById(int i) throws RemoteException;

  boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) throws RemoteException;

  void notifyDragSplitAppIconHasDrawable(boolean z) throws RemoteException;

  void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) throws RemoteException;

  boolean preventNaturalSwitching(int i) throws RemoteException;

  void registerDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback)
      throws RemoteException;

  void registerDexTransientDelayListener(
      IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) throws RemoteException;

  void registerFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException;

  void registerRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException;

  boolean removeFocusedTask(int i) throws RemoteException;

  void reportFreeformContainerPoint(PointF pointF) throws RemoteException;

  void resizeOtherTaskIfNeeded(int i, Rect rect) throws RemoteException;

  void rotateDexCompatTask(IBinder iBinder) throws RemoteException;

  void saveFreeformBounds(int i) throws RemoteException;

  void scheduleNotifyDexSnappingCallback(int i, Rect rect) throws RemoteException;

  void setBlockedMinimizeFreeformEnable(boolean z) throws RemoteException;

  void setBoostFreeformTaskLayer(int i, boolean z) throws RemoteException;

  void setCandidateTask(int i) throws RemoteException;

  void setCornerGestureEnabledWithSettings(boolean z) throws RemoteException;

  void setCustomDensityEnabled(int i) throws RemoteException;

  void setEmbedActivityPackageEnabled(String str, boolean z, int i) throws RemoteException;

  void setEnsureLaunchSplitEnabled(boolean z) throws RemoteException;

  void setMaxVisibleFreeformCountForDex(int i, int i2) throws RemoteException;

  void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i)
      throws RemoteException;

  void setNaviStarSplitImmersiveMode(boolean z) throws RemoteException;

  void setSplitActivityPackageEnabled(String str, int i, int i2) throws RemoteException;

  void setSplitImmersiveMode(boolean z) throws RemoteException;

  void setStayFocusActivityEnabled(boolean z) throws RemoteException;

  void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) throws RemoteException;

  boolean shouldDeferEnterSplit(List<PendingIntent> list, List list2) throws RemoteException;

  boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) throws RemoteException;

  boolean supportMultiSplitAppMinimumSize() throws RemoteException;

  boolean supportsMultiWindow(IBinder iBinder) throws RemoteException;

  void toggleFreeformForDexCompatApp(int i) throws RemoteException;

  boolean toggleFreeformWindowingMode() throws RemoteException;

  void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken)
      throws RemoteException;

  void unregisterDexSnappingCallback(IDexSnappingCallback iDexSnappingCallback)
      throws RemoteException;

  void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException;

  void unregisterRemoteAppTransitionListener(
      IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException;

  void updateMultiSplitAppMinimumSize() throws RemoteException;

  public static class Default implements IMultiTaskingBinder {
    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean minimizeTaskById(int taskId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean minimizeAllTasks(int displayId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean minimizeAllTasksByRecents(int displayId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean minimizeTaskToSpecificPosition(int taskId, boolean animate, int x, int y)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean hasMinimizedToggleTasks() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void registerFreeformCallback(IFreeformCallback observer) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void unregisterFreeformCallback(IFreeformCallback observer) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void notifyFreeformMinimizeAnimationEnd(int taskId, PointF point)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void reportFreeformContainerPoint(PointF point) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public PointF getFreeformContainerPoint() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setBoostFreeformTaskLayer(int taskId, boolean boost) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setBlockedMinimizeFreeformEnable(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setCustomDensityEnabled(int enabledFlags) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setEnsureLaunchSplitEnabled(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public ParceledListSlice getVisibleTasks() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setMaxVisibleFreeformCountForDex(int maxCount, int maxDexCount)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public StringParceledListSlice getMWDisableRequesters() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setMultiWindowEnabledForUser(
        String requester, String reason, boolean enable, int userId) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int getDexTaskInfoFlags(IBinder token) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isAllTasksResizable(int taskId1, int taskId2, int taskId3)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean supportsMultiWindow(IBinder token) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int getResizeMode(ActivityInfo aInfo) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isAllowedMultiWindowPackage(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public List<String> getAllowedMultiWindowPackageList() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isMultiWindowBlockListApp(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean exitMultiWindow(IBinder token, boolean checkPermission) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int getMultiWindowModeStates(int displayId) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void notifyDragSplitAppIconHasDrawable(boolean hasDrawable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int taskId) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean startNaturalSwitching(IBinder client, IBinder dragTargetToken)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void finishNaturalSwitching() throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isFlexPanelRunning() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void dismissSplitTask(IBinder token, boolean homeBehindTopTask) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void rotateDexCompatTask(IBinder token) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public List<String> getSplitActivityAllowPackages() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int getSplitActivityPackageEnabled(String packageName, int userId)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setSplitActivityPackageEnabled(String packageName, int newState, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean getEmbedActivityPackageEnabled(String packageName, int userId)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setEmbedActivityPackageEnabled(String packageName, boolean enabled, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public List<String> getSupportEmbedActivityPackages() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setCornerGestureEnabledWithSettings(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isValidCornerGesture(MotionEvent ev) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public ParceledListSlice getTaskInfoFromPackageName(String packageName) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean removeFocusedTask(int displayId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setStayFocusActivityEnabled(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setStayFocusAndTopResumedActivityEnabled(
        boolean stayFocusEnable, boolean stayTopResumedEnable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setSplitImmersiveMode(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isSplitImmersiveModeEnabled() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setNaviStarSplitImmersiveMode(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int getMultiSplitFlags() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void registerDexSnappingCallback(IDexSnappingCallback callback) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void unregisterDexSnappingCallback(IDexSnappingCallback callback)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void scheduleNotifyDexSnappingCallback(int taskId, Rect otherBounds)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void initDockingBounds(Rect leftBounds, Rect rightBounds, int displayWidth)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void setCandidateTask(int TaskId) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public int calculateMaxWidth(int taskDockingState, int displayWidth, int defaultMinWidth)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void resizeOtherTaskIfNeeded(int taskId, Rect bounds) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void clearAllDockingTasks(String reason) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void toggleFreeformForDexCompatApp(int taskId) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean supportMultiSplitAppMinimumSize() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void updateMultiSplitAppMinimumSize() throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isDismissedFlexPanelMode() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean toggleFreeformWindowingMode() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean preventNaturalSwitching(int TaskId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void saveFreeformBounds(int taskId) throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void toggleFreeformWindowingModeForDex(WindowContainerToken token)
        throws RemoteException {}

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isVisibleTaskInDexDisplay(PendingIntent intent) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean isVisibleTaskByTaskIdInDexDisplay(int taskId) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public boolean shouldDeferEnterSplit(List<PendingIntent> intents, List taskIds)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
    public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener listener)
        throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IMultiTaskingBinder {
    static final int TRANSACTION_calculateMaxWidth = 60;
    static final int TRANSACTION_clearAllDockingTasks = 62;
    static final int TRANSACTION_dismissSplitTask = 37;
    static final int TRANSACTION_exitMultiWindow = 30;
    static final int TRANSACTION_finishNaturalSwitching = 35;
    static final int TRANSACTION_getAllowedMultiWindowPackageList = 27;
    static final int TRANSACTION_getDexTaskInfoFlags = 20;
    static final int TRANSACTION_getEmbedActivityPackageEnabled = 42;
    static final int TRANSACTION_getFreeformContainerPoint = 10;
    static final int TRANSACTION_getMWDisableRequesters = 18;
    static final int TRANSACTION_getMinimizedFreeformTasksForCurrentUser = 11;
    static final int TRANSACTION_getMultiSplitFlags = 54;
    static final int TRANSACTION_getMultiWindowBlockListApp = 29;
    static final int TRANSACTION_getMultiWindowModeStates = 31;
    static final int TRANSACTION_getResizeMode = 25;
    static final int TRANSACTION_getSplitActivityAllowPackages = 39;
    static final int TRANSACTION_getSplitActivityPackageEnabled = 40;
    static final int TRANSACTION_getSupportEmbedActivityPackages = 44;
    static final int TRANSACTION_getSurfaceFreezerSnapshot = 33;
    static final int TRANSACTION_getTaskInfoFromPackageName = 47;
    static final int TRANSACTION_getVisibleTasks = 16;
    static final int TRANSACTION_hasMinimizedToggleTasks = 5;
    static final int TRANSACTION_initDockingBounds = 58;
    static final int TRANSACTION_isAllTasksResizable = 21;
    static final int TRANSACTION_isAllowedMultiWindowPackage = 26;
    static final int TRANSACTION_isDismissedFlexPanelMode = 66;
    static final int TRANSACTION_isFlexPanelRunning = 36;
    static final int TRANSACTION_isMultiWindowBlockListApp = 28;
    static final int TRANSACTION_isSplitImmersiveModeEnabled = 52;
    static final int TRANSACTION_isValidCornerGesture = 46;
    static final int TRANSACTION_isVisibleTaskByTaskIdInDexDisplay = 72;
    static final int TRANSACTION_isVisibleTaskInDexDisplay = 71;
    static final int TRANSACTION_minimizeAllTasks = 2;
    static final int TRANSACTION_minimizeAllTasksByRecents = 3;
    static final int TRANSACTION_minimizeTaskById = 1;
    static final int TRANSACTION_minimizeTaskToSpecificPosition = 4;
    static final int TRANSACTION_notifyDragSplitAppIconHasDrawable = 32;
    static final int TRANSACTION_notifyFreeformMinimizeAnimationEnd = 8;
    static final int TRANSACTION_preventNaturalSwitching = 68;
    static final int TRANSACTION_registerDexSnappingCallback = 55;
    static final int TRANSACTION_registerDexTransientDelayListener = 74;
    static final int TRANSACTION_registerFreeformCallback = 6;
    static final int TRANSACTION_registerRemoteAppTransitionListener = 22;
    static final int TRANSACTION_removeFocusedTask = 48;
    static final int TRANSACTION_reportFreeformContainerPoint = 9;
    static final int TRANSACTION_resizeOtherTaskIfNeeded = 61;
    static final int TRANSACTION_rotateDexCompatTask = 38;
    static final int TRANSACTION_saveFreeformBounds = 69;
    static final int TRANSACTION_scheduleNotifyDexSnappingCallback = 57;
    static final int TRANSACTION_setBlockedMinimizeFreeformEnable = 13;
    static final int TRANSACTION_setBoostFreeformTaskLayer = 12;
    static final int TRANSACTION_setCandidateTask = 59;
    static final int TRANSACTION_setCornerGestureEnabledWithSettings = 45;
    static final int TRANSACTION_setCustomDensityEnabled = 14;
    static final int TRANSACTION_setEmbedActivityPackageEnabled = 43;
    static final int TRANSACTION_setEnsureLaunchSplitEnabled = 15;
    static final int TRANSACTION_setMaxVisibleFreeformCountForDex = 17;
    static final int TRANSACTION_setMultiWindowEnabledForUser = 19;
    static final int TRANSACTION_setNaviStarSplitImmersiveMode = 53;
    static final int TRANSACTION_setSplitActivityPackageEnabled = 41;
    static final int TRANSACTION_setSplitImmersiveMode = 51;
    static final int TRANSACTION_setStayFocusActivityEnabled = 49;
    static final int TRANSACTION_setStayFocusAndTopResumedActivityEnabled = 50;
    static final int TRANSACTION_shouldDeferEnterSplit = 73;
    static final int TRANSACTION_startNaturalSwitching = 34;
    static final int TRANSACTION_supportMultiSplitAppMinimumSize = 64;
    static final int TRANSACTION_supportsMultiWindow = 24;
    static final int TRANSACTION_toggleFreeformForDexCompatApp = 63;
    static final int TRANSACTION_toggleFreeformWindowingMode = 67;
    static final int TRANSACTION_toggleFreeformWindowingModeForDex = 70;
    static final int TRANSACTION_unregisterDexSnappingCallback = 56;
    static final int TRANSACTION_unregisterFreeformCallback = 7;
    static final int TRANSACTION_unregisterRemoteAppTransitionListener = 23;
    static final int TRANSACTION_updateMultiSplitAppMinimumSize = 65;

    public Stub() {
      attachInterface(this, IMultiTaskingBinder.DESCRIPTOR);
    }

    public static IMultiTaskingBinder asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IMultiTaskingBinder.DESCRIPTOR);
      if (iin != null && (iin instanceof IMultiTaskingBinder)) {
        return (IMultiTaskingBinder) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "minimizeTaskById";
        case 2:
          return "minimizeAllTasks";
        case 3:
          return "minimizeAllTasksByRecents";
        case 4:
          return "minimizeTaskToSpecificPosition";
        case 5:
          return "hasMinimizedToggleTasks";
        case 6:
          return "registerFreeformCallback";
        case 7:
          return "unregisterFreeformCallback";
        case 8:
          return "notifyFreeformMinimizeAnimationEnd";
        case 9:
          return "reportFreeformContainerPoint";
        case 10:
          return "getFreeformContainerPoint";
        case 11:
          return "getMinimizedFreeformTasksForCurrentUser";
        case 12:
          return "setBoostFreeformTaskLayer";
        case 13:
          return "setBlockedMinimizeFreeformEnable";
        case 14:
          return "setCustomDensityEnabled";
        case 15:
          return "setEnsureLaunchSplitEnabled";
        case 16:
          return "getVisibleTasks";
        case 17:
          return "setMaxVisibleFreeformCountForDex";
        case 18:
          return "getMWDisableRequesters";
        case 19:
          return "setMultiWindowEnabledForUser";
        case 20:
          return "getDexTaskInfoFlags";
        case 21:
          return "isAllTasksResizable";
        case 22:
          return "registerRemoteAppTransitionListener";
        case 23:
          return "unregisterRemoteAppTransitionListener";
        case 24:
          return "supportsMultiWindow";
        case 25:
          return "getResizeMode";
        case 26:
          return "isAllowedMultiWindowPackage";
        case 27:
          return "getAllowedMultiWindowPackageList";
        case 28:
          return "isMultiWindowBlockListApp";
        case 29:
          return "getMultiWindowBlockListApp";
        case 30:
          return "exitMultiWindow";
        case 31:
          return "getMultiWindowModeStates";
        case 32:
          return "notifyDragSplitAppIconHasDrawable";
        case 33:
          return "getSurfaceFreezerSnapshot";
        case 34:
          return "startNaturalSwitching";
        case 35:
          return "finishNaturalSwitching";
        case 36:
          return "isFlexPanelRunning";
        case 37:
          return "dismissSplitTask";
        case 38:
          return "rotateDexCompatTask";
        case 39:
          return "getSplitActivityAllowPackages";
        case 40:
          return "getSplitActivityPackageEnabled";
        case 41:
          return "setSplitActivityPackageEnabled";
        case 42:
          return "getEmbedActivityPackageEnabled";
        case 43:
          return "setEmbedActivityPackageEnabled";
        case 44:
          return "getSupportEmbedActivityPackages";
        case 45:
          return "setCornerGestureEnabledWithSettings";
        case 46:
          return "isValidCornerGesture";
        case 47:
          return "getTaskInfoFromPackageName";
        case 48:
          return "removeFocusedTask";
        case 49:
          return "setStayFocusActivityEnabled";
        case 50:
          return "setStayFocusAndTopResumedActivityEnabled";
        case 51:
          return "setSplitImmersiveMode";
        case 52:
          return "isSplitImmersiveModeEnabled";
        case 53:
          return "setNaviStarSplitImmersiveMode";
        case 54:
          return "getMultiSplitFlags";
        case 55:
          return "registerDexSnappingCallback";
        case 56:
          return "unregisterDexSnappingCallback";
        case 57:
          return "scheduleNotifyDexSnappingCallback";
        case 58:
          return "initDockingBounds";
        case 59:
          return "setCandidateTask";
        case 60:
          return "calculateMaxWidth";
        case 61:
          return "resizeOtherTaskIfNeeded";
        case 62:
          return "clearAllDockingTasks";
        case 63:
          return "toggleFreeformForDexCompatApp";
        case 64:
          return "supportMultiSplitAppMinimumSize";
        case 65:
          return "updateMultiSplitAppMinimumSize";
        case 66:
          return "isDismissedFlexPanelMode";
        case 67:
          return "toggleFreeformWindowingMode";
        case 68:
          return "preventNaturalSwitching";
        case 69:
          return "saveFreeformBounds";
        case 70:
          return "toggleFreeformWindowingModeForDex";
        case 71:
          return "isVisibleTaskInDexDisplay";
        case 72:
          return "isVisibleTaskByTaskIdInDexDisplay";
        case 73:
          return "shouldDeferEnterSplit";
        case 74:
          return "registerDexTransientDelayListener";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IMultiTaskingBinder.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IMultiTaskingBinder.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = minimizeTaskById(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = minimizeAllTasks(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = minimizeAllTasksByRecents(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              int _arg04 = data.readInt();
              boolean _arg1 = data.readBoolean();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = minimizeTaskToSpecificPosition(_arg04, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              boolean _result5 = hasMinimizedToggleTasks();
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 6:
              IFreeformCallback _arg05 =
                  IFreeformCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerFreeformCallback(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              IFreeformCallback _arg06 =
                  IFreeformCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterFreeformCallback(_arg06);
              reply.writeNoException();
              return true;
            case 8:
              int _arg07 = data.readInt();
              PointF _arg12 = (PointF) data.readTypedObject(PointF.CREATOR);
              data.enforceNoDataAvail();
              notifyFreeformMinimizeAnimationEnd(_arg07, _arg12);
              reply.writeNoException();
              return true;
            case 9:
              PointF _arg08 = (PointF) data.readTypedObject(PointF.CREATOR);
              data.enforceNoDataAvail();
              reportFreeformContainerPoint(_arg08);
              reply.writeNoException();
              return true;
            case 10:
              PointF _result6 = getFreeformContainerPoint();
              reply.writeNoException();
              reply.writeTypedObject(_result6, 1);
              return true;
            case 11:
              ParceledListSlice _result7 = getMinimizedFreeformTasksForCurrentUser();
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 12:
              int _arg09 = data.readInt();
              boolean _arg13 = data.readBoolean();
              data.enforceNoDataAvail();
              setBoostFreeformTaskLayer(_arg09, _arg13);
              reply.writeNoException();
              return true;
            case 13:
              boolean _arg010 = data.readBoolean();
              data.enforceNoDataAvail();
              setBlockedMinimizeFreeformEnable(_arg010);
              return true;
            case 14:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              setCustomDensityEnabled(_arg011);
              reply.writeNoException();
              return true;
            case 15:
              boolean _arg012 = data.readBoolean();
              data.enforceNoDataAvail();
              setEnsureLaunchSplitEnabled(_arg012);
              reply.writeNoException();
              return true;
            case 16:
              ParceledListSlice _result8 = getVisibleTasks();
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 17:
              int _arg013 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              setMaxVisibleFreeformCountForDex(_arg013, _arg14);
              reply.writeNoException();
              return true;
            case 18:
              StringParceledListSlice _result9 = getMWDisableRequesters();
              reply.writeNoException();
              reply.writeTypedObject(_result9, 1);
              return true;
            case 19:
              String _arg014 = data.readString();
              String _arg15 = data.readString();
              boolean _arg22 = data.readBoolean();
              int _arg32 = data.readInt();
              data.enforceNoDataAvail();
              setMultiWindowEnabledForUser(_arg014, _arg15, _arg22, _arg32);
              reply.writeNoException();
              return true;
            case 20:
              IBinder _arg015 = data.readStrongBinder();
              data.enforceNoDataAvail();
              int _result10 = getDexTaskInfoFlags(_arg015);
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 21:
              int _arg016 = data.readInt();
              int _arg16 = data.readInt();
              int _arg23 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result11 = isAllTasksResizable(_arg016, _arg16, _arg23);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 22:
              IRemoteAppTransitionListener _arg017 =
                  IRemoteAppTransitionListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerRemoteAppTransitionListener(_arg017);
              reply.writeNoException();
              return true;
            case 23:
              IBinder _arg018 = data.readStrongBinder();
              IRemoteAppTransitionListener _arg019 =
                  IRemoteAppTransitionListener.Stub.asInterface(_arg018);
              data.enforceNoDataAvail();
              unregisterRemoteAppTransitionListener(_arg019);
              reply.writeNoException();
              return true;
            case 24:
              IBinder _arg020 = data.readStrongBinder();
              data.enforceNoDataAvail();
              boolean _result12 = supportsMultiWindow(_arg020);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 25:
              ActivityInfo _arg021 = (ActivityInfo) data.readTypedObject(ActivityInfo.CREATOR);
              data.enforceNoDataAvail();
              int _result13 = getResizeMode(_arg021);
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 26:
              String _arg022 = data.readString();
              data.enforceNoDataAvail();
              boolean _result14 = isAllowedMultiWindowPackage(_arg022);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 27:
              List<String> _result15 = getAllowedMultiWindowPackageList();
              reply.writeNoException();
              reply.writeStringList(_result15);
              return true;
            case 28:
              String _arg023 = data.readString();
              data.enforceNoDataAvail();
              boolean _result16 = isMultiWindowBlockListApp(_arg023);
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 29:
              StringParceledListSlice _result17 = getMultiWindowBlockListApp();
              reply.writeNoException();
              reply.writeTypedObject(_result17, 1);
              return true;
            case 30:
              IBinder _arg024 = data.readStrongBinder();
              boolean _arg17 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result18 = exitMultiWindow(_arg024, _arg17);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 31:
              int _arg025 = data.readInt();
              data.enforceNoDataAvail();
              int _result19 = getMultiWindowModeStates(_arg025);
              reply.writeNoException();
              reply.writeInt(_result19);
              return true;
            case 32:
              boolean _arg026 = data.readBoolean();
              data.enforceNoDataAvail();
              notifyDragSplitAppIconHasDrawable(_arg026);
              reply.writeNoException();
              return true;
            case 33:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              SurfaceFreezerSnapshot _result20 = getSurfaceFreezerSnapshot(_arg027);
              reply.writeNoException();
              reply.writeTypedObject(_result20, 1);
              return true;
            case 34:
              IBinder _arg028 = data.readStrongBinder();
              IBinder _arg18 = data.readStrongBinder();
              data.enforceNoDataAvail();
              boolean _result21 = startNaturalSwitching(_arg028, _arg18);
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 35:
              finishNaturalSwitching();
              reply.writeNoException();
              return true;
            case 36:
              boolean _result22 = isFlexPanelRunning();
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 37:
              IBinder _arg029 = data.readStrongBinder();
              boolean _arg19 = data.readBoolean();
              data.enforceNoDataAvail();
              dismissSplitTask(_arg029, _arg19);
              reply.writeNoException();
              return true;
            case 38:
              IBinder _arg030 = data.readStrongBinder();
              data.enforceNoDataAvail();
              rotateDexCompatTask(_arg030);
              reply.writeNoException();
              return true;
            case 39:
              List<String> _result23 = getSplitActivityAllowPackages();
              reply.writeNoException();
              reply.writeStringList(_result23);
              return true;
            case 40:
              String _arg031 = data.readString();
              int _arg110 = data.readInt();
              data.enforceNoDataAvail();
              int _result24 = getSplitActivityPackageEnabled(_arg031, _arg110);
              reply.writeNoException();
              reply.writeInt(_result24);
              return true;
            case 41:
              String _arg032 = data.readString();
              int _arg111 = data.readInt();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              setSplitActivityPackageEnabled(_arg032, _arg111, _arg24);
              reply.writeNoException();
              return true;
            case 42:
              String _arg033 = data.readString();
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result25 = getEmbedActivityPackageEnabled(_arg033, _arg112);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 43:
              String _arg034 = data.readString();
              boolean _arg113 = data.readBoolean();
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              setEmbedActivityPackageEnabled(_arg034, _arg113, _arg25);
              reply.writeNoException();
              return true;
            case 44:
              List<String> _result26 = getSupportEmbedActivityPackages();
              reply.writeNoException();
              reply.writeStringList(_result26);
              return true;
            case 45:
              boolean _arg035 = data.readBoolean();
              data.enforceNoDataAvail();
              setCornerGestureEnabledWithSettings(_arg035);
              reply.writeNoException();
              return true;
            case 46:
              MotionEvent _arg036 = (MotionEvent) data.readTypedObject(MotionEvent.CREATOR);
              data.enforceNoDataAvail();
              boolean _result27 = isValidCornerGesture(_arg036);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 47:
              String _arg037 = data.readString();
              data.enforceNoDataAvail();
              ParceledListSlice _result28 = getTaskInfoFromPackageName(_arg037);
              reply.writeNoException();
              reply.writeTypedObject(_result28, 1);
              return true;
            case 48:
              int _arg038 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result29 = removeFocusedTask(_arg038);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 49:
              boolean _arg039 = data.readBoolean();
              data.enforceNoDataAvail();
              setStayFocusActivityEnabled(_arg039);
              reply.writeNoException();
              return true;
            case 50:
              boolean _arg040 = data.readBoolean();
              boolean _arg114 = data.readBoolean();
              data.enforceNoDataAvail();
              setStayFocusAndTopResumedActivityEnabled(_arg040, _arg114);
              reply.writeNoException();
              return true;
            case 51:
              boolean _arg041 = data.readBoolean();
              data.enforceNoDataAvail();
              setSplitImmersiveMode(_arg041);
              reply.writeNoException();
              return true;
            case 52:
              boolean _result30 = isSplitImmersiveModeEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result30);
              return true;
            case 53:
              boolean _arg042 = data.readBoolean();
              data.enforceNoDataAvail();
              setNaviStarSplitImmersiveMode(_arg042);
              reply.writeNoException();
              return true;
            case 54:
              int _result31 = getMultiSplitFlags();
              reply.writeNoException();
              reply.writeInt(_result31);
              return true;
            case 55:
              IDexSnappingCallback _arg043 =
                  IDexSnappingCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerDexSnappingCallback(_arg043);
              reply.writeNoException();
              return true;
            case 56:
              IDexSnappingCallback _arg044 =
                  IDexSnappingCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterDexSnappingCallback(_arg044);
              reply.writeNoException();
              return true;
            case 57:
              int _arg045 = data.readInt();
              Rect _arg115 = (Rect) data.readTypedObject(Rect.CREATOR);
              data.enforceNoDataAvail();
              scheduleNotifyDexSnappingCallback(_arg045, _arg115);
              reply.writeNoException();
              return true;
            case 58:
              Rect _arg046 = (Rect) data.readTypedObject(Rect.CREATOR);
              Rect _arg116 = (Rect) data.readTypedObject(Rect.CREATOR);
              int _arg26 = data.readInt();
              data.enforceNoDataAvail();
              initDockingBounds(_arg046, _arg116, _arg26);
              reply.writeNoException();
              return true;
            case 59:
              int _arg047 = data.readInt();
              data.enforceNoDataAvail();
              setCandidateTask(_arg047);
              reply.writeNoException();
              return true;
            case 60:
              int _arg048 = data.readInt();
              int _arg117 = data.readInt();
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              int _result32 = calculateMaxWidth(_arg048, _arg117, _arg27);
              reply.writeNoException();
              reply.writeInt(_result32);
              return true;
            case 61:
              int _arg049 = data.readInt();
              Rect _arg118 = (Rect) data.readTypedObject(Rect.CREATOR);
              data.enforceNoDataAvail();
              resizeOtherTaskIfNeeded(_arg049, _arg118);
              reply.writeNoException();
              return true;
            case 62:
              String _arg050 = data.readString();
              data.enforceNoDataAvail();
              clearAllDockingTasks(_arg050);
              reply.writeNoException();
              return true;
            case 63:
              int _arg051 = data.readInt();
              data.enforceNoDataAvail();
              toggleFreeformForDexCompatApp(_arg051);
              reply.writeNoException();
              return true;
            case 64:
              boolean _result33 = supportMultiSplitAppMinimumSize();
              reply.writeNoException();
              reply.writeBoolean(_result33);
              return true;
            case 65:
              updateMultiSplitAppMinimumSize();
              reply.writeNoException();
              return true;
            case 66:
              boolean _result34 = isDismissedFlexPanelMode();
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 67:
              boolean _result35 = toggleFreeformWindowingMode();
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 68:
              int _arg052 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result36 = preventNaturalSwitching(_arg052);
              reply.writeNoException();
              reply.writeBoolean(_result36);
              return true;
            case 69:
              int _arg053 = data.readInt();
              data.enforceNoDataAvail();
              saveFreeformBounds(_arg053);
              reply.writeNoException();
              return true;
            case 70:
              WindowContainerToken _arg054 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              data.enforceNoDataAvail();
              toggleFreeformWindowingModeForDex(_arg054);
              reply.writeNoException();
              return true;
            case 71:
              PendingIntent _arg055 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              data.enforceNoDataAvail();
              boolean _result37 = isVisibleTaskInDexDisplay(_arg055);
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            case 72:
              int _arg056 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result38 = isVisibleTaskByTaskIdInDexDisplay(_arg056);
              reply.writeNoException();
              reply.writeBoolean(_result38);
              return true;
            case 73:
              List<PendingIntent> _arg057 = data.createTypedArrayList(PendingIntent.CREATOR);
              ClassLoader cl = getClass().getClassLoader();
              List _arg119 = data.readArrayList(cl);
              data.enforceNoDataAvail();
              boolean _result39 = shouldDeferEnterSplit(_arg057, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result39);
              return true;
            case 74:
              IDexTransientCaptionDelayListener _arg058 =
                  IDexTransientCaptionDelayListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerDexTransientDelayListener(_arg058);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMultiTaskingBinder {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IMultiTaskingBinder.DESCRIPTOR;
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean minimizeTaskById(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean minimizeAllTasks(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean minimizeAllTasksByRecents(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean minimizeTaskToSpecificPosition(int taskId, boolean animate, int x, int y)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(animate);
          _data.writeInt(x);
          _data.writeInt(y);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean hasMinimizedToggleTasks() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void registerFreeformCallback(IFreeformCallback observer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void unregisterFreeformCallback(IFreeformCallback observer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void notifyFreeformMinimizeAnimationEnd(int taskId, PointF point)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(point, 0);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void reportFreeformContainerPoint(PointF point) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(point, 0);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public PointF getFreeformContainerPoint() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          PointF _result = (PointF) _reply.readTypedObject(PointF.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setBoostFreeformTaskLayer(int taskId, boolean boost) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(boost);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setBlockedMinimizeFreeformEnable(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(13, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setCustomDensityEnabled(int enabledFlags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(enabledFlags);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setEnsureLaunchSplitEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public ParceledListSlice getVisibleTasks() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setMaxVisibleFreeformCountForDex(int maxCount, int maxDexCount)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(maxCount);
          _data.writeInt(maxDexCount);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public StringParceledListSlice getMWDisableRequesters() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          StringParceledListSlice _result =
              (StringParceledListSlice) _reply.readTypedObject(StringParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setMultiWindowEnabledForUser(
          String requester, String reason, boolean enable, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(requester);
          _data.writeString(reason);
          _data.writeBoolean(enable);
          _data.writeInt(userId);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int getDexTaskInfoFlags(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isAllTasksResizable(int taskId1, int taskId2, int taskId3)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId1);
          _data.writeInt(taskId2);
          _data.writeInt(taskId3);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean supportsMultiWindow(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int getResizeMode(ActivityInfo aInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(aInfo, 0);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isAllowedMultiWindowPackage(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public List<String> getAllowedMultiWindowPackageList() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isMultiWindowBlockListApp(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          StringParceledListSlice _result =
              (StringParceledListSlice) _reply.readTypedObject(StringParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean exitMultiWindow(IBinder token, boolean checkPermission)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeBoolean(checkPermission);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int getMultiWindowModeStates(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void notifyDragSplitAppIconHasDrawable(boolean hasDrawable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(hasDrawable);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          SurfaceFreezerSnapshot _result =
              (SurfaceFreezerSnapshot) _reply.readTypedObject(SurfaceFreezerSnapshot.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean startNaturalSwitching(IBinder client, IBinder dragTargetToken)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(client);
          _data.writeStrongBinder(dragTargetToken);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void finishNaturalSwitching() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isFlexPanelRunning() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void dismissSplitTask(IBinder token, boolean homeBehindTopTask)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeBoolean(homeBehindTopTask);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void rotateDexCompatTask(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public List<String> getSplitActivityAllowPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int getSplitActivityPackageEnabled(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setSplitActivityPackageEnabled(String packageName, int newState, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(newState);
          _data.writeInt(userId);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean getEmbedActivityPackageEnabled(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setEmbedActivityPackageEnabled(String packageName, boolean enabled, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeBoolean(enabled);
          _data.writeInt(userId);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public List<String> getSupportEmbedActivityPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setCornerGestureEnabledWithSettings(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isValidCornerGesture(MotionEvent ev) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(ev, 0);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public ParceledListSlice getTaskInfoFromPackageName(String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean removeFocusedTask(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setStayFocusActivityEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setStayFocusAndTopResumedActivityEnabled(
          boolean stayFocusEnable, boolean stayTopResumedEnable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(stayFocusEnable);
          _data.writeBoolean(stayTopResumedEnable);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setSplitImmersiveMode(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isSplitImmersiveModeEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setNaviStarSplitImmersiveMode(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int getMultiSplitFlags() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void registerDexSnappingCallback(IDexSnappingCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void unregisterDexSnappingCallback(IDexSnappingCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void scheduleNotifyDexSnappingCallback(int taskId, Rect otherBounds)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(otherBounds, 0);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void initDockingBounds(Rect leftBounds, Rect rightBounds, int displayWidth)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(leftBounds, 0);
          _data.writeTypedObject(rightBounds, 0);
          _data.writeInt(displayWidth);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void setCandidateTask(int TaskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(TaskId);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public int calculateMaxWidth(int taskDockingState, int displayWidth, int defaultMinWidth)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskDockingState);
          _data.writeInt(displayWidth);
          _data.writeInt(defaultMinWidth);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void resizeOtherTaskIfNeeded(int taskId, Rect bounds) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(bounds, 0);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void clearAllDockingTasks(String reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeString(reason);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void toggleFreeformForDexCompatApp(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean supportMultiSplitAppMinimumSize() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void updateMultiSplitAppMinimumSize() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isDismissedFlexPanelMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean toggleFreeformWindowingMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean preventNaturalSwitching(int TaskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(TaskId);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void saveFreeformBounds(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void toggleFreeformWindowingModeForDex(WindowContainerToken token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(token, 0);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isVisibleTaskInDexDisplay(PendingIntent intent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean isVisibleTaskByTaskIdInDexDisplay(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public boolean shouldDeferEnterSplit(List<PendingIntent> intents, List taskIds)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeTypedList(intents, 0);
          _data.writeList(taskIds);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
      public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 73;
    }
  }
}
