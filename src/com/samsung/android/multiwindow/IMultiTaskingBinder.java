package com.samsung.android.multiwindow;

import android.app.PendingIntent;
import android.content.Intent;
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
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IMultiTaskingBinder extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IMultiTaskingBinder";

    public static class Default implements IMultiTaskingBinder {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int calculateMaxWidth(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void changeToHorizontalSplitLayout(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void clearAllDockingTasks(String str) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void dismissSplitTask(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void enableHighResolutionsForExternalDesktop(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean exitMultiWindow(IBinder iBinder, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void finishNaturalSwitching() throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public List<String> getAllowedMultiWindowPackageList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getDexTaskInfoFlags(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean getEmbedActivityPackageEnabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public PointF getFreeformContainerPoint() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public StringParceledListSlice getMWDisableRequesters() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getModeForSameAssistantActivity(Intent intent) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getMultiSplitFlags() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getMultiWindowModeStates(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getResizeMode(ActivityInfo activityInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public List<String> getSplitActivityAllowPackages() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public int getSplitActivityPackageEnabled(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public List<String> getSupportEmbedActivityPackages() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public ParceledListSlice getTaskInfoFromPackageName(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public ParceledListSlice getVisibleTasks(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean hasMinimizedToggleTasks() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean inDesktopWindowing() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void initDockingBounds(Rect rect, Rect rect2, int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isAllTasksResizable(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isAllowedMultiWindowPackage(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isCornerGestureRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isDismissedFlexPanelMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isFlexPanelRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isMultiWindowBlockListApp(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isSplitImmersiveModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isValidCornerGesture(MotionEvent motionEvent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isVisibleTaskByTaskIdInDexDisplay(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean minimizeAllTasks(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean minimizeAllTasksByRecents(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean minimizeTaskById(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void notifyDragSplitAppIconHasDrawable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void notifyDragTaskToMoveStarted() throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean preventNaturalSwitching(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void registerFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean removeFocusedTask(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void reportFreeformContainerPoint(PointF pointF) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void resizeOtherTaskIfNeeded(int i, Rect rect) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void saveFreeformBounds(int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setBlockedMinimizeFreeformEnable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setBoostFreeformTaskLayer(int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setCandidateTask(int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setCornerGestureEnabledWithSettings(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setCustomDensityEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setEmbedActivityPackageEnabled(String str, boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setEnsureLaunchSplitEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setInDesktopWindowing(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setNaviStarSplitImmersiveMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setSplitActivityPackageEnabled(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setSplitImmersiveMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setStayFocusActivityEnabled(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean shouldDeferEnterSplit(List<PendingIntent> list, List list2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void startAssistantActivityToSplit(Intent intent, float f) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean supportMultiSplitAppMinimumSize() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean supportsMultiWindow(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public boolean toggleFreeformWindowingMode() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
        public void updateMultiSplitAppMinimumSize() throws RemoteException {
        }
    }

    int calculateMaxWidth(int i, int i2, int i3) throws RemoteException;

    void changeToHorizontalSplitLayout(IBinder iBinder) throws RemoteException;

    void clearAllDockingTasks(String str) throws RemoteException;

    void dismissSplitTask(IBinder iBinder, boolean z) throws RemoteException;

    void enableHighResolutionsForExternalDesktop(boolean z) throws RemoteException;

    boolean exitMultiWindow(IBinder iBinder, boolean z) throws RemoteException;

    void finishNaturalSwitching() throws RemoteException;

    List<String> getAllowedMultiWindowPackageList() throws RemoteException;

    int getDexTaskInfoFlags(IBinder iBinder) throws RemoteException;

    boolean getEmbedActivityPackageEnabled(String str, int i) throws RemoteException;

    PointF getFreeformContainerPoint() throws RemoteException;

    StringParceledListSlice getMWDisableRequesters() throws RemoteException;

    ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException;

    int getModeForSameAssistantActivity(Intent intent) throws RemoteException;

    int getMultiSplitFlags() throws RemoteException;

    StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException;

    int getMultiWindowModeStates(int i) throws RemoteException;

    int getResizeMode(ActivityInfo activityInfo) throws RemoteException;

    List<String> getSplitActivityAllowPackages() throws RemoteException;

    int getSplitActivityPackageEnabled(String str, int i) throws RemoteException;

    List<String> getSupportEmbedActivityPackages() throws RemoteException;

    SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) throws RemoteException;

    ParceledListSlice getTaskInfoFromPackageName(String str) throws RemoteException;

    ParceledListSlice getVisibleTasks(int i) throws RemoteException;

    boolean hasMinimizedToggleTasks() throws RemoteException;

    boolean inDesktopWindowing() throws RemoteException;

    void initDockingBounds(Rect rect, Rect rect2, int i) throws RemoteException;

    boolean isAllTasksResizable(int i, int i2, int i3) throws RemoteException;

    boolean isAllowedMultiWindowPackage(String str) throws RemoteException;

    boolean isCornerGestureRunning() throws RemoteException;

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

    void notifyDragTaskToMoveStarted() throws RemoteException;

    void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) throws RemoteException;

    boolean preventNaturalSwitching(int i) throws RemoteException;

    void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) throws RemoteException;

    void registerFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException;

    void registerRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException;

    boolean removeFocusedTask(int i) throws RemoteException;

    void reportFreeformContainerPoint(PointF pointF) throws RemoteException;

    void resizeOtherTaskIfNeeded(int i, Rect rect) throws RemoteException;

    void saveFreeformBounds(int i) throws RemoteException;

    void setBlockedMinimizeFreeformEnable(boolean z) throws RemoteException;

    void setBoostFreeformTaskLayer(int i, boolean z) throws RemoteException;

    void setCandidateTask(int i) throws RemoteException;

    void setCornerGestureEnabledWithSettings(boolean z) throws RemoteException;

    void setCustomDensityEnabled(int i, boolean z) throws RemoteException;

    void setEmbedActivityPackageEnabled(String str, boolean z, int i) throws RemoteException;

    void setEnsureLaunchSplitEnabled(boolean z) throws RemoteException;

    void setInDesktopWindowing(boolean z) throws RemoteException;

    void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) throws RemoteException;

    void setNaviStarSplitImmersiveMode(boolean z) throws RemoteException;

    void setSplitActivityPackageEnabled(String str, int i, int i2) throws RemoteException;

    void setSplitImmersiveMode(boolean z) throws RemoteException;

    void setStayFocusActivityEnabled(boolean z) throws RemoteException;

    void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) throws RemoteException;

    boolean shouldDeferEnterSplit(List<PendingIntent> list, List list2) throws RemoteException;

    void startAssistantActivityToSplit(Intent intent, float f) throws RemoteException;

    boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    boolean supportMultiSplitAppMinimumSize() throws RemoteException;

    boolean supportsMultiWindow(IBinder iBinder) throws RemoteException;

    boolean toggleFreeformWindowingMode() throws RemoteException;

    void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) throws RemoteException;

    void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException;

    void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException;

    void updateMultiSplitAppMinimumSize() throws RemoteException;

    public static abstract class Stub extends Binder implements IMultiTaskingBinder {
        static final int TRANSACTION_calculateMaxWidth = 66;
        static final int TRANSACTION_changeToHorizontalSplitLayout = 63;
        static final int TRANSACTION_clearAllDockingTasks = 68;
        static final int TRANSACTION_dismissSplitTask = 62;
        static final int TRANSACTION_enableHighResolutionsForExternalDesktop = 75;
        static final int TRANSACTION_exitMultiWindow = 1;
        static final int TRANSACTION_finishNaturalSwitching = 59;
        static final int TRANSACTION_getAllowedMultiWindowPackageList = 6;
        static final int TRANSACTION_getDexTaskInfoFlags = 47;
        static final int TRANSACTION_getEmbedActivityPackageEnabled = 22;
        static final int TRANSACTION_getFreeformContainerPoint = 18;
        static final int TRANSACTION_getMWDisableRequesters = 9;
        static final int TRANSACTION_getMinimizedFreeformTasksForCurrentUser = 19;
        static final int TRANSACTION_getModeForSameAssistantActivity = 71;
        static final int TRANSACTION_getMultiSplitFlags = 21;
        static final int TRANSACTION_getMultiWindowBlockListApp = 8;
        static final int TRANSACTION_getMultiWindowModeStates = 2;
        static final int TRANSACTION_getResizeMode = 4;
        static final int TRANSACTION_getSplitActivityAllowPackages = 25;
        static final int TRANSACTION_getSplitActivityPackageEnabled = 26;
        static final int TRANSACTION_getSupportEmbedActivityPackages = 24;
        static final int TRANSACTION_getSurfaceFreezerSnapshot = 57;
        static final int TRANSACTION_getTaskInfoFromPackageName = 31;
        static final int TRANSACTION_getVisibleTasks = 20;
        static final int TRANSACTION_hasMinimizedToggleTasks = 46;
        static final int TRANSACTION_inDesktopWindowing = 74;
        static final int TRANSACTION_initDockingBounds = 64;
        static final int TRANSACTION_isAllTasksResizable = 13;
        static final int TRANSACTION_isAllowedMultiWindowPackage = 5;
        static final int TRANSACTION_isCornerGestureRunning = 30;
        static final int TRANSACTION_isDismissedFlexPanelMode = 52;
        static final int TRANSACTION_isFlexPanelRunning = 61;
        static final int TRANSACTION_isMultiWindowBlockListApp = 7;
        static final int TRANSACTION_isSplitImmersiveModeEnabled = 38;
        static final int TRANSACTION_isValidCornerGesture = 29;
        static final int TRANSACTION_isVisibleTaskByTaskIdInDexDisplay = 44;
        static final int TRANSACTION_isVisibleTaskInDexDisplay = 43;
        static final int TRANSACTION_minimizeAllTasks = 34;
        static final int TRANSACTION_minimizeAllTasksByRecents = 35;
        static final int TRANSACTION_minimizeTaskById = 33;
        static final int TRANSACTION_minimizeTaskToSpecificPosition = 36;
        static final int TRANSACTION_notifyDragSplitAppIconHasDrawable = 56;
        static final int TRANSACTION_notifyDragTaskToMoveStarted = 76;
        static final int TRANSACTION_notifyFreeformMinimizeAnimationEnd = 16;
        static final int TRANSACTION_preventNaturalSwitching = 60;
        static final int TRANSACTION_registerDexTransientDelayListener = 72;
        static final int TRANSACTION_registerFreeformCallback = 14;
        static final int TRANSACTION_registerRemoteAppTransitionListener = 11;
        static final int TRANSACTION_removeFocusedTask = 32;
        static final int TRANSACTION_reportFreeformContainerPoint = 17;
        static final int TRANSACTION_resizeOtherTaskIfNeeded = 67;
        static final int TRANSACTION_saveFreeformBounds = 39;
        static final int TRANSACTION_setBlockedMinimizeFreeformEnable = 53;
        static final int TRANSACTION_setBoostFreeformTaskLayer = 48;
        static final int TRANSACTION_setCandidateTask = 65;
        static final int TRANSACTION_setCornerGestureEnabledWithSettings = 28;
        static final int TRANSACTION_setCustomDensityEnabled = 54;
        static final int TRANSACTION_setEmbedActivityPackageEnabled = 23;
        static final int TRANSACTION_setEnsureLaunchSplitEnabled = 55;
        static final int TRANSACTION_setInDesktopWindowing = 73;
        static final int TRANSACTION_setMultiWindowEnabledForUser = 10;
        static final int TRANSACTION_setNaviStarSplitImmersiveMode = 40;
        static final int TRANSACTION_setSplitActivityPackageEnabled = 27;
        static final int TRANSACTION_setSplitImmersiveMode = 37;
        static final int TRANSACTION_setStayFocusActivityEnabled = 41;
        static final int TRANSACTION_setStayFocusAndTopResumedActivityEnabled = 42;
        static final int TRANSACTION_shouldDeferEnterSplit = 45;
        static final int TRANSACTION_startAssistantActivityToSplit = 70;
        static final int TRANSACTION_startNaturalSwitching = 58;
        static final int TRANSACTION_supportMultiSplitAppMinimumSize = 50;
        static final int TRANSACTION_supportsMultiWindow = 3;
        static final int TRANSACTION_toggleFreeformWindowingMode = 69;
        static final int TRANSACTION_toggleFreeformWindowingModeForDex = 49;
        static final int TRANSACTION_unregisterFreeformCallback = 15;
        static final int TRANSACTION_unregisterRemoteAppTransitionListener = 12;
        static final int TRANSACTION_updateMultiSplitAppMinimumSize = 51;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 75;
        }

        public Stub() {
            attachInterface(this, IMultiTaskingBinder.DESCRIPTOR);
        }

        public static IMultiTaskingBinder asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMultiTaskingBinder.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMultiTaskingBinder)) {
                return (IMultiTaskingBinder) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "exitMultiWindow";
                case 2:
                    return "getMultiWindowModeStates";
                case 3:
                    return "supportsMultiWindow";
                case 4:
                    return "getResizeMode";
                case 5:
                    return "isAllowedMultiWindowPackage";
                case 6:
                    return "getAllowedMultiWindowPackageList";
                case 7:
                    return "isMultiWindowBlockListApp";
                case 8:
                    return "getMultiWindowBlockListApp";
                case 9:
                    return "getMWDisableRequesters";
                case 10:
                    return "setMultiWindowEnabledForUser";
                case 11:
                    return "registerRemoteAppTransitionListener";
                case 12:
                    return "unregisterRemoteAppTransitionListener";
                case 13:
                    return "isAllTasksResizable";
                case 14:
                    return "registerFreeformCallback";
                case 15:
                    return "unregisterFreeformCallback";
                case 16:
                    return "notifyFreeformMinimizeAnimationEnd";
                case 17:
                    return "reportFreeformContainerPoint";
                case 18:
                    return "getFreeformContainerPoint";
                case 19:
                    return "getMinimizedFreeformTasksForCurrentUser";
                case 20:
                    return "getVisibleTasks";
                case 21:
                    return "getMultiSplitFlags";
                case 22:
                    return "getEmbedActivityPackageEnabled";
                case 23:
                    return "setEmbedActivityPackageEnabled";
                case 24:
                    return "getSupportEmbedActivityPackages";
                case 25:
                    return "getSplitActivityAllowPackages";
                case 26:
                    return "getSplitActivityPackageEnabled";
                case 27:
                    return "setSplitActivityPackageEnabled";
                case 28:
                    return "setCornerGestureEnabledWithSettings";
                case 29:
                    return "isValidCornerGesture";
                case 30:
                    return "isCornerGestureRunning";
                case 31:
                    return "getTaskInfoFromPackageName";
                case 32:
                    return "removeFocusedTask";
                case 33:
                    return "minimizeTaskById";
                case 34:
                    return "minimizeAllTasks";
                case 35:
                    return "minimizeAllTasksByRecents";
                case 36:
                    return "minimizeTaskToSpecificPosition";
                case 37:
                    return "setSplitImmersiveMode";
                case 38:
                    return "isSplitImmersiveModeEnabled";
                case 39:
                    return "saveFreeformBounds";
                case 40:
                    return "setNaviStarSplitImmersiveMode";
                case 41:
                    return "setStayFocusActivityEnabled";
                case 42:
                    return "setStayFocusAndTopResumedActivityEnabled";
                case 43:
                    return "isVisibleTaskInDexDisplay";
                case 44:
                    return "isVisibleTaskByTaskIdInDexDisplay";
                case 45:
                    return "shouldDeferEnterSplit";
                case 46:
                    return "hasMinimizedToggleTasks";
                case 47:
                    return "getDexTaskInfoFlags";
                case 48:
                    return "setBoostFreeformTaskLayer";
                case 49:
                    return "toggleFreeformWindowingModeForDex";
                case 50:
                    return "supportMultiSplitAppMinimumSize";
                case 51:
                    return "updateMultiSplitAppMinimumSize";
                case 52:
                    return "isDismissedFlexPanelMode";
                case 53:
                    return "setBlockedMinimizeFreeformEnable";
                case 54:
                    return "setCustomDensityEnabled";
                case 55:
                    return "setEnsureLaunchSplitEnabled";
                case 56:
                    return "notifyDragSplitAppIconHasDrawable";
                case 57:
                    return "getSurfaceFreezerSnapshot";
                case 58:
                    return "startNaturalSwitching";
                case 59:
                    return "finishNaturalSwitching";
                case 60:
                    return "preventNaturalSwitching";
                case 61:
                    return "isFlexPanelRunning";
                case 62:
                    return "dismissSplitTask";
                case 63:
                    return "changeToHorizontalSplitLayout";
                case 64:
                    return "initDockingBounds";
                case 65:
                    return "setCandidateTask";
                case 66:
                    return "calculateMaxWidth";
                case 67:
                    return "resizeOtherTaskIfNeeded";
                case 68:
                    return "clearAllDockingTasks";
                case 69:
                    return "toggleFreeformWindowingMode";
                case 70:
                    return "startAssistantActivityToSplit";
                case 71:
                    return "getModeForSameAssistantActivity";
                case 72:
                    return "registerDexTransientDelayListener";
                case 73:
                    return "setInDesktopWindowing";
                case 74:
                    return "inDesktopWindowing";
                case 75:
                    return "enableHighResolutionsForExternalDesktop";
                case 76:
                    return "notifyDragTaskToMoveStarted";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMultiTaskingBinder.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMultiTaskingBinder.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zExitMultiWindow = exitMultiWindow(strongBinder, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zExitMultiWindow);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int multiWindowModeStates = getMultiWindowModeStates(i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(multiWindowModeStates);
                    return true;
                case 3:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zSupportsMultiWindow = supportsMultiWindow(strongBinder2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsMultiWindow);
                    return true;
                case 4:
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int resizeMode = getResizeMode(activityInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(resizeMode);
                    return true;
                case 5:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAllowedMultiWindowPackage = isAllowedMultiWindowPackage(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowedMultiWindowPackage);
                    return true;
                case 6:
                    List<String> allowedMultiWindowPackageList = getAllowedMultiWindowPackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedMultiWindowPackageList);
                    return true;
                case 7:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsMultiWindowBlockListApp = isMultiWindowBlockListApp(string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMultiWindowBlockListApp);
                    return true;
                case 8:
                    StringParceledListSlice multiWindowBlockListApp = getMultiWindowBlockListApp();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(multiWindowBlockListApp, 1);
                    return true;
                case 9:
                    StringParceledListSlice mWDisableRequesters = getMWDisableRequesters();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mWDisableRequesters, 1);
                    return true;
                case 10:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMultiWindowEnabledForUser(string3, string4, z2, i4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IRemoteAppTransitionListener iRemoteAppTransitionListenerAsInterface = IRemoteAppTransitionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteAppTransitionListener(iRemoteAppTransitionListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IRemoteAppTransitionListener iRemoteAppTransitionListenerAsInterface2 = IRemoteAppTransitionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAppTransitionListener(iRemoteAppTransitionListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAllTasksResizable = isAllTasksResizable(i5, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllTasksResizable);
                    return true;
                case 14:
                    IFreeformCallback iFreeformCallbackAsInterface = IFreeformCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerFreeformCallback(iFreeformCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IFreeformCallback iFreeformCallbackAsInterface2 = IFreeformCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterFreeformCallback(iFreeformCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i8 = parcel.readInt();
                    PointF pointF = (PointF) parcel.readTypedObject(PointF.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFreeformMinimizeAnimationEnd(i8, pointF);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    PointF pointF2 = (PointF) parcel.readTypedObject(PointF.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportFreeformContainerPoint(pointF2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    PointF freeformContainerPoint = getFreeformContainerPoint();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(freeformContainerPoint, 1);
                    return true;
                case 19:
                    ParceledListSlice minimizedFreeformTasksForCurrentUser = getMinimizedFreeformTasksForCurrentUser();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(minimizedFreeformTasksForCurrentUser, 1);
                    return true;
                case 20:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice visibleTasks = getVisibleTasks(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visibleTasks, 1);
                    return true;
                case 21:
                    int multiSplitFlags = getMultiSplitFlags();
                    parcel2.writeNoException();
                    parcel2.writeInt(multiSplitFlags);
                    return true;
                case 22:
                    String string5 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean embedActivityPackageEnabled = getEmbedActivityPackageEnabled(string5, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(embedActivityPackageEnabled);
                    return true;
                case 23:
                    String string6 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmbedActivityPackageEnabled(string6, z3, i11);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    List<String> supportEmbedActivityPackages = getSupportEmbedActivityPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportEmbedActivityPackages);
                    return true;
                case 25:
                    List<String> splitActivityAllowPackages = getSplitActivityAllowPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(splitActivityAllowPackages);
                    return true;
                case 26:
                    String string7 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int splitActivityPackageEnabled = getSplitActivityPackageEnabled(string7, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(splitActivityPackageEnabled);
                    return true;
                case 27:
                    String string8 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSplitActivityPackageEnabled(string8, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCornerGestureEnabledWithSettings(z4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsValidCornerGesture = isValidCornerGesture(motionEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsValidCornerGesture);
                    return true;
                case 30:
                    boolean zIsCornerGestureRunning = isCornerGestureRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCornerGestureRunning);
                    return true;
                case 31:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice taskInfoFromPackageName = getTaskInfoFromPackageName(string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskInfoFromPackageName, 1);
                    return true;
                case 32:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveFocusedTask = removeFocusedTask(i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveFocusedTask);
                    return true;
                case 33:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMinimizeTaskById = minimizeTaskById(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMinimizeTaskById);
                    return true;
                case 34:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMinimizeAllTasks = minimizeAllTasks(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMinimizeAllTasks);
                    return true;
                case 35:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMinimizeAllTasksByRecents = minimizeAllTasksByRecents(i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMinimizeAllTasksByRecents);
                    return true;
                case 36:
                    int i19 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMinimizeTaskToSpecificPosition = minimizeTaskToSpecificPosition(i19, z5, i20, i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMinimizeTaskToSpecificPosition);
                    return true;
                case 37:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSplitImmersiveMode(z6);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean zIsSplitImmersiveModeEnabled = isSplitImmersiveModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSplitImmersiveModeEnabled);
                    return true;
                case 39:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    saveFreeformBounds(i22);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNaviStarSplitImmersiveMode(z7);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStayFocusActivityEnabled(z8);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean z9 = parcel.readBoolean();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStayFocusAndTopResumedActivityEnabled(z9, z10);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsVisibleTaskInDexDisplay = isVisibleTaskInDexDisplay(pendingIntent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVisibleTaskInDexDisplay);
                    return true;
                case 44:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsVisibleTaskByTaskIdInDexDisplay = isVisibleTaskByTaskIdInDexDisplay(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVisibleTaskByTaskIdInDexDisplay);
                    return true;
                case 45:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    boolean zShouldDeferEnterSplit = shouldDeferEnterSplit(arrayListCreateTypedArrayList, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldDeferEnterSplit);
                    return true;
                case 46:
                    boolean zHasMinimizedToggleTasks = hasMinimizedToggleTasks();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasMinimizedToggleTasks);
                    return true;
                case 47:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int dexTaskInfoFlags = getDexTaskInfoFlags(strongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexTaskInfoFlags);
                    return true;
                case 48:
                    int i24 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBoostFreeformTaskLayer(i24, z11);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    toggleFreeformWindowingModeForDex(windowContainerToken);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    boolean zSupportMultiSplitAppMinimumSize = supportMultiSplitAppMinimumSize();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportMultiSplitAppMinimumSize);
                    return true;
                case 51:
                    updateMultiSplitAppMinimumSize();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean zIsDismissedFlexPanelMode = isDismissedFlexPanelMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDismissedFlexPanelMode);
                    return true;
                case 53:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlockedMinimizeFreeformEnable(z12);
                    return true;
                case 54:
                    int i25 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCustomDensityEnabled(i25, z13);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnsureLaunchSplitEnabled(z14);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyDragSplitAppIconHasDrawable(z15);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceFreezerSnapshot surfaceFreezerSnapshot = getSurfaceFreezerSnapshot(i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceFreezerSnapshot, 1);
                    return true;
                case 58:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zStartNaturalSwitching = startNaturalSwitching(strongBinder4, strongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartNaturalSwitching);
                    return true;
                case 59:
                    finishNaturalSwitching();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPreventNaturalSwitching = preventNaturalSwitching(i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPreventNaturalSwitching);
                    return true;
                case 61:
                    boolean zIsFlexPanelRunning = isFlexPanelRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFlexPanelRunning);
                    return true;
                case 62:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissSplitTask(strongBinder6, z16);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    changeToHorizontalSplitLayout(strongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initDockingBounds(rect, rect2, i28);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCandidateTask(i29);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCalculateMaxWidth = calculateMaxWidth(i30, i31, i32);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCalculateMaxWidth);
                    return true;
                case 67:
                    int i33 = parcel.readInt();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizeOtherTaskIfNeeded(i33, rect3);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAllDockingTasks(string10);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean z17 = toggleFreeformWindowingMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z17);
                    return true;
                case 70:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    startAssistantActivityToSplit(intent, f);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    int modeForSameAssistantActivity = getModeForSameAssistantActivity(intent2);
                    parcel2.writeNoException();
                    parcel2.writeInt(modeForSameAssistantActivity);
                    return true;
                case 72:
                    IDexTransientCaptionDelayListener iDexTransientCaptionDelayListenerAsInterface = IDexTransientCaptionDelayListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDexTransientDelayListener(iDexTransientCaptionDelayListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInDesktopWindowing(z18);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    boolean zInDesktopWindowing = inDesktopWindowing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInDesktopWindowing);
                    return true;
                case 75:
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableHighResolutionsForExternalDesktop(z19);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    notifyDragTaskToMoveStarted();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMultiTaskingBinder {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMultiTaskingBinder.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean exitMultiWindow(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getMultiWindowModeStates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean supportsMultiWindow(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getResizeMode(ActivityInfo activityInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(activityInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isAllowedMultiWindowPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getAllowedMultiWindowPackageList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isMultiWindowBlockListApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StringParceledListSlice) parcelObtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public StringParceledListSlice getMWDisableRequesters() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StringParceledListSlice) parcelObtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAppTransitionListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAppTransitionListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isAllTasksResizable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFreeformCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFreeformCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pointF, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void reportFreeformContainerPoint(PointF pointF) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointF, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public PointF getFreeformContainerPoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PointF) parcelObtain2.readTypedObject(PointF.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getVisibleTasks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getMultiSplitFlags() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean getEmbedActivityPackageEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setEmbedActivityPackageEnabled(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getSupportEmbedActivityPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getSplitActivityAllowPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getSplitActivityPackageEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setSplitActivityPackageEnabled(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCornerGestureEnabledWithSettings(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isValidCornerGesture(MotionEvent motionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isCornerGestureRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getTaskInfoFromPackageName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean removeFocusedTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeTaskById(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeAllTasks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeAllTasksByRecents(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setSplitImmersiveMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isSplitImmersiveModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void saveFreeformBounds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setNaviStarSplitImmersiveMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setStayFocusActivityEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isVisibleTaskByTaskIdInDexDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean shouldDeferEnterSplit(List<PendingIntent> list, List list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeList(list2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean hasMinimizedToggleTasks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getDexTaskInfoFlags(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setBoostFreeformTaskLayer(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean supportMultiSplitAppMinimumSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void updateMultiSplitAppMinimumSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isDismissedFlexPanelMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setBlockedMinimizeFreeformEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCustomDensityEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setEnsureLaunchSplitEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void notifyDragSplitAppIconHasDrawable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SurfaceFreezerSnapshot) parcelObtain2.readTypedObject(SurfaceFreezerSnapshot.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void finishNaturalSwitching() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean preventNaturalSwitching(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isFlexPanelRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void dismissSplitTask(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void changeToHorizontalSplitLayout(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void initDockingBounds(Rect rect, Rect rect2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeTypedObject(rect2, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCandidateTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int calculateMaxWidth(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void resizeOtherTaskIfNeeded(int i, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void clearAllDockingTasks(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean toggleFreeformWindowingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void startAssistantActivityToSplit(Intent intent, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getModeForSameAssistantActivity(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDexTransientCaptionDelayListener);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setInDesktopWindowing(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean inDesktopWindowing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void enableHighResolutionsForExternalDesktop(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void notifyDragTaskToMoveStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
