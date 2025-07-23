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
        static final int TRANSACTION_enableHighResolutionsForExternalDesktop = 74;
        static final int TRANSACTION_exitMultiWindow = 1;
        static final int TRANSACTION_finishNaturalSwitching = 59;
        static final int TRANSACTION_getAllowedMultiWindowPackageList = 6;
        static final int TRANSACTION_getDexTaskInfoFlags = 47;
        static final int TRANSACTION_getEmbedActivityPackageEnabled = 22;
        static final int TRANSACTION_getFreeformContainerPoint = 18;
        static final int TRANSACTION_getMWDisableRequesters = 9;
        static final int TRANSACTION_getMinimizedFreeformTasksForCurrentUser = 19;
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
        static final int TRANSACTION_inDesktopWindowing = 73;
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
        static final int TRANSACTION_notifyFreeformMinimizeAnimationEnd = 16;
        static final int TRANSACTION_preventNaturalSwitching = 60;
        static final int TRANSACTION_registerDexTransientDelayListener = 71;
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
        static final int TRANSACTION_setInDesktopWindowing = 72;
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
            return 73;
        }

        public Stub() {
            attachInterface(this, IMultiTaskingBinder.DESCRIPTOR);
        }

        public static IMultiTaskingBinder asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMultiTaskingBinder.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMultiTaskingBinder)) {
                return (IMultiTaskingBinder) queryLocalInterface;
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
                    return "registerDexTransientDelayListener";
                case 72:
                    return "setInDesktopWindowing";
                case 73:
                    return "inDesktopWindowing";
                case 74:
                    return "enableHighResolutionsForExternalDesktop";
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean exitMultiWindow = exitMultiWindow(readStrongBinder, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(exitMultiWindow);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int multiWindowModeStates = getMultiWindowModeStates(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(multiWindowModeStates);
                    return true;
                case 3:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean supportsMultiWindow = supportsMultiWindow(readStrongBinder2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsMultiWindow);
                    return true;
                case 4:
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int resizeMode = getResizeMode(activityInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(resizeMode);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAllowedMultiWindowPackage = isAllowedMultiWindowPackage(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllowedMultiWindowPackage);
                    return true;
                case 6:
                    List<String> allowedMultiWindowPackageList = getAllowedMultiWindowPackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedMultiWindowPackageList);
                    return true;
                case 7:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isMultiWindowBlockListApp = isMultiWindowBlockListApp(readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiWindowBlockListApp);
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
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMultiWindowEnabledForUser(readString3, readString4, readBoolean2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IRemoteAppTransitionListener asInterface = IRemoteAppTransitionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteAppTransitionListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IRemoteAppTransitionListener asInterface2 = IRemoteAppTransitionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAppTransitionListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAllTasksResizable = isAllTasksResizable(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllTasksResizable);
                    return true;
                case 14:
                    IFreeformCallback asInterface3 = IFreeformCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerFreeformCallback(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IFreeformCallback asInterface4 = IFreeformCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterFreeformCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt6 = parcel.readInt();
                    PointF pointF = (PointF) parcel.readTypedObject(PointF.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFreeformMinimizeAnimationEnd(readInt6, pointF);
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
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice visibleTasks = getVisibleTasks(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visibleTasks, 1);
                    return true;
                case 21:
                    int multiSplitFlags = getMultiSplitFlags();
                    parcel2.writeNoException();
                    parcel2.writeInt(multiSplitFlags);
                    return true;
                case 22:
                    String readString5 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean embedActivityPackageEnabled = getEmbedActivityPackageEnabled(readString5, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(embedActivityPackageEnabled);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEmbedActivityPackageEnabled(readString6, readBoolean3, readInt9);
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
                    String readString7 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int splitActivityPackageEnabled = getSplitActivityPackageEnabled(readString7, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(splitActivityPackageEnabled);
                    return true;
                case 27:
                    String readString8 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSplitActivityPackageEnabled(readString8, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCornerGestureEnabledWithSettings(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isValidCornerGesture = isValidCornerGesture(motionEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValidCornerGesture);
                    return true;
                case 30:
                    boolean isCornerGestureRunning = isCornerGestureRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCornerGestureRunning);
                    return true;
                case 31:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice taskInfoFromPackageName = getTaskInfoFromPackageName(readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskInfoFromPackageName, 1);
                    return true;
                case 32:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeFocusedTask = removeFocusedTask(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeFocusedTask);
                    return true;
                case 33:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimizeTaskById = minimizeTaskById(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimizeTaskById);
                    return true;
                case 34:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimizeAllTasks = minimizeAllTasks(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimizeAllTasks);
                    return true;
                case 35:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimizeAllTasksByRecents = minimizeAllTasksByRecents(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimizeAllTasksByRecents);
                    return true;
                case 36:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimizeTaskToSpecificPosition = minimizeTaskToSpecificPosition(readInt17, readBoolean5, readInt18, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimizeTaskToSpecificPosition);
                    return true;
                case 37:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSplitImmersiveMode(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean isSplitImmersiveModeEnabled = isSplitImmersiveModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSplitImmersiveModeEnabled);
                    return true;
                case 39:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    saveFreeformBounds(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNaviStarSplitImmersiveMode(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStayFocusActivityEnabled(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStayFocusAndTopResumedActivityEnabled(readBoolean9, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isVisibleTaskInDexDisplay = isVisibleTaskInDexDisplay(pendingIntent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVisibleTaskInDexDisplay);
                    return true;
                case 44:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVisibleTaskByTaskIdInDexDisplay = isVisibleTaskByTaskIdInDexDisplay(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVisibleTaskByTaskIdInDexDisplay);
                    return true;
                case 45:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    boolean shouldDeferEnterSplit = shouldDeferEnterSplit(createTypedArrayList, readArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldDeferEnterSplit);
                    return true;
                case 46:
                    boolean hasMinimizedToggleTasks = hasMinimizedToggleTasks();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasMinimizedToggleTasks);
                    return true;
                case 47:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int dexTaskInfoFlags = getDexTaskInfoFlags(readStrongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeInt(dexTaskInfoFlags);
                    return true;
                case 48:
                    int readInt22 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBoostFreeformTaskLayer(readInt22, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    toggleFreeformWindowingModeForDex(windowContainerToken);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    boolean supportMultiSplitAppMinimumSize = supportMultiSplitAppMinimumSize();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportMultiSplitAppMinimumSize);
                    return true;
                case 51:
                    updateMultiSplitAppMinimumSize();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean isDismissedFlexPanelMode = isDismissedFlexPanelMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDismissedFlexPanelMode);
                    return true;
                case 53:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBlockedMinimizeFreeformEnable(readBoolean12);
                    return true;
                case 54:
                    int readInt23 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCustomDensityEnabled(readInt23, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnsureLaunchSplitEnabled(readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyDragSplitAppIconHasDrawable(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SurfaceFreezerSnapshot surfaceFreezerSnapshot = getSurfaceFreezerSnapshot(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(surfaceFreezerSnapshot, 1);
                    return true;
                case 58:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean startNaturalSwitching = startNaturalSwitching(readStrongBinder4, readStrongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startNaturalSwitching);
                    return true;
                case 59:
                    finishNaturalSwitching();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean preventNaturalSwitching = preventNaturalSwitching(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(preventNaturalSwitching);
                    return true;
                case 61:
                    boolean isFlexPanelRunning = isFlexPanelRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFlexPanelRunning);
                    return true;
                case 62:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissSplitTask(readStrongBinder6, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    changeToHorizontalSplitLayout(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initDockingBounds(rect, rect2, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCandidateTask(readInt27);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int calculateMaxWidth = calculateMaxWidth(readInt28, readInt29, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(calculateMaxWidth);
                    return true;
                case 67:
                    int readInt31 = parcel.readInt();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizeOtherTaskIfNeeded(readInt31, rect3);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAllDockingTasks(readString10);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean z = toggleFreeformWindowingMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 70:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    startAssistantActivityToSplit(intent, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    IDexTransientCaptionDelayListener asInterface5 = IDexTransientCaptionDelayListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDexTransientDelayListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInDesktopWindowing(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    boolean inDesktopWindowing = inDesktopWindowing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(inDesktopWindowing);
                    return true;
                case 74:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableHighResolutionsForExternalDesktop(readBoolean18);
                    parcel2.writeNoException();
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getMultiWindowModeStates(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean supportsMultiWindow(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getResizeMode(ActivityInfo activityInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(activityInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isAllowedMultiWindowPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getAllowedMultiWindowPackageList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isMultiWindowBlockListApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public StringParceledListSlice getMultiWindowBlockListApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StringParceledListSlice) obtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public StringParceledListSlice getMWDisableRequesters() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StringParceledListSlice) obtain2.readTypedObject(StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setMultiWindowEnabledForUser(String str, String str2, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAppTransitionListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void unregisterRemoteAppTransitionListener(IRemoteAppTransitionListener iRemoteAppTransitionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAppTransitionListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isAllTasksResizable(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongInterface(iFreeformCallback);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongInterface(iFreeformCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointF, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void reportFreeformContainerPoint(PointF pointF) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(pointF, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public PointF getFreeformContainerPoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PointF) obtain2.readTypedObject(PointF.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getVisibleTasks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getMultiSplitFlags() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean getEmbedActivityPackageEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setEmbedActivityPackageEnabled(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getSupportEmbedActivityPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public List<String> getSplitActivityAllowPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getSplitActivityPackageEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setSplitActivityPackageEnabled(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCornerGestureEnabledWithSettings(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isValidCornerGesture(MotionEvent motionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isCornerGestureRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public ParceledListSlice getTaskInfoFromPackageName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean removeFocusedTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeTaskById(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeAllTasks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeAllTasksByRecents(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean minimizeTaskToSpecificPosition(int i, boolean z, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setSplitImmersiveMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isSplitImmersiveModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void saveFreeformBounds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setNaviStarSplitImmersiveMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setStayFocusActivityEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setStayFocusAndTopResumedActivityEnabled(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isVisibleTaskInDexDisplay(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isVisibleTaskByTaskIdInDexDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean shouldDeferEnterSplit(List<PendingIntent> list, List list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeList(list2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean hasMinimizedToggleTasks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int getDexTaskInfoFlags(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setBoostFreeformTaskLayer(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void toggleFreeformWindowingModeForDex(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean supportMultiSplitAppMinimumSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void updateMultiSplitAppMinimumSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isDismissedFlexPanelMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setBlockedMinimizeFreeformEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCustomDensityEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setEnsureLaunchSplitEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void notifyDragSplitAppIconHasDrawable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public SurfaceFreezerSnapshot getSurfaceFreezerSnapshot(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SurfaceFreezerSnapshot) obtain2.readTypedObject(SurfaceFreezerSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean startNaturalSwitching(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void finishNaturalSwitching() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean preventNaturalSwitching(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean isFlexPanelRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void dismissSplitTask(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void changeToHorizontalSplitLayout(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void initDockingBounds(Rect rect, Rect rect2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(rect2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setCandidateTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public int calculateMaxWidth(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void resizeOtherTaskIfNeeded(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void clearAllDockingTasks(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean toggleFreeformWindowingMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void startAssistantActivityToSplit(Intent intent, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void registerDexTransientDelayListener(IDexTransientCaptionDelayListener iDexTransientCaptionDelayListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeStrongInterface(iDexTransientCaptionDelayListener);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void setInDesktopWindowing(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public boolean inDesktopWindowing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IMultiTaskingBinder
            public void enableHighResolutionsForExternalDesktop(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMultiTaskingBinder.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
