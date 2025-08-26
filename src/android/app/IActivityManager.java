package android.app;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ApplicationErrorReport;
import android.app.IActivityController;
import android.app.IApplicationStartInfoCompleteListener;
import android.app.IApplicationThread;
import android.app.IForegroundServiceObserver;
import android.app.IHwuiCallback;
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
        public void getCurrentResourceCacheMax(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
        }

        @Override // android.app.IActivityManager
        public void getCurrentResourceCacheUsage(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
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
        public void getResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
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
        public void setResourceCacheLimit(int i, int i2, IHwuiCallback iHwuiCallback) throws RemoteException {
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
        public void setThreadRT(int i, int i2, boolean z, boolean z2) throws RemoteException {
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

    void getCurrentResourceCacheMax(int i, IHwuiCallback iHwuiCallback) throws RemoteException;

    void getCurrentResourceCacheUsage(int i, IHwuiCallback iHwuiCallback) throws RemoteException;

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

    void getResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException;

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

    void setResourceCacheLimit(int i, int i2, IHwuiCallback iHwuiCallback) throws RemoteException;

    void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException;

    void setStopUserOnSwitch(int i) throws RemoteException;

    void setTTSPkgInfo(int i) throws RemoteException;

    void setTaskResizeable(int i, int i2) throws RemoteException;

    void setThemeOverlayReady(int i) throws RemoteException;

    void setThreadRT(int i, int i2, boolean z, boolean z2) throws RemoteException;

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
        static final int TRANSACTION_getCurrentResourceCacheMax = 317;
        static final int TRANSACTION_getCurrentResourceCacheUsage = 316;
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
        static final int TRANSACTION_getOptionsForIntentSender = 318;
        static final int TRANSACTION_getPackageFromAppProcesses = 306;
        static final int TRANSACTION_getPackageProcessState = 197;
        static final int TRANSACTION_getProcessLimit = 53;
        static final int TRANSACTION_getProcessMemoryInfo = 103;
        static final int TRANSACTION_getProcessPss = 129;
        static final int TRANSACTION_getProcessesInErrorState = 80;
        static final int TRANSACTION_getRecentTasks = 62;
        static final int TRANSACTION_getRegisteredIntentFilters = 21;
        static final int TRANSACTION_getResourceCacheLimit = 315;
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
        static final int TRANSACTION_setResourceCacheLimit = 314;
        static final int TRANSACTION_setServiceForeground = 76;
        static final int TRANSACTION_setStopUserOnSwitch = 122;
        static final int TRANSACTION_setTTSPkgInfo = 303;
        static final int TRANSACTION_setTaskResizeable = 189;
        static final int TRANSACTION_setThemeOverlayReady = 185;
        static final int TRANSACTION_setThreadRT = 319;
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
            return 318;
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActivityManager)) {
                return (IActivityManager) iInterfaceQueryLocalInterface;
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
                    return "setResourceCacheLimit";
                case 315:
                    return "getResourceCacheLimit";
                case 316:
                    return "getCurrentResourceCacheUsage";
                case 317:
                    return "getCurrentResourceCacheMax";
                case 318:
                    return "getOptionsForIntentSender";
                case 319:
                    return "setThreadRT";
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor parcelFileDescriptorOpenContentUri = openContentUri(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelFileDescriptorOpenContentUri, 1);
                    return true;
                case 2:
                    IUidObserver iUidObserverAsInterface = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUidObserver(iUidObserverAsInterface, i3, i4, string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IUidObserver iUidObserverAsInterface2 = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidObserver(iUidObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    return onTransact$registerUidObserverForUids$(parcel, parcel2);
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string3 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidToObserver(strongBinder, string3, i5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidFromObserver(strongBinder2, string4, i6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidActive = isUidActive(i7, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidActive);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessState = getUidProcessState(i8, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidProcessState);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckPermission = checkPermission(string7, i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckPermission);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiBegin(i11, i12, i13);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiEnd(i14, i15, i16);
                    return true;
                case 12:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logFgsApiStateChanged(i17, i18, i19, i20);
                    return true;
                case 13:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = (ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationCrash(strongBinder3, parcelableCrashInfo);
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
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i21 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zFinishActivity = finishActivity(strongBinder4, i21, intent, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFinishActivity);
                    return true;
                case 18:
                    return onTransact$registerReceiver$(parcel, parcel2);
                case 19:
                    return onTransact$registerReceiverWithFeature$(parcel, parcel2);
                case 20:
                    IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterReceiver(iIntentReceiverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IIntentReceiver iIntentReceiverAsInterface2 = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    List<IntentFilter> registeredIntentFilters = getRegisteredIntentFilters(iIntentReceiverAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredIntentFilters, 1);
                    return true;
                case 22:
                    return onTransact$broadcastIntent$(parcel, parcel2);
                case 23:
                    return onTransact$broadcastIntentWithFeature$(parcel, parcel2);
                case 24:
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unbroadcastIntent(iApplicationThreadAsInterface, intent2, i23);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    return onTransact$finishReceiver$(parcel, parcel2);
                case 26:
                    IApplicationThread iApplicationThreadAsInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    attachApplication(iApplicationThreadAsInterface2, j);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    long j2 = parcel.readLong();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    finishAttachApplication(j2, j3);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> tasks = getTasks(i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tasks, 1);
                    return true;
                case 29:
                    return onTransact$moveTaskToFront$(parcel, parcel2);
                case 30:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(strongBinder5, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 31:
                    return onTransact$getContentProvider$(parcel, parcel2);
                case 32:
                    IApplicationThread iApplicationThreadAsInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ContentProviderHolder.CREATOR);
                    parcel.enforceNoDataAvail();
                    publishContentProviders(iApplicationThreadAsInterface3, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRefContentProvider = refContentProvider(strongBinder6, i25, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRefContentProvider);
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
                    IApplicationThread iApplicationThreadAsInterface4 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string8 = parcel.readString();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStopService = stopService(iApplicationThreadAsInterface4, intent3, string8, i27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopService);
                    return true;
                case 37:
                    return onTransact$bindService$(parcel, parcel2);
                case 38:
                    return onTransact$bindServiceInstance$(parcel, parcel2);
                case 39:
                    IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateServiceGroup(iServiceConnectionAsInterface, i28, i29);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IServiceConnection iServiceConnectionAsInterface2 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnbindService = unbindService(iServiceConnectionAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnbindService);
                    return true;
                case 41:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    Intent intent4 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    publishService(strongBinder7, intent4, strongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String string9 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDebugApp(string9, z2, z3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAgentApp(string10, string11);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAlwaysFinish(z4);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    return onTransact$startInstrumentation$(parcel, parcel2);
                case 46:
                    IApplicationThread iApplicationThreadAsInterface5 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addInstrumentationResults(iApplicationThreadAsInterface5, bundle);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IApplicationThread iApplicationThreadAsInterface6 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    int i30 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishInstrumentation(iApplicationThreadAsInterface6, i30, bundle2);
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
                    boolean zUpdateConfiguration = updateConfiguration(configuration2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfiguration);
                    return true;
                case 50:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateMccMncConfiguration = updateMccMncConfiguration(string12, string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateMccMncConfiguration);
                    return true;
                case 51:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStopServiceToken = stopServiceToken(componentName2, strongBinder9, i31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopServiceToken);
                    return true;
                case 52:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProcessLimit(i32);
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
                    IActivityController iActivityControllerAsInterface = IActivityController.Stub.asInterface(parcel.readStrongBinder());
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityController(iActivityControllerAsInterface, z5);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IApplicationThread iApplicationThreadAsInterface7 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showWaitingForDebugger(iApplicationThreadAsInterface7, z6);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    signalPersistentProcesses(i33);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice recentTasks = getRecentTasks(i34, i35, i36);
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
                    IIntentSender iIntentSenderAsInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelIntentSender(iIntentSenderAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    IIntentSender iIntentSenderAsInterface2 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ActivityManager.PendingIntentInfo infoForIntentSender = getInfoForIntentSender(iIntentSenderAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(infoForIntentSender, 1);
                    return true;
                case 68:
                    IIntentSender iIntentSenderAsInterface3 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterIntentSenderCancelListenerEx = registerIntentSenderCancelListenerEx(iIntentSenderAsInterface3, iResultReceiverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterIntentSenderCancelListenerEx);
                    return true;
                case 69:
                    IIntentSender iIntentSenderAsInterface4 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    IResultReceiver iResultReceiverAsInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentSenderCancelListener(iIntentSenderAsInterface4, iResultReceiverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    enterSafeMode();
                    parcel2.writeNoException();
                    return true;
                case 71:
                    return onTransact$noteWakeupAlarm$(parcel, parcel2);
                case 72:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    removeContentProvider(strongBinder10, z7);
                    return true;
                case 73:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(strongBinder11, i37);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    Intent intent5 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindFinished(strongBinder12, intent5);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i38 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProcessImportant(strongBinder13, i38, z8, string14);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    return onTransact$setServiceForeground$(parcel, parcel2);
                case 77:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int foregroundServiceType = getForegroundServiceType(componentName3, strongBinder14);
                    parcel2.writeNoException();
                    parcel2.writeInt(foregroundServiceType);
                    return true;
                case 78:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zMoveActivityTaskToBack = moveActivityTaskToBack(strongBinder15, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMoveActivityTaskToBack);
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
                    String string15 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    IPackageDataObserver iPackageDataObserverAsInterface = IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearApplicationUserData = clearApplicationUserData(string15, z10, iPackageDataObserverAsInterface, i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearApplicationUserData);
                    return true;
                case 82:
                    String string16 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAppForUser(string16, i40);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IForegroundServiceObserver iForegroundServiceObserverAsInterface = IForegroundServiceObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterForegroundServiceObserver = registerForegroundServiceObserver(iForegroundServiceObserverAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterForegroundServiceObserver);
                    return true;
                case 84:
                    String string17 = parcel.readString();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackage(string17, i41);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String string18 = parcel.readString();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackageEvenWhenStopping(string18, i42);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String string19 = parcel.readString();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceStopPackageByAdmin(string19, i43);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    String string20 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zKillPids = killPids(iArrCreateIntArray, string20, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zKillPids);
                    return true;
                case 88:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningServiceInfo> services = getServices(i44, i45);
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
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder iBinderPeekService = peekService(intent6, string21, string22);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderPeekService);
                    return true;
                case 91:
                    return onTransact$profileControl$(parcel, parcel2);
                case 92:
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShutdown = shutdown(i46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShutdown);
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
                    String string23 = parcel.readString();
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupAgentCreated(string23, strongBinder16, i47);
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
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addPackageDependency(string24);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    return onTransact$killApplication$(parcel, parcel2);
                case 101:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(string25);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String string26 = parcel.readString();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogsInDisplay(string26, i48);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    Debug.MemoryInfo[] processMemoryInfo = getProcessMemoryInfo(iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(processMemoryInfo, 1);
                    return true;
                case 104:
                    String string27 = parcel.readString();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killApplicationProcess(string27, i49);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    return onTransact$handleApplicationWtf$(parcel, parcel2);
                case 106:
                    String string28 = parcel.readString();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killBackgroundProcesses(string28, i50);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    boolean zIsUserAMonkey = isUserAMonkey();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserAMonkey);
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
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    int i51 = parcel.readInt();
                    StrictMode.ViolationInfo violationInfo = (StrictMode.ViolationInfo) parcel.readTypedObject(StrictMode.ViolationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleApplicationStrictModeViolation(strongBinder17, i51, violationInfo);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    registerStrictModeCallback(strongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    boolean zIsTopActivityImmersive = isTopActivityImmersive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTopActivityImmersive);
                    return true;
                case 113:
                    return onTransact$crashApplicationWithType$(parcel, parcel2);
                case 114:
                    return onTransact$crashApplicationWithTypeWithExtras$(parcel, parcel2);
                case 115:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i52 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getMimeTypeFilterAsync(uri, i52, remoteCallback);
                    return true;
                case 116:
                    return onTransact$dumpHeap$(parcel, parcel2);
                case 117:
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserRunning = isUserRunning(i53, i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserRunning);
                    return true;
                case 118:
                    String string29 = parcel.readString();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPackageScreenCompatMode(string29, i55);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSwitchUser = switchUser(i56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSwitchUser);
                    return true;
                case 120:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String switchingFromUserMessage = getSwitchingFromUserMessage(i57);
                    parcel2.writeNoException();
                    parcel2.writeString(switchingFromUserMessage);
                    return true;
                case 121:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String switchingToUserMessage = getSwitchingToUserMessage(i58);
                    parcel2.writeNoException();
                    parcel2.writeString(switchingToUserMessage);
                    return true;
                case 122:
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStopUserOnSwitch(i59);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveTask = removeTask(i60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveTask);
                    return true;
                case 124:
                    IProcessObserver iProcessObserverAsInterface = IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerProcessObserver(iProcessObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 125:
                    IProcessObserver iProcessObserverAsInterface2 = IProcessObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterProcessObserver(iProcessObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 126:
                    IIntentSender iIntentSenderAsInterface5 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zIsIntentSenderTargetedToPackage = isIntentSenderTargetedToPackage(iIntentSenderAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIntentSenderTargetedToPackage);
                    return true;
                case 127:
                    Configuration configuration3 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePersistentConfiguration(configuration3);
                    parcel2.writeNoException();
                    return true;
                case 128:
                    Configuration configuration4 = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updatePersistentConfigurationWithAttribution(configuration4, string30, string31);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] processPss = getProcessPss(iArrCreateIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(processPss);
                    return true;
                case 130:
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showBootMessage(charSequence, z12);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    killAllBackgroundProcesses();
                    parcel2.writeNoException();
                    return true;
                case 132:
                    String string32 = parcel.readString();
                    int i61 = parcel.readInt();
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ContentProviderHolder contentProviderExternal = getContentProviderExternal(string32, i61, strongBinder19, string33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contentProviderExternal, 1);
                    return true;
                case 133:
                    String string34 = parcel.readString();
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternal(string34, strongBinder20);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    String string35 = parcel.readString();
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeContentProviderExternalAsUser(string35, strongBinder21, i62);
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
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zKillProcessesBelowForeground = killProcessesBelowForeground(string36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zKillProcessesBelowForeground);
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
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(strongBinder22);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 140:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unstableProviderDied(strongBinder23);
                    parcel2.writeNoException();
                    return true;
                case 141:
                    IIntentSender iIntentSenderAsInterface6 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zIsIntentSenderAnActivity = isIntentSenderAnActivity(iIntentSenderAsInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIntentSenderAnActivity);
                    return true;
                case 142:
                    return onTransact$startActivityAsUser$(parcel, parcel2);
                case 143:
                    return onTransact$startActivityAsUserWithFeature$(parcel, parcel2);
                case 144:
                    int i63 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    IStopUserCallback iStopUserCallbackAsInterface = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopUser = stopUser(i63, z13, iStopUserCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopUser);
                    return true;
                case 145:
                    int i64 = parcel.readInt();
                    IStopUserCallback iStopUserCallbackAsInterface2 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopUserWithCallback = stopUserWithCallback(i64, iStopUserCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopUserWithCallback);
                    return true;
                case 146:
                    int i65 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    IStopUserCallback iStopUserCallbackAsInterface3 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopUserExceptCertainProfiles = stopUserExceptCertainProfiles(i65, z14, iStopUserCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopUserExceptCertainProfiles);
                    return true;
                case 147:
                    int i66 = parcel.readInt();
                    IStopUserCallback iStopUserCallbackAsInterface4 = IStopUserCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStopUserWithDelayedLocking = stopUserWithDelayedLocking(i66, iStopUserCallbackAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopUserWithDelayedLocking);
                    return true;
                case 148:
                    IUserSwitchObserver iUserSwitchObserverAsInterface = IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUserSwitchObserver(iUserSwitchObserverAsInterface, string37);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    IUserSwitchObserver iUserSwitchObserverAsInterface2 = IUserSwitchObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserSwitchObserver(iUserSwitchObserverAsInterface2);
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
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReport(i67);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBugReportWithDescription(string38, string39, i68);
                    parcel2.writeNoException();
                    return true;
                case 154:
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestTelephonyBugReport(string40, string41);
                    parcel2.writeNoException();
                    return true;
                case 155:
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestWifiBugReport(string42, string43);
                    parcel2.writeNoException();
                    return true;
                case 156:
                    String string44 = parcel.readString();
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestInteractiveBugReportWithDescription(string44, string45);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    requestInteractiveBugReport();
                    parcel2.writeNoException();
                    return true;
                case 158:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBugReportWithExtraAttachments(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    requestFullBugReport();
                    parcel2.writeNoException();
                    return true;
                case 160:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    requestRemoteBugReport(j4);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    boolean zLaunchBugReportHandlerApp = launchBugReportHandlerApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zLaunchBugReportHandlerApp);
                    return true;
                case 162:
                    List<String> bugreportWhitelistedPackages = getBugreportWhitelistedPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(bugreportWhitelistedPackages);
                    return true;
                case 163:
                    IIntentSender iIntentSenderAsInterface7 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Intent intentForIntentSender = getIntentForIntentSender(iIntentSenderAsInterface7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentForIntentSender, 1);
                    return true;
                case 164:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String launchedFromPackage = getLaunchedFromPackage(strongBinder24);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 165:
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUid(i69, i70, string46);
                    parcel2.writeNoException();
                    return true;
                case 166:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserIsMonkey(z15);
                    parcel2.writeNoException();
                    return true;
                case 167:
                    IBinder strongBinder25 = parcel.readStrongBinder();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hang(strongBinder25, z16);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    List<ActivityTaskManager.RootTaskInfo> allRootTaskInfos = getAllRootTaskInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRootTaskInfos, 1);
                    return true;
                case 169:
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveTaskToRootTask(i71, i72, z17);
                    parcel2.writeNoException();
                    return true;
                case 170:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusedRootTask(i73);
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
                    IBinder strongBinder26 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    appNotRespondingViaProvider(strongBinder26);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Rect taskBounds = getTaskBounds(i74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskBounds, 1);
                    return true;
                case 176:
                    String string47 = parcel.readString();
                    int i75 = parcel.readInt();
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processMemoryTrimLevel = setProcessMemoryTrimLevel(string47, i75, i76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processMemoryTrimLevel);
                    return true;
                case 177:
                    IIntentSender iIntentSenderAsInterface8 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String tagForIntentSender = getTagForIntentSender(iIntentSenderAsInterface8, string48);
                    parcel2.writeNoException();
                    parcel2.writeString(tagForIntentSender);
                    return true;
                case 178:
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStartUserInBackground = startUserInBackground(i77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartUserInBackground);
                    return true;
                case 179:
                    boolean zIsInLockTaskMode = isInLockTaskMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInLockTaskMode);
                    return true;
                case 180:
                    int i78 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartActivityFromRecents = startActivityFromRecents(i78, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartActivityFromRecents);
                    return true;
                case 181:
                    int i79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startSystemLockTaskMode(i79);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    IBinder strongBinder27 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsTopOfTask = isTopOfTask(strongBinder27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTopOfTask);
                    return true;
                case 183:
                    bootAnimationComplete();
                    parcel2.writeNoException();
                    return true;
                case 184:
                    int i80 = parcel.readInt();
                    int i81 = parcel.readInt();
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWindowVisible(i80, i81, i82);
                    parcel2.writeNoException();
                    return true;
                case 185:
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setThemeOverlayReady(i83);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    ITaskStackListener iTaskStackListenerAsInterface = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskStackListener(iTaskStackListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 187:
                    ITaskStackListener iTaskStackListenerAsInterface2 = ITaskStackListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskStackListener(iTaskStackListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    int i84 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyCleartextNetwork(i84, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    int i85 = parcel.readInt();
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTaskResizeable(i85, i86);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    int i87 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resizeTask(i87, rect, i88);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    int lockTaskModeState = getLockTaskModeState();
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskModeState);
                    return true;
                case 192:
                    String string49 = parcel.readString();
                    int i89 = parcel.readInt();
                    long j5 = parcel.readLong();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDumpHeapDebugLimit(string49, i89, j5, string50);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    String string51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dumpHeapFinished(string51);
                    parcel2.writeNoException();
                    return true;
                case 194:
                    int i90 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateLockTaskPackages(i90, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 195:
                    IIntentSender iIntentSenderAsInterface9 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    int i91 = parcel.readInt();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    noteAlarmStart(iIntentSenderAsInterface9, workSource, i91, string52);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    return onTransact$noteAlarmFinish$(parcel, parcel2);
                case 197:
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageProcessState = getPackageProcessState(string53, string54);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageProcessState);
                    return true;
                case 198:
                    boolean zStartBinderTracking = startBinderTracking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartBinderTracking);
                    return true;
                case 199:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStopBinderTrackingAndDump = stopBinderTrackingAndDump(parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopBinderTrackingAndDump);
                    return true;
                case 200:
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressResizeConfigChanges(z18);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    return onTransact$unlockUser$(parcel, parcel2);
                case 202:
                    int i92 = parcel.readInt();
                    IProgressListener iProgressListenerAsInterface = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnlockUser2 = unlockUser2(i92, iProgressListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnlockUser2);
                    return true;
                case 203:
                    String string55 = parcel.readString();
                    int i93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    killPackageDependents(string55, i93);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    String string56 = parcel.readString();
                    int i94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    makePackageIdle(string56, i94);
                    parcel2.writeNoException();
                    return true;
                case 205:
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeterministicUidIdle(z19);
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
                    boolean zIsVrModePackageEnabled = isVrModePackageEnabled(componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVrModePackageEnabled);
                    return true;
                case 208:
                    int i95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyLockedProfile(i95);
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
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsBackgroundRestricted = isBackgroundRestricted(string57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBackgroundRestricted);
                    return true;
                case 213:
                    int i96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRenderThread(i96);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHasTopUi(z20);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelTaskWindowTransition(i97);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i98 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(arrayListCreateStringArrayList, i98);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    int i99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPersistentVrThread(i99);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    waitForNetworkStateUpdate(j6);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    int i100 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backgroundAllowlistUid(i100);
                    parcel2.writeNoException();
                    return true;
                case 220:
                    int i101 = parcel.readInt();
                    IProgressListener iProgressListenerAsInterface2 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zStartUserInBackgroundWithListener = startUserInBackgroundWithListener(i101, iProgressListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartUserInBackgroundWithListener);
                    return true;
                case 221:
                    int i102 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    startDelegateShellPermissionIdentity(i102, strArrCreateStringArray2);
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
                    int i103 = parcel.readInt();
                    IProgressListener iProgressListenerAsInterface3 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zStartUserInForegroundWithListener = startUserInForegroundWithListener(i103, iProgressListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartUserInForegroundWithListener);
                    return true;
                case 226:
                    String string58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    appNotResponding(string58);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    String string59 = parcel.readString();
                    boolean z21 = parcel.readBoolean();
                    int i104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doActiveLaunch(string59, z21, i104);
                    return true;
                case 228:
                    String string60 = parcel.readString();
                    int i105 = parcel.readInt();
                    int i106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<ApplicationStartInfo> historicalProcessStartReasons = getHistoricalProcessStartReasons(string60, i105, i106);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(historicalProcessStartReasons, 1);
                    return true;
                case 229:
                    IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListenerAsInterface = IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int i107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addApplicationStartInfoCompleteListener(iApplicationStartInfoCompleteListenerAsInterface, i107);
                    parcel2.writeNoException();
                    return true;
                case 230:
                    IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListenerAsInterface2 = IApplicationStartInfoCompleteListener.Stub.asInterface(parcel.readStrongBinder());
                    int i108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeApplicationStartInfoCompleteListener(iApplicationStartInfoCompleteListenerAsInterface2, i108);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    int i109 = parcel.readInt();
                    long j7 = parcel.readLong();
                    int i110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addStartInfoTimestamp(i109, j7, i110);
                    parcel2.writeNoException();
                    return true;
                case 232:
                    long j8 = parcel.readLong();
                    long j9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    reportStartInfoViewTimestamps(j8, j9);
                    return true;
                case 233:
                    return onTransact$getHistoricalProcessExitReasons$(parcel, parcel2);
                case 234:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    String string61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killProcessesWhenImperceptible(iArrCreateIntArray4, string61);
                    parcel2.writeNoException();
                    return true;
                case 235:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    LocusId locusId = (LocusId) parcel.readTypedObject(LocusId.CREATOR);
                    IBinder strongBinder28 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setActivityLocusContext(componentName5, locusId, strongBinder28);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setProcessStateSummary(bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 237:
                    boolean zIsAppFreezerSupported = isAppFreezerSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppFreezerSupported);
                    return true;
                case 238:
                    boolean zIsAppFreezerEnabled = isAppFreezerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppFreezerEnabled);
                    return true;
                case 239:
                    int i111 = parcel.readInt();
                    int i112 = parcel.readInt();
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    killUidForPermissionChange(i111, i112, string62);
                    parcel2.writeNoException();
                    return true;
                case 240:
                    resetAppErrors();
                    parcel2.writeNoException();
                    return true;
                case 241:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableAppFreezer = enableAppFreezer(z22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableAppFreezer);
                    return true;
                case 242:
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableFgsNotificationRateLimit = enableFgsNotificationRateLimit(z23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableFgsNotificationRateLimit);
                    return true;
                case 243:
                    IBinder strongBinder29 = parcel.readStrongBinder();
                    int i113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(strongBinder29, i113);
                    parcel2.writeNoException();
                    return true;
                case 244:
                    int i114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStartProfile = startProfile(i114);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartProfile);
                    return true;
                case 245:
                    int i115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zStopProfile = stopProfile(i115);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopProfile);
                    return true;
                case 246:
                    IIntentSender iIntentSenderAsInterface10 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryIntentComponentsForIntentSender = queryIntentComponentsForIntentSender(iIntentSenderAsInterface10, i116);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryIntentComponentsForIntentSender, 1);
                    return true;
                case 247:
                    int i117 = parcel.readInt();
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int uidProcessCapabilities = getUidProcessCapabilities(i117, string63);
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
                    String string64 = parcel.readString();
                    long j10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    forceDelayBroadcastDelivery(string64, j10);
                    parcel2.writeNoException();
                    return true;
                case 251:
                    int i118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsProcessFrozen = isProcessFrozen(i118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProcessFrozen);
                    return true;
                case 252:
                    int i119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int backgroundRestrictionExemptionReason = getBackgroundRestrictionExemptionReason(i119);
                    parcel2.writeNoException();
                    parcel2.writeInt(backgroundRestrictionExemptionReason);
                    return true;
                case 253:
                    Intent intent8 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string65 = parcel.readString();
                    int i120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrQueryRegisteredReceiverPackages = queryRegisteredReceiverPackages(intent8, string65, i120);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrQueryRegisteredReceiverPackages);
                    return true;
                case 254:
                    int i121 = parcel.readInt();
                    int i122 = parcel.readInt();
                    IProgressListener iProgressListenerAsInterface4 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zStartUserInBackgroundVisibleOnDisplay = startUserInBackgroundVisibleOnDisplay(i121, i122, iProgressListenerAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartUserInBackgroundVisibleOnDisplay);
                    return true;
                case 255:
                    int i123 = parcel.readInt();
                    IProgressListener iProgressListenerAsInterface5 = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zStartProfileWithListener = startProfileWithListener(i123, iProgressListenerAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartProfileWithListener);
                    return true;
                case 256:
                    int i124 = parcel.readInt();
                    int i125 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRestartUserInBackground = restartUserInBackground(i124, i125);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRestartUserInBackground);
                    return true;
                case 257:
                    int[] displayIdsForStartingVisibleBackgroundUsers = getDisplayIdsForStartingVisibleBackgroundUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIdsForStartingVisibleBackgroundUsers);
                    return true;
                case 258:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder strongBinder30 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zShouldServiceTimeOut = shouldServiceTimeOut(componentName6, strongBinder30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldServiceTimeOut);
                    return true;
                case 259:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    IBinder strongBinder31 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zHasServiceTimeLimitExceeded = hasServiceTimeLimitExceeded(componentName7, strongBinder31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasServiceTimeLimitExceeded);
                    return true;
                case 260:
                    IUidFrozenStateChangedCallback iUidFrozenStateChangedCallbackAsInterface = IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUidFrozenStateChangedCallback(iUidFrozenStateChangedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 261:
                    IUidFrozenStateChangedCallback iUidFrozenStateChangedCallbackAsInterface2 = IUidFrozenStateChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUidFrozenStateChangedCallback(iUidFrozenStateChangedCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 262:
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int[] uidFrozenState = getUidFrozenState(iArrCreateIntArray5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uidFrozenState);
                    return true;
                case 263:
                    int i126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] contentByTask = getContentByTask(i126);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(contentByTask);
                    return true;
                case 264:
                    List<String> longLiveApps = getLongLiveApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveApps);
                    return true;
                case 265:
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddLongLiveApp = addLongLiveApp(string66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddLongLiveApp);
                    return true;
                case 266:
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveLongLiveApp = removeLongLiveApp(string67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveLongLiveApp);
                    return true;
                case 267:
                    int maxLongLiveApps = getMaxLongLiveApps();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxLongLiveApps);
                    return true;
                case 268:
                    int i127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean longLiveTask = setLongLiveTask(i127);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(longLiveTask);
                    return true;
                case 269:
                    int i128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zClearLongLiveTask = clearLongLiveTask(i128);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearLongLiveTask);
                    return true;
                case 270:
                    List<String> longLiveProcesses = getLongLiveProcesses();
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveProcesses);
                    return true;
                case 271:
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> longLiveProcessesForUser = getLongLiveProcessesForUser(i129);
                    parcel2.writeNoException();
                    parcel2.writeStringList(longLiveProcessesForUser);
                    return true;
                case 272:
                    int i130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List longLiveTaskIdsForUser = getLongLiveTaskIdsForUser(i130);
                    parcel2.writeNoException();
                    parcel2.writeList(longLiveTaskIdsForUser);
                    return true;
                case 273:
                    int i131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean autoRemoveRecents = getAutoRemoveRecents(i131);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRemoveRecents);
                    return true;
                case 274:
                    RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    int i132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDedicatedCallback(remoteCallback2, i132);
                    parcel2.writeNoException();
                    return true;
                case 275:
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean longLiveApp = setLongLiveApp(string68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(longLiveApp);
                    return true;
                case 276:
                    String longLiveApp2 = getLongLiveApp();
                    parcel2.writeNoException();
                    parcel2.writeString(longLiveApp2);
                    return true;
                case 277:
                    int i133 = parcel.readInt();
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zMoveTaskToBack = moveTaskToBack(i133, z24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMoveTaskToBack);
                    return true;
                case 278:
                    int i134 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zMoveTaskToBackWithBundle = moveTaskToBackWithBundle(i134, z25, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMoveTaskToBackWithBundle);
                    return true;
                case 279:
                    int i135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dismissUserSwitchingDialog(i135);
                    parcel2.writeNoException();
                    return true;
                case 280:
                    Configuration globalConfiguration = getGlobalConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalConfiguration, 1);
                    return true;
                case 281:
                    return onTransact$checkPermissionForDevice$(parcel, parcel2);
                case 282:
                    return onTransact$frozenBinderTransactionDetected$(parcel, parcel2);
                case 283:
                    int i136 = parcel.readInt();
                    String string69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int bindingUidProcessState = getBindingUidProcessState(i136, string69);
                    parcel2.writeNoException();
                    parcel2.writeInt(bindingUidProcessState);
                    return true;
                case 284:
                    int i137 = parcel.readInt();
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long uidLastIdleElapsedTime = getUidLastIdleElapsedTime(i137, string70);
                    parcel2.writeNoException();
                    parcel2.writeLong(uidLastIdleElapsedTime);
                    return true;
                case 285:
                    return onTransact$addOverridePermissionState$(parcel, parcel2);
                case 286:
                    int i138 = parcel.readInt();
                    int i139 = parcel.readInt();
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridePermissionState(i138, i139, string71);
                    parcel2.writeNoException();
                    return true;
                case 287:
                    int i140 = parcel.readInt();
                    int i141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOverridePermissionStates(i140, i141);
                    parcel2.writeNoException();
                    return true;
                case 288:
                    int i142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAllOverridePermissionStates(i142);
                    parcel2.writeNoException();
                    return true;
                case 289:
                    return onTransact$noteAppRestrictionEnabled$(parcel, parcel2);
                case 290:
                    Intent intent9 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder iBinderRefreshIntentCreatorToken = refreshIntentCreatorToken(intent9);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderRefreshIntentCreatorToken);
                    return true;
                case 291:
                    int i143 = parcel.readInt();
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    checkProfileForADCP(i143, string72);
                    parcel2.writeNoException();
                    return true;
                case 292:
                    String string73 = parcel.readString();
                    int i144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAutoRunBlockedApp = checkAutoRunBlockedApp(string73, i144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAutoRunBlockedApp);
                    return true;
                case 293:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i145 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    preloadBoosterAppsFromIpm(arrayListCreateStringArrayList2, i145);
                    parcel2.writeNoException();
                    return true;
                case 294:
                    int i146 = parcel.readInt();
                    int i147 = parcel.readInt();
                    String string74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] iArrUpdateFlingerFlag = updateFlingerFlag(i146, i147, string74);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrUpdateFlingerFlag);
                    return true;
                case 295:
                    int i148 = parcel.readInt();
                    String string75 = parcel.readString();
                    int i149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemAppRestrictionManager.RestrictionInfo restrictionInfo = getRestrictionInfo(i148, string75, i149);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(restrictionInfo, 1);
                    return true;
                case 296:
                    int i150 = parcel.readInt();
                    String string76 = parcel.readString();
                    int i151 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanRestrict = canRestrict(i150, string76, i151);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanRestrict);
                    return true;
                case 297:
                    return onTransact$restrict$(parcel, parcel2);
                case 298:
                    int i152 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictableList = getRestrictableList(i152);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(restrictableList, 1);
                    return true;
                case 299:
                    List<SemAppRestrictionManager.AppRestrictionInfo> allRestrictedList = getAllRestrictedList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allRestrictedList, 1);
                    return true;
                case 300:
                    int i153 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemAppRestrictionManager.AppRestrictionInfo> restrictedList = getRestrictedList(i153);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(restrictedList, 1);
                    return true;
                case 301:
                    SemAppRestrictionManager.RestrictionInfo restrictionInfo2 = (SemAppRestrictionManager.RestrictionInfo) parcel.readTypedObject(SemAppRestrictionManager.RestrictionInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateRestrictionInfo = updateRestrictionInfo(restrictionInfo2, arrayListCreateTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateRestrictionInfo);
                    return true;
                case 302:
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearRestrictionInfo = clearRestrictionInfo(arrayListCreateTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearRestrictionInfo);
                    return true;
                case 303:
                    int i154 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTTSPkgInfo(i154);
                    parcel2.writeNoException();
                    return true;
                case 304:
                    clearTTSPkgInfo();
                    parcel2.writeNoException();
                    return true;
                case 305:
                    int i155 = parcel.readInt();
                    int i156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PackageInfo> installedPackageListFromMARs = getInstalledPackageListFromMARs(i155, i156);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedPackageListFromMARs, 1);
                    return true;
                case 306:
                    int i157 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String packageFromAppProcesses = getPackageFromAppProcesses(i157);
                    parcel2.writeNoException();
                    parcel2.writeString(packageFromAppProcesses);
                    return true;
                case 307:
                    int i158 = parcel.readInt();
                    int i159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportAbnormalUsage(i158, i159);
                    parcel2.writeNoException();
                    return true;
                case 308:
                    boolean zIsHeapDumpAllowed = isHeapDumpAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeapDumpAllowed);
                    return true;
                case 309:
                    int i160 = parcel.readInt();
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean fGSFilter = setFGSFilter(i160, z26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fGSFilter);
                    return true;
                case 310:
                    resetAbnormalList();
                    parcel2.writeNoException();
                    return true;
                case 311:
                    int i161 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsFreezableUid = isFreezableUid(i161);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFreezableUid);
                    return true;
                case 312:
                    int i162 = parcel.readInt();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean processSlowdown = setProcessSlowdown(i162, z27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processSlowdown);
                    return true;
                case 313:
                    int[] isolatedProcessList = getIsolatedProcessList();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(isolatedProcessList);
                    return true;
                case 314:
                    int i163 = parcel.readInt();
                    int i164 = parcel.readInt();
                    IHwuiCallback iHwuiCallbackAsInterface = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResourceCacheLimit(i163, i164, iHwuiCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 315:
                    int i165 = parcel.readInt();
                    IHwuiCallback iHwuiCallbackAsInterface2 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getResourceCacheLimit(i165, iHwuiCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 316:
                    int i166 = parcel.readInt();
                    IHwuiCallback iHwuiCallbackAsInterface3 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCurrentResourceCacheUsage(i166, iHwuiCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 317:
                    int i167 = parcel.readInt();
                    IHwuiCallback iHwuiCallbackAsInterface4 = IHwuiCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCurrentResourceCacheMax(i167, iHwuiCallbackAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 318:
                    IIntentSender iIntentSenderAsInterface11 = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Bundle optionsForIntentSender = getOptionsForIntentSender(iIntentSenderAsInterface11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(optionsForIntentSender, 1);
                    return true;
                case 319:
                    return onTransact$setThreadRT$(parcel, parcel2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidObserver(IUidObserver iUidObserver, int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUidObserver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidObserver(IUidObserver iUidObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUidObserver);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder registerUidObserverForUids(IUidObserver iUidObserver, int i, int i2, String str, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUidObserver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addUidToObserver(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeUidFromObserver(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUidActive(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessState(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermission(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiBegin(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiEnd(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void logFgsApiStateChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationCrash(IBinder iBinder, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(parcelableCrashInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivity(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unhandledBack() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiver(IApplicationThread iApplicationThread, String str, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent registerReceiverWithFeature(IApplicationThread iApplicationThread, String str, String str2, String str3, IIntentReceiver iIntentReceiver, IntentFilter intentFilter, String str4, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterReceiver(IIntentReceiver iIntentReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<IntentFilter> getRegisteredIntentFilters(IIntentReceiver iIntentReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(IntentFilter.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntent(IApplicationThread iApplicationThread, Intent intent, String str, IIntentReceiver iIntentReceiver, int i, String str2, Bundle bundle, String[] strArr, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int broadcastIntentWithFeature(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IIntentReceiver iIntentReceiver, int i, String str3, Bundle bundle, String[] strArr, String[] strArr2, String[] strArr3, int i2, Bundle bundle2, boolean z, boolean z2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeStringArray(strArr3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbroadcastIntent(IApplicationThread iApplicationThread, Intent intent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishReceiver(IBinder iBinder, int i, String str, Bundle bundle, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void attachApplication(IApplicationThread iApplicationThread, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishAttachApplication(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningTaskInfo> getTasks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToFront(IApplicationThread iApplicationThread, String str, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContentProviderHolder) parcelObtain2.readTypedObject(ContentProviderHolder.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishContentProviders(IApplicationThread iApplicationThread, List<ContentProviderHolder> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean refContentProvider(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public PendingIntent getRunningServiceControlPanel(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PendingIntent) parcelObtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ComponentName startService(IApplicationThread iApplicationThread, Intent intent, String str, boolean z, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopService(IApplicationThread iApplicationThread, Intent intent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindService(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int bindServiceInstance(IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, String str, IServiceConnection iServiceConnection, long j, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateServiceGroup(IServiceConnection iServiceConnection, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unbindService(IServiceConnection iServiceConnection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void publishService(IBinder iBinder, Intent intent, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDebugApp(String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAgentApp(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setAlwaysFinish(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startInstrumentation(ComponentName componentName, String str, int i, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iInstrumentationWatcher);
                    parcelObtain.writeStrongInterface(iUiAutomationConnection);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addInstrumentationResults(IApplicationThread iApplicationThread, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishInstrumentation(IApplicationThread iApplicationThread, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Configuration) parcelObtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateConfiguration(Configuration configuration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateMccMncConfiguration(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopServiceToken(ComponentName componentName, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessLimit(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getProcessLimit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkUriPermission(Uri uri, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkContentUriPermissionFull(Uri uri, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] checkUriPermissions(List<Uri> list, int i, int i2, int i3, int i4, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void grantUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void revokeUriPermission(IApplicationThread iApplicationThread, String str, Uri uri, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityController(IActivityController iActivityController, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iActivityController);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showWaitingForDebugger(IApplicationThread iApplicationThread, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void signalPersistentProcesses(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice getRecentTasks(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void serviceDoneExecuting(IBinder iBinder, int i, int i2, int i3, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(63, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSender(int i, String str, IBinder iBinder, String str2, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(intentArr, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IIntentSender.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IIntentSender getIntentSenderWithFeature(int i, String str, String str2, IBinder iBinder, String str3, int i2, Intent[] intentArr, String[] strArr, int i3, Bundle bundle, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(intentArr, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IIntentSender.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityManager.PendingIntentInfo getInfoForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityManager.PendingIntentInfo) parcelObtain2.readTypedObject(ActivityManager.PendingIntentInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerIntentSenderCancelListenerEx(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterIntentSenderCancelListener(IIntentSender iIntentSender, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void enterSafeMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteWakeupAlarm(IIntentSender iIntentSender, WorkSource workSource, int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProvider(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(72, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindFinished(IBinder iBinder, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessImportant(IBinder iBinder, int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setServiceForeground(ComponentName componentName, IBinder iBinder, int i, Notification notification, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notification, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getForegroundServiceType(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMemoryInfo(ActivityManager.MemoryInfo memoryInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        memoryInfo.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.ProcessErrorStateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearApplicationUserData(String str, boolean z, IPackageDataObserver iPackageDataObserver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iPackageDataObserver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppForUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean registerForegroundServiceObserver(IForegroundServiceObserver iForegroundServiceObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iForegroundServiceObserver);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageEvenWhenStopping(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceStopPackageByAdmin(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killPids(int[] iArr, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningServiceInfo> getServices(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningAppProcessInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder peekService(Intent intent, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean profileControl(String str, int i, boolean z, ProfilerInfo profilerInfo, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(profilerInfo, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shutdown(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopAppSwitches() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resumeAppSwitches() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean bindBackupAgent(String str, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backupAgentCreated(String str, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unbindBackupAgent(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addPackageDependency(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplication(String str, int i, int i2, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void closeSystemDialogsInDisplay(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Debug.MemoryInfo[]) parcelObtain2.createTypedArray(Debug.MemoryInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killApplicationProcess(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean handleApplicationWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(parcelableCrashInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killBackgroundProcesses(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserAMonkey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ApplicationInfo> getRunningExternalApplications() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ApplicationInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void finishHeavyWeightApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void handleApplicationStrictModeViolation(IBinder iBinder, int i, StrictMode.ViolationInfo violationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(violationInfo, 0);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerStrictModeCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopActivityImmersive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithType(int i, int i2, String str, int i3, String str2, boolean z, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void crashApplicationWithTypeWithExtras(int i, int i2, String str, int i3, String str2, boolean z, int i4, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMimeTypeFilterAsync(Uri uri, int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(115, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean dumpHeap(String str, int i, boolean z, boolean z2, boolean z3, String str2, String str3, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isUserRunning(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPackageScreenCompatMode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean switchUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingFromUserMessage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getSwitchingToUserMessage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setStopUserOnSwitch(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterProcessObserver(IProcessObserver iProcessObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iProcessObserver);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderTargetedToPackage(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(126, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfiguration(Configuration configuration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(127, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updatePersistentConfigurationWithAttribution(Configuration configuration, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(configuration, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long[] getProcessPss(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void showBootMessage(CharSequence charSequence, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killAllBackgroundProcesses() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ContentProviderHolder getContentProviderExternal(String str, int i, IBinder iBinder, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContentProviderHolder) parcelObtain2.readTypedObject(ContentProviderHolder.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternal(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeContentProviderExternalAsUser(String str, IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getMyMemoryState(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        runningAppProcessInfo.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean killProcessesBelowForeground(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public UserInfo getCurrentUser() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserInfo) parcelObtain2.readTypedObject(UserInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getCurrentUserId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unstableProviderDied(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isIntentSenderAnActivity(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUser(IApplicationThread iApplicationThread, String str, Intent intent, String str2, IBinder iBinder, String str3, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityAsUserWithFeature(IApplicationThread iApplicationThread, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i, int i2, ProfilerInfo profilerInfo, Bundle bundle, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUser(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithCallback(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserExceptCertainProfiles(int i, boolean z, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int stopUserWithDelayedLocking(int i, IStopUserCallback iStopUserCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iStopUserCallback);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserSwitchObserver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUserSwitchObserver(IUserSwitchObserver iUserSwitchObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserSwitchObserver);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getRunningUserIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestSystemServerHeapDump() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithDescription(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestTelephonyBugReport(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestWifiBugReport(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReportWithDescription(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestInteractiveBugReport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestBugReportWithExtraAttachments(List<Uri> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestFullBugReport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void requestRemoteBugReport(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean launchBugReportHandlerApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getBugreportWhitelistedPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Intent getIntentForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUid(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setUserIsMonkey(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void hang(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void moveTaskToRootTask(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setFocusedRootTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ActivityTaskManager.RootTaskInfo) parcelObtain2.readTypedObject(ActivityTaskManager.RootTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void restart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void performIdleMaintenance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotRespondingViaProvider(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Rect getTaskBounds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessMemoryTrimLevel(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getTagForIntentSender(IIntentSender iIntentSender, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackground(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isInLockTaskMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int startActivityFromRecents(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startSystemLockTaskMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void bootAnimationComplete() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateWindowVisible(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setThemeOverlayReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterTaskStackListener(ITaskStackListener iTaskStackListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskStackListener);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyCleartextNetwork(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTaskResizeable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resizeTask(int i, Rect rect, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getLockTaskModeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDumpHeapDebugLimit(String str, int i, long j, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dumpHeapFinished(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void updateLockTaskPackages(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmStart(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAlarmFinish(IIntentSender iIntentSender, WorkSource workSource, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getPackageProcessState(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startBinderTracking() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void suppressResizeConfigChanges(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser(int i, byte[] bArr, byte[] bArr2, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean unlockUser2(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killPackageDependents(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void makePackageIdle(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setDeterministicUidIdle(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMemoryTrimLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isVrModePackageEnabled(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void notifyLockedProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startConfirmDeviceCredentialIntent(Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void sendIdleJobTrigger() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int sendIntentSender(IApplicationThread iApplicationThread, IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIntentReceiver);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isBackgroundRestricted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setRenderThread(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setHasTopUi(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void cancelTaskWindowTransition(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void scheduleApplicationInfoChanged(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setPersistentVrThread(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForNetworkStateUpdate(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void backgroundAllowlistUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void startDelegateShellPermissionIdentity(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void stopDelegateShellPermissionIdentity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getDelegatedShellPermissions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParcelFileDescriptor getLifeMonitor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInForegroundWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void appNotResponding(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void doActiveLaunch(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(227, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationStartInfo> getHistoricalProcessStartReasons(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeApplicationStartInfoCompleteListener(IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationStartInfoCompleteListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addStartInfoTimestamp(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportStartInfoViewTimestamps(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(232, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<ApplicationExitInfo> getHistoricalProcessExitReasons(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(233, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killProcessesWhenImperceptible(int[] iArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(234, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setActivityLocusContext(ComponentName componentName, LocusId locusId, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(locusId, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setProcessStateSummary(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isAppFreezerEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(238, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void killUidForPermissionChange(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(239, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAppErrors() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(240, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableAppFreezer(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(241, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean enableFgsNotificationRateLimit(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(242, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void holdLock(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(243, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(244, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean stopProfile(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(245, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice queryIntentComponentsForIntentSender(IIntentSender iIntentSender, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(246, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getUidProcessCapabilities(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(247, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastIdle() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(248, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void waitForBroadcastBarrier() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(249, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void forceDelayBroadcastDelivery(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(250, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isProcessFrozen(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(251, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBackgroundRestrictionExemptionReason(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(252, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] queryRegisteredReceiverPackages(Intent intent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(253, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(254, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean startProfileWithListener(int i, IProgressListener iProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iProgressListener);
                    this.mRemote.transact(255, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int restartUserInBackground(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(256, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getDisplayIdsForStartingVisibleBackgroundUsers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(257, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean shouldServiceTimeOut(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(258, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean hasServiceTimeLimitExceeded(ComponentName componentName, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(259, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(260, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void unregisterUidFrozenStateChangedCallback(IUidFrozenStateChangedCallback iUidFrozenStateChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUidFrozenStateChangedCallback);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getUidFrozenState(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(262, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String[] getContentByTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(263, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(264, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean addLongLiveApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(265, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean removeLongLiveApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(266, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getMaxLongLiveApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(267, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(268, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearLongLiveTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(269, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcesses() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(270, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<String> getLongLiveProcessesForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(271, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List getLongLiveTaskIdsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(272, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean getAutoRemoveRecents(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(273, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void registerDedicatedCallback(RemoteCallback remoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(274, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setLongLiveApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(275, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getLongLiveApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(276, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBack(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(277, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean moveTaskToBackWithBundle(int i, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(278, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void dismissUserSwitchingDialog(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(279, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Configuration getGlobalConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(280, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Configuration) parcelObtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int checkPermissionForDevice(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(281, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void frozenBinderTransactionDetected(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(282, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int getBindingUidProcessState(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(283, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public long getUidLastIdleElapsedTime(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(284, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void addOverridePermissionState(int i, int i2, String str, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(285, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void removeOverridePermissionState(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(286, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearOverridePermissionStates(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(287, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearAllOverridePermissionStates(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(288, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void noteAppRestrictionEnabled(String str, int i, int i2, boolean z, int i3, String str2, int i4, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(289, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public IBinder refreshIntentCreatorToken(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(290, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void checkProfileForADCP(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(291, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean checkAutoRunBlockedApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(292, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void preloadBoosterAppsFromIpm(List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(293, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] updateFlingerFlag(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(294, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public SemAppRestrictionManager.RestrictionInfo getRestrictionInfo(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(295, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemAppRestrictionManager.RestrictionInfo) parcelObtain2.readTypedObject(SemAppRestrictionManager.RestrictionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean canRestrict(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(296, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean restrict(int i, int i2, boolean z, String str, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(297, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictableList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(298, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getAllRestrictedList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(299, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public List<SemAppRestrictionManager.AppRestrictionInfo> getRestrictedList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(300, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemAppRestrictionManager.AppRestrictionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean updateRestrictionInfo(SemAppRestrictionManager.RestrictionInfo restrictionInfo, List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(restrictionInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean clearRestrictionInfo(List<SemAppRestrictionManager.AppRestrictionInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setTTSPkgInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void clearTTSPkgInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public ParceledListSlice<PackageInfo> getInstalledPackageListFromMARs(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(305, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public String getPackageFromAppProcesses(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(306, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void reportAbnormalUsage(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(307, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isHeapDumpAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(308, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setFGSFilter(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(309, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void resetAbnormalList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(310, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean isFreezableUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(311, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public boolean setProcessSlowdown(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(312, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public int[] getIsolatedProcessList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(313, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setResourceCacheLimit(int i, int i2, IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(314, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getResourceCacheLimit(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(315, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getCurrentResourceCacheUsage(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(316, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void getCurrentResourceCacheMax(int i, IHwuiCallback iHwuiCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iHwuiCallback);
                    this.mRemote.transact(317, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public Bundle getOptionsForIntentSender(IIntentSender iIntentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntentSender);
                    this.mRemote.transact(318, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityManager
            public void setThreadRT(int i, int i2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(319, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        private boolean onTransact$registerUidObserverForUids$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IUidObserver iUidObserverAsInterface = IUidObserver.Stub.asInterface(parcel.readStrongBinder());
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            int[] iArrCreateIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            IBinder iBinderRegisterUidObserverForUids = registerUidObserverForUids(iUidObserverAsInterface, i, i2, string, iArrCreateIntArray);
            parcel2.writeNoException();
            parcel2.writeStrongBinder(iBinderRegisterUidObserverForUids);
            return true;
        }

        private boolean onTransact$startActivity$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string2 = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string3 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            int iStartActivity = startActivity(iApplicationThreadAsInterface, string, intent, string2, strongBinder, string3, i, i2, profilerInfo, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(iStartActivity);
            return true;
        }

        private boolean onTransact$startActivityWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string3 = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string4 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            int iStartActivityWithFeature = startActivityWithFeature(iApplicationThreadAsInterface, string, string2, intent, string3, strongBinder, string4, i, i2, profilerInfo, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(iStartActivityWithFeature);
            return true;
        }

        private boolean onTransact$registerReceiver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            String string2 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            Intent intentRegisterReceiver = registerReceiver(iApplicationThreadAsInterface, string, iIntentReceiverAsInterface, intentFilter, string2, i, i2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(intentRegisterReceiver, 1);
            return true;
        }

        private boolean onTransact$registerReceiverWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
            String string4 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            Intent intentRegisterReceiverWithFeature = registerReceiverWithFeature(iApplicationThreadAsInterface, string, string2, string3, iIntentReceiverAsInterface, intentFilter, string4, i, i2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(intentRegisterReceiverWithFeature, 1);
            return true;
        }

        private boolean onTransact$broadcastIntent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            int i = parcel.readInt();
            String string2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            String[] strArrCreateStringArray = parcel.createStringArray();
            int i2 = parcel.readInt();
            Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iBroadcastIntent = broadcastIntent(iApplicationThreadAsInterface, intent, string, iIntentReceiverAsInterface, i, string2, bundle, strArrCreateStringArray, i2, bundle2, z, z2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iBroadcastIntent);
            return true;
        }

        private boolean onTransact$broadcastIntentWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string2 = parcel.readString();
            IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            int i = parcel.readInt();
            String string3 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            String[] strArrCreateStringArray = parcel.createStringArray();
            String[] strArrCreateStringArray2 = parcel.createStringArray();
            String[] strArrCreateStringArray3 = parcel.createStringArray();
            int i2 = parcel.readInt();
            Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iBroadcastIntentWithFeature = broadcastIntentWithFeature(iApplicationThreadAsInterface, string, intent, string2, iIntentReceiverAsInterface, i, string3, bundle, strArrCreateStringArray, strArrCreateStringArray2, strArrCreateStringArray3, i2, bundle2, z, z2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iBroadcastIntentWithFeature);
            return true;
        }

        private boolean onTransact$finishReceiver$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            String string = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            boolean z = parcel.readBoolean();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            finishReceiver(strongBinder, i, string, bundle, z, i2);
            return true;
        }

        private boolean onTransact$moveTaskToFront$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            moveTaskToFront(iApplicationThreadAsInterface, string, i, i2, bundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getContentProvider$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            ContentProviderHolder contentProvider = getContentProvider(iApplicationThreadAsInterface, string, string2, i, z);
            parcel2.writeNoException();
            parcel2.writeTypedObject(contentProvider, 1);
            return true;
        }

        private boolean onTransact$startService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            ComponentName componentNameStartService = startService(iApplicationThreadAsInterface, intent, string, z, string2, string3, i);
            parcel2.writeNoException();
            parcel2.writeTypedObject(componentNameStartService, 1);
            return true;
        }

        private boolean onTransact$bindService$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder strongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long j = parcel.readLong();
            String string2 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iBindService = bindService(iApplicationThreadAsInterface, strongBinder, intent, string, iServiceConnectionAsInterface, j, string2, i);
            parcel2.writeNoException();
            parcel2.writeInt(iBindService);
            return true;
        }

        private boolean onTransact$bindServiceInstance$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder strongBinder = parcel.readStrongBinder();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long j = parcel.readLong();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iBindServiceInstance = bindServiceInstance(iApplicationThreadAsInterface, strongBinder, intent, string, iServiceConnectionAsInterface, j, string2, string3, i);
            parcel2.writeNoException();
            parcel2.writeInt(iBindServiceInstance);
            return true;
        }

        private boolean onTransact$startInstrumentation$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            String string = parcel.readString();
            int i = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            IInstrumentationWatcher iInstrumentationWatcherAsInterface = IInstrumentationWatcher.Stub.asInterface(parcel.readStrongBinder());
            IUiAutomationConnection iUiAutomationConnectionAsInterface = IUiAutomationConnection.Stub.asInterface(parcel.readStrongBinder());
            int i2 = parcel.readInt();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean zStartInstrumentation = startInstrumentation(componentName, string, i, bundle, iInstrumentationWatcherAsInterface, iUiAutomationConnectionAsInterface, i2, string2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zStartInstrumentation);
            return true;
        }

        private boolean onTransact$checkUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            IBinder strongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            int iCheckUriPermission = checkUriPermission(uri, i, i2, i3, i4, strongBinder);
            parcel2.writeNoException();
            parcel2.writeInt(iCheckUriPermission);
            return true;
        }

        private boolean onTransact$checkContentUriPermissionFull$(Parcel parcel, Parcel parcel2) throws RemoteException {
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iCheckContentUriPermissionFull = checkContentUriPermissionFull(uri, i, i2, i3, i4);
            parcel2.writeNoException();
            parcel2.writeInt(iCheckContentUriPermissionFull);
            return true;
        }

        private boolean onTransact$checkUriPermissions$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            IBinder strongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            int[] iArrCheckUriPermissions = checkUriPermissions(arrayListCreateTypedArrayList, i, i2, i3, i4, strongBinder);
            parcel2.writeNoException();
            parcel2.writeIntArray(iArrCheckUriPermissions);
            return true;
        }

        private boolean onTransact$grantUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            grantUriPermission(iApplicationThreadAsInterface, string, uri, i, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$revokeUriPermission$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            revokeUriPermission(iApplicationThreadAsInterface, string, uri, i, i2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$serviceDoneExecuting$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            parcel.enforceNoDataAvail();
            serviceDoneExecuting(strongBinder, i, i2, i3, intent);
            return true;
        }

        private boolean onTransact$getIntentSender$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string2 = parcel.readString();
            int i2 = parcel.readInt();
            Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
            String[] strArrCreateStringArray = parcel.createStringArray();
            int i3 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            IIntentSender intentSender = getIntentSender(i, string, strongBinder, string2, i2, intentArr, strArrCreateStringArray, i3, bundle, i4);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(intentSender);
            return true;
        }

        private boolean onTransact$getIntentSenderWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string3 = parcel.readString();
            int i2 = parcel.readInt();
            Intent[] intentArr = (Intent[]) parcel.createTypedArray(Intent.CREATOR);
            String[] strArrCreateStringArray = parcel.createStringArray();
            int i3 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            IIntentSender intentSenderWithFeature = getIntentSenderWithFeature(i, string, string2, strongBinder, string3, i2, intentArr, strArrCreateStringArray, i3, bundle, i4);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(intentSenderWithFeature);
            return true;
        }

        private boolean onTransact$noteWakeupAlarm$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntentSender iIntentSenderAsInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
            WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            noteWakeupAlarm(iIntentSenderAsInterface, workSource, i, string, string2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setServiceForeground$(Parcel parcel, Parcel parcel2) throws RemoteException {
            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setServiceForeground(componentName, strongBinder, i, notification, i2, i3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$profileControl$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            int i2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zProfileControl = profileControl(string, i, z, profilerInfo, i2);
            parcel2.writeNoException();
            parcel2.writeBoolean(zProfileControl);
            return true;
        }

        private boolean onTransact$bindBackupAgent$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            boolean z = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean zBindBackupAgent = bindBackupAgent(string, i, i2, i3, z);
            parcel2.writeNoException();
            parcel2.writeBoolean(zBindBackupAgent);
            return true;
        }

        private boolean onTransact$handleIncomingUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iHandleIncomingUser = handleIncomingUser(i, i2, i3, z, z2, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iHandleIncomingUser);
            return true;
        }

        private boolean onTransact$killApplication$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string2 = parcel.readString();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            killApplication(string, i, i2, string2, i3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$handleApplicationWtf$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IBinder strongBinder = parcel.readStrongBinder();
            String string = parcel.readString();
            boolean z = parcel.readBoolean();
            ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo = (ApplicationErrorReport.ParcelableCrashInfo) parcel.readTypedObject(ApplicationErrorReport.ParcelableCrashInfo.CREATOR);
            int i = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zHandleApplicationWtf = handleApplicationWtf(strongBinder, string, z, parcelableCrashInfo, i);
            parcel2.writeNoException();
            parcel2.writeBoolean(zHandleApplicationWtf);
            return true;
        }

        private boolean onTransact$crashApplicationWithType$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            int i3 = parcel.readInt();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            crashApplicationWithType(i, i2, string, i3, string2, z, i4);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$crashApplicationWithTypeWithExtras$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            int i3 = parcel.readInt();
            String string2 = parcel.readString();
            boolean z = parcel.readBoolean();
            int i4 = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            crashApplicationWithTypeWithExtras(i, i2, string, i3, string2, z, i4, bundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$dumpHeap$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            boolean z3 = parcel.readBoolean();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
            RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
            parcel.enforceNoDataAvail();
            boolean zDumpHeap = dumpHeap(string, i, z, z2, z3, string2, string3, parcelFileDescriptor, remoteCallback);
            parcel2.writeNoException();
            parcel2.writeBoolean(zDumpHeap);
            return true;
        }

        private boolean onTransact$startActivityAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string2 = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string3 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iStartActivityAsUser = startActivityAsUser(iApplicationThreadAsInterface, string, intent, string2, strongBinder, string3, i, i2, profilerInfo, bundle, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iStartActivityAsUser);
            return true;
        }

        private boolean onTransact$startActivityAsUserWithFeature$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            String string = parcel.readString();
            String string2 = parcel.readString();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string3 = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            String string4 = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            ProfilerInfo profilerInfo = (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR);
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iStartActivityAsUserWithFeature = startActivityAsUserWithFeature(iApplicationThreadAsInterface, string, string2, intent, string3, strongBinder, string4, i, i2, profilerInfo, bundle, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iStartActivityAsUserWithFeature);
            return true;
        }

        private boolean onTransact$noteAlarmFinish$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IIntentSender iIntentSenderAsInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
            WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
            int i = parcel.readInt();
            String string = parcel.readString();
            parcel.enforceNoDataAvail();
            noteAlarmFinish(iIntentSenderAsInterface, workSource, i, string);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unlockUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            byte[] bArrCreateByteArray2 = parcel.createByteArray();
            IProgressListener iProgressListenerAsInterface = IProgressListener.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            boolean zUnlockUser = unlockUser(i, bArrCreateByteArray, bArrCreateByteArray2, iProgressListenerAsInterface);
            parcel2.writeNoException();
            parcel2.writeBoolean(zUnlockUser);
            return true;
        }

        private boolean onTransact$sendIntentSender$(Parcel parcel, Parcel parcel2) throws RemoteException {
            IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IIntentSender iIntentSenderAsInterface = IIntentSender.Stub.asInterface(parcel.readStrongBinder());
            IBinder strongBinder = parcel.readStrongBinder();
            int i = parcel.readInt();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            String string = parcel.readString();
            IIntentReceiver iIntentReceiverAsInterface = IIntentReceiver.Stub.asInterface(parcel.readStrongBinder());
            String string2 = parcel.readString();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            int iSendIntentSender = sendIntentSender(iApplicationThreadAsInterface, iIntentSenderAsInterface, strongBinder, i, intent, string, iIntentReceiverAsInterface, string2, bundle);
            parcel2.writeNoException();
            parcel2.writeInt(iSendIntentSender);
            return true;
        }

        private boolean onTransact$getHistoricalProcessExitReasons$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            ParceledListSlice<ApplicationExitInfo> historicalProcessExitReasons = getHistoricalProcessExitReasons(string, i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeTypedObject(historicalProcessExitReasons, 1);
            return true;
        }

        private boolean onTransact$checkPermissionForDevice$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int iCheckPermissionForDevice = checkPermissionForDevice(string, i, i2, i3);
            parcel2.writeNoException();
            parcel2.writeInt(iCheckPermissionForDevice);
            return true;
        }

        private boolean onTransact$frozenBinderTransactionDetected$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            frozenBinderTransactionDetected(i, i2, i3, i4);
            return true;
        }

        private boolean onTransact$addOverridePermissionState$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            String string = parcel.readString();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            addOverridePermissionState(i, i2, string, i3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$noteAppRestrictionEnabled$(Parcel parcel, Parcel parcel2) throws RemoteException {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            int i3 = parcel.readInt();
            String string2 = parcel.readString();
            int i4 = parcel.readInt();
            long j = parcel.readLong();
            parcel.enforceNoDataAvail();
            noteAppRestrictionEnabled(string, i, i2, z, i3, string2, i4, j);
            parcel2.writeNoException();
            return true;
        }

        protected void refreshIntentCreatorToken_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, getCallingPid(), getCallingUid());
        }

        private boolean onTransact$restrict$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            String string = parcel.readString();
            int i3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean zRestrict = restrict(i, i2, z, string, i3);
            parcel2.writeNoException();
            parcel2.writeBoolean(zRestrict);
            return true;
        }

        private boolean onTransact$setThreadRT$(Parcel parcel, Parcel parcel2) throws RemoteException {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            boolean z = parcel.readBoolean();
            boolean z2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setThreadRT(i, i2, z, z2);
            parcel2.writeNoException();
            return true;
        }
    }
}
