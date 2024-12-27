package android.app;

import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IProgressListener;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.WorkSource;
import android.text.TextUtils;

import com.android.internal.os.IResultReceiver;

import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.sdhms.SemAppRestrictionManager;

import java.util.List;

public interface IActivityManager extends IInterface {
    void addApplicationStartInfoCompleteListener(
            IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i)
            throws RemoteException;

    void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle)
            throws RemoteException;

    boolean addLongLiveApp(String str) throws RemoteException;

    void addOverridePermissionState(int i, int i2, String str, int i3) throws RemoteException;

    void addPackageDependency(String str) throws RemoteException;

    void addStartInfoTimestamp(int i, long j, int i2) throws RemoteException;

    void addUidToObserver(IBinder iBinder, String str, int i) throws RemoteException;

    void appNotResponding(String str) throws RemoteException;

    void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException;

    void attachApplication(IApplicationThread iApplicationThread, long j) throws RemoteException;

    void backgroundAllowlistUid(int i) throws RemoteException;

    void backupAgentCreated(String str, IBinder iBinder, int i) throws RemoteException;

    boolean bindBackupAgent(String str, int i, int i2, int i3) throws RemoteException;

    int bindService(
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            Intent intent,
            String str,
            IServiceConnection iServiceConnection,
            long j,
            String str2,
            int i)
            throws RemoteException;

    int bindServiceInstance(
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            Intent intent,
            String str,
            IServiceConnection iServiceConnection,
            long j,
            String str2,
            String str3,
            int i)
            throws RemoteException;

    void bootAnimationComplete() throws RemoteException;

    @Deprecated
    int broadcastIntent(
            IApplicationThread iApplicationThread,
            Intent intent,
            String str,
            IIntentReceiver iIntentReceiver,
            int i,
            String str2,
            Bundle bundle,
            String[] strArr,
            int i2,
            Bundle bundle2,
            boolean z,
            boolean z2,
            int i3)
            throws RemoteException;

    int broadcastIntentWithFeature(
            IApplicationThread iApplicationThread,
            String str,
            Intent intent,
            String str2,
            IIntentReceiver iIntentReceiver,
            int i,
            String str3,
            Bundle bundle,
            String[] strArr,
            String[] strArr2,
            String[] strArr3,
            int i2,
            Bundle bundle2,
            boolean z,
            boolean z2,
            int i3)
            throws RemoteException;

    boolean canRestrict(int i, String str, int i2) throws RemoteException;

    void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException;

    void cancelTaskWindowTransition(int i) throws RemoteException;

    int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3, int i4)
            throws RemoteException;

    int checkPermission(String str, int i, int i2) throws RemoteException;

    int checkPermissionForDevice(String str, int i, int i2, int i3) throws RemoteException;

    void checkProfileForADCP(int i, String str) throws RemoteException;

    int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder)
            throws RemoteException;

    int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3, int i4, IBinder iBinder)
            throws RemoteException;

    void clearAllOverridePermissionStates(int i) throws RemoteException;

    boolean clearApplicationUserData(
            String str, boolean z, IPackageDataObserver iPackageDataObserver, int i)
            throws RemoteException;

    boolean clearLongLiveTask(int i) throws RemoteException;

    void clearOverridePermissionStates(int i, int i2) throws RemoteException;

    boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list)
            throws RemoteException;

    void clearTTSPkgInfo() throws RemoteException;

    void closeSystemDialogs(String str) throws RemoteException;

    void closeSystemDialogsInDisplay(String str, int i) throws RemoteException;

    void crashApplicationWithType(int i, int i2, String str, int i3, String str2, boolean z, int i4)
            throws RemoteException;

    void crashApplicationWithTypeWithExtras(
            int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle)
            throws RemoteException;

    void dismissUserSwitchingDialog(int i) throws RemoteException;

    void doActiveLaunch(String str, boolean z, int i) throws RemoteException;

    boolean dumpHeap(
            String str,
            int i,
            boolean z,
            boolean z2,
            boolean z3,
            String str2,
            String str3,
            ParcelFileDescriptor parcelFileDescriptor,
            RemoteCallback remoteCallback)
            throws RemoteException;

    void dumpHeapFinished(String str) throws RemoteException;

    boolean enableAppFreezer(boolean z) throws RemoteException;

    boolean enableFgsNotificationRateLimit(boolean z) throws RemoteException;

    void enterSafeMode() throws RemoteException;

    boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException;

    void finishAttachApplication(long j, long j2) throws RemoteException;

    void finishHeavyWeightApp() throws RemoteException;

    void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle)
            throws RemoteException;

    void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2)
            throws RemoteException;

    void forceDelayBroadcastDelivery(String str, long j) throws RemoteException;

    void forceStopPackage(String str, int i) throws RemoteException;

    void forceStopPackageByAdmin(String str, int i) throws RemoteException;

    void forceStopPackageEvenWhenStopping(String str, int i) throws RemoteException;

    void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws RemoteException;

    List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList() throws RemoteException;

    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException;

    boolean getAutoRemoveRecents(int i) throws RemoteException;

    int getBackgroundRestrictionExemptionReason(int i) throws RemoteException;

    int getBindingUidProcessState(int i, String str) throws RemoteException;

    List<String> getBugreportWhitelistedPackages() throws RemoteException;

    Configuration getConfiguration() throws RemoteException;

    String[] getContentByTask(int i) throws RemoteException;

    ContentProviderHolder getContentProvider(
            IApplicationThread iApplicationThread, String str, String str2, int i, boolean z)
            throws RemoteException;

    ContentProviderHolder getContentProviderExternal(
            String str, int i, IBinder iBinder, String str2) throws RemoteException;

    UserInfo getCurrentUser() throws RemoteException;

    int getCurrentUserId() throws RemoteException;

    List<String> getDelegatedShellPermissions() throws RemoteException;

    int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException;

    ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException;

    int getForegroundServiceType(ComponentName componentName, IBinder iBinder)
            throws RemoteException;

    Configuration getGlobalConfiguration() throws RemoteException;

    ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(
            String str, int i, int i2, int i3) throws RemoteException;

    ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(
            String str, int i, int i2) throws RemoteException;

    ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender iIntentSender)
            throws RemoteException;

    ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int i, int i2)
            throws RemoteException;

    Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    @Deprecated
    IIntentSender getIntentSender(
            int i,
            String str,
            IBinder iBinder,
            String str2,
            int i2,
            Intent[] intentArr,
            String[] strArr,
            int i3,
            Bundle bundle,
            int i4)
            throws RemoteException;

    IIntentSender getIntentSenderWithFeature(
            int i,
            String str,
            String str2,
            IBinder iBinder,
            String str3,
            int i2,
            Intent[] intentArr,
            String[] strArr,
            int i3,
            Bundle bundle,
            int i4)
            throws RemoteException;

    int[] getIsolatedProcessList() throws RemoteException;

    String getLaunchedFromPackage(IBinder iBinder) throws RemoteException;

    int getLaunchedFromUid(IBinder iBinder) throws RemoteException;

    ParcelFileDescriptor getLifeMonitor() throws RemoteException;

    int getLockTaskModeState() throws RemoteException;

    String getLongLiveApp() throws RemoteException;

    List<String> getLongLiveApps() throws RemoteException;

    List<String> getLongLiveProcesses() throws RemoteException;

    List<String> getLongLiveProcessesForUser(int i) throws RemoteException;

    List getLongLiveTaskIdsForUser(int i) throws RemoteException;

    int getMaxLongLiveApps() throws RemoteException;

    void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException;

    int getMemoryTrimLevel() throws RemoteException;

    void getMimeTypeFilterAsync(Uri uri, int i, RemoteCallback remoteCallback)
            throws RemoteException;

    void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo)
            throws RemoteException;

    Bundle getOptionsForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    String getPackageFromAppProcesses(int i) throws RemoteException;

    int getPackageProcessState(String str, String str2) throws RemoteException;

    int getProcessLimit() throws RemoteException;

    Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException;

    long[] getProcessPss(int[] iArr) throws RemoteException;

    List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException;

    ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException;

    List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i)
            throws RemoteException;

    List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i)
            throws RemoteException;

    SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2)
            throws RemoteException;

    List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException;

    List<ApplicationInfo> getRunningExternalApplications() throws RemoteException;

    PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException;

    int[] getRunningUserIds() throws RemoteException;

    List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException;

    String getSwitchingFromUserMessage() throws RemoteException;

    String getSwitchingToUserMessage() throws RemoteException;

    String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException;

    Rect getTaskBounds(int i) throws RemoteException;

    int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException;

    int[] getUidFrozenState(int[] iArr) throws RemoteException;

    long getUidLastIdleElapsedTime(int i, String str) throws RemoteException;

    int getUidProcessCapabilities(int i, String str) throws RemoteException;

    int getUidProcessState(int i, String str) throws RemoteException;

    void grantUriPermission(
            IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2)
            throws RemoteException;

    void handleApplicationCrash(
            IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo)
            throws RemoteException;

    void handleApplicationStrictModeViolation(
            IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException;

    boolean handleApplicationWtf(
            IBinder iBinder,
            String str,
            boolean z,
            ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo,
            int i)
            throws RemoteException;

    int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2)
            throws RemoteException;

    void hang(IBinder iBinder, boolean z) throws RemoteException;

    boolean hasServiceTimeLimitExceeded(ComponentName componentName, IBinder iBinder)
            throws RemoteException;

    void holdLock(IBinder iBinder, int i) throws RemoteException;

    boolean isAppFreezerEnabled() throws RemoteException;

    boolean isAppFreezerSupported() throws RemoteException;

    boolean isBackgroundRestricted(String str) throws RemoteException;

    boolean isFreezableUid(int i) throws RemoteException;

    boolean isHeapDumpAllowed() throws RemoteException;

    boolean isInLockTaskMode() throws RemoteException;

    boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException;

    boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException;

    boolean isProcessFrozen(int i) throws RemoteException;

    boolean isTopActivityImmersive() throws RemoteException;

    boolean isTopOfTask(IBinder iBinder) throws RemoteException;

    boolean isUidActive(int i, String str) throws RemoteException;

    boolean isUserAMonkey() throws RemoteException;

    boolean isUserRunning(int i, int i2) throws RemoteException;

    boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException;

    void killAllBackgroundProcesses() throws RemoteException;

    void killApplication(String str, int i, int i2, String str2, int i3) throws RemoteException;

    void killApplicationProcess(String str, int i) throws RemoteException;

    void killBackgroundProcesses(String str, int i) throws RemoteException;

    void killPackageDependents(String str, int i) throws RemoteException;

    boolean killPids(int[] iArr, String str, boolean z) throws RemoteException;

    boolean killProcessesBelowForeground(String str) throws RemoteException;

    void killProcessesWhenImperceptible(int[] iArr, String str) throws RemoteException;

    void killUid(int i, int i2, String str) throws RemoteException;

    void killUidForPermissionChange(int i, int i2, String str) throws RemoteException;

    boolean launchBugReportHandlerApp() throws RemoteException;

    void logFgsApiBegin(int i, int i2, int i3) throws RemoteException;

    void logFgsApiEnd(int i, int i2, int i3) throws RemoteException;

    void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws RemoteException;

    void makePackageIdle(String str, int i) throws RemoteException;

    boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException;

    boolean moveTaskToBack(int i, boolean z) throws RemoteException;

    boolean moveTaskToBackWithBundle(int i, boolean z, Bundle bundle) throws RemoteException;

    void moveTaskToFront(
            IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle)
            throws RemoteException;

    void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException;

    void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str)
            throws RemoteException;

    void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str)
            throws RemoteException;

    void noteAppRestrictionEnabled(
            String str, int i, int i2, boolean z, int i3, String str2, int i4, long j)
            throws RemoteException;

    void noteWakeupAlarm(
            IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2)
            throws RemoteException;

    void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException;

    void notifyLockedProfile(int i) throws RemoteException;

    ParcelFileDescriptor openContentUri(String str) throws RemoteException;

    IBinder peekService(Intent intent, String str, String str2) throws RemoteException;

    void performIdleMaintenance() throws RemoteException;

    void preloadBoosterAppsFromIpm(List<String> list, int i) throws RemoteException;

    boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2)
            throws RemoteException;

    void publishContentProviders(
            IApplicationThread iApplicationThread, List<ContentProviderHolder> list)
            throws RemoteException;

    void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException;

    ParceledListSlice queryIntentComponentsForIntentSender(IIntentSender iIntentSender, int i)
            throws RemoteException;

    String[] queryRegisteredReceiverPackages(Intent intent, String str, int i)
            throws RemoteException;

    boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException;

    void registerDedicatedCallback(RemoteCallback remoteCallback, int i) throws RemoteException;

    boolean registerForegroundServiceObserver(IForegroundServiceObserver iForegroundServiceObserver)
            throws RemoteException;

    boolean registerIntentSenderCancelListenerEx(
            IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    Intent registerReceiver(
            IApplicationThread iApplicationThread,
            String str,
            IIntentReceiver iIntentReceiver,
            IntentFilter intentFilter,
            String str2,
            int i,
            int i2)
            throws RemoteException;

    Intent registerReceiverWithFeature(
            IApplicationThread iApplicationThread,
            String str,
            String str2,
            String str3,
            IIntentReceiver iIntentReceiver,
            IntentFilter intentFilter,
            String str4,
            int i,
            int i2)
            throws RemoteException;

    void registerStrictModeCallback(IBinder iBinder) throws RemoteException;

    void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void registerUidFrozenStateChangedCallback(
            IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException;

    void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str)
            throws RemoteException;

    IBinder registerUidObserverForUids(
            IUidObserver iUidObserver, int i, int i2, String str, int[] iArr)
            throws RemoteException;

    void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str)
            throws RemoteException;

    void removeApplicationStartInfoCompleteListener(
            IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i)
            throws RemoteException;

    void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException;

    @Deprecated
    void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException;

    void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i)
            throws RemoteException;

    boolean removeLongLiveApp(String str) throws RemoteException;

    void removeOverridePermissionState(int i, int i2, String str) throws RemoteException;

    boolean removeTask(int i) throws RemoteException;

    void removeUidFromObserver(IBinder iBinder, String str, int i) throws RemoteException;

    void reportAbnormalUsage(int i, int i2) throws RemoteException;

    void reportStartInfoViewTimestamps(long j, long j2) throws RemoteException;

    void requestBugReport(int i) throws RemoteException;

    void requestBugReportWithDescription(String str, String str2, int i) throws RemoteException;

    void requestBugReportWithExtraAttachment(Uri uri) throws RemoteException;

    void requestFullBugReport() throws RemoteException;

    void requestInteractiveBugReport() throws RemoteException;

    void requestInteractiveBugReportWithDescription(String str, String str2) throws RemoteException;

    void requestRemoteBugReport(long j) throws RemoteException;

    void requestSystemServerHeapDump() throws RemoteException;

    void requestTelephonyBugReport(String str, String str2) throws RemoteException;

    void requestWifiBugReport(String str, String str2) throws RemoteException;

    void resetAbnormalList() throws RemoteException;

    void resetAppErrors() throws RemoteException;

    void resizeTask(int i, Rect rect, int i2) throws RemoteException;

    void restart() throws RemoteException;

    int restartUserInBackground(int i, int i2) throws RemoteException;

    boolean restrict(int i, int i2, boolean z, String str, int i3) throws RemoteException;

    void resumeAppSwitches() throws RemoteException;

    void revokeUriPermission(
            IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2)
            throws RemoteException;

    void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException;

    void sendIdleJobTrigger() throws RemoteException;

    int sendIntentSender(
            IApplicationThread iApplicationThread,
            IIntentSender iIntentSender,
            IBinder iBinder,
            int i,
            Intent intent,
            String str,
            IIntentReceiver iIntentReceiver,
            String str2,
            Bundle bundle)
            throws RemoteException;

    void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3, Intent intent)
            throws RemoteException;

    void setActivityController(IActivityController iActivityController, boolean z)
            throws RemoteException;

    void setActivityLocusContext(ComponentName componentName, LocusId locusId, IBinder iBinder)
            throws RemoteException;

    void setAgentApp(String str, String str2) throws RemoteException;

    void setAlwaysFinish(boolean z) throws RemoteException;

    void setDebugApp(String str, boolean z, boolean z2) throws RemoteException;

    void setDeterministicUidIdle(boolean z) throws RemoteException;

    void setDumpHeapDebugLimit(String str, int i, long j, String str2) throws RemoteException;

    boolean setFGSFilter(int i, boolean z) throws RemoteException;

    void setFocusedRootTask(int i) throws RemoteException;

    void setHasTopUi(boolean z) throws RemoteException;

    boolean setLongLiveApp(String str) throws RemoteException;

    boolean setLongLiveTask(int i) throws RemoteException;

    void setPackageScreenCompatMode(String str, int i) throws RemoteException;

    void setPersistentVrThread(int i) throws RemoteException;

    void setProcessImportant(IBinder iBinder, int i, boolean z, String str) throws RemoteException;

    void setProcessLimit(int i) throws RemoteException;

    boolean setProcessMemoryTrimLevel(String str, int i, int i2) throws RemoteException;

    boolean setProcessSlowdown(int i, boolean z) throws RemoteException;

    void setProcessStateSummary(byte[] bArr) throws RemoteException;

    void setRenderThread(int i) throws RemoteException;

    void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException;

    void setServiceForeground(
            ComponentName componentName,
            IBinder iBinder,
            int i,
            Notification notification,
            int i2,
            int i3)
            throws RemoteException;

    void setStopUserOnSwitch(int i) throws RemoteException;

    void setTTSPkgInfo(int i) throws RemoteException;

    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setThemeOverlayReady(int i) throws RemoteException;

    void setUserIsMonkey(boolean z) throws RemoteException;

    boolean shouldServiceTimeOut(ComponentName componentName, IBinder iBinder)
            throws RemoteException;

    void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException;

    void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z)
            throws RemoteException;

    boolean shutdown(int i) throws RemoteException;

    void signalPersistentProcesses(int i) throws RemoteException;

    @Deprecated
    int startActivity(
            IApplicationThread iApplicationThread,
            String str,
            Intent intent,
            String str2,
            IBinder iBinder,
            String str3,
            int i,
            int i2,
            ProfilerInfo profilerInfo,
            Bundle bundle)
            throws RemoteException;

    @Deprecated
    int startActivityAsUser(
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
            int i3)
            throws RemoteException;

    int startActivityAsUserWithFeature(
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

    int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

    int startActivityWithFeature(
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

    boolean startBinderTracking() throws RemoteException;

    void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException;

    void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException;

    boolean startInstrumentation(
            ComponentName componentName,
            String str,
            int i,
            Bundle bundle,
            IInstrumentationWatcher iInstrumentationWatcher,
            IUiAutomationConnection iUiAutomationConnection,
            int i2,
            String str2)
            throws RemoteException;

    boolean startProfile(int i) throws RemoteException;

    boolean startProfileWithListener(int i, IProgressListener iProgressListener)
            throws RemoteException;

    ComponentName startService(
            IApplicationThread iApplicationThread,
            Intent intent,
            String str,
            boolean z,
            String str2,
            String str3,
            int i)
            throws RemoteException;

    void startSystemLockTaskMode(int i) throws RemoteException;

    boolean startUserInBackground(int i) throws RemoteException;

    boolean startUserInBackgroundVisibleOnDisplay(
            int i, int i2, IProgressListener iProgressListener) throws RemoteException;

    boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener)
            throws RemoteException;

    boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener)
            throws RemoteException;

    void stopAppForUser(String str, int i) throws RemoteException;

    void stopAppSwitches() throws RemoteException;

    boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor)
            throws RemoteException;

    void stopDelegateShellPermissionIdentity() throws RemoteException;

    boolean stopProfile(int i) throws RemoteException;

    int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i)
            throws RemoteException;

    boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i)
            throws RemoteException;

    int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException;

    int stopUserExceptCertainProfiles(int i, boolean z, IStopUserCallback iStopUserCallback)
            throws RemoteException;

    int stopUserWithCallback(int i, IStopUserCallback iStopUserCallback) throws RemoteException;

    int stopUserWithDelayedLocking(int i, IStopUserCallback iStopUserCallback)
            throws RemoteException;

    void suppressResizeConfigChanges(boolean z) throws RemoteException;

    boolean switchUser(int i) throws RemoteException;

    void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException;

    void unbindFinished(IBinder iBinder, Intent intent, boolean z) throws RemoteException;

    boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException;

    void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i)
            throws RemoteException;

    void unhandledBack() throws RemoteException;

    @Deprecated
    boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener)
            throws RemoteException;

    boolean unlockUser2(int i, IProgressListener iProgressListener) throws RemoteException;

    void unregisterIntentSenderCancelListener(
            IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException;

    void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void unregisterUidFrozenStateChangedCallback(
            IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException;

    void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException;

    void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver)
            throws RemoteException;

    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    boolean updateConfiguration(Configuration configuration) throws RemoteException;

    void updateFlingerFlag(int i, String str) throws RemoteException;

    void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

    boolean updateMccMncConfiguration(String str, String str2) throws RemoteException;

    void updatePersistentConfiguration(Configuration configuration) throws RemoteException;

    void updatePersistentConfigurationAndLocaleOverlays(
            Configuration configuration, String str, String str2, LocaleList localeList)
            throws RemoteException;

    void updatePersistentConfigurationWithAttribution(
            Configuration configuration, String str, String str2) throws RemoteException;

    boolean updateRestrictionInfo(
            SemAppRestrictionManager.RestrictionInfo restrictionInfo,
            List<SemAppRestrictionManager.AppRestrictionInfo> list)
            throws RemoteException;

    void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2)
            throws RemoteException;

    void waitForBroadcastBarrier() throws RemoteException;

    void waitForBroadcastIdle() throws RemoteException;

    void waitForNetworkStateUpdate(long j) throws RemoteException;

    public static class Default implements IActivityManager {
        @Override // android.app.IActivityManager
        public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerUidObserver(
                IUidObserver observer, int which, int cutpoint, String callingPackage)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unregisterUidObserver(IUidObserver observer) throws RemoteException {}

        @Override // android.app.IActivityManager
        public IBinder registerUidObserverForUids(
                IUidObserver observer, int which, int cutpoint, String callingPackage, int[] uids)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void addUidToObserver(IBinder observerToken, String callingPackage, int uid)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void removeUidFromObserver(IBinder observerToken, String callingPackage, int uid)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkPermission(String permission, int pid, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void logFgsApiBegin(int apiType, int appUid, int appPid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void logFgsApiEnd(int apiType, int appUid, int appPid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void logFgsApiStateChanged(int apiType, int state, int appUid, int appPid)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void handleApplicationCrash(
                IBinder app, ApplicationErrorReport.ParcelableCrashInfo crashInfo)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int startActivity(
                IApplicationThread caller,
                String callingPackage,
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

        @Override // android.app.IActivityManager
        public int startActivityWithFeature(
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

        @Override // android.app.IActivityManager
        public void unhandledBack() throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean finishActivity(IBinder token, int code, Intent data, int finishTask)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public Intent registerReceiver(
                IApplicationThread caller,
                String callerPackage,
                IIntentReceiver receiver,
                IntentFilter filter,
                String requiredPermission,
                int userId,
                int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Intent registerReceiverWithFeature(
                IApplicationThread caller,
                String callerPackage,
                String callingFeatureId,
                String receiverId,
                IIntentReceiver receiver,
                IntentFilter filter,
                String requiredPermission,
                int userId,
                int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {}

        @Override // android.app.IActivityManager
        public int broadcastIntent(
                IApplicationThread caller,
                Intent intent,
                String resolvedType,
                IIntentReceiver resultTo,
                int resultCode,
                String resultData,
                Bundle map,
                String[] requiredPermissions,
                int appOp,
                Bundle options,
                boolean serialized,
                boolean sticky,
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int broadcastIntentWithFeature(
                IApplicationThread caller,
                String callingFeatureId,
                Intent intent,
                String resolvedType,
                IIntentReceiver resultTo,
                int resultCode,
                String resultData,
                Bundle map,
                String[] requiredPermissions,
                String[] excludePermissions,
                String[] excludePackages,
                int appOp,
                Bundle options,
                boolean serialized,
                boolean sticky,
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void finishReceiver(
                IBinder who,
                int resultCode,
                String resultData,
                Bundle map,
                boolean abortBroadcast,
                int flags)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void attachApplication(IApplicationThread app, long startSeq)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void finishAttachApplication(long startSeq, long timestampApplicationOnCreateNs)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToFront(
                IApplicationThread caller,
                String callingPackage,
                int task,
                int flags,
                Bundle options)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProvider(
                IApplicationThread caller,
                String callingPackage,
                String name,
                int userId,
                boolean stable)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void publishContentProviders(
                IApplicationThread caller, List<ContentProviderHolder> providers)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean refContentProvider(IBinder connection, int stableDelta, int unstableDelta)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public PendingIntent getRunningServiceControlPanel(ComponentName service)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ComponentName startService(
                IApplicationThread caller,
                Intent service,
                String resolvedType,
                boolean requireForeground,
                String callingPackage,
                String callingFeatureId,
                int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int stopService(
                IApplicationThread caller, Intent service, String resolvedType, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindService(
                IApplicationThread caller,
                IBinder token,
                Intent service,
                String resolvedType,
                IServiceConnection connection,
                long flags,
                String callingPackage,
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindServiceInstance(
                IApplicationThread caller,
                IBinder token,
                Intent service,
                String resolvedType,
                IServiceConnection connection,
                long flags,
                String instanceName,
                String callingPackage,
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void updateServiceGroup(IServiceConnection connection, int group, int importance)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean unbindService(IServiceConnection connection) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void publishService(IBinder token, Intent intent, IBinder service)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setAgentApp(String packageName, String agent) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setAlwaysFinish(boolean enabled) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean startInstrumentation(
                ComponentName className,
                String profileFile,
                int flags,
                Bundle arguments,
                IInstrumentationWatcher watcher,
                IUiAutomationConnection connection,
                int userId,
                String abiOverride)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void addInstrumentationResults(IApplicationThread target, Bundle results)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void finishInstrumentation(IApplicationThread target, int resultCode, Bundle results)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public Configuration getConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean updateConfiguration(Configuration values) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean updateMccMncConfiguration(String mcc, String mnc) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopServiceToken(ComponentName className, IBinder token, int startId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setProcessLimit(int max) throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getProcessLimit() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkUriPermission(
                Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkContentUriPermissionFull(Uri uri, int pid, int uid, int mode, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int[] checkUriPermissions(
                List<Uri> uris, int pid, int uid, int mode, int userId, IBinder callerToken)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void grantUriPermission(
                IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void revokeUriPermission(
                IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setActivityController(IActivityController watcher, boolean imAMonkey)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void showWaitingForDebugger(IApplicationThread who, boolean waiting)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void signalPersistentProcesses(int signal) throws RemoteException {}

        @Override // android.app.IActivityManager
        public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void serviceDoneExecuting(
                IBinder token, int type, int startId, int res, Intent intent)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public IIntentSender getIntentSender(
                int type,
                String packageName,
                IBinder token,
                String resultWho,
                int requestCode,
                Intent[] intents,
                String[] resolvedTypes,
                int flags,
                Bundle options,
                int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IIntentSender getIntentSenderWithFeature(
                int type,
                String packageName,
                String featureId,
                IBinder token,
                String resultWho,
                int requestCode,
                Intent[] intents,
                String[] resolvedTypes,
                int flags,
                Bundle options,
                int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void cancelIntentSender(IIntentSender sender) throws RemoteException {}

        @Override // android.app.IActivityManager
        public ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender sender)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean registerIntentSenderCancelListenerEx(
                IIntentSender sender, IResultReceiver receiver) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void unregisterIntentSenderCancelListener(
                IIntentSender sender, IResultReceiver receiver) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void enterSafeMode() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void noteWakeupAlarm(
                IIntentSender sender,
                WorkSource workSource,
                int sourceUid,
                String sourcePkg,
                String tag)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void removeContentProvider(IBinder connection, boolean stable)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setRequestedOrientation(IBinder token, int requestedOrientation)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unbindFinished(IBinder token, Intent service, boolean doRebind)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setProcessImportant(IBinder token, int pid, boolean isForeground, String reason)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setServiceForeground(
                ComponentName className,
                IBinder token,
                int id,
                Notification notification,
                int flags,
                int foregroundServiceType)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getForegroundServiceType(ComponentName className, IBinder token)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {}

        @Override // android.app.IActivityManager
        public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState()
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean clearApplicationUserData(
                String packageName, boolean keepState, IPackageDataObserver observer, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppForUser(String packageName, int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean registerForegroundServiceObserver(IForegroundServiceObserver callback)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void forceStopPackage(String packageName, int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void forceStopPackageEvenWhenStopping(String packageName, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void forceStopPackageByAdmin(String packageName, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean killPids(int[] pids, String reason, boolean secure) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses()
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IBinder peekService(Intent service, String resolvedType, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean profileControl(
                String process,
                int userId,
                boolean start,
                ProfilerInfo profilerInfo,
                int profileType)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean shutdown(int timeout) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppSwitches() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void resumeAppSwitches() throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean bindBackupAgent(
                String packageName, int backupRestoreMode, int targetUserId, int backupDestination)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void backupAgentCreated(String packageName, IBinder agent, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unbindBackupAgent(ApplicationInfo appInfo) throws RemoteException {}

        @Override // android.app.IActivityManager
        public int handleIncomingUser(
                int callingPid,
                int callingUid,
                int userId,
                boolean allowAll,
                boolean requireFull,
                String name,
                String callerPackage)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void addPackageDependency(String packageName) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void killApplication(
                String pkg, int appId, int userId, String reason, int exitInfoReason)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void closeSystemDialogs(String reason) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void closeSystemDialogsInDisplay(String reason, int displayId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killApplicationProcess(String processName, int uid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean handleApplicationWtf(
                IBinder app,
                String tag,
                boolean system,
                ApplicationErrorReport.ParcelableCrashInfo crashInfo,
                int immediateCallerPid)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killBackgroundProcesses(String packageName, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isUserAMonkey() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void finishHeavyWeightApp() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void handleApplicationStrictModeViolation(
                IBinder app, int penaltyMask, StrictMode.ViolationInfo crashInfo)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void registerStrictModeCallback(IBinder binder) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void crashApplicationWithType(
                int uid,
                int initialPid,
                String packageName,
                int userId,
                String message,
                boolean force,
                int exceptionTypeId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void crashApplicationWithTypeWithExtras(
                int uid,
                int initialPid,
                String packageName,
                int userId,
                String message,
                boolean force,
                int exceptionTypeId,
                Bundle extras)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void getMimeTypeFilterAsync(Uri uri, int userId, RemoteCallback resultCallback)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean dumpHeap(
                String process,
                int userId,
                boolean managed,
                boolean mallocInfo,
                boolean runGc,
                String dumpBitmaps,
                String path,
                ParcelFileDescriptor fd,
                RemoteCallback finishCallback)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUserRunning(int userid, int flags) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setPackageScreenCompatMode(String packageName, int mode)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean switchUser(int userid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public String getSwitchingFromUserMessage() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getSwitchingToUserMessage() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void setStopUserOnSwitch(int value) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean removeTask(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerProcessObserver(IProcessObserver observer) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unregisterProcessObserver(IProcessObserver observer) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isIntentSenderTargetedToPackage(IIntentSender sender)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfiguration(Configuration values) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void updatePersistentConfigurationWithAttribution(
                Configuration values, String callingPackageName, String callingAttributionTag)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public long[] getProcessPss(int[] pids) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void showBootMessage(CharSequence msg, boolean always) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void killAllBackgroundProcesses() throws RemoteException {}

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProviderExternal(
                String name, int userId, IBinder token, String tag) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternal(String name, IBinder token)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void removeContentProviderExternalAsUser(String name, IBinder token, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean killProcessesBelowForeground(String reason) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public UserInfo getCurrentUser() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getCurrentUserId() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void unstableProviderDied(IBinder connection) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUser(
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
                int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUserWithFeature(
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

        @Override // android.app.IActivityManager
        public int stopUser(
                int userid, boolean stopProfileRegardlessOfParent, IStopUserCallback callback)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserWithCallback(int userid, IStopUserCallback callback)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserExceptCertainProfiles(
                int userid, boolean stopProfileRegardlessOfParent, IStopUserCallback callback)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserWithDelayedLocking(int userid, IStopUserCallback callback)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void registerUserSwitchObserver(IUserSwitchObserver observer, String name)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unregisterUserSwitchObserver(IUserSwitchObserver observer)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int[] getRunningUserIds() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void requestSystemServerHeapDump() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestBugReport(int bugreportType) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestBugReportWithDescription(
                String shareTitle, String shareDescription, int bugreportType)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestTelephonyBugReport(String shareTitle, String shareDescription)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestWifiBugReport(String shareTitle, String shareDescription)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReportWithDescription(
                String shareTitle, String shareDescription) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReport() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestBugReportWithExtraAttachment(Uri extraAttachment)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestFullBugReport() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void requestRemoteBugReport(long nonce) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean launchBugReportHandlerApp() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<String> getBugreportWhitelistedPackages() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killUid(int appId, int userId, String reason) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setUserIsMonkey(boolean monkey) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void hang(IBinder who, boolean allowRestart) throws RemoteException {}

        @Override // android.app.IActivityManager
        public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setFocusedRootTask(int taskId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void restart() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void performIdleMaintenance() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {}

        @Override // android.app.IActivityManager
        public Rect getTaskBounds(int taskId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean setProcessMemoryTrimLevel(String process, int userId, int level)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public String getTagForIntentSender(IIntentSender sender, String prefix)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackground(int userid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void startSystemLockTaskMode(int taskId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isTopOfTask(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void bootAnimationComplete() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setThemeOverlayReady(int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void registerTaskStackListener(ITaskStackListener listener) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unregisterTaskStackListener(ITaskStackListener listener)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void setDumpHeapDebugLimit(
                String processName, int uid, long maxMemSize, String reportPackage)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void dumpHeapFinished(String path) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void updateLockTaskPackages(int userId, String[] packages) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void noteAlarmStart(
                IIntentSender sender, WorkSource workSource, int sourceUid, String tag)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void noteAlarmFinish(
                IIntentSender sender, WorkSource workSource, int sourceUid, String tag)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getPackageProcessState(String packageName, String callingPackage)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean startBinderTracking() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean unlockUser(
                int userid, byte[] token, byte[] secret, IProgressListener listener)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser2(int userId, IProgressListener listener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killPackageDependents(String packageName, int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void makePackageIdle(String packageName, int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setDeterministicUidIdle(boolean deterministic) throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getMemoryTrimLevel() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isVrModePackageEnabled(ComponentName packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void notifyLockedProfile(int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void startConfirmDeviceCredentialIntent(Intent intent, Bundle options)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void sendIdleJobTrigger() throws RemoteException {}

        @Override // android.app.IActivityManager
        public int sendIntentSender(
                IApplicationThread caller,
                IIntentSender target,
                IBinder whitelistToken,
                int code,
                Intent intent,
                String resolvedType,
                IIntentReceiver finishedReceiver,
                String requiredPermission,
                Bundle options)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean isBackgroundRestricted(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setRenderThread(int tid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setHasTopUi(boolean hasTopUi) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void cancelTaskWindowTransition(int taskId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void scheduleApplicationInfoChanged(List<String> packageNames, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setPersistentVrThread(int tid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void backgroundAllowlistUid(int uid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundWithListener(
                int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void startDelegateShellPermissionIdentity(int uid, String[] permissions)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void stopDelegateShellPermissionIdentity() throws RemoteException {}

        @Override // android.app.IActivityManager
        public List<String> getDelegatedShellPermissions() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInForegroundWithListener(
                int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void appNotResponding(String reason) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void doActiveLaunch(String packageName, boolean isLauncher, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(
                String packageName, int maxNum, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void addApplicationStartInfoCompleteListener(
                IApplicationStartInfoCompleteListener listener, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void removeApplicationStartInfoCompleteListener(
                IApplicationStartInfoCompleteListener listener, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void addStartInfoTimestamp(int key, long timestampNs, int userId)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void reportStartInfoViewTimestamps(
                long renderThreadDrawStartTimeNs, long framePresentedTimeNs)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(
                String packageName, int pid, int maxNum, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void killProcessesWhenImperceptible(int[] pids, String reason)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setActivityLocusContext(
                ComponentName activity, LocusId locusId, IBinder appToken) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void setProcessStateSummary(byte[] state) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isAppFreezerSupported() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isAppFreezerEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killUidForPermissionChange(int appId, int userId, String reason)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void resetAppErrors() throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean enableAppFreezer(boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean enableFgsNotificationRateLimit(boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void holdLock(IBinder token, int durationMs) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean startProfile(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean stopProfile(int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice queryIntentComponentsForIntentSender(
                IIntentSender sender, int matchFlags) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessCapabilities(int uid, String callingPackage)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void waitForBroadcastIdle() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void waitForBroadcastBarrier() throws RemoteException {}

        @Override // android.app.IActivityManager
        public void forceDelayBroadcastDelivery(String targetPackage, long delayedDurationMs)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isProcessFrozen(int pid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getBackgroundRestrictionExemptionReason(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public String[] queryRegisteredReceiverPackages(
                Intent intent, String resolvedType, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundVisibleOnDisplay(
                int userid, int displayId, IProgressListener unlockProgressListener)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startProfileWithListener(
                int userid, IProgressListener unlockProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int restartUserInBackground(int userId, int userStartMode) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean shouldServiceTimeOut(ComponentName className, IBinder token)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean hasServiceTimeLimitExceeded(ComponentName className, IBinder token)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback callback)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void unregisterUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback callback)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int[] getUidFrozenState(int[] uids) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String[] getContentByTask(int taskId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveApps() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean addLongLiveApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean removeLongLiveApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getMaxLongLiveApps() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean setLongLiveTask(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean clearLongLiveTask(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveProcesses() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveProcessesForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List getLongLiveTaskIdsForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean getAutoRemoveRecents(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerDedicatedCallback(RemoteCallback resultCallback, int type)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean setLongLiveApp(String packageName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public String getLongLiveApp() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean moveTaskToBack(int taskId, boolean keepAlive) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean moveTaskToBackWithBundle(int taskId, boolean keepAlive, Bundle options)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void dismissUserSwitchingDialog(int userId) throws RemoteException {}

        @Override // android.app.IActivityManager
        public Configuration getGlobalConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int checkPermissionForDevice(String permission, int pid, int uid, int deviceId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void frozenBinderTransactionDetected(int debugPid, int code, int flags, int err)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public int getBindingUidProcessState(int uid, String callingPackage)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public long getUidLastIdleElapsedTime(int uid, String callingPackage)
                throws RemoteException {
            return 0L;
        }

        @Override // android.app.IActivityManager
        public void addOverridePermissionState(
                int originatingUid, int uid, String permission, int result)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void removeOverridePermissionState(int originatingUid, int uid, String permission)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void clearOverridePermissionStates(int originatingUid, int uid)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void clearAllOverridePermissionStates(int originatingUid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void noteAppRestrictionEnabled(
                String packageName,
                int uid,
                int restrictionType,
                boolean enabled,
                int reason,
                String subReason,
                int source,
                long threshold)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void checkProfileForADCP(int pid, String pkg) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void preloadBoosterAppsFromIpm(List<String> packageNames, int type)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void updateFlingerFlag(int pid, String pkg) throws RemoteException {}

        @Override // android.app.IActivityManager
        public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(
                int type, String packageName, int uid) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean canRestrict(int type, String packageName, int uid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean restrict(int type, int state, boolean byUser, String packageName, int uid)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int type)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList()
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int type)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean updateRestrictionInfo(
                SemAppRestrictionManager.RestrictionInfo info,
                List<SemAppRestrictionManager.AppRestrictionInfo> list)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setTTSPkgInfo(int uid) throws RemoteException {}

        @Override // android.app.IActivityManager
        public void clearTTSPkgInfo() throws RemoteException {}

        @Override // android.app.IActivityManager
        public ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int flags, int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getPackageFromAppProcesses(int pid) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfigurationAndLocaleOverlays(
                Configuration values,
                String callingPackageName,
                String callingAttributionTag,
                LocaleList ll)
                throws RemoteException {}

        @Override // android.app.IActivityManager
        public void reportAbnormalUsage(int pid, int type) throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isHeapDumpAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean setFGSFilter(int uid, boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void resetAbnormalList() throws RemoteException {}

        @Override // android.app.IActivityManager
        public boolean isFreezableUid(int uid) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean setProcessSlowdown(int pid, boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int[] getIsolatedProcessList() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Bundle getOptionsForIntentSender(IIntentSender sender) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IActivityManager {
        public static final String DESCRIPTOR = "android.app.IActivityManager";
        static final int TRANSACTION_addApplicationStartInfoCompleteListener = 227;
        static final int TRANSACTION_addInstrumentationResults = 45;
        static final int TRANSACTION_addLongLiveApp = 263;
        static final int TRANSACTION_addOverridePermissionState = 283;
        static final int TRANSACTION_addPackageDependency = 98;
        static final int TRANSACTION_addStartInfoTimestamp = 229;
        static final int TRANSACTION_addUidToObserver = 5;
        static final int TRANSACTION_appNotResponding = 224;
        static final int TRANSACTION_appNotRespondingViaProvider = 173;
        static final int TRANSACTION_attachApplication = 25;
        static final int TRANSACTION_backgroundAllowlistUid = 217;
        static final int TRANSACTION_backupAgentCreated = 95;
        static final int TRANSACTION_bindBackupAgent = 94;
        static final int TRANSACTION_bindService = 36;
        static final int TRANSACTION_bindServiceInstance = 37;
        static final int TRANSACTION_bootAnimationComplete = 182;
        static final int TRANSACTION_broadcastIntent = 21;
        static final int TRANSACTION_broadcastIntentWithFeature = 22;
        static final int TRANSACTION_canRestrict = 292;
        static final int TRANSACTION_cancelIntentSender = 65;
        static final int TRANSACTION_cancelTaskWindowTransition = 213;
        static final int TRANSACTION_checkContentUriPermissionFull = 54;
        static final int TRANSACTION_checkPermission = 9;
        static final int TRANSACTION_checkPermissionForDevice = 279;
        static final int TRANSACTION_checkProfileForADCP = 288;
        static final int TRANSACTION_checkUriPermission = 53;
        static final int TRANSACTION_checkUriPermissions = 55;
        static final int TRANSACTION_clearAllOverridePermissionStates = 286;
        static final int TRANSACTION_clearApplicationUserData = 80;
        static final int TRANSACTION_clearLongLiveTask = 267;
        static final int TRANSACTION_clearOverridePermissionStates = 285;
        static final int TRANSACTION_clearRestrictionInfo = 298;
        static final int TRANSACTION_clearTTSPkgInfo = 300;
        static final int TRANSACTION_closeSystemDialogs = 100;
        static final int TRANSACTION_closeSystemDialogsInDisplay = 101;
        static final int TRANSACTION_crashApplicationWithType = 112;
        static final int TRANSACTION_crashApplicationWithTypeWithExtras = 113;
        static final int TRANSACTION_dismissUserSwitchingDialog = 277;
        static final int TRANSACTION_doActiveLaunch = 225;
        static final int TRANSACTION_dumpHeap = 115;
        static final int TRANSACTION_dumpHeapFinished = 191;
        static final int TRANSACTION_enableAppFreezer = 239;
        static final int TRANSACTION_enableFgsNotificationRateLimit = 240;
        static final int TRANSACTION_enterSafeMode = 69;
        static final int TRANSACTION_finishActivity = 17;
        static final int TRANSACTION_finishAttachApplication = 26;
        static final int TRANSACTION_finishHeavyWeightApp = 108;
        static final int TRANSACTION_finishInstrumentation = 46;
        static final int TRANSACTION_finishReceiver = 24;
        static final int TRANSACTION_forceDelayBroadcastDelivery = 248;
        static final int TRANSACTION_forceStopPackage = 83;
        static final int TRANSACTION_forceStopPackageByAdmin = 85;
        static final int TRANSACTION_forceStopPackageEvenWhenStopping = 84;
        static final int TRANSACTION_frozenBinderTransactionDetected = 280;
        static final int TRANSACTION_getAllRestrictedList = 295;
        static final int TRANSACTION_getAllRootTaskInfos = 167;
        static final int TRANSACTION_getAutoRemoveRecents = 271;
        static final int TRANSACTION_getBackgroundRestrictionExemptionReason = 250;
        static final int TRANSACTION_getBindingUidProcessState = 281;
        static final int TRANSACTION_getBugreportWhitelistedPackages = 161;
        static final int TRANSACTION_getConfiguration = 47;
        static final int TRANSACTION_getContentByTask = 261;
        static final int TRANSACTION_getContentProvider = 30;
        static final int TRANSACTION_getContentProviderExternal = 131;
        static final int TRANSACTION_getCurrentUser = 136;
        static final int TRANSACTION_getCurrentUserId = 137;
        static final int TRANSACTION_getDelegatedShellPermissions = 221;
        static final int TRANSACTION_getDisplayIdsForStartingVisibleBackgroundUsers = 255;
        static final int TRANSACTION_getFocusedRootTaskInfo = 170;
        static final int TRANSACTION_getForegroundServiceType = 76;
        static final int TRANSACTION_getGlobalConfiguration = 278;
        static final int TRANSACTION_getHistoricalProcessExitReasons = 231;
        static final int TRANSACTION_getHistoricalProcessStartReasons = 226;
        static final int TRANSACTION_getInfoForIntentSender = 66;
        static final int TRANSACTION_getInstalledPackageListFromMARs = 301;
        static final int TRANSACTION_getIntentForIntentSender = 162;
        static final int TRANSACTION_getIntentSender = 63;
        static final int TRANSACTION_getIntentSenderWithFeature = 64;
        static final int TRANSACTION_getIsolatedProcessList = 310;
        static final int TRANSACTION_getLaunchedFromPackage = 163;
        static final int TRANSACTION_getLaunchedFromUid = 138;
        static final int TRANSACTION_getLifeMonitor = 222;
        static final int TRANSACTION_getLockTaskModeState = 189;
        static final int TRANSACTION_getLongLiveApp = 274;
        static final int TRANSACTION_getLongLiveApps = 262;
        static final int TRANSACTION_getLongLiveProcesses = 268;
        static final int TRANSACTION_getLongLiveProcessesForUser = 269;
        static final int TRANSACTION_getLongLiveTaskIdsForUser = 270;
        static final int TRANSACTION_getMaxLongLiveApps = 265;
        static final int TRANSACTION_getMemoryInfo = 78;
        static final int TRANSACTION_getMemoryTrimLevel = 204;
        static final int TRANSACTION_getMimeTypeFilterAsync = 114;
        static final int TRANSACTION_getMyMemoryState = 134;
        static final int TRANSACTION_getOptionsForIntentSender = 311;
        static final int TRANSACTION_getPackageFromAppProcesses = 302;
        static final int TRANSACTION_getPackageProcessState = 195;
        static final int TRANSACTION_getProcessLimit = 52;
        static final int TRANSACTION_getProcessMemoryInfo = 102;
        static final int TRANSACTION_getProcessPss = 128;
        static final int TRANSACTION_getProcessesInErrorState = 79;
        static final int TRANSACTION_getRecentTasks = 61;
        static final int TRANSACTION_getRestrictableList = 294;
        static final int TRANSACTION_getRestrictedList = 296;
        static final int TRANSACTION_getRestrictionInfo = 291;
        static final int TRANSACTION_getRunningAppProcesses = 88;
        static final int TRANSACTION_getRunningExternalApplications = 107;
        static final int TRANSACTION_getRunningServiceControlPanel = 33;
        static final int TRANSACTION_getRunningUserIds = 149;
        static final int TRANSACTION_getServices = 87;
        static final int TRANSACTION_getSwitchingFromUserMessage = 119;
        static final int TRANSACTION_getSwitchingToUserMessage = 120;
        static final int TRANSACTION_getTagForIntentSender = 176;
        static final int TRANSACTION_getTaskBounds = 174;
        static final int TRANSACTION_getTaskForActivity = 29;
        static final int TRANSACTION_getTasks = 27;
        static final int TRANSACTION_getUidFrozenState = 260;
        static final int TRANSACTION_getUidLastIdleElapsedTime = 282;
        static final int TRANSACTION_getUidProcessCapabilities = 245;
        static final int TRANSACTION_getUidProcessState = 8;
        static final int TRANSACTION_grantUriPermission = 56;
        static final int TRANSACTION_handleApplicationCrash = 13;
        static final int TRANSACTION_handleApplicationStrictModeViolation = 109;
        static final int TRANSACTION_handleApplicationWtf = 104;
        static final int TRANSACTION_handleIncomingUser = 97;
        static final int TRANSACTION_hang = 166;
        static final int TRANSACTION_hasServiceTimeLimitExceeded = 257;
        static final int TRANSACTION_holdLock = 241;
        static final int TRANSACTION_isAppFreezerEnabled = 236;
        static final int TRANSACTION_isAppFreezerSupported = 235;
        static final int TRANSACTION_isBackgroundRestricted = 210;
        static final int TRANSACTION_isFreezableUid = 308;
        static final int TRANSACTION_isHeapDumpAllowed = 305;
        static final int TRANSACTION_isInLockTaskMode = 178;
        static final int TRANSACTION_isIntentSenderAnActivity = 140;
        static final int TRANSACTION_isIntentSenderTargetedToPackage = 125;
        static final int TRANSACTION_isProcessFrozen = 249;
        static final int TRANSACTION_isTopActivityImmersive = 111;
        static final int TRANSACTION_isTopOfTask = 181;
        static final int TRANSACTION_isUidActive = 7;
        static final int TRANSACTION_isUserAMonkey = 106;
        static final int TRANSACTION_isUserRunning = 116;
        static final int TRANSACTION_isVrModePackageEnabled = 205;
        static final int TRANSACTION_killAllBackgroundProcesses = 130;
        static final int TRANSACTION_killApplication = 99;
        static final int TRANSACTION_killApplicationProcess = 103;
        static final int TRANSACTION_killBackgroundProcesses = 105;
        static final int TRANSACTION_killPackageDependents = 201;
        static final int TRANSACTION_killPids = 86;
        static final int TRANSACTION_killProcessesBelowForeground = 135;
        static final int TRANSACTION_killProcessesWhenImperceptible = 232;
        static final int TRANSACTION_killUid = 164;
        static final int TRANSACTION_killUidForPermissionChange = 237;
        static final int TRANSACTION_launchBugReportHandlerApp = 160;
        static final int TRANSACTION_logFgsApiBegin = 10;
        static final int TRANSACTION_logFgsApiEnd = 11;
        static final int TRANSACTION_logFgsApiStateChanged = 12;
        static final int TRANSACTION_makePackageIdle = 202;
        static final int TRANSACTION_moveActivityTaskToBack = 77;
        static final int TRANSACTION_moveTaskToBack = 275;
        static final int TRANSACTION_moveTaskToBackWithBundle = 276;
        static final int TRANSACTION_moveTaskToFront = 28;
        static final int TRANSACTION_moveTaskToRootTask = 168;
        static final int TRANSACTION_noteAlarmFinish = 194;
        static final int TRANSACTION_noteAlarmStart = 193;
        static final int TRANSACTION_noteAppRestrictionEnabled = 287;
        static final int TRANSACTION_noteWakeupAlarm = 70;
        static final int TRANSACTION_notifyCleartextNetwork = 186;
        static final int TRANSACTION_notifyLockedProfile = 206;
        static final int TRANSACTION_openContentUri = 1;
        static final int TRANSACTION_peekService = 89;
        static final int TRANSACTION_performIdleMaintenance = 172;
        static final int TRANSACTION_preloadBoosterAppsFromIpm = 289;
        static final int TRANSACTION_profileControl = 90;
        static final int TRANSACTION_publishContentProviders = 31;
        static final int TRANSACTION_publishService = 40;
        static final int TRANSACTION_queryIntentComponentsForIntentSender = 244;
        static final int TRANSACTION_queryRegisteredReceiverPackages = 251;
        static final int TRANSACTION_refContentProvider = 32;
        static final int TRANSACTION_registerDedicatedCallback = 272;
        static final int TRANSACTION_registerForegroundServiceObserver = 82;
        static final int TRANSACTION_registerIntentSenderCancelListenerEx = 67;
        static final int TRANSACTION_registerProcessObserver = 123;
        static final int TRANSACTION_registerReceiver = 18;
        static final int TRANSACTION_registerReceiverWithFeature = 19;
        static final int TRANSACTION_registerStrictModeCallback = 110;
        static final int TRANSACTION_registerTaskStackListener = 184;
        static final int TRANSACTION_registerUidFrozenStateChangedCallback = 258;
        static final int TRANSACTION_registerUidObserver = 2;
        static final int TRANSACTION_registerUidObserverForUids = 4;
        static final int TRANSACTION_registerUserSwitchObserver = 147;
        static final int TRANSACTION_removeApplicationStartInfoCompleteListener = 228;
        static final int TRANSACTION_removeContentProvider = 71;
        static final int TRANSACTION_removeContentProviderExternal = 132;
        static final int TRANSACTION_removeContentProviderExternalAsUser = 133;
        static final int TRANSACTION_removeLongLiveApp = 264;
        static final int TRANSACTION_removeOverridePermissionState = 284;
        static final int TRANSACTION_removeTask = 122;
        static final int TRANSACTION_removeUidFromObserver = 6;
        static final int TRANSACTION_reportAbnormalUsage = 304;
        static final int TRANSACTION_reportStartInfoViewTimestamps = 230;
        static final int TRANSACTION_requestBugReport = 151;
        static final int TRANSACTION_requestBugReportWithDescription = 152;
        static final int TRANSACTION_requestBugReportWithExtraAttachment = 157;
        static final int TRANSACTION_requestFullBugReport = 158;
        static final int TRANSACTION_requestInteractiveBugReport = 156;
        static final int TRANSACTION_requestInteractiveBugReportWithDescription = 155;
        static final int TRANSACTION_requestRemoteBugReport = 159;
        static final int TRANSACTION_requestSystemServerHeapDump = 150;
        static final int TRANSACTION_requestTelephonyBugReport = 153;
        static final int TRANSACTION_requestWifiBugReport = 154;
        static final int TRANSACTION_resetAbnormalList = 307;
        static final int TRANSACTION_resetAppErrors = 238;
        static final int TRANSACTION_resizeTask = 188;
        static final int TRANSACTION_restart = 171;
        static final int TRANSACTION_restartUserInBackground = 254;
        static final int TRANSACTION_restrict = 293;
        static final int TRANSACTION_resumeAppSwitches = 93;
        static final int TRANSACTION_revokeUriPermission = 57;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 214;
        static final int TRANSACTION_sendIdleJobTrigger = 208;
        static final int TRANSACTION_sendIntentSender = 209;
        static final int TRANSACTION_serviceDoneExecuting = 62;
        static final int TRANSACTION_setActivityController = 58;
        static final int TRANSACTION_setActivityLocusContext = 233;
        static final int TRANSACTION_setAgentApp = 42;
        static final int TRANSACTION_setAlwaysFinish = 43;
        static final int TRANSACTION_setDebugApp = 41;
        static final int TRANSACTION_setDeterministicUidIdle = 203;
        static final int TRANSACTION_setDumpHeapDebugLimit = 190;
        static final int TRANSACTION_setFGSFilter = 306;
        static final int TRANSACTION_setFocusedRootTask = 169;
        static final int TRANSACTION_setHasTopUi = 212;
        static final int TRANSACTION_setLongLiveApp = 273;
        static final int TRANSACTION_setLongLiveTask = 266;
        static final int TRANSACTION_setPackageScreenCompatMode = 117;
        static final int TRANSACTION_setPersistentVrThread = 215;
        static final int TRANSACTION_setProcessImportant = 74;
        static final int TRANSACTION_setProcessLimit = 51;
        static final int TRANSACTION_setProcessMemoryTrimLevel = 175;
        static final int TRANSACTION_setProcessSlowdown = 309;
        static final int TRANSACTION_setProcessStateSummary = 234;
        static final int TRANSACTION_setRenderThread = 211;
        static final int TRANSACTION_setRequestedOrientation = 72;
        static final int TRANSACTION_setServiceForeground = 75;
        static final int TRANSACTION_setStopUserOnSwitch = 121;
        static final int TRANSACTION_setTTSPkgInfo = 299;
        static final int TRANSACTION_setTaskResizeable = 187;
        static final int TRANSACTION_setThemeOverlayReady = 183;
        static final int TRANSACTION_setUserIsMonkey = 165;
        static final int TRANSACTION_shouldServiceTimeOut = 256;
        static final int TRANSACTION_showBootMessage = 129;
        static final int TRANSACTION_showWaitingForDebugger = 59;
        static final int TRANSACTION_shutdown = 91;
        static final int TRANSACTION_signalPersistentProcesses = 60;
        static final int TRANSACTION_startActivity = 14;
        static final int TRANSACTION_startActivityAsUser = 141;
        static final int TRANSACTION_startActivityAsUserWithFeature = 142;
        static final int TRANSACTION_startActivityFromRecents = 179;
        static final int TRANSACTION_startActivityWithFeature = 15;
        static final int TRANSACTION_startBinderTracking = 196;
        static final int TRANSACTION_startConfirmDeviceCredentialIntent = 207;
        static final int TRANSACTION_startDelegateShellPermissionIdentity = 219;
        static final int TRANSACTION_startInstrumentation = 44;
        static final int TRANSACTION_startProfile = 242;
        static final int TRANSACTION_startProfileWithListener = 253;
        static final int TRANSACTION_startService = 34;
        static final int TRANSACTION_startSystemLockTaskMode = 180;
        static final int TRANSACTION_startUserInBackground = 177;
        static final int TRANSACTION_startUserInBackgroundVisibleOnDisplay = 252;
        static final int TRANSACTION_startUserInBackgroundWithListener = 218;
        static final int TRANSACTION_startUserInForegroundWithListener = 223;
        static final int TRANSACTION_stopAppForUser = 81;
        static final int TRANSACTION_stopAppSwitches = 92;
        static final int TRANSACTION_stopBinderTrackingAndDump = 197;
        static final int TRANSACTION_stopDelegateShellPermissionIdentity = 220;
        static final int TRANSACTION_stopProfile = 243;
        static final int TRANSACTION_stopService = 35;
        static final int TRANSACTION_stopServiceToken = 50;
        static final int TRANSACTION_stopUser = 143;
        static final int TRANSACTION_stopUserExceptCertainProfiles = 145;
        static final int TRANSACTION_stopUserWithCallback = 144;
        static final int TRANSACTION_stopUserWithDelayedLocking = 146;
        static final int TRANSACTION_suppressResizeConfigChanges = 198;
        static final int TRANSACTION_switchUser = 118;
        static final int TRANSACTION_unbindBackupAgent = 96;
        static final int TRANSACTION_unbindFinished = 73;
        static final int TRANSACTION_unbindService = 39;
        static final int TRANSACTION_unbroadcastIntent = 23;
        static final int TRANSACTION_unhandledBack = 16;
        static final int TRANSACTION_unlockUser = 199;
        static final int TRANSACTION_unlockUser2 = 200;
        static final int TRANSACTION_unregisterIntentSenderCancelListener = 68;
        static final int TRANSACTION_unregisterProcessObserver = 124;
        static final int TRANSACTION_unregisterReceiver = 20;
        static final int TRANSACTION_unregisterTaskStackListener = 185;
        static final int TRANSACTION_unregisterUidFrozenStateChangedCallback = 259;
        static final int TRANSACTION_unregisterUidObserver = 3;
        static final int TRANSACTION_unregisterUserSwitchObserver = 148;
        static final int TRANSACTION_unstableProviderDied = 139;
        static final int TRANSACTION_updateConfiguration = 48;
        static final int TRANSACTION_updateFlingerFlag = 290;
        static final int TRANSACTION_updateLockTaskPackages = 192;
        static final int TRANSACTION_updateMccMncConfiguration = 49;
        static final int TRANSACTION_updatePersistentConfiguration = 126;
        static final int TRANSACTION_updatePersistentConfigurationAndLocaleOverlays = 303;
        static final int TRANSACTION_updatePersistentConfigurationWithAttribution = 127;
        static final int TRANSACTION_updateRestrictionInfo = 297;
        static final int TRANSACTION_updateServiceGroup = 38;
        static final int TRANSACTION_waitForBroadcastBarrier = 247;
        static final int TRANSACTION_waitForBroadcastIdle = 246;
        static final int TRANSACTION_waitForNetworkStateUpdate = 216;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IActivityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IActivityManager)) {
                return (IActivityManager) iin;
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
                    return "openContentUri";
                case 2:
                    return "registerUidObserver";
                case 3:
                    return "unregisterUidObserver";
                case 4:
                    return "registerUidObserverForUids";
                case 5:
                    return "addUidToObserver";
                case 6:
                    return "removeUidFromObserver";
                case 7:
                    return "isUidActive";
                case 8:
                    return "getUidProcessState";
                case 9:
                    return "checkPermission";
                case 10:
                    return "logFgsApiBegin";
                case 11:
                    return "logFgsApiEnd";
                case 12:
                    return "logFgsApiStateChanged";
                case 13:
                    return "handleApplicationCrash";
                case 14:
                    return "startActivity";
                case 15:
                    return "startActivityWithFeature";
                case 16:
                    return "unhandledBack";
                case 17:
                    return "finishActivity";
                case 18:
                    return "registerReceiver";
                case 19:
                    return "registerReceiverWithFeature";
                case 20:
                    return "unregisterReceiver";
                case 21:
                    return "broadcastIntent";
                case 22:
                    return "broadcastIntentWithFeature";
                case 23:
                    return "unbroadcastIntent";
                case 24:
                    return "finishReceiver";
                case 25:
                    return "attachApplication";
                case 26:
                    return "finishAttachApplication";
                case 27:
                    return "getTasks";
                case 28:
                    return "moveTaskToFront";
                case 29:
                    return "getTaskForActivity";
                case 30:
                    return "getContentProvider";
                case 31:
                    return "publishContentProviders";
                case 32:
                    return "refContentProvider";
                case 33:
                    return "getRunningServiceControlPanel";
                case 34:
                    return "startService";
                case 35:
                    return "stopService";
                case 36:
                    return "bindService";
                case 37:
                    return "bindServiceInstance";
                case 38:
                    return "updateServiceGroup";
                case 39:
                    return "unbindService";
                case 40:
                    return "publishService";
                case 41:
                    return "setDebugApp";
                case 42:
                    return "setAgentApp";
                case 43:
                    return "setAlwaysFinish";
                case 44:
                    return "startInstrumentation";
                case 45:
                    return "addInstrumentationResults";
                case 46:
                    return "finishInstrumentation";
                case 47:
                    return "getConfiguration";
                case 48:
                    return "updateConfiguration";
                case 49:
                    return "updateMccMncConfiguration";
                case 50:
                    return "stopServiceToken";
                case 51:
                    return "setProcessLimit";
                case 52:
                    return "getProcessLimit";
                case 53:
                    return "checkUriPermission";
                case 54:
                    return "checkContentUriPermissionFull";
                case 55:
                    return "checkUriPermissions";
                case 56:
                    return "grantUriPermission";
                case 57:
                    return "revokeUriPermission";
                case 58:
                    return "setActivityController";
                case 59:
                    return "showWaitingForDebugger";
                case 60:
                    return "signalPersistentProcesses";
                case 61:
                    return "getRecentTasks";
                case 62:
                    return "serviceDoneExecuting";
                case 63:
                    return "getIntentSender";
                case 64:
                    return "getIntentSenderWithFeature";
                case 65:
                    return "cancelIntentSender";
                case 66:
                    return "getInfoForIntentSender";
                case 67:
                    return "registerIntentSenderCancelListenerEx";
                case 68:
                    return "unregisterIntentSenderCancelListener";
                case 69:
                    return "enterSafeMode";
                case 70:
                    return "noteWakeupAlarm";
                case 71:
                    return "removeContentProvider";
                case 72:
                    return "setRequestedOrientation";
                case 73:
                    return "unbindFinished";
                case 74:
                    return "setProcessImportant";
                case 75:
                    return "setServiceForeground";
                case 76:
                    return "getForegroundServiceType";
                case 77:
                    return "moveActivityTaskToBack";
                case 78:
                    return "getMemoryInfo";
                case 79:
                    return "getProcessesInErrorState";
                case 80:
                    return "clearApplicationUserData";
                case 81:
                    return "stopAppForUser";
                case 82:
                    return "registerForegroundServiceObserver";
                case 83:
                    return "forceStopPackage";
                case 84:
                    return "forceStopPackageEvenWhenStopping";
                case 85:
                    return "forceStopPackageByAdmin";
                case 86:
                    return "killPids";
                case 87:
                    return "getServices";
                case 88:
                    return "getRunningAppProcesses";
                case 89:
                    return "peekService";
                case 90:
                    return "profileControl";
                case 91:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 92:
                    return "stopAppSwitches";
                case 93:
                    return "resumeAppSwitches";
                case 94:
                    return "bindBackupAgent";
                case 95:
                    return "backupAgentCreated";
                case 96:
                    return "unbindBackupAgent";
                case 97:
                    return "handleIncomingUser";
                case 98:
                    return "addPackageDependency";
                case 99:
                    return "killApplication";
                case 100:
                    return "closeSystemDialogs";
                case 101:
                    return "closeSystemDialogsInDisplay";
                case 102:
                    return "getProcessMemoryInfo";
                case 103:
                    return "killApplicationProcess";
                case 104:
                    return "handleApplicationWtf";
                case 105:
                    return "killBackgroundProcesses";
                case 106:
                    return "isUserAMonkey";
                case 107:
                    return "getRunningExternalApplications";
                case 108:
                    return "finishHeavyWeightApp";
                case 109:
                    return "handleApplicationStrictModeViolation";
                case 110:
                    return "registerStrictModeCallback";
                case 111:
                    return "isTopActivityImmersive";
                case 112:
                    return "crashApplicationWithType";
                case 113:
                    return "crashApplicationWithTypeWithExtras";
                case 114:
                    return "getMimeTypeFilterAsync";
                case 115:
                    return "dumpHeap";
                case 116:
                    return "isUserRunning";
                case 117:
                    return "setPackageScreenCompatMode";
                case 118:
                    return "switchUser";
                case 119:
                    return "getSwitchingFromUserMessage";
                case 120:
                    return "getSwitchingToUserMessage";
                case 121:
                    return "setStopUserOnSwitch";
                case 122:
                    return "removeTask";
                case 123:
                    return "registerProcessObserver";
                case 124:
                    return "unregisterProcessObserver";
                case 125:
                    return "isIntentSenderTargetedToPackage";
                case 126:
                    return "updatePersistentConfiguration";
                case 127:
                    return "updatePersistentConfigurationWithAttribution";
                case 128:
                    return "getProcessPss";
                case 129:
                    return "showBootMessage";
                case 130:
                    return "killAllBackgroundProcesses";
                case 131:
                    return "getContentProviderExternal";
                case 132:
                    return "removeContentProviderExternal";
                case 133:
                    return "removeContentProviderExternalAsUser";
                case 134:
                    return "getMyMemoryState";
                case 135:
                    return "killProcessesBelowForeground";
                case 136:
                    return "getCurrentUser";
                case 137:
                    return "getCurrentUserId";
                case 138:
                    return "getLaunchedFromUid";
                case 139:
                    return "unstableProviderDied";
                case 140:
                    return "isIntentSenderAnActivity";
                case 141:
                    return "startActivityAsUser";
                case 142:
                    return "startActivityAsUserWithFeature";
                case 143:
                    return "stopUser";
                case 144:
                    return "stopUserWithCallback";
                case 145:
                    return "stopUserExceptCertainProfiles";
                case 146:
                    return "stopUserWithDelayedLocking";
                case 147:
                    return "registerUserSwitchObserver";
                case 148:
                    return "unregisterUserSwitchObserver";
                case 149:
                    return "getRunningUserIds";
                case 150:
                    return "requestSystemServerHeapDump";
                case 151:
                    return "requestBugReport";
                case 152:
                    return "requestBugReportWithDescription";
                case 153:
                    return "requestTelephonyBugReport";
                case 154:
                    return "requestWifiBugReport";
                case 155:
                    return "requestInteractiveBugReportWithDescription";
                case 156:
                    return "requestInteractiveBugReport";
                case 157:
                    return "requestBugReportWithExtraAttachment";
                case 158:
                    return "requestFullBugReport";
                case 159:
                    return "requestRemoteBugReport";
                case 160:
                    return "launchBugReportHandlerApp";
                case 161:
                    return "getBugreportWhitelistedPackages";
                case 162:
                    return "getIntentForIntentSender";
                case 163:
                    return "getLaunchedFromPackage";
                case 164:
                    return "killUid";
                case 165:
                    return "setUserIsMonkey";
                case 166:
                    return "hang";
                case 167:
                    return "getAllRootTaskInfos";
                case 168:
                    return "moveTaskToRootTask";
                case 169:
                    return "setFocusedRootTask";
                case 170:
                    return "getFocusedRootTaskInfo";
                case 171:
                    return DefaultActionNames.ACTION_RESTART;
                case 172:
                    return "performIdleMaintenance";
                case 173:
                    return "appNotRespondingViaProvider";
                case 174:
                    return "getTaskBounds";
                case 175:
                    return "setProcessMemoryTrimLevel";
                case 176:
                    return "getTagForIntentSender";
                case 177:
                    return "startUserInBackground";
                case 178:
                    return "isInLockTaskMode";
                case 179:
                    return "startActivityFromRecents";
                case 180:
                    return "startSystemLockTaskMode";
                case 181:
                    return "isTopOfTask";
                case 182:
                    return "bootAnimationComplete";
                case 183:
                    return "setThemeOverlayReady";
                case 184:
                    return "registerTaskStackListener";
                case 185:
                    return "unregisterTaskStackListener";
                case 186:
                    return "notifyCleartextNetwork";
                case 187:
                    return "setTaskResizeable";
                case 188:
                    return "resizeTask";
                case 189:
                    return "getLockTaskModeState";
                case 190:
                    return "setDumpHeapDebugLimit";
                case 191:
                    return "dumpHeapFinished";
                case 192:
                    return "updateLockTaskPackages";
                case 193:
                    return "noteAlarmStart";
                case 194:
                    return "noteAlarmFinish";
                case 195:
                    return "getPackageProcessState";
                case 196:
                    return "startBinderTracking";
                case 197:
                    return "stopBinderTrackingAndDump";
                case 198:
                    return "suppressResizeConfigChanges";
                case 199:
                    return "unlockUser";
                case 200:
                    return "unlockUser2";
                case 201:
                    return "killPackageDependents";
                case 202:
                    return "makePackageIdle";
                case 203:
                    return "setDeterministicUidIdle";
                case 204:
                    return "getMemoryTrimLevel";
                case 205:
                    return "isVrModePackageEnabled";
                case 206:
                    return "notifyLockedProfile";
                case 207:
                    return "startConfirmDeviceCredentialIntent";
                case 208:
                    return "sendIdleJobTrigger";
                case 209:
                    return "sendIntentSender";
                case 210:
                    return "isBackgroundRestricted";
                case 211:
                    return "setRenderThread";
                case 212:
                    return "setHasTopUi";
                case 213:
                    return "cancelTaskWindowTransition";
                case 214:
                    return "scheduleApplicationInfoChanged";
                case 215:
                    return "setPersistentVrThread";
                case 216:
                    return "waitForNetworkStateUpdate";
                case 217:
                    return "backgroundAllowlistUid";
                case 218:
                    return "startUserInBackgroundWithListener";
                case 219:
                    return "startDelegateShellPermissionIdentity";
                case 220:
                    return "stopDelegateShellPermissionIdentity";
                case 221:
                    return "getDelegatedShellPermissions";
                case 222:
                    return "getLifeMonitor";
                case 223:
                    return "startUserInForegroundWithListener";
                case 224:
                    return "appNotResponding";
                case 225:
                    return "doActiveLaunch";
                case 226:
                    return "getHistoricalProcessStartReasons";
                case 227:
                    return "addApplicationStartInfoCompleteListener";
                case 228:
                    return "removeApplicationStartInfoCompleteListener";
                case 229:
                    return "addStartInfoTimestamp";
                case 230:
                    return "reportStartInfoViewTimestamps";
                case 231:
                    return "getHistoricalProcessExitReasons";
                case 232:
                    return "killProcessesWhenImperceptible";
                case 233:
                    return "setActivityLocusContext";
                case 234:
                    return "setProcessStateSummary";
                case 235:
                    return "isAppFreezerSupported";
                case 236:
                    return "isAppFreezerEnabled";
                case 237:
                    return "killUidForPermissionChange";
                case 238:
                    return "resetAppErrors";
                case 239:
                    return "enableAppFreezer";
                case 240:
                    return "enableFgsNotificationRateLimit";
                case 241:
                    return "holdLock";
                case 242:
                    return "startProfile";
                case 243:
                    return "stopProfile";
                case 244:
                    return "queryIntentComponentsForIntentSender";
                case 245:
                    return "getUidProcessCapabilities";
                case 246:
                    return "waitForBroadcastIdle";
                case 247:
                    return "waitForBroadcastBarrier";
                case 248:
                    return "forceDelayBroadcastDelivery";
                case 249:
                    return "isProcessFrozen";
                case 250:
                    return "getBackgroundRestrictionExemptionReason";
                case 251:
                    return "queryRegisteredReceiverPackages";
                case 252:
                    return "startUserInBackgroundVisibleOnDisplay";
                case 253:
                    return "startProfileWithListener";
                case 254:
                    return "restartUserInBackground";
                case 255:
                    return "getDisplayIdsForStartingVisibleBackgroundUsers";
                case 256:
                    return "shouldServiceTimeOut";
                case 257:
                    return "hasServiceTimeLimitExceeded";
                case 258:
                    return "registerUidFrozenStateChangedCallback";
                case 259:
                    return "unregisterUidFrozenStateChangedCallback";
                case 260:
                    return "getUidFrozenState";
                case 261:
                    return "getContentByTask";
                case 262:
                    return "getLongLiveApps";
                case 263:
                    return "addLongLiveApp";
                case 264:
                    return "removeLongLiveApp";
                case 265:
                    return "getMaxLongLiveApps";
                case 266:
                    return "setLongLiveTask";
                case 267:
                    return "clearLongLiveTask";
                case 268:
                    return "getLongLiveProcesses";
                case 269:
                    return "getLongLiveProcessesForUser";
                case 270:
                    return "getLongLiveTaskIdsForUser";
                case 271:
                    return "getAutoRemoveRecents";
                case 272:
                    return "registerDedicatedCallback";
                case 273:
                    return "setLongLiveApp";
                case 274:
                    return "getLongLiveApp";
                case 275:
                    return "moveTaskToBack";
                case 276:
                    return "moveTaskToBackWithBundle";
                case 277:
                    return "dismissUserSwitchingDialog";
                case 278:
                    return "getGlobalConfiguration";
                case 279:
                    return "checkPermissionForDevice";
                case 280:
                    return "frozenBinderTransactionDetected";
                case 281:
                    return "getBindingUidProcessState";
                case 282:
                    return "getUidLastIdleElapsedTime";
                case 283:
                    return "addOverridePermissionState";
                case 284:
                    return "removeOverridePermissionState";
                case 285:
                    return "clearOverridePermissionStates";
                case 286:
                    return "clearAllOverridePermissionStates";
                case 287:
                    return "noteAppRestrictionEnabled";
                case 288:
                    return "checkProfileForADCP";
                case 289:
                    return "preloadBoosterAppsFromIpm";
                case 290:
                    return "updateFlingerFlag";
                case 291:
                    return "getRestrictionInfo";
                case 292:
                    return "canRestrict";
                case 293:
                    return "restrict";
                case 294:
                    return "getRestrictableList";
                case 295:
                    return "getAllRestrictedList";
                case 296:
                    return "getRestrictedList";
                case 297:
                    return "updateRestrictionInfo";
                case 298:
                    return "clearRestrictionInfo";
                case 299:
                    return "setTTSPkgInfo";
                case 300:
                    return "clearTTSPkgInfo";
                case 301:
                    return "getInstalledPackageListFromMARs";
                case 302:
                    return "getPackageFromAppProcesses";
                case 303:
                    return "updatePersistentConfigurationAndLocaleOverlays";
                case 304:
                    return "reportAbnormalUsage";
                case 305:
                    return "isHeapDumpAllowed";
                case 306:
                    return "setFGSFilter";
                case 307:
                    return "resetAbnormalList";
                case 308:
                    return "isFreezableUid";
                case 309:
                    return "setProcessSlowdown";
                case 310:
                    return "getIsolatedProcessList";
                case 311:
                    return "getOptionsForIntentSender";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result = openContentUri(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    IUidObserver _arg02 = IUidObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    registerUidObserver(_arg02, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 3:
                    IUidObserver _arg03 = IUidObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterUidObserver(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    return onTransact$registerUidObserverForUids$(data, reply);
                case 5:
                    IBinder _arg04 = data.readStrongBinder();
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    addUidToObserver(_arg04, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg05 = data.readStrongBinder();
                    String _arg13 = data.readString();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    removeUidFromObserver(_arg05, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg06 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = isUidActive(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 8:
                    int _arg07 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    int _result3 = getUidProcessState(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    int _arg16 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = checkPermission(_arg08, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 10:
                    int _arg09 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    logFgsApiBegin(_arg09, _arg17, _arg25);
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    logFgsApiEnd(_arg010, _arg18, _arg26);
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    int _arg19 = data.readInt();
                    int _arg27 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    logFgsApiStateChanged(_arg011, _arg19, _arg27, _arg32);
                    return true;
                case 13:
                    IBinder _arg012 = data.readStrongBinder();
                    ApplicationErrorReport.ParcelableCrashInfo _arg110 =
                            (ApplicationErrorReport.ParcelableCrashInfo)
                                    data.readTypedObject(
                                            ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
                    data.enforceNoDataAvail();
                    handleApplicationCrash(_arg012, _arg110);
                    reply.writeNoException();
                    return true;
                case 14:
                    return onTransact$startActivity$(data, reply);
                case 15:
                    return onTransact$startActivityWithFeature$(data, reply);
                case 16:
                    unhandledBack();
                    reply.writeNoException();
                    return true;
                case 17:
                    IBinder _arg013 = data.readStrongBinder();
                    int _arg111 = data.readInt();
                    Intent _arg28 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = finishActivity(_arg013, _arg111, _arg28, _arg33);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 18:
                    return onTransact$registerReceiver$(data, reply);
                case 19:
                    return onTransact$registerReceiverWithFeature$(data, reply);
                case 20:
                    IIntentReceiver _arg014 =
                            IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterReceiver(_arg014);
                    reply.writeNoException();
                    return true;
                case 21:
                    return onTransact$broadcastIntent$(data, reply);
                case 22:
                    return onTransact$broadcastIntentWithFeature$(data, reply);
                case 23:
                    IApplicationThread _arg015 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg112 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    unbroadcastIntent(_arg015, _arg112, _arg29);
                    reply.writeNoException();
                    return true;
                case 24:
                    return onTransact$finishReceiver$(data, reply);
                case 25:
                    IApplicationThread _arg016 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    long _arg113 = data.readLong();
                    data.enforceNoDataAvail();
                    attachApplication(_arg016, _arg113);
                    reply.writeNoException();
                    return true;
                case 26:
                    long _arg017 = data.readLong();
                    long _arg114 = data.readLong();
                    data.enforceNoDataAvail();
                    finishAttachApplication(_arg017, _arg114);
                    reply.writeNoException();
                    return true;
                case 27:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> _result6 = getTasks(_arg018);
                    reply.writeNoException();
                    reply.writeTypedList(_result6, 1);
                    return true;
                case 28:
                    return onTransact$moveTaskToFront$(data, reply);
                case 29:
                    IBinder _arg019 = data.readStrongBinder();
                    boolean _arg115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result7 = getTaskForActivity(_arg019, _arg115);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 30:
                    return onTransact$getContentProvider$(data, reply);
                case 31:
                    IBinder _arg020 = data.readStrongBinder();
                    IApplicationThread _arg021 = IApplicationThread.Stub.asInterface(_arg020);
                    List<ContentProviderHolder> _arg116 =
                            data.createTypedArrayList(ContentProviderHolder.CREATOR);
                    data.enforceNoDataAvail();
                    publishContentProviders(_arg021, _arg116);
                    reply.writeNoException();
                    return true;
                case 32:
                    IBinder _arg022 = data.readStrongBinder();
                    int _arg117 = data.readInt();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = refContentProvider(_arg022, _arg117, _arg210);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 33:
                    ComponentName _arg023 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    PendingIntent _result9 = getRunningServiceControlPanel(_arg023);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 34:
                    return onTransact$startService$(data, reply);
                case 35:
                    IApplicationThread _arg024 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Intent _arg118 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg211 = data.readString();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result10 = stopService(_arg024, _arg118, _arg211, _arg34);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 36:
                    return onTransact$bindService$(data, reply);
                case 37:
                    return onTransact$bindServiceInstance$(data, reply);
                case 38:
                    IServiceConnection _arg025 =
                            IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg119 = data.readInt();
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    updateServiceGroup(_arg025, _arg119, _arg212);
                    reply.writeNoException();
                    return true;
                case 39:
                    IBinder _arg026 = data.readStrongBinder();
                    IServiceConnection _arg027 = IServiceConnection.Stub.asInterface(_arg026);
                    data.enforceNoDataAvail();
                    boolean _result11 = unbindService(_arg027);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 40:
                    IBinder _arg028 = data.readStrongBinder();
                    Intent _arg120 = (Intent) data.readTypedObject(Intent.CREATOR);
                    IBinder _arg213 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    publishService(_arg028, _arg120, _arg213);
                    reply.writeNoException();
                    return true;
                case 41:
                    String _arg029 = data.readString();
                    boolean _arg121 = data.readBoolean();
                    boolean _arg214 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDebugApp(_arg029, _arg121, _arg214);
                    reply.writeNoException();
                    return true;
                case 42:
                    String _arg030 = data.readString();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    setAgentApp(_arg030, _arg122);
                    reply.writeNoException();
                    return true;
                case 43:
                    boolean _arg031 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAlwaysFinish(_arg031);
                    reply.writeNoException();
                    return true;
                case 44:
                    return onTransact$startInstrumentation$(data, reply);
                case 45:
                    IApplicationThread _arg032 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    Bundle _arg123 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    addInstrumentationResults(_arg032, _arg123);
                    reply.writeNoException();
                    return true;
                case 46:
                    IApplicationThread _arg033 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    int _arg124 = data.readInt();
                    Bundle _arg215 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    finishInstrumentation(_arg033, _arg124, _arg215);
                    reply.writeNoException();
                    return true;
                case 47:
                    Configuration _result12 = getConfiguration();
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 48:
                    Configuration _arg034 =
                            (Configuration) data.readTypedObject(Configuration.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result13 = updateConfiguration(_arg034);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 49:
                    String _arg035 = data.readString();
                    String _arg125 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result14 = updateMccMncConfiguration(_arg035, _arg125);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 50:
                    ComponentName _arg036 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    IBinder _arg126 = data.readStrongBinder();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = stopServiceToken(_arg036, _arg126, _arg216);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 51:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    setProcessLimit(_arg037);
                    reply.writeNoException();
                    return true;
                case 52:
                    int _result16 = getProcessLimit();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 53:
                    return onTransact$checkUriPermission$(data, reply);
                case 54:
                    return onTransact$checkContentUriPermissionFull$(data, reply);
                case 55:
                    return onTransact$checkUriPermissions$(data, reply);
                case 56:
                    return onTransact$grantUriPermission$(data, reply);
                case 57:
                    return onTransact$revokeUriPermission$(data, reply);
                case 58:
                    IActivityController _arg038 =
                            IActivityController.Stub.asInterface(data.readStrongBinder());
                    boolean _arg127 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setActivityController(_arg038, _arg127);
                    reply.writeNoException();
                    return true;
                case 59:
                    IApplicationThread _arg039 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    boolean _arg128 = data.readBoolean();
                    data.enforceNoDataAvail();
                    showWaitingForDebugger(_arg039, _arg128);
                    reply.writeNoException();
                    return true;
                case 60:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    signalPersistentProcesses(_arg040);
                    reply.writeNoException();
                    return true;
                case 61:
                    int _arg041 = data.readInt();
                    int _arg129 = data.readInt();
                    int _arg217 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result17 = getRecentTasks(_arg041, _arg129, _arg217);
                    reply.writeNoException();
                    reply.writeTypedObject(_result17, 1);
                    return true;
                case 62:
                    return onTransact$serviceDoneExecuting$(data, reply);
                case 63:
                    return onTransact$getIntentSender$(data, reply);
                case 64:
                    return onTransact$getIntentSenderWithFeature$(data, reply);
                case 65:
                    IIntentSender _arg042 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    cancelIntentSender(_arg042);
                    reply.writeNoException();
                    return true;
                case 66:
                    IIntentSender _arg043 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    ActivityManager.PendingIntentInfo _result18 = getInfoForIntentSender(_arg043);
                    reply.writeNoException();
                    reply.writeTypedObject(_result18, 1);
                    return true;
                case 67:
                    IIntentSender _arg044 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    IResultReceiver _arg130 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result19 = registerIntentSenderCancelListenerEx(_arg044, _arg130);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 68:
                    IIntentSender _arg045 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    IResultReceiver _arg131 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterIntentSenderCancelListener(_arg045, _arg131);
                    reply.writeNoException();
                    return true;
                case 69:
                    enterSafeMode();
                    reply.writeNoException();
                    return true;
                case 70:
                    return onTransact$noteWakeupAlarm$(data, reply);
                case 71:
                    IBinder _arg046 = data.readStrongBinder();
                    boolean _arg132 = data.readBoolean();
                    data.enforceNoDataAvail();
                    removeContentProvider(_arg046, _arg132);
                    return true;
                case 72:
                    IBinder _arg047 = data.readStrongBinder();
                    int _arg133 = data.readInt();
                    data.enforceNoDataAvail();
                    setRequestedOrientation(_arg047, _arg133);
                    reply.writeNoException();
                    return true;
                case 73:
                    IBinder _arg048 = data.readStrongBinder();
                    Intent _arg134 = (Intent) data.readTypedObject(Intent.CREATOR);
                    boolean _arg218 = data.readBoolean();
                    data.enforceNoDataAvail();
                    unbindFinished(_arg048, _arg134, _arg218);
                    reply.writeNoException();
                    return true;
                case 74:
                    IBinder _arg049 = data.readStrongBinder();
                    int _arg135 = data.readInt();
                    boolean _arg219 = data.readBoolean();
                    String _arg35 = data.readString();
                    data.enforceNoDataAvail();
                    setProcessImportant(_arg049, _arg135, _arg219, _arg35);
                    reply.writeNoException();
                    return true;
                case 75:
                    return onTransact$setServiceForeground$(data, reply);
                case 76:
                    ComponentName _arg050 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    IBinder _arg136 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result20 = getForegroundServiceType(_arg050, _arg136);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 77:
                    IBinder _arg051 = data.readStrongBinder();
                    boolean _arg137 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result21 = moveActivityTaskToBack(_arg051, _arg137);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 78:
                    ActivityManager.MemoryInfo _arg052 = new ActivityManager.MemoryInfo();
                    data.enforceNoDataAvail();
                    getMemoryInfo(_arg052);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg052, 1);
                    return true;
                case 79:
                    List<ActivityManager.ProcessErrorStateInfo> _result22 =
                            getProcessesInErrorState();
                    reply.writeNoException();
                    reply.writeTypedList(_result22, 1);
                    return true;
                case 80:
                    String _arg053 = data.readString();
                    boolean _arg138 = data.readBoolean();
                    IPackageDataObserver _arg220 =
                            IPackageDataObserver.Stub.asInterface(data.readStrongBinder());
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result23 = clearApplicationUserData(_arg053, _arg138, _arg220, _arg36);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 81:
                    String _arg054 = data.readString();
                    int _arg139 = data.readInt();
                    data.enforceNoDataAvail();
                    stopAppForUser(_arg054, _arg139);
                    reply.writeNoException();
                    return true;
                case 82:
                    IForegroundServiceObserver _arg055 =
                            IForegroundServiceObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result24 = registerForegroundServiceObserver(_arg055);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 83:
                    String _arg056 = data.readString();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    forceStopPackage(_arg056, _arg140);
                    reply.writeNoException();
                    return true;
                case 84:
                    String _arg057 = data.readString();
                    int _arg141 = data.readInt();
                    data.enforceNoDataAvail();
                    forceStopPackageEvenWhenStopping(_arg057, _arg141);
                    reply.writeNoException();
                    return true;
                case 85:
                    String _arg058 = data.readString();
                    int _arg142 = data.readInt();
                    data.enforceNoDataAvail();
                    forceStopPackageByAdmin(_arg058, _arg142);
                    reply.writeNoException();
                    return true;
                case 86:
                    int[] _arg059 = data.createIntArray();
                    String _arg143 = data.readString();
                    boolean _arg221 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result25 = killPids(_arg059, _arg143, _arg221);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 87:
                    int _arg060 = data.readInt();
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    List<ActivityManager.RunningServiceInfo> _result26 =
                            getServices(_arg060, _arg144);
                    reply.writeNoException();
                    reply.writeTypedList(_result26, 1);
                    return true;
                case 88:
                    List<ActivityManager.RunningAppProcessInfo> _result27 =
                            getRunningAppProcesses();
                    reply.writeNoException();
                    reply.writeTypedList(_result27, 1);
                    return true;
                case 89:
                    Intent _arg061 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg145 = data.readString();
                    String _arg222 = data.readString();
                    data.enforceNoDataAvail();
                    IBinder _result28 = peekService(_arg061, _arg145, _arg222);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result28);
                    return true;
                case 90:
                    return onTransact$profileControl$(data, reply);
                case 91:
                    int _arg062 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = shutdown(_arg062);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 92:
                    stopAppSwitches();
                    reply.writeNoException();
                    return true;
                case 93:
                    resumeAppSwitches();
                    reply.writeNoException();
                    return true;
                case 94:
                    String _arg063 = data.readString();
                    int _arg146 = data.readInt();
                    int _arg223 = data.readInt();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result30 = bindBackupAgent(_arg063, _arg146, _arg223, _arg37);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 95:
                    String _arg064 = data.readString();
                    IBinder _arg147 = data.readStrongBinder();
                    int _arg224 = data.readInt();
                    data.enforceNoDataAvail();
                    backupAgentCreated(_arg064, _arg147, _arg224);
                    reply.writeNoException();
                    return true;
                case 96:
                    ApplicationInfo _arg065 =
                            (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    unbindBackupAgent(_arg065);
                    reply.writeNoException();
                    return true;
                case 97:
                    return onTransact$handleIncomingUser$(data, reply);
                case 98:
                    String _arg066 = data.readString();
                    data.enforceNoDataAvail();
                    addPackageDependency(_arg066);
                    reply.writeNoException();
                    return true;
                case 99:
                    return onTransact$killApplication$(data, reply);
                case 100:
                    String _arg067 = data.readString();
                    data.enforceNoDataAvail();
                    closeSystemDialogs(_arg067);
                    reply.writeNoException();
                    return true;
                case 101:
                    String _arg068 = data.readString();
                    int _arg148 = data.readInt();
                    data.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(_arg068, _arg148);
                    reply.writeNoException();
                    return true;
                case 102:
                    int[] _arg069 = data.createIntArray();
                    data.enforceNoDataAvail();
                    Debug.MemoryInfo[] _result31 = getProcessMemoryInfo(_arg069);
                    reply.writeNoException();
                    reply.writeTypedArray(_result31, 1);
                    return true;
                case 103:
                    String _arg070 = data.readString();
                    int _arg149 = data.readInt();
                    data.enforceNoDataAvail();
                    killApplicationProcess(_arg070, _arg149);
                    reply.writeNoException();
                    return true;
                case 104:
                    return onTransact$handleApplicationWtf$(data, reply);
                case 105:
                    String _arg071 = data.readString();
                    int _arg150 = data.readInt();
                    data.enforceNoDataAvail();
                    killBackgroundProcesses(_arg071, _arg150);
                    reply.writeNoException();
                    return true;
                case 106:
                    boolean _result32 = isUserAMonkey();
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 107:
                    List<ApplicationInfo> _result33 = getRunningExternalApplications();
                    reply.writeNoException();
                    reply.writeTypedList(_result33, 1);
                    return true;
                case 108:
                    finishHeavyWeightApp();
                    reply.writeNoException();
                    return true;
                case 109:
                    IBinder _arg072 = data.readStrongBinder();
                    int _arg151 = data.readInt();
                    StrictMode.ViolationInfo _arg225 =
                            (StrictMode.ViolationInfo)
                                    data.readTypedObject(StrictMode.ViolationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    handleApplicationStrictModeViolation(_arg072, _arg151, _arg225);
                    reply.writeNoException();
                    return true;
                case 110:
                    IBinder _arg073 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    registerStrictModeCallback(_arg073);
                    reply.writeNoException();
                    return true;
                case 111:
                    boolean _result34 = isTopActivityImmersive();
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 112:
                    return onTransact$crashApplicationWithType$(data, reply);
                case 113:
                    return onTransact$crashApplicationWithTypeWithExtras$(data, reply);
                case 114:
                    Uri _arg074 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg152 = data.readInt();
                    RemoteCallback _arg226 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    getMimeTypeFilterAsync(_arg074, _arg152, _arg226);
                    return true;
                case 115:
                    return onTransact$dumpHeap$(data, reply);
                case 116:
                    int _arg075 = data.readInt();
                    int _arg153 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result35 = isUserRunning(_arg075, _arg153);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 117:
                    String _arg076 = data.readString();
                    int _arg154 = data.readInt();
                    data.enforceNoDataAvail();
                    setPackageScreenCompatMode(_arg076, _arg154);
                    reply.writeNoException();
                    return true;
                case 118:
                    int _arg077 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result36 = switchUser(_arg077);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 119:
                    String _result37 = getSwitchingFromUserMessage();
                    reply.writeNoException();
                    reply.writeString(_result37);
                    return true;
                case 120:
                    String _result38 = getSwitchingToUserMessage();
                    reply.writeNoException();
                    reply.writeString(_result38);
                    return true;
                case 121:
                    int _arg078 = data.readInt();
                    data.enforceNoDataAvail();
                    setStopUserOnSwitch(_arg078);
                    reply.writeNoException();
                    return true;
                case 122:
                    int _arg079 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result39 = removeTask(_arg079);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 123:
                    IProcessObserver _arg080 =
                            IProcessObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerProcessObserver(_arg080);
                    reply.writeNoException();
                    return true;
                case 124:
                    IProcessObserver _arg081 =
                            IProcessObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterProcessObserver(_arg081);
                    reply.writeNoException();
                    return true;
                case 125:
                    IIntentSender _arg082 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result40 = isIntentSenderTargetedToPackage(_arg082);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 126:
                    Configuration _arg083 =
                            (Configuration) data.readTypedObject(Configuration.CREATOR);
                    data.enforceNoDataAvail();
                    updatePersistentConfiguration(_arg083);
                    reply.writeNoException();
                    return true;
                case 127:
                    Configuration _arg084 =
                            (Configuration) data.readTypedObject(Configuration.CREATOR);
                    String _arg155 = data.readString();
                    String _arg227 = data.readString();
                    data.enforceNoDataAvail();
                    updatePersistentConfigurationWithAttribution(_arg084, _arg155, _arg227);
                    reply.writeNoException();
                    return true;
                case 128:
                    int[] _arg085 = data.createIntArray();
                    data.enforceNoDataAvail();
                    long[] _result41 = getProcessPss(_arg085);
                    reply.writeNoException();
                    reply.writeLongArray(_result41);
                    return true;
                case 129:
                    CharSequence _arg086 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    boolean _arg156 = data.readBoolean();
                    data.enforceNoDataAvail();
                    showBootMessage(_arg086, _arg156);
                    reply.writeNoException();
                    return true;
                case 130:
                    killAllBackgroundProcesses();
                    reply.writeNoException();
                    return true;
                case 131:
                    String _arg087 = data.readString();
                    int _arg157 = data.readInt();
                    IBinder _arg228 = data.readStrongBinder();
                    String _arg38 = data.readString();
                    data.enforceNoDataAvail();
                    ContentProviderHolder _result42 =
                            getContentProviderExternal(_arg087, _arg157, _arg228, _arg38);
                    reply.writeNoException();
                    reply.writeTypedObject(_result42, 1);
                    return true;
                case 132:
                    String _arg088 = data.readString();
                    IBinder _arg158 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    removeContentProviderExternal(_arg088, _arg158);
                    reply.writeNoException();
                    return true;
                case 133:
                    String _arg089 = data.readString();
                    IBinder _arg159 = data.readStrongBinder();
                    int _arg229 = data.readInt();
                    data.enforceNoDataAvail();
                    removeContentProviderExternalAsUser(_arg089, _arg159, _arg229);
                    reply.writeNoException();
                    return true;
                case 134:
                    ActivityManager.RunningAppProcessInfo _arg090 =
                            new ActivityManager.RunningAppProcessInfo();
                    data.enforceNoDataAvail();
                    getMyMemoryState(_arg090);
                    reply.writeNoException();
                    reply.writeTypedObject(_arg090, 1);
                    return true;
                case 135:
                    String _arg091 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result43 = killProcessesBelowForeground(_arg091);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 136:
                    UserInfo _result44 = getCurrentUser();
                    reply.writeNoException();
                    reply.writeTypedObject(_result44, 1);
                    return true;
                case 137:
                    int _result45 = getCurrentUserId();
                    reply.writeNoException();
                    reply.writeInt(_result45);
                    return true;
                case 138:
                    IBinder _arg092 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result46 = getLaunchedFromUid(_arg092);
                    reply.writeNoException();
                    reply.writeInt(_result46);
                    return true;
                case 139:
                    IBinder _arg093 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unstableProviderDied(_arg093);
                    reply.writeNoException();
                    return true;
                case 140:
                    IIntentSender _arg094 = IIntentSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result47 = isIntentSenderAnActivity(_arg094);
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 141:
                    return onTransact$startActivityAsUser$(data, reply);
                case 142:
                    return onTransact$startActivityAsUserWithFeature$(data, reply);
                case 143:
                    int _arg095 = data.readInt();
                    boolean _arg160 = data.readBoolean();
                    IStopUserCallback _arg230 =
                            IStopUserCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result48 = stopUser(_arg095, _arg160, _arg230);
                    reply.writeNoException();
                    reply.writeInt(_result48);
                    return true;
                case 144:
                    int _arg096 = data.readInt();
                    IStopUserCallback _arg161 =
                            IStopUserCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result49 = stopUserWithCallback(_arg096, _arg161);
                    reply.writeNoException();
                    reply.writeInt(_result49);
                    return true;
                case 145:
                    int _arg097 = data.readInt();
                    boolean _arg162 = data.readBoolean();
                    IStopUserCallback _arg231 =
                            IStopUserCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result50 = stopUserExceptCertainProfiles(_arg097, _arg162, _arg231);
                    reply.writeNoException();
                    reply.writeInt(_result50);
                    return true;
                case 146:
                    int _arg098 = data.readInt();
                    IStopUserCallback _arg163 =
                            IStopUserCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result51 = stopUserWithDelayedLocking(_arg098, _arg163);
                    reply.writeNoException();
                    reply.writeInt(_result51);
                    return true;
                case 147:
                    IUserSwitchObserver _arg099 =
                            IUserSwitchObserver.Stub.asInterface(data.readStrongBinder());
                    String _arg164 = data.readString();
                    data.enforceNoDataAvail();
                    registerUserSwitchObserver(_arg099, _arg164);
                    reply.writeNoException();
                    return true;
                case 148:
                    IUserSwitchObserver _arg0100 =
                            IUserSwitchObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterUserSwitchObserver(_arg0100);
                    reply.writeNoException();
                    return true;
                case 149:
                    int[] _result52 = getRunningUserIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result52);
                    return true;
                case 150:
                    requestSystemServerHeapDump();
                    reply.writeNoException();
                    return true;
                case 151:
                    int _arg0101 = data.readInt();
                    data.enforceNoDataAvail();
                    requestBugReport(_arg0101);
                    reply.writeNoException();
                    return true;
                case 152:
                    String _arg0102 = data.readString();
                    String _arg165 = data.readString();
                    int _arg232 = data.readInt();
                    data.enforceNoDataAvail();
                    requestBugReportWithDescription(_arg0102, _arg165, _arg232);
                    reply.writeNoException();
                    return true;
                case 153:
                    String _arg0103 = data.readString();
                    String _arg166 = data.readString();
                    data.enforceNoDataAvail();
                    requestTelephonyBugReport(_arg0103, _arg166);
                    reply.writeNoException();
                    return true;
                case 154:
                    String _arg0104 = data.readString();
                    String _arg167 = data.readString();
                    data.enforceNoDataAvail();
                    requestWifiBugReport(_arg0104, _arg167);
                    reply.writeNoException();
                    return true;
                case 155:
                    String _arg0105 = data.readString();
                    String _arg168 = data.readString();
                    data.enforceNoDataAvail();
                    requestInteractiveBugReportWithDescription(_arg0105, _arg168);
                    reply.writeNoException();
                    return true;
                case 156:
                    requestInteractiveBugReport();
                    reply.writeNoException();
                    return true;
                case 157:
                    Uri _arg0106 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    requestBugReportWithExtraAttachment(_arg0106);
                    reply.writeNoException();
                    return true;
                case 158:
                    requestFullBugReport();
                    reply.writeNoException();
                    return true;
                case 159:
                    long _arg0107 = data.readLong();
                    data.enforceNoDataAvail();
                    requestRemoteBugReport(_arg0107);
                    reply.writeNoException();
                    return true;
                case 160:
                    boolean _result53 = launchBugReportHandlerApp();
                    reply.writeNoException();
                    reply.writeBoolean(_result53);
                    return true;
                case 161:
                    List<String> _result54 = getBugreportWhitelistedPackages();
                    reply.writeNoException();
                    reply.writeStringList(_result54);
                    return true;
                case 162:
                    IBinder _arg0108 = data.readStrongBinder();
                    IIntentSender _arg0109 = IIntentSender.Stub.asInterface(_arg0108);
                    data.enforceNoDataAvail();
                    Intent _result55 = getIntentForIntentSender(_arg0109);
                    reply.writeNoException();
                    reply.writeTypedObject(_result55, 1);
                    return true;
                case 163:
                    IBinder _arg0110 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    String _result56 = getLaunchedFromPackage(_arg0110);
                    reply.writeNoException();
                    reply.writeString(_result56);
                    return true;
                case 164:
                    int _arg0111 = data.readInt();
                    int _arg169 = data.readInt();
                    String _arg233 = data.readString();
                    data.enforceNoDataAvail();
                    killUid(_arg0111, _arg169, _arg233);
                    reply.writeNoException();
                    return true;
                case 165:
                    boolean _arg0112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setUserIsMonkey(_arg0112);
                    reply.writeNoException();
                    return true;
                case 166:
                    IBinder _arg0113 = data.readStrongBinder();
                    boolean _arg170 = data.readBoolean();
                    data.enforceNoDataAvail();
                    hang(_arg0113, _arg170);
                    reply.writeNoException();
                    return true;
                case 167:
                    List<ActivityTaskManager.RootTaskInfo> _result57 = getAllRootTaskInfos();
                    reply.writeNoException();
                    reply.writeTypedList(_result57, 1);
                    return true;
                case 168:
                    int _arg0114 = data.readInt();
                    int _arg171 = data.readInt();
                    boolean _arg234 = data.readBoolean();
                    data.enforceNoDataAvail();
                    moveTaskToRootTask(_arg0114, _arg171, _arg234);
                    reply.writeNoException();
                    return true;
                case 169:
                    int _arg0115 = data.readInt();
                    data.enforceNoDataAvail();
                    setFocusedRootTask(_arg0115);
                    reply.writeNoException();
                    return true;
                case 170:
                    ActivityTaskManager.RootTaskInfo _result58 = getFocusedRootTaskInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result58, 1);
                    return true;
                case 171:
                    restart();
                    reply.writeNoException();
                    return true;
                case 172:
                    performIdleMaintenance();
                    reply.writeNoException();
                    return true;
                case 173:
                    IBinder _arg0116 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    appNotRespondingViaProvider(_arg0116);
                    reply.writeNoException();
                    return true;
                case 174:
                    int _arg0117 = data.readInt();
                    data.enforceNoDataAvail();
                    Rect _result59 = getTaskBounds(_arg0117);
                    reply.writeNoException();
                    reply.writeTypedObject(_result59, 1);
                    return true;
                case 175:
                    String _arg0118 = data.readString();
                    int _arg172 = data.readInt();
                    int _arg235 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result60 = setProcessMemoryTrimLevel(_arg0118, _arg172, _arg235);
                    reply.writeNoException();
                    reply.writeBoolean(_result60);
                    return true;
                case 176:
                    IIntentSender _arg0119 =
                            IIntentSender.Stub.asInterface(data.readStrongBinder());
                    String _arg173 = data.readString();
                    data.enforceNoDataAvail();
                    String _result61 = getTagForIntentSender(_arg0119, _arg173);
                    reply.writeNoException();
                    reply.writeString(_result61);
                    return true;
                case 177:
                    int _arg0120 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result62 = startUserInBackground(_arg0120);
                    reply.writeNoException();
                    reply.writeBoolean(_result62);
                    return true;
                case 178:
                    boolean _result63 = isInLockTaskMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result63);
                    return true;
                case 179:
                    int _arg0121 = data.readInt();
                    Bundle _arg174 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    int _result64 = startActivityFromRecents(_arg0121, _arg174);
                    reply.writeNoException();
                    reply.writeInt(_result64);
                    return true;
                case 180:
                    int _arg0122 = data.readInt();
                    data.enforceNoDataAvail();
                    startSystemLockTaskMode(_arg0122);
                    reply.writeNoException();
                    return true;
                case 181:
                    IBinder _arg0123 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result65 = isTopOfTask(_arg0123);
                    reply.writeNoException();
                    reply.writeBoolean(_result65);
                    return true;
                case 182:
                    bootAnimationComplete();
                    reply.writeNoException();
                    return true;
                case 183:
                    int _arg0124 = data.readInt();
                    data.enforceNoDataAvail();
                    setThemeOverlayReady(_arg0124);
                    reply.writeNoException();
                    return true;
                case 184:
                    ITaskStackListener _arg0125 =
                            ITaskStackListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerTaskStackListener(_arg0125);
                    reply.writeNoException();
                    return true;
                case 185:
                    ITaskStackListener _arg0126 =
                            ITaskStackListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterTaskStackListener(_arg0126);
                    reply.writeNoException();
                    return true;
                case 186:
                    int _arg0127 = data.readInt();
                    byte[] _arg175 = data.createByteArray();
                    data.enforceNoDataAvail();
                    notifyCleartextNetwork(_arg0127, _arg175);
                    reply.writeNoException();
                    return true;
                case 187:
                    int _arg0128 = data.readInt();
                    int _arg176 = data.readInt();
                    data.enforceNoDataAvail();
                    setTaskResizeable(_arg0128, _arg176);
                    reply.writeNoException();
                    return true;
                case 188:
                    int _arg0129 = data.readInt();
                    Rect _arg177 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg236 = data.readInt();
                    data.enforceNoDataAvail();
                    resizeTask(_arg0129, _arg177, _arg236);
                    reply.writeNoException();
                    return true;
                case 189:
                    int _result66 = getLockTaskModeState();
                    reply.writeNoException();
                    reply.writeInt(_result66);
                    return true;
                case 190:
                    String _arg0130 = data.readString();
                    int _arg178 = data.readInt();
                    long _arg237 = data.readLong();
                    String _arg39 = data.readString();
                    data.enforceNoDataAvail();
                    setDumpHeapDebugLimit(_arg0130, _arg178, _arg237, _arg39);
                    reply.writeNoException();
                    return true;
                case 191:
                    String _arg0131 = data.readString();
                    data.enforceNoDataAvail();
                    dumpHeapFinished(_arg0131);
                    reply.writeNoException();
                    return true;
                case 192:
                    int _arg0132 = data.readInt();
                    String[] _arg179 = data.createStringArray();
                    data.enforceNoDataAvail();
                    updateLockTaskPackages(_arg0132, _arg179);
                    reply.writeNoException();
                    return true;
                case 193:
                    IIntentSender _arg0133 =
                            IIntentSender.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg180 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg238 = data.readInt();
                    String _arg310 = data.readString();
                    data.enforceNoDataAvail();
                    noteAlarmStart(_arg0133, _arg180, _arg238, _arg310);
                    reply.writeNoException();
                    return true;
                case 194:
                    IIntentSender _arg0134 =
                            IIntentSender.Stub.asInterface(data.readStrongBinder());
                    WorkSource _arg181 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    int _arg239 = data.readInt();
                    String _arg311 = data.readString();
                    data.enforceNoDataAvail();
                    noteAlarmFinish(_arg0134, _arg181, _arg239, _arg311);
                    reply.writeNoException();
                    return true;
                case 195:
                    String _arg0135 = data.readString();
                    String _arg182 = data.readString();
                    data.enforceNoDataAvail();
                    int _result67 = getPackageProcessState(_arg0135, _arg182);
                    reply.writeNoException();
                    reply.writeInt(_result67);
                    return true;
                case 196:
                    boolean _result68 = startBinderTracking();
                    reply.writeNoException();
                    reply.writeBoolean(_result68);
                    return true;
                case 197:
                    ParcelFileDescriptor _arg0136 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result69 = stopBinderTrackingAndDump(_arg0136);
                    reply.writeNoException();
                    reply.writeBoolean(_result69);
                    return true;
                case 198:
                    boolean _arg0137 = data.readBoolean();
                    data.enforceNoDataAvail();
                    suppressResizeConfigChanges(_arg0137);
                    reply.writeNoException();
                    return true;
                case 199:
                    int _arg0138 = data.readInt();
                    byte[] _arg183 = data.createByteArray();
                    byte[] _arg240 = data.createByteArray();
                    IProgressListener _arg312 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result70 = unlockUser(_arg0138, _arg183, _arg240, _arg312);
                    reply.writeNoException();
                    reply.writeBoolean(_result70);
                    return true;
                case 200:
                    int _arg0139 = data.readInt();
                    IProgressListener _arg184 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result71 = unlockUser2(_arg0139, _arg184);
                    reply.writeNoException();
                    reply.writeBoolean(_result71);
                    return true;
                case 201:
                    String _arg0140 = data.readString();
                    int _arg185 = data.readInt();
                    data.enforceNoDataAvail();
                    killPackageDependents(_arg0140, _arg185);
                    reply.writeNoException();
                    return true;
                case 202:
                    String _arg0141 = data.readString();
                    int _arg186 = data.readInt();
                    data.enforceNoDataAvail();
                    makePackageIdle(_arg0141, _arg186);
                    reply.writeNoException();
                    return true;
                case 203:
                    boolean _arg0142 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDeterministicUidIdle(_arg0142);
                    reply.writeNoException();
                    return true;
                case 204:
                    int _result72 = getMemoryTrimLevel();
                    reply.writeNoException();
                    reply.writeInt(_result72);
                    return true;
                case 205:
                    ComponentName _arg0143 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result73 = isVrModePackageEnabled(_arg0143);
                    reply.writeNoException();
                    reply.writeBoolean(_result73);
                    return true;
                case 206:
                    int _arg0144 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyLockedProfile(_arg0144);
                    reply.writeNoException();
                    return true;
                case 207:
                    Intent _arg0145 = (Intent) data.readTypedObject(Intent.CREATOR);
                    Bundle _arg187 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    startConfirmDeviceCredentialIntent(_arg0145, _arg187);
                    reply.writeNoException();
                    return true;
                case 208:
                    sendIdleJobTrigger();
                    reply.writeNoException();
                    return true;
                case 209:
                    return onTransact$sendIntentSender$(data, reply);
                case 210:
                    String _arg0146 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result74 = isBackgroundRestricted(_arg0146);
                    reply.writeNoException();
                    reply.writeBoolean(_result74);
                    return true;
                case 211:
                    int _arg0147 = data.readInt();
                    data.enforceNoDataAvail();
                    setRenderThread(_arg0147);
                    reply.writeNoException();
                    return true;
                case 212:
                    boolean _arg0148 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHasTopUi(_arg0148);
                    reply.writeNoException();
                    return true;
                case 213:
                    int _arg0149 = data.readInt();
                    data.enforceNoDataAvail();
                    cancelTaskWindowTransition(_arg0149);
                    reply.writeNoException();
                    return true;
                case 214:
                    List<String> _arg0150 = data.createStringArrayList();
                    int _arg188 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(_arg0150, _arg188);
                    reply.writeNoException();
                    return true;
                case 215:
                    int _arg0151 = data.readInt();
                    data.enforceNoDataAvail();
                    setPersistentVrThread(_arg0151);
                    reply.writeNoException();
                    return true;
                case 216:
                    long _arg0152 = data.readLong();
                    data.enforceNoDataAvail();
                    waitForNetworkStateUpdate(_arg0152);
                    reply.writeNoException();
                    return true;
                case 217:
                    int _arg0153 = data.readInt();
                    data.enforceNoDataAvail();
                    backgroundAllowlistUid(_arg0153);
                    reply.writeNoException();
                    return true;
                case 218:
                    int _arg0154 = data.readInt();
                    IProgressListener _arg189 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result75 = startUserInBackgroundWithListener(_arg0154, _arg189);
                    reply.writeNoException();
                    reply.writeBoolean(_result75);
                    return true;
                case 219:
                    int _arg0155 = data.readInt();
                    String[] _arg190 = data.createStringArray();
                    data.enforceNoDataAvail();
                    startDelegateShellPermissionIdentity(_arg0155, _arg190);
                    reply.writeNoException();
                    return true;
                case 220:
                    stopDelegateShellPermissionIdentity();
                    reply.writeNoException();
                    return true;
                case 221:
                    List<String> _result76 = getDelegatedShellPermissions();
                    reply.writeNoException();
                    reply.writeStringList(_result76);
                    return true;
                case 222:
                    ParcelFileDescriptor _result77 = getLifeMonitor();
                    reply.writeNoException();
                    reply.writeTypedObject(_result77, 1);
                    return true;
                case 223:
                    int _arg0156 = data.readInt();
                    IProgressListener _arg191 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result78 = startUserInForegroundWithListener(_arg0156, _arg191);
                    reply.writeNoException();
                    reply.writeBoolean(_result78);
                    return true;
                case 224:
                    String _arg0157 = data.readString();
                    data.enforceNoDataAvail();
                    appNotResponding(_arg0157);
                    reply.writeNoException();
                    return true;
                case 225:
                    String _arg0158 = data.readString();
                    boolean _arg192 = data.readBoolean();
                    int _arg241 = data.readInt();
                    data.enforceNoDataAvail();
                    doActiveLaunch(_arg0158, _arg192, _arg241);
                    return true;
                case 226:
                    String _arg0159 = data.readString();
                    int _arg193 = data.readInt();
                    int _arg242 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice<ApplicationStartInfo> _result79 =
                            getHistoricalProcessStartReasons(_arg0159, _arg193, _arg242);
                    reply.writeNoException();
                    reply.writeTypedObject(_result79, 1);
                    return true;
                case 227:
                    IApplicationStartInfoCompleteListener _arg0160 =
                            IApplicationStartInfoCompleteListener.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg194 = data.readInt();
                    data.enforceNoDataAvail();
                    addApplicationStartInfoCompleteListener(_arg0160, _arg194);
                    reply.writeNoException();
                    return true;
                case 228:
                    IApplicationStartInfoCompleteListener _arg0161 =
                            IApplicationStartInfoCompleteListener.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg195 = data.readInt();
                    data.enforceNoDataAvail();
                    removeApplicationStartInfoCompleteListener(_arg0161, _arg195);
                    reply.writeNoException();
                    return true;
                case 229:
                    int _arg0162 = data.readInt();
                    long _arg196 = data.readLong();
                    int _arg243 = data.readInt();
                    data.enforceNoDataAvail();
                    addStartInfoTimestamp(_arg0162, _arg196, _arg243);
                    reply.writeNoException();
                    return true;
                case 230:
                    long _arg0163 = data.readLong();
                    long _arg197 = data.readLong();
                    data.enforceNoDataAvail();
                    reportStartInfoViewTimestamps(_arg0163, _arg197);
                    return true;
                case 231:
                    String _arg0164 = data.readString();
                    int _arg198 = data.readInt();
                    int _arg244 = data.readInt();
                    int _arg313 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice<ApplicationExitInfo> _result80 =
                            getHistoricalProcessExitReasons(_arg0164, _arg198, _arg244, _arg313);
                    reply.writeNoException();
                    reply.writeTypedObject(_result80, 1);
                    return true;
                case 232:
                    int[] _arg0165 = data.createIntArray();
                    String _arg199 = data.readString();
                    data.enforceNoDataAvail();
                    killProcessesWhenImperceptible(_arg0165, _arg199);
                    reply.writeNoException();
                    return true;
                case 233:
                    ComponentName _arg0166 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    LocusId _arg1100 = (LocusId) data.readTypedObject(LocusId.CREATOR);
                    IBinder _arg245 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setActivityLocusContext(_arg0166, _arg1100, _arg245);
                    reply.writeNoException();
                    return true;
                case 234:
                    byte[] _arg0167 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setProcessStateSummary(_arg0167);
                    reply.writeNoException();
                    return true;
                case 235:
                    boolean _result81 = isAppFreezerSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result81);
                    return true;
                case 236:
                    boolean _result82 = isAppFreezerEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result82);
                    return true;
                case 237:
                    int _arg0168 = data.readInt();
                    int _arg1101 = data.readInt();
                    String _arg246 = data.readString();
                    data.enforceNoDataAvail();
                    killUidForPermissionChange(_arg0168, _arg1101, _arg246);
                    reply.writeNoException();
                    return true;
                case 238:
                    resetAppErrors();
                    reply.writeNoException();
                    return true;
                case 239:
                    boolean _arg0169 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result83 = enableAppFreezer(_arg0169);
                    reply.writeNoException();
                    reply.writeBoolean(_result83);
                    return true;
                case 240:
                    boolean _arg0170 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result84 = enableFgsNotificationRateLimit(_arg0170);
                    reply.writeNoException();
                    reply.writeBoolean(_result84);
                    return true;
                case 241:
                    IBinder _arg0171 = data.readStrongBinder();
                    int _arg1102 = data.readInt();
                    data.enforceNoDataAvail();
                    holdLock(_arg0171, _arg1102);
                    reply.writeNoException();
                    return true;
                case 242:
                    int _arg0172 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result85 = startProfile(_arg0172);
                    reply.writeNoException();
                    reply.writeBoolean(_result85);
                    return true;
                case 243:
                    int _arg0173 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result86 = stopProfile(_arg0173);
                    reply.writeNoException();
                    reply.writeBoolean(_result86);
                    return true;
                case 244:
                    IIntentSender _arg0174 =
                            IIntentSender.Stub.asInterface(data.readStrongBinder());
                    int _arg1103 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result87 =
                            queryIntentComponentsForIntentSender(_arg0174, _arg1103);
                    reply.writeNoException();
                    reply.writeTypedObject(_result87, 1);
                    return true;
                case 245:
                    int _arg0175 = data.readInt();
                    String _arg1104 = data.readString();
                    data.enforceNoDataAvail();
                    int _result88 = getUidProcessCapabilities(_arg0175, _arg1104);
                    reply.writeNoException();
                    reply.writeInt(_result88);
                    return true;
                case 246:
                    waitForBroadcastIdle();
                    reply.writeNoException();
                    return true;
                case 247:
                    waitForBroadcastBarrier();
                    reply.writeNoException();
                    return true;
                case 248:
                    String _arg0176 = data.readString();
                    long _arg1105 = data.readLong();
                    data.enforceNoDataAvail();
                    forceDelayBroadcastDelivery(_arg0176, _arg1105);
                    reply.writeNoException();
                    return true;
                case 249:
                    int _arg0177 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result89 = isProcessFrozen(_arg0177);
                    reply.writeNoException();
                    reply.writeBoolean(_result89);
                    return true;
                case 250:
                    int _arg0178 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result90 = getBackgroundRestrictionExemptionReason(_arg0178);
                    reply.writeNoException();
                    reply.writeInt(_result90);
                    return true;
                case 251:
                    Intent _arg0179 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg1106 = data.readString();
                    int _arg247 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result91 =
                            queryRegisteredReceiverPackages(_arg0179, _arg1106, _arg247);
                    reply.writeNoException();
                    reply.writeStringArray(_result91);
                    return true;
                case 252:
                    int _arg0180 = data.readInt();
                    int _arg1107 = data.readInt();
                    IProgressListener _arg248 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result92 =
                            startUserInBackgroundVisibleOnDisplay(_arg0180, _arg1107, _arg248);
                    reply.writeNoException();
                    reply.writeBoolean(_result92);
                    return true;
                case 253:
                    int _arg0181 = data.readInt();
                    IProgressListener _arg1108 =
                            IProgressListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result93 = startProfileWithListener(_arg0181, _arg1108);
                    reply.writeNoException();
                    reply.writeBoolean(_result93);
                    return true;
                case 254:
                    int _arg0182 = data.readInt();
                    int _arg1109 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result94 = restartUserInBackground(_arg0182, _arg1109);
                    reply.writeNoException();
                    reply.writeInt(_result94);
                    return true;
                case 255:
                    int[] _result95 = getDisplayIdsForStartingVisibleBackgroundUsers();
                    reply.writeNoException();
                    reply.writeIntArray(_result95);
                    return true;
                case 256:
                    ComponentName _arg0183 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    IBinder _arg1110 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result96 = shouldServiceTimeOut(_arg0183, _arg1110);
                    reply.writeNoException();
                    reply.writeBoolean(_result96);
                    return true;
                case 257:
                    ComponentName _arg0184 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    IBinder _arg1111 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result97 = hasServiceTimeLimitExceeded(_arg0184, _arg1111);
                    reply.writeNoException();
                    reply.writeBoolean(_result97);
                    return true;
                case 258:
                    IUidFrozenStateChangedCallback _arg0185 =
                            IUidFrozenStateChangedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerUidFrozenStateChangedCallback(_arg0185);
                    reply.writeNoException();
                    return true;
                case 259:
                    IUidFrozenStateChangedCallback _arg0186 =
                            IUidFrozenStateChangedCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterUidFrozenStateChangedCallback(_arg0186);
                    reply.writeNoException();
                    return true;
                case 260:
                    int[] _arg0187 = data.createIntArray();
                    data.enforceNoDataAvail();
                    int[] _result98 = getUidFrozenState(_arg0187);
                    reply.writeNoException();
                    reply.writeIntArray(_result98);
                    return true;
                case 261:
                    int _arg0188 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result99 = getContentByTask(_arg0188);
                    reply.writeNoException();
                    reply.writeStringArray(_result99);
                    return true;
                case 262:
                    List<String> _result100 = getLongLiveApps();
                    reply.writeNoException();
                    reply.writeStringList(_result100);
                    return true;
                case 263:
                    String _arg0189 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result101 = addLongLiveApp(_arg0189);
                    reply.writeNoException();
                    reply.writeBoolean(_result101);
                    return true;
                case 264:
                    String _arg0190 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result102 = removeLongLiveApp(_arg0190);
                    reply.writeNoException();
                    reply.writeBoolean(_result102);
                    return true;
                case 265:
                    int _result103 = getMaxLongLiveApps();
                    reply.writeNoException();
                    reply.writeInt(_result103);
                    return true;
                case 266:
                    int _arg0191 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result104 = setLongLiveTask(_arg0191);
                    reply.writeNoException();
                    reply.writeBoolean(_result104);
                    return true;
                case 267:
                    int _arg0192 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result105 = clearLongLiveTask(_arg0192);
                    reply.writeNoException();
                    reply.writeBoolean(_result105);
                    return true;
                case 268:
                    List<String> _result106 = getLongLiveProcesses();
                    reply.writeNoException();
                    reply.writeStringList(_result106);
                    return true;
                case 269:
                    int _arg0193 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result107 = getLongLiveProcessesForUser(_arg0193);
                    reply.writeNoException();
                    reply.writeStringList(_result107);
                    return true;
                case 270:
                    int _arg0194 = data.readInt();
                    data.enforceNoDataAvail();
                    List _result108 = getLongLiveTaskIdsForUser(_arg0194);
                    reply.writeNoException();
                    reply.writeList(_result108);
                    return true;
                case 271:
                    int _arg0195 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result109 = getAutoRemoveRecents(_arg0195);
                    reply.writeNoException();
                    reply.writeBoolean(_result109);
                    return true;
                case 272:
                    RemoteCallback _arg0196 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    int _arg1112 = data.readInt();
                    data.enforceNoDataAvail();
                    registerDedicatedCallback(_arg0196, _arg1112);
                    reply.writeNoException();
                    return true;
                case 273:
                    String _arg0197 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result110 = setLongLiveApp(_arg0197);
                    reply.writeNoException();
                    reply.writeBoolean(_result110);
                    return true;
                case 274:
                    String _result111 = getLongLiveApp();
                    reply.writeNoException();
                    reply.writeString(_result111);
                    return true;
                case 275:
                    int _arg0198 = data.readInt();
                    boolean _arg1113 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result112 = moveTaskToBack(_arg0198, _arg1113);
                    reply.writeNoException();
                    reply.writeBoolean(_result112);
                    return true;
                case 276:
                    int _arg0199 = data.readInt();
                    boolean _arg1114 = data.readBoolean();
                    Bundle _arg249 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result113 = moveTaskToBackWithBundle(_arg0199, _arg1114, _arg249);
                    reply.writeNoException();
                    reply.writeBoolean(_result113);
                    return true;
                case 277:
                    int _arg0200 = data.readInt();
                    data.enforceNoDataAvail();
                    dismissUserSwitchingDialog(_arg0200);
                    reply.writeNoException();
                    return true;
                case 278:
                    Configuration _result114 = getGlobalConfiguration();
                    reply.writeNoException();
                    reply.writeTypedObject(_result114, 1);
                    return true;
                case 279:
                    String _arg0201 = data.readString();
                    int _arg1115 = data.readInt();
                    int _arg250 = data.readInt();
                    int _arg314 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result115 = checkPermissionForDevice(_arg0201, _arg1115, _arg250, _arg314);
                    reply.writeNoException();
                    reply.writeInt(_result115);
                    return true;
                case 280:
                    int _arg0202 = data.readInt();
                    int _arg1116 = data.readInt();
                    int _arg251 = data.readInt();
                    int _arg315 = data.readInt();
                    data.enforceNoDataAvail();
                    frozenBinderTransactionDetected(_arg0202, _arg1116, _arg251, _arg315);
                    return true;
                case 281:
                    int _arg0203 = data.readInt();
                    String _arg1117 = data.readString();
                    data.enforceNoDataAvail();
                    int _result116 = getBindingUidProcessState(_arg0203, _arg1117);
                    reply.writeNoException();
                    reply.writeInt(_result116);
                    return true;
                case 282:
                    int _arg0204 = data.readInt();
                    String _arg1118 = data.readString();
                    data.enforceNoDataAvail();
                    long _result117 = getUidLastIdleElapsedTime(_arg0204, _arg1118);
                    reply.writeNoException();
                    reply.writeLong(_result117);
                    return true;
                case 283:
                    int _arg0205 = data.readInt();
                    int _arg1119 = data.readInt();
                    String _arg252 = data.readString();
                    int _arg316 = data.readInt();
                    data.enforceNoDataAvail();
                    addOverridePermissionState(_arg0205, _arg1119, _arg252, _arg316);
                    reply.writeNoException();
                    return true;
                case 284:
                    int _arg0206 = data.readInt();
                    int _arg1120 = data.readInt();
                    String _arg253 = data.readString();
                    data.enforceNoDataAvail();
                    removeOverridePermissionState(_arg0206, _arg1120, _arg253);
                    reply.writeNoException();
                    return true;
                case 285:
                    int _arg0207 = data.readInt();
                    int _arg1121 = data.readInt();
                    data.enforceNoDataAvail();
                    clearOverridePermissionStates(_arg0207, _arg1121);
                    reply.writeNoException();
                    return true;
                case 286:
                    int _arg0208 = data.readInt();
                    data.enforceNoDataAvail();
                    clearAllOverridePermissionStates(_arg0208);
                    reply.writeNoException();
                    return true;
                case 287:
                    return onTransact$noteAppRestrictionEnabled$(data, reply);
                case 288:
                    int _arg0209 = data.readInt();
                    String _arg1122 = data.readString();
                    data.enforceNoDataAvail();
                    checkProfileForADCP(_arg0209, _arg1122);
                    reply.writeNoException();
                    return true;
                case 289:
                    List<String> _arg0210 = data.createStringArrayList();
                    int _arg1123 = data.readInt();
                    data.enforceNoDataAvail();
                    preloadBoosterAppsFromIpm(_arg0210, _arg1123);
                    reply.writeNoException();
                    return true;
                case 290:
                    int _arg0211 = data.readInt();
                    String _arg1124 = data.readString();
                    data.enforceNoDataAvail();
                    updateFlingerFlag(_arg0211, _arg1124);
                    reply.writeNoException();
                    return true;
                case 291:
                    int _arg0212 = data.readInt();
                    String _arg1125 = data.readString();
                    int _arg254 = data.readInt();
                    data.enforceNoDataAvail();
                    SemAppRestrictionManager.RestrictionInfo _result118 =
                            getRestrictionInfo(_arg0212, _arg1125, _arg254);
                    reply.writeNoException();
                    reply.writeTypedObject(_result118, 1);
                    return true;
                case 292:
                    int _arg0213 = data.readInt();
                    String _arg1126 = data.readString();
                    int _arg255 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result119 = canRestrict(_arg0213, _arg1126, _arg255);
                    reply.writeNoException();
                    reply.writeBoolean(_result119);
                    return true;
                case 293:
                    return onTransact$restrict$(data, reply);
                case 294:
                    int _arg0214 = data.readInt();
                    data.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result120 =
                            getRestrictableList(_arg0214);
                    reply.writeNoException();
                    reply.writeTypedList(_result120, 1);
                    return true;
                case 295:
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result121 =
                            getAllRestrictedList();
                    reply.writeNoException();
                    reply.writeTypedList(_result121, 1);
                    return true;
                case 296:
                    int _arg0215 = data.readInt();
                    data.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result122 =
                            getRestrictedList(_arg0215);
                    reply.writeNoException();
                    reply.writeTypedList(_result122, 1);
                    return true;
                case 297:
                    SemAppRestrictionManager.RestrictionInfo _arg0216 =
                            (SemAppRestrictionManager.RestrictionInfo)
                                    data.readTypedObject(
                                            SemAppRestrictionManager.RestrictionInfo.CREATOR);
                    List<SemAppRestrictionManager.AppRestrictionInfo> _arg1127 =
                            data.createTypedArrayList(
                                    SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result123 = updateRestrictionInfo(_arg0216, _arg1127);
                    reply.writeNoException();
                    reply.writeBoolean(_result123);
                    return true;
                case 298:
                    List<SemAppRestrictionManager.AppRestrictionInfo> _arg0217 =
                            data.createTypedArrayList(
                                    SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result124 = clearRestrictionInfo(_arg0217);
                    reply.writeNoException();
                    reply.writeBoolean(_result124);
                    return true;
                case 299:
                    int _arg0218 = data.readInt();
                    data.enforceNoDataAvail();
                    setTTSPkgInfo(_arg0218);
                    reply.writeNoException();
                    return true;
                case 300:
                    clearTTSPkgInfo();
                    reply.writeNoException();
                    return true;
                case 301:
                    int _arg0219 = data.readInt();
                    int _arg1128 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PackageInfo> _result125 =
                            getInstalledPackageListFromMARs(_arg0219, _arg1128);
                    reply.writeNoException();
                    reply.writeTypedObject(_result125, 1);
                    return true;
                case 302:
                    int _arg0220 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result126 = getPackageFromAppProcesses(_arg0220);
                    reply.writeNoException();
                    reply.writeString(_result126);
                    return true;
                case 303:
                    Configuration _arg0221 =
                            (Configuration) data.readTypedObject(Configuration.CREATOR);
                    String _arg1129 = data.readString();
                    String _arg256 = data.readString();
                    LocaleList _arg317 = (LocaleList) data.readTypedObject(LocaleList.CREATOR);
                    data.enforceNoDataAvail();
                    updatePersistentConfigurationAndLocaleOverlays(
                            _arg0221, _arg1129, _arg256, _arg317);
                    reply.writeNoException();
                    return true;
                case 304:
                    int _arg0222 = data.readInt();
                    int _arg1130 = data.readInt();
                    data.enforceNoDataAvail();
                    reportAbnormalUsage(_arg0222, _arg1130);
                    reply.writeNoException();
                    return true;
                case 305:
                    boolean _result127 = isHeapDumpAllowed();
                    reply.writeNoException();
                    reply.writeBoolean(_result127);
                    return true;
                case 306:
                    int _arg0223 = data.readInt();
                    boolean _arg1131 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result128 = setFGSFilter(_arg0223, _arg1131);
                    reply.writeNoException();
                    reply.writeBoolean(_result128);
                    return true;
                case 307:
                    resetAbnormalList();
                    reply.writeNoException();
                    return true;
                case 308:
                    int _arg0224 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result129 = isFreezableUid(_arg0224);
                    reply.writeNoException();
                    reply.writeBoolean(_result129);
                    return true;
                case 309:
                    int _arg0225 = data.readInt();
                    boolean _arg1132 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result130 = setProcessSlowdown(_arg0225, _arg1132);
                    reply.writeNoException();
                    reply.writeBoolean(_result130);
                    return true;
                case 310:
                    int[] _result131 = getIsolatedProcessList();
                    reply.writeNoException();
                    reply.writeIntArray(_result131);
                    return true;
                case 311:
                    IIntentSender _arg0226 =
                            IIntentSender.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    Bundle _result132 = getOptionsForIntentSender(_arg0226);
                    reply.writeNoException();
                    reply.writeTypedObject(_result132, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IActivityManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor openContentUri(String uriString) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriString);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidObserver(
                    IUidObserver observer, int which, int cutpoint, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(which);
                    _data.writeInt(cutpoint);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidObserver(IUidObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder registerUidObserverForUids(
                    IUidObserver observer,
                    int which,
                    int cutpoint,
                    String callingPackage,
                    int[] uids)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(which);
                    _data.writeInt(cutpoint);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addUidToObserver(IBinder observerToken, String callingPackage, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observerToken);
                    _data.writeString(callingPackage);
                    _data.writeInt(uid);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeUidFromObserver(IBinder observerToken, String callingPackage, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observerToken);
                    _data.writeString(callingPackage);
                    _data.writeInt(uid);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUidActive(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessState(int uid, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermission(String permission, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiBegin(int apiType, int appUid, int appPid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(apiType);
                    _data.writeInt(appUid);
                    _data.writeInt(appPid);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiEnd(int apiType, int appUid, int appPid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(apiType);
                    _data.writeInt(appUid);
                    _data.writeInt(appPid);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiStateChanged(int apiType, int state, int appUid, int appPid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(apiType);
                    _data.writeInt(state);
                    _data.writeInt(appUid);
                    _data.writeInt(appPid);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationCrash(
                    IBinder app, ApplicationErrorReport.ParcelableCrashInfo crashInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeTypedObject(crashInfo, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivity(
                    IApplicationThread caller,
                    String callingPackage,
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
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeStrongBinder(resultTo);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    _data.writeInt(flags);
                    _data.writeTypedObject(profilerInfo, 0);
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

            @Override // android.app.IActivityManager
            public int startActivityWithFeature(
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
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(callingFeatureId);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(intent, 0);
                    try {
                        _data.writeString(resolvedType);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongBinder(resultTo);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resultWho);
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
                    _data.writeInt(requestCode);
                    try {
                        _data.writeInt(flags);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(profilerInfo, 0);
                        try {
                            _data.writeTypedObject(options, 0);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(15, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public void unhandledBack() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean finishActivity(IBinder token, int code, Intent data, int finishTask)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(finishTask);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiver(
                    IApplicationThread caller,
                    String callerPackage,
                    IIntentReceiver receiver,
                    IntentFilter filter,
                    String requiredPermission,
                    int userId,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callerPackage);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(filter, 0);
                    _data.writeString(requiredPermission);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiverWithFeature(
                    IApplicationThread caller,
                    String callerPackage,
                    String callingFeatureId,
                    String receiverId,
                    IIntentReceiver receiver,
                    IntentFilter filter,
                    String requiredPermission,
                    int userId,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callerPackage);
                    _data.writeString(callingFeatureId);
                    _data.writeString(receiverId);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(filter, 0);
                    _data.writeString(requiredPermission);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterReceiver(IIntentReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntent(
                    IApplicationThread caller,
                    Intent intent,
                    String resolvedType,
                    IIntentReceiver resultTo,
                    int resultCode,
                    String resultData,
                    Bundle map,
                    String[] requiredPermissions,
                    int appOp,
                    Bundle options,
                    boolean serialized,
                    boolean sticky,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeTypedObject(intent, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(resolvedType);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongInterface(resultTo);
                    try {
                        _data.writeInt(resultCode);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resultData);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(map, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(requiredPermissions);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(appOp);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(options, 0);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(serialized);
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
                try {
                    _data.writeBoolean(sticky);
                    try {
                        _data.writeInt(userId);
                        this.mRemote.transact(21, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public int broadcastIntentWithFeature(
                    IApplicationThread caller,
                    String callingFeatureId,
                    Intent intent,
                    String resolvedType,
                    IIntentReceiver resultTo,
                    int resultCode,
                    String resultData,
                    Bundle map,
                    String[] requiredPermissions,
                    String[] excludePermissions,
                    String[] excludePackages,
                    int appOp,
                    Bundle options,
                    boolean serialized,
                    boolean sticky,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callingFeatureId);
                } catch (Throwable th) {
                    th = th;
                }
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
                    try {
                        _data.writeStrongInterface(resultTo);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(resultCode);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resultData);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(map, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(requiredPermissions);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(excludePermissions);
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
                try {
                    _data.writeStringArray(excludePackages);
                    try {
                        _data.writeInt(appOp);
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(options, 0);
                        _data.writeBoolean(serialized);
                        _data.writeBoolean(sticky);
                        _data.writeInt(userId);
                        this.mRemote.transact(22, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public void unbroadcastIntent(IApplicationThread caller, Intent intent, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeTypedObject(intent, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishReceiver(
                    IBinder who,
                    int resultCode,
                    String resultData,
                    Bundle map,
                    boolean abortBroadcast,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who);
                    _data.writeInt(resultCode);
                    _data.writeString(resultData);
                    _data.writeTypedObject(map, 0);
                    _data.writeBoolean(abortBroadcast);
                    _data.writeInt(flags);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void attachApplication(IApplicationThread app, long startSeq)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(app);
                    _data.writeLong(startSeq);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishAttachApplication(long startSeq, long timestampApplicationOnCreateNs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(startSeq);
                    _data.writeLong(timestampApplicationOnCreateNs);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int maxNum)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    List<ActivityManager.RunningTaskInfo> _result =
                            _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToFront(
                    IApplicationThread caller,
                    String callingPackage,
                    int task,
                    int flags,
                    Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callingPackage);
                    _data.writeInt(task);
                    _data.writeInt(flags);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(onlyRoot);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProvider(
                    IApplicationThread caller,
                    String callingPackage,
                    String name,
                    int userId,
                    boolean stable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(callingPackage);
                    _data.writeString(name);
                    _data.writeInt(userId);
                    _data.writeBoolean(stable);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    ContentProviderHolder _result =
                            (ContentProviderHolder)
                                    _reply.readTypedObject(ContentProviderHolder.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishContentProviders(
                    IApplicationThread caller, List<ContentProviderHolder> providers)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeTypedList(providers, 0);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean refContentProvider(
                    IBinder connection, int stableDelta, int unstableDelta) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeInt(stableDelta);
                    _data.writeInt(unstableDelta);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public PendingIntent getRunningServiceControlPanel(ComponentName service)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(service, 0);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    PendingIntent _result =
                            (PendingIntent) _reply.readTypedObject(PendingIntent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ComponentName startService(
                    IApplicationThread caller,
                    Intent service,
                    String resolvedType,
                    boolean requireForeground,
                    String callingPackage,
                    String callingFeatureId,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeTypedObject(service, 0);
                    _data.writeString(resolvedType);
                    _data.writeBoolean(requireForeground);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    _data.writeInt(userId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopService(
                    IApplicationThread caller, Intent service, String resolvedType, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeTypedObject(service, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(userId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindService(
                    IApplicationThread caller,
                    IBinder token,
                    Intent service,
                    String resolvedType,
                    IServiceConnection connection,
                    long flags,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(service, 0);
                    _data.writeString(resolvedType);
                    _data.writeStrongInterface(connection);
                    _data.writeLong(flags);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindServiceInstance(
                    IApplicationThread caller,
                    IBinder token,
                    Intent service,
                    String resolvedType,
                    IServiceConnection connection,
                    long flags,
                    String instanceName,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(service, 0);
                    _data.writeString(resolvedType);
                    _data.writeStrongInterface(connection);
                    _data.writeLong(flags);
                    _data.writeString(instanceName);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateServiceGroup(IServiceConnection connection, int group, int importance)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(connection);
                    _data.writeInt(group);
                    _data.writeInt(importance);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unbindService(IServiceConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(connection);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishService(IBinder token, Intent intent, IBinder service)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongBinder(service);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDebugApp(String packageName, boolean waitForDebugger, boolean persistent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(waitForDebugger);
                    _data.writeBoolean(persistent);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAgentApp(String packageName, String agent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(agent);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAlwaysFinish(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startInstrumentation(
                    ComponentName className,
                    String profileFile,
                    int flags,
                    Bundle arguments,
                    IInstrumentationWatcher watcher,
                    IUiAutomationConnection connection,
                    int userId,
                    String abiOverride)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeString(profileFile);
                    _data.writeInt(flags);
                    _data.writeTypedObject(arguments, 0);
                    _data.writeStrongInterface(watcher);
                    _data.writeStrongInterface(connection);
                    _data.writeInt(userId);
                    _data.writeString(abiOverride);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addInstrumentationResults(IApplicationThread target, Bundle results)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(target);
                    _data.writeTypedObject(results, 0);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishInstrumentation(
                    IApplicationThread target, int resultCode, Bundle results)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(target);
                    _data.writeInt(resultCode);
                    _data.writeTypedObject(results, 0);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getConfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    Configuration _result =
                            (Configuration) _reply.readTypedObject(Configuration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateConfiguration(Configuration values) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(values, 0);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateMccMncConfiguration(String mcc, String mnc)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mcc);
                    _data.writeString(mnc);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopServiceToken(ComponentName className, IBinder token, int startId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeStrongBinder(token);
                    _data.writeInt(startId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessLimit(int max) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(max);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getProcessLimit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkUriPermission(
                    Uri uri, int pid, int uid, int mode, int userId, IBinder callerToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(callerToken);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkContentUriPermissionFull(
                    Uri uri, int pid, int uid, int mode, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] checkUriPermissions(
                    List<Uri> uris, int pid, int uid, int mode, int userId, IBinder callerToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(uris, 0);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(callerToken);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void grantUriPermission(
                    IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(targetPkg);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void revokeUriPermission(
                    IApplicationThread caller, String targetPkg, Uri uri, int mode, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeString(targetPkg);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(mode);
                    _data.writeInt(userId);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityController(IActivityController watcher, boolean imAMonkey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    _data.writeBoolean(imAMonkey);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showWaitingForDebugger(IApplicationThread who, boolean waiting)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(who);
                    _data.writeBoolean(waiting);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void signalPersistentProcesses(int signal) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(signal);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice getRecentTasks(int maxNum, int flags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void serviceDoneExecuting(
                    IBinder token, int type, int startId, int res, Intent intent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(type);
                    _data.writeInt(startId);
                    _data.writeInt(res);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(62, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSender(
                    int type,
                    String packageName,
                    IBinder token,
                    String resultWho,
                    int requestCode,
                    Intent[] intents,
                    String[] resolvedTypes,
                    int flags,
                    Bundle options,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    _data.writeTypedArray(intents, 0);
                    _data.writeStringArray(resolvedTypes);
                    _data.writeInt(flags);
                    _data.writeTypedObject(options, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    IIntentSender _result =
                            IIntentSender.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSenderWithFeature(
                    int type,
                    String packageName,
                    String featureId,
                    IBinder token,
                    String resultWho,
                    int requestCode,
                    Intent[] intents,
                    String[] resolvedTypes,
                    int flags,
                    Bundle options,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    try {
                        _data.writeString(packageName);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(featureId);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongBinder(token);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
                try {
                    _data.writeString(resultWho);
                    try {
                        _data.writeInt(requestCode);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedArray(intents, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(resolvedTypes);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(flags);
                    try {
                        _data.writeTypedObject(options, 0);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(64, _data, _reply, 0);
                        _reply.readException();
                        IIntentSender _result =
                                IIntentSender.Stub.asInterface(_reply.readStrongBinder());
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public void cancelIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender sender)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    ActivityManager.PendingIntentInfo _result =
                            (ActivityManager.PendingIntentInfo)
                                    _reply.readTypedObject(
                                            ActivityManager.PendingIntentInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerIntentSenderCancelListenerEx(
                    IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterIntentSenderCancelListener(
                    IIntentSender sender, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void enterSafeMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteWakeupAlarm(
                    IIntentSender sender,
                    WorkSource workSource,
                    int sourceUid,
                    String sourcePkg,
                    String tag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeTypedObject(workSource, 0);
                    _data.writeInt(sourceUid);
                    _data.writeString(sourcePkg);
                    _data.writeString(tag);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProvider(IBinder connection, boolean stable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    _data.writeBoolean(stable);
                    this.mRemote.transact(71, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRequestedOrientation(IBinder token, int requestedOrientation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(requestedOrientation);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindFinished(IBinder token, Intent service, boolean doRebind)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(service, 0);
                    _data.writeBoolean(doRebind);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessImportant(
                    IBinder token, int pid, boolean isForeground, String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(pid);
                    _data.writeBoolean(isForeground);
                    _data.writeString(reason);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setServiceForeground(
                    ComponentName className,
                    IBinder token,
                    int id,
                    Notification notification,
                    int flags,
                    int foregroundServiceType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeStrongBinder(token);
                    _data.writeInt(id);
                    _data.writeTypedObject(notification, 0);
                    _data.writeInt(flags);
                    _data.writeInt(foregroundServiceType);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getForegroundServiceType(ComponentName className, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(nonRoot);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMemoryInfo(ActivityManager.MemoryInfo outInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInfo.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    List<ActivityManager.ProcessErrorStateInfo> _result =
                            _reply.createTypedArrayList(
                                    ActivityManager.ProcessErrorStateInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearApplicationUserData(
                    String packageName,
                    boolean keepState,
                    IPackageDataObserver observer,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(keepState);
                    _data.writeStrongInterface(observer);
                    _data.writeInt(userId);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppForUser(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerForegroundServiceObserver(IForegroundServiceObserver callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageEvenWhenStopping(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageByAdmin(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killPids(int[] pids, String reason, boolean secure)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    _data.writeString(reason);
                    _data.writeBoolean(secure);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningServiceInfo> getServices(int maxNum, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(maxNum);
                    _data.writeInt(flags);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    List<ActivityManager.RunningServiceInfo> _result =
                            _reply.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    List<ActivityManager.RunningAppProcessInfo> _result =
                            _reply.createTypedArrayList(
                                    ActivityManager.RunningAppProcessInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder peekService(Intent service, String resolvedType, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(service, 0);
                    _data.writeString(resolvedType);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean profileControl(
                    String process,
                    int userId,
                    boolean start,
                    ProfilerInfo profilerInfo,
                    int profileType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(process);
                    _data.writeInt(userId);
                    _data.writeBoolean(start);
                    _data.writeTypedObject(profilerInfo, 0);
                    _data.writeInt(profileType);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shutdown(int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeout);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean bindBackupAgent(
                    String packageName,
                    int backupRestoreMode,
                    int targetUserId,
                    int backupDestination)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(backupRestoreMode);
                    _data.writeInt(targetUserId);
                    _data.writeInt(backupDestination);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backupAgentCreated(String packageName, IBinder agent, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(agent);
                    _data.writeInt(userId);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindBackupAgent(ApplicationInfo appInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(appInfo, 0);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int handleIncomingUser(
                    int callingPid,
                    int callingUid,
                    int userId,
                    boolean allowAll,
                    boolean requireFull,
                    String name,
                    String callerPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callingPid);
                    _data.writeInt(callingUid);
                    _data.writeInt(userId);
                    _data.writeBoolean(allowAll);
                    _data.writeBoolean(requireFull);
                    _data.writeString(name);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addPackageDependency(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplication(
                    String pkg, int appId, int userId, String reason, int exitInfoReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    _data.writeInt(exitInfoReason);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogsInDisplay(String reason, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    _data.writeInt(displayId);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    Debug.MemoryInfo[] _result =
                            (Debug.MemoryInfo[]) _reply.createTypedArray(Debug.MemoryInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplicationProcess(String processName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean handleApplicationWtf(
                    IBinder app,
                    String tag,
                    boolean system,
                    ApplicationErrorReport.ParcelableCrashInfo crashInfo,
                    int immediateCallerPid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeString(tag);
                    _data.writeBoolean(system);
                    _data.writeTypedObject(crashInfo, 0);
                    _data.writeInt(immediateCallerPid);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killBackgroundProcesses(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserAMonkey() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    List<ApplicationInfo> _result =
                            _reply.createTypedArrayList(ApplicationInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishHeavyWeightApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationStrictModeViolation(
                    IBinder app, int penaltyMask, StrictMode.ViolationInfo crashInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(app);
                    _data.writeInt(penaltyMask);
                    _data.writeTypedObject(crashInfo, 0);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerStrictModeCallback(IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithType(
                    int uid,
                    int initialPid,
                    String packageName,
                    int userId,
                    String message,
                    boolean force,
                    int exceptionTypeId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(initialPid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(message);
                    _data.writeBoolean(force);
                    _data.writeInt(exceptionTypeId);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithTypeWithExtras(
                    int uid,
                    int initialPid,
                    String packageName,
                    int userId,
                    String message,
                    boolean force,
                    int exceptionTypeId,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(initialPid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(message);
                    _data.writeBoolean(force);
                    _data.writeInt(exceptionTypeId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMimeTypeFilterAsync(Uri uri, int userId, RemoteCallback resultCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(userId);
                    _data.writeTypedObject(resultCallback, 0);
                    this.mRemote.transact(114, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean dumpHeap(
                    String process,
                    int userId,
                    boolean managed,
                    boolean mallocInfo,
                    boolean runGc,
                    String dumpBitmaps,
                    String path,
                    ParcelFileDescriptor fd,
                    RemoteCallback finishCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(process);
                    _data.writeInt(userId);
                    _data.writeBoolean(managed);
                    _data.writeBoolean(mallocInfo);
                    _data.writeBoolean(runGc);
                    _data.writeString(dumpBitmaps);
                    _data.writeString(path);
                    _data.writeTypedObject(fd, 0);
                    _data.writeTypedObject(finishCallback, 0);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserRunning(int userid, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(flags);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPackageScreenCompatMode(String packageName, int mode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(mode);
                    this.mRemote.transact(117, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean switchUser(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    this.mRemote.transact(118, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingFromUserMessage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(119, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingToUserMessage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(120, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setStopUserOnSwitch(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(121, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(122, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerProcessObserver(IProcessObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(123, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterProcessObserver(IProcessObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(124, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderTargetedToPackage(IIntentSender sender)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(125, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfiguration(Configuration values) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(values, 0);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfigurationWithAttribution(
                    Configuration values, String callingPackageName, String callingAttributionTag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(values, 0);
                    _data.writeString(callingPackageName);
                    _data.writeString(callingAttributionTag);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long[] getProcessPss(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showBootMessage(CharSequence msg, boolean always) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (msg != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(msg, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeBoolean(always);
                    this.mRemote.transact(129, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProviderExternal(
                    String name, int userId, IBinder token, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(token);
                    _data.writeString(tag);
                    this.mRemote.transact(131, _data, _reply, 0);
                    _reply.readException();
                    ContentProviderHolder _result =
                            (ContentProviderHolder)
                                    _reply.readTypedObject(ContentProviderHolder.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternal(String name, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(132, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternalAsUser(String name, IBinder token, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    this.mRemote.transact(133, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo outInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        outInfo.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killProcessesBelowForeground(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    this.mRemote.transact(135, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public UserInfo getCurrentUser() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(136, _data, _reply, 0);
                    _reply.readException();
                    UserInfo _result = (UserInfo) _reply.readTypedObject(UserInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getCurrentUserId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(137, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLaunchedFromUid(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    this.mRemote.transact(138, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unstableProviderDied(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    this.mRemote.transact(139, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAnActivity(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(140, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUser(
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
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(intent, 0);
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
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resultWho);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requestCode);
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
                    _data.writeInt(flags);
                    try {
                        _data.writeTypedObject(profilerInfo, 0);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(options, 0);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(141, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public int startActivityAsUserWithFeature(
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
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    try {
                        _data.writeTypedObject(intent, 0);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(resolvedType);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
                try {
                    _data.writeStrongBinder(resultTo);
                    try {
                        _data.writeString(resultWho);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(requestCode);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(profilerInfo, 0);
                    try {
                        _data.writeTypedObject(options, 0);
                        try {
                            _data.writeInt(userId);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(142, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
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

            @Override // android.app.IActivityManager
            public int stopUser(
                    int userid, boolean stopProfileRegardlessOfParent, IStopUserCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeBoolean(stopProfileRegardlessOfParent);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(143, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithCallback(int userid, IStopUserCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(144, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserExceptCertainProfiles(
                    int userid, boolean stopProfileRegardlessOfParent, IStopUserCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeBoolean(stopProfileRegardlessOfParent);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(145, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithDelayedLocking(int userid, IStopUserCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(146, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUserSwitchObserver(IUserSwitchObserver observer, String name)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    _data.writeString(name);
                    this.mRemote.transact(147, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUserSwitchObserver(IUserSwitchObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(148, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getRunningUserIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(149, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(150, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReport(int bugreportType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bugreportType);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithDescription(
                    String shareTitle, String shareDescription, int bugreportType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    _data.writeInt(bugreportType);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestTelephonyBugReport(String shareTitle, String shareDescription)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestWifiBugReport(String shareTitle, String shareDescription)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReportWithDescription(
                    String shareTitle, String shareDescription) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(shareTitle);
                    _data.writeString(shareDescription);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReport() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithExtraAttachment(Uri extraAttachment)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(extraAttachment, 0);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestFullBugReport() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestRemoteBugReport(long nonce) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(nonce);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean launchBugReportHandlerApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(160, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getBugreportWhitelistedPackages() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(161, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent getIntentForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(162, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLaunchedFromPackage(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    this.mRemote.transact(163, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUid(int appId, int userId, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    this.mRemote.transact(164, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setUserIsMonkey(boolean monkey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(monkey);
                    this.mRemote.transact(165, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void hang(IBinder who, boolean allowRestart) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(who);
                    _data.writeBoolean(allowRestart);
                    this.mRemote.transact(166, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(167, _data, _reply, 0);
                    _reply.readException();
                    List<ActivityTaskManager.RootTaskInfo> _result =
                            _reply.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(rootTaskId);
                    _data.writeBoolean(toTop);
                    this.mRemote.transact(168, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setFocusedRootTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(169, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(170, _data, _reply, 0);
                    _reply.readException();
                    ActivityTaskManager.RootTaskInfo _result =
                            (ActivityTaskManager.RootTaskInfo)
                                    _reply.readTypedObject(
                                            ActivityTaskManager.RootTaskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void restart() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void performIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(172, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotRespondingViaProvider(IBinder connection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection);
                    this.mRemote.transact(173, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Rect getTaskBounds(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(174, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessMemoryTrimLevel(String process, int userId, int level)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(process);
                    _data.writeInt(userId);
                    _data.writeInt(level);
                    this.mRemote.transact(175, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getTagForIntentSender(IIntentSender sender, String prefix)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeString(prefix);
                    this.mRemote.transact(176, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackground(int userid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    this.mRemote.transact(177, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(178, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityFromRecents(int taskId, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(179, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startSystemLockTaskMode(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(180, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopOfTask(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(181, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void bootAnimationComplete() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setThemeOverlayReady(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(183, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerTaskStackListener(ITaskStackListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(184, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterTaskStackListener(ITaskStackListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(185, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyCleartextNetwork(int uid, byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeByteArray(firstPacket);
                    this.mRemote.transact(186, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTaskResizeable(int taskId, int resizeableMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(resizeableMode);
                    this.mRemote.transact(187, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeTask(int taskId, Rect bounds, int resizeMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(bounds, 0);
                    _data.writeInt(resizeMode);
                    this.mRemote.transact(188, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(189, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDumpHeapDebugLimit(
                    String processName, int uid, long maxMemSize, String reportPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(processName);
                    _data.writeInt(uid);
                    _data.writeLong(maxMemSize);
                    _data.writeString(reportPackage);
                    this.mRemote.transact(190, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dumpHeapFinished(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(191, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateLockTaskPackages(int userId, String[] packages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStringArray(packages);
                    this.mRemote.transact(192, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmStart(
                    IIntentSender sender, WorkSource workSource, int sourceUid, String tag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeTypedObject(workSource, 0);
                    _data.writeInt(sourceUid);
                    _data.writeString(tag);
                    this.mRemote.transact(193, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmFinish(
                    IIntentSender sender, WorkSource workSource, int sourceUid, String tag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeTypedObject(workSource, 0);
                    _data.writeInt(sourceUid);
                    _data.writeString(tag);
                    this.mRemote.transact(194, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getPackageProcessState(String packageName, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(195, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(196, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor fd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    this.mRemote.transact(197, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void suppressResizeConfigChanges(boolean suppress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(suppress);
                    this.mRemote.transact(198, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser(
                    int userid, byte[] token, byte[] secret, IProgressListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(199, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser2(int userId, IProgressListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(200, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killPackageDependents(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void makePackageIdle(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDeterministicUidIdle(boolean deterministic) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(deterministic);
                    this.mRemote.transact(203, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMemoryTrimLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isVrModePackageEnabled(ComponentName packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(packageName, 0);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyLockedProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startConfirmDeviceCredentialIntent(Intent intent, Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void sendIdleJobTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int sendIntentSender(
                    IApplicationThread caller,
                    IIntentSender target,
                    IBinder whitelistToken,
                    int code,
                    Intent intent,
                    String resolvedType,
                    IIntentReceiver finishedReceiver,
                    String requiredPermission,
                    Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongInterface(target);
                    _data.writeStrongBinder(whitelistToken);
                    _data.writeInt(code);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeStrongInterface(finishedReceiver);
                    _data.writeString(requiredPermission);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isBackgroundRestricted(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRenderThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setHasTopUi(boolean hasTopUi) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(hasTopUi);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelTaskWindowTransition(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void scheduleApplicationInfoChanged(List<String> packageNames, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    _data.writeInt(userId);
                    this.mRemote.transact(214, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPersistentVrThread(int tid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(tid);
                    this.mRemote.transact(215, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForNetworkStateUpdate(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backgroundAllowlistUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(217, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundWithListener(
                    int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongInterface(unlockProgressListener);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startDelegateShellPermissionIdentity(int uid, String[] permissions)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeStringArray(permissions);
                    this.mRemote.transact(219, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(220, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getDelegatedShellPermissions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(221, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(222, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInForegroundWithListener(
                    int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongInterface(unlockProgressListener);
                    this.mRemote.transact(223, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotResponding(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    this.mRemote.transact(224, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void doActiveLaunch(String packageName, boolean isLauncher, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(isLauncher);
                    _data.writeInt(userId);
                    this.mRemote.transact(225, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(
                    String packageName, int maxNum, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(maxNum);
                    _data.writeInt(userId);
                    this.mRemote.transact(226, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<ApplicationStartInfo> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addApplicationStartInfoCompleteListener(
                    IApplicationStartInfoCompleteListener listener, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeApplicationStartInfoCompleteListener(
                    IApplicationStartInfoCompleteListener listener, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(228, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addStartInfoTimestamp(int key, long timestampNs, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeLong(timestampNs);
                    _data.writeInt(userId);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportStartInfoViewTimestamps(
                    long renderThreadDrawStartTimeNs, long framePresentedTimeNs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(renderThreadDrawStartTimeNs);
                    _data.writeLong(framePresentedTimeNs);
                    this.mRemote.transact(230, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(
                    String packageName, int pid, int maxNum, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(maxNum);
                    _data.writeInt(userId);
                    this.mRemote.transact(231, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<ApplicationExitInfo> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killProcessesWhenImperceptible(int[] pids, String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    _data.writeString(reason);
                    this.mRemote.transact(232, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityLocusContext(
                    ComponentName activity, LocusId locusId, IBinder appToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(activity, 0);
                    _data.writeTypedObject(locusId, 0);
                    _data.writeStrongBinder(appToken);
                    this.mRemote.transact(233, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessStateSummary(byte[] state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(state);
                    this.mRemote.transact(234, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(235, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(236, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUidForPermissionChange(int appId, int userId, String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    this.mRemote.transact(237, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAppErrors() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(238, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableAppFreezer(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(239, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableFgsNotificationRateLimit(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(240, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void holdLock(IBinder token, int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(durationMs);
                    this.mRemote.transact(241, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(242, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(243, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice queryIntentComponentsForIntentSender(
                    IIntentSender sender, int matchFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    _data.writeInt(matchFlags);
                    this.mRemote.transact(244, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessCapabilities(int uid, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(245, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastIdle() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(246, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastBarrier() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(247, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceDelayBroadcastDelivery(String targetPackage, long delayedDurationMs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetPackage);
                    _data.writeLong(delayedDurationMs);
                    this.mRemote.transact(248, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isProcessFrozen(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(249, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBackgroundRestrictionExemptionReason(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(250, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] queryRegisteredReceiverPackages(
                    Intent intent, String resolvedType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(userId);
                    this.mRemote.transact(251, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundVisibleOnDisplay(
                    int userid, int displayId, IProgressListener unlockProgressListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(unlockProgressListener);
                    this.mRemote.transact(252, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfileWithListener(
                    int userid, IProgressListener unlockProgressListener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userid);
                    _data.writeStrongInterface(unlockProgressListener);
                    this.mRemote.transact(253, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int restartUserInBackground(int userId, int userStartMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(userStartMode);
                    this.mRemote.transact(254, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(255, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shouldServiceTimeOut(ComponentName className, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(256, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean hasServiceTimeLimitExceeded(ComponentName className, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(className, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(257, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidFrozenStateChangedCallback(
                    IUidFrozenStateChangedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(258, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidFrozenStateChangedCallback(
                    IUidFrozenStateChangedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(259, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getUidFrozenState(int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(260, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] getContentByTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(261, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(262, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean addLongLiveApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(263, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeLongLiveApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(264, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMaxLongLiveApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(265, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(266, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearLongLiveTask(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(267, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcesses() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(268, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcessesForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(269, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List getLongLiveTaskIdsForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(270, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    List _result = _reply.readArrayList(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean getAutoRemoveRecents(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(271, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerDedicatedCallback(RemoteCallback resultCallback, int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(resultCallback, 0);
                    _data.writeInt(type);
                    this.mRemote.transact(272, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(273, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLongLiveApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(274, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBack(int taskId, boolean keepAlive) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeBoolean(keepAlive);
                    this.mRemote.transact(275, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBackWithBundle(int taskId, boolean keepAlive, Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeBoolean(keepAlive);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(276, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dismissUserSwitchingDialog(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(277, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getGlobalConfiguration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(278, _data, _reply, 0);
                    _reply.readException();
                    Configuration _result =
                            (Configuration) _reply.readTypedObject(Configuration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermissionForDevice(String permission, int pid, int uid, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(permission);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(279, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void frozenBinderTransactionDetected(int debugPid, int code, int flags, int err)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(debugPid);
                    _data.writeInt(code);
                    _data.writeInt(flags);
                    _data.writeInt(err);
                    this.mRemote.transact(280, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBindingUidProcessState(int uid, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(281, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long getUidLastIdleElapsedTime(int uid, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(282, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addOverridePermissionState(
                    int originatingUid, int uid, String permission, int result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(originatingUid);
                    _data.writeInt(uid);
                    _data.writeString(permission);
                    _data.writeInt(result);
                    this.mRemote.transact(283, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeOverridePermissionState(
                    int originatingUid, int uid, String permission) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(originatingUid);
                    _data.writeInt(uid);
                    _data.writeString(permission);
                    this.mRemote.transact(284, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearOverridePermissionStates(int originatingUid, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(originatingUid);
                    _data.writeInt(uid);
                    this.mRemote.transact(285, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearAllOverridePermissionStates(int originatingUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(originatingUid);
                    this.mRemote.transact(286, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAppRestrictionEnabled(
                    String packageName,
                    int uid,
                    int restrictionType,
                    boolean enabled,
                    int reason,
                    String subReason,
                    int source,
                    long threshold)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(restrictionType);
                    _data.writeBoolean(enabled);
                    _data.writeInt(reason);
                    _data.writeString(subReason);
                    _data.writeInt(source);
                    _data.writeLong(threshold);
                    this.mRemote.transact(287, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void checkProfileForADCP(int pid, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeString(pkg);
                    this.mRemote.transact(288, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void preloadBoosterAppsFromIpm(List<String> packageNames, int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packageNames);
                    _data.writeInt(type);
                    this.mRemote.transact(289, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateFlingerFlag(int pid, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeString(pkg);
                    this.mRemote.transact(290, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(
                    int type, String packageName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    this.mRemote.transact(291, _data, _reply, 0);
                    _reply.readException();
                    SemAppRestrictionManager.RestrictionInfo _result =
                            (SemAppRestrictionManager.RestrictionInfo)
                                    _reply.readTypedObject(
                                            SemAppRestrictionManager.RestrictionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean canRestrict(int type, String packageName, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    this.mRemote.transact(292, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean restrict(
                    int type, int state, boolean byUser, String packageName, int uid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    _data.writeBoolean(byUser);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    this.mRemote.transact(293, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(294, _data, _reply, 0);
                    _reply.readException();
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result =
                            _reply.createTypedArrayList(
                                    SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(295, _data, _reply, 0);
                    _reply.readException();
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result =
                            _reply.createTypedArrayList(
                                    SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(296, _data, _reply, 0);
                    _reply.readException();
                    List<SemAppRestrictionManager.AppRestrictionInfo> _result =
                            _reply.createTypedArrayList(
                                    SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateRestrictionInfo(
                    SemAppRestrictionManager.RestrictionInfo info,
                    List<SemAppRestrictionManager.AppRestrictionInfo> list)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedList(list, 0);
                    this.mRemote.transact(297, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearRestrictionInfo(
                    List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(list, 0);
                    this.mRemote.transact(298, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTTSPkgInfo(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(299, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearTTSPkgInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(300, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(
                    int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(301, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PackageInfo> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getPackageFromAppProcesses(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(302, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfigurationAndLocaleOverlays(
                    Configuration values,
                    String callingPackageName,
                    String callingAttributionTag,
                    LocaleList ll)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(values, 0);
                    _data.writeString(callingPackageName);
                    _data.writeString(callingAttributionTag);
                    _data.writeTypedObject(ll, 0);
                    this.mRemote.transact(303, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportAbnormalUsage(int pid, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(type);
                    this.mRemote.transact(304, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isHeapDumpAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(305, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setFGSFilter(int uid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(306, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAbnormalList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(307, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isFreezableUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(308, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessSlowdown(int pid, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(309, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getIsolatedProcessList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(310, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Bundle getOptionsForIntentSender(IIntentSender sender) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(sender);
                    this.mRemote.transact(311, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        private boolean onTransact$registerUidObserverForUids$(Parcel data, Parcel reply)
                throws RemoteException {
            IUidObserver _arg0 = IUidObserver.Stub.asInterface(data.readStrongBinder());
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            int[] _arg4 = data.createIntArray();
            data.enforceNoDataAvail();
            IBinder _result = registerUidObserverForUids(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeStrongBinder(_result);
            return true;
        }

        private boolean onTransact$startActivity$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg3 = data.readString();
            IBinder _arg4 = data.readStrongBinder();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            int _arg7 = data.readInt();
            ProfilerInfo _arg8 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
            Bundle _arg9 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            int _result =
                    startActivity(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$startActivityWithFeature$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
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
                    startActivityWithFeature(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$registerReceiver$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            IIntentReceiver _arg2 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            IntentFilter _arg3 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
            String _arg4 = data.readString();
            int _arg5 = data.readInt();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            Intent _result = registerReceiver(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$registerReceiverWithFeature$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            String _arg3 = data.readString();
            IIntentReceiver _arg4 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            IntentFilter _arg5 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
            String _arg6 = data.readString();
            int _arg7 = data.readInt();
            int _arg8 = data.readInt();
            data.enforceNoDataAvail();
            Intent _result =
                    registerReceiverWithFeature(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$broadcastIntent$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            Intent _arg1 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg2 = data.readString();
            IIntentReceiver _arg3 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            int _arg4 = data.readInt();
            String _arg5 = data.readString();
            Bundle _arg6 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            String[] _arg7 = data.createStringArray();
            int _arg8 = data.readInt();
            Bundle _arg9 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            boolean _arg10 = data.readBoolean();
            boolean _arg11 = data.readBoolean();
            int _arg12 = data.readInt();
            data.enforceNoDataAvail();
            int _result =
                    broadcastIntent(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10, _arg11, _arg12);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$broadcastIntentWithFeature$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg3 = data.readString();
            IIntentReceiver _arg4 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            int _arg5 = data.readInt();
            String _arg6 = data.readString();
            Bundle _arg7 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            String[] _arg8 = data.createStringArray();
            String[] _arg9 = data.createStringArray();
            String[] _arg10 = data.createStringArray();
            int _arg11 = data.readInt();
            Bundle _arg12 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            boolean _arg13 = data.readBoolean();
            boolean _arg14 = data.readBoolean();
            int _arg15 = data.readInt();
            data.enforceNoDataAvail();
            int _result =
                    broadcastIntentWithFeature(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10, _arg11, _arg12, _arg13, _arg14, _arg15);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$finishReceiver$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            boolean _arg4 = data.readBoolean();
            int _arg5 = data.readInt();
            data.enforceNoDataAvail();
            finishReceiver(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            return true;
        }

        private boolean onTransact$moveTaskToFront$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            moveTaskToFront(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProvider$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            boolean _arg4 = data.readBoolean();
            data.enforceNoDataAvail();
            ContentProviderHolder _result = getContentProvider(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$startService$(Parcel data, Parcel reply) throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            Intent _arg1 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg2 = data.readString();
            boolean _arg3 = data.readBoolean();
            String _arg4 = data.readString();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            ComponentName _result = startService(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            reply.writeTypedObject(_result, 1);
            return true;
        }

        private boolean onTransact$bindService$(Parcel data, Parcel reply) throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            IBinder _arg1 = data.readStrongBinder();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg3 = data.readString();
            IServiceConnection _arg4 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
            long _arg5 = data.readLong();
            String _arg6 = data.readString();
            int _arg7 = data.readInt();
            data.enforceNoDataAvail();
            int _result = bindService(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$bindServiceInstance$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            IBinder _arg1 = data.readStrongBinder();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg3 = data.readString();
            IServiceConnection _arg4 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
            long _arg5 = data.readLong();
            String _arg6 = data.readString();
            String _arg7 = data.readString();
            int _arg8 = data.readInt();
            data.enforceNoDataAvail();
            int _result =
                    bindServiceInstance(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$startInstrumentation$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            String _arg1 = data.readString();
            int _arg2 = data.readInt();
            Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            IInstrumentationWatcher _arg4 =
                    IInstrumentationWatcher.Stub.asInterface(data.readStrongBinder());
            IUiAutomationConnection _arg5 =
                    IUiAutomationConnection.Stub.asInterface(data.readStrongBinder());
            int _arg6 = data.readInt();
            String _arg7 = data.readString();
            data.enforceNoDataAvail();
            boolean _result =
                    startInstrumentation(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$checkUriPermission$(Parcel data, Parcel reply)
                throws RemoteException {
            Uri _arg0 = (Uri) data.readTypedObject(Uri.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            IBinder _arg5 = data.readStrongBinder();
            data.enforceNoDataAvail();
            int _result = checkUriPermission(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$checkContentUriPermissionFull$(Parcel data, Parcel reply)
                throws RemoteException {
            Uri _arg0 = (Uri) data.readTypedObject(Uri.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            int _result = checkContentUriPermissionFull(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$checkUriPermissions$(Parcel data, Parcel reply)
                throws RemoteException {
            List<Uri> _arg0 = data.createTypedArrayList(Uri.CREATOR);
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            IBinder _arg5 = data.readStrongBinder();
            data.enforceNoDataAvail();
            int[] _result = checkUriPermissions(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            reply.writeIntArray(_result);
            return true;
        }

        private boolean onTransact$grantUriPermission$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            Uri _arg2 = (Uri) data.readTypedObject(Uri.CREATOR);
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            grantUriPermission(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$revokeUriPermission$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            Uri _arg2 = (Uri) data.readTypedObject(Uri.CREATOR);
            int _arg3 = data.readInt();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            revokeUriPermission(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$serviceDoneExecuting$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            int _arg3 = data.readInt();
            Intent _arg4 = (Intent) data.readTypedObject(Intent.CREATOR);
            data.enforceNoDataAvail();
            serviceDoneExecuting(_arg0, _arg1, _arg2, _arg3, _arg4);
            return true;
        }

        private boolean onTransact$getIntentSender$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            IBinder _arg2 = data.readStrongBinder();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            Intent[] _arg5 = (Intent[]) data.createTypedArray(Intent.CREATOR);
            String[] _arg6 = data.createStringArray();
            int _arg7 = data.readInt();
            Bundle _arg8 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            int _arg9 = data.readInt();
            data.enforceNoDataAvail();
            IIntentSender _result =
                    getIntentSender(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
            reply.writeNoException();
            reply.writeStrongInterface(_result);
            return true;
        }

        private boolean onTransact$getIntentSenderWithFeature$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            String _arg1 = data.readString();
            String _arg2 = data.readString();
            IBinder _arg3 = data.readStrongBinder();
            String _arg4 = data.readString();
            int _arg5 = data.readInt();
            Intent[] _arg6 = (Intent[]) data.createTypedArray(Intent.CREATOR);
            String[] _arg7 = data.createStringArray();
            int _arg8 = data.readInt();
            Bundle _arg9 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            int _arg10 = data.readInt();
            data.enforceNoDataAvail();
            IIntentSender _result =
                    getIntentSenderWithFeature(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10);
            reply.writeNoException();
            reply.writeStrongInterface(_result);
            return true;
        }

        private boolean onTransact$noteWakeupAlarm$(Parcel data, Parcel reply)
                throws RemoteException {
            IIntentSender _arg0 = IIntentSender.Stub.asInterface(data.readStrongBinder());
            WorkSource _arg1 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            String _arg4 = data.readString();
            data.enforceNoDataAvail();
            noteWakeupAlarm(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$setServiceForeground$(Parcel data, Parcel reply)
                throws RemoteException {
            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
            IBinder _arg1 = data.readStrongBinder();
            int _arg2 = data.readInt();
            Notification _arg3 = (Notification) data.readTypedObject(Notification.CREATOR);
            int _arg4 = data.readInt();
            int _arg5 = data.readInt();
            data.enforceNoDataAvail();
            setServiceForeground(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$profileControl$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            ProfilerInfo _arg3 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = profileControl(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$handleIncomingUser$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            String _arg5 = data.readString();
            String _arg6 = data.readString();
            data.enforceNoDataAvail();
            int _result = handleIncomingUser(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$killApplication$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            killApplication(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$handleApplicationWtf$(Parcel data, Parcel reply)
                throws RemoteException {
            IBinder _arg0 = data.readStrongBinder();
            String _arg1 = data.readString();
            boolean _arg2 = data.readBoolean();
            ApplicationErrorReport.ParcelableCrashInfo _arg3 =
                    (ApplicationErrorReport.ParcelableCrashInfo)
                            data.readTypedObject(
                                    ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = handleApplicationWtf(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$crashApplicationWithType$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            boolean _arg5 = data.readBoolean();
            int _arg6 = data.readInt();
            data.enforceNoDataAvail();
            crashApplicationWithType(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$crashApplicationWithTypeWithExtras$(Parcel data, Parcel reply)
                throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            String _arg2 = data.readString();
            int _arg3 = data.readInt();
            String _arg4 = data.readString();
            boolean _arg5 = data.readBoolean();
            int _arg6 = data.readInt();
            Bundle _arg7 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            crashApplicationWithTypeWithExtras(
                    _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$dumpHeap$(Parcel data, Parcel reply) throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            boolean _arg3 = data.readBoolean();
            boolean _arg4 = data.readBoolean();
            String _arg5 = data.readString();
            String _arg6 = data.readString();
            ParcelFileDescriptor _arg7 =
                    (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
            RemoteCallback _arg8 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
            data.enforceNoDataAvail();
            boolean _result =
                    dumpHeap(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        private boolean onTransact$startActivityAsUser$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            String _arg1 = data.readString();
            Intent _arg2 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg3 = data.readString();
            IBinder _arg4 = data.readStrongBinder();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            int _arg7 = data.readInt();
            ProfilerInfo _arg8 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
            Bundle _arg9 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            int _arg10 = data.readInt();
            data.enforceNoDataAvail();
            int _result =
                    startActivityAsUser(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$startActivityAsUserWithFeature$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
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
            int _arg11 = data.readInt();
            data.enforceNoDataAvail();
            int _result =
                    startActivityAsUserWithFeature(
                            _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9,
                            _arg10, _arg11);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$sendIntentSender$(Parcel data, Parcel reply)
                throws RemoteException {
            IApplicationThread _arg0 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
            IIntentSender _arg1 = IIntentSender.Stub.asInterface(data.readStrongBinder());
            IBinder _arg2 = data.readStrongBinder();
            int _arg3 = data.readInt();
            Intent _arg4 = (Intent) data.readTypedObject(Intent.CREATOR);
            String _arg5 = data.readString();
            IIntentReceiver _arg6 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
            String _arg7 = data.readString();
            Bundle _arg8 = (Bundle) data.readTypedObject(Bundle.CREATOR);
            data.enforceNoDataAvail();
            int _result =
                    sendIntentSender(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
            reply.writeNoException();
            reply.writeInt(_result);
            return true;
        }

        private boolean onTransact$noteAppRestrictionEnabled$(Parcel data, Parcel reply)
                throws RemoteException {
            String _arg0 = data.readString();
            int _arg1 = data.readInt();
            int _arg2 = data.readInt();
            boolean _arg3 = data.readBoolean();
            int _arg4 = data.readInt();
            String _arg5 = data.readString();
            int _arg6 = data.readInt();
            long _arg7 = data.readLong();
            data.enforceNoDataAvail();
            noteAppRestrictionEnabled(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
            reply.writeNoException();
            return true;
        }

        private boolean onTransact$restrict$(Parcel data, Parcel reply) throws RemoteException {
            int _arg0 = data.readInt();
            int _arg1 = data.readInt();
            boolean _arg2 = data.readBoolean();
            String _arg3 = data.readString();
            int _arg4 = data.readInt();
            data.enforceNoDataAvail();
            boolean _result = restrict(_arg0, _arg1, _arg2, _arg3, _arg4);
            reply.writeNoException();
            reply.writeBoolean(_result);
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 310;
        }
    }
}
