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
        public void removeAllVisibleRecentTasks() throws RemoteException {
        }

        @Override // android.app.IActivityTaskManager
        public void removeAllVisibleRecentTasksExt(boolean z, int[] iArr) throws RemoteException {
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
        public void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, String str5, long j, Map<String, String> map) throws RemoteException {
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

    void removeAllVisibleRecentTasks() throws RemoteException;

    void removeAllVisibleRecentTasksExt(boolean z, int[] iArr) throws RemoteException;

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

    void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, String str5, long j, Map<String, String> map) throws RemoteException;

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
            return 160;
        }

        public Stub() {
            attachInterface(this, IActivityTaskManager.DESCRIPTOR);
        }

        public static IActivityTaskManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IActivityTaskManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActivityTaskManager)) {
                return (IActivityTaskManager) iInterfaceQueryLocalInterface;
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
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string3 = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartActivity = startActivity(iApplicationThreadAsInterface, string, string2, intent, string3, strongBinder, string4, i3, i4, profilerInfo, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivity);
                    return true;
                case 2:
                    IApplicationThread iApplicationThreadAsInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartActivities = startActivities(iApplicationThreadAsInterface2, string5, string6, intentArr, strArrCreateStringArray, strongBinder2, bundle2, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivities);
                    return true;
                case 3:
                    IApplicationThread iApplicationThreadAsInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string9 = parcel.readString();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    String string10 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    ProfilerInfo profilerInfo2 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartActivityAsUser = startActivityAsUser(iApplicationThreadAsInterface3, string7, string8, intent2, string9, strongBinder3, string10, i6, i7, profilerInfo2, bundle3, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityAsUser);
                    return true;
                case 4:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStartNextMatchingActivity = startNextMatchingActivity(strongBinder4, intent3, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartNextMatchingActivity);
                    return true;
                case 5:
                    IApplicationThread iApplicationThreadAsInterface4 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IIntentSender iIntentSenderAsInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string11 = parcel.readString();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string12 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartActivityIntentSender = startActivityIntentSender(iApplicationThreadAsInterface4, iIntentSenderAsInterface, strongBinder5, intent4, string11, strongBinder6, string12, i9, i10, i11, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityIntentSender);
                    return true;
                case 6:
                    IApplicationThread iApplicationThreadAsInterface5 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string15 = parcel.readString();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    String string16 = parcel.readString();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    ProfilerInfo profilerInfo3 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WaitResult waitResultStartActivityAndWait = startActivityAndWait(iApplicationThreadAsInterface5, string13, string14, intent5, string15, strongBinder7, string16, i12, i13, profilerInfo3, bundle6, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(waitResultStartActivityAndWait, 1);
                    return true;
                case 7:
                    IApplicationThread iApplicationThreadAsInterface6 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string19 = parcel.readString();
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string20 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartActivityWithConfig = startActivityWithConfig(iApplicationThreadAsInterface6, string17, string18, intent6, string19, strongBinder8, string20, i15, i16, configuration, bundle7, i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityWithConfig);
                    return true;
                case 8:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    Intent intent7 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string23 = parcel.readString();
                    IVoiceInteractionSession iVoiceInteractionSessionAsInterface = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    IVoiceInteractor iVoiceInteractorAsInterface = IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder());
                    int i20 = parcel.readInt();
                    ProfilerInfo profilerInfo4 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartVoiceActivity = startVoiceActivity(string21, string22, i18, i19, intent7, string23, iVoiceInteractionSessionAsInterface, iVoiceInteractorAsInterface, i20, profilerInfo4, bundle8, i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartVoiceActivity);
                    return true;
                case 9:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String voiceInteractorPackageName = getVoiceInteractorPackageName(strongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceInteractorPackageName);
                    return true;
                case 10:
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string26 = parcel.readString();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartAssistantActivity = startAssistantActivity(string24, string25, i22, i23, intent8, string26, bundle9, i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartAssistantActivity);
                    return true;
                case 11:
                    IApplicationThread iApplicationThreadAsInterface7 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartActivityFromGameSession = startActivityFromGameSession(iApplicationThreadAsInterface7, string27, string28, i25, i26, intent9, i27, i28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityFromGameSession);
                    return true;
                case 12:
                    int i29 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartActivityFromRecents = startActivityFromRecents(i29, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityFromRecents);
                    return true;
                case 13:
                    IApplicationThread iApplicationThreadAsInterface8 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string29 = parcel.readString();
                    Intent intent10 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string30 = parcel.readString();
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    String string31 = parcel.readString();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    ProfilerInfo profilerInfo5 = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartActivityAsCaller = startActivityAsCaller(iApplicationThreadAsInterface8, string29, intent10, string30, strongBinder10, string31, i30, i31, profilerInfo5, bundle11, z, i32);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityAsCaller);
                    return true;
                case 14:
                    Intent intent11 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    preloadRecentsActivity(intent11);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i33 = parcel.readInt();
                    Intent intent12 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string32 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsActivityStartAllowedOnDisplay = isActivityStartAllowedOnDisplay(i33, intent12, string32, i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActivityStartAllowedOnDisplay);
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
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFrontActivityScreenCompatMode(i35);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedTask(i36);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i37 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean taskIsPerceptible = setTaskIsPerceptible(i37, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(taskIsPerceptible);
                    return true;
                case 22:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveTask = removeTask(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveTask);
                    return true;
                case 23:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateActiveRecents(i39);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveTaskWithFlags = removeTaskWithFlags(i40, i41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveTaskWithFlags);
                    return true;
                case 25:
                    removeAllVisibleRecentTasks();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i42 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> tasks = getTasks(i42, z3, z4, i43);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 27:
                    IApplicationThread iApplicationThreadAsInterface9 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string33 = parcel.readString();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    moveTaskToFront(iApplicationThreadAsInterface9, string33, i44, i45, bundle12);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<ActivityManager.RecentTaskInfo> recentTasks = getRecentTasks(i46, i47, i48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentTasks, 1);
                    return true;
                case 29:
                    boolean zIsTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTopActivityImmersive);
                    return true;
                case 30:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    AssistStructure assistStructure = (AssistStructure) parcel.readTypedObject(AssistStructure.CREATOR);
                    AssistContent assistContent = (AssistContent) parcel.readTypedObject(AssistContent.CREATOR);
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportAssistContextExtras(strongBinder11, bundle13, assistStructure, assistContent, uri);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCanBeUniversalResizeable = canBeUniversalResizeable(applicationInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanBeUniversalResizeable);
                    return true;
                case 32:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(i49);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = getFocusedRootTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(focusedRootTaskInfo, 1);
                    return true;
                case 34:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect taskBounds = getTaskBounds(i50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 35:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    focusTopTask(i51);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i52 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(i52, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    boolean zIsInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInLockTaskMode);
                    return true;
                case 38:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 39:
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<IBinder> appTasks = getAppTasks(string34);
                    parcel2.writeNoException();
                    parcel2.writeBinderList(appTasks);
                    return true;
                case 40:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(i53);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    stopSystemLockTaskMode();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IVoiceInteractionSession iVoiceInteractionSessionAsInterface2 = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishVoiceTask(iVoiceInteractionSessionAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    Intent intent13 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    ActivityManager.TaskDescription taskDescription = (ActivityManager.TaskDescription) parcel.readTypedObject(ActivityManager.TaskDescription.CREATOR);
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddAppTask = addAppTask(strongBinder12, intent13, taskDescription, bitmap);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddAppTask);
                    return true;
                case 44:
                    Point appTaskThumbnailSize = getAppTaskThumbnailSize();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appTaskThumbnailSize, 1);
                    return true;
                case 45:
                    IApplicationThread iApplicationThreadAsInterface10 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    releaseSomeActivities(iApplicationThreadAsInterface10);
                    return true;
                case 46:
                    String string35 = parcel.readString();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap taskDescriptionIcon = getTaskDescriptionIcon(string35, i54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskDescriptionIcon, 1);
                    return true;
                case 47:
                    ITaskStackListener iTaskStackListenerAsInterface = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(iTaskStackListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ITaskStackListener iTaskStackListenerAsInterface2 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(iTaskStackListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(i55, i56);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int i57 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(i57, rect, i58);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveRootTaskToDisplay(i59, i60);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i61 = parcel.readInt();
                    int i62 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(i61, i62, z5);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksInWindowingModes(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeRootTasksWithActivityTypes(iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 56:
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = getRootTaskInfo(i63, i64);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfo, 1);
                    return true;
                case 57:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfosOnDisplay = getAllRootTaskInfosOnDisplay(i65);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfosOnDisplay, 1);
                    return true;
                case 58:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ActivityTaskManager.RootTaskInfo rootTaskInfoOnDisplay = getRootTaskInfoOnDisplay(i66, i67, i68);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rootTaskInfoOnDisplay, 1);
                    return true;
                case 59:
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockScreenShown(z6, z7);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle assistContextExtras = getAssistContextExtras(i69);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(assistContextExtras, 1);
                    return true;
                case 61:
                    int i70 = parcel.readInt();
                    IAssistDataReceiver iAssistDataReceiverAsInterface = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    boolean z8 = parcel.readBoolean();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRequestAssistContextExtras = requestAssistContextExtras(i70, iAssistDataReceiverAsInterface, bundle14, strongBinder13, z8, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAssistContextExtras);
                    return true;
                case 62:
                    IAssistDataReceiver iAssistDataReceiverAsInterface2 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestAutofillData = requestAutofillData(iAssistDataReceiverAsInterface2, bundle15, strongBinder14, i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAutofillData);
                    return true;
                case 63:
                    boolean zIsAssistDataAllowed = isAssistDataAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAssistDataAllowed);
                    return true;
                case 64:
                    IAssistDataReceiver iAssistDataReceiverAsInterface3 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i72 = parcel.readInt();
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRequestAssistDataForTask = requestAssistDataForTask(iAssistDataReceiverAsInterface3, i72, string36, string37, z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAssistDataForTask);
                    return true;
                case 65:
                    int i73 = parcel.readInt();
                    IAssistDataReceiver iAssistDataReceiverAsInterface4 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRequestAssistContextExtrasFromCapture = requestAssistContextExtrasFromCapture(i73, iAssistDataReceiverAsInterface4, bundle16, strongBinder15, z11, z12, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAssistContextExtrasFromCapture);
                    return true;
                case 66:
                    IAssistDataReceiver iAssistDataReceiverAsInterface5 = IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i74 = parcel.readInt();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    boolean z14 = parcel.readBoolean();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRequestAssistDataForTaskFromCapture = requestAssistDataForTaskFromCapture(iAssistDataReceiverAsInterface5, i74, string38, string39, z14, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAssistDataForTaskFromCapture);
                    return true;
                case 67:
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    keyguardGoingAway(i75);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(z16);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    IWindowOrganizerController windowOrganizerController = getWindowOrganizerController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(windowOrganizerController);
                    return true;
                case 70:
                    boolean zSupportsLocalVoiceInteraction = supportsLocalVoiceInteraction();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsLocalVoiceInteraction);
                    return true;
                case 71:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    requestOpenInBrowserEducation(strongBinder16);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    ConfigurationInfo deviceConfigurationInfo = getDeviceConfigurationInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceConfigurationInfo, 1);
                    return true;
                case 73:
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(i76);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int i77 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot taskSnapshot = getTaskSnapshot(i77, z17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshot, 1);
                    return true;
                case 75:
                    int i78 = parcel.readInt();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot taskSnapshotTakeTaskSnapshot = takeTaskSnapshot(i78, z18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshotTakeTaskSnapshot, 1);
                    return true;
                case 76:
                    int lastResumedActivityUserId = getLastResumedActivityUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastResumedActivityUserId);
                    return true;
                case 77:
                    Configuration configuration2 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfiguration);
                    return true;
                case 78:
                    int i79 = parcel.readInt();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateLockTaskFeatures(i79, i80);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    String string40 = parcel.readString();
                    RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationForNextActivityStart(string40, remoteAnimationAdapter, strongBinder17);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    String string41 = parcel.readString();
                    RemoteAnimationAdapter remoteAnimationAdapter2 = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteTransitionForNextActivityStart(string41, remoteAnimationAdapter2, strongBinder18, remoteTransition);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    int i81 = parcel.readInt();
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimationsForDisplay(i81, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    alwaysShowUnsupportedCompileSdkWarning(componentName);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVrThread(i82);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(i83);
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
                    IActivityController iActivityControllerAsInterface = IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(iActivityControllerAsInterface, z19);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    IVoiceInteractionSession iVoiceInteractionSessionAsInterface3 = IVoiceInteractionSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoiceKeepAwake(iVoiceInteractionSessionAsInterface3, z20);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageScreenCompatMode = getPackageScreenCompatMode(string42);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageScreenCompatMode);
                    return true;
                case 90:
                    String string43 = parcel.readString();
                    int i84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(string43, i84);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean packageAskScreenCompat = getPackageAskScreenCompat(string44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageAskScreenCompat);
                    return true;
                case 92:
                    String string45 = parcel.readString();
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPackageAskScreenCompat(string45, z21);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    clearLaunchParamsForPackages(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int i85 = parcel.readInt();
                    SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable = (SplashScreenView.SplashScreenViewParcelable) parcel.readTypedObject(SplashScreenView.SplashScreenViewParcelable.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSplashScreenViewCopyFinished(i85, splashScreenViewParcelable);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    PictureInPictureUiState pictureInPictureUiState = (PictureInPictureUiState) parcel.readTypedObject(PictureInPictureUiState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPictureInPictureUiStateChanged(pictureInPictureUiState);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachNavigationBarFromApp(strongBinder19);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearRecentTasks = clearRecentTasks(i86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearRecentTasks);
                    return true;
                case 98:
                    IApplicationThread iApplicationThreadAsInterface11 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRunningRemoteTransitionDelegate(iApplicationThreadAsInterface11);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    BackAnimationAdapter backAnimationAdapter = (BackAnimationAdapter) parcel.readTypedObject(BackAnimationAdapter.CREATOR);
                    parcel.enforceNoDataAvail();
                    BackNavigationInfo backNavigationInfoStartBackNavigation = startBackNavigation(remoteCallback, backAnimationAdapter);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(backNavigationInfoStartBackNavigation, 1);
                    return true;
                case 100:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterBackgroundActivityStartCallback = registerBackgroundActivityStartCallback(strongBinder20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterBackgroundActivityStartCallback);
                    return true;
                case 101:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterBackgroundActivityStartCallback(strongBinder21);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    IScreenCaptureObserver iScreenCaptureObserverAsInterface = IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerScreenCaptureObserver(strongBinder22, iScreenCaptureObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    IScreenCaptureObserver iScreenCaptureObserverAsInterface2 = IScreenCaptureObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenCaptureObserver(strongBinder23, iScreenCaptureObserverAsInterface2);
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
                    String string46 = parcel.readString();
                    int i87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<CompatChangeablePackageInfo> compatChangeablePackageInfoList = getCompatChangeablePackageInfoList(string46, i87);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(compatChangeablePackageInfoList, 1);
                    return true;
                case 107:
                    int i88 = parcel.readInt();
                    int i89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetUserPackageSettings(i88, i89);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String scpmVersion = getScpmVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(scpmVersion);
                    return true;
                case 109:
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String packageFeatureInfo = getPackageFeatureInfo(string47);
                    parcel2.writeNoException();
                    parcel2.writeString(packageFeatureInfo);
                    return true;
                case 110:
                    String string48 = parcel.readString();
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdaptiveAppByDefaultOverride(string48, z22);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUseLetterbox(z23);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    int i90 = parcel.readInt();
                    String string49 = parcel.readString();
                    int i91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrientationControlPolicy(i90, string49, i91);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    int i92 = parcel.readInt();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int orientationControlPolicy = getOrientationControlPolicy(i92, string50);
                    parcel2.writeNoException();
                    parcel2.writeInt(orientationControlPolicy);
                    return true;
                case 114:
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOrientationControlDefault(z24);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisallowWhenLandscape(z25);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    String string51 = parcel.readString();
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userOrSystemMinAspectRatioOverrideCode = getUserOrSystemMinAspectRatioOverrideCode(string51, i93);
                    parcel2.writeNoException();
                    parcel2.writeInt(userOrSystemMinAspectRatioOverrideCode);
                    return true;
                case 117:
                    String string52 = parcel.readString();
                    int i94 = parcel.readInt();
                    int i95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserMinAspectRatioOverrideCode(string52, i94, i95);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    int appCompatAlignment = getAppCompatAlignment();
                    parcel2.writeNoException();
                    parcel2.writeInt(appCompatAlignment);
                    return true;
                case 119:
                    int i96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppCompatAlignment(i96);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    int coverLauncherAppCompatAlignment = getCoverLauncherAppCompatAlignment();
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherAppCompatAlignment);
                    return true;
                case 121:
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCoverLauncherAppCompatAlignment(i97);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    scheduleRecomputeConfigurationLocked();
                    parcel2.writeNoException();
                    return true;
                case 123:
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    String string57 = parcel.readString();
                    long j = parcel.readLong();
                    int i98 = parcel.readInt();
                    final HashMap map = i98 < 0 ? null : new HashMap();
                    IntStream.range(0, i98).forEach(new IntConsumer() { // from class: android.app.IActivityTaskManager$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i99) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), parcel3.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    sendSaLoggingBroadcast(string53, string54, string55, string56, string57, j, map);
                    return true;
                case 124:
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    String string60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendSaLoggingBroadcastForSetting(string58, string59, string60);
                    return true;
                case 125:
                    int i99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bitmap resumedTaskThumbnail = getResumedTaskThumbnail(i99);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resumedTaskThumbnail, 1);
                    return true;
                case 126:
                    int i100 = parcel.readInt();
                    String string61 = parcel.readString();
                    int i101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCutoutPolicy(i100, string61, i101);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    int i102 = parcel.readInt();
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cutoutPolicy = getCutoutPolicy(i102, string62);
                    parcel2.writeNoException();
                    parcel2.writeInt(cutoutPolicy);
                    return true;
                case 128:
                    int i103 = parcel.readInt();
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cutoutPolicyForSettings = getCutoutPolicyForSettings(i103, string63);
                    parcel2.writeNoException();
                    parcel2.writeInt(cutoutPolicyForSettings);
                    return true;
                case 129:
                    IKeyEventListener iKeyEventListenerAsInterface = IKeyEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registKeyEventListener(iKeyEventListenerAsInterface);
                    return true;
                case 130:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    Intent intent14 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    boolean z26 = parcel.readBoolean();
                    String string64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startAppLockService(strongBinder24, intent14, z26, string64);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    List<String> appLockedPackageList = getAppLockedPackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(appLockedPackageList);
                    return true;
                case 132:
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAppLockedUnLockPackage(string65);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAppLockedPackage = isAppLockedPackage(string66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppLockedPackage);
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
                    String string67 = parcel.readString();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppLockedVerifying(string67, z27);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAppLockedVerifying = isAppLockedVerifying(string68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppLockedVerifying);
                    return true;
                case 139:
                    String string69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplockLockedAppsPackage(string69);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setApplockLockedAppsClass(string70);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    int i104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplockType(i104);
                    parcel2.writeNoException();
                    return true;
                case 142:
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setApplockEnabled(z28);
                    parcel2.writeNoException();
                    return true;
                case 143:
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSsecureHiddenAppsPackages(string71);
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
                    boolean zIsApplockEnabled = isApplockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsApplockEnabled);
                    return true;
                case 148:
                    String ssecureHiddenAppsPackages = getSsecureHiddenAppsPackages();
                    parcel2.writeNoException();
                    parcel2.writeString(ssecureHiddenAppsPackages);
                    return true;
                case 149:
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyPerformStop(string72);
                    return true;
                case 150:
                    int i105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> coverLauncherAvailableAppList = getCoverLauncherAvailableAppList(i105);
                    parcel2.writeNoException();
                    parcel2.writeStringList(coverLauncherAvailableAppList);
                    return true;
                case 151:
                    int i106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map coverLauncherEnabledAppList = getCoverLauncherEnabledAppList(i106);
                    parcel2.writeNoException();
                    parcel2.writeMap(coverLauncherEnabledAppList);
                    return true;
                case 152:
                    int i107 = parcel.readInt();
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map coverLauncherEnabledAppListByType = getCoverLauncherEnabledAppListByType(i107, i108);
                    parcel2.writeNoException();
                    parcel2.writeMap(coverLauncherEnabledAppListByType);
                    return true;
                case 153:
                    String string73 = parcel.readString();
                    int i109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageEnabledForCoverLauncher = isPackageEnabledForCoverLauncher(string73, i109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageEnabledForCoverLauncher);
                    return true;
                case 154:
                    String string74 = parcel.readString();
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageEnabledInInnerAppListForCoverLauncher = isPackageEnabledInInnerAppListForCoverLauncher(string74, i110);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageEnabledInInnerAppListForCoverLauncher);
                    return true;
                case 155:
                    String string75 = parcel.readString();
                    int i111 = parcel.readInt();
                    int i112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageSettingsEnabledForCoverLauncher = isPackageSettingsEnabledForCoverLauncher(string75, i111, i112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageSettingsEnabledForCoverLauncher);
                    return true;
                case 156:
                    String string76 = parcel.readString();
                    int i113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int coverLauncherPackageEnabled = setCoverLauncherPackageEnabled(string76, i113);
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherPackageEnabled);
                    return true;
                case 157:
                    String string77 = parcel.readString();
                    int i114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int coverLauncherPackageDisabled = setCoverLauncherPackageDisabled(string77, i114);
                    parcel2.writeNoException();
                    parcel2.writeInt(coverLauncherPackageDisabled);
                    return true;
                case 158:
                    Intent intent15 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startActivityForCoverLauncher(intent15, string78);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    Intent intent16 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string79 = parcel.readString();
                    int i115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startActivityForCoverLauncherAsUser(intent16, string79, i115);
                    parcel2.writeNoException();
                    return true;
                case 160:
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    TaskSnapshot taskSnapshotLowResolution = getTaskSnapshotLowResolution(i116);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskSnapshotLowResolution, 1);
                    return true;
                case 161:
                    boolean z29 = parcel.readBoolean();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeAllVisibleRecentTasksExt(z29, iArrCreateIntArray3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivities(IApplicationThread iApplicationThread, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedArray(intentArr, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, Intent intent, String str, IBinder iBinder2, String str2, int i, int i2, int i3, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public WaitResult startActivityAndWait(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WaitResult) parcelObtain2.readTypedObject(WaitResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityWithConfig(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, Configuration configuration, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(configuration, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startVoiceActivity(String str, String str2, int i, int i2, Intent intent, String str3, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, int i3, ProfilerInfo profilerInfo, Bundle bundle, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSession);
                    parcelObtain.writeStrongInterface(iVoiceInteractor);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getVoiceInteractorPackageName(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startAssistantActivity(String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromGameSession(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, Intent intent, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int startActivityAsCaller(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, boolean z, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void preloadRecentsActivity(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unhandledBack() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IActivityClientController getActivityClientController() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IActivityClientController.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getFrontActivityScreenCompatMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFrontActivityScreenCompatMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean setTaskIsPerceptible(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean removeTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateActiveRecents(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean removeTaskWithFlags(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllVisibleRecentTasks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void reportAssistContextExtras(IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(assistStructure, 0);
                    parcelObtain.writeTypedObject(assistContent, 0);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean canBeUniversalResizeable(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setFocusedRootTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) parcelObtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Rect getTaskBounds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void focusTopTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<IBinder> getAppTasks(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createBinderArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startSystemLockTaskMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopSystemLockTaskMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void finishVoiceTask(IVoiceInteractionSession iVoiceInteractionSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSession);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int addAppTask(IBinder iBinder, Intent intent, ActivityManager.TaskDescription taskDescription, Bitmap bitmap) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(taskDescription, 0);
                    parcelObtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Point getAppTaskThumbnailSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Point) parcelObtain2.readTypedObject(Point.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setTaskResizeable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveRootTaskToDisplay(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksInWindowingModes(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeRootTasksWithActivityTypes(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) parcelObtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) parcelObtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setLockScreenShown(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bundle getAssistContextExtras(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistContextExtras(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAutofillData(IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAssistDataAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistDataForTask(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistContextExtrasFromCapture(int i, IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean requestAssistDataForTaskFromCapture(IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAssistDataReceiver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void keyguardGoingAway(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void suppressResizeConfigChanges(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IWindowOrganizerController getWindowOrganizerController() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IWindowOrganizerController.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean supportsLocalVoiceInteraction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void requestOpenInBrowserEducation(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ConfigurationInfo) parcelObtain2.readTypedObject(ConfigurationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void cancelTaskWindowTransition(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot getTaskSnapshot(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TaskSnapshot) parcelObtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot takeTaskSnapshot(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TaskSnapshot) parcelObtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getLastResumedActivityUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean updateConfiguration(Configuration configuration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void updateLockTaskFeatures(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteAnimationAdapter, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteAnimationAdapter, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(remoteTransition, 0);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVrThread(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPersistentVrThread(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void stopAppSwitches() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActivityController);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoiceInteractionSession);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getPackageScreenCompatMode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean getPackageAskScreenCompat(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setPackageAskScreenCompat(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void clearLaunchParamsForPackages(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(splashScreenViewParcelable, 0);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pictureInPictureUiState, 0);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void detachNavigationBarFromApp(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean clearRecentTasks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public BackNavigationInfo startBackNavigation(RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(backAnimationAdapter, 0);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BackNavigationInfo) parcelObtain2.readTypedObject(BackNavigationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean registerBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterBackgroundActivityStartCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void unregisterScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iScreenCaptureObserver);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMultiTaskingBinder.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public IFoldStarManager getFoldStarManagerService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IFoldStarManager.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void resetUserPackageSettings(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getScpmVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getPackageFeatureInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAdaptiveAppByDefaultOverride(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setUseLetterbox(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setOrientationControlPolicy(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getOrientationControlPolicy(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setOrientationControlDefault(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setDisallowWhenLandscape(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getUserOrSystemMinAspectRatioOverrideCode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setUserMinAspectRatioOverrideCode(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getAppCompatAlignment() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppCompatAlignment(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCoverLauncherAppCompatAlignment() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setCoverLauncherAppCompatAlignment(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void scheduleRecomputeConfigurationLocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void sendSaLoggingBroadcast(String str, String str2, String str3, String str4, String str5, long j, Map<String, String> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeLong(j);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.app.IActivityTaskManager$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IActivityTaskManager.Stub.Proxy.lambda$sendSaLoggingBroadcast$0(parcelObtain, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(123, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$sendSaLoggingBroadcast$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.app.IActivityTaskManager
            public void sendSaLoggingBroadcastForSetting(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(124, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Bitmap getResumedTaskThumbnail(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bitmap) parcelObtain2.readTypedObject(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setCutoutPolicy(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCutoutPolicy(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getCutoutPolicyForSettings(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void registKeyEventListener(IKeyEventListener iKeyEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyEventListener);
                    this.mRemote.transact(129, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<String> getAppLockedPackageList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppLockedUnLockPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAppLockedPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void clearAppLockedUnLockedApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getAppLockedLockType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getAppLockedCheckAction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setAppLockedVerifying(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isAppLockedVerifying(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockLockedAppsPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockLockedAppsClass(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setApplockEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void setSsecureHiddenAppsPackages(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getApplockLockedAppsPackage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getApplockLockedAppsClass() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int getApplockType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isApplockEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public String getSsecureHiddenAppsPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void notifyPerformStop(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(149, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public List<String> getCoverLauncherAvailableAppList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Map getCoverLauncherEnabledAppList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public Map getCoverLauncherEnabledAppListByType(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageEnabledForCoverLauncher(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageEnabledInInnerAppListForCoverLauncher(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int setCoverLauncherPackageEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public int setCoverLauncherPackageDisabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startActivityForCoverLauncher(Intent intent, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public TaskSnapshot getTaskSnapshotLowResolution(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TaskSnapshot) parcelObtain2.readTypedObject(TaskSnapshot.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityTaskManager
            public void removeAllVisibleRecentTasksExt(boolean z, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
