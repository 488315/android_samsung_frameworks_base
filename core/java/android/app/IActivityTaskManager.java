package android.app;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.IIntentSender;
import android.content.Intent;
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
import android.view.IRecentsAnimationRunner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IActivityTaskManager extends IInterface {
  public static final String DESCRIPTOR = "android.app.IActivityTaskManager";

  int addAppTask(
      IBinder iBinder,
      Intent intent,
      ActivityManager.TaskDescription taskDescription,
      Bitmap bitmap)
      throws RemoteException;

  void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) throws RemoteException;

  void cancelRecentsAnimation(boolean z) throws RemoteException;

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

  String getAppLockedCheckAction() throws RemoteException;

  String getAppLockedLockType() throws RemoteException;

  List<String> getAppLockedPackageList() throws RemoteException;

  Point getAppTaskThumbnailSize() throws RemoteException;

  List<IBinder> getAppTasks(String str) throws RemoteException;

  String getApplockLockedAppsClass() throws RemoteException;

  String getApplockLockedAppsPackage() throws RemoteException;

  int getApplockType() throws RemoteException;

  Bundle getAssistContextExtras(int i) throws RemoteException;

  int getBoundsCompatAlignment() throws RemoteException;

  ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(
      String str, int i) throws RemoteException;

  List<String> getCoverLauncherAvailableAppList(int i) throws RemoteException;

  Map getCoverLauncherEnabledAppList(int i) throws RemoteException;

  Map getCoverLauncherEnabledAppListByType(int i, int i2) throws RemoteException;

  int getCutoutPolicy(int i, String str) throws RemoteException;

  ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException;

  ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException;

  IFoldStarManager getFoldStarManagerService() throws RemoteException;

  int getFrontActivityScreenCompatMode() throws RemoteException;

  int getLastResumedActivityUserId() throws RemoteException;

  int getLockTaskModeState() throws RemoteException;

  IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException;

  int getOrientationControlPolicy(int i, String str) throws RemoteException;

  boolean getPackageAskScreenCompat(String str) throws RemoteException;

  int getPackageScreenCompatMode(String str) throws RemoteException;

  ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3)
      throws RemoteException;

  Bitmap getResumedTaskThumbnail(int i) throws RemoteException;

  ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) throws RemoteException;

  ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3)
      throws RemoteException;

  String getScpmVersion() throws RemoteException;

  String getSsecureHiddenAppsPackages() throws RemoteException;

  Rect getTaskBounds(int i) throws RemoteException;

  ActivityManager.TaskDescription getTaskDescription(int i) throws RemoteException;

  Bitmap getTaskDescriptionIcon(String str, int i) throws RemoteException;

  TaskSnapshot getTaskSnapshot(int i, boolean z, boolean z2) throws RemoteException;

  List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2)
      throws RemoteException;

  String getVoiceInteractorPackageName(IBinder iBinder) throws RemoteException;

  IWindowOrganizerController getWindowOrganizerController() throws RemoteException;

  boolean hasWallpaperBackgroundForLetterbox(int i) throws RemoteException;

  boolean isActivityStartAllowedOnDisplay(int i, Intent intent, String str, int i2)
      throws RemoteException;

  boolean isAppLockedPackage(String str) throws RemoteException;

  boolean isAppLockedVerifying(String str) throws RemoteException;

  boolean isApplockEnabled() throws RemoteException;

  boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException;

  boolean isInLockTaskMode() throws RemoteException;

  boolean isPackageEnabledForCoverLauncher(String str, int i) throws RemoteException;

  boolean isPackageSettingsEnabledForCoverLauncher(String str, int i, int i2)
      throws RemoteException;

  boolean isTopActivityImmersive() throws RemoteException;

  void keyguardGoingAway(int i) throws RemoteException;

  void moveRootTaskToDisplay(int i, int i2) throws RemoteException;

  void moveTaskToFront(
      IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle)
      throws RemoteException;

  void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException;

  void onPictureInPictureStateChanged(PictureInPictureUiState pictureInPictureUiState)
      throws RemoteException;

  void onSplashScreenViewCopyFinished(
      int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable)
      throws RemoteException;

  void registKeyEventListener(IKeyEventListener iKeyEventListener) throws RemoteException;

  void registerRemoteAnimationForNextActivityStart(
      String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder)
      throws RemoteException;

  void registerRemoteAnimationsForDisplay(
      int i, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

  void registerRemoteTransitionForNextActivityStart(
      String str,
      RemoteAnimationAdapter remoteAnimationAdapter,
      IBinder iBinder,
      RemoteTransition remoteTransition)
      throws RemoteException;

  void registerScreenCaptureObserver(IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver)
      throws RemoteException;

  void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

  void releaseSomeActivities(IApplicationThread iApplicationThread) throws RemoteException;

  void removeAllVisibleRecentTasks() throws RemoteException;

  void removeRootTasksInWindowingModes(int[] iArr) throws RemoteException;

  void removeRootTasksWithActivityTypes(int[] iArr) throws RemoteException;

  boolean removeTask(int i) throws RemoteException;

  boolean removeTaskWithFlags(int i, int i2) throws RemoteException;

  void reportAssistContextExtras(
      IBinder iBinder,
      Bundle bundle,
      AssistStructure assistStructure,
      AssistContent assistContent,
      Uri uri)
      throws RemoteException;

  boolean requestAssistContextExtras(
      int i,
      IAssistDataReceiver iAssistDataReceiver,
      Bundle bundle,
      IBinder iBinder,
      boolean z,
      boolean z2)
      throws RemoteException;

  boolean requestAssistDataForTask(
      IAssistDataReceiver iAssistDataReceiver, int i, String str, String str2)
      throws RemoteException;

  boolean requestAutofillData(
      IAssistDataReceiver iAssistDataReceiver, Bundle bundle, IBinder iBinder, int i)
      throws RemoteException;

  void requestWaitingForOccluding(int i) throws RemoteException;

  void resetUserPackageSettings(int i, int i2) throws RemoteException;

  void resizeTask(int i, Rect rect, int i2) throws RemoteException;

  void resumeAppSwitches() throws RemoteException;

  void sendSaLoggingBroadcast(String str, String str2, String str3, long j, Map<String, String> map)
      throws RemoteException;

  void sendSaLoggingBroadcastForSetting(String str, String str2, String str3)
      throws RemoteException;

  void setActivityController(IActivityController iActivityController, boolean z)
      throws RemoteException;

  void setAppLockedUnLockPackage(String str) throws RemoteException;

  void setAppLockedVerifying(String str, boolean z) throws RemoteException;

  void setApplockEnabled(boolean z) throws RemoteException;

  void setApplockLockedAppsClass(String str) throws RemoteException;

  void setApplockLockedAppsPackage(String str) throws RemoteException;

  void setApplockType(int i) throws RemoteException;

  void setBoundsCompatAlignment(int i) throws RemoteException;

  int setCoverLauncherPackageDisabled(String str, int i) throws RemoteException;

  int setCoverLauncherPackageEnabled(String str, int i) throws RemoteException;

  void setCustomSplashScreenTheme(String str, String str2, int i) throws RemoteException;

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

  void setRunningRemoteTransitionDelegate(IApplicationThread iApplicationThread)
      throws RemoteException;

  void setSsecureHiddenAppsPackages(String str) throws RemoteException;

  void setTaskResizeable(int i, int i2) throws RemoteException;

  void setUseLetterbox(boolean z) throws RemoteException;

  void setVoiceKeepAwake(IVoiceInteractionSession iVoiceInteractionSession, boolean z)
      throws RemoteException;

  void setVrThread(int i) throws RemoteException;

  int startActivities(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent[] intentArr,
      String[] strArr,
      IBinder iBinder,
      Bundle bundle,
      int i)
      throws RemoteException;

  int startActivity(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle)
      throws RemoteException;

  WaitResult startActivityAndWait(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i3)
      throws RemoteException;

  int startActivityAsCaller(
      IApplicationThread iApplicationThread,
      String str,
      Intent intent,
      String str2,
      IBinder iBinder,
      String str3,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      boolean z,
      int i3)
      throws RemoteException;

  int startActivityAsUser(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i3)
      throws RemoteException;

  void startActivityForCoverLauncher(Intent intent, String str) throws RemoteException;

  void startActivityForCoverLauncherAsUser(Intent intent, String str, int i) throws RemoteException;

  void startActivityForDexRestart(int i) throws RemoteException;

  int startActivityFromGameSession(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      int i,
      int i2,
      Intent intent,
      int i3,
      int i4)
      throws RemoteException;

  int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

  int startActivityIntentSender(
      IApplicationThread iApplicationThread,
      IIntentSender iIntentSender,
      IBinder iBinder,
      Intent intent,
      String str,
      IBinder iBinder2,
      String str2,
      int i,
      int i2,
      int i3,
      Bundle bundle)
      throws RemoteException;

  int startActivityWithConfig(
      IApplicationThread iApplicationThread,
      String str,
      String str2,
      Intent intent,
      String str3,
      IBinder iBinder,
      String str4,
      int i,
      int i2,
      Configuration configuration,
      Bundle bundle,
      int i3)
      throws RemoteException;

  void startAppLockService(IBinder iBinder, Intent intent, boolean z, String str)
      throws RemoteException;

  int startAssistantActivity(
      String str, String str2, int i, int i2, Intent intent, String str3, Bundle bundle, int i3)
      throws RemoteException;

  BackNavigationInfo startBackNavigation(
      RemoteCallback remoteCallback, BackAnimationAdapter backAnimationAdapter)
      throws RemoteException;

  boolean startDreamActivity(Intent intent) throws RemoteException;

  boolean startNextMatchingActivity(IBinder iBinder, Intent intent, Bundle bundle)
      throws RemoteException;

  void startRecentsActivity(Intent intent, long j, IRecentsAnimationRunner iRecentsAnimationRunner)
      throws RemoteException;

  void startSystemLockTaskMode(int i) throws RemoteException;

  int startVoiceActivity(
      String str,
      String str2,
      int i,
      int i2,
      Intent intent,
      String str3,
      IVoiceInteractionSession iVoiceInteractionSession,
      IVoiceInteractor iVoiceInteractor,
      int i3,
      ProfilerInfo profilerInfo,
      Bundle bundle,
      int i4)
      throws RemoteException;

  void stopAppSwitches() throws RemoteException;

  void stopSystemLockTaskMode() throws RemoteException;

  boolean supportsLocalVoiceInteraction() throws RemoteException;

  void suppressResizeConfigChanges(boolean z) throws RemoteException;

  TaskSnapshot takeTaskSnapshot(int i, boolean z) throws RemoteException;

  void unhandledBack() throws RemoteException;

  void unregisterScreenCaptureObserver(
      IBinder iBinder, IScreenCaptureObserver iScreenCaptureObserver) throws RemoteException;

  void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

  boolean updateConfiguration(Configuration configuration) throws RemoteException;

  void updateLockTaskFeatures(int i, int i2) throws RemoteException;

  void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

  public static class Default implements IActivityTaskManager {
    @Override // android.app.IActivityTaskManager
    public int startActivity(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        Intent intent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int flags,
        ProfilerInfo profilerInfo,
        Bundle options)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivities(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        Intent[] intents,
        String[] resolvedTypes,
        IBinder resultTo,
        Bundle options,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivityAsUser(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        Intent intent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int flags,
        ProfilerInfo profilerInfo,
        Bundle options,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public boolean startNextMatchingActivity(IBinder callingActivity, Intent intent, Bundle options)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean startDreamActivity(Intent intent) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivityIntentSender(
        IApplicationThread caller,
        IIntentSender target,
        IBinder whitelistToken,
        Intent fillInIntent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int flagsMask,
        int flagsValues,
        Bundle options)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public WaitResult startActivityAndWait(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        Intent intent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int flags,
        ProfilerInfo profilerInfo,
        Bundle options,
        int userId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivityWithConfig(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        Intent intent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int startFlags,
        Configuration newConfig,
        Bundle options,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int startVoiceActivity(
        String callingPackage,
        String callingFeatureId,
        int callingPid,
        int callingUid,
        Intent intent,
        String resolvedType,
        IVoiceInteractionSession session,
        IVoiceInteractor interactor,
        int flags,
        ProfilerInfo profilerInfo,
        Bundle options,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public String getVoiceInteractorPackageName(IBinder callingVoiceInteractor)
        throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public int startAssistantActivity(
        String callingPackage,
        String callingFeatureId,
        int callingPid,
        int callingUid,
        Intent intent,
        String resolvedType,
        Bundle options,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivityFromGameSession(
        IApplicationThread caller,
        String callingPackage,
        String callingFeatureId,
        int callingPid,
        int callingUid,
        Intent intent,
        int taskId,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void startRecentsActivity(
        Intent intent, long eventTime, IRecentsAnimationRunner recentsAnimationRunner)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int startActivityAsCaller(
        IApplicationThread caller,
        String callingPackage,
        Intent intent,
        String resolvedType,
        IBinder resultTo,
        String resultWho,
        int requestCode,
        int flags,
        ProfilerInfo profilerInfo,
        Bundle options,
        boolean ignoreTargetSecurity,
        int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isActivityStartAllowedOnDisplay(
        int displayId, Intent intent, String resolvedType, int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void unhandledBack() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public IActivityClientController getActivityClientController() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public int getFrontActivityScreenCompatMode() throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void setFrontActivityScreenCompatMode(int mode) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setFocusedTask(int taskId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean removeTask(int taskId) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean removeTaskWithFlags(int taskId, int flags) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void removeAllVisibleRecentTasks() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public List<ActivityManager.RunningTaskInfo> getTasks(
        int maxNum, boolean filterOnlyVisibleRecents, boolean keepIntentExtra, int displayId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void moveTaskToFront(
        IApplicationThread app, String callingPackage, int task, int flags, Bundle options)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(
        int maxNum, int flags, int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isTopActivityImmersive() throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public ActivityManager.TaskDescription getTaskDescription(int taskId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void reportAssistContextExtras(
        IBinder assistToken,
        Bundle extras,
        AssistStructure structure,
        AssistContent content,
        Uri referrer)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setFocusedRootTask(int taskId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public Rect getTaskBounds(int taskId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void focusTopTask(int displayId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void cancelRecentsAnimation(boolean restoreHomeRootTaskPosition)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean isInLockTaskMode() throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public int getLockTaskModeState() throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public List<IBinder> getAppTasks(String callingPackage) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void startSystemLockTaskMode(int taskId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void stopSystemLockTaskMode() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void finishVoiceTask(IVoiceInteractionSession session) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int addAppTask(
        IBinder activityToken,
        Intent intent,
        ActivityManager.TaskDescription description,
        Bitmap thumbnail)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public Point getAppTaskThumbnailSize() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void releaseSomeActivities(IApplicationThread app) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public Bitmap getTaskDescriptionIcon(String filename, int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void moveRootTaskToDisplay(int taskId, int displayId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void removeRootTasksInWindowingModes(int[] windowingModes) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void removeRootTasksWithActivityTypes(int[] activityTypes) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int windowingMode, int activityType)
        throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int displayId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(
        int windowingMode, int activityType, int displayId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void setLockScreenShown(boolean showingKeyguard, boolean showingAod)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public Bundle getAssistContextExtras(int requestType) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public boolean requestAssistContextExtras(
        int requestType,
        IAssistDataReceiver receiver,
        Bundle receiverExtras,
        IBinder activityToken,
        boolean focused,
        boolean newSessionId)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean requestAutofillData(
        IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, int flags)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean requestAssistDataForTask(
        IAssistDataReceiver receiver,
        int taskId,
        String callingPackageName,
        String callingAttributionTag)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void keyguardGoingAway(int flags) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public IWindowOrganizerController getWindowOrganizerController() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public boolean supportsLocalVoiceInteraction() throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void cancelTaskWindowTransition(int taskId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public TaskSnapshot getTaskSnapshot(
        int taskId, boolean isLowResolution, boolean takeSnapshotIfNeeded) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public TaskSnapshot takeTaskSnapshot(int taskId, boolean updateCache) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public int getLastResumedActivityUserId() throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public boolean updateConfiguration(Configuration values) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void updateLockTaskFeatures(int userId, int flags) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void registerRemoteAnimationForNextActivityStart(
        String packageName, RemoteAnimationAdapter adapter, IBinder launchCookie)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void registerRemoteTransitionForNextActivityStart(
        String packageName,
        RemoteAnimationAdapter adapter,
        IBinder launchCookie,
        RemoteTransition remoteTransiton)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void registerRemoteAnimationsForDisplay(
        int displayId, RemoteAnimationDefinition definition) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName activity)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setVrThread(int tid) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setPersistentVrThread(int tid) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void stopAppSwitches() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void resumeAppSwitches() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setActivityController(IActivityController watcher, boolean imAMonkey)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setVoiceKeepAwake(IVoiceInteractionSession session, boolean keepAwake)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int getPackageScreenCompatMode(String packageName) throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean getPackageAskScreenCompat(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void setPackageAskScreenCompat(String packageName, boolean ask) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void clearLaunchParamsForPackages(List<String> packageNames) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void onSplashScreenViewCopyFinished(
        int taskId, SplashScreenView.SplashScreenViewParcelable material) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void onPictureInPictureStateChanged(PictureInPictureUiState pipState)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void detachNavigationBarFromApp(IBinder transition) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean clearRecentTasks(int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void setRunningRemoteTransitionDelegate(IApplicationThread caller)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public BackNavigationInfo startBackNavigation(
        RemoteCallback navigationObserver, BackAnimationAdapter adaptor) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void registerScreenCaptureObserver(
        IBinder activityToken, IScreenCaptureObserver observer) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void unregisterScreenCaptureObserver(
        IBinder activityToken, IScreenCaptureObserver observer) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public IFoldStarManager getFoldStarManagerService() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public Bitmap getResumedTaskThumbnail(int taskId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(
        String packageName, int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void resetUserPackageSettings(int userId, int flags) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void sendSaLoggingBroadcast(
        String trackingId,
        String eventId,
        String detail,
        long value,
        Map<String, String> customDimension)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void sendSaLoggingBroadcastForSetting(String trackingId, String settingId, String value)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setCustomSplashScreenTheme(String packageName, String themeName, int userId)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setCutoutPolicy(int userId, String packageName, int policy)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int getCutoutPolicy(int userId, String packageName) throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void requestWaitingForOccluding(int displayId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public String getScpmVersion() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void setOrientationControlPolicy(int userId, String packageName, int policy)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int getOrientationControlPolicy(int userId, String packageName) throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void setOrientationControlDefault(boolean enabled) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setDisallowWhenLandscape(boolean disallow) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void startAppLockService(
        IBinder token, Intent intent, boolean showWhenLocked, String pkgName)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public List<String> getAppLockedPackageList() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void setAppLockedUnLockPackage(String packageName) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean isAppLockedPackage(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void clearAppLockedUnLockedApp() throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public String getAppLockedLockType() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public String getAppLockedCheckAction() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void setAppLockedVerifying(String pkgName, boolean verifying) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean isAppLockedVerifying(String pkgName) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public void setApplockLockedAppsPackage(String packages) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setApplockLockedAppsClass(String classes) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setApplockType(int type) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setApplockEnabled(boolean enable) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void setSsecureHiddenAppsPackages(String packages) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public String getApplockLockedAppsPackage() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public String getApplockLockedAppsClass() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public int getApplockType() throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isApplockEnabled() throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public String getSsecureHiddenAppsPackages() throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public void setBoundsCompatAlignment(int alignment) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public int getBoundsCompatAlignment() throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void setUseLetterbox(boolean useLetterbox) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public boolean hasWallpaperBackgroundForLetterbox(int displayId) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public List<String> getCoverLauncherAvailableAppList(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public Map getCoverLauncherEnabledAppList(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public Map getCoverLauncherEnabledAppListByType(int userId, int type) throws RemoteException {
      return null;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isPackageEnabledForCoverLauncher(String packageName, int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public boolean isPackageSettingsEnabledForCoverLauncher(
        String packageName, int userId, int type) throws RemoteException {
      return false;
    }

    @Override // android.app.IActivityTaskManager
    public int setCoverLauncherPackageEnabled(String packageName, int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public int setCoverLauncherPackageDisabled(String packageName, int userId)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.IActivityTaskManager
    public void startActivityForCoverLauncher(Intent intent, String requestReason)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void startActivityForCoverLauncherAsUser(Intent intent, String requestReason, int userId)
        throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void startActivityForDexRestart(int taskId) throws RemoteException {}

    @Override // android.app.IActivityTaskManager
    public void registKeyEventListener(IKeyEventListener listener) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IActivityTaskManager {
    static final int TRANSACTION_addAppTask = 43;
    static final int TRANSACTION_alwaysShowUnsupportedCompileSdkWarning = 79;
    static final int TRANSACTION_cancelRecentsAnimation = 35;
    static final int TRANSACTION_cancelTaskWindowTransition = 70;
    static final int TRANSACTION_clearAppLockedUnLockedApp = 119;
    static final int TRANSACTION_clearLaunchParamsForPackages = 90;
    static final int TRANSACTION_clearRecentTasks = 94;
    static final int TRANSACTION_detachNavigationBarFromApp = 93;
    static final int TRANSACTION_finishVoiceTask = 42;
    static final int TRANSACTION_focusTopTask = 34;
    static final int TRANSACTION_getActivityClientController = 18;
    static final int TRANSACTION_getAllRootTaskInfos = 55;
    static final int TRANSACTION_getAllRootTaskInfosOnDisplay = 57;
    static final int TRANSACTION_getAppLockedCheckAction = 121;
    static final int TRANSACTION_getAppLockedLockType = 120;
    static final int TRANSACTION_getAppLockedPackageList = 116;
    static final int TRANSACTION_getAppTaskThumbnailSize = 44;
    static final int TRANSACTION_getAppTasks = 39;
    static final int TRANSACTION_getApplockLockedAppsClass = 130;
    static final int TRANSACTION_getApplockLockedAppsPackage = 129;
    static final int TRANSACTION_getApplockType = 131;
    static final int TRANSACTION_getAssistContextExtras = 60;
    static final int TRANSACTION_getBoundsCompatAlignment = 135;
    static final int TRANSACTION_getCompatChangeablePackageInfoList = 102;
    static final int TRANSACTION_getCoverLauncherAvailableAppList = 138;
    static final int TRANSACTION_getCoverLauncherEnabledAppList = 139;
    static final int TRANSACTION_getCoverLauncherEnabledAppListByType = 140;
    static final int TRANSACTION_getCutoutPolicy = 108;
    static final int TRANSACTION_getDeviceConfigurationInfo = 69;
    static final int TRANSACTION_getFocusedRootTaskInfo = 32;
    static final int TRANSACTION_getFoldStarManagerService = 100;
    static final int TRANSACTION_getFrontActivityScreenCompatMode = 19;
    static final int TRANSACTION_getLastResumedActivityUserId = 73;
    static final int TRANSACTION_getLockTaskModeState = 38;
    static final int TRANSACTION_getMultiTaskingBinder = 99;
    static final int TRANSACTION_getOrientationControlPolicy = 112;
    static final int TRANSACTION_getPackageAskScreenCompat = 88;
    static final int TRANSACTION_getPackageScreenCompatMode = 86;
    static final int TRANSACTION_getRecentTasks = 27;
    static final int TRANSACTION_getResumedTaskThumbnail = 101;
    static final int TRANSACTION_getRootTaskInfo = 56;
    static final int TRANSACTION_getRootTaskInfoOnDisplay = 58;
    static final int TRANSACTION_getScpmVersion = 110;
    static final int TRANSACTION_getSsecureHiddenAppsPackages = 133;
    static final int TRANSACTION_getTaskBounds = 33;
    static final int TRANSACTION_getTaskDescription = 29;
    static final int TRANSACTION_getTaskDescriptionIcon = 46;
    static final int TRANSACTION_getTaskSnapshot = 71;
    static final int TRANSACTION_getTasks = 25;
    static final int TRANSACTION_getVoiceInteractorPackageName = 10;
    static final int TRANSACTION_getWindowOrganizerController = 67;
    static final int TRANSACTION_hasWallpaperBackgroundForLetterbox = 137;
    static final int TRANSACTION_isActivityStartAllowedOnDisplay = 16;
    static final int TRANSACTION_isAppLockedPackage = 118;
    static final int TRANSACTION_isAppLockedVerifying = 123;
    static final int TRANSACTION_isApplockEnabled = 132;
    static final int TRANSACTION_isAssistDataAllowedOnCurrentActivity = 63;
    static final int TRANSACTION_isInLockTaskMode = 37;
    static final int TRANSACTION_isPackageEnabledForCoverLauncher = 141;
    static final int TRANSACTION_isPackageSettingsEnabledForCoverLauncher = 142;
    static final int TRANSACTION_isTopActivityImmersive = 28;
    static final int TRANSACTION_keyguardGoingAway = 65;
    static final int TRANSACTION_moveRootTaskToDisplay = 51;
    static final int TRANSACTION_moveTaskToFront = 26;
    static final int TRANSACTION_moveTaskToRootTask = 52;
    static final int TRANSACTION_onPictureInPictureStateChanged = 92;
    static final int TRANSACTION_onSplashScreenViewCopyFinished = 91;
    static final int TRANSACTION_registKeyEventListener = 148;
    static final int TRANSACTION_registerRemoteAnimationForNextActivityStart = 76;
    static final int TRANSACTION_registerRemoteAnimationsForDisplay = 78;
    static final int TRANSACTION_registerRemoteTransitionForNextActivityStart = 77;
    static final int TRANSACTION_registerScreenCaptureObserver = 97;
    static final int TRANSACTION_registerTaskStackListener = 47;
    static final int TRANSACTION_releaseSomeActivities = 45;
    static final int TRANSACTION_removeAllVisibleRecentTasks = 24;
    static final int TRANSACTION_removeRootTasksInWindowingModes = 53;
    static final int TRANSACTION_removeRootTasksWithActivityTypes = 54;
    static final int TRANSACTION_removeTask = 22;
    static final int TRANSACTION_removeTaskWithFlags = 23;
    static final int TRANSACTION_reportAssistContextExtras = 30;
    static final int TRANSACTION_requestAssistContextExtras = 61;
    static final int TRANSACTION_requestAssistDataForTask = 64;
    static final int TRANSACTION_requestAutofillData = 62;
    static final int TRANSACTION_requestWaitingForOccluding = 109;
    static final int TRANSACTION_resetUserPackageSettings = 103;
    static final int TRANSACTION_resizeTask = 50;
    static final int TRANSACTION_resumeAppSwitches = 83;
    static final int TRANSACTION_sendSaLoggingBroadcast = 104;
    static final int TRANSACTION_sendSaLoggingBroadcastForSetting = 105;
    static final int TRANSACTION_setActivityController = 84;
    static final int TRANSACTION_setAppLockedUnLockPackage = 117;
    static final int TRANSACTION_setAppLockedVerifying = 122;
    static final int TRANSACTION_setApplockEnabled = 127;
    static final int TRANSACTION_setApplockLockedAppsClass = 125;
    static final int TRANSACTION_setApplockLockedAppsPackage = 124;
    static final int TRANSACTION_setApplockType = 126;
    static final int TRANSACTION_setBoundsCompatAlignment = 134;
    static final int TRANSACTION_setCoverLauncherPackageDisabled = 144;
    static final int TRANSACTION_setCoverLauncherPackageEnabled = 143;
    static final int TRANSACTION_setCustomSplashScreenTheme = 106;
    static final int TRANSACTION_setCutoutPolicy = 107;
    static final int TRANSACTION_setDisallowWhenLandscape = 114;
    static final int TRANSACTION_setFocusedRootTask = 31;
    static final int TRANSACTION_setFocusedTask = 21;
    static final int TRANSACTION_setFrontActivityScreenCompatMode = 20;
    static final int TRANSACTION_setLockScreenShown = 59;
    static final int TRANSACTION_setOrientationControlDefault = 113;
    static final int TRANSACTION_setOrientationControlPolicy = 111;
    static final int TRANSACTION_setPackageAskScreenCompat = 89;
    static final int TRANSACTION_setPackageScreenCompatMode = 87;
    static final int TRANSACTION_setPersistentVrThread = 81;
    static final int TRANSACTION_setRunningRemoteTransitionDelegate = 95;
    static final int TRANSACTION_setSsecureHiddenAppsPackages = 128;
    static final int TRANSACTION_setTaskResizeable = 49;
    static final int TRANSACTION_setUseLetterbox = 136;
    static final int TRANSACTION_setVoiceKeepAwake = 85;
    static final int TRANSACTION_setVrThread = 80;
    static final int TRANSACTION_startActivities = 2;
    static final int TRANSACTION_startActivity = 1;
    static final int TRANSACTION_startActivityAndWait = 7;
    static final int TRANSACTION_startActivityAsCaller = 15;
    static final int TRANSACTION_startActivityAsUser = 3;
    static final int TRANSACTION_startActivityForCoverLauncher = 145;
    static final int TRANSACTION_startActivityForCoverLauncherAsUser = 146;
    static final int TRANSACTION_startActivityForDexRestart = 147;
    static final int TRANSACTION_startActivityFromGameSession = 12;
    static final int TRANSACTION_startActivityFromRecents = 14;
    static final int TRANSACTION_startActivityIntentSender = 6;
    static final int TRANSACTION_startActivityWithConfig = 8;
    static final int TRANSACTION_startAppLockService = 115;
    static final int TRANSACTION_startAssistantActivity = 11;
    static final int TRANSACTION_startBackNavigation = 96;
    static final int TRANSACTION_startDreamActivity = 5;
    static final int TRANSACTION_startNextMatchingActivity = 4;
    static final int TRANSACTION_startRecentsActivity = 13;
    static final int TRANSACTION_startSystemLockTaskMode = 40;
    static final int TRANSACTION_startVoiceActivity = 9;
    static final int TRANSACTION_stopAppSwitches = 82;
    static final int TRANSACTION_stopSystemLockTaskMode = 41;
    static final int TRANSACTION_supportsLocalVoiceInteraction = 68;
    static final int TRANSACTION_suppressResizeConfigChanges = 66;
    static final int TRANSACTION_takeTaskSnapshot = 72;
    static final int TRANSACTION_unhandledBack = 17;
    static final int TRANSACTION_unregisterScreenCaptureObserver = 98;
    static final int TRANSACTION_unregisterTaskStackListener = 48;
    static final int TRANSACTION_updateConfiguration = 74;
    static final int TRANSACTION_updateLockTaskFeatures = 75;
    static final int TRANSACTION_updateLockTaskPackages = 36;

    public Stub() {
      attachInterface(this, IActivityTaskManager.DESCRIPTOR);
    }

    public static IActivityTaskManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IActivityTaskManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IActivityTaskManager)) {
        return (IActivityTaskManager) iin;
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
          return "startActivity";
        case 2:
          return "startActivities";
        case 3:
          return "startActivityAsUser";
        case 4:
          return "startNextMatchingActivity";
        case 5:
          return "startDreamActivity";
        case 6:
          return "startActivityIntentSender";
        case 7:
          return "startActivityAndWait";
        case 8:
          return "startActivityWithConfig";
        case 9:
          return "startVoiceActivity";
        case 10:
          return "getVoiceInteractorPackageName";
        case 11:
          return "startAssistantActivity";
        case 12:
          return "startActivityFromGameSession";
        case 13:
          return "startRecentsActivity";
        case 14:
          return "startActivityFromRecents";
        case 15:
          return "startActivityAsCaller";
        case 16:
          return "isActivityStartAllowedOnDisplay";
        case 17:
          return "unhandledBack";
        case 18:
          return "getActivityClientController";
        case 19:
          return "getFrontActivityScreenCompatMode";
        case 20:
          return "setFrontActivityScreenCompatMode";
        case 21:
          return "setFocusedTask";
        case 22:
          return "removeTask";
        case 23:
          return "removeTaskWithFlags";
        case 24:
          return "removeAllVisibleRecentTasks";
        case 25:
          return "getTasks";
        case 26:
          return "moveTaskToFront";
        case 27:
          return "getRecentTasks";
        case 28:
          return "isTopActivityImmersive";
        case 29:
          return "getTaskDescription";
        case 30:
          return "reportAssistContextExtras";
        case 31:
          return "setFocusedRootTask";
        case 32:
          return "getFocusedRootTaskInfo";
        case 33:
          return "getTaskBounds";
        case 34:
          return "focusTopTask";
        case 35:
          return "cancelRecentsAnimation";
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
          return "isAssistDataAllowedOnCurrentActivity";
        case 64:
          return "requestAssistDataForTask";
        case 65:
          return "keyguardGoingAway";
        case 66:
          return "suppressResizeConfigChanges";
        case 67:
          return "getWindowOrganizerController";
        case 68:
          return "supportsLocalVoiceInteraction";
        case 69:
          return "getDeviceConfigurationInfo";
        case 70:
          return "cancelTaskWindowTransition";
        case 71:
          return "getTaskSnapshot";
        case 72:
          return "takeTaskSnapshot";
        case 73:
          return "getLastResumedActivityUserId";
        case 74:
          return "updateConfiguration";
        case 75:
          return "updateLockTaskFeatures";
        case 76:
          return "registerRemoteAnimationForNextActivityStart";
        case 77:
          return "registerRemoteTransitionForNextActivityStart";
        case 78:
          return "registerRemoteAnimationsForDisplay";
        case 79:
          return "alwaysShowUnsupportedCompileSdkWarning";
        case 80:
          return "setVrThread";
        case 81:
          return "setPersistentVrThread";
        case 82:
          return "stopAppSwitches";
        case 83:
          return "resumeAppSwitches";
        case 84:
          return "setActivityController";
        case 85:
          return "setVoiceKeepAwake";
        case 86:
          return "getPackageScreenCompatMode";
        case 87:
          return "setPackageScreenCompatMode";
        case 88:
          return "getPackageAskScreenCompat";
        case 89:
          return "setPackageAskScreenCompat";
        case 90:
          return "clearLaunchParamsForPackages";
        case 91:
          return "onSplashScreenViewCopyFinished";
        case 92:
          return "onPictureInPictureStateChanged";
        case 93:
          return "detachNavigationBarFromApp";
        case 94:
          return "clearRecentTasks";
        case 95:
          return "setRunningRemoteTransitionDelegate";
        case 96:
          return "startBackNavigation";
        case 97:
          return "registerScreenCaptureObserver";
        case 98:
          return "unregisterScreenCaptureObserver";
        case 99:
          return "getMultiTaskingBinder";
        case 100:
          return "getFoldStarManagerService";
        case 101:
          return "getResumedTaskThumbnail";
        case 102:
          return "getCompatChangeablePackageInfoList";
        case 103:
          return "resetUserPackageSettings";
        case 104:
          return "sendSaLoggingBroadcast";
        case 105:
          return "sendSaLoggingBroadcastForSetting";
        case 106:
          return "setCustomSplashScreenTheme";
        case 107:
          return "setCutoutPolicy";
        case 108:
          return "getCutoutPolicy";
        case 109:
          return "requestWaitingForOccluding";
        case 110:
          return "getScpmVersion";
        case 111:
          return "setOrientationControlPolicy";
        case 112:
          return "getOrientationControlPolicy";
        case 113:
          return "setOrientationControlDefault";
        case 114:
          return "setDisallowWhenLandscape";
        case 115:
          return "startAppLockService";
        case 116:
          return "getAppLockedPackageList";
        case 117:
          return "setAppLockedUnLockPackage";
        case 118:
          return "isAppLockedPackage";
        case 119:
          return "clearAppLockedUnLockedApp";
        case 120:
          return "getAppLockedLockType";
        case 121:
          return "getAppLockedCheckAction";
        case 122:
          return "setAppLockedVerifying";
        case 123:
          return "isAppLockedVerifying";
        case 124:
          return "setApplockLockedAppsPackage";
        case 125:
          return "setApplockLockedAppsClass";
        case 126:
          return "setApplockType";
        case 127:
          return "setApplockEnabled";
        case 128:
          return "setSsecureHiddenAppsPackages";
        case 129:
          return "getApplockLockedAppsPackage";
        case 130:
          return "getApplockLockedAppsClass";
        case 131:
          return "getApplockType";
        case 132:
          return "isApplockEnabled";
        case 133:
          return "getSsecureHiddenAppsPackages";
        case 134:
          return "setBoundsCompatAlignment";
        case 135:
          return "getBoundsCompatAlignment";
        case 136:
          return "setUseLetterbox";
        case 137:
          return "hasWallpaperBackgroundForLetterbox";
        case 138:
          return "getCoverLauncherAvailableAppList";
        case 139:
          return "getCoverLauncherEnabledAppList";
        case 140:
          return "getCoverLauncherEnabledAppListByType";
        case 141:
          return "isPackageEnabledForCoverLauncher";
        case 142:
          return "isPackageSettingsEnabledForCoverLauncher";
        case 143:
          return "setCoverLauncherPackageEnabled";
        case 144:
          return "setCoverLauncherPackageDisabled";
        case 145:
          return "startActivityForCoverLauncher";
        case 146:
          return "startActivityForCoverLauncherAsUser";
        case 147:
          return "startActivityForDexRestart";
        case 148:
          return "registKeyEventListener";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, final Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IActivityTaskManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IActivityTaskManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IApplicationThread _arg0 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg1 = data.readString();
              String _arg2 = data.readString();
              Intent _arg3 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg4 = data.readString();
              IBinder _arg5 = data.readStrongBinder();
              String _arg6 = data.readString();
              int _arg7 = data.readInt();
              int _arg8 = data.readInt();
              ProfilerInfo _arg9 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
              Bundle _arg10 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              int _result =
                  startActivity(
                      _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              IApplicationThread _arg02 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg12 = data.readString();
              String _arg22 = data.readString();
              Intent[] _arg32 = (Intent[]) data.createTypedArray(Intent.CREATOR);
              String[] _arg42 = data.createStringArray();
              IBinder _arg52 = data.readStrongBinder();
              Bundle _arg62 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg72 = data.readInt();
              data.enforceNoDataAvail();
              int _result2 =
                  startActivities(_arg02, _arg12, _arg22, _arg32, _arg42, _arg52, _arg62, _arg72);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              IApplicationThread _arg03 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg13 = data.readString();
              String _arg23 = data.readString();
              Intent _arg33 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg43 = data.readString();
              IBinder _arg53 = data.readStrongBinder();
              String _arg63 = data.readString();
              int _arg73 = data.readInt();
              int _arg82 = data.readInt();
              ProfilerInfo _arg92 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
              Bundle _arg102 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg11 = data.readInt();
              data.enforceNoDataAvail();
              int _result3 =
                  startActivityAsUser(
                      _arg03, _arg13, _arg23, _arg33, _arg43, _arg53, _arg63, _arg73, _arg82,
                      _arg92, _arg102, _arg11);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 4:
              IBinder _arg04 = data.readStrongBinder();
              Intent _arg14 = (Intent) data.readTypedObject(Intent.CREATOR);
              Bundle _arg24 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              boolean _result4 = startNextMatchingActivity(_arg04, _arg14, _arg24);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              Intent _arg05 = (Intent) data.readTypedObject(Intent.CREATOR);
              data.enforceNoDataAvail();
              boolean _result5 = startDreamActivity(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 6:
              IApplicationThread _arg06 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              IIntentSender _arg15 = IIntentSender.Stub.asInterface(data.readStrongBinder());
              IBinder _arg25 = data.readStrongBinder();
              Intent _arg34 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg44 = data.readString();
              IBinder _arg54 = data.readStrongBinder();
              String _arg64 = data.readString();
              int _arg74 = data.readInt();
              int _arg83 = data.readInt();
              int _arg93 = data.readInt();
              Bundle _arg103 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              int _result6 =
                  startActivityIntentSender(
                      _arg06, _arg15, _arg25, _arg34, _arg44, _arg54, _arg64, _arg74, _arg83,
                      _arg93, _arg103);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 7:
              IApplicationThread _arg07 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg16 = data.readString();
              String _arg26 = data.readString();
              Intent _arg35 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg45 = data.readString();
              IBinder _arg55 = data.readStrongBinder();
              String _arg65 = data.readString();
              int _arg75 = data.readInt();
              int _arg84 = data.readInt();
              ProfilerInfo _arg94 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
              Bundle _arg104 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              WaitResult _result7 =
                  startActivityAndWait(
                      _arg07, _arg16, _arg26, _arg35, _arg45, _arg55, _arg65, _arg75, _arg84,
                      _arg94, _arg104, _arg112);
              reply.writeNoException();
              reply.writeTypedObject(_result7, 1);
              return true;
            case 8:
              IApplicationThread _arg08 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg17 = data.readString();
              String _arg27 = data.readString();
              Intent _arg36 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg46 = data.readString();
              IBinder _arg56 = data.readStrongBinder();
              String _arg66 = data.readString();
              int _arg76 = data.readInt();
              int _arg85 = data.readInt();
              Configuration _arg95 = (Configuration) data.readTypedObject(Configuration.CREATOR);
              Bundle _arg105 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg113 = data.readInt();
              data.enforceNoDataAvail();
              int _result8 =
                  startActivityWithConfig(
                      _arg08, _arg17, _arg27, _arg36, _arg46, _arg56, _arg66, _arg76, _arg85,
                      _arg95, _arg105, _arg113);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              String _arg09 = data.readString();
              String _arg18 = data.readString();
              int _arg28 = data.readInt();
              int _arg37 = data.readInt();
              Intent _arg47 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg57 = data.readString();
              IVoiceInteractionSession _arg67 =
                  IVoiceInteractionSession.Stub.asInterface(data.readStrongBinder());
              IVoiceInteractor _arg77 = IVoiceInteractor.Stub.asInterface(data.readStrongBinder());
              int _arg86 = data.readInt();
              ProfilerInfo _arg96 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
              Bundle _arg106 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg114 = data.readInt();
              data.enforceNoDataAvail();
              int _result9 =
                  startVoiceActivity(
                      _arg09, _arg18, _arg28, _arg37, _arg47, _arg57, _arg67, _arg77, _arg86,
                      _arg96, _arg106, _arg114);
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 10:
              IBinder _arg010 = data.readStrongBinder();
              data.enforceNoDataAvail();
              String _result10 = getVoiceInteractorPackageName(_arg010);
              reply.writeNoException();
              reply.writeString(_result10);
              return true;
            case 11:
              String _arg011 = data.readString();
              String _arg19 = data.readString();
              int _arg29 = data.readInt();
              int _arg38 = data.readInt();
              Intent _arg48 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg58 = data.readString();
              Bundle _arg68 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg78 = data.readInt();
              data.enforceNoDataAvail();
              int _result11 =
                  startAssistantActivity(
                      _arg011, _arg19, _arg29, _arg38, _arg48, _arg58, _arg68, _arg78);
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 12:
              IApplicationThread _arg012 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg110 = data.readString();
              String _arg210 = data.readString();
              int _arg39 = data.readInt();
              int _arg49 = data.readInt();
              Intent _arg59 = (Intent) data.readTypedObject(Intent.CREATOR);
              int _arg69 = data.readInt();
              int _arg79 = data.readInt();
              data.enforceNoDataAvail();
              int _result12 =
                  startActivityFromGameSession(
                      _arg012, _arg110, _arg210, _arg39, _arg49, _arg59, _arg69, _arg79);
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 13:
              Intent _arg013 = (Intent) data.readTypedObject(Intent.CREATOR);
              long _arg111 = data.readLong();
              IRecentsAnimationRunner _arg211 =
                  IRecentsAnimationRunner.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              startRecentsActivity(_arg013, _arg111, _arg211);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              Bundle _arg115 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              int _result13 = startActivityFromRecents(_arg014, _arg115);
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 15:
              IApplicationThread _arg015 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg116 = data.readString();
              Intent _arg212 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg310 = data.readString();
              IBinder _arg410 = data.readStrongBinder();
              String _arg510 = data.readString();
              int _arg610 = data.readInt();
              int _arg710 = data.readInt();
              ProfilerInfo _arg87 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
              Bundle _arg97 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              boolean _arg107 = data.readBoolean();
              int _arg117 = data.readInt();
              data.enforceNoDataAvail();
              int _result14 =
                  startActivityAsCaller(
                      _arg015, _arg116, _arg212, _arg310, _arg410, _arg510, _arg610, _arg710,
                      _arg87, _arg97, _arg107, _arg117);
              reply.writeNoException();
              reply.writeInt(_result14);
              return true;
            case 16:
              int _arg016 = data.readInt();
              Intent _arg118 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg213 = data.readString();
              int _arg311 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result15 =
                  isActivityStartAllowedOnDisplay(_arg016, _arg118, _arg213, _arg311);
              reply.writeNoException();
              reply.writeBoolean(_result15);
              return true;
            case 17:
              unhandledBack();
              reply.writeNoException();
              return true;
            case 18:
              IActivityClientController _result16 = getActivityClientController();
              reply.writeNoException();
              reply.writeStrongInterface(_result16);
              return true;
            case 19:
              int _result17 = getFrontActivityScreenCompatMode();
              reply.writeNoException();
              reply.writeInt(_result17);
              return true;
            case 20:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              setFrontActivityScreenCompatMode(_arg017);
              reply.writeNoException();
              return true;
            case 21:
              int _arg018 = data.readInt();
              data.enforceNoDataAvail();
              setFocusedTask(_arg018);
              reply.writeNoException();
              return true;
            case 22:
              int _arg019 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result18 = removeTask(_arg019);
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 23:
              int _arg020 = data.readInt();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result19 = removeTaskWithFlags(_arg020, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 24:
              removeAllVisibleRecentTasks();
              reply.writeNoException();
              return true;
            case 25:
              int _arg021 = data.readInt();
              boolean _arg120 = data.readBoolean();
              boolean _arg214 = data.readBoolean();
              int _arg312 = data.readInt();
              data.enforceNoDataAvail();
              List<ActivityManager.RunningTaskInfo> _result20 =
                  getTasks(_arg021, _arg120, _arg214, _arg312);
              reply.writeNoException();
              reply.writeTypedList(_result20, 1);
              return true;
            case 26:
              IApplicationThread _arg022 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              String _arg121 = data.readString();
              int _arg215 = data.readInt();
              int _arg313 = data.readInt();
              Bundle _arg411 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              moveTaskToFront(_arg022, _arg121, _arg215, _arg313, _arg411);
              reply.writeNoException();
              return true;
            case 27:
              int _arg023 = data.readInt();
              int _arg122 = data.readInt();
              int _arg216 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice<ActivityManager.RecentTaskInfo> _result21 =
                  getRecentTasks(_arg023, _arg122, _arg216);
              reply.writeNoException();
              reply.writeTypedObject(_result21, 1);
              return true;
            case 28:
              boolean _result22 = isTopActivityImmersive();
              reply.writeNoException();
              reply.writeBoolean(_result22);
              return true;
            case 29:
              int _arg024 = data.readInt();
              data.enforceNoDataAvail();
              ActivityManager.TaskDescription _result23 = getTaskDescription(_arg024);
              reply.writeNoException();
              reply.writeTypedObject(_result23, 1);
              return true;
            case 30:
              IBinder _arg025 = data.readStrongBinder();
              Bundle _arg123 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              AssistStructure _arg217 =
                  (AssistStructure) data.readTypedObject(AssistStructure.CREATOR);
              AssistContent _arg314 = (AssistContent) data.readTypedObject(AssistContent.CREATOR);
              Uri _arg412 = (Uri) data.readTypedObject(Uri.CREATOR);
              data.enforceNoDataAvail();
              reportAssistContextExtras(_arg025, _arg123, _arg217, _arg314, _arg412);
              reply.writeNoException();
              return true;
            case 31:
              int _arg026 = data.readInt();
              data.enforceNoDataAvail();
              setFocusedRootTask(_arg026);
              reply.writeNoException();
              return true;
            case 32:
              ActivityTaskManager.RootTaskInfo _result24 = getFocusedRootTaskInfo();
              reply.writeNoException();
              reply.writeTypedObject(_result24, 1);
              return true;
            case 33:
              int _arg027 = data.readInt();
              data.enforceNoDataAvail();
              Rect _result25 = getTaskBounds(_arg027);
              reply.writeNoException();
              reply.writeTypedObject(_result25, 1);
              return true;
            case 34:
              int _arg028 = data.readInt();
              data.enforceNoDataAvail();
              focusTopTask(_arg028);
              reply.writeNoException();
              return true;
            case 35:
              boolean _arg029 = data.readBoolean();
              data.enforceNoDataAvail();
              cancelRecentsAnimation(_arg029);
              reply.writeNoException();
              return true;
            case 36:
              int _arg030 = data.readInt();
              String[] _arg124 = data.createStringArray();
              data.enforceNoDataAvail();
              updateLockTaskPackages(_arg030, _arg124);
              reply.writeNoException();
              return true;
            case 37:
              boolean _result26 = isInLockTaskMode();
              reply.writeNoException();
              reply.writeBoolean(_result26);
              return true;
            case 38:
              int _result27 = getLockTaskModeState();
              reply.writeNoException();
              reply.writeInt(_result27);
              return true;
            case 39:
              String _arg031 = data.readString();
              data.enforceNoDataAvail();
              List<IBinder> _result28 = getAppTasks(_arg031);
              reply.writeNoException();
              reply.writeBinderList(_result28);
              return true;
            case 40:
              int _arg032 = data.readInt();
              data.enforceNoDataAvail();
              startSystemLockTaskMode(_arg032);
              reply.writeNoException();
              return true;
            case 41:
              stopSystemLockTaskMode();
              reply.writeNoException();
              return true;
            case 42:
              IBinder _arg033 = data.readStrongBinder();
              IVoiceInteractionSession _arg034 = IVoiceInteractionSession.Stub.asInterface(_arg033);
              data.enforceNoDataAvail();
              finishVoiceTask(_arg034);
              reply.writeNoException();
              return true;
            case 43:
              IBinder _arg035 = data.readStrongBinder();
              Intent _arg125 = (Intent) data.readTypedObject(Intent.CREATOR);
              ActivityManager.TaskDescription _arg218 =
                  (ActivityManager.TaskDescription)
                      data.readTypedObject(ActivityManager.TaskDescription.CREATOR);
              Bitmap _arg315 = (Bitmap) data.readTypedObject(Bitmap.CREATOR);
              data.enforceNoDataAvail();
              int _result29 = addAppTask(_arg035, _arg125, _arg218, _arg315);
              reply.writeNoException();
              reply.writeInt(_result29);
              return true;
            case 44:
              Point _result30 = getAppTaskThumbnailSize();
              reply.writeNoException();
              reply.writeTypedObject(_result30, 1);
              return true;
            case 45:
              IApplicationThread _arg036 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              releaseSomeActivities(_arg036);
              return true;
            case 46:
              String _arg037 = data.readString();
              int _arg126 = data.readInt();
              data.enforceNoDataAvail();
              Bitmap _result31 = getTaskDescriptionIcon(_arg037, _arg126);
              reply.writeNoException();
              reply.writeTypedObject(_result31, 1);
              return true;
            case 47:
              ITaskStackListener _arg038 =
                  ITaskStackListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerTaskStackListener(_arg038);
              reply.writeNoException();
              return true;
            case 48:
              ITaskStackListener _arg039 =
                  ITaskStackListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterTaskStackListener(_arg039);
              reply.writeNoException();
              return true;
            case 49:
              int _arg040 = data.readInt();
              int _arg127 = data.readInt();
              data.enforceNoDataAvail();
              setTaskResizeable(_arg040, _arg127);
              reply.writeNoException();
              return true;
            case 50:
              int _arg041 = data.readInt();
              Rect _arg128 = (Rect) data.readTypedObject(Rect.CREATOR);
              int _arg219 = data.readInt();
              data.enforceNoDataAvail();
              resizeTask(_arg041, _arg128, _arg219);
              reply.writeNoException();
              return true;
            case 51:
              int _arg042 = data.readInt();
              int _arg129 = data.readInt();
              data.enforceNoDataAvail();
              moveRootTaskToDisplay(_arg042, _arg129);
              reply.writeNoException();
              return true;
            case 52:
              int _arg043 = data.readInt();
              int _arg130 = data.readInt();
              boolean _arg220 = data.readBoolean();
              data.enforceNoDataAvail();
              moveTaskToRootTask(_arg043, _arg130, _arg220);
              reply.writeNoException();
              return true;
            case 53:
              int[] _arg044 = data.createIntArray();
              data.enforceNoDataAvail();
              removeRootTasksInWindowingModes(_arg044);
              reply.writeNoException();
              return true;
            case 54:
              int[] _arg045 = data.createIntArray();
              data.enforceNoDataAvail();
              removeRootTasksWithActivityTypes(_arg045);
              reply.writeNoException();
              return true;
            case 55:
              List<ActivityTaskManager.RootTaskInfo> _result32 = getAllRootTaskInfos();
              reply.writeNoException();
              reply.writeTypedList(_result32, 1);
              return true;
            case 56:
              int _arg046 = data.readInt();
              int _arg131 = data.readInt();
              data.enforceNoDataAvail();
              ActivityTaskManager.RootTaskInfo _result33 = getRootTaskInfo(_arg046, _arg131);
              reply.writeNoException();
              reply.writeTypedObject(_result33, 1);
              return true;
            case 57:
              int _arg047 = data.readInt();
              data.enforceNoDataAvail();
              List<ActivityTaskManager.RootTaskInfo> _result34 =
                  getAllRootTaskInfosOnDisplay(_arg047);
              reply.writeNoException();
              reply.writeTypedList(_result34, 1);
              return true;
            case 58:
              int _arg048 = data.readInt();
              int _arg132 = data.readInt();
              int _arg221 = data.readInt();
              data.enforceNoDataAvail();
              ActivityTaskManager.RootTaskInfo _result35 =
                  getRootTaskInfoOnDisplay(_arg048, _arg132, _arg221);
              reply.writeNoException();
              reply.writeTypedObject(_result35, 1);
              return true;
            case 59:
              boolean _arg049 = data.readBoolean();
              boolean _arg133 = data.readBoolean();
              data.enforceNoDataAvail();
              setLockScreenShown(_arg049, _arg133);
              return true;
            case 60:
              int _arg050 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result36 = getAssistContextExtras(_arg050);
              reply.writeNoException();
              reply.writeTypedObject(_result36, 1);
              return true;
            case 61:
              int _arg051 = data.readInt();
              IAssistDataReceiver _arg134 =
                  IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
              Bundle _arg222 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              IBinder _arg316 = data.readStrongBinder();
              boolean _arg413 = data.readBoolean();
              boolean _arg511 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result37 =
                  requestAssistContextExtras(_arg051, _arg134, _arg222, _arg316, _arg413, _arg511);
              reply.writeNoException();
              reply.writeBoolean(_result37);
              return true;
            case 62:
              IAssistDataReceiver _arg052 =
                  IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
              Bundle _arg135 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              IBinder _arg223 = data.readStrongBinder();
              int _arg317 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result38 = requestAutofillData(_arg052, _arg135, _arg223, _arg317);
              reply.writeNoException();
              reply.writeBoolean(_result38);
              return true;
            case 63:
              boolean _result39 = isAssistDataAllowedOnCurrentActivity();
              reply.writeNoException();
              reply.writeBoolean(_result39);
              return true;
            case 64:
              IAssistDataReceiver _arg053 =
                  IAssistDataReceiver.Stub.asInterface(data.readStrongBinder());
              int _arg136 = data.readInt();
              String _arg224 = data.readString();
              String _arg318 = data.readString();
              data.enforceNoDataAvail();
              boolean _result40 = requestAssistDataForTask(_arg053, _arg136, _arg224, _arg318);
              reply.writeNoException();
              reply.writeBoolean(_result40);
              return true;
            case 65:
              int _arg054 = data.readInt();
              data.enforceNoDataAvail();
              keyguardGoingAway(_arg054);
              return true;
            case 66:
              boolean _arg055 = data.readBoolean();
              data.enforceNoDataAvail();
              suppressResizeConfigChanges(_arg055);
              reply.writeNoException();
              return true;
            case 67:
              IWindowOrganizerController _result41 = getWindowOrganizerController();
              reply.writeNoException();
              reply.writeStrongInterface(_result41);
              return true;
            case 68:
              boolean _result42 = supportsLocalVoiceInteraction();
              reply.writeNoException();
              reply.writeBoolean(_result42);
              return true;
            case 69:
              ConfigurationInfo _result43 = getDeviceConfigurationInfo();
              reply.writeNoException();
              reply.writeTypedObject(_result43, 1);
              return true;
            case 70:
              int _arg056 = data.readInt();
              data.enforceNoDataAvail();
              cancelTaskWindowTransition(_arg056);
              reply.writeNoException();
              return true;
            case 71:
              int _arg057 = data.readInt();
              boolean _arg137 = data.readBoolean();
              boolean _arg225 = data.readBoolean();
              data.enforceNoDataAvail();
              TaskSnapshot _result44 = getTaskSnapshot(_arg057, _arg137, _arg225);
              reply.writeNoException();
              reply.writeTypedObject(_result44, 1);
              return true;
            case 72:
              int _arg058 = data.readInt();
              boolean _arg138 = data.readBoolean();
              data.enforceNoDataAvail();
              TaskSnapshot _result45 = takeTaskSnapshot(_arg058, _arg138);
              reply.writeNoException();
              reply.writeTypedObject(_result45, 1);
              return true;
            case 73:
              int _result46 = getLastResumedActivityUserId();
              reply.writeNoException();
              reply.writeInt(_result46);
              return true;
            case 74:
              Configuration _arg059 = (Configuration) data.readTypedObject(Configuration.CREATOR);
              data.enforceNoDataAvail();
              boolean _result47 = updateConfiguration(_arg059);
              reply.writeNoException();
              reply.writeBoolean(_result47);
              return true;
            case 75:
              int _arg060 = data.readInt();
              int _arg139 = data.readInt();
              data.enforceNoDataAvail();
              updateLockTaskFeatures(_arg060, _arg139);
              reply.writeNoException();
              return true;
            case 76:
              String _arg061 = data.readString();
              RemoteAnimationAdapter _arg140 =
                  (RemoteAnimationAdapter) data.readTypedObject(RemoteAnimationAdapter.CREATOR);
              IBinder _arg226 = data.readStrongBinder();
              data.enforceNoDataAvail();
              registerRemoteAnimationForNextActivityStart(_arg061, _arg140, _arg226);
              reply.writeNoException();
              return true;
            case 77:
              String _arg062 = data.readString();
              RemoteAnimationAdapter _arg141 =
                  (RemoteAnimationAdapter) data.readTypedObject(RemoteAnimationAdapter.CREATOR);
              IBinder _arg227 = data.readStrongBinder();
              RemoteTransition _arg319 =
                  (RemoteTransition) data.readTypedObject(RemoteTransition.CREATOR);
              data.enforceNoDataAvail();
              registerRemoteTransitionForNextActivityStart(_arg062, _arg141, _arg227, _arg319);
              reply.writeNoException();
              return true;
            case 78:
              int _arg063 = data.readInt();
              RemoteAnimationDefinition _arg142 =
                  (RemoteAnimationDefinition)
                      data.readTypedObject(RemoteAnimationDefinition.CREATOR);
              data.enforceNoDataAvail();
              registerRemoteAnimationsForDisplay(_arg063, _arg142);
              reply.writeNoException();
              return true;
            case 79:
              ComponentName _arg064 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              alwaysShowUnsupportedCompileSdkWarning(_arg064);
              reply.writeNoException();
              return true;
            case 80:
              int _arg065 = data.readInt();
              data.enforceNoDataAvail();
              setVrThread(_arg065);
              reply.writeNoException();
              return true;
            case 81:
              int _arg066 = data.readInt();
              data.enforceNoDataAvail();
              setPersistentVrThread(_arg066);
              reply.writeNoException();
              return true;
            case 82:
              stopAppSwitches();
              reply.writeNoException();
              return true;
            case 83:
              resumeAppSwitches();
              reply.writeNoException();
              return true;
            case 84:
              IActivityController _arg067 =
                  IActivityController.Stub.asInterface(data.readStrongBinder());
              boolean _arg143 = data.readBoolean();
              data.enforceNoDataAvail();
              setActivityController(_arg067, _arg143);
              reply.writeNoException();
              return true;
            case 85:
              IVoiceInteractionSession _arg068 =
                  IVoiceInteractionSession.Stub.asInterface(data.readStrongBinder());
              boolean _arg144 = data.readBoolean();
              data.enforceNoDataAvail();
              setVoiceKeepAwake(_arg068, _arg144);
              reply.writeNoException();
              return true;
            case 86:
              String _arg069 = data.readString();
              data.enforceNoDataAvail();
              int _result48 = getPackageScreenCompatMode(_arg069);
              reply.writeNoException();
              reply.writeInt(_result48);
              return true;
            case 87:
              String _arg070 = data.readString();
              int _arg145 = data.readInt();
              data.enforceNoDataAvail();
              setPackageScreenCompatMode(_arg070, _arg145);
              reply.writeNoException();
              return true;
            case 88:
              String _arg071 = data.readString();
              data.enforceNoDataAvail();
              boolean _result49 = getPackageAskScreenCompat(_arg071);
              reply.writeNoException();
              reply.writeBoolean(_result49);
              return true;
            case 89:
              String _arg072 = data.readString();
              boolean _arg146 = data.readBoolean();
              data.enforceNoDataAvail();
              setPackageAskScreenCompat(_arg072, _arg146);
              reply.writeNoException();
              return true;
            case 90:
              List<String> _arg073 = data.createStringArrayList();
              data.enforceNoDataAvail();
              clearLaunchParamsForPackages(_arg073);
              reply.writeNoException();
              return true;
            case 91:
              int _arg074 = data.readInt();
              SplashScreenView.SplashScreenViewParcelable _arg147 =
                  (SplashScreenView.SplashScreenViewParcelable)
                      data.readTypedObject(SplashScreenView.SplashScreenViewParcelable.CREATOR);
              data.enforceNoDataAvail();
              onSplashScreenViewCopyFinished(_arg074, _arg147);
              reply.writeNoException();
              return true;
            case 92:
              PictureInPictureUiState _arg075 =
                  (PictureInPictureUiState) data.readTypedObject(PictureInPictureUiState.CREATOR);
              data.enforceNoDataAvail();
              onPictureInPictureStateChanged(_arg075);
              reply.writeNoException();
              return true;
            case 93:
              IBinder _arg076 = data.readStrongBinder();
              data.enforceNoDataAvail();
              detachNavigationBarFromApp(_arg076);
              reply.writeNoException();
              return true;
            case 94:
              int _arg077 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result50 = clearRecentTasks(_arg077);
              reply.writeNoException();
              reply.writeBoolean(_result50);
              return true;
            case 95:
              IApplicationThread _arg078 =
                  IApplicationThread.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setRunningRemoteTransitionDelegate(_arg078);
              reply.writeNoException();
              return true;
            case 96:
              RemoteCallback _arg079 =
                  (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
              BackAnimationAdapter _arg148 =
                  (BackAnimationAdapter) data.readTypedObject(BackAnimationAdapter.CREATOR);
              data.enforceNoDataAvail();
              BackNavigationInfo _result51 = startBackNavigation(_arg079, _arg148);
              reply.writeNoException();
              reply.writeTypedObject(_result51, 1);
              return true;
            case 97:
              IBinder _arg080 = data.readStrongBinder();
              IScreenCaptureObserver _arg149 =
                  IScreenCaptureObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerScreenCaptureObserver(_arg080, _arg149);
              reply.writeNoException();
              return true;
            case 98:
              IBinder _arg081 = data.readStrongBinder();
              IScreenCaptureObserver _arg150 =
                  IScreenCaptureObserver.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterScreenCaptureObserver(_arg081, _arg150);
              reply.writeNoException();
              return true;
            case 99:
              IMultiTaskingBinder _result52 = getMultiTaskingBinder();
              reply.writeNoException();
              reply.writeStrongInterface(_result52);
              return true;
            case 100:
              IFoldStarManager _result53 = getFoldStarManagerService();
              reply.writeNoException();
              reply.writeStrongInterface(_result53);
              return true;
            case 101:
              int _arg082 = data.readInt();
              data.enforceNoDataAvail();
              Bitmap _result54 = getResumedTaskThumbnail(_arg082);
              reply.writeNoException();
              reply.writeTypedObject(_result54, 1);
              return true;
            case 102:
              String _arg083 = data.readString();
              int _arg151 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice<CompatChangeablePackageInfo> _result55 =
                  getCompatChangeablePackageInfoList(_arg083, _arg151);
              reply.writeNoException();
              reply.writeTypedObject(_result55, 1);
              return true;
            case 103:
              int _arg084 = data.readInt();
              int _arg152 = data.readInt();
              data.enforceNoDataAvail();
              resetUserPackageSettings(_arg084, _arg152);
              reply.writeNoException();
              return true;
            case 104:
              String _arg085 = data.readString();
              String _arg153 = data.readString();
              String _arg228 = data.readString();
              long _arg320 = data.readLong();
              int N = data.readInt();
              final Map<String, String> _arg414 = N < 0 ? null : new HashMap<>();
              IntStream.range(0, N)
                  .forEach(
                      new IntConsumer() { // from class:
                                          // android.app.IActivityTaskManager$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                          IActivityTaskManager.Stub.lambda$onTransact$0(Parcel.this, _arg414, i);
                        }
                      });
              data.enforceNoDataAvail();
              sendSaLoggingBroadcast(_arg085, _arg153, _arg228, _arg320, _arg414);
              return true;
            case 105:
              String _arg086 = data.readString();
              String _arg154 = data.readString();
              String _arg229 = data.readString();
              data.enforceNoDataAvail();
              sendSaLoggingBroadcastForSetting(_arg086, _arg154, _arg229);
              return true;
            case 106:
              String _arg087 = data.readString();
              String _arg155 = data.readString();
              int _arg230 = data.readInt();
              data.enforceNoDataAvail();
              setCustomSplashScreenTheme(_arg087, _arg155, _arg230);
              reply.writeNoException();
              return true;
            case 107:
              int _arg088 = data.readInt();
              String _arg156 = data.readString();
              int _arg231 = data.readInt();
              data.enforceNoDataAvail();
              setCutoutPolicy(_arg088, _arg156, _arg231);
              reply.writeNoException();
              return true;
            case 108:
              int _arg089 = data.readInt();
              String _arg157 = data.readString();
              data.enforceNoDataAvail();
              int _result56 = getCutoutPolicy(_arg089, _arg157);
              reply.writeNoException();
              reply.writeInt(_result56);
              return true;
            case 109:
              int _arg090 = data.readInt();
              data.enforceNoDataAvail();
              requestWaitingForOccluding(_arg090);
              reply.writeNoException();
              return true;
            case 110:
              String _result57 = getScpmVersion();
              reply.writeNoException();
              reply.writeString(_result57);
              return true;
            case 111:
              int _arg091 = data.readInt();
              String _arg158 = data.readString();
              int _arg232 = data.readInt();
              data.enforceNoDataAvail();
              setOrientationControlPolicy(_arg091, _arg158, _arg232);
              reply.writeNoException();
              return true;
            case 112:
              int _arg092 = data.readInt();
              String _arg159 = data.readString();
              data.enforceNoDataAvail();
              int _result58 = getOrientationControlPolicy(_arg092, _arg159);
              reply.writeNoException();
              reply.writeInt(_result58);
              return true;
            case 113:
              boolean _arg093 = data.readBoolean();
              data.enforceNoDataAvail();
              setOrientationControlDefault(_arg093);
              reply.writeNoException();
              return true;
            case 114:
              boolean _arg094 = data.readBoolean();
              data.enforceNoDataAvail();
              setDisallowWhenLandscape(_arg094);
              reply.writeNoException();
              return true;
            case 115:
              IBinder _arg095 = data.readStrongBinder();
              Intent _arg160 = (Intent) data.readTypedObject(Intent.CREATOR);
              boolean _arg233 = data.readBoolean();
              String _arg321 = data.readString();
              data.enforceNoDataAvail();
              startAppLockService(_arg095, _arg160, _arg233, _arg321);
              reply.writeNoException();
              return true;
            case 116:
              List<String> _result59 = getAppLockedPackageList();
              reply.writeNoException();
              reply.writeStringList(_result59);
              return true;
            case 117:
              String _arg096 = data.readString();
              data.enforceNoDataAvail();
              setAppLockedUnLockPackage(_arg096);
              reply.writeNoException();
              return true;
            case 118:
              String _arg097 = data.readString();
              data.enforceNoDataAvail();
              boolean _result60 = isAppLockedPackage(_arg097);
              reply.writeNoException();
              reply.writeBoolean(_result60);
              return true;
            case 119:
              clearAppLockedUnLockedApp();
              reply.writeNoException();
              return true;
            case 120:
              String _result61 = getAppLockedLockType();
              reply.writeNoException();
              reply.writeString(_result61);
              return true;
            case 121:
              String _result62 = getAppLockedCheckAction();
              reply.writeNoException();
              reply.writeString(_result62);
              return true;
            case 122:
              String _arg098 = data.readString();
              boolean _arg161 = data.readBoolean();
              data.enforceNoDataAvail();
              setAppLockedVerifying(_arg098, _arg161);
              reply.writeNoException();
              return true;
            case 123:
              String _arg099 = data.readString();
              data.enforceNoDataAvail();
              boolean _result63 = isAppLockedVerifying(_arg099);
              reply.writeNoException();
              reply.writeBoolean(_result63);
              return true;
            case 124:
              String _arg0100 = data.readString();
              data.enforceNoDataAvail();
              setApplockLockedAppsPackage(_arg0100);
              reply.writeNoException();
              return true;
            case 125:
              String _arg0101 = data.readString();
              data.enforceNoDataAvail();
              setApplockLockedAppsClass(_arg0101);
              reply.writeNoException();
              return true;
            case 126:
              int _arg0102 = data.readInt();
              data.enforceNoDataAvail();
              setApplockType(_arg0102);
              reply.writeNoException();
              return true;
            case 127:
              boolean _arg0103 = data.readBoolean();
              data.enforceNoDataAvail();
              setApplockEnabled(_arg0103);
              reply.writeNoException();
              return true;
            case 128:
              String _arg0104 = data.readString();
              data.enforceNoDataAvail();
              setSsecureHiddenAppsPackages(_arg0104);
              reply.writeNoException();
              return true;
            case 129:
              String _result64 = getApplockLockedAppsPackage();
              reply.writeNoException();
              reply.writeString(_result64);
              return true;
            case 130:
              String _result65 = getApplockLockedAppsClass();
              reply.writeNoException();
              reply.writeString(_result65);
              return true;
            case 131:
              int _result66 = getApplockType();
              reply.writeNoException();
              reply.writeInt(_result66);
              return true;
            case 132:
              boolean _result67 = isApplockEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result67);
              return true;
            case 133:
              String _result68 = getSsecureHiddenAppsPackages();
              reply.writeNoException();
              reply.writeString(_result68);
              return true;
            case 134:
              int _arg0105 = data.readInt();
              data.enforceNoDataAvail();
              setBoundsCompatAlignment(_arg0105);
              reply.writeNoException();
              return true;
            case 135:
              int _result69 = getBoundsCompatAlignment();
              reply.writeNoException();
              reply.writeInt(_result69);
              return true;
            case 136:
              boolean _arg0106 = data.readBoolean();
              data.enforceNoDataAvail();
              setUseLetterbox(_arg0106);
              reply.writeNoException();
              return true;
            case 137:
              int _arg0107 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result70 = hasWallpaperBackgroundForLetterbox(_arg0107);
              reply.writeNoException();
              reply.writeBoolean(_result70);
              return true;
            case 138:
              int _arg0108 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result71 = getCoverLauncherAvailableAppList(_arg0108);
              reply.writeNoException();
              reply.writeStringList(_result71);
              return true;
            case 139:
              int _arg0109 = data.readInt();
              data.enforceNoDataAvail();
              Map _result72 = getCoverLauncherEnabledAppList(_arg0109);
              reply.writeNoException();
              reply.writeMap(_result72);
              return true;
            case 140:
              int _arg0110 = data.readInt();
              int _arg162 = data.readInt();
              data.enforceNoDataAvail();
              Map _result73 = getCoverLauncherEnabledAppListByType(_arg0110, _arg162);
              reply.writeNoException();
              reply.writeMap(_result73);
              return true;
            case 141:
              String _arg0111 = data.readString();
              int _arg163 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result74 = isPackageEnabledForCoverLauncher(_arg0111, _arg163);
              reply.writeNoException();
              reply.writeBoolean(_result74);
              return true;
            case 142:
              String _arg0112 = data.readString();
              int _arg164 = data.readInt();
              int _arg234 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result75 =
                  isPackageSettingsEnabledForCoverLauncher(_arg0112, _arg164, _arg234);
              reply.writeNoException();
              reply.writeBoolean(_result75);
              return true;
            case 143:
              String _arg0113 = data.readString();
              int _arg165 = data.readInt();
              data.enforceNoDataAvail();
              int _result76 = setCoverLauncherPackageEnabled(_arg0113, _arg165);
              reply.writeNoException();
              reply.writeInt(_result76);
              return true;
            case 144:
              String _arg0114 = data.readString();
              int _arg166 = data.readInt();
              data.enforceNoDataAvail();
              int _result77 = setCoverLauncherPackageDisabled(_arg0114, _arg166);
              reply.writeNoException();
              reply.writeInt(_result77);
              return true;
            case 145:
              Intent _arg0115 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg167 = data.readString();
              data.enforceNoDataAvail();
              startActivityForCoverLauncher(_arg0115, _arg167);
              return true;
            case 146:
              Intent _arg0116 = (Intent) data.readTypedObject(Intent.CREATOR);
              String _arg168 = data.readString();
              int _arg235 = data.readInt();
              data.enforceNoDataAvail();
              startActivityForCoverLauncherAsUser(_arg0116, _arg168, _arg235);
              return true;
            case 147:
              int _arg0117 = data.readInt();
              data.enforceNoDataAvail();
              startActivityForDexRestart(_arg0117);
              reply.writeNoException();
              return true;
            case 148:
              IKeyEventListener _arg0118 =
                  IKeyEventListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registKeyEventListener(_arg0118);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    static /* synthetic */ void lambda$onTransact$0(Parcel data, Map _arg4, int i) {
      String k = data.readString();
      String v = data.readString();
      _arg4.put(k, v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Proxy implements IActivityTaskManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IActivityTaskManager.DESCRIPTOR;
      }

      @Override // android.app.IActivityTaskManager
      public int startActivity(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          Intent intent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int flags,
          ProfilerInfo profilerInfo,
          Bundle options)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          try {
            _data.writeString(callingPackage);
            try {
              _data.writeString(callingFeatureId);
              try {
                _data.writeTypedObject(intent, 0);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(resolvedType);
            try {
              _data.writeStrongBinder(resultTo);
              try {
                _data.writeString(resultWho);
                try {
                  _data.writeInt(requestCode);
                } catch (Throwable th4) {
                  th = th4;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th5) {
                th = th5;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeInt(flags);
              try {
                _data.writeTypedObject(profilerInfo, 0);
                try {
                  _data.writeTypedObject(options, 0);
                  try {
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                  } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th8) {
                  th = th8;
                }
              } catch (Throwable th9) {
                th = th9;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivities(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          Intent[] intents,
          String[] resolvedTypes,
          IBinder resultTo,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          _data.writeTypedArray(intents, 0);
          _data.writeStringArray(resolvedTypes);
          _data.writeStrongBinder(resultTo);
          _data.writeTypedObject(options, 0);
          _data.writeInt(userId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityAsUser(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          Intent intent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int flags,
          ProfilerInfo profilerInfo,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
        } catch (Throwable th) {
          th = th;
        }
        try {
          _data.writeString(callingFeatureId);
          try {
            _data.writeTypedObject(intent, 0);
            try {
              _data.writeString(resolvedType);
              try {
                _data.writeStrongBinder(resultTo);
              } catch (Throwable th2) {
                th = th2;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th3) {
              th = th3;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th4) {
            th = th4;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(resultWho);
            try {
              _data.writeInt(requestCode);
              try {
                _data.writeInt(flags);
                try {
                  _data.writeTypedObject(profilerInfo, 0);
                } catch (Throwable th5) {
                  th = th5;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th6) {
                th = th6;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th7) {
              th = th7;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeTypedObject(options, 0);
              try {
                _data.writeInt(userId);
                try {
                  this.mRemote.transact(3, _data, _reply, 0);
                  _reply.readException();
                  int _result = _reply.readInt();
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean startNextMatchingActivity(
          IBinder callingActivity, Intent intent, Bundle options) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(callingActivity);
          _data.writeTypedObject(intent, 0);
          _data.writeTypedObject(options, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean startDreamActivity(Intent intent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityIntentSender(
          IApplicationThread caller,
          IIntentSender target,
          IBinder whitelistToken,
          Intent fillInIntent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int flagsMask,
          int flagsValues,
          Bundle options)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          try {
            _data.writeStrongInterface(target);
            try {
              _data.writeStrongBinder(whitelistToken);
              try {
                _data.writeTypedObject(fillInIntent, 0);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(resolvedType);
            try {
              _data.writeStrongBinder(resultTo);
              try {
                _data.writeString(resultWho);
                try {
                  _data.writeInt(requestCode);
                } catch (Throwable th4) {
                  th = th4;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th5) {
                th = th5;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeInt(flagsMask);
              try {
                _data.writeInt(flagsValues);
                try {
                  _data.writeTypedObject(options, 0);
                  try {
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                  } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th8) {
                  th = th8;
                }
              } catch (Throwable th9) {
                th = th9;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
        }
      }

      @Override // android.app.IActivityTaskManager
      public WaitResult startActivityAndWait(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          Intent intent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int flags,
          ProfilerInfo profilerInfo,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
          try {
            _data.writeString(callingFeatureId);
          } catch (Throwable th) {
            th = th;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
        }
        try {
          _data.writeTypedObject(intent, 0);
          try {
            _data.writeString(resolvedType);
            try {
              _data.writeStrongBinder(resultTo);
              try {
                _data.writeString(resultWho);
              } catch (Throwable th3) {
                th = th3;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th4) {
              th = th4;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th5) {
            th = th5;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeInt(requestCode);
            try {
              _data.writeInt(flags);
              try {
                _data.writeTypedObject(profilerInfo, 0);
                try {
                  _data.writeTypedObject(options, 0);
                  try {
                    _data.writeInt(userId);
                    try {
                      this.mRemote.transact(7, _data, _reply, 0);
                      _reply.readException();
                      WaitResult _result = (WaitResult) _reply.readTypedObject(WaitResult.CREATOR);
                      _reply.recycle();
                      _data.recycle();
                      return _result;
                    } catch (Throwable th6) {
                      th = th6;
                      _reply.recycle();
                      _data.recycle();
                      throw th;
                    }
                  } catch (Throwable th7) {
                    th = th7;
                  }
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityWithConfig(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          Intent intent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int startFlags,
          Configuration newConfig,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
        } catch (Throwable th) {
          th = th;
        }
        try {
          _data.writeString(callingFeatureId);
          try {
            _data.writeTypedObject(intent, 0);
            try {
              _data.writeString(resolvedType);
              try {
                _data.writeStrongBinder(resultTo);
              } catch (Throwable th2) {
                th = th2;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th3) {
              th = th3;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th4) {
            th = th4;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(resultWho);
            try {
              _data.writeInt(requestCode);
              try {
                _data.writeInt(startFlags);
                try {
                  _data.writeTypedObject(newConfig, 0);
                } catch (Throwable th5) {
                  th = th5;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th6) {
                th = th6;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th7) {
              th = th7;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeTypedObject(options, 0);
              try {
                _data.writeInt(userId);
                try {
                  this.mRemote.transact(8, _data, _reply, 0);
                  _reply.readException();
                  int _result = _reply.readInt();
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startVoiceActivity(
          String callingPackage,
          String callingFeatureId,
          int callingPid,
          int callingUid,
          Intent intent,
          String resolvedType,
          IVoiceInteractionSession session,
          IVoiceInteractor interactor,
          int flags,
          ProfilerInfo profilerInfo,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
        } catch (Throwable th) {
          th = th;
        }
        try {
          _data.writeInt(callingPid);
          try {
            _data.writeInt(callingUid);
            try {
              _data.writeTypedObject(intent, 0);
              try {
                _data.writeString(resolvedType);
              } catch (Throwable th2) {
                th = th2;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th3) {
              th = th3;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th4) {
            th = th4;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeStrongInterface(session);
            try {
              _data.writeStrongInterface(interactor);
              try {
                _data.writeInt(flags);
                try {
                  _data.writeTypedObject(profilerInfo, 0);
                } catch (Throwable th5) {
                  th = th5;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th6) {
                th = th6;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th7) {
              th = th7;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeTypedObject(options, 0);
              try {
                _data.writeInt(userId);
                try {
                  this.mRemote.transact(9, _data, _reply, 0);
                  _reply.readException();
                  int _result = _reply.readInt();
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getVoiceInteractorPackageName(IBinder callingVoiceInteractor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(callingVoiceInteractor);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startAssistantActivity(
          String callingPackage,
          String callingFeatureId,
          int callingPid,
          int callingUid,
          Intent intent,
          String resolvedType,
          Bundle options,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          _data.writeInt(callingPid);
          _data.writeInt(callingUid);
          _data.writeTypedObject(intent, 0);
          _data.writeString(resolvedType);
          _data.writeTypedObject(options, 0);
          _data.writeInt(userId);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityFromGameSession(
          IApplicationThread caller,
          String callingPackage,
          String callingFeatureId,
          int callingPid,
          int callingUid,
          Intent intent,
          int taskId,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
          _data.writeString(callingFeatureId);
          _data.writeInt(callingPid);
          _data.writeInt(callingUid);
          _data.writeTypedObject(intent, 0);
          _data.writeInt(taskId);
          _data.writeInt(userId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startRecentsActivity(
          Intent intent, long eventTime, IRecentsAnimationRunner recentsAnimationRunner)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeLong(eventTime);
          _data.writeStrongInterface(recentsAnimationRunner);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(options, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int startActivityAsCaller(
          IApplicationThread caller,
          String callingPackage,
          Intent intent,
          String resolvedType,
          IBinder resultTo,
          String resultWho,
          int requestCode,
          int flags,
          ProfilerInfo profilerInfo,
          Bundle options,
          boolean ignoreTargetSecurity,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          _data.writeString(callingPackage);
        } catch (Throwable th) {
          th = th;
        }
        try {
          _data.writeTypedObject(intent, 0);
          try {
            _data.writeString(resolvedType);
            try {
              _data.writeStrongBinder(resultTo);
              try {
                _data.writeString(resultWho);
              } catch (Throwable th2) {
                th = th2;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th3) {
              th = th3;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th4) {
            th = th4;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeInt(requestCode);
            try {
              _data.writeInt(flags);
              try {
                _data.writeTypedObject(profilerInfo, 0);
                try {
                  _data.writeTypedObject(options, 0);
                } catch (Throwable th5) {
                  th = th5;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th6) {
                th = th6;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th7) {
              th = th7;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeBoolean(ignoreTargetSecurity);
              try {
                _data.writeInt(userId);
                try {
                  this.mRemote.transact(15, _data, _reply, 0);
                  _reply.readException();
                  int _result = _reply.readInt();
                  _reply.recycle();
                  _data.recycle();
                  return _result;
                } catch (Throwable th8) {
                  th = th8;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th9) {
                th = th9;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
          _reply.recycle();
          _data.recycle();
          throw th;
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isActivityStartAllowedOnDisplay(
          int displayId, Intent intent, String resolvedType, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeTypedObject(intent, 0);
          _data.writeString(resolvedType);
          _data.writeInt(userId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void unhandledBack() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public IActivityClientController getActivityClientController() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          IActivityClientController _result =
              IActivityClientController.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getFrontActivityScreenCompatMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setFrontActivityScreenCompatMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setFocusedTask(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean removeTask(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean removeTaskWithFlags(int taskId, int flags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeInt(flags);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void removeAllVisibleRecentTasks() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<ActivityManager.RunningTaskInfo> getTasks(
          int maxNum, boolean filterOnlyVisibleRecents, boolean keepIntentExtra, int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(maxNum);
          _data.writeBoolean(filterOnlyVisibleRecents);
          _data.writeBoolean(keepIntentExtra);
          _data.writeInt(displayId);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          List<ActivityManager.RunningTaskInfo> _result =
              _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void moveTaskToFront(
          IApplicationThread app, String callingPackage, int task, int flags, Bundle options)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(app);
          _data.writeString(callingPackage);
          _data.writeInt(task);
          _data.writeInt(flags);
          _data.writeTypedObject(options, 0);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(
          int maxNum, int flags, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(maxNum);
          _data.writeInt(flags);
          _data.writeInt(userId);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice<ActivityManager.RecentTaskInfo> _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isTopActivityImmersive() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ActivityManager.TaskDescription getTaskDescription(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          ActivityManager.TaskDescription _result =
              (ActivityManager.TaskDescription)
                  _reply.readTypedObject(ActivityManager.TaskDescription.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void reportAssistContextExtras(
          IBinder assistToken,
          Bundle extras,
          AssistStructure structure,
          AssistContent content,
          Uri referrer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(assistToken);
          _data.writeTypedObject(extras, 0);
          _data.writeTypedObject(structure, 0);
          _data.writeTypedObject(content, 0);
          _data.writeTypedObject(referrer, 0);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setFocusedRootTask(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          ActivityTaskManager.RootTaskInfo _result =
              (ActivityTaskManager.RootTaskInfo)
                  _reply.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Rect getTaskBounds(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void focusTopTask(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void cancelRecentsAnimation(boolean restoreHomeRootTaskPosition)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(restoreHomeRootTaskPosition);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringArray(packages);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isInLockTaskMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getLockTaskModeState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<IBinder> getAppTasks(String callingPackage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(callingPackage);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
          List<IBinder> _result = _reply.createBinderArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startSystemLockTaskMode(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void stopSystemLockTaskMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void finishVoiceTask(IVoiceInteractionSession session) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(session);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int addAppTask(
          IBinder activityToken,
          Intent intent,
          ActivityManager.TaskDescription description,
          Bitmap thumbnail)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(activityToken);
          _data.writeTypedObject(intent, 0);
          _data.writeTypedObject(description, 0);
          _data.writeTypedObject(thumbnail, 0);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Point getAppTaskThumbnailSize() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
          Point _result = (Point) _reply.readTypedObject(Point.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void releaseSomeActivities(IApplicationThread app) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(app);
          this.mRemote.transact(45, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Bitmap getTaskDescriptionIcon(String filename, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(filename);
          _data.writeInt(userId);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
          Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void unregisterTaskStackListener(ITaskStackListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeInt(resizeableMode);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(bounds, 0);
          _data.writeInt(resizeMode);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void moveRootTaskToDisplay(int taskId, int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeInt(displayId);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeInt(rootTaskId);
          _data.writeBoolean(toTop);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void removeRootTasksInWindowingModes(int[] windowingModes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeIntArray(windowingModes);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void removeRootTasksWithActivityTypes(int[] activityTypes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeIntArray(activityTypes);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          List<ActivityTaskManager.RootTaskInfo> _result =
              _reply.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ActivityTaskManager.RootTaskInfo getRootTaskInfo(int windowingMode, int activityType)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(windowingMode);
          _data.writeInt(activityType);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
          ActivityTaskManager.RootTaskInfo _result =
              (ActivityTaskManager.RootTaskInfo)
                  _reply.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
          List<ActivityTaskManager.RootTaskInfo> _result =
              _reply.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(
          int windowingMode, int activityType, int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(windowingMode);
          _data.writeInt(activityType);
          _data.writeInt(displayId);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
          ActivityTaskManager.RootTaskInfo _result =
              (ActivityTaskManager.RootTaskInfo)
                  _reply.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setLockScreenShown(boolean showingKeyguard, boolean showingAod)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(showingKeyguard);
          _data.writeBoolean(showingAod);
          this.mRemote.transact(59, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Bundle getAssistContextExtras(int requestType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(requestType);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean requestAssistContextExtras(
          int requestType,
          IAssistDataReceiver receiver,
          Bundle receiverExtras,
          IBinder activityToken,
          boolean focused,
          boolean newSessionId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(requestType);
          _data.writeStrongInterface(receiver);
          _data.writeTypedObject(receiverExtras, 0);
          _data.writeStrongBinder(activityToken);
          _data.writeBoolean(focused);
          _data.writeBoolean(newSessionId);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean requestAutofillData(
          IAssistDataReceiver receiver, Bundle receiverExtras, IBinder activityToken, int flags)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(receiver);
          _data.writeTypedObject(receiverExtras, 0);
          _data.writeStrongBinder(activityToken);
          _data.writeInt(flags);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isAssistDataAllowedOnCurrentActivity() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean requestAssistDataForTask(
          IAssistDataReceiver receiver,
          int taskId,
          String callingPackageName,
          String callingAttributionTag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(receiver);
          _data.writeInt(taskId);
          _data.writeString(callingPackageName);
          _data.writeString(callingAttributionTag);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void keyguardGoingAway(int flags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(flags);
          this.mRemote.transact(65, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(suppress);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public IWindowOrganizerController getWindowOrganizerController() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
          IWindowOrganizerController _result =
              IWindowOrganizerController.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean supportsLocalVoiceInteraction() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ConfigurationInfo getDeviceConfigurationInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
          ConfigurationInfo _result =
              (ConfigurationInfo) _reply.readTypedObject(ConfigurationInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void cancelTaskWindowTransition(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public TaskSnapshot getTaskSnapshot(
          int taskId, boolean isLowResolution, boolean takeSnapshotIfNeeded)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(isLowResolution);
          _data.writeBoolean(takeSnapshotIfNeeded);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
          TaskSnapshot _result = (TaskSnapshot) _reply.readTypedObject(TaskSnapshot.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public TaskSnapshot takeTaskSnapshot(int taskId, boolean updateCache) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(updateCache);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
          TaskSnapshot _result = (TaskSnapshot) _reply.readTypedObject(TaskSnapshot.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getLastResumedActivityUserId() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean updateConfiguration(Configuration values) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(values, 0);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void updateLockTaskFeatures(int userId, int flags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(flags);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registerRemoteAnimationForNextActivityStart(
          String packageName, RemoteAnimationAdapter adapter, IBinder launchCookie)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedObject(adapter, 0);
          _data.writeStrongBinder(launchCookie);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registerRemoteTransitionForNextActivityStart(
          String packageName,
          RemoteAnimationAdapter adapter,
          IBinder launchCookie,
          RemoteTransition remoteTransiton)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedObject(adapter, 0);
          _data.writeStrongBinder(launchCookie);
          _data.writeTypedObject(remoteTransiton, 0);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registerRemoteAnimationsForDisplay(
          int displayId, RemoteAnimationDefinition definition) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeTypedObject(definition, 0);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void alwaysShowUnsupportedCompileSdkWarning(ComponentName activity)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(activity, 0);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setVrThread(int tid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(tid);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setPersistentVrThread(int tid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(tid);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void stopAppSwitches() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void resumeAppSwitches() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setActivityController(IActivityController watcher, boolean imAMonkey)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(watcher);
          _data.writeBoolean(imAMonkey);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setVoiceKeepAwake(IVoiceInteractionSession session, boolean keepAwake)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(session);
          _data.writeBoolean(keepAwake);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getPackageScreenCompatMode(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setPackageScreenCompatMode(String packageName, int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(mode);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean getPackageAskScreenCompat(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setPackageAskScreenCompat(String packageName, boolean ask)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeBoolean(ask);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void clearLaunchParamsForPackages(List<String> packageNames) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStringList(packageNames);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void onSplashScreenViewCopyFinished(
          int taskId, SplashScreenView.SplashScreenViewParcelable material) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeTypedObject(material, 0);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void onPictureInPictureStateChanged(PictureInPictureUiState pipState)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(pipState, 0);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void detachNavigationBarFromApp(IBinder transition) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(transition);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean clearRecentTasks(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setRunningRemoteTransitionDelegate(IApplicationThread caller)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(caller);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public BackNavigationInfo startBackNavigation(
          RemoteCallback navigationObserver, BackAnimationAdapter adaptor) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(navigationObserver, 0);
          _data.writeTypedObject(adaptor, 0);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
          BackNavigationInfo _result =
              (BackNavigationInfo) _reply.readTypedObject(BackNavigationInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registerScreenCaptureObserver(
          IBinder activityToken, IScreenCaptureObserver observer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(activityToken);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void unregisterScreenCaptureObserver(
          IBinder activityToken, IScreenCaptureObserver observer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(activityToken);
          _data.writeStrongInterface(observer);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public IMultiTaskingBinder getMultiTaskingBinder() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
          IMultiTaskingBinder _result =
              IMultiTaskingBinder.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public IFoldStarManager getFoldStarManagerService() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
          IFoldStarManager _result = IFoldStarManager.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Bitmap getResumedTaskThumbnail(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
          Bitmap _result = (Bitmap) _reply.readTypedObject(Bitmap.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public ParceledListSlice<CompatChangeablePackageInfo> getCompatChangeablePackageInfoList(
          String packageName, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice<CompatChangeablePackageInfo> _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void resetUserPackageSettings(int userId, int flags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(flags);
          this.mRemote.transact(103, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void sendSaLoggingBroadcast(
          String trackingId,
          String eventId,
          String detail,
          long value,
          Map<String, String> customDimension)
          throws RemoteException {
        final Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(trackingId);
          _data.writeString(eventId);
          _data.writeString(detail);
          _data.writeLong(value);
          if (customDimension == null) {
            _data.writeInt(-1);
          } else {
            _data.writeInt(customDimension.size());
            customDimension.forEach(
                new BiConsumer() { // from class:
                                   // android.app.IActivityTaskManager$Stub$Proxy$$ExternalSyntheticLambda0
                  @Override // java.util.function.BiConsumer
                  public final void accept(Object obj, Object obj2) {
                    IActivityTaskManager.Stub.Proxy.lambda$sendSaLoggingBroadcast$0(
                        Parcel.this, (String) obj, (String) obj2);
                  }
                });
          }
          this.mRemote.transact(104, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      static /* synthetic */ void lambda$sendSaLoggingBroadcast$0(
          Parcel _data, String k, String v) {
        _data.writeString(k);
        _data.writeString(v);
      }

      @Override // android.app.IActivityTaskManager
      public void sendSaLoggingBroadcastForSetting(
          String trackingId, String settingId, String value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(trackingId);
          _data.writeString(settingId);
          _data.writeString(value);
          this.mRemote.transact(105, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setCustomSplashScreenTheme(String packageName, String themeName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeString(themeName);
          _data.writeInt(userId);
          this.mRemote.transact(106, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setCutoutPolicy(int userId, String packageName, int policy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeInt(policy);
          this.mRemote.transact(107, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getCutoutPolicy(int userId, String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(108, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void requestWaitingForOccluding(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(109, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getScpmVersion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(110, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setOrientationControlPolicy(int userId, String packageName, int policy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          _data.writeInt(policy);
          this.mRemote.transact(111, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getOrientationControlPolicy(int userId, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(packageName);
          this.mRemote.transact(112, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setOrientationControlDefault(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(113, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setDisallowWhenLandscape(boolean disallow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(disallow);
          this.mRemote.transact(114, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startAppLockService(
          IBinder token, Intent intent, boolean showWhenLocked, String pkgName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeTypedObject(intent, 0);
          _data.writeBoolean(showWhenLocked);
          _data.writeString(pkgName);
          this.mRemote.transact(115, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<String> getAppLockedPackageList() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(116, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setAppLockedUnLockPackage(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(117, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isAppLockedPackage(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(118, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void clearAppLockedUnLockedApp() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(119, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getAppLockedLockType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(120, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getAppLockedCheckAction() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(121, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setAppLockedVerifying(String pkgName, boolean verifying) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeBoolean(verifying);
          this.mRemote.transact(122, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isAppLockedVerifying(String pkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(pkgName);
          this.mRemote.transact(123, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setApplockLockedAppsPackage(String packages) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packages);
          this.mRemote.transact(124, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setApplockLockedAppsClass(String classes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(classes);
          this.mRemote.transact(125, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setApplockType(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(126, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setApplockEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(127, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setSsecureHiddenAppsPackages(String packages) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packages);
          this.mRemote.transact(128, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getApplockLockedAppsPackage() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(129, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getApplockLockedAppsClass() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(130, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getApplockType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(131, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isApplockEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(132, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public String getSsecureHiddenAppsPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(133, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setBoundsCompatAlignment(int alignment) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(alignment);
          this.mRemote.transact(134, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int getBoundsCompatAlignment() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          this.mRemote.transact(135, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void setUseLetterbox(boolean useLetterbox) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeBoolean(useLetterbox);
          this.mRemote.transact(136, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean hasWallpaperBackgroundForLetterbox(int displayId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(displayId);
          this.mRemote.transact(137, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public List<String> getCoverLauncherAvailableAppList(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(138, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Map getCoverLauncherEnabledAppList(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(139, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public Map getCoverLauncherEnabledAppListByType(int userId, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(type);
          this.mRemote.transact(140, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isPackageEnabledForCoverLauncher(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(141, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public boolean isPackageSettingsEnabledForCoverLauncher(
          String packageName, int userId, int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          _data.writeInt(type);
          this.mRemote.transact(142, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int setCoverLauncherPackageEnabled(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(143, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public int setCoverLauncherPackageDisabled(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(144, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startActivityForCoverLauncher(Intent intent, String requestReason)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeString(requestReason);
          this.mRemote.transact(145, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startActivityForCoverLauncherAsUser(
          Intent intent, String requestReason, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeTypedObject(intent, 0);
          _data.writeString(requestReason);
          _data.writeInt(userId);
          this.mRemote.transact(146, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void startActivityForDexRestart(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(147, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IActivityTaskManager
      public void registKeyEventListener(IKeyEventListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IActivityTaskManager.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(148, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 147;
    }
  }
}
