package android.app;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityClientController;
import android.app.IActivityController;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IAssistDataReceiver;
import android.app.IScreenCaptureObserver;
import android.app.ITaskStackListener;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionSession;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.window.BackAnimationAdapter;
import android.window.BackNavigationInfo;
import android.window.IWindowOrganizerController;
import android.window.RemoteTransition;
import android.window.SplashScreenView;
import android.window.TaskSnapshot;
import com.android.internal.app.IVoiceInteractor;
import com.samsung.android.core.CompatChangeablePackageInfo;
import com.samsung.android.core.IFoldStarManager;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.IMultiTaskingBinder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IActivityTaskManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IActivityTaskManager";

    public static class Default implements IActivityTaskManager {
        @Override // android.app.IActivityTaskManager
        public int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean canBeUniversalResizeable(ApplicationInfo applicationInfo) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void cancelTaskWindowTransition(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void clearAppLockedUnLockedApp() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean clearRecentTasks(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void detachNavigationBarFromApp(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void focusTopTask(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public IActivityClientController getActivityClientController() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getAppCompatAlignment() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public String getAppLockedCheckAction() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public String getAppLockedLockType() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public List<String> getAppLockedPackageList() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Point getAppTaskThumbnailSize() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public List<IBinder> getAppTasks(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public String getApplockLockedAppsClass() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public String getApplockLockedAppsPackage() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getApplockType() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public Bundle getAssistContextExtras(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getCoverLauncherAppCompatAlignment() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public List<String> getCoverLauncherAvailableAppList(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Map getCoverLauncherEnabledAppList(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Map getCoverLauncherEnabledAppListByType(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getCutoutPolicy(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int getCutoutPolicyForSettings(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public IFoldStarManager getFoldStarManagerService() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getFrontActivityScreenCompatMode() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int getLastResumedActivityUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getOrientationControlPolicy(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public boolean getPackageAskScreenCompat(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public String getPackageFeatureInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getPackageScreenCompatMode(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Bitmap getResumedTaskThumbnail(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public String getScpmVersion() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public String getSsecureHiddenAppsPackages() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Rect getTaskBounds(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public TaskSnapshot getTaskSnapshotLowResolution(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int getUserOrSystemMinAspectRatioOverrideCode(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public String getVoiceInteractorPackageName(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public IWindowOrganizerController getWindowOrganizerController() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isAppLockedPackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isAppLockedVerifying(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isApplockEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isAssistDataAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isPackageEnabledForCoverLauncher(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isPackageEnabledInInnerAppListForCoverLauncher(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void keyguardGoingAway(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void moveRootTaskToDisplay(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void notifyPerformStop(String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void preloadRecentsActivity(Intent intent) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registKeyEventListener(IKeyEventListener iKeyEventListener) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean registerBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeAllTasksInRootTask(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeAllVisibleRecentTasks() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeAllVisibleRecentTasksExt(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeRootTasksInWindowingModes(int[] iArr) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeRootTasksWithActivityTypes(int[] iArr) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean removeTask(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean removeTaskWithFlags(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistContextExtrasFromCapture(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2, boolean z3) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistDataForTask(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAssistDataForTaskFromCapture(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void requestOpenInBrowserEducation(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void resetUserPackageSettings(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void resumeAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void scheduleRecomputeConfigurationLocked() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, long j, Map<String, String> map) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setAdaptiveAppByDefaultOverride(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setAppCompatAlignment(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setAppLockedUnLockPackage(String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setAppLockedVerifying(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setApplockEnabled(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setApplockLockedAppsClass(String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setApplockLockedAppsPackage(String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setApplockType(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setCoverLauncherAppCompatAlignment(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int setCoverLauncherPackageDisabled(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int setCoverLauncherPackageEnabled(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void setCutoutPolicy(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setDisallowWhenLandscape(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setFocusedRootTask(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setFocusedTask(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setFrontActivityScreenCompatMode(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setLockScreenShown(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setOrientationControlDefault(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setOrientationControlPolicy(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setPackageAskScreenCompat(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setPersistentVrThread(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setSsecureHiddenAppsPackages(String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean setTaskIsPerceptible(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void setTaskResizeable(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setUseLetterbox(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setUserMinAspectRatioOverrideCode(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void setVrThread(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int startActivities(IApplicationThread iApplicationThread, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivity(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityAsCaller(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, boolean z, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void startActivityForCoverLauncher(Intent intent, String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityFromGameSession(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, Intent intent, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public int startActivityWithConfig(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, Configuration configuration, Bundle bundle, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int startAssistantActivity(String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public BackNavigationInfo startBackNavigation(RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void startSystemLockTaskMode(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public int startVoiceActivity(String str, String str2, int i, int i2, Intent intent, String str3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityTaskManager
        public void stopAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void stopSystemLockTaskMode() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean supportsLocalVoiceInteraction() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void suppressResizeConfigChanges(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public TaskSnapshot takeTaskSnapshot(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityTaskManager
        public void unhandledBack() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void unregisterBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void unregisterScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void updateActiveRecents(int i) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public boolean updateConfiguration(Configuration configuration) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityTaskManager
        public void updateLockTaskFeatures(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
        }
    }

    int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException;

    void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException;

    boolean canBeUniversalResizeable(ApplicationInfo applicationInfo) throws RemoteException;

    void cancelTaskWindowTransition(int i) throws RemoteException;

    void clearAppLockedUnLockedApp() throws RemoteException;

    void clearLaunchParamsForPackages(List<String> list) throws RemoteException;

    boolean clearRecentTasks(int i) throws RemoteException;

    void detachNavigationBarFromApp(IBinder iBinder) throws RemoteException;

    void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException;

    void focusTopTask(int i) throws RemoteException;

    IActivityClientController getActivityClientController() throws RemoteException;

    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException;

    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws RemoteException;

    int getAppCompatAlignment() throws RemoteException;

    String getAppLockedCheckAction() throws RemoteException;

    String getAppLockedLockType() throws RemoteException;

    List<String> getAppLockedPackageList() throws RemoteException;

    Point getAppTaskThumbnailSize() throws RemoteException;

    List<IBinder> getAppTasks(String str) throws RemoteException;

    String getApplockLockedAppsClass() throws RemoteException;

    String getApplockLockedAppsPackage() throws RemoteException;

    int getApplockType() throws RemoteException;

    Bundle getAssistContextExtras(int i) throws RemoteException;

    ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(String str, int i) throws RemoteException;

    int getCoverLauncherAppCompatAlignment() throws RemoteException;

    List<String> getCoverLauncherAvailableAppList(int i) throws RemoteException;

    Map getCoverLauncherEnabledAppList(int i) throws RemoteException;

    Map getCoverLauncherEnabledAppListByType(int i, int i2) throws RemoteException;

    int getCutoutPolicy(int i, String str) throws RemoteException;

    int getCutoutPolicyForSettings(int i, String str) throws RemoteException;

    ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException;

    ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException;

    IFoldStarManager getFoldStarManagerService() throws RemoteException;

    int getFrontActivityScreenCompatMode() throws RemoteException;

    int getLastResumedActivityUserId() throws RemoteException;

    int getLockTaskModeState() throws RemoteException;

    IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException;

    int getOrientationControlPolicy(int i, String str) throws RemoteException;

    boolean getPackageAskScreenCompat(String str) throws RemoteException;

    String getPackageFeatureInfo(String str) throws RemoteException;

    int getPackageScreenCompatMode(String str) throws RemoteException;

    ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws RemoteException;

    Bitmap getResumedTaskThumbnail(int i) throws RemoteException;

    ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws RemoteException;

    ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws RemoteException;

    String getScpmVersion() throws RemoteException;

    String getSsecureHiddenAppsPackages() throws RemoteException;

    Rect getTaskBounds(int i) throws RemoteException;

    Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException;

    TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException;

    TaskSnapshot getTaskSnapshotLowResolution(int i) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws RemoteException;

    int getUserOrSystemMinAspectRatioOverrideCode(String str, int i) throws RemoteException;

    String getVoiceInteractorPackageName(IBinder iBinder) throws RemoteException;

    IWindowOrganizerController getWindowOrganizerController() throws RemoteException;

    boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) throws RemoteException;

    boolean isAppLockedPackage(String str) throws RemoteException;

    boolean isAppLockedVerifying(String str) throws RemoteException;

    boolean isApplockEnabled() throws RemoteException;

    boolean isAssistDataAllowed() throws RemoteException;

    boolean isInLockTaskMode() throws RemoteException;

    boolean isPackageEnabledForCoverLauncher(String str, int i) throws RemoteException;

    boolean isPackageEnabledInInnerAppListForCoverLauncher(String str, int i) throws RemoteException;

    boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) throws RemoteException;

    boolean isTopActivityImmersive() throws RemoteException;

    void keyguardGoingAway(int i) throws RemoteException;

    void moveRootTaskToDisplay(int i, int i2) throws RemoteException;

    void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException;

    void notifyPerformStop(String str) throws RemoteException;

    void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) throws RemoteException;

    void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws RemoteException;

    void preloadRecentsActivity(Intent intent) throws RemoteException;

    void registKeyEventListener(IKeyEventListener iKeyEventListener) throws RemoteException;

    boolean registerBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException;

    void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) throws RemoteException;

    void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

    void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) throws RemoteException;

    void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException;

    void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException;

    void removeAllTasksInRootTask(int i) throws RemoteException;

    void removeAllVisibleRecentTasks() throws RemoteException;

    void removeAllVisibleRecentTasksExt(boolean z, boolean z2) throws RemoteException;

    void removeRootTasksInWindowingModes(int[] iArr) throws RemoteException;

    void removeRootTasksWithActivityTypes(int[] iArr) throws RemoteException;

    boolean removeTask(int i) throws RemoteException;

    boolean removeTaskWithFlags(int i, int i2) throws RemoteException;

    void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException;

    boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    boolean requestAssistContextExtrasFromCapture(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2, boolean z3) throws RemoteException;

    boolean requestAssistDataForTask(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z) throws RemoteException;

    boolean requestAssistDataForTaskFromCapture(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z, boolean z2) throws RemoteException;

    boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) throws RemoteException;

    void requestOpenInBrowserEducation(IBinder iBinder) throws RemoteException;

    void resetUserPackageSettings(int i, int i2) throws RemoteException;

    void resizeTask(int i, Rect rect, int i2) throws RemoteException;

    void resumeAppSwitches() throws RemoteException;

    void scheduleRecomputeConfigurationLocked() throws RemoteException;

    void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, long j, Map<String, String> map) throws RemoteException;

    void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) throws RemoteException;

    void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException;

    void setAdaptiveAppByDefaultOverride(String str, boolean z) throws RemoteException;

    void setAppCompatAlignment(int i) throws RemoteException;

    void setAppLockedUnLockPackage(String str) throws RemoteException;

    void setAppLockedVerifying(String str, boolean z) throws RemoteException;

    void setApplockEnabled(boolean z) throws RemoteException;

    void setApplockLockedAppsClass(String str) throws RemoteException;

    void setApplockLockedAppsPackage(String str) throws RemoteException;

    void setApplockType(int i) throws RemoteException;

    void setCoverLauncherAppCompatAlignment(int i) throws RemoteException;

    int setCoverLauncherPackageDisabled(String str, int i) throws RemoteException;

    int setCoverLauncherPackageEnabled(String str, int i) throws RemoteException;

    void setCutoutPolicy(int i, String str, int i2) throws RemoteException;

    void setDisallowWhenLandscape(boolean z) throws RemoteException;

    void setFocusedRootTask(int i) throws RemoteException;

    void setFocusedTask(int i) throws RemoteException;

    void setFrontActivityScreenCompatMode(int i) throws RemoteException;

    void setLockScreenShown(boolean z, boolean z2) throws RemoteException;

    void setOrientationControlDefault(boolean z) throws RemoteException;

    void setOrientationControlPolicy(int i, String str, int i2) throws RemoteException;

    void setPackageAskScreenCompat(String str, boolean z) throws RemoteException;

    void setPackageScreenCompatMode(String str, int i) throws RemoteException;

    void setPersistentVrThread(int i) throws RemoteException;

    void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) throws RemoteException;

    void setSsecureHiddenAppsPackages(String str) throws RemoteException;

    boolean setTaskIsPerceptible(int i, boolean z) throws RemoteException;

    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setUseLetterbox(boolean z) throws RemoteException;

    void setUserMinAspectRatioOverrideCode(String str, int i, int i2) throws RemoteException;

    void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws RemoteException;

    void setVrThread(int i) throws RemoteException;

    int startActivities(IApplicationThread iApplicationThread, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) throws RemoteException;

    int startActivity(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException;

    WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    int startActivityAsCaller(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, boolean z, int i3) throws RemoteException;

    int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    void startActivityForCoverLauncher(Intent intent, String str) throws RemoteException;

    void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) throws RemoteException;

    int startActivityFromGameSession(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, Intent intent, int i3, int i4) throws RemoteException;

    int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

    int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) throws RemoteException;

    int startActivityWithConfig(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, Configuration configuration, Bundle bundle, int i3) throws RemoteException;

    void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) throws RemoteException;

    int startAssistantActivity(String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) throws RemoteException;

    BackNavigationInfo startBackNavigation(RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) throws RemoteException;

    boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException;

    void startSystemLockTaskMode(int i) throws RemoteException;

    int startVoiceActivity(String str, String str2, int i, int i2, Intent intent, String str3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) throws RemoteException;

    void stopAppSwitches() throws RemoteException;

    void stopSystemLockTaskMode() throws RemoteException;

    boolean supportsLocalVoiceInteraction() throws RemoteException;

    void suppressResizeConfigChanges(boolean z) throws RemoteException;

    TaskSnapshot takeTaskSnapshot(int i, boolean z) throws RemoteException;

    void unhandledBack() throws RemoteException;

    void unregisterBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException;

    void unregisterScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException;

    void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void updateActiveRecents(int i) throws RemoteException;

    boolean updateConfiguration(Configuration configuration) throws RemoteException;

    void updateLockTaskFeatures(int i, int i2) throws RemoteException;

    void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityTaskManager {
        static final int TRANSACTION_addAppTask = 43;
        static final int TRANSACTION_alwaysShowUnsupportedCompileSdkWarning = 82;
        static final int TRANSACTION_canBeUniversalResizeable = 31;
        static final int TRANSACTION_cancelTaskWindowTransition = 73;
        static final int TRANSACTION_clearAppLockedUnLockedApp = 134;
        static final int TRANSACTION_clearLaunchParamsForPackages = 93;
        static final int TRANSACTION_clearRecentTasks = 97;
        static final int TRANSACTION_detachNavigationBarFromApp = 96;
        static final int TRANSACTION_finishVoiceTask = 42;
        static final int TRANSACTION_focusTopTask = 35;
        static final int TRANSACTION_getActivityClientController = 17;
        static final int TRANSACTION_getAllRootTaskInfos = 55;
        static final int TRANSACTION_getAllRootTaskInfosOnDisplay = 57;
        static final int TRANSACTION_getAppCompatAlignment = 118;
        static final int TRANSACTION_getAppLockedCheckAction = 136;
        static final int TRANSACTION_getAppLockedLockType = 135;
        static final int TRANSACTION_getAppLockedPackageList = 131;
        static final int TRANSACTION_getAppTaskThumbnailSize = 44;
        static final int TRANSACTION_getAppTasks = 39;
        static final int TRANSACTION_getApplockLockedAppsClass = 145;
        static final int TRANSACTION_getApplockLockedAppsPackage = 144;
        static final int TRANSACTION_getApplockType = 146;
        static final int TRANSACTION_getAssistContextExtras = 60;
        static final int TRANSACTION_getCompatChangeablePackageInfoList = 106;
        static final int TRANSACTION_getCoverLauncherAppCompatAlignment = 120;
        static final int TRANSACTION_getCoverLauncherAvailableAppList = 150;
        static final int TRANSACTION_getCoverLauncherEnabledAppList = 151;
        static final int TRANSACTION_getCoverLauncherEnabledAppListByType = 152;
        static final int TRANSACTION_getCutoutPolicy = 127;
        static final int TRANSACTION_getCutoutPolicyForSettings = 128;
        static final int TRANSACTION_getDeviceConfigurationInfo = 72;
        static final int TRANSACTION_getFocusedRootTaskInfo = 33;
        static final int TRANSACTION_getFoldStarManagerService = 105;
        static final int TRANSACTION_getFrontActivityScreenCompatMode = 18;
        static final int TRANSACTION_getLastResumedActivityUserId = 76;
        static final int TRANSACTION_getLockTaskModeState = 38;
        static final int TRANSACTION_getMultiTaskingBinder = 104;
        static final int TRANSACTION_getOrientationControlPolicy = 113;
        static final int TRANSACTION_getPackageAskScreenCompat = 91;
        static final int TRANSACTION_getPackageFeatureInfo = 109;
        static final int TRANSACTION_getPackageScreenCompatMode = 89;
        static final int TRANSACTION_getRecentTasks = 28;
        static final int TRANSACTION_getResumedTaskThumbnail = 125;
        static final int TRANSACTION_getRootTaskInfo = 56;
        static final int TRANSACTION_getRootTaskInfoOnDisplay = 58;
        static final int TRANSACTION_getScpmVersion = 108;
        static final int TRANSACTION_getSsecureHiddenAppsPackages = 148;
        static final int TRANSACTION_getTaskBounds = 34;
        static final int TRANSACTION_getTaskDescriptionIcon = 46;
        static final int TRANSACTION_getTaskSnapshot = 74;
        static final int TRANSACTION_getTaskSnapshotLowResolution = 160;
        static final int TRANSACTION_getTasks = 26;
        static final int TRANSACTION_getUserOrSystemMinAspectRatioOverrideCode = 116;
        static final int TRANSACTION_getVoiceInteractorPackageName = 9;
        static final int TRANSACTION_getWindowOrganizerController = 69;
        static final int TRANSACTION_isActivityStartAllowedOnDisplay = 15;
        static final int TRANSACTION_isAppLockedPackage = 133;
        static final int TRANSACTION_isAppLockedVerifying = 138;
        static final int TRANSACTION_isApplockEnabled = 147;
        static final int TRANSACTION_isAssistDataAllowed = 63;
        static final int TRANSACTION_isInLockTaskMode = 37;
        static final int TRANSACTION_isPackageEnabledForCoverLauncher = 153;
        static final int TRANSACTION_isPackageEnabledInInnerAppListForCoverLauncher = 154;
        static final int TRANSACTION_isPackageSettingsEnabledForCoverLauncher = 155;
        static final int TRANSACTION_isTopActivityImmersive = 29;
        static final int TRANSACTION_keyguardGoingAway = 67;
        static final int TRANSACTION_moveRootTaskToDisplay = 51;
        static final int TRANSACTION_moveTaskToFront = 27;
        static final int TRANSACTION_moveTaskToRootTask = 52;
        static final int TRANSACTION_notifyPerformStop = 149;
        static final int TRANSACTION_onPictureInPictureUiStateChanged = 95;
        static final int TRANSACTION_onSplashScreenViewCopyFinished = 94;
        static final int TRANSACTION_preloadRecentsActivity = 14;
        static final int TRANSACTION_registKeyEventListener = 129;
        static final int TRANSACTION_registerBackgroundActivityStartCallback = 100;
        static final int TRANSACTION_registerRemoteAnimationForNextActivityStart = 79;
        static final int TRANSACTION_registerRemoteAnimationsForDisplay = 81;
        static final int TRANSACTION_registerRemoteTransitionForNextActivityStart = 80;
        static final int TRANSACTION_registerScreenCaptureObserver = 102;
        static final int TRANSACTION_registerTaskStackListener = 47;
        static final int TRANSACTION_releaseSomeActivities = 45;
        static final int TRANSACTION_removeAllTasksInRootTask = 162;
        static final int TRANSACTION_removeAllVisibleRecentTasks = 25;
        static final int TRANSACTION_removeAllVisibleRecentTasksExt = 161;
        static final int TRANSACTION_removeRootTasksInWindowingModes = 53;
        static final int TRANSACTION_removeRootTasksWithActivityTypes = 54;
        static final int TRANSACTION_removeTask = 22;
        static final int TRANSACTION_removeTaskWithFlags = 24;
        static final int TRANSACTION_reportAssistContextExtras = 30;
        static final int TRANSACTION_requestAssistContextExtras = 61;
        static final int TRANSACTION_requestAssistContextExtrasFromCapture = 65;
        static final int TRANSACTION_requestAssistDataForTask = 64;
        static final int TRANSACTION_requestAssistDataForTaskFromCapture = 66;
        static final int TRANSACTION_requestAutofillData = 62;
        static final int TRANSACTION_requestOpenInBrowserEducation = 71;
        static final int TRANSACTION_resetUserPackageSettings = 107;
        static final int TRANSACTION_resizeTask = 50;
        static final int TRANSACTION_resumeAppSwitches = 86;
        static final int TRANSACTION_scheduleRecomputeConfigurationLocked = 122;
        static final int TRANSACTION_sendSaLoggingBroadcast = 123;
        static final int TRANSACTION_sendSaLoggingBroadcastForSetting = 124;
        static final int TRANSACTION_setActivityController = 87;
        static final int TRANSACTION_setAdaptiveAppByDefaultOverride = 110;
        static final int TRANSACTION_setAppCompatAlignment = 119;
        static final int TRANSACTION_setAppLockedUnLockPackage = 132;
        static final int TRANSACTION_setAppLockedVerifying = 137;
        static final int TRANSACTION_setApplockEnabled = 142;
        static final int TRANSACTION_setApplockLockedAppsClass = 140;
        static final int TRANSACTION_setApplockLockedAppsPackage = 139;
        static final int TRANSACTION_setApplockType = 141;
        static final int TRANSACTION_setCoverLauncherAppCompatAlignment = 121;
        static final int TRANSACTION_setCoverLauncherPackageDisabled = 157;
        static final int TRANSACTION_setCoverLauncherPackageEnabled = 156;
        static final int TRANSACTION_setCutoutPolicy = 126;
        static final int TRANSACTION_setDisallowWhenLandscape = 115;
        static final int TRANSACTION_setFocusedRootTask = 32;
        static final int TRANSACTION_setFocusedTask = 20;
        static final int TRANSACTION_setFrontActivityScreenCompatMode = 19;
        static final int TRANSACTION_setLockScreenShown = 59;
        static final int TRANSACTION_setOrientationControlDefault = 114;
        static final int TRANSACTION_setOrientationControlPolicy = 112;
        static final int TRANSACTION_setPackageAskScreenCompat = 92;
        static final int TRANSACTION_setPackageScreenCompatMode = 90;
        static final int TRANSACTION_setPersistentVrThread = 84;
        static final int TRANSACTION_setRunningRemoteTransitionDelegate = 98;
        static final int TRANSACTION_setSsecureHiddenAppsPackages = 143;
        static final int TRANSACTION_setTaskIsPerceptible = 21;
        static final int TRANSACTION_setTaskResizeable = 49;
        static final int TRANSACTION_setUseLetterbox = 111;
        static final int TRANSACTION_setUserMinAspectRatioOverrideCode = 117;
        static final int TRANSACTION_setVoiceKeepAwake = 88;
        static final int TRANSACTION_setVrThread = 83;
        static final int TRANSACTION_startActivities = 2;
        static final int TRANSACTION_startActivity = 1;
        static final int TRANSACTION_startActivityAndWait = 6;
        static final int TRANSACTION_startActivityAsCaller = 13;
        static final int TRANSACTION_startActivityAsUser = 3;
        static final int TRANSACTION_startActivityForCoverLauncher = 158;
        static final int TRANSACTION_startActivityForCoverLauncherAsUser = 159;
        static final int TRANSACTION_startActivityFromGameSession = 11;
        static final int TRANSACTION_startActivityFromRecents = 12;
        static final int TRANSACTION_startActivityIntentSender = 5;
        static final int TRANSACTION_startActivityWithConfig = 7;
        static final int TRANSACTION_startAppLockService = 130;
        static final int TRANSACTION_startAssistantActivity = 10;
        static final int TRANSACTION_startBackNavigation = 99;
        static final int TRANSACTION_startNextMatchingActivity = 4;
        static final int TRANSACTION_startSystemLockTaskMode = 40;
        static final int TRANSACTION_startVoiceActivity = 8;
        static final int TRANSACTION_stopAppSwitches = 85;
        static final int TRANSACTION_stopSystemLockTaskMode = 41;
        static final int TRANSACTION_supportsLocalVoiceInteraction = 70;
        static final int TRANSACTION_suppressResizeConfigChanges = 68;
        static final int TRANSACTION_takeTaskSnapshot = 75;
        static final int TRANSACTION_unhandledBack = 16;
        static final int TRANSACTION_unregisterBackgroundActivityStartCallback = 101;
        static final int TRANSACTION_unregisterScreenCaptureObserver = 103;
        static final int TRANSACTION_unregisterTaskStackListener = 48;
        static final int TRANSACTION_updateActiveRecents = 23;
        static final int TRANSACTION_updateConfiguration = 77;
        static final int TRANSACTION_updateLockTaskFeatures = 78;
        static final int TRANSACTION_updateLockTaskPackages = 36;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 161;
        }

        public Stub() {
            attachInterface(this, IActivityTaskManager.DESCRIPTOR);
        }

        public static IActivityTaskManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IActivityTaskManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityTaskManager)) {
                return (IActivityTaskManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startActivity";
                case 2:
                    return "startActivities";
                case 3:
                    return "startActivityAsUser";
                case 4:
                    return "startNextMatchingActivity";
                case 5:
                    return "startActivityIntentSender";
                case 6:
                    return "startActivityAndWait";
                case 7:
                    return "startActivityWithConfig";
                case 8:
                    return "startVoiceActivity";
                case 9:
                    return "getVoiceInteractorPackageName";
                case 10:
                    return "startAssistantActivity";
                case 11:
                    return "startActivityFromGameSession";
                case 12:
                    return "startActivityFromRecents";
                case 13:
                    return "startActivityAsCaller";
                case 14:
                    return "preloadRecentsActivity";
                case 15:
                    return "isActivityStartAllowedOnDisplay";
                case 16:
                    return "unhandledBack";
                case 17:
                    return "getActivityClientController";
                case 18:
                    return "getFrontActivityScreenCompatMode";
                case 19:
                    return "setFrontActivityScreenCompatMode";
                case 20:
                    return "setFocusedTask";
                case 21:
                    return "setTaskIsPerceptible";
                case 22:
                    return "removeTask";
                case 23:
                    return "updateActiveRecents";
                case 24:
                    return "removeTaskWithFlags";
                case 25:
                    return "removeAllVisibleRecentTasks";
                case 26:
                    return "getTasks";
                case 27:
                    return "moveTaskToFront";
                case 28:
                    return "getRecentTasks";
                case 29:
                    return "isTopActivityImmersive";
                case 30:
                    return "reportAssistContextExtras";
                case 31:
                    return "canBeUniversalResizeable";
                case 32:
                    return "setFocusedRootTask";
                case 33:
                    return "getFocusedRootTaskInfo";
                case 34:
                    return "getTaskBounds";
                case 35:
                    return "focusTopTask";
                case 36:
                    return "updateLockTaskPackages";
                case 37:
                    return "isInLockTaskMode";
                case 38:
                    return "getLockTaskModeState";
                case 39:
                    return "getAppTasks";
                case 40:
                    return "startSystemLockTaskMode";
                case 41:
                    return "stopSystemLockTaskMode";
                case 42:
                    return "finishVoiceTask";
                case 43:
                    return "addAppTask";
                case 44:
                    return "getAppTaskThumbnailSize";
                case 45:
                    return "releaseSomeActivities";
                case 46:
                    return "getTaskDescriptionIcon";
                case 47:
                    return "registerTaskStackListener";
                case 48:
                    return "unregisterTaskStackListener";
                case 49:
                    return "setTaskResizeable";
                case 50:
                    return "resizeTask";
                case 51:
                    return "moveRootTaskToDisplay";
                case 52:
                    return "moveTaskToRootTask";
                case 53:
                    return "removeRootTasksInWindowingModes";
                case 54:
                    return "removeRootTasksWithActivityTypes";
                case 55:
                    return "getAllRootTaskInfos";
                case 56:
                    return "getRootTaskInfo";
                case 57:
                    return "getAllRootTaskInfosOnDisplay";
                case 58:
                    return "getRootTaskInfoOnDisplay";
                case 59:
                    return "setLockScreenShown";
                case 60:
                    return "getAssistContextExtras";
                case 61:
                    return "requestAssistContextExtras";
                case 62:
                    return "requestAutofillData";
                case 63:
                    return "isAssistDataAllowed";
                case 64:
                    return "requestAssistDataForTask";
                case 65:
                    return "requestAssistContextExtrasFromCapture";
                case 66:
                    return "requestAssistDataForTaskFromCapture";
                case 67:
                    return "keyguardGoingAway";
                case 68:
                    return "suppressResizeConfigChanges";
                case 69:
                    return "getWindowOrganizerController";
                case 70:
                    return "supportsLocalVoiceInteraction";
                case 71:
                    return "requestOpenInBrowserEducation";
                case 72:
                    return "getDeviceConfigurationInfo";
                case 73:
                    return "cancelTaskWindowTransition";
                case 74:
                    return "getTaskSnapshot";
                case 75:
                    return "takeTaskSnapshot";
                case 76:
                    return "getLastResumedActivityUserId";
                case 77:
                    return "updateConfiguration";
                case 78:
                    return "updateLockTaskFeatures";
                case 79:
                    return "registerRemoteAnimationForNextActivityStart";
                case 80:
                    return "registerRemoteTransitionForNextActivityStart";
                case 81:
                    return "registerRemoteAnimationsForDisplay";
                case 82:
                    return "alwaysShowUnsupportedCompileSdkWarning";
                case 83:
                    return "setVrThread";
                case 84:
                    return "setPersistentVrThread";
                case 85:
                    return "stopAppSwitches";
                case 86:
                    return "resumeAppSwitches";
                case 87:
                    return "setActivityController";
                case 88:
                    return "setVoiceKeepAwake";
                case 89:
                    return "getPackageScreenCompatMode";
                case 90:
                    return "setPackageScreenCompatMode";
                case 91:
                    return "getPackageAskScreenCompat";
                case 92:
                    return "setPackageAskScreenCompat";
                case 93:
                    return "clearLaunchParamsForPackages";
                case 94:
                    return "onSplashScreenViewCopyFinished";
                case 95:
                    return "onPictureInPictureUiStateChanged";
                case 96:
                    return "detachNavigationBarFromApp";
                case 97:
                    return "clearRecentTasks";
                case 98:
                    return "setRunningRemoteTransitionDelegate";
                case 99:
                    return "startBackNavigation";
                case 100:
                    return "registerBackgroundActivityStartCallback";
                case 101:
                    return "unregisterBackgroundActivityStartCallback";
                case 102:
                    return "registerScreenCaptureObserver";
                case 103:
                    return "unregisterScreenCaptureObserver";
                case 104:
                    return "getMultiTaskingBinder";
                case 105:
                    return "getFoldStarManagerService";
                case 106:
                    return "getCompatChangeablePackageInfoList";
                case 107:
                    return "resetUserPackageSettings";
                case 108:
                    return "getScpmVersion";
                case 109:
                    return "getPackageFeatureInfo";
                case 110:
                    return "setAdaptiveAppByDefaultOverride";
                case 111:
                    return "setUseLetterbox";
                case 112:
                    return "setOrientationControlPolicy";
                case 113:
                    return "getOrientationControlPolicy";
                case 114:
                    return "setOrientationControlDefault";
                case 115:
                    return "setDisallowWhenLandscape";
                case 116:
                    return "getUserOrSystemMinAspectRatioOverrideCode";
                case 117:
                    return "setUserMinAspectRatioOverrideCode";
                case 118:
                    return "getAppCompatAlignment";
                case 119:
                    return "setAppCompatAlignment";
                case 120:
                    return "getCoverLauncherAppCompatAlignment";
                case 121:
                    return "setCoverLauncherAppCompatAlignment";
                case 122:
                    return "scheduleRecomputeConfigurationLocked";
                case 123:
                    return "sendSaLoggingBroadcast";
                case 124:
                    return "sendSaLoggingBroadcastForSetting";
                case 125:
                    return "getResumedTaskThumbnail";
                case 126:
                    return "setCutoutPolicy";
                case 127:
                    return "getCutoutPolicy";
                case 128:
                    return "getCutoutPolicyForSettings";
                case 129:
                    return "registKeyEventListener";
                case 130:
                    return "startAppLockService";
                case 131:
                    return "getAppLockedPackageList";
                case 132:
                    return "setAppLockedUnLockPackage";
                case 133:
                    return "isAppLockedPackage";
                case 134:
                    return "clearAppLockedUnLockedApp";
                case 135:
                    return "getAppLockedLockType";
                case 136:
                    return "getAppLockedCheckAction";
                case 137:
                    return "setAppLockedVerifying";
                case 138:
                    return "isAppLockedVerifying";
                case 139:
                    return "setApplockLockedAppsPackage";
                case 140:
                    return "setApplockLockedAppsClass";
                case 141:
                    return "setApplockType";
                case 142:
                    return "setApplockEnabled";
                case 143:
                    return "setSsecureHiddenAppsPackages";
                case 144:
                    return "getApplockLockedAppsPackage";
                case 145:
                    return "getApplockLockedAppsClass";
                case 146:
                    return "getApplockType";
                case 147:
                    return "isApplockEnabled";
                case 148:
                    return "getSsecureHiddenAppsPackages";
                case 149:
                    return "notifyPerformStop";
                case 150:
                    return "getCoverLauncherAvailableAppList";
                case 151:
                    return "getCoverLauncherEnabledAppList";
                case 152:
                    return "getCoverLauncherEnabledAppListByType";
                case 153:
                    return "isPackageEnabledForCoverLauncher";
                case 154:
                    return "isPackageEnabledInInnerAppListForCoverLauncher";
                case 155:
                    return "isPackageSettingsEnabledForCoverLauncher";
                case 156:
                    return "setCoverLauncherPackageEnabled";
                case 157:
                    return "setCoverLauncherPackageDisabled";
                case 158:
                    return "startActivityForCoverLauncher";
                case 159:
                    return "startActivityForCoverLauncherAsUser";
                case 160:
                    return "getTaskSnapshotLowResolution";
                case 161:
                    return "removeAllVisibleRecentTasksExt";
                case 162:
                    return "removeAllTasksInRootTask";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IActivityTaskManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IActivityTaskManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString3 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivity = startActivity(asInterface, readString, readString2, intent, readString3, readStrongBinder, readString4, readInt, readInt2, profilerInfo, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivity);
                    return true;
                case 2:
                    IApplicationThread asInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivities = startActivities(asInterface2, readString5, readString6, intentArr, createStringArray, readStrongBinder2, bundle2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivities);
                    return true;
                case 3:
                    IApplicationThread asInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString9 = parcel.readString();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    String readString10 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    ProfilerInfo profilerInfo2 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsUser = startActivityAsUser(asInterface3, readString7, readString8, intent2, readString9, readStrongBinder3, readString10, readInt4, readInt5, profilerInfo2, bundle3, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsUser);
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startNextMatchingActivity = startNextMatchingActivity(readStrongBinder4, intent3, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startNextMatchingActivity);
                    return true;
                case 5:
                    IApplicationThread asInterface4 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IIntentSender asInterface5 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString11 = parcel.readString();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString12 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityIntentSender = startActivityIntentSender(asInterface4, asInterface5, readStrongBinder5, intent4, readString11, readStrongBinder6, readString12, readInt7, readInt8, readInt9, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityIntentSender);
                    return true;
                case 6:
                    IApplicationThread asInterface6 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString15 = parcel.readString();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    String readString16 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    ProfilerInfo profilerInfo3 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WaitResult startActivityAndWait = startActivityAndWait(asInterface6, readString13, readString14, intent5, readString15, readStrongBinder7, readString16, readInt10, readInt11, profilerInfo3, bundle6, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startActivityAndWait, 1);
                    return true;
                case 7:
                    IApplicationThread asInterface7 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString19 = parcel.readString();
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString20 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityWithConfig = startActivityWithConfig(asInterface7, readString17, readString18, intent6, readString19, readStrongBinder8, readString20, readInt13, readInt14, configuration, bundle7, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityWithConfig);
                    return true;
                case 8:
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    Intent intent7 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString23 = parcel.readString();
                    IVoiceInteractionSession asInterface8 = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    IVoiceInteractor asInterface9 = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    ProfilerInfo profilerInfo4 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startVoiceActivity = startVoiceActivity(readString21, readString22, readInt16, readInt17, intent7, readString23, asInterface8, asInterface9, readInt18, profilerInfo4, bundle8, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(startVoiceActivity);
                    return true;
                case 9:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String voiceInteractorPackageName = getVoiceInteractorPackageName(readStrongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceInteractorPackageName);
                    return true;
                case 10:
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString26 = parcel.readString();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startAssistantActivity = startAssistantActivity(readString24, readString25, readInt20, readInt21, intent8, readString26, bundle9, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAssistantActivity);
                    return true;
                case 11:
                    IApplicationThread asInterface10 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityFromGameSession = startActivityFromGameSession(asInterface10, readString27, readString28, readInt23, readInt24, intent9, readInt25, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromGameSession);
                    return true;
                case 12:
                    int readInt27 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityFromRecents = startActivityFromRecents(readInt27, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromRecents);
                    return true;
                case 13:
                    IApplicationThread asInterface11 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString29 = parcel.readString();
                    Intent intent10 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString30 = parcel.readString();
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    String readString31 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    ProfilerInfo profilerInfo5 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startActivityAsCaller = startActivityAsCaller(asInterface11, readString29, intent10, readString30, readStrongBinder10, readString31, readInt28, readInt29, profilerInfo5, bundle11, readBoolean, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityAsCaller);
                    return true;
                case 14:
                    Intent intent11 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    preloadRecentsActivity(intent11);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt31 = parcel.readInt();
                    Intent intent12 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString32 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isActivityStartAllowedOnDisplay = isActivityStartAllowedOnDisplay(readInt31, intent12, readString32, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityStartAllowedOnDisplay);
                    return true;
                case 16:
                    unhandledBack();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IActivityClientController activityClientController = getActivityClientController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(activityClientController);
                    return true;
                case 18:
                    int frontActivityScreenCompatMode = getFrontActivityScreenCompatMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(frontActivityScreenCompatMode);
                    return true;
                case 19:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFrontActivityScreenCompatMode(readInt33);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedTask(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt35 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean taskIsPerceptible = setTaskIsPerceptible(readInt35, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(taskIsPerceptible);
                    return true;
                case 22:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTask = removeTask(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTask);
                    return true;
                case 23:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateActiveRecents(readInt37);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTaskWithFlags = removeTaskWithFlags(readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTaskWithFlags);
                    return true;
                case 25:
                    removeAllVisibleRecentTasks();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt40 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> tasks = getTasks(readInt40, readBoolean3, readBoolean4, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 27:
                    IApplicationThread asInterface12 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString33 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    moveTaskToFront(asInterface12, readString33, readInt42, readInt43, bundle12);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<ActivityManager.RecentTaskInfo> recentTasks = getRecentTasks(readInt44, readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentTasks, 1);
                    return true;
                case 29:
                    boolean isTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopActivityImmersive);
                    return true;
                case 30:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    AssistStructure assistStructure = (AssistStructure) parcel.readTypedObject(AssistStructure.CREATOR);
                    AssistContent assistContent = (AssistContent) parcel.readTypedObject(AssistContent.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportAssistContextExtras(readStrongBinder11, bundle13, assistStructure, assistContent, uri);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canBeUniversalResizeable = canBeUniversalResizeable(applicationInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canBeUniversalResizeable);
                    return true;
                case 32:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(readInt47);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = getFocusedRootTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(focusedRootTaskInfo, 1);
                    return true;
                case 34:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect taskBounds = getTaskBounds(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 35:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    focusTopTask(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt50 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(readInt50, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    boolean isInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInLockTaskMode);
                    return true;
                case 38:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 39:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<IBinder> appTasks = getAppTasks(readString34);
                    parcel2.writeNoException();
                    parcel2.writeBinderList(appTasks);
                    return true;
                case 40:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(readInt51);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    stopSystemLockTaskMode();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IVoiceInteractionSession asInterface13 = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishVoiceTask(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    Intent intent13 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ActivityManager.TaskDescription taskDescription = (ActivityManager.TaskDescription) parcel.readTypedObject(ActivityManager.TaskDescription.CREATOR);
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addAppTask = addAppTask(readStrongBinder12, intent13, taskDescription, bitmap);
                    parcel2.writeNoException();
                    parcel2.writeInt(addAppTask);
                    return true;
                case 44:
                    Point appTaskThumbnailSize = getAppTaskThumbnailSize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appTaskThumbnailSize, 1);
                    return true;
                case 45:
                    IApplicationThread asInterface14 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseSomeActivities(asInterface14);
                    return true;
                case 46:
                    String readString35 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap taskDescriptionIcon = getTaskDescriptionIcon(readString35, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskDescriptionIcon, 1);
                    return true;
                case 47:
                    ITaskStackListener asInterface15 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ITaskStackListener asInterface16 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(readInt53, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt55 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(readInt55, rect, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveRootTaskToDisplay(readInt57, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(readInt59, readInt60, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksInWindowingModes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksWithActivityTypes(createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 56:
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = getRootTaskInfo(readInt61, readInt62);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfo, 1);
                    return true;
                case 57:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfosOnDisplay = getAllRootTaskInfosOnDisplay(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfosOnDisplay, 1);
                    return true;
                case 58:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityTaskManager.RootTaskInfo rootTaskInfoOnDisplay = getRootTaskInfoOnDisplay(readInt64, readInt65, readInt66);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfoOnDisplay, 1);
                    return true;
                case 59:
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockScreenShown(readBoolean6, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle assistContextExtras = getAssistContextExtras(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(assistContextExtras, 1);
                    return true;
                case 61:
                    int readInt68 = parcel.readInt();
                    IAssistDataReceiver asInterface17 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistContextExtras = requestAssistContextExtras(readInt68, asInterface17, bundle14, readStrongBinder13, readBoolean8, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistContextExtras);
                    return true;
                case 62:
                    IAssistDataReceiver asInterface18 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestAutofillData = requestAutofillData(asInterface18, bundle15, readStrongBinder14, readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAutofillData);
                    return true;
                case 63:
                    boolean isAssistDataAllowed = isAssistDataAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAssistDataAllowed);
                    return true;
                case 64:
                    IAssistDataReceiver asInterface19 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt70 = parcel.readInt();
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistDataForTask = requestAssistDataForTask(asInterface19, readInt70, readString36, readString37, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistDataForTask);
                    return true;
                case 65:
                    int readInt71 = parcel.readInt();
                    IAssistDataReceiver asInterface20 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistContextExtrasFromCapture = requestAssistContextExtrasFromCapture(readInt71, asInterface20, bundle16, readStrongBinder15, readBoolean11, readBoolean12, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistContextExtrasFromCapture);
                    return true;
                case 66:
                    IAssistDataReceiver asInterface21 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt72 = parcel.readInt();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean requestAssistDataForTaskFromCapture = requestAssistDataForTaskFromCapture(asInterface21, readInt72, readString38, readString39, readBoolean14, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistDataForTaskFromCapture);
                    return true;
                case 67:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    keyguardGoingAway(readInt73);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    IWindowOrganizerController windowOrganizerController = getWindowOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowOrganizerController);
                    return true;
                case 70:
                    boolean supportsLocalVoiceInteraction = supportsLocalVoiceInteraction();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsLocalVoiceInteraction);
                    return true;
                case 71:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    requestOpenInBrowserEducation(readStrongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    ConfigurationInfo deviceConfigurationInfo = getDeviceConfigurationInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceConfigurationInfo, 1);
                    return true;
                case 73:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(readInt74);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int readInt75 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot taskSnapshot = getTaskSnapshot(readInt75, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshot, 1);
                    return true;
                case 75:
                    int readInt76 = parcel.readInt();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot takeTaskSnapshot = takeTaskSnapshot(readInt76, readBoolean18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(takeTaskSnapshot, 1);
                    return true;
                case 76:
                    int lastResumedActivityUserId = getLastResumedActivityUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastResumedActivityUserId);
                    return true;
                case 77:
                    Configuration configuration2 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 78:
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateLockTaskFeatures(readInt77, readInt78);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    String readString40 = parcel.readString();
                    RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationForNextActivityStart(readString40, remoteAnimationAdapter, readStrongBinder17);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    String readString41 = parcel.readString();
                    RemoteAnimationAdapter remoteAnimationAdapter2 = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteTransitionForNextActivityStart(readString41, remoteAnimationAdapter2, readStrongBinder18, remoteTransition);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    int readInt79 = parcel.readInt();
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationsForDisplay(readInt79, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    alwaysShowUnsupportedCompileSdkWarning(componentName);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVrThread(readInt80);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(readInt81);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    stopAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 86:
                    resumeAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 87:
                    IActivityController asInterface22 = IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(asInterface22, readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    IVoiceInteractionSession asInterface23 = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoiceKeepAwake(asInterface23, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageScreenCompatMode = getPackageScreenCompatMode(readString42);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageScreenCompatMode);
                    return true;
                case 90:
                    String readString43 = parcel.readString();
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(readString43, readInt82);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean packageAskScreenCompat = getPackageAskScreenCompat(readString44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageAskScreenCompat);
                    return true;
                case 92:
                    String readString45 = parcel.readString();
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPackageAskScreenCompat(readString45, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    clearLaunchParamsForPackages(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int readInt83 = parcel.readInt();
                    SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable = (SplashScreenView.SplashScreenViewParcelable) parcel.readTypedObject(SplashScreenView.SplashScreenViewParcelable.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSplashScreenViewCopyFinished(readInt83, splashScreenViewParcelable);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    PictureInPictureUiState pictureInPictureUiState = (PictureInPictureUiState) parcel.readTypedObject(PictureInPictureUiState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPictureInPictureUiStateChanged(pictureInPictureUiState);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachNavigationBarFromApp(readStrongBinder19);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearRecentTasks = clearRecentTasks(readInt84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearRecentTasks);
                    return true;
                case 98:
                    IApplicationThread asInterface24 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRunningRemoteTransitionDelegate(asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    BackAnimationAdapter backAnimationAdapter = (BackAnimationAdapter) parcel.readTypedObject(BackAnimationAdapter.CREATOR);
                    parcel.enforceNoDataAvail();
                    BackNavigationInfo startBackNavigation = startBackNavigation(remoteCallback, backAnimationAdapter);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startBackNavigation, 1);
                    return true;
                case 100:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean registerBackgroundActivityStartCallback = registerBackgroundActivityStartCallback(readStrongBinder20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerBackgroundActivityStartCallback);
                    return true;
                case 101:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterBackgroundActivityStartCallback(readStrongBinder21);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    IScreenCaptureObserver asInterface25 = IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerScreenCaptureObserver(readStrongBinder22, asInterface25);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    IScreenCaptureObserver asInterface26 = IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenCaptureObserver(readStrongBinder23, asInterface26);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    IMultiTaskingBinder multiTaskingBinder = getMultiTaskingBinder();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiTaskingBinder);
                    return true;
                case 105:
                    IFoldStarManager foldStarManagerService = getFoldStarManagerService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(foldStarManagerService);
                    return true;
                case 106:
                    String readString46 = parcel.readString();
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<CompatChangeablePackageInfo> compatChangeablePackageInfoList = getCompatChangeablePackageInfoList(readString46, readInt85);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(compatChangeablePackageInfoList, 1);
                    return true;
                case 107:
                    int readInt86 = parcel.readInt();
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetUserPackageSettings(readInt86, readInt87);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String scpmVersion = getScpmVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(scpmVersion);
                    return true;
                case 109:
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String packageFeatureInfo = getPackageFeatureInfo(readString47);
                    parcel2.writeNoException();
                    parcel2.writeString(packageFeatureInfo);
                    return true;
                case 110:
                    String readString48 = parcel.readString();
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdaptiveAppByDefaultOverride(readString48, readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUseLetterbox(readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    int readInt88 = parcel.readInt();
                    String readString49 = parcel.readString();
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrientationControlPolicy(readInt88, readString49, readInt89);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    int readInt90 = parcel.readInt();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int orientationControlPolicy = getOrientationControlPolicy(readInt90, readString50);
                    parcel2.writeNoException();
                    parcel2.writeInt(orientationControlPolicy);
                    return true;
                case 114:
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOrientationControlDefault(readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisallowWhenLandscape(readBoolean25);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    String readString51 = parcel.readString();
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userOrSystemMinAspectRatioOverrideCode = getUserOrSystemMinAspectRatioOverrideCode(readString51, readInt91);
                    parcel2.writeNoException();
                    parcel2.writeInt(userOrSystemMinAspectRatioOverrideCode);
                    return true;
                case 117:
                    String readString52 = parcel.readString();
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserMinAspectRatioOverrideCode(readString52, readInt92, readInt93);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    int appCompatAlignment = getAppCompatAlignment();
                    parcel2.writeNoException();
                    parcel2.writeInt(appCompatAlignment);
                    return true;
                case 119:
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCompatAlignment(readInt94);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    int coverLauncherAppCompatAlignment = getCoverLauncherAppCompatAlignment();
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherAppCompatAlignment);
                    return true;
                case 121:
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCoverLauncherAppCompatAlignment(readInt95);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    scheduleRecomputeConfigurationLocked();
                    parcel2.writeNoException();
                    return true;
                case 123:
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt96 = parcel.readInt();
                    final HashMap hashMap = readInt96 < 0 ? null : new HashMap();
                    IntStream.range(0, readInt96).forEach(new IntConsumer() { // from class: android.app.IActivityTaskManager$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    sendSaLoggingBroadcast(readString53, readString54, readString55, readString56, readLong, hashMap);
                    return true;
                case 124:
                    String readString57 = parcel.readString();
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendSaLoggingBroadcastForSetting(readString57, readString58, readString59);
                    return true;
                case 125:
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap resumedTaskThumbnail = getResumedTaskThumbnail(readInt97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resumedTaskThumbnail, 1);
                    return true;
                case 126:
                    int readInt98 = parcel.readInt();
                    String readString60 = parcel.readString();
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCutoutPolicy(readInt98, readString60, readInt99);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int readInt100 = parcel.readInt();
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cutoutPolicy = getCutoutPolicy(readInt100, readString61);
                    parcel2.writeNoException();
                    parcel2.writeInt(cutoutPolicy);
                    return true;
                case 128:
                    int readInt101 = parcel.readInt();
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cutoutPolicyForSettings = getCutoutPolicyForSettings(readInt101, readString62);
                    parcel2.writeNoException();
                    parcel2.writeInt(cutoutPolicyForSettings);
                    return true;
                case 129:
                    IKeyEventListener asInterface27 = IKeyEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registKeyEventListener(asInterface27);
                    return true;
                case 130:
                    IBinder readStrongBinder24 = parcel.readStrongBinder();
                    Intent intent14 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    boolean readBoolean26 = parcel.readBoolean();
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startAppLockService(readStrongBinder24, intent14, readBoolean26, readString63);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    List<String> appLockedPackageList = getAppLockedPackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(appLockedPackageList);
                    return true;
                case 132:
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAppLockedUnLockPackage(readString64);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppLockedPackage = isAppLockedPackage(readString65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppLockedPackage);
                    return true;
                case 134:
                    clearAppLockedUnLockedApp();
                    parcel2.writeNoException();
                    return true;
                case 135:
                    String appLockedLockType = getAppLockedLockType();
                    parcel2.writeNoException();
                    parcel2.writeString(appLockedLockType);
                    return true;
                case 136:
                    String appLockedCheckAction = getAppLockedCheckAction();
                    parcel2.writeNoException();
                    parcel2.writeString(appLockedCheckAction);
                    return true;
                case 137:
                    String readString66 = parcel.readString();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppLockedVerifying(readString66, readBoolean27);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppLockedVerifying = isAppLockedVerifying(readString67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppLockedVerifying);
                    return true;
                case 139:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplockLockedAppsPackage(readString68);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplockLockedAppsClass(readString69);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplockType(readInt102);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplockEnabled(readBoolean28);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSsecureHiddenAppsPackages(readString70);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    String applockLockedAppsPackage = getApplockLockedAppsPackage();
                    parcel2.writeNoException();
                    parcel2.writeString(applockLockedAppsPackage);
                    return true;
                case 145:
                    String applockLockedAppsClass = getApplockLockedAppsClass();
                    parcel2.writeNoException();
                    parcel2.writeString(applockLockedAppsClass);
                    return true;
                case 146:
                    int applockType = getApplockType();
                    parcel2.writeNoException();
                    parcel2.writeInt(applockType);
                    return true;
                case 147:
                    boolean isApplockEnabled = isApplockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplockEnabled);
                    return true;
                case 148:
                    String ssecureHiddenAppsPackages = getSsecureHiddenAppsPackages();
                    parcel2.writeNoException();
                    parcel2.writeString(ssecureHiddenAppsPackages);
                    return true;
                case 149:
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyPerformStop(readString71);
                    return true;
                case 150:
                    int readInt103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> coverLauncherAvailableAppList = getCoverLauncherAvailableAppList(readInt103);
                    parcel2.writeNoException();
                    parcel2.writeStringList(coverLauncherAvailableAppList);
                    return true;
                case 151:
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map coverLauncherEnabledAppList = getCoverLauncherEnabledAppList(readInt104);
                    parcel2.writeNoException();
                    parcel2.writeMap(coverLauncherEnabledAppList);
                    return true;
                case 152:
                    int readInt105 = parcel.readInt();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map coverLauncherEnabledAppListByType = getCoverLauncherEnabledAppListByType(readInt105, readInt106);
                    parcel2.writeNoException();
                    parcel2.writeMap(coverLauncherEnabledAppListByType);
                    return true;
                case 153:
                    String readString72 = parcel.readString();
                    int readInt107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabledForCoverLauncher = isPackageEnabledForCoverLauncher(readString72, readInt107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabledForCoverLauncher);
                    return true;
                case 154:
                    String readString73 = parcel.readString();
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabledInInnerAppListForCoverLauncher = isPackageEnabledInInnerAppListForCoverLauncher(readString73, readInt108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabledInInnerAppListForCoverLauncher);
                    return true;
                case 155:
                    String readString74 = parcel.readString();
                    int readInt109 = parcel.readInt();
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageSettingsEnabledForCoverLauncher = isPackageSettingsEnabledForCoverLauncher(readString74, readInt109, readInt110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageSettingsEnabledForCoverLauncher);
                    return true;
                case 156:
                    String readString75 = parcel.readString();
                    int readInt111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int coverLauncherPackageEnabled = setCoverLauncherPackageEnabled(readString75, readInt111);
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherPackageEnabled);
                    return true;
                case 157:
                    String readString76 = parcel.readString();
                    int readInt112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int coverLauncherPackageDisabled = setCoverLauncherPackageDisabled(readString76, readInt112);
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherPackageDisabled);
                    return true;
                case 158:
                    Intent intent15 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startActivityForCoverLauncher(intent15, readString77);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    Intent intent16 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString78 = parcel.readString();
                    int readInt113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startActivityForCoverLauncherAsUser(intent16, readString78, readInt113);
                    parcel2.writeNoException();
                    return true;
                case 160:
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot taskSnapshotLowResolution = getTaskSnapshotLowResolution(readInt114);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshotLowResolution, 1);
                    return true;
                case 161:
                    boolean readBoolean29 = parcel.readBoolean();
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeAllVisibleRecentTasksExt(readBoolean29, readBoolean30);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    int readInt115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeAllTasksInRootTask(readInt115);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IActivityTaskManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IActivityTaskManager.DESCRIPTOR;
            }

            @Override // android.app.IActivityTaskManager
            public int startActivity(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivities(IApplicationThread iApplicationThread, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WaitResult) obtain2.readTypedObject(WaitResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityWithConfig(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, Configuration configuration, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startVoiceActivity(String str, String str2, int i, int i2, Intent intent, String str3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeStrongInterface(iVoiceInteractor);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getVoiceInteractorPackageName(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startAssistantActivity(String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromGameSession(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, Intent intent, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsCaller(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, boolean z, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void preloadRecentsActivity(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unhandledBack() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IActivityClientController getActivityClientController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return IActivityClientController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getFrontActivityScreenCompatMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFrontActivityScreenCompatMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean setTaskIsPerceptible(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean removeTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateActiveRecents(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean removeTaskWithFlags(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllVisibleRecentTasks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(assistStructure, 0);
                    obtain.writeTypedObject(assistContent, 0);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean canBeUniversalResizeable(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedRootTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Rect getTaskBounds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void focusTopTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<IBinder> getAppTasks(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBinderArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startSystemLockTaskMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopSystemLockTaskMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(taskDescription, 0);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Point getAppTaskThumbnailSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Point) obtain2.readTypedObject(Point.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setTaskResizeable(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveRootTaskToDisplay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksInWindowingModes(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksWithActivityTypes(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setLockScreenShown(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bundle getAssistContextExtras(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAssistDataAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistDataForTask(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistContextExtrasFromCapture(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistDataForTaskFromCapture(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void keyguardGoingAway(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void suppressResizeConfigChanges(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IWindowOrganizerController getWindowOrganizerController() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return IWindowOrganizerController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean supportsLocalVoiceInteraction() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void requestOpenInBrowserEducation(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ConfigurationInfo) obtain2.readTypedObject(ConfigurationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void cancelTaskWindowTransition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TaskSnapshot) obtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot takeTaskSnapshot(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TaskSnapshot) obtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLastResumedActivityUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean updateConfiguration(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskFeatures(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(remoteTransition, 0);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVrThread(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPersistentVrThread(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopAppSwitches() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityController);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractionSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getPackageScreenCompatMode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean getPackageAskScreenCompat(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageAskScreenCompat(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(splashScreenViewParcelable, 0);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(pictureInPictureUiState, 0);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void detachNavigationBarFromApp(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean clearRecentTasks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public BackNavigationInfo startBackNavigation(RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedObject(backAnimationAdapter, 0);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BackNavigationInfo) obtain2.readTypedObject(BackNavigationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean registerBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return IMultiTaskingBinder.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IFoldStarManager getFoldStarManagerService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return IFoldStarManager.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resetUserPackageSettings(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getScpmVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getPackageFeatureInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAdaptiveAppByDefaultOverride(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setUseLetterbox(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setOrientationControlPolicy(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getOrientationControlPolicy(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setOrientationControlDefault(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setDisallowWhenLandscape(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getUserOrSystemMinAspectRatioOverrideCode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setUserMinAspectRatioOverrideCode(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getAppCompatAlignment() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppCompatAlignment(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCoverLauncherAppCompatAlignment() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setCoverLauncherAppCompatAlignment(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void scheduleRecomputeConfigurationLocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, long j, Map<String, String> map) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeLong(j);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.app.IActivityTaskManager$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IActivityTaskManager.Stub.Proxy.lambda$sendSaLoggingBroadcast$0(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(123, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$sendSaLoggingBroadcast$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.app.IActivityTaskManager
            public void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(124, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bitmap getResumedTaskThumbnail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bitmap) obtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setCutoutPolicy(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCutoutPolicy(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCutoutPolicyForSettings(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registKeyEventListener(IKeyEventListener iKeyEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyEventListener);
                    this.mRemote.transact(129, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<String> getAppLockedPackageList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppLockedUnLockPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAppLockedPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void clearAppLockedUnLockedApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getAppLockedLockType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getAppLockedCheckAction() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppLockedVerifying(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAppLockedVerifying(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockLockedAppsPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockLockedAppsClass(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setSsecureHiddenAppsPackages(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getApplockLockedAppsPackage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getApplockLockedAppsClass() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getApplockType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isApplockEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getSsecureHiddenAppsPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void notifyPerformStop(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(149, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<String> getCoverLauncherAvailableAppList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Map getCoverLauncherEnabledAppList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Map getCoverLauncherEnabledAppListByType(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageEnabledForCoverLauncher(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageEnabledInInnerAppListForCoverLauncher(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int setCoverLauncherPackageEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int setCoverLauncherPackageDisabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startActivityForCoverLauncher(Intent intent, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot getTaskSnapshotLowResolution(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TaskSnapshot) obtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllVisibleRecentTasksExt(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllTasksInRootTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
