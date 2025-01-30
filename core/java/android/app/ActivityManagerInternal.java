package android.app;

import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.p002pm.ActivityInfo;
import android.content.p002pm.ActivityPresentationInfo;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.UserInfo;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.LocaleList;
import android.p009os.TransactionTooLargeException;
import android.p009os.WorkSource;
import android.util.ArraySet;
import android.util.Pair;
import android.util.StatsEvent;
import com.android.internal.p029os.TimeoutRecord;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public abstract class ActivityManagerInternal {
    public static final int ALLOW_FULL_ONLY = 2;
    public static final int ALLOW_NON_FULL = 0;
    public static final int ALLOW_NON_FULL_IN_PROFILE = 1;
    public static final int ALLOW_PROFILES_OR_NON_FULL = 3;
    public static final int MEDIA_PROJECTION_TOKEN_EVENT_CREATED = 0;
    public static final int MEDIA_PROJECTION_TOKEN_EVENT_DESTROYED = 1;
    public static final int OOM_ADJ_REASON_ACTIVITY = 1;
    public static final int OOM_ADJ_REASON_ALLOWLIST = 10;
    public static final int OOM_ADJ_REASON_BACKUP = 15;
    public static final int OOM_ADJ_REASON_BINDER_BUFFER_FULL = 25;
    public static final int OOM_ADJ_REASON_BIND_SERVICE = 4;
    public static final int OOM_ADJ_REASON_COMPONENT_DISABLED = 22;
    public static final int OOM_ADJ_REASON_EXECUTING_SERVICE = 20;
    public static final int OOM_ADJ_REASON_FGSFILTER = 24;
    public static final int OOM_ADJ_REASON_FINISH_RECEIVER = 2;
    public static final int OOM_ADJ_REASON_GET_PROVIDER = 7;
    public static final int OOM_ADJ_REASON_NONE = 0;
    public static final int OOM_ADJ_REASON_PROCESS_BEGIN = 11;
    public static final int OOM_ADJ_REASON_PROCESS_END = 12;
    public static final int OOM_ADJ_REASON_REMOVE_PROVIDER = 8;
    public static final int OOM_ADJ_REASON_REMOVE_TASK = 17;
    public static final int OOM_ADJ_REASON_RESTRICTION_CHANGE = 21;
    public static final int OOM_ADJ_REASON_SHELL = 16;
    public static final int OOM_ADJ_REASON_SHORT_FGS_TIMEOUT = 13;
    public static final int OOM_ADJ_REASON_SLOWDOWN = 23;
    public static final int OOM_ADJ_REASON_START_RECEIVER = 3;
    public static final int OOM_ADJ_REASON_START_SERVICE = 6;
    public static final int OOM_ADJ_REASON_STOP_SERVICE = 19;
    public static final int OOM_ADJ_REASON_SYSTEM_INIT = 14;
    public static final int OOM_ADJ_REASON_UID_IDLE = 18;
    public static final int OOM_ADJ_REASON_UI_VISIBILITY = 9;
    public static final int OOM_ADJ_REASON_UNBIND_SERVICE = 5;

    public interface BindServiceEventListener {
        void onBindingService(String str, int i);
    }

    public interface BroadcastEventListener {
        void onSendingBroadcast(String str, int i);
    }

    public interface ForegroundServiceStateListener {
        void onForegroundServiceNotificationUpdated(String str, int i, int i2, boolean z);

        void onForegroundServiceStateChanged(String str, int i, int i2, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaProjectionTokenEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OomAdjReason {
    }

    public enum ServiceNotificationPolicy {
        NOT_FOREGROUND_SERVICE,
        SHOW_IMMEDIATELY,
        UPDATE_ONLY
    }

    public interface VoiceInteractionManagerProvider {
        void notifyActivityDestroyed(IBinder iBinder);
    }

    public abstract void addAppBackgroundRestrictionListener(AppBackgroundRestrictionListener appBackgroundRestrictionListener);

    public abstract void addBindServiceEventListener(BindServiceEventListener bindServiceEventListener);

    public abstract void addBroadcastEventListener(BroadcastEventListener broadcastEventListener);

    public abstract void addForegroundServiceStateListener(ForegroundServiceStateListener foregroundServiceStateListener);

    public abstract void addPendingTopUid(int i, int i2, IApplicationThread iApplicationThread);

    public abstract void addToLocaleChangedHistoryLocked(LocaleList localeList, LocaleList localeList2, boolean z);

    public abstract void appNotResponding(String str, int i, TimeoutRecord timeoutRecord);

    public abstract ServiceNotificationPolicy applyForegroundServiceNotification(Notification notification, String str, int i, String str2, int i2);

    public abstract void broadcastCloseSystemDialogs(String str);

    public abstract void broadcastCloseSystemDialogs(String str, int i);

    public abstract void broadcastGlobalConfigurationChanged(int i, boolean z);

    public abstract int broadcastIntent(Intent intent, IIntentReceiver iIntentReceiver, String[] strArr, boolean z, int i, int[] iArr, BiFunction<Integer, Bundle, Bundle> biFunction, Bundle bundle);

    public abstract int broadcastIntentInPackage(IApplicationThread iApplicationThread, String str, String str2, int i, int i2, int i3, Intent intent, String str3, IApplicationThread iApplicationThread2, IIntentReceiver iIntentReceiver, int i4, String str4, Bundle bundle, String str5, Bundle bundle2, boolean z, boolean z2, int i5, BackgroundStartPrivileges backgroundStartPrivileges, int[] iArr);

    public abstract int broadcastIntentWithCallback(Intent intent, IIntentReceiver iIntentReceiver, String[] strArr, int i, int[] iArr, BiFunction<Integer, Bundle, Bundle> biFunction, Bundle bundle);

    public abstract boolean canScheduleUserInitiatedJobs(int i, int i2, String str);

    public abstract boolean canStartMoreUsers();

    public abstract boolean cancelDisablePolicy(String str, int i, int i2);

    public abstract void cancelKillProcessWhenDexExit();

    public abstract String checkContentProviderAccess(String str, int i);

    public abstract int checkContentProviderUriPermission(Uri uri, int i, int i2, int i3);

    public abstract void cleanUpServices(int i, ComponentName componentName, Intent intent);

    public abstract void clearPendingBackup(int i);

    public abstract void clearPendingIntentAllowBgActivityStarts(IIntentSender iIntentSender, IBinder iBinder);

    public abstract void deletePendingTopUid(int i, long j);

    public abstract void disconnectActivityFromServices(Object obj);

    public abstract void enforceBroadcastOptionsPermissions(Bundle bundle, int i);

    public abstract void enforceCallingPermission(String str, String str2);

    public abstract void ensureBootCompleted();

    public abstract void ensureNotSpecialUser(int i);

    public abstract void finishBooting();

    public abstract void finishUserSwitch(Object obj);

    public abstract ActivityInfo getActivityInfoForUser(ActivityInfo activityInfo, int i);

    public abstract ActivityPresentationInfo getActivityPresentationInfo(IBinder iBinder);

    public abstract Pair<String, String> getAppProfileStatsForDebugging(long j, int i);

    public abstract BackgroundStartPrivileges getBackgroundStartPrivileges(int i);

    public abstract long getBootTimeTempAllowListDuration();

    public abstract StatsEvent getCachedAppsHighWatermarkStats(int i, boolean z);

    public abstract ArraySet<String> getClientPackages(String str);

    public abstract Pair<Integer, Integer> getCurrentAndTargetUserIds();

    public abstract int[] getCurrentProfileIds();

    public abstract UserInfo getCurrentUser();

    public abstract int getCurrentUserId();

    public abstract int getInstrumentationSourceUid(int i);

    public abstract Intent getIntentForIntentSender(IIntentSender iIntentSender);

    public abstract boolean getIsDataClearedInAms(String str, int i);

    public abstract List<Integer> getIsolatedProcesses(int i);

    public abstract Intent getLaunchIntentForPackage(String str, int i);

    public abstract int getMaxRunningUsers();

    public abstract List<ProcessMemoryState> getMemoryStateForProcesses();

    public abstract String getPackageNameByPid(int i);

    public abstract PendingIntent getPendingIntentActivityAsApp(int i, Intent intent, int i2, Bundle bundle, String str, int i3);

    public abstract PendingIntent getPendingIntentActivityAsApp(int i, Intent[] intentArr, int i2, Bundle bundle, String str, int i3);

    public abstract int getPendingIntentFlags(IIntentSender iIntentSender);

    public abstract List<PendingIntentStats> getPendingIntentStats();

    public abstract Map<Integer, String> getProcessesWithPendingBindMounts(int i);

    public abstract int getPushMessagingOverQuotaBehavior();

    public abstract IUnsafeIntentStrictModeCallback getRegisteredStrictModeCallback(int i);

    public abstract int getRestrictionLevel(int i);

    public abstract int getRestrictionLevel(String str, int i);

    public abstract int getServiceStartForegroundTimeout();

    public abstract int[] getStartedUserIds();

    public abstract int getStorageMountMode(int i, int i2);

    public abstract int getTaskIdForActivity(IBinder iBinder, boolean z);

    public abstract int getUidCapability(int i);

    public abstract int getUidProcessState(int i);

    public abstract int handleIncomingUser(int i, int i2, int i3, boolean z, int i4, String str, String str2);

    public abstract boolean hasForegroundServiceNotification(String str, int i, String str2);

    public abstract boolean hasMetaData(String str, String str2);

    public abstract boolean hasRunningActivity(int i, String str);

    public abstract boolean hasRunningForegroundService(int i, int i2);

    public abstract boolean hasStartedUserState(int i);

    public abstract void inputDispatchingResumed(int i);

    public abstract long inputDispatchingTimedOut(int i, boolean z, TimeoutRecord timeoutRecord);

    public abstract boolean inputDispatchingTimedOut(Object obj, String str, ApplicationInfo applicationInfo, String str2, Object obj2, boolean z, TimeoutRecord timeoutRecord);

    public abstract boolean isActivityStartsLoggingEnabled();

    public abstract boolean isAppBad(String str, int i);

    public abstract boolean isAppForeground(int i);

    public abstract boolean isAppStartModeDisabled(int i, String str);

    public abstract boolean isAssociatedCompanionApp(int i, int i2);

    public abstract boolean isAutoRunBlockedApp(String str, int i);

    public abstract boolean isBackgroundActivityStartsEnabled();

    public abstract boolean isBgAutoRestrictedBucketFeatureFlagEnabled();

    public abstract boolean isBooted();

    public abstract boolean isBooting();

    public abstract boolean isCurrentProfile(int i);

    public abstract boolean isDeviceOwner(int i);

    public abstract boolean isModernQueueEnabled();

    public abstract boolean isPendingTopUid(int i);

    public abstract boolean isProfileOwner(int i);

    public abstract boolean isRestrictedPackage(ComponentName componentName, String str, int i, String str2, Intent intent, int i2);

    public abstract boolean isRuntimeRestarted();

    public abstract boolean isSystemReady();

    public abstract boolean isTempAllowlistedForFgsWhileInUse(int i);

    public abstract boolean isUidActive(int i);

    public abstract boolean isUserRunning(int i, int i2);

    public abstract void killAllBackgroundProcessesExcept(int i, int i2);

    public abstract void killAllBackgroundProcessesExcept(int i, int i2, Bundle bundle);

    public abstract void killForegroundAppsForUser(int i);

    public abstract void killPackageProcesses(String str, int i, int i2, String str2, Runnable runnable, Runnable runnable2);

    public abstract void killProcess(String str, int i, String str2);

    public abstract void killProcessForDex(int i, int i2, String str);

    public abstract void killProcessWhenDexExit();

    public abstract void killProcessesForRemovedTask(ArrayList<Object> arrayList);

    public abstract void logFgsApiBegin(int i, int i2, int i3);

    public abstract void logFgsApiEnd(int i, int i2, int i3);

    public abstract void monitor();

    public abstract void noteAlarmFinish(PendingIntent pendingIntent, WorkSource workSource, int i, String str);

    public abstract void noteAlarmStart(PendingIntent pendingIntent, WorkSource workSource, int i, String str);

    public abstract void noteWakeupAlarm(PendingIntent pendingIntent, WorkSource workSource, int i, String str, String str2);

    public abstract void notifyMediaProjectionEvent(int i, IBinder iBinder, int i2);

    public abstract void notifyNetworkPolicyRulesUpdated(int i, long j);

    public abstract void onForegroundServiceNotificationUpdate(boolean z, Notification notification, int i, String str, int i2);

    public abstract void onPackagePausedBG(String str, String str2, boolean z, int i);

    public abstract void onPackageResumedFG(List<String> list, String str, String str2, boolean z, Intent intent, int i);

    public abstract void onUidBlockedReasonsChanged(int i, int i2);

    public abstract void onUserRemoved(int i);

    public abstract void onWakefulnessChanged(int i);

    public abstract void prepareForPossibleShutdown();

    public abstract void registerAnrController(AnrController anrController);

    public abstract void registerNetworkPolicyUidObserver(IUidObserver iUidObserver, int i, int i2, String str);

    public abstract void registerProcessObserver(IProcessObserver iProcessObserver);

    public abstract void reportCurKeyguardUsageEvent(boolean z);

    public abstract void requestCustomFullBugreport();

    public abstract void rescheduleAnrDialog(Object obj);

    public abstract void resetUpdateConfigurationCallerLocked();

    public abstract void restart();

    public abstract void scheduleAppGcs();

    public abstract void sendForegroundProfileChanged(int i);

    public abstract int sendIntentSender(IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle);

    public abstract int sendIntentSender(IIntentSender iIntentSender, IBinder iBinder, int i, Intent intent, String str, IIntentReceiver iIntentReceiver, String str2, Bundle bundle, int i2, int i3);

    public abstract void setBooted(boolean z);

    public abstract void setBooting(boolean z);

    public abstract void setCompanionAppUids(int i, Set<Integer> set);

    public abstract void setDebugFlagsForStartingActivity(ActivityInfo activityInfo, int i, ProfilerInfo profilerInfo, Object obj);

    public abstract void setDeviceIdleAllowlist(int[] iArr, int[] iArr2);

    public abstract void setDeviceOwnerUid(int i);

    public abstract void setHasOverlayUi(int i, boolean z);

    public abstract void setHasTopUiInternal(int i, boolean z);

    public abstract void setKeyguardPkgInfo(String str, int i);

    public abstract void setLongLiveProcess(int i);

    public abstract void setPendingIntentAllowBgActivityStarts(IIntentSender iIntentSender, IBinder iBinder, int i);

    public abstract void setPendingIntentAllowlistDuration(IIntentSender iIntentSender, IBinder iBinder, long j, int i, int i2, String str);

    public abstract void setProfileOwnerUid(ArraySet<Integer> arraySet);

    public abstract void setStopUserOnSwitch(int i);

    public abstract void setSwitchingFromSystemUserMessage(String str);

    public abstract void setSwitchingToSystemUserMessage(String str);

    public abstract void setUpdateConfigurationCallerLocked(int i);

    public abstract void setVoiceInteractionManagerProvider(VoiceInteractionManagerProvider voiceInteractionManagerProvider);

    public abstract boolean shouldConfirmCredentials(int i);

    public abstract boolean startForegroundServiceDelegate(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, ServiceConnection serviceConnection);

    public abstract boolean startIsolatedProcess(String str, String[] strArr, String str2, String str3, int i, Runnable runnable);

    public abstract void startProcess(String str, ApplicationInfo applicationInfo, boolean z, boolean z2, String str2, ComponentName componentName);

    public abstract void startProcess(String str, ApplicationInfo applicationInfo, boolean z, boolean z2, String str2, ComponentName componentName, int i, int i2);

    public abstract void startProcess(String str, ApplicationInfo applicationInfo, boolean z, boolean z2, String str2, ComponentName componentName, boolean z3, int i);

    public abstract boolean startProfileEvenWhenDisabled(int i);

    public abstract ComponentName startServiceInPackage(IApplicationThread iApplicationThread, int i, Intent intent, String str, boolean z, String str2, String str3, int i2, BackgroundStartPrivileges backgroundStartPrivileges) throws TransactionTooLargeException;

    public abstract void stopAppForUser(String str, int i);

    public abstract void stopForegroundServiceDelegate(ForegroundServiceDelegationOptions foregroundServiceDelegationOptions);

    public abstract void stopForegroundServiceDelegate(ServiceConnection serviceConnection);

    public abstract void tempAllowWhileInUsePermissionInFgs(int i, long j);

    public abstract void tempAllowlistForPendingIntent(int i, int i2, int i3, long j, int i4, int i5, String str);

    public abstract void trimApplications();

    public abstract void unregisterAnrController(AnrController anrController);

    public abstract void unregisterProcessObserver(IProcessObserver iProcessObserver);

    public abstract void unregisterStrictModeCallback(int i);

    public abstract void updateActivityUsageStats(ComponentName componentName, int i, int i2, IBinder iBinder, ComponentName componentName2, ActivityId activityId);

    public abstract void updateActivityUsageStatsWithIntent(ComponentName componentName, int i, int i2, IBinder iBinder, ComponentName componentName2, ActivityId activityId, Intent intent);

    public abstract void updateBackupServicePkg(int i, boolean z);

    public abstract void updateBatteryStats(ComponentName componentName, int i, int i2, boolean z);

    public abstract void updateCpuStats();

    public abstract void updateDeviceIdleTempAllowlist(int[] iArr, int i, boolean z, long j, int i2, int i3, String str, int i4);

    public abstract void updateForegroundTimeIfOnBattery(String str, int i, long j);

    public abstract void updateOomAdj(int i);

    public abstract void updateOomLevelsForDisplay(int i);

    public abstract void updateTransitionPlayerPid(int i);

    public interface AppBackgroundRestrictionListener {
        default void onRestrictionLevelChanged(int uid, String packageName, int newLevel) {
        }

        default void onAutoRestrictedBucketFeatureFlagChanged(boolean autoRestrictedBucket) {
        }
    }
}
