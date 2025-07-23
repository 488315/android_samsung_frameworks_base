package android.app;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ApplicationErrorReport;
import android.app.IActivityController;
import android.app.IApplicationStartInfoCompleteListener;
import android.app.IApplicationThread;
import android.app.IForegroundServiceObserver;
import android.app.IInstrumentationWatcher;
import android.app.IProcessObserver;
import android.app.IServiceConnection;
import android.app.IStopUserCallback;
import android.app.ITaskStackListener;
import android.app.IUiAutomationConnection;
import android.app.IUidFrozenStateChangedCallback;
import android.app.IUidObserver;
import android.app.IUserSwitchObserver;
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
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.WorkSource;
import android.text.TextUtils;
import com.android.internal.os.IResultReceiver;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.sdhms.SemAppRestrictionManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IActivityManager extends IInterface {

    public static class Default implements IActivityManager {
        @Override // android.app.IActivityManager
        public void addApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean addLongLiveApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void addOverridePermissionState(int i, int i2, String str, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void addPackageDependency(String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void addStartInfoTimestamp(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void addUidToObserver(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void appNotResponding(String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IActivityManager
        public void attachApplication(IApplicationThread iApplicationThread, long j) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void backgroundAllowlistUid(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void backupAgentCreated(String str, IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean bindBackupAgent(String str, int i, int i2, int i3, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int bindServiceInstance(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, String str3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void bootAnimationComplete() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int broadcastIntentWithFeature(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IIntentReceiver iIntentReceiver, int i, String str3, Bundle bundle, String[] strArr, String[] strArr2, String[] strArr3, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean canRestrict(int i, String str, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void cancelTaskWindowTransition(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean checkAutoRunBlockedApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkPermission(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int checkPermissionForDevice(String str, int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void checkProfileForADCP(int i, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void clearAllOverridePermissionStates(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean clearApplicationUserData(String str, boolean z, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean clearLongLiveTask(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void clearOverridePermissionStates(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void clearTTSPkgInfo() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void closeSystemDialogs(String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void crashApplicationWithType(int i, int i2, String str, int i3, String str2, boolean z, int i4) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void crashApplicationWithTypeWithExtras(int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void dismissUserSwitchingDialog(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void doActiveLaunch(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean dumpHeap(String str, int i, boolean z, boolean z2, boolean z3, String str2, String str3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void dumpHeapFinished(String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean enableAppFreezer(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean enableFgsNotificationRateLimit(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void enterSafeMode() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void finishAttachApplication(long j, long j2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishHeavyWeightApp() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceDelayBroadcastDelivery(String str, long j) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceStopPackage(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceStopPackageByAdmin(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void forceStopPackageEvenWhenStopping(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean getAutoRemoveRecents(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int getBackgroundRestrictionExemptionReason(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int getBindingUidProcessState(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public List<String> getBugreportWhitelistedPackages() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Configuration getConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String[] getContentByTask(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ContentProviderHolder getContentProviderExternal(String str, int i, IBinder iBinder, String str2) throws RemoteException {
            return null;
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
        public List<String> getDelegatedShellPermissions() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public Configuration getGlobalConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(String str, int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IIntentSender getIntentSender(int i, String str, IBinder iBinder, String str2, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IIntentSender getIntentSenderWithFeature(int i, String str, String str2, IBinder iBinder, String str3, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int[] getIsolatedProcessList() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getLockTaskModeState() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public String getLongLiveApp() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveApps() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveProcesses() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<String> getLongLiveProcessesForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List getLongLiveTaskIdsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getMaxLongLiveApps() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int getMemoryTrimLevel() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void getMimeTypeFilterAsync(Uri uri, int i, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Bundle getOptionsForIntentSender(IIntentSender iIntentSender) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getPackageFromAppProcesses(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getPackageProcessState(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int getProcessLimit() throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public long[] getProcessPss(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<IntentFilter> getRegisteredIntentFilters(IIntentReceiver iIntentReceiver) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int[] getRunningUserIds() throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getSwitchingFromUserMessage(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getSwitchingToUserMessage(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Rect getTaskBounds(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public int[] getUidFrozenState(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public long getUidLastIdleElapsedTime(int i, String str) throws RemoteException {
            return 0L;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessCapabilities(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int getUidProcessState(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void grantUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void handleApplicationStrictModeViolation(IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void hang(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean hasServiceTimeLimitExceeded(ComponentName componentName, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void holdLock(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean isAppFreezerEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isAppFreezerSupported() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isBackgroundRestricted(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isFreezableUid(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isHeapDumpAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isInLockTaskMode() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isProcessFrozen(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isTopActivityImmersive() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUidActive(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUserAMonkey() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isUserRunning(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killAllBackgroundProcesses() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killApplication(String str, int i, int i2, String str2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killApplicationProcess(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killBackgroundProcesses(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killPackageDependents(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean killPids(int[] iArr, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean killProcessesBelowForeground(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void killProcessesWhenImperceptible(int[] iArr, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killUid(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void killUidForPermissionChange(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean launchBugReportHandlerApp() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void logFgsApiBegin(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void logFgsApiEnd(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void makePackageIdle(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean moveTaskToBack(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean moveTaskToBackWithBundle(int i, boolean z, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteAppRestrictionEnabled(String str, int i, int i2, boolean z, int i3, String str2, int i4, long j) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void notifyLockedProfile(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ParcelFileDescriptor openContentUri(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public IBinder peekService(Intent intent, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void performIdleMaintenance() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void preloadBoosterAppsFromIpm(List<String> list, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public ParceledListSlice queryIntentComponentsForIntentSender(IIntentSender iIntentSender, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public String[] queryRegisteredReceiverPackages(Intent intent, String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public IBinder refreshIntentCreatorToken(Intent intent) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerDedicatedCallback(RemoteCallback remoteCallback, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean registerForegroundServiceObserver(IForegroundServiceObserver iForegroundServiceObserver) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean registerIntentSenderCancelListenerEx(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public Intent registerReceiver(IApplicationThread iApplicationThread, String str, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str2, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public Intent registerReceiverWithFeature(IApplicationThread iApplicationThread, String str, String str2, String str3, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str4, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerStrictModeCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void registerUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public IBinder registerUidObserverForUids(IUidObserver iUidObserver, int i, int i2, String str, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean removeLongLiveApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void removeOverridePermissionState(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean removeTask(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void removeUidFromObserver(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void reportAbnormalUsage(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void reportStartInfoViewTimestamps(long j, long j2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReport(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReportWithDescription(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestBugReportWithExtraAttachments(List<Uri> list) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestFullBugReport() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReport() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestInteractiveBugReportWithDescription(String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestRemoteBugReport(long j) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestSystemServerHeapDump() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestTelephonyBugReport(String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void requestWifiBugReport(String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resetAbnormalList() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resetAppErrors() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void restart() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int restartUserInBackground(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean restrict(int i, int i2, boolean z, String str, int i3) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void resumeAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void revokeUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void sendIdleJobTrigger() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int sendIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3, Intent intent) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setActivityLocusContext(ComponentName componentName, LocusId locusId, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAgentApp(String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setAlwaysFinish(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDebugApp(String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDeterministicUidIdle(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setDumpHeapDebugLimit(String str, int i, long j, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean setFGSFilter(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setFocusedRootTask(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setHasTopUi(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean setLongLiveApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean setLongLiveTask(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setPersistentVrThread(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setProcessImportant(IBinder iBinder, int i, boolean z, String str) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setProcessLimit(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean setProcessMemoryTrimLevel(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean setProcessSlowdown(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void setProcessStateSummary(byte[] bArr) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setRenderThread(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setStopUserOnSwitch(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setTTSPkgInfo(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setTaskResizeable(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setThemeOverlayReady(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void setUserIsMonkey(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean shouldServiceTimeOut(ComponentName componentName, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean shutdown(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void signalPersistentProcesses(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityAsUserWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int startActivityWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean startBinderTracking() throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startInstrumentation(ComponentName componentName, String str, int i, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i2, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startProfileWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String str, boolean z, String str2, String str3, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void startSystemLockTaskMode(int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackground(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopAppForUser(String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void stopAppSwitches() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void stopDelegateShellPermissionIdentity() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean stopProfile(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserExceptCertainProfiles(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserWithCallback(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public int stopUserWithDelayedLocking(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityManager
        public void suppressResizeConfigChanges(boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean switchUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unbindFinished(IBinder iBinder, Intent intent) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unhandledBack() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public boolean unlockUser2(int i, IProgressListener iProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void unstableProviderDied(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean updateConfiguration(Configuration configuration) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public int[] updateFlingerFlag(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityManager
        public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean updateMccMncConfiguration(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfiguration(Configuration configuration) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void updatePersistentConfigurationWithAttribution(Configuration configuration, String str, String str2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityManager
        public void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void updateWindowVisible(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForBroadcastBarrier() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForBroadcastIdle() throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void waitForNetworkStateUpdate(long j) throws RemoteException {
        }
    }

    void addApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException;

    void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException;

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

    boolean bindBackupAgent(String str, int i, int i2, int i3, boolean z) throws RemoteException;

    int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, int i) throws RemoteException;

    int bindServiceInstance(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, String str3, int i) throws RemoteException;

    void bootAnimationComplete() throws RemoteException;

    @Deprecated
    int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException;

    int broadcastIntentWithFeature(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IIntentReceiver iIntentReceiver, int i, String str3, Bundle bundle, String[] strArr, String[] strArr2, String[] strArr3, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException;

    boolean canRestrict(int i, String str, int i2) throws RemoteException;

    void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException;

    void cancelTaskWindowTransition(int i) throws RemoteException;

    boolean checkAutoRunBlockedApp(String str, int i) throws RemoteException;

    int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3, int i4) throws RemoteException;

    int checkPermission(String str, int i, int i2) throws RemoteException;

    int checkPermissionForDevice(String str, int i, int i2, int i3) throws RemoteException;

    void checkProfileForADCP(int i, String str) throws RemoteException;

    int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException;

    int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException;

    void clearAllOverridePermissionStates(int i) throws RemoteException;

    boolean clearApplicationUserData(String str, boolean z, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException;

    boolean clearLongLiveTask(int i) throws RemoteException;

    void clearOverridePermissionStates(int i, int i2) throws RemoteException;

    boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException;

    void clearTTSPkgInfo() throws RemoteException;

    void closeSystemDialogs(String str) throws RemoteException;

    void closeSystemDialogsInDisplay(String str, int i) throws RemoteException;

    void crashApplicationWithType(int i, int i2, String str, int i3, String str2, boolean z, int i4) throws RemoteException;

    void crashApplicationWithTypeWithExtras(int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle) throws RemoteException;

    void dismissUserSwitchingDialog(int i) throws RemoteException;

    void doActiveLaunch(String str, boolean z, int i) throws RemoteException;

    boolean dumpHeap(String str, int i, boolean z, boolean z2, boolean z3, String str2, String str3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpHeapFinished(String str) throws RemoteException;

    boolean enableAppFreezer(boolean z) throws RemoteException;

    boolean enableFgsNotificationRateLimit(boolean z) throws RemoteException;

    void enterSafeMode() throws RemoteException;

    boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException;

    void finishAttachApplication(long j, long j2) throws RemoteException;

    void finishHeavyWeightApp() throws RemoteException;

    void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle) throws RemoteException;

    void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2) throws RemoteException;

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

    ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) throws RemoteException;

    ContentProviderHolder getContentProviderExternal(String str, int i, IBinder iBinder, String str2) throws RemoteException;

    UserInfo getCurrentUser() throws RemoteException;

    int getCurrentUserId() throws RemoteException;

    List<String> getDelegatedShellPermissions() throws RemoteException;

    int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException;

    ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException;

    int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException;

    Configuration getGlobalConfiguration() throws RemoteException;

    ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(String str, int i, int i2, int i3) throws RemoteException;

    ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(String str, int i, int i2) throws RemoteException;

    ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int i, int i2) throws RemoteException;

    Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    @Deprecated
    IIntentSender getIntentSender(int i, String str, IBinder iBinder, String str2, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException;

    IIntentSender getIntentSenderWithFeature(int i, String str, String str2, IBinder iBinder, String str3, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException;

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

    void getMimeTypeFilterAsync(Uri uri, int i, RemoteCallback remoteCallback) throws RemoteException;

    void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException;

    Bundle getOptionsForIntentSender(IIntentSender iIntentSender) throws RemoteException;

    String getPackageFromAppProcesses(int i) throws RemoteException;

    int getPackageProcessState(String str, String str2) throws RemoteException;

    int getProcessLimit() throws RemoteException;

    Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException;

    long[] getProcessPss(int[] iArr) throws RemoteException;

    List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException;

    ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException;

    List<IntentFilter> getRegisteredIntentFilters(IIntentReceiver iIntentReceiver) throws RemoteException;

    List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i) throws RemoteException;

    List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i) throws RemoteException;

    SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2) throws RemoteException;

    List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException;

    List<ApplicationInfo> getRunningExternalApplications() throws RemoteException;

    PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException;

    int[] getRunningUserIds() throws RemoteException;

    List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException;

    String getSwitchingFromUserMessage(int i) throws RemoteException;

    String getSwitchingToUserMessage(int i) throws RemoteException;

    String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException;

    Rect getTaskBounds(int i) throws RemoteException;

    int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException;

    int[] getUidFrozenState(int[] iArr) throws RemoteException;

    long getUidLastIdleElapsedTime(int i, String str) throws RemoteException;

    int getUidProcessCapabilities(int i, String str) throws RemoteException;

    int getUidProcessState(int i, String str) throws RemoteException;

    void grantUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException;

    void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException;

    void handleApplicationStrictModeViolation(IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException;

    boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws RemoteException;

    int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) throws RemoteException;

    void hang(IBinder iBinder, boolean z) throws RemoteException;

    boolean hasServiceTimeLimitExceeded(ComponentName componentName, IBinder iBinder) throws RemoteException;

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

    void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException;

    void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException;

    void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException;

    void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException;

    void noteAppRestrictionEnabled(String str, int i, int i2, boolean z, int i3, String str2, int i4, long j) throws RemoteException;

    void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2) throws RemoteException;

    void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException;

    void notifyLockedProfile(int i) throws RemoteException;

    ParcelFileDescriptor openContentUri(String str) throws RemoteException;

    IBinder peekService(Intent intent, String str, String str2) throws RemoteException;

    void performIdleMaintenance() throws RemoteException;

    void preloadBoosterAppsFromIpm(List<String> list, int i) throws RemoteException;

    boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2) throws RemoteException;

    void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException;

    void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException;

    ParceledListSlice queryIntentComponentsForIntentSender(IIntentSender iIntentSender, int i) throws RemoteException;

    String[] queryRegisteredReceiverPackages(Intent intent, String str, int i) throws RemoteException;

    boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException;

    IBinder refreshIntentCreatorToken(Intent intent) throws RemoteException;

    void registerDedicatedCallback(RemoteCallback remoteCallback, int i) throws RemoteException;

    boolean registerForegroundServiceObserver(IForegroundServiceObserver iForegroundServiceObserver) throws RemoteException;

    boolean registerIntentSenderCancelListenerEx(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    Intent registerReceiver(IApplicationThread iApplicationThread, String str, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str2, int i, int i2) throws RemoteException;

    Intent registerReceiverWithFeature(IApplicationThread iApplicationThread, String str, String str2, String str3, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str4, int i, int i2) throws RemoteException;

    void registerStrictModeCallback(IBinder iBinder) throws RemoteException;

    void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void registerUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException;

    void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str) throws RemoteException;

    IBinder registerUidObserverForUids(IUidObserver iUidObserver, int i, int i2, String str, int[] iArr) throws RemoteException;

    void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) throws RemoteException;

    void removeApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException;

    void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException;

    @Deprecated
    void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException;

    void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i) throws RemoteException;

    boolean removeLongLiveApp(String str) throws RemoteException;

    void removeOverridePermissionState(int i, int i2, String str) throws RemoteException;

    boolean removeTask(int i) throws RemoteException;

    void removeUidFromObserver(IBinder iBinder, String str, int i) throws RemoteException;

    void reportAbnormalUsage(int i, int i2) throws RemoteException;

    void reportStartInfoViewTimestamps(long j, long j2) throws RemoteException;

    void requestBugReport(int i) throws RemoteException;

    void requestBugReportWithDescription(String str, String str2, int i) throws RemoteException;

    void requestBugReportWithExtraAttachments(List<Uri> list) throws RemoteException;

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

    void revokeUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException;

    void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException;

    void sendIdleJobTrigger() throws RemoteException;

    int sendIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException;

    void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3, Intent intent) throws RemoteException;

    void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException;

    void setActivityLocusContext(ComponentName componentName, LocusId locusId, IBinder iBinder) throws RemoteException;

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

    void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException;

    void setStopUserOnSwitch(int i) throws RemoteException;

    void setTTSPkgInfo(int i) throws RemoteException;

    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setThemeOverlayReady(int i) throws RemoteException;

    void setUserIsMonkey(boolean z) throws RemoteException;

    boolean shouldServiceTimeOut(ComponentName componentName, IBinder iBinder) throws RemoteException;

    void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException;

    void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z) throws RemoteException;

    boolean shutdown(int i) throws RemoteException;

    void signalPersistentProcesses(int i) throws RemoteException;

    @Deprecated
    int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException;

    @Deprecated
    int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    int startActivityAsUserWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException;

    int startActivityFromRecents(int i, Bundle bundle) throws RemoteException;

    int startActivityWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException;

    boolean startBinderTracking() throws RemoteException;

    void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException;

    void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException;

    boolean startInstrumentation(ComponentName componentName, String str, int i, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i2, String str2) throws RemoteException;

    boolean startProfile(int i) throws RemoteException;

    boolean startProfileWithListener(int i, IProgressListener iProgressListener) throws RemoteException;

    ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String str, boolean z, String str2, String str3, int i) throws RemoteException;

    void startSystemLockTaskMode(int i) throws RemoteException;

    boolean startUserInBackground(int i) throws RemoteException;

    boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, IProgressListener iProgressListener) throws RemoteException;

    boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException;

    boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException;

    void stopAppForUser(String str, int i) throws RemoteException;

    void stopAppSwitches() throws RemoteException;

    boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void stopDelegateShellPermissionIdentity() throws RemoteException;

    boolean stopProfile(int i) throws RemoteException;

    int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i) throws RemoteException;

    boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i) throws RemoteException;

    int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException;

    int stopUserExceptCertainProfiles(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException;

    int stopUserWithCallback(int i, IStopUserCallback iStopUserCallback) throws RemoteException;

    int stopUserWithDelayedLocking(int i, IStopUserCallback iStopUserCallback) throws RemoteException;

    void suppressResizeConfigChanges(boolean z) throws RemoteException;

    boolean switchUser(int i) throws RemoteException;

    void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException;

    void unbindFinished(IBinder iBinder, Intent intent) throws RemoteException;

    boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException;

    void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i) throws RemoteException;

    void unhandledBack() throws RemoteException;

    @Deprecated
    boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener) throws RemoteException;

    boolean unlockUser2(int i, IProgressListener iProgressListener) throws RemoteException;

    void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException;

    void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException;

    void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException;

    void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException;

    void unregisterUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException;

    void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException;

    void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException;

    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    boolean updateConfiguration(Configuration configuration) throws RemoteException;

    int[] updateFlingerFlag(int i, int i2, String str) throws RemoteException;

    void updateLockTaskPackages(int i, String[] strArr) throws RemoteException;

    boolean updateMccMncConfiguration(String str, String str2) throws RemoteException;

    void updatePersistentConfiguration(Configuration configuration) throws RemoteException;

    void updatePersistentConfigurationWithAttribution(Configuration configuration, String str, String str2) throws RemoteException;

    boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException;

    void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2) throws RemoteException;

    void updateWindowVisible(int i, int i2, int i3) throws RemoteException;

    void waitForBroadcastBarrier() throws RemoteException;

    void waitForBroadcastIdle() throws RemoteException;

    void waitForNetworkStateUpdate(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityManager {
        public static final String DESCRIPTOR = "android.app.IActivityManager";
        static final int TRANSACTION_addApplicationStartInfoCompleteListener = 229;
        static final int TRANSACTION_addInstrumentationResults = 46;
        static final int TRANSACTION_addLongLiveApp = 265;
        static final int TRANSACTION_addOverridePermissionState = 285;
        static final int TRANSACTION_addPackageDependency = 99;
        static final int TRANSACTION_addStartInfoTimestamp = 231;
        static final int TRANSACTION_addUidToObserver = 5;
        static final int TRANSACTION_appNotResponding = 226;
        static final int TRANSACTION_appNotRespondingViaProvider = 174;
        static final int TRANSACTION_attachApplication = 26;
        static final int TRANSACTION_backgroundAllowlistUid = 219;
        static final int TRANSACTION_backupAgentCreated = 96;
        static final int TRANSACTION_bindBackupAgent = 95;
        static final int TRANSACTION_bindService = 37;
        static final int TRANSACTION_bindServiceInstance = 38;
        static final int TRANSACTION_bootAnimationComplete = 183;
        static final int TRANSACTION_broadcastIntent = 22;
        static final int TRANSACTION_broadcastIntentWithFeature = 23;
        static final int TRANSACTION_canRestrict = 296;
        static final int TRANSACTION_cancelIntentSender = 66;
        static final int TRANSACTION_cancelTaskWindowTransition = 215;
        static final int TRANSACTION_checkAutoRunBlockedApp = 292;
        static final int TRANSACTION_checkContentUriPermissionFull = 55;
        static final int TRANSACTION_checkPermission = 9;
        static final int TRANSACTION_checkPermissionForDevice = 281;
        static final int TRANSACTION_checkProfileForADCP = 291;
        static final int TRANSACTION_checkUriPermission = 54;
        static final int TRANSACTION_checkUriPermissions = 56;
        static final int TRANSACTION_clearAllOverridePermissionStates = 288;
        static final int TRANSACTION_clearApplicationUserData = 81;
        static final int TRANSACTION_clearLongLiveTask = 269;
        static final int TRANSACTION_clearOverridePermissionStates = 287;
        static final int TRANSACTION_clearRestrictionInfo = 302;
        static final int TRANSACTION_clearTTSPkgInfo = 304;
        static final int TRANSACTION_closeSystemDialogs = 101;
        static final int TRANSACTION_closeSystemDialogsInDisplay = 102;
        static final int TRANSACTION_crashApplicationWithType = 113;
        static final int TRANSACTION_crashApplicationWithTypeWithExtras = 114;
        static final int TRANSACTION_dismissUserSwitchingDialog = 279;
        static final int TRANSACTION_doActiveLaunch = 227;
        static final int TRANSACTION_dumpHeap = 116;
        static final int TRANSACTION_dumpHeapFinished = 193;
        static final int TRANSACTION_enableAppFreezer = 241;
        static final int TRANSACTION_enableFgsNotificationRateLimit = 242;
        static final int TRANSACTION_enterSafeMode = 70;
        static final int TRANSACTION_finishActivity = 17;
        static final int TRANSACTION_finishAttachApplication = 27;
        static final int TRANSACTION_finishHeavyWeightApp = 109;
        static final int TRANSACTION_finishInstrumentation = 47;
        static final int TRANSACTION_finishReceiver = 25;
        static final int TRANSACTION_forceDelayBroadcastDelivery = 250;
        static final int TRANSACTION_forceStopPackage = 84;
        static final int TRANSACTION_forceStopPackageByAdmin = 86;
        static final int TRANSACTION_forceStopPackageEvenWhenStopping = 85;
        static final int TRANSACTION_frozenBinderTransactionDetected = 282;
        static final int TRANSACTION_getAllRestrictedList = 299;
        static final int TRANSACTION_getAllRootTaskInfos = 168;
        static final int TRANSACTION_getAutoRemoveRecents = 273;
        static final int TRANSACTION_getBackgroundRestrictionExemptionReason = 252;
        static final int TRANSACTION_getBindingUidProcessState = 283;
        static final int TRANSACTION_getBugreportWhitelistedPackages = 162;
        static final int TRANSACTION_getConfiguration = 48;
        static final int TRANSACTION_getContentByTask = 263;
        static final int TRANSACTION_getContentProvider = 31;
        static final int TRANSACTION_getContentProviderExternal = 132;
        static final int TRANSACTION_getCurrentUser = 137;
        static final int TRANSACTION_getCurrentUserId = 138;
        static final int TRANSACTION_getDelegatedShellPermissions = 223;
        static final int TRANSACTION_getDisplayIdsForStartingVisibleBackgroundUsers = 257;
        static final int TRANSACTION_getFocusedRootTaskInfo = 171;
        static final int TRANSACTION_getForegroundServiceType = 77;
        static final int TRANSACTION_getGlobalConfiguration = 280;
        static final int TRANSACTION_getHistoricalProcessExitReasons = 233;
        static final int TRANSACTION_getHistoricalProcessStartReasons = 228;
        static final int TRANSACTION_getInfoForIntentSender = 67;
        static final int TRANSACTION_getInstalledPackageListFromMARs = 305;
        static final int TRANSACTION_getIntentForIntentSender = 163;
        static final int TRANSACTION_getIntentSender = 64;
        static final int TRANSACTION_getIntentSenderWithFeature = 65;
        static final int TRANSACTION_getIsolatedProcessList = 313;
        static final int TRANSACTION_getLaunchedFromPackage = 164;
        static final int TRANSACTION_getLaunchedFromUid = 139;
        static final int TRANSACTION_getLifeMonitor = 224;
        static final int TRANSACTION_getLockTaskModeState = 191;
        static final int TRANSACTION_getLongLiveApp = 276;
        static final int TRANSACTION_getLongLiveApps = 264;
        static final int TRANSACTION_getLongLiveProcesses = 270;
        static final int TRANSACTION_getLongLiveProcessesForUser = 271;
        static final int TRANSACTION_getLongLiveTaskIdsForUser = 272;
        static final int TRANSACTION_getMaxLongLiveApps = 267;
        static final int TRANSACTION_getMemoryInfo = 79;
        static final int TRANSACTION_getMemoryTrimLevel = 206;
        static final int TRANSACTION_getMimeTypeFilterAsync = 115;
        static final int TRANSACTION_getMyMemoryState = 135;
        static final int TRANSACTION_getOptionsForIntentSender = 314;
        static final int TRANSACTION_getPackageFromAppProcesses = 306;
        static final int TRANSACTION_getPackageProcessState = 197;
        static final int TRANSACTION_getProcessLimit = 53;
        static final int TRANSACTION_getProcessMemoryInfo = 103;
        static final int TRANSACTION_getProcessPss = 129;
        static final int TRANSACTION_getProcessesInErrorState = 80;
        static final int TRANSACTION_getRecentTasks = 62;
        static final int TRANSACTION_getRegisteredIntentFilters = 21;
        static final int TRANSACTION_getRestrictableList = 298;
        static final int TRANSACTION_getRestrictedList = 300;
        static final int TRANSACTION_getRestrictionInfo = 295;
        static final int TRANSACTION_getRunningAppProcesses = 89;
        static final int TRANSACTION_getRunningExternalApplications = 108;
        static final int TRANSACTION_getRunningServiceControlPanel = 34;
        static final int TRANSACTION_getRunningUserIds = 150;
        static final int TRANSACTION_getServices = 88;
        static final int TRANSACTION_getSwitchingFromUserMessage = 120;
        static final int TRANSACTION_getSwitchingToUserMessage = 121;
        static final int TRANSACTION_getTagForIntentSender = 177;
        static final int TRANSACTION_getTaskBounds = 175;
        static final int TRANSACTION_getTaskForActivity = 30;
        static final int TRANSACTION_getTasks = 28;
        static final int TRANSACTION_getUidFrozenState = 262;
        static final int TRANSACTION_getUidLastIdleElapsedTime = 284;
        static final int TRANSACTION_getUidProcessCapabilities = 247;
        static final int TRANSACTION_getUidProcessState = 8;
        static final int TRANSACTION_grantUriPermission = 57;
        static final int TRANSACTION_handleApplicationCrash = 13;
        static final int TRANSACTION_handleApplicationStrictModeViolation = 110;
        static final int TRANSACTION_handleApplicationWtf = 105;
        static final int TRANSACTION_handleIncomingUser = 98;
        static final int TRANSACTION_hang = 167;
        static final int TRANSACTION_hasServiceTimeLimitExceeded = 259;
        static final int TRANSACTION_holdLock = 243;
        static final int TRANSACTION_isAppFreezerEnabled = 238;
        static final int TRANSACTION_isAppFreezerSupported = 237;
        static final int TRANSACTION_isBackgroundRestricted = 212;
        static final int TRANSACTION_isFreezableUid = 311;
        static final int TRANSACTION_isHeapDumpAllowed = 308;
        static final int TRANSACTION_isInLockTaskMode = 179;
        static final int TRANSACTION_isIntentSenderAnActivity = 141;
        static final int TRANSACTION_isIntentSenderTargetedToPackage = 126;
        static final int TRANSACTION_isProcessFrozen = 251;
        static final int TRANSACTION_isTopActivityImmersive = 112;
        static final int TRANSACTION_isTopOfTask = 182;
        static final int TRANSACTION_isUidActive = 7;
        static final int TRANSACTION_isUserAMonkey = 107;
        static final int TRANSACTION_isUserRunning = 117;
        static final int TRANSACTION_isVrModePackageEnabled = 207;
        static final int TRANSACTION_killAllBackgroundProcesses = 131;
        static final int TRANSACTION_killApplication = 100;
        static final int TRANSACTION_killApplicationProcess = 104;
        static final int TRANSACTION_killBackgroundProcesses = 106;
        static final int TRANSACTION_killPackageDependents = 203;
        static final int TRANSACTION_killPids = 87;
        static final int TRANSACTION_killProcessesBelowForeground = 136;
        static final int TRANSACTION_killProcessesWhenImperceptible = 234;
        static final int TRANSACTION_killUid = 165;
        static final int TRANSACTION_killUidForPermissionChange = 239;
        static final int TRANSACTION_launchBugReportHandlerApp = 161;
        static final int TRANSACTION_logFgsApiBegin = 10;
        static final int TRANSACTION_logFgsApiEnd = 11;
        static final int TRANSACTION_logFgsApiStateChanged = 12;
        static final int TRANSACTION_makePackageIdle = 204;
        static final int TRANSACTION_moveActivityTaskToBack = 78;
        static final int TRANSACTION_moveTaskToBack = 277;
        static final int TRANSACTION_moveTaskToBackWithBundle = 278;
        static final int TRANSACTION_moveTaskToFront = 29;
        static final int TRANSACTION_moveTaskToRootTask = 169;
        static final int TRANSACTION_noteAlarmFinish = 196;
        static final int TRANSACTION_noteAlarmStart = 195;
        static final int TRANSACTION_noteAppRestrictionEnabled = 289;
        static final int TRANSACTION_noteWakeupAlarm = 71;
        static final int TRANSACTION_notifyCleartextNetwork = 188;
        static final int TRANSACTION_notifyLockedProfile = 208;
        static final int TRANSACTION_openContentUri = 1;
        static final int TRANSACTION_peekService = 90;
        static final int TRANSACTION_performIdleMaintenance = 173;
        static final int TRANSACTION_preloadBoosterAppsFromIpm = 293;
        static final int TRANSACTION_profileControl = 91;
        static final int TRANSACTION_publishContentProviders = 32;
        static final int TRANSACTION_publishService = 41;
        static final int TRANSACTION_queryIntentComponentsForIntentSender = 246;
        static final int TRANSACTION_queryRegisteredReceiverPackages = 253;
        static final int TRANSACTION_refContentProvider = 33;
        static final int TRANSACTION_refreshIntentCreatorToken = 290;
        static final int TRANSACTION_registerDedicatedCallback = 274;
        static final int TRANSACTION_registerForegroundServiceObserver = 83;
        static final int TRANSACTION_registerIntentSenderCancelListenerEx = 68;
        static final int TRANSACTION_registerProcessObserver = 124;
        static final int TRANSACTION_registerReceiver = 18;
        static final int TRANSACTION_registerReceiverWithFeature = 19;
        static final int TRANSACTION_registerStrictModeCallback = 111;
        static final int TRANSACTION_registerTaskStackListener = 186;
        static final int TRANSACTION_registerUidFrozenStateChangedCallback = 260;
        static final int TRANSACTION_registerUidObserver = 2;
        static final int TRANSACTION_registerUidObserverForUids = 4;
        static final int TRANSACTION_registerUserSwitchObserver = 148;
        static final int TRANSACTION_removeApplicationStartInfoCompleteListener = 230;
        static final int TRANSACTION_removeContentProvider = 72;
        static final int TRANSACTION_removeContentProviderExternal = 133;
        static final int TRANSACTION_removeContentProviderExternalAsUser = 134;
        static final int TRANSACTION_removeLongLiveApp = 266;
        static final int TRANSACTION_removeOverridePermissionState = 286;
        static final int TRANSACTION_removeTask = 123;
        static final int TRANSACTION_removeUidFromObserver = 6;
        static final int TRANSACTION_reportAbnormalUsage = 307;
        static final int TRANSACTION_reportStartInfoViewTimestamps = 232;
        static final int TRANSACTION_requestBugReport = 152;
        static final int TRANSACTION_requestBugReportWithDescription = 153;
        static final int TRANSACTION_requestBugReportWithExtraAttachments = 158;
        static final int TRANSACTION_requestFullBugReport = 159;
        static final int TRANSACTION_requestInteractiveBugReport = 157;
        static final int TRANSACTION_requestInteractiveBugReportWithDescription = 156;
        static final int TRANSACTION_requestRemoteBugReport = 160;
        static final int TRANSACTION_requestSystemServerHeapDump = 151;
        static final int TRANSACTION_requestTelephonyBugReport = 154;
        static final int TRANSACTION_requestWifiBugReport = 155;
        static final int TRANSACTION_resetAbnormalList = 310;
        static final int TRANSACTION_resetAppErrors = 240;
        static final int TRANSACTION_resizeTask = 190;
        static final int TRANSACTION_restart = 172;
        static final int TRANSACTION_restartUserInBackground = 256;
        static final int TRANSACTION_restrict = 297;
        static final int TRANSACTION_resumeAppSwitches = 94;
        static final int TRANSACTION_revokeUriPermission = 58;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 216;
        static final int TRANSACTION_sendIdleJobTrigger = 210;
        static final int TRANSACTION_sendIntentSender = 211;
        static final int TRANSACTION_serviceDoneExecuting = 63;
        static final int TRANSACTION_setActivityController = 59;
        static final int TRANSACTION_setActivityLocusContext = 235;
        static final int TRANSACTION_setAgentApp = 43;
        static final int TRANSACTION_setAlwaysFinish = 44;
        static final int TRANSACTION_setDebugApp = 42;
        static final int TRANSACTION_setDeterministicUidIdle = 205;
        static final int TRANSACTION_setDumpHeapDebugLimit = 192;
        static final int TRANSACTION_setFGSFilter = 309;
        static final int TRANSACTION_setFocusedRootTask = 170;
        static final int TRANSACTION_setHasTopUi = 214;
        static final int TRANSACTION_setLongLiveApp = 275;
        static final int TRANSACTION_setLongLiveTask = 268;
        static final int TRANSACTION_setPackageScreenCompatMode = 118;
        static final int TRANSACTION_setPersistentVrThread = 217;
        static final int TRANSACTION_setProcessImportant = 75;
        static final int TRANSACTION_setProcessLimit = 52;
        static final int TRANSACTION_setProcessMemoryTrimLevel = 176;
        static final int TRANSACTION_setProcessSlowdown = 312;
        static final int TRANSACTION_setProcessStateSummary = 236;
        static final int TRANSACTION_setRenderThread = 213;
        static final int TRANSACTION_setRequestedOrientation = 73;
        static final int TRANSACTION_setServiceForeground = 76;
        static final int TRANSACTION_setStopUserOnSwitch = 122;
        static final int TRANSACTION_setTTSPkgInfo = 303;
        static final int TRANSACTION_setTaskResizeable = 189;
        static final int TRANSACTION_setThemeOverlayReady = 185;
        static final int TRANSACTION_setUserIsMonkey = 166;
        static final int TRANSACTION_shouldServiceTimeOut = 258;
        static final int TRANSACTION_showBootMessage = 130;
        static final int TRANSACTION_showWaitingForDebugger = 60;
        static final int TRANSACTION_shutdown = 92;
        static final int TRANSACTION_signalPersistentProcesses = 61;
        static final int TRANSACTION_startActivity = 14;
        static final int TRANSACTION_startActivityAsUser = 142;
        static final int TRANSACTION_startActivityAsUserWithFeature = 143;
        static final int TRANSACTION_startActivityFromRecents = 180;
        static final int TRANSACTION_startActivityWithFeature = 15;
        static final int TRANSACTION_startBinderTracking = 198;
        static final int TRANSACTION_startConfirmDeviceCredentialIntent = 209;
        static final int TRANSACTION_startDelegateShellPermissionIdentity = 221;
        static final int TRANSACTION_startInstrumentation = 45;
        static final int TRANSACTION_startProfile = 244;
        static final int TRANSACTION_startProfileWithListener = 255;
        static final int TRANSACTION_startService = 35;
        static final int TRANSACTION_startSystemLockTaskMode = 181;
        static final int TRANSACTION_startUserInBackground = 178;
        static final int TRANSACTION_startUserInBackgroundVisibleOnDisplay = 254;
        static final int TRANSACTION_startUserInBackgroundWithListener = 220;
        static final int TRANSACTION_startUserInForegroundWithListener = 225;
        static final int TRANSACTION_stopAppForUser = 82;
        static final int TRANSACTION_stopAppSwitches = 93;
        static final int TRANSACTION_stopBinderTrackingAndDump = 199;
        static final int TRANSACTION_stopDelegateShellPermissionIdentity = 222;
        static final int TRANSACTION_stopProfile = 245;
        static final int TRANSACTION_stopService = 36;
        static final int TRANSACTION_stopServiceToken = 51;
        static final int TRANSACTION_stopUser = 144;
        static final int TRANSACTION_stopUserExceptCertainProfiles = 146;
        static final int TRANSACTION_stopUserWithCallback = 145;
        static final int TRANSACTION_stopUserWithDelayedLocking = 147;
        static final int TRANSACTION_suppressResizeConfigChanges = 200;
        static final int TRANSACTION_switchUser = 119;
        static final int TRANSACTION_unbindBackupAgent = 97;
        static final int TRANSACTION_unbindFinished = 74;
        static final int TRANSACTION_unbindService = 40;
        static final int TRANSACTION_unbroadcastIntent = 24;
        static final int TRANSACTION_unhandledBack = 16;
        static final int TRANSACTION_unlockUser = 201;
        static final int TRANSACTION_unlockUser2 = 202;
        static final int TRANSACTION_unregisterIntentSenderCancelListener = 69;
        static final int TRANSACTION_unregisterProcessObserver = 125;
        static final int TRANSACTION_unregisterReceiver = 20;
        static final int TRANSACTION_unregisterTaskStackListener = 187;
        static final int TRANSACTION_unregisterUidFrozenStateChangedCallback = 261;
        static final int TRANSACTION_unregisterUidObserver = 3;
        static final int TRANSACTION_unregisterUserSwitchObserver = 149;
        static final int TRANSACTION_unstableProviderDied = 140;
        static final int TRANSACTION_updateConfiguration = 49;
        static final int TRANSACTION_updateFlingerFlag = 294;
        static final int TRANSACTION_updateLockTaskPackages = 194;
        static final int TRANSACTION_updateMccMncConfiguration = 50;
        static final int TRANSACTION_updatePersistentConfiguration = 127;
        static final int TRANSACTION_updatePersistentConfigurationWithAttribution = 128;
        static final int TRANSACTION_updateRestrictionInfo = 301;
        static final int TRANSACTION_updateServiceGroup = 39;
        static final int TRANSACTION_updateWindowVisible = 184;
        static final int TRANSACTION_waitForBroadcastBarrier = 249;
        static final int TRANSACTION_waitForBroadcastIdle = 248;
        static final int TRANSACTION_waitForNetworkStateUpdate = 218;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 313;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IActivityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityManager)) {
                return (IActivityManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "getRegisteredIntentFilters";
                case 22:
                    return "broadcastIntent";
                case 23:
                    return "broadcastIntentWithFeature";
                case 24:
                    return "unbroadcastIntent";
                case 25:
                    return "finishReceiver";
                case 26:
                    return "attachApplication";
                case 27:
                    return "finishAttachApplication";
                case 28:
                    return "getTasks";
                case 29:
                    return "moveTaskToFront";
                case 30:
                    return "getTaskForActivity";
                case 31:
                    return "getContentProvider";
                case 32:
                    return "publishContentProviders";
                case 33:
                    return "refContentProvider";
                case 34:
                    return "getRunningServiceControlPanel";
                case 35:
                    return "startService";
                case 36:
                    return "stopService";
                case 37:
                    return "bindService";
                case 38:
                    return "bindServiceInstance";
                case 39:
                    return "updateServiceGroup";
                case 40:
                    return "unbindService";
                case 41:
                    return "publishService";
                case 42:
                    return "setDebugApp";
                case 43:
                    return "setAgentApp";
                case 44:
                    return "setAlwaysFinish";
                case 45:
                    return "startInstrumentation";
                case 46:
                    return "addInstrumentationResults";
                case 47:
                    return "finishInstrumentation";
                case 48:
                    return "getConfiguration";
                case 49:
                    return "updateConfiguration";
                case 50:
                    return "updateMccMncConfiguration";
                case 51:
                    return "stopServiceToken";
                case 52:
                    return "setProcessLimit";
                case 53:
                    return "getProcessLimit";
                case 54:
                    return "checkUriPermission";
                case 55:
                    return "checkContentUriPermissionFull";
                case 56:
                    return "checkUriPermissions";
                case 57:
                    return "grantUriPermission";
                case 58:
                    return "revokeUriPermission";
                case 59:
                    return "setActivityController";
                case 60:
                    return "showWaitingForDebugger";
                case 61:
                    return "signalPersistentProcesses";
                case 62:
                    return "getRecentTasks";
                case 63:
                    return "serviceDoneExecuting";
                case 64:
                    return "getIntentSender";
                case 65:
                    return "getIntentSenderWithFeature";
                case 66:
                    return "cancelIntentSender";
                case 67:
                    return "getInfoForIntentSender";
                case 68:
                    return "registerIntentSenderCancelListenerEx";
                case 69:
                    return "unregisterIntentSenderCancelListener";
                case 70:
                    return "enterSafeMode";
                case 71:
                    return "noteWakeupAlarm";
                case 72:
                    return "removeContentProvider";
                case 73:
                    return "setRequestedOrientation";
                case 74:
                    return "unbindFinished";
                case 75:
                    return "setProcessImportant";
                case 76:
                    return "setServiceForeground";
                case 77:
                    return "getForegroundServiceType";
                case 78:
                    return "moveActivityTaskToBack";
                case 79:
                    return "getMemoryInfo";
                case 80:
                    return "getProcessesInErrorState";
                case 81:
                    return "clearApplicationUserData";
                case 82:
                    return "stopAppForUser";
                case 83:
                    return "registerForegroundServiceObserver";
                case 84:
                    return "forceStopPackage";
                case 85:
                    return "forceStopPackageEvenWhenStopping";
                case 86:
                    return "forceStopPackageByAdmin";
                case 87:
                    return "killPids";
                case 88:
                    return "getServices";
                case 89:
                    return "getRunningAppProcesses";
                case 90:
                    return "peekService";
                case 91:
                    return "profileControl";
                case 92:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 93:
                    return "stopAppSwitches";
                case 94:
                    return "resumeAppSwitches";
                case 95:
                    return "bindBackupAgent";
                case 96:
                    return "backupAgentCreated";
                case 97:
                    return "unbindBackupAgent";
                case 98:
                    return "handleIncomingUser";
                case 99:
                    return "addPackageDependency";
                case 100:
                    return "killApplication";
                case 101:
                    return "closeSystemDialogs";
                case 102:
                    return "closeSystemDialogsInDisplay";
                case 103:
                    return "getProcessMemoryInfo";
                case 104:
                    return "killApplicationProcess";
                case 105:
                    return "handleApplicationWtf";
                case 106:
                    return "killBackgroundProcesses";
                case 107:
                    return "isUserAMonkey";
                case 108:
                    return "getRunningExternalApplications";
                case 109:
                    return "finishHeavyWeightApp";
                case 110:
                    return "handleApplicationStrictModeViolation";
                case 111:
                    return "registerStrictModeCallback";
                case 112:
                    return "isTopActivityImmersive";
                case 113:
                    return "crashApplicationWithType";
                case 114:
                    return "crashApplicationWithTypeWithExtras";
                case 115:
                    return "getMimeTypeFilterAsync";
                case 116:
                    return "dumpHeap";
                case 117:
                    return "isUserRunning";
                case 118:
                    return "setPackageScreenCompatMode";
                case 119:
                    return "switchUser";
                case 120:
                    return "getSwitchingFromUserMessage";
                case 121:
                    return "getSwitchingToUserMessage";
                case 122:
                    return "setStopUserOnSwitch";
                case 123:
                    return "removeTask";
                case 124:
                    return "registerProcessObserver";
                case 125:
                    return "unregisterProcessObserver";
                case 126:
                    return "isIntentSenderTargetedToPackage";
                case 127:
                    return "updatePersistentConfiguration";
                case 128:
                    return "updatePersistentConfigurationWithAttribution";
                case 129:
                    return "getProcessPss";
                case 130:
                    return "showBootMessage";
                case 131:
                    return "killAllBackgroundProcesses";
                case 132:
                    return "getContentProviderExternal";
                case 133:
                    return "removeContentProviderExternal";
                case 134:
                    return "removeContentProviderExternalAsUser";
                case 135:
                    return "getMyMemoryState";
                case 136:
                    return "killProcessesBelowForeground";
                case 137:
                    return "getCurrentUser";
                case 138:
                    return "getCurrentUserId";
                case 139:
                    return "getLaunchedFromUid";
                case 140:
                    return "unstableProviderDied";
                case 141:
                    return "isIntentSenderAnActivity";
                case 142:
                    return "startActivityAsUser";
                case 143:
                    return "startActivityAsUserWithFeature";
                case 144:
                    return "stopUser";
                case 145:
                    return "stopUserWithCallback";
                case 146:
                    return "stopUserExceptCertainProfiles";
                case 147:
                    return "stopUserWithDelayedLocking";
                case 148:
                    return "registerUserSwitchObserver";
                case 149:
                    return "unregisterUserSwitchObserver";
                case 150:
                    return "getRunningUserIds";
                case 151:
                    return "requestSystemServerHeapDump";
                case 152:
                    return "requestBugReport";
                case 153:
                    return "requestBugReportWithDescription";
                case 154:
                    return "requestTelephonyBugReport";
                case 155:
                    return "requestWifiBugReport";
                case 156:
                    return "requestInteractiveBugReportWithDescription";
                case 157:
                    return "requestInteractiveBugReport";
                case 158:
                    return "requestBugReportWithExtraAttachments";
                case 159:
                    return "requestFullBugReport";
                case 160:
                    return "requestRemoteBugReport";
                case 161:
                    return "launchBugReportHandlerApp";
                case 162:
                    return "getBugreportWhitelistedPackages";
                case 163:
                    return "getIntentForIntentSender";
                case 164:
                    return "getLaunchedFromPackage";
                case 165:
                    return "killUid";
                case 166:
                    return "setUserIsMonkey";
                case 167:
                    return "hang";
                case 168:
                    return "getAllRootTaskInfos";
                case 169:
                    return "moveTaskToRootTask";
                case 170:
                    return "setFocusedRootTask";
                case 171:
                    return "getFocusedRootTaskInfo";
                case 172:
                    return DefaultActionNames.ACTION_RESTART;
                case 173:
                    return "performIdleMaintenance";
                case 174:
                    return "appNotRespondingViaProvider";
                case 175:
                    return "getTaskBounds";
                case 176:
                    return "setProcessMemoryTrimLevel";
                case 177:
                    return "getTagForIntentSender";
                case 178:
                    return "startUserInBackground";
                case 179:
                    return "isInLockTaskMode";
                case 180:
                    return "startActivityFromRecents";
                case 181:
                    return "startSystemLockTaskMode";
                case 182:
                    return "isTopOfTask";
                case 183:
                    return "bootAnimationComplete";
                case 184:
                    return "updateWindowVisible";
                case 185:
                    return "setThemeOverlayReady";
                case 186:
                    return "registerTaskStackListener";
                case 187:
                    return "unregisterTaskStackListener";
                case 188:
                    return "notifyCleartextNetwork";
                case 189:
                    return "setTaskResizeable";
                case 190:
                    return "resizeTask";
                case 191:
                    return "getLockTaskModeState";
                case 192:
                    return "setDumpHeapDebugLimit";
                case 193:
                    return "dumpHeapFinished";
                case 194:
                    return "updateLockTaskPackages";
                case 195:
                    return "noteAlarmStart";
                case 196:
                    return "noteAlarmFinish";
                case 197:
                    return "getPackageProcessState";
                case 198:
                    return "startBinderTracking";
                case 199:
                    return "stopBinderTrackingAndDump";
                case 200:
                    return "suppressResizeConfigChanges";
                case 201:
                    return "unlockUser";
                case 202:
                    return "unlockUser2";
                case 203:
                    return "killPackageDependents";
                case 204:
                    return "makePackageIdle";
                case 205:
                    return "setDeterministicUidIdle";
                case 206:
                    return "getMemoryTrimLevel";
                case 207:
                    return "isVrModePackageEnabled";
                case 208:
                    return "notifyLockedProfile";
                case 209:
                    return "startConfirmDeviceCredentialIntent";
                case 210:
                    return "sendIdleJobTrigger";
                case 211:
                    return "sendIntentSender";
                case 212:
                    return "isBackgroundRestricted";
                case 213:
                    return "setRenderThread";
                case 214:
                    return "setHasTopUi";
                case 215:
                    return "cancelTaskWindowTransition";
                case 216:
                    return "scheduleApplicationInfoChanged";
                case 217:
                    return "setPersistentVrThread";
                case 218:
                    return "waitForNetworkStateUpdate";
                case 219:
                    return "backgroundAllowlistUid";
                case 220:
                    return "startUserInBackgroundWithListener";
                case 221:
                    return "startDelegateShellPermissionIdentity";
                case 222:
                    return "stopDelegateShellPermissionIdentity";
                case 223:
                    return "getDelegatedShellPermissions";
                case 224:
                    return "getLifeMonitor";
                case 225:
                    return "startUserInForegroundWithListener";
                case 226:
                    return "appNotResponding";
                case 227:
                    return "doActiveLaunch";
                case 228:
                    return "getHistoricalProcessStartReasons";
                case 229:
                    return "addApplicationStartInfoCompleteListener";
                case 230:
                    return "removeApplicationStartInfoCompleteListener";
                case 231:
                    return "addStartInfoTimestamp";
                case 232:
                    return "reportStartInfoViewTimestamps";
                case 233:
                    return "getHistoricalProcessExitReasons";
                case 234:
                    return "killProcessesWhenImperceptible";
                case 235:
                    return "setActivityLocusContext";
                case 236:
                    return "setProcessStateSummary";
                case 237:
                    return "isAppFreezerSupported";
                case 238:
                    return "isAppFreezerEnabled";
                case 239:
                    return "killUidForPermissionChange";
                case 240:
                    return "resetAppErrors";
                case 241:
                    return "enableAppFreezer";
                case 242:
                    return "enableFgsNotificationRateLimit";
                case 243:
                    return "holdLock";
                case 244:
                    return "startProfile";
                case 245:
                    return "stopProfile";
                case 246:
                    return "queryIntentComponentsForIntentSender";
                case 247:
                    return "getUidProcessCapabilities";
                case 248:
                    return "waitForBroadcastIdle";
                case 249:
                    return "waitForBroadcastBarrier";
                case 250:
                    return "forceDelayBroadcastDelivery";
                case 251:
                    return "isProcessFrozen";
                case 252:
                    return "getBackgroundRestrictionExemptionReason";
                case 253:
                    return "queryRegisteredReceiverPackages";
                case 254:
                    return "startUserInBackgroundVisibleOnDisplay";
                case 255:
                    return "startProfileWithListener";
                case 256:
                    return "restartUserInBackground";
                case 257:
                    return "getDisplayIdsForStartingVisibleBackgroundUsers";
                case 258:
                    return "shouldServiceTimeOut";
                case 259:
                    return "hasServiceTimeLimitExceeded";
                case 260:
                    return "registerUidFrozenStateChangedCallback";
                case 261:
                    return "unregisterUidFrozenStateChangedCallback";
                case 262:
                    return "getUidFrozenState";
                case 263:
                    return "getContentByTask";
                case 264:
                    return "getLongLiveApps";
                case 265:
                    return "addLongLiveApp";
                case 266:
                    return "removeLongLiveApp";
                case 267:
                    return "getMaxLongLiveApps";
                case 268:
                    return "setLongLiveTask";
                case 269:
                    return "clearLongLiveTask";
                case 270:
                    return "getLongLiveProcesses";
                case 271:
                    return "getLongLiveProcessesForUser";
                case 272:
                    return "getLongLiveTaskIdsForUser";
                case 273:
                    return "getAutoRemoveRecents";
                case 274:
                    return "registerDedicatedCallback";
                case 275:
                    return "setLongLiveApp";
                case 276:
                    return "getLongLiveApp";
                case 277:
                    return "moveTaskToBack";
                case 278:
                    return "moveTaskToBackWithBundle";
                case 279:
                    return "dismissUserSwitchingDialog";
                case 280:
                    return "getGlobalConfiguration";
                case 281:
                    return "checkPermissionForDevice";
                case 282:
                    return "frozenBinderTransactionDetected";
                case 283:
                    return "getBindingUidProcessState";
                case 284:
                    return "getUidLastIdleElapsedTime";
                case 285:
                    return "addOverridePermissionState";
                case 286:
                    return "removeOverridePermissionState";
                case 287:
                    return "clearOverridePermissionStates";
                case 288:
                    return "clearAllOverridePermissionStates";
                case 289:
                    return "noteAppRestrictionEnabled";
                case 290:
                    return "refreshIntentCreatorToken";
                case 291:
                    return "checkProfileForADCP";
                case 292:
                    return "checkAutoRunBlockedApp";
                case 293:
                    return "preloadBoosterAppsFromIpm";
                case 294:
                    return "updateFlingerFlag";
                case 295:
                    return "getRestrictionInfo";
                case 296:
                    return "canRestrict";
                case 297:
                    return "restrict";
                case 298:
                    return "getRestrictableList";
                case 299:
                    return "getAllRestrictedList";
                case 300:
                    return "getRestrictedList";
                case 301:
                    return "updateRestrictionInfo";
                case 302:
                    return "clearRestrictionInfo";
                case 303:
                    return "setTTSPkgInfo";
                case 304:
                    return "clearTTSPkgInfo";
                case 305:
                    return "getInstalledPackageListFromMARs";
                case 306:
                    return "getPackageFromAppProcesses";
                case 307:
                    return "reportAbnormalUsage";
                case 308:
                    return "isHeapDumpAllowed";
                case 309:
                    return "setFGSFilter";
                case 310:
                    return "resetAbnormalList";
                case 311:
                    return "isFreezableUid";
                case 312:
                    return "setProcessSlowdown";
                case 313:
                    return "getIsolatedProcessList";
                case 314:
                    return "getOptionsForIntentSender";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openContentUri = openContentUri(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openContentUri, 1);
                    return true;
                case 2:
                    IUidObserver asInterface = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUidObserver(asInterface, readInt, readInt2, readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IUidObserver asInterface2 = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    return onTransact$registerUidObserverForUids$(parcel, parcel2);
                case 5:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidToObserver(readStrongBinder, readString3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidFromObserver(readStrongBinder2, readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUidActive = isUidActive(readInt5, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidActive);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessState = getUidProcessState(readInt6, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidProcessState);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString7, readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiBegin(readInt9, readInt10, readInt11);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiEnd(readInt12, readInt13, readInt14);
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiStateChanged(readInt15, readInt16, readInt17, readInt18);
                    return true;
                case 13:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = (ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationCrash(readStrongBinder3, parcelableCrashInfo);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    return onTransact$startActivity$(parcel, parcel2);
                case 15:
                    return onTransact$startActivityWithFeature$(parcel, parcel2);
                case 16:
                    unhandledBack();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean finishActivity = finishActivity(readStrongBinder4, readInt19, intent, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivity);
                    return true;
                case 18:
                    return onTransact$registerReceiver$(parcel, parcel2);
                case 19:
                    return onTransact$registerReceiverWithFeature$(parcel, parcel2);
                case 20:
                    IIntentReceiver asInterface3 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterReceiver(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IIntentReceiver asInterface4 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    List<IntentFilter> registeredIntentFilters = getRegisteredIntentFilters(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredIntentFilters, 1);
                    return true;
                case 22:
                    return onTransact$broadcastIntent$(parcel, parcel2);
                case 23:
                    return onTransact$broadcastIntentWithFeature$(parcel, parcel2);
                case 24:
                    IApplicationThread asInterface5 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unbroadcastIntent(asInterface5, intent2, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    return onTransact$finishReceiver$(parcel, parcel2);
                case 26:
                    IApplicationThread asInterface6 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    attachApplication(asInterface6, readLong);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    finishAttachApplication(readLong2, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> tasks = getTasks(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 29:
                    return onTransact$moveTaskToFront$(parcel, parcel2);
                case 30:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(readStrongBinder5, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 31:
                    return onTransact$getContentProvider$(parcel, parcel2);
                case 32:
                    IApplicationThread asInterface7 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ContentProviderHolder.CREATOR);
                    parcel.enforceNoDataAvail();
                    publishContentProviders(asInterface7, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean refContentProvider = refContentProvider(readStrongBinder6, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(refContentProvider);
                    return true;
                case 34:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    PendingIntent runningServiceControlPanel = getRunningServiceControlPanel(componentName);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(runningServiceControlPanel, 1);
                    return true;
                case 35:
                    return onTransact$startService$(parcel, parcel2);
                case 36:
                    IApplicationThread asInterface8 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString8 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopService = stopService(asInterface8, intent3, readString8, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopService);
                    return true;
                case 37:
                    return onTransact$bindService$(parcel, parcel2);
                case 38:
                    return onTransact$bindServiceInstance$(parcel, parcel2);
                case 39:
                    IServiceConnection asInterface9 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateServiceGroup(asInterface9, readInt26, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IServiceConnection asInterface10 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unbindService = unbindService(asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unbindService);
                    return true;
                case 41:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    publishService(readStrongBinder7, intent4, readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String readString9 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDebugApp(readString9, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAgentApp(readString10, readString11);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAlwaysFinish(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    return onTransact$startInstrumentation$(parcel, parcel2);
                case 46:
                    IApplicationThread asInterface11 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addInstrumentationResults(asInterface11, bundle);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IApplicationThread asInterface12 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    int readInt28 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishInstrumentation(asInterface12, readInt28, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    Configuration configuration = getConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configuration, 1);
                    return true;
                case 49:
                    Configuration configuration2 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 50:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean updateMccMncConfiguration = updateMccMncConfiguration(readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateMccMncConfiguration);
                    return true;
                case 51:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean stopServiceToken = stopServiceToken(componentName2, readStrongBinder9, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopServiceToken);
                    return true;
                case 52:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProcessLimit(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int processLimit = getProcessLimit();
                    parcel2.writeNoException();
                    parcel2.writeInt(processLimit);
                    return true;
                case 54:
                    return onTransact$checkUriPermission$(parcel, parcel2);
                case 55:
                    return onTransact$checkContentUriPermissionFull$(parcel, parcel2);
                case 56:
                    return onTransact$checkUriPermissions$(parcel, parcel2);
                case 57:
                    return onTransact$grantUriPermission$(parcel, parcel2);
                case 58:
                    return onTransact$revokeUriPermission$(parcel, parcel2);
                case 59:
                    IActivityController asInterface13 = IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(asInterface13, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IApplicationThread asInterface14 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showWaitingForDebugger(asInterface14, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    signalPersistentProcesses(readInt31);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice recentTasks = getRecentTasks(readInt32, readInt33, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentTasks, 1);
                    return true;
                case 63:
                    return onTransact$serviceDoneExecuting$(parcel, parcel2);
                case 64:
                    return onTransact$getIntentSender$(parcel, parcel2);
                case 65:
                    return onTransact$getIntentSenderWithFeature$(parcel, parcel2);
                case 66:
                    IIntentSender asInterface15 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelIntentSender(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IIntentSender asInterface16 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ActivityManager.PendingIntentInfo infoForIntentSender = getInfoForIntentSender(asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(infoForIntentSender, 1);
                    return true;
                case 68:
                    IIntentSender asInterface17 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IResultReceiver asInterface18 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerIntentSenderCancelListenerEx = registerIntentSenderCancelListenerEx(asInterface17, asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerIntentSenderCancelListenerEx);
                    return true;
                case 69:
                    IIntentSender asInterface19 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IResultReceiver asInterface20 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentSenderCancelListener(asInterface19, asInterface20);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    enterSafeMode();
                    parcel2.writeNoException();
                    return true;
                case 71:
                    return onTransact$noteWakeupAlarm$(parcel, parcel2);
                case 72:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeContentProvider(readStrongBinder10, readBoolean7);
                    return true;
                case 73:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(readStrongBinder11, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindFinished(readStrongBinder12, intent5);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt36 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProcessImportant(readStrongBinder13, readInt36, readBoolean8, readString14);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    return onTransact$setServiceForeground$(parcel, parcel2);
                case 77:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int foregroundServiceType = getForegroundServiceType(componentName3, readStrongBinder14);
                    parcel2.writeNoException();
                    parcel2.writeInt(foregroundServiceType);
                    return true;
                case 78:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean moveActivityTaskToBack = moveActivityTaskToBack(readStrongBinder15, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveActivityTaskToBack);
                    return true;
                case 79:
                    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                    parcel.enforceNoDataAvail();
                    getMemoryInfo(memoryInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(memoryInfo, 1);
                    return true;
                case 80:
                    List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = getProcessesInErrorState();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(processesInErrorState, 1);
                    return true;
                case 81:
                    String readString15 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    IPackageDataObserver asInterface21 = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearApplicationUserData = clearApplicationUserData(readString15, readBoolean10, asInterface21, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearApplicationUserData);
                    return true;
                case 82:
                    String readString16 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAppForUser(readString16, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IForegroundServiceObserver asInterface22 = IForegroundServiceObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerForegroundServiceObserver = registerForegroundServiceObserver(asInterface22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerForegroundServiceObserver);
                    return true;
                case 84:
                    String readString17 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackage(readString17, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String readString18 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackageEvenWhenStopping(readString18, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String readString19 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackageByAdmin(readString19, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int[] createIntArray = parcel.createIntArray();
                    String readString20 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean killPids = killPids(createIntArray, readString20, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(killPids);
                    return true;
                case 88:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningServiceInfo> services = getServices(readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(services, 1);
                    return true;
                case 89:
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = getRunningAppProcesses();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(runningAppProcesses, 1);
                    return true;
                case 90:
                    Intent intent6 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder peekService = peekService(intent6, readString21, readString22);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(peekService);
                    return true;
                case 91:
                    return onTransact$profileControl$(parcel, parcel2);
                case 92:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shutdown = shutdown(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shutdown);
                    return true;
                case 93:
                    stopAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 94:
                    resumeAppSwitches();
                    parcel2.writeNoException();
                    return true;
                case 95:
                    return onTransact$bindBackupAgent$(parcel, parcel2);
                case 96:
                    String readString23 = parcel.readString();
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupAgentCreated(readString23, readStrongBinder16, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    ApplicationInfo applicationInfo = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindBackupAgent(applicationInfo);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    return onTransact$handleIncomingUser$(parcel, parcel2);
                case 99:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addPackageDependency(readString24);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    return onTransact$killApplication$(parcel, parcel2);
                case 101:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readString25);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String readString26 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(readString26, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    Debug.MemoryInfo[] processMemoryInfo = getProcessMemoryInfo(createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(processMemoryInfo, 1);
                    return true;
                case 104:
                    String readString27 = parcel.readString();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killApplicationProcess(readString27, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    return onTransact$handleApplicationWtf$(parcel, parcel2);
                case 106:
                    String readString28 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killBackgroundProcesses(readString28, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    boolean isUserAMonkey = isUserAMonkey();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserAMonkey);
                    return true;
                case 108:
                    List<ApplicationInfo> runningExternalApplications = getRunningExternalApplications();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(runningExternalApplications, 1);
                    return true;
                case 109:
                    finishHeavyWeightApp();
                    parcel2.writeNoException();
                    return true;
                case 110:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    int readInt49 = parcel.readInt();
                    StrictMode.ViolationInfo violationInfo = (StrictMode.ViolationInfo) parcel.readTypedObject(StrictMode.ViolationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationStrictModeViolation(readStrongBinder17, readInt49, violationInfo);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerStrictModeCallback(readStrongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    boolean isTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopActivityImmersive);
                    return true;
                case 113:
                    return onTransact$crashApplicationWithType$(parcel, parcel2);
                case 114:
                    return onTransact$crashApplicationWithTypeWithExtras$(parcel, parcel2);
                case 115:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt50 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getMimeTypeFilterAsync(uri, readInt50, remoteCallback);
                    return true;
                case 116:
                    return onTransact$dumpHeap$(parcel, parcel2);
                case 117:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserRunning = isUserRunning(readInt51, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRunning);
                    return true;
                case 118:
                    String readString29 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(readString29, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean switchUser = switchUser(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchUser);
                    return true;
                case 120:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String switchingFromUserMessage = getSwitchingFromUserMessage(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeString(switchingFromUserMessage);
                    return true;
                case 121:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String switchingToUserMessage = getSwitchingToUserMessage(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeString(switchingToUserMessage);
                    return true;
                case 122:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStopUserOnSwitch(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeTask = removeTask(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTask);
                    return true;
                case 124:
                    IProcessObserver asInterface23 = IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerProcessObserver(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    IProcessObserver asInterface24 = IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterProcessObserver(asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    IIntentSender asInterface25 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isIntentSenderTargetedToPackage = isIntentSenderTargetedToPackage(asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntentSenderTargetedToPackage);
                    return true;
                case 127:
                    Configuration configuration3 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePersistentConfiguration(configuration3);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    Configuration configuration4 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updatePersistentConfigurationWithAttribution(configuration4, readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] processPss = getProcessPss(createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(processPss);
                    return true;
                case 130:
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showBootMessage(charSequence, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    killAllBackgroundProcesses();
                    parcel2.writeNoException();
                    return true;
                case 132:
                    String readString32 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ContentProviderHolder contentProviderExternal = getContentProviderExternal(readString32, readInt59, readStrongBinder19, readString33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contentProviderExternal, 1);
                    return true;
                case 133:
                    String readString34 = parcel.readString();
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternal(readString34, readStrongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    String readString35 = parcel.readString();
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternalAsUser(readString35, readStrongBinder21, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 135:
                    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                    parcel.enforceNoDataAvail();
                    getMyMemoryState(runningAppProcessInfo);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(runningAppProcessInfo, 1);
                    return true;
                case 136:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean killProcessesBelowForeground = killProcessesBelowForeground(readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(killProcessesBelowForeground);
                    return true;
                case 137:
                    UserInfo currentUser = getCurrentUser();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentUser, 1);
                    return true;
                case 138:
                    int currentUserId = getCurrentUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentUserId);
                    return true;
                case 139:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(readStrongBinder22);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 140:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unstableProviderDied(readStrongBinder23);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    IIntentSender asInterface26 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isIntentSenderAnActivity = isIntentSenderAnActivity(asInterface26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIntentSenderAnActivity);
                    return true;
                case 142:
                    return onTransact$startActivityAsUser$(parcel, parcel2);
                case 143:
                    return onTransact$startActivityAsUserWithFeature$(parcel, parcel2);
                case 144:
                    int readInt61 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    IStopUserCallback asInterface27 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUser = stopUser(readInt61, readBoolean13, asInterface27);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUser);
                    return true;
                case 145:
                    int readInt62 = parcel.readInt();
                    IStopUserCallback asInterface28 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUserWithCallback = stopUserWithCallback(readInt62, asInterface28);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUserWithCallback);
                    return true;
                case 146:
                    int readInt63 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    IStopUserCallback asInterface29 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUserExceptCertainProfiles = stopUserExceptCertainProfiles(readInt63, readBoolean14, asInterface29);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUserExceptCertainProfiles);
                    return true;
                case 147:
                    int readInt64 = parcel.readInt();
                    IStopUserCallback asInterface30 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int stopUserWithDelayedLocking = stopUserWithDelayedLocking(readInt64, asInterface30);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUserWithDelayedLocking);
                    return true;
                case 148:
                    IUserSwitchObserver asInterface31 = IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUserSwitchObserver(asInterface31, readString37);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    IUserSwitchObserver asInterface32 = IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserSwitchObserver(asInterface32);
                    parcel2.writeNoException();
                    return true;
                case 150:
                    int[] runningUserIds = getRunningUserIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(runningUserIds);
                    return true;
                case 151:
                    requestSystemServerHeapDump();
                    parcel2.writeNoException();
                    return true;
                case 152:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReport(readInt65);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReportWithDescription(readString38, readString39, readInt66);
                    parcel2.writeNoException();
                    return true;
                case 154:
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestTelephonyBugReport(readString40, readString41);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestWifiBugReport(readString42, readString43);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    String readString44 = parcel.readString();
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestInteractiveBugReportWithDescription(readString44, readString45);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    requestInteractiveBugReport();
                    parcel2.writeNoException();
                    return true;
                case 158:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBugReportWithExtraAttachments(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    requestFullBugReport();
                    parcel2.writeNoException();
                    return true;
                case 160:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    requestRemoteBugReport(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    boolean launchBugReportHandlerApp = launchBugReportHandlerApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(launchBugReportHandlerApp);
                    return true;
                case 162:
                    List<String> bugreportWhitelistedPackages = getBugreportWhitelistedPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(bugreportWhitelistedPackages);
                    return true;
                case 163:
                    IIntentSender asInterface33 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Intent intentForIntentSender = getIntentForIntentSender(asInterface33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentForIntentSender, 1);
                    return true;
                case 164:
                    IBinder readStrongBinder24 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String launchedFromPackage = getLaunchedFromPackage(readStrongBinder24);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 165:
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUid(readInt67, readInt68, readString46);
                    parcel2.writeNoException();
                    return true;
                case 166:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserIsMonkey(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    IBinder readStrongBinder25 = parcel.readStrongBinder();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hang(readStrongBinder25, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 169:
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(readInt69, readInt70, readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 170:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(readInt71);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    ActivityTaskManager.RootTaskInfo focusedRootTaskInfo = getFocusedRootTaskInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(focusedRootTaskInfo, 1);
                    return true;
                case 172:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 173:
                    performIdleMaintenance();
                    parcel2.writeNoException();
                    return true;
                case 174:
                    IBinder readStrongBinder26 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    appNotRespondingViaProvider(readStrongBinder26);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect taskBounds = getTaskBounds(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 176:
                    String readString47 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processMemoryTrimLevel = setProcessMemoryTrimLevel(readString47, readInt73, readInt74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processMemoryTrimLevel);
                    return true;
                case 177:
                    IIntentSender asInterface34 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String tagForIntentSender = getTagForIntentSender(asInterface34, readString48);
                    parcel2.writeNoException();
                    parcel2.writeString(tagForIntentSender);
                    return true;
                case 178:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackground = startUserInBackground(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackground);
                    return true;
                case 179:
                    boolean isInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInLockTaskMode);
                    return true;
                case 180:
                    int readInt76 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startActivityFromRecents = startActivityFromRecents(readInt76, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startActivityFromRecents);
                    return true;
                case 181:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(readInt77);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    IBinder readStrongBinder27 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isTopOfTask = isTopOfTask(readStrongBinder27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopOfTask);
                    return true;
                case 183:
                    bootAnimationComplete();
                    parcel2.writeNoException();
                    return true;
                case 184:
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWindowVisible(readInt78, readInt79, readInt80);
                    parcel2.writeNoException();
                    return true;
                case 185:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setThemeOverlayReady(readInt81);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    ITaskStackListener asInterface35 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(asInterface35);
                    parcel2.writeNoException();
                    return true;
                case 187:
                    ITaskStackListener asInterface36 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(asInterface36);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    int readInt82 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCleartextNetwork(readInt82, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(readInt83, readInt84);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    int readInt85 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(readInt85, rect, readInt86);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 192:
                    String readString49 = parcel.readString();
                    int readInt87 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDumpHeapDebugLimit(readString49, readInt87, readLong5, readString50);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dumpHeapFinished(readString51);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    int readInt88 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(readInt88, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    IIntentSender asInterface37 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt89 = parcel.readInt();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteAlarmStart(asInterface37, workSource, readInt89, readString52);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    IIntentSender asInterface38 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    WorkSource workSource2 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int readInt90 = parcel.readInt();
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteAlarmFinish(asInterface38, workSource2, readInt90, readString53);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageProcessState = getPackageProcessState(readString54, readString55);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageProcessState);
                    return true;
                case 198:
                    boolean startBinderTracking = startBinderTracking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startBinderTracking);
                    return true;
                case 199:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean stopBinderTrackingAndDump = stopBinderTrackingAndDump(parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopBinderTrackingAndDump);
                    return true;
                case 200:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    int readInt91 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    IProgressListener asInterface39 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unlockUser = unlockUser(readInt91, createByteArray2, createByteArray3, asInterface39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockUser);
                    return true;
                case 202:
                    int readInt92 = parcel.readInt();
                    IProgressListener asInterface40 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unlockUser2 = unlockUser2(readInt92, asInterface40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlockUser2);
                    return true;
                case 203:
                    String readString56 = parcel.readString();
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killPackageDependents(readString56, readInt93);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    String readString57 = parcel.readString();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makePackageIdle(readString57, readInt94);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeterministicUidIdle(readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    int memoryTrimLevel = getMemoryTrimLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(memoryTrimLevel);
                    return true;
                case 207:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isVrModePackageEnabled = isVrModePackageEnabled(componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVrModePackageEnabled);
                    return true;
                case 208:
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyLockedProfile(readInt95);
                    parcel2.writeNoException();
                    return true;
                case 209:
                    Intent intent7 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConfirmDeviceCredentialIntent(intent7, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    sendIdleJobTrigger();
                    parcel2.writeNoException();
                    return true;
                case 211:
                    return onTransact$sendIntentSender$(parcel, parcel2);
                case 212:
                    String readString58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBackgroundRestricted = isBackgroundRestricted(readString58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackgroundRestricted);
                    return true;
                case 213:
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRenderThread(readInt96);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHasTopUi(readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(readInt97);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(createStringArrayList, readInt98);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(readInt99);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForNetworkStateUpdate(readLong6);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    int readInt100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backgroundAllowlistUid(readInt100);
                    parcel2.writeNoException();
                    return true;
                case 220:
                    int readInt101 = parcel.readInt();
                    IProgressListener asInterface41 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackgroundWithListener = startUserInBackgroundWithListener(readInt101, asInterface41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackgroundWithListener);
                    return true;
                case 221:
                    int readInt102 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    startDelegateShellPermissionIdentity(readInt102, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 222:
                    stopDelegateShellPermissionIdentity();
                    parcel2.writeNoException();
                    return true;
                case 223:
                    List<String> delegatedShellPermissions = getDelegatedShellPermissions();
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatedShellPermissions);
                    return true;
                case 224:
                    ParcelFileDescriptor lifeMonitor = getLifeMonitor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lifeMonitor, 1);
                    return true;
                case 225:
                    int readInt103 = parcel.readInt();
                    IProgressListener asInterface42 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInForegroundWithListener = startUserInForegroundWithListener(readInt103, asInterface42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInForegroundWithListener);
                    return true;
                case 226:
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    appNotResponding(readString59);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    String readString60 = parcel.readString();
                    boolean readBoolean21 = parcel.readBoolean();
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doActiveLaunch(readString60, readBoolean21, readInt104);
                    return true;
                case 228:
                    String readString61 = parcel.readString();
                    int readInt105 = parcel.readInt();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<ApplicationStartInfo> historicalProcessStartReasons = getHistoricalProcessStartReasons(readString61, readInt105, readInt106);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(historicalProcessStartReasons, 1);
                    return true;
                case 229:
                    IApplicationStartInfoCompleteListener asInterface43 = IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addApplicationStartInfoCompleteListener(asInterface43, readInt107);
                    parcel2.writeNoException();
                    return true;
                case 230:
                    IApplicationStartInfoCompleteListener asInterface44 = IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeApplicationStartInfoCompleteListener(asInterface44, readInt108);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    int readInt109 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addStartInfoTimestamp(readInt109, readLong7, readInt110);
                    parcel2.writeNoException();
                    return true;
                case 232:
                    long readLong8 = parcel.readLong();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    reportStartInfoViewTimestamps(readLong8, readLong9);
                    return true;
                case 233:
                    String readString62 = parcel.readString();
                    int readInt111 = parcel.readInt();
                    int readInt112 = parcel.readInt();
                    int readInt113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<ApplicationExitInfo> historicalProcessExitReasons = getHistoricalProcessExitReasons(readString62, readInt111, readInt112, readInt113);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(historicalProcessExitReasons, 1);
                    return true;
                case 234:
                    int[] createIntArray4 = parcel.createIntArray();
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killProcessesWhenImperceptible(createIntArray4, readString63);
                    parcel2.writeNoException();
                    return true;
                case 235:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    LocusId locusId = (LocusId) parcel.readTypedObject(LocusId.CREATOR);
                    IBinder readStrongBinder28 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setActivityLocusContext(componentName5, locusId, readStrongBinder28);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setProcessStateSummary(createByteArray4);
                    parcel2.writeNoException();
                    return true;
                case 237:
                    boolean isAppFreezerSupported = isAppFreezerSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppFreezerSupported);
                    return true;
                case 238:
                    boolean isAppFreezerEnabled = isAppFreezerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppFreezerEnabled);
                    return true;
                case 239:
                    int readInt114 = parcel.readInt();
                    int readInt115 = parcel.readInt();
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUidForPermissionChange(readInt114, readInt115, readString64);
                    parcel2.writeNoException();
                    return true;
                case 240:
                    resetAppErrors();
                    parcel2.writeNoException();
                    return true;
                case 241:
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableAppFreezer = enableAppFreezer(readBoolean22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableAppFreezer);
                    return true;
                case 242:
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableFgsNotificationRateLimit = enableFgsNotificationRateLimit(readBoolean23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableFgsNotificationRateLimit);
                    return true;
                case 243:
                    IBinder readStrongBinder29 = parcel.readStrongBinder();
                    int readInt116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder29, readInt116);
                    parcel2.writeNoException();
                    return true;
                case 244:
                    int readInt117 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startProfile = startProfile(readInt117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startProfile);
                    return true;
                case 245:
                    int readInt118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean stopProfile = stopProfile(readInt118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopProfile);
                    return true;
                case 246:
                    IIntentSender asInterface45 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    int readInt119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryIntentComponentsForIntentSender = queryIntentComponentsForIntentSender(asInterface45, readInt119);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryIntentComponentsForIntentSender, 1);
                    return true;
                case 247:
                    int readInt120 = parcel.readInt();
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessCapabilities = getUidProcessCapabilities(readInt120, readString65);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidProcessCapabilities);
                    return true;
                case 248:
                    waitForBroadcastIdle();
                    parcel2.writeNoException();
                    return true;
                case 249:
                    waitForBroadcastBarrier();
                    parcel2.writeNoException();
                    return true;
                case 250:
                    String readString66 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    forceDelayBroadcastDelivery(readString66, readLong10);
                    parcel2.writeNoException();
                    return true;
                case 251:
                    int readInt121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isProcessFrozen = isProcessFrozen(readInt121);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProcessFrozen);
                    return true;
                case 252:
                    int readInt122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int backgroundRestrictionExemptionReason = getBackgroundRestrictionExemptionReason(readInt122);
                    parcel2.writeNoException();
                    parcel2.writeInt(backgroundRestrictionExemptionReason);
                    return true;
                case 253:
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString67 = parcel.readString();
                    int readInt123 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] queryRegisteredReceiverPackages = queryRegisteredReceiverPackages(intent8, readString67, readInt123);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(queryRegisteredReceiverPackages);
                    return true;
                case 254:
                    int readInt124 = parcel.readInt();
                    int readInt125 = parcel.readInt();
                    IProgressListener asInterface46 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startUserInBackgroundVisibleOnDisplay = startUserInBackgroundVisibleOnDisplay(readInt124, readInt125, asInterface46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startUserInBackgroundVisibleOnDisplay);
                    return true;
                case 255:
                    int readInt126 = parcel.readInt();
                    IProgressListener asInterface47 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean startProfileWithListener = startProfileWithListener(readInt126, asInterface47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startProfileWithListener);
                    return true;
                case 256:
                    int readInt127 = parcel.readInt();
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int restartUserInBackground = restartUserInBackground(readInt127, readInt128);
                    parcel2.writeNoException();
                    parcel2.writeInt(restartUserInBackground);
                    return true;
                case 257:
                    int[] displayIdsForStartingVisibleBackgroundUsers = getDisplayIdsForStartingVisibleBackgroundUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIdsForStartingVisibleBackgroundUsers);
                    return true;
                case 258:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder readStrongBinder30 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean shouldServiceTimeOut = shouldServiceTimeOut(componentName6, readStrongBinder30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldServiceTimeOut);
                    return true;
                case 259:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder readStrongBinder31 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean hasServiceTimeLimitExceeded = hasServiceTimeLimitExceeded(componentName7, readStrongBinder31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasServiceTimeLimitExceeded);
                    return true;
                case 260:
                    IUidFrozenStateChangedCallback asInterface48 = IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUidFrozenStateChangedCallback(asInterface48);
                    parcel2.writeNoException();
                    return true;
                case 261:
                    IUidFrozenStateChangedCallback asInterface49 = IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidFrozenStateChangedCallback(asInterface49);
                    parcel2.writeNoException();
                    return true;
                case 262:
                    int[] createIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] uidFrozenState = getUidFrozenState(createIntArray5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uidFrozenState);
                    return true;
                case 263:
                    int readInt129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] contentByTask = getContentByTask(readInt129);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(contentByTask);
                    return true;
                case 264:
                    List<String> longLiveApps = getLongLiveApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveApps);
                    return true;
                case 265:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addLongLiveApp = addLongLiveApp(readString68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addLongLiveApp);
                    return true;
                case 266:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeLongLiveApp = removeLongLiveApp(readString69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeLongLiveApp);
                    return true;
                case 267:
                    int maxLongLiveApps = getMaxLongLiveApps();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxLongLiveApps);
                    return true;
                case 268:
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean longLiveTask = setLongLiveTask(readInt130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(longLiveTask);
                    return true;
                case 269:
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearLongLiveTask = clearLongLiveTask(readInt131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearLongLiveTask);
                    return true;
                case 270:
                    List<String> longLiveProcesses = getLongLiveProcesses();
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveProcesses);
                    return true;
                case 271:
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> longLiveProcessesForUser = getLongLiveProcessesForUser(readInt132);
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveProcessesForUser);
                    return true;
                case 272:
                    int readInt133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List longLiveTaskIdsForUser = getLongLiveTaskIdsForUser(readInt133);
                    parcel2.writeNoException();
                    parcel2.writeList(longLiveTaskIdsForUser);
                    return true;
                case 273:
                    int readInt134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean autoRemoveRecents = getAutoRemoveRecents(readInt134);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRemoveRecents);
                    return true;
                case 274:
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    int readInt135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDedicatedCallback(remoteCallback2, readInt135);
                    parcel2.writeNoException();
                    return true;
                case 275:
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean longLiveApp = setLongLiveApp(readString70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(longLiveApp);
                    return true;
                case 276:
                    String longLiveApp2 = getLongLiveApp();
                    parcel2.writeNoException();
                    parcel2.writeString(longLiveApp2);
                    return true;
                case 277:
                    int readInt136 = parcel.readInt();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean moveTaskToBack = moveTaskToBack(readInt136, readBoolean24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveTaskToBack);
                    return true;
                case 278:
                    int readInt137 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean moveTaskToBackWithBundle = moveTaskToBackWithBundle(readInt137, readBoolean25, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveTaskToBackWithBundle);
                    return true;
                case 279:
                    int readInt138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissUserSwitchingDialog(readInt138);
                    parcel2.writeNoException();
                    return true;
                case 280:
                    Configuration globalConfiguration = getGlobalConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalConfiguration, 1);
                    return true;
                case 281:
                    String readString71 = parcel.readString();
                    int readInt139 = parcel.readInt();
                    int readInt140 = parcel.readInt();
                    int readInt141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermissionForDevice = checkPermissionForDevice(readString71, readInt139, readInt140, readInt141);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermissionForDevice);
                    return true;
                case 282:
                    return onTransact$frozenBinderTransactionDetected$(parcel, parcel2);
                case 283:
                    int readInt142 = parcel.readInt();
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int bindingUidProcessState = getBindingUidProcessState(readInt142, readString72);
                    parcel2.writeNoException();
                    parcel2.writeInt(bindingUidProcessState);
                    return true;
                case 284:
                    int readInt143 = parcel.readInt();
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long uidLastIdleElapsedTime = getUidLastIdleElapsedTime(readInt143, readString73);
                    parcel2.writeNoException();
                    parcel2.writeLong(uidLastIdleElapsedTime);
                    return true;
                case 285:
                    return onTransact$addOverridePermissionState$(parcel, parcel2);
                case 286:
                    int readInt144 = parcel.readInt();
                    int readInt145 = parcel.readInt();
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridePermissionState(readInt144, readInt145, readString74);
                    parcel2.writeNoException();
                    return true;
                case 287:
                    int readInt146 = parcel.readInt();
                    int readInt147 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOverridePermissionStates(readInt146, readInt147);
                    parcel2.writeNoException();
                    return true;
                case 288:
                    int readInt148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAllOverridePermissionStates(readInt148);
                    parcel2.writeNoException();
                    return true;
                case 289:
                    return onTransact$noteAppRestrictionEnabled$(parcel, parcel2);
                case 290:
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder refreshIntentCreatorToken = refreshIntentCreatorToken(intent9);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(refreshIntentCreatorToken);
                    return true;
                case 291:
                    int readInt149 = parcel.readInt();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    checkProfileForADCP(readInt149, readString75);
                    parcel2.writeNoException();
                    return true;
                case 292:
                    String readString76 = parcel.readString();
                    int readInt150 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkAutoRunBlockedApp = checkAutoRunBlockedApp(readString76, readInt150);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAutoRunBlockedApp);
                    return true;
                case 293:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt151 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    preloadBoosterAppsFromIpm(createStringArrayList2, readInt151);
                    parcel2.writeNoException();
                    return true;
                case 294:
                    int readInt152 = parcel.readInt();
                    int readInt153 = parcel.readInt();
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] updateFlingerFlag = updateFlingerFlag(readInt152, readInt153, readString77);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(updateFlingerFlag);
                    return true;
                case 295:
                    int readInt154 = parcel.readInt();
                    String readString78 = parcel.readString();
                    int readInt155 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemAppRestrictionManager.RestrictionInfo restrictionInfo = getRestrictionInfo(readInt154, readString78, readInt155);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(restrictionInfo, 1);
                    return true;
                case 296:
                    int readInt156 = parcel.readInt();
                    String readString79 = parcel.readString();
                    int readInt157 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canRestrict = canRestrict(readInt156, readString79, readInt157);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRestrict);
                    return true;
                case 297:
                    return onTransact$restrict$(parcel, parcel2);
                case 298:
                    int readInt158 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictableList = getRestrictableList(readInt158);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(restrictableList, 1);
                    return true;
                case 299:
                    List<SemAppRestrictionManager.AppRestrictionInfo> allRestrictedList = getAllRestrictedList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRestrictedList, 1);
                    return true;
                case 300:
                    int readInt159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictedList = getRestrictedList(readInt159);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(restrictedList, 1);
                    return true;
                case 301:
                    SemAppRestrictionManager.RestrictionInfo restrictionInfo2 = (SemAppRestrictionManager.RestrictionInfo) parcel.readTypedObject(SemAppRestrictionManager.RestrictionInfo.CREATOR);
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateRestrictionInfo = updateRestrictionInfo(restrictionInfo2, createTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateRestrictionInfo);
                    return true;
                case 302:
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearRestrictionInfo = clearRestrictionInfo(createTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearRestrictionInfo);
                    return true;
                case 303:
                    int readInt160 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTTSPkgInfo(readInt160);
                    parcel2.writeNoException();
                    return true;
                case 304:
                    clearTTSPkgInfo();
                    parcel2.writeNoException();
                    return true;
                case 305:
                    int readInt161 = parcel.readInt();
                    int readInt162 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PackageInfo> installedPackageListFromMARs = getInstalledPackageListFromMARs(readInt161, readInt162);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedPackageListFromMARs, 1);
                    return true;
                case 306:
                    int readInt163 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String packageFromAppProcesses = getPackageFromAppProcesses(readInt163);
                    parcel2.writeNoException();
                    parcel2.writeString(packageFromAppProcesses);
                    return true;
                case 307:
                    int readInt164 = parcel.readInt();
                    int readInt165 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportAbnormalUsage(readInt164, readInt165);
                    parcel2.writeNoException();
                    return true;
                case 308:
                    boolean isHeapDumpAllowed = isHeapDumpAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeapDumpAllowed);
                    return true;
                case 309:
                    int readInt166 = parcel.readInt();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean fGSFilter = setFGSFilter(readInt166, readBoolean26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fGSFilter);
                    return true;
                case 310:
                    resetAbnormalList();
                    parcel2.writeNoException();
                    return true;
                case 311:
                    int readInt167 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isFreezableUid = isFreezableUid(readInt167);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFreezableUid);
                    return true;
                case 312:
                    int readInt168 = parcel.readInt();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean processSlowdown = setProcessSlowdown(readInt168, readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processSlowdown);
                    return true;
                case 313:
                    int[] isolatedProcessList = getIsolatedProcessList();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(isolatedProcessList);
                    return true;
                case 314:
                    IIntentSender asInterface50 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Bundle optionsForIntentSender = getOptionsForIntentSender(asInterface50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(optionsForIntentSender, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IActivityManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor openContentUri(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder registerUidObserverForUids(IUidObserver iUidObserver, int i, int i2, String str, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addUidToObserver(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeUidFromObserver(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUidActive(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessState(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermission(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiBegin(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiEnd(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(parcelableCrashInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unhandledBack() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiver(IApplicationThread iApplicationThread, String str, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiverWithFeature(IApplicationThread iApplicationThread, String str, String str2, String str3, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str4, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentReceiver);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<IntentFilter> getRegisteredIntentFilters(IIntentReceiver iIntentReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentReceiver);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(IntentFilter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntentWithFeature(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IIntentReceiver iIntentReceiver, int i, String str3, Bundle bundle, String[] strArr, String[] strArr2, String[] strArr3, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeStringArray(strArr3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void attachApplication(IApplicationThread iApplicationThread, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishAttachApplication(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContentProviderHolder) obtain2.readTypedObject(ContentProviderHolder.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String str, boolean z, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindServiceInstance(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iServiceConnection);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDebugApp(String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAgentApp(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAlwaysFinish(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startInstrumentation(ComponentName componentName, String str, int i, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iInstrumentationWatcher);
                    obtain.writeStrongInterface(iUiAutomationConnection);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Configuration) obtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateConfiguration(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateMccMncConfiguration(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessLimit(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getProcessLimit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void grantUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void revokeUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iActivityController);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void signalPersistentProcesses(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(63, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSender(int i, String str, IBinder iBinder, String str2, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return IIntentSender.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSenderWithFeature(int i, String str, String str2, IBinder iBinder, String str3, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(intentArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return IIntentSender.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityManager.PendingIntentInfo) obtain2.readTypedObject(ActivityManager.PendingIntentInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerIntentSenderCancelListenerEx(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void enterSafeMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(72, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindFinished(IBinder iBinder, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessImportant(IBinder iBinder, int i, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notification, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        memoryInfo.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.ProcessErrorStateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearApplicationUserData(String str, boolean z, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerForegroundServiceObserver(IForegroundServiceObserver iForegroundServiceObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iForegroundServiceObserver);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageEvenWhenStopping(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageByAdmin(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killPids(int[] iArr, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder peekService(Intent intent, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(profilerInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shutdown(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppSwitches() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean bindBackupAgent(String str, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backupAgentCreated(String str, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addPackageDependency(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplication(String str, int i, int i2, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Debug.MemoryInfo[]) obtain2.createTypedArray(Debug.MemoryInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplicationProcess(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(parcelableCrashInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killBackgroundProcesses(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserAMonkey() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishHeavyWeightApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationStrictModeViolation(IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(violationInfo, 0);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerStrictModeCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithType(int i, int i2, String str, int i3, String str2, boolean z, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithTypeWithExtras(int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMimeTypeFilterAsync(Uri uri, int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(115, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean dumpHeap(String str, int i, boolean z, boolean z2, boolean z3, String str2, String str3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserRunning(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean switchUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingFromUserMessage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingToUserMessage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setStopUserOnSwitch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfiguration(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfigurationWithAttribution(Configuration configuration, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long[] getProcessPss(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeBoolean(z);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProviderExternal(String str, int i, IBinder iBinder, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str2);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContentProviderHolder) obtain2.readTypedObject(ContentProviderHolder.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        runningAppProcessInfo.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killProcessesBelowForeground(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public UserInfo getCurrentUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserInfo) obtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getCurrentUserId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unstableProviderDied(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    obtain.writeInt(i3);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUserWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithCallback(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserExceptCertainProfiles(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithDelayedLocking(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserSwitchObserver);
                    obtain.writeString(str);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserSwitchObserver);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getRunningUserIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReport(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithDescription(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestTelephonyBugReport(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestWifiBugReport(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReportWithDescription(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithExtraAttachments(List<Uri> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestFullBugReport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestRemoteBugReport(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean launchBugReportHandlerApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getBugreportWhitelistedPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUid(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setUserIsMonkey(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void hang(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setFocusedRootTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) obtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void restart() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void performIdleMaintenance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Rect getTaskBounds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessMemoryTrimLevel(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeString(str);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackground(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startSystemLockTaskMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void bootAnimationComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateWindowVisible(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setThemeOverlayReady(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTaskResizeable(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDumpHeapDebugLimit(String str, int i, long j, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dumpHeapFinished(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getPackageProcessState(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startBinderTracking() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void suppressResizeConfigChanges(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser2(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killPackageDependents(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void makePackageIdle(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDeterministicUidIdle(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMemoryTrimLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyLockedProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void sendIdleJobTrigger() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int sendIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntentReceiver);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isBackgroundRestricted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRenderThread(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setHasTopUi(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelTaskWindowTransition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPersistentVrThread(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForNetworkStateUpdate(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backgroundAllowlistUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getDelegatedShellPermissions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotResponding(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void doActiveLaunch(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(227, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addStartInfoTimestamp(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportStartInfoViewTimestamps(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(232, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killProcessesWhenImperceptible(int[] iArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityLocusContext(ComponentName componentName, LocusId locusId, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(locusId, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessStateSummary(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUidForPermissionChange(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAppErrors() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableAppFreezer(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableFgsNotificationRateLimit(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopProfile(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice queryIntentComponentsForIntentSender(IIntentSender iIntentSender, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    obtain.writeInt(i);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessCapabilities(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastBarrier() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceDelayBroadcastDelivery(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isProcessFrozen(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBackgroundRestrictionExemptionReason(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] queryRegisteredReceiverPackages(Intent intent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfileWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int restartUserInBackground(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(257, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shouldServiceTimeOut(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(258, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean hasServiceTimeLimitExceeded(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(259, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getUidFrozenState(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] getContentByTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean addLongLiveApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(265, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeLongLiveApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(266, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMaxLongLiveApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(267, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(268, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearLongLiveTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(269, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcesses() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(270, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcessesForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(271, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List getLongLiveTaskIdsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(272, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean getAutoRemoveRecents(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(273, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerDedicatedCallback(RemoteCallback remoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(274, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(275, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLongLiveApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(276, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBack(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(277, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBackWithBundle(int i, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(278, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dismissUserSwitchingDialog(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(279, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getGlobalConfiguration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(280, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Configuration) obtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermissionForDevice(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(281, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(282, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBindingUidProcessState(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(283, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long getUidLastIdleElapsedTime(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(284, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addOverridePermissionState(int i, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(285, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeOverridePermissionState(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(286, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearOverridePermissionStates(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(287, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearAllOverridePermissionStates(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(288, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAppRestrictionEnabled(String str, int i, int i2, boolean z, int i3, String str2, int i4, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeInt(i4);
                    obtain.writeLong(j);
                    this.mRemote.transact(289, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder refreshIntentCreatorToken(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(290, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void checkProfileForADCP(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(291, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean checkAutoRunBlockedApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(292, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void preloadBoosterAppsFromIpm(List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(293, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] updateFlingerFlag(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(294, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(295, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemAppRestrictionManager.RestrictionInfo) obtain2.readTypedObject(SemAppRestrictionManager.RestrictionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean canRestrict(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(296, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean restrict(int i, int i2, boolean z, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(297, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(298, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(299, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(300, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(restrictionInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTTSPkgInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearTTSPkgInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(304, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(305, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getPackageFromAppProcesses(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(306, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportAbnormalUsage(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(307, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isHeapDumpAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(308, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setFGSFilter(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(309, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAbnormalList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(310, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isFreezableUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(311, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessSlowdown(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(312, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getIsolatedProcessList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(313, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Bundle getOptionsForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(314, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        private boolean onTransact$registerUidObserverForUids$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IUidObserver asInterface = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int[] createIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            IBinder registerUidObserverForUids = registerUidObserverForUids(asInterface, readInt, readInt2, readString, createIntArray);
            parcel2.writeNoException();
            parcel2.writeStrongBinder(registerUidObserverForUids);
            return true;
        }

        private boolean onTransact$startActivity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString2 = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            int startActivity = startActivity(asInterface, readString, intent, readString2, readStrongBinder, readString3, readInt, readInt2, profilerInfo, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(startActivity);
            return true;
        }

        private boolean onTransact$startActivityWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
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
            int startActivityWithFeature = startActivityWithFeature(asInterface, readString, readString2, intent, readString3, readStrongBinder, readString4, readInt, readInt2, profilerInfo, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(startActivityWithFeature);
            return true;
        }

        private boolean onTransact$registerReceiver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            IIntentReceiver asInterface2 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            Intent registerReceiver = registerReceiver(asInterface, readString, asInterface2, intentFilter, readString2, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(registerReceiver, 1);
            return true;
        }

        private boolean onTransact$registerReceiverWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            IIntentReceiver asInterface2 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            String readString4 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            Intent registerReceiverWithFeature = registerReceiverWithFeature(asInterface, readString, readString2, readString3, asInterface2, intentFilter, readString4, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(registerReceiverWithFeature, 1);
            return true;
        }

        private boolean onTransact$broadcastIntent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString = parcel.readString();
            IIntentReceiver asInterface2 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            String readString2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            String[] createStringArray = parcel.createStringArray();
            int readInt2 = parcel.readInt();
            Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int broadcastIntent = broadcastIntent(asInterface, intent, readString, asInterface2, readInt, readString2, bundle, createStringArray, readInt2, bundle2, readBoolean, readBoolean2, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(broadcastIntent);
            return true;
        }

        private boolean onTransact$broadcastIntentWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString2 = parcel.readString();
            IIntentReceiver asInterface2 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            int readInt = parcel.readInt();
            String readString3 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            String[] createStringArray = parcel.createStringArray();
            String[] createStringArray2 = parcel.createStringArray();
            String[] createStringArray3 = parcel.createStringArray();
            int readInt2 = parcel.readInt();
            Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int broadcastIntentWithFeature = broadcastIntentWithFeature(asInterface, readString, intent, readString2, asInterface2, readInt, readString3, bundle, createStringArray, createStringArray2, createStringArray3, readInt2, bundle2, readBoolean, readBoolean2, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(broadcastIntentWithFeature);
            return true;
        }

        private boolean onTransact$finishReceiver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            finishReceiver(readStrongBinder, readInt, readString, bundle, readBoolean, readInt2);
            return true;
        }

        private boolean onTransact$moveTaskToFront$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            moveTaskToFront(asInterface, readString, readInt, readInt2, bundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ContentProviderHolder contentProvider = getContentProvider(asInterface, readString, readString2, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedObject(contentProvider, 1);
            return true;
        }

        private boolean onTransact$startService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            ComponentName startService = startService(asInterface, intent, readString, readBoolean, readString2, readString3, readInt);
            parcel2.writeNoException();
            parcel2.writeTypedObject(startService, 1);
            return true;
        }

        private boolean onTransact$bindService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder readStrongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString = parcel.readString();
            IServiceConnection asInterface2 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long readLong = parcel.readLong();
            String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int bindService = bindService(asInterface, readStrongBinder, intent, readString, asInterface2, readLong, readString2, readInt);
            parcel2.writeNoException();
            parcel2.writeInt(bindService);
            return true;
        }

        private boolean onTransact$bindServiceInstance$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder readStrongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString = parcel.readString();
            IServiceConnection asInterface2 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long readLong = parcel.readLong();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            int bindServiceInstance = bindServiceInstance(asInterface, readStrongBinder, intent, readString, asInterface2, readLong, readString2, readString3, readInt);
            parcel2.writeNoException();
            parcel2.writeInt(bindServiceInstance);
            return true;
        }

        private boolean onTransact$startInstrumentation$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            IInstrumentationWatcher asInterface = IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
            IUiAutomationConnection asInterface2 = IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
            int readInt2 = parcel.readInt();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean startInstrumentation = startInstrumentation(componentName, readString, readInt, bundle, asInterface, asInterface2, readInt2, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(startInstrumentation);
            return true;
        }

        private boolean onTransact$checkUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            IBinder readStrongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            int checkUriPermission = checkUriPermission(uri, readInt, readInt2, readInt3, readInt4, readStrongBinder);
            parcel2.writeNoException();
            parcel2.writeInt(checkUriPermission);
            return true;
        }

        private boolean onTransact$checkContentUriPermissionFull$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int checkContentUriPermissionFull = checkContentUriPermissionFull(uri, readInt, readInt2, readInt3, readInt4);
            parcel2.writeNoException();
            parcel2.writeInt(checkContentUriPermissionFull);
            return true;
        }

        private boolean onTransact$checkUriPermissions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            IBinder readStrongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            int[] checkUriPermissions = checkUriPermissions(createTypedArrayList, readInt, readInt2, readInt3, readInt4, readStrongBinder);
            parcel2.writeNoException();
            parcel2.writeIntArray(checkUriPermissions);
            return true;
        }

        private boolean onTransact$grantUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            grantUriPermission(asInterface, readString, uri, readInt, readInt2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$revokeUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            revokeUriPermission(asInterface, readString, uri, readInt, readInt2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$serviceDoneExecuting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            serviceDoneExecuting(readStrongBinder, readInt, readInt2, readInt3, intent);
            return true;
        }

        private boolean onTransact$getIntentSender$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            String readString2 = parcel.readString();
            int readInt2 = parcel.readInt();
            Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
            String[] createStringArray = parcel.createStringArray();
            int readInt3 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            IIntentSender intentSender = getIntentSender(readInt, readString, readStrongBinder, readString2, readInt2, intentArr, createStringArray, readInt3, bundle, readInt4);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(intentSender);
            return true;
        }

        private boolean onTransact$getIntentSenderWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            String readString3 = parcel.readString();
            int readInt2 = parcel.readInt();
            Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
            String[] createStringArray = parcel.createStringArray();
            int readInt3 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            IIntentSender intentSenderWithFeature = getIntentSenderWithFeature(readInt, readString, readString2, readStrongBinder, readString3, readInt2, intentArr, createStringArray, readInt3, bundle, readInt4);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(intentSenderWithFeature);
            return true;
        }

        private boolean onTransact$noteWakeupAlarm$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntentSender asInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
            WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            noteWakeupAlarm(asInterface, workSource, readInt, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setServiceForeground$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            IBinder readStrongBinder = parcel.readStrongBinder();
            int readInt = parcel.readInt();
            Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setServiceForeground(componentName, readStrongBinder, readInt, notification, readInt2, readInt3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$profileControl$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean profileControl = profileControl(readString, readInt, readBoolean, profilerInfo, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(profileControl);
            return true;
        }

        private boolean onTransact$bindBackupAgent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean bindBackupAgent = bindBackupAgent(readString, readInt, readInt2, readInt3, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(bindBackupAgent);
            return true;
        }

        private boolean onTransact$handleIncomingUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int handleIncomingUser = handleIncomingUser(readInt, readInt2, readInt3, readBoolean, readBoolean2, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(handleIncomingUser);
            return true;
        }

        private boolean onTransact$killApplication$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString2 = parcel.readString();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            killApplication(readString, readInt, readInt2, readString2, readInt3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$handleApplicationWtf$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder readStrongBinder = parcel.readStrongBinder();
            String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = (ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean handleApplicationWtf = handleApplicationWtf(readStrongBinder, readString, readBoolean, parcelableCrashInfo, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(handleApplicationWtf);
            return true;
        }

        private boolean onTransact$crashApplicationWithType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            crashApplicationWithType(readInt, readInt2, readString, readInt3, readString2, readBoolean, readInt4);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$crashApplicationWithTypeWithExtras$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            int readInt4 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            crashApplicationWithTypeWithExtras(readInt, readInt2, readString, readInt3, readString2, readBoolean, readInt4, bundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$dumpHeap$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            boolean readBoolean3 = parcel.readBoolean();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            parcel.enforceNoDataAvail();
            boolean dumpHeap = dumpHeap(readString, readInt, readBoolean, readBoolean2, readBoolean3, readString2, readString3, parcelFileDescriptor, remoteCallback);
            parcel2.writeNoException();
            parcel2.writeBoolean(dumpHeap);
            return true;
        }

        private boolean onTransact$startActivityAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String readString = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString2 = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int startActivityAsUser = startActivityAsUser(asInterface, readString, intent, readString2, readStrongBinder, readString3, readInt, readInt2, profilerInfo, bundle, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(startActivityAsUser);
            return true;
        }

        private boolean onTransact$startActivityAsUserWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
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
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int startActivityAsUserWithFeature = startActivityAsUserWithFeature(asInterface, readString, readString2, intent, readString3, readStrongBinder, readString4, readInt, readInt2, profilerInfo, bundle, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(startActivityAsUserWithFeature);
            return true;
        }

        private boolean onTransact$sendIntentSender$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IIntentSender asInterface2 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
            IBinder readStrongBinder = parcel.readStrongBinder();
            int readInt = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String readString = parcel.readString();
            IIntentReceiver asInterface3 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            String readString2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            int sendIntentSender = sendIntentSender(asInterface, asInterface2, readStrongBinder, readInt, intent, readString, asInterface3, readString2, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(sendIntentSender);
            return true;
        }

        private boolean onTransact$frozenBinderTransactionDetected$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            frozenBinderTransactionDetected(readInt, readInt2, readInt3, readInt4);
            return true;
        }

        private boolean onTransact$addOverridePermissionState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            addOverridePermissionState(readInt, readInt2, readString, readInt3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$noteAppRestrictionEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            int readInt3 = parcel.readInt();
            String readString2 = parcel.readString();
            int readInt4 = parcel.readInt();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            noteAppRestrictionEnabled(readString, readInt, readInt2, readBoolean, readInt3, readString2, readInt4, readLong);
            parcel2.writeNoException();
            return true;
        }

        protected void refreshIntentCreatorToken_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$restrict$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean restrict = restrict(readInt, readInt2, readBoolean, readString, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(restrict);
            return true;
        }
    }
}
